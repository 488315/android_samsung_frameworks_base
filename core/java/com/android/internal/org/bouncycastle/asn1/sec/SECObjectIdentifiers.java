package com.android.internal.org.bouncycastle.asn1.sec;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;

/* loaded from: classes5.dex */
public interface SECObjectIdentifiers {
    public static final ASN1ObjectIdentifier ellipticCurve = new ASN1ObjectIdentifier("1.3.132.0");
    public static final ASN1ObjectIdentifier sect163k1 = ellipticCurve.branch("1");
    public static final ASN1ObjectIdentifier sect163r1 = ellipticCurve.branch("2");
    public static final ASN1ObjectIdentifier sect239k1 = ellipticCurve.branch("3");
    public static final ASN1ObjectIdentifier sect113r1 = ellipticCurve.branch("4");
    public static final ASN1ObjectIdentifier sect113r2 = ellipticCurve.branch("5");
    public static final ASN1ObjectIdentifier secp112r1 = ellipticCurve.branch("6");
    public static final ASN1ObjectIdentifier secp112r2 = ellipticCurve.branch("7");
    public static final ASN1ObjectIdentifier secp160r1 = ellipticCurve.branch("8");
    public static final ASN1ObjectIdentifier secp160k1 = ellipticCurve.branch("9");
    public static final ASN1ObjectIdentifier secp256k1 = ellipticCurve.branch("10");
    public static final ASN1ObjectIdentifier sect163r2 = ellipticCurve.branch("15");
    public static final ASN1ObjectIdentifier sect283k1 = ellipticCurve.branch("16");
    public static final ASN1ObjectIdentifier sect283r1 = ellipticCurve.branch("17");
    public static final ASN1ObjectIdentifier sect131r1 = ellipticCurve.branch("22");
    public static final ASN1ObjectIdentifier sect131r2 = ellipticCurve.branch("23");
    public static final ASN1ObjectIdentifier sect193r1 = ellipticCurve.branch("24");
    public static final ASN1ObjectIdentifier sect193r2 = ellipticCurve.branch("25");
    public static final ASN1ObjectIdentifier sect233k1 = ellipticCurve.branch("26");
    public static final ASN1ObjectIdentifier sect233r1 = ellipticCurve.branch("27");
    public static final ASN1ObjectIdentifier secp128r1 = ellipticCurve.branch("28");
    public static final ASN1ObjectIdentifier secp128r2 = ellipticCurve.branch("29");
    public static final ASN1ObjectIdentifier secp160r2 = ellipticCurve.branch("30");
    public static final ASN1ObjectIdentifier secp192k1 = ellipticCurve.branch("31");
    public static final ASN1ObjectIdentifier secp224k1 = ellipticCurve.branch("32");
    public static final ASN1ObjectIdentifier secp224r1 = ellipticCurve.branch("33");
    public static final ASN1ObjectIdentifier secp384r1 = ellipticCurve.branch("34");
    public static final ASN1ObjectIdentifier secp521r1 = ellipticCurve.branch("35");
    public static final ASN1ObjectIdentifier sect409k1 = ellipticCurve.branch("36");
    public static final ASN1ObjectIdentifier sect409r1 = ellipticCurve.branch("37");
    public static final ASN1ObjectIdentifier sect571k1 = ellipticCurve.branch("38");
    public static final ASN1ObjectIdentifier sect571r1 = ellipticCurve.branch("39");
    public static final ASN1ObjectIdentifier secp192r1 = X9ObjectIdentifiers.prime192v1;
    public static final ASN1ObjectIdentifier secp256r1 = X9ObjectIdentifiers.prime256v1;
    public static final ASN1ObjectIdentifier secg_scheme = new ASN1ObjectIdentifier("1.3.132.1");
    public static final ASN1ObjectIdentifier dhSinglePass_stdDH_sha224kdf_scheme = secg_scheme.branch("11.0");
    public static final ASN1ObjectIdentifier dhSinglePass_stdDH_sha256kdf_scheme = secg_scheme.branch("11.1");
    public static final ASN1ObjectIdentifier dhSinglePass_stdDH_sha384kdf_scheme = secg_scheme.branch("11.2");
    public static final ASN1ObjectIdentifier dhSinglePass_stdDH_sha512kdf_scheme = secg_scheme.branch("11.3");
    public static final ASN1ObjectIdentifier dhSinglePass_cofactorDH_sha224kdf_scheme = secg_scheme.branch("14.0");
    public static final ASN1ObjectIdentifier dhSinglePass_cofactorDH_sha256kdf_scheme = secg_scheme.branch("14.1");
    public static final ASN1ObjectIdentifier dhSinglePass_cofactorDH_sha384kdf_scheme = secg_scheme.branch("14.2");
    public static final ASN1ObjectIdentifier dhSinglePass_cofactorDH_sha512kdf_scheme = secg_scheme.branch("14.3");
    public static final ASN1ObjectIdentifier mqvSinglePass_sha224kdf_scheme = secg_scheme.branch("15.0");
    public static final ASN1ObjectIdentifier mqvSinglePass_sha256kdf_scheme = secg_scheme.branch("15.1");
    public static final ASN1ObjectIdentifier mqvSinglePass_sha384kdf_scheme = secg_scheme.branch("15.2");
    public static final ASN1ObjectIdentifier mqvSinglePass_sha512kdf_scheme = secg_scheme.branch("15.3");
    public static final ASN1ObjectIdentifier mqvFull_sha224kdf_scheme = secg_scheme.branch("16.0");
    public static final ASN1ObjectIdentifier mqvFull_sha256kdf_scheme = secg_scheme.branch("16.1");
    public static final ASN1ObjectIdentifier mqvFull_sha384kdf_scheme = secg_scheme.branch("16.2");
    public static final ASN1ObjectIdentifier mqvFull_sha512kdf_scheme = secg_scheme.branch("16.3");
}
