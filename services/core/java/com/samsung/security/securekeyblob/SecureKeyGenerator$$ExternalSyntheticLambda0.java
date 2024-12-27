package com.samsung.security.securekeyblob;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.security.keymint.KeyParameter;
import android.hardware.security.keymint.KeyParameterValue;
import android.os.Parcel;
import android.security.keymaster.KeymasterDefs;
import android.security.keystore.ArrayUtils;
import android.security.keystore.KeyProperties;
import android.security.securekeygeneration.ISecureKeyGeneration;
import android.security.securekeygeneration.SecureKeyInfo;
import android.text.TextUtils;

import java.nio.charset.StandardCharsets;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import javax.security.auth.x500.X500Principal;

public final /* synthetic */ class SecureKeyGenerator$$ExternalSyntheticLambda0 {
    public final /* synthetic */ SecureKeyGenerator f$0;
    public final /* synthetic */ SecureKeyGenParameterSpec f$1;

    public /* synthetic */ SecureKeyGenerator$$ExternalSyntheticLambda0(
            SecureKeyGenerator secureKeyGenerator,
            SecureKeyGenParameterSpec secureKeyGenParameterSpec) {
        this.f$0 = secureKeyGenerator;
        this.f$1 = secureKeyGenParameterSpec;
    }

    public final SecureKeyInfo execute(ISecureKeyGeneration iSecureKeyGeneration) {
        int i;
        int[] iArr;
        final int i2 = 2;
        final int i3 = 3;
        final int i4 = 1;
        final int i5 = 0;
        this.f$0.getClass();
        final ArrayList arrayList = new ArrayList();
        SecureKeyGenParameterSpec secureKeyGenParameterSpec = this.f$1;
        int i6 = secureKeyGenParameterSpec.mKeySize;
        int tagType = KeymasterDefs.getTagType(805306371);
        if (tagType != 805306368 && tagType != 1073741824) {
            throw new IllegalArgumentException("Not an int or repeatable int tag: 805306371");
        }
        KeyParameter keyParameter = new KeyParameter();
        keyParameter.tag = 805306371;
        keyParameter.value = KeyParameterValue.integer(i6);
        arrayList.add(keyParameter);
        String str = secureKeyGenParameterSpec.mAlgorithm;
        str.getClass();
        if (str.equals("EC")) {
            arrayList.add(SecureKeyGenerator.makeEnum(268435458, 3));
            if (i6 == 224) {
                i = 0;
            } else if (i6 == 256) {
                i = 1;
            } else if (i6 == 384) {
                i = 2;
            } else {
                if (i6 != 521) {
                    throw new IllegalArgumentException(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    i6, "Unsupported EC curve keysize: "));
                }
                i = 3;
            }
            arrayList.add(SecureKeyGenerator.makeEnum(268435466, i));
        } else if (str.equals("RSA")) {
            arrayList.add(SecureKeyGenerator.makeEnum(268435458, 1));
            long longValue = RSAKeyGenParameterSpec.F4.longValue();
            int tagType2 = KeymasterDefs.getTagType(1342177480);
            if (tagType2 != 1342177280 && tagType2 != -1610612736) {
                throw new IllegalArgumentException("Not a long or repeatable long tag: 1342177480");
            }
            KeyParameter keyParameter2 = new KeyParameter();
            keyParameter2.tag = 1342177480;
            keyParameter2.value = KeyParameterValue.longInteger(longValue);
            arrayList.add(keyParameter2);
        }
        ArrayUtils.forEach(
                KeyProperties.Purpose.allToKeymaster(secureKeyGenParameterSpec.mPurposes),
                new Consumer() { // from class:
                                 // com.samsung.security.securekeyblob.SecureKeyGenerator$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i7 = i5;
                        List list = arrayList;
                        Integer num = (Integer) obj;
                        switch (i7) {
                            case 0:
                                list.add(SecureKeyGenerator.makeEnum(536870913, num.intValue()));
                                break;
                            case 1:
                                list.add(SecureKeyGenerator.makeEnum(536870916, num.intValue()));
                                break;
                            case 2:
                                list.add(SecureKeyGenerator.makeEnum(536870918, num.intValue()));
                                break;
                            default:
                                list.add(SecureKeyGenerator.makeEnum(536870917, num.intValue()));
                                break;
                        }
                    }
                });
        ArrayUtils.forEach(
                KeyProperties.BlockMode.allToKeymaster(
                        ArrayUtils.cloneIfNotEmpty(secureKeyGenParameterSpec.mBlockModes)),
                new Consumer() { // from class:
                                 // com.samsung.security.securekeyblob.SecureKeyGenerator$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i7 = i4;
                        List list = arrayList;
                        Integer num = (Integer) obj;
                        switch (i7) {
                            case 0:
                                list.add(SecureKeyGenerator.makeEnum(536870913, num.intValue()));
                                break;
                            case 1:
                                list.add(SecureKeyGenerator.makeEnum(536870916, num.intValue()));
                                break;
                            case 2:
                                list.add(SecureKeyGenerator.makeEnum(536870918, num.intValue()));
                                break;
                            default:
                                list.add(SecureKeyGenerator.makeEnum(536870917, num.intValue()));
                                break;
                        }
                    }
                });
        if (!(!secureKeyGenParameterSpec.mMgf1Digests.isEmpty())) {
            iArr = new int[] {KeyProperties.Digest.toKeymaster("SHA-1")};
        } else {
            if (secureKeyGenParameterSpec.mMgf1Digests.isEmpty()) {
                throw new IllegalStateException("Mask generation function (MGF) not specified");
            }
            HashSet hashSet = new HashSet(secureKeyGenParameterSpec.mMgf1Digests);
            iArr = new int[hashSet.size()];
            Iterator it = hashSet.iterator();
            int i7 = 0;
            while (it.hasNext()) {
                iArr[i7] = KeyProperties.Digest.toKeymaster((String) it.next());
                i7++;
            }
        }
        ArrayUtils.forEach(
                KeyProperties.EncryptionPadding.allToKeymaster(
                        ArrayUtils.cloneIfNotEmpty(secureKeyGenParameterSpec.mEncryptionPaddings)),
                new SecureKeyGenerator$$ExternalSyntheticLambda3(arrayList, 0, iArr));
        ArrayUtils.forEach(
                KeyProperties.SignaturePadding.allToKeymaster(
                        ArrayUtils.cloneIfNotEmpty(secureKeyGenParameterSpec.mSignaturePaddings)),
                new Consumer() { // from class:
                                 // com.samsung.security.securekeyblob.SecureKeyGenerator$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i72 = i2;
                        List list = arrayList;
                        Integer num = (Integer) obj;
                        switch (i72) {
                            case 0:
                                list.add(SecureKeyGenerator.makeEnum(536870913, num.intValue()));
                                break;
                            case 1:
                                list.add(SecureKeyGenerator.makeEnum(536870916, num.intValue()));
                                break;
                            case 2:
                                list.add(SecureKeyGenerator.makeEnum(536870918, num.intValue()));
                                break;
                            default:
                                list.add(SecureKeyGenerator.makeEnum(536870917, num.intValue()));
                                break;
                        }
                    }
                });
        String[] strArr = secureKeyGenParameterSpec.mDigests;
        if (strArr == null) {
            throw new IllegalStateException("Digests not specified");
        }
        ArrayUtils.forEach(
                KeyProperties.Digest.allToKeymaster(ArrayUtils.cloneIfNotEmpty(strArr)),
                new Consumer() { // from class:
                                 // com.samsung.security.securekeyblob.SecureKeyGenerator$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i72 = i3;
                        List list = arrayList;
                        Integer num = (Integer) obj;
                        switch (i72) {
                            case 0:
                                list.add(SecureKeyGenerator.makeEnum(536870913, num.intValue()));
                                break;
                            case 1:
                                list.add(SecureKeyGenerator.makeEnum(536870916, num.intValue()));
                                break;
                            case 2:
                                list.add(SecureKeyGenerator.makeEnum(536870918, num.intValue()));
                                break;
                            default:
                                list.add(SecureKeyGenerator.makeEnum(536870917, num.intValue()));
                                break;
                        }
                    }
                });
        arrayList.add(
                SecureKeyGenerator.makeBytes(
                        -1879045791, secureKeyGenParameterSpec.mServiceTAName));
        byte[] bArr = secureKeyGenParameterSpec.mDNQualifier;
        if (bArr != null) {
            arrayList.add(SecureKeyGenerator.makeBytes(-1879045789, bArr));
        }
        KeyParameter[] keyParameterArr =
                (KeyParameter[]) arrayList.toArray(new KeyParameter[arrayList.size()]);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(
                SecureKeyGenerator.makeBytes(-1879047484, secureKeyGenParameterSpec.mChallenge));
        if (secureKeyGenParameterSpec.mDeviceAttestation) {
            arrayList2.add(
                    SecureKeyGenerator.makeBytes(
                            -1879046090, "samsungDeviceIds".getBytes(StandardCharsets.UTF_8)));
        } else {
            arrayList2.add(
                    SecureKeyGenerator.makeBytes(
                            -1879046090, "samsung".getBytes(StandardCharsets.UTF_8)));
        }
        X500Principal x500Principal = secureKeyGenParameterSpec.mCertificateSubject;
        if (x500Principal != null && !TextUtils.isEmpty(x500Principal.getName("RFC1779"))) {
            arrayList2.add(
                    SecureKeyGenerator.makeBytes(
                            -1879046089,
                            x500Principal.getName("RFC1779").getBytes(StandardCharsets.UTF_8)));
        }
        if (secureKeyGenParameterSpec.mVerifiableIntegrity) {
            if (KeymasterDefs.getTagType(1879050494) != 1879048192) {
                throw new IllegalArgumentException("Not a boolean tag: 1879050494");
            }
            KeyParameter keyParameter3 = new KeyParameter();
            keyParameter3.tag = 1879050494;
            keyParameter3.value = KeyParameterValue.boolValue(true);
            arrayList2.add(keyParameter3);
        }
        KeyParameter[] keyParameterArr2 =
                (KeyParameter[]) arrayList2.toArray(new KeyParameter[arrayList2.size()]);
        ISecureKeyGeneration.Stub.Proxy proxy =
                (ISecureKeyGeneration.Stub.Proxy) iSecureKeyGeneration;
        Parcel obtain = Parcel.obtain(proxy.mRemote);
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("android.security.securekeygeneration.ISecureKeyGeneration");
            obtain.writeTypedArray(keyParameterArr, 0);
            obtain.writeTypedArray(keyParameterArr2, 0);
            proxy.mRemote.transact(1, obtain, obtain2, 0);
            obtain2.readException();
            return (SecureKeyInfo) obtain2.readTypedObject(SecureKeyInfo.CREATOR);
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}
