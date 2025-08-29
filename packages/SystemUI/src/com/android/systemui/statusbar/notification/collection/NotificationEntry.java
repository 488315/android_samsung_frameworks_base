package com.android.systemui.statusbar.notification.collection;

import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.view.ContentInfo;
import android.view.View;
import com.android.internal.widget.LocalImageResolver;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.AllowedOngoingActivityListManager;
import com.android.systemui.statusbar.notification.headsup.PinnedStatus;
import com.android.systemui.statusbar.notification.icon.IconPack;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.wakelock.WakeLock;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.sec.ims.IMSParameter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class NotificationEntry extends ListEntry {
    public EditedSuggestionInfo editedSuggestionInfo;
    public boolean hasSentReply;
    public long initializationTime;
    public boolean interruption;
    public long lastFullScreenIntentLaunchTime;
    public long lastRemoteInputSent;
    public boolean mBlockVisible;
    public boolean mBlockable;
    public Notification.BubbleMetadata mBubbleMetadata;
    public int mCachedContrastColor;
    public int mCachedContrastColorIsFor;
    public int mCancellationReason;
    public final List mDismissInterceptors;
    public DismissState mDismissState;
    public boolean mExpandAnimationRunning;
    public long mFullscreenPopUpStartTime;
    public GroupEntry mGroupEntry;
    public boolean mHasEverBeenGroupChild;
    public final StateFlowImpl mHeadsUpStatusBarText;
    public final StateFlowImpl mHeadsUpStatusBarTextPublic;
    public IconPack mIcons;
    public WakeLock mInflationWakeLock;
    public Boolean mIsBindCutOff;
    public boolean mIsDemoted;
    public boolean mIsGhost;
    public boolean mIsHeadsUpByBriefExpanding;
    public boolean mIsHeadsUpEntry;
    public Boolean mIsLockscreenSecret;
    public boolean mIsMarkedForUserTriggeredMovement;
    public Boolean mIsPlayingMediaOngoingActivity;
    public Boolean mIsPromoted;
    public boolean mIsReaded;
    public Boolean mIsRon;
    public final String mKey;
    public final List mLifetimeExtenders;
    public final ListenerSet mOnHideRawValueChangedListeners;
    public final ListenerSet mOnSensitivityChangedListeners;
    public View mPromotedOngoingView;
    public NotificationListenerService.Ranking mRanking;
    public boolean mRawValueHide;
    public boolean mRemoteEditImeAnimatingAway;
    public boolean mRemoteEditImeVisible;
    public ExpandableNotificationRowController mRowController;
    public InflationTask mRunningTask;
    public StatusBarNotification mSbn;
    public final StateFlowImpl mSensitive;
    public boolean mUserPublic;
    public boolean mWillBeHUN;
    public ContentInfo remoteInputAttachment;
    public String remoteInputMimeType;
    public CharSequence remoteInputText;
    public CharSequence remoteInputTextWhenReset;
    public Uri remoteInputUri;
    public ExpandableNotificationRow row;
    public int targetSdk;

    public enum DismissState {
        NOT_DISMISSED,
        DISMISSED,
        PARENT_DISMISSED
    }

    public class EditedSuggestionInfo {
        public final int index;
        public final CharSequence originalText;

        public EditedSuggestionInfo(CharSequence charSequence, int i) {
            this.originalText = charSequence;
            this.index = i;
        }
    }

    public interface OnHideRawValueChangedListener {
    }

    public interface OnSensitivityChangedListener {
        void onSensitivityChanged(NotificationEntry notificationEntry);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public NotificationEntry(StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking, long j) {
        Objects.requireNonNull(statusBarNotification);
        String key = statusBarNotification.getKey();
        Objects.requireNonNull(key);
        super(key, j);
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
        this.mHeadsUpStatusBarText = StateFlowKt.MutableStateFlow(null);
        this.mHeadsUpStatusBarTextPublic = StateFlowKt.MutableStateFlow(null);
        this.initializationTime = -1L;
        this.mSensitive = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        this.mOnSensitivityChangedListeners = new ListenerSet();
        this.mOnHideRawValueChangedListeners = new ListenerSet();
        this.mIsDemoted = false;
        this.mIsHeadsUpByBriefExpanding = false;
        this.mFullscreenPopUpStartTime = 0L;
        this.mIsPromoted = null;
        this.mPromotedOngoingView = null;
        Boolean bool = Boolean.FALSE;
        this.mIsLockscreenSecret = bool;
        this.mIsRon = bool;
        this.mIsPlayingMediaOngoingActivity = bool;
        this.mIsBindCutOff = bool;
        this.mWillBeHUN = false;
        Objects.requireNonNull(ranking);
        this.mIsPromoted = Boolean.valueOf(ranking.isOngoingActivityTurnedOn());
        this.mIsLockscreenSecret = Boolean.valueOf(ranking.getLockscreenVisibilityOverride() == -1);
        this.mKey = statusBarNotification.getKey();
        setSbn(statusBarNotification);
        setRanking(ranking);
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
        int i = NotificationBundleUi.$r8$clinit;
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (expandableNotificationRow == null || (attachedChildren = expandableNotificationRow.getAttachedChildren()) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) attachedChildren;
        int size = arrayList2.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList2.get(i2);
            i2++;
            arrayList.add(((ExpandableNotificationRow) obj).getEntryLegacy());
        }
        return arrayList;
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineEntry
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

    public final boolean isBubble() {
        return (this.mSbn.getNotification().flags & 4096) != 0;
    }

    public final boolean isCanceled() {
        return this.mCancellationReason != -1;
    }

    public final boolean isClearable() {
        ExpandableNotificationRow expandableNotificationRow;
        if ((this.mSbn.getNotification().flags & 8192) == 0 && !this.mSbn.getNotification().isMediaNotification() && (expandableNotificationRow = this.row) != null && expandableNotificationRow.canViewBeDismissed$1() && !this.mIsPlayingMediaOngoingActivity.booleanValue() && (!isOngoingActivity() || !isPromotedState())) {
            List attachedNotifChildren = getAttachedNotifChildren();
            if (attachedNotifChildren == null) {
                return true;
            }
            ArrayList arrayList = (ArrayList) attachedNotifChildren;
            if (arrayList.size() <= 0) {
                return true;
            }
            for (int i = 0; i < arrayList.size(); i++) {
                if (((NotificationEntry) arrayList.get(i)).isClearable()) {
                }
            }
            return true;
        }
        return false;
    }

    public boolean isDemoted() {
        return this.mIsDemoted;
    }

    public final boolean isDismissableForState(boolean z) {
        if (this.mSbn.isNonDismissable() || this.mIsPlayingMediaOngoingActivity.booleanValue()) {
            return false;
        }
        return (this.mSbn.isOngoing() && z) ? false : true;
    }

    public boolean isExemptFromDndVisualSuppression() {
        Notification notification2 = this.mSbn.getNotification();
        if (Objects.equals(notification2.category, "call") || Objects.equals(notification2.category, "msg") || Objects.equals(notification2.category, "alarm") || Objects.equals(notification2.category, IMSParameter.CALL.EVENT) || Objects.equals(notification2.category, "reminder")) {
            return false;
        }
        return this.mSbn.getNotification().isFgsOrUij() || this.mSbn.getNotification().isMediaNotification() || !this.mBlockable;
    }

    public final boolean isInsignificant() {
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificantBgActivities()) {
            StatusBarNotification statusBarNotification = this.mSbn;
            if (statusBarNotification != null && (statusBarNotification.getNotification().semFlags & 1048576) != 0) {
                return true;
            }
            StatusBarNotification statusBarNotification2 = this.mSbn;
            if (statusBarNotification2 != null && statusBarNotification2.getGroupKey().contains("INSIGNIFICANT")) {
                return true;
            }
        }
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificantMinimized()) {
            StatusBarNotification statusBarNotification3 = this.mSbn;
            if (statusBarNotification3 != null && (statusBarNotification3.getNotification().semFlags & 2097152) != 0) {
                return true;
            }
            StatusBarNotification statusBarNotification4 = this.mSbn;
            if (statusBarNotification4 != null && statusBarNotification4.getGroupKey().contains("INSIGNIFICANT")) {
                return true;
            }
        }
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificantOld()) {
            StatusBarNotification statusBarNotification5 = this.mSbn;
            if (statusBarNotification5 != null && (statusBarNotification5.getNotification().semFlags & 4194304) != 0) {
                return true;
            }
            StatusBarNotification statusBarNotification6 = this.mSbn;
            if (statusBarNotification6 != null && statusBarNotification6.getGroupKey().contains("INSIGNIFICANT")) {
                return true;
            }
        }
        if (!((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificantPromotion()) {
            return false;
        }
        StatusBarNotification statusBarNotification7 = this.mSbn;
        if (statusBarNotification7 != null && (statusBarNotification7.getNotification().semFlags & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0) {
            return true;
        }
        StatusBarNotification statusBarNotification8 = this.mSbn;
        return statusBarNotification8 != null && statusBarNotification8.getGroupKey().contains("INSIGNIFICANT");
    }

    public final boolean isOngoingActivity() {
        if (NotiRune.NOTI_ONGOING_GEMINI_DEMO) {
            OngoingActivityDataHelper.INSTANCE.getClass();
            if (OngoingActivityDataHelper.isExceptionalOngoingActivity(this)) {
                return true;
            }
        }
        StatusBarNotification statusBarNotification = this.mSbn;
        if (statusBarNotification == null) {
            return false;
        }
        if (statusBarNotification.getNotification().extras.getInt("android.ongoingActivityNoti.style", 0) <= 0) {
            return this.mIsRon.booleanValue();
        }
        this.mIsRon = Boolean.FALSE;
        return true;
    }

    public final boolean isProgressStyle() {
        return (getNotificationStyle().contains("ProgressStyle") && (this.mSbn.getNotification().extras.getParcelableArray("android.progressSegments") != null)) || ((this.mSbn.getNotification().extras.getInt("android.ongoingActivityNoti.progress") > 0) && (this.mSbn.getNotification().extras.getParcelableArray("android.ongoingActivityNoti.progressSegments") != null));
    }

    public final boolean isPromotedState() {
        if (NotiRune.NOTI_ONGOING_GEMINI_DEMO) {
            OngoingActivityDataHelper.INSTANCE.getClass();
            if (OngoingActivityDataHelper.isExceptionalOngoingActivity(this)) {
                return true;
            }
        }
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isDevelopRonTestAllowed() && isOngoingActivity()) {
            return true;
        }
        return this.mIsPromoted.booleanValue();
    }

    public final boolean isRowDismissed() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        return expandableNotificationRow != null && expandableNotificationRow.mDismissed;
    }

    public final boolean isRowPinned() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        return (expandableNotificationRow != null ? expandableNotificationRow.mPinnedStatus : PinnedStatus.NotPinned).isPinned();
    }

    public final boolean isStickyAndNotDemoted() {
        boolean z = (this.mSbn.getNotification().flags & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0;
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

    public final void setHeadsUp(boolean z) {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (expandableNotificationRow != null) {
            boolean zIsAboveShelf = expandableNotificationRow.isAboveShelf();
            int intrinsicHeight = expandableNotificationRow.getIntrinsicHeight();
            expandableNotificationRow.mIsHeadsUp = z;
            NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
            notificationContentView.mIsHeadsUp = z;
            notificationContentView.selectLayout(false, true);
            notificationContentView.updateExpandButtons(notificationContentView.mExpandable);
            if (expandableNotificationRow.mIsSummaryWithChildren) {
                expandableNotificationRow.mChildrenContainer.updateGroupOverflow();
            }
            if (intrinsicHeight != expandableNotificationRow.getIntrinsicHeight()) {
                expandableNotificationRow.notifyHeightChanged(false);
            }
            if (z) {
                expandableNotificationRow.mMustStayOnScreen = true;
                expandableNotificationRow.setAboveShelf(true);
            } else if (expandableNotificationRow.isAboveShelf() != zIsAboveShelf) {
                expandableNotificationRow.mAboveShelfChangedListener.onAboveShelfStateChanged(!zIsAboveShelf);
            }
            this.mWillBeHUN = false;
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
        Objects.requireNonNull(ranking.getKey());
        String key = ranking.getKey();
        String str = this.mKey;
        if (!key.equals(str)) {
            throw new IllegalArgumentException("New key " + ranking.getKey() + " doesn't match existing key " + str);
        }
        NotificationListenerService.Ranking rankingWithAudiblyAlertedInfo = ranking.withAudiblyAlertedInfo(this.mRanking);
        this.mRanking = rankingWithAudiblyAlertedInfo;
        if (rankingWithAudiblyAlertedInfo.getChannel() == null) {
            this.mBlockable = false;
        } else if (!this.mRanking.getChannel().isImportanceLockedByCriticalDeviceFunction() || this.mRanking.getChannel().isBlockable()) {
            this.mBlockable = true;
        } else {
            this.mBlockable = false;
        }
        if (this.row == null || !isOngoingActivity()) {
            return;
        }
        this.row.mViewState.hasGradient = false;
        boolean zIsOngoingActivityTurnedOn = this.mRanking.isOngoingActivityTurnedOn();
        if (this.mIsPromoted.booleanValue() != zIsOngoingActivityTurnedOn) {
            this.mIsPromoted = Boolean.valueOf(zIsOngoingActivityTurnedOn);
            OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
            NotificationLockscreenUserManager notificationLockscreenUserManager = (NotificationLockscreenUserManager) Dependency.sDependency.getDependencyInner(NotificationLockscreenUserManager.class);
            ongoingActivityDataHelper.getClass();
            OngoingActivityDataHelper.updateOngoingList(notificationLockscreenUserManager);
            OngoingActivityDataHelper.notifyRankingStateChanged(str);
            OngoingActivityDataHelper.recreateOngoingActivity(this.row.getContext(), this);
        }
        boolean z = this.mRanking.getLockscreenVisibilityOverride() == -1;
        if (this.mIsLockscreenSecret.booleanValue() != z) {
            this.mIsLockscreenSecret = Boolean.valueOf(z);
            OngoingActivityDataHelper.INSTANCE.getClass();
            OngoingActivityDataHelper.notifyRankingStateChanged(str);
        }
    }

    public final void setSbn(StatusBarNotification statusBarNotification) {
        Objects.requireNonNull(statusBarNotification);
        Objects.requireNonNull(statusBarNotification.getKey());
        String key = statusBarNotification.getKey();
        String str = this.mKey;
        if (!key.equals(str)) {
            throw new IllegalArgumentException("New key " + statusBarNotification.getKey() + " doesn't match existing key " + str);
        }
        this.mSbn = statusBarNotification;
        this.mBubbleMetadata = statusBarNotification.getNotification().getBubbleMetadata();
        AllowedOngoingActivityListManager allowedOngoingActivityListManager = (AllowedOngoingActivityListManager) Dependency.sDependency.getDependencyInner(AllowedOngoingActivityListManager.class);
        String packageName = this.mSbn.getPackageName();
        boolean zContains = true;
        if (Settings.Secure.getInt(allowedOngoingActivityListManager.context.getContentResolver(), SettingsHelper.INDEX_DEVELOP_RON_TEST, 0) != 1) {
            List list = allowedOngoingActivityListManager.allowedList;
            if (list == null) {
                list = null;
            }
            zContains = list.contains(packageName);
        }
        if (zContains || ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isDevelopRonTestAllowed()) {
            this.mIsRon = Boolean.valueOf(statusBarNotification.getNotification().hasPromotableCharacteristics());
        }
    }

    public final void setSensitive(boolean z, boolean z2) {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        int intrinsicHeight = expandableNotificationRow.getIntrinsicHeight();
        boolean z3 = expandableNotificationRow.mSensitive != z;
        expandableNotificationRow.mSensitive = z;
        expandableNotificationRow.mSensitiveHiddenInGeneral = z2;
        if (intrinsicHeight != expandableNotificationRow.getIntrinsicHeight() || (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE && expandableNotificationRow.isChildInGroup() && !z && z3)) {
            expandableNotificationRow.notifyHeightChanged(true);
        }
        expandableNotificationRow.updateBackgroundForGroupState();
        StateFlowImpl stateFlowImpl = this.mSensitive;
        if (z != ((Boolean) stateFlowImpl.getValue()).booleanValue()) {
            stateFlowImpl.updateState(null, Boolean.valueOf(z));
            Iterator it = this.mOnSensitivityChangedListeners.iterator();
            while (it.hasNext()) {
                ((OnSensitivityChangedListener) it.next()).onSensitivityChanged(this);
            }
        }
    }

    public final boolean shouldSuppressVisualEffect(int i) {
        return (isExemptFromDndVisualSuppression() || (this.mRanking.getSuppressedVisualEffects() & i) == 0) ? false : true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineEntry
    public final NotificationEntry getRepresentativeEntry() {
        return this;
    }
}
