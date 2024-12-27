package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

public final class UsbVCHeader extends UsbVCInterface {
    public int mVDCRelease;

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        textReportCanvas.writeListItem(
                "Release: " + TextReportCanvas.getBCDString(this.mVDCRelease));
        textReportCanvas.writeListItem("Total Length: 0");
        textReportCanvas.closeList();
    }
}
