package com.android.systemui.shared.customization.data.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import com.android.systemui.shared.customization.data.content.CustomizationProviderContract;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class CustomizationProviderClientImpl$insertSelection$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $affordanceId;
    final /* synthetic */ String $slotId;
    int label;
    final /* synthetic */ CustomizationProviderClientImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomizationProviderClientImpl$insertSelection$2(CustomizationProviderClientImpl customizationProviderClientImpl, String str, String str2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customizationProviderClientImpl;
        this.$slotId = str;
        this.$affordanceId = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomizationProviderClientImpl$insertSelection$2(this.this$0, this.$slotId, this.$affordanceId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomizationProviderClientImpl$insertSelection$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ContentResolver contentResolver = this.this$0.context.getContentResolver();
        CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.INSTANCE.getClass();
        Uri uri = CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.URI;
        ContentValues contentValues = new ContentValues();
        String str = this.$slotId;
        String str2 = this.$affordanceId;
        contentValues.put("slot_id", str);
        contentValues.put("affordance_id", str2);
        Unit unit = Unit.INSTANCE;
        return contentResolver.insert(uri, contentValues);
    }
}
