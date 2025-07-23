package com.android.internal.midi;

/* loaded from: classes5.dex */
public final class MidiConstants {
    public static final byte STATUS_ACTIVE_SENSING = -2;
    public static final byte STATUS_CHANNEL_MASK = 15;
    public static final byte STATUS_CHANNEL_PRESSURE = -48;
    public static final byte STATUS_COMMAND_MASK = -16;
    public static final byte STATUS_CONTINUE = -5;
    public static final byte STATUS_CONTROL_CHANGE = -80;
    public static final byte STATUS_END_SYSEX = -9;
    public static final byte STATUS_MIDI_TIME_CODE = -15;
    public static final byte STATUS_NOTE_OFF = Byte.MIN_VALUE;
    public static final byte STATUS_NOTE_ON = -112;
    public static final byte STATUS_PITCH_BEND = -32;
    public static final byte STATUS_POLYPHONIC_AFTERTOUCH = -96;
    public static final byte STATUS_PROGRAM_CHANGE = -64;
    public static final byte STATUS_RESET = -1;
    public static final byte STATUS_SONG_POSITION = -14;
    public static final byte STATUS_SONG_SELECT = -13;
    public static final byte STATUS_START = -6;
    public static final byte STATUS_STOP = -4;
    public static final byte STATUS_SYSTEM_EXCLUSIVE = -16;
    public static final byte STATUS_TIMING_CLOCK = -8;
    public static final byte STATUS_TUNE_REQUEST = -10;
    public static final int[] CHANNEL_BYTE_LENGTHS = {3, 3, 3, 3, 2, 2, 3};
    public static final int[] SYSTEM_BYTE_LENGTHS = {1, 2, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    public static boolean allowRunningStatus(byte b) {
        return b >= Byte.MIN_VALUE && b < -16;
    }

    public static boolean cancelsRunningStatus(byte b) {
        return b >= -16 && b <= -9;
    }

    public static int getBytesPerMessage(byte b) {
        int i = b & 255;
        if (i >= 240) {
            return SYSTEM_BYTE_LENGTHS[b & 15];
        }
        if (i >= 128) {
            return CHANNEL_BYTE_LENGTHS[(i >> 4) - 8];
        }
        return 0;
    }

    public static boolean isAllActiveSensing(byte[] bArr, int i, int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            if (bArr[i + i4] != -2) {
                i3++;
            }
        }
        return i3 == 0;
    }
}
