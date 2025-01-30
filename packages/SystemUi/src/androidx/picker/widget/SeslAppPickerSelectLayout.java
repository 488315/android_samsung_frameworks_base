package androidx.picker.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.picker.R$styleable;
import androidx.picker.adapter.HeaderFooterAdapter;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager;
import androidx.picker.common.log.LogTag;
import androidx.picker.common.log.LogTagHelperKt;
import androidx.picker.controller.ViewDataController;
import androidx.picker.decorator.RecyclerViewCornerDecoration;
import androidx.picker.helper.DrawableHelperKt;
import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoData;
import androidx.picker.model.SpanData;
import androidx.picker.model.appdata.CategoryAppData;
import androidx.picker.model.appdata.GroupAppData;
import androidx.picker.model.viewdata.ViewData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsJvmKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SeslAppPickerSelectLayout extends FrameLayout implements LogTag {
    public static final /* synthetic */ int $r8$clinit = 0;
    public LayoutType curLayoutType;
    public final FrameLayout mAppPickerStateContainerView;
    public final SeslAppPickerListView mAppPickerStateView;
    public final CheckStateManager mCheckStateManager;
    public int mHeaderHeight;
    public boolean mHeaderVisibility;
    public final int mListItemHeight;
    public final TextView mMainViewTitleView;
    public final ConstraintLayout mRootView;
    public final View mSearchNoResultFoundView;
    public final SelectLayoutType mSelectLayoutType;
    public final SeslAppPickerGridView mSelectedListView;
    public final FrameLayout mSelectedViewHeader;
    public int mSelectedViewHeight;
    public int mSelectedViewTitleHeight;
    public boolean mShouldCheckHeaderVisibility;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.picker.widget.SeslAppPickerSelectLayout$5 */
    public final class C03685 {
        public C03685() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.picker.widget.SeslAppPickerSelectLayout$8 */
    public abstract /* synthetic */ class AbstractC03718 {

        /* renamed from: $SwitchMap$androidx$picker$widget$SeslAppPickerSelectLayout$SelectLayoutType */
        public static final /* synthetic */ int[] f174xbaf46ff;

        static {
            int[] iArr = new int[SelectLayoutType.values().length];
            f174xbaf46ff = iArr;
            try {
                iArr[SelectLayoutType.PORT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f174xbaf46ff[SelectLayoutType.LAND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f174xbaf46ff[SelectLayoutType.AUTO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CheckStateManager implements LogTag {
        public final LinkedHashMap mFixedAppMap = new LinkedHashMap();
        public final LinkedHashMap mCheckedMap = new LinkedHashMap();

        public final AppInfoData get(AppInfo appInfo) {
            LinkedHashMap linkedHashMap = this.mCheckedMap;
            if (linkedHashMap.containsKey(appInfo)) {
                return (AppInfoData) linkedHashMap.get(appInfo);
            }
            LinkedHashMap linkedHashMap2 = this.mFixedAppMap;
            if (linkedHashMap2.containsKey(appInfo)) {
                return (AppInfoData) linkedHashMap2.get(appInfo);
            }
            return null;
        }

        @Override // androidx.picker.common.log.LogTag
        public final String getLogTag() {
            return "CheckStateManager";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum LayoutType {
        LAND(R.layout.picker_app_list_selectlayout_template_land),
        LAND_HEADER_ONLY(R.layout.picker_app_list_selectlayout_template_land_header_only),
        LAND_SELECTED(R.layout.picker_app_list_selectlayout_template_land_with_selected),
        PORT(R.layout.picker_app_list_selectlayout_template_portrait),
        PORT_SELECTED(R.layout.picker_app_list_selectlayout_template_portrait_with_selected);

        public final int layoutResId;

        LayoutType(int i) {
            this.layoutResId = i;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum SelectLayoutType {
        AUTO,
        PORT,
        LAND
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SelectedHorizontalItemDecoration extends RecyclerView.ItemDecoration {
        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            super.getItemOffsets(rect, view, recyclerView, state);
            if (recyclerView.mAdapter == null) {
                return;
            }
            int childAdapterPosition = RecyclerView.getChildAdapterPosition(view);
            Resources resources = recyclerView.getContext().getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.picker_app_selected_layout_horizontal_padding);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.picker_app_selected_item_view_interval_horizontal_on_port);
            rect.left = childAdapterPosition == 0 ? dimensionPixelSize : dimensionPixelSize2;
            if (childAdapterPosition != r3.getItemCount() - 1) {
                dimensionPixelSize = dimensionPixelSize2;
            }
            rect.right = dimensionPixelSize;
            rect.top = resources.getDimensionPixelSize(R.dimen.picker_app_grid_item_view_item_top_padding);
            rect.bottom = resources.getDimensionPixelSize(R.dimen.picker_app_grid_item_view_item_bottom_padding);
            View findViewById = view.findViewById(R.id.item);
            findViewById.getLayoutParams().width = resources.getDimensionPixelOffset(R.dimen.picker_app_grid_item_view_title_width);
            findViewById.getLayoutParams().height = (int) Math.ceil(((resources.getDimension(R.dimen.picker_app_grid_icon_title_size) * 2.0f) + (resources.getDimension(R.dimen.picker_app_grid_item_view_icon_layout_margin_bottom) + (resources.getDimension(R.dimen.picker_app_grid_item_view_icon_layout_margin_top) + resources.getDimension(R.dimen.picker_app_grid_icon_size)))) - resources.getDimension(R.dimen.picker_app_grid_item_view_remove_icon_layout_margin));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SelectedVerticallItemDecoration extends RecyclerView.ItemDecoration {
        public final int spacing;

        public SelectedVerticallItemDecoration(int i) {
            this.spacing = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            super.getItemOffsets(rect, view, recyclerView, state);
            recyclerView.getClass();
            int childAdapterPosition = RecyclerView.getChildAdapterPosition(view);
            if (childAdapterPosition == -1 || recyclerView.mAdapter == null) {
                return;
            }
            RecyclerView.LayoutManager layoutManager$1 = recyclerView.getLayoutManager$1();
            if (layoutManager$1 instanceof GridLayoutManager) {
                int i = ((GridLayoutManager) layoutManager$1).mSpanCount;
                int i2 = this.spacing / 2;
                rect.top = i2;
                rect.bottom = i2;
                int dimensionPixelOffset = view.getContext().getResources().getDimensionPixelOffset(R.dimen.picker_app_selected_layout_horizontal_interval) / 2;
                int i3 = childAdapterPosition % i;
                rect.left = i3 == 0 ? 0 : dimensionPixelOffset;
                if (i3 == i - 1) {
                    dimensionPixelOffset = 0;
                }
                rect.right = dimensionPixelOffset;
                view.findViewById(R.id.item).getLayoutParams().width = -1;
            }
        }
    }

    public SeslAppPickerSelectLayout(Context context) {
        this(context, null);
    }

    public static AppInfoData convertCheckBox2Remove(AppInfoData appInfoData) {
        return new AppData.GridRemoveAppDataBuilder(appInfoData).setIcon(DrawableHelperKt.newMutateDrawable(appInfoData.getIcon())).setSubIcon(DrawableHelperKt.newMutateDrawable(appInfoData.getSubIcon())).build();
    }

    public static CategoryAppData getCategoryAppDataContainsAppInfo(List list, AppInfo appInfo) {
        AppInfoData appInfoData;
        CategoryAppData categoryAppData;
        Iterator it = ((ArrayList) list).iterator();
        do {
            appInfoData = null;
            if (!it.hasNext()) {
                return null;
            }
            categoryAppData = (CategoryAppData) it.next();
            Iterator it2 = categoryAppData.appInfoDataList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                AppInfoData appInfoData2 = (AppInfoData) it2.next();
                if (appInfoData2.getAppInfo().equals(appInfo)) {
                    appInfoData = appInfoData2;
                    break;
                }
            }
        } while (appInfoData == null);
        return categoryAppData;
    }

    public static List getCategoryAppDataList(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AppData appData = (AppData) it.next();
            if (appData instanceof GroupAppData) {
                arrayList.addAll(CollectionsKt___CollectionsJvmKt.filterIsInstance(((GroupAppData) appData).appDataList, CategoryAppData.class));
            } else if (appData instanceof CategoryAppData) {
                arrayList.add((CategoryAppData) appData);
            }
        }
        return arrayList;
    }

    public final void addCheckedItem(AppInfoData appInfoData) {
        if (appInfoData.getDimmed()) {
            CheckStateManager checkStateManager = this.mCheckStateManager;
            checkStateManager.getClass();
            AppInfo appInfo = appInfoData.getAppInfo();
            LinkedHashMap linkedHashMap = checkStateManager.mFixedAppMap;
            if (!linkedHashMap.containsKey(appInfo)) {
                linkedHashMap.put(appInfo, appInfoData);
                return;
            }
            LogTagHelperKt.warn(checkStateManager, appInfoData + " is already added");
            return;
        }
        CheckStateManager checkStateManager2 = this.mCheckStateManager;
        checkStateManager2.getClass();
        AppInfo appInfo2 = appInfoData.getAppInfo();
        LinkedHashMap linkedHashMap2 = checkStateManager2.mCheckedMap;
        if (!linkedHashMap2.containsKey(appInfo2)) {
            linkedHashMap2.put(appInfo2, appInfoData);
            return;
        }
        LogTagHelperKt.warn(checkStateManager2, appInfoData + " is already added");
    }

    public final void addSelectItem(CategoryAppData categoryAppData) {
        removeSelectItemInCategory(categoryAppData);
        AppInfoData build = new AppData.GridRemoveAppDataBuilder(categoryAppData.appInfo).setLabel(categoryAppData.label).setIcon(DrawableHelperKt.newMutateDrawable(categoryAppData.icon)).setSelected(categoryAppData.getSelected()).build();
        addCheckedItem(build);
        Arrays.asList(build);
    }

    public final int convertOrientation(int i) {
        int i2 = AbstractC03718.f174xbaf46ff[this.mSelectLayoutType.ordinal()];
        int i3 = 1;
        if (i2 != 1) {
            i3 = 2;
            if (i2 != 2) {
                return i;
            }
        }
        return i3;
    }

    @Override // androidx.picker.common.log.LogTag
    public final String getLogTag() {
        return "SeslAppPickerSelectLayout";
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mShouldCheckHeaderVisibility = shouldCheckHeaderVisibility();
        if (this.mSelectLayoutType == SelectLayoutType.AUTO) {
            updateLayout();
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mSelectedViewHeader.getChildCount() <= 0) {
            return;
        }
        post(new SeslAppPickerSelectLayout$$ExternalSyntheticLambda0(this, 0));
    }

    public final void refreshSelectedAppPickerView(boolean z) {
        int convertOrientation = convertOrientation(getResources().getConfiguration().orientation);
        boolean z2 = this.mSelectedViewHeader.getChildCount() > 0 && this.mHeaderVisibility;
        LayoutType layoutType = convertOrientation == 1 ? LayoutType.PORT : z2 ? LayoutType.LAND_HEADER_ONLY : LayoutType.LAND;
        if (this.curLayoutType != layoutType) {
            this.curLayoutType = layoutType;
            int visibility = this.mSearchNoResultFoundView.getVisibility();
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(layoutType.layoutResId, getContext());
            constraintSet.applyTo(this.mRootView);
            this.mSearchNoResultFoundView.setVisibility(visibility);
            if (z) {
                ChangeBounds changeBounds = new ChangeBounds();
                changeBounds.addListener(new C03696());
                this.mRootView.clearAnimation();
                TransitionManager.beginDelayedTransition(this.mRootView, changeBounds);
            } else {
                TransitionManager.endTransitions(this.mRootView);
            }
        }
        this.mSelectedViewHeader.setVisibility(z2 ? 0 : 8);
    }

    public final void removeSelectItemInCategory(CategoryAppData categoryAppData) {
        ArrayList arrayList = new ArrayList();
        Iterator it = categoryAppData.appInfoDataList.iterator();
        while (it.hasNext()) {
            AppInfoData appInfoData = this.mCheckStateManager.get(((AppInfoData) it.next()).getAppInfo());
            if (appInfoData != null) {
                CheckStateManager checkStateManager = this.mCheckStateManager;
                AppInfo appInfo = appInfoData.getAppInfo();
                LinkedHashMap linkedHashMap = checkStateManager.mCheckedMap;
                if (linkedHashMap.containsKey(appInfo)) {
                    linkedHashMap.remove(appInfo);
                }
                LinkedHashMap linkedHashMap2 = checkStateManager.mFixedAppMap;
                if (linkedHashMap2.containsKey(appInfo)) {
                    linkedHashMap2.remove(appInfo);
                }
                arrayList.add(appInfoData);
            }
        }
    }

    public final boolean shouldCheckHeaderVisibility() {
        Context context = getContext();
        Boolean bool = Boolean.FALSE;
        if (context instanceof Activity) {
            bool = Boolean.valueOf(((Activity) context).isInMultiWindowMode());
        }
        try {
            Configuration configuration = getResources().getConfiguration();
            bool = Boolean.valueOf(configuration.semIsPopOver() | bool.booleanValue());
        } catch (NoSuchMethodError unused) {
            LogTagHelperKt.warn(this, "Failed to call semIsPopOver");
        }
        return bool.booleanValue();
    }

    public final void updateCheckedAppList(CategoryAppData categoryAppData) {
        if (categoryAppData.getSelected()) {
            removeSelectItemInCategory(categoryAppData);
            addCheckedItem(new AppData.GridRemoveAppDataBuilder(categoryAppData.appInfo).setIcon(DrawableHelperKt.newMutateDrawable(categoryAppData.icon)).setLabel(categoryAppData.label).build());
            return;
        }
        for (AppInfoData appInfoData : categoryAppData.appInfoDataList) {
            if (appInfoData.getSelected()) {
                addCheckedItem(convertCheckBox2Remove(appInfoData));
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0032, code lost:
    
        if (((((getHeight() - r4.mHeaderHeight) - r4.mSelectedViewTitleHeight) - r4.mSelectedViewHeight) - r4.mMainViewTitleView.getHeight() > r4.mListItemHeight) != false) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateHeaderVisibility() {
        boolean z;
        Configuration configuration = getResources().getConfiguration();
        if (this.mShouldCheckHeaderVisibility && convertOrientation(configuration.orientation) != 2) {
            z = false;
        }
        z = true;
        if (this.mHeaderVisibility != z) {
            this.mHeaderVisibility = z;
            post(new SeslAppPickerSelectLayout$$ExternalSyntheticLambda0(this, 1));
        }
    }

    public final void updateLayout() {
        LinearLayoutManager linearLayoutManager;
        int convertOrientation = convertOrientation(getResources().getConfiguration().orientation);
        this.mSelectedListView.clearItemDecoration();
        if (convertOrientation == 1) {
            this.mSelectedListView.getLayoutParams().height = -2;
            this.mSelectedListView.addItemDecoration(new SelectedHorizontalItemDecoration());
        } else {
            this.mSelectedListView.getLayoutParams().height = 0;
            this.mSelectedListView.addItemDecoration(new SelectedVerticallItemDecoration(getResources().getDimensionPixelOffset(R.dimen.picker_app_selected_item_view_interval_vertical_on_land)));
        }
        this.mSelectedListView.addItemDecoration(new RecyclerViewCornerDecoration(getContext()));
        this.mSelectedListView.seslSetFillBottomEnabled(false);
        SeslAppPickerGridView seslAppPickerGridView = this.mSelectedListView;
        if (convertOrientation == 1) {
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
            linearLayoutManager2.setOrientation(0);
            linearLayoutManager = linearLayoutManager2;
        } else {
            final AutoFitGridLayoutManager autoFitGridLayoutManager = new AutoFitGridLayoutManager(getContext());
            final SeslAppPickerGridView seslAppPickerGridView2 = this.mSelectedListView;
            autoFitGridLayoutManager.mSpanSizeLookup = new GridLayoutManager.SpanSizeLookup(this) { // from class: androidx.picker.widget.SeslAppPickerSelectLayout.7
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public final int getSpanSize(int i) {
                    SeslAppPickerGridView seslAppPickerGridView3 = seslAppPickerGridView2;
                    HeaderFooterAdapter headerFooterAdapter = ((SeslAppPickerView) seslAppPickerGridView3).mAdapter;
                    if (headerFooterAdapter == null || i < 0 || i >= headerFooterAdapter.getItemCount()) {
                        return 1;
                    }
                    ViewData item = ((SeslAppPickerView) seslAppPickerGridView3).mAdapter.getItem(i);
                    boolean z = item instanceof SpanData;
                    GridLayoutManager gridLayoutManager = autoFitGridLayoutManager;
                    if (!z) {
                        return gridLayoutManager.mSpanCount;
                    }
                    int spanCount = ((SpanData) item).getSpanCount();
                    return spanCount == -1 ? gridLayoutManager.mSpanCount : spanCount;
                }
            };
            linearLayoutManager = autoFitGridLayoutManager;
        }
        seslAppPickerGridView.setLayoutManager(linearLayoutManager);
        refreshSelectedAppPickerView(false);
    }

    public SeslAppPickerSelectLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeslAppPickerSelectLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0068, code lost:
    
        if (r6 == 0) goto L21;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0169  */
    /* JADX WARN: Type inference failed for: r6v0, types: [android.util.AttributeSet] */
    /* JADX WARN: Type inference failed for: r6v17, types: [android.content.res.TypedArray] */
    /* JADX WARN: Type inference failed for: r6v18, types: [android.content.res.TypedArray] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SeslAppPickerSelectLayout(final Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        RuntimeException e;
        this.mCheckStateManager = new CheckStateManager();
        this.mHeaderVisibility = true;
        this.mListItemHeight = getResources().getDimensionPixelOffset(R.dimen.picker_app_list_single_line_height);
        new Object(this) { // from class: androidx.picker.widget.SeslAppPickerSelectLayout.1
        };
        this.mSelectLayoutType = SelectLayoutType.AUTO;
        TypedArray typedArray = null;
        this.curLayoutType = null;
        try {
            try {
                attributeSet = context.obtainStyledAttributes(attributeSet, R$styleable.SeslAppPickerSelectLayout, i, 0);
                try {
                    int i3 = attributeSet.getInt(0, -1);
                    if (i3 >= 0 && i3 < SelectLayoutType.values().length) {
                        this.mSelectLayoutType = SelectLayoutType.values()[i3];
                    } else {
                        boolean z = LogTagHelperKt.IS_DEBUG_DEVICE;
                        getLogTag();
                        Log.e("SeslAppPicker[1.0.44-sesl6].SeslAppPickerSelectLayout", "AppPickerSelectLayout Type is wrong =" + i3);
                    }
                } catch (RuntimeException e2) {
                    e = e2;
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                th = th;
                typedArray = attributeSet;
                if (typedArray != null) {
                    typedArray.recycle();
                }
                throw th;
            }
        } catch (RuntimeException e3) {
            e = e3;
            attributeSet = 0;
        } catch (Throwable th2) {
            th = th2;
            if (typedArray != null) {
            }
            throw th;
        }
        attributeSet.recycle();
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.picker_app_list_checkbox_container, (ViewGroup) this, true);
        this.mRootView = (ConstraintLayout) findViewById(R.id.root_app_picker_container);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.selected_app_picker_header);
        this.mSelectedViewHeader = frameLayout;
        frameLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: androidx.picker.widget.SeslAppPickerSelectLayout.2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                SeslAppPickerSelectLayout seslAppPickerSelectLayout = SeslAppPickerSelectLayout.this;
                if (seslAppPickerSelectLayout.mHeaderVisibility) {
                    seslAppPickerSelectLayout.mSelectedViewHeight = i7 - i5;
                    seslAppPickerSelectLayout.post(new SeslAppPickerSelectLayout$$ExternalSyntheticLambda0(seslAppPickerSelectLayout, 3));
                }
            }
        });
        FrameLayout frameLayout2 = (FrameLayout) findViewById(R.id.app_picker_state_view_container);
        this.mAppPickerStateContainerView = frameLayout2;
        this.mMainViewTitleView = (TextView) findViewById(R.id.main_view_title);
        ((TextView) findViewById(R.id.selected_view_title)).addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: androidx.picker.widget.SeslAppPickerSelectLayout.3
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                SeslAppPickerSelectLayout seslAppPickerSelectLayout = SeslAppPickerSelectLayout.this;
                if (seslAppPickerSelectLayout.mHeaderVisibility) {
                    seslAppPickerSelectLayout.mSelectedViewTitleHeight = i7 - i5;
                    seslAppPickerSelectLayout.post(new SeslAppPickerSelectLayout$$ExternalSyntheticLambda0(seslAppPickerSelectLayout, 4));
                }
            }
        });
        View findViewById = findViewById(R.id.no_results_found);
        this.mSearchNoResultFoundView = findViewById;
        findViewById.setOnTouchListener(new View.OnTouchListener() { // from class: androidx.picker.widget.SeslAppPickerSelectLayout$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                SeslAppPickerSelectLayout seslAppPickerSelectLayout = SeslAppPickerSelectLayout.this;
                Context context2 = context;
                int i4 = SeslAppPickerSelectLayout.$r8$clinit;
                seslAppPickerSelectLayout.getClass();
                if (motionEvent.getAction() == 0) {
                    ((InputMethodManager) context2.getSystemService("input_method")).hideSoftInputFromWindow(seslAppPickerSelectLayout.getWindowToken(), 0);
                }
                return false;
            }
        });
        SeslAppPickerGridView seslAppPickerGridView = (SeslAppPickerGridView) findViewById(R.id.selected_app_picker_view);
        this.mSelectedListView = seslAppPickerGridView;
        seslAppPickerGridView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: androidx.picker.widget.SeslAppPickerSelectLayout.4
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                SeslAppPickerSelectLayout seslAppPickerSelectLayout = SeslAppPickerSelectLayout.this;
                if (seslAppPickerSelectLayout.mHeaderVisibility) {
                    seslAppPickerSelectLayout.mHeaderHeight = i7 - i5;
                    seslAppPickerSelectLayout.post(new SeslAppPickerSelectLayout$$ExternalSyntheticLambda0(seslAppPickerSelectLayout, 5));
                }
            }
        });
        ViewDataController viewDataController = seslAppPickerGridView.mViewDataController;
        viewDataController.order = null;
        viewDataController.submit(viewDataController.appDataList, null);
        CheckStateManager checkStateManager = this.mCheckStateManager;
        checkStateManager.getClass();
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(checkStateManager.mFixedAppMap.values());
        arrayList.addAll(checkStateManager.mCheckedMap.values());
        LogTagHelperKt.info(seslAppPickerGridView, "submitList=" + Integer.valueOf(arrayList.size()));
        ArrayList arrayList2 = new ArrayList(arrayList);
        ViewDataController viewDataController2 = seslAppPickerGridView.mViewDataController;
        viewDataController2.appDataList = arrayList2;
        viewDataController2.submit(arrayList2, viewDataController2.order);
        seslAppPickerGridView.seslSetGoToTopEnabled(false);
        seslAppPickerGridView.seslSetFastScrollerEnabled(false);
        seslAppPickerGridView.mOnClickEventListener = new SeslAppPickerSelectLayout$$ExternalSyntheticLambda2(this, context);
        if (((SeslAppPickerView) seslAppPickerGridView).mAdapter != null) {
            seslAppPickerGridView.post(new SeslAppPickerView$$ExternalSyntheticLambda1(seslAppPickerGridView, 1));
        }
        this.mShouldCheckHeaderVisibility = shouldCheckHeaderVisibility();
        updateLayout();
        SeslAppPickerListView seslAppPickerListView = new SeslAppPickerListView(context);
        this.mAppPickerStateView = seslAppPickerListView;
        frameLayout2.addView(seslAppPickerListView);
        seslAppPickerListView.mOnStateChangeListener = new C03685();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.picker.widget.SeslAppPickerSelectLayout$6 */
    public final class C03696 implements Transition.TransitionListener {
        public SeslAppPickerSelectLayout$6$$ExternalSyntheticLambda0 rollback = null;

        public C03696() {
        }

        @Override // android.transition.Transition.TransitionListener
        public final void onTransitionEnd(Transition transition) {
            SeslAppPickerSelectLayout$6$$ExternalSyntheticLambda0 seslAppPickerSelectLayout$6$$ExternalSyntheticLambda0 = this.rollback;
            if (seslAppPickerSelectLayout$6$$ExternalSyntheticLambda0 != null) {
                seslAppPickerSelectLayout$6$$ExternalSyntheticLambda0.run();
            }
        }

        @Override // android.transition.Transition.TransitionListener
        public final void onTransitionStart(Transition transition) {
            SeslAppPickerSelectLayout seslAppPickerSelectLayout = SeslAppPickerSelectLayout.this;
            RecyclerView.ItemAnimator itemAnimator = seslAppPickerSelectLayout.mSelectedListView.mItemAnimator;
            if (itemAnimator != null) {
                LogTagHelperKt.debug(seslAppPickerSelectLayout, "setItemAnimator = null");
                SeslAppPickerSelectLayout.this.mSelectedListView.clearAnimation();
                SeslAppPickerSelectLayout.this.mSelectedListView.setItemAnimator(null);
                this.rollback = new SeslAppPickerSelectLayout$6$$ExternalSyntheticLambda0(this, itemAnimator);
            }
        }

        @Override // android.transition.Transition.TransitionListener
        public final void onTransitionCancel(Transition transition) {
        }

        @Override // android.transition.Transition.TransitionListener
        public final void onTransitionPause(Transition transition) {
        }

        @Override // android.transition.Transition.TransitionListener
        public final void onTransitionResume(Transition transition) {
        }
    }
}
