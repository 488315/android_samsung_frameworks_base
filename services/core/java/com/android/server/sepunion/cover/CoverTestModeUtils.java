package com.android.server.sepunion.cover;

import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;

import com.samsung.android.sepunion.Log;

public final class CoverTestModeUtils {
    public static final boolean SHIPPED = !Debug.semIsProductDev();
    public static int sCurrentTestMode = -1;
    public static Rect sCurrentTestVisibleRect = new Rect();
    public final OnCoverTestModeChanged mCallback;
    public final Context mContext;
    public final AnonymousClass2 mObserver;
    public final AnonymousClass1 mTestModeChangeHandler =
            new Handler() { // from class: com.android.server.sepunion.cover.CoverTestModeUtils.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i = CoverTestModeUtils.sCurrentTestMode;
                    if (i != -1) {
                        CoverTestModeUtils.this.mCallback.onCoverTestModeChanged(i, true);
                    }
                }
            };
    public final AnonymousClass3 mVisibleRectObserver;

    public interface OnCoverTestModeChanged {
        void onCoverTestModeChanged(int i, boolean z);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.sepunion.cover.CoverTestModeUtils$1] */
    public CoverTestModeUtils(Context context, OnCoverTestModeChanged onCoverTestModeChanged) {
        if (SHIPPED) {
            Log.d(
                    "CoverManager_CoverTestModeUtils",
                    "This version has been shipped!! Then cover test mode is not available");
            return;
        }
        this.mContext = context;
        this.mCallback = onCoverTestModeChanged;
        ContentObserver contentObserver =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.android.server.sepunion.cover.CoverTestModeUtils.2
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        OnCoverTestModeChanged onCoverTestModeChanged2;
                        CoverTestModeUtils coverTestModeUtils = CoverTestModeUtils.this;
                        boolean z2 = CoverTestModeUtils.SHIPPED;
                        int i =
                                Settings.System.getInt(
                                        coverTestModeUtils.mContext.getContentResolver(),
                                        "cover_test_mode",
                                        -1);
                        CoverTestModeUtils coverTestModeUtils2 = CoverTestModeUtils.this;
                        coverTestModeUtils2.getClass();
                        Log.d(
                                "CoverManager_CoverTestModeUtils",
                                "updateCoverTestMode() sCurrentTestMode = "
                                        + CoverTestModeUtils.sCurrentTestMode
                                        + ", currentTestMode = "
                                        + i);
                        int i2 = CoverTestModeUtils.sCurrentTestMode;
                        if (i2 == i
                                || (onCoverTestModeChanged2 = coverTestModeUtils2.mCallback)
                                        == null) {
                            return;
                        }
                        if (i2 != -1) {
                            onCoverTestModeChanged2.onCoverTestModeChanged(i2, false);
                        }
                        CoverTestModeUtils.sCurrentTestMode = i;
                        coverTestModeUtils2.mTestModeChangeHandler.sendEmptyMessageDelayed(0, 500L);
                    }
                };
        context.getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("cover_test_mode"), false, contentObserver);
        sCurrentTestMode =
                Settings.System.getInt(this.mContext.getContentResolver(), "cover_test_mode", -1);
        ContentObserver contentObserver2 =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.android.server.sepunion.cover.CoverTestModeUtils.3
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        CoverTestModeUtils coverTestModeUtils = CoverTestModeUtils.this;
                        boolean z2 = CoverTestModeUtils.SHIPPED;
                        CoverTestModeUtils.sCurrentTestVisibleRect =
                                coverTestModeUtils.getTestVisibleRectFromSetting();
                    }
                };
        context.getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("cover_test_visible_rect"),
                        false,
                        contentObserver2);
        sCurrentTestVisibleRect = getTestVisibleRectFromSetting();
    }

    public static boolean isTestMode() {
        return (SHIPPED || sCurrentTestMode == -1) ? false : true;
    }

    public final Rect getTestVisibleRectFromSetting() {
        Rect rect = new Rect();
        String string =
                Settings.System.getString(
                        this.mContext.getContentResolver(), "cover_test_visible_rect");
        if (string != null) {
            try {
                String[] split = string.split(",");
                rect.left = Integer.parseInt(split[0]);
                rect.top = Integer.parseInt(split[1]);
                rect.right = Integer.parseInt(split[2]);
                rect.bottom = Integer.parseInt(split[3]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rect;
    }

    public final void setTestModeToSetting(int i) {
        Settings.System.putInt(this.mContext.getContentResolver(), "cover_test_mode", i);
    }
}
