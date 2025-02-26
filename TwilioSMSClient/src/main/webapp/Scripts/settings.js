document.addEventListener("DOMContentLoaded", function () {
    fetch("/TwilioSMSClient/SettingsServlet")
        .then(response => {
            if (!response.ok) {
                throw new Error("Unauthorized access or server error.");
            }
            return response.json();
        })
        .then(user => {
            document.getElementById("id").value = user.id;
            document.getElementById("name").value = user.name;
            document.getElementById("username").value = user.username;
            document.getElementById("email").value = user.email;
            document.getElementById("job").value = user.job;
            document.getElementById("phone").value = user.phoneNumber;
            document.getElementById("twilio").value = user.accountSid;
        })
        .catch(error => {
            console.error("Error fetching user data:", error);
            document.getElementById("alertBox").innerHTML =
                `<div class="alert alert-error">${error.message}</div>`;
        });
});

function enableEditing() {
    document.querySelectorAll("input:not([type=hidden])").forEach(input => input.removeAttribute("readonly"));
    document.getElementById("saveButton").style.display = "inline";
}
