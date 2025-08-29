package com.android.systemui.people.ui.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.ScrollKt;
import androidx.compose.foundation.ScrollState;
import androidx.compose.foundation.ScrollingContainerKt;
import androidx.compose.foundation.ScrollingLayoutElement;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
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
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.AndroidImageBitmap;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.R;
import com.android.systemui.compose.modifiers.SysuiTestTagKt;
import com.android.systemui.people.ui.viewmodel.PeopleTileViewModel;
import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

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
            float fDimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.people_space_widget_radius, composerImpl);
            float f = 4;
            Dp.Companion companion2 = Dp.Companion;
            Arrangement.INSTANCE.getClass();
            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(2);
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(spacedAlignedM92spacedBy0680j_4, Alignment.Companion.Start, composerImpl, 6);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion);
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
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            String strStringResource = StringResources_androidKt.stringResource(i, composerImpl);
            Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(companion, 16, 0.0f, 0.0f, 8, 6);
            MaterialTheme.INSTANCE.getClass();
            TextKt.m317Text4IGK_g(strStringResource, modifierM129paddingqDBjuR0$default, MaterialTheme.getColorScheme(composerImpl).primary, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).labelLarge, composerImpl, 48, 0, 65528);
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
                m2644Tilevz2T9sI(peopleTileViewModel, function1, i5 == 0 ? fDimensionResource : f, i5 == CollectionsKt__CollectionsKt.getLastIndex(list) ? fDimensionResource : f, null, composerImpl, (i4 >> 3) & 112);
                composerImpl.end(false);
                i5 = i6;
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
            modifier2 = companion;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i2 | 1);
                    Function1 function12 = function1;
                    Modifier modifier3 = modifier2;
                    PeopleScreenKt.ConversationList(i, list, function12, modifier3, (Composer) obj2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void PeopleScreen(final PeopleViewModel peopleViewModel, final Function1 function1, Modifier.Companion companion, Composer composer, final int i) {
        final Modifier.Companion companion2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1150087777);
        int i2 = (composerImpl.changedInstance(peopleViewModel) ? 4 : 2) | i | (composerImpl.changedInstance(function1) ? 32 : 16) | 384;
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
        } else {
            Modifier.Companion companion3 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.people.ui.compose.PeopleScreen (PeopleScreen.kt:67)");
            }
            final MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(peopleViewModel.priorityTiles, composerImpl);
            final MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(peopleViewModel.recentTiles, composerImpl);
            composerImpl.startReplaceGroup(-1947957944);
            boolean zChangedInstance = ((i2 & 112) == 32) | composerImpl.changedInstance(peopleViewModel);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new PeopleScreenKt$PeopleScreen$1$1(peopleViewModel, function1, null);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                EffectsKt.LaunchedEffect(composerImpl, peopleViewModel.result, (Function2) objRememberedValue);
                MaterialTheme.INSTANCE.getClass();
                SurfaceKt.m304SurfaceT9BRK9s(SizeKt.fillMaxSize(companion3, 1.0f), null, MaterialTheme.getColorScheme(composerImpl).background, 0L, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-1665605340, new Function2() { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt.PeopleScreen.2
                    /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
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
                                    ComposerKt.traceEventStart("com.android.systemui.people.ui.compose.PeopleScreen.<anonymous> (PeopleScreen.kt:82)");
                                }
                                float f = PeopleScreenKt.PeopleSpacePadding;
                                State state = mutableStateCollectAsStateWithLifecycle;
                                boolean zIsEmpty = ((List) state.getValue()).isEmpty();
                                PeopleViewModel peopleViewModel2 = peopleViewModel;
                                State state2 = mutableStateCollectAsStateWithLifecycle2;
                                if (zIsEmpty && ((List) state2.getValue()).isEmpty()) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    composerImpl3.startReplaceGroup(-482911097);
                                    PeopleScreenEmptyKt.PeopleScreenEmpty(peopleViewModel2.onUserJourneyCancelled, null, composerImpl3, 0);
                                    composerImpl3.end(false);
                                } else {
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                    composerImpl4.startReplaceGroup(-483021240);
                                    PeopleScreenKt.PeopleScreenWithConversations((List) state.getValue(), (List) state2.getValue(), peopleViewModel2.onTileClicked, null, composerImpl4, 0);
                                    composerImpl4.end(false);
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 12582912, 122);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                companion2 = companion3;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(function1, companion2, i) { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$$ExternalSyntheticLambda0
                public final /* synthetic */ Function1 f$1;
                public final /* synthetic */ Modifier.Companion f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Function1 function12 = this.f$1;
                    Modifier.Companion companion4 = this.f$2;
                    PeopleScreenKt.PeopleScreen(this.f$0, function12, companion4, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void PeopleScreenWithConversations(final List list, final List list2, final Function1 function1, Modifier.Companion companion, Composer composer, final int i) {
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
            Modifier modifierSysuiResTag = SysuiTestTagKt.sysuiResTag(ComposedModifierKt.composed(SizeKt.fillMaxSize(companion3, 1.0f), InspectableValueKt.NoInspectorInfo, new WindowInsetsPadding_androidKt$safeDrawingPadding$$inlined$windowInsetsPadding$1()), "top_level_with_conversations");
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            BiasAlignment.Horizontal horizontal = Alignment.Companion.Start;
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierSysuiResTag);
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
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion3, 1.0f);
            float f = PeopleSpacePadding;
            Modifier modifierM125padding3ABfNKs = PaddingKt.m125padding3ABfNKs(modifierFillMaxWidth, f);
            ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.CenterHorizontally, composerImpl, 48);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierM125padding3ABfNKs);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, columnMeasurePolicy2, function2);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
            String strStringResource = StringResources_androidKt.stringResource(R.string.select_conversation_title, composerImpl);
            MaterialTheme.INSTANCE.getClass();
            TextStyle textStyle = MaterialTheme.getTypography(composerImpl).headlineSmall;
            TextAlign.Companion.getClass();
            int i4 = TextAlign.Center;
            TextKt.m317Text4IGK_g(strStringResource, null, 0L, 0L, null, null, null, 0L, null, TextAlign.m807boximpl(i4), 0L, 0, false, 0, 0, null, textStyle, composerImpl, 0, 0, 65022);
            float f2 = 24;
            Dp.Companion companion4 = Dp.Companion;
            SpacerKt.Spacer(composerImpl, SizeKt.m131height3ABfNKs(companion3, f2));
            TextKt.m317Text4IGK_g(StringResources_androidKt.stringResource(R.string.select_conversation_text, composerImpl), PaddingKt.m127paddingVpY3zN4$default(companion3, f2, 0.0f, 2), 0L, 0L, null, null, null, 0L, null, TextAlign.m807boximpl(i4), 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).bodyLarge, composerImpl, 48, 0, 65020);
            composerImpl.end(true);
            Modifier modifierSysuiResTag2 = SysuiTestTagKt.sysuiResTag(SizeKt.fillMaxWidth(companion3, 1.0f), "scroll_view");
            ScrollState scrollStateRememberScrollState = ScrollKt.rememberScrollState(composerImpl);
            float f3 = 8;
            Modifier modifierM128paddingqDBjuR0 = PaddingKt.m128paddingqDBjuR0(ScrollingContainerKt.scrollingContainer(modifierSysuiResTag2, scrollStateRememberScrollState, Orientation.Vertical, (14 & 2) != 0, false, null, scrollStateRememberScrollState.internalInteractionSource, true, null, null).then(new ScrollingLayoutElement(scrollStateRememberScrollState, false, true)), f3, 16, f3, f);
            ColumnMeasurePolicy columnMeasurePolicy3 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 0);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierM128paddingqDBjuR0);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, columnMeasurePolicy3, function2);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function24);
            boolean zIsEmpty = list.isEmpty();
            composerImpl.startReplaceGroup(107166788);
            if (zIsEmpty) {
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
                if (!zIsEmpty) {
                    SpacerKt.Spacer(composerImpl, SizeKt.m131height3ABfNKs(companion2, 35));
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final Modifier.Companion companion5 = companion2;
            recomposeScopeImplEndRestartGroup.block = new Function2(list, list2, function1, companion5, i) { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$$ExternalSyntheticLambda1
                public final /* synthetic */ List f$0;
                public final /* synthetic */ List f$1;
                public final /* synthetic */ Function1 f$2;
                public final /* synthetic */ Modifier.Companion f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    PeopleScreenKt.PeopleScreenWithConversations(this.f$0, this.f$1, this.f$2, this.f$3, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: Tile-vz2T9sI, reason: not valid java name */
    public static final void m2644Tilevz2T9sI(final PeopleTileViewModel peopleTileViewModel, final Function1 function1, final float f, final float f2, Modifier.Companion companion, Composer composer, final int i) {
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
            SurfaceKt.m304SurfaceT9BRK9s(companion3, RoundedCornerShapeKt.m188RoundedCornerShapea9UjIt4(f, f, f2, f2), MaterialTheme.getColorScheme(composerImpl2).secondaryContainer, 0L, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(1982267689, new Function2() { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$Tile$1
                /* JADX WARN: Removed duplicated region for block: B:15:0x005a  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        if (composerImpl3.getSkipping()) {
                            composerImpl3.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.people.ui.compose.Tile.<anonymous> (PeopleScreen.kt:196)");
                            }
                            Modifier.Companion companion4 = Modifier.Companion;
                            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion4, 1.0f);
                            ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                            composerImpl4.startReplaceGroup(-1796854502);
                            final Function1 function12 = function1;
                            boolean zChanged = composerImpl4.changed(function12);
                            final PeopleTileViewModel peopleTileViewModel2 = peopleTileViewModel;
                            boolean zChangedInstance = zChanged | composerImpl4.changedInstance(peopleTileViewModel2);
                            Object objRememberedValue = composerImpl4.rememberedValue();
                            if (!zChangedInstance) {
                                Composer.Companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new Function0() { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$Tile$1$$ExternalSyntheticLambda0
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            function12.mo781invoke(peopleTileViewModel2);
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl4.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl4.end(false);
                                Dp.Companion companion5 = Dp.Companion;
                                Modifier modifierM125padding3ABfNKs = PaddingKt.m125padding3ABfNKs(ClickableKt.m35clickableXHw0xAI$default(modifierFillMaxWidth, false, null, (Function0) objRememberedValue, 7), 12);
                                Alignment.Companion.getClass();
                                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                                Arrangement.INSTANCE.getClass();
                                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl4, 48);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl4, modifierM125padding3ABfNKs);
                                ComposeUiNode.Companion.getClass();
                                Function0 function0 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl4.applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl4.startReusableNode();
                                if (composerImpl4.inserting) {
                                    composerImpl4.createNode(function0);
                                } else {
                                    composerImpl4.useNode();
                                }
                                Updater.m337setimpl(composerImpl4, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function2);
                                }
                                Updater.m337setimpl(composerImpl4, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                                ImageKt.m42Image5hnEew(new AndroidImageBitmap(peopleTileViewModel2.icon), null, SizeKt.m140size3ABfNKs(companion4, PrimitiveResources_androidKt.dimensionResource(R.dimen.avatar_size_for_medium, composerImpl4)), null, composerImpl4, 48, IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut);
                                String str = peopleTileViewModel2.username;
                                if (str == null) {
                                    str = "";
                                }
                                Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(companion4, 16, 0.0f, 2);
                                MaterialTheme.INSTANCE.getClass();
                                TextKt.m317Text4IGK_g(str, modifierM127paddingVpY3zN4$default, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl4).titleLarge, composerImpl4, 48, 0, 65532);
                                composerImpl4.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2), composerImpl, ((i3 >> 12) & 14) | 12582912, 120);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            companion2 = companion3;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    PeopleTileViewModel peopleTileViewModel2 = peopleTileViewModel;
                    float f3 = f2;
                    Modifier.Companion companion4 = companion2;
                    PeopleScreenKt.m2644Tilevz2T9sI(peopleTileViewModel2, function1, f, f3, companion4, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
