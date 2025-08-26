package com.android.systemui.media.mediaoutput.compose;

import androidx.activity.compose.BackHandlerKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigator;
import androidx.navigation.compose.NavHostControllerKt;
import androidx.navigation.compose.NavHostKt;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.compose.common.DismissCallback;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.common.MediaOutputState;
import com.android.systemui.media.mediaoutput.compose.common.MediaOutputState$Companion$$ExternalSyntheticLambda0;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import com.samsung.sesl.compose.theme.ThemeKt;
import java.util.List;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public abstract class MediaOutputHostKt {
    public static final void MediaOutputHost(final LabsViewModel labsViewModel, Composer composer, final int i) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1378427780);
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
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaOutputHost (MediaOutputHost.kt:39)");
            }
            final MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(labsViewModel.isCloseOnTouchOutside, Boolean.TRUE, null, composerImpl, 48, 2);
            final MutableState mutableStateCollectAsState2 = SnapshotStateKt.collectAsState(labsViewModel.isSupportMultipleMediaSession, Boolean.FALSE, null, composerImpl, 48, 2);
            ThemeKt.SeslTheme(false, null, ComposableLambdaKt.rememberComposableLambda(-767531723, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt.MediaOutputHost.1
                /* JADX WARN: Removed duplicated region for block: B:21:0x00ab  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous> (MediaOutputHost.kt:44)");
                            }
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            final Feature feature = (Feature) composerImpl3.consume(CompositionExtKt.LocalFeature);
                            final DismissCallback dismissCallback = (DismissCallback) composerImpl3.consume(CompositionExtKt.LocalDismissCallback);
                            final NavHostController navHostControllerRememberNavController = NavHostControllerKt.rememberNavController(new Navigator[0], composerImpl3);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.navigation.compose.currentBackStackEntryAsState (NavHostController.kt:41)");
                            }
                            final MutableState mutableStateCollectAsState3 = SnapshotStateKt.collectAsState(navHostControllerRememberNavController.currentBackStackEntryFlow, null, null, composerImpl3, 48, 2);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            final ReadonlyStateFlow readonlyStateFlow = navHostControllerRememberNavController.visibleEntries;
                            final MutableState mutableStateCollectAsState4 = SnapshotStateKt.collectAsState(new Flow() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$invoke$$inlined$map$1

                                /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$invoke$$inlined$map$1$2, reason: invalid class name */
                                public final class AnonymousClass2 implements FlowCollector {
                                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                    /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$invoke$$inlined$map$1$2$1, reason: invalid class name */
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
                                            Boolean boolValueOf = Boolean.valueOf(((List) obj).size() > 1);
                                            anonymousClass1.label = 1;
                                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                                    Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                                }
                            }, Boolean.FALSE, null, composerImpl3, 48, 2);
                            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
                            composerImpl3.startReplaceGroup(1628838396);
                            final State state = mutableStateCollectAsState;
                            boolean zChanged = composerImpl3.changed(state) | composerImpl3.changed(mutableStateCollectAsState4) | composerImpl3.changedInstance(navHostControllerRememberNavController) | composerImpl3.changedInstance(dismissCallback);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            if (!zChanged) {
                                Composer.Companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$$ExternalSyntheticLambda0
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            if (((Boolean) state.getValue()).booleanValue() && !((Boolean) mutableStateCollectAsState4.getValue()).booleanValue() && !navHostControllerRememberNavController.popBackStack()) {
                                                ((Feature) dismissCallback).animateDismiss();
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl3.end(false);
                                Modifier modifierM34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(modifierFillMaxWidth, null, null, false, null, null, (Function0) objRememberedValue, 28);
                                Color.Companion.getClass();
                                long j = Color.Transparent;
                                MaterialTheme.INSTANCE.getClass();
                                long j2 = MaterialTheme.getColorScheme(composerImpl3).onSurfaceVariant;
                                final State state2 = mutableStateCollectAsState2;
                                SurfaceKt.m304SurfaceT9BRK9s(modifierM34clickableO2vRcR0$default, null, j, j2, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-1098278342, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt.MediaOutputHost.1.2
                                    /* JADX WARN: Removed duplicated region for block: B:41:0x010f  */
                                    /* JADX WARN: Removed duplicated region for block: B:49:0x0130  */
                                    /* JADX WARN: Removed duplicated region for block: B:52:0x0145  */
                                    /* JADX WARN: Removed duplicated region for block: B:56:0x0171  */
                                    /* JADX WARN: Removed duplicated region for block: B:59:0x0197  */
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                                    @Override // kotlin.jvm.functions.Function2
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj3, Object obj4) throws Throwable {
                                        Object objRememberedValue2;
                                        Object objM;
                                        State state3;
                                        boolean zChanged2;
                                        Object objRememberedValue3;
                                        Composer composer3 = (Composer) obj3;
                                        if ((((Number) obj4).intValue() & 3) == 2) {
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            if (composerImpl4.getSkipping()) {
                                                composerImpl4.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous>.<anonymous> (MediaOutputHost.kt:67)");
                                                }
                                                MediaOutputState.Companion.getClass();
                                                ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                composerImpl5.startReplaceGroup(1404299270);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.common.MediaOutputState.Companion.rememberDismissState (Feature.kt:162)");
                                                }
                                                State stateRememberMediaOutputState = MediaOutputState.Companion.rememberMediaOutputState(composerImpl5);
                                                composerImpl5.startReplaceGroup(1751720735);
                                                Object objRememberedValue4 = composerImpl5.rememberedValue();
                                                Composer.Companion.getClass();
                                                Object obj5 = Composer.Companion.Empty;
                                                if (objRememberedValue4 == obj5) {
                                                    objRememberedValue4 = SnapshotStateKt.derivedStateOf(new MediaOutputState$Companion$$ExternalSyntheticLambda0(stateRememberMediaOutputState, 2));
                                                    composerImpl5.updateRememberedValue(objRememberedValue4);
                                                }
                                                State state4 = (State) objRememberedValue4;
                                                composerImpl5.end(false);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                Object objM2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl5, false, 691728190);
                                                if (objM2 == obj5) {
                                                    objM2 = SnapshotStateKt.mutableStateOf$default(null);
                                                    composerImpl5.updateRememberedValue(objM2);
                                                }
                                                MutableState mutableState = (MutableState) objM2;
                                                composerImpl5.end(false);
                                                boolean z = !((Boolean) state4.getValue()).booleanValue();
                                                composerImpl5.startReplaceGroup(691730888);
                                                NavHostController navHostController = navHostControllerRememberNavController;
                                                boolean zChangedInstance = composerImpl5.changedInstance(navHostController);
                                                DismissCallback dismissCallback2 = dismissCallback;
                                                boolean zChangedInstance2 = zChangedInstance | composerImpl5.changedInstance(dismissCallback2);
                                                Object objRememberedValue5 = composerImpl5.rememberedValue();
                                                if (zChangedInstance2 || objRememberedValue5 == obj5) {
                                                    objRememberedValue5 = new MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda0(navHostController, dismissCallback2, 0);
                                                    composerImpl5.updateRememberedValue(objRememberedValue5);
                                                }
                                                composerImpl5.end(false);
                                                BackHandlerKt.BackHandler(z, (Function0) objRememberedValue5, composerImpl5, 0, 0);
                                                State state5 = mutableStateCollectAsState3;
                                                NavBackStackEntry navBackStackEntry = (NavBackStackEntry) state5.getValue();
                                                composerImpl5.startReplaceGroup(691737028);
                                                boolean zChanged3 = composerImpl5.changed(state5);
                                                Object objRememberedValue6 = composerImpl5.rememberedValue();
                                                if (zChanged3 || objRememberedValue6 == obj5) {
                                                    objRememberedValue6 = new MediaOutputHostKt$MediaOutputHost$1$2$2$1(state5, mutableState, null);
                                                    composerImpl5.updateRememberedValue(objRememberedValue6);
                                                }
                                                composerImpl5.end(false);
                                                EffectsKt.LaunchedEffect(composerImpl5, navBackStackEntry, (Function2) objRememberedValue6);
                                                Feature.Companion.getClass();
                                                Feature feature2 = feature;
                                                List list = feature2.deviceIds;
                                                if (list == null) {
                                                    String str = feature2.packageName;
                                                    String str2 = StringsKt__StringsKt.isBlank(str) ? null : str;
                                                    String str3 = str2 == null ? "-" : str2;
                                                    String strCreateRoute = feature2.defaultScreen.createRoute(str3);
                                                    composerImpl5.startReplaceGroup(691754377);
                                                    objRememberedValue2 = composerImpl5.rememberedValue();
                                                    if (objRememberedValue2 == obj5) {
                                                        final int i4 = 0;
                                                        objRememberedValue2 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda1
                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj6) {
                                                                switch (i4) {
                                                                    case 0:
                                                                        return EnterExitTransitionKt.fadeIn$default(null, 3);
                                                                    default:
                                                                        return EnterExitTransitionKt.fadeOut$default(null, 3);
                                                                }
                                                            }
                                                        };
                                                        composerImpl5.updateRememberedValue(objRememberedValue2);
                                                    }
                                                    Function1 function1 = (Function1) objRememberedValue2;
                                                    objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl5, false, 691755882);
                                                    if (objM == obj5) {
                                                        final int i5 = 1;
                                                        objM = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda1
                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj6) {
                                                                switch (i5) {
                                                                    case 0:
                                                                        return EnterExitTransitionKt.fadeIn$default(null, 3);
                                                                    default:
                                                                        return EnterExitTransitionKt.fadeOut$default(null, 3);
                                                                }
                                                            }
                                                        };
                                                        composerImpl5.updateRememberedValue(objM);
                                                    }
                                                    Function1 function12 = (Function1) objM;
                                                    composerImpl5.end(false);
                                                    composerImpl5.startReplaceGroup(691758994);
                                                    boolean zChangedInstance3 = composerImpl5.changedInstance(navHostController);
                                                    state3 = state2;
                                                    zChanged2 = zChangedInstance3 | composerImpl5.changed(state3) | composerImpl5.changedInstance(dismissCallback2);
                                                    objRememberedValue3 = composerImpl5.rememberedValue();
                                                    if (!zChanged2 || objRememberedValue3 == obj5) {
                                                        objRememberedValue3 = new MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda3(navHostController, state3, dismissCallback2);
                                                        composerImpl5.updateRememberedValue(objRememberedValue3);
                                                    }
                                                    composerImpl5.end(false);
                                                    NavHostKt.NavHost(navHostControllerRememberNavController, strCreateRoute, null, null, null, function1, function12, null, null, null, (Function1) objRememberedValue3, composerImpl5, 1769472, 0, 924);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                } else {
                                                    if (list.size() != 1) {
                                                        list = null;
                                                    }
                                                    if (list == null || (str3 = (String) CollectionsKt___CollectionsKt.firstOrNull(list)) == null) {
                                                    }
                                                    String strCreateRoute2 = feature2.defaultScreen.createRoute(str3);
                                                    composerImpl5.startReplaceGroup(691754377);
                                                    objRememberedValue2 = composerImpl5.rememberedValue();
                                                    if (objRememberedValue2 == obj5) {
                                                    }
                                                    Function1 function13 = (Function1) objRememberedValue2;
                                                    objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl5, false, 691755882);
                                                    if (objM == obj5) {
                                                    }
                                                    Function1 function122 = (Function1) objM;
                                                    composerImpl5.end(false);
                                                    composerImpl5.startReplaceGroup(691758994);
                                                    boolean zChangedInstance32 = composerImpl5.changedInstance(navHostController);
                                                    state3 = state2;
                                                    zChanged2 = zChangedInstance32 | composerImpl5.changed(state3) | composerImpl5.changedInstance(dismissCallback2);
                                                    objRememberedValue3 = composerImpl5.rememberedValue();
                                                    if (!zChanged2) {
                                                        objRememberedValue3 = new MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda3(navHostController, state3, dismissCallback2);
                                                        composerImpl5.updateRememberedValue(objRememberedValue3);
                                                        composerImpl5.end(false);
                                                        NavHostKt.NavHost(navHostControllerRememberNavController, strCreateRoute2, null, null, null, function13, function122, null, null, null, (Function1) objRememberedValue3, composerImpl5, 1769472, 0, 924);
                                                        if (ComposerKt.isTraceInProgress()) {
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composerImpl3), composerImpl3, 12583296, 114);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 384, 3);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(i) { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    MediaOutputHostKt.MediaOutputHost(this.f$0, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
