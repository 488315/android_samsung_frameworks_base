package com.android.server.usb.descriptors.tree;

import com.android.server.usb.descriptors.UsbDeviceDescriptor;

import java.util.ArrayList;

public final class UsbDescriptorsDeviceNode extends UsbDescriptorsTreeNode {
    public final ArrayList mConfigNodes = new ArrayList();
    public final UsbDeviceDescriptor mDeviceDescriptor;

    public UsbDescriptorsDeviceNode(UsbDeviceDescriptor usbDeviceDescriptor) {
        this.mDeviceDescriptor = usbDeviceDescriptor;
    }
}
