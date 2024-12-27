package com.android.server.pm;

import android.content.IntentFilter;
import com.android.server.utils.Snappable;
import com.android.server.utils.WatchableImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class WatchedIntentFilter extends WatchableImpl implements Snappable {
    public IntentFilter mFilter;

    public WatchedIntentFilter() {
        this.mFilter = new IntentFilter();
    }

    public WatchedIntentFilter(IntentFilter intentFilter) {
        this.mFilter = new IntentFilter(intentFilter);
    }

    public WatchedIntentFilter(WatchedIntentFilter watchedIntentFilter) {
        this(watchedIntentFilter.getIntentFilter$3());
    }

    public final void addAction(String str) {
        this.mFilter.addAction(str);
        dispatchChange(this);
    }

    public final void addCategory(String str) {
        this.mFilter.addCategory(str);
    }

    public final void addDataScheme(String str) {
        this.mFilter.addDataScheme(str);
        dispatchChange(this);
    }

    public final void addDataType(String str) {
        this.mFilter.addDataType(str);
        dispatchChange(this);
    }

    public IntentFilter getIntentFilter$3() {
        return this.mFilter;
    }

    @Override // com.android.server.utils.Snappable
    public WatchedIntentFilter snapshot() {
        return new WatchedIntentFilter(getIntentFilter$3());
    }
}
