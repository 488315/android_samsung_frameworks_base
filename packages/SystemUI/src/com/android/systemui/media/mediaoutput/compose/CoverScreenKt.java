package com.android.systemui.media.mediaoutput.compose;

import android.content.Context;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.foundation.BackgroundElement;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.foundation.layout.WindowInsetsSides;
import androidx.compose.foundation.layout.WindowInsets_androidKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
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
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.AlphaKt;
import androidx.compose.ui.draw.BlurKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.unit.Density;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.ext.BrushExtKt$radialGradientShader$1;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt;
import com.android.systemui.media.mediaoutput.controller.media.MediaSession;
import com.android.systemui.media.mediaoutput.controller.media.SessionController$special$$inlined$map$1;
import com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import com.samsung.sesl.compose.component.AppBarKt;
import com.samsung.sesl.compose.component.SeslTopAppBarDefaults;
import com.samsung.sesl.compose.template.SeslScaffoldTemplate$BackgroundScope;
import com.samsung.sesl.compose.template.SeslTopAppBarTemplate$NavigationScope;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;

/* loaded from: classes2.dex */
public abstract class CoverScreenKt {
    /* JADX WARN: Removed duplicated region for block: B:64:0x013c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void CoverScreen(final Function0 function0, final MediaSessionViewModel mediaSessionViewModel, final SessionAudioPathViewModel sessionAudioPathViewModel, Composer composer, final int i) {
        Object failure;
        Object failure2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1937252265);
        if ((((composerImpl.changedInstance(function0) ? 4 : 2) | i | 144) & 147) == 146 && composerImpl.getSkipping()) {
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
                    int i4 = Result.$r8$clinit;
                    failure2 = new Result.Failure(th2);
                }
                Throwable thM3441exceptionOrNullimpl2 = Result.m3441exceptionOrNullimpl(failure2);
                if (thM3441exceptionOrNullimpl2 != null) {
                    thM3441exceptionOrNullimpl2.printStackTrace();
                }
                if (failure2 instanceof Result.Failure) {
                    failure2 = null;
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory2 = (ViewModelProvider.Factory) failure2;
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
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.CoverScreen (CoverScreen.kt:54)");
            }
            Feature feature = (Feature) composerImpl.consume(CompositionExtKt.LocalFeature);
            float density = ((Density) composerImpl.consume(CompositionLocalsKt.LocalDensity)).getDensity() / ((Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext)).getApplicationContext().getResources().getDisplayMetrics().density;
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(-2116758784);
            boolean zChangedInstance = composerImpl.changedInstance(sessionAudioPathViewModel) | composerImpl.changedInstance(feature);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new CoverScreenKt$CoverScreen$1$1(sessionAudioPathViewModel, feature, null);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) objRememberedValue);
                ProvidedValue providedValueDefaultProvidedValue$runtime_release = CompositionExtKt.LocalMediaInteraction.defaultProvidedValue$runtime_release(mediaSessionViewModel);
                ProvidedValue providedValueDefaultProvidedValue$runtime_release2 = CompositionExtKt.LocalAudioPathInteraction.defaultProvidedValue$runtime_release(sessionAudioPathViewModel);
                StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionExtKt.LocalBackgroundColor;
                Color.Companion.getClass();
                CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{providedValueDefaultProvidedValue$runtime_release, providedValueDefaultProvidedValue$runtime_release2, staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m456boximpl(Color.Transparent)), CompositionExtKt.LocalDensityScale.defaultProvidedValue$runtime_release(Float.valueOf(density))}, ComposableLambdaKt.rememberComposableLambda(594425111, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.CoverScreenKt.CoverScreen.2
                    /* JADX WARN: Removed duplicated region for block: B:8:0x001d  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2) {
                        ComposerImpl composerImpl2;
                        final MutableState mutableStateCollectAsState;
                        Composer composer2 = (Composer) obj;
                        if ((((Number) obj2).intValue() & 3) == 2) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            if (composerImpl3.getSkipping()) {
                                composerImpl3.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.CoverScreen.<anonymous> (CoverScreen.kt:68)");
                                }
                                MediaSession mediaSession = (MediaSession) SnapshotStateKt.collectAsState(mediaSessionViewModel.currentSessionController, null, null, composer2, 48, 2).getValue();
                                SessionController$special$$inlined$map$1 sessionController$special$$inlined$map$1 = mediaSession != null ? new SessionController$special$$inlined$map$1(mediaSession.getColorScheme()) : null;
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                composerImpl4.startReplaceGroup(1280692210);
                                if (sessionController$special$$inlined$map$1 == null) {
                                    mutableStateCollectAsState = null;
                                    composerImpl2 = composerImpl4;
                                } else {
                                    composerImpl2 = composerImpl4;
                                    mutableStateCollectAsState = SnapshotStateKt.collectAsState(sessionController$special$$inlined$map$1, null, null, composerImpl2, 48, 2);
                                }
                                composerImpl2.end(false);
                                if (mutableStateCollectAsState != null) {
                                    composerImpl2.startReplaceGroup(1280695155);
                                    Object objRememberedValue2 = composerImpl2.rememberedValue();
                                    Composer.Companion.getClass();
                                    Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                                    if (objRememberedValue2 == composer$Companion$Empty$1) {
                                        objRememberedValue2 = SnapshotStateKt.mutableStateOf$default(Float.valueOf(0.0f));
                                        composerImpl2.updateRememberedValue(objRememberedValue2);
                                    }
                                    MutableState mutableState = (MutableState) objRememberedValue2;
                                    composerImpl2.end(false);
                                    ComposerImpl composerImpl5 = composerImpl2;
                                    State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(((Number) mutableState.getValue()).floatValue(), AnimationSpecKt.tween$default(500, 0, null, 6), null, null, composerImpl5, 48, 28);
                                    Unit unit2 = Unit.INSTANCE;
                                    composerImpl5.startReplaceGroup(1280700167);
                                    Object objRememberedValue3 = composerImpl5.rememberedValue();
                                    if (objRememberedValue3 == composer$Companion$Empty$1) {
                                        objRememberedValue3 = new CoverScreenKt$CoverScreen$2$1$1(mutableState, null);
                                        composerImpl5.updateRememberedValue(objRememberedValue3);
                                    }
                                    composerImpl5.end(false);
                                    EffectsKt.LaunchedEffect(composerImpl5, unit2, (Function2) objRememberedValue3);
                                    MediaCardKt.ThumbnailSection(AlphaKt.alpha(BlurKt.m359blurF8QBwvs$default(SizeKt.fillMaxSize(Modifier.Companion, 1.0f), 14), ((Number) stateAnimateFloatAsState.getValue()).floatValue()), composerImpl5, 0);
                                    final Function0 function02 = function0;
                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(341519360, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.CoverScreenKt.CoverScreen.2.2
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj3, Object obj4) {
                                            Composer composer3 = (Composer) obj3;
                                            if ((((Number) obj4).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                                if (composerImpl6.getSkipping()) {
                                                    composerImpl6.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.CoverScreen.<anonymous>.<anonymous> (CoverScreen.kt:84)");
                                                    }
                                                    ComposableSingletons$CoverScreenKt.INSTANCE.getClass();
                                                    ComposableLambdaImpl composableLambdaImpl = ComposableSingletons$CoverScreenKt.f54lambda2;
                                                    final Function0 function03 = function02;
                                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(-321362563, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.CoverScreenKt.CoverScreen.2.2.1
                                                        /* JADX WARN: Removed duplicated region for block: B:18:0x003c  */
                                                        /* JADX WARN: Removed duplicated region for block: B:25:0x0065  */
                                                        @Override // kotlin.jvm.functions.Function3
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                                            SeslTopAppBarTemplate$NavigationScope seslTopAppBarTemplate$NavigationScope = (SeslTopAppBarTemplate$NavigationScope) obj5;
                                                            Composer composer4 = (Composer) obj6;
                                                            int iIntValue = ((Number) obj7).intValue();
                                                            if ((iIntValue & 6) == 0) {
                                                                iIntValue |= (iIntValue & 8) == 0 ? ((ComposerImpl) composer4).changed(seslTopAppBarTemplate$NavigationScope) : ((ComposerImpl) composer4).changedInstance(seslTopAppBarTemplate$NavigationScope) ? 4 : 2;
                                                            }
                                                            if ((iIntValue & 19) == 18) {
                                                                ComposerImpl composerImpl7 = (ComposerImpl) composer4;
                                                                if (composerImpl7.getSkipping()) {
                                                                    composerImpl7.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.CoverScreen.<anonymous>.<anonymous>.<anonymous> (CoverScreen.kt:98)");
                                                                    }
                                                                    ComposerImpl composerImpl8 = (ComposerImpl) composer4;
                                                                    composerImpl8.startReplaceGroup(-755416889);
                                                                    final Function0 function04 = function03;
                                                                    boolean zChanged = composerImpl8.changed(function04);
                                                                    Object objRememberedValue4 = composerImpl8.rememberedValue();
                                                                    if (!zChanged) {
                                                                        Composer.Companion.getClass();
                                                                        if (objRememberedValue4 == Composer.Companion.Empty) {
                                                                            objRememberedValue4 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.CoverScreenKt$CoverScreen$2$2$1$$ExternalSyntheticLambda0
                                                                                @Override // kotlin.jvm.functions.Function0
                                                                                public final Object invoke() {
                                                                                    function04.invoke();
                                                                                    return Unit.INSTANCE;
                                                                                }
                                                                            };
                                                                            composerImpl8.updateRememberedValue(objRememberedValue4);
                                                                        }
                                                                        composerImpl8.end(false);
                                                                        ComposableSingletons$CoverScreenKt.INSTANCE.getClass();
                                                                        ComposableLambdaImpl composableLambdaImpl2 = ComposableSingletons$CoverScreenKt.f55lambda3;
                                                                        SeslTopAppBarTemplate$NavigationScope.Companion companion = SeslTopAppBarTemplate$NavigationScope.Companion;
                                                                        seslTopAppBarTemplate$NavigationScope.NavigationUp((Function0) objRememberedValue4, null, null, composableLambdaImpl2, composerImpl8, 3072 | ((iIntValue << 12) & 57344));
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composer3);
                                                    WindowInsets.Companion companion = WindowInsets.Companion;
                                                    WindowInsets safeDrawing = WindowInsets_androidKt.getSafeDrawing(composer3);
                                                    WindowInsetsSides.Companion.getClass();
                                                    WindowInsets windowInsetsM149onlybOOhFvg = WindowInsetsKt.m149onlybOOhFvg(safeDrawing, WindowInsetsSides.Horizontal | WindowInsetsSides.Top);
                                                    SeslTopAppBarDefaults seslTopAppBarDefaults = SeslTopAppBarDefaults.INSTANCE;
                                                    Color.Companion.getClass();
                                                    long j = Color.Transparent;
                                                    seslTopAppBarDefaults.getClass();
                                                    AppBarKt.m3336SeslTopAppBarau3_HiA(composableLambdaImpl, null, composableLambdaImplRememberComposableLambda2, null, windowInsetsM149onlybOOhFvg, SeslTopAppBarDefaults.m3344topAppBarColors5tl4gsc(j, composer3), 0.0f, composer3, 390, 74);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl5);
                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(1245069068, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.CoverScreenKt.CoverScreen.2.3
                                        /* JADX WARN: Removed duplicated region for block: B:26:0x0055  */
                                        @Override // kotlin.jvm.functions.Function4
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj3, Object obj4, Object obj5, Object obj6) {
                                            int i5;
                                            SeslScaffoldTemplate$BackgroundScope seslScaffoldTemplate$BackgroundScope = (SeslScaffoldTemplate$BackgroundScope) obj3;
                                            PaddingValues paddingValues = (PaddingValues) obj4;
                                            Composer composer3 = (Composer) obj5;
                                            int iIntValue = ((Number) obj6).intValue();
                                            if ((iIntValue & 6) == 0) {
                                                i5 = ((iIntValue & 8) == 0 ? ((ComposerImpl) composer3).changed(seslScaffoldTemplate$BackgroundScope) : ((ComposerImpl) composer3).changedInstance(seslScaffoldTemplate$BackgroundScope) ? 4 : 2) | iIntValue;
                                            } else {
                                                i5 = iIntValue;
                                            }
                                            if ((iIntValue & 48) == 0) {
                                                i5 |= ((ComposerImpl) composer3).changed(paddingValues) ? 32 : 16;
                                            }
                                            if ((i5 & 147) == 146) {
                                                ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                                if (composerImpl6.getSkipping()) {
                                                    composerImpl6.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.CoverScreen.<anonymous>.<anonymous> (CoverScreen.kt:114)");
                                                    }
                                                    Modifier.Companion companion = Modifier.Companion;
                                                    List list = (List) mutableStateCollectAsState.getValue();
                                                    List listCardMediaControlDefaultBackground = null;
                                                    if (list != null) {
                                                        List list2 = list;
                                                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                                                        Iterator it = list2.iterator();
                                                        while (it.hasNext()) {
                                                            arrayList.add(Color.m456boximpl(ColorKt.Color(((Number) it.next()).intValue())));
                                                        }
                                                        if (!arrayList.isEmpty()) {
                                                            listCardMediaControlDefaultBackground = arrayList;
                                                        }
                                                    }
                                                    ComposerImpl composerImpl7 = (ComposerImpl) composer3;
                                                    composerImpl7.startReplaceGroup(-594491141);
                                                    if (listCardMediaControlDefaultBackground == null) {
                                                        listCardMediaControlDefaultBackground = com.android.systemui.media.mediaoutput.compose.theme.ColorKt.cardMediaControlDefaultBackground(composerImpl7);
                                                    }
                                                    composerImpl7.end(false);
                                                    Modifier modifierThen = companion.then(new BackgroundElement(0L, new BrushExtKt$radialGradientShader$1(listCardMediaControlDefaultBackground), 1.0f, RectangleShapeKt.RectangleShape, InspectableValueKt.NoInspectorInfo, 1, null));
                                                    Color.Companion.getClass();
                                                    long j = Color.Transparent;
                                                    SeslScaffoldTemplate$BackgroundScope.Companion companion2 = SeslScaffoldTemplate$BackgroundScope.Companion;
                                                    seslScaffoldTemplate$BackgroundScope.m3355BackgroundFNF3uiM(paddingValues, modifierThen, j, composerImpl7, ((i5 >> 3) & 14) | 384 | ((i5 << 9) & 7168), 0);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl5);
                                    ComposableSingletons$CoverScreenKt.INSTANCE.getClass();
                                    SnackbarScaffoldKt.SnackbarScaffold(null, composableLambdaImplRememberComposableLambda, composableLambdaImplRememberComposableLambda2, ComposableSingletons$CoverScreenKt.f56lambda4, composerImpl5, 3504, 1);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                } else if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 56);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(mediaSessionViewModel, sessionAudioPathViewModel, i) { // from class: com.android.systemui.media.mediaoutput.compose.CoverScreenKt$$ExternalSyntheticLambda0
                public final /* synthetic */ MediaSessionViewModel f$1;
                public final /* synthetic */ SessionAudioPathViewModel f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    MediaSessionViewModel mediaSessionViewModel3 = this.f$1;
                    SessionAudioPathViewModel sessionAudioPathViewModel2 = this.f$2;
                    CoverScreenKt.CoverScreen(this.f$0, mediaSessionViewModel3, sessionAudioPathViewModel2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
