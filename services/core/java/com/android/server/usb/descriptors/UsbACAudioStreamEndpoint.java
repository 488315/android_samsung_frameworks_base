package com.android.server.usb.descriptors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UsbACAudioStreamEndpoint extends UsbACEndpoint {
    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        int i = byteStream.mReadCount;
        int i2 = this.mLength;
        byteStream.advance(i2 - i);
        return i2;
    }
}
