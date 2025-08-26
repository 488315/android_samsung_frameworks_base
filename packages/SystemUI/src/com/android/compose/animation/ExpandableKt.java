package com.android.compose.animation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewParent;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderKt;
import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.InteractiveComponentSizeKt;
import androidx.compose.material3.MinimumInteractiveModifier;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MovableContentKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.GraphicsLayerScopeKt;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.OutlineKt;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.PathOperation;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.graphics.layer.GraphicsLayerKt;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.OnGloballyPositionedModifierKt;
import androidx.compose.ui.layout.OnPlacedModifierKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.ComposeView;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpSize;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;
import com.android.compose.modifiers.AnimatedBackgroundKt;
import com.android.compose.modifiers.AnimatedBackgroundKt$$ExternalSyntheticLambda0;
import com.android.compose.ui.graphics.DrawInOverlayKt;
import com.android.systemui.R;
import com.android.systemui.animation.ComposableControllerFactory;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class ExpandableKt {
    /* JADX WARN: Removed duplicated region for block: B:51:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01a3  */
    /* renamed from: AnimatedContentInOverlay-CISuavA, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m911AnimatedContentInOverlayCISuavA(final Function0 function0, final long j, final ViewGroupOverlay viewGroupOverlay, final ExpandableControllerImpl expandableControllerImpl, final Function3 function3, final View view, final Function1 function1, final Density density, Composer composer, final int i) {
        Composer.Companion companion;
        boolean z;
        Object obj;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2070928462);
        int i2 = i | (composerImpl.changedInstance(function0) ? 4 : 2) | (composerImpl.changed(j) ? 32 : 16) | (composerImpl.changedInstance(viewGroupOverlay) ? 256 : 128) | (composerImpl.changed(expandableControllerImpl) ? 2048 : 1024) | (composerImpl.changedInstance(function3) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192) | (composerImpl.changedInstance(view) ? 131072 : 65536) | (composerImpl.changedInstance(function1) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) | (composerImpl.changed(density) ? 8388608 : 4194304);
        if ((4793491 & i2) == 4793490 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.animation.AnimatedContentInOverlay (Expandable.kt:503)");
            }
            ComposerImpl.CompositionContextImpl compositionContextImplRememberCompositionContext = ComposablesKt.rememberCompositionContext(composerImpl);
            Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl.startReplaceGroup(-788610044);
            boolean zChanged = ((i2 & 29360128) == 8388608) | composerImpl.changed(context);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion2 = Composer.Companion;
            if (!zChanged) {
                companion2.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    final float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
                    companion = companion2;
                    final float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
                    Modifier.Companion companion3 = Modifier.Companion;
                    DpSize dpSizeM844boximpl = DpSize.m844boximpl(density.mo56toDpSizekrfVVM(j));
                    FillElement fillElement = SizeKt.FillWholeMaxWidth;
                    long j2 = dpSizeM844boximpl.packedValue;
                    final Modifier modifierDrawWithContent = DrawModifierKt.drawWithContent(SizeKt.m137requiredSizeVpY3zN4(companion3, DpSize.m847getWidthD9Ej5fM(j2), DpSize.m846getHeightD9Ej5fM(j2)), new Function1() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda15
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            ContentDrawScope contentDrawScope = (ContentDrawScope) obj2;
                            if (((TransitionAnimator.State) ((SnapshotMutableStateImpl) expandableControllerImpl.animatorState$delegate).getValue()) == null) {
                                return Unit.INSTANCE;
                            }
                            float f = fIntBitsToFloat;
                            float width = f == 0.0f ? 1.0f : r0.getWidth() / f;
                            float f2 = fIntBitsToFloat2;
                            float fMin = Math.min(width, f2 != 0.0f ? r0.getHeight() / f2 : 1.0f);
                            LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) contentDrawScope;
                            long jMo546getCenterF1C5BW0 = layoutNodeDrawScope.canvasDrawScope.mo546getCenterF1C5BW0();
                            CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = layoutNodeDrawScope.canvasDrawScope.drawContext;
                            long jM528getSizeNHjbRc = canvasDrawScope$drawContext$1.m528getSizeNHjbRc();
                            canvasDrawScope$drawContext$1.getCanvas().save();
                            try {
                                canvasDrawScope$drawContext$1.transform.m532scale0AR0LA0(fMin, fMin, jMo546getCenterF1C5BW0);
                                layoutNodeDrawScope.drawContent();
                                BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, jM528getSizeNHjbRc);
                                return Unit.INSTANCE;
                            } catch (Throwable th) {
                                BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, jM528getSizeNHjbRc);
                                throw th;
                            }
                        }
                    });
                    ComposeView composeView = new ComposeView(context, null, 0, 6, null);
                    z = true;
                    composeView.setContent(new ComposableLambdaImpl(-838452482, true, new Function2() { // from class: com.android.compose.animation.ExpandableKt$AnimatedContentInOverlay$composeViewInOverlay$1$composeView$1$1
                        /* JADX WARN: Removed duplicated region for block: B:15:0x0054  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x001d  */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj2, Object obj3) {
                            Composer composer2 = (Composer) obj2;
                            if ((((Number) obj3).intValue() & 3) == 2) {
                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                if (composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.compose.animation.AnimatedContentInOverlay.<anonymous>.<anonymous>.<anonymous> (Expandable.kt:541)");
                                    }
                                    Modifier modifierFillMaxSize = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    composerImpl3.startReplaceGroup(1260092920);
                                    ExpandableControllerImpl expandableControllerImpl2 = expandableControllerImpl;
                                    boolean zChanged2 = composerImpl3.changed(expandableControllerImpl2);
                                    Function0 function02 = function0;
                                    boolean zChanged3 = zChanged2 | composerImpl3.changed(function02);
                                    Object objRememberedValue2 = composerImpl3.rememberedValue();
                                    if (!zChanged3) {
                                        Composer.Companion.getClass();
                                        if (objRememberedValue2 == Composer.Companion.Empty) {
                                            objRememberedValue2 = new ExpandableKt$$ExternalSyntheticLambda8(expandableControllerImpl2, function02, 1);
                                            composerImpl3.updateRememberedValue(objRememberedValue2);
                                        }
                                        composerImpl3.end(false);
                                        Modifier modifierDrawWithContent2 = DrawModifierKt.drawWithContent(modifierFillMaxSize, (Function1) objRememberedValue2);
                                        Alignment.Companion.getClass();
                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, modifierDrawWithContent2);
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
                                        Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                                        Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                                        Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                                        Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                                        Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                                        }
                                        Function2 function24 = ComposeUiNode.Companion.SetModifier;
                                        Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, function24);
                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                        int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl3, modifierDrawWithContent);
                                        composerImpl3.startReusableNode();
                                        if (composerImpl3.inserting) {
                                            composerImpl3.createNode(function03);
                                        } else {
                                            composerImpl3.useNode();
                                        }
                                        Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy2, function2);
                                        Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function23);
                                        }
                                        Updater.m337setimpl(composerImpl3, modifierMaterializeModifier2, function24);
                                        function3.invoke(expandableControllerImpl2.expandable, composerImpl3, 0);
                                        if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl3, true, true)) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }));
                    View view2 = new View(context);
                    viewGroupOverlay.add(view2);
                    ViewParent parent = view2.getParent();
                    while (parent.getParent() != null) {
                        parent = parent.getParent();
                    }
                    viewGroupOverlay.remove(view2);
                    ViewGroup viewGroup = (ViewGroup) parent;
                    viewGroup.setTag(R.id.view_tree_lifecycle_owner, ViewTreeLifecycleOwner.get(view));
                    viewGroup.setTag(R.id.view_tree_view_model_store_owner, ViewTreeViewModelStoreOwner.get(view));
                    viewGroup.setTag(R.id.view_tree_saved_state_registry_owner, ViewTreeSavedStateRegistryOwner.get(view));
                    composeView.setParentCompositionContext(compositionContextImplRememberCompositionContext);
                    composerImpl.updateRememberedValue(composeView);
                    obj = composeView;
                } else {
                    companion = companion2;
                    z = true;
                    obj = objRememberedValue;
                }
                final ComposeView composeView2 = (ComposeView) obj;
                composerImpl.end(false);
                composerImpl.startReplaceGroup(-788514525);
                boolean zChangedInstance = composerImpl.changedInstance(viewGroupOverlay) | composerImpl.changedInstance(composeView2) | ((i2 & 7168) == 2048 ? z : false) | ((3670016 & i2) == 1048576 ? z : false);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance) {
                    companion.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new Function1() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda16
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                final ViewGroupOverlay viewGroupOverlay2 = viewGroupOverlay;
                                final ComposeView composeView3 = composeView2;
                                viewGroupOverlay2.add(composeView3);
                                TransitionAnimator.State state = (TransitionAnimator.State) ((SnapshotMutableStateImpl) expandableControllerImpl.animatorState$delegate).getValue();
                                if (state == null) {
                                    throw new IllegalStateException("AnimatedContentInOverlay shouldn't be composed with null animatorState.");
                                }
                                ExpandableKt.measureAndLayoutComposeViewInOverlay(composeView3, state);
                                final Function1 function12 = function1;
                                function12.mo781invoke(composeView3);
                                return new DisposableEffectResult() { // from class: com.android.compose.animation.ExpandableKt$AnimatedContentInOverlay_CISuavA$lambda$42$lambda$41$$inlined$onDispose$1
                                    @Override // androidx.compose.runtime.DisposableEffectResult
                                    public final void dispose() {
                                        ComposeView composeView4 = composeView3;
                                        composeView4.disposeComposition();
                                        viewGroupOverlay2.remove(composeView4);
                                        function12.mo781invoke(null);
                                    }
                                };
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl.end(false);
                    int i3 = ComposeView.$r8$clinit;
                    EffectsKt.DisposableEffect(viewGroupOverlay, composeView2, (Function1) objRememberedValue2, composerImpl);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(j, viewGroupOverlay, expandableControllerImpl, function3, view, function1, density, i) { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda17
                public final /* synthetic */ long f$1;
                public final /* synthetic */ ViewGroupOverlay f$2;
                public final /* synthetic */ ExpandableControllerImpl f$3;
                public final /* synthetic */ Function3 f$4;
                public final /* synthetic */ View f$5;
                public final /* synthetic */ Function1 f$6;
                public final /* synthetic */ Density f$7;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Function1 function12 = this.f$6;
                    Density density2 = this.f$7;
                    ExpandableKt.m911AnimatedContentInOverlayCISuavA(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, function12, density2, (Composer) obj2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x02da  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0308  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0593  */
    /* JADX WARN: Removed duplicated region for block: B:233:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0133  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void Expandable(ExpandableControllerImpl expandableControllerImpl, final Modifier modifier, Function1 function1, MutableInteractionSource mutableInteractionSource, final boolean z, boolean z2, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i, final int i2) {
        int i3;
        Function1 function12;
        int i4;
        MutableInteractionSource mutableInteractionSource2;
        int i5;
        final boolean z3;
        ComposableControllerFactory composableControllerFactory;
        Modifier modifier2;
        Composer.Companion companion;
        Object obj;
        Modifier modifier3;
        boolean z4;
        Function1 function13;
        final Function1 function14;
        final MutableInteractionSource mutableInteractionSource3;
        ExpandableControllerImpl$expandable$1 expandableControllerImpl$expandable$1;
        boolean z5;
        Modifier modifierThen;
        Object obj2;
        Object obj3;
        Object obj4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        final ExpandableControllerImpl expandableControllerImpl2 = expandableControllerImpl;
        final int i6 = 0;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(480643257);
        if ((i & 6) == 0) {
            i3 = (composerImpl.changed(expandableControllerImpl2) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        int i7 = i2 & 4;
        if (i7 != 0) {
            i3 |= 384;
        } else {
            if ((i & 384) == 0) {
                function12 = function1;
                i3 |= composerImpl.changedInstance(function12) ? 256 : 128;
            }
            i4 = i2 & 8;
            if (i4 != 0) {
                if ((i & 3072) == 0) {
                    mutableInteractionSource2 = mutableInteractionSource;
                    i3 |= composerImpl.changed(mutableInteractionSource2) ? 2048 : 1024;
                }
                if ((i & 24576) == 0) {
                    i3 |= composerImpl.changed(z) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                i5 = i2 & 32;
                if (i5 != 0) {
                    i3 |= 196608;
                    z3 = z2;
                } else {
                    z3 = z2;
                    if ((i & 196608) == 0) {
                        i3 |= composerImpl.changed(z3) ? 131072 : 65536;
                    }
                }
                if ((i & 1572864) == 0) {
                    i3 |= composerImpl.changedInstance(composableLambdaImpl) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                }
                if ((i3 & 599187) == 599186 && composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    function14 = function12;
                    mutableInteractionSource3 = mutableInteractionSource2;
                    modifier2 = modifier;
                } else {
                    Function1 function15 = i7 == 0 ? null : function12;
                    if (i4 != 0) {
                        mutableInteractionSource2 = null;
                    }
                    if (i5 != 0) {
                        z3 = true;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.compose.animation.Expandable (Expandable.kt:202)");
                    }
                    composerImpl.startReplaceGroup(-1530902443);
                    composableControllerFactory = expandableControllerImpl2.transitionControllerFactory;
                    Composer.Companion companion2 = Composer.Companion;
                    if (composableControllerFactory != null) {
                        composerImpl.startReplaceGroup(-1530898760);
                        boolean zChanged = composerImpl.changed(expandableControllerImpl2);
                        Object objRememberedValue = composerImpl.rememberedValue();
                        if (!zChanged) {
                            companion2.getClass();
                            Object obj5 = objRememberedValue;
                            if (objRememberedValue == Composer.Companion.Empty) {
                                Function1 function16 = new Function1() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda1
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj6) {
                                        switch (i6) {
                                            case 0:
                                                final ExpandableControllerImpl expandableControllerImpl3 = expandableControllerImpl2;
                                                expandableControllerImpl3.transitionControllerFactory.expandable.setValue(expandableControllerImpl3.expandable);
                                                break;
                                            case 1:
                                                ((SnapshotMutableStateImpl) expandableControllerImpl2.currentComposeViewInOverlay$delegate).setValue((View) obj6);
                                                break;
                                            case 2:
                                                ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).setValue(LayoutCoordinatesKt.boundsInRoot((LayoutCoordinates) obj6));
                                                break;
                                            case 3:
                                                ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).setValue(LayoutCoordinatesKt.boundsInRoot((LayoutCoordinates) obj6));
                                                break;
                                            default:
                                                ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).setValue(LayoutCoordinatesKt.boundsInRoot((LayoutCoordinates) obj6));
                                                break;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl.updateRememberedValue(function16);
                                obj5 = function16;
                            }
                            composerImpl.end(false);
                            EffectsKt.DisposableEffect(composableControllerFactory, (Function1) obj5, composerImpl);
                        }
                    }
                    composerImpl.end(false);
                    composerImpl.startReplaceGroup(-1530882599);
                    AnimatedBackgroundKt$$ExternalSyntheticLambda0 animatedBackgroundKt$$ExternalSyntheticLambda0 = AnimatedBackgroundKt.DefaultAlpha;
                    BorderStroke borderStroke = expandableControllerImpl2.borderStroke;
                    MutableState mutableState = expandableControllerImpl2.isDialogShowing$delegate;
                    MutableState mutableState2 = expandableControllerImpl2.overlay$delegate;
                    State state = expandableControllerImpl2.isAnimating$delegate;
                    Shape shape = expandableControllerImpl2.shape;
                    Applier applier = composerImpl.applier;
                    final boolean z6 = z3;
                    ExpandableControllerImpl$expandable$1 expandableControllerImpl$expandable$12 = expandableControllerImpl2.expandable;
                    if (!z) {
                        composerImpl.startReplaceGroup(502950784);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.compose.animation.expandable (Expandable.kt:355)");
                        }
                        final GraphicsLayer graphicsLayerRememberGraphicsLayer = GraphicsLayerScopeKt.rememberGraphicsLayer(composerImpl);
                        boolean zBooleanValue = ((Boolean) state.getValue()).booleanValue();
                        composerImpl.startReplaceGroup(-1695033496);
                        if (zBooleanValue) {
                            ViewGroupOverlay viewGroupOverlay = (ViewGroupOverlay) ((SnapshotMutableStateImpl) mutableState2).getValue();
                            expandableControllerImpl$expandable$1 = expandableControllerImpl$expandable$12;
                            composerImpl.startReplaceGroup(-1695031084);
                            boolean zChanged2 = composerImpl.changed(expandableControllerImpl2) | composerImpl.changedInstance(graphicsLayerRememberGraphicsLayer);
                            Object objRememberedValue2 = composerImpl.rememberedValue();
                            if (!zChanged2) {
                                companion2.getClass();
                                if (objRememberedValue2 == Composer.Companion.Empty) {
                                    z5 = false;
                                    ExpandableKt$$ExternalSyntheticLambda8 expandableKt$$ExternalSyntheticLambda8 = new ExpandableKt$$ExternalSyntheticLambda8(expandableControllerImpl2, graphicsLayerRememberGraphicsLayer, false ? 1 : 0);
                                    composerImpl.updateRememberedValue(expandableKt$$ExternalSyntheticLambda8);
                                    obj4 = expandableKt$$ExternalSyntheticLambda8;
                                } else {
                                    z5 = false;
                                    obj4 = objRememberedValue2;
                                }
                                composerImpl.end(z5);
                                DrawInOverlayKt.FullScreenComposeViewInOverlay(viewGroupOverlay, (Function1) obj4, composerImpl, z5 ? 1 : 0, z5 ? 1 : 0);
                            }
                        } else {
                            expandableControllerImpl$expandable$1 = expandableControllerImpl$expandable$12;
                            z5 = false;
                        }
                        composerImpl.end(z5);
                        final boolean z7 = (zBooleanValue || ((Boolean) ((SnapshotMutableStateImpl) mutableState).getValue()).booleanValue()) ? false : true;
                        if (function15 != null) {
                            Modifier.Companion companion3 = Modifier.Companion;
                            StaticProvidableCompositionLocal staticProvidableCompositionLocal = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
                            MinimumInteractiveModifier minimumInteractiveModifier = MinimumInteractiveModifier.INSTANCE;
                            companion3.getClass();
                            modifierThen = modifier.then(minimumInteractiveModifier);
                        } else {
                            modifierThen = modifier;
                        }
                        if (z7) {
                            Modifier modifierM29borderziNgDLE = Modifier.Companion;
                            if (borderStroke != null) {
                                modifierM29borderziNgDLE = BorderKt.m29borderziNgDLE(modifierM29borderziNgDLE, borderStroke.width, borderStroke.brush, shape);
                            }
                            modifierThen = modifierThen.then(AnimatedBackgroundKt.animatedBackground(modifierM29borderziNgDLE.then(clickModifier(expandableControllerImpl2, function15, mutableInteractionSource2)), expandableControllerImpl2.color, animatedBackgroundKt$$ExternalSyntheticLambda0, shape));
                        }
                        composerImpl.startReplaceGroup(-1695014272);
                        boolean zChanged3 = composerImpl.changed(expandableControllerImpl2);
                        Object objRememberedValue3 = composerImpl.rememberedValue();
                        if (!zChanged3) {
                            companion2.getClass();
                            obj2 = objRememberedValue3;
                            if (objRememberedValue3 == Composer.Companion.Empty) {
                                final int i8 = 4;
                                Function1 function17 = new Function1() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda1
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj6) {
                                        switch (i8) {
                                            case 0:
                                                final ExpandableControllerImpl expandableControllerImpl3 = expandableControllerImpl2;
                                                expandableControllerImpl3.transitionControllerFactory.expandable.setValue(expandableControllerImpl3.expandable);
                                                break;
                                            case 1:
                                                ((SnapshotMutableStateImpl) expandableControllerImpl2.currentComposeViewInOverlay$delegate).setValue((View) obj6);
                                                break;
                                            case 2:
                                                ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).setValue(LayoutCoordinatesKt.boundsInRoot((LayoutCoordinates) obj6));
                                                break;
                                            case 3:
                                                ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).setValue(LayoutCoordinatesKt.boundsInRoot((LayoutCoordinates) obj6));
                                                break;
                                            default:
                                                ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).setValue(LayoutCoordinatesKt.boundsInRoot((LayoutCoordinates) obj6));
                                                break;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl.updateRememberedValue(function17);
                                obj2 = function17;
                            }
                        }
                        composerImpl.end(false);
                        Modifier modifierOnPlaced = OnPlacedModifierKt.onPlaced(modifierThen, (Function1) obj2);
                        composerImpl.startReplaceGroup(-1695011472);
                        boolean zChangedInstance = composerImpl.changedInstance(graphicsLayerRememberGraphicsLayer) | composerImpl.changed(z7);
                        Object objRememberedValue4 = composerImpl.rememberedValue();
                        if (!zChangedInstance) {
                            companion2.getClass();
                            obj3 = objRememberedValue4;
                            if (objRememberedValue4 == Composer.Companion.Empty) {
                                Function1 function18 = new Function1() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda10
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj6) {
                                        ContentDrawScope contentDrawScope = (ContentDrawScope) obj6;
                                        ExpandableKt$$ExternalSyntheticLambda11 expandableKt$$ExternalSyntheticLambda11 = new ExpandableKt$$ExternalSyntheticLambda11(contentDrawScope, 1);
                                        GraphicsLayer graphicsLayer = graphicsLayerRememberGraphicsLayer;
                                        ((LayoutNodeDrawScope) contentDrawScope).m648recordJVtK1S4(IntSizeKt.m865toIntSizeuvyYCjk(((LayoutNodeDrawScope) contentDrawScope).canvasDrawScope.mo547getSizeNHjbRc()), graphicsLayer, expandableKt$$ExternalSyntheticLambda11);
                                        if (z7) {
                                            GraphicsLayerKt.drawLayer(contentDrawScope, graphicsLayer);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl.updateRememberedValue(function18);
                                obj3 = function18;
                            }
                        }
                        composerImpl.end(false);
                        Modifier modifierDrawWithContent = DrawModifierKt.drawWithContent(modifierOnPlaced, (Function1) obj3);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        composerImpl.end(false);
                        Alignment.Companion.getClass();
                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierDrawWithContent);
                        ComposeUiNode.Companion.getClass();
                        Function0 function0 = ComposeUiNode.Companion.Constructor;
                        if (applier == null) {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                        composerImpl.startReusableNode();
                        if (composerImpl.inserting) {
                            composerImpl.createNode(function0);
                        } else {
                            composerImpl.useNode();
                        }
                        Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                        }
                        Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                        final Function1 function19 = function15;
                        m913WrappedContent3IgeMak(expandableControllerImpl$expandable$1, expandableControllerImpl2.contentColor, z6, composableLambdaImpl, composerImpl, (i3 >> 9) & 8064);
                        if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, true, false)) {
                            ComposerKt.traceEventEnd();
                        }
                        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup2 != null) {
                            final int i9 = 0;
                            final MutableInteractionSource mutableInteractionSource4 = mutableInteractionSource2;
                            recomposeScopeImplEndRestartGroup2.block = new Function2() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda2
                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj6, Object obj7) {
                                    switch (i9) {
                                        case 0:
                                            ((Integer) obj7).getClass();
                                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                            ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                                            ExpandableKt.Expandable(expandableControllerImpl2, modifier, function19, mutableInteractionSource4, z, z6, composableLambdaImpl2, (Composer) obj6, iUpdateChangedFlags, i2);
                                            break;
                                        default:
                                            ((Integer) obj7).getClass();
                                            int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                            ComposableLambdaImpl composableLambdaImpl3 = composableLambdaImpl;
                                            ExpandableKt.Expandable(expandableControllerImpl2, modifier, function19, mutableInteractionSource4, z, z6, composableLambdaImpl3, (Composer) obj6, iUpdateChangedFlags2, i2);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    Function1 function110 = function15;
                    MutableInteractionSource mutableInteractionSource5 = mutableInteractionSource2;
                    modifier2 = modifier;
                    composerImpl.end(false);
                    composerImpl.startReplaceGroup(-1530867123);
                    boolean z8 = (3670016 & i3) == 1048576;
                    Object objRememberedValue5 = composerImpl.rememberedValue();
                    if (!z8) {
                        companion2.getClass();
                        if (objRememberedValue5 == Composer.Companion.Empty) {
                            companion = companion2;
                            final long j = expandableControllerImpl2.contentColor;
                            ComposableLambdaImpl composableLambdaImplMovableContentOf = MovableContentKt.movableContentOf(new ComposableLambdaImpl(-2076638018, true, new Function3() { // from class: com.android.compose.animation.ExpandableKt$Expandable$wrappedContent$1$1
                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj6, Object obj7, Object obj8) {
                                    Expandable expandable = (Expandable) obj6;
                                    Composer composer2 = (Composer) obj7;
                                    int iIntValue = ((Number) obj8).intValue();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.compose.animation.Expandable.<anonymous>.<anonymous> (Expandable.kt:235)");
                                    }
                                    Function3 function3 = composableLambdaImpl;
                                    ExpandableKt.m913WrappedContent3IgeMak(expandable, j, z6, function3, composer2, iIntValue & 14);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    return Unit.INSTANCE;
                                }
                            }));
                            composerImpl.updateRememberedValue(composableLambdaImplMovableContentOf);
                            obj = composableLambdaImplMovableContentOf;
                        } else {
                            companion = companion2;
                            obj = objRememberedValue5;
                        }
                        Function3 function3 = (Function3) obj;
                        composerImpl.end(false);
                        composerImpl.startReplaceGroup(-1530859892);
                        Object objRememberedValue6 = composerImpl.rememberedValue();
                        companion.getClass();
                        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                        Object obj6 = objRememberedValue6;
                        if (objRememberedValue6 == composer$Companion$Empty$1) {
                            Size.Companion.getClass();
                            MutableState mutableStateMutableStateOf$default = SnapshotStateKt.mutableStateOf$default(Size.m415boximpl(0L));
                            composerImpl.updateRememberedValue(mutableStateMutableStateOf$default);
                            obj6 = mutableStateMutableStateOf$default;
                        }
                        MutableState mutableState3 = (MutableState) obj6;
                        composerImpl.end(false);
                        boolean zBooleanValue2 = ((Boolean) state.getValue()).booleanValue();
                        if (function110 != null) {
                            Modifier.Companion companion4 = Modifier.Companion;
                            StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
                            modifier3 = MinimumInteractiveModifier.INSTANCE;
                            companion4.getClass();
                        } else {
                            modifier3 = Modifier.Companion;
                        }
                        Modifier modifier4 = modifier3;
                        Function0 function02 = expandableControllerImpl2.color;
                        if (zBooleanValue2) {
                            composerImpl.startReplaceGroup(-210897263);
                            long jMo56toDpSizekrfVVM = expandableControllerImpl2.density.mo56toDpSizekrfVVM(((Size) mutableState3.getValue()).packedValue);
                            FillElement fillElement = SizeKt.FillWholeMaxWidth;
                            SpacerKt.Spacer(composerImpl, SizeKt.m137requiredSizeVpY3zN4(modifier2, DpSize.m847getWidthD9Ej5fM(jMo56toDpSizekrfVVM), DpSize.m846getHeightD9Ej5fM(jMo56toDpSizekrfVVM)));
                            long jM410getSizeNHjbRc = ((Rect) ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).getValue()).m410getSizeNHjbRc();
                            ViewGroupOverlay viewGroupOverlay2 = (ViewGroupOverlay) ((SnapshotMutableStateImpl) mutableState2).getValue();
                            if (viewGroupOverlay2 == null) {
                                throw new IllegalStateException("AnimatedContentInOverlay shouldn't be composed with null overlay.");
                            }
                            z4 = z6;
                            View view = expandableControllerImpl2.composeViewRoot;
                            composerImpl.startReplaceGroup(-1530797099);
                            boolean zChanged4 = composerImpl.changed(expandableControllerImpl2);
                            Object objRememberedValue7 = composerImpl.rememberedValue();
                            Object obj7 = objRememberedValue7;
                            if (zChanged4 || objRememberedValue7 == composer$Companion$Empty$1) {
                                final int i10 = 1;
                                Function1 function111 = new Function1() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda1
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj62) {
                                        switch (i10) {
                                            case 0:
                                                final ExpandableControllerImpl expandableControllerImpl3 = expandableControllerImpl2;
                                                expandableControllerImpl3.transitionControllerFactory.expandable.setValue(expandableControllerImpl3.expandable);
                                                break;
                                            case 1:
                                                ((SnapshotMutableStateImpl) expandableControllerImpl2.currentComposeViewInOverlay$delegate).setValue((View) obj62);
                                                break;
                                            case 2:
                                                ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).setValue(LayoutCoordinatesKt.boundsInRoot((LayoutCoordinates) obj62));
                                                break;
                                            case 3:
                                                ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).setValue(LayoutCoordinatesKt.boundsInRoot((LayoutCoordinates) obj62));
                                                break;
                                            default:
                                                ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).setValue(LayoutCoordinatesKt.boundsInRoot((LayoutCoordinates) obj62));
                                                break;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl.updateRememberedValue(function111);
                                obj7 = function111;
                            }
                            composerImpl.end(false);
                            m911AnimatedContentInOverlayCISuavA(function02, jM410getSizeNHjbRc, viewGroupOverlay2, expandableControllerImpl2, function3, view, (Function1) obj7, expandableControllerImpl2.density, composerImpl, 0);
                            expandableControllerImpl2 = expandableControllerImpl2;
                            composerImpl.end(false);
                            function13 = function110;
                            mutableInteractionSource5 = mutableInteractionSource5;
                        } else {
                            z4 = z6;
                            function13 = function110;
                            if (((Boolean) ((SnapshotMutableStateImpl) mutableState).getValue()).booleanValue()) {
                                composerImpl.startReplaceGroup(-209912145);
                                Modifier modifierThen2 = OnGloballyPositionedModifierKt.onGloballyPositioned(modifier2, new ExpandableKt$$ExternalSyntheticLambda11(mutableState3, 0)).then(modifier4);
                                composerImpl.startReplaceGroup(-1530786659);
                                Object objRememberedValue8 = composerImpl.rememberedValue();
                                Object obj8 = objRememberedValue8;
                                if (objRememberedValue8 == composer$Companion$Empty$1) {
                                    ExpandableKt$$ExternalSyntheticLambda4 expandableKt$$ExternalSyntheticLambda4 = new ExpandableKt$$ExternalSyntheticLambda4();
                                    composerImpl.updateRememberedValue(expandableKt$$ExternalSyntheticLambda4);
                                    obj8 = expandableKt$$ExternalSyntheticLambda4;
                                }
                                composerImpl.end(false);
                                Modifier modifierDrawWithContent2 = DrawModifierKt.drawWithContent(modifierThen2, (Function1) obj8);
                                composerImpl.startReplaceGroup(-1530783520);
                                boolean zChanged5 = composerImpl.changed(expandableControllerImpl2);
                                Object objRememberedValue9 = composerImpl.rememberedValue();
                                Object obj9 = objRememberedValue9;
                                if (zChanged5 || objRememberedValue9 == composer$Companion$Empty$1) {
                                    final int i11 = 2;
                                    Function1 function112 = new Function1() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda1
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj62) {
                                            switch (i11) {
                                                case 0:
                                                    final ExpandableControllerImpl expandableControllerImpl3 = expandableControllerImpl2;
                                                    expandableControllerImpl3.transitionControllerFactory.expandable.setValue(expandableControllerImpl3.expandable);
                                                    break;
                                                case 1:
                                                    ((SnapshotMutableStateImpl) expandableControllerImpl2.currentComposeViewInOverlay$delegate).setValue((View) obj62);
                                                    break;
                                                case 2:
                                                    ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).setValue(LayoutCoordinatesKt.boundsInRoot((LayoutCoordinates) obj62));
                                                    break;
                                                case 3:
                                                    ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).setValue(LayoutCoordinatesKt.boundsInRoot((LayoutCoordinates) obj62));
                                                    break;
                                                default:
                                                    ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).setValue(LayoutCoordinatesKt.boundsInRoot((LayoutCoordinates) obj62));
                                                    break;
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl.updateRememberedValue(function112);
                                    obj9 = function112;
                                }
                                composerImpl.end(false);
                                Modifier modifierOnGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned(modifierDrawWithContent2, (Function1) obj9);
                                Alignment.Companion.getClass();
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierOnGloballyPositioned);
                                ComposeUiNode.Companion.getClass();
                                Function0 function03 = ComposeUiNode.Companion.Constructor;
                                if (applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl.startReusableNode();
                                if (composerImpl.inserting) {
                                    composerImpl.createNode(function03);
                                } else {
                                    composerImpl.useNode();
                                }
                                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function22);
                                }
                                Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, ComposeUiNode.Companion.SetModifier);
                                BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
                                function3.invoke(expandableControllerImpl$expandable$12, composerImpl, 0);
                                composerImpl.end(true);
                                composerImpl.end(false);
                            } else {
                                composerImpl.startReplaceGroup(-209466923);
                                Modifier modifierAnimatedBackground = AnimatedBackgroundKt.animatedBackground(OnGloballyPositionedModifierKt.onGloballyPositioned(modifier2, new ExpandableKt$$ExternalSyntheticLambda11(mutableState3, 0)).then(modifier4).then(clickModifier(expandableControllerImpl2, function13, mutableInteractionSource5)), function02, animatedBackgroundKt$$ExternalSyntheticLambda0, shape);
                                if (borderStroke != null) {
                                    modifierAnimatedBackground = BorderKt.m29borderziNgDLE(modifierAnimatedBackground, borderStroke.width, borderStroke.brush, shape);
                                }
                                composerImpl.startReplaceGroup(-1530766368);
                                boolean zChanged6 = composerImpl.changed(expandableControllerImpl2);
                                Object objRememberedValue10 = composerImpl.rememberedValue();
                                Object obj10 = objRememberedValue10;
                                if (zChanged6 || objRememberedValue10 == composer$Companion$Empty$1) {
                                    final int i12 = 3;
                                    Function1 function113 = new Function1() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda1
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj62) {
                                            switch (i12) {
                                                case 0:
                                                    final ExpandableControllerImpl expandableControllerImpl3 = expandableControllerImpl2;
                                                    expandableControllerImpl3.transitionControllerFactory.expandable.setValue(expandableControllerImpl3.expandable);
                                                    break;
                                                case 1:
                                                    ((SnapshotMutableStateImpl) expandableControllerImpl2.currentComposeViewInOverlay$delegate).setValue((View) obj62);
                                                    break;
                                                case 2:
                                                    ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).setValue(LayoutCoordinatesKt.boundsInRoot((LayoutCoordinates) obj62));
                                                    break;
                                                case 3:
                                                    ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).setValue(LayoutCoordinatesKt.boundsInRoot((LayoutCoordinates) obj62));
                                                    break;
                                                default:
                                                    ((SnapshotMutableStateImpl) expandableControllerImpl2.boundsInComposeViewRoot$delegate).setValue(LayoutCoordinatesKt.boundsInRoot((LayoutCoordinates) obj62));
                                                    break;
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl.updateRememberedValue(function113);
                                    obj10 = function113;
                                }
                                composerImpl.end(false);
                                Modifier modifierOnGloballyPositioned2 = OnGloballyPositionedModifierKt.onGloballyPositioned(modifierAnimatedBackground, (Function1) obj10);
                                Alignment.Companion.getClass();
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy3 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierOnGloballyPositioned2);
                                ComposeUiNode.Companion.getClass();
                                Function0 function04 = ComposeUiNode.Companion.Constructor;
                                if (applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl.startReusableNode();
                                if (composerImpl.inserting) {
                                    composerImpl.createNode(function04);
                                } else {
                                    composerImpl.useNode();
                                }
                                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy3, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
                                }
                                Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, ComposeUiNode.Companion.SetModifier);
                                BoxScopeInstance boxScopeInstance3 = BoxScopeInstance.INSTANCE;
                                function3.invoke(expandableControllerImpl$expandable$12, composerImpl, 0);
                                composerImpl.end(true);
                                composerImpl.end(false);
                            }
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        function14 = function13;
                        mutableInteractionSource3 = mutableInteractionSource5;
                        z3 = z4;
                    }
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final int i13 = 1;
                    final Modifier modifier5 = modifier2;
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj62, Object obj72) {
                            switch (i13) {
                                case 0:
                                    ((Integer) obj72).getClass();
                                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                    ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                                    ExpandableKt.Expandable(expandableControllerImpl2, modifier5, function14, mutableInteractionSource3, z, z3, composableLambdaImpl2, (Composer) obj62, iUpdateChangedFlags, i2);
                                    break;
                                default:
                                    ((Integer) obj72).getClass();
                                    int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                    ComposableLambdaImpl composableLambdaImpl3 = composableLambdaImpl;
                                    ExpandableKt.Expandable(expandableControllerImpl2, modifier5, function14, mutableInteractionSource3, z, z3, composableLambdaImpl3, (Composer) obj62, iUpdateChangedFlags2, i2);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i3 |= 3072;
            mutableInteractionSource2 = mutableInteractionSource;
            if ((i & 24576) == 0) {
            }
            i5 = i2 & 32;
            if (i5 != 0) {
            }
            if ((i & 1572864) == 0) {
            }
            if ((i3 & 599187) == 599186) {
                if (i7 == 0) {
                }
                if (i4 != 0) {
                }
                if (i5 != 0) {
                }
                if (ComposerKt.isTraceInProgress()) {
                }
                composerImpl.startReplaceGroup(-1530902443);
                composableControllerFactory = expandableControllerImpl2.transitionControllerFactory;
                Composer.Companion companion22 = Composer.Companion;
                if (composableControllerFactory != null) {
                }
                composerImpl.end(false);
                composerImpl.startReplaceGroup(-1530882599);
                AnimatedBackgroundKt$$ExternalSyntheticLambda0 animatedBackgroundKt$$ExternalSyntheticLambda02 = AnimatedBackgroundKt.DefaultAlpha;
                BorderStroke borderStroke2 = expandableControllerImpl2.borderStroke;
                MutableState mutableState4 = expandableControllerImpl2.isDialogShowing$delegate;
                MutableState mutableState22 = expandableControllerImpl2.overlay$delegate;
                State state2 = expandableControllerImpl2.isAnimating$delegate;
                Shape shape2 = expandableControllerImpl2.shape;
                Applier applier2 = composerImpl.applier;
                final boolean z62 = z3;
                ExpandableControllerImpl$expandable$1 expandableControllerImpl$expandable$122 = expandableControllerImpl2.expandable;
                if (!z) {
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        function12 = function1;
        i4 = i2 & 8;
        if (i4 != 0) {
        }
        mutableInteractionSource2 = mutableInteractionSource;
        if ((i & 24576) == 0) {
        }
        i5 = i2 & 32;
        if (i5 != 0) {
        }
        if ((i & 1572864) == 0) {
        }
        if ((i3 & 599187) == 599186) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0259  */
    /* JADX WARN: Removed duplicated region for block: B:163:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00ff  */
    /* renamed from: Expandable-S04cQl8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m912ExpandableS04cQl8(final long j, final RoundedCornerShape roundedCornerShape, final Modifier modifier, long j2, BorderStroke borderStroke, final Function1 function1, MutableInteractionSource mutableInteractionSource, boolean z, boolean z2, ComposableControllerFactory composableControllerFactory, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i, final int i2) {
        int i3;
        final long jM259contentColorForek8zF_U;
        BorderStroke borderStroke2;
        int i4;
        MutableInteractionSource mutableInteractionSource2;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        boolean z3;
        BorderStroke borderStroke3;
        boolean z4;
        MutableInteractionSource mutableInteractionSource3;
        ComposableControllerFactory composableControllerFactory2;
        int i13;
        final boolean z5;
        final boolean z6;
        ComposerImpl composerImpl;
        final BorderStroke borderStroke4;
        final ComposableControllerFactory composableControllerFactory3;
        final MutableInteractionSource mutableInteractionSource4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1287084083);
        if ((i & 6) == 0) {
            i3 = (composerImpl2.changed(j) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl2.changed(roundedCornerShape) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl2.changed(modifier) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            if ((i2 & 8) == 0) {
                jM259contentColorForek8zF_U = j2;
                int i14 = composerImpl2.changed(jM259contentColorForek8zF_U) ? 2048 : 1024;
                i3 |= i14;
            } else {
                jM259contentColorForek8zF_U = j2;
            }
            i3 |= i14;
        } else {
            jM259contentColorForek8zF_U = j2;
        }
        int i15 = i2 & 16;
        if (i15 != 0) {
            i3 |= 24576;
        } else {
            if ((i & 24576) == 0) {
                borderStroke2 = borderStroke;
                i3 |= composerImpl2.changed(borderStroke2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
            }
            if ((196608 & i) == 0) {
                i3 |= composerImpl2.changedInstance(function1) ? 131072 : 65536;
            }
            i4 = i2 & 64;
            if (i4 == 0) {
                i3 |= 1572864;
            } else {
                if ((i & 1572864) == 0) {
                    mutableInteractionSource2 = mutableInteractionSource;
                    i3 |= composerImpl2.changed(mutableInteractionSource2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                }
                i5 = i2 & 128;
                if (i5 != 0) {
                    i3 |= 12582912;
                } else {
                    if ((i & 12582912) == 0) {
                        i6 = i3 | (composerImpl2.changed(z) ? 8388608 : 4194304);
                    }
                    i7 = i2 & 256;
                    if (i7 != 0) {
                        if ((i & 100663296) == 0) {
                            i8 = i7;
                            i6 |= composerImpl2.changed(z2) ? 67108864 : 33554432;
                        }
                        i9 = i2 & 512;
                        if (i9 != 0) {
                            i6 |= 805306368;
                            i10 = i9;
                        } else if ((i & 805306368) == 0) {
                            i10 = i9;
                            i6 |= composerImpl2.changedInstance(composableControllerFactory) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                        } else {
                            i10 = i9;
                        }
                        i11 = i6;
                        if ((i11 & 306783379) == 306783378 && composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            z5 = z;
                            composableControllerFactory3 = composableControllerFactory;
                            borderStroke4 = borderStroke2;
                            composerImpl = composerImpl2;
                            mutableInteractionSource4 = mutableInteractionSource2;
                            z6 = z2;
                        } else {
                            composerImpl2.startDefaults();
                            boolean z7 = true;
                            if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                if ((i2 & 8) != 0) {
                                    jM259contentColorForek8zF_U = ColorSchemeKt.m259contentColorForek8zF_U(j, composerImpl2);
                                    i11 &= -7169;
                                }
                                if (i15 != 0) {
                                    borderStroke2 = null;
                                }
                                if (i4 != 0) {
                                    mutableInteractionSource2 = null;
                                }
                                boolean z8 = i5 == 0 ? false : z;
                                boolean z9 = i8 == 0 ? true : z2;
                                i12 = i11;
                                z3 = z8;
                                borderStroke3 = borderStroke2;
                                if (i10 == 0) {
                                    z4 = z9;
                                    mutableInteractionSource3 = mutableInteractionSource2;
                                    composableControllerFactory2 = null;
                                    composerImpl2.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.compose.animation.Expandable (Expandable.kt:147)");
                                    }
                                    int i16 = i12 >> 3;
                                    i13 = (i12 & 126) | (i16 & 896) | (i16 & 7168) | ((i12 >> 15) & 57344);
                                    composerImpl2.startReplaceGroup(88731891);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.compose.animation.rememberExpandableController (ExpandableController.kt:80)");
                                    }
                                    composerImpl2.startReplaceGroup(1184917240);
                                    if ((((i13 & 14) ^ 6) > 4 || !composerImpl2.changed(j)) && (i13 & 6) != 4) {
                                        z7 = false;
                                    }
                                    Object objRememberedValue = composerImpl2.rememberedValue();
                                    if (!z7) {
                                        Composer.Companion.getClass();
                                        if (objRememberedValue == Composer.Companion.Empty) {
                                            objRememberedValue = new Function0() { // from class: com.android.compose.animation.ExpandableControllerKt$rememberExpandableController$1$1
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return Color.m456boximpl(j);
                                                }
                                            };
                                            composerImpl2.updateRememberedValue(objRememberedValue);
                                        }
                                        composerImpl2.end(false);
                                        MutableInteractionSource mutableInteractionSource5 = mutableInteractionSource3;
                                        ExpandableControllerImpl expandableControllerImplM910rememberExpandableControllerT042LqI = ExpandableControllerKt.m910rememberExpandableControllerT042LqI((Function0) objRememberedValue, roundedCornerShape, jM259contentColorForek8zF_U, borderStroke3, composableControllerFactory2, composerImpl2, i13 & 65520, 0);
                                        long j3 = jM259contentColorForek8zF_U;
                                        BorderStroke borderStroke5 = borderStroke3;
                                        ComposableControllerFactory composableControllerFactory4 = composableControllerFactory2;
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl2.end(false);
                                        int i17 = i12 >> 9;
                                        boolean z10 = z3;
                                        boolean z11 = z4;
                                        Expandable(expandableControllerImplM910rememberExpandableControllerT042LqI, modifier, function1, mutableInteractionSource5, z10, z11, composableLambdaImpl, composerImpl2, (i16 & 112) | (i17 & 896) | (i17 & 7168) | (i17 & 57344) | (i17 & 458752) | 1572864, 0);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        z5 = z10;
                                        z6 = z11;
                                        composerImpl = composerImpl2;
                                        borderStroke4 = borderStroke5;
                                        composableControllerFactory3 = composableControllerFactory4;
                                        mutableInteractionSource4 = mutableInteractionSource5;
                                        jM259contentColorForek8zF_U = j3;
                                    }
                                } else {
                                    z4 = z9;
                                }
                            } else {
                                composerImpl2.skipToGroupEnd();
                                if ((i2 & 8) != 0) {
                                    i11 &= -7169;
                                }
                                z3 = z;
                                z4 = z2;
                                i12 = i11;
                                borderStroke3 = borderStroke2;
                            }
                            mutableInteractionSource3 = mutableInteractionSource2;
                            composableControllerFactory2 = composableControllerFactory;
                            composerImpl2.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            int i162 = i12 >> 3;
                            i13 = (i12 & 126) | (i162 & 896) | (i162 & 7168) | ((i12 >> 15) & 57344);
                            composerImpl2.startReplaceGroup(88731891);
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            composerImpl2.startReplaceGroup(1184917240);
                            if (((i13 & 14) ^ 6) > 4) {
                                z7 = false;
                                Object objRememberedValue2 = composerImpl2.rememberedValue();
                                if (!z7) {
                                }
                            } else {
                                z7 = false;
                                Object objRememberedValue22 = composerImpl2.rememberedValue();
                                if (!z7) {
                                }
                            }
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    ((Integer) obj2).getClass();
                                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                    ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                                    int i18 = i2;
                                    ExpandableKt.m912ExpandableS04cQl8(j, roundedCornerShape, modifier, jM259contentColorForek8zF_U, borderStroke4, function1, mutableInteractionSource4, z5, z6, composableControllerFactory3, composableLambdaImpl2, (Composer) obj, iUpdateChangedFlags, i18);
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    i6 |= 100663296;
                    i8 = i7;
                    i9 = i2 & 512;
                    if (i9 != 0) {
                    }
                    i11 = i6;
                    if ((i11 & 306783379) == 306783378) {
                        composerImpl2.startDefaults();
                        boolean z72 = true;
                        if ((i & 1) != 0) {
                            if ((i2 & 8) != 0) {
                            }
                            if (i15 != 0) {
                            }
                            if (i4 != 0) {
                            }
                            if (i5 == 0) {
                            }
                            if (i8 == 0) {
                            }
                            i12 = i11;
                            z3 = z8;
                            borderStroke3 = borderStroke2;
                            if (i10 == 0) {
                            }
                        }
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                i6 = i3;
                i7 = i2 & 256;
                if (i7 != 0) {
                }
                i8 = i7;
                i9 = i2 & 512;
                if (i9 != 0) {
                }
                i11 = i6;
                if ((i11 & 306783379) == 306783378) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            mutableInteractionSource2 = mutableInteractionSource;
            i5 = i2 & 128;
            if (i5 != 0) {
            }
            i6 = i3;
            i7 = i2 & 256;
            if (i7 != 0) {
            }
            i8 = i7;
            i9 = i2 & 512;
            if (i9 != 0) {
            }
            i11 = i6;
            if ((i11 & 306783379) == 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        borderStroke2 = borderStroke;
        if ((196608 & i) == 0) {
        }
        i4 = i2 & 64;
        if (i4 == 0) {
        }
        mutableInteractionSource2 = mutableInteractionSource;
        i5 = i2 & 128;
        if (i5 != 0) {
        }
        i6 = i3;
        i7 = i2 & 256;
        if (i7 != 0) {
        }
        i8 = i7;
        i9 = i2 & 512;
        if (i9 != 0) {
        }
        i11 = i6;
        if ((i11 & 306783379) == 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* renamed from: WrappedContent-3IgeMak, reason: not valid java name */
    public static final void m913WrappedContent3IgeMak(final Expandable expandable, final long j, final boolean z, final Function3 function3, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-91621291);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(expandable) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(j) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(z) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function3) ? 2048 : 1024;
        }
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.animation.WrappedContent (Expandable.kt:322)");
            }
            ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-242023019, new Function2() { // from class: com.android.compose.animation.ExpandableKt$WrappedContent$minSizeContent$1
                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.compose.animation.WrappedContent.<anonymous> (Expandable.kt:325)");
                            }
                            boolean z2 = z;
                            Expandable expandable2 = expandable;
                            Function3 function32 = function3;
                            if (z2) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                composerImpl3.startReplaceGroup(-605032501);
                                float f = 40;
                                Dp.Companion companion = Dp.Companion;
                                Modifier modifierM130defaultMinSizeVpY3zN4 = SizeKt.m130defaultMinSizeVpY3zN4(Modifier.Companion, f, f);
                                Alignment.Companion.getClass();
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, modifierM130defaultMinSizeVpY3zN4);
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
                                Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                                }
                                Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                function32.invoke(expandable2, composerImpl3, 0);
                                composerImpl3.end(true);
                                composerImpl3.end(false);
                            } else {
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                composerImpl4.startReplaceGroup(-604423382);
                                function32.invoke(expandable2, composerImpl4, 0);
                                composerImpl4.end(false);
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl);
            if (j != 16) {
                composerImpl.startReplaceGroup(-1328291087);
                CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(j)), composableLambdaImplRememberComposableLambda, composerImpl, 56);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-1328181316);
                composableLambdaImplRememberComposableLambda.invoke((Object) composerImpl, (Object) 6);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda14
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    ExpandableKt.m913WrappedContent3IgeMak(expandable, j, z, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final Modifier clickModifier(final ExpandableControllerImpl expandableControllerImpl, final Function1 function1, MutableInteractionSource mutableInteractionSource) {
        if (function1 == null) {
            return Modifier.Companion;
        }
        if (mutableInteractionSource != null) {
            final int i = 0;
            return ClickableKt.m34clickableO2vRcR0$default(Modifier.Companion, mutableInteractionSource, null, false, null, null, new Function0() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda12
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i) {
                        case 0:
                            function1.mo781invoke(expandableControllerImpl.expandable);
                            break;
                        default:
                            function1.mo781invoke(expandableControllerImpl.expandable);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            }, 28);
        }
        final int i2 = 1;
        return ClickableKt.m35clickableXHw0xAI$default(ClipKt.clip(Modifier.Companion, expandableControllerImpl.shape), false, null, new Function0() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        function1.mo781invoke(expandableControllerImpl.expandable);
                        break;
                    default:
                        function1.mo781invoke(expandableControllerImpl.expandable);
                        break;
                }
                return Unit.INSTANCE;
            }
        }, 7);
    }

    /* renamed from: drawBackground-HilfTbk, reason: not valid java name */
    public static final void m914drawBackgroundHilfTbk(ContentDrawScope contentDrawScope, TransitionAnimator.State state, long j, BorderStroke borderStroke, long j2) {
        float f = state.topCornerRadius;
        float f2 = state.bottomCornerRadius;
        if (f != f2) {
            LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) contentDrawScope;
            Outline outlineMo41createOutlinePq9zytI = RoundedCornerShapeKt.RoundedCornerShape(f, f, f2, f2).mo41createOutlinePq9zytI(j2, layoutNodeDrawScope.getLayoutDirection(), layoutNodeDrawScope);
            OutlineKt.m492drawOutlinewDX37Ww$default(layoutNodeDrawScope, outlineMo41createOutlinePq9zytI, j, 0.0f, null, 60);
            if (borderStroke != null) {
                float fMo58toPx0680j_4 = layoutNodeDrawScope.mo58toPx0680j_4(borderStroke.width);
                AndroidPath androidPathPath = AndroidPath_androidKt.Path();
                RoundRect roundRect = ((Outline.Rounded) outlineMo41createOutlinePq9zytI).roundRect;
                Path.addRoundRect$default(androidPathPath, roundRect);
                Path Path = AndroidPath_androidKt.Path();
                Path.addRoundRect$default(Path, new RoundRect(fMo58toPx0680j_4, fMo58toPx0680j_4, roundRect.getWidth() - fMo58toPx0680j_4, roundRect.getHeight() - fMo58toPx0680j_4, m915shrinkKibmq7A(fMo58toPx0680j_4, roundRect.topLeftCornerRadius), m915shrinkKibmq7A(fMo58toPx0680j_4, roundRect.topRightCornerRadius), m915shrinkKibmq7A(fMo58toPx0680j_4, roundRect.bottomRightCornerRadius), m915shrinkKibmq7A(fMo58toPx0680j_4, roundRect.bottomLeftCornerRadius), null));
                PathOperation.Companion.getClass();
                androidPathPath.m445opN5in7k0(androidPathPath, Path, 0);
                DrawScope.m538drawPathGBMwjPU$default(layoutNodeDrawScope, androidPathPath, borderStroke.brush, 0.0f, null, 60);
                return;
            }
            return;
        }
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f) & 4294967295L);
        CornerRadius.Companion companion = CornerRadius.Companion;
        DrawScope.m543drawRoundRectuAw5IA$default(contentDrawScope, j, 0L, j2, jFloatToRawIntBits, null, 0.0f, IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState);
        if (borderStroke != null) {
            LayoutNodeDrawScope layoutNodeDrawScope2 = (LayoutNodeDrawScope) contentDrawScope;
            float fMo58toPx0680j_42 = layoutNodeDrawScope2.mo58toPx0680j_4(borderStroke.width);
            Stroke stroke = new Stroke(fMo58toPx0680j_42, 0.0f, 0, 0, null, 30, null);
            long jFloatToRawIntBits2 = (Float.floatToRawIntBits(r3) << 32) | (Float.floatToRawIntBits(r3) & 4294967295L);
            Offset.Companion companion2 = Offset.Companion;
            float fIntBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32)) - fMo58toPx0680j_42;
            float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j2 & 4294967295L)) - fMo58toPx0680j_42;
            long jFloatToRawIntBits3 = (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L);
            Size.Companion companion3 = Size.Companion;
            DrawScope.m542drawRoundRectZuiqVtQ$default(layoutNodeDrawScope2, borderStroke.brush, jFloatToRawIntBits2, jFloatToRawIntBits3, m915shrinkKibmq7A(fMo58toPx0680j_42 / 2, jFloatToRawIntBits), 0.0f, stroke, 208);
        }
    }

    public static final void measureAndLayoutComposeViewInOverlay(View view, TransitionAnimator.State state) {
        view.measure(View.MeasureSpec.makeSafeMeasureSpec(state.getWidth(), 1073741824), View.MeasureSpec.makeSafeMeasureSpec(state.getHeight(), 1073741824));
        int[] locationOnScreen = ((ViewGroup) view.getParent()).getLocationOnScreen();
        int i = locationOnScreen[0];
        int i2 = locationOnScreen[1];
        view.layout(state.left - i, state.top - i2, state.right - i, state.bottom - i2);
    }

    /* renamed from: shrink-Kibmq7A, reason: not valid java name */
    public static final long m915shrinkKibmq7A(float f, long j) {
        float fMax = Math.max(0.0f, Float.intBitsToFloat((int) (j >> 32)) - f);
        float fMax2 = Math.max(0.0f, Float.intBitsToFloat((int) (j & 4294967295L)) - f);
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fMax) << 32) | (Float.floatToRawIntBits(fMax2) & 4294967295L);
        CornerRadius.Companion companion = CornerRadius.Companion;
        return jFloatToRawIntBits;
    }
}
