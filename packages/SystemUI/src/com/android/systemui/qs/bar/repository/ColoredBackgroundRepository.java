package com.android.systemui.qs.bar.repository;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import com.android.systemui.R;
import com.android.systemui.blur.domain.interactor.SecBlurSettingsInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.qs.bar.domain.interactor.ColoredBackgroundWallpaperInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.sec.ims.configuration.DATA;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.text.HexExtensionsKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
public final class ColoredBackgroundRepository {
    public static final String TAG;
    public final Context context;
    public final ReadonlyStateFlow currentExtractedBackgroundColor;
    public final SharedFlowImpl wallPaperColorChanged;
    public final int WALLPAPER_FIXED_ALPHA = HexExtensionsKt.hexToInt$default("3d");
    public final int THEME_FIXED_ALPHA = HexExtensionsKt.hexToInt$default(DATA.DM_FIELD_INDEX.PREF_CSCF_PORT);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TAG = Reflection.getOrCreateKotlinClass(ColoredBackgroundRepository.class).getSimpleName();
    }

    public ColoredBackgroundRepository(Context context, CoroutineScope coroutineScope, SecBlurSettingsInteractor secBlurSettingsInteractor, ColoredBackgroundWallpaperInteractor coloredBackgroundWallpaperInteractor, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor, ConfigurationInteractor configurationInteractor) {
        this.context = context;
        final Flow flow = ((ConfigurationInteractorImpl) configurationInteractor).configurationValues;
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.qs.bar.repository.ColoredBackgroundRepository$special$$inlined$map$1

            /* renamed from: com.android.systemui.qs.bar.repository.ColoredBackgroundRepository$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.bar.repository.ColoredBackgroundRepository$special$$inlined$map$1$2$1, reason: invalid class name */
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
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6);
        this.wallPaperColorChanged = sharedFlowImplMutableSharedFlow$default;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3Combine = FlowKt.combine(secBlurSettingsInteractor.blurReduced, secBlurSettingsInteractor.minimalBatteryUse, secPanelExpansionStateInteractor.statusBarState, flowDistinctUntilChanged, sharedFlowImplMutableSharedFlow$default, new ColoredBackgroundRepository$currentExtractedBackgroundColor$1(coloredBackgroundWallpaperInteractor, this, null));
        SharingStarted.Companion.getClass();
        this.currentExtractedBackgroundColor = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3Combine, coroutineScope, SharingStarted.Companion.Eagerly, Integer.valueOf(toARGB(getResourceColor(), getAlpha())));
    }

    public static int toARGB(int i, int i2) {
        return Color.argb(i2, Color.red(i), Color.green(i), Color.blue(i));
    }

    public final int getAlpha() {
        return getBgColorOverlaid() ? this.THEME_FIXED_ALPHA : this.WALLPAPER_FIXED_ALPHA;
    }

    public final boolean getBgColorOverlaid() {
        return !StringsKt__StringsJVMKt.equals(Integer.toHexString(getResourceColor()), "3d000000", true);
    }

    public final int getResourceColor() {
        return this.context.getResources().getColor(R.color.qs_tile_container_bg);
    }
}
