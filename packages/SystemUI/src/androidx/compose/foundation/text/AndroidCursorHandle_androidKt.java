package androidx.compose.foundation.text;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt;
import androidx.compose.foundation.text.selection.OffsetProvider;
import androidx.compose.foundation.text.selection.SelectionHandleAnchor;
import androidx.compose.foundation.text.selection.SelectionHandleInfo;
import androidx.compose.foundation.text.selection.SelectionHandlesKt;
import androidx.compose.foundation.text.selection.TextSelectionColors;
import androidx.compose.foundation.text.selection.TextSelectionColorsKt;
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
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.CacheDrawScope;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.BlendModeColorFilter;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScopeKt$asDrawTransform$1;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpSize;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class AndroidCursorHandle_androidKt {
    public static final float CursorHandleHeight;
    public static final float CursorHandleWidth;

    static {
        float f = 25;
        Dp.Companion companion = Dp.Companion;
        CursorHandleHeight = f;
        CursorHandleWidth = (f * 2.0f) / 2.4142137f;
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00b8  */
    /* renamed from: CursorHandle-USBMPiE, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m190CursorHandleUSBMPiE(final OffsetProvider offsetProvider, final Modifier modifier, final long j, Composer composer, final int i, final int i2) throws Throwable {
        int i3;
        int i4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1776202187);
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
            i3 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= ((i2 & 4) == 0 && composerImpl.changed(j)) ? 256 : 128;
        }
        boolean z = true;
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 147) != 146)) {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if ((i2 & 4) != 0) {
                    DpSize.Companion.getClass();
                    j = DpSize.Unspecified;
                    i3 &= -897;
                }
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.foundation.text.CursorHandle (AndroidCursorHandle.android.kt:51)");
                }
                i4 = i3 & 14;
                if (i4 != 4 && ((i3 & 8) == 0 || !composerImpl.changedInstance(offsetProvider))) {
                    z = false;
                }
                Object objRememberedValue = composerImpl.rememberedValue();
                if (z) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function1() { // from class: androidx.compose.foundation.text.AndroidCursorHandle_androidKt$CursorHandle$finalModifier$1$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                ((SemanticsConfiguration) ((SemanticsPropertyReceiver) obj)).set(SelectionHandlesKt.SelectionHandleInfoKey, new SelectionHandleInfo(Handle.Cursor, offsetProvider.mo198provideF1C5BW0(), SelectionHandleAnchor.Middle, true, null));
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    final Modifier modifierSemantics = SemanticsModifierKt.semantics(modifier, false, (Function1) objRememberedValue);
                    Alignment.Companion.getClass();
                    AndroidSelectionHandles_androidKt.HandlePopup(offsetProvider, Alignment.Companion.TopCenter, ComposableLambdaKt.rememberComposableLambda(-1653527038, new Function2() { // from class: androidx.compose.foundation.text.AndroidCursorHandle_androidKt$CursorHandle$1
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
                                    ComposerKt.traceEventStart("androidx.compose.foundation.text.CursorHandle.<anonymous> (AndroidCursorHandle.android.kt:63)");
                                }
                                if (j != 9205357640488583168L) {
                                    composerImpl2.startReplaceGroup(1828931592);
                                    Modifier modifierM138requiredSizeInqDBjuR0$default = SizeKt.m138requiredSizeInqDBjuR0$default(modifierSemantics, DpSize.m847getWidthD9Ej5fM(j), DpSize.m846getHeightD9Ej5fM(j), 0.0f, 0.0f, 12);
                                    Alignment.Companion.getClass();
                                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopCenter, false);
                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierM138requiredSizeInqDBjuR0$default);
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
                                    Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                    Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
                                    }
                                    Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                    AndroidCursorHandle_androidKt.access$DefaultCursorHandle(null, composerImpl2, 0, 1);
                                    composerImpl2.end(true);
                                    composerImpl2.end(false);
                                } else {
                                    composerImpl2.startReplaceGroup(1829298756);
                                    AndroidCursorHandle_androidKt.access$DefaultCursorHandle(modifierSemantics, composerImpl2, 0, 0);
                                    composerImpl2.end(false);
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            } else {
                                composerImpl2.skipToGroupEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl), composerImpl, i4 | 432);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            } else {
                composerImpl.skipToGroupEnd();
                if ((i2 & 4) != 0) {
                    i3 &= -897;
                }
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                i4 = i3 & 14;
                if (i4 != 4) {
                    z = false;
                }
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (z) {
                }
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        final long j2 = j;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.AndroidCursorHandle_androidKt$CursorHandle$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Throwable {
                    ((Number) obj2).intValue();
                    AndroidCursorHandle_androidKt.m190CursorHandleUSBMPiE(offsetProvider, modifier, j2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void access$DefaultCursorHandle(final Modifier modifier, Composer composer, final int i, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(694251107);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 3) != 2)) {
            if (i4 != 0) {
                modifier = Modifier.Companion;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.text.DefaultCursorHandle (AndroidCursorHandle.android.kt:82)");
            }
            SpacerKt.Spacer(composerImpl, ComposedModifierKt.composed(SizeKt.m141sizeVpY3zN4(modifier, CursorHandleWidth, CursorHandleHeight), InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.text.AndroidCursorHandle_androidKt$drawCursorHandle$1
                /* JADX WARN: Removed duplicated region for block: B:9:0x003b  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Modifier modifier2 = (Modifier) obj;
                    ((Number) obj3).intValue();
                    ComposerImpl composerImpl2 = (ComposerImpl) ((Composer) obj2);
                    composerImpl2.startReplaceGroup(-2126899193);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.foundation.text.drawCursorHandle.<anonymous> (AndroidCursorHandle.android.kt:87)");
                    }
                    final long j = ((TextSelectionColors) composerImpl2.consume(TextSelectionColorsKt.LocalTextSelectionColors)).handleColor;
                    Modifier.Companion companion = Modifier.Companion;
                    boolean zChanged = composerImpl2.changed(j);
                    Object objRememberedValue = composerImpl2.rememberedValue();
                    if (!zChanged) {
                        Composer.Companion.getClass();
                        if (objRememberedValue == Composer.Companion.Empty) {
                            objRememberedValue = new Function1() { // from class: androidx.compose.foundation.text.AndroidCursorHandle_androidKt$drawCursorHandle$1$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj4) {
                                    CacheDrawScope cacheDrawScope = (CacheDrawScope) obj4;
                                    final float fIntBitsToFloat = Float.intBitsToFloat((int) (cacheDrawScope.cacheParams.mo361getSizeNHjbRc() >> 32)) / 2.0f;
                                    final ImageBitmap imageBitmapCreateHandleImage = AndroidSelectionHandles_androidKt.createHandleImage(cacheDrawScope, fIntBitsToFloat);
                                    final BlendModeColorFilter blendModeColorFilterM465tintxETnrds$default = ColorFilter.Companion.m465tintxETnrds$default(ColorFilter.Companion, j);
                                    return cacheDrawScope.onDrawWithContent(new Function1() { // from class: androidx.compose.foundation.text.AndroidCursorHandle_androidKt$drawCursorHandle$1$1$1.1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj5) {
                                            LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj5);
                                            layoutNodeDrawScope.drawContent();
                                            float f = fIntBitsToFloat;
                                            ImageBitmap imageBitmap = imageBitmapCreateHandleImage;
                                            ColorFilter colorFilter = blendModeColorFilterM465tintxETnrds$default;
                                            CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = layoutNodeDrawScope.canvasDrawScope.drawContext;
                                            long jM528getSizeNHjbRc = canvasDrawScope$drawContext$1.m528getSizeNHjbRc();
                                            canvasDrawScope$drawContext$1.getCanvas().save();
                                            try {
                                                CanvasDrawScopeKt$asDrawTransform$1 canvasDrawScopeKt$asDrawTransform$1 = canvasDrawScope$drawContext$1.transform;
                                                canvasDrawScopeKt$asDrawTransform$1.translate(f, 0.0f);
                                                Offset.Companion.getClass();
                                                canvasDrawScopeKt$asDrawTransform$1.m531rotateUv8p0NA(45.0f, 0L);
                                                DrawScope.m536drawImagegbVJVH8$default(layoutNodeDrawScope, imageBitmap, colorFilter);
                                                BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, jM528getSizeNHjbRc);
                                                return Unit.INSTANCE;
                                            } catch (Throwable th) {
                                                BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, jM528getSizeNHjbRc);
                                                throw th;
                                            }
                                        }
                                    });
                                }
                            };
                            composerImpl2.updateRememberedValue(objRememberedValue);
                        }
                    }
                    Modifier modifierThen = modifier2.then(DrawModifierKt.drawWithCache(companion, (Function1) objRememberedValue));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl2.end(false);
                    return modifierThen;
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
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.AndroidCursorHandle_androidKt$DefaultCursorHandle$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    AndroidCursorHandle_androidKt.access$DefaultCursorHandle(modifier, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
