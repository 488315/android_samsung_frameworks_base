package com.android.server.usb.descriptors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbACSelectorUnit extends UsbACInterface {
    public byte mNumPins;
    public byte[] mSourceIDs;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        byteStream.getByte();
        int i = byteStream.getByte();
        this.mNumPins = i;
        this.mSourceIDs = new byte[i];
        for (int i2 = 0; i2 < this.mNumPins; i2++) {
            this.mSourceIDs[i2] = byteStream.getByte();
        }
        byteStream.getByte();
        return this.mLength;
    }
}
