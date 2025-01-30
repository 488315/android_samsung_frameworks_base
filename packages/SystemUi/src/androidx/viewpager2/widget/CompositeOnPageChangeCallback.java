package androidx.viewpager2.widget;

import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CompositeOnPageChangeCallback extends ViewPager2.OnPageChangeCallback {
    public final List mCallbacks;

    public CompositeOnPageChangeCallback(int i) {
        this.mCallbacks = new ArrayList(i);
    }

    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
    public final void onPageScrollStateChanged(int i) {
        try {
            Iterator it = ((ArrayList) this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((ViewPager2.OnPageChangeCallback) it.next()).onPageScrollStateChanged(i);
            }
        } catch (ConcurrentModificationException e) {
            throw new IllegalStateException("Adding and removing callbacks during dispatch to callbacks is not supported", e);
        }
    }

    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
    public final void onPageScrolled(float f, int i, int i2) {
        try {
            Iterator it = ((ArrayList) this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((ViewPager2.OnPageChangeCallback) it.next()).onPageScrolled(f, i, i2);
            }
        } catch (ConcurrentModificationException e) {
            throw new IllegalStateException("Adding and removing callbacks during dispatch to callbacks is not supported", e);
        }
    }

    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
    public final void onPageSelected(int i) {
        try {
            Iterator it = ((ArrayList) this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((ViewPager2.OnPageChangeCallback) it.next()).onPageSelected(i);
            }
        } catch (ConcurrentModificationException e) {
            throw new IllegalStateException("Adding and removing callbacks during dispatch to callbacks is not supported", e);
        }
    }
}
