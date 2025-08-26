package com.android.systemui.statusbar.notification.collection.inflation;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Trace;
import android.util.ArrayMap;
import android.view.View;
import com.android.systemui.NotiRune;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.EntryAdapterFactoryImpl;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntryAdapter;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.row.NotificationSettingsController;
import com.android.systemui.statusbar.notification.row.RowInflaterTask;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$NotificationListContainerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationRowBinderImpl$$ExternalSyntheticLambda0 implements RowInflaterTask.RowInflationFinishedListener {
    public final /* synthetic */ NotificationRowBinderImpl f$0;
    public final /* synthetic */ NotificationEntry f$1;
    public final /* synthetic */ NotifInflater.Params f$2;
    public final /* synthetic */ NotifInflaterImpl.AnonymousClass1 f$3;

    public /* synthetic */ NotificationRowBinderImpl$$ExternalSyntheticLambda0(NotificationRowBinderImpl notificationRowBinderImpl, NotificationEntry notificationEntry, NotifInflater.Params params, NotifInflaterImpl.AnonymousClass1 anonymousClass1) {
        this.f$0 = notificationRowBinderImpl;
        this.f$1 = notificationEntry;
        this.f$2 = params;
        this.f$3 = anonymousClass1;
    }

    public final void onInflationFinished(ExpandableNotificationRow expandableNotificationRow) {
        NotificationRowBinderImpl notificationRowBinderImpl = this.f$0;
        NotificationRowBinderLogger notificationRowBinderLogger = notificationRowBinderImpl.mLogger;
        notificationRowBinderLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationRowBinderLogger$$ExternalSyntheticLambda0 notificationRowBinderLogger$$ExternalSyntheticLambda0 = new NotificationRowBinderLogger$$ExternalSyntheticLambda0(5);
        LogBuffer logBuffer = notificationRowBinderLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotificationRowBinder", logLevel, notificationRowBinderLogger$$ExternalSyntheticLambda0, null);
        NotificationEntry notificationEntry = this.f$1;
        ((LogMessageImpl) logMessageObtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(logMessageObtain);
        DaggerReferenceGlobalRootComponent.ExpandableNotificationRowComponentBuilder expandableNotificationRowComponentBuilder = (DaggerReferenceGlobalRootComponent.ExpandableNotificationRowComponentBuilder) notificationRowBinderImpl.mExpandableNotificationRowComponentBuilder;
        expandableNotificationRowComponentBuilder.getClass();
        expandableNotificationRow.getClass();
        expandableNotificationRowComponentBuilder.expandableNotificationRow = expandableNotificationRow;
        notificationEntry.getClass();
        expandableNotificationRowComponentBuilder.notificationEntry = notificationEntry;
        NotificationPresenter notificationPresenter = notificationRowBinderImpl.mPresenter;
        notificationPresenter.getClass();
        expandableNotificationRowComponentBuilder.onExpandClickListener = notificationPresenter;
        ExpandableNotificationRowController expandableNotificationRowController = ((DaggerReferenceGlobalRootComponent.ExpandableNotificationRowComponentImpl) expandableNotificationRowComponentBuilder.build()).getExpandableNotificationRowController();
        expandableNotificationRowController.mActivatableNotificationViewController.init();
        EntryAdapterFactoryImpl entryAdapterFactoryImpl = (EntryAdapterFactoryImpl) expandableNotificationRowController.mEntryAdapterFactory;
        entryAdapterFactoryImpl.getClass();
        new NotificationEntryAdapter(entryAdapterFactoryImpl.notificationActivityStarter, entryAdapterFactoryImpl.metricsLogger, entryAdapterFactoryImpl.peopleNotificationIdentifier, entryAdapterFactoryImpl.iconStyleProvider, entryAdapterFactoryImpl.visualStabilityCoordinator, entryAdapterFactoryImpl.notificationActionClickManager, entryAdapterFactoryImpl.highPriorityProvider, entryAdapterFactoryImpl.headsUpManager, notificationEntry);
        expandableNotificationRowController.mView.initialize(notificationEntry, expandableNotificationRowController.mRemoteInputViewSubcomponentFactory, expandableNotificationRowController.mAppName, expandableNotificationRowController.mNotificationKey, expandableNotificationRowController.mLoggerCallback, expandableNotificationRowController.mKeyguardBypassController, expandableNotificationRowController.mGroupMembershipManager, expandableNotificationRowController.mGroupExpansionManager, expandableNotificationRowController.mHeadsUpManager, expandableNotificationRowController.mRowContentBindStage, expandableNotificationRowController.mOnExpandClickListener, expandableNotificationRowController.mFalsingManager, expandableNotificationRowController.mStatusBarStateController, expandableNotificationRowController.mPeopleNotificationIdentifier, expandableNotificationRowController.mOnUserInteractionCallback, expandableNotificationRowController.mNotificationGutsManager, expandableNotificationRowController.mDismissibilityProvider, expandableNotificationRowController.mMetricsLogger, expandableNotificationRowController.mChildrenContainerLogger, expandableNotificationRowController.mColorUpdateLogger, expandableNotificationRowController.mSmartReplyConstants, expandableNotificationRowController.mSmartReplyController, expandableNotificationRowController.mStatusBarService, expandableNotificationRowController.mUiEventLogger, expandableNotificationRowController.mNotificationRebindingTracker, expandableNotificationRowController.mMediaHost, expandableNotificationRowController.mMediaDataManager);
        ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRowController.mView;
        expandableNotificationRow2.setDescendantFocusability(393216);
        if (expandableNotificationRowController.mAllowLongPress) {
            if (((FeatureFlagsClassicRelease) expandableNotificationRowController.mFeatureFlags).isEnabled(Flags.NOTIFICATION_DRAG_TO_CONTENTS)) {
                expandableNotificationRow2.mDragController = expandableNotificationRowController.mDragController;
            }
            ExpandableNotificationRowController$$ExternalSyntheticLambda1 expandableNotificationRowController$$ExternalSyntheticLambda1 = new ExpandableNotificationRowController$$ExternalSyntheticLambda1(expandableNotificationRowController, notificationEntry);
            expandableNotificationRowController.mLongPressListener = expandableNotificationRowController$$ExternalSyntheticLambda1;
            expandableNotificationRow2.mLongPressListener = expandableNotificationRowController$$ExternalSyntheticLambda1;
        }
        if (NotificationRemoteInputManager.ENABLE_REMOTE_INPUT) {
            expandableNotificationRow2.setDescendantFocusability(131072);
        }
        expandableNotificationRow2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController.3
            public AnonymousClass3() {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                int i = NotificationBundleUi.$r8$clinit;
                NotificationEntry entryLegacy = ExpandableNotificationRowController.this.mView.getEntryLegacy();
                long jElapsedRealtime = ExpandableNotificationRowController.this.mClock.elapsedRealtime();
                entryLegacy.getClass();
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                if (entryLegacy.initializationTime == -1) {
                    entryLegacy.initializationTime = jElapsedRealtime;
                }
                ExpandableNotificationRowController expandableNotificationRowController2 = ExpandableNotificationRowController.this;
                final NotificationSettingsController notificationSettingsController = expandableNotificationRowController2.mSettingsController;
                final Uri uri = ExpandableNotificationRowController.BUBBLES_SETTING_URI;
                final NotificationSettingsController.Listener listener = expandableNotificationRowController2.mSettingsListener;
                notificationSettingsController.getClass();
                if (uri != null && listener != null) {
                    Trace.traceBegin(4096L, "NotificationSettingsController.addCallback");
                    synchronized (notificationSettingsController.mListeners) {
                        try {
                            ArrayList arrayList = (ArrayList) notificationSettingsController.mListeners.get(uri);
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            if (!arrayList.contains(listener)) {
                                arrayList.add(listener);
                            }
                            notificationSettingsController.mListeners.put(uri, arrayList);
                            if (arrayList.size() == 1) {
                                notificationSettingsController.mBackgroundHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationSettingsController$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        NotificationSettingsController notificationSettingsController2 = notificationSettingsController;
                                        notificationSettingsController2.mSecureSettings.registerContentObserverForUserSync(uri, false, (ContentObserver) notificationSettingsController2.mContentObserver, ((UserTrackerImpl) notificationSettingsController2.mUserTracker).getUserId());
                                    }
                                });
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    notificationSettingsController.mBackgroundHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationSettingsController$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationSettingsController notificationSettingsController2 = notificationSettingsController;
                            Uri uri2 = uri;
                            NotificationSettingsController.Listener listener2 = listener;
                            int userId = ((UserTrackerImpl) notificationSettingsController2.mUserTracker).getUserId();
                            notificationSettingsController2.mMainHandler.post(new NotificationSettingsController$$ExternalSyntheticLambda3(listener2, uri2, userId, notificationSettingsController2.mSecureSettings.getStringForUser(uri2 == null ? null : uri2.getLastPathSegment(), userId), 0));
                        }
                    });
                    Trace.traceEnd(4096L);
                }
                ExpandableNotificationRowController expandableNotificationRowController3 = ExpandableNotificationRowController.this;
                expandableNotificationRowController3.mPluginManager.addPluginListener((PluginListener) expandableNotificationRowController3.mView, NotificationMenuRowPlugin.class, false);
                int i2 = SceneContainerFlag.$r8$clinit;
                ExpandableNotificationRowController expandableNotificationRowController4 = ExpandableNotificationRowController.this;
                expandableNotificationRowController4.mView.setOnKeyguard(expandableNotificationRowController4.mStatusBarStateController.getState() == 1);
                ExpandableNotificationRowController expandableNotificationRowController5 = ExpandableNotificationRowController.this;
                expandableNotificationRowController5.mStatusBarStateController.addCallback(expandableNotificationRowController5.mStatusBarStateListener);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                ExpandableNotificationRowController expandableNotificationRowController2 = ExpandableNotificationRowController.this;
                expandableNotificationRowController2.mPluginManager.removePluginListener(expandableNotificationRowController2.mView);
                int i = SceneContainerFlag.$r8$clinit;
                ExpandableNotificationRowController expandableNotificationRowController3 = ExpandableNotificationRowController.this;
                expandableNotificationRowController3.mStatusBarStateController.removeCallback(expandableNotificationRowController3.mStatusBarStateListener);
                ExpandableNotificationRowController expandableNotificationRowController4 = ExpandableNotificationRowController.this;
                final NotificationSettingsController notificationSettingsController = expandableNotificationRowController4.mSettingsController;
                Uri uri = ExpandableNotificationRowController.BUBBLES_SETTING_URI;
                NotificationSettingsController.Listener listener = expandableNotificationRowController4.mSettingsListener;
                notificationSettingsController.getClass();
                Trace.traceBegin(4096L, "NotificationSettingsController.removeCallback");
                synchronized (notificationSettingsController.mListeners) {
                    try {
                        ArrayList arrayList = (ArrayList) notificationSettingsController.mListeners.get(uri);
                        if (arrayList != null) {
                            arrayList.remove(listener);
                        }
                        if (arrayList == null || arrayList.size() == 0) {
                            notificationSettingsController.mListeners.remove(uri);
                        }
                        if (notificationSettingsController.mListeners.size() == 0) {
                            notificationSettingsController.mBackgroundHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationSettingsController$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    NotificationSettingsController notificationSettingsController2 = notificationSettingsController;
                                    notificationSettingsController2.mSecureSettings.unregisterContentObserverSync(notificationSettingsController2.mContentObserver);
                                }
                            });
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                Trace.traceEnd(4096L);
            }
        });
        notificationEntry.mRowController = expandableNotificationRowController;
        Flags flags = Flags.INSTANCE;
        notificationRowBinderImpl.mFeatureFlags.getClass();
        NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl = (NotificationStackScrollLayoutController.NotificationListContainerImpl) notificationRowBinderImpl.mListContainer;
        notificationListContainerImpl.getClass();
        expandableNotificationRow.mHeadsUpAnimatingAwayListener = new NotificationStackScrollLayoutController$NotificationListContainerImpl$$ExternalSyntheticLambda0(notificationListContainerImpl, expandableNotificationRow);
        NotificationRemoteInputManager notificationRemoteInputManager = notificationRowBinderImpl.mNotificationRemoteInputManager;
        expandableNotificationRow.mPrivateLayout.mRemoteInputController = notificationRemoteInputManager.mRemoteInputController;
        expandableNotificationRow.mNotificationRemoteInputManager = notificationRemoteInputManager;
        notificationEntry.row = expandableNotificationRow;
        NotifBindPipeline notifBindPipeline = notificationRowBinderImpl.mNotifBindPipeline;
        NotifBindPipelineLogger notifBindPipelineLogger = notifBindPipeline.mLogger;
        notifBindPipelineLogger.getClass();
        LogLevel logLevel2 = LogLevel.INFO;
        NotifBindPipelineLogger$$ExternalSyntheticLambda0 notifBindPipelineLogger$$ExternalSyntheticLambda0 = new NotifBindPipelineLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer2 = notifBindPipelineLogger.buffer;
        LogMessage logMessageObtain2 = logBuffer2.obtain("NotifBindPipeline", logLevel2, notifBindPipelineLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain2).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer2.commit(logMessageObtain2);
        LogMessage logMessageObtain3 = logBuffer2.obtain("NotifBindPipeline", logLevel2, new NotifBindPipelineLogger$$ExternalSyntheticLambda0(1), null);
        ((LogMessageImpl) logMessageObtain3).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer2.commit(logMessageObtain3);
        NotifBindPipeline.BindEntry bindEntry = (NotifBindPipeline.BindEntry) ((ArrayMap) notifBindPipeline.mBindEntries).get(notificationEntry);
        if (bindEntry != null) {
            bindEntry.row = expandableNotificationRow;
            if (bindEntry.invalidated) {
                notifBindPipeline.requestPipelineRun(notificationEntry);
            }
        }
        StatusBarNotificationPresenter statusBarNotificationPresenter = (StatusBarNotificationPresenter) notificationRowBinderImpl.mPresenter;
        expandableNotificationRow.mAboveShelfChangedListener = statusBarNotificationPresenter.mAboveShelfObserver;
        KeyguardStateController keyguardStateController = statusBarNotificationPresenter.mKeyguardStateController;
        Objects.requireNonNull(keyguardStateController);
        expandableNotificationRow.mSecureStateProvider = new StatusBarNotificationPresenter$$ExternalSyntheticLambda4(keyguardStateController);
        notificationRowBinderImpl.updateRow(notificationEntry, expandableNotificationRow);
        notificationRowBinderImpl.inflateContentViews(notificationEntry, this.f$2, expandableNotificationRow, this.f$3);
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) {
            notificationEntry.setMessageUriToBitmap(notificationRowBinderImpl.mContext);
        }
    }
}
