package com.android.systemui.statusbar.notification.collection;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.net.Uri;
import android.os.SystemClock;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.view.ContentInfo;
import com.android.internal.widget.LocalImageResolver;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.notification.icon.IconPack;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.wakelock.WakeLock;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.KeyguardConstants;
import com.sec.ims.IMSParameter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationEntry extends ListEntry {
    public EditedSuggestionInfo editedSuggestionInfo;
    public boolean hasSentReply;
    public CharSequence headsUpStatusBarText;
    public CharSequence headsUpStatusBarTextPublic;
    public long initializationTime;
    public boolean interruption;
    public long lastFullScreenIntentLaunchTime;
    public long lastRemoteInputSent;
    public boolean mBlockable;
    public Notification.BubbleMetadata mBubbleMetadata;
    public int mBucket;
    public int mCachedContrastColor;
    public int mCachedContrastColorIsFor;
    public int mCancellationReason;
    public final List mDismissInterceptors;
    public DismissState mDismissState;
    public boolean mExpandAnimationRunning;
    public long mFullscreenPopUpStartTime;
    public GroupEntry mGroupEntry;
    public IconPack mIcons;
    public WakeLock mInflationWakeLock;
    public boolean mIsAlerting;
    public boolean mIsDemoted;
    public boolean mIsGhost;
    public boolean mIsHeadsUpByBriefExpanding;
    public boolean mIsMarkedForUserTriggeredMovement;
    public final String mKey;
    public final List mLifetimeExtenders;
    public final ListenerSet mOnSensitivityChangedListeners;
    public boolean mPulseSupressed;
    public NotificationListenerService.Ranking mRanking;
    public boolean mRemoteEditImeAnimatingAway;
    public boolean mRemoteEditImeVisible;
    public ExpandableNotificationRowController mRowController;
    public InflationTask mRunningTask;
    public StatusBarNotification mSbn;
    public boolean mSensitive;
    public boolean mUserPublic;
    public ContentInfo remoteInputAttachment;
    public String remoteInputMimeType;
    public CharSequence remoteInputText;
    public CharSequence remoteInputTextWhenReset;
    public Uri remoteInputUri;
    public ExpandableNotificationRow row;
    public int targetSdk;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum DismissState {
        NOT_DISMISSED,
        DISMISSED,
        PARENT_DISMISSED
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EditedSuggestionInfo {
        public final int index;
        public final CharSequence originalText;

        public EditedSuggestionInfo(CharSequence charSequence, int i) {
            this.originalText = charSequence;
            this.index = i;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnSensitivityChangedListener {
        void onSensitivityChanged(NotificationEntry notificationEntry);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public NotificationEntry(StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking, long j) {
        super(r0, j);
        Objects.requireNonNull(statusBarNotification);
        String key = statusBarNotification.getKey();
        Objects.requireNonNull(key);
        this.mLifetimeExtenders = new ArrayList();
        this.mDismissInterceptors = new ArrayList();
        this.mCancellationReason = -1;
        this.mDismissState = DismissState.NOT_DISMISSED;
        this.mIcons = IconPack.buildEmptyPack(null);
        this.lastFullScreenIntentLaunchTime = -2000L;
        this.mCachedContrastColor = 1;
        this.mCachedContrastColorIsFor = 1;
        this.mRunningTask = null;
        this.lastRemoteInputSent = -2000L;
        new ArraySet(3);
        this.initializationTime = -1L;
        this.mSensitive = true;
        this.mOnSensitivityChangedListeners = new ListenerSet();
        this.mBucket = 8;
        this.mIsDemoted = false;
        this.mIsHeadsUpByBriefExpanding = false;
        this.mFullscreenPopUpStartTime = 0L;
        Objects.requireNonNull(ranking);
        this.mKey = statusBarNotification.getKey();
        setSbn(statusBarNotification);
        setRanking(ranking);
        SystemClock.elapsedRealtime();
    }

    public final boolean abortTask() {
        InflationTask inflationTask = this.mRunningTask;
        if (inflationTask == null) {
            return false;
        }
        inflationTask.abort();
        this.mRunningTask = null;
        return true;
    }

    public final boolean canBubble() {
        return this.mRanking.canBubble();
    }

    public final List getAttachedNotifChildren() {
        List attachedChildren;
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (expandableNotificationRow == null || (attachedChildren = expandableNotificationRow.getAttachedChildren()) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = attachedChildren.iterator();
        while (it.hasNext()) {
            arrayList.add(((ExpandableNotificationRow) it.next()).mEntry);
        }
        return arrayList;
    }

    public final NotificationChannel getChannel() {
        return this.mRanking.getChannel();
    }

    public final int getImportance() {
        return this.mRanking.getImportance();
    }

    @Override // com.android.systemui.statusbar.notification.collection.ListEntry
    public final String getKey() {
        return this.mKey;
    }

    public InflationTask getRunningTask() {
        return this.mRunningTask;
    }

    public final boolean hasFinishedInitialization() {
        return this.initializationTime != -1 && SystemClock.elapsedRealtime() > this.initializationTime + 400;
    }

    public final boolean isAmbient() {
        return this.mRanking.isAmbient();
    }

    public final boolean isBubble() {
        return (this.mSbn.getNotification().flags & 4096) != 0;
    }

    public final boolean isCanceled() {
        return this.mCancellationReason != -1;
    }

    public final boolean isChildInGroup() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        return expandableNotificationRow != null && expandableNotificationRow.isChildInGroup();
    }

    public final boolean isClearable() {
        if (!this.mSbn.isClearable()) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (!(expandableNotificationRow == null ? true : expandableNotificationRow.canViewBeDismissed$1())) {
            return false;
        }
        List attachedNotifChildren = getAttachedNotifChildren();
        if (attachedNotifChildren != null) {
            ArrayList arrayList = (ArrayList) attachedNotifChildren;
            if (arrayList.size() > 0) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (!((NotificationEntry) arrayList.get(i)).mSbn.isClearable()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isDemoted() {
        return this.mIsDemoted;
    }

    public boolean isExemptFromDndVisualSuppression() {
        Notification notification2 = this.mSbn.getNotification();
        if (Objects.equals(notification2.category, "call") || Objects.equals(notification2.category, KeyguardConstants.UpdateType.BouncerTextKey.MSG) || Objects.equals(notification2.category, "alarm") || Objects.equals(notification2.category, IMSParameter.CALL.EVENT) || Objects.equals(notification2.category, "reminder")) {
            return false;
        }
        return this.mSbn.getNotification().isFgsOrUij() || this.mSbn.getNotification().isMediaNotification() || !this.mBlockable;
    }

    public final boolean isRowPinned() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        return expandableNotificationRow != null && expandableNotificationRow.mIsPinned;
    }

    public final boolean isStickyAndNotDemoted() {
        boolean z = (this.mSbn.getNotification().flags & 16384) != 0;
        if (!z && !this.mIsDemoted) {
            this.mIsDemoted = true;
        }
        return z && !this.mIsDemoted;
    }

    public final boolean legacyIsDismissableRecursive() {
        if (this.mSbn.isOngoing()) {
            return false;
        }
        List attachedNotifChildren = getAttachedNotifChildren();
        if (attachedNotifChildren == null) {
            return true;
        }
        ArrayList arrayList = (ArrayList) attachedNotifChildren;
        if (arrayList.size() <= 0) {
            return true;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if (((NotificationEntry) arrayList.get(i)).mSbn.isOngoing()) {
                return false;
            }
        }
        return true;
    }

    public final boolean rowExists() {
        return this.row != null;
    }

    public final void setDismissState(DismissState dismissState) {
        Objects.requireNonNull(dismissState);
        this.mDismissState = dismissState;
    }

    public final void setHeadsUp(boolean z) {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (expandableNotificationRow != null) {
            boolean isAboveShelf = expandableNotificationRow.isAboveShelf();
            int intrinsicHeight = expandableNotificationRow.getIntrinsicHeight();
            expandableNotificationRow.mIsHeadsUp = z;
            NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
            notificationContentView.mIsHeadsUp = z;
            notificationContentView.selectLayout(false, true);
            notificationContentView.updateExpandButtonsDuringLayout(notificationContentView.mExpandable, false);
            if (expandableNotificationRow.mIsSummaryWithChildren) {
                expandableNotificationRow.mChildrenContainer.updateGroupOverflow();
            }
            if (intrinsicHeight != expandableNotificationRow.getIntrinsicHeight()) {
                expandableNotificationRow.notifyHeightChanged(false);
            }
            if (z) {
                expandableNotificationRow.mMustStayOnScreen = true;
                expandableNotificationRow.setAboveShelf(true);
            } else if (expandableNotificationRow.isAboveShelf() != isAboveShelf) {
                expandableNotificationRow.mAboveShelfChangedListener.onAboveShelfStateChanged(!isAboveShelf);
            }
        }
    }

    public final void setMessageUriToBitmap(Context context) {
        if (Notification.MessagingStyle.class.equals(this.mSbn.getNotification().getNotificationStyle())) {
            for (Notification.MessagingStyle.Message message : Notification.MessagingStyle.Message.getMessagesFromBundleArray(this.mSbn.getNotification().extras.getParcelableArray("android.messages"))) {
                if (message.getDataUri() != null && message.getDataMimeType() != null && message.getDataMimeType().startsWith("image/")) {
                    try {
                        Uri dataUri = message.getDataUri();
                        NotificationInlineImageResolver notificationInlineImageResolver = this.row.mImageResolver;
                        if (notificationInlineImageResolver != null) {
                            notificationInlineImageResolver.loadImage(dataUri);
                        } else {
                            LocalImageResolver.resolveImage(dataUri, context);
                        }
                    } catch (IOException | SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public final void setRanking(NotificationListenerService.Ranking ranking) {
        Objects.requireNonNull(ranking);
        Objects.requireNonNull(ranking.getKey());
        String key = ranking.getKey();
        String str = this.mKey;
        if (!key.equals(str)) {
            throw new IllegalArgumentException("New key " + ranking.getKey() + " doesn't match existing key " + str);
        }
        this.mRanking = ranking.withAudiblyAlertedInfo(this.mRanking);
        if (getChannel() == null) {
            this.mBlockable = false;
        } else if (!getChannel().isImportanceLockedByCriticalDeviceFunction() || getChannel().isBlockable()) {
            this.mBlockable = true;
        } else {
            this.mBlockable = false;
        }
    }

    public final void setSbn(StatusBarNotification statusBarNotification) {
        Objects.requireNonNull(statusBarNotification);
        Objects.requireNonNull(statusBarNotification.getKey());
        String key = statusBarNotification.getKey();
        String str = this.mKey;
        if (key.equals(str)) {
            this.mSbn = statusBarNotification;
            this.mBubbleMetadata = statusBarNotification.getNotification().getBubbleMetadata();
        } else {
            throw new IllegalArgumentException("New key " + statusBarNotification.getKey() + " doesn't match existing key " + str);
        }
    }

    public final boolean shouldSuppressVisualEffect(int i) {
        return (isExemptFromDndVisualSuppression() || (this.mRanking.getSuppressedVisualEffects() & i) == 0) ? false : true;
    }

    public void setCreationElapsedRealTime(long j) {
    }

    @Override // com.android.systemui.statusbar.notification.collection.ListEntry
    public final NotificationEntry getRepresentativeEntry() {
        return this;
    }
}
