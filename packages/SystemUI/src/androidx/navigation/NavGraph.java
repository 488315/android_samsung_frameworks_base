package androidx.navigation;

import androidx.collection.SparseArrayCompat;
import androidx.collection.SparseArrayKt$valueIterator$1;
import androidx.navigation.NavDestination;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.serialization.KSerializer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class NavGraph extends NavDestination implements Iterable, KMappedMarker {
    public static final Companion Companion = new Companion(null);
    public final SparseArrayCompat nodes;
    public int startDestId;
    public String startDestIdName;
    public String startDestinationRoute;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public NavGraph(Navigator navigator) {
        super(navigator);
        this.nodes = new SparseArrayCompat();
    }

    @Override // androidx.navigation.NavDestination
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof NavGraph) || !super.equals(obj)) {
            return false;
        }
        NavGraph navGraph = (NavGraph) obj;
        if (this.nodes.size() != navGraph.nodes.size() || this.startDestId != navGraph.startDestId) {
            return false;
        }
        Iterator it = SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.nodes)).iterator();
        while (it.hasNext()) {
            NavDestination navDestination = (NavDestination) it.next();
            if (!navDestination.equals(navGraph.nodes.get(navDestination.id))) {
                return false;
            }
        }
        return true;
    }

    public final NavDestination findNode(String str, boolean z) {
        Object obj;
        NavGraph navGraph;
        Iterator it = SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.nodes)).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            NavDestination navDestination = (NavDestination) obj;
            if (StringsKt__StringsJVMKt.equals(navDestination.route, str, false) || navDestination.matchDeepLink(str) != null) {
                break;
            }
        }
        NavDestination navDestination2 = (NavDestination) obj;
        if (navDestination2 != null) {
            return navDestination2;
        }
        if (!z || (navGraph = this.parent) == null || str == null || StringsKt__StringsKt.isBlank(str)) {
            return null;
        }
        return navGraph.findNode(str, true);
    }

    public final NavDestination findNodeComprehensive(int i, NavGraph navGraph, boolean z) {
        NavDestination navDestination = (NavDestination) this.nodes.get(i);
        if (navDestination != null) {
            return navDestination;
        }
        if (z) {
            Iterator it = SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.nodes)).iterator();
            while (true) {
                if (!it.hasNext()) {
                    navDestination = null;
                    break;
                }
                NavDestination navDestination2 = (NavDestination) it.next();
                NavDestination findNodeComprehensive = (!(navDestination2 instanceof NavGraph) || Intrinsics.areEqual(navDestination2, navGraph)) ? null : ((NavGraph) navDestination2).findNodeComprehensive(i, this, true);
                if (findNodeComprehensive != null) {
                    navDestination = findNodeComprehensive;
                    break;
                }
            }
        }
        if (navDestination != null) {
            return navDestination;
        }
        NavGraph navGraph2 = this.parent;
        if (navGraph2 == null || navGraph2.equals(navGraph)) {
            return null;
        }
        NavGraph navGraph3 = this.parent;
        navGraph3.getClass();
        return navGraph3.findNodeComprehensive(i, this, z);
    }

    @Override // androidx.navigation.NavDestination
    public final int hashCode() {
        int i = this.startDestId;
        SparseArrayCompat sparseArrayCompat = this.nodes;
        int size = sparseArrayCompat.size();
        for (int i2 = 0; i2 < size; i2++) {
            i = (((i * 31) + sparseArrayCompat.keyAt(i2)) * 31) + ((NavDestination) sparseArrayCompat.valueAt(i2)).hashCode();
        }
        return i;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new NavGraph$iterator$1(this);
    }

    @Override // androidx.navigation.NavDestination
    public final NavDestination.DeepLinkMatch matchDeepLink(NavDeepLinkRequest navDeepLinkRequest) {
        NavDestination.DeepLinkMatch matchDeepLink = super.matchDeepLink(navDeepLinkRequest);
        ArrayList arrayList = new ArrayList();
        Iterator it = iterator();
        while (it.hasNext()) {
            NavDestination.DeepLinkMatch matchDeepLink2 = ((NavDestination) it.next()).matchDeepLink(navDeepLinkRequest);
            if (matchDeepLink2 != null) {
                arrayList.add(matchDeepLink2);
            }
        }
        return (NavDestination.DeepLinkMatch) CollectionsKt___CollectionsKt.maxOrNull((Iterable) ArraysKt___ArraysKt.filterNotNull(new NavDestination.DeepLinkMatch[]{matchDeepLink, (NavDestination.DeepLinkMatch) CollectionsKt___CollectionsKt.maxOrNull((Iterable) arrayList)}));
    }

    public final NavDestination.DeepLinkMatch matchDeepLinkExcludingChildren(NavDeepLinkRequest navDeepLinkRequest) {
        return super.matchDeepLink(navDeepLinkRequest);
    }

    public final void setStartDestination(KSerializer kSerializer, Function1 function1) {
        int hashCode = kSerializer.hashCode();
        NavDestination findNodeComprehensive = findNodeComprehensive(hashCode, this, false);
        if (findNodeComprehensive != null) {
            setStartDestinationRoute((String) function1.mo779invoke(findNodeComprehensive));
            this.startDestId = hashCode;
        } else {
            throw new IllegalStateException(("Cannot find startDestination " + kSerializer.getDescriptor().getSerialName() + " from NavGraph. Ensure the starting NavDestination was added with route from KClass.").toString());
        }
    }

    public final void setStartDestinationRoute(String str) {
        int hashCode;
        if (str == null) {
            hashCode = 0;
        } else {
            if (str.equals(this.route)) {
                throw new IllegalArgumentException(("Start destination " + str + " cannot use the same route as the graph " + this).toString());
            }
            if (StringsKt__StringsKt.isBlank(str)) {
                throw new IllegalArgumentException("Cannot have an empty start destination route");
            }
            NavDestination.Companion.getClass();
            hashCode = "android-app://androidx.navigation/".concat(str).hashCode();
        }
        this.startDestId = hashCode;
        this.startDestinationRoute = str;
    }

    @Override // androidx.navigation.NavDestination
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        String str = this.startDestinationRoute;
        NavDestination findNode = (str == null || StringsKt__StringsKt.isBlank(str)) ? null : findNode(str, true);
        if (findNode == null) {
            findNode = findNodeComprehensive(this.startDestId, this, false);
        }
        sb.append(" startDestination=");
        if (findNode == null) {
            String str2 = this.startDestinationRoute;
            if (str2 != null) {
                sb.append(str2);
            } else {
                String str3 = this.startDestIdName;
                if (str3 != null) {
                    sb.append(str3);
                } else {
                    sb.append("0x" + Integer.toHexString(this.startDestId));
                }
            }
        } else {
            sb.append("{");
            sb.append(findNode.toString());
            sb.append("}");
        }
        return sb.toString();
    }
}
