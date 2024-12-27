package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbConfigDescriptor extends UsbDescriptor {
    public int mAttribs;
    public byte mConfigIndex;
    public int mConfigValue;
    public ArrayList mInterfaceDescriptors;
    public int mMaxPower;
    public byte mNumInterfaces;

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        byteStream.unpackUsbShort();
        this.mNumInterfaces = byteStream.getByte();
        this.mConfigValue = byteStream.getUnsignedByte();
        this.mConfigIndex = byteStream.getByte();
        this.mAttribs = byteStream.getUnsignedByte();
        this.mMaxPower = byteStream.getUnsignedByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("Config # " + this.mConfigValue);
        textReportCanvas.writeListItem(((int) this.mNumInterfaces) + " Interfaces.");
        StringBuilder sb = new StringBuilder("Attributes: ");
        sb.append(TextReportCanvas.getHexString(this.mAttribs));
        textReportCanvas.writeListItem(sb.toString());
        textReportCanvas.closeList();
    }
}
