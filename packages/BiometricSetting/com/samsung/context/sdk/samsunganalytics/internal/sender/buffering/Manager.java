package com.samsung.context.sdk.samsunganalytics.internal.sender.buffering;

import android.content.Context;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database.DbManager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.queue.QueueManager;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes.dex */
public final class Manager {
    private static Manager instance;
    private DbManager dbManager;
    private QueueManager queueManager;
    private boolean useDatabase;

    private Manager() {
        throw null;
    }

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
                configuration.getClass();
                if (PolicyUtils.getSenderType() != 0) {
                    instance = new Manager(context, false);
                } else if (context.getSharedPreferences("SamsungAnalyticsPrefs", 0).getString("lgt", "").equals("rtb")) {
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

    public final void enableDatabaseBuffering(DbManager dbManager) {
        this.useDatabase = true;
        this.dbManager = dbManager;
        if (this.queueManager.getAll().isEmpty()) {
            return;
        }
        Iterator it = ((LinkedBlockingQueue) this.queueManager.getAll()).iterator();
        while (it.hasNext()) {
            this.dbManager.insert((SimpleLog) it.next());
        }
        ((LinkedBlockingQueue) this.queueManager.getAll()).clear();
    }

    public final Queue<SimpleLog> get(int i) {
        Queue<SimpleLog> all;
        if (this.useDatabase) {
            delete();
            all = i <= 0 ? this.dbManager.selectAll() : this.dbManager.selectSome(i);
        } else {
            all = this.queueManager.getAll();
        }
        if (!all.isEmpty()) {
            StringBuilder sb = new StringBuilder("get log from ");
            sb.append(this.useDatabase ? "Database " : "Queue ");
            sb.append("(");
            sb.append(all.size());
            sb.append(")");
            Debug.LogENG(sb.toString());
        }
        return all;
    }

    public final void insert(SimpleLog simpleLog) {
        if (this.useDatabase) {
            this.dbManager.insert(simpleLog);
        } else {
            this.queueManager.insert(simpleLog);
        }
    }

    public final boolean isEnabledDatabaseBuffering() {
        return this.useDatabase;
    }

    public final void remove(List<String> list) {
        if (!((ArrayList) list).isEmpty() && this.useDatabase) {
            this.dbManager.delete(list);
        }
    }
}
