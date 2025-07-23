package androidx.navigation;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class NavGraph$Companion$findStartDestination$1 extends Lambda implements Function1 {
    public static final NavGraph$Companion$findStartDestination$1 INSTANCE = new NavGraph$Companion$findStartDestination$1();

    public NavGraph$Companion$findStartDestination$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        NavDestination navDestination = (NavDestination) obj;
        if (!(navDestination instanceof NavGraph)) {
            return null;
        }
        NavGraph navGraph = (NavGraph) navDestination;
        return navGraph.findNodeComprehensive(navGraph.startDestId, navGraph, false);
    }
}
