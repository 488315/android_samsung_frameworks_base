package androidx.navigation;

import android.os.Bundle;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigator;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@Navigator.Name("navigation")
/* loaded from: classes.dex */
public class NavGraphNavigator extends Navigator {
    public final NavigatorProvider navigatorProvider;

    public NavGraphNavigator(NavigatorProvider navigatorProvider) {
        this.navigatorProvider = navigatorProvider;
    }

    @Override // androidx.navigation.Navigator
    public final void navigate(List list, NavOptions navOptions) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            NavBackStackEntry navBackStackEntry = (NavBackStackEntry) it.next();
            NavGraph navGraph = (NavGraph) navBackStackEntry.destination;
            Bundle arguments = navBackStackEntry.getArguments();
            int i = navGraph.startDestId;
            String str = navGraph.startDestinationRoute;
            if (i == 0 && str == null) {
                StringBuilder sb = new StringBuilder("no start destination defined via app:startDestination for ");
                int i2 = navGraph.id;
                sb.append(i2 != 0 ? String.valueOf(i2) : "the root navigation");
                throw new IllegalStateException(sb.toString().toString());
            }
            NavDestination findNode = str != null ? navGraph.findNode(str, false) : (NavDestination) navGraph.nodes.get(i);
            if (findNode == null) {
                if (navGraph.startDestIdName == null) {
                    String str2 = navGraph.startDestinationRoute;
                    if (str2 == null) {
                        str2 = String.valueOf(navGraph.startDestId);
                    }
                    navGraph.startDestIdName = str2;
                }
                String str3 = navGraph.startDestIdName;
                str3.getClass();
                throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("navigation destination ", str3, " is not a direct child of this NavGraph"));
            }
            if (str != null) {
                NavDestination.DeepLinkMatch matchDeepLink = findNode.matchDeepLink(str);
                Bundle bundle = matchDeepLink != null ? matchDeepLink.matchingArgs : null;
                if (bundle != null && !bundle.isEmpty()) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putAll(bundle);
                    if (arguments != null) {
                        bundle2.putAll(arguments);
                    }
                    arguments = bundle2;
                }
            }
            this.navigatorProvider.getNavigator(findNode.navigatorName).navigate(Collections.singletonList(getState().createBackStackEntry(findNode, findNode.addInDefaultArgs(arguments))), navOptions);
        }
    }

    @Override // androidx.navigation.Navigator
    public NavGraph createDestination() {
        return new NavGraph(this);
    }
}
