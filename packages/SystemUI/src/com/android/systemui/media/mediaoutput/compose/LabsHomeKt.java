package com.android.systemui.media.mediaoutput.compose;

import android.content.Intent;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
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
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

public abstract class LabsHomeKt {
    public static final void LabsHome(final Function0 function0, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-743334390);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            ComposableSingletons$LabsHomeKt.INSTANCE.getClass();
            ActionBarKt.SecTitle(function0, "Labs", ComposableSingletons$LabsHomeKt.f55lambda6, composerImpl, (i2 & 14) | 432, 0);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$LabsHome$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    LabsHomeKt.LabsHome(Function0.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ActionLabs$1, kotlin.jvm.internal.Lambda] */
    public static final void access$ActionLabs(final LabsViewModel labsViewModel, Composer composer, final int i, final int i2) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(506053709);
        int i3 = i2 & 1;
        int i4 = i3 != 0 ? i | 2 : i;
        if (i3 == 1 && (i4 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) != 0 && !composerImpl.getDefaultsInvalid()) {
                composerImpl.skipToGroupEnd();
            } else if (i3 != 0) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i5 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i6 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                if (m2527exceptionOrNullimpl != null) {
                    m2527exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factory = (ViewModelProvider.Factory) failure;
                if (factory == null) {
                    factory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel = (LabsViewModel) viewModel;
            }
            composerImpl.endDefaults();
            OpaqueKey opaqueKey = ComposerKt.invocation;
            ListsKt.SecSubHeader("Actions", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(1062474114, composerImpl, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ActionLabs$1
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 81) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    MutableState collectAsState = SnapshotStateKt.collectAsState(LabsViewModel.this.isQuickboardInstalled, null, null, composer2, 56, 2);
                    final LabsViewModel labsViewModel2 = LabsViewModel.this;
                    ListsKt.SecListItem(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ActionLabs$1.1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            LabsViewModel labsViewModel3 = LabsViewModel.this;
                            labsViewModel3.getClass();
                            Intent intent = new Intent("com.android.systemui.action.OPEN_MEDIA_OUTPUT");
                            intent.setPackage(labsViewModel3.context.getPackageName());
                            intent.putExtra("force", true);
                            labsViewModel3.activityStarter.postStartActivityDismissingKeyguard(intent, 0);
                            return Unit.INSTANCE;
                        }
                    }, "Open Activity", null, composer2, 48, 4);
                    if (Intrinsics.areEqual((Boolean) collectAsState.getValue(), Boolean.TRUE)) {
                        final LabsViewModel labsViewModel3 = LabsViewModel.this;
                        ListsKt.SecListItem(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ActionLabs$1.2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                LabsViewModel labsViewModel4 = LabsViewModel.this;
                                labsViewModel4.getClass();
                                Intent intent = new Intent("com.samsung.android.mdx.quickboard.ACTION_OPEN_MEDIA_PANEL");
                                intent.setPackage("com.samsung.android.mdx.quickboard");
                                labsViewModel4.context.startForegroundService(intent);
                                return Unit.INSTANCE;
                            }
                        }, "Open Quickboard", null, composer2, 48, 4);
                    }
                    return Unit.INSTANCE;
                }
            }), composerImpl, 6);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ActionLabs$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    LabsHomeKt.access$ActionLabs(LabsViewModel.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ChromecastLabs$1, kotlin.jvm.internal.Lambda] */
    public static final void access$ChromecastLabs(final LabsViewModel labsViewModel, Composer composer, final int i, final int i2) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1172440810);
        int i3 = i2 & 1;
        int i4 = i3 != 0 ? i | 2 : i;
        if (i3 == 1 && (i4 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) != 0 && !composerImpl.getDefaultsInvalid()) {
                composerImpl.skipToGroupEnd();
            } else if (i3 != 0) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i5 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i6 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                if (m2527exceptionOrNullimpl != null) {
                    m2527exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factory = (ViewModelProvider.Factory) failure;
                if (factory == null) {
                    factory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel = (LabsViewModel) viewModel;
            }
            composerImpl.endDefaults();
            OpaqueKey opaqueKey = ComposerKt.invocation;
            ListsKt.SecSubHeader("Chromecast Labs", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(34124191, composerImpl, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ChromecastLabs$1
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 81) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    MutableState collectAsState = SnapshotStateKt.collectAsState(LabsViewModel.this.isSupportTransferableRoutesWhileConnecting, null, null, composer2, 56, 2);
                    MutableState collectAsState2 = SnapshotStateKt.collectAsState(LabsViewModel.this.isSupportForTransferDuringRouting, null, null, composer2, 56, 2);
                    MutableState collectAsState3 = SnapshotStateKt.collectAsState(LabsViewModel.this.isSupportSpotifyChromecast, null, null, composer2, 56, 2);
                    MutableState collectAsState4 = SnapshotStateKt.collectAsState(LabsViewModel.this.isSupportDisplayOnlyRemoteDevice, null, null, composer2, 56, 2);
                    final LabsViewModel labsViewModel2 = LabsViewModel.this;
                    ListsKt.SecSwitchListItem(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ChromecastLabs$1.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj4) {
                            LabsViewModel.this.setSupportTransferableRoutesWhileConnecting(((Boolean) obj4).booleanValue());
                            return Unit.INSTANCE;
                        }
                    }, "Support Transferable Routes While Connecting", null, (Boolean) collectAsState.getValue(), composer2, 48, 4);
                    final LabsViewModel labsViewModel3 = LabsViewModel.this;
                    ListsKt.SecSwitchListItem(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ChromecastLabs$1.2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj4) {
                            LabsViewModel.this.setSupportForTransferDuringRouting(((Boolean) obj4).booleanValue());
                            return Unit.INSTANCE;
                        }
                    }, "Support For Transfer During Routing", null, (Boolean) collectAsState2.getValue(), composer2, 48, 4);
                    final LabsViewModel labsViewModel4 = LabsViewModel.this;
                    ListsKt.SecSwitchListItem(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ChromecastLabs$1.3
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj4) {
                            LabsViewModel.this.setSupportSpotifyChromecast(((Boolean) obj4).booleanValue());
                            return Unit.INSTANCE;
                        }
                    }, "Support Spotify Chromecast", null, (Boolean) collectAsState3.getValue(), composer2, 48, 4);
                    final LabsViewModel labsViewModel5 = LabsViewModel.this;
                    ListsKt.SecSwitchListItem(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ChromecastLabs$1.4
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj4) {
                            LabsViewModel.this.setSupportDisplayOnlyRemoteDevice(((Boolean) obj4).booleanValue());
                            return Unit.INSTANCE;
                        }
                    }, "Support Display Only Remote Device", null, (Boolean) collectAsState4.getValue(), composer2, 48, 4);
                    return Unit.INSTANCE;
                }
            }), composerImpl, 6);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ChromecastLabs$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    LabsHomeKt.access$ChromecastLabs(LabsViewModel.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.media.mediaoutput.compose.LabsHomeKt$DebugLabs$1, kotlin.jvm.internal.Lambda] */
    public static final void access$DebugLabs(final LabsViewModel labsViewModel, Composer composer, final int i, final int i2) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1324084820);
        int i3 = i2 & 1;
        int i4 = i3 != 0 ? i | 2 : i;
        if (i3 == 1 && (i4 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) != 0 && !composerImpl.getDefaultsInvalid()) {
                composerImpl.skipToGroupEnd();
            } else if (i3 != 0) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i5 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i6 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                if (m2527exceptionOrNullimpl != null) {
                    m2527exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factory = (ViewModelProvider.Factory) failure;
                if (factory == null) {
                    factory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel = (LabsViewModel) viewModel;
            }
            composerImpl.endDefaults();
            OpaqueKey opaqueKey = ComposerKt.invocation;
            ListsKt.SecSubHeader("Debug Labs", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(494979543, composerImpl, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$DebugLabs$1
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 81) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    MutableState collectAsState = SnapshotStateKt.collectAsState(LabsViewModel.this.isGrayscaleThumbnail, null, null, composer2, 56, 2);
                    MutableState collectAsState2 = SnapshotStateKt.collectAsState(LabsViewModel.this.isSupportMultipleMediaSession, null, null, composer2, 56, 2);
                    MutableState collectAsState3 = SnapshotStateKt.collectAsState(LabsViewModel.this.isSupportSelectableBudsTogether, null, null, composer2, 56, 2);
                    MutableState collectAsState4 = SnapshotStateKt.collectAsState(LabsViewModel.this.isSupportDisplayDeviceVolumeControl, null, null, composer2, 56, 2);
                    final LabsViewModel labsViewModel2 = LabsViewModel.this;
                    ListsKt.SecSwitchListItem(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$DebugLabs$1.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj4) {
                            LabsViewModel.this.setGrayscaleThumbnail(((Boolean) obj4).booleanValue());
                            return Unit.INSTANCE;
                        }
                    }, "Grayscale thumbnail", null, (Boolean) collectAsState.getValue(), composer2, 48, 4);
                    final LabsViewModel labsViewModel3 = LabsViewModel.this;
                    ListsKt.SecSwitchListItem(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$DebugLabs$1.2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj4) {
                            LabsViewModel.this.setSupportMultipleMediaSession(((Boolean) obj4).booleanValue());
                            return Unit.INSTANCE;
                        }
                    }, "Support Multiple Media Session", null, (Boolean) collectAsState2.getValue(), composer2, 48, 4);
                    final LabsViewModel labsViewModel4 = LabsViewModel.this;
                    ListsKt.SecSwitchListItem(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$DebugLabs$1.3
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj4) {
                            LabsViewModel.this.setSupportSelectableBudsTogether(((Boolean) obj4).booleanValue());
                            return Unit.INSTANCE;
                        }
                    }, "Support Selectable BudsTogether", null, (Boolean) collectAsState3.getValue(), composer2, 48, 4);
                    final LabsViewModel labsViewModel5 = LabsViewModel.this;
                    ListsKt.SecSwitchListItem(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$DebugLabs$1.4
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj4) {
                            LabsViewModel.this.setSupportDisplayDeviceVolumeControl(((Boolean) obj4).booleanValue());
                            return Unit.INSTANCE;
                        }
                    }, "Support Display Device Volume Control", null, (Boolean) collectAsState4.getValue(), composer2, 48, 4);
                    return Unit.INSTANCE;
                }
            }), composerImpl, 6);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$DebugLabs$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    LabsHomeKt.access$DebugLabs(LabsViewModel.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.media.mediaoutput.compose.LabsHomeKt$Labs$1, kotlin.jvm.internal.Lambda] */
    public static final void access$Labs(final LabsViewModel labsViewModel, Composer composer, final int i, final int i2) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-16641565);
        int i3 = i2 & 1;
        int i4 = i3 != 0 ? i | 2 : i;
        if (i3 == 1 && (i4 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) != 0 && !composerImpl.getDefaultsInvalid()) {
                composerImpl.skipToGroupEnd();
            } else if (i3 != 0) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i5 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i6 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                if (m2527exceptionOrNullimpl != null) {
                    m2527exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factory = (ViewModelProvider.Factory) failure;
                if (factory == null) {
                    factory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel = (LabsViewModel) viewModel;
            }
            composerImpl.endDefaults();
            OpaqueKey opaqueKey = ComposerKt.invocation;
            ListsKt.SecSubHeader("Labs", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(744991448, composerImpl, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$Labs$1
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 81) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    MutableState collectAsState = SnapshotStateKt.collectAsState(LabsViewModel.this.isCloseOnTouchOutside, null, null, composer2, 56, 2);
                    MutableState collectAsState2 = SnapshotStateKt.collectAsState(LabsViewModel.this.isSupportVolumeInteraction, null, null, composer2, 56, 2);
                    MutableState collectAsState3 = SnapshotStateKt.collectAsState(LabsViewModel.this.isGroupSpeakerDefaultExpanded, null, null, composer2, 56, 2);
                    final LabsViewModel labsViewModel2 = LabsViewModel.this;
                    ListsKt.SecSwitchListItem(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$Labs$1.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj4) {
                            LabsViewModel.this.setCloseOnTouchOutside(((Boolean) obj4).booleanValue());
                            return Unit.INSTANCE;
                        }
                    }, "Close On Touch Outside", null, (Boolean) collectAsState.getValue(), composer2, 48, 4);
                    final LabsViewModel labsViewModel3 = LabsViewModel.this;
                    ListsKt.SecSwitchListItem(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$Labs$1.2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj4) {
                            LabsViewModel.this.setSupportVolumeInteraction(((Boolean) obj4).booleanValue());
                            return Unit.INSTANCE;
                        }
                    }, "Support Volume Interaction", null, (Boolean) collectAsState2.getValue(), composer2, 48, 4);
                    final LabsViewModel labsViewModel4 = LabsViewModel.this;
                    ListsKt.SecSwitchListItem(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$Labs$1.3
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj4) {
                            LabsViewModel.this.setGroupSpeakerDefaultExpanded(((Boolean) obj4).booleanValue());
                            return Unit.INSTANCE;
                        }
                    }, "Group Speaker Default Expanded", null, (Boolean) collectAsState3.getValue(), composer2, 48, 4);
                    return Unit.INSTANCE;
                }
            }), composerImpl, 6);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$Labs$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    LabsHomeKt.access$Labs(LabsViewModel.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.media.mediaoutput.compose.LabsHomeKt$SmartThingsLabs$1, kotlin.jvm.internal.Lambda] */
    public static final void access$SmartThingsLabs(final LabsViewModel labsViewModel, Composer composer, final int i, final int i2) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1156387791);
        int i3 = i2 & 1;
        int i4 = i3 != 0 ? i | 2 : i;
        if (i3 == 1 && (i4 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) != 0 && !composerImpl.getDefaultsInvalid()) {
                composerImpl.skipToGroupEnd();
            } else if (i3 != 0) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i5 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i6 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                if (m2527exceptionOrNullimpl != null) {
                    m2527exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factory = (ViewModelProvider.Factory) failure;
                if (factory == null) {
                    factory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel = (LabsViewModel) viewModel;
            }
            composerImpl.endDefaults();
            OpaqueKey opaqueKey = ComposerKt.invocation;
            ListsKt.SecSubHeader("SmartThings Labs", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(-2084464612, composerImpl, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$SmartThingsLabs$1
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 81) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    MutableState collectAsState = SnapshotStateKt.collectAsState(LabsViewModel.this.isSupportForUnsupportedTV, null, null, composer2, 56, 2);
                    final LabsViewModel labsViewModel2 = LabsViewModel.this;
                    ListsKt.SecSwitchListItem(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$SmartThingsLabs$1.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj4) {
                            LabsViewModel.this.setSupportForUnsupportedTV(((Boolean) obj4).booleanValue());
                            return Unit.INSTANCE;
                        }
                    }, "Support for unsupported TV", null, (Boolean) collectAsState.getValue(), composer2, 48, 4);
                    return Unit.INSTANCE;
                }
            }), composerImpl, 6);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$SmartThingsLabs$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    LabsHomeKt.access$SmartThingsLabs(LabsViewModel.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
