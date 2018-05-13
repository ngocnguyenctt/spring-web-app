$(document).ready(function () {
    var size = 10;
    var currentPage = 1;

    if ($("#sales-person-pagination")[0] != null) {
        currentPage = parseInt($("#sales-person-pagination input")[0].value);
    }

    $("#sales-person-pagination .next").on("click", function () {
        var nextPage = currentPage + 1;
        location.href = "/salesPerson?page=" + nextPage + "&size=" + size;
    });

    $("#sales-person-pagination .prev").on("click", function () {
        var prevPage = currentPage - 1;
        location.href = "/salesPerson?page=" + prevPage + "&size=" + size;
    });

    if ($("#customer-pagination")[0] != null) {
        currentPage = parseInt($("#customer-pagination input")[0].value);
    }

    $("#customer-pagination .next").on("click", function () {
        var nextPage = currentPage + 1;
        location.href = "/customer?page=" + nextPage + "&size=" + size;
    });

    $("#customer-pagination .prev").on("click", function () {
        var prevPage = currentPage - 1;
        location.href = "/customer?page=" + prevPage + "&size=" + size;
    });

    if ($("#item-pagination")[0] != null) {
        currentPage = parseInt($("#item-pagination input")[0].value);
    }

    $("#item-pagination .next").on("click", function () {
        var nextPage = currentPage + 1;
        location.href = "/item?page=" + nextPage + "&size=" + size;
    });

    $("#item-pagination .prev").on("click", function () {
        var prevPage = currentPage - 1;
        location.href = "/item?page=" + prevPage + "&size=" + size;
    });
});