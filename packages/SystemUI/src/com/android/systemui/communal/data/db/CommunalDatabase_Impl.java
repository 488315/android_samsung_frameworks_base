package com.android.systemui.communal.data.db;

import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenDelegate;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.settings.ImsSettings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public final class CommunalDatabase_Impl extends CommunalDatabase {
    public static final /* synthetic */ int $r8$clinit = 0;
    public volatile CommunalWidgetDao_Impl _communalWidgetDao;

    @Override // com.android.systemui.communal.data.db.CommunalDatabase
    public final CommunalWidgetDao communalWidgetDao() {
        CommunalWidgetDao_Impl communalWidgetDao_Impl;
        if (this._communalWidgetDao != null) {
            return this._communalWidgetDao;
        }
        synchronized (this) {
            try {
                if (this._communalWidgetDao == null) {
                    this._communalWidgetDao = new CommunalWidgetDao_Impl(this);
                }
                communalWidgetDao_Impl = this._communalWidgetDao;
            } catch (Throwable th) {
                throw th;
            }
        }
        return communalWidgetDao_Impl;
    }

    @Override // androidx.room.RoomDatabase
    public final InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "communal_widget_table", "communal_item_rank_table");
    }

    @Override // androidx.room.RoomDatabase
    public final RoomOpenDelegate createOpenDelegate() {
        return new RoomOpenDelegate(5, "a83f96ef4babe730b3a00e8acb777a25", "00c4799cc21dd74a230a6173b8d645a3") { // from class: com.android.systemui.communal.data.db.CommunalDatabase_Impl.1
            @Override // androidx.room.RoomOpenDelegate
            public final void createAllTables(SQLiteConnection sQLiteConnection) throws Exception {
                SQLite.execSQL(sQLiteConnection, "CREATE TABLE IF NOT EXISTS `communal_widget_table` (`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `widget_id` INTEGER NOT NULL, `component_name` TEXT NOT NULL, `item_id` INTEGER NOT NULL, `user_serial_number` INTEGER NOT NULL DEFAULT -1, `span_y` INTEGER NOT NULL DEFAULT 3, `span_y_new` INTEGER NOT NULL DEFAULT 1)");
                SQLite.execSQL(sQLiteConnection, "CREATE TABLE IF NOT EXISTS `communal_item_rank_table` (`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `rank` INTEGER NOT NULL DEFAULT 0)");
                SQLite.execSQL(sQLiteConnection, "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
                SQLite.execSQL(sQLiteConnection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a83f96ef4babe730b3a00e8acb777a25')");
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void dropAllTables(SQLiteConnection sQLiteConnection) throws Exception {
                SQLite.execSQL(sQLiteConnection, "DROP TABLE IF EXISTS `communal_widget_table`");
                SQLite.execSQL(sQLiteConnection, "DROP TABLE IF EXISTS `communal_item_rank_table`");
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void onOpen(SQLiteConnection sQLiteConnection) throws Exception {
                int i = CommunalDatabase_Impl.$r8$clinit;
                CommunalDatabase_Impl.this.internalInitInvalidationTracker(sQLiteConnection);
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void onPreMigrate(SQLiteConnection sQLiteConnection) throws Exception {
                DBUtil.dropFtsSyncTriggers(sQLiteConnection);
            }

            @Override // androidx.room.RoomOpenDelegate
            public final RoomOpenDelegate.ValidationResult onValidateSchema(SQLiteConnection sQLiteConnection) throws Exception {
                HashMap map = new HashMap(7);
                map.put(NetworkAnalyticsConstants.DataPoints.UID, new TableInfo.Column(NetworkAnalyticsConstants.DataPoints.UID, "INTEGER", true, 1, null, 1));
                map.put("widget_id", new TableInfo.Column("widget_id", "INTEGER", true, 0, null, 1));
                map.put("component_name", new TableInfo.Column("component_name", ImsSettings.TYPE_TEXT, true, 0, null, 1));
                map.put("item_id", new TableInfo.Column("item_id", "INTEGER", true, 0, null, 1));
                map.put("user_serial_number", new TableInfo.Column("user_serial_number", "INTEGER", true, 0, "-1", 1));
                map.put("span_y", new TableInfo.Column("span_y", "INTEGER", true, 0, "3", 1));
                map.put("span_y_new", new TableInfo.Column("span_y_new", "INTEGER", true, 0, "1", 1));
                TableInfo tableInfo = new TableInfo("communal_widget_table", map, new HashSet(0), new HashSet(0));
                TableInfo tableInfo2 = TableInfo.read(sQLiteConnection, "communal_widget_table");
                if (!tableInfo.equals(tableInfo2)) {
                    return new RoomOpenDelegate.ValidationResult(false, "communal_widget_table(com.android.systemui.communal.data.db.CommunalWidgetItem).\n Expected:\n" + tableInfo + "\n Found:\n" + tableInfo2);
                }
                HashMap map2 = new HashMap(2);
                map2.put(NetworkAnalyticsConstants.DataPoints.UID, new TableInfo.Column(NetworkAnalyticsConstants.DataPoints.UID, "INTEGER", true, 1, null, 1));
                map2.put("rank", new TableInfo.Column("rank", "INTEGER", true, 0, "0", 1));
                TableInfo tableInfo3 = new TableInfo("communal_item_rank_table", map2, new HashSet(0), new HashSet(0));
                TableInfo tableInfo4 = TableInfo.read(sQLiteConnection, "communal_item_rank_table");
                if (tableInfo3.equals(tableInfo4)) {
                    return new RoomOpenDelegate.ValidationResult(true, null);
                }
                return new RoomOpenDelegate.ValidationResult(false, "communal_item_rank_table(com.android.systemui.communal.data.db.CommunalItemRank).\n Expected:\n" + tableInfo3 + "\n Found:\n" + tableInfo4);
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void onCreate() {
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void onPostMigrate() {
            }
        };
    }

    @Override // androidx.room.RoomDatabase
    public final List getAutoMigrations() {
        return new ArrayList();
    }

    @Override // androidx.room.RoomDatabase
    public final Set getRequiredAutoMigrationSpecs() {
        return new HashSet();
    }

    @Override // androidx.room.RoomDatabase
    public final Map getRequiredTypeConverters() {
        HashMap map = new HashMap();
        map.put(CommunalWidgetDao.class, Collections.EMPTY_LIST);
        return map;
    }
}
