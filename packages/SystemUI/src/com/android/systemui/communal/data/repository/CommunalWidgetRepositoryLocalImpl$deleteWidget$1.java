package com.android.systemui.communal.data.repository;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import com.android.systemui.communal.data.db.CommunalWidgetDao;
import com.android.systemui.communal.data.db.CommunalWidgetDao_Impl;
import com.android.systemui.log.core.Logger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalWidgetRepositoryLocalImpl$deleteWidget$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $widgetId;
    int label;
    final /* synthetic */ CommunalWidgetRepositoryLocalImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalWidgetRepositoryLocalImpl$deleteWidget$1(CommunalWidgetRepositoryLocalImpl communalWidgetRepositoryLocalImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalWidgetRepositoryLocalImpl;
        this.$widgetId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalWidgetRepositoryLocalImpl$deleteWidget$1(this.this$0, this.$widgetId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalWidgetRepositoryLocalImpl$deleteWidget$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CommunalWidgetDao communalWidgetDao = this.this$0.communalWidgetDao;
        final int i = this.$widgetId;
        final CommunalWidgetDao_Impl communalWidgetDao_Impl = (CommunalWidgetDao_Impl) communalWidgetDao;
        communalWidgetDao_Impl.getClass();
        if (((Boolean) DBUtil.performBlocking(communalWidgetDao_Impl.__db, false, true, new Function1() { // from class: com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                CommunalWidgetDao_Impl communalWidgetDao_Impl2 = CommunalWidgetDao_Impl.this;
                communalWidgetDao_Impl2.getClass();
                boolean z = true;
                CommunalWidgetDao_Impl$$ExternalSyntheticLambda10 communalWidgetDao_Impl$$ExternalSyntheticLambda10 = new CommunalWidgetDao_Impl$$ExternalSyntheticLambda10(i, 1);
                RoomDatabase roomDatabase = communalWidgetDao_Impl2.__db;
                CommunalWidgetItem communalWidgetItem = (CommunalWidgetItem) DBUtil.performBlocking(roomDatabase, true, false, communalWidgetDao_Impl$$ExternalSyntheticLambda10);
                if (communalWidgetItem == null) {
                    z = false;
                } else {
                    final long j = communalWidgetItem.itemId;
                    DBUtil.performBlocking(roomDatabase, false, true, new Function1() { // from class: com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$$ExternalSyntheticLambda16
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj3) {
                            long j2 = j;
                            SQLiteStatement prepare = ((SQLiteConnection) obj3).prepare("DELETE FROM communal_item_rank_table WHERE uid = ?");
                            try {
                                prepare.bindLong(1, j2);
                                prepare.step();
                                prepare.close();
                                return null;
                            } catch (Throwable th) {
                                prepare.close();
                                throw th;
                            }
                        }
                    });
                    DBUtil.performBlocking(roomDatabase, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda1(communalWidgetDao_Impl2, new CommunalWidgetItem[]{communalWidgetItem}, 2));
                }
                return Boolean.valueOf(z);
            }
        })).booleanValue()) {
            this.this$0.appWidgetHost.deleteAppWidgetId(this.$widgetId);
            Logger.i$default(this.this$0.logger, ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(this.$widgetId, "Deleted widget with id ", "."), null, 2, null);
            this.this$0.backupManager.dataChanged();
        }
        return Unit.INSTANCE;
    }
}
