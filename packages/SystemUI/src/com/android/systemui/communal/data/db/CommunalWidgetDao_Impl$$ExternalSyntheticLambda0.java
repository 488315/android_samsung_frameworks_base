package com.android.systemui.communal.data.db;

import android.content.ComponentName;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteConnectionUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class CommunalWidgetDao_Impl$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ CommunalWidgetDao_Impl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ CommunalWidgetDao_Impl$$ExternalSyntheticLambda0(CommunalWidgetDao_Impl communalWidgetDao_Impl, int i, ComponentName componentName, int i2) {
        this.f$0 = communalWidgetDao_Impl;
        this.f$1 = i;
        this.f$2 = componentName;
        this.f$3 = i2;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ComponentName componentName = (ComponentName) this.f$2;
                CommunalWidgetDao_Impl communalWidgetDao_Impl = this.f$0;
                communalWidgetDao_Impl.getClass();
                Long l = (Long) DBUtil.performBlocking(communalWidgetDao_Impl.__db, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda0(communalWidgetDao_Impl, this.f$1, componentName.flattenToString(), this.f$3));
                l.longValue();
                return l;
            default:
                CommunalWidgetDao_Impl communalWidgetDao_Impl2 = this.f$0;
                communalWidgetDao_Impl2.getClass();
                CommunalWidgetDao_Impl$$ExternalSyntheticLambda5 communalWidgetDao_Impl$$ExternalSyntheticLambda5 = new CommunalWidgetDao_Impl$$ExternalSyntheticLambda5(this.f$3, 1);
                RoomDatabase roomDatabase = communalWidgetDao_Impl2.__db;
                final long longValue = ((Long) DBUtil.performBlocking(roomDatabase, false, true, communalWidgetDao_Impl$$ExternalSyntheticLambda5)).longValue();
                final int i = this.f$1;
                final String str = (String) this.f$2;
                Long l2 = (Long) DBUtil.performBlocking(roomDatabase, false, true, new Function1() { // from class: com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$$ExternalSyntheticLambda12
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        int i2 = i;
                        long j = longValue;
                        SQLiteConnection sQLiteConnection = (SQLiteConnection) obj2;
                        SQLiteStatement prepare = sQLiteConnection.prepare("INSERT INTO communal_widget_table(widget_id, component_name, item_id) VALUES(?, ?, ?)");
                        try {
                            prepare.bindLong(1, i2);
                            String str2 = str;
                            if (str2 == null) {
                                prepare.bindNull();
                            } else {
                                prepare.bindText(str2);
                            }
                            prepare.bindLong(3, j);
                            prepare.step();
                            Long valueOf = Long.valueOf(SQLiteConnectionUtil.getLastInsertedRowId(sQLiteConnection));
                            prepare.close();
                            return valueOf;
                        } catch (Throwable th) {
                            prepare.close();
                            throw th;
                        }
                    }
                });
                l2.getClass();
                return l2;
        }
    }

    public /* synthetic */ CommunalWidgetDao_Impl$$ExternalSyntheticLambda0(CommunalWidgetDao_Impl communalWidgetDao_Impl, int i, String str, int i2) {
        this.f$0 = communalWidgetDao_Impl;
        this.f$1 = i;
        this.f$2 = str;
        this.f$3 = i2;
    }
}
