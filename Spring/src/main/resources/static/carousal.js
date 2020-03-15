var curIndex = 0;
update();

function update(){
    var listOfPics = document.getElementsByClassName("slide");

    var i;
    for(i = 0; i < listOfPics.length; i++){
        listOfPics[i].style.display = "none";
    }

//    if the curIndex is greater than the # of terms in the list
//    then start from the beginning
    if(curIndex > listOfPics.length - 1){
        curIndex = 0;
    }

    listOfPics[curIndex].style.display = "inline";
    curIndex = curIndex + 1;
    setTimeout(update, 3000);
}
