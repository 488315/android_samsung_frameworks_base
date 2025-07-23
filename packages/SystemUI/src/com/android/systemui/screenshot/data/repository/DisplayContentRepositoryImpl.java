package com.android.systemui.screenshot.data.repository;

import android.app.IActivityTaskManager;
import com.android.systemui.screenshot.proxy.ScreenshotProxy;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DisplayContentRepositoryImpl implements DisplayContentRepository {
    public final IActivityTaskManager atmService;
    public final CoroutineDispatcher background;
    public final ScreenshotProxy screenshotProxy;

    public DisplayContentRepositoryImpl(IActivityTaskManager iActivityTaskManager, ScreenshotProxy screenshotProxy, CoroutineDispatcher coroutineDispatcher) {
        this.atmService = iActivityTaskManager;
        this.screenshotProxy = screenshotProxy;
        this.background = coroutineDispatcher;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$toDisplayTasksModel(com.android.systemui.screenshot.data.repository.DisplayContentRepositoryImpl r4, int r5, java.util.List r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r4.getClass()
            boolean r0 = r7 instanceof com.android.systemui.screenshot.data.repository.DisplayContentRepositoryImpl$toDisplayTasksModel$1
            if (r0 == 0) goto L16
            r0 = r7
            com.android.systemui.screenshot.data.repository.DisplayContentRepositoryImpl$toDisplayTasksModel$1 r0 = (com.android.systemui.screenshot.data.repository.DisplayContentRepositoryImpl$toDisplayTasksModel$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.screenshot.data.repository.DisplayContentRepositoryImpl$toDisplayTasksModel$1 r0 = new com.android.systemui.screenshot.data.repository.DisplayContentRepositoryImpl$toDisplayTasksModel$1
            r0.<init>(r4, r7)
        L1b:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L39
            if (r2 != r3) goto L31
            int r5 = r0.I$0
            java.lang.Object r4 = r0.L$0
            r6 = r4
            java.util.List r6 = (java.util.List) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L6e
        L31:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L39:
            kotlin.ResultKt.throwOnFailure(r7)
            r0.L$0 = r6
            r0.I$0 = r5
            r0.label = r3
            com.android.systemui.screenshot.proxy.ScreenshotProxy r4 = r4.screenshotProxy
            com.android.systemui.screenshot.proxy.ScreenshotProxyClient r4 = (com.android.systemui.screenshot.proxy.ScreenshotProxyClient) r4
            r4.getClass()
            kotlin.coroutines.SafeContinuation r7 = new kotlin.coroutines.SafeContinuation
            kotlin.coroutines.Continuation r0 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r0)
            r7.<init>(r0)
            com.android.internal.infra.ServiceConnector r4 = r4.proxyConnector
            com.android.systemui.screenshot.proxy.ScreenshotProxyClient$isNotificationShadeExpanded$2$1 r0 = new com.android.internal.infra.ServiceConnector.Job() { // from class: com.android.systemui.screenshot.proxy.ScreenshotProxyClient$isNotificationShadeExpanded$2$1
                static {
                    /*
                        com.android.systemui.screenshot.proxy.ScreenshotProxyClient$isNotificationShadeExpanded$2$1 r0 = new com.android.systemui.screenshot.proxy.ScreenshotProxyClient$isNotificationShadeExpanded$2$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.android.systemui.screenshot.proxy.ScreenshotProxyClient$isNotificationShadeExpanded$2$1) com.android.systemui.screenshot.proxy.ScreenshotProxyClient$isNotificationShadeExpanded$2$1.INSTANCE com.android.systemui.screenshot.proxy.ScreenshotProxyClient$isNotificationShadeExpanded$2$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.proxy.ScreenshotProxyClient$isNotificationShadeExpanded$2$1.<clinit>():void");
                }

                {
                    /*
                        r0 = this;
                        r0.<init>()
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.proxy.ScreenshotProxyClient$isNotificationShadeExpanded$2$1.<init>():void");
                }

                public final java.lang.Object run(java.lang.Object r1) {
                    /*
                        r0 = this;
                        com.android.systemui.screenshot.proxy.IScreenshotProxy r1 = (com.android.systemui.screenshot.proxy.IScreenshotProxy) r1
                        boolean r0 = r1.isNotificationShadeExpanded()
                        java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.proxy.ScreenshotProxyClient$isNotificationShadeExpanded$2$1.run(java.lang.Object):java.lang.Object");
                }
            }
            com.android.internal.infra.AndroidFuture r4 = r4.postForResult(r0)
            com.android.systemui.screenshot.proxy.ScreenshotProxyClient$isNotificationShadeExpanded$2$2 r0 = new com.android.systemui.screenshot.proxy.ScreenshotProxyClient$isNotificationShadeExpanded$2$2
            r0.<init>()
            com.android.systemui.screenshot.proxy.ScreenshotProxyClientKt$sam$java_util_function_BiConsumer$0 r2 = new com.android.systemui.screenshot.proxy.ScreenshotProxyClientKt$sam$java_util_function_BiConsumer$0
            r2.<init>()
            r4.whenComplete(r2)
            java.lang.Object r7 = r7.getOrThrow()
            if (r7 != r1) goto L6e
            return r1
        L6e:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r4 = r7.booleanValue()
            com.android.systemui.screenshot.data.model.SystemUiState r7 = new com.android.systemui.screenshot.data.model.SystemUiState
            r7.<init>(r4)
            com.android.systemui.screenshot.data.model.DisplayContentModel r4 = new com.android.systemui.screenshot.data.model.DisplayContentModel
            r4.<init>(r5, r7, r6)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.data.repository.DisplayContentRepositoryImpl.access$toDisplayTasksModel(com.android.systemui.screenshot.data.repository.DisplayContentRepositoryImpl, int, java.util.List, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final Object getDisplayContent(int i, Continuation continuation) {
        return BuildersKt.withContext(this.background, new DisplayContentRepositoryImpl$getDisplayContent$2(this, i, null), continuation);
    }
}
