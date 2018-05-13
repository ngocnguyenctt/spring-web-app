function getSalesPersonInfo(_$this) {
    if (_$this[0] != null && _$this[0].value.length !== 0) {
        var salesPersonUrl = "/salesperson/" + _$this[0].value + "/info";
        $.ajax({
            type: "GET",
            url: salesPersonUrl,
            dataType: "json",
            beforeSend(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                $("#salesPersonId").parent().next("span").remove();
                if ($("#sales-person-modal")[0] != null) {
                    $("#sales-person-modal #status").find("option[selected='selected']").removeAttr("selected");
                } else {
                    $("#status").find("option[selected='selected']").removeAttr("selected");
                }
            },
            success: function (response) {
                $("#itemLimit")[0].value = response.itemLimit;
                if ($("#sales-person-modal")[0] != null) {
                    $("#sales-person-modal #firstName")[0].value = response.firstName;
                    $("#sales-person-modal #lastName")[0].value = response.lastName;
                    $("#sales-person-modal #address")[0].value = response.address;
                    $("#sales-person-modal #email")[0].value = response.email;
                    $("#sales-person-modal #phone")[0].value = response.phone;
                    $("#sales-person-modal #description")[0].value = response.description;
                    $("#sales-person-modal #status option[value='" + response.status + "']").attr("selected", "selected");
                } else {
                    $("#salesPersonId")[0].value = response.salesPersonId;
                    $("#lastName")[0].value = response.lastName;
                    $("#firstName")[0].value = response.firstName;
                    $("#address")[0].value = response.address;
                    $("#email")[0].value = response.email;
                    $("#phone")[0].value = response.phone;
                    $("#description")[0].value = response.description;
                    $("#status option[value='" + response.status + "']").attr("selected", "selected");
                }
            }, error: function (jqXHR, textStatus, errorThrown) {
                var responseBody = $.parseJSON(jqXHR.responseText);
                if ($("#sales-person-modal")[0] == null) {
                    $("#salesPersonId").closest("td").append("<span class='error'>" + responseBody.message + "</span>");
                }
                reset("salesPerson", "");
            }
        });
    }
}

function getSalesPersonName(_$this) {
    var url = "/salesperson/" + _$this[0].value;
    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        beforeSend(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            $(".sod-salesPersonId").parent().next().text("");
        },
        success: function (response) {
            $(".sod-salesPersonId")[0].value = response.salesPersonId;
            $(".sod-salesPersonName")[0].value = response.lastName + " " + response.firstName;
        }, error: function (jqXHR, textStatus, errorThrown) {
            var responseBody = $.parseJSON(jqXHR.responseText);
            $(".sod-salesPersonId").parent().next().text(responseBody.message);
            $(".sod-salesPersonName")[0].value = "";
            reset("salesPerson", "");
        }
    });
}

function getCustomerInfo(_$this) {
    if (_$this[0] != null && _$this[0].value.length !== 0) {
        var url = "/customers/" + _$this[0].value + "/info";
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            beforeSend(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                $("#customerId").parent().next("span").remove();
                if ($("#customer-modal")[0] != null) {
                    $("#customer-modal #status").find("option[selected='selected']").removeAttr("selected");
                } else {
                    $("#status").find("option[selected='selected']").removeAttr("selected");
                }
            },
            success: function (response) {
                $("#fax")[0].value = response.fax;
                if ($("#customer-modal")[0] != null) {
                    $("#customer-modal #firstName")[0].value = response.firstName;
                    $("#customer-modal #lastName")[0].value = response.lastName;
                    $("#customer-modal #address")[0].value = response.address;
                    $("#customer-modal #description")[0].value = response.description;
                    $("#customer-modal #status option[value='" + response.status + "']").attr("selected", "selected");
                    $("#customer-modal #email")[0].value = response.email;
                    $("#customer-modal #phone")[0].value = response.phone;
                } else {
                    $("#customerId")[0].value = response.customerId;
                    $("#firstName")[0].value = response.firstName;
                    $("#lastName")[0].value = response.lastName;
                    $("#address")[0].value = response.address;
                    $("#description")[0].value = response.description;
                    $("#status option[value='" + response.status + "']").attr("selected", "selected");
                    $("#email")[0].value = response.email;
                    $("#phone")[0].value = response.phone;
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                var responseBody = $.parseJSON(jqXHR.responseText);
                if ($("#customer-modal")[0] == null) {
                    $("#customerId").closest("td").append("<span class='error'>" + responseBody.message + "</span>");
                }
                reset("customer", "");
            }
        });
    }
}

