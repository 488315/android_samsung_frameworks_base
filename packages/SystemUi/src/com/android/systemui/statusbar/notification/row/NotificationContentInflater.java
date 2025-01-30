package com.android.systemui.statusbar.notification.row;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AppGlobals;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CancellationSignal;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.Pair;
import android.util.Pools;
import android.view.View;
import android.widget.RemoteViews;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.ImageMessageConsumer;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.media.controls.util.MediaFeatureFlag;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor;
import com.android.systemui.statusbar.notification.ImageTransformState;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.policy.InflatedSmartReplyState;
import com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder;
import com.android.systemui.statusbar.policy.SmartReplyStateInflater;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt;
import com.android.systemui.statusbar.policy.SmartReplyView;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.Utils;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.rune.CoreRune;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NotificationContentInflater implements NotificationRowContentBinder {
    public final Executor mBgExecutor;
    public final ConversationNotificationProcessor mConversationProcessor;
    public boolean mInflateSynchronously = false;
    public final boolean mIsMediaInQS;
    public final NotificationRemoteInputManager mRemoteInputManager;
    public final NotifRemoteViewCache mRemoteViewCache;
    public final SmartReplyStateInflater mSmartReplyStateInflater;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class ApplyCallback {
        public abstract RemoteViews getRemoteView();

        public abstract void setResultView(View view);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AsyncInflationTask extends AsyncTask implements NotificationRowContentBinder.InflationCallback, InflationTask {
        public final Executor mBgExecutor;
        public final NotificationRowContentBinder.InflationCallback mCallback;
        public CancellationSignal mCancellationSignal;
        public final Context mContext;
        public final ConversationNotificationProcessor mConversationProcessor;
        public final NotificationEntry mEntry;
        public Exception mError;
        public final boolean mInflateSynchronously;
        public final boolean mIsAllowPrivateNotificationsWhenUnsecure;
        public final boolean mIsLowPriority;
        public final int mReInflateFlags;
        public final NotifRemoteViewCache mRemoteViewCache;
        public final RemoteViews.InteractionHandler mRemoteViewClickHandler;
        public final ExpandableNotificationRow mRow;
        public final SmartReplyStateInflater mSmartRepliesInflater;
        public final boolean mUsesIncreasedHeadsUpHeight;
        public final boolean mUsesIncreasedHeight;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class RtlEnabledContext extends ContextWrapper {
            public /* synthetic */ RtlEnabledContext(Context context, int i) {
                this(context);
            }

            @Override // android.content.ContextWrapper, android.content.Context
            public final ApplicationInfo getApplicationInfo() {
                ApplicationInfo applicationInfo = new ApplicationInfo(super.getApplicationInfo());
                applicationInfo.flags |= QuickStepContract.SYSUI_STATE_BACK_DISABLED;
                return applicationInfo;
            }

            private RtlEnabledContext(Context context) {
                super(context);
            }
        }

        @Override // com.android.systemui.statusbar.InflationTask
        public final void abort() {
            cancel(true);
            CancellationSignal cancellationSignal = this.mCancellationSignal;
            if (cancellationSignal != null) {
                cancellationSignal.cancel();
            }
        }

        @Override // android.os.AsyncTask
        public final /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            return doInBackground();
        }

        public int getReInflateFlags() {
            return this.mReInflateFlags;
        }

        public final void handleError(Exception exc) {
            NotificationEntry notificationEntry = this.mEntry;
            notificationEntry.mRunningTask = null;
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            Log.e("CentralSurfaces", "couldn't inflate view for notification " + (statusBarNotification.getPackageName() + "/0x" + Integer.toHexString(statusBarNotification.getId())), exc);
            NotificationRowContentBinder.InflationCallback inflationCallback = this.mCallback;
            if (inflationCallback != null) {
                inflationCallback.handleInflationException(this.mRow.mEntry, new InflationException("Couldn't inflate contentViews" + exc));
            }
            NotificationInlineImageResolver notificationInlineImageResolver = this.mRow.mImageResolver;
            if (notificationInlineImageResolver.hasCache()) {
                ((NotificationInlineImageCache) notificationInlineImageResolver.mImageCache).mCache.forEach(new NotificationInlineImageCache$$ExternalSyntheticLambda1());
            }
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public final void handleInflationException(NotificationEntry notificationEntry, Exception exc) {
            handleError(exc);
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public final void onAsyncInflationFinished(NotificationEntry notificationEntry) {
            this.mEntry.mRunningTask = null;
            this.mRow.onNotificationUpdated();
            NotificationRowContentBinder.InflationCallback inflationCallback = this.mCallback;
            if (inflationCallback != null) {
                inflationCallback.onAsyncInflationFinished(this.mEntry);
            }
            NotificationInlineImageResolver notificationInlineImageResolver = this.mRow.mImageResolver;
            if (notificationInlineImageResolver.hasCache()) {
                NotificationInlineImageCache notificationInlineImageCache = (NotificationInlineImageCache) notificationInlineImageResolver.mImageCache;
                final Set set = notificationInlineImageCache.mResolver.mWantedUriSet;
                notificationInlineImageCache.mCache.entrySet().removeIf(new Predicate() { // from class: com.android.systemui.statusbar.notification.row.NotificationInlineImageCache$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return !set.contains(((Map.Entry) obj).getKey());
                    }
                });
            }
            NotificationInlineImageResolver notificationInlineImageResolver2 = this.mRow.mImageResolver;
            if (notificationInlineImageResolver2.hasCache()) {
                ((NotificationInlineImageCache) notificationInlineImageResolver2.mImageCache).mCache.forEach(new NotificationInlineImageCache$$ExternalSyntheticLambda1());
            }
        }

        private AsyncInflationTask(Executor executor, boolean z, int i, NotifRemoteViewCache notifRemoteViewCache, NotificationEntry notificationEntry, ConversationNotificationProcessor conversationNotificationProcessor, ExpandableNotificationRow expandableNotificationRow, boolean z2, boolean z3, boolean z4, NotificationRowContentBinder.InflationCallback inflationCallback, RemoteViews.InteractionHandler interactionHandler, boolean z5, SmartReplyStateInflater smartReplyStateInflater, boolean z6) {
            this.mEntry = notificationEntry;
            this.mRow = expandableNotificationRow;
            this.mBgExecutor = executor;
            this.mInflateSynchronously = z;
            this.mReInflateFlags = i;
            this.mRemoteViewCache = notifRemoteViewCache;
            this.mSmartRepliesInflater = smartReplyStateInflater;
            this.mContext = expandableNotificationRow.getContext();
            this.mIsLowPriority = z2;
            this.mUsesIncreasedHeight = z3;
            this.mUsesIncreasedHeadsUpHeight = z4;
            this.mRemoteViewClickHandler = interactionHandler;
            this.mCallback = inflationCallback;
            this.mConversationProcessor = conversationNotificationProcessor;
            this.mIsAllowPrivateNotificationsWhenUnsecure = z6;
            notificationEntry.abortTask();
            notificationEntry.mRunningTask = this;
        }

        public final InflationProgress doInBackground() {
            Set set;
            try {
                StatusBarNotification statusBarNotification = this.mEntry.mSbn;
                try {
                    Notification.addFieldsFromContext(this.mContext.getPackageManager().getApplicationInfoAsUser(statusBarNotification.getPackageName(), 8192, UserHandle.getUserId(statusBarNotification.getUid())), statusBarNotification.getNotification());
                } catch (PackageManager.NameNotFoundException unused) {
                }
                Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(this.mContext, statusBarNotification.getNotification());
                Context packageContext = statusBarNotification.getPackageContext(this.mContext);
                if (recoverBuilder.usesTemplate()) {
                    packageContext = new RtlEnabledContext(packageContext, 0);
                }
                if (this.mEntry.mRanking.isConversation()) {
                    this.mConversationProcessor.processNotification(this.mEntry, recoverBuilder);
                }
                InflationProgress createRemoteViews = NotificationContentInflater.createRemoteViews(this.mReInflateFlags, recoverBuilder, this.mIsLowPriority, this.mUsesIncreasedHeight, this.mUsesIncreasedHeadsUpHeight, packageContext, this.mIsAllowPrivateNotificationsWhenUnsecure);
                NotificationContentInflater.inflateSmartReplyViews(createRemoteViews, this.mReInflateFlags, this.mEntry, this.mContext, packageContext, this.mRow.mPrivateLayout.mCurrentSmartReplyState, this.mSmartRepliesInflater);
                final NotificationInlineImageResolver notificationInlineImageResolver = this.mRow.mImageResolver;
                if (notificationInlineImageResolver.hasCache() && (set = notificationInlineImageResolver.mWantedUriSet) != null) {
                    final long elapsedRealtime = SystemClock.elapsedRealtime() + 1000;
                    set.forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            NotificationInlineImageResolver notificationInlineImageResolver2 = NotificationInlineImageResolver.this;
                            long j = elapsedRealtime;
                            notificationInlineImageResolver2.getClass();
                            notificationInlineImageResolver2.loadImageFromCache(j - SystemClock.elapsedRealtime(), (Uri) obj);
                        }
                    });
                }
                return createRemoteViews;
            } catch (Exception e) {
                this.mError = e;
                return null;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(InflationProgress inflationProgress) {
            Exception exc = this.mError;
            if (exc == null) {
                this.mCancellationSignal = NotificationContentInflater.apply(this.mBgExecutor, this.mInflateSynchronously, inflationProgress, this.mReInflateFlags, this.mRemoteViewCache, this.mEntry, this.mRow, this.mRemoteViewClickHandler, this);
            } else {
                handleError(exc);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class InflationProgress {
        public InflatedSmartReplyViewHolder expandedInflatedSmartReplies;
        public InflatedSmartReplyViewHolder headsUpInflatedSmartReplies;
        public CharSequence headsUpStatusBarText;
        public CharSequence headsUpStatusBarTextPublic;
        public View inflatedContentView;
        public View inflatedExpandedView;
        public View inflatedHeadsUpView;
        public View inflatedPublicView;
        public InflatedSmartReplyState inflatedSmartReplyState;
        public RemoteViews newContentView;
        public RemoteViews newExpandedView;
        public RemoteViews newHeadsUpView;
        public RemoteViews newPublicView;
        Context packageContext;
    }

    public NotificationContentInflater(NotifRemoteViewCache notifRemoteViewCache, NotificationRemoteInputManager notificationRemoteInputManager, ConversationNotificationProcessor conversationNotificationProcessor, MediaFeatureFlag mediaFeatureFlag, Executor executor, SmartReplyStateInflater smartReplyStateInflater) {
        this.mRemoteViewCache = notifRemoteViewCache;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mConversationProcessor = conversationNotificationProcessor;
        this.mIsMediaInQS = Utils.useQsMediaPlayer(mediaFeatureFlag.context);
        this.mBgExecutor = executor;
        this.mSmartReplyStateInflater = smartReplyStateInflater;
    }

    public static CancellationSignal apply(Executor executor, boolean z, final InflationProgress inflationProgress, int i, NotifRemoteViewCache notifRemoteViewCache, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, RemoteViews.InteractionHandler interactionHandler, NotificationRowContentBinder.InflationCallback inflationCallback) {
        HashMap hashMap;
        NotificationContentView notificationContentView;
        NotificationContentView notificationContentView2;
        RemoteViews remoteViews;
        RemoteViews remoteViews2;
        NotificationContentView notificationContentView3 = expandableNotificationRow.mPrivateLayout;
        NotificationContentView notificationContentView4 = expandableNotificationRow.mPublicLayout;
        HashMap hashMap2 = new HashMap();
        if ((i & 1) != 0) {
            hashMap = hashMap2;
            notificationContentView = notificationContentView4;
            notificationContentView2 = notificationContentView3;
            applyRemoteView(executor, z, inflationProgress, i, 1, (NotifRemoteViewCacheImpl) notifRemoteViewCache, notificationEntry, expandableNotificationRow, !canReapplyRemoteView(inflationProgress.newContentView, r5.getCachedView(notificationEntry, 1)), interactionHandler, inflationCallback, notificationContentView3, notificationContentView3.mContractedChild, notificationContentView3.getVisibleWrapper(0), hashMap, new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.1
                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final RemoteViews getRemoteView() {
                    return InflationProgress.this.newContentView;
                }

                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final void setResultView(View view) {
                    InflationProgress.this.inflatedContentView = view;
                }
            });
        } else {
            hashMap = hashMap2;
            notificationContentView = notificationContentView4;
            notificationContentView2 = notificationContentView3;
        }
        if ((i & 2) != 0 && (remoteViews2 = inflationProgress.newExpandedView) != null) {
            NotificationContentView notificationContentView5 = notificationContentView2;
            notificationContentView2 = notificationContentView5;
            applyRemoteView(executor, z, inflationProgress, i, 2, (NotifRemoteViewCacheImpl) notifRemoteViewCache, notificationEntry, expandableNotificationRow, !canReapplyRemoteView(remoteViews2, r5.getCachedView(notificationEntry, 2)), interactionHandler, inflationCallback, notificationContentView2, notificationContentView5.mExpandedChild, notificationContentView5.getVisibleWrapper(1), hashMap, new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.2
                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final RemoteViews getRemoteView() {
                    return InflationProgress.this.newExpandedView;
                }

                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final void setResultView(View view) {
                    InflationProgress.this.inflatedExpandedView = view;
                }
            });
        }
        if ((i & 4) != 0 && (remoteViews = inflationProgress.newHeadsUpView) != null) {
            NotificationContentView notificationContentView6 = notificationContentView2;
            applyRemoteView(executor, z, inflationProgress, i, 4, (NotifRemoteViewCacheImpl) notifRemoteViewCache, notificationEntry, expandableNotificationRow, !canReapplyRemoteView(remoteViews, r5.getCachedView(notificationEntry, 4)), interactionHandler, inflationCallback, notificationContentView6, notificationContentView6.mHeadsUpChild, notificationContentView6.getVisibleWrapper(2), hashMap, new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.3
                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final RemoteViews getRemoteView() {
                    return InflationProgress.this.newHeadsUpView;
                }

                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final void setResultView(View view) {
                    InflationProgress.this.inflatedHeadsUpView = view;
                }
            });
        }
        if ((i & 8) != 0) {
            NotificationContentView notificationContentView7 = notificationContentView;
            applyRemoteView(executor, z, inflationProgress, i, 8, (NotifRemoteViewCacheImpl) notifRemoteViewCache, notificationEntry, expandableNotificationRow, !canReapplyRemoteView(inflationProgress.newPublicView, r5.getCachedView(notificationEntry, 8)), interactionHandler, inflationCallback, notificationContentView7, notificationContentView7.mContractedChild, notificationContentView7.getVisibleWrapper(0), hashMap, new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.4
                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final RemoteViews getRemoteView() {
                    return InflationProgress.this.newPublicView;
                }

                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final void setResultView(View view) {
                    InflationProgress.this.inflatedPublicView = view;
                }
            });
        }
        finishIfDone(inflationProgress, i, notifRemoteViewCache, hashMap, inflationCallback, notificationEntry, expandableNotificationRow);
        CancellationSignal cancellationSignal = new CancellationSignal();
        final HashMap hashMap3 = hashMap;
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda1
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                hashMap3.values().forEach(new NotificationContentInflater$$ExternalSyntheticLambda0(1));
            }
        });
        return cancellationSignal;
    }

    public static void applyRemoteView(Executor executor, boolean z, final InflationProgress inflationProgress, final int i, final int i2, final NotifRemoteViewCache notifRemoteViewCache, final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, final boolean z2, final RemoteViews.InteractionHandler interactionHandler, final NotificationRowContentBinder.InflationCallback inflationCallback, final NotificationContentView notificationContentView, final View view, final NotificationViewWrapper notificationViewWrapper, final HashMap<Integer, CancellationSignal> hashMap, final ApplyCallback applyCallback) {
        final RemoteViews remoteView = applyCallback.getRemoteView();
        if (!z) {
            RemoteViews.OnViewAppliedListener onViewAppliedListener = new RemoteViews.OnViewAppliedListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.5
                public final void onError(Exception exc) {
                    try {
                        View view2 = view;
                        if (z2) {
                            view2 = remoteView.apply(inflationProgress.packageContext, notificationContentView, interactionHandler);
                        } else {
                            remoteView.reapply(inflationProgress.packageContext, view2, interactionHandler);
                        }
                        Log.wtf("NotifContentInflater", "Async Inflation failed but normal inflation finished normally.", exc);
                        onViewApplied(view2);
                    } catch (Exception unused) {
                        hashMap.remove(Integer.valueOf(i2));
                        NotificationContentInflater.handleInflationError(hashMap, exc, ExpandableNotificationRow.this.mEntry, inflationCallback);
                    }
                }

                public final void onViewApplied(View view2) {
                    CachingIconView findViewById;
                    Drawable semGetApplicationIconForIconTray;
                    String isValidView = NotificationContentInflater.isValidView(view2, notificationEntry, ExpandableNotificationRow.this.getResources());
                    if (isValidView != null) {
                        NotificationContentInflater.handleInflationError(hashMap, new InflationException(isValidView), ExpandableNotificationRow.this.mEntry, inflationCallback);
                        hashMap.remove(Integer.valueOf(i2));
                        return;
                    }
                    if (z2) {
                        view2.setIsRootNamespace(true);
                        applyCallback.setResultView(view2);
                    } else {
                        NotificationViewWrapper notificationViewWrapper2 = notificationViewWrapper;
                        if (notificationViewWrapper2 != null) {
                            notificationViewWrapper2.onReinflated();
                        }
                    }
                    hashMap.remove(Integer.valueOf(i2));
                    NotificationContentInflater.finishIfDone(inflationProgress, i, notifRemoteViewCache, hashMap, inflationCallback, notificationEntry, ExpandableNotificationRow.this);
                    final NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.get(NotificationColorPicker.class);
                    if ((view2.findViewById(R.id.icon) instanceof CachingIconView) && (findViewById = view2.findViewById(R.id.icon)) != null) {
                        Pools.SimplePool simplePool = ImageTransformState.sInstancePool;
                        findViewById.setTag(com.android.systemui.R.id.image_icon_tag, ExpandableNotificationRow.this.mEntry.mSbn.getNotification().getSmallIcon());
                        if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
                            try {
                                PackageManager packageManager = ExpandableNotificationRow.this.getContext().getPackageManager();
                                String packageName = ExpandableNotificationRow.this.mEntry.mSbn.getPackageName();
                                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 4202624);
                                if ((packageName.equals("android") || packageName.equals("com.android.systemui") || applicationInfo.icon == 0) ? false : true) {
                                    SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
                                    settingsHelper.getClass();
                                    if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER && settingsHelper.mItemLists.get("colortheme_app_icon").getIntValue() == 1) {
                                        List<LauncherActivityInfo> activityList = ((LauncherApps) ExpandableNotificationRow.this.getContext().getSystemService(LauncherApps.class)).getActivityList(packageName, UserHandle.getUserHandleForUid(applicationInfo.uid));
                                        semGetApplicationIconForIconTray = !activityList.isEmpty() ? activityList.get(0).semGetBadgedIconForIconTray(ExpandableNotificationRow.this.getContext().getResources().getDisplayMetrics().densityDpi) : packageManager.semGetApplicationIconForIconTray(applicationInfo, 1);
                                    } else {
                                        semGetApplicationIconForIconTray = packageManager.semGetApplicationIconForIconTray(applicationInfo, 1);
                                    }
                                    findViewById.setColorFilter((ColorFilter) null);
                                    findViewById.setBackground((Drawable) null);
                                    findViewById.setPadding(0, 0, 0, 0);
                                    findViewById.setImageDrawable(semGetApplicationIconForIconTray);
                                    findViewById.setTag(com.android.systemui.R.id.use_app_icon, Boolean.TRUE);
                                } else {
                                    ((NotificationColorPicker) Dependency.get(NotificationColorPicker.class)).updateSmallIcon(view2, ExpandableNotificationRow.this, findViewById);
                                }
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ((NotificationColorPicker) Dependency.get(NotificationColorPicker.class)).updateSmallIcon(view2, ExpandableNotificationRow.this, findViewById);
                        }
                    }
                    ExpandableNotificationRow expandableNotificationRow2 = ExpandableNotificationRow.this;
                    if (expandableNotificationRow2.mAnimationRunning) {
                        expandableNotificationRow2.setAnimationRunning(true);
                    } else {
                        expandableNotificationRow2.setAnimationRunning(false);
                    }
                    Optional.ofNullable(ExpandableNotificationRow.this).filter(new NotificationContentInflater$5$$ExternalSyntheticLambda0()).ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$5$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) obj;
                            NotificationColorPicker.this.updateAllTextViewColors(expandableNotificationRow3, expandableNotificationRow3.mDimmed);
                        }
                    });
                    ExpandableNotificationRow expandableNotificationRow3 = ExpandableNotificationRow.this;
                    notificationColorPicker.getClass();
                    if (NotificationColorPicker.isNeedToUpdated(expandableNotificationRow3)) {
                        ExpandableNotificationRow expandableNotificationRow4 = ExpandableNotificationRow.this;
                        if (expandableNotificationRow4.mDimmed) {
                            notificationColorPicker.updateBig(view2, notificationColorPicker.getAppPrimaryColor(expandableNotificationRow4), notificationColorPicker.isGrayScaleIcon(ExpandableNotificationRow.this), notificationViewWrapper, true, ExpandableNotificationRow.this);
                        }
                    }
                    ExpandableNotificationRow expandableNotificationRow5 = ExpandableNotificationRow.this;
                    if (expandableNotificationRow5.mIsPinned) {
                        ExpandableNotificationRow.this.applyHeadsUpBackground(NotificationColorPicker.isCustom(expandableNotificationRow5));
                    }
                }

                public final void onViewInflated(View view2) {
                    if (view2 instanceof ImageMessageConsumer) {
                        ((ImageMessageConsumer) view2).setImageResolver(ExpandableNotificationRow.this.mImageResolver);
                    }
                }
            };
            hashMap.put(Integer.valueOf(i2), z2 ? remoteView.applyAsync(inflationProgress.packageContext, notificationContentView, executor, onViewAppliedListener, interactionHandler) : remoteView.reapplyAsync(inflationProgress.packageContext, view, executor, onViewAppliedListener, interactionHandler));
            return;
        }
        try {
            if (!z2) {
                remoteView.reapply(inflationProgress.packageContext, view, interactionHandler);
                String isValidView = isValidView(view, notificationEntry, expandableNotificationRow.getResources());
                if (isValidView != null) {
                    throw new InflationException(isValidView);
                }
                notificationViewWrapper.onReinflated();
                return;
            }
            View apply = remoteView.apply(inflationProgress.packageContext, notificationContentView, interactionHandler);
            String isValidView2 = isValidView(apply, notificationEntry, expandableNotificationRow.getResources());
            if (isValidView2 != null) {
                throw new InflationException(isValidView2);
            }
            apply.setIsRootNamespace(true);
            applyCallback.setResultView(apply);
        } catch (Exception e) {
            handleInflationError(hashMap, e, expandableNotificationRow.mEntry, inflationCallback);
            hashMap.put(Integer.valueOf(i2), new CancellationSignal());
        }
    }

    public static boolean canReapplyRemoteView(RemoteViews remoteViews, RemoteViews remoteViews2) {
        if (remoteViews == null && remoteViews2 == null) {
            return true;
        }
        return (remoteViews == null || remoteViews2 == null || remoteViews2.getPackage() == null || remoteViews.getPackage() == null || !remoteViews.getPackage().equals(remoteViews2.getPackage()) || remoteViews.getLayoutId() != remoteViews2.getLayoutId() || remoteViews2.hasFlags(1)) ? false : true;
    }

    public static InflationProgress createRemoteViews(int i, Notification.Builder builder, boolean z, boolean z2, boolean z3, Context context, boolean z4) {
        InflationProgress inflationProgress = new InflationProgress();
        if ((i & 1) != 0) {
            inflationProgress.newContentView = z ? builder.makeLowPriorityContentView(false) : builder.createContentView(z2);
        }
        if ((i & 2) != 0) {
            RemoteViews createBigContentView = builder.createBigContentView();
            if (createBigContentView == null) {
                if (z) {
                    createBigContentView = builder.createContentView();
                    Notification.Builder.makeHeaderExpanded(createBigContentView);
                } else {
                    createBigContentView = null;
                }
            }
            inflationProgress.newExpandedView = createBigContentView;
        }
        if ((i & 4) != 0) {
            inflationProgress.newHeadsUpView = builder.createHeadsUpContentView(z3);
        }
        if ((i & 8) != 0) {
            inflationProgress.newPublicView = builder.makePublicContentView(z, z4);
        }
        inflationProgress.packageContext = context;
        inflationProgress.headsUpStatusBarText = builder.getHeadsUpStatusBarText(false);
        inflationProgress.headsUpStatusBarTextPublic = builder.getHeadsUpStatusBarText(true);
        return inflationProgress;
    }

    public static boolean finishIfDone(InflationProgress inflationProgress, int i, NotifRemoteViewCache notifRemoteViewCache, HashMap hashMap, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        Assert.isMainThread();
        NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
        NotificationContentView notificationContentView2 = expandableNotificationRow.mPublicLayout;
        if (!hashMap.isEmpty()) {
            return false;
        }
        if ((i & 1) != 0) {
            View view = inflationProgress.inflatedContentView;
            if (view != null) {
                notificationContentView.setContractedChild(view);
                ((NotifRemoteViewCacheImpl) notifRemoteViewCache).putCachedView(notificationEntry, 1, inflationProgress.newContentView);
            } else {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                if (notifRemoteViewCacheImpl.getCachedView(notificationEntry, 1) != null) {
                    notifRemoteViewCacheImpl.putCachedView(notificationEntry, 1, inflationProgress.newContentView);
                }
            }
        }
        expandableNotificationRow.mIsCustomNotification = isCustomNotification(notificationEntry.mSbn.getNotification(), notificationContentView.mContractedChild, notificationEntry.mSbn.getNotification().contentView);
        if ((i & 2) != 0) {
            View view2 = inflationProgress.inflatedExpandedView;
            if (view2 != null) {
                notificationContentView.setExpandedChild(view2);
                ((NotifRemoteViewCacheImpl) notifRemoteViewCache).putCachedView(notificationEntry, 2, inflationProgress.newExpandedView);
            } else if (inflationProgress.newExpandedView == null) {
                notificationContentView.setExpandedChild(null);
                ((NotifRemoteViewCacheImpl) notifRemoteViewCache).removeCachedView(notificationEntry, 2);
            } else {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl2 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                if (notifRemoteViewCacheImpl2.getCachedView(notificationEntry, 2) != null) {
                    notifRemoteViewCacheImpl2.putCachedView(notificationEntry, 2, inflationProgress.newExpandedView);
                }
            }
            RemoteViews remoteViews = inflationProgress.newExpandedView;
            if (remoteViews != null) {
                InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder = inflationProgress.expandedInflatedSmartReplies;
                notificationContentView.mExpandedInflatedSmartReplies = inflatedSmartReplyViewHolder;
                if (inflatedSmartReplyViewHolder == null) {
                    notificationContentView.mExpandedSmartReplyView = null;
                }
            } else {
                notificationContentView.mExpandedInflatedSmartReplies = null;
                notificationContentView.mExpandedSmartReplyView = null;
            }
            expandableNotificationRow.mExpandable = remoteViews != null;
            expandableNotificationRow.mPrivateLayout.updateExpandButtonsDuringLayout(expandableNotificationRow.isExpandable(), false);
            expandableNotificationRow.mIsCustomBigNotification = isCustomNotification(notificationEntry.mSbn.getNotification(), notificationContentView.mExpandedChild, notificationEntry.mSbn.getNotification().bigContentView);
        }
        if ((i & 4) != 0) {
            View view3 = inflationProgress.inflatedHeadsUpView;
            if (view3 != null) {
                notificationContentView.setHeadsUpChild(view3);
                ((NotifRemoteViewCacheImpl) notifRemoteViewCache).putCachedView(notificationEntry, 4, inflationProgress.newHeadsUpView);
            } else if (inflationProgress.newHeadsUpView == null) {
                notificationContentView.setHeadsUpChild(null);
                ((NotifRemoteViewCacheImpl) notifRemoteViewCache).removeCachedView(notificationEntry, 4);
            } else {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl3 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                if (notifRemoteViewCacheImpl3.getCachedView(notificationEntry, 4) != null) {
                    notifRemoteViewCacheImpl3.putCachedView(notificationEntry, 4, inflationProgress.newHeadsUpView);
                }
            }
            if (inflationProgress.newHeadsUpView != null) {
                InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder2 = inflationProgress.headsUpInflatedSmartReplies;
                notificationContentView.mHeadsUpInflatedSmartReplies = inflatedSmartReplyViewHolder2;
                if (inflatedSmartReplyViewHolder2 == null) {
                    notificationContentView.mHeadsUpSmartReplyView = null;
                }
            } else {
                notificationContentView.mHeadsUpInflatedSmartReplies = null;
                notificationContentView.mHeadsUpSmartReplyView = null;
            }
            expandableNotificationRow.mIsCustomHeadsUpNotification = isCustomNotification(notificationEntry.mSbn.getNotification(), notificationContentView.mHeadsUpChild, notificationEntry.mSbn.getNotification().headsUpContentView);
        }
        notificationContentView.mCurrentSmartReplyState = inflationProgress.inflatedSmartReplyState;
        if ((i & 8) != 0) {
            View view4 = inflationProgress.inflatedPublicView;
            if (view4 != null) {
                notificationContentView2.setContractedChild(view4);
                ((NotifRemoteViewCacheImpl) notifRemoteViewCache).putCachedView(notificationEntry, 8, inflationProgress.newPublicView);
            } else {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl4 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                if (notifRemoteViewCacheImpl4.getCachedView(notificationEntry, 8) != null) {
                    notifRemoteViewCacheImpl4.putCachedView(notificationEntry, 8, inflationProgress.newPublicView);
                }
            }
            if (notificationEntry.mSbn.getNotification().publicVersion != null) {
                expandableNotificationRow.mIsCustomPublicNotification = isCustomNotification(notificationEntry.mSbn.getNotification().publicVersion, notificationContentView2.mContractedChild, notificationEntry.mSbn.getNotification().publicVersion.contentView);
            }
        }
        notificationEntry.headsUpStatusBarText = inflationProgress.headsUpStatusBarText;
        notificationEntry.headsUpStatusBarTextPublic = inflationProgress.headsUpStatusBarTextPublic;
        if (inflationCallback != null) {
            inflationCallback.onAsyncInflationFinished(notificationEntry);
        }
        return true;
    }

    public static void handleInflationError(HashMap hashMap, Exception exc, NotificationEntry notificationEntry, NotificationRowContentBinder.InflationCallback inflationCallback) {
        Assert.isMainThread();
        hashMap.values().forEach(new NotificationContentInflater$$ExternalSyntheticLambda0(0));
        if (inflationCallback != null) {
            inflationCallback.handleInflationException(notificationEntry, exc);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01ec A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0175 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void inflateSmartReplyViews(InflationProgress inflationProgress, int i, NotificationEntry notificationEntry, Context context, Context context2, InflatedSmartReplyState inflatedSmartReplyState, SmartReplyStateInflater smartReplyStateInflater) {
        SmartReplyView.SmartReplies smartReplies;
        SmartReplyView.SmartActions smartActions;
        boolean z;
        InflatedSmartReplyState.SuppressedActions suppressedActions;
        InflatedSmartReplyState inflatedSmartReplyState2;
        boolean z2;
        Integer valueOf;
        List<Notification.Action> list;
        List<CharSequence> smartReplies2;
        List<Notification.Action> smartActions2;
        SmartReplyView.SmartReplies smartReplies3;
        boolean z3;
        SmartReplyView.SmartReplies smartReplies4;
        boolean z4;
        Intent intent;
        ResolveInfo resolveInfo;
        PendingIntent pendingIntent;
        PendingIntent pendingIntent2;
        boolean z5;
        boolean z6 = ((i & 1) == 0 || inflationProgress.newContentView == null) ? false : true;
        boolean z7 = ((i & 2) == 0 || inflationProgress.newExpandedView == null) ? false : true;
        boolean z8 = ((i & 4) == 0 || inflationProgress.newHeadsUpView == null) ? false : true;
        if (z6 || z7 || z8) {
            SmartReplyStateInflaterImpl smartReplyStateInflaterImpl = (SmartReplyStateInflaterImpl) smartReplyStateInflater;
            smartReplyStateInflaterImpl.getClass();
            Notification notification2 = notificationEntry.mSbn.getNotification();
            Pair<RemoteInput, Notification.Action> findRemoteInputActionPair = notification2.findRemoteInputActionPair(false);
            Pair<RemoteInput, Notification.Action> findRemoteInputActionPair2 = notification2.findRemoteInputActionPair(true);
            if (smartReplyStateInflaterImpl.constants.mEnabled) {
                boolean z9 = !smartReplyStateInflaterImpl.constants.mRequiresTargetingP || notificationEntry.targetSdk >= 28;
                List<Notification.Action> contextualActions = notification2.getContextualActions();
                if (z9 && findRemoteInputActionPair != null && (pendingIntent2 = ((Notification.Action) findRemoteInputActionPair.second).actionIntent) != null) {
                    CharSequence[] choices = ((RemoteInput) findRemoteInputActionPair.first).getChoices();
                    if (choices != null) {
                        if (!(choices.length == 0)) {
                            z5 = true;
                            if (z5) {
                                smartReplies = new SmartReplyView.SmartReplies(Arrays.asList(((RemoteInput) findRemoteInputActionPair.first).getChoices()), (RemoteInput) findRemoteInputActionPair.first, pendingIntent2, false);
                                smartActions = contextualActions.isEmpty() ^ true ? new SmartReplyView.SmartActions(contextualActions, false) : null;
                                if (smartReplies == null && smartActions == null) {
                                    smartReplies2 = notificationEntry.mRanking.getSmartReplies();
                                    smartActions2 = notificationEntry.mRanking.getSmartActions();
                                    if ((!smartReplies2.isEmpty()) && findRemoteInputActionPair2 != null && ((Notification.Action) findRemoteInputActionPair2.second).getAllowGeneratedReplies() && (pendingIntent = ((Notification.Action) findRemoteInputActionPair2.second).actionIntent) != null) {
                                        smartReplies = new SmartReplyView.SmartReplies(smartReplies2, (RemoteInput) findRemoteInputActionPair2.first, pendingIntent, true);
                                    }
                                    if ((!smartActions2.isEmpty()) || !notification2.getAllowSystemGeneratedContextualActions()) {
                                        smartReplies3 = smartReplies;
                                    } else {
                                        smartReplyStateInflaterImpl.activityManagerWrapper.getClass();
                                        if (ActivityTaskManager.getService().getLockTaskModeState() == 1) {
                                            z3 = true;
                                            if (z3) {
                                                smartReplies3 = smartReplies;
                                            } else {
                                                ArrayList arrayList = new ArrayList();
                                                for (Object obj : smartActions2) {
                                                    PendingIntent pendingIntent3 = ((Notification.Action) obj).actionIntent;
                                                    if (pendingIntent3 == null || (intent = pendingIntent3.getIntent()) == null) {
                                                        smartReplies4 = smartReplies;
                                                    } else {
                                                        smartReplyStateInflaterImpl.packageManagerWrapper.getClass();
                                                        try {
                                                            smartReplies4 = smartReplies;
                                                            try {
                                                                resolveInfo = PackageManagerWrapper.mIPackageManager.resolveIntent(intent, intent.resolveTypeIfNeeded(AppGlobals.getInitialApplication().getContentResolver()), 0, UserHandle.getCallingUserId());
                                                            } catch (RemoteException e) {
                                                                e = e;
                                                                e.printStackTrace();
                                                                resolveInfo = null;
                                                                if (resolveInfo != null) {
                                                                }
                                                                z4 = false;
                                                                if (z4) {
                                                                }
                                                                smartReplies = smartReplies4;
                                                            }
                                                        } catch (RemoteException e2) {
                                                            e = e2;
                                                            smartReplies4 = smartReplies;
                                                        }
                                                        if (resolveInfo != null) {
                                                            String str = resolveInfo.activityInfo.packageName;
                                                            smartReplyStateInflaterImpl.devicePolicyManagerWrapper.getClass();
                                                            z4 = DevicePolicyManagerWrapper.sDevicePolicyManager.isLockTaskPermitted(str);
                                                            if (z4) {
                                                                arrayList.add(obj);
                                                            }
                                                            smartReplies = smartReplies4;
                                                        }
                                                    }
                                                    z4 = false;
                                                    if (z4) {
                                                    }
                                                    smartReplies = smartReplies4;
                                                }
                                                smartReplies3 = smartReplies;
                                                smartActions2 = arrayList;
                                            }
                                            smartActions = new SmartReplyView.SmartActions(smartActions2, true);
                                        }
                                        z3 = false;
                                        if (z3) {
                                        }
                                        smartActions = new SmartReplyView.SmartActions(smartActions2, true);
                                    }
                                    smartReplies = smartReplies3;
                                }
                                if (smartActions != null && (list = smartActions.actions) != null && !list.isEmpty()) {
                                    for (Notification.Action action : list) {
                                        if (action.isContextual() && action.getSemanticAction() == 12) {
                                            z = true;
                                            break;
                                        }
                                    }
                                }
                                z = false;
                                if (z) {
                                    suppressedActions = null;
                                } else {
                                    Notification.Action[] actionArr = notification2.actions;
                                    ArrayList arrayList2 = new ArrayList();
                                    int length = actionArr.length;
                                    int i2 = 0;
                                    int i3 = 0;
                                    while (i2 < length) {
                                        int i4 = i3 + 1;
                                        RemoteInput[] remoteInputs = actionArr[i2].getRemoteInputs();
                                        if (remoteInputs != null) {
                                            if (!(remoteInputs.length == 0)) {
                                                z2 = true;
                                                valueOf = !z2 ? Integer.valueOf(i3) : null;
                                                if (valueOf == null) {
                                                    arrayList2.add(valueOf);
                                                }
                                                i2++;
                                                i3 = i4;
                                            }
                                        }
                                        z2 = false;
                                        if (!z2) {
                                        }
                                        if (valueOf == null) {
                                        }
                                        i2++;
                                        i3 = i4;
                                    }
                                    suppressedActions = new InflatedSmartReplyState.SuppressedActions(arrayList2);
                                }
                                inflatedSmartReplyState2 = new InflatedSmartReplyState(smartReplies, smartActions, suppressedActions, z);
                            }
                        }
                    }
                    z5 = false;
                    if (z5) {
                    }
                }
                smartReplies = null;
                if (contextualActions.isEmpty() ^ true) {
                }
                if (smartReplies == null) {
                    smartReplies2 = notificationEntry.mRanking.getSmartReplies();
                    smartActions2 = notificationEntry.mRanking.getSmartActions();
                    if (!smartReplies2.isEmpty()) {
                        smartReplies = new SmartReplyView.SmartReplies(smartReplies2, (RemoteInput) findRemoteInputActionPair2.first, pendingIntent, true);
                    }
                    if (!smartActions2.isEmpty()) {
                    }
                    smartReplies3 = smartReplies;
                    smartReplies = smartReplies3;
                }
                if (smartActions != null) {
                    while (r0.hasNext()) {
                    }
                }
                z = false;
                if (z) {
                }
                inflatedSmartReplyState2 = new InflatedSmartReplyState(smartReplies, smartActions, suppressedActions, z);
            } else {
                if (SmartReplyStateInflaterKt.DEBUG) {
                    AbstractC0000x2c234b15.m3m("Smart suggestions not enabled, not adding suggestions for ", notificationEntry.mSbn.getKey(), "SmartReplyViewInflater");
                }
                inflatedSmartReplyState2 = new InflatedSmartReplyState(null, null, null, false);
            }
            inflationProgress.inflatedSmartReplyState = inflatedSmartReplyState2;
        }
        if (z7) {
            inflationProgress.expandedInflatedSmartReplies = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyViewHolder(context, context2, notificationEntry, inflatedSmartReplyState, inflationProgress.inflatedSmartReplyState);
        }
        if (z8) {
            inflationProgress.headsUpInflatedSmartReplies = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyViewHolder(context, context2, notificationEntry, inflatedSmartReplyState, inflationProgress.inflatedSmartReplyState);
        }
    }

    public static boolean isCustomNotification(Notification notification2, View view, RemoteViews remoteViews) {
        if (view == null) {
            return false;
        }
        boolean z = remoteViews != null;
        Class notificationStyle = notification2.getNotificationStyle();
        return (Notification.DecoratedCustomViewStyle.class.equals(notificationStyle) || Notification.DecoratedMediaCustomViewStyle.class.equals(notificationStyle)) || z || !(view.getId() == 16909810 || view.getId() == 16909393);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0051 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String isValidView(View view, NotificationEntry notificationEntry, Resources resources) {
        boolean z;
        if (notificationEntry.targetSdk < 31) {
            Notification notification2 = notificationEntry.mSbn.getNotification();
            if (notification2.contentView != null || notification2.bigContentView != null || notification2.headsUpContentView != null) {
                z = true;
                if (z) {
                    Trace.beginSection("NotificationContentInflater#satisfiesMinHeightRequirement");
                    view.measure(View.MeasureSpec.makeMeasureSpec(resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_validation_reference_width), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(0, 0));
                    r2 = view.getMeasuredHeight() >= resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_validation_minimum_allowed_height);
                    Trace.endSection();
                }
                if (r2) {
                    return "inflated notification does not meet minimum height requirement";
                }
                return null;
            }
        }
        z = false;
        if (z) {
        }
        if (r2) {
        }
    }

    public InflationProgress inflateNotificationViews(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotificationRowContentBinder.BindParams bindParams, boolean z, int i, Notification.Builder builder, Context context, SmartReplyStateInflater smartReplyStateInflater) {
        InflationProgress createRemoteViews = createRemoteViews(i, builder, bindParams.isLowPriority, bindParams.usesIncreasedHeight, bindParams.usesIncreasedHeadsUpHeight, context, NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE ? ((SettingsHelper) Dependency.get(SettingsHelper.class)).isAllowPrivateNotificationsWhenUnsecure(ActivityManager.getCurrentUser()) : false);
        inflateSmartReplyViews(createRemoteViews, i, notificationEntry, expandableNotificationRow.getContext(), context, expandableNotificationRow.mPrivateLayout.mCurrentSmartReplyState, smartReplyStateInflater);
        apply(this.mBgExecutor, z, createRemoteViews, i, this.mRemoteViewCache, notificationEntry, expandableNotificationRow, this.mRemoteInputManager.mInteractionHandler, null);
        return createRemoteViews;
    }

    public void setInflateSynchronously(boolean z) {
        this.mInflateSynchronously = z;
    }
}
