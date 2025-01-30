package com.samsung.ucm.keystore;

import android.p009os.RemoteException;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;

/* loaded from: classes6.dex */
public class UcmKeyStoreSignatureSpi extends SignatureSpi {
    private static final String TAG = "UcmKeyStoreSignatureSpi";
    private final String mAlgorithm;
    private boolean mIsSigning = false;
    private ByteArrayOutputStream mStream = new ByteArrayOutputStream();
    private UcmKeyStorePrivateKey mKey = null;

    UcmKeyStoreSignatureSpi(String algorithm) {
        this.mAlgorithm = algorithm;
    }

    private void engineUpdateInternal(byte[] input, int offset, int len) throws SignatureException {
        this.mStream.write(input, offset, len);
    }

    @Override // java.security.SignatureSpi
    public void engineUpdate(byte[] input, int offset, int len) throws SignatureException {
        if (!this.mIsSigning) {
            throw new ProviderException("Update operation doesn't support verify");
        }
        if (input == null) {
            throw new ProviderException("wrong input. input is null");
        }
        if (input.length < offset + len) {
            throw new ProviderException("wrong input. input data size is wrong");
        }
        engineUpdateInternal(input, offset, len);
    }

    @Override // java.security.SignatureSpi
    public void engineUpdate(byte input) throws SignatureException {
        engineUpdateInternal(new byte[]{input}, 0, 1);
    }

    @Override // java.security.SignatureSpi
    public void engineUpdate(ByteBuffer input) {
        byte[] b;
        int off;
        int len = input.remaining();
        if (input.hasArray()) {
            b = input.array();
            off = input.arrayOffset() + input.position();
            input.position(input.limit());
        } else {
            b = new byte[len];
            off = 0;
            input.get(b);
        }
        try {
            engineUpdateInternal(b, off, len);
        } catch (SignatureException e) {
            throw new ProviderException("update() failed", e);
        }
    }

    @Override // java.security.SignatureSpi
    @Deprecated
    protected Object engineGetParameter(String param) throws InvalidParameterException {
        return null;
    }

    private void initInternal(PrivateKey privateKey, boolean signing) throws InvalidKeyException {
        if (!(privateKey instanceof UcmKeyStorePrivateKey)) {
            throw new InvalidKeyException("not supported key type");
        }
        this.mIsSigning = signing;
        this.mKey = (UcmKeyStorePrivateKey) privateKey;
    }

    private boolean isInitialized() {
        return this.mIsSigning && this.mKey != null && this.mStream.size() > 0;
    }

    private void resetContext() {
        this.mIsSigning = false;
        this.mKey = null;
        this.mStream.reset();
    }

