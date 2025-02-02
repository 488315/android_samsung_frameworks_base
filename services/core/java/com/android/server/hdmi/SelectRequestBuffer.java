package com.android.server.hdmi;

import android.hardware.hdmi.IHdmiControlCallback;
import android.os.RemoteException;
import android.util.Slog;

/* loaded from: classes2.dex */
public class SelectRequestBuffer {
  public static final SelectRequestBuffer EMPTY_BUFFER =
      new SelectRequestBuffer() { // from class: com.android.server.hdmi.SelectRequestBuffer.1
        @Override // com.android.server.hdmi.SelectRequestBuffer
        public void process() {}
      };
  public SelectRequest mRequest;

  public abstract class SelectRequest {
    public final IHdmiControlCallback mCallback;
    public final int mId;
    public final HdmiControlService mService;

    public abstract void process();

    public SelectRequest(
        HdmiControlService hdmiControlService, int i, IHdmiControlCallback iHdmiControlCallback) {
      this.mService = hdmiControlService;
      this.mId = i;
      this.mCallback = iHdmiControlCallback;
    }

    /* renamed from: tv */
    public HdmiCecLocalDeviceTv m31tv() {
      return this.mService.m30tv();
    }

    public HdmiCecLocalDeviceAudioSystem audioSystem() {
      return this.mService.audioSystem();
    }

    public boolean isLocalDeviceReady() {
      if (m31tv() != null) {
        return true;
      }
      Slog.e("SelectRequestBuffer", "Local tv device not available");
      invokeCallback(2);
      return false;
    }

    public final void invokeCallback(int i) {
      try {
        IHdmiControlCallback iHdmiControlCallback = this.mCallback;
        if (iHdmiControlCallback != null) {
          iHdmiControlCallback.onComplete(i);
        }
      } catch (RemoteException e) {
        Slog.e("SelectRequestBuffer", "Invoking callback failed:" + e);
      }
    }
  }

  public class DeviceSelectRequest extends SelectRequest {
    public DeviceSelectRequest(
        HdmiControlService hdmiControlService, int i, IHdmiControlCallback iHdmiControlCallback) {
      super(hdmiControlService, i, iHdmiControlCallback);
    }

    @Override // com.android.server.hdmi.SelectRequestBuffer.SelectRequest
    public void process() {
      if (isLocalDeviceReady()) {
        Slog.v("SelectRequestBuffer", "calling delayed deviceSelect id:" + this.mId);
        m31tv().deviceSelect(this.mId, this.mCallback);
      }
    }
  }

  public class PortSelectRequest extends SelectRequest {
    public PortSelectRequest(
        HdmiControlService hdmiControlService, int i, IHdmiControlCallback iHdmiControlCallback) {
      super(hdmiControlService, i, iHdmiControlCallback);
    }

    @Override // com.android.server.hdmi.SelectRequestBuffer.SelectRequest
    public void process() {
      if (isLocalDeviceReady()) {
        Slog.v("SelectRequestBuffer", "calling delayed portSelect id:" + this.mId);
        HdmiCecLocalDeviceTv m31tv = m31tv();
        if (m31tv != null) {
          m31tv.doManualPortSwitching(this.mId, this.mCallback);
          return;
        }
        HdmiCecLocalDeviceAudioSystem audioSystem = audioSystem();
        if (audioSystem != null) {
          audioSystem.doManualPortSwitching(this.mId, this.mCallback);
        }
      }
    }
  }

  public static DeviceSelectRequest newDeviceSelect(
      HdmiControlService hdmiControlService, int i, IHdmiControlCallback iHdmiControlCallback) {
    return new DeviceSelectRequest(hdmiControlService, i, iHdmiControlCallback);
  }

  public static PortSelectRequest newPortSelect(
      HdmiControlService hdmiControlService, int i, IHdmiControlCallback iHdmiControlCallback) {
    return new PortSelectRequest(hdmiControlService, i, iHdmiControlCallback);
  }

  public void set(SelectRequest selectRequest) {
    this.mRequest = selectRequest;
  }

  public void process() {
    SelectRequest selectRequest = this.mRequest;
    if (selectRequest != null) {
      selectRequest.process();
      clear();
    }
  }

  public void clear() {
    this.mRequest = null;
  }
}
