document.addEventListener("DOMContentLoaded", function () {
    fetch("/TwilioSMSClient/UserSessionServlet")
            .then(response => response.json())
            .then(data => {
                if (data.username) {
                    document.getElementById("usernameDisplay").textContent =
                            data.username.charAt(0).toUpperCase() + data.username.slice(1);
                } else {
                    window.location.href = "login.html"; // Redirect if not logged in
                }
            })
            .catch(error => console.error("Error fetching username:", error));
});