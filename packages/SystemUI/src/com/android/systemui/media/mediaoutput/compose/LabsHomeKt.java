package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.graphics.ColorKt;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt;
import com.android.systemui.media.mediaoutput.compose.widget.ListsKt;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import com.samsung.sesl.compose.component.SeslSwitchDefaults;
import com.samsung.sesl.compose.component.SwitchKt;
import com.samsung.sesl.compose.template.SeslTopAppBarTemplate$ActionScope;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class LabsHomeKt {
    public static final void ActionLabs(final LabsViewModel labsViewModel, Composer composer, int i) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(506053709);
        if (((i | 2) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
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
                    int i2 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
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
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factoryCreateDaggerViewModelFactory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel = (LabsViewModel) viewModel;
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ActionLabs (LabsHome.kt:191)");
            }
            ListsKt.SecSubHeader("Actions", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(1062474114, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt.ActionLabs.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x005f  */
                /* JADX WARN: Removed duplicated region for block: B:20:0x008f  */
                /* JADX WARN: Removed duplicated region for block: B:27:0x00d2  */
                /* JADX WARN: Removed duplicated region for block: B:33:0x0104  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ActionLabs.<anonymous> (LabsHome.kt:195)");
                            }
                            LabsViewModel labsViewModel2 = labsViewModel;
                            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(labsViewModel2.isQuickboardInstalled, null, null, composer2, 48, 2);
                            MutableState mutableStateCollectAsState2 = SnapshotStateKt.collectAsState(labsViewModel2.isActionOpenOutputSwitcher, null, null, composer2, 48, 2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(1447914345);
                            boolean zChangedInstance = composerImpl3.changedInstance(labsViewModel2);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            Composer.Companion companion = Composer.Companion;
                            if (!zChangedInstance) {
                                companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new LabsHomeKt$ResetItem$1$$ExternalSyntheticLambda0(labsViewModel2, 1);
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl3.end(false);
                                ListsKt.SecListItem((Function0) objRememberedValue, "Open Dex UX", null, composerImpl3, 48, 4);
                                composerImpl3.startReplaceGroup(1447918250);
                                boolean zChangedInstance2 = composerImpl3.changedInstance(labsViewModel2);
                                Object objRememberedValue2 = composerImpl3.rememberedValue();
                                if (!zChangedInstance2) {
                                    companion.getClass();
                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                        objRememberedValue2 = new LabsHomeKt$ResetItem$1$$ExternalSyntheticLambda0(labsViewModel2, 2);
                                        composerImpl3.updateRememberedValue(objRememberedValue2);
                                    }
                                    composerImpl3.end(false);
                                    ListsKt.SecListItem((Function0) objRememberedValue2, "Open Cover UX", null, composerImpl3, 48, 4);
                                    composerImpl3.startReplaceGroup(1447921281);
                                    if (Intrinsics.areEqual((Boolean) mutableStateCollectAsState.getValue(), Boolean.TRUE)) {
                                        composerImpl3.startReplaceGroup(1447923943);
                                        boolean zChangedInstance3 = composerImpl3.changedInstance(labsViewModel2);
                                        Object objRememberedValue3 = composerImpl3.rememberedValue();
                                        if (!zChangedInstance3) {
                                            companion.getClass();
                                            if (objRememberedValue3 == Composer.Companion.Empty) {
                                                objRememberedValue3 = new LabsHomeKt$ResetItem$1$$ExternalSyntheticLambda0(labsViewModel2, 3);
                                                composerImpl3.updateRememberedValue(objRememberedValue3);
                                            }
                                            composerImpl3.end(false);
                                            ListsKt.SecListItem((Function0) objRememberedValue3, "Open Quickboard", null, composerImpl3, 48, 4);
                                        }
                                    }
                                    composerImpl3.end(false);
                                    composerImpl3.startReplaceGroup(1447928662);
                                    boolean zChangedInstance4 = composerImpl3.changedInstance(labsViewModel2);
                                    Object objRememberedValue4 = composerImpl3.rememberedValue();
                                    if (!zChangedInstance4) {
                                        companion.getClass();
                                        if (objRememberedValue4 == Composer.Companion.Empty) {
                                            objRememberedValue4 = new LabsHomeKt$Labs$1$$ExternalSyntheticLambda0(labsViewModel2, 1);
                                            composerImpl3.updateRememberedValue(objRememberedValue4);
                                        }
                                        composerImpl3.end(false);
                                        ListsKt.SecSwitchListItem((Function1) objRememberedValue4, "Support Open OutputSwitcher Button", null, (Boolean) mutableStateCollectAsState2.getValue(), composerImpl3, 48, 4);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new LabsHomeKt$$ExternalSyntheticLambda1(labsViewModel, i, 4);
        }
    }

    public static final void ChromecastLabs(final LabsViewModel labsViewModel, Composer composer, int i) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1172440810);
        if (((i | 2) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
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
                    int i2 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
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
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factoryCreateDaggerViewModelFactory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel = (LabsViewModel) viewModel;
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ChromecastLabs (LabsHome.kt:147)");
            }
            ListsKt.SecSubHeader("Chromecast Labs", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(34124191, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt.ChromecastLabs.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x006a  */
                /* JADX WARN: Removed duplicated region for block: B:20:0x00a1  */
                /* JADX WARN: Removed duplicated region for block: B:25:0x00d7  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ChromecastLabs.<anonymous> (LabsHome.kt:151)");
                            }
                            LabsViewModel labsViewModel2 = labsViewModel;
                            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(labsViewModel2.isSupportTransferableRoutesWhileConnecting, null, null, composer2, 48, 2);
                            MutableState mutableStateCollectAsState2 = SnapshotStateKt.collectAsState(labsViewModel2.isSupportForTransferDuringRouting, null, null, composer2, 48, 2);
                            MutableState mutableStateCollectAsState3 = SnapshotStateKt.collectAsState(labsViewModel2.isSupportDisplayOnlyRemoteDevice, null, null, composer2, 48, 2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(949468073);
                            boolean zChangedInstance = composerImpl3.changedInstance(labsViewModel2);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            Composer.Companion companion = Composer.Companion;
                            if (!zChangedInstance) {
                                companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new LabsHomeKt$Labs$1$$ExternalSyntheticLambda0(labsViewModel2, 2);
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl3.end(false);
                                ListsKt.SecSwitchListItem((Function1) objRememberedValue, "Support Transferable Routes While Connecting", null, (Boolean) mutableStateCollectAsState.getValue(), composerImpl3, 48, 4);
                                composerImpl3.startReplaceGroup(949476224);
                                boolean zChangedInstance2 = composerImpl3.changedInstance(labsViewModel2);
                                Object objRememberedValue2 = composerImpl3.rememberedValue();
                                if (!zChangedInstance2) {
                                    companion.getClass();
                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                        objRememberedValue2 = new LabsHomeKt$Labs$1$$ExternalSyntheticLambda0(labsViewModel2, 3);
                                        composerImpl3.updateRememberedValue(objRememberedValue2);
                                    }
                                    composerImpl3.end(false);
                                    ListsKt.SecSwitchListItem((Function1) objRememberedValue2, "Support For Transfer During Routing", null, (Boolean) mutableStateCollectAsState2.getValue(), composerImpl3, 48, 4);
                                    composerImpl3.startReplaceGroup(949483519);
                                    boolean zChangedInstance3 = composerImpl3.changedInstance(labsViewModel2);
                                    Object objRememberedValue3 = composerImpl3.rememberedValue();
                                    if (!zChangedInstance3) {
                                        companion.getClass();
                                        if (objRememberedValue3 == Composer.Companion.Empty) {
                                            objRememberedValue3 = new LabsHomeKt$Labs$1$$ExternalSyntheticLambda0(labsViewModel2, 4);
                                            composerImpl3.updateRememberedValue(objRememberedValue3);
                                        }
                                        composerImpl3.end(false);
                                        ListsKt.SecSwitchListItem((Function1) objRememberedValue3, "Support Display Only Remote Device", null, (Boolean) mutableStateCollectAsState3.getValue(), composerImpl3, 48, 4);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new LabsHomeKt$$ExternalSyntheticLambda1(labsViewModel, i, 0);
        }
    }

    public static final void DebugLabs(final LabsViewModel labsViewModel, Composer composer, int i) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1324084820);
        if (((i | 2) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
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
                    int i2 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
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
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factoryCreateDaggerViewModelFactory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel = (LabsViewModel) viewModel;
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.DebugLabs (LabsHome.kt:111)");
            }
            ListsKt.SecSubHeader("Debug Labs", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(494979543, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt.DebugLabs.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x0075  */
                /* JADX WARN: Removed duplicated region for block: B:20:0x00ac  */
                /* JADX WARN: Removed duplicated region for block: B:25:0x00e2  */
                /* JADX WARN: Removed duplicated region for block: B:30:0x0118  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.DebugLabs.<anonymous> (LabsHome.kt:115)");
                            }
                            LabsViewModel labsViewModel2 = labsViewModel;
                            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(labsViewModel2.isGrayscaleThumbnail, null, null, composer2, 48, 2);
                            MutableState mutableStateCollectAsState2 = SnapshotStateKt.collectAsState(labsViewModel2.isSupportMultipleMediaSession, null, null, composer2, 48, 2);
                            MutableState mutableStateCollectAsState3 = SnapshotStateKt.collectAsState(labsViewModel2.isSupportSelectableBudsTogether, null, null, composer2, 48, 2);
                            MutableState mutableStateCollectAsState4 = SnapshotStateKt.collectAsState(labsViewModel2.isSupportDisplayDeviceVolumeControl, null, null, composer2, 48, 2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(-1462237967);
                            boolean zChangedInstance = composerImpl3.changedInstance(labsViewModel2);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            Composer.Companion companion = Composer.Companion;
                            if (!zChangedInstance) {
                                companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new LabsHomeKt$Labs$1$$ExternalSyntheticLambda0(labsViewModel2, 5);
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl3.end(false);
                                ListsKt.SecSwitchListItem((Function1) objRememberedValue, "Grayscale thumbnail", null, (Boolean) mutableStateCollectAsState.getValue(), composerImpl3, 48, 4);
                                composerImpl3.startReplaceGroup(-1462232006);
                                boolean zChangedInstance2 = composerImpl3.changedInstance(labsViewModel2);
                                Object objRememberedValue2 = composerImpl3.rememberedValue();
                                if (!zChangedInstance2) {
                                    companion.getClass();
                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                        objRememberedValue2 = new LabsHomeKt$Labs$1$$ExternalSyntheticLambda0(labsViewModel2, 6);
                                        composerImpl3.updateRememberedValue(objRememberedValue2);
                                    }
                                    composerImpl3.end(false);
                                    ListsKt.SecSwitchListItem((Function1) objRememberedValue2, "Support Multiple Media Session", null, (Boolean) mutableStateCollectAsState2.getValue(), composerImpl3, 48, 4);
                                    composerImpl3.startReplaceGroup(-1462225124);
                                    boolean zChangedInstance3 = composerImpl3.changedInstance(labsViewModel2);
                                    Object objRememberedValue3 = composerImpl3.rememberedValue();
                                    if (!zChangedInstance3) {
                                        companion.getClass();
                                        if (objRememberedValue3 == Composer.Companion.Empty) {
                                            objRememberedValue3 = new LabsHomeKt$Labs$1$$ExternalSyntheticLambda0(labsViewModel2, 7);
                                            composerImpl3.updateRememberedValue(objRememberedValue3);
                                        }
                                        composerImpl3.end(false);
                                        ListsKt.SecSwitchListItem((Function1) objRememberedValue3, "Support Selectable BudsTogether", null, (Boolean) mutableStateCollectAsState3.getValue(), composerImpl3, 48, 4);
                                        composerImpl3.startReplaceGroup(-1462218080);
                                        boolean zChangedInstance4 = composerImpl3.changedInstance(labsViewModel2);
                                        Object objRememberedValue4 = composerImpl3.rememberedValue();
                                        if (!zChangedInstance4) {
                                            companion.getClass();
                                            if (objRememberedValue4 == Composer.Companion.Empty) {
                                                objRememberedValue4 = new LabsHomeKt$Labs$1$$ExternalSyntheticLambda0(labsViewModel2, 8);
                                                composerImpl3.updateRememberedValue(objRememberedValue4);
                                            }
                                            composerImpl3.end(false);
                                            ListsKt.SecSwitchListItem((Function1) objRememberedValue4, "Support Display Device Volume Control", null, (Boolean) mutableStateCollectAsState4.getValue(), composerImpl3, 48, 4);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new LabsHomeKt$$ExternalSyntheticLambda1(labsViewModel, i, 1);
        }
    }

    public static final void Labs(final LabsViewModel labsViewModel, Composer composer, int i) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-16641565);
        if (((i | 2) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
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
                    int i2 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
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
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factoryCreateDaggerViewModelFactory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel = (LabsViewModel) viewModel;
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.Labs (LabsHome.kt:75)");
            }
            ListsKt.SecSubHeader("Labs", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(744991448, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt.Labs.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x0075  */
                /* JADX WARN: Removed duplicated region for block: B:20:0x00ac  */
                /* JADX WARN: Removed duplicated region for block: B:25:0x00e3  */
                /* JADX WARN: Removed duplicated region for block: B:30:0x011a  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.Labs.<anonymous> (LabsHome.kt:79)");
                            }
                            LabsViewModel labsViewModel2 = labsViewModel;
                            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(labsViewModel2.isCloseOnTouchOutside, null, null, composer2, 48, 2);
                            MutableState mutableStateCollectAsState2 = SnapshotStateKt.collectAsState(labsViewModel2.isSupportVolumeInteraction, null, null, composer2, 48, 2);
                            MutableState mutableStateCollectAsState3 = SnapshotStateKt.collectAsState(labsViewModel2.isGroupSpeakerDefaultExpanded, null, null, composer2, 48, 2);
                            MutableState mutableStateCollectAsState4 = SnapshotStateKt.collectAsState(labsViewModel2.isSupportRecentGroupSpeaker, null, null, composer2, 48, 2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(1680486747);
                            boolean zChangedInstance = composerImpl3.changedInstance(labsViewModel2);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            Composer.Companion companion = Composer.Companion;
                            if (!zChangedInstance) {
                                companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new LabsHomeKt$Labs$1$$ExternalSyntheticLambda0(labsViewModel2, 0);
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl3.end(false);
                                ListsKt.SecSwitchListItem((Function1) objRememberedValue, "Close On Touch Outside", null, (Boolean) mutableStateCollectAsState.getValue(), composerImpl3, 48, 4);
                                composerImpl3.startReplaceGroup(1680492864);
                                boolean zChangedInstance2 = composerImpl3.changedInstance(labsViewModel2);
                                Object objRememberedValue2 = composerImpl3.rememberedValue();
                                if (!zChangedInstance2) {
                                    companion.getClass();
                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                        objRememberedValue2 = new LabsHomeKt$Labs$1$$ExternalSyntheticLambda0(labsViewModel2, 9);
                                        composerImpl3.updateRememberedValue(objRememberedValue2);
                                    }
                                    composerImpl3.end(false);
                                    ListsKt.SecSwitchListItem((Function1) objRememberedValue2, "Support Volume Interaction", null, (Boolean) mutableStateCollectAsState2.getValue(), composerImpl3, 48, 4);
                                    composerImpl3.startReplaceGroup(1680499427);
                                    boolean zChangedInstance3 = composerImpl3.changedInstance(labsViewModel2);
                                    Object objRememberedValue3 = composerImpl3.rememberedValue();
                                    if (!zChangedInstance3) {
                                        companion.getClass();
                                        if (objRememberedValue3 == Composer.Companion.Empty) {
                                            objRememberedValue3 = new LabsHomeKt$Labs$1$$ExternalSyntheticLambda0(labsViewModel2, 10);
                                            composerImpl3.updateRememberedValue(objRememberedValue3);
                                        }
                                        composerImpl3.end(false);
                                        ListsKt.SecSwitchListItem((Function1) objRememberedValue3, "Group Speaker Default Expanded", null, (Boolean) mutableStateCollectAsState3.getValue(), composerImpl3, 48, 4);
                                        composerImpl3.startReplaceGroup(1680506303);
                                        boolean zChangedInstance4 = composerImpl3.changedInstance(labsViewModel2);
                                        Object objRememberedValue4 = composerImpl3.rememberedValue();
                                        if (!zChangedInstance4) {
                                            companion.getClass();
                                            if (objRememberedValue4 == Composer.Companion.Empty) {
                                                objRememberedValue4 = new LabsHomeKt$Labs$1$$ExternalSyntheticLambda0(labsViewModel2, 11);
                                                composerImpl3.updateRememberedValue(objRememberedValue4);
                                            }
                                            composerImpl3.end(false);
                                            ListsKt.SecSwitchListItem((Function1) objRememberedValue4, "Support Recent Group Speaker", null, (Boolean) mutableStateCollectAsState4.getValue(), composerImpl3, 48, 4);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new LabsHomeKt$$ExternalSyntheticLambda1(labsViewModel, i, 3);
        }
    }

    public static final void LabsHome(Function0 function0, final LabsViewModel labsViewModel, Composer composer, final int i) {
        Object failure;
        final Function0 function02;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1253005640);
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
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factoryCreateDaggerViewModelFactory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel = (LabsViewModel) viewModel;
            } else {
                composerImpl.skipToGroupEnd();
            }
            int i5 = i2 & (-113);
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.LabsHome (LabsHome.kt:28)");
            }
            ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-1132156546, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt.LabsHome.1
                /* JADX WARN: Removed duplicated region for block: B:18:0x003d  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    SeslTopAppBarTemplate$ActionScope seslTopAppBarTemplate$ActionScope = (SeslTopAppBarTemplate$ActionScope) obj;
                    Composer composer2 = (Composer) obj2;
                    int iIntValue = ((Number) obj3).intValue();
                    if ((iIntValue & 6) == 0) {
                        iIntValue |= (iIntValue & 8) == 0 ? ((ComposerImpl) composer2).changed(seslTopAppBarTemplate$ActionScope) : ((ComposerImpl) composer2).changedInstance(seslTopAppBarTemplate$ActionScope) ? 4 : 2;
                    }
                    if ((iIntValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.LabsHome.<anonymous> (LabsHome.kt:33)");
                            }
                            final LabsViewModel labsViewModel2 = labsViewModel;
                            ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(2013360012, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt.LabsHome.1.1
                                /* JADX WARN: Removed duplicated region for block: B:18:0x0074  */
                                /* JADX WARN: Removed duplicated region for block: B:26:0x00b9  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
                                @Override // kotlin.jvm.functions.Function4
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj4, Object obj5, Object obj6, Object obj7) {
                                    Composer composer3 = (Composer) obj6;
                                    if ((((Number) obj7).intValue() & 129) == 128) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.LabsHome.<anonymous>.<anonymous> (LabsHome.kt:35)");
                                            }
                                            final LabsViewModel labsViewModel3 = labsViewModel2;
                                            Boolean bool = (Boolean) SnapshotStateKt.collectAsState(labsViewModel3.isShowLabsMenu, null, null, composer3, 48, 2).getValue();
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            composerImpl4.startReplaceGroup(1868165050);
                                            Composer.Companion companion = Composer.Companion;
                                            if (bool == null) {
                                                bool = null;
                                            } else {
                                                final boolean zBooleanValue = bool.booleanValue();
                                                composerImpl4.startReplaceGroup(475927255);
                                                boolean zChangedInstance = composerImpl4.changedInstance(labsViewModel3) | composerImpl4.changed(zBooleanValue);
                                                Object objRememberedValue = composerImpl4.rememberedValue();
                                                if (!zChangedInstance) {
                                                    companion.getClass();
                                                    if (objRememberedValue == Composer.Companion.Empty) {
                                                        objRememberedValue = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$LabsHome$1$1$$ExternalSyntheticLambda0
                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj8) {
                                                                ((Boolean) obj8).booleanValue();
                                                                labsViewModel3.setShowLabsMenu(!zBooleanValue);
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                                        composerImpl4.updateRememberedValue(objRememberedValue);
                                                    }
                                                    composerImpl4.end(false);
                                                    SeslSwitchDefaults seslSwitchDefaults = SeslSwitchDefaults.INSTANCE;
                                                    long jColor = ColorKt.Color(4281891583L);
                                                    seslSwitchDefaults.getClass();
                                                    SwitchKt.SeslSwitch(zBooleanValue, (Function1) objRememberedValue, null, false, SeslSwitchDefaults.m3344colorsoq7We08(jColor, composerImpl4), null, composerImpl4, 0, 44);
                                                }
                                            }
                                            composerImpl4.end(false);
                                            if (bool == null) {
                                                composerImpl4.startReplaceGroup(1868178644);
                                                boolean zChangedInstance2 = composerImpl4.changedInstance(labsViewModel3);
                                                Object objRememberedValue2 = composerImpl4.rememberedValue();
                                                if (!zChangedInstance2) {
                                                    companion.getClass();
                                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                                        objRememberedValue2 = new LabsHomeKt$Labs$1$$ExternalSyntheticLambda0(labsViewModel3, 12);
                                                        composerImpl4.updateRememberedValue(objRememberedValue2);
                                                    }
                                                    composerImpl4.end(false);
                                                    SwitchKt.SeslSwitch(false, (Function1) objRememberedValue2, null, false, null, null, composerImpl4, 6, 60);
                                                }
                                            }
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composer2);
                            ComposableSingletons$LabsHomeKt.INSTANCE.getClass();
                            ComposableLambdaImpl composableLambdaImpl = ComposableSingletons$LabsHomeKt.f57lambda1;
                            ComposableLambdaImpl composableLambdaImpl2 = ComposableSingletons$LabsHomeKt.f58lambda2;
                            SeslTopAppBarTemplate$ActionScope.Companion companion = SeslTopAppBarTemplate$ActionScope.Companion;
                            seslTopAppBarTemplate$ActionScope.SeslTopAppBarActionLayout(composableLambdaImplRememberComposableLambda2, composableLambdaImpl, composableLambdaImpl2, null, composer2, 438 | ((iIntValue << 12) & 57344));
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl);
            ComposableSingletons$LabsHomeKt.INSTANCE.getClass();
            function02 = function0;
            ActionBarKt.SecTitle(function02, "Labs", composableLambdaImplRememberComposableLambda, ComposableSingletons$LabsHomeKt.f65lambda9, composerImpl, (i5 & 14) | 3504, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(labsViewModel, i) { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$$ExternalSyntheticLambda0
                public final /* synthetic */ LabsViewModel f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    LabsHomeKt.LabsHome(this.f$0, this.f$1, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ResetItem(final LabsViewModel labsViewModel, Composer composer, int i) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1618650109);
        if (((i | 2) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
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
                    int i2 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
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
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factoryCreateDaggerViewModelFactory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel = (LabsViewModel) viewModel;
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ResetItem (LabsHome.kt:224)");
            }
            ListsKt.SecSubHeader("Reset", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(200414254, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt.ResetItem.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x0047  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ResetItem.<anonymous> (LabsHome.kt:228)");
                            }
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(-1111946904);
                            LabsViewModel labsViewModel2 = labsViewModel;
                            boolean zChangedInstance = composerImpl3.changedInstance(labsViewModel2);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            if (!zChangedInstance) {
                                Composer.Companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new LabsHomeKt$ResetItem$1$$ExternalSyntheticLambda0(labsViewModel2, 0);
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl3.end(false);
                                ListsKt.SecListItem((Function0) objRememberedValue, "Reset Labs Settings", null, composerImpl3, 48, 4);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new LabsHomeKt$$ExternalSyntheticLambda1(labsViewModel, i, 2);
        }
    }

    public static final void SmartThingsLabs(final LabsViewModel labsViewModel, Composer composer, int i) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1156387791);
        if (((i | 2) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
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
                    int i2 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
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
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factoryCreateDaggerViewModelFactory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel = (LabsViewModel) viewModel;
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SmartThingsLabs (LabsHome.kt:176)");
            }
            ListsKt.SecSubHeader("SmartThings Labs", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(-2084464612, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt.SmartThingsLabs.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x0053  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SmartThingsLabs.<anonymous> (LabsHome.kt:180)");
                            }
                            LabsViewModel labsViewModel2 = labsViewModel;
                            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(labsViewModel2.isSupportForUnsupportedTV, null, null, composer2, 48, 2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(910588785);
                            boolean zChangedInstance = composerImpl3.changedInstance(labsViewModel2);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            if (!zChangedInstance) {
                                Composer.Companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new LabsHomeKt$Labs$1$$ExternalSyntheticLambda0(labsViewModel2, 13);
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl3.end(false);
                                ListsKt.SecSwitchListItem((Function1) objRememberedValue, "Support for unsupported TV", null, (Boolean) mutableStateCollectAsState.getValue(), composerImpl3, 48, 4);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new LabsHomeKt$$ExternalSyntheticLambda1(labsViewModel, i, 5);
        }
    }
}
