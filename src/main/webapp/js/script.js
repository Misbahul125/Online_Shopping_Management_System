function changeText(event) {
    const readMoreBtn = event.target;
    
    const targetId = event.target.getAttribute("data-target-id");
    
    if (readMoreBtn.innerText === 'Read More') {
        $(`[data-id=${targetId}]`).addClass('show-more');
        readMoreBtn.innerText = 'Read Less';
    } else {
        $(`[data-id=${targetId}]`).removeClass('show-more');
        readMoreBtn.innerText = 'Read More';
    }
}