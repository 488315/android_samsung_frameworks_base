package com.android.systemui.volume.panel.component.captioning.ui.viewmodel;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.view.accessibility.data.repository.CaptioningRepositoryImpl;
import com.android.settingslib.view.accessibility.domain.interactor.CaptioningInteractor;
import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

public final class CaptioningViewModel {
    public final ReadonlyStateFlow buttonViewModel;
    public final CaptioningInteractor captioningInteractor;
    public final Context context;
    public final CoroutineScope coroutineScope;
    public final UiEventLogger uiEventLogger;

    public CaptioningViewModel(Context context, CaptioningInteractor captioningInteractor, CoroutineScope coroutineScope, UiEventLogger uiEventLogger) {
        this.context = context;
        this.captioningInteractor = captioningInteractor;
        this.coroutineScope = coroutineScope;
        this.uiEventLogger = uiEventLogger;
        final ReadonlyStateFlow readonlyStateFlow = ((CaptioningRepositoryImpl) captioningInteractor.repository).isSystemAudioCaptioningEnabled;
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.volume.panel.component.captioning.ui.viewmodel.CaptioningViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.volume.panel.component.captioning.ui.viewmodel.CaptioningViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.panel.component.captioning.ui.viewmodel.CaptioningViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.panel.component.captioning.ui.viewmodel.CaptioningViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.panel.component.captioning.ui.viewmodel.CaptioningViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L65
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        com.android.systemui.common.shared.model.Icon$Resource r7 = new com.android.systemui.common.shared.model.Icon$Resource
                        if (r6 == 0) goto L40
                        r2 = 2131233623(0x7f080b57, float:1.8083389E38)
                        goto L43
                    L40:
                        r2 = 2131233624(0x7f080b58, float:1.808339E38)
                    L43:
                        r4 = 0
                        r7.<init>(r2, r4)
                        com.android.systemui.volume.panel.component.captioning.ui.viewmodel.CaptioningViewModel r2 = r5.this$0
                        android.content.Context r2 = r2.context
                        r4 = 2131956800(0x7f131440, float:1.9550166E38)
                        java.lang.String r2 = r2.getString(r4)
                        com.android.systemui.volume.panel.component.button.ui.viewmodel.ButtonViewModel r4 = new com.android.systemui.volume.panel.component.button.ui.viewmodel.ButtonViewModel
                        kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
                        r4.<init>(r7, r2, r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r4, r0)
                        if (r5 != r1) goto L65
                        return r1
                    L65:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.captioning.ui.viewmodel.CaptioningViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.buttonViewModel = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, null);
    }

    public final void setIsSystemAudioCaptioningEnabled(boolean z) {
        this.uiEventLogger.logWithPosition(VolumePanelUiEvent.VOLUME_PANEL_LIVE_CAPTION_TOGGLE_CLICKED, 0, (String) null, z ? 1 : 0);
        BuildersKt.launch$default(this.coroutineScope, null, null, new CaptioningViewModel$setIsSystemAudioCaptioningEnabled$1(this, z, null), 3);
    }
}
