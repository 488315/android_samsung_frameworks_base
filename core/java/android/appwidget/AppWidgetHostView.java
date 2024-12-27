package android.appwidget;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.LoadedApk;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.SizeF;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.RemoteViewsAdapter;
import android.widget.TextView;

import com.android.internal.R;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class AppWidgetHostView extends FrameLayout implements AppWidgetHost.AppWidgetHostListener {
    private static final String APPWIDGET_HOST_OPTIONS_HOST_TYPE = "semHostType";
    public static final int APPWIDGET_HOST_TYPE_COVER = 4;
    public static final int APPWIDGET_HOST_TYPE_HOME = 1;
    public static final int APPWIDGET_HOST_TYPE_LOCK_AND_AOD = 2;
    private static final int FIRST_RESOURCE_COLOR_ID = 17170461;
    private static final LayoutInflater.Filter INFLATER_FILTER =
            new LayoutInflater.Filter() { // from class:
                // android.appwidget.AppWidgetHostView$$ExternalSyntheticLambda3
                @Override // android.view.LayoutInflater.Filter
                public final boolean onLoadClass(Class cls) {
                    boolean isAnnotationPresent;
                    isAnnotationPresent = cls.isAnnotationPresent(RemoteViews.RemoteView.class);
                    return isAnnotationPresent;
                }
            };
    private static final String KEY_INFLATION_ID = "inflation_id";
    private static final String KEY_JAILED_ARRAY = "jail";
    private static final int LAST_RESOURCE_COLOR_ID = 17170525;
    static final boolean LOGD = false;
    static final String TAG = "AppWidgetHostView";
    static final int VIEW_MODE_CONTENT = 1;
    static final int VIEW_MODE_DEFAULT = 3;
    static final int VIEW_MODE_ERROR = 2;
    static final int VIEW_MODE_NOINIT = 0;
    int mAppWidgetId;
    private Executor mAsyncExecutor;
    boolean mColorMappingChanged;
    private RemoteViews.ColorResources mColorResources;
    private boolean mConfigChanged;
    Context mContext;
    private SizeF mCurrentSize;
    private long mDelayedRestoredInflationId;
    private SparseArray<Parcelable> mDelayedRestoredState;
    private int mHostType;
    AppWidgetProviderInfo mInfo;
    private RemoteViews.InteractionHandler mInteractionHandler;
    private boolean mIsForcedOrientation;
    private boolean mIsPortrait;
    private Configuration mLastConfig;
    private CancellationSignal mLastExecutionSignal;
    private RemoteViews mLastInflatedRemoteViews;
    private long mLastInflatedRemoteViewsId;
    int mLastViewIdToDataChanged;
    private View.OnClickListener mOnContentAppliedListener;
    private boolean mOnLightBackground;
    Context mRemoteContext;
    View mView;
    int mViewMode;

    public AppWidgetHostView(Context context) {
        this(context, 17432576, 17432577);
    }

    public AppWidgetHostView(Context context, RemoteViews.InteractionHandler handler) {
        this(context, 17432576, 17432577);
        this.mInteractionHandler = getHandler(handler);
    }

    public AppWidgetHostView(Context context, int animationIn, int animationOut) {
        super(context);
        this.mViewMode = 0;
        this.mColorMappingChanged = false;
        this.mCurrentSize = null;
        this.mColorResources = null;
        this.mLastInflatedRemoteViews = null;
        this.mLastInflatedRemoteViewsId = -1L;
        this.mLastViewIdToDataChanged = -1;
        this.mContext = context;
        setIsRootNamespace(true);
    }

    public void setInteractionHandler(RemoteViews.InteractionHandler handler) {
        this.mInteractionHandler = getHandler(handler);
    }

    public static class AdapterChildHostView extends AppWidgetHostView {
        public AdapterChildHostView(Context context) {
            super(context);
        }

        @Override // android.appwidget.AppWidgetHostView
        public Context getRemoteContextEnsuringCorrectCachedApkPath() {
            return null;
        }
    }

    public void setAppWidget(int appWidgetId, AppWidgetProviderInfo info) {
        this.mAppWidgetId = appWidgetId;
        this.mInfo = info;
        Rect padding = getDefaultPadding();
        setPadding(padding.left, padding.top, padding.right, padding.bottom);
        if (info != null) {
            String description = info.loadLabel(getContext().getPackageManager());
            if ((info.providerInfo.applicationInfo.flags & 1073741824) != 0) {
                description =
                        Resources.getSystem()
                                .getString(R.string.suspended_widget_accessibility, description);
            }
            setContentDescription(description);
        }
    }

    public static Rect getDefaultPaddingForWidget(
            Context context, ComponentName component, Rect padding) {
        return getDefaultPaddingForWidget(context, padding);
    }

    private static Rect getDefaultPaddingForWidget(Context context, Rect padding) {
        if (padding == null) {
            padding = new Rect(0, 0, 0, 0);
        } else {
            padding.set(0, 0, 0, 0);
        }
        Resources r = context.getResources();
        padding.left = r.getDimensionPixelSize(R.dimen.default_app_widget_padding_left);
        padding.right = r.getDimensionPixelSize(R.dimen.default_app_widget_padding_right);
        padding.top = r.getDimensionPixelSize(R.dimen.default_app_widget_padding_top);
        padding.bottom = r.getDimensionPixelSize(R.dimen.default_app_widget_padding_bottom);
        return padding;
    }

    private Rect getDefaultPadding() {
        return getDefaultPaddingForWidget(this.mContext, null);
    }

    public int getAppWidgetId() {
        return this.mAppWidgetId;
    }

    public AppWidgetProviderInfo getAppWidgetInfo() {
        return this.mInfo;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        SparseArray<Parcelable> jail = new SparseArray<>();
        super.dispatchSaveInstanceState(jail);
        Bundle bundle = new Bundle();
        bundle.putSparseParcelableArray(KEY_JAILED_ARRAY, jail);
        bundle.putLong(KEY_INFLATION_ID, this.mLastInflatedRemoteViewsId);
        container.put(generateId(), bundle);
        container.put(generateId(), bundle);
    }

    private int generateId() {
        int id = getId();
        return id == -1 ? this.mAppWidgetId : id;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        Parcelable parcelable = container.get(generateId());
        SparseArray<Parcelable> jail = null;
        long inflationId = -1;
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            jail = bundle.getSparseParcelableArray(KEY_JAILED_ARRAY);
            inflationId = bundle.getLong(KEY_INFLATION_ID, -1L);
        }
        if (jail == null) {
            jail = new SparseArray<>();
        }
        this.mDelayedRestoredState = jail;
        this.mDelayedRestoredInflationId = inflationId;
        restoreInstanceState();
    }

    void restoreInstanceState() {
        long inflationId = this.mDelayedRestoredInflationId;
        SparseArray<Parcelable> state = this.mDelayedRestoredState;
        if (inflationId == -1 || inflationId != this.mLastInflatedRemoteViewsId) {
            return;
        }
        this.mDelayedRestoredInflationId = -1L;
        this.mDelayedRestoredState = null;
        try {
            super.dispatchRestoreInstanceState(state);
        } catch (Exception e) {
            Log.e(
                    TAG,
                    "failed to restoreInstanceState for widget id: "
                            + this.mAppWidgetId
                            + ", "
                            + (this.mInfo == null ? "null" : this.mInfo.provider),
                    e);
        }
    }

    private SizeF computeSizeFromLayout(int left, int top, int right, int bottom) {
        float density = getResources().getDisplayMetrics().density;
        return new SizeF(
                (((right - left) - getPaddingLeft()) - getPaddingRight()) / density,
                (((bottom - top) - getPaddingTop()) - getPaddingBottom()) / density);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        RemoteViews toApply;
        try {
            SizeF oldSize = this.mCurrentSize;
            SizeF newSize = computeSizeFromLayout(left, top, right, bottom);
            this.mCurrentSize = newSize;
            if (this.mLastInflatedRemoteViews != null
                    && (toApply =
                                    this.mLastInflatedRemoteViews.getRemoteViewsToApplyIfDifferent(
                                            oldSize, newSize))
                            != null) {
                applyRemoteViews(toApply, false);
                measureChildWithMargins(
                        this.mView,
                        View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824),
                        0,
                        View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824),
                        0);
            }
            super.onLayout(changed, left, top, right, bottom);
        } catch (RuntimeException e) {
            Log.e(TAG, "Remote provider threw runtime exception, using error view instead.", e);
            handleViewError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleViewError() {
        removeViewInLayout(this.mView);
        View child = getErrorView();
        prepareView(child);
        addViewInLayout(child, 0, child.getLayoutParams());
        measureChild(
                child,
                View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824),
                View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
        child.layout(
                0,
                0,
                child.getMeasuredWidth() + this.mPaddingLeft + this.mPaddingRight,
                child.getMeasuredHeight() + this.mPaddingTop + this.mPaddingBottom);
        this.mView = child;
        this.mViewMode = 2;
    }

    @Deprecated
    public void updateAppWidgetSize(
            Bundle newOptions, int minWidth, int minHeight, int maxWidth, int maxHeight) {
        updateAppWidgetSize(newOptions, minWidth, minHeight, maxWidth, maxHeight, false);
    }

    public void updateAppWidgetSize(Bundle newOptions, List<SizeF> sizes) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this.mContext);
        Rect padding = getDefaultPadding();
        float density = getResources().getDisplayMetrics().density;
        float xPaddingDips = (padding.left + padding.right) / density;
        float yPaddingDips = (padding.top + padding.bottom) / density;
        ArrayList<SizeF> paddedSizes = new ArrayList<>(sizes.size());
        float minWidth = Float.MAX_VALUE;
        float maxWidth = 0.0f;
        float minHeight = Float.MAX_VALUE;
        float maxHeight = 0.0f;
        int i = 0;
        while (i < sizes.size()) {
            SizeF size = sizes.get(i);
            SizeF paddedSize =
                    new SizeF(
                            Math.max(0.0f, size.getWidth() - xPaddingDips),
                            Math.max(0.0f, size.getHeight() - yPaddingDips));
            paddedSizes.add(paddedSize);
            minWidth = Math.min(minWidth, paddedSize.getWidth());
            maxWidth = Math.max(maxWidth, paddedSize.getWidth());
            minHeight = Math.min(minHeight, paddedSize.getHeight());
            maxHeight = Math.max(maxHeight, paddedSize.getHeight());
            i++;
            padding = padding;
            density = density;
        }
        if (paddedSizes.equals(
                widgetManager
                        .getAppWidgetOptions(this.mAppWidgetId)
                        .getParcelableArrayList(AppWidgetManager.OPTION_APPWIDGET_SIZES))) {
            return;
        }
        Bundle options = newOptions.deepCopy();
        options.putInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, (int) minWidth);
        options.putInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, (int) minHeight);
        options.putInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH, (int) maxWidth);
        options.putInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, (int) maxHeight);
        options.putParcelableArrayList(AppWidgetManager.OPTION_APPWIDGET_SIZES, paddedSizes);
        updateAppWidgetOptions(options);
    }

    public void updateAppWidgetSize(
            Bundle newOptions,
            int minWidth,
            int minHeight,
            int maxWidth,
            int maxHeight,
            boolean ignorePadding) {
        Bundle newOptions2;
        if (newOptions != null) {
            newOptions2 = newOptions;
        } else {
            newOptions2 = new Bundle();
        }
        Rect padding = getDefaultPadding();
        float density = getResources().getDisplayMetrics().density;
        int xPaddingDips = (int) ((padding.left + padding.right) / density);
        int yPaddingDips = (int) ((padding.top + padding.bottom) / density);
        int newMinWidth = minWidth - (ignorePadding ? 0 : xPaddingDips);
        int newMinHeight = minHeight - (ignorePadding ? 0 : yPaddingDips);
        int newMaxWidth = maxWidth - (ignorePadding ? 0 : xPaddingDips);
        int newMaxHeight = maxHeight - (ignorePadding ? 0 : yPaddingDips);
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this.mContext);
        Bundle oldOptions = widgetManager.getAppWidgetOptions(this.mAppWidgetId);
        boolean needsUpdate = false;
        if (newMinWidth != oldOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)
                || newMinHeight != oldOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT)
                || newMaxWidth != oldOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH)
                || newMaxHeight
                        != oldOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT)) {
            needsUpdate = true;
        }
        if (needsUpdate) {
            newOptions2.putInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, newMinWidth);
            newOptions2.putInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, newMinHeight);
            newOptions2.putInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH, newMaxWidth);
            newOptions2.putInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, newMaxHeight);
            newOptions2.putParcelableArrayList(
                    AppWidgetManager.OPTION_APPWIDGET_SIZES, new ArrayList<>());
            updateAppWidgetOptions(newOptions2);
        }
    }

    public void updateAppWidgetOptions(Bundle options) {
        AppWidgetManager.getInstance(this.mContext)
                .updateAppWidgetOptions(this.mAppWidgetId, options);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        Context context = this.mRemoteContext != null ? this.mRemoteContext : this.mContext;
        return new FrameLayout.LayoutParams(context, attrs);
    }

    public void setExecutor(Executor executor) {
        if (this.mLastExecutionSignal != null) {
            this.mLastExecutionSignal.cancel();
            this.mLastExecutionSignal = null;
        }
        this.mAsyncExecutor = executor;
    }

    public void setOnLightBackground(boolean onLightBackground) {
        this.mOnLightBackground = onLightBackground;
    }

    @Override // android.appwidget.AppWidgetHost.AppWidgetHostListener
    public void onUpdateProviderInfo(AppWidgetProviderInfo info) {
        setAppWidget(this.mAppWidgetId, info);
        this.mViewMode = 0;
        updateAppWidget(null);
    }

    @Override // android.appwidget.AppWidgetHost.AppWidgetHostListener
    public void updateAppWidget(RemoteViews remoteViews) {
        this.mLastInflatedRemoteViews = remoteViews;
        applyRemoteViews(remoteViews, true);
        if (this.mInfo != null
                && this.mInfo.getProfile() != null
                && SemDualAppManager.isDualAppId(this.mInfo.getProfile().getIdentifier())) {
            SemDualAppManager.drawDualAppBadge(this.mContext, this, this.mInfo.getProfile());
        }
        if (this.mInfo != null
                && this.mInfo.getProfile() != null
                && SemPersonaManager.isKnoxId(this.mInfo.getProfile().getIdentifier())) {
            SemPersonaManager.drawKnoxAppBadge(this.mContext, this, this.mInfo.getProfile());
        }
    }

    private void reapplyLastRemoteViews() {
        SparseArray<Parcelable> savedState = new SparseArray<>();
        saveHierarchyState(savedState);
        applyRemoteViews(this.mLastInflatedRemoteViews, true);
        restoreHierarchyState(savedState);
    }

    protected void applyRemoteViews(RemoteViews remoteViews, boolean useAsyncIfPossible) {
        boolean recycled = false;
        View content = null;
        Exception exception = null;
        this.mLastInflatedRemoteViewsId = -1L;
        if (this.mLastExecutionSignal != null) {
            this.mLastExecutionSignal.cancel();
            this.mLastExecutionSignal = null;
        }
        if (remoteViews == null) {
            if (this.mViewMode == 3) {
                return;
            }
            content = getDefaultView();
            this.mViewMode = 3;
        } else {
            if (this.mIsForcedOrientation) {
                remoteViews.setOrientation(this.mIsPortrait);
            }
            RemoteViews rvToApply =
                    remoteViews.getRemoteViewsToApply(this.mContext, this.mCurrentSize);
            if (this.mOnLightBackground) {
                rvToApply = rvToApply.getDarkTextViews();
            }
            if (this.mAsyncExecutor != null && useAsyncIfPossible) {
                inflateAsync(rvToApply);
                return;
            }
            this.mRemoteContext = getRemoteContextEnsuringCorrectCachedApkPath();
            if (!this.mColorMappingChanged
                    && rvToApply.canRecycleView(this.mView)
                    && !this.mConfigChanged) {
                try {
                    rvToApply.reapply(
                            this.mContext,
                            this.mView,
                            this.mInteractionHandler,
                            this.mCurrentSize,
                            this.mColorResources);
                    content = this.mView;
                    this.mLastInflatedRemoteViewsId = rvToApply.computeUniqueId(remoteViews);
                    recycled = true;
                } catch (RuntimeException e) {
                    exception = e;
                }
            }
            if (content == null) {
                try {
                    content =
                            rvToApply.apply(
                                    this.mContext,
                                    this,
                                    this.mInteractionHandler,
                                    this.mCurrentSize,
                                    this.mColorResources);
                    this.mLastInflatedRemoteViewsId = rvToApply.computeUniqueId(remoteViews);
                } catch (RuntimeException e2) {
                    exception = e2;
                }
            }
            this.mConfigChanged = false;
            this.mViewMode = 1;
        }
        applyContent(content, recycled, exception);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyContent(View content, boolean recycled, Exception exception) {
        this.mColorMappingChanged = false;
        if (content == null) {
            if (this.mViewMode == 2) {
                return;
            }
            if (exception != null) {
                Log.w(TAG, "Error inflating RemoteViews", exception);
            }
            content = getErrorView();
            this.mViewMode = 2;
        }
        if (!recycled) {
            try {
                prepareView(content);
                addView(content);
            } catch (Exception e) {
                removeViewInLayout(content);
                View child = getErrorView();
                prepareView(child);
                addViewInLayout(child, 0, child.getLayoutParams());
                measureChild(
                        child,
                        View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824),
                        View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                child.layout(
                        0,
                        0,
                        child.getMeasuredWidth() + this.mPaddingLeft + this.mPaddingRight,
                        child.getMeasuredHeight() + this.mPaddingTop + this.mPaddingBottom);
                this.mView = child;
                this.mViewMode = 2;
                Log.e(TAG, "Error on prepare/add Views", e);
                return;
            }
        }
        if (this.mView != content) {
            removeView(this.mView);
            this.mView = content;
        }
        if (this.mOnContentAppliedListener != null) {
            this.mOnContentAppliedListener.onClick(content);
        }
    }

    private void hidden_semSetOnContentAppliedListener(View.OnClickListener l) {
        this.mOnContentAppliedListener = l;
    }

    private void inflateAsync(RemoteViews remoteViews) {
        this.mRemoteContext = getRemoteContextEnsuringCorrectCachedApkPath();
        int layoutId = remoteViews.getLayoutId();
        if (this.mLastExecutionSignal != null) {
            this.mLastExecutionSignal.cancel();
        }
        if (!this.mColorMappingChanged
                && remoteViews.canRecycleView(this.mView)
                && !this.mConfigChanged) {
            try {
                this.mLastExecutionSignal =
                        remoteViews.reapplyAsync(
                                this.mContext,
                                this.mView,
                                this.mAsyncExecutor,
                                new ViewApplyListener(remoteViews, layoutId, true),
                                this.mInteractionHandler,
                                this.mCurrentSize,
                                this.mColorResources);
            } catch (Exception e) {
            }
        }
        if (this.mLastExecutionSignal == null) {
            this.mLastExecutionSignal =
                    remoteViews.applyAsync(
                            this.mContext,
                            this,
                            this.mAsyncExecutor,
                            new ViewApplyListener(remoteViews, layoutId, false),
                            this.mInteractionHandler,
                            this.mCurrentSize,
                            this.mColorResources);
        }
        this.mConfigChanged = false;
    }

    private class ViewApplyListener implements RemoteViews.OnViewAppliedListener {
        private final boolean mIsReapply;
        private final int mLayoutId;
        private final RemoteViews mViews;

        ViewApplyListener(RemoteViews views, int layoutId, boolean isReapply) {
            this.mViews = views;
            this.mLayoutId = layoutId;
            this.mIsReapply = isReapply;
        }

        @Override // android.widget.RemoteViews.OnViewAppliedListener
        public void onViewApplied(View v) {
            AppWidgetHostView.this.mViewMode = 1;
            AppWidgetHostView.this.applyContent(v, this.mIsReapply, null);
            int viewId = AppWidgetHostView.this.mLastViewIdToDataChanged;
            if (viewId > -1) {
                AppWidgetHostView.this.mLastViewIdToDataChanged = -1;
                Log.i(
                        AppWidgetHostView.TAG,
                        "onViewApplied, Trigger viewDataChanged for viewId : " + viewId);
                AppWidgetHostView.this.onViewDataChanged(viewId);
            }
            AppWidgetHostView.this.mLastInflatedRemoteViewsId =
                    this.mViews.computeUniqueId(AppWidgetHostView.this.mLastInflatedRemoteViews);
            AppWidgetHostView.this.restoreInstanceState();
            AppWidgetHostView.this.mLastExecutionSignal = null;
        }

        @Override // android.widget.RemoteViews.OnViewAppliedListener
        public void onError(Exception e) {
            if (this.mIsReapply) {
                AppWidgetHostView.this.mLastExecutionSignal =
                        this.mViews.applyAsync(
                                AppWidgetHostView.this.mContext,
                                AppWidgetHostView.this,
                                AppWidgetHostView.this.mAsyncExecutor,
                                AppWidgetHostView.this
                                .new ViewApplyListener(this.mViews, this.mLayoutId, false),
                                AppWidgetHostView.this.mInteractionHandler,
                                AppWidgetHostView.this.mCurrentSize);
            } else {
                AppWidgetHostView.this.applyContent(null, false, e);
            }
            AppWidgetHostView.this.mLastExecutionSignal = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.appwidget.AppWidgetHost.AppWidgetHostListener
    public void onViewDataChanged(int viewId) {
        View v = findViewById(viewId);
        Log.i(TAG, "viewDataChanged, viewId = " + viewId + ", v = " + v);
        if (v != null && (v instanceof AdapterView)) {
            AdapterView adapterView = (AdapterView) v;
            Adapter adapter = adapterView.getAdapter();
            if (adapter instanceof BaseAdapter) {
                BaseAdapter baseAdapter = (BaseAdapter) adapter;
                baseAdapter.notifyDataSetChanged();
            } else if (adapter == null
                    && (adapterView
                            instanceof RemoteViewsAdapter.RemoteAdapterConnectionCallback)) {
                ((RemoteViewsAdapter.RemoteAdapterConnectionCallback) adapterView)
                        .deferNotifyDataSetChanged();
            }
            this.mLastViewIdToDataChanged = -1;
            return;
        }
        this.mLastViewIdToDataChanged = viewId;
        Log.i(TAG, "view is null, will retry when view inflating is finished.");
    }

    protected Context getRemoteContextEnsuringCorrectCachedApkPath() {
        try {
            ApplicationInfo expectedAppInfo = this.mInfo.providerInfo.applicationInfo;
            LoadedApk.checkAndUpdateApkPaths(expectedAppInfo);
            Context newContext =
                    this.mContext.createApplicationContext(
                            this.mInfo.providerInfo.applicationInfo, 4);
            if (this.mColorResources != null) {
                this.mColorResources.apply(newContext);
            }
            return newContext;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Package name " + this.mInfo.providerInfo.packageName + " not found");
            return this.mContext;
        } catch (Resources.NotFoundException e2) {
            Log.w(
                    TAG,
                    "Failed to get RemoteContext."
                            + this.mInfo.providerInfo.applicationInfo.sourceDir
                            + ", "
                            + e2,
                    e2);
            if (1 != 0) {
                String packageName = this.mInfo.providerInfo.applicationInfo.packageName;
                if (!clearResourcePackageCache(this.mContext, packageName)) {
                    return this.mContext;
                }
                try {
                    ApplicationInfo ai =
                            this.mContext.getPackageManager().getApplicationInfo(packageName, 0);
                    try {
                        return this.mContext.createApplicationContext(ai, 4);
                    } catch (PackageManager.NameNotFoundException e3) {
                        Log.e(
                                TAG,
                                "Package name "
                                        + this.mInfo.providerInfo.packageName
                                        + " not found");
                        return this.mContext;
                    }
                } catch (PackageManager.NameNotFoundException e4) {
                    Log.e(
                            TAG,
                            "Package name " + this.mInfo.providerInfo.packageName + " not found");
                    return this.mContext;
                }
            }
            return this.mContext;
        } catch (NullPointerException e5) {
            Log.e(TAG, "Error trying to create the remote context.", e5);
            return this.mContext;
        }
    }

    private boolean clearResourcePackageCache(Context context, String pkg) {
        try {
            if (context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
            }
            Field field = Class.forName("android.app.ContextImpl").getDeclaredField("mMainThread");
            field.setAccessible(true);
            Object mtObject = field.get(context);
            Field field2 = ActivityThread.class.getDeclaredField("mResourcesManager");
            field2.setAccessible(true);
            Object lockObject = field2.get(mtObject);
            Field field3 = ActivityThread.class.getDeclaredField("mResourcePackages");
            field3.setAccessible(true);
            Object rpObject = field3.get(mtObject);
            synchronized (lockObject) {
                ArrayMap<?, ?> resourcePackages = (ArrayMap) rpObject;
                resourcePackages.remove(pkg);
            }
            return true;
        } catch (ClassCastException
                | ClassNotFoundException
                | IllegalAccessException
                | IllegalArgumentException
                | NoSuchFieldException
                | NullPointerException
                | SecurityException e) {
            Log.e(TAG, "Failed to clear cache for " + pkg + ", " + e, e);
            return false;
        }
    }

    protected void prepareView(View view) {
        FrameLayout.LayoutParams requested = (FrameLayout.LayoutParams) view.getLayoutParams();
        if (requested == null) {
            requested = new FrameLayout.LayoutParams(-1, -1);
        }
        requested.gravity = 17;
        view.setLayoutParams(requested);
    }

    protected View getDefaultView() {
        View defaultView = null;
        Exception exception = null;
        try {
            if (this.mInfo != null) {
                Context theirContext = getRemoteContextEnsuringCorrectCachedApkPath();
                this.mRemoteContext = theirContext;
                LayoutInflater inflater =
                        ((LayoutInflater)
                                        theirContext.getSystemService(
                                                Context.LAYOUT_INFLATER_SERVICE))
                                .cloneInContext(theirContext);
                inflater.setFilter(INFLATER_FILTER);
                AppWidgetManager manager = AppWidgetManager.getInstance(this.mContext);
                Bundle options = manager.getAppWidgetOptions(this.mAppWidgetId);
                int layoutId = this.mInfo.initialLayout;
                if (options.containsKey(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY)) {
                    int category = options.getInt(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY);
                    if (category == 2) {
                        int kgLayoutId = this.mInfo.initialKeyguardLayout;
                        layoutId = kgLayoutId == 0 ? layoutId : kgLayoutId;
                    }
                }
                defaultView = inflater.inflate(layoutId, (ViewGroup) this, false);
                if (!(defaultView instanceof AdapterView)) {
                    defaultView.setOnClickListener(
                            new View.OnClickListener() { // from class:
                                // android.appwidget.AppWidgetHostView$$ExternalSyntheticLambda2
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    AppWidgetHostView.this.onDefaultViewClicked(view);
                                }
                            });
                }
            } else {
                Log.w(TAG, "can't inflate defaultView because mInfo is missing");
            }
        } catch (RuntimeException e) {
            exception = e;
        }
        if (exception != null) {
            Log.w(TAG, "Error inflating AppWidget " + this.mInfo, exception);
        }
        if (defaultView == null) {
            return getErrorView();
        }
        return defaultView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDefaultViewClicked(View view) {
        if (this.mInfo != null) {
            LauncherApps launcherApps =
                    (LauncherApps) getContext().getSystemService(LauncherApps.class);
            List<LauncherActivityInfo> activities =
                    launcherApps.getActivityList(
                            this.mInfo.provider.getPackageName(), this.mInfo.getProfile());
            if (!activities.isEmpty()) {
                LauncherActivityInfo ai = activities.get(0);
                launcherApps.startMainActivity(
                        ai.getComponentName(),
                        ai.getUser(),
                        RemoteViews.getSourceBounds(view),
                        null);
            }
        }
    }

    protected View getErrorView() {
        TextView tv = new TextView(this.mContext);
        tv.setText(R.string.gadget_host_error_inflating);
        tv.setBackgroundColor(Color.argb(127, 0, 0, 0));
        return tv;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoInternal(info);
        info.setClassName(AppWidgetHostView.class.getName());
    }

    public ActivityOptions createSharedElementActivityOptions(
            int[] sharedViewIds, String[] sharedViewNames, Intent fillInIntent) {
        Context parentContext = getContext();
        while ((parentContext instanceof ContextWrapper) && !(parentContext instanceof Activity)) {
            parentContext = ((ContextWrapper) parentContext).getBaseContext();
        }
        if (!(parentContext instanceof Activity)) {
            return null;
        }
        List<Pair<View, String>> sharedElements = new ArrayList<>();
        Bundle extras = new Bundle();
        for (int i = 0; i < sharedViewIds.length; i++) {
            View view = findViewById(sharedViewIds[i]);
            if (view != null) {
                sharedElements.add(Pair.create(view, sharedViewNames[i]));
                extras.putParcelable(sharedViewNames[i], RemoteViews.getSourceBounds(view));
            }
        }
        if (sharedElements.isEmpty()) {
            return null;
        }
        fillInIntent.putExtra(RemoteViews.EXTRA_SHARED_ELEMENT_BOUNDS, extras);
        ActivityOptions opts =
                ActivityOptions.makeSceneTransitionAnimation(
                        (Activity) parentContext,
                        (Pair[]) sharedElements.toArray(new Pair[sharedElements.size()]));
        opts.setPendingIntentLaunchFlags(268435456);
        return opts;
    }

    private RemoteViews.InteractionHandler getHandler(
            final RemoteViews.InteractionHandler handler) {
        return new RemoteViews.InteractionHandler() { // from class:
            // android.appwidget.AppWidgetHostView$$ExternalSyntheticLambda0
            @Override // android.widget.RemoteViews.InteractionHandler
            public final boolean onInteraction(
                    View view,
                    PendingIntent pendingIntent,
                    RemoteViews.RemoteResponse remoteResponse) {
                boolean lambda$getHandler$1;
                lambda$getHandler$1 =
                        AppWidgetHostView.this.lambda$getHandler$1(
                                handler, view, pendingIntent, remoteResponse);
                return lambda$getHandler$1;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getHandler$1(
            RemoteViews.InteractionHandler handler,
            View view,
            PendingIntent pendingIntent,
            RemoteViews.RemoteResponse response) {
        AppWidgetManager manager = AppWidgetManager.getInstance(this.mContext);
        if (manager != null) {
            manager.noteAppWidgetTapped(this.mAppWidgetId);
        }
        if (handler != null) {
            return handler.onInteraction(view, pendingIntent, response);
        }
        return RemoteViews.startPendingIntent(view, pendingIntent, response.getLaunchOptions(view));
    }

    public void setColorResources(SparseIntArray colorMapping) {
        if (this.mColorResources != null
                && isSameColorMapping(this.mColorResources.getColorMapping(), colorMapping)) {
            return;
        }
        setColorResources(RemoteViews.ColorResources.create(this.mContext, colorMapping));
    }

    private void setColorResourcesStates(RemoteViews.ColorResources colorResources) {
        this.mColorResources = colorResources;
        this.mColorMappingChanged = true;
        this.mViewMode = 0;
    }

    public void setColorResources(RemoteViews.ColorResources colorResources) {
        if (colorResources == this.mColorResources) {
            return;
        }
        setColorResourcesStates(colorResources);
        reapplyLastRemoteViews();
    }

    public void setColorResourcesNoReapply(RemoteViews.ColorResources colorResources) {
        if (colorResources == this.mColorResources) {
            return;
        }
        setColorResourcesStates(colorResources);
    }

    private boolean isSameColorMapping(SparseIntArray oldColors, SparseIntArray newColors) {
        if (oldColors.size() != newColors.size()) {
            return false;
        }
        for (int i = 0; i < oldColors.size(); i++) {
            if (oldColors.keyAt(i) != newColors.keyAt(i)
                    || oldColors.valueAt(i) != newColors.valueAt(i)) {
                return false;
            }
        }
        return true;
    }

    public void resetColorResources() {
        if (this.mColorResources != null) {
            this.mColorResources = null;
            this.mColorMappingChanged = true;
            this.mViewMode = 0;
            reapplyLastRemoteViews();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        try {
            super.dispatchDraw(canvas);
        } catch (Exception e) {
            Log.e(TAG, "Drawing view failed: " + e);
            post(
                    new Runnable() { // from class:
                        // android.appwidget.AppWidgetHostView$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppWidgetHostView.this.handleViewError();
                        }
                    });
        }
    }

    @Override // android.view.ViewGroup
    public boolean semDispatchTooltipHoverEvent(MotionEvent event) {
        if (event.getToolType(0) == 2) {
            return true;
        }
        return super.semDispatchTooltipHoverEvent(event);
    }

    public void semForceOrientation(boolean forced, boolean isPortrait) {
        Log.d(TAG, "force orientation - forced=" + forced + ", isPortrait=" + isPortrait);
        this.mIsForcedOrientation = forced;
        if (this.mIsForcedOrientation) {
            this.mIsPortrait = isPortrait;
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mLastConfig == null) {
            this.mLastConfig = newConfig;
            this.mConfigChanged = true;
        } else {
            if (this.mLastConfig.equals(newConfig)) {
                return;
            }
            this.mLastConfig = newConfig;
            this.mConfigChanged = true;
        }
    }

    private void hidden_semSetOptions(Bundle options) {
        this.mHostType = options.getInt(APPWIDGET_HOST_OPTIONS_HOST_TYPE, 0);
    }

    public int getHostType() {
        return this.mHostType;
    }
}
