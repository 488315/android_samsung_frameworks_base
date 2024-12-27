package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
