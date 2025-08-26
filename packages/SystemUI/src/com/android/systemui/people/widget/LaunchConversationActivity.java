package com.android.systemui.people.widget;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.app.UnlaunchableAppActivity;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.people.PeopleSpaceUtils;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.wmshell.BubblesManager;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda14;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda4;
import com.android.wm.shell.bubbles.BubbleEntry;
import java.util.Optional;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class LaunchConversationActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Executor mBgExecutor;
    public Bubble mBubble;
    public final Optional mBubblesManagerOptional;
    public final CommandQueue mCommandQueue;
    public final CommonNotifCollection mCommonNotifCollection;
    public NotificationEntry mEntryToBubble;
    public IStatusBarService mIStatusBarService;
    public boolean mIsForTesting;
    public final UiEventLogger mUiEventLogger = new UiEventLoggerImpl();
    public final UserManager mUserManager;
    public final NotificationVisibilityProvider mVisibilityProvider;

    public LaunchConversationActivity(NotificationVisibilityProvider notificationVisibilityProvider, CommonNotifCollection commonNotifCollection, Optional<BubblesManager> optional, UserManager userManager, CommandQueue commandQueue, Executor executor) {
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mCommonNotifCollection = commonNotifCollection;
        this.mBubblesManagerOptional = optional;
        this.mUserManager = userManager;
        this.mCommandQueue = commandQueue;
        commandQueue.addCallback(new CommandQueue.Callbacks() { // from class: com.android.systemui.people.widget.LaunchConversationActivity.1
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void appTransitionFinished(int i) {
                LaunchConversationActivity launchConversationActivity = LaunchConversationActivity.this;
                if (launchConversationActivity.mBubblesManagerOptional.isPresent()) {
                    if (launchConversationActivity.mBubble != null) {
                        BubblesManager bubblesManager = (BubblesManager) launchConversationActivity.mBubblesManagerOptional.get();
                        Bubble bubble = launchConversationActivity.mBubble;
                        BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                        BubbleController.this.mMainExecutor.execute(new BubbleController$$ExternalSyntheticLambda14(2, bubblesImpl, bubble));
                    } else if (launchConversationActivity.mEntryToBubble != null) {
                        BubblesManager bubblesManager2 = (BubblesManager) launchConversationActivity.mBubblesManagerOptional.get();
                        BubbleEntry bubbleEntryNotifToBubbleEntry = bubblesManager2.notifToBubbleEntry(launchConversationActivity.mEntryToBubble);
                        BubbleController.BubblesImpl bubblesImpl2 = (BubbleController.BubblesImpl) bubblesManager2.mBubbles;
                        BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda4(bubblesImpl2, bubbleEntryNotifToBubbleEntry, 1));
                    }
                }
                launchConversationActivity.mCommandQueue.removeCallback((CommandQueue.Callbacks) this);
            }
        });
        this.mBgExecutor = executor;
    }

    public final void clearNotificationIfPresent(final String str, final String str2, final UserHandle userHandle) {
        CommonNotifCollection commonNotifCollection;
        NotificationEntry entry;
        if (TextUtils.isEmpty(str) || this.mIStatusBarService == null || (commonNotifCollection = this.mCommonNotifCollection) == null || (entry = ((NotifPipeline) commonNotifCollection).mNotifCollection.getEntry(str)) == null || entry.mRanking == null) {
            return;
        }
        final NotificationVisibility notificationVisibilityObtain = ((NotificationVisibilityProviderImpl) this.mVisibilityProvider).obtain(entry);
        int i = notificationVisibilityObtain.rank;
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.people.widget.LaunchConversationActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LaunchConversationActivity launchConversationActivity = this.f$0;
                String str3 = str2;
                UserHandle userHandle2 = userHandle;
                String str4 = str;
                NotificationVisibility notificationVisibility = notificationVisibilityObtain;
                int i2 = LaunchConversationActivity.$r8$clinit;
                launchConversationActivity.getClass();
                try {
                    launchConversationActivity.mIStatusBarService.onNotificationClear(str3, userHandle2.getIdentifier(), str4, 0, 2, notificationVisibility);
                } catch (RemoteException e) {
                    Log.e("PeopleSpaceLaunchConv", "Exception cancelling notification:" + e);
                }
            }
        });
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        Bubble bubble;
        if (!this.mIsForTesting) {
            super.onCreate(bundle);
        }
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("extra_tile_id");
        String stringExtra2 = intent.getStringExtra("extra_package_name");
        UserHandle userHandle = (UserHandle) intent.getParcelableExtra("extra_user_handle");
        String stringExtra3 = intent.getStringExtra("extra_notification_key");
        if (!TextUtils.isEmpty(stringExtra)) {
            this.mUiEventLogger.log(PeopleSpaceUtils.PeopleSpaceWidgetEvent.PEOPLE_SPACE_WIDGET_CLICKED);
            try {
                if (this.mUserManager.isQuietModeEnabled(userHandle)) {
                    getApplicationContext().startActivity(UnlaunchableAppActivity.createInQuietModeDialogIntent(userHandle.getIdentifier()));
                    finish();
                    return;
                }
                if (this.mBubblesManagerOptional.isPresent()) {
                    BubbleController.BubblesImpl.CachedState cachedState = ((BubbleController.BubblesImpl) ((BubblesManager) this.mBubblesManagerOptional.get()).mBubbles).mCachedState;
                    synchronized (cachedState) {
                        bubble = (Bubble) cachedState.mShortcutIdToBubble.get(stringExtra);
                    }
                    this.mBubble = bubble;
                    NotificationEntry entry = ((NotifPipeline) this.mCommonNotifCollection).mNotifCollection.getEntry(stringExtra3);
                    if (this.mBubble != null || (entry != null && entry.mRanking.canBubble())) {
                        this.mEntryToBubble = entry;
                        finish();
                        return;
                    }
                }
                if (this.mIStatusBarService == null) {
                    this.mIStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
                }
                clearNotificationIfPresent(stringExtra3, stringExtra2, userHandle);
                ((LauncherApps) getApplicationContext().getSystemService(LauncherApps.class)).startShortcut(stringExtra2, stringExtra, null, null, userHandle);
            } catch (Exception e) {
                EmergencyButton$$ExternalSyntheticOutline0.m("Exception launching shortcut:", e, "PeopleSpaceLaunchConv");
            }
        }
        finish();
    }

    public void setIsForTesting(boolean z, IStatusBarService iStatusBarService) {
        this.mIsForTesting = z;
        this.mIStatusBarService = iStatusBarService;
    }
}
