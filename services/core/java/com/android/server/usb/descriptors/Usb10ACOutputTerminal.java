package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Usb10ACOutputTerminal extends UsbACTerminal {
    public byte mSourceID;

    @Override // com.android.server.usb.descriptors.UsbACTerminal,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        super.parseRawDescriptors(byteStream);
        this.mSourceID = byteStream.getByte();
        byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACTerminal,
              // com.android.server.usb.descriptors.UsbACInterface,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem(
                "Source ID: " + TextReportCanvas.getHexString(this.mSourceID));
        textReportCanvas.closeList();
    }
}
