package androidx.navigation;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.collection.SparseArrayCompat;
import androidx.collection.SparseArrayKt$valueIterator$1;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.navigation.NavDeepLink;
import androidx.navigation.NavDeepLinkRequest;
import androidx.navigation.NavigatorProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes.dex */
public class NavDestination {
    public static final Companion Companion = new Companion(null);
    public final Map _arguments;
    public final SparseArrayCompat actions;
    public final List deepLinks;
    public int id;
    public final String navigatorName;
    public NavGraph parent;
    public String route;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static String getDisplayName(int i, Context context) {
            if (i <= 16777215) {
                return String.valueOf(i);
            }
            try {
                return context.getResources().getResourceName(i);
            } catch (Resources.NotFoundException unused) {
                return String.valueOf(i);
            }
        }

        public static Sequence getHierarchy(NavDestination navDestination) {
            return SequencesKt__SequencesKt.generateSequence(navDestination, NavDestination$Companion$hierarchy$1.INSTANCE);
        }

        private Companion() {
        }
    }

    public final class DeepLinkMatch implements Comparable {
        public final NavDestination destination;
        public final boolean hasMatchingAction;
        public final boolean isExactDeepLink;
        public final Bundle matchingArgs;
        public final int matchingPathSegments;
        public final int mimeTypeMatchLevel;

        public DeepLinkMatch(NavDestination navDestination, Bundle bundle, boolean z, int i, boolean z2, int i2) {
            this.destination = navDestination;
            this.matchingArgs = bundle;
            this.isExactDeepLink = z;
            this.matchingPathSegments = i;
            this.hasMatchingAction = z2;
            this.mimeTypeMatchLevel = i2;
        }

        @Override // java.lang.Comparable
        public final int compareTo(DeepLinkMatch deepLinkMatch) {
            boolean z = this.isExactDeepLink;
            if (z && !deepLinkMatch.isExactDeepLink) {
                return 1;
            }
            if (!z && deepLinkMatch.isExactDeepLink) {
                return -1;
            }
            int i = this.matchingPathSegments - deepLinkMatch.matchingPathSegments;
            if (i > 0) {
                return 1;
            }
            if (i < 0) {
                return -1;
            }
            Bundle bundle = this.matchingArgs;
            if (bundle != null && deepLinkMatch.matchingArgs == null) {
                return 1;
            }
            if (bundle == null && deepLinkMatch.matchingArgs != null) {
                return -1;
            }
            if (bundle != null) {
                int size = bundle.size();
                Bundle bundle2 = deepLinkMatch.matchingArgs;
                bundle2.getClass();
                int size2 = size - bundle2.size();
                if (size2 > 0) {
                    return 1;
                }
                if (size2 < 0) {
                    return -1;
                }
            }
            boolean z2 = this.hasMatchingAction;
            if (z2 && !deepLinkMatch.hasMatchingAction) {
                return 1;
            }
            if (z2 || !deepLinkMatch.hasMatchingAction) {
                return this.mimeTypeMatchLevel - deepLinkMatch.mimeTypeMatchLevel;
            }
            return -1;
        }
    }

    static {
        new LinkedHashMap();
    }

    public NavDestination(String str) {
        this.navigatorName = str;
        this.deepLinks = new ArrayList();
        this.actions = new SparseArrayCompat();
        this._arguments = new LinkedHashMap();
    }

    public final void addDeepLink(final NavDeepLink navDeepLink) {
        List listMissingRequiredArguments = NavArgumentKt.missingRequiredArguments(this._arguments, new Function1() { // from class: androidx.navigation.NavDestination$addDeepLink$missingRequiredArguments$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                String str = (String) obj;
                NavDeepLink navDeepLink2 = navDeepLink;
                List list = navDeepLink2.pathArgs;
                Collection collectionValues = ((Map) navDeepLink2.queryArgsMap$delegate.getValue()).values();
                ArrayList arrayList = new ArrayList();
                Iterator it = collectionValues.iterator();
                while (it.hasNext()) {
                    CollectionsKt__MutableCollectionsKt.addAll(((NavDeepLink.ParamQuery) it.next()).arguments, arrayList);
                }
                return Boolean.valueOf(!((ArrayList) CollectionsKt___CollectionsKt.plus((Iterable) navDeepLink2.fragArgs$delegate.getValue(), (Collection) CollectionsKt___CollectionsKt.plus((Iterable) arrayList, (Collection) list))).contains(str));
            }
        });
        if (((ArrayList) listMissingRequiredArguments).isEmpty()) {
            ((ArrayList) this.deepLinks).add(navDeepLink);
            return;
        }
        throw new IllegalArgumentException(("Deep link " + navDeepLink.uriPattern + " can't be used to open destination " + this + ".\nFollowing required arguments are missing: " + listMissingRequiredArguments).toString());
    }

    public final Bundle addInDefaultArgs(Bundle bundle) {
        Object obj;
        if (bundle == null && this._arguments.isEmpty()) {
            return null;
        }
        Bundle bundle2 = new Bundle();
        for (Map.Entry entry : ((LinkedHashMap) this._arguments).entrySet()) {
            String str = (String) entry.getKey();
            NavArgument navArgument = (NavArgument) entry.getValue();
            if (navArgument.isDefaultValuePresent && (obj = navArgument.defaultValue) != null) {
                navArgument.type.put(bundle2, str, obj);
            }
        }
        if (bundle != null) {
            bundle2.putAll(bundle);
            for (Map.Entry entry2 : ((LinkedHashMap) this._arguments).entrySet()) {
                String str2 = (String) entry2.getKey();
                NavArgument navArgument2 = (NavArgument) entry2.getValue();
                if (!navArgument2.isDefaultValueUnknown) {
                    NavType navType = navArgument2.type;
                    if (navArgument2.isNullable || !bundle2.containsKey(str2) || bundle2.get(str2) != null) {
                        try {
                            navType.get(bundle2, str2);
                        } catch (ClassCastException unused) {
                        }
                    }
                    StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Wrong argument type for '", str2, "' in argument bundle. ");
                    sbM.append(navType.getName());
                    sbM.append(" expected.");
                    throw new IllegalArgumentException(sbM.toString().toString());
                }
            }
        }
        return bundle2;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this != obj) {
            if (obj != null && (obj instanceof NavDestination)) {
                NavDestination navDestination = (NavDestination) obj;
                boolean zAreEqual = Intrinsics.areEqual(this.deepLinks, navDestination.deepLinks);
                final SparseArrayCompat sparseArrayCompat = this.actions;
                int size = sparseArrayCompat.size();
                SparseArrayCompat sparseArrayCompat2 = navDestination.actions;
                if (size == sparseArrayCompat2.size()) {
                    Iterator it = SequencesKt__SequencesKt.asSequence(new IntIterator() { // from class: androidx.collection.SparseArrayKt$keyIterator$1
                        public int index;

                        @Override // java.util.Iterator
                        public final boolean hasNext() {
                            return this.index < sparseArrayCompat.size();
                        }

                        @Override // kotlin.collections.IntIterator
                        public final int nextInt() {
                            SparseArrayCompat sparseArrayCompat3 = sparseArrayCompat;
                            int i = this.index;
                            this.index = i + 1;
                            return sparseArrayCompat3.keyAt(i);
                        }
                    }).iterator();
                    while (it.hasNext()) {
                        int iIntValue = ((Number) it.next()).intValue();
                        if (!Intrinsics.areEqual(sparseArrayCompat.get(iIntValue), sparseArrayCompat2.get(iIntValue))) {
                        }
                    }
                    z = true;
                    if (this._arguments.size() == navDestination._arguments.size()) {
                        for (Map.Entry entry : new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(((LinkedHashMap) this._arguments).entrySet()).$this_asSequence$inlined) {
                            if (navDestination._arguments.containsKey(entry.getKey())) {
                                if (Intrinsics.areEqual(((LinkedHashMap) navDestination._arguments).get(entry.getKey()), entry.getValue())) {
                                }
                            }
                        }
                        z2 = true;
                        if (this.id == navDestination.id || !Intrinsics.areEqual(this.route, navDestination.route) || !zAreEqual || !z || !z2) {
                        }
                    }
                    z2 = false;
                    if (this.id == navDestination.id) {
                    }
                }
                z = false;
                if (this._arguments.size() == navDestination._arguments.size()) {
                }
                z2 = false;
                if (this.id == navDestination.id) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        Set<String> setKeySet;
        int i = this.id * 31;
        String str = this.route;
        int iHashCode = i + (str != null ? str.hashCode() : 0);
        for (NavDeepLink navDeepLink : this.deepLinks) {
            int i2 = iHashCode * 31;
            String str2 = navDeepLink.uriPattern;
            int iHashCode2 = (i2 + (str2 != null ? str2.hashCode() : 0)) * 31;
            String str3 = navDeepLink.action;
            int iHashCode3 = (iHashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
            String str4 = navDeepLink.mimeType;
            iHashCode = iHashCode3 + (str4 != null ? str4.hashCode() : 0);
        }
        SparseArrayKt$valueIterator$1 sparseArrayKt$valueIterator$1 = new SparseArrayKt$valueIterator$1(this.actions);
        while (sparseArrayKt$valueIterator$1.hasNext()) {
            NavAction navAction = (NavAction) sparseArrayKt$valueIterator$1.next();
            int i3 = ((iHashCode * 31) + navAction.destinationId) * 31;
            NavOptions navOptions = navAction.navOptions;
            iHashCode = i3 + (navOptions != null ? navOptions.hashCode() : 0);
            Bundle bundle = navAction.defaultArguments;
            if (bundle != null && (setKeySet = bundle.keySet()) != null) {
                for (String str5 : setKeySet) {
                    int i4 = iHashCode * 31;
                    Bundle bundle2 = navAction.defaultArguments;
                    bundle2.getClass();
                    Object obj = bundle2.get(str5);
                    iHashCode = i4 + (obj != null ? obj.hashCode() : 0);
                }
            }
        }
        for (String str6 : ((LinkedHashMap) this._arguments).keySet()) {
            int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(iHashCode * 31, 31, str6);
            Object obj2 = ((LinkedHashMap) this._arguments).get(str6);
            iHashCode = iM + (obj2 != null ? obj2.hashCode() : 0);
        }
        return iHashCode;
    }

    public final DeepLinkMatch matchDeepLink(String str) {
        NavDeepLinkRequest.Builder.Companion companion = NavDeepLinkRequest.Builder.Companion;
        Companion.getClass();
        Uri uri = Uri.parse(str != null ? "android-app://androidx.navigation/".concat(str) : "");
        companion.getClass();
        new NavDeepLinkRequest.Builder(null).uri = uri;
        NavDeepLinkRequest navDeepLinkRequest = new NavDeepLinkRequest(uri, null, null);
        return this instanceof NavGraph ? ((NavGraph) this).matchDeepLinkExcludingChildren(navDeepLinkRequest) : matchDeepLink(navDeepLinkRequest);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("(0x");
        sb.append(Integer.toHexString(this.id));
        sb.append(")");
        String str = this.route;
        if (str != null && !StringsKt__StringsKt.isBlank(str)) {
            sb.append(" route=");
            sb.append(this.route);
        }
        return sb.toString();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public NavDestination(Navigator navigator) {
        NavigatorProvider.Companion companion = NavigatorProvider.Companion;
        Class<?> cls = navigator.getClass();
        companion.getClass();
        this(NavigatorProvider.Companion.getNameForNavigator$navigation_common_release(cls));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0181  */
    /* JADX WARN: Type inference failed for: r17v3, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r17v4 */
    /* JADX WARN: Type inference failed for: r17v5 */
    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.navigation.NavDeepLink, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v23, types: [androidx.navigation.NavType] */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v4, types: [android.os.Bundle] */
    /* JADX WARN: Type inference failed for: r5v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public DeepLinkMatch matchDeepLink(NavDeepLinkRequest navDeepLinkRequest) {
        Matcher matcher;
        final Bundle bundle;
        Matcher matcher2;
        int size;
        String str;
        int i;
        String str2;
        ?? r17;
        NavDestination navDestination = this;
        Matcher matcher3 = null;
        if (((ArrayList) navDestination.deepLinks).isEmpty()) {
            return null;
        }
        ArrayList arrayList = (ArrayList) navDestination.deepLinks;
        int size2 = arrayList.size();
        DeepLinkMatch deepLinkMatch = null;
        int i2 = 0;
        while (i2 < size2) {
            int i3 = i2 + 1;
            ?? r2 = (NavDeepLink) arrayList.get(i2);
            Uri uri = navDeepLinkRequest.uri;
            if (uri != null) {
                Map map = navDestination._arguments;
                Pattern pattern = (Pattern) r2.pathPattern$delegate.getValue();
                Matcher matcher4 = pattern != null ? pattern.matcher(uri.toString()) : matcher3;
                if (matcher4 != null && matcher4.matches()) {
                    bundle = new Bundle();
                    if (r2.getMatchingPathArguments(matcher4, bundle, map) && (!((Boolean) r2.isParameterizedQuery$delegate.getValue()).booleanValue() || r2.getMatchingQueryArguments(uri, bundle, map))) {
                        String fragment = uri.getFragment();
                        Pattern pattern2 = (Pattern) r2.fragPattern$delegate.getValue();
                        Matcher matcher5 = pattern2 != null ? pattern2.matcher(String.valueOf(fragment)) : matcher3;
                        if (matcher5 != null && matcher5.matches()) {
                            List list = (List) r2.fragArgs$delegate.getValue();
                            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                            int i4 = 0;
                            for (Object obj : list) {
                                r17 = matcher3;
                                int i5 = i4 + 1;
                                if (i4 < 0) {
                                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                                    throw r17;
                                }
                                String str3 = (String) obj;
                                String strDecode = Uri.decode(matcher5.group(i5));
                                Matcher matcher6 = matcher5;
                                NavArgument navArgument = (NavArgument) ((LinkedHashMap) map).get(str3);
                                if (navArgument != null) {
                                    try {
                                        ?? r4 = navArgument.type;
                                        r4.put(bundle, str3, r4.parseValue(strDecode));
                                    } catch (IllegalArgumentException unused) {
                                    }
                                } else {
                                    bundle.putString(str3, strDecode);
                                }
                                arrayList2.add(Unit.INSTANCE);
                                i4 = i5;
                                matcher5 = matcher6;
                                matcher3 = r17;
                            }
                            r17 = matcher3;
                            matcher2 = r17;
                            matcher = r17;
                            if (!((ArrayList) NavArgumentKt.missingRequiredArguments(map, new Function1() { // from class: androidx.navigation.NavDeepLink$getMatchingArguments$missingRequiredArguments$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    return Boolean.valueOf(!bundle.containsKey((String) obj2));
                                }
                            })).isEmpty()) {
                            }
                        } else {
                            r17 = matcher3;
                            matcher2 = r17;
                            matcher = r17;
                            if (!((ArrayList) NavArgumentKt.missingRequiredArguments(map, new Function1() { // from class: androidx.navigation.NavDeepLink$getMatchingArguments$missingRequiredArguments$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    return Boolean.valueOf(!bundle.containsKey((String) obj2));
                                }
                            })).isEmpty()) {
                            }
                        }
                    } else {
                        bundle = matcher3;
                        matcher2 = bundle;
                    }
                }
                if (uri == null) {
                    String str4 = r2.uriPattern;
                    if (str4 != null) {
                        size = CollectionsKt___CollectionsKt.intersect(uri.getPathSegments(), Uri.parse(str4).getPathSegments()).size();
                    }
                    String str5 = navDeepLinkRequest.action;
                    boolean z = str5 != null && str5.equals(r2.action);
                    str = navDeepLinkRequest.mimeType;
                    if (str == null || (str2 = r2.mimeType) == null) {
                        i = -1;
                    } else {
                        Pattern pattern3 = (Pattern) r2.mimeTypePattern$delegate.getValue();
                        pattern3.getClass();
                        if (pattern3.matcher(str).matches()) {
                            NavDeepLink.MimeType mimeType = new NavDeepLink.MimeType(str2);
                            NavDeepLink.MimeType mimeType2 = new NavDeepLink.MimeType(str);
                            i = Intrinsics.areEqual(mimeType.type, mimeType2.type) ? 2 : 0;
                            if (Intrinsics.areEqual(mimeType.subType, mimeType2.subType)) {
                                i++;
                            }
                        }
                    }
                    if (bundle != null) {
                        DeepLinkMatch deepLinkMatch2 = new DeepLinkMatch(navDestination, bundle, r2.isExactDeepLink, size, z, i);
                        if (deepLinkMatch == null || deepLinkMatch2.compareTo(deepLinkMatch) > 0) {
                            navDestination = this;
                            deepLinkMatch = deepLinkMatch2;
                        } else {
                            navDestination = this;
                        }
                    } else {
                        if (z || i > -1) {
                            Map map2 = navDestination._arguments;
                            final Bundle bundle2 = new Bundle();
                            if (uri != null) {
                                Pattern pattern4 = (Pattern) r2.pathPattern$delegate.getValue();
                                Matcher matcher7 = pattern4 != null ? pattern4.matcher(uri.toString()) : matcher2;
                                if (matcher7 != null && matcher7.matches()) {
                                    r2.getMatchingPathArguments(matcher7, bundle2, map2);
                                    if (((Boolean) r2.isParameterizedQuery$delegate.getValue()).booleanValue()) {
                                        r2.getMatchingQueryArguments(uri, bundle2, map2);
                                    }
                                }
                            }
                            if (((ArrayList) NavArgumentKt.missingRequiredArguments(map2, new Function1() { // from class: androidx.navigation.NavDestination$hasRequiredArguments$missingRequiredArguments$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    return Boolean.valueOf(!bundle2.containsKey((String) obj2));
                                }
                            })).isEmpty()) {
                            }
                        }
                        navDestination = this;
                    }
                    i2 = i3;
                    matcher3 = matcher2;
                } else {
                    r2.getClass();
                }
                size = 0;
                String str52 = navDeepLinkRequest.action;
                if (str52 != null) {
                }
                str = navDeepLinkRequest.mimeType;
                if (str == null) {
                    i = -1;
                }
                if (bundle != null) {
                }
                i2 = i3;
                matcher3 = matcher2;
            } else {
                matcher = matcher3;
            }
            bundle = matcher;
            matcher2 = matcher;
            if (uri == null) {
            }
            size = 0;
            String str522 = navDeepLinkRequest.action;
            if (str522 != null) {
            }
            str = navDeepLinkRequest.mimeType;
            if (str == null) {
            }
            if (bundle != null) {
            }
            i2 = i3;
            matcher3 = matcher2;
        }
        return deepLinkMatch;
    }
}
