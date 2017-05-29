"use strict";
var uploadObj = (function() {
	var self = null;
	var cls = function() {
            this.uploadFile = $('#upload-file');
			this.uploadToServer = $('#upload-to-server');
			this.fileUploadForm = $('#file-upload-form');
			this.transcribedText = $('#transcribed-text');
	};

	cls.prototype = {
		initialize : function () {
			self = this;
            self.uploadToServer.bind({
                click : self.sendFilesToServer
            });
		},

        sendFilesToServer : function() {
            //Need to make an ajax call here.
            console.log(self.uploadFile[0].files);

			// Create a formdata object and add the files
			var data = new FormData(self.fileUploadForm[0]);

			console.log(data);

			$.ajax({
					url: "/TestApp/service/uploadFiles",
					data : data,
					type : 'POST',
					enctype: 'multipart/form-data',
					cache : false,
					processData: false, // Don't process the files
					contentType: false,
					success: function(data) {
						try {
							alert("File Upload successful!");
							self.uploadFile.val(''); // Clears the existing file queue.
							console.log(data);
							
							// Think of another way for this
							self.transcribedText.empty();
							
							data.forEach(function(element, index){
								var div = document.createElement('div');
								var divEnd = document.createElement('div');
								var txt = document.createTextNode(element);
								var txtEnd = document.createTextNode("--------File Transcription Ends here--------");
								div.innerText = txt.textContent;
								divEnd.innerText = txtEnd.textContent;
								self.transcribedText[0].appendChild(div);
								self.transcribedText[0].appendChild(divEnd);
							});
							
						}
						catch (err) {
							alert("Something went wrong!");
						}
					},
					error : function() {
						alert("Something went wrong!");
					}
			});
        },
	};
	return cls;
})();

var uploadObjInstance = new uploadObj();
uploadObjInstance.initialize();
