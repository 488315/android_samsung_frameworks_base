package com.samsung.android.content.clipboard.data;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Binder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.sec.clipboard.data.ClipboardConstants;
import android.sec.clipboard.util.FileHelper;
import android.sec.clipboard.util.Log;
import android.text.TextUtils;
import com.samsung.android.content.clipboard.provider.SemImageClipDataProvider;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;

/* loaded from: classes6.dex */
public class SemUriClipData extends SemClipData {
    private static final String TAG = "SemUriClipData";
    private static final long serialVersionUID = 1;
    private String mThumbnailFilePath;
    private String mValue;

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toLoad() {
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toSave() {
    }

    public SemUriClipData() {
        super(16);
        this.mThumbnailFilePath = "";
        this.mValue = "";
    }

    public SemUriClipData(Parcel parcel) {
        super(parcel);
        this.mThumbnailFilePath = "";
        this.mValue = "";
        readFromSource(parcel);
    }

    public boolean setUri(Uri uri) {
        if (uri == null || uri.toString().length() == 0) {
            return false;
        }
        this.mValue = uri.toString();
        return true;
    }

    public Uri getUri() {
        return Uri.parse(this.mValue);
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean setAlternateClipData(int i, SemClipData semClipData) {
        if (!super.setAlternateClipData(i, semClipData) || this.mValue.length() < 1 || i != 16 || !(semClipData instanceof SemUriClipData)) {
            return false;
        }
        SemUriClipData semUriClipData = (SemUriClipData) semClipData;
        boolean uri = semUriClipData.setUri(Uri.parse(this.mValue));
        return this.mThumbnailFilePath.length() > 1 ? semUriClipData.setThumbnailPath(this.mThumbnailFilePath) & uri : uri;
    }

    private void setClipData() {
        setClipData(new String[]{ClipDescription.MIMETYPE_TEXT_URILIST}, new ClipData.Item(Uri.parse(this.mValue)));
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
        Log.secI(TAG, "uri equals");
        return super.equals(obj) && (obj instanceof SemUriClipData) && this.mValue.compareTo(((SemUriClipData) obj).getUri().toString()) == 0;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean setThumbnailPath(String str) {
        Log.secI(TAG, "setPreviewImgPath :" + str);
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (file.isFile() && isImageFile(file)) {
            this.mThumbnailFilePath = str;
            return true;
        }
        this.mThumbnailFilePath = "";
        Log.secE(TAG, "SemUriClipData : value is no file path or not image file");
        return false;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public String getThumbnailPath() {
        return this.mThumbnailFilePath;
    }

    public boolean isImageFile(File file) {
        if (file != null) {
            return new ImageFileFilter().accept(file);
        }
        return false;
    }

    private static class ImageFileFilter implements FileFilter {
        private final String[] extensions;

        private ImageFileFilter() {
            this.extensions = new String[]{"jpg", "png", "gif", "jpeg"};
        }

        @Override // java.io.FileFilter
        public boolean accept(File file) {
            if (file == null) {
                return false;
            }
            for (String str : this.extensions) {
                if (file.getName().toLowerCase().endsWith(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    protected void readFromSource(Parcel parcel) {
        this.mValue = parcel.readString();
        this.mThumbnailFilePath = parcel.readString();
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public ParcelFileDescriptor getParcelFileDescriptor() {
        ParcelFileDescriptor parcelFileDescriptor = super.getParcelFileDescriptor();
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor;
        }
        if (TextUtils.isEmpty(this.mThumbnailFilePath)) {
            return null;
        }
        try {
            return ParcelFileDescriptor.open(new File(this.mThumbnailFilePath), 268435456);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Log.secI(TAG, "Uri write to parcel");
        parcel.writeInt(16);
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mValue);
        parcel.writeString(this.mThumbnailFilePath);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SemUriClipData class. Value is ");
        int length = this.mValue.length();
        String str = this.mValue;
        CharSequence charSequence = str;
        if (length > 20) {
            charSequence = str.subSequence(0, 20);
        }
        sb.append((Object) charSequence);
        return sb.toString();
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public String createThumbnailFromData(Context context) {
        return FileHelper.getInstance().createThumnailFromUriData(context, this);
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void convertForRemote() {
        String str = this.mThumbnailFilePath;
        if (setThumbnailPath(ClipboardConstants.CLIPBOARD_REMOTE_PATH + str.substring(str.lastIndexOf("/")))) {
            Log.d(TAG, "success converting");
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void insertContentUri(Context context, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_data", str);
            setUri(context.getContentResolver().insert(SemImageClipDataProvider.CONTENT_URI, contentValues));
        } catch (Exception e) {
            Log.e(TAG, "Exception occurs because " + e.getMessage());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void deleteContentUri(Context context, String str) {
        File file = new File(str, ClipboardConstants.CLIPBOARD_REMOTE_FILE);
        if (file.exists()) {
            deleteContentUriInternal(context, file.getAbsolutePath());
        }
    }
}
