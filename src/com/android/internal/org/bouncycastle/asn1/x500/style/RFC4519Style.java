package com.android.internal.org.bouncycastle.asn1.x500.style;

import android.app.blob.XmlTags;
import android.provider.Telephony;
import android.view.View;
import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.DERIA5String;
import com.android.internal.org.bouncycastle.asn1.DERPrintableString;
import com.android.internal.org.bouncycastle.asn1.x500.RDN;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle;
import java.util.Hashtable;

/* loaded from: classes5.dex */
public class RFC4519Style extends AbstractX500NameStyle {
    private static final Hashtable DefaultLookUp;
    private static final Hashtable DefaultSymbols;
    public static final X500NameStyle INSTANCE;
    public static final ASN1ObjectIdentifier businessCategory;
    public static final ASN1ObjectIdentifier c;
    public static final ASN1ObjectIdentifier cn;
    public static final ASN1ObjectIdentifier dc;
    public static final ASN1ObjectIdentifier description;
    public static final ASN1ObjectIdentifier destinationIndicator;
    public static final ASN1ObjectIdentifier distinguishedName;
    public static final ASN1ObjectIdentifier dnQualifier;
    public static final ASN1ObjectIdentifier enhancedSearchGuide;
    public static final ASN1ObjectIdentifier facsimileTelephoneNumber;
    public static final ASN1ObjectIdentifier generationQualifier;
    public static final ASN1ObjectIdentifier givenName;
    public static final ASN1ObjectIdentifier houseIdentifier;
    public static final ASN1ObjectIdentifier initials;
    public static final ASN1ObjectIdentifier internationalISDNNumber;
    public static final ASN1ObjectIdentifier l;
    public static final ASN1ObjectIdentifier member;
    public static final ASN1ObjectIdentifier name;
    public static final ASN1ObjectIdentifier o;
    public static final ASN1ObjectIdentifier ou;
    public static final ASN1ObjectIdentifier owner;
    public static final ASN1ObjectIdentifier physicalDeliveryOfficeName;
    public static final ASN1ObjectIdentifier postOfficeBox;
    public static final ASN1ObjectIdentifier postalAddress;
    public static final ASN1ObjectIdentifier postalCode;
    public static final ASN1ObjectIdentifier preferredDeliveryMethod;
    public static final ASN1ObjectIdentifier registeredAddress;
    public static final ASN1ObjectIdentifier roleOccupant;
    public static final ASN1ObjectIdentifier searchGuide;
    public static final ASN1ObjectIdentifier seeAlso;
    public static final ASN1ObjectIdentifier serialNumber;
    public static final ASN1ObjectIdentifier sn;
    public static final ASN1ObjectIdentifier st;
    public static final ASN1ObjectIdentifier street;
    public static final ASN1ObjectIdentifier telephoneNumber;
    public static final ASN1ObjectIdentifier teletexTerminalIdentifier;
    public static final ASN1ObjectIdentifier telexNumber;
    public static final ASN1ObjectIdentifier title;
    public static final ASN1ObjectIdentifier uid;
    public static final ASN1ObjectIdentifier uniqueMember;
    public static final ASN1ObjectIdentifier userPassword;
    public static final ASN1ObjectIdentifier x121Address;
    public static final ASN1ObjectIdentifier x500UniqueIdentifier;
    protected final Hashtable defaultSymbols = copyHashTable(DefaultSymbols);
    protected final Hashtable defaultLookUp = copyHashTable(DefaultLookUp);

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern = new ASN1ObjectIdentifier("2.5.4.15").intern();
        businessCategory = aSN1ObjectIdentifierIntern;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern2 = new ASN1ObjectIdentifier("2.5.4.6").intern();
        c = aSN1ObjectIdentifierIntern2;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern3 = new ASN1ObjectIdentifier("2.5.4.3").intern();
        cn = aSN1ObjectIdentifierIntern3;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern4 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25").intern();
        dc = aSN1ObjectIdentifierIntern4;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern5 = new ASN1ObjectIdentifier("2.5.4.13").intern();
        description = aSN1ObjectIdentifierIntern5;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern6 = new ASN1ObjectIdentifier("2.5.4.27").intern();
        destinationIndicator = aSN1ObjectIdentifierIntern6;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern7 = new ASN1ObjectIdentifier("2.5.4.49").intern();
        distinguishedName = aSN1ObjectIdentifierIntern7;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern8 = new ASN1ObjectIdentifier("2.5.4.46").intern();
        dnQualifier = aSN1ObjectIdentifierIntern8;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern9 = new ASN1ObjectIdentifier("2.5.4.47").intern();
        enhancedSearchGuide = aSN1ObjectIdentifierIntern9;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern10 = new ASN1ObjectIdentifier("2.5.4.23").intern();
        facsimileTelephoneNumber = aSN1ObjectIdentifierIntern10;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern11 = new ASN1ObjectIdentifier("2.5.4.44").intern();
        generationQualifier = aSN1ObjectIdentifierIntern11;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern12 = new ASN1ObjectIdentifier("2.5.4.42").intern();
        givenName = aSN1ObjectIdentifierIntern12;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern13 = new ASN1ObjectIdentifier("2.5.4.51").intern();
        houseIdentifier = aSN1ObjectIdentifierIntern13;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern14 = new ASN1ObjectIdentifier("2.5.4.43").intern();
        initials = aSN1ObjectIdentifierIntern14;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern15 = new ASN1ObjectIdentifier("2.5.4.25").intern();
        internationalISDNNumber = aSN1ObjectIdentifierIntern15;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern16 = new ASN1ObjectIdentifier("2.5.4.7").intern();
        l = aSN1ObjectIdentifierIntern16;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern17 = new ASN1ObjectIdentifier("2.5.4.31").intern();
        member = aSN1ObjectIdentifierIntern17;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern18 = new ASN1ObjectIdentifier("2.5.4.41").intern();
        name = aSN1ObjectIdentifierIntern18;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern19 = new ASN1ObjectIdentifier("2.5.4.10").intern();
        o = aSN1ObjectIdentifierIntern19;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern20 = new ASN1ObjectIdentifier("2.5.4.11").intern();
        ou = aSN1ObjectIdentifierIntern20;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern21 = new ASN1ObjectIdentifier("2.5.4.32").intern();
        owner = aSN1ObjectIdentifierIntern21;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern22 = new ASN1ObjectIdentifier("2.5.4.19").intern();
        physicalDeliveryOfficeName = aSN1ObjectIdentifierIntern22;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern23 = new ASN1ObjectIdentifier("2.5.4.16").intern();
        postalAddress = aSN1ObjectIdentifierIntern23;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern24 = new ASN1ObjectIdentifier("2.5.4.17").intern();
        postalCode = aSN1ObjectIdentifierIntern24;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern25 = new ASN1ObjectIdentifier("2.5.4.18").intern();
        postOfficeBox = aSN1ObjectIdentifierIntern25;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern26 = new ASN1ObjectIdentifier("2.5.4.28").intern();
        preferredDeliveryMethod = aSN1ObjectIdentifierIntern26;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern27 = new ASN1ObjectIdentifier("2.5.4.26").intern();
        registeredAddress = aSN1ObjectIdentifierIntern27;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern28 = new ASN1ObjectIdentifier("2.5.4.33").intern();
        roleOccupant = aSN1ObjectIdentifierIntern28;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern29 = new ASN1ObjectIdentifier("2.5.4.14").intern();
        searchGuide = aSN1ObjectIdentifierIntern29;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern30 = new ASN1ObjectIdentifier("2.5.4.34").intern();
        seeAlso = aSN1ObjectIdentifierIntern30;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern31 = new ASN1ObjectIdentifier("2.5.4.5").intern();
        serialNumber = aSN1ObjectIdentifierIntern31;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern32 = new ASN1ObjectIdentifier("2.5.4.4").intern();
        sn = aSN1ObjectIdentifierIntern32;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern33 = new ASN1ObjectIdentifier("2.5.4.8").intern();
        st = aSN1ObjectIdentifierIntern33;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern34 = new ASN1ObjectIdentifier("2.5.4.9").intern();
        street = aSN1ObjectIdentifierIntern34;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern35 = new ASN1ObjectIdentifier("2.5.4.20").intern();
        telephoneNumber = aSN1ObjectIdentifierIntern35;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern36 = new ASN1ObjectIdentifier("2.5.4.22").intern();
        teletexTerminalIdentifier = aSN1ObjectIdentifierIntern36;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern37 = new ASN1ObjectIdentifier("2.5.4.21").intern();
        telexNumber = aSN1ObjectIdentifierIntern37;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern38 = new ASN1ObjectIdentifier("2.5.4.12").intern();
        title = aSN1ObjectIdentifierIntern38;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern39 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1").intern();
        uid = aSN1ObjectIdentifierIntern39;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern40 = new ASN1ObjectIdentifier("2.5.4.50").intern();
        uniqueMember = aSN1ObjectIdentifierIntern40;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern41 = new ASN1ObjectIdentifier("2.5.4.35").intern();
        userPassword = aSN1ObjectIdentifierIntern41;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern42 = new ASN1ObjectIdentifier("2.5.4.24").intern();
        x121Address = aSN1ObjectIdentifierIntern42;
        ASN1ObjectIdentifier aSN1ObjectIdentifierIntern43 = new ASN1ObjectIdentifier("2.5.4.45").intern();
        x500UniqueIdentifier = aSN1ObjectIdentifierIntern43;
        Hashtable hashtable = new Hashtable();
        DefaultSymbols = hashtable;
        Hashtable hashtable2 = new Hashtable();
        DefaultLookUp = hashtable2;
        hashtable.put(aSN1ObjectIdentifierIntern, "businessCategory");
        hashtable.put(aSN1ObjectIdentifierIntern2, "c");
        hashtable.put(aSN1ObjectIdentifierIntern3, "cn");
        hashtable.put(aSN1ObjectIdentifierIntern4, "dc");
        hashtable.put(aSN1ObjectIdentifierIntern5, "description");
        hashtable.put(aSN1ObjectIdentifierIntern6, "destinationIndicator");
        hashtable.put(aSN1ObjectIdentifierIntern7, "distinguishedName");
        hashtable.put(aSN1ObjectIdentifierIntern8, "dnQualifier");
        hashtable.put(aSN1ObjectIdentifierIntern9, "enhancedSearchGuide");
        hashtable.put(aSN1ObjectIdentifierIntern10, "facsimileTelephoneNumber");
        hashtable.put(aSN1ObjectIdentifierIntern11, "generationQualifier");
        hashtable.put(aSN1ObjectIdentifierIntern12, "givenName");
        hashtable.put(aSN1ObjectIdentifierIntern13, "houseIdentifier");
        hashtable.put(aSN1ObjectIdentifierIntern14, "initials");
        hashtable.put(aSN1ObjectIdentifierIntern15, "internationalISDNNumber");
        hashtable.put(aSN1ObjectIdentifierIntern16, XmlTags.TAG_LEASEE);
        hashtable.put(aSN1ObjectIdentifierIntern17, "member");
        hashtable.put(aSN1ObjectIdentifierIntern18, "name");
        hashtable.put(aSN1ObjectIdentifierIntern19, "o");
        hashtable.put(aSN1ObjectIdentifierIntern20, "ou");
        hashtable.put(aSN1ObjectIdentifierIntern21, "owner");
        hashtable.put(aSN1ObjectIdentifierIntern22, "physicalDeliveryOfficeName");
        hashtable.put(aSN1ObjectIdentifierIntern23, View.AUTOFILL_HINT_POSTAL_ADDRESS);
        hashtable.put(aSN1ObjectIdentifierIntern24, View.AUTOFILL_HINT_POSTAL_CODE);
        hashtable.put(aSN1ObjectIdentifierIntern25, "postOfficeBox");
        hashtable.put(aSN1ObjectIdentifierIntern26, "preferredDeliveryMethod");
        hashtable.put(aSN1ObjectIdentifierIntern27, "registeredAddress");
        hashtable.put(aSN1ObjectIdentifierIntern28, "roleOccupant");
        hashtable.put(aSN1ObjectIdentifierIntern29, "searchGuide");
        hashtable.put(aSN1ObjectIdentifierIntern30, "seeAlso");
        hashtable.put(aSN1ObjectIdentifierIntern31, "serialNumber");
        hashtable.put(aSN1ObjectIdentifierIntern32, "sn");
        hashtable.put(aSN1ObjectIdentifierIntern33, Telephony.BaseMmsColumns.STATUS);
        hashtable.put(aSN1ObjectIdentifierIntern34, "street");
        hashtable.put(aSN1ObjectIdentifierIntern35, "telephoneNumber");
        hashtable.put(aSN1ObjectIdentifierIntern36, "teletexTerminalIdentifier");
        hashtable.put(aSN1ObjectIdentifierIntern37, "telexNumber");
        hashtable.put(aSN1ObjectIdentifierIntern38, "title");
        hashtable.put(aSN1ObjectIdentifierIntern39, "uid");
        hashtable.put(aSN1ObjectIdentifierIntern40, "uniqueMember");
        hashtable.put(aSN1ObjectIdentifierIntern41, "userPassword");
        hashtable.put(aSN1ObjectIdentifierIntern42, "x121Address");
        hashtable.put(aSN1ObjectIdentifierIntern43, "x500UniqueIdentifier");
        hashtable2.put("businesscategory", aSN1ObjectIdentifierIntern);
        hashtable2.put("c", aSN1ObjectIdentifierIntern2);
        hashtable2.put("cn", aSN1ObjectIdentifierIntern3);
        hashtable2.put("dc", aSN1ObjectIdentifierIntern4);
        hashtable2.put("description", aSN1ObjectIdentifierIntern5);
        hashtable2.put("destinationindicator", aSN1ObjectIdentifierIntern6);
        hashtable2.put("distinguishedname", aSN1ObjectIdentifierIntern7);
        hashtable2.put("dnqualifier", aSN1ObjectIdentifierIntern8);
        hashtable2.put("enhancedsearchguide", aSN1ObjectIdentifierIntern9);
        hashtable2.put("facsimiletelephonenumber", aSN1ObjectIdentifierIntern10);
        hashtable2.put("generationqualifier", aSN1ObjectIdentifierIntern11);
        hashtable2.put("givenname", aSN1ObjectIdentifierIntern12);
        hashtable2.put("houseidentifier", aSN1ObjectIdentifierIntern13);
        hashtable2.put("initials", aSN1ObjectIdentifierIntern14);
        hashtable2.put("internationalisdnnumber", aSN1ObjectIdentifierIntern15);
        hashtable2.put(XmlTags.TAG_LEASEE, aSN1ObjectIdentifierIntern16);
        hashtable2.put("member", aSN1ObjectIdentifierIntern17);
        hashtable2.put("name", aSN1ObjectIdentifierIntern18);
        hashtable2.put("o", aSN1ObjectIdentifierIntern19);
        hashtable2.put("ou", aSN1ObjectIdentifierIntern20);
        hashtable2.put("owner", aSN1ObjectIdentifierIntern21);
        hashtable2.put("physicaldeliveryofficename", aSN1ObjectIdentifierIntern22);
        hashtable2.put("postaladdress", aSN1ObjectIdentifierIntern23);
        hashtable2.put("postalcode", aSN1ObjectIdentifierIntern24);
        hashtable2.put("postofficebox", aSN1ObjectIdentifierIntern25);
        hashtable2.put("preferreddeliverymethod", aSN1ObjectIdentifierIntern26);
        hashtable2.put("registeredaddress", aSN1ObjectIdentifierIntern27);
        hashtable2.put("roleoccupant", aSN1ObjectIdentifierIntern28);
        hashtable2.put("searchguide", aSN1ObjectIdentifierIntern29);
        hashtable2.put("seealso", aSN1ObjectIdentifierIntern30);
        hashtable2.put("serialnumber", aSN1ObjectIdentifierIntern31);
        hashtable2.put("sn", aSN1ObjectIdentifierIntern32);
        hashtable2.put(Telephony.BaseMmsColumns.STATUS, aSN1ObjectIdentifierIntern33);
        hashtable2.put("street", aSN1ObjectIdentifierIntern34);
        hashtable2.put("telephonenumber", aSN1ObjectIdentifierIntern35);
        hashtable2.put("teletexterminalidentifier", aSN1ObjectIdentifierIntern36);
        hashtable2.put("telexnumber", aSN1ObjectIdentifierIntern37);
        hashtable2.put("title", aSN1ObjectIdentifierIntern38);
        hashtable2.put("uid", aSN1ObjectIdentifierIntern39);
        hashtable2.put("uniquemember", aSN1ObjectIdentifierIntern40);
        hashtable2.put("userpassword", aSN1ObjectIdentifierIntern41);
        hashtable2.put("x121address", aSN1ObjectIdentifierIntern42);
        hashtable2.put("x500uniqueidentifier", aSN1ObjectIdentifierIntern43);
        INSTANCE = new RFC4519Style();
    }

    protected RFC4519Style() {
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.style.AbstractX500NameStyle
    protected ASN1Encodable encodeStringValue(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) dc)) {
            return new DERIA5String(str);
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) c) || aSN1ObjectIdentifier.equals((ASN1Primitive) serialNumber) || aSN1ObjectIdentifier.equals((ASN1Primitive) dnQualifier) || aSN1ObjectIdentifier.equals((ASN1Primitive) telephoneNumber)) {
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
        RDN[] rdnArrRDNsFromString = IETFUtils.rDNsFromString(str, this);
        RDN[] rdnArr = new RDN[rdnArrRDNsFromString.length];
        for (int i = 0; i != rdnArrRDNsFromString.length; i++) {
            rdnArr[(r5 - i) - 1] = rdnArrRDNsFromString[i];
        }
        return rdnArr;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public String toString(X500Name x500Name) {
        StringBuffer stringBuffer = new StringBuffer();
        RDN[] rDNs = x500Name.getRDNs();
        boolean z = true;
        for (int length = rDNs.length - 1; length >= 0; length--) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append(',');
            }
            IETFUtils.appendRDN(stringBuffer, rDNs[length], this.defaultSymbols);
        }
        return stringBuffer.toString();
    }
}
