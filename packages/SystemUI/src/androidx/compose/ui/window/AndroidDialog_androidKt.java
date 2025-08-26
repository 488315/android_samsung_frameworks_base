package androidx.compose.ui.window;

import android.view.View;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;

/* loaded from: classes.dex */
public abstract class AndroidDialog_androidKt {
    /* JADX WARN: Removed duplicated region for block: B:26:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:84:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void Dialog(final Function0 function0, DialogProperties dialogProperties, final Function2 function2, Composer composer, final int i, final int i2) {
        int i3;
        DialogProperties dialogProperties2;
        final DialogProperties dialogProperties3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i4;
        int i5;
        int i6;
        int i7;
        final LayoutDirection layoutDirection;
        boolean z;
        Object obj;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2032877254);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i8 = i2 & 2;
        if (i8 == 0) {
            if ((i & 48) == 0) {
                dialogProperties2 = dialogProperties;
                i3 |= composerImpl.changed(dialogProperties2) ? 32 : 16;
            }
            if ((i2 & 4) == 0) {
                i3 |= 384;
            } else if ((i & 384) == 0) {
                i3 |= composerImpl.changedInstance(function2) ? 256 : 128;
            }
            if (composerImpl.shouldExecute(i3 & 1, (i3 & 147) == 146)) {
                composerImpl.skipToGroupEnd();
                dialogProperties3 = dialogProperties2;
            } else {
                if (i8 != 0) {
                    i4 = 4;
                    dialogProperties3 = new DialogProperties(false, false, false, 7, (DefaultConstructorMarker) null);
                } else {
                    i4 = 4;
                    dialogProperties3 = dialogProperties2;
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.ui.window.Dialog (AndroidDialog.android.kt:178)");
                }
                View view = (View) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalView);
                Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
                LayoutDirection layoutDirection2 = (LayoutDirection) composerImpl.consume(CompositionLocalsKt.LocalLayoutDirection);
                ComposerImpl.CompositionContextImpl compositionContextImplRememberCompositionContext = ComposablesKt.rememberCompositionContext(composerImpl);
                final MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function2, composerImpl);
                UUID uuid = (UUID) RememberSaveableKt.rememberSaveable(new Object[0], null, null, new Function0() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$Dialog$dialogId$1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return UUID.randomUUID();
                    }
                }, composerImpl, 3072, 6);
                boolean zChanged = composerImpl.changed(view) | composerImpl.changed(density);
                Object objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion companion = Composer.Companion;
                if (!zChanged) {
                    companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        i5 = i4;
                        i6 = i3;
                        i7 = 32;
                        layoutDirection = layoutDirection2;
                        DialogWrapper dialogWrapper = new DialogWrapper(function0, dialogProperties3, view, layoutDirection, density, uuid);
                        z = true;
                        ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(488261145, true, new Function2() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$Dialog$dialog$1$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Multi-variable type inference failed */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                Composer composer2 = (Composer) obj2;
                                int iIntValue = ((Number) obj3).intValue();
                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                if (composerImpl2.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.ui.window.Dialog.<anonymous>.<anonymous>.<anonymous> (AndroidDialog.android.kt:190)");
                                    }
                                    AndroidDialog_androidKt.access$DialogLayout(SemanticsModifierKt.semantics(Modifier.Companion, false, new Function1() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$Dialog$dialog$1$1$1.1
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj4) {
                                            KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
                                            SemanticsProperties.INSTANCE.getClass();
                                            SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.IsDialog;
                                            Unit unit = Unit.INSTANCE;
                                            ((SemanticsConfiguration) ((SemanticsPropertyReceiver) obj4)).set(semanticsPropertyKey, unit);
                                            return unit;
                                        }
                                    }), (Function2) mutableStateRememberUpdatedState.getValue(), composerImpl2, 0, 0);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                } else {
                                    composerImpl2.skipToGroupEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        });
                        DialogLayout dialogLayout = dialogWrapper.dialogLayout;
                        dialogLayout.setParentCompositionContext(compositionContextImplRememberCompositionContext);
                        ((SnapshotMutableStateImpl) dialogLayout.content$delegate).setValue(composableLambdaImpl);
                        dialogLayout.shouldCreateCompositionOnAttachedToWindow = true;
                        dialogLayout.createComposition();
                        composerImpl.updateRememberedValue(dialogWrapper);
                        obj = dialogWrapper;
                    } else {
                        i6 = i3;
                        obj = objRememberedValue;
                        i5 = i4;
                        i7 = 32;
                        layoutDirection = layoutDirection2;
                        z = true;
                    }
                    final DialogWrapper dialogWrapper2 = (DialogWrapper) obj;
                    Unit unit = Unit.INSTANCE;
                    boolean zChangedInstance = composerImpl.changedInstance(dialogWrapper2);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (!zChangedInstance) {
                        companion.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            objRememberedValue2 = new AndroidDialog_androidKt$Dialog$1$1(dialogWrapper2, null);
                            composerImpl.updateRememberedValue(objRememberedValue2);
                        }
                        EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) objRememberedValue2);
                        boolean zChangedInstance2 = composerImpl.changedInstance(dialogWrapper2);
                        Object objRememberedValue3 = composerImpl.rememberedValue();
                        if (!zChangedInstance2) {
                            companion.getClass();
                            if (objRememberedValue3 == Composer.Companion.Empty) {
                                objRememberedValue3 = new Function1() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$Dialog$2$1
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj2) {
                                        final DialogWrapper dialogWrapper3 = dialogWrapper2;
                                        return new DisposableEffectResult() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$Dialog$2$1$invoke$$inlined$onDispose$1
                                            @Override // androidx.compose.runtime.DisposableEffectResult
                                            public final void dispose() {
                                                DialogWrapper dialogWrapper4 = dialogWrapper3;
                                                dialogWrapper4.dismiss();
                                                dialogWrapper4.dialogLayout.disposeComposition();
                                            }
                                        };
                                    }
                                };
                                composerImpl.updateRememberedValue(objRememberedValue3);
                            }
                            EffectsKt.DisposableEffect(dialogWrapper2, (Function1) objRememberedValue3, composerImpl);
                            boolean zChangedInstance3 = composerImpl.changedInstance(dialogWrapper2) | ((i6 & 14) == i5 ? z : false) | ((i6 & 112) == i7 ? z : false) | composerImpl.changed(layoutDirection);
                            Object objRememberedValue4 = composerImpl.rememberedValue();
                            if (!zChangedInstance3) {
                                companion.getClass();
                                if (objRememberedValue4 == Composer.Companion.Empty) {
                                    objRememberedValue4 = new Function0() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$Dialog$3$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            dialogWrapper2.updateParameters(function0, dialogProperties3, layoutDirection);
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl.updateRememberedValue(objRememberedValue4);
                                }
                                EffectsKt.SideEffect((Function0) objRememberedValue4, composerImpl);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                    }
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt.Dialog.4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        ((Number) obj3).intValue();
                        AndroidDialog_androidKt.Dialog(function0, dialogProperties3, function2, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 48;
        dialogProperties2 = dialogProperties;
        if ((i2 & 4) == 0) {
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 147) == 146)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    public static final void access$DialogLayout(final Modifier modifier, final Function2 function2, Composer composer, final int i, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1177876616);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(function2) ? 32 : 16;
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 19) != 18)) {
            if (i4 != 0) {
                modifier = Modifier.Companion;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.ui.window.DialogLayout (AndroidDialog.android.kt:604)");
            }
            int i5 = ((i3 >> 3) & 14) | 384 | ((i3 << 3) & 112);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            int i6 = ((i5 << 6) & 896) | 6;
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
            Updater.m337setimpl(composerImpl, new MeasurePolicy() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$DialogLayout$1
                @Override // androidx.compose.ui.layout.MeasurePolicy
                /* renamed from: measure-3p2s80s */
                public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
                    final ArrayList arrayList = new ArrayList(list.size());
                    int size = list.size();
                    int iM825getMinWidthimpl = 0;
                    int iM824getMinHeightimpl = 0;
                    for (int i7 = 0; i7 < size; i7++) {
                        Placeable placeableMo610measureBRTryo0 = ((Measurable) list.get(i7)).mo610measureBRTryo0(j);
                        iM825getMinWidthimpl = Math.max(iM825getMinWidthimpl, placeableMo610measureBRTryo0.width);
                        iM824getMinHeightimpl = Math.max(iM824getMinHeightimpl, placeableMo610measureBRTryo0.height);
                        arrayList.add(placeableMo610measureBRTryo0);
                    }
                    if (list.isEmpty()) {
                        iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(j);
                        iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(j);
                    }
                    return measureScope.layout$1(iM825getMinWidthimpl, iM824getMinHeightimpl, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$DialogLayout$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                            List<Placeable> list2 = arrayList;
                            int size2 = list2.size();
                            for (int i8 = 0; i8 < size2; i8++) {
                                placementScope.placeRelative(list2.get(i8), 0, 0, 0.0f);
                            }
                            return Unit.INSTANCE;
                        }
                    });
                }
            }, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function22);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            function2.invoke(composerImpl, Integer.valueOf((i6 >> 6) & 14));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$DialogLayout$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    AndroidDialog_androidKt.access$DialogLayout(modifier, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
