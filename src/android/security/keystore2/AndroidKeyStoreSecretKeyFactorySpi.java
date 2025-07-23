package android.security.keystore2;

import android.security.GateKeeper;
import android.security.keymaster.KeymasterArguments;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import android.security.keystore.KeyProperties;
import android.system.keystore2.Authorization;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.ProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactorySpi;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes3.dex */
public class AndroidKeyStoreSecretKeyFactorySpi extends SecretKeyFactorySpi {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.crypto.SecretKeyFactorySpi
    protected KeySpec engineGetKeySpec(SecretKey secretKey, Class cls) throws InvalidKeySpecException {
        if (cls == null) {
            throw new InvalidKeySpecException("keySpecClass == null");
        }
        if (!(secretKey instanceof AndroidKeyStoreSecretKey)) {
            StringBuilder sb = new StringBuilder("Only Android KeyStore secret keys supported: ");
            sb.append(secretKey != 0 ? secretKey.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING);
            throw new InvalidKeySpecException(sb.toString());
        }
        if (SecretKeySpec.class.isAssignableFrom(cls)) {
            throw new InvalidKeySpecException("Key material export of Android KeyStore keys is not supported");
        }
        if (!KeyInfo.class.equals(cls)) {
            throw new InvalidKeySpecException("Unsupported key spec: " + cls.getName());
        }
        return getKeyInfo((AndroidKeyStoreKey) secretKey);
    }

