package androidx.compose.foundation.text.selection;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.text.Handle;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.AbsoluteAlignment;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAbsoluteAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.CacheDrawScope;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidImageBitmap;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.BlendModeColorFilter;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.CanvasKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.ImageBitmapConfig;
import androidx.compose.ui.graphics.ImageBitmapKt;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.style.ResolvedTextDirection;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DpSize;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.window.AndroidPopup_androidKt;
import androidx.compose.ui.window.PopupProperties;
import androidx.compose.ui.window.SecureFlagPolicy;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class AndroidSelectionHandles_androidKt {
    /* JADX WARN: Removed duplicated region for block: B:52:0x0093  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void HandlePopup(final OffsetProvider offsetProvider, final Alignment alignment, final Function2 function2, Composer composer, final int i) throws Throwable {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(476043083);
        if ((i & 6) == 0) {
            i2 = ((i & 8) == 0 ? composerImpl.changed(offsetProvider) : composerImpl.changedInstance(offsetProvider) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(alignment) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 256 : 128;
        }
        boolean z = true;
        if (composerImpl.shouldExecute(i2 & 1, (i2 & 147) != 146)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.text.selection.HandlePopup (AndroidSelectionHandles.android.kt:223)");
            }
            boolean z2 = (i2 & 112) == 32;
            if ((i2 & 14) != 4 && ((i2 & 8) == 0 || !composerImpl.changed(offsetProvider))) {
                z = false;
            }
            boolean z3 = z2 | z;
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!z3) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new HandlePositionProvider(alignment, offsetProvider);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                AndroidPopup_androidKt.Popup((HandlePositionProvider) objRememberedValue, null, new PopupProperties(false, false, false, (SecureFlagPolicy) null, true, false, 15, (DefaultConstructorMarker) null), function2, composerImpl, ((i2 << 3) & 7168) | 384, 2);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt.HandlePopup.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Throwable {
                    ((Number) obj2).intValue();
                    AndroidSelectionHandles_androidKt.HandlePopup(offsetProvider, alignment, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x010c  */
    /* renamed from: SelectionHandle-wLIcFTc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m233SelectionHandlewLIcFTc(final OffsetProvider offsetProvider, final boolean z, final ResolvedTextDirection resolvedTextDirection, final boolean z2, long j, final float f, final Modifier modifier, Composer composer, final int i, final int i2) {
        int i3;
        long j2;
        final boolean z3;
        boolean zChanged;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-466280168);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = ((i & 8) == 0 ? composerImpl.changed(offsetProvider) : composerImpl.changedInstance(offsetProvider) ? 4 : 2) | i;
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
            i3 |= composerImpl.changed(resolvedTextDirection) ? 256 : 128;
        }
        if ((i2 & 8) != 0) {
            i3 |= 3072;
        } else if ((i & 3072) == 0) {
            i3 |= composerImpl.changed(z2) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            j2 = j;
            i3 |= ((i2 & 16) == 0 && composerImpl.changed(j2)) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        } else {
            j2 = j;
        }
        if ((i2 & 64) != 0) {
            i3 |= 1572864;
        } else if ((i & 1572864) == 0) {
            i3 |= composerImpl.changed(modifier) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if (composerImpl.shouldExecute(i3 & 1, (533651 & i3) != 533650)) {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if ((i2 & 16) != 0) {
                    DpSize.Companion.getClass();
                    j2 = DpSize.Unspecified;
                    i3 &= -57345;
                }
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.foundation.text.selection.SelectionHandle (AndroidSelectionHandles.android.kt:65)");
                }
                if (z) {
                    float f2 = SelectionHandlesKt.HandleWidth;
                    if (!((resolvedTextDirection == ResolvedTextDirection.Ltr && !z2) || (resolvedTextDirection == ResolvedTextDirection.Rtl && z2))) {
                    }
                } else {
                    float f3 = SelectionHandlesKt.HandleWidth;
                    z3 = (resolvedTextDirection == ResolvedTextDirection.Ltr && !z2) || (resolvedTextDirection == ResolvedTextDirection.Rtl && z2);
                }
                AbsoluteAlignment.INSTANCE.getClass();
                BiasAbsoluteAlignment biasAbsoluteAlignment = !z3 ? AbsoluteAlignment.TopRight : AbsoluteAlignment.TopLeft;
                int i4 = i3 & 14;
                zChanged = ((i3 & 112) == 32) | (i4 != 4 || ((i3 & 8) != 0 && composerImpl.changedInstance(offsetProvider))) | composerImpl.changed(z3);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (zChanged) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function1() { // from class: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt$SelectionHandle$semanticsModifier$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                                long jMo198provideF1C5BW0 = offsetProvider.mo198provideF1C5BW0();
                                ((SemanticsConfiguration) semanticsPropertyReceiver).set(SelectionHandlesKt.SelectionHandleInfoKey, new SelectionHandleInfo(z ? Handle.SelectionStart : Handle.SelectionEnd, jMo198provideF1C5BW0, z3 ? SelectionHandleAnchor.Left : SelectionHandleAnchor.Right, (9223372034707292159L & jMo198provideF1C5BW0) != 9205357640488583168L, null));
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    final Modifier modifierSemantics = SemanticsModifierKt.semantics(modifier, false, (Function1) objRememberedValue);
                    final ViewConfiguration viewConfiguration = (ViewConfiguration) composerImpl.consume(CompositionLocalsKt.LocalViewConfiguration);
                    final boolean z4 = z3;
                    final long j3 = j2;
                    HandlePopup(offsetProvider, biasAbsoluteAlignment, ComposableLambdaKt.rememberComposableLambda(1365123137, new Function2() { // from class: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt$SelectionHandle$1
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
                                    ComposerKt.traceEventStart("androidx.compose.foundation.text.selection.SelectionHandle.<anonymous> (AndroidSelectionHandles.android.kt:85)");
                                }
                                ProvidedValue providedValueDefaultProvidedValue$runtime_release = CompositionLocalsKt.LocalViewConfiguration.defaultProvidedValue$runtime_release(viewConfiguration);
                                final long j4 = j3;
                                final boolean z5 = z4;
                                final Modifier modifier2 = modifierSemantics;
                                final OffsetProvider offsetProvider2 = offsetProvider;
                                CompositionLocalKt.CompositionLocalProvider(providedValueDefaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(1260045569, new Function2() { // from class: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt$SelectionHandle$1.1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    /* JADX WARN: Removed duplicated region for block: B:32:0x00d1  */
                                    /* JADX WARN: Removed duplicated region for block: B:40:0x0108  */
                                    @Override // kotlin.jvm.functions.Function2
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj3, Object obj4) {
                                        Arrangement.Horizontal horizontal;
                                        Composer composer3 = (Composer) obj3;
                                        int iIntValue2 = ((Number) obj4).intValue();
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                        if (composerImpl3.shouldExecute(iIntValue2 & 1, (iIntValue2 & 3) != 2)) {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.foundation.text.selection.SelectionHandle.<anonymous>.<anonymous> (AndroidSelectionHandles.android.kt:86)");
                                            }
                                            long j5 = j4;
                                            Composer.Companion companion = Composer.Companion;
                                            if (j5 != 9205357640488583168L) {
                                                composerImpl3.startReplaceGroup(-837626688);
                                                if (z5) {
                                                    Arrangement.Absolute.INSTANCE.getClass();
                                                    horizontal = Arrangement.Absolute.Right;
                                                } else {
                                                    Arrangement.Absolute.INSTANCE.getClass();
                                                    horizontal = Arrangement.Absolute.Left;
                                                }
                                                Modifier modifierM138requiredSizeInqDBjuR0$default = SizeKt.m138requiredSizeInqDBjuR0$default(modifier2, DpSize.m847getWidthD9Ej5fM(j4), DpSize.m846getHeightD9Ej5fM(j4), 0.0f, 0.0f, 12);
                                                final OffsetProvider offsetProvider3 = offsetProvider2;
                                                boolean z6 = z5;
                                                Alignment.Companion.getClass();
                                                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(horizontal, Alignment.Companion.Top, composerImpl3, 0);
                                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, modifierM138requiredSizeInqDBjuR0$default);
                                                ComposeUiNode.Companion.getClass();
                                                Function0 function0 = ComposeUiNode.Companion.Constructor;
                                                if (composerImpl3.applier == null) {
                                                    ComposablesKt.invalidApplier();
                                                    throw null;
                                                }
                                                composerImpl3.startReusableNode();
                                                if (composerImpl3.inserting) {
                                                    composerImpl3.createNode(function0);
                                                } else {
                                                    composerImpl3.useNode();
                                                }
                                                Updater.m337setimpl(composerImpl3, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                                                }
                                                Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                                                Modifier.Companion companion2 = Modifier.Companion;
                                                boolean zChangedInstance = composerImpl3.changedInstance(offsetProvider3);
                                                Object objRememberedValue2 = composerImpl3.rememberedValue();
                                                if (!zChangedInstance) {
                                                    companion.getClass();
                                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                                        objRememberedValue2 = new Function0() { // from class: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt$SelectionHandle$1$1$1$1$1
                                                            {
                                                                super(0);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                return Boolean.valueOf((offsetProvider3.mo198provideF1C5BW0() & 9223372034707292159L) != 9205357640488583168L);
                                                            }
                                                        };
                                                        composerImpl3.updateRememberedValue(objRememberedValue2);
                                                    }
                                                    AndroidSelectionHandles_androidKt.SelectionHandleIcon(6, composerImpl3, companion2, (Function0) objRememberedValue2, z6);
                                                    composerImpl3.end(true);
                                                    composerImpl3.end(false);
                                                }
                                            } else {
                                                composerImpl3.startReplaceGroup(-836697680);
                                                Modifier modifier3 = modifier2;
                                                boolean zChangedInstance2 = composerImpl3.changedInstance(offsetProvider2);
                                                final OffsetProvider offsetProvider4 = offsetProvider2;
                                                Object objRememberedValue3 = composerImpl3.rememberedValue();
                                                if (!zChangedInstance2) {
                                                    companion.getClass();
                                                    if (objRememberedValue3 == Composer.Companion.Empty) {
                                                        objRememberedValue3 = new Function0() { // from class: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt$SelectionHandle$1$1$2$1
                                                            {
                                                                super(0);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                return Boolean.valueOf((offsetProvider4.mo198provideF1C5BW0() & 9223372034707292159L) != 9205357640488583168L);
                                                            }
                                                        };
                                                        composerImpl3.updateRememberedValue(objRememberedValue3);
                                                    }
                                                    AndroidSelectionHandles_androidKt.SelectionHandleIcon(0, composerImpl3, modifier3, (Function0) objRememberedValue3, z5);
                                                    composerImpl3.end(false);
                                                }
                                            }
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        } else {
                                            composerImpl3.skipToGroupEnd();
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composerImpl2), composerImpl2, 56);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            } else {
                                composerImpl2.skipToGroupEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl), composerImpl, i4 | 384);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    j2 = j3;
                }
            } else {
                composerImpl.skipToGroupEnd();
                if ((i2 & 16) != 0) {
                    i3 &= -57345;
                }
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                if (z) {
                }
                AbsoluteAlignment.INSTANCE.getClass();
                if (!z3) {
                }
                int i42 = i3 & 14;
                if (i42 != 4) {
                    zChanged = ((i3 & 112) == 32) | (i42 != 4 || ((i3 & 8) != 0 && composerImpl.changedInstance(offsetProvider))) | composerImpl.changed(z3);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (zChanged) {
                    }
                }
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final long j4 = j2;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt$SelectionHandle$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    AndroidSelectionHandles_androidKt.m233SelectionHandlewLIcFTc(offsetProvider, z, resolvedTextDirection, z2, j4, f, modifier, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SelectionHandleIcon(final int i, Composer composer, final Modifier modifier, final Function0 function0, final boolean z) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2111672474);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(z) ? 256 : 128;
        }
        if (composerImpl.shouldExecute(i2 & 1, (i2 & 147) != 146)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.text.selection.SelectionHandleIcon (AndroidSelectionHandles.android.kt:127)");
            }
            SpacerKt.Spacer(composerImpl, ComposedModifierKt.composed(SizeKt.m141sizeVpY3zN4(modifier, SelectionHandlesKt.HandleWidth, SelectionHandlesKt.HandleHeight), InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt$drawSelectionHandle$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                /* JADX WARN: Removed duplicated region for block: B:9:0x004b  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Modifier modifier2 = (Modifier) obj;
                    ((Number) obj3).intValue();
                    ComposerImpl composerImpl2 = (ComposerImpl) ((Composer) obj2);
                    composerImpl2.startReplaceGroup(-196777734);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.foundation.text.selection.drawSelectionHandle.<anonymous> (AndroidSelectionHandles.android.kt:133)");
                    }
                    final long j = ((TextSelectionColors) composerImpl2.consume(TextSelectionColorsKt.LocalTextSelectionColors)).handleColor;
                    boolean zChanged = composerImpl2.changed(j) | composerImpl2.changed(function0) | composerImpl2.changed(z);
                    final Function0 function02 = function0;
                    final boolean z2 = z;
                    Object objRememberedValue = composerImpl2.rememberedValue();
                    if (!zChanged) {
                        Composer.Companion.getClass();
                        if (objRememberedValue == Composer.Companion.Empty) {
                            objRememberedValue = new Function1() { // from class: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt$drawSelectionHandle$1$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj4) {
                                    CacheDrawScope cacheDrawScope = (CacheDrawScope) obj4;
                                    final ImageBitmap imageBitmapCreateHandleImage = AndroidSelectionHandles_androidKt.createHandleImage(cacheDrawScope, Float.intBitsToFloat((int) (cacheDrawScope.cacheParams.mo361getSizeNHjbRc() >> 32)) / 2.0f);
                                    final BlendModeColorFilter blendModeColorFilterM465tintxETnrds$default = ColorFilter.Companion.m465tintxETnrds$default(ColorFilter.Companion, j);
                                    final Function0 function03 = function02;
                                    final boolean z3 = z2;
                                    return cacheDrawScope.onDrawWithContent(new Function1() { // from class: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt$drawSelectionHandle$1$1$1.1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj5) {
                                            LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj5);
                                            layoutNodeDrawScope.drawContent();
                                            if (((Boolean) function03.invoke()).booleanValue()) {
                                                if (z3) {
                                                    ImageBitmap imageBitmap = imageBitmapCreateHandleImage;
                                                    ColorFilter colorFilter = blendModeColorFilterM465tintxETnrds$default;
                                                    CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                                                    long jMo546getCenterF1C5BW0 = canvasDrawScope.mo546getCenterF1C5BW0();
                                                    CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
                                                    long jM528getSizeNHjbRc = canvasDrawScope$drawContext$1.m528getSizeNHjbRc();
                                                    canvasDrawScope$drawContext$1.getCanvas().save();
                                                    try {
                                                        canvasDrawScope$drawContext$1.transform.m532scale0AR0LA0(-1.0f, 1.0f, jMo546getCenterF1C5BW0);
                                                        DrawScope.m536drawImagegbVJVH8$default(layoutNodeDrawScope, imageBitmap, colorFilter);
                                                    } finally {
                                                        BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, jM528getSizeNHjbRc);
                                                    }
                                                } else {
                                                    DrawScope.m536drawImagegbVJVH8$default(layoutNodeDrawScope, imageBitmapCreateHandleImage, blendModeColorFilterM465tintxETnrds$default);
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    });
                                }
                            };
                            composerImpl2.updateRememberedValue(objRememberedValue);
                        }
                    }
                    Modifier modifierDrawWithCache = DrawModifierKt.drawWithCache(modifier2, (Function1) objRememberedValue);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl2.end(false);
                    return modifierDrawWithCache;
                }
            }));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt.SelectionHandleIcon.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    Modifier modifier2 = modifier;
                    Function0 function02 = function0;
                    boolean z2 = z;
                    AndroidSelectionHandles_androidKt.SelectionHandleIcon(RecomposeScopeImplKt.updateChangedFlags(i | 1), (Composer) obj, modifier2, function02, z2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final ImageBitmap createHandleImage(CacheDrawScope cacheDrawScope, float f) {
        int iCeil = ((int) Math.ceil(f)) * 2;
        HandleImageCache.INSTANCE.getClass();
        AndroidImageBitmap androidImageBitmapM481ImageBitmapx__hDU$default = HandleImageCache.imageBitmap;
        AndroidCanvas androidCanvasCanvas = HandleImageCache.canvas;
        CanvasDrawScope canvasDrawScope = HandleImageCache.canvasDrawScope;
        if (androidImageBitmapM481ImageBitmapx__hDU$default == null || androidCanvasCanvas == null || iCeil > androidImageBitmapM481ImageBitmapx__hDU$default.bitmap.getWidth() || iCeil > androidImageBitmapM481ImageBitmapx__hDU$default.bitmap.getHeight()) {
            ImageBitmapConfig.Companion.getClass();
            androidImageBitmapM481ImageBitmapx__hDU$default = ImageBitmapKt.m481ImageBitmapx__hDU$default(iCeil, iCeil, ImageBitmapConfig.Alpha8);
            HandleImageCache.imageBitmap = androidImageBitmapM481ImageBitmapx__hDU$default;
            androidCanvasCanvas = CanvasKt.Canvas(androidImageBitmapM481ImageBitmapx__hDU$default);
            HandleImageCache.canvas = androidCanvasCanvas;
        }
        AndroidImageBitmap androidImageBitmap = androidImageBitmapM481ImageBitmapx__hDU$default;
        AndroidCanvas androidCanvas = androidCanvasCanvas;
        if (canvasDrawScope == null) {
            canvasDrawScope = new CanvasDrawScope();
            HandleImageCache.canvasDrawScope = canvasDrawScope;
        }
        CanvasDrawScope canvasDrawScope2 = canvasDrawScope;
        LayoutDirection layoutDirection = cacheDrawScope.cacheParams.getLayoutDirection();
        float width = androidImageBitmap.bitmap.getWidth();
        float height = androidImageBitmap.bitmap.getHeight();
        Size.Companion companion = Size.Companion;
        CanvasDrawScope.DrawParams drawParams = canvasDrawScope2.drawParams;
        Density density = drawParams.density;
        LayoutDirection layoutDirection2 = drawParams.layoutDirection;
        Canvas canvas = drawParams.canvas;
        long j = drawParams.size;
        drawParams.density = cacheDrawScope;
        drawParams.layoutDirection = layoutDirection;
        drawParams.canvas = androidCanvas;
        drawParams.size = (Float.floatToRawIntBits(width) << 32) | (Float.floatToRawIntBits(height) & 4294967295L);
        androidCanvas.save();
        Color.Companion.getClass();
        long j2 = Color.Black;
        long jMo547getSizeNHjbRc = canvasDrawScope2.mo547getSizeNHjbRc();
        BlendMode.Companion.getClass();
        DrawScope.m541drawRectnJ9OG0$default(canvasDrawScope2, j2, 0L, jMo547getSizeNHjbRc, 0.0f, null, null, 0, 58);
        long jColor = ColorKt.Color(4278190080L);
        Offset.Companion.getClass();
        DrawScope.m541drawRectnJ9OG0$default(canvasDrawScope2, jColor, 0L, (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f) & 4294967295L), 0.0f, null, null, 0, 120);
        DrawScope.m534drawCircleVaOC9Bg$default(canvasDrawScope2, ColorKt.Color(4278190080L), f, (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f) & 4294967295L), 0.0f, null, 0, 120);
        androidCanvas.restore();
        drawParams.density = density;
        drawParams.layoutDirection = layoutDirection2;
        drawParams.canvas = canvas;
        drawParams.size = j;
        return androidImageBitmap;
    }
}
