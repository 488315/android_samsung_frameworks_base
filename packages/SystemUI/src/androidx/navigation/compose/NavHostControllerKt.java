package androidx.navigation.compose;

import android.content.Context;
import androidx.navigation.NavHostController;
import androidx.navigation.NavigatorProvider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class NavHostControllerKt {
    public static final NavHostController access$createNavController(Context context) {
        NavHostController navHostController = new NavHostController(context);
        NavigatorProvider navigatorProvider = navHostController._navigatorProvider;
        navigatorProvider.addNavigator(new ComposeNavGraphNavigator(navigatorProvider));
        navHostController._navigatorProvider.addNavigator(new ComposeNavigator());
        navHostController._navigatorProvider.addNavigator(new DialogNavigator());
        return navHostController;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0038, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.navigation.NavHostController rememberNavController(androidx.navigation.Navigator[] r9, androidx.compose.runtime.ComposerImpl r10) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.navigation.compose.rememberNavController (NavHostController.kt:57)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            androidx.compose.runtime.StaticProvidableCompositionLocal r0 = androidx.compose.ui.platform.AndroidCompositionLocals_androidKt.LocalContext
            java.lang.Object r0 = r10.consume(r0)
            android.content.Context r0 = (android.content.Context) r0
            int r1 = r9.length
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r9, r1)
            androidx.navigation.compose.NavHostControllerKt$NavControllerSaver$1 r1 = new kotlin.jvm.functions.Function2() { // from class: androidx.navigation.compose.NavHostControllerKt$NavControllerSaver$1
                static {
                    /*
                        androidx.navigation.compose.NavHostControllerKt$NavControllerSaver$1 r0 = new androidx.navigation.compose.NavHostControllerKt$NavControllerSaver$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:androidx.navigation.compose.NavHostControllerKt$NavControllerSaver$1) androidx.navigation.compose.NavHostControllerKt$NavControllerSaver$1.INSTANCE androidx.navigation.compose.NavHostControllerKt$NavControllerSaver$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.compose.NavHostControllerKt$NavControllerSaver$1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 2
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.compose.NavHostControllerKt$NavControllerSaver$1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function2
                public final java.lang.Object invoke(java.lang.Object r10, java.lang.Object r11) {
                    /*
                        Method dump skipped, instructions count: 356
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.compose.NavHostControllerKt$NavControllerSaver$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }
            androidx.navigation.compose.NavHostControllerKt$NavControllerSaver$2 r3 = new androidx.navigation.compose.NavHostControllerKt$NavControllerSaver$2
            r3.<init>()
            androidx.compose.runtime.saveable.SaverKt$Saver$1 r4 = androidx.compose.runtime.saveable.SaverKt.AutoSaver
            r4 = r3
            androidx.compose.runtime.saveable.SaverKt$Saver$1 r3 = new androidx.compose.runtime.saveable.SaverKt$Saver$1
            r3.<init>(r1, r4)
            boolean r1 = r10.changedInstance(r0)
            java.lang.Object r4 = r10.rememberedValue()
            if (r1 != 0) goto L3a
            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
            r1.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r4 != r1) goto L42
        L3a:
            androidx.navigation.compose.NavHostControllerKt$rememberNavController$1$1 r4 = new androidx.navigation.compose.NavHostControllerKt$rememberNavController$1$1
            r4.<init>()
            r10.updateRememberedValue(r4)
        L42:
            r5 = r4
            kotlin.jvm.functions.Function0 r5 = (kotlin.jvm.functions.Function0) r5
            r8 = 4
            r4 = 0
            r7 = 0
            r6 = r10
            java.lang.Object r10 = androidx.compose.runtime.saveable.RememberSaveableKt.rememberSaveable(r2, r3, r4, r5, r6, r7, r8)
            androidx.navigation.NavHostController r10 = (androidx.navigation.NavHostController) r10
            int r0 = r9.length
            r1 = 0
        L51:
            if (r1 >= r0) goto L5d
            r2 = r9[r1]
            androidx.navigation.NavigatorProvider r3 = r10._navigatorProvider
            r3.addNavigator(r2)
            int r1 = r1 + 1
            goto L51
        L5d:
            boolean r9 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r9 == 0) goto L66
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L66:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.compose.NavHostControllerKt.rememberNavController(androidx.navigation.Navigator[], androidx.compose.runtime.ComposerImpl):androidx.navigation.NavHostController");
    }
}
