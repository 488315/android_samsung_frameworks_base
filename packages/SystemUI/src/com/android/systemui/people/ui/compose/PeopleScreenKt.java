package com.android.systemui.people.ui.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ScrollKt;
import androidx.compose.foundation.ScrollingContainerKt;
import androidx.compose.foundation.ScrollingLayoutElement;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.layout.WindowInsetsPadding_androidKt$safeDrawingPadding$$inlined$windowInsetsPadding$1;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.material3.TextKt;
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
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import com.android.systemui.compose.modifiers.SysuiTestTagKt;
import com.android.systemui.people.ui.viewmodel.PeopleTileViewModel;
import com.samsung.android.knox.EnterpriseContainerCallback;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class PeopleScreenKt {
    public static final float PeopleSpacePadding;

    static {
        Dp.Companion companion = Dp.Companion;
        PeopleSpacePadding = 24;
    }

    public static final void ConversationList(final int i, final List list, final Function1 function1, Modifier modifier, Composer composer, final int i2) {
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2007697085);
        int i3 = (i2 & 6) == 0 ? (composerImpl.changed(i) ? 4 : 2) | i2 : i2;
        if ((i2 & 48) == 0) {
            i3 |= composerImpl.changedInstance(list) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i3 |= composerImpl.changedInstance(function1) ? 256 : 128;
        }
        int i4 = i3 | 3072;
        if ((i4 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        } else {
            Modifier.Companion companion = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.people.ui.compose.ConversationList (PeopleScreen.kt:148)");
            }
            float dimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.people_space_widget_radius, composerImpl);
            float f = 4;
            Dp.Companion companion2 = Dp.Companion;
            Arrangement.INSTANCE.getClass();
            Arrangement.SpacedAligned m91spacedBy0680j_4 = Arrangement.m91spacedBy0680j_4(2);
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m91spacedBy0680j_4, Alignment.Companion.Start, composerImpl, 6);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion);
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
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            String stringResource = StringResources_androidKt.stringResource(i, composerImpl);
            Modifier m128paddingqDBjuR0$default = PaddingKt.m128paddingqDBjuR0$default(companion, 16, 0.0f, 0.0f, 8, 6);
            MaterialTheme.INSTANCE.getClass();
            TextKt.m316Text4IGK_g(stringResource, m128paddingqDBjuR0$default, MaterialTheme.getColorScheme(composerImpl).primary, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).labelLarge, composerImpl, 48, 0, 65528);
            composerImpl = composerImpl;
            composerImpl.startReplaceGroup(-1762238353);
            int i5 = 0;
            for (Object obj : list) {
                int i6 = i5 + 1;
                if (i5 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                PeopleTileViewModel peopleTileViewModel = (PeopleTileViewModel) obj;
                composerImpl.startMovableGroup(1174402744, peopleTileViewModel.key.toString());
                m2628Tilevz2T9sI(peopleTileViewModel, function1, i5 == 0 ? dimensionResource : f, i5 == CollectionsKt__CollectionsKt.getLastIndex(list) ? dimensionResource : f, null, composerImpl, (i4 >> 3) & 112);
                composerImpl.end(false);
                i5 = i6;
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
            modifier2 = companion;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i2 | 1);
                    Function1 function12 = function1;
                    Modifier modifier3 = modifier2;
                    PeopleScreenKt.ConversationList(i, list, function12, modifier3, (Composer) obj2, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0076, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void PeopleScreen(final com.android.systemui.people.ui.viewmodel.PeopleViewModel r19, final kotlin.jvm.functions.Function1 r20, androidx.compose.ui.Modifier.Companion r21, androidx.compose.runtime.Composer r22, final int r23) {
        /*
            r0 = r19
            r1 = r20
            r2 = r23
            r13 = r22
            androidx.compose.runtime.ComposerImpl r13 = (androidx.compose.runtime.ComposerImpl) r13
            r3 = -1150087777(0xffffffffbb730d9f, float:-0.0037086976)
            r13.startRestartGroup(r3)
            boolean r3 = r13.changedInstance(r0)
            if (r3 == 0) goto L18
            r3 = 4
            goto L19
        L18:
            r3 = 2
        L19:
            r3 = r3 | r2
            boolean r4 = r13.changedInstance(r1)
            r5 = 32
            if (r4 == 0) goto L24
            r4 = r5
            goto L26
        L24:
            r4 = 16
        L26:
            r3 = r3 | r4
            r3 = r3 | 384(0x180, float:5.38E-43)
            r4 = r3 & 147(0x93, float:2.06E-43)
            r6 = 146(0x92, float:2.05E-43)
            if (r4 != r6) goto L3d
            boolean r4 = r13.getSkipping()
            if (r4 != 0) goto L36
            goto L3d
        L36:
            r13.skipToGroupEnd()
            r3 = r21
            goto Lca
        L3d:
            androidx.compose.ui.Modifier$Companion r4 = androidx.compose.ui.Modifier.Companion
            boolean r6 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r6 == 0) goto L4a
            java.lang.String r6 = "com.android.systemui.people.ui.compose.PeopleScreen (PeopleScreen.kt:67)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r6)
        L4a:
            kotlinx.coroutines.flow.StateFlow r6 = r0.priorityTiles
            androidx.compose.runtime.MutableState r6 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r6, r13)
            kotlinx.coroutines.flow.StateFlow r7 = r0.recentTiles
            androidx.compose.runtime.MutableState r7 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r7, r13)
            r8 = -1947957944(0xffffffff8be48548, float:-8.8022945E-32)
            r13.startReplaceGroup(r8)
            boolean r8 = r13.changedInstance(r0)
            r3 = r3 & 112(0x70, float:1.57E-43)
            r9 = 0
            if (r3 != r5) goto L67
            r3 = 1
            goto L68
        L67:
            r3 = r9
        L68:
            r3 = r3 | r8
            java.lang.Object r5 = r13.rememberedValue()
            if (r3 != 0) goto L78
            androidx.compose.runtime.Composer$Companion r3 = androidx.compose.runtime.Composer.Companion
            r3.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
            if (r5 != r3) goto L81
        L78:
            com.android.systemui.people.ui.compose.PeopleScreenKt$PeopleScreen$1$1 r5 = new com.android.systemui.people.ui.compose.PeopleScreenKt$PeopleScreen$1$1
            r3 = 0
            r5.<init>(r0, r1, r3)
            r13.updateRememberedValue(r5)
        L81:
            kotlin.jvm.functions.Function2 r5 = (kotlin.jvm.functions.Function2) r5
            r13.end(r9)
            kotlinx.coroutines.flow.StateFlow r3 = r0.result
            androidx.compose.runtime.EffectsKt.LaunchedEffect(r13, r3, r5)
            androidx.compose.material3.MaterialTheme r3 = androidx.compose.material3.MaterialTheme.INSTANCE
            r3.getClass()
            androidx.compose.material3.ColorScheme r3 = androidx.compose.material3.MaterialTheme.getColorScheme(r13)
            long r8 = r3.background
            r3 = 1065353216(0x3f800000, float:1.0)
            androidx.compose.ui.Modifier r3 = androidx.compose.foundation.layout.SizeKt.fillMaxSize(r4, r3)
            com.android.systemui.people.ui.compose.PeopleScreenKt$PeopleScreen$2 r5 = new com.android.systemui.people.ui.compose.PeopleScreenKt$PeopleScreen$2
            r5.<init>()
            r6 = -1665605340(0xffffffff9cb8e124, float:-1.2234296E-21)
            androidx.compose.runtime.internal.ComposableLambdaImpl r12 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r6, r5, r13)
            r10 = 0
            r11 = 0
            r5 = r4
            r4 = 0
            r17 = r8
            r9 = r5
            r5 = r17
            r7 = 0
            r14 = r9
            r9 = 0
            r15 = r14
            r14 = 12582912(0xc00000, float:1.7632415E-38)
            r16 = r15
            r15 = 122(0x7a, float:1.71E-43)
            androidx.compose.material3.SurfaceKt.m303SurfaceT9BRK9s(r3, r4, r5, r7, r9, r10, r11, r12, r13, r14, r15)
            boolean r3 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r3 == 0) goto Lc8
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Lc8:
            r3 = r16
        Lca:
            androidx.compose.runtime.RecomposeScopeImpl r4 = r13.endRestartGroup()
            if (r4 == 0) goto Ld7
            com.android.systemui.people.ui.compose.PeopleScreenKt$$ExternalSyntheticLambda0 r5 = new com.android.systemui.people.ui.compose.PeopleScreenKt$$ExternalSyntheticLambda0
            r5.<init>(r1, r3, r2)
            r4.block = r5
        Ld7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.ui.compose.PeopleScreenKt.PeopleScreen(com.android.systemui.people.ui.viewmodel.PeopleViewModel, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier$Companion, androidx.compose.runtime.Composer, int):void");
    }

    public static final void PeopleScreenWithConversations(final List list, final List list2, final Function1 function1, Modifier.Companion companion, Composer composer, final int i) {
        Modifier then;
        Modifier.Companion companion2;
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1843046450);
        int i3 = i | (composerImpl.changedInstance(list) ? 4 : 2) | (composerImpl.changedInstance(list2) ? 32 : 16) | (composerImpl.changedInstance(function1) ? 256 : 128) | 3072;
        if ((i3 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
        } else {
            Modifier.Companion companion3 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.people.ui.compose.PeopleScreenWithConversations (PeopleScreen.kt:96)");
            }
            Modifier sysuiResTag = SysuiTestTagKt.sysuiResTag(ComposedModifierKt.composed(SizeKt.fillMaxSize(companion3, 1.0f), InspectableValueKt.NoInspectorInfo, new WindowInsetsPadding_androidKt$safeDrawingPadding$$inlined$windowInsetsPadding$1()), "top_level_with_conversations");
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            BiasAlignment.Horizontal horizontal = Alignment.Companion.Start;
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, sysuiResTag);
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
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composerImpl, materializeModifier, function24);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(companion3, 1.0f);
            float f = PeopleSpacePadding;
            Modifier m124padding3ABfNKs = PaddingKt.m124padding3ABfNKs(fillMaxWidth, f);
            ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.CenterHorizontally, composerImpl, 48);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, m124padding3ABfNKs);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, columnMeasurePolicy2, function2);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope2, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
            }
            Updater.m336setimpl(composerImpl, materializeModifier2, function24);
            String stringResource = StringResources_androidKt.stringResource(R.string.select_conversation_title, composerImpl);
            MaterialTheme.INSTANCE.getClass();
            TextStyle textStyle = MaterialTheme.getTypography(composerImpl).headlineSmall;
            TextAlign.Companion.getClass();
            int i4 = TextAlign.Center;
            TextKt.m316Text4IGK_g(stringResource, null, 0L, 0L, null, null, null, 0L, null, TextAlign.m805boximpl(i4), 0L, 0, false, 0, 0, null, textStyle, composerImpl, 0, 0, 65022);
            float f2 = 24;
            Dp.Companion companion4 = Dp.Companion;
            SpacerKt.Spacer(composerImpl, SizeKt.m130height3ABfNKs(companion3, f2));
            TextKt.m316Text4IGK_g(StringResources_androidKt.stringResource(R.string.select_conversation_text, composerImpl), PaddingKt.m126paddingVpY3zN4$default(companion3, f2, 0.0f, 2), 0L, 0L, null, null, null, 0L, null, TextAlign.m805boximpl(i4), 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).bodyLarge, composerImpl, 48, 0, 65020);
            composerImpl.end(true);
            then = ScrollingContainerKt.scrollingContainer(SysuiTestTagKt.sysuiResTag(SizeKt.fillMaxWidth(companion3, 1.0f), "scroll_view"), r8, Orientation.Vertical, (r14 & 2) != 0, false, null, r8.internalInteractionSource, true, null, null).then(new ScrollingLayoutElement(ScrollKt.rememberScrollState(composerImpl), false, true));
            float f3 = 8;
            Modifier m127paddingqDBjuR0 = PaddingKt.m127paddingqDBjuR0(then, f3, 16, f3, f);
            ColumnMeasurePolicy columnMeasurePolicy3 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 0);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, m127paddingqDBjuR0);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, columnMeasurePolicy3, function2);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope3, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
            }
            Updater.m336setimpl(composerImpl, materializeModifier3, function24);
            boolean isEmpty = list.isEmpty();
            composerImpl.startReplaceGroup(107166788);
            if (isEmpty) {
                companion2 = companion3;
                composerImpl = composerImpl;
                i2 = i3;
            } else {
                companion2 = companion3;
                composerImpl = composerImpl;
                i2 = i3;
                ConversationList(R.string.priority_conversations, list, function1, null, composerImpl, ((i3 << 3) & 112) | (i3 & 896));
            }
            composerImpl.end(false);
            composerImpl.startReplaceGroup(107171862);
            if (!list2.isEmpty()) {
                composerImpl.startReplaceGroup(107173243);
                if (!isEmpty) {
                    SpacerKt.Spacer(composerImpl, SizeKt.m130height3ABfNKs(companion2, 35));
                }
                composerImpl.end(false);
                ConversationList(R.string.recent_conversations, list2, function1, null, composerImpl, i2 & EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS);
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
            final Modifier.Companion companion5 = companion2;
            endRestartGroup.block = new Function2(list, list2, function1, companion5, i) { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$$ExternalSyntheticLambda1
                public final /* synthetic */ List f$0;
                public final /* synthetic */ List f$1;
                public final /* synthetic */ Function1 f$2;
                public final /* synthetic */ Modifier.Companion f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    PeopleScreenKt.PeopleScreenWithConversations(this.f$0, this.f$1, this.f$2, this.f$3, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: Tile-vz2T9sI, reason: not valid java name */
    public static final void m2628Tilevz2T9sI(final PeopleTileViewModel peopleTileViewModel, final Function1 function1, final float f, final float f2, Modifier.Companion companion, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        final Modifier.Companion companion2;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-821560604);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changedInstance(peopleTileViewModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changedInstance(function1) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changed(f) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl2.changed(f2) ? 2048 : 1024;
        }
        int i3 = i2 | 24576;
        if ((i3 & 9363) == 9362 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            companion2 = companion;
            composerImpl = composerImpl2;
        } else {
            Modifier.Companion companion3 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.people.ui.compose.Tile (PeopleScreen.kt:184)");
            }
            MaterialTheme.INSTANCE.getClass();
            composerImpl = composerImpl2;
            SurfaceKt.m303SurfaceT9BRK9s(companion3, RoundedCornerShapeKt.m187RoundedCornerShapea9UjIt4(f, f, f2, f2), MaterialTheme.getColorScheme(composerImpl2).secondaryContainer, 0L, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(1982267689, new Function2() { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$Tile$1
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0058, code lost:
                
                    if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r31, java.lang.Object r32) {
                    /*
                        Method dump skipped, instructions count: 330
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.ui.compose.PeopleScreenKt$Tile$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl2), composerImpl, ((i3 >> 12) & 14) | 12582912, 120);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            companion2 = companion3;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    PeopleTileViewModel peopleTileViewModel2 = PeopleTileViewModel.this;
                    float f3 = f2;
                    Modifier.Companion companion4 = companion2;
                    PeopleScreenKt.m2628Tilevz2T9sI(peopleTileViewModel2, function1, f, f3, companion4, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
