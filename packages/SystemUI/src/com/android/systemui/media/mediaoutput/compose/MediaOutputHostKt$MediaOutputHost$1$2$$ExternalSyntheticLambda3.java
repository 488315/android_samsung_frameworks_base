package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.animation.AnimatedContentScope;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.navigation.NavController;
import androidx.navigation.NavGraphBuilder;
import androidx.navigation.NavHostController;
import androidx.navigation.NavOptions;
import androidx.navigation.NavOptionsBuilder;
import androidx.navigation.PopUpToBuilder;
import androidx.navigation.compose.NavGraphBuilderKt;
import com.android.systemui.media.mediaoutput.compose.Screen;
import com.android.systemui.media.mediaoutput.compose.common.DismissCallback;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ NavHostController f$0;
    public final /* synthetic */ State f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda3(NavHostController navHostController, AnimatedContentScope animatedContentScope, State state) {
        this.f$0 = navHostController;
        this.f$2 = animatedContentScope;
        this.f$1 = state;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Object obj2 = this.f$2;
        final State state = this.f$1;
        final NavHostController navHostController = this.f$0;
        NavOptions navOptionsBuild = null;
        switch (this.$r8$classId) {
            case 0:
                NavGraphBuilder navGraphBuilder = (NavGraphBuilder) obj;
                Screen.Phone phone = Screen.Phone.INSTANCE;
                NavGraphBuilderKt.composable$default(navGraphBuilder, phone.route, phone.navArgument, new ComposableLambdaImpl(-1164333731, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$1
                    /* JADX WARN: Removed duplicated region for block: B:9:0x0033  */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj3, Object obj4, Object obj5, Object obj6) {
                        Composer composer = (Composer) obj5;
                        ((Number) obj6).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputHost.kt:93)");
                        }
                        ComposerImpl composerImpl = (ComposerImpl) composer;
                        composerImpl.startReplaceGroup(-2063529680);
                        NavHostController navHostController2 = navHostController;
                        boolean zChangedInstance = composerImpl.changedInstance(navHostController2);
                        Object objRememberedValue = composerImpl.rememberedValue();
                        if (!zChangedInstance) {
                            Composer.Companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new MediaOutputHostKt$MediaOutputHost$1$2$5$1$1$$ExternalSyntheticLambda0(navHostController2, 0);
                                composerImpl.updateRememberedValue(objRememberedValue);
                            }
                        }
                        composerImpl.end(false);
                        PhoneScreenKt.PhoneScreen((Function1) objRememberedValue, null, null, composerImpl, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }), IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
                Screen.TV tv = Screen.TV.INSTANCE;
                NavGraphBuilderKt.composable$default(navGraphBuilder, tv.route, tv.navArgument, new ComposableLambdaImpl(-546615596, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$2
                    /* JADX WARN: Removed duplicated region for block: B:9:0x0033  */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj3, Object obj4, Object obj5, Object obj6) {
                        Composer composer = (Composer) obj5;
                        ((Number) obj6).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputHost.kt:99)");
                        }
                        ComposerImpl composerImpl = (ComposerImpl) composer;
                        composerImpl.startReplaceGroup(-2063522000);
                        NavHostController navHostController2 = navHostController;
                        boolean zChangedInstance = composerImpl.changedInstance(navHostController2);
                        Object objRememberedValue = composerImpl.rememberedValue();
                        if (!zChangedInstance) {
                            Composer.Companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new MediaOutputHostKt$MediaOutputHost$1$2$5$1$1$$ExternalSyntheticLambda0(navHostController2, 1);
                                composerImpl.updateRememberedValue(objRememberedValue);
                            }
                        }
                        composerImpl.end(false);
                        TvScreenKt.TvScreen((Function1) objRememberedValue, null, null, composerImpl, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }), IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
                NavGraphBuilderKt.composable$default(navGraphBuilder, Screen.Selector.INSTANCE.route, null, new ComposableLambdaImpl(-941019435, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$3
                    /* JADX WARN: Removed duplicated region for block: B:9:0x0040  */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj3, Object obj4, Object obj5, Object obj6) {
                        AnimatedContentScope animatedContentScope = (AnimatedContentScope) obj3;
                        Composer composer = (Composer) obj5;
                        ((Number) obj6).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputHost.kt:105)");
                        }
                        ComposerImpl composerImpl = (ComposerImpl) composer;
                        composerImpl.startReplaceGroup(-2063514352);
                        NavHostController navHostController2 = navHostController;
                        boolean zChangedInstance = composerImpl.changedInstance(navHostController2) | composerImpl.changedInstance(animatedContentScope);
                        State state2 = state;
                        boolean zChanged = zChangedInstance | composerImpl.changed(state2);
                        Object objRememberedValue = composerImpl.rememberedValue();
                        if (!zChanged) {
                            Composer.Companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda3(navHostController2, animatedContentScope, state2);
                                composerImpl.updateRememberedValue(objRememberedValue);
                            }
                        }
                        composerImpl.end(false);
                        SelectorScreenKt.SelectorScreen((Function1) objRememberedValue, null, null, null, composerImpl, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }), 254);
                final DismissCallback dismissCallback = (DismissCallback) obj2;
                NavGraphBuilderKt.composable$default(navGraphBuilder, Screen.SettingHome.INSTANCE.route, null, new ComposableLambdaImpl(-1335423274, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$4
                    /* JADX WARN: Removed duplicated region for block: B:14:0x0062  */
                    /* JADX WARN: Removed duplicated region for block: B:9:0x003b  */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj3, Object obj4, Object obj5, Object obj6) {
                        Composer composer = (Composer) obj5;
                        ((Number) obj6).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputHost.kt:124)");
                        }
                        ComposerImpl composerImpl = (ComposerImpl) composer;
                        composerImpl.startReplaceGroup(-2063484934);
                        NavHostController navHostController2 = navHostController;
                        boolean zChangedInstance = composerImpl.changedInstance(navHostController2);
                        DismissCallback dismissCallback2 = dismissCallback;
                        boolean zChangedInstance2 = zChangedInstance | composerImpl.changedInstance(dismissCallback2);
                        Object objRememberedValue = composerImpl.rememberedValue();
                        Composer.Companion companion = Composer.Companion;
                        if (!zChangedInstance2) {
                            companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda0(navHostController2, dismissCallback2, 1);
                                composerImpl.updateRememberedValue(objRememberedValue);
                            }
                        }
                        Function0 function0 = (Function0) objRememberedValue;
                        composerImpl.end(false);
                        composerImpl.startReplaceGroup(-2063481794);
                        boolean zChangedInstance3 = composerImpl.changedInstance(navHostController2);
                        Object objRememberedValue2 = composerImpl.rememberedValue();
                        if (!zChangedInstance3) {
                            companion.getClass();
                            if (objRememberedValue2 == Composer.Companion.Empty) {
                                objRememberedValue2 = new MediaOutputHostKt$MediaOutputHost$1$2$5$1$1$$ExternalSyntheticLambda0(navHostController2, 2);
                                composerImpl.updateRememberedValue(objRememberedValue2);
                            }
                        }
                        composerImpl.end(false);
                        SettingHomeKt.SettingHome(function0, (Function1) objRememberedValue2, null, null, composerImpl, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }), 254);
                NavGraphBuilderKt.composable$default(navGraphBuilder, Screen.CastSetting.INSTANCE.route, null, new ComposableLambdaImpl(-1729827113, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$5
                    /* JADX WARN: Removed duplicated region for block: B:9:0x0033  */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj3, Object obj4, Object obj5, Object obj6) {
                        Composer composer = (Composer) obj5;
                        ((Number) obj6).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputHost.kt:131)");
                        }
                        ComposerImpl composerImpl = (ComposerImpl) composer;
                        composerImpl.startReplaceGroup(-2063475718);
                        NavHostController navHostController2 = navHostController;
                        boolean zChangedInstance = composerImpl.changedInstance(navHostController2);
                        Object objRememberedValue = composerImpl.rememberedValue();
                        if (!zChangedInstance) {
                            Composer.Companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new MediaOutputHostKt$MediaOutputHost$1$2$5$1$5$$ExternalSyntheticLambda0(navHostController2, 0);
                                composerImpl.updateRememberedValue(objRememberedValue);
                            }
                        }
                        composerImpl.end(false);
                        CastSettingScreenKt.CastSettingScreen((Function0) objRememberedValue, null, composerImpl, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }), 254);
                NavGraphBuilderKt.composable$default(navGraphBuilder, Screen.SpotifyCastSetting.INSTANCE.route, null, new ComposableLambdaImpl(-2124230952, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$6
                    /* JADX WARN: Removed duplicated region for block: B:9:0x0033  */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj3, Object obj4, Object obj5, Object obj6) {
                        Composer composer = (Composer) obj5;
                        ((Number) obj6).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputHost.kt:135)");
                        }
                        ComposerImpl composerImpl = (ComposerImpl) composer;
                        composerImpl.startReplaceGroup(-2063469990);
                        NavHostController navHostController2 = navHostController;
                        boolean zChangedInstance = composerImpl.changedInstance(navHostController2);
                        Object objRememberedValue = composerImpl.rememberedValue();
                        if (!zChangedInstance) {
                            Composer.Companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new MediaOutputHostKt$MediaOutputHost$1$2$5$1$5$$ExternalSyntheticLambda0(navHostController2, 1);
                                composerImpl.updateRememberedValue(objRememberedValue);
                            }
                        }
                        composerImpl.end(false);
                        SpotifyCastSettingScreenKt.SpotifyCastSettingScreen((Function0) objRememberedValue, null, composerImpl, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }), 254);
                NavGraphBuilderKt.composable$default(navGraphBuilder, Screen.LabsHome.INSTANCE.route, null, new ComposableLambdaImpl(1776332505, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$7
                    /* JADX WARN: Removed duplicated region for block: B:9:0x0033  */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj3, Object obj4, Object obj5, Object obj6) {
                        Composer composer = (Composer) obj5;
                        ((Number) obj6).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputHost.kt:139)");
                        }
                        ComposerImpl composerImpl = (ComposerImpl) composer;
                        composerImpl.startReplaceGroup(-2063465094);
                        NavHostController navHostController2 = navHostController;
                        boolean zChangedInstance = composerImpl.changedInstance(navHostController2);
                        Object objRememberedValue = composerImpl.rememberedValue();
                        if (!zChangedInstance) {
                            Composer.Companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new MediaOutputHostKt$MediaOutputHost$1$2$5$1$5$$ExternalSyntheticLambda0(navHostController2, 2);
                                composerImpl.updateRememberedValue(objRememberedValue);
                            }
                        }
                        composerImpl.end(false);
                        LabsHomeKt.LabsHome((Function0) objRememberedValue, null, composerImpl, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }), 254);
                break;
            default:
                String str = (String) obj;
                if ((!StringsKt__StringsKt.isBlank(str) ? str : null) != null) {
                    if ((((Boolean) state.getValue()).booleanValue() ? (AnimatedContentScope) obj2 : null) != null) {
                        NavOptionsBuilder navOptionsBuilder = new NavOptionsBuilder();
                        navOptionsBuilder.popUpToId = 0;
                        navOptionsBuilder.inclusive = false;
                        PopUpToBuilder popUpToBuilder = new PopUpToBuilder();
                        popUpToBuilder.inclusive = true;
                        Unit unit = Unit.INSTANCE;
                        navOptionsBuilder.inclusive = true;
                        navOptionsBuilder.saveState = popUpToBuilder.saveState;
                        navOptionsBuilder.launchSingleTop = true;
                        Unit unit2 = Unit.INSTANCE;
                        boolean z = navOptionsBuilder.launchSingleTop;
                        NavOptions.Builder builder = navOptionsBuilder.builder;
                        builder.singleTop = z;
                        builder.restoreState = navOptionsBuilder.restoreState;
                        int i = navOptionsBuilder.popUpToId;
                        boolean z2 = navOptionsBuilder.inclusive;
                        boolean z3 = navOptionsBuilder.saveState;
                        builder.popUpToId = i;
                        builder.popUpToInclusive = z2;
                        builder.popUpToSaveState = z3;
                        navOptionsBuild = builder.build();
                    }
                    NavController.navigate$default(navHostController, str, navOptionsBuild, 4);
                } else {
                    navHostController.popBackStack();
                }
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda3(NavHostController navHostController, State state, DismissCallback dismissCallback) {
        this.f$0 = navHostController;
        this.f$1 = state;
        this.f$2 = dismissCallback;
    }
}
