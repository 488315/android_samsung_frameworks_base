package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.animation.graphics.res.AnimatedVectorPainterResources_androidKt;
import androidx.compose.animation.graphics.res.AnimatedVectorResources_androidKt;
import androidx.compose.animation.graphics.vector.AnimatedImageVector;
import androidx.compose.foundation.BasicMarqueeKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.BasicTextKt;
import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.TextAutoSize;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.CompositingStrategy;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.LinearGradient;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.TileMode;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.VectorPainter;
import androidx.compose.ui.layout.LookaheadScopeKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.state.ToggleableState;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import com.android.compose.modifiers.SizeModifier;
import com.android.compose.ui.graphics.DrawInContainerNode$$ExternalSyntheticLambda1;
import com.android.compose.ui.graphics.painter.DrawablePainterKt;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.compose.ContentDescriptionKt;
import com.android.systemui.common.ui.compose.IconKt;
import com.android.systemui.compose.modifiers.SysuiTestTagKt;
import com.android.systemui.qs.panels.ui.viewmodel.AccessibilityUiState;
import com.android.systemui.qs.ui.compose.BorderOnFocusKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.Arrays;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public abstract class CommonTileKt {
    /* JADX WARN: Removed duplicated region for block: B:100:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x026f  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x03b1  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x03cc  */
    /* JADX WARN: Removed duplicated region for block: B:169:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0150  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void LargeTileContent(final String str, final String str2, final Function1 function1, Drawable drawable, final TileColors tileColors, final Function0 function0, Function0 function02, AccessibilityUiState accessibilityUiState, RoundedCornerShape roundedCornerShape, Function0 function03, Function0 function04, Composer composer, final int i, final int i2) {
        Function0 function05;
        int i3;
        final AccessibilityUiState accessibilityUiState2;
        int i4;
        RoundedCornerShape roundedCornerShapeM187RoundedCornerShape0680j_4;
        int i5;
        int i6;
        int i7;
        int i8;
        char c;
        int i9;
        Function0 function06;
        Function0 function07;
        int i10;
        Function0 function08;
        Applier applier;
        RoundedCornerShape roundedCornerShape2;
        boolean zChanged;
        Function0 function09;
        Function0 function010;
        Drawable drawable2;
        final AccessibilityUiState accessibilityUiState3;
        final RoundedCornerShape roundedCornerShape3;
        final Function0 function011;
        final Function0 function012;
        final Function0 function013;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1626412212);
        int i11 = (composerImpl.changed(str) ? 4 : 2) | i;
        if ((i & 48) == 0) {
            i11 |= composerImpl.changed(str2) ? 32 : 16;
        }
        int i12 = i11 | (composerImpl.changedInstance(function1) ? 256 : 128);
        if ((i & 3072) == 0) {
            i12 |= composerImpl.changedInstance(drawable) ? 2048 : 1024;
        }
        int i13 = i12 | (composerImpl.changed(tileColors) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192);
        if ((196608 & i) == 0) {
            i13 |= composerImpl.changedInstance(function0) ? 131072 : 65536;
        }
        int i14 = i2 & 64;
        if (i14 != 0) {
            i3 = i13 | 1572864;
            function05 = function02;
        } else {
            function05 = function02;
            i3 = i13 | (composerImpl.changedInstance(function05) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        }
        int i15 = i2 & 128;
        if (i15 != 0) {
            i4 = i3 | 12582912;
            accessibilityUiState2 = accessibilityUiState;
        } else {
            accessibilityUiState2 = accessibilityUiState;
            i4 = i3 | (composerImpl.changed(accessibilityUiState2) ? 8388608 : 4194304);
        }
        if ((i2 & 256) == 0) {
            roundedCornerShapeM187RoundedCornerShape0680j_4 = roundedCornerShape;
            int i16 = composerImpl.changed(roundedCornerShapeM187RoundedCornerShape0680j_4) ? 67108864 : 33554432;
            int i17 = i4 | i16;
            i5 = i2 & 512;
            if (i5 == 0) {
                i6 = i17 | 805306368;
            } else {
                i6 = i17 | (composerImpl.changedInstance(function03) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456);
            }
            i7 = i2 & 1024;
            if (i7 == 0) {
                c = 6;
                i8 = i7;
            } else {
                i8 = i7;
                c = composerImpl.changedInstance(function04) ? (char) 4 : (char) 2;
            }
            if ((i6 & 306783379) != 306783378 && (c & 3) == 2 && composerImpl.getSkipping()) {
                composerImpl.skipToGroupEnd();
                function011 = function04;
                drawable2 = drawable;
                roundedCornerShape3 = roundedCornerShapeM187RoundedCornerShape0680j_4;
                function013 = function05;
                accessibilityUiState3 = accessibilityUiState2;
                function012 = function03;
            } else {
                composerImpl.startDefaults();
                i9 = i & 1;
                Composer.Companion companion = Composer.Companion;
                if (i9 != 0 || composerImpl.getDefaultsInvalid()) {
                    if (i14 != 0) {
                        Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, -135458072, companion);
                        if (objM == Composer.Companion.Empty) {
                            objM = new DrawInContainerNode$$ExternalSyntheticLambda1();
                            composerImpl.updateRememberedValue(objM);
                        }
                        composerImpl.end(false);
                        function05 = (Function0) objM;
                    }
                    if (i15 != 0) {
                        accessibilityUiState2 = null;
                    }
                    if ((i2 & 256) != 0) {
                        CommonTileDefaults.INSTANCE.getClass();
                        i6 &= -234881025;
                        roundedCornerShapeM187RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(CommonTileDefaults.InactiveCornerRadius);
                    }
                    Function0 function014 = i5 == 0 ? null : function03;
                    if (i8 == 0) {
                        function07 = function014;
                        i10 = i6;
                        function08 = function05;
                        function06 = null;
                        composerImpl.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.LargeTileContent (CommonTile.kt:112)");
                        }
                        Alignment.Companion companion2 = Alignment.Companion;
                        companion2.getClass();
                        BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                        Arrangement arrangement = Arrangement.INSTANCE;
                        CommonTileDefaults commonTileDefaults = CommonTileDefaults.INSTANCE;
                        commonTileDefaults.getClass();
                        float f = CommonTileDefaults.TileArrangementPadding;
                        companion2.getClass();
                        BiasAlignment.Horizontal horizontal = Alignment.Companion.Start;
                        arrangement.getClass();
                        Arrangement.SpacedAligned spacedAlignedM93spacedByD5KLDUw = Arrangement.m93spacedByD5KLDUw(f, horizontal);
                        Modifier.Companion companion3 = Modifier.Companion;
                        RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM93spacedByD5KLDUw, vertical, composerImpl, 48);
                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                        Function0 function015 = function08;
                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion3);
                        ComposeUiNode.Companion.getClass();
                        Function0 function016 = ComposeUiNode.Companion.Constructor;
                        applier = composerImpl.applier;
                        if (applier != null) {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                        composerImpl.startReusableNode();
                        if (composerImpl.inserting) {
                            composerImpl.createNode(function016);
                        } else {
                            composerImpl.useNode();
                        }
                        Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                        Updater.m337setimpl(composerImpl, rowMeasurePolicy, function2);
                        Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                        Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl.inserting) {
                            roundedCornerShape2 = roundedCornerShapeM187RoundedCornerShape0680j_4;
                        } else {
                            roundedCornerShape2 = roundedCornerShapeM187RoundedCornerShape0680j_4;
                            if (!Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            }
                            Function2 function24 = ComposeUiNode.Companion.SetModifier;
                            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
                            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                            commonTileDefaults.getClass();
                            String strLongPressLabel = function06 == null ? CommonTileDefaults.longPressLabel(composerImpl) : null;
                            RoundedCornerShape roundedCornerShape4 = roundedCornerShape2;
                            State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(tileColors.iconBackground, null, "QSTileDualTargetBackgroundColor", composerImpl, 384, 10);
                            MaterialTheme.INSTANCE.getClass();
                            long j = MaterialTheme.getColorScheme(composerImpl).secondary;
                            Modifier modifierApproachLayout = LookaheadScopeKt.approachLayout(ClipKt.clip(SizeKt.m140size3ABfNKs(companion3, CommonTileDefaults.ToggleTargetSize), roundedCornerShape4), new SquishTileKt$$ExternalSyntheticLambda0(function0), LookaheadScopeKt.defaultPlacementApproachInProgress, new SquishTileKt$$ExternalSyntheticLambda1(function0));
                            composerImpl.startReplaceGroup(1196127524);
                            zChanged = composerImpl.changed(stateM7animateColorAsStateeuL9pac);
                            Object objRememberedValue = composerImpl.rememberedValue();
                            if (zChanged) {
                                companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new CommonTileKt$$ExternalSyntheticLambda4(stateM7animateColorAsStateeuL9pac, 2);
                                    composerImpl.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl.end(false);
                                Modifier modifierDrawBehind = DrawModifierKt.drawBehind(modifierApproachLayout, (Function1) objRememberedValue);
                                if (function07 != null) {
                                    Modifier modifierM2937borderOnFocusPOIbLQ4$default = BorderOnFocusKt.m2937borderOnFocusPOIbLQ4$default(companion3, j, roundedCornerShape4.topEnd);
                                    function07.getClass();
                                    Function0 function017 = function07;
                                    Function0 function018 = function06;
                                    Modifier modifierM39combinedClickablef5TDLPQ$default = ClickableKt.m39combinedClickablef5TDLPQ$default(modifierM2937borderOnFocusPOIbLQ4$default, null, strLongPressLabel, function018, function017, 39);
                                    function010 = function018;
                                    function09 = function017;
                                    if (accessibilityUiState2 != null) {
                                        modifierM39combinedClickablef5TDLPQ$default = modifierM39combinedClickablef5TDLPQ$default.then(SysuiTestTagKt.sysuiResTag(SemanticsModifierKt.semantics(companion3, false, new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.CommonTileKt$LargeTileContent$2$2$1$1
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj) {
                                                SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                                                AccessibilityUiState accessibilityUiState4 = accessibilityUiState2;
                                                SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, accessibilityUiState4.contentDescription);
                                                SemanticsPropertiesKt.setStateDescription(semanticsPropertyReceiver, accessibilityUiState4.stateDescription);
                                                ToggleableState toggleableState = accessibilityUiState4.toggleableState;
                                                if (toggleableState != null) {
                                                    SemanticsPropertiesKt.setToggleableState(semanticsPropertyReceiver, toggleableState);
                                                }
                                                Role.Companion.getClass();
                                                SemanticsPropertiesKt.m719setRolekuIjeqM(semanticsPropertyReceiver, Role.Switch);
                                                return Unit.INSTANCE;
                                            }
                                        }), "qs_tile_toggle_target"));
                                    }
                                    modifierDrawBehind = modifierDrawBehind.then(modifierM39combinedClickablef5TDLPQ$default);
                                } else {
                                    Function0 function019 = function06;
                                    function09 = function07;
                                    function010 = function019;
                                }
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierDrawBehind);
                                if (applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl.startReusableNode();
                                if (composerImpl.inserting) {
                                    composerImpl.createNode(function016);
                                } else {
                                    composerImpl.useNode();
                                }
                                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
                                }
                                Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
                                Modifier modifierAlign = BoxScopeInstance.INSTANCE.align(companion3, Alignment.Companion.Center);
                                Object objM2 = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, 1623988550, companion);
                                if (objM2 == Composer.Companion.Empty) {
                                    objM2 = new CommonTileKt$$ExternalSyntheticLambda1(0);
                                    composerImpl.updateRememberedValue(objM2);
                                }
                                composerImpl.end(false);
                                int i18 = i10 >> 6;
                                AccessibilityUiState accessibilityUiState4 = accessibilityUiState2;
                                m2906SmallTileContent8V94_ZQ(function1, tileColors.icon, modifierAlign, (Function0) objM2, false, composerImpl, (i18 & 14) | 3072, 16);
                                composerImpl.end(true);
                                LargeTileLabels(str, str2, tileColors, rowScopeInstance.weight(companion3, 1.0f, true), function015, accessibilityUiState4, composerImpl, (i10 & 126) | (i18 & 896) | (57344 & i18) | (i18 & 458752), 0);
                                ComposerImpl composerImpl2 = composerImpl;
                                composerImpl2.startReplaceGroup(1196188674);
                                drawable2 = drawable;
                                if (drawable != null) {
                                    ImageKt.Image(DrawablePainterKt.rememberDrawablePainter(drawable2, composerImpl2), null, SizeKt.m131height3ABfNKs(SizeKt.m144width3ABfNKs(companion3, CommonTileDefaults.SideIconWidth), CommonTileDefaults.SideIconHeight), null, null, 0.0f, null, composerImpl2, 432, 120);
                                    composerImpl2 = composerImpl2;
                                }
                                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl2, false, true)) {
                                    ComposerKt.traceEventEnd();
                                }
                                accessibilityUiState3 = accessibilityUiState4;
                                composerImpl = composerImpl2;
                                roundedCornerShape3 = roundedCornerShape4;
                                function011 = function010;
                                function012 = function09;
                                function013 = function015;
                            }
                        }
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
                        Function2 function242 = ComposeUiNode.Companion.SetModifier;
                        Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function242);
                        RowScopeInstance rowScopeInstance2 = RowScopeInstance.INSTANCE;
                        commonTileDefaults.getClass();
                        if (function06 == null) {
                        }
                        RoundedCornerShape roundedCornerShape42 = roundedCornerShape2;
                        State stateM7animateColorAsStateeuL9pac2 = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(tileColors.iconBackground, null, "QSTileDualTargetBackgroundColor", composerImpl, 384, 10);
                        MaterialTheme.INSTANCE.getClass();
                        long j2 = MaterialTheme.getColorScheme(composerImpl).secondary;
                        Modifier modifierApproachLayout2 = LookaheadScopeKt.approachLayout(ClipKt.clip(SizeKt.m140size3ABfNKs(companion3, CommonTileDefaults.ToggleTargetSize), roundedCornerShape42), new SquishTileKt$$ExternalSyntheticLambda0(function0), LookaheadScopeKt.defaultPlacementApproachInProgress, new SquishTileKt$$ExternalSyntheticLambda1(function0));
                        composerImpl.startReplaceGroup(1196127524);
                        zChanged = composerImpl.changed(stateM7animateColorAsStateeuL9pac2);
                        Object objRememberedValue2 = composerImpl.rememberedValue();
                        if (zChanged) {
                        }
                    } else {
                        function06 = function04;
                        function07 = function014;
                    }
                } else {
                    composerImpl.skipToGroupEnd();
                    if ((i2 & 256) != 0) {
                        i6 &= -234881025;
                    }
                    function07 = function03;
                    function06 = function04;
                }
                i10 = i6;
                function08 = function05;
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                Alignment.Companion companion22 = Alignment.Companion;
                companion22.getClass();
                BiasAlignment.Vertical vertical2 = Alignment.Companion.CenterVertically;
                Arrangement arrangement2 = Arrangement.INSTANCE;
                CommonTileDefaults commonTileDefaults2 = CommonTileDefaults.INSTANCE;
                commonTileDefaults2.getClass();
                float f2 = CommonTileDefaults.TileArrangementPadding;
                companion22.getClass();
                BiasAlignment.Horizontal horizontal2 = Alignment.Companion.Start;
                arrangement2.getClass();
                Arrangement.SpacedAligned spacedAlignedM93spacedByD5KLDUw2 = Arrangement.m93spacedByD5KLDUw(f2, horizontal2);
                Modifier.Companion companion32 = Modifier.Companion;
                RowMeasurePolicy rowMeasurePolicy2 = RowKt.rowMeasurePolicy(spacedAlignedM93spacedByD5KLDUw2, vertical2, composerImpl, 48);
                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                Function0 function0152 = function08;
                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, companion32);
                ComposeUiNode.Companion.getClass();
                Function0 function0162 = ComposeUiNode.Companion.Constructor;
                applier = composerImpl.applier;
                if (applier != null) {
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                final Drawable drawable3 = drawable2;
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.CommonTileKt$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        Function0 function020 = function011;
                        int i19 = i2;
                        CommonTileKt.LargeTileContent(str, str2, function1, drawable3, tileColors, function0, function013, accessibilityUiState3, roundedCornerShape3, function012, function020, (Composer) obj, iUpdateChangedFlags, i19);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        roundedCornerShapeM187RoundedCornerShape0680j_4 = roundedCornerShape;
        int i172 = i4 | i16;
        i5 = i2 & 512;
        if (i5 == 0) {
        }
        i7 = i2 & 1024;
        if (i7 == 0) {
        }
        if ((i6 & 306783379) != 306783378) {
            composerImpl.startDefaults();
            i9 = i & 1;
            Composer.Companion companion4 = Composer.Companion;
            if (i9 != 0) {
                if (i14 != 0) {
                }
                if (i15 != 0) {
                }
                if ((i2 & 256) != 0) {
                }
                if (i5 == 0) {
                }
                if (i8 == 0) {
                }
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x022e  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x023a  */
    /* JADX WARN: Removed duplicated region for block: B:124:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x018c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void LargeTileLabels(final String str, final String str2, final TileColors tileColors, final Modifier modifier, Function0 function0, AccessibilityUiState accessibilityUiState, Composer composer, final int i, final int i2) {
        String str3;
        int i3;
        Function0 function02;
        int i4;
        AccessibilityUiState accessibilityUiState2;
        int i5;
        Function0 function03;
        boolean z;
        final AccessibilityUiState accessibilityUiState3;
        String str4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1265509123);
        if ((i & 6) == 0) {
            str3 = str;
            i3 = (composerImpl.changed(str3) ? 4 : 2) | i;
        } else {
            str3 = str;
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl.changed(str2) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl.changed(tileColors) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i3 |= composerImpl.changed(modifier) ? 2048 : 1024;
        }
        int i6 = i2 & 16;
        if (i6 != 0) {
            i3 |= 24576;
        } else {
            if ((i & 24576) == 0) {
                function02 = function0;
                i3 |= composerImpl.changedInstance(function02) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
            }
            i4 = i2 & 32;
            if (i4 != 0) {
                if ((196608 & i) == 0) {
                    accessibilityUiState2 = accessibilityUiState;
                    i3 |= composerImpl.changed(accessibilityUiState2) ? 131072 : 65536;
                }
                i5 = i3;
                if ((74899 & i5) == 74898 && composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    accessibilityUiState3 = accessibilityUiState2;
                } else {
                    Composer.Companion companion = Composer.Companion;
                    if (i6 == 0) {
                        Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, -1811667424, companion);
                        if (objM == Composer.Companion.Empty) {
                            objM = new DrawInContainerNode$$ExternalSyntheticLambda1();
                            composerImpl.updateRememberedValue(objM);
                        }
                        function03 = (Function0) objM;
                        composerImpl.end(false);
                    } else {
                        function03 = function02;
                    }
                    AccessibilityUiState accessibilityUiState4 = i4 == 0 ? null : accessibilityUiState2;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.LargeTileLabels (CommonTile.kt:187)");
                    }
                    final State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(tileColors.label, null, "QSTileLabelColor", composerImpl, 384, 10);
                    final State stateM7animateColorAsStateeuL9pac2 = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(tileColors.secondaryLabel, null, "QSTileSecondaryLabelColor", composerImpl, 384, 10);
                    Arrangement.INSTANCE.getClass();
                    Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
                    Modifier modifierThen = modifier.then(SizeKt.FillWholeMaxHeight);
                    Alignment.Companion.getClass();
                    ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Center$1, Alignment.Companion.Start, composerImpl, 6);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierThen);
                    ComposeUiNode.Companion.getClass();
                    Function0 function04 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl.applier != null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function04);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                    ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                    MaterialTheme.INSTANCE.getClass();
                    TextStyle textStyle = MaterialTheme.getTypography(composerImpl).titleSmallEmphasized;
                    composerImpl.startReplaceGroup(-1837804467);
                    boolean zChanged = composerImpl.changed(stateM7animateColorAsStateeuL9pac);
                    Object objRememberedValue = composerImpl.rememberedValue();
                    if (!zChanged) {
                        companion.getClass();
                        if (objRememberedValue == Composer.Companion.Empty) {
                            objRememberedValue = new ColorProducer() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.CommonTileKt$LargeTileLabels$2$1$1
                                @Override // androidx.compose.ui.graphics.ColorProducer
                                /* renamed from: invoke-0d7_KjU */
                                public final long mo262invoke0d7_KjU() {
                                    return ((Color) stateM7animateColorAsStateeuL9pac.getValue()).value;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue);
                        }
                        composerImpl.end(false);
                        int i7 = i5 & 57344;
                        Function0 function05 = function03;
                        TileLabel(str3, (ColorProducer) objRememberedValue, textStyle, null, function05, composerImpl, i5 & 57358, 8);
                        function02 = function05;
                        composerImpl = composerImpl;
                        composerImpl.startReplaceGroup(-1837801442);
                        if (TextUtils.isEmpty(str2)) {
                            z = true;
                        } else {
                            String str5 = str2 != null ? str2 : "";
                            composerImpl.startReplaceGroup(-1837797930);
                            boolean zChanged2 = composerImpl.changed(stateM7animateColorAsStateeuL9pac2);
                            Object objRememberedValue2 = composerImpl.rememberedValue();
                            if (!zChanged2) {
                                companion.getClass();
                                if (objRememberedValue2 == Composer.Companion.Empty) {
                                    objRememberedValue2 = new ColorProducer() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.CommonTileKt$LargeTileLabels$2$2$1
                                        @Override // androidx.compose.ui.graphics.ColorProducer
                                        /* renamed from: invoke-0d7_KjU */
                                        public final long mo262invoke0d7_KjU() {
                                            return ((Color) stateM7animateColorAsStateeuL9pac2.getValue()).value;
                                        }
                                    };
                                    composerImpl.updateRememberedValue(objRememberedValue2);
                                }
                                ColorProducer colorProducer = (ColorProducer) objRememberedValue2;
                                composerImpl.end(false);
                                TextStyle textStyle2 = MaterialTheme.getTypography(composerImpl).labelMedium;
                                Modifier modifierClearAndSetSemantics = Modifier.Companion;
                                if (accessibilityUiState4 != null && (str4 = accessibilityUiState4.stateDescription) != null) {
                                    if (StringsKt__StringsKt.contains(str4, str2 != null ? str2 : "", false)) {
                                        modifierClearAndSetSemantics = SemanticsModifierKt.clearAndSetSemantics(modifierClearAndSetSemantics, new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.CommonTileKt$LargeTileLabels$2$3$1
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                                                return Unit.INSTANCE;
                                            }
                                        });
                                    }
                                }
                                z = true;
                                TileLabel(str5, colorProducer, textStyle2, modifierClearAndSetSemantics, function02, composerImpl, i7, 0);
                                function02 = function02;
                                composerImpl = composerImpl;
                            }
                        }
                        if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, z)) {
                            ComposerKt.traceEventEnd();
                        }
                        accessibilityUiState3 = accessibilityUiState4;
                    }
                }
                final Function0 function06 = function02;
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.CommonTileKt$$ExternalSyntheticLambda8
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            AccessibilityUiState accessibilityUiState5 = accessibilityUiState3;
                            CommonTileKt.LargeTileLabels(str, str2, tileColors, modifier, function06, accessibilityUiState5, (Composer) obj, iUpdateChangedFlags, i2);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i3 |= 196608;
            accessibilityUiState2 = accessibilityUiState;
            i5 = i3;
            if ((74899 & i5) == 74898) {
                Composer.Companion companion2 = Composer.Companion;
                if (i6 == 0) {
                }
                if (i4 == 0) {
                }
                if (ComposerKt.isTraceInProgress()) {
                }
                final State<Color> stateM7animateColorAsStateeuL9pac3 = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(tileColors.label, null, "QSTileLabelColor", composerImpl, 384, 10);
                final State<Color> stateM7animateColorAsStateeuL9pac22 = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(tileColors.secondaryLabel, null, "QSTileSecondaryLabelColor", composerImpl, 384, 10);
                Arrangement.INSTANCE.getClass();
                Arrangement$Center$1 arrangement$Center$12 = Arrangement.Center;
                Modifier modifierThen2 = modifier.then(SizeKt.FillWholeMaxHeight);
                Alignment.Companion.getClass();
                ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement$Center$12, Alignment.Companion.Start, composerImpl, 6);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierThen2);
                ComposeUiNode.Companion.getClass();
                Function0 function042 = ComposeUiNode.Companion.Constructor;
                if (composerImpl.applier != null) {
                }
            }
            final Function0 function062 = function02;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        function02 = function0;
        i4 = i2 & 32;
        if (i4 != 0) {
        }
        accessibilityUiState2 = accessibilityUiState;
        i5 = i3;
        if ((74899 & i5) == 74898) {
        }
        final Function0 function0622 = function02;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:145:0x0310  */
    /* JADX WARN: Removed duplicated region for block: B:149:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0174  */
    /* renamed from: SmallTileContent-8V94_ZQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m2906SmallTileContent8V94_ZQ(final Function1 function1, final long j, final Modifier modifier, Function0 function0, boolean z, Composer composer, final int i, final int i2) {
        int i3;
        long j2;
        Function0 function02;
        int i4;
        boolean z2;
        Function0 function03;
        final Function0 function04;
        boolean z3;
        Drawable drawable;
        final boolean z4;
        Painter painterRememberDrawablePainter;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1953366698);
        if ((i & 6) == 0) {
            i3 = (composerImpl.changedInstance(function1) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            j2 = j;
            i3 |= composerImpl.changed(j2) ? 32 : 16;
        } else {
            j2 = j;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        int i5 = i2 & 8;
        if (i5 != 0) {
            i3 |= 3072;
        } else {
            if ((i & 3072) == 0) {
                function02 = function0;
                i3 |= composerImpl.changedInstance(function02) ? 2048 : 1024;
            }
            i4 = i2 & 16;
            if (i4 != 0) {
                if ((i & 24576) == 0) {
                    z2 = z;
                    i3 |= composerImpl.changed(z2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                if ((i3 & 9363) == 9362 && composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    function04 = function02;
                    z4 = z2;
                } else {
                    Composer.Companion companion = Composer.Companion;
                    if (i5 == 0) {
                        Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, 327995027, companion);
                        if (objM == Composer.Companion.Empty) {
                            objM = new CommonTileKt$$ExternalSyntheticLambda1(1);
                            composerImpl.updateRememberedValue(objM);
                        }
                        function03 = (Function0) objM;
                        composerImpl.end(false);
                    } else {
                        function03 = function02;
                    }
                    boolean z5 = i4 == 0 ? false : z2;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.SmallTileContent (CommonTile.kt:224)");
                    }
                    Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
                    Icon icon = (Icon) function1.mo781invoke(context);
                    function04 = function03;
                    State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(j2, null, "QSTileIconColor", composerImpl, ((i3 >> 3) & 14) | 384, 10);
                    composerImpl.startReplaceGroup(328003466);
                    int i6 = i3 & 7168;
                    z3 = i6 != 2048;
                    Object objRememberedValue = composerImpl.rememberedValue();
                    if (z3) {
                        companion.getClass();
                        if (objRememberedValue == Composer.Companion.Empty) {
                            objRememberedValue = new CommonTileKt$$ExternalSyntheticLambda4(function04, 0);
                            composerImpl.updateRememberedValue(objRememberedValue);
                        }
                        Function1 function12 = (Function1) objRememberedValue;
                        composerImpl.end(false);
                        composerImpl.startReplaceGroup(328004234);
                        boolean z6 = i6 == 2048;
                        Object objRememberedValue2 = composerImpl.rememberedValue();
                        if (!z6) {
                            companion.getClass();
                            if (objRememberedValue2 == Composer.Companion.Empty) {
                                objRememberedValue2 = new CommonTileKt$$ExternalSyntheticLambda4(function04, 1);
                                composerImpl.updateRememberedValue(objRememberedValue2);
                            }
                            Function1 function13 = (Function1) objRememberedValue2;
                            composerImpl.end(false);
                            Modifier modifierThen = modifier.then(new SizeModifier(function12, function13, function12, function13, true, InspectableValueKt.NoInspectorInfo));
                            composerImpl.startReplaceGroup(328006225);
                            boolean zChanged = composerImpl.changed(icon) | composerImpl.changed(context);
                            Object objRememberedValue3 = composerImpl.rememberedValue();
                            if (!zChanged) {
                                companion.getClass();
                                if (objRememberedValue3 == Composer.Companion.Empty) {
                                    if (icon instanceof Icon.Loaded) {
                                        drawable = ((Icon.Loaded) icon).drawable;
                                    } else {
                                        if (!(icon instanceof Icon.Resource)) {
                                            throw new NoWhenBranchMatchedException();
                                        }
                                        drawable = context.getDrawable(((Icon.Resource) icon).res);
                                    }
                                    objRememberedValue3 = drawable;
                                    composerImpl.updateRememberedValue(objRememberedValue3);
                                }
                                final Drawable drawable2 = (Drawable) objRememberedValue3;
                                composerImpl.end(false);
                                if (drawable2 instanceof Animatable) {
                                    composerImpl.startReplaceGroup(1578533324);
                                    composerImpl.startReplaceGroup(328018613);
                                    Object objRememberedValue4 = composerImpl.rememberedValue();
                                    companion.getClass();
                                    Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                                    if (objRememberedValue4 == composer$Companion$Empty$1) {
                                        objRememberedValue4 = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);
                                        composerImpl.updateRememberedValue(objRememberedValue4);
                                    }
                                    final MutableState mutableState = (MutableState) objRememberedValue4;
                                    composerImpl.end(false);
                                    Unit unit = Unit.INSTANCE;
                                    composerImpl.startReplaceGroup(328020641);
                                    boolean z7 = (i3 & 57344) == 16384;
                                    Object objRememberedValue5 = composerImpl.rememberedValue();
                                    if (z7 || objRememberedValue5 == composer$Companion$Empty$1) {
                                        objRememberedValue5 = new CommonTileKt$SmallTileContent$2$1(z5, mutableState, null);
                                        composerImpl.updateRememberedValue(objRememberedValue5);
                                    }
                                    composerImpl.end(false);
                                    EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) objRememberedValue5);
                                    if (icon instanceof Icon.Resource) {
                                        composerImpl.startReplaceGroup(1578847292);
                                        AnimatedImageVector.Companion companion2 = AnimatedImageVector.Companion;
                                        Icon.Resource resource = (Icon.Resource) icon;
                                        AnimatedImageVector animatedImageVectorAnimatedVectorResource = AnimatedVectorResources_androidKt.animatedVectorResource(resource.res, composerImpl, 6);
                                        composerImpl.startMovableGroup(328028676, icon);
                                        composerImpl.startReplaceGroup(328030001);
                                        boolean zChanged2 = composerImpl.changed(resource);
                                        Object objRememberedValue6 = composerImpl.rememberedValue();
                                        if (zChanged2 || objRememberedValue6 == composer$Companion$Empty$1) {
                                            Boolean bool = (Boolean) mutableState.getValue();
                                            bool.getClass();
                                            objRememberedValue6 = SnapshotStateKt.mutableStateOf$default(bool);
                                            composerImpl.updateRememberedValue(objRememberedValue6);
                                        }
                                        MutableState mutableState2 = (MutableState) objRememberedValue6;
                                        composerImpl.end(false);
                                        Integer numValueOf = Integer.valueOf(resource.res);
                                        composerImpl.startReplaceGroup(328033732);
                                        boolean zChanged3 = composerImpl.changed(mutableState2);
                                        Object objRememberedValue7 = composerImpl.rememberedValue();
                                        if (zChanged3 || objRememberedValue7 == composer$Companion$Empty$1) {
                                            objRememberedValue7 = new CommonTileKt$SmallTileContent$painter$1$1(mutableState2, null);
                                            composerImpl.updateRememberedValue(objRememberedValue7);
                                        }
                                        composerImpl.end(false);
                                        EffectsKt.LaunchedEffect(composerImpl, numValueOf, (Function2) objRememberedValue7);
                                        VectorPainter vectorPainterRememberAnimatedVectorPainter = AnimatedVectorPainterResources_androidKt.rememberAnimatedVectorPainter(animatedImageVectorAnimatedVectorResource, ((Boolean) mutableState2.getValue()).booleanValue(), composerImpl);
                                        composerImpl.end(false);
                                        composerImpl.end(false);
                                        painterRememberDrawablePainter = vectorPainterRememberAnimatedVectorPainter;
                                    } else {
                                        if (!(icon instanceof Icon.Loaded)) {
                                            composerImpl.startReplaceGroup(328024409);
                                            composerImpl.end(false);
                                            throw new NoWhenBranchMatchedException();
                                        }
                                        composerImpl.startReplaceGroup(1579321375);
                                        painterRememberDrawablePainter = DrawablePainterKt.rememberDrawablePainter(drawable2, composerImpl);
                                        composerImpl.startReplaceGroup(328049272);
                                        boolean zChangedInstance = composerImpl.changedInstance(drawable2);
                                        Object objRememberedValue8 = composerImpl.rememberedValue();
                                        if (zChangedInstance || objRememberedValue8 == composer$Companion$Empty$1) {
                                            objRememberedValue8 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.CommonTileKt$$ExternalSyntheticLambda6
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj) {
                                                    Object obj2 = drawable2;
                                                    if (obj2 instanceof AnimatedVectorDrawable) {
                                                        ((AnimatedVectorDrawable) obj2).forceAnimationOnUI();
                                                    }
                                                    if (((Boolean) mutableState.getValue()).booleanValue()) {
                                                        ((Animatable) obj2).stop();
                                                    }
                                                    return new DisposableEffectResult() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.CommonTileKt$SmallTileContent_8V94_ZQ$lambda$40$lambda$39$$inlined$onDispose$1
                                                        @Override // androidx.compose.runtime.DisposableEffectResult
                                                        public final void dispose() {
                                                        }
                                                    };
                                                }
                                            };
                                            composerImpl.updateRememberedValue(objRememberedValue8);
                                        }
                                        composerImpl.end(false);
                                        EffectsKt.DisposableEffect(painterRememberDrawablePainter, (Function1) objRememberedValue8, composerImpl);
                                        composerImpl.end(false);
                                    }
                                    ContentDescription contentDescription = icon.getContentDescription();
                                    composerImpl.startReplaceGroup(328065658);
                                    String strLoad = contentDescription == null ? null : ContentDescriptionKt.load(contentDescription, composerImpl);
                                    composerImpl.end(false);
                                    ImageKt.Image(painterRememberDrawablePainter, strLoad, modifierThen, null, null, 0.0f, ColorFilter.Companion.m465tintxETnrds$default(ColorFilter.Companion, ((Color) stateM7animateColorAsStateeuL9pac.getValue()).value), composerImpl, 0, 56);
                                    composerImpl = composerImpl;
                                    composerImpl.end(false);
                                } else {
                                    composerImpl.startReplaceGroup(1580235038);
                                    IconKt.m1074IconFNF3uiM(icon, modifierThen, ((Color) stateM7animateColorAsStateeuL9pac.getValue()).value, composerImpl, 0, 0);
                                    composerImpl.end(false);
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                z4 = z5;
                            }
                        }
                    }
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.CommonTileKt$$ExternalSyntheticLambda7
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            boolean z8 = z4;
                            CommonTileKt.m2906SmallTileContent8V94_ZQ(function1, j, modifier, function04, z8, (Composer) obj, iUpdateChangedFlags, i2);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i3 |= 24576;
            z2 = z;
            if ((i3 & 9363) == 9362) {
                Composer.Companion companion3 = Composer.Companion;
                if (i5 == 0) {
                }
                if (i4 == 0) {
                }
                if (ComposerKt.isTraceInProgress()) {
                }
                Context context2 = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
                Icon icon2 = (Icon) function1.mo781invoke(context2);
                function04 = function03;
                State stateM7animateColorAsStateeuL9pac2 = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(j2, null, "QSTileIconColor", composerImpl, ((i3 >> 3) & 14) | 384, 10);
                composerImpl.startReplaceGroup(328003466);
                int i62 = i3 & 7168;
                if (i62 != 2048) {
                }
                Object objRememberedValue9 = composerImpl.rememberedValue();
                if (z3) {
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        function02 = function0;
        i4 = i2 & 16;
        if (i4 != 0) {
        }
        z2 = z;
        if ((i3 & 9363) == 9362) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:75:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void TileLabel(final String str, final ColorProducer colorProducer, final TextStyle textStyle, Modifier modifier, Function0 function0, Composer composer, final int i, final int i2) {
        String str2;
        int i3;
        final Modifier modifier2;
        final Function0 function02;
        Object objRememberedValue;
        Composer$Companion$Empty$1 composer$Companion$Empty$1;
        Object objRememberedValue2;
        Object objRememberedValue3;
        Object objRememberedValue4;
        ComposerImpl composerImpl;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(53961902);
        if ((i & 6) == 0) {
            str2 = str;
            i3 = (composerImpl2.changed(str2) ? 4 : 2) | i;
        } else {
            str2 = str;
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl2.changedInstance(colorProducer) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl2.changed(textStyle) ? 256 : 128;
        }
        int i4 = i2 & 8;
        if (i4 == 0) {
            if ((i & 3072) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl2.changed(modifier2) ? 2048 : 1024;
            }
            if ((i & 24576) != 0) {
                function02 = function0;
                i3 |= composerImpl2.changedInstance(function02) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
            } else {
                function02 = function0;
            }
            if ((i3 & 9363) == 9362 || !composerImpl2.getSkipping()) {
                Modifier modifier3 = i4 == 0 ? Modifier.Companion : modifier2;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileLabel (CommonTile.kt:291)");
                }
                composerImpl2.startReplaceGroup(-962528137);
                objRememberedValue = composerImpl2.rememberedValue();
                Composer.Companion.getClass();
                composer$Companion$Empty$1 = Composer.Companion.Empty;
                if (objRememberedValue == composer$Companion$Empty$1) {
                    objRememberedValue = SnapshotIntStateKt.mutableIntStateOf(0);
                    composerImpl2.updateRememberedValue(objRememberedValue);
                }
                final MutableIntState mutableIntState = (MutableIntState) objRememberedValue;
                composerImpl2.end(false);
                boolean zBooleanValue = ((Boolean) function02.invoke()).booleanValue();
                Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(modifier3, 1.0f);
                composerImpl2.startReplaceGroup(-962516324);
                objRememberedValue2 = composerImpl2.rememberedValue();
                if (objRememberedValue2 == composer$Companion$Empty$1) {
                    final int i5 = 0;
                    objRememberedValue2 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.CommonTileKt$$ExternalSyntheticLambda9
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            MutableIntState mutableIntState2 = mutableIntState;
                            switch (i5) {
                                case 0:
                                    ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj);
                                    if (((SnapshotMutableIntStateImpl) mutableIntState2).getIntValue() > Float.intBitsToFloat((int) (reusableGraphicsLayerScope.size >> 32))) {
                                        CompositingStrategy.Companion.getClass();
                                        reusableGraphicsLayerScope.m496setCompositingStrategyaDBOjCE(CompositingStrategy.Offscreen);
                                    }
                                    break;
                                case 1:
                                    LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj);
                                    layoutNodeDrawScope.drawContent();
                                    float intValue = ((SnapshotMutableIntStateImpl) mutableIntState2).getIntValue();
                                    CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                                    if (intValue > Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32))) {
                                        CommonTileDefaults.INSTANCE.getClass();
                                        float fMo58toPx0680j_4 = layoutNodeDrawScope.mo58toPx0680j_4(CommonTileDefaults.TileLabelBlurWidth);
                                        long jFloatToRawIntBits = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32)) - fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
                                        Offset.Companion companion = Offset.Companion;
                                        float fIntBitsToFloat = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() & 4294967295L));
                                        long jFloatToRawIntBits2 = (Float.floatToRawIntBits(fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat) & 4294967295L);
                                        Size.Companion companion2 = Size.Companion;
                                        Brush.Companion companion3 = Brush.Companion;
                                        Color.Companion.getClass();
                                        List listAsList = Arrays.asList(Color.m456boximpl(Color.Transparent), Color.m456boximpl(Color.Black));
                                        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32));
                                        float fIntBitsToFloat3 = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32)) - fMo58toPx0680j_4;
                                        TileMode.Companion.getClass();
                                        companion3.getClass();
                                        long jFloatToRawIntBits3 = Float.floatToRawIntBits(fIntBitsToFloat2);
                                        Offset.Companion companion4 = Offset.Companion;
                                        LinearGradient linearGradient = new LinearGradient(listAsList, null, (Float.floatToRawIntBits(0.0f) & 4294967295L) | (jFloatToRawIntBits3 << 32), (Float.floatToRawIntBits(fIntBitsToFloat3) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L), 0, null);
                                        BlendMode.Companion.getClass();
                                        DrawScope.m540drawRectAsUm42w$default(layoutNodeDrawScope, linearGradient, jFloatToRawIntBits, jFloatToRawIntBits2, 0.0f, null, BlendMode.DstIn, 56);
                                    }
                                    break;
                                default:
                                    ((SnapshotMutableIntStateImpl) mutableIntState2).setIntValue((int) (((TextLayoutResult) obj).size >> 32));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(objRememberedValue2);
                }
                composerImpl2.end(false);
                Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(modifierFillMaxWidth, (Function1) objRememberedValue2);
                composerImpl2.startReplaceGroup(-962509234);
                objRememberedValue3 = composerImpl2.rememberedValue();
                if (objRememberedValue3 == composer$Companion$Empty$1) {
                    final int i6 = 1;
                    objRememberedValue3 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.CommonTileKt$$ExternalSyntheticLambda9
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            MutableIntState mutableIntState2 = mutableIntState;
                            switch (i6) {
                                case 0:
                                    ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj);
                                    if (((SnapshotMutableIntStateImpl) mutableIntState2).getIntValue() > Float.intBitsToFloat((int) (reusableGraphicsLayerScope.size >> 32))) {
                                        CompositingStrategy.Companion.getClass();
                                        reusableGraphicsLayerScope.m496setCompositingStrategyaDBOjCE(CompositingStrategy.Offscreen);
                                    }
                                    break;
                                case 1:
                                    LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj);
                                    layoutNodeDrawScope.drawContent();
                                    float intValue = ((SnapshotMutableIntStateImpl) mutableIntState2).getIntValue();
                                    CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                                    if (intValue > Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32))) {
                                        CommonTileDefaults.INSTANCE.getClass();
                                        float fMo58toPx0680j_4 = layoutNodeDrawScope.mo58toPx0680j_4(CommonTileDefaults.TileLabelBlurWidth);
                                        long jFloatToRawIntBits = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32)) - fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
                                        Offset.Companion companion = Offset.Companion;
                                        float fIntBitsToFloat = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() & 4294967295L));
                                        long jFloatToRawIntBits2 = (Float.floatToRawIntBits(fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat) & 4294967295L);
                                        Size.Companion companion2 = Size.Companion;
                                        Brush.Companion companion3 = Brush.Companion;
                                        Color.Companion.getClass();
                                        List listAsList = Arrays.asList(Color.m456boximpl(Color.Transparent), Color.m456boximpl(Color.Black));
                                        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32));
                                        float fIntBitsToFloat3 = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32)) - fMo58toPx0680j_4;
                                        TileMode.Companion.getClass();
                                        companion3.getClass();
                                        long jFloatToRawIntBits3 = Float.floatToRawIntBits(fIntBitsToFloat2);
                                        Offset.Companion companion4 = Offset.Companion;
                                        LinearGradient linearGradient = new LinearGradient(listAsList, null, (Float.floatToRawIntBits(0.0f) & 4294967295L) | (jFloatToRawIntBits3 << 32), (Float.floatToRawIntBits(fIntBitsToFloat3) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L), 0, null);
                                        BlendMode.Companion.getClass();
                                        DrawScope.m540drawRectAsUm42w$default(layoutNodeDrawScope, linearGradient, jFloatToRawIntBits, jFloatToRawIntBits2, 0.0f, null, BlendMode.DstIn, 56);
                                    }
                                    break;
                                default:
                                    ((SnapshotMutableIntStateImpl) mutableIntState2).setIntValue((int) (((TextLayoutResult) obj).size >> 32));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(objRememberedValue3);
                }
                composerImpl2.end(false);
                Modifier modifierM27basicMarquee1Mj1MLw$default = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(DrawModifierKt.drawWithContent(modifierGraphicsLayer, (Function1) objRememberedValue3), zBooleanValue ? 1 : 0, 54);
                composerImpl2.startReplaceGroup(-962520718);
                objRememberedValue4 = composerImpl2.rememberedValue();
                if (objRememberedValue4 == composer$Companion$Empty$1) {
                    final int i7 = 2;
                    objRememberedValue4 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.CommonTileKt$$ExternalSyntheticLambda9
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            MutableIntState mutableIntState2 = mutableIntState;
                            switch (i7) {
                                case 0:
                                    ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj);
                                    if (((SnapshotMutableIntStateImpl) mutableIntState2).getIntValue() > Float.intBitsToFloat((int) (reusableGraphicsLayerScope.size >> 32))) {
                                        CompositingStrategy.Companion.getClass();
                                        reusableGraphicsLayerScope.m496setCompositingStrategyaDBOjCE(CompositingStrategy.Offscreen);
                                    }
                                    break;
                                case 1:
                                    LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj);
                                    layoutNodeDrawScope.drawContent();
                                    float intValue = ((SnapshotMutableIntStateImpl) mutableIntState2).getIntValue();
                                    CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                                    if (intValue > Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32))) {
                                        CommonTileDefaults.INSTANCE.getClass();
                                        float fMo58toPx0680j_4 = layoutNodeDrawScope.mo58toPx0680j_4(CommonTileDefaults.TileLabelBlurWidth);
                                        long jFloatToRawIntBits = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32)) - fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
                                        Offset.Companion companion = Offset.Companion;
                                        float fIntBitsToFloat = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() & 4294967295L));
                                        long jFloatToRawIntBits2 = (Float.floatToRawIntBits(fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat) & 4294967295L);
                                        Size.Companion companion2 = Size.Companion;
                                        Brush.Companion companion3 = Brush.Companion;
                                        Color.Companion.getClass();
                                        List listAsList = Arrays.asList(Color.m456boximpl(Color.Transparent), Color.m456boximpl(Color.Black));
                                        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32));
                                        float fIntBitsToFloat3 = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32)) - fMo58toPx0680j_4;
                                        TileMode.Companion.getClass();
                                        companion3.getClass();
                                        long jFloatToRawIntBits3 = Float.floatToRawIntBits(fIntBitsToFloat2);
                                        Offset.Companion companion4 = Offset.Companion;
                                        LinearGradient linearGradient = new LinearGradient(listAsList, null, (Float.floatToRawIntBits(0.0f) & 4294967295L) | (jFloatToRawIntBits3 << 32), (Float.floatToRawIntBits(fIntBitsToFloat3) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L), 0, null);
                                        BlendMode.Companion.getClass();
                                        DrawScope.m540drawRectAsUm42w$default(layoutNodeDrawScope, linearGradient, jFloatToRawIntBits, jFloatToRawIntBits2, 0.0f, null, BlendMode.DstIn, 56);
                                    }
                                    break;
                                default:
                                    ((SnapshotMutableIntStateImpl) mutableIntState2).setIntValue((int) (((TextLayoutResult) obj).size >> 32));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(objRememberedValue4);
                }
                composerImpl2.end(false);
                composerImpl = composerImpl2;
                BasicTextKt.m194BasicTextRWo7tUw(str2, modifierM27basicMarquee1Mj1MLw$default, textStyle, (Function1) objRememberedValue4, 0, false, 1, 0, colorProducer, (TextAutoSize) null, (Composer) composerImpl, (i3 & 14) | 1575936 | (i3 & 896) | ((i3 << 21) & 234881024), 688);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                modifier2 = modifier3;
            } else {
                composerImpl2.skipToGroupEnd();
                composerImpl = composerImpl2;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.CommonTileKt$$ExternalSyntheticLambda12
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        Function0 function03 = function02;
                        CommonTileKt.TileLabel(str, colorProducer, textStyle, modifier2, function03, (Composer) obj, iUpdateChangedFlags, i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 3072;
        modifier2 = modifier;
        if ((i & 24576) != 0) {
        }
        if ((i3 & 9363) == 9362) {
            if (i4 == 0) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            composerImpl2.startReplaceGroup(-962528137);
            objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
            }
            final MutableIntState mutableIntState2 = (MutableIntState) objRememberedValue;
            composerImpl2.end(false);
            boolean zBooleanValue2 = ((Boolean) function02.invoke()).booleanValue();
            Modifier modifierFillMaxWidth2 = SizeKt.fillMaxWidth(modifier3, 1.0f);
            composerImpl2.startReplaceGroup(-962516324);
            objRememberedValue2 = composerImpl2.rememberedValue();
            if (objRememberedValue2 == composer$Companion$Empty$1) {
            }
            composerImpl2.end(false);
            Modifier modifierGraphicsLayer2 = GraphicsLayerModifierKt.graphicsLayer(modifierFillMaxWidth2, (Function1) objRememberedValue2);
            composerImpl2.startReplaceGroup(-962509234);
            objRememberedValue3 = composerImpl2.rememberedValue();
            if (objRememberedValue3 == composer$Companion$Empty$1) {
            }
            composerImpl2.end(false);
            Modifier modifierM27basicMarquee1Mj1MLw$default2 = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(DrawModifierKt.drawWithContent(modifierGraphicsLayer2, (Function1) objRememberedValue3), zBooleanValue2 ? 1 : 0, 54);
            composerImpl2.startReplaceGroup(-962520718);
            objRememberedValue4 = composerImpl2.rememberedValue();
            if (objRememberedValue4 == composer$Companion$Empty$1) {
            }
            composerImpl2.end(false);
            composerImpl = composerImpl2;
            BasicTextKt.m194BasicTextRWo7tUw(str2, modifierM27basicMarquee1Mj1MLw$default2, textStyle, (Function1) objRememberedValue4, 0, false, 1, 0, colorProducer, (TextAutoSize) null, (Composer) composerImpl, (i3 & 14) | 1575936 | (i3 & 896) | ((i3 << 21) & 234881024), 688);
            if (ComposerKt.isTraceInProgress()) {
            }
            modifier2 = modifier3;
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
