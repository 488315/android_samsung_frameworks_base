package com.android.server.usb.descriptors;

import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.usb.descriptors.report.TextReportCanvas;
import com.android.server.usb.descriptors.report.UsbStrings;

import java.util.ArrayList;

public final class UsbDeviceDescriptor extends UsbDescriptor {
    public ArrayList mConfigDescriptors;
    public int mDevClass;
    public int mDevSubClass;
    public int mDeviceRelease;
    public byte mMfgIndex;
    public int mProductID;
    public byte mProductIndex;
    public int mProtocol;
    public byte mSerialIndex;
    public int mSpec;
    public int mVendorID;

    public final String getDeviceReleaseString() {
        int i = this.mDeviceRelease;
        return String.format(
                "%d.%d%d",
                Integer.valueOf((((i & 61440) >> 12) * 10) + ((i & 3840) >> 8)),
                Integer.valueOf((i & 240) >> 4),
                Integer.valueOf(i & 15));
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        this.mSpec = byteStream.unpackUsbShort();
        this.mDevClass = byteStream.getUnsignedByte();
        this.mDevSubClass = byteStream.getUnsignedByte();
        this.mProtocol = byteStream.getUnsignedByte();
        byteStream.getByte();
        this.mVendorID = byteStream.unpackUsbShort();
        this.mProductID = byteStream.unpackUsbShort();
        this.mDeviceRelease = byteStream.unpackUsbShort();
        this.mMfgIndex = byteStream.getByte();
        this.mProductIndex = byteStream.getByte();
        this.mSerialIndex = byteStream.getByte();
        byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("Spec: " + TextReportCanvas.getBCDString(this.mSpec));
        int i = this.mDevClass;
        String className = UsbStrings.getClassName(i);
        int i2 = this.mDevSubClass;
        String className2 = UsbStrings.getClassName(i2);
        StringBuilder m =
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        i, "Class ", ": ", className, " Subclass");
        m.append(i2);
        m.append(": ");
        m.append(className2);
        textReportCanvas.writeListItem(m.toString());
        textReportCanvas.writeListItem(
                "Vendor ID: "
                        + TextReportCanvas.getHexString(this.mVendorID)
                        + " Product ID: "
                        + TextReportCanvas.getHexString(this.mProductID)
                        + " Product Release: "
                        + TextReportCanvas.getBCDString(this.mDeviceRelease));
        byte b = this.mMfgIndex;
        UsbDescriptorParser usbDescriptorParser = textReportCanvas.mParser;
        String descriptorString = usbDescriptorParser.getDescriptorString(b);
        byte b2 = this.mProductIndex;
        String descriptorString2 = usbDescriptorParser.getDescriptorString(b2);
        StringBuilder m2 =
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        b, "Manufacturer ", ": ", descriptorString, " Product ");
        m2.append((int) b2);
        m2.append(": ");
        m2.append(descriptorString2);
        textReportCanvas.writeListItem(m2.toString());
        textReportCanvas.closeList();
    }
}
