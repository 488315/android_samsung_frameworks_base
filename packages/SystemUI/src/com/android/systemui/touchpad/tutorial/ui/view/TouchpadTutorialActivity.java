package com.android.systemui.touchpad.tutorial.ui.view;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.activity.EdgeToEdgeApi29;
import androidx.activity.SystemBarStyle;
import androidx.activity.compose.ComponentActivityKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.core.view.WindowInsetsControllerCompat;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.touchpad.tutorial.ui.TouchpadTutorialViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public final class TouchpadTutorialActivity extends ComponentActivity {
    public final TouchpadTutorialViewModel.Factory viewModelFactory;

    public TouchpadTutorialActivity(TouchpadTutorialViewModel.Factory factory) {
        this.viewModelFactory = factory;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        int i = EdgeToEdge.DefaultLightScrim;
        SystemBarStyle.Companion companion = SystemBarStyle.Companion;
        SystemBarStyle auto$default = SystemBarStyle.Companion.auto$default(companion, 0, 0);
        SystemBarStyle auto$default2 = SystemBarStyle.Companion.auto$default(companion, EdgeToEdge.DefaultLightScrim, EdgeToEdge.DefaultDarkScrim);
        View decorView = getWindow().getDecorView();
        boolean booleanValue = ((Boolean) auto$default.detectDarkMode.invoke(decorView.getResources())).booleanValue();
        boolean booleanValue2 = ((Boolean) auto$default2.detectDarkMode.invoke(decorView.getResources())).booleanValue();
        new EdgeToEdgeApi29();
        Window window = getWindow();
        window.setDecorFitsSystemWindows(false);
        window.setStatusBarColor(auto$default.nightMode == 0 ? 0 : booleanValue ? auto$default.darkScrim : auto$default.lightScrim);
        int i2 = auto$default2.nightMode;
        window.setNavigationBarColor(i2 == 0 ? 0 : booleanValue2 ? auto$default2.darkScrim : auto$default2.lightScrim);
        window.setStatusBarContrastEnforced(false);
        window.setNavigationBarContrastEnforced(i2 == 0);
        WindowInsetsControllerCompat windowInsetsControllerCompat = new WindowInsetsControllerCompat(window, decorView);
        windowInsetsControllerCompat.setAppearanceLightStatusBars(!booleanValue);
        windowInsetsControllerCompat.setAppearanceLightNavigationBars(!booleanValue2);
        ComponentActivityKt.setContent$default(this, new ComposableLambdaImpl(-404230632, true, new Function2() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity$onCreate$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 11) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey = ComposerKt.invocation;
                final TouchpadTutorialActivity touchpadTutorialActivity = TouchpadTutorialActivity.this;
                PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(-2016162462, composer, new Function2() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity$onCreate$1.1
                    {
                        super(2);
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:12:0x003e, code lost:
                    
                        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L12;
                     */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r3, java.lang.Object r4) {
                        /*
                            r2 = this;
                            androidx.compose.runtime.Composer r3 = (androidx.compose.runtime.Composer) r3
                            java.lang.Number r4 = (java.lang.Number) r4
                            int r4 = r4.intValue()
                            r4 = r4 & 11
                            r0 = 2
                            if (r4 != r0) goto L1b
                            r4 = r3
                            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
                            boolean r0 = r4.getSkipping()
                            if (r0 != 0) goto L17
                            goto L1b
                        L17:
                            r4.skipToGroupEnd()
                            goto L51
                        L1b:
                            androidx.compose.runtime.OpaqueKey r4 = androidx.compose.runtime.ComposerKt.invocation
                            com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity r4 = com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity.this
                            com.android.systemui.touchpad.tutorial.ui.TouchpadTutorialViewModel$Factory r4 = r4.viewModelFactory
                            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
                            r0 = 683850992(0x28c2bcf0, float:2.1620265E-14)
                            r3.startReplaceGroup(r0)
                            com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity r0 = com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity.this
                            boolean r0 = r3.changed(r0)
                            com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity r2 = com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity.this
                            java.lang.Object r1 = r3.rememberedValue()
                            if (r0 != 0) goto L40
                            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
                            r0.getClass()
                            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
                            if (r1 != r0) goto L48
                        L40:
                            com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity$onCreate$1$1$1$1 r1 = new com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity$onCreate$1$1$1$1
                            r1.<init>()
                            r3.updateRememberedValue(r1)
                        L48:
                            kotlin.jvm.functions.Function0 r1 = (kotlin.jvm.functions.Function0) r1
                            r2 = 0
                            r3.end(r2)
                            com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt.TouchpadTutorialScreen(r4, r1, r3, r2)
                        L51:
                            kotlin.Unit r2 = kotlin.Unit.INSTANCE
                            return r2
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity$onCreate$1.AnonymousClass1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                }), composer, 48, 1);
                return Unit.INSTANCE;
            }
        }));
    }
}
