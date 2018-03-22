var taskLists = {
    init: function () {
        this.today =  document.getElementById('today');
        this.tomorrow = document.getElementById('tomorrow');
        this.someday = document.getElementById('someday');
        this.done = document.getElementById('done-list');
        this.bin = document.getElementById('bin-list');
    },
    clearLists: function () {
        for (var taskList in this) {
            this[taskList].innerHTML = '';
        }
    }
};

window.requests = {
    performRequest: function (action, arguments, func) {
        var xhr = getXMLHttpRequest();
        var body = 'action=' + encodeURIComponent(action) + '&'+arguments;
        xhr.open("POST", '/request', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function() {
            if (xhr.readyState !== 4) return;
            if (xhr.status === 200) {
                func(xhr.responseText);
            }
        };
        xhr.send(body);
    },
    updatePage: function () {
        var updFunc = function (json) {
            taskLists.clearLists();
            var now = new Date();
            var today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            var tomorrow = new Date(today.valueOf() + 86400000);
            var afterTomorrow = new Date(tomorrow.valueOf() + 86400000);

            var tasks = JSON.parse(json);
            tasks.forEach(function (task, i, tasks) {
                var column;
                var actionIcon;
                var actionMethod;
                if (task.bin === true) {
                    column = 'bin';
                    actionIcon = 'bin';
                    actionMethod = 'deleteTask';
                } else if (task.done === true) {
                    column = 'done';
                    actionIcon = 'bin';
                    actionMethod = 'binTask';
                } else {
                    actionIcon = 'tick';
                    actionMethod = 'doneTask';
                    var date = new Date(task.date);
                    if (date < tomorrow) {
                        column = 'today';
                    } else if (date < afterTomorrow) {
                        column = 'tomorrow';
                    } else {
                        column = 'someday';
                    }
                }

                var itemDiv = document.createElement('div');
                itemDiv.setAttribute('class', 'task-item');

                var linkA = document.createElement('a');
                linkA.setAttribute('href', 'JavaScript:modal.openEditModal('+task.id+');');

                var textP = document.createElement('p');
                textP.setAttribute('class', 'task-item-text');
                textP.innerHTML = task.text;

                var jsonTaskStorage = document.createElement('input');
                jsonTaskStorage.setAttribute('id','task-'+task.id);
                jsonTaskStorage.setAttribute('type','text');
                jsonTaskStorage.setAttribute('hidden','');
                jsonTaskStorage.setAttribute('value', JSON.stringify(task));

                var fileA = document.createElement('a');
                fileA.setAttribute('class', 'file-open');
                fileA.setAttribute('href', 'JavaScript:fileModal.openModal('+task.id+');');

                var fileImg = document.createElement('img');
                fileImg.setAttribute('src', 'icon/file.png');

                var tickA = document.createElement('a');
                tickA.setAttribute('class', 'ticker');
                tickA.setAttribute('href', 'JavaScript:requests.'+actionMethod+'('+task.id+');');

                var img = document.createElement('img');
                img.setAttribute('src', 'icon/' + actionIcon + '.png');

                fileA.appendChild(fileImg);
                tickA.appendChild(img);
                linkA.appendChild(textP);
                linkA.appendChild(fileA);
                linkA.appendChild(tickA);
                linkA.appendChild(jsonTaskStorage);
                itemDiv.appendChild(linkA);
                taskLists[column].appendChild(itemDiv);
            });
        };
        this.performRequest('get_tasks', '', updFunc);
    },
    saveTask: function (task) {
        var afterParty = function () {
            requests.updatePage();
        };
        var taskJson = JSON.stringify(task);
        var args = '&taskJson=' + encodeURIComponent(taskJson);
        this.performRequest('save_task', args, afterParty);
    },
    doneTask: function (taskId) {
        var afterParty = function () {
            requests.updatePage();
        };
        var args = '&taskId=' + encodeURIComponent(taskId);
        this.performRequest('done_task', args, afterParty);
    },
    binTask: function (taskId) {
        var afterParty = function () {
            requests.updatePage();
        };
        var args = '&taskId=' + encodeURIComponent(taskId);
        this.performRequest('bin_task', args, afterParty);
    },
    deleteTask: function (taskId) {
        var afterParty = function () {
            requests.updatePage();
        };
        var args = '&taskId=' + encodeURIComponent(taskId);
        this.performRequest('delete_task', args, afterParty);
    },
    emptyBin: function () {
        var afterParty = function () {
            requests.updatePage();
        };
        this.performRequest('empty_bin', '', afterParty);
    }
};

var tabs = {
    currentTabNum: 1,
    tabList: document.getElementsByClassName('tab'),
    iconList: [document.getElementsByClassName('hamb'),
        document.getElementsByClassName('done'),
        document.getElementsByClassName('bin')
    ],

    goToTab: function(toTabNum) {
        requests.updatePage();

        this.iconList[this.currentTabNum-1][0].classList.add("is-active");
        this.iconList[this.currentTabNum-1][1].classList.remove("is-active");

        this.iconList[toTabNum-1][0].classList.remove("is-active");
        this.iconList[toTabNum-1][1].classList.add("is-active");

        this.tabList[this.currentTabNum-1].classList.remove("is-active");
        this.tabList[toTabNum-1].classList.add("is-active");

        this.currentTabNum = toTabNum;

    }
};

window.initialize = function () {
    taskLists.init();
    requests.updatePage();
    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target === modal.modal) {
            modal.closeModal();
        }
        if (event.target === fileModal.modal) {
            fileModal.closeModal();
        }
    };
};