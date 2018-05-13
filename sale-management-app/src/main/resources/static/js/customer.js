$(document).ready(function () {
    $(document).on("click", ".select-customer", function () {
        var value = this.value;
        var that = $("#customer-" + value).closest("tr");
        var fullName = that.find("td").eq("2").text();
        var statusValue;
        switch (that.find("td").eq(7).text()) {
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
        $("#customerId")[0].value = that.find("td").eq(1).text();
        $("#firstName")[0].value = fullName.split(" ")[fullName.split(" ").length - 1];
        $("#lastName")[0].value = fullName.split(" ")[0];
        $("#address")[0].value = that.find("td").eq(3).text();
        $("#email")[0].value = that.find("td").eq(4).text();
        $("#phone")[0].value = that.find("td").eq(5).text();
        $("#fax")[0].value = that.find("td").eq(6).text();
        $("#status option[value='" + statusValue + "']").attr("selected", "selected");
        $("#description")[0].value = that.find("input").eq(1).val();
    });

    $("#customerId").on("change", function () {
        getCustomerInfo($(this));
    });

    $("#basic-addon1").on("click", function () {
        $("#customerId").removeAttr("readonly");
    });

    $("#customer-search").on("click", function () {
        var inputElement = $(".input-form input");
        var valueArr = inputElement.map(function (index, input) {
            return input.value;
        });
        location.href  = "/customer/search?customerId=" + valueArr[0] + "&firstName=" + valueArr[1] + "&lastName="
            + valueArr[2] + "&address=" + valueArr[3] + "&email=" + valueArr[4] + "&phone=" + valueArr[5] +
            "&fax=" + valueArr[6] + "&status=" + $("#status")[0].value;
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
        var customerId = $("#customerId")[0].value;
        location.href = "/customer/" + customerId;
    });
});