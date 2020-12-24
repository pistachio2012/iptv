var MediaPlayer = function(){ 
  this.getNativePlayerInstanceID = function(){ 
   return MediaPlayerImpl.getNativePlayerInstanceID(); 
  }; 
  this.setVideoDisplayMode = function(mode){ 
   return MediaPlayerImpl.setVideoDisplayMode(mode); 
  }; 
  this.setNativeUIFlag = function(mode){ 
   return MediaPlayerImpl.setNativeUIFlag(mode); 
  }; 
  this.setVolume = function(mode){ 
   return MediaPlayerImpl.setVolume(mode); 
  }; 
  this.setMuteUIFlag = function(mode){ 
   return MediaPlayerImpl.setMuteUIFlag(mode); 
  }; 
  this.setAudioVolumeUIFlag = function(mode){ 
   return MediaPlayerImpl.setAudioVolumeUIFlag(mode); 
  }; 
  this.setAudioTrackUIFlag = function(mode){ 
   return MediaPlayerImpl.setAudioTrackUIFlag(mode); 
  }; 
  this.setProgressBarUIFlag = function(mode){ 
   return MediaPlayerImpl.setProgressBarUIFlag(mode); 
  }; 
  this.setChannelNoUIFlag = function(mode){ 
   return MediaPlayerImpl.setChannelNoUIFlag(mode); 
  }; 
  this.setMuteFlag = function(mode){ 
   return MediaPlayerImpl.setMuteFlag(mode); 
  }; 
  this.setAllowTrickmodeFlag = function(mode){ 
   return MediaPlayerImpl.setAllowTrickmodeFlag(mode); 
  }; 
  this.setVideoDisplayArea = function(x, y, length, heigh){ 
    if(typeof x == 'string')  {x = parseInt(x)  }   
    if(typeof y == 'string')  {y = parseInt(y)  }   
    if(typeof length == 'string')  {length = parseInt(length)  }   
    if(typeof heigh == 'string')  {heigh= parseInt(heigh)  }   
   return MediaPlayerImpl.setVideoDisplayArea(x, y, length, heigh); 
  }; 
  this.setCycleFlag = function(x){ 
   return MediaPlayerImpl.setCycleFlag(x); 
  }; 
  this.refreshVideoDisplay = function(){ 
   return MediaPlayerImpl.refreshVideoDisplay(); 
  }; 
  this.joinChannel = function(mode){ 
   return MediaPlayerImpl.joinChannel(mode); 
  }; 
  this.bindNativePlayerInstance = function(mode){ 
   return MediaPlayerImpl.bindNativePlayerInstance(mode); 
  }; 
  this.leaveChannel = function(){ 
   return MediaPlayerImpl.leaveChannel(); 
  }; 
  this.getVolume = function(){ 
   return MediaPlayerImpl.getVolume(); 
  }; 
  this.gotoEnd = function(){ 
   return MediaPlayerImpl.gotoEnd(); 
  }; 
  this.gotoStart = function(){ 
   return MediaPlayerImpl.gotoStart(); 
  }; 
  this.stop = function(){ 
   return MediaPlayerImpl.stop(); 
  }; 
  this.setRandomFlag = function(x){ 
   return MediaPlayerImpl.setRandomFlag(x); 
  }; 
  this.getChannelNum = function(){ 
   return MediaPlayerImpl.getChannelNum(); 
  }; 
  this.initMediaPlayer = function( instanceId, playListFlag, videoDisplayMode, height, width, left, top, muteFlag, useNativeUIFlag, subtitleFlag, videoAlpha, cycleFlag, randomFlag, autoDelFlag){ 
   return MediaPlayerImpl.initMediaPlayer(instanceId, playListFlag, videoDisplayMode, height, width, left, top, muteFlag, useNativeUIFlag, subtitleFlag, videoAlpha, cycleFlag, randomFlag, autoDelFlag); 
  }; 
  this.initMediaBasePlayer = function( instanceId, playListFlag, videoDisplayMode, height, width, left, top, muteFlag, useNativeUIFlag, subtitleFlag, videoAlpha, cycleFlag, randomFlag, autoDelFlag){ 
   return MediaPlayerImpl.initMediaPlayer(instanceId, playListFlag, videoDisplayMode, height, width, left, top, muteFlag, useNativeUIFlag, subtitleFlag, videoAlpha, cycleFlag, randomFlag, autoDelFlag); 
  }; 
  this.setSingleMedia = function(mediaStr){ 
   return MediaPlayerImpl.setSingleMedia(mediaStr); 
  }; 
  this.playFromStart = function(){ 
   return MediaPlayerImpl.playFromStart(); 
  }; 
  this.getNativeUIFlag = function(){ 
   return MediaPlayerImpl.getNativeUIFlag(); 
  }; 
  this.getChannelNoUIFlag = function(){ 
   return MediaPlayerImpl.getChannelNoUIFlag(); 
  }; 
  this.playByTime = function(type,timestamp,speed){ 
   return MediaPlayerImpl.playByTime(type,timestamp,speed); 
  }; 
  this.resume = function(){ 
   return MediaPlayerImpl.resume(); 
  }; 
  this.pause = function(){ 
   return MediaPlayerImpl.pause(); 
  }; 
  this.getCurrentPlayTime = function(){ 
   return MediaPlayerImpl.getCurrentPlayTime(); 
  }; 
  this.fastForward = function(speed){ 
   return MediaPlayerImpl.fastForward(speed); 
  }; 
  this.fastRewind = function(speed){ 
   return MediaPlayerImpl.fastRewind(speed); 
  }; 
  this.setSingleOrPlaylistMode = function(mode){ 
   return MediaPlayerImpl.setSingleOrPlaylistMode(mode); 
  }; 
  this.switchAudioChannel = function(){ 
   return MediaPlayerImpl.switchAudioChannel(); 
  }; 
  this.getMediaDuration = function(){ 
   return MediaPlayerImpl.getMediaDuration(); 
  }; 
  this.getMuteUIFlag = function(){ 
   return MediaPlayerImpl.getMuteUIFlag(); 
  }; 
  this.getPlaybackMode = function(){ 
   return MediaPlayerImpl.getPlaybackMode(); 
  }; 
  this.getMuteFlag = function(){ 
   return MediaPlayerImpl.getMuteFlag(); 
  }; 
  this.getCurrentAudioChannel = function(){ 
   return MediaPlayerImpl.getCurrentAudioChannel(); 
  }; 
  this.sendVendorSpecificCommand = function(mode){ 
   return MediaPlayerImpl.sendVendorSpecificCommand(mode); 
  }; 
  this.releaseMediaPlayer = function(mode){ 
   return MediaPlayerImpl.releaseMediaPlayer(mode); 
  }; 
  this.setVideoAlpha = function(mode){ 
   return false; 
  }; 
  this.set = function(){ 
   return false; 
  }; 
}
