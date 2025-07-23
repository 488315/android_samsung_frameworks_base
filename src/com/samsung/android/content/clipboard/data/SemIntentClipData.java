package com.samsung.android.content.clipboard.data;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.sec.clipboard.util.Log;
import java.net.URISyntaxException;

/* loaded from: classes6.dex */
public class SemIntentClipData extends SemClipData {
    private static final String TAG = "SemIntentClipData";
    private static final long serialVersionUID = 1;
    private String mValue;

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

    public SemIntentClipData() {
        super(8);
        this.mValue = "";
    }

    public SemIntentClipData(Parcel parcel) {
        super(parcel);
        this.mValue = "";
        readFromSource(parcel);
    }

    public boolean setIntent(Intent intent) {
        if (intent == null || intent.toUri(1).length() == 0) {
            return false;
        }
        this.mValue = intent.toUri(1);
        return true;
    }

    public Intent getIntent() {
        try {
            return Intent.parseUri(this.mValue, 1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean setAlternateClipData(int i, SemClipData semClipData) {
        if (!super.setAlternateClipData(i, semClipData) || this.mValue.length() < 1 || i != 8) {
            return false;
        }
        try {
            return ((SemIntentClipData) semClipData).setIntent(Intent.parseUri(this.mValue, 1));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setClipData() {
        Intent intent;
        try {
            intent = Intent.parseUri(this.mValue, 1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            intent = null;
        }
        setClipData(new String[]{ClipDescription.MIMETYPE_TEXT_INTENT}, new ClipData.Item(intent));
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
        Log.secI(TAG, "intent equals");
        if (!super.equals(obj) || !(obj instanceof SemIntentClipData)) {
            return false;
        }
        SemIntentClipData semIntentClipData = (SemIntentClipData) obj;
        return semIntentClipData.getIntent() != null && this.mValue.compareTo(semIntentClipData.getIntent().toUri(1)) == 0;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    protected void readFromSource(Parcel parcel) {
        this.mValue = parcel.readString();
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Log.secI(TAG, "Intent write to parcel");
        parcel.writeInt(8);
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mValue);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SemIntentClipData class. Value is ");
        int length = this.mValue.length();
        String str = this.mValue;
        CharSequence charSequence = str;
        if (length > 20) {
            charSequence = str.subSequence(0, 20);
        }
        sb.append((Object) charSequence);
        return sb.toString();
    }
}
