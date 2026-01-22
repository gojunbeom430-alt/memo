$(document).ready(function () {
    getMessages();
});

function getMemosByKeyword() {
    let keyword = $('#search-keyword').val();
    if (keyword == '') {
        alert('검색어를 입력해주세요');
        return;
    }
    $('#cards-box').empty(); // 기존 목록 지우기

    $.ajax({
        type: 'GET',
        url: `/api/memos/contents?keyword=${keyword}`,
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let message = response[i];
                addHTML(message.id, message.username, message.contents, message.modifiedAt);
            }
        }
    });
}

function getMessages() {
    $('#cards-box').empty();
    $.ajax({
        type: 'GET',
        url: '/api/memos',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let message = response[i];
                addHTML(message.id, message.username, message.contents, message.modifiedAt);
            }
        }
    });
}

function addHTML(id, username, contents, modifiedAt) {
    let tempHtml = `<div class="card">
        <div class="metadata">
            <div class="date">${modifiedAt}</div>
            <div id="${id}-username" class="username">${username}</div>
        </div>
        <div class="contents">
            <div id="${id}-contents" class="text">${contents}</div>
            <div id="${id}-editarea" class="edit">
                <textarea id="${id}-textarea" class="te-edit" cols="30" rows="5"></textarea>
            </div>
        </div>
        <div class="footer">
            <img id="${id}-edit" class="icon-start-edit" src="images/edit.png" onclick="editPost('${id}')">
            <img id="${id}-delete" class="icon-delete" src="images/delete.png" onclick="deleteOne('${id}')">
            <img id="${id}-submit" class="icon-end-edit" src="images/done.png" onclick="submitEdit('${id}')">
        </div>
    </div>`;
    $('#cards-box').append(tempHtml);
}

function writePost() {
    let contents = $('#contents').val();
    if (!isValidContents(contents)) return;
    let username = genRandomName(10);
    let data = {'username': username, 'contents': contents};

    $.ajax({
        type: "POST",
        url: "/api/memos",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert('성공적으로 작성되었습니다.');
            window.location.reload();
        }
    });
}

function submitEdit(id) {
    let username = $(`#${id}-username`).text().trim();
    let contents = $(`#${id}-textarea`).val().trim();
    if (!isValidContents(contents)) return;
    let data = {'username': username, 'contents': contents};

    $.ajax({
        type: "PUT",
        url: `/api/memos/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert('변경에 성공하였습니다.');
            window.location.reload();
        }
    });
}

function deleteOne(id) {
    $.ajax({
        type: "DELETE",
        url: `/api/memos/${id}`,
        success: function (response) {
            alert('삭제에 성공하였습니다.');
            window.location.reload();
        }
    })
}

function isValidContents(contents) {
    if (contents == '') {
        alert('내용을 입력해주세요');
        return false;
    }
    if (contents.trim().length > 140) {
        alert('140자 이하로 입력해주세요');
        return false;
    }
    return true;
}

function genRandomName(length) {
    let result = '';
    let characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    for (let i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return result;
}

function editPost(id) {
    $(`#${id}-editarea`).show();
    $(`#${id}-submit`).show();
    $(`#${id}-contents`).hide();
    $(`#${id}-edit`).hide();
    let contents = $(`#${id}-contents`).text().trim();
    $(`#${id}-textarea`).val(contents);
}