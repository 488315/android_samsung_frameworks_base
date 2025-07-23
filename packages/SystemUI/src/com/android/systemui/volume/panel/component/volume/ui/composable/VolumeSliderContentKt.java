package com.android.systemui.volume.panel.component.volume.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.foundation.BasicMarqueeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Constraints;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.IntCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class VolumeSliderContentKt {
    public static final void VolumeSliderContent(final String str, final boolean z, final String str2, final Modifier modifier, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(694921196);
        int i2 = i | (composerImpl.changed(str) ? 4 : 2) | (composerImpl.changed(z) ? 32 : 16) | (composerImpl.changed(str2) ? 256 : 128);
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderContent (VolumeSliderContent.kt:62)");
            }
            composerImpl.startReplaceGroup(-398663602);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.animateContentHeight (VolumeSliderContent.kt:141)");
            }
            composerImpl.startReplaceGroup(-518649456);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final MutableState mutableState = (MutableState) rememberedValue;
            composerImpl.end(false);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            final CoroutineScope coroutineScope = (CoroutineScope) rememberedValue2;
            composerImpl.startReplaceGroup(-518644557);
            boolean changedInstance = composerImpl.changedInstance(coroutineScope);
            Object rememberedValue3 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue3 == composer$Companion$Empty$1) {
                rememberedValue3 = new Function3() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderContentKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        MeasureResult layout$1;
                        MeasureScope measureScope = (MeasureScope) obj;
                        final Placeable mo608measureBRTryo0 = ((Measurable) obj2).mo608measureBRTryo0(((Constraints) obj3).value);
                        MutableState mutableState2 = mutableState;
                        Animatable animatable = (Animatable) mutableState2.getValue();
                        if (animatable == null) {
                            Integer valueOf = Integer.valueOf(mo608measureBRTryo0.height);
                            int i3 = IntCompanionObject.$r8$clinit;
                            Animatable animatable2 = new Animatable(valueOf, VectorConvertersKt.IntToVector, null, null, 12, null);
                            mutableState2.setValue(animatable2);
                            animatable = animatable2;
                        } else {
                            BuildersKt.launch$default(CoroutineScope.this, null, null, new VolumeSliderContentKt$animateContentHeight$1$1$anim$2(animatable, mo608measureBRTryo0, null), 3);
                        }
                        layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, ((Number) animatable.internalState.getValue()).intValue(), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderContentKt$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo779invoke(Object obj4) {
                                ((Placeable.PlacementScope) obj4).place(Placeable.this, 0, 0, 0.0f);
                                return Unit.INSTANCE;
                            }
                        });
                        return layout$1;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue3);
            }
            composerImpl.end(false);
            Modifier layout = LayoutModifierKt.layout(modifier, (Function3) rememberedValue3);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            VolumeSliderContentMeasurePolicy volumeSliderContentMeasurePolicy = new VolumeSliderContentMeasurePolicy(z);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, layout);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, volumeSliderContentMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            Modifier.Companion companion = Modifier.Companion;
            Modifier m27basicMarquee1Mj1MLw$default = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(LayoutIdKt.layoutId(companion, VolumeSliderContentComponent.Label), 0, 63);
            MaterialTheme.INSTANCE.getClass();
            TextKt.m316Text4IGK_g(str, m27basicMarquee1Mj1MLw$default, ((Color) composerImpl.consume(ContentColorKt.LocalContentColor)).value, 0L, null, null, null, 0L, null, null, 0L, 0, false, 1, 0, null, MaterialTheme.getTypography(composerImpl).titleMedium, composerImpl, (i2 & 14) | 48, 3072, 57336);
            composerImpl = composerImpl;
            composerImpl.startReplaceGroup(-814747478);
            if (str2 != null) {
                Modifier layoutId = LayoutIdKt.layoutId(companion, VolumeSliderContentComponent.DisabledMessage);
                Alignment.Companion.getClass();
                BiasAlignment.Vertical vertical = Alignment.Companion.Top;
                AnimatedVisibilityKt.AnimatedVisibility(!z, layoutId, EnterExitTransitionKt.expandVertically$default(null, vertical, null, 13).plus(EnterExitTransitionKt.fadeIn$default(null, 3)), EnterExitTransitionKt.shrinkVertically$default(null, vertical, null, 13).plus(EnterExitTransitionKt.fadeOut$default(null, 3)), null, ComposableLambdaKt.rememberComposableLambda(1284708907, new Function3() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderContentKt$VolumeSliderContent$1$1$1
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        Composer composer2 = (Composer) obj2;
                        ((Number) obj3).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderContent.<anonymous>.<anonymous>.<anonymous> (VolumeSliderContent.kt:81)");
                        }
                        Modifier m27basicMarquee1Mj1MLw$default2 = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(Modifier.Companion, 0, 63);
                        MaterialTheme.INSTANCE.getClass();
                        TextStyle textStyle = MaterialTheme.getTypography(composer2).bodySmall;
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        TextKt.m316Text4IGK_g(str2, m27basicMarquee1Mj1MLw$default2, ((Color) composerImpl2.consume(ContentColorKt.LocalContentColor)).value, 0L, null, null, null, 0L, null, null, 0L, 0, false, 1, 0, null, textStyle, composerImpl2, 48, 3072, 57336);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 200112, 16);
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(str, z, str2, modifier, i) { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderContentKt$$ExternalSyntheticLambda0
                public final /* synthetic */ String f$0;
                public final /* synthetic */ boolean f$1;
                public final /* synthetic */ String f$2;
                public final /* synthetic */ Modifier f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(3073);
                    String str3 = this.f$2;
                    Modifier modifier2 = this.f$3;
                    VolumeSliderContentKt.VolumeSliderContent(this.f$0, this.f$1, str3, modifier2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
