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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    m3422exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factory = (ViewModelProvider.Factory) failure;
                if (factory == null) {
                    factory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, MediaDeviceViewModel.class, factory, defaultViewModelCreationExtras);
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
                Throwable m3422exceptionOrNullimpl2 = Result.m3422exceptionOrNullimpl(failure2);
                if (m3422exceptionOrNullimpl2 != null) {
                    m3422exceptionOrNullimpl2.printStackTrace();
                }
                ViewModelProvider.Factory factory2 = (ViewModelProvider.Factory) (failure2 instanceof Result.Failure ? null : failure2);
                if (factory2 == null) {
                    factory2 = ViewModelKt.createDaggerViewModelFactory(current2);
                }
                ViewModel viewModel2 = ViewModelKt.get(current2, DeviceAudioPathViewModel.class, factory2, defaultViewModelCreationExtras2);
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
            SnackbarScaffoldKt.SnackbarScaffold(null, null, null, ComposableLambdaKt.rememberComposableLambda(-1289229168, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.TvScreenKt$TvScreen$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.TvScreen.<anonymous> (TvScreen.kt:21)");
                    }
                    final MediaDeviceViewModel mediaDeviceViewModel3 = mediaDeviceViewModel;
                    final DeviceAudioPathViewModel deviceAudioPathViewModel2 = deviceAudioPathViewModel;
                    ContainerBoxKt.ContainerBox(false, null, null, null, ComposableLambdaKt.rememberComposableLambda(1041843797, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.TvScreenKt$TvScreen$1.1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj4, Object obj5) {
                            Composer composer3 = (Composer) obj4;
                            if ((((Number) obj5).intValue() & 3) == 2) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.TvScreen.<anonymous>.<anonymous> (TvScreen.kt:22)");
                            }
                            ProvidedValue[] providedValueArr = {CompositionExtKt.LocalMediaInteraction.defaultProvidedValue$runtime_release(MediaDeviceViewModel.this), CompositionExtKt.LocalAudioPathInteraction.defaultProvidedValue$runtime_release(deviceAudioPathViewModel2)};
                            ComposableSingletons$TvScreenKt.INSTANCE.getClass();
                            CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableSingletons$TvScreenKt.f81lambda1, composer3, 56);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composer2), composer2, 24582);
                    ContainerBoxKt.SettingButton(Function1.this, false, null, null, composer2, 48, 12);
                    ContainerBoxKt.ActionButton(Function1.this, null, null, null, false, composer2, 24576);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 3072, 7);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(mediaDeviceViewModel, deviceAudioPathViewModel, i) { // from class: com.android.systemui.media.mediaoutput.compose.TvScreenKt$$ExternalSyntheticLambda0
                public final /* synthetic */ MediaDeviceViewModel f$1;
                public final /* synthetic */ DeviceAudioPathViewModel f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    MediaDeviceViewModel mediaDeviceViewModel3 = this.f$1;
                    DeviceAudioPathViewModel deviceAudioPathViewModel2 = this.f$2;
                    TvScreenKt.TvScreen(Function1.this, mediaDeviceViewModel3, deviceAudioPathViewModel2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
