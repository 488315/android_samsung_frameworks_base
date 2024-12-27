package com.android.server.accessibility;

import android.accessibilityservice.GestureDescription;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MotionEventInjector extends BaseEventStreamTransformation
        implements Handler.Callback {
    public static MotionEvent.PointerCoords[] sPointerCoords;
    public static MotionEvent.PointerProperties[] sPointerProps;
    public long mDownTime;
    public final Handler mHandler;
    public long mLastScheduledEventTime;
    public GestureDescription.TouchPoint[] mLastTouchPoints;
    public int mNumLastTouchPoints;
    public IAccessibilityServiceClient mServiceInterfaceForCurrentGesture;
    public final AccessibilityTraceManager mTrace;
    public final SparseArray mOpenGesturesInProgress = new SparseArray();
    public final IntArray mSequencesInProgress = new IntArray(5);
    public boolean mIsDestroyed = false;
    public final SparseIntArray mStrokeIdToPointerId = new SparseIntArray(5);

    public MotionEventInjector(Looper looper, AccessibilityTraceManager accessibilityTraceManager) {
        this.mHandler = new Handler(looper, this);
        this.mTrace = accessibilityTraceManager;
    }

    public static int findPointByStrokeId(
            GestureDescription.TouchPoint[] touchPointArr, int i, int i2) {
        for (int i3 = 0; i3 < i; i3++) {
            if (touchPointArr[i3].mStrokeId == i2) {
                return i3;
            }
        }
        return -1;
    }

    public final void cancelAnyGestureInProgress() {
        if (this.mNext != null) {
            SparseArray sparseArray = this.mOpenGesturesInProgress;
            Boolean bool = Boolean.FALSE;
            if (((Boolean) sparseArray.get(4098, bool)).booleanValue()) {
                long uptimeMillis = SystemClock.uptimeMillis();
                MotionEvent obtainMotionEvent =
                        obtainMotionEvent(uptimeMillis, uptimeMillis, 3, getLastTouchPoints(), 1);
                sendMotionEventToNext(obtainMotionEvent, obtainMotionEvent, 1073872896);
                this.mOpenGesturesInProgress.put(4098, bool);
            }
        }
    }

    public final void cancelAnyPendingInjectedEvents() {
        if (this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
            cancelAnyGestureInProgress();
            for (int size = this.mSequencesInProgress.size() - 1; size >= 0; size--) {
                notifyService(
                        this.mServiceInterfaceForCurrentGesture,
                        this.mSequencesInProgress.get(size),
                        false);
                this.mSequencesInProgress.remove(size);
            }
        } else if (this.mNumLastTouchPoints != 0) {
            cancelAnyGestureInProgress();
        }
        this.mNumLastTouchPoints = 0;
        this.mStrokeIdToPointerId.clear();
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void clearEvents(int i) {
        if (this.mHandler.hasMessages(1)) {
            return;
        }
        this.mOpenGesturesInProgress.put(i, Boolean.FALSE);
    }

    public final GestureDescription.TouchPoint[] getLastTouchPoints() {
        if (this.mLastTouchPoints == null) {
            int maxStrokeCount = GestureDescription.getMaxStrokeCount();
            this.mLastTouchPoints = new GestureDescription.TouchPoint[maxStrokeCount];
            for (int i = 0; i < maxStrokeCount; i++) {
                this.mLastTouchPoints[i] = new GestureDescription.TouchPoint();
            }
        }
        return this.mLastTouchPoints;
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x013c, code lost:

       if (r5 == 0) goto L66;
    */
    @Override // android.os.Handler.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean handleMessage(android.os.Message r30) {
        /*
            Method dump skipped, instructions count: 872
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.MotionEventInjector.handleMessage(android.os.Message):boolean");
    }

    public final void notifyService(
            IAccessibilityServiceClient iAccessibilityServiceClient, int i, boolean z) {
        try {
            iAccessibilityServiceClient.onPerformGestureResult(i, z);
        } catch (RemoteException e) {
            Slog.e(
                    "MotionEventInjector",
                    "Error sending motion event injection status to "
                            + this.mServiceInterfaceForCurrentGesture,
                    e);
        } catch (NullPointerException unused) {
            Slog.e("MotionEventInjector", "Null pointer exception in notifyService");
        }
    }

    public final MotionEvent obtainMotionEvent(
            long j, long j2, int i, GestureDescription.TouchPoint[] touchPointArr, int i2) {
        MotionEvent.PointerCoords[] pointerCoordsArr = sPointerCoords;
        if (pointerCoordsArr == null || pointerCoordsArr.length < i2) {
            sPointerCoords = new MotionEvent.PointerCoords[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                sPointerCoords[i3] = new MotionEvent.PointerCoords();
            }
        }
        MotionEvent.PointerProperties[] pointerPropertiesArr = sPointerProps;
        if (pointerPropertiesArr == null || pointerPropertiesArr.length < i2) {
            sPointerProps = new MotionEvent.PointerProperties[i2];
            for (int i4 = 0; i4 < i2; i4++) {
                sPointerProps[i4] = new MotionEvent.PointerProperties();
            }
        }
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = this.mStrokeIdToPointerId.get(touchPointArr[i5].mStrokeId, -1);
            if (i6 == -1) {
                i6 = 0;
                while (true) {
                    if (this.mStrokeIdToPointerId.indexOfValue(i6) < 0) {
                        break;
                    }
                    i6++;
                    if (i6 >= 10) {
                        i6 = 10;
                        break;
                    }
                }
                this.mStrokeIdToPointerId.put(touchPointArr[i5].mStrokeId, i6);
            }
            MotionEvent.PointerProperties pointerProperties = sPointerProps[i5];
            pointerProperties.id = i6;
            pointerProperties.toolType = 0;
            sPointerCoords[i5].clear();
            MotionEvent.PointerCoords pointerCoords = sPointerCoords[i5];
            pointerCoords.pressure = 1.0f;
            pointerCoords.size = 1.0f;
            GestureDescription.TouchPoint touchPoint = touchPointArr[i5];
            pointerCoords.x = touchPoint.mX;
            pointerCoords.y = touchPoint.mY;
        }
        return MotionEvent.obtain(
                j, j2, i, i2, sPointerProps, sPointerCoords, 0, 0, 1.0f, 1.0f, -1, 0, 4098, 0);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onDestroy() {
        cancelAnyPendingInjectedEvents();
        this.mIsDestroyed = true;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (this.mTrace.isA11yTracingEnabledForTypes(12288L)) {
            this.mTrace.logTrace(
                    "MotionEventInjector.onMotionEvent",
                    12288L,
                    "event=" + motionEvent + ";rawEvent=" + motionEvent2 + ";policyFlags=" + i);
        }
        if (motionEvent.isFromSource(8194)
                && motionEvent.getActionMasked() == 7
                && ((Boolean) this.mOpenGesturesInProgress.get(4098, Boolean.FALSE))
                        .booleanValue()) {
            return;
        }
        cancelAnyPendingInjectedEvents();
        sendMotionEventToNext(motionEvent, motionEvent2, i | 131072);
    }

    public final void sendMotionEventToNext(
            MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (this.mNext != null) {
            super.onMotionEvent(motionEvent, motionEvent2, i);
            if (motionEvent.getActionMasked() == 0) {
                this.mOpenGesturesInProgress.put(motionEvent.getSource(), Boolean.TRUE);
            }
            if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
                this.mOpenGesturesInProgress.put(motionEvent.getSource(), Boolean.FALSE);
            }
        }
    }
}
