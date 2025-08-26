package com.android.wm.shell.bubbles;

import android.content.LocusId;
import android.content.pm.LauncherApps;
import android.util.Log;
import android.util.SparseArray;
import com.android.wm.shell.bubbles.storage.BubbleEntity;
import com.android.wm.shell.bubbles.storage.BubblePersistentRepository;
import com.android.wm.shell.bubbles.storage.BubbleVolatileRepository;
import com.android.wm.shell.bubbles.storage.BubbleXmlHelperKt;
import com.android.wm.shell.common.ShellExecutor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.SupervisorJobImpl;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

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

    /* renamed from: com.android.wm.shell.bubbles.BubbleDataRepository$persistToDisk$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ SparseArray<List<BubbleEntity>> $entitiesByUser;
        final /* synthetic */ Job $prev;
        int label;
        final /* synthetic */ BubbleDataRepository this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Job job, BubbleDataRepository bubbleDataRepository, SparseArray<List<BubbleEntity>> sparseArray, Continuation continuation) {
            super(2, continuation);
            this.$prev = job;
            this.this$0 = bubbleDataRepository;
            this.$entitiesByUser = sparseArray;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$prev, this.this$0, this.$entitiesByUser, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x0032, code lost:
        
            if (kotlinx.coroutines.YieldKt.yield(r4) == r0) goto L17;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Job job = this.$prev;
                if (job != null) {
                    this.label = 1;
                    if (JobKt.cancelAndJoin(job, this) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    BubblePersistentRepository bubblePersistentRepository = this.this$0.persistentRepository;
                    SparseArray<List<BubbleEntity>> sparseArray = this.$entitiesByUser;
                    synchronized (bubblePersistentRepository.bubbleFile) {
                        try {
                            FileOutputStream fileOutputStreamStartWrite = bubblePersistentRepository.bubbleFile.startWrite();
                            fileOutputStreamStartWrite.getClass();
                            try {
                                BubbleXmlHelperKt.writeXml(fileOutputStreamStartWrite, sparseArray);
                                bubblePersistentRepository.bubbleFile.finishWrite(fileOutputStreamStartWrite);
                            } catch (Exception e) {
                                Log.e("BubblePersistentRepository", "Failed to save bubble file, restoring backup", e);
                                bubblePersistentRepository.bubbleFile.failWrite(fileOutputStreamStartWrite);
                                Unit unit = Unit.INSTANCE;
                            }
                        } catch (IOException e2) {
                            Log.e("BubblePersistentRepository", "Failed to save bubble file", e2);
                        }
                    }
                    return Unit.INSTANCE;
                }
                ResultKt.throwOnFailure(obj);
            }
            this.label = 2;
        }
    }

    public BubbleDataRepository(LauncherApps launcherApps, ShellExecutor shellExecutor, Executor executor, BubblePersistentRepository bubblePersistentRepository) {
        this.launcherApps = launcherApps;
        this.mainExecutor = shellExecutor;
        this.bgExecutor = executor;
        this.persistentRepository = bubblePersistentRepository;
        this.volatileRepository = new BubbleVolatileRepository(launcherApps);
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        DefaultIoScheduler defaultIoScheduler = DefaultIoScheduler.INSTANCE;
        SupervisorJobImpl supervisorJobImplSupervisorJob$default = SupervisorKt.SupervisorJob$default();
        defaultIoScheduler.getClass();
        this.coroutineScope = CoroutineScopeKt.CoroutineScope(CoroutineContext.DefaultImpls.plus(defaultIoScheduler, supervisorJobImplSupervisorJob$default));
    }

    public static void persistToDisk$default(BubbleDataRepository bubbleDataRepository) {
        SparseArray<List<BubbleEntity>> sparseArray;
        BubbleVolatileRepository bubbleVolatileRepository = bubbleDataRepository.volatileRepository;
        synchronized (bubbleVolatileRepository) {
            sparseArray = new SparseArray<>();
            int size = bubbleVolatileRepository.entitiesByUser.size();
            for (int i = 0; i < size; i++) {
                int iKeyAt = bubbleVolatileRepository.entitiesByUser.keyAt(i);
                List list = (List) bubbleVolatileRepository.entitiesByUser.valueAt(i);
                list.getClass();
                sparseArray.put(iKeyAt, CollectionsKt___CollectionsKt.toList(list));
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
                id = locusId != null ? locusId.getId() : null;
                id = new BubbleEntity(identifier, str, str2, bubble.mKey, i, i2, str3, taskId, id, bubble.mIsDismissable);
            }
            if (id != null) {
                arrayList.add(id);
            }
        }
        return arrayList;
    }

    public final SparseArray<List<BubbleEntity>> filterForActiveUsersAndPersist(List<Integer> list, SparseArray<List<BubbleEntity>> sparseArray) {
        SparseArray<List<BubbleEntity>> sparseArray2 = new SparseArray<>();
        int size = sparseArray.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            int iKeyAt = sparseArray.keyAt(i);
            if (list.contains(Integer.valueOf(iKeyAt))) {
                ArrayList arrayList = new ArrayList();
                for (BubbleEntity bubbleEntity : sparseArray.get(iKeyAt)) {
                    if (list.contains(Integer.valueOf(bubbleEntity.userId))) {
                        arrayList.add(bubbleEntity);
                    } else {
                        z = true;
                    }
                }
                if (!arrayList.isEmpty()) {
                    sparseArray2.put(iKeyAt, arrayList);
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
        this.job = BuildersKt.launch$default(this.coroutineScope, null, null, new AnonymousClass1(this.job, this, sparseArray, null), 3);
    }
}
