package com.android.systemui.screenshot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.UserHandle;
import android.util.Log;
import android.view.IWindowManager;
import android.view.RemoteAnimationAdapter;
import android.view.WindowManagerGlobal;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.screenshot.ICrossProfileService;
import com.android.systemui.screenshot.IScreenshotProxy;
import com.android.systemui.settings.DisplayTracker;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.function.Function;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.JobSupportKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ActionIntentExecutor {
    public final CoroutineScope applicationScope;
    public final Context context;
    public final DisplayTracker displayTracker;
    public final CoroutineDispatcher mainDispatcher;
    public final ServiceConnector proxyConnector;

    public ActionIntentExecutor(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Context context, DisplayTracker displayTracker) {
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.context = context;
        this.displayTracker = displayTracker;
        this.proxyConnector = new ServiceConnector.Impl(context, new Intent(context, (Class<?>) ScreenshotProxyService.class), 1073741857, context.getUserId(), new Function() { // from class: com.android.systemui.screenshot.ActionIntentExecutor$proxyConnector$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                IBinder iBinder = (IBinder) obj;
                int i = IScreenshotProxy.Stub.$r8$clinit;
                if (iBinder == null) {
                    return null;
                }
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.screenshot.IScreenshotProxy");
                return (queryLocalInterface == null || !(queryLocalInterface instanceof IScreenshotProxy)) ? new IScreenshotProxy.Stub.Proxy(iBinder) : (IScreenshotProxy) queryLocalInterface;
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object launchIntent(Intent intent, Bundle bundle, int i, boolean z, Continuation continuation) {
        ActionIntentExecutor$launchIntent$1 actionIntentExecutor$launchIntent$1;
        int i2;
        final Intent intent2;
        final Bundle bundle2;
        int i3;
        ActionIntentExecutor actionIntentExecutor;
        boolean z2;
        ActionIntentExecutor actionIntentExecutor2;
        if (continuation instanceof ActionIntentExecutor$launchIntent$1) {
            actionIntentExecutor$launchIntent$1 = (ActionIntentExecutor$launchIntent$1) continuation;
            int i4 = actionIntentExecutor$launchIntent$1.label;
            if ((i4 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                actionIntentExecutor$launchIntent$1.label = i4 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = actionIntentExecutor$launchIntent$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i2 = actionIntentExecutor$launchIntent$1.label;
                if (i2 != 0) {
                    ResultKt.throwOnFailure(obj);
                    actionIntentExecutor$launchIntent$1.L$0 = this;
                    intent2 = intent;
                    actionIntentExecutor$launchIntent$1.L$1 = intent2;
                    bundle2 = bundle;
                    actionIntentExecutor$launchIntent$1.L$2 = bundle2;
                    i3 = i;
                    actionIntentExecutor$launchIntent$1.I$0 = i3;
                    actionIntentExecutor$launchIntent$1.Z$0 = z;
                    actionIntentExecutor$launchIntent$1.label = 1;
                    CompletableDeferredImpl completableDeferredImpl = new CompletableDeferredImpl(null);
                    final ActionIntentExecutor$dismissKeyguard$onDoneBinder$1 actionIntentExecutor$dismissKeyguard$onDoneBinder$1 = new ActionIntentExecutor$dismissKeyguard$onDoneBinder$1(completableDeferredImpl);
                    this.proxyConnector.post(new ServiceConnector.VoidJob() { // from class: com.android.systemui.screenshot.ActionIntentExecutor$dismissKeyguard$2
                        public final void runNoResult(Object obj2) {
                            ((IScreenshotProxy) obj2).dismissKeyguard(ActionIntentExecutor$dismissKeyguard$onDoneBinder$1.this);
                        }
                    });
                    Object await = completableDeferredImpl.await(actionIntentExecutor$launchIntent$1);
                    if (await != coroutineSingletons) {
                        await = Unit.INSTANCE;
                    }
                    if (await == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    actionIntentExecutor = this;
                    z2 = z;
                } else {
                    if (i2 != 1) {
                        if (i2 != 2 && i2 != 3) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        z2 = actionIntentExecutor$launchIntent$1.Z$0;
                        actionIntentExecutor2 = (ActionIntentExecutor) actionIntentExecutor$launchIntent$1.L$0;
                        ResultKt.throwOnFailure(obj);
                        if (z2) {
                            RemoteAnimationAdapter remoteAnimationAdapter = new RemoteAnimationAdapter(ActionIntentExecutorKt.SCREENSHOT_REMOTE_RUNNER, 0L, 0L);
                            try {
                                IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
                                actionIntentExecutor2.displayTracker.getClass();
                                windowManagerService.overridePendingAppTransitionRemote(remoteAnimationAdapter, 0);
                            } catch (Exception e) {
                                Log.e("ActionIntentExecutor", "Error overriding screenshot app transition", e);
                            }
                        }
                        return Unit.INSTANCE;
                    }
                    z2 = actionIntentExecutor$launchIntent$1.Z$0;
                    int i5 = actionIntentExecutor$launchIntent$1.I$0;
                    Bundle bundle3 = (Bundle) actionIntentExecutor$launchIntent$1.L$2;
                    Intent intent3 = (Intent) actionIntentExecutor$launchIntent$1.L$1;
                    actionIntentExecutor = (ActionIntentExecutor) actionIntentExecutor$launchIntent$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    intent2 = intent3;
                    i3 = i5;
                    bundle2 = bundle3;
                }
                if (i3 != UserHandle.myUserId()) {
                    CoroutineDispatcher coroutineDispatcher = actionIntentExecutor.mainDispatcher;
                    ActionIntentExecutor$launchIntent$2 actionIntentExecutor$launchIntent$2 = new ActionIntentExecutor$launchIntent$2(actionIntentExecutor, intent2, bundle2, null);
                    actionIntentExecutor$launchIntent$1.L$0 = actionIntentExecutor;
                    actionIntentExecutor$launchIntent$1.L$1 = null;
                    actionIntentExecutor$launchIntent$1.L$2 = null;
                    actionIntentExecutor$launchIntent$1.Z$0 = z2;
                    actionIntentExecutor$launchIntent$1.label = 2;
                    if (BuildersKt.withContext(coroutineDispatcher, actionIntentExecutor$launchIntent$2, actionIntentExecutor$launchIntent$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    actionIntentExecutor$launchIntent$1.L$0 = actionIntentExecutor;
                    actionIntentExecutor$launchIntent$1.L$1 = null;
                    actionIntentExecutor$launchIntent$1.L$2 = null;
                    actionIntentExecutor$launchIntent$1.Z$0 = z2;
                    actionIntentExecutor$launchIntent$1.label = 3;
                    actionIntentExecutor.getClass();
                    ServiceConnector.Impl impl = new ServiceConnector.Impl(actionIntentExecutor.context, new Intent(actionIntentExecutor.context, (Class<?>) ScreenshotCrossProfileService.class), 1073741857, i3, new Function() { // from class: com.android.systemui.screenshot.ActionIntentExecutor$getCrossProfileConnector$1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj2) {
                            IBinder iBinder = (IBinder) obj2;
                            int i6 = ICrossProfileService.Stub.$r8$clinit;
                            if (iBinder == null) {
                                return null;
                            }
                            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.screenshot.ICrossProfileService");
                            return (queryLocalInterface == null || !(queryLocalInterface instanceof ICrossProfileService)) ? new ICrossProfileService.Stub.Proxy(iBinder) : (ICrossProfileService) queryLocalInterface;
                        }
                    });
                    final CompletableDeferredImpl completableDeferredImpl2 = new CompletableDeferredImpl(null);
                    impl.post(new ServiceConnector.VoidJob() { // from class: com.android.systemui.screenshot.ActionIntentExecutor$launchCrossProfileIntent$2
                        public final void runNoResult(Object obj2) {
                            Object tryMakeCompleting;
                            ((ICrossProfileService) obj2).launchIntent(intent2, bundle2);
                            CompletableDeferred completableDeferred = completableDeferredImpl2;
                            Unit unit = Unit.INSTANCE;
                            CompletableDeferredImpl completableDeferredImpl3 = (CompletableDeferredImpl) completableDeferred;
                            do {
                                tryMakeCompleting = completableDeferredImpl3.tryMakeCompleting(completableDeferredImpl3.m293x8adbf455(), unit);
                                if (tryMakeCompleting == JobSupportKt.COMPLETING_ALREADY || tryMakeCompleting == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
                                    return;
                                }
                            } while (tryMakeCompleting == JobSupportKt.COMPLETING_RETRY);
                        }
                    });
                    Object await2 = completableDeferredImpl2.await(actionIntentExecutor$launchIntent$1);
                    if (await2 != coroutineSingletons) {
                        await2 = Unit.INSTANCE;
                    }
                    if (await2 == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
                actionIntentExecutor2 = actionIntentExecutor;
                if (z2) {
                }
                return Unit.INSTANCE;
            }
        }
        actionIntentExecutor$launchIntent$1 = new ActionIntentExecutor$launchIntent$1(this, continuation);
        Object obj2 = actionIntentExecutor$launchIntent$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i2 = actionIntentExecutor$launchIntent$1.label;
        if (i2 != 0) {
        }
        if (i3 != UserHandle.myUserId()) {
        }
        actionIntentExecutor2 = actionIntentExecutor;
        if (z2) {
        }
        return Unit.INSTANCE;
    }

    public final void launchIntentAsync(Intent intent, Bundle bundle, int i, boolean z) {
        BuildersKt.launch$default(this.applicationScope, null, null, new ActionIntentExecutor$launchIntentAsync$1(this, intent, bundle, i, z, null), 3);
    }
}
