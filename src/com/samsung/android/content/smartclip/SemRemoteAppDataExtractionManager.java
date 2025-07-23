package com.samsung.android.content.smartclip;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.InputEvent;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class SemRemoteAppDataExtractionManager {
    private static final String TAG = "SemRemoteAppDataExtractionManager";
    private SpenGestureManager mManager;

    public SemRemoteAppDataExtractionManager(Context context) {
        this.mManager = null;
        if (context == null) {
            Log.e(TAG, "SemRemoteAppDataExtractionManager : Context is null! ");
            throw new RuntimeException("Context should not be null!");
        }
        SpenGestureManager spenGestureManager = (SpenGestureManager) context.getSystemService(Context.SEM_SPEN_GESTURE_SERVICE);
        this.mManager = spenGestureManager;
        if (spenGestureManager != null) {
            return;
        }
        Log.e(TAG, "SemRemoteAppDataExtractionManager : Failed to connect to the service");
        throw new RuntimeException("Failed to connect to the service. Feature is not supported");
    }

    public SemSmartClipDataRepository getSmartClipDataByScreenRect(Rect rect, IBinder iBinder, int i) {
        return this.mManager.getSmartClipDataByScreenRect(rect, iBinder, i);
    }

    public Bundle getScrollableAreaInfo(Rect rect, IBinder iBinder) {
        if (rect == null) {
            Log.e(TAG, "getScrollableAreaInfo : rect is null!");
            return null;
        }
        return this.mManager.getScrollableAreaInfo(rect, iBinder);
    }

    public Bundle getScrollableViewInfo(Rect rect, int i, IBinder iBinder) {
        if (rect == null) {
            Log.e(TAG, "getScrollableViewInfo : rect is null!");
            return null;
        }
        return this.mManager.getScrollableViewInfo(rect, i, iBinder);
    }

    public boolean injectInputEvent(int i, int i2, ArrayList<InputEvent> arrayList, boolean z, IBinder iBinder) {
        if (arrayList == null || arrayList.size() == 0) {
            Log.e(TAG, "injectInputEvent : Empty input event");
            return false;
        }
        this.mManager.injectInputEvent(i, i2, arrayList, z, iBinder);
        return true;
    }
}
