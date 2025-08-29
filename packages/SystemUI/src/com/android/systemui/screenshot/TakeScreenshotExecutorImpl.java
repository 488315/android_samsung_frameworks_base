package com.android.systemui.screenshot;

import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.ScreenshotRequest;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.display.data.repository.FocusedDisplayRepository;
import com.android.systemui.screenshot.InteractiveScreenshotHandler;
import com.android.systemui.screenshot.ScreenshotData;
import com.android.systemui.screenshot.ScreenshotNotificationsController;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.policy.PolicyRequestProcessor;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.screenshot.TakeScreenshotExecutorImpl$dispatchToController$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            TakeScreenshotExecutorImpl takeScreenshotExecutorImpl = TakeScreenshotExecutorImpl.this;
            String str = TakeScreenshotExecutorImpl.TAG;
            return takeScreenshotExecutorImpl.dispatchToController(null, null, null, null, this);
        }
    }

    /* renamed from: com.android.systemui.screenshot.TakeScreenshotExecutorImpl$executeScreenshots$1, reason: invalid class name and case insensitive filesystem */
    final class C10291 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public C10291(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TakeScreenshotExecutorImpl.this.executeScreenshots(null, null, null, this);
        }
    }

    /* renamed from: com.android.systemui.screenshot.TakeScreenshotExecutorImpl$getDisplaysToScreenshot$1, reason: invalid class name and case insensitive filesystem */
    final class C10301 extends ContinuationImpl {
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C10301(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            TakeScreenshotExecutorImpl takeScreenshotExecutorImpl = TakeScreenshotExecutorImpl.this;
            String str = TakeScreenshotExecutorImpl.TAG;
            return takeScreenshotExecutorImpl.getDisplaysToScreenshot(0, this);
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r7v9, types: [com.android.systemui.screenshot.ScreenshotHandler] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object dispatchToController(InteractiveScreenshotHandler interactiveScreenshotHandler, ScreenshotData screenshotData, Function1 function1, TakeScreenshotService.RequestCallback requestCallback, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        Object failure;
        InteractiveScreenshotHandler interactiveScreenshotHandler2;
        InteractiveScreenshotHandler interactiveScreenshotHandler3;
        int i;
        InteractiveScreenshotHandler interactiveScreenshotHandler4;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objProcess = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        try {
            if (i3 == 0) {
                ResultKt.throwOnFailure(objProcess);
                int i4 = Result.$r8$clinit;
                ScreenshotRequestProcessor screenshotRequestProcessor = this.screenshotRequestProcessor;
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = interactiveScreenshotHandler;
                anonymousClass1.L$2 = screenshotData;
                anonymousClass1.L$3 = function1;
                anonymousClass1.L$4 = requestCallback;
                anonymousClass1.label = 1;
                objProcess = ((PolicyRequestProcessor) screenshotRequestProcessor).process(screenshotData, anonymousClass1);
                interactiveScreenshotHandler = interactiveScreenshotHandler;
                if (objProcess == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i3 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                requestCallback = (TakeScreenshotService.RequestCallback) anonymousClass1.L$4;
                function1 = (Function1) anonymousClass1.L$3;
                screenshotData = (ScreenshotData) anonymousClass1.L$2;
                ?? r7 = (ScreenshotHandler) anonymousClass1.L$1;
                this = (TakeScreenshotExecutorImpl) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objProcess);
                interactiveScreenshotHandler = r7;
            }
            failure = (ScreenshotData) objProcess;
            int i5 = Result.$r8$clinit;
            interactiveScreenshotHandler2 = interactiveScreenshotHandler;
        } catch (Throwable th) {
            int i6 = Result.$r8$clinit;
            failure = new Result.Failure(th);
            interactiveScreenshotHandler2 = interactiveScreenshotHandler;
        }
        Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
        String str = TAG;
        if (thM3441exceptionOrNullimpl != null) {
            Log.e(str, "Failed to process screenshot request!", thM3441exceptionOrNullimpl);
            this.uiEventLogger.log(ScreenshotEvent.getScreenshotSource(screenshotData.source), 0, screenshotData.getPackageNameString());
            this.onFailedScreenshotRequest(screenshotData, requestCallback);
        }
        if (failure instanceof Result.Failure) {
            failure = null;
        }
        ScreenshotData screenshotData2 = (ScreenshotData) failure;
        if (screenshotData2 == null) {
            return Unit.INSTANCE;
        }
        this.uiEventLogger.log(ScreenshotEvent.getScreenshotSource(screenshotData2.source), 0, screenshotData2.getPackageNameString());
        Log.d(str, "Screenshot request: " + screenshotData2);
        InteractiveScreenshotHandler interactiveScreenshotHandler5 = this.screenshotController;
        if ((interactiveScreenshotHandler5 != null && interactiveScreenshotHandler5.isAnimationRunning()) || (((interactiveScreenshotHandler3 = this.screenshotController) != null && interactiveScreenshotHandler3.isSnackBarShowing()) || ((i = screenshotData2.type) == 2 && (interactiveScreenshotHandler4 = this.screenshotController) != null && interactiveScreenshotHandler4.isScreenshotSelectorViewVisible()))) {
            this.onFailedScreenshotRequest(screenshotData2, requestCallback);
            return Unit.INSTANCE;
        }
        ScreenCaptureHelper screenCaptureHelper = this.screenCaptureHelper;
        InteractiveScreenshotHandler interactiveScreenshotHandler6 = this.screenshotController;
        if (interactiveScreenshotHandler6 != null) {
            if (screenCaptureHelper == null) {
                screenCaptureHelper = null;
            }
            interactiveScreenshotHandler6.setScreenCaptureHelper(screenCaptureHelper);
        }
        InteractiveScreenshotHandler interactiveScreenshotHandler7 = this.screenshotController;
        if (interactiveScreenshotHandler7 != null) {
            interactiveScreenshotHandler7.initSemScreenshotLayout();
        }
        if (i == 2) {
            Log.d(str, "Partial screenshot");
            InteractiveScreenshotHandler interactiveScreenshotHandler8 = this.screenshotController;
            if (interactiveScreenshotHandler8 != null) {
                ScreenCaptureHelper screenCaptureHelper2 = this.screenCaptureHelper;
                interactiveScreenshotHandler8.setPartialScreenshotSelector((screenCaptureHelper2 != null ? screenCaptureHelper2 : null).mBundle, screenshotData2, new TakeScreenshotExecutorImpl$sam$java_util_function_Consumer$0(function1), requestCallback);
            }
            return Unit.INSTANCE;
        }
        try {
            interactiveScreenshotHandler2.handleScreenshot(screenshotData2, new TakeScreenshotExecutorImpl$sam$java_util_function_Consumer$0(function1), requestCallback);
            return Unit.INSTANCE;
        } catch (IllegalStateException e) {
            Log.e(str, "Error while ScreenshotController was handling ScreenshotData!", e);
            this.onFailedScreenshotRequest(screenshotData2, requestCallback);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x00d5, code lost:
    
        if (r3.dispatchToController(r1, r11, r6, r7, r8) == r2) goto L29;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0018  */
    /* JADX WARN: Type inference failed for: r3v5, types: [kotlin.jvm.functions.Function1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object executeScreenshots(ScreenshotRequest screenshotRequest, TakeScreenshotExecutorImpl$executeScreenshotsAsync$1$$ExternalSyntheticLambda0 takeScreenshotExecutorImpl$executeScreenshotsAsync$1$$ExternalSyntheticLambda0, TakeScreenshotService.RequestCallback requestCallback, ContinuationImpl continuationImpl) {
        C10291 c10291;
        TakeScreenshotExecutorImpl$executeScreenshotsAsync$1$$ExternalSyntheticLambda0 takeScreenshotExecutorImpl$executeScreenshotsAsync$1$$ExternalSyntheticLambda02;
        TakeScreenshotService.RequestCallback requestCallback2;
        ScreenshotRequest screenshotRequest2;
        TakeScreenshotExecutorImpl takeScreenshotExecutorImpl;
        if (continuationImpl instanceof C10291) {
            c10291 = (C10291) continuationImpl;
            int i = c10291.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c10291.label = i - Integer.MIN_VALUE;
            } else {
                c10291 = new C10291(continuationImpl);
            }
        }
        C10291 c102912 = c10291;
        Object displaysToScreenshot = c102912.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c102912.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(displaysToScreenshot);
            int type = screenshotRequest.getType();
            c102912.L$0 = this;
            c102912.L$1 = screenshotRequest;
            takeScreenshotExecutorImpl$executeScreenshotsAsync$1$$ExternalSyntheticLambda02 = takeScreenshotExecutorImpl$executeScreenshotsAsync$1$$ExternalSyntheticLambda0;
            c102912.L$2 = takeScreenshotExecutorImpl$executeScreenshotsAsync$1$$ExternalSyntheticLambda02;
            requestCallback2 = requestCallback;
            c102912.L$3 = requestCallback2;
            c102912.label = 1;
            displaysToScreenshot = getDisplaysToScreenshot(type, c102912);
            if (displaysToScreenshot != coroutineSingletons) {
                screenshotRequest2 = screenshotRequest;
                takeScreenshotExecutorImpl = this;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(displaysToScreenshot);
            return Unit.INSTANCE;
        }
        TakeScreenshotService.RequestCallback requestCallback3 = (TakeScreenshotService.RequestCallback) c102912.L$3;
        ?? r3 = (Function1) c102912.L$2;
        screenshotRequest2 = (ScreenshotRequest) c102912.L$1;
        TakeScreenshotExecutorImpl takeScreenshotExecutorImpl2 = (TakeScreenshotExecutorImpl) c102912.L$0;
        ResultKt.throwOnFailure(displaysToScreenshot);
        takeScreenshotExecutorImpl$executeScreenshotsAsync$1$$ExternalSyntheticLambda02 = r3;
        takeScreenshotExecutorImpl = takeScreenshotExecutorImpl2;
        requestCallback2 = requestCallback3;
        Display display = (Display) CollectionsKt___CollectionsKt.first((List) displaysToScreenshot);
        InteractiveScreenshotHandler interactiveScreenshotHandlerCreate = takeScreenshotExecutorImpl.screenshotController;
        if (interactiveScreenshotHandlerCreate == null) {
            interactiveScreenshotHandlerCreate = takeScreenshotExecutorImpl.interactiveScreenshotHandlerFactory.create(display);
        }
        takeScreenshotExecutorImpl.screenshotController = interactiveScreenshotHandlerCreate;
        ScreenshotData.Companion companion = ScreenshotData.Companion;
        ScreenCaptureHelper screenCaptureHelper = takeScreenshotExecutorImpl.screenCaptureHelper;
        if (screenCaptureHelper == null) {
            screenCaptureHelper = null;
        }
        int i3 = screenCaptureHelper.builtInDisplayId;
        companion.getClass();
        ScreenshotData screenshotData = new ScreenshotData(screenshotRequest2.getType(), screenshotRequest2.getSource(), UserHandle.of(screenshotRequest2.getUserId()), screenshotRequest2.getTopComponent(), screenshotRequest2.getTaskId(), screenshotRequest2.getBoundsInScreen(), screenshotRequest2.getInsets(), screenshotRequest2.getBitmap(), i3, false, false, 1536, null);
        c102912.L$0 = null;
        c102912.L$1 = null;
        c102912.L$2 = null;
        c102912.L$3 = null;
        c102912.label = 2;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getDisplaysToScreenshot(int i, ContinuationImpl continuationImpl) {
        C10301 c10301;
        if (continuationImpl instanceof C10301) {
            c10301 = (C10301) continuationImpl;
            int i2 = c10301.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c10301.label = i2 - Integer.MIN_VALUE;
            } else {
                c10301 = new C10301(continuationImpl);
            }
        }
        Object objFirst = c10301.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = c10301.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(objFirst);
            c10301.L$0 = this;
            c10301.I$0 = i;
            c10301.label = 1;
            objFirst = FlowKt.first(this.displays, c10301);
            if (objFirst == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = c10301.I$0;
            this = (TakeScreenshotExecutorImpl) c10301.L$0;
            ResultKt.throwOnFailure(objFirst);
        }
        Set set = (Set) objFirst;
        if (i == 3) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : set) {
                if (((Display) obj).getDisplayId() == 0) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }
        Set set2 = set;
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : set2) {
            int displayId = ((Display) obj2).getDisplayId();
            ScreenCaptureHelper screenCaptureHelper = this.screenCaptureHelper;
            if (screenCaptureHelper == null) {
                screenCaptureHelper = null;
            }
            if (displayId == screenCaptureHelper.builtInDisplayId) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj3 : set2) {
            if (ALLOWED_DISPLAY_TYPES.contains(new Integer(((Display) obj3).getType()))) {
                arrayList3.add(obj3);
            }
        }
        return arrayList3;
    }

    public final void onFailedScreenshotRequest(ScreenshotData screenshotData, TakeScreenshotService.RequestCallback requestCallback) {
        this.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_CAPTURE_FAILED, 0, screenshotData.getPackageNameString());
        ((TakeScreenshotService.RequestCallbackImpl) requestCallback).reportError();
    }
}
