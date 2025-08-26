package com.android.systemui.statusbar.notification.row;

import android.animation.TimeInterpolator;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import com.android.app.animation.Interpolators;
import com.android.settingslib.Utils;
import com.android.settingslib.notification.ConversationIconFactory;
import com.android.systemui.R;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.people.widget.PeopleSpaceWidgetPinnedReceiver;
import com.android.systemui.people.widget.PeopleSpaceWidgetProvider;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.notification.NmSummarizationUiFlag;
import com.android.systemui.statusbar.notification.NotificationChannelHelper;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.wmshell.BubblesManager;
import java.util.Optional;

/* loaded from: classes3.dex */
public class NotificationConversationInfo extends LinearLayout implements NotificationGuts.GutsContent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mActualHeight;
    public int mAppBubble;
    public String mAppName;
    public int mAppUid;
    public Handler mBgHandler;
    public Notification.BubbleMetadata mBubbleMetadata;
    public Optional mBubblesManagerOptional;
    public TextView mDefaultDescriptionView;
    public String mDelegatePkg;
    public NotificationEntry mEntry;
    public ViewCompat$$ExternalSyntheticLambda0 mFeedbackClickListener;
    public NotificationGuts mGutsContainer;
    public INotificationManager mINotificationManager;
    public ConversationIconFactory mIconFactory;
    public boolean mIsDeviceProvisioned;
    public Handler mMainHandler;
    public NotificationChannel mNotificationChannel;
    public final NotificationConversationInfo$$ExternalSyntheticLambda0 mOnDefaultClick;
    public final NotificationConversationInfo$$ExternalSyntheticLambda0 mOnDone;
    public final NotificationConversationInfo$$ExternalSyntheticLambda0 mOnFavoriteClick;
    public final NotificationConversationInfo$$ExternalSyntheticLambda0 mOnMuteClick;
    public NotificationGutsManager$$ExternalSyntheticLambda1 mOnSettingsClickListener;
    public OnUserInteractionCallback mOnUserInteractionCallback;
    public String mPackageName;
    public PeopleSpaceWidgetManager mPeopleSpaceWidgetManager;
    public PackageManager mPm;
    public boolean mPressedApply;
    public TextView mPriorityDescriptionView;
    public StatusBarNotification mSbn;
    public int mSelectedAction;
    public ShadeController mShadeController;
    public ShortcutInfo mShortcutInfo;
    public TextView mSilentDescriptionView;
    boolean mSkipPost;
    public UserManager mUm;

    public class UpdateChannelRunnable implements Runnable {
        public final int mAction;
        public final String mAppPkg;
        public final int mAppUid;
        public final NotificationChannel mChannelToUpdate;
        public final INotificationManager mINotificationManager;

        public UpdateChannelRunnable(INotificationManager iNotificationManager, String str, int i, int i2, NotificationChannel notificationChannel) {
            this.mINotificationManager = iNotificationManager;
            this.mAppPkg = str;
            this.mAppUid = i;
            this.mChannelToUpdate = notificationChannel;
            this.mAction = i2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            int i = 1;
            try {
                int i2 = this.mAction;
                if (i2 == 0) {
                    NotificationChannel notificationChannel = this.mChannelToUpdate;
                    notificationChannel.setImportance(Math.max(notificationChannel.getOriginalImportance(), 3));
                    if (this.mChannelToUpdate.isImportantConversation()) {
                        this.mChannelToUpdate.setImportantConversation(false);
                        this.mChannelToUpdate.setAllowBubbles(false);
                    }
                } else if (i2 == 2) {
                    this.mChannelToUpdate.setImportantConversation(true);
                    if (this.mChannelToUpdate.isImportantConversation()) {
                        this.mChannelToUpdate.setAllowBubbles(true);
                        if (NotificationConversationInfo.this.mAppBubble == 0) {
                            this.mINotificationManager.setBubblesAllowed(this.mAppPkg, this.mAppUid, 2);
                        }
                        if (NotificationConversationInfo.this.mBubblesManagerOptional.isPresent()) {
                            int i3 = NotificationBundleUi.$r8$clinit;
                            NotificationConversationInfo.this.post(new NotificationConversationInfo$$ExternalSyntheticLambda4(this, i));
                        }
                    }
                    NotificationChannel notificationChannel2 = this.mChannelToUpdate;
                    notificationChannel2.setImportance(Math.max(notificationChannel2.getOriginalImportance(), 3));
                } else if (i2 == 4) {
                    if (this.mChannelToUpdate.getImportance() == -1000 || this.mChannelToUpdate.getImportance() >= 3) {
                        this.mChannelToUpdate.setImportance(2);
                    }
                    if (this.mChannelToUpdate.isImportantConversation()) {
                        this.mChannelToUpdate.setImportantConversation(false);
                        this.mChannelToUpdate.setAllowBubbles(false);
                    }
                }
                this.mINotificationManager.updateNotificationChannelForPackage(this.mAppPkg, this.mAppUid, this.mChannelToUpdate);
            } catch (RemoteException e) {
                Log.e("ConversationGuts", "Unable to update notification channel", e);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda0] */
    public NotificationConversationInfo(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSelectedAction = -1;
        this.mSkipPost = false;
        final int i = 0;
        this.mOnFavoriteClick = new View.OnClickListener(this) { // from class: com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda0
            public final /* synthetic */ NotificationConversationInfo f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                NotificationConversationInfo notificationConversationInfo = this.f$0;
                switch (i2) {
                    case 0:
                        int i3 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(2);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    case 1:
                        int i4 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(0);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    case 2:
                        int i5 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(4);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    default:
                        notificationConversationInfo.mPressedApply = true;
                        if (notificationConversationInfo.mSelectedAction == 2 && notificationConversationInfo.getPriority() != notificationConversationInfo.mSelectedAction) {
                            notificationConversationInfo.mShadeController.animateCollapseShade(0);
                            if (notificationConversationInfo.mUm.isSameProfileGroup(0, notificationConversationInfo.mSbn.getNormalizedUserId())) {
                                PeopleSpaceWidgetManager peopleSpaceWidgetManager = notificationConversationInfo.mPeopleSpaceWidgetManager;
                                ShortcutInfo shortcutInfo = notificationConversationInfo.mShortcutInfo;
                                Bundle bundle = new Bundle();
                                if (!peopleSpaceWidgetManager.mAppWidgetManagerOptional.isEmpty()) {
                                    RemoteViews preview = peopleSpaceWidgetManager.getPreview(shortcutInfo.getId(), shortcutInfo.getUserHandle(), shortcutInfo.getPackage(), bundle);
                                    if (preview == null) {
                                        Log.w("PeopleSpaceWidgetMgr", "Skipping pinning widget: no tile for shortcutId: " + shortcutInfo.getId());
                                    } else {
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putParcelable("appWidgetPreview", preview);
                                        Context context2 = peopleSpaceWidgetManager.mContext;
                                        int i6 = PeopleSpaceWidgetPinnedReceiver.$r8$clinit;
                                        Intent intentAddFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(268435456);
                                        intentAddFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                        intentAddFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                        intentAddFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                        ((AppWidgetManager) peopleSpaceWidgetManager.mAppWidgetManagerOptional.get()).requestPinAppWidget(new ComponentName(peopleSpaceWidgetManager.mContext, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, intentAddFlags, 167772160));
                                    }
                                }
                            }
                        }
                        notificationConversationInfo.mGutsContainer.closeControls(view, true);
                        break;
                }
            }
        };
        final int i2 = 1;
        this.mOnDefaultClick = new View.OnClickListener(this) { // from class: com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda0
            public final /* synthetic */ NotificationConversationInfo f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i2;
                NotificationConversationInfo notificationConversationInfo = this.f$0;
                switch (i22) {
                    case 0:
                        int i3 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(2);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    case 1:
                        int i4 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(0);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    case 2:
                        int i5 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(4);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    default:
                        notificationConversationInfo.mPressedApply = true;
                        if (notificationConversationInfo.mSelectedAction == 2 && notificationConversationInfo.getPriority() != notificationConversationInfo.mSelectedAction) {
                            notificationConversationInfo.mShadeController.animateCollapseShade(0);
                            if (notificationConversationInfo.mUm.isSameProfileGroup(0, notificationConversationInfo.mSbn.getNormalizedUserId())) {
                                PeopleSpaceWidgetManager peopleSpaceWidgetManager = notificationConversationInfo.mPeopleSpaceWidgetManager;
                                ShortcutInfo shortcutInfo = notificationConversationInfo.mShortcutInfo;
                                Bundle bundle = new Bundle();
                                if (!peopleSpaceWidgetManager.mAppWidgetManagerOptional.isEmpty()) {
                                    RemoteViews preview = peopleSpaceWidgetManager.getPreview(shortcutInfo.getId(), shortcutInfo.getUserHandle(), shortcutInfo.getPackage(), bundle);
                                    if (preview == null) {
                                        Log.w("PeopleSpaceWidgetMgr", "Skipping pinning widget: no tile for shortcutId: " + shortcutInfo.getId());
                                    } else {
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putParcelable("appWidgetPreview", preview);
                                        Context context2 = peopleSpaceWidgetManager.mContext;
                                        int i6 = PeopleSpaceWidgetPinnedReceiver.$r8$clinit;
                                        Intent intentAddFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(268435456);
                                        intentAddFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                        intentAddFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                        intentAddFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                        ((AppWidgetManager) peopleSpaceWidgetManager.mAppWidgetManagerOptional.get()).requestPinAppWidget(new ComponentName(peopleSpaceWidgetManager.mContext, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, intentAddFlags, 167772160));
                                    }
                                }
                            }
                        }
                        notificationConversationInfo.mGutsContainer.closeControls(view, true);
                        break;
                }
            }
        };
        final int i3 = 2;
        this.mOnMuteClick = new View.OnClickListener(this) { // from class: com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda0
            public final /* synthetic */ NotificationConversationInfo f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i3;
                NotificationConversationInfo notificationConversationInfo = this.f$0;
                switch (i22) {
                    case 0:
                        int i32 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(2);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    case 1:
                        int i4 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(0);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    case 2:
                        int i5 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(4);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    default:
                        notificationConversationInfo.mPressedApply = true;
                        if (notificationConversationInfo.mSelectedAction == 2 && notificationConversationInfo.getPriority() != notificationConversationInfo.mSelectedAction) {
                            notificationConversationInfo.mShadeController.animateCollapseShade(0);
                            if (notificationConversationInfo.mUm.isSameProfileGroup(0, notificationConversationInfo.mSbn.getNormalizedUserId())) {
                                PeopleSpaceWidgetManager peopleSpaceWidgetManager = notificationConversationInfo.mPeopleSpaceWidgetManager;
                                ShortcutInfo shortcutInfo = notificationConversationInfo.mShortcutInfo;
                                Bundle bundle = new Bundle();
                                if (!peopleSpaceWidgetManager.mAppWidgetManagerOptional.isEmpty()) {
                                    RemoteViews preview = peopleSpaceWidgetManager.getPreview(shortcutInfo.getId(), shortcutInfo.getUserHandle(), shortcutInfo.getPackage(), bundle);
                                    if (preview == null) {
                                        Log.w("PeopleSpaceWidgetMgr", "Skipping pinning widget: no tile for shortcutId: " + shortcutInfo.getId());
                                    } else {
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putParcelable("appWidgetPreview", preview);
                                        Context context2 = peopleSpaceWidgetManager.mContext;
                                        int i6 = PeopleSpaceWidgetPinnedReceiver.$r8$clinit;
                                        Intent intentAddFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(268435456);
                                        intentAddFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                        intentAddFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                        intentAddFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                        ((AppWidgetManager) peopleSpaceWidgetManager.mAppWidgetManagerOptional.get()).requestPinAppWidget(new ComponentName(peopleSpaceWidgetManager.mContext, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, intentAddFlags, 167772160));
                                    }
                                }
                            }
                        }
                        notificationConversationInfo.mGutsContainer.closeControls(view, true);
                        break;
                }
            }
        };
        final int i4 = 3;
        this.mOnDone = new View.OnClickListener(this) { // from class: com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda0
            public final /* synthetic */ NotificationConversationInfo f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i4;
                NotificationConversationInfo notificationConversationInfo = this.f$0;
                switch (i22) {
                    case 0:
                        int i32 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(2);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    case 1:
                        int i42 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(0);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    case 2:
                        int i5 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(4);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    default:
                        notificationConversationInfo.mPressedApply = true;
                        if (notificationConversationInfo.mSelectedAction == 2 && notificationConversationInfo.getPriority() != notificationConversationInfo.mSelectedAction) {
                            notificationConversationInfo.mShadeController.animateCollapseShade(0);
                            if (notificationConversationInfo.mUm.isSameProfileGroup(0, notificationConversationInfo.mSbn.getNormalizedUserId())) {
                                PeopleSpaceWidgetManager peopleSpaceWidgetManager = notificationConversationInfo.mPeopleSpaceWidgetManager;
                                ShortcutInfo shortcutInfo = notificationConversationInfo.mShortcutInfo;
                                Bundle bundle = new Bundle();
                                if (!peopleSpaceWidgetManager.mAppWidgetManagerOptional.isEmpty()) {
                                    RemoteViews preview = peopleSpaceWidgetManager.getPreview(shortcutInfo.getId(), shortcutInfo.getUserHandle(), shortcutInfo.getPackage(), bundle);
                                    if (preview == null) {
                                        Log.w("PeopleSpaceWidgetMgr", "Skipping pinning widget: no tile for shortcutId: " + shortcutInfo.getId());
                                    } else {
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putParcelable("appWidgetPreview", preview);
                                        Context context2 = peopleSpaceWidgetManager.mContext;
                                        int i6 = PeopleSpaceWidgetPinnedReceiver.$r8$clinit;
                                        Intent intentAddFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(268435456);
                                        intentAddFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                        intentAddFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                        intentAddFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                        ((AppWidgetManager) peopleSpaceWidgetManager.mAppWidgetManagerOptional.get()).requestPinAppWidget(new ComponentName(peopleSpaceWidgetManager.mContext, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, intentAddFlags, 167772160));
                                    }
                                }
                            }
                        }
                        notificationConversationInfo.mGutsContainer.closeControls(view, true);
                        break;
                }
            }
        };
    }

    public final void bindIcon(boolean z) {
        Drawable defaultActivityIcon;
        Drawable shortcutIconDrawable = this.mIconFactory.mLauncherApps.getShortcutIconDrawable(this.mShortcutInfo, 450);
        if (shortcutIconDrawable == null) {
            shortcutIconDrawable = ((LinearLayout) this).mContext.getDrawable(R.drawable.ic_person).mutate();
            shortcutIconDrawable.setTint(((LinearLayout) this).mContext.getColor(android.R.color.secondary_text_inverse_when_activated_material));
        }
        ((ImageView) findViewById(R.id.conversation_icon)).setImageDrawable(shortcutIconDrawable);
        ImageView imageView = (ImageView) findViewById(R.id.conversation_icon_badge_icon);
        ConversationIconFactory conversationIconFactory = this.mIconFactory;
        String str = this.mPackageName;
        int userId = UserHandle.getUserId(this.mSbn.getUid());
        conversationIconFactory.getClass();
        try {
            defaultActivityIcon = Utils.getBadgedIcon(conversationIconFactory.mContext, conversationIconFactory.mPackageManager.getApplicationInfoAsUser(str, 128, userId));
        } catch (PackageManager.NameNotFoundException unused) {
            defaultActivityIcon = conversationIconFactory.mPackageManager.getDefaultActivityIcon();
        }
        imageView.setImageDrawable(defaultActivityIcon);
        findViewById(R.id.conversation_icon_badge_ring).setVisibility(z ? 0 : 8);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x00ab  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void bindNotification(PackageManager packageManager, UserManager userManager, PeopleSpaceWidgetManager peopleSpaceWidgetManager, INotificationManager iNotificationManager, OnUserInteractionCallback onUserInteractionCallback, String str, NotificationEntry notificationEntry, NotificationListenerService.Ranking ranking, StatusBarNotification statusBarNotification, NotificationGutsManager$$ExternalSyntheticLambda1 notificationGutsManager$$ExternalSyntheticLambda1, ViewCompat$$ExternalSyntheticLambda0 viewCompat$$ExternalSyntheticLambda0, ConversationIconFactory conversationIconFactory, boolean z, Handler handler, Handler handler2, Optional optional, ShadeController shadeController, boolean z2, ExpandableNotificationRow$$ExternalSyntheticLambda6 expandableNotificationRow$$ExternalSyntheticLambda6) {
        CharSequence name;
        this.mINotificationManager = iNotificationManager;
        this.mPeopleSpaceWidgetManager = peopleSpaceWidgetManager;
        this.mOnUserInteractionCallback = onUserInteractionCallback;
        this.mPackageName = str;
        this.mEntry = notificationEntry;
        this.mSbn = statusBarNotification;
        this.mPm = packageManager;
        this.mUm = userManager;
        this.mAppName = str;
        this.mOnSettingsClickListener = notificationGutsManager$$ExternalSyntheticLambda1;
        this.mNotificationChannel = ranking.getChannel();
        this.mAppUid = this.mSbn.getUid();
        this.mDelegatePkg = this.mSbn.getOpPkg();
        this.mIsDeviceProvisioned = z;
        this.mIconFactory = conversationIconFactory;
        this.mBubbleMetadata = statusBarNotification.getNotification().getBubbleMetadata();
        this.mBubblesManagerOptional = optional;
        this.mShadeController = shadeController;
        this.mMainHandler = handler;
        this.mBgHandler = handler2;
        ShortcutInfo conversationShortcutInfo = ranking.getConversationShortcutInfo();
        this.mShortcutInfo = conversationShortcutInfo;
        this.mFeedbackClickListener = viewCompat$$ExternalSyntheticLambda0;
        if (conversationShortcutInfo == null) {
            throw new IllegalArgumentException("Does not have required information");
        }
        this.mNotificationChannel = NotificationChannelHelper.createConversationChannelIfNeeded(getContext(), this.mINotificationManager, notificationEntry, this.mNotificationChannel);
        try {
            this.mAppBubble = this.mINotificationManager.getBubblePreferenceForPackage(this.mPackageName, this.mAppUid);
        } catch (RemoteException e) {
            Log.e("ConversationGuts", "can't reach OS", e);
            this.mAppBubble = 2;
        }
        ((TextView) findViewById(R.id.parent_channel_name)).setText(this.mNotificationChannel.getName());
        NotificationChannel notificationChannel = this.mNotificationChannel;
        View.OnClickListener onClickListener = null;
        if (notificationChannel == null || notificationChannel.getGroup() == null) {
            name = null;
        } else {
            try {
                NotificationChannelGroup notificationChannelGroupForPackage = this.mINotificationManager.getNotificationChannelGroupForPackage(this.mNotificationChannel.getGroup(), this.mPackageName, this.mAppUid);
                if (notificationChannelGroupForPackage != null) {
                    name = notificationChannelGroupForPackage.getName();
                }
            } catch (RemoteException unused) {
            }
        }
        TextView textView = (TextView) findViewById(R.id.group_name);
        if (name != null) {
            textView.setText(name);
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
        ApplicationInfo applicationInfo = (ApplicationInfo) this.mSbn.getNotification().extras.getParcelable("android.appInfo", ApplicationInfo.class);
        if (applicationInfo != null) {
            try {
                this.mAppName = String.valueOf(this.mPm.getApplicationLabel(applicationInfo));
            } catch (Exception unused2) {
            }
        }
        ((TextView) findViewById(R.id.pkg_name)).setText(this.mAppName);
        bindIcon(this.mNotificationChannel.isImportantConversation());
        this.mPriorityDescriptionView = (TextView) findViewById(R.id.priority_summary);
        if (this.mBubbleMetadata != null && BubblesManager.areBubblesEnabled(((LinearLayout) this).mContext, this.mSbn.getUser()) && willBypassDnd()) {
            this.mPriorityDescriptionView.setText(R.string.notification_channel_summary_priority_all);
        } else if (this.mBubbleMetadata != null && BubblesManager.areBubblesEnabled(((LinearLayout) this).mContext, this.mSbn.getUser())) {
            this.mPriorityDescriptionView.setText(R.string.notification_channel_summary_priority_bubble);
        } else if (willBypassDnd()) {
            this.mPriorityDescriptionView.setText(R.string.notification_channel_summary_priority_dnd);
        } else {
            this.mPriorityDescriptionView.setText(R.string.notification_channel_summary_priority_baseline);
        }
        TextView textView2 = (TextView) findViewById(R.id.delegate_name);
        if (TextUtils.equals(this.mPackageName, this.mDelegatePkg)) {
            textView2.setVisibility(8);
        } else {
            textView2.setVisibility(0);
        }
        TextView textView3 = (TextView) findViewById(R.id.default_summary);
        if (this.mAppBubble == 1 && BubblesManager.areBubblesEnabled(((LinearLayout) this).mContext, this.mSbn.getUser())) {
            textView3.setText(getResources().getString(R.string.notification_channel_summary_default_with_bubbles, this.mAppName));
        } else {
            textView3.setText(getResources().getString(R.string.notification_channel_summary_default));
        }
        findViewById(R.id.priority).setOnClickListener(this.mOnFavoriteClick);
        findViewById(R.id.default_behavior).setOnClickListener(this.mOnDefaultClick);
        findViewById(R.id.silence).setOnClickListener(this.mOnMuteClick);
        View viewFindViewById = findViewById(R.id.info);
        final int i = this.mAppUid;
        if (i >= 0 && this.mOnSettingsClickListener != null && this.mIsDeviceProvisioned) {
            onClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NotificationConversationInfo notificationConversationInfo = this.f$0;
                    int i2 = i;
                    NotificationGutsManager$$ExternalSyntheticLambda1 notificationGutsManager$$ExternalSyntheticLambda12 = notificationConversationInfo.mOnSettingsClickListener;
                    NotificationChannel notificationChannel2 = notificationConversationInfo.mNotificationChannel;
                    StatusBarNotification statusBarNotification2 = notificationGutsManager$$ExternalSyntheticLambda12.f$2;
                    NotificationGutsManager notificationGutsManager = notificationGutsManager$$ExternalSyntheticLambda12.f$0;
                    notificationGutsManager.mMetricsLogger.action(205);
                    notificationGutsManager$$ExternalSyntheticLambda12.f$1.resetFalsingCheck();
                    notificationGutsManager.mOnSettingsClickListener.onSettingsClick(statusBarNotification2.getKey());
                    notificationGutsManager.startAppNotificationSettingsActivity(notificationGutsManager$$ExternalSyntheticLambda12.f$3, i2, notificationChannel2, notificationGutsManager$$ExternalSyntheticLambda12.f$4);
                }
            };
        }
        viewFindViewById.setOnClickListener(onClickListener);
        viewFindViewById.setVisibility(viewFindViewById.hasOnClickListeners() ? 0 : 8);
        View viewFindViewById2 = findViewById(R.id.feedback);
        int i2 = NmSummarizationUiFlag.$r8$clinit;
        viewFindViewById2.setVisibility(8);
        int priority = this.mSelectedAction;
        if (priority == -1) {
            priority = getPriority();
        }
        updateToggleActions(priority, false);
        View viewFindViewById3 = findViewById(R.id.inline_dismiss);
        viewFindViewById3.setOnClickListener(expandableNotificationRow$$ExternalSyntheticLambda6);
        viewFindViewById3.setVisibility((viewFindViewById3.hasOnClickListeners() && z2) ? 0 : 8);
        View viewFindViewById4 = findViewById(R.id.done);
        viewFindViewById4.setOnClickListener(this.mOnDone);
        viewFindViewById4.setAccessibilityDelegate(this.mGutsContainer.getAccessibilityDelegate());
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final int getActualHeight() {
        return this.mActualHeight;
    }

    public final int getPriority() {
        if (this.mNotificationChannel.getImportance() > 2 || this.mNotificationChannel.getImportance() <= -1000) {
            return this.mNotificationChannel.isImportantConversation() ? 2 : 0;
        }
        return 4;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean handleCloseControls(boolean z, boolean z2) {
        NotificationConversationInfo notificationConversationInfo;
        int i;
        int i2 = 0;
        if (!z || (i = this.mSelectedAction) <= -1) {
            notificationConversationInfo = this;
        } else {
            notificationConversationInfo = this;
            this.mBgHandler.post(notificationConversationInfo.new UpdateChannelRunnable(this.mINotificationManager, this.mPackageName, this.mAppUid, i, this.mNotificationChannel));
            int i3 = NotificationBundleUi.$r8$clinit;
            notificationConversationInfo.mEntry.mIsMarkedForUserTriggeredMovement = true;
            notificationConversationInfo.mMainHandler.postDelayed(new NotificationConversationInfo$$ExternalSyntheticLambda4(notificationConversationInfo, i2), 360L);
        }
        notificationConversationInfo.mSelectedAction = -1;
        notificationConversationInfo.mPressedApply = false;
        return false;
    }

    public boolean isAnimating() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean needsFalsingProtection() {
        return true;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mDefaultDescriptionView = (TextView) findViewById(R.id.default_summary);
        this.mSilentDescriptionView = (TextView) findViewById(R.id.silence_summary);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (this.mGutsContainer == null || accessibilityEvent.getEventType() != 32) {
            return;
        }
        if (this.mGutsContainer.mExposed) {
            accessibilityEvent.getText().add(((LinearLayout) this).mContext.getString(R.string.notification_channel_controls_opened_accessibility, this.mAppName));
        } else {
            accessibilityEvent.getText().add(((LinearLayout) this).mContext.getString(R.string.notification_channel_controls_closed_accessibility, this.mAppName));
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mActualHeight = getHeight();
    }

    @Override // android.view.View
    public final boolean post(Runnable runnable) {
        if (!this.mSkipPost) {
            return super.post(runnable);
        }
        runnable.run();
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final void setGutsParent(NotificationGuts notificationGuts) {
        this.mGutsContainer = notificationGuts;
    }

    public void setSelectedAction(int i) {
        if (this.mSelectedAction == i) {
            return;
        }
        this.mSelectedAction = i;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean shouldBeSavedOnClose() {
        return this.mPressedApply;
    }

    public final void updateToggleActions(int i, boolean z) {
        if (z) {
            TransitionSet transitionSet = new TransitionSet();
            transitionSet.setOrdering(0);
            TransitionSet transitionSetAddTransition = transitionSet.addTransition(new Fade(2)).addTransition(new ChangeBounds());
            Transition duration = new Fade(1).setStartDelay(150L).setDuration(200L);
            Interpolator interpolator = Interpolators.FAST_OUT_SLOW_IN;
            transitionSetAddTransition.addTransition(duration.setInterpolator(interpolator));
            transitionSet.setDuration(350L);
            transitionSet.setInterpolator((TimeInterpolator) interpolator);
            TransitionManager.beginDelayedTransition(this, transitionSet);
        }
        final View viewFindViewById = findViewById(R.id.priority);
        final View viewFindViewById2 = findViewById(R.id.default_behavior);
        final View viewFindViewById3 = findViewById(R.id.silence);
        if (i == 0) {
            this.mDefaultDescriptionView.setVisibility(0);
            this.mSilentDescriptionView.setVisibility(8);
            this.mPriorityDescriptionView.setVisibility(8);
            final int i2 = 2;
            post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            View view = viewFindViewById;
                            View view2 = viewFindViewById2;
                            View view3 = viewFindViewById3;
                            int i3 = NotificationConversationInfo.$r8$clinit;
                            view.setSelected(true);
                            view2.setSelected(false);
                            view3.setSelected(false);
                            break;
                        case 1:
                            View view4 = viewFindViewById;
                            View view5 = viewFindViewById2;
                            View view6 = viewFindViewById3;
                            int i4 = NotificationConversationInfo.$r8$clinit;
                            view4.setSelected(false);
                            view5.setSelected(false);
                            view6.setSelected(true);
                            break;
                        default:
                            View view7 = viewFindViewById;
                            View view8 = viewFindViewById2;
                            View view9 = viewFindViewById3;
                            int i5 = NotificationConversationInfo.$r8$clinit;
                            view7.setSelected(false);
                            view8.setSelected(true);
                            view9.setSelected(false);
                            break;
                    }
                }
            });
        } else if (i == 2) {
            this.mPriorityDescriptionView.setVisibility(0);
            this.mDefaultDescriptionView.setVisibility(8);
            this.mSilentDescriptionView.setVisibility(8);
            final int i3 = 0;
            post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i3) {
                        case 0:
                            View view = viewFindViewById;
                            View view2 = viewFindViewById2;
                            View view3 = viewFindViewById3;
                            int i32 = NotificationConversationInfo.$r8$clinit;
                            view.setSelected(true);
                            view2.setSelected(false);
                            view3.setSelected(false);
                            break;
                        case 1:
                            View view4 = viewFindViewById;
                            View view5 = viewFindViewById2;
                            View view6 = viewFindViewById3;
                            int i4 = NotificationConversationInfo.$r8$clinit;
                            view4.setSelected(false);
                            view5.setSelected(false);
                            view6.setSelected(true);
                            break;
                        default:
                            View view7 = viewFindViewById;
                            View view8 = viewFindViewById2;
                            View view9 = viewFindViewById3;
                            int i5 = NotificationConversationInfo.$r8$clinit;
                            view7.setSelected(false);
                            view8.setSelected(true);
                            view9.setSelected(false);
                            break;
                    }
                }
            });
        } else {
            if (i != 4) {
                throw new IllegalArgumentException("Unrecognized behavior: " + this.mSelectedAction);
            }
            this.mSilentDescriptionView.setVisibility(0);
            this.mDefaultDescriptionView.setVisibility(8);
            this.mPriorityDescriptionView.setVisibility(8);
            final int i4 = 1;
            post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i4) {
                        case 0:
                            View view = viewFindViewById;
                            View view2 = viewFindViewById2;
                            View view3 = viewFindViewById3;
                            int i32 = NotificationConversationInfo.$r8$clinit;
                            view.setSelected(true);
                            view2.setSelected(false);
                            view3.setSelected(false);
                            break;
                        case 1:
                            View view4 = viewFindViewById;
                            View view5 = viewFindViewById2;
                            View view6 = viewFindViewById3;
                            int i42 = NotificationConversationInfo.$r8$clinit;
                            view4.setSelected(false);
                            view5.setSelected(false);
                            view6.setSelected(true);
                            break;
                        default:
                            View view7 = viewFindViewById;
                            View view8 = viewFindViewById2;
                            View view9 = viewFindViewById3;
                            int i5 = NotificationConversationInfo.$r8$clinit;
                            view7.setSelected(false);
                            view8.setSelected(true);
                            view9.setSelected(false);
                            break;
                    }
                }
            });
        }
        ((TextView) findViewById(R.id.done)).setText(getPriority() != i ? R.string.inline_ok_button : R.string.inline_done_button);
        bindIcon(i == 2);
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean willBeRemoved() {
        return false;
    }

    public final boolean willBypassDnd() {
        try {
            int i = this.mINotificationManager.getConsolidatedNotificationPolicy().priorityConversationSenders;
            return i == 2 || i == 1;
        } catch (RemoteException e) {
            Log.e("ConversationGuts", "Could not check conversation senders", e);
            return false;
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final View getContentView() {
        return this;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final void onFinishedClosing() {
    }
}
