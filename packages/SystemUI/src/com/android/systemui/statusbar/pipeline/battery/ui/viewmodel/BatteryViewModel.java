package com.android.systemui.statusbar.pipeline.battery.ui.viewmodel;

import android.content.Context;
import androidx.compose.runtime.State;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryAttributionModel;
import com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor;
import com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryColors;
import com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryFrame;
import com.android.systemui.statusbar.pipeline.battery.shared.ui.PathSpec;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BatteryViewModel extends ExclusiveActivatable {
    public static final Companion Companion = new Companion(null);
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 _colorProfile;
    public final ChannelFlowTransformLatest _glyphList;
    public final BatteryViewModel$special$$inlined$map$1 attributionGlyph;
    public final PathSpec batteryFrame;
    public final State colorProfile$delegate;
    public final State contentDescription$delegate;
    public final State glyphList$delegate;
    public final Hydrator hydrator;
    public final State isFull$delegate;
    public final State level$delegate;
    public final BatteryViewModel$special$$inlined$map$2 levelGlyphs;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BatteryAttributionModel.values().length];
            try {
                iArr[BatteryAttributionModel.Charging.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BatteryAttributionModel.PowerSave.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[BatteryAttributionModel.Defend.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        Dp.Companion companion = Dp.Companion;
    }

    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$2] */
    public BatteryViewModel(BatteryInteractor batteryInteractor, Context context) {
        Hydrator hydrator = new Hydrator("BatteryViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        BatteryFrame.INSTANCE.getClass();
        this.batteryFrame = BatteryFrame.pathSpec;
        this.level$delegate = hydrator.hydratedStateOf(ActionResults.RESULT_SET_VOLUME_SUCCESS, 0, batteryInteractor.level);
        this.isFull$delegate = hydrator.hydratedStateOf("isFull", Boolean.FALSE, batteryInteractor.isFull);
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 = batteryInteractor.batteryAttributionType;
        this.attributionGlyph = new Flow() { // from class: com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L72
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryAttributionModel r5 = (com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryAttributionModel) r5
                        if (r5 != 0) goto L38
                        r5 = -1
                        goto L40
                    L38:
                        int[] r6 = com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel.WhenMappings.$EnumSwitchMapping$0
                        int r5 = r5.ordinal()
                        r5 = r6[r5]
                    L40:
                        if (r5 == r3) goto L5e
                        r6 = 2
                        if (r5 == r6) goto L54
                        r6 = 3
                        if (r5 == r6) goto L4a
                        r5 = 0
                        goto L67
                    L4a:
                        com.android.systemui.statusbar.pipeline.battery.ui.model.AttributionGlyph r5 = new com.android.systemui.statusbar.pipeline.battery.ui.model.AttributionGlyph
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$Defend r6 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.Defend.INSTANCE
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$DefendLarge r2 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.DefendLarge.INSTANCE
                        r5.<init>(r6, r2)
                        goto L67
                    L54:
                        com.android.systemui.statusbar.pipeline.battery.ui.model.AttributionGlyph r5 = new com.android.systemui.statusbar.pipeline.battery.ui.model.AttributionGlyph
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$Plus r6 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.Plus.INSTANCE
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$PlusLarge r2 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.PlusLarge.INSTANCE
                        r5.<init>(r6, r2)
                        goto L67
                    L5e:
                        com.android.systemui.statusbar.pipeline.battery.ui.model.AttributionGlyph r5 = new com.android.systemui.statusbar.pipeline.battery.ui.model.AttributionGlyph
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$Bolt r6 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.Bolt.INSTANCE
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$BoltLarge r2 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.BoltLarge.INSTANCE
                        r5.<init>(r6, r2)
                    L67:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L72
                        return r1
                    L72:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = batteryInteractor.level;
        this.levelGlyphs = new Flow() { // from class: com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto La5
                    L28:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L30:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.lang.Number r7 = (java.lang.Number) r7
                        int r7 = r7.intValue()
                        com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$Companion r8 = com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel.Companion
                        r8.getClass()
                        java.lang.String r7 = java.lang.String.valueOf(r7)
                        java.util.ArrayList r8 = new java.util.ArrayList
                        int r2 = r7.length()
                        r8.<init>(r2)
                        r2 = 0
                    L4c:
                        int r4 = r7.length()
                        if (r2 >= r4) goto L9a
                        char r4 = r7.charAt(r2)
                        com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$Companion r5 = com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel.Companion
                        r5.getClass()
                        switch(r4) {
                            case 48: goto L92;
                            case 49: goto L8f;
                            case 50: goto L8c;
                            case 51: goto L89;
                            case 52: goto L86;
                            case 53: goto L83;
                            case 54: goto L80;
                            case 55: goto L7d;
                            case 56: goto L7a;
                            case 57: goto L77;
                            default: goto L5e;
                        }
                    L5e:
                        java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
                        java.lang.StringBuilder r7 = new java.lang.StringBuilder
                        java.lang.String r8 = "cannot make glyph from char ("
                        r7.<init>(r8)
                        r7.append(r4)
                        java.lang.String r8 = ")"
                        r7.append(r8)
                        java.lang.String r7 = r7.toString()
                        r6.<init>(r7)
                        throw r6
                    L77:
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$Nine r4 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.Nine.INSTANCE
                        goto L94
                    L7a:
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$Eight r4 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.Eight.INSTANCE
                        goto L94
                    L7d:
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$Seven r4 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.Seven.INSTANCE
                        goto L94
                    L80:
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$Six r4 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.Six.INSTANCE
                        goto L94
                    L83:
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$Five r4 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.Five.INSTANCE
                        goto L94
                    L86:
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$Four r4 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.Four.INSTANCE
                        goto L94
                    L89:
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$Three r4 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.Three.INSTANCE
                        goto L94
                    L8c:
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$Two r4 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.Two.INSTANCE
                        goto L94
                    L8f:
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$One r4 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.One.INSTANCE
                        goto L94
                    L92:
                        com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph$Zero r4 = com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph.Zero.INSTANCE
                    L94:
                        r8.add(r4)
                        int r2 = r2 + 1
                        goto L4c
                    L9a:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto La5
                        return r1
                    La5:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(batteryInteractor.isBatteryPercentSettingEnabled, new BatteryViewModel$special$$inlined$flatMapLatest$1(null, batteryInteractor, this));
        this._glyphList = transformLatest;
        this.glyphList$delegate = hydrator.hydratedStateOf("glyphList", EmptyList.INSTANCE, transformLatest);
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, batteryInteractor.isCritical, new BatteryViewModel$_colorProfile$1(null));
        this._colorProfile = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.colorProfile$delegate = hydrator.hydratedStateOf("colorProfile", new ColorProfile(BatteryColors.DarkThemeDefaultColors.INSTANCE, BatteryColors.LightThemeDefaultColors.INSTANCE), flowKt__ZipKt$combine$$inlined$unsafeFlow$1);
        this.contentDescription$delegate = hydrator.hydratedStateOf("contentDescription", new ContentDescription.Loaded(null), FlowKt.combine(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, batteryInteractor.isStateUnknown, flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, new BatteryViewModel$contentDescription$2(context, null)));
        hydrator.hydratedStateOf("timeRemainingEstimate", null, batteryInteractor.batteryTimeRemainingEstimate);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$onActivated$1 r0 = (com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$onActivated$1 r0 = new com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L3d
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.label = r3
            com.android.systemui.lifecycle.Hydrator r4 = r4.hydrator
            java.lang.Object r4 = r4.activate(r0)
            if (r4 != r1) goto L3d
            return r1
        L3d:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
