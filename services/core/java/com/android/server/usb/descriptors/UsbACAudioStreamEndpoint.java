package com.android.server.usb.descriptors;

public final class UsbACAudioStreamEndpoint extends UsbACEndpoint {
    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        int i = byteStream.mReadCount;
        int i2 = this.mLength;
        byteStream.advance(i2 - i);
        return i2;
    }
}
