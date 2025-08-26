package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.media.mediaoutput.compose.common.MediaOutputState;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt;
import com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt;
import com.android.systemui.media.mediaoutput.controller.media.MediaSession;
import com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

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
                ViewModel viewModel = ViewModelKt.get(current, MediaSessionViewModel.class, factoryCreateDaggerViewModelFactory, defaultViewModelCreationExtras);
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
                Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(failure2);
                if (thM3442exceptionOrNullimpl2 != null) {
                    thM3442exceptionOrNullimpl2.printStackTrace();
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory2 = (ViewModelProvider.Factory) (failure2 instanceof Result.Failure ? null : failure2);
                if (factoryCreateDaggerViewModelFactory2 == null) {
                    factoryCreateDaggerViewModelFactory2 = ViewModelKt.createDaggerViewModelFactory(current2);
                }
                ViewModel viewModel2 = ViewModelKt.get(current2, SessionAudioPathViewModel.class, factoryCreateDaggerViewModelFactory2, defaultViewModelCreationExtras2);
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
            SnackbarScaffoldKt.SnackbarScaffold(null, null, null, ComposableLambdaKt.rememberComposableLambda(-682313306, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt.PhoneScreen.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x0079  */
                /* JADX WARN: Removed duplicated region for block: B:20:0x00ba  */
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
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.PhoneScreen.<anonymous> (PhoneScreen.kt:31)");
                            }
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            MediaOutputState mediaOutputState = (MediaOutputState) composerImpl3.consume(CompositionExtKt.LocalMediaOutputState);
                            MediaOutputState.Companion.getClass();
                            State stateRememberShownState = MediaOutputState.Companion.rememberShownState(composerImpl3);
                            final MediaSessionViewModel mediaSessionViewModel3 = mediaSessionViewModel;
                            final MediaSessionViewModel$special$$inlined$map$2 mediaSessionViewModel$special$$inlined$map$2 = mediaSessionViewModel3.currentSessionController;
                            final MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(new Flow() { // from class: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$PhoneScreen$1$invoke$$inlined$map$1

                                /* renamed from: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$PhoneScreen$1$invoke$$inlined$map$1$2, reason: invalid class name */
                                public final class AnonymousClass2 implements FlowCollector {
                                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                    /* renamed from: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$PhoneScreen$1$invoke$$inlined$map$1$2$1, reason: invalid class name */
                                    public final class AnonymousClass1 extends ContinuationImpl {
                                        Object L$0;
                                        int label;
                                        /* synthetic */ Object result;

                                        public AnonymousClass1(Continuation continuation) {
                                            super(continuation);
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Object invokeSuspend(Object obj) {
                                            this.result = obj;
                                            this.label |= Integer.MIN_VALUE;
                                            return AnonymousClass2.this.emit(null, this);
                                        }
                                    }

                                    public AnonymousClass2(FlowCollector flowCollector) {
                                        this.$this_unsafeFlow = flowCollector;
                                    }

                                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                                    @Override // kotlinx.coroutines.flow.FlowCollector
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object emit(Object obj, Continuation continuation) {
                                        AnonymousClass1 anonymousClass1;
                                        if (continuation instanceof AnonymousClass1) {
                                            anonymousClass1 = (AnonymousClass1) continuation;
                                            int i = anonymousClass1.label;
                                            if ((i & Integer.MIN_VALUE) != 0) {
                                                anonymousClass1.label = i - Integer.MIN_VALUE;
                                            } else {
                                                anonymousClass1 = new AnonymousClass1(continuation);
                                            }
                                        }
                                        Object obj2 = anonymousClass1.result;
                                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                        int i2 = anonymousClass1.label;
                                        if (i2 == 0) {
                                            ResultKt.throwOnFailure(obj2);
                                            String id = ((MediaSession) obj).getId();
                                            anonymousClass1.label = 1;
                                            if (this.$this_unsafeFlow.emit(id, anonymousClass1) == coroutineSingletons) {
                                                return coroutineSingletons;
                                            }
                                        } else {
                                            if (i2 != 1) {
                                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                            }
                                            ResultKt.throwOnFailure(obj2);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }

                                @Override // kotlinx.coroutines.flow.Flow
                                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                    Object objCollect = mediaSessionViewModel$special$$inlined$map$2.collect(new AnonymousClass2(flowCollector), continuation);
                                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                                }
                            }, "", null, composerImpl3, 48, 2);
                            String str = (String) mutableStateCollectAsState.getValue();
                            composerImpl3.startReplaceGroup(777121128);
                            final SessionAudioPathViewModel sessionAudioPathViewModel2 = sessionAudioPathViewModel;
                            boolean zChangedInstance = composerImpl3.changedInstance(sessionAudioPathViewModel2) | composerImpl3.changed(mutableStateCollectAsState);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            Composer.Companion companion = Composer.Companion;
                            if (!zChangedInstance) {
                                companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new PhoneScreenKt$PhoneScreen$1$1$1(sessionAudioPathViewModel2, mutableStateCollectAsState, null);
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl3.end(false);
                                EffectsKt.LaunchedEffect(composerImpl3, str, (Function2) objRememberedValue);
                                ContainerBoxKt.ContainerBox(true, null, null, null, ComposableLambdaKt.rememberComposableLambda(-997818773, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt.PhoneScreen.1.2
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                    @Override // kotlin.jvm.functions.Function2
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj4, Object obj5) {
                                        Composer composer3 = (Composer) obj4;
                                        if ((((Number) obj5).intValue() & 3) == 2) {
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            if (composerImpl4.getSkipping()) {
                                                composerImpl4.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.PhoneScreen.<anonymous>.<anonymous> (PhoneScreen.kt:41)");
                                                }
                                                ProvidedValue[] providedValueArr = {CompositionExtKt.LocalMediaInteraction.defaultProvidedValue$runtime_release(mediaSessionViewModel3), CompositionExtKt.LocalAudioPathInteraction.defaultProvidedValue$runtime_release(sessionAudioPathViewModel2)};
                                                ComposableSingletons$PhoneScreenKt.INSTANCE.getClass();
                                                CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableSingletons$PhoneScreenKt.f75lambda1, composer3, 56);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composerImpl3), composerImpl3, 24582);
                                Unit unit = Unit.INSTANCE;
                                composerImpl3.startReplaceGroup(777135648);
                                boolean zChangedInstance2 = composerImpl3.changedInstance(mediaOutputState);
                                Object objRememberedValue2 = composerImpl3.rememberedValue();
                                if (!zChangedInstance2) {
                                    companion.getClass();
                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                        objRememberedValue2 = new PhoneScreenKt$PhoneScreen$1$3$1(mediaOutputState, null);
                                        composerImpl3.updateRememberedValue(objRememberedValue2);
                                    }
                                    composerImpl3.end(false);
                                    EffectsKt.LaunchedEffect(composerImpl3, unit, (Function2) objRememberedValue2);
                                    boolean zBooleanValue = ((Boolean) stateRememberShownState.getValue()).booleanValue();
                                    EnterTransition enterTransitionFadeIn$default = EnterExitTransitionKt.fadeIn$default(null, 3);
                                    ExitTransition exitTransitionFadeOut$default = EnterExitTransitionKt.fadeOut$default(null, 3);
                                    final Function1 function12 = function1;
                                    AnimatedVisibilityKt.AnimatedVisibility(zBooleanValue, null, enterTransitionFadeIn$default, exitTransitionFadeOut$default, null, ComposableLambdaKt.rememberComposableLambda(1215608270, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt.PhoneScreen.1.4
                                        /* JADX WARN: Removed duplicated region for block: B:9:0x0032  */
                                        @Override // kotlin.jvm.functions.Function3
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj4, Object obj5, Object obj6) {
                                            Composer composer3 = (Composer) obj5;
                                            ((Number) obj6).intValue();
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.PhoneScreen.<anonymous>.<anonymous> (PhoneScreen.kt:58)");
                                            }
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            composerImpl4.startReplaceGroup(-1600739527);
                                            final State state = mutableStateCollectAsState;
                                            boolean zChanged = composerImpl4.changed(state);
                                            Object objRememberedValue3 = composerImpl4.rememberedValue();
                                            if (!zChanged) {
                                                Composer.Companion.getClass();
                                                if (objRememberedValue3 == Composer.Companion.Empty) {
                                                    objRememberedValue3 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$PhoneScreen$1$4$$ExternalSyntheticLambda0
                                                        @Override // kotlin.jvm.functions.Function0
                                                        public final Object invoke() {
                                                            return (String) state.getValue();
                                                        }
                                                    };
                                                    composerImpl4.updateRememberedValue(objRememberedValue3);
                                                }
                                            }
                                            composerImpl4.end(false);
                                            ContainerBoxKt.SettingButton(function12, true, (Function0) objRememberedValue3, null, composerImpl4, 48, 8);
                                            ContainerBoxKt.ActionButton(function12, null, null, null, true, composerImpl4, 24576);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl3), composerImpl3, 200064, 18);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
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
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.PhoneScreenKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    MediaSessionViewModel mediaSessionViewModel3 = mediaSessionViewModel;
                    SessionAudioPathViewModel sessionAudioPathViewModel2 = sessionAudioPathViewModel;
                    PhoneScreenKt.PhoneScreen(function1, mediaSessionViewModel3, sessionAudioPathViewModel2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
