const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl,
    updateTable: function () {
        $.get(userAjaxUrl, updateTableByData);
    }
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        }),
    );
});

function enable(chkbox, id) {
    var status = chkbox.is(":checked")
    $.ajax({
        type: "POST",
        url: userAjaxUrl + id,
        data: "enabled=" + status
    }).done(function () {
        chkbox.closest("tr").attr("data-user-enabled", status);
        successNoty(status ? "Profile activated" : "Profile deactivated")
    }).fail(function () {
        $(chkbox).prop("checked", !status)
    });
}
