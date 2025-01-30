package com.android.systemui.statusbar.notification.row;

import android.animation.TimeInterpolator;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
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
import com.android.app.animation.Interpolators;
import com.android.settingslib.Utils;
import com.android.settingslib.notification.ConversationIconFactory;
import com.android.systemui.R;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.people.widget.PeopleSpaceWidgetPinnedReceiver;
import com.android.systemui.people.widget.PeopleSpaceWidgetProvider;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.statusbar.notification.NotificationChannelHelper;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.wmshell.BubblesManager;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    public NotificationGutsManager$$ExternalSyntheticLambda2 mOnSettingsClickListener;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

        @Override // java.lang.Runnable
        public final void run() {
            try {
                int i = this.mAction;
                if (i == 0) {
                    NotificationChannel notificationChannel = this.mChannelToUpdate;
                    notificationChannel.setImportance(Math.max(notificationChannel.getOriginalImportance(), 3));
                    if (this.mChannelToUpdate.isImportantConversation()) {
                        this.mChannelToUpdate.setImportantConversation(false);
                        this.mChannelToUpdate.setAllowBubbles(false);
                    }
                } else if (i == 2) {
                    int i2 = 1;
                    this.mChannelToUpdate.setImportantConversation(true);
                    if (this.mChannelToUpdate.isImportantConversation()) {
                        this.mChannelToUpdate.setAllowBubbles(true);
                        if (NotificationConversationInfo.this.mAppBubble == 0) {
                            this.mINotificationManager.setBubblesAllowed(this.mAppPkg, this.mAppUid, 2);
                        }
                        if (NotificationConversationInfo.this.mBubblesManagerOptional.isPresent()) {
                            NotificationConversationInfo.this.post(new NotificationConversationInfo$$ExternalSyntheticLambda1(this, i2));
                        }
                    }
                    NotificationChannel notificationChannel2 = this.mChannelToUpdate;
                    notificationChannel2.setImportance(Math.max(notificationChannel2.getOriginalImportance(), 3));
                } else if (i == 4) {
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
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda0] */
    public NotificationConversationInfo(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSelectedAction = -1;
        final int i = 0;
        this.mSkipPost = false;
        this.mOnFavoriteClick = new View.OnClickListener(this) { // from class: com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda0
            public final /* synthetic */ NotificationConversationInfo f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        NotificationConversationInfo notificationConversationInfo = this.f$0;
                        int i2 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(2);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    case 1:
                        NotificationConversationInfo notificationConversationInfo2 = this.f$0;
                        int i3 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo2.setSelectedAction(0);
                        notificationConversationInfo2.updateToggleActions(notificationConversationInfo2.mSelectedAction, true);
                        break;
                    case 2:
                        NotificationConversationInfo notificationConversationInfo3 = this.f$0;
                        int i4 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo3.setSelectedAction(4);
                        notificationConversationInfo3.updateToggleActions(notificationConversationInfo3.mSelectedAction, true);
                        break;
                    default:
                        NotificationConversationInfo notificationConversationInfo4 = this.f$0;
                        notificationConversationInfo4.mPressedApply = true;
                        if (notificationConversationInfo4.mSelectedAction == 2 && notificationConversationInfo4.getPriority() != notificationConversationInfo4.mSelectedAction) {
                            ((ShadeControllerImpl) notificationConversationInfo4.mShadeController).animateCollapseShade(0);
                            if (notificationConversationInfo4.mUm.isSameProfileGroup(0, notificationConversationInfo4.mSbn.getNormalizedUserId())) {
                                PeopleSpaceWidgetManager peopleSpaceWidgetManager = notificationConversationInfo4.mPeopleSpaceWidgetManager;
                                ShortcutInfo shortcutInfo = notificationConversationInfo4.mShortcutInfo;
                                Bundle bundle = new Bundle();
                                peopleSpaceWidgetManager.getClass();
                                RemoteViews preview = peopleSpaceWidgetManager.getPreview(shortcutInfo.getId(), shortcutInfo.getUserHandle(), shortcutInfo.getPackage(), bundle);
                                if (preview == null) {
                                    Log.w("PeopleSpaceWidgetMgr", "Skipping pinning widget: no tile for shortcutId: " + shortcutInfo.getId());
                                } else {
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putParcelable("appWidgetPreview", preview);
                                    int i5 = PeopleSpaceWidgetPinnedReceiver.$r8$clinit;
                                    Context context2 = peopleSpaceWidgetManager.mContext;
                                    Intent addFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                    addFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                    addFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                    addFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                    peopleSpaceWidgetManager.mAppWidgetManager.requestPinAppWidget(new ComponentName(context2, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, addFlags, 167772160));
                                }
                            }
                        }
                        notificationConversationInfo4.mGutsContainer.closeControls(view, true);
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
                switch (i2) {
                    case 0:
                        NotificationConversationInfo notificationConversationInfo = this.f$0;
                        int i22 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(2);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    case 1:
                        NotificationConversationInfo notificationConversationInfo2 = this.f$0;
                        int i3 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo2.setSelectedAction(0);
                        notificationConversationInfo2.updateToggleActions(notificationConversationInfo2.mSelectedAction, true);
                        break;
                    case 2:
                        NotificationConversationInfo notificationConversationInfo3 = this.f$0;
                        int i4 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo3.setSelectedAction(4);
                        notificationConversationInfo3.updateToggleActions(notificationConversationInfo3.mSelectedAction, true);
                        break;
                    default:
                        NotificationConversationInfo notificationConversationInfo4 = this.f$0;
                        notificationConversationInfo4.mPressedApply = true;
                        if (notificationConversationInfo4.mSelectedAction == 2 && notificationConversationInfo4.getPriority() != notificationConversationInfo4.mSelectedAction) {
                            ((ShadeControllerImpl) notificationConversationInfo4.mShadeController).animateCollapseShade(0);
                            if (notificationConversationInfo4.mUm.isSameProfileGroup(0, notificationConversationInfo4.mSbn.getNormalizedUserId())) {
                                PeopleSpaceWidgetManager peopleSpaceWidgetManager = notificationConversationInfo4.mPeopleSpaceWidgetManager;
                                ShortcutInfo shortcutInfo = notificationConversationInfo4.mShortcutInfo;
                                Bundle bundle = new Bundle();
                                peopleSpaceWidgetManager.getClass();
                                RemoteViews preview = peopleSpaceWidgetManager.getPreview(shortcutInfo.getId(), shortcutInfo.getUserHandle(), shortcutInfo.getPackage(), bundle);
                                if (preview == null) {
                                    Log.w("PeopleSpaceWidgetMgr", "Skipping pinning widget: no tile for shortcutId: " + shortcutInfo.getId());
                                } else {
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putParcelable("appWidgetPreview", preview);
                                    int i5 = PeopleSpaceWidgetPinnedReceiver.$r8$clinit;
                                    Context context2 = peopleSpaceWidgetManager.mContext;
                                    Intent addFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                    addFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                    addFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                    addFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                    peopleSpaceWidgetManager.mAppWidgetManager.requestPinAppWidget(new ComponentName(context2, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, addFlags, 167772160));
                                }
                            }
                        }
                        notificationConversationInfo4.mGutsContainer.closeControls(view, true);
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
                switch (i3) {
                    case 0:
                        NotificationConversationInfo notificationConversationInfo = this.f$0;
                        int i22 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(2);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    case 1:
                        NotificationConversationInfo notificationConversationInfo2 = this.f$0;
                        int i32 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo2.setSelectedAction(0);
                        notificationConversationInfo2.updateToggleActions(notificationConversationInfo2.mSelectedAction, true);
                        break;
                    case 2:
                        NotificationConversationInfo notificationConversationInfo3 = this.f$0;
                        int i4 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo3.setSelectedAction(4);
                        notificationConversationInfo3.updateToggleActions(notificationConversationInfo3.mSelectedAction, true);
                        break;
                    default:
                        NotificationConversationInfo notificationConversationInfo4 = this.f$0;
                        notificationConversationInfo4.mPressedApply = true;
                        if (notificationConversationInfo4.mSelectedAction == 2 && notificationConversationInfo4.getPriority() != notificationConversationInfo4.mSelectedAction) {
                            ((ShadeControllerImpl) notificationConversationInfo4.mShadeController).animateCollapseShade(0);
                            if (notificationConversationInfo4.mUm.isSameProfileGroup(0, notificationConversationInfo4.mSbn.getNormalizedUserId())) {
                                PeopleSpaceWidgetManager peopleSpaceWidgetManager = notificationConversationInfo4.mPeopleSpaceWidgetManager;
                                ShortcutInfo shortcutInfo = notificationConversationInfo4.mShortcutInfo;
                                Bundle bundle = new Bundle();
                                peopleSpaceWidgetManager.getClass();
                                RemoteViews preview = peopleSpaceWidgetManager.getPreview(shortcutInfo.getId(), shortcutInfo.getUserHandle(), shortcutInfo.getPackage(), bundle);
                                if (preview == null) {
                                    Log.w("PeopleSpaceWidgetMgr", "Skipping pinning widget: no tile for shortcutId: " + shortcutInfo.getId());
                                } else {
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putParcelable("appWidgetPreview", preview);
                                    int i5 = PeopleSpaceWidgetPinnedReceiver.$r8$clinit;
                                    Context context2 = peopleSpaceWidgetManager.mContext;
                                    Intent addFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                    addFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                    addFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                    addFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                    peopleSpaceWidgetManager.mAppWidgetManager.requestPinAppWidget(new ComponentName(context2, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, addFlags, 167772160));
                                }
                            }
                        }
                        notificationConversationInfo4.mGutsContainer.closeControls(view, true);
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
                switch (i4) {
                    case 0:
                        NotificationConversationInfo notificationConversationInfo = this.f$0;
                        int i22 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo.setSelectedAction(2);
                        notificationConversationInfo.updateToggleActions(notificationConversationInfo.mSelectedAction, true);
                        break;
                    case 1:
                        NotificationConversationInfo notificationConversationInfo2 = this.f$0;
                        int i32 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo2.setSelectedAction(0);
                        notificationConversationInfo2.updateToggleActions(notificationConversationInfo2.mSelectedAction, true);
                        break;
                    case 2:
                        NotificationConversationInfo notificationConversationInfo3 = this.f$0;
                        int i42 = NotificationConversationInfo.$r8$clinit;
                        notificationConversationInfo3.setSelectedAction(4);
                        notificationConversationInfo3.updateToggleActions(notificationConversationInfo3.mSelectedAction, true);
                        break;
                    default:
                        NotificationConversationInfo notificationConversationInfo4 = this.f$0;
                        notificationConversationInfo4.mPressedApply = true;
                        if (notificationConversationInfo4.mSelectedAction == 2 && notificationConversationInfo4.getPriority() != notificationConversationInfo4.mSelectedAction) {
                            ((ShadeControllerImpl) notificationConversationInfo4.mShadeController).animateCollapseShade(0);
                            if (notificationConversationInfo4.mUm.isSameProfileGroup(0, notificationConversationInfo4.mSbn.getNormalizedUserId())) {
                                PeopleSpaceWidgetManager peopleSpaceWidgetManager = notificationConversationInfo4.mPeopleSpaceWidgetManager;
                                ShortcutInfo shortcutInfo = notificationConversationInfo4.mShortcutInfo;
                                Bundle bundle = new Bundle();
                                peopleSpaceWidgetManager.getClass();
                                RemoteViews preview = peopleSpaceWidgetManager.getPreview(shortcutInfo.getId(), shortcutInfo.getUserHandle(), shortcutInfo.getPackage(), bundle);
                                if (preview == null) {
                                    Log.w("PeopleSpaceWidgetMgr", "Skipping pinning widget: no tile for shortcutId: " + shortcutInfo.getId());
                                } else {
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putParcelable("appWidgetPreview", preview);
                                    int i5 = PeopleSpaceWidgetPinnedReceiver.$r8$clinit;
                                    Context context2 = peopleSpaceWidgetManager.mContext;
                                    Intent addFlags = new Intent(context2, (Class<?>) PeopleSpaceWidgetPinnedReceiver.class).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                    addFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                                    addFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                                    addFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                                    peopleSpaceWidgetManager.mAppWidgetManager.requestPinAppWidget(new ComponentName(context2, (Class<?>) PeopleSpaceWidgetProvider.class), bundle2, PendingIntent.getBroadcast(context2, 0, addFlags, 167772160));
                                }
                            }
                        }
                        notificationConversationInfo4.mGutsContainer.closeControls(view, true);
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
            TypedArray obtainStyledAttributes = ((LinearLayout) this).mContext.obtainStyledAttributes(new int[]{android.R.attr.colorAccent});
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

    /* JADX WARN: Can't wrap try/catch for region: R(32:3|(2:4|5)|6|(3:10|11|(26:13|14|(1:16)(1:88)|17|18|(1:20)|22|(1:86)(1:26)|27|(5:75|76|(2:82|(13:81|36|(1:38)(1:61)|39|(1:60)(1:43)|44|(1:50)|51|(1:53)|54|(1:56)|57|58))|79|(0))|29|(1:74)(1:33)|(1:35)(5:62|63|(2:70|(1:68)(1:69))|66|(0)(0))|36|(0)(0)|39|(1:41)|60|44|(3:46|48|50)|51|(0)|54|(0)|57|58))|91|14|(0)(0)|17|18|(0)|22|(1:24)|86|27|(0)|29|(1:31)|74|(0)(0)|36|(0)(0)|39|(0)|60|44|(0)|51|(0)|54|(0)|57|58) */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00da A[Catch: NameNotFoundException -> 0x00e6, TRY_LEAVE, TryCatch #1 {NameNotFoundException -> 0x00e6, blocks: (B:18:0x00cd, B:20:0x00da), top: B:17:0x00cd }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0160 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0122 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00ca  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void bindNotification(PackageManager packageManager, UserManager userManager, PeopleSpaceWidgetManager peopleSpaceWidgetManager, INotificationManager iNotificationManager, OnUserInteractionCallback onUserInteractionCallback, String str, NotificationChannel notificationChannel, NotificationEntry notificationEntry, Notification.BubbleMetadata bubbleMetadata, NotificationGutsManager$$ExternalSyntheticLambda2 notificationGutsManager$$ExternalSyntheticLambda2, ConversationIconFactory conversationIconFactory, boolean z, Handler handler, Handler handler2, Optional optional, ShadeController shadeController) {
        CharSequence charSequence;
        boolean z2;
        int i;
        TextView textView;
        final int i2;
        int i3;
        boolean z3;
        int i4;
        ApplicationInfo applicationInfo;
        NotificationChannelGroup notificationChannelGroupForPackage;
        this.mINotificationManager = iNotificationManager;
        this.mPeopleSpaceWidgetManager = peopleSpaceWidgetManager;
        this.mOnUserInteractionCallback = onUserInteractionCallback;
        this.mPackageName = str;
        this.mEntry = notificationEntry;
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        this.mSbn = statusBarNotification;
        this.mPm = packageManager;
        this.mUm = userManager;
        this.mAppName = str;
        this.mOnSettingsClickListener = notificationGutsManager$$ExternalSyntheticLambda2;
        this.mNotificationChannel = notificationChannel;
        this.mAppUid = statusBarNotification.getUid();
        this.mDelegatePkg = this.mSbn.getOpPkg();
        this.mIsDeviceProvisioned = z;
        this.mIconFactory = conversationIconFactory;
        this.mBubbleMetadata = bubbleMetadata;
        this.mBubblesManagerOptional = optional;
        this.mShadeController = shadeController;
        this.mMainHandler = handler;
        this.mBgHandler = handler2;
        ShortcutInfo conversationShortcutInfo = notificationEntry.mRanking.getConversationShortcutInfo();
        this.mShortcutInfo = conversationShortcutInfo;
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
        NotificationChannel notificationChannel2 = this.mNotificationChannel;
        View.OnClickListener onClickListener = null;
        if (notificationChannel2 != null && notificationChannel2.getGroup() != null) {
            try {
                notificationChannelGroupForPackage = this.mINotificationManager.getNotificationChannelGroupForPackage(this.mNotificationChannel.getGroup(), this.mPackageName, this.mAppUid);
            } catch (RemoteException unused) {
            }
            if (notificationChannelGroupForPackage != null) {
                charSequence = notificationChannelGroupForPackage.getName();
                TextView textView2 = (TextView) findViewById(R.id.group_name);
                if (charSequence == null) {
                    textView2.setText(charSequence);
                    textView2.setVisibility(0);
                } else {
                    textView2.setVisibility(8);
                }
                applicationInfo = this.mPm.getApplicationInfo(this.mPackageName, 795136);
                if (applicationInfo != null) {
                    this.mAppName = String.valueOf(this.mPm.getApplicationLabel(applicationInfo));
                }
                ((TextView) findViewById(R.id.pkg_name)).setText(this.mAppName);
                bindIcon(this.mNotificationChannel.isImportantConversation());
                this.mPriorityDescriptionView = (TextView) findViewById(R.id.priority_summary);
                if (this.mBubbleMetadata == null && BubblesManager.areBubblesEnabled(((LinearLayout) this).mContext, this.mSbn.getUser())) {
                    try {
                        i = this.mINotificationManager.getConsolidatedNotificationPolicy().priorityConversationSenders;
                    } catch (RemoteException e2) {
                        Log.e("ConversationGuts", "Could not check conversation senders", e2);
                    }
                    if (i == 2 || i == 1) {
                        z2 = true;
                        if (z2) {
                            this.mPriorityDescriptionView.setText(R.string.notification_channel_summary_priority_all);
                            TextView textView3 = (TextView) findViewById(R.id.delegate_name);
                            if (TextUtils.equals(this.mPackageName, this.mDelegatePkg)) {
                                textView3.setVisibility(8);
                            } else {
                                textView3.setVisibility(0);
                            }
                            textView = (TextView) findViewById(R.id.default_summary);
                            if (this.mAppBubble == 1 || !BubblesManager.areBubblesEnabled(((LinearLayout) this).mContext, this.mSbn.getUser())) {
                                textView.setText(getResources().getString(R.string.notification_channel_summary_default));
                            } else {
                                textView.setText(getResources().getString(R.string.notification_channel_summary_default_with_bubbles, this.mAppName));
                            }
                            findViewById(R.id.priority).setOnClickListener(this.mOnFavoriteClick);
                            findViewById(R.id.default_behavior).setOnClickListener(this.mOnDefaultClick);
                            findViewById(R.id.silence).setOnClickListener(this.mOnMuteClick);
                            View findViewById = findViewById(R.id.info);
                            i2 = this.mAppUid;
                            if (i2 >= 0 && this.mOnSettingsClickListener != null && this.mIsDeviceProvisioned) {
                                onClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda2
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        NotificationConversationInfo notificationConversationInfo = NotificationConversationInfo.this;
                                        notificationConversationInfo.mOnSettingsClickListener.onClick(i2, notificationConversationInfo.mNotificationChannel);
                                    }
                                };
                            }
                            findViewById.setOnClickListener(onClickListener);
                            findViewById.setVisibility(findViewById.hasOnClickListeners() ? 0 : 8);
                            i3 = this.mSelectedAction;
                            if (i3 == -1) {
                                i3 = getPriority();
                            }
                            updateToggleActions(i3, false);
                            View findViewById2 = findViewById(R.id.done);
                            findViewById2.setOnClickListener(this.mOnDone);
                            findViewById2.setAccessibilityDelegate(this.mGutsContainer.getAccessibilityDelegate());
                        }
                    }
                    z2 = false;
                    if (z2) {
                    }
                }
                if (this.mBubbleMetadata == null && BubblesManager.areBubblesEnabled(((LinearLayout) this).mContext, this.mSbn.getUser())) {
                    try {
                        i4 = this.mINotificationManager.getConsolidatedNotificationPolicy().priorityConversationSenders;
                    } catch (RemoteException e3) {
                        Log.e("ConversationGuts", "Could not check conversation senders", e3);
                    }
                    if (i4 == 2 || i4 == 1) {
                        z3 = true;
                        if (z3) {
                            this.mPriorityDescriptionView.setText(R.string.notification_channel_summary_priority_baseline);
                        } else {
                            this.mPriorityDescriptionView.setText(R.string.notification_channel_summary_priority_dnd);
                        }
                    }
                    z3 = false;
                    if (z3) {
                    }
                } else {
                    this.mPriorityDescriptionView.setText(R.string.notification_channel_summary_priority_bubble);
                }
                TextView textView32 = (TextView) findViewById(R.id.delegate_name);
                if (TextUtils.equals(this.mPackageName, this.mDelegatePkg)) {
                }
                textView = (TextView) findViewById(R.id.default_summary);
                if (this.mAppBubble == 1) {
                }
                textView.setText(getResources().getString(R.string.notification_channel_summary_default));
                findViewById(R.id.priority).setOnClickListener(this.mOnFavoriteClick);
                findViewById(R.id.default_behavior).setOnClickListener(this.mOnDefaultClick);
                findViewById(R.id.silence).setOnClickListener(this.mOnMuteClick);
                View findViewById3 = findViewById(R.id.info);
                i2 = this.mAppUid;
                if (i2 >= 0) {
                    onClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            NotificationConversationInfo notificationConversationInfo = NotificationConversationInfo.this;
                            notificationConversationInfo.mOnSettingsClickListener.onClick(i2, notificationConversationInfo.mNotificationChannel);
                        }
                    };
                }
                findViewById3.setOnClickListener(onClickListener);
                findViewById3.setVisibility(findViewById3.hasOnClickListeners() ? 0 : 8);
                i3 = this.mSelectedAction;
                if (i3 == -1) {
                }
                updateToggleActions(i3, false);
                View findViewById22 = findViewById(R.id.done);
                findViewById22.setOnClickListener(this.mOnDone);
                findViewById22.setAccessibilityDelegate(this.mGutsContainer.getAccessibilityDelegate());
            }
        }
        charSequence = null;
        TextView textView22 = (TextView) findViewById(R.id.group_name);
        if (charSequence == null) {
        }
        applicationInfo = this.mPm.getApplicationInfo(this.mPackageName, 795136);
        if (applicationInfo != null) {
        }
        ((TextView) findViewById(R.id.pkg_name)).setText(this.mAppName);
        bindIcon(this.mNotificationChannel.isImportantConversation());
        this.mPriorityDescriptionView = (TextView) findViewById(R.id.priority_summary);
        if (this.mBubbleMetadata == null && BubblesManager.areBubblesEnabled(((LinearLayout) this).mContext, this.mSbn.getUser())) {
        }
        if (this.mBubbleMetadata == null && BubblesManager.areBubblesEnabled(((LinearLayout) this).mContext, this.mSbn.getUser())) {
        }
        TextView textView322 = (TextView) findViewById(R.id.delegate_name);
        if (TextUtils.equals(this.mPackageName, this.mDelegatePkg)) {
        }
        textView = (TextView) findViewById(R.id.default_summary);
        if (this.mAppBubble == 1) {
        }
        textView.setText(getResources().getString(R.string.notification_channel_summary_default));
        findViewById(R.id.priority).setOnClickListener(this.mOnFavoriteClick);
        findViewById(R.id.default_behavior).setOnClickListener(this.mOnDefaultClick);
        findViewById(R.id.silence).setOnClickListener(this.mOnMuteClick);
        View findViewById32 = findViewById(R.id.info);
        i2 = this.mAppUid;
        if (i2 >= 0) {
        }
        findViewById32.setOnClickListener(onClickListener);
        findViewById32.setVisibility(findViewById32.hasOnClickListeners() ? 0 : 8);
        i3 = this.mSelectedAction;
        if (i3 == -1) {
        }
        updateToggleActions(i3, false);
        View findViewById222 = findViewById(R.id.done);
        findViewById222.setOnClickListener(this.mOnDone);
        findViewById222.setAccessibilityDelegate(this.mGutsContainer.getAccessibilityDelegate());
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
        int i2 = 0;
        if (z && (i = this.mSelectedAction) > -1) {
            this.mBgHandler.post(new UpdateChannelRunnable(this.mINotificationManager, this.mPackageName, this.mAppUid, i, this.mNotificationChannel));
            this.mEntry.mIsMarkedForUserTriggeredMovement = true;
            this.mMainHandler.postDelayed(new NotificationConversationInfo$$ExternalSyntheticLambda1(this, i2), 360L);
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

    /* JADX WARN: Multi-variable type inference failed */
    public final void updateToggleActions(int i, boolean z) {
        char c = 1;
        final int i2 = 2;
        final int i3 = 0;
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
            post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            View view = findViewById;
                            View view2 = findViewById2;
                            View view3 = findViewById3;
                            int i4 = NotificationConversationInfo.$r8$clinit;
                            view.setSelected(true);
                            view2.setSelected(false);
                            view3.setSelected(false);
                            break;
                        case 1:
                            View view4 = findViewById;
                            View view5 = findViewById2;
                            View view6 = findViewById3;
                            int i5 = NotificationConversationInfo.$r8$clinit;
                            view4.setSelected(false);
                            view5.setSelected(false);
                            view6.setSelected(true);
                            break;
                        default:
                            View view7 = findViewById;
                            View view8 = findViewById2;
                            View view9 = findViewById3;
                            int i6 = NotificationConversationInfo.$r8$clinit;
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
            post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i3) {
                        case 0:
                            View view = findViewById;
                            View view2 = findViewById2;
                            View view3 = findViewById3;
                            int i4 = NotificationConversationInfo.$r8$clinit;
                            view.setSelected(true);
                            view2.setSelected(false);
                            view3.setSelected(false);
                            break;
                        case 1:
                            View view4 = findViewById;
                            View view5 = findViewById2;
                            View view6 = findViewById3;
                            int i5 = NotificationConversationInfo.$r8$clinit;
                            view4.setSelected(false);
                            view5.setSelected(false);
                            view6.setSelected(true);
                            break;
                        default:
                            View view7 = findViewById;
                            View view8 = findViewById2;
                            View view9 = findViewById3;
                            int i6 = NotificationConversationInfo.$r8$clinit;
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
            final char c2 = c == true ? 1 : 0;
            post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    switch (c2) {
                        case 0:
                            View view = findViewById;
                            View view2 = findViewById2;
                            View view3 = findViewById3;
                            int i4 = NotificationConversationInfo.$r8$clinit;
                            view.setSelected(true);
                            view2.setSelected(false);
                            view3.setSelected(false);
                            break;
                        case 1:
                            View view4 = findViewById;
                            View view5 = findViewById2;
                            View view6 = findViewById3;
                            int i5 = NotificationConversationInfo.$r8$clinit;
                            view4.setSelected(false);
                            view5.setSelected(false);
                            view6.setSelected(true);
                            break;
                        default:
                            View view7 = findViewById;
                            View view8 = findViewById2;
                            View view9 = findViewById3;
                            int i6 = NotificationConversationInfo.$r8$clinit;
                            view7.setSelected(false);
                            view8.setSelected(true);
                            view9.setSelected(false);
                            break;
                    }
                }
            });
        }
        ((TextView) findViewById(R.id.done)).setText((getPriority() != i) != false ? R.string.inline_ok_button : R.string.inline_done_button);
        bindIcon(i == 2);
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean willBeRemoved() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final View getContentView() {
        return this;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final void onFinishedClosing() {
    }
}
