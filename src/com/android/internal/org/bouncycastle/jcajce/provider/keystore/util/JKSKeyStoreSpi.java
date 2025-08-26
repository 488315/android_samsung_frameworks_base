package com.android.internal.org.bouncycastle.jcajce.provider.keystore.util;

import com.android.internal.org.bouncycastle.crypto.Digest;
import com.android.internal.org.bouncycastle.jcajce.BCLoadStoreParameter;
import com.android.internal.org.bouncycastle.jcajce.provider.util.DigestFactory;
import com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Strings;
import com.android.internal.org.bouncycastle.util.io.Streams;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

/* loaded from: classes5.dex */
public class JKSKeyStoreSpi extends KeyStoreSpi {
    private static final String NOT_IMPLEMENTED_MESSAGE = "BC JKS store is read-only and only supports certificate entries";
    private final Hashtable<String, BCJKSTrustedCertEntry> certificateEntries = new Hashtable<>();
    private final JcaJceHelper helper;

    @Override // java.security.KeyStoreSpi
    public Certificate[] engineGetCertificateChain(String str) {
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public Key engineGetKey(String str, char[] cArr) throws NoSuchAlgorithmException, UnrecoverableKeyException {
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsKeyEntry(String str) {
        return false;
    }

    public JKSKeyStoreSpi(JcaJceHelper jcaJceHelper) {
        this.helper = jcaJceHelper;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineProbe(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream;
        if (inputStream instanceof DataInputStream) {
            dataInputStream = (DataInputStream) inputStream;
        } else {
            dataInputStream = new DataInputStream(inputStream);
        }
        int i = dataInputStream.readInt();
        int i2 = dataInputStream.readInt();
        if (i == -17957139) {
            return i2 == 1 || i2 == 2;
        }
        return false;
    }

    @Override // java.security.KeyStoreSpi
    public Certificate engineGetCertificate(String str) {
        synchronized (this.certificateEntries) {
            BCJKSTrustedCertEntry bCJKSTrustedCertEntry = this.certificateEntries.get(str);
            if (bCJKSTrustedCertEntry == null) {
                return null;
            }
            return bCJKSTrustedCertEntry.cert;
        }
    }

    @Override // java.security.KeyStoreSpi
    public Date engineGetCreationDate(String str) {
        synchronized (this.certificateEntries) {
            BCJKSTrustedCertEntry bCJKSTrustedCertEntry = this.certificateEntries.get(str);
            if (bCJKSTrustedCertEntry == null) {
                return null;
            }
            return bCJKSTrustedCertEntry.date;
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) throws KeyStoreException {
        throw new KeyStoreException(NOT_IMPLEMENTED_MESSAGE);
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) throws KeyStoreException {
        throw new KeyStoreException(NOT_IMPLEMENTED_MESSAGE);
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetCertificateEntry(String str, Certificate certificate) throws KeyStoreException {
        throw new KeyStoreException(NOT_IMPLEMENTED_MESSAGE);
    }

    @Override // java.security.KeyStoreSpi
    public void engineDeleteEntry(String str) throws KeyStoreException {
        throw new KeyStoreException(NOT_IMPLEMENTED_MESSAGE);
    }

    @Override // java.security.KeyStoreSpi
    public Enumeration<String> engineAliases() {
        Enumeration<String> enumerationKeys;
        synchronized (this.certificateEntries) {
            enumerationKeys = this.certificateEntries.keys();
        }
        return enumerationKeys;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineContainsAlias(String str) {
        boolean zContainsKey;
        if (str == null) {
            throw new NullPointerException("alias value is null");
        }
        synchronized (this.certificateEntries) {
            zContainsKey = this.certificateEntries.containsKey(str);
        }
        return zContainsKey;
    }

    @Override // java.security.KeyStoreSpi
    public int engineSize() {
        return this.certificateEntries.size();
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsCertificateEntry(String str) {
        boolean zContainsKey;
        synchronized (this.certificateEntries) {
            zContainsKey = this.certificateEntries.containsKey(str);
        }
        return zContainsKey;
    }

    @Override // java.security.KeyStoreSpi
    public String engineGetCertificateAlias(Certificate certificate) {
        synchronized (this.certificateEntries) {
            for (Map.Entry<String, BCJKSTrustedCertEntry> entry : this.certificateEntries.entrySet()) {
                if (entry.getValue().cert.equals(certificate)) {
                    return entry.getKey();
                }
            }
            return null;
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(OutputStream outputStream, char[] cArr) throws NoSuchAlgorithmException, IOException, CertificateException {
        throw new IOException(NOT_IMPLEMENTED_MESSAGE);
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(KeyStore.LoadStoreParameter loadStoreParameter) throws NoSuchAlgorithmException, IOException, CertificateException {
        if (loadStoreParameter == null) {
            engineLoad(null, null);
        } else if (loadStoreParameter instanceof BCLoadStoreParameter) {
            engineLoad(((BCLoadStoreParameter) loadStoreParameter).getInputStream(), ParameterUtil.extractPassword(loadStoreParameter));
        } else {
            throw new IllegalArgumentException("no support for 'param' of type " + loadStoreParameter.getClass().getName());
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(InputStream inputStream, char[] cArr) throws NoSuchAlgorithmException, IOException, CertificateException {
        Hashtable hashtable;
        if (inputStream == null) {
            return;
        }
        ErasableByteStream erasableByteStreamValidateStream = validateStream(inputStream, cArr);
        synchronized (this.certificateEntries) {
            try {
                DataInputStream dataInputStream = new DataInputStream(erasableByteStreamValidateStream);
                int i = dataInputStream.readInt();
                int i2 = dataInputStream.readInt();
                if (i == -17957139) {
                    CertificateFactory certificateFactoryCreateCertFactory = null;
                    int i3 = 2;
                    if (i2 == 1) {
                        hashtable = null;
                        certificateFactoryCreateCertFactory = createCertFactory("X.509");
                    } else if (i2 == 2) {
                        hashtable = new Hashtable();
                    } else {
                        throw new IllegalStateException("unable to discern store version");
                    }
                    int i4 = dataInputStream.readInt();
                    int i5 = 0;
                    while (i5 < i4) {
                        int i6 = dataInputStream.readInt();
                        if (i6 == 1) {
                            throw new IOException(NOT_IMPLEMENTED_MESSAGE);
                        }
                        if (i6 == i3) {
                            String utf = dataInputStream.readUTF();
                            Date date = new Date(dataInputStream.readLong());
                            if (i2 == i3) {
                                String utf2 = dataInputStream.readUTF();
                                if (hashtable.containsKey(utf2)) {
                                    certificateFactoryCreateCertFactory = (CertificateFactory) hashtable.get(utf2);
                                } else {
                                    CertificateFactory certificateFactoryCreateCertFactory2 = createCertFactory(utf2);
                                    hashtable.put(utf2, certificateFactoryCreateCertFactory2);
                                    certificateFactoryCreateCertFactory = certificateFactoryCreateCertFactory2;
                                }
                            }
                            int i7 = dataInputStream.readInt();
                            byte[] bArr = new byte[i7];
                            dataInputStream.readFully(bArr);
                            ErasableByteStream erasableByteStream = new ErasableByteStream(bArr, 0, i7);
                            try {
                                Certificate certificateGenerateCertificate = certificateFactoryCreateCertFactory.generateCertificate(erasableByteStream);
                                if (erasableByteStream.available() != 0) {
                                    throw new IOException("password incorrect or store tampered with");
                                }
                                erasableByteStream.erase();
                                this.certificateEntries.put(utf, new BCJKSTrustedCertEntry(date, certificateGenerateCertificate));
                                i5++;
                                i3 = 2;
                            } catch (Throwable th) {
                                erasableByteStream.erase();
                                throw th;
                            }
                        } else {
                            throw new IllegalStateException("unable to discern entry type");
                        }
                    }
                }
                if (erasableByteStreamValidateStream.available() != 0) {
                    throw new IOException("password incorrect or store tampered with");
                }
            } finally {
                erasableByteStreamValidateStream.erase();
            }
        }
    }

    private CertificateFactory createCertFactory(String str) throws CertificateException {
        JcaJceHelper jcaJceHelper = this.helper;
        if (jcaJceHelper != null) {
            try {
                return jcaJceHelper.createCertificateFactory(str);
            } catch (NoSuchProviderException e) {
                throw new CertificateException(e.toString());
            }
        }
        return CertificateFactory.getInstance(str);
    }

    private void addPassword(Digest digest, char[] cArr) throws IOException {
        for (int i = 0; i < cArr.length; i++) {
            digest.update((byte) (cArr[i] >> '\b'));
            digest.update((byte) cArr[i]);
        }
        digest.update(Strings.toByteArray("Mighty Aphrodite"), 0, 16);
    }

    private ErasableByteStream validateStream(InputStream inputStream, char[] cArr) throws IOException {
        Digest digest = DigestFactory.getDigest("SHA-1");
        byte[] all = Streams.readAll(inputStream);
        if (cArr != null) {
            addPassword(digest, cArr);
            digest.update(all, 0, all.length - digest.getDigestSize());
            int digestSize = digest.getDigestSize();
            byte[] bArr = new byte[digestSize];
            digest.doFinal(bArr, 0);
            byte[] bArr2 = new byte[digestSize];
            System.arraycopy(all, all.length - digestSize, bArr2, 0, digestSize);
            if (!Arrays.constantTimeAreEqual(bArr, bArr2)) {
                Arrays.fill(all, (byte) 0);
                throw new IOException("password incorrect or store tampered with");
            }
            return new ErasableByteStream(all, 0, all.length - digestSize);
        }
        return new ErasableByteStream(all, 0, all.length - digest.getDigestSize());
    }

    private static final class BCJKSTrustedCertEntry {
        final Certificate cert;
        final Date date;

        public BCJKSTrustedCertEntry(Date date, Certificate certificate) {
            this.date = date;
            this.cert = certificate;
        }
    }

    private static final class ErasableByteStream extends ByteArrayInputStream {
        public ErasableByteStream(byte[] bArr, int i, int i2) {
            super(bArr, i, i2);
        }

        public void erase() {
            Arrays.fill(this.buf, (byte) 0);
        }
    }
}
