package com.android.wm.shell.bubbles;

import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.os.UserHandle;
import android.util.Log;
import android.util.SparseArray;
import com.android.wm.shell.bubbles.storage.BubbleEntity;
import com.android.wm.shell.bubbles.storage.BubblePersistentRepository;
import com.android.wm.shell.bubbles.storage.BubbleXmlHelperKt;
import com.android.wm.shell.common.ShellExecutor;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class BubbleDataRepository$loadBubbles$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $cb;
    final /* synthetic */ List<Integer> $currentUsers;
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ BubbleDataRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BubbleDataRepository$loadBubbles$1(BubbleDataRepository bubbleDataRepository, List<Integer> list, int i, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bubbleDataRepository;
        this.$currentUsers = list;
        this.$userId = i;
        this.$cb = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BubbleDataRepository$loadBubbles$1(this.this$0, this.$currentUsers, this.$userId, this.$cb, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BubbleDataRepository$loadBubbles$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x0187  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        SparseArray<List<BubbleEntity>> sparseArray;
        Iterator it;
        Bubble bubble;
        Object next;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BubblePersistentRepository bubblePersistentRepository = this.this$0.persistentRepository;
        synchronized (bubblePersistentRepository.bubbleFile) {
            obj2 = null;
            if (bubblePersistentRepository.bubbleFile.exists()) {
                try {
                    FileInputStream fileInputStreamOpenRead = bubblePersistentRepository.bubbleFile.openRead();
                    try {
                        sparseArray = BubbleXmlHelperKt.readXml(fileInputStreamOpenRead);
                        CloseableKt.closeFinally(fileInputStreamOpenRead, null);
                    } finally {
                    }
                } catch (Throwable th) {
                    Log.e("BubblePersistentRepository", "Failed to open bubble file", th);
                    sparseArray = new SparseArray<>();
                }
            } else {
                sparseArray = new SparseArray<>();
            }
        }
        List<BubbleEntity> list = this.this$0.filterForActiveUsersAndPersist(this.$currentUsers, sparseArray).get(this.$userId);
        if (list == null) {
            return Unit.INSTANCE;
        }
        this.this$0.volatileRepository.addBubbles(this.$userId, list);
        List<BubbleEntity> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (BubbleEntity bubbleEntity : list2) {
            arrayList.add(new ShortcutKey(bubbleEntity.userId, bubbleEntity.packageName));
        }
        Set<ShortcutKey> set = CollectionsKt___CollectionsKt.toSet(arrayList);
        BubbleDataRepository bubbleDataRepository = this.this$0;
        ArrayList arrayList2 = new ArrayList();
        for (ShortcutKey shortcutKey : set) {
            List<ShortcutInfo> shortcuts = bubbleDataRepository.launcherApps.getShortcuts(new LauncherApps.ShortcutQuery().setPackage(shortcutKey.pkg).setQueryFlags(1041), UserHandle.of(shortcutKey.userId));
            if (shortcuts == null) {
                shortcuts = EmptyList.INSTANCE;
            }
            CollectionsKt__MutableCollectionsKt.addAll(shortcuts, arrayList2);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj3 = arrayList2.get(i);
            i++;
            ShortcutInfo shortcutInfo = (ShortcutInfo) obj3;
            ShortcutKey shortcutKey2 = new ShortcutKey(shortcutInfo.getUserId(), shortcutInfo.getPackage());
            Object arrayList3 = linkedHashMap.get(shortcutKey2);
            if (arrayList3 == null) {
                arrayList3 = new ArrayList();
                linkedHashMap.put(shortcutKey2, arrayList3);
            }
            ((List) arrayList3).add(obj3);
        }
        BubbleDataRepository bubbleDataRepository2 = this.this$0;
        final ArrayList arrayList4 = new ArrayList();
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            BubbleEntity bubbleEntity2 = (BubbleEntity) it2.next();
            List list3 = (List) linkedHashMap.get(new ShortcutKey(bubbleEntity2.userId, bubbleEntity2.packageName));
            if (list3 != null) {
                Iterator it3 = list3.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        next = obj2;
                        break;
                    }
                    next = it3.next();
                    if (Intrinsics.areEqual(bubbleEntity2.shortcutId, ((ShortcutInfo) next).getId())) {
                        break;
                    }
                }
                ShortcutInfo shortcutInfo2 = (ShortcutInfo) next;
                if (shortcutInfo2 != null) {
                    String str = bubbleEntity2.key;
                    int i2 = bubbleEntity2.desiredHeight;
                    int i3 = bubbleEntity2.desiredHeightResId;
                    String str2 = bubbleEntity2.title;
                    int i4 = bubbleEntity2.taskId;
                    String str3 = bubbleEntity2.locus;
                    boolean z = bubbleEntity2.isDismissable;
                    ShellExecutor shellExecutor = bubbleDataRepository2.mainExecutor;
                    Executor executor = bubbleDataRepository2.bgExecutor;
                    it = it2;
                    BubbleController$$ExternalSyntheticLambda5 bubbleController$$ExternalSyntheticLambda5 = bubbleDataRepository2.bubbleMetadataFlagListener;
                    bubble = new Bubble(str, shortcutInfo2, i2, i3, str2, i4, str3, z, shellExecutor, executor, bubbleController$$ExternalSyntheticLambda5 == null ? null : bubbleController$$ExternalSyntheticLambda5);
                } else {
                    it = it2;
                    bubble = null;
                }
            }
            if (bubble != null) {
                arrayList4.add(bubble);
            }
            it2 = it;
            obj2 = null;
        }
        ShellExecutor shellExecutor2 = this.this$0.mainExecutor;
        final Function1 function1 = this.$cb;
        shellExecutor2.execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleDataRepository$loadBubbles$1.1
            @Override // java.lang.Runnable
            public final void run() {
                function1.mo781invoke(arrayList4);
            }
        });
        return Unit.INSTANCE;
    }
}
