package com.android.systemui.communal.data.db;

import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import com.android.systemui.communal.data.db.CommunalWidgetDao_Impl;
import com.android.systemui.communal.nano.CommunalHubState;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ArrayIterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class CommunalWidgetDao_Impl$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CommunalWidgetDao_Impl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CommunalWidgetDao_Impl$$ExternalSyntheticLambda2(CommunalWidgetDao_Impl communalWidgetDao_Impl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = communalWidgetDao_Impl;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Object obj2 = this.f$1;
        CommunalWidgetDao_Impl communalWidgetDao_Impl = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                communalWidgetDao_Impl.getClass();
                for (Map.Entry entry : ((Map) obj2).entrySet()) {
                    int intValue = ((Number) entry.getKey()).intValue();
                    final int intValue2 = ((Number) entry.getValue()).intValue();
                    CommunalWidgetDao_Impl$$ExternalSyntheticLambda5 communalWidgetDao_Impl$$ExternalSyntheticLambda5 = new CommunalWidgetDao_Impl$$ExternalSyntheticLambda5(intValue, 0);
                    RoomDatabase roomDatabase = communalWidgetDao_Impl.__db;
                    CommunalWidgetItem communalWidgetItem = (CommunalWidgetItem) DBUtil.performBlocking(roomDatabase, true, false, communalWidgetDao_Impl$$ExternalSyntheticLambda5);
                    if (communalWidgetItem != null) {
                        final long j = communalWidgetItem.itemId;
                        DBUtil.performBlocking(roomDatabase, false, true, new Function1() { // from class: com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$$ExternalSyntheticLambda7
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                int i = intValue2;
                                long j2 = j;
                                SQLiteStatement prepare = ((SQLiteConnection) obj3).prepare("UPDATE communal_item_rank_table SET rank = ? WHERE uid = ?");
                                try {
                                    prepare.bindLong(1, i);
                                    prepare.bindLong(2, j2);
                                    prepare.step();
                                    prepare.close();
                                    return null;
                                } catch (Throwable th) {
                                    prepare.close();
                                    throw th;
                                }
                            }
                        });
                    }
                }
                return Unit.INSTANCE;
            case 1:
                CommunalWidgetDao_Impl$$ExternalSyntheticLambda1 communalWidgetDao_Impl$$ExternalSyntheticLambda1 = new CommunalWidgetDao_Impl$$ExternalSyntheticLambda1(1);
                RoomDatabase roomDatabase2 = communalWidgetDao_Impl.__db;
                DBUtil.performBlocking(roomDatabase2, false, true, communalWidgetDao_Impl$$ExternalSyntheticLambda1);
                DBUtil.performBlocking(roomDatabase2, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda1(2));
                CommunalHubState.CommunalWidgetItem[] communalWidgetItemArr = ((CommunalHubState) obj2).widgets;
                for (CommunalHubState.CommunalWidgetItem communalWidgetItem2 : communalWidgetItemArr) {
                    ((Long) DBUtil.performBlocking(communalWidgetDao_Impl.__db, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda0(communalWidgetDao_Impl, communalWidgetItem2.widgetId, communalWidgetItem2.componentName, communalWidgetItem2.rank))).longValue();
                }
                return Unit.INSTANCE;
            default:
                SQLiteConnection sQLiteConnection = (SQLiteConnection) obj;
                CommunalWidgetDao_Impl.AnonymousClass1 anonymousClass1 = communalWidgetDao_Impl.__deleteAdapterOfCommunalWidgetItem;
                CommunalWidgetItem[] communalWidgetItemArr2 = (CommunalWidgetItem[]) obj2;
                if (communalWidgetItemArr2 == null) {
                    anonymousClass1.getClass();
                    return null;
                }
                anonymousClass1.getClass();
                SQLiteStatement prepare = sQLiteConnection.prepare("DELETE FROM `communal_widget_table` WHERE `uid` = ?");
                try {
                    ArrayIterator arrayIterator = new ArrayIterator(communalWidgetItemArr2);
                    while (arrayIterator.hasNext()) {
                        Object next = arrayIterator.next();
                        if (next != null) {
                            anonymousClass1.getClass();
                            prepare.bindLong(1, ((CommunalWidgetItem) next).uid);
                            prepare.step();
                            prepare.reset();
                            prepare = sQLiteConnection.prepare("SELECT changes()");
                            try {
                                prepare.step();
                                prepare.getLong(0);
                                prepare.close();
                            } finally {
                                prepare.close();
                            }
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                    return null;
                } catch (Throwable th) {
                    throw th;
                }
        }
    }
}
