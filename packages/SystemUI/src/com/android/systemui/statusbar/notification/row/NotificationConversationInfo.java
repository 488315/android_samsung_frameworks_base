package com.android.systemui.statusbar.notification.row;

import android.animation.TimeInterpolator;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.notification.StatusBarNotification;
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
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                                        Intent addFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(268435456);
                                        addFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                        addFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                        addFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                        ((AppWidgetManager) peopleSpaceWidgetManager.mAppWidgetManagerOptional.get()).requestPinAppWidget(new ComponentName(peopleSpaceWidgetManager.mContext, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, addFlags, 167772160));
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
                                        Intent addFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(268435456);
                                        addFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                        addFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                        addFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                        ((AppWidgetManager) peopleSpaceWidgetManager.mAppWidgetManagerOptional.get()).requestPinAppWidget(new ComponentName(peopleSpaceWidgetManager.mContext, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, addFlags, 167772160));
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
                                        Intent addFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(268435456);
                                        addFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                        addFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                        addFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                        ((AppWidgetManager) peopleSpaceWidgetManager.mAppWidgetManagerOptional.get()).requestPinAppWidget(new ComponentName(peopleSpaceWidgetManager.mContext, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, addFlags, 167772160));
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
                                        Intent addFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(268435456);
                                        addFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                        addFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                        addFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                        ((AppWidgetManager) peopleSpaceWidgetManager.mAppWidgetManagerOptional.get()).requestPinAppWidget(new ComponentName(peopleSpaceWidgetManager.mContext, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, addFlags, 167772160));
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

    /* JADX WARN: Removed duplicated region for block: B:16:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01f9  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00d8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void bindNotification(android.content.pm.PackageManager r2, android.os.UserManager r3, com.android.systemui.people.widget.PeopleSpaceWidgetManager r4, android.app.INotificationManager r5, com.android.systemui.statusbar.notification.row.OnUserInteractionCallback r6, java.lang.String r7, com.android.systemui.statusbar.notification.collection.NotificationEntry r8, android.service.notification.NotificationListenerService.Ranking r9, android.service.notification.StatusBarNotification r10, com.android.systemui.statusbar.notification.row.NotificationGutsManager$$ExternalSyntheticLambda1 r11, androidx.core.view.ViewCompat$$ExternalSyntheticLambda0 r12, com.android.settingslib.notification.ConversationIconFactory r13, boolean r14, android.os.Handler r15, android.os.Handler r16, java.util.Optional r17, com.android.systemui.shade.ShadeController r18, boolean r19, com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda6 r20) {
        /*
            Method dump skipped, instructions count: 588
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.NotificationConversationInfo.bindNotification(android.content.pm.PackageManager, android.os.UserManager, com.android.systemui.people.widget.PeopleSpaceWidgetManager, android.app.INotificationManager, com.android.systemui.statusbar.notification.row.OnUserInteractionCallback, java.lang.String, com.android.systemui.statusbar.notification.collection.NotificationEntry, android.service.notification.NotificationListenerService$Ranking, android.service.notification.StatusBarNotification, com.android.systemui.statusbar.notification.row.NotificationGutsManager$$ExternalSyntheticLambda1, androidx.core.view.ViewCompat$$ExternalSyntheticLambda0, com.android.settingslib.notification.ConversationIconFactory, boolean, android.os.Handler, android.os.Handler, java.util.Optional, com.android.systemui.shade.ShadeController, boolean, com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda6):void");
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
            TransitionSet addTransition = transitionSet.addTransition(new Fade(2)).addTransition(new ChangeBounds());
            Transition duration = new Fade(1).setStartDelay(150L).setDuration(200L);
            Interpolator interpolator = Interpolators.FAST_OUT_SLOW_IN;
            addTransition.addTransition(duration.setInterpolator(interpolator));
            transitionSet.setDuration(350L);
            transitionSet.setInterpolator((TimeInterpolator) interpolator);
            TransitionManager.beginDelayedTransition(this, transitionSet);
        }
        final View findViewById = findViewById(R.id.priority);
        final View findViewById2 = findViewById(R.id.default_behavior);
        final View findViewById3 = findViewById(R.id.silence);
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
                            View view = findViewById;
                            View view2 = findViewById2;
                            View view3 = findViewById3;
                            int i3 = NotificationConversationInfo.$r8$clinit;
                            view.setSelected(true);
                            view2.setSelected(false);
                            view3.setSelected(false);
                            break;
                        case 1:
                            View view4 = findViewById;
                            View view5 = findViewById2;
                            View view6 = findViewById3;
                            int i4 = NotificationConversationInfo.$r8$clinit;
                            view4.setSelected(false);
                            view5.setSelected(false);
                            view6.setSelected(true);
                            break;
                        default:
                            View view7 = findViewById;
                            View view8 = findViewById2;
                            View view9 = findViewById3;
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
                            View view = findViewById;
                            View view2 = findViewById2;
                            View view3 = findViewById3;
                            int i32 = NotificationConversationInfo.$r8$clinit;
                            view.setSelected(true);
                            view2.setSelected(false);
                            view3.setSelected(false);
                            break;
                        case 1:
                            View view4 = findViewById;
                            View view5 = findViewById2;
                            View view6 = findViewById3;
                            int i4 = NotificationConversationInfo.$r8$clinit;
                            view4.setSelected(false);
                            view5.setSelected(false);
                            view6.setSelected(true);
                            break;
                        default:
                            View view7 = findViewById;
                            View view8 = findViewById2;
                            View view9 = findViewById3;
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
                            View view = findViewById;
                            View view2 = findViewById2;
                            View view3 = findViewById3;
                            int i32 = NotificationConversationInfo.$r8$clinit;
                            view.setSelected(true);
                            view2.setSelected(false);
                            view3.setSelected(false);
                            break;
                        case 1:
                            View view4 = findViewById;
                            View view5 = findViewById2;
                            View view6 = findViewById3;
                            int i42 = NotificationConversationInfo.$r8$clinit;
                            view4.setSelected(false);
                            view5.setSelected(false);
                            view6.setSelected(true);
                            break;
                        default:
                            View view7 = findViewById;
                            View view8 = findViewById2;
                            View view9 = findViewById3;
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
