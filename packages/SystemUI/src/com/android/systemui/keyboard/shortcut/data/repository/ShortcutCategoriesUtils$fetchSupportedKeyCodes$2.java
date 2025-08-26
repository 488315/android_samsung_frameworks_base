package com.android.systemui.keyboard.shortcut.data.repository;

import com.android.systemui.keyboard.shortcut.data.model.InternalKeyboardShortcutGroup;
import com.android.systemui.keyboard.shortcut.data.model.InternalKeyboardShortcutInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class ShortcutCategoriesUtils$fetchSupportedKeyCodes$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $deviceId;
    final /* synthetic */ List<List<InternalKeyboardShortcutGroup>> $groupsFromAllSources;
    int label;
    final /* synthetic */ ShortcutCategoriesUtils this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ShortcutCategoriesUtils$fetchSupportedKeyCodes$2(List<? extends List<InternalKeyboardShortcutGroup>> list, ShortcutCategoriesUtils shortcutCategoriesUtils, int i, Continuation continuation) {
        super(2, continuation);
        this.$groupsFromAllSources = list;
        this.this$0 = shortcutCategoriesUtils;
        this.$deviceId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShortcutCategoriesUtils$fetchSupportedKeyCodes$2(this.$groupsFromAllSources, this.this$0, this.$deviceId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShortcutCategoriesUtils$fetchSupportedKeyCodes$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List<List<InternalKeyboardShortcutGroup>> list = this.$groupsFromAllSources;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            List list2 = (List) it.next();
            ArrayList arrayList2 = new ArrayList();
            Iterator it2 = list2.iterator();
            while (it2.hasNext()) {
                CollectionsKt__MutableCollectionsKt.addAll(((InternalKeyboardShortcutGroup) it2.next()).items, arrayList2);
            }
            CollectionsKt__MutableCollectionsKt.addAll(arrayList2, arrayList);
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj2 = arrayList.get(i2);
            i2++;
            arrayList3.add(new Integer(((InternalKeyboardShortcutInfo) obj2).keycode));
        }
        List listDistinct = CollectionsKt___CollectionsKt.distinct(arrayList3);
        boolean[] zArrDeviceHasKeys = this.this$0.inputManager.deviceHasKeys(this.$deviceId, CollectionsKt___CollectionsKt.toIntArray(listDistinct));
        ArrayList arrayList4 = new ArrayList();
        for (Object obj3 : listDistinct) {
            int i3 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            ((Number) obj3).intValue();
            if (zArrDeviceHasKeys[i]) {
                arrayList4.add(obj3);
            }
            i = i3;
        }
        return CollectionsKt___CollectionsKt.toSet(arrayList4);
    }
}
