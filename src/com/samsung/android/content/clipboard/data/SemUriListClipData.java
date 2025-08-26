package com.samsung.android.content.clipboard.data;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.sec.clipboard.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class SemUriListClipData extends SemClipData {
    private static final String TAG = "SemUriListClipData";
    private static final long serialVersionUID = 1;
    private ArrayList<String> mUriArray;

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void convertForRemote() {
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void deleteContentUri(Context context, String str) {
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public ParcelFileDescriptor getParcelFileDescriptor() {
        return null;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void insertContentUri(Context context, String str) {
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toLoad() {
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toSave() {
    }

    public SemUriListClipData() {
        super(32);
    }

    public SemUriListClipData(Parcel parcel) {
        super(parcel);
        readFromSource(parcel);
    }

    public boolean setUriList(ArrayList<Uri> arrayList) {
        if (arrayList == null) {
            return false;
        }
        this.mUriArray = new ArrayList<>();
        Iterator<Uri> it = arrayList.iterator();
        while (it.hasNext()) {
            this.mUriArray.add(it.next().toString());
        }
        return true;
    }

    public ArrayList<Uri> getUriList() {
        if (this.mUriArray == null) {
            return null;
        }
        ArrayList<Uri> arrayList = new ArrayList<>();
        Iterator<String> it = this.mUriArray.iterator();
        while (it.hasNext()) {
            arrayList.add(Uri.parse(it.next()));
        }
        return arrayList;
    }

    public boolean setUriListInternal(ArrayList<String> arrayList) {
        if (arrayList == null) {
            return false;
        }
        this.mUriArray = new ArrayList<>();
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            this.mUriArray.add(it.next());
        }
        return true;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean setAlternateClipData(int i, SemClipData semClipData) {
        if (super.setAlternateClipData(i, semClipData) && this.mUriArray.size() >= 1 && i == 32) {
            return ((SemUriListClipData) semClipData).setUriListInternal(this.mUriArray);
        }
        return false;
    }

    private void setClipData() {
        setClipData(new String[]{ClipDescription.MIMETYPE_TEXT_URILIST}, new ClipData.Item(Uri.parse(this.mUriArray.get(0))));
        for (int i = 1; i < this.mUriArray.size(); i++) {
            getClipDataInternal().addItem(new ClipData.Item(Uri.parse(this.mUriArray.get(i))));
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public ClipData getClipData() {
        if (this.mClipData == null) {
            setClipData();
        }
        return this.mClipData;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    protected ClipData getClipDataInternal() {
        if (this.mClipData == null) {
            setClipData();
        }
        return this.mClipData;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean equals(Object obj) {
        Log.secI(TAG, "multiple uri equals");
        if (!super.equals(obj) || !(obj instanceof SemUriListClipData)) {
            return false;
        }
        SemUriListClipData semUriListClipData = (SemUriListClipData) obj;
        return semUriListClipData.getUriList() != null ? this.mUriArray.toString().compareTo(semUriListClipData.getUriList().toString()) == 0 : getUriList() == null;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    protected void readFromSource(Parcel parcel) {
        ArrayList<String> arrayList = new ArrayList<>();
        this.mUriArray = arrayList;
        parcel.readStringList(arrayList);
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        Log.secI(TAG, "Multiple Uri write to parcel");
        parcel.writeInt(32);
        super.writeToParcel(parcel, i);
        parcel.writeStringList(this.mUriArray);
    }

    public String toString() {
        return "SemUriListClipData class.";
    }
}
