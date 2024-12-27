package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

public final class Usb20ACHeader extends UsbACHeaderInterface {
    public byte mCategory;
    public byte mControls;

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        this.mCategory = byteStream.getByte();
        this.mTotalLength = byteStream.unpackUsbShort();
        this.mControls = byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACHeaderInterface,
              // com.android.server.usb.descriptors.UsbACInterface,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem(
                "Category: " + TextReportCanvas.getHexString(this.mCategory));
        textReportCanvas.writeListItem(
                "Controls: " + TextReportCanvas.getHexString(this.mControls));
        textReportCanvas.closeList();
    }
}
