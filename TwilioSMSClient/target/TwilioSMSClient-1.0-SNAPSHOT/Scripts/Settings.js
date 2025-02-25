let actionType = ""; // Track if it's for viewing Twilio token or editing

function requestPassword(type) {
    actionType = type; // Save action type ("view" or "edit")
    document.getElementById("passwordModal").style.display = "flex";
}

function validatePassword() {
    let password = document.getElementById("passwordInput").value;

    if (password === "19012001") { // Replace with actual password check
        if (actionType === "view") {
            fetch('/api/settings')
                .then(response => response.json())
                .then(data => {
                    document.getElementById("twilioToken").value = data.twilioToken;
                    document.getElementById("twilioToken").type = "text";
                });
        } else if (actionType === "edit") {
            document.querySelectorAll("input:not(#twilioToken)").forEach(input => input.removeAttribute("readonly"));
        }

        closeModal();
    } else {
        alert("Incorrect password!");
    }
}

function closeModal() {
    document.getElementById("passwordModal").style.display = "none";
    document.getElementById("passwordInput").value = ""; // Clear input
}
