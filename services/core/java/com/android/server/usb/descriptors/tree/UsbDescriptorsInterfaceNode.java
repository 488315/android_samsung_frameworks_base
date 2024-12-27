package com.android.server.usb.descriptors.tree;

import com.android.server.usb.descriptors.UsbInterfaceDescriptor;

import java.util.ArrayList;

public final class UsbDescriptorsInterfaceNode extends UsbDescriptorsTreeNode {
    public final UsbInterfaceDescriptor mInterfaceDescriptor;
    public final ArrayList mEndpointNodes = new ArrayList();
    public final ArrayList mACInterfaceNodes = new ArrayList();

    public UsbDescriptorsInterfaceNode(UsbInterfaceDescriptor usbInterfaceDescriptor) {
        this.mInterfaceDescriptor = usbInterfaceDescriptor;
    }
}
