package com.android.systemui.classifier;

import android.hardware.devicestate.DeviceStateManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.android.systemui.dock.DockManager;
import com.android.systemui.statusbar.policy.BatteryController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FalsingDataProvider {
    public boolean mA11YAction;
    public final BatteryController mBatteryController;
    public final DockManager mDockManager;
    public boolean mDropLastEvent;
    public MotionEvent mFirstRecentMotionEvent;
    public final DeviceStateManager.FoldStateListener mFoldStateListener;
    public final int mHeightPixels;
    public final boolean mIsFoldableDevice;
    public boolean mJustUnlockedWithFace;
    public MotionEvent mLastMotionEvent;
    public final int mWidthPixels;
    public final float mXdpi;
    public final float mYdpi;
    public final List mSessionListeners = new ArrayList();
    public final List mMotionEventListeners = new ArrayList();
    public final List mGestureFinalizedListeners = new ArrayList();
    public TimeLimitedMotionEventBuffer mRecentMotionEvents = new TimeLimitedMotionEventBuffer(1000);
    public List mPriorMotionEvents = new ArrayList();
    public boolean mDirty = true;
    public float mAngle = 0.0f;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SessionListener {
    }

    public FalsingDataProvider(DisplayMetrics displayMetrics, BatteryController batteryController, DeviceStateManager.FoldStateListener foldStateListener, DockManager dockManager, boolean z) {
        this.mXdpi = displayMetrics.xdpi;
        this.mYdpi = displayMetrics.ydpi;
        this.mWidthPixels = displayMetrics.widthPixels;
        this.mHeightPixels = displayMetrics.heightPixels;
        this.mBatteryController = batteryController;
        this.mFoldStateListener = foldStateListener;
        this.mDockManager = dockManager;
        this.mIsFoldableDevice = z;
        boolean z2 = BrightLineFalsingManager.DEBUG;
    }

    public final void completePriorGesture() {
        if (!this.mRecentMotionEvents.isEmpty()) {
            ((ArrayList) this.mGestureFinalizedListeners).forEach(new FalsingDataProvider$$ExternalSyntheticLambda0(this, 1));
            this.mPriorMotionEvents = this.mRecentMotionEvents;
            this.mRecentMotionEvents = new TimeLimitedMotionEventBuffer(1000L);
        }
        this.mDropLastEvent = false;
        this.mA11YAction = false;
    }

    public final List getRecentMotionEvents() {
        if (!this.mDropLastEvent || this.mRecentMotionEvents.isEmpty()) {
            return this.mRecentMotionEvents;
        }
        return this.mRecentMotionEvents.subList(0, r2.size() - 1);
    }

    public final boolean isHorizontal() {
        recalculateData();
        return !this.mRecentMotionEvents.isEmpty() && Math.abs(this.mFirstRecentMotionEvent.getX() - this.mLastMotionEvent.getX()) > Math.abs(this.mFirstRecentMotionEvent.getY() - this.mLastMotionEvent.getY());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onMotionEvent(MotionEvent motionEvent) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int pointerCount = motionEvent.getPointerCount();
        int i = 0;
        for (int i2 = 0; i2 < pointerCount; i2++) {
            MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
            motionEvent.getPointerProperties(i2, pointerProperties);
            arrayList2.add(pointerProperties);
        }
        MotionEvent.PointerProperties[] pointerPropertiesArr = new MotionEvent.PointerProperties[arrayList2.size()];
        arrayList2.toArray(pointerPropertiesArr);
        int historySize = motionEvent.getHistorySize();
        int i3 = 0;
        while (i3 < historySize) {
            ArrayList arrayList3 = new ArrayList();
            for (int i4 = i; i4 < pointerCount; i4++) {
                MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
                motionEvent.getHistoricalPointerCoords(i4, i3, pointerCoords);
                arrayList3.add(pointerCoords);
            }
            arrayList.add(MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getHistoricalEventTime(i3), motionEvent.getAction(), pointerCount, pointerPropertiesArr, (MotionEvent.PointerCoords[]) arrayList3.toArray(new MotionEvent.PointerCoords[i]), motionEvent.getMetaState(), motionEvent.getButtonState(), motionEvent.getXPrecision(), motionEvent.getYPrecision(), motionEvent.getDeviceId(), motionEvent.getEdgeFlags(), motionEvent.getSource(), motionEvent.getFlags()));
            i3++;
            i = i;
            pointerPropertiesArr = pointerPropertiesArr;
            historySize = historySize;
            pointerCount = pointerCount;
        }
        int i5 = i;
        arrayList.add(MotionEvent.obtainNoHistory(motionEvent));
        arrayList.size();
        if (BrightLineFalsingManager.DEBUG) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                MotionEvent motionEvent2 = (MotionEvent) it.next();
                motionEvent2.getX();
                motionEvent2.getY();
                motionEvent2.getEventTime();
                boolean z2 = BrightLineFalsingManager.DEBUG;
            }
        }
        if (motionEvent.getActionMasked() == 0) {
            completePriorGesture();
        }
        if (this.mRecentMotionEvents.size() >= 3) {
            TimeLimitedMotionEventBuffer timeLimitedMotionEventBuffer = this.mRecentMotionEvents;
            MotionEvent motionEvent3 = (MotionEvent) ((ArrayList) timeLimitedMotionEventBuffer.mMotionEvents).get(timeLimitedMotionEventBuffer.size() - 1);
            int i6 = (motionEvent.getActionMasked() == 1 && motionEvent3.getActionMasked() == 2) ? 1 : i5;
            int i7 = motionEvent.getEventTime() - motionEvent3.getEventTime() < 50 ? 1 : i5;
            if (i6 != 0 && i7 != 0) {
                z = 1;
                this.mDropLastEvent = z;
                this.mRecentMotionEvents.addAll(arrayList);
                this.mRecentMotionEvents.size();
                boolean z3 = BrightLineFalsingManager.DEBUG;
                ((ArrayList) this.mMotionEventListeners).forEach(new FalsingDataProvider$$ExternalSyntheticLambda0(motionEvent, i5));
                this.mDirty = true;
            }
        }
        z = i5;
        this.mDropLastEvent = z;
        this.mRecentMotionEvents.addAll(arrayList);
        this.mRecentMotionEvents.size();
        boolean z32 = BrightLineFalsingManager.DEBUG;
        ((ArrayList) this.mMotionEventListeners).forEach(new FalsingDataProvider$$ExternalSyntheticLambda0(motionEvent, i5));
        this.mDirty = true;
    }

    public final void recalculateData() {
        if (this.mDirty) {
            List recentMotionEvents = getRecentMotionEvents();
            if (recentMotionEvents.isEmpty()) {
                this.mFirstRecentMotionEvent = null;
                this.mLastMotionEvent = null;
            } else {
                this.mFirstRecentMotionEvent = (MotionEvent) recentMotionEvents.get(0);
                this.mLastMotionEvent = (MotionEvent) recentMotionEvents.get(recentMotionEvents.size() - 1);
            }
            if (this.mRecentMotionEvents.size() >= 2) {
                this.mAngle = (float) Math.atan2(this.mLastMotionEvent.getY() - this.mFirstRecentMotionEvent.getY(), this.mLastMotionEvent.getX() - this.mFirstRecentMotionEvent.getX());
                while (true) {
                    float f = this.mAngle;
                    if (f >= 0.0f) {
                        break;
                    } else {
                        this.mAngle = f + 6.2831855f;
                    }
                }
                while (true) {
                    float f2 = this.mAngle;
                    if (f2 <= 6.2831855f) {
                        break;
                    } else {
                        this.mAngle = f2 - 6.2831855f;
                    }
                }
            } else {
                this.mAngle = Float.MAX_VALUE;
            }
            this.mDirty = false;
        }
    }
}
