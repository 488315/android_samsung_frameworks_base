package com.android.systemui.screenshot;

import android.app.IActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Process;
import android.os.UserManager;
import android.util.Log;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.SystemUIService;
import com.android.systemui.screenshot.IScreenshotProxy;
import com.android.systemui.screenshot.ScreenshotPolicy;
import com.android.systemui.settings.DisplayTracker;
import java.util.function.BiConsumer;
import java.util.function.Function;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

public final class ScreenshotPolicyImpl implements ScreenshotPolicy {
    public final IActivityTaskManager atmService;
    public final CoroutineDispatcher bgDispatcher;
    public final ServiceConnector proxyConnector;
    public final ScreenshotPolicy.DisplayContentInfo systemUiContent;
    public final UserManager userMgr;

    public ScreenshotPolicyImpl(Context context, UserManager userManager, IActivityTaskManager iActivityTaskManager, CoroutineDispatcher coroutineDispatcher, DisplayTracker displayTracker) {
        this.userMgr = userManager;
        this.atmService = iActivityTaskManager;
        this.bgDispatcher = coroutineDispatcher;
        this.proxyConnector = new ServiceConnector.Impl(context, new Intent(context, (Class<?>) ScreenshotProxyService.class), 1073741857, context.getUserId(), new Function() { // from class: com.android.systemui.screenshot.ScreenshotPolicyImpl$proxyConnector$1
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
        this.systemUiContent = new ScreenshotPolicy.DisplayContentInfo(new ComponentName(context, (Class<?>) SystemUIService.class), new Rect(), Process.myUserHandle(), -1);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object findPrimaryContent$suspendImpl(com.android.systemui.screenshot.ScreenshotPolicyImpl r5, int r6, kotlin.coroutines.Continuation r7) {
        /*
            boolean r0 = r7 instanceof com.android.systemui.screenshot.ScreenshotPolicyImpl$findPrimaryContent$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.screenshot.ScreenshotPolicyImpl$findPrimaryContent$1 r0 = (com.android.systemui.screenshot.ScreenshotPolicyImpl$findPrimaryContent$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.screenshot.ScreenshotPolicyImpl$findPrimaryContent$1 r0 = new com.android.systemui.screenshot.ScreenshotPolicyImpl$findPrimaryContent$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L40
            if (r2 == r4) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r5 = r0.L$0
            com.android.systemui.screenshot.ScreenshotPolicyImpl r5 = (com.android.systemui.screenshot.ScreenshotPolicyImpl) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L66
        L2e:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L36:
            int r6 = r0.I$0
            java.lang.Object r5 = r0.L$0
            com.android.systemui.screenshot.ScreenshotPolicyImpl r5 = (com.android.systemui.screenshot.ScreenshotPolicyImpl) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L50
        L40:
            kotlin.ResultKt.throwOnFailure(r7)
            r0.L$0 = r5
            r0.I$0 = r6
            r0.label = r4
            java.lang.Object r7 = r5.isNotificationShadeExpanded(r0)
            if (r7 != r1) goto L50
            return r1
        L50:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L5b
            com.android.systemui.screenshot.ScreenshotPolicy$DisplayContentInfo r5 = r5.systemUiContent
            return r5
        L5b:
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r7 = r5.getAllRootTaskInfosOnDisplay(r6, r0)
            if (r7 != r1) goto L66
            return r1
        L66:
            java.util.List r7 = (java.util.List) r7
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            java.util.Iterator r6 = r7.iterator()
        L6e:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto La0
            java.lang.Object r7 = r6.next()
            r0 = r7
            android.app.ActivityTaskManager$RootTaskInfo r0 = (android.app.ActivityTaskManager.RootTaskInfo) r0
            r5.getClass()
            int r1 = r0.getWindowingMode()
            if (r1 == r3) goto L6e
            boolean r1 = r0.isVisible
            if (r1 == 0) goto L6e
            boolean r1 = r0.isRunning
            if (r1 == 0) goto L6e
            int r1 = r0.numActivities
            if (r1 <= 0) goto L6e
            android.content.ComponentName r1 = r0.topActivity
            if (r1 == 0) goto L6e
            int[] r0 = r0.childTaskIds
            int r0 = r0.length
            if (r0 != 0) goto L9b
            r0 = r4
            goto L9c
        L9b:
            r0 = 0
        L9c:
            r0 = r0 ^ r4
            if (r0 == 0) goto L6e
            goto La1
        La0:
            r7 = 0
        La1:
            android.app.ActivityTaskManager$RootTaskInfo r7 = (android.app.ActivityTaskManager.RootTaskInfo) r7
            if (r7 != 0) goto La8
            com.android.systemui.screenshot.ScreenshotPolicy$DisplayContentInfo r5 = r5.systemUiContent
            return r5
        La8:
            com.android.systemui.screenshot.ScreenshotPolicy$DisplayContentInfo r5 = com.android.systemui.screenshot.ScreenshotPolicyImplKt.toDisplayContentInfo(r7)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.ScreenshotPolicyImpl.findPrimaryContent$suspendImpl(com.android.systemui.screenshot.ScreenshotPolicyImpl, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ Object getAllRootTaskInfosOnDisplay$suspendImpl(ScreenshotPolicyImpl screenshotPolicyImpl, int i, Continuation continuation) {
        return BuildersKt.withContext(screenshotPolicyImpl.bgDispatcher, new ScreenshotPolicyImpl$getAllRootTaskInfosOnDisplay$2(screenshotPolicyImpl, i, null), continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object isManagedProfile$suspendImpl(com.android.systemui.screenshot.ScreenshotPolicyImpl r5, int r6, kotlin.coroutines.Continuation r7) {
        /*
            boolean r0 = r7 instanceof com.android.systemui.screenshot.ScreenshotPolicyImpl$isManagedProfile$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.screenshot.ScreenshotPolicyImpl$isManagedProfile$1 r0 = (com.android.systemui.screenshot.ScreenshotPolicyImpl$isManagedProfile$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.screenshot.ScreenshotPolicyImpl$isManagedProfile$1 r0 = new com.android.systemui.screenshot.ScreenshotPolicyImpl$isManagedProfile$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r7)
            goto L43
        L27:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2f:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.CoroutineDispatcher r7 = r5.bgDispatcher
            com.android.systemui.screenshot.ScreenshotPolicyImpl$isManagedProfile$managed$1 r2 = new com.android.systemui.screenshot.ScreenshotPolicyImpl$isManagedProfile$managed$1
            r4 = 0
            r2.<init>(r5, r6, r4)
            r0.label = r3
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r2, r0)
            if (r7 != r1) goto L43
            return r1
        L43:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r5 = r7.booleanValue()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "isManagedProfile: "
            r6.<init>(r7)
            r6.append(r5)
            java.lang.String r6 = r6.toString()
            java.lang.String r7 = "ScreenshotPolicyImpl"
            android.util.Log.d(r7, r6)
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.ScreenshotPolicyImpl.isManagedProfile$suspendImpl(com.android.systemui.screenshot.ScreenshotPolicyImpl, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static Object isNotificationShadeExpanded$suspendImpl(ScreenshotPolicyImpl screenshotPolicyImpl, Continuation continuation) {
        final SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation));
        screenshotPolicyImpl.proxyConnector.postForResult(new ServiceConnector.Job() { // from class: com.android.systemui.screenshot.ScreenshotPolicyImpl$isNotificationShadeExpanded$2$1
            public final Object run(Object obj) {
                return Boolean.valueOf(((IScreenshotProxy) obj).isNotificationShadeExpanded());
            }
        }).whenComplete(new BiConsumer() { // from class: com.android.systemui.screenshot.ScreenshotPolicyImpl$isNotificationShadeExpanded$2$2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Boolean bool = (Boolean) obj;
                Throwable th = (Throwable) obj2;
                if (th != null) {
                    Log.e("ScreenshotPolicyImpl", "isNotificationShadeExpanded", th);
                }
                Continuation continuation2 = Continuation.this;
                int i = Result.$r8$clinit;
                continuation2.resumeWith(Boolean.valueOf(bool == null ? false : bool.booleanValue()));
            }
        });
        Object orThrow = safeContinuation.getOrThrow();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return orThrow;
    }

    public Object getAllRootTaskInfosOnDisplay(int i, Continuation continuation) {
        return getAllRootTaskInfosOnDisplay$suspendImpl(this, i, continuation);
    }

    public Object isNotificationShadeExpanded(Continuation continuation) {
        return isNotificationShadeExpanded$suspendImpl(this, continuation);
    }

    public static /* synthetic */ void getSystemUiContent$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }
}
