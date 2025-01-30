package com.android.server.biometrics.sensors.face;

import android.content.Context;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.FaceManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import android.view.Surface;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/* loaded from: classes.dex */
public abstract class SemFaceUtils {
    public static Bundle mBundle;
    public static byte[] mFidoRequestData;
    public static byte[] mFidoResultData;
    public static boolean mNeedtoAuthenticateExt;
    public static Surface mSurface;

    public static boolean semBigDataFABKCondition(int i) {
        return i >= 4;
    }

    public static boolean semBigDataFALICondition(int i) {
        return i >= 4;
    }

    public static boolean semBigDataFALQCondition(int i, int i2) {
        return ((double) (i2 * 5)) * 0.7d < ((double) i);
    }

    public static boolean semBigDataFAMOCondition(int i) {
        return i >= 4;
    }

    public static void setFidoRequestData(byte[] bArr) {
        if (Utils.DEBUG) {
            if (bArr == null) {
                Log.i("SemFace", "fidoRequestData is null");
            } else {
                Log.i("SemFace", "fidoRequestData(" + bArr.length + ") = " + Arrays.toString(bArr));
            }
        }
        mFidoRequestData = bArr;
    }

    public static byte[] getFidoRequestData() {
        byte[] bArr = mFidoRequestData;
        mFidoRequestData = null;
        return bArr == null ? new byte[0] : bArr;
    }

    public static ArrayList getFidoRequestDataAsArrayList() {
        ArrayList arrayList = new ArrayList();
        byte[] bArr = mFidoRequestData;
        if (bArr != null && bArr.length > 0) {
            for (byte b : bArr) {
                arrayList.add(Byte.valueOf(b));
            }
        }
        mFidoRequestData = null;
        return arrayList;
    }

    public static void setFidoResultData(ArrayList arrayList) {
        if (arrayList == null) {
            mFidoResultData = new byte[0];
            return;
        }
        mFidoResultData = new byte[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            mFidoResultData[i] = ((Byte) arrayList.get(i)).byteValue();
        }
        if (Utils.DEBUG) {
            Log.i("SemFace", "fidoResultData(" + arrayList.size() + ") = " + Arrays.toString(mFidoResultData));
        }
    }

    public static void setFidoResultData(byte[] bArr) {
        if (bArr == null) {
            bArr = new byte[0];
        }
        mFidoResultData = bArr;
        if (!Utils.DEBUG || mFidoResultData.length >= 128) {
            return;
        }
        Log.i("SemFace", "fidoResultData(" + mFidoResultData.length + ") = " + Arrays.toString(mFidoResultData));
    }

    public static byte[] getFidoResultData() {
        byte[] bArr = mFidoResultData;
        byte[] bArr2 = bArr != null ? (byte[]) bArr.clone() : null;
        mFidoResultData = null;
        return bArr2;
    }

    public static void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    public static Bundle getBundle() {
        return mBundle;
    }

    public static void resetBundle() {
        mBundle = null;
    }

    public static void setSurface(Surface surface) {
        mSurface = surface;
        mNeedtoAuthenticateExt = true;
    }

    public static Surface getSurface() {
        return mSurface;
    }

    public static boolean needToAuthenticateExt() {
        return mNeedtoAuthenticateExt;
    }

    public static void resetSurface() {
        mSurface = null;
        mNeedtoAuthenticateExt = false;
    }

