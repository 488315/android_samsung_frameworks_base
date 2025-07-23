package androidx.compose.material3;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.layout.SubcomposeLayoutKt;
import androidx.compose.ui.layout.SubcomposeMeasureScope;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ScaffoldKt {
    public static final float FabSpacing;

    static {
        Dp.Companion companion = Dp.Companion;
        FabSpacing = 16;
    }

    /* JADX WARN: Code restructure failed: missing block: B:89:0x0241, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L179;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0270, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L191;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x02c8  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x026b  */
    /* renamed from: Scaffold-TvnljyQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m281ScaffoldTvnljyQ(androidx.compose.ui.Modifier r24, kotlin.jvm.functions.Function2 r25, kotlin.jvm.functions.Function2 r26, kotlin.jvm.functions.Function2 r27, kotlin.jvm.functions.Function2 r28, int r29, long r30, long r32, androidx.compose.foundation.layout.WindowInsets r34, final kotlin.jvm.functions.Function3 r35, androidx.compose.runtime.Composer r36, final int r37, final int r38) {
        /*
            Method dump skipped, instructions count: 747
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.ScaffoldKt.m281ScaffoldTvnljyQ(androidx.compose.ui.Modifier, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, int, long, long, androidx.compose.foundation.layout.WindowInsets, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int):void");
    }

    /* renamed from: access$ScaffoldLayout-FMILGgc, reason: not valid java name */
    public static final void m282access$ScaffoldLayoutFMILGgc(final int i, final Function2 function2, final Function3 function3, final Function2 function22, final Function2 function23, final WindowInsets windowInsets, final Function2 function24, Composer composer, final int i2) {
        int i3;
        Function3 function32;
        WindowInsets windowInsets2;
        Function2 function25;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-975511942);
        if ((i2 & 6) == 0) {
            i3 = (composerImpl.changed(i) ? 4 : 2) | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            i3 |= composerImpl.changedInstance(function2) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            function32 = function3;
            i3 |= composerImpl.changedInstance(function32) ? 256 : 128;
        } else {
            function32 = function3;
        }
        if ((i2 & 3072) == 0) {
            i3 |= composerImpl.changedInstance(function22) ? 2048 : 1024;
        }
        if ((i2 & 24576) == 0) {
            i3 |= composerImpl.changedInstance(function23) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i2) == 0) {
            windowInsets2 = windowInsets;
            i3 |= composerImpl.changed(windowInsets2) ? 131072 : 65536;
        } else {
            windowInsets2 = windowInsets;
        }
        if ((1572864 & i2) == 0) {
            function25 = function24;
            i3 |= composerImpl.changedInstance(function25) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        } else {
            function25 = function24;
        }
        if ((i3 & 599187) == 599186 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.ScaffoldLayout (Scaffold.kt:138)");
            }
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = new ScaffoldKt$ScaffoldLayout$contentPadding$1$1();
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final ScaffoldKt$ScaffoldLayout$contentPadding$1$1 scaffoldKt$ScaffoldLayout$contentPadding$1$1 = (ScaffoldKt$ScaffoldLayout$contentPadding$1$1) rememberedValue;
            boolean z = ((i3 & 896) == 256) | ((i3 & 112) == 32) | ((i3 & 458752) == 131072) | ((i3 & 7168) == 2048) | ((57344 & i3) == 16384) | ((i3 & 14) == 4) | ((3670016 & i3) == 1048576);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (z || rememberedValue2 == composer$Companion$Empty$1) {
                final Function3 function33 = function32;
                final WindowInsets windowInsets3 = windowInsets2;
                final Function2 function26 = function25;
                Function2 function27 = new Function2() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        int mo51roundToPx0680j_4;
                        int mo51roundToPx0680j_42;
                        final FabPlacement fabPlacement;
                        Integer num;
                        int i4;
                        MeasureResult layout$1;
                        int intValue;
                        int mo51roundToPx0680j_43;
                        final SubcomposeMeasureScope subcomposeMeasureScope = (SubcomposeMeasureScope) obj;
                        long j = ((Constraints) obj2).value;
                        final int m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(j);
                        final int m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j);
                        long m814copyZbe2FdA$default = Constraints.m814copyZbe2FdA$default(j, 0, 0, 0, 0, 10);
                        int left = WindowInsets.this.getLeft(subcomposeMeasureScope, subcomposeMeasureScope.getLayoutDirection());
                        int right = WindowInsets.this.getRight(subcomposeMeasureScope, subcomposeMeasureScope.getLayoutDirection());
                        int bottom = WindowInsets.this.getBottom(subcomposeMeasureScope);
                        ScaffoldLayoutContent scaffoldLayoutContent = ScaffoldLayoutContent.TopBar;
                        final Function2 function28 = function2;
                        final Placeable mo608measureBRTryo0 = ((Measurable) CollectionsKt___CollectionsKt.first(subcomposeMeasureScope.subcompose(scaffoldLayoutContent, new ComposableLambdaImpl(821838737, true, new Function2() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$1$1$topBarPlaceable$1
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                if ((((Number) obj4).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.ScaffoldLayout.<anonymous>.<anonymous>.<anonymous> (Scaffold.kt:171)");
                                }
                                Function2 function29 = Function2.this;
                                Modifier.Companion companion = Modifier.Companion;
                                Alignment.Companion.getClass();
                                MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
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
                                Updater.m336setimpl(composer2, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m336setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function210 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function210);
                                }
                                Updater.m336setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                function29.invoke(composer2, 0);
                                composerImpl3.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        })))).mo608measureBRTryo0(m814copyZbe2FdA$default);
                        ScaffoldLayoutContent scaffoldLayoutContent2 = ScaffoldLayoutContent.Snackbar;
                        final Function2 function29 = function22;
                        int i5 = (-left) - right;
                        int i6 = -bottom;
                        final Placeable mo608measureBRTryo02 = ((Measurable) CollectionsKt___CollectionsKt.first(subcomposeMeasureScope.subcompose(scaffoldLayoutContent2, new ComposableLambdaImpl(83362666, true, new Function2() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$1$1$snackbarPlaceable$1
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                if ((((Number) obj4).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.ScaffoldLayout.<anonymous>.<anonymous>.<anonymous> (Scaffold.kt:176)");
                                }
                                Function2 function210 = Function2.this;
                                Modifier.Companion companion = Modifier.Companion;
                                Alignment.Companion.getClass();
                                MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
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
                                Updater.m336setimpl(composer2, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m336setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function211 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function211);
                                }
                                Updater.m336setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                function210.invoke(composer2, 0);
                                composerImpl3.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        })))).mo608measureBRTryo0(ConstraintsKt.m833offsetNN6EwU(i5, i6, m814copyZbe2FdA$default));
                        ScaffoldLayoutContent scaffoldLayoutContent3 = ScaffoldLayoutContent.Fab;
                        final Function2 function210 = function23;
                        final Placeable mo608measureBRTryo03 = ((Measurable) CollectionsKt___CollectionsKt.first(subcomposeMeasureScope.subcompose(scaffoldLayoutContent3, new ComposableLambdaImpl(1546204780, true, new Function2() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$1$1$fabPlaceable$1
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                if ((((Number) obj4).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.ScaffoldLayout.<anonymous>.<anonymous>.<anonymous> (Scaffold.kt:181)");
                                }
                                Function2 function211 = Function2.this;
                                Modifier.Companion companion = Modifier.Companion;
                                Alignment.Companion.getClass();
                                MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
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
                                Updater.m336setimpl(composer2, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m336setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function212 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function212);
                                }
                                Updater.m336setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                function211.invoke(composer2, 0);
                                composerImpl3.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        })))).mo608measureBRTryo0(ConstraintsKt.m833offsetNN6EwU(i5, i6, m814copyZbe2FdA$default));
                        int i7 = mo608measureBRTryo03.width;
                        if (i7 == 0 && mo608measureBRTryo03.height == 0) {
                            fabPlacement = null;
                        } else {
                            int i8 = mo608measureBRTryo03.height;
                            int i9 = i;
                            FabPosition.Companion.getClass();
                            if (i9 != 0) {
                                if (i9 != FabPosition.End && i9 != FabPosition.EndOverlay) {
                                    mo51roundToPx0680j_4 = (m821getMaxWidthimpl - i7) / 2;
                                } else if (subcomposeMeasureScope.getLayoutDirection() == LayoutDirection.Ltr) {
                                    mo51roundToPx0680j_42 = subcomposeMeasureScope.mo51roundToPx0680j_4(ScaffoldKt.FabSpacing);
                                    mo51roundToPx0680j_4 = (m821getMaxWidthimpl - mo51roundToPx0680j_42) - i7;
                                } else {
                                    mo51roundToPx0680j_4 = subcomposeMeasureScope.mo51roundToPx0680j_4(ScaffoldKt.FabSpacing);
                                }
                                fabPlacement = new FabPlacement(mo51roundToPx0680j_4, i7, i8);
                            } else if (subcomposeMeasureScope.getLayoutDirection() == LayoutDirection.Ltr) {
                                mo51roundToPx0680j_4 = subcomposeMeasureScope.mo51roundToPx0680j_4(ScaffoldKt.FabSpacing);
                                fabPlacement = new FabPlacement(mo51roundToPx0680j_4, i7, i8);
                            } else {
                                mo51roundToPx0680j_42 = subcomposeMeasureScope.mo51roundToPx0680j_4(ScaffoldKt.FabSpacing);
                                mo51roundToPx0680j_4 = (m821getMaxWidthimpl - mo51roundToPx0680j_42) - i7;
                                fabPlacement = new FabPlacement(mo51roundToPx0680j_4, i7, i8);
                            }
                        }
                        ScaffoldLayoutContent scaffoldLayoutContent4 = ScaffoldLayoutContent.BottomBar;
                        final Function2 function211 = function26;
                        final Placeable mo608measureBRTryo04 = ((Measurable) CollectionsKt___CollectionsKt.first(subcomposeMeasureScope.subcompose(scaffoldLayoutContent4, new ComposableLambdaImpl(1868541227, true, new Function2() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$1$1$bottomBarPlaceable$1
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                if ((((Number) obj4).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.ScaffoldLayout.<anonymous>.<anonymous>.<anonymous> (Scaffold.kt:217)");
                                }
                                Function2 function212 = Function2.this;
                                Modifier.Companion companion = Modifier.Companion;
                                Alignment.Companion.getClass();
                                MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
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
                                Updater.m336setimpl(composer2, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m336setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function213 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function213);
                                }
                                Updater.m336setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                function212.invoke(composer2, 0);
                                composerImpl3.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        })))).mo608measureBRTryo0(m814copyZbe2FdA$default);
                        boolean z2 = mo608measureBRTryo04.width == 0 && mo608measureBRTryo04.height == 0;
                        if (fabPlacement != null) {
                            int i10 = i;
                            WindowInsets windowInsets4 = WindowInsets.this;
                            int i11 = fabPlacement.height;
                            if (!z2) {
                                FabPosition.Companion.getClass();
                                if (i10 != FabPosition.EndOverlay) {
                                    mo51roundToPx0680j_43 = subcomposeMeasureScope.mo51roundToPx0680j_4(ScaffoldKt.FabSpacing) + mo608measureBRTryo04.height + i11;
                                    num = Integer.valueOf(mo51roundToPx0680j_43);
                                }
                            }
                            mo51roundToPx0680j_43 = subcomposeMeasureScope.mo51roundToPx0680j_4(ScaffoldKt.FabSpacing) + i11 + windowInsets4.getBottom(subcomposeMeasureScope);
                            num = Integer.valueOf(mo51roundToPx0680j_43);
                        } else {
                            num = null;
                        }
                        int i12 = mo608measureBRTryo02.height;
                        if (i12 != 0) {
                            if (num != null) {
                                intValue = num.intValue();
                            } else {
                                Integer valueOf = Integer.valueOf(mo608measureBRTryo04.height);
                                if (z2) {
                                    valueOf = null;
                                }
                                intValue = valueOf != null ? valueOf.intValue() : WindowInsets.this.getBottom(subcomposeMeasureScope);
                            }
                            i4 = intValue + i12;
                        } else {
                            i4 = 0;
                        }
                        PaddingValues asPaddingValues = WindowInsetsKt.asPaddingValues(WindowInsets.this, subcomposeMeasureScope);
                        ((SnapshotMutableStateImpl) scaffoldKt$ScaffoldLayout$contentPadding$1$1.paddingHolder$delegate).setValue(PaddingKt.m122PaddingValuesa9UjIt4(PaddingKt.calculateStartPadding(asPaddingValues, subcomposeMeasureScope.getLayoutDirection()), (mo608measureBRTryo0.width == 0 && mo608measureBRTryo0.height == 0) ? asPaddingValues.mo112calculateTopPaddingD9Ej5fM() : subcomposeMeasureScope.mo54toDpu2uoSUM(mo608measureBRTryo0.height), PaddingKt.calculateEndPadding(asPaddingValues, subcomposeMeasureScope.getLayoutDirection()), z2 ? asPaddingValues.mo109calculateBottomPaddingD9Ej5fM() : subcomposeMeasureScope.mo54toDpu2uoSUM(mo608measureBRTryo04.height)));
                        ScaffoldLayoutContent scaffoldLayoutContent5 = ScaffoldLayoutContent.MainContent;
                        final Function3 function34 = function33;
                        final ScaffoldKt$ScaffoldLayout$contentPadding$1$1 scaffoldKt$ScaffoldLayout$contentPadding$1$12 = scaffoldKt$ScaffoldLayout$contentPadding$1$1;
                        final Placeable mo608measureBRTryo05 = ((Measurable) CollectionsKt___CollectionsKt.first(subcomposeMeasureScope.subcompose(scaffoldLayoutContent5, new ComposableLambdaImpl(906691836, true, new Function2() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$1$1$bodyContentPlaceable$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                if ((((Number) obj4).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.ScaffoldLayout.<anonymous>.<anonymous>.<anonymous> (Scaffold.kt:268)");
                                }
                                Function3 function35 = Function3.this;
                                ScaffoldKt$ScaffoldLayout$contentPadding$1$1 scaffoldKt$ScaffoldLayout$contentPadding$1$13 = scaffoldKt$ScaffoldLayout$contentPadding$1$12;
                                Modifier.Companion companion = Modifier.Companion;
                                Alignment.Companion.getClass();
                                MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
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
                                Updater.m336setimpl(composer2, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m336setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function212 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function212);
                                }
                                Updater.m336setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                function35.invoke(scaffoldKt$ScaffoldLayout$contentPadding$1$13, composer2, 6);
                                composerImpl3.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        })))).mo608measureBRTryo0(m814copyZbe2FdA$default);
                        final WindowInsets windowInsets5 = WindowInsets.this;
                        final Integer num2 = num;
                        final int i13 = i4;
                        layout$1 = subcomposeMeasureScope.layout$1(m821getMaxWidthimpl, m820getMaxHeightimpl, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$1$1.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo779invoke(Object obj3) {
                                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj3;
                                placementScope.place(Placeable.this, 0, 0, 0.0f);
                                placementScope.place(mo608measureBRTryo0, 0, 0, 0.0f);
                                Placeable placeable = mo608measureBRTryo02;
                                int i14 = (m821getMaxWidthimpl - placeable.width) / 2;
                                WindowInsets windowInsets6 = windowInsets5;
                                SubcomposeMeasureScope subcomposeMeasureScope2 = subcomposeMeasureScope;
                                placementScope.place(placeable, windowInsets6.getLeft(subcomposeMeasureScope2, subcomposeMeasureScope2.getLayoutDirection()) + i14, m820getMaxHeightimpl - i13, 0.0f);
                                Placeable placeable2 = mo608measureBRTryo04;
                                placementScope.place(placeable2, 0, m820getMaxHeightimpl - placeable2.height, 0.0f);
                                FabPlacement fabPlacement2 = fabPlacement;
                                if (fabPlacement2 != null) {
                                    Placeable placeable3 = mo608measureBRTryo03;
                                    int i15 = m820getMaxHeightimpl;
                                    Integer num3 = num2;
                                    num3.getClass();
                                    placementScope.place(placeable3, fabPlacement2.left, i15 - num3.intValue(), 0.0f);
                                }
                                return Unit.INSTANCE;
                            }
                        });
                        return layout$1;
                    }
                };
                composerImpl.updateRememberedValue(function27);
                rememberedValue2 = function27;
            }
            SubcomposeLayoutKt.SubcomposeLayout(null, (Function2) rememberedValue2, composerImpl, 0, 1);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ScaffoldKt.m282access$ScaffoldLayoutFMILGgc(i, function2, function3, function22, function23, windowInsets, function24, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i2 | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
