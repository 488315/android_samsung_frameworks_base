package com.android.server.usb.descriptors;

public final class Usb20ACMixerUnit extends UsbACMixerUnit implements UsbAudioChannelCluster {
    public byte[] mControls;

    @Override // com.android.server.usb.descriptors.UsbAudioChannelCluster
    public final byte getChannelCount() {
        return this.mNumOutputs;
    }

    @Override // com.android.server.usb.descriptors.UsbACMixerUnit,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        super.parseRawDescriptors(byteStream);
        byteStream.unpackUsbInt();
        byteStream.getByte();
        int i = ((this.mNumInputs * this.mNumOutputs) + 7) / 8;
        this.mControls = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mControls[i2] = byteStream.getByte();
        }
        byteStream.getByte();
        byteStream.getByte();
        return this.mLength;
    }
}
