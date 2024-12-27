package com.android.server.soundtrigger;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Slog;
import com.android.internal.telephony.flags.Flags;
import com.android.server.soundtrigger.PhoneCallStateHandler;
import com.android.server.soundtrigger.PhoneCallStateHandler.MyCallStateListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PhoneCallStateHandler {
    public final DeviceStateHandler mCallback;
    public final ExecutorService mExecutor;
    public final SubscriptionManager mSubscriptionManager;
    public final TelephonyManager mTelephonyManager;
    public final Object mLock = new Object();
    public final List mListenerList = new ArrayList();
    public final AtomicBoolean mIsPhoneCallOngoing = new AtomicBoolean(false);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyCallStateListener extends TelephonyCallback implements TelephonyCallback.CallStateListener {
        public final TelephonyManager mTelephonyManagerForSubId;

        public MyCallStateListener(TelephonyManager telephonyManager) {
            this.mTelephonyManagerForSubId = telephonyManager;
        }

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public final void onCallStateChanged(int i) {
            boolean anyMatch;
            final PhoneCallStateHandler phoneCallStateHandler = PhoneCallStateHandler.this;
            List<SubscriptionInfo> activeSubscriptionInfoList = phoneCallStateHandler.mSubscriptionManager.getActiveSubscriptionInfoList();
            if (activeSubscriptionInfoList == null) {
                anyMatch = false;
            } else if (Flags.enforceTelephonyFeatureMapping()) {
                final int i2 = 1;
                anyMatch = activeSubscriptionInfoList.stream().filter(new PhoneCallStateHandler$$ExternalSyntheticLambda0(2)).anyMatch(new Predicate() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        int i3 = i2;
                        PhoneCallStateHandler phoneCallStateHandler2 = phoneCallStateHandler;
                        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
                        switch (i3) {
                            case 0:
                                break;
                            default:
                                phoneCallStateHandler2.getClass();
                                try {
                                    break;
                                }
                        }
                        return PhoneCallStateHandler.isCallOngoingFromState(phoneCallStateHandler2.mTelephonyManager.createForSubscriptionId(subscriptionInfo.getSubscriptionId()).getCallStateForSubscription());
                    }
                });
            } else {
                final int i3 = 0;
                anyMatch = activeSubscriptionInfoList.stream().filter(new PhoneCallStateHandler$$ExternalSyntheticLambda0(1)).anyMatch(new Predicate() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        int i32 = i3;
                        PhoneCallStateHandler phoneCallStateHandler2 = phoneCallStateHandler;
                        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
                        switch (i32) {
                            case 0:
                                break;
                            default:
                                phoneCallStateHandler2.getClass();
                                try {
                                    break;
                                }
                        }
                        return PhoneCallStateHandler.isCallOngoingFromState(phoneCallStateHandler2.mTelephonyManager.createForSubscriptionId(subscriptionInfo.getSubscriptionId()).getCallStateForSubscription());
                    }
                });
            }
            if (phoneCallStateHandler.mIsPhoneCallOngoing.compareAndSet(!anyMatch, anyMatch)) {
                phoneCallStateHandler.mCallback.onPhoneCallStateChanged(anyMatch);
            }
        }
    }

    public PhoneCallStateHandler(SubscriptionManager subscriptionManager, TelephonyManager telephonyManager, DeviceStateHandler deviceStateHandler) {
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        this.mExecutor = newSingleThreadExecutor;
        Objects.requireNonNull(subscriptionManager);
        SubscriptionManager createForAllUserProfiles = subscriptionManager.createForAllUserProfiles();
        this.mSubscriptionManager = createForAllUserProfiles;
        Objects.requireNonNull(telephonyManager);
        this.mTelephonyManager = telephonyManager;
        Objects.requireNonNull(deviceStateHandler);
        this.mCallback = deviceStateHandler;
        createForAllUserProfiles.addOnSubscriptionsChangedListener(newSingleThreadExecutor, new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler.1
            public final void onAddListenerFailed() {
                Slog.wtf("SoundTriggerPhoneCallStateHandler", "Failed to add a telephony listener");
            }

            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public final void onSubscriptionsChanged() {
                final PhoneCallStateHandler phoneCallStateHandler = PhoneCallStateHandler.this;
                synchronized (phoneCallStateHandler.mLock) {
                    try {
                        Iterator it = ((ArrayList) phoneCallStateHandler.mListenerList).iterator();
                        while (it.hasNext()) {
                            final MyCallStateListener myCallStateListener = (MyCallStateListener) it.next();
                            PhoneCallStateHandler.this.mExecutor.execute(new Runnable() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$MyCallStateListener$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    PhoneCallStateHandler.MyCallStateListener myCallStateListener2 = PhoneCallStateHandler.MyCallStateListener.this;
                                    myCallStateListener2.mTelephonyManagerForSubId.unregisterTelephonyCallback(myCallStateListener2);
                                }
                            });
                        }
                        ((ArrayList) phoneCallStateHandler.mListenerList).clear();
                        List<SubscriptionInfo> activeSubscriptionInfoList = phoneCallStateHandler.mSubscriptionManager.getActiveSubscriptionInfoList();
                        if (activeSubscriptionInfoList == null) {
                            return;
                        }
                        activeSubscriptionInfoList.stream().filter(new PhoneCallStateHandler$$ExternalSyntheticLambda0(0)).map(new Function() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda1
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return PhoneCallStateHandler.this.mTelephonyManager.createForSubscriptionId(((SubscriptionInfo) obj).getSubscriptionId());
                            }
                        }).forEach(new Consumer() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                PhoneCallStateHandler phoneCallStateHandler2 = PhoneCallStateHandler.this;
                                TelephonyManager telephonyManager2 = (TelephonyManager) obj;
                                synchronized (phoneCallStateHandler2.mLock) {
                                    PhoneCallStateHandler.MyCallStateListener myCallStateListener2 = phoneCallStateHandler2.new MyCallStateListener(telephonyManager2);
                                    ((ArrayList) phoneCallStateHandler2.mListenerList).add(myCallStateListener2);
                                    telephonyManager2.registerTelephonyCallback(phoneCallStateHandler2.mExecutor, myCallStateListener2);
                                }
                            }
                        });
                    } finally {
                    }
                }
            }
        });
    }

    public static boolean isCallOngoingFromState(int i) {
        if (i == 0 || i == 1) {
            return false;
        }
        if (i == 2) {
            return true;
        }
        throw new IllegalStateException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Received unexpected call state from Telephony Manager: "));
    }
}
