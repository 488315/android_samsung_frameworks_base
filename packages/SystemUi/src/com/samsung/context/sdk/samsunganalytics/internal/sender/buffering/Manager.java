package com.samsung.context.sdk.samsunganalytics.internal.sender.buffering;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.DBOpenHelper;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database.DbManager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database.DefaultDBOpenHelper;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.queue.QueueManager;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Manager {
    public static Manager instance;
    public DbManager dbManager;
    public final QueueManager queueManager;
    public boolean useDatabase;

    private Manager(Context context, boolean z) {
        if (z) {
            this.dbManager = new DbManager(context);
        }
        this.queueManager = new QueueManager();
        this.useDatabase = z;
    }

    public static Manager getInstance(Context context, Configuration configuration) {
        if (instance == null) {
            synchronized (Manager.class) {
                if (PolicyUtils.senderType != 0) {
                    instance = new Manager(context, false);
                } else if (context.getSharedPreferences("SamsungAnalyticsPrefs", 0).getString("lgt", "").equals("rtb")) {
                    configuration.getClass();
                    instance = new Manager(context, true);
                } else {
                    instance = new Manager(context, false);
                }
            }
        }
        return instance;
    }

    public final void delete() {
        if (this.useDatabase) {
            this.dbManager.delete(Long.valueOf(System.currentTimeMillis()).longValue() - (5 * 86400000));
        }
    }

    public final Queue get(int i) {
        Queue queue;
        if (this.useDatabase) {
            delete();
            if (i <= 0) {
                queue = this.dbManager.select("select * from logs_v2");
            } else {
                DbManager dbManager = this.dbManager;
                dbManager.getClass();
                queue = dbManager.select("select * from logs_v2 LIMIT " + i);
            }
        } else {
            queue = this.queueManager.logQueue;
        }
        if (!queue.isEmpty()) {
            StringBuilder sb = new StringBuilder("get log from ");
            sb.append(this.useDatabase ? "Database " : "Queue ");
            sb.append("(");
            sb.append(queue.size());
            sb.append(")");
            Debug.LogENG(sb.toString());
        }
        return queue;
    }

    public final void insert(SimpleLog simpleLog) {
        if (this.useDatabase) {
            this.dbManager.insert(simpleLog);
            return;
        }
        LinkedBlockingQueue linkedBlockingQueue = this.queueManager.logQueue;
        if (linkedBlockingQueue.offer(simpleLog)) {
            return;
        }
        Debug.LogD("QueueManager", "queue size over. remove oldest log");
        linkedBlockingQueue.poll();
        linkedBlockingQueue.offer(simpleLog);
    }

    public final void remove(List list) {
        ArrayList arrayList = (ArrayList) list;
        if (!arrayList.isEmpty() && this.useDatabase) {
            SQLiteDatabase writableDatabase = ((DefaultDBOpenHelper) this.dbManager.dbOpenHelper).getWritableDatabase();
            writableDatabase.beginTransaction();
            try {
                try {
                    int size = arrayList.size();
                    int i = 0;
                    while (size > 0) {
                        int i2 = 900;
                        if (size < 900) {
                            i2 = size;
                        }
                        int i3 = i + i2;
                        List subList = arrayList.subList(i, i3);
                        writableDatabase.delete("logs_v2", ("_id IN(" + new String(new char[subList.size() - 1]).replaceAll("\u0000", "?,")) + "?)", (String[]) subList.toArray(new String[0]));
                        size -= i2;
                        i = i3;
                    }
                    arrayList.clear();
                    writableDatabase.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                writableDatabase.endTransaction();
            }
        }
    }

    private Manager(DBOpenHelper dBOpenHelper) {
        this.dbManager = new DbManager(dBOpenHelper);
        this.queueManager = new QueueManager();
        this.useDatabase = true;
    }
}
