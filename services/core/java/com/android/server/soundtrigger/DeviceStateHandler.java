package com.android.server.soundtrigger;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;

import com.android.server.utils.EventLogger;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class DeviceStateHandler {
    public final Executor mCallbackExecutor;
    public final EventLogger mEventLogger;
    public final Object mLock = new Object();
    public SoundTriggerDeviceState mSoundTriggerDeviceState = SoundTriggerDeviceState.ENABLE;
    public int mSoundTriggerPowerSaveMode = 0;
    public boolean mIsPhoneCallOngoing = false;
    public NotificationTask mPhoneStateChangePendingNotify = null;
    public final Set mCallbackSet = ConcurrentHashMap.newKeySet(4);
    public final Executor mDelayedNotificationExecutor = Executors.newSingleThreadExecutor();

    public final class DeviceStateEvent extends EventLogger.Event {
        public final SoundTriggerDeviceState mSoundTriggerDeviceState;

        public DeviceStateEvent(SoundTriggerDeviceState soundTriggerDeviceState) {
            this.mSoundTriggerDeviceState = soundTriggerDeviceState;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public final String eventToString() {
            return "DeviceStateChange: " + this.mSoundTriggerDeviceState.name();
        }
    }

    public final class NotificationTask implements Runnable {
        public final Runnable mRunnable;
        public final CountDownLatch mCancelLatch = new CountDownLatch(1);
        public final long mWaitInMillis = 1000;

        public NotificationTask(AnonymousClass1 anonymousClass1) {
            this.mRunnable = anonymousClass1;
        }

        @Override // java.lang.Runnable
        public final void run() {
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

    public final class PhoneCallEvent extends EventLogger.Event {
        public final boolean mIsInPhoneCall;

        public PhoneCallEvent(boolean z) {
            this.mIsInPhoneCall = z;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public final String eventToString() {
            return "PhoneCallChange - inPhoneCall: " + this.mIsInPhoneCall;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class SoundTriggerDeviceState {
        public static final /* synthetic */ SoundTriggerDeviceState[] $VALUES;
        public static final SoundTriggerDeviceState CRITICAL;
        public static final SoundTriggerDeviceState DISABLE;
        public static final SoundTriggerDeviceState ENABLE;

        static {
            SoundTriggerDeviceState soundTriggerDeviceState =
                    new SoundTriggerDeviceState("DISABLE", 0);
            DISABLE = soundTriggerDeviceState;
            SoundTriggerDeviceState soundTriggerDeviceState2 =
                    new SoundTriggerDeviceState("CRITICAL", 1);
            CRITICAL = soundTriggerDeviceState2;
            SoundTriggerDeviceState soundTriggerDeviceState3 =
                    new SoundTriggerDeviceState("ENABLE", 2);
            ENABLE = soundTriggerDeviceState3;
            $VALUES =
                    new SoundTriggerDeviceState[] {
                        soundTriggerDeviceState, soundTriggerDeviceState2, soundTriggerDeviceState3
                    };
        }

        public static SoundTriggerDeviceState valueOf(String str) {
            return (SoundTriggerDeviceState) Enum.valueOf(SoundTriggerDeviceState.class, str);
        }

        public static SoundTriggerDeviceState[] values() {
            return (SoundTriggerDeviceState[]) $VALUES.clone();
        }
    }

    public final class SoundTriggerPowerEvent extends EventLogger.Event {
        public final int mSoundTriggerPowerState;

        public SoundTriggerPowerEvent(int i) {
            this.mSoundTriggerPowerState = i;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public final String eventToString() {
            StringBuilder sb = new StringBuilder("SoundTriggerPowerChange: ");
            int i = this.mSoundTriggerPowerState;
            sb.append(
                    i != 0
                            ? i != 1
                                    ? i != 2
                                            ? VibrationParam$1$$ExternalSyntheticOutline0.m(
                                                    i, "Unknown power state: ")
                                            : "All disabled"
                                    : "Critical only"
                            : "All enabled");
            return sb.toString();
        }
    }

    public DeviceStateHandler(Executor executor, EventLogger eventLogger) {
        Objects.requireNonNull(executor);
        this.mCallbackExecutor = executor;
        this.mEventLogger = eventLogger;
    }

    public final void evaluateStateChange() {
        boolean z = this.mIsPhoneCallOngoing;
        SoundTriggerDeviceState soundTriggerDeviceState = SoundTriggerDeviceState.DISABLE;
        if (!z) {
            int i = this.mSoundTriggerPowerSaveMode;
            if (i == 0) {
                soundTriggerDeviceState = SoundTriggerDeviceState.ENABLE;
            } else if (i == 1) {
                soundTriggerDeviceState = SoundTriggerDeviceState.CRITICAL;
            } else if (i != 2) {
                throw new IllegalStateException(
                        "Received unexpected power state code" + this.mSoundTriggerPowerSaveMode);
            }
        }
        if (this.mPhoneStateChangePendingNotify != null
                || this.mSoundTriggerDeviceState == soundTriggerDeviceState) {
            return;
        }
        this.mSoundTriggerDeviceState = soundTriggerDeviceState;
        this.mEventLogger.enqueue(new DeviceStateEvent(this.mSoundTriggerDeviceState));
        SoundTriggerDeviceState soundTriggerDeviceState2 = this.mSoundTriggerDeviceState;
        Iterator it = this.mCallbackSet.iterator();
        while (it.hasNext()) {
            this.mCallbackExecutor.execute(
                    new DeviceStateHandler$$ExternalSyntheticLambda0(
                            (SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1)
                                    it.next(),
                            soundTriggerDeviceState2,
                            0));
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.soundtrigger.DeviceStateHandler$1] */
    public final void onPhoneCallStateChanged(boolean z) {
        this.mEventLogger.enqueue(new PhoneCallEvent(z));
        synchronized (this.mLock) {
            try {
                if (this.mIsPhoneCallOngoing == z) {
                    return;
                }
                NotificationTask notificationTask = this.mPhoneStateChangePendingNotify;
                if (notificationTask != null) {
                    notificationTask.mCancelLatch.countDown();
                    this.mPhoneStateChangePendingNotify = null;
                }
                this.mIsPhoneCallOngoing = z;
                if (z) {
                    evaluateStateChange();
                } else {
                    NotificationTask notificationTask2 =
                            new NotificationTask(
                                    new Runnable() { // from class:
                                                     // com.android.server.soundtrigger.DeviceStateHandler.1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            synchronized (DeviceStateHandler.this.mLock) {
                                                DeviceStateHandler deviceStateHandler =
                                                        DeviceStateHandler.this;
                                                NotificationTask notificationTask3 =
                                                        deviceStateHandler
                                                                .mPhoneStateChangePendingNotify;
                                                if (notificationTask3 != null
                                                        && notificationTask3.mRunnable == this) {
                                                    deviceStateHandler
                                                                    .mPhoneStateChangePendingNotify =
                                                            null;
                                                    deviceStateHandler.evaluateStateChange();
                                                }
                                            }
                                        }
                                    });
                    this.mPhoneStateChangePendingNotify = notificationTask2;
                    this.mDelayedNotificationExecutor.execute(notificationTask2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onPowerModeChanged(int i) {
        this.mEventLogger.enqueue(new SoundTriggerPowerEvent(i));
        synchronized (this.mLock) {
            try {
                if (i == this.mSoundTriggerPowerSaveMode) {
                    return;
                }
                this.mSoundTriggerPowerSaveMode = i;
                evaluateStateChange();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerListener(
            SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1
                    soundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1) {
        SoundTriggerDeviceState soundTriggerDeviceState;
        synchronized (this.mLock) {
            soundTriggerDeviceState = this.mSoundTriggerDeviceState;
        }
        this.mCallbackExecutor.execute(
                new DeviceStateHandler$$ExternalSyntheticLambda0(
                        soundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1,
                        soundTriggerDeviceState,
                        1));
        this.mCallbackSet.add(
                soundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1);
    }
}
