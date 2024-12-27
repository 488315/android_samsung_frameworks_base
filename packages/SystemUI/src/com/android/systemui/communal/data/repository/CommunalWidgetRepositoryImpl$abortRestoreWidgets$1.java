package com.android.systemui.communal.data.repository;

import com.android.systemui.communal.data.backup.CommunalBackupUtils;
import com.android.systemui.log.core.Logger;
import java.io.File;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class CommunalWidgetRepositoryImpl$abortRestoreWidgets$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CommunalWidgetRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalWidgetRepositoryImpl$abortRestoreWidgets$1(CommunalWidgetRepositoryImpl communalWidgetRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalWidgetRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalWidgetRepositoryImpl$abortRestoreWidgets$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalWidgetRepositoryImpl$abortRestoreWidgets$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Logger.i$default(this.this$0.logger, "Restore widgets aborted", null, 2, null);
        CommunalBackupUtils communalBackupUtils = this.this$0.backupUtils;
        communalBackupUtils.getClass();
        new File(communalBackupUtils.context.getFilesDir(), "communal_restore").delete();
        return Unit.INSTANCE;
    }
}
