package com.android.systemui.statusbar.notification.collection;

import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import android.os.SystemClock;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.view.ContentInfo;
import com.android.internal.widget.LocalImageResolver;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.notification.icon.IconPack;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.wakelock.WakeLock;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.KeyguardConstants;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.sec.ims.IMSParameter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlinx.coroutines.flow.StateFlowImpl;

public final class NotificationEntry extends ListEntry {
    public EditedSuggestionInfo editedSuggestionInfo;
    public boolean hasSentReply;
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
    public final StateFlowImpl mHeadsUpStatusBarText;
    public final StateFlowImpl mHeadsUpStatusBarTextPublic;
    public IconPack mIcons;
    public boolean mInflateDone;
    public WakeLock mInflationWakeLock;
    public boolean mIsDemoted;
    public boolean mIsGhost;
    public boolean mIsHeadsUpByBriefExpanding;
    public boolean mIsHeadsUpEntry;
    public boolean mIsMarkedForUserTriggeredMovement;
    public boolean mIsReaded;
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
    public final StateFlowImpl mSensitive;
    public boolean mSummarizeDone;
    public boolean mUserPublic;
    public ContentInfo remoteInputAttachment;
    public String remoteInputMimeType;
    public CharSequence remoteInputText;
    public CharSequence remoteInputTextWhenReset;
    public Uri remoteInputUri;
    public List remoteInputs;
    public ExpandableNotificationRow row;
    public int targetSdk;

    public enum DismissState {
        NOT_DISMISSED,
        DISMISSED,
        PARENT_DISMISSED
    }

    public final class EditedSuggestionInfo {
        public final int index;
        public final CharSequence originalText;

        public EditedSuggestionInfo(CharSequence charSequence, int i) {
            this.originalText = charSequence;
            this.index = i;
        }
    }

