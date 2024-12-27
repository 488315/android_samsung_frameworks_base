package com.android.server.usb.descriptors;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.usb.descriptors.report.TextReportCanvas;

public final class UsbACMidi10Endpoint extends UsbACEndpoint {
    public byte[] mJackIds;
    public byte mNumJacks;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        int i = byteStream.getByte();
        this.mNumJacks = i;
        if (i > 0) {
            this.mJackIds = new byte[i];
            for (int i2 = 0; i2 < this.mNumJacks; i2++) {
                this.mJackIds[i2] = byteStream.getByte();
            }
        }
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.writeHeader(
                "ACMidi10Endpoint: "
                        + TextReportCanvas.getHexString(this.mType)
                        + " Length: "
                        + this.mLength);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("" + ((int) this.mNumJacks) + " Jacks.");
        for (int i = 0; i < this.mNumJacks; i++) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Jack ", ": ");
            m.append((int) this.mJackIds[i]);
            textReportCanvas.writeListItem(m.toString());
        }
        textReportCanvas.closeList();
    }
}
