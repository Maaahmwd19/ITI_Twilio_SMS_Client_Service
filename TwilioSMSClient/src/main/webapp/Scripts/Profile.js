document.addEventListener("DOMContentLoaded", function () {
    fetch("/TwilioSMSClient/profile")
        .then(response => response.json())
        .then(data => {
            console.log("Profile Data Received:", data);  // Debugging

            if (Object.keys(data).length === 0) {
                console.warn("No profile data found!");
                return;
            }

            document.getElementById("name").value = data.name || "N/A";
            document.getElementById("username").value = data.username || "N/A";
            document.getElementById("email").value = data.email || "N/A";
            document.getElementById("job").value = data.job || "N/A";
            document.getElementById("phone").value = data.phone || "N/A";
            document.getElementById("twilio").value = data.twilio || "N/A";
            document.getElementById("twilioToken").value = data.twilioToken || "N/A";
        })
        .catch(error => console.error("Error fetching profile data:", error));
});
