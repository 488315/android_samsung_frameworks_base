package com.android.internal.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.inputmethod.InputMethodInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes5.dex */
public final class InputMethodInfoSafeList implements Parcelable {
    public static final Parcelable.Creator<InputMethodInfoSafeList> CREATOR = new Parcelable.Creator<InputMethodInfoSafeList>() { // from class: com.android.internal.inputmethod.InputMethodInfoSafeList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputMethodInfoSafeList createFromParcel(Parcel parcel) {
            return new InputMethodInfoSafeList(parcel.readBlob());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputMethodInfoSafeList[] newArray(int i) {
            return new InputMethodInfoSafeList[i];
        }
    };
    private byte[] mBuffer;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static List<InputMethodInfo> extractFrom(InputMethodInfoSafeList inputMethodInfoSafeList) {
        InputMethodInfo[] unmarshall;
        byte[] bArr = inputMethodInfoSafeList.mBuffer;
        inputMethodInfoSafeList.mBuffer = null;
        if (bArr != null && (unmarshall = unmarshall(bArr)) != null) {
            return new ArrayList(Arrays.asList(unmarshall));
        }
        return new ArrayList();
    }

    private static InputMethodInfo[] toArray(List<InputMethodInfo> list) {
        if (list == null) {
            return new InputMethodInfo[0];
        }
        return (InputMethodInfo[]) list.toArray(new InputMethodInfo[0]);
    }

    private static byte[] marshall(InputMethodInfo[] inputMethodInfoArr) {
        Parcel parcel;
        try {
            parcel = Parcel.obtain();
        } catch (Throwable th) {
            th = th;
            parcel = null;
        }
        try {
            parcel.writeTypedArray(inputMethodInfoArr, 0);
            byte[] marshall = parcel.marshall();
            if (parcel != null) {
                parcel.recycle();
            }
            return marshall;
        } catch (Throwable th2) {
            th = th2;
            if (parcel != null) {
                parcel.recycle();
            }
            throw th;
        }
    }

    private static InputMethodInfo[] unmarshall(byte[] bArr) {
        Parcel parcel;
        try {
            parcel = Parcel.obtain();
        } catch (Throwable th) {
            th = th;
            parcel = null;
        }
        try {
            parcel.unmarshall(bArr, 0, bArr.length);
            parcel.setDataPosition(0);
            InputMethodInfo[] inputMethodInfoArr = (InputMethodInfo[]) parcel.createTypedArray(InputMethodInfo.CREATOR);
            if (parcel != null) {
                parcel.recycle();
            }
            return inputMethodInfoArr;
        } catch (Throwable th2) {
            th = th2;
            if (parcel != null) {
                parcel.recycle();
            }
            throw th;
        }
    }

    private InputMethodInfoSafeList(byte[] bArr) {
        this.mBuffer = bArr;
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBlob(this.mBuffer);
    }
}
