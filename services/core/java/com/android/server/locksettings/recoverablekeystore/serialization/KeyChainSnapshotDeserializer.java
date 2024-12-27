package com.android.server.locksettings.recoverablekeystore.serialization;

import android.security.keystore.recovery.KeyChainProtectionParams;
import android.security.keystore.recovery.KeyChainSnapshot;
import android.security.keystore.recovery.KeyDerivationParams;
import android.security.keystore.recovery.WrappedApplicationKey;
import android.util.Base64;
import android.util.Xml;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Locale;

public abstract class KeyChainSnapshotDeserializer {
    public static KeyChainSnapshot deserializeInternal(InputStream inputStream) {
        String str;
        char c;
        char c2;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        String str12;
        String str13;
        String str14;
        String str15;
        String str16;
        String str17;
        String str18;
        String str19;
        String str20;
        String str21;
        String str22;
        char c3;
        String str23;
        String str24;
        int i;
        String str25;
        String str26;
        String str27;
        String str28;
        char c4;
        String str29;
        String str30;
        String str31;
        String str32;
        String str33;
        KeyDerivationParams createSha256Params;
        String str34;
        String str35;
        String str36;
        char c5;
        String str37 = "algorithm";
        String str38 = "keyDerivationParams";
        String str39 = "userSecretType";
        String str40 = "lockScreenUiType";
        String str41 = "serverParams";
        String str42 = "applicationKeysList";
        String str43 = "snapshotVersion";
        String str44 = "thmCertPath";
        String str45 = "recoveryKeyMaterial";
        String str46 = "keyMetadata";
        String str47 = "maxAttempts";
        String str48 = "keyMaterial";
        TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(inputStream);
        resolvePullParser.nextTag();
        String str49 = "alias";
        String str50 = "memoryDifficulty";
        String str51 = "salt";
        String str52 = "keyChainSnapshot";
        resolvePullParser.require(2, (String) null, "keyChainSnapshot");
        KeyChainSnapshot.Builder builder = new KeyChainSnapshot.Builder();
        while (true) {
            String str53 = str52;
            if (resolvePullParser.next() == 3) {
                resolvePullParser.require(3, (String) null, str53);
                try {
                    return builder.build();
                } catch (NullPointerException e) {
                    throw new KeyChainSnapshotParserException(
                            "Failed to build KeyChainSnapshot", e);
                }
            }
            if (resolvePullParser.getEventType() == 2) {
                String name = resolvePullParser.getName();
                name.getClass();
                String str54 = "Unexpected tag ";
                switch (name.hashCode()) {
                    case -1719931702:
                        str = str45;
                        if (name.equals(str47)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1388433662:
                        str = str45;
                        if (name.equals("backendPublicKey")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1370381871:
                        if (name.equals(str45)) {
                            str = str45;
                            c = 2;
                            break;
                        }
                        str = str45;
                        c = 65535;
                        break;
                    case -1368437758:
                        if (name.equals(str44)) {
                            str = str45;
                            c = 3;
                            break;
                        }
                        str = str45;
                        c = 65535;
                        break;
                    case 481270388:
                        if (name.equals(str43)) {
                            c2 = 4;
                            char c6 = c2;
                            str = str45;
                            c = c6;
                            break;
                        }
                        str = str45;
                        c = 65535;
                        break;
                    case 1190285858:
                        if (name.equals(str42)) {
                            c2 = 5;
                            char c62 = c2;
                            str = str45;
                            c = c62;
                            break;
                        }
                        str = str45;
                        c = 65535;
                        break;
                    case 1352257591:
                        if (name.equals("counterId")) {
                            c2 = 6;
                            char c622 = c2;
                            str = str45;
                            c = c622;
                            break;
                        }
                        str = str45;
                        c = 65535;
                        break;
                    case 1596875199:
                        if (name.equals("keyChainProtectionParamsList")) {
                            c2 = 7;
                            char c6222 = c2;
                            str = str45;
                            c = c6222;
                            break;
                        }
                        str = str45;
                        c = 65535;
                        break;
                    case 1806980777:
                        if (name.equals(str41)) {
                            c2 = '\b';
                            char c62222 = c2;
                            str = str45;
                            c = c62222;
                            break;
                        }
                        str = str45;
                        c = 65535;
                        break;
                    default:
                        str = str45;
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        str2 = str37;
                        str3 = str41;
                        str4 = str42;
                        str5 = str43;
                        str6 = str49;
                        str7 = str50;
                        str8 = str51;
                        str9 = str;
                        str10 = str38;
                        str11 = str40;
                        str12 = str48;
                        str13 = str47;
                        str14 = str39;
                        str15 = str44;
                        str16 = str46;
                        builder.setMaxAttempts(readIntTag(resolvePullParser, str13));
                        str44 = str15;
                        str46 = str16;
                        str47 = str13;
                        str50 = str7;
                        str39 = str14;
                        str40 = str11;
                        str41 = str3;
                        str42 = str4;
                        str48 = str12;
                        str51 = str8;
                        str37 = str2;
                        str38 = str10;
                        str43 = str5;
                        str49 = str6;
                        str45 = str9;
                        break;
                    case 1:
                        str17 = str47;
                        str2 = str37;
                        str3 = str41;
                        str4 = str42;
                        str5 = str43;
                        str15 = str44;
                        str6 = str49;
                        str7 = str50;
                        str8 = str51;
                        str9 = str;
                        str10 = str38;
                        str11 = str40;
                        str12 = str48;
                        str14 = str39;
                        str16 = str46;
                        str13 = str17;
                        str44 = str15;
                        str46 = str16;
                        str47 = str13;
                        str50 = str7;
                        str39 = str14;
                        str40 = str11;
                        str41 = str3;
                        str42 = str4;
                        str48 = str12;
                        str51 = str8;
                        str37 = str2;
                        str38 = str10;
                        str43 = str5;
                        str49 = str6;
                        str45 = str9;
                        break;
                    case 2:
                        str17 = str47;
                        str2 = str37;
                        str3 = str41;
                        str4 = str42;
                        str5 = str43;
                        str15 = str44;
                        str6 = str49;
                        str7 = str50;
                        str8 = str51;
                        str9 = str;
                        str10 = str38;
                        str11 = str40;
                        str12 = str48;
                        str14 = str39;
                        str16 = str46;
                        builder.setEncryptedRecoveryKeyBlob(readBlobTag(resolvePullParser, str9));
                        str13 = str17;
                        str44 = str15;
                        str46 = str16;
                        str47 = str13;
                        str50 = str7;
                        str39 = str14;
                        str40 = str11;
                        str41 = str3;
                        str42 = str4;
                        str48 = str12;
                        str51 = str8;
                        str37 = str2;
                        str38 = str10;
                        str43 = str5;
                        str49 = str6;
                        str45 = str9;
                        break;
                    case 3:
                        str17 = str47;
                        str2 = str37;
                        str3 = str41;
                        str4 = str42;
                        str5 = str43;
                        str15 = str44;
                        str6 = str49;
                        str7 = str50;
                        str8 = str51;
                        str10 = str38;
                        str11 = str40;
                        str12 = str48;
                        str14 = str39;
                        str16 = str46;
                        try {
                            try {
                                builder.setTrustedHardwareCertPath(
                                        CertificateFactory.getInstance("X.509")
                                                .generateCertPath(
                                                        new ByteArrayInputStream(
                                                                readBlobTag(
                                                                        resolvePullParser,
                                                                        str15))));
                                str9 = str;
                                str13 = str17;
                                str44 = str15;
                                str46 = str16;
                                str47 = str13;
                                str50 = str7;
                                str39 = str14;
                                str40 = str11;
                                str41 = str3;
                                str42 = str4;
                                str48 = str12;
                                str51 = str8;
                                str37 = str2;
                                str38 = str10;
                                str43 = str5;
                                str49 = str6;
                                str45 = str9;
                                break;
                            } catch (CertificateException e2) {
                                throw new KeyChainSnapshotParserException(
                                        "Could not parse CertPath in tag " + str15, e2);
                            }
                        } catch (CertificateException e3) {
                            throw new KeyChainSnapshotParserException(
                                    "Could not set trustedHardwareCertPath", e3);
                        }
                    case 4:
                        str2 = str37;
                        str3 = str41;
                        str4 = str42;
                        str5 = str43;
                        str6 = str49;
                        str7 = str50;
                        str8 = str51;
                        str10 = str38;
                        str11 = str40;
                        str12 = str48;
                        str14 = str39;
                        str16 = str46;
                        builder.setSnapshotVersion(readIntTag(resolvePullParser, str5));
                        str9 = str;
                        str13 = str47;
                        str15 = str44;
                        str44 = str15;
                        str46 = str16;
                        str47 = str13;
                        str50 = str7;
                        str39 = str14;
                        str40 = str11;
                        str41 = str3;
                        str42 = str4;
                        str48 = str12;
                        str51 = str8;
                        str37 = str2;
                        str38 = str10;
                        str43 = str5;
                        str49 = str6;
                        str45 = str9;
                        break;
                    case 5:
                        String str55 = str47;
                        str2 = str37;
                        str3 = str41;
                        str4 = str42;
                        String str56 = str43;
                        String str57 = str44;
                        str7 = str50;
                        str8 = str51;
                        int i2 = 2;
                        String str58 = null;
                        resolvePullParser.require(2, (String) null, str4);
                        ArrayList arrayList = new ArrayList();
                        while (true) {
                            int i3 = 3;
                            if (resolvePullParser.next() == 3) {
                                str11 = str40;
                                str6 = str49;
                                str10 = str38;
                                str12 = str48;
                                str14 = str39;
                                str16 = str46;
                                resolvePullParser.require(3, str58, str4);
                                builder.setWrappedApplicationKeys(arrayList);
                                str9 = str;
                                str13 = str55;
                                str15 = str57;
                                str5 = str56;
                                break;
                            } else if (resolvePullParser.getEventType() == i2) {
                                resolvePullParser.require(i2, str58, "applicationKey");
                                WrappedApplicationKey.Builder builder2 =
                                        new WrappedApplicationKey.Builder();
                                while (resolvePullParser.next() != i3) {
                                    if (resolvePullParser.getEventType() != 2) {
                                        i3 = 3;
                                    } else {
                                        String name2 = resolvePullParser.getName();
                                        name2.getClass();
                                        switch (name2.hashCode()) {
                                            case -1712279890:
                                                str18 = str49;
                                                str19 = str38;
                                                str20 = str48;
                                                str21 = str39;
                                                str22 = str46;
                                                if (name2.equals(str22)) {
                                                    c3 = 0;
                                                    break;
                                                }
                                                c3 = 65535;
                                                break;
                                            case -963209050:
                                                str18 = str49;
                                                str19 = str38;
                                                str20 = str48;
                                                if (name2.equals(str20)) {
                                                    str21 = str39;
                                                    str22 = str46;
                                                    c3 = 1;
                                                    break;
                                                }
                                                str21 = str39;
                                                str22 = str46;
                                                c3 = 65535;
                                                break;
                                            case 92902992:
                                                str18 = str49;
                                                if (name2.equals(str18)) {
                                                    str19 = str38;
                                                    str20 = str48;
                                                    str21 = str39;
                                                    str22 = str46;
                                                    c3 = 2;
                                                    break;
                                                }
                                                str19 = str38;
                                                str20 = str48;
                                                str21 = str39;
                                                str22 = str46;
                                                c3 = 65535;
                                                break;
                                            default:
                                                str18 = str49;
                                                str19 = str38;
                                                str20 = str48;
                                                str21 = str39;
                                                str22 = str46;
                                                c3 = 65535;
                                                break;
                                        }
                                        switch (c3) {
                                            case 0:
                                                str23 = str54;
                                                str24 = str40;
                                                i = 3;
                                                builder2.setMetadata(
                                                        readBlobTag(resolvePullParser, str22));
                                                break;
                                            case 1:
                                                str23 = str54;
                                                str24 = str40;
                                                i = 3;
                                                builder2.setEncryptedKeyMaterial(
                                                        readBlobTag(resolvePullParser, str20));
                                                break;
                                            case 2:
                                                str23 = str54;
                                                resolvePullParser.require(2, (String) null, str18);
                                                String readText = readText(resolvePullParser);
                                                str24 = str40;
                                                i = 3;
                                                resolvePullParser.require(3, (String) null, str18);
                                                builder2.setAlias(readText);
                                                break;
                                            default:
                                                Locale locale = Locale.US;
                                                throw new KeyChainSnapshotParserException(
                                                        XmlUtils$$ExternalSyntheticOutline0.m(
                                                                str54,
                                                                name2,
                                                                " in wrappedApplicationKey"));
                                        }
                                        str54 = str23;
                                        str46 = str22;
                                        str39 = str21;
                                        str48 = str20;
                                        str38 = str19;
                                        str49 = str18;
                                        i3 = i;
                                        str40 = str24;
                                    }
                                }
                                String str59 = str40;
                                int i4 = i3;
                                String str60 = str49;
                                String str61 = str38;
                                String str62 = str48;
                                String str63 = str39;
                                String str64 = str46;
                                String str65 = str54;
                                resolvePullParser.require(i4, (String) null, "applicationKey");
                                try {
                                    arrayList.add(builder2.build());
                                    str54 = str65;
                                    str40 = str59;
                                    i2 = 2;
                                    str58 = null;
                                    str46 = str64;
                                    str39 = str63;
                                    str48 = str62;
                                    str38 = str61;
                                    str49 = str60;
                                } catch (NullPointerException e4) {
                                    throw new KeyChainSnapshotParserException(
                                            "Failed to build WrappedApplicationKey", e4);
                                }
                            }
                        }
                    case 6:
                        str25 = str47;
                        str2 = str37;
                        str3 = str41;
                        str26 = str43;
                        str27 = str44;
                        str8 = str51;
                        str28 = str42;
                        str7 = str50;
                        resolvePullParser.require(2, (String) null, "counterId");
                        String readText2 = readText(resolvePullParser);
                        resolvePullParser.require(3, (String) null, "counterId");
                        try {
                            builder.setCounterId(Long.valueOf(readText2).longValue());
                            str6 = str49;
                            str4 = str28;
                            str9 = str;
                            str15 = str27;
                            str5 = str26;
                            str10 = str38;
                            str11 = str40;
                            str12 = str48;
                            str13 = str25;
                            str14 = str39;
                            str16 = str46;
                            str44 = str15;
                            str46 = str16;
                            str47 = str13;
                            str50 = str7;
                            str39 = str14;
                            str40 = str11;
                            str41 = str3;
                            str42 = str4;
                            str48 = str12;
                            str51 = str8;
                            str37 = str2;
                            str38 = str10;
                            str43 = str5;
                            str49 = str6;
                            str45 = str9;
                            break;
                        } catch (NumberFormatException e5) {
                            Locale locale2 = Locale.US;
                            throw new KeyChainSnapshotParserException(
                                    XmlUtils$$ExternalSyntheticOutline0.m(
                                            "counterId expected long but got '", readText2, "'"),
                                    e5);
                        }
                    case 7:
                        resolvePullParser.require(2, (String) null, "keyChainProtectionParamsList");
                        ArrayList arrayList2 = new ArrayList();
                        while (true) {
                            str3 = str41;
                            if (resolvePullParser.next() == 3) {
                                str25 = str47;
                                str2 = str37;
                                str26 = str43;
                                str27 = str44;
                                str8 = str51;
                                str28 = str42;
                                str7 = str50;
                                resolvePullParser.require(
                                        3, (String) null, "keyChainProtectionParamsList");
                                builder.setKeyChainProtectionParams(arrayList2);
                                break;
                            } else if (resolvePullParser.getEventType() != 2) {
                                str41 = str3;
                            } else {
                                String str66 = str47;
                                resolvePullParser.require(
                                        2, (String) null, "keyChainProtectionParams");
                                KeyChainProtectionParams.Builder builder3 =
                                        new KeyChainProtectionParams.Builder();
                                while (true) {
                                    String str67 = str44;
                                    if (resolvePullParser.next() == 3) {
                                        String str68 = str37;
                                        String str69 = str43;
                                        String str70 = str51;
                                        String str71 = str42;
                                        String str72 = str50;
                                        resolvePullParser.require(
                                                3, (String) null, "keyChainProtectionParams");
                                        try {
                                            arrayList2.add(builder3.build());
                                            str50 = str72;
                                            str37 = str68;
                                            str42 = str71;
                                            str41 = str3;
                                            str47 = str66;
                                            str44 = str67;
                                            str51 = str70;
                                            str43 = str69;
                                        } catch (NullPointerException e6) {
                                            throw new KeyChainSnapshotParserException(
                                                    "Failed to build KeyChainProtectionParams", e6);
                                        }
                                    } else if (resolvePullParser.getEventType() != 2) {
                                        str44 = str67;
                                    } else {
                                        String name3 = resolvePullParser.getName();
                                        name3.getClass();
                                        switch (name3.hashCode()) {
                                            case -776797115:
                                                if (name3.equals(str40)) {
                                                    c4 = 0;
                                                    break;
                                                }
                                                c4 = 65535;
                                                break;
                                            case -696958923:
                                                if (name3.equals(str39)) {
                                                    c4 = 1;
                                                    break;
                                                }
                                                c4 = 65535;
                                                break;
                                            case 912448924:
                                                if (name3.equals(str38)) {
                                                    c4 = 2;
                                                    break;
                                                }
                                                c4 = 65535;
                                                break;
                                            default:
                                                c4 = 65535;
                                                break;
                                        }
                                        switch (c4) {
                                            case 0:
                                                str29 = str37;
                                                str30 = str43;
                                                str31 = str51;
                                                str32 = str42;
                                                str33 = str50;
                                                builder3.setLockScreenUiFormat(
                                                        readIntTag(resolvePullParser, str40));
                                                break;
                                            case 1:
                                                str29 = str37;
                                                str30 = str43;
                                                str31 = str51;
                                                str32 = str42;
                                                str33 = str50;
                                                builder3.setUserSecretType(
                                                        readIntTag(resolvePullParser, str39));
                                                break;
                                            case 2:
                                                resolvePullParser.require(2, (String) null, str38);
                                                int i5 = -1;
                                                int i6 = -1;
                                                byte[] bArr = null;
                                                while (true) {
                                                    str30 = str43;
                                                    if (resolvePullParser.next() != 3) {
                                                        if (resolvePullParser.getEventType() == 2) {
                                                            String name4 =
                                                                    resolvePullParser.getName();
                                                            name4.getClass();
                                                            switch (name4.hashCode()) {
                                                                case -973274212:
                                                                    str34 = str51;
                                                                    str35 = str42;
                                                                    str36 = str50;
                                                                    if (name4.equals(str36)) {
                                                                        c5 = 0;
                                                                        break;
                                                                    }
                                                                    c5 = 65535;
                                                                    break;
                                                                case 3522646:
                                                                    str34 = str51;
                                                                    if (name4.equals(str34)) {
                                                                        str35 = str42;
                                                                        str36 = str50;
                                                                        c5 = 1;
                                                                        break;
                                                                    }
                                                                    str35 = str42;
                                                                    str36 = str50;
                                                                    c5 = 65535;
                                                                    break;
                                                                case 225490031:
                                                                    if (name4.equals(str37)) {
                                                                        str34 = str51;
                                                                        str35 = str42;
                                                                        str36 = str50;
                                                                        c5 = 2;
                                                                        break;
                                                                    }
                                                                default:
                                                                    str34 = str51;
                                                                    str35 = str42;
                                                                    str36 = str50;
                                                                    c5 = 65535;
                                                                    break;
                                                            }
                                                            switch (c5) {
                                                                case 0:
                                                                    i6 =
                                                                            readIntTag(
                                                                                    resolvePullParser,
                                                                                    str36);
                                                                    break;
                                                                case 1:
                                                                    bArr =
                                                                            readBlobTag(
                                                                                    resolvePullParser,
                                                                                    str34);
                                                                    break;
                                                                case 2:
                                                                    i5 =
                                                                            readIntTag(
                                                                                    resolvePullParser,
                                                                                    str37);
                                                                    break;
                                                                default:
                                                                    Locale locale3 = Locale.US;
                                                                    throw new KeyChainSnapshotParserException(
                                                                            XmlUtils$$ExternalSyntheticOutline0
                                                                                    .m(
                                                                                            "Unexpected"
                                                                                                + " tag ",
                                                                                            name4,
                                                                                            " in keyDerivationParams"));
                                                            }
                                                            str50 = str36;
                                                            str42 = str35;
                                                            str51 = str34;
                                                        }
                                                        str43 = str30;
                                                    } else {
                                                        str31 = str51;
                                                        str32 = str42;
                                                        str33 = str50;
                                                        if (bArr == null) {
                                                            throw new KeyChainSnapshotParserException(
                                                                    "salt was not set in"
                                                                        + " keyDerivationParams");
                                                        }
                                                        str29 = str37;
                                                        int i7 = i5;
                                                        if (i7 == 1) {
                                                            createSha256Params =
                                                                    KeyDerivationParams
                                                                            .createSha256Params(
                                                                                    bArr);
                                                        } else {
                                                            if (i7 != 2) {
                                                                throw new KeyChainSnapshotParserException(
                                                                        "Unknown algorithm in"
                                                                            + " keyDerivationParams");
                                                            }
                                                            createSha256Params =
                                                                    KeyDerivationParams
                                                                            .createScryptParams(
                                                                                    bArr, i6);
                                                        }
                                                        resolvePullParser.require(
                                                                3, (String) null, str38);
                                                        builder3.setKeyDerivationParams(
                                                                createSha256Params);
                                                        break;
                                                    }
                                                }
                                            default:
                                                Locale locale4 = Locale.US;
                                                throw new KeyChainSnapshotParserException(
                                                        XmlUtils$$ExternalSyntheticOutline0.m(
                                                                "Unexpected tag ",
                                                                name3,
                                                                " in keyChainProtectionParams"));
                                        }
                                        str50 = str33;
                                        str37 = str29;
                                        str42 = str32;
                                        str44 = str67;
                                        str51 = str31;
                                        str43 = str30;
                                    }
                                }
                            }
                        }
                    case '\b':
                        builder.setServerParams(readBlobTag(resolvePullParser, str41));
                        str2 = str37;
                        str3 = str41;
                        str4 = str42;
                        str5 = str43;
                        str6 = str49;
                        str7 = str50;
                        str8 = str51;
                        str9 = str;
                        str10 = str38;
                        str11 = str40;
                        str12 = str48;
                        str13 = str47;
                        str14 = str39;
                        str15 = str44;
                        str16 = str46;
                        str44 = str15;
                        str46 = str16;
                        str47 = str13;
                        str50 = str7;
                        str39 = str14;
                        str40 = str11;
                        str41 = str3;
                        str42 = str4;
                        str48 = str12;
                        str51 = str8;
                        str37 = str2;
                        str38 = str10;
                        str43 = str5;
                        str49 = str6;
                        str45 = str9;
                        break;
                    default:
                        Locale locale5 = Locale.US;
                        throw new KeyChainSnapshotParserException(
                                XmlUtils$$ExternalSyntheticOutline0.m(
                                        "Unexpected tag ", name, " in keyChainSnapshot"));
                }
            }
            str52 = str53;
        }
    }

    public static byte[] readBlobTag(TypedXmlPullParser typedXmlPullParser, String str) {
        typedXmlPullParser.require(2, (String) null, str);
        String readText = readText(typedXmlPullParser);
        typedXmlPullParser.require(3, (String) null, str);
        try {
            return Base64.decode(readText, 0);
        } catch (IllegalArgumentException e) {
            Locale locale = Locale.US;
            throw new KeyChainSnapshotParserException(
                    str + " expected base64 encoded bytes but got '" + readText + "'", e);
        }
    }

    public static int readIntTag(TypedXmlPullParser typedXmlPullParser, String str) {
        typedXmlPullParser.require(2, (String) null, str);
        String readText = readText(typedXmlPullParser);
        typedXmlPullParser.require(3, (String) null, str);
        try {
            return Integer.valueOf(readText).intValue();
        } catch (NumberFormatException e) {
            Locale locale = Locale.US;
            throw new KeyChainSnapshotParserException(
                    str + " expected int but got '" + readText + "'", e);
        }
    }

    public static String readText(TypedXmlPullParser typedXmlPullParser) {
        if (typedXmlPullParser.next() != 4) {
            return "";
        }
        String text = typedXmlPullParser.getText();
        typedXmlPullParser.nextTag();
        return text;
    }
}
