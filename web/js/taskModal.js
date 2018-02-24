var modal = {
    init: function () {
        this.modal = document.getElementById('myModal');
        this.id = document.getElementById('id');
        this.text = document.getElementById('task-text');
        this.date = document.getElementById('date');
        this.done = document.getElementById('done-check');
        this.bin = document.getElementById('delete-check');
    },
    openModal:function (day) {
        var now = new Date();
        now = new Date(now.getFullYear(), now.getMonth(), now.getDate());
        switch (day){
            case 'tomorrow':
                now = new Date(now.valueOf() + 86400000);
                break;
            case 'someday':
                now = new Date(now.valueOf() + 2*86400000);
                break;
        }
        var year = now.getFullYear();
        var month = now.getMonth()+1;
        month = month<10?'0'+month:month;
        var date = now.getDate();
        this.modal.style.display = "block";
        this.date.setAttribute("value", year+'-'+month+'-'+date);
        this.text.focus();
    },
    openEditModal:function (id) {
        var jsonTask = document.getElementById('task-'+id).value;
        var task = JSON.parse(jsonTask);
        modal.id.value = task.id;
        modal.text.value = task.text;
        modal.bin.checked = task.bin;
        modal.done.checked = task.done;
        modal.date.value = task.date;
        this.modal.style.display = "block";
        this.text.focus();
    },
    closeModal: function () {
        this.id.value = 0;
        this.text.value = '';
        this.done.checked = false;
        this.bin.checked = false;
        this.modal.style.display = "none";
    },
    saveTask: function () {
        if (isEmptyField(this.text.value)){
            return false;
        }
        var task = {};
        task.id = modal.id.value;
        task.text = modal.text.value;
        task.date = modal.date.value;
        task.done = modal.done.checked;
        task.bin = modal.bin.checked;
        this.closeModal();
        window.requests.saveTask(task);
        return false;
    }
};