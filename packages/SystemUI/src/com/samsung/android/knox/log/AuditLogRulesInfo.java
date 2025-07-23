package com.samsung.android.knox.log;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class AuditLogRulesInfo implements Parcelable {
    public static final int AUDIT_LOG_OUTCOME_ALL = 2;
    public static final int AUDIT_LOG_OUTCOME_FAILURE = 0;
    public static final int AUDIT_LOG_OUTCOME_SUCCESS = 1;
    public static final Parcelable.Creator<AuditLogRulesInfo> CREATOR = new Parcelable.Creator<AuditLogRulesInfo>() { // from class: com.samsung.android.knox.log.AuditLogRulesInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuditLogRulesInfo createFromParcel(Parcel parcel) {
            return new AuditLogRulesInfo(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuditLogRulesInfo[] newArray(int i) {
            return new AuditLogRulesInfo[i];
        }
    };
    public List<Integer> mGroupsRule;
    public int mOutcomeRule;
    public int mSeverityRule;
    public List<Integer> mUsersRule;

    public /* synthetic */ AuditLogRulesInfo(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<Integer> getGroupsRule() {
        return this.mGroupsRule;
    }

    public int getOutcomeRule() {
        return this.mOutcomeRule;
    }

    public int getSeverityRule() {
        return this.mSeverityRule;
    }

    public List<Integer> getUsersRule() {
        return this.mUsersRule;
    }

    public boolean isKernelLogsEnabled() {
        return false;
    }

    public final void readFromParcel(Parcel parcel) {
        this.mSeverityRule = parcel.readInt();
        this.mOutcomeRule = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        this.mGroupsRule = arrayList;
        parcel.readList(arrayList, Integer.class.getClassLoader());
        ArrayList arrayList2 = new ArrayList();
        this.mUsersRule = arrayList2;
        parcel.readList(arrayList2, Integer.class.getClassLoader());
    }

    public void setGroupsRule(List<Integer> list) {
        this.mGroupsRule = list;
    }

    public void setOutcomeRule(int i) {
        this.mOutcomeRule = i;
    }

    public void setSeverityRule(int i) {
        this.mSeverityRule = i;
    }

    public void setUsersRule(List<Integer> list) {
        this.mUsersRule = list;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSeverityRule);
        parcel.writeInt(this.mOutcomeRule);
        parcel.writeList(this.mGroupsRule);
        parcel.writeList(this.mUsersRule);
    }

    public AuditLogRulesInfo() {
        this.mSeverityRule = 5;
        this.mOutcomeRule = 2;
        this.mGroupsRule = null;
        this.mUsersRule = null;
    }

    public AuditLogRulesInfo(int i, int i2, List<Integer> list, boolean z, List<Integer> list2) {
        this.mSeverityRule = i;
        this.mOutcomeRule = i2;
        this.mGroupsRule = list;
        this.mUsersRule = list2;
    }

    private AuditLogRulesInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void setKernelLogsEnabled(boolean z) {
    }
}
