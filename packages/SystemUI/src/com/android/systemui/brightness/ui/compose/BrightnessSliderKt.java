package com.android.systemui.brightness.ui.compose;

import android.content.Context;
import android.view.MotionEvent;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.CornerSizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.CacheDrawScope;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.input.pointer.PointerInteropFilter_androidKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.modifiers.PaddingKt;
import com.android.compose.ui.graphics.ContainerState;
import com.android.compose.ui.graphics.DrawInContainerKt;
import com.android.compose.ui.graphics.DrawInContainerNode$$ExternalSyntheticLambda1;
import com.android.compose.ui.graphics.DrawInOverlayKt;
import com.android.systemui.brightness.shared.model.GammaBrightness;
import com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel;
import com.android.systemui.compose.modifiers.SysuiTestTagKt;
import com.android.systemui.qs.ui.compose.BorderOnFocusKt;
import com.android.systemui.utils.PolicyRestriction;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.reflect.KFunction;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BrightnessSliderKt {
    /* JADX WARN: Code restructure failed: missing block: B:71:0x01ee, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L164;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0385  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x03b5  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x03cc  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x03d7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0404  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0421  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x042c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0467  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0479  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0489 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x04f6  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0504 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x051e  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x047c  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x046a  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0424  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0407  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x03cf  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x02f9  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0528  */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0238  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0259  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0273 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x02a7  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02e6  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x02f6  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0305 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0359 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void BrightnessSlider(final int r47, final kotlin.ranges.IntRange r48, final kotlin.jvm.functions.Function1 r49, final kotlin.jvm.functions.Function3 r50, final com.android.systemui.utils.PolicyRestriction r51, final kotlin.jvm.functions.Function1 r52, final kotlin.jvm.functions.Function1 r53, final kotlin.jvm.functions.Function1 r54, final boolean r55, androidx.compose.ui.Modifier r56, kotlin.jvm.functions.Function0 r57, final com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel.Factory r58, androidx.compose.runtime.Composer r59, final int r60, final int r61, final int r62) {
        /*
            Method dump skipped, instructions count: 1353
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.brightness.ui.compose.BrightnessSliderKt.BrightnessSlider(int, kotlin.ranges.IntRange, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function3, com.android.systemui.utils.PolicyRestriction, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, boolean, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function0, com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$Factory, androidx.compose.runtime.Composer, int, int, int):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v11, types: [androidx.compose.ui.Modifier] */
    public static final void BrightnessSliderContainer(final BrightnessSliderViewModel brightnessSliderViewModel, final Modifier modifier, final ContainerColors containerColors, Composer composer, final int i) {
        KFunction kFunction;
        IntRange intRange;
        KFunction kFunction2;
        boolean z;
        Modifier.Companion companion;
        final MutableState mutableState;
        boolean z2;
        Object obj;
        ComposerImpl composerImpl;
        final int i2 = 0;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-2106303987);
        if (((i | (composerImpl2.changedInstance(brightnessSliderViewModel) ? 4 : 2) | (composerImpl2.changed(containerColors) ? 256 : 128)) & 147) == 146 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.brightness.ui.compose.BrightnessSliderContainer (BrightnessSlider.kt:332)");
            }
            int i3 = ((GammaBrightness) ((SnapshotMutableStateImpl) brightnessSliderViewModel.currentBrightness$delegate).getValue()).value;
            BrightnessSliderViewModel.Companion companion2 = BrightnessSliderViewModel.Companion;
            companion2.getClass();
            if (i3 == BrightnessSliderViewModel.initialValue) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl endRestartGroup = composerImpl2.endRestartGroup();
                if (endRestartGroup != null) {
                    final int i4 = 0;
                    endRestartGroup.block = new Function2(brightnessSliderViewModel, modifier, containerColors, i, i4) { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda7
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ BrightnessSliderViewModel f$0;
                        public final /* synthetic */ Modifier f$1;
                        public final /* synthetic */ ContainerColors f$2;

                        {
                            this.$r8$classId = i4;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            int i5 = this.$r8$classId;
                            Composer composer2 = (Composer) obj2;
                            ((Integer) obj3).getClass();
                            switch (i5) {
                                case 0:
                                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                                    BrightnessSliderKt.BrightnessSliderContainer(this.f$0, this.f$1, this.f$2, composer2, updateChangedFlags);
                                    break;
                                default:
                                    int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(49);
                                    BrightnessSliderKt.BrightnessSliderContainer(this.f$0, this.f$1, this.f$2, composer2, updateChangedFlags2);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            Context context = (Context) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalContext);
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            Object obj2 = rememberedValue;
            if (rememberedValue == composer$Companion$Empty$1) {
                CoroutineScope createCompositionCoroutineScope = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                composerImpl2.updateRememberedValue(createCompositionCoroutineScope);
                obj2 = createCompositionCoroutineScope;
            }
            final CoroutineScope coroutineScope = (CoroutineScope) obj2;
            MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(brightnessSliderViewModel.policyRestriction, PolicyRestriction.NoRestriction.INSTANCE, composerImpl2, 48);
            composerImpl2.startReplaceGroup(-777739249);
            MutableState collectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(brightnessSliderViewModel.brightnessOverriddenByWindow, composerImpl2);
            composerImpl2.end(false);
            Unit unit = Unit.INSTANCE;
            composerImpl2.startReplaceGroup(2053127495);
            boolean changedInstance = composerImpl2.changedInstance(brightnessSliderViewModel);
            Object rememberedValue2 = composerImpl2.rememberedValue();
            Object obj3 = rememberedValue2;
            if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                Function1 function1 = new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda8
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj4) {
                        switch (i2) {
                            case 0:
                                final BrightnessSliderViewModel brightnessSliderViewModel2 = brightnessSliderViewModel;
                                return new DisposableEffectResult() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$BrightnessSliderContainer$lambda$32$lambda$31$$inlined$onDispose$1
                                    @Override // androidx.compose.runtime.DisposableEffectResult
                                    public final void dispose() {
                                        BrightnessSliderViewModel.this.setIsDragging(false);
                                    }
                                };
                            default:
                                MotionEvent motionEvent = (MotionEvent) obj4;
                                if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
                                    brightnessSliderViewModel.falsingInteractor.manager.isFalseTouch(10);
                                }
                                return Boolean.FALSE;
                        }
                    }
                };
                composerImpl2.updateRememberedValue(function1);
                obj3 = function1;
            }
            composerImpl2.end(false);
            EffectsKt.DisposableEffect(unit, (Function1) obj3, composerImpl2);
            composerImpl2.startReplaceGroup(2053129721);
            Object rememberedValue3 = composerImpl2.rememberedValue();
            Object obj4 = rememberedValue3;
            if (rememberedValue3 == composer$Companion$Empty$1) {
                MutableState mutableStateOf$default = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl2.updateRememberedValue(mutableStateOf$default);
                obj4 = mutableStateOf$default;
            }
            MutableState mutableState2 = (MutableState) obj4;
            composerImpl2.end(false);
            State m7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(((Boolean) mutableState2.getValue()).booleanValue() ? containerColors.mirrorColor : containerColors.idleColor, null, null, composerImpl2, 0, 14);
            composerImpl2.startReplaceGroup(2053145543);
            Object rememberedValue4 = composerImpl2.rememberedValue();
            Object obj5 = rememberedValue4;
            if (rememberedValue4 == composer$Companion$Empty$1) {
                BrightnessSliderKt$$ExternalSyntheticLambda9 brightnessSliderKt$$ExternalSyntheticLambda9 = new BrightnessSliderKt$$ExternalSyntheticLambda9();
                composerImpl2.updateRememberedValue(brightnessSliderKt$$ExternalSyntheticLambda9);
                obj5 = brightnessSliderKt$$ExternalSyntheticLambda9;
            }
            composerImpl2.end(false);
            Modifier sysuiResTag = SysuiTestTagKt.sysuiResTag(SizeKt.fillMaxWidth(PaddingKt.padding$default(modifier, null, (Function1) obj5, 1), 1.0f), "brightness_slider");
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, sysuiResTag);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl2.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function0);
            } else {
                composerImpl2.useNode();
            }
            Updater.m336setimpl(composerImpl2, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl2, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            IntRange intRange2 = new IntRange(0, brightnessSliderViewModel.maxBrightness);
            composerImpl2.startReplaceGroup(-734809435);
            Object rememberedValue5 = composerImpl2.rememberedValue();
            Object obj6 = rememberedValue5;
            if (rememberedValue5 == composer$Companion$Empty$1) {
                BrightnessSliderKt$BrightnessSliderContainer$4$1$1 brightnessSliderKt$BrightnessSliderContainer$4$1$1 = new BrightnessSliderKt$BrightnessSliderContainer$4$1$1(companion2);
                composerImpl2.updateRememberedValue(brightnessSliderKt$BrightnessSliderContainer$4$1$1);
                obj6 = brightnessSliderKt$BrightnessSliderContainer$4$1$1;
            }
            KFunction kFunction3 = (KFunction) obj6;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(-734807062);
            boolean changedInstance2 = composerImpl2.changedInstance(brightnessSliderViewModel);
            Object rememberedValue6 = composerImpl2.rememberedValue();
            Object obj7 = rememberedValue6;
            if (changedInstance2 || rememberedValue6 == composer$Companion$Empty$1) {
                BrightnessSliderKt$BrightnessSliderContainer$4$2$1 brightnessSliderKt$BrightnessSliderContainer$4$2$1 = new BrightnessSliderKt$BrightnessSliderContainer$4$2$1(brightnessSliderViewModel);
                composerImpl2.updateRememberedValue(brightnessSliderKt$BrightnessSliderContainer$4$2$1);
                obj7 = brightnessSliderKt$BrightnessSliderContainer$4$2$1;
            }
            KFunction kFunction4 = (KFunction) obj7;
            composerImpl2.end(false);
            PolicyRestriction policyRestriction = (PolicyRestriction) collectAsStateWithLifecycle.getValue();
            composerImpl2.startReplaceGroup(-734804068);
            boolean changedInstance3 = composerImpl2.changedInstance(brightnessSliderViewModel);
            Object rememberedValue7 = composerImpl2.rememberedValue();
            Object obj8 = rememberedValue7;
            if (changedInstance3 || rememberedValue7 == composer$Companion$Empty$1) {
                BrightnessSliderKt$BrightnessSliderContainer$4$3$1 brightnessSliderKt$BrightnessSliderContainer$4$3$1 = new BrightnessSliderKt$BrightnessSliderContainer$4$3$1(brightnessSliderViewModel);
                composerImpl2.updateRememberedValue(brightnessSliderKt$BrightnessSliderContainer$4$3$1);
                obj8 = brightnessSliderKt$BrightnessSliderContainer$4$3$1;
            }
            KFunction kFunction5 = (KFunction) obj8;
            composerImpl2.end(false);
            Modifier.Companion companion3 = Modifier.Companion;
            MaterialTheme.INSTANCE.getClass();
            long j = MaterialTheme.getColorScheme(composerImpl2).secondary;
            Dimensions.INSTANCE.getClass();
            Modifier m2920borderOnFocusPOIbLQ4$default = BorderOnFocusKt.m2920borderOnFocusPOIbLQ4$default(companion3, j, CornerSizeKt.m185CornerSize0680j_4(Dimensions.SliderTrackRoundedCorner));
            composerImpl2.startReplaceGroup(-734781066);
            if (((Boolean) ((SnapshotMutableStateImpl) brightnessSliderViewModel.showMirror$delegate).getValue()).booleanValue()) {
                composerImpl2.startReplaceGroup(-294285675);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.compose.ui.graphics.drawInOverlay (DrawInOverlay.kt:42)");
                }
                composerImpl2.startReplaceGroup(-1255809229);
                Object rememberedValue8 = composerImpl2.rememberedValue();
                Object obj9 = rememberedValue8;
                if (rememberedValue8 == composer$Companion$Empty$1) {
                    ContainerState containerState = new ContainerState();
                    composerImpl2.updateRememberedValue(containerState);
                    obj9 = containerState;
                }
                final ContainerState containerState2 = (ContainerState) obj9;
                composerImpl2.end(false);
                composerImpl2.startReplaceGroup(-1255807140);
                boolean changedInstance4 = composerImpl2.changedInstance(containerState2);
                kFunction = kFunction3;
                Object rememberedValue9 = composerImpl2.rememberedValue();
                Object obj10 = rememberedValue9;
                if (changedInstance4 || rememberedValue9 == composer$Companion$Empty$1) {
                    Function1 function12 = new Function1() { // from class: com.android.compose.ui.graphics.DrawInOverlayKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj11) {
                            Modifier.Companion companion4 = Modifier.Companion;
                            ContainerElement containerElement = new ContainerElement(ContainerState.this);
                            companion4.then(containerElement);
                            return containerElement;
                        }
                    };
                    composerImpl2.updateRememberedValue(function12);
                    obj10 = function12;
                }
                composerImpl2.end(false);
                intRange = intRange2;
                kFunction2 = kFunction4;
                DrawInOverlayKt.FullScreenComposeViewInOverlay(null, (Function1) obj10, composerImpl2, 0, 1);
                composerImpl2.startReplaceGroup(-1255804066);
                Object rememberedValue10 = composerImpl2.rememberedValue();
                Object obj11 = rememberedValue10;
                if (rememberedValue10 == composer$Companion$Empty$1) {
                    DrawInContainerNode$$ExternalSyntheticLambda1 drawInContainerNode$$ExternalSyntheticLambda1 = new DrawInContainerNode$$ExternalSyntheticLambda1();
                    composerImpl2.updateRememberedValue(drawInContainerNode$$ExternalSyntheticLambda1);
                    obj11 = drawInContainerNode$$ExternalSyntheticLambda1;
                }
                z = false;
                composerImpl2.end(false);
                ?? drawInContainer$default = DrawInContainerKt.drawInContainer$default(companion3, containerState2, (Function0) obj11);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl2.end(false);
                companion = drawInContainer$default;
            } else {
                kFunction = kFunction3;
                intRange = intRange2;
                kFunction2 = kFunction4;
                z = false;
                companion = companion3;
            }
            composerImpl2.end(z);
            Modifier then = m2920borderOnFocusPOIbLQ4$default.then(companion);
            final long j2 = ((Color) m7animateColorAsStateeuL9pac.getValue()).value;
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(DrawModifierKt.drawWithCache(then, new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda15
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj12) {
                    CacheDrawScope cacheDrawScope = (CacheDrawScope) obj12;
                    Dimensions.INSTANCE.getClass();
                    long mo58toSizeXkaWNTQ = cacheDrawScope.mo58toSizeXkaWNTQ(Dimensions.SliderBackgroundFrameSize);
                    float f = 2;
                    int i5 = (int) (mo58toSizeXkaWNTQ >> 32);
                    float intBitsToFloat = (Float.intBitsToFloat(i5) * f) + Float.intBitsToFloat((int) (cacheDrawScope.cacheParams.mo360getSizeNHjbRc() >> 32));
                    int i6 = (int) (mo58toSizeXkaWNTQ & 4294967295L);
                    float intBitsToFloat2 = (Float.intBitsToFloat(i6) * f) + Float.intBitsToFloat((int) (cacheDrawScope.cacheParams.mo360getSizeNHjbRc() & 4294967295L));
                    final long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L);
                    Size.Companion companion4 = Size.Companion;
                    float f2 = -Float.intBitsToFloat(i5);
                    float f3 = -Float.intBitsToFloat(i6);
                    final long floatToRawIntBits2 = (Float.floatToRawIntBits(f2) << 32) | (Float.floatToRawIntBits(f3) & 4294967295L);
                    Offset.Companion companion5 = Offset.Companion;
                    float density = cacheDrawScope.getDensity() * Dimensions.SliderBackgroundRoundedCorner;
                    final long floatToRawIntBits3 = (Float.floatToRawIntBits(density) << 32) | (Float.floatToRawIntBits(density) & 4294967295L);
                    CornerRadius.Companion companion6 = CornerRadius.Companion;
                    final long j3 = j2;
                    return cacheDrawScope.onDrawBehind(new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda16
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj13) {
                            DrawScope.m541drawRoundRectuAw5IA$default((DrawScope) obj13, j3, floatToRawIntBits2, floatToRawIntBits, floatToRawIntBits3, null, 0.0f, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
                            return Unit.INSTANCE;
                        }
                    });
                }
            }), 1.0f);
            composerImpl2.startReplaceGroup(-734774439);
            boolean changedInstance5 = composerImpl2.changedInstance(brightnessSliderViewModel);
            Object rememberedValue11 = composerImpl2.rememberedValue();
            Object obj12 = rememberedValue11;
            if (changedInstance5 || rememberedValue11 == composer$Companion$Empty$1) {
                final int i5 = 1;
                Function1 function13 = new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda8
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj42) {
                        switch (i5) {
                            case 0:
                                final BrightnessSliderViewModel brightnessSliderViewModel2 = brightnessSliderViewModel;
                                return new DisposableEffectResult() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$BrightnessSliderContainer$lambda$32$lambda$31$$inlined$onDispose$1
                                    @Override // androidx.compose.runtime.DisposableEffectResult
                                    public final void dispose() {
                                        BrightnessSliderViewModel.this.setIsDragging(false);
                                    }
                                };
                            default:
                                MotionEvent motionEvent = (MotionEvent) obj42;
                                if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
                                    brightnessSliderViewModel.falsingInteractor.manager.isFalseTouch(10);
                                }
                                return Boolean.FALSE;
                        }
                    }
                };
                composerImpl2.updateRememberedValue(function13);
                obj12 = function13;
            }
            composerImpl2.end(false);
            Modifier pointerInteropFilter$default = PointerInteropFilter_androidKt.pointerInteropFilter$default(fillMaxWidth, (Function1) obj12);
            boolean booleanValue = ((Boolean) collectAsStateWithLifecycle2.getValue()).booleanValue();
            Function1 function14 = (Function1) kFunction;
            Function3 function3 = (Function3) kFunction2;
            Function1 function15 = (Function1) kFunction5;
            composerImpl2.startReplaceGroup(-734801966);
            boolean changedInstance6 = composerImpl2.changedInstance(brightnessSliderViewModel) | composerImpl2.changedInstance(coroutineScope);
            Object rememberedValue12 = composerImpl2.rememberedValue();
            if (changedInstance6 || rememberedValue12 == composer$Companion$Empty$1) {
                mutableState = mutableState2;
                z2 = false;
                final boolean z3 = false ? 1 : 0;
                Function1 function16 = new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda11
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj13) {
                        int i6 = z3;
                        int intValue = ((Integer) obj13).intValue();
                        switch (i6) {
                            case 0:
                                BrightnessSliderViewModel brightnessSliderViewModel2 = brightnessSliderViewModel;
                                brightnessSliderViewModel2.setIsDragging(true);
                                mutableState.setValue(Boolean.TRUE);
                                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new BrightnessSliderKt$BrightnessSliderContainer$4$5$1$1(brightnessSliderViewModel2, intValue, null), 7);
                                break;
                            default:
                                BrightnessSliderViewModel brightnessSliderViewModel3 = brightnessSliderViewModel;
                                brightnessSliderViewModel3.setIsDragging(false);
                                mutableState.setValue(Boolean.FALSE);
                                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new BrightnessSliderKt$BrightnessSliderContainer$4$6$1$1(brightnessSliderViewModel3, intValue, null), 7);
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(function16);
                obj = function16;
            } else {
                mutableState = mutableState2;
                z2 = false;
                obj = rememberedValue12;
            }
            Function1 function17 = (Function1) obj;
            composerImpl2.end(z2);
            composerImpl2.startReplaceGroup(-734795213);
            boolean changedInstance7 = composerImpl2.changedInstance(brightnessSliderViewModel) | composerImpl2.changedInstance(coroutineScope);
            Object rememberedValue13 = composerImpl2.rememberedValue();
            Object obj13 = rememberedValue13;
            if (changedInstance7 || rememberedValue13 == composer$Companion$Empty$1) {
                final int i6 = 1;
                Function1 function18 = new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda11
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj132) {
                        int i62 = i6;
                        int intValue = ((Integer) obj132).intValue();
                        switch (i62) {
                            case 0:
                                BrightnessSliderViewModel brightnessSliderViewModel2 = brightnessSliderViewModel;
                                brightnessSliderViewModel2.setIsDragging(true);
                                mutableState.setValue(Boolean.TRUE);
                                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new BrightnessSliderKt$BrightnessSliderContainer$4$5$1$1(brightnessSliderViewModel2, intValue, null), 7);
                                break;
                            default:
                                BrightnessSliderViewModel brightnessSliderViewModel3 = brightnessSliderViewModel;
                                brightnessSliderViewModel3.setIsDragging(false);
                                mutableState.setValue(Boolean.FALSE);
                                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new BrightnessSliderKt$BrightnessSliderContainer$4$6$1$1(brightnessSliderViewModel3, intValue, null), 7);
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(function18);
                obj13 = function18;
            }
            Function1 function19 = (Function1) obj13;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(-734758328);
            boolean changedInstance8 = composerImpl2.changedInstance(brightnessSliderViewModel) | composerImpl2.changedInstance(context);
            Object rememberedValue14 = composerImpl2.rememberedValue();
            Object obj14 = rememberedValue14;
            if (changedInstance8 || rememberedValue14 == composer$Companion$Empty$1) {
                BrightnessSliderKt$$ExternalSyntheticLambda3 brightnessSliderKt$$ExternalSyntheticLambda3 = new BrightnessSliderKt$$ExternalSyntheticLambda3(1, brightnessSliderViewModel, context);
                composerImpl2.updateRememberedValue(brightnessSliderKt$$ExternalSyntheticLambda3);
                obj14 = brightnessSliderKt$$ExternalSyntheticLambda3;
            }
            composerImpl2.end(false);
            BrightnessSlider(i3, intRange, function14, function3, policyRestriction, function15, function17, function19, booleanValue, pointerInteropFilter$default, (Function0) obj14, brightnessSliderViewModel.hapticsViewModelFactory, composerImpl2, 0, 0, 0);
            composerImpl = composerImpl2;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            final int i7 = 1;
            endRestartGroup2.block = new Function2(brightnessSliderViewModel, modifier, containerColors, i, i7) { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda7
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ BrightnessSliderViewModel f$0;
                public final /* synthetic */ Modifier f$1;
                public final /* synthetic */ ContainerColors f$2;

                {
                    this.$r8$classId = i7;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj22, Object obj32) {
                    int i52 = this.$r8$classId;
                    Composer composer2 = (Composer) obj22;
                    ((Integer) obj32).getClass();
                    switch (i52) {
                        case 0:
                            int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                            BrightnessSliderKt.BrightnessSliderContainer(this.f$0, this.f$1, this.f$2, composer2, updateChangedFlags);
                            break;
                        default:
                            int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(49);
                            BrightnessSliderKt.BrightnessSliderContainer(this.f$0, this.f$1, this.f$2, composer2, updateChangedFlags2);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
