package com.android.internal.org.bouncycastle.its.asn1;

import com.android.internal.org.bouncycastle.asn1.ASN1Choice;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;

/* loaded from: classes5.dex */
public class SubjectPermissions extends ASN1Object implements ASN1Choice {
    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return null;
    }

    public static SubjectPermissions getInstance(Object obj) {
        if (obj instanceof SubjectPermissions) {
            return (SubjectPermissions) obj;
        }
        return null;
    }
}
