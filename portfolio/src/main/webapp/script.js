function displayComments()
{
    fetch('/loginStats').then(response => response.json()).then(loginStats =>
    {
        if(loginStats.LoggedIn=="1")
        {
            fetch('/data')
            .then(response => response.json())
            .then(comments =>
            {
                const commentsListElements = document.getElementById('comments-here');
                //const imagesListElements = document.getElementById('images-here');
                const emailListElements = document.getElementById('email-here');
                commentsListElements.innerHTML = '';
                emailListElements.innerHTML = '';
                for(var key in comments)
                {
                    // console.log(comments[key].comment);
                    // console.log(comments[key].email);
                     commentsListElements.appendChild(createListElement(comments[key].comment));
                     emailListElements.appendChild(createListElement(comments[key].email));
                    if(comments[key].image!==null){
                    imagesListElements.getElementById('images-here').setAttribute("src",comments[key].image);                    
                }}
                // const commentForm = document.getElementById('comment-form');
                // commentForm.action = imageUploadUrl;
                // commentForm.classList.remove('hidden');
            });
            console.log(loginStats.URL);
            document.getElementById('login-logout-here').innerHTML="Logout";
            document.getElementById('login-logout-here').setAttribute("href",loginStats.URL);
            
        }
        else
        {
            document.getElementById('login-logout-here').innerHTML="Login";
            document.getElementById('login-logout-here').setAttribute("href",loginStats.URL);
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
