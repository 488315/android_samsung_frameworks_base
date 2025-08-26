package com.android.systemui.qs.footer.ui.compose;

import android.content.Context;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.Indication;
import androidx.compose.foundation.IndicationKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.CornerSizeKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.compose.animation.ExpandableKt;
import com.android.compose.modifiers.AnimatedBackgroundKt;
import com.android.compose.theme.ColorKt;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.compose.IconKt;
import com.android.systemui.compose.modifiers.SysuiTestTagKt;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsButtonViewModel;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsForegroundServicesButtonViewModel;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsSecurityButtonViewModel;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel;
import com.android.systemui.qs.ui.compose.BorderOnFocusKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;

/* loaded from: classes2.dex */
public abstract class FooterActionsKt {
    public static final void FooterActions(FooterActionsViewModel footerActionsViewModel, LifecycleOwner lifecycleOwner, Modifier.Companion companion, Composer composer, final int i) {
        final Modifier.Companion companion2;
        Object footerActionsKt$FooterActions$1$1;
        MutableState mutableState;
        Object[] objArr;
        Throwable th;
        MutableState mutableState2;
        boolean z;
        final LifecycleOwner lifecycleOwner2;
        MutableState mutableState3;
        MutableState mutableState4;
        final FooterActionsViewModel footerActionsViewModel2 = footerActionsViewModel;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(548205006);
        if ((((composerImpl.changedInstance(footerActionsViewModel2) ? 4 : 2) | i | (composerImpl.changedInstance(lifecycleOwner) ? 32 : 16) | 384) & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
            lifecycleOwner2 = lifecycleOwner;
        } else {
            companion2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.footer.ui.compose.FooterActions (FooterActions.kt:133)");
            }
            Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            Object objCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(footerActionsViewModel2.alpha, composerImpl);
            final MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(footerActionsViewModel2.backgroundAlpha, composerImpl);
            composerImpl.startReplaceGroup(1467762618);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (objRememberedValue == obj) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            MutableState mutableState5 = (MutableState) objRememberedValue;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1467765936);
            if (objM == obj) {
                objM = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(objM);
            }
            MutableState mutableState6 = (MutableState) objM;
            Object objM2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1467769714);
            if (objM2 == obj) {
                objM2 = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(objM2);
            }
            MutableState mutableState7 = (MutableState) objM2;
            Object objM3 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1467772327);
            if (objM3 == obj) {
                objM3 = SnapshotStateKt.mutableStateOf$default(footerActionsViewModel2.initialPower.invoke());
                composerImpl.updateRememberedValue(objM3);
            }
            MutableState mutableState8 = (MutableState) objM3;
            composerImpl.end(false);
            Object[] objArr2 = {context, lifecycleOwner, footerActionsViewModel2, footerActionsViewModel2.security, footerActionsViewModel2.foregroundServices, footerActionsViewModel2.userSwitcher};
            composerImpl.startReplaceGroup(1467780949);
            boolean zChangedInstance = composerImpl.changedInstance(footerActionsViewModel2) | composerImpl.changedInstance(context) | composerImpl.changedInstance(lifecycleOwner);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue2 == obj) {
                mutableState = mutableState6;
                objArr = objArr2;
                th = null;
                mutableState2 = mutableState7;
                z = false;
                footerActionsKt$FooterActions$1$1 = new FooterActionsKt$FooterActions$1$1(lifecycleOwner, footerActionsViewModel2, context, mutableState5, mutableState, mutableState2, mutableState8, null);
                lifecycleOwner2 = lifecycleOwner;
                mutableState3 = mutableState8;
                mutableState4 = mutableState5;
                composerImpl.updateRememberedValue(footerActionsKt$FooterActions$1$1);
            } else {
                objArr = objArr2;
                footerActionsKt$FooterActions$1$1 = objRememberedValue2;
                mutableState4 = mutableState5;
                mutableState = mutableState6;
                th = null;
                mutableState2 = mutableState7;
                z = false;
                lifecycleOwner2 = lifecycleOwner;
                mutableState3 = mutableState8;
            }
            composerImpl.end(z);
            EffectsKt.LaunchedEffect(objArr, (Function2) footerActionsKt$FooterActions$1$1, composerImpl);
            composerImpl.startReplaceGroup(1467802117);
            final MutableState mutableState9 = mutableState3;
            final long jColorAttr = ColorKt.colorAttr(R.attr.underSurface, composerImpl);
            composerImpl.end(z);
            composerImpl.startReplaceGroup(1467805780);
            PropertyReference0Impl propertyReference0Impl = new PropertyReference0Impl(mutableStateCollectAsStateWithLifecycle) { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$backgroundAlphaValue$1
                @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                public final Object get() {
                    return ((State) this.receiver).getValue();
                }
            };
            composerImpl.end(z);
            MaterialTheme.INSTANCE.getClass();
            final MutableState mutableState10 = mutableState4;
            long j = MaterialTheme.getColorScheme(composerImpl).onSurface;
            float fDimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_corner_radius, composerImpl);
            composerImpl.startReplaceGroup(1467813606);
            boolean zChanged = composerImpl.changed(jColorAttr) | composerImpl.changed(propertyReference0Impl) | composerImpl.changed(fDimensionResource);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (zChanged || objRememberedValue3 == obj) {
                objRememberedValue3 = AnimatedBackgroundKt.animatedBackground(companion2, new Function0() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$backgroundModifier$1$1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Color.m456boximpl(jColorAttr);
                    }
                }, propertyReference0Impl, RoundedCornerShapeKt.m189RoundedCornerShapea9UjIt4$default(fDimensionResource, fDimensionResource, 0.0f, 0.0f, 12));
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
            Modifier modifier = (Modifier) objRememberedValue3;
            composerImpl.end(false);
            float fDimensionResource2 = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_content_horizontal_padding, composerImpl);
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion2, 1.0f);
            composerImpl.startReplaceGroup(1467828552);
            boolean zChanged2 = composerImpl.changed(objCollectAsStateWithLifecycle);
            Object objRememberedValue4 = composerImpl.rememberedValue();
            if (zChanged2 || objRememberedValue4 == obj) {
                objRememberedValue4 = new FooterActionsKt$$ExternalSyntheticLambda0(objCollectAsStateWithLifecycle, 2);
                composerImpl.updateRememberedValue(objRememberedValue4);
            }
            composerImpl.end(false);
            Modifier modifierM128paddingqDBjuR0 = PaddingKt.m128paddingqDBjuR0(GraphicsLayerModifierKt.graphicsLayer(modifierFillMaxWidth, (Function1) objRememberedValue4).then(modifier), fDimensionResource2, PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_footer_actions_top_padding, composerImpl), fDimensionResource2, PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_footer_actions_bottom_padding, composerImpl));
            composerImpl.startReplaceGroup(1467841357);
            Object objRememberedValue5 = composerImpl.rememberedValue();
            if (objRememberedValue5 == obj) {
                objRememberedValue5 = new FooterActionsKt$$ExternalSyntheticLambda4();
                composerImpl.updateRememberedValue(objRememberedValue5);
            }
            composerImpl.end(false);
            Modifier modifierLayout = LayoutModifierKt.layout(modifierM128paddingqDBjuR0, (Function3) objRememberedValue5);
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            Arrangement.INSTANCE.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl, 48);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierLayout);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw th;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            final RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            footerActionsViewModel2 = footerActionsViewModel;
            final MutableState mutableState11 = mutableState;
            final MutableState mutableState12 = mutableState2;
            CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(j)), ComposableLambdaKt.rememberComposableLambda(-2122823950, new Function2() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$4$1
                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
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
                                ComposerKt.traceEventStart("com.android.systemui.qs.footer.ui.compose.FooterActions.<anonymous>.<anonymous> (FooterActions.kt:217)");
                            }
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(1849424287);
                            final MutableState mutableState13 = mutableState10;
                            FooterActionsSecurityButtonViewModel footerActionsSecurityButtonViewModel = (FooterActionsSecurityButtonViewModel) mutableState13.getValue();
                            RowScope rowScope = rowScopeInstance;
                            final MutableState mutableState14 = mutableState11;
                            if (footerActionsSecurityButtonViewModel == null && ((FooterActionsForegroundServicesButtonViewModel) mutableState14.getValue()) == null) {
                                SpacerKt.Spacer(composerImpl3, rowScope.weight(Modifier.Companion, 1.0f, true));
                            }
                            Object objM4 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl3, false, 1849429272);
                            Composer.Companion.getClass();
                            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                            if (objM4 == composer$Companion$Empty$1) {
                                objM4 = Boolean.FALSE;
                                composerImpl3.updateRememberedValue(objM4);
                            }
                            boolean zBooleanValue = ((Boolean) objM4).booleanValue();
                            composerImpl3.end(false);
                            composerImpl3.startReplaceGroup(1849431420);
                            Object objRememberedValue6 = composerImpl3.rememberedValue();
                            if (objRememberedValue6 == composer$Companion$Empty$1) {
                                final int i2 = 0;
                                objRememberedValue6 = new Function0() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$4$1$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        switch (i2) {
                                            case 0:
                                                return (FooterActionsSecurityButtonViewModel) ((MutableState) mutableState13).getValue();
                                            case 1:
                                                return (FooterActionsForegroundServicesButtonViewModel) ((MutableState) mutableState13).getValue();
                                            case 2:
                                                return (FooterActionsButtonViewModel) ((MutableState) mutableState13).getValue();
                                            case 3:
                                                return (FooterActionsButtonViewModel) ((MutableState) mutableState13).getValue();
                                            default:
                                                return ((FooterActionsViewModel) mutableState13).settings;
                                        }
                                    }
                                };
                                composerImpl3.updateRememberedValue(objRememberedValue6);
                            }
                            composerImpl3.end(false);
                            Modifier.Companion companion3 = Modifier.Companion;
                            FooterActionsKt.SecurityButton(54, composerImpl3, rowScope.weight(companion3, 1.0f, true), (Function0) objRememberedValue6, zBooleanValue);
                            composerImpl3.startReplaceGroup(1849434630);
                            Object objRememberedValue7 = composerImpl3.rememberedValue();
                            if (objRememberedValue7 == composer$Companion$Empty$1) {
                                final int i3 = 1;
                                objRememberedValue7 = new Function0() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$4$1$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        switch (i3) {
                                            case 0:
                                                return (FooterActionsSecurityButtonViewModel) ((MutableState) mutableState14).getValue();
                                            case 1:
                                                return (FooterActionsForegroundServicesButtonViewModel) ((MutableState) mutableState14).getValue();
                                            case 2:
                                                return (FooterActionsButtonViewModel) ((MutableState) mutableState14).getValue();
                                            case 3:
                                                return (FooterActionsButtonViewModel) ((MutableState) mutableState14).getValue();
                                            default:
                                                return ((FooterActionsViewModel) mutableState14).settings;
                                        }
                                    }
                                };
                                composerImpl3.updateRememberedValue(objRememberedValue7);
                            }
                            composerImpl3.end(false);
                            FooterActionsKt.ForegroundServicesButton(rowScope, (Function0) objRememberedValue7, zBooleanValue, composerImpl3, 432);
                            composerImpl3.startReplaceGroup(1849437568);
                            Object objRememberedValue8 = composerImpl3.rememberedValue();
                            if (objRememberedValue8 == composer$Companion$Empty$1) {
                                final MutableState mutableState15 = mutableState12;
                                final int i4 = 2;
                                objRememberedValue8 = new Function0() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$4$1$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        switch (i4) {
                                            case 0:
                                                return (FooterActionsSecurityButtonViewModel) ((MutableState) mutableState15).getValue();
                                            case 1:
                                                return (FooterActionsForegroundServicesButtonViewModel) ((MutableState) mutableState15).getValue();
                                            case 2:
                                                return (FooterActionsButtonViewModel) ((MutableState) mutableState15).getValue();
                                            case 3:
                                                return (FooterActionsButtonViewModel) ((MutableState) mutableState15).getValue();
                                            default:
                                                return ((FooterActionsViewModel) mutableState15).settings;
                                        }
                                    }
                                };
                                composerImpl3.updateRememberedValue(objRememberedValue8);
                            }
                            composerImpl3.end(false);
                            FooterActionsKt.IconButton(438, composerImpl3, SysuiTestTagKt.sysuiResTag(companion3, "multi_user_switch"), (Function0) objRememberedValue8, zBooleanValue);
                            composerImpl3.startReplaceGroup(1849443174);
                            final FooterActionsViewModel footerActionsViewModel3 = footerActionsViewModel2;
                            boolean zChangedInstance2 = composerImpl3.changedInstance(footerActionsViewModel3);
                            Object objRememberedValue9 = composerImpl3.rememberedValue();
                            if (zChangedInstance2 || objRememberedValue9 == composer$Companion$Empty$1) {
                                final int i5 = 4;
                                objRememberedValue9 = new Function0() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$4$1$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        switch (i5) {
                                            case 0:
                                                return (FooterActionsSecurityButtonViewModel) ((MutableState) footerActionsViewModel3).getValue();
                                            case 1:
                                                return (FooterActionsForegroundServicesButtonViewModel) ((MutableState) footerActionsViewModel3).getValue();
                                            case 2:
                                                return (FooterActionsButtonViewModel) ((MutableState) footerActionsViewModel3).getValue();
                                            case 3:
                                                return (FooterActionsButtonViewModel) ((MutableState) footerActionsViewModel3).getValue();
                                            default:
                                                return ((FooterActionsViewModel) footerActionsViewModel3).settings;
                                        }
                                    }
                                };
                                composerImpl3.updateRememberedValue(objRememberedValue9);
                            }
                            composerImpl3.end(false);
                            FooterActionsKt.IconButton(432, composerImpl3, SysuiTestTagKt.sysuiResTag(companion3, "settings_button_container"), (Function0) objRememberedValue9, zBooleanValue);
                            composerImpl3.startReplaceGroup(1849448665);
                            Object objRememberedValue10 = composerImpl3.rememberedValue();
                            if (objRememberedValue10 == composer$Companion$Empty$1) {
                                final MutableState mutableState16 = mutableState9;
                                final int i6 = 3;
                                objRememberedValue10 = new Function0() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$4$1$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        switch (i6) {
                                            case 0:
                                                return (FooterActionsSecurityButtonViewModel) ((MutableState) mutableState16).getValue();
                                            case 1:
                                                return (FooterActionsForegroundServicesButtonViewModel) ((MutableState) mutableState16).getValue();
                                            case 2:
                                                return (FooterActionsButtonViewModel) ((MutableState) mutableState16).getValue();
                                            case 3:
                                                return (FooterActionsButtonViewModel) ((MutableState) mutableState16).getValue();
                                            default:
                                                return ((FooterActionsViewModel) mutableState16).settings;
                                        }
                                    }
                                };
                                composerImpl3.updateRememberedValue(objRememberedValue10);
                            }
                            composerImpl3.end(false);
                            FooterActionsKt.IconButton(438, composerImpl3, SysuiTestTagKt.sysuiResTag(companion3, "pm_lite"), (Function0) objRememberedValue10, zBooleanValue);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(lifecycleOwner2, companion2, i) { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda5
                public final /* synthetic */ LifecycleOwner f$1;
                public final /* synthetic */ Modifier.Companion f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    LifecycleOwner lifecycleOwner3 = this.f$1;
                    Modifier.Companion companion3 = this.f$2;
                    FooterActionsKt.FooterActions(this.f$0, lifecycleOwner3, companion3, (Composer) obj2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ForegroundServicesButton(final RowScope rowScope, final Function0 function0, final boolean z, Composer composer, final int i) {
        final boolean z2;
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1097782003);
        if ((((composerImpl.changed(rowScope) ? 4 : 2) | i) & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            z2 = z;
            i2 = i;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.footer.ui.compose.ForegroundServicesButton (FooterActions.kt:268)");
            }
            FooterActionsForegroundServicesButtonViewModel footerActionsForegroundServicesButtonViewModel = (FooterActionsForegroundServicesButtonViewModel) function0.invoke();
            if (footerActionsForegroundServicesButtonViewModel == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final int i3 = 0;
                    recomposeScopeImplEndRestartGroup.block = new Function2(rowScope, function0, z, i, i3) { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda7
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ RowScope f$0;
                        public final /* synthetic */ Function0 f$1;
                        public final /* synthetic */ boolean f$2;

                        {
                            this.$r8$classId = i3;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            int i4 = this.$r8$classId;
                            Composer composer2 = (Composer) obj;
                            ((Integer) obj2).getClass();
                            switch (i4) {
                                case 0:
                                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(433);
                                    FooterActionsKt.ForegroundServicesButton(this.f$0, this.f$1, this.f$2, composer2, iUpdateChangedFlags);
                                    break;
                                default:
                                    int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(433);
                                    FooterActionsKt.ForegroundServicesButton(this.f$0, this.f$1, this.f$2, composer2, iUpdateChangedFlags2);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            z2 = z;
            i2 = i;
            if (footerActionsForegroundServicesButtonViewModel.displayText) {
                composerImpl.startReplaceGroup(-1725755180);
                TextButton(new Icon.Resource(R.drawable.ic_info_outline, null), footerActionsForegroundServicesButtonViewModel.text, footerActionsForegroundServicesButtonViewModel.hasNewChanges, footerActionsForegroundServicesButtonViewModel.onClick, z2, rowScope.weight(Modifier.Companion, 1.0f, true), composerImpl, 24576);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-1725454139);
                NumberButton(footerActionsForegroundServicesButtonViewModel.foregroundServicesCount, footerActionsForegroundServicesButtonViewModel.text, footerActionsForegroundServicesButtonViewModel.hasNewChanges, footerActionsForegroundServicesButtonViewModel.onClick, z2, null, composerImpl, 24576);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            final int i4 = 1;
            final int i5 = i2;
            recomposeScopeImplEndRestartGroup2.block = new Function2(rowScope, function0, z2, i5, i4) { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda7
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ RowScope f$0;
                public final /* synthetic */ Function0 f$1;
                public final /* synthetic */ boolean f$2;

                {
                    this.$r8$classId = i4;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    int i42 = this.$r8$classId;
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    switch (i42) {
                        case 0:
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(433);
                            FooterActionsKt.ForegroundServicesButton(this.f$0, this.f$1, this.f$2, composer2, iUpdateChangedFlags);
                            break;
                        default:
                            int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(433);
                            FooterActionsKt.ForegroundServicesButton(this.f$0, this.f$1, this.f$2, composer2, iUpdateChangedFlags2);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void IconButton(int i, Composer composer, Modifier modifier, Function0 function0, boolean z) {
        int i2;
        int i3;
        Modifier modifier2;
        Function0 function02;
        boolean z2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1683403180);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            i3 = i;
            modifier2 = modifier;
            function02 = function0;
            z2 = z;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.footer.ui.compose.IconButton (FooterActions.kt:296)");
            }
            FooterActionsButtonViewModel footerActionsButtonViewModel = (FooterActionsButtonViewModel) function0.invoke();
            if (footerActionsButtonViewModel == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new FooterActionsKt$$ExternalSyntheticLambda12(function0, z, modifier, i, 0);
                    return;
                }
                return;
            }
            i3 = i;
            modifier2 = modifier;
            function02 = function0;
            z2 = z;
            IconButton(footerActionsButtonViewModel, z2, modifier2, composerImpl, 432);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            int i4 = i3;
            recomposeScopeImplEndRestartGroup2.block = new FooterActionsKt$$ExternalSyntheticLambda12(function02, z2, modifier2, i4, 1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0091  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void NewChangesDot(final Modifier modifier, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1339346955);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.footer.ui.compose.NewChangesDot (FooterActions.kt:382)");
            }
            String strStringResource = StringResources_androidKt.stringResource(R.string.fgs_dot_content_description, composerImpl);
            MaterialTheme.INSTANCE.getClass();
            final long j = MaterialTheme.getColorScheme(composerImpl).tertiary;
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(modifier, 12);
            composerImpl.startReplaceGroup(2035887558);
            boolean zChanged = composerImpl.changed(strStringResource);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion2 = Composer.Companion;
            if (!zChanged) {
                companion2.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new FooterActionsKt$$ExternalSyntheticLambda0(strStringResource, 0);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierM140size3ABfNKs, false, (Function1) objRememberedValue);
                composerImpl.startReplaceGroup(2035889143);
                boolean zChanged2 = composerImpl.changed(j);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChanged2) {
                    companion2.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new Function1() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                DrawScope.m534drawCircleVaOC9Bg$default((DrawScope) obj, j, 0.0f, 0L, 0.0f, null, 0, 126);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl.end(false);
                    CanvasKt.Canvas(modifierSemantics, (Function1) objRememberedValue2, composerImpl, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    FooterActionsKt.NewChangesDot(modifier, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void NumberButton(final int i, final String str, final boolean z, final Function1 function1, final boolean z2, Modifier modifier, Composer composer, final int i2) {
        int i3;
        boolean z3;
        final Modifier modifier2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-320772767);
        if ((i2 & 6) == 0) {
            i3 = (composerImpl2.changed(i) ? 4 : 2) | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            i3 |= composerImpl2.changed(str) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i3 |= composerImpl2.changed(z) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            i3 |= composerImpl2.changedInstance(function1) ? 2048 : 1024;
        }
        if ((i2 & 24576) == 0) {
            z3 = z2;
            i3 |= composerImpl2.changed(z3) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        } else {
            z3 = z2;
        }
        int i4 = i3 | 196608;
        if ((74899 & i4) == 74898 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            modifier2 = modifier;
            composerImpl = composerImpl2;
        } else {
            modifier2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.footer.ui.compose.NumberButton (FooterActions.kt:333)");
            }
            composerImpl2.startReplaceGroup(138381970);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerImpl2.updateRememberedValue(objRememberedValue);
            }
            final MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) objRememberedValue;
            composerImpl2.end(false);
            long jColorAttr = ColorKt.colorAttr(R.attr.shadeInactive, composerImpl2);
            RoundedCornerShape roundedCornerShape = RoundedCornerShapeKt.CircleShape;
            MaterialTheme.INSTANCE.getClass();
            composerImpl = composerImpl2;
            ExpandableKt.m912ExpandableS04cQl8(jColorAttr, roundedCornerShape, BorderOnFocusKt.m2937borderOnFocusPOIbLQ4$default(modifier2, MaterialTheme.getColorScheme(composerImpl2).secondary, CornerSizeKt.CornerSize(50)), 0L, null, function1, mutableInteractionSource, z3, false, null, ComposableLambdaKt.rememberComposableLambda(-609751411, new Function3() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt.NumberButton.1
                /* JADX WARN: Removed duplicated region for block: B:29:0x0101  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    boolean z4;
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.qs.footer.ui.compose.NumberButton.<anonymous> (FooterActions.kt:353)");
                    }
                    Modifier.Companion companion = Modifier.Companion;
                    Dp.Companion companion2 = Dp.Companion;
                    Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(companion, 40);
                    Alignment.Companion.getClass();
                    BiasAlignment biasAlignment = Alignment.Companion.TopStart;
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierM140size3ABfNKs);
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
                    Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                    Updater.m337setimpl(composer2, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                    Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                    Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                    Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                    }
                    Function2 function24 = ComposeUiNode.Companion.SetModifier;
                    Updater.m337setimpl(composer2, modifierMaterializeModifier, function24);
                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                    Modifier modifierIndication = IndicationKt.indication(ClipKt.clip(SizeKt.fillMaxSize(companion, 1.0f), RoundedCornerShapeKt.CircleShape), mutableInteractionSource, (Indication) composerImpl3.consume(IndicationKt.LocalIndication));
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composer2, modifierIndication);
                    composerImpl3.startReusableNode();
                    if (composerImpl3.inserting) {
                        composerImpl3.createNode(function0);
                    } else {
                        composerImpl3.useNode();
                    }
                    Updater.m337setimpl(composer2, measurePolicyMaybeCachedBoxMeasurePolicy2, function2);
                    Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function23);
                    }
                    Updater.m337setimpl(composer2, modifierMaterializeModifier2, function24);
                    String strValueOf = String.valueOf(i);
                    Modifier modifierAlign = boxScopeInstance.align(companion, Alignment.Companion.Center);
                    composerImpl3.startReplaceGroup(1641172204);
                    String str2 = str;
                    boolean zChanged = composerImpl3.changed(str2);
                    Object objRememberedValue2 = composerImpl3.rememberedValue();
                    if (!zChanged) {
                        Composer.Companion.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            objRememberedValue2 = new FooterActionsKt$$ExternalSyntheticLambda0(str2, 1);
                            composerImpl3.updateRememberedValue(objRememberedValue2);
                        }
                    }
                    composerImpl3.end(false);
                    Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierAlign, false, (Function1) objRememberedValue2);
                    MaterialTheme.INSTANCE.getClass();
                    TextKt.m317Text4IGK_g(strValueOf, modifierSemantics, ColorKt.colorAttr(R.attr.onShadeInactiveVariant, composer2), TextUnitKt.getSp(18), null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composer2).bodyLarge, composer2, 3072, 0, 65520);
                    composerImpl3.end(true);
                    composerImpl3.startReplaceGroup(-577381461);
                    if (z) {
                        z4 = false;
                        FooterActionsKt.NewChangesDot(boxScopeInstance.align(companion, Alignment.Companion.BottomEnd), composer2, 0);
                    } else {
                        z4 = false;
                    }
                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl3, z4, true)) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2), composerImpl, (458752 & (i4 << 6)) | 1572864 | ((i4 << 9) & 29360128), 792);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda16
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i2 | 1);
                    boolean z4 = z2;
                    Modifier modifier3 = modifier2;
                    FooterActionsKt.NumberButton(i, str, z, function1, z4, modifier3, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x008f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SecurityButton(final int i, Composer composer, final Modifier modifier, final Function0 function0, final boolean z) {
        Function0 function02;
        Function1 function1;
        boolean z2;
        Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-970415781);
        int i2 = (composerImpl.changed(modifier) ? 256 : 128) | i;
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
            function02 = function0;
            z2 = z;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.footer.ui.compose.SecurityButton (FooterActions.kt:245)");
            }
            FooterActionsSecurityButtonViewModel footerActionsSecurityButtonViewModel = (FooterActionsSecurityButtonViewModel) function0.invoke();
            if (footerActionsSecurityButtonViewModel == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final int i3 = 0;
                    recomposeScopeImplEndRestartGroup.block = new Function2(function0, z, modifier, i, i3) { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda9
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ Function0 f$0;
                        public final /* synthetic */ boolean f$1;
                        public final /* synthetic */ Modifier f$2;

                        {
                            this.$r8$classId = i3;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            int i4 = this.$r8$classId;
                            Composer composer2 = (Composer) obj;
                            ((Integer) obj2).getClass();
                            switch (i4) {
                                case 0:
                                    FooterActionsKt.SecurityButton(RecomposeScopeImplKt.updateChangedFlags(55), composer2, this.f$2, this.f$0, this.f$1);
                                    break;
                                default:
                                    FooterActionsKt.SecurityButton(RecomposeScopeImplKt.updateChangedFlags(55), composer2, this.f$2, this.f$0, this.f$1);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            function02 = function0;
            composerImpl.startReplaceGroup(-1412168314);
            final Function2 function2 = footerActionsSecurityButtonViewModel.onClick;
            if (function2 == null) {
                function1 = null;
            } else {
                final Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
                composerImpl.startReplaceGroup(693161391);
                boolean zChanged = composerImpl.changed(function2) | composerImpl.changedInstance(context);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChanged) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function1() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda10
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                function2.invoke(context, (Expandable) obj);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    function1 = (Function1) objRememberedValue;
                    composerImpl.end(false);
                }
            }
            composerImpl.end(false);
            TextButton(footerActionsSecurityButtonViewModel.icon, footerActionsSecurityButtonViewModel.text, false, function1, z, modifier, composerImpl, 24960 | ((i2 << 9) & 458752));
            z2 = z;
            modifier2 = modifier;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            final int i4 = 1;
            final Modifier modifier3 = modifier2;
            final boolean z3 = z2;
            final Function0 function03 = function02;
            recomposeScopeImplEndRestartGroup2.block = new Function2(function03, z3, modifier3, i, i4) { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda9
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ Function0 f$0;
                public final /* synthetic */ boolean f$1;
                public final /* synthetic */ Modifier f$2;

                {
                    this.$r8$classId = i4;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    int i42 = this.$r8$classId;
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    switch (i42) {
                        case 0:
                            FooterActionsKt.SecurityButton(RecomposeScopeImplKt.updateChangedFlags(55), composer2, this.f$2, this.f$0, this.f$1);
                            break;
                        default:
                            FooterActionsKt.SecurityButton(RecomposeScopeImplKt.updateChangedFlags(55), composer2, this.f$2, this.f$0, this.f$1);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void TextButton(final Icon icon, final String str, final boolean z, final Function1 function1, final boolean z2, final Modifier modifier, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-381465187);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(icon) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(str) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changed(z) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl2.changedInstance(function1) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changed(z2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl2.changed(modifier) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.footer.ui.compose.TextButton (FooterActions.kt:401)");
            }
            int i3 = i2;
            RoundedCornerShape roundedCornerShape = RoundedCornerShapeKt.CircleShape;
            long jColorAttr = ColorKt.colorAttr(R.attr.underSurface, composerImpl2);
            MaterialTheme.INSTANCE.getClass();
            long j = MaterialTheme.getColorScheme(composerImpl2).onSurfaceVariant;
            Dp.Companion companion = Dp.Companion;
            composerImpl = composerImpl2;
            ExpandableKt.m912ExpandableS04cQl8(jColorAttr, roundedCornerShape, BorderOnFocusKt.m2937borderOnFocusPOIbLQ4$default(PaddingKt.m127paddingVpY3zN4$default(modifier, 4, 0.0f, 2), MaterialTheme.getColorScheme(composerImpl2).secondary, CornerSizeKt.CornerSize(50)), j, BorderStrokeKt.m31BorderStrokecXLIe8U(1, ColorKt.colorAttr(R.attr.shadeInactive, composerImpl2)), function1, null, z2, false, null, ComposableLambdaKt.rememberComposableLambda(-667881743, new Function3() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt.TextButton.1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Modifier.Companion companion2;
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.qs.footer.ui.compose.TextButton.<anonymous> (FooterActions.kt:414)");
                    }
                    Modifier.Companion companion3 = Modifier.Companion;
                    Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(companion3, PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_footer_padding, composer2), 0.0f, 2);
                    Alignment.Companion.getClass();
                    BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                    Arrangement.INSTANCE.getClass();
                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composer2, 48);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierM127paddingVpY3zN4$default);
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
                    Updater.m337setimpl(composer2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                    }
                    Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    Dp.Companion companion4 = Dp.Companion;
                    float f = 20;
                    IconKt.m1074IconFNF3uiM(icon, SizeKt.m140size3ABfNKs(PaddingKt.m129paddingqDBjuR0$default(companion3, 0.0f, 0.0f, 12, 0.0f, 11), f), ColorKt.colorAttr(R.attr.onShadeInactiveVariant, composer2), composer2, 48, 0);
                    Modifier modifierWeight = rowScopeInstance.weight(companion3, 1.0f, true);
                    composerImpl3.startReplaceGroup(-145544510);
                    MaterialTheme.INSTANCE.getClass();
                    TextStyle textStyle = MaterialTheme.getTypography(composer2).bodyMedium;
                    composerImpl3.end(false);
                    long jPack = TextUnitKt.pack((float) 0.01d, 8589934592L);
                    long jColorAttr2 = ColorKt.colorAttr(R.attr.onShadeInactiveVariant, composer2);
                    TextOverflow.Companion.getClass();
                    TextKt.m317Text4IGK_g(str, modifierWeight, jColorAttr2, 0L, null, null, null, jPack, null, null, 0L, TextOverflow.Ellipsis, false, 1, 0, null, textStyle, composer2, 0, 3120, 55160);
                    composerImpl3.startReplaceGroup(272410442);
                    if (z) {
                        companion2 = companion3;
                        FooterActionsKt.NewChangesDot(PaddingKt.m129paddingqDBjuR0$default(companion2, 8, 0.0f, 0.0f, 0.0f, 14), composer2, 6);
                    } else {
                        companion2 = companion3;
                    }
                    composerImpl3.end(false);
                    composerImpl3.startReplaceGroup(272414107);
                    if (function1 != null) {
                        androidx.compose.material3.IconKt.m270Iconww6aTOc(PainterResources_androidKt.painterResource(android.R.drawable.ic_fingerprint, composer2, 6), (String) null, SizeKt.m140size3ABfNKs(PaddingKt.m129paddingqDBjuR0$default(companion2, 8, 0.0f, 0.0f, 0.0f, 14), f), ColorKt.colorAttr(R.attr.onShadeInactiveVariant, composer2), composer2, 432, 0);
                    }
                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl3, false, true)) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2), composerImpl, (458752 & (i3 << 6)) | ((i3 << 9) & 29360128), 832);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda15
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    boolean z3 = z2;
                    Modifier modifier2 = modifier;
                    FooterActionsKt.TextButton(icon, str, z, function1, z3, modifier2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void IconButton(final FooterActionsButtonViewModel footerActionsButtonViewModel, boolean z, Modifier modifier, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1682468438);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(footerActionsButtonViewModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(z) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changed(modifier) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.footer.ui.compose.IconButton (FooterActions.kt:307)");
            }
            long jColorAttr = ColorKt.colorAttr(footerActionsButtonViewModel.backgroundColor, composerImpl2);
            RoundedCornerShape roundedCornerShape = RoundedCornerShapeKt.CircleShape;
            MaterialTheme.INSTANCE.getClass();
            composerImpl = composerImpl2;
            ExpandableKt.m912ExpandableS04cQl8(jColorAttr, roundedCornerShape, BorderOnFocusKt.m2937borderOnFocusPOIbLQ4$default(modifier, MaterialTheme.getColorScheme(composerImpl2).secondary, CornerSizeKt.CornerSize(50)), 0L, null, footerActionsButtonViewModel.onClick, null, z, false, null, ComposableLambdaKt.rememberComposableLambda(-1968802346, new Function3() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt.IconButton.2
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    long jColor;
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.qs.footer.ui.compose.IconButton.<anonymous> (FooterActions.kt:319)");
                    }
                    FooterActionsButtonViewModel footerActionsButtonViewModel2 = footerActionsButtonViewModel;
                    Integer num = footerActionsButtonViewModel2.iconTint;
                    if (num != null) {
                        jColor = androidx.compose.ui.graphics.ColorKt.Color(num.intValue());
                    } else {
                        Color.Companion.getClass();
                        jColor = Color.Unspecified;
                    }
                    long j = jColor;
                    Dp.Companion companion = Dp.Companion;
                    IconKt.m1074IconFNF3uiM(footerActionsButtonViewModel2.icon, SizeKt.m140size3ABfNKs(Modifier.Companion, 20), j, composer2, 48, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2), composerImpl, (i2 << 18) & 29360128, 856);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new FooterActionsKt$$ExternalSyntheticLambda12(footerActionsButtonViewModel, z, modifier, i, 2);
        }
    }
}
