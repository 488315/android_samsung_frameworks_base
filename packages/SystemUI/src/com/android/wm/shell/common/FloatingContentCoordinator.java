package com.android.wm.shell.common;

import android.graphics.Rect;
import android.util.Log;
import com.android.wm.shell.common.FloatingContentCoordinator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FloatingContentCoordinator {
    public static final Companion Companion = new Companion(null);
    public final Map allContentBounds = new HashMap();
    public boolean currentlyResolvingConflicts;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
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

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface FloatingContent {
        Rect getAllowedFloatingBoundsRegion();

        Rect getFloatingBoundsOnScreen();

        void moveToBounds(Rect rect);
    }

    /* JADX WARN: Type inference failed for: r4v13, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v3, types: [T, java.lang.Object] */
    public final void maybeMoveConflictingContent(FloatingContent floatingContent) {
        int i;
        boolean z = true;
        this.currentlyResolvingConflicts = true;
        Object obj = ((HashMap) this.allContentBounds).get(floatingContent);
        obj.getClass();
        final Rect rect = (Rect) obj;
        Map map = this.allContentBounds;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : ((HashMap) map).entrySet()) {
            FloatingContent floatingContent2 = (FloatingContent) entry.getKey();
            Rect rect2 = (Rect) entry.getValue();
            if (!Intrinsics.areEqual(floatingContent2, floatingContent) && Rect.intersects(rect, rect2)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            FloatingContent floatingContent3 = (FloatingContent) entry2.getKey();
            List minus = CollectionsKt___CollectionsKt.minus(CollectionsKt___CollectionsKt.minus(((HashMap) this.allContentBounds).values(), (Rect) entry2.getValue()), rect);
            floatingContent3.getClass();
            final Rect floatingBoundsOnScreen = floatingContent3.getFloatingBoundsOnScreen();
            final Rect allowedFloatingBoundsRegion = floatingContent3.getAllowedFloatingBoundsRegion();
            Companion.getClass();
            boolean z2 = rect.centerY() < floatingBoundsOnScreen.centerY() ? z : false;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) minus;
            int size = arrayList2.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj2 = arrayList2.get(i2);
                i2++;
                Rect rect3 = (Rect) obj2;
                int i3 = rect3.left;
                int i4 = floatingBoundsOnScreen.left;
                if ((i3 >= i4 && i3 <= floatingBoundsOnScreen.right) || ((i = rect3.right) <= floatingBoundsOnScreen.right && i >= i4)) {
                    arrayList.add(obj2);
                }
            }
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            int size2 = arrayList.size();
            int i5 = 0;
            while (i5 < size2) {
                Object obj3 = arrayList.get(i5);
                i5++;
                if (((Rect) obj3).top < floatingBoundsOnScreen.top) {
                    arrayList3.add(obj3);
                } else {
                    arrayList4.add(obj3);
                }
            }
            Pair pair = new Pair(arrayList3, arrayList4);
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = pair.component1();
            final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
            ref$ObjectRef2.element = pair.component2();
            final int i6 = 0;
            final Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.wm.shell.common.FloatingContentCoordinator$Companion$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i6) {
                        case 0:
                            Rect rect4 = floatingBoundsOnScreen;
                            Rect rect5 = rect;
                            FloatingContentCoordinator.Companion companion = FloatingContentCoordinator.Companion;
                            List plus = CollectionsKt___CollectionsKt.plus((Collection) ref$ObjectRef.element, rect5);
                            companion.getClass();
                            return FloatingContentCoordinator.Companion.findAreaForContentAboveOrBelow(rect4, plus, true);
                        default:
                            Rect rect6 = floatingBoundsOnScreen;
                            Rect rect7 = rect;
                            FloatingContentCoordinator.Companion companion2 = FloatingContentCoordinator.Companion;
                            List plus2 = CollectionsKt___CollectionsKt.plus((Collection) ref$ObjectRef.element, rect7);
                            companion2.getClass();
                            return FloatingContentCoordinator.Companion.findAreaForContentAboveOrBelow(rect6, plus2, false);
                    }
                }
            });
            final int i7 = 1;
            final Lazy lazy2 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.wm.shell.common.FloatingContentCoordinator$Companion$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i7) {
                        case 0:
                            Rect rect4 = floatingBoundsOnScreen;
                            Rect rect5 = rect;
                            FloatingContentCoordinator.Companion companion = FloatingContentCoordinator.Companion;
                            List plus = CollectionsKt___CollectionsKt.plus((Collection) ref$ObjectRef2.element, rect5);
                            companion.getClass();
                            return FloatingContentCoordinator.Companion.findAreaForContentAboveOrBelow(rect4, plus, true);
                        default:
                            Rect rect6 = floatingBoundsOnScreen;
                            Rect rect7 = rect;
                            FloatingContentCoordinator.Companion companion2 = FloatingContentCoordinator.Companion;
                            List plus2 = CollectionsKt___CollectionsKt.plus((Collection) ref$ObjectRef2.element, rect7);
                            companion2.getClass();
                            return FloatingContentCoordinator.Companion.findAreaForContentAboveOrBelow(rect6, plus2, false);
                    }
                }
            });
            final int i8 = 0;
            Rect rect4 = (!(z2 && ((Boolean) LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.wm.shell.common.FloatingContentCoordinator$Companion$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i7) {
                    }
                    return Boolean.valueOf(allowedFloatingBoundsRegion.contains((Rect) lazy2.getValue()));
                }
            }).getValue()).booleanValue()) && (z2 || ((Boolean) LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.wm.shell.common.FloatingContentCoordinator$Companion$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i8) {
                    }
                    return Boolean.valueOf(allowedFloatingBoundsRegion.contains((Rect) lazy.getValue()));
                }
            }).getValue()).booleanValue())) ? (Rect) lazy.getValue() : (Rect) lazy2.getValue();
            if (!allowedFloatingBoundsRegion.contains(rect4)) {
                rect4 = new Rect();
            }
            if (!rect4.isEmpty()) {
                floatingContent3.moveToBounds(rect4);
                ((HashMap) this.allContentBounds).put(floatingContent3, floatingContent3.getFloatingBoundsOnScreen());
            }
            z = true;
        }
        this.currentlyResolvingConflicts = false;
    }

    public final void onContentAdded(FloatingContent floatingContent) {
        updateContentBounds();
        ((HashMap) this.allContentBounds).put(floatingContent, floatingContent.getFloatingBoundsOnScreen());
        maybeMoveConflictingContent(floatingContent);
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
        for (FloatingContent floatingContent : ((HashMap) this.allContentBounds).keySet()) {
            ((HashMap) this.allContentBounds).put(floatingContent, floatingContent.getFloatingBoundsOnScreen());
        }
    }
}
