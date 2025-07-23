package com.android.systemui.settings;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.UserSwitchObserver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.Trace;
import android.os.UserManager;
import android.util.Log;
import com.android.app.tracing.TraceUtilsKt;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.Flags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.Assert;
import dagger.internal.Provider;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiUserUtilsModule_ProvideUserTrackerFactory implements Provider {
    public final Provider appScopeProvider;
    public final Provider backgroundDispatcherProvider;
    public final Provider contextProvider;
    public final Provider dumpManagerProvider;
    public final Provider featureFlagsProvider;
    public final Provider handlerProvider;
    public final Provider iActivityManagerProvider;
    public final Provider userManagerProvider;

    public MultiUserUtilsModule_ProvideUserTrackerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.featureFlagsProvider = provider2;
        this.userManagerProvider = provider3;
        this.iActivityManagerProvider = provider4;
        this.dumpManagerProvider = provider5;
        this.appScopeProvider = provider6;
        this.backgroundDispatcherProvider = provider7;
        this.handlerProvider = provider8;
    }

    public static UserTrackerImpl provideUserTracker(Context context, javax.inject.Provider provider, UserManager userManager, IActivityManager iActivityManager, DumpManager dumpManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Handler handler) {
        int currentUser = ActivityManager.getCurrentUser();
        final UserTrackerImpl userTrackerImpl = new UserTrackerImpl(context, provider, userManager, iActivityManager, dumpManager, coroutineScope, coroutineDispatcher, handler);
        if (userTrackerImpl.initialized) {
            return userTrackerImpl;
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(currentUser, "Starting user: ", "UserTrackerImpl");
        userTrackerImpl.initialized = true;
        userTrackerImpl.setUserIdInternal(currentUser);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter.addAction("android.intent.action.USER_INFO_CHANGED");
        intentFilter.addAction("android.intent.action.PROFILE_ADDED");
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter, "android.intent.action.PROFILE_REMOVED", "android.intent.action.PROFILE_AVAILABLE", "android.intent.action.PROFILE_UNAVAILABLE", "android.intent.action.MANAGED_PROFILE_AVAILABLE");
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter, "android.intent.action.MANAGED_PROFILE_UNAVAILABLE", "android.intent.action.MANAGED_PROFILE_ADDED", "android.intent.action.MANAGED_PROFILE_REMOVED", "android.intent.action.MANAGED_PROFILE_UNLOCKED");
        userTrackerImpl.context.registerReceiverForAllUsers(userTrackerImpl, intentFilter, null, userTrackerImpl.backgroundHandler);
        userTrackerImpl.iActivityManager.registerUserSwitchObserver(new UserSwitchObserver() { // from class: com.android.systemui.settings.UserTrackerImpl$registerUserSwitchObserver$1
            public final void onBeforeUserSwitching(final int i, IRemoteCallback iRemoteCallback) {
                List<DataItem> list;
                UserTrackerImpl userTrackerImpl2 = UserTrackerImpl.this;
                userTrackerImpl2.setUserIdInternal(i);
                synchronized (userTrackerImpl2.callbacks) {
                    list = CollectionsKt___CollectionsKt.toList(userTrackerImpl2.callbacks);
                }
                final CountDownLatch countDownLatch = new CountDownLatch(list.size());
                for (DataItem dataItem : list) {
                    final UserTracker.Callback callback = (UserTracker.Callback) dataItem.callback.get();
                    if (callback != null) {
                        dataItem.executor.execute(new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleBeforeUserSwitching$$inlined$notifySubscribers$1

                            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                            /* renamed from: com.android.systemui.settings.UserTrackerImpl$handleBeforeUserSwitching$$inlined$notifySubscribers$1$1, reason: invalid class name */
                            public final class AnonymousClass1 implements Runnable {
                                public final /* synthetic */ CountDownLatch $latch;

                                public AnonymousClass1(CountDownLatch countDownLatch) {
                                    this.$latch = countDownLatch;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.$latch.countDown();
                                }
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                UserTracker.Callback callback2 = UserTracker.Callback.this;
                                CountDownLatch countDownLatch2 = countDownLatch;
                                boolean isEnabled = Trace.isEnabled();
                                if (isEnabled) {
                                    TraceUtilsKt.beginSlice("UserTrackerImpl::" + callback2);
                                }
                                try {
                                    callback2.onBeforeUserSwitching(i, new AnonymousClass1(countDownLatch2));
                                } finally {
                                    if (isEnabled) {
                                        TraceUtilsKt.endSlice();
                                    }
                                }
                            }
                        });
                    } else {
                        countDownLatch.countDown();
                    }
                }
                countDownLatch.await();
                if (iRemoteCallback != null) {
                    iRemoteCallback.sendResult((Bundle) null);
                }
            }

            public final void onUserSwitchComplete(final int i) {
                List<DataItem> list;
                UserTrackerImpl userTrackerImpl2 = UserTrackerImpl.this;
                userTrackerImpl2.isUserSwitching = false;
                FeatureFlagsClassic featureFlagsClassic = (FeatureFlagsClassic) userTrackerImpl2.featureFlagsProvider.get();
                Flags flags = Flags.INSTANCE;
                featureFlagsClassic.getClass();
                final UserTrackerImpl userTrackerImpl3 = UserTrackerImpl.this;
                userTrackerImpl3.getClass();
                Assert.isNotMainThread();
                Log.i("UserTrackerImpl", "Switched to user " + i);
                synchronized (userTrackerImpl3.callbacks) {
                    list = CollectionsKt___CollectionsKt.toList(userTrackerImpl3.callbacks);
                }
                final CountDownLatch countDownLatch = new CountDownLatch(list.size());
                for (DataItem dataItem : list) {
                    final UserTracker.Callback callback = (UserTracker.Callback) dataItem.callback.get();
                    if (callback != null) {
                        dataItem.executor.execute(new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleUserSwitchComplete$$inlined$notifySubscribers$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                UserTracker.Callback callback2 = UserTracker.Callback.this;
                                final CountDownLatch countDownLatch2 = countDownLatch;
                                boolean isEnabled = Trace.isEnabled();
                                if (isEnabled) {
                                    TraceUtilsKt.beginSlice("UserTrackerImpl::" + callback2);
                                }
                                try {
                                    new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleUserSwitchComplete$$inlined$notifySubscribers$1.1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            countDownLatch2.countDown();
                                        }
                                    };
                                    callback2.onUserChanged(i, userTrackerImpl3.getUserContext());
                                    callback2.onProfilesChanged(userTrackerImpl3.getUserProfiles());
                                } finally {
                                    if (isEnabled) {
                                        TraceUtilsKt.endSlice();
                                    }
                                }
                            }
                        });
                    } else {
                        countDownLatch.countDown();
                    }
                }
            }

            public final void onUserSwitching(final int i, IRemoteCallback iRemoteCallback) {
                List<DataItem> list;
                UserTrackerImpl userTrackerImpl2 = UserTrackerImpl.this;
                userTrackerImpl2.isUserSwitching = true;
                FeatureFlagsClassic featureFlagsClassic = (FeatureFlagsClassic) userTrackerImpl2.featureFlagsProvider.get();
                Flags flags = Flags.INSTANCE;
                featureFlagsClassic.getClass();
                final UserTrackerImpl userTrackerImpl3 = UserTrackerImpl.this;
                userTrackerImpl3.getClass();
                Assert.isNotMainThread();
                Log.i("UserTrackerImpl", "Switching to user " + i);
                synchronized (userTrackerImpl3.callbacks) {
                    list = CollectionsKt___CollectionsKt.toList(userTrackerImpl3.callbacks);
                }
                final CountDownLatch countDownLatch = new CountDownLatch(list.size());
                for (DataItem dataItem : list) {
                    final UserTracker.Callback callback = (UserTracker.Callback) dataItem.callback.get();
                    if (callback != null) {
                        dataItem.executor.execute(new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleUserSwitching$$inlined$notifySubscribers$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                UserTracker.Callback callback2 = UserTracker.Callback.this;
                                final CountDownLatch countDownLatch2 = countDownLatch;
                                boolean isEnabled = Trace.isEnabled();
                                if (isEnabled) {
                                    TraceUtilsKt.beginSlice("UserTrackerImpl::" + callback2);
                                }
                                try {
                                    callback2.onUserChanging(i, userTrackerImpl3.getUserContext(), new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleUserSwitching$$inlined$notifySubscribers$1.1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            countDownLatch2.countDown();
                                        }
                                    });
                                } finally {
                                    if (isEnabled) {
                                        TraceUtilsKt.endSlice();
                                    }
                                }
                            }
                        });
                    } else {
                        countDownLatch.countDown();
                    }
                }
                countDownLatch.await();
                if (iRemoteCallback != null) {
                    iRemoteCallback.sendResult((Bundle) null);
                }
            }
        }, "UserTrackerImpl");
        userTrackerImpl.dumpManager.registerNormalDumpable("UserTrackerImpl", userTrackerImpl);
        return userTrackerImpl;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideUserTracker((Context) this.contextProvider.get(), this.featureFlagsProvider, (UserManager) this.userManagerProvider.get(), (IActivityManager) this.iActivityManagerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (CoroutineScope) this.appScopeProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get(), (Handler) this.handlerProvider.get());
    }
}
