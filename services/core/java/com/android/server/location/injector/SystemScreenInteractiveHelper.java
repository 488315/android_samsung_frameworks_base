package com.android.server.location.injector;

import android.content.Context;

import java.util.concurrent.CopyOnWriteArrayList;

public final class SystemScreenInteractiveHelper {
    public final Context mContext;
    public boolean mReady;
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();
    public volatile boolean mIsInteractive = true;

    public SystemScreenInteractiveHelper(Context context) {
        this.mContext = context;
    }
}
