package com.android.systemui.p016qs.tileimpl;

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
import android.view.View;
import android.widget.Toast;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.indexsearch.CircleFramedTileIcon;
import com.android.systemui.indexsearch.Searchable;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.p014qp.SubscreenQsPanelController;
import com.android.systemui.p016qs.QSEvent;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QSTileHost;
import com.android.systemui.p016qs.QsEventLogger;
import com.android.systemui.p016qs.QsEventLoggerImpl;
import com.android.systemui.p016qs.SecQQSTileHost;
import com.android.systemui.p016qs.SecQSPanelResourcePicker;
import com.android.systemui.p016qs.SideLabelTileLayout;
import com.android.systemui.p016qs.external.CustomTile;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.DetailAdapter;
import com.android.systemui.plugins.p013qs.QSIconView;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.ScalingDrawableWrapper;
import com.android.systemui.util.SystemUIAnalytics;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class QSTileImpl implements QSTile, LifecycleOwner, Dumpable, Searchable {
    public static final boolean DEBUG = Log.isLoggable("Tile", 3);
    public final ActivityStarter mActivityStarter;
    public boolean mAnnounceNextStateChange;
    public final Context mContext;
    protected RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    public final FalsingManager mFalsingManager;
    public final HandlerC2195H mHandler;
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
    public final QsEventLoggerImpl mUiEventLogger;
    public final Handler mUiHandler;
    public final String TAG = "Tile.".concat(getClass().getSimpleName());
    public final ArraySet mListeners = new ArraySet();
    public int mClickEventId = 0;
    public final ArrayList mCallbacks = new ArrayList();
    public final Object mStaleListener = new Object();
    public final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.qs.tileimpl.QSTileImpl$H */
    public class HandlerC2195H extends Handler {
        protected static final int STALE = 11;

        public HandlerC2195H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                int i = message.what;
                boolean z = true;
                if (i == 1) {
                    QSTileImpl qSTileImpl = QSTileImpl.this;
                    QSTile.Callback callback = (QSTile.Callback) message.obj;
                    qSTileImpl.mCallbacks.add(callback);
                    callback.onStateChanged(qSTileImpl.mState);
                    return;
                }
                if (i == 8) {
                    QSTileImpl.this.mCallbacks.clear();
                    return;
                }
                if (i == 9) {
                    QSTileImpl.this.mCallbacks.remove((QSTile.Callback) message.obj);
                    return;
                }
                if (i == 2) {
                    QSTileImpl qSTileImpl2 = QSTileImpl.this;
                    qSTileImpl2.mAnnounceNextStateChange = true;
                    if (qSTileImpl2.mState.disabledByPolicy) {
                        QSTileImpl.this.mActivityStarter.postStartActivityDismissingKeyguard(RestrictedLockUtils.getShowAdminSupportDetailsIntent(qSTileImpl2.mEnforcedAdmin), 0);
                        return;
                    } else {
                        if (qSTileImpl2.mHost.shouldUnavailableByKnox(qSTileImpl2.mTileSpec)) {
                            String str = QSTileImpl.this.TAG;
                            return;
                        }
                        QSTileImpl qSTileImpl3 = QSTileImpl.this;
                        qSTileImpl3.mQSLogger.logHandleClick(message.arg1, qSTileImpl3.mTileSpec);
                        QSTileImpl.this.handleClick((View) message.obj);
                        return;
                    }
                }
                if (i == 3) {
                    QSTileImpl qSTileImpl4 = QSTileImpl.this;
                    if (qSTileImpl4.mHost.shouldUnavailableByKnox(qSTileImpl4.mTileSpec)) {
                        String str2 = QSTileImpl.this.TAG;
                        return;
                    }
                    QSTileImpl qSTileImpl5 = QSTileImpl.this;
                    qSTileImpl5.mQSLogger.logHandleSecondaryClick(message.arg1, qSTileImpl5.mTileSpec);
                    QSTileImpl.this.handleSecondaryClick((View) message.obj);
                    return;
                }
                if (i == 4) {
                    QSTileImpl qSTileImpl6 = QSTileImpl.this;
                    if (qSTileImpl6.mHost.shouldUnavailableByKnox(qSTileImpl6.mTileSpec)) {
                        String str3 = QSTileImpl.this.TAG;
                        return;
                    }
                    QSTileImpl qSTileImpl7 = QSTileImpl.this;
                    qSTileImpl7.mQSLogger.logHandleLongClick(message.arg1, qSTileImpl7.mTileSpec);
                    QSTileImpl.this.handleLongClick((View) message.obj);
                    return;
                }
                if (i == 5) {
                    QSTileImpl.this.handleRefreshState(message.obj);
                    return;
                }
                if (i == 6) {
                    QSTileImpl.this.handleUserSwitch(message.arg1);
                    return;
                }
                if (i == 7) {
                    QSTileImpl.this.handleDestroy();
                    return;
                }
                if (i == 10) {
                    QSTileImpl qSTileImpl8 = QSTileImpl.this;
                    Object obj = message.obj;
                    if (message.arg1 == 0) {
                        z = false;
                    }
                    QSTileImpl.m1627$$Nest$mhandleSetListeningInternal(qSTileImpl8, obj, z);
                    return;
                }
                if (i == 11) {
                    QSTileImpl.this.handleStale();
                    return;
                }
                if (i == 12) {
                    QSTileImpl.this.handleInitialize();
                } else if (i == 102) {
                    QSTileImpl.m1626$$Nest$mhandleSaveTileIcon(QSTileImpl.this);
                } else {
                    throw new IllegalArgumentException("Unknown msg: " + message.what);
                }
            } catch (Throwable th) {
                Log.w(QSTileImpl.this.TAG, KeyAttributes$$ExternalSyntheticOutline0.m21m("Error in ", null), th);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    public static void m1626$$Nest$mhandleSaveTileIcon(QSTileImpl qSTileImpl) {
        Drawable tileIconDrawable = qSTileImpl.getTileIconDrawable();
        ((SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class)).getClass();
        Context context = qSTileImpl.mContext;
        int tileIconSize = SecQSPanelResourcePicker.getTileIconSize(context);
        if (qSTileImpl.mTileSpec == null || tileIconDrawable == null) {
            return;
        }
        CircleFramedTileIcon.mContext = context;
        CircleFramedTileIcon circleFramedTileIcon = new CircleFramedTileIcon(tileIconDrawable, tileIconSize);
        Bitmap createBitmap = Bitmap.createBitmap(tileIconSize, tileIconSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        circleFramedTileIcon.setBounds(0, 0, tileIconSize, tileIconSize);
        circleFramedTileIcon.draw(canvas);
        String str = context.getFilesDir() + "/tiles/";
        StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
        m18m.append(qSTileImpl.getTileIconFileName(qSTileImpl.mTileSpec));
        String sb = m18m.toString();
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
    public static void m1627$$Nest$mhandleSetListeningInternal(QSTileImpl qSTileImpl, Object obj, boolean z) {
        String str = qSTileImpl.TAG;
        Handler handler = qSTileImpl.mUiHandler;
        boolean z2 = DEBUG;
        int i = 1;
        ArraySet arraySet = qSTileImpl.mListeners;
        if (z) {
            if (arraySet.add(obj) && arraySet.size() == 1) {
                if (z2) {
                    Log.d(str, "handleSetListening true");
                }
                qSTileImpl.handleSetListening(z);
                handler.post(new QSTileImpl$$ExternalSyntheticLambda0(qSTileImpl, i));
            }
        } else if (arraySet.remove(obj) && arraySet.size() == 0) {
            if (z2) {
                Log.d(str, "handleSetListening false");
            }
            qSTileImpl.handleSetListening(z);
            handler.post(new QSTileImpl$$ExternalSyntheticLambda0(qSTileImpl, 2));
        }
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            if (SideLabelTileLayout.class.equals(it.next().getClass())) {
                qSTileImpl.mIsFullQs = 1;
                return;
            }
        }
        qSTileImpl.mIsFullQs = 0;
    }

    public QSTileImpl(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger) {
        int i = 0;
        this.mHost = qSHost;
        Context context = qSHost.getContext();
        this.mContext = context;
        QsEventLoggerImpl qsEventLoggerImpl = (QsEventLoggerImpl) qsEventLogger;
        this.mInstanceId = qsEventLoggerImpl.sequence.newInstanceId();
        this.mUiEventLogger = qsEventLoggerImpl;
        if (QSTileHost.TilesMap.sInstance == null) {
            QSTileHost.TilesMap.sInstance = new QSTileHost.TilesMap(context);
        }
        this.mTilesMap = QSTileHost.TilesMap.sInstance;
        this.mUiHandler = handler;
        this.mHandler = new HandlerC2195H(looper);
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
        handler.post(new QSTileImpl$$ExternalSyntheticLambda0(this, i));
        if (QpRune.QUICK_PANEL_SUBSCREEN) {
            ((SubscreenQsPanelController) Dependency.get(SubscreenQsPanelController.class)).getClass();
            this.mSubscreenContext = SubscreenQsPanelController.mContext;
        }
    }

    public static Bitmap getTileIconBitmap(Context context, Drawable drawable) {
        if (drawable != null) {
            try {
                Drawable newDrawable = drawable instanceof ScalingDrawableWrapper ? ((ScalingDrawableWrapper) drawable).mCloneDrawable : drawable.getConstantState().newDrawable();
                ((SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class)).getClass();
                int tileIconSize = SecQSPanelResourcePicker.getTileIconSize(context);
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

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public final void addCallback(QSTile.Callback callback) {
        this.mHandler.obtainMessage(1, callback).sendToTarget();
    }

    public final void checkIfRestrictionEnforcedByAdminOnly(QSTile.State state, String str) {
        QSHost qSHost = this.mHost;
        int userId = qSHost.getUserId();
        Context context = this.mContext;
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(context, str, userId);
        if (checkIfRestrictionEnforced == null || RestrictedLockUtilsInternal.hasBaseUserRestriction(context, str, qSHost.getUserId())) {
            state.disabledByPolicy = false;
            this.mEnforcedAdmin = null;
        } else {
            state.disabledByPolicy = true;
            this.mEnforcedAdmin = checkIfRestrictionEnforced;
        }
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public final void click(View view) {
        LogMaker type = new LogMaker(925).setType(4);
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        this.mMetricsLogger.write(populate(type.addTaggedData(1592, Integer.valueOf(statusBarStateController.getState()))));
        this.mUiEventLogger.logWithInstanceId(QSEvent.QS_ACTION_CLICK, 0, getMetricsSpec(), this.mInstanceId);
        int i = this.mClickEventId;
        this.mClickEventId = i + 1;
        String str = this.mTileSpec;
        this.mQSLogger.logTileClick(statusBarStateController.getState(), this.mState.state, i, str);
        if (!QpRune.QUICK_PANEL_SUBSCREEN) {
            sendEventCDLog("icon tap");
            sendRunestoneEventCDLog("QPBE1101");
        } else if (((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
            sendEventCDLog("icon tap");
            sendRunestoneEventCDLog("QPBE1101");
        }
        if (this.mFalsingManager.isFalseTap(1)) {
            return;
        }
        this.mHandler.obtainMessage(2, i, 0, view).sendToTarget();
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public final QSIconView createTileView(Context context) {
        return new QSIconViewImpl(context);
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public void destroy() {
        this.mHandler.sendEmptyMessage(7);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(getClass().getSimpleName().concat(":"));
        printWriter.print("    ");
        printWriter.println(this.mState.toString());
        printWriter.print("  mCallbacks=  ");
        printWriter.println(this.mCallbacks);
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public DetailAdapter getDetailAdapter() {
        return null;
    }

    public final Uri getIconUri() {
        Context context = this.mContext;
        Uri uriForFile = FileProvider.getUriForFile(context, "com.android.systemui.fileprovider", new File(new File(context.getFilesDir(), "tiles"), getTileIconFileName(this.mTileSpec)));
        context.grantUriPermission("com.sec.android.app.launcher", uriForFile, 1);
        return uriForFile;
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public final InstanceId getInstanceId() {
        return this.mInstanceId;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final LifecycleRegistry getLifecycle() {
        return this.mLifecycle;
    }

    public abstract Intent getLongClickIntent();

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public String getMetricsSpec() {
        return this.mTileSpec;
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

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public final QSTile.State getState() {
        return this.mState;
    }

    public final Context getSubScreenContext() {
        return (!QpRune.QUICK_PANEL_SUBSCREEN || ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) ? this.mContext : this.mSubscreenContext;
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
            Context context = this.mContext;
            if (supplier2 != null) {
                return supplier2.get().getDrawable(context);
            }
            QSTile.Icon icon = state2.icon;
            return icon instanceof AnimationIcon ? icon.getInvisibleDrawable(context) : icon.getDrawable(context);
        } catch (Exception unused) {
            return null;
        }
    }

    public final String getTileIconFileName(String str) {
        String className;
        if (!this.mTileSpec.startsWith("custom(")) {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "_tile_icon.png");
        }
        String customTileNameFromSpec = this.mHost.getCustomTileNameFromSpec(str);
        if (customTileNameFromSpec != null) {
            return customTileNameFromSpec.concat("_tile_icon.png");
        }
        CustomTile customTile = (CustomTile) this;
        if (customTile.mTileClassName != null) {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(), customTile.mTileClassName, "_tile_icon.png");
        }
        ComponentName componentFromSpec = CustomTile.getComponentFromSpec(str);
        return (componentFromSpec == null || (className = componentFromSpec.getClassName()) == null) ? str.concat("_tile_icon.png") : className.concat("_tile_icon.png");
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public abstract CharSequence getTileLabel();

    public String getTileMapKey() {
        QSTile.State state = this.mState;
        return state.isCustomTile ? state.tileClassName : this.mTileSpec;
    }

    public int getTileMapValue() {
        return this.mState.state == 2 ? 1 : 0;
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public final String getTileSpec() {
        return this.mTileSpec;
    }

    public abstract void handleClick(View view);

    public void handleDestroy() {
        this.mQSLogger.logTileDestroyed(this.mTileSpec, "Handle destroy");
        ArraySet arraySet = this.mListeners;
        if (arraySet.size() != 0) {
            handleSetListening(false);
            arraySet.clear();
        }
        this.mCallbacks.clear();
        this.mHandler.removeCallbacksAndMessages(null);
        this.mUiHandler.post(new QSTileImpl$$ExternalSyntheticLambda0(this, 3));
    }

    public void handleLongClick(View view) {
        Intent longClickIntent = getLongClickIntent();
        if (longClickIntent != null) {
            this.mActivityStarter.postStartActivityDismissingKeyguard(longClickIntent, 0, view != null ? ActivityLaunchAnimator.Controller.fromView(view, 32) : null);
        } else {
            Log.d(this.TAG, "handleLongClick() : getLongClickIntent is null.");
            handleSecondaryClick(view);
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
                    if (!getTileIconBitmap(this.mContext, this.mOldIconDrawable).sameAs(getTileIconBitmap(this.mContext, getTileIconDrawable()))) {
                        boolean z2 = this instanceof CustomTile ? ((CustomTile) this).mIsSecCustomTile : false;
                        String str2 = this.mTileSpec;
                        if (str2 != null ? this.mHost.isAvailableForSearch(str2, z2) : false) {
                            this.mHandler.obtainMessage(102, 0, 0).sendToTarget();
                        }
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

    public void handleSecondaryClick(View view) {
        if (getDetailAdapter() != null) {
            showDetail(true);
        } else {
            handleClick(view);
        }
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
        ArrayList arrayList = this.mCallbacks;
        if (arrayList.size() != 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                ((QSTile.Callback) arrayList.get(i)).onStateChanged(this.mState);
            }
        }
    }

    public abstract void handleUpdateState(QSTile.State state, Object obj);

    public void handleUserSwitch(int i) {
        handleRefreshState(null);
    }

    public void initialize() {
        this.mHandler.sendEmptyMessage(12);
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public boolean isAvailable() {
        return true;
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public final boolean isListening() {
        return this.mLifecycle.mState.isAtLeast(Lifecycle.State.RESUMED);
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public final boolean isTileReady() {
        return this.mReadyState == 2;
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public final void longClick(View view) {
        LogMaker type = new LogMaker(366).setType(4);
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        this.mMetricsLogger.write(populate(type.addTaggedData(1592, Integer.valueOf(statusBarStateController.getState()))));
        this.mUiEventLogger.logWithInstanceId(QSEvent.QS_ACTION_LONG_PRESS, 0, getMetricsSpec(), this.mInstanceId);
        int i = this.mClickEventId;
        this.mClickEventId = i + 1;
        String str = this.mTileSpec;
        this.mQSLogger.logTileLongClick(statusBarStateController.getState(), this.mState.state, i, str);
        if (!QpRune.QUICK_PANEL_SUBSCREEN) {
            sendEventCDLog("long press");
            sendRunestoneEventCDLog("QPBE1102");
        } else if (((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
            sendEventCDLog("long press");
            sendRunestoneEventCDLog("QPBE1102");
        }
        this.mHandler.obtainMessage(4, i, 0, view).sendToTarget();
    }

    public abstract QSTile.State newTileState();

    @Override // com.android.systemui.plugins.p013qs.QSTile
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

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public final void refreshState() {
        refreshState(null);
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public void removeCallback(QSTile.Callback callback) {
        this.mHandler.obtainMessage(9, callback).sendToTarget();
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public final void removeCallbacks() {
        this.mHandler.sendEmptyMessage(8);
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public final void secondaryClick(View view) {
        LogMaker type = new LogMaker(926).setType(4);
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        this.mMetricsLogger.write(populate(type.addTaggedData(1592, Integer.valueOf(statusBarStateController.getState()))));
        this.mUiEventLogger.logWithInstanceId(QSEvent.QS_ACTION_SECONDARY_CLICK, 0, getMetricsSpec(), this.mInstanceId);
        int i = this.mClickEventId;
        this.mClickEventId = i + 1;
        String str = this.mTileSpec;
        this.mQSLogger.logTileSecondaryClick(statusBarStateController.getState(), this.mState.state, i, str);
        if (!QpRune.QUICK_PANEL_SUBSCREEN) {
            sendEventCDLog("label tap");
            sendRunestoneEventCDLog("QPBE1103");
        } else if (((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
            sendEventCDLog("label tap");
            sendRunestoneEventCDLog("QPBE1103");
        }
        this.mHandler.obtainMessage(3, i, 0, view).sendToTarget();
    }

    public final void sendEventCDLog(String str) {
        String tileMapKey = getTileMapKey();
        HashMap hashMap = QSTileHost.TilesMap.mTilesMap;
        this.mTilesMap.getClass();
        String id = QSTileHost.TilesMap.getId(0, tileMapKey);
        if (id != null) {
            boolean equals = "QPP101".equals(SystemUIAnalytics.sCurrentScreenID);
            QSHost qSHost = this.mHost;
            if (equals) {
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.sCurrentScreenID, id, "interaction_2depth", str, "position_2depth", String.valueOf(qSHost.indexOf(this.mTileSpec)));
                return;
            }
            String str2 = SystemUIAnalytics.sCurrentScreenID;
            SecQQSTileHost qQSTileHost = qSHost.getQQSTileHost();
            SystemUIAnalytics.sendEventCDLog(str2, id, "interaction_1depth", str, "position_1depth", String.valueOf(qQSTileHost.mTileSpecs.indexOf(this.mTileSpec)));
        }
    }

    public final void sendRunestoneEventCDLog(String str) {
        boolean equals = "QPP101".equals(SystemUIAnalytics.sCurrentScreenID);
        QSHost qSHost = this.mHost;
        if (equals) {
            SystemUIAnalytics.sendRunestoneEventCDLog$1(SystemUIAnalytics.sCurrentScreenID, str, "interaction_2depth", String.valueOf(qSHost.indexOf(this.mTileSpec)), getTileMapKey());
            return;
        }
        String str2 = SystemUIAnalytics.sCurrentScreenID;
        SecQQSTileHost qQSTileHost = qSHost.getQQSTileHost();
        SystemUIAnalytics.sendRunestoneEventCDLog$1(str2, str, "interaction_1depth", String.valueOf(qQSTileHost.mTileSpecs.indexOf(this.mTileSpec)), getTileMapKey());
    }

    public void sendTileStatusLog() {
        String tileMapKey = getTileMapKey();
        this.mHost.sendTileStatusLog(getTileMapValue(), tileMapKey);
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public final void setListening(Object obj, boolean z) {
        this.mHandler.obtainMessage(10, z ? 1 : 0, 0, obj).sendToTarget();
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public final void setTileSpec(String str) {
        this.mTileSpec = str;
        this.mState.spec = str;
        this.mTmpState.spec = str;
    }

    public void showDetail(boolean z) {
        this.mHandler.obtainMessage(99, 1, 0).sendToTarget();
    }

    public final void showItPolicyToast() {
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

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public final void userSwitch(int i) {
        this.mHandler.obtainMessage(6, i, 0).sendToTarget();
    }

    public final void refreshState(Object obj) {
        this.mHandler.obtainMessage(5, obj).sendToTarget();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DrawableIcon extends QSTile.Icon {
        public final Drawable mDrawable;
        public final Drawable mInvisibleDrawable;

        public DrawableIcon(Drawable drawable) {
            this.mDrawable = drawable;
            if (drawable instanceof ScalingDrawableWrapper) {
                this.mInvisibleDrawable = ((ScalingDrawableWrapper) drawable).mCloneDrawable;
            } else {
                this.mInvisibleDrawable = drawable.getConstantState().newDrawable();
            }
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
        public final String toString() {
            return "DrawableIcon";
        }

        public DrawableIcon(Drawable drawable, Context context) {
            this.mDrawable = drawable;
            if (drawable instanceof ScalingDrawableWrapper) {
                Drawable drawable2 = ((ScalingDrawableWrapper) drawable).mCloneDrawable;
                ((SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class)).getClass();
                this.mInvisibleDrawable = new ScalingDrawableWrapper(drawable2, context.getResources().getFloat(R.dimen.qs_non_sec_customtile_icon_resize_ratio) * (SecQSPanelResourcePicker.getTileIconSize(context) / drawable2.getIntrinsicWidth()));
                return;
            }
            this.mInvisibleDrawable = drawable.getConstantState().newDrawable();
        }
    }

    @Override // com.android.systemui.plugins.p013qs.QSTile
    public void setDetailListening(boolean z) {
    }

    public void handleInitialize() {
    }
}
