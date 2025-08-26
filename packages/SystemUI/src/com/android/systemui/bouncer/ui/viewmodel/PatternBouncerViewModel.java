package com.android.systemui.bouncer.ui.viewmodel;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.bouncer.ui.UpdateInteractor;
import com.android.systemui.R;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.authentication.shared.model.AuthenticationPatternCoordinate;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class PatternBouncerViewModel extends AuthMethodBouncerViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _currentDot;
    public final StateFlowImpl _dotColor;
    public final StateFlowImpl _dots;
    public final StateFlowImpl _isWhiteBg;
    public final Context applicationContext;
    public final AuthenticationMethodModel.Pattern authenticationMethod;
    public final int columnCount;
    public final ReadonlyStateFlow currentDot;
    public final StateFlowImpl dotColor;
    public final ReadonlyStateFlow dots;
    public final Lazy hitFactor$delegate;
    public final ReadonlyStateFlow isPatternVisible;
    public final ReadonlyStateFlow isWhiteBg;
    public final StateFlowImpl lineColor;
    public final Function0 onIntentionalUserInput;
    public final int rowCount;
    public final StateFlowImpl selectedDotList;
    public final StateFlowImpl selectedDotSet;
    public final ReadonlyStateFlow selectedDots;
    public final WallpaperManager wallpaperManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        PatternBouncerViewModel create(BouncerHapticPlayer bouncerHapticPlayer, StateFlow stateFlow, Function0 function0);
    }

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PatternBouncerViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PatternBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(PatternBouncerViewModel patternBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = patternBouncerViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
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
                    PatternBouncerViewModel patternBouncerViewModel = this.this$0;
                    this.label = 1;
                    int i2 = PatternBouncerViewModel.$r8$clinit;
                    patternBouncerViewModel.getClass();
                    if (AuthMethodBouncerViewModel.onActivated$suspendImpl(patternBouncerViewModel, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C01542 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PatternBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01542(PatternBouncerViewModel patternBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = patternBouncerViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01542(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01542) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final StateFlowImpl stateFlowImpl = this.this$0.selectedDotSet;
                    Flow flow = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$2$2$invokeSuspend$$inlined$map$1

                        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$2$2$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$2$2$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                            public AnonymousClass2(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                if (continuation instanceof AnonymousClass1) {
                                    anonymousClass1 = (AnonymousClass1) continuation;
                                    int i = anonymousClass1.label;
                                    if ((i & Integer.MIN_VALUE) != 0) {
                                        anonymousClass1.label = i - Integer.MIN_VALUE;
                                    } else {
                                        anonymousClass1 = new AnonymousClass1(continuation);
                                    }
                                }
                                Object obj2 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i2 = anonymousClass1.label;
                                if (i2 == 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    List list = CollectionsKt___CollectionsKt.toList((LinkedHashSet) obj);
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(list, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (i2 != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object objCollect = stateFlowImpl.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                    final PatternBouncerViewModel patternBouncerViewModel = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel.onActivated.2.2.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            patternBouncerViewModel.selectedDotList.setValue(CollectionsKt___CollectionsKt.toList((List) obj2));
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PatternBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(PatternBouncerViewModel patternBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = patternBouncerViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.this$0, continuation);
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
                    final PatternBouncerViewModel patternBouncerViewModel = this.this$0;
                    ReadonlyStateFlow readonlyStateFlow = patternBouncerViewModel.isWhiteBg;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel.onActivated.2.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Boolean) obj2).getClass();
                            PatternBouncerViewModel patternBouncerViewModel2 = patternBouncerViewModel;
                            patternBouncerViewModel2._dotColor.updateState(null, Color.m456boximpl(((Boolean) patternBouncerViewModel2.isWhiteBg.$$delegate_0.getValue()).booleanValue() ? ColorKt.Color(patternBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_pattern_dot_whitebg_color)) : ColorKt.Color(patternBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_pattern_dot_color))));
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$2$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PatternBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(PatternBouncerViewModel patternBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = patternBouncerViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0._isWhiteBg.updateState(null, Boolean.valueOf(this.this$0.wallpaperManager.semGetWallpaperColors(10).get(512L).getFontColor() == 1));
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = PatternBouncerViewModel.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(PatternBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C01542(PatternBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(PatternBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(PatternBouncerViewModel.this, null), 7);
                this.label = 1;
                if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
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

    static {
        new Companion(null);
    }

    public PatternBouncerViewModel(Context context, BouncerInteractor bouncerInteractor, BouncerHapticPlayer bouncerHapticPlayer, StateFlow stateFlow, Function0 function0, UpdateInteractor updateInteractor, WallpaperManager wallpaperManager) {
        super(bouncerInteractor, stateFlow, "PatternBouncerViewModel", bouncerHapticPlayer, null);
        this.applicationContext = context;
        this.onIntentionalUserInput = function0;
        this.wallpaperManager = wallpaperManager;
        this.columnCount = 3;
        this.rowCount = 3;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(new LinkedHashSet());
        this.selectedDotSet = stateFlowImplMutableStateFlow;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(CollectionsKt___CollectionsKt.toList((Iterable) stateFlowImplMutableStateFlow.getValue()));
        this.selectedDotList = stateFlowImplMutableStateFlow2;
        this.selectedDots = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(null);
        this._currentDot = stateFlowImplMutableStateFlow3;
        this.currentDot = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(defaultDots());
        this._dots = stateFlowImplMutableStateFlow4;
        this.dots = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        this.isPatternVisible = bouncerInteractor.isPatternVisible;
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isWhiteBg = stateFlowImplMutableStateFlow5;
        this.isWhiteBg = FlowKt.asStateFlow(stateFlowImplMutableStateFlow5);
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow(Color.m456boximpl(ColorKt.Color(context.getColor(R.color.kg_compose_pattern_dot_color))));
        this._dotColor = stateFlowImplMutableStateFlow6;
        this.dotColor = stateFlowImplMutableStateFlow6;
        this.lineColor = stateFlowImplMutableStateFlow6;
        this.authenticationMethod = AuthenticationMethodModel.Pattern.INSTANCE;
        this.hitFactor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws Resources.NotFoundException {
                int i = PatternBouncerViewModel.$r8$clinit;
                TypedValue typedValue = new TypedValue();
                this.f$0.applicationContext.getResources().getValue(android.R.dimen.notification_verification_icon_size, typedValue, true);
                return Float.valueOf(Math.max(Math.min(typedValue.getFloat(), 1.0f), 0.2f));
            }
        });
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final void clearInput() {
        this._dots.setValue(defaultDots());
        this._currentDot.setValue(null);
        this.selectedDotSet.updateState(null, new LinkedHashSet());
    }

    public final ListBuilder defaultDots() {
        ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        IntProgressionIterator it = RangesKt___RangesKt.until(0, this.columnCount).iterator();
        while (it.hasNext) {
            int iNextInt = it.nextInt();
            IntProgressionIterator it2 = RangesKt___RangesKt.until(0, this.rowCount).iterator();
            while (it2.hasNext) {
                listBuilderCreateListBuilder.add(new PatternDotViewModel(iNextInt, it2.nextInt()));
            }
        }
        return listBuilderCreateListBuilder.build();
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final AuthenticationMethodModel getAuthenticationMethod() {
        return this.authenticationMethod;
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final List getInput() {
        Iterable<PatternDotViewModel> iterable = (Iterable) this.selectedDotSet.getValue();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
        for (PatternDotViewModel patternDotViewModel : iterable) {
            arrayList.add(new AuthenticationPatternCoordinate(patternDotViewModel.x, patternDotViewModel.y));
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel, com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
            anonymousClass1.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass2, anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
