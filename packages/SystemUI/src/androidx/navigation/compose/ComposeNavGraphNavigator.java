package androidx.navigation.compose;

import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.Navigator;
import androidx.navigation.NavigatorProvider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@Navigator.Name("navigation")
/* loaded from: classes.dex */
public final class ComposeNavGraphNavigator extends NavGraphNavigator {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ComposeNavGraph extends NavGraph {
        public ComposeNavGraph(Navigator navigator) {
            super(navigator);
        }
    }

    public ComposeNavGraphNavigator(NavigatorProvider navigatorProvider) {
        super(navigatorProvider);
    }

    @Override // androidx.navigation.NavGraphNavigator, androidx.navigation.Navigator
    public final NavDestination createDestination() {
        return new ComposeNavGraph(this);
    }

    @Override // androidx.navigation.NavGraphNavigator, androidx.navigation.Navigator
    public final NavGraph createDestination() {
        return new ComposeNavGraph(this);
    }
}
