package com.android.server.knox.dar;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AttestedCertParser {
    public final IntegrityStatus mKnoxIngetrity;

    public AttestedCertParser(X509Certificate x509Certificate) {
        ASN1Sequence asn1SequenceFromStream;
        byte[] extensionValue = x509Certificate.getExtensionValue("1.3.6.1.4.1.236.11.3.23.7");
        if (extensionValue == null) {
            Log.e("AttestedCertParser", "getKnoxExtensionSequence : not include knox extension");
            asn1SequenceFromStream = null;
        } else {
            StringBuilder sb = new StringBuilder();
            int length = extensionValue.length;
            for (int i = 0; i < length; i = MARsFreezeStateRecord$$ExternalSyntheticOutline0.m("%02x", new Object[]{Integer.valueOf(extensionValue[i] & 255)}, sb, i, 1)) {
            }
            String sb2 = sb.toString();
            String substring = sb2.substring(2, 4);
            String substring2 = sb2.substring(10, 12);
            int parseInt = Integer.parseInt(substring, 16);
            int parseInt2 = Integer.parseInt(substring2, 16);
            String str = SystemProperties.get("ro.product.first_api_level");
            Log.d("AttestedCertParser", "firstApiLevel = " + str);
            Log.d("AttestedCertParser", "lengthOfextenstion = " + parseInt);
            Log.d("AttestedCertParser", "lengthOfvalue = " + parseInt2);
            if (!TextUtils.isEmpty(str) && Integer.parseInt(str) < 26 && parseInt - 4 != parseInt2) {
                int i2 = parseInt2 + 6;
                byte[] bArr = new byte[i2];
                System.arraycopy(extensionValue, 0, bArr, 0, i2);
                String hexString = Integer.toHexString(parseInt2 + 4);
                Charset charset = StandardCharsets.UTF_8;
                System.arraycopy(hexString.getBytes(charset), 1, bArr, 1, 1);
                System.arraycopy(Integer.toHexString(parseInt2 + 2).getBytes(charset), 1, bArr, 3, 1);
                extensionValue = bArr;
            }
            if (extensionValue.length == 0) {
                throw new CertificateParsingException("Did not find extension with OID 1.3.6.1.4.1.236.11.3.23.7");
            }
            try {
                ASN1InputStream aSN1InputStream = new ASN1InputStream(extensionValue);
                try {
                    asn1SequenceFromStream = Asn1Utils.getAsn1SequenceFromStream(aSN1InputStream);
                    aSN1InputStream.close();
                } finally {
                }
            } catch (IOException e) {
                throw new CertificateParsingException("Failed to parse SEQUENCE", e);
            }
        }
        if (asn1SequenceFromStream != null) {
            for (int i3 = 0; i3 < asn1SequenceFromStream.size(); i3++) {
                try {
                    ASN1TaggedObject objectAt = asn1SequenceFromStream.getObjectAt(i3);
                    if (objectAt instanceof ASN1TaggedObject) {
                        if (objectAt.getTagNo() != 5) {
                            Log.d("AttestedCertParser", "Skip unknown case : " + objectAt.getTagNo());
                        } else {
                            IntegrityStatus integrityStatus = new IntegrityStatus(objectAt.getObject());
                            this.mKnoxIngetrity = integrityStatus;
                            Log.i("AttestedCertParser", "Found [Integrity Status] :\n" + integrityStatus.toString());
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e2) {
                    Log.e("AttestedCertParser", "sequence index is too small to get challenge or idAttest", e2);
                    e2.printStackTrace();
                    return;
                }
            }
        }
    }
}
