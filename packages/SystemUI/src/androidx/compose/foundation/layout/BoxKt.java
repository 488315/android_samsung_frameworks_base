package androidx.compose.foundation.layout;

import androidx.collection.MutableScatterMap;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BoxKt {
    public static final MutableScatterMap Cache1 = cacheFor(true);
    public static final MutableScatterMap Cache2 = cacheFor(false);
    public static final MeasurePolicy DefaultBoxMeasurePolicy = null;
    public static final MeasurePolicy EmptyBoxMeasurePolicy;

    static {
        Alignment.Companion.getClass();
        new BoxMeasurePolicy(Alignment.Companion.TopStart, false);
        EmptyBoxMeasurePolicy = new MeasurePolicy() { // from class: androidx.compose.foundation.layout.BoxKt$EmptyBoxMeasurePolicy$1
            @Override // androidx.compose.ui.layout.MeasurePolicy
            /* renamed from: measure-3p2s80s */
            public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
                MeasureResult layout$1;
                layout$1 = measureScope.layout$1(Constraints.m823getMinWidthimpl(j), Constraints.m822getMinHeightimpl(j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.BoxKt$EmptyBoxMeasurePolicy$1.1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
                        return Unit.INSTANCE;
                    }
                });
                return layout$1;
            }
        };
    }

    public static final void Box(final Modifier modifier, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-211209833);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if (composerImpl.shouldExecute(i2 & 1, (i2 & 3) != 2)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.layout.Box (Box.kt:232)");
            }
            MeasurePolicy measurePolicy = EmptyBoxMeasurePolicy;
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
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
            Updater.m336setimpl(composerImpl, measurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.layout.BoxKt$Box$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    BoxKt.Box(Modifier.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void access$placeInBox(Placeable.PlacementScope placementScope, Placeable placeable, Measurable measurable, LayoutDirection layoutDirection, int i, int i2, Alignment alignment) {
        Alignment alignment2;
        Object parentData = measurable.getParentData();
        BoxChildDataNode boxChildDataNode = parentData instanceof BoxChildDataNode ? (BoxChildDataNode) parentData : null;
        Alignment alignment3 = (boxChildDataNode == null || (alignment2 = boxChildDataNode.alignment) == null) ? alignment : alignment2;
        IntSize.Companion companion = IntSize.Companion;
        Placeable.PlacementScope.m626place70tqf50$default(placementScope, placeable, alignment3.mo352alignKFBX0sM((placeable.width << 32) | (placeable.height & 4294967295L), (i << 32) | (i2 & 4294967295L), layoutDirection));
    }

    public static final MutableScatterMap cacheFor(boolean z) {
        MutableScatterMap mutableScatterMap = new MutableScatterMap(9);
        Alignment.Companion.getClass();
        BiasAlignment biasAlignment = Alignment.Companion.TopStart;
        mutableScatterMap.set(biasAlignment, new BoxMeasurePolicy(biasAlignment, z));
        BiasAlignment biasAlignment2 = Alignment.Companion.TopCenter;
        mutableScatterMap.set(biasAlignment2, new BoxMeasurePolicy(biasAlignment2, z));
        BiasAlignment biasAlignment3 = Alignment.Companion.TopEnd;
        mutableScatterMap.set(biasAlignment3, new BoxMeasurePolicy(biasAlignment3, z));
        BiasAlignment biasAlignment4 = Alignment.Companion.CenterStart;
        mutableScatterMap.set(biasAlignment4, new BoxMeasurePolicy(biasAlignment4, z));
        BiasAlignment biasAlignment5 = Alignment.Companion.Center;
        mutableScatterMap.set(biasAlignment5, new BoxMeasurePolicy(biasAlignment5, z));
        BiasAlignment biasAlignment6 = Alignment.Companion.CenterEnd;
        mutableScatterMap.set(biasAlignment6, new BoxMeasurePolicy(biasAlignment6, z));
        BiasAlignment biasAlignment7 = Alignment.Companion.BottomStart;
        mutableScatterMap.set(biasAlignment7, new BoxMeasurePolicy(biasAlignment7, z));
        BiasAlignment biasAlignment8 = Alignment.Companion.BottomCenter;
        mutableScatterMap.set(biasAlignment8, new BoxMeasurePolicy(biasAlignment8, z));
        BiasAlignment biasAlignment9 = Alignment.Companion.BottomEnd;
        mutableScatterMap.set(biasAlignment9, new BoxMeasurePolicy(biasAlignment9, z));
        return mutableScatterMap;
    }

    public static final MeasurePolicy maybeCachedBoxMeasurePolicy(Alignment alignment, boolean z) {
        MeasurePolicy measurePolicy = (MeasurePolicy) (z ? Cache1 : Cache2).get(alignment);
        return measurePolicy == null ? new BoxMeasurePolicy(alignment, z) : measurePolicy;
    }
}
