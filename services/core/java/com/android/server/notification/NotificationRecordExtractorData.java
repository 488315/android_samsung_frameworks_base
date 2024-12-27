package com.android.server.notification;

import android.app.NotificationChannel;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NotificationRecordExtractorData {
    public final boolean mAllowBubble;
    public final NotificationChannel mChannel;
    public final String mGroupKey;
    public final int mImportance;
    public final boolean mIsBubble;
    public final boolean mIsConversation;
    public final ArrayList mOverridePeople;
    public final int mPosition;
    public final int mProposedImportance;
    public final float mRankingScore;
    public final boolean mSensitiveContent;
    public final boolean mShowBadge;
    public final ArrayList mSmartReplies;
    public final ArrayList mSnoozeCriteria;
    public final Integer mSuppressVisually;
    public final ArrayList mSystemSmartActions;
    public final Integer mUserSentiment;
    public final int mVisibility;

    public NotificationRecordExtractorData(
            int i,
            int i2,
            boolean z,
            boolean z2,
            boolean z3,
            NotificationChannel notificationChannel,
            String str,
            ArrayList arrayList,
            ArrayList arrayList2,
            Integer num,
            Integer num2,
            ArrayList arrayList3,
            ArrayList arrayList4,
            int i3,
            float f,
            boolean z4,
            int i4,
            boolean z5) {
        this.mPosition = i;
        this.mVisibility = i2;
        this.mShowBadge = z;
        this.mAllowBubble = z2;
        this.mIsBubble = z3;
        this.mChannel = notificationChannel;
        this.mGroupKey = str;
        this.mOverridePeople = arrayList;
        this.mSnoozeCriteria = arrayList2;
        this.mUserSentiment = num;
        this.mSuppressVisually = num2;
        this.mSystemSmartActions = arrayList3;
        this.mSmartReplies = arrayList4;
        this.mImportance = i3;
        this.mRankingScore = f;
        this.mIsConversation = z4;
        this.mProposedImportance = i4;
        this.mSensitiveContent = z5;
    }

    public final boolean hasDiffForLoggingLocked(NotificationRecord notificationRecord, int i) {
        if (this.mPosition == i && Objects.equals(this.mChannel, notificationRecord.mChannel)) {
            if (Objects.equals(this.mGroupKey, notificationRecord.sbn.getGroupKey())
                    && Objects.equals(this.mOverridePeople, notificationRecord.mPeopleOverride)
                    && Objects.equals(this.mSnoozeCriteria, notificationRecord.mSnoozeCriteria)) {
                if (Objects.equals(
                                this.mUserSentiment,
                                Integer.valueOf(notificationRecord.mUserSentiment))
                        && Objects.equals(
                                this.mSystemSmartActions,
                                notificationRecord.mSystemGeneratedSmartActions)
                        && Objects.equals(this.mSmartReplies, notificationRecord.mSmartReplies)) {
                    if (this.mImportance == notificationRecord.mImportance
                            && Math.abs(notificationRecord.mRankingScore - this.mRankingScore)
                                    < 1.0E-4d
                            && this.mIsConversation == notificationRecord.isConversation()) {
                        if (this.mProposedImportance == notificationRecord.mProposedImportance) {
                            if (this.mSensitiveContent == notificationRecord.mSensitiveContent) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public final boolean hasDiffForRankingLocked(NotificationRecord notificationRecord, int i) {
        if (this.mPosition == i) {
            if (this.mVisibility == notificationRecord.mPackageVisibility) {
                if (this.mShowBadge == notificationRecord.mShowBadge) {
                    if (this.mAllowBubble == notificationRecord.mAllowBubble) {
                        if (this.mIsBubble
                                        == notificationRecord
                                                .sbn
                                                .getNotification()
                                                .isBubbleNotification()
                                && Objects.equals(this.mChannel, notificationRecord.mChannel)) {
                            if (Objects.equals(this.mGroupKey, notificationRecord.sbn.getGroupKey())
                                    && Objects.equals(
                                            this.mOverridePeople,
                                            notificationRecord.mPeopleOverride)
                                    && Objects.equals(
                                            this.mSnoozeCriteria,
                                            notificationRecord.mSnoozeCriteria)) {
                                if (Objects.equals(
                                        this.mUserSentiment,
                                        Integer.valueOf(notificationRecord.mUserSentiment))) {
                                    if (Objects.equals(
                                                    this.mSuppressVisually,
                                                    Integer.valueOf(
                                                            notificationRecord
                                                                    .mSuppressedVisualEffects))
                                            && Objects.equals(
                                                    this.mSystemSmartActions,
                                                    notificationRecord.mSystemGeneratedSmartActions)
                                            && Objects.equals(
                                                    this.mSmartReplies,
                                                    notificationRecord.mSmartReplies)) {
                                        if (this.mImportance == notificationRecord.mImportance) {
                                            if (this.mProposedImportance
                                                    == notificationRecord.mProposedImportance) {
                                                if (this.mSensitiveContent
                                                        == notificationRecord.mSensitiveContent) {
                                                    return false;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
