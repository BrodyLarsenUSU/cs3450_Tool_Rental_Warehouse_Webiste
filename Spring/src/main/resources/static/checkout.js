var modal = document.getElementById("checkoutModal");

var checkoutButton = document.getElementById("checkoutSubmitButton");

var closeModal = document.getElementById("modalCloseButton");

checkoutButton.onclick = function(){
    modal.style.display="block";
}

closeModal.onclick = function(){
    modal.style.display="none";
}
