package com.android.internal.org.bouncycastle.asn1.x500.style;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1String;
import com.android.internal.org.bouncycastle.asn1.ASN1UniversalString;
import com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import com.android.internal.org.bouncycastle.asn1.x500.RDN;
import com.android.internal.org.bouncycastle.asn1.x500.X500NameBuilder;
import com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle;
import com.android.internal.org.bouncycastle.util.Strings;
import com.android.internal.org.bouncycastle.util.encoders.Hex;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* loaded from: classes5.dex */
public class IETFUtils {
    private static int convertHex(char c) {
        return ('0' > c || c > '9') ? ('a' > c || c > 'f') ? c - '7' : c - 'W' : c - '0';
    }

    private static boolean isHexDigit(char c) {
        if ('0' <= c && c <= '9') {
            return true;
        }
        if ('a' > c || c > 'f') {
            return 'A' <= c && c <= 'F';
        }
        return true;
    }

    private static String unescape(String str) {
        int i;
        if (str.length() == 0) {
            return str;
        }
        if (str.indexOf(92) < 0 && str.indexOf(34) < 0) {
            return str.trim();
        }
        StringBuffer stringBuffer = new StringBuffer(str.length());
        if (str.charAt(0) == '\\' && str.charAt(1) == '#') {
            stringBuffer.append("\\#");
            i = 2;
        } else {
            i = 0;
        }
        boolean z = false;
        int i2 = 0;
        boolean z2 = false;
        boolean z3 = false;
        char c = 0;
        while (i != str.length()) {
            char charAt = str.charAt(i);
            if (charAt != ' ') {
                z3 = true;
            }
            if (charAt == '\"') {
                if (z) {
                    stringBuffer.append(charAt);
                    z = false;
                } else {
                    z2 = !z2;
                }
            } else if (charAt == '\\' && !z && !z2) {
                i2 = stringBuffer.length();
                z = true;
            } else if (charAt != ' ' || z || z3) {
                if (!z || !isHexDigit(charAt)) {
                    stringBuffer.append(charAt);
                    z = false;
                } else if (c != 0) {
                    stringBuffer.append((char) ((convertHex(c) * 16) + convertHex(charAt)));
                    z = false;
                    c = 0;
                } else {
                    c = charAt;
                }
            }
            i++;
        }
        if (stringBuffer.length() > 0) {
            while (stringBuffer.charAt(stringBuffer.length() - 1) == ' ' && i2 != stringBuffer.length() - 1) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }

    public static RDN[] rDNsFromString(String str, X500NameStyle x500NameStyle) {
        X500NameTokenizer x500NameTokenizer = new X500NameTokenizer(str);
        X500NameBuilder x500NameBuilder = new X500NameBuilder(x500NameStyle);
        addRDNs(x500NameStyle, x500NameBuilder, x500NameTokenizer);
        return x500NameBuilder.build().getRDNs();
    }

    private static void addRDNs(X500NameStyle x500NameStyle, X500NameBuilder x500NameBuilder, X500NameTokenizer x500NameTokenizer) {
        while (true) {
            String nextToken = x500NameTokenizer.nextToken();
            if (nextToken == null) {
                return;
            }
            if (nextToken.indexOf(43) >= 0) {
                addMultiValuedRDN(x500NameStyle, x500NameBuilder, new X500NameTokenizer(nextToken, '+'));
            } else {
                addRDN(x500NameStyle, x500NameBuilder, nextToken);
            }
        }
    }

    private static void addMultiValuedRDN(X500NameStyle x500NameStyle, X500NameBuilder x500NameBuilder, X500NameTokenizer x500NameTokenizer) {
        String nextToken = x500NameTokenizer.nextToken();
        if (nextToken == null) {
            throw new IllegalArgumentException("badly formatted directory string");
        }
        if (!x500NameTokenizer.hasMoreTokens()) {
            addRDN(x500NameStyle, x500NameBuilder, nextToken);
            return;
        }
        Vector vector = new Vector();
        Vector vector2 = new Vector();
        do {
            collectAttributeTypeAndValue(x500NameStyle, vector, vector2, nextToken);
            nextToken = x500NameTokenizer.nextToken();
        } while (nextToken != null);
        x500NameBuilder.addMultiValuedRDN(toOIDArray(vector), toValueArray(vector2));
    }

    private static void addRDN(X500NameStyle x500NameStyle, X500NameBuilder x500NameBuilder, String str) {
        X500NameTokenizer x500NameTokenizer = new X500NameTokenizer(str, '=');
        x500NameBuilder.addRDN(x500NameStyle.attrNameToOID(nextToken(x500NameTokenizer, true).trim()), unescape(nextToken(x500NameTokenizer, false)));
    }

    private static void collectAttributeTypeAndValue(X500NameStyle x500NameStyle, Vector vector, Vector vector2, String str) {
        X500NameTokenizer x500NameTokenizer = new X500NameTokenizer(str, '=');
        String nextToken = nextToken(x500NameTokenizer, true);
        String nextToken2 = nextToken(x500NameTokenizer, false);
        ASN1ObjectIdentifier attrNameToOID = x500NameStyle.attrNameToOID(nextToken.trim());
        String unescape = unescape(nextToken2);
        vector.addElement(attrNameToOID);
        vector2.addElement(unescape);
    }

    private static String nextToken(X500NameTokenizer x500NameTokenizer, boolean z) {
        String nextToken = x500NameTokenizer.nextToken();
        if (nextToken == null || x500NameTokenizer.hasMoreTokens() != z) {
            throw new IllegalArgumentException("badly formatted directory string");
        }
        return nextToken;
    }

    private static String[] toValueArray(Vector vector) {
        int size = vector.size();
        String[] strArr = new String[size];
        for (int i = 0; i != size; i++) {
            strArr[i] = (String) vector.elementAt(i);
        }
        return strArr;
    }

    private static ASN1ObjectIdentifier[] toOIDArray(Vector vector) {
        int size = vector.size();
        ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = new ASN1ObjectIdentifier[size];
        for (int i = 0; i != size; i++) {
            aSN1ObjectIdentifierArr[i] = (ASN1ObjectIdentifier) vector.elementAt(i);
        }
        return aSN1ObjectIdentifierArr;
    }

    public static String[] findAttrNamesForOID(ASN1ObjectIdentifier aSN1ObjectIdentifier, Hashtable hashtable) {
        Enumeration elements = hashtable.elements();
        int i = 0;
        int i2 = 0;
        while (elements.hasMoreElements()) {
            if (aSN1ObjectIdentifier.equals(elements.nextElement())) {
                i2++;
            }
        }
        String[] strArr = new String[i2];
        Enumeration keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            String str = (String) keys.nextElement();
            if (aSN1ObjectIdentifier.equals(hashtable.get(str))) {
                strArr[i] = str;
                i++;
            }
        }
        return strArr;
    }

