package com.android.internal.org.bouncycastle.crypto.digests;

import com.android.internal.midi.MidiConstants;
import com.android.internal.org.bouncycastle.crypto.Digest;
import com.android.internal.org.bouncycastle.util.Bytes;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;

/* loaded from: classes5.dex */
public abstract class HarakaBase implements Digest {
    protected static final int DIGEST_SIZE = 32;
    static final byte[][] RC = {new byte[]{-99, 123, -127, 117, -16, -2, -59, -78, 10, MidiConstants.STATUS_PROGRAM_CHANGE, 32, -26, 76, SprAttributeBase.TYPE_SHADOW, -124, 6}, new byte[]{23, -9, 8, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, -92, 107, 15, 100, 107, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, MidiConstants.STATUS_SONG_SELECT, -120, -31, -76, 102, -117}, new byte[]{20, -111, 2, -97, SprAttributeBase.TYPE_DURATION, -99, 2, -49, -104, -124, MidiConstants.STATUS_SONG_POSITION, 83, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -34, 2, 52}, new byte[]{121, 79, 91, -3, -81, -68, MidiConstants.STATUS_SONG_SELECT, -69, 8, 79, 123, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, -26, -22, -42, 14}, new byte[]{68, SprAttributeBase.TYPE_SHADOW, 57, -66, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, -51, -18, 121, -117, 68, 114, 72, -53, MidiConstants.STATUS_CONTROL_CHANGE, -49, -53}, new byte[]{123, 5, -118, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, -19, 53, 83, -115, -73, 50, MidiConstants.STATUS_NOTE_ON, 110, -18, -51, -22, 126}, new byte[]{27, -17, 79, -38, SprAttributeBase.TYPE_ANIMATOR_SET, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, 65, -30, MidiConstants.STATUS_CHANNEL_PRESSURE, 124, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, 94, 67, -113, -62, 103}, new byte[]{59, 11, -57, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, -30, -3, 95, 103, 7, -52, -54, -81, MidiConstants.STATUS_CONTROL_CHANGE, -39, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, 41}, new byte[]{-18, 101, -44, -71, -54, -113, -37, -20, -23, Byte.MAX_VALUE, -122, -26, MidiConstants.STATUS_MIDI_TIME_CODE, 99, 77, -85}, new byte[]{51, 126, 3, -83, 79, 64, SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33, 91, 100, -51, -73, -44, -124, -65, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN}, new byte[]{0, -104, -10, -115, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, -117, 2, 105, -65, 35, 23, -108, -71, 11, -52, -78}, new byte[]{-118, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -99, 92, -56, -98, -86, 74, 114, 85, 111, -34, -90, 120, 4, -6}, new byte[]{-44, -97, 18, 41, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, 79, -6, 14, 18, SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33, 119, 107, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, -97, -76, -33}, new byte[]{-18, 18, 106, -69, -82, 17, -42, 50, 54, -94, 73, -12, 68, 3, -95, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT}, new byte[]{-90, -20, -88, -100, -55, 0, -106, 95, -124, 0, 5, 75, -120, 73, 4, -81}, new byte[]{-20, -109, -27, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, -29, -57, -94, 120, 79, -100, 25, -99, -40, 94, 2, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT}, new byte[]{115, 1, -44, -126, -51, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, 40, -71, -73, -55, 89, -89, -8, -86, 58, -65}, new byte[]{107, 125, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, 16, -39, -17, MidiConstants.STATUS_SONG_POSITION, 55, 23, MidiConstants.STATUS_CONTROL_CHANGE, -122, SprAttributeBase.TYPE_ANIMATOR_SET, 13, SprAttributeBase.TYPE_SHADOW, SprAttributeBase.TYPE_DURATION, 98}, new byte[]{-58, -102, -4, -10, 83, -111, -62, -127, 67, 4, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -62, 69, -54, 90}, new byte[]{58, -108, -47, 54, -24, -110, -81, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50, -69, 104, 107, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, 60, -105, 35, -110}, new byte[]{-76, 113, 16, -27, 88, -71, -70, 108, -21, -122, 88, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, 56, -110, -65, -45}, new byte[]{-115, 18, -31, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -35, -3, 61, -109, 119, -58, -16, -82, -27, 60, -122, -37}, new byte[]{-79, 18, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, -53, -29, -115, -28, -125, -100, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, -21, -1, 104, 98, SprAttributeBase.TYPE_DURATION, -69}, new byte[]{125, -9, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, -57, 78, 26, -71, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -100, -47, -28, -30, -36, -45, 75, 115}, new byte[]{78, -110, -77, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50, -60, 21, 20, 75, 67, 27, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, SprAttributeBase.TYPE_ANIMATOR_SET, -61, 71, -69, 67}, new byte[]{-103, 104, -21, 22, -35, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33, -78, 3, -10, -17, 7, -25, -88, 117, -89, -37}, new byte[]{SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50, 71, -54, 126, 2, 35, 94, -114, 119, 89, 117, 60, 75, SprAttributeBase.TYPE_ANIMATOR_SET, MidiConstants.STATUS_SONG_SELECT, 109}, new byte[]{-7, 23, -122, -72, -71, -27, 27, 109, 119, 125, -34, -42, 23, 90, -89, -51}, new byte[]{93, -18, 70, -87, -99, 6, 108, -99, -86, -23, -88, 107, -16, 67, 107, -20}, new byte[]{-63, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, MidiConstants.STATUS_SONG_SELECT, 59, 89, 17, 83, -94, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, 51, 87, -7, 80, 105, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT, -53}, new byte[]{-39, MidiConstants.STATUS_CHANNEL_PRESSURE, 14, SprAttributeBase.TYPE_DURATION, 83, 3, -19, -28, -100, SprAttributeBase.TYPE_ANIMATOR_SET, -38, 0, 117, 12, -18, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50}, new byte[]{80, -93, -92, 99, -68, -70, -69, Byte.MIN_VALUE, -85, 12, -23, -106, -95, -91, -79, -16}, new byte[]{57, -54, -115, -109, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, -34, 13, -85, -120, 41, -106, 94, 2, -79, 61, -82}, new byte[]{66, -76, 117, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, -88, MidiConstants.STATUS_SONG_SELECT, 20, -120, 11, -92, 84, -43, 56, -113, -69, 23}, new byte[]{-10, 22, 10, 54, 121, -73, -74, -82, -41, Byte.MAX_VALUE, 66, 95, 91, -118, -69, 52}, new byte[]{-34, -81, -70, -1, 24, 89, -50, 67, 56, 84, -27, -53, 65, 82, -10, 38}, new byte[]{120, -55, -98, -125, -9, -100, -54, -94, 106, 2, MidiConstants.STATUS_SONG_SELECT, -71, 84, -102, -23, 76}, new byte[]{53, 18, MidiConstants.STATUS_NOTE_ON, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, 40, 110, MidiConstants.STATUS_PROGRAM_CHANGE, 64, -66, -9, -33, 27, 26, -91, 81, -82}, new byte[]{-49, 89, -90, 72, 15, -68, 115, -63, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, -46, 126, -70, 60, SprAttributeBase.TYPE_ANIMATOR_SET, -63, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH}, new byte[]{-95, -99, -59, -23, -3, -67, -42, 74, -120, -126, 40, 2, 3, -52, 106, 117}};
    private static final byte[][] S = {new byte[]{99, 124, 119, 123, MidiConstants.STATUS_SONG_POSITION, 107, 111, -59, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, 1, 103, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, -2, -41, -85, 118}, new byte[]{-54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, MidiConstants.STATUS_PROGRAM_CHANGE}, new byte[]{-73, -3, -109, 38, 54, 63, -9, -52, 52, -91, -27, MidiConstants.STATUS_MIDI_TIME_CODE, 113, -40, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33, 21}, new byte[]{4, -57, 35, -61, 24, -106, 5, -102, 7, 18, Byte.MIN_VALUE, -30, -21, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, -78, 117}, new byte[]{9, -125, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50, 26, 27, 110, 90, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 82, 59, -42, -77, 41, -29, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, -124}, new byte[]{83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49}, new byte[]{MidiConstants.STATUS_CHANNEL_PRESSURE, -17, -86, -5, 67, 77, 51, -123, 69, -7, 2, Byte.MAX_VALUE, 80, 60, -97, -88}, new byte[]{81, -93, 64, -113, -110, -99, 56, -11, -68, -74, -38, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, 16, -1, MidiConstants.STATUS_SONG_SELECT, -46}, new byte[]{-51, 12, 19, -20, 95, -105, 68, 23, -60, -89, 126, 61, 100, 93, 25, 115}, new byte[]{SprAttributeBase.TYPE_DURATION, -127, 79, -36, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33, MidiConstants.STATUS_NOTE_ON, -120, 70, -18, -72, 20, -34, 94, 11, -37}, new byte[]{MidiConstants.STATUS_PITCH_BEND, 50, 58, 10, 73, 6, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, 92, -62, -45, -84, 98, -111, -107, -28, 121}, new byte[]{-25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8}, new byte[]{-70, 120, 37, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, -90, -76, -58, -24, -35, 116, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, 75, -67, -117, -118}, new byte[]{SprAttributeBase.TYPE_SHADOW, 62, -75, 102, 72, 3, -10, 14, SprAttributeBase.TYPE_ANIMATOR_SET, 53, 87, -71, -122, -63, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT, -98}, new byte[]{-31, -8, -104, 17, 105, -39, -114, -108, -101, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT, -121, -23, -50, 85, 40, -33}, new byte[]{-116, -95, -119, 13, -65, -26, 66, 104, 65, -103, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 15, MidiConstants.STATUS_CONTROL_CHANGE, 84, -69, 22}};

