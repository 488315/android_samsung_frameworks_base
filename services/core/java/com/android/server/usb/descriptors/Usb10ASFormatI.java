package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Usb10ASFormatI extends UsbASFormat {
    public byte mBitResolution;
    public byte mNumChannels;
    public byte mSampleFreqType;
    public int[] mSampleRates;
    public byte mSubframeSize;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        this.mNumChannels = byteStream.getByte();
        this.mSubframeSize = byteStream.getByte();
        this.mBitResolution = byteStream.getByte();
        int i = byteStream.getByte();
        this.mSampleFreqType = i;
        if (i == 0) {
            this.mSampleRates = new int[]{byteStream.unpackUsbTriple(), 0};
            this.mSampleRates[1] = byteStream.unpackUsbTriple();
        } else {
            this.mSampleRates = new int[i];
            for (int i2 = 0; i2 < this.mSampleFreqType; i2++) {
                this.mSampleRates[i2] = byteStream.unpackUsbTriple();
            }
        }
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbASFormat, com.android.server.usb.descriptors.UsbACInterface, com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("" + ((int) this.mNumChannels) + " Channels.");
        StringBuilder sb = new StringBuilder("Subframe Size: ");
        sb.append((int) this.mSubframeSize);
        textReportCanvas.writeListItem(sb.toString());
        textReportCanvas.writeListItem("Bit Resolution: " + ((int) this.mBitResolution));
        byte b = this.mSampleFreqType;
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
