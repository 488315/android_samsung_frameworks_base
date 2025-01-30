package com.android.server.hdmi;

import android.hardware.hdmi.IHdmiControlCallback;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Pair;
import android.util.Slog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class HdmiCecFeatureAction {
  public ActionTimer mActionTimer;
  public final List mCallbacks;
  public ArrayList mOnFinishedCallbacks;
  public final HdmiControlService mService;
  public final HdmiCecLocalDevice mSource;
  public int mState;

  public interface ActionTimer {
    void clearTimerMessage();

    void sendTimerMessage(int i, long j);
  }

  public abstract void handleTimerEvent(int i);

  public abstract boolean processCommand(HdmiCecMessage hdmiCecMessage);

  public abstract boolean start();

  public HdmiCecFeatureAction(HdmiCecLocalDevice hdmiCecLocalDevice) {
    this(hdmiCecLocalDevice, new ArrayList());
  }

  public HdmiCecFeatureAction(
      HdmiCecLocalDevice hdmiCecLocalDevice, IHdmiControlCallback iHdmiControlCallback) {
    this(hdmiCecLocalDevice, Arrays.asList(iHdmiControlCallback));
  }

  public HdmiCecFeatureAction(HdmiCecLocalDevice hdmiCecLocalDevice, List list) {
    this.mState = 0;
    this.mCallbacks = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      addCallback((IHdmiControlCallback) it.next());
    }
    this.mSource = hdmiCecLocalDevice;
    HdmiControlService service = hdmiCecLocalDevice.getService();
    this.mService = service;
    this.mActionTimer = createActionTimer(service.getServiceLooper());
  }

  public void setActionTimer(ActionTimer actionTimer) {
    this.mActionTimer = actionTimer;
  }

  public class ActionTimerHandler extends Handler implements ActionTimer {
    public ActionTimerHandler(Looper looper) {
      super(looper);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction.ActionTimer
    public void sendTimerMessage(int i, long j) {
      sendMessageDelayed(obtainMessage(100, i, 0), j);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction.ActionTimer
    public void clearTimerMessage() {
      removeMessages(100);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      if (message.what == 100) {
        HdmiCecFeatureAction.this.handleTimerEvent(message.arg1);
        return;
      }
      Slog.w("HdmiCecFeatureAction", "Unsupported message:" + message.what);
    }
  }

  public final ActionTimer createActionTimer(Looper looper) {
    return new ActionTimerHandler(looper);
  }

  public void addTimer(int i, int i2) {
    this.mActionTimer.sendTimerMessage(i, i2);
  }

  public boolean started() {
    return this.mState != 0;
  }

  public final void sendCommand(HdmiCecMessage hdmiCecMessage) {
    this.mService.sendCecCommand(hdmiCecMessage);
  }

  public final void sendCommand(
      HdmiCecMessage hdmiCecMessage, HdmiControlService.SendMessageCallback sendMessageCallback) {
    this.mService.sendCecCommand(hdmiCecMessage, sendMessageCallback);
  }

  public final void addAndStartAction(HdmiCecFeatureAction hdmiCecFeatureAction) {
    this.mSource.addAndStartAction(hdmiCecFeatureAction);
  }

  public final List getActions(Class cls) {
    return this.mSource.getActions(cls);
  }

  public final HdmiCecMessageCache getCecMessageCache() {
    return this.mSource.getCecMessageCache();
  }

  public final void removeAction(HdmiCecFeatureAction hdmiCecFeatureAction) {
    this.mSource.removeAction(hdmiCecFeatureAction);
  }

  public final void removeAction(Class cls) {
    this.mSource.removeActionExcept(cls, null);
  }

  public final void removeActionExcept(Class cls, HdmiCecFeatureAction hdmiCecFeatureAction) {
    this.mSource.removeActionExcept(cls, hdmiCecFeatureAction);
  }

  public final void pollDevices(
      HdmiControlService.DevicePollingCallback devicePollingCallback, int i, int i2) {
    this.mService.pollDevices(devicePollingCallback, getSourceAddress(), i, i2);
  }

  public void clear() {
    this.mState = 0;
    this.mActionTimer.clearTimerMessage();
  }

  public void finish() {
    finish(true);
  }

  public void finish(boolean z) {
    clear();
    if (z) {
      removeAction(this);
    }
    ArrayList arrayList = this.mOnFinishedCallbacks;
    if (arrayList != null) {
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        Pair pair = (Pair) it.next();
        if (((HdmiCecFeatureAction) pair.first).mState != 0) {
          ((Runnable) pair.second).run();
        }
      }
      this.mOnFinishedCallbacks = null;
    }
  }

  public final HdmiCecLocalDevice localDevice() {
    return this.mSource;
  }

  public final HdmiCecLocalDevicePlayback playback() {
    return (HdmiCecLocalDevicePlayback) this.mSource;
  }

  public final HdmiCecLocalDeviceSource source() {
    return (HdmiCecLocalDeviceSource) this.mSource;
  }

  /* renamed from: tv */
  public final HdmiCecLocalDeviceTv m27tv() {
    return (HdmiCecLocalDeviceTv) this.mSource;
  }

  public final HdmiCecLocalDeviceAudioSystem audioSystem() {
    return (HdmiCecLocalDeviceAudioSystem) this.mSource;
  }

  public final int getSourceAddress() {
    return this.mSource.getDeviceInfo().getLogicalAddress();
  }

  public final int getSourcePath() {
    return this.mSource.getDeviceInfo().getPhysicalAddress();
  }

  public final void sendUserControlPressedAndReleased(int i, int i2) {
    this.mSource.sendUserControlPressedAndReleased(i, i2);
  }

  public final void addOnFinishedCallback(
      HdmiCecFeatureAction hdmiCecFeatureAction, Runnable runnable) {
    if (this.mOnFinishedCallbacks == null) {
      this.mOnFinishedCallbacks = new ArrayList();
    }
    this.mOnFinishedCallbacks.add(Pair.create(hdmiCecFeatureAction, runnable));
  }

  public void finishWithCallback(int i) {
    invokeCallback(i);
    finish();
  }

  public void addCallback(IHdmiControlCallback iHdmiControlCallback) {
    this.mCallbacks.add(iHdmiControlCallback);
  }

  public final void invokeCallback(int i) {
    try {
      for (IHdmiControlCallback iHdmiControlCallback : this.mCallbacks) {
        if (iHdmiControlCallback != null) {
          iHdmiControlCallback.onComplete(i);
        }
      }
    } catch (RemoteException e) {
      Slog.e("HdmiCecFeatureAction", "Callback failed:" + e);
    }
  }
}
