package com.android.systemui.communal.data.db;

import android.content.ComponentName;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteConnectionUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import com.android.systemui.communal.shared.model.SpanValue;
import com.android.systemui.communal.shared.model.SpanValueKt;
import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalWidgetDao_Impl$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ CommunalWidgetDao_Impl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ Integer f$3;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ SpanValue f$5;

    public /* synthetic */ CommunalWidgetDao_Impl$$ExternalSyntheticLambda3(CommunalWidgetDao_Impl communalWidgetDao_Impl, int i, ComponentName componentName, Integer num, int i2, SpanValue.Fixed fixed) {
        this.f$0 = communalWidgetDao_Impl;
        this.f$1 = i;
        this.f$2 = componentName;
        this.f$3 = num;
        this.f$4 = i2;
        this.f$5 = fixed;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0051  */
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo781invoke(Object obj) {
        int iIntValue;
        switch (this.$r8$classId) {
            case 0:
                CommunalWidgetDao_Impl$$ExternalSyntheticLambda0 communalWidgetDao_Impl$$ExternalSyntheticLambda0 = new CommunalWidgetDao_Impl$$ExternalSyntheticLambda0(2);
                CommunalWidgetDao_Impl communalWidgetDao_Impl = this.f$0;
                RoomDatabase roomDatabase = communalWidgetDao_Impl.__db;
                Map map = (Map) DBUtil.performBlocking(roomDatabase, true, false, communalWidgetDao_Impl$$ExternalSyntheticLambda0);
                Integer num = this.f$3;
                Integer numValueOf = null;
                if (num == null) {
                    Iterator it = map.keySet().iterator();
                    if (it.hasNext()) {
                        numValueOf = Integer.valueOf(((CommunalItemRank) it.next()).rank + 1);
                        while (it.hasNext()) {
                            Integer numValueOf2 = Integer.valueOf(((CommunalItemRank) it.next()).rank + 1);
                            if (numValueOf.compareTo(numValueOf2) < 0) {
                                numValueOf = numValueOf2;
                            }
                        }
                    }
                    iIntValue = numValueOf != null ? numValueOf.intValue() : 0;
                } else {
                    Integer num2 = num.intValue() >= 0 ? num : null;
                    if (num2 != null) {
                        iIntValue = num2.intValue();
                    }
                }
                if (num != null) {
                    for (Map.Entry entry : map.entrySet()) {
                        CommunalItemRank communalItemRank = (CommunalItemRank) entry.getKey();
                        CommunalWidgetItem communalWidgetItem = (CommunalWidgetItem) entry.getValue();
                        int i = communalItemRank.rank;
                        if (i >= iIntValue) {
                            DBUtil.performBlocking(communalWidgetDao_Impl.__db, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda15(i + 1, communalWidgetItem.itemId));
                        }
                    }
                }
                final long jLongValue = ((Long) DBUtil.performBlocking(roomDatabase, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda10(iIntValue, 0))).longValue();
                SpanValue spanValue = this.f$5;
                final int fixed = SpanValueKt.toFixed(spanValue);
                final int responsive = SpanValueKt.toResponsive(spanValue);
                final String str = (String) this.f$2;
                final int i2 = this.f$4;
                final int i3 = this.f$1;
                Long l = (Long) DBUtil.performBlocking(roomDatabase, false, true, new Function1() { // from class: com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$$ExternalSyntheticLambda14
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) throws Exception {
                        int i4 = i3;
                        long j = jLongValue;
                        int i5 = i2;
                        int i6 = fixed;
                        int i7 = responsive;
                        SQLiteConnection sQLiteConnection = (SQLiteConnection) obj2;
                        SQLiteStatement sQLiteStatementPrepare = sQLiteConnection.prepare("INSERT INTO communal_widget_table(widget_id, component_name, item_id, user_serial_number, span_y, span_y_new) VALUES(?, ?, ?, ?, ?, ?)");
                        try {
                            sQLiteStatementPrepare.bindLong(1, i4);
                            String str2 = str;
                            if (str2 == null) {
                                sQLiteStatementPrepare.bindNull(2);
                            } else {
                                sQLiteStatementPrepare.bindText(2, str2);
                            }
                            sQLiteStatementPrepare.bindLong(3, j);
                            sQLiteStatementPrepare.bindLong(4, i5);
                            sQLiteStatementPrepare.bindLong(5, i6);
                            sQLiteStatementPrepare.bindLong(6, i7);
                            sQLiteStatementPrepare.step();
                            Long lValueOf = Long.valueOf(SQLiteConnectionUtil.getLastInsertedRowId(sQLiteConnection));
                            sQLiteStatementPrepare.close();
                            return lValueOf;
                        } catch (Throwable th) {
                            sQLiteStatementPrepare.close();
                            throw th;
                        }
                    }
                });
                l.getClass();
                return l;
            default:
                ComponentName componentName = (ComponentName) this.f$2;
                CommunalWidgetDao_Impl communalWidgetDao_Impl2 = this.f$0;
                communalWidgetDao_Impl2.getClass();
                return Long.valueOf(communalWidgetDao_Impl2.addWidget(this.f$1, componentName.flattenToString(), this.f$3, this.f$4, (SpanValue.Fixed) this.f$5));
        }
    }

    public /* synthetic */ CommunalWidgetDao_Impl$$ExternalSyntheticLambda3(CommunalWidgetDao_Impl communalWidgetDao_Impl, int i, String str, Integer num, int i2, SpanValue spanValue) {
        this.f$0 = communalWidgetDao_Impl;
        this.f$1 = i;
        this.f$2 = str;
        this.f$3 = num;
        this.f$4 = i2;
        this.f$5 = spanValue;
    }
}
