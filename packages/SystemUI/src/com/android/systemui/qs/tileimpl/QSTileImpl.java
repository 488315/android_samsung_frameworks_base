package com.android.systemui.qs.tileimpl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.SecurityUtils$$ExternalSyntheticOutline0;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.graph.SignalDrawable;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.indexsearch.CircleFramedTileIcon;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.LockQSTile;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qs.QSEvent;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.SecQQSTileHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.SideLabelTileLayout;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.statusbar.ScalingDrawableWrapper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.leak.LeakReporter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class QSTileImpl implements QSTile, LifecycleOwner, Dumpable, LockQSTile {
    public final ActivityStarter mActivityStarter;
    public final Context mContext;
    protected RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    public final FalsingManager mFalsingManager;
    public final H mHandler;
    public final QSHost mHost;
    public final InstanceId mInstanceId;
    public int mIsFullQs;
    public final MetricsLogger mMetricsLogger;
    public Drawable mOldIconDrawable;
    public final QSLogger mQSLogger;
    public volatile int mReadyState;
    public QSTile.State mState;
    public final StatusBarStateController mStatusBarStateController;
    public final Context mSubscreenContext;
    public String mTileSpec;
    public final QSTileHost.TilesMap mTilesMap;
    public QSTile.State mTmpState;
    public final QsEventLogger mUiEventLogger;
    public final Handler mUiHandler;
    public final String TAG = "Tile.".concat(getClass().getSimpleName());
    public final boolean DEBUG = Log.isLoggable("Tile", 3);
    public final ArraySet mListeners = new ArraySet();
    public int mClickEventId = 0;
    public final ArraySet mCallbacks = new ArraySet();
    public final Object mStaleListener = new Object();
    public final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AnimationIcon extends ResourceIcon {
        public final int mAnimatedResId;

        public AnimationIcon(int i, int i2) {
            super(i2, 0);
            this.mAnimatedResId = i;
        }

        @Override // com.android.systemui.qs.tileimpl.QSTileImpl.ResourceIcon, com.android.systemui.plugins.qs.QSTile.Icon
        public final Drawable getDrawable(Context context) {
            return context.getDrawable(this.mAnimatedResId).getConstantState().newDrawable();
        }

        @Override // com.android.systemui.qs.tileimpl.QSTileImpl.ResourceIcon, com.android.systemui.plugins.qs.QSTile.Icon
        public final String toString() {
            return String.format("AnimationIcon[resId=0x%08x]", Integer.valueOf(this.mResId));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DrawableIconWithRes extends DrawableIcon {
        public final int mId;

        public DrawableIconWithRes(Drawable drawable, int i) {
            super(drawable);
            this.mId = i;
        }

        public final boolean equals(Object obj) {
            return (obj instanceof DrawableIconWithRes) && ((DrawableIconWithRes) obj).mId == this.mId;
        }

        @Override // com.android.systemui.qs.tileimpl.QSTileImpl.DrawableIcon, com.android.systemui.plugins.qs.QSTile.Icon
        public final String toString() {
            return String.format("DrawableIconWithRes[resId=0x%08x]", Integer.valueOf(this.mId));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class H extends Handler {
        protected static final int STALE = 11;

        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            QSTileImpl qSTileImpl = QSTileImpl.this;
            try {
                int i = message.what;
                boolean z = true;
                if (i == 1) {
                    QSTile.Callback callback = (QSTile.Callback) message.obj;
                    qSTileImpl.mCallbacks.add(callback);
                    callback.onStateChanged(qSTileImpl.mState);
                    return;
                }
                if (i == 8) {
                    qSTileImpl.mCallbacks.clear();
                    return;
                }
                if (i == 9) {
                    qSTileImpl.mCallbacks.remove((QSTile.Callback) message.obj);
                    return;
                }
                if (i == 2) {
                    if (qSTileImpl.mState.disabledByPolicy) {
                        qSTileImpl.mActivityStarter.postStartActivityDismissingKeyguard(RestrictedLockUtils.getShowAdminSupportDetailsIntent(qSTileImpl.mEnforcedAdmin), 0);
                        return;
                    } else if (qSTileImpl.mHost.shouldUnavailableByKnox(qSTileImpl.mTileSpec)) {
                        String str = qSTileImpl.TAG;
                        String str2 = qSTileImpl.mTileSpec;
                        return;
                    } else {
                        qSTileImpl.mQSLogger.logHandleClick(message.arg1, qSTileImpl.mTileSpec);
                        qSTileImpl.handleClick((Expandable) message.obj);
                        return;
                    }
                }
                if (i == 3) {
                    if (qSTileImpl.mHost.shouldUnavailableByKnox(qSTileImpl.mTileSpec)) {
                        String str3 = qSTileImpl.TAG;
                        String str4 = qSTileImpl.mTileSpec;
                        return;
                    } else {
                        qSTileImpl.mQSLogger.logHandleSecondaryClick(message.arg1, qSTileImpl.mTileSpec);
                        qSTileImpl.handleSecondaryClick((Expandable) message.obj);
                        return;
                    }
                }
                if (i == 4) {
                    if (qSTileImpl.mHost.shouldUnavailableByKnox(qSTileImpl.mTileSpec)) {
                        String str5 = qSTileImpl.TAG;
                        String str6 = qSTileImpl.mTileSpec;
                        return;
                    } else {
                        qSTileImpl.mQSLogger.logHandleLongClick(message.arg1, qSTileImpl.mTileSpec);
                        qSTileImpl.handleLongClick((Expandable) message.obj);
                        return;
                    }
                }
                if (i == 5) {
                    qSTileImpl.handleRefreshState(message.obj);
                    return;
                }
                if (i == 6) {
                    qSTileImpl.handleUserSwitch(message.arg1);
                    return;
                }
                if (i == 7) {
                    qSTileImpl.handleDestroy();
                    return;
                }
                if (i == 10) {
                    Object obj = message.obj;
                    if (message.arg1 == 0) {
                        z = false;
                    }
                    QSTileImpl.m2076$$Nest$mhandleSetListeningInternal(qSTileImpl, obj, z);
                    return;
                }
                if (i == 11) {
                    qSTileImpl.handleStale();
                    return;
                }
                if (i == 12) {
                    qSTileImpl.handleInitialize();
                } else if (i == 102) {
                    QSTileImpl.m2075$$Nest$mhandleSaveTileIcon(qSTileImpl);
                } else {
                    throw new IllegalArgumentException("Unknown msg: " + message.what);
                }
            } catch (Throwable th) {
                Log.w(qSTileImpl.TAG, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Error in ", null), th);
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class ResourceIcon extends QSTile.Icon {
        public static final SparseArray ICONS = new SparseArray();
        public final int mResId;

        public /* synthetic */ ResourceIcon(int i, int i2) {
            this(i);
        }

        public static synchronized QSTile.Icon get(int i) {
            QSTile.Icon icon;
            synchronized (ResourceIcon.class) {
                SparseArray sparseArray = ICONS;
                icon = (QSTile.Icon) sparseArray.get(i);
                if (icon == null) {
                    icon = new ResourceIcon(i);
                    sparseArray.put(i, icon);
                }
            }
            return icon;
        }

        public final boolean equals(Object obj) {
            return (obj instanceof ResourceIcon) && ((ResourceIcon) obj).mResId == this.mResId;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public Drawable getDrawable(Context context) {
            return context.getDrawable(this.mResId);
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public final Drawable getInvisibleDrawable(Context context) {
            return context.getDrawable(this.mResId);
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public String toString() {
            return String.format("ResourceIcon[resId=0x%08x]", Integer.valueOf(this.mResId));
        }

        private ResourceIcon(int i) {
            this.mResId = i;
        }
    }

    /* renamed from: -$$Nest$mhandleSaveTileIcon, reason: not valid java name */
    public static void m2075$$Nest$mhandleSaveTileIcon(QSTileImpl qSTileImpl) {
        Drawable tileIconDrawable = qSTileImpl.getTileIconDrawable();
        int tileIconSize = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getTileIconSize(qSTileImpl.mContext);
        if (qSTileImpl.mTileSpec == null || tileIconDrawable == null) {
            return;
        }
        CircleFramedTileIcon.mContext = qSTileImpl.mContext;
        CircleFramedTileIcon circleFramedTileIcon = new CircleFramedTileIcon(tileIconDrawable, tileIconSize);
        Bitmap createBitmap = Bitmap.createBitmap(tileIconSize, tileIconSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        circleFramedTileIcon.setBounds(0, 0, tileIconSize, tileIconSize);
        circleFramedTileIcon.draw(canvas);
        String str = qSTileImpl.mContext.getFilesDir() + "/tiles/";
        StringBuilder m = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str);
        m.append(qSTileImpl.getTileIconFileName(qSTileImpl.mTileSpec));
        String sb = m.toString();
        File file = new File(str);
        if (file.exists() || file.mkdir()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(sb);
                try {
                    createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } finally {
                }
            } catch (Exception e) {
                Log.d(qSTileImpl.TAG, "handleSaveTileIcon Exception : " + e);
            }
        }
    }

    /* renamed from: -$$Nest$mhandleSetListeningInternal, reason: not valid java name */
    public static void m2076$$Nest$mhandleSetListeningInternal(QSTileImpl qSTileImpl, Object obj, boolean z) {
        Handler handler = qSTileImpl.mUiHandler;
        String str = qSTileImpl.TAG;
        boolean z2 = qSTileImpl.DEBUG;
        if (z) {
            if (qSTileImpl.mListeners.add(obj) && qSTileImpl.mListeners.size() == 1) {
                if (z2) {
                    Log.d(str, "handleSetListening true");
                }
                qSTileImpl.handleSetListening(z);
                handler.post(new QSTileImpl$$ExternalSyntheticLambda0(qSTileImpl, 2));
            }
        } else if (qSTileImpl.mListeners.remove(obj) && qSTileImpl.mListeners.size() == 0) {
            if (z2) {
                Log.d(str, "handleSetListening false");
            }
            qSTileImpl.handleSetListening(z);
            handler.post(new QSTileImpl$$ExternalSyntheticLambda0(qSTileImpl, 3));
        }
        Iterator it = qSTileImpl.mListeners.iterator();
        while (it.hasNext()) {
            if (SideLabelTileLayout.class.equals(it.next().getClass())) {
                qSTileImpl.mIsFullQs = 1;
                return;
            }
        }
        qSTileImpl.mIsFullQs = 0;
    }

    public QSTileImpl(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger) {
        this.mHost = qSHost;
        Context context = qSHost.getContext();
        this.mContext = context;
        this.mInstanceId = ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId();
        this.mUiEventLogger = qsEventLogger;
        if (QSTileHost.TilesMap.sInstance == null) {
            QSTileHost.TilesMap.sInstance = new QSTileHost.TilesMap(context);
        }
        this.mTilesMap = QSTileHost.TilesMap.sInstance;
        this.mUiHandler = handler;
        this.mHandler = new H(looper);
        this.mFalsingManager = falsingManager;
        this.mQSLogger = qSLogger;
        this.mMetricsLogger = metricsLogger;
        this.mStatusBarStateController = statusBarStateController;
        this.mActivityStarter = activityStarter;
        this.mState = newTileState();
        QSTile.State newTileState = newTileState();
        this.mTmpState = newTileState;
        QSTile.State state = this.mState;
        String str = this.mTileSpec;
        state.spec = str;
        newTileState.spec = str;
        handler.post(new QSTileImpl$$ExternalSyntheticLambda0(this, 0));
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            this.mSubscreenContext = ((SubscreenQsPanelController) Dependency.sDependency.getDependencyInner(SubscreenQsPanelController.class)).mContext;
        }
    }

    public static Bitmap getTileIconBitmap(Drawable drawable, Context context) {
        if (drawable != null) {
            try {
                Drawable newDrawable = drawable instanceof ScalingDrawableWrapper ? ((ScalingDrawableWrapper) drawable).mCloneDrawable : drawable.getConstantState().newDrawable();
                int tileIconSize = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getTileIconSize(context);
                Bitmap createBitmap = Bitmap.createBitmap(tileIconSize, tileIconSize, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                newDrawable.setBounds(0, 0, tileIconSize, tileIconSize);
                newDrawable.draw(canvas);
                return createBitmap;
            } catch (Exception unused) {
            }
        }
        return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final void addCallback(QSTile.Callback callback) {
        this.mHandler.obtainMessage(1, callback).sendToTarget();
    }

    public final void checkIfRestrictionEnforcedByAdminOnly(QSTile.State state, String str) {
        Context context = this.mContext;
        QSHost qSHost = this.mHost;
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(context, str, qSHost.getUserId());
        if (checkIfRestrictionEnforced == null || RestrictedLockUtilsInternal.hasBaseUserRestriction(this.mContext, str, qSHost.getUserId())) {
            state.disabledByPolicy = false;
            this.mEnforcedAdmin = null;
        } else {
            state.disabledByPolicy = true;
            this.mEnforcedAdmin = checkIfRestrictionEnforced;
        }
    }

    @Override // com.android.systemui.plugins.qs.LockQSTile
    public final void click() {
        Log.d(this.TAG, "click from LockQSTile");
        this.mHandler.obtainMessage(2, -100, 0, null).sendToTarget();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void destroy() {
        this.mHandler.sendEmptyMessage(7);
    }

    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(getClass().getSimpleName().concat(":"));
        printWriter.print("    ");
        printWriter.println(getState().toString());
    }

    public final Uri getIconUri() {
        Uri uriForFile = FileProvider.getUriForFile(this.mContext, LeakReporter.FILEPROVIDER_AUTHORITY, new File(new File(this.mContext.getFilesDir(), "tiles"), getTileIconFileName(this.mTileSpec)));
        this.mContext.grantUriPermission("com.sec.android.app.launcher", uriForFile, 1);
        return uriForFile;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final InstanceId getInstanceId() {
        return this.mInstanceId;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.mLifecycle;
    }

    public abstract Intent getLongClickIntent();

    @Override // com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public String getMetricsSpec() {
        return this.mTileSpec;
    }

    @Override // com.android.systemui.plugins.qs.LockQSTile
    public final Drawable getNextTileIconDrawable() {
        try {
            QSTile.Icon icon = this.mState.nextIcon;
            if (icon == null) {
                return null;
            }
            return icon.getInvisibleDrawable(this.mContext);
        } catch (Exception unused) {
            return null;
        }
    }

    public String getSearchTitle() {
        CharSequence tileLabel = getTileLabel();
        if (tileLabel == null) {
            return null;
        }
        return tileLabel.toString().replaceAll(System.getProperty("line.separator"), " ").trim();
    }

    public ArrayList getSearchWords() {
        String searchTitle = getSearchTitle();
        if (searchTitle == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        String lowerCase = searchTitle.trim().toLowerCase();
        String replaceAll = lowerCase.trim().toLowerCase().replaceAll("-", "");
        arrayList.add(lowerCase);
        if (!lowerCase.equals(replaceAll)) {
            arrayList.add(replaceAll);
        }
        return arrayList;
    }

    public long getStaleTimeout() {
        return 600000L;
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public QSTile.State getState() {
        return this.mState;
    }

    public final Context getSubScreenContext() {
        return (!QpRune.QUICK_SUBSCREEN_PANEL || ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) ? this.mContext : this.mSubscreenContext;
    }

    public Drawable getTileIconDrawable() {
        Supplier<QSTile.Icon> supplier;
        try {
            QSTile.State state = this.mState;
            if (state.icon == null && ((supplier = state.iconSupplier) == null || supplier.get() == null)) {
                return null;
            }
            QSTile.State state2 = this.mState;
            Supplier<QSTile.Icon> supplier2 = state2.iconSupplier;
            return supplier2 != null ? supplier2.get().getDrawable(this.mContext) : state2.icon.getInvisibleDrawable(this.mContext);
        } catch (Exception unused) {
            return null;
        }
    }

    public final String getTileIconFileName(String str) {
        String className;
        if (!this.mTileSpec.startsWith("custom(")) {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "_tile_icon.png");
        }
        String customTileNameFromSpec = this.mHost.getCustomTileNameFromSpec(str);
        if (customTileNameFromSpec != null) {
            return customTileNameFromSpec.concat("_tile_icon.png");
        }
        CustomTile customTile = (CustomTile) this;
        if (customTile.mTileClassName != null) {
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder(), customTile.mTileClassName, "_tile_icon.png");
        }
        ComponentName componentFromSpec = CustomTile.getComponentFromSpec(str);
        return (componentFromSpec == null || (className = componentFromSpec.getClassName()) == null) ? str.concat("_tile_icon.png") : className.concat("_tile_icon.png");
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public abstract CharSequence getTileLabel();

    public String getTileMapKey() {
        QSTile.State state = this.mState;
        return state.isCustomTile ? state.tileClassName : this.mTileSpec;
    }

    public int getTileMapValue() {
        return getState().state == 2 ? 1 : 0;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final String getTileSpec() {
        return this.mTileSpec;
    }

    public abstract void handleClick(Expandable expandable);

    public void handleDestroy() {
        this.mQSLogger.logTileDestroyed(this.mTileSpec, "Handle destroy");
        if (this.mListeners.size() != 0) {
            handleSetListening(false);
            this.mListeners.clear();
        }
        this.mCallbacks.clear();
        this.mHandler.removeCallbacksAndMessages(null);
        this.mUiHandler.post(new QSTileImpl$$ExternalSyntheticLambda0(this, 1));
    }

    public void handleLongClick(Expandable expandable) {
        Intent longClickIntent = getLongClickIntent();
        if (longClickIntent != null) {
            this.mActivityStarter.postStartActivityDismissingKeyguard(longClickIntent, 0, expandable != null ? expandable.activityTransitionController(32) : null);
        } else {
            Log.d(this.TAG, "handleLongClick(): longClickIntent is null");
            handleSecondaryClick(expandable);
        }
    }

    public final void handleRefreshState(Object obj) {
        handleUpdateState(this.mTmpState, obj);
        if (this.mHost.shouldUnavailableByKnox(this.mTileSpec)) {
            this.mTmpState.state = 0;
        }
        boolean copyTo = this.mTmpState.copyTo(this.mState);
        if (this.mReadyState == 1) {
            this.mReadyState = 2;
            copyTo = true;
        }
        if (copyTo) {
            this.mQSLogger.logTileUpdated(this.mState, this.mTileSpec);
            handleStateChanged();
            boolean z = this instanceof CustomTile ? ((CustomTile) this).mIsSecCustomTile : false;
            String str = this.mTileSpec;
            if (str != null ? this.mHost.isAvailableForSearch(str, z) : false) {
                try {
                    if (!getTileIconBitmap(this.mOldIconDrawable, this.mContext).sameAs(getTileIconBitmap(getTileIconDrawable(), this.mContext))) {
                        saveTileIconAsImage();
                    }
                } catch (Exception unused) {
                }
                this.mOldIconDrawable = getTileIconDrawable();
            }
        }
        if (copyTo) {
            sendTileStatusLog();
        }
        this.mHandler.removeMessages(11);
        this.mHandler.sendEmptyMessageDelayed(11, getStaleTimeout());
        setListening(this.mStaleListener, false);
    }

    public void handleSecondaryClick(Expandable expandable) {
        handleClick(expandable);
    }

    public void handleSetListening(boolean z) {
        String str = this.mTileSpec;
        if (str != null) {
            this.mQSLogger.logTileChangeListening(str, z);
        }
    }

    public void handleStale() {
        if (this.mListeners.isEmpty()) {
            setListening(this.mStaleListener, true);
        } else {
            refreshState(null);
        }
    }

    public void handleStateChanged() {
        if (this.mCallbacks.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            ((QSTile.Callback) this.mCallbacks.valueAt(i)).onStateChanged(this.mState);
        }
    }

    public abstract void handleUpdateState(QSTile.State state, Object obj);

    public void handleUserSwitch(int i) {
        handleRefreshState(null);
    }

    public void initialize() {
        this.mHandler.sendEmptyMessage(12);
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public boolean isAvailable() {
        return true;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final boolean isListening() {
        return this.mLifecycle.state.isAtLeast(Lifecycle.State.RESUMED);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final boolean isTileReady() {
        return this.mReadyState == 2;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void longClick(Expandable expandable) {
        MetricsLogger metricsLogger = this.mMetricsLogger;
        LogMaker type = new LogMaker(366).setType(4);
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        metricsLogger.write(populate(type.addTaggedData(1592, Integer.valueOf(statusBarStateController.getState()))));
        ((QsEventLoggerImpl) this.mUiEventLogger).logWithInstanceId(QSEvent.QS_ACTION_LONG_PRESS, 0, getMetricsSpec(), this.mInstanceId);
        int i = this.mClickEventId;
        this.mClickEventId = i + 1;
        this.mQSLogger.logTileLongClick(this.mTileSpec, statusBarStateController.getState(), this.mState.state, i);
        if (!QpRune.QUICK_SUBSCREEN_PANEL) {
            sendEventCDLog("long press");
            sendRunestoneEventCDLog(SystemUIAnalytics.EID_QUICKTILE_LONG_CLICK);
        } else if (((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
            sendEventCDLog("long press");
            sendRunestoneEventCDLog(SystemUIAnalytics.EID_QUICKTILE_LONG_CLICK);
        }
        if (this.mFalsingManager.isFalseLongTap(1)) {
            return;
        }
        this.mHandler.obtainMessage(4, i, 0, expandable).sendToTarget();
    }

    public abstract QSTile.State newTileState();

    @Override // com.android.systemui.plugins.qs.QSTile
    public LogMaker populate(LogMaker logMaker) {
        QSTile.State state = this.mState;
        if (state instanceof QSTile.BooleanState) {
            logMaker.addTaggedData(928, Integer.valueOf(((QSTile.BooleanState) state).value ? 1 : 0));
        }
        return logMaker.setSubtype(getMetricsCategory()).addTaggedData(1593, Integer.valueOf(this.mIsFullQs)).addTaggedData(927, Integer.valueOf(this.mHost.indexOf(this.mTileSpec)));
    }

    public void postStale() {
        this.mHandler.sendEmptyMessage(11);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void refreshState() {
        refreshState(null);
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public void removeCallback(QSTile.Callback callback) {
        this.mHandler.obtainMessage(9, callback).sendToTarget();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void removeCallbacks() {
        this.mHandler.sendEmptyMessage(8);
    }

    public final void saveTileIconAsImage() {
        boolean z = this instanceof CustomTile ? ((CustomTile) this).mIsSecCustomTile : false;
        String str = this.mTileSpec;
        if (str != null ? this.mHost.isAvailableForSearch(str, z) : false) {
            this.mHandler.obtainMessage(102, 0, 0).sendToTarget();
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void secondaryClick(Expandable expandable) {
        MetricsLogger metricsLogger = this.mMetricsLogger;
        LogMaker type = new LogMaker(926).setType(4);
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        metricsLogger.write(populate(type.addTaggedData(1592, Integer.valueOf(statusBarStateController.getState()))));
        ((QsEventLoggerImpl) this.mUiEventLogger).logWithInstanceId(QSEvent.QS_ACTION_SECONDARY_CLICK, 0, getMetricsSpec(), this.mInstanceId);
        int i = this.mClickEventId;
        this.mClickEventId = i + 1;
        this.mQSLogger.logTileSecondaryClick(this.mTileSpec, statusBarStateController.getState(), this.mState.state, i);
        if (!QpRune.QUICK_SUBSCREEN_PANEL) {
            sendEventCDLog("label tap");
            sendRunestoneEventCDLog(SystemUIAnalytics.EID_QUICKTILE_LABEL_TAP);
        } else if (((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
            sendEventCDLog("label tap");
            sendRunestoneEventCDLog(SystemUIAnalytics.EID_QUICKTILE_LABEL_TAP);
        }
        this.mHandler.obtainMessage(3, i, 0, expandable).sendToTarget();
    }

    public final void sendEventCDLog(String str) {
        String tileMapKey = getTileMapKey();
        HashMap hashMap = QSTileHost.TilesMap.mTilesMap;
        this.mTilesMap.getClass();
        String id = QSTileHost.TilesMap.getId(0, tileMapKey);
        if (id != null) {
            boolean equals = SystemUIAnalytics.SID_QUICKPANEL_EXPANDED.equals(SystemUIAnalytics.getCurrentScreenID());
            QSHost qSHost = this.mHost;
            if (equals) {
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), id, SystemUIAnalytics.QPBE_QS_TILE_INTERACTION, str, SystemUIAnalytics.QPBE_QS_TILE_ICON_POSITION, String.valueOf(qSHost.indexOf(this.mTileSpec)));
                return;
            }
            String currentScreenID = SystemUIAnalytics.getCurrentScreenID();
            SecQQSTileHost qQSTileHost = qSHost.getQQSTileHost();
            SystemUIAnalytics.sendEventCDLog(currentScreenID, id, SystemUIAnalytics.QPBE_QQS_TILE_INTERACTION, str, SystemUIAnalytics.QPBE_QQS_TILE_ICON_POSITION, String.valueOf(qQSTileHost.mTileSpecs.indexOf(this.mTileSpec)));
        }
    }

    public final void sendRunestoneEventCDLog(String str) {
        boolean equals = SystemUIAnalytics.SID_QUICKPANEL_EXPANDED.equals(SystemUIAnalytics.getCurrentScreenID());
        QSHost qSHost = this.mHost;
        if (equals) {
            SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.getCurrentScreenID(), str, SystemUIAnalytics.QPBE_QS_TILE_INTERACTION, String.valueOf(qSHost.indexOf(this.mTileSpec)), "settings", getTileMapKey(), SystemUIAnalytics.RUNESTONE_LABEL_QP_BUTTON);
            return;
        }
        String currentScreenID = SystemUIAnalytics.getCurrentScreenID();
        SecQQSTileHost qQSTileHost = qSHost.getQQSTileHost();
        SystemUIAnalytics.sendRunestoneEventCDLog(currentScreenID, str, SystemUIAnalytics.QPBE_QQS_TILE_INTERACTION, String.valueOf(qQSTileHost.mTileSpecs.indexOf(this.mTileSpec)), "settings", getTileMapKey(), SystemUIAnalytics.RUNESTONE_LABEL_QP_BUTTON);
    }

    public void sendTileStatusLog() {
        String tileMapKey = getTileMapKey();
        this.mHost.sendTileStatusLog(getTileMapValue(), tileMapKey);
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final void setListening(Object obj, boolean z) {
        this.mHandler.obtainMessage(10, z ? 1 : 0, 0, obj).sendToTarget();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void setTileSpec(String str) {
        this.mTileSpec = str;
        this.mState.spec = str;
        this.mTmpState.spec = str;
    }

    public void showItPolicyToast() {
        String valueOf = String.valueOf(getTileLabel());
        if (valueOf.contains("\n")) {
            valueOf = valueOf.replace("\n", " ");
        }
        Context context = this.mContext;
        Toast.makeText(context, context.getString(R.string.quick_settings_it_policy_prevents, valueOf), 0).show();
    }

    public final void showItPolicyToastOnSubScreen(Context context) {
        String valueOf = String.valueOf(getTileLabel());
        if (valueOf.contains("\n")) {
            valueOf = valueOf.replace("\n", " ");
        }
        Toast.makeText(context, this.mContext.getString(R.string.quick_settings_it_policy_prevents, valueOf), 0).show();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void userSwitch(int i) {
        this.mHandler.obtainMessage(6, i, 0).sendToTarget();
    }

    public final void refreshState(Object obj) {
        this.mHandler.obtainMessage(5, obj).sendToTarget();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void click(Expandable expandable) {
        MetricsLogger metricsLogger = this.mMetricsLogger;
        LogMaker type = new LogMaker(925).setType(4);
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        metricsLogger.write(populate(type.addTaggedData(1592, Integer.valueOf(statusBarStateController.getState()))));
        ((QsEventLoggerImpl) this.mUiEventLogger).logWithInstanceId(QSEvent.QS_ACTION_CLICK, 0, getMetricsSpec(), this.mInstanceId);
        int i = this.mClickEventId;
        this.mClickEventId = i + 1;
        this.mQSLogger.logTileClick(this.mTileSpec, statusBarStateController.getState(), this.mState.state, i);
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            if (((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                sendEventCDLog("icon tap");
                sendRunestoneEventCDLog(SystemUIAnalytics.EID_QUICKTILE_ICON_TAP);
            }
        } else {
            sendEventCDLog("icon tap");
            sendRunestoneEventCDLog(SystemUIAnalytics.EID_QUICKTILE_ICON_TAP);
        }
        if (this.mFalsingManager.isFalseTap(1)) {
            return;
        }
        this.mHandler.obtainMessage(2, i, 0, expandable).sendToTarget();
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class DrawableIcon extends QSTile.Icon {
        public final Drawable mDrawable;
        public final Drawable mInvisibleDrawable;

        public DrawableIcon(Drawable drawable) {
            this.mDrawable = drawable;
            if (drawable instanceof ScalingDrawableWrapper) {
                this.mInvisibleDrawable = ((ScalingDrawableWrapper) drawable).mCloneDrawable;
                return;
            }
            Drawable.ConstantState constantState = drawable.getConstantState();
            if (constantState != null) {
                this.mInvisibleDrawable = constantState.newDrawable();
                return;
            }
            if (!(drawable instanceof SignalDrawable)) {
                Log.w("QSTileImpl", "DrawableIcon: drawable has null ConstantState and is not a SignalDrawable");
            }
            this.mInvisibleDrawable = drawable;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public final Drawable getDrawable(Context context) {
            return this.mDrawable;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public final Drawable getInvisibleDrawable(Context context) {
            return this.mInvisibleDrawable;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public String toString() {
            return "DrawableIcon";
        }

        public DrawableIcon(Drawable drawable, Context context) {
            this.mDrawable = drawable;
            if (drawable instanceof ScalingDrawableWrapper) {
                this.mInvisibleDrawable = new ScalingDrawableWrapper(((ScalingDrawableWrapper) drawable).mCloneDrawable, SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_non_sec_customtile_icon_resize_ratio, ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getTileIconSize(context) / r3.getIntrinsicWidth()));
            } else {
                this.mInvisibleDrawable = drawable.getConstantState().newDrawable();
            }
        }
    }

    public void handleInitialize() {
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void setDetailListening(boolean z) {
    }
}
