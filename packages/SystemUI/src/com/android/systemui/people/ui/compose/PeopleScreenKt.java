package com.android.systemui.people.ui.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.DividerKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
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
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.compose.theme.AndroidColorScheme;
import com.android.compose.theme.AndroidColorSchemeKt;
import com.android.systemui.R;
import com.android.systemui.people.ui.viewmodel.PeopleTileViewModel;
import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

public abstract class PeopleScreenKt {
    public static final float PeopleSpacePadding;

    static {
        Dp.Companion companion = Dp.Companion;
        PeopleSpacePadding = 24;
    }

    public static final void ConversationList(final int i, final List list, final Function1 function1, Composer composer, final int i2) {
        PeopleTileViewModel peopleTileViewModel;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1586868510);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        String stringResource = StringResources_androidKt.stringResource(i, composerImpl);
        Modifier.Companion companion = Modifier.Companion;
        Dp.Companion companion2 = Dp.Companion;
        Modifier m106paddingqDBjuR0$default = PaddingKt.m106paddingqDBjuR0$default(companion, 16, 0.0f, 0.0f, 0.0f, 14);
        MaterialTheme.INSTANCE.getClass();
        TextKt.m257Text4IGK_g(stringResource, m106paddingqDBjuR0$default, ((AndroidColorScheme) composerImpl.consume(AndroidColorSchemeKt.LocalAndroidColorScheme)).deprecated.colorAccentPrimaryVariant, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).labelLarge, composerImpl, 48, 0, 65528);
        SpacerKt.Spacer(composerImpl, SizeKt.m108height3ABfNKs(companion, 10));
        int i3 = 0;
        for (Object obj : list) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            PeopleTileViewModel peopleTileViewModel2 = (PeopleTileViewModel) obj;
            composerImpl.startReplaceGroup(-588347591);
            if (i3 > 0) {
                peopleTileViewModel = peopleTileViewModel2;
                DividerKt.m217Divider9IZ8Weo(null, 2, ((AndroidColorScheme) composerImpl.consume(AndroidColorSchemeKt.LocalAndroidColorScheme)).deprecated.colorBackground, composerImpl, 48, 1);
            } else {
                peopleTileViewModel = peopleTileViewModel2;
            }
            composerImpl.end(false);
            composerImpl.startMovableGroup(-749583209, peopleTileViewModel.key.toString());
            Tile(peopleTileViewModel, function1, i3 == 0, i3 == CollectionsKt__CollectionsKt.getLastIndex(list), composerImpl, ((i2 >> 3) & 112) | 8);
            composerImpl.end(false);
            i3 = i4;
        }
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$ConversationList$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    PeopleScreenKt.ConversationList(i, list, function1, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i2 | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void PeopleScreen(final PeopleViewModel peopleViewModel, final Function1 function1, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(825885692);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(peopleViewModel.priorityTiles, composerImpl);
        final MutableState collectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(peopleViewModel.recentTiles, composerImpl);
        EffectsKt.LaunchedEffect(composerImpl, peopleViewModel.result, new PeopleScreenKt$PeopleScreen$1(peopleViewModel, function1, null));
        AndroidColorScheme.DeprecatedValues deprecatedValues = ((AndroidColorScheme) composerImpl.consume(AndroidColorSchemeKt.LocalAndroidColorScheme)).deprecated;
        long j = deprecatedValues.colorBackground;
        Modifier.Companion companion = Modifier.Companion;
        FillElement fillElement = SizeKt.FillWholeMaxSize;
        companion.then(fillElement);
        SurfaceKt.m248SurfaceT9BRK9s(fillElement, null, j, deprecatedValues.textColorPrimary, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(899692247, composerImpl, new Function2() { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$PeopleScreen$2
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer2 = (Composer) obj;
                if ((((Number) obj2).intValue() & 11) == 2) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                State state = collectAsStateWithLifecycle;
                float f = PeopleScreenKt.PeopleSpacePadding;
                if ((!((List) state.getValue()).isEmpty()) || (!((List) collectAsStateWithLifecycle2.getValue()).isEmpty())) {
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    composerImpl3.startReplaceGroup(-541284050);
                    PeopleScreenKt.access$PeopleScreenWithConversations((List) collectAsStateWithLifecycle.getValue(), (List) collectAsStateWithLifecycle2.getValue(), PeopleViewModel.this.onTileClicked, composerImpl3, 72);
                    composerImpl3.end(false);
                } else {
                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                    composerImpl4.startReplaceGroup(-541283938);
                    PeopleScreenEmptyKt.PeopleScreenEmpty(PeopleViewModel.this.onUserJourneyCancelled, composerImpl4, 0);
                    composerImpl4.end(false);
                }
                return Unit.INSTANCE;
            }
        }), composerImpl, 12582918, 114);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$PeopleScreen$3
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    PeopleScreenKt.PeopleScreen(PeopleViewModel.this, function1, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void Tile(final PeopleTileViewModel peopleTileViewModel, final Function1 function1, final boolean z, final boolean z2, Composer composer, final int i) {
        float f;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1505732001);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AndroidColorScheme.DeprecatedValues deprecatedValues = ((AndroidColorScheme) composerImpl.consume(AndroidColorSchemeKt.LocalAndroidColorScheme)).deprecated;
        float dimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.people_space_widget_radius, composerImpl);
        if (z) {
            f = dimensionResource;
        } else {
            f = 0;
            Dp.Companion companion = Dp.Companion;
        }
        if (!z2) {
            dimensionResource = 0;
            Dp.Companion companion2 = Dp.Companion;
        }
        SurfaceKt.m248SurfaceT9BRK9s(null, RoundedCornerShapeKt.m152RoundedCornerShapea9UjIt4(f, f, dimensionResource, dimensionResource), deprecatedValues.colorSurface, deprecatedValues.textColorPrimary, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-486400838, composerImpl, new Function2() { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$Tile$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer2 = (Composer) obj;
                if ((((Number) obj2).intValue() & 11) == 2) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                Modifier.Companion companion3 = Modifier.Companion;
                Modifier fillMaxWidth = SizeKt.fillMaxWidth(companion3, 1.0f);
                final Function1 function12 = Function1.this;
                final PeopleTileViewModel peopleTileViewModel2 = peopleTileViewModel;
                Dp.Companion companion4 = Dp.Companion;
                Modifier m102padding3ABfNKs = PaddingKt.m102padding3ABfNKs(ClickableKt.m31clickableXHw0xAI$default(fillMaxWidth, false, null, new Function0() { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$Tile$1.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Function1.this.invoke(peopleTileViewModel2);
                        return Unit.INSTANCE;
                    }
                }, 7), 12);
                Alignment.Companion.getClass();
                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                PeopleTileViewModel peopleTileViewModel3 = peopleTileViewModel;
                Arrangement.INSTANCE.getClass();
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composer2, 48);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, m102padding3ABfNKs);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                if (!(composerImpl3.applier instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl3.startReusableNode();
                if (composerImpl3.inserting) {
                    composerImpl3.createNode(function0);
                } else {
                    composerImpl3.useNode();
                }
                Updater.m276setimpl(composer2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m276setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                }
                Updater.m276setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                ImageKt.m34Image5hnEew(new AndroidImageBitmap(peopleTileViewModel3.icon), null, SizeKt.m115size3ABfNKs(companion3, PrimitiveResources_androidKt.dimensionResource(R.dimen.avatar_size_for_medium, composer2)), null, composer2, 56, IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut);
                String str = peopleTileViewModel3.username;
                if (str == null) {
                    str = "";
                }
                Modifier m104paddingVpY3zN4$default = PaddingKt.m104paddingVpY3zN4$default(companion3, 16, 0.0f, 2);
                MaterialTheme.INSTANCE.getClass();
                TextKt.m257Text4IGK_g(str, m104paddingVpY3zN4$default, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composer2).titleLarge, composer2, 48, 0, 65532);
                composerImpl3.end(true);
                return Unit.INSTANCE;
            }
        }), composerImpl, 12582912, 113);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$Tile$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    PeopleScreenKt.Tile(PeopleTileViewModel.this, function1, z, z2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0075, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r4.rememberedValue(), java.lang.Integer.valueOf(r7)) == false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$PeopleScreenWithConversations(final java.util.List r42, final java.util.List r43, final kotlin.jvm.functions.Function1 r44, androidx.compose.runtime.Composer r45, final int r46) {
        /*
            Method dump skipped, instructions count: 642
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.ui.compose.PeopleScreenKt.access$PeopleScreenWithConversations(java.util.List, java.util.List, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int):void");
    }
}
