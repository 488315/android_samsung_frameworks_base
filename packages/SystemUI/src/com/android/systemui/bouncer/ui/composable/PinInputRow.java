package com.android.systemui.bouncer.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import java.util.ListIterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PinInputRow {
    public final SnapshotStateList entries = new SnapshotStateList();
    public final ShapeAnimations shapeAnimations;

    public PinInputRow(ShapeAnimations shapeAnimations) {
        this.shapeAnimations = shapeAnimations;
    }

    public final void Content(final PinBouncerViewModel pinBouncerViewModel, final Modifier modifier, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1350064931);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(pinBouncerViewModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(this) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.bouncer.ui.composable.PinInputRow.Content (PinInputDisplay.kt:367)");
            }
            float dimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_dot_size, composerImpl) * 32;
            Dp.Companion companion = Dp.Companion;
            Modifier.Companion companion2 = Modifier.Companion;
            Modifier clipToBounds = ClipKt.clipToBounds(SizeKt.wrapContentHeight$default(SizeKt.m143width3ABfNKs(companion2, dimensionResource), 3));
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, clipToBounds);
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
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composerImpl, materializeModifier, function24);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            ShapeAnimations shapeAnimations = this.shapeAnimations;
            Modifier wrapContentSize$default = SizeKt.wrapContentSize$default(SizeKt.m130height3ABfNKs(companion2, shapeAnimations.shapeSize), null, 1);
            SnapshotStateList snapshotStateList = this.entries;
            Modifier m128paddingqDBjuR0$default = PaddingKt.m128paddingqDBjuR0$default(wrapContentSize$default, 0.0f, 0.0f, shapeAnimations.shapeSize * Math.max(snapshotStateList.size() - 32, 0), 0.0f, 11);
            Arrangement.INSTANCE.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl, 48);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, m128paddingqDBjuR0$default);
            composerImpl.startReusableNode();
            int i3 = i2;
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, rowMeasurePolicy, function2);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope2, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
            }
            Updater.m336setimpl(composerImpl, materializeModifier2, function24);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(2117625286);
            ListIterator listIterator = snapshotStateList.listIterator();
            while (listIterator.hasNext()) {
                PinInputEntry pinInputEntry = (PinInputEntry) listIterator.next();
                composerImpl.startMovableGroup(-723577840, pinInputEntry.digit);
                pinInputEntry.Content(pinBouncerViewModel, composerImpl, i3 & 14);
                composerImpl.end(false);
            }
            composerImpl.end(false);
            composerImpl.end(true);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.bouncer.ui.composable.PinInputRow$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    PinInputRow.this.Content(pinBouncerViewModel, modifier, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
