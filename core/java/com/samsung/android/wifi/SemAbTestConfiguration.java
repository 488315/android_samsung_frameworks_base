package com.samsung.android.wifi;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.text.format.DateFormat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes6.dex */
public class SemAbTestConfiguration implements Parcelable {
    public static final Parcelable.Creator<SemAbTestConfiguration> CREATOR =
            new Parcelable.Creator<
                    SemAbTestConfiguration>() { // from class:
                                                // com.samsung.android.wifi.SemAbTestConfiguration.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemAbTestConfiguration createFromParcel(Parcel in) {
                    SemAbTestConfiguration config = new SemAbTestConfiguration();
                    config.id = in.readString();
                    config.moduleName = in.readString();
                    config.startDate = in.readString();
                    config.endDate = in.readString();
                    config.mSalesModelAllowList = in.readArrayList(null, String.class);
                    config.mSalesModelBlockList = in.readArrayList(null, String.class);
                    config.abTestGroupAllocation = in.readInt();
                    config.mTestParamList = in.readArrayList(TestParam.class.getClassLoader());
                    config.mGroupSize = SemAbTestConfiguration.readGroup(in);
                    config.mTestOutputList = in.readArrayList(TestOutput.class.getClassLoader());
                    return config;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemAbTestConfiguration[] newArray(int size) {
                    return new SemAbTestConfiguration[size];
                }
            };
    public static final int TESTGROUP_A = 0;
    public static final int TESTGROUP_B = 1;
    public static final int TESTGROUP_C = 2;
    public static final int TESTGROUP_INVALID = -1;
    public int abTestGroupAllocation;
    public String endDate;
    public String id;
    private Group mGroupSize;
    private List<String> mSalesModelAllowList;
    private List<String> mSalesModelBlockList;
    private List<TestOutput> mTestOutputList;
    private List<TestParam> mTestParamList;
    public String moduleName;
    public String startDate;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AbTestGroupAllocation {}

    public static class Group {
        public String groupA;
        public String groupB;
        public String groupC;

        public String toString() {
            return "{groupA='"
                    + this.groupA
                    + DateFormat.QUOTE
                    + ", groupB='"
                    + this.groupB
                    + DateFormat.QUOTE
                    + ", groupC='"
                    + this.groupC
                    + DateFormat.QUOTE
                    + '}';
        }
    }

