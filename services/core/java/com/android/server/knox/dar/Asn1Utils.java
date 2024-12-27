package com.android.server.knox.dar;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.DERPrintableString;

import java.security.cert.CertificateParsingException;

public abstract class Asn1Utils {
    public static ASN1Sequence getAsn1SequenceFromStream(ASN1InputStream aSN1InputStream) {
        ASN1OctetString readObject = aSN1InputStream.readObject();
        if (!(readObject instanceof ASN1OctetString)) {
            throw new CertificateParsingException(
                    "Expected octet stream, found ".concat(readObject.getClass().getName()));
        }
        ASN1InputStream aSN1InputStream2 = new ASN1InputStream(readObject.getOctets());
        try {
            ASN1Sequence readObject2 = aSN1InputStream2.readObject();
            if (!(readObject2 instanceof ASN1Sequence)) {
                throw new CertificateParsingException(
                        "Expected sequence, found ".concat(readObject2.getClass().getName()));
            }
            ASN1Sequence aSN1Sequence = readObject2;
            aSN1InputStream2.close();
            return aSN1Sequence;
        } catch (Throwable th) {
            try {
                aSN1InputStream2.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static byte[] getByteArrayFromAsn1(ASN1Encodable aSN1Encodable) {
        ASN1OctetString object = ((ASN1TaggedObject) aSN1Encodable).getObject();
        if (object instanceof DEROctetString) {
            return object.getOctets();
        }
        if (object instanceof DERPrintableString) {
            return ((DERPrintableString) object).getOctets();
        }
        throw new CertificateParsingException("Expected DEROctetString");
    }
}
