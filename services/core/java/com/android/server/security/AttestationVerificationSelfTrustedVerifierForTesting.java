package com.android.server.security;

import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;
import android.util.Slog;
import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import java.io.ByteArrayInputStream;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public class AttestationVerificationSelfTrustedVerifierForTesting {
  public static final boolean DEBUG;
  public static final String GOLDEN_ALIAS;
  public static volatile AttestationVerificationSelfTrustedVerifierForTesting
      sAttestationVerificationSelfTrustedVerifier;
  public final KeyStore mAndroidKeyStore;
  public X509Certificate mGoldenRootCert;
  public final CertificateFactory mCertificateFactory = CertificateFactory.getInstance("X.509");
  public final CertPathValidator mCertPathValidator = CertPathValidator.getInstance("PKIX");

  static {
    DEBUG = Build.IS_DEBUGGABLE && Log.isLoggable("AVF", 2);
    GOLDEN_ALIAS =
        AttestationVerificationSelfTrustedVerifierForTesting.class.getCanonicalName() + ".Golden";
    sAttestationVerificationSelfTrustedVerifier = null;
  }

  public static AttestationVerificationSelfTrustedVerifierForTesting getInstance() {
    if (sAttestationVerificationSelfTrustedVerifier == null) {
      synchronized (AttestationVerificationSelfTrustedVerifierForTesting.class) {
        if (sAttestationVerificationSelfTrustedVerifier == null) {
          sAttestationVerificationSelfTrustedVerifier =
              new AttestationVerificationSelfTrustedVerifierForTesting();
        }
      }
    }
    return sAttestationVerificationSelfTrustedVerifier;
  }

  public static void debugVerboseLog(String str, Throwable th) {
    if (DEBUG) {
      Slog.v("AVF", str, th);
    }
  }

  public static void debugVerboseLog(String str) {
    if (DEBUG) {
      Slog.v("AVF", str);
    }
  }

  public AttestationVerificationSelfTrustedVerifierForTesting() {
    KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
    this.mAndroidKeyStore = keyStore;
    keyStore.load(null);
    String str = GOLDEN_ALIAS;
    if (!keyStore.containsAlias(str)) {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "AndroidKeyStore");
      keyPairGenerator.initialize(
          new KeyGenParameterSpec.Builder(str, 12)
              .setAttestationChallenge(str.getBytes())
              .setDigests("SHA-256", "SHA-512")
              .build());
      keyPairGenerator.generateKeyPair();
    }
    this.mGoldenRootCert =
        ((X509Certificate[])
                ((KeyStore.PrivateKeyEntry) keyStore.getEntry(str, null)).getCertificateChain())
            [r0.length - 1];
  }

  public int verifyAttestation(int i, Bundle bundle, byte[] bArr) {
    ArrayList arrayList = new ArrayList();
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
    while (byteArrayInputStream.available() > 0) {
      try {
        arrayList.add(
            (X509Certificate) this.mCertificateFactory.generateCertificate(byteArrayInputStream));
      } catch (CertificateException e) {
        debugVerboseLog("Unable to parse certificates from attestation", e);
        return 2;
      }
    }
    return (i == 3
            && validateRequirements(bundle)
            && checkLeafChallenge(bundle, arrayList)
            && verifyCertificateChain(arrayList))
        ? 1
        : 2;
  }

  public final boolean verifyCertificateChain(List list) {
    if (list.size() < 2) {
      debugVerboseLog("Certificate chain less than 2 in size.");
      return false;
    }
    try {
      CertPath generateCertPath =
          this.mCertificateFactory.generateCertPath((List<? extends Certificate>) list);
      PKIXParameters pKIXParameters = new PKIXParameters((Set<TrustAnchor>) getTrustAnchors());
      pKIXParameters.setRevocationEnabled(false);
      this.mCertPathValidator.validate(generateCertPath, pKIXParameters);
      return true;
    } catch (Throwable th) {
      debugVerboseLog("Invalid certificate chain", th);
      return false;
    }
  }

  public final Set getTrustAnchors() {
    return Collections.singleton(new TrustAnchor(this.mGoldenRootCert, null));
  }

  public final boolean validateRequirements(Bundle bundle) {
    if (bundle.size() != 1) {
      debugVerboseLog("Requirements does not contain exactly 1 key.");
      return false;
    }
    if (bundle.containsKey("localbinding.challenge")) {
      return true;
    }
    debugVerboseLog("Requirements does not contain key: localbinding.challenge");
    return false;
  }

  public final boolean checkLeafChallenge(Bundle bundle, List list) {
    try {
      if (Arrays.equals(
          bundle.getByteArray("localbinding.challenge"),
          getChallengeFromCert((X509Certificate) list.get(0)))) {
        return true;
      }
      debugVerboseLog("Self-Trusted validation failed; challenge mismatch.");
      return false;
    } catch (Throwable th) {
      debugVerboseLog("Unable to parse challenge from certificate.", th);
      return false;
    }
  }

  public final byte[] getChallengeFromCert(X509Certificate x509Certificate) {
    return com.android.internal.org.bouncycastle.asn1.x509.Certificate.getInstance(
            new ASN1InputStream(x509Certificate.getEncoded()).readObject())
        .getTBSCertificate()
        .getExtensions()
        .getExtensionParsedValue(new ASN1ObjectIdentifier("1.3.6.1.4.1.11129.2.1.17"))
        .getObjectAt(4)
        .getOctets();
  }
}
