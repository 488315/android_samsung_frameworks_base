package com.android.systemui.screenshot.policy;

import android.app.ActivityTaskManager;
import android.os.UserHandle;
import com.android.systemui.screenshot.data.model.ChildTaskModel;
import com.android.systemui.screenshot.data.model.DisplayContentModel;
import com.android.systemui.screenshot.data.model.ProfileType;
import com.android.systemui.screenshot.data.repository.ProfileTypeRepository;
import com.android.systemui.screenshot.data.repository.ProfileTypeRepositoryImpl;
import com.android.systemui.screenshot.policy.CapturePolicy;
import com.android.systemui.screenshot.policy.CaptureType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntProgression;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence.AnonymousClass1;

/* loaded from: classes2.dex */
public final class PrivateProfilePolicy implements CapturePolicy {
    public final ProfileTypeRepository profileTypes;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.screenshot.policy.PrivateProfilePolicy$check$1, reason: invalid class name */
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
            return PrivateProfilePolicy.this.check(null, this);
        }
    }

    static {
        new Companion(null);
    }

    public PrivateProfilePolicy(ProfileTypeRepository profileTypeRepository) {
        this.profileTypes = profileTypeRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b1 A[PHI: r2 r6 r7 r11
      0x00b1: PHI (r2v9 java.util.Iterator) = (r2v7 java.util.Iterator), (r2v10 java.util.Iterator) binds: [B:27:0x0082, B:34:0x00d9] A[DONT_GENERATE, DONT_INLINE]
      0x00b1: PHI (r6v9 com.android.systemui.screenshot.data.model.DisplayContentModel) = 
      (r6v7 com.android.systemui.screenshot.data.model.DisplayContentModel)
      (r6v10 com.android.systemui.screenshot.data.model.DisplayContentModel)
     binds: [B:27:0x0082, B:34:0x00d9] A[DONT_GENERATE, DONT_INLINE]
      0x00b1: PHI (r7v8 com.android.systemui.screenshot.policy.PrivateProfilePolicy) = 
      (r7v6 com.android.systemui.screenshot.policy.PrivateProfilePolicy)
      (r7v9 com.android.systemui.screenshot.policy.PrivateProfilePolicy)
     binds: [B:27:0x0082, B:34:0x00d9] A[DONT_GENERATE, DONT_INLINE]
      0x00b1: PHI (r11v13 java.util.Iterator) = (r11v3 java.util.Iterator), (r11v14 java.util.Iterator) binds: [B:27:0x0082, B:34:0x00d9] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x0082 -> B:28:0x00b1). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x00d4 -> B:33:0x00d7). Please report as a decompilation issue!!! */
    @Override // com.android.systemui.screenshot.policy.CapturePolicy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object check(DisplayContentModel displayContentModel, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        Iterator it;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object profileType = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        ChildTaskModel childTaskModel = null;
        if (i2 == 0) {
            ResultKt.throwOnFailure(profileType);
            if (displayContentModel.systemUiState.shadeExpanded) {
                return new CapturePolicy.PolicyResult.NotMatched("PrivateProfile", "Notification shade is expanded");
            }
            List list = displayContentModel.rootTasks;
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (((ActivityTaskManager.RootTaskInfo) obj).isVisible) {
                    arrayList.add(obj);
                }
            }
            it = arrayList.iterator();
            if (it.hasNext()) {
            }
            if (childTaskModel == null) {
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Object next = anonymousClass1.L$4;
            Iterator it2 = (Iterator) anonymousClass1.L$3;
            Iterator it3 = (Iterator) anonymousClass1.L$2;
            DisplayContentModel displayContentModel2 = (DisplayContentModel) anonymousClass1.L$1;
            PrivateProfilePolicy privateProfilePolicy = (PrivateProfilePolicy) anonymousClass1.L$0;
            ResultKt.throwOnFailure(profileType);
            if (profileType != ProfileType.PRIVATE) {
                if (it2.hasNext()) {
                    next = null;
                } else {
                    next = it2.next();
                    ProfileTypeRepository profileTypeRepository = privateProfilePolicy.profileTypes;
                    int i3 = ((ChildTaskModel) next).userId;
                    anonymousClass1.L$0 = privateProfilePolicy;
                    anonymousClass1.L$1 = displayContentModel2;
                    anonymousClass1.L$2 = it3;
                    anonymousClass1.L$3 = it2;
                    anonymousClass1.L$4 = next;
                    anonymousClass1.label = 1;
                    profileType = ((ProfileTypeRepositoryImpl) profileTypeRepository).getProfileType(i3, anonymousClass1);
                    if (profileType == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    if (profileType != ProfileType.PRIVATE) {
                    }
                }
            }
            it = it3;
            displayContentModel = displayContentModel2;
            ChildTaskModel childTaskModel2 = (ChildTaskModel) next;
            if (childTaskModel2 != null) {
                childTaskModel = childTaskModel2;
                if (childTaskModel == null) {
                    return new CapturePolicy.PolicyResult.NotMatched("PrivateProfile", "No private profile tasks are visible");
                }
                CaptureType.FullScreen fullScreen = new CaptureType.FullScreen(displayContentModel.displayId);
                for (ActivityTaskManager.RootTaskInfo rootTaskInfo : displayContentModel.rootTasks) {
                    if (rootTaskInfo.isVisible) {
                        return new CapturePolicy.PolicyResult.Matched("PrivateProfile", "At least one private profile task is visible", new LegacyCaptureParameters(fullScreen, rootTaskInfo.topActivity, UserHandle.of(childTaskModel.userId)));
                    }
                }
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
            this = privateProfilePolicy;
            if (it.hasNext()) {
                ActivityTaskManager.RootTaskInfo rootTaskInfo2 = (ActivityTaskManager.RootTaskInfo) it.next();
                int length = rootTaskInfo2.childTaskIds.length - 1;
                IntProgression.Companion.getClass();
                TransformingSequence.AnonymousClass1 anonymousClass12 = new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(new IntProgression(length, 0, -1)), new RootTaskInfoExtKt$$ExternalSyntheticLambda0(rootTaskInfo2)).new AnonymousClass1();
                displayContentModel2 = displayContentModel;
                it2 = anonymousClass12;
                privateProfilePolicy = this;
                it3 = it;
                if (it2.hasNext()) {
                }
            }
            if (childTaskModel == null) {
            }
        }
    }
}
