function deleteSMS(button) {
    const row = button.parentNode.parentNode;
    row.parentNode.removeChild(row);
}

function searchSMS() {
    const fromValue = document.getElementById('searchFrom').value.toLowerCase();
    const toValue = document.getElementById('searchTo').value.toLowerCase();
    const startDateValue = document.getElementById('startDate').value;
    const endDateValue = document.getElementById('endDate').value;

    const rows = document.querySelectorAll('#smsTable tbody tr');
    rows.forEach(row => {
        const fromCell = row.cells[0].textContent.toLowerCase();
        const toCell = row.cells[1].textContent.toLowerCase();
        const dateCell = row.cells[3].textContent;

        const matchesFrom = fromValue === '' || fromCell.includes(fromValue);
        const matchesTo = toValue === '' || toCell.includes(toValue);
        const matchesDate = (startDateValue === '' || dateCell >= startDateValue) && (endDateValue === '' || dateCell <= endDateValue);

        if (matchesFrom && matchesTo && matchesDate) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    });
}