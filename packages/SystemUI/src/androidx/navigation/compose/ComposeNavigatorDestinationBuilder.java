package androidx.navigation.compose;

import androidx.navigation.NavDestination;
import androidx.navigation.NavDestinationBuilder;
import androidx.navigation.NavType;
import androidx.navigation.compose.ComposeNavigator;
import java.util.Map;
import kotlin.jvm.functions.Function4;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;

/* loaded from: classes.dex */
public final class ComposeNavigatorDestinationBuilder extends NavDestinationBuilder {
    public final ComposeNavigator composeNavigator;
    public final Function4 content;

    public ComposeNavigatorDestinationBuilder(ComposeNavigator composeNavigator, String str, Function4 function4) {
        super(composeNavigator, str);
        this.composeNavigator = composeNavigator;
        this.content = function4;
    }

    @Override // androidx.navigation.NavDestinationBuilder
    public final NavDestination build() {
        return (ComposeNavigator.Destination) super.build();
    }

    @Override // androidx.navigation.NavDestinationBuilder
    public final NavDestination instantiateDestination() {
        return new ComposeNavigator.Destination(this.composeNavigator, this.content);
    }

    public ComposeNavigatorDestinationBuilder(ComposeNavigator composeNavigator, KClass kClass, Map<KType, NavType> map, Function4 function4) {
        super(composeNavigator, kClass, map);
        this.composeNavigator = composeNavigator;
        this.content = function4;
    }
}
