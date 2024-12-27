package com.android.server.usb.descriptors;

public final class UsbACAudioControlEndpoint extends UsbACEndpoint {
    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        byteStream.getByte();
        byteStream.getByte();
        byteStream.unpackUsbShort();
        byteStream.getByte();
        return this.mLength;
    }
}
