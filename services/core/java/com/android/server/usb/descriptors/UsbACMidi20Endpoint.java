package com.android.server.usb.descriptors;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.usb.descriptors.report.TextReportCanvas;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbACMidi20Endpoint extends UsbACEndpoint {
    public byte[] mBlockIds;
    public byte mNumGroupTerminals;

    /* JADX WARN: Multi-variable type inference failed */
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
        textReportCanvas.writeHeader("AC Midi20 Endpoint: " + TextReportCanvas.getHexString(this.mType) + " Length: " + this.mLength);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("" + ((int) this.mNumGroupTerminals) + " Group Terminals.");
        for (int i = 0; i < this.mNumGroupTerminals; i++) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Group Terminal ", ": ");
            m.append((int) this.mBlockIds[i]);
            textReportCanvas.writeListItem(m.toString());
        }
        textReportCanvas.closeList();
    }
}
