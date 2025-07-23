package com.android.systemui.statusbar.notification;

import android.R;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.hardware.biometrics.BiometricSourceType;
import android.net.Uri;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfo;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SubscreenDeviceModelCommon extends SubscreenDeviceModelParent implements ConfigurationController.ConfigurationListener {
    public Boolean isNightMode;
    public View mPopUpViewLayout;
    private final SettingsHelper.OnChangedCallback mSettingsListener;
    public final SubscreenDeviceModelCommon$mUpdateMonitorCallback$1 mUpdateMonitorCallback;
    public boolean needsRedaction;
    public SubscreenNotificationInfo popupInfo;

    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon$mUpdateMonitorCallback$1] */
    public SubscreenDeviceModelCommon(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, UserContextProvider userContextProvider, SubscreenNotificationController subscreenNotificationController, Lazy lazy, CommonNotifCollection commonNotifCollection, LogBuffer logBuffer, NotificationInterruptStateProvider notificationInterruptStateProvider, Lazy lazy2, Lazy lazy3, NotificationVisibilityProvider notificationVisibilityProvider, BindEventManager bindEventManager, NotificationController notificationController, UserManager userManager, ConversationNotificationManager conversationNotificationManager) {
        super(context, keyguardUpdateMonitor, settingsHelper, userContextProvider, subscreenNotificationController, lazy, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy2, lazy3, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
        this.isNightMode = Boolean.valueOf((context.getResources().getConfiguration().uiMode & 48) == 32);
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon$mUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                ArrayList arrayList;
                SubscreenNotificationInfo subscreenNotificationInfo;
                List list;
                ExpandableNotificationRow expandableNotificationRow;
                SubscreenSubRoomNotification subscreenSubRoomNotification;
                SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter;
                SubscreenNotificationInfoManager subscreenNotificationInfoManager;
                ArrayList arrayList2;
                SubscreenNotificationInfoManager subscreenNotificationInfoManager2;
                SubscreenDeviceModelCommon subscreenDeviceModelCommon = SubscreenDeviceModelCommon.this;
                subscreenDeviceModelCommon.hideDetailNotificationIfCallback();
                if (Intrinsics.areEqual(subscreenDeviceModelCommon.isShowBouncer(), Boolean.FALSE)) {
                    if (!subscreenDeviceModelCommon.isShownGroup()) {
                        SubscreenSubRoomNotification subscreenSubRoomNotification2 = subscreenDeviceModelCommon.mSubRoomNotification;
                        int subscreenNotificationInfoListSize = (subscreenSubRoomNotification2 == null || subscreenSubRoomNotification2.mNotificationInfoManager == null) ? 0 : SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize();
                        for (int i2 = 0; i2 < subscreenNotificationInfoListSize; i2++) {
                            SubscreenSubRoomNotification subscreenSubRoomNotification3 = subscreenDeviceModelCommon.mSubRoomNotification;
                            if (subscreenSubRoomNotification3 == null || subscreenSubRoomNotification3.mNotificationInfoManager == null || (arrayList = SubscreenNotificationInfoManager.mSubscreenNotificationInfoList) == null || (subscreenNotificationInfo = (SubscreenNotificationInfo) arrayList.get(i2)) == null) {
                                break;
                            }
                            if (subscreenNotificationInfo.mRow.needsRedaction()) {
                                subscreenDeviceModelCommon.notifyListAdapterItemChanged(i2);
                            } else if (subscreenDeviceModelCommon.isOneUI7_0()) {
                                ExpandableNotificationRow expandableNotificationRow2 = subscreenNotificationInfo.mRow;
                                if (expandableNotificationRow2.mIsSummaryWithChildren) {
                                    NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow2.mChildrenContainer;
                                    ExpandableNotificationRow expandableNotificationRow3 = (notificationChildrenContainer == null || (list = notificationChildrenContainer.mAttachedChildren) == null) ? null : (ExpandableNotificationRow) ((ArrayList) list).get(0);
                                    if (expandableNotificationRow3 != null && expandableNotificationRow3.needsRedaction()) {
                                        subscreenDeviceModelCommon.notifyListAdapterItemChanged(i2);
                                    }
                                }
                            }
                        }
                    } else {
                        SubscreenSubRoomNotification subscreenSubRoomNotification4 = subscreenDeviceModelCommon.mSubRoomNotification;
                        Integer valueOf = (subscreenSubRoomNotification4 == null || (subscreenNotificationInfoManager2 = subscreenSubRoomNotification4.mNotificationInfoManager) == null) ? null : Integer.valueOf(subscreenNotificationInfoManager2.mGroupDataArray.size());
                        valueOf.getClass();
                        int intValue = valueOf.intValue();
                        for (int i3 = 0; i3 < intValue; i3++) {
                            SubscreenSubRoomNotification subscreenSubRoomNotification5 = subscreenDeviceModelCommon.mSubRoomNotification;
                            SubscreenNotificationInfo subscreenNotificationInfo2 = (subscreenSubRoomNotification5 == null || (subscreenNotificationInfoManager = subscreenSubRoomNotification5.mNotificationInfoManager) == null || (arrayList2 = subscreenNotificationInfoManager.mGroupDataArray) == null) ? null : (SubscreenNotificationInfo) arrayList2.get(i3);
                            if (subscreenNotificationInfo2 != null && (expandableNotificationRow = subscreenNotificationInfo2.mRow) != null && expandableNotificationRow.needsRedaction() && (subscreenSubRoomNotification = subscreenDeviceModelCommon.mSubRoomNotification) != null && (subscreenNotificationGroupAdapter = subscreenSubRoomNotification.mNotificationGroupAdapter) != null) {
                                subscreenNotificationGroupAdapter.notifyItemChanged((!subscreenDeviceModelCommon.isMainHeader() ? 1 : 0) + i3);
                            }
                        }
                    }
                }
                subscreenDeviceModelCommon.showUnlockIconAnim();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i) {
                SubscreenDeviceModelCommon subscreenDeviceModelCommon = SubscreenDeviceModelCommon.this;
                subscreenDeviceModelCommon.updateShowNotificationTip();
                subscreenDeviceModelCommon.updateNotiShowBlocked();
            }
        };
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon$mSettingsListener$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (Intrinsics.areEqual(uri, Settings.Secure.getUriFor(SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS)) || Intrinsics.areEqual(uri, Settings.Secure.getUriFor(SettingsHelper.INDEX_TURN_ON_COVER_SCREEN_FOR_NOTIFICATION)) || Intrinsics.areEqual(uri, Settings.Secure.getUriFor(SettingsHelper.INDEX_COVER_SCREEN_SHOW_NOTIFICATION))) {
                    SubscreenDeviceModelCommon subscreenDeviceModelCommon = SubscreenDeviceModelCommon.this;
                    subscreenDeviceModelCommon.updateShowNotificationTip();
                    subscreenDeviceModelCommon.updateNotiShowBlocked();
                }
            }
        };
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void dimissTopPopupNotification() {
        if (this.popupViewShowing) {
            dismissImmediately(2);
        }
        if (this.presentationShowing && useTopPresentation()) {
            dismissImmediately(1);
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public void foldStateChanged(boolean z) {
        Log.d("S.S.N.", " FOLD STATE common- ".concat(z ? "FOLD " : "UNFOLD "));
        super.foldStateChanged(z);
        if (z) {
            if (getMSettingsHelper().isCoverscreenShowNotificationTip()) {
                SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
                if (subscreenSubRoomNotification != null) {
                    Log.d("SubscreenSubRoomNotification", "initTipData");
                    subscreenSubRoomNotification.mNotificationRecyclerView.setAdapter(subscreenSubRoomNotification.mSubRoomNotificationTipAdapter);
                }
            } else {
                SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
                if (subscreenSubRoomNotification2 != null) {
                    subscreenSubRoomNotification2.initData();
                }
            }
            this.mIsChangedToFoldState = true;
            boolean isNightModeActive = this.mContext.getResources().getConfiguration().isNightModeActive();
            if (!Intrinsics.areEqual(this.isNightMode, Boolean.valueOf(isNightModeActive))) {
                this.isNightMode = Boolean.valueOf(isNightModeActive);
                SubscreenSubRoomNotification subscreenSubRoomNotification3 = this.mSubRoomNotification;
                if (subscreenSubRoomNotification3 != null) {
                    SubscreenSubRoomNotification.getDeviceModel().initMainHeaderView(subscreenSubRoomNotification3.mSubscreenMainLayout);
                }
            }
        } else {
            SubscreenSubRoomNotification subscreenSubRoomNotification4 = this.mSubRoomNotification;
            if (subscreenSubRoomNotification4 != null) {
                subscreenSubRoomNotification4.updateNotificationState(null, 2);
            }
        }
        this.mIsFolded = z;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public WindowManager.LayoutParams getTopPopupLp() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -2, 2021, R.id.KEYCODE_NUMPAD_DIVIDE, -3);
        if (!getMSettingsHelper().isVoiceAssistantEnabled()) {
            layoutParams.flags |= 8;
        }
        layoutParams.setTitle("SubscreenNotification");
        layoutParams.gravity = 48;
        return layoutParams;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final ImageView initDetailAdapterBackButton(View view) {
        ImageView imageView = (ImageView) view.findViewById(com.android.systemui.R.id.back_key);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon$initDetailAdapterBackButton$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SubscreenDeviceModelCommon.this.hideDetailNotificationAnimated(300, false);
                }
            });
        }
        return imageView;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public void initDetailAdapterItemViewHolder(Context context, final SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, final SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        super.initDetailAdapterItemViewHolder(context, subscreenNotificationDetailAdapter, itemViewHolder);
        itemViewHolder.mCallBackButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon$initDetailAdapterItemViewHolder$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubRoom.StateChangeListener stateChangeListener;
                Log.d("SubscreenNotificationDetailAdapter", "Click call back button");
                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = SubscreenNotificationDetailAdapter.this;
                subscreenNotificationDetailAdapter2.mCallbackClicked = true;
                SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder2 = itemViewHolder;
                PendingIntent pendingIntent = itemViewHolder2.mInfo.mSemanticCallPendingIntent;
                subscreenNotificationDetailAdapter2.mSelectHolder = itemViewHolder2;
                if (this.isRunOnCoverAvailable()) {
                    Intent intent = new Intent();
                    intent.putExtra("runOnCover", true);
                    intent.putExtra("afterKeyguardGone", true);
                    intent.putExtra("ignoreKeyguardState", true);
                    SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
                    if (subscreenSubRoomNotification != null && (stateChangeListener = subscreenSubRoomNotification.mStateChangeListener) != null) {
                        stateChangeListener.requestCoverPopup(pendingIntent, intent);
                    }
                    if (!((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isSecure() || ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isKeyguardUnlocking()) {
                        this.hideDetailNotification();
                    }
                } else {
                    SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
                    if (subscreenSubRoomNotification2 != null) {
                        String str = itemViewHolder.mInfo.mKey;
                        SubRoom.StateChangeListener stateChangeListener2 = subscreenSubRoomNotification2.mStateChangeListener;
                        if (stateChangeListener2 != null) {
                            stateChangeListener2.requestCoverPopup(pendingIntent, str);
                        }
                    }
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_CALLBACK_VIA_MISSED_CALL_NOTIFICATION);
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initGroupAdapterHeaderViewHolder(Context context, View view, final SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter, SubscreenNotificationGroupAdapter.HeaderViewHolder headerViewHolder) {
        super.initGroupAdapterHeaderViewHolder(context, view, subscreenNotificationGroupAdapter, headerViewHolder);
        headerViewHolder.mBackButton = (ImageView) view.findViewById(com.android.systemui.R.id.back_key);
        headerViewHolder.mBackButton.setContentDescription(context.getResources().getString(com.android.systemui.R.string.subscreen_back_button_content_description));
        headerViewHolder.mBackButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon$initGroupAdapterHeaderViewHolder$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SubscreenDeviceModelCommon subscreenDeviceModelCommon = SubscreenDeviceModelCommon.this;
                SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelCommon.mSubRoomNotification;
                if (subscreenSubRoomNotification != null) {
                    subscreenSubRoomNotification.mRecyclerViewItemSelectKey = subscreenNotificationGroupAdapter.mSummaryInfo.mKey;
                }
                subscreenDeviceModelCommon.hideGroupNotification();
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public void initialize() {
        super.initialize();
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        updateShowNotificationTip();
        getMSettingsHelper().registerCallback(this.mSettingsListener, Settings.Secure.getUriFor(SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS), Settings.Secure.getUriFor(SettingsHelper.INDEX_TURN_ON_COVER_SCREEN_FOR_NOTIFICATION), Settings.Secure.getUriFor(SettingsHelper.INDEX_COVER_SCREEN_SHOW_NOTIFICATION));
        ((ConfigurationControllerImpl) ((ConfigurationController) Dependency.sDependency.getDependencyInner(ConfigurationController.class))).addCallback(this);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isProper(NotificationEntry notificationEntry, boolean z) {
        return super.isProper(notificationEntry, z) && this.mIsFolded;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void makePopupDetailView(Context context, NotificationEntry notificationEntry, boolean z, FrameLayout frameLayout) {
        SubscreenNotificationInfoManager subscreenNotificationInfoManager;
        SubscreenNotificationInfo subscreenNotificationInfo = null;
        subscreenNotificationInfo = null;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("makePopupDetailView Common- ", notificationEntry != null ? notificationEntry.mKey : null, "S.S.N.");
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if (subscreenSubRoomNotification != null && (subscreenNotificationInfoManager = subscreenSubRoomNotification.mNotificationInfoManager) != null) {
            subscreenNotificationInfo = subscreenNotificationInfoManager.createItemsData(notificationEntry != null ? notificationEntry.row : null);
        }
        this.popupInfo = subscreenNotificationInfo;
        setPopupViewLayout(context, z, frameLayout);
        setPopupItemInfo(context, notificationEntry, z);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public void onBindDetailAdapterItemViewHolder(SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        SubscreenNotificationInfo subscreenNotificationInfo = itemViewHolder.mInfo;
        if (subscreenNotificationInfo.mIsMissedCall && subscreenNotificationInfo.mHasSemanticCall) {
            itemViewHolder.mCallBackButton.setVisibility(0);
        } else {
            itemViewHolder.mCallBackButton.setVisibility(8);
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void onTipButtonClicked() {
        Prefs.putBoolean(((UserTrackerImpl) this.mUserContextProvider).getUserContext(), "NotiShowCoverScreenTip", true);
        updateShowNotificationTip();
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean panelsEnabled() {
        return ((CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class)).panelsEnabled();
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setDetailAdapterItemHolderButtonContentDescription(SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        super.setDetailAdapterItemHolderButtonContentDescription(subscreenNotificationDetailAdapter, itemViewHolder);
        String string = subscreenNotificationDetailAdapter.mContext.getResources().getString(com.android.systemui.R.string.subscreen_back_button_content_description);
        subscreenNotificationDetailAdapter.mContext.getResources().getString(com.android.systemui.R.string.subscreen_detail_adapter_reply_button_text);
        subscreenNotificationDetailAdapter.mContext.getResources().getString(com.android.systemui.R.string.accessibility_button);
        itemViewHolder.mBackButton.setContentDescription(string);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setDetailAdapterTextHolderButtonContentDescription(SubscreenNotificationDetailAdapter.TextViewHolder textViewHolder, SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter) {
        super.setDetailAdapterTextHolderButtonContentDescription(textViewHolder, subscreenNotificationDetailAdapter);
        textViewHolder.mBackButton.setContentDescription(subscreenNotificationDetailAdapter.mContext.getResources().getString(com.android.systemui.R.string.subscreen_back_button_content_description));
    }

    public final void setEditButton(SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        itemViewHolder.mEditButton.setVisibility(8);
        itemViewHolder.mEditButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon$setEditButton$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.samsung.settings.SubScreenQuickReplySettings");
                intent.setFlags(335544320);
                PendingIntent activityAsUser = PendingIntent.getActivityAsUser(SubscreenDeviceModelCommon.this.mContext, 0, intent, 167772160, null, UserHandle.CURRENT);
                KeyguardManager keyguardManager = (KeyguardManager) SubscreenDeviceModelCommon.this.mContext.getSystemService("keyguard");
                Intent intent2 = new Intent();
                intent2.putExtra("showCoverToast", true);
                intent2.putExtra("ignoreKeyguardState", true);
                keyguardManager.semSetPendingIntentAfterUnlock(activityAsUser, intent2);
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setKeyguardStateWhenAddSubscreenNotificationInfoList(boolean z) {
        this.mIsKeyguardStateWhenAddSubscreenNotificationInfoList = z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:145:0x03c4, code lost:
    
        if (r1.isImportantConversation() == true) goto L256;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setPopupItemInfo(android.content.Context r30, com.android.systemui.statusbar.notification.collection.NotificationEntry r31, boolean r32) {
        /*
            Method dump skipped, instructions count: 1019
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon.setPopupItemInfo(android.content.Context, com.android.systemui.statusbar.notification.collection.NotificationEntry, boolean):void");
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setRightIcon(Context context, SubscreenNotificationInfo subscreenNotificationInfo, View view) {
        ImageView imageView;
        Drawable loadDrawable;
        if (subscreenNotificationInfo == null || view == null || (imageView = (ImageView) view.findViewById(com.android.systemui.R.id.subscreen_right_icon)) == null) {
            return;
        }
        int size = subscreenNotificationInfo.mMessageingStyleInfoArray.size();
        if (!subscreenNotificationInfo.mIsMessagingStyle || size <= 0) {
            Icon icon = subscreenNotificationInfo.mLargeIcon;
            loadDrawable = icon != null ? icon.loadDrawable(context) : null;
        } else {
            loadDrawable = ((SubscreenNotificationInfo.MessagingStyleInfo) subscreenNotificationInfo.mMessageingStyleInfoArray.get(size - 1)).mUriImage;
        }
        if (loadDrawable == null) {
            imageView.setVisibility(8);
        } else {
            imageView.setImageDrawable(loadDrawable);
            imageView.setVisibility(0);
        }
    }

    public final void updateShowNotificationTip() {
        boolean z = false;
        boolean z2 = Prefs.getBoolean(((UserTrackerImpl) this.mUserContextProvider).getUserContext(), "NotiShowCoverScreenTip", false);
        SettingsHelper mSettingsHelper = getMSettingsHelper();
        Boolean valueOf = mSettingsHelper != null ? Boolean.valueOf(mSettingsHelper.isShowNotificationOnKeyguard()) : null;
        SettingsHelper mSettingsHelper2 = getMSettingsHelper();
        Boolean valueOf2 = mSettingsHelper2 != null ? Boolean.valueOf(mSettingsHelper2.isCoverscreenShowNotification()) : null;
        SettingsHelper mSettingsHelper3 = getMSettingsHelper();
        if (mSettingsHelper3 != null) {
            if (!z2) {
                valueOf.getClass();
                if (!valueOf.booleanValue()) {
                    valueOf2.getClass();
                    if (!valueOf2.booleanValue()) {
                        z = true;
                    }
                }
            }
            mSettingsHelper3.setCoverscreenShowNotificationTip(z);
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
    }

    public void setMarqueeItem(TextView textView) {
    }

    public void hideDetailNotificationIfCallback() {
    }

    public void showUnlockIconAnim() {
    }

    public void setPopupViewLayout(Context context, boolean z, FrameLayout frameLayout) {
    }
}
