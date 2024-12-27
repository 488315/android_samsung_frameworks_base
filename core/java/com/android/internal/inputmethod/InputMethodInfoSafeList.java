package com.android.internal.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.inputmethod.InputMethodInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class InputMethodInfoSafeList implements Parcelable {
    public static final Parcelable.Creator<InputMethodInfoSafeList> CREATOR =
            new Parcelable.Creator<
                    InputMethodInfoSafeList>() { // from class:
                                                 // com.android.internal.inputmethod.InputMethodInfoSafeList.1
                @Override // android.os.Parcelable.Creator
                public InputMethodInfoSafeList createFromParcel(Parcel in) {
                    return new InputMethodInfoSafeList(in.readBlob());
                }

                @Override // android.os.Parcelable.Creator
                public InputMethodInfoSafeList[] newArray(int size) {
                    return new InputMethodInfoSafeList[size];
                }
            };
    private byte[] mBuffer;

    public static List<InputMethodInfo> extractFrom(InputMethodInfoSafeList from) {
        InputMethodInfo[] array;
        byte[] buf = from.mBuffer;
        from.mBuffer = null;
        if (buf != null && (array = unmarshall(buf)) != null) {
            return new ArrayList(Arrays.asList(array));
        }
        return new ArrayList();
    }

    private static InputMethodInfo[] toArray(List<InputMethodInfo> original) {
        if (original == null) {
            return new InputMethodInfo[0];
        }
        return (InputMethodInfo[]) original.toArray(new InputMethodInfo[0]);
    }

    private static byte[] marshall(InputMethodInfo[] array) {
        Parcel parcel = null;
        try {
            parcel = Parcel.obtain();
            parcel.writeTypedArray(array, 0);
            return parcel.marshall();
        } finally {
            if (parcel != null) {
                parcel.recycle();
            }
        }
    }

    private static InputMethodInfo[] unmarshall(byte[] data) {
        Parcel parcel = null;
        try {
            parcel = Parcel.obtain();
            parcel.unmarshall(data, 0, data.length);
            parcel.setDataPosition(0);
            return (InputMethodInfo[]) parcel.createTypedArray(InputMethodInfo.CREATOR);
        } finally {
            if (parcel != null) {
                parcel.recycle();
            }
        }
    }

    private InputMethodInfoSafeList(byte[] blob) {
        this.mBuffer = blob;
    }

    public static InputMethodInfoSafeList create(List<InputMethodInfo> list) {
        if (list == null || list.isEmpty()) {
            return empty();
        }
        return new InputMethodInfoSafeList(marshall(toArray(list)));
    }

    public static InputMethodInfoSafeList empty() {
        return new InputMethodInfoSafeList(null);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBlob(this.mBuffer);
    }
}
