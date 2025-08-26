package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable;
import com.android.internal.org.bouncycastle.asn1.x9.X962Parameters;
import com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder;
import com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves;
import com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec;
import com.android.internal.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.field.FiniteField;
import com.android.internal.org.bouncycastle.math.field.Polynomial;
import com.android.internal.org.bouncycastle.math.field.PolynomialExtensionField;
import com.android.internal.org.bouncycastle.util.Arrays;
import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public class EC5Util {

    private static class CustomCurves {
        private static Map CURVE_MAP = createCurveMap();

        private CustomCurves() {
        }

        private static Map createCurveMap() {
            HashMap map = new HashMap();
            Enumeration names = CustomNamedCurves.getNames();
            while (names.hasMoreElements()) {
                String str = (String) names.nextElement();
                X9ECParametersHolder byNameLazy = ECNamedCurveTable.getByNameLazy(str);
                if (byNameLazy != null) {
                    ECCurve curve = byNameLazy.getCurve();
                    if (ECAlgorithms.isFpCurve(curve)) {
                        map.put(curve, CustomNamedCurves.getByNameLazy(str).getCurve());
                    }
                }
            }
            ECCurve curve2 = CustomNamedCurves.getByNameLazy("Curve25519").getCurve();
            map.put(new ECCurve.Fp(curve2.getField().getCharacteristic(), curve2.getA().toBigInteger(), curve2.getB().toBigInteger(), curve2.getOrder(), curve2.getCofactor(), true), curve2);
            return map;
        }

        static ECCurve substitute(ECCurve eCCurve) {
            ECCurve eCCurve2 = (ECCurve) CURVE_MAP.get(eCCurve);
            return eCCurve2 != null ? eCCurve2 : eCCurve;
        }
    }

    public static ECCurve getCurve(ProviderConfiguration providerConfiguration, X962Parameters x962Parameters) {
        Set acceptableNamedCurves = providerConfiguration.getAcceptableNamedCurves();
        if (x962Parameters.isNamedCurve()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = ASN1ObjectIdentifier.getInstance(x962Parameters.getParameters());
            if (acceptableNamedCurves.isEmpty() || acceptableNamedCurves.contains(aSN1ObjectIdentifier)) {
                X9ECParameters namedCurveByOid = ECUtil.getNamedCurveByOid(aSN1ObjectIdentifier);
                if (namedCurveByOid == null) {
                    namedCurveByOid = (X9ECParameters) providerConfiguration.getAdditionalECParameters().get(aSN1ObjectIdentifier);
                }
                return namedCurveByOid.getCurve();
            }
            throw new IllegalStateException("named curve not acceptable");
        }
        if (x962Parameters.isImplicitlyCA()) {
            return providerConfiguration.getEcImplicitlyCa().getCurve();
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(x962Parameters.getParameters());
        if (acceptableNamedCurves.isEmpty()) {
            if (aSN1Sequence.size() > 3) {
                return X9ECParameters.getInstance(aSN1Sequence).getCurve();
            }
            throw new IllegalStateException("GOST is not supported");
        }
        throw new IllegalStateException("encoded parameters not acceptable");
    }

    public static ECDomainParameters getDomainParameters(ProviderConfiguration providerConfiguration, ECParameterSpec eCParameterSpec) {
        if (eCParameterSpec == null) {
            com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec ecImplicitlyCa = providerConfiguration.getEcImplicitlyCa();
            return new ECDomainParameters(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getG(), ecImplicitlyCa.getN(), ecImplicitlyCa.getH(), ecImplicitlyCa.getSeed());
        }
        return ECUtil.getDomainParameters(providerConfiguration, convertSpec(eCParameterSpec));
    }

    public static ECParameterSpec convertToSpec(X962Parameters x962Parameters, ECCurve eCCurve) {
        if (x962Parameters.isNamedCurve()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) x962Parameters.getParameters();
            X9ECParameters namedCurveByOid = ECUtil.getNamedCurveByOid(aSN1ObjectIdentifier);
            if (namedCurveByOid == null) {
                Map additionalECParameters = BouncyCastleProvider.CONFIGURATION.getAdditionalECParameters();
                if (!additionalECParameters.isEmpty()) {
                    namedCurveByOid = (X9ECParameters) additionalECParameters.get(aSN1ObjectIdentifier);
                }
            }
            return new ECNamedCurveSpec(ECUtil.getCurveName(aSN1ObjectIdentifier), convertCurve(eCCurve, namedCurveByOid.getSeed()), convertPoint(namedCurveByOid.getG()), namedCurveByOid.getN(), namedCurveByOid.getH());
        }
        if (x962Parameters.isImplicitlyCA()) {
            return null;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(x962Parameters.getParameters());
        if (aSN1Sequence.size() <= 3) {
            return null;
        }
        X9ECParameters x9ECParameters = X9ECParameters.getInstance(aSN1Sequence);
        EllipticCurve ellipticCurveConvertCurve = convertCurve(eCCurve, x9ECParameters.getSeed());
        if (x9ECParameters.getH() != null) {
            return new ECParameterSpec(ellipticCurveConvertCurve, convertPoint(x9ECParameters.getG()), x9ECParameters.getN(), x9ECParameters.getH().intValue());
        }
        return new ECParameterSpec(ellipticCurveConvertCurve, convertPoint(x9ECParameters.getG()), x9ECParameters.getN(), 1);
    }

    public static ECParameterSpec convertToSpec(X9ECParameters x9ECParameters) {
        return new ECParameterSpec(convertCurve(x9ECParameters.getCurve(), null), convertPoint(x9ECParameters.getG()), x9ECParameters.getN(), x9ECParameters.getH().intValue());
    }

    public static ECParameterSpec convertToSpec(ECDomainParameters eCDomainParameters) {
        return new ECParameterSpec(convertCurve(eCDomainParameters.getCurve(), null), convertPoint(eCDomainParameters.getG()), eCDomainParameters.getN(), eCDomainParameters.getH().intValue());
    }

    public static EllipticCurve convertCurve(ECCurve eCCurve, byte[] bArr) {
        return new EllipticCurve(convertField(eCCurve.getField()), eCCurve.getA().toBigInteger(), eCCurve.getB().toBigInteger(), null);
    }

    public static ECCurve convertCurve(EllipticCurve ellipticCurve) {
        ECField field = ellipticCurve.getField();
        BigInteger a = ellipticCurve.getA();
        BigInteger b = ellipticCurve.getB();
        if (field instanceof ECFieldFp) {
            return CustomCurves.substitute(new ECCurve.Fp(((ECFieldFp) field).getP(), a, b, null, null));
        }
        ECFieldF2m eCFieldF2m = (ECFieldF2m) field;
        int m = eCFieldF2m.getM();
        int[] iArrConvertMidTerms = ECUtil.convertMidTerms(eCFieldF2m.getMidTermsOfReductionPolynomial());
        return new ECCurve.F2m(m, iArrConvertMidTerms[0], iArrConvertMidTerms[1], iArrConvertMidTerms[2], a, b, (BigInteger) null, (BigInteger) null);
    }

    public static ECField convertField(FiniteField finiteField) {
        if (ECAlgorithms.isFpField(finiteField)) {
            return new ECFieldFp(finiteField.getCharacteristic());
        }
        Polynomial minimalPolynomial = ((PolynomialExtensionField) finiteField).getMinimalPolynomial();
        int[] exponentsPresent = minimalPolynomial.getExponentsPresent();
        return new ECFieldF2m(minimalPolynomial.getDegree(), Arrays.reverseInPlace(Arrays.copyOfRange(exponentsPresent, 1, exponentsPresent.length - 1)));
    }

    public static ECParameterSpec convertSpec(EllipticCurve ellipticCurve, com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        ECPoint eCPointConvertPoint = convertPoint(eCParameterSpec.getG());
        if (eCParameterSpec instanceof ECNamedCurveParameterSpec) {
            return new ECNamedCurveSpec(((ECNamedCurveParameterSpec) eCParameterSpec).getName(), ellipticCurve, eCPointConvertPoint, eCParameterSpec.getN(), eCParameterSpec.getH());
        }
        return new ECParameterSpec(ellipticCurve, eCPointConvertPoint, eCParameterSpec.getN(), eCParameterSpec.getH().intValue());
    }

    public static com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec convertSpec(ECParameterSpec eCParameterSpec) {
        ECCurve eCCurveConvertCurve = convertCurve(eCParameterSpec.getCurve());
        com.android.internal.org.bouncycastle.math.ec.ECPoint eCPointConvertPoint = convertPoint(eCCurveConvertCurve, eCParameterSpec.getGenerator());
        BigInteger order = eCParameterSpec.getOrder();
        BigInteger bigIntegerValueOf = BigInteger.valueOf(eCParameterSpec.getCofactor());
        byte[] seed = eCParameterSpec.getCurve().getSeed();
        if (eCParameterSpec instanceof ECNamedCurveSpec) {
            return new ECNamedCurveParameterSpec(((ECNamedCurveSpec) eCParameterSpec).getName(), eCCurveConvertCurve, eCPointConvertPoint, order, bigIntegerValueOf, seed);
        }
        return new com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec(eCCurveConvertCurve, eCPointConvertPoint, order, bigIntegerValueOf, seed);
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint convertPoint(ECParameterSpec eCParameterSpec, ECPoint eCPoint) {
        return convertPoint(convertCurve(eCParameterSpec.getCurve()), eCPoint);
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint convertPoint(ECCurve eCCurve, ECPoint eCPoint) {
        return eCCurve.createPoint(eCPoint.getAffineX(), eCPoint.getAffineY());
    }

    public static ECPoint convertPoint(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        com.android.internal.org.bouncycastle.math.ec.ECPoint eCPointNormalize = eCPoint.normalize();
        return new ECPoint(eCPointNormalize.getAffineXCoord().toBigInteger(), eCPointNormalize.getAffineYCoord().toBigInteger());
    }
}