function getCustomerName(_$this) {
    var url = "/customers/" + _$this[0].value;
    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            $(".sod-customerId").parent().next().text("");
        },
        success: function (response) {
            $(".sod-customerId")[0].value = response.customerId;
            $(".sod-customerName")[0].value = response.lastName + " " + response.firstName;
        }, error: function (jqXHR, textStatus, errorThrown) {
            var responseBody = $.parseJSON(jqXHR.responseText);
            $(".sod-customerName")[0].value = "";
            $(".sod-customerId").parent().next().text(responseBody.message);
            reset("customer", "");
        }
    });
}

function getItemInfo(_$itemId) {
    var itemUrl = "/items/" + _$itemId[0].value + "/info";
    $.ajax({
        type: "GET",
        url: itemUrl,
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            _$itemId.next("span").remove();
        },
        success: function (response) {
            _$itemId.closest("tr").find("td:eq(1) input")[0].value = response.itemId;
            _$itemId.closest("tr").find("td:eq(2) input")[0].value = response.itemName;
            _$itemId.closest("tr").find("td:eq(3) input")[0].value = response.qtyStock;
            _$itemId.closest("tr").find("td:eq(4) input")[0].value = response.price;
            _$itemId.closest("tr").find("td:eq(5) input")[0].value = response.unit;
            _$itemId.closest("tr").find("td:eq(6) input")[0].value = response.itemDisc;
            _$itemId.closest("tr").find("td:eq(7) input")[0].value = response.taxAmt;
            _$itemId.closest("tr").find("td:eq(8) input")[0].value = response.amount;
            charge();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            var responseBody = $.parseJSON(jqXHR.responseText);
            _$itemId.parent().append("<span class='error'>" + responseBody.message + "</span>");
            reset("item", _$itemId);
        }
    });
}

function getSalesOrderInfo(_$orderNo) {
    var salesOrderUrl = "/sales-orders/" + _$orderNo[0].value + "/info";
    $.ajax({
        type: "GET",
        url: salesOrderUrl,
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            $("#orderNo").parent().next("span").remove();
            reset("salesOrder", "");
        },
        success: function (response) {
            // $("#orderNo").attr("disabled", "disabled")
            $("#orderDate")[0].value = response[0].salesOrderDetailId.salesOrder.orderDate;
            $("#orderDate").attr("disabled", "disabled");
            $("#overdueDate")[0].value = response[0].salesOrderDetailId.salesOrder.overdueDate;
            $("#overdueDate").attr("disabled", "disabled");
            $(".sod-salesPersonId")[0].value = response[0].salesOrderDetailId.salesOrder.salesPerson.salesPersonId;
            $(".sod-salesPersonId").attr("disabled", "disabled");
            $(".sod-customerId")[0].value = response[0].salesOrderDetailId.salesOrder.customer.customerId;
            $(".sod-customerId").attr("disabled", "disabled");
            $("#description")[0].value = response[0].salesOrderDetailId.salesOrder.description;
            $("#description").attr("disabled", "disabled");
            $("#orderDisc")[0].value = response[0].salesOrderDetailId.salesOrder.orderDisc;
            $("#totalAmt")[0].value = response[0].salesOrderDetailId.salesOrder.totalAmt;
            $("#taxAmt")[0].value = response[0].salesOrderDetailId.salesOrder.taxAmt;
            $("#payment")[0].value = response[0].salesOrderDetailId.salesOrder.payment.toFixed(2);
            /*	$("#payment").attr("disabled","disabled");*/
            $(".sod-salesPersonName")[0].value = response[0].salesOrderDetailId.salesOrder.salesPerson.lastName
                + " " + response[0].salesOrderDetailId.salesOrder.salesPerson.firstName;
            $(".sod-customerName")[0].value = response[0].salesOrderDetailId.salesOrder.customer.lastName + " "
                + response[0].salesOrderDetailId.salesOrder.customer.firstName;
            $("#overdueDate")[0].value = response[0].salesOrderDetailId.salesOrder.overdueDate;
            for (var i = 0; i < response.length; i++) {
                if (i >= 1) {
                    addInput();
                    changeItemsAttribute();
                }
                $(".sod-itemId-" + i)[0].value = response[i].salesOrderDetailId.item.itemId;
                $(".sod-itemId-" + i).attr("disabled", "disabled");
                $(".sod-itemName-" + i)[0].value = response[i].salesOrderDetailId.item.itemName;
                $(".sod-qty-" + i)[0].value = response[i].qty;
                $(".sod-qty-" + i).attr("disabled", "disabled");
                $(".sod-price-" + i)[0].value = response[i].price;
                $(".sod-price-" + i).attr("disabled", "disabled");
                $(".sod-unit-" + i)[0].value = response[i].salesOrderDetailId.item.unit;
                $(".sod-itemDisc-" + i)[0].value = response[i].itemDisc;
                $(".sod-itemDisc-" + i).attr("disabled", "disabled");
                $(".sod-taxAmt-" + i)[0].value = response[i].taxAmt;
                $(".sod-taxAmt-" + i).attr("disabled", "disabled");
                $(".sod-amount-" + i)[0].value = response[i].amount;
            }
            $("#btn-charge").attr("disabled", "disabled");
            $(".btn-add").remove();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            var responseBody = $.parseJSON(jqXHR.responseText);
            $("#orderNo").closest("td").append("<span class='error'>" + responseBody.message + "</span>");
            reset("salesOrder", "");
        }
    })
}

