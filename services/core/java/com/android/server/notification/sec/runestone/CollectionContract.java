package com.android.server.notification.sec.runestone;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class CollectionContract {
    public static final Uri CONTENT_URI = Uri.parse("content://com.samsung.android.rubin.collection/");

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class API {
        public static void putLogs(Context context, List list) {
            if (context == null) {
                throw new IllegalArgumentException("context is null");
            }
            ArrayList arrayList = (ArrayList) list;
            if (arrayList.isEmpty()) {
                throw new IllegalArgumentException("log is null or empty");
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                CollectionContract$Notification$Log collectionContract$Notification$Log = (CollectionContract$Notification$Log) it.next();
                HashMap hashMap = new HashMap();
                hashMap.put("package", collectionContract$Notification$Log.pkg);
                hashMap.put("id", String.valueOf(collectionContract$Notification$Log.id));
                hashMap.put("category", collectionContract$Notification$Log.category);
                hashMap.put("channel_id", collectionContract$Notification$Log.channelId);
                hashMap.put("cancel_reason", String.valueOf(collectionContract$Notification$Log.cancelReason));
                hashMap.put("tag", String.valueOf(collectionContract$Notification$Log.tag));
                hashMap.put("enqueued_time_ms", String.valueOf(collectionContract$Notification$Log.enqueuedTimeMs));
                hashMap.put("canceled_time_ms", String.valueOf(collectionContract$Notification$Log.canceledTimeMs));
                hashMap.put("first_expanded_time_ms", String.valueOf(collectionContract$Notification$Log.firstExpandedTimeMs));
                hashMap.put("first_shown_time_ms", String.valueOf(collectionContract$Notification$Log.firstShownTimeMs));
                arrayList2.add(hashMap);
            }
            Bundle bundle = new Bundle();
            bundle.putString("log_id", "notification");
            bundle.putSerializable("logs", arrayList2);
            bundle.putLong("r_ts", System.currentTimeMillis());
            context.getContentResolver().call(CollectionContract.CONTENT_URI, "put_logs", (String) null, bundle);
        }
    }
}
