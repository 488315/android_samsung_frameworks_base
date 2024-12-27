package com.samsung.android.camera.scpm;

import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class ScpmList {
    public String mVersion = null;
    public String mConfigurationName = null;
    public final CopyOnWriteArrayList mPackageList = new CopyOnWriteArrayList();
    public PolicyType mType = null;
    public String[] mItemNames = null;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PolicyType {
        public static final /* synthetic */ PolicyType[] $VALUES;
        public static final PolicyType CAMERA_3RD_PARTY;
        public static final PolicyType HIDDEN_ID_PERMITTED;

        static {
            PolicyType policyType = new PolicyType("CAMERA_3RD_PARTY", 0);
            CAMERA_3RD_PARTY = policyType;
            PolicyType policyType2 = new PolicyType("HIDDEN_ID_PERMITTED", 1);
            HIDDEN_ID_PERMITTED = policyType2;
            $VALUES = new PolicyType[] {policyType, policyType2};
        }

        public static PolicyType valueOf(String str) {
            return (PolicyType) Enum.valueOf(PolicyType.class, str);
        }

        public static PolicyType[] values() {
            return (PolicyType[]) $VALUES.clone();
        }
    }

    public final void setPackageList(String[][] strArr) {
        this.mPackageList.clear();
        for (String[] strArr2 : strArr) {
            this.mPackageList.add(new PolicyListVO(strArr2[0], strArr2[1], strArr2[2]));
        }
    }
}
