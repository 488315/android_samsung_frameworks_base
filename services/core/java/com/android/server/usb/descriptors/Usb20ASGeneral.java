package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Usb20ASGeneral extends UsbACInterface implements UsbAudioChannelCluster {
    public int mChannelConfig;
    public byte mChannelNames;
    public byte mControls;
    public byte mFormatType;
    public int mFormats;
    public byte mNumChannels;
    public byte mTerminalLink;

    @Override // com.android.server.usb.descriptors.UsbAudioChannelCluster
    public final byte getChannelCount() {
        return this.mNumChannels;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        this.mTerminalLink = byteStream.getByte();
        this.mControls = byteStream.getByte();
        this.mFormatType = byteStream.getByte();
        this.mFormats = byteStream.unpackUsbInt();
        this.mNumChannels = byteStream.getByte();
        this.mChannelConfig = byteStream.unpackUsbInt();
        this.mChannelNames = byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACInterface,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("Terminal Link: " + ((int) this.mTerminalLink));
        textReportCanvas.writeListItem(
                "Controls: " + TextReportCanvas.getHexString(this.mControls));
        textReportCanvas.writeListItem(
                "Format Type: " + TextReportCanvas.getHexString(this.mFormatType));
        textReportCanvas.writeListItem("Formats: " + TextReportCanvas.getHexString(this.mFormats));
        textReportCanvas.writeListItem("Channel Count: " + ((int) this.mNumChannels));
        textReportCanvas.writeListItem(
                "Channel Config: " + TextReportCanvas.getHexString(this.mChannelConfig));
        textReportCanvas.writeListItem("Channel Names String ID: " + ((int) this.mChannelNames));
        textReportCanvas.closeList();
    }
}
