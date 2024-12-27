package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

public final class Usb20ACInputTerminal extends UsbACTerminal implements UsbAudioChannelCluster {
    public int mChanConfig;
    public byte mClkSourceID;
    public byte mNumChannels;

    @Override // com.android.server.usb.descriptors.UsbAudioChannelCluster
    public final byte getChannelCount() {
        return this.mNumChannels;
    }

    @Override // com.android.server.usb.descriptors.UsbACTerminal,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        super.parseRawDescriptors(byteStream);
        this.mClkSourceID = byteStream.getByte();
        this.mNumChannels = byteStream.getByte();
        this.mChanConfig = byteStream.unpackUsbInt();
        byteStream.getByte();
        byteStream.unpackUsbShort();
        byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACTerminal,
              // com.android.server.usb.descriptors.UsbACInterface,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("Clock Source: " + ((int) this.mClkSourceID));
        textReportCanvas.writeListItem(
                ""
                        + ((int) this.mNumChannels)
                        + " Channels. Config: "
                        + TextReportCanvas.getHexString(this.mChanConfig));
        textReportCanvas.closeList();
    }
}
