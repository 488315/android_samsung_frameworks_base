package com.android.systemui.communal.data.repository;

import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import com.android.systemui.communal.data.db.CommunalWidgetDao;
import com.android.systemui.communal.data.db.CommunalWidgetDao_Impl;
import com.android.systemui.communal.shared.model.SpanValue;
import com.android.systemui.communal.shared.model.SpanValueKt;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import java.util.Map;
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
final class CommunalWidgetRepositoryLocalImpl$resizeWidget$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $appWidgetId;
    final /* synthetic */ SpanValue $spanValue;
    final /* synthetic */ int $spanY;
    final /* synthetic */ Map<Integer, Integer> $widgetIdToRankMap;
    int label;
    final /* synthetic */ CommunalWidgetRepositoryLocalImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalWidgetRepositoryLocalImpl$resizeWidget$1(CommunalWidgetRepositoryLocalImpl communalWidgetRepositoryLocalImpl, int i, SpanValue spanValue, Map<Integer, Integer> map, int i2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalWidgetRepositoryLocalImpl;
        this.$appWidgetId = i;
        this.$spanValue = spanValue;
        this.$widgetIdToRankMap = map;
        this.$spanY = i2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalWidgetRepositoryLocalImpl$resizeWidget$1(this.this$0, this.$appWidgetId, this.$spanValue, this.$widgetIdToRankMap, this.$spanY, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalWidgetRepositoryLocalImpl$resizeWidget$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CommunalWidgetDao communalWidgetDao = this.this$0.communalWidgetDao;
        final int i = this.$appWidgetId;
        final SpanValue spanValue = this.$spanValue;
        final Map<Integer, Integer> map = this.$widgetIdToRankMap;
        final CommunalWidgetDao_Impl communalWidgetDao_Impl = (CommunalWidgetDao_Impl) communalWidgetDao;
        communalWidgetDao_Impl.getClass();
        DBUtil.performBlocking(communalWidgetDao_Impl.__db, false, true, new Function1() { // from class: com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                Map map2 = map;
                CommunalWidgetDao_Impl communalWidgetDao_Impl2 = CommunalWidgetDao_Impl.this;
                communalWidgetDao_Impl2.getClass();
                CommunalWidgetDao_Impl$$ExternalSyntheticLambda10 communalWidgetDao_Impl$$ExternalSyntheticLambda10 = new CommunalWidgetDao_Impl$$ExternalSyntheticLambda10(i, 1);
                RoomDatabase roomDatabase = communalWidgetDao_Impl2.__db;
                CommunalWidgetItem communalWidgetItem = (CommunalWidgetItem) DBUtil.performBlocking(roomDatabase, true, false, communalWidgetDao_Impl$$ExternalSyntheticLambda10);
                if (communalWidgetItem != null) {
                    SpanValue spanValue2 = spanValue;
                    int fixed = SpanValueKt.toFixed(spanValue2);
                    int responsive = SpanValueKt.toResponsive(spanValue2);
                    DBUtil.performBlocking(roomDatabase, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda1(communalWidgetDao_Impl2, new CommunalWidgetItem(communalWidgetItem.uid, communalWidgetItem.widgetId, communalWidgetItem.componentName, communalWidgetItem.itemId, communalWidgetItem.userSerialNumber, fixed, responsive), 3));
                }
                DBUtil.performBlocking(roomDatabase, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda1(communalWidgetDao_Impl2, map2, 1));
                return Unit.INSTANCE;
            }
        });
        Logger logger = this.this$0.logger;
        CommunalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0 communalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0 = new CommunalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0(0);
        int i2 = this.$appWidgetId;
        int i3 = this.$spanY;
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, communalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0, null);
        obtain.setInt1(i2);
        obtain.setInt2(i3);
        logger.getBuffer().commit(obtain);
        this.this$0.backupManager.dataChanged();
        return Unit.INSTANCE;
    }
}
