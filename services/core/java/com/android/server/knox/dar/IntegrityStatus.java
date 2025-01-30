package com.android.server.knox.dar;

import android.util.Log;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import java.util.Enumeration;

/* loaded from: classes2.dex */
public class IntegrityStatus {
    public AuthResult mAuthResult;
    public int mIcd;
    public int mKernelStatus;
    public int mSystemStatus;
    public int mTrustBoot;
    public int mWarranty;

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
            } else if (tagNo == 5) {
                this.mAuthResult = new AuthResult(aSN1TaggedObject.getObject());
            } else {
                Log.e("IntegrityStatus", "invalid tag no : " + aSN1TaggedObject.getTagNo());
            }
        }
    }

    public int getTrustBoot() {
        return this.mTrustBoot;
    }

    public int getWarranty() {
        return this.mWarranty;
    }

    public String statusToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? Integer.toHexString(i) : "Not support" : "Abnormal" : "Normal";
    }

    public String toString() {
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
