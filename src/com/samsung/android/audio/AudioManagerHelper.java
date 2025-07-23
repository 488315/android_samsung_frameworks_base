package com.samsung.android.audio;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.media.MediaMetrics;
import android.os.Binder;
import android.os.SystemProperties;
import android.telecom.Logging.Session;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.common.AsPackageName;
import com.samsung.android.media.AudioParameter;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
public class AudioManagerHelper {
    private static final int LOGGING_CALLER_DEFAULT_DEPTH = 5;
    private static final String TAG = "AudioManagerHelper";
    private static final boolean USER_SHIP;
    private static final ArrayList<String> mLoggingPackages = new ArrayList<>(Arrays.asList("android", AsPackageName.SYSTEMUI, "com.android.settings"));

    static /* synthetic */ String lambda$getSimpleClassName$0(String str, String str2) {
        return str2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x002d, code lost:
    
        if (android.os.SystemProperties.getBoolean("ro.product_ship", true) != false) goto L8;
     */
    static {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            java.lang.String r1 = "com.android.systemui"
            java.lang.String r2 = "com.android.settings"
            java.lang.String r3 = "android"
            java.lang.String[] r1 = new java.lang.String[]{r3, r1, r2}
            java.util.List r1 = java.util.Arrays.asList(r1)
            r0.<init>(r1)
            com.samsung.android.audio.AudioManagerHelper.mLoggingPackages = r0
            java.lang.String r0 = "ro.build.type"
            java.lang.String r1 = "user"
            java.lang.String r0 = android.os.SystemProperties.get(r0, r1)
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L30
            java.lang.String r0 = "ro.product_ship"
            r1 = 1
            boolean r0 = android.os.SystemProperties.getBoolean(r0, r1)
            if (r0 == 0) goto L30
            goto L31
        L30:
            r1 = 0
        L31:
            com.samsung.android.audio.AudioManagerHelper.USER_SHIP = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.audio.AudioManagerHelper.<clinit>():void");
    }

    public static void setMusicShareSyncDelay(int i) {
        AudioSystem.setParameters(new AudioParameter.Builder().setParam(AudioParameter.SEC_GLOBAL_SET_A2DP_AV_SYNC, "musicshare," + i).build().toString());
    }

    public static boolean isFMPlayerActive() {
        try {
            return Integer.parseInt(SystemProperties.get("persist.audio.isfmactive")) == 1;
        } catch (Exception unused) {
            return false;
        }
    }

    public static String getFmRadioPackageName(Context context) {
        String[] packagesForUid = context.getPackageManager().getPackagesForUid(Binder.getCallingUid());
        if (packagesForUid == null) {
            return AsPackageName.FM_RADIO;
        }
        return packagesForUid[0];
    }

    public static void semSetAudioHDR(boolean z) {
        AudioManager.setAudioServiceConfig("audioHDR=" + (z ? 1 : 0));
    }

    public static String getAddressForLog(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() != 17 || !USER_SHIP) {
            return str;
        }
        String replaceAll = str.replaceAll(":", "");
        return replaceAll.substring(0, 6) + Session.SESSION_SEPARATION_CHAR_CHILD + replaceAll.substring(11);
    }

    public static String getAddressForLog(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return null;
        }
        return getAddressForLog(bluetoothDevice.getAddress());
    }

    public static boolean isSupportSoftPhone() {
        return isTablet() && isAttModelWithSoftPhone();
    }

    private static boolean isTablet() {
        return SystemProperties.get("ro.build.characteristics", "").contains(BnRConstants.DEVICETYPE_TABLET);
    }

    private static boolean isAttModelWithSoftPhone() {
        try {
            String str = SystemProperties.get("persist.omc.sales_code");
            if (TextUtils.isEmpty(str)) {
                str = SystemProperties.get("ro.csc.sales_code");
            }
            if (TextUtils.equals("ATT", str)) {
                return true;
            }
            return TextUtils.equals("APP", str);
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean needToLogCaller(String str) {
        return mLoggingPackages.contains(str);
    }

    public static void logCaller() {
        logging(getTrace(), "");
    }

    public static void logCaller(String str, Object... objArr) {
        logging(getTrace(), String.format(str, objArr));
    }

    private static StackTraceElement[] getTrace() {
        return Thread.currentThread().getStackTrace();
    }

    private static void logging(StackTraceElement[] stackTraceElementArr, String str) {
        Log.d(getSimpleClassName(stackTraceElementArr[4]), stackTraceElementArr[4].getMethodName() + " : " + str + " callers=" + buildCallStack(stackTraceElementArr, 5));
    }

    public static String buildCallStack(StackTraceElement[] stackTraceElementArr, int i) {
        return (String) List.of((Object[]) stackTraceElementArr).subList(5, Math.min(i + 5, stackTraceElementArr.length)).stream().map(new Function() { // from class: com.samsung.android.audio.AudioManagerHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String makeMethodString;
                makeMethodString = AudioManagerHelper.makeMethodString((StackTraceElement) obj);
                return makeMethodString;
            }
        }).collect(Collectors.joining("<-"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String makeMethodString(StackTraceElement stackTraceElement) {
        return getSimpleClassName(stackTraceElement) + MediaMetrics.SEPARATOR + stackTraceElement.getMethodName() + ":" + stackTraceElement.getLineNumber();
    }

    private static String getSimpleClassName(StackTraceElement stackTraceElement) {
        return (String) Arrays.stream(stackTraceElement.getClassName().split("\\.")).reduce(new BinaryOperator() { // from class: com.samsung.android.audio.AudioManagerHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AudioManagerHelper.lambda$getSimpleClassName$0((String) obj, (String) obj2);
            }
        }).orElse("");
    }

    public static String convertStartingPathToSystem(String str) {
        if (str == null || !str.startsWith("/product/media/audio/ui/")) {
            return str;
        }
        String replaceFirst = str.replaceFirst("/product", "/system");
        Log.e(TAG, "convert starting path: " + replaceFirst);
        return replaceFirst;
    }
}
