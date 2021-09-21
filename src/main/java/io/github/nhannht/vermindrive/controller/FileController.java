package io.github.nhannht.vermindrive.controller;

import io.github.nhannht.vermindrive.model.File;
import io.github.nhannht.vermindrive.service.FileService;
import io.github.nhannht.vermindrive.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {

    private final UserService users;
    private final FileService files;
    Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    public FileController(UserService userService, FileService fileService) {
        this.users = userService;
        this.files = fileService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadAView(
            HttpServletResponse response,
            Authentication authentication,
            @RequestParam(name = "file") MultipartFile multipartFile,
            Model model
    ) {
        List<String> errors = new ArrayList<String>();

        if (multipartFile.isEmpty())
            errors.add("We cannot let this field empty");

        model.addAttribute("success", true);
        var UID = users.getUser(authentication.getName()).getUserId();
        try {
            var file = new File(
                    multipartFile.getOriginalFilename(),
                    multipartFile.getSize(),
                    multipartFile.getContentType(),
                    multipartFile.getBytes(),
                    UID
            );

            if (files.exists(file)) {
                errors.add("Already have this file");
                model.addAttribute("errors", errors);
                model.addAttribute("success", false);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return "result";
            }

            files.store(file);

        } catch (Exception ignored) {
            errors.add("We have something wrong here");
            model.addAttribute("success", false);
            model.addAttribute("errors", errors);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "result";
    }

    @GetMapping(value = "/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadView(
            HttpServletResponse response,
            Authentication authentication,
            @PathVariable Integer fileId
    ) {
        var UID = users.getUser(authentication.getName()).getUserId();
        var file   = files.get(new File(fileId, UID));
        if (file != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header("Content-Disposition", "attachment; filename=" + file.getName())
                    .body(new ByteArrayResource(file.getData()));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{fileId}")
    public String removeView(
            Authentication authentication,
            HttpServletResponse response,
            @PathVariable Integer fileId,
            Model model
    ) {
        var UID = users.getUser(authentication.getName()).getUserId();
        logger.error("UID is " + UID);
        List<String> errors = new ArrayList<String>();
        model.addAttribute("success", true);
        try {
            logger.error("Success action");
            files.remove(new File(fileId, UID));

        } catch (Exception ignore) {
            errors.add("The file will not be drop. We have some bug here");
            model.addAttribute("errors", errors);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "result";
    }


}
