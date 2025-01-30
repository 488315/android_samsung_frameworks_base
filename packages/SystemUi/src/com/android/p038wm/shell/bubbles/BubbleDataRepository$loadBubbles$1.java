package com.android.p038wm.shell.bubbles;

import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.os.UserHandle;
import android.util.Log;
import android.util.SparseArray;
import com.android.p038wm.shell.bubbles.Bubbles;
import com.android.p038wm.shell.bubbles.storage.BubbleEntity;
import com.android.p038wm.shell.bubbles.storage.BubblePersistentRepository;
import com.android.p038wm.shell.bubbles.storage.BubbleXmlHelperKt;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p055io.CloseableKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.wm.shell.bubbles.BubbleDataRepository$loadBubbles$1", m277f = "BubbleDataRepository.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class BubbleDataRepository$loadBubbles$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $cb;
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ BubbleDataRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BubbleDataRepository$loadBubbles$1(BubbleDataRepository bubbleDataRepository, int i, Function1 function1, Continuation<? super BubbleDataRepository$loadBubbles$1> continuation) {
        super(2, continuation);
        this.this$0 = bubbleDataRepository;
        this.$userId = i;
        this.$cb = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BubbleDataRepository$loadBubbles$1(this.this$0, this.$userId, this.$cb, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BubbleDataRepository$loadBubbles$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0177 A[SYNTHETIC] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        SparseArray sparseArray;
        Bubble bubble;
        Object obj3;
        Bubbles.BubbleMetadataFlagListener bubbleMetadataFlagListener;
        ShellExecutor shellExecutor;
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
                    FileInputStream openRead = bubblePersistentRepository.bubbleFile.openRead();
                    try {
                        sparseArray = BubbleXmlHelperKt.readXml(openRead);
                        CloseableKt.closeFinally(openRead, null);
                    } finally {
                    }
                } catch (Throwable th) {
                    Log.e("BubblePersistentRepository", "Failed to open bubble file", th);
                    sparseArray = new SparseArray();
                }
            } else {
                sparseArray = new SparseArray();
            }
        }
        List<BubbleEntity> list = (List) sparseArray.get(this.$userId);
        if (list == null) {
            return Unit.INSTANCE;
        }
        this.this$0.volatileRepository.addBubbles(this.$userId, list);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (BubbleEntity bubbleEntity : list) {
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
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            ShortcutInfo shortcutInfo = (ShortcutInfo) next;
            ShortcutKey shortcutKey2 = new ShortcutKey(shortcutInfo.getUserId(), shortcutInfo.getPackage());
            Object obj4 = linkedHashMap.get(shortcutKey2);
            if (obj4 == null) {
                obj4 = new ArrayList();
                linkedHashMap.put(shortcutKey2, obj4);
            }
            ((List) obj4).add(next);
        }
        BubbleDataRepository bubbleDataRepository2 = this.this$0;
        final ArrayList arrayList3 = new ArrayList();
        for (BubbleEntity bubbleEntity2 : list) {
            List list2 = (List) linkedHashMap.get(new ShortcutKey(bubbleEntity2.userId, bubbleEntity2.packageName));
            if (list2 != null) {
                Iterator it2 = list2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        obj3 = obj2;
                        break;
                    }
                    obj3 = it2.next();
                    if (Intrinsics.areEqual(bubbleEntity2.shortcutId, ((ShortcutInfo) obj3).getId())) {
                        break;
                    }
                }
                ShortcutInfo shortcutInfo2 = (ShortcutInfo) obj3;
                if (shortcutInfo2 != null) {
                    String str = bubbleEntity2.key;
                    int i = bubbleEntity2.desiredHeight;
                    int i2 = bubbleEntity2.desiredHeightResId;
                    String str2 = bubbleEntity2.title;
                    int i3 = bubbleEntity2.taskId;
                    String str3 = bubbleEntity2.locus;
                    boolean z = bubbleEntity2.isDismissable;
                    ShellExecutor shellExecutor2 = bubbleDataRepository2.mainExecutor;
                    Bubbles.BubbleMetadataFlagListener bubbleMetadataFlagListener2 = bubbleDataRepository2.bubbleMetadataFlagListener;
                    if (bubbleMetadataFlagListener2 == null) {
                        shellExecutor = shellExecutor2;
                        bubbleMetadataFlagListener = null;
                    } else {
                        bubbleMetadataFlagListener = bubbleMetadataFlagListener2;
                        shellExecutor = shellExecutor2;
                    }
                    bubble = new Bubble(str, shortcutInfo2, i, i2, str2, i3, str3, z, shellExecutor, bubbleMetadataFlagListener);
                    if (bubble == null) {
                        arrayList3.add(bubble);
                    }
                    obj2 = null;
                }
            }
            bubble = null;
            if (bubble == null) {
            }
            obj2 = null;
        }
        ShellExecutor shellExecutor3 = this.this$0.mainExecutor;
        final Function1 function1 = this.$cb;
        ((HandlerExecutor) shellExecutor3).execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleDataRepository$loadBubbles$1.1
            @Override // java.lang.Runnable
            public final void run() {
                Function1.this.invoke(arrayList3);
            }
        });
        return Unit.INSTANCE;
    }
}
