package android.net.http;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.org.bouncycastle.asn1.x509.X509Name;
import com.android.internal.util.HexDump;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/* loaded from: classes3.dex */
public class SslCertificate {
    private static String ISO_8601_DATE_FORMAT = "yyyy-MM-dd HH:mm:ssZ";
    private static final String ISSUED_BY = "issued-by";
    private static final String ISSUED_TO = "issued-to";
    private static final String VALID_NOT_AFTER = "valid-not-after";
    private static final String VALID_NOT_BEFORE = "valid-not-before";
    private static final String X509_CERTIFICATE = "x509-certificate";
    private final DName mIssuedBy;
    private final DName mIssuedTo;
    private final Date mValidNotAfter;
    private final Date mValidNotBefore;
    private final X509Certificate mX509Certificate;

    public static Bundle saveState(SslCertificate sslCertificate) {
        if (sslCertificate == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString(ISSUED_TO, sslCertificate.getIssuedTo().getDName());
        bundle.putString(ISSUED_BY, sslCertificate.getIssuedBy().getDName());
        bundle.putString(VALID_NOT_BEFORE, sslCertificate.getValidNotBefore());
        bundle.putString(VALID_NOT_AFTER, sslCertificate.getValidNotAfter());
        X509Certificate x509Certificate = sslCertificate.mX509Certificate;
        if (x509Certificate != null) {
            try {
                bundle.putByteArray(X509_CERTIFICATE, x509Certificate.getEncoded());
            } catch (CertificateEncodingException unused) {
            }
        }
        return bundle;
    }

    public static SslCertificate restoreState(Bundle bundle) {
        X509Certificate x509Certificate;
        if (bundle == null) {
            return null;
        }
        byte[] byteArray = bundle.getByteArray(X509_CERTIFICATE);
        if (byteArray != null) {
            try {
                x509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(byteArray));
            } catch (CertificateException unused) {
            }
            return new SslCertificate(bundle.getString(ISSUED_TO), bundle.getString(ISSUED_BY), parseDate(bundle.getString(VALID_NOT_BEFORE)), parseDate(bundle.getString(VALID_NOT_AFTER)), x509Certificate);
        }
        x509Certificate = null;
        return new SslCertificate(bundle.getString(ISSUED_TO), bundle.getString(ISSUED_BY), parseDate(bundle.getString(VALID_NOT_BEFORE)), parseDate(bundle.getString(VALID_NOT_AFTER)), x509Certificate);
    }

    @Deprecated
    public SslCertificate(String str, String str2, String str3, String str4) {
        this(str, str2, parseDate(str3), parseDate(str4), null);
    }

    @Deprecated
    public SslCertificate(String str, String str2, Date date, Date date2) {
        this(str, str2, date, date2, null);
    }

    public SslCertificate(X509Certificate x509Certificate) {
        this(x509Certificate.getSubjectDN().getName(), x509Certificate.getIssuerDN().getName(), x509Certificate.getNotBefore(), x509Certificate.getNotAfter(), x509Certificate);
    }

    private SslCertificate(String str, String str2, Date date, Date date2, X509Certificate x509Certificate) {
        this.mIssuedTo = new DName(this, str);
        this.mIssuedBy = new DName(this, str2);
        this.mValidNotBefore = cloneDate(date);
        this.mValidNotAfter = cloneDate(date2);
        this.mX509Certificate = x509Certificate;
    }

    public Date getValidNotBeforeDate() {
        return cloneDate(this.mValidNotBefore);
    }

    @Deprecated
    public String getValidNotBefore() {
        return formatDate(this.mValidNotBefore);
    }

    public Date getValidNotAfterDate() {
        return cloneDate(this.mValidNotAfter);
    }

    @Deprecated
    public String getValidNotAfter() {
        return formatDate(this.mValidNotAfter);
    }

    public DName getIssuedTo() {
        return this.mIssuedTo;
    }

    public DName getIssuedBy() {
        return this.mIssuedBy;
    }

    public X509Certificate getX509Certificate() {
        return this.mX509Certificate;
    }

    private static String getSerialNumber(X509Certificate x509Certificate) {
        BigInteger serialNumber;
        if (x509Certificate == null || (serialNumber = x509Certificate.getSerialNumber()) == null) {
            return "";
        }
        return fingerprint(serialNumber.toByteArray());
    }

