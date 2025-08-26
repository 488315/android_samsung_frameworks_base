package androidx.compose.foundation.contextmenu;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.ScrollKt;
import androidx.compose.foundation.ScrollState;
import androidx.compose.foundation.ScrollingContainerKt;
import androidx.compose.foundation.ScrollingLayoutElement;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.IntrinsicKt;
import androidx.compose.foundation.layout.IntrinsicSize;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.BasicTextKt;
import androidx.compose.foundation.text.TextAutoSize;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ShadowKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.text.PlatformTextStyle;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.window.AndroidPopup_androidKt;
import androidx.compose.ui.window.PopupPositionProvider;
import androidx.compose.ui.window.PopupProperties;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class ContextMenuUi_androidKt {
    public static final ContextMenuColors DefaultContextMenuColors;
    public static final PopupProperties DefaultPopupProperties = new PopupProperties(true, false, false, false, 14, (DefaultConstructorMarker) null);

    static {
        Color.Companion.getClass();
        long j = Color.White;
        long j2 = Color.Black;
        DefaultContextMenuColors = new ContextMenuColors(j, j2, j2, ColorKt.Color(Color.m463getRedimpl(j2), Color.m462getGreenimpl(j2), Color.m460getBlueimpl(j2), 0.38f, Color.m461getColorSpaceimpl(j2)), ColorKt.Color(Color.m463getRedimpl(j2), Color.m462getGreenimpl(j2), Color.m460getBlueimpl(j2), 0.38f, Color.m461getColorSpaceimpl(j2)), null);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:68:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ContextMenuColumn(final ContextMenuColors contextMenuColors, Modifier modifier, final Function3 function3, Composer composer, final int i, final int i2) {
        int i3;
        Modifier modifier2;
        final Modifier modifier3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-921259293);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(contextMenuColors) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i4 = i2 & 2;
        if (i4 == 0) {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 32 : 16;
            }
            if ((i2 & 4) == 0) {
                i3 |= 384;
            } else if ((i & 384) == 0) {
                i3 |= composerImpl.changedInstance(function3) ? 256 : 128;
            }
            if (composerImpl.shouldExecute(i3 & 1, (i3 & 147) == 146)) {
                composerImpl.skipToGroupEnd();
                modifier3 = modifier2;
            } else {
                Modifier modifier4 = i4 != 0 ? Modifier.Companion : modifier2;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.foundation.contextmenu.ContextMenuColumn (ContextMenuUi.android.kt:147)");
                }
                ContextMenuSpec.INSTANCE.getClass();
                Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(IntrinsicKt.width(BackgroundKt.m26backgroundbw27NRU(ShadowKt.m366shadows4CzXII$default(modifier4, ContextMenuSpec.MenuContainerElevation, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(ContextMenuSpec.CornerRadius), 0L, 28), contextMenuColors.backgroundColor, RectangleShapeKt.RectangleShape), IntrinsicSize.Max), 0.0f, ContextMenuSpec.VerticalPadding, 1);
                ScrollState scrollStateRememberScrollState = ScrollKt.rememberScrollState(composerImpl);
                Modifier modifierThen = ScrollingContainerKt.scrollingContainer(modifierM127paddingVpY3zN4$default, scrollStateRememberScrollState, Orientation.Vertical, (14 & 2) != 0, false, null, scrollStateRememberScrollState.internalInteractionSource, true, null, null).then(new ScrollingLayoutElement(scrollStateRememberScrollState, false, true));
                int i5 = (i3 << 3) & 7168;
                Arrangement.INSTANCE.getClass();
                Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                Alignment.Companion.getClass();
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierThen);
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
                Updater.m337setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                function3.invoke(ColumnScopeInstance.INSTANCE, composerImpl, Integer.valueOf(((i5 >> 6) & 112) | 6));
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                modifier3 = modifier4;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.contextmenu.ContextMenuUi_androidKt.ContextMenuColumn.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Number) obj2).intValue();
                        ContextMenuUi_androidKt.ContextMenuColumn(contextMenuColors, modifier3, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 48;
        modifier2 = modifier;
        if ((i2 & 4) == 0) {
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 147) == 146)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:131:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:136:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0110  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ContextMenuItem(final String str, final boolean z, final ContextMenuColors contextMenuColors, Modifier modifier, Function3 function3, final Function0 function0, Composer composer, final int i, final int i2) {
        int i3;
        Modifier modifier2;
        int i4;
        Function3 function32;
        final Modifier modifier3;
        final Function3 function33;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i5;
        Modifier modifier4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(791018367);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(str) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(z) ? 32 : 16;
        }
        if ((i2 & 4) != 0) {
            i3 |= 384;
        } else if ((i & 384) == 0) {
            i3 |= composerImpl.changed(contextMenuColors) ? 256 : 128;
        }
        int i6 = i2 & 8;
        if (i6 != 0) {
            i3 |= 3072;
        } else {
            if ((i & 3072) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 2048 : 1024;
            }
            i4 = i2 & 16;
            if (i4 != 0) {
                if ((i & 24576) == 0) {
                    function32 = function3;
                    i3 |= composerImpl.changedInstance(function32) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                if ((i2 & 32) != 0) {
                    i3 |= 196608;
                } else if ((i & 196608) == 0) {
                    i3 |= composerImpl.changedInstance(function0) ? 131072 : 65536;
                }
                if (composerImpl.shouldExecute(i3 & 1, (74899 & i3) != 74898)) {
                    Modifier modifier5 = i6 != 0 ? Modifier.Companion : modifier2;
                    Function3 function34 = i4 != 0 ? null : function32;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.foundation.contextmenu.ContextMenuItem (ContextMenuUi.android.kt:185)");
                    }
                    ContextMenuSpec.INSTANCE.getClass();
                    BiasAlignment.Vertical vertical = ContextMenuSpec.LabelVerticalTextAlignment;
                    Arrangement arrangement = Arrangement.INSTANCE;
                    float f = ContextMenuSpec.HorizontalPadding;
                    arrangement.getClass();
                    Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(f);
                    boolean z2 = ((i3 & 112) == 32) | ((458752 & i3) == 131072);
                    Object objRememberedValue = composerImpl.rememberedValue();
                    if (!z2) {
                        Composer.Companion.getClass();
                        if (objRememberedValue == Composer.Companion.Empty) {
                            objRememberedValue = new Function0() { // from class: androidx.compose.foundation.contextmenu.ContextMenuUi_androidKt$ContextMenuItem$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    if (z) {
                                        function0.invoke();
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue);
                        }
                        Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(ClickableKt.m35clickableXHw0xAI$default(modifier5, z, str, (Function0) objRememberedValue, 4), 1.0f);
                        float f2 = ContextMenuSpec.ContainerWidthMin;
                        float f3 = ContextMenuSpec.ContainerWidthMax;
                        float f4 = ContextMenuSpec.ListItemHeight;
                        Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(SizeKt.m142sizeInqDBjuR0(modifierFillMaxWidth, f2, f4, f3, f4), f, 0.0f, 2);
                        RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM92spacedBy0680j_4, vertical, composerImpl, 54);
                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM127paddingVpY3zN4$default);
                        ComposeUiNode.Companion.getClass();
                        Function0 function02 = ComposeUiNode.Companion.Constructor;
                        if (composerImpl.applier == null) {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                        composerImpl.startReusableNode();
                        if (composerImpl.inserting) {
                            composerImpl.createNode(function02);
                        } else {
                            composerImpl.useNode();
                        }
                        Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                        Updater.m337setimpl(composerImpl, rowMeasurePolicy, function2);
                        Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                        Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
                        }
                        Function2 function24 = ComposeUiNode.Companion.SetModifier;
                        Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
                        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                        if (function34 == null) {
                            composerImpl.startReplaceGroup(554788141);
                            composerImpl.end(false);
                            i5 = i3;
                            modifier4 = modifier5;
                        } else {
                            composerImpl.startReplaceGroup(554788142);
                            Modifier.Companion companion = Modifier.Companion;
                            float f5 = ContextMenuSpec.IconSize;
                            Modifier modifierM138requiredSizeInqDBjuR0$default = SizeKt.m138requiredSizeInqDBjuR0$default(companion, f5, 0.0f, f5, f5, 2);
                            Alignment.Companion.getClass();
                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                            i5 = i3;
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierM138requiredSizeInqDBjuR0$default);
                            composerImpl.startReusableNode();
                            modifier4 = modifier5;
                            if (composerImpl.inserting) {
                                composerImpl.createNode(function02);
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
                            function34.invoke(Color.m456boximpl(z ? contextMenuColors.iconColor : contextMenuColors.disabledIconColor), composerImpl, 0);
                            composerImpl.end(true);
                            Unit unit = Unit.INSTANCE;
                            composerImpl.end(false);
                        }
                        int i7 = (i5 & 14) | 1572864;
                        Function3 function35 = function34;
                        BasicTextKt.m194BasicTextRWo7tUw(str, rowScopeInstance.weight(Modifier.Companion, 1.0f, true), new TextStyle(z ? contextMenuColors.textColor : contextMenuColors.disabledTextColor, ContextMenuSpec.FontSize, ContextMenuSpec.FontWeight, (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, ContextMenuSpec.LetterSpacing, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, ContextMenuSpec.LabelHorizontalTextAlignment, 0, ContextMenuSpec.LineHeight, (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16613240, (DefaultConstructorMarker) null), (Function1) null, 0, false, 1, 0, (ColorProducer) null, (TextAutoSize) null, (Composer) composerImpl, i7, 952);
                        composerImpl.end(true);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        function33 = function35;
                        modifier3 = modifier4;
                    }
                } else {
                    composerImpl.skipToGroupEnd();
                    modifier3 = modifier2;
                    function33 = function32;
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.contextmenu.ContextMenuUi_androidKt.ContextMenuItem.3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ContextMenuUi_androidKt.ContextMenuItem(str, z, contextMenuColors, modifier3, function33, function0, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i3 |= 24576;
            function32 = function3;
            if ((i2 & 32) != 0) {
            }
            if (composerImpl.shouldExecute(i3 & 1, (74899 & i3) != 74898)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 16;
        if (i4 != 0) {
        }
        function32 = function3;
        if ((i2 & 32) != 0) {
        }
        if (composerImpl.shouldExecute(i3 & 1, (74899 & i3) != 74898)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:98:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ContextMenuPopup(final PopupPositionProvider popupPositionProvider, final Function0 function0, Modifier modifier, final Function1 function1, Composer composer, final int i, final int i2) throws Throwable {
        PopupPositionProvider popupPositionProvider2;
        int i3;
        Function0 function02;
        int i4;
        Modifier modifier2;
        Function1 function12;
        final Modifier modifier3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(712057293);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
            popupPositionProvider2 = popupPositionProvider;
        } else {
            popupPositionProvider2 = popupPositionProvider;
            if ((i & 6) == 0) {
                i3 = (composerImpl.changed(popupPositionProvider2) ? 4 : 2) | i;
            } else {
                i3 = i;
            }
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                function02 = function0;
                i3 |= composerImpl.changedInstance(function02) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 != 0) {
                if ((i & 384) == 0) {
                    modifier2 = modifier;
                    i3 |= composerImpl.changed(modifier2) ? 256 : 128;
                }
                if ((i2 & 8) != 0) {
                    i3 |= 3072;
                    function12 = function1;
                } else {
                    function12 = function1;
                    if ((i & 3072) == 0) {
                        i3 |= composerImpl.changedInstance(function12) ? 2048 : 1024;
                    }
                }
                if (composerImpl.shouldExecute(i3 & 1, (i3 & 1171) != 1170)) {
                    Modifier modifier4 = i4 != 0 ? Modifier.Companion : modifier2;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.foundation.contextmenu.ContextMenuPopup (ContextMenuUi.android.kt:106)");
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.foundation.contextmenu.computeContextMenuColors (ContextMenuUi.android.kt:363)");
                    }
                    Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
                    boolean zChanged = composerImpl.changed((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)) | composerImpl.changed(context);
                    Object objRememberedValue = composerImpl.rememberedValue();
                    if (!zChanged) {
                        Composer.Companion.getClass();
                        if (objRememberedValue == Composer.Companion.Empty) {
                            ContextMenuColors contextMenuColors = DefaultContextMenuColors;
                            long jColor = contextMenuColors.backgroundColor;
                            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(R.style.Widget.PopupMenu, new int[]{R.attr.colorBackground});
                            int iM469toArgb8_81llA = ColorKt.m469toArgb8_81llA(jColor);
                            int color = typedArrayObtainStyledAttributes.getColor(0, iM469toArgb8_81llA);
                            typedArrayObtainStyledAttributes.recycle();
                            if (color != iM469toArgb8_81llA) {
                                jColor = ColorKt.Color(color);
                            }
                            long j = jColor;
                            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(R.style.TextAppearance.Widget.PopupMenu.Large, new int[]{R.attr.textColorPrimary});
                            ColorStateList colorStateList = typedArrayObtainStyledAttributes2.getColorStateList(0);
                            typedArrayObtainStyledAttributes2.recycle();
                            long jColor2 = contextMenuColors.textColor;
                            int iM469toArgb8_81llA2 = ColorKt.m469toArgb8_81llA(jColor2);
                            Integer numValueOf = colorStateList != null ? Integer.valueOf(colorStateList.getColorForState(new int[]{R.attr.state_enabled}, iM469toArgb8_81llA2)) : null;
                            if (numValueOf != null && numValueOf.intValue() != iM469toArgb8_81llA2) {
                                jColor2 = ColorKt.Color(numValueOf.intValue());
                            }
                            long j2 = jColor2;
                            long jColor3 = contextMenuColors.disabledTextColor;
                            int iM469toArgb8_81llA3 = ColorKt.m469toArgb8_81llA(jColor3);
                            Integer numValueOf2 = colorStateList != null ? Integer.valueOf(colorStateList.getColorForState(new int[]{-16842910}, iM469toArgb8_81llA3)) : null;
                            if (numValueOf2 != null && numValueOf2.intValue() != iM469toArgb8_81llA3) {
                                jColor3 = ColorKt.Color(numValueOf2.intValue());
                            }
                            long j3 = jColor3;
                            ContextMenuColors contextMenuColors2 = new ContextMenuColors(j, j2, j2, j3, j3, null);
                            composerImpl.updateRememberedValue(contextMenuColors2);
                            objRememberedValue = contextMenuColors2;
                        }
                        ContextMenuColors contextMenuColors3 = (ContextMenuColors) objRememberedValue;
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        Modifier modifier5 = modifier4;
                        ContextMenuPopup(popupPositionProvider2, function02, modifier5, contextMenuColors3, function12, composerImpl, (i3 & 1022) | ((i3 << 3) & 57344), 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        modifier3 = modifier5;
                    }
                } else {
                    composerImpl.skipToGroupEnd();
                    modifier3 = modifier2;
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.contextmenu.ContextMenuUi_androidKt.ContextMenuPopup.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) throws Throwable {
                            ((Number) obj2).intValue();
                            ContextMenuUi_androidKt.ContextMenuPopup(popupPositionProvider, function0, modifier3, function1, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i3 |= 384;
            modifier2 = modifier;
            if ((i2 & 8) != 0) {
            }
            if (composerImpl.shouldExecute(i3 & 1, (i3 & 1171) != 1170)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        function02 = function0;
        i4 = i2 & 4;
        if (i4 != 0) {
        }
        modifier2 = modifier;
        if ((i2 & 8) != 0) {
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 1171) != 1170)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:75:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ContextMenuPopup(final PopupPositionProvider popupPositionProvider, final Function0 function0, Modifier modifier, final ContextMenuColors contextMenuColors, final Function1 function1, Composer composer, final int i, final int i2) throws Throwable {
        int i3;
        Modifier modifier2;
        final Modifier modifier3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1447189339);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(popupPositionProvider) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(function0) ? 32 : 16;
        }
        int i4 = i2 & 4;
        if (i4 == 0) {
            if ((i & 384) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 256 : 128;
            }
            if ((i2 & 8) == 0) {
                i3 |= 3072;
            } else if ((i & 3072) == 0) {
                i3 |= composerImpl.changed(contextMenuColors) ? 2048 : 1024;
            }
            if ((i2 & 16) == 0) {
                i3 |= 24576;
            } else if ((i & 24576) == 0) {
                i3 |= composerImpl.changedInstance(function1) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
            }
            if (!composerImpl.shouldExecute(i3 & 1, (i3 & 9363) == 9362)) {
                final Modifier modifier4 = i4 != 0 ? Modifier.Companion : modifier2;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.foundation.contextmenu.ContextMenuPopup (ContextMenuUi.android.kt:124)");
                }
                AndroidPopup_androidKt.Popup(popupPositionProvider, function0, DefaultPopupProperties, ComposableLambdaKt.rememberComposableLambda(795909757, new Function2() { // from class: androidx.compose.foundation.contextmenu.ContextMenuUi_androidKt.ContextMenuPopup.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Composer composer2 = (Composer) obj;
                        int iIntValue = ((Number) obj2).intValue();
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.foundation.contextmenu.ContextMenuPopup.<anonymous> (ContextMenuUi.android.kt:130)");
                            }
                            final ContextMenuColors contextMenuColors2 = contextMenuColors;
                            Modifier modifier5 = modifier4;
                            final Function1 function12 = function1;
                            ContextMenuUi_androidKt.ContextMenuColumn(contextMenuColors2, modifier5, ComposableLambdaKt.rememberComposableLambda(1156688164, new Function3() { // from class: androidx.compose.foundation.contextmenu.ContextMenuUi_androidKt.ContextMenuPopup.2.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                    Composer composer3 = (Composer) obj4;
                                    int iIntValue2 = ((Number) obj5).intValue();
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                    if (composerImpl3.shouldExecute(iIntValue2 & 1, (iIntValue2 & 17) != 16)) {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.foundation.contextmenu.ContextMenuPopup.<anonymous>.<anonymous> (ContextMenuUi.android.kt:131)");
                                        }
                                        Object objRememberedValue = composerImpl3.rememberedValue();
                                        Composer.Companion.getClass();
                                        if (objRememberedValue == Composer.Companion.Empty) {
                                            objRememberedValue = new ContextMenuScope();
                                            composerImpl3.updateRememberedValue(objRememberedValue);
                                        }
                                        ContextMenuScope contextMenuScope = (ContextMenuScope) objRememberedValue;
                                        Function1 function13 = function12;
                                        ContextMenuColors contextMenuColors3 = contextMenuColors2;
                                        contextMenuScope.composables.clear();
                                        function13.mo781invoke(contextMenuScope);
                                        contextMenuScope.Content$foundation_release(contextMenuColors3, composerImpl3, 0);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    } else {
                                        composerImpl3.skipToGroupEnd();
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composerImpl2), composerImpl2, 384, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        } else {
                            composerImpl2.skipToGroupEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, (i3 & 14) | 3456 | (i3 & 112), 0);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                modifier3 = modifier4;
            } else {
                composerImpl.skipToGroupEnd();
                modifier3 = modifier2;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.contextmenu.ContextMenuUi_androidKt.ContextMenuPopup.3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) throws Throwable {
                        ((Number) obj2).intValue();
                        ContextMenuUi_androidKt.ContextMenuPopup(popupPositionProvider, function0, modifier3, contextMenuColors, function1, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 384;
        modifier2 = modifier;
        if ((i2 & 8) == 0) {
        }
        if ((i2 & 16) == 0) {
        }
        if (!composerImpl.shouldExecute(i3 & 1, (i3 & 9363) == 9362)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
