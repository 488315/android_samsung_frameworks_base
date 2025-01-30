package com.android.server.knox.dar;

import android.util.Log;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import java.security.cert.CertificateParsingException;
import java.util.Enumeration;

/* loaded from: classes2.dex */
public class AuthResult {
    public int mCallerAuthResult;
    public byte[] mCallingPackage;
    public int mCallingPackageAuthResult;
    public byte[] mCallingPackageSigs;

    public AuthResult(ASN1Primitive aSN1Primitive) {
        this.mCallerAuthResult = -1;
        this.mCallingPackage = new byte[]{0};
        this.mCallingPackageSigs = new byte[]{0};
        this.mCallingPackageAuthResult = -1;
        if (!(aSN1Primitive instanceof ASN1Sequence)) {
            throw new CertificateParsingException("Expected sequence for root of trust, found " + aSN1Primitive.getClass().getName());
        }
        Enumeration objects = ((ASN1Sequence) aSN1Primitive).getObjects();
        while (objects.hasMoreElements()) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) objects.nextElement();
            int tagNo = aSN1TaggedObject.getTagNo();
            if (tagNo == 0) {
                this.mCallerAuthResult = aSN1TaggedObject.getObject().getValue().intValue();
            } else if (tagNo == 1) {
                this.mCallingPackage = Asn1Utils.getByteArrayFromAsn1(aSN1TaggedObject);
            } else if (tagNo == 2) {
                this.mCallingPackageSigs = Asn1Utils.getByteArrayFromAsn1(aSN1TaggedObject);
            } else if (tagNo == 3) {
                this.mCallingPackageAuthResult = aSN1TaggedObject.getObject().getValue().intValue();
            } else {
                Log.e("AuthResult", "invalid tag no : " + aSN1TaggedObject.getTagNo());
            }
        }
    }

    public String statusToString(int i, boolean z) {
        return i != 0 ? i != 1 ? (i == 2 || z) ? "Not support" : Integer.toHexString(i) : "Abnormal" : "Normal";
    }

    public String toString() {
        return "        Caller Auth Result : " + statusToString(this.mCallerAuthResult, false) + "\n        Calling Package : " + new String(this.mCallingPackage) + "\n        Calling Package Signatures : " + new String(this.mCallingPackageSigs) + "\n        Calling Package Auth Result : " + statusToString(this.mCallingPackageAuthResult, true);
    }
}
