package com.android.server.hdmi;

import android.hardware.hdmi.HdmiDeviceInfo;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public final class DelayedMessageBuffer {
  public final ArrayList mBuffer = new ArrayList();
  public final HdmiCecLocalDevice mDevice;

  public DelayedMessageBuffer(HdmiCecLocalDevice hdmiCecLocalDevice) {
    this.mDevice = hdmiCecLocalDevice;
  }

  /* JADX WARN: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void add(HdmiCecMessage hdmiCecMessage) {
    boolean z;
    int opcode = hdmiCecMessage.getOpcode();
    if (opcode != 114) {
      if (opcode == 130) {
        removeActiveSource();
        this.mBuffer.add(hdmiCecMessage);
        z = true;
        if (z) {}
      } else if (opcode != 192) {
        z = false;
        if (z) {
          HdmiLogger.debug("Buffering message:" + hdmiCecMessage, new Object[0]);
          return;
        }
        return;
      }
    }
    this.mBuffer.add(hdmiCecMessage);
    z = true;
    if (z) {}
  }

  public void removeActiveSource() {
    Iterator it = this.mBuffer.iterator();
    while (it.hasNext()) {
      if (((HdmiCecMessage) it.next()).getOpcode() == 130) {
        it.remove();
      }
    }
  }

  public boolean isBuffered(int i) {
    Iterator it = this.mBuffer.iterator();
    while (it.hasNext()) {
      if (((HdmiCecMessage) it.next()).getOpcode() == i) {
        return true;
      }
    }
    return false;
  }

  public void processAllMessages() {
    ArrayList arrayList = new ArrayList(this.mBuffer);
    this.mBuffer.clear();
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      HdmiCecMessage hdmiCecMessage = (HdmiCecMessage) it.next();
      this.mDevice.onMessage(hdmiCecMessage);
      HdmiLogger.debug("Processing message:" + hdmiCecMessage, new Object[0]);
    }
  }

  public void processMessagesForDevice(int i) {
    ArrayList arrayList = new ArrayList(this.mBuffer);
    this.mBuffer.clear();
    HdmiLogger.debug("Checking message for address:" + i, new Object[0]);
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      HdmiCecMessage hdmiCecMessage = (HdmiCecMessage) it.next();
      if (hdmiCecMessage.getSource() != i) {
        this.mBuffer.add(hdmiCecMessage);
      } else if (hdmiCecMessage.getOpcode() == 130
          && !this.mDevice.isInputReady(HdmiDeviceInfo.idForCecDevice(i))) {
        this.mBuffer.add(hdmiCecMessage);
      } else {
        this.mDevice.onMessage(hdmiCecMessage);
        HdmiLogger.debug("Processing message:" + hdmiCecMessage, new Object[0]);
      }
    }
  }

  public void processActiveSource(int i) {
    ArrayList arrayList = new ArrayList(this.mBuffer);
    this.mBuffer.clear();
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      HdmiCecMessage hdmiCecMessage = (HdmiCecMessage) it.next();
      if (hdmiCecMessage.getOpcode() == 130 && hdmiCecMessage.getSource() == i) {
        this.mDevice.onMessage(hdmiCecMessage);
        HdmiLogger.debug("Processing message:" + hdmiCecMessage, new Object[0]);
      } else {
        this.mBuffer.add(hdmiCecMessage);
      }
    }
  }
}
