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
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyListScope;
import androidx.compose.material3.DividerKt;
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
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.compose.FlowExtKt;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.ext.CharSequenceExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.theme.ColorKt;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt;
import com.android.systemui.media.mediaoutput.compose.widget.ListsKt;
import com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import java.util.ArrayList;
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
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public abstract class CastSettingScreenKt {
    public static final void CastSettingScreen(Function0 function0, final SettingViewModel settingViewModel, Composer composer, final int i) {
        Object failure;
        final Function0 function02;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1106898304);
        int i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i | 16;
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            function02 = function0;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i3 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i4 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
                if (thM3441exceptionOrNullimpl != null) {
                    thM3441exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory = (ViewModelProvider.Factory) failure;
                if (factoryCreateDaggerViewModelFactory == null) {
                    factoryCreateDaggerViewModelFactory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, SettingViewModel.class, factoryCreateDaggerViewModelFactory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                settingViewModel = (SettingViewModel) viewModel;
            } else {
                composerImpl.skipToGroupEnd();
            }
            int i5 = i2 & (-113);
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.CastSettingScreen (CastSettingScreen.kt:51)");
            }
            Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl.startReplaceGroup(909905358);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (objRememberedValue == obj) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(EmptyList.INSTANCE);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            final MutableState mutableState = (MutableState) objRememberedValue;
            composerImpl.end(false);
            final MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(settingViewModel.isSupportSpotifyMediaProvider, null, composerImpl, 48);
            function02 = function0;
            ActionBarKt.SecTitle(function02, StringResources_androidKt.stringResource(R.string.cast_setting, composerImpl), null, ComposableLambdaKt.rememberComposableLambda(-623146778, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt.CastSettingScreen.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
                /* JADX WARN: Removed duplicated region for block: B:22:0x0090  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj2, Object obj3, Object obj4) {
                    PaddingValues paddingValues = (PaddingValues) obj2;
                    Composer composer2 = (Composer) obj3;
                    int iIntValue = ((Number) obj4).intValue();
                    if ((iIntValue & 6) == 0) {
                        iIntValue |= ((ComposerImpl) composer2).changed(paddingValues) ? 4 : 2;
                    }
                    if ((iIntValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.CastSettingScreen.<anonymous> (CastSettingScreen.kt:60)");
                            }
                            LayoutDirection layoutDirection = LayoutDirection.Ltr;
                            PaddingValuesImpl paddingValuesImplM124PaddingValuesa9UjIt4$default = PaddingKt.m124PaddingValuesa9UjIt4$default(PaddingKt.calculateStartPadding(paddingValues, layoutDirection), 0.0f, PaddingKt.calculateEndPadding(paddingValues, layoutDirection), paddingValues.mo110calculateBottomPaddingD9Ej5fM(), 2);
                            Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(SizeKt.fillMaxSize(Modifier.Companion, 1.0f), 0.0f, paddingValues.mo113calculateTopPaddingD9Ej5fM(), 0.0f, 0.0f, 13);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(-904282733);
                            final SettingViewModel settingViewModel2 = settingViewModel;
                            boolean zChangedInstance = composerImpl3.changedInstance(settingViewModel2);
                            final State state = mutableStateCollectAsStateWithLifecycle;
                            boolean zChanged = zChangedInstance | composerImpl3.changed(state);
                            Object objRememberedValue2 = composerImpl3.rememberedValue();
                            if (!zChanged) {
                                Composer.Companion.getClass();
                                if (objRememberedValue2 == Composer.Companion.Empty) {
                                    final MutableState mutableState2 = mutableState;
                                    objRememberedValue2 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1$$ExternalSyntheticLambda0
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj5) {
                                            LazyListScope lazyListScope = (LazyListScope) obj5;
                                            final SettingViewModel settingViewModel3 = settingViewModel2;
                                            LazyListScope.item$default(lazyListScope, new ComposableLambdaImpl(-397919173, true, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1$1$1$1
                                                /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
                                                @Override // kotlin.jvm.functions.Function3
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj6, Object obj7, Object obj8) {
                                                    Composer composer3 = (Composer) obj7;
                                                    if ((((Number) obj8).intValue() & 17) == 16) {
                                                        ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                        if (composerImpl4.getSkipping()) {
                                                            composerImpl4.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.CastSettingScreen.<anonymous>.<anonymous>.<anonymous>.<anonymous> (CastSettingScreen.kt:72)");
                                                            }
                                                            final SettingViewModel settingViewModel4 = settingViewModel3;
                                                            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(944085040, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1$1$1$1.1
                                                                /* JADX WARN: Multi-variable type inference failed */
                                                                /* JADX WARN: Removed duplicated region for block: B:37:0x0176  */
                                                                /* JADX WARN: Removed duplicated region for block: B:45:0x01df  */
                                                                /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
                                                                @Override // kotlin.jvm.functions.Function3
                                                                /*
                                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                                */
                                                                public final Object invoke(Object obj9, Object obj10, Object obj11) {
                                                                    boolean z;
                                                                    final int i6 = 1;
                                                                    Composer composer4 = (Composer) obj10;
                                                                    if ((((Number) obj11).intValue() & 17) == 16) {
                                                                        ComposerImpl composerImpl5 = (ComposerImpl) composer4;
                                                                        if (composerImpl5.getSkipping()) {
                                                                            composerImpl5.skipToGroupEnd();
                                                                        } else {
                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.CastSettingScreen.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (CastSettingScreen.kt:73)");
                                                                            }
                                                                            final SettingViewModel settingViewModel5 = settingViewModel4;
                                                                            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(settingViewModel5.isCastingPriority, null, null, composer4, 48, 2);
                                                                            Modifier.Companion companion = Modifier.Companion;
                                                                            Arrangement.INSTANCE.getClass();
                                                                            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                                                                            Alignment.Companion.getClass();
                                                                            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composer4, 0);
                                                                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer4);
                                                                            ComposerImpl composerImpl6 = (ComposerImpl) composer4;
                                                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl6.currentCompositionLocalScope();
                                                                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer4, companion);
                                                                            ComposeUiNode.Companion.getClass();
                                                                            Function0 function03 = ComposeUiNode.Companion.Constructor;
                                                                            if (composerImpl6.applier == null) {
                                                                                ComposablesKt.invalidApplier();
                                                                                throw null;
                                                                            }
                                                                            composerImpl6.startReusableNode();
                                                                            if (composerImpl6.inserting) {
                                                                                composerImpl6.createNode(function03);
                                                                            } else {
                                                                                composerImpl6.useNode();
                                                                            }
                                                                            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                                                                            Updater.m337setimpl(composer4, columnMeasurePolicy, function2);
                                                                            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                                                                            Updater.m337setimpl(composer4, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                                                                            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                                            if (composerImpl6.inserting || !Intrinsics.areEqual(composerImpl6.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl6, currentCompositeKeyHash, function23);
                                                                            }
                                                                            Function2 function24 = ComposeUiNode.Companion.SetModifier;
                                                                            Updater.m337setimpl(composer4, modifierMaterializeModifier, function24);
                                                                            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                                                                            Dp.Companion companion2 = Dp.Companion;
                                                                            float f = 16;
                                                                            Modifier modifierM129paddingqDBjuR0$default2 = PaddingKt.m129paddingqDBjuR0$default(PaddingKt.m127paddingVpY3zN4$default(companion, 18, 0.0f, 2), 0.0f, f, 0.0f, 4, 5);
                                                                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                                            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer4);
                                                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl6.currentCompositionLocalScope();
                                                                            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composer4, modifierM129paddingqDBjuR0$default2);
                                                                            composerImpl6.startReusableNode();
                                                                            if (composerImpl6.inserting) {
                                                                                composerImpl6.createNode(function03);
                                                                            } else {
                                                                                composerImpl6.useNode();
                                                                            }
                                                                            Updater.m337setimpl(composer4, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                                                                            Updater.m337setimpl(composer4, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                                                                            if (composerImpl6.inserting || !Intrinsics.areEqual(composerImpl6.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl6, currentCompositeKeyHash2, function23);
                                                                            }
                                                                            Updater.m337setimpl(composer4, modifierMaterializeModifier2, function24);
                                                                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                                            TextKt.m317Text4IGK_g(StringResources_androidKt.stringResource(R.string.cast_setting_description, composer4), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, TypeKt.DescriptionTextStyle(composer4), composer4, 0, 0, 65534);
                                                                            composerImpl6.end(true);
                                                                            composerImpl6.startReplaceGroup(-314097552);
                                                                            boolean zChangedInstance2 = composerImpl6.changedInstance(settingViewModel5);
                                                                            Object objRememberedValue3 = composerImpl6.rememberedValue();
                                                                            Composer.Companion companion3 = Composer.Companion;
                                                                            if (!zChangedInstance2) {
                                                                                companion3.getClass();
                                                                                if (objRememberedValue3 == Composer.Companion.Empty) {
                                                                                    z = false;
                                                                                    final Object[] objArr = 0 == true ? 1 : 0;
                                                                                    objRememberedValue3 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1$1$1$1$1$$ExternalSyntheticLambda0
                                                                                        @Override // kotlin.jvm.functions.Function0
                                                                                        public final Object invoke() {
                                                                                            switch (objArr) {
                                                                                                case 0:
                                                                                                    settingViewModel5.setCastingPriority(false);
                                                                                                    break;
                                                                                                default:
                                                                                                    settingViewModel5.setCastingPriority(true);
                                                                                                    break;
                                                                                            }
                                                                                            return Unit.INSTANCE;
                                                                                        }
                                                                                    };
                                                                                    composerImpl6.updateRememberedValue(objRememberedValue3);
                                                                                } else {
                                                                                    z = false;
                                                                                }
                                                                                Function0 function04 = (Function0) objRememberedValue3;
                                                                                composerImpl6.end(z);
                                                                                ListsKt.SecRadioListItem(function04, StringResources_androidKt.stringResource(R.string.audio_mirroring_priority, composer4), CharSequenceExtKt.stringResourceExt(R.string.audio_mirroring_priority_description, composer4), ((Boolean) mutableStateCollectAsState.getValue()) != null ? Boolean.valueOf(!r6.booleanValue()) : null, composer4, 0);
                                                                                DividerKt.m263HorizontalDivider9IZ8Weo(PaddingKt.m129paddingqDBjuR0$default(companion, 62, 0.0f, f, 0.0f, 10), 1, ColorKt.dividerColor(composer4), composer4, 54, 0);
                                                                                composerImpl6.startReplaceGroup(-314079953);
                                                                                boolean zChangedInstance3 = composerImpl6.changedInstance(settingViewModel5);
                                                                                Object objRememberedValue4 = composerImpl6.rememberedValue();
                                                                                if (!zChangedInstance3) {
                                                                                    companion3.getClass();
                                                                                    if (objRememberedValue4 == Composer.Companion.Empty) {
                                                                                        objRememberedValue4 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1$1$1$1$1$$ExternalSyntheticLambda0
                                                                                            @Override // kotlin.jvm.functions.Function0
                                                                                            public final Object invoke() {
                                                                                                switch (i6) {
                                                                                                    case 0:
                                                                                                        settingViewModel5.setCastingPriority(false);
                                                                                                        break;
                                                                                                    default:
                                                                                                        settingViewModel5.setCastingPriority(true);
                                                                                                        break;
                                                                                                }
                                                                                                return Unit.INSTANCE;
                                                                                            }
                                                                                        };
                                                                                        composerImpl6.updateRememberedValue(objRememberedValue4);
                                                                                    }
                                                                                    composerImpl6.end(false);
                                                                                    ListsKt.SecRadioListItem((Function0) objRememberedValue4, StringResources_androidKt.stringResource(R.string.casting_priority, composer4), CharSequenceExtKt.stringResourceExt(R.string.casting_priority_description, composer4), (Boolean) mutableStateCollectAsState.getValue(), composer4, 0);
                                                                                    composerImpl6.end(true);
                                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                                        ComposerKt.traceEventEnd();
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    return Unit.INSTANCE;
                                                                }
                                                            }, composer3), composer3, 6);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }));
                                            ComposableSingletons$CastSettingScreenKt.INSTANCE.getClass();
                                            LazyListScope.item$default(lazyListScope, ComposableSingletons$CastSettingScreenKt.f51lambda1);
                                            final MutableState mutableState3 = mutableState2;
                                            final State state2 = state;
                                            LazyListScope.item$default(lazyListScope, new ComposableLambdaImpl(-1913197709, true, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1$1$1$2
                                                /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
                                                @Override // kotlin.jvm.functions.Function3
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj6, Object obj7, Object obj8) {
                                                    Composer composer3 = (Composer) obj7;
                                                    if ((((Number) obj8).intValue() & 17) == 16) {
                                                        ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                        if (composerImpl4.getSkipping()) {
                                                            composerImpl4.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.CastSettingScreen.<anonymous>.<anonymous>.<anonymous>.<anonymous> (CastSettingScreen.kt:111)");
                                                            }
                                                            final MutableState mutableState4 = mutableState3;
                                                            final State state3 = state2;
                                                            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(-737337816, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1$1$1$2.1
                                                                /* JADX WARN: Removed duplicated region for block: B:8:0x0028  */
                                                                @Override // kotlin.jvm.functions.Function3
                                                                /*
                                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                                */
                                                                public final Object invoke(Object obj9, Object obj10, Object obj11) {
                                                                    ArrayList arrayList;
                                                                    Painter painter;
                                                                    String str;
                                                                    int i6 = 1;
                                                                    Composer composer4 = (Composer) obj10;
                                                                    if ((((Number) obj11).intValue() & 17) == 16) {
                                                                        ComposerImpl composerImpl5 = (ComposerImpl) composer4;
                                                                        if (composerImpl5.getSkipping()) {
                                                                            composerImpl5.skipToGroupEnd();
                                                                        } else {
                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.CastSettingScreen.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (CastSettingScreen.kt:112)");
                                                                            }
                                                                            List list = (List) mutableState4.getValue();
                                                                            Unit unit = null;
                                                                            if (list.isEmpty()) {
                                                                                list = null;
                                                                            }
                                                                            if (list != null) {
                                                                                arrayList = new ArrayList();
                                                                                for (Object obj12 : list) {
                                                                                    if (StringsKt__StringsKt.contains((String) ((Pair) obj12).component2(), "spotify", true) ? Intrinsics.areEqual((Boolean) state3.getValue(), Boolean.FALSE) : true) {
                                                                                        arrayList.add(obj12);
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                arrayList = null;
                                                                            }
                                                                            ComposerImpl composerImpl6 = (ComposerImpl) composer4;
                                                                            composerImpl6.startReplaceGroup(26117379);
                                                                            if (arrayList != null) {
                                                                                int size = arrayList.size();
                                                                                int i7 = 0;
                                                                                int i8 = 0;
                                                                                while (i8 < size) {
                                                                                    Object obj13 = arrayList.get(i8);
                                                                                    int i9 = i8 + 1;
                                                                                    int i10 = i7 + 1;
                                                                                    if (i7 < 0) {
                                                                                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                                                                                        throw null;
                                                                                    }
                                                                                    Pair pair = (Pair) obj13;
                                                                                    Painter painter2 = (Painter) pair.component1();
                                                                                    String str2 = (String) pair.component2();
                                                                                    composerImpl6.startReplaceGroup(26119387);
                                                                                    if (i7 > 0) {
                                                                                        Dp.Companion companion = Dp.Companion;
                                                                                        painter = painter2;
                                                                                        str = str2;
                                                                                        DividerKt.m263HorizontalDivider9IZ8Weo(PaddingKt.m129paddingqDBjuR0$default(Modifier.Companion, 70, 0.0f, 16, 0.0f, 10), i6, ColorKt.dividerColor(composerImpl6), composerImpl6, 54, 0);
                                                                                    } else {
                                                                                        painter = painter2;
                                                                                        str = str2;
                                                                                    }
                                                                                    composerImpl6.end(false);
                                                                                    ListsKt.SecIconListItem(str, painter, composerImpl6, 0);
                                                                                    i8 = i9;
                                                                                    i7 = i10;
                                                                                    i6 = 1;
                                                                                }
                                                                                unit = Unit.INSTANCE;
                                                                            }
                                                                            composerImpl6.end(false);
                                                                            if (unit == null) {
                                                                                ListsKt.EmptyListItem(StringResources_androidKt.stringResource(R.string.cast_setting_apps_not_installed, composerImpl6), composerImpl6, 0);
                                                                            }
                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                ComposerKt.traceEventEnd();
                                                                            }
                                                                        }
                                                                    }
                                                                    return Unit.INSTANCE;
                                                                }
                                                            }, composer3), composer3, 6);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }));
                                            LazyListScope.item$default(lazyListScope, ComposableSingletons$CastSettingScreenKt.f52lambda2);
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl3.updateRememberedValue(objRememberedValue2);
                                }
                                composerImpl3.end(false);
                                LazyDslKt.LazyColumn(modifierM129paddingqDBjuR0$default, null, paddingValuesImplM124PaddingValuesa9UjIt4$default, false, null, null, null, false, null, (Function1) objRememberedValue2, composerImpl3, 0, 506);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, (i5 & 14) | 3072, 4);
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(910021836);
            boolean zChangedInstance = composerImpl.changedInstance(context);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue2 == obj) {
                objRememberedValue2 = new CastSettingScreenKt$CastSettingScreen$2$1(context, mutableState, null);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) objRememberedValue2);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(settingViewModel, i) { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$$ExternalSyntheticLambda0
                public final /* synthetic */ SettingViewModel f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    CastSettingScreenKt.CastSettingScreen(this.f$0, this.f$1, (Composer) obj2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
