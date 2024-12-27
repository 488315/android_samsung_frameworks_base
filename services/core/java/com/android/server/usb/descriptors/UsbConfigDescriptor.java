package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

import java.util.ArrayList;

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
