package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.ReportCanvas;

/* loaded from: classes3.dex */
public final class UsbVCHeader extends UsbVCHeaderInterface {
  public UsbVCHeader(int i, byte b, byte b2, int i2) {
    super(i, b, b2, i2);
  }

  @Override // com.android.server.usb.descriptors.UsbDescriptor
  public int parseRawDescriptors(ByteStream byteStream) {
    return super.parseRawDescriptors(byteStream);
  }

  @Override // com.android.server.usb.descriptors.UsbVCHeaderInterface,
  // com.android.server.usb.descriptors.UsbDescriptor
  public void report(ReportCanvas reportCanvas) {
    super.report(reportCanvas);
  }
}
