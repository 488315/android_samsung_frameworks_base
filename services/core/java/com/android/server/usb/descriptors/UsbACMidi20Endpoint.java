package com.android.server.usb.descriptors;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.usb.descriptors.report.TextReportCanvas;

public final class UsbACMidi20Endpoint extends UsbACEndpoint {
    public byte[] mBlockIds;
    public byte mNumGroupTerminals;

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        int i = byteStream.getByte();
        this.mNumGroupTerminals = i;
        if (i > 0) {
            this.mBlockIds = new byte[i];
            for (int i2 = 0; i2 < this.mNumGroupTerminals; i2++) {
                this.mBlockIds[i2] = byteStream.getByte();
            }
        }
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.writeHeader(
                "AC Midi20 Endpoint: "
                        + TextReportCanvas.getHexString(this.mType)
                        + " Length: "
                        + this.mLength);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("" + ((int) this.mNumGroupTerminals) + " Group Terminals.");
        for (int i = 0; i < this.mNumGroupTerminals; i++) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(i, "Group Terminal ", ": ");
            m.append((int) this.mBlockIds[i]);
            textReportCanvas.writeListItem(m.toString());
        }
        textReportCanvas.closeList();
    }
}
