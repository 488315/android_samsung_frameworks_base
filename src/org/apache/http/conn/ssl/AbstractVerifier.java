package org.apache.http.conn.ssl;

import android.provider.DocumentsContract;
import java.io.IOException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

@Deprecated
/* loaded from: classes6.dex */
public abstract class AbstractVerifier implements X509HostnameVerifier {
    private static final String[] BAD_COUNTRY_2LDS;
    private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    static {
        String[] strArr = {"ac", "co", "com", "ed", "edu", "go", "gouv", "gov", DocumentsContract.EXTRA_INFO, "lg", "ne", "net", "or", "org"};
        BAD_COUNTRY_2LDS = strArr;
        Arrays.sort(strArr);
    }

    @Override // org.apache.http.conn.ssl.X509HostnameVerifier
    public final void verify(String str, SSLSocket sSLSocket) throws IOException {
        if (str == null) {
            throw new NullPointerException("host to verify is null");
        }
        verify(str, (X509Certificate) sSLSocket.getSession().getPeerCertificates()[0]);
    }

    @Override // org.apache.http.conn.ssl.X509HostnameVerifier, javax.net.ssl.HostnameVerifier
    public final boolean verify(String str, SSLSession sSLSession) {
        try {
            verify(str, (X509Certificate) sSLSession.getPeerCertificates()[0]);
            return true;
        } catch (SSLException unused) {
            return false;
        }
    }

    @Override // org.apache.http.conn.ssl.X509HostnameVerifier
    public final void verify(String str, X509Certificate x509Certificate) throws SSLException {
        verify(str, getCNs(x509Certificate), getDNSSubjectAlts(x509Certificate));
    }

    public final void verify(String str, String[] strArr, String[] strArr2, boolean z) throws SSLException {
        String str2;
        LinkedList linkedList = new LinkedList();
        if (strArr != null && strArr.length > 0 && (str2 = strArr[0]) != null) {
            linkedList.add(str2);
        }
        if (strArr2 != null) {
            for (String str3 : strArr2) {
                if (str3 != null) {
                    linkedList.add(str3);
                }
            }
        }
        if (linkedList.isEmpty()) {
            throw new SSLException("Certificate for <" + str + "> doesn't contain CN or DNS subjectAlt");
        }
        StringBuffer stringBuffer = new StringBuffer();
        String lowerCase = str.trim().toLowerCase(Locale.ENGLISH);
        Iterator it = linkedList.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            String lowerCase2 = ((String) it.next()).toLowerCase(Locale.ENGLISH);
            stringBuffer.append(" <");
            stringBuffer.append(lowerCase2);
            stringBuffer.append('>');
            if (it.hasNext()) {
                stringBuffer.append(" OR");
            }
            if (lowerCase2.startsWith("*.") && lowerCase2.indexOf(46, 2) != -1 && acceptableCountryWildcard(lowerCase2) && !isIPv4Address(str)) {
                boolean endsWith = lowerCase.endsWith(lowerCase2.substring(1));
                if (endsWith && z) {
                    z2 = countDots(lowerCase) == countDots(lowerCase2);
                } else {
                    z2 = endsWith;
                }
            } else {
                z2 = lowerCase.equals(lowerCase2);
            }
            if (z2) {
                break;
            }
        }
        if (z2) {
            return;
        }
        throw new SSLException("hostname in certificate didn't match: <" + str + "> !=" + ((Object) stringBuffer));
    }

    public static boolean acceptableCountryWildcard(String str) {
        int length = str.length();
        if (length >= 7 && length <= 9) {
            int i = length - 3;
            if (str.charAt(i) == '.') {
                return Arrays.binarySearch(BAD_COUNTRY_2LDS, str.substring(2, i)) < 0;
            }
        }
        return true;
    }

    public static String[] getCNs(X509Certificate x509Certificate) {
        List<String> allMostSpecificFirst = new AndroidDistinguishedNameParser(x509Certificate.getSubjectX500Principal()).getAllMostSpecificFirst("cn");
        if (allMostSpecificFirst.isEmpty()) {
            return null;
        }
        String[] strArr = new String[allMostSpecificFirst.size()];
        allMostSpecificFirst.toArray(strArr);
        return strArr;
    }

    public static String[] getDNSSubjectAlts(X509Certificate x509Certificate) {
        Collection<List<?>> collection;
        LinkedList linkedList = new LinkedList();
        try {
            collection = x509Certificate.getSubjectAlternativeNames();
        } catch (CertificateParsingException e) {
            Logger.getLogger(AbstractVerifier.class.getName()).log(Level.FINE, "Error parsing certificate.", (Throwable) e);
            collection = null;
        }
        if (collection != null) {
            for (List<?> list : collection) {
                if (((Integer) list.get(0)).intValue() == 2) {
                    linkedList.add((String) list.get(1));
                }
            }
        }
        if (linkedList.isEmpty()) {
            return null;
        }
        String[] strArr = new String[linkedList.size()];
        linkedList.toArray(strArr);
        return strArr;
    }

    public static int countDots(String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) == '.') {
                i++;
            }
        }
        return i;
    }

    private static boolean isIPv4Address(String str) {
        return IPV4_PATTERN.matcher(str).matches();
    }
}
