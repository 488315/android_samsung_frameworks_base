package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.app.ActivityManager;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.UserInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UserManager;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.AbstractC0000x2c234b15;
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
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.util.ContrastColorUtil;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.LockscreenNotificationInfo;
import com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfo;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda6;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import com.samsung.android.edge.SemEdgeManager;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SubscreenDeviceModelParent {
    public ActivityManager activityManager;
    public NotificationEntry currentPopupViewEntry;
    public NotificationEntry currentPresentationEntry;
    public NotificationEntry mBubbleReplyEntry;
    public final LogBuffer mBuffer;
    public FrameLayout mCallFullPopupBacgroundView;
    public final Context mContext;
    public final SubscreenNotificationController mController;
    public Context mDisplayContext;
    public SemEdgeManager mEdgeManager;
    public final NotificationInterruptStateProvider mInterruptionStateProvider;
    public boolean mIsChangedToFoldState;
    public boolean mIsCovered;
    public boolean mIsFlexMode;
    public boolean mIsFolded;
    public boolean mIsFullscreenFullPopupWindowClosing;
    public boolean mIsKeyguardStateWhenAddLockscreenNotificationInfoArray;
    public boolean mIsNotificationRemoved;
    public boolean mIsRemoving;
    public boolean mIsReplyNotification;
    public boolean mIsUpdatedAllMainList;
    public KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public int mListAdapterItemPosition;
    public Animator mMainViewAnimator;
    public int mNotiPopupType;
    public View mNotiPopupView;
    public final CommonNotifCollection mNotifCollection;
    public NotificationActivityStarter mNotificationActivityStarter;
    public PowerManager mPowerManager;
    public SubscreenNotificationPresentation mPresentation;
    public SettableWakeLock mScreenOnwakelock;
    public final SettingsHelper mSettingsHelper;
    public Display mSubDisplay;
    public SubscreenSubRoomNotification mSubRoomNotification;
    public final Lazy mSubScreenManagerLazy;
    public final UserContextProvider mUserContextProvider;
    public final UserManager mUserManager;
    public WindowManager mWindowManager;
    public boolean notiFullPopupBlocked;
    public boolean notiShowBlocked;
    public SubscreenNotificationTemplate popupViewNotiTemplate;
    public boolean popupViewShowing;
    public SubscreenDeviceModelParent$initTimeoutRunnable$2 popupViewTimeoutRunnable;
    public SubscreenNotificationDetail presentationNotiTemplate;
    public boolean presentationShowing;
    public SubscreenDeviceModelParent$initTimeoutRunnable$1 presentationTimeoutRunnable;
    public int bModeUserId = -1;
    public int currentUserId = ActivityManager.getCurrentUser();
    public final Handler mHandler = new Handler();
    public final LinkedHashMap mFullScreenIntentEntries = new LinkedHashMap();
    public final HashSet mNotiKeySet = new HashSet();
    public final LinkedHashMap mMainListArrayHashMap = new LinkedHashMap();
    public final LinkedHashMap mMainListUpdateItemHashMap = new LinkedHashMap();
    public final LinkedHashMap mMainListAddEntryHashMap = new LinkedHashMap();
    public final LinkedHashMap mMainListRemoveEntryHashMap = new LinkedHashMap();
    public final GroupMembershipManager mGroupMembershipManager = (GroupMembershipManager) Dependency.get(GroupMembershipManager.class);
    public final SubscreenDeviceModelParent$marqueeStartRunnable$1 marqueeStartRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$marqueeStartRunnable$1
        @Override // java.lang.Runnable
        public final void run() {
            TextView textView;
            SubscreenNotificationTemplate subscreenNotificationTemplate = SubscreenDeviceModelParent.this.popupViewNotiTemplate;
            if (subscreenNotificationTemplate == null || (textView = subscreenNotificationTemplate.mMarqueeText) == null) {
                return;
            }
            textView.setSelected(true);
        }
    };
    public final SubscreenDeviceModelParent$topPopupAnimationListener$1 topPopupAnimationListener = new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$topPopupAnimationListener$1
        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            SubscreenDeviceModelParent subscreenDeviceModelParent = SubscreenDeviceModelParent.this;
            Log.d("S.S.N.", " topPopupAnimationListener - onAnimationEnd , mNotiPopupView : " + subscreenDeviceModelParent.mNotiPopupView + ", popupViewNotiTemplate : " + subscreenDeviceModelParent.popupViewNotiTemplate);
            View view = SubscreenDeviceModelParent.this.mNotiPopupView;
            if (view != null) {
                if (view != null) {
                    view.setVisibility(4);
                }
                SubscreenDeviceModelParent subscreenDeviceModelParent2 = SubscreenDeviceModelParent.this;
                WindowManager windowManager = subscreenDeviceModelParent2.mWindowManager;
                if (windowManager != null) {
                    windowManager.removeViewImmediate(subscreenDeviceModelParent2.mNotiPopupView);
                }
            }
            SubscreenDeviceModelParent subscreenDeviceModelParent3 = SubscreenDeviceModelParent.this;
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
            SubscreenDeviceModelParent subscreenDeviceModelParent = SubscreenDeviceModelParent.this;
            if (subscreenDeviceModelParent.popupViewShowing) {
                Log.d("S.S.N.", " onFinishedGoingToSleep and HUN is showing, so dismiss it");
                subscreenDeviceModelParent.dismissImmediately(2);
            }
            if (subscreenDeviceModelParent.presentationShowing && subscreenDeviceModelParent.isDismissiblePopup()) {
                Log.d("S.S.N.", " onFinishedGoingToSleep and PRESENTATION is showing, so dismiss it");
                subscreenDeviceModelParent.dismissImmediately(1);
            }
            if (subscreenDeviceModelParent.showPopupEntryKeySet.size() > 0) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m(" onFinishedGoingToSleep - clear popup key set : ", subscreenDeviceModelParent.showPopupEntryKeySet.size(), "S.S.N.");
                subscreenDeviceModelParent.showPopupEntryKeySet.clear();
            }
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedGoingToSleep() {
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter;
            SubscreenNotificationInfo subscreenNotificationInfo;
            ExpandableNotificationRow expandableNotificationRow;
            SubscreenDeviceModelParent subscreenDeviceModelParent = SubscreenDeviceModelParent.this;
            if (subscreenDeviceModelParent.isSubScreen()) {
                Log.d("S.S.N.", " onStartedGoingToSleep");
                if (subscreenDeviceModelParent.isShownDetail()) {
                    subscreenDeviceModelParent.hideDetailNotification();
                    SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = subscreenDeviceModelParent.mController.replyActivity;
                    if (subscreenNotificationReplyActivity != null) {
                        subscreenNotificationReplyActivity.finish();
                    }
                }
                if (subscreenDeviceModelParent.isShownGroup()) {
                    SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelParent.mSubRoomNotification;
                    boolean z = false;
                    if (subscreenSubRoomNotification != null && (subscreenNotificationGroupAdapter = subscreenSubRoomNotification.mNotificationGroupAdapter) != null && (subscreenNotificationInfo = subscreenNotificationGroupAdapter.mSummaryInfo) != null && (expandableNotificationRow = subscreenNotificationInfo.mRow) != null && expandableNotificationRow.needsRedaction()) {
                        z = true;
                    }
                    if (z) {
                        subscreenDeviceModelParent.hideGroupNotification();
                    }
                }
            }
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedWakingUp() {
            int i = ((WakefulnessLifecycle) Dependency.get(WakefulnessLifecycle.class)).mLastWakeReason;
            ListPopupWindow$$ExternalSyntheticOutline0.m10m(" onStartedWakingUp - why: ", i, "S.S.N.");
            if (i == 6 || i == 15 || i == 113) {
                SubscreenDeviceModelParent subscreenDeviceModelParent = SubscreenDeviceModelParent.this;
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
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$mUpdateMonitorCallback$1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("onUserSwitchComplete : ", i, "S.S.N.");
            SubscreenDeviceModelParent subscreenDeviceModelParent = SubscreenDeviceModelParent.this;
            subscreenDeviceModelParent.updateNotiShowBlocked();
            subscreenDeviceModelParent.currentUserId = i;
            subscreenDeviceModelParent.updateBModeStatus();
            subscreenDeviceModelParent.updateSamsungAccount();
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MainListHashMapItem {
        public NotificationEntry mEntry;
        public SubscreenNotificationInfo mInfo;
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v22, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$marqueeStartRunnable$1] */
    /* JADX WARN: Type inference failed for: r1v23, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$topPopupAnimationListener$1] */
    /* JADX WARN: Type inference failed for: r1v25, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$mWakefulnessObserver$1] */
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

    public static boolean isOnlyGroupSummary(NotificationEntry notificationEntry) {
        NotificationChildrenContainer notificationChildrenContainer = notificationEntry.row.mChildrenContainer;
        return notificationEntry.mSbn.getNotification().isGroupSummary() && (notificationChildrenContainer == null || notificationChildrenContainer.getNotificationChildCount() == 0);
    }

    public static void updateKnoxIcon(ImageView imageView, SubscreenNotificationInfo subscreenNotificationInfo) {
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

    public void bindImageBitmap(ImageView imageView, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        imageView.setImageBitmap(bitmap);
    }

    public final boolean checkBubbleLastHistoryReply(NotificationEntry notificationEntry) {
        SubscreenNotificationInfoManager subscreenNotificationInfoManager;
        if (this.mBubbleReplyEntry == null || !notificationEntry.canBubble()) {
            return false;
        }
        NotificationEntry notificationEntry2 = this.mBubbleReplyEntry;
        if (!notificationEntry.mKey.equals(notificationEntry2 != null ? notificationEntry2.mKey : null)) {
            return false;
        }
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        SubscreenNotificationInfo createItemsData = (subscreenSubRoomNotification == null || (subscreenNotificationInfoManager = subscreenSubRoomNotification.mNotificationInfoManager) == null) ? null : subscreenNotificationInfoManager.createItemsData(notificationEntry.row);
        ArrayList arrayList = createItemsData != null ? createItemsData.mMessageingStyleInfoArray : null;
        Intrinsics.checkNotNull(arrayList);
        return !this.mController.useHistory(notificationEntry) || (arrayList.size() > 0 && ((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(arrayList.size() - 1)).mIsReply);
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

    /* JADX WARN: Removed duplicated region for block: B:28:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void detailClicked(final NotificationEntry notificationEntry) {
        boolean z;
        SubscreenRecyclerView subscreenRecyclerView;
        View childAt;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        SubscreenNotificationInfo subscreenNotificationInfo;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2;
        if (skipDetailClicked(notificationEntry)) {
            return;
        }
        if (notificationEntry != null && !launchApp(notificationEntry)) {
            if (isShownDetail()) {
                SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
                if (((subscreenSubRoomNotification == null || (subscreenNotificationDetailAdapter2 = subscreenSubRoomNotification.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter2.mSelectNotificationInfo) != null) {
                    if (notificationEntry.mKey.equals((subscreenSubRoomNotification == null || (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) == null || (subscreenNotificationInfo = subscreenNotificationDetailAdapter.mSelectNotificationInfo) == null) ? null : subscreenNotificationInfo.mKey)) {
                        z = true;
                        Lazy lazy = this.mSubScreenManagerLazy;
                        if (z) {
                            SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
                            SubscreenNotificationInfo createItemsData = subscreenSubRoomNotification2 != null ? subscreenSubRoomNotification2.mNotificationInfoManager.createItemsData(notificationEntry.row) : null;
                            SubscreenSubRoomNotification subscreenSubRoomNotification3 = this.mSubRoomNotification;
                            if (subscreenSubRoomNotification3 != null) {
                                subscreenSubRoomNotification3.notifyNotificationSubRoomRequest();
                            }
                            ((SubScreenManager) lazy.get()).startSubHomeActivity();
                            SubscreenSubRoomNotification subscreenSubRoomNotification4 = this.mSubRoomNotification;
                            if (subscreenSubRoomNotification4 != null) {
                                subscreenSubRoomNotification4.showDetailNotification(createItemsData);
                            }
                        } else {
                            SubscreenSubRoomNotification subscreenSubRoomNotification5 = this.mSubRoomNotification;
                            if (subscreenSubRoomNotification5 != null) {
                                subscreenSubRoomNotification5.notifyNotificationSubRoomRequest();
                            }
                            ((SubScreenManager) lazy.get()).startSubHomeActivity();
                            SubscreenSubRoomNotification subscreenSubRoomNotification6 = this.mSubRoomNotification;
                            if (subscreenSubRoomNotification6 != null && (subscreenRecyclerView = subscreenSubRoomNotification6.mNotificationRecyclerView) != null && (childAt = subscreenRecyclerView.getChildAt(0)) != null) {
                                moveDetailAdapterContentScroll(childAt, false, false, true);
                            }
                        }
                    }
                }
            }
            z = false;
            Lazy lazy2 = this.mSubScreenManagerLazy;
            if (z) {
            }
        }
        if (isCoverBriefAllowed(notificationEntry)) {
            return;
        }
        Handler handler = this.mHandler;
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$detailClicked$2
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
        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m(" DISMISS IMMEDIATELY(popupType) - popupViewShowing : ", z, ", presentationShowing : ", z2, ", mNotiPopupView : ");
        m69m.append(view2);
        m69m.append(", mPresentation : ");
        m69m.append(subscreenNotificationPresentation);
        m69m.append(", popupType : ");
        RecyclerView$$ExternalSyntheticOutline0.m46m(m69m, i, "S.S.N.");
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
        if (NotiRune.NOTI_SUBSCREEN_PENDING_CALL_FULLSCRREN_INTENT && (notificationActivityStarter2 = this.mNotificationActivityStarter) != null) {
            StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = (StatusBarNotificationActivityStarter) notificationActivityStarter2;
            statusBarNotificationActivityStarter.mShouldSkipFullScreenIntent = false;
            NotificationEntry notificationEntry = statusBarNotificationActivityStarter.mPendingFullscreenEntry;
            if (notificationEntry != null && notificationEntry.mSbn.getNotification().fullScreenIntent != null) {
                statusBarNotificationActivityStarter.launchFullScreenIntent(statusBarNotificationActivityStarter.mPendingFullscreenEntry);
                statusBarNotificationActivityStarter.mPendingFullscreenEntry = null;
            }
        }
        LinkedHashMap linkedHashMap = this.mFullScreenIntentEntries;
        if (linkedHashMap.size() > 0) {
            Log.d("S.S.N.", " foldStateChanged - clear mFullScreenIntentEntries");
            linkedHashMap.clear();
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
        ArraySet arraySet = this.showPopupEntryKeySet;
        if (arraySet.size() > 0) {
            Log.d("S.S.N.", " foldStateChanged - clear popup key set : " + arraySet.size());
            arraySet.clear();
        }
        clearMainList();
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        String str = "SubscreenNotificationInfoManager";
        if (subscreenSubRoomNotification != null && (subscreenNotificationInfoManager2 = subscreenSubRoomNotification.mNotificationInfoManager) != null) {
            Log.d("SubscreenNotificationInfoManager", " clearArrayListAll");
            subscreenNotificationInfoManager2.clearAllRecyclerViewItem();
            subscreenNotificationInfoManager2.mGroupDataArray.clear();
            SubscreenNotificationInfoManager.mLockscreenNotificationInfoArray.clear();
            subscreenNotificationInfoManager2.mNotificationListAdapter.notifyDataSetChanged();
            subscreenNotificationInfoManager2.mNotificationGroupAdapter.notifyDataSetChanged();
            subscreenNotificationInfoManager2.mNotificationDetailAdapter.notifyDataSetChanged();
        }
        SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
        if (subscreenSubRoomNotification2 == null || (subscreenNotificationInfoManager = subscreenSubRoomNotification2.mNotificationInfoManager) == null) {
            return;
        }
        int notificationInfoArraySize = SubscreenNotificationInfoManager.getNotificationInfoArraySize();
        for (int i = 0; i < notificationInfoArraySize; i++) {
            NotificationEntry notificationEntry2 = ((LockscreenNotificationInfo) SubscreenNotificationInfoManager.mLockscreenNotificationInfoArray.get(i)).mRow.mEntry;
            if (notificationEntry2.canBubble()) {
                NotifCollection notifCollection = subscreenNotificationInfoManager.mNotifCollection;
                notifCollection.getClass();
                notifCollection.mMainHandler.post(new NotifCollection$$ExternalSyntheticLambda6(notificationEntry2.mSbn, notifCollection, str, "Update the bubble notification in the subscreen state"));
                return;
            }
        }
    }

    public int getDetailAdapterAutoScrollCurrentPositionByReceive(View view) {
        return 0;
    }

    public int getDetailAdapterContentViewResource() {
        return -1;
    }

    public View getDetailAdapterLayout(RecyclerView recyclerView, int i, Context context) {
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

    public View getGroupAdapterLayout(RecyclerView recyclerView, int i, Context context) {
        return null;
    }

    public int getLayoutInDisplayCutoutMode() {
        return 0;
    }

    public int getListAdapterGroupItemResource() {
        return -1;
    }

    public View getListAdapterLayout(RecyclerView recyclerView, int i, Context context) {
        return null;
    }

    public final Context getMDisplayContext() {
        Context context = this.mDisplayContext;
        if (context != null) {
            return context;
        }
        return null;
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
            this.mSubRoomNotification = SubscreenSubRoomNotification.getInstance(getMDisplayContext());
        }
        return this.mSubRoomNotification;
    }

    public int getSubscreenNotificationTipResource() {
        return -1;
    }

    public final String getTopActivityName() {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        ComponentName componentName;
        try {
            ActivityManager activityManager = this.activityManager;
            String str = null;
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager != null ? activityManager.getRunningTasks(1) : null;
            if (runningTasks != null && (runningTaskInfo = runningTasks.get(0)) != null && (componentName = runningTaskInfo.topActivity) != null) {
                str = componentName.getClassName();
            }
            return str == null ? "" : str;
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

    public final void hideDetailNotification() {
        hideDetailNotificationAnimated(300, true);
    }

    public final void hideDetailNotificationAnimated(int i, boolean z) {
        if (z) {
            this.mIsUpdatedAllMainList = true;
        }
        setIsReplySendButtonLoading();
        if (this.mMainViewAnimator == null) {
            Log.d("S.S.N.", "hideDetailNotificationAnimated start animtion");
            final SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
            this.mMainViewAnimator = subscreenSubRoomNotification != null ? subscreenSubRoomNotification.mNotificationAnimatorManager.alphaAnimatedMainView(subscreenSubRoomNotification.mSubscreenMainLayout, new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$hideDetailNotificationAnimated$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION) {
                        SubscreenDeviceModelParent.this.mController.hideDetailNotif();
                    }
                    subscreenSubRoomNotification.hideDetailNotification();
                }
            }, i) : null;
            return;
        }
        Log.d("S.S.N.", "hideDetailNotificationAnimated already animtion");
        if (NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION) {
            this.mController.hideDetailNotif();
        }
        SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
        if (subscreenSubRoomNotification2 != null) {
            subscreenSubRoomNotification2.hideDetailNotification();
        }
    }

    public final void hideGroupNotification() {
        Animator animator;
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
            animator = subscreenSubRoomNotification2.mNotificationAnimatorManager.alphaAnimatedMainView(subscreenSubRoomNotification2.mSubscreenMainLayout, new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$hideGroupNotificationAnimated$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    SubscreenSubRoomNotification.this.hideGroupNotification();
                }
            }, 300);
        } else {
            animator = null;
        }
        this.mMainViewAnimator = animator;
    }

    public ImageView initDetailAdapterBackButton(View view) {
        return null;
    }

    public void initDetailAdapterItemViewHolder(Context context, final SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, final SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        itemViewHolder.mAdapter = subscreenNotificationDetailAdapter;
        itemViewHolder.mOpenAppButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$initDetailAdapterItemViewHolder$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscreenNotificationInfo subscreenNotificationInfo = SubscreenNotificationDetailAdapter.ItemViewHolder.this.mInfo;
                String str = subscreenNotificationInfo.mKey;
                String str2 = subscreenNotificationInfo.mPkg;
                String str3 = subscreenNotificationInfo.mAppName;
                StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("Click BodyLayout Key: ", str, ", mInfo.getPkg()", str2, ", mInfo.getAppName() ");
                m87m.append(str3);
                Log.e("SubscreenNotificationDetailAdapter", m87m.toString());
                subscreenNotificationDetailAdapter.getClass();
                SubscreenParentDetailItemViewHolder subscreenParentDetailItemViewHolder = SubscreenNotificationDetailAdapter.ItemViewHolder.this;
                subscreenParentDetailItemViewHolder.startWaitState(subscreenNotificationDetailAdapter, subscreenParentDetailItemViewHolder);
                SystemUIAnalytics.sendEventLog("QPN102", "QPNE0204");
            }
        });
    }

    public void initDisplay() {
        Context context = this.mContext;
        Display[] displays = ((DisplayManager) context.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        if (displays.length <= 1) {
            this.mDisplayContext = context;
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("Parent - fail to get subDisplay, display list size is ", displays.length, "S.S.N.");
        } else {
            Display display = displays[1];
            this.mSubDisplay = display;
            this.mDisplayContext = context.createDisplayContext(display);
        }
    }

    public void initGroupAdapterHeaderViewHolder(Context context, View view, SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter, SubscreenNotificationGroupAdapter.HeaderViewHolder headerViewHolder) {
        headerViewHolder.mIcon = (ImageView) view.findViewById(R.id.subscreen_header_icon);
        headerViewHolder.mAppName = (TextView) view.findViewById(R.id.subscreen_header_app_name);
        headerViewHolder.mTwoPhoneIcon = (ImageView) view.findViewById(R.id.two_phone_icon);
        headerViewHolder.mSecureIcon = (ImageView) view.findViewById(R.id.secure_icon);
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$initTimeoutRunnable$1] */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$initTimeoutRunnable$2] */
    public void initialize() {
        Context context = this.mContext;
        this.mEdgeManager = (SemEdgeManager) context.getSystemService("edge");
        this.activityManager = (ActivityManager) context.getSystemService("activity");
        initDisplay();
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        this.mWindowManager = (WindowManager) getMDisplayContext().getSystemService("window");
        PowerManager powerManager = this.mPowerManager;
        this.mScreenOnwakelock = new SettableWakeLock(WakeLock.wrap(powerManager != null ? powerManager.newWakeLock(268435466, "SystemUI:SubscreenNotification") : null, null, 300000L), "S.S.N.:ScreenOn");
        ((WakefulnessLifecycle) Dependency.get(WakefulnessLifecycle.class)).addObserver(this.mWakefulnessObserver);
        this.presentationTimeoutRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$initTimeoutRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                SubscreenDeviceModelParent subscreenDeviceModelParent = SubscreenDeviceModelParent.this;
                NotificationEntry notificationEntry = subscreenDeviceModelParent.currentPresentationEntry;
                String str = notificationEntry != null ? notificationEntry.mKey : null;
                Log.d("S.S.N.", " TIMEOUT Run Parent PRESENTATION - RELEASE DOZE STATE - TIMEOUT : " + str + " , presentationShowing : " + subscreenDeviceModelParent.presentationShowing + ", mPresentation : " + subscreenDeviceModelParent.mPresentation);
                SubscreenDeviceModelParent subscreenDeviceModelParent2 = SubscreenDeviceModelParent.this;
                subscreenDeviceModelParent2.mIsRemoving = true;
                subscreenDeviceModelParent2.updateWakeLock(false, false);
                final SubscreenDeviceModelParent subscreenDeviceModelParent3 = SubscreenDeviceModelParent.this;
                subscreenDeviceModelParent3.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$initTimeoutRunnable$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SubscreenNotificationPresentation subscreenNotificationPresentation;
                        SubscreenDeviceModelParent subscreenDeviceModelParent4 = SubscreenDeviceModelParent.this;
                        Log.d("S.S.N.", " DISMISS Run - isRemoving: " + subscreenDeviceModelParent4.mIsRemoving + ", presentation: " + subscreenDeviceModelParent4.mPresentation);
                        SubscreenDeviceModelParent subscreenDeviceModelParent5 = SubscreenDeviceModelParent.this;
                        if (!subscreenDeviceModelParent5.mIsRemoving || (subscreenNotificationPresentation = subscreenDeviceModelParent5.mPresentation) == null) {
                            return;
                        }
                        if (subscreenNotificationPresentation != null) {
                            subscreenNotificationPresentation.dismiss();
                        }
                        SubscreenDeviceModelParent.this.mIsRemoving = false;
                        Log.d("S.S.N.", "updateWakeLock false in timeoutRunnable");
                    }
                }, 1000L);
                SubscreenDeviceModelParent.this.mNotiPopupType = 0;
            }
        };
        this.popupViewTimeoutRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$initTimeoutRunnable$2
            @Override // java.lang.Runnable
            public final void run() {
                View view;
                Animator popUpViewDismissAnimator;
                SubscreenDeviceModelParent subscreenDeviceModelParent = SubscreenDeviceModelParent.this;
                NotificationEntry notificationEntry = subscreenDeviceModelParent.currentPopupViewEntry;
                String str = notificationEntry != null ? notificationEntry.mKey : null;
                Log.d("S.S.N.", " TIMEOUT Run Parent POPUPVIEW - RELEASE DOZE STATE - TIMEOUT : " + str + " ,popupViewShowing : " + subscreenDeviceModelParent.popupViewShowing + " , mNotiPopupView : " + subscreenDeviceModelParent.mNotiPopupView);
                SubscreenDeviceModelParent subscreenDeviceModelParent2 = SubscreenDeviceModelParent.this;
                if (subscreenDeviceModelParent2.popupViewShowing && (view = subscreenDeviceModelParent2.mNotiPopupView) != null && (popUpViewDismissAnimator = subscreenDeviceModelParent2.getPopUpViewDismissAnimator(view)) != null) {
                    popUpViewDismissAnimator.start();
                }
                SubscreenDeviceModelParent subscreenDeviceModelParent3 = SubscreenDeviceModelParent.this;
                boolean z = subscreenDeviceModelParent3.presentationShowing;
                LinkedHashMap linkedHashMap = subscreenDeviceModelParent3.mFullScreenIntentEntries;
                NotificationEntry notificationEntry2 = subscreenDeviceModelParent3.currentPresentationEntry;
                subscreenDeviceModelParent3.updateWakeLock(z, linkedHashMap.containsKey(notificationEntry2 != null ? notificationEntry2.mKey : null));
                SubscreenDeviceModelParent.this.mNotiPopupType = 0;
            }
        };
        updateNotiShowBlocked();
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        updateBModeStatus();
        loadOnDeviceMetaData();
    }

    public final boolean isBubbleNotificationSuppressed(NotificationEntry notificationEntry) {
        if (!notificationEntry.canBubble()) {
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

    public final boolean isGrayScaleIcon(NotificationEntry notificationEntry) {
        if (notificationEntry == null) {
            return false;
        }
        boolean isImportantConversation = notificationEntry.getChannel().isImportantConversation();
        Context context = this.mContext;
        if (!isImportantConversation) {
            return NotificationUtils.isGrayscale(notificationEntry.mIcons.mStatusBarIcon, ContrastColorUtil.getInstance(context));
        }
        ImageView imageView = new ImageView(context);
        imageView.setImageIcon(notificationEntry.mSbn.getNotification().getSmallIcon());
        return NotificationUtils.isGrayscale(imageView, ContrastColorUtil.getInstance(context));
    }

    public boolean isKeyguardStats() {
        return true;
    }

    public boolean isKnoxSecurity(NotificationEntry notificationEntry) {
        return true;
    }

    public boolean isLaunchApp(NotificationEntry notificationEntry) {
        return false;
    }

    public boolean isNotShwonNotificationState(NotificationEntry notificationEntry) {
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0080, code lost:
    
        if (r0 != false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x00a8, code lost:
    
        if ((r13.flags & 8) != 0) goto L39;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0178 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:84:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isProper(NotificationEntry notificationEntry, boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        GroupEntry groupEntry;
        NotificationEntry notificationEntry2;
        NotificationInterruptStateProviderImpl notificationInterruptStateProviderImpl = (NotificationInterruptStateProviderImpl) this.mInterruptionStateProvider;
        boolean canHeadsUpCommonForFrontCoverScreen = notificationInterruptStateProviderImpl.canHeadsUpCommonForFrontCoverScreen(notificationEntry);
        String str = notificationEntry.mKey;
        if (canHeadsUpCommonForFrontCoverScreen) {
            if (!panelsEnabled()) {
                AbstractC0000x2c234b15.m3m("No heads up: disabled panel : ", str, "S.S.N.");
            } else if (!this.mController.shouldFilterOut(notificationEntry)) {
                if (NotiRune.NOTI_SUBSCREEN_CHILD_TO_RECEIVE_PARENT_ALERT) {
                    if (notificationEntry.mSbn.getNotification().getGroupAlertBehavior() == 1 && (groupEntry = notificationEntry.mGroupEntry) != null && (notificationEntry2 = groupEntry.mLogicalSummary) != null && notificationInterruptStateProviderImpl.canHeadsUpCommonForFrontCoverScreen(notificationEntry2) && (notificationEntry2.mSbn.getNotification().getGroupAlertBehavior() == 1 || notificationEntry2.mSbn.getNotification().getGroupAlertBehavior() == 0)) {
                        Log.d("S.S.N.", "alertOverride : summary - " + notificationEntry2.mKey + " child - " + str);
                        z4 = true;
                    } else {
                        AbstractC0000x2c234b15.m3m("can't be alert because suppressAlertingDueToGrouping", str, "S.S.N.");
                        z4 = false;
                    }
                }
                if (!notificationEntry.mSbn.isGroup() || !notificationEntry.mSbn.getNotification().suppressAlertingDueToGrouping()) {
                    if (z) {
                        Notification notification2 = notificationEntry.mSbn.getNotification();
                        if (notificationEntry.interruption) {
                        }
                    }
                    z2 = true;
                    if (z2) {
                        if ((NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION && notificationEntry.mIsGhost) ? false : true) {
                            z3 = true;
                            boolean z5 = !notificationEntry.mSbn.getNotification().isGroupSummary();
                            boolean z6 = notificationEntry.mSbn.getNotification().visibility == -1;
                            boolean z7 = (notificationEntry.mSbn.getNotification().semFlags & 256) != 0;
                            SemEdgeManager semEdgeManager = this.mEdgeManager;
                            Boolean valueOf = semEdgeManager != null ? Boolean.valueOf(semEdgeManager.isPackageEnabled(notificationEntry.mSbn.getPackageName(), -2)) : null;
                            Intrinsics.checkNotNull(valueOf);
                            boolean booleanValue = valueOf.booleanValue();
                            if (notificationEntry.getImportance() >= 4) {
                                String str2 = !booleanValue ? ":!isActivatedPackage:" : "";
                                if (!z3) {
                                    str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str2, ":!needsAlert:");
                                }
                                if (!z5) {
                                    str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str2, ":!isNotSummary:");
                                }
                                if (z6) {
                                    str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str2, ":isSecret:");
                                }
                                if (z7) {
                                    str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str2, ":isDisabledByApp:");
                                }
                                if (!this.mIsFolded) {
                                    str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str2, ":!isFolded:");
                                }
                                if (!this.mIsCovered) {
                                    str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str2, ":!isCovered:");
                                }
                                if (NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION && notificationEntry.mIsGhost) {
                                    str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str2, ":isGhost:");
                                }
                                if (!(str2.length() == 0)) {
                                    String m15m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str, " - ", str2);
                                    LogLevel logLevel = LogLevel.DEBUG;
                                    SubscreenDeviceModelParent$logReason$2 subscreenDeviceModelParent$logReason$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$logReason$2
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj) {
                                            return KeyAttributes$$ExternalSyntheticOutline0.m21m(" isNotProper : ", ((LogMessage) obj).getStr1());
                                        }
                                    };
                                    LogBuffer logBuffer = this.mBuffer;
                                    LogMessage obtain = logBuffer.obtain("S.S.N.", logLevel, subscreenDeviceModelParent$logReason$2, null);
                                    obtain.setStr1(m15m);
                                    logBuffer.commit(obtain);
                                }
                            }
                            return booleanValue ? false : false;
                        }
                    }
                    z3 = false;
                    boolean z52 = !notificationEntry.mSbn.getNotification().isGroupSummary();
                    if (notificationEntry.mSbn.getNotification().visibility == -1) {
                    }
                    if ((notificationEntry.mSbn.getNotification().semFlags & 256) != 0) {
                    }
                    SemEdgeManager semEdgeManager2 = this.mEdgeManager;
                    if (semEdgeManager2 != null) {
                    }
                    Intrinsics.checkNotNull(valueOf);
                    boolean booleanValue2 = valueOf.booleanValue();
                    if (notificationEntry.getImportance() >= 4) {
                    }
                    return booleanValue2 ? false : false;
                }
            }
        }
        z2 = false;
        if (z2) {
        }
        z3 = false;
        boolean z522 = !notificationEntry.mSbn.getNotification().isGroupSummary();
        if (notificationEntry.mSbn.getNotification().visibility == -1) {
        }
        if ((notificationEntry.mSbn.getNotification().semFlags & 256) != 0) {
        }
        SemEdgeManager semEdgeManager22 = this.mEdgeManager;
        if (semEdgeManager22 != null) {
        }
        Intrinsics.checkNotNull(valueOf);
        boolean booleanValue22 = valueOf.booleanValue();
        if (notificationEntry.getImportance() >= 4) {
        }
        if (booleanValue22) {
        }
    }

    public boolean isRunOnCoverAvailable() {
        return true;
    }

    public boolean isSamsungAccountLoggedIn() {
        return false;
    }

    public boolean isShowNotificationAppIcon() {
        return true;
    }

    public boolean isShowingRemoteView(String str) {
        return false;
    }

    public final boolean isShownDetail() {
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if (subscreenSubRoomNotification == null || subscreenSubRoomNotification == null) {
            return false;
        }
        return subscreenSubRoomNotification.mIsShownDetail;
    }

    public final boolean isShownGroup() {
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if (subscreenSubRoomNotification == null || subscreenSubRoomNotification == null) {
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

    public boolean isUpdatedRemoteView(String str) {
        return false;
    }

    public boolean launchApp(NotificationEntry notificationEntry) {
        return false;
    }

    public boolean launchFullscreenIntent(NotificationEntry notificationEntry) {
        return false;
    }

    public final void makeSubScreenNotification(NotificationEntry notificationEntry) {
        FrameLayout frameLayout;
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        boolean z = expandableNotificationRow != null && (expandableNotificationRow.mIsCustomNotification || expandableNotificationRow.mIsCustomBigNotification || expandableNotificationRow.mIsCustomHeadsUpNotification || expandableNotificationRow.mIsCustomPublicNotification);
        boolean needsRedaction = expandableNotificationRow.needsRedaction();
        boolean z2 = notificationEntry.mSbn.getNotification().publicVersion != null;
        NotificationEntry notificationEntry2 = this.currentPopupViewEntry;
        String str = notificationEntry2 != null ? notificationEntry2.mKey : null;
        NotificationEntry notificationEntry3 = this.currentPresentationEntry;
        StringBuilder m87m = AbstractC0866xb1ce8deb.m87m(" MAKE DETAIL : exist Parent- PopupView: ", str, " Presentation: ", notificationEntry3 != null ? notificationEntry3.mKey : null, " entry key - ");
        String str2 = notificationEntry.mKey;
        m87m.append(str2);
        m87m.append(" isCustom - ");
        m87m.append(z);
        m87m.append(" needsRedaction - ");
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(m87m, needsRedaction, " hasPublic - ", z2, "S.S.N.");
        PowerManager powerManager = this.mPowerManager;
        Boolean valueOf = powerManager != null ? Boolean.valueOf(powerManager.isInteractive()) : null;
        Intrinsics.checkNotNull(valueOf);
        boolean booleanValue = valueOf.booleanValue();
        boolean z3 = this.mFullScreenIntentEntries.get(str2) != null;
        if (!booleanValue || z3) {
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
        sb.append(booleanValue);
        sb.append(" currentPopupViewEntry - ");
        sb.append(str3);
        sb.append(" currentPresentationEntry - ");
        sb.append(str4);
        sb.append(" notiPopupType - ");
        sb.append(i);
        sb.append(" fullScreenNoti - ");
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, z3, "S.S.N.");
        if (this.popupViewShowing && this.mNotiPopupType == 2) {
            SubscreenNotificationTemplate subscreenNotificationTemplate = this.popupViewNotiTemplate;
            if (subscreenNotificationTemplate != null && (frameLayout = subscreenNotificationTemplate.mLayout) != null) {
                frameLayout.removeAllViews();
            }
            SubscreenNotificationTemplate subscreenNotificationTemplate2 = this.popupViewNotiTemplate;
            if (subscreenNotificationTemplate2 != null) {
                subscreenNotificationTemplate2.makeView(this.currentPopupViewEntry, true);
                return;
            }
            return;
        }
        if (this.mNotiPopupType == 2) {
            SubscreenNotificationDetail subscreenNotificationDetail = new SubscreenNotificationDetail(getMDisplayContext());
            subscreenNotificationDetail.makeView(this.currentPopupViewEntry, true);
            this.popupViewNotiTemplate = subscreenNotificationDetail;
        }
        if (this.mNotiPopupType == 1) {
            SubscreenNotificationDetail subscreenNotificationDetail2 = new SubscreenNotificationDetail(getMDisplayContext());
            subscreenNotificationDetail2.makeView(this.currentPresentationEntry, false);
            this.presentationNotiTemplate = subscreenNotificationDetail2;
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
        Integer num = null;
        Integer valueOf = (subscreenSubRoomNotification2 == null || (subscreenNotificationInfoManager2 = subscreenSubRoomNotification2.mNotificationInfoManager) == null) ? null : Integer.valueOf(subscreenNotificationInfoManager2.removeGroupDataArrayItem(notificationEntry));
        Intrinsics.checkNotNull(valueOf);
        int intValue = valueOf.intValue();
        if (intValue > -1 && (subscreenSubRoomNotification = this.mSubRoomNotification) != null && (subscreenNotificationGroupAdapter = subscreenSubRoomNotification.mNotificationGroupAdapter) != null) {
            subscreenNotificationGroupAdapter.notifyItemRemoved(intValue);
        }
        Log.d("S.S.N.", "notifyGroupAdapterItemRemoved parent - Entry  : " + notificationEntry.mKey + ", index :" + intValue + ", isLaunchApp :" + isLaunchApp(notificationEntry) + ", mMainViewAnimator :" + this.mMainViewAnimator);
        if (!isShownDetail()) {
            SubscreenSubRoomNotification subscreenSubRoomNotification3 = this.mSubRoomNotification;
            if (subscreenSubRoomNotification3 != null && (subscreenNotificationInfoManager = subscreenSubRoomNotification3.mNotificationInfoManager) != null) {
                num = Integer.valueOf(subscreenNotificationInfoManager.getGroupDataArraySize());
            }
            Intrinsics.checkNotNull(num);
            if (num.intValue() <= 1) {
                hideGroupNotification();
            }
        }
        return intValue;
    }

    public final int notifyListAdapterItemRemoved(NotificationEntry notificationEntry) {
        SubscreenNotificationListAdapter subscreenNotificationListAdapter;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        Integer valueOf = (subscreenSubRoomNotification == null || subscreenSubRoomNotification.mNotificationInfoManager == null) ? null : Integer.valueOf(SubscreenNotificationInfoManager.removeLockscreenNotificationInfoItem(notificationEntry));
        Log.d("S.S.N.", "notifyListAdapterItemRemoved parent - Entry  : " + notificationEntry.mKey + ", index :" + valueOf);
        if (valueOf == null || valueOf.intValue() <= -1) {
            return -1;
        }
        SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
        if (subscreenSubRoomNotification2 != null && (subscreenNotificationListAdapter = subscreenSubRoomNotification2.mNotificationListAdapter) != null) {
            subscreenNotificationListAdapter.notifyItemRemoved(valueOf.intValue());
        }
        return valueOf.intValue();
    }

    public boolean panelsEnabled() {
        return true;
    }

    public final void putMainListArrayHashMap(NotificationEntry notificationEntry) {
        SubscreenNotificationInfoManager subscreenNotificationInfoManager;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        SubscreenNotificationInfo createItemsData = (subscreenSubRoomNotification == null || (subscreenNotificationInfoManager = subscreenSubRoomNotification.mNotificationInfoManager) == null) ? null : subscreenNotificationInfoManager.createItemsData(notificationEntry.row);
        MainListHashMapItem mainListHashMapItem = new MainListHashMapItem();
        if (createItemsData != null) {
            mainListHashMapItem.mEntry = notificationEntry;
            mainListHashMapItem.mInfo = createItemsData;
        }
        this.mMainListArrayHashMap.put(notificationEntry.mKey, mainListHashMapItem);
    }

    public final void removeMainHashItem(NotificationEntry notificationEntry) {
        HashSet hashSet = this.mNotiKeySet;
        String str = notificationEntry.mKey;
        hashSet.remove(str);
        this.mMainListArrayHashMap.remove(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r10v4 */
    public void setContentViewItem(Context context, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        int i;
        int detailAdapterContentViewResource = getDetailAdapterContentViewResource();
        SubscreenNotificationInfo subscreenNotificationInfo = itemViewHolder.mInfo;
        boolean z = subscreenNotificationInfo.mIsMessagingStyle;
        int i2 = R.id.detail_clock;
        int i3 = R.id.detail_content_image;
        int i4 = R.id.detail_content_text;
        int i5 = 8;
        ?? r10 = 0;
        LinearLayout linearLayout = itemViewHolder.mContentLayout;
        if (!z) {
            View inflate = LayoutInflater.from(context).inflate(detailAdapterContentViewResource, (ViewGroup) linearLayout, false);
            TextView textView = (TextView) inflate.findViewById(R.id.detail_content_text);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.detail_content_image);
            DateTimeView findViewById = inflate.findViewById(R.id.detail_clock);
            SubscreenNotificationInfo subscreenNotificationInfo2 = itemViewHolder.mInfo;
            String str = subscreenNotificationInfo2.mBigText;
            if (str == null) {
                str = subscreenNotificationInfo2.mContent;
            }
            if (str == null || StringsKt__StringsJVMKt.isBlank(str)) {
                textView.setVisibility(8);
                i = 0;
            } else {
                textView.setText(str);
                i = 0;
                textView.setVisibility(0);
            }
            if (itemViewHolder.mInfo.mBitmap != null) {
                imageView.setVisibility(i);
                imageView.setScaleType(getDetailContentImageScaleType());
                bindImageBitmap(imageView, itemViewHolder.mInfo.mBitmap);
            } else {
                imageView.setVisibility(8);
            }
            SubscreenNotificationInfo subscreenNotificationInfo3 = itemViewHolder.mInfo;
            if (subscreenNotificationInfo3.mWhen <= 0 || !subscreenNotificationInfo3.mShowWhen) {
                findViewById.setVisibility(8);
            } else {
                findViewById.setVisibility(0);
                findViewById.setTime(itemViewHolder.mInfo.mWhen);
            }
            linearLayout.addView(inflate);
            itemViewHolder.mBodyLayoutString = itemViewHolder.mBodyLayoutString + ((Object) textView.getText()) + ((Object) findViewById.getText());
            return;
        }
        ArrayList arrayList = subscreenNotificationInfo.mMessageingStyleInfoArray;
        int size = arrayList.size();
        int i6 = 0;
        while (i6 < size) {
            View inflate2 = LayoutInflater.from(context).inflate(detailAdapterContentViewResource, linearLayout, (boolean) r10);
            TextView textView2 = (TextView) inflate2.findViewById(R.id.detail_sender_text);
            TextView textView3 = (TextView) inflate2.findViewById(i4);
            ImageView imageView2 = (ImageView) inflate2.findViewById(i3);
            DateTimeView findViewById2 = inflate2.findViewById(i2);
            if (itemViewHolder.mInfo.mIsGroupConversation) {
                textView2.setVisibility(r10);
                textView2.setText(((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i6)).mSender + " : ");
            } else {
                String str2 = ((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i6)).mSender;
                String str3 = itemViewHolder.mPrevSender;
                if (str3 == null || Intrinsics.areEqual(str3, str2)) {
                    textView2.setVisibility(i5);
                } else {
                    textView2.setVisibility(r10);
                    textView2.setText(((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i6)).mSender + " : ");
                }
                itemViewHolder.mPrevSender = str2;
            }
            textView3.setText(((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i6)).mContentText);
            if (((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i6)).mUriImage != null) {
                try {
                    imageView2.setImageDrawable(((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i6)).mUriImage);
                    imageView2.setVisibility(0);
                } catch (SecurityException e) {
                    SubscreenNotificationInfo subscreenNotificationInfo4 = itemViewHolder.mInfo;
                    Log.w("SubscreenNotificationDetailAdapter", "SecurityException: " + e + "appName : " + subscreenNotificationInfo4.mAppName + "packageName : " + subscreenNotificationInfo4.mSbn.getPackageName());
                    imageView2.setImageURI(null);
                    imageView2.setVisibility(8);
                }
            } else {
                imageView2.setVisibility(i5);
            }
            long j = ((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i6)).mTimeStamp;
            if (j <= 0 || !itemViewHolder.mInfo.mShowWhen) {
                findViewById2.setVisibility(8);
            } else {
                findViewById2.setVisibility(0);
                findViewById2.setTime(j);
            }
            linearLayout.addView(inflate2);
            if (textView2.getVisibility() == 0) {
                itemViewHolder.mBodyLayoutString = itemViewHolder.mBodyLayoutString + ((Object) textView2.getText());
            }
            itemViewHolder.mBodyLayoutString = itemViewHolder.mBodyLayoutString + ((Object) textView3.getText()) + ((Object) findViewById2.getText());
            i6++;
            i2 = R.id.detail_clock;
            i3 = R.id.detail_content_image;
            i4 = R.id.detail_content_text;
            i5 = 8;
            r10 = 0;
        }
    }

    public void setDetailAdapterItemHolderButtonContentDescription(SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        String string = subscreenNotificationDetailAdapter.mContext.getResources().getString(R.string.subscreen_detail_adapter_open_app_button_text);
        String string2 = subscreenNotificationDetailAdapter.mContext.getResources().getString(R.string.clear_all_text);
        itemViewHolder.mOpenAppButton.setContentDescription(string);
        itemViewHolder.mClearButton.setContentDescription(string2);
        itemViewHolder.mBodyLayout.setContentDescription(itemViewHolder.mBodyLayoutString);
    }

    public void setDetailAdapterTextHolderButtonContentDescription(SubscreenNotificationDetailAdapter.TextViewHolder textViewHolder, SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter) {
        textViewHolder.mOpenAppButton.setContentDescription(subscreenNotificationDetailAdapter.mContext.getResources().getString(R.string.subscreen_detail_adapter_open_app_button_text));
    }

    public void setQuickReplyFocusBackground(View view) {
        view.setBackground(null);
    }

    public PopupWindow showReplyButtonViewPopupWindow(View view, View view2) {
        return null;
    }

    public final void showSubscreenNotification() {
        Runnable runnable;
        NotificationEntry notificationEntry;
        ViewParent parent;
        if (this.mSubDisplay == null) {
            Log.d("S.S.N.", "return showSubscreenNotification - subDisplay does not exist");
            return;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("showSubscreenNotification Parent - ", this.mNotiPopupType, "S.S.N.");
        if (this.mNotiPopupType == 2) {
            if (this.presentationShowing && isDismissiblePopup()) {
                Log.d("S.S.N.", "showSubscreenNotification PopupView - dismiss top presentation if it's showing");
                dismissImmediately(1);
            }
            WindowManager.LayoutParams topPopupLp = getTopPopupLp();
            Display display = this.mSubDisplay;
            if (display != null && topPopupLp != null) {
                topPopupLp.width = display.getWidth();
            }
            SubscreenNotificationTemplate subscreenNotificationTemplate = this.popupViewNotiTemplate;
            if (subscreenNotificationTemplate != null) {
                if (!this.popupViewShowing || this.mNotiPopupView == null) {
                    FrameLayout frameLayout = subscreenNotificationTemplate.mLayout;
                    if (frameLayout != null && (parent = frameLayout.getParent()) != null) {
                        ((ViewGroup) parent).removeView(subscreenNotificationTemplate.mLayout);
                    }
                    WindowManager windowManager = this.mWindowManager;
                    if (windowManager != null) {
                        windowManager.addView(subscreenNotificationTemplate.mLayout, topPopupLp);
                    }
                    this.popupViewShowing = true;
                    this.mNotiPopupView = subscreenNotificationTemplate.mLayout;
                    NotificationEntry notificationEntry2 = this.currentPopupViewEntry;
                    AbstractC0000x2c234b15.m3m("  Noti popup attached - ", notificationEntry2 != null ? notificationEntry2.mKey : null, "S.S.N.");
                    Animator popUpViewShowAnimator = getPopUpViewShowAnimator(this.mNotiPopupView);
                    if (popUpViewShowAnimator != null) {
                        popUpViewShowAnimator.start();
                    }
                } else {
                    WindowManager windowManager2 = this.mWindowManager;
                    if (windowManager2 != null) {
                        windowManager2.updateViewLayout(subscreenNotificationTemplate.mLayout, topPopupLp);
                    }
                    NotificationEntry notificationEntry3 = this.currentPopupViewEntry;
                    AbstractC0000x2c234b15.m3m("  Noti popup updated - ", notificationEntry3 != null ? notificationEntry3.mKey : null, "S.S.N.");
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
            if (this.mPresentation == null) {
                NotificationEntry notificationEntry4 = this.currentPresentationEntry;
                Log.d("S.S.N.", "  SHOW NEW - " + (notificationEntry4 != null ? notificationEntry4.mKey : null));
                Context mDisplayContext = getMDisplayContext();
                Display display2 = this.mSubDisplay;
                SubscreenNotificationDetail subscreenNotificationDetail = this.presentationNotiTemplate;
                SubscreenNotificationPresentation subscreenNotificationPresentation = new SubscreenNotificationPresentation(mDisplayContext, display2, subscreenNotificationDetail != null ? subscreenNotificationDetail.mLayout : null, this);
                this.mPresentation = subscreenNotificationPresentation;
                try {
                    subscreenNotificationPresentation.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$showSubscreenNotification$3
                        @Override // android.content.DialogInterface.OnShowListener
                        public final void onShow(DialogInterface dialogInterface) {
                            StatusBarNotificationActivityStarter statusBarNotificationActivityStarter;
                            NotificationEntry notificationEntry5;
                            SubscreenDeviceModelParent subscreenDeviceModelParent = SubscreenDeviceModelParent.this;
                            LinkedHashMap linkedHashMap = subscreenDeviceModelParent.mFullScreenIntentEntries;
                            NotificationEntry notificationEntry6 = subscreenDeviceModelParent.currentPresentationEntry;
                            subscreenDeviceModelParent.updateWakeLock(true, linkedHashMap.containsKey(notificationEntry6 != null ? notificationEntry6.mKey : null));
                            NotificationActivityStarter notificationActivityStarter = SubscreenDeviceModelParent.this.mNotificationActivityStarter;
                            if (notificationActivityStarter == null || (notificationEntry5 = (statusBarNotificationActivityStarter = (StatusBarNotificationActivityStarter) notificationActivityStarter).mPendingFullscreenEntry) == null) {
                                return;
                            }
                            if (new ArrayList(Arrays.asList("com.tencent.mm", "us.zoom.videomeetings")).contains(notificationEntry5.mSbn.getPackageName())) {
                                return;
                            }
                            if (statusBarNotificationActivityStarter.mPendingFullscreenEntry.mSbn.getNotification().fullScreenIntent == null) {
                                ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("return launchFullScreenIntent() - fullScreenIntent is null: "), statusBarNotificationActivityStarter.mPendingFullscreenEntry.mKey, "StatusBarNotificationActivityStarter");
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
                SubscreenNotificationDetail subscreenNotificationDetail2 = this.presentationNotiTemplate;
                final FrameLayout frameLayout2 = subscreenNotificationDetail2 != null ? subscreenNotificationDetail2.mLayout : null;
                NotificationEntry notificationEntry5 = this.currentPresentationEntry;
                AbstractC0000x2c234b15.m3m(" SHOW UPDATED - ", notificationEntry5 != null ? notificationEntry5.mKey : null, "S.S.N.");
                if (this.mPresentation != null) {
                    handler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelParent$updateSubscreenNotificationView$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ViewGroup viewGroup;
                            SubscreenNotificationPresentation subscreenNotificationPresentation4 = SubscreenDeviceModelParent.this.mPresentation;
                            ViewGroup viewGroup2 = subscreenNotificationPresentation4 != null ? subscreenNotificationPresentation4.contents : null;
                            if (viewGroup2 != null && viewGroup2.getChildCount() > 0) {
                                viewGroup2.removeAllViews();
                            }
                            SubscreenNotificationPresentation subscreenNotificationPresentation5 = SubscreenDeviceModelParent.this.mPresentation;
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
            LinkedHashMap linkedHashMap = this.mFullScreenIntentEntries;
            String str = notificationEntry.mKey;
            long j = 300000;
            if (linkedHashMap.get(str) != null) {
                if (notificationEntry.mFullscreenPopUpStartTime != 0) {
                    j = 300000 - (System.currentTimeMillis() - notificationEntry.mFullscreenPopUpStartTime);
                } else {
                    notificationEntry.mFullscreenPopUpStartTime = System.currentTimeMillis();
                }
            }
            Intrinsics.checkNotNull(runnable);
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, linkedHashMap.get(str) != null ? j : 3000L);
            if (linkedHashMap.get(str) == null) {
                j = 3000;
            }
            Log.d("S.S.N.", "  showSubscreenNotification - " + str + ", " + j);
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
        ExifInterface$$ExternalSyntheticOutline0.m35m(sb, str, "S.S.N.");
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
        Intrinsics.checkNotNull(users);
        for (UserInfo userInfo : users) {
            if (userInfo.isBMode()) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("update bModeUserId : ", userInfo.id, "S.S.N.");
                this.bModeUserId = userInfo.id;
                return;
            }
        }
    }

    public final void updateIconColor(ImageView imageView, NotificationEntry notificationEntry) {
        StatusBarNotification statusBarNotification;
        Notification notification2;
        StatusBarNotification statusBarNotification2;
        Notification notification3;
        ExpandableNotificationRow expandableNotificationRow;
        boolean z = false;
        int appPrimaryColor = (notificationEntry == null || (expandableNotificationRow = notificationEntry.row) == null) ? 0 : ((NotificationColorPicker) Dependency.get(NotificationColorPicker.class)).getAppPrimaryColor(expandableNotificationRow);
        boolean isGrayScaleIcon = isGrayScaleIcon(notificationEntry);
        Log.d("S.S.N.", "updateIconColor() isGrayScale = " + isGrayScaleIcon + ", " + (notificationEntry != null ? notificationEntry.mKey : null));
        if (imageView != null) {
            Context context = this.mContext;
            if (!isGrayScaleIcon) {
                imageView.setColorFilter(0);
                if (imageView.getBackground() != null) {
                    int color = context.getColor(R.color.notification_non_grayscale_border_color);
                    int color2 = context.getColor(R.color.notification_non_grayscale_fill_color);
                    imageView.getBackground().setColorFilter(null);
                    GradientDrawable gradientDrawable = (GradientDrawable) imageView.getBackground().mutate();
                    gradientDrawable.setColor(color2);
                    gradientDrawable.setStroke(context.getResources().getDimensionPixelSize(R.dimen.notification_icon_border_width), color);
                    return;
                }
                return;
            }
            int color3 = context.getColor(R.color.notification_app_icon_color);
            if (context.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
                imageView.setColorFilter(Color.argb(255, Color.red(color3), Color.green(color3), Color.blue(color3)), PorterDuff.Mode.SRC_IN);
            } else {
                if ((notificationEntry == null || (statusBarNotification = notificationEntry.mSbn) == null || (notification2 = statusBarNotification.getNotification()) == null || notification2.isColorized()) ? false : true) {
                    imageView.setColorFilter(color3, PorterDuff.Mode.SRC_IN);
                }
            }
            if (imageView.getBackground() != null) {
                if (notificationEntry != null && (statusBarNotification2 = notificationEntry.mSbn) != null && (notification3 = statusBarNotification2.getNotification()) != null && notification3.isColorized()) {
                    z = true;
                }
                if (z) {
                    Log.d("S.S.N.", "updateIconColor() - colorized " + (notificationEntry != null ? notificationEntry.mKey : null));
                    NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.get(NotificationColorPicker.class);
                    appPrimaryColor = notificationColorPicker.resolveContrastColor(notificationColorPicker.getNotificationDefaultBgColor(), context.getResources().getConfiguration().isNightModeActive(), notificationEntry != null ? notificationEntry.row : null);
                }
                imageView.getBackground().setColorFilter(appPrimaryColor, PorterDuff.Mode.SRC_IN);
            }
        }
    }

    public final void updateNotiShowBlocked() {
        SettingsHelper settingsHelper = this.mSettingsHelper;
        settingsHelper.getClass();
        boolean z = NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON;
        this.notiShowBlocked = !(!(z || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) || settingsHelper.mItemLists.get("cover_screen_show_notification").getIntValue() == 1);
        this.notiFullPopupBlocked = !(((z || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) && settingsHelper.mItemLists.get("turn_on_cover_screen_for_notification").getIntValue() == 0) ? false : true);
    }

    public final void updateSmallIconSquircleBg(ImageView imageView, boolean z, boolean z2) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setCornerRadius(squircleRadius(z, z2));
        int smallIconPadding = smallIconPadding(z, z2, false);
        if (imageView != null) {
            imageView.setBackground(gradientDrawable);
            imageView.setPadding(smallIconPadding, smallIconPadding, smallIconPadding, smallIconPadding);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x001f, code lost:
    
        if (r1 == true) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateWakeLock(boolean z, boolean z2) {
        SettableWakeLock settableWakeLock;
        SettableWakeLock settableWakeLock2;
        EmergencyButtonController$$ExternalSyntheticOutline0.m59m(" updateWakeLock - acquire : ", z, ", force : ", z2, "S.S.N.");
        boolean z3 = true;
        if (z) {
            if (!z2 || (settableWakeLock2 = this.mScreenOnwakelock) == null) {
                return;
            }
            settableWakeLock2.setAcquired(true);
            return;
        }
        SettableWakeLock settableWakeLock3 = this.mScreenOnwakelock;
        if (settableWakeLock3 != null) {
            synchronized (settableWakeLock3) {
                boolean z4 = settableWakeLock3.mAcquired;
            }
        }
        z3 = false;
        if (!z3 || (settableWakeLock = this.mScreenOnwakelock) == null) {
            return;
        }
        settableWakeLock.setAcquired(false);
    }

    public boolean useTopPresentation() {
        return false;
    }

    public void initDetailAdapterTextViewHolder(SubscreenNotificationDetailAdapter.TextViewHolder textViewHolder) {
    }

    public void initMainHeaderView(LinearLayout linearLayout) {
    }

    public void onBindDetailAdapterTextViewHolder(SubscreenNotificationDetailAdapter.TextViewHolder textViewHolder) {
    }

    public void onStateChangedInDeviceStateCallback(int i) {
    }

    public void removeSmartReplyHashMap(String str) {
    }

    public void replyActivityFinished(boolean z) {
    }

    public void setDimOnMainBackground(View view) {
    }

    public void setFullPopupWindowKeyEventListener(FrameLayout frameLayout) {
    }

    public void setItemDecoration(SubscreenRecyclerView subscreenRecyclerView) {
    }

    public void setKeyguardStateWhenAddLockscreenNotificationInfoArray(boolean z) {
    }

    public void setListAdpaterFirstChildTopMargin(SubscreenParentItemViewHolder subscreenParentItemViewHolder) {
    }

    public void setListAdpaterPosition(int i) {
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

    public void enableGoToTopButton() {
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

    public void clickAdapterItem(Context context, SubscreenParentItemViewHolder subscreenParentItemViewHolder) {
    }

    public void onBindDetailAdapterItemViewHolder(SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
    }

    public void setClock(SubscreenNotificationInfo subscreenNotificationInfo, View view) {
    }

    public void setGroupAdapterFooterMargin(Context context, RecyclerView.ViewHolder viewHolder) {
    }

    public void setListItemTextLayout(Context context, View view) {
    }

    public void setReplyWordTextStyle(TextView textView, Typeface typeface) {
    }

    public void updateIconContainer(View view, boolean z) {
    }

    public void updateImportBadgeIconRing(View view, boolean z) {
    }

    public void updateShadowIconColor(View view, NotificationEntry notificationEntry) {
    }

    public final void dismissImmediately(NotificationEntry notificationEntry) {
        boolean z = this.popupViewShowing;
        boolean z2 = this.presentationShowing;
        NotificationEntry notificationEntry2 = this.currentPopupViewEntry;
        String str = notificationEntry2 != null ? notificationEntry2.mKey : null;
        NotificationEntry notificationEntry3 = this.currentPresentationEntry;
        String str2 = notificationEntry3 != null ? notificationEntry3.mKey : null;
        String str3 = notificationEntry != null ? notificationEntry.mKey : null;
        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m(" DISMISS IMMEDIATELY(entry) - popupViewShowing : ", z, ", presentationShowing : ", z2, ", currentPopupViewEntry : ");
        AppOpItem$$ExternalSyntheticOutline0.m97m(m69m, str, ", currentPresentationEntry : ", str2, ", key : ");
        ExifInterface$$ExternalSyntheticOutline0.m35m(m69m, str3, "S.S.N.");
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

    public void initMainHeaderViewItems(Context context, SubscreenNotificationInfo subscreenNotificationInfo, boolean z) {
    }

    public void setGroupAdapterIcon(Context context, SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter, SubscreenNotificationGroupAdapter.NotificationGroupItemViewHolder notificationGroupItemViewHolder) {
    }

    public void setRightIcon(Context context, SubscreenNotificationInfo subscreenNotificationInfo, View view) {
    }

    public void setSmartReplyResultValue(int i, StringBuilder sb, String str) {
    }

    public void makePopupDetailView(Context context, NotificationEntry notificationEntry, boolean z, FrameLayout frameLayout) {
    }

    public void moveDetailAdapterContentScroll(View view, boolean z, boolean z2, boolean z3) {
    }

    public void updateSmallIconBg(ImageView imageView, boolean z, boolean z2, boolean z3) {
    }
}
