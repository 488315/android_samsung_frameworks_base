package com.android.systemui.shared.customization.data.content;

import android.content.ContentResolver;
import android.database.Cursor;
import com.android.systemui.shared.customization.data.content.CustomizationProviderClient;
import com.android.systemui.shared.customization.data.content.CustomizationProviderContract;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.p055io.CloseableKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$querySelections$2", m277f = "CustomizationProviderClient.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class CustomizationProviderClientImpl$querySelections$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CustomizationProviderClientImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomizationProviderClientImpl$querySelections$2(CustomizationProviderClientImpl customizationProviderClientImpl, Continuation<? super CustomizationProviderClientImpl$querySelections$2> continuation) {
        super(2, continuation);
        this.this$0 = customizationProviderClientImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomizationProviderClientImpl$querySelections$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomizationProviderClientImpl$querySelections$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        Cursor query = contentResolver.query(CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.URI, null, null, null, null);
        if (query == null) {
            return null;
        }
        try {
            ListBuilder listBuilder = new ListBuilder();
            int columnIndex = query.getColumnIndex("slot_id");
            int columnIndex2 = query.getColumnIndex("affordance_id");
            int columnIndex3 = query.getColumnIndex("affordance_name");
            if (columnIndex != -1 && columnIndex2 != -1 && columnIndex3 != -1) {
                while (query.moveToNext()) {
                    listBuilder.add(new CustomizationProviderClient.Selection(query.getString(columnIndex), query.getString(columnIndex2), query.getString(columnIndex3)));
                }
            }
            listBuilder.build();
            CloseableKt.closeFinally(query, null);
            return listBuilder;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(query, th);
                throw th2;
            }
        }
    }
}
