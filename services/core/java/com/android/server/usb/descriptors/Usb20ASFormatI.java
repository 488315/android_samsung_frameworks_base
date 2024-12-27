package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

public final class Usb20ASFormatI extends UsbASFormat {
    public byte mBitResolution;
    public byte mSubSlotSize;

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        this.mSubSlotSize = byteStream.getByte();
        this.mBitResolution = byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbASFormat,
              // com.android.server.usb.descriptors.UsbACInterface,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("Subslot Size: " + ((int) this.mSubSlotSize));
        textReportCanvas.writeListItem("Bit Resolution: " + ((int) this.mBitResolution));
        textReportCanvas.closeList();
    }
}