function addInput() {
    var count = parseInt($("#count")[0].value);
    count += 1;
    $("#count")[0].value = count;

    var html = "<tr><td>";
    html += "<span class='text-center btn-remove glyphicon glyphicon-minus-sign'></span><input type='hidden' value='" + count + "'></td>";
    html += "<td><input type='text' minlength='5' maxlength='20' class='sod-itemId itemId-" + count + " form-control' required></td>";
    html += "<td><input type='text' class='sod-itemName itemName-" + count + " form-control' readonly></td>";
    html += "<td><input type='number' min='0' class='pay sod-qty qty-" + count + " form-control'></td>";
    html += "<td><input type='number' min='0' step='any' class='pay sod-price price-" + count + " form-control'></td>";
    html += "<td><input type='text' class='sod-unit unit-" + count + " form-control' readonly></td>";
    html += "<td><input type='number' min='0' step='any' class='pay sod-itemDisc itemDisc-" + count + " form-control'></td>";
    html += "<td><input type='number' min='0' step='any' class='pay sod-taxAmt taxAmt-" + count + " form-control'></td>";
    html += "<td><input type='number' min='0' step='any' class='sod-amount amount-" + count + " form-control' readonly>";
    html += "</td></tr>";
    $("#input-sales-item").append(html);
}

function changeItemsAttribute() {
    var itemCount = $("#input-sales-item tr").length;
    for (var i = 1; i < itemCount; i++) {
        var index = $("#input-sales-item tr").eq(i).find("input")[0].value;
        $(".itemId-" + index).attr("id", "items[" + i + "].salesOrderDetailId.item.itemId").addClass("sod-itemId-" + i).attr("name", "items[" + i + "].salesOrderDetailId.item.itemId");
        $(".itemName-" + index).attr("id", "items[" + i + "].salesOrderDetailId.item.itemName").addClass("sod-itemName-" + i).attr("name", "items[" + i + "].salesOrderDetailId.item.itemName");
        $(".qty-" + index).attr("id", "items[" + i + "].qty").addClass("sod-qty-" + i).attr("name", "items[" + i + "].qty");
        $(".price-" + index).attr("id", "items[" + i + "].price").addClass("sod-price-" + i).attr("name", "items[" + i + "].price");
        $(".unit-" + index).attr("id", "items[" + i + "].salesOrderDetailId.item.unit").addClass("sod-unit-" + i).attr("name", "items[" + i + "].salesOrderDetailId.item.unit");
        $(".itemDisc-" + index).attr("id", "items[" + i + "].itemDisc").addClass("sod-itemDisc-" + i).attr("name", "items[" + i + "].itemDisc");
        $(".taxAmt-" + index).attr("id", "items[" + i + "].taxAmt").addClass("sod-taxAmt-" + i).attr("name", "items[" + i + "].taxAmt");
        $(".amount-" + index).attr("id", "items[" + i + "].amount").addClass("sod-amount-" + i).attr("name", "items[" + i + "].amount");
    }
}

