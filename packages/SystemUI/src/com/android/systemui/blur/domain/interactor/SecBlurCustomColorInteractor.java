package com.android.systemui.blur.domain.interactor;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.blur.data.repository.SecBlurSettingsRepository;
import com.android.systemui.blur.data.repository.SecBlurSettingsRepositoryImpl;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
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
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecBlurCustomColorInteractor {
    public static final int backgroundColorId;
    public static final int bgOverlayColorId;
    public final Flow configurationChanged;
    public final Context context;
    public final ReadonlyStateFlow hasCustomColorApplied;
    public final SharedFlowImpl updateBackgroundColor = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        backgroundColorId = R.color.open_theme_qp_bg_color;
        bgOverlayColorId = R.color.sec_panel_background_color_tablet;
    }

    public SecBlurCustomColorInteractor(CoroutineScope coroutineScope, Context context, SecBlurSettingsRepository secBlurSettingsRepository, ConfigurationInteractor configurationInteractor) {
        this.context = context;
        final Flow flow = ((ConfigurationInteractorImpl) configurationInteractor).configurationValues;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L59
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        android.content.res.Configuration r7 = (android.content.res.Configuration) r7
                        kotlin.Triple r8 = new kotlin.Triple
                        int r2 = r7.uiMode
                        java.lang.Integer r4 = new java.lang.Integer
                        r4.<init>(r2)
                        int r2 = r7.themeSeq
                        java.lang.Integer r5 = new java.lang.Integer
                        r5.<init>(r2)
                        int r7 = r7.assetsSeq
                        java.lang.Integer r2 = new java.lang.Integer
                        r2.<init>(r7)
                        r8.<init>(r4, r5, r2)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L59
                        return r1
                    L59:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.configurationChanged = distinctUntilChanged;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(distinctUntilChanged, ((SecBlurSettingsRepositoryImpl) secBlurSettingsRepository).minimalBatteryUse, new SecBlurCustomColorInteractor$hasCustomColorApplied$1(this, null));
        SharingStarted.Companion.getClass();
        this.hasCustomColorApplied = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
    }
}
