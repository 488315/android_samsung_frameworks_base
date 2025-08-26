package androidx.navigation;

import androidx.navigation.serialization.RouteSerializerKt;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlinx.serialization.SerializersKt;

/* loaded from: classes.dex */
public class NavGraphBuilder extends NavDestinationBuilder {
    public final List destinations;
    public final NavigatorProvider provider;
    public final KClass startDestinationClass;
    public final int startDestinationId;
    public final Object startDestinationObject;
    public final String startDestinationRoute;

    public NavGraphBuilder(NavigatorProvider navigatorProvider, int i, int i2) {
        super(navigatorProvider.getNavigator(NavGraphNavigator.class), i);
        this.destinations = new ArrayList();
        this.provider = navigatorProvider;
        this.startDestinationId = i2;
    }

    @Override // androidx.navigation.NavDestinationBuilder
    public final NavGraph build() {
        NavGraph navGraph = (NavGraph) super.build();
        ArrayList arrayList = (ArrayList) this.destinations;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            NavDestination navDestination = (NavDestination) obj;
            if (navDestination != null) {
                int i2 = navDestination.id;
                String str = navDestination.route;
                if (i2 == 0 && str == null) {
                    throw new IllegalArgumentException("Destinations must have an id or route. Call setId(), setRoute(), or include an android:id or app:route in your navigation XML.");
                }
                String str2 = navGraph.route;
                if (str2 != null && Intrinsics.areEqual(str, str2)) {
                    throw new IllegalArgumentException(("Destination " + navDestination + " cannot have the same route as graph " + navGraph).toString());
                }
                if (i2 == navGraph.id) {
                    throw new IllegalArgumentException(("Destination " + navDestination + " cannot have the same id as graph " + navGraph).toString());
                }
                NavDestination navDestination2 = (NavDestination) navGraph.nodes.get(i2);
                if (navDestination2 == navDestination) {
                    continue;
                } else {
                    if (navDestination.parent != null) {
                        throw new IllegalStateException("Destination already has a parent set. Call NavGraph.remove() to remove the previous parent.");
                    }
                    if (navDestination2 != null) {
                        navDestination2.parent = null;
                    }
                    navDestination.parent = navGraph;
                    navGraph.nodes.put(navDestination.id, navDestination);
                }
            }
        }
        Object obj2 = this.startDestinationObject;
        KClass kClass = this.startDestinationClass;
        String str3 = this.startDestinationRoute;
        int i3 = this.startDestinationId;
        if (i3 == 0 && str3 == null && kClass == null && obj2 == null) {
            if (this.route != null) {
                throw new IllegalStateException("You must set a start destination route");
            }
            throw new IllegalStateException("You must set a start destination id");
        }
        if (str3 != null) {
            navGraph.setStartDestinationRoute(str3);
            return navGraph;
        }
        if (kClass != null) {
            navGraph.setStartDestination(SerializersKt.serializer(kClass), new Function1() { // from class: androidx.navigation.NavGraphBuilder$build$1$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj3) {
                    String str4 = ((NavDestination) obj3).route;
                    str4.getClass();
                    return str4;
                }
            });
            return navGraph;
        }
        if (obj2 != null) {
            navGraph.setStartDestination(SerializersKt.serializer(Reflection.getOrCreateKotlinClass(obj2.getClass())), new Function1() { // from class: androidx.navigation.NavGraph.setStartDestination.2
                final /* synthetic */ Object $startDestRoute;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(Object obj22) {
                    super(1);
                    obj = obj22;
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj3) {
                    Map map = MapsKt__MapsKt.toMap(((NavDestination) obj3)._arguments);
                    LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map.size()));
                    for (Map.Entry entry : map.entrySet()) {
                        linkedHashMap.put(entry.getKey(), ((NavArgument) entry.getValue()).type);
                    }
                    return RouteSerializerKt.generateRouteWithArgs(obj, linkedHashMap);
                }
            });
            return navGraph;
        }
        if (i3 != navGraph.id) {
            if (navGraph.startDestinationRoute != null) {
                navGraph.setStartDestinationRoute(null);
            }
            navGraph.startDestId = i3;
            navGraph.startDestIdName = null;
            return navGraph;
        }
        throw new IllegalArgumentException(("Start destination " + i3 + " cannot use the same id as the graph " + navGraph).toString());
    }

    public NavGraphBuilder(NavigatorProvider navigatorProvider, String str, String str2) {
        super(navigatorProvider.getNavigator(NavGraphNavigator.class), str2);
        this.destinations = new ArrayList();
        this.provider = navigatorProvider;
        this.startDestinationRoute = str;
    }

    public NavGraphBuilder(NavigatorProvider navigatorProvider, KClass kClass, KClass kClass2, Map<KType, NavType> map) {
        super(navigatorProvider.getNavigator(NavGraphNavigator.class), kClass2, map);
        this.destinations = new ArrayList();
        this.provider = navigatorProvider;
        this.startDestinationClass = kClass;
    }

    public NavGraphBuilder(NavigatorProvider navigatorProvider, Object obj, KClass kClass, Map<KType, NavType> map) {
        super(navigatorProvider.getNavigator(NavGraphNavigator.class), kClass, map);
        this.destinations = new ArrayList();
        this.provider = navigatorProvider;
        this.startDestinationObject = obj;
    }
}
