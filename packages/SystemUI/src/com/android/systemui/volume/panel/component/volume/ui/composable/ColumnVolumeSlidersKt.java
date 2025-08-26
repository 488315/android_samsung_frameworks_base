package com.android.systemui.volume.panel.component.volume.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.AnimatedVisibilityScope;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.IconButtonColors;
import androidx.compose.material3.IconButtonDefaults;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.compose.PlatformSliderColors;
import com.android.compose.modifiers.PaddingKt;
import com.android.systemui.R;
import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public abstract class ColumnVolumeSlidersKt {
    /* JADX WARN: Removed duplicated region for block: B:77:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x022f  */
    /* JADX WARN: Type inference failed for: r6v29, types: [com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$ColumnVolumeSliders$1$1$1$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ColumnVolumeSliders(final List list, final boolean z, final Function1 function1, final PlatformSliderColors platformSliderColors, final boolean z2, final Modifier modifier, Composer composer, final int i) {
        float f;
        boolean z3;
        boolean z4;
        boolean z5;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(338998927);
        int i2 = i | (composerImpl.changedInstance(list) ? 4 : 2) | (composerImpl.changed(z) ? 32 : 16) | (composerImpl.changedInstance(function1) ? 256 : 128) | (composerImpl.changed(platformSliderColors) ? 2048 : 1024) | (composerImpl.changed(z2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192) | (composerImpl.changed(modifier) ? 131072 : 65536);
        if ((74899 & i2) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSliders (ColumnVolumeSliders.kt:80)");
            }
            if (list.isEmpty()) {
                throw new IllegalArgumentException("Failed requirement.");
            }
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            Modifier.Companion companion = Modifier.Companion;
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion, 1.0f);
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxWidth);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            SliderViewModel sliderViewModel = (SliderViewModel) CollectionsKt___CollectionsKt.first(list);
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(((SliderViewModel) CollectionsKt___CollectionsKt.first(list)).getSlider(), composerImpl);
            composerImpl.startReplaceGroup(-734902023);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.topSliderPadding (ColumnVolumeSliders.kt:330)");
            }
            TweenSpec tweenSpecTween$default = z2 ? AnimationSpecKt.tween$default(400, 300, null, 4) : AnimationSpecKt.tween$default(400, 500, null, 4);
            if (z2) {
                f = 72;
                Dp.Companion companion2 = Dp.Companion;
            } else {
                Dp.Companion companion3 = Dp.Companion;
                f = 0;
            }
            final State stateM8animateDpAsStateAjpBEmI = AnimateAsStateKt.m8animateDpAsStateAjpBEmI(f, tweenSpecTween$default, "TopVolumeSliderPadding", composerImpl, 384, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            Modifier modifierFillMaxWidth2 = SizeKt.fillMaxWidth(PaddingKt.padding$default(companion, null, new Function1() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$ColumnVolumeSliders$1$1$1$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    return Integer.valueOf(((Density) obj).mo52roundToPx0680j_4(((Dp) stateM8animateDpAsStateAjpBEmI.getValue()).value));
                }
            }, null, 11), 1.0f);
            SliderState sliderState = (SliderState) mutableStateCollectAsStateWithLifecycle.getValue();
            SliderHapticsViewModel.Factory sliderHapticsViewModelFactory = sliderViewModel.getSliderHapticsViewModelFactory();
            composerImpl.startReplaceGroup(-1268761861);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-1268776891);
            boolean zChangedInstance = composerImpl.changedInstance(sliderViewModel) | composerImpl.changed(mutableStateCollectAsStateWithLifecycle);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion4 = Composer.Companion;
            if (!zChangedInstance) {
                companion4.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    z3 = false;
                    objRememberedValue = new ColumnVolumeSlidersKt$$ExternalSyntheticLambda0(sliderViewModel, mutableStateCollectAsStateWithLifecycle, 0);
                    composerImpl.updateRememberedValue(objRememberedValue);
                } else {
                    z3 = false;
                }
                Function1 function12 = (Function1) objRememberedValue;
                composerImpl.end(z3);
                composerImpl.startReplaceGroup(-1268769599);
                boolean zChangedInstance2 = composerImpl.changedInstance(sliderViewModel) | composerImpl.changed(mutableStateCollectAsStateWithLifecycle);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance2) {
                    companion4.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        z4 = false;
                        objRememberedValue2 = new ColumnVolumeSlidersKt$$ExternalSyntheticLambda1(sliderViewModel, mutableStateCollectAsStateWithLifecycle, 0);
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    } else {
                        z4 = false;
                    }
                    Function0 function02 = (Function0) objRememberedValue2;
                    composerImpl.end(z4);
                    composerImpl.startReplaceGroup(-1268772032);
                    boolean zChangedInstance3 = composerImpl.changedInstance(sliderViewModel);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (!zChangedInstance3) {
                        companion4.getClass();
                        if (objRememberedValue3 == Composer.Companion.Empty) {
                            z5 = false;
                            objRememberedValue3 = new ColumnVolumeSlidersKt$$ExternalSyntheticLambda2(sliderViewModel, 0);
                            composerImpl.updateRememberedValue(objRememberedValue3);
                        } else {
                            z5 = false;
                        }
                        composerImpl.end(z5);
                        int i3 = i2 & 7168;
                        VolumeSliderKt.VolumeSlider(sliderState, function12, function02, platformSliderColors, modifierFillMaxWidth2, sliderHapticsViewModelFactory, (Function0) objRememberedValue3, null, composerImpl, i3, 0);
                        composerImpl.startReplaceGroup(-1268747483);
                        ExpandButtonLegacy(z, z2, function1, platformSliderColors, boxScopeInstance.align(companion, Alignment.Companion.CenterEnd), composerImpl, ((i2 >> 3) & 14) | ((i2 >> 9) & 112) | (i2 & 896) | i3);
                        composerImpl.end(false);
                        composerImpl.end(true);
                        AnimatedVisibilityKt.AnimatedVisibility(columnScopeInstance, z || !z2, (Modifier) null, EnterExitTransitionKt.expandVertically$default(AnimationSpecKt.tween$default(500, 0, null, 6), null, null, 14), EnterExitTransitionKt.shrinkVertically$default(AnimationSpecKt.tween$default(300, 0, null, 6), null, null, 14), "CollapsableSliders", ComposableLambdaKt.rememberComposableLambda(536411649, new Function3() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$ColumnVolumeSliders$1$2
                            /* JADX WARN: Removed duplicated region for block: B:42:0x01b2  */
                            /* JADX WARN: Removed duplicated region for block: B:47:0x01dd  */
                            /* JADX WARN: Removed duplicated region for block: B:52:0x0203  */
                            @Override // kotlin.jvm.functions.Function3
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                boolean z6;
                                ComposerImpl composerImpl2;
                                int i4 = 1;
                                AnimatedVisibilityScope animatedVisibilityScope = (AnimatedVisibilityScope) obj;
                                Composer composer2 = (Composer) obj2;
                                ((Number) obj3).intValue();
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSliders.<anonymous>.<anonymous> (ColumnVolumeSliders.kt:136)");
                                }
                                Modifier.Companion companion5 = Modifier.Companion;
                                Modifier modifierFillMaxWidth3 = SizeKt.fillMaxWidth(companion5, 1.0f);
                                Alignment.Companion.getClass();
                                BiasAlignment biasAlignment = Alignment.Companion.BottomCenter;
                                List list2 = list;
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl3.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composer2, modifierFillMaxWidth3);
                                ComposeUiNode.Companion.getClass();
                                Function0 function03 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl3.applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl3.startReusableNode();
                                if (composerImpl3.inserting) {
                                    composerImpl3.createNode(function03);
                                } else {
                                    composerImpl3.useNode();
                                }
                                Function2 function25 = ComposeUiNode.Companion.SetMeasurePolicy;
                                Updater.m337setimpl(composer2, measurePolicyMaybeCachedBoxMeasurePolicy2, function25);
                                Function2 function26 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                                Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope3, function26);
                                Function2 function27 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl3, currentCompositeKeyHash3, function27);
                                }
                                Function2 function28 = ComposeUiNode.Companion.SetModifier;
                                Updater.m337setimpl(composer2, modifierMaterializeModifier3, function28);
                                BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
                                Arrangement.INSTANCE.getClass();
                                ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(Arrangement.Top, Alignment.Companion.Start, composer2, 0);
                                int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl3.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composer2, companion5);
                                composerImpl3.startReusableNode();
                                if (composerImpl3.inserting) {
                                    composerImpl3.createNode(function03);
                                } else {
                                    composerImpl3.useNode();
                                }
                                Updater.m337setimpl(composer2, columnMeasurePolicy2, function25);
                                Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope4, function26);
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl3, currentCompositeKeyHash4, function27);
                                }
                                Updater.m337setimpl(composer2, modifierMaterializeModifier4, function28);
                                ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                                composerImpl3.startReplaceGroup(-1113978252);
                                int lastIndex = CollectionsKt__CollectionsKt.getLastIndex(list2);
                                if (1 <= lastIndex) {
                                    int i5 = 1;
                                    while (true) {
                                        SliderViewModel sliderViewModel2 = (SliderViewModel) list2.get(i5);
                                        MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(sliderViewModel2.getSlider(), composer2);
                                        Modifier modifierFillMaxWidth4 = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
                                        int size = ((list2.size() - i5) + i4) * 10;
                                        if (size < 0) {
                                            size = 0;
                                        }
                                        int i6 = 500 - size;
                                        if (i6 < 100) {
                                            i6 = 100;
                                        }
                                        final int i7 = 1;
                                        List list3 = list2;
                                        EnterTransition enterTransitionPlus = EnterExitTransitionKt.m5scaleInL8ZKhE$default(AnimationSpecKt.tween$default(i6, size, null, 4), 0.9f, 4).plus(EnterExitTransitionKt.expandVertically$default(AnimationSpecKt.tween$default(i6, size, null, 4), null, new Function1() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$$ExternalSyntheticLambda5
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj4) {
                                                Integer num = (Integer) obj4;
                                                switch (i7) {
                                                }
                                                return Integer.valueOf((int) (num.intValue() * 0.55f));
                                            }
                                        }, 2)).plus(EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(i6, size, null, 4), 2));
                                        int size2 = 300 - (((list3.size() - i5) + 1) * 10);
                                        int i8 = size2 < 100 ? 100 : size2;
                                        final int i9 = 0;
                                        Composer composer3 = composer2;
                                        Dp.Companion companion6 = Dp.Companion;
                                        Modifier modifierM129paddingqDBjuR0$default = androidx.compose.foundation.layout.PaddingKt.m129paddingqDBjuR0$default(animatedVisibilityScope.animateEnterExit(modifierFillMaxWidth4, enterTransitionPlus, EnterExitTransitionKt.m6scaleOutL8ZKhE$default(AnimationSpecKt.tween$default(i8, 0, null, 6), 0.9f, 4).plus(EnterExitTransitionKt.shrinkVertically$default(AnimationSpecKt.tween$default(i8, 0, null, 6), null, new Function1() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$$ExternalSyntheticLambda5
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj4) {
                                                Integer num = (Integer) obj4;
                                                switch (i9) {
                                                }
                                                return Integer.valueOf((int) (num.intValue() * 0.55f));
                                            }
                                        }, 2)).plus(EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(i8, 0, null, 6), 2))), 0.0f, 16, 0.0f, 0.0f, 13);
                                        SliderState sliderState2 = (SliderState) mutableStateCollectAsStateWithLifecycle2.getValue();
                                        SliderHapticsViewModel.Factory sliderHapticsViewModelFactory2 = sliderViewModel2.getSliderHapticsViewModelFactory();
                                        composerImpl3.startReplaceGroup(-1113940645);
                                        boolean zChangedInstance4 = composerImpl3.changedInstance(sliderViewModel2) | composerImpl3.changed(mutableStateCollectAsStateWithLifecycle2);
                                        Object objRememberedValue4 = composerImpl3.rememberedValue();
                                        Composer.Companion companion7 = Composer.Companion;
                                        if (!zChangedInstance4) {
                                            companion7.getClass();
                                            if (objRememberedValue4 == Composer.Companion.Empty) {
                                                objRememberedValue4 = new ColumnVolumeSlidersKt$$ExternalSyntheticLambda0(sliderViewModel2, mutableStateCollectAsStateWithLifecycle2, 1);
                                                composerImpl3.updateRememberedValue(objRememberedValue4);
                                            }
                                            Function1 function13 = (Function1) objRememberedValue4;
                                            composerImpl3.end(false);
                                            composerImpl3.startReplaceGroup(-1113931841);
                                            boolean zChangedInstance5 = composerImpl3.changedInstance(sliderViewModel2) | composerImpl3.changed(mutableStateCollectAsStateWithLifecycle2);
                                            Object objRememberedValue5 = composerImpl3.rememberedValue();
                                            if (!zChangedInstance5) {
                                                companion7.getClass();
                                                if (objRememberedValue5 == Composer.Companion.Empty) {
                                                    objRememberedValue5 = new ColumnVolumeSlidersKt$$ExternalSyntheticLambda1(sliderViewModel2, mutableStateCollectAsStateWithLifecycle2, 1);
                                                    composerImpl3.updateRememberedValue(objRememberedValue5);
                                                }
                                                Function0 function04 = (Function0) objRememberedValue5;
                                                composerImpl3.end(false);
                                                composerImpl3.startReplaceGroup(-1113934658);
                                                boolean zChangedInstance6 = composerImpl3.changedInstance(sliderViewModel2);
                                                Object objRememberedValue6 = composerImpl3.rememberedValue();
                                                if (!zChangedInstance6) {
                                                    companion7.getClass();
                                                    if (objRememberedValue6 == Composer.Companion.Empty) {
                                                        objRememberedValue6 = new ColumnVolumeSlidersKt$$ExternalSyntheticLambda2(sliderViewModel2, 1);
                                                        composerImpl3.updateRememberedValue(objRememberedValue6);
                                                    }
                                                    composerImpl3.end(false);
                                                    composerImpl2 = composerImpl3;
                                                    composer2 = composer3;
                                                    VolumeSliderKt.VolumeSlider(sliderState2, function13, function04, platformSliderColors, modifierM129paddingqDBjuR0$default, sliderHapticsViewModelFactory2, (Function0) objRememberedValue6, null, composer2, 0, 128);
                                                    z6 = true;
                                                    if (i5 == lastIndex) {
                                                        break;
                                                    }
                                                    i5++;
                                                    composerImpl3 = composerImpl2;
                                                    i4 = 1;
                                                    list2 = list3;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    z6 = true;
                                    composerImpl2 = composerImpl3;
                                }
                                composerImpl2.end(false);
                                composerImpl2.end(z6);
                                composerImpl2.end(z6);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        }, composerImpl), composerImpl, 1797126, 2);
                        composerImpl = composerImpl;
                        composerImpl.end(true);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(list, z, function1, platformSliderColors, z2, modifier, i) { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$$ExternalSyntheticLambda3
                public final /* synthetic */ List f$0;
                public final /* synthetic */ boolean f$1;
                public final /* synthetic */ Function1 f$2;
                public final /* synthetic */ PlatformSliderColors f$3;
                public final /* synthetic */ boolean f$4;
                public final /* synthetic */ Modifier f$5;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    PlatformSliderColors platformSliderColors2 = this.f$3;
                    boolean z6 = this.f$4;
                    Modifier modifier2 = this.f$5;
                    ColumnVolumeSlidersKt.ColumnVolumeSliders(this.f$0, this.f$1, this.f$2, platformSliderColors2, z6, modifier2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ExpandButtonLegacy(final boolean z, boolean z2, final Function1 function1, final PlatformSliderColors platformSliderColors, Modifier modifier, Composer composer, final int i) {
        int i2;
        final String strStringResource;
        boolean z3;
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1510574524);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(z) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(z2) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function1) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= (i & 4096) == 0 ? composerImpl.changed(platformSliderColors) : composerImpl.changedInstance(platformSliderColors) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(modifier) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i2 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            z3 = z2;
            modifier2 = modifier;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.ExpandButtonLegacy (ColumnVolumeSliders.kt:182)");
            }
            if (z) {
                composerImpl.startReplaceGroup(793949332);
                strStringResource = StringResources_androidKt.stringResource(R.string.volume_panel_expanded_sliders, composerImpl);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(794032691);
                strStringResource = StringResources_androidKt.stringResource(R.string.volume_panel_collapsed_sliders, composerImpl);
                composerImpl.end(false);
            }
            z3 = z2;
            AnimatedVisibilityKt.AnimatedVisibility(z3, modifier, EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(350, 350, null, 4), 2).plus(EnterExitTransitionKt.m5scaleInL8ZKhE$default(AnimationSpecKt.tween$default(350, 350, null, 4), 0.8f, 4)), EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(350, 500, null, 4), 2).plus(EnterExitTransitionKt.m6scaleOutL8ZKhE$default(AnimationSpecKt.tween$default(350, 500, null, 4), 0.8f, 4)), null, ComposableLambdaKt.rememberComposableLambda(-1830541852, new Function3() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt.ExpandButtonLegacy.1
                /* JADX WARN: Removed duplicated region for block: B:23:0x00c1  */
                /* JADX WARN: Removed duplicated region for block: B:9:0x0046  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.ExpandButtonLegacy.<anonymous> (ColumnVolumeSliders.kt:195)");
                    }
                    Dp.Companion companion = Dp.Companion;
                    Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(Modifier.Companion, 64);
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    composerImpl2.startReplaceGroup(927431229);
                    final String str = strStringResource;
                    boolean zChanged = composerImpl2.changed(str);
                    Object objRememberedValue = composerImpl2.rememberedValue();
                    Composer.Companion companion2 = Composer.Companion;
                    if (!zChanged) {
                        companion2.getClass();
                        if (objRememberedValue == Composer.Companion.Empty) {
                            objRememberedValue = new Function1() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$ExpandButtonLegacy$1$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj4) {
                                    SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj4;
                                    Role.Companion.getClass();
                                    SemanticsPropertiesKt.m719setRolekuIjeqM(semanticsPropertyReceiver, Role.Switch);
                                    SemanticsPropertiesKt.setStateDescription(semanticsPropertyReceiver, str);
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl2.updateRememberedValue(objRememberedValue);
                        }
                    }
                    composerImpl2.end(false);
                    Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierM140size3ABfNKs, false, (Function1) objRememberedValue);
                    IconButtonDefaults iconButtonDefaults = IconButtonDefaults.INSTANCE;
                    PlatformSliderColors platformSliderColors2 = platformSliderColors;
                    long j = platformSliderColors2.indicatorColor;
                    iconButtonDefaults.getClass();
                    long j2 = platformSliderColors2.iconColor;
                    if ((12 & 1) != 0) {
                        Color.Companion.getClass();
                        j = Color.Unspecified;
                    }
                    long j3 = j;
                    Color.Companion companion3 = Color.Companion;
                    companion3.getClass();
                    long j4 = Color.Unspecified;
                    companion3.getClass();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.material3.IconButtonDefaults.filledIconButtonColors (IconButtonDefaults.kt:323)");
                    }
                    MaterialTheme.INSTANCE.getClass();
                    IconButtonColors iconButtonColorsM265copyjRlVdoo = IconButtonDefaults.getDefaultFilledIconButtonColors$material3_release(MaterialTheme.getColorScheme(composerImpl2)).m265copyjRlVdoo(j3, j2, j4, j4);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl2.startReplaceGroup(927435937);
                    final Function1 function12 = function1;
                    boolean zChanged2 = composerImpl2.changed(function12);
                    final boolean z4 = z;
                    boolean zChanged3 = zChanged2 | composerImpl2.changed(z4);
                    Object objRememberedValue2 = composerImpl2.rememberedValue();
                    if (!zChanged3) {
                        companion2.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            objRememberedValue2 = new Function0() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$ExpandButtonLegacy$1$$ExternalSyntheticLambda1
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    function12.mo781invoke(Boolean.valueOf(!z4));
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl2.updateRememberedValue(objRememberedValue2);
                        }
                    }
                    composerImpl2.end(false);
                    IconButtonKt.IconButton(1572864, 52, null, iconButtonColorsM265copyjRlVdoo, composerImpl2, modifierSemantics, null, (Function0) objRememberedValue2, ComposableLambdaKt.rememberComposableLambda(242978822, new Function2() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt.ExpandButtonLegacy.1.3
                        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj4, Object obj5) {
                            Composer composer3 = (Composer) obj4;
                            if ((((Number) obj5).intValue() & 3) == 2) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.ExpandButtonLegacy.<anonymous>.<anonymous> (ColumnVolumeSliders.kt:208)");
                                    }
                                    IconKt.m270Iconww6aTOc(PainterResources_androidKt.painterResource(z4 ? R.drawable.ic_filled_arrow_down : R.drawable.ic_filled_arrow_up, composer3, 0), (String) null, (Modifier) null, 0L, composer3, 48, 12);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl2), false);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, ((i2 >> 3) & 14) | 196608 | ((i2 >> 9) & 112), 16);
            modifier2 = modifier;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final boolean z4 = z3;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    PlatformSliderColors platformSliderColors2 = platformSliderColors;
                    Modifier modifier3 = modifier2;
                    ColumnVolumeSlidersKt.ExpandButtonLegacy(z, z4, function1, platformSliderColors2, modifier3, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
