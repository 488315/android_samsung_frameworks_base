package com.android.systemui.screenshot;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ExitTransitionCoordinator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.view.IWindowManager;
import android.view.RemoteAnimationAdapter;
import android.view.ViewGroup;
import android.view.WindowManagerGlobal;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.screenshot.ICrossProfileService;
import com.android.systemui.screenshot.proxy.ScreenshotProxy;
import com.android.systemui.screenshot.proxy.ScreenshotProxyClient;
import com.android.systemui.screenshot.proxy.ScreenshotProxyClient$dismissKeyguard$onDoneBinder$1;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import java.util.function.Function;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class ActionIntentExecutor {
    public final ActivityManagerWrapper activityManagerWrapper;
    public final CoroutineScope applicationScope;
    public final Context context;
    public final DisplayTracker displayTracker;
    public final CoroutineDispatcher mainDispatcher;
    public final ScreenshotProxy screenshotProxy;

    /* renamed from: com.android.systemui.screenshot.ActionIntentExecutor$launchIntent$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ActionIntentExecutor.this.launchIntent(null, null, false, null, null, this);
        }
    }

    /* renamed from: com.android.systemui.screenshot.ActionIntentExecutor$launchIntent$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Intent $intent;
        final /* synthetic */ Ref$ObjectRef<ActivityOptions> $transitionOptions;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Intent intent, Ref$ObjectRef<ActivityOptions> ref$ObjectRef, Continuation continuation) {
            super(2, continuation);
            this.$intent = intent;
            this.$transitionOptions = ref$ObjectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ActionIntentExecutor.this.new AnonymousClass2(this.$intent, this.$transitionOptions, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Context context = ActionIntentExecutor.this.context;
            Intent intent = this.$intent;
            ActivityOptions activityOptions = this.$transitionOptions.element;
            context.startActivity(intent, activityOptions != null ? activityOptions.toBundle() : null);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.screenshot.ActionIntentExecutor$launchIntentAsync$1, reason: invalid class name and case insensitive filesystem */
    final class C10231 extends SuspendLambda implements Function2 {
        final /* synthetic */ Intent $intent;
        final /* synthetic */ ActivityOptions $options;
        final /* synthetic */ boolean $overrideTransition;
        final /* synthetic */ ExitTransitionCoordinator $transitionCoordinator;
        final /* synthetic */ UserHandle $user;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10231(Intent intent, UserHandle userHandle, boolean z, ActivityOptions activityOptions, ExitTransitionCoordinator exitTransitionCoordinator, Continuation continuation) {
            super(2, continuation);
            this.$intent = intent;
            this.$user = userHandle;
            this.$overrideTransition = z;
            this.$options = activityOptions;
            this.$transitionCoordinator = exitTransitionCoordinator;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ActionIntentExecutor.this.new C10231(this.$intent, this.$user, this.$overrideTransition, this.$options, this.$transitionCoordinator, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10231) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ActionIntentExecutor actionIntentExecutor = ActionIntentExecutor.this;
                Intent intent = this.$intent;
                UserHandle userHandle = this.$user;
                boolean z = this.$overrideTransition;
                ActivityOptions activityOptions = this.$options;
                ExitTransitionCoordinator exitTransitionCoordinator = this.$transitionCoordinator;
                this.label = 1;
                if (actionIntentExecutor.launchIntent(intent, userHandle, z, activityOptions, exitTransitionCoordinator, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public ActionIntentExecutor(Context context, ActivityManagerWrapper activityManagerWrapper, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, ScreenshotProxy screenshotProxy, DisplayTracker displayTracker) {
        this.context = context;
        this.activityManagerWrapper = activityManagerWrapper;
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.screenshotProxy = screenshotProxy;
        this.displayTracker = displayTracker;
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x010c, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r4, r5, r2) == r3) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x010f, code lost:
    
        r2 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x016a, code lost:
    
        if (r0 == r3) goto L55;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:57:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object launchIntent(Intent intent, UserHandle userHandle, boolean z, ActivityOptions activityOptions, ExitTransitionCoordinator exitTransitionCoordinator, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        Object objAwaitInternal;
        ActionIntentExecutor actionIntentExecutor;
        boolean z2;
        final Intent intent2;
        ExitTransitionCoordinator exitTransitionCoordinator2;
        UserHandle userHandle2;
        T t;
        ViewGroup decor;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            this.activityManagerWrapper.getClass();
            try {
                ActivityManager.getService().closeSystemDialogs("screenshot");
            } catch (RemoteException e) {
                Log.w("ActivityManagerWrapper", "Failed to close system windows", e);
            }
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = intent;
            anonymousClass1.L$2 = userHandle;
            anonymousClass1.L$3 = activityOptions;
            anonymousClass1.L$4 = exitTransitionCoordinator;
            anonymousClass1.Z$0 = z;
            anonymousClass1.label = 1;
            ScreenshotProxyClient screenshotProxyClient = (ScreenshotProxyClient) this.screenshotProxy;
            screenshotProxyClient.getClass();
            CompletableDeferredImpl completableDeferredImplCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default();
            final ScreenshotProxyClient$dismissKeyguard$onDoneBinder$1 screenshotProxyClient$dismissKeyguard$onDoneBinder$1 = new ScreenshotProxyClient$dismissKeyguard$onDoneBinder$1(completableDeferredImplCompletableDeferred$default);
            if (screenshotProxyClient.proxyConnector.run(new ServiceConnector.VoidJob() { // from class: com.android.systemui.screenshot.proxy.ScreenshotProxyClient$dismissKeyguard$2
                public final void runNoResult(Object obj2) {
                    ((IScreenshotProxy) obj2).dismissKeyguard(screenshotProxyClient$dismissKeyguard$onDoneBinder$1);
                }
            })) {
                objAwaitInternal = completableDeferredImplCompletableDeferred$default.awaitInternal(anonymousClass1);
                if (objAwaitInternal != CoroutineSingletons.COROUTINE_SUSPENDED) {
                    objAwaitInternal = Unit.INSTANCE;
                }
            } else {
                Log.wtf("SystemUiProxy", "Keyguard dismissal request failed");
                objAwaitInternal = Unit.INSTANCE;
            }
            if (objAwaitInternal != coroutineSingletons) {
                actionIntentExecutor = this;
                z2 = z;
                intent2 = intent;
                exitTransitionCoordinator2 = exitTransitionCoordinator;
                userHandle2 = userHandle;
                t = activityOptions;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2 && i2 != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            z2 = anonymousClass1.Z$0;
            ActionIntentExecutor actionIntentExecutor2 = (ActionIntentExecutor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            if (z2) {
                RemoteAnimationAdapter remoteAnimationAdapter = new RemoteAnimationAdapter(ActionIntentExecutorKt.SCREENSHOT_REMOTE_RUNNER, 0L, 0L);
                try {
                    IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
                    if (windowManagerService == null) {
                        throw new IllegalStateException("Required value was null.");
                    }
                    actionIntentExecutor2.displayTracker.getClass();
                    windowManagerService.overridePendingAppTransitionRemote(remoteAnimationAdapter, 0);
                    Unit unit = Unit.INSTANCE;
                } catch (Exception e2) {
                    Boxing.boxInt(Log.e("ActionIntentExecutor", "Error overriding screenshot app transition", e2));
                }
            }
            return Unit.INSTANCE;
        }
        z2 = anonymousClass1.Z$0;
        exitTransitionCoordinator2 = (ExitTransitionCoordinator) anonymousClass1.L$4;
        ActivityOptions activityOptions2 = (ActivityOptions) anonymousClass1.L$3;
        userHandle2 = (UserHandle) anonymousClass1.L$2;
        intent2 = (Intent) anonymousClass1.L$1;
        actionIntentExecutor = (ActionIntentExecutor) anonymousClass1.L$0;
        ResultKt.throwOnFailure(obj);
        t = activityOptions2;
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        if (exitTransitionCoordinator2 != null && (decor = exitTransitionCoordinator2.getDecor()) != null && decor.isAttachedToWindow()) {
            exitTransitionCoordinator2.startExit();
            ref$ObjectRef.element = t;
        }
        if (Intrinsics.areEqual(userHandle2, Process.myUserHandle())) {
            CoroutineDispatcher coroutineDispatcher = actionIntentExecutor.mainDispatcher;
            AnonymousClass2 anonymousClass2 = actionIntentExecutor.new AnonymousClass2(intent2, ref$ObjectRef, null);
            anonymousClass1.L$0 = actionIntentExecutor;
            anonymousClass1.L$1 = null;
            anonymousClass1.L$2 = null;
            anonymousClass1.L$3 = null;
            anonymousClass1.L$4 = null;
            anonymousClass1.Z$0 = z2;
            anonymousClass1.label = 2;
        } else {
            ActivityOptions activityOptions3 = (ActivityOptions) ref$ObjectRef.element;
            final Bundle bundle = activityOptions3 != null ? activityOptions3.toBundle() : null;
            anonymousClass1.L$0 = actionIntentExecutor;
            anonymousClass1.L$1 = null;
            anonymousClass1.L$2 = null;
            anonymousClass1.L$3 = null;
            anonymousClass1.L$4 = null;
            anonymousClass1.Z$0 = z2;
            anonymousClass1.label = 3;
            actionIntentExecutor.getClass();
            ServiceConnector.Impl impl = new ServiceConnector.Impl(actionIntentExecutor.context, new Intent(actionIntentExecutor.context, (Class<?>) ScreenshotCrossProfileService.class), 1073741857, userHandle2.getIdentifier(), new Function() { // from class: com.android.systemui.screenshot.ActionIntentExecutor$getCrossProfileConnector$1
                @Override // java.util.function.Function
                public final Object apply(Object obj2) {
                    IBinder iBinder = (IBinder) obj2;
                    int i3 = ICrossProfileService.Stub.$r8$clinit;
                    if (iBinder == null) {
                        return null;
                    }
                    IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.screenshot.ICrossProfileService");
                    return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICrossProfileService)) ? new ICrossProfileService.Stub.Proxy(iBinder) : (ICrossProfileService) iInterfaceQueryLocalInterface;
                }
            });
            final CompletableDeferredImpl completableDeferredImplCompletableDeferred$default2 = CompletableDeferredKt.CompletableDeferred$default();
            impl.post(new ServiceConnector.VoidJob() { // from class: com.android.systemui.screenshot.ActionIntentExecutor$launchCrossProfileIntent$2
                public final void runNoResult(Object obj2) {
                    intent2.collectExtraIntentKeys();
                    ((ICrossProfileService) obj2).launchIntent(intent2, bundle);
                    ((CompletableDeferredImpl) completableDeferredImplCompletableDeferred$default2).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Unit.INSTANCE);
                }
            });
            Object objAwaitInternal2 = completableDeferredImplCompletableDeferred$default2.awaitInternal(anonymousClass1);
            if (objAwaitInternal2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                objAwaitInternal2 = Unit.INSTANCE;
            }
        }
        if (z2) {
        }
        return Unit.INSTANCE;
    }

    public final void launchIntentAsync(Intent intent, UserHandle userHandle) {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new C10231(intent, userHandle, false, null, null, null), 6);
    }
}
