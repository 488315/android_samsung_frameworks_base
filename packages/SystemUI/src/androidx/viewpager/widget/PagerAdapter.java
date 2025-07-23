package androidx.viewpager.widget;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PagerAdapter {
    public final DataSetObservable mObservable = new DataSetObservable();
    public DataSetObserver mViewPagerObserver;

    public void destroyItem(ViewPager viewPager, int i, Object obj) {
        throw new UnsupportedOperationException("Required method destroyItem was not overridden");
    }

    public abstract int getCount();

    public int getItemPosition(Object obj) {
        return -1;
    }

    public Object instantiateItem(ViewPager viewPager, int i) {
        throw new UnsupportedOperationException("Required method instantiateItem was not overridden");
    }

    public abstract boolean isViewFromObject(View view, Object obj);

    public final void notifyDataSetChanged() {
        synchronized (this) {
            try {
                DataSetObserver dataSetObserver = this.mViewPagerObserver;
                if (dataSetObserver != null) {
                    dataSetObserver.onChanged();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mObservable.notifyChanged();
    }
}
