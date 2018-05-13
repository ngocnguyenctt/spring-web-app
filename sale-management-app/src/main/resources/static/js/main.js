$(document).ready(function () {
    var pathArr = location.pathname.split("/");

    if (location.pathname === "/") {
        $("aside li").removeClass();
        $("#home").addClass("active");
    }

    if (pathArr.includes("salesPerson")) {
        $("aside li").removeClass();
        $("#sales-person").addClass("active");
    }

    if (pathArr.includes("customer")) {
        $("aside li").removeClass();
        $("#customer").addClass("active");
    }

    if (pathArr.includes("item")) {
        $("aside li").removeClass();
        $("#item").addClass("active");
    }

    if (pathArr.includes("user")) {
        $("aside li").removeClass();
        $("#user").addClass("active");
    }

    if (pathArr.includes("salesOrder")) {
        $("aside li").removeClass();
        $("#sales-order").addClass("active");
        var itemCount = $("#input-sales-item tr").length;
        for (var i = 0; i < itemCount; i++) {
            var index = $("#input-sales-item tr").eq(i).find("input")[0].value;
            $("#itemId-" + index).attr("id", "items[" + i + "].salesOrderDetailId.item.itemId").addClass("sod-itemId-" + i).attr("name", "items[" + i + "].salesOrderDetailId.item.itemId");
            $("#itemName-" + index).attr("id", "items[" + i + "].salesOrderDetailId.item.itemName").addClass("sod-itemName-" + i).attr("name", "items[" + i + "].salesOrderDetailId.item.itemName");
            $("#qty-" + index).attr("id", "items[" + i + "].qty").addClass("sod-qty-" + i).attr("name", "items[" + i + "].qty");
            $("#price-" + index).attr("id", "items[" + i + "].price").addClass("sod-price-" + i).attr("name", "items[" + i + "].price");
            $("#unit-" + index).attr("id", "items[" + i + "].salesOrderDetailId.item.unit").addClass("sod-unit-" + i).attr("name", "items[" + i + "].salesOrderDetailId.item.unit");
            $("#itemDisc-" + index).attr("id", "items[" + i + "].itemDisc").addClass("sod-itemDisc-" + i).attr("name", "items[" + i + "].itemDisc");
            $("#taxAmt-" + index).attr("id", "items[" + i + "].taxAmt").addClass("sod-taxAmt-" + i).attr("name", "items[" + i + "].taxAmt");
            $("#amount-" + index).attr("id", "items[" + i + "].amount").addClass("sod-amount-" + i).attr("name", "items[" + i + "].amount");
        }
    }

    if (localStorage.language === "vi") {
        $(".show-language").html($(".language .dropdown-menu li:nth-child(2)").children()[0]);
    }

    $(document).on("click", ".select-language li", function () {
        if (["Tiếng việt", "Vietnamese"].includes($(this).children()[0].text.trim())) {
            localStorage.setItem("language", "vi");
        }
        if (["Tiếng Anh", "English"].includes($(this).children()[0].text.trim())) {
            localStorage.setItem("language", "en");
        }
    });
});