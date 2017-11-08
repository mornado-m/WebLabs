function SetTable(Text) {
    document.getElementsByTagName("input")[1].value = Text;
    document.getElementsByTagName("input")[3].value = 0;
    document.forms[0].submit();
}

function DeleteRow(idx) {
    if (!confirm("Delete this row?")) return;
    document.getElementsByTagName("input")[1].value =
        document.getElementById("table_name").innerText;
    document.getElementsByTagName("input")[2].value = idx;
    document.getElementsByTagName("input")[3].value = 0;
    document.forms[0].submit();
}

function DeleteAllRows() {
    if (!confirm("Delete all rows?")) return;
    document.getElementsByTagName("input")[1].value =
        document.getElementById("table_name").innerText;
    document.getElementsByTagName("input")[3].value = 1;
    document.forms[0].submit();
}