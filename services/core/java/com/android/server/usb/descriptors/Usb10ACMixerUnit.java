package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Usb10ACMixerUnit extends UsbACMixerUnit implements UsbAudioChannelCluster {
    public int mChannelConfig;
    public byte[] mControls;

    @Override // com.android.server.usb.descriptors.UsbAudioChannelCluster
    public final byte getChannelCount() {
        return this.mNumOutputs;
    }

    @Override // com.android.server.usb.descriptors.UsbACMixerUnit,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        super.parseRawDescriptors(byteStream);
        this.mChannelConfig = byteStream.unpackUsbShort();
        byteStream.getByte();
        int i = ((this.mNumInputs * this.mNumOutputs) + 7) / 8;
        this.mControls = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mControls[i2] = byteStream.getByte();
        }
        byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACInterface,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.writeParagraph("Mixer Unit", false);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("Unit ID: " + TextReportCanvas.getHexString(this.mUnitID));
        byte b = this.mNumInputs;
        byte[] bArr = this.mInputIDs;
        textReportCanvas.openListItem();
        textReportCanvas.write("Num Inputs: " + ((int) b) + " [");
        for (int i = 0; i < b; i++) {
            textReportCanvas.write("" + TextReportCanvas.getHexString(bArr[i]));
            if (i < b - 1) {
                textReportCanvas.write(" ");
            }
        }
        textReportCanvas.write("]");
        textReportCanvas.closeListItem();
        textReportCanvas.writeListItem("Num Outputs: " + ((int) this.mNumOutputs));
        textReportCanvas.writeListItem(
                "Channel Config: " + TextReportCanvas.getHexString(this.mChannelConfig));
        byte[] bArr2 = this.mControls;
        textReportCanvas.openListItem();
        textReportCanvas.write("Controls: " + bArr2.length + " [");
        for (int i2 = 0; i2 < bArr2.length; i2++) {
            textReportCanvas.write("" + ((int) bArr2[i2]));
            if (i2 < bArr2.length - 1) {
                textReportCanvas.write(" ");
            }
        }
        textReportCanvas.write("]");
        textReportCanvas.closeListItem();
        textReportCanvas.closeList();
    }
}
