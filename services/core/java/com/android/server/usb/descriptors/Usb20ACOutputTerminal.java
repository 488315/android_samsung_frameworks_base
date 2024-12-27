package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

public final class Usb20ACOutputTerminal extends UsbACTerminal {
    public byte mClkSoureID;
    public int mControls;
    public byte mSourceID;
    public byte mTerminalID;

    @Override // com.android.server.usb.descriptors.UsbACTerminal
    public final byte getTerminalID() {
        return this.mTerminalID;
    }

    @Override // com.android.server.usb.descriptors.UsbACTerminal,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        super.parseRawDescriptors(byteStream);
        this.mSourceID = byteStream.getByte();
        this.mClkSoureID = byteStream.getByte();
        this.mControls = byteStream.unpackUsbShort();
        this.mTerminalID = byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACTerminal,
              // com.android.server.usb.descriptors.UsbACInterface,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("Source ID:" + ((int) this.mSourceID));
        textReportCanvas.writeListItem("Clock Source ID: " + ((int) this.mClkSoureID));
        textReportCanvas.writeListItem(
                "Controls: " + TextReportCanvas.getHexString(this.mControls));
        textReportCanvas.writeListItem("Terminal Name ID: " + ((int) this.mTerminalID));
        textReportCanvas.closeList();
    }
}
