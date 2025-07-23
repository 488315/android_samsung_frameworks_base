package com.android.systemui.education.ui.viewmodel;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.contextualeducation.GestureType;
import com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor;
import com.android.systemui.education.shared.model.EducationInfo;
import com.android.systemui.education.shared.model.EducationUiType;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ContextualEduViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ChannelFlowTransformLatest eduContent;
    public final Resources resources;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[GestureType.values().length];
            try {
                iArr[GestureType.BACK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[GestureType.HOME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[GestureType.OVERVIEW.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[GestureType.ALL_APPS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public ContextualEduViewModel(Resources resources, KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor, AccessibilityManagerWrapper accessibilityManagerWrapper) {
        this.resources = resources;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(keyboardTouchpadEduInteractor.educationTriggered);
        this.eduContent = FlowKt.transformLatest(new Flow() { // from class: com.android.systemui.education.ui.viewmodel.ContextualEduViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.education.ui.viewmodel.ContextualEduViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ContextualEduViewModel this$0;

                /* renamed from: com.android.systemui.education.ui.viewmodel.ContextualEduViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, ContextualEduViewModel contextualEduViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = contextualEduViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                    /*
                        r11 = this;
                        boolean r0 = r13 instanceof com.android.systemui.education.ui.viewmodel.ContextualEduViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r13
                        com.android.systemui.education.ui.viewmodel.ContextualEduViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.education.ui.viewmodel.ContextualEduViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.education.ui.viewmodel.ContextualEduViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.education.ui.viewmodel.ContextualEduViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r13)
                    L18:
                        java.lang.Object r13 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r13)
                        goto Lb3
                    L28:
                        java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                        java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                        r11.<init>(r12)
                        throw r11
                    L30:
                        kotlin.ResultKt.throwOnFailure(r13)
                        com.android.systemui.education.shared.model.EducationInfo r12 = (com.android.systemui.education.shared.model.EducationInfo) r12
                        com.android.systemui.education.shared.model.EducationUiType r13 = r12.educationUiType
                        com.android.systemui.education.shared.model.EducationUiType r2 = com.android.systemui.education.shared.model.EducationUiType.Notification
                        r4 = 4
                        r5 = 3
                        r6 = 2
                        com.android.systemui.education.ui.viewmodel.ContextualEduViewModel r7 = r11.this$0
                        int r8 = r12.userId
                        com.android.systemui.contextualeducation.GestureType r9 = r12.gestureType
                        if (r13 != r2) goto L7e
                        com.android.systemui.education.ui.viewmodel.ContextualEduNotificationViewModel r13 = new com.android.systemui.education.ui.viewmodel.ContextualEduNotificationViewModel
                        int r2 = com.android.systemui.education.ui.viewmodel.ContextualEduViewModel.$r8$clinit
                        r7.getClass()
                        int[] r2 = com.android.systemui.education.ui.viewmodel.ContextualEduViewModel.WhenMappings.$EnumSwitchMapping$0
                        int r10 = r9.ordinal()
                        r2 = r2[r10]
                        if (r2 == r3) goto L6d
                        if (r2 == r6) goto L69
                        if (r2 == r5) goto L65
                        if (r2 != r4) goto L5f
                        r2 = 2131952068(0x7f1301c4, float:1.9540568E38)
                        goto L70
                    L5f:
                        kotlin.NoWhenBranchMatchedException r11 = new kotlin.NoWhenBranchMatchedException
                        r11.<init>()
                        throw r11
                    L65:
                        r2 = 2131955420(0x7f130edc, float:1.9547367E38)
                        goto L70
                    L69:
                        r2 = 2131953741(0x7f13084d, float:1.9543962E38)
                        goto L70
                    L6d:
                        r2 = 2131952138(0x7f13020a, float:1.954071E38)
                    L70:
                        android.content.res.Resources r4 = r7.resources
                        java.lang.String r2 = r4.getString(r2)
                        java.lang.String r12 = com.android.systemui.education.ui.viewmodel.ContextualEduViewModel.access$getEduContent(r7, r12)
                        r13.<init>(r2, r12, r9, r8)
                        goto La8
                    L7e:
                        com.android.systemui.education.ui.viewmodel.ContextualEduToastViewModel r13 = new com.android.systemui.education.ui.viewmodel.ContextualEduToastViewModel
                        java.lang.String r12 = com.android.systemui.education.ui.viewmodel.ContextualEduViewModel.access$getEduContent(r7, r12)
                        int[] r2 = com.android.systemui.education.ui.viewmodel.ContextualEduViewModel.WhenMappings.$EnumSwitchMapping$0
                        int r7 = r9.ordinal()
                        r2 = r2[r7]
                        if (r2 == r3) goto La2
                        if (r2 == r6) goto L9e
                        if (r2 == r5) goto L9e
                        if (r2 != r4) goto L98
                        r2 = 2131232483(0x7f0806e3, float:1.8081077E38)
                        goto La5
                    L98:
                        kotlin.NoWhenBranchMatchedException r11 = new kotlin.NoWhenBranchMatchedException
                        r11.<init>()
                        throw r11
                    L9e:
                        r2 = 2131232486(0x7f0806e6, float:1.8081083E38)
                        goto La5
                    La2:
                        r2 = 2131232485(0x7f0806e5, float:1.808108E38)
                    La5:
                        r13.<init>(r12, r2, r8)
                    La8:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r11 = r11.$this_unsafeFlow
                        java.lang.Object r11 = r11.emit(r13, r0)
                        if (r11 != r1) goto Lb3
                        return r1
                    Lb3:
                        kotlin.Unit r11 = kotlin.Unit.INSTANCE
                        return r11
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.education.ui.viewmodel.ContextualEduViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new ContextualEduViewModel$timeout$$inlined$flatMapLatest$1(null, accessibilityManagerWrapper.mAccessibilityManager.getRecommendedTimeoutMillis(5000, 2), null));
    }

    public static final String access$getEduContent(ContextualEduViewModel contextualEduViewModel, EducationInfo educationInfo) {
        int i;
        contextualEduViewModel.getClass();
        EducationUiType educationUiType = educationInfo.educationUiType;
        EducationUiType educationUiType2 = EducationUiType.Notification;
        GestureType gestureType = educationInfo.gestureType;
        if (educationUiType == educationUiType2) {
            int i2 = WhenMappings.$EnumSwitchMapping$0[gestureType.ordinal()];
            if (i2 == 1) {
                i = R.string.back_edu_notification_content;
            } else if (i2 == 2) {
                i = R.string.home_edu_notification_content;
            } else if (i2 == 3) {
                i = R.string.overview_edu_notification_content;
            } else {
                if (i2 != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                i = R.string.all_apps_edu_notification_content;
            }
        } else {
            int i3 = WhenMappings.$EnumSwitchMapping$0[gestureType.ordinal()];
            if (i3 == 1) {
                i = R.string.back_edu_toast_content;
            } else if (i3 == 2) {
                i = R.string.home_edu_toast_content;
            } else if (i3 == 3) {
                i = R.string.overview_edu_toast_content;
            } else {
                if (i3 != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                i = R.string.all_apps_edu_toast_content;
            }
        }
        return contextualEduViewModel.resources.getString(i);
    }
}
