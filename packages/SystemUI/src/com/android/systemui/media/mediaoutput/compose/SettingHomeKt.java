package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import kotlin.Pair;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public abstract class SettingHomeKt {
    public static final void SettingHome(final Function0 function0, final Function1 function1, SettingViewModel settingViewModel, LabsViewModel labsViewModel, Composer composer, final int i) {
        Object failure;
        Object failure2;
        int i2;
        final LabsViewModel labsViewModel2;
        final SettingViewModel settingViewModel2;
        final SettingViewModel settingViewModel3;
        final LabsViewModel labsViewModel3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2079907393);
        int i3 = i | (composerImpl.changedInstance(function0) ? 4 : 2) | (composerImpl.changedInstance(function1) ? 32 : 16) | 1152;
        if ((i3 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            settingViewModel3 = settingViewModel;
            labsViewModel3 = labsViewModel;
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
                    int i4 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i5 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure);
                if (thM3442exceptionOrNullimpl != null) {
                    thM3442exceptionOrNullimpl.printStackTrace();
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
                SettingViewModel settingViewModel4 = (SettingViewModel) viewModel;
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current2 = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current2 == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras2 = current2 instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current2).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    failure2 = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th2) {
                    int i6 = Result.$r8$clinit;
                    failure2 = new Result.Failure(th2);
                }
                Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(failure2);
                if (thM3442exceptionOrNullimpl2 != null) {
                    thM3442exceptionOrNullimpl2.printStackTrace();
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory2 = (ViewModelProvider.Factory) (failure2 instanceof Result.Failure ? null : failure2);
                if (factoryCreateDaggerViewModelFactory2 == null) {
                    factoryCreateDaggerViewModelFactory2 = ViewModelKt.createDaggerViewModelFactory(current2);
                }
                ViewModel viewModel2 = ViewModelKt.get(current2, LabsViewModel.class, factoryCreateDaggerViewModelFactory2, defaultViewModelCreationExtras2);
                composerImpl.end(false);
                i2 = i3 & (-8065);
                labsViewModel2 = (LabsViewModel) viewModel2;
                settingViewModel2 = settingViewModel4;
            } else {
                composerImpl.skipToGroupEnd();
                labsViewModel2 = labsViewModel;
                i2 = i3 & (-8065);
                settingViewModel2 = settingViewModel;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SettingHome (SettingHome.kt:48)");
            }
            ActionBarKt.SecTitle(function0, StringResources_androidKt.stringResource(R.string.settings, new Object[]{StringResources_androidKt.stringResource(R.string.media_output, composerImpl)}, composerImpl), null, ComposableLambdaKt.rememberComposableLambda(679383207, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt.SettingHome.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    PaddingValues paddingValues = (PaddingValues) obj;
                    Composer composer2 = (Composer) obj2;
                    int iIntValue = ((Number) obj3).intValue();
                    if ((iIntValue & 6) == 0) {
                        iIntValue |= ((ComposerImpl) composer2).changed(paddingValues) ? 4 : 2;
                    }
                    if ((iIntValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SettingHome.<anonymous> (SettingHome.kt:53)");
                            }
                            LayoutDirection layoutDirection = LayoutDirection.Ltr;
                            PaddingValuesImpl paddingValuesImplM124PaddingValuesa9UjIt4$default = PaddingKt.m124PaddingValuesa9UjIt4$default(PaddingKt.calculateStartPadding(paddingValues, layoutDirection), 0.0f, PaddingKt.calculateEndPadding(paddingValues, layoutDirection), paddingValues.mo110calculateBottomPaddingD9Ej5fM(), 2);
                            final LabsViewModel labsViewModel4 = labsViewModel2;
                            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(labsViewModel4.isShowLabsMenu, Boolean.FALSE, null, composer2, 48, 2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(44292291);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            Composer.Companion.getClass();
                            Object obj4 = Composer.Companion.Empty;
                            if (objRememberedValue == obj4) {
                                objRememberedValue = SnapshotStateKt.mutableStateOf$default(new Pair(0, 0));
                                composerImpl3.updateRememberedValue(objRememberedValue);
                            }
                            final MutableState mutableState = (MutableState) objRememberedValue;
                            composerImpl3.end(false);
                            Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(SizeKt.fillMaxSize(Modifier.Companion, 1.0f), 0.0f, paddingValues.mo113calculateTopPaddingD9Ej5fM(), 0.0f, 0.0f, 13);
                            Unit unit = Unit.INSTANCE;
                            composerImpl3.startReplaceGroup(44300331);
                            boolean zChangedInstance = composerImpl3.changedInstance(labsViewModel4);
                            Object objRememberedValue2 = composerImpl3.rememberedValue();
                            if (zChangedInstance || objRememberedValue2 == obj4) {
                                objRememberedValue2 = new PointerInputEventHandler() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$1$1
                                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                                        Object objDetectTapGestures$default = TapGestureDetectorKt.detectTapGestures$default(pointerInputScope, null, null, null, new SettingHomeKt$SettingHome$1$$ExternalSyntheticLambda0(labsViewModel4, mutableState, pointerInputScope), continuation, 7);
                                        return objDetectTapGestures$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectTapGestures$default : Unit.INSTANCE;
                                    }
                                };
                                composerImpl3.updateRememberedValue(objRememberedValue2);
                            }
                            composerImpl3.end(false);
                            Modifier modifierPointerInput = SuspendingPointerInputFilterKt.pointerInput(modifierM129paddingqDBjuR0$default, unit, (PointerInputEventHandler) objRememberedValue2);
                            composerImpl3.startReplaceGroup(44332305);
                            SettingViewModel settingViewModel5 = settingViewModel2;
                            boolean zChangedInstance2 = composerImpl3.changedInstance(settingViewModel5);
                            Function1 function12 = function1;
                            boolean zChanged = zChangedInstance2 | composerImpl3.changed(function12) | composerImpl3.changed(mutableStateCollectAsState);
                            Object objRememberedValue3 = composerImpl3.rememberedValue();
                            if (zChanged || objRememberedValue3 == obj4) {
                                objRememberedValue3 = new SettingHomeKt$SettingHome$1$$ExternalSyntheticLambda0(settingViewModel5, function12, mutableStateCollectAsState);
                                composerImpl3.updateRememberedValue(objRememberedValue3);
                            }
                            composerImpl3.end(false);
                            LazyDslKt.LazyColumn(modifierPointerInput, null, paddingValuesImplM124PaddingValuesa9UjIt4$default, false, null, null, null, false, null, (Function1) objRememberedValue3, composerImpl3, 0, 506);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, (i2 & 14) | 3072, 4);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            settingViewModel3 = settingViewModel2;
            labsViewModel3 = labsViewModel2;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(function1, settingViewModel3, labsViewModel3, i) { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$$ExternalSyntheticLambda0
                public final /* synthetic */ Function1 f$1;
                public final /* synthetic */ SettingViewModel f$2;
                public final /* synthetic */ LabsViewModel f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    SettingViewModel settingViewModel5 = this.f$2;
                    LabsViewModel labsViewModel4 = this.f$3;
                    SettingHomeKt.SettingHome(this.f$0, this.f$1, settingViewModel5, labsViewModel4, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