    public static String byteArrayToHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(String.format("%02x", Integer.valueOf(b & 255)));
        }
        return sb.toString();
    }

    public static boolean hasPrivilegedAttr(Bundle bundle, int i) {
        return ((bundle == null ? 0 : bundle.getInt("sem_privileged_attr", 0)) & i) != 0;
    }

    public static boolean isNoFaceGuideEvents(int i, int i2) {
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 16:
            case 17:
            case 18:
                return true;
            case 13:
            case 14:
            case 15:
            case 20:
            case 21:
            default:
                return false;
            case 19:
                return !SemBiometricFeature.FEATURE_JDM_HAL;
            case 22:
                switch (i2) {
                    case 1006:
                    case 1007:
                    case 1008:
                    case 1009:
                    case 1011:
                    case 1012:
                    case 1013:
                    case 1014:
                        return true;
                    case 1010:
                    default:
                        return false;
                }
        }
    }

    public static int getSecurityMode(Context context) {
        int i = isOpenEyesMode(context) ? 4 : 0;
        if (isMaskSettingOn(context) && FaceManager.semIsSupportOnMask()) {
            i |= 16;
        }
        Slog.i("SemFace", "mode=0x" + Integer.toBinaryString(i));
        return i;
    }

    public static boolean isOpenEyesMode(Context context) {
        return 1 == Settings.Secure.getIntForUser(context.getContentResolver(), "face_open_eyes", -1, -2);
    }

    public static boolean isMaskSettingOn(Context context) {
        return 1 == Settings.Secure.getIntForUser(context.getContentResolver(), "face_recognize_mask", -1, -2);
    }

    public static FaceAuthenticateOptions copyOptions(int i, FaceAuthenticateOptions faceAuthenticateOptions) {
        return new FaceAuthenticateOptions.Builder().setUserId(i).setSensorId(faceAuthenticateOptions.getSensorId()).setOpPackageName(faceAuthenticateOptions.getOpPackageName()).setAttributionTag(faceAuthenticateOptions.getAttributionTag()).setDisplayState(faceAuthenticateOptions.getDisplayState()).build();
    }

    public static int getNoMatchReason(int i, int i2, int i3, int i4, int i5) {
        if (semBigDataFALICondition(i)) {
            return 100002;
        }
        if (semBigDataFABKCondition(i2)) {
            return 100005;
        }
        if (semBigDataFAMOCondition(i3)) {
            return 100006;
        }
        return semBigDataFALQCondition(i4, i5) ? 100003 : 100004;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:36:0x0084 -> B:12:0x00a1). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:65:0x003b -> B:42:0x0068). Please report as a decompilation issue!!! */
    public static void saveEnrolledFacePostion(Context context, int i, int i2) {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        Properties properties = new Properties();
        File file = new File(Environment.getUserSystemDirectory(0), "face_enrolled_position_" + i2 + ".xml");
        ?? exists = file.exists();
        FileOutputStream fileOutputStream2 = null;
        FileInputStream fileInputStream2 = null;
        fileOutputStream2 = null;
        fileOutputStream2 = null;
        fileOutputStream2 = null;
        try {
            try {
            } catch (Throwable th) {
                th = th;
                fileInputStream2 = exists;
            }
        } catch (Exception e) {
            String exc = e.toString();
            Log.w("SemFace", exc);
            exists = exc;
        }
        if (exists != 0) {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    properties.load(fileInputStream);
                    fileInputStream.close();
                    exists = fileInputStream;
                } catch (Exception e2) {
                    e = e2;
                    Log.w("SemFace", e.toString());
                    exists = fileInputStream;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                        exists = fileInputStream;
                    }
                    properties.setProperty(String.valueOf(i), String.valueOf(getFoldState(context)));
                    try {
                        try {
                            fileOutputStream = new FileOutputStream(file);
                            properties.store(fileOutputStream, (String) null);
                            fileOutputStream.close();
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (Exception e3) {
                        e = e3;
                    }
                }
            } catch (Exception e4) {
                e = e4;
                fileInputStream = null;
            } catch (Throwable th3) {
                th = th3;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception e5) {
                        Log.w("SemFace", e5.toString());
                    }
                }
                throw th;
            }
        }
        properties.setProperty(String.valueOf(i), String.valueOf(getFoldState(context)));
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (Exception e6) {
            Log.w("SemFace", e6.toString());
        }
        try {
            properties.store(fileOutputStream, (String) null);
            fileOutputStream.close();
        } catch (Exception e7) {
            e = e7;
            fileOutputStream2 = fileOutputStream;
            Log.w("SemFace", e.toString());
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (Exception e8) {
                    Log.w("SemFace", e8.toString());
                }
            }
            throw th;
        }
    }

    public static int getEnrolledPositionForFaceID(int i, int i2) {
        FileInputStream fileInputStream;
        Properties properties = new Properties();
        FileInputStream fileInputStream2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(new File(Environment.getUserSystemDirectory(0), "face_enrolled_position_" + i2 + ".xml"));
            } catch (Throwable unused) {
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            properties.load(fileInputStream);
            try {
                fileInputStream.close();
            } catch (Exception e2) {
                e = e2;
                Log.w("SemFace", e.toString());
                return Integer.valueOf(properties.getProperty(String.valueOf(i), String.valueOf(0))).intValue();
            }
        } catch (Exception e3) {
            e = e3;
            fileInputStream2 = fileInputStream;
            Log.w("SemFace", e.toString());
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (Exception e4) {
                    e = e4;
                    Log.w("SemFace", e.toString());
                    return Integer.valueOf(properties.getProperty(String.valueOf(i), String.valueOf(0))).intValue();
                }
            }
            return Integer.valueOf(properties.getProperty(String.valueOf(i), String.valueOf(0))).intValue();
        } catch (Throwable unused2) {
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (Exception e5) {
                    e = e5;
                    Log.w("SemFace", e.toString());
                    return Integer.valueOf(properties.getProperty(String.valueOf(i), String.valueOf(0))).intValue();
                }
            }
            return Integer.valueOf(properties.getProperty(String.valueOf(i), String.valueOf(0))).intValue();
        }
        return Integer.valueOf(properties.getProperty(String.valueOf(i), String.valueOf(0))).intValue();
    }

    public static int getFoldState(Context context) {
        if (SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FLIP || SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FOLD) {
            return (Utils.isFolderOpened(context) || Utils.isFlipOpened(context)) ? 1 : 2;
        }
        return 0;
    }
}
