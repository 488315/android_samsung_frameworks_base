package com.android.internal.telephony;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.telephony.Rlog;

import java.util.ArrayList;

/* loaded from: classes5.dex */
public class PublishDialog implements Parcelable {
    public static final Parcelable.Creator<PublishDialog> CREATOR =
            new Parcelable.Creator<
                    PublishDialog>() { // from class: com.android.internal.telephony.PublishDialog.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PublishDialog createFromParcel(Parcel in) {
                    return new PublishDialog(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PublishDialog[] newArray(int size) {
                    return new PublishDialog[size];
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

    public int[] arrayListToIntArray(ArrayList<Integer> ints) {
        int[] ret = new int[ints.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = ints.get(i).intValue();
        }
        return ret;
    }

    public static ArrayList<Integer> intArrayToArrayList(int[] arr) {
        ArrayList<Integer> arrayList = new ArrayList<>(arr.length);
        for (int i : arr) {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    public String[] arrayListToStringArray(ArrayList<String> strs) {
        String[] ret = new String[strs.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = strs.get(i);
        }
        return ret;
    }

    public static ArrayList<String> stringArrayToArrayList(String[] arr) {
        ArrayList<String> arrayList = new ArrayList<>(arr.length);
        for (String i : arr) {
            arrayList.add(i);
        }
        return arrayList;
    }

    public boolean[] arrayListToBooleanArray(ArrayList<Boolean> booleans) {
        boolean[] ret = new boolean[booleans.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = booleans.get(i).booleanValue();
        }
        return ret;
    }

    public static ArrayList<Boolean> booleanArrayToArrayList(boolean[] arr) {
        ArrayList<Boolean> arrayList = new ArrayList<>(arr.length);
        for (boolean i : arr) {
            arrayList.add(Boolean.valueOf(i));
        }
        return arrayList;
    }

    public long[] arrayListToLongArray(ArrayList<Long> longs) {
        long[] ret = new long[longs.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = longs.get(i).longValue();
        }
        return ret;
    }

    public static ArrayList<Long> longArrayToArrayList(long[] arr) {
        ArrayList<Long> arrayList = new ArrayList<>(arr.length);
        for (long i : arr) {
            arrayList.add(Long.valueOf(i));
        }
        return arrayList;
    }

    public void dump() {
        if (!SemTelephonyUtils.SHIP_BUILD) {
            log("==== Start Dump for Publish Diallog =====");
            log("==== mCallCount is " + this.mCallCount);
            log(
                    " mCallId: "
                            + this.mCallId
                            + ", mCallDomain: "
                            + this.mCallDomain
                            + ", mCallStatus: "
                            + this.mCallStatus
                            + ", mCallType: "
                            + this.mCallType
                            + ", mCallDirection: "
                            + this.mCallDirection
                            + ", mCallRemoteUri: "
                            + this.mCallRemoteUri
                            + ", mCallPullable: "
                            + this.mCallPullable
                            + ", mCallNumberPresentation: "
                            + this.mCallNumberPresentation
                            + ", mCallCnapNamePresentation: "
                            + this.mCallCnapNamePresentation
                            + ", mCallCnapName: "
                            + this.mCallCnapName
                            + ", mCallMptyCall: "
                            + this.mCallMptyCall
                            + ", mConnectedTime: "
                            + this.mConnectedTime);
            log("==== End Dump for Publish Diallog   =====");
        }
    }

    public void setCallCount(int callCount) {
        this.mCallCount = callCount;
    }

    public int getCallCount() {
        return this.mCallCount;
    }

    public void addCallId(int callId) {
        this.mCallId.add(Integer.valueOf(callId));
    }

    public int[] getCallId() {
        return arrayListToIntArray(this.mCallId);
    }

    public void addCallDomain(int callDomain) {
        this.mCallDomain.add(Integer.valueOf(callDomain));
    }

    public int[] getCallDomain() {
        return arrayListToIntArray(this.mCallDomain);
    }

    public void addCallStatus(int callStatus) {
        this.mCallStatus.add(Integer.valueOf(callStatus));
    }

    public int[] getCallStatus() {
        return arrayListToIntArray(this.mCallStatus);
    }

    public void setCallStatus(int callId, int callStatus) {
        try {
            this.mCallStatus.set(callId, Integer.valueOf(callStatus));
        } catch (IndexOutOfBoundsException e) {
            log("setCallStatus is fail. " + e);
        }
    }

    public void addCallType(int callType) {
        this.mCallType.add(Integer.valueOf(callType));
    }

    public int[] getCallType() {
        return arrayListToIntArray(this.mCallType);
    }

    public void addCallDirection(int callDirection) {
        this.mCallDirection.add(Integer.valueOf(callDirection));
    }

    public int[] getCallDirection() {
        return arrayListToIntArray(this.mCallDirection);
    }

    public void addCallRemoteUri(String callRemoteUri) {
        this.mCallRemoteUri.add(callRemoteUri);
    }

    public String[] getCallRemoteUri() {
        return arrayListToStringArray(this.mCallRemoteUri);
    }

    public void addCallPullable(boolean pullable) {
        this.mCallPullable.add(Boolean.valueOf(pullable));
    }

    public boolean[] getCallPullable() {
        return arrayListToBooleanArray(this.mCallPullable);
    }

    public void addCallNumberPresentation(int callNumberPresentation) {
        this.mCallNumberPresentation.add(Integer.valueOf(callNumberPresentation));
    }

    public int[] getCallNumberPresentation() {
        return arrayListToIntArray(this.mCallNumberPresentation);
    }

    public void addCallCnapNamePresentation(int callCnapNamePresentation) {
        this.mCallCnapNamePresentation.add(Integer.valueOf(callCnapNamePresentation));
    }

    public int[] getCallCnapNamePresentation() {
        return arrayListToIntArray(this.mCallCnapNamePresentation);
    }

    public void addCallCnapName(String callCnapName) {
        this.mCallCnapName.add(callCnapName);
    }

    public String[] getCallCnapName() {
        return arrayListToStringArray(this.mCallCnapName);
    }

    public void addCallMpty(boolean mptyCall) {
        this.mCallMptyCall.add(Boolean.valueOf(mptyCall));
    }

    public boolean[] getCallMpty() {
        return arrayListToBooleanArray(this.mCallMptyCall);
    }

    public void setCallMpty(int callId, boolean mptyCall) {
        try {
            this.mCallMptyCall.set(callId, Boolean.valueOf(mptyCall));
        } catch (IndexOutOfBoundsException e) {
            log("setCallMpty is fail. " + e);
        }
    }

    public void addConnectedTime(long connectedTime) {
        this.mConnectedTime.add(Long.valueOf(connectedTime));
    }

    public long[] getConnectedTime() {
        return arrayListToLongArray(this.mConnectedTime);
    }

    protected void log(String msg) {
        Rlog.d(LOG_TAG, msg);
    }

    public PublishDialog() {}

    public PublishDialog(Parcel source) {
        readFromParcel(source);
    }

    public void readFromParcel(Parcel source) {
        this.mCallCount = source.readInt();
        int length = source.readInt();
        if (length > 0) {
            int[] callId = new int[length];
            source.readIntArray(callId);
            this.mCallId = intArrayToArrayList(callId);
        }
        int length2 = source.readInt();
        if (length2 > 0) {
            int[] callDomain = new int[length2];
            source.readIntArray(callDomain);
            this.mCallDomain = intArrayToArrayList(callDomain);
        }
        int length3 = source.readInt();
        if (length3 > 0) {
            int[] callStatus = new int[length3];
            source.readIntArray(callStatus);
            this.mCallStatus = intArrayToArrayList(callStatus);
        }
        int length4 = source.readInt();
        if (length4 > 0) {
            int[] callType = new int[length4];
            source.readIntArray(callType);
            this.mCallType = intArrayToArrayList(callType);
        }
        int length5 = source.readInt();
        if (length5 > 0) {
            int[] callDirection = new int[length5];
            source.readIntArray(callDirection);
            this.mCallDirection = intArrayToArrayList(callDirection);
        }
        int length6 = source.readInt();
        if (length6 > 0) {
            String[] callRemoteUri = new String[length6];
            source.readStringArray(callRemoteUri);
            this.mCallRemoteUri = stringArrayToArrayList(callRemoteUri);
        }
        int length7 = source.readInt();
        if (length7 > 0) {
            boolean[] callPullable = new boolean[length7];
            source.readBooleanArray(callPullable);
            this.mCallPullable = booleanArrayToArrayList(callPullable);
        }
        int length8 = source.readInt();
        if (length8 > 0) {
            int[] callNumberPresentation = new int[length8];
            source.readIntArray(callNumberPresentation);
            this.mCallNumberPresentation = intArrayToArrayList(callNumberPresentation);
        }
        int length9 = source.readInt();
        if (length9 > 0) {
            int[] callCnapNamePresentation = new int[length9];
            source.readIntArray(callCnapNamePresentation);
            this.mCallCnapNamePresentation = intArrayToArrayList(callCnapNamePresentation);
        }
        int length10 = source.readInt();
        if (length10 > 0) {
            String[] callCnapName = new String[length10];
            source.readStringArray(callCnapName);
            this.mCallCnapName = stringArrayToArrayList(callCnapName);
        }
        int length11 = source.readInt();
        if (length11 > 0) {
            boolean[] callMptyCall = new boolean[length11];
            source.readBooleanArray(callMptyCall);
            this.mCallMptyCall = booleanArrayToArrayList(callMptyCall);
        }
        int length12 = source.readInt();
        if (length12 > 0) {
            long[] connectedTime = new long[length12];
            source.readLongArray(connectedTime);
            this.mConnectedTime = longArrayToArrayList(connectedTime);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mCallCount);
        if (this.mCallId != null && this.mCallId.size() > 0) {
            dest.writeInt(this.mCallId.size());
            dest.writeIntArray(getCallId());
        } else {
            dest.writeInt(0);
        }
        if (this.mCallDomain != null && this.mCallDomain.size() > 0) {
            dest.writeInt(this.mCallDomain.size());
            dest.writeIntArray(getCallDomain());
        } else {
            dest.writeInt(0);
        }
        if (this.mCallStatus != null && this.mCallStatus.size() > 0) {
            dest.writeInt(this.mCallStatus.size());
            dest.writeIntArray(getCallStatus());
        } else {
            dest.writeInt(0);
        }
        if (this.mCallType != null && this.mCallType.size() > 0) {
            dest.writeInt(this.mCallType.size());
            dest.writeIntArray(getCallType());
        } else {
            dest.writeInt(0);
        }
        if (this.mCallDirection != null && this.mCallDirection.size() > 0) {
            dest.writeInt(this.mCallDirection.size());
            dest.writeIntArray(getCallDirection());
        } else {
            dest.writeInt(0);
        }
        if (this.mCallRemoteUri != null && this.mCallRemoteUri.size() > 0) {
            dest.writeInt(this.mCallRemoteUri.size());
            dest.writeStringArray(getCallRemoteUri());
        } else {
            dest.writeInt(0);
        }
        if (this.mCallPullable != null && this.mCallPullable.size() > 0) {
            dest.writeInt(this.mCallPullable.size());
            dest.writeBooleanArray(getCallPullable());
        } else {
            dest.writeInt(0);
        }
        if (this.mCallNumberPresentation != null && this.mCallNumberPresentation.size() > 0) {
            dest.writeInt(this.mCallNumberPresentation.size());
            dest.writeIntArray(getCallNumberPresentation());
        } else {
            dest.writeInt(0);
        }
        if (this.mCallCnapNamePresentation != null && this.mCallCnapNamePresentation.size() > 0) {
            dest.writeInt(this.mCallCnapNamePresentation.size());
            dest.writeIntArray(getCallCnapNamePresentation());
        } else {
            dest.writeInt(0);
        }
        if (this.mCallCnapName != null && this.mCallCnapName.size() > 0) {
            dest.writeInt(this.mCallCnapName.size());
            dest.writeStringArray(getCallCnapName());
        } else {
            dest.writeInt(0);
        }
        if (this.mCallMptyCall != null && this.mCallMptyCall.size() > 0) {
            dest.writeInt(this.mCallMptyCall.size());
            dest.writeBooleanArray(getCallMpty());
        } else {
            dest.writeInt(0);
        }
        if (this.mConnectedTime != null && this.mConnectedTime.size() > 0) {
            dest.writeInt(this.mConnectedTime.size());
            dest.writeLongArray(getConnectedTime());
        } else {
            dest.writeInt(0);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
