package com.android.server.hdmi;

import java.util.ArrayList;
import java.util.Iterator;

public final class DelayedMessageBuffer {
    public final ArrayList mBuffer = new ArrayList();
    public final HdmiCecLocalDevice mDevice;

    public DelayedMessageBuffer(HdmiCecLocalDevice hdmiCecLocalDevice) {
        this.mDevice = hdmiCecLocalDevice;
    }

    public final void add(HdmiCecMessage hdmiCecMessage) {
        int i = hdmiCecMessage.mOpcode;
        if (i != 114) {
            if (i == 130) {
                Iterator it = this.mBuffer.iterator();
                while (it.hasNext()) {
                    if (((HdmiCecMessage) it.next()).mOpcode == 130) {
                        it.remove();
                    }
                }
                this.mBuffer.add(hdmiCecMessage);
                HdmiLogger.debug("Buffering message:" + hdmiCecMessage, new Object[0]);
            }
            if (i != 192) {
                return;
            }
        }
        this.mBuffer.add(hdmiCecMessage);
        HdmiLogger.debug("Buffering message:" + hdmiCecMessage, new Object[0]);
    }

    public final void processActiveSource(int i) {
        ArrayList arrayList = new ArrayList(this.mBuffer);
        this.mBuffer.clear();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            HdmiCecMessage hdmiCecMessage = (HdmiCecMessage) it.next();
            if (hdmiCecMessage.mOpcode == 130 && hdmiCecMessage.mSource == i) {
                this.mDevice.onMessage(hdmiCecMessage);
                HdmiLogger.debug("Processing message:" + hdmiCecMessage, new Object[0]);
            } else {
                this.mBuffer.add(hdmiCecMessage);
            }
        }
    }
}