    public static class TestParam implements Parcelable {
        public static final Parcelable.Creator<TestParam> CREATOR =
                new Parcelable.Creator<
                        TestParam>() { // from class:
                                       // com.samsung.android.wifi.SemAbTestConfiguration.TestParam.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public TestParam createFromParcel(Parcel in) {
                        TestParam testParam = new TestParam();
                        testParam.name = in.readString();
                        testParam.group.groupA = in.readString();
                        testParam.group.groupB = in.readString();
                        testParam.group.groupC = in.readString();
                        return testParam;
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public TestParam[] newArray(int size) {
                        return new TestParam[size];
                    }
                };
        public Group group = new Group();
        public String name;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "TestParam{name='"
                    + this.name
                    + DateFormat.QUOTE
                    + ", group="
                    + this.group
                    + '}';
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            SemAbTestConfiguration.writeGroup(dest, this.group);
        }
    }

    public static class TestOutput implements Parcelable {
        public static final Parcelable.Creator<TestOutput> CREATOR =
                new Parcelable.Creator<
                        TestOutput>() { // from class:
                                        // com.samsung.android.wifi.SemAbTestConfiguration.TestOutput.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public TestOutput createFromParcel(Parcel in) {
                        TestOutput testOutput = new TestOutput();
                        testOutput.name = in.readString();
                        testOutput.event.groupA = in.readString();
                        testOutput.event.groupB = in.readString();
                        testOutput.event.groupC = in.readString();
                        testOutput.dimension.groupA = in.readString();
                        testOutput.dimension.groupB = in.readString();
                        testOutput.dimension.groupC = in.readString();
                        return testOutput;
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public TestOutput[] newArray(int size) {
                        return new TestOutput[size];
                    }
                };
        public String name;
        public Group event = new Group();
        public Group dimension = new Group();

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "TestOutput{name='"
                    + this.name
                    + DateFormat.QUOTE
                    + ", event="
                    + this.event
                    + ", dimension="
                    + this.dimension
                    + '}';
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            SemAbTestConfiguration.writeGroup(dest, this.event);
            SemAbTestConfiguration.writeGroup(dest, this.dimension);
        }
    }

    public SemAbTestConfiguration() {
        this.id = null;
        this.moduleName = null;
        this.startDate = null;
        this.endDate = null;
        this.mSalesModelAllowList = Collections.emptyList();
        this.mSalesModelBlockList = Collections.emptyList();
        this.abTestGroupAllocation = -1;
        this.mTestParamList = Collections.emptyList();
        this.mGroupSize = new Group();
        this.mTestOutputList = Collections.emptyList();
    }

    public SemAbTestConfiguration(String moduleName) {
        this.id = "0";
        this.moduleName = moduleName;
        this.startDate = "2024.03.01";
        this.endDate = "2024.12.31";
        this.mSalesModelAllowList = Collections.emptyList();
        this.mSalesModelBlockList = Collections.emptyList();
        this.abTestGroupAllocation = 0;
        this.mTestParamList = Collections.emptyList();
        this.mGroupSize = new Group();
        this.mTestOutputList = Collections.emptyList();
    }

    public List<TestParam> getTestParamList() {
        return this.mTestParamList;
    }

    public void setTestParamList(List<TestParam> testParams) {
        if (testParams == null) {
            this.mTestParamList = Collections.emptyList();
        } else {
            this.mTestParamList = new ArrayList(testParams);
        }
    }

    public Group getGroupSize() {
        return this.mGroupSize;
    }

    public void setGroupSize(Group groupSize) {
        if (groupSize == null) {
            this.mGroupSize = new Group();
        } else {
            this.mGroupSize = groupSize;
        }
    }

    public List<TestOutput> getTestOutputList() {
        return this.mTestOutputList;
    }

    public void setTestOutputList(List<TestOutput> testOutputList) {
        if (testOutputList == null) {
            this.mTestOutputList = Collections.emptyList();
        } else {
            this.mTestOutputList = new ArrayList(testOutputList);
        }
    }

    public List<String> getSalesModelAllowList() {
        return this.mSalesModelAllowList;
    }

    public void setSalesModelAllowList(List<String> salesModelAllowList) {
        if (salesModelAllowList == null) {
            this.mSalesModelAllowList = Collections.emptyList();
        } else {
            this.mSalesModelAllowList = new ArrayList(salesModelAllowList);
        }
    }

    public List<String> getSalesModelBlockList() {
        return this.mSalesModelBlockList;
    }

    public void setSalesModelBlockList(List<String> salesModelBlockList) {
        if (salesModelBlockList == null) {
            this.mSalesModelBlockList = Collections.emptyList();
        } else {
            this.mSalesModelBlockList = new ArrayList(salesModelBlockList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeGroup(Parcel dest, Group set) {
        dest.writeString(set.groupA);
        dest.writeString(set.groupB);
        dest.writeString(set.groupC);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Group readGroup(Parcel src) {
        Group set = new Group();
        set.groupA = src.readString();
        set.groupB = src.readString();
        set.groupC = src.readString();
        return set;
    }

    public SemAbTestConfiguration(SemAbTestConfiguration source) {
        this.id = source.id;
        this.moduleName = source.moduleName;
        this.startDate = source.startDate;
        this.endDate = source.endDate;
        this.mSalesModelAllowList = new ArrayList(source.mSalesModelAllowList);
        this.mSalesModelBlockList = new ArrayList(source.mSalesModelBlockList);
        this.abTestGroupAllocation = source.abTestGroupAllocation;
        this.mTestParamList = new ArrayList(source.mTestParamList);
        this.mGroupSize = source.mGroupSize;
        this.mTestOutputList = new ArrayList(source.mTestOutputList);
    }

    public boolean matches(SemAbTestConfiguration other) {
        if (other == null) {
            return false;
        }
        return TextUtils.equals(this.moduleName, other.moduleName);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return this.moduleName.hashCode();
    }

    public boolean isAbTestInProgress() {
        String now = "99999999";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
            now = sdf.format(new Date(System.currentTimeMillis()));
        } catch (IllegalArgumentException e) {
        }
        try {
            int today = Integer.parseInt(now);
            if (today < Integer.parseInt(this.startDate)) {
                return false;
            }
            if (today <= Integer.parseInt(this.endDate)) {
                return true;
            }
            return false;
        } catch (NumberFormatException e2) {
            return false;
        }
    }

    public Map<String, String> getMyTestParamsMap() {
        Map<String, String> params = new HashMap<>();
        if (this.abTestGroupAllocation == -1 || !isAbTestInProgress()) {
            return params;
        }
        for (TestParam testParam : this.mTestParamList) {
            if (this.abTestGroupAllocation == 0) {
                params.put(testParam.name, testParam.group.groupA);
            } else if (this.abTestGroupAllocation == 1) {
                params.put(testParam.name, testParam.group.groupB);
            } else if (this.abTestGroupAllocation == 2) {
                params.put(testParam.name, testParam.group.groupC);
            }
        }
        return params;
    }

    public List<String> getMyOutputList() {
        List<String> outputList = new ArrayList<>();
        if (this.abTestGroupAllocation == -1 || !isAbTestInProgress()) {
            return outputList;
        }
        for (TestOutput output : this.mTestOutputList) {
            outputList.add(output.name);
        }
        return outputList;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SemAbTestConfiguration)) {
            return false;
        }
        SemAbTestConfiguration other = (SemAbTestConfiguration) obj;
        return matches(other);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.moduleName);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeList(this.mSalesModelAllowList);
        dest.writeList(this.mSalesModelBlockList);
        dest.writeInt(this.abTestGroupAllocation);
        dest.writeList(this.mTestParamList);
        writeGroup(dest, this.mGroupSize);
        dest.writeList(this.mTestOutputList);
    }

    public String toString() {
        StringBuilder sbuf = new StringBuilder();
        sbuf.append("AbTestConfiguration ID : ").append(this.id).append("\n");
        sbuf.append(" moduleName : ").append(this.moduleName).append("\n");
        sbuf.append(" startDate : ").append(this.startDate).append("\n");
        sbuf.append(" endDate : ").append(this.endDate).append("\n");
        if (this.mSalesModelAllowList != null && !this.mSalesModelAllowList.isEmpty()) {
            sbuf.append(" mSalesModelAllowList: [");
            for (String salesModelAllow : this.mSalesModelAllowList) {
                sbuf.append(salesModelAllow + ", ");
            }
            sbuf.append(NavigationBarInflaterView.SIZE_MOD_END);
        } else {
            sbuf.append(" salesModelAllowList unset");
        }
        if (this.mSalesModelBlockList != null && !this.mSalesModelBlockList.isEmpty()) {
            sbuf.append(" mSalesModelBlockList: [");
            for (String salesModelBlock : this.mSalesModelBlockList) {
                sbuf.append(salesModelBlock + ", ");
            }
            sbuf.append(NavigationBarInflaterView.SIZE_MOD_END);
        } else {
            sbuf.append(" mSalesModelBlockList unset");
        }
        sbuf.append(" abTestGroupAllocation : ").append(this.abTestGroupAllocation).append("\n");
        if (this.mTestParamList != null && !this.mTestParamList.isEmpty()) {
            sbuf.append(" TestParamList: [");
            for (TestParam testParam : this.mTestParamList) {
                sbuf.append(testParam + ", ");
            }
            sbuf.append(NavigationBarInflaterView.SIZE_MOD_END);
        } else {
            sbuf.append(" TestParamList unset");
        }
        sbuf.append("\n");
        sbuf.append(" GroupSize : ").append(this.mGroupSize).append("\n");
        if (this.mTestOutputList != null && !this.mTestOutputList.isEmpty()) {
            sbuf.append(" TestOutputList: [");
            for (TestOutput testOutput : this.mTestOutputList) {
                sbuf.append(testOutput + ", ");
            }
            sbuf.append(NavigationBarInflaterView.SIZE_MOD_END);
        } else {
            sbuf.append(" TestOutputList unset");
        }
        sbuf.append("\n");
        return sbuf.toString();
    }
}
