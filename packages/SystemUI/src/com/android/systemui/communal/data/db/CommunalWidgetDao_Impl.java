package com.android.systemui.communal.data.db;

import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.RoomDatabase;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class CommunalWidgetDao_Impl implements CommunalWidgetDao {
    public final RoomDatabase __db;
    public final AnonymousClass1 __deleteAdapterOfCommunalWidgetItem = new EntityDeleteOrUpdateAdapter(this) { // from class: com.android.systemui.communal.data.db.CommunalWidgetDao_Impl.1
    };

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$1] */
    public CommunalWidgetDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
    }
}
