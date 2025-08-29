package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
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

/* loaded from: classes2.dex */
public abstract class TvScreenKt {
    public static final void TvScreen(final Function1 function1, final MediaDeviceViewModel mediaDeviceViewModel, final DeviceAudioPathViewModel deviceAudioPathViewModel, Composer composer, final int i) {
        Object failure;
        Object failure2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-383217374);
        if ((((composerImpl.changedInstance(function1) ? 4 : 2) | i | 144) & 147) == 146 && composerImpl.getSkipping()) {
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
                ViewModel viewModel = ViewModelKt.get(current, MediaDeviceViewModel.class, factoryCreateDaggerViewModelFactory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                MediaDeviceViewModel mediaDeviceViewModel2 = (MediaDeviceViewModel) viewModel;
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
                    int i4 = Result.$r8$clinit;
                    failure2 = new Result.Failure(th2);
                }
                Throwable thM3441exceptionOrNullimpl2 = Result.m3441exceptionOrNullimpl(failure2);
                if (thM3441exceptionOrNullimpl2 != null) {
                    thM3441exceptionOrNullimpl2.printStackTrace();
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory2 = (ViewModelProvider.Factory) (failure2 instanceof Result.Failure ? null : failure2);
                if (factoryCreateDaggerViewModelFactory2 == null) {
                    factoryCreateDaggerViewModelFactory2 = ViewModelKt.createDaggerViewModelFactory(current2);
                }
                ViewModel viewModel2 = ViewModelKt.get(current2, DeviceAudioPathViewModel.class, factoryCreateDaggerViewModelFactory2, defaultViewModelCreationExtras2);
                composerImpl.end(false);
                deviceAudioPathViewModel = (DeviceAudioPathViewModel) viewModel2;
                mediaDeviceViewModel = mediaDeviceViewModel2;
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.TvScreen (TvScreen.kt:19)");
            }
            SnackbarScaffoldKt.SnackbarScaffold(null, null, null, ComposableLambdaKt.rememberComposableLambda(-1289229168, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.TvScreenKt.TvScreen.1
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
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.TvScreen.<anonymous> (TvScreen.kt:21)");
                            }
                            final MediaDeviceViewModel mediaDeviceViewModel3 = mediaDeviceViewModel;
                            final DeviceAudioPathViewModel deviceAudioPathViewModel2 = deviceAudioPathViewModel;
                            ContainerBoxKt.ContainerBox(false, null, null, null, ComposableLambdaKt.rememberComposableLambda(1041843797, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.TvScreenKt.TvScreen.1.1
                                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                @Override // kotlin.jvm.functions.Function2
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj4, Object obj5) {
                                    Composer composer3 = (Composer) obj4;
                                    if ((((Number) obj5).intValue() & 3) == 2) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.TvScreen.<anonymous>.<anonymous> (TvScreen.kt:22)");
                                            }
                                            ProvidedValue[] providedValueArr = {CompositionExtKt.LocalMediaInteraction.defaultProvidedValue$runtime_release(mediaDeviceViewModel3), CompositionExtKt.LocalAudioPathInteraction.defaultProvidedValue$runtime_release(deviceAudioPathViewModel2)};
                                            ComposableSingletons$TvScreenKt.INSTANCE.getClass();
                                            CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableSingletons$TvScreenKt.f81lambda1, composer3, 56);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composer2), composer2, 24582);
                            ContainerBoxKt.SettingButton(function1, false, null, null, composer2, 48, 12);
                            ContainerBoxKt.ActionButton(function1, null, null, null, false, composer2, 24576);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 3072, 7);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(mediaDeviceViewModel, deviceAudioPathViewModel, i) { // from class: com.android.systemui.media.mediaoutput.compose.TvScreenKt$$ExternalSyntheticLambda0
                public final /* synthetic */ MediaDeviceViewModel f$1;
                public final /* synthetic */ DeviceAudioPathViewModel f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    MediaDeviceViewModel mediaDeviceViewModel3 = this.f$1;
                    DeviceAudioPathViewModel deviceAudioPathViewModel2 = this.f$2;
                    TvScreenKt.TvScreen(this.f$0, mediaDeviceViewModel3, deviceAudioPathViewModel2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
