package com.android.systemui.qs.footer.ui.compose;

import android.content.Context;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.Arrangement;
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
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            Object collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(footerActionsViewModel2.alpha, composerImpl);
            final MutableState collectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(footerActionsViewModel2.backgroundAlpha, composerImpl);
            composerImpl.startReplaceGroup(1467762618);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (rememberedValue == obj) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            MutableState mutableState5 = (MutableState) rememberedValue;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1467765936);
            if (m == obj) {
                m = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(m);
            }
            MutableState mutableState6 = (MutableState) m;
            Object m2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1467769714);
            if (m2 == obj) {
                m2 = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(m2);
            }
            MutableState mutableState7 = (MutableState) m2;
            Object m3 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1467772327);
            if (m3 == obj) {
                m3 = SnapshotStateKt.mutableStateOf$default(footerActionsViewModel2.initialPower.invoke());
                composerImpl.updateRememberedValue(m3);
            }
            MutableState mutableState8 = (MutableState) m3;
            composerImpl.end(false);
            Object[] objArr2 = {context, lifecycleOwner, footerActionsViewModel2, footerActionsViewModel2.security, footerActionsViewModel2.foregroundServices, footerActionsViewModel2.userSwitcher};
            composerImpl.startReplaceGroup(1467780949);
            boolean changedInstance = composerImpl.changedInstance(footerActionsViewModel2) | composerImpl.changedInstance(context) | composerImpl.changedInstance(lifecycleOwner);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue2 == obj) {
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
                footerActionsKt$FooterActions$1$1 = rememberedValue2;
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
            final long colorAttr = ColorKt.colorAttr(R.attr.underSurface, composerImpl);
            composerImpl.end(z);
            composerImpl.startReplaceGroup(1467805780);
            PropertyReference0Impl propertyReference0Impl = new PropertyReference0Impl(collectAsStateWithLifecycle2) { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$backgroundAlphaValue$1
                @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                public final Object get() {
                    return ((State) this.receiver).getValue();
                }
            };
            composerImpl.end(z);
            MaterialTheme.INSTANCE.getClass();
            final MutableState mutableState10 = mutableState4;
            long j = MaterialTheme.getColorScheme(composerImpl).onSurface;
            float dimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_corner_radius, composerImpl);
            composerImpl.startReplaceGroup(1467813606);
            boolean changed = composerImpl.changed(colorAttr) | composerImpl.changed(propertyReference0Impl) | composerImpl.changed(dimensionResource);
            Object rememberedValue3 = composerImpl.rememberedValue();
            if (changed || rememberedValue3 == obj) {
                rememberedValue3 = AnimatedBackgroundKt.animatedBackground(companion2, new Function0() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$backgroundModifier$1$1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Color.m454boximpl(colorAttr);
                    }
                }, propertyReference0Impl, RoundedCornerShapeKt.m188RoundedCornerShapea9UjIt4$default(dimensionResource, dimensionResource, 0.0f, 0.0f, 12));
                composerImpl.updateRememberedValue(rememberedValue3);
            }
            Modifier modifier = (Modifier) rememberedValue3;
            composerImpl.end(false);
            float dimensionResource2 = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_content_horizontal_padding, composerImpl);
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(companion2, 1.0f);
            composerImpl.startReplaceGroup(1467828552);
            boolean changed2 = composerImpl.changed(collectAsStateWithLifecycle);
            Object rememberedValue4 = composerImpl.rememberedValue();
            if (changed2 || rememberedValue4 == obj) {
                rememberedValue4 = new FooterActionsKt$$ExternalSyntheticLambda0(collectAsStateWithLifecycle, 2);
                composerImpl.updateRememberedValue(rememberedValue4);
            }
            composerImpl.end(false);
            Modifier m127paddingqDBjuR0 = PaddingKt.m127paddingqDBjuR0(GraphicsLayerModifierKt.graphicsLayer(fillMaxWidth, (Function1) rememberedValue4).then(modifier), dimensionResource2, PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_footer_actions_top_padding, composerImpl), dimensionResource2, PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_footer_actions_bottom_padding, composerImpl));
            composerImpl.startReplaceGroup(1467841357);
            Object rememberedValue5 = composerImpl.rememberedValue();
            if (rememberedValue5 == obj) {
                rememberedValue5 = new FooterActionsKt$$ExternalSyntheticLambda4();
                composerImpl.updateRememberedValue(rememberedValue5);
            }
            composerImpl.end(false);
            Modifier layout = LayoutModifierKt.layout(m127paddingqDBjuR0, (Function3) rememberedValue5);
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            Arrangement.INSTANCE.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl, 48);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, layout);
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
            Updater.m336setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            final RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            footerActionsViewModel2 = footerActionsViewModel;
            final MutableState mutableState11 = mutableState;
            final MutableState mutableState12 = mutableState2;
            CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m454boximpl(j)), ComposableLambdaKt.rememberComposableLambda(-2122823950, new Function2() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$4$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.qs.footer.ui.compose.FooterActions.<anonymous>.<anonymous> (FooterActions.kt:217)");
                    }
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    composerImpl3.startReplaceGroup(1849424287);
                    final MutableState mutableState13 = mutableState10;
                    FooterActionsSecurityButtonViewModel footerActionsSecurityButtonViewModel = (FooterActionsSecurityButtonViewModel) mutableState13.getValue();
                    RowScope rowScope = RowScope.this;
                    final MutableState mutableState14 = mutableState11;
                    if (footerActionsSecurityButtonViewModel == null && ((FooterActionsForegroundServicesButtonViewModel) mutableState14.getValue()) == null) {
                        SpacerKt.Spacer(composerImpl3, rowScope.weight(Modifier.Companion, 1.0f, true));
                    }
                    Object m4 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl3, false, 1849429272);
                    Composer.Companion.getClass();
                    Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                    if (m4 == composer$Companion$Empty$1) {
                        m4 = Boolean.FALSE;
                        composerImpl3.updateRememberedValue(m4);
                    }
                    boolean booleanValue = ((Boolean) m4).booleanValue();
                    composerImpl3.end(false);
                    composerImpl3.startReplaceGroup(1849431420);
                    Object rememberedValue6 = composerImpl3.rememberedValue();
                    if (rememberedValue6 == composer$Companion$Empty$1) {
                        final int i2 = 0;
                        rememberedValue6 = new Function0() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$4$1$$ExternalSyntheticLambda0
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
                        composerImpl3.updateRememberedValue(rememberedValue6);
                    }
                    composerImpl3.end(false);
                    Modifier.Companion companion3 = Modifier.Companion;
                    FooterActionsKt.SecurityButton(54, composerImpl3, rowScope.weight(companion3, 1.0f, true), (Function0) rememberedValue6, booleanValue);
                    composerImpl3.startReplaceGroup(1849434630);
                    Object rememberedValue7 = composerImpl3.rememberedValue();
                    if (rememberedValue7 == composer$Companion$Empty$1) {
                        final int i3 = 1;
                        rememberedValue7 = new Function0() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$4$1$$ExternalSyntheticLambda0
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
                        composerImpl3.updateRememberedValue(rememberedValue7);
                    }
                    composerImpl3.end(false);
                    FooterActionsKt.ForegroundServicesButton(rowScope, (Function0) rememberedValue7, booleanValue, composerImpl3, 432);
                    composerImpl3.startReplaceGroup(1849437568);
                    Object rememberedValue8 = composerImpl3.rememberedValue();
                    if (rememberedValue8 == composer$Companion$Empty$1) {
                        final MutableState mutableState15 = mutableState12;
                        final int i4 = 2;
                        rememberedValue8 = new Function0() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$4$1$$ExternalSyntheticLambda0
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
                        composerImpl3.updateRememberedValue(rememberedValue8);
                    }
                    composerImpl3.end(false);
                    FooterActionsKt.IconButton(438, composerImpl3, SysuiTestTagKt.sysuiResTag(companion3, "multi_user_switch"), (Function0) rememberedValue8, booleanValue);
                    composerImpl3.startReplaceGroup(1849443174);
                    final FooterActionsViewModel footerActionsViewModel3 = footerActionsViewModel2;
                    boolean changedInstance2 = composerImpl3.changedInstance(footerActionsViewModel3);
                    Object rememberedValue9 = composerImpl3.rememberedValue();
                    if (changedInstance2 || rememberedValue9 == composer$Companion$Empty$1) {
                        final int i5 = 4;
                        rememberedValue9 = new Function0() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$4$1$$ExternalSyntheticLambda0
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
                        composerImpl3.updateRememberedValue(rememberedValue9);
                    }
                    composerImpl3.end(false);
                    FooterActionsKt.IconButton(432, composerImpl3, SysuiTestTagKt.sysuiResTag(companion3, "settings_button_container"), (Function0) rememberedValue9, booleanValue);
                    composerImpl3.startReplaceGroup(1849448665);
                    Object rememberedValue10 = composerImpl3.rememberedValue();
                    if (rememberedValue10 == composer$Companion$Empty$1) {
                        final MutableState mutableState16 = mutableState9;
                        final int i6 = 3;
                        rememberedValue10 = new Function0() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$4$1$$ExternalSyntheticLambda0
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
                        composerImpl3.updateRememberedValue(rememberedValue10);
                    }
                    composerImpl3.end(false);
                    FooterActionsKt.IconButton(438, composerImpl3, SysuiTestTagKt.sysuiResTag(companion3, "pm_lite"), (Function0) rememberedValue10, booleanValue);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(lifecycleOwner2, companion2, i) { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda5
                public final /* synthetic */ LifecycleOwner f$1;
                public final /* synthetic */ Modifier.Companion f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    LifecycleOwner lifecycleOwner3 = this.f$1;
                    Modifier.Companion companion3 = this.f$2;
                    FooterActionsKt.FooterActions(FooterActionsViewModel.this, lifecycleOwner3, companion3, (Composer) obj2, updateChangedFlags);
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
                RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                if (endRestartGroup != null) {
                    final int i3 = 0;
                    endRestartGroup.block = new Function2(rowScope, function0, z, i, i3) { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda7
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
                                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(433);
                                    FooterActionsKt.ForegroundServicesButton(this.f$0, this.f$1, this.f$2, composer2, updateChangedFlags);
                                    break;
                                default:
                                    int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(433);
                                    FooterActionsKt.ForegroundServicesButton(this.f$0, this.f$1, this.f$2, composer2, updateChangedFlags2);
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
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            final int i4 = 1;
            final int i5 = i2;
            endRestartGroup2.block = new Function2(rowScope, function0, z2, i5, i4) { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda7
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
                            int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(433);
                            FooterActionsKt.ForegroundServicesButton(this.f$0, this.f$1, this.f$2, composer2, updateChangedFlags);
                            break;
                        default:
                            int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(433);
                            FooterActionsKt.ForegroundServicesButton(this.f$0, this.f$1, this.f$2, composer2, updateChangedFlags2);
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
                RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                if (endRestartGroup != null) {
                    endRestartGroup.block = new FooterActionsKt$$ExternalSyntheticLambda12(function0, z, modifier, i, 0);
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
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            int i4 = i3;
            endRestartGroup2.block = new FooterActionsKt$$ExternalSyntheticLambda12(function02, z2, modifier2, i4, 1);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0067, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x008f, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void NewChangesDot(final androidx.compose.ui.Modifier r8, androidx.compose.runtime.Composer r9, final int r10) {
        /*
            r0 = 0
            androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
            r1 = 1339346955(0x4fd4d00b, float:7.140808E9)
            r9.startRestartGroup(r1)
            r1 = r10 & 6
            r2 = 2
            if (r1 != 0) goto L19
            boolean r1 = r9.changed(r8)
            if (r1 == 0) goto L16
            r1 = 4
            goto L17
        L16:
            r1 = r2
        L17:
            r1 = r1 | r10
            goto L1a
        L19:
            r1 = r10
        L1a:
            r1 = r1 & 3
            if (r1 != r2) goto L2a
            boolean r1 = r9.getSkipping()
            if (r1 != 0) goto L25
            goto L2a
        L25:
            r9.skipToGroupEnd()
            goto Laa
        L2a:
            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r1 == 0) goto L35
            java.lang.String r1 = "com.android.systemui.qs.footer.ui.compose.NewChangesDot (FooterActions.kt:382)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r1)
        L35:
            r1 = 2131953540(0x7f130784, float:1.9543554E38)
            java.lang.String r1 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r1, r9)
            androidx.compose.material3.MaterialTheme r2 = androidx.compose.material3.MaterialTheme.INSTANCE
            r2.getClass()
            androidx.compose.material3.ColorScheme r2 = androidx.compose.material3.MaterialTheme.getColorScheme(r9)
            long r2 = r2.tertiary
            r4 = 12
            float r4 = (float) r4
            androidx.compose.ui.unit.Dp$Companion r5 = androidx.compose.ui.unit.Dp.Companion
            androidx.compose.ui.Modifier r4 = androidx.compose.foundation.layout.SizeKt.m139size3ABfNKs(r8, r4)
            r5 = 2035887558(0x79592dc6, float:7.047855E34)
            r9.startReplaceGroup(r5)
            boolean r5 = r9.changed(r1)
            java.lang.Object r6 = r9.rememberedValue()
            androidx.compose.runtime.Composer$Companion r7 = androidx.compose.runtime.Composer.Companion
            if (r5 != 0) goto L69
            r7.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r5 = androidx.compose.runtime.Composer.Companion.Empty
            if (r6 != r5) goto L71
        L69:
            com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda0 r6 = new com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda0
            r6.<init>(r1, r0)
            r9.updateRememberedValue(r6)
        L71:
            kotlin.jvm.functions.Function1 r6 = (kotlin.jvm.functions.Function1) r6
            r9.end(r0)
            androidx.compose.ui.Modifier r1 = androidx.compose.ui.semantics.SemanticsModifierKt.semantics(r4, r0, r6)
            r4 = 2035889143(0x795933f7, float:7.04864E34)
            r9.startReplaceGroup(r4)
            boolean r4 = r9.changed(r2)
            java.lang.Object r5 = r9.rememberedValue()
            if (r4 != 0) goto L91
            r7.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r4 = androidx.compose.runtime.Composer.Companion.Empty
            if (r5 != r4) goto L99
        L91:
            com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda1 r5 = new com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda1
            r5.<init>()
            r9.updateRememberedValue(r5)
        L99:
            kotlin.jvm.functions.Function1 r5 = (kotlin.jvm.functions.Function1) r5
            r9.end(r0)
            androidx.compose.foundation.CanvasKt.Canvas(r1, r5, r9, r0)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Laa
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Laa:
            androidx.compose.runtime.RecomposeScopeImpl r9 = r9.endRestartGroup()
            if (r9 == 0) goto Lb7
            com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda2 r0 = new com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda2
            r0.<init>()
            r9.block = r0
        Lb7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.footer.ui.compose.FooterActionsKt.NewChangesDot(androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
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
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            final MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) rememberedValue;
            composerImpl2.end(false);
            long colorAttr = ColorKt.colorAttr(R.attr.shadeInactive, composerImpl2);
            RoundedCornerShape roundedCornerShape = RoundedCornerShapeKt.CircleShape;
            MaterialTheme.INSTANCE.getClass();
            composerImpl = composerImpl2;
            ExpandableKt.m910ExpandableS04cQl8(colorAttr, roundedCornerShape, BorderOnFocusKt.m2920borderOnFocusPOIbLQ4$default(modifier2, MaterialTheme.getColorScheme(composerImpl2).secondary, CornerSizeKt.CornerSize(50)), 0L, null, function1, mutableInteractionSource, z3, false, null, ComposableLambdaKt.rememberComposableLambda(-609751411, new Function3() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$NumberButton$1
                /* JADX WARN: Code restructure failed: missing block: B:23:0x00ff, code lost:
                
                    if (r10 == androidx.compose.runtime.Composer.Companion.Empty) goto L29;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r35, java.lang.Object r36, java.lang.Object r37) {
                    /*
                        Method dump skipped, instructions count: 418
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$NumberButton$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl2), composerImpl, (458752 & (i4 << 6)) | 1572864 | ((i4 << 9) & 29360128), 792);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda16
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i2 | 1);
                    boolean z4 = z2;
                    Modifier modifier3 = modifier2;
                    FooterActionsKt.NumberButton(i, str, z, function1, z4, modifier3, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x008d, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SecurityButton(final int r8, androidx.compose.runtime.Composer r9, final androidx.compose.ui.Modifier r10, final kotlin.jvm.functions.Function0 r11, final boolean r12) {
        /*
            Method dump skipped, instructions count: 210
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.footer.ui.compose.FooterActionsKt.SecurityButton(int, androidx.compose.runtime.Composer, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function0, boolean):void");
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
            long colorAttr = ColorKt.colorAttr(R.attr.underSurface, composerImpl2);
            MaterialTheme.INSTANCE.getClass();
            long j = MaterialTheme.getColorScheme(composerImpl2).onSurfaceVariant;
            Dp.Companion companion = Dp.Companion;
            composerImpl = composerImpl2;
            ExpandableKt.m910ExpandableS04cQl8(colorAttr, roundedCornerShape, BorderOnFocusKt.m2920borderOnFocusPOIbLQ4$default(PaddingKt.m126paddingVpY3zN4$default(modifier, 4, 0.0f, 2), MaterialTheme.getColorScheme(composerImpl2).secondary, CornerSizeKt.CornerSize(50)), j, BorderStrokeKt.m31BorderStrokecXLIe8U(1, ColorKt.colorAttr(R.attr.shadeInactive, composerImpl2)), function1, null, z2, false, null, ComposableLambdaKt.rememberComposableLambda(-667881743, new Function3() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$TextButton$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Modifier.Companion companion2;
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.qs.footer.ui.compose.TextButton.<anonymous> (FooterActions.kt:414)");
                    }
                    Modifier.Companion companion3 = Modifier.Companion;
                    Modifier m126paddingVpY3zN4$default = PaddingKt.m126paddingVpY3zN4$default(companion3, PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_footer_padding, composer2), 0.0f, 2);
                    Alignment.Companion.getClass();
                    BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                    Arrangement.INSTANCE.getClass();
                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composer2, 48);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                    Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, m126paddingVpY3zN4$default);
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
                    Updater.m336setimpl(composer2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m336setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                    }
                    Updater.m336setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    Dp.Companion companion4 = Dp.Companion;
                    float f = 20;
                    IconKt.m1072IconFNF3uiM(Icon.this, SizeKt.m139size3ABfNKs(PaddingKt.m128paddingqDBjuR0$default(companion3, 0.0f, 0.0f, 12, 0.0f, 11), f), ColorKt.colorAttr(R.attr.onShadeInactiveVariant, composer2), composer2, 48, 0);
                    Modifier weight = rowScopeInstance.weight(companion3, 1.0f, true);
                    composerImpl3.startReplaceGroup(-145544510);
                    MaterialTheme.INSTANCE.getClass();
                    TextStyle textStyle = MaterialTheme.getTypography(composer2).bodyMedium;
                    composerImpl3.end(false);
                    long pack = TextUnitKt.pack((float) 0.01d, 8589934592L);
                    long colorAttr2 = ColorKt.colorAttr(R.attr.onShadeInactiveVariant, composer2);
                    TextOverflow.Companion.getClass();
                    TextKt.m316Text4IGK_g(str, weight, colorAttr2, 0L, null, null, null, pack, null, null, 0L, TextOverflow.Ellipsis, false, 1, 0, null, textStyle, composer2, 0, 3120, 55160);
                    composerImpl3.startReplaceGroup(272410442);
                    if (z) {
                        companion2 = companion3;
                        FooterActionsKt.NewChangesDot(PaddingKt.m128paddingqDBjuR0$default(companion2, 8, 0.0f, 0.0f, 0.0f, 14), composer2, 6);
                    } else {
                        companion2 = companion3;
                    }
                    composerImpl3.end(false);
                    composerImpl3.startReplaceGroup(272414107);
                    if (function1 != null) {
                        androidx.compose.material3.IconKt.m269Iconww6aTOc(PainterResources_androidKt.painterResource(android.R.drawable.ic_fingerprint, composer2, 6), (String) null, SizeKt.m139size3ABfNKs(PaddingKt.m128paddingqDBjuR0$default(companion2, 8, 0.0f, 0.0f, 0.0f, 14), f), ColorKt.colorAttr(R.attr.onShadeInactiveVariant, composer2), composer2, 432, 0);
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda15
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    boolean z3 = z2;
                    Modifier modifier2 = modifier;
                    FooterActionsKt.TextButton(Icon.this, str, z, function1, z3, modifier2, (Composer) obj, updateChangedFlags);
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
            long colorAttr = ColorKt.colorAttr(footerActionsButtonViewModel.backgroundColor, composerImpl2);
            RoundedCornerShape roundedCornerShape = RoundedCornerShapeKt.CircleShape;
            MaterialTheme.INSTANCE.getClass();
            composerImpl = composerImpl2;
            ExpandableKt.m910ExpandableS04cQl8(colorAttr, roundedCornerShape, BorderOnFocusKt.m2920borderOnFocusPOIbLQ4$default(modifier, MaterialTheme.getColorScheme(composerImpl2).secondary, CornerSizeKt.CornerSize(50)), 0L, null, footerActionsButtonViewModel.onClick, null, z, false, null, ComposableLambdaKt.rememberComposableLambda(-1968802346, new Function3() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$IconButton$2
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    long j;
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.qs.footer.ui.compose.IconButton.<anonymous> (FooterActions.kt:319)");
                    }
                    FooterActionsButtonViewModel footerActionsButtonViewModel2 = FooterActionsButtonViewModel.this;
                    Integer num = footerActionsButtonViewModel2.iconTint;
                    if (num != null) {
                        j = androidx.compose.ui.graphics.ColorKt.Color(num.intValue());
                    } else {
                        Color.Companion.getClass();
                        j = Color.Unspecified;
                    }
                    long j2 = j;
                    Dp.Companion companion = Dp.Companion;
                    IconKt.m1072IconFNF3uiM(footerActionsButtonViewModel2.icon, SizeKt.m139size3ABfNKs(Modifier.Companion, 20), j2, composer2, 48, 0);
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new FooterActionsKt$$ExternalSyntheticLambda12(footerActionsButtonViewModel, z, modifier, i, 2);
        }
    }
}
