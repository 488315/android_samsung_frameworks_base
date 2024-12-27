package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

public final class Usb20ASFormatII extends UsbASFormat {
    public int mMaxBitRate;
    public int mSlotsPerFrame;

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        this.mMaxBitRate = byteStream.unpackUsbShort();
        this.mSlotsPerFrame = byteStream.unpackUsbShort();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbASFormat,
              // com.android.server.usb.descriptors.UsbACInterface,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("Max Bit Rate: " + this.mMaxBitRate);
        textReportCanvas.writeListItem("slots Per Frame: " + this.mSlotsPerFrame);
        textReportCanvas.closeList();
    }
}
