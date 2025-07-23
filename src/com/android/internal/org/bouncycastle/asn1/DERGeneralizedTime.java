package com.android.internal.org.bouncycastle.asn1;

import com.android.internal.org.bouncycastle.util.Strings;
import java.io.IOException;
import java.util.Date;

/* loaded from: classes5.dex */
public class DERGeneralizedTime extends ASN1GeneralizedTime {
    @Override // com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDERObject() {
        return this;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDLObject() {
        return this;
    }

    public DERGeneralizedTime(byte[] bArr) {
        super(bArr);
    }

    public DERGeneralizedTime(Date date) {
        super(date);
    }

    public DERGeneralizedTime(String str) {
        super(str);
    }

    private byte[] getDERTime() {
        if (this.contents[this.contents.length - 1] == 90) {
            if (!hasMinutes()) {
                byte[] bArr = new byte[this.contents.length + 4];
                System.arraycopy(this.contents, 0, bArr, 0, this.contents.length - 1);
                System.arraycopy(Strings.toByteArray("0000Z"), 0, bArr, this.contents.length - 1, 5);
                return bArr;
            }
            if (!hasSeconds()) {
                byte[] bArr2 = new byte[this.contents.length + 2];
                System.arraycopy(this.contents, 0, bArr2, 0, this.contents.length - 1);
                System.arraycopy(Strings.toByteArray("00Z"), 0, bArr2, this.contents.length - 1, 3);
                return bArr2;
            }
            if (hasFractionalSeconds()) {
                int length = this.contents.length - 2;
                while (length > 0 && this.contents[length] == 48) {
                    length--;
                }
                if (this.contents[length] == 46) {
                    byte[] bArr3 = new byte[length + 1];
                    System.arraycopy(this.contents, 0, bArr3, 0, length);
                    bArr3[length] = 90;
                    return bArr3;
                }
                byte[] bArr4 = new byte[length + 2];
                int i = length + 1;
                System.arraycopy(this.contents, 0, bArr4, 0, i);
                bArr4[i] = 90;
                return bArr4;
            }
            return this.contents;
        }
        return this.contents;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength(boolean z) {
        return ASN1OutputStream.getLengthOfEncodingDL(z, getDERTime().length);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(ASN1OutputStream aSN1OutputStream, boolean z) throws IOException {
        aSN1OutputStream.writeEncodingDL(z, 24, getDERTime());
    }
}