    static byte mulX(byte b) {
        return (byte) ((((b & 128) >> 7) * 27) ^ ((b & Byte.MAX_VALUE) << 1));
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 32;
    }

    static byte sBox(byte b) {
        return S[(b & 255) >>> 4][b & 15];
    }

    static byte[] subBytes(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        bArr2[0] = sBox(bArr[0]);
        bArr2[1] = sBox(bArr[1]);
        bArr2[2] = sBox(bArr[2]);
        bArr2[3] = sBox(bArr[3]);
        bArr2[4] = sBox(bArr[4]);
        bArr2[5] = sBox(bArr[5]);
        bArr2[6] = sBox(bArr[6]);
        bArr2[7] = sBox(bArr[7]);
        bArr2[8] = sBox(bArr[8]);
        bArr2[9] = sBox(bArr[9]);
        bArr2[10] = sBox(bArr[10]);
        bArr2[11] = sBox(bArr[11]);
        bArr2[12] = sBox(bArr[12]);
        bArr2[13] = sBox(bArr[13]);
        bArr2[14] = sBox(bArr[14]);
        bArr2[15] = sBox(bArr[15]);
        return bArr2;
    }

    static byte[] shiftRows(byte[] bArr) {
        return new byte[]{bArr[0], bArr[5], bArr[10], bArr[15], bArr[4], bArr[9], bArr[14], bArr[3], bArr[8], bArr[13], bArr[2], bArr[7], bArr[12], bArr[1], bArr[6], bArr[11]};
    }

