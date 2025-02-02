package com.android.server.usb.descriptors.tree;

import com.android.server.audio.CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.usb.descriptors.UsbInterfaceDescriptor;
import com.android.server.usb.descriptors.report.ReportCanvas;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public final class UsbDescriptorsInterfaceNode extends UsbDescriptorsTreeNode {
  public final UsbInterfaceDescriptor mInterfaceDescriptor;
  public final ArrayList mEndpointNodes = new ArrayList();
  public final ArrayList mACInterfaceNodes = new ArrayList();

  public UsbDescriptorsInterfaceNode(UsbInterfaceDescriptor usbInterfaceDescriptor) {
    this.mInterfaceDescriptor = usbInterfaceDescriptor;
  }

  public void addEndpointNode(UsbDescriptorsEndpointNode usbDescriptorsEndpointNode) {
    this.mEndpointNodes.add(usbDescriptorsEndpointNode);
  }

  public void report(ReportCanvas reportCanvas) {
    this.mInterfaceDescriptor.report(reportCanvas);
    if (this.mACInterfaceNodes.size() > 0) {
      reportCanvas.writeParagraph("Audio Class Interfaces", false);
      reportCanvas.openList();
      Iterator it = this.mACInterfaceNodes.iterator();
      if (it.hasNext()) {
        CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(it.next());
        throw null;
      }
      reportCanvas.closeList();
    }
    if (this.mEndpointNodes.size() > 0) {
      reportCanvas.writeParagraph("Endpoints", false);
      reportCanvas.openList();
      Iterator it2 = this.mEndpointNodes.iterator();
      while (it2.hasNext()) {
        ((UsbDescriptorsEndpointNode) it2.next()).report(reportCanvas);
      }
      reportCanvas.closeList();
    }
  }
}
