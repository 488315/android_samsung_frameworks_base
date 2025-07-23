package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
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
import com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt;
import com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class PhoneScreenKt {
    public static final void PhoneScreen(final Function1 function1, final MediaSessionViewModel mediaSessionViewModel, final SessionAudioPathViewModel sessionAudioPathViewModel, Composer composer, final int i) {
        int i2;
        Object failure;
        Object failure2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1937288248);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function1) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if (((i2 | 144) & 147) == 146 && composerImpl.getSkipping()) {
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
                    int i3 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i4 = Result.$r8$clinit;
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
                ViewModel viewModel = ViewModelKt.get(current, MediaSessionViewModel.class, factory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                MediaSessionViewModel mediaSessionViewModel2 = (MediaSessionViewModel) viewModel;
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
                    int i5 = Result.$r8$clinit;
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
                ViewModel viewModel2 = ViewModelKt.get(current2, SessionAudioPathViewModel.class, factory2, defaultViewModelCreationExtras2);
                composerImpl.end(false);
                sessionAudioPathViewModel = (SessionAudioPathViewModel) viewModel2;
                mediaSessionViewModel = mediaSessionViewModel2;
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.PhoneScreen (PhoneScreen.kt:29)");
            }
            SnackbarScaffoldKt.SnackbarScaffold(null, null, null, ComposableLambdaKt.rememberComposableLambda(-682313306, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$PhoneScreen$1
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0077, code lost:
                
                    if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:19:0x00b8, code lost:
                
                    if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r12, java.lang.Object r13, java.lang.Object r14) {
                    /*
                        Method dump skipped, instructions count: 259
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$PhoneScreen$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 3072, 7);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    MediaSessionViewModel mediaSessionViewModel3 = mediaSessionViewModel;
                    SessionAudioPathViewModel sessionAudioPathViewModel2 = sessionAudioPathViewModel;
                    PhoneScreenKt.PhoneScreen(Function1.this, mediaSessionViewModel3, sessionAudioPathViewModel2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
