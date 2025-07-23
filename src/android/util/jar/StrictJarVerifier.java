package android.util.jar;

import android.security.keystore.KeyProperties;
import android.util.jar.StrictJarManifest;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import sun.security.jca.Providers;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;

/* loaded from: classes4.dex */
class StrictJarVerifier {
    private static final String[] DIGEST_ALGORITHMS = {KeyProperties.DIGEST_SHA512, KeyProperties.DIGEST_SHA384, "SHA-256", "SHA1"};
    private static final int MAX_JAR_SIGNERS = 10;
    private static final String SF_ATTRIBUTE_ANDROID_APK_SIGNED_NAME = "X-Android-APK-Signed";
    private final String jarName;
    private final int mainAttributesEnd;
    private final StrictJarManifest manifest;
    private final HashMap<String, byte[]> metaEntries;
    private final boolean signatureSchemeRollbackProtectionsEnforced;
    private final Hashtable<String, HashMap<String, Attributes>> signatures = new Hashtable<>(5);
    private final Hashtable<String, Certificate[]> certificates = new Hashtable<>(5);
    private final Hashtable<String, Certificate[][]> verifiedEntries = new Hashtable<>();

    static class VerifierEntry extends OutputStream {
        private final Certificate[][] certChains;
        private final MessageDigest digest;
        private final byte[] hash;
        private final String name;
        private final Hashtable<String, Certificate[][]> verifiedEntries;

        VerifierEntry(String str, MessageDigest messageDigest, byte[] bArr, Certificate[][] certificateArr, Hashtable<String, Certificate[][]> hashtable) {
            this.name = str;
            this.digest = messageDigest;
            this.hash = bArr;
            this.certChains = certificateArr;
            this.verifiedEntries = hashtable;
        }

        @Override // java.io.OutputStream
        public void write(int i) {
            this.digest.update((byte) i);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) {
            this.digest.update(bArr, i, i2);
        }