    static KeyInfo getKeyInfo(AndroidKeyStoreKey androidKeyStoreKey) {
        long j;
        Date date;
        Date date2;
        Date date3;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        boolean z;
        int i6;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        int i7;
        int i8;
        boolean z7;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        try {
            j = 0;
            date = null;
            date2 = null;
            date3 = null;
            i2 = 0;
            i3 = -1;
            i4 = -1;
            i5 = 0;
            z = false;
            i6 = 0;
            z2 = true;
            z3 = false;
            z4 = false;
            z5 = false;
            z6 = false;
            i7 = 0;
            i8 = -1;
        } catch (IllegalArgumentException e) {
            throw new ProviderException("Unsupported key characteristic", e);
        }
        for (Authorization authorization : androidKeyStoreKey.getAuthorizations()) {
            switch (authorization.keyParameter.tag) {
                case -1610612234:
                    arrayList3.add(KeymasterArguments.toUint64(authorization.keyParameter.value.getLongInteger()));
                    continue;
                case 268435960:
                    int hardwareAuthenticatorType = authorization.keyParameter.value.getHardwareAuthenticatorType();
                    if (KeyStore2ParameterUtils.isSecureHardware(authorization.securityLevel)) {
                        i5 = hardwareAuthenticatorType;
                        continue;
                    } else {
                        i2 = hardwareAuthenticatorType;
                    }
                case 268436158:
                    z = KeyStore2ParameterUtils.isSecureHardware(authorization.securityLevel);
                    int i9 = authorization.securityLevel;
                    i4 = KeyProperties.Origin.fromKeymaster(authorization.keyParameter.value.getOrigin());
                    i7 = i9;
                    continue;
                case 536870913:
                    i6 |= KeyProperties.Purpose.fromKeymaster(authorization.keyParameter.value.getKeyPurpose());
                    continue;
                case 536870916:
                    arrayList2.add(KeyProperties.BlockMode.fromKeymaster(authorization.keyParameter.value.getBlockMode()));
                    continue;
                case 536870917:
                    arrayList.add(KeyProperties.Digest.fromKeymaster(authorization.keyParameter.value.getDigest()));
                    continue;
                case 536870918:
                    int paddingMode = authorization.keyParameter.value.getPaddingMode();
                    if (paddingMode == 5 || paddingMode == 3) {
                        arrayList5.add(KeyProperties.SignaturePadding.fromKeymaster(paddingMode));
                    } else {
                        try {
                            arrayList4.add(KeyProperties.EncryptionPadding.fromKeymaster(paddingMode));
                        } catch (IllegalArgumentException unused) {
                            throw new ProviderException("Unsupported padding: " + paddingMode);
                        }
                    }
                    break;
                case 805306371:
                    long unsignedInt = KeyStore2ParameterUtils.getUnsignedInt(authorization);
                    if (unsignedInt > 2147483647L) {
                        throw new ProviderException("Key too large: " + unsignedInt + " bits");
                    }
                    i3 = (int) unsignedInt;
                    continue;
                case 805306773:
                    long unsignedInt2 = KeyStore2ParameterUtils.getUnsignedInt(authorization);
                    if (unsignedInt2 > 2147483647L) {
                        throw new ProviderException("Usage count of limited use key too long: " + unsignedInt2);
                    }
                    i8 = (int) unsignedInt2;
                    continue;
                case 805306873:
                    long unsignedInt3 = KeyStore2ParameterUtils.getUnsignedInt(authorization);
                    if (unsignedInt3 > 2147483647L) {
                        throw new ProviderException("User authentication timeout validity too long: " + unsignedInt3 + " seconds");
                    }
                    j = unsignedInt3;
                    continue;
                case 1610613136:
                    date = KeyStore2ParameterUtils.getDate(authorization);
                    continue;
                case 1610613137:
                    date2 = KeyStore2ParameterUtils.getDate(authorization);
                    continue;
                case 1610613138:
                    date3 = KeyStore2ParameterUtils.getDate(authorization);
                    continue;
                case 1879048695:
                    z2 = false;
                    continue;
                case 1879048698:
                    z3 = KeyStore2ParameterUtils.isSecureHardware(authorization.securityLevel);
                    continue;
                case 1879048699:
                    z5 = KeyStore2ParameterUtils.isSecureHardware(authorization.securityLevel);
                    continue;
                case 1879048700:
                    z6 = KeyStore2ParameterUtils.isSecureHardware(authorization.securityLevel);
                    continue;
                case 1879048701:
                    z4 = true;
                    continue;
                default:
                    continue;
            }
            throw new ProviderException("Unsupported key characteristic", e);
        }
        if (i3 == -1) {
            throw new ProviderException("Key size not available");
        }
        if (i4 == -1) {
            throw new ProviderException("Key origin not available");
        }
        String[] strArr = (String[]) arrayList4.toArray(new String[0]);
        String[] strArr2 = (String[]) arrayList5.toArray(new String[0]);
        boolean z8 = z2 && i5 != 0 && i2 == 0;
        String[] strArr3 = (String[]) arrayList.toArray(new String[0]);
        String[] strArr4 = (String[]) arrayList2.toArray(new String[0]);
        if (i2 == 2 || i5 == 2) {
            z7 = (arrayList3.isEmpty() || arrayList3.contains(getGateKeeperSecureUserId())) ? false : true;
        } else {
            z7 = false;
        }
        return new KeyInfo(androidKeyStoreKey.getUserKeyDescriptor().alias, z, i4, i3, date, date2, date3, i6, strArr, strArr2, strArr3, strArr4, z2, (int) j, z8 ? i5 : i2, z8, z3, z4, z5, z7, z6, i7, i8);
    }

    private static BigInteger getGateKeeperSecureUserId() throws ProviderException {
        try {
            return BigInteger.valueOf(GateKeeper.getSecureUserId());
        } catch (IllegalStateException e) {
            throw new ProviderException("Failed to get GateKeeper secure user ID", e);
        }
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected SecretKey engineGenerateSecret(KeySpec keySpec) throws InvalidKeySpecException {
        throw new InvalidKeySpecException("To generate secret key in Android Keystore, use KeyGenerator initialized with " + KeyGenParameterSpec.class.getName());
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected SecretKey engineTranslateKey(SecretKey secretKey) throws InvalidKeyException {
        if (secretKey == null) {
            throw new InvalidKeyException("key == null");
        }
        if (secretKey instanceof AndroidKeyStoreSecretKey) {
            return secretKey;
        }
        throw new InvalidKeyException("To import a secret key into Android Keystore, use KeyStore.setEntry");
    }
}
