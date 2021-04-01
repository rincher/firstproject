function getMessage() {
    $('#comment').empty();
    $.ajax({
        type: "GET",
        url: "/api/boards",
        success: function (response) {
            console.log(response)
            for (let i = 0; response.length; i++) {
                let board = response[i];
                let id = board.id;
                let title = board.title;
                let name = board.username;
                let modifiedAt = board.modifiedAt;
                let createdAt = board.createdAt;
                addHTML(id, title, name, modifiedAt, createdAt)
            }
        }
    })
}

function addHTML(id, name, title, modifiedAt, createdAt) {
    let tempHtml = `<tr>
                <td><a href="/board/${id}">${id}</a></td>
                <td>${name}</td>
                <td>${title}</td>
                <td>${createdAt}</td>
                <td>${modifiedAt}</td>`;
    $('#comment').append(tempHtml);
}

function getDetail(id) {
    $('#board_detail').empty();
        $.ajax({
            type: "GET",
            url: `/api/boards/${id}`,
            success: function (response) {
                let board = response[0];
                let userid = board.userId;
                let id = board.id;
                let title = board.title;
                let name = board.username;
                let contents = board.contents;
                let modifiedAt = board.modifiedAt;
                let createdAt = board.createdAt;
                addDetail(id, title, name, contents, modifiedAt, createdAt, userid)
            }
        })
}
function addDetail(id, title, name, contents, modifiedAt, createdAt, userid) {
    let temp_html = `<div class="card">
                        <div class="card-body">
                            <section class="article-detail table-common con row">
                                <table class="cell" border="1">
                                    <colgroup>
                                        <col width="100px">
                                    </colgroup>
                                    <tbody>
                                        <tr class="article-title">
                                            <th>id</th>
                                            <td colspan="3" id="board-id">${id}</td>
                                            <th>제목</th>
                                            <td colspan="3" id="${id}-title">${title}</td>
                                            <th>작성자</th>
                                            <td colspan="3" id="${id}-name">${name}</td>
                                        </tr>
                                        <tr class="article-info">
                                            <th>작성일</th>
                                            <td colspan="3">${createdAt}</td>
                                            <th>수정일</th>
                                            <td colspan="3">${modifiedAt}</td>
                                            <td hidden id="user_id">${userid}</td>
                                        </tr>
                                        <tr class="article-body">
                                            <td colspan="3" id="${id}-contents" class="text">${contents}</td>
                                        </tr>
                                        <tr id="${id}-editarea">
                                            <td colspan="3"><textarea id="${id}-textarea" class="to-edit" name="" id="" cols="30" rows="5"></textarea></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </section>
                            <div class="footer">
                                <img id="${id}-edit" class="icon-start-edit" src="/images/edit.png" alt="" onclick="editPost('${id}')">
                                <img id="${id}-delete" class="icon-delete" src="/images/delete.png" alt="" onclick="deleteOne('${id}')">
                                <img id="${id}-submit" class="icon-end-edit" src="/images/done.png" alt="" onclick="submitEdit('${id}')">
                            </div>
                        </div>
                    </div>`
    $('#board_detail').append(temp_html);
    $(`#${id}-editarea`).hide();
}

function writePost() {
    name = $("#username").val();
    console.log(name)
    title = $("#title").val();
    contents = $('#contents').val();
    if (isvalid(contents, title, name) === false) {
        return;
    }
    let data = {"title": title, "username": name, 'contents': contents};
    $.ajax({
        type: "POST",
        url: "/api/boards",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert("메세지가 성공적으로 작성되었습니다.");
            document.location.href = "/";
        }
    });
}

function isvalid(contents, title, name) {
    if (contents === '') {
        alert('내용을 입력해주세요');
        return false;
    }
    if (contents.trim().length > 140) {
        alert("140자 이내로 적어주세요");
        return false;
    }
    if (title === '') {
        alert('제목을 입력해주세요');
        return false;
    }
    if (title.trim().length > 20) {
        alert("20자 이내로 적어주세요");
        return false;
    }
    if (name === '') {
        alert('이름을 입력해주세요');
        return false;
    }
    if (name.trim().length > 10) {
        alert("10자 이내로 적어주세요");
        return false;
    }
    return true;
}

function editPost(id){
    showEdits(id);
    let contents = $(`#${id}-contents`).text().trim();
    $(`#${id}-textarea`).val(contents);
}

function showEdits(id){
    $(`#${id}-editarea`).show();
    $(`#${id}-submit`).show();
    $(`#${id}-delete`).show();

    $(`#${id}-contents`).hide();
    $(`#${id}-edit`).hide();
}

function submitEdit(id){
    let name = $(`#${id}-name`).text().trim();
    let contents = $(`#${id}-textarea`).val().trim();
    let title = $(`#${id}-title`).text().trim();
    if (isvalid(contents, title, name) === false){
        return
    }
    let data = {'name':name, 'contents':contents, 'title':title};
    $.ajax({
        type : "PUT",
        url : `/api/boards/${id}`,
        contentType : "application/json",
        data : JSON.stringify(data),
        success: function(response){
            alert("메세지 변경에 성공하셨습니다.");
            window.location.reload();
        },
        error: function (response){
            alert("다른 사용자 게글을 수정할 수 없습니다.")
        }
    })
}

function deleteOne(id){
    let name = $(`#${id}-name`).text().trim();
    let contents = $(`#${id}-textarea`).val().trim();
    let title = $(`#${id}-title`).text().trim();
    let data = {'name':name, 'contents':contents, 'title':title};
    $.ajax({
        type : "DELETE",
        url : `/api/boards/${id}`,
        data : JSON.stringify(data),
        success : function(response){
            alert("메세지 삭제 성공하였습니다.");
            window.location.reload();
        },
        error: function (response){
            alert("다른 사용자 게시글을 삭제 하실 수 없습니다.")
        }
    })
}