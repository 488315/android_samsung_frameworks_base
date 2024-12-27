package com.android.server.pm;

import android.content.IntentFilter;

import com.android.server.utils.Snappable;
import com.android.server.utils.WatchableImpl;

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
