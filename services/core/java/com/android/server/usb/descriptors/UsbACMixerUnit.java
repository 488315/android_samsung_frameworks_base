package com.android.server.usb.descriptors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class UsbACMixerUnit extends UsbACInterface {
    public byte[] mInputIDs;
    public byte mNumInputs;
    public byte mNumOutputs;
    public byte mUnitID;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(ByteStream byteStream) {
        this.mUnitID = byteStream.getByte();
        int i = byteStream.getByte();
        this.mNumInputs = i;
        this.mInputIDs = new byte[i];
        for (int i2 = 0; i2 < this.mNumInputs; i2++) {
            this.mInputIDs[i2] = byteStream.getByte();
        }
        this.mNumOutputs = byteStream.getByte();
        return this.mLength;
    }
}
