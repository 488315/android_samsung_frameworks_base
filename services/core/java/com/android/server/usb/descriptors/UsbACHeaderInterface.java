package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class UsbACHeaderInterface extends UsbACInterface {
    public final int mADCRelease;
    public int mTotalLength;

    public UsbACHeaderInterface(int i, byte b, byte b2, int i2, int i3) {
        super(i, b, b2, i2);
        this.mADCRelease = i3;
    }

    @Override // com.android.server.usb.descriptors.UsbACInterface, com.android.server.usb.descriptors.UsbDescriptor
    public void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("Release: " + TextReportCanvas.getBCDString(this.mADCRelease));
        textReportCanvas.writeListItem("Total Length: " + this.mTotalLength);
        textReportCanvas.closeList();
    }
}
