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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        List missingRequiredArguments = NavArgumentKt.missingRequiredArguments(this._arguments, new Function1() { // from class: androidx.navigation.NavDestination$addDeepLink$missingRequiredArguments$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                String str = (String) obj;
                NavDeepLink navDeepLink2 = NavDeepLink.this;
                List list = navDeepLink2.pathArgs;
                Collection values = ((Map) navDeepLink2.queryArgsMap$delegate.getValue()).values();
                ArrayList arrayList = new ArrayList();
                Iterator it = values.iterator();
                while (it.hasNext()) {
                    CollectionsKt__MutableCollectionsKt.addAll(((NavDeepLink.ParamQuery) it.next()).arguments, arrayList);
                }
                return Boolean.valueOf(!((ArrayList) CollectionsKt___CollectionsKt.plus((Iterable) navDeepLink2.fragArgs$delegate.getValue(), (Collection) CollectionsKt___CollectionsKt.plus((Iterable) arrayList, (Collection) list))).contains(str));
            }
        });
        if (((ArrayList) missingRequiredArguments).isEmpty()) {
            ((ArrayList) this.deepLinks).add(navDeepLink);
            return;
        }
        throw new IllegalArgumentException(("Deep link " + navDeepLink.uriPattern + " can't be used to open destination " + this + ".\nFollowing required arguments are missing: " + missingRequiredArguments).toString());
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
                    StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Wrong argument type for '", str2, "' in argument bundle. ");
                    m.append(navType.getName());
                    m.append(" expected.");
                    throw new IllegalArgumentException(m.toString().toString());
                }
            }
        }
        return bundle2;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean equals(java.lang.Object r9) {
        /*
            r8 = this;
            r0 = 1
            if (r8 != r9) goto L5
            goto Lc0
        L5:
            r1 = 0
            if (r9 == 0) goto Lc1
            boolean r2 = r9 instanceof androidx.navigation.NavDestination
            if (r2 != 0) goto Le
            goto Lc1
        Le:
            java.util.List r2 = r8.deepLinks
            androidx.navigation.NavDestination r9 = (androidx.navigation.NavDestination) r9
            java.util.List r3 = r9.deepLinks
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r3)
            androidx.collection.SparseArrayCompat r3 = r8.actions
            int r4 = r3.size()
            androidx.collection.SparseArrayCompat r5 = r9.actions
            int r6 = r5.size()
            if (r4 != r6) goto L54
            androidx.collection.SparseArrayKt$keyIterator$1 r4 = new androidx.collection.SparseArrayKt$keyIterator$1
            r4.<init>()
            kotlin.sequences.ConstrainedOnceSequence r4 = kotlin.sequences.SequencesKt__SequencesKt.asSequence(r4)
            java.util.Iterator r4 = r4.iterator()
        L33:
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto L52
            java.lang.Object r6 = r4.next()
            java.lang.Number r6 = (java.lang.Number) r6
            int r6 = r6.intValue()
            java.lang.Object r7 = r3.get(r6)
            java.lang.Object r6 = r5.get(r6)
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r6)
            if (r6 != 0) goto L33
            goto L54
        L52:
            r3 = r0
            goto L55
        L54:
            r3 = r1
        L55:
            java.util.Map r4 = r8._arguments
            int r4 = r4.size()
            java.util.Map r5 = r9._arguments
            int r5 = r5.size()
            if (r4 != r5) goto La9
            java.util.Map r4 = r8._arguments
            java.util.LinkedHashMap r4 = (java.util.LinkedHashMap) r4
            java.util.Set r4 = r4.entrySet()
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 r5 = new kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1
            r5.<init>(r4)
            java.lang.Iterable r4 = r5.$this_asSequence$inlined
            java.util.Iterator r4 = r4.iterator()
        L78:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto La7
            java.lang.Object r5 = r4.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            java.util.Map r6 = r9._arguments
            java.lang.Object r7 = r5.getKey()
            boolean r6 = r6.containsKey(r7)
            if (r6 == 0) goto La9
            java.util.Map r6 = r9._arguments
            java.lang.Object r7 = r5.getKey()
            java.util.LinkedHashMap r6 = (java.util.LinkedHashMap) r6
            java.lang.Object r6 = r6.get(r7)
            java.lang.Object r5 = r5.getValue()
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r5)
            if (r5 == 0) goto La9
            goto L78
        La7:
            r4 = r0
            goto Laa
        La9:
            r4 = r1
        Laa:
            int r5 = r8.id
            int r6 = r9.id
            if (r5 != r6) goto Lc1
            java.lang.String r8 = r8.route
            java.lang.String r9 = r9.route
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r8, r9)
            if (r8 == 0) goto Lc1
            if (r2 == 0) goto Lc1
            if (r3 == 0) goto Lc1
            if (r4 == 0) goto Lc1
        Lc0:
            return r0
        Lc1:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavDestination.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        Set<String> keySet;
        int i = this.id * 31;
        String str = this.route;
        int hashCode = i + (str != null ? str.hashCode() : 0);
        for (NavDeepLink navDeepLink : this.deepLinks) {
            int i2 = hashCode * 31;
            String str2 = navDeepLink.uriPattern;
            int hashCode2 = (i2 + (str2 != null ? str2.hashCode() : 0)) * 31;
            String str3 = navDeepLink.action;
            int hashCode3 = (hashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
            String str4 = navDeepLink.mimeType;
            hashCode = hashCode3 + (str4 != null ? str4.hashCode() : 0);
        }
        SparseArrayKt$valueIterator$1 sparseArrayKt$valueIterator$1 = new SparseArrayKt$valueIterator$1(this.actions);
        while (sparseArrayKt$valueIterator$1.hasNext()) {
            NavAction navAction = (NavAction) sparseArrayKt$valueIterator$1.next();
            int i3 = ((hashCode * 31) + navAction.destinationId) * 31;
            NavOptions navOptions = navAction.navOptions;
            hashCode = i3 + (navOptions != null ? navOptions.hashCode() : 0);
            Bundle bundle = navAction.defaultArguments;
            if (bundle != null && (keySet = bundle.keySet()) != null) {
                for (String str5 : keySet) {
                    int i4 = hashCode * 31;
                    Bundle bundle2 = navAction.defaultArguments;
                    bundle2.getClass();
                    Object obj = bundle2.get(str5);
                    hashCode = i4 + (obj != null ? obj.hashCode() : 0);
                }
            }
        }
        for (String str6 : ((LinkedHashMap) this._arguments).keySet()) {
            int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(hashCode * 31, 31, str6);
            Object obj2 = ((LinkedHashMap) this._arguments).get(str6);
            hashCode = m + (obj2 != null ? obj2.hashCode() : 0);
        }
        return hashCode;
    }

    public final DeepLinkMatch matchDeepLink(String str) {
        NavDeepLinkRequest.Builder.Companion companion = NavDeepLinkRequest.Builder.Companion;
        Companion.getClass();
        Uri parse = Uri.parse(str != null ? "android-app://androidx.navigation/".concat(str) : "");
        companion.getClass();
        new NavDeepLinkRequest.Builder(null).uri = parse;
        NavDeepLinkRequest navDeepLinkRequest = new NavDeepLinkRequest(parse, null, null);
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public NavDestination(androidx.navigation.Navigator r2) {
        /*
            r1 = this;
            androidx.navigation.NavigatorProvider$Companion r0 = androidx.navigation.NavigatorProvider.Companion
            java.lang.Class r2 = r2.getClass()
            r0.getClass()
            java.lang.String r2 = androidx.navigation.NavigatorProvider.Companion.getNameForNavigator$navigation_common_release(r2)
            r1.<init>(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavDestination.<init>(androidx.navigation.Navigator):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x01d1, code lost:
    
        if (((java.util.ArrayList) androidx.navigation.NavArgumentKt.missingRequiredArguments(r6, new androidx.navigation.NavDestination$hasRequiredArguments$missingRequiredArguments$1(r11))).isEmpty() != false) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0100, code lost:
    
        if (((java.util.ArrayList) androidx.navigation.NavArgumentKt.missingRequiredArguments(r3, new androidx.navigation.NavDeepLink$getMatchingArguments$missingRequiredArguments$1(r5))).isEmpty() == false) goto L51;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0128  */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public androidx.navigation.NavDestination.DeepLinkMatch matchDeepLink(androidx.navigation.NavDeepLinkRequest r19) {
        /*
            Method dump skipped, instructions count: 500
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavDestination.matchDeepLink(androidx.navigation.NavDeepLinkRequest):androidx.navigation.NavDestination$DeepLinkMatch");
    }
}
