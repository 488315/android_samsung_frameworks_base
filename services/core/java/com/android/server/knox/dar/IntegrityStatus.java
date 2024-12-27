package com.android.server.knox.dar;

import android.util.Log;

import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.security.cert.CertificateParsingException;
import java.util.Enumeration;

public final class IntegrityStatus {
    public final AuthResult mAuthResult;
    public final int mIcd;
    public final int mKernelStatus;
    public final int mSystemStatus;
    public final int mTrustBoot;
    public final int mWarranty;

    public IntegrityStatus(ASN1Primitive aSN1Primitive) {
        this.mTrustBoot = -1;
        this.mWarranty = -1;
        this.mIcd = -1;
        this.mKernelStatus = -1;
        this.mSystemStatus = -1;
        this.mAuthResult = null;
        Enumeration objects = ((ASN1Sequence) aSN1Primitive).getObjects();
        while (objects.hasMoreElements()) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) objects.nextElement();
            int tagNo = aSN1TaggedObject.getTagNo();
            if (tagNo == 0) {
                this.mTrustBoot = aSN1TaggedObject.getObject().getValue().intValue();
            } else if (tagNo == 1) {
                this.mWarranty = aSN1TaggedObject.getObject().getValue().intValue();
            } else if (tagNo == 2) {
                this.mIcd = aSN1TaggedObject.getObject().getValue().intValue();
            } else if (tagNo == 3) {
                this.mKernelStatus = aSN1TaggedObject.getObject().getValue().intValue();
            } else if (tagNo == 4) {
                this.mSystemStatus = aSN1TaggedObject.getObject().getValue().intValue();
            } else if (tagNo != 5) {
                Log.e("IntegrityStatus", "invalid tag no : " + aSN1TaggedObject.getTagNo());
            } else {
                ASN1Sequence object = aSN1TaggedObject.getObject();
                AuthResult authResult = new AuthResult();
                authResult.mCallerAuthResult = -1;
                authResult.mCallingPackage = new byte[] {0};
                authResult.mCallingPackageSigs = new byte[] {0};
                authResult.mCallingPackageAuthResult = -1;
                if (!(object instanceof ASN1Sequence)) {
                    throw new CertificateParsingException(
                            "Expected sequence for root of trust, found "
                                    .concat(object.getClass().getName()));
                }
                Enumeration objects2 = object.getObjects();
                while (objects2.hasMoreElements()) {
                    ASN1TaggedObject aSN1TaggedObject2 = (ASN1TaggedObject) objects2.nextElement();
                    int tagNo2 = aSN1TaggedObject2.getTagNo();
                    if (tagNo2 == 0) {
                        authResult.mCallerAuthResult =
                                aSN1TaggedObject2.getObject().getValue().intValue();
                    } else if (tagNo2 == 1) {
                        authResult.mCallingPackage =
                                Asn1Utils.getByteArrayFromAsn1(aSN1TaggedObject2);
                    } else if (tagNo2 == 2) {
                        authResult.mCallingPackageSigs =
                                Asn1Utils.getByteArrayFromAsn1(aSN1TaggedObject2);
                    } else if (tagNo2 != 3) {
                        Log.e("AuthResult", "invalid tag no : " + aSN1TaggedObject2.getTagNo());
                    } else {
                        authResult.mCallingPackageAuthResult =
                                aSN1TaggedObject2.getObject().getValue().intValue();
                    }
                }
                this.mAuthResult = authResult;
            }
        }
    }

    public static String statusToString(int i) {
        return i != 0
                ? i != 1 ? i != 2 ? Integer.toHexString(i) : "Not support" : "Abnormal"
                : Constants.RLC_STATE_NORMAL;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("    TrustBoot : ");
        sb.append(statusToString(this.mTrustBoot));
        sb.append("\n    Warranty : ");
        sb.append(statusToString(this.mWarranty));
        sb.append("\n    ICD : ");
        sb.append(statusToString(this.mIcd));
        sb.append("\n    Kernel Status : ");
        sb.append(statusToString(this.mKernelStatus));
        sb.append("\n    System Status : ");
        sb.append(statusToString(this.mSystemStatus));
        sb.append("\n    Caller auth(with PROCA) Status : \n");
        AuthResult authResult = this.mAuthResult;
        sb.append(authResult == null ? "Not performed" : authResult.toString());
        return sb.toString();
    }
}
