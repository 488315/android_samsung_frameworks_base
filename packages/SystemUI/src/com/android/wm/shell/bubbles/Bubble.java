package com.android.wm.shell.bubbles;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Person;
import android.app.TaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.LocusId;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Parcelable;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.text.TextUtils;
import android.util.Log;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleTransitions;
import com.android.wm.shell.bubbles.BubbleViewInfoTask;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;
import com.android.wm.shell.bubbles.bar.BubbleBarLayerView;
import com.android.wm.shell.common.ComponentUtils;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.taskview.TaskView;
import com.android.wm.shell.taskview.TaskViewTaskController;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class Bubble implements BubbleViewProvider {
    public String mAppName;
    public int mAppUid;
    public Bitmap mBadgeBitmap;
    public final Executor mBgExecutor;
    public BubbleBarExpandedView mBubbleBarExpandedView;
    public Bitmap mBubbleBitmap;
    public final Bubbles.BubbleMetadataFlagListener mBubbleMetadataFlagListener;
    public BubbleTaskView mBubbleTaskView;
    public String mChannelId;
    public PendingIntent mDeleteIntent;
    public int mDesiredHeight;
    public int mDesiredHeightResId;
    public int mDotColor;
    public Path mDotPath;
    public BubbleExpandedView mExpandedView;
    public int mFlags;
    public FlyoutMessage mFlyoutMessage;
    public final String mGroupKey;
    public Icon mIcon;
    public BadgedImageView mIconView;
    public boolean mInflateSynchronously;
    public BubbleViewInfoTask mInflationTask;
    public InstanceId mInstanceId;
    public Intent mIntent;
    public boolean mIsBubble;
    public boolean mIsDismissable;
    public boolean mIsImportantConversation;
    public boolean mIsTextChanged;
    public final String mKey;
    public long mLastAccessed;
    public long mLastUpdated;
    public final LocusId mLocusId;
    public final Executor mMainExecutor;
    public String mMetadataShortcutId;
    public int mNotificationId;
    public String mPackageName;
    public PendingIntent mPendingIntent;
    public boolean mPendingIntentActive;
    public final Bubble$$ExternalSyntheticLambda0 mPendingIntentCancelListener;
    public boolean mPendingIntentCanceled;
    public BubbleTransitions.BubbleTransition mPreparingTransition;
    public Bitmap mRawBadgeBitmap;
    public ShortcutInfo mShortcutInfo;
    public boolean mShouldSuppressNotificationDot;
    public boolean mShouldSuppressNotificationList;
    public boolean mShouldSuppressPeek;
    public boolean mShowBubbleUpdateDot;
    public boolean mSuppressFlyout;
    public final int mTaskId;
    public String mTitle;
    public final BubbleType mType;
    public UserHandle mUser;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum BubbleType {
        TYPE_CHAT,
        TYPE_NOTE,
        TYPE_SHORTCUT,
        TYPE_APP
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class FlyoutMessage {
        public boolean isGroupChat;
        public CharSequence message;
        public Drawable senderAvatar;
        public Icon senderIcon;
        public CharSequence senderName;
    }

    public Bubble(String str, ShortcutInfo shortcutInfo, int i, int i2, String str2, int i3, String str3, boolean z, Executor executor, Executor executor2, Bubbles.BubbleMetadataFlagListener bubbleMetadataFlagListener) {
        this.mShowBubbleUpdateDot = true;
        this.mAppUid = -1;
        Objects.requireNonNull(str);
        Objects.requireNonNull(shortcutInfo);
        this.mMetadataShortcutId = shortcutInfo.getId();
        this.mShortcutInfo = shortcutInfo;
        this.mKey = str;
        this.mGroupKey = null;
        this.mLocusId = str3 != null ? new LocusId(str3) : null;
        this.mIsDismissable = z;
        this.mFlags = 0;
        this.mUser = shortcutInfo.getUserHandle();
        this.mPackageName = shortcutInfo.getPackage();
        this.mIcon = shortcutInfo.getIcon();
        this.mDesiredHeight = i;
        this.mDesiredHeightResId = i2;
        this.mTitle = str2;
        this.mShowBubbleUpdateDot = false;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mTaskId = i3;
        this.mBubbleMetadataFlagListener = bubbleMetadataFlagListener;
        this.mType = BubbleType.TYPE_CHAT;
    }

    public static Bubble createNotesBubble(Intent intent, UserHandle userHandle, Icon icon, Executor executor, Executor executor2) {
        return new Bubble(intent, userHandle, icon, BubbleType.TYPE_NOTE, getNoteBubbleKeyForApp(intent.getPackage(), userHandle), executor, executor2);
    }

    public static String getNoteBubbleKeyForApp(String str, UserHandle userHandle) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(userHandle);
        return "key_note_bubble:" + userHandle.getIdentifier() + ":" + str;
    }

    public final void cleanupExpandedView(boolean z) {
        BubbleTaskView bubbleTaskView;
        BubbleExpandedView bubbleExpandedView = this.mExpandedView;
        if (bubbleExpandedView != null) {
            TaskView taskView = bubbleExpandedView.mTaskView;
            if (taskView != null) {
                taskView.setVisibility(8);
            }
            this.mExpandedView = null;
        }
        BubbleBarExpandedView bubbleBarExpandedView = this.mBubbleBarExpandedView;
        if (bubbleBarExpandedView != null) {
            bubbleBarExpandedView.mMenuViewController.hideMenu(false);
            this.mBubbleBarExpandedView = null;
        }
        if (z && (bubbleTaskView = this.mBubbleTaskView) != null) {
            TaskView taskView2 = bubbleTaskView.taskView;
            taskView2.mTaskViewController.removeTaskView(taskView2.mTaskViewTaskController, null);
            this.mBubbleTaskView = null;
        }
        PendingIntent pendingIntent = this.mPendingIntent;
        if (pendingIntent != null) {
            pendingIntent.unregisterCancelListener(this.mPendingIntentCancelListener);
        }
        this.mPendingIntentActive = false;
    }

    public final void cleanupViews() {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -7367924038303663062L, 0, String.valueOf(this.mKey));
        }
        cleanupExpandedView(true);
        this.mIconView = null;
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.print("key: ");
        printWriter.println(this.mKey);
        printWriter.print("  showInShade:   ");
        printWriter.println(showInShade());
        printWriter.print("  showDot:       ");
        printWriter.println(showDot());
        printWriter.print("  showFlyout:    ");
        printWriter.println(showFlyout());
        printWriter.print("  lastActivity:  ");
        printWriter.println(Math.max(this.mLastUpdated, this.mLastAccessed));
        printWriter.print("  desiredHeight: ");
        int i = this.mDesiredHeightResId;
        printWriter.println(i != 0 ? String.valueOf(i) : String.valueOf(this.mDesiredHeight));
        printWriter.print("  suppressNotif: ");
        printWriter.println(isEnabled(2));
        printWriter.print("  autoExpand:    ");
        printWriter.println(isEnabled(1));
        printWriter.print("  isDismissable: ");
        printWriter.println(this.mIsDismissable);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  bubbleMetadataFlagListener null?: "), this.mBubbleMetadataFlagListener == null, printWriter);
        BubbleExpandedView bubbleExpandedView = this.mExpandedView;
        if (bubbleExpandedView != null) {
            printWriter.print("  ");
            printWriter.println("BubbleExpandedView:");
            printWriter.print("  ");
            printWriter.print("  taskId: ");
            printWriter.println(bubbleExpandedView.mTaskId);
            printWriter.print("  ");
            printWriter.print("  stackView: ");
            printWriter.println(bubbleExpandedView.mStackView);
            printWriter.print("  ");
            printWriter.print("  contentVisibility: ");
            printWriter.println(bubbleExpandedView.mIsContentVisible);
            printWriter.print("  ");
            printWriter.print("  isAnimating: ");
            printWriter.println(bubbleExpandedView.mIsAnimating);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Bubble) {
            return Objects.equals(this.mKey, ((Bubble) obj).mKey);
        }
        return false;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final Bitmap getAppBadge() {
        return this.mBadgeBitmap;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final BubbleBarExpandedView getBubbleBarExpandedView() {
        return this.mBubbleBarExpandedView;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final Bitmap getBubbleIcon() {
        return this.mBubbleBitmap;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final int getDotColor() {
        return this.mDotColor;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final Path getDotPath() {
        return this.mDotPath;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final BubbleExpandedView getExpandedView() {
        return this.mExpandedView;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final BadgedImageView getIconView$1() {
        return this.mIconView;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final String getKey() {
        return this.mKey;
    }

    public final BubbleTaskView getOrCreateBubbleTaskView(BubbleTaskViewFactory bubbleTaskViewFactory) {
        if (this.mBubbleTaskView == null) {
            BubbleController.AnonymousClass1 anonymousClass1 = (BubbleController.AnonymousClass1) bubbleTaskViewFactory;
            anonymousClass1.getClass();
            Context context = anonymousClass1.val$context;
            BubbleController bubbleController = BubbleController.this;
            this.mBubbleTaskView = new BubbleTaskView(new TaskView(anonymousClass1.val$context, bubbleController.mTaskViewController, new TaskViewTaskController(context, anonymousClass1.val$organizer, bubbleController.mTaskViewController, anonymousClass1.val$syncQueue)), anonymousClass1.val$mainExecutor);
        }
        return this.mBubbleTaskView;
    }

    public final Intent getSettingsIntent(Context context) {
        Intent intent = new Intent("android.settings.APP_NOTIFICATION_BUBBLE_SETTINGS");
        String str = this.mChannelId;
        if (str == null || str.equals("miscellaneous")) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
        } else {
            intent.setAction("android.settings.CHANNEL_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.CHANNEL_ID", this.mChannelId);
        }
        intent.putExtra("android.provider.extra.APP_PACKAGE", this.mPackageName);
        int i = this.mAppUid;
        if (i == -1) {
            PackageManager packageManagerForUser = BubbleController.getPackageManagerForUser(this.mUser.getIdentifier(), context);
            if (packageManagerForUser != null) {
                try {
                    i = packageManagerForUser.getApplicationInfo(this.mShortcutInfo.getPackage(), 0).uid;
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("Bubble", "cannot find uid", e);
                }
            }
            i = -1;
        }
        if (i != -1) {
            intent.putExtra("app_uid", i);
        }
        intent.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
        intent.addFlags(268435456);
        return intent;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final int getTaskId() {
        BubbleBarExpandedView bubbleBarExpandedView = this.mBubbleBarExpandedView;
        if (bubbleBarExpandedView == null) {
            BubbleExpandedView bubbleExpandedView = this.mExpandedView;
            return bubbleExpandedView != null ? bubbleExpandedView.mTaskId : this.mTaskId;
        }
        BubbleTaskViewListener bubbleTaskViewListener = bubbleBarExpandedView.mBubbleTaskViewListener;
        if (bubbleTaskViewListener != null) {
            return bubbleTaskViewListener.mTaskId;
        }
        return -1;
    }

    public final boolean hasMetadataShortcutId() {
        String str = this.mMetadataShortcutId;
        return (str == null || str.isEmpty()) ? false : true;
    }

    public final int hashCode() {
        return Objects.hash(this.mKey);
    }

    public final void inflate(BubbleViewInfoTask.Callback callback, Context context, BubbleExpandedViewManager bubbleExpandedViewManager, BubbleTaskViewFactory bubbleTaskViewFactory, BubblePositioner bubblePositioner, BubbleStackView bubbleStackView, BubbleBarLayerView bubbleBarLayerView, BubbleIconFactory bubbleIconFactory, BubbleBadgeIconFactory bubbleBadgeIconFactory, boolean z) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_BUBBLES, 5368920303216639299L, 0, String.valueOf(this.mKey));
        }
        BubbleViewInfoTask bubbleViewInfoTask = this.mInflationTask;
        if (bubbleViewInfoTask != null && !bubbleViewInfoTask.mFinished.get()) {
            this.mInflationTask.mCancelled.set(true);
        }
        final BubbleViewInfoTask bubbleViewInfoTask2 = new BubbleViewInfoTask(this, context, bubbleExpandedViewManager, bubbleTaskViewFactory, bubblePositioner, bubbleStackView, bubbleBarLayerView, bubbleIconFactory, bubbleBadgeIconFactory, z, callback, this.mMainExecutor, this.mBgExecutor);
        this.mInflationTask = bubbleViewInfoTask2;
        if (!this.mInflateSynchronously) {
            if (bubbleViewInfoTask2.mStarted.getAndSet(true)) {
                throw new IllegalStateException("Task already started");
            }
            if (bubbleViewInfoTask2.mCancelled.get()) {
                bubbleViewInfoTask2.mFinished.set(true);
                return;
            } else {
                bubbleViewInfoTask2.mBgExecutor.execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleViewInfoTask$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        final BubbleViewInfoTask bubbleViewInfoTask3 = BubbleViewInfoTask.this;
                        if (bubbleViewInfoTask3.mCancelled.get()) {
                            bubbleViewInfoTask3.mFinished.set(true);
                            return;
                        }
                        final BubbleViewInfoTask.BubbleViewInfo loadViewInfo = bubbleViewInfoTask3.loadViewInfo();
                        if (bubbleViewInfoTask3.mCancelled.get()) {
                            bubbleViewInfoTask3.mFinished.set(true);
                        } else {
                            bubbleViewInfoTask3.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleViewInfoTask$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BubbleViewInfoTask bubbleViewInfoTask4 = BubbleViewInfoTask.this;
                                    BubbleViewInfoTask.BubbleViewInfo bubbleViewInfo = loadViewInfo;
                                    if (!bubbleViewInfoTask4.mCancelled.get()) {
                                        bubbleViewInfoTask4.updateViewInfo(bubbleViewInfo);
                                    }
                                    bubbleViewInfoTask4.mFinished.set(true);
                                }
                            });
                        }
                    }
                });
                return;
            }
        }
        if (bubbleViewInfoTask2.mStarted.getAndSet(true)) {
            throw new IllegalStateException("Task already started");
        }
        if (bubbleViewInfoTask2.mCancelled.get()) {
            bubbleViewInfoTask2.mFinished.set(true);
        } else {
            bubbleViewInfoTask2.updateViewInfo(bubbleViewInfoTask2.loadViewInfo());
            bubbleViewInfoTask2.mFinished.set(true);
        }
    }

    public final boolean isChat() {
        return this.mType == BubbleType.TYPE_CHAT;
    }

    public final boolean isEnabled(int i) {
        return (this.mFlags & i) != 0;
    }

    public final boolean isInflated() {
        return ((this.mIconView == null || this.mExpandedView == null) && this.mBubbleBarExpandedView == null) ? false : true;
    }

    public final boolean isNote() {
        return this.mType == BubbleType.TYPE_NOTE;
    }

    public final void setEntry(BubbleEntry bubbleEntry) {
        Objects.requireNonNull(bubbleEntry);
        boolean showDot = showDot();
        this.mLastUpdated = bubbleEntry.mSbn.getPostTime();
        this.mIsBubble = bubbleEntry.mSbn.getNotification().isBubbleNotification();
        this.mPackageName = bubbleEntry.mSbn.getPackageName();
        this.mUser = bubbleEntry.mSbn.getUser();
        CharSequence charSequence = bubbleEntry.mSbn.getNotification().extras.getCharSequence("android.title");
        this.mTitle = charSequence == null ? null : charSequence.toString();
        NotificationListenerService.Ranking ranking = bubbleEntry.mRanking;
        this.mChannelId = (ranking == null || ranking.getChannel() == null) ? bubbleEntry.mSbn.getNotification().getChannelId() : bubbleEntry.mRanking.getChannel().getId();
        this.mNotificationId = bubbleEntry.mSbn.getId();
        this.mAppUid = bubbleEntry.mSbn.getUid();
        this.mInstanceId = bubbleEntry.mSbn.getInstanceId();
        Notification notification2 = bubbleEntry.mSbn.getNotification();
        Class notificationStyle = notification2.getNotificationStyle();
        FlyoutMessage flyoutMessage = new FlyoutMessage();
        flyoutMessage.isGroupChat = notification2.extras.getBoolean("android.isGroupConversation");
        try {
            if (Notification.BigTextStyle.class.equals(notificationStyle)) {
                CharSequence charSequence2 = notification2.extras.getCharSequence("android.bigText");
                if (TextUtils.isEmpty(charSequence2)) {
                    charSequence2 = notification2.extras.getCharSequence("android.text");
                }
                flyoutMessage.message = charSequence2;
            } else if (Notification.MessagingStyle.class.equals(notificationStyle)) {
                Notification.MessagingStyle.Message findLatestIncomingMessage = Notification.MessagingStyle.findLatestIncomingMessage(Notification.MessagingStyle.Message.getMessagesFromBundleArray((Parcelable[]) notification2.extras.get("android.messages")));
                if (findLatestIncomingMessage != null) {
                    flyoutMessage.message = findLatestIncomingMessage.getText();
                    Person senderPerson = findLatestIncomingMessage.getSenderPerson();
                    flyoutMessage.senderName = senderPerson != null ? senderPerson.getName() : null;
                    flyoutMessage.senderAvatar = null;
                    flyoutMessage.senderIcon = senderPerson != null ? senderPerson.getIcon() : null;
                }
            } else if (Notification.InboxStyle.class.equals(notificationStyle)) {
                CharSequence[] charSequenceArray = notification2.extras.getCharSequenceArray("android.textLines");
                if (charSequenceArray != null && charSequenceArray.length > 0) {
                    flyoutMessage.message = charSequenceArray[charSequenceArray.length - 1];
                }
            } else if (!Notification.MediaStyle.class.equals(notificationStyle)) {
                flyoutMessage.message = notification2.extras.getCharSequence("android.text");
            }
        } catch (ArrayIndexOutOfBoundsException | ClassCastException | NullPointerException e) {
            e.printStackTrace();
        }
        this.mFlyoutMessage = flyoutMessage;
        NotificationListenerService.Ranking ranking2 = bubbleEntry.mRanking;
        if (ranking2 != null) {
            this.mShortcutInfo = ranking2.getConversationShortcutInfo();
            this.mIsTextChanged = bubbleEntry.mRanking.isTextChanged();
            if (bubbleEntry.mRanking.getChannel() != null) {
                this.mIsImportantConversation = bubbleEntry.mRanking.getChannel().isImportantConversation();
            }
        }
        if (bubbleEntry.getBubbleMetadata() != null) {
            this.mMetadataShortcutId = bubbleEntry.getBubbleMetadata().getShortcutId();
            this.mFlags = bubbleEntry.getBubbleMetadata().getFlags();
            this.mDesiredHeight = bubbleEntry.getBubbleMetadata().getDesiredHeight();
            this.mDesiredHeightResId = bubbleEntry.getBubbleMetadata().getDesiredHeightResId();
            this.mIcon = bubbleEntry.getBubbleMetadata().getIcon();
            if (!this.mPendingIntentActive || this.mPendingIntent == null) {
                PendingIntent pendingIntent = this.mPendingIntent;
                if (pendingIntent != null) {
                    pendingIntent.unregisterCancelListener(this.mPendingIntentCancelListener);
                }
                PendingIntent intent = bubbleEntry.getBubbleMetadata().getIntent();
                this.mPendingIntent = intent;
                if (intent != null) {
                    intent.registerCancelListener(this.mPendingIntentCancelListener);
                }
            } else if (bubbleEntry.getBubbleMetadata().getIntent() == null) {
                this.mPendingIntent.unregisterCancelListener(this.mPendingIntentCancelListener);
                this.mPendingIntentActive = false;
                this.mPendingIntent = null;
            }
            this.mDeleteIntent = bubbleEntry.getBubbleMetadata().getDeleteIntent();
        }
        this.mIsDismissable = bubbleEntry.mIsDismissable;
        this.mShouldSuppressNotificationDot = bubbleEntry.mShouldSuppressNotificationDot;
        this.mShouldSuppressNotificationList = bubbleEntry.mShouldSuppressNotificationList;
        this.mShouldSuppressPeek = bubbleEntry.mShouldSuppressPeek;
        if (showDot != showDot()) {
            setShowDot(showDot());
        }
    }

    public void setInflateSynchronously(boolean z) {
        this.mInflateSynchronously = z;
    }

    public void setShouldAutoExpand(boolean z) {
        Bubbles.BubbleMetadataFlagListener bubbleMetadataFlagListener;
        boolean isEnabled = isEnabled(1);
        if (z) {
            this.mFlags = 1 | this.mFlags;
        } else {
            this.mFlags &= -2;
        }
        if (isEnabled == z || (bubbleMetadataFlagListener = this.mBubbleMetadataFlagListener) == null) {
            return;
        }
        bubbleMetadataFlagListener.onBubbleMetadataFlagChanged(this);
    }

    public final void setShowDot(boolean z) {
        this.mShowBubbleUpdateDot = z;
        BadgedImageView badgedImageView = this.mIconView;
        if (badgedImageView != null) {
            badgedImageView.updateDotVisibility(true);
        }
    }

    public final void setSuppressBubble(boolean z) {
        Bubbles.BubbleMetadataFlagListener bubbleMetadataFlagListener;
        int i = this.mFlags;
        if ((i & 4) == 0) {
            Log.e("Bubble", "calling setSuppressBubble on " + this.mKey + " when bubble not suppressable");
            return;
        }
        boolean z2 = (i & 8) != 0;
        if (z) {
            this.mFlags = i | 8;
        } else {
            this.mFlags = i & (-9);
        }
        if (z2 == z || (bubbleMetadataFlagListener = this.mBubbleMetadataFlagListener) == null) {
            return;
        }
        bubbleMetadataFlagListener.onBubbleMetadataFlagChanged(this);
    }

    public void setSuppressNotification(boolean z) {
        Bubbles.BubbleMetadataFlagListener bubbleMetadataFlagListener;
        boolean showInShade = showInShade();
        if (z) {
            this.mFlags |= 2;
        } else {
            this.mFlags &= -3;
        }
        if (showInShade() == showInShade || (bubbleMetadataFlagListener = this.mBubbleMetadataFlagListener) == null) {
            return;
        }
        bubbleMetadataFlagListener.onBubbleMetadataFlagChanged(this);
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final void setTaskViewVisibility() {
        BubbleExpandedView bubbleExpandedView = this.mExpandedView;
        if (bubbleExpandedView != null) {
            bubbleExpandedView.setContentVisibility(false);
        }
    }

    public void setTextChangedForTest(boolean z) {
        this.mIsTextChanged = z;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final boolean showDot() {
        return (!this.mShowBubbleUpdateDot || this.mShouldSuppressNotificationDot || isEnabled(2)) ? false : true;
    }

    public boolean showFlyout() {
        return (this.mSuppressFlyout || this.mShouldSuppressPeek || isEnabled(2) || this.mShouldSuppressNotificationList) ? false : true;
    }

    public final boolean showInShade() {
        return (isEnabled(2) && this.mIsDismissable) ? false : true;
    }

    public final String toString() {
        return OpaqueKey$$ExternalSyntheticOutline0.m(new StringBuilder("Bubble{"), this.mKey, '}');
    }

    private Bubble(Intent intent, UserHandle userHandle, Icon icon, BubbleType bubbleType, String str, Executor executor, Executor executor2) {
        this.mAppUid = -1;
        this.mGroupKey = null;
        this.mLocusId = null;
        this.mFlags = 0;
        this.mUser = userHandle;
        this.mIcon = icon;
        this.mType = bubbleType;
        this.mKey = str;
        this.mShowBubbleUpdateDot = false;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mTaskId = -1;
        this.mIntent = intent;
        this.mDesiredHeight = Integer.MAX_VALUE;
        this.mPackageName = intent.getPackage();
    }

    private Bubble(PendingIntent pendingIntent, UserHandle userHandle, String str, Executor executor, Executor executor2) {
        this.mShowBubbleUpdateDot = true;
        this.mAppUid = -1;
        this.mGroupKey = null;
        this.mLocusId = null;
        this.mFlags = 0;
        this.mUser = userHandle;
        this.mIcon = null;
        this.mType = BubbleType.TYPE_APP;
        this.mKey = str;
        this.mShowBubbleUpdateDot = false;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mTaskId = -1;
        this.mPendingIntent = pendingIntent;
        this.mIntent = null;
        this.mDesiredHeight = Integer.MAX_VALUE;
        this.mPackageName = ComponentUtils.getPackageName(pendingIntent);
    }

    private Bubble(ShortcutInfo shortcutInfo, Executor executor, Executor executor2) {
        this.mShowBubbleUpdateDot = true;
        this.mAppUid = -1;
        this.mGroupKey = null;
        this.mLocusId = null;
        this.mFlags = 0;
        this.mUser = shortcutInfo.getUserHandle();
        this.mIcon = shortcutInfo.getIcon();
        this.mType = BubbleType.TYPE_SHORTCUT;
        this.mKey = shortcutInfo.getPackage() + ":" + shortcutInfo.getUserId() + ":" + shortcutInfo.getId();
        this.mShowBubbleUpdateDot = false;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mTaskId = -1;
        this.mIntent = null;
        this.mDesiredHeight = Integer.MAX_VALUE;
        this.mPackageName = shortcutInfo.getPackage();
        this.mShortcutInfo = shortcutInfo;
    }

    private Bubble(TaskInfo taskInfo, UserHandle userHandle, Icon icon, String str, Executor executor, Executor executor2) {
        this.mShowBubbleUpdateDot = true;
        this.mAppUid = -1;
        this.mGroupKey = null;
        this.mLocusId = null;
        this.mFlags = 0;
        this.mUser = userHandle;
        this.mIcon = icon;
        this.mType = BubbleType.TYPE_APP;
        this.mKey = str;
        this.mShowBubbleUpdateDot = false;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mTaskId = taskInfo.taskId;
        this.mIntent = null;
        this.mDesiredHeight = Integer.MAX_VALUE;
        this.mPackageName = taskInfo.baseActivity.getPackageName();
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.wm.shell.bubbles.Bubble$$ExternalSyntheticLambda0] */
    public Bubble(BubbleEntry bubbleEntry, Bubbles.BubbleMetadataFlagListener bubbleMetadataFlagListener, final Bubbles.PendingIntentCanceledListener pendingIntentCanceledListener, final Executor executor, Executor executor2) {
        this.mShowBubbleUpdateDot = true;
        this.mAppUid = -1;
        this.mType = BubbleType.TYPE_CHAT;
        this.mKey = bubbleEntry.mSbn.getKey();
        this.mGroupKey = bubbleEntry.mSbn.getGroupKey();
        this.mLocusId = bubbleEntry.mSbn.getNotification().getLocusId();
        this.mBubbleMetadataFlagListener = bubbleMetadataFlagListener;
        this.mPendingIntentCancelListener = new PendingIntent.CancelListener() { // from class: com.android.wm.shell.bubbles.Bubble$$ExternalSyntheticLambda0
            public final void onCanceled(PendingIntent pendingIntent) {
                final Bubble bubble = Bubble.this;
                Executor executor3 = executor;
                final Bubbles.PendingIntentCanceledListener pendingIntentCanceledListener2 = pendingIntentCanceledListener;
                PendingIntent pendingIntent2 = bubble.mPendingIntent;
                if (pendingIntent2 != null) {
                    pendingIntent2.unregisterCancelListener(bubble.mPendingIntentCancelListener);
                }
                executor3.execute(new Runnable() { // from class: com.android.wm.shell.bubbles.Bubble$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Bubble bubble2 = Bubble.this;
                        BubbleController bubbleController = (BubbleController) ((BubbleController$$ExternalSyntheticLambda5) pendingIntentCanceledListener2).f$0;
                        if (bubble2.mPendingIntent == null) {
                            return;
                        }
                        if (bubble2.mPendingIntentActive || bubbleController.mBubbleData.hasBubbleInStackWithKey(bubble2.mKey)) {
                            bubble2.mPendingIntentCanceled = true;
                        } else {
                            bubbleController.mMainExecutor.execute(new BubbleController$$ExternalSyntheticLambda14(0, bubbleController, bubble2));
                        }
                    }
                });
            }
        };
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mTaskId = -1;
        setEntry(bubbleEntry);
    }
}
