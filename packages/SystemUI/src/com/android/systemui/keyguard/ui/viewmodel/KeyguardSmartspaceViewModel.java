package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardSmartspaceInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;

/* loaded from: classes2.dex */
public final class KeyguardSmartspaceViewModel {
    public static final Companion Companion = new Companion(null);
    public final ReadonlyStateFlow bcSmartspaceVisibility;
    public final ReadonlyStateFlow isDateVisible;
    public final boolean isDateWeatherDecoupled;
    public final ReadonlyStateFlow isShadeLayoutWide;
    public final boolean isSmartspaceEnabled;
    public final ReadonlyStateFlow isWeatherEnabled;
    public final ReadonlyStateFlow isWeatherVisible;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public KeyguardSmartspaceViewModel(CoroutineScope coroutineScope, LockscreenSmartspaceController lockscreenSmartspaceController, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceInteractor keyguardSmartspaceInteractor, ShadeModeInteractor shadeModeInteractor) {
        this.isSmartspaceEnabled = lockscreenSmartspaceController.isEnabled;
        ReadonlyStateFlow readonlyStateFlow = keyguardSmartspaceInteractor.isWeatherEnabled;
        this.isWeatherEnabled = readonlyStateFlow;
        this.isDateWeatherDecoupled = lockscreenSmartspaceController.isDateWeatherDecoupled;
        final ReadonlyStateFlow readonlyStateFlow2 = keyguardClockViewModel.hasCustomWeatherDataDisplay;
        Flow flow = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(!((Boolean) obj).booleanValue());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        ReadonlyStateFlow readonlyStateFlow3 = keyguardClockViewModel.hasCustomWeatherDataDisplay;
        this.isDateVisible = FlowKt.stateIn(flow, coroutineScope, startedWhileSubscribedWhileSubscribed$default, Boolean.valueOf(!((Boolean) readonlyStateFlow3.$$delegate_0.getValue()).booleanValue()));
        this.isWeatherVisible = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, readonlyStateFlow3, new KeyguardSmartspaceViewModel$isWeatherVisible$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(!((Boolean) readonlyStateFlow3.$$delegate_0.getValue()).booleanValue() && ((Boolean) readonlyStateFlow.$$delegate_0.getValue()).booleanValue()));
        this.bcSmartspaceVisibility = keyguardSmartspaceInteractor.bcSmartspaceVisibility;
        this.isShadeLayoutWide = ((ShadeModeInteractorImpl) shadeModeInteractor).isShadeLayoutWide;
    }
}
