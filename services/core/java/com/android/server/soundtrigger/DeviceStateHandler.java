package com.android.server.soundtrigger;

import com.android.server.soundtrigger.DeviceStateHandler;
import com.android.server.soundtrigger.PhoneCallStateHandler;
import com.android.server.utils.EventLogger;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class DeviceStateHandler implements PhoneCallStateHandler.Callback {
    public final Executor mCallbackExecutor;
    public final EventLogger mEventLogger;
    public final Object mLock = new Object();
    public SoundTriggerDeviceState mSoundTriggerDeviceState = SoundTriggerDeviceState.ENABLE;
    public int mSoundTriggerPowerSaveMode = 0;
    public boolean mIsPhoneCallOngoing = false;
    public NotificationTask mPhoneStateChangePendingNotify = null;
    public Set mCallbackSet = ConcurrentHashMap.newKeySet(4);
    public final Executor mDelayedNotificationExecutor = Executors.newSingleThreadExecutor();

    public interface DeviceStateListener {
        void onSoundTriggerDeviceStateUpdate(SoundTriggerDeviceState soundTriggerDeviceState);
    }

    public enum SoundTriggerDeviceState {
        DISABLE,
        CRITICAL,
        ENABLE
    }

    public void onPowerModeChanged(int i) {
        this.mEventLogger.enqueue(new SoundTriggerPowerEvent(i));
        synchronized (this.mLock) {
            if (i == this.mSoundTriggerPowerSaveMode) {
                return;
            }
            this.mSoundTriggerPowerSaveMode = i;
            evaluateStateChange();
        }
    }

    @Override // com.android.server.soundtrigger.PhoneCallStateHandler.Callback
    public void onPhoneCallStateChanged(boolean z) {
        this.mEventLogger.enqueue(new PhoneCallEvent(z));
        synchronized (this.mLock) {
            if (this.mIsPhoneCallOngoing == z) {
                return;
            }
            NotificationTask notificationTask = this.mPhoneStateChangePendingNotify;
            if (notificationTask != null) {
                notificationTask.cancel();
                this.mPhoneStateChangePendingNotify = null;
            }
            this.mIsPhoneCallOngoing = z;
            if (!z) {
                NotificationTask notificationTask2 = new NotificationTask(new Runnable() { // from class: com.android.server.soundtrigger.DeviceStateHandler.1
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (DeviceStateHandler.this.mLock) {
                            if (DeviceStateHandler.this.mPhoneStateChangePendingNotify != null && DeviceStateHandler.this.mPhoneStateChangePendingNotify.runnableEquals(this)) {
                                DeviceStateHandler.this.mPhoneStateChangePendingNotify = null;
                                DeviceStateHandler.this.evaluateStateChange();
                            }
                        }
                    }
                }, 1000L);
                this.mPhoneStateChangePendingNotify = notificationTask2;
                this.mDelayedNotificationExecutor.execute(notificationTask2);
            } else {
                evaluateStateChange();
            }
        }
    }

    public DeviceStateHandler(Executor executor, EventLogger eventLogger) {
        Objects.requireNonNull(executor);
        this.mCallbackExecutor = executor;
        Objects.requireNonNull(eventLogger);
        this.mEventLogger = eventLogger;
    }

    public SoundTriggerDeviceState getDeviceState() {
        SoundTriggerDeviceState soundTriggerDeviceState;
        synchronized (this.mLock) {
            soundTriggerDeviceState = this.mSoundTriggerDeviceState;
        }
        return soundTriggerDeviceState;
    }

    public void registerListener(final DeviceStateListener deviceStateListener) {
        final SoundTriggerDeviceState deviceState = getDeviceState();
        this.mCallbackExecutor.execute(new Runnable() { // from class: com.android.server.soundtrigger.DeviceStateHandler$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DeviceStateHandler.DeviceStateListener.this.onSoundTriggerDeviceStateUpdate(deviceState);
            }
        });
        this.mCallbackSet.add(deviceStateListener);
    }

    public void unregisterListener(DeviceStateListener deviceStateListener) {
        this.mCallbackSet.remove(deviceStateListener);
    }

    public void dump(PrintWriter printWriter) {
        synchronized (this.mLock) {
            printWriter.println("DeviceState: " + this.mSoundTriggerDeviceState.name());
            printWriter.println("PhoneState: " + this.mIsPhoneCallOngoing);
            printWriter.println("PowerSaveMode: " + this.mSoundTriggerPowerSaveMode);
        }
    }

    public final void evaluateStateChange() {
        SoundTriggerDeviceState computeState = computeState();
        if (this.mPhoneStateChangePendingNotify != null || this.mSoundTriggerDeviceState == computeState) {
            return;
        }
        this.mSoundTriggerDeviceState = computeState;
        this.mEventLogger.enqueue(new DeviceStateEvent(this.mSoundTriggerDeviceState));
        final SoundTriggerDeviceState soundTriggerDeviceState = this.mSoundTriggerDeviceState;
        for (final DeviceStateListener deviceStateListener : this.mCallbackSet) {
            this.mCallbackExecutor.execute(new Runnable() { // from class: com.android.server.soundtrigger.DeviceStateHandler$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DeviceStateHandler.DeviceStateListener.this.onSoundTriggerDeviceStateUpdate(soundTriggerDeviceState);
                }
            });
        }
    }

    public final SoundTriggerDeviceState computeState() {
        if (this.mIsPhoneCallOngoing) {
            return SoundTriggerDeviceState.DISABLE;
        }
        int i = this.mSoundTriggerPowerSaveMode;
        if (i == 0) {
            return SoundTriggerDeviceState.ENABLE;
        }
        if (i == 1) {
            return SoundTriggerDeviceState.CRITICAL;
        }
        if (i == 2) {
            return SoundTriggerDeviceState.DISABLE;
        }
        throw new IllegalStateException("Received unexpected power state code" + this.mSoundTriggerPowerSaveMode);
    }

    public class NotificationTask implements Runnable {
        public final CountDownLatch mCancelLatch = new CountDownLatch(1);
        public final Runnable mRunnable;
        public final long mWaitInMillis;

        public NotificationTask(Runnable runnable, long j) {
            this.mRunnable = runnable;
            this.mWaitInMillis = j;
        }

        public void cancel() {
            this.mCancelLatch.countDown();
        }

        public boolean runnableEquals(Runnable runnable) {
            return this.mRunnable == runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (this.mCancelLatch.await(this.mWaitInMillis, TimeUnit.MILLISECONDS)) {
                    return;
                }
                this.mRunnable.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new AssertionError("Unexpected InterruptedException", e);
            }
        }
    }

    public class PhoneCallEvent extends EventLogger.Event {
        public final boolean mIsInPhoneCall;

        public PhoneCallEvent(boolean z) {
            this.mIsInPhoneCall = z;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public String eventToString() {
            return "PhoneCallChange - inPhoneCall: " + this.mIsInPhoneCall;
        }
    }

    public class SoundTriggerPowerEvent extends EventLogger.Event {
        public final int mSoundTriggerPowerState;

        public SoundTriggerPowerEvent(int i) {
            this.mSoundTriggerPowerState = i;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public String eventToString() {
            return "SoundTriggerPowerChange: " + stateToString();
        }

        public final String stateToString() {
            int i = this.mSoundTriggerPowerState;
            if (i == 0) {
                return "All enabled";
            }
            if (i == 1) {
                return "Critical only";
            }
            if (i == 2) {
                return "All disabled";
            }
            return "Unknown power state: " + this.mSoundTriggerPowerState;
        }
    }

    public class DeviceStateEvent extends EventLogger.Event {
        public final SoundTriggerDeviceState mSoundTriggerDeviceState;

        public DeviceStateEvent(SoundTriggerDeviceState soundTriggerDeviceState) {
            this.mSoundTriggerDeviceState = soundTriggerDeviceState;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public String eventToString() {
            return "DeviceStateChange: " + this.mSoundTriggerDeviceState.name();
        }
    }
}
