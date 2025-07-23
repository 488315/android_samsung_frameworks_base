package androidx.navigation.compose;

import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.navigation.NamedNavArgument;
import androidx.navigation.NavDeepLink;
import androidx.navigation.NavGraphBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class NavGraphBuilderKt {
    public static void composable$default(NavGraphBuilder navGraphBuilder, String str, List list, ComposableLambdaImpl composableLambdaImpl, int i) {
        if ((i & 2) != 0) {
            list = EmptyList.INSTANCE;
        }
        EmptyList emptyList = EmptyList.INSTANCE;
        ComposeNavigatorDestinationBuilder composeNavigatorDestinationBuilder = new ComposeNavigatorDestinationBuilder((ComposeNavigator) navGraphBuilder.provider.getNavigator(ComposeNavigator.class), str, composableLambdaImpl);
        for (NamedNavArgument namedNavArgument : list) {
            composeNavigatorDestinationBuilder.arguments.put(namedNavArgument.name, namedNavArgument.argument);
        }
        Iterator<E> it = emptyList.iterator();
        while (it.hasNext()) {
            ((ArrayList) composeNavigatorDestinationBuilder.deepLinks).add((NavDeepLink) it.next());
        }
        ((ArrayList) navGraphBuilder.destinations).add(composeNavigatorDestinationBuilder.build());
    }
}
