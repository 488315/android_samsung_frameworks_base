package com.android.internal.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.telephony.Rlog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class PublishDialog implements Parcelable {
    public static final Parcelable.Creator<PublishDialog> CREATOR = new Parcelable.Creator<PublishDialog>() { // from class: com.android.internal.telephony.PublishDialog.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PublishDialog createFromParcel(Parcel parcel) {
            return new PublishDialog(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PublishDialog[] newArray(int i) {
            return new PublishDialog[i];
        }
    };
    public static final int CS_DOMAIN = 1;
    private static final String LOG_TAG = "PublishDialog";
    public static final int PS_DOMAIN = 2;
    private int mCallCount;
    private ArrayList<Integer> mCallId = new ArrayList<>();
    private ArrayList<Integer> mCallDomain = new ArrayList<>();
    private ArrayList<Integer> mCallStatus = new ArrayList<>();
    private ArrayList<Integer> mCallType = new ArrayList<>();
    private ArrayList<Integer> mCallDirection = new ArrayList<>();
    private ArrayList<String> mCallRemoteUri = new ArrayList<>();
    private ArrayList<Boolean> mCallPullable = new ArrayList<>();
    private ArrayList<Integer> mCallNumberPresentation = new ArrayList<>();
    private ArrayList<Integer> mCallCnapNamePresentation = new ArrayList<>();
    private ArrayList<String> mCallCnapName = new ArrayList<>();
    private ArrayList<Boolean> mCallMptyCall = new ArrayList<>();
    private ArrayList<Long> mConnectedTime = new ArrayList<>();

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int[] arrayListToIntArray(ArrayList<Integer> arrayList) {
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = arrayList.get(i).intValue();
        }
        return iArr;
    }

    public static ArrayList<Integer> intArrayToArrayList(int[] iArr) {
        ArrayList<Integer> arrayList = new ArrayList<>(iArr.length);
        for (int i : iArr) {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    public String[] arrayListToStringArray(ArrayList<String> arrayList) {
        int size = arrayList.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = arrayList.get(i);
        }
        return strArr;
    }

    public static ArrayList<String> stringArrayToArrayList(String[] strArr) {
        ArrayList<String> arrayList = new ArrayList<>(strArr.length);
        for (String str : strArr) {
            arrayList.add(str);
        }
        return arrayList;
    }

    public boolean[] arrayListToBooleanArray(ArrayList<Boolean> arrayList) {
        int size = arrayList.size();
        boolean[] zArr = new boolean[size];
        for (int i = 0; i < size; i++) {
            zArr[i] = arrayList.get(i).booleanValue();
        }
        return zArr;
    }

    public static ArrayList<Boolean> booleanArrayToArrayList(boolean[] zArr) {
        ArrayList<Boolean> arrayList = new ArrayList<>(zArr.length);
        for (boolean z : zArr) {
            arrayList.add(Boolean.valueOf(z));
        }
        return arrayList;
    }

    public long[] arrayListToLongArray(ArrayList<Long> arrayList) {
        int size = arrayList.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = arrayList.get(i).longValue();
        }
        return jArr;
    }

    public static ArrayList<Long> longArrayToArrayList(long[] jArr) {
        ArrayList<Long> arrayList = new ArrayList<>(jArr.length);
        for (long j : jArr) {
            arrayList.add(Long.valueOf(j));
        }
        return arrayList;
    }

    public void dump() {
        if (SemTelephonyUtils.SHIP_BUILD) {
            return;
        }
        log("==== Start Dump for Publish Diallog =====");
        log("==== mCallCount is " + this.mCallCount);
        log(" mCallId: " + this.mCallId + ", mCallDomain: " + this.mCallDomain + ", mCallStatus: " + this.mCallStatus + ", mCallType: " + this.mCallType + ", mCallDirection: " + this.mCallDirection + ", mCallRemoteUri: " + this.mCallRemoteUri + ", mCallPullable: " + this.mCallPullable + ", mCallNumberPresentation: " + this.mCallNumberPresentation + ", mCallCnapNamePresentation: " + this.mCallCnapNamePresentation + ", mCallCnapName: " + this.mCallCnapName + ", mCallMptyCall: " + this.mCallMptyCall + ", mConnectedTime: " + this.mConnectedTime);
        log("==== End Dump for Publish Diallog   =====");
    }

    public void setCallCount(int i) {
        this.mCallCount = i;
    }

    public int getCallCount() {
        return this.mCallCount;
    }

    public void addCallId(int i) {
        this.mCallId.add(Integer.valueOf(i));
    }

    public int[] getCallId() {
        return arrayListToIntArray(this.mCallId);
    }

    public void addCallDomain(int i) {
        this.mCallDomain.add(Integer.valueOf(i));
    }

    public int[] getCallDomain() {
        return arrayListToIntArray(this.mCallDomain);
    }

    public void addCallStatus(int i) {
        this.mCallStatus.add(Integer.valueOf(i));
    }

    public int[] getCallStatus() {
        return arrayListToIntArray(this.mCallStatus);
    }

    public void setCallStatus(int i, int i2) {
        try {
            this.mCallStatus.set(i, Integer.valueOf(i2));
        } catch (IndexOutOfBoundsException e) {
            log("setCallStatus is fail. " + e);
        }
    }

    public void addCallType(int i) {
        this.mCallType.add(Integer.valueOf(i));
    }

    public int[] getCallType() {
        return arrayListToIntArray(this.mCallType);
    }

    public void addCallDirection(int i) {
        this.mCallDirection.add(Integer.valueOf(i));
    }

    public int[] getCallDirection() {
        return arrayListToIntArray(this.mCallDirection);
    }

    public void addCallRemoteUri(String str) {
        this.mCallRemoteUri.add(str);
    }

    public String[] getCallRemoteUri() {
        return arrayListToStringArray(this.mCallRemoteUri);
    }

    public void addCallPullable(boolean z) {
        this.mCallPullable.add(Boolean.valueOf(z));
    }

    public boolean[] getCallPullable() {
        return arrayListToBooleanArray(this.mCallPullable);
    }

    public void addCallNumberPresentation(int i) {
        this.mCallNumberPresentation.add(Integer.valueOf(i));
    }

    public int[] getCallNumberPresentation() {
        return arrayListToIntArray(this.mCallNumberPresentation);
    }

    public void addCallCnapNamePresentation(int i) {
        this.mCallCnapNamePresentation.add(Integer.valueOf(i));
    }

    public int[] getCallCnapNamePresentation() {
        return arrayListToIntArray(this.mCallCnapNamePresentation);
    }

    public void addCallCnapName(String str) {
        this.mCallCnapName.add(str);
    }

    public String[] getCallCnapName() {
        return arrayListToStringArray(this.mCallCnapName);
    }

    public void addCallMpty(boolean z) {
        this.mCallMptyCall.add(Boolean.valueOf(z));
    }

    public boolean[] getCallMpty() {
        return arrayListToBooleanArray(this.mCallMptyCall);
    }

    public void setCallMpty(int i, boolean z) {
        try {
            this.mCallMptyCall.set(i, Boolean.valueOf(z));
        } catch (IndexOutOfBoundsException e) {
            log("setCallMpty is fail. " + e);
        }
    }

    public void addConnectedTime(long j) {
        this.mConnectedTime.add(Long.valueOf(j));
    }

    public long[] getConnectedTime() {
        return arrayListToLongArray(this.mConnectedTime);
    }

    protected void log(String str) {
        Rlog.d(LOG_TAG, str);
    }

    public PublishDialog() {
    }

    public PublishDialog(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.mCallCount = parcel.readInt();
        int i = parcel.readInt();
        if (i > 0) {
            int[] iArr = new int[i];
            parcel.readIntArray(iArr);
            this.mCallId = intArrayToArrayList(iArr);
        }
        int i2 = parcel.readInt();
        if (i2 > 0) {
            int[] iArr2 = new int[i2];
            parcel.readIntArray(iArr2);
            this.mCallDomain = intArrayToArrayList(iArr2);
        }
        int i3 = parcel.readInt();
        if (i3 > 0) {
            int[] iArr3 = new int[i3];
            parcel.readIntArray(iArr3);
            this.mCallStatus = intArrayToArrayList(iArr3);
        }
        int i4 = parcel.readInt();
        if (i4 > 0) {
            int[] iArr4 = new int[i4];
            parcel.readIntArray(iArr4);
            this.mCallType = intArrayToArrayList(iArr4);
        }
        int i5 = parcel.readInt();
        if (i5 > 0) {
            int[] iArr5 = new int[i5];
            parcel.readIntArray(iArr5);
            this.mCallDirection = intArrayToArrayList(iArr5);
        }
        int i6 = parcel.readInt();
        if (i6 > 0) {
            String[] strArr = new String[i6];
            parcel.readStringArray(strArr);
            this.mCallRemoteUri = stringArrayToArrayList(strArr);
        }
        int i7 = parcel.readInt();
        if (i7 > 0) {
            boolean[] zArr = new boolean[i7];
            parcel.readBooleanArray(zArr);
            this.mCallPullable = booleanArrayToArrayList(zArr);
        }
        int i8 = parcel.readInt();
        if (i8 > 0) {
            int[] iArr6 = new int[i8];
            parcel.readIntArray(iArr6);
            this.mCallNumberPresentation = intArrayToArrayList(iArr6);
        }
        int i9 = parcel.readInt();
        if (i9 > 0) {
            int[] iArr7 = new int[i9];
            parcel.readIntArray(iArr7);
            this.mCallCnapNamePresentation = intArrayToArrayList(iArr7);
        }
        int i10 = parcel.readInt();
        if (i10 > 0) {
            String[] strArr2 = new String[i10];
            parcel.readStringArray(strArr2);
            this.mCallCnapName = stringArrayToArrayList(strArr2);
        }
        int i11 = parcel.readInt();
        if (i11 > 0) {
            boolean[] zArr2 = new boolean[i11];
            parcel.readBooleanArray(zArr2);
            this.mCallMptyCall = booleanArrayToArrayList(zArr2);
        }
        int i12 = parcel.readInt();
        if (i12 > 0) {
            long[] jArr = new long[i12];
            parcel.readLongArray(jArr);
            this.mConnectedTime = longArrayToArrayList(jArr);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCallCount);
        ArrayList<Integer> arrayList = this.mCallId;
        if (arrayList != null && arrayList.size() > 0) {
            parcel.writeInt(this.mCallId.size());
            parcel.writeIntArray(getCallId());
        } else {
            parcel.writeInt(0);
        }
        ArrayList<Integer> arrayList2 = this.mCallDomain;
        if (arrayList2 != null && arrayList2.size() > 0) {
            parcel.writeInt(this.mCallDomain.size());
            parcel.writeIntArray(getCallDomain());
        } else {
            parcel.writeInt(0);
        }
        ArrayList<Integer> arrayList3 = this.mCallStatus;
        if (arrayList3 != null && arrayList3.size() > 0) {
            parcel.writeInt(this.mCallStatus.size());
            parcel.writeIntArray(getCallStatus());
        } else {
            parcel.writeInt(0);
        }
        ArrayList<Integer> arrayList4 = this.mCallType;
        if (arrayList4 != null && arrayList4.size() > 0) {
            parcel.writeInt(this.mCallType.size());
            parcel.writeIntArray(getCallType());
        } else {
            parcel.writeInt(0);
        }
        ArrayList<Integer> arrayList5 = this.mCallDirection;
        if (arrayList5 != null && arrayList5.size() > 0) {
            parcel.writeInt(this.mCallDirection.size());
            parcel.writeIntArray(getCallDirection());
        } else {
            parcel.writeInt(0);
        }
        ArrayList<String> arrayList6 = this.mCallRemoteUri;
        if (arrayList6 != null && arrayList6.size() > 0) {
            parcel.writeInt(this.mCallRemoteUri.size());
            parcel.writeStringArray(getCallRemoteUri());
        } else {
            parcel.writeInt(0);
        }
        ArrayList<Boolean> arrayList7 = this.mCallPullable;
        if (arrayList7 != null && arrayList7.size() > 0) {
            parcel.writeInt(this.mCallPullable.size());
            parcel.writeBooleanArray(getCallPullable());
        } else {
            parcel.writeInt(0);
        }
        ArrayList<Integer> arrayList8 = this.mCallNumberPresentation;
        if (arrayList8 != null && arrayList8.size() > 0) {
            parcel.writeInt(this.mCallNumberPresentation.size());
            parcel.writeIntArray(getCallNumberPresentation());
        } else {
            parcel.writeInt(0);
        }
        ArrayList<Integer> arrayList9 = this.mCallCnapNamePresentation;
        if (arrayList9 != null && arrayList9.size() > 0) {
            parcel.writeInt(this.mCallCnapNamePresentation.size());
            parcel.writeIntArray(getCallCnapNamePresentation());
        } else {
            parcel.writeInt(0);
        }
        ArrayList<String> arrayList10 = this.mCallCnapName;
        if (arrayList10 != null && arrayList10.size() > 0) {
            parcel.writeInt(this.mCallCnapName.size());
            parcel.writeStringArray(getCallCnapName());
        } else {
            parcel.writeInt(0);
        }
        ArrayList<Boolean> arrayList11 = this.mCallMptyCall;
        if (arrayList11 != null && arrayList11.size() > 0) {
            parcel.writeInt(this.mCallMptyCall.size());
            parcel.writeBooleanArray(getCallMpty());
        } else {
            parcel.writeInt(0);
        }
        ArrayList<Long> arrayList12 = this.mConnectedTime;
        if (arrayList12 != null && arrayList12.size() > 0) {
            parcel.writeInt(this.mConnectedTime.size());
            parcel.writeLongArray(getConnectedTime());
        } else {
            parcel.writeInt(0);
        }
    }
}
