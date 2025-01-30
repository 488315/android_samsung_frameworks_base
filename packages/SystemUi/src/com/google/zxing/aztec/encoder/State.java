package com.google.zxing.aztec.encoder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class State {
    public static final State INITIAL_STATE = new State(Token.EMPTY, 0, 0, 0);
    public final int binaryShiftByteCount;
    public final int bitCount;
    public final int mode;
    public final Token token;

    private State(Token token, int i, int i2, int i3) {
        this.token = token;
        this.mode = i;
        this.binaryShiftByteCount = i2;
        this.bitCount = i3;
    }

    public final State addBinaryShiftChar(int i) {
        Token token = this.token;
        int i2 = this.mode;
        int i3 = this.bitCount;
        if (i2 == 4 || i2 == 2) {
            int[] iArr = HighLevelEncoder.LATCH_TABLE[i2];
            i2 = 0;
            int i4 = iArr[0];
            int i5 = 65535 & i4;
            int i6 = i4 >> 16;
            token.getClass();
            i3 += i6;
            token = new SimpleToken(token, i5, i6);
        }
        int i7 = this.binaryShiftByteCount;
        State state = new State(token, i2, i7 + 1, i3 + ((i7 == 0 || i7 == 31) ? 18 : i7 == 62 ? 9 : 8));
        return state.binaryShiftByteCount == 2078 ? state.endBinaryShift(i + 1) : state;
    }

    public final State endBinaryShift(int i) {
        int i2 = this.binaryShiftByteCount;
        if (i2 == 0) {
            return this;
        }
        Token token = this.token;
        token.getClass();
        return new State(new BinaryShiftToken(token, i - i2, i2), this.mode, 0, this.bitCount);
    }

    public final boolean isBetterThanOrEqualTo(State state) {
        int i;
        int i2 = this.bitCount + (HighLevelEncoder.LATCH_TABLE[this.mode][state.mode] >> 16);
        int i3 = state.binaryShiftByteCount;
        if (i3 > 0 && ((i = this.binaryShiftByteCount) == 0 || i > i3)) {
            i2 += 10;
        }
        return i2 <= state.bitCount;
    }

    public final State latchAndAppend(int i, int i2) {
        int i3 = this.bitCount;
        Token token = this.token;
        int i4 = this.mode;
        if (i != i4) {
            int i5 = HighLevelEncoder.LATCH_TABLE[i4][i];
            int i6 = 65535 & i5;
            int i7 = i5 >> 16;
            token.getClass();
            i3 += i7;
            token = new SimpleToken(token, i6, i7);
        }
        int i8 = i == 2 ? 4 : 5;
        token.getClass();
        return new State(new SimpleToken(token, i2, i8), i, 0, i3 + i8);
    }

    public final State shiftAndAppend(int i, int i2) {
        int i3 = this.mode;
        int i4 = i3 == 2 ? 4 : 5;
        int i5 = HighLevelEncoder.SHIFT_TABLE[i3][i];
        Token token = this.token;
        token.getClass();
        return new State(new SimpleToken(new SimpleToken(token, i5, i4), i2, 5), i3, 0, this.bitCount + i4 + 5);
    }

    public final String toString() {
        return String.format("%s bits=%d bytes=%d", HighLevelEncoder.MODE_NAMES[this.mode], Integer.valueOf(this.bitCount), Integer.valueOf(this.binaryShiftByteCount));
    }
}
