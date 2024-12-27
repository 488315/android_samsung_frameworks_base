package com.android.systemui.communal.data.db;

import androidx.room.RoomDatabase;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class CommunalDatabase extends RoomDatabase {
    public static final Companion Companion = new Companion(null);
    public static CommunalDatabase instance;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
