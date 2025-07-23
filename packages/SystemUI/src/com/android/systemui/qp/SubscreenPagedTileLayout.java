package com.android.systemui.qp;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelControllerBase$TileRecord;
import com.android.systemui.qs.SecPageIndicator;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SubscreenPagedTileLayout extends ViewPager implements QSPanel.QSTileLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass4 mAdapter;
    public boolean mDistributeTiles;
    public int mLastMaxHeight;
    public int mLastMaxWidth;
    public int mLayoutDirection;
    public boolean mListening;
    public final AnonymousClass3 mOnPageChangeListener;
    public int mPageHeight;
    public SecPageIndicator mPageIndicator;
    public float mPageIndicatorPosition;
    public int mPageToRestore;
    public final ArrayList mPages;
    public final ArrayList mTiles;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qp.SubscreenPagedTileLayout$4, reason: invalid class name */
    public class AnonymousClass4 extends PagerAdapter {
        public AnonymousClass4() {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final void destroyItem(ViewPager viewPager, int i, Object obj) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "destroyItem ", "SubscreenPagedTileLayout");
            viewPager.removeView((View) obj);
            int i2 = SubscreenPagedTileLayout.$r8$clinit;
            SubscreenPagedTileLayout.this.updateListening$1();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final int getCount() {
            return SubscreenPagedTileLayout.this.mPages.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final Object instantiateItem(ViewPager viewPager, int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "instantiateItem ", "SubscreenPagedTileLayout");
            SubscreenPagedTileLayout subscreenPagedTileLayout = SubscreenPagedTileLayout.this;
            if (subscreenPagedTileLayout.isLayoutRtl()) {
                i = (subscreenPagedTileLayout.mPages.size() - 1) - i;
            }
            ViewGroup viewGroup = (ViewGroup) subscreenPagedTileLayout.mPages.get(i);
            if (viewGroup.getParent() != null) {
                viewPager.removeView(viewGroup);
            }
            viewPager.addView(viewGroup);
            subscreenPagedTileLayout.updateListening$1();
            return viewGroup;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [androidx.viewpager.widget.ViewPager$OnPageChangeListener, com.android.systemui.qp.SubscreenPagedTileLayout$3] */
    public SubscreenPagedTileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTiles = new ArrayList();
        this.mPages = new ArrayList();
        this.mDistributeTiles = false;
        this.mPageToRestore = -1;
        this.mLastMaxWidth = -1;
        this.mPageHeight = -1;
        this.mLastMaxHeight = -1;
        ?? r3 = new ViewPager.SimpleOnPageChangeListener() { // from class: com.android.systemui.qp.SubscreenPagedTileLayout.3
            @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrolled(float f, int i) {
                SubscreenPagedTileLayout subscreenPagedTileLayout = SubscreenPagedTileLayout.this;
                SecPageIndicator secPageIndicator = subscreenPagedTileLayout.mPageIndicator;
                if (secPageIndicator == null) {
                    return;
                }
                float f2 = i + f;
                subscreenPagedTileLayout.mPageIndicatorPosition = f2;
                secPageIndicator.setLocation(f2);
            }

            @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageSelected(int i) {
            }
        };
        this.mOnPageChangeListener = r3;
        AnonymousClass4 anonymousClass4 = new AnonymousClass4();
        this.mAdapter = anonymousClass4;
        setAdapter(anonymousClass4);
        super.mOnPageChangeListener = r3;
        setCurrentItem(0, false);
        this.mLayoutDirection = getLayoutDirection();
    }

    @Override // androidx.viewpager.widget.ViewPager
    public final PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    public final int getColumnCount() {
        if (this.mPages.size() == 0) {
            return 0;
        }
        return ((SubscreenTileLayout) this.mPages.get(0)).mColumns;
    }

    @Override // androidx.viewpager.widget.ViewPager
    public final int getCurrentItem() {
        int i = this.mCurItem;
        return (this.mAdapter == null || !isLayoutRtl()) ? i : (this.mAdapter.getCount() - 1) - i;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final boolean isLayoutRtl() {
        return this.mLayoutDirection == 1;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        Log.d("SubscreenPagedTileLayout", "onFinishInflate");
        ArrayList arrayList = this.mPages;
        SubscreenTileLayout subscreenTileLayout = (SubscreenTileLayout) LayoutInflater.from(getContext()).inflate(R.layout.qs_subscreen_paged_page, (ViewGroup) this, false);
        subscreenTileLayout.getClass();
        subscreenTileLayout.mColumns = 4;
        arrayList.add(subscreenTileLayout);
        this.mAdapter.notifyDataSetChanged();
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (((SubscreenTileLayout) this.mPages.get(0)).getParent() == null) {
            ((SubscreenTileLayout) this.mPages.get(0)).layout(i, i2, i3, i4);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public final void onMeasure(int i, int i2) {
        this.mTiles.size();
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mPageHeight, 1073741824);
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(makeMeasureSpec, "onMeasure, heightMeasureSpec: ", " mDistributeTiles: ");
        m.append(this.mDistributeTiles);
        m.append(" mLastMaxHeight: ");
        m.append(this.mLastMaxHeight);
        m.append(" mPageHeight: ");
        m.append(this.mPageHeight);
        m.append(" mLastMaxWidth: ");
        m.append(this.mLastMaxWidth);
        m.append(" MeasureSpec.getSize: ");
        m.append(View.MeasureSpec.getSize(i));
        Log.d("SubscreenPagedTileLayout", m.toString());
        if (this.mDistributeTiles || this.mLastMaxHeight != this.mPageHeight || this.mLastMaxWidth != View.MeasureSpec.getSize(i)) {
            SubscreenTileLayout subscreenTileLayout = (SubscreenTileLayout) this.mPages.get(0);
            int i3 = this.mPageHeight;
            subscreenTileLayout.mTileLayoutHeight = i3;
            this.mLastMaxHeight = i3;
            this.mLastMaxWidth = View.MeasureSpec.getSize(i);
            SubscreenTileLayout subscreenTileLayout2 = (SubscreenTileLayout) this.mPages.get(0);
            subscreenTileLayout2.getClass();
            int size = View.MeasureSpec.getSize(i);
            int i4 = subscreenTileLayout2.mColumns;
            subscreenTileLayout2.mColumns = 4;
            StringBuilder m2 = MutableObjectList$$ExternalSyntheticOutline0.m(size, size, "updateMaxRowsAndColumns width: ", " availableWidth: ", " mColumns: ");
            m2.append(subscreenTileLayout2.mColumns);
            m2.append(" heightMeasureSpec: ");
            m2.append(View.MeasureSpec.getSize(makeMeasureSpec));
            Log.d("SubscreenTileLayout", m2.toString());
            int size2 = View.MeasureSpec.getSize(makeMeasureSpec);
            int i5 = subscreenTileLayout2.mRows;
            subscreenTileLayout2.mRows = size2 / subscreenTileLayout2.mMaxCellHeight;
            RecyclerView$$ExternalSyntheticOutline0.m(subscreenTileLayout2.mRows, "SubscreenTileLayout", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(size2, "updateMaxRows before availableHeight: ", " mRows: "));
            subscreenTileLayout2.mRows = subscreenTileLayout2.mMaxAllowedRows;
            RecyclerView$$ExternalSyntheticOutline0.m(subscreenTileLayout2.mRows, "SubscreenTileLayout", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(size2, "updateMaxRows After availableHeight: ", " mRows: "));
            if (i5 != subscreenTileLayout2.mRows || i4 != subscreenTileLayout2.mColumns || this.mDistributeTiles) {
                this.mDistributeTiles = false;
                int size3 = this.mTiles.size();
                SubscreenTileLayout subscreenTileLayout3 = (SubscreenTileLayout) this.mPages.get(0);
                int max = Math.max(size3 / Math.max(subscreenTileLayout3.mColumns * subscreenTileLayout3.mRows, 1), 1);
                SubscreenTileLayout subscreenTileLayout4 = (SubscreenTileLayout) this.mPages.get(0);
                if (size3 > Math.max(subscreenTileLayout4.mColumns * subscreenTileLayout4.mRows, 1) * max) {
                    max++;
                }
                int size4 = this.mPages.size();
                for (int i6 = 0; i6 < size4; i6++) {
                    ((SubscreenTileLayout) this.mPages.get(i6)).removeAllViews();
                }
                if (size4 != max) {
                    while (this.mPages.size() < max) {
                        Log.d("SubscreenPagedTileLayout", "Adding page");
                        ArrayList arrayList = this.mPages;
                        SubscreenTileLayout subscreenTileLayout5 = (SubscreenTileLayout) LayoutInflater.from(getContext()).inflate(R.layout.qs_subscreen_paged_page, (ViewGroup) this, false);
                        subscreenTileLayout5.getClass();
                        subscreenTileLayout5.mColumns = 4;
                        arrayList.add(subscreenTileLayout5);
                    }
                    while (this.mPages.size() > max) {
                        Log.d("SubscreenPagedTileLayout", "Removing page");
                        ArrayList arrayList2 = this.mPages;
                        arrayList2.remove(arrayList2.size() - 1);
                    }
                    SecPageIndicator secPageIndicator = this.mPageIndicator;
                    if (secPageIndicator != null) {
                        secPageIndicator.setNumPages(this.mPages.size());
                    }
                    setAdapter(this.mAdapter);
                    this.mAdapter.notifyDataSetChanged();
                    if (isLayoutRtl()) {
                        setCurrentItem(0, false);
                    }
                    int i7 = this.mPageToRestore;
                    if (i7 != -1) {
                        setCurrentItem(i7, false);
                        this.mPageToRestore = -1;
                    }
                    StringBuilder m3 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(size4, "pages count is changed (", " -> ");
                    m3.append(this.mPages.size());
                    m3.append(" ), pageRestore=");
                    RecyclerView$$ExternalSyntheticOutline0.m(this.mPageToRestore, "SubscreenPagedTileLayout", m3);
                }
                SubscreenTileLayout subscreenTileLayout6 = (SubscreenTileLayout) this.mPages.get(0);
                int max2 = Math.max(subscreenTileLayout6.mColumns * subscreenTileLayout6.mRows, 1);
                Log.d("SubscreenPagedTileLayout", "Distributing tiles");
                int size5 = this.mTiles.size();
                int i8 = 0;
                for (int i9 = 0; i9 < size5; i9++) {
                    QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = (QSPanelControllerBase$TileRecord) this.mTiles.get(i9);
                    if (((SubscreenTileLayout) this.mPages.get(i8)).mRecords.size() == max2) {
                        i8++;
                    }
                    Log.d("SubscreenPagedTileLayout", "Adding " + qSPanelControllerBase$TileRecord.tile.getClass().getSimpleName() + " to " + i8);
                    final SubscreenTileLayout subscreenTileLayout7 = (SubscreenTileLayout) this.mPages.get(i8);
                    subscreenTileLayout7.mRecords.add(qSPanelControllerBase$TileRecord);
                    qSPanelControllerBase$TileRecord.tile.setListening(subscreenTileLayout7, subscreenTileLayout7.mListening);
                    qSPanelControllerBase$TileRecord.tileView.getIcon().setOnLongClickListener(new View.OnLongClickListener(subscreenTileLayout7) { // from class: com.android.systemui.qp.SubscreenTileLayout.1
                        @Override // android.view.View.OnLongClickListener
                        public final boolean onLongClick(View view) {
                            return true;
                        }
                    });
                    QSTileView qSTileView = qSPanelControllerBase$TileRecord.tileView;
                    if (qSTileView != null) {
                        qSTileView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
                        subscreenTileLayout7.addView(qSTileView);
                    }
                }
            }
            int i10 = ((SubscreenTileLayout) this.mPages.get(0)).mRows;
            int i11 = ((SubscreenTileLayout) this.mPages.get(0)).mColumns;
            SubscreenTileLayout subscreenTileLayout8 = (SubscreenTileLayout) this.mPages.get(0);
            int size6 = View.MeasureSpec.getSize(makeMeasureSpec);
            subscreenTileLayout8.mTileLayoutHeight = size6;
            for (int i12 = 0; i12 < this.mPages.size(); i12++) {
                SubscreenTileLayout subscreenTileLayout9 = (SubscreenTileLayout) this.mPages.get(i12);
                subscreenTileLayout9.mRows = i10;
                subscreenTileLayout9.mColumns = i11;
                subscreenTileLayout9.mTileLayoutHeight = size6;
            }
        }
        super.onMeasure(i, makeMeasureSpec);
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (this.mLayoutDirection != i) {
            this.mLayoutDirection = i;
            setAdapter(this.mAdapter);
            setCurrentItem(0, false);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager
    public final void setCurrentItem(int i, boolean z) {
        if (isLayoutRtl()) {
            i = (this.mPages.size() - 1) - i;
        }
        super.setCurrentItem(i, z);
    }

    public final void setListening(boolean z, UiEventLogger uiEventLogger) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        updateListening$1();
    }

    public final void setPageIndicator(SecPageIndicator secPageIndicator) {
        ArrayList arrayList;
        this.mPageIndicator = secPageIndicator;
        if (secPageIndicator == null || (arrayList = this.mPages) == null || arrayList.size() <= 1) {
            return;
        }
        ((RelativeLayout) this.mPageIndicator.getParent()).setVisibility(0);
        this.mPageIndicator.setNumPages(this.mPages.size());
        this.mPageIndicator.setLocation(this.mPageIndicatorPosition);
        Resources resources = ((ViewGroup) this).mContext.getResources();
        int color = resources.getColor(R.color.subscreen_page_indicator_tint_color_selected);
        int color2 = resources.getColor(R.color.subscreen_page_indicator_tint_color_unselected);
        SecPageIndicator secPageIndicator2 = this.mPageIndicator;
        secPageIndicator2.mSelectedColor = color;
        secPageIndicator2.mUnselectedColor = color2;
    }

    public final void updateListening$1() {
        ArrayList arrayList = this.mPages;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            SubscreenTileLayout subscreenTileLayout = (SubscreenTileLayout) obj;
            boolean z = subscreenTileLayout.getParent() != null && this.mListening;
            if (subscreenTileLayout.mListening != z) {
                subscreenTileLayout.mListening = z;
                ArrayList arrayList2 = subscreenTileLayout.mRecords;
                int size2 = arrayList2.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList2.get(i2);
                    i2++;
                    ((QSPanelControllerBase$TileRecord) obj2).tile.setListening(subscreenTileLayout, subscreenTileLayout.mListening);
                }
            }
        }
    }

    public final boolean updateResources() {
        boolean z = false;
        for (int i = 0; i < this.mPages.size(); i++) {
            z |= ((SubscreenTileLayout) this.mPages.get(i)).updateResources();
        }
        boolean z2 = (this.mLastMaxHeight != this.mPageHeight) | z;
        if (z2) {
            this.mDistributeTiles = true;
            requestLayout();
        }
        return z2;
    }
}
