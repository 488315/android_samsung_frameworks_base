package com.android.systemui.statusbar.notification;

import android.R;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.hardware.biometrics.BiometricSourceType;
import android.net.Uri;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.PathInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.android.systemui.edgelighting.utils.ExtractAppIconUtils;
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
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import dagger.Lazy;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

public class SubscreenDeviceModelCommon extends SubscreenDeviceModelParent implements ConfigurationController.ConfigurationListener {
    public Boolean isNightMode;
    public View mPopUpViewLayout;
    private final SettingsHelper.OnChangedCallback mSettingsListener;
    public final SubscreenDeviceModelCommon$mUpdateMonitorCallback$1 mUpdateMonitorCallback;
    public boolean needsRedaction;
    public SubscreenNotificationInfo popupInfo;

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon$mUpdateMonitorCallback$1] */
    public SubscreenDeviceModelCommon(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, UserContextProvider userContextProvider, SubscreenNotificationController subscreenNotificationController, Lazy lazy, CommonNotifCollection commonNotifCollection, LogBuffer logBuffer, NotificationInterruptStateProvider notificationInterruptStateProvider, Lazy lazy2, Lazy lazy3, NotificationVisibilityProvider notificationVisibilityProvider, BindEventManager bindEventManager, NotificationController notificationController, UserManager userManager, ConversationNotificationManager conversationNotificationManager) {
        super(context, keyguardUpdateMonitor, settingsHelper, userContextProvider, subscreenNotificationController, lazy, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy2, lazy3, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
        this.isNightMode = Boolean.valueOf((context.getResources().getConfiguration().uiMode & 48) == 32);
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon$mUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                ExpandableNotificationRow expandableNotificationRow;
                SubscreenSubRoomNotification subscreenSubRoomNotification;
                SubscreenNotificationListAdapter subscreenNotificationListAdapter;
                ArrayList arrayList;
                SubscreenDeviceModelCommon subscreenDeviceModelCommon = SubscreenDeviceModelCommon.this;
                subscreenDeviceModelCommon.hideDetailNotificationIfCallback();
                SubscreenSubRoomNotification subscreenSubRoomNotification2 = subscreenDeviceModelCommon.mSubRoomNotification;
                int subscreenNotificationInfoListSize = (subscreenSubRoomNotification2 == null || subscreenSubRoomNotification2.mNotificationInfoManager == null) ? 0 : SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize();
                for (int i2 = 0; i2 < subscreenNotificationInfoListSize; i2++) {
                    SubscreenSubRoomNotification subscreenSubRoomNotification3 = subscreenDeviceModelCommon.mSubRoomNotification;
                    SubscreenNotificationInfo subscreenNotificationInfo = (subscreenSubRoomNotification3 == null || subscreenSubRoomNotification3.mNotificationInfoManager == null || (arrayList = SubscreenNotificationInfoManager.mSubscreenNotificationInfoList) == null) ? null : (SubscreenNotificationInfo) arrayList.get(i2);
                    if (subscreenNotificationInfo != null && (expandableNotificationRow = subscreenNotificationInfo.mRow) != null && expandableNotificationRow.needsRedaction() && (subscreenSubRoomNotification = subscreenDeviceModelCommon.mSubRoomNotification) != null && (subscreenNotificationListAdapter = subscreenSubRoomNotification.mNotificationListAdapter) != null) {
                        subscreenNotificationListAdapter.notifyItemChanged(i2);
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
    public final WindowManager.LayoutParams getTopPopupLp() {
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
    public final void initialize() {
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
        setPopupItemInfo(context, notificationEntry, z, false);
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

    public void setPopupItemInfo(Context context, NotificationEntry notificationEntry, boolean z, boolean z2) {
        FrameLayout frameLayout;
        boolean z3;
        Context context2;
        boolean z4;
        Drawable drawable;
        int dimensionPixelSize;
        int i;
        SubscreenNotificationInfo subscreenNotificationInfo;
        int i2;
        NotificationChannel channel;
        StatusBarNotification statusBarNotification;
        Notification notification2;
        StatusBarNotification statusBarNotification2;
        Notification notification3;
        SubscreenNotificationInfo subscreenNotificationInfo2 = this.popupInfo;
        Boolean valueOf = subscreenNotificationInfo2 != null ? Boolean.valueOf(subscreenNotificationInfo2.mIsMessagingStyle) : null;
        SubscreenNotificationInfo subscreenNotificationInfo3 = this.popupInfo;
        Drawable drawable2 = subscreenNotificationInfo3 != null ? subscreenNotificationInfo3.mIcon : null;
        this.needsRedaction = false;
        Intrinsics.checkNotNull(notificationEntry);
        if (isNotShwonNotificationState(notificationEntry)) {
            if (isKeyguardStats()) {
                this.needsRedaction = notificationEntry.row.needsRedaction();
            } else if (isKnoxSecurity(notificationEntry) && notificationEntry.mUserPublic) {
                this.needsRedaction = true;
            }
        }
        SubscreenNotificationInfo subscreenNotificationInfo4 = this.popupInfo;
        if ("call".equals((subscreenNotificationInfo4 == null || (statusBarNotification2 = subscreenNotificationInfo4.mSbn) == null || (notification3 = statusBarNotification2.getNotification()) == null) ? null : notification3.category)) {
            SubscreenNotificationInfo subscreenNotificationInfo5 = this.popupInfo;
            if (((subscreenNotificationInfo5 == null || (statusBarNotification = subscreenNotificationInfo5.mSbn) == null || (notification2 = statusBarNotification.getNotification()) == null) ? null : notification2.fullScreenIntent) != null && this.needsRedaction && !z) {
                this.needsRedaction = false;
            }
        }
        SubscreenNotificationInfo subscreenNotificationInfo6 = this.popupInfo;
        Icon icon = subscreenNotificationInfo6 != null ? subscreenNotificationInfo6.mConversationIcon : null;
        Icon icon2 = subscreenNotificationInfo6 != null ? subscreenNotificationInfo6.mLargeIcon : null;
        Drawable drawable3 = subscreenNotificationInfo6 != null ? subscreenNotificationInfo6.mAppIcon : null;
        String str = subscreenNotificationInfo6 != null ? subscreenNotificationInfo6.mAppName : null;
        String title = subscreenNotificationInfo6 != null ? subscreenNotificationInfo6.getTitle() : null;
        SubscreenNotificationInfo subscreenNotificationInfo7 = this.popupInfo;
        String str2 = subscreenNotificationInfo7 != null ? subscreenNotificationInfo7.mContent : null;
        View view = this.mPopUpViewLayout;
        TextView textView = view != null ? (TextView) view.findViewById(com.android.systemui.R.id.subscreen_notification_title_text) : null;
        View view2 = this.mPopUpViewLayout;
        TextView textView2 = view2 != null ? (TextView) view2.findViewById(com.android.systemui.R.id.subscreen_notification_content_text) : null;
        View view3 = this.mPopUpViewLayout;
        ImageView imageView = view3 != null ? (ImageView) view3.findViewById(com.android.systemui.R.id.subscreen_notification_header_icon) : null;
        View view4 = this.mPopUpViewLayout;
        ImageView imageView2 = view4 != null ? (ImageView) view4.findViewById(com.android.systemui.R.id.subscreen_notification_header_app_icon) : null;
        View view5 = this.mPopUpViewLayout;
        String str3 = title;
        ImageView imageView3 = view5 != null ? (ImageView) view5.findViewById(com.android.systemui.R.id.subscreen_notification_header_icon_conversation) : null;
        View view6 = this.mPopUpViewLayout;
        ImageView imageView4 = view6 != null ? (ImageView) view6.findViewById(com.android.systemui.R.id.secure_icon) : null;
        View view7 = this.mPopUpViewLayout;
        ImageView imageView5 = imageView4;
        ImageView imageView6 = view7 != null ? (ImageView) view7.findViewById(com.android.systemui.R.id.two_phone_icon) : null;
        View view8 = this.mPopUpViewLayout;
        ImageView imageView7 = imageView6;
        FrameLayout frameLayout2 = view8 != null ? (FrameLayout) view8.findViewById(com.android.systemui.R.id.subscreen_notification_app_icon_background) : null;
        String str4 = this.needsRedaction ? str : str3;
        if (textView != null) {
            textView.setText(str4);
        }
        if (textView2 != null) {
            textView2.setText(str2);
        }
        boolean z5 = str4 == null || StringsKt__StringsKt.trim(str4).toString().length() == 0;
        if (str2 == null || StringsKt__StringsKt.trim(str2).toString().length() == 0) {
            frameLayout = frameLayout2;
            z3 = true;
        } else {
            frameLayout = frameLayout2;
            z3 = false;
        }
        if (z5) {
            if (textView2 != null) {
                textView2.setVisibility(8);
            }
            if (textView != null) {
                textView.setText(str2);
            }
            if (z3 && textView != null) {
                textView.setText(str);
            }
        }
        if (z3 && textView2 != null) {
            textView2.setVisibility(8);
        }
        if (this.needsRedaction) {
            if (textView2 != null) {
                textView2.setText(context.getResources().getString(com.android.systemui.R.string.subscreen_hide_content_new_notification_text));
            }
            if (textView2 != null) {
                textView2.setVisibility(z ? 8 : 0);
            }
        }
        if (this.needsRedaction || !Intrinsics.areEqual(valueOf, Boolean.TRUE) || (icon == null && icon2 == null)) {
            if (!isShowNotificationAppIcon()) {
                if (imageView != null) {
                    imageView.setVisibility(0);
                }
                if (imageView2 != null) {
                    imageView2.setVisibility(8);
                }
                updateSmallIconBg(imageView, false, (z || useTopPresentation()) ? false : true, false);
                if (imageView != null) {
                    imageView.setImageDrawable(drawable2);
                }
                updateIconColor(imageView, notificationEntry);
            } else if (drawable3 == null || (subscreenNotificationInfo = this.popupInfo) == null || subscreenNotificationInfo.useSmallIcon()) {
                if (imageView != null) {
                    imageView.setVisibility(0);
                }
                if (imageView2 != null) {
                    imageView2.setVisibility(8);
                }
                if (imageView != null) {
                    imageView.setImageDrawable(drawable2);
                }
                updateSmallIconSquircleBg(imageView, false, !z);
                updateIconColor(imageView, notificationEntry);
            } else {
                if (imageView != null) {
                    imageView.setVisibility(8);
                }
                if (imageView2 != null) {
                    imageView2.setVisibility(0);
                }
                if (imageView2 != null) {
                    imageView2.setImageDrawable(drawable3);
                }
            }
            SubscreenNotificationInfo subscreenNotificationInfo8 = this.popupInfo;
            if (subscreenNotificationInfo8 == null || !subscreenNotificationInfo8.mIsCall) {
                context2 = context;
            } else {
                FrameLayout frameLayout3 = frameLayout;
                if (frameLayout != null) {
                    frameLayout3.setVisibility(0);
                }
                if (isShowNotificationAppIcon()) {
                    drawable = subscreenNotificationInfo8.mAppIcon;
                    dimensionPixelSize = drawable.getIntrinsicWidth();
                } else {
                    Context context3 = this.mDisplayContext;
                    if (context3 == null) {
                        context3 = null;
                    }
                    drawable = context3.getResources().getDrawable(com.android.systemui.R.drawable.notification_icon_circle, null);
                    Context context4 = this.mDisplayContext;
                    if (context4 == null) {
                        context4 = null;
                    }
                    dimensionPixelSize = context4.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.subscreen_noti_full_popup_icon_size_b5);
                }
                Bitmap.Config config = Bitmap.Config.ARGB_8888;
                Bitmap createBitmap = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, config);
                Canvas canvas = new Canvas(createBitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                Paint paint = new Paint();
                String str5 = subscreenNotificationInfo8.mPkg;
                Drawable drawable4 = subscreenNotificationInfo8.mAppIcon;
                context2 = context;
                int loadAppCustomColor = EdgeLightingSettingUtils.loadAppCustomColor(context2, str5);
                if (loadAppCustomColor == 0) {
                    i = ExtractAppIconUtils.processDominantColorInImage(drawable4);
                    EdgeLightingSettingUtils.saveAppCustomColor(context2, str5, i);
                } else {
                    i = loadAppCustomColor;
                }
                PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
                paint.setColorFilter(new PorterDuffColorFilter(i, mode));
                canvas.drawBitmap(createBitmap, 0.0f, 0.0f, paint);
                ImageView imageView8 = frameLayout3 != null ? (ImageView) frameLayout3.findViewById(com.android.systemui.R.id.subscreen_notification_app_icon_background_color) : null;
                if (imageView8 != null) {
                    imageView8.setBackground(new BitmapDrawable(context.getResources(), createBitmap));
                }
                Bitmap createBitmap2 = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, config);
                Canvas canvas2 = new Canvas(createBitmap2);
                drawable.setBounds(0, 0, canvas2.getWidth(), canvas2.getHeight());
                drawable.draw(canvas2);
                Paint paint2 = new Paint();
                paint2.setColorFilter(new PorterDuffColorFilter(-1, mode));
                canvas2.drawBitmap(createBitmap2, 0.0f, 0.0f, paint2);
                ImageView imageView9 = frameLayout3 != null ? (ImageView) frameLayout3.findViewById(com.android.systemui.R.id.subscreen_notification_app_icon_background_white) : null;
                if (imageView9 != null) {
                    imageView9.setBackground(new BitmapDrawable(context.getResources(), createBitmap2));
                }
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.5f, 1.0f, 2.5f, 1, 0.5f, 1, 0.5f);
                scaleAnimation.setDuration(DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
                scaleAnimation.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
                scaleAnimation.setRepeatCount(-1);
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                alphaAnimation.setDuration(1000L);
                alphaAnimation.setStartOffset(1000L);
                alphaAnimation.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f));
                alphaAnimation.setRepeatCount(-1);
                AnimationSet animationSet = new AnimationSet(false);
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(alphaAnimation);
                if (frameLayout3 != null) {
                    frameLayout3.startAnimation(animationSet);
                }
                this.mCallFullPopupBacgroundView = frameLayout3;
            }
            z4 = false;
        } else {
            if (imageView3 != null) {
                imageView3.setVisibility(0);
            }
            if (imageView != null) {
                imageView.setVisibility(8);
            }
            if (imageView2 != null) {
                imageView2.setVisibility(8);
            }
            if (icon != null) {
                if (imageView3 != null) {
                    imageView3.setImageIcon(icon);
                }
            } else if (imageView3 != null) {
                imageView3.setImageIcon(icon2);
            }
            context2 = context;
            z4 = true;
        }
        View view9 = this.mPopUpViewLayout;
        ImageView imageView10 = view9 != null ? (ImageView) view9.findViewById(com.android.systemui.R.id.subscreen_notification_sub_icon) : null;
        if (z4) {
            if (isShowNotificationAppIcon()) {
                if (imageView10 != null) {
                    imageView10.setImageDrawable(drawable3);
                }
                i2 = 0;
            } else {
                i2 = 0;
                updateSmallIconBg(imageView10, false, false, true);
                if (imageView10 != null) {
                    imageView10.setImageDrawable(drawable2);
                }
                updateIconColor(imageView10, notificationEntry);
            }
            if (imageView10 != null) {
                imageView10.setVisibility(i2);
            }
        } else {
            i2 = 0;
            if (imageView10 != null) {
                imageView10.setVisibility(8);
            }
        }
        updateImportBadgeIconRing(this.mPopUpViewLayout, (z4 && (channel = notificationEntry.mRanking.getChannel()) != null && channel.isImportantConversation()) ? true : i2);
        SubscreenDeviceModelParent.updateKnoxIcon(imageView5, this.popupInfo);
        SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView7, this.popupInfo);
        if (!this.needsRedaction) {
            SubscreenNotificationInfo subscreenNotificationInfo9 = this.popupInfo;
            Intrinsics.checkNotNull(subscreenNotificationInfo9);
            View view10 = this.mPopUpViewLayout;
            Intrinsics.checkNotNull(view10);
            setRightIcon(context2, subscreenNotificationInfo9, view10);
        }
        if (z) {
            View view11 = this.mPopUpViewLayout;
            Intrinsics.checkNotNull(view11);
            setListItemTextLayout(context2, view11);
        }
        setMarqueeItem(textView2);
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setPopupItemInfo Common - needsRedaction : ", "S.S.N.", this.needsRedaction);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setRightIcon(Context context, SubscreenNotificationInfo subscreenNotificationInfo, View view) {
        Drawable loadDrawable;
        ImageView imageView = (ImageView) view.findViewById(com.android.systemui.R.id.subscreen_right_icon);
        if (imageView == null) {
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
                Intrinsics.checkNotNull(valueOf);
                if (!valueOf.booleanValue()) {
                    Intrinsics.checkNotNull(valueOf2);
                    if (!valueOf2.booleanValue()) {
                        z = true;
                    }
                }
            }
            mSettingsHelper3.setCoverscreenShowNotificationTip(z);
        }
    }

    public void hideDetailNotificationIfCallback() {
    }

    public void showUnlockIconAnim() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
    }

    public void setMarqueeItem(TextView textView) {
    }

    public void setPopupViewLayout(Context context, boolean z, FrameLayout frameLayout) {
    }
}
