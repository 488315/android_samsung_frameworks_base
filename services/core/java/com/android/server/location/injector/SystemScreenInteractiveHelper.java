package com.android.server.location.injector;

import android.content.Context;

import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SystemScreenInteractiveHelper {
    public final Context mContext;
    public boolean mReady;
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();
    public volatile boolean mIsInteractive = true;

    public SystemScreenInteractiveHelper(Context context) {
        this.mContext = context;
    }
}