    public interface OnSensitivityChangedListener {
        void onSensitivityChanged(NotificationEntry notificationEntry);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public NotificationEntry(android.service.notification.StatusBarNotification r3, android.service.notification.NotificationListenerService.Ranking r4, long r5) {
        /*
            r2 = this;
            java.util.Objects.requireNonNull(r3)
            java.lang.String r0 = r3.getKey()
            java.util.Objects.requireNonNull(r0)
            r2.<init>(r0, r5)
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r2.mLifetimeExtenders = r5
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r2.mDismissInterceptors = r5
            r5 = -1
            r2.mCancellationReason = r5
            com.android.systemui.statusbar.notification.collection.NotificationEntry$DismissState r5 = com.android.systemui.statusbar.notification.collection.NotificationEntry.DismissState.NOT_DISMISSED
            r2.mDismissState = r5
            r5 = 0
            com.android.systemui.statusbar.notification.icon.IconPack r6 = com.android.systemui.statusbar.notification.icon.IconPack.buildEmptyPack(r5)
            r2.mIcons = r6
            r0 = -2000(0xfffffffffffff830, double:NaN)
            r2.lastFullScreenIntentLaunchTime = r0
            r2.remoteInputs = r5
            r6 = 1
            r2.mCachedContrastColor = r6
            r2.mCachedContrastColorIsFor = r6
            r2.mRunningTask = r5
            r2.lastRemoteInputSent = r0
            kotlinx.coroutines.flow.StateFlowImpl r6 = kotlinx.coroutines.flow.StateFlowKt.MutableStateFlow(r5)
            r2.mHeadsUpStatusBarText = r6
            kotlinx.coroutines.flow.StateFlowImpl r5 = kotlinx.coroutines.flow.StateFlowKt.MutableStateFlow(r5)
            r2.mHeadsUpStatusBarTextPublic = r5
            r5 = -1
            r2.initializationTime = r5
            java.lang.Boolean r5 = java.lang.Boolean.TRUE
            kotlinx.coroutines.flow.StateFlowImpl r5 = kotlinx.coroutines.flow.StateFlowKt.MutableStateFlow(r5)
            r2.mSensitive = r5
            com.android.systemui.util.ListenerSet r5 = new com.android.systemui.util.ListenerSet
            r5.<init>()
            r2.mOnSensitivityChangedListeners = r5
            r5 = 12
            r2.mBucket = r5
            r5 = 0
            r2.mIsDemoted = r5
            r2.mIsHeadsUpByBriefExpanding = r5
            r0 = 0
            r2.mFullscreenPopUpStartTime = r0
            r2.mSummarizeDone = r5
            r2.mInflateDone = r5
            java.util.Objects.requireNonNull(r4)
            java.lang.String r5 = r3.getKey()
            r2.mKey = r5
            r2.setSbn(r3)
            r2.setRanking(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.NotificationEntry.<init>(android.service.notification.StatusBarNotification, android.service.notification.NotificationListenerService$Ranking, long):void");
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

    @Override // com.android.systemui.statusbar.notification.collection.ListEntry
    public final String getKey() {
        return this.mKey;
    }

    public final String getNotificationStyle() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (expandableNotificationRow != null && expandableNotificationRow.mIsSummaryWithChildren) {
            return UniversalCredentialUtil.AGENT_SUMMARY;
        }
        Class notificationStyle = this.mSbn.getNotification().getNotificationStyle();
        return notificationStyle == null ? "nostyle" : notificationStyle.getSimpleName();
    }

    public InflationTask getRunningTask() {
        return this.mRunningTask;
    }

    public final boolean hasFinishedInitialization() {
        return this.initializationTime != -1 && SystemClock.elapsedRealtime() > this.initializationTime + 400;
    }

    public final boolean isBubble() {
        return (this.mSbn.getNotification().flags & 4096) != 0;
    }

    public final boolean isCanceled() {
        return this.mCancellationReason != -1;
    }

    public final boolean isClearable() {
        if ((this.mSbn.getNotification().flags & 8192) != 0 || this.mSbn.getNotification().isMediaNotification()) {
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
                    if (!((NotificationEntry) arrayList.get(i)).isClearable()) {
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
        return (Objects.equals(notification2.category, "call") || Objects.equals(notification2.category, KeyguardConstants.UpdateType.BouncerTextKey.MSG) || Objects.equals(notification2.category, "alarm") || Objects.equals(notification2.category, IMSParameter.CALL.EVENT) || Objects.equals(notification2.category, "reminder") || (!this.mSbn.getNotification().isFgsOrUij() && !this.mSbn.getNotification().isMediaNotification() && this.mBlockable)) ? false : true;
    }

    public final boolean isHighlightsStyle() {
        StatusBarNotification statusBarNotification;
        return (!((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableHighlights() || (statusBarNotification = this.mSbn) == null || (statusBarNotification.getNotification().semFlags & 131072) == 0) ? false : true;
    }

    public final boolean isInsignificant() {
        if (!((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificant()) {
            return false;
        }
        StatusBarNotification statusBarNotification = this.mSbn;
        if (statusBarNotification != null && (statusBarNotification.getNotification().semFlags & 262144) != 0) {
            return true;
        }
        StatusBarNotification statusBarNotification2 = this.mSbn;
        return statusBarNotification2 != null && statusBarNotification2.getGroupKey().contains("INSIGNIFICANT");
    }

    public final boolean isOngoingAcitivty() {
        if (NotiRune.NOTI_ONGOING_GEMINI_DEMO) {
            OngoingActivityDataHelper.INSTANCE.getClass();
            if (OngoingActivityDataHelper.isExceptionalOngoingActivity(this)) {
                return true;
            }
        }
        StatusBarNotification statusBarNotification = this.mSbn;
        return statusBarNotification != null && statusBarNotification.getNotification().extras.getInt("android.ongoingActivityNoti.style", 0) == 1;
    }

    public final boolean isPromotedState() {
        if (NotiRune.NOTI_ONGOING_GEMINI_DEMO) {
            OngoingActivityDataHelper.INSTANCE.getClass();
            if (OngoingActivityDataHelper.isExceptionalOngoingActivity(this)) {
                return true;
            }
        }
        return this.mRanking.isOngoingActivityTurnedOn();
    }

    public final boolean isRowDismissed() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        return expandableNotificationRow != null && expandableNotificationRow.mDismissed;
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

    public final boolean rowExists() {
        return this.row != null;
    }

    public final boolean rowIsChildInGroup() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        return expandableNotificationRow != null && expandableNotificationRow.isChildInGroup();
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
        NotificationListenerService.Ranking withAudiblyAlertedInfo = ranking.withAudiblyAlertedInfo(this.mRanking);
        this.mRanking = withAudiblyAlertedInfo;
        if (withAudiblyAlertedInfo.getChannel() == null) {
            this.mBlockable = false;
        } else if (!this.mRanking.getChannel().isImportanceLockedByCriticalDeviceFunction() || this.mRanking.getChannel().isBlockable()) {
            this.mBlockable = true;
        } else {
            this.mBlockable = false;
        }
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.mViewState.hasGradient = false;
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

    @Override // com.android.systemui.statusbar.notification.collection.ListEntry
    public final NotificationEntry getRepresentativeEntry() {
        return this;
    }
}
