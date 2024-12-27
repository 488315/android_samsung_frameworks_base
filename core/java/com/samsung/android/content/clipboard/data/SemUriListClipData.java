package com.samsung.android.content.clipboard.data;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.sec.clipboard.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class SemUriListClipData extends SemClipData {
    private static final String TAG = "SemUriListClipData";
    private static final long serialVersionUID = 1;
    private ArrayList<String> mUriArray;

    public SemUriListClipData() {
        super(32);
    }

    public SemUriListClipData(Parcel source) {
        super(source);
        readFromSource(source);
    }

    public boolean setUriList(ArrayList<Uri> uriList) {
        if (uriList == null) {
            return false;
        }
        this.mUriArray = new ArrayList<>();
        Iterator<Uri> it = uriList.iterator();
        while (it.hasNext()) {
            Uri uri = it.next();
            this.mUriArray.add(uri.toString());
        }
        return true;
    }

    public ArrayList<Uri> getUriList() {
        if (this.mUriArray == null) {
            return null;
        }
        ArrayList<Uri> multiUri = new ArrayList<>();
        Iterator<String> it = this.mUriArray.iterator();
        while (it.hasNext()) {
            String uri = it.next();
            multiUri.add(Uri.parse(uri));
        }
        return multiUri;
    }

    public boolean setUriListInternal(ArrayList<String> uriList) {
        if (uriList == null) {
            return false;
        }
        this.mUriArray = new ArrayList<>();
        Iterator<String> it = uriList.iterator();
        while (it.hasNext()) {
            String str = it.next();
            this.mUriArray.add(str);
        }
        return true;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean setAlternateClipData(int format, SemClipData altData) {
        boolean result = super.setAlternateClipData(format, altData);
        if (!result || this.mUriArray.size() < 1) {
            return false;
        }
        switch (format) {
            case 32:
                boolean result2 = ((SemUriListClipData) altData).setUriListInternal(this.mUriArray);
                return result2;
            default:
                return false;
        }
    }

    private void setClipData() {
        String[] mimeType = {ClipDescription.MIMETYPE_TEXT_URILIST};
        ClipData.Item item = new ClipData.Item(Uri.parse(this.mUriArray.get(0)));
        setClipData(mimeType, item);
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
    public boolean equals(Object o) {
        Log.secI(TAG, "multiple uri equals");
        if (!super.equals(o) || !(o instanceof SemUriListClipData)) {
            return false;
        }
        SemUriListClipData trgData = (SemUriListClipData) o;
        if (trgData.getUriList() == null) {
            return getUriList() == null;
        }
        boolean result = this.mUriArray.toString().compareTo(trgData.getUriList().toString()) == 0;
        return result;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    protected void readFromSource(Parcel source) {
        this.mUriArray = new ArrayList<>();
        source.readStringList(this.mUriArray);
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public ParcelFileDescriptor getParcelFileDescriptor() {
        return null;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        Log.secI(TAG, "Multiple Uri write to parcel");
        dest.writeInt(32);
        super.writeToParcel(dest, flags);
        dest.writeStringList(this.mUriArray);
    }

    public String toString() {
        return "SemUriListClipData class.";
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toSave() {}

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toLoad() {}

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void convertForRemote() {}

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void insertContentUri(Context context, String path) {}

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void deleteContentUri(Context context, String path) {}
}
