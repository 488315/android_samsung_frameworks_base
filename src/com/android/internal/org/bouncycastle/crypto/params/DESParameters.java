package com.android.internal.org.bouncycastle.crypto.params;

import com.android.internal.midi.MidiConstants;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;

/* loaded from: classes5.dex */
public class DESParameters extends KeyParameter {
    public static final int DES_KEY_LENGTH = 8;
    private static byte[] DES_weak_keys = {1, 1, 1, 1, 1, 1, 1, 1, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, 14, 14, 14, 14, MidiConstants.STATUS_PITCH_BEND, MidiConstants.STATUS_PITCH_BEND, MidiConstants.STATUS_PITCH_BEND, MidiConstants.STATUS_PITCH_BEND, MidiConstants.STATUS_MIDI_TIME_CODE, MidiConstants.STATUS_MIDI_TIME_CODE, MidiConstants.STATUS_MIDI_TIME_CODE, MidiConstants.STATUS_MIDI_TIME_CODE, -2, -2, -2, -2, -2, -2, -2, -2, 1, -2, 1, -2, 1, -2, 1, -2, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, MidiConstants.STATUS_PITCH_BEND, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, MidiConstants.STATUS_PITCH_BEND, 14, MidiConstants.STATUS_MIDI_TIME_CODE, 14, MidiConstants.STATUS_MIDI_TIME_CODE, 1, MidiConstants.STATUS_PITCH_BEND, 1, MidiConstants.STATUS_PITCH_BEND, 1, MidiConstants.STATUS_MIDI_TIME_CODE, 1, MidiConstants.STATUS_MIDI_TIME_CODE, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, -2, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, -2, 14, -2, 14, -2, 1, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, 1, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, 1, 14, 1, 14, MidiConstants.STATUS_PITCH_BEND, -2, MidiConstants.STATUS_PITCH_BEND, -2, MidiConstants.STATUS_MIDI_TIME_CODE, -2, MidiConstants.STATUS_MIDI_TIME_CODE, -2, -2, 1, -2, 1, -2, 1, -2, 1, MidiConstants.STATUS_PITCH_BEND, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, MidiConstants.STATUS_PITCH_BEND, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, MidiConstants.STATUS_MIDI_TIME_CODE, 14, MidiConstants.STATUS_MIDI_TIME_CODE, 14, MidiConstants.STATUS_PITCH_BEND, 1, MidiConstants.STATUS_PITCH_BEND, 1, MidiConstants.STATUS_MIDI_TIME_CODE, 1, MidiConstants.STATUS_MIDI_TIME_CODE, 1, -2, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, -2, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, -2, 14, -2, 14, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, 1, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, 1, 14, 1, 14, 1, -2, MidiConstants.STATUS_PITCH_BEND, -2, MidiConstants.STATUS_PITCH_BEND, -2, MidiConstants.STATUS_MIDI_TIME_CODE, -2, MidiConstants.STATUS_MIDI_TIME_CODE};
    private static final int N_DES_WEAK_KEYS = 16;

    public DESParameters(byte[] bArr) {
        super(bArr);
        if (isWeakKey(bArr, 0)) {
            throw new IllegalArgumentException("attempt to create weak DES key");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001c, code lost:
    
        r2 = r2 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isWeakKey(byte[] bArr, int i) {
        if (bArr.length - i < 8) {
            throw new IllegalArgumentException("key material too short.");
        }
        int i2 = 0;
        while (i2 < 16) {
            for (int i3 = 0; i3 < 8; i3++) {
                if (bArr[i3 + i] != DES_weak_keys[(i2 * 8) + i3]) {
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public static void setOddParity(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            bArr[i] = (byte) (((((b >> 7) ^ ((((((b >> 1) ^ (b >> 2)) ^ (b >> 3)) ^ (b >> 4)) ^ (b >> 5)) ^ (b >> 6))) ^ 1) & 1) | (b & MidiConstants.STATUS_ACTIVE_SENSING));
        }
    }
}
