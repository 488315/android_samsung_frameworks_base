package androidx.navigation;

import androidx.navigation.NavOptions;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class NavOptionsBuilderKt {
    public static final NavOptions navOptions(Function1 function1) {
        NavOptionsBuilder navOptionsBuilder = new NavOptionsBuilder();
        function1.mo779invoke(navOptionsBuilder);
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
