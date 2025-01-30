package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.shared.customization.data.content.CustomizationProviderClient;
import com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$setSelections$1$1", m277f = "KeyguardQuickAffordanceRemoteUserSelectionManager.kt", m278l = {118, 120}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$setSelections$1$1 */
/* loaded from: classes.dex */
final class C1573xbef10b67 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<String> $affordanceIds;
    final /* synthetic */ CustomizationProviderClient $client;
    final /* synthetic */ String $slotId;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1573xbef10b67(CustomizationProviderClient customizationProviderClient, String str, List<String> list, Continuation<? super C1573xbef10b67> continuation) {
        super(2, continuation);
        this.$client = customizationProviderClient;
        this.$slotId = str;
        this.$affordanceIds = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new C1573xbef10b67(this.$client, this.$slotId, this.$affordanceIds, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C1573xbef10b67) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:9:0x004e  */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v9, types: [com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        String str;
        Iterator it;
        ?? r7;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CustomizationProviderClient customizationProviderClient = this.$client;
            String str2 = this.$slotId;
            this.label = 1;
            if (((CustomizationProviderClientImpl) customizationProviderClient).deleteAllSelections(str2, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                it = (Iterator) this.L$2;
                str = (String) this.L$1;
                CustomizationProviderClient customizationProviderClient2 = (CustomizationProviderClient) this.L$0;
                ResultKt.throwOnFailure(obj);
                r7 = customizationProviderClient2;
                while (it.hasNext()) {
                    String str3 = (String) it.next();
                    this.L$0 = r7;
                    this.L$1 = str;
                    this.L$2 = it;
                    this.label = 2;
                    r7 = (CustomizationProviderClientImpl) r7;
                    if (r7.insertSelection(str, str3, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
        }
        List<String> list = this.$affordanceIds;
        CustomizationProviderClient customizationProviderClient3 = this.$client;
        str = this.$slotId;
        it = list.iterator();
        r7 = customizationProviderClient3;
        while (it.hasNext()) {
        }
        return Unit.INSTANCE;
    }
}
