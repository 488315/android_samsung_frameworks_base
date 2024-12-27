package com.android.server.security;

import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;
import android.util.Slog;

import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.Certificate;

import java.io.ByteArrayInputStream;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AttestationVerificationSelfTrustedVerifierForTesting {
    public static final boolean DEBUG;
    public static final String GOLDEN_ALIAS;
    public static volatile AttestationVerificationSelfTrustedVerifierForTesting
            sAttestationVerificationSelfTrustedVerifier;
    public final X509Certificate mGoldenRootCert;
    public final CertificateFactory mCertificateFactory = CertificateFactory.getInstance("X.509");
    public final CertPathValidator mCertPathValidator = CertPathValidator.getInstance("PKIX");

    static {
        DEBUG = Build.IS_DEBUGGABLE && Log.isLoggable("AVF", 2);
        GOLDEN_ALIAS =
                AttestationVerificationSelfTrustedVerifierForTesting.class.getCanonicalName()
                        + ".Golden";
        sAttestationVerificationSelfTrustedVerifier = null;
    }

    public AttestationVerificationSelfTrustedVerifierForTesting() {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        String str = GOLDEN_ALIAS;
        if (!keyStore.containsAlias(str)) {
            KeyPairGenerator keyPairGenerator =
                    KeyPairGenerator.getInstance("EC", "AndroidKeyStore");
            keyPairGenerator.initialize(
                    new KeyGenParameterSpec.Builder(str, 12)
                            .setAttestationChallenge(str.getBytes())
                            .setDigests("SHA-256", "SHA-512")
                            .build());
            keyPairGenerator.generateKeyPair();
        }
        this.mGoldenRootCert =
                ((X509Certificate[])
                                ((KeyStore.PrivateKeyEntry) keyStore.getEntry(str, null))
                                        .getCertificateChain())
                        [r0.length - 1];
    }

    public static byte[] getChallengeFromCert(X509Certificate x509Certificate) {
        return Certificate.getInstance(
                        new ASN1InputStream(x509Certificate.getEncoded()).readObject())
                .getTBSCertificate()
                .getExtensions()
                .getExtensionParsedValue(new ASN1ObjectIdentifier("1.3.6.1.4.1.11129.2.1.17"))
                .getObjectAt(4)
                .getOctets();
    }

    public final int verifyAttestation(int i, byte[] bArr, Bundle bundle) {
        boolean z = DEBUG;
        ArrayList arrayList = new ArrayList();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        while (byteArrayInputStream.available() > 0) {
            try {
                arrayList.add(
                        (X509Certificate)
                                this.mCertificateFactory.generateCertificate(byteArrayInputStream));
            } catch (CertificateException e) {
                if (z) {
                    Slog.v("AVF", "Unable to parse certificates from attestation", e);
                }
                return 2;
            }
        }
        if (i == 3) {
            if (bundle.size() != 1) {
                if (z) {
                    Slog.v("AVF", "Requirements does not contain exactly 1 key.");
                }
            } else if (bundle.containsKey("localbinding.challenge")) {
                try {
                    if (Arrays.equals(
                            bundle.getByteArray("localbinding.challenge"),
                            getChallengeFromCert((X509Certificate) arrayList.get(0)))) {
                        if (arrayList.size() >= 2) {
                            try {
                                CertPath generateCertPath =
                                        this.mCertificateFactory.generateCertPath(arrayList);
                                PKIXParameters pKIXParameters =
                                        new PKIXParameters(
                                                (Set<TrustAnchor>)
                                                        Collections.singleton(
                                                                new TrustAnchor(
                                                                        this.mGoldenRootCert,
                                                                        null)));
                                pKIXParameters.setRevocationEnabled(false);
                                this.mCertPathValidator.validate(generateCertPath, pKIXParameters);
                                return 1;
                            } catch (Throwable th) {
                                if (z) {
                                    Slog.v("AVF", "Invalid certificate chain", th);
                                }
                            }
                        } else if (z) {
                            Slog.v("AVF", "Certificate chain less than 2 in size.");
                        }
                    } else if (z) {
                        Slog.v("AVF", "Self-Trusted validation failed; challenge mismatch.");
                    }
                } catch (Throwable th2) {
                    if (z) {
                        Slog.v("AVF", "Unable to parse challenge from certificate.", th2);
                    }
                }
            } else if (z) {
                Slog.v("AVF", "Requirements does not contain key: localbinding.challenge");
            }
        }
        return 2;
    }
}
