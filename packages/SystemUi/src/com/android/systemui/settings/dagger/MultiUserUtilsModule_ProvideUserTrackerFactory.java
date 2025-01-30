package com.android.systemui.settings.dagger;

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
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.Assert;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javax.inject.Provider;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MultiUserUtilsModule_ProvideUserTrackerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider dumpManagerProvider;
    public final Provider handlerProvider;
    public final Provider iActivityManagerProvider;
    public final Provider userManagerProvider;

    public MultiUserUtilsModule_ProvideUserTrackerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.userManagerProvider = provider2;
        this.iActivityManagerProvider = provider3;
        this.dumpManagerProvider = provider4;
        this.handlerProvider = provider5;
    }

    public static UserTrackerImpl provideUserTracker(Context context, UserManager userManager, IActivityManager iActivityManager, DumpManager dumpManager, Handler handler) {
        int currentUser = ActivityManager.getCurrentUser();
        final UserTrackerImpl userTrackerImpl = new UserTrackerImpl(context, userManager, iActivityManager, dumpManager, handler);
        if (!userTrackerImpl.initialized) {
            userTrackerImpl.initialized = true;
            userTrackerImpl.setUserIdInternal(currentUser);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_INFO_CHANGED");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_ADDED");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_REMOVED");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNLOCKED");
            userTrackerImpl.context.registerReceiverForAllUsers(userTrackerImpl, intentFilter, null, userTrackerImpl.backgroundHandler);
            userTrackerImpl.iActivityManager.registerUserSwitchObserver(new UserSwitchObserver() { // from class: com.android.systemui.settings.UserTrackerImpl$registerUserSwitchObserver$1
                public final void onBeforeUserSwitching(int i) {
                    UserTrackerImpl userTrackerImpl2 = UserTrackerImpl.this;
                    userTrackerImpl2.getClass();
                    Assert.isNotMainThread();
                    userTrackerImpl2.setUserIdInternal(i);
                }

                public final void onUserSwitchComplete(final int i) {
                    List<DataItem> list;
                    final UserTrackerImpl userTrackerImpl2 = UserTrackerImpl.this;
                    userTrackerImpl2.getClass();
                    Assert.isNotMainThread();
                    Log.i("UserTrackerImpl", "Switched to user " + i);
                    synchronized (userTrackerImpl2.callbacks) {
                        list = CollectionsKt___CollectionsKt.toList(userTrackerImpl2.callbacks);
                    }
                    for (final DataItem dataItem : list) {
                        if (dataItem.callback.get() != null) {
                            dataItem.executor.execute(new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleUserSwitchComplete$$inlined$notifySubscribers$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    UserTracker.Callback callback = (UserTracker.Callback) DataItem.this.callback.get();
                                    if (callback != null) {
                                        callback.onUserChanged(i, userTrackerImpl2.getUserContext());
                                        callback.onProfilesChanged(userTrackerImpl2.getUserProfiles());
                                    }
                                }
                            });
                        }
                    }
                }

                public final void onUserSwitching(int i, IRemoteCallback iRemoteCallback) {
                    List<DataItem> list;
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
                            dataItem.executor.execute(new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleUserSwitching$1$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    UserTracker.Callback.this.onUserChanging(userTrackerImpl2.getUserId(), userTrackerImpl2.getUserContext(), countDownLatch);
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
        return provideUserTracker((Context) this.contextProvider.get(), (UserManager) this.userManagerProvider.get(), (IActivityManager) this.iActivityManagerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (Handler) this.handlerProvider.get());
    }
}
