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
import com.android.systemui.R;
import com.android.systemui.qs.SecPageIndicator;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.customize.SecQSCustomizerAnimator;
import com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$2;
import com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$3;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class CustomizerTileViewPager extends ViewPager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mAdapter;
    public QSTileCustomizerInteractionManager$initializeListeners$3 mClickListener;
    public int mColumns;
    public final Context mContext;
    public CustomActionManager mCustomActionManager;
    public final AnonymousClass2 mDistribute;
    public QSTileCustomizerInteractionManager$initializeListeners$2 mDragListener;
    public CustomTileInfo mDummyTile;
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
    public class AnonymousClass1 extends PagerAdapter {
        public AnonymousClass1() {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final void destroyItem(ViewPager viewPager, int i, Object obj) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Destantiating ", "CSTMPagedTileLayout");
            viewPager.removeView((View) obj);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final int getCount() {
            return CustomizerTileViewPager.this.mPages.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final int getItemPosition(Object obj) {
            CustomizerTileViewPager customizerTileViewPager = CustomizerTileViewPager.this;
            int iIndexOf = customizerTileViewPager.mPages.indexOf(obj);
            if (iIndexOf == -1) {
                return -2;
            }
            return customizerTileViewPager.isLayoutRtl() ? (customizerTileViewPager.mPages.size() - 1) - iIndexOf : iIndexOf;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final Object instantiateItem(ViewPager viewPager, int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Instantiating ", "CSTMPagedTileLayout");
            CustomizerTileViewPager customizerTileViewPager = CustomizerTileViewPager.this;
            if (customizerTileViewPager.isLayoutRtl()) {
                i = (customizerTileViewPager.mPages.size() - 1) - i;
            }
            ViewGroup viewGroup = (ViewGroup) customizerTileViewPager.mPages.get(i);
            viewPager.addView(viewGroup);
            return viewGroup;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
    }

    /* renamed from: com.android.systemui.qs.customize.CustomizerTileViewPager$4, reason: invalid class name */
    public class AnonymousClass4 {
        public AnonymousClass4() {
        }
    }

    public class CustomizerTilePage extends CustomizerTileLayout {
        public int mCurrentRows;

        public CustomizerTilePage(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public final boolean isFull() {
            return !this.mIsScrollView && this.mCustomTilesInfo.size() >= this.mColumns * this.mMaxRows;
        }

        public final boolean updateResources() {
            boolean z;
            Log.d("CSTMPagedTileLayout", "updateResources 2");
            if (CustomizerTileViewPager.isLargeScreen()) {
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

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qs.customize.CustomizerTileViewPager$2] */
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
        this.mDummyTile = null;
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
        boolean zIsLargeScreen = isLargeScreen();
        setHorizontalFadingEdgeEnabled(zIsLargeScreen);
        setFadingEdgeLength(zIsLargeScreen ? (int) (secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getPanelWidth(context) * 0.0192f) : 0);
    }

    public static boolean isLargeScreen() {
        return ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
    }

    public final void addPage() {
        CustomizerTilePage customizerTilePage = (CustomizerTilePage) LayoutInflater.from(this.mContext).inflate(R.layout.qs_customizer_paged_page, (ViewGroup) this, false);
        customizerTilePage.mIsTopEdit = this.mIsTopEdit;
        customizerTilePage.mClickListener = this.mClickListener;
        int i = this.mRows;
        int i2 = this.mColumns;
        if (isLargeScreen()) {
            customizerTilePage.mCurrentRows = customizerTilePage.mMaxRows;
        }
        customizerTilePage.mMaxRows = i;
        customizerTilePage.mColumns = i2;
        customizerTilePage.mShowLabel = this.mShowLabel;
        customizerTilePage.addBackgroundBox(this.mRows, this.mColumns, this.mDragListener);
        customizerTilePage.updateResources();
        this.mPages.add(customizerTilePage);
        int currentItem = getCurrentItem();
        SecPageIndicator secPageIndicator = this.mPageIndicator;
        if (secPageIndicator != null) {
            AnonymousClass1 anonymousClass1 = this.mAdapter;
            secPageIndicator.setNumPages(anonymousClass1 != null ? anonymousClass1.getCount() : 0);
            this.mPageIndicator.setLocation(currentItem);
        }
        this.mAdapter.notifyDataSetChanged();
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
                if (((CustomizerTilePage) this.mPages.get(i2)).isFull()) {
                    if (i2 == this.mAdapter.getCount() - 1) {
                        addPage();
                        if (z && i < 19998) {
                            currentItem++;
                            setCurrentItem(currentItem, false);
                            break;
                        } else {
                            CustomTileInfo customTileInfo2 = (CustomTileInfo) AlertController$$ExternalSyntheticOutline0.m(1, ((CustomizerTilePage) this.mPages.get(i2)).mCustomTilesInfo);
                            ((CustomizerTilePage) this.mPages.get(i2 + 1)).addTile(customTileInfo2, 0, false);
                            ((CustomizerTilePage) this.mPages.get(i2)).removeTile(customTileInfo2, false);
                        }
                    } else {
                        CustomTileInfo customTileInfo3 = (CustomTileInfo) AlertController$$ExternalSyntheticOutline0.m(1, ((CustomizerTilePage) this.mPages.get(i2)).mCustomTilesInfo);
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

    @Override // androidx.viewpager.widget.ViewPager
    public final boolean canScroll(int i, int i2, int i3, View view, boolean z) {
        return false;
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
            if (((CustomizerTilePage) this.mPages.get(i)).isFull() && (i = i + 1) == this.mPages.size()) {
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
        CustomizerTilePage customizerTilePage = (CustomizerTilePage) this.mPages.get(0);
        companion.getClass();
        if (customizerTilePage == null) {
            return;
        }
        customizerTilePage.getChildAt(0);
        int childCount = customizerTilePage.getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View viewFindViewById = customizerTilePage.getChildAt(i5).findViewById(SecQSSettingEditResources.REMOVE_ICON_ID);
            if (viewFindViewById != null) {
                viewFindViewById.setScaleX(0.5f);
                viewFindViewById.setScaleY(0.5f);
                viewFindViewById.setAlpha(0.0f);
                viewFindViewById.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200L).setStartDelay(200L).start();
            }
        }
    }

    public final int getColumnCount() {
        if (this.mPages.size() == 0) {
            return 0;
        }
        return ((CustomizerTilePage) this.mPages.get(0)).mColumns;
    }

    public final int getColumnMaxCountInPage() {
        return this.mColumns * this.mRows;
    }

    @Override // androidx.viewpager.widget.ViewPager
    public final int getCurrentItem() {
        int i = this.mCurItem;
        return (this.mAdapter == null || !isLayoutRtl()) ? i : (this.mAdapter.getCount() - 1) - i;
    }

    public final int getMinimumTileNum() {
        return ((this.mPages.size() - 1) * this.mColumns * this.mRows) + (this.mAdapter.getCount() + (-1) < this.mPages.size() ? ((CustomizerTilePage) this.mPages.get(this.mAdapter.getCount() - 1)).mCustomTilesInfo.size() : 0);
    }

    @Override // android.view.View
    public final float getRightFadingEdgeStrength() {
        return 1.0f;
    }

    public final List getSpec() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mPages.size(); i++) {
            ArrayList arrayList2 = ((CustomizerTilePage) this.mPages.get(i)).mCustomTilesInfo;
            int size = arrayList2.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList2.get(i2);
                i2++;
                arrayList.add(((CustomTileInfo) obj).spec);
            }
        }
        Log.d("CSTMPagedTileLayout", "newspecs =  " + arrayList);
        return arrayList;
    }

    public final int getTiledPageIndex(CustomTileInfo customTileInfo) {
        ArrayList arrayList = this.mPages;
        int size = arrayList.size();
        int i = 0;
        int i2 = -1;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            i2++;
            if (((CustomizerTileLayout) obj).indexOf(customTileInfo) >= 0) {
                return i2;
            }
        }
        return -1;
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
        if (isLargeScreen()) {
            customizerTilePage.mCurrentRows = customizerTilePage.mMaxRows;
        }
        customizerTilePage.mMaxRows = i;
        customizerTilePage.mColumns = i2;
        customizerTilePage.mShowLabel = this.mShowLabel;
        customizerTilePage.mCustomActionManager = this.mCustomActionManager;
        customizerTilePage.updateResources();
        return customizerTilePage;
    }

    public final void moveTile(CustomTileInfo customTileInfo, int i) {
        int tiledPageIndex = getTiledPageIndex(customTileInfo);
        Log.d("CSTMPagedTileLayout", "moveTile: " + customTileInfo + ", pageIndex=" + tiledPageIndex);
        if (tiledPageIndex == -1 || this.mPages.size() == 0) {
            return;
        }
        int columnMaxCountInPage = i / getColumnMaxCountInPage();
        int columnMaxCountInPage2 = i % getColumnMaxCountInPage();
        if (tiledPageIndex > columnMaxCountInPage) {
            int i2 = columnMaxCountInPage;
            while (i2 < tiledPageIndex) {
                CustomTileInfo customTileInfo2 = (CustomTileInfo) AlertController$$ExternalSyntheticOutline0.m(1, ((CustomizerTilePage) this.mPages.get(i2)).mCustomTilesInfo);
                int i3 = i2 + 1;
                ((CustomizerTilePage) this.mPages.get(i3)).addTile(customTileInfo2, 0, false);
                ((CustomizerTilePage) this.mPages.get(i2)).removeTile(customTileInfo2, false);
                i2 = i3;
            }
        } else {
            int i4 = tiledPageIndex;
            while (i4 < columnMaxCountInPage) {
                int i5 = i4 + 1;
                CustomTileInfo info = ((CustomizerTilePage) this.mPages.get(i5)).getInfo(0);
                if (info == null) {
                    return;
                }
                ((CustomizerTilePage) this.mPages.get(i4)).addTile(info);
                ((CustomizerTilePage) this.mPages.get(i5)).removeTile(info, false);
                i4 = i5;
            }
        }
        ((CustomizerTilePage) this.mPages.get(tiledPageIndex)).removeTile(customTileInfo, false);
        ((CustomizerTilePage) this.mPages.get(columnMaxCountInPage)).addTile(customTileInfo, columnMaxCountInPage2, false);
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
            if (!isLargeScreen() && this.mIsTopEdit && this.mParentContainer != null) {
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
        int tiledPageIndex = getTiledPageIndex(customTileInfo);
        Log.d("CSTMPagedTileLayout", "removeTile: " + customTileInfo + ", pageIndex=" + tiledPageIndex);
        if (tiledPageIndex == -1 || this.mPages.size() == 0) {
            return;
        }
        int size = this.mPages.size();
        if (!this.mIsScrollView) {
            int i = tiledPageIndex;
            while (i < size - 1) {
                int i2 = i + 1;
                CustomTileInfo info = ((CustomizerTilePage) this.mPages.get(i2)).getInfo(0);
                if (info == null) {
                    return;
                }
                ((CustomizerTilePage) this.mPages.get(i)).addTile(info);
                ((CustomizerTilePage) this.mPages.get(i2)).removeTile(info, false);
                if (((CustomizerTilePage) this.mPages.get(i2)).mCustomTilesInfo.size() == 0) {
                    removePage();
                }
                i = i2;
            }
        }
        ((CustomizerTilePage) this.mPages.get(tiledPageIndex)).removeTile(customTileInfo, true);
        if (((CustomizerTilePage) this.mPages.get(tiledPageIndex)).mCustomTilesInfo.size() != 0 || tiledPageIndex == 0) {
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
        boolean zUpdateResources = false;
        for (int i = 0; i < this.mPages.size(); i++) {
            CustomizerTilePage customizerTilePage = (CustomizerTilePage) this.mPages.get(i);
            int i2 = this.mRows;
            int i3 = this.mColumns;
            customizerTilePage.getClass();
            if (isLargeScreen()) {
                customizerTilePage.mCurrentRows = customizerTilePage.mMaxRows;
            }
            customizerTilePage.mMaxRows = i2;
            customizerTilePage.mColumns = i3;
            ((CustomizerTilePage) this.mPages.get(i)).mIsScrollView = this.mIsScrollView;
            ((CustomizerTilePage) this.mPages.get(i)).mShowLabel = this.mShowLabel;
            zUpdateResources |= ((CustomizerTilePage) this.mPages.get(i)).updateResources();
        }
        if (!zUpdateResources) {
            return true;
        }
        this.mInitialTiles.clear();
        for (int i4 = 0; i4 < this.mPages.size(); i4++) {
            ArrayList arrayList = ((CustomizerTilePage) this.mPages.get(i4)).mCustomTilesInfo;
            int size = arrayList.size();
            int i5 = 0;
            while (i5 < size) {
                Object obj = arrayList.get(i5);
                i5++;
                this.mInitialTiles.add((CustomTileInfo) obj);
            }
        }
        removeAllViews();
        distributeTiles$2();
        return true;
    }
}
