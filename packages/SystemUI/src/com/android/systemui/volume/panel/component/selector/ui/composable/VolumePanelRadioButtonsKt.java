package com.android.systemui.volume.panel.component.selector.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
import androidx.compose.foundation.layout.OffsetKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.foundation.shape.CornerSizeKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntOffset;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.IntCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public abstract class VolumePanelRadioButtonsKt {
    public static final ComposableLambdaImpl Empty;

    static {
        ComposableSingletons$VolumePanelRadioButtonsKt.INSTANCE.getClass();
        Empty = ComposableSingletons$VolumePanelRadioButtonsKt.f118lambda1;
    }

    /* JADX WARN: Removed duplicated region for block: B:109:0x0423  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0464  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0468  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0489  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x04a0  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0518  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0230  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0286  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x028a  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x02c1  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x02f9  */
    /* renamed from: VolumePanelRadioButtonBar-cjTkxnM, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m3220VolumePanelRadioButtonBarcjTkxnM(Modifier modifier, float f, float f2, float f3, CornerSize cornerSize, CornerSize cornerSize2, VolumePanelRadioButtonBarColors volumePanelRadioButtonBarColors, final Function1 function1, Composer composer, final int i) {
        Modifier modifier2;
        float f4;
        float f5;
        float f6;
        CornerSize cornerSize3;
        CornerSize cornerSize4;
        final VolumePanelRadioButtonBarColors volumePanelRadioButtonBarColors2;
        VolumePanelRadioButtonBarScopeImpl volumePanelRadioButtonBarScopeImpl;
        boolean zChangedInstance;
        Object obj;
        int currentCompositeKeyHash;
        int size;
        final int i2;
        Object objRememberedValue;
        int currentCompositeKeyHash2;
        Function2 function2;
        int size2;
        final int i3;
        ComposerImpl composerImpl;
        final VolumePanelRadioButtonBarColors volumePanelRadioButtonBarColors3;
        final float f7;
        final float f8;
        final float f9;
        final Modifier modifier3;
        final CornerSize cornerSize5;
        final CornerSize cornerSize6;
        Object obj2;
        boolean z;
        Object obj3;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(595676240);
        if (((i | 748982 | (composerImpl2.changedInstance(function1) ? 8388608 : 4194304)) & 4793491) == 4793490 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            modifier3 = modifier;
            f8 = f2;
            f7 = f3;
            cornerSize5 = cornerSize;
            cornerSize6 = cornerSize2;
            volumePanelRadioButtonBarColors3 = volumePanelRadioButtonBarColors;
            composerImpl = composerImpl2;
            f9 = f;
        } else {
            composerImpl2.startDefaults();
            if ((i & 1) == 0 || composerImpl2.getDefaultsInvalid()) {
                modifier2 = Modifier.Companion;
                VolumePanelRadioButtonBarDefaults volumePanelRadioButtonBarDefaults = VolumePanelRadioButtonBarDefaults.INSTANCE;
                volumePanelRadioButtonBarDefaults.getClass();
                f4 = VolumePanelRadioButtonBarDefaults.DefaultIndicatorBackgroundPadding;
                volumePanelRadioButtonBarDefaults.getClass();
                f5 = VolumePanelRadioButtonBarDefaults.DefaultSpacing;
                volumePanelRadioButtonBarDefaults.getClass();
                f6 = VolumePanelRadioButtonBarDefaults.DefaultLabelIndicatorBackgroundSpacing;
                volumePanelRadioButtonBarDefaults.getClass();
                CornerSize cornerSizeM186CornerSize0680j_4 = CornerSizeKt.m186CornerSize0680j_4(VolumePanelRadioButtonBarDefaults.DefaultIndicatorCornerRadius);
                volumePanelRadioButtonBarDefaults.getClass();
                CornerSize cornerSizeM186CornerSize0680j_42 = CornerSizeKt.m186CornerSize0680j_4(VolumePanelRadioButtonBarDefaults.DefaultIndicatorBackgroundCornerRadius);
                volumePanelRadioButtonBarDefaults.getClass();
                composerImpl2.startReplaceGroup(738317083);
                MaterialTheme.INSTANCE.getClass();
                long j = MaterialTheme.getColorScheme(composerImpl2).tertiaryContainer;
                long j2 = MaterialTheme.getColorScheme(composerImpl2).surface;
                long j3 = MaterialTheme.getColorScheme(composerImpl2).onSurfaceVariant;
                long j4 = MaterialTheme.getColorScheme(composerImpl2).onTertiaryContainer;
                long j5 = MaterialTheme.getColorScheme(composerImpl2).onSurfaceVariant;
                long j6 = MaterialTheme.getColorScheme(composerImpl2).onSurface;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonBarDefaults.defaultColors (VolumePanelRadioButtons.kt:316)");
                }
                VolumePanelRadioButtonBarColors volumePanelRadioButtonBarColors4 = new VolumePanelRadioButtonBarColors(j, j2, j3, j4, j5, j6, null);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl2.end(false);
                cornerSize3 = cornerSizeM186CornerSize0680j_4;
                cornerSize4 = cornerSizeM186CornerSize0680j_42;
                volumePanelRadioButtonBarColors2 = volumePanelRadioButtonBarColors4;
            } else {
                composerImpl2.skipToGroupEnd();
                modifier2 = modifier;
                f4 = f;
                f5 = f2;
                f6 = f3;
                cornerSize3 = cornerSize;
                cornerSize4 = cornerSize2;
                volumePanelRadioButtonBarColors2 = volumePanelRadioButtonBarColors;
            }
            composerImpl2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonBar (VolumePanelRadioButtons.kt:85)");
            }
            VolumePanelRadioButtonBarScopeImpl volumePanelRadioButtonBarScopeImpl2 = new VolumePanelRadioButtonBarScopeImpl();
            function1.mo781invoke(volumePanelRadioButtonBarScopeImpl2);
            if (volumePanelRadioButtonBarScopeImpl2.selectedIndex == -1) {
                throw new IllegalArgumentException("At least one item should be selected");
            }
            List list = volumePanelRadioButtonBarScopeImpl2.items;
            Object objRememberedValue2 = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            Object obj4 = objRememberedValue2;
            if (objRememberedValue2 == composer$Companion$Empty$1) {
                CoroutineScope coroutineScopeCreateCompositionCoroutineScope = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                composerImpl2.updateRememberedValue(coroutineScopeCreateCompositionCoroutineScope);
                obj4 = coroutineScopeCreateCompositionCoroutineScope;
            }
            final CoroutineScope coroutineScope = (CoroutineScope) obj4;
            composerImpl2.startReplaceGroup(131944836);
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            Object obj5 = objRememberedValue3;
            if (objRememberedValue3 == composer$Companion$Empty$1) {
                int i4 = IntCompanionObject.$r8$clinit;
                Animatable animatable = new Animatable(-1, VectorConvertersKt.IntToVector, null, null, 12, null);
                composerImpl2.updateRememberedValue(animatable);
                obj5 = animatable;
            }
            final Animatable animatable2 = (Animatable) obj5;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(132075906);
            Density density = (Density) composerImpl2.consume(CompositionLocalsKt.LocalDensity);
            Dp.Companion companion = Dp.Companion;
            int iMo52roundToPx0680j_4 = density.mo52roundToPx0680j_4(f5 - (2 * f4));
            if (iMo52roundToPx0680j_4 < 0) {
                iMo52roundToPx0680j_4 = 0;
            }
            ArrayList arrayList = (ArrayList) list;
            int size3 = arrayList.size();
            float f10 = f5;
            int i5 = volumePanelRadioButtonBarScopeImpl2.selectedIndex;
            float f11 = f6;
            composerImpl2.startReplaceGroup(-1371013326);
            boolean zChangedInstance2 = composerImpl2.changedInstance(coroutineScope) | composerImpl2.changedInstance(animatable2);
            Object objRememberedValue4 = composerImpl2.rememberedValue();
            Object obj6 = objRememberedValue4;
            if (zChangedInstance2 || objRememberedValue4 == composer$Companion$Empty$1) {
                Function1 function12 = new Function1() { // from class: com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonsKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj7) {
                        BuildersKt.launch$default(coroutineScope, null, null, new VolumePanelRadioButtonsKt$VolumePanelRadioButtonBar$1$1$1$1(animatable2, ((Integer) obj7).intValue(), null), 3);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(function12);
                obj6 = function12;
            }
            composerImpl2.end(false);
            BarMeasurePolicy barMeasurePolicy = new BarMeasurePolicy(size3, i5, iMo52roundToPx0680j_4, (Function1) obj6);
            composerImpl2.end(false);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifier2);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            Modifier modifier4 = modifier2;
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
            Function2 function22 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m337setimpl(composerImpl2, barMeasurePolicy, function22);
            Function2 function23 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, function23);
            Function2 function24 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting) {
                volumePanelRadioButtonBarScopeImpl = volumePanelRadioButtonBarScopeImpl2;
            } else {
                volumePanelRadioButtonBarScopeImpl = volumePanelRadioButtonBarScopeImpl2;
                if (!Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                }
                Function2 function25 = ComposeUiNode.Companion.SetModifier;
                Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, function25);
                Modifier.Companion companion2 = Modifier.Companion;
                Modifier modifierLayoutId = LayoutIdKt.layoutId(companion2, RadioButtonBarComponent.ButtonsBackground);
                ArrayList arrayList2 = arrayList;
                long j7 = volumePanelRadioButtonBarColors2.indicatorBackgroundColor;
                RoundedCornerShape roundedCornerShape = RoundedCornerShapeKt.CircleShape;
                SpacerKt.Spacer(composerImpl2, BackgroundKt.m26backgroundbw27NRU(modifierLayoutId, j7, new RoundedCornerShape(cornerSize4, cornerSize4, cornerSize4, cornerSize4)));
                Modifier modifierLayoutId2 = LayoutIdKt.layoutId(companion2, RadioButtonBarComponent.Indicator);
                composerImpl2.startReplaceGroup(-1371135599);
                zChangedInstance = composerImpl2.changedInstance(animatable2);
                Object objRememberedValue5 = composerImpl2.rememberedValue();
                obj = objRememberedValue5;
                if (!zChangedInstance || objRememberedValue5 == composer$Companion$Empty$1) {
                    Function1 function13 = new Function1() { // from class: com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonsKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj7) {
                            return IntOffset.m849boximpl((((Number) animatable2.internalState.getValue()).intValue() << 32) | (0 & 4294967295L));
                        }
                    };
                    composerImpl2.updateRememberedValue(function13);
                    obj = function13;
                }
                composerImpl2.end(false);
                SpacerKt.Spacer(composerImpl2, BackgroundKt.m26backgroundbw27NRU(PaddingKt.m125padding3ABfNKs(OffsetKt.offset(modifierLayoutId2, (Function1) obj), f4), volumePanelRadioButtonBarColors2.indicatorColor, new RoundedCornerShape(cornerSize3, cornerSize3, cornerSize3, cornerSize3)));
                Modifier modifierM125padding3ABfNKs = PaddingKt.m125padding3ABfNKs(LayoutIdKt.layoutId(companion2, RadioButtonBarComponent.Buttons), f4);
                Arrangement.INSTANCE.getClass();
                Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(f10);
                Alignment.Companion.getClass();
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM92spacedBy0680j_4, Alignment.Companion.Top, composerImpl2, 0);
                currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl2, modifierM125padding3ABfNKs);
                composerImpl2.startReusableNode();
                if (composerImpl2.inserting) {
                    composerImpl2.useNode();
                } else {
                    composerImpl2.createNode(function0);
                }
                Updater.m337setimpl(composerImpl2, rowMeasurePolicy, function22);
                Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope2, function23);
                if (!composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function24);
                }
                Updater.m337setimpl(composerImpl2, modifierMaterializeModifier2, function25);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                composerImpl2.startReplaceGroup(377449402);
                size = arrayList2.size();
                i2 = 0;
                while (i2 < size) {
                    final ArrayList arrayList3 = arrayList2;
                    final Item item = (Item) arrayList3.get(i2);
                    VolumePanelRadioButtonBarScopeImpl volumePanelRadioButtonBarScopeImpl3 = volumePanelRadioButtonBarScopeImpl;
                    final boolean z2 = i2 == volumePanelRadioButtonBarScopeImpl3.selectedIndex;
                    Modifier modifierWeight = rowScopeInstance.weight(SizeKt.m131height3ABfNKs(Modifier.Companion, 48), 1.0f, true);
                    composerImpl2.startReplaceGroup(377459449);
                    boolean zChanged = composerImpl2.changed(item) | composerImpl2.changed(z2);
                    Object objRememberedValue6 = composerImpl2.rememberedValue();
                    if (!zChanged) {
                        obj2 = objRememberedValue6;
                        if (objRememberedValue6 == Composer.Companion.Empty) {
                            Function1 function14 = new Function1() { // from class: com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonsKt$$ExternalSyntheticLambda2
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj7) {
                                    SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj7;
                                    String str = item.contentDescription;
                                    if (str != null) {
                                        SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, str);
                                    }
                                    Role.Companion.getClass();
                                    SemanticsPropertiesKt.m719setRolekuIjeqM(semanticsPropertyReceiver, Role.Switch);
                                    SemanticsProperties.INSTANCE.getClass();
                                    SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.Selected;
                                    KProperty kProperty = SemanticsPropertiesKt.$$delegatedProperties[19];
                                    semanticsPropertyKey.setValue(semanticsPropertyReceiver, Boolean.valueOf(z2));
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl2.updateRememberedValue(function14);
                            obj2 = function14;
                        }
                    }
                    composerImpl2.end(false);
                    Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierWeight, false, (Function1) obj2);
                    composerImpl2.startReplaceGroup(377473613);
                    boolean zChangedInstance3 = composerImpl2.changedInstance(arrayList3) | composerImpl2.changed(i2);
                    Object objRememberedValue7 = composerImpl2.rememberedValue();
                    if (zChangedInstance3 || objRememberedValue7 == Composer.Companion.Empty) {
                        z = false;
                        final boolean z3 = false ? 1 : 0;
                        Function0 function02 = new Function0() { // from class: com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonsKt$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                switch (z3) {
                                    case 0:
                                        ((Item) arrayList3.get(i2)).onItemSelected.invoke();
                                        break;
                                    default:
                                        ((Item) arrayList3.get(i2)).onItemSelected.invoke();
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl2.updateRememberedValue(function02);
                        obj3 = function02;
                    } else {
                        z = false;
                        obj3 = objRememberedValue7;
                    }
                    composerImpl2.end(z);
                    Modifier modifierM34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(modifierSemantics, null, null, false, null, null, (Function0) obj3, 28);
                    Arrangement.INSTANCE.getClass();
                    Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
                    Alignment.Companion.getClass();
                    RowMeasurePolicy rowMeasurePolicy2 = RowKt.rowMeasurePolicy(arrangement$Center$1, Alignment.Companion.CenterVertically, composerImpl2, 54);
                    int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl2.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl2, modifierM34clickableO2vRcR0$default);
                    ComposeUiNode.Companion.getClass();
                    CornerSize cornerSize7 = cornerSize3;
                    Function0 function03 = ComposeUiNode.Companion.Constructor;
                    composerImpl2.startReusableNode();
                    CornerSize cornerSize8 = cornerSize4;
                    if (composerImpl2.inserting) {
                        composerImpl2.createNode(function03);
                    } else {
                        composerImpl2.useNode();
                    }
                    Updater.m337setimpl(composerImpl2, rowMeasurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope3, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function26 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl2, currentCompositeKeyHash4, function26);
                    }
                    Updater.m337setimpl(composerImpl2, modifierMaterializeModifier3, ComposeUiNode.Companion.SetModifier);
                    final RowScopeInstance rowScopeInstance2 = RowScopeInstance.INSTANCE;
                    composerImpl2.startReplaceGroup(941564428);
                    if (item.icon != Empty) {
                        CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(z2 ? volumePanelRadioButtonBarColors2.selectedIconColor : volumePanelRadioButtonBarColors2.iconColor)), ComposableLambdaKt.rememberComposableLambda(-1177006516, new Function2() { // from class: com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonsKt$VolumePanelRadioButtonBar$2$2$3$1
                            /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj7, Object obj8) {
                                Composer composer2 = (Composer) obj7;
                                if ((((Number) obj8).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonBar.<anonymous>.<anonymous>.<anonymous>.<anonymous> (VolumePanelRadioButtons.kt:145)");
                                        }
                                        ((Item) arrayList3.get(i2)).icon.invoke(rowScopeInstance2, composer2, 0);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, composerImpl2), composerImpl2, 56);
                    }
                    composerImpl2.end(false);
                    composerImpl2.end(true);
                    i2++;
                    arrayList2 = arrayList3;
                    volumePanelRadioButtonBarScopeImpl = volumePanelRadioButtonBarScopeImpl3;
                    cornerSize3 = cornerSize7;
                    cornerSize4 = cornerSize8;
                }
                final VolumePanelRadioButtonBarScopeImpl volumePanelRadioButtonBarScopeImpl4 = volumePanelRadioButtonBarScopeImpl;
                CornerSize cornerSize9 = cornerSize3;
                CornerSize cornerSize10 = cornerSize4;
                final ArrayList arrayList4 = arrayList2;
                composerImpl2.end(false);
                composerImpl2.end(true);
                Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(LayoutIdKt.layoutId(Modifier.Companion, RadioButtonBarComponent.Labels), f4, f11, f4, 0.0f, 8);
                composerImpl2.startReplaceGroup(-1371057845);
                objRememberedValue = composerImpl2.rememberedValue();
                Object obj7 = objRememberedValue;
                if (objRememberedValue == Composer.Companion.Empty) {
                    VolumePanelRadioButtonsKt$$ExternalSyntheticLambda4 volumePanelRadioButtonsKt$$ExternalSyntheticLambda4 = new VolumePanelRadioButtonsKt$$ExternalSyntheticLambda4();
                    composerImpl2.updateRememberedValue(volumePanelRadioButtonsKt$$ExternalSyntheticLambda4);
                    obj7 = volumePanelRadioButtonsKt$$ExternalSyntheticLambda4;
                }
                composerImpl2.end(false);
                Modifier modifierClearAndSetSemantics = SemanticsModifierKt.clearAndSetSemantics(modifierM129paddingqDBjuR0$default, (Function1) obj7);
                Arrangement.INSTANCE.getClass();
                Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_42 = Arrangement.m92spacedBy0680j_4(f10);
                Alignment.Companion.getClass();
                RowMeasurePolicy rowMeasurePolicy3 = RowKt.rowMeasurePolicy(spacedAlignedM92spacedBy0680j_42, Alignment.Companion.Top, composerImpl2, 0);
                currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl2.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl2, modifierClearAndSetSemantics);
                ComposeUiNode.Companion.getClass();
                Function0 function04 = ComposeUiNode.Companion.Constructor;
                composerImpl2.startReusableNode();
                if (composerImpl2.inserting) {
                    composerImpl2.useNode();
                } else {
                    composerImpl2.createNode(function04);
                }
                Updater.m337setimpl(composerImpl2, rowMeasurePolicy3, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope4, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (!composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl2, currentCompositeKeyHash2, function2);
                }
                Updater.m337setimpl(composerImpl2, modifierMaterializeModifier4, ComposeUiNode.Companion.SetModifier);
                RowScopeInstance rowScopeInstance3 = RowScopeInstance.INSTANCE;
                composerImpl2.startReplaceGroup(377511555);
                size2 = arrayList4.size();
                i3 = 0;
                ComposerImpl composerImpl3 = composerImpl2;
                while (i3 < size2) {
                    float f12 = 4;
                    Modifier modifierWeight2 = rowScopeInstance3.weight(Modifier.Companion, 1.0f, true);
                    RoundedCornerShape roundedCornerShapeM187RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(f12);
                    PaddingValuesImpl paddingValuesImplM120PaddingValues0680j_4 = PaddingKt.m120PaddingValues0680j_4(f12);
                    composerImpl3.startReplaceGroup(377517197);
                    boolean zChangedInstance4 = composerImpl3.changedInstance(arrayList4) | composerImpl3.changed(i3);
                    Object objRememberedValue8 = composerImpl3.rememberedValue();
                    if (zChangedInstance4 || objRememberedValue8 == Composer.Companion.Empty) {
                        final int i6 = 1;
                        objRememberedValue8 = new Function0() { // from class: com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonsKt$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                switch (i6) {
                                    case 0:
                                        ((Item) arrayList4.get(i3)).onItemSelected.invoke();
                                        break;
                                    default:
                                        ((Item) arrayList4.get(i3)).onItemSelected.invoke();
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl3.updateRememberedValue(objRememberedValue8);
                    }
                    composerImpl3.end(false);
                    ComposerImpl composerImpl4 = composerImpl3;
                    ButtonKt.TextButton((Function0) objRememberedValue8, modifierWeight2, false, roundedCornerShapeM187RoundedCornerShape0680j_4, null, null, null, paddingValuesImplM120PaddingValues0680j_4, null, ComposableLambdaKt.rememberComposableLambda(246403073, new Function3() { // from class: com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonsKt$VolumePanelRadioButtonBar$2$4$2
                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        @Override // kotlin.jvm.functions.Function3
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj8, Object obj9, Object obj10) {
                            final RowScope rowScope = (RowScope) obj8;
                            Composer composer2 = (Composer) obj9;
                            int iIntValue = ((Number) obj10).intValue();
                            if ((iIntValue & 6) == 0) {
                                iIntValue |= ((ComposerImpl) composer2).changed(rowScope) ? 4 : 2;
                            }
                            if ((iIntValue & 19) == 18) {
                                ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                if (composerImpl5.getSkipping()) {
                                    composerImpl5.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonBar.<anonymous>.<anonymous>.<anonymous> (VolumePanelRadioButtons.kt:170)");
                                    }
                                    List list2 = arrayList4;
                                    final int i7 = i3;
                                    if (((Item) list2.get(i7)).icon != VolumePanelRadioButtonsKt.Empty) {
                                        int i8 = volumePanelRadioButtonBarScopeImpl4.selectedIndex;
                                        VolumePanelRadioButtonBarColors volumePanelRadioButtonBarColors5 = volumePanelRadioButtonBarColors2;
                                        ProvidedValue providedValueDefaultProvidedValue$runtime_release = ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(i7 == i8 ? volumePanelRadioButtonBarColors5.selectedLabelColor : volumePanelRadioButtonBarColors5.labelColor));
                                        final List list3 = arrayList4;
                                        CompositionLocalKt.CompositionLocalProvider(providedValueDefaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(-322520890, new Function2() { // from class: com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonsKt$VolumePanelRadioButtonBar$2$4$2.1
                                            /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                            @Override // kotlin.jvm.functions.Function2
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                            */
                                            public final Object invoke(Object obj11, Object obj12) {
                                                Composer composer3 = (Composer) obj11;
                                                if ((((Number) obj12).intValue() & 3) == 2) {
                                                    ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                                    if (composerImpl6.getSkipping()) {
                                                        composerImpl6.skipToGroupEnd();
                                                    } else {
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonBar.<anonymous>.<anonymous>.<anonymous>.<anonymous> (VolumePanelRadioButtons.kt:174)");
                                                        }
                                                        ((Item) list3.get(i7)).label.invoke(rowScope, composer3, 0);
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                    }
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composer2), composer2, 56);
                                    }
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl3), composerImpl4, 817889280, 372);
                    i3++;
                    arrayList4 = arrayList4;
                    volumePanelRadioButtonBarScopeImpl4 = volumePanelRadioButtonBarScopeImpl4;
                    composerImpl3 = composerImpl4;
                }
                composerImpl = composerImpl3;
                composerImpl.end(false);
                composerImpl.end(true);
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                volumePanelRadioButtonBarColors3 = volumePanelRadioButtonBarColors2;
                f7 = f11;
                f8 = f10;
                f9 = f4;
                modifier3 = modifier4;
                cornerSize5 = cornerSize9;
                cornerSize6 = cornerSize10;
            }
            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl2, currentCompositeKeyHash3, function24);
            Function2 function252 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, function252);
            Modifier.Companion companion22 = Modifier.Companion;
            Modifier modifierLayoutId3 = LayoutIdKt.layoutId(companion22, RadioButtonBarComponent.ButtonsBackground);
            ArrayList arrayList22 = arrayList;
            long j72 = volumePanelRadioButtonBarColors2.indicatorBackgroundColor;
            RoundedCornerShape roundedCornerShape2 = RoundedCornerShapeKt.CircleShape;
            SpacerKt.Spacer(composerImpl2, BackgroundKt.m26backgroundbw27NRU(modifierLayoutId3, j72, new RoundedCornerShape(cornerSize4, cornerSize4, cornerSize4, cornerSize4)));
            Modifier modifierLayoutId22 = LayoutIdKt.layoutId(companion22, RadioButtonBarComponent.Indicator);
            composerImpl2.startReplaceGroup(-1371135599);
            zChangedInstance = composerImpl2.changedInstance(animatable2);
            Object objRememberedValue52 = composerImpl2.rememberedValue();
            obj = objRememberedValue52;
            if (!zChangedInstance) {
                Function1 function132 = new Function1() { // from class: com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonsKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj72) {
                        return IntOffset.m849boximpl((((Number) animatable2.internalState.getValue()).intValue() << 32) | (0 & 4294967295L));
                    }
                };
                composerImpl2.updateRememberedValue(function132);
                obj = function132;
                composerImpl2.end(false);
                SpacerKt.Spacer(composerImpl2, BackgroundKt.m26backgroundbw27NRU(PaddingKt.m125padding3ABfNKs(OffsetKt.offset(modifierLayoutId22, (Function1) obj), f4), volumePanelRadioButtonBarColors2.indicatorColor, new RoundedCornerShape(cornerSize3, cornerSize3, cornerSize3, cornerSize3)));
                Modifier modifierM125padding3ABfNKs2 = PaddingKt.m125padding3ABfNKs(LayoutIdKt.layoutId(companion22, RadioButtonBarComponent.Buttons), f4);
                Arrangement.INSTANCE.getClass();
                Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_43 = Arrangement.m92spacedBy0680j_4(f10);
                Alignment.Companion.getClass();
                RowMeasurePolicy rowMeasurePolicy4 = RowKt.rowMeasurePolicy(spacedAlignedM92spacedBy0680j_43, Alignment.Companion.Top, composerImpl2, 0);
                currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope22 = composerImpl2.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier22 = ComposedModifierKt.materializeModifier(composerImpl2, modifierM125padding3ABfNKs2);
                composerImpl2.startReusableNode();
                if (composerImpl2.inserting) {
                }
                Updater.m337setimpl(composerImpl2, rowMeasurePolicy4, function22);
                Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope22, function23);
                if (!composerImpl2.inserting) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function24);
                    Updater.m337setimpl(composerImpl2, modifierMaterializeModifier22, function252);
                    RowScopeInstance rowScopeInstance4 = RowScopeInstance.INSTANCE;
                    composerImpl2.startReplaceGroup(377449402);
                    size = arrayList22.size();
                    i2 = 0;
                    while (i2 < size) {
                    }
                    final VolumePanelRadioButtonBarScopeImpl volumePanelRadioButtonBarScopeImpl42 = volumePanelRadioButtonBarScopeImpl;
                    CornerSize cornerSize92 = cornerSize3;
                    CornerSize cornerSize102 = cornerSize4;
                    final List<Item> arrayList42 = arrayList22;
                    composerImpl2.end(false);
                    composerImpl2.end(true);
                    Modifier modifierM129paddingqDBjuR0$default2 = PaddingKt.m129paddingqDBjuR0$default(LayoutIdKt.layoutId(Modifier.Companion, RadioButtonBarComponent.Labels), f4, f11, f4, 0.0f, 8);
                    composerImpl2.startReplaceGroup(-1371057845);
                    objRememberedValue = composerImpl2.rememberedValue();
                    Object obj72 = objRememberedValue;
                    if (objRememberedValue == Composer.Companion.Empty) {
                    }
                    composerImpl2.end(false);
                    Modifier modifierClearAndSetSemantics2 = SemanticsModifierKt.clearAndSetSemantics(modifierM129paddingqDBjuR0$default2, (Function1) obj72);
                    Arrangement.INSTANCE.getClass();
                    Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_422 = Arrangement.m92spacedBy0680j_4(f10);
                    Alignment.Companion.getClass();
                    RowMeasurePolicy rowMeasurePolicy32 = RowKt.rowMeasurePolicy(spacedAlignedM92spacedBy0680j_422, Alignment.Companion.Top, composerImpl2, 0);
                    currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope42 = composerImpl2.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier42 = ComposedModifierKt.materializeModifier(composerImpl2, modifierClearAndSetSemantics2);
                    ComposeUiNode.Companion.getClass();
                    Function0 function042 = ComposeUiNode.Companion.Constructor;
                    composerImpl2.startReusableNode();
                    if (composerImpl2.inserting) {
                    }
                    Updater.m337setimpl(composerImpl2, rowMeasurePolicy32, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope42, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (!composerImpl2.inserting) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl2, currentCompositeKeyHash2, function2);
                        Updater.m337setimpl(composerImpl2, modifierMaterializeModifier42, ComposeUiNode.Companion.SetModifier);
                        RowScopeInstance rowScopeInstance32 = RowScopeInstance.INSTANCE;
                        composerImpl2.startReplaceGroup(377511555);
                        size2 = arrayList42.size();
                        i3 = 0;
                        ComposerImpl composerImpl32 = composerImpl2;
                        while (i3 < size2) {
                        }
                        composerImpl = composerImpl32;
                        composerImpl.end(false);
                        composerImpl.end(true);
                        composerImpl.end(true);
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        volumePanelRadioButtonBarColors3 = volumePanelRadioButtonBarColors2;
                        f7 = f11;
                        f8 = f10;
                        f9 = f4;
                        modifier3 = modifier4;
                        cornerSize5 = cornerSize92;
                        cornerSize6 = cornerSize102;
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(f9, f8, f7, cornerSize5, cornerSize6, volumePanelRadioButtonBarColors3, function1, i) { // from class: com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonsKt$$ExternalSyntheticLambda6
                public final /* synthetic */ float f$1;
                public final /* synthetic */ float f$2;
                public final /* synthetic */ float f$3;
                public final /* synthetic */ CornerSize f$4;
                public final /* synthetic */ CornerSize f$5;
                public final /* synthetic */ VolumePanelRadioButtonBarColors f$6;
                public final /* synthetic */ Function1 f$7;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj8, Object obj9) {
                    ((Integer) obj9).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    CornerSize cornerSize11 = this.f$4;
                    CornerSize cornerSize12 = this.f$5;
                    VolumePanelRadioButtonBarColors volumePanelRadioButtonBarColors5 = this.f$6;
                    Function1 function15 = this.f$7;
                    VolumePanelRadioButtonsKt.m3220VolumePanelRadioButtonBarcjTkxnM(this.f$0, this.f$1, this.f$2, this.f$3, cornerSize11, cornerSize12, volumePanelRadioButtonBarColors5, function15, (Composer) obj8, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
