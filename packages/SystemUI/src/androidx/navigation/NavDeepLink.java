package androidx.navigation;

import android.net.Uri;
import android.os.Bundle;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.os.BundleKt;
import androidx.navigation.NavDeepLink;
import com.samsung.android.knox.accounts.DeviceAccountPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes.dex */
public final class NavDeepLink {
    public static final Pattern FILL_IN_PATTERN;
    public static final Pattern SCHEME_PATTERN;
    public final String action;
    public final Lazy fragArgs$delegate;
    public final Lazy fragArgsAndRegex$delegate;
    public final Lazy fragPattern$delegate;
    public final Lazy fragRegex$delegate;
    public final boolean isExactDeepLink;
    public final Lazy isParameterizedQuery$delegate;
    public boolean isSingleQueryParamValueOnly;
    public final String mimeType;
    public final Lazy mimeTypePattern$delegate;
    public final String mimeTypeRegex;
    public final List pathArgs;
    public final Lazy pathPattern$delegate;
    public final String pathRegex;
    public final Lazy queryArgsMap$delegate;
    public final String uriPattern;

    public final class Builder {
        public String uriPattern;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class MimeType implements Comparable {
        public final String subType;
        public final String type;

        public MimeType(String str) {
            List listTake;
            List listSplit = new Regex("/").split(str);
            if (listSplit.isEmpty()) {
                listTake = EmptyList.INSTANCE;
            } else {
                ListIterator listIterator = listSplit.listIterator(listSplit.size());
                while (listIterator.hasPrevious()) {
                    if (((String) listIterator.previous()).length() != 0) {
                        listTake = CollectionsKt___CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                        break;
                    }
                }
                listTake = EmptyList.INSTANCE;
            }
            this.type = (String) listTake.get(0);
            this.subType = (String) listTake.get(1);
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            MimeType mimeType = (MimeType) obj;
            int i = Intrinsics.areEqual(this.type, mimeType.type) ? 2 : 0;
            return Intrinsics.areEqual(this.subType, mimeType.subType) ? i + 1 : i;
        }
    }

    public final class ParamQuery {
        public final List arguments = new ArrayList();
        public String paramRegex;
    }

    static {
        new Companion(null);
        SCHEME_PATTERN = Pattern.compile("^[a-zA-Z]+[+\\w\\-.]*:");
        FILL_IN_PATTERN = Pattern.compile("\\{(.+?)\\}");
    }

