let deleteFile = (id) => {
    let url = window.location.origin + `/files/${id}`;
    console.log(url)
    var settings = {
        "url": url,
        "method": "DELETE",
        timeout: 10000,
    };

    $.ajax(settings).done(function (response) {
        console.log(response);
    });
    location.reload();
}
let getHome = () => {
    var settings = {
        "url": window.location.origin + '/home',
        "method": "GET",
        "timeout": 0,
    };

    $.ajax(settings).done(function (response) {
        console.log(response);
    });
}
