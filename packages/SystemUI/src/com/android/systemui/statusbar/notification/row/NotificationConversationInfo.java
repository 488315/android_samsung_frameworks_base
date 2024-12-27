package com.android.systemui.statusbar.notification.row;

import android.animation.TimeInterpolator;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.res.TypedArray;
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
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.Optional;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class UpdateChannelRunnable implements Runnable {
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

        /* JADX WARN: Removed duplicated region for block: B:20:0x0031 A[Catch: RemoteException -> 0x0021, TryCatch #0 {RemoteException -> 0x0021, blocks: (B:2:0x0000, B:9:0x00a0, B:13:0x000e, B:15:0x0018, B:18:0x0029, B:20:0x0031, B:21:0x0024, B:22:0x003c, B:24:0x004a, B:26:0x0055, B:27:0x005e, B:29:0x0068, B:30:0x0073, B:31:0x0081, B:33:0x0096), top: B:1:0x0000 }] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                r5 = this;
                int r0 = r5.mAction     // Catch: android.os.RemoteException -> L21
                r1 = 3
                r2 = 0
                if (r0 == 0) goto L81
                r3 = 2
                if (r0 == r3) goto L3c
                r4 = 4
                if (r0 == r4) goto Le
                goto La0
            Le:
                android.app.NotificationChannel r0 = r5.mChannelToUpdate     // Catch: android.os.RemoteException -> L21
                int r0 = r0.getImportance()     // Catch: android.os.RemoteException -> L21
                r4 = -1000(0xfffffffffffffc18, float:NaN)
                if (r0 == r4) goto L24
                android.app.NotificationChannel r0 = r5.mChannelToUpdate     // Catch: android.os.RemoteException -> L21
                int r0 = r0.getImportance()     // Catch: android.os.RemoteException -> L21
                if (r0 < r1) goto L29
                goto L24
            L21:
                r5 = move-exception
                goto Lac
            L24:
                android.app.NotificationChannel r0 = r5.mChannelToUpdate     // Catch: android.os.RemoteException -> L21
                r0.setImportance(r3)     // Catch: android.os.RemoteException -> L21
            L29:
                android.app.NotificationChannel r0 = r5.mChannelToUpdate     // Catch: android.os.RemoteException -> L21
                boolean r0 = r0.isImportantConversation()     // Catch: android.os.RemoteException -> L21
                if (r0 == 0) goto La0
                android.app.NotificationChannel r0 = r5.mChannelToUpdate     // Catch: android.os.RemoteException -> L21
                r0.setImportantConversation(r2)     // Catch: android.os.RemoteException -> L21
                android.app.NotificationChannel r0 = r5.mChannelToUpdate     // Catch: android.os.RemoteException -> L21
                r0.setAllowBubbles(r2)     // Catch: android.os.RemoteException -> L21
                goto La0
            L3c:
                android.app.NotificationChannel r0 = r5.mChannelToUpdate     // Catch: android.os.RemoteException -> L21
                r2 = 1
                r0.setImportantConversation(r2)     // Catch: android.os.RemoteException -> L21
                android.app.NotificationChannel r0 = r5.mChannelToUpdate     // Catch: android.os.RemoteException -> L21
                boolean r0 = r0.isImportantConversation()     // Catch: android.os.RemoteException -> L21
                if (r0 == 0) goto L73
                android.app.NotificationChannel r0 = r5.mChannelToUpdate     // Catch: android.os.RemoteException -> L21
                r0.setAllowBubbles(r2)     // Catch: android.os.RemoteException -> L21
                com.android.systemui.statusbar.notification.row.NotificationConversationInfo r0 = com.android.systemui.statusbar.notification.row.NotificationConversationInfo.this     // Catch: android.os.RemoteException -> L21
                int r0 = r0.mAppBubble     // Catch: android.os.RemoteException -> L21
                if (r0 != 0) goto L5e
                android.app.INotificationManager r0 = r5.mINotificationManager     // Catch: android.os.RemoteException -> L21
                java.lang.String r2 = r5.mAppPkg     // Catch: android.os.RemoteException -> L21
                int r4 = r5.mAppUid     // Catch: android.os.RemoteException -> L21
                r0.setBubblesAllowed(r2, r4, r3)     // Catch: android.os.RemoteException -> L21
            L5e:
                com.android.systemui.statusbar.notification.row.NotificationConversationInfo r0 = com.android.systemui.statusbar.notification.row.NotificationConversationInfo.this     // Catch: android.os.RemoteException -> L21
                java.util.Optional r0 = r0.mBubblesManagerOptional     // Catch: android.os.RemoteException -> L21
                boolean r0 = r0.isPresent()     // Catch: android.os.RemoteException -> L21
                if (r0 == 0) goto L73
                com.android.systemui.statusbar.notification.row.NotificationConversationInfo r0 = com.android.systemui.statusbar.notification.row.NotificationConversationInfo.this     // Catch: android.os.RemoteException -> L21
                com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda4 r2 = new com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda4     // Catch: android.os.RemoteException -> L21
                r3 = 1
                r2.<init>(r5, r3)     // Catch: android.os.RemoteException -> L21
                r0.post(r2)     // Catch: android.os.RemoteException -> L21
            L73:
                android.app.NotificationChannel r0 = r5.mChannelToUpdate     // Catch: android.os.RemoteException -> L21
                int r2 = r0.getOriginalImportance()     // Catch: android.os.RemoteException -> L21
                int r1 = java.lang.Math.max(r2, r1)     // Catch: android.os.RemoteException -> L21
                r0.setImportance(r1)     // Catch: android.os.RemoteException -> L21
                goto La0
            L81:
                android.app.NotificationChannel r0 = r5.mChannelToUpdate     // Catch: android.os.RemoteException -> L21
                int r3 = r0.getOriginalImportance()     // Catch: android.os.RemoteException -> L21
                int r1 = java.lang.Math.max(r3, r1)     // Catch: android.os.RemoteException -> L21
                r0.setImportance(r1)     // Catch: android.os.RemoteException -> L21
                android.app.NotificationChannel r0 = r5.mChannelToUpdate     // Catch: android.os.RemoteException -> L21
                boolean r0 = r0.isImportantConversation()     // Catch: android.os.RemoteException -> L21
                if (r0 == 0) goto La0
                android.app.NotificationChannel r0 = r5.mChannelToUpdate     // Catch: android.os.RemoteException -> L21
                r0.setImportantConversation(r2)     // Catch: android.os.RemoteException -> L21
                android.app.NotificationChannel r0 = r5.mChannelToUpdate     // Catch: android.os.RemoteException -> L21
                r0.setAllowBubbles(r2)     // Catch: android.os.RemoteException -> L21
            La0:
                android.app.INotificationManager r0 = r5.mINotificationManager     // Catch: android.os.RemoteException -> L21
                java.lang.String r1 = r5.mAppPkg     // Catch: android.os.RemoteException -> L21
                int r2 = r5.mAppUid     // Catch: android.os.RemoteException -> L21
                android.app.NotificationChannel r5 = r5.mChannelToUpdate     // Catch: android.os.RemoteException -> L21
                r0.updateNotificationChannelForPackage(r1, r2, r5)     // Catch: android.os.RemoteException -> L21
                goto Lb3
            Lac:
                java.lang.String r0 = "ConversationGuts"
                java.lang.String r1 = "Unable to update notification channel"
                android.util.Log.e(r0, r1, r5)
            Lb3:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.NotificationConversationInfo.UpdateChannelRunnable.run():void");
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
                                peopleSpaceWidgetManager.getClass();
                                RemoteViews preview = peopleSpaceWidgetManager.getPreview(shortcutInfo.getId(), shortcutInfo.getUserHandle(), shortcutInfo.getPackage(), bundle);
                                if (preview == null) {
                                    Log.w("PeopleSpaceWidgetMgr", "Skipping pinning widget: no tile for shortcutId: " + shortcutInfo.getId());
                                } else {
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putParcelable("appWidgetPreview", preview);
                                    Context context2 = peopleSpaceWidgetManager.mContext;
                                    int i6 = PeopleSpaceWidgetPinnedReceiver.$r8$clinit;
                                    Intent addFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                    addFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                    addFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                    addFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                    peopleSpaceWidgetManager.mAppWidgetManager.requestPinAppWidget(new ComponentName(peopleSpaceWidgetManager.mContext, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, addFlags, 167772160));
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
                                peopleSpaceWidgetManager.getClass();
                                RemoteViews preview = peopleSpaceWidgetManager.getPreview(shortcutInfo.getId(), shortcutInfo.getUserHandle(), shortcutInfo.getPackage(), bundle);
                                if (preview == null) {
                                    Log.w("PeopleSpaceWidgetMgr", "Skipping pinning widget: no tile for shortcutId: " + shortcutInfo.getId());
                                } else {
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putParcelable("appWidgetPreview", preview);
                                    Context context2 = peopleSpaceWidgetManager.mContext;
                                    int i6 = PeopleSpaceWidgetPinnedReceiver.$r8$clinit;
                                    Intent addFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                    addFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                    addFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                    addFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                    peopleSpaceWidgetManager.mAppWidgetManager.requestPinAppWidget(new ComponentName(peopleSpaceWidgetManager.mContext, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, addFlags, 167772160));
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
                                peopleSpaceWidgetManager.getClass();
                                RemoteViews preview = peopleSpaceWidgetManager.getPreview(shortcutInfo.getId(), shortcutInfo.getUserHandle(), shortcutInfo.getPackage(), bundle);
                                if (preview == null) {
                                    Log.w("PeopleSpaceWidgetMgr", "Skipping pinning widget: no tile for shortcutId: " + shortcutInfo.getId());
                                } else {
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putParcelable("appWidgetPreview", preview);
                                    Context context2 = peopleSpaceWidgetManager.mContext;
                                    int i6 = PeopleSpaceWidgetPinnedReceiver.$r8$clinit;
                                    Intent addFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                    addFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                    addFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                    addFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                    peopleSpaceWidgetManager.mAppWidgetManager.requestPinAppWidget(new ComponentName(peopleSpaceWidgetManager.mContext, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, addFlags, 167772160));
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
                                peopleSpaceWidgetManager.getClass();
                                RemoteViews preview = peopleSpaceWidgetManager.getPreview(shortcutInfo.getId(), shortcutInfo.getUserHandle(), shortcutInfo.getPackage(), bundle);
                                if (preview == null) {
                                    Log.w("PeopleSpaceWidgetMgr", "Skipping pinning widget: no tile for shortcutId: " + shortcutInfo.getId());
                                } else {
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putParcelable("appWidgetPreview", preview);
                                    Context context2 = peopleSpaceWidgetManager.mContext;
                                    int i6 = PeopleSpaceWidgetPinnedReceiver.$r8$clinit;
                                    Intent addFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                    addFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                    addFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                    addFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                    peopleSpaceWidgetManager.mAppWidgetManager.requestPinAppWidget(new ComponentName(peopleSpaceWidgetManager.mContext, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, addFlags, 167772160));
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
        ConversationIconFactory conversationIconFactory = this.mIconFactory;
        Drawable shortcutIconDrawable = conversationIconFactory.mLauncherApps.getShortcutIconDrawable(this.mShortcutInfo, conversationIconFactory.mFillResIconDpi);
        if (shortcutIconDrawable == null) {
            shortcutIconDrawable = ((LinearLayout) this).mContext.getDrawable(R.drawable.ic_person).mutate();
            TypedArray obtainStyledAttributes = ((LinearLayout) this).mContext.obtainStyledAttributes(new int[]{android.R.^attr-private.materialColorPrimaryFixedDim});
            int color = obtainStyledAttributes.getColor(0, 0);
            obtainStyledAttributes.recycle();
            shortcutIconDrawable.setTint(color);
        }
        ((ImageView) findViewById(R.id.conversation_icon)).setImageDrawable(shortcutIconDrawable);
        ImageView imageView = (ImageView) findViewById(R.id.conversation_icon_badge_icon);
        ConversationIconFactory conversationIconFactory2 = this.mIconFactory;
        String str = this.mPackageName;
        int userId = UserHandle.getUserId(this.mSbn.getUid());
        conversationIconFactory2.getClass();
        try {
            defaultActivityIcon = Utils.getBadgedIcon(conversationIconFactory2.mContext, conversationIconFactory2.mPackageManager.getApplicationInfoAsUser(str, 128, userId));
        } catch (PackageManager.NameNotFoundException unused) {
            defaultActivityIcon = conversationIconFactory2.mPackageManager.getDefaultActivityIcon();
        }
        imageView.setImageDrawable(defaultActivityIcon);
        findViewById(R.id.conversation_icon_badge_ring).setVisibility(z ? 0 : 8);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00df A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00c8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void bindNotification(android.content.pm.PackageManager r9, android.os.UserManager r10, com.android.systemui.people.widget.PeopleSpaceWidgetManager r11, android.app.INotificationManager r12, com.android.systemui.statusbar.notification.row.OnUserInteractionCallback r13, java.lang.String r14, android.app.NotificationChannel r15, com.android.systemui.statusbar.notification.collection.NotificationEntry r16, android.app.Notification.BubbleMetadata r17, com.android.systemui.statusbar.notification.row.NotificationGutsManager$$ExternalSyntheticLambda1 r18, com.android.settingslib.notification.ConversationIconFactory r19, boolean r20, android.os.Handler r21, android.os.Handler r22, java.util.Optional r23, com.android.systemui.shade.ShadeController r24) {
        /*
            Method dump skipped, instructions count: 556
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.NotificationConversationInfo.bindNotification(android.content.pm.PackageManager, android.os.UserManager, com.android.systemui.people.widget.PeopleSpaceWidgetManager, android.app.INotificationManager, com.android.systemui.statusbar.notification.row.OnUserInteractionCallback, java.lang.String, android.app.NotificationChannel, com.android.systemui.statusbar.notification.collection.NotificationEntry, android.app.Notification$BubbleMetadata, com.android.systemui.statusbar.notification.row.NotificationGutsManager$$ExternalSyntheticLambda1, com.android.settingslib.notification.ConversationIconFactory, boolean, android.os.Handler, android.os.Handler, java.util.Optional, com.android.systemui.shade.ShadeController):void");
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
        int i;
        if (z && (i = this.mSelectedAction) > -1) {
            this.mBgHandler.post(new UpdateChannelRunnable(this.mINotificationManager, this.mPackageName, this.mAppUid, i, this.mNotificationChannel));
            this.mEntry.mIsMarkedForUserTriggeredMovement = true;
            this.mMainHandler.postDelayed(new NotificationConversationInfo$$ExternalSyntheticLambda4(this, 0), 360L);
        }
        this.mSelectedAction = -1;
        this.mPressedApply = false;
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
