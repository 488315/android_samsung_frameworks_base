package com.android.server.usb;

import android.util.Log;

import java.io.ByteArrayOutputStream;

public final class UsbMidiPacketConverter {
    public final ByteArrayOutputStream mEncoderOutputStream = new ByteArrayOutputStream();
    public UsbMidiDecoder mUsbMidiDecoder;
    public UsbMidiEncoder[] mUsbMidiEncoders;
    public static final int[] PAYLOAD_SIZE = {-1, -1, 2, 3, 3, 1, 2, 3, 3, 3, 3, 3, 2, 2, 3, 1};
    public static final int[] CODE_INDEX_NUMBER_FROM_SYSTEM_TYPE = {
        -1, 2, 3, 2, -1, -1, 5, -1, 5, -1, 5, 5, 5, -1, 5, 5
    };

    public final class UsbMidiDecoder {
        public ByteArrayOutputStream[] mDecodedByteArrays;
        public int mNumJacks;
    }

    public final class UsbMidiEncoder {
        public byte[] mEmptyBytes;
        public boolean mHasSystemExclusiveStarted;
        public int mNumStoredSystemExclusiveBytes;
        public byte mShiftedCableNumber;
        public byte[] mStoredSystemExclusiveBytes;

        public final void writeSingleByte(ByteArrayOutputStream byteArrayOutputStream, byte b) {
            byteArrayOutputStream.write(this.mShiftedCableNumber | 15);
            byteArrayOutputStream.write(b);
            byteArrayOutputStream.write(0);
            byteArrayOutputStream.write(0);
        }
    }

    public final void createEncoders(int i) {
        this.mUsbMidiEncoders = new UsbMidiEncoder[i];
        for (int i2 = 0; i2 < i; i2++) {
            UsbMidiEncoder[] usbMidiEncoderArr = this.mUsbMidiEncoders;
            UsbMidiEncoder usbMidiEncoder = new UsbMidiEncoder();
            usbMidiEncoder.mStoredSystemExclusiveBytes = new byte[3];
            usbMidiEncoder.mNumStoredSystemExclusiveBytes = 0;
            usbMidiEncoder.mHasSystemExclusiveStarted = false;
            usbMidiEncoder.mEmptyBytes = new byte[3];
            usbMidiEncoder.mShiftedCableNumber = (byte) (i2 << 4);
            usbMidiEncoderArr[i2] = usbMidiEncoder;
        }
    }

    public final void decodeMidiPackets(int i, byte[] bArr) {
        UsbMidiDecoder usbMidiDecoder = this.mUsbMidiDecoder;
        usbMidiDecoder.getClass();
        new ByteArrayOutputStream();
        if (i % 4 != 0) {
            Log.w("UsbMidiPacketConverter", "size " + i + " not multiple of 4");
        }
        for (int i2 = 0; i2 + 3 < i; i2 += 4) {
            byte b = bArr[i2];
            int i3 = (b >> 4) & 15;
            int i4 = PAYLOAD_SIZE[b & 15];
            if (i4 >= 0) {
                if (i3 >= usbMidiDecoder.mNumJacks) {
                    Log.w("UsbMidiPacketConverter", "cableNumber " + i3 + " invalid");
                    i3 = 0;
                }
                usbMidiDecoder.mDecodedByteArrays[i3].write(bArr, i2 + 1, i4);
            }
        }
    }

