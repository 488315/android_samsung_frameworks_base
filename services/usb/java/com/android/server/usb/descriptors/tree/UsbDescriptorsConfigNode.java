package com.android.server.usb.descriptors.tree;

import com.android.server.usb.descriptors.UsbConfigDescriptor;
import com.android.server.usb.descriptors.report.ReportCanvas;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public final class UsbDescriptorsConfigNode extends UsbDescriptorsTreeNode {
  public final UsbConfigDescriptor mConfigDescriptor;
  public final ArrayList mInterfaceNodes = new ArrayList();

  public UsbDescriptorsConfigNode(UsbConfigDescriptor usbConfigDescriptor) {
    this.mConfigDescriptor = usbConfigDescriptor;
  }

  public void addInterfaceNode(UsbDescriptorsInterfaceNode usbDescriptorsInterfaceNode) {
    this.mInterfaceNodes.add(usbDescriptorsInterfaceNode);
  }

  public void report(ReportCanvas reportCanvas) {
    this.mConfigDescriptor.report(reportCanvas);
    reportCanvas.openList();
    Iterator it = this.mInterfaceNodes.iterator();
    while (it.hasNext()) {
      ((UsbDescriptorsInterfaceNode) it.next()).report(reportCanvas);
    }
    reportCanvas.closeList();
  }
}
