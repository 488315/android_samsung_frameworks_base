package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.animation.AnimatedContentScope;
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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final Object mo779invoke(Object obj) {
        Object obj2 = this.f$2;
        final State state = this.f$1;
        final NavHostController navHostController = this.f$0;
        NavOptions navOptions = null;
        switch (this.$r8$classId) {
            case 0:
                NavGraphBuilder navGraphBuilder = (NavGraphBuilder) obj;
                Screen.Phone phone = Screen.Phone.INSTANCE;
                NavGraphBuilderKt.composable$default(navGraphBuilder, phone.route, phone.navArgument, new ComposableLambdaImpl(-1164333731, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$1
                    /* JADX WARN: Code restructure failed: missing block: B:7:0x0031, code lost:
                    
                        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
                     */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r1, java.lang.Object r2, java.lang.Object r3, java.lang.Object r4) {
                        /*
                            r0 = this;
                            androidx.compose.animation.AnimatedContentScope r1 = (androidx.compose.animation.AnimatedContentScope) r1
                            androidx.navigation.NavBackStackEntry r2 = (androidx.navigation.NavBackStackEntry) r2
                            androidx.compose.runtime.Composer r3 = (androidx.compose.runtime.Composer) r3
                            java.lang.Number r4 = (java.lang.Number) r4
                            r4.intValue()
                            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r1 == 0) goto L16
                            java.lang.String r1 = "com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputHost.kt:93)"
                            androidx.compose.runtime.ComposerKt.traceEventStart(r1)
                        L16:
                            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
                            r1 = -2063529680(0xffffffff85010930, float:-6.067238E-36)
                            r3.startReplaceGroup(r1)
                            androidx.navigation.NavHostController r0 = androidx.navigation.NavHostController.this
                            boolean r1 = r3.changedInstance(r0)
                            java.lang.Object r2 = r3.rememberedValue()
                            if (r1 != 0) goto L33
                            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
                            r1.getClass()
                            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
                            if (r2 != r1) goto L3c
                        L33:
                            com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$1$$ExternalSyntheticLambda0 r2 = new com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$1$$ExternalSyntheticLambda0
                            r1 = 0
                            r2.<init>(r0, r1)
                            r3.updateRememberedValue(r2)
                        L3c:
                            kotlin.jvm.functions.Function1 r2 = (kotlin.jvm.functions.Function1) r2
                            r0 = 0
                            r3.end(r0)
                            r1 = 0
                            com.android.systemui.media.mediaoutput.compose.PhoneScreenKt.PhoneScreen(r2, r1, r1, r3, r0)
                            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r0 == 0) goto L4f
                            androidx.compose.runtime.ComposerKt.traceEventEnd()
                        L4f:
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                }), IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
                Screen.TV tv = Screen.TV.INSTANCE;
                NavGraphBuilderKt.composable$default(navGraphBuilder, tv.route, tv.navArgument, new ComposableLambdaImpl(-546615596, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$2
                    /* JADX WARN: Code restructure failed: missing block: B:7:0x0031, code lost:
                    
                        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
                     */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r1, java.lang.Object r2, java.lang.Object r3, java.lang.Object r4) {
                        /*
                            r0 = this;
                            androidx.compose.animation.AnimatedContentScope r1 = (androidx.compose.animation.AnimatedContentScope) r1
                            androidx.navigation.NavBackStackEntry r2 = (androidx.navigation.NavBackStackEntry) r2
                            androidx.compose.runtime.Composer r3 = (androidx.compose.runtime.Composer) r3
                            java.lang.Number r4 = (java.lang.Number) r4
                            r4.intValue()
                            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r1 == 0) goto L16
                            java.lang.String r1 = "com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputHost.kt:99)"
                            androidx.compose.runtime.ComposerKt.traceEventStart(r1)
                        L16:
                            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
                            r1 = -2063522000(0xffffffff85012730, float:-6.0727485E-36)
                            r3.startReplaceGroup(r1)
                            androidx.navigation.NavHostController r0 = androidx.navigation.NavHostController.this
                            boolean r1 = r3.changedInstance(r0)
                            java.lang.Object r2 = r3.rememberedValue()
                            if (r1 != 0) goto L33
                            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
                            r1.getClass()
                            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
                            if (r2 != r1) goto L3c
                        L33:
                            com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$1$$ExternalSyntheticLambda0 r2 = new com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$1$$ExternalSyntheticLambda0
                            r1 = 1
                            r2.<init>(r0, r1)
                            r3.updateRememberedValue(r2)
                        L3c:
                            kotlin.jvm.functions.Function1 r2 = (kotlin.jvm.functions.Function1) r2
                            r0 = 0
                            r3.end(r0)
                            r1 = 0
                            com.android.systemui.media.mediaoutput.compose.TvScreenKt.TvScreen(r2, r1, r1, r3, r0)
                            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r0 == 0) goto L4f
                            androidx.compose.runtime.ComposerKt.traceEventEnd()
                        L4f:
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$2.invoke(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                }), IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
                NavGraphBuilderKt.composable$default(navGraphBuilder, Screen.Selector.INSTANCE.route, null, new ComposableLambdaImpl(-941019435, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$3
                    /* JADX WARN: Code restructure failed: missing block: B:7:0x003e, code lost:
                    
                        if (r10 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
                     */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r7, java.lang.Object r8, java.lang.Object r9, java.lang.Object r10) {
                        /*
                            r6 = this;
                            androidx.compose.animation.AnimatedContentScope r7 = (androidx.compose.animation.AnimatedContentScope) r7
                            androidx.navigation.NavBackStackEntry r8 = (androidx.navigation.NavBackStackEntry) r8
                            androidx.compose.runtime.Composer r9 = (androidx.compose.runtime.Composer) r9
                            java.lang.Number r10 = (java.lang.Number) r10
                            r10.intValue()
                            boolean r8 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r8 == 0) goto L16
                            java.lang.String r8 = "com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputHost.kt:105)"
                            androidx.compose.runtime.ComposerKt.traceEventStart(r8)
                        L16:
                            r4 = r9
                            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
                            r8 = -2063514352(0xffffffff85014510, float:-6.0782356E-36)
                            r4.startReplaceGroup(r8)
                            androidx.navigation.NavHostController r8 = androidx.navigation.NavHostController.this
                            boolean r9 = r4.changedInstance(r8)
                            boolean r10 = r4.changedInstance(r7)
                            r9 = r9 | r10
                            androidx.compose.runtime.State r6 = r2
                            boolean r10 = r4.changed(r6)
                            r9 = r9 | r10
                            java.lang.Object r10 = r4.rememberedValue()
                            if (r9 != 0) goto L40
                            androidx.compose.runtime.Composer$Companion r9 = androidx.compose.runtime.Composer.Companion
                            r9.getClass()
                            androidx.compose.runtime.Composer$Companion$Empty$1 r9 = androidx.compose.runtime.Composer.Companion.Empty
                            if (r10 != r9) goto L48
                        L40:
                            com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda3 r10 = new com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda3
                            r10.<init>(r8, r7, r6)
                            r4.updateRememberedValue(r10)
                        L48:
                            r0 = r10
                            kotlin.jvm.functions.Function1 r0 = (kotlin.jvm.functions.Function1) r0
                            r6 = 0
                            r4.end(r6)
                            r1 = 0
                            r5 = 0
                            r2 = 0
                            r3 = 0
                            com.android.systemui.media.mediaoutput.compose.SelectorScreenKt.SelectorScreen(r0, r1, r2, r3, r4, r5)
                            boolean r6 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r6 == 0) goto L5f
                            androidx.compose.runtime.ComposerKt.traceEventEnd()
                        L5f:
                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                            return r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$3.invoke(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                }), 254);
                final DismissCallback dismissCallback = (DismissCallback) obj2;
                NavGraphBuilderKt.composable$default(navGraphBuilder, Screen.SettingHome.INSTANCE.route, null, new ComposableLambdaImpl(-1335423274, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$4
                    /* JADX WARN: Code restructure failed: missing block: B:11:0x0060, code lost:
                    
                        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L14;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:7:0x0039, code lost:
                    
                        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
                     */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r7, java.lang.Object r8, java.lang.Object r9, java.lang.Object r10) {
                        /*
                            r6 = this;
                            androidx.compose.animation.AnimatedContentScope r7 = (androidx.compose.animation.AnimatedContentScope) r7
                            androidx.navigation.NavBackStackEntry r8 = (androidx.navigation.NavBackStackEntry) r8
                            androidx.compose.runtime.Composer r9 = (androidx.compose.runtime.Composer) r9
                            java.lang.Number r10 = (java.lang.Number) r10
                            r10.intValue()
                            boolean r7 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r7 == 0) goto L16
                            java.lang.String r7 = "com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputHost.kt:124)"
                            androidx.compose.runtime.ComposerKt.traceEventStart(r7)
                        L16:
                            r4 = r9
                            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
                            r7 = -2063484934(0xffffffff8501b7fa, float:-6.099342E-36)
                            r4.startReplaceGroup(r7)
                            androidx.navigation.NavHostController r7 = androidx.navigation.NavHostController.this
                            boolean r8 = r4.changedInstance(r7)
                            com.android.systemui.media.mediaoutput.compose.common.DismissCallback r6 = r2
                            boolean r9 = r4.changedInstance(r6)
                            r8 = r8 | r9
                            java.lang.Object r9 = r4.rememberedValue()
                            androidx.compose.runtime.Composer$Companion r10 = androidx.compose.runtime.Composer.Companion
                            if (r8 != 0) goto L3b
                            r10.getClass()
                            androidx.compose.runtime.Composer$Companion$Empty$1 r8 = androidx.compose.runtime.Composer.Companion.Empty
                            if (r9 != r8) goto L44
                        L3b:
                            com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda0 r9 = new com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda0
                            r8 = 1
                            r9.<init>(r7, r6, r8)
                            r4.updateRememberedValue(r9)
                        L44:
                            r0 = r9
                            kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
                            r6 = 0
                            r4.end(r6)
                            r8 = -2063481794(0xffffffff8501c43e, float:-6.101595E-36)
                            r4.startReplaceGroup(r8)
                            boolean r8 = r4.changedInstance(r7)
                            java.lang.Object r9 = r4.rememberedValue()
                            if (r8 != 0) goto L62
                            r10.getClass()
                            androidx.compose.runtime.Composer$Companion$Empty$1 r8 = androidx.compose.runtime.Composer.Companion.Empty
                            if (r9 != r8) goto L6b
                        L62:
                            com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$1$$ExternalSyntheticLambda0 r9 = new com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$1$$ExternalSyntheticLambda0
                            r8 = 2
                            r9.<init>(r7, r8)
                            r4.updateRememberedValue(r9)
                        L6b:
                            r1 = r9
                            kotlin.jvm.functions.Function1 r1 = (kotlin.jvm.functions.Function1) r1
                            r4.end(r6)
                            r3 = 0
                            r5 = 0
                            r2 = 0
                            com.android.systemui.media.mediaoutput.compose.SettingHomeKt.SettingHome(r0, r1, r2, r3, r4, r5)
                            boolean r6 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r6 == 0) goto L80
                            androidx.compose.runtime.ComposerKt.traceEventEnd()
                        L80:
                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                            return r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$4.invoke(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                }), 254);
                NavGraphBuilderKt.composable$default(navGraphBuilder, Screen.CastSetting.INSTANCE.route, null, new ComposableLambdaImpl(-1729827113, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$5
                    /* JADX WARN: Code restructure failed: missing block: B:7:0x0031, code lost:
                    
                        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
                     */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r1, java.lang.Object r2, java.lang.Object r3, java.lang.Object r4) {
                        /*
                            r0 = this;
                            androidx.compose.animation.AnimatedContentScope r1 = (androidx.compose.animation.AnimatedContentScope) r1
                            androidx.navigation.NavBackStackEntry r2 = (androidx.navigation.NavBackStackEntry) r2
                            androidx.compose.runtime.Composer r3 = (androidx.compose.runtime.Composer) r3
                            java.lang.Number r4 = (java.lang.Number) r4
                            r4.intValue()
                            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r1 == 0) goto L16
                            java.lang.String r1 = "com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputHost.kt:131)"
                            androidx.compose.runtime.ComposerKt.traceEventStart(r1)
                        L16:
                            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
                            r1 = -2063475718(0xffffffff8501dbfa, float:-6.105954E-36)
                            r3.startReplaceGroup(r1)
                            androidx.navigation.NavHostController r0 = androidx.navigation.NavHostController.this
                            boolean r1 = r3.changedInstance(r0)
                            java.lang.Object r2 = r3.rememberedValue()
                            if (r1 != 0) goto L33
                            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
                            r1.getClass()
                            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
                            if (r2 != r1) goto L3c
                        L33:
                            com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$5$$ExternalSyntheticLambda0 r2 = new com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$5$$ExternalSyntheticLambda0
                            r1 = 0
                            r2.<init>(r0, r1)
                            r3.updateRememberedValue(r2)
                        L3c:
                            kotlin.jvm.functions.Function0 r2 = (kotlin.jvm.functions.Function0) r2
                            r0 = 0
                            r3.end(r0)
                            r1 = 0
                            com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt.CastSettingScreen(r2, r1, r3, r0)
                            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r0 == 0) goto L4f
                            androidx.compose.runtime.ComposerKt.traceEventEnd()
                        L4f:
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$5.invoke(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                }), 254);
                NavGraphBuilderKt.composable$default(navGraphBuilder, Screen.SpotifyCastSetting.INSTANCE.route, null, new ComposableLambdaImpl(-2124230952, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$6
                    /* JADX WARN: Code restructure failed: missing block: B:7:0x0031, code lost:
                    
                        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
                     */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r1, java.lang.Object r2, java.lang.Object r3, java.lang.Object r4) {
                        /*
                            r0 = this;
                            androidx.compose.animation.AnimatedContentScope r1 = (androidx.compose.animation.AnimatedContentScope) r1
                            androidx.navigation.NavBackStackEntry r2 = (androidx.navigation.NavBackStackEntry) r2
                            androidx.compose.runtime.Composer r3 = (androidx.compose.runtime.Composer) r3
                            java.lang.Number r4 = (java.lang.Number) r4
                            r4.intValue()
                            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r1 == 0) goto L16
                            java.lang.String r1 = "com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputHost.kt:135)"
                            androidx.compose.runtime.ComposerKt.traceEventStart(r1)
                        L16:
                            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
                            r1 = -2063469990(0xffffffff8501f25a, float:-6.110064E-36)
                            r3.startReplaceGroup(r1)
                            androidx.navigation.NavHostController r0 = androidx.navigation.NavHostController.this
                            boolean r1 = r3.changedInstance(r0)
                            java.lang.Object r2 = r3.rememberedValue()
                            if (r1 != 0) goto L33
                            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
                            r1.getClass()
                            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
                            if (r2 != r1) goto L3c
                        L33:
                            com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$5$$ExternalSyntheticLambda0 r2 = new com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$5$$ExternalSyntheticLambda0
                            r1 = 1
                            r2.<init>(r0, r1)
                            r3.updateRememberedValue(r2)
                        L3c:
                            kotlin.jvm.functions.Function0 r2 = (kotlin.jvm.functions.Function0) r2
                            r0 = 0
                            r3.end(r0)
                            r1 = 0
                            com.android.systemui.media.mediaoutput.compose.SpotifyCastSettingScreenKt.SpotifyCastSettingScreen(r2, r1, r3, r0)
                            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r0 == 0) goto L4f
                            androidx.compose.runtime.ComposerKt.traceEventEnd()
                        L4f:
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$6.invoke(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                }), 254);
                NavGraphBuilderKt.composable$default(navGraphBuilder, Screen.LabsHome.INSTANCE.route, null, new ComposableLambdaImpl(1776332505, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$7
                    /* JADX WARN: Code restructure failed: missing block: B:7:0x0031, code lost:
                    
                        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
                     */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r1, java.lang.Object r2, java.lang.Object r3, java.lang.Object r4) {
                        /*
                            r0 = this;
                            androidx.compose.animation.AnimatedContentScope r1 = (androidx.compose.animation.AnimatedContentScope) r1
                            androidx.navigation.NavBackStackEntry r2 = (androidx.navigation.NavBackStackEntry) r2
                            androidx.compose.runtime.Composer r3 = (androidx.compose.runtime.Composer) r3
                            java.lang.Number r4 = (java.lang.Number) r4
                            r4.intValue()
                            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r1 == 0) goto L16
                            java.lang.String r1 = "com.android.systemui.media.mediaoutput.compose.MediaOutputHost.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputHost.kt:139)"
                            androidx.compose.runtime.ComposerKt.traceEventStart(r1)
                        L16:
                            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
                            r1 = -2063465094(0xffffffff8502057a, float:-6.1135765E-36)
                            r3.startReplaceGroup(r1)
                            androidx.navigation.NavHostController r0 = androidx.navigation.NavHostController.this
                            boolean r1 = r3.changedInstance(r0)
                            java.lang.Object r2 = r3.rememberedValue()
                            if (r1 != 0) goto L33
                            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
                            r1.getClass()
                            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
                            if (r2 != r1) goto L3c
                        L33:
                            com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$5$$ExternalSyntheticLambda0 r2 = new com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$5$$ExternalSyntheticLambda0
                            r1 = 2
                            r2.<init>(r0, r1)
                            r3.updateRememberedValue(r2)
                        L3c:
                            kotlin.jvm.functions.Function0 r2 = (kotlin.jvm.functions.Function0) r2
                            r0 = 0
                            r3.end(r0)
                            r1 = 0
                            com.android.systemui.media.mediaoutput.compose.LabsHomeKt.LabsHome(r2, r1, r3, r0)
                            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r0 == 0) goto L4f
                            androidx.compose.runtime.ComposerKt.traceEventEnd()
                        L4f:
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2$5$1$7.invoke(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
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
                        navOptions = builder.build();
                    }
                    NavController.navigate$default(navHostController, str, navOptions, 4);
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
