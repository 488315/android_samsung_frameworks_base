package com.android.server.soundtrigger;

import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Slog;
import com.android.server.soundtrigger.PhoneCallStateHandler;
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

/* loaded from: classes3.dex */
public class PhoneCallStateHandler {
    public final Callback mCallback;
    public final ExecutorService mExecutor;
    public final SubscriptionManager mSubscriptionManager;
    public final TelephonyManager mTelephonyManager;
    public final Object mLock = new Object();
    public final List mListenerList = new ArrayList();
    public final AtomicBoolean mIsPhoneCallOngoing = new AtomicBoolean(false);

    public interface Callback {
        void onPhoneCallStateChanged(boolean z);
    }

    public PhoneCallStateHandler(SubscriptionManager subscriptionManager, TelephonyManager telephonyManager, Callback callback) {
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        this.mExecutor = newSingleThreadExecutor;
        Objects.requireNonNull(subscriptionManager);
        this.mSubscriptionManager = subscriptionManager;
        Objects.requireNonNull(telephonyManager);
        this.mTelephonyManager = telephonyManager;
        Objects.requireNonNull(callback);
        this.mCallback = callback;
        subscriptionManager.addOnSubscriptionsChangedListener(newSingleThreadExecutor, new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler.1
            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public void onSubscriptionsChanged() {
                PhoneCallStateHandler.this.updateTelephonyListeners();
            }

            public void onAddListenerFailed() {
                Slog.wtf("SoundTriggerPhoneCallStateHandler", "Failed to add a telephony listener");
            }
        });
    }

    public final class MyCallStateListener extends TelephonyCallback implements TelephonyCallback.CallStateListener {
        public final TelephonyManager mTelephonyManagerForSubId;

        public MyCallStateListener(TelephonyManager telephonyManager) {
            this.mTelephonyManagerForSubId = telephonyManager;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$cleanup$0() {
            this.mTelephonyManagerForSubId.unregisterTelephonyCallback(this);
        }

        public void cleanup() {
            PhoneCallStateHandler.this.mExecutor.execute(new Runnable() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$MyCallStateListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PhoneCallStateHandler.MyCallStateListener.this.lambda$cleanup$0();
                }
            });
        }

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public void onCallStateChanged(int i) {
            PhoneCallStateHandler.this.updateCallStatus();
        }
    }

    public final void updateCallStatus() {
        boolean checkCallStatus = checkCallStatus();
        if (this.mIsPhoneCallOngoing.compareAndSet(!checkCallStatus, checkCallStatus)) {
            this.mCallback.onPhoneCallStateChanged(checkCallStatus);
        }
    }

    public final boolean checkCallStatus() {
        List<SubscriptionInfo> activeSubscriptionInfoList = this.mSubscriptionManager.getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList == null) {
            return false;
        }
        return activeSubscriptionInfoList.stream().filter(new Predicate() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$checkCallStatus$0;
                lambda$checkCallStatus$0 = PhoneCallStateHandler.lambda$checkCallStatus$0((SubscriptionInfo) obj);
                return lambda$checkCallStatus$0;
            }
        }).anyMatch(new Predicate() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$checkCallStatus$1;
                lambda$checkCallStatus$1 = PhoneCallStateHandler.this.lambda$checkCallStatus$1((SubscriptionInfo) obj);
                return lambda$checkCallStatus$1;
            }
        });
    }

    public static /* synthetic */ boolean lambda$checkCallStatus$0(SubscriptionInfo subscriptionInfo) {
        return subscriptionInfo.getSubscriptionId() != -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$checkCallStatus$1(SubscriptionInfo subscriptionInfo) {
        return isCallOngoingFromState(this.mTelephonyManager.createForSubscriptionId(subscriptionInfo.getSubscriptionId()).getCallStateForSubscription());
    }

    public final void updateTelephonyListeners() {
        synchronized (this.mLock) {
            Iterator it = this.mListenerList.iterator();
            while (it.hasNext()) {
                ((MyCallStateListener) it.next()).cleanup();
            }
            this.mListenerList.clear();
            List<SubscriptionInfo> activeSubscriptionInfoList = this.mSubscriptionManager.getActiveSubscriptionInfoList();
            if (activeSubscriptionInfoList == null) {
                return;
            }
            activeSubscriptionInfoList.stream().filter(new Predicate() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$updateTelephonyListeners$2;
                    lambda$updateTelephonyListeners$2 = PhoneCallStateHandler.lambda$updateTelephonyListeners$2((SubscriptionInfo) obj);
                    return lambda$updateTelephonyListeners$2;
                }
            }).map(new Function() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    TelephonyManager lambda$updateTelephonyListeners$3;
                    lambda$updateTelephonyListeners$3 = PhoneCallStateHandler.this.lambda$updateTelephonyListeners$3((SubscriptionInfo) obj);
                    return lambda$updateTelephonyListeners$3;
                }
            }).forEach(new Consumer() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneCallStateHandler.this.lambda$updateTelephonyListeners$4((TelephonyManager) obj);
                }
            });
        }
    }

    public static /* synthetic */ boolean lambda$updateTelephonyListeners$2(SubscriptionInfo subscriptionInfo) {
        return subscriptionInfo.getSubscriptionId() != -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ TelephonyManager lambda$updateTelephonyListeners$3(SubscriptionInfo subscriptionInfo) {
        return this.mTelephonyManager.createForSubscriptionId(subscriptionInfo.getSubscriptionId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateTelephonyListeners$4(TelephonyManager telephonyManager) {
        synchronized (this.mLock) {
            MyCallStateListener myCallStateListener = new MyCallStateListener(telephonyManager);
            this.mListenerList.add(myCallStateListener);
            telephonyManager.registerTelephonyCallback(this.mExecutor, myCallStateListener);
        }
    }

    public static boolean isCallOngoingFromState(int i) {
        if (i == 0 || i == 1) {
            return false;
        }
        if (i == 2) {
            return true;
        }
        throw new IllegalStateException("Received unexpected call state from Telephony Manager: " + i);
    }
}
