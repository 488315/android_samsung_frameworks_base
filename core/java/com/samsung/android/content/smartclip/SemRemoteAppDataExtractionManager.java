package com.samsung.android.content.smartclip;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.InputEvent;

import java.util.ArrayList;

public class SemRemoteAppDataExtractionManager {
    private static final String TAG = "SemRemoteAppDataExtractionManager";
    private SpenGestureManager mManager;

    public SemRemoteAppDataExtractionManager(Context context) {
        this.mManager = null;
        if (context == null) {
            Log.e(TAG, "SemRemoteAppDataExtractionManager : Context is null! ");
            throw new RuntimeException("Context should not be null!");
        }
        this.mManager =
                (SpenGestureManager) context.getSystemService(Context.SEM_SPEN_GESTURE_SERVICE);
        if (this.mManager == null) {
            Log.e(TAG, "SemRemoteAppDataExtractionManager : Failed to connect to the service");
            throw new RuntimeException(
                    "Failed to connect to the service. Feature is not supported");
        }
    }

    public SemSmartClipDataRepository getSmartClipDataFromCurrentScreen() {
        return getSmartClipDataByScreenRect(null, null, 1);
    }

    public SemSmartClipDataRepository getSmartClipDataByScreenRect(
            Rect rect, IBinder skipWindowToken, int extractionMode) {
        return this.mManager.getSmartClipDataByScreenRect(rect, skipWindowToken, extractionMode);
    }

    public Bundle getScrollableAreaInfo(Rect rect, IBinder skipWindowToken) {
        if (rect == null) {
            Log.e(TAG, "getScrollableAreaInfo : rect is null!");
            return null;
        }
        return this.mManager.getScrollableAreaInfo(rect, skipWindowToken);
    }

    public Bundle getScrollableViewInfo(Rect rect, int viewHash, IBinder skipWindowToken) {
        if (rect == null) {
            Log.e(TAG, "getScrollableViewInfo : rect is null!");
            return null;
        }
        return this.mManager.getScrollableViewInfo(rect, viewHash, skipWindowToken);
    }

    public boolean injectInputEvent(
            int targetX,
            int targetY,
            ArrayList<InputEvent> inputEvents,
            boolean waitUntilConsume,
            IBinder skipWindowToken) {
        if (inputEvents == null || inputEvents.size() == 0) {
            Log.e(TAG, "injectInputEvent : Empty input event");
            return false;
        }
        this.mManager.injectInputEvent(
                targetX, targetY, inputEvents, waitUntilConsume, skipWindowToken);
        return true;
    }
}
