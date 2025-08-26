package com.android.systemui.blur.domain.interactor;

import android.content.Context;
import android.content.res.Configuration;
import com.android.systemui.R;
import com.android.systemui.blur.data.repository.SecBlurSettingsRepository;
import com.android.systemui.blur.data.repository.SecBlurSettingsRepositoryImpl;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import kotlin.ResultKt;
import kotlin.Triple;
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

/* loaded from: classes.dex */
public final class SecBlurCustomColorInteractor {
    public static final int backgroundColorId;
    public static final int bgOverlayColorId;
    public final Flow configurationChanged;
    public final Context context;
    public final ReadonlyStateFlow hasCustomColorApplied;
    public final SharedFlowImpl updateBackgroundColor = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6);

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
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor$special$$inlined$map$1

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
                        Configuration configuration = (Configuration) obj;
                        Triple triple = new Triple(new Integer(configuration.uiMode), new Integer(configuration.themeSeq), new Integer(configuration.assetsSeq));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(triple, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.configurationChanged = flowDistinctUntilChanged;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowDistinctUntilChanged, ((SecBlurSettingsRepositoryImpl) secBlurSettingsRepository).minimalBatteryUse, new SecBlurCustomColorInteractor$hasCustomColorApplied$1(this, null));
        SharingStarted.Companion.getClass();
        this.hasCustomColorApplied = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
    }
}
