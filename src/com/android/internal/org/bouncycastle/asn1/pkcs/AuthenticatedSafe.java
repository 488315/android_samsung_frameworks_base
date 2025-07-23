package com.android.internal.org.bouncycastle.asn1.pkcs;

import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.BERSequence;
import com.android.internal.org.bouncycastle.asn1.DLSequence;

/* loaded from: classes5.dex */
public class AuthenticatedSafe extends ASN1Object {
    private ContentInfo[] info;
    private boolean isBer;

    private AuthenticatedSafe(ASN1Sequence aSN1Sequence) {
        this.isBer = true;
        this.info = new ContentInfo[aSN1Sequence.size()];
        int i = 0;
        while (true) {
            ContentInfo[] contentInfoArr = this.info;
            if (i != contentInfoArr.length) {
                contentInfoArr[i] = ContentInfo.getInstance(aSN1Sequence.getObjectAt(i));
                i++;
            } else {
                this.isBer = aSN1Sequence instanceof BERSequence;
                return;
            }
        }
    }

    public static AuthenticatedSafe getInstance(Object obj) {
        if (obj instanceof AuthenticatedSafe) {
            return (AuthenticatedSafe) obj;
        }
        if (obj != null) {
            return new AuthenticatedSafe(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public AuthenticatedSafe(ContentInfo[] contentInfoArr) {
        this.isBer = true;
        this.info = copy(contentInfoArr);
    }

    public ContentInfo[] getContentInfo() {
        return copy(this.info);
    }

    private ContentInfo[] copy(ContentInfo[] contentInfoArr) {
        int length = contentInfoArr.length;
        ContentInfo[] contentInfoArr2 = new ContentInfo[length];
        System.arraycopy(contentInfoArr, 0, contentInfoArr2, 0, length);
        return contentInfoArr2;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        if (this.isBer) {
            return new BERSequence(this.info);
        }
        return new DLSequence(this.info);
    }
}
