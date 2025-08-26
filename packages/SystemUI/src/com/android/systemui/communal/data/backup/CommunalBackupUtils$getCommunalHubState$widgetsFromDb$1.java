package com.android.systemui.communal.data.backup;

import androidx.room.coroutines.FlowUtil$createFlow$$inlined$map$1;
import com.android.systemui.communal.data.db.CommunalDatabase;
import com.android.systemui.communal.data.db.CommunalWidgetDao_Impl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
final class CommunalBackupUtils$getCommunalHubState$widgetsFromDb$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ CommunalDatabase $database;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalBackupUtils$getCommunalHubState$widgetsFromDb$1(CommunalDatabase communalDatabase, Continuation continuation) {
        super(2, continuation);
        this.$database = communalDatabase;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalBackupUtils$getCommunalHubState$widgetsFromDb$1(this.$database, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalBackupUtils$getCommunalHubState$widgetsFromDb$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        FlowUtil$createFlow$$inlined$map$1 widgets = ((CommunalWidgetDao_Impl) this.$database.communalWidgetDao()).getWidgets();
        this.label = 1;
        Object objFirst = FlowKt.first(widgets, this);
        return objFirst == coroutineSingletons ? coroutineSingletons : objFirst;
    }
}
