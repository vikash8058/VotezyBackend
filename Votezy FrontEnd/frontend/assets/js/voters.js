document.addEventListener("DOMContentLoaded", () => {
  loadVoters();
});

// Load all voters
function loadVoters() {
  fetch("http://localhost:8080/api/voters/get")
    .then((res) => res.json())
    .then((data) => {
      const votersContainer = document.getElementById("votersContainer");
      votersContainer.innerHTML = "";
      data.forEach((voter) => {
        votersContainer.innerHTML += `
          <tr>
            <td>${voter.id}</td>
            <td>${voter.name}</td>
            <td>${voter.email}</td>
            <td>
              <button class="btn btn-danger btn-sm" onclick="deleteVoter(${voter.id})">Delete</button> 
              <button class="btn btn-primary btn-sm" onclick="editVoter(${voter.id})">Edit</button>
            </td>
          </tr>`;
      });
    })
    .catch((error) => {
      console.error("Error loading voters:", error);
      showAlert("Error", "Failed to load voters.", "error");
    });
}
//add Voters


// Edit voter
function editVoter(voterId) {
  Swal.fire({
    title: "Edit Voter Details",
    html: `<input type="text" id="newName" class="swal2-input" placeholder="Enter new name">
           <input type="text" id="newEmail" class="swal2-input" placeholder="Enter new email">`,
    showCancelButton: true,
    confirmButtonText: "Update",
    preConfirm: () => {
      let newName = document.getElementById("newName").value;
      let newEmail = document.getElementById("newEmail").value;

      if (!newName && !newEmail) {
        Swal.showValidationMessage("Atleast one field required!");
        return false;
      }

      return { newName, newEmail };
    },
  }).then((result) => {
    if (result.isConfirmed) {
      updateVoter(voterId, result.value.newName, result.value.newEmail);
    }
  });
}

// Update voter
function updateVoter(voterId, newName, newEmail) {
  const voterData = {

  };

  if (newName) voterData.name = newName;
  if (newEmail) voterData.email = newEmail;


  fetch(`http://localhost:8080/api/voters/update/${voterId}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(voterData),
  })
    .then(() => {
      showToast("Voter updated successfully!", "success");
      loadVoters();
    })
    .catch(() => showToast("Failed to update voter.", "error"));
}

// Delete voter
function deleteVoter(id) {
  Swal.fire({
    title: "Are you sure?",
    text: "This voter will be permanently deleted.",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#d9534f",
    cancelButtonColor: "#6c757d",
    confirmButtonText: "Yes, delete it!",
  }).then((result) => {
    if (result.isConfirmed) {
      fetch(`http://localhost:8080/api/voters/delete/${id}`, {
        method: "DELETE",
      })
        .then(() => {
          showAlert("Deleted!", "Voter has been removed.", "success");
          loadVoters();
        })
        .catch(() => showAlert("Error", "Failed to delete voter.", "error"));
    }
  });
}

// Add voter
document.getElementById("voterForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const name = document.getElementById("name").value;
  const email = document.getElementById("email").value;

  fetch("http://localhost:8080/api/voters/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      name: name,
      email: email,
    }),
  })
    .then((res) => {
      if (!res.ok) {
        return res.json().then((error) => {
        
          throw new Error(error.message || `Voter with email "${email}" is already registered.`);
        });
      }
      return res.json();
    })
    .then(() => {
      showToast("Voter registered successfully!", "success");
      document.getElementById("voterForm").reset();
      loadVoters(); // Refresh the table
    })
    .catch((err) => {
      showToast(err.message, "error");
      console.error(err);
    });
});
// Show alert using SweetAlert
function showAlert(title, text, icon) {
  Swal.fire({
    title,
    text,
    icon,
    timer: 2000,
    showConfirmButton: false,
  });
}
// Show toast notification
function showToast(message, type = "info") {
  Swal.fire({
    toast: true,
    position: "top-end",
    icon: type,
    title: message,
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
  });
}
// Show toast using SweetAlert
// function showToast(message, type) {
//   Swal.fire({
//     position: "top-end",
//     icon: type,
//     title: message,
//     showConfirmButton: false,
//     timer: 3000,
//   });
// }
