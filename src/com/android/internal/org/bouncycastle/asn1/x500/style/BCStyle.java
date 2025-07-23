package com.android.internal.org.bouncycastle.asn1.x500.style;

import android.app.blob.XmlTags;
import android.content.Context;
import android.hardware.gnss.GnssSignalType;
import android.provider.Telephony;
import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.DERIA5String;
import com.android.internal.org.bouncycastle.asn1.DERPrintableString;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x500.RDN;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle;
import com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import java.util.Hashtable;

/* loaded from: classes5.dex */
public class BCStyle extends AbstractX500NameStyle {
    public static final ASN1ObjectIdentifier BUSINESS_CATEGORY;
    public static final ASN1ObjectIdentifier C;
    public static final ASN1ObjectIdentifier CN;
    public static final ASN1ObjectIdentifier COUNTRY_OF_CITIZENSHIP;
    public static final ASN1ObjectIdentifier COUNTRY_OF_RESIDENCE;
    public static final ASN1ObjectIdentifier DATE_OF_BIRTH;
    public static final ASN1ObjectIdentifier DC;
    public static final ASN1ObjectIdentifier DESCRIPTION;
    public static final ASN1ObjectIdentifier DMD_NAME;
    public static final ASN1ObjectIdentifier DN_QUALIFIER;
    private static final Hashtable DefaultLookUp;
    private static final Hashtable DefaultSymbols;
    public static final ASN1ObjectIdentifier E;
    public static final ASN1ObjectIdentifier EmailAddress;
    public static final ASN1ObjectIdentifier GENDER;
    public static final ASN1ObjectIdentifier GENERATION;
    public static final ASN1ObjectIdentifier GIVENNAME;
    public static final ASN1ObjectIdentifier INITIALS;
    public static final X500NameStyle INSTANCE;
    public static final ASN1ObjectIdentifier L;
    public static final ASN1ObjectIdentifier NAME;
    public static final ASN1ObjectIdentifier NAME_AT_BIRTH;
    public static final ASN1ObjectIdentifier O;
    public static final ASN1ObjectIdentifier ORGANIZATION_IDENTIFIER;
    public static final ASN1ObjectIdentifier OU;
    public static final ASN1ObjectIdentifier PLACE_OF_BIRTH;
    public static final ASN1ObjectIdentifier POSTAL_ADDRESS;
    public static final ASN1ObjectIdentifier POSTAL_CODE;
    public static final ASN1ObjectIdentifier PSEUDONYM;
    public static final ASN1ObjectIdentifier ROLE;
    public static final ASN1ObjectIdentifier SERIALNUMBER;
    public static final ASN1ObjectIdentifier SN;
    public static final ASN1ObjectIdentifier ST;
    public static final ASN1ObjectIdentifier STREET;
    public static final ASN1ObjectIdentifier SURNAME;
    public static final ASN1ObjectIdentifier T;
    public static final ASN1ObjectIdentifier TELEPHONE_NUMBER;
    public static final ASN1ObjectIdentifier UID;
    public static final ASN1ObjectIdentifier UNIQUE_IDENTIFIER;
    public static final ASN1ObjectIdentifier UnstructuredAddress;
    public static final ASN1ObjectIdentifier UnstructuredName;
    protected final Hashtable defaultSymbols = copyHashTable(DefaultSymbols);
    protected final Hashtable defaultLookUp = copyHashTable(DefaultLookUp);

