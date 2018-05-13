$(document).ready(function () {
    var $orderDate = $("#orderDate");
    var $orderNo = $("#orderNo");
    var $customerModal = $("#customer-modal");
    var $salesPersonModal = $("#sales-person-modal");
    var $sodCustomerId = $(".sod-customerId");
    var $sodSalesPersonId = $(".sod-salesPersonId");
    var $status = $("#status option[selected='selected']");
    var $customerModalInput = $("#customer-modal input");
    var $active = $("#status option[value='AV']");
    var $salesPersonModalInput = $("#sales-person-modal input");

    $orderDate.attr("min", new Date().toJSON().split("T")[0]);
    $sodSalesPersonId.on("change", function () {
        getSalesPersonName($(this));
    });

    $sodCustomerId.on("change", function () {
        getCustomerName($(this));
    });

    $(document).on("change", ".sod-itemId", function () {
        getItemInfo($(this));
    });

    $orderNo.on("change", function () {
        getSalesOrderInfo($(this));
    });

    $("#sod-basic-addon").on("click", function () {
        $orderNo.removeAttr("readonly");
    });

    $orderDate.on("change", function () {
        var orderDate = $orderDate[0].value.trim();
        var overdueDate = new Date(orderDate.split("-").join("/"));
        overdueDate.setDate(overdueDate.getDate() + 10);
        $("#overdueDate")[0].value = overdueDate.toISOString().substring(0, 10);
    });

    $(document).on("click", ".btn-remove", function () {
        $(this).closest("tr").remove();
    });

    $(".btn-add").on("click", function () {
        addInput();
    });

    $(document).on("change", ".pay", function () {
        var quantity = parseFloat($(this).closest("tr").find("td:eq(3) input")[0].value);
        var price = parseFloat($(this).closest("tr").find("td:eq(4) input")[0].value);
        var itemDisc = parseFloat($(this).closest("tr").find("td:eq(6) input")[0].value);
        var taxAmt = parseFloat($(this).closest("tr").find("td:eq(7) input")[0].value);
        var salesPrice = (quantity * price) - itemDisc;
        if (salesPrice < 0) {
            salesPrice = 0;
        }
        $(this).closest("tr").find("td:eq(8) input")[0].value = (salesPrice + (salesPrice * taxAmt) / 100).toFixed(2);
        charge();
    });

    $("#btn-charge").on("click", function () {
        changeItemsAttribute();
        charge();
        $(this).popover("show");
    });

    $('[data-toggle="popover"]').popover({
        html: true,
        placement: 'top',
        trigger: 'manual',
        template: '<div class="popover" style="width: 500px">' +
        '<div class="arrow"></div>' +
        '<h3 class="popover-title" style="font-weight:bold; background-color: #add8e6; font-size: 13px;"></h3>' +
        '<div class="popover-content" style="height: 50px; padding-top: 17px; text-align: center; color: red; background-color: #faebd7"></div>' +
        '<div class="popover-footer" style="padding-bottom: 15px; padding-left: 33px; background-color: #faebd7;">' +
        '<button type="submit" class="btn-save btn btn-primary" style="width: 95px; padding: 0; height: 34px" id="save-order">' +
        'Save&nbsp;</button>' + '<button type="button" class="btn btn-default" style="width: 95px; margin-left: 10px">Cancel</button>' + '</div></div>'
    });

    $(document).on("click", ".popover-footer .btn", function () {
        $(this).parents(".popover").popover('hide');
    });

    $("#sales-person-modal button[type=submit]").on("click", function (event) {
        event.preventDefault();
        if (validateSalesPerson()) {
            $.ajax({
                type: "POST",
                url: "/salesperson",
                data: $("form[name=salesPersonForm]").serialize(),
                dataType: "json",
                beforeSend(xhr) {
                    xhr.setRequestHeader("Accept", "application/json");
                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                    $salesPersonModalInput.next().remove();
                },
                success: function (response) {
                    $salesPersonModal.modal("hide");
                    $sodSalesPersonId[0].value = response.salesPersonId;
                    $(".sod-salesPersonName")[0].value = response.lastName + " " + response.firstName;
                    $salesPersonModalInput.each(function (index, input) {
                        input.value = "";
                    });
                    $active.attr("selected", "selected");
                    $sodSalesPersonId.parent().next().text("");
                }, error: function (jqXHR, textStatus, errorThrown) {
                    var responseBody = $.parseJSON(jqXHR.responseText);
                    var fieldErrors = responseBody.fieldErrors.map(function (fieldError) {
                        return fieldError.field;
                    });
                    for (var i = 0; i < fieldErrors.length; i++) {
                        $("#sales-person-modal #" + fieldErrors[i]).parent().append("<span class='error'>" + responseBody.fieldErrors[i].message + "</span>");
                    }
                }
            })
        }
    });

    $salesPersonModal.on("shown.bs.modal", function () {
        getSalesPersonInfo($sodSalesPersonId);
    });

    $salesPersonModal.on("hidden.bs.modal", function () {
        $status.removeAttr("selected");
        $salesPersonModalInput.next().remove();
    });

    $("#customer-modal button[type=submit]").on("click", function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: "/customers",
            data: $("form[name=customerForm]").serialize(),
            dataType: "json",
            beforeSend(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                $customerModalInput.next().remove();
            },
            success: function (response) {
                $customerModal.modal("hide");
                $sodCustomerId[0].value = response.customerId;
                $(".sod-customerName")[0].value = response.lastName + " " + response.firstName;
                $customerModalInput.each(function (index, input) {
                    input.value = "";
                });
                $active.attr("selected", "selected");
                $sodCustomerId.parent().next().text("");
            }, error: function (jqXHR, textStatus, errorThrown) {
                var responseBody = $.parseJSON(jqXHR.responseText);
                var fieldErrors = responseBody.fieldErrors.map(function (fieldError) {
                    return fieldError.field;
                });
                for (var i = 0; i < fieldErrors.length; i++) {
                    $("#customer-modal #" + fieldErrors[i]).parent().append("<span class='error'>" + responseBody.fieldErrors[i].message + "</span>");
                }
            }
        })
    });

    $customerModal.on("shown.bs.modal", function () {
        getCustomerInfo($sodCustomerId);
    });

    $customerModal.on("hidden.bs.modal", function () {
        $status.removeAttr("selected");
        $customerModalInput.next().remove();
    });

    function validateSalesPerson() {
        var $firstName = $("#sales-person-modal #firstName");
        var $lastName = $("#sales-person-modal #lastName");
        var isValidated = true;

        if ($firstName[0] != null && $firstName[0].value.length === 0) {
            $firstName.parent().append("<span class='error'>FirstName must be not blank</span>");
            isValidated = false;
        }
        if ($lastName[0] != null && $lastName[0].value.length === 0) {
            $lastName.parent().append("<span class='error'>LastName must be not blank</span>");
            isValidated = false;
        }
        return isValidated;
    }
});