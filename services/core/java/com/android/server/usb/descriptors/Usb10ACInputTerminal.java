package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

public final class Usb10ACInputTerminal extends UsbACTerminal implements UsbAudioChannelCluster {
    public int mChannelConfig;
    public byte mNrChannels;

    @Override // com.android.server.usb.descriptors.UsbAudioChannelCluster
    public final byte getChannelCount() {
        return this.mNrChannels;
    }

    @Override // com.android.server.usb.descriptors.UsbACTerminal,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        super.parseRawDescriptors(byteStream);
        this.mNrChannels = byteStream.getByte();
        this.mChannelConfig = byteStream.unpackUsbShort();
        byteStream.getByte();
        byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACTerminal,
              // com.android.server.usb.descriptors.UsbACInterface,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem(
                ""
                        + ((int) this.mNrChannels)
                        + " Chans. Config: "
                        + TextReportCanvas.getHexString(this.mChannelConfig));
        textReportCanvas.closeList();
    }
}
