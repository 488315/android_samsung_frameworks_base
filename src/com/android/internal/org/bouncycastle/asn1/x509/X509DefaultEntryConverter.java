package com.android.internal.org.bouncycastle.asn1.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.DERGeneralizedTime;
import com.android.internal.org.bouncycastle.asn1.DERIA5String;
import com.android.internal.org.bouncycastle.asn1.DERPrintableString;
import com.android.internal.org.bouncycastle.asn1.DERUTF8String;
import com.android.internal.org.bouncycastle.asn1.x500.style.BCStyle;
import java.io.IOException;

/* loaded from: classes5.dex */
public class X509DefaultEntryConverter extends X509NameEntryConverter {
    @Override // com.android.internal.org.bouncycastle.asn1.x509.X509NameEntryConverter
    public ASN1Primitive getConvertedValue(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        if (str.length() != 0 && str.charAt(0) == '#') {
            try {
                return convertHexEncoded(str, 1);
            } catch (IOException unused) {
                throw new RuntimeException("can't recode value for oid " + aSN1ObjectIdentifier.getId());
            }
        }
        if (str.length() != 0 && str.charAt(0) == '\\') {
            str = str.substring(1);
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) BCStyle.EmailAddress) || aSN1ObjectIdentifier.equals((ASN1Primitive) BCStyle.DC)) {
            return new DERIA5String(str);
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) BCStyle.DATE_OF_BIRTH)) {
            return new DERGeneralizedTime(str);
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) BCStyle.C) || aSN1ObjectIdentifier.equals((ASN1Primitive) BCStyle.SERIALNUMBER) || aSN1ObjectIdentifier.equals((ASN1Primitive) BCStyle.DN_QUALIFIER) || aSN1ObjectIdentifier.equals((ASN1Primitive) BCStyle.TELEPHONE_NUMBER)) {
            return new DERPrintableString(str);
        }
        return new DERUTF8String(str);
    }
}
