Class = require('simple-class').Class
Mustache = require('mustache');
$ = require('jquery');
file = require('./file.js')

UIElement = Class.extend({
  init: function(args, parent) {
    this.args = args;
    this.render();
    if (parent)
      this.jquery.appendTo(parent);
    this._bindEvents();
  },
  render: function() {
    this.jquery = $(Mustache.render(this.template, this.args));
    return this;
  },
  _bindEvents: function() {
    for (var i in this) {
      if (i.startsWith('_events_') && typeof this[i] == "function") {
        this.jquery.on(i.subsring(8), this[i].bind(this));
      }
    }
  },
  bind: function(ev, func) {
    this.jquery.on(ev, func.bind(this));
    this['_event_' + ev] = func;
  },
  update: function(args) {
    // TODO: implement this
  },
  destroy: function() {
    this.jquery.remove();
  },
  template: '<div/>'
});

Splits = UIElement.extend({
  init: function(args, parent) {
    this._super(args, parent);
    this.mainTimer = new MainTimer({}, $('footer'));
    this.splits = [];
  },
  start: function() {
    this.startTime = new Date().getTime();
    console.info("Started")
    for (var i=0; i < this.splits.length; i++)
      this.splits[i].startTime(this.startTime);
    this.resume();
    this.activeSplit = 0;
    console.log("Active split: " + this.activeSplit);
    this.splits[this.activeSplit].active(true);
    return this;
  },
  pause: function() {
    clearInterval(this.interval);
    this.updateTime();
    return this;
  },
  resume: function() {
    this.interval = setInterval(this.updateTime.bind(this), 30);
    delete this.stopTime;
    return this;
  },
  stop: function() {
    if (!this.stopTime) {
      clearInterval(this.interval);
      this.stopTime = new Date().getTime();
      this.updateTime();
    }
    return this;
  },
  stopped: function() {
    return !!this.stopTime;
  },
  reset: function() {
    clearInterval(this.interval);
    delete this.stopTime
    delete this.startTime
    this.mainTimer.set(0);
    return this;
  },
  skip: function() {
    if (this.activeSplit < this.splits.length-1) {
      this.splits[this.activeSplit].active(false);
      this.activeSplit++
      console.log("Active split: " + this.activeSplit);
      this.splits[this.activeSplit].active(true);
    }
    return this;
  },
  unsplit: function() {
    if (this.isDone) {
      delete this.isDone;
      this.splits[this.activeSplit].active(true);
      this.resume();
    } else if (this.activeSplit > 0) {
      this.splits[this.activeSplit].active(false);
      this.activeSplit--;
      console.log("Active split: " + this.activeSplit);
      this.splits[this.activeSplit].active(true);
      this.splits[this.activeSplit].done(false);
    }
    return this;
  },
  done: function() {
    this.isDone = true;
    this.splits[this.activeSplit].active(false);
    return this.stop();
  },
  updateTime: function() {
    var time = (this.stopTime || new Date().getTime()) - this.startTime;
    this.splits[this.activeSplit].updateTime(time);
    this.mainTimer.set(time);
    return this;
  },
  add: function(split) {
    this.splits.push(split);
    split.jquery.appendTo(this.jquery);
    return this;
  },
  next: function() {
    if (this.activeSplit === undefined)
      return this.start();
    if (this.activeSplit == this.splits.length - 1)
      return this.done();
    this.splits[this.activeSplit].active(false);
    this.splits[this.activeSplit].done(true);
    this.activeSplit++
    console.log("Active split: " + this.activeSplit);
    this.splits[this.activeSplit].active(true);
    return this;
  },
  template: '<splits>' +
              '<splits-name>{{name}}</splits-name>' +
            '</splits>'
});

