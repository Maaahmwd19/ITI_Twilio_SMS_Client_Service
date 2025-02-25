document.addEventListener("DOMContentLoaded", function () {
    fetch("/TwilioSMSClient/SMSHistoryServlet")
            .then(response => response.json())
            .then(data => {
                const tableBody = document.querySelector("#smsTable tbody");
                tableBody.innerHTML = "";

                data.forEach(sms => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                    <td>${sms.from}</td>
                    <td>${sms.to}</td>
                    <td>${sms.body}</td>
                    <td>${sms.date}</td>
                    <td class="actions">
                        <button class="delete-btn" onclick="deleteSMS(this)">Delete</button>
                    </td>                `;
                    tableBody.appendChild(row);
                });

                console.log("SMS data loaded successfully.");
            })
            .catch(error => console.error("Error fetching SMS data:", error));
});


function deleteSMS(button) {
    if (!confirm("Are you sure you want to delete this SMS?")) {
        return;
    }

    const row = button.closest("tr");
    if (!row) {
        console.error("Row not found!");
        return;
    }

    // Extract SMS ID from dataset (Make sure your backend sends this ID)
    const smsId = row.dataset.smsId;
    if (!smsId) {
        console.error("SMS ID is missing!");
        return;
    }

    console.log(`Attempting to delete SMS with ID: ${smsId}`);

    fetch(`/TwilioSMSClient/DeleteSMSServlet?smsId=${smsId}`, {
        method: "DELETE",
    })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    console.log("SMS deleted successfully.");
                    row.remove();
                } else {
                    alert("Failed to delete SMS: " + data.error);
                }
            })
            .catch(error => console.error("Error deleting SMS:", error));
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
