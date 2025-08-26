package com.android.systemui.volume.panel.component.captioning.ui.viewmodel;

import android.content.Context;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl;
import com.android.systemui.accessibility.domain.interactor.CaptioningInteractor;
import com.android.systemui.accessibility.domain.interactor.CaptioningInteractor$special$$inlined$map$1;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.volume.panel.component.button.ui.viewmodel.ButtonViewModel;
import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class CaptioningViewModel {
    public final ReadonlyStateFlow buttonViewModel;
    public final CaptioningInteractor captioningInteractor;
    public final Context context;
    public final CoroutineScope coroutineScope;
    public final UiEventLogger uiEventLogger;

    /* renamed from: com.android.systemui.volume.panel.component.captioning.ui.viewmodel.CaptioningViewModel$setIsSystemAudioCaptioningEnabled$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CaptioningViewModel.this.new AnonymousClass1(this.$enabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CaptioningInteractor captioningInteractor = CaptioningViewModel.this.captioningInteractor;
                boolean z = this.$enabled;
                this.label = 1;
                Object isSystemAudioCaptioningEnabled = ((CaptioningRepositoryImpl) captioningInteractor.repository).setIsSystemAudioCaptioningEnabled(z, this);
                if (isSystemAudioCaptioningEnabled != coroutineSingletons) {
                    isSystemAudioCaptioningEnabled = Unit.INSTANCE;
                }
                if (isSystemAudioCaptioningEnabled == coroutineSingletons) {
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

    public CaptioningViewModel(Context context, CaptioningInteractor captioningInteractor, CoroutineScope coroutineScope, UiEventLogger uiEventLogger) {
        this.context = context;
        this.captioningInteractor = captioningInteractor;
        this.coroutineScope = coroutineScope;
        this.uiEventLogger = uiEventLogger;
        final CaptioningInteractor$special$$inlined$map$1 captioningInteractor$special$$inlined$map$1 = captioningInteractor.isSystemAudioCaptioningEnabled;
        Flow flow = new Flow() { // from class: com.android.systemui.volume.panel.component.captioning.ui.viewmodel.CaptioningViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.volume.panel.component.captioning.ui.viewmodel.CaptioningViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CaptioningViewModel this$0;

                /* renamed from: com.android.systemui.volume.panel.component.captioning.ui.viewmodel.CaptioningViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CaptioningViewModel captioningViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = captioningViewModel;
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
                        boolean zBooleanValue = ((Boolean) obj).booleanValue();
                        ButtonViewModel buttonViewModel = new ButtonViewModel(new Icon.Resource(zBooleanValue ? R.drawable.ic_volume_odi_captions : R.drawable.ic_volume_odi_captions_disabled, null), this.this$0.context.getString(R.string.volume_panel_captioning_title), zBooleanValue);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(buttonViewModel, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = captioningInteractor$special$$inlined$map$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.buttonViewModel = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, null);
    }

    public final void setIsSystemAudioCaptioningEnabled(boolean z) {
        this.uiEventLogger.logWithPosition(VolumePanelUiEvent.VOLUME_PANEL_LIVE_CAPTION_TOGGLE_CLICKED, 0, (String) null, z ? 1 : 0);
        CoroutineTracingKt.launchTraced$default(this.coroutineScope, null, null, new AnonymousClass1(z, null), 7);
    }
}