    public static ASN1ObjectIdentifier decodeAttrName(String str, Hashtable hashtable) {
        if (Strings.toUpperCase(str).startsWith("OID.")) {
            return new ASN1ObjectIdentifier(str.substring(4));
        }
        if (str.charAt(0) >= '0' && str.charAt(0) <= '9') {
            return new ASN1ObjectIdentifier(str);
        }
        ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) hashtable.get(Strings.toLowerCase(str));
        if (aSN1ObjectIdentifier != null) {
            return aSN1ObjectIdentifier;
        }
        throw new IllegalArgumentException("Unknown object id - " + str + " - passed to distinguished name");
    }

    public static ASN1Encodable valueFromHexString(String str, int i) throws IOException {
        int length = (str.length() - i) / 2;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 != length; i2++) {
            int i3 = (i2 * 2) + i;
            char charAt = str.charAt(i3);
            char charAt2 = str.charAt(i3 + 1);
            bArr[i2] = (byte) (convertHex(charAt2) | (convertHex(charAt) << 4));
        }
        return ASN1Primitive.fromByteArray(bArr);
    }

    public static void appendRDN(StringBuffer stringBuffer, RDN rdn, Hashtable hashtable) {
        if (rdn.isMultiValued()) {
            AttributeTypeAndValue[] typesAndValues = rdn.getTypesAndValues();
            boolean z = true;
            for (int i = 0; i != typesAndValues.length; i++) {
                if (z) {
                    z = false;
                } else {
                    stringBuffer.append('+');
                }
                appendTypeAndValue(stringBuffer, typesAndValues[i], hashtable);
            }
            return;
        }
        if (rdn.getFirst() != null) {
            appendTypeAndValue(stringBuffer, rdn.getFirst(), hashtable);
        }
    }

    public static void appendTypeAndValue(StringBuffer stringBuffer, AttributeTypeAndValue attributeTypeAndValue, Hashtable hashtable) {
        String str = (String) hashtable.get(attributeTypeAndValue.getType());
        if (str != null) {
            stringBuffer.append(str);
        } else {
            stringBuffer.append(attributeTypeAndValue.getType().getId());
        }
        stringBuffer.append('=');
        stringBuffer.append(valueToString(attributeTypeAndValue.getValue()));
    }

    public static String valueToString(ASN1Encodable aSN1Encodable) {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        if ((aSN1Encodable instanceof ASN1String) && !(aSN1Encodable instanceof ASN1UniversalString)) {
            String string = ((ASN1String) aSN1Encodable).getString();
            if (string.length() > 0 && string.charAt(0) == '#') {
                stringBuffer.append('\\');
            }
            stringBuffer.append(string);
        } else {
            try {
                stringBuffer.append('#');
                stringBuffer.append(Hex.toHexString(aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER)));
            } catch (IOException unused) {
                throw new IllegalArgumentException("Other value has no encoded form");
            }
        }
        int length = stringBuffer.length();
        int i2 = (stringBuffer.length() >= 2 && stringBuffer.charAt(0) == '\\' && stringBuffer.charAt(1) == '#') ? 2 : 0;
        while (i2 != length) {
            char charAt = stringBuffer.charAt(i2);
            if (charAt != '\"' && charAt != '\\' && charAt != '+' && charAt != ',') {
                switch (charAt) {
                    case ';':
                    case '<':
                    case '=':
                    case '>':
                        break;
                    default:
                        i2++;
                        continue;
                }
            }
            stringBuffer.insert(i2, "\\");
            i2 += 2;
            length++;
        }
        if (stringBuffer.length() > 0) {
            while (stringBuffer.length() > i && stringBuffer.charAt(i) == ' ') {
                stringBuffer.insert(i, "\\");
                i += 2;
            }
        }
        for (int length2 = stringBuffer.length() - 1; length2 >= i && stringBuffer.charAt(length2) == ' '; length2--) {
            stringBuffer.insert(length2, '\\');
        }
        return stringBuffer.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x005a, code lost:
    
        if (r5 >= r0) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String canonicalize(java.lang.String r7) {
        /*
            int r0 = r7.length()
            r1 = 0
            if (r0 <= 0) goto L1d
            char r0 = r7.charAt(r1)
            r2 = 35
            if (r0 != r2) goto L1d
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive r0 = decodeObject(r7)
            boolean r2 = r0 instanceof com.android.internal.org.bouncycastle.asn1.ASN1String
            if (r2 == 0) goto L1d
            com.android.internal.org.bouncycastle.asn1.ASN1String r0 = (com.android.internal.org.bouncycastle.asn1.ASN1String) r0
            java.lang.String r7 = r0.getString()
        L1d:
            java.lang.String r7 = com.android.internal.org.bouncycastle.util.Strings.toLowerCase(r7)
            int r0 = r7.length()
            r2 = 2
            if (r0 >= r2) goto L29
            return r7
        L29:
            int r0 = r0 + (-1)
        L2b:
            r2 = 32
            r3 = 92
            if (r1 >= r0) goto L42
            char r4 = r7.charAt(r1)
            if (r4 != r3) goto L42
            int r4 = r1 + 1
            char r4 = r7.charAt(r4)
            if (r4 != r2) goto L42
            int r1 = r1 + 2
            goto L2b
        L42:
            int r4 = r1 + 1
            r5 = r0
        L45:
            if (r5 <= r4) goto L58
            int r6 = r5 + (-1)
            char r6 = r7.charAt(r6)
            if (r6 != r3) goto L58
            char r6 = r7.charAt(r5)
            if (r6 != r2) goto L58
            int r5 = r5 + (-2)
            goto L45
        L58:
            if (r1 > 0) goto L5c
            if (r5 >= r0) goto L62
        L5c:
            int r5 = r5 + 1
            java.lang.String r7 = r7.substring(r1, r5)
        L62:
            java.lang.String r7 = stripInternalSpaces(r7)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.asn1.x500.style.IETFUtils.canonicalize(java.lang.String):java.lang.String");
    }

    public static String canonicalString(ASN1Encodable aSN1Encodable) {
        return canonicalize(valueToString(aSN1Encodable));
    }

    private static ASN1Primitive decodeObject(String str) {
        try {
            return ASN1Primitive.fromByteArray(Hex.decodeStrict(str, 1, str.length() - 1));
        } catch (IOException e) {
            throw new IllegalStateException("unknown encoding in name: " + e);
        }
    }

    public static String stripInternalSpaces(String str) {
        if (str.indexOf("  ") < 0) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        char charAt = str.charAt(0);
        stringBuffer.append(charAt);
        for (int i = 1; i < str.length(); i++) {
            char charAt2 = str.charAt(i);
            if (charAt != ' ' || charAt2 != ' ') {
                stringBuffer.append(charAt2);
                charAt = charAt2;
            }
        }
        return stringBuffer.toString();
    }

    public static boolean rDNAreEqual(RDN rdn, RDN rdn2) {
        if (rdn.size() != rdn2.size()) {
            return false;
        }
        AttributeTypeAndValue[] typesAndValues = rdn.getTypesAndValues();
        AttributeTypeAndValue[] typesAndValues2 = rdn2.getTypesAndValues();
        if (typesAndValues.length != typesAndValues2.length) {
            return false;
        }
        for (int i = 0; i != typesAndValues.length; i++) {
            if (!atvAreEqual(typesAndValues[i], typesAndValues2[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean atvAreEqual(AttributeTypeAndValue attributeTypeAndValue, AttributeTypeAndValue attributeTypeAndValue2) {
        if (attributeTypeAndValue == attributeTypeAndValue2) {
            return true;
        }
        return attributeTypeAndValue != null && attributeTypeAndValue2 != null && attributeTypeAndValue.getType().equals((ASN1Primitive) attributeTypeAndValue2.getType()) && canonicalString(attributeTypeAndValue.getValue()).equals(canonicalString(attributeTypeAndValue2.getValue()));
    }
}