    @Override // java.security.SignatureSpi
    public void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        initInternal(privateKey, true);
    }

    @Override // java.security.SignatureSpi
    public void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        initInternal(null, false);
        throw new InvalidKeyException("verify is not supported");
    }

    @Override // java.security.SignatureSpi
    @Deprecated
    protected void engineSetParameter(String param, Object value) throws InvalidParameterException {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override // java.security.SignatureSpi
    public byte[] engineSign() throws SignatureException {
        if (!isInitialized()) {
            throw new SignatureException("engineSign. not initialized");
        }
        IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
        if (lService == null) {
            throw new SignatureException("failed to connect ucm service");
        }
        try {
            byte[] output = lService.ucmSign(this.mKey.getAlias(), this.mStream.toByteArray(), this.mAlgorithm);
            if (output == null) {
                throw new SignatureException("output is null");
            }
            resetContext();
            return output;
        } catch (RemoteException re) {
            Log.m96e(TAG, "Remote Exception " + re);
            throw new SignatureException("RemoteException");
        }
    }

    @Override // java.security.SignatureSpi
    public boolean engineVerify(byte[] sigBytes) throws SignatureException {
        throw new SignatureException("engineVerify unsupported");
    }

    public static final class MD5WithPKCS1Padding extends UcmKeyStoreSignatureSpi {
        public MD5WithPKCS1Padding() {
            super("MD5withRSA");
        }
    }

    public static final class SHA1WithPKCS1Padding extends UcmKeyStoreSignatureSpi {
        public SHA1WithPKCS1Padding() {
            super("SHA1withRSA");
        }
    }

    public static final class SHA224WithPKCS1Padding extends UcmKeyStoreSignatureSpi {
        public SHA224WithPKCS1Padding() {
            super("SHA224withRSA");
        }
    }

    public static final class SHA256WithPKCS1Padding extends UcmKeyStoreSignatureSpi {
        public SHA256WithPKCS1Padding() {
            super("SHA256withRSA");
        }
    }

    public static final class SHA384WithPKCS1Padding extends UcmKeyStoreSignatureSpi {
        public SHA384WithPKCS1Padding() {
            super("SHA384withRSA");
        }
    }

    public static final class SHA512WithPKCS1Padding extends UcmKeyStoreSignatureSpi {
        public SHA512WithPKCS1Padding() {
            super("SHA512withRSA");
        }
    }

    public static final class SHA1WithPSSPadding extends UcmKeyStoreSignatureSpi {
        public SHA1WithPSSPadding() {
            super("SHA1withRSA/PSS");
        }
    }

    public static final class SHA224WithPSSPadding extends UcmKeyStoreSignatureSpi {
        public SHA224WithPSSPadding() {
            super("SHA224withRSA/PSS");
        }
    }

    public static final class SHA256WithPSSPadding extends UcmKeyStoreSignatureSpi {
        public SHA256WithPSSPadding() {
            super("SHA256withRSA/PSS");
        }
    }

    public static final class SHA384WithPSSPadding extends UcmKeyStoreSignatureSpi {
        public SHA384WithPSSPadding() {
            super("SHA384withRSA/PSS");
        }
    }

    public static final class SHA512WithPSSPadding extends UcmKeyStoreSignatureSpi {
        public SHA512WithPSSPadding() {
            super("SHA512withRSA/PSS");
        }
    }

    public static final class NONEwithECDSA extends UcmKeyStoreSignatureSpi {
        public NONEwithECDSA() {
            super("NONEwithECDSA");
        }
    }

    public static final class SHA1withECDSA extends UcmKeyStoreSignatureSpi {
        public SHA1withECDSA() {
            super("SHA1withECDSA");
        }
    }

    public static final class SHA224withECDSA extends UcmKeyStoreSignatureSpi {
        public SHA224withECDSA() {
            super("SHA224withECDSA");
        }
    }

    public static final class SHA256withECDSA extends UcmKeyStoreSignatureSpi {
        public SHA256withECDSA() {
            super("SHA256withECDSA");
        }
    }

    public static final class SHA384withECDSA extends UcmKeyStoreSignatureSpi {
        public SHA384withECDSA() {
            super("SHA384withECDSA");
        }
    }

    public static final class SHA512withECDSA extends UcmKeyStoreSignatureSpi {
        public SHA512withECDSA() {
            super("SHA512withECDSA");
        }
    }

    public static final class NONEwithECGDSA extends UcmKeyStoreSignatureSpi {
        public NONEwithECGDSA() {
            super("NONEwithECGDSA");
        }
    }

    public static final class SHA1withECGDSA extends UcmKeyStoreSignatureSpi {
        public SHA1withECGDSA() {
            super("SHA1withECGDSA");
        }
    }

    public static final class SHA224withECGDSA extends UcmKeyStoreSignatureSpi {
        public SHA224withECGDSA() {
            super("SHA224withECGDSA");
        }
    }

    public static final class SHA256withECGDSA extends UcmKeyStoreSignatureSpi {
        public SHA256withECGDSA() {
            super("SHA256withECGDSA");
        }
    }

    public static final class SHA384withECGDSA extends UcmKeyStoreSignatureSpi {
        public SHA384withECGDSA() {
            super("SHA384withECGDSA");
        }
    }

    public static final class SHA512withECGDSA extends UcmKeyStoreSignatureSpi {
        public SHA512withECGDSA() {
            super("SHA512withECGDSA");
        }
    }
}
