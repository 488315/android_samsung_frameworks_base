package com.android.p038wm.shell.bubbles.storage;

import android.content.pm.LauncherApps;
import android.os.UserHandle;
import android.util.SparseArray;
import com.android.p038wm.shell.bubbles.ShortcutKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubbleVolatileRepository {
    public final LauncherApps launcherApps;
    public final SparseArray entitiesByUser = new SparseArray();
    public final int capacity = 16;

    public BubbleVolatileRepository(LauncherApps launcherApps) {
        this.launcherApps = launcherApps;
    }

    public final synchronized void addBubbles(int i, List list) {
        if (list.isEmpty()) {
            return;
        }
        synchronized (this) {
            List list2 = (List) this.entitiesByUser.get(i);
            if (list2 == null) {
                list2 = new ArrayList();
                this.entitiesByUser.put(i, list2);
            }
            List takeLast = CollectionsKt___CollectionsKt.takeLast(this.capacity, list);
            ArrayList arrayList = new ArrayList();
            for (Object obj : takeLast) {
                final BubbleEntity bubbleEntity = (BubbleEntity) obj;
                if (!list2.removeIf(new Predicate() { // from class: com.android.wm.shell.bubbles.storage.BubbleVolatileRepository$addBubbles$uniqueBubbles$1$1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        return Intrinsics.areEqual(BubbleEntity.this.key, ((BubbleEntity) obj2).key);
                    }
                })) {
                    arrayList.add(obj);
                }
            }
            int size = (list2.size() + takeLast.size()) - this.capacity;
            if (size > 0) {
                uncache(CollectionsKt___CollectionsKt.take(list2, size));
                list2 = new ArrayList(CollectionsKt___CollectionsKt.drop(list2, size));
            }
            list2.addAll(takeLast);
            this.entitiesByUser.put(i, list2);
            cache(arrayList);
        }
    }

    public final void cache(List list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            Object next = it.next();
            BubbleEntity bubbleEntity = (BubbleEntity) next;
            ShortcutKey shortcutKey = new ShortcutKey(bubbleEntity.userId, bubbleEntity.packageName);
            Object obj = linkedHashMap.get(shortcutKey);
            if (obj == null) {
                obj = new ArrayList();
                linkedHashMap.put(shortcutKey, obj);
            }
            ((List) obj).add(next);
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            ShortcutKey shortcutKey2 = (ShortcutKey) entry.getKey();
            List list2 = (List) entry.getValue();
            String str = shortcutKey2.pkg;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            Iterator it2 = list2.iterator();
            while (it2.hasNext()) {
                arrayList.add(((BubbleEntity) it2.next()).shortcutId);
            }
            this.launcherApps.cacheShortcuts(str, arrayList, UserHandle.of(shortcutKey2.userId), 1);
        }
    }

    public final void uncache(List list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : list) {
            BubbleEntity bubbleEntity = (BubbleEntity) obj;
            ShortcutKey shortcutKey = new ShortcutKey(bubbleEntity.userId, bubbleEntity.packageName);
            Object obj2 = linkedHashMap.get(shortcutKey);
            if (obj2 == null) {
                obj2 = new ArrayList();
                linkedHashMap.put(shortcutKey, obj2);
            }
            ((List) obj2).add(obj);
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            ShortcutKey shortcutKey2 = (ShortcutKey) entry.getKey();
            List list2 = (List) entry.getValue();
            String str = shortcutKey2.pkg;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(((BubbleEntity) it.next()).shortcutId);
            }
            this.launcherApps.uncacheShortcuts(str, arrayList, UserHandle.of(shortcutKey2.userId), 1);
        }
    }

    public static /* synthetic */ void getCapacity$annotations() {
    }
}
