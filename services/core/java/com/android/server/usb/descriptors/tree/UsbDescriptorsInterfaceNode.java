package com.android.server.usb.descriptors.tree;

import com.android.server.usb.descriptors.UsbInterfaceDescriptor;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbDescriptorsInterfaceNode extends UsbDescriptorsTreeNode {
    public final UsbInterfaceDescriptor mInterfaceDescriptor;
    public final ArrayList mEndpointNodes = new ArrayList();
    public final ArrayList mACInterfaceNodes = new ArrayList();

    public UsbDescriptorsInterfaceNode(UsbInterfaceDescriptor usbInterfaceDescriptor) {
        this.mInterfaceDescriptor = usbInterfaceDescriptor;
    }
}
