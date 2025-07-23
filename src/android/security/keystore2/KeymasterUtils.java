package android.security.keystore2;

import android.app.ActivityThread;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.os.Build;
import android.os.RemoteException;
import android.security.keystore.KeyProperties;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* loaded from: classes3.dex */
public final class KeymasterUtils {
    private static final int DEVICE_KEYMASTER = 1;
    private static final int DEVICE_KEYMINT = 2;
    private static final int DEVICE_NOT_QC = 4;
    private static final int DEVICE_QC = 3;
    private static final int UNINITIALIZE = 0;
    private static int mIsQCDeivce;
    private static int mIsStrongBoxKeyMintDevice;
    private static int mIsTEEKeyMintDevice;

    private KeymasterUtils() {
    }

    static int getDigestOutputSizeBits(int i) {
        switch (i) {
            case 0:
                return -1;
            case 1:
                return 128;
            case 2:
                return 160;
            case 3:
                return 224;
            case 4:
                return 256;
            case 5:
                return 384;
            case 6:
                return 512;
            default:
                throw new IllegalArgumentException("Unknown digest: " + i);
        }
    }

    static boolean isKeymasterBlockModeIndCpaCompatibleWithSymmetricCrypto(int i) {
        if (i == 1) {
            return false;
        }
        if (i == 2 || i == 3 || i == 32) {
            return true;
        }
        throw new IllegalArgumentException("Unsupported block mode: " + i);
    }

    static boolean isKeymasterPaddingSchemeIndCpaCompatibleWithAsymmetricCrypto(int i) {
        if (i == 1) {
            return false;
        }
        if (i == 2 || i == 4) {
            return true;
        }
        throw new IllegalArgumentException("Unsupported asymmetric encryption padding scheme: " + i);
    }

    static String getEcCurveFromKeymaster(int i) {
        if (i == 0) {
            return "secp224r1";
        }
        if (i == 1) {
            return "secp256r1";
        }
        if (i == 2) {
            return "secp384r1";
        }
        if (i == 3) {
            return "secp521r1";
        }
        return "";
    }

    static int getKeymasterEcCurve(String str) {
        if (str.equals("secp224r1")) {
            return 0;
        }
        if (str.equals("secp256r1")) {
            return 1;
        }
        if (str.equals("secp384r1")) {
            return 2;
        }
        return str.equals("secp521r1") ? 3 : -1;
    }

    static ECParameterSpec getCurveSpec(String str) throws NoSuchAlgorithmException, InvalidParameterSpecException {
        AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance(KeyProperties.KEY_ALGORITHM_EC);
        algorithmParameters.init(new ECGenParameterSpec(str));
        return (ECParameterSpec) algorithmParameters.getParameterSpec(ECParameterSpec.class);
    }

    static String getCurveName(ECParameterSpec eCParameterSpec) {
        if (isECParameterSpecOfCurve(eCParameterSpec, "secp224r1")) {
            return "secp224r1";
        }
        if (isECParameterSpecOfCurve(eCParameterSpec, "secp256r1")) {
            return "secp256r1";
        }
        if (isECParameterSpecOfCurve(eCParameterSpec, "secp384r1")) {
            return "secp384r1";
        }
        if (isECParameterSpecOfCurve(eCParameterSpec, "secp521r1")) {
            return "secp521r1";
        }
        return null;
    }

    private static boolean isECParameterSpecOfCurve(ECParameterSpec eCParameterSpec, String str) {
        try {
            ECParameterSpec curveSpec = getCurveSpec(str);
            if (curveSpec.getCurve().equals(eCParameterSpec.getCurve()) && curveSpec.getOrder().equals(eCParameterSpec.getOrder())) {
                if (curveSpec.getGenerator().equals(eCParameterSpec.getGenerator())) {
                    return true;
                }
            }
        } catch (NoSuchAlgorithmException | InvalidParameterSpecException unused) {
        }
        return false;
    }

    public static boolean isQCDevice() {
        if (mIsQCDeivce == 0) {
            mIsQCDeivce = Build.HARDWARE.equalsIgnoreCase("qcom") ? 3 : 4;
        }
        return mIsQCDeivce == 3;
    }

    public static boolean isKeyMintDevice(int i) {
        try {
            if (i == 1) {
                if (mIsTEEKeyMintDevice == 0) {
                    mIsTEEKeyMintDevice = getKeyMintVersion(PackageManager.FEATURE_HARDWARE_KEYSTORE) >= 100 ? 2 : 1;
                }
                return mIsTEEKeyMintDevice == 2;
            }
            if (i != 2) {
                return false;
            }
            if (mIsStrongBoxKeyMintDevice == 0) {
                mIsStrongBoxKeyMintDevice = getKeyMintVersion(PackageManager.FEATURE_STRONGBOX_KEYSTORE) >= 100 ? 2 : 1;
            }
            return mIsStrongBoxKeyMintDevice == 2;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int getKeyMintVersion(String str) {
        try {
            ActivityThread.currentActivityThread();
            ParceledListSlice systemAvailableFeatures = ActivityThread.getPackageManager().getSystemAvailableFeatures();
            if (systemAvailableFeatures == null) {
                return -1;
            }
            for (FeatureInfo featureInfo : systemAvailableFeatures.getList()) {
                if (str.equalsIgnoreCase(featureInfo.name)) {
                    return featureInfo.version;
                }
            }
            return 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
