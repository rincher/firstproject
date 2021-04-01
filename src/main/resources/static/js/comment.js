function writeComment(){
    let contents = $('#comment_write').val();
    console.log(contents)
    let data = {"comments" : contents}
    console.log(data)
    $.ajax({
        type : "POST",
        url : "/api/comment",
        data : JSON.stringify(data),
        contentType : "application/json",
        success : function (response){
            alert("댓글을 작성완료 하였습니다.")
            window.location.reload()
        }
    })
}
function readComment(){
    $('#comment_read').empty()
    $.ajax({
        type: "GET",
        url: '/api/comment',
        success : function (response){
            for (let i =0 ; i < response.length; i++){
                let comment = response[i].comment
                let username = response[i].user.username
                let temp_html = `<table border="1">
            <colgroup>
                <col width="100px">
            </colgroup>
            <thead >
            <tr>
                <th>닉네임</th>
                <th>댓글</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${username}</td>
                <td>${comment}</td>
            </tr>
            </tbody>
        </table>`
                $('#comment_read').append(temp_html)
            }
        }
    })
}