function writeComment(){
    let contents = $('#comment_write').val();
    let user_id = $("#user_id").text()
    let id = $('#board-id').text()
    let data = {"user_id":user_id,"board_id" : id, "comments" : contents}
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
function readComment(id){
    $('#comment_read').empty()
    $.ajax({
        type: "GET",
        url: '/api/comment',
        success : function (response){
            console.log(response)
            for (let i =0 ; i < response.length; i++){
                let comment = response[i].comment
                let username = response[i].user.username
                let id = response[i].id
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
                <td id="${id}-username">${username}</td>
                <td id="${id}-comment">${comment}</td>
            </tr>
            <tr id="${id}-editcommentarea">
                <td colspan="3"><textarea id="${id}-commenttextarea" class="to-edit" name="" id="" cols="30" rows="5"></textarea></td>
            </tr>
            </tbody>
        </table>
        <div class="footer">
            <img id="${id}-commentedit" class="icon-start-edit" src="/images/edit.png" alt="" onclick="editComment('${id}')">
            <img id="${id}-commentdelete" class="icon-delete" src="/images/delete.png" alt="" onclick="deleteComment('${id}')">
            <img id="${id}-commentsubmit" class="icon-end-edit" src="/images/done.png" alt="" onclick="submitComment('${id}')">
        </div>
            `
                $('#comment_read').append(temp_html)
            }
        }
    })
    $(`#${id}-editcommentarea`).hide();
}
function editComment(id){
    showComment(id);
    let contents = $(`#${id}-comment`).text().trim();
    $(`#${id}-commenttextarea`).val(contents);
}

function showComment(id){
    $(`#${id}-editcommentarea`).show();
    $(`#${id}-commentsubmit`).show();
    $(`#${id}-commentdelete`).show();

    $(`#${id}-comment`).hide();
    $(`#${id}-commentedit`).hide();
}

function submitComment(id){
    let name = $(`#${id}-username`).text().trim();
    let contents = $(`#${id}-commenttextarea`).val().trim();
    let data = {'username':name, 'comments':contents};
    console.log(data)
    $.ajax({
        type : "PUT",
        url : `/api/comment/${id}`,
        contentType : "application/json",
        data : JSON.stringify(data),
        success: function(response){
            alert("메세지 변경에 성공하셨습니다.");
            window.location.reload();
        },
        error: function (response){
            alert("다른 사용자 댓글을 수정할 수 없습니다.")
        }
    })
}

function deleteComment(id){
    confirm("정말로 삭제하시겠습니까?");
    $.ajax({
        type : "DELETE",
        url : `/api/comment/${id}`,
        success : function(response){
            alert("메세지 삭제 성공하였습니다.");
            window.location.reload();
        },
        error: function (response){
            alert("다른 사용자 댓글 삭제 하실 수 없습니다.")
        }
    })
}