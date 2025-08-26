package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.hardware.devicestate.DeviceState;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UserManager;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArraySet;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.DateTimeView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.util.ContrastColorUtil;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfo;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda9;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.Bubbles;
import dagger.Lazy;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import noticolorpicker.NotificationColorPicker;

/* loaded from: classes3.dex */
public class SubscreenDeviceModelParent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ActivityManager activityManager;
    public int bubbleReply;
    public NotificationEntry currentPopupViewEntry;
    public NotificationEntry currentPresentationEntry;
    public boolean isHomeKeyClicked;
    public int largeSubScreenCardWidth;
    public NotificationEntry mBubbleReplyEntry;
    public final LogBuffer mBuffer;
    public FrameLayout mCallFullPopupBacgroundView;
    public final Context mContext;
    public final SubscreenNotificationController mController;
    public Context mDisplayContext;
    public final NotificationInterruptStateProvider mInterruptionStateProvider;
    public boolean mIsChangedToFoldState;
    public boolean mIsCovered;
    public boolean mIsFlexMode;
    public boolean mIsFolded;
    public boolean mIsFullscreenFullPopupWindowClosing;
    public boolean mIsKeyguardStateWhenAddSubscreenNotificationInfoList;
    public boolean mIsNotificationRemoved;
    public boolean mIsRemoving;
    public boolean mIsReplyNotification;
    public boolean mIsUpdatedAllMainList;
    public KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public int mListAdapterItemPosition;
    public Animator mMainViewAnimator;
    public int mMoreNotificationCount;
    public int mNotiPopupType;
    public View mNotiPopupView;
    public final CommonNotifCollection mNotifCollection;
    public NotificationActivityStarter mNotificationActivityStarter;
    public PowerManager mPowerManager;
    public SubscreenNotificationPresentation mPresentation;
    public SettableWakeLock mScreenOnwakelock;
    private final SettingsHelper mSettingsHelper;
    public Display mSubDisplay;
    public SubscreenSubRoomNotification mSubRoomNotification;
    public final Lazy mSubScreenManagerLazy;
    public final UserContextProvider mUserContextProvider;
    public final UserManager mUserManager;
    public WindowManager mWindowManager;
    public boolean notiFullPopupBlocked;
    public boolean notiShowBlocked;
    public SubscreenNotificationDetail popupViewNotiTemplate;
    public boolean popupViewShowing;
    public SubscreenDeviceModelParent$initTimeoutRunnable$2 popupViewTimeoutRunnable;
    public SubscreenNotificationDetail presentationNotiTemplate;
    public boolean presentationShowing;
    public SubscreenDeviceModelParent$initTimeoutRunnable$1 presentationTimeoutRunnable;
    public int bModeUserId = -1;
    public int currentUserId = ActivityManager.getCurrentUser();
    public final Handler mHandler = new Handler();
    public final LinkedHashMap mFullScreenIntentEntries = new LinkedHashMap();
    public final LinkedHashMap mMainListArrayHashMap = new LinkedHashMap();
    public final LinkedHashMap mMainListUpdateItemHashMap = new LinkedHashMap();
    public final LinkedHashMap mMainListAddEntryHashMap = new LinkedHashMap();
    public final LinkedHashMap mMainListRemoveEntryHashMap = new LinkedHashMap();
    public final GroupMembershipManager mGroupMembershipManager = (GroupMembershipManager) Dependency.sDependency.getDependencyInner(GroupMembershipManager.class);
    public final SubscreenDeviceModelParent$marqueeStartRunnable$1 marqueeStartRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$marqueeStartRunnable$1
        @Override // java.lang.Runnable
        public final void run() {
            TextView textView;
            SubscreenNotificationDetail subscreenNotificationDetail = this.this$0.popupViewNotiTemplate;
            if (subscreenNotificationDetail == null || (textView = subscreenNotificationDetail.mMarqueeText) == null) {
                return;
            }
            textView.setSelected(true);
        }
    };
    public final SubscreenDeviceModelParent$topPopupAnimationListener$1 topPopupAnimationListener = new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$topPopupAnimationListener$1
        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            SubscreenDeviceModelParent subscreenDeviceModelParent = this.this$0;
            Log.d("S.S.N.", " topPopupAnimationListener - onAnimationEnd , mNotiPopupView : " + subscreenDeviceModelParent.mNotiPopupView + ", popupViewNotiTemplate : " + subscreenDeviceModelParent.popupViewNotiTemplate);
            View view = this.this$0.mNotiPopupView;
            if (view != null) {
                view.setVisibility(4);
                SubscreenDeviceModelParent subscreenDeviceModelParent2 = this.this$0;
                WindowManager windowManager = subscreenDeviceModelParent2.mWindowManager;
                if (windowManager != null) {
                    windowManager.removeViewImmediate(subscreenDeviceModelParent2.mNotiPopupView);
                }
            }
            SubscreenDeviceModelParent subscreenDeviceModelParent3 = this.this$0;
            subscreenDeviceModelParent3.mNotiPopupView = null;
            subscreenDeviceModelParent3.popupViewNotiTemplate = null;
            subscreenDeviceModelParent3.currentPopupViewEntry = null;
            subscreenDeviceModelParent3.popupViewShowing = false;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
        }
    };
    public final ArraySet showPopupEntryKeySet = new ArraySet();
    public final SubscreenDeviceModelParent$mWakefulnessObserver$1 mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$mWakefulnessObserver$1
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedGoingToSleep() {
            Log.d("S.S.N.", " onFinishedGoingToSleep");
            SubscreenDeviceModelParent subscreenDeviceModelParent = this.this$0;
            if (subscreenDeviceModelParent.popupViewShowing) {
                Log.d("S.S.N.", " onFinishedGoingToSleep and HUN is showing, so dismiss it");
                subscreenDeviceModelParent.dismissImmediately(2);
            }
            if (subscreenDeviceModelParent.presentationShowing && subscreenDeviceModelParent.isDismissiblePopup()) {
                Log.d("S.S.N.", " onFinishedGoingToSleep and PRESENTATION is showing, so dismiss it");
                subscreenDeviceModelParent.dismissImmediately(1);
            }
            if (subscreenDeviceModelParent.showPopupEntryKeySet.size() > 0) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(subscreenDeviceModelParent.showPopupEntryKeySet.size(), " onFinishedGoingToSleep - clear popup key set : ", "S.S.N.");
                subscreenDeviceModelParent.showPopupEntryKeySet.clear();
            }
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedGoingToSleep() {
            SubscreenSubRoomNotification subscreenSubRoomNotification;
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter;
            SubscreenNotificationInfo subscreenNotificationInfo;
            ExpandableNotificationRow expandableNotificationRow;
            SubscreenDeviceModelParent subscreenDeviceModelParent = this.this$0;
            if (subscreenDeviceModelParent.isSubScreen()) {
                Log.d("S.S.N.", " onStartedGoingToSleep");
                if (subscreenDeviceModelParent.isShownDetail()) {
                    subscreenDeviceModelParent.hideDetailNotification();
                    SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = subscreenDeviceModelParent.mController.replyActivity;
                    if (subscreenNotificationReplyActivity != null) {
                        subscreenNotificationReplyActivity.finish();
                    }
                }
                if (!subscreenDeviceModelParent.isShownGroup() || (subscreenSubRoomNotification = subscreenDeviceModelParent.mSubRoomNotification) == null || (subscreenNotificationGroupAdapter = subscreenSubRoomNotification.mNotificationGroupAdapter) == null || (subscreenNotificationInfo = subscreenNotificationGroupAdapter.mSummaryInfo) == null || (expandableNotificationRow = subscreenNotificationInfo.mRow) == null || !expandableNotificationRow.needsRedaction()) {
                    return;
                }
                subscreenDeviceModelParent.hideGroupNotification();
            }
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedWakingUp() {
            int i = ((WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class)).mLastWakeReason;
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, " onStartedWakingUp - why: ", "S.S.N.");
            if (i == 6 || i == 15 || i == 113) {
                SubscreenDeviceModelParent subscreenDeviceModelParent = this.this$0;
                if (subscreenDeviceModelParent.mPresentation == null || subscreenDeviceModelParent.useTopPresentation()) {
                    return;
                }
                subscreenDeviceModelParent.updateContentScroll();
                SubscreenNotificationDetail subscreenNotificationDetail = subscreenDeviceModelParent.presentationNotiTemplate;
                if (subscreenNotificationDetail != null) {
                    subscreenNotificationDetail.performClick();
                }
            }
        }
    };
    public final SubscreenDeviceModelParent$mUpdateMonitorCallback$1 mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$mUpdateMonitorCallback$1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onUserSwitchComplete : ", "S.S.N.");
            SubscreenDeviceModelParent subscreenDeviceModelParent = this.this$0;
            subscreenDeviceModelParent.updateNotiShowBlocked();
            subscreenDeviceModelParent.currentUserId = i;
            subscreenDeviceModelParent.updateBModeStatus();
            subscreenDeviceModelParent.updateSamsungAccount();
        }
    };
    public final SubscreenDeviceModelParent$drawWalkLockReleaseRunnable$1 drawWalkLockReleaseRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$drawWalkLockReleaseRunnable$1
        @Override // java.lang.Runnable
        public final void run() {
            WakeLock wakeLock;
            Log.d("S.S.N.", " drawWalkLockReleaseRunnable - releaseInflationWakeLock");
            NotificationEntry notificationEntry = this.this$0.currentPresentationEntry;
            if (notificationEntry != null && (wakeLock = notificationEntry.mInflationWakeLock) != null) {
                wakeLock.release(notificationEntry.mKey);
                notificationEntry.mInflationWakeLock = null;
            }
            this.this$0.currentPresentationEntry = null;
        }
    };
    public final SubscreenDeviceModelParent$updateSubscreenListRunnable$1 updateSubscreenListRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$updateSubscreenListRunnable$1
        @Override // java.lang.Runnable
        public final void run() {
            SubscreenDeviceModelParent subscreenDeviceModelParent = this.this$0;
            int i = SubscreenDeviceModelParent.$r8$clinit;
            subscreenDeviceModelParent.clearMainList();
            subscreenDeviceModelParent.mController.notifPipeline.mShadeListBuilder.buildList();
        }
    };

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class MainListHashMapItem {
        public NotificationEntry mEntry;
        public SubscreenNotificationInfo mInfo;
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v12, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$marqueeStartRunnable$1] */
    /* JADX WARN: Type inference failed for: r1v13, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$topPopupAnimationListener$1] */
    /* JADX WARN: Type inference failed for: r1v15, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$mWakefulnessObserver$1] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$mUpdateMonitorCallback$1] */
    /* JADX WARN: Type inference failed for: r1v17, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$drawWalkLockReleaseRunnable$1] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$updateSubscreenListRunnable$1] */
    public SubscreenDeviceModelParent(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, UserContextProvider userContextProvider, SubscreenNotificationController subscreenNotificationController, Lazy lazy, CommonNotifCollection commonNotifCollection, LogBuffer logBuffer, NotificationInterruptStateProvider notificationInterruptStateProvider, Lazy lazy2, Lazy lazy3, NotificationVisibilityProvider notificationVisibilityProvider, BindEventManager bindEventManager, NotificationController notificationController, UserManager userManager, ConversationNotificationManager conversationNotificationManager) {
        this.mContext = context;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mSettingsHelper = settingsHelper;
        this.mUserContextProvider = userContextProvider;
        this.mController = subscreenNotificationController;
        this.mSubScreenManagerLazy = lazy;
        this.mNotifCollection = commonNotifCollection;
        this.mBuffer = logBuffer;
        this.mInterruptionStateProvider = notificationInterruptStateProvider;
        this.mUserManager = userManager;
    }

    public static boolean isAutoGrouping(NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
        return StringsKt__StringsKt.contains(notificationEntry.mSbn.getGroupKey(), "Aggregate", false) && notificationEntry.mSbn.getPackageName().equals(notificationEntry2.mSbn.getPackageName()) && notificationEntry.mSbn.getUserId() == notificationEntry2.mSbn.getUserId();
    }

    public static void putMainListArrayHashMap$default(SubscreenDeviceModelParent subscreenDeviceModelParent, NotificationEntry notificationEntry) {
        SubscreenNotificationInfoManager subscreenNotificationInfoManager;
        SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelParent.mSubRoomNotification;
        subscreenDeviceModelParent.putMainListArrayHashMap(notificationEntry, (subscreenSubRoomNotification == null || (subscreenNotificationInfoManager = subscreenSubRoomNotification.mNotificationInfoManager) == null) ? null : subscreenNotificationInfoManager.createItemsData(notificationEntry.row));
    }

    public static void updateTwoPhoneIcon(ImageView imageView, SubscreenNotificationInfo subscreenNotificationInfo) {
        if (imageView == null || subscreenNotificationInfo == null) {
            return;
        }
        if (subscreenNotificationInfo.mNeedsOnePhoneIcon) {
            Log.d("S.S.N.", "set sim_1 icon: " + subscreenNotificationInfo.mKey);
            imageView.setImageResource(R.drawable.subscreen_stat_notify_multi_sim_1);
            imageView.setVisibility(0);
            return;
        }
        if (!subscreenNotificationInfo.mNeedsTwoPhoneIcon) {
            imageView.setVisibility(8);
            return;
        }
        Log.d("S.S.N.", "set sim_2 icon: " + subscreenNotificationInfo.mKey);
        imageView.setImageResource(R.drawable.subscreen_stat_notify_multi_sim_2);
        imageView.setVisibility(0);
    }

    public final void againPipLine() {
        boolean z = this.isHomeKeyClicked;
        SubscreenNotificationController subscreenNotificationController = this.mController;
        if (z) {
            this.isHomeKeyClicked = false;
            if (subscreenNotificationController.panelExpanded) {
                Handler handler = this.mHandler;
                SubscreenDeviceModelParent$updateSubscreenListRunnable$1 subscreenDeviceModelParent$updateSubscreenListRunnable$1 = this.updateSubscreenListRunnable;
                handler.removeCallbacks(subscreenDeviceModelParent$updateSubscreenListRunnable$1);
                handler.postDelayed(subscreenDeviceModelParent$updateSubscreenListRunnable$1, 100);
                return;
            }
        }
        clearMainList();
        subscreenNotificationController.notifPipeline.mShadeListBuilder.buildList();
    }

    public void bindImageBitmap(ImageView imageView, Bitmap bitmap) {
        if (bitmap == null || imageView == null) {
            return;
        }
        imageView.setImageBitmap(bitmap);
    }

    public final boolean checkBubbleLastHistoryReply(NotificationEntry notificationEntry) {
        SubscreenNotificationInfoManager subscreenNotificationInfoManager;
        if (this.mBubbleReplyEntry == null || !notificationEntry.mRanking.canBubble()) {
            return false;
        }
        NotificationEntry notificationEntry2 = this.mBubbleReplyEntry;
        if (!notificationEntry.mKey.equals(notificationEntry2 != null ? notificationEntry2.mKey : null)) {
            return false;
        }
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        SubscreenNotificationInfo subscreenNotificationInfoCreateItemsData = (subscreenSubRoomNotification == null || (subscreenNotificationInfoManager = subscreenSubRoomNotification.mNotificationInfoManager) == null) ? null : subscreenNotificationInfoManager.createItemsData(notificationEntry.row);
        ArrayList arrayList = subscreenNotificationInfoCreateItemsData != null ? subscreenNotificationInfoCreateItemsData.mMessageingStyleInfoArray : null;
        arrayList.getClass();
        return !this.mController.useHistory(notificationEntry) || (arrayList.size() > 0 && ((SubscreenNotificationInfo.MessagingStyleInfo) AlertController$$ExternalSyntheticOutline0.m(1, arrayList)).mIsReply);
    }

    public final boolean checkEntryConditionsForNonAddition(NotificationEntry notificationEntry, String str) {
        boolean z;
        if (checkBubbleLastHistoryReply(notificationEntry)) {
            MediaSessions$H$$ExternalSyntheticOutline0.m("updateMainListArray ", str, " skip - mBubbleReplyEntry: ", notificationEntry.mKey, "S.S.N.");
            if (str.equals("add Item")) {
                notifyListAdapterItemRemoved(notificationEntry);
            }
            this.bubbleReply = 1;
            z = true;
        } else {
            z = false;
        }
        boolean zIsBubbleNotificationSuppressed$1 = isBubbleNotificationSuppressed$1(notificationEntry);
        String str2 = notificationEntry.mKey;
        if (zIsBubbleNotificationSuppressed$1) {
            MediaSessions$H$$ExternalSyntheticOutline0.m("updateMainListArray ", str, " skip - isBubbleNotificationSuppressed: ", str2, "S.S.N.");
            z = true;
        }
        if (this.mIsNotificationRemoved) {
            NotificationEntry notificationEntry2 = (NotificationEntry) this.mMainListRemoveEntryHashMap.get(str2);
            StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("updateMainListArray ", str, " skip - mIsNotificationRemoved - key :", str2, " ,entry :");
            sbM.append(notificationEntry2);
            Log.d("S.S.N.", sbM.toString());
            if (notificationEntry2 != null) {
                MediaSessions$H$$ExternalSyntheticOutline0.m("updateMainListArray ", str, " skip - mIsNotificationRemoved: ", str2, "S.S.N.");
                this.mMainListRemoveEntryHashMap.remove(str2);
                return true;
            }
        }
        return z;
    }

    public final void clearMainList() {
        Log.d("S.S.N.", " clearMainList");
        this.mMainListArrayHashMap.clear();
        this.mMainListUpdateItemHashMap.clear();
        this.mMainListAddEntryHashMap.clear();
        if (this.mIsNotificationRemoved) {
            return;
        }
        this.mMainListRemoveEntryHashMap.clear();
    }

    public boolean clickLiveNotificationActionButtonForActivity(String str, PendingIntent pendingIntent) {
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void detailClicked(final NotificationEntry notificationEntry) {
        SubscreenRecyclerView subscreenRecyclerView;
        View childAt;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        SubscreenNotificationInfo subscreenNotificationInfo;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2;
        if (skipDetailClicked(notificationEntry)) {
            return;
        }
        if (notificationEntry != null) {
            boolean zIsShownDetail = isShownDetail();
            Lazy lazy = this.mSubScreenManagerLazy;
            if (zIsShownDetail) {
                SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
                if (((subscreenSubRoomNotification == null || (subscreenNotificationDetailAdapter2 = subscreenSubRoomNotification.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter2.mSelectNotificationInfo) != null) {
                    if (notificationEntry.mKey.equals((subscreenSubRoomNotification == null || (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) == null || (subscreenNotificationInfo = subscreenNotificationDetailAdapter.mSelectNotificationInfo) == null) ? null : subscreenNotificationInfo.mKey)) {
                        SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
                        if (subscreenSubRoomNotification2 != null) {
                            subscreenSubRoomNotification2.notifyNotificationSubRoomRequest();
                        }
                        ((SubScreenManager) lazy.get()).startSubHomeActivity();
                        SubscreenSubRoomNotification subscreenSubRoomNotification3 = this.mSubRoomNotification;
                        if (subscreenSubRoomNotification3 != null && (subscreenRecyclerView = subscreenSubRoomNotification3.mNotificationRecyclerView) != null && (childAt = subscreenRecyclerView.getChildAt(0)) != null) {
                            moveDetailAdapterContentScroll(childAt, false, false, true);
                        }
                    }
                }
            } else {
                SubscreenSubRoomNotification subscreenSubRoomNotification4 = this.mSubRoomNotification;
                SubscreenNotificationInfo subscreenNotificationInfoCreateItemsData = subscreenSubRoomNotification4 != null ? subscreenSubRoomNotification4.mNotificationInfoManager.createItemsData(notificationEntry.row) : null;
                SubscreenSubRoomNotification subscreenSubRoomNotification5 = this.mSubRoomNotification;
                if (subscreenSubRoomNotification5 != null) {
                    subscreenSubRoomNotification5.notifyNotificationSubRoomRequest();
                }
                ((SubScreenManager) lazy.get()).startSubHomeActivity();
                SubscreenSubRoomNotification subscreenSubRoomNotification6 = this.mSubRoomNotification;
                if (subscreenSubRoomNotification6 != null) {
                    subscreenSubRoomNotification6.showDetailNotification(subscreenNotificationInfoCreateItemsData);
                }
            }
        }
        if (isCoverBriefAllowed(notificationEntry)) {
            return;
        }
        Handler handler = this.mHandler;
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent.detailClicked.2
            @Override // java.lang.Runnable
            public final void run() {
                SubscreenDeviceModelParent.this.dismissImmediately(notificationEntry);
            }
        };
        String str = notificationEntry != null ? notificationEntry.mKey : null;
        NotificationEntry notificationEntry2 = this.currentPresentationEntry;
        handler.postDelayed(runnable, StringsKt__StringsJVMKt.equals(str, notificationEntry2 != null ? notificationEntry2.mKey : null, false) ? 300L : 0L);
    }

    public final void dismissImmediately(int i) {
        View view;
        Animator popUpViewDismissAnimator;
        boolean z = this.popupViewShowing;
        boolean z2 = this.presentationShowing;
        View view2 = this.mNotiPopupView;
        SubscreenNotificationPresentation subscreenNotificationPresentation = this.mPresentation;
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m(" DISMISS IMMEDIATELY(popupType) - popupViewShowing : ", ", presentationShowing : ", ", mNotiPopupView : ", z, z2);
        sbM.append(view2);
        sbM.append(", mPresentation : ");
        sbM.append(subscreenNotificationPresentation);
        sbM.append(", popupType : ");
        RecyclerView$$ExternalSyntheticOutline0.m(i, "S.S.N.", sbM);
        Handler handler = this.mHandler;
        handler.removeCallbacks(this.marqueeStartRunnable);
        if (i == 2) {
            SubscreenDeviceModelParent$initTimeoutRunnable$2 subscreenDeviceModelParent$initTimeoutRunnable$2 = this.popupViewTimeoutRunnable;
            if (subscreenDeviceModelParent$initTimeoutRunnable$2 == null) {
                subscreenDeviceModelParent$initTimeoutRunnable$2 = null;
            }
            handler.removeCallbacks(subscreenDeviceModelParent$initTimeoutRunnable$2);
            if (this.popupViewShowing && (view = this.mNotiPopupView) != null && (popUpViewDismissAnimator = getPopUpViewDismissAnimator(view)) != null) {
                popUpViewDismissAnimator.start();
            }
            if (this.mPresentation == null) {
                updateWakeLock(false, false);
            }
        }
        if (i == 1) {
            this.mIsRemoving = false;
            SubscreenDeviceModelParent$initTimeoutRunnable$1 subscreenDeviceModelParent$initTimeoutRunnable$1 = this.presentationTimeoutRunnable;
            handler.removeCallbacks(subscreenDeviceModelParent$initTimeoutRunnable$1 != null ? subscreenDeviceModelParent$initTimeoutRunnable$1 : null);
            SubscreenNotificationPresentation subscreenNotificationPresentation2 = this.mPresentation;
            if (subscreenNotificationPresentation2 != null) {
                subscreenNotificationPresentation2.dismiss();
            }
        }
        this.mNotiPopupType = 0;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    public void foldStateChanged(boolean z) {
        NotificationActivityStarter notificationActivityStarter;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager2;
        NotificationActivityStarter notificationActivityStarter2;
        Log.d("S.S.N.", " FOLD STATE parent- ".concat(z ? "FOLD " : "UNFOLD "));
        if (z) {
            if (!NotiRune.NOTI_SUBSCREEN_PENDING_CALL_FULLSCRREN_INTENT || (notificationActivityStarter = this.mNotificationActivityStarter) == null) {
                return;
            }
            ((StatusBarNotificationActivityStarter) notificationActivityStarter).mShouldSkipFullScreenIntent = true;
            return;
        }
        int i = 0;
        if (NotiRune.NOTI_SUBSCREEN_PENDING_CALL_FULLSCRREN_INTENT && (notificationActivityStarter2 = this.mNotificationActivityStarter) != null) {
            StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = (StatusBarNotificationActivityStarter) notificationActivityStarter2;
            statusBarNotificationActivityStarter.mShouldSkipFullScreenIntent = false;
            NotificationEntry notificationEntry = statusBarNotificationActivityStarter.mPendingFullscreenEntry;
            if (notificationEntry != null && notificationEntry.mSbn.getNotification().fullScreenIntent != null) {
                statusBarNotificationActivityStarter.launchFullScreenIntent(statusBarNotificationActivityStarter.mPendingFullscreenEntry);
                statusBarNotificationActivityStarter.mPendingFullscreenEntry = null;
            }
        }
        if (this.mFullScreenIntentEntries.size() > 0) {
            Log.d("S.S.N.", " foldStateChanged - clear mFullScreenIntentEntries");
            this.mFullScreenIntentEntries.clear();
        }
        SubscreenNotificationPresentation subscreenNotificationPresentation = this.mPresentation;
        Handler handler = this.mHandler;
        if (subscreenNotificationPresentation != null) {
            Log.d("S.S.N.", " foldStateChanged - dismiss Presentation");
            SubscreenDeviceModelParent$initTimeoutRunnable$1 subscreenDeviceModelParent$initTimeoutRunnable$1 = this.presentationTimeoutRunnable;
            if (subscreenDeviceModelParent$initTimeoutRunnable$1 == null) {
                subscreenDeviceModelParent$initTimeoutRunnable$1 = null;
            }
            handler.removeCallbacks(subscreenDeviceModelParent$initTimeoutRunnable$1);
            SubscreenNotificationPresentation subscreenNotificationPresentation2 = this.mPresentation;
            if (subscreenNotificationPresentation2 != null) {
                subscreenNotificationPresentation2.dismiss();
            }
        }
        if (this.mNotiPopupView != null) {
            Log.d("S.S.N.", " foldStateChanged - remove top popup window");
            WindowManager windowManager = this.mWindowManager;
            if (windowManager != null) {
                windowManager.removeViewImmediate(this.mNotiPopupView);
            }
            this.mNotiPopupView = null;
            this.currentPopupViewEntry = null;
            SubscreenDeviceModelParent$initTimeoutRunnable$2 subscreenDeviceModelParent$initTimeoutRunnable$2 = this.popupViewTimeoutRunnable;
            if (subscreenDeviceModelParent$initTimeoutRunnable$2 == null) {
                subscreenDeviceModelParent$initTimeoutRunnable$2 = null;
            }
            handler.removeCallbacks(subscreenDeviceModelParent$initTimeoutRunnable$2);
            this.popupViewNotiTemplate = null;
            this.popupViewShowing = false;
        }
        if (this.showPopupEntryKeySet.size() > 0) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(this.showPopupEntryKeySet.size(), " foldStateChanged - clear popup key set : ", "S.S.N.");
            this.showPopupEntryKeySet.clear();
        }
        clearMainList();
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        String str = "SubscreenNotificationInfoManager";
        if (subscreenSubRoomNotification != null && (subscreenNotificationInfoManager2 = subscreenSubRoomNotification.mNotificationInfoManager) != null) {
            Log.d("SubscreenNotificationInfoManager", " clearArrayListAll");
            subscreenNotificationInfoManager2.clearAllRecyclerViewItem();
            subscreenNotificationInfoManager2.mGroupDataArray.clear();
            SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.clear();
            subscreenNotificationInfoManager2.mNotificationListAdapter.notifyDataSetChanged();
            subscreenNotificationInfoManager2.mNotificationGroupAdapter.notifyDataSetChanged();
            subscreenNotificationInfoManager2.mNotificationDetailAdapter.notifyDataSetChanged();
        }
        SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
        if (subscreenSubRoomNotification2 != null && (subscreenNotificationInfoManager = subscreenSubRoomNotification2.mNotificationInfoManager) != null) {
            int subscreenNotificationInfoListSize = SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize();
            while (true) {
                if (i >= subscreenNotificationInfoListSize) {
                    break;
                }
                NotificationEntry notificationEntry2 = ((SubscreenNotificationInfo) SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.get(i)).mRow.mEntry;
                if (notificationEntry2.mRanking.canBubble()) {
                    NotifCollection notifCollection = subscreenNotificationInfoManager.mNotifCollection;
                    notifCollection.getClass();
                    StatusBarNotification statusBarNotification = notificationEntry2.mSbn;
                    int i2 = NotifCollection.$r8$clinit;
                    notifCollection.mMainHandler.post(new NotifCollection$$ExternalSyntheticLambda9(notifCollection, statusBarNotification, str, "Update the bubble notification in the subscreen state"));
                    break;
                }
                i++;
            }
        }
        this.mBubbleReplyEntry = null;
    }

    public int getDetailAdapterAutoScrollCurrentPositionByReceive(View view) {
        return 0;
    }

    public int getDetailAdapterContentViewResource() {
        return -1;
    }

    public View getDetailAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        return null;
    }

    public int getDetailAdapterReplyWordResource() {
        return -1;
    }

    public ImageView.ScaleType getDetailContentImageScaleType() {
        return ImageView.ScaleType.FIT_CENTER;
    }

    public int getDispalyHeight() {
        return -1;
    }

    public int getFullPopupWindowType() {
        return 2026;
    }

    public View getGroupAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        return null;
    }

    public int getLayoutInDisplayCutoutMode() {
        return 0;
    }

    public int getListAdapterAddItemCnt() {
        return 1;
    }

    public int getListAdapterGroupItemResource() {
        return -1;
    }

    public View getListAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        return null;
    }

    public final SettingsHelper getMSettingsHelper() {
        return this.mSettingsHelper;
    }

    public int getMainHeaderViewHeight() {
        return 0;
    }

    public Animator getPopUpViewDismissAnimator(View view) {
        return null;
    }

    public Animator getPopUpViewShowAnimator(View view) {
        return null;
    }

    public View getReplyButtonView() {
        return null;
    }

    public int getSelectedReplyBGColor() {
        return -1;
    }

    public boolean getSubIconVisible(boolean z, boolean z2) {
        return true;
    }

    public final SubscreenSubRoomNotification getSubRoomNotification() {
        if (this.mSubRoomNotification == null) {
            Context context = this.mDisplayContext;
            if (context == null) {
                context = null;
            }
            this.mSubRoomNotification = SubscreenSubRoomNotification.getInstance(context);
        }
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        subscreenSubRoomNotification.getClass();
        return subscreenSubRoomNotification;
    }

    public int getSubscreenNotificationTipResource() {
        return -1;
    }

    public final String getTopActivityName() {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        ComponentName componentName;
        try {
            ActivityManager activityManager = this.activityManager;
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager != null ? activityManager.getRunningTasks(1) : null;
            if (runningTasks != null && (runningTaskInfo = runningTasks.get(0)) != null && (componentName = runningTaskInfo.topActivity) != null) {
                String className = componentName.getClassName();
                if (className != null) {
                    return className;
                }
            }
            return "";
        } catch (SecurityException unused) {
            Log.e("S.S.N.", "SecurityException while get top activity");
            return "";
        }
    }

    public WindowManager.LayoutParams getTopPopupLp() {
        return null;
    }

    public Animator getTopPresentationDismissAnimator(View view) {
        return null;
    }

    public Intent getWritingAssistFTUIntent() {
        return null;
    }

    public final void hideDetailNotification() {
        hideDetailNotificationAnimated(300, true);
    }

    public final void hideDetailNotificationAnimated(int i, boolean z) {
        Animator animatorAlphaAnimatedMainView;
        if (z) {
            this.mIsUpdatedAllMainList = true;
        }
        setIsReplySendButtonLoading();
        if (this.mMainViewAnimator == null) {
            Log.d("S.S.N.", "hideDetailNotificationAnimated start animtion");
            final SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
            if (subscreenSubRoomNotification != null) {
                animatorAlphaAnimatedMainView = subscreenSubRoomNotification.mNotificationAnimatorManager.alphaAnimatedMainView(i, subscreenSubRoomNotification.mSubscreenMainLayout, new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$hideDetailNotificationAnimated$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION) {
                            this.this$0.mController.hideDetailNotif();
                        }
                        subscreenSubRoomNotification.hideDetailNotification();
                    }
                });
            } else {
                animatorAlphaAnimatedMainView = null;
            }
            this.mMainViewAnimator = animatorAlphaAnimatedMainView;
        } else {
            Log.d("S.S.N.", "hideDetailNotificationAnimated already animtion");
            if (NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION) {
                this.mController.hideDetailNotif();
            }
            SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
            if (subscreenSubRoomNotification2 != null) {
                subscreenSubRoomNotification2.hideDetailNotification();
            }
        }
        releaseSmartReply();
    }

    public final void hideGroupNotification() {
        Animator animatorAlphaAnimatedMainView;
        this.mIsUpdatedAllMainList = true;
        if (this.mMainViewAnimator != null) {
            Log.d("S.S.N.", "hideGroupNotificationAnimated already animtion");
            SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
            if (subscreenSubRoomNotification != null) {
                subscreenSubRoomNotification.hideGroupNotification();
                return;
            }
            return;
        }
        Log.d("S.S.N.", "hideGroupNotificationAnimated start animtion");
        final SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
        if (subscreenSubRoomNotification2 != null) {
            subscreenSubRoomNotification2.mIsShownGroup = false;
            animatorAlphaAnimatedMainView = subscreenSubRoomNotification2.mNotificationAnimatorManager.alphaAnimatedMainView(300, subscreenSubRoomNotification2.mSubscreenMainLayout, new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$hideGroupNotificationAnimated$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    subscreenSubRoomNotification2.hideGroupNotification();
                }
            });
        } else {
            animatorAlphaAnimatedMainView = null;
        }
        this.mMainViewAnimator = animatorAlphaAnimatedMainView;
    }

    public ImageView initDetailAdapterBackButton(View view) {
        return null;
    }

    public void initDetailAdapterItemViewHolder(Context context, final SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, final SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        itemViewHolder.mAdapter = subscreenNotificationDetailAdapter;
        itemViewHolder.mOpenAppButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent.initDetailAdapterItemViewHolder.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscreenNotificationInfo subscreenNotificationInfo = itemViewHolder.mInfo;
                String str = subscreenNotificationInfo.mKey;
                String str2 = subscreenNotificationInfo.mPkg;
                String str3 = subscreenNotificationInfo.mAppName;
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Click BodyLayout Key: ", str, ", mInfo.getPkg()", str2, ", mInfo.getAppName() ");
                sbM.append(str3);
                Log.e("SubscreenNotificationDetailAdapter", sbM.toString());
                subscreenNotificationDetailAdapter.getClass();
                SubscreenParentDetailItemViewHolder subscreenParentDetailItemViewHolder = itemViewHolder;
                subscreenParentDetailItemViewHolder.startWaitState(subscreenNotificationDetailAdapter, subscreenParentDetailItemViewHolder);
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_OPEN_IN_MAIN_SCREEN);
            }
        });
    }

    public void initDisplay() {
        Display[] displays = ((DisplayManager) this.mContext.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        if (displays.length <= 1) {
            this.mDisplayContext = this.mContext;
            ListPopupWindow$$ExternalSyntheticOutline0.m(displays.length, "Parent - fail to get subDisplay, display list size is ", "S.S.N.");
            return;
        }
        Display display = displays[1];
        this.mSubDisplay = display;
        Context context = this.mContext;
        display.getClass();
        this.mDisplayContext = context.createDisplayContext(display);
    }

    public void initGroupAdapterHeaderViewHolder(Context context, View view, SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter, SubscreenNotificationGroupAdapter.HeaderViewHolder headerViewHolder) {
        headerViewHolder.mIcon = (ImageView) view.findViewById(R.id.subscreen_header_icon);
        headerViewHolder.mAppName = (TextView) view.findViewById(R.id.subscreen_header_app_name);
        headerViewHolder.mTwoPhoneIcon = (ImageView) view.findViewById(R.id.two_phone_icon);
        headerViewHolder.mSecureIcon = (ImageView) view.findViewById(R.id.secure_icon);
    }

    /* JADX WARN: Type inference failed for: r0v17, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$initTimeoutRunnable$1] */
    /* JADX WARN: Type inference failed for: r0v18, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$initTimeoutRunnable$2] */
    public void initialize() {
        this.activityManager = (ActivityManager) this.mContext.getSystemService("activity");
        initDisplay();
        this.mPowerManager = (PowerManager) this.mContext.getSystemService("power");
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        PowerManager powerManager = this.mPowerManager;
        this.mScreenOnwakelock = new SettableWakeLock(WakeLock.wrap(powerManager != null ? powerManager.newWakeLock(268435466, "SystemUI:SubscreenNotification") : null, null, 300000L), "S.S.N.:ScreenOn");
        ((WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class)).addObserver(this.mWakefulnessObserver);
        this.presentationTimeoutRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$initTimeoutRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                SubscreenDeviceModelParent subscreenDeviceModelParent = this.this$0;
                NotificationEntry notificationEntry = subscreenDeviceModelParent.currentPresentationEntry;
                String str = notificationEntry != null ? notificationEntry.mKey : null;
                boolean z = subscreenDeviceModelParent.presentationShowing;
                SubscreenNotificationPresentation subscreenNotificationPresentation = subscreenDeviceModelParent.mPresentation;
                StringBuilder sbM = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(" TIMEOUT Run Parent PRESENTATION - RELEASE DOZE STATE - TIMEOUT : ", str, " , presentationShowing : ", ", mPresentation : ", z);
                sbM.append(subscreenNotificationPresentation);
                Log.d("S.S.N.", sbM.toString());
                SubscreenDeviceModelParent subscreenDeviceModelParent2 = this.this$0;
                subscreenDeviceModelParent2.mIsRemoving = true;
                subscreenDeviceModelParent2.updateWakeLock(false, false);
                final SubscreenDeviceModelParent subscreenDeviceModelParent3 = this.this$0;
                subscreenDeviceModelParent3.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$initTimeoutRunnable$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SubscreenNotificationPresentation subscreenNotificationPresentation2;
                        SubscreenDeviceModelParent subscreenDeviceModelParent4 = subscreenDeviceModelParent3;
                        Log.d("S.S.N.", " DISMISS Run - isRemoving: " + subscreenDeviceModelParent4.mIsRemoving + ", presentation: " + subscreenDeviceModelParent4.mPresentation);
                        SubscreenDeviceModelParent subscreenDeviceModelParent5 = subscreenDeviceModelParent3;
                        if (!subscreenDeviceModelParent5.mIsRemoving || (subscreenNotificationPresentation2 = subscreenDeviceModelParent5.mPresentation) == null) {
                            return;
                        }
                        subscreenNotificationPresentation2.dismiss();
                        subscreenDeviceModelParent3.mIsRemoving = false;
                        Log.d("S.S.N.", "updateWakeLock false in timeoutRunnable");
                    }
                }, 300L);
                this.this$0.mNotiPopupType = 0;
            }
        };
        this.popupViewTimeoutRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$initTimeoutRunnable$2
            @Override // java.lang.Runnable
            public final void run() {
                View view;
                Animator popUpViewDismissAnimator;
                SubscreenDeviceModelParent subscreenDeviceModelParent = this.this$0;
                NotificationEntry notificationEntry = subscreenDeviceModelParent.currentPopupViewEntry;
                String str = notificationEntry != null ? notificationEntry.mKey : null;
                boolean z = subscreenDeviceModelParent.popupViewShowing;
                View view2 = subscreenDeviceModelParent.mNotiPopupView;
                StringBuilder sbM = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(" TIMEOUT Run Parent POPUPVIEW - RELEASE DOZE STATE - TIMEOUT : ", str, " ,popupViewShowing : ", " , mNotiPopupView : ", z);
                sbM.append(view2);
                Log.d("S.S.N.", sbM.toString());
                SubscreenDeviceModelParent subscreenDeviceModelParent2 = this.this$0;
                if (subscreenDeviceModelParent2.popupViewShowing && (view = subscreenDeviceModelParent2.mNotiPopupView) != null && (popUpViewDismissAnimator = subscreenDeviceModelParent2.getPopUpViewDismissAnimator(view)) != null) {
                    popUpViewDismissAnimator.start();
                }
                SubscreenDeviceModelParent subscreenDeviceModelParent3 = this.this$0;
                boolean z2 = subscreenDeviceModelParent3.presentationShowing;
                LinkedHashMap linkedHashMap = subscreenDeviceModelParent3.mFullScreenIntentEntries;
                NotificationEntry notificationEntry2 = subscreenDeviceModelParent3.currentPresentationEntry;
                subscreenDeviceModelParent3.updateWakeLock(z2, linkedHashMap.containsKey(notificationEntry2 != null ? notificationEntry2.mKey : null));
                this.this$0.mNotiPopupType = 0;
            }
        };
        updateNotiShowBlocked();
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        updateBModeStatus();
        loadOnDeviceMetaData();
        Context context2 = this.mDisplayContext;
        this.largeSubScreenCardWidth = this.mController.getSubScreenCardWidth(context2 != null ? context2 : null);
    }

    public final boolean isBubbleNotificationSuppressed$1(NotificationEntry notificationEntry) {
        if (!notificationEntry.mRanking.canBubble()) {
            return false;
        }
        SubscreenNotificationController subscreenNotificationController = this.mController;
        if (subscreenNotificationController.bubblesOptional.isPresent()) {
            return ((BubbleController.BubblesImpl) ((Bubbles) subscreenNotificationController.bubblesOptional.get())).isBubbleNotificationSuppressedFromShade(notificationEntry.mKey, notificationEntry.mSbn.getGroupKey());
        }
        return false;
    }

    public boolean isCoverBriefAllowed(NotificationEntry notificationEntry) {
        return false;
    }

    public boolean isDismissiblePopup() {
        return this.mFullScreenIntentEntries.isEmpty();
    }

    public boolean isKeyguardStats() {
        return true;
    }

    public boolean isKeyguardUsed() {
        return false;
    }

    public boolean isKnoxSecurity(NotificationEntry notificationEntry) {
        return true;
    }

    public boolean isLargeSubscreen() {
        return false;
    }

    public boolean isLaunchApp(NotificationEntry notificationEntry) {
        return false;
    }

    public boolean isMainHeader() {
        return false;
    }

    public boolean isNotShwonNotificationState(NotificationEntry notificationEntry) {
        return true;
    }

    public boolean isOneUI7_0() {
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isProper(NotificationEntry notificationEntry, boolean z) {
        boolean z2;
        GroupEntry groupEntry;
        NotificationEntry notificationEntry2;
        NotificationInterruptStateProviderImpl notificationInterruptStateProviderImpl = (NotificationInterruptStateProviderImpl) this.mInterruptionStateProvider;
        boolean zCanHeadsUpCommonForFrontCoverScreen = notificationInterruptStateProviderImpl.canHeadsUpCommonForFrontCoverScreen(notificationEntry);
        String str = notificationEntry.mKey;
        if (zCanHeadsUpCommonForFrontCoverScreen) {
            if (!panelsEnabled()) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("No heads up: disabled panel : ", str, "S.S.N.");
            } else if (!this.mController.shouldFilterOut(notificationEntry)) {
                if (NotiRune.NOTI_SUBSCREEN_CHILD_TO_RECEIVE_PARENT_ALERT) {
                    if (notificationEntry.mSbn.getNotification().getGroupAlertBehavior() == 1 && (groupEntry = notificationEntry.mGroupEntry) != null && (notificationEntry2 = groupEntry.mLogicalSummary) != null && notificationInterruptStateProviderImpl.canHeadsUpCommonForFrontCoverScreen(notificationEntry2) && (notificationEntry2.mSbn.getNotification().getGroupAlertBehavior() == 1 || notificationEntry2.mSbn.getNotification().getGroupAlertBehavior() == 0)) {
                        Log.d("S.S.N.", "alertOverride : summary - " + notificationEntry2.mKey + " child - " + str);
                        if (NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION) {
                        }
                        z2 = true;
                    } else {
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("can't be alert because suppressAlertingDueToGrouping", str, "S.S.N.");
                        if (notificationEntry.mSbn.isGroup()) {
                            if (!z) {
                            }
                        }
                    }
                } else if (notificationEntry.mSbn.isGroup() || !notificationEntry.mSbn.getNotification().suppressAlertingDueToGrouping()) {
                    if (!z) {
                        Notification notification2 = notificationEntry.mSbn.getNotification();
                        if (!notificationEntry.interruption || (notification2.flags & 8) == 0) {
                            if (NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION || !notificationEntry.mIsGhost) {
                                z2 = true;
                            }
                        }
                    }
                }
            }
            z2 = false;
        } else {
            z2 = false;
        }
        boolean zIsGroupSummary = notificationEntry.mSbn.getNotification().isGroupSummary();
        boolean z3 = notificationEntry.mSbn.getNotification().visibility == -1;
        boolean z4 = (notificationEntry.mSbn.getNotification().semFlags & 256) != 0;
        boolean z5 = (notificationEntry.mSbn.getNotification().semFlags & 8) != 0;
        if (notificationEntry.mRanking.getImportance() >= 4) {
            String strM = !z2 ? ":!needsAlert:" : "";
            if (zIsGroupSummary) {
                strM = strM.concat(":!isNotSummary:");
            }
            if (z3) {
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, ":isSecret:");
            }
            if (z4) {
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, ":isDisabledByApp:");
            }
            if (z5) {
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, ":isDisableHeadsUP:");
            }
            if (!this.mIsFolded) {
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, ":!isFolded:");
            }
            if (!this.mIsCovered) {
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, ":!isCovered:");
            }
            if (NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION && notificationEntry.mIsGhost) {
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, ":isGhost:");
            }
            if (strM.length() != 0) {
                String strM2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, " - ", strM);
                LogLevel logLevel = LogLevel.DEBUG;
                SubscreenDeviceModelParent$$ExternalSyntheticLambda0 subscreenDeviceModelParent$$ExternalSyntheticLambda0 = new SubscreenDeviceModelParent$$ExternalSyntheticLambda0();
                LogBuffer logBuffer = this.mBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("S.S.N.", logLevel, subscreenDeviceModelParent$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).str1 = strM2;
                logBuffer.commit(logMessageObtain);
            }
        }
        return (z4 || !z2 || zIsGroupSummary || z3 || z5) ? false : true;
    }

    public boolean isRunOnCoverAvailable() {
        return true;
    }

    public boolean isSamsungAccountLoggedIn() {
        return false;
    }

    public Boolean isShowBouncer() {
        return Boolean.FALSE;
    }

    public boolean isShowNotificationAppIcon() {
        return true;
    }

    public final boolean isShownDetail() {
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if (subscreenSubRoomNotification == null) {
            return false;
        }
        return subscreenSubRoomNotification.mIsShownDetail;
    }

    public final boolean isShownGroup() {
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if (subscreenSubRoomNotification == null) {
            return false;
        }
        return subscreenSubRoomNotification.mIsShownGroup;
    }

    public boolean isSkipFullscreenIntentClicked(NotificationEntry notificationEntry) {
        return this.mFullScreenIntentEntries.get(notificationEntry.mKey) != null || this.mIsFullscreenFullPopupWindowClosing;
    }

    public final boolean isSubScreen() {
        return this.mIsFolded || this.mIsCovered;
    }

    public boolean isSupportRemoteView(NotificationEntry notificationEntry) {
        return false;
    }

    public boolean isZenModeViewType(int i) {
        return false;
    }

    public boolean launchFullscreenIntent(NotificationEntry notificationEntry) {
        return false;
    }

    public final void makeSubScreenNotification(NotificationEntry notificationEntry) {
        FrameLayout frameLayout;
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        boolean z = expandableNotificationRow != null && (expandableNotificationRow.mIsCustomNotification || expandableNotificationRow.mIsCustomBigNotification || expandableNotificationRow.mIsCustomHeadsUpNotification || expandableNotificationRow.mIsCustomPublicNotification);
        boolean zNeedsRedaction = expandableNotificationRow.needsRedaction();
        boolean z2 = notificationEntry.mSbn.getNotification().publicVersion != null;
        NotificationEntry notificationEntry2 = this.currentPopupViewEntry;
        String str = notificationEntry2 != null ? notificationEntry2.mKey : null;
        NotificationEntry notificationEntry3 = this.currentPresentationEntry;
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m(" MAKE DETAIL : exist Parent- PopupView: ", str, " Presentation: ", notificationEntry3 != null ? notificationEntry3.mKey : null, " entry key - ");
        String str2 = notificationEntry.mKey;
        sbM.append(str2);
        sbM.append(" isCustom - ");
        sbM.append(z);
        sbM.append(" needsRedaction - ");
        CarrierTextManager$$ExternalSyntheticOutline0.m(sbM, zNeedsRedaction, " hasPublic - ", z2, "S.S.N.");
        PowerManager powerManager = this.mPowerManager;
        Boolean boolValueOf = powerManager != null ? Boolean.valueOf(powerManager.isInteractive()) : null;
        boolValueOf.getClass();
        boolean zBooleanValue = boolValueOf.booleanValue();
        boolean z3 = this.mFullScreenIntentEntries.get(str2) != null;
        if (!zBooleanValue || z3) {
            this.mNotiPopupType = 1;
            this.currentPresentationEntry = notificationEntry;
        } else {
            this.mNotiPopupType = 2;
            this.currentPopupViewEntry = notificationEntry;
        }
        if (!z3) {
            if (this.notiShowBlocked) {
                Log.d("S.S.N.", " MAKE DETAIL : show notification is disabled. popup not showing");
                this.mNotiPopupType = 0;
                this.presentationNotiTemplate = null;
                this.popupViewNotiTemplate = null;
                this.currentPresentationEntry = null;
                this.currentPopupViewEntry = null;
                return;
            }
            if (this.notiFullPopupBlocked && this.mNotiPopupType == 1) {
                Log.d("S.S.N.", " MAKE DETAIL : full popup not showing");
                this.mNotiPopupType = 0;
                this.presentationNotiTemplate = null;
                this.popupViewNotiTemplate = null;
                this.currentPresentationEntry = null;
                this.currentPopupViewEntry = null;
                return;
            }
        }
        NotificationEntry notificationEntry4 = this.currentPopupViewEntry;
        String str3 = notificationEntry4 != null ? notificationEntry4.mKey : null;
        NotificationEntry notificationEntry5 = this.currentPresentationEntry;
        String str4 = notificationEntry5 != null ? notificationEntry5.mKey : null;
        int i = this.mNotiPopupType;
        StringBuilder sb = new StringBuilder(" MAKE DETAIL : isInteractive - ");
        sb.append(zBooleanValue);
        sb.append(" currentPopupViewEntry - ");
        sb.append(str3);
        sb.append(" currentPresentationEntry - ");
        sb.append(str4);
        sb.append(" notiPopupType - ");
        sb.append(i);
        sb.append(" fullScreenNoti - ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z3, "S.S.N.");
        if (this.popupViewShowing && this.mNotiPopupType == 2) {
            SubscreenNotificationDetail subscreenNotificationDetail = this.popupViewNotiTemplate;
            if (subscreenNotificationDetail != null && (frameLayout = subscreenNotificationDetail.mLayout) != null) {
                frameLayout.removeAllViews();
            }
            SubscreenNotificationDetail subscreenNotificationDetail2 = this.popupViewNotiTemplate;
            if (subscreenNotificationDetail2 != null) {
                subscreenNotificationDetail2.makeView(this.currentPopupViewEntry, true);
                return;
            }
            return;
        }
        if (this.mNotiPopupType == 2) {
            Context context = this.mDisplayContext;
            if (context == null) {
                context = null;
            }
            SubscreenNotificationDetail subscreenNotificationDetail3 = new SubscreenNotificationDetail(context);
            subscreenNotificationDetail3.makeView(this.currentPopupViewEntry, true);
            this.popupViewNotiTemplate = subscreenNotificationDetail3;
        }
        if (this.mNotiPopupType == 1) {
            Context context2 = this.mDisplayContext;
            SubscreenNotificationDetail subscreenNotificationDetail4 = new SubscreenNotificationDetail(context2 != null ? context2 : null);
            subscreenNotificationDetail4.makeView(this.currentPresentationEntry, false);
            this.presentationNotiTemplate = subscreenNotificationDetail4;
        }
    }

    public final int notifyGroupAdapterItemRemoved(NotificationEntry notificationEntry) {
        SubscreenNotificationInfoManager subscreenNotificationInfoManager;
        SubscreenSubRoomNotification subscreenSubRoomNotification;
        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager2;
        if (!isShownGroup()) {
            return -1;
        }
        SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
        Integer numValueOf = null;
        Integer numValueOf2 = (subscreenSubRoomNotification2 == null || (subscreenNotificationInfoManager2 = subscreenSubRoomNotification2.mNotificationInfoManager) == null) ? null : Integer.valueOf(subscreenNotificationInfoManager2.removeGroupDataArrayItem(notificationEntry));
        numValueOf2.getClass();
        int iIntValue = numValueOf2.intValue();
        if (iIntValue > -1 && (subscreenSubRoomNotification = this.mSubRoomNotification) != null && (subscreenNotificationGroupAdapter = subscreenSubRoomNotification.mNotificationGroupAdapter) != null) {
            subscreenNotificationGroupAdapter.notifyItemRemoved(iIntValue);
        }
        boolean zIsLaunchApp = isLaunchApp(notificationEntry);
        String str = notificationEntry.mKey;
        Animator animator = this.mMainViewAnimator;
        StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(iIntValue, "notifyGroupAdapterItemRemoved parent - Entry  : ", str, ", index :", ", isLaunchApp :");
        sbM890m.append(zIsLaunchApp);
        sbM890m.append(", mMainViewAnimator :");
        sbM890m.append(animator);
        Log.d("S.S.N.", sbM890m.toString());
        if (!isShownDetail()) {
            SubscreenSubRoomNotification subscreenSubRoomNotification3 = this.mSubRoomNotification;
            if (subscreenSubRoomNotification3 != null && (subscreenNotificationInfoManager = subscreenSubRoomNotification3.mNotificationInfoManager) != null) {
                numValueOf = Integer.valueOf(subscreenNotificationInfoManager.mGroupDataArray.size());
            }
            numValueOf.getClass();
            if (numValueOf.intValue() <= 1 && !notificationEntry.isInsignificant()) {
                hideGroupNotification();
            }
        }
        return iIntValue;
    }

    public final void notifyListAdapterItemChanged(int i) {
        SubscreenNotificationListAdapter subscreenNotificationListAdapter;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if (subscreenSubRoomNotification == null || (subscreenNotificationListAdapter = subscreenSubRoomNotification.mNotificationListAdapter) == null) {
            return;
        }
        subscreenNotificationListAdapter.notifyItemChanged(convertInfoIndexToAdapterPosition(i));
    }

    public final int notifyListAdapterItemRemoved(NotificationEntry notificationEntry) {
        SubscreenNotificationListAdapter subscreenNotificationListAdapter;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        Integer numValueOf = (subscreenSubRoomNotification == null || subscreenSubRoomNotification.mNotificationInfoManager == null) ? null : Integer.valueOf(SubscreenNotificationInfoManager.removeSubscreenNotificationInfoItem(notificationEntry));
        Log.d("S.S.N.", "notifyListAdapterItemRemoved parent - Entry  : " + notificationEntry.mKey + ", index :" + numValueOf);
        if (numValueOf == null || numValueOf.intValue() <= -1) {
            return -1;
        }
        int iIntValue = numValueOf.intValue();
        SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
        if (subscreenSubRoomNotification2 != null && (subscreenNotificationListAdapter = subscreenSubRoomNotification2.mNotificationListAdapter) != null) {
            subscreenNotificationListAdapter.notifyItemRemoved(convertInfoIndexToAdapterPosition(iIntValue));
        }
        return numValueOf.intValue();
    }

    public boolean panelsEnabled() {
        return true;
    }

    public final void putMainListArrayHashMap(NotificationEntry notificationEntry, SubscreenNotificationInfo subscreenNotificationInfo) {
        MainListHashMapItem mainListHashMapItem = new MainListHashMapItem();
        if (subscreenNotificationInfo != null) {
            mainListHashMapItem.mEntry = notificationEntry;
            mainListHashMapItem.mInfo = subscreenNotificationInfo;
        }
        this.mMainListArrayHashMap.put(notificationEntry.mKey, mainListHashMapItem);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00db A[Catch: SecurityException -> 0x00d7, TRY_LEAVE, TryCatch #0 {SecurityException -> 0x00d7, blocks: (B:29:0x00cb, B:33:0x00db), top: B:109:0x00cb }] */
    /* JADX WARN: Type inference failed for: r11v0 */
    /* JADX WARN: Type inference failed for: r11v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r11v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setContentViewItem(Context context, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        CharSequence charSequence;
        int detailAdapterContentViewResource = getDetailAdapterContentViewResource();
        SubscreenNotificationInfo subscreenNotificationInfo = itemViewHolder.mInfo;
        boolean z = subscreenNotificationInfo.mIsMessagingStyle;
        int i = R.id.detail_clock;
        int i2 = R.id.detail_content_image;
        int i3 = R.id.detail_content_text;
        ?? r11 = 0;
        if (!z) {
            View viewInflate = LayoutInflater.from(context).inflate(detailAdapterContentViewResource, (ViewGroup) itemViewHolder.mContentLayout, false);
            TextView textView = (TextView) viewInflate.findViewById(R.id.detail_content_text);
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.detail_content_image);
            DateTimeView dateTimeViewFindViewById = viewInflate.findViewById(R.id.detail_clock);
            SubscreenNotificationInfo subscreenNotificationInfo2 = itemViewHolder.mInfo;
            String str = subscreenNotificationInfo2.mBigText;
            if (str == null) {
                str = subscreenNotificationInfo2.mContent;
            }
            if (str != null && !StringsKt__StringsKt.isBlank(str)) {
                if (textView != null) {
                    textView.setText(str);
                }
                if (textView != null) {
                    textView.setVisibility(0);
                }
            } else if (textView != null) {
                textView.setVisibility(8);
            }
            if (itemViewHolder.mInfo.mBitmap != null) {
                if (imageView != null) {
                    imageView.setVisibility(0);
                }
                if (imageView != null) {
                    imageView.setScaleType(getDetailContentImageScaleType());
                }
                bindImageBitmap(imageView, itemViewHolder.mInfo.mBitmap);
            } else if (imageView != null) {
                imageView.setVisibility(8);
            }
            SubscreenNotificationInfo subscreenNotificationInfo3 = itemViewHolder.mInfo;
            if (subscreenNotificationInfo3.mWhen > 0 && subscreenNotificationInfo3.mShowWhen) {
                if (dateTimeViewFindViewById != null) {
                    dateTimeViewFindViewById.setVisibility(0);
                }
                if (dateTimeViewFindViewById != null) {
                    dateTimeViewFindViewById.setTime(itemViewHolder.mInfo.mWhen);
                }
            } else if (dateTimeViewFindViewById != null) {
                dateTimeViewFindViewById.setVisibility(8);
            }
            itemViewHolder.mContentLayout.addView(viewInflate);
            itemViewHolder.mBodyLayoutString = itemViewHolder.mBodyLayoutString + ((Object) (textView != null ? textView.getText() : null)) + ((Object) (dateTimeViewFindViewById != null ? dateTimeViewFindViewById.getText() : null));
            return;
        }
        ArrayList arrayList = subscreenNotificationInfo.mMessageingStyleInfoArray;
        int size = arrayList.size();
        int i4 = 0;
        while (i4 < size) {
            View viewInflate2 = LayoutInflater.from(context).inflate(detailAdapterContentViewResource, itemViewHolder.mContentLayout, (boolean) r11);
            TextView textView2 = (TextView) viewInflate2.findViewById(R.id.detail_sender_text);
            TextView textView3 = (TextView) viewInflate2.findViewById(i3);
            ImageView imageView2 = (ImageView) viewInflate2.findViewById(i2);
            DateTimeView dateTimeViewFindViewById2 = viewInflate2.findViewById(i);
            if (itemViewHolder.mInfo.mIsGroupConversation) {
                if (textView2 != 0) {
                    textView2.setVisibility(r11);
                }
                if (textView2 != 0) {
                    textView2.setText(((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i4)).mSender + " : ");
                }
            } else {
                String str2 = ((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i4)).mSender;
                String str3 = itemViewHolder.mPrevSender;
                if (str3 != null && !str3.equals(str2)) {
                    if (textView2 != 0) {
                        textView2.setVisibility(r11);
                    }
                    if (textView2 != 0) {
                        textView2.setText(((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i4)).mSender + " : ");
                    }
                } else if (textView2 != 0) {
                    textView2.setVisibility(8);
                }
                itemViewHolder.mPrevSender = str2;
            }
            if (textView3 != null) {
                textView3.setText(((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i4)).mContentText);
            }
            if (((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i4)).mUriImage == null) {
                charSequence = null;
                if (imageView2 != 0) {
                    imageView2.setVisibility(8);
                }
            } else if (imageView2 != 0) {
                try {
                    imageView2.setImageDrawable(((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i4)).mUriImage);
                    if (imageView2 != 0) {
                        imageView2.setVisibility(r11);
                    }
                    charSequence = null;
                } catch (SecurityException e) {
                    SubscreenNotificationInfo subscreenNotificationInfo4 = itemViewHolder.mInfo;
                    Log.w("SubscreenNotificationDetailAdapter", "SecurityException: " + e + "appName : " + subscreenNotificationInfo4.mAppName + "packageName : " + subscreenNotificationInfo4.mSbn.getPackageName());
                    charSequence = null;
                    if (imageView2 != 0) {
                        imageView2.setImageURI(null);
                    }
                    if (imageView2 != 0) {
                        imageView2.setVisibility(8);
                    }
                }
            } else {
                if (imageView2 != 0) {
                }
                charSequence = null;
            }
            long j = ((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i4)).mTimeStamp;
            if (j > 0 && itemViewHolder.mInfo.mShowWhen) {
                if (dateTimeViewFindViewById2 != null) {
                    dateTimeViewFindViewById2.setVisibility(0);
                }
                if (dateTimeViewFindViewById2 != null) {
                    dateTimeViewFindViewById2.setTime(j);
                }
            } else if (dateTimeViewFindViewById2 != null) {
                dateTimeViewFindViewById2.setVisibility(8);
            }
            itemViewHolder.mContentLayout.addView(viewInflate2);
            if (textView2 != 0 && textView2.getVisibility() == 0) {
                itemViewHolder.mBodyLayoutString = itemViewHolder.mBodyLayoutString + ((Object) textView2.getText());
            }
            itemViewHolder.mBodyLayoutString = itemViewHolder.mBodyLayoutString + ((Object) (textView3 != null ? textView3.getText() : charSequence)) + ((Object) (dateTimeViewFindViewById2 != null ? dateTimeViewFindViewById2.getText() : charSequence));
            i4++;
            i = R.id.detail_clock;
            i2 = R.id.detail_content_image;
            i3 = R.id.detail_content_text;
            r11 = 0;
        }
    }

    public void setDetailAdapterItemHolderButtonContentDescription(SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) throws Resources.NotFoundException {
        String string = subscreenNotificationDetailAdapter.mContext.getResources().getString(R.string.subscreen_detail_adapter_open_app_button_text);
        String string2 = subscreenNotificationDetailAdapter.mContext.getResources().getString(R.string.clear_all_text);
        itemViewHolder.mOpenAppButton.setContentDescription(string);
        itemViewHolder.mClearButton.setContentDescription(string2);
        itemViewHolder.mBodyLayout.setContentDescription(itemViewHolder.mBodyLayoutString);
    }

    public void setDetailAdapterTextHolderButtonContentDescription(SubscreenNotificationDetailAdapter.TextViewHolder textViewHolder, SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter) throws Resources.NotFoundException {
        textViewHolder.mOpenAppButton.setContentDescription(subscreenNotificationDetailAdapter.mContext.getResources().getString(R.string.subscreen_detail_adapter_open_app_button_text));
    }

    public void setQuickReplyFocusBackground(View view) {
        view.setBackground(null);
    }

    public final void setUnreadMessageCount(SubscreenNotificationInfo subscreenNotificationInfo, View view) {
        TextView textView;
        if (subscreenNotificationInfo == null || view == null || (textView = (TextView) view.findViewById(R.id.unread_message_count)) == null) {
            return;
        }
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.unread_message_count_layout);
        textView.setVisibility(8);
        if (frameLayout != null) {
            frameLayout.setVisibility(8);
        }
        if (subscreenNotificationInfo.mIsMessagingStyle) {
            ConversationNotificationManager.ConversationState conversationState = (ConversationNotificationManager.ConversationState) this.mController.conversationNotificationManager.states.get(subscreenNotificationInfo.mRow.mEntry.mKey);
            int i = conversationState != null ? conversationState.unreadCount : 1;
            if (i > 1) {
                int i2 = StringCompanionObject.$r8$clinit;
                textView.setText(String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf(i)}, 1)));
                textView.setVisibility(0);
                if (frameLayout != null) {
                    frameLayout.setVisibility(0);
                }
            }
        }
    }

    public PopupWindow showReplyButtonViewPopupWindow(View view, View view2) {
        return null;
    }

    public void showSubscreenNotification() {
        Runnable runnable;
        NotificationEntry notificationEntry;
        WakeLock wakeLock;
        ViewParent parent;
        if (this.mSubDisplay == null) {
            Log.d("S.S.N.", "return showSubscreenNotification - subDisplay does not exist");
            return;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(this.mNotiPopupType, "showSubscreenNotification Parent - ", "S.S.N.");
        if (this.mNotiPopupType == 2) {
            if (this.presentationShowing && isDismissiblePopup()) {
                Log.d("S.S.N.", "showSubscreenNotification PopupView - dismiss top presentation if it's showing");
                dismissImmediately(1);
            }
            WindowManager.LayoutParams topPopupLp = getTopPopupLp();
            SubscreenNotificationDetail subscreenNotificationDetail = this.popupViewNotiTemplate;
            if (subscreenNotificationDetail != null) {
                if (!this.popupViewShowing || this.mNotiPopupView == null) {
                    FrameLayout frameLayout = subscreenNotificationDetail.mLayout;
                    if (frameLayout != null && (parent = frameLayout.getParent()) != null) {
                        ((ViewGroup) parent).removeView(subscreenNotificationDetail.mLayout);
                    }
                    WindowManager windowManager = this.mWindowManager;
                    if (windowManager != null) {
                        windowManager.addView(subscreenNotificationDetail.mLayout, topPopupLp);
                    }
                    this.popupViewShowing = true;
                    this.mNotiPopupView = subscreenNotificationDetail.mLayout;
                    NotificationEntry notificationEntry2 = this.currentPopupViewEntry;
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("  Noti popup attached - ", notificationEntry2 != null ? notificationEntry2.mKey : null, "S.S.N.");
                    Animator popUpViewShowAnimator = getPopUpViewShowAnimator(this.mNotiPopupView);
                    if (popUpViewShowAnimator != null) {
                        popUpViewShowAnimator.start();
                    }
                } else {
                    WindowManager windowManager2 = this.mWindowManager;
                    if (windowManager2 != null) {
                        windowManager2.updateViewLayout(subscreenNotificationDetail.mLayout, topPopupLp);
                    }
                    NotificationEntry notificationEntry3 = this.currentPopupViewEntry;
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("  Noti popup updated - ", notificationEntry3 != null ? notificationEntry3.mKey : null, "S.S.N.");
                }
            }
            updateWakeLock(true, true);
            PowerManager powerManager = this.mPowerManager;
            if (powerManager != null) {
                powerManager.userActivity(SystemClock.uptimeMillis(), true);
            }
            runnable = this.popupViewTimeoutRunnable;
            if (runnable == null) {
                runnable = null;
            }
            notificationEntry = this.currentPopupViewEntry;
        } else {
            runnable = null;
            notificationEntry = null;
        }
        int i = this.mNotiPopupType;
        Handler handler = this.mHandler;
        if (i == 1) {
            if (this.popupViewShowing) {
                Log.d("S.S.N.", "showSubscreenNotification Presentation - dismiss top popup if it's showing");
                dismissImmediately(2);
            }
            this.mController.requestDozeState(64, true);
            handler.removeCallbacks(this.drawWalkLockReleaseRunnable);
            LinkedHashMap linkedHashMap = this.mFullScreenIntentEntries;
            NotificationEntry notificationEntry4 = this.currentPresentationEntry;
            if (!linkedHashMap.containsKey(notificationEntry4 != null ? notificationEntry4.mKey : null)) {
                NotificationEntry notificationEntry5 = this.currentPresentationEntry;
                if ((notificationEntry5 != null ? notificationEntry5.mInflationWakeLock : null) != null && notificationEntry5 != null && (wakeLock = notificationEntry5.mInflationWakeLock) != null) {
                    wakeLock.release(notificationEntry5.mKey);
                    notificationEntry5.mInflationWakeLock = null;
                }
                NotificationEntry notificationEntry6 = this.currentPresentationEntry;
                if (notificationEntry6 != null) {
                    WakeLock.Builder maxTimeout = new WakeLock.Builder(this.mContext, null).setMaxTimeout(5000L);
                    StringBuilder sb = new StringBuilder("Subscreen:DRAW_WAKE_LOCK: ");
                    String str = notificationEntry6.mKey;
                    sb.append(str);
                    WakeLock wakeLockBuild = maxTimeout.setTag(sb.toString()).build();
                    notificationEntry6.mInflationWakeLock = wakeLockBuild;
                    wakeLockBuild.acquire(str);
                }
            }
            if (this.mPresentation == null) {
                NotificationEntry notificationEntry7 = this.currentPresentationEntry;
                Log.d("S.S.N.", "  SHOW NEW - " + (notificationEntry7 != null ? notificationEntry7.mKey : null));
                Context context = this.mDisplayContext;
                if (context == null) {
                    context = null;
                }
                Display display = this.mSubDisplay;
                SubscreenNotificationDetail subscreenNotificationDetail2 = this.presentationNotiTemplate;
                SubscreenNotificationPresentation subscreenNotificationPresentation = new SubscreenNotificationPresentation(context, display, subscreenNotificationDetail2 != null ? subscreenNotificationDetail2.mLayout : null, this);
                this.mPresentation = subscreenNotificationPresentation;
                try {
                    subscreenNotificationPresentation.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent.showSubscreenNotification.3
                        @Override // android.content.DialogInterface.OnShowListener
                        public final void onShow(DialogInterface dialogInterface) {
                            StatusBarNotificationActivityStarter statusBarNotificationActivityStarter;
                            NotificationEntry notificationEntry8;
                            SubscreenDeviceModelParent subscreenDeviceModelParent = SubscreenDeviceModelParent.this;
                            LinkedHashMap linkedHashMap2 = subscreenDeviceModelParent.mFullScreenIntentEntries;
                            NotificationEntry notificationEntry9 = subscreenDeviceModelParent.currentPresentationEntry;
                            subscreenDeviceModelParent.updateWakeLock(true, linkedHashMap2.containsKey(notificationEntry9 != null ? notificationEntry9.mKey : null));
                            NotificationActivityStarter notificationActivityStarter = SubscreenDeviceModelParent.this.mNotificationActivityStarter;
                            if (notificationActivityStarter == null || (notificationEntry8 = (statusBarNotificationActivityStarter = (StatusBarNotificationActivityStarter) notificationActivityStarter).mPendingFullscreenEntry) == null) {
                                return;
                            }
                            if (new ArrayList(Arrays.asList("com.tencent.mm", "us.zoom.videomeetings", "com.google.android.dialer")).contains(notificationEntry8.mSbn.getPackageName())) {
                                return;
                            }
                            if (statusBarNotificationActivityStarter.mPendingFullscreenEntry.mSbn.getNotification().fullScreenIntent == null) {
                                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("return launchFullScreenIntent() - fullScreenIntent is null: "), statusBarNotificationActivityStarter.mPendingFullscreenEntry.mKey, "StatusBarNotificationActivityStarter");
                                statusBarNotificationActivityStarter.mPendingFullscreenEntry = null;
                            } else {
                                statusBarNotificationActivityStarter.mIsStartFullscreenIntentWhenSubscreen = Boolean.TRUE;
                                statusBarNotificationActivityStarter.launchFullScreenIntent(statusBarNotificationActivityStarter.mPendingFullscreenEntry);
                                statusBarNotificationActivityStarter.mPendingFullscreenEntry = null;
                            }
                        }
                    });
                    SubscreenNotificationPresentation subscreenNotificationPresentation2 = this.mPresentation;
                    if (subscreenNotificationPresentation2 != null) {
                        subscreenNotificationPresentation2.show();
                    }
                    this.presentationShowing = true;
                } catch (WindowManager.InvalidDisplayException e) {
                    Log.w("S.S.N.", "Invalid display: ", e);
                    updateWakeLock(false, false);
                    SubscreenNotificationPresentation subscreenNotificationPresentation3 = this.mPresentation;
                    if (subscreenNotificationPresentation3 != null) {
                        subscreenNotificationPresentation3.setOnShowListener(null);
                    }
                    this.mPresentation = null;
                }
            } else {
                SubscreenNotificationDetail subscreenNotificationDetail3 = this.presentationNotiTemplate;
                final FrameLayout frameLayout2 = subscreenNotificationDetail3 != null ? subscreenNotificationDetail3.mLayout : null;
                NotificationEntry notificationEntry8 = this.currentPresentationEntry;
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(" SHOW UPDATED - ", notificationEntry8 != null ? notificationEntry8.mKey : null, "S.S.N.");
                if (this.mPresentation != null) {
                    handler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$updateSubscreenNotificationView$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ViewGroup viewGroup;
                            SubscreenNotificationPresentation subscreenNotificationPresentation4 = this.this$0.mPresentation;
                            ViewGroup viewGroup2 = subscreenNotificationPresentation4 != null ? subscreenNotificationPresentation4.contents : null;
                            if (viewGroup2 != null && viewGroup2.getChildCount() > 0) {
                                viewGroup2.removeAllViews();
                            }
                            SubscreenNotificationPresentation subscreenNotificationPresentation5 = this.this$0.mPresentation;
                            if (subscreenNotificationPresentation5 == null || (viewGroup = subscreenNotificationPresentation5.contents) == null) {
                                return;
                            }
                            viewGroup.addView(frameLayout2);
                        }
                    });
                }
            }
            SubscreenDeviceModelParent$initTimeoutRunnable$1 subscreenDeviceModelParent$initTimeoutRunnable$1 = this.presentationTimeoutRunnable;
            SubscreenDeviceModelParent$initTimeoutRunnable$1 subscreenDeviceModelParent$initTimeoutRunnable$12 = subscreenDeviceModelParent$initTimeoutRunnable$1 != null ? subscreenDeviceModelParent$initTimeoutRunnable$1 : null;
            notificationEntry = this.currentPresentationEntry;
            runnable = subscreenDeviceModelParent$initTimeoutRunnable$12;
        }
        if (notificationEntry != null) {
            SubscreenDeviceModelParent$marqueeStartRunnable$1 subscreenDeviceModelParent$marqueeStartRunnable$1 = this.marqueeStartRunnable;
            handler.removeCallbacks(subscreenDeviceModelParent$marqueeStartRunnable$1);
            handler.postDelayed(subscreenDeviceModelParent$marqueeStartRunnable$1, 1000L);
            LinkedHashMap linkedHashMap2 = this.mFullScreenIntentEntries;
            String str2 = notificationEntry.mKey;
            long jCurrentTimeMillis = 300000;
            if (linkedHashMap2.get(str2) != null) {
                if (notificationEntry.mFullscreenPopUpStartTime != 0) {
                    jCurrentTimeMillis = 300000 - (System.currentTimeMillis() - notificationEntry.mFullscreenPopUpStartTime);
                } else {
                    notificationEntry.mFullscreenPopUpStartTime = System.currentTimeMillis();
                }
            }
            runnable.getClass();
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, this.mFullScreenIntentEntries.get(str2) != null ? jCurrentTimeMillis : 3000L);
            if (this.mFullScreenIntentEntries.get(str2) == null) {
                jCurrentTimeMillis = 3000;
            }
            Log.d("S.S.N.", "  showSubscreenNotification - " + str2 + ", " + jCurrentTimeMillis);
        }
    }

    public final boolean skipDetailClicked(NotificationEntry notificationEntry) {
        if (notificationEntry == null) {
            Log.d("S.S.N.", " TOAST CLICKED parent - entry is null");
            dismissImmediately(this.popupViewShowing ? 2 : 1);
            return true;
        }
        if (isCoverBriefAllowed(notificationEntry)) {
            Log.d("S.S.N.", " DETAIL CLICKED brief popup ");
            return false;
        }
        if (this.popupViewShowing && this.currentPopupViewEntry == null) {
            Log.d("S.S.N.", " TOAST CLICKED parent - currentPopupViewEntry is null");
            dismissImmediately(2);
            return true;
        }
        StringBuilder sb = new StringBuilder(" DETAIL CLICKED parent");
        String str = notificationEntry.mKey;
        ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "S.S.N.");
        NotificationEntry notificationEntry2 = this.currentPresentationEntry;
        if (!StringsKt__StringsJVMKt.equals(notificationEntry2 != null ? notificationEntry2.mKey : null, str, false) || !isSkipFullscreenIntentClicked(notificationEntry)) {
            return false;
        }
        Log.d("S.S.N.", " DETAIL CLICKED fullscreenIntent so return");
        return true;
    }

    public int smallIconPadding(boolean z, boolean z2, boolean z3) {
        return 0;
    }

    public int squircleRadius(boolean z, boolean z2) {
        return 0;
    }

    public final void updateBModeStatus() {
        UserManager userManager = this.mUserManager;
        List<UserInfo> users = userManager != null ? userManager.getUsers() : null;
        users.getClass();
        for (UserInfo userInfo : users) {
            if (userInfo.isBMode()) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(userInfo.id, "update bModeUserId : ", "S.S.N.");
                this.bModeUserId = userInfo.id;
                return;
            }
        }
    }

    public final void updateIconColor(ImageView imageView, NotificationEntry notificationEntry) {
        boolean zIsGrayscale;
        StatusBarNotification statusBarNotification;
        Notification notification2;
        StatusBarNotification statusBarNotification2;
        Notification notification3;
        ExpandableNotificationRow expandableNotificationRow;
        int appPrimaryColor = (notificationEntry == null || (expandableNotificationRow = notificationEntry.row) == null) ? 0 : ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getAppPrimaryColor(expandableNotificationRow);
        if (notificationEntry == null) {
            zIsGrayscale = false;
        } else if (notificationEntry.mRanking.getChannel().isImportantConversation()) {
            ImageView imageView2 = new ImageView(this.mContext);
            imageView2.setImageIcon(notificationEntry.mSbn.getNotification().getSmallIcon());
            zIsGrayscale = NotificationUtils.isGrayscale(imageView2, ContrastColorUtil.getInstance(this.mContext));
        } else {
            zIsGrayscale = NotificationUtils.isGrayscale(notificationEntry.mIcons.mStatusBarIcon, ContrastColorUtil.getInstance(this.mContext));
        }
        Log.d("S.S.N.", "updateIconColor() isGrayScale = " + zIsGrayscale + ", " + (notificationEntry != null ? notificationEntry.mKey : null));
        if (imageView != null) {
            if (!zIsGrayscale) {
                imageView.setColorFilter(0);
                if (imageView.getBackground() != null) {
                    int color = this.mContext.getColor(R.color.notification_non_grayscale_border_color);
                    int color2 = this.mContext.getColor(R.color.notification_non_grayscale_fill_color);
                    imageView.getBackground().setColorFilter(null);
                    if (this.mSettingsHelper.isShowNotificationAppIconEnabled()) {
                        imageView.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color2, PorterDuff.Mode.SRC_IN));
                        return;
                    }
                    GradientDrawable gradientDrawable = (GradientDrawable) imageView.getBackground().mutate();
                    gradientDrawable.setColor(color2);
                    gradientDrawable.setStroke(this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_icon_border_width), color);
                    return;
                }
                return;
            }
            int color3 = this.mContext.getColor(R.color.notification_app_icon_color);
            if (this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
                imageView.setColorFilter(Color.argb(255, Color.red(color3), Color.green(color3), Color.blue(color3)), PorterDuff.Mode.SRC_IN);
            } else if (notificationEntry != null && (statusBarNotification = notificationEntry.mSbn) != null && (notification2 = statusBarNotification.getNotification()) != null && !notification2.isColorized()) {
                imageView.setColorFilter(color3, PorterDuff.Mode.SRC_IN);
            }
            if (imageView.getBackground() != null) {
                if (notificationEntry != null && (statusBarNotification2 = notificationEntry.mSbn) != null && (notification3 = statusBarNotification2.getNotification()) != null && notification3.isColorized()) {
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("updateIconColor() - colorized "), notificationEntry.mKey, "S.S.N.");
                    NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
                    appPrimaryColor = notificationColorPicker.resolveContrastColor(notificationColorPicker.getNotificationDefaultBgColor(), this.mContext.getResources().getConfiguration().isNightModeActive(), notificationEntry.row);
                }
                imageView.getBackground().setColorFilter(appPrimaryColor, PorterDuff.Mode.SRC_IN);
            }
        }
    }

    public void updateKnoxIcon(ImageView imageView, SubscreenNotificationInfo subscreenNotificationInfo) {
        if (imageView == null || subscreenNotificationInfo == null) {
            return;
        }
        Drawable drawable = subscreenNotificationInfo.mKnoxBadgeDrawable;
        if (drawable == null) {
            imageView.setVisibility(8);
        } else {
            imageView.setVisibility(0);
            imageView.setImageDrawable(drawable);
        }
    }

    public final void updateNotiShowBlocked() {
        this.notiShowBlocked = !this.mSettingsHelper.isCoverscreenShowNotification();
        this.notiFullPopupBlocked = !this.mSettingsHelper.isTurnOnCoverscreenForNotification();
    }

    public void updateSmallIconSquircleBg(ImageView imageView, boolean z, boolean z2) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setCornerRadius(squircleRadius(z, z2));
        int iSmallIconPadding = smallIconPadding(z, z2, false);
        if (imageView != null) {
            imageView.setBackground(gradientDrawable);
            imageView.setPadding(iSmallIconPadding, iSmallIconPadding, iSmallIconPadding, iSmallIconPadding);
        }
    }

    public final void updateWakeLock(boolean z, boolean z2) {
        SettableWakeLock settableWakeLock;
        SettableWakeLock settableWakeLock2;
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m(" updateWakeLock - acquire : ", ", force : ", "S.S.N.", z, z2);
        if (z) {
            if (!z2 || (settableWakeLock2 = this.mScreenOnwakelock) == null) {
                return;
            }
            settableWakeLock2.setAcquired(true);
            return;
        }
        SettableWakeLock settableWakeLock3 = this.mScreenOnwakelock;
        if (settableWakeLock3 == null || !settableWakeLock3.isAcquired() || (settableWakeLock = this.mScreenOnwakelock) == null) {
            return;
        }
        settableWakeLock.setAcquired(false);
    }

    public boolean useTopPresentation() {
        return false;
    }

    public int convertAdapterPositionToInfoIndex(int i) {
        return i;
    }

    public int convertInfoIndexToAdapterPosition(int i) {
        return i;
    }

    public void initDetailAdapterTextViewHolder(SubscreenNotificationDetailAdapter.TextViewHolder textViewHolder) {
    }

    public void initMainHeaderView(LinearLayout linearLayout) {
    }

    public void onBindDetailAdapterTextViewHolder(SubscreenNotificationDetailAdapter.TextViewHolder textViewHolder) {
    }

    public void onStateChangedInDeviceStateCallback(DeviceState deviceState) {
    }

    public void removeSmartReplyHashMap(String str) {
    }

    public void replyActivityFinished(boolean z) {
    }

    public void setDimOnMainBackground(View view) {
    }

    public void setFullPopupWindowKeyEventListener(FrameLayout frameLayout) {
    }

    public void setItemDecoration(RecyclerView recyclerView) {
    }

    public void setKeyguardStateWhenAddSubscreenNotificationInfoList(boolean z) {
    }

    public void setListAdpaterFirstChildTopMargin(SubscreenParentItemViewHolder subscreenParentItemViewHolder) {
    }

    public void setListAdpaterPosition(int i) {
    }

    public void setTipViewPadding(View view) {
    }

    public void updateMainHeaderView(LinearLayout linearLayout) {
    }

    public void updateMainHeaderViewVisibility(int i) {
    }

    public void cancelReplySendButtonAnimator() {
    }

    public void closeFullscreenFullPopupWindow() {
    }

    public void dimissTopPopupNotification() {
    }

    public void hideSmartReplyErrorMessage() {
    }

    public void initFirstHistoryItemPostTimeInDetailAdapter() {
    }

    public void initKeyguardActioninfo() {
    }

    public void initSmartReplyStatus() {
    }

    public void loadOnDeviceMetaData() {
    }

    public void onDisplayReady() {
    }

    public void onTipButtonClicked() {
    }

    public void registerAODTspReceiver() {
    }

    public void releaseSmartReply() {
    }

    public void runSmartReplyUncompletedOperation() {
    }

    public void setIsReplySendButtonLoading() {
    }

    public void setStartedReplyActivity() {
    }

    public void showAIReply() {
    }

    public void unregisterAODTspReceiver() {
    }

    public void updateContentScroll() {
    }

    public void updateSamsungAccount() {
    }

    public final void dismissImmediately(NotificationEntry notificationEntry) {
        boolean z = this.popupViewShowing;
        boolean z2 = this.presentationShowing;
        NotificationEntry notificationEntry2 = this.currentPopupViewEntry;
        String str = notificationEntry2 != null ? notificationEntry2.mKey : null;
        NotificationEntry notificationEntry3 = this.currentPresentationEntry;
        String str2 = notificationEntry3 != null ? notificationEntry3.mKey : null;
        String str3 = notificationEntry != null ? notificationEntry.mKey : null;
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m(" DISMISS IMMEDIATELY(entry) - popupViewShowing : ", ", presentationShowing : ", ", currentPopupViewEntry : ", z, z2);
        MoveResult$$ExternalSyntheticOutline0.m(sbM, str, ", currentPresentationEntry : ", str2, ", key : ");
        ExifInterface$$ExternalSyntheticOutline0.m(sbM, str3, "S.S.N.");
        if (notificationEntry == null) {
            return;
        }
        NotificationEntry notificationEntry4 = this.currentPresentationEntry;
        String str4 = notificationEntry4 != null ? notificationEntry4.mKey : null;
        String str5 = notificationEntry.mKey;
        if (StringsKt__StringsJVMKt.equals(str4, str5, false)) {
            dismissImmediately(1);
        }
        NotificationEntry notificationEntry5 = this.currentPopupViewEntry;
        if (StringsKt__StringsJVMKt.equals(notificationEntry5 != null ? notificationEntry5.mKey : null, str5, false)) {
            dismissImmediately(2);
        }
    }

    public void adjustLayoutByCutout(int i, int i2) {
    }

    public void clickAdapterItem(Context context, SubscreenParentItemViewHolder subscreenParentItemViewHolder) {
    }

    public void onBindDetailAdapterItemViewHolder(SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
    }

    public void setClock(SubscreenNotificationInfo subscreenNotificationInfo, View view) {
    }

    public void setGroupAdapterFooterMargin(Context context, SubscreenNotificationGroupAdapter.FooterViewHolder footerViewHolder) {
    }

    public void setListItemTextLayout(Context context, View view) {
    }

    public void setReplyWordTextStyle(TextView textView, Typeface typeface) {
    }

    public void updateImportBadgeIconRing(View view, boolean z) {
    }

    public void updateMoreShadowIconColor(View view, NotificationEntry notificationEntry) {
    }

    public void clickLiveNotification(Context context, SubscreenParentItemViewHolder subscreenParentItemViewHolder, OngoingActivityData ongoingActivityData) {
    }

    public void initMainHeaderViewItems(Context context, SubscreenNotificationInfo subscreenNotificationInfo, boolean z) {
    }

    public void setGroupAdapterIcon(Context context, SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter, SubscreenParentItemViewHolder subscreenParentItemViewHolder) {
    }

    public void setRightIcon(Context context, SubscreenNotificationInfo subscreenNotificationInfo, View view) {
    }

    public void setSmartReplyResultValue(int i, String str, StringBuilder sb) {
    }

    public void makePopupDetailView(Context context, NotificationEntry notificationEntry, boolean z, FrameLayout frameLayout) {
    }

    public void moveDetailAdapterContentScroll(View view, boolean z, boolean z2, boolean z3) {
    }

    public void updateSmallIconBg(ImageView imageView, boolean z, boolean z2, boolean z3) {
    }
}
