package com.android.server.am;

import android.app.ActivityTaskManager;
import android.app.AppOpsManager;
import android.app.IActivityTaskManager;
import android.app.IAssistDataReceiver;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.IWindowManager;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AssistDataRequester extends IAssistDataReceiver.Stub {
    public final AppOpsManager mAppOpsManager;
    public final AssistDataRequesterCallbacks mCallbacks;
    public final Object mCallbacksLock;
    public boolean mCanceled;
    public final Context mContext;
    public int mPendingDataCount;
    public int mPendingScreenshotCount;
    public final int mRequestScreenshotAppOps;
    public final IWindowManager mWindowManager;
    public final ArrayList mAssistData = new ArrayList();
    public final ArrayList mAssistScreenshot = new ArrayList();
    public IActivityTaskManager mActivityTaskManager = ActivityTaskManager.getService();
    public final int mRequestStructureAppOps = 49;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface AssistDataRequesterCallbacks {
        boolean canHandleReceivedAssistDataLocked();

        void onAssistDataReceivedLocked(int i, int i2, Bundle bundle);

        default void onAssistRequestCompleted() {}

        default void onAssistScreenshotReceivedLocked(Bitmap bitmap) {}
    }

    public AssistDataRequester(
            Context context,
            IWindowManager iWindowManager,
            AppOpsManager appOpsManager,
            AssistDataRequesterCallbacks assistDataRequesterCallbacks,
            Object obj,
            int i) {
        this.mCallbacks = assistDataRequesterCallbacks;
        this.mCallbacksLock = obj;
        this.mWindowManager = iWindowManager;
        this.mContext = context;
        this.mAppOpsManager = appOpsManager;
        this.mRequestScreenshotAppOps = i;
    }

    public final void dispatchAssistDataReceived(Bundle bundle) {
        int i;
        int i2;
        Bundle bundle2 = bundle != null ? bundle.getBundle("receiverExtras") : null;
        if (bundle2 != null) {
            i = bundle2.getInt(LauncherConfigurationInternal.KEY_INDEX_INT);
            i2 = bundle2.getInt("count");
        } else {
            i = 0;
            i2 = 0;
        }
        this.mCallbacks.onAssistDataReceivedLocked(i, i2, bundle);
    }

    public final void flushPendingAssistData() {
        int size = this.mAssistData.size();
        for (int i = 0; i < size; i++) {
            dispatchAssistDataReceived((Bundle) this.mAssistData.get(i));
        }
        this.mAssistData.clear();
        int size2 = this.mAssistScreenshot.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mCallbacks.onAssistScreenshotReceivedLocked(
                    (Bitmap) this.mAssistScreenshot.get(i2));
        }
        this.mAssistScreenshot.clear();
    }

    public final void onHandleAssistData(Bundle bundle) {
        synchronized (this.mCallbacksLock) {
            try {
                if (this.mCanceled) {
                    return;
                }
                this.mPendingDataCount--;
                if (this.mCallbacks.canHandleReceivedAssistDataLocked()) {
                    flushPendingAssistData();
                    dispatchAssistDataReceived(bundle);
                    tryDispatchRequestComplete();
                } else {
                    this.mAssistData.add(bundle);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onHandleAssistScreenshot(Bitmap bitmap) {
        synchronized (this.mCallbacksLock) {
            try {
                if (this.mCanceled) {
                    return;
                }
                this.mPendingScreenshotCount--;
                if (this.mCallbacks.canHandleReceivedAssistDataLocked()) {
                    flushPendingAssistData();
                    this.mCallbacks.onAssistScreenshotReceivedLocked(bitmap);
                    tryDispatchRequestComplete();
                } else {
                    this.mAssistScreenshot.add(bitmap);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00c9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void requestData(
            java.util.List r20,
            boolean r21,
            boolean r22,
            boolean r23,
            boolean r24,
            boolean r25,
            boolean r26,
            int r27,
            java.lang.String r28,
            java.lang.String r29,
            boolean r30) {
        /*
            Method dump skipped, instructions count: 280
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AssistDataRequester.requestData(java.util.List,"
                    + " boolean, boolean, boolean, boolean, boolean, boolean, int,"
                    + " java.lang.String, java.lang.String, boolean):void");
    }

    public final void tryDispatchRequestComplete() {
        if (this.mPendingDataCount == 0
                && this.mPendingScreenshotCount == 0
                && this.mAssistData.isEmpty()
                && this.mAssistScreenshot.isEmpty()) {
            this.mCallbacks.onAssistRequestCompleted();
        }
    }
}
