package com.android.systemui.settings;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.UserSwitchObserver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.UserManager;
import android.util.Log;
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

public final class MultiUserUtilsModule_ProvideUserTrackerFactory implements Provider {
    public final javax.inject.Provider appScopeProvider;
    public final javax.inject.Provider backgroundDispatcherProvider;
    public final javax.inject.Provider contextProvider;
    public final javax.inject.Provider dumpManagerProvider;
    public final javax.inject.Provider featureFlagsProvider;
    public final javax.inject.Provider handlerProvider;
    public final javax.inject.Provider iActivityManagerProvider;
    public final javax.inject.Provider userManagerProvider;

    public MultiUserUtilsModule_ProvideUserTrackerFactory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8) {
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
        if (!userTrackerImpl.initialized) {
            userTrackerImpl.initialized = true;
            userTrackerImpl.setUserIdInternal(currentUser);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
            intentFilter.addAction("android.intent.action.USER_INFO_CHANGED");
            intentFilter.addAction("android.intent.action.PROFILE_ADDED");
            intentFilter.addAction("android.intent.action.PROFILE_REMOVED");
            intentFilter.addAction("android.intent.action.PROFILE_AVAILABLE");
            intentFilter.addAction("android.intent.action.PROFILE_UNAVAILABLE");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_ADDED");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_REMOVED");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNLOCKED");
            userTrackerImpl.context.registerReceiverForAllUsers(userTrackerImpl, intentFilter, null, userTrackerImpl.backgroundHandler);
            userTrackerImpl.iActivityManager.registerUserSwitchObserver(new UserSwitchObserver() { // from class: com.android.systemui.settings.UserTrackerImpl$registerUserSwitchObserver$1
                public final void onBeforeUserSwitching(final int i) {
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
                                @Override // java.lang.Runnable
                                public final void run() {
                                    UserTracker.Callback callback2 = UserTracker.Callback.this;
                                    final CountDownLatch countDownLatch2 = countDownLatch;
                                    Runnable runnable = new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleBeforeUserSwitching$$inlined$notifySubscribers$1.1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            countDownLatch2.countDown();
                                        }
                                    };
                                    callback2.onBeforeUserSwitching(i);
                                    runnable.run();
                                }
                            });
                        } else {
                            countDownLatch.countDown();
                        }
                    }
                    countDownLatch.await();
                }

                public final void onUserSwitchComplete(final int i) {
                    List<DataItem> list;
                    FeatureFlagsClassic featureFlagsClassic = (FeatureFlagsClassic) UserTrackerImpl.this.featureFlagsProvider.get();
                    Flags flags = Flags.INSTANCE;
                    featureFlagsClassic.getClass();
                    final UserTrackerImpl userTrackerImpl2 = UserTrackerImpl.this;
                    userTrackerImpl2.getClass();
                    Assert.isNotMainThread();
                    Log.i("UserTrackerImpl", "Switched to user " + i);
                    synchronized (userTrackerImpl2.callbacks) {
                        list = CollectionsKt___CollectionsKt.toList(userTrackerImpl2.callbacks);
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
                                    new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleUserSwitchComplete$$inlined$notifySubscribers$1.1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            countDownLatch2.countDown();
                                        }
                                    };
                                    callback2.onUserChanged(i, userTrackerImpl2.getUserContext());
                                    callback2.onProfilesChanged(userTrackerImpl2.getUserProfiles());
                                }
                            });
                        } else {
                            countDownLatch.countDown();
                        }
                    }
                }

                public final void onUserSwitching(final int i, IRemoteCallback iRemoteCallback) {
                    List<DataItem> list;
                    FeatureFlagsClassic featureFlagsClassic = (FeatureFlagsClassic) UserTrackerImpl.this.featureFlagsProvider.get();
                    Flags flags = Flags.INSTANCE;
                    featureFlagsClassic.getClass();
                    final UserTrackerImpl userTrackerImpl2 = UserTrackerImpl.this;
                    userTrackerImpl2.getClass();
                    Assert.isNotMainThread();
                    Log.i("UserTrackerImpl", "Switching to user " + i);
                    synchronized (userTrackerImpl2.callbacks) {
                        list = CollectionsKt___CollectionsKt.toList(userTrackerImpl2.callbacks);
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
                                    callback2.onUserChanging(i, userTrackerImpl2.getUserContext(), new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleUserSwitching$$inlined$notifySubscribers$1.1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            countDownLatch2.countDown();
                                        }
                                    });
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
            DumpManager.registerDumpable$default(userTrackerImpl.dumpManager, "UserTrackerImpl", userTrackerImpl);
        }
        return userTrackerImpl;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideUserTracker((Context) this.contextProvider.get(), this.featureFlagsProvider, (UserManager) this.userManagerProvider.get(), (IActivityManager) this.iActivityManagerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (CoroutineScope) this.appScopeProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get(), (Handler) this.handlerProvider.get());
    }
}
