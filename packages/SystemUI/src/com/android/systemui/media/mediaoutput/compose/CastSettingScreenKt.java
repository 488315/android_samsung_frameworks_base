package com.android.systemui.media.mediaoutput.compose;

import android.content.Context;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyListIntervalContent;
import androidx.compose.material3.DividerKt;
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
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.livedata.LiveDataAdapterKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.theme.ColorKt;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt;
import com.android.systemui.media.mediaoutput.compose.widget.ListsKt;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.List;
import kotlin.Pair;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class CastSettingScreenKt {
    /* JADX WARN: Type inference failed for: r3v24, types: [com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1, kotlin.jvm.internal.Lambda] */
    public static final void CastSettingScreen(final Function0 function0, SettingViewModel settingViewModel, LabsViewModel labsViewModel, Composer composer, final int i, final int i2) {
        int i3;
        int i4;
        SettingViewModel settingViewModel2;
        LabsViewModel labsViewModel2;
        final SettingViewModel settingViewModel3;
        int i5;
        Object failure;
        Object failure2;
        final SettingViewModel settingViewModel4;
        final LabsViewModel labsViewModel3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2058220562);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 14) == 0) {
            i3 = i | (composerImpl.changedInstance(function0) ? 4 : 2);
        } else {
            i3 = i;
        }
        int i6 = i2 & 2;
        if (i6 != 0) {
            i3 |= 16;
        }
        int i7 = i2 & 4;
        if (i7 != 0) {
            i3 |= 128;
        }
        if ((i2 & 6) == 6 && (i3 & 731) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            settingViewModel4 = settingViewModel;
            labsViewModel3 = labsViewModel;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if (i6 != 0) {
                    composerImpl.startReplaceGroup(1487631618);
                    LocalViewModelStoreOwner.INSTANCE.getClass();
                    ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                    if (current == null) {
                        throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                    }
                    CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                    try {
                        int i8 = Result.$r8$clinit;
                        failure2 = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                    } catch (Throwable th) {
                        int i9 = Result.$r8$clinit;
                        failure2 = new Result.Failure(th);
                    }
                    Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure2);
                    if (m2527exceptionOrNullimpl != null) {
                        m2527exceptionOrNullimpl.printStackTrace();
                    }
                    if (failure2 instanceof Result.Failure) {
                        failure2 = null;
                    }
                    ViewModelProvider.Factory factory = (ViewModelProvider.Factory) failure2;
                    if (factory == null) {
                        factory = ViewModelKt.createDaggerViewModelFactory(current);
                    }
                    ViewModel viewModel = ViewModelKt.get(current, SettingViewModel.class, factory, defaultViewModelCreationExtras);
                    composerImpl.end(false);
                    i4 = i3 & (-113);
                    settingViewModel2 = (SettingViewModel) viewModel;
                } else {
                    i4 = i3;
                    settingViewModel2 = settingViewModel;
                }
                if (i7 != 0) {
                    composerImpl.startReplaceGroup(1487631618);
                    LocalViewModelStoreOwner.INSTANCE.getClass();
                    ViewModelStoreOwner current2 = LocalViewModelStoreOwner.getCurrent(composerImpl);
                    if (current2 == null) {
                        throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                    }
                    CreationExtras defaultViewModelCreationExtras2 = current2 instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current2).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                    try {
                        int i10 = Result.$r8$clinit;
                        failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                    } catch (Throwable th2) {
                        int i11 = Result.$r8$clinit;
                        failure = new Result.Failure(th2);
                    }
                    Throwable m2527exceptionOrNullimpl2 = Result.m2527exceptionOrNullimpl(failure);
                    if (m2527exceptionOrNullimpl2 != null) {
                        m2527exceptionOrNullimpl2.printStackTrace();
                    }
                    if (failure instanceof Result.Failure) {
                        failure = null;
                    }
                    ViewModelProvider.Factory factory2 = (ViewModelProvider.Factory) failure;
                    if (factory2 == null) {
                        factory2 = ViewModelKt.createDaggerViewModelFactory(current2);
                    }
                    ViewModel viewModel2 = ViewModelKt.get(current2, LabsViewModel.class, factory2, defaultViewModelCreationExtras2);
                    composerImpl.end(false);
                    i5 = i4 & (-897);
                    labsViewModel2 = (LabsViewModel) viewModel2;
                    settingViewModel3 = settingViewModel2;
                } else {
                    labsViewModel2 = labsViewModel;
                    settingViewModel3 = settingViewModel2;
                    i5 = i4;
                }
            } else {
                composerImpl.skipToGroupEnd();
                if (i6 != 0) {
                    i3 &= -113;
                }
                if (i7 != 0) {
                    i3 &= -897;
                }
                settingViewModel3 = settingViewModel;
                labsViewModel2 = labsViewModel;
                i5 = i3;
            }
            composerImpl.endDefaults();
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            MutableState collectAsState = SnapshotStateKt.collectAsState(labsViewModel2.isSupportSpotifyChromecast, null, null, composerImpl, 56, 2);
            composerImpl.startReplaceGroup(999182730);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = SnapshotStateKt.mutableStateOf(EmptyList.INSTANCE, StructuralEqualityPolicy.INSTANCE);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final MutableState mutableState = (MutableState) rememberedValue;
            composerImpl.end(false);
            ActionBarKt.SecTitle(function0, StringResources_androidKt.stringResource(R.string.cast_setting, composerImpl), ComposableLambdaKt.rememberComposableLambda(872240409, composerImpl, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    PaddingValues paddingValues = (PaddingValues) obj;
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 14) == 0) {
                        intValue |= ((ComposerImpl) composer2).changed(paddingValues) ? 4 : 2;
                    }
                    if ((intValue & 91) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    LayoutDirection layoutDirection = LayoutDirection.Ltr;
                    PaddingValuesImpl m101PaddingValuesa9UjIt4$default = PaddingKt.m101PaddingValuesa9UjIt4$default(PaddingKt.calculateStartPadding(paddingValues, layoutDirection), 0.0f, PaddingKt.calculateEndPadding(paddingValues, layoutDirection), paddingValues.mo89calculateBottomPaddingD9Ej5fM(), 2);
                    Modifier.Companion companion = Modifier.Companion;
                    FillElement fillElement = SizeKt.FillWholeMaxSize;
                    companion.then(fillElement);
                    Modifier m106paddingqDBjuR0$default = PaddingKt.m106paddingqDBjuR0$default(fillElement, 0.0f, paddingValues.mo92calculateTopPaddingD9Ej5fM(), 0.0f, 0.0f, 13);
                    final SettingViewModel settingViewModel5 = SettingViewModel.this;
                    final MutableState mutableState2 = mutableState;
                    LazyDslKt.LazyColumn(m106paddingqDBjuR0$default, null, m101PaddingValuesa9UjIt4$default, false, null, null, null, false, new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj4) {
                            LazyListIntervalContent lazyListIntervalContent = (LazyListIntervalContent) obj4;
                            final SettingViewModel settingViewModel6 = SettingViewModel.this;
                            LazyListIntervalContent.item$default(lazyListIntervalContent, new ComposableLambdaImpl(1243110061, true, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt.CastSettingScreen.1.1.1
                                {
                                    super(3);
                                }

                                /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1$1$1$1, kotlin.jvm.internal.Lambda] */
                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                    Composer composer3 = (Composer) obj6;
                                    if ((((Number) obj7).intValue() & 81) == 16) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                    final SettingViewModel settingViewModel7 = SettingViewModel.this;
                                    ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(1494689250, composer3, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt.CastSettingScreen.1.1.1.1
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public final Object invoke(Object obj8, Object obj9, Object obj10) {
                                            Composer composer4 = (Composer) obj9;
                                            if ((((Number) obj10).intValue() & 81) == 16) {
                                                ComposerImpl composerImpl4 = (ComposerImpl) composer4;
                                                if (composerImpl4.getSkipping()) {
                                                    composerImpl4.skipToGroupEnd();
                                                    return Unit.INSTANCE;
                                                }
                                            }
                                            OpaqueKey opaqueKey4 = ComposerKt.invocation;
                                            MutableState observeAsState = LiveDataAdapterKt.observeAsState(SettingViewModel.this.isCastingPriority, null, composer4);
                                            final SettingViewModel settingViewModel8 = SettingViewModel.this;
                                            Modifier.Companion companion2 = Modifier.Companion;
                                            Arrangement.INSTANCE.getClass();
                                            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                                            Alignment.Companion.getClass();
                                            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composer4, 0);
                                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer4);
                                            ComposerImpl composerImpl5 = (ComposerImpl) composer4;
                                            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl5.currentCompositionLocalScope();
                                            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer4, companion2);
                                            ComposeUiNode.Companion.getClass();
                                            Function0 function02 = ComposeUiNode.Companion.Constructor;
                                            boolean z = composerImpl5.applier instanceof Applier;
                                            if (!z) {
                                                ComposablesKt.invalidApplier();
                                                throw null;
                                            }
                                            composerImpl5.startReusableNode();
                                            if (composerImpl5.inserting) {
                                                composerImpl5.createNode(function02);
                                            } else {
                                                composerImpl5.useNode();
                                            }
                                            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                                            Updater.m276setimpl(composer4, columnMeasurePolicy, function2);
                                            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                                            Updater.m276setimpl(composer4, currentCompositionLocalScope, function22);
                                            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                            if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl5, currentCompositeKeyHash, function23);
                                            }
                                            Function2 function24 = ComposeUiNode.Companion.SetModifier;
                                            Updater.m276setimpl(composer4, materializeModifier, function24);
                                            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                                            Dp.Companion companion3 = Dp.Companion;
                                            float f = 16;
                                            Modifier m106paddingqDBjuR0$default2 = PaddingKt.m106paddingqDBjuR0$default(PaddingKt.m104paddingVpY3zN4$default(companion2, 18, 0.0f, 2), 0.0f, f, 0.0f, 4, 5);
                                            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer4);
                                            PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl5.currentCompositionLocalScope();
                                            Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composer4, m106paddingqDBjuR0$default2);
                                            if (!z) {
                                                ComposablesKt.invalidApplier();
                                                throw null;
                                            }
                                            composerImpl5.startReusableNode();
                                            if (composerImpl5.inserting) {
                                                composerImpl5.createNode(function02);
                                            } else {
                                                composerImpl5.useNode();
                                            }
                                            Updater.m276setimpl(composer4, maybeCachedBoxMeasurePolicy, function2);
                                            Updater.m276setimpl(composer4, currentCompositionLocalScope2, function22);
                                            if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl5, currentCompositeKeyHash2, function23);
                                            }
                                            Updater.m276setimpl(composer4, materializeModifier2, function24);
                                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                            String stringResource = StringResources_androidKt.stringResource(R.string.cast_setting_description, composer4);
                                            composerImpl5.startReplaceGroup(-314729347);
                                            TextStyle.Companion companion4 = TextStyle.Companion;
                                            TextStyle secRegular = TypeKt.getSecRegular();
                                            long mediaPrimaryColor = ColorKt.mediaPrimaryColor(composerImpl5);
                                            TextUnitType.Companion.getClass();
                                            TextStyle m651copyp1EtxEg$default = TextStyle.m651copyp1EtxEg$default(secRegular, mediaPrimaryColor, TextUnitKt.pack(14.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 16777212);
                                            composerImpl5.end(false);
                                            TextKt.m257Text4IGK_g(stringResource, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, m651copyp1EtxEg$default, composer4, 0, 0, 65534);
                                            composerImpl5.end(true);
                                            ListsKt.SecRadioListItem(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1$1$1$1$1$2
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    SettingViewModel.this.setCastingPriority(false);
                                                    return Unit.INSTANCE;
                                                }
                                            }, StringResources_androidKt.stringResource(R.string.audio_mirroring_priority, composer4), StringResources_androidKt.stringResource(R.string.audio_mirroring_priority_description, composer4), ((Boolean) observeAsState.getValue()) != null ? Boolean.valueOf(!r3.booleanValue()) : null, composer4, 0, 0);
                                            DividerKt.m218HorizontalDivider9IZ8Weo(PaddingKt.m106paddingqDBjuR0$default(companion2, 62, 0.0f, f, 0.0f, 10), 1, ColorKt.dividerColor(composer4), composer4, 54, 0);
                                            ListsKt.SecRadioListItem(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1$1$1$1$1$4
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    SettingViewModel.this.setCastingPriority(true);
                                                    return Unit.INSTANCE;
                                                }
                                            }, StringResources_androidKt.stringResource(R.string.casting_priority, composer4), StringResources_androidKt.stringResource(R.string.casting_priority_description, composer4), (Boolean) observeAsState.getValue(), composer4, 0, 0);
                                            composerImpl5.end(true);
                                            return Unit.INSTANCE;
                                        }
                                    }), composer3, 6);
                                    return Unit.INSTANCE;
                                }
                            }));
                            ComposableSingletons$CastSettingScreenKt.INSTANCE.getClass();
                            LazyListIntervalContent.item$default(lazyListIntervalContent, ComposableSingletons$CastSettingScreenKt.f49lambda1);
                            final MutableState mutableState3 = mutableState2;
                            LazyListIntervalContent.item$default(lazyListIntervalContent, new ComposableLambdaImpl(216532965, true, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt.CastSettingScreen.1.1.2
                                {
                                    super(3);
                                }

                                /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1$1$2$1, kotlin.jvm.internal.Lambda] */
                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                    Composer composer3 = (Composer) obj6;
                                    if ((((Number) obj7).intValue() & 81) == 16) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                    final MutableState mutableState4 = MutableState.this;
                                    ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(1465965018, composer3, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt.CastSettingScreen.1.1.2.1
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public final Object invoke(Object obj8, Object obj9, Object obj10) {
                                            Composer composer4 = (Composer) obj9;
                                            if ((((Number) obj10).intValue() & 81) == 16) {
                                                ComposerImpl composerImpl4 = (ComposerImpl) composer4;
                                                if (composerImpl4.getSkipping()) {
                                                    composerImpl4.skipToGroupEnd();
                                                    return Unit.INSTANCE;
                                                }
                                            }
                                            OpaqueKey opaqueKey4 = ComposerKt.invocation;
                                            List list = (List) MutableState.this.getValue();
                                            Unit unit = null;
                                            if (!(!list.isEmpty())) {
                                                list = null;
                                            }
                                            ComposerImpl composerImpl5 = (ComposerImpl) composer4;
                                            composerImpl5.startReplaceGroup(-41605868);
                                            if (list != null) {
                                                int i12 = 0;
                                                for (Object obj11 : list) {
                                                    int i13 = i12 + 1;
                                                    if (i12 < 0) {
                                                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                                                        throw null;
                                                    }
                                                    Pair pair = (Pair) obj11;
                                                    Painter painter = (Painter) pair.component1();
                                                    String str = (String) pair.component2();
                                                    composerImpl5.startReplaceGroup(-41605800);
                                                    if (i12 > 0) {
                                                        Dp.Companion companion2 = Dp.Companion;
                                                        DividerKt.m218HorizontalDivider9IZ8Weo(PaddingKt.m106paddingqDBjuR0$default(Modifier.Companion, 70, 0.0f, 16, 0.0f, 10), 1, ColorKt.dividerColor(composerImpl5), composerImpl5, 54, 0);
                                                    }
                                                    composerImpl5.end(false);
                                                    ListsKt.SecIconListItem(null, str, null, painter, composerImpl5, 4096, 5);
                                                    i12 = i13;
                                                }
                                                unit = Unit.INSTANCE;
                                            }
                                            composerImpl5.end(false);
                                            if (unit == null) {
                                                ListsKt.EmptyListItem(StringResources_androidKt.stringResource(R.string.cast_setting_apps_not_installed, composerImpl5), composerImpl5, 0);
                                            }
                                            OpaqueKey opaqueKey5 = ComposerKt.invocation;
                                            return Unit.INSTANCE;
                                        }
                                    }), composer3, 6);
                                    return Unit.INSTANCE;
                                }
                            }));
                            return Unit.INSTANCE;
                        }
                    }, composer2, 0, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
                    return Unit.INSTANCE;
                }
            }), composerImpl, (i5 & 14) | 384, 0);
            EffectsKt.LaunchedEffect(composerImpl, (Boolean) collectAsState.getValue(), new CastSettingScreenKt$CastSettingScreen$2(context, collectAsState, mutableState, null));
            settingViewModel4 = settingViewModel3;
            labsViewModel3 = labsViewModel2;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CastSettingScreenKt.CastSettingScreen(Function0.this, settingViewModel4, labsViewModel3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
