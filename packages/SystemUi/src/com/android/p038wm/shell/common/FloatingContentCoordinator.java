package com.android.p038wm.shell.common;

import android.graphics.Rect;
import android.util.Log;
import com.android.p038wm.shell.common.FloatingContentCoordinator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FloatingContentCoordinator {
    public static final Companion Companion = new Companion(null);
    public final Map allContentBounds = new HashMap();
    public boolean currentlyResolvingConflicts;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static Rect findAreaForContentAboveOrBelow(Rect rect, Collection collection, final boolean z) {
            List<Rect> sortedWith = CollectionsKt___CollectionsKt.sortedWith(collection, new Comparator() { // from class: com.android.wm.shell.common.FloatingContentCoordinator$Companion$findAreaForContentAboveOrBelow$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    boolean z2 = z;
                    int i = ((Rect) obj).top;
                    if (z2) {
                        i = -i;
                    }
                    Rect rect2 = (Rect) obj2;
                    return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(i), Integer.valueOf(z ? -rect2.top : rect2.top));
                }
            });
            Rect rect2 = new Rect(rect);
            for (Rect rect3 : sortedWith) {
                if (!Rect.intersects(rect2, rect3)) {
                    break;
                }
                rect2.offsetTo(rect2.left, rect3.top + (z ? -rect.height() : rect3.height()));
            }
            return rect2;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface FloatingContent {
        Rect getAllowedFloatingBoundsRegion();

        Rect getFloatingBoundsOnScreen();

        void moveToBounds(Rect rect);
    }

    /* JADX WARN: Type inference failed for: r5v11, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v2, types: [T, java.lang.Object] */
    public final void maybeMoveConflictingContent(FloatingContent floatingContent) {
        int i;
        boolean z = true;
        this.currentlyResolvingConflicts = true;
        Map map = this.allContentBounds;
        Object obj = ((HashMap) map).get(floatingContent);
        Intrinsics.checkNotNull(obj);
        final Rect rect = (Rect) obj;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : ((HashMap) map).entrySet()) {
            if (!Intrinsics.areEqual((FloatingContent) entry.getKey(), floatingContent) && Rect.intersects(rect, (Rect) entry.getValue())) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            FloatingContent floatingContent2 = (FloatingContent) entry2.getKey();
            List minus = CollectionsKt___CollectionsKt.minus(CollectionsKt___CollectionsKt.minus(((HashMap) map).values(), (Rect) entry2.getValue()), rect);
            floatingContent2.getClass();
            final Rect floatingBoundsOnScreen = floatingContent2.getFloatingBoundsOnScreen();
            final Rect allowedFloatingBoundsRegion = floatingContent2.getAllowedFloatingBoundsRegion();
            Companion companion = Companion;
            companion.getClass();
            boolean z2 = rect.centerY() < floatingBoundsOnScreen.centerY() ? z : false;
            ArrayList arrayList = new ArrayList();
            Iterator it = ((ArrayList) minus).iterator();
            while (it.hasNext()) {
                Object next = it.next();
                Rect rect2 = (Rect) next;
                companion.getClass();
                int i2 = rect2.left;
                int i3 = floatingBoundsOnScreen.left;
                if ((i2 >= i3 && i2 <= floatingBoundsOnScreen.right) || ((i = rect2.right) <= floatingBoundsOnScreen.right && i >= i3)) {
                    arrayList.add(next);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Object next2 = it2.next();
                if (((Rect) next2).top < floatingBoundsOnScreen.top) {
                    arrayList2.add(next2);
                } else {
                    arrayList3.add(next2);
                }
            }
            Pair pair = new Pair(arrayList2, arrayList3);
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = pair.component1();
            final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
            ref$ObjectRef2.element = pair.component2();
            final Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.wm.shell.common.FloatingContentCoordinator$Companion$findAreaForContentVertically$newContentBoundsAbove$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    FloatingContentCoordinator.Companion companion2 = FloatingContentCoordinator.Companion;
                    Rect rect3 = floatingBoundsOnScreen;
                    List plus = CollectionsKt___CollectionsKt.plus(ref$ObjectRef.element, rect);
                    companion2.getClass();
                    return FloatingContentCoordinator.Companion.findAreaForContentAboveOrBelow(rect3, plus, true);
                }
            });
            final Lazy lazy2 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.wm.shell.common.FloatingContentCoordinator$Companion$findAreaForContentVertically$newContentBoundsBelow$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    FloatingContentCoordinator.Companion companion2 = FloatingContentCoordinator.Companion;
                    Rect rect3 = floatingBoundsOnScreen;
                    List plus = CollectionsKt___CollectionsKt.plus(ref$ObjectRef2.element, rect);
                    companion2.getClass();
                    return FloatingContentCoordinator.Companion.findAreaForContentAboveOrBelow(rect3, plus, false);
                }
            });
            Rect rect3 = (z2 && ((Boolean) LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.wm.shell.common.FloatingContentCoordinator$Companion$findAreaForContentVertically$positionBelowInBounds$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Boolean.valueOf(allowedFloatingBoundsRegion.contains((Rect) lazy2.getValue()));
                }
            }).getValue()).booleanValue()) || (!z2 && !((Boolean) LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.wm.shell.common.FloatingContentCoordinator$Companion$findAreaForContentVertically$positionAboveInBounds$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Boolean.valueOf(allowedFloatingBoundsRegion.contains((Rect) lazy.getValue()));
                }
            }).getValue()).booleanValue()) ? (Rect) lazy2.getValue() : (Rect) lazy.getValue();
            if (!allowedFloatingBoundsRegion.contains(rect3)) {
                rect3 = new Rect();
            }
            if (!rect3.isEmpty()) {
                floatingContent2.moveToBounds(rect3);
                ((HashMap) map).put(floatingContent2, floatingContent2.getFloatingBoundsOnScreen());
            }
            z = true;
        }
        this.currentlyResolvingConflicts = false;
    }

    public final void onContentMoved(FloatingContent floatingContent) {
        if (this.currentlyResolvingConflicts) {
            return;
        }
        if (!((HashMap) this.allContentBounds).containsKey(floatingContent)) {
            Log.wtf("FloatingCoordinator", "Received onContentMoved call before onContentAdded! This should never happen.");
        } else {
            updateContentBounds();
            maybeMoveConflictingContent(floatingContent);
        }
    }

    public final void updateContentBounds() {
        Map map = this.allContentBounds;
        for (FloatingContent floatingContent : ((HashMap) map).keySet()) {
            ((HashMap) map).put(floatingContent, floatingContent.getFloatingBoundsOnScreen());
        }
    }
}
