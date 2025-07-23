package com.android.systemui.qs.bar.repository;

import android.content.Context;
import android.graphics.Color;
import com.android.systemui.R;
import com.android.systemui.blur.domain.interactor.SecBlurSettingsInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.qs.bar.domain.interactor.ColoredBackgroundWallpaperInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.sec.ims.configuration.DATA;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ColoredBackgroundRepository {
    public static final String TAG;
    public final Context context;
    public final ReadonlyStateFlow currentExtractedBackgroundColor;
    public final SharedFlowImpl wallPaperColorChanged;
    public final int WALLPAPER_FIXED_ALPHA = HexExtensionsKt.hexToInt$default("3d");
    public final int THEME_FIXED_ALPHA = HexExtensionsKt.hexToInt$default(DATA.DM_FIELD_INDEX.PREF_CSCF_PORT);

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
        TAG = Reflection.getOrCreateKotlinClass(ColoredBackgroundRepository.class).getSimpleName();
    }

    public ColoredBackgroundRepository(Context context, CoroutineScope coroutineScope, SecBlurSettingsInteractor secBlurSettingsInteractor, ColoredBackgroundWallpaperInteractor coloredBackgroundWallpaperInteractor, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor, ConfigurationInteractor configurationInteractor) {
        this.context = context;
        final Flow flow = ((ConfigurationInteractorImpl) configurationInteractor).configurationValues;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.qs.bar.repository.ColoredBackgroundRepository$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r8 instanceof com.android.systemui.qs.bar.repository.ColoredBackgroundRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.qs.bar.repository.ColoredBackgroundRepository$special$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.bar.repository.ColoredBackgroundRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.bar.repository.ColoredBackgroundRepository$special$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.bar.repository.ColoredBackgroundRepository$special$$inlined$map$1$2$1
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.bar.repository.ColoredBackgroundRepository$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6);
        this.wallPaperColorChanged = MutableSharedFlow$default;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 combine = FlowKt.combine(secBlurSettingsInteractor.blurReduced, secBlurSettingsInteractor.minimalBatteryUse, secPanelExpansionStateInteractor.statusBarState, distinctUntilChanged, MutableSharedFlow$default, new ColoredBackgroundRepository$currentExtractedBackgroundColor$1(coloredBackgroundWallpaperInteractor, this, null));
        SharingStarted.Companion.getClass();
        this.currentExtractedBackgroundColor = FlowKt.stateIn(combine, coroutineScope, SharingStarted.Companion.Eagerly, Integer.valueOf(toARGB(getResourceColor(), getAlpha())));
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
