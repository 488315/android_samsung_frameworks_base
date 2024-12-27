package com.android.systemui.communal.data.db;

import androidx.room.RoomDatabase;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class CommunalDatabase extends RoomDatabase {
    public static final Companion Companion = new Companion(null);
    public static CommunalDatabase instance;

    public final class Companion {
        private Companion() {
        }

        public final void setInstance(CommunalDatabase communalDatabase) {
            CommunalDatabase.instance = communalDatabase;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract CommunalWidgetDao communalWidgetDao();
}
