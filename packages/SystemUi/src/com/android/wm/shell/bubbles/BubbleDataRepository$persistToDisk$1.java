package com.android.wm.shell.bubbles;

import android.util.Log;
import android.util.SparseArray;
import com.android.wm.shell.bubbles.storage.BubblePersistentRepository;
import com.android.wm.shell.bubbles.storage.BubbleVolatileRepository;
import com.android.wm.shell.bubbles.storage.BubbleXmlHelperKt;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.YieldKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.wm.shell.bubbles.BubbleDataRepository$persistToDisk$1", m277f = "BubbleDataRepository.kt", m278l = {136, 138}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class BubbleDataRepository$persistToDisk$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Job $prev;
    int label;
    final /* synthetic */ BubbleDataRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BubbleDataRepository$persistToDisk$1(Job job, BubbleDataRepository bubbleDataRepository, Continuation<? super BubbleDataRepository$persistToDisk$1> continuation) {
        super(2, continuation);
        this.$prev = job;
        this.this$0 = bubbleDataRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BubbleDataRepository$persistToDisk$1(this.$prev, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BubbleDataRepository$persistToDisk$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0047 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        BubbleVolatileRepository bubbleVolatileRepository;
        SparseArray sparseArray;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Job job = this.$prev;
            if (job != null) {
                this.label = 1;
                job.cancel(null);
                Object join = ((JobSupport) job).join(this);
                if (join != coroutineSingletons) {
                    join = Unit.INSTANCE;
                }
                if (join == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                BubbleDataRepository bubbleDataRepository = this.this$0;
                BubblePersistentRepository bubblePersistentRepository = bubbleDataRepository.persistentRepository;
                bubbleVolatileRepository = bubbleDataRepository.volatileRepository;
                synchronized (bubbleVolatileRepository) {
                    sparseArray = new SparseArray();
                    int size = bubbleVolatileRepository.entitiesByUser.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        sparseArray.put(bubbleVolatileRepository.entitiesByUser.keyAt(i2), CollectionsKt___CollectionsKt.toList((List) bubbleVolatileRepository.entitiesByUser.valueAt(i2)));
                    }
                }
                synchronized (bubblePersistentRepository.bubbleFile) {
                    try {
                        FileOutputStream startWrite = bubblePersistentRepository.bubbleFile.startWrite();
                        try {
                            BubbleXmlHelperKt.writeXml(startWrite, sparseArray);
                            bubblePersistentRepository.bubbleFile.finishWrite(startWrite);
                        } catch (Exception e) {
                            Log.e("BubblePersistentRepository", "Failed to save bubble file, restoring backup", e);
                            bubblePersistentRepository.bubbleFile.failWrite(startWrite);
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
        if (YieldKt.yield(this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        BubbleDataRepository bubbleDataRepository2 = this.this$0;
        BubblePersistentRepository bubblePersistentRepository2 = bubbleDataRepository2.persistentRepository;
        bubbleVolatileRepository = bubbleDataRepository2.volatileRepository;
        synchronized (bubbleVolatileRepository) {
        }
    }
}
