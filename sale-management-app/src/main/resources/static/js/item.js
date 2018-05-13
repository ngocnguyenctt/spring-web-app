$(document).ready(function () {
    $(document).on("click", ".select-item", function () {
        var value = this.value;
        var that = $("#item-" + value).closest("tr");
        var statusValue;
        switch (that.find("td").eq(4).text()) {
            case "Active":
                statusValue = "AV";
                break;
            case "Deleted":
                statusValue = "DE";
                break;
            case "UnActive":
                statusValue = "UA";
                break;
            default:
                statusValue = "";
                break;
        }
        $("#status").find("option[selected='selected']").removeAttr("selected");
        $("#itemId")[0].value = that.find("td").eq(1).text();
        $("#itemName")[0].value = that.find("td").eq("2").text();
        $("#qty")[0].value = that.find("td").eq(3).text();
        $("#qtyStock")[0].value = that.find("td").eq(4).text();
        $("#price")[0].value = that.find("td").eq(5).text();
        $("#unit")[0].value = that.find("td").eq(6).text();
        $("#itemDisc")[0].value = that.find("td").eq(7).text();
        $("#taxAmt")[0].value = that.find("td").eq(8).text();
        $("#amount")[0].value = that.find("td").eq(9).text();
    });

    $("#itemId").on("change", function () {
        var url = "/items/" + $(this)[0].value + "/info";
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            beforeSend(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                $("#itemId").parent().next("span").remove();
            },
            success: function (response) {
                $("#itemId")[0].value = response.itemId;
                $("#itemName")[0].value = response.itemName;
                $("#qty")[0].value = response.qty;
                $("#qtyStock")[0].value = response.qtyStock;
                $("#price")[0].value = response.price;
                $("#taxAmt")[0].value = response.taxAmt;
                $("#unit")[0].value = response.unit;
                $("#itemDisc")[0].value = response.itemDisc;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                var responseBody = $.parseJSON(jqXHR.responseText);
                $("#itemId").closest("td").append("<span class='error'>" + responseBody.message + "</span>");
                $("#itemName")[0].value = "";
                $("#qty")[0].value = "";
                $("#qtyStock")[0].value = "";
                $("#price")[0].value = "";
                $("#taxAmt")[0].value = "";
                $("#unit")[0].value = "";
                $("#itemDisc")[0].value = "";
            }
        });
    });

    $("#basic-addon1").on("click", function () {
        $("#itemId").removeAttr("readonly");
    });

    $("#item-search").on("click", function () {
        var inputElement = $(".input-form input");
        var valueArr = inputElement.map(function (index, input) {
            return input.value;
        });
        var url = "/item/search?itemId=" + valueArr[0] + "&itemName=" + valueArr[1] + "&qty=" + valueArr[2] +
            "&qtyStock=" + valueArr[3] + "&price=" + valueArr[4] + "&unit=" + valueArr[5] + "&itemDisc=" + valueArr[6] + "&taxAmt=" + valueArr[7];
        location.href = url;
    });

    $("[data-toggle='popover']").on("click", function () {
        $(this).popover("show");
    });

    $('[data-toggle="popover"]').popover({
        html: true,
        placement: 'top',
        trigger: 'manual',
        template: '<div class="popover" style="width: 500px">' +
        '<div class="arrow"></div>' +
        '<h3 class="popover-title" style="font-weight:bold; background-color: #add8e6; font-size: 13px;"></h3>' +
        '<div class="popover-content" style="height: 30px; padding-top: 17px; text-align: center; color: red; background-color: #faebd7"></div>' +
        '<div class="popover-footer" style="padding: 20px 11px; background-color: #faebd7;">' +
        '<a href="#" class="btn-delete btn btn-primary" style="width: 95px; margin-right: 16px; margin-left: 22px;">' +
        'Yes&nbsp;</a>' + '<a href="#" class="btn-close btn btn-default" style="width: 95px;">No</a>' + '</div></div>'
    });

    $(document).on("click", ".popover-footer .btn-close", function () {
        $(this).parents(".popover").popover('hide');
    });

    $(document).on("click", ".popover-footer .btn-delete", function () {
        var itemId = $("#itemId")[0].value;
        location.href = "/item/" + itemId;
    });
});