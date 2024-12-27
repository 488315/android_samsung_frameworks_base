package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;
import com.android.server.usb.descriptors.report.UsbStrings;

import java.util.HashMap;

public abstract class UsbACInterface extends UsbDescriptor {
    public final int mSubclass;
    public final byte mSubtype;

    public UsbACInterface(int i, byte b, byte b2, int i2) {
        super(b, i);
        this.mSubtype = b2;
        this.mSubclass = i2;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        int i = this.mSubclass;
        String str = i == 1 ? "AC Control" : "AC Streaming";
        HashMap hashMap = UsbStrings.sACControlInterfaceNames;
        byte b = this.mSubtype;
        String str2 = (String) hashMap.get(Byte.valueOf(b));
        int i2 = b & 255;
        if (str2 == null) {
            str2 = "Unknown subtype [0x" + Integer.toHexString(i2) + ":" + i2 + "]";
        }
        textReportCanvas.openList();
        textReportCanvas.writeListItem("Subclass: " + TextReportCanvas.getHexString(i) + " " + str);
        textReportCanvas.writeListItem("Subtype: " + TextReportCanvas.getHexString(b) + " " + str2);
        textReportCanvas.closeList();
    }
}
