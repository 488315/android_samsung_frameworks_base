package com.android.server.appfunctions;

import android.app.appfunctions.AppFunctionManagerConfiguration;
import android.app.appsearch.AppSearchManager;
import android.app.appsearch.GlobalSearchSession;
import android.app.appsearch.observer.ObserverSpec;
import android.content.Context;
import android.os.UserHandle;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.infra.AndroidFuture;
import com.android.server.SystemService;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class AppFunctionManagerService extends SystemService {
    public final AppFunctionManagerServiceImpl mServiceImpl;

    public AppFunctionManagerService(Context context) {
        super(context);
        this.mServiceImpl =
                new AppFunctionManagerServiceImpl(
                        context,
                        new RemoteServiceCallerImpl(
                                context,
                                new AppFunctionManagerServiceImpl$$ExternalSyntheticLambda0(0),
                                AppFunctionExecutors.THREAD_POOL_EXECUTOR),
                        new CallerValidatorImpl(context),
                        new ServiceHelperImpl(context),
                        new ServiceConfigImpl());
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        if (AppFunctionManagerConfiguration.isSupported(getContext())) {
            publishBinderService("app_function", this.mServiceImpl);
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
        this.mServiceImpl.getClass();
        Objects.requireNonNull(targetUser);
        UserHandle userHandle = targetUser.getUserHandle();
        synchronized (MetadataSyncPerUser.sLock) {
            try {
                SparseArray sparseArray = MetadataSyncPerUser.sPerUserMetadataSyncAdapter;
                MetadataSyncAdapter metadataSyncAdapter =
                        (MetadataSyncAdapter) sparseArray.get(userHandle.getIdentifier(), null);
                if (metadataSyncAdapter != null) {
                    metadataSyncAdapter.mExecutor.shutdown();
                    sparseArray.remove(userHandle.getIdentifier());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocked(SystemService.TargetUser targetUser) {
        AppFunctionManagerServiceImpl appFunctionManagerServiceImpl = this.mServiceImpl;
        appFunctionManagerServiceImpl.getClass();
        Objects.requireNonNull(targetUser);
        AppSearchManager appSearchManager =
                (AppSearchManager)
                        appFunctionManagerServiceImpl
                                .mContext
                                .createContextAsUser(targetUser.getUserHandle(), 0)
                                .getSystemService(AppSearchManager.class);
        if (appSearchManager == null) {
            Slog.d(
                    "AppFunctionManagerServiceImpl",
                    "AppSearch Manager not found for user: " + targetUser.getUserIdentifier());
        } else {
            final Executor executor = AppFunctionExecutors.THREAD_POOL_EXECUTOR;
            final FutureGlobalSearchSession futureGlobalSearchSession =
                    new FutureGlobalSearchSession(appSearchManager, executor);
            final AppFunctionManagerServiceImpl.AppFunctionMetadataObserver
                    appFunctionMetadataObserver =
                            new AppFunctionManagerServiceImpl.AppFunctionMetadataObserver(
                                    targetUser.getUserHandle(),
                                    appFunctionManagerServiceImpl.mContext.createContextAsUser(
                                            targetUser.getUserHandle(), 0),
                                    appFunctionManagerServiceImpl.mAppFunctionAgentPolicyManager);
            final ObserverSpec build = new ObserverSpec.Builder().build();
            futureGlobalSearchSession
                    .mSettableSessionFuture
                    .thenApply(new FutureGlobalSearchSession$$ExternalSyntheticLambda4())
                    .thenCompose(
                            new Function() { // from class:
                                             // com.android.server.appfunctions.FutureGlobalSearchSession$$ExternalSyntheticLambda0
                                public final /* synthetic */ String f$0 = "android";

                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    try {
                                        ((GlobalSearchSession) obj)
                                                .registerObserverCallback(
                                                        this.f$0,
                                                        build,
                                                        executor,
                                                        appFunctionMetadataObserver);
                                        return AndroidFuture.completedFuture((Object) null);
                                    } catch (
                                            android.app.appsearch.exceptions.AppSearchException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            })
                    .whenComplete(
                            new BiConsumer() { // from class:
                                               // com.android.server.appfunctions.AppFunctionManagerServiceImpl$$ExternalSyntheticLambda4
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    FutureGlobalSearchSession futureGlobalSearchSession2 =
                                            FutureGlobalSearchSession.this;
                                    Throwable th = (Throwable) obj2;
                                    if (th != null) {
                                        Slog.e(
                                                "AppFunctionManagerServiceImpl",
                                                "Failed to register observer: ",
                                                th);
                                    }
                                    futureGlobalSearchSession2.close();
                                }
                            });
        }
        appFunctionManagerServiceImpl.trySyncRuntimeMetadata(targetUser, false);
    }
}
