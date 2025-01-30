package com.samsung.android.content.clipboard.data;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.p009os.Parcel;
import android.p009os.ParcelFileDescriptor;
import android.sec.clipboard.util.Log;
import java.net.URISyntaxException;

/* loaded from: classes5.dex */
public class SemIntentClipData extends SemClipData {
    private static final String TAG = "SemIntentClipData";
    private static final long serialVersionUID = 1;
    private String mValue;

    public SemIntentClipData() {
        super(8);
        this.mValue = "";
    }

    public SemIntentClipData(Parcel source) {
        super(source);
        this.mValue = "";
        readFromSource(source);
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
    public boolean setAlternateClipData(int type, SemClipData altData) {
        boolean result = super.setAlternateClipData(type, altData);
        if (!result || this.mValue.length() < 1) {
            return false;
        }
        switch (type) {
            case 8:
                try {
                    boolean result2 = ((SemIntentClipData) altData).setIntent(Intent.parseUri(this.mValue, 1));
                    break;
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    return false;
                }
        }
        return false;
    }

    private void setClipData() {
        Intent intent = null;
        try {
            intent = Intent.parseUri(this.mValue, 1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String[] mimeType = {ClipDescription.MIMETYPE_TEXT_INTENT};
        ClipData.Item item = new ClipData.Item(intent);
        setClipData(mimeType, item);
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
        Log.secI(TAG, "intent equals");
        if (!super.equals(o) || !(o instanceof SemIntentClipData)) {
            return false;
        }
        SemIntentClipData trgData = (SemIntentClipData) o;
        if (trgData.getIntent() == null) {
            return false;
        }
        boolean result = this.mValue.compareTo(trgData.getIntent().toUri(1)) == 0;
        return result;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    protected void readFromSource(Parcel source) {
        this.mValue = source.readString();
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public ParcelFileDescriptor getParcelFileDescriptor() {
        return null;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData, android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        Log.secI(TAG, "Intent write to parcel");
        dest.writeInt(8);
        super.writeToParcel(dest, flags);
        dest.writeString(this.mValue);
    }

    public String toString() {
        return "SemIntentClipData class. Value is " + ((Object) (this.mValue.length() > 20 ? this.mValue.subSequence(0, 20) : this.mValue));
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toSave() {
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toLoad() {
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void convertForRemote() {
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void insertContentUri(Context context, String path) {
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void deleteContentUri(Context context, String path) {
    }
}
