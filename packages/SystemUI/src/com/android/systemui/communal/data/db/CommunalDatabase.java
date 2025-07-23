package com.android.systemui.communal.data.db;

import android.content.Context;
import android.database.Cursor;
import android.os.Process;
import android.util.Log;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.android.systemui.R;
import com.android.systemui.communal.shared.model.GlanceableHubMultiUserHelperImpl;
import com.android.systemui.communal.shared.model.SpanValue;
import com.android.systemui.communal.shared.model.SpanValueKt;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class CommunalDatabase extends RoomDatabase {
    public static final Companion Companion = new Companion(null);
    public static final CommunalDatabase$Companion$MIGRATION_1_2$1 MIGRATION_1_2 = new Migration() { // from class: com.android.systemui.communal.data.db.CommunalDatabase$Companion$MIGRATION_1_2$1
        @Override // androidx.room.migration.Migration
        public final void migrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            Log.i("CommunalDatabase", "Migrating from version 1 to 2");
            supportSQLiteDatabase.execSQL("ALTER TABLE communal_widget_table ADD COLUMN user_serial_number INTEGER NOT NULL DEFAULT -1");
        }
    };
    public static final CommunalDatabase$Companion$MIGRATION_2_3$1 MIGRATION_2_3 = new Migration() { // from class: com.android.systemui.communal.data.db.CommunalDatabase$Companion$MIGRATION_2_3$1
        @Override // androidx.room.migration.Migration
        public final void migrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            Log.i("CommunalDatabase", "Migrating from version 2 to 3");
            supportSQLiteDatabase.execSQL("UPDATE communal_item_rank_table SET rank = (SELECT MAX(rank) FROM communal_item_rank_table) - rank");
        }
    };
    public static final CommunalDatabase$Companion$MIGRATION_3_4$1 MIGRATION_3_4 = new Migration() { // from class: com.android.systemui.communal.data.db.CommunalDatabase$Companion$MIGRATION_3_4$1
        @Override // androidx.room.migration.Migration
        public final void migrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            Log.i("CommunalDatabase", "Migrating from version 3 to 4");
            supportSQLiteDatabase.execSQL("ALTER TABLE communal_widget_table ADD COLUMN span_y INTEGER NOT NULL DEFAULT 3");
        }
    };
    public static final CommunalDatabase$Companion$MIGRATION_4_5$1 MIGRATION_4_5 = new Migration() { // from class: com.android.systemui.communal.data.db.CommunalDatabase$Companion$MIGRATION_4_5$1
        @Override // androidx.room.migration.Migration
        public final void migrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            Log.i("CommunalDatabase", "Migrating from version 4 to 5");
            supportSQLiteDatabase.execSQL("ALTER TABLE communal_widget_table ADD COLUMN span_y_new INTEGER NOT NULL DEFAULT 1");
            Cursor query = supportSQLiteDatabase.query();
            while (query.moveToNext()) {
                try {
                    int i = query.getInt(query.getColumnIndex("item_id"));
                    supportSQLiteDatabase.execSQL("UPDATE communal_widget_table SET span_y_new = " + SpanValueKt.toResponsive(SpanValue.Fixed.m1075boximpl(query.getInt(query.getColumnIndex("span_y")))) + " WHERE item_id = " + i);
                } finally {
                }
            }
            Unit unit = Unit.INSTANCE;
            query.close();
        }
    };
    public static CommunalDatabase instance;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static CommunalDatabase getInstance(Context context, DefaultWidgetPopulation defaultWidgetPopulation) {
            new GlanceableHubMultiUserHelperImpl(Process.myUserHandle());
            if (CommunalDatabase.instance == null) {
                String string = context.getResources().getString(R.string.config_communalDatabase);
                int i = Room.$r8$clinit;
                if (string == null || StringsKt__StringsKt.isBlank(string)) {
                    throw new IllegalArgumentException("Cannot build a database with null or empty name. If you are trying to create an in memory database, use Room.inMemoryDatabaseBuilder");
                }
                if (string.equals(":memory:")) {
                    throw new IllegalArgumentException("Cannot build a database with the special name ':memory:'. If you are trying to create an in memory database, use Room.inMemoryDatabaseBuilder");
                }
                RoomDatabase.Builder builder = new RoomDatabase.Builder(context, CommunalDatabase.class, string);
                CommunalDatabase.Companion.getClass();
                Migration[] migrationArr = {CommunalDatabase.MIGRATION_1_2, CommunalDatabase.MIGRATION_2_3, CommunalDatabase.MIGRATION_3_4, CommunalDatabase.MIGRATION_4_5};
                for (int i2 = 0; i2 < 4; i2++) {
                    Migration migration = migrationArr[i2];
                    builder.migrationStartAndEndVersions.add(Integer.valueOf(migration.startVersion));
                    builder.migrationStartAndEndVersions.add(Integer.valueOf(migration.endVersion));
                }
                Migration[] migrationArr2 = (Migration[]) Arrays.copyOf(migrationArr, 4);
                RoomDatabase.MigrationContainer migrationContainer = builder.migrationContainer;
                migrationContainer.getClass();
                for (Migration migration2 : migrationArr2) {
                    migrationContainer.addMigration(migration2);
                }
                builder.requireMigration = false;
                builder.allowDestructiveMigrationOnDowngrade = true;
                builder.allowDestructiveMigrationForAllTables = true;
                if (defaultWidgetPopulation != null) {
                    ((ArrayList) builder.callbacks).add(defaultWidgetPopulation);
                }
                CommunalDatabase.instance = (CommunalDatabase) builder.build();
            }
            CommunalDatabase communalDatabase = CommunalDatabase.instance;
            communalDatabase.getClass();
            return communalDatabase;
        }

        public final void setInstance(CommunalDatabase communalDatabase) {
            CommunalDatabase.instance = communalDatabase;
        }

        private Companion() {
        }

        public static /* synthetic */ void getMIGRATION_1_2$annotations() {
        }

        public static /* synthetic */ void getMIGRATION_2_3$annotations() {
        }

        public static /* synthetic */ void getMIGRATION_3_4$annotations() {
        }

        public static /* synthetic */ void getMIGRATION_4_5$annotations() {
        }
    }

    public abstract CommunalWidgetDao communalWidgetDao();
}
