window.fileModal = {
    init: function () {
        this.modal = document.getElementById('myFileModal');
        this.taskId = 0;
        this.fileChooser = document.getElementById('choose-file');
        this.fileChooser.onchange = function () {
            if (fileModal.fileChooser.files[0]){
                fileModal.upload(fileModal.fileChooser.files[0]);
                fileModal.fileChooser.value = '';
            }
        };
        this.fileProgress = document.getElementById('upload-progress');
        this.fileBar = document.getElementById('file-bar');
        this.modal = document.getElementById('myFileModal');
    },
    openModal:function (id) {
        var jsonTask = document.getElementById('task-'+id).value;
        var task = JSON.parse(jsonTask);
        if (task.filename){
            this.toFileBar();
            document.getElementById('file-name').innerHTML = task.filename;
        } else {
            this.toFileChooser();
        }
        this.taskId = task.id;
        this.modal.style.display = 'block';
    },
    closeModal: function () {
        this.taskId = 0;
        this.modal.style.display = 'none';
        this.closeAll();
    },
    toFileChooser:function(){
        this.fileChooser.style.display = 'block';
    },
    toFileProgress:function(){
        this.fileProgress.style.display = 'block';
    },
    toFileBar:function(){
        this.fileBar.style.display = 'block';
    },
    closeAll:function () {
        this.fileChooser.style.display = 'none';
        this.fileProgress.style.display = 'none';
        this.fileBar.style.display = 'none'; 
    },
    upload: function (file) {
        var formData = new FormData();
        formData.append('fileId', this.taskId);
        formData.append('file', file);
        var xhr = new XMLHttpRequest();
        xhr.upload.addEventListener('progress', uploadProgress, false);
        xhr.addEventListener('load', uploadComplete, false);
        xhr.open('POST', '/uploadFile', true);
        xhr.send(formData);
        this.filename = file.name;
        function uploadProgress(event) {
            var progress = Math.round(event.loaded / event.total * 100);
            fileModal.closeAll();
            fileModal.toFileProgress();
            document.getElementById('progress-percents').innerHTML = progress+'';
        }

        function uploadComplete(event) {
            requests.updatePage();
            document.getElementById('file-name').innerHTML = fileModal.filename;
            fileModal.closeAll();
            fileModal.toFileBar();
        }
    },
    downloadFile: function () {
        post('getFile',{'fileId':fileModal.taskId});
    },
    deleteFile: function () {
        var xhr = getXMLHttpRequest();
        var body = 'fileId=' + encodeURIComponent(fileModal.taskId);
        xhr.open("POST", '/deleteFile', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function() {
            if (xhr.readyState !== 4) return;
            if (xhr.status === 200) {
                requests.updatePage();
                fileModal.closeAll();
                fileModal.toFileChooser();
            }
        };
        xhr.send(body);
    }
};