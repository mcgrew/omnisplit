
$(function() {
  splits = new Splits({name: "Test splits"}, 'splits');
  for (var i=1; i <= 10; i++) {
    splits.add(new Split({name: "Split " + i, 
                          time: i * 30000, 
                          bestTime: i * 30000}));
  }
  $(document).keydown(function(event) {
    e = event;
    console.log('Keypress: ' + event.keyCode);
    switch(event.keyCode) {
      case 32: // space
        if (splits.stopped())
          splits.resume();
        else
          splits.next();
        break;
      case 8: // backspace
        if (splits.stopped())
          splits.reset();
        else
          splits.stop();
        break;
      case 33: // pgup
        splits.unsplit();
        break;
      case 34: //pgdown
        splits.skip();
        break;
      case 79: // "o"
        if (event.ctrlKey)
          SplitFile.open()
        break;
      default:
        break;
    }
  })
});
 

// test code
FileUtil.fs.readFile(app.getPath('home')+'/projects/omnisplit/tests/he-man.json',
                     'utf-8', function(err, content) {
  splitFile = new SplitFile(content);
});

