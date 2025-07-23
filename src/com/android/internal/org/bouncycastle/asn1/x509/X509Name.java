package com.android.internal.org.bouncycastle.asn1.x509;

import android.app.blob.XmlTags;
import android.hardware.gnss.GnssSignalType;
import android.provider.Telephony;
import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1Set;
import com.android.internal.org.bouncycastle.asn1.ASN1String;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.ASN1UniversalString;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERSet;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.util.Strings;
import com.android.internal.org.bouncycastle.util.encoders.Hex;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* loaded from: classes5.dex */
public class X509Name extends ASN1Object {
    public static final ASN1ObjectIdentifier BUSINESS_CATEGORY;
    public static final ASN1ObjectIdentifier C;
    public static final ASN1ObjectIdentifier CN;
    public static final ASN1ObjectIdentifier COUNTRY_OF_CITIZENSHIP;
    public static final ASN1ObjectIdentifier COUNTRY_OF_RESIDENCE;
    public static final ASN1ObjectIdentifier DATE_OF_BIRTH;
    public static final ASN1ObjectIdentifier DC;
    public static final ASN1ObjectIdentifier DMD_NAME;
    public static final ASN1ObjectIdentifier DN_QUALIFIER;
    public static final Hashtable DefaultLookUp;
    public static boolean DefaultReverse;
    public static final Hashtable DefaultSymbols;
    public static final ASN1ObjectIdentifier E;
    public static final ASN1ObjectIdentifier EmailAddress;
    private static final Boolean FALSE;
    public static final ASN1ObjectIdentifier GENDER;
    public static final ASN1ObjectIdentifier GENERATION;
    public static final ASN1ObjectIdentifier GIVENNAME;
    public static final ASN1ObjectIdentifier INITIALS;
    public static final ASN1ObjectIdentifier L;
    public static final ASN1ObjectIdentifier NAME;
    public static final ASN1ObjectIdentifier NAME_AT_BIRTH;
    public static final ASN1ObjectIdentifier O;
    public static final Hashtable OIDLookUp;
    public static final ASN1ObjectIdentifier OU;
    public static final ASN1ObjectIdentifier PLACE_OF_BIRTH;
    public static final ASN1ObjectIdentifier POSTAL_ADDRESS;
    public static final ASN1ObjectIdentifier POSTAL_CODE;
    public static final ASN1ObjectIdentifier PSEUDONYM;
    public static final Hashtable RFC1779Symbols;
    public static final Hashtable RFC2253Symbols;
    public static final ASN1ObjectIdentifier SERIALNUMBER;
    public static final ASN1ObjectIdentifier SN;
    public static final ASN1ObjectIdentifier ST;
    public static final ASN1ObjectIdentifier STREET;
    public static final ASN1ObjectIdentifier SURNAME;
    public static final Hashtable SymbolLookUp;
    public static final ASN1ObjectIdentifier T;
    public static final ASN1ObjectIdentifier TELEPHONE_NUMBER;
    private static final Boolean TRUE;
    public static final ASN1ObjectIdentifier UID;
    public static final ASN1ObjectIdentifier UNIQUE_IDENTIFIER;
    public static final ASN1ObjectIdentifier UnstructuredAddress;
    public static final ASN1ObjectIdentifier UnstructuredName;
    private Vector added;
    private X509NameEntryConverter converter;
    private int hashCodeValue;
    private boolean isHashCodeCalculated;
    private Vector ordering;
    private ASN1Sequence seq;
    private Vector values;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("2.5.4.6");
        C = aSN1ObjectIdentifier;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = new ASN1ObjectIdentifier("2.5.4.10");
        O = aSN1ObjectIdentifier2;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = new ASN1ObjectIdentifier("2.5.4.11");
        OU = aSN1ObjectIdentifier3;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = new ASN1ObjectIdentifier("2.5.4.12");
        T = aSN1ObjectIdentifier4;
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = new ASN1ObjectIdentifier("2.5.4.3");
        CN = aSN1ObjectIdentifier5;
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = new ASN1ObjectIdentifier("2.5.4.5");
        SN = aSN1ObjectIdentifier6;
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = new ASN1ObjectIdentifier("2.5.4.9");
        STREET = aSN1ObjectIdentifier7;
        SERIALNUMBER = aSN1ObjectIdentifier6;
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = new ASN1ObjectIdentifier("2.5.4.7");
        L = aSN1ObjectIdentifier8;
        ASN1ObjectIdentifier aSN1ObjectIdentifier9 = new ASN1ObjectIdentifier("2.5.4.8");
        ST = aSN1ObjectIdentifier9;
        ASN1ObjectIdentifier aSN1ObjectIdentifier10 = new ASN1ObjectIdentifier("2.5.4.4");
        SURNAME = aSN1ObjectIdentifier10;
        ASN1ObjectIdentifier aSN1ObjectIdentifier11 = new ASN1ObjectIdentifier("2.5.4.42");
        GIVENNAME = aSN1ObjectIdentifier11;
        ASN1ObjectIdentifier aSN1ObjectIdentifier12 = new ASN1ObjectIdentifier("2.5.4.43");
        INITIALS = aSN1ObjectIdentifier12;
        ASN1ObjectIdentifier aSN1ObjectIdentifier13 = new ASN1ObjectIdentifier("2.5.4.44");
        GENERATION = aSN1ObjectIdentifier13;
        ASN1ObjectIdentifier aSN1ObjectIdentifier14 = new ASN1ObjectIdentifier("2.5.4.45");
        UNIQUE_IDENTIFIER = aSN1ObjectIdentifier14;
        ASN1ObjectIdentifier aSN1ObjectIdentifier15 = new ASN1ObjectIdentifier("2.5.4.15");
        BUSINESS_CATEGORY = aSN1ObjectIdentifier15;
        ASN1ObjectIdentifier aSN1ObjectIdentifier16 = new ASN1ObjectIdentifier("2.5.4.17");
        POSTAL_CODE = aSN1ObjectIdentifier16;
        ASN1ObjectIdentifier aSN1ObjectIdentifier17 = new ASN1ObjectIdentifier("2.5.4.46");
        DN_QUALIFIER = aSN1ObjectIdentifier17;
        ASN1ObjectIdentifier aSN1ObjectIdentifier18 = new ASN1ObjectIdentifier("2.5.4.65");
        PSEUDONYM = aSN1ObjectIdentifier18;
        ASN1ObjectIdentifier aSN1ObjectIdentifier19 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.1");
        DATE_OF_BIRTH = aSN1ObjectIdentifier19;
        ASN1ObjectIdentifier aSN1ObjectIdentifier20 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.2");
        PLACE_OF_BIRTH = aSN1ObjectIdentifier20;
        ASN1ObjectIdentifier aSN1ObjectIdentifier21 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.3");
        GENDER = aSN1ObjectIdentifier21;
        ASN1ObjectIdentifier aSN1ObjectIdentifier22 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.4");
        COUNTRY_OF_CITIZENSHIP = aSN1ObjectIdentifier22;
        ASN1ObjectIdentifier aSN1ObjectIdentifier23 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.5");
        COUNTRY_OF_RESIDENCE = aSN1ObjectIdentifier23;
        ASN1ObjectIdentifier aSN1ObjectIdentifier24 = new ASN1ObjectIdentifier("1.3.36.8.3.14");
        NAME_AT_BIRTH = aSN1ObjectIdentifier24;
        ASN1ObjectIdentifier aSN1ObjectIdentifier25 = new ASN1ObjectIdentifier("2.5.4.16");
        POSTAL_ADDRESS = aSN1ObjectIdentifier25;
        DMD_NAME = new ASN1ObjectIdentifier("2.5.4.54");
        ASN1ObjectIdentifier aSN1ObjectIdentifier26 = X509ObjectIdentifiers.id_at_telephoneNumber;
        TELEPHONE_NUMBER = aSN1ObjectIdentifier26;
        ASN1ObjectIdentifier aSN1ObjectIdentifier27 = X509ObjectIdentifiers.id_at_name;
        NAME = aSN1ObjectIdentifier27;
        ASN1ObjectIdentifier aSN1ObjectIdentifier28 = PKCSObjectIdentifiers.pkcs_9_at_emailAddress;
        EmailAddress = aSN1ObjectIdentifier28;
        ASN1ObjectIdentifier aSN1ObjectIdentifier29 = PKCSObjectIdentifiers.pkcs_9_at_unstructuredName;
        UnstructuredName = aSN1ObjectIdentifier29;
        ASN1ObjectIdentifier aSN1ObjectIdentifier30 = PKCSObjectIdentifiers.pkcs_9_at_unstructuredAddress;
        UnstructuredAddress = aSN1ObjectIdentifier30;
        E = aSN1ObjectIdentifier28;
        ASN1ObjectIdentifier aSN1ObjectIdentifier31 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
        DC = aSN1ObjectIdentifier31;
        ASN1ObjectIdentifier aSN1ObjectIdentifier32 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
        UID = aSN1ObjectIdentifier32;
        DefaultReverse = false;
        Hashtable hashtable = new Hashtable();
        DefaultSymbols = hashtable;
        Hashtable hashtable2 = new Hashtable();
        RFC2253Symbols = hashtable2;
        Hashtable hashtable3 = new Hashtable();
        RFC1779Symbols = hashtable3;
        Hashtable hashtable4 = new Hashtable();
        DefaultLookUp = hashtable4;
        OIDLookUp = hashtable;
        SymbolLookUp = hashtable4;
        TRUE = Boolean.TRUE;
        FALSE = Boolean.FALSE;
        hashtable.put(aSN1ObjectIdentifier, GnssSignalType.CODE_TYPE_C);
        hashtable.put(aSN1ObjectIdentifier2, "O");
        hashtable.put(aSN1ObjectIdentifier4, "T");
        hashtable.put(aSN1ObjectIdentifier3, "OU");
        hashtable.put(aSN1ObjectIdentifier5, "CN");
        hashtable.put(aSN1ObjectIdentifier8, GnssSignalType.CODE_TYPE_L);
        hashtable.put(aSN1ObjectIdentifier9, "ST");
        hashtable.put(aSN1ObjectIdentifier6, "SERIALNUMBER");
        hashtable.put(aSN1ObjectIdentifier28, "E");
        hashtable.put(aSN1ObjectIdentifier31, "DC");
        hashtable.put(aSN1ObjectIdentifier32, "UID");
        hashtable.put(aSN1ObjectIdentifier7, "STREET");
        hashtable.put(aSN1ObjectIdentifier10, "SURNAME");
        hashtable.put(aSN1ObjectIdentifier11, "GIVENNAME");
        hashtable.put(aSN1ObjectIdentifier12, "INITIALS");
        hashtable.put(aSN1ObjectIdentifier13, "GENERATION");
        hashtable.put(aSN1ObjectIdentifier30, "unstructuredAddress");
        hashtable.put(aSN1ObjectIdentifier29, "unstructuredName");
        hashtable.put(aSN1ObjectIdentifier14, "UniqueIdentifier");
        hashtable.put(aSN1ObjectIdentifier17, "DN");
        hashtable.put(aSN1ObjectIdentifier18, "Pseudonym");
        hashtable.put(aSN1ObjectIdentifier25, "PostalAddress");
        hashtable.put(aSN1ObjectIdentifier24, "NameAtBirth");
        hashtable.put(aSN1ObjectIdentifier22, "CountryOfCitizenship");
        hashtable.put(aSN1ObjectIdentifier23, "CountryOfResidence");
        hashtable.put(aSN1ObjectIdentifier21, "Gender");
        hashtable.put(aSN1ObjectIdentifier20, "PlaceOfBirth");
        hashtable.put(aSN1ObjectIdentifier19, "DateOfBirth");
        hashtable.put(aSN1ObjectIdentifier16, "PostalCode");
        hashtable.put(aSN1ObjectIdentifier15, "BusinessCategory");
        hashtable.put(aSN1ObjectIdentifier26, "TelephoneNumber");
        hashtable.put(aSN1ObjectIdentifier27, "Name");
        hashtable2.put(aSN1ObjectIdentifier, GnssSignalType.CODE_TYPE_C);
        hashtable2.put(aSN1ObjectIdentifier2, "O");
        hashtable2.put(aSN1ObjectIdentifier3, "OU");
        hashtable2.put(aSN1ObjectIdentifier5, "CN");
        hashtable2.put(aSN1ObjectIdentifier8, GnssSignalType.CODE_TYPE_L);
        hashtable2.put(aSN1ObjectIdentifier9, "ST");
        hashtable2.put(aSN1ObjectIdentifier7, "STREET");
        hashtable2.put(aSN1ObjectIdentifier31, "DC");
        hashtable2.put(aSN1ObjectIdentifier32, "UID");
        hashtable3.put(aSN1ObjectIdentifier, GnssSignalType.CODE_TYPE_C);
        hashtable3.put(aSN1ObjectIdentifier2, "O");
        hashtable3.put(aSN1ObjectIdentifier3, "OU");
        hashtable3.put(aSN1ObjectIdentifier5, "CN");
        hashtable3.put(aSN1ObjectIdentifier8, GnssSignalType.CODE_TYPE_L);
        hashtable3.put(aSN1ObjectIdentifier9, "ST");
        hashtable3.put(aSN1ObjectIdentifier7, "STREET");
        hashtable4.put("c", aSN1ObjectIdentifier);
        hashtable4.put("o", aSN1ObjectIdentifier2);
        hashtable4.put("t", aSN1ObjectIdentifier4);
        hashtable4.put("ou", aSN1ObjectIdentifier3);
        hashtable4.put("cn", aSN1ObjectIdentifier5);
        hashtable4.put(XmlTags.TAG_LEASEE, aSN1ObjectIdentifier8);
        hashtable4.put(Telephony.BaseMmsColumns.STATUS, aSN1ObjectIdentifier9);
        hashtable4.put("sn", aSN1ObjectIdentifier6);
        hashtable4.put("serialnumber", aSN1ObjectIdentifier6);
        hashtable4.put("street", aSN1ObjectIdentifier7);
        hashtable4.put("emailaddress", aSN1ObjectIdentifier28);
        hashtable4.put("dc", aSN1ObjectIdentifier31);
        hashtable4.put("e", aSN1ObjectIdentifier28);
        hashtable4.put("uid", aSN1ObjectIdentifier32);
        hashtable4.put("surname", aSN1ObjectIdentifier10);
        hashtable4.put("givenname", aSN1ObjectIdentifier11);
        hashtable4.put("initials", aSN1ObjectIdentifier12);
        hashtable4.put("generation", aSN1ObjectIdentifier13);
        hashtable4.put("unstructuredaddress", aSN1ObjectIdentifier30);
        hashtable4.put("unstructuredname", aSN1ObjectIdentifier29);
        hashtable4.put("uniqueidentifier", aSN1ObjectIdentifier14);
        hashtable4.put("dn", aSN1ObjectIdentifier17);
        hashtable4.put("pseudonym", aSN1ObjectIdentifier18);
        hashtable4.put("postaladdress", aSN1ObjectIdentifier25);
        hashtable4.put("nameofbirth", aSN1ObjectIdentifier24);
        hashtable4.put("countryofcitizenship", aSN1ObjectIdentifier22);
        hashtable4.put("countryofresidence", aSN1ObjectIdentifier23);
        hashtable4.put("gender", aSN1ObjectIdentifier21);
        hashtable4.put("placeofbirth", aSN1ObjectIdentifier20);
        hashtable4.put("dateofbirth", aSN1ObjectIdentifier19);
        hashtable4.put("postalcode", aSN1ObjectIdentifier16);
        hashtable4.put("businesscategory", aSN1ObjectIdentifier15);
        hashtable4.put("telephonenumber", aSN1ObjectIdentifier26);
        hashtable4.put("name", aSN1ObjectIdentifier27);
    }

    public static X509Name getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static X509Name getInstance(Object obj) {
        if (obj instanceof X509Name) {
            return (X509Name) obj;
        }
        if (obj instanceof X500Name) {
            return new X509Name(ASN1Sequence.getInstance(((X500Name) obj).toASN1Primitive()));
        }
        if (obj != null) {
            return new X509Name(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    protected X509Name() {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
    }

    public X509Name(ASN1Sequence aSN1Sequence) {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
        this.seq = aSN1Sequence;
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            ASN1Set aSN1Set = ASN1Set.getInstance(((ASN1Encodable) objects.nextElement()).toASN1Primitive());
            int i = 0;
            while (i < aSN1Set.size()) {
                ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance(aSN1Set.getObjectAt(i).toASN1Primitive());
                if (aSN1Sequence2.size() != 2) {
                    throw new IllegalArgumentException("badly sized pair");
                }
                this.ordering.addElement(ASN1ObjectIdentifier.getInstance(aSN1Sequence2.getObjectAt(0)));
                ASN1Encodable objectAt = aSN1Sequence2.getObjectAt(1);
                if ((objectAt instanceof ASN1String) && !(objectAt instanceof ASN1UniversalString)) {
                    String string = ((ASN1String) objectAt).getString();
                    if (string.length() > 0 && string.charAt(0) == '#') {
                        this.values.addElement("\\" + string);
                    } else {
                        this.values.addElement(string);
                    }
                } else {
                    try {
                        this.values.addElement("#" + bytesToString(Hex.encode(objectAt.toASN1Primitive().getEncoded(ASN1Encoding.DER))));
                    } catch (IOException unused) {
                        throw new IllegalArgumentException("cannot encode value");
                    }
                }
                this.added.addElement(i != 0 ? TRUE : FALSE);
                i++;
            }
        }
    }

    public X509Name(Hashtable hashtable) {
        this((Vector) null, hashtable);
    }

    public X509Name(Vector vector, Hashtable hashtable) {
        this(vector, hashtable, new X509DefaultEntryConverter());
    }

    public X509Name(Vector vector, Hashtable hashtable, X509NameEntryConverter x509NameEntryConverter) {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
        this.converter = x509NameEntryConverter;
        if (vector != null) {
            for (int i = 0; i != vector.size(); i++) {
                this.ordering.addElement(vector.elementAt(i));
                this.added.addElement(FALSE);
            }
        } else {
            Enumeration keys = hashtable.keys();
            while (keys.hasMoreElements()) {
                this.ordering.addElement(keys.nextElement());
                this.added.addElement(FALSE);
            }
        }
        for (int i2 = 0; i2 != this.ordering.size(); i2++) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) this.ordering.elementAt(i2);
            if (hashtable.get(aSN1ObjectIdentifier) == null) {
                throw new IllegalArgumentException("No attribute for object id - " + aSN1ObjectIdentifier.getId() + " - passed to distinguished name");
            }
            this.values.addElement(hashtable.get(aSN1ObjectIdentifier));
        }
    }

    public X509Name(Vector vector, Vector vector2) {
        this(vector, vector2, new X509DefaultEntryConverter());
    }

    public X509Name(Vector vector, Vector vector2, X509NameEntryConverter x509NameEntryConverter) {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
        this.converter = x509NameEntryConverter;
        if (vector.size() != vector2.size()) {
            throw new IllegalArgumentException("oids vector must be same length as values.");
        }
        for (int i = 0; i < vector.size(); i++) {
            this.ordering.addElement(vector.elementAt(i));
            this.values.addElement(vector2.elementAt(i));
            this.added.addElement(FALSE);
        }
    }

    public X509Name(String str) {
        this(DefaultReverse, DefaultLookUp, str);
    }

    public X509Name(String str, X509NameEntryConverter x509NameEntryConverter) {
        this(DefaultReverse, DefaultLookUp, str, x509NameEntryConverter);
    }

    public X509Name(boolean z, String str) {
        this(z, DefaultLookUp, str);
    }

    public X509Name(boolean z, String str, X509NameEntryConverter x509NameEntryConverter) {
        this(z, DefaultLookUp, str, x509NameEntryConverter);
    }

    public X509Name(boolean z, Hashtable hashtable, String str) {
        this(z, hashtable, str, new X509DefaultEntryConverter());
    }

    private ASN1ObjectIdentifier decodeOID(String str, Hashtable hashtable) {
        String trim = str.trim();
        if (Strings.toUpperCase(trim).startsWith("OID.")) {
            return new ASN1ObjectIdentifier(trim.substring(4));
        }
        if (trim.charAt(0) >= '0' && trim.charAt(0) <= '9') {
            return new ASN1ObjectIdentifier(trim);
        }
        ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) hashtable.get(Strings.toLowerCase(trim));
        if (aSN1ObjectIdentifier != null) {
            return aSN1ObjectIdentifier;
        }
        throw new IllegalArgumentException("Unknown object id - " + trim + " - passed to distinguished name");
    }

    private String unescape(String str) {
        int i;
        if (str.length() == 0 || (str.indexOf(92) < 0 && str.indexOf(34) < 0)) {
            return str.trim();
        }
        char[] charArray = str.toCharArray();
        StringBuffer stringBuffer = new StringBuffer(str.length());
        if (charArray[0] == '\\' && charArray[1] == '#') {
            stringBuffer.append("\\#");
            i = 2;
        } else {
            i = 0;
        }
        boolean z = false;
        int i2 = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (i != charArray.length) {
            char c = charArray[i];
            if (c != ' ') {
                z3 = true;
            }
            if (c != '\"') {
                if (c == '\\' && !z && !z2) {
                    i2 = stringBuffer.length();
                    z = true;
                } else if (c != ' ' || z || z3) {
                    stringBuffer.append(c);
                }
                i++;
            } else if (z) {
                stringBuffer.append(c);
            } else {
                z2 = !z2;
            }
            z = false;
            i++;
        }
        if (stringBuffer.length() > 0) {
            while (stringBuffer.charAt(stringBuffer.length() - 1) == ' ' && i2 != stringBuffer.length() - 1) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }

    public X509Name(boolean z, Hashtable hashtable, String str, X509NameEntryConverter x509NameEntryConverter) {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
        this.converter = x509NameEntryConverter;
        X509NameTokenizer x509NameTokenizer = new X509NameTokenizer(str);
        while (x509NameTokenizer.hasMoreTokens()) {
            String nextToken = x509NameTokenizer.nextToken();
            if (nextToken.indexOf(43) > 0) {
                X509NameTokenizer x509NameTokenizer2 = new X509NameTokenizer(nextToken, '+');
                addEntry(hashtable, x509NameTokenizer2.nextToken(), FALSE);
                while (x509NameTokenizer2.hasMoreTokens()) {
                    addEntry(hashtable, x509NameTokenizer2.nextToken(), TRUE);
                }
            } else {
                addEntry(hashtable, nextToken, FALSE);
            }
        }
        if (z) {
            Vector vector = new Vector();
            Vector vector2 = new Vector();
            Vector vector3 = new Vector();
            int i = 1;
            for (int i2 = 0; i2 < this.ordering.size(); i2++) {
                if (((Boolean) this.added.elementAt(i2)).booleanValue()) {
                    vector.insertElementAt(this.ordering.elementAt(i2), i);
                    vector2.insertElementAt(this.values.elementAt(i2), i);
                    vector3.insertElementAt(this.added.elementAt(i2), i);
                    i++;
                } else {
                    vector.insertElementAt(this.ordering.elementAt(i2), 0);
                    vector2.insertElementAt(this.values.elementAt(i2), 0);
                    vector3.insertElementAt(this.added.elementAt(i2), 0);
                    i = 1;
                }
            }
            this.ordering = vector;
            this.values = vector2;
            this.added = vector3;
        }
    }

    private void addEntry(Hashtable hashtable, String str, Boolean bool) {
        X509NameTokenizer x509NameTokenizer = new X509NameTokenizer(str, '=');
        String nextToken = x509NameTokenizer.nextToken();
        if (!x509NameTokenizer.hasMoreTokens()) {
            throw new IllegalArgumentException("badly formatted directory string");
        }
        String nextToken2 = x509NameTokenizer.nextToken();
        this.ordering.addElement(decodeOID(nextToken, hashtable));
        this.values.addElement(unescape(nextToken2));
        this.added.addElement(bool);
    }

    public Vector getOIDs() {
        Vector vector = new Vector();
        for (int i = 0; i != this.ordering.size(); i++) {
            vector.addElement(this.ordering.elementAt(i));
        }
        return vector;
    }

    public Vector getValues() {
        Vector vector = new Vector();
        for (int i = 0; i != this.values.size(); i++) {
            vector.addElement(this.values.elementAt(i));
        }
        return vector;
    }

    public Vector getValues(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Vector vector = new Vector();
        for (int i = 0; i != this.values.size(); i++) {
            if (this.ordering.elementAt(i).equals(aSN1ObjectIdentifier)) {
                String str = (String) this.values.elementAt(i);
                if (str.length() > 2 && str.charAt(0) == '\\' && str.charAt(1) == '#') {
                    vector.addElement(str.substring(1));
                } else {
                    vector.addElement(str);
                }
            }
        }
        return vector;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        if (this.seq == null) {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            ASN1ObjectIdentifier aSN1ObjectIdentifier = null;
            int i = 0;
            while (i != this.ordering.size()) {
                ASN1EncodableVector aSN1EncodableVector3 = new ASN1EncodableVector(2);
                ASN1ObjectIdentifier aSN1ObjectIdentifier2 = (ASN1ObjectIdentifier) this.ordering.elementAt(i);
                aSN1EncodableVector3.add(aSN1ObjectIdentifier2);
                aSN1EncodableVector3.add(this.converter.getConvertedValue(aSN1ObjectIdentifier2, (String) this.values.elementAt(i)));
                if (aSN1ObjectIdentifier == null || ((Boolean) this.added.elementAt(i)).booleanValue()) {
                    aSN1EncodableVector2.add(new DERSequence(aSN1EncodableVector3));
                } else {
                    aSN1EncodableVector.add(new DERSet(aSN1EncodableVector2));
                    aSN1EncodableVector2 = new ASN1EncodableVector();
                    aSN1EncodableVector2.add(new DERSequence(aSN1EncodableVector3));
                }
                i++;
                aSN1ObjectIdentifier = aSN1ObjectIdentifier2;
            }
            aSN1EncodableVector.add(new DERSet(aSN1EncodableVector2));
            this.seq = new DERSequence(aSN1EncodableVector);
        }
        return this.seq;
    }

    public boolean equals(Object obj, boolean z) {
        if (!z) {
            return equals(obj);
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X509Name) && !(obj instanceof ASN1Sequence)) {
            return false;
        }
        if (toASN1Primitive().equals(((ASN1Encodable) obj).toASN1Primitive())) {
            return true;
        }
        try {
            X509Name x509Name = getInstance(obj);
            int size = this.ordering.size();
            if (size != x509Name.ordering.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!((ASN1ObjectIdentifier) this.ordering.elementAt(i)).equals((ASN1Primitive) x509Name.ordering.elementAt(i)) || !equivalentStrings((String) this.values.elementAt(i), (String) x509Name.values.elementAt(i))) {
                    return false;
                }
            }
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        if (this.isHashCodeCalculated) {
            return this.hashCodeValue;
        }
        this.isHashCodeCalculated = true;
        for (int i = 0; i != this.ordering.size(); i++) {
            String stripInternalSpaces = stripInternalSpaces(canonicalize((String) this.values.elementAt(i)));
            int hashCode = this.hashCodeValue ^ this.ordering.elementAt(i).hashCode();
            this.hashCodeValue = hashCode;
            this.hashCodeValue = stripInternalSpaces.hashCode() ^ hashCode;
        }
        return this.hashCodeValue;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0085, code lost:
    
        r3[r9] = true;
        r4 = r4 + r6;
     */
    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean equals(java.lang.Object r12) {
        /*
            r11 = this;
            r0 = 1
            if (r12 != r11) goto L4
            return r0
        L4:
            boolean r1 = r12 instanceof com.android.internal.org.bouncycastle.asn1.x509.X509Name
            r2 = 0
            if (r1 != 0) goto Le
            boolean r1 = r12 instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence
            if (r1 != 0) goto Le
            return r2
        Le:
            r1 = r12
            com.android.internal.org.bouncycastle.asn1.ASN1Encodable r1 = (com.android.internal.org.bouncycastle.asn1.ASN1Encodable) r1
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive r1 = r1.toASN1Primitive()
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive r3 = r11.toASN1Primitive()
            boolean r1 = r3.equals(r1)
            if (r1 == 0) goto L20
            return r0
        L20:
            com.android.internal.org.bouncycastle.asn1.x509.X509Name r12 = getInstance(r12)     // Catch: java.lang.IllegalArgumentException -> L8e
            java.util.Vector r1 = r11.ordering
            int r1 = r1.size()
            java.util.Vector r3 = r12.ordering
            int r3 = r3.size()
            if (r1 == r3) goto L33
            return r2
        L33:
            boolean[] r3 = new boolean[r1]
            java.util.Vector r4 = r11.ordering
            java.lang.Object r4 = r4.elementAt(r2)
            java.util.Vector r5 = r12.ordering
            java.lang.Object r5 = r5.elementAt(r2)
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L4b
            r6 = r0
            r5 = r1
            r4 = r2
            goto L4f
        L4b:
            int r4 = r1 + (-1)
            r5 = -1
            r6 = r5
        L4f:
            if (r4 == r5) goto L8d
            java.util.Vector r7 = r11.ordering
            java.lang.Object r7 = r7.elementAt(r4)
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier r7 = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) r7
            java.util.Vector r8 = r11.values
            java.lang.Object r8 = r8.elementAt(r4)
            java.lang.String r8 = (java.lang.String) r8
            r9 = r2
        L62:
            if (r9 >= r1) goto L8c
            boolean r10 = r3[r9]
            if (r10 == 0) goto L69
            goto L89
        L69:
            java.util.Vector r10 = r12.ordering
            java.lang.Object r10 = r10.elementAt(r9)
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier r10 = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) r10
            boolean r10 = r7.equals(r10)
            if (r10 == 0) goto L89
            java.util.Vector r10 = r12.values
            java.lang.Object r10 = r10.elementAt(r9)
            java.lang.String r10 = (java.lang.String) r10
            boolean r10 = r11.equivalentStrings(r8, r10)
            if (r10 == 0) goto L89
            r3[r9] = r0
            int r4 = r4 + r6
            goto L4f
        L89:
            int r9 = r9 + 1
            goto L62
        L8c:
            return r2
        L8d:
            return r0
        L8e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.asn1.x509.X509Name.equals(java.lang.Object):boolean");
    }

    private boolean equivalentStrings(String str, String str2) {
        String canonicalize = canonicalize(str);
        String canonicalize2 = canonicalize(str2);
        return canonicalize.equals(canonicalize2) || stripInternalSpaces(canonicalize).equals(stripInternalSpaces(canonicalize2));
    }

    private String canonicalize(String str) {
        String lowerCase = Strings.toLowerCase(str.trim());
        if (lowerCase.length() > 0 && lowerCase.charAt(0) == '#') {
            ASN1Encodable decodeObject = decodeObject(lowerCase);
            if (decodeObject instanceof ASN1String) {
                return Strings.toLowerCase(((ASN1String) decodeObject).getString().trim());
            }
        }
        return lowerCase;
    }

    private ASN1Primitive decodeObject(String str) {
        try {
            return ASN1Primitive.fromByteArray(Hex.decodeStrict(str, 1, str.length() - 1));
        } catch (IOException e) {
            throw new IllegalStateException("unknown encoding in name: " + e);
        }
    }

    private String stripInternalSpaces(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str.length() != 0) {
            char charAt = str.charAt(0);
            stringBuffer.append(charAt);
            int i = 1;
            while (i < str.length()) {
                char charAt2 = str.charAt(i);
                if (charAt != ' ' || charAt2 != ' ') {
                    stringBuffer.append(charAt2);
                }
                i++;
                charAt = charAt2;
            }
        }
        return stringBuffer.toString();
    }

    private void appendValue(StringBuffer stringBuffer, Hashtable hashtable, ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        String str2 = (String) hashtable.get(aSN1ObjectIdentifier);
        if (str2 != null) {
            stringBuffer.append(str2);
        } else {
            stringBuffer.append(aSN1ObjectIdentifier.getId());
        }
        stringBuffer.append('=');
        int length = stringBuffer.length();
        stringBuffer.append(str);
        int length2 = stringBuffer.length();
        if (str.length() >= 2 && str.charAt(0) == '\\' && str.charAt(1) == '#') {
            length += 2;
        }
        while (length < length2 && stringBuffer.charAt(length) == ' ') {
            stringBuffer.insert(length, "\\");
            length += 2;
            length2++;
        }
        while (true) {
            length2--;
            if (length2 <= length || stringBuffer.charAt(length2) != ' ') {
                break;
            } else {
                stringBuffer.insert(length2, '\\');
            }
        }
        while (length <= length2) {
            char charAt = stringBuffer.charAt(length);
            if (charAt != '\"' && charAt != '\\' && charAt != '+' && charAt != ',') {
                switch (charAt) {
                    case ';':
                    case '<':
                    case '=':
                    case '>':
                        break;
                    default:
                        length++;
                        break;
                }
            }
            stringBuffer.insert(length, "\\");
            length += 2;
            length2++;
        }
    }

    public String toString(boolean z, Hashtable hashtable) {
        StringBuffer stringBuffer = new StringBuffer();
        Vector vector = new Vector();
        StringBuffer stringBuffer2 = null;
        for (int i = 0; i < this.ordering.size(); i++) {
            if (((Boolean) this.added.elementAt(i)).booleanValue()) {
                stringBuffer2.append('+');
                appendValue(stringBuffer2, hashtable, (ASN1ObjectIdentifier) this.ordering.elementAt(i), (String) this.values.elementAt(i));
            } else {
                stringBuffer2 = new StringBuffer();
                appendValue(stringBuffer2, hashtable, (ASN1ObjectIdentifier) this.ordering.elementAt(i), (String) this.values.elementAt(i));
                vector.addElement(stringBuffer2);
            }
        }
        boolean z2 = true;
        if (z) {
            for (int size = vector.size() - 1; size >= 0; size--) {
                if (z2) {
                    z2 = false;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append(vector.elementAt(size).toString());
            }
        } else {
            for (int i2 = 0; i2 < vector.size(); i2++) {
                if (z2) {
                    z2 = false;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append(vector.elementAt(i2).toString());
            }
        }
        return stringBuffer.toString();
    }

    private String bytesToString(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length];
        for (int i = 0; i != length; i++) {
            cArr[i] = (char) (bArr[i] & 255);
        }
        return new String(cArr);
    }

    public String toString() {
        return toString(DefaultReverse, DefaultSymbols);
    }
}
