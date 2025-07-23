package com.sec.ims.presence;

import android.util.Pair;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class DeviceTuple {
    public List<Pair<String, String>> mDescriptions;
    public List<String> mDeviceCapabilities;
    public String mDeviceId;
    public List<Pair<String, String>> mNotes;
    public String mTimestamp;

    public DeviceTuple(String str) {
        this.mDeviceId = str;
        this.mDeviceCapabilities = null;
        this.mDescriptions = null;
        this.mNotes = null;
        this.mTimestamp = null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DeviceTuple deviceTuple = (DeviceTuple) obj;
        String str = this.mDeviceId;
        if (str == null) {
            if (deviceTuple.mDeviceId != null) {
                return false;
            }
        } else if (!str.equals(deviceTuple.mDeviceId)) {
            return false;
        }
        List<String> list = this.mDeviceCapabilities;
        if (list == null) {
            if (deviceTuple.mDeviceCapabilities != null) {
                return false;
            }
        } else if (!list.equals(deviceTuple.mDeviceCapabilities)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        String str = this.mDeviceId;
        int hashCode = ((str == null ? 0 : str.hashCode()) + 31) * 31;
        List<String> list = this.mDeviceCapabilities;
        int hashCode2 = (hashCode + (list == null ? 0 : list.hashCode())) * 31;
        List<Pair<String, String>> list2 = this.mDescriptions;
        int hashCode3 = (hashCode2 + (list2 == null ? 0 : list2.hashCode())) * 31;
        List<Pair<String, String>> list3 = this.mNotes;
        int hashCode4 = (hashCode3 + (list3 == null ? 0 : list3.hashCode())) * 31;
        String str2 = this.mTimestamp;
        return hashCode4 + (str2 != null ? str2.hashCode() : 0);
    }

    public DeviceTuple(String str, List<String> list) {
        this(str);
        this.mDeviceCapabilities = list;
    }

    public DeviceTuple(String str, List<String> list, String str2) {
        this(str, list);
        this.mTimestamp = str2;
    }

    public DeviceTuple(String str, List<String> list, List<Pair<String, String>> list2, List<Pair<String, String>> list3, String str2) {
        this(str, list, str2);
        this.mDescriptions = list2;
        this.mNotes = list3;
    }
}
