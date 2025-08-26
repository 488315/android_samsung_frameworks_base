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
import com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph;
import com.android.systemui.statusbar.pipeline.battery.shared.ui.PathSpec;
import com.android.systemui.statusbar.pipeline.battery.ui.model.AttributionGlyph;
import com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel;
import java.util.ArrayList;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
    }

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

    /* renamed from: com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$onActivated$1, reason: invalid class name */
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
            return BatteryViewModel.this.onActivated(this);
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
                        BatteryAttributionModel batteryAttributionModel = (BatteryAttributionModel) obj;
                        int i3 = batteryAttributionModel == null ? -1 : BatteryViewModel.WhenMappings.$EnumSwitchMapping$0[batteryAttributionModel.ordinal()];
                        AttributionGlyph attributionGlyph = i3 != 1 ? i3 != 2 ? i3 != 3 ? null : new AttributionGlyph(BatteryGlyph.Defend.INSTANCE, BatteryGlyph.DefendLarge.INSTANCE) : new AttributionGlyph(BatteryGlyph.Plus.INSTANCE, BatteryGlyph.PlusLarge.INSTANCE) : new AttributionGlyph(BatteryGlyph.Bolt.INSTANCE, BatteryGlyph.BoltLarge.INSTANCE);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(attributionGlyph, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = batteryInteractor.level;
        this.levelGlyphs = new Flow() { // from class: com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$special$$inlined$map$2

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object obj2;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj3 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj3);
                        int iIntValue = ((Number) obj).intValue();
                        BatteryViewModel.Companion.getClass();
                        String strValueOf = String.valueOf(iIntValue);
                        ArrayList arrayList = new ArrayList(strValueOf.length());
                        for (int i3 = 0; i3 < strValueOf.length(); i3++) {
                            char cCharAt = strValueOf.charAt(i3);
                            BatteryViewModel.Companion.getClass();
                            switch (cCharAt) {
                                case '0':
                                    obj2 = BatteryGlyph.Zero.INSTANCE;
                                    break;
                                case '1':
                                    obj2 = BatteryGlyph.One.INSTANCE;
                                    break;
                                case '2':
                                    obj2 = BatteryGlyph.Two.INSTANCE;
                                    break;
                                case '3':
                                    obj2 = BatteryGlyph.Three.INSTANCE;
                                    break;
                                case '4':
                                    obj2 = BatteryGlyph.Four.INSTANCE;
                                    break;
                                case '5':
                                    obj2 = BatteryGlyph.Five.INSTANCE;
                                    break;
                                case '6':
                                    obj2 = BatteryGlyph.Six.INSTANCE;
                                    break;
                                case '7':
                                    obj2 = BatteryGlyph.Seven.INSTANCE;
                                    break;
                                case '8':
                                    obj2 = BatteryGlyph.Eight.INSTANCE;
                                    break;
                                case '9':
                                    obj2 = BatteryGlyph.Nine.INSTANCE;
                                    break;
                                default:
                                    throw new IllegalArgumentException("cannot make glyph from char (" + cCharAt + ")");
                            }
                            arrayList.add(obj2);
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(arrayList, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj3);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(batteryInteractor.isBatteryPercentSettingEnabled, new BatteryViewModel$special$$inlined$flatMapLatest$1(null, batteryInteractor, this));
        this._glyphList = channelFlowTransformLatestTransformLatest;
        this.glyphList$delegate = hydrator.hydratedStateOf("glyphList", EmptyList.INSTANCE, channelFlowTransformLatestTransformLatest);
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, batteryInteractor.isCritical, new BatteryViewModel$_colorProfile$1(null));
        this._colorProfile = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.colorProfile$delegate = hydrator.hydratedStateOf("colorProfile", new ColorProfile(BatteryColors.DarkThemeDefaultColors.INSTANCE, BatteryColors.LightThemeDefaultColors.INSTANCE), flowKt__ZipKt$combine$$inlined$unsafeFlow$1);
        this.contentDescription$delegate = hydrator.hydratedStateOf("contentDescription", new ContentDescription.Loaded(null), FlowKt.combine(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, batteryInteractor.isStateUnknown, flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, new BatteryViewModel$contentDescription$2(context, null)));
        hydrator.hydratedStateOf("timeRemainingEstimate", null, batteryInteractor.batteryTimeRemainingEstimate);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
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
            anonymousClass1.label = 1;
            if (this.hydrator.activate(anonymousClass1) == coroutineSingletons) {
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
