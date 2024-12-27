package com.samsung.android.globalactions.util;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class ContentObserverWrapper {
    private final Context mContext;
    private ArrayList<ContentObserver> mObserverList = new ArrayList<>();

    public ContentObserverWrapper(Context context) {
        this.mContext = context;
    }

    public void registerObserver(Uri uri, final Runnable runnable) {
        ContentObserver contentObserver = new ContentObserver(new Handler()) { // from class: com.samsung.android.globalactions.util.ContentObserverWrapper.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                runnable.run();
            }
        };
        this.mContext.getContentResolver().registerContentObserver(uri, false, contentObserver);
        this.mObserverList.add(contentObserver);
    }

    public void unregisterObservers() {
        Iterator<ContentObserver> it = this.mObserverList.iterator();
        while (it.hasNext()) {
            ContentObserver contentObserver = it.next();
            this.mContext.getContentResolver().unregisterContentObserver(contentObserver);
        }
    }
}
