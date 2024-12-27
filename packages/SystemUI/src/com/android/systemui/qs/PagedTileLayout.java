package com.android.systemui.qs;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Scroller;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.log.ConstantStringsLoggerImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.HeightOverrideable;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PagedTileLayout extends ViewPager implements SecQSPanel.QSTileLayout {
    public static final PagedTileLayout$$ExternalSyntheticLambda0 SCROLL_CUBIC = new PagedTileLayout$$ExternalSyntheticLambda0();
    public final AnonymousClass3 mAdapter;
    public boolean mDistributeTiles;
    public int mExcessHeight;
    public int mLastExcessHeight;
    public int mLastMaxHeight;
    public int mLayoutDirection;
    public int mLayoutOrientation;
    public boolean mListening;
    public QSLogger mLogger;
    public final int mMaxColumns;
    public final int mMinRows;
    public final AnonymousClass2 mOnPageChangeListener;
    public int mPageToRestore;
    public final ArrayList mPages;
    Scroller mScroller;
    public final ArrayList mTiles;
    public final UiEventLogger mUiEventLogger;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.qs.PagedTileLayout$3, reason: invalid class name */
    public final class AnonymousClass3 extends PagerAdapter {
        public AnonymousClass3() {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            PagedTileLayout pagedTileLayout = PagedTileLayout.this;
            pagedTileLayout.mLogger.d(Integer.valueOf(i), "Destantiating page at");
            viewGroup.removeView((View) obj);
            pagedTileLayout.updateListening();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final int getCount() {
            return PagedTileLayout.this.mPages.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final Object instantiateItem(int i, ViewGroup viewGroup) {
            PagedTileLayout pagedTileLayout = PagedTileLayout.this;
            pagedTileLayout.mLogger.d(Integer.valueOf(i), "Instantiating page at");
            if (pagedTileLayout.isLayoutRtl()) {
                i = (pagedTileLayout.mPages.size() - 1) - i;
            }
            ViewGroup viewGroup2 = (ViewGroup) pagedTileLayout.mPages.get(i);
            if (viewGroup2.getParent() != null) {
                viewGroup.removeView(viewGroup2);
            }
            viewGroup.addView(viewGroup2);
            pagedTileLayout.updateListening();
            return viewGroup2;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
    }

    public PagedTileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTiles = new ArrayList();
        this.mPages = new ArrayList();
        this.mDistributeTiles = false;
        this.mPageToRestore = -1;
        QSEvents.INSTANCE.getClass();
        this.mUiEventLogger = QSEvents.qsUiEventsLogger;
        this.mMinRows = 1;
        this.mMaxColumns = 100;
        ActivityManager.isRunningInTestHarness();
        this.mLastMaxHeight = -1;
        ViewPager.SimpleOnPageChangeListener simpleOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() { // from class: com.android.systemui.qs.PagedTileLayout.2
            public int mCurrentScrollState = 0;
            public boolean mIsScrollJankTraceBegin = false;

            @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrollStateChanged(int i) {
                if (i != this.mCurrentScrollState && i == 0) {
                    InteractionJankMonitor.getInstance().end(6);
                    this.mIsScrollJankTraceBegin = false;
                }
                this.mCurrentScrollState = i;
            }

            @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrolled(float f, int i) {
                boolean z = this.mIsScrollJankTraceBegin;
                PagedTileLayout pagedTileLayout = PagedTileLayout.this;
                if (!z && this.mCurrentScrollState == 1) {
                    InteractionJankMonitor.getInstance().begin(pagedTileLayout, 6);
                    this.mIsScrollJankTraceBegin = true;
                }
                PagedTileLayout$$ExternalSyntheticLambda0 pagedTileLayout$$ExternalSyntheticLambda0 = PagedTileLayout.SCROLL_CUBIC;
                pagedTileLayout.getClass();
            }

            @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageSelected(int i) {
                PagedTileLayout$$ExternalSyntheticLambda0 pagedTileLayout$$ExternalSyntheticLambda0 = PagedTileLayout.SCROLL_CUBIC;
                PagedTileLayout pagedTileLayout = PagedTileLayout.this;
                pagedTileLayout.setImportantForAccessibility(4);
                if (pagedTileLayout.isLayoutRtl()) {
                    pagedTileLayout.mPages.size();
                }
                for (int i2 = 0; i2 < pagedTileLayout.mPages.size(); i2++) {
                    TileLayout tileLayout = (TileLayout) pagedTileLayout.mPages.get(i2);
                    tileLayout.setSelected(false);
                    if (tileLayout.isSelected()) {
                        for (int i3 = 0; i3 < tileLayout.mRecords.size(); i3++) {
                            QSTile qSTile = ((SecQSPanelControllerBase.TileRecord) tileLayout.mRecords.get(i3)).tile;
                            pagedTileLayout.mUiEventLogger.logWithInstanceId(QSEvent.QS_TILE_VISIBLE, 0, qSTile.getMetricsSpec(), qSTile.getInstanceId());
                        }
                    }
                }
                pagedTileLayout.setImportantForAccessibility(0);
            }
        };
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mAdapter = anonymousClass3;
        this.mScroller = new Scroller(context, SCROLL_CUBIC);
        setAdapter(anonymousClass3);
        super.mOnPageChangeListener = simpleOnPageChangeListener;
        setCurrentItem(0, false);
        this.mLayoutOrientation = getResources().getConfiguration().orientation;
        this.mLayoutDirection = getLayoutDirection();
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void addTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        this.mTiles.add(tileRecord);
        this.mLogger.d("adding new tile", "forcing tile redistribution across pages, reason");
        this.mDistributeTiles = true;
        requestLayout();
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public final void computeScroll() {
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            if (!this.mFakeDragging && !this.mIsBeingDragged) {
                this.mFakeDragging = true;
                setScrollState(1);
                this.mLastMotionX = 0.0f;
                this.mInitialMotionX = 0.0f;
                VelocityTracker velocityTracker = this.mVelocityTracker;
                if (velocityTracker == null) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker.clear();
                }
                long uptimeMillis = SystemClock.uptimeMillis();
                MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, 0.0f, 0.0f, 0);
                this.mVelocityTracker.addMovement(obtain);
                obtain.recycle();
                this.mFakeDragBeginTime = uptimeMillis;
            }
            try {
                super.fakeDragBy(getScrollX() - this.mScroller.getCurrX());
                postInvalidateOnAnimation();
            } catch (NullPointerException e) {
                this.mLogger.logException("FakeDragBy called before begin", e);
                final int size = this.mPages.size() - 1;
                post(new Runnable() { // from class: com.android.systemui.qs.PagedTileLayout$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PagedTileLayout pagedTileLayout = PagedTileLayout.this;
                        int i = size;
                        PagedTileLayout$$ExternalSyntheticLambda0 pagedTileLayout$$ExternalSyntheticLambda0 = PagedTileLayout.SCROLL_CUBIC;
                        pagedTileLayout.setCurrentItem(i, true);
                        pagedTileLayout.setOffscreenPageLimit();
                    }
                });
            }
        } else if (this.mFakeDragging) {
            try {
                super.endFakeDrag();
            } catch (NullPointerException e2) {
                this.mLogger.logException("endFakeDrag called without velocityTracker", e2);
            }
            setOffscreenPageLimit();
        }
        super.computeScroll();
    }

    public final TileLayout createTileLayout() {
        TileLayout tileLayout = (TileLayout) LayoutInflater.from(getContext()).inflate(R.layout.qs_paged_page, (ViewGroup) this, false);
        int i = this.mMinRows;
        if (tileLayout.mMinRows != i) {
            tileLayout.mMinRows = i;
            tileLayout.updateResources();
        }
        int i2 = this.mMaxColumns;
        tileLayout.mMaxColumns = i2;
        tileLayout.mColumns = Math.min(tileLayout.mResourceColumns, i2);
        tileLayout.setSelected(false);
        float f = this.mPages.isEmpty() ? 1.0f : ((TileLayout) this.mPages.get(0)).mSquishinessFraction;
        if (Float.compare(tileLayout.mSquishinessFraction, f) != 0) {
            tileLayout.mSquishinessFraction = f;
            tileLayout.layoutTileRecords(tileLayout.mRecords.size(), false);
            Iterator it = tileLayout.mRecords.iterator();
            while (it.hasNext()) {
                ViewParent viewParent = ((SecQSPanelControllerBase.TileRecord) it.next()).tileView;
                if (viewParent instanceof HeightOverrideable) {
                    float f2 = tileLayout.mSquishinessFraction;
                    QSTileViewImpl qSTileViewImpl = (QSTileViewImpl) ((HeightOverrideable) viewParent);
                    if (qSTileViewImpl.squishinessFraction != f2) {
                        qSTileViewImpl.squishinessFraction = f2;
                        qSTileViewImpl.updateHeight();
                    }
                }
            }
        }
        return tileLayout;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int size = this.mPages.size();
        for (int i = 0; i < size; i++) {
            View view = (View) this.mPages.get(i);
            if (view.getParent() == null) {
                view.dispatchConfigurationChanged(configuration);
            }
        }
        int i2 = this.mLayoutOrientation;
        int i3 = configuration.orientation;
        if (i2 == i3) {
            this.mLogger.d(configuration, "Orientation didn't change, tiles might be not redistributed, new config");
            return;
        }
        this.mLayoutOrientation = i3;
        this.mLogger.d("orientation changed to " + this.mLayoutOrientation, "forcing tile redistribution across pages, reason");
        this.mDistributeTiles = true;
        setCurrentItem(0, false);
        this.mPageToRestore = 0;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mPages.add(createTileLayout());
        this.mAdapter.notifyDataSetChanged();
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        AnonymousClass3 anonymousClass3 = this.mAdapter;
        if (anonymousClass3 == null || anonymousClass3.getCount() <= 0) {
            return;
        }
        accessibilityEvent.setItemCount(this.mAdapter.getCount());
        boolean isLayoutRtl = isLayoutRtl();
        int i = this.mCurItem;
        if (isLayoutRtl) {
            i = (this.mPages.size() - 1) - i;
        }
        accessibilityEvent.setFromIndex(i);
        boolean isLayoutRtl2 = isLayoutRtl();
        int i2 = this.mCurItem;
        if (isLayoutRtl2) {
            i2 = (this.mPages.size() - 1) - i2;
        }
        accessibilityEvent.setToIndex(i2);
    }

    public final void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.mCurItem != 0) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_LEFT);
        }
        if (this.mCurItem != this.mPages.size() - 1) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_RIGHT);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (((TileLayout) this.mPages.get(0)).getParent() == null) {
            ((TileLayout) this.mPages.get(0)).layout(i, i2, i3, i4);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public final void onMeasure(int i, int i2) {
        int size = this.mTiles.size();
        if (this.mDistributeTiles || this.mLastMaxHeight != View.MeasureSpec.getSize(i2) || this.mLastExcessHeight != this.mExcessHeight) {
            int size2 = View.MeasureSpec.getSize(i2);
            this.mLastMaxHeight = size2;
            int i3 = this.mExcessHeight;
            this.mLastExcessHeight = i3;
            if (((TileLayout) this.mPages.get(0)).updateMaxRows(size2 - i3, size) || this.mDistributeTiles) {
                this.mDistributeTiles = false;
                int size3 = this.mTiles.size();
                TileLayout tileLayout = (TileLayout) this.mPages.get(0);
                int max = Math.max(size3 / Math.max(tileLayout.mColumns * tileLayout.mRows, 1), 1);
                TileLayout tileLayout2 = (TileLayout) this.mPages.get(0);
                if (size3 > Math.max(tileLayout2.mColumns * tileLayout2.mRows, 1) * max) {
                    max++;
                }
                int size4 = this.mPages.size();
                for (int i4 = 0; i4 < size4; i4++) {
                    ((TileLayout) this.mPages.get(i4)).removeAllViews();
                }
                if (size4 != max) {
                    while (this.mPages.size() < max) {
                        ConstantStringsLoggerImpl constantStringsLoggerImpl = this.mLogger.$$delegate_0;
                        constantStringsLoggerImpl.getClass();
                        constantStringsLoggerImpl.buffer.log(constantStringsLoggerImpl.tag, LogLevel.DEBUG, "Adding new page", null);
                        this.mPages.add(createTileLayout());
                    }
                    while (this.mPages.size() > max) {
                        ConstantStringsLoggerImpl constantStringsLoggerImpl2 = this.mLogger.$$delegate_0;
                        constantStringsLoggerImpl2.getClass();
                        constantStringsLoggerImpl2.buffer.log(constantStringsLoggerImpl2.tag, LogLevel.DEBUG, "Removing page", null);
                        ArrayList arrayList = this.mPages;
                        arrayList.remove(arrayList.size() - 1);
                    }
                    setAdapter(this.mAdapter);
                    this.mAdapter.notifyDataSetChanged();
                    int i5 = this.mPageToRestore;
                    if (i5 != -1) {
                        setCurrentItem(i5, false);
                        this.mPageToRestore = -1;
                    }
                }
                TileLayout tileLayout3 = (TileLayout) this.mPages.get(0);
                int max2 = Math.max(tileLayout3.mColumns * tileLayout3.mRows, 1);
                int size5 = this.mTiles.size();
                this.mLogger.logTileDistributionInProgress(max2, size5);
                int i6 = 0;
                for (int i7 = 0; i7 < size5; i7++) {
                    SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) this.mTiles.get(i7);
                    if (((TileLayout) this.mPages.get(i6)).mRecords.size() == max2) {
                        i6++;
                    }
                    this.mLogger.logTileDistributed(i6, tileRecord.tile.getClass().getSimpleName());
                    ((TileLayout) this.mPages.get(i6)).addTile(tileRecord);
                }
            }
            int i8 = ((TileLayout) this.mPages.get(0)).mRows;
            for (int i9 = 0; i9 < this.mPages.size(); i9++) {
                ((TileLayout) this.mPages.get(i9)).mRows = i8;
            }
        }
        super.onMeasure(i, i2);
        int childCount = getChildCount();
        int i10 = 0;
        for (int i11 = 0; i11 < childCount; i11++) {
            int measuredHeight = getChildAt(i11).getMeasuredHeight();
            if (measuredHeight > i10) {
                i10 = measuredHeight;
            }
        }
        if (((TileLayout) this.mPages.get(0)).getParent() == null) {
            ((TileLayout) this.mPages.get(0)).measure(i, i2);
            int measuredHeight2 = ((TileLayout) this.mPages.get(0)).getMeasuredHeight();
            if (measuredHeight2 > i10) {
                i10 = measuredHeight2;
            }
        }
        setMeasuredDimension(getMeasuredWidth(), getPaddingBottom() + i10);
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        boolean z = this.mLayoutDirection == 1;
        int i2 = this.mCurItem;
        if (z) {
            i2 = (this.mPages.size() - 1) - i2;
        }
        super.onRtlPropertiesChanged(i);
        if (this.mLayoutDirection != i) {
            this.mLayoutDirection = i;
            setAdapter(this.mAdapter);
            setCurrentItem(i2, false);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x001e, code lost:
    
        r5 = 4096;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean performAccessibilityAction(int r5, android.os.Bundle r6) {
        /*
            r4 = this;
            android.view.accessibility.AccessibilityNodeInfo$AccessibilityAction r0 = android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_LEFT
            int r0 = r0.getId()
            android.view.accessibility.AccessibilityNodeInfo$AccessibilityAction r1 = android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_RIGHT
            int r1 = r1.getId()
            r2 = 8192(0x2000, float:1.14794E-41)
            r3 = 4096(0x1000, float:5.74E-42)
            if (r5 == r0) goto L14
            if (r5 != r1) goto L23
        L14:
            boolean r1 = r4.isLayoutRtl()
            if (r1 != 0) goto L20
            if (r5 != r0) goto L1e
        L1c:
            r5 = r2
            goto L23
        L1e:
            r5 = r3
            goto L23
        L20:
            if (r5 != r0) goto L1c
            goto L1e
        L23:
            boolean r6 = super.performAccessibilityAction(r5, r6)
            if (r6 == 0) goto L30
            if (r5 == r2) goto L2d
            if (r5 != r3) goto L30
        L2d:
            r4.requestAccessibilityFocus()
        L30:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.PagedTileLayout.performAccessibilityAction(int, android.os.Bundle):boolean");
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void removeTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        if (this.mTiles.remove(tileRecord)) {
            this.mLogger.d("removing tile", "forcing tile redistribution across pages, reason");
            this.mDistributeTiles = true;
            requestLayout();
        }
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void restoreInstanceState(Bundle bundle) {
        this.mPageToRestore = bundle.getInt("current_page", -1);
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void saveInstanceState(Bundle bundle) {
        int i = this.mPageToRestore;
        if (i == -1) {
            boolean isLayoutRtl = isLayoutRtl();
            int i2 = this.mCurItem;
            i = isLayoutRtl ? (this.mPages.size() - 1) - i2 : i2;
        }
        bundle.putInt("current_page", i);
    }

    @Override // androidx.viewpager.widget.ViewPager
    public final void setCurrentItem(int i, boolean z) {
        if (isLayoutRtl()) {
            i = (this.mPages.size() - 1) - i;
        }
        super.setCurrentItem(i, z);
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void setListening(boolean z, UiEventLogger uiEventLogger) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        updateListening();
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void setLogger(QSLogger qSLogger) {
        this.mLogger = qSLogger;
    }

    @Override // androidx.viewpager.widget.ViewPager
    public final void setPageMargin(int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        int i2 = -i;
        marginLayoutParams.setMarginStart(i2);
        marginLayoutParams.setMarginEnd(i2);
        setLayoutParams(marginLayoutParams);
        int size = this.mPages.size();
        for (int i3 = 0; i3 < size; i3++) {
            View view = (View) this.mPages.get(i3);
            view.setPadding(i, view.getPaddingTop(), i, view.getPaddingBottom());
        }
    }

    public final void updateListening() {
        Iterator it = this.mPages.iterator();
        while (it.hasNext()) {
            TileLayout tileLayout = (TileLayout) it.next();
            tileLayout.setListening(tileLayout.getParent() != null && this.mListening, null);
        }
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final boolean updateResources() {
        boolean z = false;
        for (int i = 0; i < this.mPages.size(); i++) {
            z |= ((TileLayout) this.mPages.get(i)).updateResources();
        }
        if (z) {
            this.mLogger.d("resources in pages changed", "forcing tile redistribution across pages, reason");
            this.mDistributeTiles = true;
            requestLayout();
        } else {
            ConstantStringsLoggerImpl constantStringsLoggerImpl = this.mLogger.$$delegate_0;
            constantStringsLoggerImpl.getClass();
            constantStringsLoggerImpl.buffer.log(constantStringsLoggerImpl.tag, LogLevel.DEBUG, "resource in pages didn't change, tiles might be not redistributed", null);
        }
        return z;
    }
}
