package com.android.server.usb.descriptors;

public final class UsbInterfaceAssoc extends UsbDescriptor {
    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        byteStream.getByte();
        byteStream.getByte();
        byteStream.getByte();
        byteStream.getByte();
        byteStream.getByte();
        byteStream.getByte();
        return this.mLength;
    }
}
