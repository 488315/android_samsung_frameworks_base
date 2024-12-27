package com.android.server.usb.descriptors.tree;

import com.android.server.usb.descriptors.UsbEndpointDescriptor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UsbDescriptorsEndpointNode extends UsbDescriptorsTreeNode {
    public final UsbEndpointDescriptor mEndpointDescriptor;

    public UsbDescriptorsEndpointNode(UsbEndpointDescriptor usbEndpointDescriptor) {
        this.mEndpointDescriptor = usbEndpointDescriptor;
    }
}