Split = UIElement.extend({
  init: function(args) {
    this._super(args);
    if (args.time) {
      var time = Time.format(args.time);
      if (time.hours)
        this.jquery.find('split-time hours').text(time.hours + ':');
      else
        this.jquery.find('split-time hours').text('');
      if (time.minutes)
        this.jquery.find('split-time minutes').text(time.minutes + ':');
      else
        this.jquery.find('split-time minutes').text('');
      this.jquery.find('split-time seconds').text(time.seconds);
      if (!time.hours)
        this.jquery.find('split-time msec').text('.' + time.msec.substring(0,1));
    }
  },
  render: function() {
    this._super();
    this.currentHours = this.jquery.find('current-time hours');
    this.currentMinutes = this.jquery.find('current-time minutes');
    this.currentSeconds = this.jquery.find('current-time seconds');
    this.currentMsec = this.jquery.find('current-time msec');
  },
  active: function(active) {
    if (active === undefined)
      return this.isActive;
    if (active)
      this.jquery.addClass('active');
    else
      this.jquery.removeClass('active');
    this.isActive = active;
    return this;
  },
  startTime: function(time) {
    this.startTime = time;
  },
  updateTime: function(time) {
    this.currentTime = time
    if (this.time - this.args.time > -30000) {
      this.showTime();
    }
  },
  done: function(done) {
    if (done === undefined)
      return this.isDone
    this.isDone = done;
    if (done) {
      this.stopTime = new Date().getTime();
      this.showTime();
    } else {
      delete this.stopTime;
      this.jquery.find('current-time hours').text('')
      this.jquery.find('current-time minutes').text('')
      this.jquery.find('current-time seconds').text('')
      this.jquery.find('current-time msec').text('')
    }
    return this;
  },
  showTime: function() {
    var time = (((this.stopTime || this.currentTime) - this.startTime)
                  - this.args.time);
    time = Time.format(time);
    if (time.hours)
      this.currentHours.text(time.hours + ':');
    else
      this.currentHours.text('');
    if (time.minutes)
      this.currentMinutes.text(time.minutes + ':');
    else
      this.currentMinutes.text('');
    this.currentSeconds.text(time.seconds);
    if (!time.hours)
      this.currentMsec.text('.' + time.msec.substring(0,1));
  },
  template: '<split>'+
              '<split-time>' +
                '<hours></hours>' +
                '<minutes></minutes>' +
                '<seconds></seconds>' +
                '<msec></msec>' +
              '</split-time>' +
              '<current-time>' + 
                '<hours></hours>' +
                '<minutes></minutes>' +
                '<seconds></seconds>' +
                '<msec></msec>' +
              '</current-time>' +
              '<split-name>{{name}}</split-name>' +
            '</split>'
});

MainTimer = UIElement.extend({
  init: function(args, parent) {
    this._super(args, parent);
    this.set(0);
  },
  render: function() {
    this._super();
    this.hours = this.jquery.find('.hours');
    this.minutes = this.jquery.find('minutes');
    this.seconds = this.jquery.find('seconds');
    this.msec = this.jquery.find('msec');
  },
  set: function(time) {
    var time = Time.format(time)
    if (time.hours)
      this.hours.text(time.hours + ':');
    if (time.minutes)
      this.minutes.text(time.minutes + ':');
    this.seconds.text(time.seconds);
    this.msec.text('.' + time.msec.substring(0,2));
  },
  template: '<main-timer>' + 
              '<hours></hours>' +
              '<minutes></minutes>' +
              '<seconds></seconds>' +
              '<msec></msec>' +
            '</main-timer>'
});

FooterEntry = UIElement.extend({
  set: function(time) {
    var time = Time.format(args.time);
    if (time.hours)
      this.jquery.find('time hours').text(time.hours + ':');
    else
      this.jquery.find('time hours').text('');
    if (time.minutes)
      this.jquery.find('time minutes').text(time.minutes + ':');
    else
      this.jquery.find('time minutes').text('');
    this.jquery.find('time seconds').text(time.seconds);
    if (!time.hours)
      this.jquery.find('time msec').text('.' + time.msec.substring(0,1));
  },
  template: '<footer-entry class="{{class}}">' +
              '<title>{{title}}</title>' +
              '<time>' +
                '<hours></hours>' +
                '<minutes></minutes>' +
                '<seconds></seconds>' +
                '<msec></msec>' +
              '</time>' +
            '</footer-entry>'
});

Time = {
  format: function (time) {
    var returnvalue = {}  
    var absTime = Math.abs(time)
    if (absTime >= 3600000) {
      returnvalue.hours = String(Math.floor(absTime / 3600000))
      if (time < 0) returnvalue.hours = '-' + returnvalue.hours;
      returnvalue.minutes = (Math.floor(absTime / 60000) % 60).pad(2);
      returnvalue.seconds = (Math.floor(absTime / 1000) % 60).pad(2);
    } else if (absTime >= 60000) {
      returnvalue.minutes = String(Math.floor(absTime / 60000) % 60);
      if (time < 0) returnvalue.minutes = '-' + returnvalue.minutes;
      returnvalue.seconds = (Math.floor(absTime / 1000) % 60).pad(2);
    } else {
      returnvalue.seconds = String(Math.floor(absTime / 1000) % 60);
      if (time < 0) returnvalue.seconds = '-' + returnvalue.seconds;
    }
    returnvalue.msec = (absTime % 1000).pad(3)
    return returnvalue;
  },
  parse: function(time) {
    if (typeof time == "string") {
      timeParts = time.split(':');
      time = Number(timeParts.pop());
      if (timeParts.length)
        time += Number(timeParts.pop()) * 60;
      if (timeParts.length)
        time += Number(timeParts.pop()) * 3600;
    }
    return time;
  },
  isTime: function(time) {
    return time instanceof Number || 
      /^([+-]?(\d+:){0,2}\d+\.?\d*|\.\d+)$/.test(time)
  }
};

Number.prototype.pad = function(digits) {
  var returnvalue = String(this);
  while (returnvalue.length < digits) {
    returnvalue = "0" + returnvalue;
  }
  return returnvalue;
}

