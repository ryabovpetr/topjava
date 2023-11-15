const mealsAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealsAjaxUrl,
    updateTable: function () {
        // $.ajax({
        //     type: "GET",
        //     url: mealsAjaxUrl + "filter",
        //     data: $('#filterForm').serialize(),
        // }).done(updateTableByData);
        $.get(mealsAjaxUrl + "filter", $('#filterForm').serialize(), updateTableByData);
    },
};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
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
        })
    );
});

function clearFilter() {
    $('#filterForm')[0].reset();
    ctx.updateTable();
}