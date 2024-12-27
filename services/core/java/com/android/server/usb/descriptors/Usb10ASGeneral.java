package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;
import com.android.server.usb.descriptors.report.UsbStrings;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Usb10ASGeneral extends UsbACInterface {
    public byte mDelay;
    public int mFormatTag;
    public byte mTerminalLink;

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        this.mTerminalLink = byteStream.getByte();
        this.mDelay = byteStream.getByte();
        this.mFormatTag = byteStream.unpackUsbShort();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACInterface, com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("Delay: " + ((int) this.mDelay));
        textReportCanvas.writeListItem("Terminal Link: " + ((int) this.mTerminalLink));
        StringBuilder sb = new StringBuilder("Format: ");
        int i = this.mFormatTag;
        String str = (String) UsbStrings.sAudioEncodingNames.get(Integer.valueOf(i));
        if (str == null) {
            str = "Unknown Format (encoding) ID [0x" + Integer.toHexString(i) + ":" + i + "]";
        }
        sb.append(str);
        sb.append(" - ");
        sb.append(TextReportCanvas.getHexString(this.mFormatTag));
        textReportCanvas.writeListItem(sb.toString());
        textReportCanvas.closeList();
    }
}
