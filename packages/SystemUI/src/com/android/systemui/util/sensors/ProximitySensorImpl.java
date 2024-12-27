package com.android.systemui.util.sensors;

import android.os.Build;
import android.util.Log;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.sensors.ProximitySensorImpl;
import com.android.systemui.util.sensors.ThresholdSensor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class ProximitySensorImpl implements ProximitySensor {
    private static final boolean DEBUG;
    private static final long SECONDARY_PING_INTERVAL_MS = 5000;
    private static final String TAG = "ProxSensor";
    private Runnable mCancelSecondaryRunnable;
    private final DelayableExecutor mDelayableExecutor;
    protected int mDevicePosture;
    private final Execution mExecution;
    ThresholdSensorEvent mLastEvent;
    private ThresholdSensorEvent mLastPrimaryEvent;
    protected boolean mPaused;
    ThresholdSensor mPrimaryThresholdSensor;
    private boolean mRegistered;
    ThresholdSensor mSecondaryThresholdSensor;
    private final List<ThresholdSensor.Listener> mListeners = new ArrayList();
    private String mTag = null;
    private final AtomicBoolean mAlerting = new AtomicBoolean();
    boolean mInitializedListeners = false;
    private boolean mSecondarySafe = false;
    final ThresholdSensor.Listener mPrimaryEventListener = new ThresholdSensor.Listener() { // from class: com.android.systemui.util.sensors.ProximitySensorImpl$$ExternalSyntheticLambda1
        @Override // com.android.systemui.util.sensors.ThresholdSensor.Listener
        public final void onThresholdCrossed(ThresholdSensorEvent thresholdSensorEvent) {
            ProximitySensorImpl.this.onPrimarySensorEvent(thresholdSensorEvent);
        }
    };
    final ThresholdSensor.Listener mSecondaryEventListener = new AnonymousClass1();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.util.sensors.ProximitySensorImpl$1, reason: invalid class name */
    class AnonymousClass1 implements ThresholdSensor.Listener {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onThresholdCrossed$0() {
            ProximitySensorImpl.this.mPrimaryThresholdSensor.pause();
            ProximitySensorImpl.this.mSecondaryThresholdSensor.resume();
        }

        @Override // com.android.systemui.util.sensors.ThresholdSensor.Listener
        public void onThresholdCrossed(ThresholdSensorEvent thresholdSensorEvent) {
            if (!ProximitySensorImpl.this.mSecondarySafe && (ProximitySensorImpl.this.mLastPrimaryEvent == null || !ProximitySensorImpl.this.mLastPrimaryEvent.getBelow() || !thresholdSensorEvent.getBelow())) {
                ProximitySensorImpl.this.chooseSensor();
                if (ProximitySensorImpl.this.mLastPrimaryEvent == null || !ProximitySensorImpl.this.mLastPrimaryEvent.getBelow()) {
                    if (ProximitySensorImpl.this.mCancelSecondaryRunnable != null) {
                        ProximitySensorImpl.this.mCancelSecondaryRunnable.run();
                        ProximitySensorImpl.this.mCancelSecondaryRunnable = null;
                        return;
                    }
                    return;
                }
                ProximitySensorImpl proximitySensorImpl = ProximitySensorImpl.this;
                proximitySensorImpl.mCancelSecondaryRunnable = proximitySensorImpl.mDelayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.util.sensors.ProximitySensorImpl$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ProximitySensorImpl.AnonymousClass1.this.lambda$onThresholdCrossed$0();
                    }
                }, ProximitySensorImpl.SECONDARY_PING_INTERVAL_MS);
            }
            ProximitySensorImpl.this.logDebug("Secondary sensor event: " + thresholdSensorEvent.getBelow() + ".");
            ProximitySensorImpl proximitySensorImpl2 = ProximitySensorImpl.this;
            if (proximitySensorImpl2.mPaused) {
                return;
            }
            proximitySensorImpl2.onSensorEvent(thresholdSensorEvent);
        }
    }

    static {
        DEBUG = Log.isLoggable(TAG, 3) || Build.IS_DEBUGGABLE;
    }

    public ProximitySensorImpl(@PrimaryProxSensor ThresholdSensor thresholdSensor, @SecondaryProxSensor ThresholdSensor thresholdSensor2, DelayableExecutor delayableExecutor, Execution execution) {
        this.mPrimaryThresholdSensor = thresholdSensor;
        this.mSecondaryThresholdSensor = thresholdSensor2;
        this.mDelayableExecutor = delayableExecutor;
        this.mExecution = execution;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void chooseSensor() {
        this.mExecution.assertIsMainThread();
        if (!this.mRegistered || this.mPaused || this.mListeners.isEmpty()) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public void onPrimarySensorEvent(ThresholdSensorEvent thresholdSensorEvent) {
        this.mExecution.assertIsMainThread();
        if (this.mLastPrimaryEvent == null || thresholdSensorEvent.getBelow() != this.mLastPrimaryEvent.getBelow()) {
            this.mLastPrimaryEvent = thresholdSensorEvent;
            if (this.mSecondarySafe && this.mSecondaryThresholdSensor.isLoaded()) {
                logDebug(ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("Primary sensor reported "), thresholdSensorEvent.getBelow() ? "near" : "far", ". Checking secondary."));
                if (this.mCancelSecondaryRunnable == null) {
                    this.mSecondaryThresholdSensor.resume();
                    return;
                }
                return;
            }
            if (!this.mSecondaryThresholdSensor.isLoaded()) {
                logDebug("Primary sensor event: " + thresholdSensorEvent.getBelow() + ". No secondary.");
                onSensorEvent(thresholdSensorEvent);
                return;
            }
            if (!thresholdSensorEvent.getBelow()) {
                onSensorEvent(thresholdSensorEvent);
                return;
            }
            logDebug("Primary sensor event: " + thresholdSensorEvent.getBelow() + ". Checking secondary.");
            Runnable runnable = this.mCancelSecondaryRunnable;
            if (runnable != null) {
                runnable.run();
            }
            this.mSecondaryThresholdSensor.resume();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSensorEvent(ThresholdSensorEvent thresholdSensorEvent) {
        this.mExecution.assertIsMainThread();
        if (this.mLastEvent == null || thresholdSensorEvent.getBelow() != this.mLastEvent.getBelow()) {
            if (!this.mSecondarySafe && !thresholdSensorEvent.getBelow()) {
                chooseSensor();
            }
            this.mLastEvent = thresholdSensorEvent;
            alertListeners();
        }
    }

    @Override // com.android.systemui.util.sensors.ProximitySensor
    public void alertListeners() {
        this.mExecution.assertIsMainThread();
        if (this.mAlerting.getAndSet(true)) {
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
        this.mAlerting.set(false);
    }

    @Override // com.android.systemui.util.sensors.ProximitySensor
    public void destroy() {
        pause();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public String getName() {
        return this.mPrimaryThresholdSensor.getName();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public String getType() {
        return this.mPrimaryThresholdSensor.getType();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public boolean isLoaded() {
        return this.mPrimaryThresholdSensor.isLoaded();
    }

    @Override // com.android.systemui.util.sensors.ProximitySensor
    public Boolean isNear() {
        ThresholdSensorEvent thresholdSensorEvent;
        if (!isLoaded() || (thresholdSensorEvent = this.mLastEvent) == null) {
            return null;
        }
        return Boolean.valueOf(thresholdSensorEvent.getBelow());
    }

    @Override // com.android.systemui.util.sensors.ProximitySensor
    public boolean isRegistered() {
        return this.mRegistered;
    }

    public void logDebug(String str) {
        if (DEBUG) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder(), this.mTag != null ? ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("["), this.mTag, "] ") : "", str, TAG);
        }
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public void pause() {
        this.mExecution.assertIsMainThread();
        this.mPaused = true;
        unregisterInternal();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public void register(ThresholdSensor.Listener listener) {
        this.mExecution.assertIsMainThread();
        if (isLoaded()) {
            if (this.mListeners.contains(listener)) {
                logDebug("ProxListener registered multiple times: " + listener);
            } else {
                this.mListeners.add(listener);
            }
            registerInternal();
        }
    }

    public void registerInternal() {
        this.mExecution.assertIsMainThread();
        if (this.mRegistered || this.mPaused || this.mListeners.isEmpty()) {
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
    public void resume() {
        this.mExecution.assertIsMainThread();
        this.mPaused = false;
        registerInternal();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public void setDelay(int i) {
        this.mExecution.assertIsMainThread();
        this.mPrimaryThresholdSensor.setDelay(i);
        this.mSecondaryThresholdSensor.setDelay(i);
    }

    @Override // com.android.systemui.util.sensors.ProximitySensor
    public void setSecondarySafe(boolean z) {
        this.mSecondarySafe = this.mSecondaryThresholdSensor.isLoaded() && z;
        chooseSensor();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public void setTag(String str) {
        this.mTag = str;
        this.mPrimaryThresholdSensor.setTag(str + ":primary");
        this.mSecondaryThresholdSensor.setTag(str + ":secondary");
    }

    public String toString() {
        return String.format("{registered=%s, paused=%s, near=%s, posture=%s, primarySensor=%s, secondarySensor=%s secondarySafe=%s}", Boolean.valueOf(isRegistered()), Boolean.valueOf(this.mPaused), isNear(), Integer.valueOf(this.mDevicePosture), this.mPrimaryThresholdSensor, this.mSecondaryThresholdSensor, Boolean.valueOf(this.mSecondarySafe));
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public void unregister(ThresholdSensor.Listener listener) {
        this.mExecution.assertIsMainThread();
        this.mListeners.remove(listener);
        if (this.mListeners.size() == 0) {
            unregisterInternal();
        }
    }

    public void unregisterInternal() {
        this.mExecution.assertIsMainThread();
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
