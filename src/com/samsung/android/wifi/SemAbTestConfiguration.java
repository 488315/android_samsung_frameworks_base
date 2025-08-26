package com.samsung.android.wifi;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes6.dex */
public class SemAbTestConfiguration implements Parcelable {
    public static final Parcelable.Creator<SemAbTestConfiguration> CREATOR = new Parcelable.Creator<SemAbTestConfiguration>() { // from class: com.samsung.android.wifi.SemAbTestConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemAbTestConfiguration createFromParcel(Parcel parcel) {
            SemAbTestConfiguration semAbTestConfiguration = new SemAbTestConfiguration();
            semAbTestConfiguration.id = parcel.readString();
            semAbTestConfiguration.moduleName = parcel.readString();
            semAbTestConfiguration.startDate = parcel.readString();
            semAbTestConfiguration.endDate = parcel.readString();
            semAbTestConfiguration.mSalesModelAllowList = parcel.readArrayList(null, String.class);
            semAbTestConfiguration.mSalesModelBlockList = parcel.readArrayList(null, String.class);
            semAbTestConfiguration.abTestGroupAllocation = parcel.readInt();
            semAbTestConfiguration.mTestParamList = parcel.readArrayList(TestParam.class.getClassLoader());
            semAbTestConfiguration.mGroupSize = SemAbTestConfiguration.readGroup(parcel);
            semAbTestConfiguration.mTestOutputList = parcel.readArrayList(TestOutput.class.getClassLoader());
            return semAbTestConfiguration;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemAbTestConfiguration[] newArray(int i) {
            return new SemAbTestConfiguration[i];
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
    public @interface AbTestGroupAllocation {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class Group {
        public String groupA;
        public String groupB;
        public String groupC;

        public String toString() {
            return "{groupA='" + this.groupA + "', groupB='" + this.groupB + "', groupC='" + this.groupC + "'}";
        }
    }

    public static class TestParam implements Parcelable {
        public static final Parcelable.Creator<TestParam> CREATOR = new Parcelable.Creator<TestParam>() { // from class: com.samsung.android.wifi.SemAbTestConfiguration.TestParam.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TestParam createFromParcel(Parcel parcel) {
                TestParam testParam = new TestParam();
                testParam.name = parcel.readString();
                testParam.group.groupA = parcel.readString();
                testParam.group.groupB = parcel.readString();
                testParam.group.groupC = parcel.readString();
                return testParam;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TestParam[] newArray(int i) {
                return new TestParam[i];
            }
        };
        public Group group = new Group();
        public String name;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "TestParam{name='" + this.name + "', group=" + this.group + '}';
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.name);
            SemAbTestConfiguration.writeGroup(parcel, this.group);
        }
    }

    public static class TestOutput implements Parcelable {
        public static final Parcelable.Creator<TestOutput> CREATOR = new Parcelable.Creator<TestOutput>() { // from class: com.samsung.android.wifi.SemAbTestConfiguration.TestOutput.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TestOutput createFromParcel(Parcel parcel) {
                TestOutput testOutput = new TestOutput();
                testOutput.name = parcel.readString();
                testOutput.event.groupA = parcel.readString();
                testOutput.event.groupB = parcel.readString();
                testOutput.event.groupC = parcel.readString();
                testOutput.dimension.groupA = parcel.readString();
                testOutput.dimension.groupB = parcel.readString();
                testOutput.dimension.groupC = parcel.readString();
                return testOutput;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TestOutput[] newArray(int i) {
                return new TestOutput[i];
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
            return "TestOutput{name='" + this.name + "', event=" + this.event + ", dimension=" + this.dimension + '}';
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.name);
            SemAbTestConfiguration.writeGroup(parcel, this.event);
            SemAbTestConfiguration.writeGroup(parcel, this.dimension);
        }
    }

    public SemAbTestConfiguration() {
        this.id = null;
        this.moduleName = null;
        this.startDate = null;
        this.endDate = null;
        this.mSalesModelAllowList = Collections.EMPTY_LIST;
        this.mSalesModelBlockList = Collections.EMPTY_LIST;
        this.abTestGroupAllocation = -1;
        this.mTestParamList = Collections.EMPTY_LIST;
        this.mGroupSize = new Group();
        this.mTestOutputList = Collections.EMPTY_LIST;
    }

    public SemAbTestConfiguration(String str) {
        this.id = "0";
        this.moduleName = str;
        this.startDate = "2024.03.01";
        this.endDate = "2024.12.31";
        this.mSalesModelAllowList = Collections.EMPTY_LIST;
        this.mSalesModelBlockList = Collections.EMPTY_LIST;
        this.abTestGroupAllocation = 0;
        this.mTestParamList = Collections.EMPTY_LIST;
        this.mGroupSize = new Group();
        this.mTestOutputList = Collections.EMPTY_LIST;
    }

    public List<TestParam> getTestParamList() {
        return this.mTestParamList;
    }

    public void setTestParamList(List<TestParam> list) {
        if (list == null) {
            this.mTestParamList = Collections.EMPTY_LIST;
        } else {
            this.mTestParamList = new ArrayList(list);
        }
    }

    public Group getGroupSize() {
        return this.mGroupSize;
    }

    public void setGroupSize(Group group) {
        if (group == null) {
            this.mGroupSize = new Group();
        } else {
            this.mGroupSize = group;
        }
    }

    public List<TestOutput> getTestOutputList() {
        return this.mTestOutputList;
    }

    public void setTestOutputList(List<TestOutput> list) {
        if (list == null) {
            this.mTestOutputList = Collections.EMPTY_LIST;
        } else {
            this.mTestOutputList = new ArrayList(list);
        }
    }

    public List<String> getSalesModelAllowList() {
        return this.mSalesModelAllowList;
    }

    public void setSalesModelAllowList(List<String> list) {
        if (list == null) {
            this.mSalesModelAllowList = Collections.EMPTY_LIST;
        } else {
            this.mSalesModelAllowList = new ArrayList(list);
        }
    }

    public List<String> getSalesModelBlockList() {
        return this.mSalesModelBlockList;
    }

    public void setSalesModelBlockList(List<String> list) {
        if (list == null) {
            this.mSalesModelBlockList = Collections.EMPTY_LIST;
        } else {
            this.mSalesModelBlockList = new ArrayList(list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeGroup(Parcel parcel, Group group) {
        parcel.writeString(group.groupA);
        parcel.writeString(group.groupB);
        parcel.writeString(group.groupC);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Group readGroup(Parcel parcel) {
        Group group = new Group();
        group.groupA = parcel.readString();
        group.groupB = parcel.readString();
        group.groupC = parcel.readString();
        return group;
    }

    public SemAbTestConfiguration(SemAbTestConfiguration semAbTestConfiguration) {
        this.id = semAbTestConfiguration.id;
        this.moduleName = semAbTestConfiguration.moduleName;
        this.startDate = semAbTestConfiguration.startDate;
        this.endDate = semAbTestConfiguration.endDate;
        this.mSalesModelAllowList = new ArrayList(semAbTestConfiguration.mSalesModelAllowList);
        this.mSalesModelBlockList = new ArrayList(semAbTestConfiguration.mSalesModelBlockList);
        this.abTestGroupAllocation = semAbTestConfiguration.abTestGroupAllocation;
        this.mTestParamList = new ArrayList(semAbTestConfiguration.mTestParamList);
        this.mGroupSize = semAbTestConfiguration.mGroupSize;
        this.mTestOutputList = new ArrayList(semAbTestConfiguration.mTestOutputList);
    }

    public boolean matches(SemAbTestConfiguration semAbTestConfiguration) {
        if (semAbTestConfiguration == null) {
            return false;
        }
        return TextUtils.equals(this.moduleName, semAbTestConfiguration.moduleName);
    }

    public int hashCode() {
        return this.moduleName.hashCode();
    }

    public boolean isAbTestInProgress() throws NumberFormatException {
        String str;
        try {
            str = new SimpleDateFormat("yyyyMMdd", Locale.US).format(new Date(System.currentTimeMillis()));
        } catch (IllegalArgumentException unused) {
            str = "99999999";
        }
        try {
            int i = Integer.parseInt(str);
            if (i >= Integer.parseInt(this.startDate)) {
                return i <= Integer.parseInt(this.endDate);
            }
            return false;
        } catch (NumberFormatException unused2) {
            return false;
        }
    }

    public Map<String, String> getMyTestParamsMap() {
        HashMap map = new HashMap();
        if (this.abTestGroupAllocation != -1 && isAbTestInProgress()) {
            for (TestParam testParam : this.mTestParamList) {
                int i = this.abTestGroupAllocation;
                if (i == 0) {
                    map.put(testParam.name, testParam.group.groupA);
                } else if (i == 1) {
                    map.put(testParam.name, testParam.group.groupB);
                } else if (i == 2) {
                    map.put(testParam.name, testParam.group.groupC);
                }
            }
        }
        return map;
    }

    public List<String> getMyOutputList() {
        ArrayList arrayList = new ArrayList();
        if (this.abTestGroupAllocation != -1 && isAbTestInProgress()) {
            Iterator<TestOutput> it = this.mTestOutputList.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().name);
            }
        }
        return arrayList;
    }

    public boolean equals(Object obj) {
        return (obj instanceof SemAbTestConfiguration) && matches((SemAbTestConfiguration) obj);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.moduleName);
        parcel.writeString(this.startDate);
        parcel.writeString(this.endDate);
        parcel.writeList(this.mSalesModelAllowList);
        parcel.writeList(this.mSalesModelBlockList);
        parcel.writeInt(this.abTestGroupAllocation);
        parcel.writeList(this.mTestParamList);
        writeGroup(parcel, this.mGroupSize);
        parcel.writeList(this.mTestOutputList);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AbTestConfiguration ID : ");
        sb.append(this.id);
        sb.append("\n moduleName : ");
        sb.append(this.moduleName);
        sb.append("\n startDate : ");
        sb.append(this.startDate);
        sb.append("\n endDate : ");
        sb.append(this.endDate);
        sb.append(ShaderAssembler.NEWLINE);
        List<String> list = this.mSalesModelAllowList;
        if (list != null && !list.isEmpty()) {
            sb.append(" mSalesModelAllowList: [");
            Iterator<String> it = this.mSalesModelAllowList.iterator();
            while (it.hasNext()) {
                sb.append(it.next() + ", ");
            }
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        } else {
            sb.append(" salesModelAllowList unset");
        }
        List<String> list2 = this.mSalesModelBlockList;
        if (list2 != null && !list2.isEmpty()) {
            sb.append(" mSalesModelBlockList: [");
            Iterator<String> it2 = this.mSalesModelBlockList.iterator();
            while (it2.hasNext()) {
                sb.append(it2.next() + ", ");
            }
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        } else {
            sb.append(" mSalesModelBlockList unset");
        }
        sb.append(" abTestGroupAllocation : ");
        sb.append(this.abTestGroupAllocation);
        sb.append(ShaderAssembler.NEWLINE);
        List<TestParam> list3 = this.mTestParamList;
        if (list3 != null && !list3.isEmpty()) {
            sb.append(" TestParamList: [");
            Iterator<TestParam> it3 = this.mTestParamList.iterator();
            while (it3.hasNext()) {
                sb.append(it3.next() + ", ");
            }
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        } else {
            sb.append(" TestParamList unset");
        }
        sb.append("\n GroupSize : ");
        sb.append(this.mGroupSize);
        sb.append(ShaderAssembler.NEWLINE);
        List<TestOutput> list4 = this.mTestOutputList;
        if (list4 != null && !list4.isEmpty()) {
            sb.append(" TestOutputList: [");
            Iterator<TestOutput> it4 = this.mTestOutputList.iterator();
            while (it4.hasNext()) {
                sb.append(it4.next() + ", ");
            }
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        } else {
            sb.append(" TestOutputList unset");
        }
        sb.append(ShaderAssembler.NEWLINE);
        return sb.toString();
    }
}
