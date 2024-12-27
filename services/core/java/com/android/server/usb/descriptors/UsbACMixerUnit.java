package com.android.server.usb.descriptors;

public abstract class UsbACMixerUnit extends UsbACInterface {
    public byte[] mInputIDs;
    public byte mNumInputs;
    public byte mNumOutputs;
    public byte mUnitID;

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
