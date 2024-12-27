package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

public final class Usb10ASFormatII extends UsbASFormat {
    public int mMaxBitRate;
    public byte mSamFreqType;
    public int[] mSampleRates;

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        this.mMaxBitRate = byteStream.unpackUsbShort();
        byteStream.unpackUsbShort();
        byte b = byteStream.getByte();
        this.mSamFreqType = b;
        int i = b;
        if (b == 0) {
            i = 2;
        }
        this.mSampleRates = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mSampleRates[i2] = byteStream.unpackUsbTriple();
        }
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbASFormat,
              // com.android.server.usb.descriptors.UsbACInterface,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("Max Bit Rate: " + this.mMaxBitRate);
        textReportCanvas.writeListItem("Samples Per Frame: " + this.mMaxBitRate);
        byte b = this.mSamFreqType;
        int[] iArr = this.mSampleRates;
        textReportCanvas.writeListItem("Sample Freq Type: " + ((int) b));
        textReportCanvas.openList();
        if (b == 0) {
            textReportCanvas.writeListItem("min: " + iArr[0]);
            textReportCanvas.writeListItem("max: " + iArr[1]);
        } else {
            for (int i = 0; i < b; i++) {
                textReportCanvas.writeListItem("" + iArr[i]);
            }
        }
        textReportCanvas.closeList();
        textReportCanvas.closeList();
    }
}
