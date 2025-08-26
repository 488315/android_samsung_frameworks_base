package androidx.navigation;

import androidx.collection.SparseArrayCompat;
import androidx.collection.SparseArrayCompatKt;
import androidx.collection.SparseArrayKt$valueIterator$1;
import androidx.navigation.NavDestination;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
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

/* loaded from: classes.dex */
public class NavGraph extends NavDestination implements Iterable, KMappedMarker {
    public static final Companion Companion = new Companion(null);
    public final SparseArrayCompat nodes;
    public int startDestId;
    public String startDestIdName;
    public String startDestinationRoute;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: androidx.navigation.NavGraph$iterator$1, reason: invalid class name */
    public final class AnonymousClass1 implements Iterator, KMappedMarker {
        public int index = -1;
        public boolean wentToNext;

        public AnonymousClass1() {
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.index + 1 < NavGraph.this.nodes.size();
        }

        @Override // java.util.Iterator
        public final Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.wentToNext = true;
            SparseArrayCompat sparseArrayCompat = NavGraph.this.nodes;
            int i = this.index + 1;
            this.index = i;
            return (NavDestination) sparseArrayCompat.valueAt(i);
        }

        @Override // java.util.Iterator
        public final void remove() {
            if (!this.wentToNext) {
                throw new IllegalStateException("You must call next() before you can remove an element");
            }
            SparseArrayCompat sparseArrayCompat = NavGraph.this.nodes;
            ((NavDestination) sparseArrayCompat.valueAt(this.index)).parent = null;
            int i = this.index;
            Object[] objArr = sparseArrayCompat.values;
            Object obj = objArr[i];
            Object obj2 = SparseArrayCompatKt.DELETED;
            if (obj != obj2) {
                objArr[i] = obj2;
                sparseArrayCompat.garbage = true;
            }
            this.index = i - 1;
            this.wentToNext = false;
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
        Object next;
        NavGraph navGraph;
        Iterator it = SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.nodes)).iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            NavDestination navDestination = (NavDestination) next;
            if (StringsKt__StringsJVMKt.equals(navDestination.route, str, false) || navDestination.matchDeepLink(str) != null) {
                break;
            }
        }
        NavDestination navDestination2 = (NavDestination) next;
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
                NavDestination navDestinationFindNodeComprehensive = (!(navDestination2 instanceof NavGraph) || Intrinsics.areEqual(navDestination2, navGraph)) ? null : ((NavGraph) navDestination2).findNodeComprehensive(i, this, true);
                if (navDestinationFindNodeComprehensive != null) {
                    navDestination = navDestinationFindNodeComprehensive;
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
        int iKeyAt = this.startDestId;
        SparseArrayCompat sparseArrayCompat = this.nodes;
        int size = sparseArrayCompat.size();
        for (int i = 0; i < size; i++) {
            iKeyAt = (((iKeyAt * 31) + sparseArrayCompat.keyAt(i)) * 31) + ((NavDestination) sparseArrayCompat.valueAt(i)).hashCode();
        }
        return iKeyAt;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new AnonymousClass1();
    }

    @Override // androidx.navigation.NavDestination
    public final NavDestination.DeepLinkMatch matchDeepLink(NavDeepLinkRequest navDeepLinkRequest) {
        NavDestination.DeepLinkMatch deepLinkMatchMatchDeepLink = super.matchDeepLink(navDeepLinkRequest);
        ArrayList arrayList = new ArrayList();
        Iterator it = iterator();
        while (it.hasNext()) {
            NavDestination.DeepLinkMatch deepLinkMatchMatchDeepLink2 = ((NavDestination) it.next()).matchDeepLink(navDeepLinkRequest);
            if (deepLinkMatchMatchDeepLink2 != null) {
                arrayList.add(deepLinkMatchMatchDeepLink2);
            }
        }
        return (NavDestination.DeepLinkMatch) CollectionsKt___CollectionsKt.maxOrNull((Iterable) ArraysKt___ArraysKt.filterNotNull(new NavDestination.DeepLinkMatch[]{deepLinkMatchMatchDeepLink, (NavDestination.DeepLinkMatch) CollectionsKt___CollectionsKt.maxOrNull((Iterable) arrayList)}));
    }

    public final NavDestination.DeepLinkMatch matchDeepLinkExcludingChildren(NavDeepLinkRequest navDeepLinkRequest) {
        return super.matchDeepLink(navDeepLinkRequest);
    }

    public final void setStartDestination(KSerializer kSerializer, Function1 function1) {
        int iHashCode = kSerializer.hashCode();
        NavDestination navDestinationFindNodeComprehensive = findNodeComprehensive(iHashCode, this, false);
        if (navDestinationFindNodeComprehensive != null) {
            setStartDestinationRoute((String) function1.mo781invoke(navDestinationFindNodeComprehensive));
            this.startDestId = iHashCode;
        } else {
            throw new IllegalStateException(("Cannot find startDestination " + kSerializer.getDescriptor().getSerialName() + " from NavGraph. Ensure the starting NavDestination was added with route from KClass.").toString());
        }
    }

    public final void setStartDestinationRoute(String str) {
        int iHashCode;
        if (str == null) {
            iHashCode = 0;
        } else {
            if (str.equals(this.route)) {
                throw new IllegalArgumentException(("Start destination " + str + " cannot use the same route as the graph " + this).toString());
            }
            if (StringsKt__StringsKt.isBlank(str)) {
                throw new IllegalArgumentException("Cannot have an empty start destination route");
            }
            NavDestination.Companion.getClass();
            iHashCode = "android-app://androidx.navigation/".concat(str).hashCode();
        }
        this.startDestId = iHashCode;
        this.startDestinationRoute = str;
    }

    @Override // androidx.navigation.NavDestination
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        String str = this.startDestinationRoute;
        NavDestination navDestinationFindNode = (str == null || StringsKt__StringsKt.isBlank(str)) ? null : findNode(str, true);
        if (navDestinationFindNode == null) {
            navDestinationFindNode = findNodeComprehensive(this.startDestId, this, false);
        }
        sb.append(" startDestination=");
        if (navDestinationFindNode == null) {
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
            sb.append(navDestinationFindNode.toString());
            sb.append("}");
        }
        return sb.toString();
    }
}
