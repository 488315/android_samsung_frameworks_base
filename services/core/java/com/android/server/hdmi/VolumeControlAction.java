package com.android.server.hdmi;

/* loaded from: classes2.dex */
public final class VolumeControlAction extends HdmiCecFeatureAction {
  public final int mAvrAddress;
  public boolean mIsVolumeUp;
  public boolean mLastAvrMute;
  public int mLastAvrVolume;
  public long mLastKeyUpdateTime;
  public boolean mSentKeyPressed;

  public static int scaleToCecVolume(int i, int i2) {
    return (i * 100) / i2;
  }

  public static int scaleToCustomVolume(int i, int i2) {
    return (i * i2) / 100;
  }

  public VolumeControlAction(HdmiCecLocalDevice hdmiCecLocalDevice, int i, boolean z) {
    super(hdmiCecLocalDevice);
    this.mAvrAddress = i;
    this.mIsVolumeUp = z;
    this.mLastAvrVolume = -1;
    this.mLastAvrMute = false;
    this.mSentKeyPressed = false;
    updateLastKeyUpdateTime();
  }

  public final void updateLastKeyUpdateTime() {
    this.mLastKeyUpdateTime = System.currentTimeMillis();
  }

  @Override // com.android.server.hdmi.HdmiCecFeatureAction
  public boolean start() {
    this.mState = 1;
    sendVolumeKeyPressed();
    resetTimer();
    return true;
  }

  public final void sendVolumeKeyPressed() {
    sendCommand(
        HdmiCecMessageBuilder.buildUserControlPressed(
            getSourceAddress(), this.mAvrAddress, this.mIsVolumeUp ? 65 : 66));
    this.mSentKeyPressed = true;
  }

  public final void resetTimer() {
    this.mActionTimer.clearTimerMessage();
    addTimer(1, 300);
  }

  public void handleVolumeChange(boolean z) {
    boolean z2 = this.mIsVolumeUp;
    if (z2 != z) {
      HdmiLogger.debug(
          "Volume Key Status Changed[old:%b new:%b]", Boolean.valueOf(z2), Boolean.valueOf(z));
      sendVolumeKeyReleased();
      this.mIsVolumeUp = z;
      sendVolumeKeyPressed();
      resetTimer();
    }
    updateLastKeyUpdateTime();
  }

  public final void sendVolumeKeyReleased() {
    sendCommand(
        HdmiCecMessageBuilder.buildUserControlReleased(getSourceAddress(), this.mAvrAddress));
    this.mSentKeyPressed = false;
  }

  @Override // com.android.server.hdmi.HdmiCecFeatureAction
  public boolean processCommand(HdmiCecMessage hdmiCecMessage) {
    if (this.mState != 1 || hdmiCecMessage.getSource() != this.mAvrAddress) {
      return false;
    }
    int opcode = hdmiCecMessage.getOpcode();
    if (opcode == 0) {
      return handleFeatureAbort(hdmiCecMessage);
    }
    if (opcode != 122) {
      return false;
    }
    return handleReportAudioStatus(hdmiCecMessage);
  }

  public final boolean handleReportAudioStatus(HdmiCecMessage hdmiCecMessage) {
    boolean isAudioStatusMute = HdmiUtils.isAudioStatusMute(hdmiCecMessage);
    int audioStatusVolume = HdmiUtils.getAudioStatusVolume(hdmiCecMessage);
    this.mLastAvrVolume = audioStatusVolume;
    this.mLastAvrMute = isAudioStatusMute;
    if (!shouldUpdateAudioVolume(isAudioStatusMute)) {
      return true;
    }
    HdmiLogger.debug(
        "Force volume change[mute:%b, volume=%d]",
        Boolean.valueOf(isAudioStatusMute), Integer.valueOf(audioStatusVolume));
    m27tv().setAudioStatus(isAudioStatusMute, audioStatusVolume);
    this.mLastAvrVolume = -1;
    this.mLastAvrMute = false;
    return true;
  }

  public final boolean shouldUpdateAudioVolume(boolean z) {
    if (z) {
      return true;
    }
    AudioManagerWrapper audioManager = m27tv().getService().getAudioManager();
    int streamVolume = audioManager.getStreamVolume(3);
    return this.mIsVolumeUp
        ? streamVolume == audioManager.getStreamMaxVolume(3)
        : streamVolume == 0;
  }

  public final boolean handleFeatureAbort(HdmiCecMessage hdmiCecMessage) {
    if ((hdmiCecMessage.getParams()[0] & 255) != 68) {
      return false;
    }
    finish();
    return true;
  }

  @Override // com.android.server.hdmi.HdmiCecFeatureAction
  public void clear() {
    super.clear();
    if (this.mSentKeyPressed) {
      sendVolumeKeyReleased();
    }
    if (this.mLastAvrVolume != -1) {
      m27tv().setAudioStatus(this.mLastAvrMute, this.mLastAvrVolume);
      this.mLastAvrVolume = -1;
      this.mLastAvrMute = false;
    }
  }

  @Override // com.android.server.hdmi.HdmiCecFeatureAction
  public void handleTimerEvent(int i) {
    if (i != 1) {
      return;
    }
    if (System.currentTimeMillis() - this.mLastKeyUpdateTime >= 300) {
      finish();
    } else {
      sendVolumeKeyPressed();
      resetTimer();
    }
  }
}
