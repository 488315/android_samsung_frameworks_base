package androidx.compose.ui.window;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Constraints;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AndroidDialog_androidKt {
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0135, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0154, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0189, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L75;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x019e  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x004b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void Dialog(final kotlin.jvm.functions.Function0 r22, androidx.compose.ui.window.DialogProperties r23, final kotlin.jvm.functions.Function2 r24, androidx.compose.runtime.Composer r25, final int r26, final int r27) {
        /*
            Method dump skipped, instructions count: 441
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.window.AndroidDialog_androidKt.Dialog(kotlin.jvm.functions.Function0, androidx.compose.ui.window.DialogProperties, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int, int):void");
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
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
            Updater.m336setimpl(composerImpl, new MeasurePolicy() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$DialogLayout$1
                @Override // androidx.compose.ui.layout.MeasurePolicy
                /* renamed from: measure-3p2s80s */
                public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
                    MeasureResult layout$1;
                    final ArrayList arrayList = new ArrayList(list.size());
                    int size = list.size();
                    int i7 = 0;
                    int i8 = 0;
                    for (int i9 = 0; i9 < size; i9++) {
                        Placeable mo608measureBRTryo0 = ((Measurable) list.get(i9)).mo608measureBRTryo0(j);
                        i7 = Math.max(i7, mo608measureBRTryo0.width);
                        i8 = Math.max(i8, mo608measureBRTryo0.height);
                        arrayList.add(mo608measureBRTryo0);
                    }
                    if (list.isEmpty()) {
                        i7 = Constraints.m823getMinWidthimpl(j);
                        i8 = Constraints.m822getMinHeightimpl(j);
                    }
                    layout$1 = measureScope.layout$1(i7, i8, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$DialogLayout$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                            List<Placeable> list2 = arrayList;
                            int size2 = list2.size();
                            for (int i10 = 0; i10 < size2; i10++) {
                                placementScope.placeRelative(list2.get(i10), 0, 0, 0.0f);
                            }
                            return Unit.INSTANCE;
                        }
                    });
                    return layout$1;
                }
            }, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function22);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            function2.invoke(composerImpl, Integer.valueOf((i6 >> 6) & 14));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$DialogLayout$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    AndroidDialog_androidKt.access$DialogLayout(Modifier.this, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
