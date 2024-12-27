package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
