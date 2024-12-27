package com.android.server.sepunion;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;

import com.samsung.android.sepunion.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SemDeviceInfoTestModeUtils {
    public static final boolean SHIPPED = !Debug.semIsProductDev();
    public static int sCurrentTestMode = 0;
    public final OnDeviceInfoTestModeChanged mCallback;
    public final Context mContext;
    public final AnonymousClass2 mObserver;
    public final AnonymousClass1 mTestModeChangeHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.android.server.sepunion.SemDeviceInfoTestModeUtils.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    SemDeviceInfoTestModeUtils.this.mCallback.onDeviceInfoTestModeChanged();
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface OnDeviceInfoTestModeChanged {
        void onDeviceInfoTestModeChanged();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.sepunion.SemDeviceInfoTestModeUtils$1] */
    public SemDeviceInfoTestModeUtils(
            Context context, OnDeviceInfoTestModeChanged onDeviceInfoTestModeChanged) {
        if (SHIPPED) {
            Log.d(
                    "SemDeviceInfoTestModeUtils",
                    "This version has been shipped!! Then device info test mode is not available");
            return;
        }
        this.mContext = context;
        this.mCallback = onDeviceInfoTestModeChanged;
        ContentObserver contentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.server.sepunion.SemDeviceInfoTestModeUtils.2
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        SemDeviceInfoTestModeUtils.sCurrentTestMode =
                                Settings.System.getInt(
                                        SemDeviceInfoTestModeUtils.this.mContext
                                                .getContentResolver(),
                                        "deviceinfo_test_mode",
                                        0);
                        Log.d(
                                "SemDeviceInfoTestModeUtils",
                                "updateDeviceInfoTestMode() sCurrentTestMode = "
                                        + SemDeviceInfoTestModeUtils.sCurrentTestMode);
                        sendEmptyMessageDelayed(0, 500L);
                    }
                };
        context.getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("deviceinfo_test_mode"), false, contentObserver);
        sCurrentTestMode =
                Settings.System.getInt(context.getContentResolver(), "deviceinfo_test_mode", 0);
    }
}
