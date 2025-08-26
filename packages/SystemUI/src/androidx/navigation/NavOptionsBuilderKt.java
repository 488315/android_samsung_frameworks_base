package androidx.navigation;

import androidx.navigation.NavOptions;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class NavOptionsBuilderKt {
    public static final NavOptions navOptions(Function1 function1) {
        NavOptionsBuilder navOptionsBuilder = new NavOptionsBuilder();
        function1.mo781invoke(navOptionsBuilder);
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
        return builder.build();
    }
}