    static {
        ASN1ObjectIdentifier intern = new ASN1ObjectIdentifier("2.5.4.6").intern();
        C = intern;
        ASN1ObjectIdentifier intern2 = new ASN1ObjectIdentifier("2.5.4.10").intern();
        O = intern2;
        ASN1ObjectIdentifier intern3 = new ASN1ObjectIdentifier("2.5.4.11").intern();
        OU = intern3;
        ASN1ObjectIdentifier intern4 = new ASN1ObjectIdentifier("2.5.4.12").intern();
        T = intern4;
        ASN1ObjectIdentifier intern5 = new ASN1ObjectIdentifier("2.5.4.3").intern();
        CN = intern5;
        SN = new ASN1ObjectIdentifier("2.5.4.5").intern();
        ASN1ObjectIdentifier intern6 = new ASN1ObjectIdentifier("2.5.4.9").intern();
        STREET = intern6;
        ASN1ObjectIdentifier intern7 = new ASN1ObjectIdentifier("2.5.4.5").intern();
        SERIALNUMBER = intern7;
        ASN1ObjectIdentifier intern8 = new ASN1ObjectIdentifier("2.5.4.7").intern();
        L = intern8;
        ASN1ObjectIdentifier intern9 = new ASN1ObjectIdentifier("2.5.4.8").intern();
        ST = intern9;
        ASN1ObjectIdentifier intern10 = new ASN1ObjectIdentifier("2.5.4.4").intern();
        SURNAME = intern10;
        ASN1ObjectIdentifier intern11 = new ASN1ObjectIdentifier("2.5.4.42").intern();
        GIVENNAME = intern11;
        ASN1ObjectIdentifier intern12 = new ASN1ObjectIdentifier("2.5.4.43").intern();
        INITIALS = intern12;
        ASN1ObjectIdentifier intern13 = new ASN1ObjectIdentifier("2.5.4.44").intern();
        GENERATION = intern13;
        ASN1ObjectIdentifier intern14 = new ASN1ObjectIdentifier("2.5.4.45").intern();
        UNIQUE_IDENTIFIER = intern14;
        ASN1ObjectIdentifier intern15 = new ASN1ObjectIdentifier("2.5.4.13").intern();
        DESCRIPTION = intern15;
        ASN1ObjectIdentifier intern16 = new ASN1ObjectIdentifier("2.5.4.15").intern();
        BUSINESS_CATEGORY = intern16;
        ASN1ObjectIdentifier intern17 = new ASN1ObjectIdentifier("2.5.4.17").intern();
        POSTAL_CODE = intern17;
        ASN1ObjectIdentifier intern18 = new ASN1ObjectIdentifier("2.5.4.46").intern();
        DN_QUALIFIER = intern18;
        ASN1ObjectIdentifier intern19 = new ASN1ObjectIdentifier("2.5.4.65").intern();
        PSEUDONYM = intern19;
        ASN1ObjectIdentifier intern20 = new ASN1ObjectIdentifier("2.5.4.72").intern();
        ROLE = intern20;
        ASN1ObjectIdentifier intern21 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.1").intern();
        DATE_OF_BIRTH = intern21;
        ASN1ObjectIdentifier intern22 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.2").intern();
        PLACE_OF_BIRTH = intern22;
        ASN1ObjectIdentifier intern23 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.3").intern();
        GENDER = intern23;
        ASN1ObjectIdentifier intern24 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.4").intern();
        COUNTRY_OF_CITIZENSHIP = intern24;
        ASN1ObjectIdentifier intern25 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.5").intern();
        COUNTRY_OF_RESIDENCE = intern25;
        ASN1ObjectIdentifier intern26 = new ASN1ObjectIdentifier("1.3.36.8.3.14").intern();
        NAME_AT_BIRTH = intern26;
        ASN1ObjectIdentifier intern27 = new ASN1ObjectIdentifier("2.5.4.16").intern();
        POSTAL_ADDRESS = intern27;
        DMD_NAME = new ASN1ObjectIdentifier("2.5.4.54").intern();
        ASN1ObjectIdentifier aSN1ObjectIdentifier = X509ObjectIdentifiers.id_at_telephoneNumber;
        TELEPHONE_NUMBER = aSN1ObjectIdentifier;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = X509ObjectIdentifiers.id_at_name;
        NAME = aSN1ObjectIdentifier2;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = X509ObjectIdentifiers.id_at_organizationIdentifier;
        ORGANIZATION_IDENTIFIER = aSN1ObjectIdentifier3;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = PKCSObjectIdentifiers.pkcs_9_at_emailAddress;
        EmailAddress = aSN1ObjectIdentifier4;
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = PKCSObjectIdentifiers.pkcs_9_at_unstructuredName;
        UnstructuredName = aSN1ObjectIdentifier5;
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = PKCSObjectIdentifiers.pkcs_9_at_unstructuredAddress;
        UnstructuredAddress = aSN1ObjectIdentifier6;
        E = aSN1ObjectIdentifier4;
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
        DC = aSN1ObjectIdentifier7;
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
        UID = aSN1ObjectIdentifier8;
        Hashtable hashtable = new Hashtable();
        DefaultSymbols = hashtable;
        Hashtable hashtable2 = new Hashtable();
        DefaultLookUp = hashtable2;
        hashtable.put(intern, GnssSignalType.CODE_TYPE_C);
        hashtable.put(intern2, "O");
        hashtable.put(intern4, "T");
        hashtable.put(intern3, "OU");
        hashtable.put(intern5, "CN");
        hashtable.put(intern8, GnssSignalType.CODE_TYPE_L);
        hashtable.put(intern9, "ST");
        hashtable.put(intern7, "SERIALNUMBER");
        hashtable.put(aSN1ObjectIdentifier4, "E");
        hashtable.put(aSN1ObjectIdentifier7, "DC");
        hashtable.put(aSN1ObjectIdentifier8, "UID");
        hashtable.put(intern6, "STREET");
        hashtable.put(intern10, "SURNAME");
        hashtable.put(intern11, "GIVENNAME");
        hashtable.put(intern12, "INITIALS");
        hashtable.put(intern13, "GENERATION");
        hashtable.put(intern15, "DESCRIPTION");
        hashtable.put(intern20, "ROLE");
        hashtable.put(aSN1ObjectIdentifier6, "unstructuredAddress");
        hashtable.put(aSN1ObjectIdentifier5, "unstructuredName");
        hashtable.put(intern14, "UniqueIdentifier");
        hashtable.put(intern18, "DN");
        hashtable.put(intern19, "Pseudonym");
        hashtable.put(intern27, "PostalAddress");
        hashtable.put(intern26, "NameAtBirth");
        hashtable.put(intern24, "CountryOfCitizenship");
        hashtable.put(intern25, "CountryOfResidence");
        hashtable.put(intern23, "Gender");
        hashtable.put(intern22, "PlaceOfBirth");
        hashtable.put(intern21, "DateOfBirth");
        hashtable.put(intern17, "PostalCode");
        hashtable.put(intern16, "BusinessCategory");
        hashtable.put(aSN1ObjectIdentifier, "TelephoneNumber");
        hashtable.put(aSN1ObjectIdentifier2, "Name");
        hashtable.put(aSN1ObjectIdentifier3, "organizationIdentifier");
        hashtable2.put("c", intern);
        hashtable2.put("o", intern2);
        hashtable2.put("t", intern4);
        hashtable2.put("ou", intern3);
        hashtable2.put("cn", intern5);
        hashtable2.put(XmlTags.TAG_LEASEE, intern8);
        hashtable2.put(Telephony.BaseMmsColumns.STATUS, intern9);
        hashtable2.put("sn", intern10);
        hashtable2.put("serialnumber", intern7);
        hashtable2.put("street", intern6);
        hashtable2.put("emailaddress", aSN1ObjectIdentifier4);
        hashtable2.put("dc", aSN1ObjectIdentifier7);
        hashtable2.put("e", aSN1ObjectIdentifier4);
        hashtable2.put("uid", aSN1ObjectIdentifier8);
        hashtable2.put("surname", intern10);
        hashtable2.put("givenname", intern11);
        hashtable2.put("initials", intern12);
        hashtable2.put("generation", intern13);
        hashtable2.put("description", intern15);
        hashtable2.put(Context.ROLE_SERVICE, intern20);
        hashtable2.put("unstructuredaddress", aSN1ObjectIdentifier6);
        hashtable2.put("unstructuredname", aSN1ObjectIdentifier5);
        hashtable2.put("uniqueidentifier", intern14);
        hashtable2.put("dn", intern18);
        hashtable2.put("pseudonym", intern19);
        hashtable2.put("postaladdress", intern27);
        hashtable2.put("nameatbirth", intern26);
        hashtable2.put("countryofcitizenship", intern24);
        hashtable2.put("countryofresidence", intern25);
        hashtable2.put("gender", intern23);
        hashtable2.put("placeofbirth", intern22);
        hashtable2.put("dateofbirth", intern21);
        hashtable2.put("postalcode", intern17);
        hashtable2.put("businesscategory", intern16);
        hashtable2.put("telephonenumber", aSN1ObjectIdentifier);
        hashtable2.put("name", aSN1ObjectIdentifier2);
        hashtable2.put("organizationidentifier", aSN1ObjectIdentifier3);
        INSTANCE = new BCStyle();
    }

