package com.android.systemui.keyguard.ui.binder;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1", m277f = "KeyguardSecBottomAreaViewBinder.kt", m278l = {134}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ View $ambientIndicationArea;
    final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
    final /* synthetic */ KeyguardSecAffordanceView $endButton;
    final /* synthetic */ View $indicationArea;
    final /* synthetic */ View $leftShortcutArea;
    final /* synthetic */ View $rightShortcutArea;
    final /* synthetic */ KeyguardSecAffordanceView $startButton;
    final /* synthetic */ KeyguardIndicationTextView $upperFPIndication;
    final /* synthetic */ LinearLayout $usimTextArea;
    final /* synthetic */ KeyguardSecBottomAreaView $view;
    final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1", m277f = "KeyguardSecBottomAreaViewBinder.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1 */
    final class C17461 extends SuspendLambda implements Function2 {
        final /* synthetic */ View $ambientIndicationArea;
        final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
        final /* synthetic */ KeyguardSecAffordanceView $endButton;
        final /* synthetic */ View $indicationArea;
        final /* synthetic */ View $leftShortcutArea;
        final /* synthetic */ View $rightShortcutArea;
        final /* synthetic */ KeyguardSecAffordanceView $startButton;
        final /* synthetic */ KeyguardIndicationTextView $upperFPIndication;
        final /* synthetic */ LinearLayout $usimTextArea;
        final /* synthetic */ KeyguardSecBottomAreaView $view;
        final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$1", m277f = "KeyguardSecBottomAreaViewBinder.kt", m278l = {136}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecAffordanceView $startButton;
            final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, KeyguardSecAffordanceView keyguardSecAffordanceView, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBottomAreaViewModel;
                this.$startButton = keyguardSecAffordanceView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, this.$startButton, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ChannelFlowTransformLatest channelFlowTransformLatest = this.$viewModel.startButton;
                    final KeyguardSecAffordanceView keyguardSecAffordanceView = this.$startButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder.bind.disposableHandle.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel = (KeyguardQuickAffordanceViewModel) obj2;
                            KeyguardSecBottomAreaViewBinder.INSTANCE.getClass();
                            boolean z = keyguardQuickAffordanceViewModel.isActivated;
                            KeyguardSecAffordanceView keyguardSecAffordanceView2 = KeyguardSecAffordanceView.this;
                            keyguardSecAffordanceView2.setActivated(z);
                            keyguardSecAffordanceView2.setClickable(keyguardQuickAffordanceViewModel.isClickable);
                            keyguardSecAffordanceView2.setSelected(keyguardQuickAffordanceViewModel.isSelected);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (channelFlowTransformLatest.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$2", m277f = "KeyguardSecBottomAreaViewBinder.kt", m278l = {145}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecAffordanceView $endButton;
            final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, KeyguardSecAffordanceView keyguardSecAffordanceView, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBottomAreaViewModel;
                this.$endButton = keyguardSecAffordanceView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$endButton, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ChannelFlowTransformLatest channelFlowTransformLatest = this.$viewModel.endButton;
                    final KeyguardSecAffordanceView keyguardSecAffordanceView = this.$endButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder.bind.disposableHandle.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel = (KeyguardQuickAffordanceViewModel) obj2;
                            KeyguardSecBottomAreaViewBinder.INSTANCE.getClass();
                            boolean z = keyguardQuickAffordanceViewModel.isActivated;
                            KeyguardSecAffordanceView keyguardSecAffordanceView2 = KeyguardSecAffordanceView.this;
                            keyguardSecAffordanceView2.setActivated(z);
                            keyguardSecAffordanceView2.setClickable(keyguardQuickAffordanceViewModel.isClickable);
                            keyguardSecAffordanceView2.setSelected(keyguardQuickAffordanceViewModel.isSelected);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (channelFlowTransformLatest.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$3", m277f = "KeyguardSecBottomAreaViewBinder.kt", m278l = {154}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $ambientIndicationArea;
            final /* synthetic */ View $indicationArea;
            final /* synthetic */ KeyguardSecBottomAreaView $view;
            final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, KeyguardSecBottomAreaView keyguardSecBottomAreaView, View view, View view2, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBottomAreaViewModel;
                this.$view = keyguardSecBottomAreaView;
                this.$ambientIndicationArea = view;
                this.$indicationArea = view2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$view, this.$ambientIndicationArea, this.$indicationArea, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ChannelFlowTransformLatest channelFlowTransformLatest = this.$viewModel.alpha;
                    final KeyguardSecBottomAreaView keyguardSecBottomAreaView = this.$view;
                    final View view = this.$ambientIndicationArea;
                    final View view2 = this.$indicationArea;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder.bind.disposableHandle.1.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            float floatValue = ((Number) obj2).floatValue();
                            KeyguardSecBottomAreaView.this.setImportantForAccessibility((floatValue > 0.0f ? 1 : (floatValue == 0.0f ? 0 : -1)) == 0 ? 4 : 0);
                            View view3 = view;
                            if (view3 != null) {
                                view3.setAlpha(floatValue);
                            }
                            view2.setAlpha(floatValue);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (channelFlowTransformLatest.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$4", m277f = "KeyguardSecBottomAreaViewBinder.kt", m278l = {168}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $ambientIndicationArea;
            final /* synthetic */ View $indicationArea;
            final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, View view, View view2, Continuation<? super AnonymousClass4> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBottomAreaViewModel;
                this.$indicationArea = view;
                this.$ambientIndicationArea = view2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$viewModel, this.$indicationArea, this.$ambientIndicationArea, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$viewModel.indicationAreaTranslationX;
                    final View view = this.$indicationArea;
                    final View view2 = this.$ambientIndicationArea;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder.bind.disposableHandle.1.1.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            float floatValue = ((Number) obj2).floatValue();
                            View view3 = view2;
                            if (view3 != null) {
                                view3.setTranslationX(floatValue);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$5", m277f = "KeyguardSecBottomAreaViewBinder.kt", m278l = {187}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
            final /* synthetic */ View $indicationArea;
            final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
            int label;

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$5$2", m277f = "KeyguardSecBottomAreaViewBinder.kt", m278l = {}, m279m = "invokeSuspend")
            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$5$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function3 {
                /* synthetic */ int I$0;
                /* synthetic */ boolean Z$0;
                int label;

                public AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
                    super(3, continuation);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    int intValue = ((Number) obj2).intValue();
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2((Continuation) obj3);
                    anonymousClass2.Z$0 = booleanValue;
                    anonymousClass2.I$0 = intValue;
                    return anonymousClass2.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    boolean z = this.Z$0;
                    int i = this.I$0;
                    if (!z) {
                        i = 0;
                    }
                    return new Integer(i);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, MutableStateFlow mutableStateFlow, View view, Continuation<? super AnonymousClass5> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBottomAreaViewModel;
                this.$configurationBasedDimensions = mutableStateFlow;
                this.$indicationArea = view;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.$viewModel, this.$configurationBasedDimensions, this.$indicationArea, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$viewModel.isIndicationAreaPadded;
                    final MutableStateFlow mutableStateFlow = this.$configurationBasedDimensions;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, new Flow() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$5$invokeSuspend$$inlined$map$1

                        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$5$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                            @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$5$invokeSuspend$$inlined$map$1$2", m277f = "KeyguardSecBottomAreaViewBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$5$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                    this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                int i;
                                if (continuation instanceof AnonymousClass1) {
                                    anonymousClass1 = (AnonymousClass1) continuation;
                                    int i2 = anonymousClass1.label;
                                    if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                                        anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                                        Object obj2 = anonymousClass1.result;
                                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                        i = anonymousClass1.label;
                                        if (i != 0) {
                                            ResultKt.throwOnFailure(obj2);
                                            Integer num = new Integer(((KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions) obj).indicationAreaPaddingPx);
                                            anonymousClass1.label = 1;
                                            if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                                                return coroutineSingletons;
                                            }
                                        } else {
                                            if (i != 1) {
                                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                            }
                                            ResultKt.throwOnFailure(obj2);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }
                                anonymousClass1 = new AnonymousClass1(continuation);
                                Object obj22 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                                i = anonymousClass1.label;
                                if (i != 0) {
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    }, new AnonymousClass2(null));
                    final View view = this.$indicationArea;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder.bind.disposableHandle.1.1.5.3
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int intValue = ((Number) obj2).intValue();
                            view.setPadding(intValue, 0, intValue, 0);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$6", m277f = "KeyguardSecBottomAreaViewBinder.kt", m278l = {198}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $ambientIndicationArea;
            final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
            final /* synthetic */ View $indicationArea;
            final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(MutableStateFlow mutableStateFlow, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, View view, View view2, Continuation<? super AnonymousClass6> continuation) {
                super(2, continuation);
                this.$configurationBasedDimensions = mutableStateFlow;
                this.$viewModel = keyguardBottomAreaViewModel;
                this.$indicationArea = view;
                this.$ambientIndicationArea = view2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass6(this.$configurationBasedDimensions, this.$viewModel, this.$indicationArea, this.$ambientIndicationArea, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final MutableStateFlow mutableStateFlow = this.$configurationBasedDimensions;
                    ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(new Flow() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$6$invokeSuspend$$inlined$map$1

                        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$6$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                            @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$6$invokeSuspend$$inlined$map$1$2", m277f = "KeyguardSecBottomAreaViewBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$6$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                    this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                int i;
                                if (continuation instanceof AnonymousClass1) {
                                    anonymousClass1 = (AnonymousClass1) continuation;
                                    int i2 = anonymousClass1.label;
                                    if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                                        anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                                        Object obj2 = anonymousClass1.result;
                                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                        i = anonymousClass1.label;
                                        if (i != 0) {
                                            ResultKt.throwOnFailure(obj2);
                                            Integer num = new Integer(((KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions) obj).defaultBurnInPreventionYOffsetPx);
                                            anonymousClass1.label = 1;
                                            if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                                                return coroutineSingletons;
                                            }
                                        } else {
                                            if (i != 1) {
                                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                            }
                                            ResultKt.throwOnFailure(obj2);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }
                                anonymousClass1 = new AnonymousClass1(continuation);
                                Object obj22 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                                i = anonymousClass1.label;
                                if (i != 0) {
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    }, new C1744x5446f995(null, this.$viewModel));
                    final View view = this.$indicationArea;
                    final View view2 = this.$ambientIndicationArea;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder.bind.disposableHandle.1.1.6.3
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            float floatValue = ((Number) obj2).floatValue();
                            View view3 = view2;
                            if (view3 != null) {
                                view3.setTranslationY(floatValue);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (transformLatest.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$7", m277f = "KeyguardSecBottomAreaViewBinder.kt", m278l = {207}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$7, reason: invalid class name */
        final class AnonymousClass7 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
            final /* synthetic */ KeyguardSecAffordanceView $endButton;
            final /* synthetic */ View $indicationArea;
            final /* synthetic */ View $leftShortcutArea;
            final /* synthetic */ View $rightShortcutArea;
            final /* synthetic */ KeyguardSecAffordanceView $startButton;
            final /* synthetic */ KeyguardIndicationTextView $upperFPIndication;
            final /* synthetic */ LinearLayout $usimTextArea;
            final /* synthetic */ KeyguardSecBottomAreaView $view;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(MutableStateFlow mutableStateFlow, KeyguardSecAffordanceView keyguardSecAffordanceView, KeyguardSecAffordanceView keyguardSecAffordanceView2, View view, View view2, KeyguardSecBottomAreaView keyguardSecBottomAreaView, View view3, KeyguardIndicationTextView keyguardIndicationTextView, LinearLayout linearLayout, Continuation<? super AnonymousClass7> continuation) {
                super(2, continuation);
                this.$configurationBasedDimensions = mutableStateFlow;
                this.$startButton = keyguardSecAffordanceView;
                this.$endButton = keyguardSecAffordanceView2;
                this.$leftShortcutArea = view;
                this.$rightShortcutArea = view2;
                this.$view = keyguardSecBottomAreaView;
                this.$indicationArea = view3;
                this.$upperFPIndication = keyguardIndicationTextView;
                this.$usimTextArea = linearLayout;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass7(this.$configurationBasedDimensions, this.$startButton, this.$endButton, this.$leftShortcutArea, this.$rightShortcutArea, this.$view, this.$indicationArea, this.$upperFPIndication, this.$usimTextArea, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass7) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    MutableStateFlow mutableStateFlow = this.$configurationBasedDimensions;
                    final KeyguardSecAffordanceView keyguardSecAffordanceView = this.$startButton;
                    final KeyguardSecAffordanceView keyguardSecAffordanceView2 = this.$endButton;
                    final View view = this.$leftShortcutArea;
                    final View view2 = this.$rightShortcutArea;
                    final KeyguardSecBottomAreaView keyguardSecBottomAreaView = this.$view;
                    final View view3 = this.$indicationArea;
                    final KeyguardIndicationTextView keyguardIndicationTextView = this.$upperFPIndication;
                    final LinearLayout linearLayout = this.$usimTextArea;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder.bind.disposableHandle.1.1.7.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions configurationBasedDimensions = (KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions) obj2;
                            KeyguardSecAffordanceView keyguardSecAffordanceView3 = KeyguardSecAffordanceView.this;
                            ViewGroup.LayoutParams layoutParams = keyguardSecAffordanceView3.getLayoutParams();
                            if (layoutParams == null) {
                                throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
                            }
                            layoutParams.width = configurationBasedDimensions.buttonSizePx.getWidth();
                            layoutParams.height = configurationBasedDimensions.buttonSizePx.getHeight();
                            keyguardSecAffordanceView3.setLayoutParams(layoutParams);
                            KeyguardSecAffordanceView keyguardSecAffordanceView4 = keyguardSecAffordanceView2;
                            ViewGroup.LayoutParams layoutParams2 = keyguardSecAffordanceView4.getLayoutParams();
                            if (layoutParams2 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
                            }
                            layoutParams2.width = configurationBasedDimensions.buttonSizePx.getWidth();
                            layoutParams2.height = configurationBasedDimensions.buttonSizePx.getHeight();
                            keyguardSecAffordanceView4.setLayoutParams(layoutParams2);
                            View view4 = view;
                            ViewGroup.LayoutParams layoutParams3 = view4.getLayoutParams();
                            if (layoutParams3 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                            }
                            FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) layoutParams3;
                            layoutParams4.bottomMargin = configurationBasedDimensions.shortcutBottomMargin;
                            layoutParams4.setMarginStart(configurationBasedDimensions.shortcutSideMargin);
                            view4.setLayoutParams(layoutParams4);
                            View view5 = view2;
                            ViewGroup.LayoutParams layoutParams5 = view5.getLayoutParams();
                            if (layoutParams5 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                            }
                            FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) layoutParams5;
                            layoutParams6.bottomMargin = configurationBasedDimensions.shortcutBottomMargin;
                            layoutParams6.setMarginEnd(configurationBasedDimensions.shortcutSideMargin);
                            view5.setLayoutParams(layoutParams6);
                            KeyguardSecBottomAreaView keyguardSecBottomAreaView2 = keyguardSecBottomAreaView;
                            keyguardSecBottomAreaView2.getUpdateLeftAffordanceIcon().invoke();
                            keyguardSecBottomAreaView2.getUpdateRightAffordanceIcon().invoke();
                            View view6 = view3;
                            ViewGroup.LayoutParams layoutParams7 = view6.getLayoutParams();
                            if (layoutParams7 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                            }
                            FrameLayout.LayoutParams layoutParams8 = (FrameLayout.LayoutParams) layoutParams7;
                            layoutParams8.setMarginStart(configurationBasedDimensions.indicationAreaSideMargin);
                            layoutParams8.setMarginEnd(configurationBasedDimensions.indicationAreaSideMargin);
                            layoutParams8.bottomMargin = configurationBasedDimensions.indicationAreaBottomMargin;
                            int marginStart = layoutParams8.getMarginStart();
                            int marginEnd = layoutParams8.getMarginEnd();
                            int i2 = layoutParams8.bottomMargin;
                            StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("mIndicationArea margin start : ", marginStart, ", end : ", marginEnd, ", bottom : ");
                            m45m.append(i2);
                            Log.d("KeyguardSecBottomAreaViewBinder", m45m.toString());
                            view6.setLayoutParams(layoutParams8);
                            KeyguardIndicationTextView keyguardIndicationTextView2 = keyguardIndicationTextView;
                            ViewGroup.LayoutParams layoutParams9 = keyguardIndicationTextView2.getLayoutParams();
                            if (layoutParams9 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                            }
                            FrameLayout.LayoutParams layoutParams10 = (FrameLayout.LayoutParams) layoutParams9;
                            layoutParams10.bottomMargin = configurationBasedDimensions.upperFPIndicationBottomMargin;
                            keyguardIndicationTextView2.setLayoutParams(layoutParams10);
                            LinearLayout linearLayout2 = linearLayout;
                            ViewGroup.LayoutParams layoutParams11 = linearLayout2.getLayoutParams();
                            if (layoutParams11 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                            }
                            LinearLayout.LayoutParams layoutParams12 = (LinearLayout.LayoutParams) layoutParams11;
                            layoutParams12.bottomMargin = configurationBasedDimensions.usimTextAreaBottomMargin;
                            linearLayout2.setLayoutParams(layoutParams12);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (((StateFlowImpl) mutableStateFlow).collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C17461(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, KeyguardSecAffordanceView keyguardSecAffordanceView, KeyguardSecAffordanceView keyguardSecAffordanceView2, KeyguardSecBottomAreaView keyguardSecBottomAreaView, View view, View view2, MutableStateFlow mutableStateFlow, View view3, View view4, KeyguardIndicationTextView keyguardIndicationTextView, LinearLayout linearLayout, Continuation<? super C17461> continuation) {
            super(2, continuation);
            this.$viewModel = keyguardBottomAreaViewModel;
            this.$startButton = keyguardSecAffordanceView;
            this.$endButton = keyguardSecAffordanceView2;
            this.$view = keyguardSecBottomAreaView;
            this.$ambientIndicationArea = view;
            this.$indicationArea = view2;
            this.$configurationBasedDimensions = mutableStateFlow;
            this.$leftShortcutArea = view3;
            this.$rightShortcutArea = view4;
            this.$upperFPIndication = keyguardIndicationTextView;
            this.$usimTextArea = linearLayout;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C17461 c17461 = new C17461(this.$viewModel, this.$startButton, this.$endButton, this.$view, this.$ambientIndicationArea, this.$indicationArea, this.$configurationBasedDimensions, this.$leftShortcutArea, this.$rightShortcutArea, this.$upperFPIndication, this.$usimTextArea, continuation);
            c17461.L$0 = obj;
            return c17461;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C17461) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$viewModel, this.$startButton, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$endButton, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$view, this.$ambientIndicationArea, this.$indicationArea, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$indicationArea, this.$ambientIndicationArea, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$configurationBasedDimensions, this.$indicationArea, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass6(this.$configurationBasedDimensions, this.$viewModel, this.$indicationArea, this.$ambientIndicationArea, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass7(this.$configurationBasedDimensions, this.$startButton, this.$endButton, this.$leftShortcutArea, this.$rightShortcutArea, this.$view, this.$indicationArea, this.$upperFPIndication, this.$usimTextArea, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, KeyguardSecAffordanceView keyguardSecAffordanceView, KeyguardSecAffordanceView keyguardSecAffordanceView2, KeyguardSecBottomAreaView keyguardSecBottomAreaView, View view, View view2, MutableStateFlow mutableStateFlow, View view3, View view4, KeyguardIndicationTextView keyguardIndicationTextView, LinearLayout linearLayout, Continuation<? super KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1> continuation) {
        super(3, continuation);
        this.$viewModel = keyguardBottomAreaViewModel;
        this.$startButton = keyguardSecAffordanceView;
        this.$endButton = keyguardSecAffordanceView2;
        this.$view = keyguardSecBottomAreaView;
        this.$ambientIndicationArea = view;
        this.$indicationArea = view2;
        this.$configurationBasedDimensions = mutableStateFlow;
        this.$leftShortcutArea = view3;
        this.$rightShortcutArea = view4;
        this.$upperFPIndication = keyguardIndicationTextView;
        this.$usimTextArea = linearLayout;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1 keyguardSecBottomAreaViewBinder$bind$disposableHandle$1 = new KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1(this.$viewModel, this.$startButton, this.$endButton, this.$view, this.$ambientIndicationArea, this.$indicationArea, this.$configurationBasedDimensions, this.$leftShortcutArea, this.$rightShortcutArea, this.$upperFPIndication, this.$usimTextArea, (Continuation) obj3);
        keyguardSecBottomAreaViewBinder$bind$disposableHandle$1.L$0 = (LifecycleOwner) obj;
        return keyguardSecBottomAreaViewBinder$bind$disposableHandle$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            C17461 c17461 = new C17461(this.$viewModel, this.$startButton, this.$endButton, this.$view, this.$ambientIndicationArea, this.$indicationArea, this.$configurationBasedDimensions, this.$leftShortcutArea, this.$rightShortcutArea, this.$upperFPIndication, this.$usimTextArea, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c17461, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
