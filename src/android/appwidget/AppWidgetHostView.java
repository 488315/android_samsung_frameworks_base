package android.appwidget;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.flags.Flags;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.ArraySet;
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
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.RemoteViewsAdapter;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class AppWidgetHostView extends FrameLayout implements AppWidgetHost.AppWidgetHostListener {
    private static final String APPWIDGET_HOST_OPTIONS_HOST_TYPE = "semHostType";
    public static final int APPWIDGET_HOST_TYPE_COVER = 4;
    public static final int APPWIDGET_HOST_TYPE_HOME = 1;
    public static final int APPWIDGET_HOST_TYPE_LOCK_AND_AOD = 2;
    private static final int FIRST_RESOURCE_COLOR_ID = 17170461;
    private static final LayoutInflater.Filter INFLATER_FILTER = new LayoutInflater.Filter() { // from class: android.appwidget.AppWidgetHostView$$ExternalSyntheticLambda3
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
    private InteractionLogger mInteractionLogger;
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

    public AppWidgetHostView(Context context, RemoteViews.InteractionHandler interactionHandler) {
        this(context, 17432576, 17432577);
        setInteractionHandler(interactionHandler);
    }

    public AppWidgetHostView(Context context, int i, int i2) {
        super(context);
        this.mViewMode = 0;
        this.mColorMappingChanged = false;
        this.mInteractionLogger = new InteractionLogger();
        this.mCurrentSize = null;
        this.mColorResources = null;
        this.mLastInflatedRemoteViews = null;
        this.mLastInflatedRemoteViewsId = -1L;
        this.mLastViewIdToDataChanged = -1;
        this.mContext = context;
        setIsRootNamespace(true);
    }

    public void setInteractionHandler(RemoteViews.InteractionHandler interactionHandler) {
        if (interactionHandler instanceof InteractionLogger) {
            this.mInteractionLogger = (InteractionLogger) interactionHandler;
        } else {
            this.mInteractionLogger = new InteractionLogger(interactionHandler);
        }
    }

    public InteractionLogger getInteractionLogger() {
        return this.mInteractionLogger;
    }

    public static class AdapterChildHostView extends AppWidgetHostView {
        @Override // android.appwidget.AppWidgetHostView
        public Context getRemoteContextEnsuringCorrectCachedApkPath() {
            return null;
        }

        public AdapterChildHostView(Context context) {
            super(context);
        }
    }

    public void setAppWidget(int i, AppWidgetProviderInfo appWidgetProviderInfo) {
        this.mAppWidgetId = i;
        this.mInfo = appWidgetProviderInfo;
        Rect defaultPadding = getDefaultPadding();
        setPadding(defaultPadding.left, defaultPadding.top, defaultPadding.right, defaultPadding.bottom);
        if (appWidgetProviderInfo != null) {
            String loadLabel = appWidgetProviderInfo.loadLabel(getContext().getPackageManager());
            if ((appWidgetProviderInfo.providerInfo.applicationInfo.flags & 1073741824) != 0) {
                loadLabel = Resources.getSystem().getString(R.string.suspended_widget_accessibility, loadLabel);
            }
            setContentDescription(loadLabel);
        }
    }

    public static Rect getDefaultPaddingForWidget(Context context, ComponentName componentName, Rect rect) {
        return getDefaultPaddingForWidget(context, rect);
    }

    private static Rect getDefaultPaddingForWidget(Context context, Rect rect) {
        if (rect == null) {
            rect = new Rect(0, 0, 0, 0);
        } else {
            rect.set(0, 0, 0, 0);
        }
        Resources resources = context.getResources();
        rect.left = resources.getDimensionPixelSize(R.dimen.default_app_widget_padding_left);
        rect.right = resources.getDimensionPixelSize(R.dimen.default_app_widget_padding_right);
        rect.top = resources.getDimensionPixelSize(R.dimen.default_app_widget_padding_top);
        rect.bottom = resources.getDimensionPixelSize(R.dimen.default_app_widget_padding_bottom);
        return rect;
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
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        SparseArray<Parcelable> sparseArray2 = new SparseArray<>();
        super.dispatchSaveInstanceState(sparseArray2);
        Bundle bundle = new Bundle();
        bundle.putSparseParcelableArray(KEY_JAILED_ARRAY, sparseArray2);
        bundle.putLong(KEY_INFLATION_ID, this.mLastInflatedRemoteViewsId);
        sparseArray.put(generateId(), bundle);
        sparseArray.put(generateId(), bundle);
    }

    private int generateId() {
        int id = getId();
        return id == -1 ? this.mAppWidgetId : id;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        SparseArray<Parcelable> sparseArray2;
        Parcelable parcelable = sparseArray.get(generateId());
        long j = -1;
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            sparseArray2 = bundle.getSparseParcelableArray(KEY_JAILED_ARRAY);
            j = bundle.getLong(KEY_INFLATION_ID, -1L);
        } else {
            sparseArray2 = null;
        }
        if (sparseArray2 == null) {
            sparseArray2 = new SparseArray<>();
        }
        this.mDelayedRestoredState = sparseArray2;
        this.mDelayedRestoredInflationId = j;
        restoreInstanceState();
    }

    void restoreInstanceState() {
        long j = this.mDelayedRestoredInflationId;
        SparseArray<Parcelable> sparseArray = this.mDelayedRestoredState;
        if (j == -1 || j != this.mLastInflatedRemoteViewsId) {
            return;
        }
        this.mDelayedRestoredInflationId = -1L;
        this.mDelayedRestoredState = null;
        try {
            super.dispatchRestoreInstanceState(sparseArray);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("failed to restoreInstanceState for widget id: ");
            sb.append(this.mAppWidgetId);
            sb.append(", ");
            sb.append(this.mInfo == null ? PerfettoProtoLogImpl.NULL_STRING : this.mInfo.provider);
            Log.e(TAG, sb.toString(), e);
        }
    }

    private SizeF computeSizeFromLayout(int i, int i2, int i3, int i4) {
        float f = getResources().getDisplayMetrics().density;
        return new SizeF((((i3 - i) - getPaddingLeft()) - getPaddingRight()) / f, (((i4 - i2) - getPaddingTop()) - getPaddingBottom()) / f);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        RemoteViews remoteViewsToApplyIfDifferent;
        try {
            SizeF sizeF = this.mCurrentSize;
            SizeF computeSizeFromLayout = computeSizeFromLayout(i, i2, i3, i4);
            this.mCurrentSize = computeSizeFromLayout;
            RemoteViews remoteViews = this.mLastInflatedRemoteViews;
            if (remoteViews != null && (remoteViewsToApplyIfDifferent = remoteViews.getRemoteViewsToApplyIfDifferent(sizeF, computeSizeFromLayout)) != null) {
                applyRemoteViews(remoteViewsToApplyIfDifferent, false);
                try {
                    measureChildWithMargins(this.mView, View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), 0, View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824), 0);
                } catch (RuntimeException e) {
                    e = e;
                    this = this;
                    Log.e(TAG, "Remote provider threw runtime exception, using error view instead.", e);
                    this.handleViewError();
                    return;
                }
            }
            if (z) {
                final InteractionLogger interactionLogger = this.mInteractionLogger;
                Objects.requireNonNull(interactionLogger);
                post(new Runnable() { // from class: android.appwidget.AppWidgetHostView$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppWidgetHostView.InteractionLogger.this.onPositionChanged();
                    }
                });
            }
            super.onLayout(z, i, i2, i3, i4);
        } catch (RuntimeException e2) {
            e = e2;
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.mInteractionLogger.onWindowFocusChanged(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleViewError() {
        removeViewInLayout(this.mView);
        View errorView = getErrorView();
        prepareView(errorView);
        addViewInLayout(errorView, 0, errorView.getLayoutParams());
        measureChild(errorView, View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
        errorView.layout(0, 0, errorView.getMeasuredWidth() + this.mPaddingLeft + this.mPaddingRight, errorView.getMeasuredHeight() + this.mPaddingTop + this.mPaddingBottom);
        this.mView = errorView;
        this.mViewMode = 2;
    }

    @Deprecated
    public void updateAppWidgetSize(Bundle bundle, int i, int i2, int i3, int i4) {
        updateAppWidgetSize(bundle, i, i2, i3, i4, false);
    }

    public void updateAppWidgetSize(Bundle bundle, List<SizeF> list) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.mContext);
        Rect defaultPadding = getDefaultPadding();
        float f = getResources().getDisplayMetrics().density;
        float f2 = (defaultPadding.left + defaultPadding.right) / f;
        float f3 = (defaultPadding.top + defaultPadding.bottom) / f;
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>(list.size());
        float f4 = Float.MAX_VALUE;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = Float.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            SizeF sizeF = list.get(i);
            SizeF sizeF2 = new SizeF(Math.max(0.0f, sizeF.getWidth() - f2), Math.max(0.0f, sizeF.getHeight() - f3));
            arrayList.add(sizeF2);
            f4 = Math.min(f4, sizeF2.getWidth());
            f5 = Math.max(f5, sizeF2.getWidth());
            f7 = Math.min(f7, sizeF2.getHeight());
            f6 = Math.max(f6, sizeF2.getHeight());
        }
        if (arrayList.equals(appWidgetManager.getAppWidgetOptions(this.mAppWidgetId).getParcelableArrayList(AppWidgetManager.OPTION_APPWIDGET_SIZES))) {
            return;
        }
        Bundle deepCopy = bundle.deepCopy();
        deepCopy.putInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, (int) f4);
        deepCopy.putInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, (int) f7);
        deepCopy.putInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH, (int) f5);
        deepCopy.putInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, (int) f6);
        deepCopy.putParcelableArrayList(AppWidgetManager.OPTION_APPWIDGET_SIZES, arrayList);
        updateAppWidgetOptions(deepCopy);
    }

    public void updateAppWidgetSize(Bundle bundle, int i, int i2, int i3, int i4, boolean z) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        Rect defaultPadding = getDefaultPadding();
        float f = getResources().getDisplayMetrics().density;
        int i5 = (int) ((defaultPadding.left + defaultPadding.right) / f);
        int i6 = (int) ((defaultPadding.top + defaultPadding.bottom) / f);
        int i7 = i - (z ? 0 : i5);
        int i8 = i2 - (z ? 0 : i6);
        if (z) {
            i5 = 0;
        }
        int i9 = i3 - i5;
        if (z) {
            i6 = 0;
        }
        int i10 = i4 - i6;
        Bundle appWidgetOptions = AppWidgetManager.getInstance(this.mContext).getAppWidgetOptions(this.mAppWidgetId);
        if (i7 == appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH) && i8 == appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT) && i9 == appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH) && i10 == appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT)) {
            return;
        }
        bundle.putInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, i7);
        bundle.putInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, i8);
        bundle.putInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH, i9);
        bundle.putInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, i10);
        bundle.putParcelableArrayList(AppWidgetManager.OPTION_APPWIDGET_SIZES, new ArrayList<>());
        updateAppWidgetOptions(bundle);
    }

    public void updateAppWidgetOptions(Bundle bundle) {
        AppWidgetManager.getInstance(this.mContext).updateAppWidgetOptions(this.mAppWidgetId, bundle);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        Context context = this.mRemoteContext;
        if (context == null) {
            context = this.mContext;
        }
        return new FrameLayout.LayoutParams(context, attributeSet);
    }

    public void setExecutor(Executor executor) {
        CancellationSignal cancellationSignal = this.mLastExecutionSignal;
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
            this.mLastExecutionSignal = null;
        }
        this.mAsyncExecutor = executor;
    }

    public void setOnLightBackground(boolean z) {
        this.mOnLightBackground = z;
    }

    @Override // android.appwidget.AppWidgetHost.AppWidgetHostListener
    public void onUpdateProviderInfo(AppWidgetProviderInfo appWidgetProviderInfo) {
        setAppWidget(this.mAppWidgetId, appWidgetProviderInfo);
        this.mViewMode = 0;
        updateAppWidget(null);
    }

    @Override // android.appwidget.AppWidgetHost.AppWidgetHostListener
    public void updateAppWidget(RemoteViews remoteViews) {
        this.mLastInflatedRemoteViews = remoteViews;
        applyRemoteViews(remoteViews, true);
        AppWidgetProviderInfo appWidgetProviderInfo = this.mInfo;
        if (appWidgetProviderInfo != null && appWidgetProviderInfo.getProfile() != null && SemDualAppManager.isDualAppId(this.mInfo.getProfile().getIdentifier())) {
            SemDualAppManager.drawDualAppBadge(this.mContext, this, this.mInfo.getProfile());
        }
        AppWidgetProviderInfo appWidgetProviderInfo2 = this.mInfo;
        if (appWidgetProviderInfo2 == null || appWidgetProviderInfo2.getProfile() == null || !SemPersonaManager.isKnoxId(this.mInfo.getProfile().getIdentifier())) {
            return;
        }
        SemPersonaManager.drawKnoxAppBadge(this.mContext, this, this.mInfo.getProfile());
    }

    private void reapplyLastRemoteViews() {
        SparseArray<Parcelable> sparseArray = new SparseArray<>();
        saveHierarchyState(sparseArray);
        applyRemoteViews(this.mLastInflatedRemoteViews, true);
        restoreHierarchyState(sparseArray);
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    protected void applyRemoteViews(android.widget.RemoteViews r13, boolean r14) {
        /*
            r12 = this;
            r0 = -1
            r12.mLastInflatedRemoteViewsId = r0
            android.os.CancellationSignal r0 = r12.mLastExecutionSignal
            r1 = 0
            if (r0 == 0) goto Le
            r0.cancel()
            r12.mLastExecutionSignal = r1
        Le:
            r2 = 0
            if (r13 != 0) goto L20
            int r13 = r12.mViewMode
            r14 = 3
            if (r13 != r14) goto L17
            return
        L17:
            android.view.View r13 = r12.getDefaultView()
            r12.mViewMode = r14
            r5 = r12
            goto La9
        L20:
            boolean r0 = r12.mIsForcedOrientation
            if (r0 == 0) goto L29
            boolean r0 = r12.mIsPortrait
            r13.setOrientation(r0)
        L29:
            android.content.Context r0 = r12.mContext
            android.util.SizeF r3 = r12.mCurrentSize
            android.widget.RemoteViews r0 = r13.getRemoteViewsToApply(r0, r3)
            boolean r3 = r12.mOnLightBackground
            if (r3 == 0) goto L39
            android.widget.RemoteViews r0 = r0.getDarkTextViews()
        L39:
            r3 = r0
            java.util.concurrent.Executor r0 = r12.mAsyncExecutor
            if (r0 == 0) goto L44
            if (r14 == 0) goto L44
            r12.inflateAsync(r3)
            return
        L44:
            android.content.Context r14 = r12.getRemoteContextEnsuringCorrectCachedApkPath()
            r12.mRemoteContext = r14
            boolean r14 = r12.mColorMappingChanged
            r9 = 1
            if (r14 != 0) goto L81
            android.view.View r14 = r12.mView
            boolean r14 = r3.canRecycleView(r14)
            if (r14 == 0) goto L81
            boolean r14 = r12.mConfigChanged
            if (r14 != 0) goto L81
            android.content.Context r4 = r12.mContext     // Catch: java.lang.RuntimeException -> L78
            android.view.View r5 = r12.mView     // Catch: java.lang.RuntimeException -> L78
            android.appwidget.AppWidgetHostView$InteractionLogger r6 = r12.mInteractionLogger     // Catch: java.lang.RuntimeException -> L78
            android.util.SizeF r7 = r12.mCurrentSize     // Catch: java.lang.RuntimeException -> L78
            android.widget.RemoteViews$ColorResources r8 = r12.mColorResources     // Catch: java.lang.RuntimeException -> L78
            r3.reapply(r4, r5, r6, r7, r8)     // Catch: java.lang.RuntimeException -> L78
            android.view.View r14 = r12.mView     // Catch: java.lang.RuntimeException -> L78
            long r4 = r3.computeUniqueId(r13)     // Catch: java.lang.RuntimeException -> L75
            r12.mLastInflatedRemoteViewsId = r4     // Catch: java.lang.RuntimeException -> L75
            r10 = r1
            r1 = r14
            r14 = r10
            r10 = r9
            goto L83
        L75:
            r0 = move-exception
            r1 = r0
            goto L7d
        L78:
            r0 = move-exception
            r14 = r0
            r11 = r1
            r1 = r14
            r14 = r11
        L7d:
            r10 = r1
            r1 = r14
            r14 = r10
            goto L82
        L81:
            r14 = r1
        L82:
            r10 = r2
        L83:
            if (r1 != 0) goto La1
            android.content.Context r4 = r12.mContext     // Catch: java.lang.RuntimeException -> L9b
            android.appwidget.AppWidgetHostView$InteractionLogger r6 = r12.mInteractionLogger     // Catch: java.lang.RuntimeException -> L9b
            android.util.SizeF r7 = r12.mCurrentSize     // Catch: java.lang.RuntimeException -> L9b
            android.widget.RemoteViews$ColorResources r8 = r12.mColorResources     // Catch: java.lang.RuntimeException -> L9b
            r5 = r12
            android.view.View r1 = r3.apply(r4, r5, r6, r7, r8)     // Catch: java.lang.RuntimeException -> L99
            long r12 = r3.computeUniqueId(r13)     // Catch: java.lang.RuntimeException -> L99
            r5.mLastInflatedRemoteViewsId = r12     // Catch: java.lang.RuntimeException -> L99
            goto La2
        L99:
            r0 = move-exception
            goto L9d
        L9b:
            r0 = move-exception
            r5 = r12
        L9d:
            r12 = r0
            r13 = r1
            r1 = r12
            goto La4
        La1:
            r5 = r12
        La2:
            r13 = r1
            r1 = r14
        La4:
            r5.mConfigChanged = r2
            r5.mViewMode = r9
            r2 = r10
        La9:
            r5.applyContent(r13, r2, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.appwidget.AppWidgetHostView.applyRemoteViews(android.widget.RemoteViews, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyContent(View view, boolean z, Exception exc) {
        this.mColorMappingChanged = false;
        if (view == null) {
            if (this.mViewMode == 2) {
                return;
            }
            if (exc != null) {
                Log.w(TAG, "Error inflating RemoteViews", exc);
            }
            view = getErrorView();
            this.mViewMode = 2;
        }
        if (!z) {
            try {
                prepareView(view);
                addView(view);
            } catch (Exception e) {
                removeViewInLayout(view);
                View errorView = getErrorView();
                prepareView(errorView);
                addViewInLayout(errorView, 0, errorView.getLayoutParams());
                measureChild(errorView, View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                errorView.layout(0, 0, errorView.getMeasuredWidth() + this.mPaddingLeft + this.mPaddingRight, errorView.getMeasuredHeight() + this.mPaddingTop + this.mPaddingBottom);
                this.mView = errorView;
                this.mViewMode = 2;
                Log.e(TAG, "Error on prepare/add Views", e);
                return;
            }
        }
        View view2 = this.mView;
        if (view2 != view) {
            removeView(view2);
            this.mView = view;
        }
        View.OnClickListener onClickListener = this.mOnContentAppliedListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    private void hidden_semSetOnContentAppliedListener(View.OnClickListener onClickListener) {
        this.mOnContentAppliedListener = onClickListener;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void inflateAsync(android.widget.RemoteViews r14) {
        /*
            r13 = this;
            android.content.Context r0 = r13.getRemoteContextEnsuringCorrectCachedApkPath()
            r13.mRemoteContext = r0
            int r0 = r14.getLayoutId()
            android.os.CancellationSignal r1 = r13.mLastExecutionSignal
            if (r1 == 0) goto L11
            r1.cancel()
        L11:
            android.appwidget.AppWidgetProviderInfo r1 = r13.mInfo
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L1d
            int r1 = r1.hidden_semAppWidgeAdditionOptions
            if (r1 != r2) goto L1d
            r1 = r2
            goto L1e
        L1d:
            r1 = r3
        L1e:
            if (r1 == 0) goto L28
            java.lang.String r4 = "AppWidgetHostView"
            java.lang.String r5 = "skip reapplyAsync requested"
            android.util.Log.i(r4, r5)
        L28:
            boolean r4 = r13.mColorMappingChanged
            if (r4 != 0) goto L56
            android.view.View r4 = r13.mView
            boolean r4 = r14.canRecycleView(r4)
            if (r4 == 0) goto L56
            boolean r4 = r13.mConfigChanged
            if (r4 != 0) goto L56
            if (r1 != 0) goto L56
            android.content.Context r6 = r13.mContext     // Catch: java.lang.Exception -> L56
            android.view.View r7 = r13.mView     // Catch: java.lang.Exception -> L56
            java.util.concurrent.Executor r8 = r13.mAsyncExecutor     // Catch: java.lang.Exception -> L56
            android.appwidget.AppWidgetHostView$ViewApplyListener r9 = new android.appwidget.AppWidgetHostView$ViewApplyListener     // Catch: java.lang.Exception -> L56
            r9.<init>(r14, r0, r2)     // Catch: java.lang.Exception -> L56
            android.appwidget.AppWidgetHostView$InteractionLogger r10 = r13.mInteractionLogger     // Catch: java.lang.Exception -> L56
            android.util.SizeF r11 = r13.mCurrentSize     // Catch: java.lang.Exception -> L56
            android.widget.RemoteViews$ColorResources r12 = r13.mColorResources     // Catch: java.lang.Exception -> L56
            r5 = r14
            android.os.CancellationSignal r14 = r5.reapplyAsync(r6, r7, r8, r9, r10, r11, r12)     // Catch: java.lang.Exception -> L54
            r4 = r5
            r13.mLastExecutionSignal = r14     // Catch: java.lang.Exception -> L57
            goto L57
        L54:
            r4 = r5
            goto L57
        L56:
            r4 = r14
        L57:
            android.os.CancellationSignal r14 = r13.mLastExecutionSignal
            if (r14 != 0) goto L72
            android.content.Context r5 = r13.mContext
            java.util.concurrent.Executor r7 = r13.mAsyncExecutor
            android.appwidget.AppWidgetHostView$ViewApplyListener r8 = new android.appwidget.AppWidgetHostView$ViewApplyListener
            r8.<init>(r4, r0, r3)
            android.appwidget.AppWidgetHostView$InteractionLogger r9 = r13.mInteractionLogger
            android.util.SizeF r10 = r13.mCurrentSize
            android.widget.RemoteViews$ColorResources r11 = r13.mColorResources
            r6 = r13
            android.os.CancellationSignal r13 = r4.applyAsync(r5, r6, r7, r8, r9, r10, r11)
            r6.mLastExecutionSignal = r13
            goto L73
        L72:
            r6 = r13
        L73:
            r6.mConfigChanged = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.appwidget.AppWidgetHostView.inflateAsync(android.widget.RemoteViews):void");
    }

    private class ViewApplyListener implements RemoteViews.OnViewAppliedListener {
        private final boolean mIsReapply;
        private final int mLayoutId;
        private final RemoteViews mViews;

        ViewApplyListener(RemoteViews remoteViews, int i, boolean z) {
            this.mViews = remoteViews;
            this.mLayoutId = i;
            this.mIsReapply = z;
        }

        @Override // android.widget.RemoteViews.OnViewAppliedListener
        public void onViewApplied(View view) {
            AppWidgetHostView.this.mViewMode = 1;
            AppWidgetHostView.this.applyContent(view, this.mIsReapply, null);
            int i = AppWidgetHostView.this.mLastViewIdToDataChanged;
            if (i > -1) {
                AppWidgetHostView.this.mLastViewIdToDataChanged = -1;
                Log.i(AppWidgetHostView.TAG, "onViewApplied, Trigger viewDataChanged for viewId : " + i);
                AppWidgetHostView.this.onViewDataChanged(i);
            }
            AppWidgetHostView appWidgetHostView = AppWidgetHostView.this;
            appWidgetHostView.mLastInflatedRemoteViewsId = this.mViews.computeUniqueId(appWidgetHostView.mLastInflatedRemoteViews);
            AppWidgetHostView.this.restoreInstanceState();
            AppWidgetHostView.this.mLastExecutionSignal = null;
        }

        @Override // android.widget.RemoteViews.OnViewAppliedListener
        public void onError(Exception exc) {
            if (this.mIsReapply) {
                AppWidgetHostView appWidgetHostView = AppWidgetHostView.this;
                RemoteViews remoteViews = this.mViews;
                Context context = appWidgetHostView.mContext;
                AppWidgetHostView appWidgetHostView2 = AppWidgetHostView.this;
                appWidgetHostView.mLastExecutionSignal = remoteViews.applyAsync(context, appWidgetHostView2, appWidgetHostView2.mAsyncExecutor, AppWidgetHostView.this.new ViewApplyListener(this.mViews, this.mLayoutId, false), AppWidgetHostView.this.mInteractionLogger, AppWidgetHostView.this.mCurrentSize);
                return;
            }
            AppWidgetHostView.this.applyContent(null, false, exc);
            AppWidgetHostView.this.mLastExecutionSignal = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.appwidget.AppWidgetHost.AppWidgetHostListener
    public void onViewDataChanged(int i) {
        View findViewById = findViewById(i);
        Log.i(TAG, "viewDataChanged, viewId = " + i + ", v = " + findViewById);
        if (findViewById != null && (findViewById instanceof AdapterView)) {
            AdapterView adapterView = (AdapterView) findViewById;
            Adapter adapter = adapterView.getAdapter();
            if (adapter instanceof BaseAdapter) {
                ((BaseAdapter) adapter).notifyDataSetChanged();
            } else if (adapter == null && (adapterView instanceof RemoteViewsAdapter.RemoteAdapterConnectionCallback)) {
                ((RemoteViewsAdapter.RemoteAdapterConnectionCallback) adapterView).deferNotifyDataSetChanged();
            }
            this.mLastViewIdToDataChanged = -1;
            return;
        }
        this.mLastViewIdToDataChanged = i;
        Log.i(TAG, "view is null, will retry when view inflating is finished.");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0072 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected android.content.Context getRemoteContextEnsuringCorrectCachedApkPath() {
        /*
            r7 = this;
            java.lang.String r0 = " not found"
            java.lang.String r1 = "Package name "
            java.lang.String r2 = "AppWidgetHostView"
            r3 = 4
            android.content.Context r4 = r7.mContext     // Catch: android.content.res.Resources.NotFoundException -> L1b java.lang.NullPointerException -> L3e android.content.pm.PackageManager.NameNotFoundException -> L47
            android.appwidget.AppWidgetProviderInfo r5 = r7.mInfo     // Catch: android.content.res.Resources.NotFoundException -> L1b java.lang.NullPointerException -> L3e android.content.pm.PackageManager.NameNotFoundException -> L47
            android.content.pm.ActivityInfo r5 = r5.providerInfo     // Catch: android.content.res.Resources.NotFoundException -> L1b java.lang.NullPointerException -> L3e android.content.pm.PackageManager.NameNotFoundException -> L47
            android.content.pm.ApplicationInfo r5 = r5.applicationInfo     // Catch: android.content.res.Resources.NotFoundException -> L1b java.lang.NullPointerException -> L3e android.content.pm.PackageManager.NameNotFoundException -> L47
            android.content.Context r4 = r4.createApplicationContext(r5, r3)     // Catch: android.content.res.Resources.NotFoundException -> L1b java.lang.NullPointerException -> L3e android.content.pm.PackageManager.NameNotFoundException -> L47
            android.widget.RemoteViews$ColorResources r5 = r7.mColorResources     // Catch: android.content.res.Resources.NotFoundException -> L1b java.lang.NullPointerException -> L3e android.content.pm.PackageManager.NameNotFoundException -> L47
            if (r5 == 0) goto L1a
            r5.apply(r4)     // Catch: android.content.res.Resources.NotFoundException -> L1b java.lang.NullPointerException -> L3e android.content.pm.PackageManager.NameNotFoundException -> L47
        L1a:
            return r4
        L1b:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Failed to get RemoteContext."
            r5.<init>(r6)
            android.appwidget.AppWidgetProviderInfo r6 = r7.mInfo
            android.content.pm.ActivityInfo r6 = r6.providerInfo
            android.content.pm.ApplicationInfo r6 = r6.applicationInfo
            java.lang.String r6 = r6.sourceDir
            r5.append(r6)
            java.lang.String r6 = ", "
            r5.append(r6)
            r5.append(r4)
            java.lang.String r5 = r5.toString()
            android.util.Log.w(r2, r5, r4)
            goto L5f
        L3e:
            r0 = move-exception
            java.lang.String r1 = "Error trying to create the remote context."
            android.util.Log.e(r2, r1, r0)
            android.content.Context r7 = r7.mContext
            return r7
        L47:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r1)
            android.appwidget.AppWidgetProviderInfo r5 = r7.mInfo
            android.content.pm.ActivityInfo r5 = r5.providerInfo
            java.lang.String r5 = r5.packageName
            r4.append(r5)
            r4.append(r0)
            java.lang.String r4 = r4.toString()
            android.util.Log.e(r2, r4)
        L5f:
            android.appwidget.AppWidgetProviderInfo r4 = r7.mInfo
            android.content.pm.ActivityInfo r4 = r4.providerInfo
            android.content.pm.ApplicationInfo r4 = r4.applicationInfo
            java.lang.String r4 = r4.packageName
            android.content.Context r5 = r7.mContext
            boolean r5 = r7.clearResourcePackageCache(r5, r4)
            if (r5 != 0) goto L72
            android.content.Context r7 = r7.mContext
            return r7
        L72:
            android.content.Context r5 = r7.mContext     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9f
            android.content.pm.PackageManager r5 = r5.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9f
            r6 = 0
            android.content.pm.ApplicationInfo r4 = r5.getApplicationInfo(r4, r6)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9f
            android.content.Context r5 = r7.mContext     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L84
            android.content.Context r7 = r5.createApplicationContext(r4, r3)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L84
            return r7
        L84:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r1)
            android.appwidget.AppWidgetProviderInfo r1 = r7.mInfo
            android.content.pm.ActivityInfo r1 = r1.providerInfo
            java.lang.String r1 = r1.packageName
            r3.append(r1)
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            android.util.Log.e(r2, r0)
            android.content.Context r7 = r7.mContext
            return r7
        L9f:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r1)
            android.appwidget.AppWidgetProviderInfo r1 = r7.mInfo
            android.content.pm.ActivityInfo r1 = r1.providerInfo
            java.lang.String r1 = r1.packageName
            r3.append(r1)
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            android.util.Log.e(r2, r0)
            android.content.Context r7 = r7.mContext
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.appwidget.AppWidgetHostView.getRemoteContextEnsuringCorrectCachedApkPath():android.content.Context");
    }

    private boolean clearResourcePackageCache(Context context, String str) {
        try {
            if (context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
            }
            Field declaredField = Class.forName("android.app.ContextImpl").getDeclaredField("mMainThread");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(context);
            Field declaredField2 = ActivityThread.class.getDeclaredField("mResourcesManager");
            declaredField2.setAccessible(true);
            Object obj2 = declaredField2.get(obj);
            Field declaredField3 = ActivityThread.class.getDeclaredField("mResourcePackages");
            declaredField3.setAccessible(true);
            Object obj3 = declaredField3.get(obj);
            synchronized (obj2) {
                ((ArrayMap) obj3).remove(str);
            }
            return true;
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NullPointerException | SecurityException e) {
            Log.e(TAG, "Failed to clear cache for " + str + ", " + e, e);
            return false;
        }
    }

    protected void prepareView(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new FrameLayout.LayoutParams(-1, -1);
        }
        layoutParams.gravity = 17;
        view.setLayoutParams(layoutParams);
    }

    protected View getDefaultView() {
        View view;
        int i;
        RuntimeException e = null;
        try {
            if (this.mInfo != null) {
                Context remoteContextEnsuringCorrectCachedApkPath = getRemoteContextEnsuringCorrectCachedApkPath();
                this.mRemoteContext = remoteContextEnsuringCorrectCachedApkPath;
                LayoutInflater cloneInContext = ((LayoutInflater) remoteContextEnsuringCorrectCachedApkPath.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).cloneInContext(remoteContextEnsuringCorrectCachedApkPath);
                cloneInContext.setFilter(INFLATER_FILTER);
                Bundle appWidgetOptions = AppWidgetManager.getInstance(this.mContext).getAppWidgetOptions(this.mAppWidgetId);
                int i2 = this.mInfo.initialLayout;
                if (appWidgetOptions.containsKey(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY) && appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY) == 2 && (i = this.mInfo.initialKeyguardLayout) != 0) {
                    i2 = i;
                }
                view = cloneInContext.inflate(i2, (ViewGroup) this, false);
                try {
                    if (!(view instanceof AdapterView)) {
                        view.setOnClickListener(new View.OnClickListener() { // from class: android.appwidget.AppWidgetHostView$$ExternalSyntheticLambda2
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                AppWidgetHostView.this.onDefaultViewClicked(view2);
                            }
                        });
                    }
                } catch (RuntimeException e2) {
                    e = e2;
                }
            } else {
                Log.w(TAG, "can't inflate defaultView because mInfo is missing");
                view = null;
            }
        } catch (RuntimeException e3) {
            e = e3;
            view = null;
        }
        if (e != null) {
            Log.w(TAG, "Error inflating AppWidget " + this.mInfo, e);
        }
        return view == null ? getErrorView() : view;
    }

    protected void onDefaultViewClicked(View view) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.mContext);
        if (appWidgetManager != null) {
            appWidgetManager.noteAppWidgetTapped(this.mAppWidgetId);
        }
        if (this.mInfo != null) {
            LauncherApps launcherApps = (LauncherApps) getContext().getSystemService(LauncherApps.class);
            List<LauncherActivityInfo> activityList = launcherApps.getActivityList(this.mInfo.provider.getPackageName(), this.mInfo.getProfile());
            if (activityList.isEmpty()) {
                return;
            }
            LauncherActivityInfo launcherActivityInfo = activityList.get(0);
            launcherApps.startMainActivity(launcherActivityInfo.getComponentName(), launcherActivityInfo.getUser(), RemoteViews.getSourceBounds(view), null);
        }
    }

    protected View getErrorView() {
        TextView textView = new TextView(this.mContext);
        textView.setText(R.string.gadget_host_error_inflating);
        textView.setBackgroundColor(Color.argb(127, 0, 0, 0));
        return textView;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(AppWidgetHostView.class.getName());
    }

    public ActivityOptions createSharedElementActivityOptions(int[] iArr, String[] strArr, Intent intent) {
        Context context = getContext();
        while ((context instanceof ContextWrapper) && !(context instanceof Activity)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (!(context instanceof Activity)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Bundle bundle = new Bundle();
        for (int i = 0; i < iArr.length; i++) {
            View findViewById = findViewById(iArr[i]);
            if (findViewById != null) {
                arrayList.add(Pair.create(findViewById, strArr[i]));
                bundle.putParcelable(strArr[i], RemoteViews.getSourceBounds(findViewById));
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        intent.putExtra(RemoteViews.EXTRA_SHARED_ELEMENT_BOUNDS, bundle);
        ActivityOptions makeSceneTransitionAnimation = ActivityOptions.makeSceneTransitionAnimation((Activity) context, (Pair[]) arrayList.toArray(new Pair[arrayList.size()]));
        makeSceneTransitionAnimation.setPendingIntentLaunchFlags(268435456);
        return makeSceneTransitionAnimation;
    }

    public void setColorResources(SparseIntArray sparseIntArray) {
        RemoteViews.ColorResources colorResources = this.mColorResources;
        if (colorResources == null || !isSameColorMapping(colorResources.getColorMapping(), sparseIntArray)) {
            setColorResources(RemoteViews.ColorResources.create(this.mContext, sparseIntArray));
        }
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

    private boolean isSameColorMapping(SparseIntArray sparseIntArray, SparseIntArray sparseIntArray2) {
        if (sparseIntArray.size() != sparseIntArray2.size()) {
            return false;
        }
        for (int i = 0; i < sparseIntArray.size(); i++) {
            if (sparseIntArray.keyAt(i) != sparseIntArray2.keyAt(i) || sparseIntArray.valueAt(i) != sparseIntArray2.valueAt(i)) {
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
            this.mInteractionLogger.onDraw();
        } catch (Exception e) {
            Log.e(TAG, "Drawing view failed: " + e);
            post(new Runnable() { // from class: android.appwidget.AppWidgetHostView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AppWidgetHostView.this.handleViewError();
                }
            });
        }
    }

    public class InteractionLogger implements RemoteViews.InteractionHandler {
        public static final int MAX_NUM_ITEMS = 10;
        private static final long UPDATE_VISIBILITY_DELAY_MS = 1000;
        private final Set<Integer> mClickedIds;
        private long mDurationMs;
        private RemoteViews.InteractionHandler mInteractionHandler;
        private boolean mIsVisible;
        private Rect mPosition;
        private final Set<Integer> mScrolledIds;
        private boolean mUpdateVisibilityScheduled;
        private long mVisibilityChangeMs;

        InteractionLogger() {
            this.mClickedIds = new ArraySet(10);
            this.mScrolledIds = new ArraySet(10);
            this.mInteractionHandler = null;
            this.mPosition = null;
            this.mDurationMs = 0L;
            this.mVisibilityChangeMs = 0L;
            this.mIsVisible = false;
            this.mUpdateVisibilityScheduled = false;
        }

        InteractionLogger(RemoteViews.InteractionHandler interactionHandler) {
            this.mClickedIds = new ArraySet(10);
            this.mScrolledIds = new ArraySet(10);
            this.mPosition = null;
            this.mDurationMs = 0L;
            this.mVisibilityChangeMs = 0L;
            this.mIsVisible = false;
            this.mUpdateVisibilityScheduled = false;
            this.mInteractionHandler = interactionHandler;
        }

        public Set<Integer> getClickedIds() {
            return this.mClickedIds;
        }

        public Set<Integer> getScrolledIds() {
            return this.mScrolledIds;
        }

        public long getDurationMs() {
            return this.mDurationMs;
        }

        public Rect getPosition() {
            return this.mPosition;
        }

        @Override // android.widget.RemoteViews.InteractionHandler
        public boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
            if (Flags.engagementMetrics() && this.mClickedIds.size() < 10) {
                this.mClickedIds.add(Integer.valueOf(getMetricsId(view)));
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(AppWidgetHostView.this.mContext);
            if (appWidgetManager != null) {
                appWidgetManager.noteAppWidgetTapped(AppWidgetHostView.this.mAppWidgetId);
            }
            RemoteViews.InteractionHandler interactionHandler = this.mInteractionHandler;
            if (interactionHandler != null) {
                return interactionHandler.onInteraction(view, pendingIntent, remoteResponse);
            }
            return RemoteViews.startPendingIntent(view, pendingIntent, remoteResponse.getLaunchOptions(view));
        }

        @Override // android.widget.RemoteViews.InteractionHandler
        public void onScroll(AbsListView absListView) {
            if (Flags.engagementMetrics()) {
                if (this.mScrolledIds.size() < 10) {
                    this.mScrolledIds.add(Integer.valueOf(getMetricsId(absListView)));
                }
                RemoteViews.InteractionHandler interactionHandler = this.mInteractionHandler;
                if (interactionHandler != null) {
                    interactionHandler.onScroll(absListView);
                }
            }
        }

        private int getMetricsId(View view) {
            Object tag = view.getTag(R.id.remoteViewsMetricsId);
            if (tag instanceof Integer) {
                return ((Integer) tag).intValue();
            }
            return view.getId();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onPositionChanged() {
            if (Flags.engagementMetrics()) {
                Rect rect = new Rect();
                this.mPosition = rect;
                if (AppWidgetHostView.this.getGlobalVisibleRect(rect)) {
                    applyScrollOffset();
                }
            }
        }

        private void applyScrollOffset() {
            int i;
            int i2;
            if (this.mPosition == null) {
                return;
            }
            for (ViewParent parent = AppWidgetHostView.this.getParent(); parent != null; parent = parent.getParent()) {
                if (parent instanceof View) {
                    View view = (View) parent;
                    if (view.getScrollX() != 0 || view.getScrollY() != 0) {
                        i = view.getScrollX();
                        i2 = view.getScrollY();
                        break;
                    }
                }
            }
            i = 0;
            i2 = 0;
            this.mPosition.offset(i, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onDraw() {
            if (Flags.engagementMetrics()) {
                Object parent = AppWidgetHostView.this.getParent();
                if ((parent instanceof View) && ((View) parent).isDirty()) {
                    scheduleUpdateVisibility();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onWindowFocusChanged(boolean z) {
            if (Flags.engagementMetrics()) {
                updateVisibility(z);
            }
        }

        private void scheduleUpdateVisibility() {
            if (this.mUpdateVisibilityScheduled) {
                return;
            }
            AppWidgetHostView.this.postDelayed(new Runnable() { // from class: android.appwidget.AppWidgetHostView$InteractionLogger$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AppWidgetHostView.InteractionLogger.this.lambda$scheduleUpdateVisibility$0();
                }
            }, 1000L);
            this.mUpdateVisibilityScheduled = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$scheduleUpdateVisibility$0() {
            updateVisibility(AppWidgetHostView.this.hasWindowFocus());
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x002e, code lost:
        
            if (r9 == false) goto L18;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0030, code lost:
        
            r8.mVisibilityChangeMs = android.os.SystemClock.uptimeMillis();
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v5, types: [android.view.ViewParent] */
        /* JADX WARN: Type inference failed for: r2v7 */
        /* JADX WARN: Type inference failed for: r2v8 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void updateVisibility(boolean r9) {
            /*
                r8 = this;
                boolean r0 = r8.mIsVisible
                r1 = 0
                if (r9 == 0) goto Lf
                android.appwidget.AppWidgetHostView r9 = android.appwidget.AppWidgetHostView.this
                boolean r9 = r8.testVisibility(r9)
                if (r9 == 0) goto Lf
                r9 = 1
                goto L10
            Lf:
                r9 = r1
            L10:
                if (r9 == 0) goto L2c
                android.appwidget.AppWidgetHostView r2 = android.appwidget.AppWidgetHostView.this
                android.view.ViewParent r2 = r2.getParent()
            L18:
                if (r2 == 0) goto L2c
                if (r9 == 0) goto L2c
                boolean r3 = r2 instanceof android.view.View
                if (r3 == 0) goto L2c
                r9 = r2
                android.view.View r9 = (android.view.View) r9
                boolean r9 = r8.testVisibility(r9)
                android.view.ViewParent r2 = r2.getParent()
                goto L18
            L2c:
                if (r0 != 0) goto L37
                if (r9 == 0) goto L37
                long r2 = android.os.SystemClock.uptimeMillis()
                r8.mVisibilityChangeMs = r2
                goto L47
            L37:
                if (r0 == 0) goto L47
                if (r9 != 0) goto L47
                long r2 = r8.mDurationMs
                long r4 = android.os.SystemClock.uptimeMillis()
                long r6 = r8.mVisibilityChangeMs
                long r4 = r4 - r6
                long r2 = r2 + r4
                r8.mDurationMs = r2
            L47:
                r8.mIsVisible = r9
                r8.mUpdateVisibilityScheduled = r1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.appwidget.AppWidgetHostView.InteractionLogger.updateVisibility(boolean):void");
        }

        private boolean testVisibility(View view) {
            return view.isAggregatedVisible() && view.getGlobalVisibleRect(new Rect()) && view.getAlpha() != 0.0f;
        }
    }

    @Override // android.view.ViewGroup
    public boolean semDispatchTooltipHoverEvent(MotionEvent motionEvent) {
        if (motionEvent.getToolType(0) == 2) {
            return true;
        }
        return super.semDispatchTooltipHoverEvent(motionEvent);
    }

    public void semForceOrientation(boolean z, boolean z2) {
        Log.d(TAG, "force orientation - forced=" + z + ", isPortrait=" + z2);
        this.mIsForcedOrientation = z;
        if (z) {
            this.mIsPortrait = z2;
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Configuration configuration2 = this.mLastConfig;
        if (configuration2 == null) {
            this.mLastConfig = configuration;
            this.mConfigChanged = true;
        } else {
            if (configuration2.equals(configuration)) {
                return;
            }
            this.mLastConfig = configuration;
            this.mConfigChanged = true;
        }
    }

    private void hidden_semSetOptions(Bundle bundle) {
        this.mHostType = bundle.getInt(APPWIDGET_HOST_OPTIONS_HOST_TYPE, 0);
    }

    public int getHostType() {
        return this.mHostType;
    }
}
