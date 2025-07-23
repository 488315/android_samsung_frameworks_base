package com.android.wm.shell.bubbles.storage;

import android.content.pm.LauncherApps;
import android.os.UserHandle;
import android.util.SparseArray;
import com.android.wm.shell.bubbles.ShortcutKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BubbleVolatileRepository {
    public final LauncherApps launcherApps;
    public final SparseArray entitiesByUser = new SparseArray();
    public final int capacity = 16;

    public BubbleVolatileRepository(LauncherApps launcherApps) {
        this.launcherApps = launcherApps;
    }

    public final synchronized void addBubbles(int i, List list) {
        try {
            if (list.isEmpty()) {
                return;
            }
            List entities = getEntities(i);
            List takeLast = CollectionsKt___CollectionsKt.takeLast(this.capacity, list);
            ArrayList arrayList = new ArrayList();
            for (Object obj : takeLast) {
                if (!entities.removeIf(new BubbleVolatileRepositoryKt$sam$java_util_function_Predicate$0(new BubbleVolatileRepository$$ExternalSyntheticLambda0((BubbleEntity) obj, 0)))) {
                    arrayList.add(obj);
                }
            }
            int size = (entities.size() + takeLast.size()) - this.capacity;
            if (size > 0) {
                uncache(CollectionsKt___CollectionsKt.take(entities, size));
                entities = new ArrayList(CollectionsKt___CollectionsKt.drop(entities, size));
            }
            entities.addAll(takeLast);
            this.entitiesByUser.put(i, entities);
            cache(arrayList);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void cache(List list) {
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
            LauncherApps launcherApps = this.launcherApps;
            String str = shortcutKey2.pkg;
            List list3 = list2;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
            Iterator it = list3.iterator();
            while (it.hasNext()) {
                arrayList.add(((BubbleEntity) it.next()).shortcutId);
            }
            launcherApps.cacheShortcuts(str, arrayList, UserHandle.of(shortcutKey2.userId), 1);
        }
    }

    public final synchronized List getEntities(int i) {
        List list;
        list = (List) this.entitiesByUser.get(i);
        if (list == null) {
            list = new ArrayList();
            this.entitiesByUser.put(i, list);
        }
        return list;
    }

    public final synchronized boolean removeBubblesForUserWithParent(final int i, int i2) {
        if (this.entitiesByUser.get(i2) == null) {
            return false;
        }
        return ((List) this.entitiesByUser.get(i2)).removeIf(new BubbleVolatileRepositoryKt$sam$java_util_function_Predicate$0(new Function1() { // from class: com.android.wm.shell.bubbles.storage.BubbleVolatileRepository$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return Boolean.valueOf(((BubbleEntity) obj).userId == i);
            }
        }));
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
            LauncherApps launcherApps = this.launcherApps;
            String str = shortcutKey2.pkg;
            List list3 = list2;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
            Iterator it = list3.iterator();
            while (it.hasNext()) {
                arrayList.add(((BubbleEntity) it.next()).shortcutId);
            }
            launcherApps.uncacheShortcuts(str, arrayList, UserHandle.of(shortcutKey2.userId), 1);
        }
    }

    public static /* synthetic */ void getCapacity$annotations() {
    }
}
