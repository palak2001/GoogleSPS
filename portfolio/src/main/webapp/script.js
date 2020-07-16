function displayComments()
{
    fetch('/loginStats').then(response => response.json()).then(loginStats =>
    {
        if(loginStats[0]=="1")
        {
            fetch('/data').then(response => response.json()).then(comments =>{
                const commentsListElements = document.getElementById('all-comments');
                commentsListElements.innerHTML = '';
                for(var key in comments)
                {
                    commentsListElements.appendChild(createListElement(comments[key]));
                }
            });
        }
        else
        {
            const loginHere = document.getElementById('login-here');
            loginHere.innerHTML = '';
            for(var key in loginStats)
            {
                loginHere.appendChild(createListElement(comments[key]));
            }
        }
    });
}

function createListElement(text) {
    const liElement = document.createElement('li');
    liElement.innerText = text;
    return liElement;
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
