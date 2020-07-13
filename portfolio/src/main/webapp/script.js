const line= document.querySelector('.line');
let start = Date.now();
let timer = setInterval(function() 
{
    let timePassed = Date.now() - start;
    if (timePassed >= 2000)
    {
        clearInterval(timer);
        return;
    }
    drawline(timePassed);
}, 20);

function drawline(timePassed) 
{
    line.style.left = timePassed / 5 + 'px';
}
