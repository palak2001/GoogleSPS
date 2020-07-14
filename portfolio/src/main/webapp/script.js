function displayData()
{
    console.log("data fetched");
    fetch('/data').then(response => response.text()).then(data =>{
        document.getElementById('data-container').innerText = data;
    console.log("data fetched successfully");
    });
}





// const line= document.querySelector('.line');
// let start = Date.now();
// let timer = setInterval(function() 
// {
//     let timePassed = Date.now() - start;
//     if (timePassed >= 2000)
//     {
//         clearInterval(timer);
//         return;
//     }
//     drawline(timePassed);
// }, 20);

// function drawline(timePassed) 
// {
//     line.style.left = timePassed / 5 + 'px';
// }