        void verify() {
            if (!StrictJarVerifier.verifyMessageDigest(this.digest.digest(), this.hash)) {
                String str = this.name;
                throw StrictJarVerifier.invalidDigest("META-INF/MANIFEST.MF", str, str);
            }
            this.verifiedEntries.put(this.name, this.certChains);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static SecurityException invalidDigest(String str, String str2, String str3) {
        throw new SecurityException(str + " has invalid digest for " + str2 + " in " + str3);
    }

    private static SecurityException failedVerification(String str, String str2) {
        throw new SecurityException(str + " failed verification of " + str2);
    }

    private static SecurityException failedVerification(String str, String str2, Throwable th) {
        throw new SecurityException(str + " failed verification of " + str2, th);
    }

    StrictJarVerifier(String str, StrictJarManifest strictJarManifest, HashMap<String, byte[]> hashMap, boolean z) {
        this.jarName = str;
        this.manifest = strictJarManifest;
        this.metaEntries = hashMap;
        this.mainAttributesEnd = strictJarManifest.getMainAttributesEnd();
        this.signatureSchemeRollbackProtectionsEnforced = z;
    }

    VerifierEntry initEntry(String str) {
        Attributes attributes;
        String str2;
        if (this.manifest == null || this.signatures.isEmpty() || (attributes = this.manifest.getAttributes(str)) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, HashMap<String, Attributes>> entry : this.signatures.entrySet()) {
            if (entry.getValue().get(str) != null) {
                Certificate[] certificateArr = this.certificates.get(entry.getKey());
                if (certificateArr != null) {
                    arrayList.add(certificateArr);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        Certificate[][] certificateArr2 = (Certificate[][]) arrayList.toArray(new Certificate[arrayList.size()][]);
        int i = 0;
        while (true) {
            String[] strArr = DIGEST_ALGORITHMS;
            if (i >= strArr.length) {
                break;
            }
            String str3 = strArr[i];
            String value = attributes.getValue(str3 + "-Digest");
            if (value != null) {
                byte[] bytes = value.getBytes(StandardCharsets.ISO_8859_1);
                try {
                    str2 = str;
                    try {
                        return new VerifierEntry(str2, MessageDigest.getInstance(str3), bytes, certificateArr2, this.verifiedEntries);
                    } catch (NoSuchAlgorithmException unused) {
                        continue;
                    }
                } catch (NoSuchAlgorithmException unused2) {
                }
            }
            str2 = str;
            i++;
            str = str2;
        }
    }

    void addMetaEntry(String str, byte[] bArr) {
        this.metaEntries.put(str.toUpperCase(Locale.US), bArr);
    }

    synchronized boolean readCertificates() {
        int i = 0;
        if (this.metaEntries.isEmpty()) {
            return false;
        }
        Iterator<String> it = this.metaEntries.keySet().iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (next.endsWith(".DSA") || next.endsWith(".RSA") || next.endsWith(".EC")) {
                i++;
                if (i > 10) {
                    throw new SecurityException("APK Signature Scheme v1 only supports a maximum of 10 signers");
                }
                verifyCertificate(next);
                it.remove();
            }
        }
        return true;
    }

    static Certificate[] verifyBytes(byte[] bArr, byte[] bArr2) throws GeneralSecurityException {
        try {
            try {
                Object startJarVerification = Providers.startJarVerification();
                PKCS7 pkcs7 = new PKCS7(bArr);
                SignerInfo[] verify = pkcs7.verify(bArr2);
                if (verify == null || verify.length == 0) {
                    throw new GeneralSecurityException("Failed to verify signature: no verified SignerInfos");
                }
                ArrayList certificateChain = verify[0].getCertificateChain(pkcs7);
                if (certificateChain == null) {
                    throw new GeneralSecurityException("Failed to find verified SignerInfo certificate chain");
                }
                if (certificateChain.isEmpty()) {
                    throw new GeneralSecurityException("Verified SignerInfo certificate chain is emtpy");
                }
                Certificate[] certificateArr = (Certificate[]) certificateChain.toArray(new X509Certificate[certificateChain.size()]);
                Providers.stopJarVerification(startJarVerification);
                return certificateArr;
            } catch (IOException e) {
                throw new GeneralSecurityException("IO exception verifying jar cert", e);
            }
        } catch (Throwable th) {
            Providers.stopJarVerification((Object) null);
            throw th;
        }
    }

    private void verifyCertificate(String str) {
        byte[] bArr;
        StrictJarVerifier strictJarVerifier;
        GeneralSecurityException generalSecurityException;
        StrictJarVerifier strictJarVerifier2;
        String value;
        boolean z;
        boolean z2;
        StringBuilder sb = new StringBuilder();
        boolean z3 = false;
        sb.append(str.substring(0, str.lastIndexOf(46)));
        sb.append(".SF");
        String sb2 = sb.toString();
        byte[] bArr2 = this.metaEntries.get(sb2);
        if (bArr2 == null || (bArr = this.metaEntries.get("META-INF/MANIFEST.MF")) == null) {
            return;
        }
        try {
            Certificate[] verifyBytes = verifyBytes(this.metaEntries.get(str), bArr2);
            if (verifyBytes != null) {
                try {
                    this.certificates.put(sb2, verifyBytes);
                } catch (GeneralSecurityException e) {
                    generalSecurityException = e;
                    strictJarVerifier = this;
                    throw failedVerification(strictJarVerifier.jarName, sb2, generalSecurityException);
                }
            }
            Attributes attributes = new Attributes();
            HashMap<String, Attributes> hashMap = new HashMap<>();
            try {
                new StrictJarManifestReader(bArr2, attributes).readEntries(hashMap, null);
                if (this.signatureSchemeRollbackProtectionsEnforced && (value = attributes.getValue(SF_ATTRIBUTE_ANDROID_APK_SIGNED_NAME)) != null) {
                    StringTokenizer stringTokenizer = new StringTokenizer(value, ",");
                    while (true) {
                        if (!stringTokenizer.hasMoreTokens()) {
                            z = false;
                            z2 = false;
                            break;
                        }
                        String trim = stringTokenizer.nextToken().trim();
                        if (!trim.isEmpty()) {
                            try {
                                int parseInt = Integer.parseInt(trim);
                                if (parseInt != 2) {
                                    if (parseInt == 3) {
                                        z = false;
                                        z2 = true;
                                        break;
                                    }
                                } else {
                                    z2 = false;
                                    z = true;
                                    break;
                                }
                            } catch (Exception unused) {
                                continue;
                            }
                        }
                    }
                    if (z) {
                        throw new SecurityException(sb2 + " indicates " + this.jarName + " is signed using APK Signature Scheme v2, but no such signature was found. Signature stripped?");
                    }
                    if (z2) {
                        throw new SecurityException(sb2 + " indicates " + this.jarName + " is signed using APK Signature Scheme v3, but no such signature was found. Signature stripped?");
                    }
                }
                if (attributes.get(Attributes.Name.SIGNATURE_VERSION) == null) {
                    return;
                }
                String value2 = attributes.getValue("Created-By");
                if (value2 != null && value2.indexOf("signtool") != -1) {
                    z3 = true;
                }
                int i = this.mainAttributesEnd;
                if (i <= 0 || z3) {
                    strictJarVerifier2 = this;
                } else {
                    strictJarVerifier2 = this;
                    if (!strictJarVerifier2.verify(attributes, "-Digest-Manifest-Main-Attributes", bArr, 0, i, false, true)) {
                        throw failedVerification(strictJarVerifier2.jarName, sb2);
                    }
                }
                if (!strictJarVerifier2.verify(attributes, z3 ? "-Digest" : "-Digest-Manifest", bArr, 0, bArr.length, false, false)) {
                    for (Map.Entry<String, Attributes> entry : hashMap.entrySet()) {
                        StrictJarManifest.Chunk chunk = strictJarVerifier2.manifest.getChunk(entry.getKey());
                        if (chunk == null) {
                            return;
                        }
                        boolean z4 = z3;
                        if (!strictJarVerifier2.verify(entry.getValue(), "-Digest", bArr, chunk.start, chunk.end, z4, false)) {
                            throw invalidDigest(sb2, entry.getKey(), strictJarVerifier2.jarName);
                        }
                        z3 = z4;
                    }
                }
                strictJarVerifier2.metaEntries.put(sb2, null);
                strictJarVerifier2.signatures.put(sb2, hashMap);
            } catch (IOException unused2) {
            }
        } catch (GeneralSecurityException e2) {
            strictJarVerifier = this;
            generalSecurityException = e2;
        }
    }

    boolean isSignedJar() {
        return this.certificates.size() > 0;
    }

    private boolean verify(Attributes attributes, String str, byte[] bArr, int i, int i2, boolean z, boolean z2) {
        int i3 = 0;
        while (true) {
            String[] strArr = DIGEST_ALGORITHMS;
            if (i3 >= strArr.length) {
                return z2;
            }
            String str2 = strArr[i3];
            String value = attributes.getValue(str2 + str);
            if (value != null) {
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance(str2);
                    if (z) {
                        int i4 = i2 - 1;
                        if (bArr[i4] == 10 && bArr[i2 - 2] == 10) {
                            messageDigest.update(bArr, i, i4 - i);
                            return verifyMessageDigest(messageDigest.digest(), value.getBytes(StandardCharsets.ISO_8859_1));
                        }
                    }
                    messageDigest.update(bArr, i, i2 - i);
                    return verifyMessageDigest(messageDigest.digest(), value.getBytes(StandardCharsets.ISO_8859_1));
                } catch (NoSuchAlgorithmException unused) {
                    continue;
                }
            }
            i3++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean verifyMessageDigest(byte[] bArr, byte[] bArr2) {
        try {
            return MessageDigest.isEqual(bArr, Base64.getDecoder().decode(bArr2));
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    Certificate[][] getCertificateChains(String str) {
        return this.verifiedEntries.get(str);
    }

    void removeMetaEntries() {
        this.metaEntries.clear();
    }
}
