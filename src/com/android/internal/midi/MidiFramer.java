package com.android.internal.midi;

import android.media.midi.MidiReceiver;
import java.io.IOException;

/* loaded from: classes5.dex */
public class MidiFramer extends MidiReceiver {
    public String TAG = "MidiFramer";
    private byte[] mBuffer = new byte[3];
    private int mCount;
    private boolean mInSysEx;
    private int mNeeded;
    private MidiReceiver mReceiver;
    private byte mRunningStatus;

    public MidiFramer(MidiReceiver midiReceiver) {
        this.mReceiver = midiReceiver;
    }

    public static String formatMidiData(byte[] bArr, int i, int i2) {
        String str = "MIDI+" + i + " : ";
        for (int i3 = 0; i3 < i2; i3++) {
            str = str + String.format("0x%02X, ", Byte.valueOf(bArr[i + i3]));
        }
        return str;
    }

    @Override // android.media.midi.MidiReceiver
    public void onSend(byte[] bArr, int i, int i2, long j) throws IOException {
        int i3 = i;
        int i4 = this.mInSysEx ? i : -1;
        for (int i5 = 0; i5 < i2; i5++) {
            byte b = bArr[i3];
            int i6 = b & 255;
            if (i6 >= 128) {
                if (i6 < 240) {
                    this.mRunningStatus = b;
                    this.mCount = 1;
                    this.mNeeded = MidiConstants.getBytesPerMessage(b) - 1;
                } else if (i6 >= 248) {
                    if (this.mInSysEx) {
                        this.mReceiver.send(bArr, i4, i3 - i4, j);
                        i4 = i3 + 1;
                    }
                    this.mReceiver.send(bArr, i3, 1, j);
                    i4 = i4;
                } else if (i6 == 240) {
                    this.mInSysEx = true;
                    i4 = i3;
                } else if (i6 == 247) {
                    if (this.mInSysEx) {
                        this.mReceiver.send(bArr, i4, (i3 - i4) + 1, j);
                        this.mInSysEx = false;
                        i4 = -1;
                    }
                } else {
                    this.mBuffer[0] = b;
                    this.mRunningStatus = (byte) 0;
                    this.mCount = 1;
                    this.mNeeded = MidiConstants.getBytesPerMessage(b) - 1;
                }
            } else if (this.mInSysEx) {
                continue;
            } else {
                int i7 = this.mNeeded;
                if (i7 <= 0) {
                    break;
                }
                byte[] bArr2 = this.mBuffer;
                int i8 = this.mCount;
                int i9 = i8 + 1;
                this.mCount = i9;
                bArr2[i8] = b;
                int i10 = i7 - 1;
                this.mNeeded = i10;
                if (i10 == 0) {
                    byte b2 = this.mRunningStatus;
                    if (b2 != 0) {
                        bArr2[0] = b2;
                    }
                    this.mReceiver.send(bArr2, 0, i9, j);
                    this.mNeeded = MidiConstants.getBytesPerMessage(this.mBuffer[0]) - 1;
                    this.mCount = 1;
                }
            }
            i3++;
        }
        if (i4 < 0 || i4 >= i3) {
            return;
        }
        this.mReceiver.send(bArr, i4, i3 - i4, j);
    }
}
