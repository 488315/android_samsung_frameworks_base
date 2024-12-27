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
import java.util.List;
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

        VerifierEntry(String name, MessageDigest digest, byte[] hash, Certificate[][] certChains, Hashtable<String, Certificate[][]> verifedEntries) {
            this.name = name;
            this.digest = digest;
            this.hash = hash;
            this.certChains = certChains;
            this.verifiedEntries = verifedEntries;
        }

        @Override // java.io.OutputStream
        public void write(int value) {
            this.digest.update((byte) value);
        }

        @Override // java.io.OutputStream
        public void write(byte[] buf, int off, int nbytes) {
            this.digest.update(buf, off, nbytes);
        }

        void verify() {
            byte[] d = this.digest.digest();
            if (!StrictJarVerifier.verifyMessageDigest(d, this.hash)) {
                throw StrictJarVerifier.invalidDigest("META-INF/MANIFEST.MF", this.name, this.name);
            }
            this.verifiedEntries.put(this.name, this.certChains);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static SecurityException invalidDigest(String signatureFile, String name, String jarName) {
        throw new SecurityException(signatureFile + " has invalid digest for " + name + " in " + jarName);
    }

    private static SecurityException failedVerification(String jarName, String signatureFile) {
        throw new SecurityException(jarName + " failed verification of " + signatureFile);
    }

    private static SecurityException failedVerification(String jarName, String signatureFile, Throwable e) {
        throw new SecurityException(jarName + " failed verification of " + signatureFile, e);
    }

    StrictJarVerifier(String name, StrictJarManifest manifest, HashMap<String, byte[]> metaEntries, boolean signatureSchemeRollbackProtectionsEnforced) {
        this.jarName = name;
        this.manifest = manifest;
        this.metaEntries = metaEntries;
        this.mainAttributesEnd = manifest.getMainAttributesEnd();
        this.signatureSchemeRollbackProtectionsEnforced = signatureSchemeRollbackProtectionsEnforced;
    }

    VerifierEntry initEntry(String name) {
        Attributes attributes;
        if (this.manifest == null || this.signatures.isEmpty() || (attributes = this.manifest.getAttributes(name)) == null) {
            return null;
        }
        ArrayList<Certificate[]> certChains = new ArrayList<>();
        for (Map.Entry<String, HashMap<String, Attributes>> entry : this.signatures.entrySet()) {
            HashMap<String, Attributes> hm = entry.getValue();
            if (hm.get(name) != null) {
                String signatureFile = entry.getKey();
                Certificate[] certChain = this.certificates.get(signatureFile);
                if (certChain != null) {
                    certChains.add(certChain);
                }
            }
        }
        if (certChains.isEmpty()) {
            return null;
        }
        Certificate[][] certChainsArray = (Certificate[][]) certChains.toArray(new Certificate[certChains.size()][]);
        for (int i = 0; i < DIGEST_ALGORITHMS.length; i++) {
            String algorithm = DIGEST_ALGORITHMS[i];
            String hash = attributes.getValue(algorithm + "-Digest");
            if (hash != null) {
                byte[] hashBytes = hash.getBytes(StandardCharsets.ISO_8859_1);
                try {
                    try {
                        return new VerifierEntry(name, MessageDigest.getInstance(algorithm), hashBytes, certChainsArray, this.verifiedEntries);
                    } catch (NoSuchAlgorithmException e) {
                    }
                } catch (NoSuchAlgorithmException e2) {
                }
            }
        }
        return null;
    }

    void addMetaEntry(String name, byte[] buf) {
        this.metaEntries.put(name.toUpperCase(Locale.US), buf);
    }

    synchronized boolean readCertificates() {
        if (this.metaEntries.isEmpty()) {
            return false;
        }
        int signerCount = 0;
        Iterator<String> it = this.metaEntries.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (key.endsWith(".DSA") || key.endsWith(".RSA") || key.endsWith(".EC")) {
                signerCount++;
                if (signerCount > 10) {
                    throw new SecurityException("APK Signature Scheme v1 only supports a maximum of 10 signers");
                }
                verifyCertificate(key);
                it.remove();
            }
        }
        return true;
    }

    static Certificate[] verifyBytes(byte[] blockBytes, byte[] sfBytes) throws GeneralSecurityException {
        Object obj = null;
        try {
            try {
                obj = Providers.startJarVerification();
                PKCS7 block = new PKCS7(blockBytes);
                SignerInfo[] verifiedSignerInfos = block.verify(sfBytes);
                if (verifiedSignerInfos == null || verifiedSignerInfos.length == 0) {
                    throw new GeneralSecurityException("Failed to verify signature: no verified SignerInfos");
                }
                SignerInfo verifiedSignerInfo = verifiedSignerInfos[0];
                List<X509Certificate> verifiedSignerCertChain = verifiedSignerInfo.getCertificateChain(block);
                if (verifiedSignerCertChain == null) {
                    throw new GeneralSecurityException("Failed to find verified SignerInfo certificate chain");
                }
                if (verifiedSignerCertChain.isEmpty()) {
                    throw new GeneralSecurityException("Verified SignerInfo certificate chain is emtpy");
                }
                return (Certificate[]) verifiedSignerCertChain.toArray(new X509Certificate[verifiedSignerCertChain.size()]);
            } catch (IOException e) {
                throw new GeneralSecurityException("IO exception verifying jar cert", e);
            }
        } finally {
            Providers.stopJarVerification(obj);
        }
    }

    private void verifyCertificate(String certFile) {
        byte[] manifestBytes;
        HashMap<String, Attributes> entries;
        String apkSignatureSchemeIdList;
        String signatureFile = certFile.substring(0, certFile.lastIndexOf(46)) + ".SF";
        byte[] sfBytes = this.metaEntries.get(signatureFile);
        if (sfBytes == null || (manifestBytes = this.metaEntries.get("META-INF/MANIFEST.MF")) == null) {
            return;
        }
        byte[] sBlockBytes = this.metaEntries.get(certFile);
        try {
            Certificate[] signerCertChain = verifyBytes(sBlockBytes, sfBytes);
            if (signerCertChain != null) {
                try {
                    this.certificates.put(signatureFile, signerCertChain);
                } catch (GeneralSecurityException e) {
                    e = e;
                    throw failedVerification(this.jarName, signatureFile, e);
                }
            }
            Attributes attributes = new Attributes();
            HashMap<String, Attributes> entries2 = new HashMap<>();
            try {
                StrictJarManifestReader im = new StrictJarManifestReader(sfBytes, attributes);
                im.readEntries(entries2, null);
                if (this.signatureSchemeRollbackProtectionsEnforced && (apkSignatureSchemeIdList = attributes.getValue(SF_ATTRIBUTE_ANDROID_APK_SIGNED_NAME)) != null) {
                    boolean v2SignatureGenerated = false;
                    boolean v3SignatureGenerated = false;
                    StringTokenizer tokenizer = new StringTokenizer(apkSignatureSchemeIdList, ",");
                    while (true) {
                        if (!tokenizer.hasMoreTokens()) {
                            break;
                        }
                        String idText = tokenizer.nextToken().trim();
                        if (!idText.isEmpty()) {
                            try {
                                int id = Integer.parseInt(idText);
                                if (id == 2) {
                                    v2SignatureGenerated = true;
                                    break;
                                } else if (id == 3) {
                                    v3SignatureGenerated = true;
                                    break;
                                }
                            } catch (Exception e2) {
                            }
                        }
                    }
                    if (v2SignatureGenerated) {
                        throw new SecurityException(signatureFile + " indicates " + this.jarName + " is signed using APK Signature Scheme v2, but no such signature was found. Signature stripped?");
                    }
                    if (v3SignatureGenerated) {
                        throw new SecurityException(signatureFile + " indicates " + this.jarName + " is signed using APK Signature Scheme v3, but no such signature was found. Signature stripped?");
                    }
                }
                if (attributes.get(Attributes.Name.SIGNATURE_VERSION) == null) {
                    return;
                }
                boolean createdBySigntool = false;
                String createdBy = attributes.getValue("Created-By");
                if (createdBy != null) {
                    createdBySigntool = createdBy.indexOf("signtool") != -1;
                }
                if (this.mainAttributesEnd <= 0 || createdBySigntool) {
                    entries = entries2;
                } else {
                    entries = entries2;
                    if (!verify(attributes, "-Digest-Manifest-Main-Attributes", manifestBytes, 0, this.mainAttributesEnd, false, true)) {
                        throw failedVerification(this.jarName, signatureFile);
                    }
                }
                String digestAttribute = createdBySigntool ? "-Digest" : "-Digest-Manifest";
                if (!verify(attributes, digestAttribute, manifestBytes, 0, manifestBytes.length, false, false)) {
                    for (Map.Entry<String, Attributes> entry : entries.entrySet()) {
                        StrictJarManifest.Chunk chunk = this.manifest.getChunk(entry.getKey());
                        if (chunk == null) {
                            return;
                        }
                        Attributes attributes2 = attributes;
                        byte[] sBlockBytes2 = sBlockBytes;
                        byte[] manifestBytes2 = manifestBytes;
                        if (!verify(entry.getValue(), "-Digest", manifestBytes, chunk.start, chunk.end, createdBySigntool, false)) {
                            throw invalidDigest(signatureFile, entry.getKey(), this.jarName);
                        }
                        attributes = attributes2;
                        sBlockBytes = sBlockBytes2;
                        manifestBytes = manifestBytes2;
                    }
                }
                this.metaEntries.put(signatureFile, null);
                this.signatures.put(signatureFile, entries);
            } catch (IOException e3) {
            }
        } catch (GeneralSecurityException e4) {
            e = e4;
        }
    }

    boolean isSignedJar() {
        return this.certificates.size() > 0;
    }

    private boolean verify(Attributes attributes, String entry, byte[] data, int start, int end, boolean ignoreSecondEndline, boolean ignorable) {
        for (int i = 0; i < DIGEST_ALGORITHMS.length; i++) {
            String algorithm = DIGEST_ALGORITHMS[i];
            String hash = attributes.getValue(algorithm + entry);
            if (hash != null) {
                try {
                    MessageDigest md = MessageDigest.getInstance(algorithm);
                    if (ignoreSecondEndline && data[end - 1] == 10 && data[end - 2] == 10) {
                        md.update(data, start, (end - 1) - start);
                    } else {
                        md.update(data, start, end - start);
                    }
                    byte[] b = md.digest();
                    byte[] encodedHashBytes = hash.getBytes(StandardCharsets.ISO_8859_1);
                    return verifyMessageDigest(b, encodedHashBytes);
                } catch (NoSuchAlgorithmException e) {
                }
            }
        }
        return ignorable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean verifyMessageDigest(byte[] expected, byte[] encodedActual) {
        try {
            byte[] actual = Base64.getDecoder().decode(encodedActual);
            return MessageDigest.isEqual(expected, actual);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    Certificate[][] getCertificateChains(String name) {
        return this.verifiedEntries.get(name);
    }

    void removeMetaEntries() {
        this.metaEntries.clear();
    }
}
