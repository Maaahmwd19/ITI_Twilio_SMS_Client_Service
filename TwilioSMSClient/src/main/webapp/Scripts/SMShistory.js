function deleteSMS(button) {
    if (!confirm("Are you sure you want to delete this SMS?")) return;

    // Get the SMS ID from the row's data attribute
    const smsId = button.closest("tr").dataset.smsId;
    console.log("Deleting SMS ID:", smsId);

    fetch("/TwilioSMSClient/DeleteSMSServlet", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "id=" + encodeURIComponent(smsId),
    })
    .then(response => response.text())
    .then(data => {
        console.log("Server Response:", data);
        if (data.includes("SMS Deleted Successfully")) {
            button.closest("tr").remove(); // Remove the row from the table
        } else {
            alert("Error: " + data);
        }
    })
    .catch(error => console.error("Error:", error));
}



function searchSMS() {
    const fromValue = document.getElementById("searchFrom").value.trim().toLowerCase();
    const toValue = document.getElementById("searchTo").value.trim().toLowerCase();
    const startDateValue = document.getElementById("startDate").value;
    const endDateValue = document.getElementById("endDate").value;

    console.log("Search Filters:", {fromValue, toValue, startDateValue, endDateValue});

    const rows = document.querySelectorAll("#smsTable tbody tr");
    if (rows.length === 0) {
        console.warn("No SMS data found in the table!");
        return;
    }

    rows.forEach(row => {
        const fromCell = row.cells[0].textContent.trim().toLowerCase();
        const toCell = row.cells[1].textContent.trim().toLowerCase();
        const dateCell = row.cells[3].textContent.trim();

        // Convert "DD/MM/YYYY" or "YYYY-MM-DD" to "YYYY-MM-DD"
        let rowDate = dateCell;
        if (dateCell.includes("/")) {
            const parts = dateCell.split("/");
            rowDate = `${parts[2]}-${parts[1]}-${parts[0]}`;
        }

        console.log("Checking row:", {fromCell, toCell, rowDate});

        const matchesFrom = !fromValue || fromCell.includes(fromValue);
        const matchesTo = !toValue || toCell.includes(toValue);
        const matchesDate = (!startDateValue || rowDate >= startDateValue) &&
                (!endDateValue || rowDate <= endDateValue);

        row.style.display = (matchesFrom && matchesTo && matchesDate) ? "" : "none";
    });
}
