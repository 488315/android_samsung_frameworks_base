package androidx.viewpager.widget;

import android.database.DataSetObservable;
import android.database.DataSetObserver;

/* loaded from: classes.dex */
public abstract class PagerAdapter {
    private final DataSetObservable mObservable = new DataSetObservable();

    public abstract int getCount();

    public abstract boolean isViewFromObject();

    public final void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.mObservable.registerObserver(dataSetObserver);
    }

    public final void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.mObservable.unregisterObserver(dataSetObserver);
    }
}
