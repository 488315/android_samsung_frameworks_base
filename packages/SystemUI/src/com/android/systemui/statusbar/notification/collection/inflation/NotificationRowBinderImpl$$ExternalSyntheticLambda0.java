package com.android.systemui.statusbar.notification.collection.inflation;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Trace;
import android.util.ArrayMap;
import android.view.View;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.NotiRune;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.row.NotificationSettingsController;
import com.android.systemui.statusbar.notification.row.RowInflaterTask;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class NotificationRowBinderImpl$$ExternalSyntheticLambda0 implements RowInflaterTask.RowInflationFinishedListener {
    public final /* synthetic */ NotificationRowBinderImpl f$0;
    public final /* synthetic */ NotificationEntry f$1;
    public final /* synthetic */ NotifInflater.Params f$2;
    public final /* synthetic */ NotificationRowContentBinder.InflationCallback f$3;

    public /* synthetic */ NotificationRowBinderImpl$$ExternalSyntheticLambda0(NotificationRowBinderImpl notificationRowBinderImpl, NotificationEntry notificationEntry, NotifInflater.Params params, NotifInflaterImpl.AnonymousClass1 anonymousClass1) {
        this.f$0 = notificationRowBinderImpl;
        this.f$1 = notificationEntry;
        this.f$2 = params;
        this.f$3 = anonymousClass1;
    }

    public final void onInflationFinished(final ExpandableNotificationRow expandableNotificationRow) {
        NotificationRowBinderImpl notificationRowBinderImpl = this.f$0;
        NotificationRowBinderLogger notificationRowBinderLogger = notificationRowBinderImpl.mLogger;
        notificationRowBinderLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationRowBinderLogger$logInflatedRow$2 notificationRowBinderLogger$logInflatedRow$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$logInflatedRow$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("inflated row for ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = notificationRowBinderLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationRowBinder", logLevel, notificationRowBinderLogger$logInflatedRow$2, null);
        NotificationEntry notificationEntry = this.f$1;
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
        DaggerReferenceGlobalRootComponent.ExpandableNotificationRowComponentBuilder expandableNotificationRowComponentBuilder = (DaggerReferenceGlobalRootComponent.ExpandableNotificationRowComponentBuilder) notificationRowBinderImpl.mExpandableNotificationRowComponentBuilder;
        expandableNotificationRowComponentBuilder.getClass();
        expandableNotificationRow.getClass();
        expandableNotificationRowComponentBuilder.expandableNotificationRow = expandableNotificationRow;
        expandableNotificationRowComponentBuilder.notificationEntry = notificationEntry;
        NotificationPresenter notificationPresenter = notificationRowBinderImpl.mPresenter;
        notificationPresenter.getClass();
        expandableNotificationRowComponentBuilder.onExpandClickListener = notificationPresenter;
        ExpandableNotificationRowController expandableNotificationRowController = expandableNotificationRowComponentBuilder.build().getExpandableNotificationRowController();
        expandableNotificationRowController.mActivatableNotificationViewController.init();
        MetricsLogger metricsLogger = expandableNotificationRowController.mMetricsLogger;
        IStatusBarService iStatusBarService = expandableNotificationRowController.mStatusBarService;
        expandableNotificationRowController.mView.initialize(notificationEntry, expandableNotificationRowController.mRemoteInputViewSubcomponentFactory, expandableNotificationRowController.mAppName, expandableNotificationRowController.mNotificationKey, expandableNotificationRowController.mLoggerCallback, expandableNotificationRowController.mKeyguardBypassController, expandableNotificationRowController.mGroupMembershipManager, expandableNotificationRowController.mGroupExpansionManager, expandableNotificationRowController.mHeadsUpManager, expandableNotificationRowController.mRowContentBindStage, expandableNotificationRowController.mOnExpandClickListener, expandableNotificationRowController.mOnFeedbackClickListener, expandableNotificationRowController.mFalsingManager, expandableNotificationRowController.mStatusBarStateController, expandableNotificationRowController.mPeopleNotificationIdentifier, expandableNotificationRowController.mOnUserInteractionCallback, expandableNotificationRowController.mNotificationGutsManager, expandableNotificationRowController.mDismissibilityProvider, metricsLogger, expandableNotificationRowController.mChildrenContainerLogger, expandableNotificationRowController.mColorUpdateLogger, expandableNotificationRowController.mSmartReplyConstants, expandableNotificationRowController.mSmartReplyController, iStatusBarService);
        ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRowController.mView;
        expandableNotificationRow2.setDescendantFocusability(393216);
        if (expandableNotificationRowController.mAllowLongPress) {
            if (((FeatureFlagsClassicRelease) expandableNotificationRowController.mFeatureFlags).isEnabled(Flags.NOTIFICATION_DRAG_TO_CONTENTS)) {
                expandableNotificationRow2.mDragController = expandableNotificationRowController.mDragController;
            }
            expandableNotificationRow2.mLongPressListener = new ExpandableNotificationRowController$$ExternalSyntheticLambda0(expandableNotificationRowController);
        }
        if (NotificationRemoteInputManager.ENABLE_REMOTE_INPUT) {
            expandableNotificationRow2.setDescendantFocusability(131072);
        }
        expandableNotificationRow2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController.3
            public AnonymousClass3() {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                ExpandableNotificationRowController expandableNotificationRowController2 = ExpandableNotificationRowController.this;
                NotificationEntry notificationEntry2 = expandableNotificationRowController2.mView.mEntry;
                long elapsedRealtime = expandableNotificationRowController2.mClock.elapsedRealtime();
                if (notificationEntry2.initializationTime == -1) {
                    notificationEntry2.initializationTime = elapsedRealtime;
                }
                ExpandableNotificationRowController expandableNotificationRowController3 = ExpandableNotificationRowController.this;
                expandableNotificationRowController3.mPluginManager.addPluginListener((PluginListener) expandableNotificationRowController3.mView, NotificationMenuRowPlugin.class, false);
                ExpandableNotificationRowController expandableNotificationRowController4 = ExpandableNotificationRowController.this;
                expandableNotificationRowController4.mView.setOnKeyguard(expandableNotificationRowController4.mStatusBarStateController.getState() == 1);
                ExpandableNotificationRowController expandableNotificationRowController5 = ExpandableNotificationRowController.this;
                expandableNotificationRowController5.mStatusBarStateController.addCallback(expandableNotificationRowController5.mStatusBarStateListener);
                ExpandableNotificationRowController expandableNotificationRowController6 = ExpandableNotificationRowController.this;
                final NotificationSettingsController notificationSettingsController = expandableNotificationRowController6.mSettingsController;
                Uri uri = ExpandableNotificationRowController.BUBBLES_SETTING_URI;
                final NotificationSettingsController.Listener listener = expandableNotificationRowController6.mSettingsListener;
                notificationSettingsController.getClass();
                if (uri == null || listener == null) {
                    return;
                }
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
                                public final /* synthetic */ Uri f$1 = ExpandableNotificationRowController.BUBBLES_SETTING_URI;

                                @Override // java.lang.Runnable
                                public final void run() {
                                    NotificationSettingsController notificationSettingsController2 = NotificationSettingsController.this;
                                    notificationSettingsController2.mSecureSettings.registerContentObserverForUserSync(this.f$1, false, (ContentObserver) notificationSettingsController2.mContentObserver, ((UserTrackerImpl) notificationSettingsController2.mUserTracker).getUserId());
                                }
                            });
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                notificationSettingsController.mBackgroundHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationSettingsController$$ExternalSyntheticLambda1
                    public final /* synthetic */ Uri f$1 = ExpandableNotificationRowController.BUBBLES_SETTING_URI;

                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationSettingsController notificationSettingsController2 = NotificationSettingsController.this;
                        Uri uri2 = this.f$1;
                        NotificationSettingsController.Listener listener2 = listener;
                        int userId = ((UserTrackerImpl) notificationSettingsController2.mUserTracker).getUserId();
                        notificationSettingsController2.mMainHandler.post(new NotificationSettingsController$$ExternalSyntheticLambda3(listener2, userId, notificationSettingsController2.mSecureSettings.getStringForUser(uri2 == null ? null : uri2.getLastPathSegment(), userId)));
                    }
                });
                Trace.traceEnd(4096L);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                ExpandableNotificationRowController expandableNotificationRowController2 = ExpandableNotificationRowController.this;
                expandableNotificationRowController2.mPluginManager.removePluginListener(expandableNotificationRowController2.mView);
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
                                    NotificationSettingsController notificationSettingsController2 = NotificationSettingsController.this;
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
        final NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl = (NotificationStackScrollLayoutController.NotificationListContainerImpl) notificationRowBinderImpl.mListContainer;
        notificationListContainerImpl.getClass();
        expandableNotificationRow.mHeadsUpAnimatingAwayListener = new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$NotificationListContainerImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl2 = NotificationStackScrollLayoutController.NotificationListContainerImpl.this;
                ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow;
                notificationListContainerImpl2.getClass();
                NotificationEntry notificationEntry2 = expandableNotificationRow3.mEntry;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController.mHeadsUpAppearanceController.updateHeader(notificationEntry2);
                notificationStackScrollLayoutController.mHeadsUpAppearanceController.updateHeadsUpAndPulsingRoundness(notificationEntry2);
            }
        };
        expandableNotificationRow.mPrivateLayout.mRemoteInputController = notificationRowBinderImpl.mNotificationRemoteInputManager.mRemoteInputController;
        notificationEntry.row = expandableNotificationRow;
        NotifBindPipeline notifBindPipeline = notificationRowBinderImpl.mNotifBindPipeline;
        NotifBindPipelineLogger notifBindPipelineLogger = notifBindPipeline.mLogger;
        notifBindPipelineLogger.logManagedRow(notificationEntry);
        notifBindPipelineLogger.logManagedRow(notificationEntry);
        NotifBindPipeline.BindEntry bindEntry = (NotifBindPipeline.BindEntry) ((ArrayMap) notifBindPipeline.mBindEntries).get(notificationEntry);
        if (bindEntry != null) {
            bindEntry.row = expandableNotificationRow;
            if (bindEntry.invalidated) {
                notifBindPipeline.requestPipelineRun(notificationEntry);
            }
        }
        StatusBarNotificationPresenter statusBarNotificationPresenter = (StatusBarNotificationPresenter) notificationRowBinderImpl.mPresenter;
        expandableNotificationRow.mAboveShelfChangedListener = statusBarNotificationPresenter.mAboveShelfObserver;
        final KeyguardStateController keyguardStateController = statusBarNotificationPresenter.mKeyguardStateController;
        Objects.requireNonNull(keyguardStateController);
        expandableNotificationRow.mSecureStateProvider = new BooleanSupplier() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda4
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return ((KeyguardStateControllerImpl) KeyguardStateController.this).mCanDismissLockScreen;
            }
        };
        notificationRowBinderImpl.updateRow(notificationEntry, expandableNotificationRow);
        notificationRowBinderImpl.inflateContentViews(notificationEntry, this.f$2, expandableNotificationRow, this.f$3);
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) {
            notificationEntry.setMessageUriToBitmap(notificationRowBinderImpl.mContext);
        }
    }
}
