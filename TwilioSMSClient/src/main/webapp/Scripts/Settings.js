document.addEventListener("DOMContentLoaded", function () {
    fetch("/TwilioSMSClient/settings")
        .then(response => response.json())
        .then(user => {
            if (!user || Object.keys(user).length === 0) {
                console.warn("No user data found or user is not logged in.");
                return;
            }

            document.getElementById("id").value = user.id || "";
            document.getElementById("name").value = user.name || "";
            document.getElementById("username").value = user.username || "";
            document.getElementById("email").value = user.email || "";
            document.getElementById("job").value = user.job || "";
            document.getElementById("phone").value = user.phoneNumber || "";
            document.getElementById("twilio").value = user.accountSid || "";
            document.getElementById("twilioToken").value = "••••••••"; // Hide token by default

            console.log("User settings loaded successfully.");
        })
        .catch(error => console.error("Error fetching user settings:", error));
});

function enableEditing() {
    document.getElementById("name").removeAttribute("readonly");
    document.getElementById("email").removeAttribute("readonly");
    document.getElementById("job").removeAttribute("readonly");
    document.getElementById("phone").removeAttribute("readonly");

    document.getElementById("saveButton").style.display = "block";
}

function requestPassword() {
    if (!confirm("Do you want to reveal your Twilio Token?")) {
        return;
    }

    fetch("/TwilioSMSClient/revealToken")
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                document.getElementById("twilioToken").value = data.token;
            } else {
                alert("Failed to retrieve token.");
            }
        })
        .catch(error => console.error("Error fetching Twilio token:", error));
}