    public final void encodeMidiPackets(int i, int i2, byte[] bArr) {
        int i3;
        if (i2 >= this.mUsbMidiEncoders.length) {
            Log.w("UsbMidiPacketConverter", "encoderId " + i2 + " invalid");
            i2 = 0;
        }
        UsbMidiEncoder usbMidiEncoder = this.mUsbMidiEncoders[i2];
        usbMidiEncoder.getClass();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i4 = 0;
        while (i4 < i) {
            byte b = bArr[i4];
            byte b2 = usbMidiEncoder.mShiftedCableNumber;
            byte[] bArr2 = usbMidiEncoder.mStoredSystemExclusiveBytes;
            if (b < 0) {
                if (b != -9 && usbMidiEncoder.mHasSystemExclusiveStarted) {
                    for (int i5 = 0; i5 < usbMidiEncoder.mNumStoredSystemExclusiveBytes; i5++) {
                        usbMidiEncoder.writeSingleByte(byteArrayOutputStream, bArr2[i5]);
                    }
                    usbMidiEncoder.mNumStoredSystemExclusiveBytes = 0;
                    usbMidiEncoder.mHasSystemExclusiveStarted = false;
                }
                byte b3 = bArr[i4];
                int[] iArr = PAYLOAD_SIZE;
                byte[] bArr3 = usbMidiEncoder.mEmptyBytes;
                if (b3 < -16) {
                    byte b4 = (byte) ((b3 >> 4) & 15);
                    int i6 = iArr[b4];
                    i3 = i4 + i6;
                    if (i3 <= i) {
                        byteArrayOutputStream.write(b4 | b2);
                        byteArrayOutputStream.write(bArr, i4, i6);
                        byteArrayOutputStream.write(bArr3, 0, 3 - i6);
                        i4 = i3;
                    } else {
                        while (i4 < i) {
                            usbMidiEncoder.writeSingleByte(byteArrayOutputStream, bArr[i4]);
                            i4++;
                        }
                    }
                } else if (b3 == -16) {
                    usbMidiEncoder.mHasSystemExclusiveStarted = true;
                    bArr2[0] = b3;
                    usbMidiEncoder.mNumStoredSystemExclusiveBytes = 1;
                } else if (b3 == -9) {
                    byteArrayOutputStream.write(
                            (usbMidiEncoder.mNumStoredSystemExclusiveBytes + 5) | b2);
                    int i7 = usbMidiEncoder.mNumStoredSystemExclusiveBytes;
                    bArr2[i7] = bArr[i4];
                    int i8 = i7 + 1;
                    usbMidiEncoder.mNumStoredSystemExclusiveBytes = i8;
                    byteArrayOutputStream.write(bArr2, 0, i8);
                    byteArrayOutputStream.write(
                            bArr3, 0, 3 - usbMidiEncoder.mNumStoredSystemExclusiveBytes);
                    usbMidiEncoder.mHasSystemExclusiveStarted = false;
                    usbMidiEncoder.mNumStoredSystemExclusiveBytes = 0;
                } else {
                    int i9 = CODE_INDEX_NUMBER_FROM_SYSTEM_TYPE[b3 & 15];
                    if (i9 < 0) {
                        usbMidiEncoder.writeSingleByte(byteArrayOutputStream, b3);
                    } else {
                        int i10 = iArr[i9];
                        i3 = i4 + i10;
                        if (i3 <= i) {
                            byteArrayOutputStream.write(i9 | b2);
                            byteArrayOutputStream.write(bArr, i4, i10);
                            byteArrayOutputStream.write(bArr3, 0, 3 - i10);
                            i4 = i3;
                        } else {
                            while (i4 < i) {
                                usbMidiEncoder.writeSingleByte(byteArrayOutputStream, bArr[i4]);
                                i4++;
                            }
                        }
                    }
                }
            } else if (usbMidiEncoder.mHasSystemExclusiveStarted) {
                int i11 = usbMidiEncoder.mNumStoredSystemExclusiveBytes;
                bArr2[i11] = b;
                int i12 = i11 + 1;
                usbMidiEncoder.mNumStoredSystemExclusiveBytes = i12;
                if (i12 == 3) {
                    byteArrayOutputStream.write(b2 | 4);
                    byteArrayOutputStream.write(bArr2, 0, 3);
                    usbMidiEncoder.mNumStoredSystemExclusiveBytes = 0;
                }
            } else {
                usbMidiEncoder.writeSingleByte(byteArrayOutputStream, b);
            }
            i4++;
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        this.mEncoderOutputStream.write(byteArray, 0, byteArray.length);
    }

    public final byte[] pullDecodedMidiPackets(int i) {
        UsbMidiDecoder usbMidiDecoder = this.mUsbMidiDecoder;
        if (i >= usbMidiDecoder.mNumJacks) {
            Log.w("UsbMidiPacketConverter", "cableNumber " + i + " invalid");
            i = 0;
        }
        byte[] byteArray = usbMidiDecoder.mDecodedByteArrays[i].toByteArray();
        usbMidiDecoder.mDecodedByteArrays[i].reset();
        return byteArray;
    }
}
