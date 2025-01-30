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

/* loaded from: classes3.dex */
public class SemDeviceInfoTestModeUtils {
    public static final String TAG = "SemDeviceInfoTestModeUtils";
    public OnDeviceInfoTestModeChanged mCallback;
    public Context mContext;
    public ContentObserver mObserver;
    public Handler mTestModeChangeHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.server.sepunion.SemDeviceInfoTestModeUtils.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            SemDeviceInfoTestModeUtils.this.mCallback.onDeviceInfoTestModeChanged();
        }
    };
    public static final boolean SHIPPED = !Debug.semIsProductDev();
    public static int sCurrentTestMode = 0;

    public interface OnDeviceInfoTestModeChanged {
        void onDeviceInfoTestModeChanged();
    }

    public SemDeviceInfoTestModeUtils(Context context, OnDeviceInfoTestModeChanged onDeviceInfoTestModeChanged) {
        if (SHIPPED) {
            Log.d(TAG, "This version has been shipped!! Then device info test mode is not available");
            return;
        }
        this.mContext = context;
        this.mCallback = onDeviceInfoTestModeChanged;
        this.mObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.server.sepunion.SemDeviceInfoTestModeUtils.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                SemDeviceInfoTestModeUtils.updateTestModeFromSetting(SemDeviceInfoTestModeUtils.this.mContext);
                Log.d(SemDeviceInfoTestModeUtils.TAG, "updateDeviceInfoTestMode() sCurrentTestMode = " + SemDeviceInfoTestModeUtils.sCurrentTestMode);
                SemDeviceInfoTestModeUtils.this.mTestModeChangeHandler.sendEmptyMessageDelayed(0, 500L);
            }
        };
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("deviceinfo_test_mode"), false, this.mObserver);
        updateTestModeFromSetting(this.mContext);
    }

    public static void updateTestModeFromSetting(Context context) {
        sCurrentTestMode = Settings.System.getInt(context.getContentResolver(), "deviceinfo_test_mode", 0);
    }
}
