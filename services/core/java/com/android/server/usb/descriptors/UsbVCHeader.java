package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbVCHeader extends UsbVCInterface {
    public int mVDCRelease;

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem("Release: " + TextReportCanvas.getBCDString(this.mVDCRelease));
        textReportCanvas.writeListItem("Total Length: 0");
        textReportCanvas.closeList();
    }
}
