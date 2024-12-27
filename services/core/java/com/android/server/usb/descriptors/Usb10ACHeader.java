package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Usb10ACHeader extends UsbACHeaderInterface {
    public byte mControls;
    public byte[] mInterfaceNums;
    public byte mNumInterfaces;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        this.mTotalLength = byteStream.unpackUsbShort();
        if (this.mADCRelease >= 512) {
            this.mControls = byteStream.getByte();
        } else {
            int i = byteStream.getByte();
            this.mNumInterfaces = i;
            this.mInterfaceNums = new byte[i];
            for (int i2 = 0; i2 < this.mNumInterfaces; i2++) {
                this.mInterfaceNums[i2] = byteStream.getByte();
            }
        }
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACHeaderInterface,
              // com.android.server.usb.descriptors.UsbACInterface,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        byte b = this.mNumInterfaces;
        StringBuilder sb = new StringBuilder();
        sb.append("" + ((int) b) + " Interfaces");
        if (b > 0) {
            sb.append(" [");
            byte[] bArr = this.mInterfaceNums;
            if (bArr != null) {
                for (int i = 0; i < b; i++) {
                    sb.append("" + ((int) bArr[i]));
                    if (i < b - 1) {
                        sb.append(" ");
                    }
                }
            }
            sb.append("]");
        }
        textReportCanvas.writeListItem(sb.toString());
        textReportCanvas.writeListItem(
                "Controls: " + TextReportCanvas.getHexString(this.mControls));
        textReportCanvas.closeList();
    }
}
