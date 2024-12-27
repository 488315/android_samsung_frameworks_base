package com.android.server.usb.descriptors;

import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.usb.descriptors.report.TextReportCanvas;
import com.android.server.usb.descriptors.report.UsbStrings;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbInterfaceDescriptor extends UsbDescriptor {
    public byte mAlternateSetting;
    public byte mDescrIndex;
    public ArrayList mEndpointDescriptors;
    public int mInterfaceNumber;
    public UsbDescriptor mMidiHeaderInterfaceDescriptor;
    public byte mNumEndpoints;
    public int mProtocol;
    public int mUsbClass;
    public int mUsbSubclass;

    public final UsbEndpointDescriptor getEndpointDescriptor(int i) {
        if (i < 0 || i >= this.mEndpointDescriptors.size()) {
            return null;
        }
        return (UsbEndpointDescriptor) this.mEndpointDescriptors.get(i);
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        this.mInterfaceNumber = byteStream.getUnsignedByte();
        this.mAlternateSetting = byteStream.getByte();
        this.mNumEndpoints = byteStream.getByte();
        this.mUsbClass = byteStream.getUnsignedByte();
        this.mUsbSubclass = byteStream.getUnsignedByte();
        this.mProtocol = byteStream.getUnsignedByte();
        this.mDescrIndex = byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        String str;
        super.report(textReportCanvas);
        int i = this.mUsbClass;
        int i2 = this.mUsbSubclass;
        int i3 = this.mProtocol;
        String className = UsbStrings.getClassName(i);
        if (i == 1) {
            str = (String) UsbStrings.sAudioSubclassNames.get(Integer.valueOf(i2));
            int i4 = i2 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
            if (str == null) {
                str = "Unknown Audio Subclass [0x" + Integer.toHexString(i4) + ":" + i4 + "]";
            }
        } else {
            str = "";
        }
        textReportCanvas.openList();
        textReportCanvas.writeListItem("Interface #" + this.mInterfaceNumber);
        textReportCanvas.writeListItem("Class: " + TextReportCanvas.getHexString(i) + ": " + className);
        textReportCanvas.writeListItem("Subclass: " + TextReportCanvas.getHexString(i2) + ": " + str);
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i3, "Protocol: ", ": ");
        m.append(TextReportCanvas.getHexString(i3));
        textReportCanvas.writeListItem(m.toString());
        textReportCanvas.writeListItem("Endpoints: " + ((int) this.mNumEndpoints));
        textReportCanvas.closeList();
    }

    public final UsbInterface toAndroid(UsbDescriptorParser usbDescriptorParser) {
        UsbInterface usbInterface = new UsbInterface(this.mInterfaceNumber, this.mAlternateSetting, usbDescriptorParser.getDescriptorString(this.mDescrIndex), this.mUsbClass, this.mUsbSubclass, this.mProtocol);
        UsbEndpoint[] usbEndpointArr = new UsbEndpoint[this.mEndpointDescriptors.size()];
        for (int i = 0; i < this.mEndpointDescriptors.size(); i++) {
            UsbEndpointDescriptor usbEndpointDescriptor = (UsbEndpointDescriptor) this.mEndpointDescriptors.get(i);
            usbEndpointDescriptor.getClass();
            usbEndpointArr[i] = new UsbEndpoint(usbEndpointDescriptor.mEndpointAddress, usbEndpointDescriptor.mAttributes, usbEndpointDescriptor.mPacketSize, usbEndpointDescriptor.mInterval);
        }
        usbInterface.setEndpoints(usbEndpointArr);
        return usbInterface;
    }
}
