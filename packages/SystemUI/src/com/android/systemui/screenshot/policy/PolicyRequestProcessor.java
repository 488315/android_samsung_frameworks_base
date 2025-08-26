package com.android.systemui.screenshot.policy;

import android.app.ActivityTaskManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.screenshot.ImageCapture;
import com.android.systemui.screenshot.ScreenshotData;
import com.android.systemui.screenshot.ScreenshotRequestProcessor;
import com.android.systemui.screenshot.data.model.DisplayContentModel;
import com.android.systemui.screenshot.data.repository.DisplayContentRepository;
import com.android.systemui.screenshot.data.repository.DisplayContentRepositoryImpl;
import com.android.systemui.screenshot.policy.CapturePolicy;
import com.android.systemui.screenshot.policy.CaptureType;
import com.android.systemui.screenshot.sep.SemImageCaptureImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes2.dex */
public final class PolicyRequestProcessor implements ScreenshotRequestProcessor {
    public final CoroutineDispatcher background;
    public final ImageCapture capture;
    public final ComponentName defaultComponent;
    public final UserHandle defaultOwner;
    public final DisplayContentRepository displayTasks;
    public final List policies;
    public final SemImageCaptureImpl semImageCapture;

    /* renamed from: com.android.systemui.screenshot.policy.PolicyRequestProcessor$modify$2, reason: invalid class name */
    final class AnonymousClass2 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass2(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PolicyRequestProcessor.this.modify(null, null, this);
        }
    }

    /* renamed from: com.android.systemui.screenshot.policy.PolicyRequestProcessor$process$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PolicyRequestProcessor.this.process(null, this);
        }
    }

    /* renamed from: com.android.systemui.screenshot.policy.PolicyRequestProcessor$replaceWithScreenshot$1, reason: invalid class name and case insensitive filesystem */
    final class C10311 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public C10311(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PolicyRequestProcessor.this.replaceWithScreenshot(null, null, null, 0, null, this);
        }
    }

    /* renamed from: com.android.systemui.screenshot.policy.PolicyRequestProcessor$replaceWithTaskSnapshot$1, reason: invalid class name and case insensitive filesystem */
    final class C10321 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public C10321(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PolicyRequestProcessor.this.replaceWithTaskSnapshot(null, null, null, 0, null, this);
        }
    }

    public PolicyRequestProcessor(CoroutineDispatcher coroutineDispatcher, ImageCapture imageCapture, DisplayContentRepository displayContentRepository, List<? extends CapturePolicy> list, ScreenshotPolicy screenshotPolicy, UserHandle userHandle, ComponentName componentName, SemImageCaptureImpl semImageCaptureImpl, DevicePolicyManager devicePolicyManager) {
        this.background = coroutineDispatcher;
        this.capture = imageCapture;
        this.displayTasks = displayContentRepository;
        this.policies = list;
        this.defaultOwner = userHandle;
        this.defaultComponent = componentName;
        this.semImageCapture = semImageCaptureImpl;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0067, code lost:
    
        if (r11 == r0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0083, code lost:
    
        if (r11 == r0) goto L28;
     */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object modify(ScreenshotData screenshotData, LegacyCaptureParameters legacyCaptureParameters, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass2 anonymousClass2;
        if (continuationImpl instanceof AnonymousClass2) {
            anonymousClass2 = (AnonymousClass2) continuationImpl;
            int i = anonymousClass2.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass2.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass2 = new AnonymousClass2(continuationImpl);
            }
        }
        AnonymousClass2 anonymousClass22 = anonymousClass2;
        Object objReplaceWithScreenshot = anonymousClass22.result;
        Object obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass22.label;
        if (i2 != 0) {
            if (i2 == 1) {
                ResultKt.throwOnFailure(objReplaceWithScreenshot);
                return (ScreenshotData) objReplaceWithScreenshot;
            }
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objReplaceWithScreenshot);
            return (ScreenshotData) objReplaceWithScreenshot;
        }
        ResultKt.throwOnFailure(objReplaceWithScreenshot);
        Log.d("PolicyRequestProcessor", "[modify] CaptureParameters = " + legacyCaptureParameters);
        CaptureType captureType = legacyCaptureParameters.type;
        if (captureType instanceof CaptureType.IsolatedTask) {
            ComponentName componentName = legacyCaptureParameters.component;
            UserHandle userHandle = legacyCaptureParameters.owner;
            CaptureType.IsolatedTask isolatedTask = (CaptureType.IsolatedTask) captureType;
            int i3 = isolatedTask.taskId;
            Rect rect = isolatedTask.taskBounds;
            anonymousClass22.label = 1;
            objReplaceWithScreenshot = replaceWithTaskSnapshot(screenshotData, componentName, userHandle, i3, rect, anonymousClass22);
        } else {
            if (!(captureType instanceof CaptureType.FullScreen)) {
                throw new NoWhenBranchMatchedException();
            }
            ComponentName componentName2 = legacyCaptureParameters.component;
            UserHandle userHandle2 = legacyCaptureParameters.owner;
            int i4 = ((CaptureType.FullScreen) captureType).displayId;
            anonymousClass22.label = 2;
            objReplaceWithScreenshot = replaceWithScreenshot(screenshotData, componentName2, userHandle2, i4, null, anonymousClass22);
        }
        return obj;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0095, code lost:
    
        if (r2 == r4) goto L78;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x00da -> B:36:0x00db). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object process(ScreenshotData screenshotData, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        PolicyRequestProcessor policyRequestProcessor;
        ScreenshotData screenshotData2;
        Iterator it;
        Collection collection;
        DisplayContentModel displayContentModel;
        ActivityTaskManager.RootTaskInfo rootTaskInfo;
        ComponentName componentName;
        Object next;
        PolicyRequestProcessor policyRequestProcessor2 = this;
        ScreenshotData screenshotData3 = screenshotData;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = policyRequestProcessor2.new AnonymousClass1(continuationImpl);
            }
        }
        Object displayContent = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(displayContent);
            if (screenshotData3.type == 3) {
                Log.i("PolicyRequestProcessor", "Screenshot bitmap provided. No modifications applied.");
                return screenshotData3;
            }
            anonymousClass1.L$0 = policyRequestProcessor2;
            anonymousClass1.L$1 = screenshotData3;
            anonymousClass1.label = 1;
            displayContent = ((DisplayContentRepositoryImpl) policyRequestProcessor2.displayTasks).getDisplayContent(screenshotData3.displayId, anonymousClass1);
        } else if (i2 == 1) {
            ScreenshotData screenshotData4 = (ScreenshotData) anonymousClass1.L$1;
            PolicyRequestProcessor policyRequestProcessor3 = (PolicyRequestProcessor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(displayContent);
            screenshotData3 = screenshotData4;
            policyRequestProcessor2 = policyRequestProcessor3;
        } else {
            if (i2 != 2) {
                if (i2 == 3) {
                    ResultKt.throwOnFailure(displayContent);
                    return displayContent;
                }
                if (i2 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(displayContent);
                return displayContent;
            }
            collection = (Collection) anonymousClass1.L$5;
            it = (Iterator) anonymousClass1.L$4;
            Collection collection2 = (Collection) anonymousClass1.L$3;
            displayContentModel = (DisplayContentModel) anonymousClass1.L$2;
            ScreenshotData screenshotData5 = (ScreenshotData) anonymousClass1.L$1;
            PolicyRequestProcessor policyRequestProcessor4 = (PolicyRequestProcessor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(displayContent);
            screenshotData2 = screenshotData5;
            policyRequestProcessor = policyRequestProcessor4;
            CapturePolicy.PolicyResult policyResult = (CapturePolicy.PolicyResult) displayContent;
            if (policyResult instanceof CapturePolicy.PolicyResult.Matched) {
                if (!(policyResult instanceof CapturePolicy.PolicyResult.NotMatched)) {
                    throw new NoWhenBranchMatchedException();
                }
                collection.add(new Integer(Log.i("PolicyRequestProcessor", String.valueOf(policyResult))));
                collection = collection2;
                if (it.hasNext()) {
                    anonymousClass1.L$0 = null;
                    anonymousClass1.L$1 = null;
                    anonymousClass1.L$2 = null;
                    anonymousClass1.L$3 = null;
                    anonymousClass1.L$4 = null;
                    anonymousClass1.L$5 = null;
                    anonymousClass1.label = 4;
                    policyRequestProcessor.getClass();
                    if (displayContentModel.systemUiState.shadeExpanded) {
                        rootTaskInfo = null;
                    } else {
                        Iterator it2 = displayContentModel.rootTasks.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it2.next();
                            ActivityTaskManager.RootTaskInfo rootTaskInfo2 = (ActivityTaskManager.RootTaskInfo) next;
                            if (rootTaskInfo2.getWindowingMode() != 2 && rootTaskInfo2.isVisible && rootTaskInfo2.isRunning && rootTaskInfo2.numActivities > 0 && rootTaskInfo2.topActivity != null && rootTaskInfo2.childTaskIds.length != 0) {
                                break;
                            }
                        }
                        rootTaskInfo = (ActivityTaskManager.RootTaskInfo) next;
                    }
                    if (rootTaskInfo == null || (componentName = rootTaskInfo.topActivity) == null) {
                        componentName = policyRequestProcessor.defaultComponent;
                    }
                    Object objReplaceWithScreenshot = policyRequestProcessor.replaceWithScreenshot(screenshotData2, componentName, policyRequestProcessor.defaultOwner, screenshotData2.displayId, rootTaskInfo != null ? new Integer(rootTaskInfo.taskId) : null, anonymousClass1);
                    if (objReplaceWithScreenshot != coroutineSingletons) {
                        return objReplaceWithScreenshot;
                    }
                } else {
                    CapturePolicy capturePolicy = (CapturePolicy) it.next();
                    anonymousClass1.L$0 = policyRequestProcessor;
                    anonymousClass1.L$1 = screenshotData2;
                    anonymousClass1.L$2 = displayContentModel;
                    anonymousClass1.L$3 = collection;
                    anonymousClass1.L$4 = it;
                    anonymousClass1.L$5 = collection;
                    anonymousClass1.label = 2;
                    displayContent = capturePolicy.check(displayContentModel, anonymousClass1);
                    if (displayContent != coroutineSingletons) {
                        collection2 = collection;
                        CapturePolicy.PolicyResult policyResult2 = (CapturePolicy.PolicyResult) displayContent;
                        if (policyResult2 instanceof CapturePolicy.PolicyResult.Matched) {
                            Log.i("PolicyRequestProcessor", String.valueOf(policyResult2));
                            LegacyCaptureParameters legacyCaptureParameters = ((CapturePolicy.PolicyResult.Matched) policyResult2).parameters;
                            anonymousClass1.L$0 = null;
                            anonymousClass1.L$1 = null;
                            anonymousClass1.L$2 = null;
                            anonymousClass1.L$3 = null;
                            anonymousClass1.L$4 = null;
                            anonymousClass1.L$5 = null;
                            anonymousClass1.label = 3;
                            Object objModify = policyRequestProcessor.modify(screenshotData2, legacyCaptureParameters, anonymousClass1);
                            if (objModify != coroutineSingletons) {
                                return objModify;
                            }
                        }
                    }
                }
                return coroutineSingletons;
            }
        }
        Log.i("PolicyRequestProcessor", "Applying policy checks....");
        List list = policyRequestProcessor2.policies;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        policyRequestProcessor = policyRequestProcessor2;
        screenshotData2 = screenshotData3;
        it = list.iterator();
        collection = arrayList;
        displayContentModel = (DisplayContentModel) displayContent;
        if (it.hasNext()) {
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object replaceWithScreenshot(ScreenshotData screenshotData, ComponentName componentName, UserHandle userHandle, int i, Integer num, ContinuationImpl continuationImpl) throws Throwable {
        C10311 c10311;
        ComponentName componentName2;
        UserHandle userHandle2;
        Integer num2;
        ScreenshotData screenshotData2;
        if (continuationImpl instanceof C10311) {
            c10311 = (C10311) continuationImpl;
            int i2 = c10311.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c10311.label = i2 - Integer.MIN_VALUE;
            } else {
                c10311 = new C10311(continuationImpl);
            }
        }
        Object obj = c10311.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = c10311.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            Log.i("PolicyRequestProcessor", "Capturing screenshot: " + componentName + " / " + userHandle);
            c10311.L$0 = screenshotData;
            c10311.L$1 = componentName;
            c10311.L$2 = userHandle;
            c10311.L$3 = num;
            c10311.label = 1;
            Object objWithContext = BuildersKt.withContext(this.background, new PolicyRequestProcessor$captureDisplay$2(this, i, null), c10311);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = objWithContext;
            componentName2 = componentName;
            userHandle2 = userHandle;
            num2 = num;
            screenshotData2 = screenshotData;
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            num2 = (Integer) c10311.L$3;
            UserHandle userHandle3 = (UserHandle) c10311.L$2;
            ComponentName componentName3 = (ComponentName) c10311.L$1;
            ScreenshotData screenshotData3 = (ScreenshotData) c10311.L$0;
            ResultKt.throwOnFailure(obj);
            userHandle2 = userHandle3;
            componentName2 = componentName3;
            screenshotData2 = screenshotData3;
        }
        Bitmap bitmap = (Bitmap) obj;
        if (bitmap == null) {
            throw new IllegalStateException("Failed to capture screenshot");
        }
        return ScreenshotData.copy$default(screenshotData2, screenshotData2.type, userHandle2, componentName2, num2 != null ? num2.intValue() : -1, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), bitmap, false, 1858);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object replaceWithTaskSnapshot(ScreenshotData screenshotData, ComponentName componentName, UserHandle userHandle, int i, Rect rect, ContinuationImpl continuationImpl) throws Throwable {
        C10321 c10321;
        ComponentName componentName2;
        int i2;
        Rect rect2;
        ScreenshotData screenshotData2;
        UserHandle userHandle2 = userHandle;
        if (continuationImpl instanceof C10321) {
            c10321 = (C10321) continuationImpl;
            int i3 = c10321.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                c10321.label = i3 - Integer.MIN_VALUE;
            } else {
                c10321 = new C10321(continuationImpl);
            }
        }
        Object obj = c10321.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = c10321.label;
        if (i4 == 0) {
            ResultKt.throwOnFailure(obj);
            Log.i("PolicyRequestProcessor", "Capturing task snapshot: " + componentName + " / " + userHandle2);
            c10321.L$0 = screenshotData;
            c10321.L$1 = componentName;
            c10321.L$2 = userHandle2;
            c10321.L$3 = rect;
            c10321.I$0 = i;
            c10321.label = 1;
            Object objSemCaptureTask = this.semImageCapture.semCaptureTask(i, c10321);
            if (objSemCaptureTask == coroutineSingletons) {
                return coroutineSingletons;
            }
            componentName2 = componentName;
            i2 = i;
            rect2 = rect;
            screenshotData2 = screenshotData;
            obj = objSemCaptureTask;
        } else {
            if (i4 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i5 = c10321.I$0;
            Rect rect3 = (Rect) c10321.L$3;
            userHandle2 = (UserHandle) c10321.L$2;
            ComponentName componentName3 = (ComponentName) c10321.L$1;
            ScreenshotData screenshotData3 = (ScreenshotData) c10321.L$0;
            ResultKt.throwOnFailure(obj);
            i2 = i5;
            rect2 = rect3;
            componentName2 = componentName3;
            screenshotData2 = screenshotData3;
        }
        UserHandle userHandle3 = userHandle2;
        Pair pair = (Pair) obj;
        Bitmap bitmap = (Bitmap) pair.getSecond();
        if (bitmap != null) {
            return ScreenshotData.copy$default(screenshotData2, 3, userHandle3, componentName2, i2, rect2, bitmap, ((Boolean) pair.getFirst()).booleanValue(), 834);
        }
        throw new IllegalStateException("Failed to capture screenshot");
    }

    public /* synthetic */ PolicyRequestProcessor(CoroutineDispatcher coroutineDispatcher, ImageCapture imageCapture, DisplayContentRepository displayContentRepository, List list, ScreenshotPolicy screenshotPolicy, UserHandle userHandle, ComponentName componentName, SemImageCaptureImpl semImageCaptureImpl, DevicePolicyManager devicePolicyManager, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(coroutineDispatcher, imageCapture, displayContentRepository, list, screenshotPolicy, (i & 32) != 0 ? Process.myUserHandle() : userHandle, componentName, semImageCaptureImpl, devicePolicyManager);
    }
}
