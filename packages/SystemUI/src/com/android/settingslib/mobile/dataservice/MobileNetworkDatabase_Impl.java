package com.android.settingslib.mobile.dataservice;

import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenDelegate;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import com.sec.ims.settings.ImsSettings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class MobileNetworkDatabase_Impl extends MobileNetworkDatabase {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // androidx.room.RoomDatabase
    public final InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "subscriptionInfo", "MobileNetworkInfo");
    }

    @Override // androidx.room.RoomDatabase
    public final RoomOpenDelegate createOpenDelegate() {
        return new RoomOpenDelegate(1, "c4b8b3a0a8ec44684d55f07bbe0fbdb5", "1cdce0c1e59b9e90ca6bdf9ec4c4e881") { // from class: com.android.settingslib.mobile.dataservice.MobileNetworkDatabase_Impl.1
            @Override // androidx.room.RoomOpenDelegate
            public final void createAllTables(SQLiteConnection sQLiteConnection) throws Exception {
                SQLite.execSQL(sQLiteConnection, "CREATE TABLE IF NOT EXISTS `subscriptionInfo` (`sudId` TEXT NOT NULL, `simSlotIndex` INTEGER NOT NULL, `isEmbedded` INTEGER NOT NULL, `isOpportunistic` INTEGER NOT NULL, `uniqueName` TEXT, `isSubscriptionVisible` INTEGER NOT NULL, `isDefaultSubscriptionSelection` INTEGER NOT NULL, `isValidSubscription` INTEGER NOT NULL, `isActiveSubscription` INTEGER NOT NULL, PRIMARY KEY(`sudId`))");
                SQLite.execSQL(sQLiteConnection, "CREATE INDEX IF NOT EXISTS `index_subscriptionInfo_sudId` ON `subscriptionInfo` (`sudId`)");
                SQLite.execSQL(sQLiteConnection, "CREATE TABLE IF NOT EXISTS `MobileNetworkInfo` (`subId` TEXT NOT NULL, `isMobileDataEnabled` INTEGER NOT NULL, PRIMARY KEY(`subId`))");
                SQLite.execSQL(sQLiteConnection, "CREATE INDEX IF NOT EXISTS `index_MobileNetworkInfo_subId` ON `MobileNetworkInfo` (`subId`)");
                SQLite.execSQL(sQLiteConnection, "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
                SQLite.execSQL(sQLiteConnection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'c4b8b3a0a8ec44684d55f07bbe0fbdb5')");
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void dropAllTables(SQLiteConnection sQLiteConnection) throws Exception {
                SQLite.execSQL(sQLiteConnection, "DROP TABLE IF EXISTS `subscriptionInfo`");
                SQLite.execSQL(sQLiteConnection, "DROP TABLE IF EXISTS `MobileNetworkInfo`");
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void onOpen(SQLiteConnection sQLiteConnection) throws Exception {
                int i = MobileNetworkDatabase_Impl.$r8$clinit;
                MobileNetworkDatabase_Impl.this.internalInitInvalidationTracker(sQLiteConnection);
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void onPreMigrate(SQLiteConnection sQLiteConnection) throws Exception {
                DBUtil.dropFtsSyncTriggers(sQLiteConnection);
            }

            @Override // androidx.room.RoomOpenDelegate
            public final RoomOpenDelegate.ValidationResult onValidateSchema(SQLiteConnection sQLiteConnection) throws Exception {
                HashMap map = new HashMap(9);
                map.put("sudId", new TableInfo.Column("sudId", ImsSettings.TYPE_TEXT, true, 1, null, 1));
                map.put("simSlotIndex", new TableInfo.Column("simSlotIndex", "INTEGER", true, 0, null, 1));
                map.put("isEmbedded", new TableInfo.Column("isEmbedded", "INTEGER", true, 0, null, 1));
                map.put("isOpportunistic", new TableInfo.Column("isOpportunistic", "INTEGER", true, 0, null, 1));
                map.put("uniqueName", new TableInfo.Column("uniqueName", ImsSettings.TYPE_TEXT, false, 0, null, 1));
                map.put("isSubscriptionVisible", new TableInfo.Column("isSubscriptionVisible", "INTEGER", true, 0, null, 1));
                map.put("isDefaultSubscriptionSelection", new TableInfo.Column("isDefaultSubscriptionSelection", "INTEGER", true, 0, null, 1));
                map.put("isValidSubscription", new TableInfo.Column("isValidSubscription", "INTEGER", true, 0, null, 1));
                map.put("isActiveSubscription", new TableInfo.Column("isActiveSubscription", "INTEGER", true, 0, null, 1));
                HashSet hashSet = new HashSet(0);
                HashSet hashSet2 = new HashSet(1);
                hashSet2.add(new TableInfo.Index("index_subscriptionInfo_sudId", false, Arrays.asList("sudId"), Arrays.asList("ASC")));
                TableInfo tableInfo = new TableInfo("subscriptionInfo", map, hashSet, hashSet2);
                TableInfo tableInfo2 = TableInfo.read(sQLiteConnection, "subscriptionInfo");
                if (!tableInfo.equals(tableInfo2)) {
                    return new RoomOpenDelegate.ValidationResult(false, "subscriptionInfo(com.android.settingslib.mobile.dataservice.SubscriptionInfoEntity).\n Expected:\n" + tableInfo + "\n Found:\n" + tableInfo2);
                }
                HashMap map2 = new HashMap(2);
                map2.put("subId", new TableInfo.Column("subId", ImsSettings.TYPE_TEXT, true, 1, null, 1));
                map2.put("isMobileDataEnabled", new TableInfo.Column("isMobileDataEnabled", "INTEGER", true, 0, null, 1));
                HashSet hashSet3 = new HashSet(0);
                HashSet hashSet4 = new HashSet(1);
                hashSet4.add(new TableInfo.Index("index_MobileNetworkInfo_subId", false, Arrays.asList("subId"), Arrays.asList("ASC")));
                TableInfo tableInfo3 = new TableInfo("MobileNetworkInfo", map2, hashSet3, hashSet4);
                TableInfo tableInfo4 = TableInfo.read(sQLiteConnection, "MobileNetworkInfo");
                if (tableInfo3.equals(tableInfo4)) {
                    return new RoomOpenDelegate.ValidationResult(true, null);
                }
                return new RoomOpenDelegate.ValidationResult(false, "MobileNetworkInfo(com.android.settingslib.mobile.dataservice.MobileNetworkInfoEntity).\n Expected:\n" + tableInfo3 + "\n Found:\n" + tableInfo4);
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
        List list = Collections.EMPTY_LIST;
        map.put(SubscriptionInfoDao_Impl.class, list);
        map.put(MobileNetworkInfoDao_Impl.class, list);
        return map;
    }
}
