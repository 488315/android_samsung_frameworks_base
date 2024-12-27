package com.android.server.usb.descriptors;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;

import com.android.server.usb.descriptors.report.TextReportCanvas;
import com.android.server.usb.descriptors.report.UsbStrings;

public abstract class UsbACTerminal extends UsbACInterface {
    public byte mAssocTerminal;
    public byte mTerminalID;
    public int mTerminalType;

    public byte getTerminalID() {
        return this.mTerminalID;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(ByteStream byteStream) {
        this.mTerminalID = byteStream.getByte();
        this.mTerminalType = byteStream.unpackUsbShort();
        this.mAssocTerminal = byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACInterface,
              // com.android.server.usb.descriptors.UsbDescriptor
    public void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        int i = this.mTerminalType;
        StringBuilder sb = new StringBuilder("Type: ");
        sb.append(TextReportCanvas.getHexString(i));
        sb.append(": ");
        String str = (String) UsbStrings.sTerminalNames.get(Integer.valueOf(i));
        if (str == null) {
            str =
                    AudioChannelMask$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Unknown Terminal Type 0x"), i);
        }
        sb.append(str);
        textReportCanvas.writeListItem(sb.toString());
        textReportCanvas.writeListItem("ID: " + TextReportCanvas.getHexString(getTerminalID()));
        textReportCanvas.writeListItem(
                "Associated terminal: " + TextReportCanvas.getHexString(this.mAssocTerminal));
        textReportCanvas.closeList();
    }
}