    public NavDeepLink(String str, String str2, String str3) {
        this.uriPattern = str;
        this.action = str2;
        this.mimeType = str3;
        ArrayList arrayList = new ArrayList();
        this.pathArgs = arrayList;
        this.pathPattern$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.navigation.NavDeepLink$pathPattern$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String str4 = this.this$0.pathRegex;
                if (str4 != null) {
                    return Pattern.compile(str4, 2);
                }
                return null;
            }
        });
        this.isParameterizedQuery$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.navigation.NavDeepLink$isParameterizedQuery$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String str4 = this.this$0.uriPattern;
                return Boolean.valueOf((str4 == null || Uri.parse(str4).getQuery() == null) ? false : true);
            }
        });
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        this.queryArgsMap$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: androidx.navigation.NavDeepLink$queryArgsMap$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                NavDeepLink navDeepLink = this.this$0;
                Pattern pattern = NavDeepLink.SCHEME_PATTERN;
                navDeepLink.getClass();
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                if (((Boolean) navDeepLink.isParameterizedQuery$delegate.getValue()).booleanValue()) {
                    String str4 = navDeepLink.uriPattern;
                    Uri uri = Uri.parse(str4);
                    for (String str5 : uri.getQueryParameterNames()) {
                        StringBuilder sb = new StringBuilder();
                        List<String> queryParameters = uri.getQueryParameters(str5);
                        if (queryParameters.size() > 1) {
                            throw new IllegalArgumentException(MotionLayout$$ExternalSyntheticOutline0.m("Query parameter ", str5, " must only be present once in ", str4, ". To support repeated query parameters, use an array type for your argument and the pattern provided in your URI will be used to parse each query parameter instance.").toString());
                        }
                        String str6 = (String) CollectionsKt___CollectionsKt.firstOrNull((List) queryParameters);
                        if (str6 == null) {
                            navDeepLink.isSingleQueryParamValueOnly = true;
                            str6 = str5;
                        }
                        Matcher matcher = NavDeepLink.FILL_IN_PATTERN.matcher(str6);
                        NavDeepLink.ParamQuery paramQuery = new NavDeepLink.ParamQuery();
                        int iEnd = 0;
                        while (matcher.find()) {
                            ((ArrayList) paramQuery.arguments).add(matcher.group(1));
                            sb.append(Pattern.quote(str6.substring(iEnd, matcher.start())));
                            sb.append("(.+?)?");
                            iEnd = matcher.end();
                        }
                        if (iEnd < str6.length()) {
                            sb.append(Pattern.quote(str6.substring(iEnd)));
                        }
                        paramQuery.paramRegex = StringsKt__StringsJVMKt.replace$default(sb.toString(), DeviceAccountPolicy.ALL_ACCOUNTS, "\\E.*\\Q");
                        linkedHashMap.put(str5, paramQuery);
                    }
                }
                return linkedHashMap;
            }
        });
        this.fragArgsAndRegex$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: androidx.navigation.NavDeepLink$fragArgsAndRegex$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String str4 = this.this$0.uriPattern;
                if (str4 == null || Uri.parse(str4).getFragment() == null) {
                    return null;
                }
                ArrayList arrayList2 = new ArrayList();
                String fragment = Uri.parse(str4).getFragment();
                StringBuilder sb = new StringBuilder();
                fragment.getClass();
                NavDeepLink.buildRegex(fragment, arrayList2, sb);
                return new Pair(arrayList2, sb.toString());
            }
        });
        this.fragArgs$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: androidx.navigation.NavDeepLink$fragArgs$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List list;
                Pair pair = (Pair) this.this$0.fragArgsAndRegex$delegate.getValue();
                return (pair == null || (list = (List) pair.getFirst()) == null) ? new ArrayList() : list;
            }
        });
        this.fragRegex$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: androidx.navigation.NavDeepLink$fragRegex$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Pair pair = (Pair) this.this$0.fragArgsAndRegex$delegate.getValue();
                if (pair != null) {
                    return (String) pair.getSecond();
                }
                return null;
            }
        });
        this.fragPattern$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.navigation.NavDeepLink$fragPattern$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String str4 = (String) this.this$0.fragRegex$delegate.getValue();
                if (str4 != null) {
                    return Pattern.compile(str4, 2);
                }
                return null;
            }
        });
        this.mimeTypePattern$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.navigation.NavDeepLink$mimeTypePattern$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String str4 = this.this$0.mimeTypeRegex;
                if (str4 != null) {
                    return Pattern.compile(str4);
                }
                return null;
            }
        });
        if (str != null) {
            StringBuilder sb = new StringBuilder("^");
            if (!SCHEME_PATTERN.matcher(str).find()) {
                sb.append("http[s]?://");
            }
            Matcher matcher = Pattern.compile("(\\?|\\#|$)").matcher(str);
            matcher.find();
            boolean z = false;
            buildRegex(str.substring(0, matcher.start()), arrayList, sb);
            if (!StringsKt__StringsKt.contains(sb, DeviceAccountPolicy.ALL_ACCOUNTS, false) && !StringsKt__StringsKt.contains(sb, "([^/]+?)", false)) {
                z = true;
            }
            this.isExactDeepLink = z;
            sb.append("($|(\\?(.)*)|(\\#(.)*))");
            this.pathRegex = StringsKt__StringsJVMKt.replace$default(sb.toString(), DeviceAccountPolicy.ALL_ACCOUNTS, "\\E.*\\Q");
        }
        if (str3 == null) {
            return;
        }
        if (!Pattern.compile("^[\\s\\S]+/[\\s\\S]+$").matcher(str3).matches()) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("The given mimeType ", str3, " does not match to required \"type/subtype\" format").toString());
        }
        MimeType mimeType = new MimeType(str3);
        this.mimeTypeRegex = StringsKt__StringsJVMKt.replace$default("^(" + mimeType.type + "|[*]+)/(" + mimeType.subType + "|[*]+)$", "*|[*]", "[\\s\\S]");
    }

    public static void buildRegex(String str, List list, StringBuilder sb) {
        Matcher matcher = FILL_IN_PATTERN.matcher(str);
        int iEnd = 0;
        while (matcher.find()) {
            ((ArrayList) list).add(matcher.group(1));
            if (matcher.start() > iEnd) {
                sb.append(Pattern.quote(str.substring(iEnd, matcher.start())));
            }
            sb.append("([^/]+?)");
            iEnd = matcher.end();
        }
        if (iEnd < str.length()) {
            sb.append(Pattern.quote(str.substring(iEnd)));
        }
    }

    public final boolean equals(Object obj) {
        if (obj != null && (obj instanceof NavDeepLink)) {
            NavDeepLink navDeepLink = (NavDeepLink) obj;
            if (Intrinsics.areEqual(this.uriPattern, navDeepLink.uriPattern) && Intrinsics.areEqual(this.action, navDeepLink.action) && Intrinsics.areEqual(this.mimeType, navDeepLink.mimeType)) {
                return true;
            }
        }
        return false;
    }

    public final boolean getMatchingPathArguments(Matcher matcher, Bundle bundle, Map map) {
        List list = this.pathArgs;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        ArrayList arrayList2 = (ArrayList) list;
        int size = arrayList2.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList2.get(i2);
            i2++;
            int i3 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            String str = (String) obj;
            String strDecode = Uri.decode(matcher.group(i3));
            NavArgument navArgument = (NavArgument) ((LinkedHashMap) map).get(str);
            if (navArgument != null) {
                try {
                    NavType navType = navArgument.type;
                    navType.put(bundle, str, navType.parseValue(strDecode));
                } catch (IllegalArgumentException unused) {
                    return false;
                }
            } else {
                bundle.putString(str, strDecode);
            }
            arrayList.add(Unit.INSTANCE);
            i = i3;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean getMatchingQueryArguments(Uri uri, Bundle bundle, Map map) {
        Object objValueOf;
        boolean z;
        String query;
        for (Map.Entry entry : ((Map) this.queryArgsMap$delegate.getValue()).entrySet()) {
            String str = (String) entry.getKey();
            ParamQuery paramQuery = (ParamQuery) entry.getValue();
            List<String> queryParameters = uri.getQueryParameters(str);
            if (this.isSingleQueryParamValueOnly && (query = uri.getQuery()) != null && !query.equals(uri.toString())) {
                queryParameters = Collections.singletonList(query);
            }
            int i = 0;
            Bundle bundleBundleOf = BundleKt.bundleOf(new Pair[0]);
            ArrayList arrayList = (ArrayList) paramQuery.arguments;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                String str2 = (String) obj;
                NavArgument navArgument = (NavArgument) ((LinkedHashMap) map).get(str2);
                NavType navType = navArgument != null ? navArgument.type : null;
                if ((navType instanceof CollectionNavType) && !navArgument.isDefaultValuePresent) {
                    CollectionNavType collectionNavType = (CollectionNavType) navType;
                    collectionNavType.put(bundleBundleOf, str2, collectionNavType.emptyCollection());
                }
            }
            for (String str3 : queryParameters) {
                String str4 = paramQuery.paramRegex;
                Matcher matcher = str4 != null ? Pattern.compile(str4, 32).matcher(str3) : null;
                if (matcher == null || !matcher.matches()) {
                    return i;
                }
                List list = paramQuery.arguments;
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                ArrayList arrayList3 = (ArrayList) list;
                int size2 = arrayList3.size();
                int i3 = i;
                int i4 = i3;
                while (i4 < size2) {
                    Object obj2 = arrayList3.get(i4);
                    i4++;
                    int i5 = i3 + 1;
                    if (i3 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                    String str5 = (String) obj2;
                    String strGroup = matcher.group(i5);
                    if (strGroup == null) {
                        strGroup = "";
                    }
                    int i6 = i;
                    NavArgument navArgument2 = (NavArgument) ((LinkedHashMap) map).get(str5);
                    try {
                        if (bundleBundleOf.containsKey(str5)) {
                            if (bundleBundleOf.containsKey(str5)) {
                                if (navArgument2 != null) {
                                    NavType navType2 = navArgument2.type;
                                    Object obj3 = navType2.get(bundleBundleOf, str5);
                                    if (!bundleBundleOf.containsKey(str5)) {
                                        throw new IllegalArgumentException("There is no previous value in this bundle.");
                                    }
                                    navType2.put(bundleBundleOf, str5, navType2.parseValue(obj3, strGroup));
                                }
                                z = i6;
                            } else {
                                z = 1;
                            }
                            try {
                                objValueOf = Boolean.valueOf(z);
                            } catch (IllegalArgumentException unused) {
                                objValueOf = Unit.INSTANCE;
                                arrayList2.add(objValueOf);
                                i3 = i5;
                                i = i6;
                            }
                        } else {
                            if (navArgument2 != null) {
                                NavType navType3 = navArgument2.type;
                                navType3.put(bundleBundleOf, str5, navType3.parseValue(strGroup));
                            } else {
                                bundleBundleOf.putString(str5, strGroup);
                            }
                            objValueOf = Unit.INSTANCE;
                        }
                    } catch (IllegalArgumentException unused2) {
                    }
                    arrayList2.add(objValueOf);
                    i3 = i5;
                    i = i6;
                }
            }
            bundle.putAll(bundleBundleOf);
        }
        return true;
    }

    public final int hashCode() {
        String str = this.uriPattern;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.action;
        int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.mimeType;
        return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    public NavDeepLink(String str) {
        this(str, null, null);
    }
}
