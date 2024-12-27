package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

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
