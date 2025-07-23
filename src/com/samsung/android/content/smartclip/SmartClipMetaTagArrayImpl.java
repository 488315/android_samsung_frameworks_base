package com.samsung.android.content.smartclip;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class SmartClipMetaTagArrayImpl extends SemSmartClipMetaTagArray implements Parcelable {
    public static final Parcelable.Creator<SmartClipMetaTagArrayImpl> CREATOR = new Parcelable.Creator<SmartClipMetaTagArrayImpl>() { // from class: com.samsung.android.content.smartclip.SmartClipMetaTagArrayImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartClipMetaTagArrayImpl createFromParcel(Parcel parcel) {
            Log.d(SmartClipMetaTagArrayImpl.TAG, "SmartClipMetaTagArrayImpl.createFromParcel called");
            SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = new SmartClipMetaTagArrayImpl();
            smartClipMetaTagArrayImpl.readFromParcel(parcel);
            return smartClipMetaTagArrayImpl;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartClipMetaTagArrayImpl[] newArray(int i) {
            return new SmartClipMetaTagArrayImpl[i];
        }
    };
    private static final String TAG = "SmartClipMetaTagArrayImpl";

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipMetaTagArray
    public int removeMetaTags(String str) {
        int i = 0;
        for (int size = size() - 1; size >= 0; size--) {
            String type = ((SemSmartClipMetaTag) get(size)).getType();
            if (type != null && type.equals(str)) {
                remove(size);
                i++;
            }
        }
        return i;
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipMetaTagArray
    public SemSmartClipMetaTagArray getMetaTags(String str) {
        SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = new SmartClipMetaTagArrayImpl();
        int size = size();
        for (int i = 0; i < size; i++) {
            SemSmartClipMetaTag semSmartClipMetaTag = (SemSmartClipMetaTag) get(i);
            String type = semSmartClipMetaTag.getType();
            if (type != null && type.equals(str)) {
                smartClipMetaTagArrayImpl.add(semSmartClipMetaTag);
            }
        }
        return smartClipMetaTagArrayImpl;
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipMetaTagArray
    public boolean addMetaTag(SemSmartClipMetaTag semSmartClipMetaTag) {
        if (semSmartClipMetaTag == null) {
            return false;
        }
        return add(semSmartClipMetaTag);
    }

    public int removeTags(String str) {
        return removeMetaTags(str);
    }

    public SemSmartClipMetaTagArray getTags(String str) {
        return getMetaTags(str);
    }

    public boolean addTag(SemSmartClipMetaTag semSmartClipMetaTag) {
        return addMetaTag(semSmartClipMetaTag);
    }

    public void addTag(SemSmartClipMetaTagArray semSmartClipMetaTagArray) {
        if (semSmartClipMetaTagArray == null) {
            return;
        }
        Iterator<SemSmartClipMetaTag> it = semSmartClipMetaTagArray.iterator();
        while (it.hasNext()) {
            add(it.next());
        }
    }

    public SmartClipMetaTagArrayImpl getCopy() {
        SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = new SmartClipMetaTagArrayImpl();
        int size = size();
        for (int i = 0; i < size; i++) {
            smartClipMetaTagArrayImpl.add((SemSmartClipMetaTag) get(i));
        }
        return smartClipMetaTagArrayImpl;
    }

    public void dump() {
        int size = size();
        for (int i = 0; i < size; i++) {
            SemSmartClipMetaTag semSmartClipMetaTag = (SemSmartClipMetaTag) get(i);
            String type = semSmartClipMetaTag.getType();
            String value = semSmartClipMetaTag.getValue();
            if (value == null) {
                value = PerfettoProtoLogImpl.NULL_STRING;
            }
            Log.d(TAG, type + NavigationBarInflaterView.KEY_CODE_START + value + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int size = size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            SemSmartClipMetaTag semSmartClipMetaTag = (SemSmartClipMetaTag) get(i2);
            parcel.writeString("BasicMetaTag");
            parcel.writeString(semSmartClipMetaTag.getType());
            parcel.writeString(semSmartClipMetaTag.getValue());
        }
    }

    public void readFromParcel(Parcel parcel) {
        SemSmartClipMetaTag semSmartClipMetaTag;
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            String readString = parcel.readString();
            if (readString.equals("BasicMetaTag")) {
                semSmartClipMetaTag = new SemSmartClipMetaTag(parcel.readString(), parcel.readString());
            } else {
                semSmartClipMetaTag = readString.equals("ParcelableMetaTag") ? (SemSmartClipMetaTag) parcel.readParcelable(null) : null;
            }
            if (semSmartClipMetaTag == null) {
                Log.e(TAG, "readFromParcel : Could not read tag!!");
                return;
            }
            add(semSmartClipMetaTag);
        }
    }
}