function charge() {
    var orderDisc = parseFloat("0"), totalAmt = parseFloat("0"), totalTaxAmt = parseFloat("0");
    for (var i = 0; i < $(".sod-itemDisc").length; i++) {
        orderDisc += parseFloat($(".sod-itemDisc")[i].value);
    }
    for (var i = 0; i < $(".sod-taxAmt").length; i++) {
        totalTaxAmt += parseFloat($(".sod-taxAmt")[i].value);
    }
    for (var i = 0; i < $(".sod-amount").length; i++) {
        totalAmt += parseFloat($(".sod-amount")[i].value);
    }
    $("#orderDisc")[0].value = orderDisc.toFixed(2);
    $("#taxAmt")[0].value = totalTaxAmt.toFixed(2);
    $("#totalAmt")[0].value = totalAmt.toFixed(2);
    $("#payment")[0].value = totalAmt.toFixed(2);
}

function reset(form, element) {
    switch (form) {
        case "salesOrder":
            $("#orderDate")[0].value = "";
            $("#orderDate").removeAttr("disabled", "disabled");
            $(".sod-salesPersonId")[0].value = "";
            $(".sod-salesPersonId").removeAttr("disabled", "disabled");
            $(".sod-customerId")[0].value = "";
            $(".sood-customerId").removeAttr("disabled", "disabled");
            $("#description")[0].value = "";
            $("#description").removeAttr("disabled", "disabled");
            $("#orderDisc")[0].value = "";
            $("#totalAmt")[0].value = "";
            $("#taxAmt")[0].value = "";
            $("#payment")[0].value = "";
            $(".sod-salesPersonName")[0].value = "";
            $(".sod-customerName")[0].value = "";
            $("#overdueDate")[0].value = "";
            for (var i = 0; i < $("#input-sales-item tr").length; i++) {
                if (i >= 1) {
                    $("#input-sales-item tr")[i].remove()
                } else {
                    $("#input-sales-item tr span.error").remove();
                    $("#charge tr span.error").remove();
                    $(".sod-itemId-" + i)[0].value = "";
                    $(".sod-itemId-" + i).removeAttr("disabled", "disabled");
                    $(".sod-itemName-" + i)[0].value = "";
                    $(".sod-qty-" + i)[0].value = "";
                    $(".sod-qty-" + i).removeAttr("disabled", "disabled");
                    $(".sod-price-" + i)[0].value = "";
                    $(".sod-price-" + i).removeAttr("disabled", "disabled");
                    $(".sod-unit-" + i)[0].value = "";
                    $(".sod-itemDisc-" + i)[0].value = "";
                    $(".sod-itemDisc-" + i).removeAttr("disabled", "disabled");
                    $(".sod-taxAmt-" + i)[0].value = "";
                    $(".sod-taxAmt-" + i).removeAttr("disabled", "disabled");
                    $(".sod-amount-" + i)[0].value = "";
                }
            }
            break;
        case "salesPerson":
            $("#itemLimit")[0].value = "";
            $("#status option[value='AV']").attr("selected", "selected");
            if ($("#sales-person-modal")[0] != null) {
                $("#sales-person-modal #firstName")[0].value = "";
                $("#sales-person-modal #lastName")[0].value = "";
                $("#sales-person-modal #address")[0].value = "";
                $("#sales-person-modal #email")[0].value = "";
                $("#sales-person-modal #phone")[0].value = "";
                $("#sales-person-modal #description")[0].value = "";
            } else {
                $("#firstName")[0].value = "";
                $("#lastName")[0].value = "";
                $("#address")[0].value = "";
                $("#email")[0].value = "";
                $("#phone")[0].value = "";
                $("#description")[0].value = "";
            }
            break;
        case "item":
            element.closest("tr").find("td:eq(2) input")[0].value = "";
            element.closest("tr").find("td:eq(3) input")[0].value = "";
            element.closest("tr").find("td:eq(4) input")[0].value = "";
            element.closest("tr").find("td:eq(5) input")[0].value = "";
            element.closest("tr").find("td:eq(6) input")[0].value = "";
            element.closest("tr").find("td:eq(7) input")[0].value = "";
            element.closest("tr").find("td:eq(8) input")[0].value = "";
            break;
        case "customer":
            $("#email")[0].value = "";
            $("#phone")[0].value = "";
            $("#fax")[0].value = "";
            $("#status option[value='AV']").attr("selected", "selected");
            if ($("#customer-modal")[0] != null) {
                $("#customer-modal #firstName")[0].value = "";
                $("#customer-modal #lastName")[0].value = "";
                $("#customer-modal #address")[0].value = "";
                $("#customer-modal #description")[0].value = "";
            } else {
                $("#firstName")[0].value = "";
                $("#lastName")[0].value = "";
                $("#address")[0].value = "";
                $("#description")[0].value = "";
            }
            break;
        default:
            break;
    }
}
