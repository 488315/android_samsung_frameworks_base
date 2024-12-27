package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt;
import com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt;
import com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class TvScreenKt {
    /* JADX WARN: Type inference failed for: r0v15, types: [com.android.systemui.media.mediaoutput.compose.TvScreenKt$TvScreen$1, kotlin.jvm.internal.Lambda] */
    public static final void TvScreen(final Function1 function1, final MediaDeviceViewModel mediaDeviceViewModel, final DeviceAudioPathViewModel deviceAudioPathViewModel, Composer composer, final int i, final int i2) {
        int i3;
        Object failure;
        Object failure2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-383217374);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 14) == 0) {
            i3 = (composerImpl.changedInstance(function1) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i4 = 2 & i2;
        if (i4 != 0) {
            i3 |= 16;
        }
        int i5 = 4 & i2;
        if (i5 != 0) {
            i3 |= 128;
        }
        if ((i2 & 6) == 6 && (i3 & 731) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if (i4 != 0) {
                    composerImpl.startReplaceGroup(1487631618);
                    LocalViewModelStoreOwner.INSTANCE.getClass();
                    ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                    if (current == null) {
                        throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                    }
                    CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                    try {
                        int i6 = Result.$r8$clinit;
                        failure2 = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                    } catch (Throwable th) {
                        int i7 = Result.$r8$clinit;
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
                    ViewModel viewModel = ViewModelKt.get(current, MediaDeviceViewModel.class, factory, defaultViewModelCreationExtras);
                    composerImpl.end(false);
                    mediaDeviceViewModel = (MediaDeviceViewModel) viewModel;
                }
                if (i5 != 0) {
                    composerImpl.startReplaceGroup(1487631618);
                    LocalViewModelStoreOwner.INSTANCE.getClass();
                    ViewModelStoreOwner current2 = LocalViewModelStoreOwner.getCurrent(composerImpl);
                    if (current2 == null) {
                        throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                    }
                    CreationExtras defaultViewModelCreationExtras2 = current2 instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current2).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                    try {
                        int i8 = Result.$r8$clinit;
                        failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                    } catch (Throwable th2) {
                        int i9 = Result.$r8$clinit;
                        failure = new Result.Failure(th2);
                    }
                    Throwable m2527exceptionOrNullimpl2 = Result.m2527exceptionOrNullimpl(failure);
                    if (m2527exceptionOrNullimpl2 != null) {
                        m2527exceptionOrNullimpl2.printStackTrace();
                    }
                    ViewModelProvider.Factory factory2 = (ViewModelProvider.Factory) (failure instanceof Result.Failure ? null : failure);
                    if (factory2 == null) {
                        factory2 = ViewModelKt.createDaggerViewModelFactory(current2);
                    }
                    ViewModel viewModel2 = ViewModelKt.get(current2, DeviceAudioPathViewModel.class, factory2, defaultViewModelCreationExtras2);
                    composerImpl.end(false);
                    deviceAudioPathViewModel = (DeviceAudioPathViewModel) viewModel2;
                }
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            OpaqueKey opaqueKey = ComposerKt.invocation;
            SnackbarScaffoldKt.SnackbarScaffold(ComposableLambdaKt.rememberComposableLambda(1595646477, composerImpl, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.TvScreenKt$TvScreen$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                /* JADX WARN: Type inference failed for: r10v4, types: [com.android.systemui.media.mediaoutput.compose.TvScreenKt$TvScreen$1$1, kotlin.jvm.internal.Lambda] */
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
                    final MediaDeviceViewModel mediaDeviceViewModel2 = mediaDeviceViewModel;
                    final DeviceAudioPathViewModel deviceAudioPathViewModel2 = deviceAudioPathViewModel;
                    ContainerBoxKt.ContainerBox(false, null, null, null, ComposableLambdaKt.rememberComposableLambda(-368247854, composer2, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.TvScreenKt$TvScreen$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj4, Object obj5) {
                            Composer composer3 = (Composer) obj4;
                            if ((((Number) obj5).intValue() & 11) == 2) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey3 = ComposerKt.invocation;
                            ProvidedValue[] providedValueArr = {CompositionExtKt.LocalMediaInteraction.defaultProvidedValue$runtime_release(MediaDeviceViewModel.this), CompositionExtKt.LocalAudioPathInteraction.defaultProvidedValue$runtime_release(deviceAudioPathViewModel2)};
                            ComposableSingletons$TvScreenKt.INSTANCE.getClass();
                            CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableSingletons$TvScreenKt.f68lambda1, composer3, 56);
                            return Unit.INSTANCE;
                        }
                    }), composer2, 24582, 14);
                    ContainerBoxKt.SettingButton(Function1.this, false, null, composer2, 48, 4);
                    ContainerBoxKt.ActionButton(Function1.this, null, null, null, false, composer2, 24576, 14);
                    return Unit.INSTANCE;
                }
            }), composerImpl, 6);
        }
        final MediaDeviceViewModel mediaDeviceViewModel2 = mediaDeviceViewModel;
        final DeviceAudioPathViewModel deviceAudioPathViewModel2 = deviceAudioPathViewModel;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.TvScreenKt$TvScreen$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    TvScreenKt.TvScreen(Function1.this, mediaDeviceViewModel2, deviceAudioPathViewModel2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
