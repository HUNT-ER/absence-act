"use strict"

let listUpdate;

function stopUpdating() {
    clearInterval(listUpdate);
}

function startUpdating() {
    listUpdate = setInterval(getActList, 3000);
}


function clearData() {
    const dataList = $("#data");
    dataList.empty();
}

startUpdating(listUpdate);

function showUnderheader() {
    const dataList = $("#data");
    dataList.empty();

    dataList.append('<h2 align="center">Список записей об отсутствии работника</h2>');
    dataList.append(`
        <div style="display:flex; flex-direction: row; justify-content: center; align-items: center">
            <button onclick="create()">Создать новый акт</button><br><br>

        </div>
    `)
}

function showActList(data) {
    const dataList = $("#data");
    dataList.empty();

    showUnderheader();

    data.forEach(act => {
        const listItem = $("<li>");
        listItem.html(`
                <p>Идентификатор: ${act.id}</p>
                <p>Причина отсутствия: ${act.reason}</p>
                <p>Дата начала: ${act.start_date}</p>
                <p>Продолжительность: ${act.duration} дней</p>
                <p>Учтено при оплате: ${act.discounted ? "Да" : "Нет"}</p>
                <p>Комментарий: ${act.description}</p>
                <hr>
                <div style="display:flex; flex-direction: row; justify-content: center; align-items: center">
                    <button onclick="deleteById(${act.id})">Удалить</button>
                    <button onclick="editById(${act.id})">Изменить</button>
                </div>
            `
        );
        dataList.append(listItem);
    });
}

function getActList() {
    $.ajax({
        url: "http://localhost:8080/api/absence-acts",
        method: "GET",
        dataType: "json",
        success: function (data) {
            showActList(data.body);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showUnderheader();
            console.log("Произошла ошибка при запросе GET: ", errorThrown);
        }
    });
}

function deleteById(id) {
    let del = confirm("Вы действительно хотите удалить запись " + id + "?");
    if (del) {
        $.ajax({
            url: "http://localhost:8080/api/absence-acts/" + id,
            method: "DELETE",
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("Произошла ошибка при запросе DELETE: ", errorThrown);
            }
        });
    }
}

function findById(id) {
    return $.ajax({
        url: "http://localhost:8080/api/absence-acts/" + id,
        method: "GET",
        dataType: "json",
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Произошла ошибка при запросе GET: ", errorThrown);
        }
    });
}

function updateAct(act) {
    let id = act.id;
    delete act.id;
    console.log(JSON.stringify(act));
    $.ajax({
        url: "http://localhost:8080/api/absence-acts/" + id,
        contentType: 'application/json',
        method: "PUT",
        dataType: "json",
        data: JSON.stringify(act),
        error: function (jqXHR, textStatus, errorThrown) {
            alert("Ошибка, поля заполнены не правильно")
            editById(id)
            console.log("Произошла ошибка при запросе PUT: ", errorThrown);
        }
    });
}

function createAct(act) {
    delete act.id;
    $.ajax({
        url: "http://localhost:8080/api/absence-acts",
        contentType: 'application/json',
        method: "POST",
        dataType: "json",
        data: JSON.stringify(act),
        error: function (jqXHR, textStatus, errorThrown) {
            alert("Ошибка, поля заполнены не правильно");
            create(act);
            console.log("Произошла ошибка при запросе POST: ", errorThrown);
        }
    });
}

function editById(id) {

    let promise = findById(id);

    promise.then(data => showAndSaveAct(data.body, "Изменение записи: " + data.body.id, "editForm"), error => comsole.log(error));
}

function create(act) {

    if (act == null) {
        act = {
            "reason": null,
            "duration": null,
            "description": "",
            "start_date": "",
            "discounted": false
        }
    }

    act.id = "Новая запись";

    showAndSaveAct(act, "Создание новой записи", "createForm");
}

function cancelSaving() {
    if (confirm("Отменить сохранение?")) {
        clearData();
        startUpdating();
    }
}

function showAndSaveAct(act, header, formId) {
    clearData();
    stopUpdating();

    const dataList = $("#data");
    dataList.append(`<h2 align="center">${header}</h2>`);

    dataList.append(`
        <form id=${formId}>
            <div style="display: flex; flex-direction: column; align-items: flex-start;">
                
                <label for="id">Идентификатор: ${act.id}</label><br>

                <label for="reason">Причина отсутствия:</label>
                <select id="reason">
                    <option value="Отпуск">Отпуск</option>
                    <option value="Больничный">Больничный</option>
                    <option value="Прогул">Прогул</option>
                </select><br>

                <label for="startDate">Дата начала:</label>
                <input type="date" id="startDate" name="start_date" value=${act.start_date}>
                <br>

                <label for="duration">Продолжительность:</label>
                <input type="number" id="duration" name="duration" value=${act.duration}>
                <br>

                <label for="discounted">Учтено при оплате:</label>
                <input type="checkbox" id="discounted" name="discounted" checked=${act.discounted}>
                <br>

                <label for="description">Комментарий:</label>
                <textarea id="description" name="description" cols="50" rows="8">${act.description}</textarea>
                <br>

                <input type="submit" value="Сохранить">
                <br>
            </div>
            <button onclick="cancelSaving()">Отменить сохранение</button>
        </form>`);

    document.getElementById(formId).addEventListener("submit", (event) => {
        event.preventDefault();

        act.reason = document.getElementById("reason").value;
        act.duration = document.getElementById("duration").value;
        act.description = document.getElementById("description").value;
        act.start_date = document.getElementById("startDate").value;
        act.discounted = document.getElementById("discounted").checked;


        if (formId == "editForm") {
            updateAct(act);
        } else {
            createAct(act);
        }

        clearData();
        startUpdating();
    });
}










