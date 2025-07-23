package com.android.internal.org.bouncycastle.asn1.x509;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.org.bouncycastle.asn1.ASN1Boolean;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class BasicConstraints extends ASN1Object {
    ASN1Boolean cA;
    ASN1Integer pathLenConstraint;

    public static BasicConstraints getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static BasicConstraints getInstance(Object obj) {
        if (obj instanceof BasicConstraints) {
            return (BasicConstraints) obj;
        }
        if (obj instanceof X509Extension) {
            return getInstance(X509Extension.convertValueToObject((X509Extension) obj));
        }
        if (obj != null) {
            return new BasicConstraints(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static BasicConstraints fromExtensions(Extensions extensions) {
        return getInstance(Extensions.getExtensionParsedValue(extensions, Extension.basicConstraints));
    }

    private BasicConstraints(ASN1Sequence aSN1Sequence) {
        this.cA = ASN1Boolean.getInstance(false);
        this.pathLenConstraint = null;
        if (aSN1Sequence.size() == 0) {
            this.cA = null;
            this.pathLenConstraint = null;
            return;
        }
        if (aSN1Sequence.getObjectAt(0) instanceof ASN1Boolean) {
            this.cA = ASN1Boolean.getInstance(aSN1Sequence.getObjectAt(0));
        } else {
            this.cA = null;
            this.pathLenConstraint = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
        }
        if (aSN1Sequence.size() > 1) {
            if (this.cA != null) {
                this.pathLenConstraint = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1));
                return;
            }
            throw new IllegalArgumentException("wrong sequence in constructor");
        }
    }

    public BasicConstraints(boolean z) {
        this.cA = ASN1Boolean.getInstance(false);
        this.pathLenConstraint = null;
        if (z) {
            this.cA = ASN1Boolean.getInstance(true);
        } else {
            this.cA = null;
        }
        this.pathLenConstraint = null;
    }

    public BasicConstraints(int i) {
        this.cA = ASN1Boolean.getInstance(false);
        this.pathLenConstraint = null;
        this.cA = ASN1Boolean.getInstance(true);
        this.pathLenConstraint = new ASN1Integer(i);
    }

    public boolean isCA() {
        ASN1Boolean aSN1Boolean = this.cA;
        return aSN1Boolean != null && aSN1Boolean.isTrue();
    }

    public BigInteger getPathLenConstraint() {
        ASN1Integer aSN1Integer = this.pathLenConstraint;
        if (aSN1Integer != null) {
            return aSN1Integer.getValue();
        }
        return null;
    }

    public ASN1Integer getPathLenConstraintInteger() {
        return this.pathLenConstraint;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        ASN1Boolean aSN1Boolean = this.cA;
        if (aSN1Boolean != null) {
            aSN1EncodableVector.add(aSN1Boolean);
        }
        ASN1Integer aSN1Integer = this.pathLenConstraint;
        if (aSN1Integer != null) {
            aSN1EncodableVector.add(aSN1Integer);
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        if (this.pathLenConstraint == null) {
            return "BasicConstraints: isCa(" + isCA() + NavigationBarInflaterView.KEY_CODE_END;
        }
        return "BasicConstraints: isCa(" + isCA() + "), pathLenConstraint = " + this.pathLenConstraint.getValue();
    }
}
