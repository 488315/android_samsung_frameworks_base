package com.android.settingslib.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class AsyncLoader extends AsyncTaskLoader {
    public Object mResult;

    public AsyncLoader(Context context) {
        super(context);
    }

    @Override // android.content.Loader
    public final void deliverResult(Object obj) {
        if (isReset()) {
            if (obj != null) {
                onDiscardResult(obj);
                return;
            }
            return;
        }
        Object obj2 = this.mResult;
        this.mResult = obj;
        if (isStarted()) {
            super.deliverResult(obj);
        }
        if (obj2 == null || obj2 == this.mResult) {
            return;
        }
        onDiscardResult(obj2);
    }

    @Override // android.content.AsyncTaskLoader
    public final void onCanceled(Object obj) {
        super.onCanceled(obj);
        if (obj != null) {
            onDiscardResult(obj);
        }
    }

    public abstract void onDiscardResult(Object obj);

    @Override // android.content.Loader
    public final void onReset() {
        super.onReset();
        cancelLoad();
        Object obj = this.mResult;
        if (obj != null) {
            onDiscardResult(obj);
        }
        this.mResult = null;
    }

    @Override // android.content.Loader
    public final void onStartLoading() {
        Object obj = this.mResult;
        if (obj != null) {
            deliverResult(obj);
        }
        if (takeContentChanged() || this.mResult == null) {
            forceLoad();
        }
    }

    @Override // android.content.Loader
    public final void onStopLoading() {
        cancelLoad();
    }
}
