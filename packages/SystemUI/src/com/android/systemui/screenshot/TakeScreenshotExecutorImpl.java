package com.android.systemui.screenshot;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.display.data.repository.FocusedDisplayRepository;
import com.android.systemui.screenshot.InteractiveScreenshotHandler;
import com.android.systemui.screenshot.ScreenshotNotificationsController;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TakeScreenshotExecutorImpl implements TakeScreenshotExecutor {
    public static final List ALLOWED_DISPLAY_TYPES;
    public static final String TAG;
    public final StateFlow displays;
    public final InteractiveScreenshotHandler.Factory interactiveScreenshotHandlerFactory;
    public final CoroutineScope mainScope;
    public ScreenCaptureHelper screenCaptureHelper;
    public InteractiveScreenshotHandler screenshotController;
    public final ScreenshotRequestProcessor screenshotRequestProcessor;
    public final UiEventLogger uiEventLogger;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TAG = "Screenshot";
        ALLOWED_DISPLAY_TYPES = Arrays.asList(2, 1, 4, 3);
    }

    public TakeScreenshotExecutorImpl(InteractiveScreenshotHandler.Factory factory, DisplayRepository displayRepository, CoroutineScope coroutineScope, ScreenshotRequestProcessor screenshotRequestProcessor, UiEventLogger uiEventLogger, ScreenshotNotificationsController.Factory factory2, HeadlessScreenshotHandler headlessScreenshotHandler, FocusedDisplayRepository focusedDisplayRepository) {
        this.interactiveScreenshotHandlerFactory = factory;
        this.mainScope = coroutineScope;
        this.screenshotRequestProcessor = screenshotRequestProcessor;
        this.uiEventLogger = uiEventLogger;
        this.displays = ((DisplayRepositoryImpl) displayRepository).displayRepositoryFromLib.getDisplays();
        new LinkedHashMap();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(15:0|1|(2:3|(11:5|6|7|(1:(2:10|11)(2:66|67))(3:68|69|(1:71))|12|13|(1:15)|16|(1:18)|19|(2:21|22)(2:24|(5:40|(2:(1:43)|44)|45|(1:47)|(4:49|(3:51|(1:53)|54)|55|56)(4:57|58|59|60))(2:28|29))))|74|6|7|(0)(0)|12|13|(0)|16|(0)|19|(0)(0)|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x003f, code lost:
    
        r11 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x006a, code lost:
    
        r0 = kotlin.Result.$r8$clinit;
        r11 = new kotlin.Result.Failure(r11);
        r7 = r7;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /* JADX WARN: Type inference failed for: r7v9, types: [com.android.systemui.screenshot.ScreenshotHandler] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object dispatchToController(com.android.systemui.screenshot.InteractiveScreenshotHandler r7, com.android.systemui.screenshot.ScreenshotData r8, kotlin.jvm.functions.Function1 r9, com.android.systemui.screenshot.TakeScreenshotService.RequestCallback r10, kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            Method dump skipped, instructions count: 306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.TakeScreenshotExecutorImpl.dispatchToController(com.android.systemui.screenshot.InteractiveScreenshotHandler, com.android.systemui.screenshot.ScreenshotData, kotlin.jvm.functions.Function1, com.android.systemui.screenshot.TakeScreenshotService$RequestCallback, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x00d5, code lost:
    
        if (r3.dispatchToController(r1, r11, r6, r7, r8) != r2) goto L30;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0028  */
    /* JADX WARN: Type inference failed for: r3v5, types: [kotlin.jvm.functions.Function1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object executeScreenshots(com.android.internal.util.ScreenshotRequest r26, com.android.systemui.screenshot.TakeScreenshotExecutorImpl$executeScreenshotsAsync$1$$ExternalSyntheticLambda0 r27, com.android.systemui.screenshot.TakeScreenshotService.RequestCallback r28, kotlin.coroutines.jvm.internal.ContinuationImpl r29) {
        /*
            Method dump skipped, instructions count: 219
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.TakeScreenshotExecutorImpl.executeScreenshots(com.android.internal.util.ScreenshotRequest, com.android.systemui.screenshot.TakeScreenshotExecutorImpl$executeScreenshotsAsync$1$$ExternalSyntheticLambda0, com.android.systemui.screenshot.TakeScreenshotService$RequestCallback, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getDisplaysToScreenshot(int r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
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
            kotlinx.coroutines.flow.StateFlow r6 = r4.displays
            java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.first(r6, r0)
            if (r6 != r1) goto L47
            return r1
        L47:
            java.util.Set r6 = (java.util.Set) r6
            r0 = 3
            if (r5 != r0) goto L6f
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.Iterator r5 = r6.iterator()
        L57:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L6e
            java.lang.Object r6 = r5.next()
            r0 = r6
            android.view.Display r0 = (android.view.Display) r0
            int r0 = r0.getDisplayId()
            if (r0 != 0) goto L57
            r4.add(r6)
            goto L57
        L6e:
            return r4
        L6f:
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.Iterator r0 = r6.iterator()
        L7a:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L98
            java.lang.Object r1 = r0.next()
            r2 = r1
            android.view.Display r2 = (android.view.Display) r2
            int r2 = r2.getDisplayId()
            com.android.systemui.screenshot.sep.ScreenCaptureHelper r3 = r4.screenCaptureHelper
            if (r3 != 0) goto L90
            r3 = 0
        L90:
            int r3 = r3.builtInDisplayId
            if (r2 != r3) goto L7a
            r5.add(r1)
            goto L7a
        L98:
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.Iterator r5 = r6.iterator()
        La1:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto Lc3
            java.lang.Object r6 = r5.next()
            r0 = r6
            android.view.Display r0 = (android.view.Display) r0
            java.util.List r1 = com.android.systemui.screenshot.TakeScreenshotExecutorImpl.ALLOWED_DISPLAY_TYPES
            int r0 = r0.getType()
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r0)
            boolean r0 = r1.contains(r2)
            if (r0 == 0) goto La1
            r4.add(r6)
            goto La1
        Lc3:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.TakeScreenshotExecutorImpl.getDisplaysToScreenshot(int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final void onFailedScreenshotRequest(ScreenshotData screenshotData, TakeScreenshotService.RequestCallback requestCallback) {
        this.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_CAPTURE_FAILED, 0, screenshotData.getPackageNameString());
        ((TakeScreenshotService.RequestCallbackImpl) requestCallback).reportError();
    }
}
