package androidx.picker.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import androidx.picker.R$styleable;
import androidx.picker.adapter.AbsAdapter;
import androidx.picker.adapter.AppPickerAdapter$OnBindListener;
import androidx.picker.adapter.HeaderFooterAdapter;
import androidx.picker.adapter.viewholder.PickerViewHolder;
import androidx.picker.common.log.LogTag;
import androidx.picker.common.log.LogTagHelperKt;
import androidx.picker.controller.ViewDataController;
import androidx.picker.controller.strategy.AppItemStrategy;
import androidx.picker.controller.strategy.Strategy;
import androidx.picker.decorator.RecyclerViewCornerDecoration;
import androidx.picker.helper.FlowHelperKt;
import androidx.picker.loader.select.SelectStateLoader;
import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.AppSideViewData;
import androidx.picker.model.viewdata.ViewData;
import androidx.picker.p000di.AppPickerContext;
import androidx.picker.repository.AppDataRepository;
import androidx.picker.widget.SeslAppPickerSelectLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Objects;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class SeslAppPickerView extends RecyclerView implements RecyclerView.RecyclerListener, AppPickerAdapter$OnBindListener, LogTag {
    public final C03721 clearKeyboardListener;
    public DisposableHandle disposable;
    public HeaderFooterAdapter mAdapter;
    public final AppDataRepository mAppDataRepository;
    public final Context mContext;
    public SeslAppPickerSelectLayout$$ExternalSyntheticLambda2 mOnClickEventListener;
    public SeslAppPickerSelectLayout.C03685 mOnStateChangeListener;
    public final SelectStateLoader mSelectStateLoader;
    public final int mSeslRoundedCorner;
    public final ViewDataController mViewDataController;
    public int mViewType;
    public final C03732 scrollListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.picker.widget.SeslAppPickerView$3 */
    public final class C03743 {
        public C03743() {
        }
    }

    public SeslAppPickerView(Context context) {
        this(context, null);
    }

    public final void clearItemDecoration() {
        while (this.mItemDecorations.size() > 0) {
            int size = this.mItemDecorations.size();
            if (size <= 0) {
                throw new IndexOutOfBoundsException(AbstractC0000x2c234b15.m0m("0 is an invalid index for size ", size));
            }
            int size2 = this.mItemDecorations.size();
            if (size2 <= 0) {
                throw new IndexOutOfBoundsException(AbstractC0000x2c234b15.m0m("0 is an invalid index for size ", size2));
            }
            removeItemDecoration((RecyclerView.ItemDecoration) this.mItemDecorations.get(0));
        }
    }

    public final AppData getAppData(AppInfo appInfo) {
        ViewData viewData = this.mViewDataController.getViewData(appInfo);
        if (!(viewData instanceof AppSideViewData)) {
            return null;
        }
        if (viewData instanceof AppInfoViewData) {
            AppInfoViewData appInfoViewData = (AppInfoViewData) viewData;
            if (appInfoViewData.appInfoData.getIcon() == null) {
                FlowHelperKt.loadIconSync(appInfoViewData.iconFlow);
            }
        }
        return ((AppSideViewData) viewData).getAppData();
    }

    public abstract AbsAdapter getAppPickerAdapter();

    public abstract RecyclerView.LayoutManager getLayoutManager();

    public String getLogTag() {
        return "SeslAppPickerView";
    }

    public final void initialize() {
        HeaderFooterAdapter headerFooterAdapter = new HeaderFooterAdapter(getAppPickerAdapter());
        this.mAdapter = headerFooterAdapter;
        setItemDecoration(this.mViewType, headerFooterAdapter);
        setLayoutManager(getLayoutManager());
        setAdapter(this.mAdapter);
        this.mAdapter.wrappedAdapter.mOnBindListener = this;
        seslSetGoToTopEnabled(true);
        seslSetFastScrollerEnabled(true);
        seslSetFillBottomEnabled(true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        addOnScrollListener(this.scrollListener);
        addOnScrollListener(this.clearKeyboardListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        setAdapter(null);
        super.onDetachedFromWindow();
        removeOnScrollListener(this.scrollListener);
        removeOnScrollListener(this.clearKeyboardListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.RecyclerListener
    public final void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        ((PickerViewHolder) viewHolder).onViewRecycled();
    }

    public void setItemDecoration(int i, HeaderFooterAdapter headerFooterAdapter) {
        clearItemDecoration();
        addItemDecoration(new RecyclerViewCornerDecoration(this.mContext, this.mSeslRoundedCorner));
    }

    public SeslAppPickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0073 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v0, types: [androidx.picker.widget.SeslAppPickerView$1] */
    /* JADX WARN: Type inference failed for: r4v1, types: [androidx.picker.widget.SeslAppPickerView$2] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SeslAppPickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray typedArray;
        RuntimeException e;
        String str;
        String str2;
        AppPickerContext appPickerContext;
        Strategy appItemStrategy;
        this.mSeslRoundedCorner = 15;
        this.mViewType = 0;
        TypedArray typedArray2 = null;
        String str3 = null;
        typedArray2 = null;
        this.mOnClickEventListener = null;
        this.mOnStateChangeListener = null;
        this.clearKeyboardListener = new RecyclerView.OnScrollListener() { // from class: androidx.picker.widget.SeslAppPickerView.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public final void onScrollStateChanged(RecyclerView recyclerView, int i2) {
                if (i2 == 1) {
                    SeslAppPickerView seslAppPickerView = SeslAppPickerView.this;
                    ((InputMethodManager) seslAppPickerView.mContext.getSystemService("input_method")).hideSoftInputFromWindow(seslAppPickerView.getWindowToken(), 0);
                }
            }
        };
        this.scrollListener = new RecyclerView.OnScrollListener() { // from class: androidx.picker.widget.SeslAppPickerView.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public final void onScrollStateChanged(RecyclerView recyclerView, int i2) {
                DisposableHandle disposableHandle;
                SeslAppPickerView seslAppPickerView = SeslAppPickerView.this;
                if (i2 == 0) {
                    seslAppPickerView.getClass();
                } else if (i2 == 1 && (disposableHandle = seslAppPickerView.disposable) != null) {
                    disposableHandle.dispose();
                }
            }
        };
        this.mContext = context;
        try {
            try {
                typedArray = context.obtainStyledAttributes(attributeSet, R$styleable.SeslAppPickerView, i, 0);
                try {
                    try {
                        str2 = typedArray.getString(2);
                        try {
                            str3 = typedArray.getString(0);
                            int i2 = typedArray.getInt(1, 15);
                            this.mSeslRoundedCorner = i2;
                            LogTagHelperKt.debug(this, "init strategy=" + str2 + ", roundedCorner=" + i2);
                            typedArray.recycle();
                        } catch (RuntimeException e2) {
                            e = e2;
                            String str4 = str3;
                            typedArray2 = typedArray;
                            str = str4;
                            e.printStackTrace();
                            if (typedArray2 != null) {
                                typedArray2.recycle();
                            }
                            str3 = str;
                            if (str3 == null) {
                            }
                            appPickerContext = (AppPickerContext) Class.forName(str3).getConstructor(Context.class).newInstance(context);
                            LogTagHelperKt.debug(this, "used appPickerContext: " + appPickerContext);
                            this.mRecyclerListener = this;
                            this.mAppDataRepository = (AppDataRepository) appPickerContext.appDataRepository$delegate.getValue();
                            this.mSelectStateLoader = (SelectStateLoader) appPickerContext.selectStateLoader$delegate.getValue();
                            Objects.requireNonNull(str2);
                            appItemStrategy = (Strategy) Class.forName(str2).getConstructor(AppPickerContext.class).newInstance(appPickerContext);
                            ViewDataController viewDataController = new ViewDataController(appItemStrategy);
                            this.mViewDataController = viewDataController;
                            SeslAppPickerView$$ExternalSyntheticLambda1 seslAppPickerView$$ExternalSyntheticLambda1 = new SeslAppPickerView$$ExternalSyntheticLambda1(this, 0);
                            this.mSelectStateLoader.onSelectListener = new C03743();
                            ((ArrayList) viewDataController.listeners).add(new SeslAppPickerView$$ExternalSyntheticLambda2(this, seslAppPickerView$$ExternalSyntheticLambda1));
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (typedArray != null) {
                            typedArray.recycle();
                        }
                        throw th;
                    }
                } catch (RuntimeException e3) {
                    e = e3;
                    str2 = null;
                    typedArray2 = typedArray;
                    str = null;
                }
            } catch (RuntimeException e4) {
                e = e4;
                str = null;
                str2 = null;
            }
            if (str3 == null) {
                try {
                    str3 = AppPickerContext.class.getName();
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | NullPointerException | InvocationTargetException unused) {
                    appPickerContext = new AppPickerContext(context);
                }
            }
            appPickerContext = (AppPickerContext) Class.forName(str3).getConstructor(Context.class).newInstance(context);
            LogTagHelperKt.debug(this, "used appPickerContext: " + appPickerContext);
            this.mRecyclerListener = this;
            this.mAppDataRepository = (AppDataRepository) appPickerContext.appDataRepository$delegate.getValue();
            this.mSelectStateLoader = (SelectStateLoader) appPickerContext.selectStateLoader$delegate.getValue();
            try {
                Objects.requireNonNull(str2);
                appItemStrategy = (Strategy) Class.forName(str2).getConstructor(AppPickerContext.class).newInstance(appPickerContext);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | NullPointerException | InvocationTargetException unused2) {
                appItemStrategy = new AppItemStrategy(appPickerContext);
            }
            ViewDataController viewDataController2 = new ViewDataController(appItemStrategy);
            this.mViewDataController = viewDataController2;
            SeslAppPickerView$$ExternalSyntheticLambda1 seslAppPickerView$$ExternalSyntheticLambda12 = new SeslAppPickerView$$ExternalSyntheticLambda1(this, 0);
            this.mSelectStateLoader.onSelectListener = new C03743();
            ((ArrayList) viewDataController2.listeners).add(new SeslAppPickerView$$ExternalSyntheticLambda2(this, seslAppPickerView$$ExternalSyntheticLambda12));
        } catch (Throwable th2) {
            th = th2;
            typedArray = typedArray2;
        }
    }
}
