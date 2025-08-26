package com.android.systemui.communal.data.db;

import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.coroutines.FlowUtil$createFlow$$inlined$map$1;
import androidx.room.util.DBUtil;
import androidx.sqlite.SQLiteStatement;
import com.android.systemui.communal.shared.model.SpanValue;
import java.util.Arrays;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class CommunalWidgetDao_Impl implements CommunalWidgetDao {
    public final RoomDatabase __db;
    public final AnonymousClass1 __deleteAdapterOfCommunalWidgetItem = new EntityDeleteOrUpdateAdapter(this) { // from class: com.android.systemui.communal.data.db.CommunalWidgetDao_Impl.1
    };
    public final AnonymousClass2 __updateAdapterOfCommunalWidgetItem = new AnonymousClass2(this);

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
        final Flow flowBuffer$default = FlowKt.buffer$default(invalidationTracker.createFlow((String[]) Arrays.copyOf(strArr, 2)), -1, 2);
        final boolean z = false;
        return new Flow() { // from class: androidx.room.coroutines.FlowUtil$createFlow$$inlined$map$1

            /* renamed from: androidx.room.coroutines.FlowUtil$createFlow$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Function1 $block$inlined;
                public final /* synthetic */ RoomDatabase $db$inlined;
                public final /* synthetic */ boolean $inTransaction$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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

                /* JADX WARN: Code restructure failed: missing block: B:21:0x005c, code lost:
                
                    if (r5.emit(r7, r0) == r1) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object objPerformSuspending = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(objPerformSuspending);
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector2;
                        anonymousClass1.label = 1;
                        objPerformSuspending = DBUtil.performSuspending(this.$db$inlined, true, this.$inTransaction$inlined, this.$block$inlined, anonymousClass1);
                        if (objPerformSuspending != coroutineSingletons) {
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(objPerformSuspending);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(objPerformSuspending);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowBuffer$default.collect(new AnonymousClass2(flowCollector, roomDatabase, z, communalWidgetDao_Impl$$ExternalSyntheticLambda0), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }
}
