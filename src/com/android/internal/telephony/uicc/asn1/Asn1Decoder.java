package com.android.internal.telephony.uicc.asn1;

import com.android.internal.telephony.uicc.IccUtils;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;

/* loaded from: classes4.dex */
public final class Asn1Decoder {
    private final int mEnd;
    private int mPosition;
    private final byte[] mSrc;

    public Asn1Decoder(String str) {
        this(IccUtils.hexStringToBytes(str));
    }

    public Asn1Decoder(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public Asn1Decoder(byte[] bArr, int i, int i2) {
        int i3;
        if (i < 0 || i2 < 0 || (i3 = i + i2) > bArr.length) {
            throw new IndexOutOfBoundsException("Out of the bounds: bytes=[" + bArr.length + "], offset=" + i + ", length=" + i2);
        }
        this.mSrc = bArr;
        this.mPosition = i;
        this.mEnd = i3;
    }

    public int getPosition() {
        return this.mPosition;
    }

    public boolean hasNextNode() {
        return this.mPosition < this.mEnd;
    }

    public Asn1Node nextNode() throws InvalidAsn1DataException {
        int i = this.mPosition;
        if (i >= this.mEnd) {
            throw new IllegalStateException("No bytes to parse.");
        }
        int i2 = i + 1;
        if ((this.mSrc[i] & SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN) == 31) {
            while (i2 < this.mEnd) {
                int i3 = this.mSrc[i2] & 128;
                i2++;
                if (i3 == 0) {
                    break;
                }
            }
        }
        if (i2 >= this.mEnd) {
            throw new InvalidAsn1DataException(0, "Invalid length at position: " + i2);
        }
        try {
            int bytesToInt = IccUtils.bytesToInt(this.mSrc, i, i2 - i);
            byte[] bArr = this.mSrc;
            int i4 = i2 + 1;
            int i5 = bArr[i2];
            if ((i5 & 128) != 0) {
                int i6 = i5 & 127;
                int i7 = i4 + i6;
                if (i7 > this.mEnd) {
                    throw new InvalidAsn1DataException(bytesToInt, "Cannot parse length at position: " + i4);
                }
                try {
                    i5 = IccUtils.bytesToInt(bArr, i4, i6);
                    i4 = i7;
                } catch (IllegalArgumentException e) {
                    throw new InvalidAsn1DataException(bytesToInt, "Cannot parse length at position: " + i4, e);
                }
            }
            int i8 = i4 + i5;
            if (i8 > this.mEnd) {
                throw new InvalidAsn1DataException(bytesToInt, "Incomplete data at position: " + i4 + ", expected bytes: " + i5 + ", actual bytes: " + (this.mEnd - i4));
            }
            Asn1Node asn1Node = new Asn1Node(bytesToInt, this.mSrc, i4, i5);
            this.mPosition = i8;
            return asn1Node;
        } catch (IllegalArgumentException e2) {
            throw new InvalidAsn1DataException(0, "Cannot parse tag at position: " + i, e2);
        }
    }
}
