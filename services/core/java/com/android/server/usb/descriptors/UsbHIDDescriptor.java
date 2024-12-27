package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

public final class UsbHIDDescriptor extends UsbDescriptor {
    public int mDescriptorLen;
    public byte mDescriptorType;
    public byte mNumDescriptors;
    public int mRelease;

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        this.mRelease = byteStream.unpackUsbShort();
        byteStream.getByte();
        this.mNumDescriptors = byteStream.getByte();
        this.mDescriptorType = byteStream.getByte();
        this.mDescriptorLen = byteStream.unpackUsbShort();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("Spec: " + TextReportCanvas.getBCDString(this.mRelease));
        textReportCanvas.writeListItem(
                "Type: " + TextReportCanvas.getBCDString(this.mDescriptorType));
        textReportCanvas.writeListItem(
                "" + ((int) this.mNumDescriptors) + " Descriptors Len: " + this.mDescriptorLen);
        textReportCanvas.closeList();
    }
}
