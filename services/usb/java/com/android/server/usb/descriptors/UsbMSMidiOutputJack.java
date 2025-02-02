package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.ReportCanvas;

/* loaded from: classes3.dex */
public final class UsbMSMidiOutputJack extends UsbACInterface {
  public UsbMSMidiOutputJack(int i, byte b, byte b2, int i2) {
    super(i, b, b2, i2);
  }

  @Override // com.android.server.usb.descriptors.UsbDescriptor
  public int parseRawDescriptors(ByteStream byteStream) {
    byteStream.advance(this.mLength - byteStream.getReadCount());
    return this.mLength;
  }

  @Override // com.android.server.usb.descriptors.UsbACInterface,
  // com.android.server.usb.descriptors.UsbDescriptor
  public void report(ReportCanvas reportCanvas) {
    super.report(reportCanvas);
    reportCanvas.writeHeader(
        3,
        "MS Midi Output Jack: "
            + ReportCanvas.getHexString(getType())
            + " SubType: "
            + ReportCanvas.getHexString(getSubclass())
            + " Length: "
            + getLength());
  }
}
