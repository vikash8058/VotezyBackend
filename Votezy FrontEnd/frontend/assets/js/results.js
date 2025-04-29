// 1. Load elections into dropdown on page load
document.addEventListener("DOMContentLoaded", function () {
  loadElectionDropdown();
});

function loadElectionDropdown() {
  fetch("http://localhost:8080/api/electionResult/get")
    .then((response) => response.json())
    .then((data) => {
      const dropdown = document.getElementById("electionName"); // Important: ID must match
      data.forEach((election) => {
        const option = document.createElement("option");
        option.value = election.electionName;
        option.text = election.electionName;
        dropdown.appendChild(option);
      });
    })
    .catch((error) => console.error("Error loading elections:", error));
}

// 2. Handle Search (Submit button click)
document
  .getElementById("searchResultForm")
  .addEventListener("submit", function (event) {
    event.preventDefault();

    const electionName = document.getElementById("electionName").value;
    const requestData = { electionName: electionName };

    // Make API request
    fetch(`http://localhost:8080/api/electionResult/declare`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(requestData),
    })
      .then(async (res) => {
        const data = await res.json();

        if (!res.ok) {
          throw new Error(data.messageString || "Something went wrong");
        }

        // If the API call is successful, show result card
        let resultHTML = `
        <div class="card text-center shadow-lg p-4" style="width: 400px;">
          <h4 class="text-danger">${data.electionName}</h4>
          <p><strong>Total Votes:</strong> ${data.totalVotes}</p>
          <p><strong>WinnerId:</strong> ${data.winnerId}</p>
          <p><strong>Votes Obtained:</strong> ${data.winnerVotes}</p>
        </div>
      `;
        document.getElementById("resultsContainer").innerHTML = resultHTML;
      })
      .catch((error) => {
        console.error("Error:", error.message);

        Swal.fire({
          title: "Error",
          text: error.message || "Election result not found.",
          icon: "error",
          confirmButtonText: "OK",
        });
      });
  });