    private static String getDigest(X509Certificate x509Certificate, String str) {
        if (x509Certificate == null) {
            return "";
        }
        try {
            return fingerprint(MessageDigest.getInstance(str).digest(x509Certificate.getEncoded()));
        } catch (NoSuchAlgorithmException | CertificateEncodingException unused) {
            return "";
        }
    }

    private static final String fingerprint(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < bArr.length) {
            HexDump.appendByteAsHex(sb, bArr[i], true);
            i++;
            if (i != bArr.length) {
                sb.append(ShortcutConstants.SERVICES_SEPARATOR);
            }
        }
        return sb.toString();
    }

    public String toString() {
        return "Issued to: " + this.mIssuedTo.getDName() + ";\nIssued by: " + this.mIssuedBy.getDName() + ";\n";
    }

    private static Date parseDate(String str) {
        try {
            return new SimpleDateFormat(ISO_8601_DATE_FORMAT).parse(str);
        } catch (ParseException unused) {
            return null;
        }
    }

    private static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(ISO_8601_DATE_FORMAT).format(date);
    }

    private static Date cloneDate(Date date) {
        if (date == null) {
            return null;
        }
        return (Date) date.clone();
    }

    public class DName {
        private String mCName;
        private String mDName;
        private String mOName;
        private String mUName;

        public DName(SslCertificate sslCertificate, String str) {
            if (str != null) {
                this.mDName = str;
                try {
                    X509Name x509Name = new X509Name(str);
                    Vector values = x509Name.getValues();
                    Vector oIDs = x509Name.getOIDs();
                    for (int i = 0; i < oIDs.size(); i++) {
                        if (oIDs.elementAt(i).equals(X509Name.CN)) {
                            if (this.mCName == null) {
                                this.mCName = (String) values.elementAt(i);
                            }
                        } else if (oIDs.elementAt(i).equals(X509Name.O) && this.mOName == null) {
                            this.mOName = (String) values.elementAt(i);
                        } else if (oIDs.elementAt(i).equals(X509Name.OU) && this.mUName == null) {
                            this.mUName = (String) values.elementAt(i);
                        }
                    }
                } catch (IllegalArgumentException unused) {
                }
            }
        }

        public String getDName() {
            String str = this.mDName;
            return str != null ? str : "";
        }

        public String getCName() {
            String str = this.mCName;
            return str != null ? str : "";
        }

        public String getOName() {
            String str = this.mOName;
            return str != null ? str : "";
        }

        public String getUName() {
            String str = this.mUName;
            return str != null ? str : "";
        }
    }

    public View inflateCertificateView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.ssl_certificate, (ViewGroup) null);
        DName issuedTo = getIssuedTo();
        if (issuedTo != null) {
            ((TextView) inflate.findViewById(R.id.to_common)).lambda$setTextAsync$0(issuedTo.getCName());
            ((TextView) inflate.findViewById(R.id.to_org)).lambda$setTextAsync$0(issuedTo.getOName());
            ((TextView) inflate.findViewById(R.id.to_org_unit)).lambda$setTextAsync$0(issuedTo.getUName());
        }
        ((TextView) inflate.findViewById(R.id.serial_number)).lambda$setTextAsync$0(getSerialNumber(this.mX509Certificate));
        DName issuedBy = getIssuedBy();
        if (issuedBy != null) {
            ((TextView) inflate.findViewById(R.id.by_common)).lambda$setTextAsync$0(issuedBy.getCName());
            ((TextView) inflate.findViewById(R.id.by_org)).lambda$setTextAsync$0(issuedBy.getOName());
            ((TextView) inflate.findViewById(R.id.by_org_unit)).lambda$setTextAsync$0(issuedBy.getUName());
        }
        ((TextView) inflate.findViewById(R.id.issued_on)).lambda$setTextAsync$0(formatCertificateDate(context, getValidNotBeforeDate()));
        ((TextView) inflate.findViewById(R.id.expires_on)).lambda$setTextAsync$0(formatCertificateDate(context, getValidNotAfterDate()));
        ((TextView) inflate.findViewById(R.id.sha256_fingerprint)).lambda$setTextAsync$0(getDigest(this.mX509Certificate, "SHA256"));
        ((TextView) inflate.findViewById(R.id.sha1_fingerprint)).lambda$setTextAsync$0(getDigest(this.mX509Certificate, "SHA1"));
        return inflate;
    }

    private String formatCertificateDate(Context context, Date date) {
        if (date == null) {
            return "";
        }
        return DateFormat.getMediumDateFormat(context).format(date);
    }
}
