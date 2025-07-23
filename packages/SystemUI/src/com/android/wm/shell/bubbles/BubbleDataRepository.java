package com.android.wm.shell.bubbles;

import android.content.LocusId;
import android.content.pm.LauncherApps;
import android.util.SparseArray;
import com.android.wm.shell.bubbles.storage.BubbleEntity;
import com.android.wm.shell.bubbles.storage.BubblePersistentRepository;
import com.android.wm.shell.bubbles.storage.BubbleVolatileRepository;
import com.android.wm.shell.common.ShellExecutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.SupervisorJobImpl;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BubbleDataRepository {
    public final Executor bgExecutor;
    public BubbleController$$ExternalSyntheticLambda5 bubbleMetadataFlagListener;
    public final ContextScope coroutineScope;
    public StandaloneCoroutine job;
    public final LauncherApps launcherApps;
    public final ShellExecutor mainExecutor;
    public final BubblePersistentRepository persistentRepository;
    public final BubbleVolatileRepository volatileRepository;

    public BubbleDataRepository(LauncherApps launcherApps, ShellExecutor shellExecutor, Executor executor, BubblePersistentRepository bubblePersistentRepository) {
        this.launcherApps = launcherApps;
        this.mainExecutor = shellExecutor;
        this.bgExecutor = executor;
        this.persistentRepository = bubblePersistentRepository;
        this.volatileRepository = new BubbleVolatileRepository(launcherApps);
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        DefaultIoScheduler defaultIoScheduler = DefaultIoScheduler.INSTANCE;
        SupervisorJobImpl SupervisorJob$default = SupervisorKt.SupervisorJob$default();
        defaultIoScheduler.getClass();
        this.coroutineScope = CoroutineScopeKt.CoroutineScope(CoroutineContext.DefaultImpls.plus(defaultIoScheduler, SupervisorJob$default));
    }

    public static void persistToDisk$default(BubbleDataRepository bubbleDataRepository) {
        SparseArray<List<BubbleEntity>> sparseArray;
        BubbleVolatileRepository bubbleVolatileRepository = bubbleDataRepository.volatileRepository;
        synchronized (bubbleVolatileRepository) {
            sparseArray = new SparseArray<>();
            int size = bubbleVolatileRepository.entitiesByUser.size();
            for (int i = 0; i < size; i++) {
                int keyAt = bubbleVolatileRepository.entitiesByUser.keyAt(i);
                List list = (List) bubbleVolatileRepository.entitiesByUser.valueAt(i);
                list.getClass();
                sparseArray.put(keyAt, CollectionsKt___CollectionsKt.toList(list));
            }
        }
        bubbleDataRepository.persistToDisk(sparseArray);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.wm.shell.bubbles.storage.BubbleEntity] */
    public static List transform(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Bubble bubble = (Bubble) it.next();
            int identifier = bubble.mUser.getIdentifier();
            String str = bubble.mPackageName;
            String str2 = bubble.mMetadataShortcutId;
            if (str2 != null) {
                int i = bubble.mDesiredHeight;
                int i2 = bubble.mDesiredHeightResId;
                String str3 = bubble.mTitle;
                int taskId = bubble.getTaskId();
                LocusId locusId = bubble.mLocusId;
                r2 = locusId != null ? locusId.getId() : null;
                r2 = new BubbleEntity(identifier, str, str2, bubble.mKey, i, i2, str3, taskId, r2, bubble.mIsDismissable);
            }
            if (r2 != null) {
                arrayList.add(r2);
            }
        }
        return arrayList;
    }

    public final SparseArray<List<BubbleEntity>> filterForActiveUsersAndPersist(List<Integer> list, SparseArray<List<BubbleEntity>> sparseArray) {
        SparseArray<List<BubbleEntity>> sparseArray2 = new SparseArray<>();
        int size = sparseArray.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            int keyAt = sparseArray.keyAt(i);
            if (list.contains(Integer.valueOf(keyAt))) {
                ArrayList arrayList = new ArrayList();
                for (BubbleEntity bubbleEntity : sparseArray.get(keyAt)) {
                    if (list.contains(Integer.valueOf(bubbleEntity.userId))) {
                        arrayList.add(bubbleEntity);
                    } else {
                        z = true;
                    }
                }
                if (!arrayList.isEmpty()) {
                    sparseArray2.put(keyAt, arrayList);
                }
            } else {
                z = true;
            }
        }
        if (!z) {
            return sparseArray;
        }
        persistToDisk(sparseArray2);
        return sparseArray2;
    }

    public final void persistToDisk(SparseArray<List<BubbleEntity>> sparseArray) {
        this.job = BuildersKt.launch$default(this.coroutineScope, null, null, new BubbleDataRepository$persistToDisk$1(this.job, this, sparseArray, null), 3);
    }
}
