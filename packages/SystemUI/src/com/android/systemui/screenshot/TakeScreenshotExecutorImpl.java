package com.android.systemui.screenshot;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScreenshotNotificationsController;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TakeScreenshotExecutorImpl implements TakeScreenshotExecutor {
    public static final List ALLOWED_DISPLAY_TYPES;
    public static final String TAG;
    public final Flow displays;
    public final CoroutineScope mainScope;
    public ScreenCaptureHelper screenCaptureHelper;
    public ScreenshotController screenshotController;
    public final ScreenshotController.Factory screenshotControllerFactory;
    public final ScreenshotRequestProcessor screenshotRequestProcessor;
    public final UiEventLogger uiEventLogger;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        TAG = "Screenshot";
        ALLOWED_DISPLAY_TYPES = CollectionsKt__CollectionsKt.listOf(2, 1, 4, 3);
    }

    public TakeScreenshotExecutorImpl(ScreenshotController.Factory factory, DisplayRepository displayRepository, CoroutineScope coroutineScope, ScreenshotRequestProcessor screenshotRequestProcessor, UiEventLogger uiEventLogger, ScreenshotNotificationsController.Factory factory2, HeadlessScreenshotHandler headlessScreenshotHandler) {
        this.screenshotControllerFactory = factory;
        this.mainScope = coroutineScope;
        this.screenshotRequestProcessor = screenshotRequestProcessor;
        this.uiEventLogger = uiEventLogger;
        this.displays = ((DisplayRepositoryImpl) displayRepository).displays;
        new LinkedHashMap();
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object dispatchToController(com.android.systemui.screenshot.ScreenshotController r19, com.android.systemui.screenshot.ScreenshotData r20, kotlin.jvm.functions.Function1 r21, com.android.systemui.screenshot.TakeScreenshotService.RequestCallback r22, kotlin.coroutines.Continuation r23) {
        /*
            Method dump skipped, instructions count: 591
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.TakeScreenshotExecutorImpl.dispatchToController(com.android.systemui.screenshot.ScreenshotController, com.android.systemui.screenshot.ScreenshotData, kotlin.jvm.functions.Function1, com.android.systemui.screenshot.TakeScreenshotService$RequestCallback, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00e7 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object executeScreenshots(com.android.internal.util.ScreenshotRequest r27, kotlin.jvm.functions.Function1 r28, com.android.systemui.screenshot.TakeScreenshotService.RequestCallback r29, kotlin.coroutines.Continuation r30) {
        /*
            Method dump skipped, instructions count: 235
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.TakeScreenshotExecutorImpl.executeScreenshots(com.android.internal.util.ScreenshotRequest, kotlin.jvm.functions.Function1, com.android.systemui.screenshot.TakeScreenshotService$RequestCallback, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getDisplaysToScreenshot(int r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.screenshot.TakeScreenshotExecutorImpl$getDisplaysToScreenshot$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.screenshot.TakeScreenshotExecutorImpl$getDisplaysToScreenshot$1 r0 = (com.android.systemui.screenshot.TakeScreenshotExecutorImpl$getDisplaysToScreenshot$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.screenshot.TakeScreenshotExecutorImpl$getDisplaysToScreenshot$1 r0 = new com.android.systemui.screenshot.TakeScreenshotExecutorImpl$getDisplaysToScreenshot$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            int r5 = r0.I$0
            java.lang.Object r4 = r0.L$0
            com.android.systemui.screenshot.TakeScreenshotExecutorImpl r4 = (com.android.systemui.screenshot.TakeScreenshotExecutorImpl) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L47
        L2d:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L35:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r4
            r0.I$0 = r5
            r0.label = r3
            kotlinx.coroutines.flow.Flow r6 = r4.displays
            java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.first(r6, r0)
            if (r6 != r1) goto L47
            return r1
        L47:
            java.util.Set r6 = (java.util.Set) r6
            r0 = 3
            if (r5 != r0) goto L6e
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.Iterator r5 = r6.iterator()
        L57:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto Lc2
            java.lang.Object r6 = r5.next()
            r0 = r6
            android.view.Display r0 = (android.view.Display) r0
            int r0 = r0.getDisplayId()
            if (r0 != 0) goto L57
            r4.add(r6)
            goto L57
        L6e:
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.Iterator r0 = r6.iterator()
        L79:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L97
            java.lang.Object r1 = r0.next()
            r2 = r1
            android.view.Display r2 = (android.view.Display) r2
            int r2 = r2.getDisplayId()
            com.android.systemui.screenshot.sep.ScreenCaptureHelper r3 = r4.screenCaptureHelper
            if (r3 != 0) goto L8f
            r3 = 0
        L8f:
            int r3 = r3.builtInDisplayId
            if (r2 != r3) goto L79
            r5.add(r1)
            goto L79
        L97:
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.Iterator r5 = r6.iterator()
        La0:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto Lc2
            java.lang.Object r6 = r5.next()
            r0 = r6
            android.view.Display r0 = (android.view.Display) r0
            java.util.List r1 = com.android.systemui.screenshot.TakeScreenshotExecutorImpl.ALLOWED_DISPLAY_TYPES
            int r0 = r0.getType()
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r0)
            boolean r0 = r1.contains(r2)
            if (r0 == 0) goto La0
            r4.add(r6)
            goto La0
        Lc2:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.TakeScreenshotExecutorImpl.getDisplaysToScreenshot(int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void onFailedScreenshotRequest(ScreenshotData screenshotData, TakeScreenshotService.RequestCallback requestCallback) {
        this.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_CAPTURE_FAILED, 0, screenshotData.getPackageNameString());
        ((TakeScreenshotService.RequestCallbackImpl) requestCallback).reportError();
    }
}
