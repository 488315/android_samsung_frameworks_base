package com.android.systemui.communal.data.db;

import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteConnectionUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import com.android.systemui.communal.data.db.CommunalWidgetDao_Impl;
import com.android.systemui.communal.nano.CommunalHubState;
import com.android.systemui.communal.shared.model.SpanValue;
import java.util.Map;
import kotlin.Unit;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalWidgetDao_Impl$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CommunalWidgetDao_Impl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CommunalWidgetDao_Impl$$ExternalSyntheticLambda1(CommunalWidgetDao_Impl communalWidgetDao_Impl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = communalWidgetDao_Impl;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) throws Exception {
        SQLiteStatement sQLiteStatementPrepare;
        CommunalWidgetDao_Impl communalWidgetDao_Impl = this.f$0;
        Object obj2 = this.f$1;
        switch (this.$r8$classId) {
            case 0:
                CommunalWidgetDao_Impl$$ExternalSyntheticLambda0 communalWidgetDao_Impl$$ExternalSyntheticLambda0 = new CommunalWidgetDao_Impl$$ExternalSyntheticLambda0(1);
                CommunalWidgetDao_Impl communalWidgetDao_Impl2 = this.f$0;
                RoomDatabase roomDatabase = communalWidgetDao_Impl2.__db;
                DBUtil.performBlocking(roomDatabase, false, true, communalWidgetDao_Impl$$ExternalSyntheticLambda0);
                DBUtil.performBlocking(roomDatabase, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda0(3));
                for (CommunalHubState.CommunalWidgetItem communalWidgetItem : ((CommunalHubState) obj2).widgets) {
                    int i = communalWidgetItem.spanYNew;
                    SpanValue spanValueM1078boximpl = i != 0 ? SpanValue.Responsive.m1078boximpl(i) : null;
                    if (spanValueM1078boximpl == null) {
                        spanValueM1078boximpl = SpanValue.Fixed.m1077boximpl(RangesKt___RangesKt.coerceIn(communalWidgetItem.spanY, 3, 6));
                    }
                    communalWidgetDao_Impl2.addWidget(communalWidgetItem.widgetId, communalWidgetItem.componentName, Integer.valueOf(communalWidgetItem.rank), communalWidgetItem.userSerialNumber, spanValueM1078boximpl);
                }
                return Unit.INSTANCE;
            case 1:
                communalWidgetDao_Impl.getClass();
                for (Map.Entry entry : ((Map) obj2).entrySet()) {
                    int iIntValue = ((Number) entry.getKey()).intValue();
                    int iIntValue2 = ((Number) entry.getValue()).intValue();
                    CommunalWidgetDao_Impl$$ExternalSyntheticLambda10 communalWidgetDao_Impl$$ExternalSyntheticLambda10 = new CommunalWidgetDao_Impl$$ExternalSyntheticLambda10(iIntValue, 1);
                    RoomDatabase roomDatabase2 = communalWidgetDao_Impl.__db;
                    CommunalWidgetItem communalWidgetItem2 = (CommunalWidgetItem) DBUtil.performBlocking(roomDatabase2, true, false, communalWidgetDao_Impl$$ExternalSyntheticLambda10);
                    if (communalWidgetItem2 != null) {
                        DBUtil.performBlocking(roomDatabase2, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda15(iIntValue2, communalWidgetItem2.itemId));
                    }
                }
                return Unit.INSTANCE;
            case 2:
                SQLiteConnection sQLiteConnection = (SQLiteConnection) obj;
                CommunalWidgetDao_Impl.AnonymousClass1 anonymousClass1 = communalWidgetDao_Impl.__deleteAdapterOfCommunalWidgetItem;
                CommunalWidgetItem[] communalWidgetItemArr = (CommunalWidgetItem[]) obj2;
                anonymousClass1.getClass();
                sQLiteStatementPrepare = sQLiteConnection.prepare("DELETE FROM `communal_widget_table` WHERE `uid` = ?");
                try {
                    ArrayIterator arrayIterator = new ArrayIterator(communalWidgetItemArr);
                    while (arrayIterator.hasNext()) {
                        Object next = arrayIterator.next();
                        if (next != null) {
                            anonymousClass1.getClass();
                            sQLiteStatementPrepare.bindLong(1, ((CommunalWidgetItem) next).uid);
                            sQLiteStatementPrepare.step();
                            sQLiteStatementPrepare.reset();
                            SQLiteConnectionUtil.getTotalChangedRows(sQLiteConnection);
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                    AutoCloseableKt.closeFinally(sQLiteStatementPrepare, null);
                    return null;
                } finally {
                }
            default:
                SQLiteConnection sQLiteConnection2 = (SQLiteConnection) obj;
                CommunalWidgetDao_Impl.AnonymousClass2 anonymousClass2 = communalWidgetDao_Impl.__updateAdapterOfCommunalWidgetItem;
                CommunalWidgetItem communalWidgetItem3 = (CommunalWidgetItem) obj2;
                anonymousClass2.getClass();
                sQLiteStatementPrepare = sQLiteConnection2.prepare("UPDATE OR ABORT `communal_widget_table` SET `uid` = ?,`widget_id` = ?,`component_name` = ?,`item_id` = ?,`user_serial_number` = ?,`span_y` = ?,`span_y_new` = ? WHERE `uid` = ?");
                try {
                    anonymousClass2.bind(sQLiteStatementPrepare, communalWidgetItem3);
                    sQLiteStatementPrepare.step();
                    sQLiteStatementPrepare.close();
                    SQLiteConnectionUtil.getTotalChangedRows(sQLiteConnection2);
                    return null;
                } finally {
                }
        }
    }
}
