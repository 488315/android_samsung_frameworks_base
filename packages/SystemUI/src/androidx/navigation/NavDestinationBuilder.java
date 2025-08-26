package androidx.navigation;

import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavDeepLink;
import androidx.navigation.NavDestination;
import androidx.navigation.serialization.RouteSerializerKt;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.serialization.SerializersKt;

/* loaded from: classes.dex */
public class NavDestinationBuilder {
    public final Map actions;
    public final Map arguments;
    public final List deepLinks;
    public final int id;
    public final Navigator navigator;
    public final String route;

    public NavDestinationBuilder(Navigator navigator, int i, String str) {
        this.navigator = navigator;
        this.id = i;
        this.route = str;
        this.arguments = new LinkedHashMap();
        this.deepLinks = new ArrayList();
        this.actions = new LinkedHashMap();
    }

    public NavDestination build() {
        NavDestination navDestinationInstantiateDestination = instantiateDestination();
        navDestinationInstantiateDestination.getClass();
        for (Map.Entry entry : ((LinkedHashMap) this.arguments).entrySet()) {
            navDestinationInstantiateDestination._arguments.put((String) entry.getKey(), (NavArgument) entry.getValue());
        }
        ArrayList arrayList = (ArrayList) this.deepLinks;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            navDestinationInstantiateDestination.addDeepLink((NavDeepLink) obj);
        }
        for (Map.Entry entry2 : ((LinkedHashMap) this.actions).entrySet()) {
            int iIntValue = ((Number) entry2.getKey()).intValue();
            NavAction navAction = (NavAction) entry2.getValue();
            if (navDestinationInstantiateDestination instanceof ActivityNavigator.Destination) {
                throw new UnsupportedOperationException("Cannot add action " + iIntValue + " to " + navDestinationInstantiateDestination + " as it does not support actions, indicating that it is a terminal destination in your navigation graph and will never trigger actions.");
            }
            if (iIntValue == 0) {
                throw new IllegalArgumentException("Cannot have an action with actionId 0");
            }
            navDestinationInstantiateDestination.actions.put(iIntValue, navAction);
        }
        String str = this.route;
        if (str != null) {
            NavDestination.Companion companion = NavDestination.Companion;
            if (StringsKt__StringsKt.isBlank(str)) {
                throw new IllegalArgumentException("Cannot have an empty route");
            }
            companion.getClass();
            String strConcat = "android-app://androidx.navigation/".concat(str);
            navDestinationInstantiateDestination.id = strConcat.hashCode();
            NavDeepLink.Builder builder = new NavDeepLink.Builder();
            builder.uriPattern = strConcat;
            Object obj2 = null;
            navDestinationInstantiateDestination.addDeepLink(new NavDeepLink(builder.uriPattern, null, null));
            ArrayList arrayList2 = (ArrayList) navDestinationInstantiateDestination.deepLinks;
            int size2 = arrayList2.size();
            while (true) {
                if (i >= size2) {
                    break;
                }
                Object obj3 = arrayList2.get(i);
                i++;
                String str2 = ((NavDeepLink) obj3).uriPattern;
                String str3 = navDestinationInstantiateDestination.route;
                companion.getClass();
                if (Intrinsics.areEqual(str2, str3 != null ? "android-app://androidx.navigation/".concat(str3) : "")) {
                    obj2 = obj3;
                    break;
                }
            }
            TypeIntrinsics.asMutableCollection(arrayList2).remove(obj2);
            navDestinationInstantiateDestination.route = str;
        }
        int i3 = this.id;
        if (i3 != -1) {
            navDestinationInstantiateDestination.id = i3;
        }
        return navDestinationInstantiateDestination;
    }

    public NavDestination instantiateDestination() {
        return this.navigator.createDestination();
    }

    public NavDestinationBuilder(Navigator navigator, int i) {
        this(navigator, i, (String) null);
    }

    public NavDestinationBuilder(Navigator navigator, String str) {
        this(navigator, -1, str);
    }

    public NavDestinationBuilder(Navigator navigator, KClass kClass, Map<KType, NavType> map) {
        this(navigator, kClass != null ? SerializersKt.serializer(kClass).hashCode() : -1, kClass != null ? RouteSerializerKt.generateRoutePattern$default(SerializersKt.serializer(kClass), map) : null);
        if (kClass != null) {
            ArrayList arrayList = (ArrayList) RouteSerializerKt.generateNavArguments(SerializersKt.serializer(kClass), map);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                NamedNavArgument namedNavArgument = (NamedNavArgument) obj;
                this.arguments.put(namedNavArgument.name, namedNavArgument.argument);
            }
        }
    }
}
