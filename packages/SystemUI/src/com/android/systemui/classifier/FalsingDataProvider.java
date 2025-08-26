package com.android.systemui.classifier;

import android.hardware.devicestate.DeviceStateManager;
import android.util.DisplayMetrics;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0;
import com.android.systemui.dock.DockManager;
import com.android.systemui.statusbar.policy.BatteryController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class FalsingDataProvider {
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
    public TimeLimitedInputEventBuffer mRecentMotionEvents = new TimeLimitedInputEventBuffer(1000);
    public final TimeLimitedInputEventBuffer mRecentKeyEvents = new TimeLimitedInputEventBuffer(500);
    public List mPriorMotionEvents = new ArrayList();
    public boolean mDirty = true;
    public float mAngle = 0.0f;

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
            ((ArrayList) this.mGestureFinalizedListeners).forEach(new FalsingDataProvider$$ExternalSyntheticLambda0(this, 0));
            this.mPriorMotionEvents = this.mRecentMotionEvents;
            this.mRecentMotionEvents = new TimeLimitedInputEventBuffer(1000L);
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
    /* JADX WARN: Removed duplicated region for block: B:21:0x00eb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMotionEvent(MotionEvent motionEvent) {
        boolean z;
        int i = 1;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int pointerCount = motionEvent.getPointerCount();
        int i2 = 0;
        for (int i3 = 0; i3 < pointerCount; i3++) {
            MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
            motionEvent.getPointerProperties(i3, pointerProperties);
            arrayList2.add(pointerProperties);
        }
        MotionEvent.PointerProperties[] pointerPropertiesArr = new MotionEvent.PointerProperties[arrayList2.size()];
        arrayList2.toArray(pointerPropertiesArr);
        int historySize = motionEvent.getHistorySize();
        int i4 = 0;
        while (i4 < historySize) {
            ArrayList arrayList3 = new ArrayList();
            for (int i5 = i2; i5 < pointerCount; i5++) {
                MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
                motionEvent.getHistoricalPointerCoords(i5, i4, pointerCoords);
                arrayList3.add(pointerCoords);
            }
            arrayList.add(MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getHistoricalEventTime(i4), motionEvent.getAction(), pointerCount, pointerPropertiesArr, (MotionEvent.PointerCoords[]) arrayList3.toArray(new MotionEvent.PointerCoords[i2]), motionEvent.getMetaState(), motionEvent.getButtonState(), motionEvent.getXPrecision(), motionEvent.getYPrecision(), motionEvent.getDeviceId(), motionEvent.getEdgeFlags(), motionEvent.getSource(), motionEvent.getDisplayId(), motionEvent.getFlags(), motionEvent.getClassification()));
            i4++;
            i2 = i2;
        }
        int i6 = i2;
        arrayList.add(MotionEvent.obtainNoHistory(motionEvent));
        arrayList.size();
        if (BrightLineFalsingManager.DEBUG) {
            int size = arrayList.size();
            int i7 = i6;
            while (i7 < size) {
                Object obj = arrayList.get(i7);
                i7++;
                MotionEvent motionEvent2 = (MotionEvent) obj;
                motionEvent2.getX();
                motionEvent2.getY();
                motionEvent2.getEventTime();
                boolean z2 = BrightLineFalsingManager.DEBUG;
            }
        }
        if (motionEvent.getActionMasked() == 0) {
            completePriorGesture();
        }
        if (this.mRecentMotionEvents.size() < 3) {
            z = i6;
        } else {
            TimeLimitedInputEventBuffer timeLimitedInputEventBuffer = this.mRecentMotionEvents;
            MotionEvent motionEvent3 = (MotionEvent) ((InputEvent) ((ArrayList) timeLimitedInputEventBuffer.mInputEvents).get(timeLimitedInputEventBuffer.size() - 1));
            int i8 = (motionEvent.getActionMasked() == 1 && motionEvent3.getActionMasked() == 2) ? 1 : i6;
            int i9 = motionEvent.getEventTime() - motionEvent3.getEventTime() < 50 ? 1 : i6;
            if (i8 != 0 && i9 != 0) {
                z = 1;
            }
        }
        this.mDropLastEvent = z;
        if (!arrayList.isEmpty()) {
            TimeLimitedInputEventBuffer timeLimitedInputEventBuffer2 = this.mRecentKeyEvents;
            if (!timeLimitedInputEventBuffer2.isEmpty()) {
                Iterator it = timeLimitedInputEventBuffer2.iterator();
                while (it.hasNext()) {
                    ((KeyEvent) it.next()).recycle();
                }
                timeLimitedInputEventBuffer2.clear();
            }
        }
        this.mRecentMotionEvents.addAll(arrayList);
        this.mRecentMotionEvents.size();
        boolean z3 = BrightLineFalsingManager.DEBUG;
        ((ArrayList) this.mMotionEventListeners).forEach(new FalsingDataProvider$$ExternalSyntheticLambda0(motionEvent, i));
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
                this.mLastMotionEvent = (MotionEvent) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, recentMotionEvents);
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
