package com.android.systemui.screenshot.policy;

import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.screenshot.data.model.ChildTaskModel;
import com.android.systemui.screenshot.data.model.DisplayContentModel;
import com.android.systemui.screenshot.data.model.ProfileType;
import com.android.systemui.screenshot.data.repository.ProfileTypeRepository;
import com.android.systemui.screenshot.data.repository.ProfileTypeRepositoryImpl;
import com.android.systemui.screenshot.policy.CapturePolicy;
import com.android.systemui.screenshot.policy.CaptureType;
import com.android.wm.shell.shared.desktopmode.DesktopModeStatus;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntProgression;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence.AnonymousClass1;

/* loaded from: classes2.dex */
public final class WorkProfilePolicy implements CapturePolicy {
    public final Context context;
    public final ProfileTypeRepository profileTypes;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.screenshot.policy.WorkProfilePolicy$check$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return WorkProfilePolicy.this.check(null, this);
        }
    }

    static {
        new Companion(null);
    }

    public WorkProfilePolicy(ProfileTypeRepository profileTypeRepository, Context context) {
        this.profileTypes = profileTypeRepository;
        this.context = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x0156 -> B:59:0x015a). Please report as a decompilation issue!!! */
    @Override // com.android.systemui.screenshot.policy.CapturePolicy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object check(DisplayContentModel displayContentModel, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        Iterator it;
        ActivityTaskManager.RootTaskInfo rootTaskInfo;
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
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Object obj2 = anonymousClass1.L$2;
            it = (Iterator) anonymousClass1.L$1;
            WorkProfilePolicy workProfilePolicy = (WorkProfilePolicy) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            if (obj != ProfileType.WORK) {
                this = workProfilePolicy;
                if (it.hasNext()) {
                    obj2 = null;
                } else {
                    Object next = it.next();
                    ChildTaskModel childTaskModel = (ChildTaskModel) ((Pair) next).component2();
                    ProfileTypeRepository profileTypeRepository = this.profileTypes;
                    int i3 = childTaskModel.userId;
                    anonymousClass1.L$0 = this;
                    anonymousClass1.L$1 = it;
                    anonymousClass1.L$2 = next;
                    anonymousClass1.label = 1;
                    Object profileType = ((ProfileTypeRepositoryImpl) profileTypeRepository).getProfileType(i3, anonymousClass1);
                    if (profileType == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    workProfilePolicy = this;
                    obj2 = next;
                    obj = profileType;
                    if (obj != ProfileType.WORK) {
                    }
                }
            }
            Pair pair = (Pair) obj2;
            if (pair == null) {
                return new CapturePolicy.PolicyResult.NotMatched("WorkProfile", "The top-most non-PINNED task does not belong to a work profile user");
            }
            ActivityTaskManager.RootTaskInfo rootTaskInfo2 = (ActivityTaskManager.RootTaskInfo) pair.component1();
            ChildTaskModel childTaskModel2 = (ChildTaskModel) pair.component2();
            CaptureType.IsolatedTask isolatedTask = new CaptureType.IsolatedTask(childTaskModel2.id, childTaskModel2.bounds);
            ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(childTaskModel2.name);
            if (componentNameUnflattenFromString == null) {
                componentNameUnflattenFromString = rootTaskInfo2.topActivity;
            }
            return new CapturePolicy.PolicyResult.Matched("WorkProfile", "The top-most non-PINNED task belongs to a work profile user", new LegacyCaptureParameters(isolatedTask, componentNameUnflattenFromString, UserHandle.of(childTaskModel2.userId)));
        }
        ResultKt.throwOnFailure(obj);
        if (displayContentModel.systemUiState.shadeExpanded) {
            return new CapturePolicy.PolicyResult.NotMatched("WorkProfile", "Notification shade is expanded");
        }
        if (DesktopModeStatus.canEnterDesktopMode(this.context) && (rootTaskInfo = (ActivityTaskManager.RootTaskInfo) CollectionsKt___CollectionsKt.firstOrNull(displayContentModel.rootTasks)) != null && rootTaskInfo.getWindowingMode() == 5) {
            return new CapturePolicy.PolicyResult.NotMatched("WorkProfile", "enable_desktop_windowing_mode is enabled and top RootTask has WINDOWING_MODE_FREEFORM");
        }
        if (new MultiWindowManager().isFlexPanelRunning()) {
            return new CapturePolicy.PolicyResult.NotMatched("WorkProfile", "flex panle mode is running");
        }
        int multiSplitFlags = new MultiWindowManager().getMultiSplitFlags();
        ListPopupWindow$$ExternalSyntheticOutline0.m(multiSplitFlags, "isInSplitWindow : ", "Screenshot");
        if ((multiSplitFlags & 56) != 8) {
            return new CapturePolicy.PolicyResult.NotMatched("WorkProfile", "split window mode is running");
        }
        List list = displayContentModel.rootTasks;
        ArrayList arrayList = new ArrayList();
        for (Object obj3 : list) {
            ActivityTaskManager.RootTaskInfo rootTaskInfo3 = (ActivityTaskManager.RootTaskInfo) obj3;
            if (rootTaskInfo3.isVisible && rootTaskInfo3.getWindowingMode() != 2 && rootTaskInfo3.childTaskUserIds.length != 0) {
                arrayList.add(obj3);
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj4 = arrayList.get(i4);
            i4++;
            ActivityTaskManager.RootTaskInfo rootTaskInfo4 = (ActivityTaskManager.RootTaskInfo) obj4;
            int length = rootTaskInfo4.childTaskIds.length - 1;
            IntProgression.Companion.getClass();
            TransformingSequence.AnonymousClass1 anonymousClass12 = new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(new IntProgression(length, 0, -1)), new RootTaskInfoExtKt$$ExternalSyntheticLambda0(rootTaskInfo4)).new AnonymousClass1();
            if (!anonymousClass12.iterator.hasNext()) {
                throw new NoSuchElementException("Sequence is empty.");
            }
            arrayList2.add(new Pair(rootTaskInfo4, anonymousClass12.next()));
        }
        it = arrayList2.iterator();
        if (it.hasNext()) {
        }
    }
}