    static byte[] aesEnc(byte[] bArr, byte[] bArr2) {
        byte[] bArrMixColumns = mixColumns(shiftRows(subBytes(bArr)));
        Bytes.xorTo(16, bArr2, bArrMixColumns);
        return bArrMixColumns;
    }

    private static byte[] mixColumns(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            int i3 = i2 * 4;
            int i4 = i3 + 1;
            int i5 = i3 + 2;
            int i6 = i3 + 3;
            bArr2[i] = (byte) ((((mulX(bArr[i3]) ^ mulX(bArr[i4])) ^ bArr[i4]) ^ bArr[i5]) ^ bArr[i6]);
            bArr2[i + 1] = (byte) ((((bArr[i3] ^ mulX(bArr[i4])) ^ mulX(bArr[i5])) ^ bArr[i5]) ^ bArr[i6]);
            int i7 = i + 3;
            bArr2[i + 2] = (byte) ((((bArr[i3] ^ bArr[i4]) ^ mulX(bArr[i5])) ^ mulX(bArr[i6])) ^ bArr[i6]);
            i += 4;
            bArr2[i7] = (byte) ((((mulX(bArr[i3]) ^ bArr[i3]) ^ bArr[i4]) ^ bArr[i5]) ^ mulX(bArr[i6]));
        }
        return bArr2;
    }
}
