package com.android.systemui.util.sensors;

import android.os.Build;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.concurrency.ExecutionImpl;
import com.android.systemui.util.sensors.ThresholdSensor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ProximitySensorImpl implements ProximitySensor {
    public static final boolean DEBUG;
    public Runnable mCancelSecondaryRunnable;
    public final DelayableExecutor mDelayableExecutor;
    public int mDevicePosture;
    public final Execution mExecution;
    ThresholdSensorEvent mLastEvent;
    public ThresholdSensorEvent mLastPrimaryEvent;
    protected boolean mPaused;
    public ThresholdSensor mPrimaryThresholdSensor;
    public boolean mRegistered;
    public ThresholdSensor mSecondaryThresholdSensor;
    public final List mListeners = new ArrayList();
    public String mTag = null;
    public final AtomicBoolean mAlerting = new AtomicBoolean();
    public boolean mInitializedListeners = false;
    public boolean mSecondarySafe = false;
    public final ProximitySensorImpl$$ExternalSyntheticLambda1 mPrimaryEventListener = new ThresholdSensor.Listener() { // from class: com.android.systemui.util.sensors.ProximitySensorImpl$$ExternalSyntheticLambda1
        @Override // com.android.systemui.util.sensors.ThresholdSensor.Listener
        public final void onThresholdCrossed(ThresholdSensorEvent thresholdSensorEvent) {
            ProximitySensorImpl proximitySensorImpl = ProximitySensorImpl.this;
            ((ExecutionImpl) proximitySensorImpl.mExecution).assertIsMainThread();
            ThresholdSensorEvent thresholdSensorEvent2 = proximitySensorImpl.mLastPrimaryEvent;
            if (thresholdSensorEvent2 == null || thresholdSensorEvent.mBelow != thresholdSensorEvent2.mBelow) {
                proximitySensorImpl.mLastPrimaryEvent = thresholdSensorEvent;
                if (proximitySensorImpl.mSecondarySafe && proximitySensorImpl.mSecondaryThresholdSensor.isLoaded()) {
                    StringBuilder sb = new StringBuilder("Primary sensor reported ");
                    sb.append(thresholdSensorEvent.mBelow ? "near" : "far");
                    sb.append(". Checking secondary.");
                    proximitySensorImpl.logDebug(sb.toString());
                    if (proximitySensorImpl.mCancelSecondaryRunnable == null) {
                        proximitySensorImpl.mSecondaryThresholdSensor.resume();
                        return;
                    }
                    return;
                }
                if (!proximitySensorImpl.mSecondaryThresholdSensor.isLoaded()) {
                    proximitySensorImpl.logDebug("Primary sensor event: " + thresholdSensorEvent.mBelow + ". No secondary.");
                    proximitySensorImpl.onSensorEvent(thresholdSensorEvent);
                    return;
                }
                if (!thresholdSensorEvent.mBelow) {
                    proximitySensorImpl.onSensorEvent(thresholdSensorEvent);
                    return;
                }
                proximitySensorImpl.logDebug("Primary sensor event: " + thresholdSensorEvent.mBelow + ". Checking secondary.");
                Runnable runnable = proximitySensorImpl.mCancelSecondaryRunnable;
                if (runnable != null) {
                    runnable.run();
                }
                proximitySensorImpl.mSecondaryThresholdSensor.resume();
            }
        }
    };
    public final C35901 mSecondaryEventListener = new C35901();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.util.sensors.ProximitySensorImpl$1 */
    public final class C35901 implements ThresholdSensor.Listener {
        public C35901() {
        }

        @Override // com.android.systemui.util.sensors.ThresholdSensor.Listener
        public final void onThresholdCrossed(ThresholdSensorEvent thresholdSensorEvent) {
            ThresholdSensorEvent thresholdSensorEvent2;
            ProximitySensorImpl proximitySensorImpl = ProximitySensorImpl.this;
            if (!proximitySensorImpl.mSecondarySafe && ((thresholdSensorEvent2 = proximitySensorImpl.mLastPrimaryEvent) == null || !thresholdSensorEvent2.mBelow || !thresholdSensorEvent.mBelow)) {
                proximitySensorImpl.chooseSensor();
                ThresholdSensorEvent thresholdSensorEvent3 = proximitySensorImpl.mLastPrimaryEvent;
                if (thresholdSensorEvent3 == null || !thresholdSensorEvent3.mBelow) {
                    Runnable runnable = proximitySensorImpl.mCancelSecondaryRunnable;
                    if (runnable != null) {
                        runnable.run();
                        proximitySensorImpl.mCancelSecondaryRunnable = null;
                        return;
                    }
                    return;
                }
                proximitySensorImpl.mCancelSecondaryRunnable = proximitySensorImpl.mDelayableExecutor.executeDelayed(5000L, new Runnable() { // from class: com.android.systemui.util.sensors.ProximitySensorImpl$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ProximitySensorImpl proximitySensorImpl2 = ProximitySensorImpl.this;
                        proximitySensorImpl2.mPrimaryThresholdSensor.pause();
                        proximitySensorImpl2.mSecondaryThresholdSensor.resume();
                    }
                });
            }
            proximitySensorImpl.logDebug("Secondary sensor event: " + thresholdSensorEvent.mBelow + ".");
            if (proximitySensorImpl.mPaused) {
                return;
            }
            proximitySensorImpl.onSensorEvent(thresholdSensorEvent);
        }
    }

    static {
        DEBUG = Log.isLoggable("ProxSensor", 3) || Build.IS_DEBUGGABLE;
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.util.sensors.ProximitySensorImpl$$ExternalSyntheticLambda1] */
    public ProximitySensorImpl(ThresholdSensor thresholdSensor, ThresholdSensor thresholdSensor2, DelayableExecutor delayableExecutor, Execution execution) {
        this.mPrimaryThresholdSensor = thresholdSensor;
        this.mSecondaryThresholdSensor = thresholdSensor2;
        this.mDelayableExecutor = delayableExecutor;
        this.mExecution = execution;
    }

    public final void alertListeners() {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        AtomicBoolean atomicBoolean = this.mAlerting;
        if (atomicBoolean.getAndSet(true)) {
            return;
        }
        final ThresholdSensorEvent thresholdSensorEvent = this.mLastEvent;
        if (thresholdSensorEvent != null) {
            new ArrayList(this.mListeners).forEach(new Consumer() { // from class: com.android.systemui.util.sensors.ProximitySensorImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((ThresholdSensor.Listener) obj).onThresholdCrossed(ThresholdSensorEvent.this);
                }
            });
        }
        atomicBoolean.set(false);
    }

    public final void chooseSensor() {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        if (!this.mRegistered || this.mPaused || ((ArrayList) this.mListeners).isEmpty()) {
            return;
        }
        if (this.mSecondarySafe) {
            this.mSecondaryThresholdSensor.resume();
            this.mPrimaryThresholdSensor.pause();
        } else {
            this.mPrimaryThresholdSensor.resume();
            this.mSecondaryThresholdSensor.pause();
        }
    }

    @Override // com.android.systemui.util.sensors.ProximitySensor
    public void destroy() {
        pause();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public final boolean isLoaded() {
        return this.mPrimaryThresholdSensor.isLoaded();
    }

    public final Boolean isNear() {
        ThresholdSensorEvent thresholdSensorEvent;
        if (!isLoaded() || (thresholdSensorEvent = this.mLastEvent) == null) {
            return null;
        }
        return Boolean.valueOf(thresholdSensorEvent.mBelow);
    }

    public final void logDebug(String str) {
        if (DEBUG) {
            ExifInterface$$ExternalSyntheticOutline0.m36m(new StringBuilder(), this.mTag != null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("["), this.mTag, "] ") : "", str, "ProxSensor");
        }
    }

    public final void onSensorEvent(ThresholdSensorEvent thresholdSensorEvent) {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        ThresholdSensorEvent thresholdSensorEvent2 = this.mLastEvent;
        if (thresholdSensorEvent2 == null || thresholdSensorEvent.mBelow != thresholdSensorEvent2.mBelow) {
            if (!this.mSecondarySafe && !thresholdSensorEvent.mBelow) {
                chooseSensor();
            }
            this.mLastEvent = thresholdSensorEvent;
            alertListeners();
        }
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public final void pause() {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        this.mPaused = true;
        unregisterInternal();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public void register(ThresholdSensor.Listener listener) {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        if (isLoaded()) {
            ArrayList arrayList = (ArrayList) this.mListeners;
            if (arrayList.contains(listener)) {
                logDebug("ProxListener registered multiple times: " + listener);
            } else {
                arrayList.add(listener);
            }
            registerInternal();
        }
    }

    public final void registerInternal() {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        if (this.mRegistered || this.mPaused || ((ArrayList) this.mListeners).isEmpty()) {
            return;
        }
        if (!this.mInitializedListeners) {
            this.mPrimaryThresholdSensor.pause();
            this.mSecondaryThresholdSensor.pause();
            this.mPrimaryThresholdSensor.register(this.mPrimaryEventListener);
            this.mSecondaryThresholdSensor.register(this.mSecondaryEventListener);
            this.mInitializedListeners = true;
        }
        this.mRegistered = true;
        chooseSensor();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public final void resume() {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        this.mPaused = false;
        registerInternal();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public final void setDelay() {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        this.mPrimaryThresholdSensor.setDelay();
        this.mSecondaryThresholdSensor.setDelay();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public final void setTag(String str) {
        this.mTag = str;
        this.mPrimaryThresholdSensor.setTag(str + ":primary");
        this.mSecondaryThresholdSensor.setTag(str + ":secondary");
    }

    public String toString() {
        return String.format("{registered=%s, paused=%s, near=%s, posture=%s, primarySensor=%s, secondarySensor=%s secondarySafe=%s}", Boolean.valueOf(this.mRegistered), Boolean.valueOf(this.mPaused), isNear(), Integer.valueOf(this.mDevicePosture), this.mPrimaryThresholdSensor, this.mSecondaryThresholdSensor, Boolean.valueOf(this.mSecondarySafe));
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public void unregister(ThresholdSensor.Listener listener) {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        ArrayList arrayList = (ArrayList) this.mListeners;
        arrayList.remove(listener);
        if (arrayList.size() == 0) {
            unregisterInternal();
        }
    }

    public final void unregisterInternal() {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        if (this.mRegistered) {
            logDebug("unregistering sensor listener");
            this.mPrimaryThresholdSensor.pause();
            this.mSecondaryThresholdSensor.pause();
            Runnable runnable = this.mCancelSecondaryRunnable;
            if (runnable != null) {
                runnable.run();
                this.mCancelSecondaryRunnable = null;
            }
            this.mLastPrimaryEvent = null;
            this.mLastEvent = null;
            this.mRegistered = false;
        }
    }
}
