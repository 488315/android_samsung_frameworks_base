package com.google.zxing.aztec.encoder;

import java.nio.charset.StandardCharsets;

/* loaded from: classes4.dex */
public final class State {
    public static final State INITIAL_STATE = new State(Token.EMPTY, 0, 0, 0);
    public final int binaryShiftByteCount;
    public final int binaryShiftCost;
    public final int bitCount;
    public final int mode;
    public final Token token;

    private State(Token token, int i, int i2, int i3) {
        this.token = token;
        this.mode = i;
        this.binaryShiftByteCount = i2;
        this.bitCount = i3;
        this.binaryShiftCost = i2 > 62 ? 21 : i2 > 31 ? 20 : i2 > 0 ? 10 : 0;
    }

    public final State addBinaryShiftChar(int i) {
        Token simpleToken = this.token;
        int i2 = this.mode;
        int i3 = this.bitCount;
        if (i2 == 4 || i2 == 2) {
            int[] iArr = HighLevelEncoder.LATCH_TABLE[i2];
            i2 = 0;
            int i4 = iArr[0];
            int i5 = 65535 & i4;
            int i6 = i4 >> 16;
            simpleToken.getClass();
            i3 += i6;
            simpleToken = new SimpleToken(simpleToken, i5, i6);
        }
        int i7 = this.binaryShiftByteCount;
        State state = new State(simpleToken, i2, i7 + 1, i3 + ((i7 == 0 || i7 == 31) ? 18 : i7 == 62 ? 9 : 8));
        return state.binaryShiftByteCount == 2078 ? state.endBinaryShift(i + 1) : state;
    }

    public final State appendFLGn(int i) {
        SimpleToken simpleToken;
        Token token = shiftAndAppend(4, 0).token;
        int length = 3;
        if (i < 0) {
            token.getClass();
            simpleToken = new SimpleToken(token, 0, 3);
        } else {
            if (i > 999999) {
                throw new IllegalArgumentException("ECI code must be between 0 and 999999");
            }
            byte[] bytes = Integer.toString(i).getBytes(StandardCharsets.ISO_8859_1);
            int length2 = bytes.length;
            token.getClass();
            SimpleToken simpleToken2 = new SimpleToken(token, length2, 3);
            int length3 = bytes.length;
            int i2 = 0;
            while (i2 < length3) {
                SimpleToken simpleToken3 = new SimpleToken(simpleToken2, bytes[i2] - 46, 4);
                i2++;
                simpleToken2 = simpleToken3;
            }
            length = 3 + (bytes.length * 4);
            simpleToken = simpleToken2;
        }
        return new State(simpleToken, this.mode, 0, this.bitCount + length);
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
        int i = this.bitCount + (HighLevelEncoder.LATCH_TABLE[this.mode][state.mode] >> 16);
        int i2 = this.binaryShiftByteCount;
        int i3 = state.binaryShiftByteCount;
        if (i2 < i3) {
            i += state.binaryShiftCost - this.binaryShiftCost;
        } else if (i2 > i3 && i3 > 0) {
            i += 10;
        }
        return i <= state.bitCount;
    }

    public final State latchAndAppend(int i, int i2) {
        int i3 = this.bitCount;
        Token simpleToken = this.token;
        int i4 = this.mode;
        if (i != i4) {
            int i5 = HighLevelEncoder.LATCH_TABLE[i4][i];
            int i6 = 65535 & i5;
            int i7 = i5 >> 16;
            simpleToken.getClass();
            i3 += i7;
            simpleToken = new SimpleToken(simpleToken, i6, i7);
        }
        int i8 = i == 2 ? 4 : 5;
        simpleToken.getClass();
        return new State(new SimpleToken(simpleToken, i2, i8), i, 0, i3 + i8);
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
