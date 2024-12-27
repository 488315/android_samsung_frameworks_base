package com.android.systemui.communal.data.db;

import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.RoomDatabase;

public final class CommunalWidgetDao_Impl implements CommunalWidgetDao {
    public final RoomDatabase __db;
    public final AnonymousClass1 __deleteAdapterOfCommunalWidgetItem = new EntityDeleteOrUpdateAdapter(this) { // from class: com.android.systemui.communal.data.db.CommunalWidgetDao_Impl.1
    };

    public CommunalWidgetDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
    }
}
