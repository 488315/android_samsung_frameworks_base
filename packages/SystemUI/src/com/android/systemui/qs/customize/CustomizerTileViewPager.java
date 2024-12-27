package com.android.systemui.qs.customize;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.SecPageIndicator;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.customize.SecQSCustomizerAnimator;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomizerTileViewPager extends ViewPager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mAdapter;
    public View.OnClickListener mClickListener;
    public int mColumns;
    public final Context mContext;
    public final AnonymousClass2 mDistribute;
    public View.OnDragListener mDragListener;
    public int mInitialPagenum;
    public final ArrayList mInitialTiles;
    public boolean mIsAvailableArea;
    public boolean mIsMultiTouch;
    public boolean mIsScrollView;
    public boolean mIsTopEdit;
    public SecPageIndicator mPageIndicator;
    public float mPageIndicatorPosition;
    public final ArrayList mPages;
    public View mParentContainer;
    public int mRows;
    public boolean mShowLabel;

    /* renamed from: com.android.systemui.qs.customize.CustomizerTileViewPager$1, reason: invalid class name */
    public final class AnonymousClass1 extends PagerAdapter {
        public AnonymousClass1() {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Destantiating ", "CSTMPagedTileLayout");
            viewGroup.removeView((View) obj);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final int getCount() {
            return CustomizerTileViewPager.this.mPages.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final int getItemPosition(Object obj) {
            CustomizerTileViewPager customizerTileViewPager = CustomizerTileViewPager.this;
            int indexOf = customizerTileViewPager.mPages.indexOf(obj);
            if (indexOf == -1) {
                return -2;
            }
            return customizerTileViewPager.isLayoutRtl() ? (customizerTileViewPager.mPages.size() - 1) - indexOf : indexOf;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final Object instantiateItem(int i, ViewGroup viewGroup) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Instantiating ", "CSTMPagedTileLayout");
            CustomizerTileViewPager customizerTileViewPager = CustomizerTileViewPager.this;
            if (customizerTileViewPager.isLayoutRtl()) {
                i = (customizerTileViewPager.mPages.size() - 1) - i;
            }
            ViewGroup viewGroup2 = (ViewGroup) customizerTileViewPager.mPages.get(i);
            viewGroup.addView(viewGroup2);
            return viewGroup2;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
    }

    /* renamed from: com.android.systemui.qs.customize.CustomizerTileViewPager$4, reason: invalid class name */
    public final class AnonymousClass4 {
        public AnonymousClass4() {
        }
    }

    public class CustomizerTilePage extends CustomizerTileLayout {
        public int mCurrentRows;

        public CustomizerTilePage(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public final boolean updateResources() {
            boolean z;
            Log.d("CSTMPagedTileLayout", "updateResources 2");
            if (QpRune.QUICK_TABLET) {
                z = this.mMaxRows != this.mCurrentRows;
                if (z) {
                    requestLayout();
                }
            } else {
                z = false;
            }
            this.mCellWidth = withDefaultDensity(R.dimen.qs_edit_tile_expanded_width);
            if (!this.mIsTopEdit && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isQSButtonGridPopupEnabled()) {
                this.mCellWidth = Math.min(this.mCellWidth, this.mResourcePicker.resourcePickHelper.getTargetPicker().getTileExpandedWidth(this.mContext));
            }
            this.mCellHeight = withDefaultDensity(R.dimen.qs_edit_tile_icon_frame_size) + (this.mShowLabel ? withDefaultDensity(R.dimen.qs_edit_tile_label_height) : 0);
            return z;
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.qs.customize.CustomizerTileViewPager$2] */
    public CustomizerTileViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsMultiTouch = false;
        this.mIsAvailableArea = false;
        this.mShowLabel = true;
        this.mIsTopEdit = false;
        this.mInitialTiles = new ArrayList();
        this.mPages = new ArrayList();
        this.mInitialPagenum = 0;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mAdapter = anonymousClass1;
        this.mColumns = 4;
        this.mRows = 2;
        this.mDistribute = new Runnable() { // from class: com.android.systemui.qs.customize.CustomizerTileViewPager.2
            @Override // java.lang.Runnable
            public final void run() {
                CustomizerTileViewPager customizerTileViewPager = CustomizerTileViewPager.this;
                int i = CustomizerTileViewPager.$r8$clinit;
                customizerTileViewPager.distributeTiles$2();
            }
        };
        this.mContext = context;
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        updateResources();
        setAdapter(anonymousClass1);
        addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.android.systemui.qs.customize.CustomizerTileViewPager.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrolled(float f, int i) {
                CustomizerTileViewPager customizerTileViewPager = CustomizerTileViewPager.this;
                SecPageIndicator secPageIndicator = customizerTileViewPager.mPageIndicator;
                if (secPageIndicator == null) {
                    return;
                }
                float f2 = i + f;
                secPageIndicator.setLocation(f2);
                customizerTileViewPager.mPageIndicatorPosition = f2;
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageSelected(int i) {
                CustomizerTileViewPager customizerTileViewPager = CustomizerTileViewPager.this;
                if (customizerTileViewPager.mPageIndicator == null) {
                    return;
                }
                customizerTileViewPager.getClass();
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrollStateChanged(int i) {
            }
        });
        setCurrentItem(0, false);
        boolean z = QpRune.QUICK_TABLET;
        setHorizontalFadingEdgeEnabled(z);
        setFadingEdgeLength(z ? (int) (secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getPanelWidth(context) * 0.0192f) : 0);
    }

    public final void addTile(CustomTileInfo customTileInfo, int i) {
        Log.d("CSTMPagedTileLayout", "addTile: " + customTileInfo);
        int currentItem = getCurrentItem();
        int size = this.mPages.size();
        boolean z = i >= 9999;
        if (z && (currentItem = this.mPages.size() - 1) < 0) {
            currentItem = 0;
        }
        if (!this.mIsScrollView) {
            int i2 = size - 1;
            while (true) {
                if (i2 < currentItem) {
                    break;
                }
                CustomizerTilePage customizerTilePage = (CustomizerTilePage) this.mPages.get(i2);
                if (!customizerTilePage.mIsScrollView && customizerTilePage.mCustomTilesInfo.size() >= customizerTilePage.mColumns * customizerTilePage.mMaxRows) {
                    if (i2 == this.mAdapter.getCount() - 1) {
                        CustomizerTilePage customizerTilePage2 = (CustomizerTilePage) LayoutInflater.from(this.mContext).inflate(R.layout.qs_customizer_paged_page, (ViewGroup) this, false);
                        customizerTilePage2.mIsTopEdit = this.mIsTopEdit;
                        customizerTilePage2.mClickListener = this.mClickListener;
                        int i3 = this.mRows;
                        int i4 = this.mColumns;
                        if (QpRune.QUICK_TABLET) {
                            customizerTilePage2.mCurrentRows = customizerTilePage2.mMaxRows;
                        }
                        customizerTilePage2.mMaxRows = i3;
                        customizerTilePage2.mColumns = i4;
                        customizerTilePage2.mShowLabel = this.mShowLabel;
                        customizerTilePage2.addBackgroundBox(i3, i4, this.mDragListener);
                        customizerTilePage2.updateResources();
                        this.mPages.add(customizerTilePage2);
                        int currentItem2 = getCurrentItem();
                        SecPageIndicator secPageIndicator = this.mPageIndicator;
                        if (secPageIndicator != null) {
                            AnonymousClass1 anonymousClass1 = this.mAdapter;
                            secPageIndicator.setNumPages(anonymousClass1 != null ? anonymousClass1.getCount() : 0);
                            this.mPageIndicator.setLocation(currentItem2);
                        }
                        this.mAdapter.notifyDataSetChanged();
                        if (z && i < 19998) {
                            currentItem++;
                            setCurrentItem(currentItem, false);
                            break;
                        } else {
                            CustomTileInfo customTileInfo2 = (CustomTileInfo) AlertController$$ExternalSyntheticOutline0.m(((CustomizerTilePage) this.mPages.get(i2)).mCustomTilesInfo, 1);
                            ((CustomizerTilePage) this.mPages.get(i2 + 1)).addTile(customTileInfo2, 0, false);
                            ((CustomizerTilePage) this.mPages.get(i2)).removeTile(customTileInfo2, false);
                        }
                    } else {
                        CustomTileInfo customTileInfo3 = (CustomTileInfo) AlertController$$ExternalSyntheticOutline0.m(((CustomizerTilePage) this.mPages.get(i2)).mCustomTilesInfo, 1);
                        ((CustomizerTilePage) this.mPages.get(i2 + 1)).addTile(customTileInfo3, 0, false);
                        ((CustomizerTilePage) this.mPages.get(i2)).removeTile(customTileInfo3, false);
                    }
                }
                i2--;
            }
        }
        if (size > 0) {
            ((CustomizerTilePage) this.mPages.get(currentItem)).addTile(customTileInfo, i, true);
        }
    }

    public final void addTiles(ArrayList arrayList) {
        this.mInitialTiles.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            this.mInitialTiles.add((CustomTileInfo) arrayList.get(i));
        }
        Log.d("CSTMPagedTileLayout", this.mInitialTiles.size() + " tiles added");
        removeCallbacks(this.mDistribute);
        post(this.mDistribute);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mIsMultiTouch && this.mIsAvailableArea) {
            return true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public final void distributeTiles$2() {
        int size = this.mPages.size();
        int size2 = this.mInitialTiles.size();
        ArrayList arrayList = new ArrayList();
        removeAllViews();
        if (size == 0) {
            this.mPages.add(makePage());
        }
        int i = 0;
        for (int i2 = 0; i2 < size2; i2++) {
            CustomTileInfo customTileInfo = (CustomTileInfo) this.mInitialTiles.get(i2);
            CustomizerTilePage customizerTilePage = (CustomizerTilePage) this.mPages.get(i);
            if (!customizerTilePage.mIsScrollView && customizerTilePage.mCustomTilesInfo.size() >= customizerTilePage.mColumns * customizerTilePage.mMaxRows && (i = i + 1) == this.mPages.size()) {
                this.mPages.add(makePage());
            }
            ((CustomizerTilePage) this.mPages.get(i)).addTile(customTileInfo);
            arrayList.add(customTileInfo);
        }
        int i3 = i + 1;
        if (this.mPages.size() != i3) {
            while (i3 < this.mPages.size()) {
                Log.d("CSTMPagedTileLayout", "mPages.remove");
                this.mPages.remove(r1.size() - 1);
            }
            SecPageIndicator secPageIndicator = this.mPageIndicator;
            if (secPageIndicator != null) {
                secPageIndicator.setNumPages(this.mPages.size());
            }
            setAdapter(this.mAdapter);
            this.mAdapter.notifyDataSetChanged();
            setCurrentItem(0, false);
        }
        for (int i4 = 0; i4 < this.mPages.size(); i4++) {
            ((CustomizerTilePage) this.mPages.get(i4)).addBackgroundBox(this.mRows, this.mColumns, this.mDragListener);
        }
        SecPageIndicator secPageIndicator2 = this.mPageIndicator;
        if (secPageIndicator2 != null) {
            secPageIndicator2.setNumPages(this.mPages.size());
        }
        setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
        setCurrentItem(this.mInitialPagenum, false);
        SecQSCustomizerAnimator.Companion companion = SecQSCustomizerAnimator.Companion;
        CustomizerTilePage customizerTilePage2 = (CustomizerTilePage) this.mPages.get(0);
        companion.getClass();
        if (customizerTilePage2 == null) {
            return;
        }
        customizerTilePage2.getChildAt(0);
        int childCount = customizerTilePage2.getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View findViewById = customizerTilePage2.getChildAt(i5).findViewById(SecQSSettingEditResources.REMOVE_ICON_ID);
            if (findViewById != null) {
                findViewById.setScaleX(0.5f);
                findViewById.setScaleY(0.5f);
                findViewById.setAlpha(0.0f);
                findViewById.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200L).setStartDelay(200L).start();
            }
        }
    }

    public final int getColumnCount() {
        if (this.mPages.size() == 0) {
            return 0;
        }
        return ((CustomizerTilePage) this.mPages.get(0)).mColumns;
    }

    @Override // androidx.viewpager.widget.ViewPager
    public final int getCurrentItem() {
        int i = this.mCurItem;
        return (this.mAdapter == null || !isLayoutRtl()) ? i : (this.mAdapter.getCount() - 1) - i;
    }

    @Override // android.view.View
    public final float getRightFadingEdgeStrength() {
        return 1.0f;
    }

    public final List getSpec() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mPages.size(); i++) {
            Iterator it = ((CustomizerTilePage) this.mPages.get(i)).mCustomTilesInfo.iterator();
            while (it.hasNext()) {
                arrayList.add(((CustomTileInfo) it.next()).spec);
            }
        }
        Log.d("CSTMPagedTileLayout", "newspecs =  " + arrayList);
        return arrayList;
    }

    public final ArrayList getTilesInfo() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mPages.size(); i++) {
            arrayList.addAll(((CustomizerTilePage) this.mPages.get(i)).mCustomTilesInfo);
        }
        if (!this.mPages.isEmpty()) {
            arrayList.size();
            this.mInitialTiles.clear();
            this.mInitialTiles.addAll(arrayList);
        }
        return this.mInitialTiles;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final CustomizerTilePage makePage() {
        CustomizerTilePage customizerTilePage = (CustomizerTilePage) LayoutInflater.from(this.mContext).inflate(R.layout.qs_customizer_paged_page, (ViewGroup) this, false);
        customizerTilePage.mIsTopEdit = this.mIsTopEdit;
        customizerTilePage.mClickListener = this.mClickListener;
        int i = this.mRows;
        int i2 = this.mColumns;
        if (QpRune.QUICK_TABLET) {
            customizerTilePage.mCurrentRows = customizerTilePage.mMaxRows;
        }
        customizerTilePage.mMaxRows = i;
        customizerTilePage.mColumns = i2;
        customizerTilePage.mShowLabel = this.mShowLabel;
        customizerTilePage.updateResources();
        return customizerTilePage;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsAvailableArea = getId() == R.id.qs_customizer_available_pager;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public final void onMeasure(int i, int i2) {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_icon_frame_size) + (this.mShowLabel ? getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_label_height) : 0);
        if (!(this instanceof CustomizerActiveTileViewPager)) {
            int i3 = this.mRows * dimensionPixelSize;
            if (this.mIsTopEdit && this.mParentContainer != null) {
                int height = (((this.mParentContainer.getHeight() - this.mParentContainer.requireViewById(R.id.qs_edit_available_text).getHeight()) - this.mParentContainer.requireViewById(R.id.qs_available_paged_indicator_container).getHeight()) - getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_icon_frame_size)) - getResources().getDimensionPixelSize(R.dimen.qs_edit_available_active_between_margin);
                if (height > i3) {
                    i3 = height;
                }
            }
            i2 = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
        } else if (!this.mIsScrollView) {
            i2 = View.MeasureSpec.makeMeasureSpec(this.mRows * dimensionPixelSize, 1073741824);
        } else if (this.mPages.size() > 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(((((CustomizerTilePage) this.mPages.get(0)).mCustomTilesInfo.size() / this.mColumns) + 1) * dimensionPixelSize, 1073741824);
        }
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        setAdapter(this.mAdapter);
        setCurrentItem(0, false);
    }

    @Override // android.view.ViewGroup
    public final void removeAllViews() {
        int size = this.mPages.size();
        for (int i = 0; i < size; i++) {
            ((CustomizerTilePage) this.mPages.get(i)).removeAllViews();
        }
    }

    public final void removePage() {
        int currentItem = getCurrentItem();
        this.mPages.remove(r1.size() - 1);
        SecPageIndicator secPageIndicator = this.mPageIndicator;
        if (secPageIndicator != null) {
            AnonymousClass1 anonymousClass1 = this.mAdapter;
            secPageIndicator.setNumPages(anonymousClass1 != null ? anonymousClass1.getCount() : 0);
            if (currentItem == this.mPages.size()) {
                this.mPageIndicator.setLocation(this.mPages.size() - 1);
            } else {
                this.mPageIndicator.setLocation(currentItem);
            }
        }
        this.mAdapter.notifyDataSetChanged();
    }

    public final void removeTile(CustomTileInfo customTileInfo) {
        Iterator it = this.mPages.iterator();
        int i = -1;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            } else {
                i++;
                if (((CustomizerTileLayout) it.next()).indexOf(customTileInfo) >= 0) {
                    break;
                }
            }
        }
        Log.d("CSTMPagedTileLayout", "removeTile: " + customTileInfo + ", pageIndex=" + i);
        if (i == -1 || this.mPages.size() == 0) {
            return;
        }
        int size = this.mPages.size();
        if (!this.mIsScrollView) {
            int i2 = i;
            while (i2 < size - 1) {
                int i3 = i2 + 1;
                CustomTileInfo info = ((CustomizerTilePage) this.mPages.get(i3)).getInfo(0);
                if (info == null) {
                    return;
                }
                ((CustomizerTilePage) this.mPages.get(i2)).addTile(info);
                ((CustomizerTilePage) this.mPages.get(i3)).removeTile(info, false);
                if (((CustomizerTilePage) this.mPages.get(i3)).mCustomTilesInfo.size() == 0) {
                    removePage();
                }
                i2 = i3;
            }
        }
        ((CustomizerTilePage) this.mPages.get(i)).removeTile(customTileInfo, true);
        if (((CustomizerTilePage) this.mPages.get(i)).mCustomTilesInfo.size() != 0 || i == 0) {
            return;
        }
        removePage();
    }

    @Override // androidx.viewpager.widget.ViewPager
    public final void setCurrentItem(int i, boolean z) {
        if (isLayoutRtl()) {
            i = (this.mPages.size() - 1) - i;
        }
        super.setCurrentItem(i, z);
    }

    public void setIsTopEdit(boolean z) {
        this.mIsTopEdit = z;
    }

    public final boolean updateResources() {
        Log.d("CSTMPagedTileLayout", "updateResources");
        boolean z = false;
        for (int i = 0; i < this.mPages.size(); i++) {
            CustomizerTilePage customizerTilePage = (CustomizerTilePage) this.mPages.get(i);
            int i2 = this.mRows;
            int i3 = this.mColumns;
            customizerTilePage.getClass();
            if (QpRune.QUICK_TABLET) {
                customizerTilePage.mCurrentRows = customizerTilePage.mMaxRows;
            }
            customizerTilePage.mMaxRows = i2;
            customizerTilePage.mColumns = i3;
            ((CustomizerTilePage) this.mPages.get(i)).mIsScrollView = this.mIsScrollView;
            ((CustomizerTilePage) this.mPages.get(i)).mShowLabel = this.mShowLabel;
            z |= ((CustomizerTilePage) this.mPages.get(i)).updateResources();
        }
        if (!z) {
            return true;
        }
        this.mInitialTiles.clear();
        for (int i4 = 0; i4 < this.mPages.size(); i4++) {
            Iterator it = ((CustomizerTilePage) this.mPages.get(i4)).mCustomTilesInfo.iterator();
            while (it.hasNext()) {
                this.mInitialTiles.add((CustomTileInfo) it.next());
            }
        }
        removeAllViews();
        distributeTiles$2();
        return true;
    }
}
