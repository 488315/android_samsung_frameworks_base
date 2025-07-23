package com.android.internal.org.bouncycastle.crypto.macs;

import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.Mac;
import com.android.internal.org.bouncycastle.crypto.engines.Zuc256CoreEngine;

/* loaded from: classes5.dex */
public final class Zuc256Mac implements Mac {
    private static final int TOPBIT = 128;
    private int theByteIndex;
    private final InternalZuc256Engine theEngine;
    private final int[] theKeyStream;
    private final int[] theMac;
    private final int theMacLength;
    private Zuc256CoreEngine theState;
    private int theWordIndex;

    public Zuc256Mac(int i) {
        this.theEngine = new InternalZuc256Engine(i);
        this.theMacLength = i;
        int i2 = i / 32;
        this.theMac = new int[i2];
        this.theKeyStream = new int[i2 + 1];
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public String getAlgorithmName() {
        return "Zuc256Mac-" + this.theMacLength;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public int getMacSize() {
        return this.theMacLength / 8;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public void init(CipherParameters cipherParameters) {
        this.theEngine.init(true, cipherParameters);
        this.theState = (Zuc256CoreEngine) this.theEngine.copy();
        initKeyStream();
    }

    private void initKeyStream() {
        int i = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.theMac;
            if (i2 >= iArr.length) {
                break;
            }
            iArr[i2] = this.theEngine.createKeyStreamWord();
            i2++;
        }
        while (true) {
            int[] iArr2 = this.theKeyStream;
            if (i < iArr2.length - 1) {
                iArr2[i] = this.theEngine.createKeyStreamWord();
                i++;
            } else {
                this.theWordIndex = iArr2.length - 1;
                this.theByteIndex = 3;
                return;
            }
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public void update(byte b) {
        shift4NextByte();
        int i = this.theByteIndex * 8;
        int i2 = 128;
        int i3 = 0;
        while (i2 > 0) {
            if ((b & i2) != 0) {
                updateMac(i + i3);
            }
            i2 >>= 1;
            i3++;
        }
    }

    private void shift4NextByte() {
        int i = (this.theByteIndex + 1) % 4;
        this.theByteIndex = i;
        if (i == 0) {
            this.theKeyStream[this.theWordIndex] = this.theEngine.createKeyStreamWord();
            this.theWordIndex = (this.theWordIndex + 1) % this.theKeyStream.length;
        }
    }

    private void shift4Final() {
        int i = (this.theByteIndex + 1) % 4;
        this.theByteIndex = i;
        if (i == 0) {
            this.theWordIndex = (this.theWordIndex + 1) % this.theKeyStream.length;
        }
    }

    private void updateMac(int i) {
        int i2 = 0;
        while (true) {
            int[] iArr = this.theMac;
            if (i2 >= iArr.length) {
                return;
            }
            iArr[i2] = iArr[i2] ^ getKeyStreamWord(i2, i);
            i2++;
        }
    }

    private int getKeyStreamWord(int i, int i2) {
        int[] iArr = this.theKeyStream;
        int i3 = this.theWordIndex;
        int i4 = iArr[(i3 + i) % iArr.length];
        if (i2 == 0) {
            return i4;
        }
        int i5 = iArr[((i3 + i) + 1) % iArr.length];
        return (i5 >>> (32 - i2)) | (i4 << i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public void update(byte[] bArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            update(bArr[i + i3]);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public int doFinal(byte[] bArr, int i) {
        shift4Final();
        updateMac(this.theByteIndex * 8);
        int i2 = 0;
        while (true) {
            int[] iArr = this.theMac;
            if (i2 < iArr.length) {
                Zuc256CoreEngine.encode32be(iArr[i2], bArr, (i2 * 4) + i);
                i2++;
            } else {
                reset();
                return getMacSize();
            }
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public void reset() {
        Zuc256CoreEngine zuc256CoreEngine = this.theState;
        if (zuc256CoreEngine != null) {
            this.theEngine.reset(zuc256CoreEngine);
        }
        initKeyStream();
    }

    private static class InternalZuc256Engine extends Zuc256CoreEngine {
        public InternalZuc256Engine(int i) {
            super(i);
        }

        int createKeyStreamWord() {
            return super.makeKeyStreamWord();
        }
    }
}
