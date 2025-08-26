package android.security.keystore2;

import android.app.AppGlobals;
import android.hardware.biometrics.BiometricManager;
import android.hardware.security.keymint.KeyParameter;
import android.hardware.security.keymint.KeyParameterValue;
import android.hardware.security.keymint.Tag;
import android.security.GateKeeper;
import android.security.keymaster.KeymasterDefs;
import android.security.keystore.UserAuthArgs;
import android.system.keystore2.Authorization;
import java.math.BigInteger;
import java.security.ProviderException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class KeyStore2ParameterUtils {
    static boolean isSecureHardware(int i) {
        return i == 1 || i == 2;
    }

    private KeyStore2ParameterUtils() {
    }

    static KeyParameter makeBool(int i) {
        if (KeymasterDefs.getTagType(i) != 1879048192) {
            throw new IllegalArgumentException("Not a boolean tag: " + i);
        }
        KeyParameter keyParameter = new KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = KeyParameterValue.boolValue(true);
        return keyParameter;
    }

    static KeyParameter makeEnum(int i, int i2) {
        KeyParameter keyParameter = new KeyParameter();
        keyParameter.tag = i;
        switch (i) {
            case 268435458:
                keyParameter.value = KeyParameterValue.algorithm(i2);
                return keyParameter;
            case 268435466:
                keyParameter.value = KeyParameterValue.ecCurve(i2);
                return keyParameter;
            case Tag.HARDWARE_TYPE /* 268435760 */:
                keyParameter.value = KeyParameterValue.securityLevel(i2);
                return keyParameter;
            case 268435960:
                keyParameter.value = KeyParameterValue.hardwareAuthenticatorType(i2);
                return keyParameter;
            case 268436158:
                keyParameter.value = KeyParameterValue.origin(i2);
                return keyParameter;
            case 536870913:
                keyParameter.value = KeyParameterValue.keyPurpose(i2);
                return keyParameter;
            case 536870916:
                keyParameter.value = KeyParameterValue.blockMode(i2);
                return keyParameter;
            case 536870917:
            case 536871115:
                keyParameter.value = KeyParameterValue.digest(i2);
                return keyParameter;
            case 536870918:
                keyParameter.value = KeyParameterValue.paddingMode(i2);
                return keyParameter;
            default:
                throw new IllegalArgumentException("Not an enum or repeatable enum tag: " + i);
        }
    }

    static KeyParameter makeInt(int i, int i2) {
        int tagType = KeymasterDefs.getTagType(i);
        if (tagType != 805306368 && tagType != 1073741824) {
            throw new IllegalArgumentException("Not an int or repeatable int tag: " + i);
        }
        KeyParameter keyParameter = new KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = KeyParameterValue.integer(i2);
        return keyParameter;
    }

    static KeyParameter makeLong(int i, long j) {
        int tagType = KeymasterDefs.getTagType(i);
        if (tagType != 1342177280 && tagType != -1610612736) {
            throw new IllegalArgumentException("Not a long or repeatable long tag: " + i);
        }
        KeyParameter keyParameter = new KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = KeyParameterValue.longInteger(j);
        return keyParameter;
    }

    static KeyParameter makeBytes(int i, byte[] bArr) {
        if (KeymasterDefs.getTagType(i) != -1879048192) {
            throw new IllegalArgumentException("Not a bytes tag: " + i);
        }
        KeyParameter keyParameter = new KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = KeyParameterValue.blob(bArr);
        return keyParameter;
    }

    static KeyParameter makeBignum(int i, BigInteger bigInteger) {
        if (KeymasterDefs.getTagType(i) != Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Not a bignum tag: " + i);
        }
        KeyParameter keyParameter = new KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = KeyParameterValue.blob(bigInteger.toByteArray());
        return keyParameter;
    }

    static KeyParameter makeDate(int i, Date date) {
        if (KeymasterDefs.getTagType(i) != 1610612736) {
            throw new IllegalArgumentException("Not a date tag: " + i);
        }
        KeyParameter keyParameter = new KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = KeyParameterValue.dateTime(date.getTime());
        return keyParameter;
    }

    static long getUnsignedInt(Authorization authorization) {
        if (KeymasterDefs.getTagType(authorization.keyParameter.tag) != 805306368) {
            throw new IllegalArgumentException("Not an int tag: " + authorization.keyParameter.tag);
        }
        return authorization.keyParameter.value.getInteger() & 4294967295L;
    }

    static Date getDate(Authorization authorization) {
        if (KeymasterDefs.getTagType(authorization.keyParameter.tag) != 1610612736) {
            throw new IllegalArgumentException("Not a date tag: " + authorization.keyParameter.tag);
        }
        if (authorization.keyParameter.value.getDateTime() < 0) {
            throw new IllegalArgumentException("Date Value too large: " + authorization.keyParameter.value.getDateTime());
        }
        return new Date(authorization.keyParameter.value.getDateTime());
    }

    static void forEachSetFlag(int i, Consumer<Integer> consumer) {
        int i2 = 0;
        while (i != 0) {
            if ((i & 1) == 1) {
                consumer.accept(Integer.valueOf(1 << i2));
            }
            i2++;
            i >>>= 1;
        }
    }

    private static long getRootSid() throws IllegalStateException {
        long secureUserId = GateKeeper.getSecureUserId();
        if (secureUserId != 0) {
            return secureUserId;
        }
        throw new IllegalStateException("Secure lock screen must be enabled to create keys requiring user authentication");
    }

    private static void addSids(List<KeyParameter> list, UserAuthArgs userAuthArgs) {
        if (userAuthArgs.getUserAuthenticationType() == 3) {
            if (userAuthArgs.getBoundToSpecificSecureUserId() != 0) {
                list.add(makeLong(-1610612234, userAuthArgs.getBoundToSpecificSecureUserId()));
                return;
            } else {
                list.add(makeLong(-1610612234, getRootSid()));
                return;
            }
        }
        ArrayList arrayList = new ArrayList();
        if ((userAuthArgs.getUserAuthenticationType() & 2) != 0) {
            long[] authenticatorIds = ((BiometricManager) AppGlobals.getInitialApplication().getSystemService(BiometricManager.class)).getAuthenticatorIds();
            if (authenticatorIds.length == 0) {
                throw new IllegalStateException("At least one biometric must be enrolled to create keys requiring user authentication for every use");
            }
            if (userAuthArgs.getBoundToSpecificSecureUserId() != 0) {
                arrayList.add(Long.valueOf(userAuthArgs.getBoundToSpecificSecureUserId()));
            } else if (userAuthArgs.isInvalidatedByBiometricEnrollment()) {
                for (long j : authenticatorIds) {
                    arrayList.add(Long.valueOf(j));
                }
            } else {
                arrayList.add(Long.valueOf(getRootSid()));
            }
        } else if ((userAuthArgs.getUserAuthenticationType() & 1) != 0) {
            arrayList.add(Long.valueOf(getRootSid()));
        } else {
            throw new IllegalStateException("Invalid or no authentication type specified.");
        }
        for (int i = 0; i < arrayList.size(); i++) {
            list.add(makeLong(-1610612234, ((Long) arrayList.get(i)).longValue()));
        }
    }

    static void addUserAuthArgs(List<KeyParameter> list, UserAuthArgs userAuthArgs) {
        if (userAuthArgs.isUserConfirmationRequired()) {
            list.add(makeBool(1879048700));
        }
        if (userAuthArgs.isUserPresenceRequired()) {
            list.add(makeBool(1879048699));
        }
        if (userAuthArgs.isUnlockedDeviceRequired()) {
            list.add(makeBool(1879048701));
        }
        if (!userAuthArgs.isUserAuthenticationRequired()) {
            list.add(makeBool(1879048695));
            return;
        }
        addSids(list, userAuthArgs);
        list.add(makeEnum(268435960, userAuthArgs.getUserAuthenticationType()));
        if (userAuthArgs.getUserAuthenticationValidityDurationSeconds() != 0) {
            list.add(makeInt(805306873, userAuthArgs.getUserAuthenticationValidityDurationSeconds()));
        }
        if (userAuthArgs.isUserAuthenticationValidWhileOnBody()) {
            if (userAuthArgs.getUserAuthenticationValidityDurationSeconds() == 0) {
                throw new ProviderException("Key validity extension while device is on-body is not supported for keys requiring fingerprint authentication");
            }
            list.add(makeBool(1879048698));
        }
    }
}
