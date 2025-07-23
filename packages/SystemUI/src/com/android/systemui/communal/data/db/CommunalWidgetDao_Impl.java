package com.android.systemui.communal.data.db;

import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.coroutines.FlowUtil$createFlow$$inlined$map$1;
import androidx.room.util.DBUtil;
import androidx.sqlite.SQLiteStatement;
import com.android.systemui.communal.shared.model.SpanValue;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalWidgetDao_Impl implements CommunalWidgetDao {
    public final RoomDatabase __db;
    public final AnonymousClass1 __deleteAdapterOfCommunalWidgetItem = new EntityDeleteOrUpdateAdapter(this) { // from class: com.android.systemui.communal.data.db.CommunalWidgetDao_Impl.1
    };
    public final AnonymousClass2 __updateAdapterOfCommunalWidgetItem = new AnonymousClass2(this);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$2, reason: invalid class name */
    public class AnonymousClass2 extends EntityDeleteOrUpdateAdapter {
        public AnonymousClass2(CommunalWidgetDao_Impl communalWidgetDao_Impl) {
        }

        public final void bind(SQLiteStatement sQLiteStatement, Object obj) {
            CommunalWidgetItem communalWidgetItem = (CommunalWidgetItem) obj;
            long j = communalWidgetItem.uid;
            sQLiteStatement.bindLong(1, j);
            sQLiteStatement.bindLong(2, communalWidgetItem.widgetId);
            String str = communalWidgetItem.componentName;
            if (str == null) {
                sQLiteStatement.bindNull(3);
            } else {
                sQLiteStatement.bindText(3, str);
            }
            sQLiteStatement.bindLong(4, communalWidgetItem.itemId);
            sQLiteStatement.bindLong(5, communalWidgetItem.userSerialNumber);
            sQLiteStatement.bindLong(6, communalWidgetItem.spanY);
            sQLiteStatement.bindLong(7, communalWidgetItem.spanYNew);
            sQLiteStatement.bindLong(8, j);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$1] */
    public CommunalWidgetDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
    }

    public final long addWidget(int i, String str, Integer num, int i2, SpanValue spanValue) {
        return ((Long) DBUtil.performBlocking(this.__db, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda3(this, i, str, num, i2, spanValue))).longValue();
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [androidx.room.coroutines.FlowUtil$createFlow$$inlined$map$1] */
    public final FlowUtil$createFlow$$inlined$map$1 getWidgets() {
        String[] strArr = {"communal_widget_table", "communal_item_rank_table"};
        final CommunalWidgetDao_Impl$$ExternalSyntheticLambda0 communalWidgetDao_Impl$$ExternalSyntheticLambda0 = new CommunalWidgetDao_Impl$$ExternalSyntheticLambda0(0);
        final RoomDatabase roomDatabase = this.__db;
        InvalidationTracker invalidationTracker = roomDatabase.internalTracker;
        if (invalidationTracker == null) {
            invalidationTracker = null;
        }
        final Flow buffer$default = FlowKt.buffer$default(invalidationTracker.createFlow((String[]) Arrays.copyOf(strArr, 2)), -1, 2);
        final boolean z = false;
        return new Flow() { // from class: androidx.room.coroutines.FlowUtil$createFlow$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: androidx.room.coroutines.FlowUtil$createFlow$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Function1 $block$inlined;
                public final /* synthetic */ RoomDatabase $db$inlined;
                public final /* synthetic */ boolean $inTransaction$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: androidx.room.coroutines.FlowUtil$createFlow$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, RoomDatabase roomDatabase, boolean z, Function1 function1) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$db$inlined = roomDatabase;
                    this.$inTransaction$inlined = z;
                    this.$block$inlined = function1;
                }

                /* JADX WARN: Code restructure failed: missing block: B:18:0x005c, code lost:
                
                    if (r5.emit(r7, r0) != r1) goto L23;
                 */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof androidx.room.coroutines.FlowUtil$createFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        androidx.room.coroutines.FlowUtil$createFlow$$inlined$map$1$2$1 r0 = (androidx.room.coroutines.FlowUtil$createFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        androidx.room.coroutines.FlowUtil$createFlow$$inlined$map$1$2$1 r0 = new androidx.room.coroutines.FlowUtil$createFlow$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        if (r2 == 0) goto L3a
                        if (r2 == r4) goto L32
                        if (r2 != r3) goto L2a
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L5f
                    L2a:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L32:
                        java.lang.Object r5 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L53
                    L3a:
                        kotlin.ResultKt.throwOnFailure(r7)
                        java.util.Set r6 = (java.util.Set) r6
                        kotlinx.coroutines.flow.FlowCollector r6 = r5.$this_unsafeFlow
                        r0.L$0 = r6
                        r0.label = r4
                        boolean r7 = r5.$inTransaction$inlined
                        kotlin.jvm.functions.Function1 r2 = r5.$block$inlined
                        androidx.room.RoomDatabase r5 = r5.$db$inlined
                        java.lang.Object r7 = androidx.room.util.DBUtil.performSuspending(r5, r4, r7, r2, r0)
                        if (r7 != r1) goto L52
                        goto L5e
                    L52:
                        r5 = r6
                    L53:
                        r6 = 0
                        r0.L$0 = r6
                        r0.label = r3
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L5f
                    L5e:
                        return r1
                    L5f:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.room.coroutines.FlowUtil$createFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, roomDatabase, z, communalWidgetDao_Impl$$ExternalSyntheticLambda0), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
