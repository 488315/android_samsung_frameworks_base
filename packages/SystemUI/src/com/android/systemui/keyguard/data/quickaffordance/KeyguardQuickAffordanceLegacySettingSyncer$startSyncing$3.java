package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ KeyguardQuickAffordanceLegacySettingSyncer.Binding $binding;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ KeyguardQuickAffordanceLegacySettingSyncer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$3(KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer, KeyguardQuickAffordanceLegacySettingSyncer.Binding binding, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardQuickAffordanceLegacySettingSyncer;
        this.$binding = binding;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$3 keyguardQuickAffordanceLegacySettingSyncer$startSyncing$3 = new KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$3(this.this$0, this.$binding, continuation);
        keyguardQuickAffordanceLegacySettingSyncer$startSyncing$3.Z$0 = ((Boolean) obj).booleanValue();
        return keyguardQuickAffordanceLegacySettingSyncer$startSyncing$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$3) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer = this.this$0;
        if (CollectionsKt___CollectionsKt.toSet(CollectionsKt__IterablesKt.flatten(keyguardQuickAffordanceLegacySettingSyncer.selectionsManager.getSelections().values())).contains(this.$binding.affordanceId) != z) {
            if (z) {
                KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer2 = this.this$0;
                KeyguardQuickAffordanceLegacySettingSyncer.Binding binding = this.$binding;
                String str = binding.slotId;
                String str2 = binding.affordanceId;
                KeyguardQuickAffordanceLocalUserSelectionManager keyguardQuickAffordanceLocalUserSelectionManager = keyguardQuickAffordanceLegacySettingSyncer2.selectionsManager;
                Collection collection = (List) keyguardQuickAffordanceLocalUserSelectionManager.getSelections().get(str);
                if (collection == null) {
                    collection = EmptyList.INSTANCE;
                }
                keyguardQuickAffordanceLocalUserSelectionManager.setSelections(str, CollectionsKt___CollectionsKt.plus((Iterable) Collections.singletonList(str2), collection));
            } else {
                KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer3 = this.this$0;
                String str3 = this.$binding.affordanceId;
                KeyguardQuickAffordanceLocalUserSelectionManager keyguardQuickAffordanceLocalUserSelectionManager2 = keyguardQuickAffordanceLegacySettingSyncer3.selectionsManager;
                Map selections = keyguardQuickAffordanceLocalUserSelectionManager2.getSelections();
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry entry : selections.entrySet()) {
                    if (((List) entry.getValue()).contains(str3)) {
                        linkedHashMap.put(entry.getKey(), entry.getValue());
                    }
                }
                ArrayList arrayList = new ArrayList(linkedHashMap.size());
                Iterator it = linkedHashMap.entrySet().iterator();
                while (it.hasNext()) {
                    arrayList.add((String) ((Map.Entry) it.next()).getKey());
                }
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    String str4 = (String) it2.next();
                    Collection collection2 = (List) selections.get(str4);
                    if (collection2 == null) {
                        collection2 = EmptyList.INSTANCE;
                    }
                    ArrayList arrayList2 = new ArrayList(collection2);
                    arrayList2.remove(str3);
                    keyguardQuickAffordanceLocalUserSelectionManager2.setSelections(str4, arrayList2);
                }
            }
        }
        return Unit.INSTANCE;
    }
}