    protected BCStyle() {
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.style.AbstractX500NameStyle
    protected ASN1Encodable encodeStringValue(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) EmailAddress) || aSN1ObjectIdentifier.equals((ASN1Primitive) DC)) {
            return new DERIA5String(str);
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) DATE_OF_BIRTH)) {
            return new ASN1GeneralizedTime(str);
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) C) || aSN1ObjectIdentifier.equals((ASN1Primitive) SERIALNUMBER) || aSN1ObjectIdentifier.equals((ASN1Primitive) DN_QUALIFIER) || aSN1ObjectIdentifier.equals((ASN1Primitive) TELEPHONE_NUMBER)) {
            return new DERPrintableString(str);
        }
        return super.encodeStringValue(aSN1ObjectIdentifier, str);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public String oidToDisplayName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (String) this.defaultSymbols.get(aSN1ObjectIdentifier);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public String[] oidToAttrNames(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return IETFUtils.findAttrNamesForOID(aSN1ObjectIdentifier, this.defaultLookUp);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public ASN1ObjectIdentifier attrNameToOID(String str) {
        return IETFUtils.decodeAttrName(str, this.defaultLookUp);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public RDN[] fromString(String str) {
        return IETFUtils.rDNsFromString(str, this);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public String toString(X500Name x500Name) {
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = true;
        for (RDN rdn : x500Name.getRDNs()) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append(',');
            }
            IETFUtils.appendRDN(stringBuffer, rdn, this.defaultSymbols);
        }
        return stringBuffer.toString();
    }
}
