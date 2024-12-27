package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.EnterTransitionImpl;
import androidx.compose.animation.ExitTransitionImpl;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.common.MediaOutputState;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt;
import com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt;
import com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class PhoneScreenKt {
    /* JADX WARN: Type inference failed for: r0v15, types: [com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$PhoneScreen$1, kotlin.jvm.internal.Lambda] */
    public static final void PhoneScreen(final Function1 function1, final MediaSessionViewModel mediaSessionViewModel, final SessionAudioPathViewModel sessionAudioPathViewModel, Composer composer, final int i, final int i2) {
        int i3;
        Object failure;
        Object failure2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1937288248);
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
                    ViewModel viewModel = ViewModelKt.get(current, MediaSessionViewModel.class, factory, defaultViewModelCreationExtras);
                    composerImpl.end(false);
                    mediaSessionViewModel = (MediaSessionViewModel) viewModel;
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
                    ViewModel viewModel2 = ViewModelKt.get(current2, SessionAudioPathViewModel.class, factory2, defaultViewModelCreationExtras2);
                    composerImpl.end(false);
                    sessionAudioPathViewModel = (SessionAudioPathViewModel) viewModel2;
                }
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            OpaqueKey opaqueKey = ComposerKt.invocation;
            SnackbarScaffoldKt.SnackbarScaffold(ComposableLambdaKt.rememberComposableLambda(-1473404381, composerImpl, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$PhoneScreen$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$PhoneScreen$1$2, reason: invalid class name */
                final class AnonymousClass2 extends SuspendLambda implements Function2 {
                    final /* synthetic */ MediaOutputState $mediaOutputState;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass2(MediaOutputState mediaOutputState, Continuation continuation) {
                        super(2, continuation);
                        this.$mediaOutputState = mediaOutputState;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass2(this.$mediaOutputState, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        if (this.label != 0) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        ((Feature) this.$mediaOutputState).setState(MediaOutputState.StateInfo.Showing);
                        return Unit.INSTANCE;
                    }
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$PhoneScreen$1$1, kotlin.jvm.internal.Lambda] */
                /* JADX WARN: Type inference failed for: r10v11, types: [com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$PhoneScreen$1$3, kotlin.jvm.internal.Lambda] */
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
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    MediaOutputState mediaOutputState = (MediaOutputState) composerImpl3.consume(CompositionExtKt.LocalMediaOutputState);
                    MediaOutputState.Companion.getClass();
                    State rememberButtonVisibleState = MediaOutputState.Companion.rememberButtonVisibleState(composerImpl3);
                    final MediaSessionViewModel mediaSessionViewModel2 = MediaSessionViewModel.this;
                    final SessionAudioPathViewModel sessionAudioPathViewModel2 = sessionAudioPathViewModel;
                    ContainerBoxKt.ContainerBox(true, null, null, null, ComposableLambdaKt.rememberComposableLambda(-1788909848, composerImpl3, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$PhoneScreen$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj4, Object obj5) {
                            Composer composer3 = (Composer) obj4;
                            if ((((Number) obj5).intValue() & 11) == 2) {
                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                if (composerImpl4.getSkipping()) {
                                    composerImpl4.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey3 = ComposerKt.invocation;
                            ProvidedValue[] providedValueArr = {CompositionExtKt.LocalMediaInteraction.defaultProvidedValue$runtime_release(MediaSessionViewModel.this), CompositionExtKt.LocalAudioPathInteraction.defaultProvidedValue$runtime_release(sessionAudioPathViewModel2)};
                            ComposableSingletons$PhoneScreenKt.INSTANCE.getClass();
                            CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableSingletons$PhoneScreenKt.f64lambda1, composer3, 56);
                            return Unit.INSTANCE;
                        }
                    }), composerImpl3, 24582, 14);
                    EffectsKt.LaunchedEffect(composerImpl3, Unit.INSTANCE, new AnonymousClass2(mediaOutputState, null));
                    boolean booleanValue = ((Boolean) rememberButtonVisibleState.getValue()).booleanValue();
                    EnterTransitionImpl fadeIn$default = EnterExitTransitionKt.fadeIn$default(null, 3);
                    ExitTransitionImpl fadeOut$default = EnterExitTransitionKt.fadeOut$default(null, 3);
                    final Function1 function12 = function1;
                    AnimatedVisibilityKt.AnimatedVisibility(booleanValue, (Modifier) null, fadeIn$default, fadeOut$default, (String) null, ComposableLambdaKt.rememberComposableLambda(424517195, composerImpl3, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$PhoneScreen$1.3
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj4, Object obj5, Object obj6) {
                            Composer composer3 = (Composer) obj5;
                            ((Number) obj6).intValue();
                            OpaqueKey opaqueKey3 = ComposerKt.invocation;
                            ContainerBoxKt.SettingButton(Function1.this, true, null, composer3, 48, 4);
                            ContainerBoxKt.ActionButton(Function1.this, null, null, null, true, composer3, 24576, 14);
                            return Unit.INSTANCE;
                        }
                    }), composerImpl3, 200064, 18);
                    return Unit.INSTANCE;
                }
            }), composerImpl, 6);
        }
        final MediaSessionViewModel mediaSessionViewModel2 = mediaSessionViewModel;
        final SessionAudioPathViewModel sessionAudioPathViewModel2 = sessionAudioPathViewModel;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$PhoneScreen$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    PhoneScreenKt.PhoneScreen(Function1.this, mediaSessionViewModel2, sessionAudioPathViewModel2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
