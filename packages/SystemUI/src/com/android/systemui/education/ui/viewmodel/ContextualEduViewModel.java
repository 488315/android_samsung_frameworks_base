package com.android.systemui.education.ui.viewmodel;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.contextualeducation.GestureType;
import com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor;
import com.android.systemui.education.shared.model.EducationInfo;
import com.android.systemui.education.shared.model.EducationUiType;
import com.android.systemui.education.ui.viewmodel.ContextualEduViewModel;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
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

/* loaded from: classes2.dex */
public final class ContextualEduViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ChannelFlowTransformLatest eduContent;
    public final Resources resources;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object contextualEduToastViewModel;
                    int i;
                    int i2;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i3 = anonymousClass1.label;
                        if ((i3 & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i3 - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i4 = anonymousClass1.label;
                    if (i4 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        EducationInfo educationInfo = (EducationInfo) obj;
                        EducationUiType educationUiType = educationInfo.educationUiType;
                        EducationUiType educationUiType2 = EducationUiType.Notification;
                        ContextualEduViewModel contextualEduViewModel = this.this$0;
                        int i5 = educationInfo.userId;
                        GestureType gestureType = educationInfo.gestureType;
                        if (educationUiType == educationUiType2) {
                            int i6 = ContextualEduViewModel.$r8$clinit;
                            contextualEduViewModel.getClass();
                            int i7 = ContextualEduViewModel.WhenMappings.$EnumSwitchMapping$0[gestureType.ordinal()];
                            if (i7 == 1) {
                                i2 = R.string.back_edu_notification_title;
                            } else if (i7 == 2) {
                                i2 = R.string.home_edu_notification_title;
                            } else if (i7 == 3) {
                                i2 = R.string.overview_edu_notification_title;
                            } else {
                                if (i7 != 4) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                i2 = R.string.all_apps_edu_notification_title;
                            }
                            contextualEduToastViewModel = new ContextualEduNotificationViewModel(contextualEduViewModel.resources.getString(i2), ContextualEduViewModel.access$getEduContent(contextualEduViewModel, educationInfo), gestureType, i5);
                        } else {
                            String strAccess$getEduContent = ContextualEduViewModel.access$getEduContent(contextualEduViewModel, educationInfo);
                            int i8 = ContextualEduViewModel.WhenMappings.$EnumSwitchMapping$0[gestureType.ordinal()];
                            if (i8 == 1) {
                                i = R.drawable.contextual_edu_swipe_back;
                            } else if (i8 == 2 || i8 == 3) {
                                i = R.drawable.contextual_edu_swipe_up;
                            } else {
                                if (i8 != 4) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                i = R.drawable.contextual_edu_all_apps;
                            }
                            contextualEduToastViewModel = new ContextualEduToastViewModel(strAccess$getEduContent, i, i5);
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(contextualEduToastViewModel, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i4 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
