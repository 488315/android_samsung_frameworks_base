package com.android.server.usb.descriptors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
