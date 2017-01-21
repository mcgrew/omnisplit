Class = require('simple-class').Class
app = require('electron').remote.app;

FileUtil = {
  fs: require('fs'),
  open: require('choose-file'),
  save: require('save-as').saveAs,

  settingsFile: app.getPath('home') + '/.config/omnisplit.conf'
}

Settings = Class.extend({
  init: function() {
    FileUtil.fs.readFile(FileUtil.settingsFile, 'utf-8', function(err, content) {
      if (err) {
        console.error(err);
        this.content = {}
      }
      this.content = JSON.parse(content);
    }.bind(this));
  }
})

SplitFile = Class.extend({
  init: function(content) {
    this.content = JSON.parse(content);
  },
  toSplits: function() {
  }
})

SplitFile.open = function() {
  FileUtil.open(function(file) {
    FileUtil.fs.readFile(file.files[0].path, 'utf-8', function(err, content) {
      splitFile = new SplitFile(content);
    });
  });
}


