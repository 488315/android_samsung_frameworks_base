package com.android.server.usb.descriptors.tree;

import com.android.server.usb.descriptors.UsbConfigDescriptor;

import java.util.ArrayList;

public final class UsbDescriptorsConfigNode extends UsbDescriptorsTreeNode {
    public final UsbConfigDescriptor mConfigDescriptor;
    public final ArrayList mInterfaceNodes = new ArrayList();

    public UsbDescriptorsConfigNode(UsbConfigDescriptor usbConfigDescriptor) {
        this.mConfigDescriptor = usbConfigDescriptor;
    }
}
