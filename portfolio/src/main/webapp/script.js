function displayComments()
{
    console.log(document.getElementsByName('add-comment'));
    fetch('/loginStats').then(response => response.json()).then(loginStats =>
    {
        if(loginStats.LoggedIn=="1")
        {
            fetch('/data')
            .then(response => response.json())
            .then(comments =>
            {
                const mainDiv = document.getElementById('comments-div');

                for(var i=0;i<comments.length;i=i+1)
                {
                    const commentDiv = document.createElement('ul');
                    const comment = document.createTextNode(comments[i].comment);
                    commentDiv.append(comment);
                    commentDiv.append("- ");
                    const email = document.createElement('strong');
                    email.append(comments[i].email);
                    commentDiv.append(email);
                    mainDiv.append(commentDiv);
                }
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