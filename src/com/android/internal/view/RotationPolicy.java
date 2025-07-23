package com.android.internal.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.android.internal.R;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes4.dex */
public final class RotationPolicy {
    private static final int CURRENT_ROTATION = -1;
    public static final int NATURAL_ROTATION = 0;
    private static final String TAG = "RotationPolicy";

    public static abstract class RotationPolicyListener {
        final ContentObserver mObserver = new ContentObserver(new Handler()) { // from class: com.android.internal.view.RotationPolicy.RotationPolicyListener.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                RotationPolicyListener.this.onChange();
            }
        };

        public abstract void onChange();
    }

    private static boolean useCurrentRotationOnRotationLockChange(Context context) {
        return true;
    }

    private RotationPolicy() {
    }

    public static boolean isRotationSupported(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER) && packageManager.hasSystemFeature(PackageManager.FEATURE_SCREEN_PORTRAIT) && packageManager.hasSystemFeature(PackageManager.FEATURE_SCREEN_LANDSCAPE) && context.getResources().getBoolean(R.bool.config_supportAutoRotation);
    }

    public static int getRotationLockOrientation(Context context) {
        try {
            return WindowManagerGlobal.getWindowManagerService().getRotationLockOrientation(context.getDisplayId());
        } catch (RemoteException unused) {
            Log.w(TAG, "Unable to get rotation lock orientation");
            if (areAllRotationsAllowed(context)) {
                return 0;
            }
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            boolean z = context.getResources().getConfiguration().windowConfiguration.getRotation() % 2 != 0;
            return (z ? displayMetrics.heightPixels : displayMetrics.widthPixels) < (z ? displayMetrics.widthPixels : displayMetrics.heightPixels) ? 1 : 2;
        }
    }

    public static boolean isRotationLockToggleVisible(Context context) {
        return isRotationSupported(context) && Settings.System.getIntForUser(context.getContentResolver(), Settings.System.HIDE_ROTATION_LOCK_TOGGLE_FOR_ACCESSIBILITY, 0, -2) == 0;
    }

    public static boolean isRotationLocked(Context context) {
        return Settings.System.getIntForUser(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0, -2) == 0;
    }

    public static void setRotationLock(Context context, boolean z, String str) {
        setRotationLockAtAngle(context, z, (areAllRotationsAllowed(context) || useCurrentRotationOnRotationLockChange(context)) ? -1 : 0, str);
    }

    public static void setRotationLockAtAngle(Context context, boolean z, int i, String str) {
        Settings.System.putIntForUser(context.getContentResolver(), Settings.System.HIDE_ROTATION_LOCK_TOGGLE_FOR_ACCESSIBILITY, 0, -2);
        setRotationLock(z, i, str);
    }

    public static void setRotationLockForAccessibility(Context context, boolean z, String str) {
        Settings.System.putIntForUser(context.getContentResolver(), Settings.System.HIDE_ROTATION_LOCK_TOGGLE_FOR_ACCESSIBILITY, z ? 1 : 0, -2);
        setRotationLock(z, 0, str);
    }

    private static boolean areAllRotationsAllowed(Context context) {
        return CoreRune.FW_ALLOW_ALL_ROTATION || context.getResources().getBoolean(R.bool.config_allowAllRotations);
    }

    private static void setRotationLock(final boolean z, final int i, final String str) {
        AsyncTask.execute(new Runnable() { // from class: com.android.internal.view.RotationPolicy.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
                    if (z) {
                        windowManagerService.freezeRotation(i, str);
                    } else {
                        windowManagerService.thawRotation(str);
                    }
                } catch (RemoteException unused) {
                    Log.w(RotationPolicy.TAG, "Unable to save auto-rotate setting");
                }
            }
        });
    }

    public static void registerRotationPolicyListener(Context context, RotationPolicyListener rotationPolicyListener) {
        registerRotationPolicyListener(context, rotationPolicyListener, UserHandle.getCallingUserId());
    }

    public static void registerRotationPolicyListener(Context context, RotationPolicyListener rotationPolicyListener, int i) {
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.ACCELEROMETER_ROTATION), false, rotationPolicyListener.mObserver, i);
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.HIDE_ROTATION_LOCK_TOGGLE_FOR_ACCESSIBILITY), false, rotationPolicyListener.mObserver, i);
    }

    public static void unregisterRotationPolicyListener(Context context, RotationPolicyListener rotationPolicyListener) {
        context.getContentResolver().unregisterContentObserver(rotationPolicyListener.mObserver);
    }
}
