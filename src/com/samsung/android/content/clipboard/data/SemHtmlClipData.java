package com.samsung.android.content.clipboard.data;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.sec.clipboard.data.ClipboardConstants;
import android.sec.clipboard.util.ClipboardDataBitmapUtil;
import android.sec.clipboard.util.ClipboardProcText;
import android.sec.clipboard.util.CompatabilityHelper;
import android.sec.clipboard.util.FileHelper;
import android.sec.clipboard.util.Log;
import android.text.Html;
import android.text.TextUtils;
import com.samsung.android.content.clipboard.provider.SemImageClipDataProvider;
import java.io.File;
import java.io.FileNotFoundException;

/* loaded from: classes6.dex */
public class SemHtmlClipData extends SemClipData {
    private static final String REGEX = "(?i)<[^/bpd][^>]*>|<p[a-z][^>]*>|<br[a-z][^>]*>|<d[^i][^v][^>]*>|<div[a-z][^>]*>|</[^bpd]+?>|</p[a-z]+>|</br[a-z]+>|</d[^i][^v]+>|</div[a-z]+>";
    private static final String TAG = "SemHtmlClipData";
    private static final long serialVersionUID = 1;
    private String mHtml;
    private String mPlainText;
    private String mThumbnailImagePath;

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toSave() {
    }

    public SemHtmlClipData() {
        super(4);
        this.mHtml = "";
        this.mPlainText = "";
        this.mThumbnailImagePath = "";
    }

    public SemHtmlClipData(Parcel parcel) {
        super(parcel);
        this.mHtml = "";
        this.mPlainText = "";
        this.mThumbnailImagePath = "";
        readFromSource(parcel);
    }

    public boolean setHtml(CharSequence charSequence) {
        return setHtmlInternal(this.mPlainText, charSequence);
    }

    public String getHtml() {
        return this.mHtml;
    }

    public String getPlainText() {
        return this.mPlainText;
    }

    public ParcelFileDescriptor getThumbnailFileDescriptor() {
        return getParcelFileDescriptor();
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean setAlternateClipData(int i, SemClipData semClipData) {
        if (!super.setAlternateClipData(i, semClipData) || this.mHtml.length() < 1) {
            return false;
        }
        if (i == 1) {
            return ((SemTextClipData) semClipData).setText(this.mPlainText);
        }
        if (i != 4) {
            return false;
        }
        ((SemHtmlClipData) semClipData).setHtmlWithImagePathInternal(this.mPlainText, this.mHtml, this.mThumbnailImagePath);
        return this.mHtml.length() > 0;
    }

    private void setClipData() {
        setClipData(new String[]{"text/html"}, new ClipData.Item(this.mPlainText, this.mHtml, null, Uri.fromFile(new File(this.mThumbnailImagePath))));
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

    public boolean setHtmlInternal(CharSequence charSequence, CharSequence charSequence2) {
        if (TextUtils.isEmpty(charSequence2)) {
            return false;
        }
        if (charSequence2.length() > 131072) {
            charSequence2 = charSequence2.subSequence(0, 131072);
        }
        this.mHtml = charSequence2.toString();
        if (TextUtils.isEmpty(charSequence)) {
            String replaceAll = this.mHtml.replaceAll(REGEX, "");
            this.mPlainText = replaceAll;
            this.mPlainText = Html.fromHtml(replaceAll).toString();
        } else {
            this.mPlainText = charSequence.toString();
        }
        Log.secD(TAG, "htmlclipdata setHtmlInternal called");
        return true;
    }

    public boolean setHtmlWithImagePathInternal(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        if (charSequence != null && charSequence.toString().length() > 0) {
            this.mPlainText = charSequence.toString();
        }
        return setHtmlWithImagePath(charSequence2, charSequence3);
    }

    public boolean setHtmlWithImagePath(CharSequence charSequence, CharSequence charSequence2) {
        if (!setHtmlInternal(this.mPlainText, charSequence)) {
            return false;
        }
        if (charSequence2 == null || charSequence2.length() < 1) {
            Log.secI(TAG, "filePath is null");
            return false;
        }
        this.mThumbnailImagePath = charSequence2.toString();
        new File(charSequence2.toString());
        ParcelFileDescriptor parcelFileDescriptor = super.getParcelFileDescriptor();
        if (parcelFileDescriptor != null && parcelFileDescriptor.getFileDescriptor().valid()) {
            Log.secI(TAG, "setHtmlWithImagePath : value is GOOD file path.");
            return true;
        }
        if (ClipboardConstants.DEBUG) {
            Log.e(TAG, "setHtmlWithImagePath : value is no file descriptor ..check plz");
        }
        return false;
    }

    public Bitmap getThumbnailBitmap(int i, int i2) {
        String str;
        if (this.mHtml.length() < 1) {
            Log.secW(TAG, "getThumbnailBitmap : Data is empty.");
            return null;
        }
        String str2 = "";
        try {
            str2 = Uri.decode(ClipboardProcText.getImgFileNameFromHtml(this.mHtml));
            str = Html.fromHtml(str2).toString();
        } catch (Exception e) {
            e.printStackTrace();
            str = str2;
        }
        if (str != null && str.length() < 1) {
            Log.secW(TAG, "getThumbnailBitmap : FileName is empty.");
            return null;
        }
        if (str != null && str.length() > 7 && str.substring(0, 7).compareTo("http://") == 0) {
            return null;
        }
        if (str != null && str.length() > 7 && str.substring(0, 7).compareTo("file://") == 0) {
            return ClipboardDataBitmapUtil.getFilePathBitmap(str.substring(7, str.length()), i, i2);
        }
        return ClipboardDataBitmapUtil.getFilePathBitmap(str, i, i2);
    }

    public boolean setThumbnailImagePath(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        this.mThumbnailImagePath = str;
        ParcelFileDescriptor parcelFileDescriptor = getParcelFileDescriptor();
        if (parcelFileDescriptor != null && parcelFileDescriptor.getFileDescriptor().valid()) {
            return true;
        }
        if (ClipboardConstants.DEBUG) {
            Log.e(TAG, "ClipboardDataHtml : value is no file descriptor ..check plz");
        }
        return false;
    }

    public String getThumbnailImagePath() {
        return this.mThumbnailImagePath;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean equals(Object obj) {
        Log.secI(TAG, "html equals");
        return super.equals(obj) && (obj instanceof SemHtmlClipData) && this.mHtml.compareTo(((SemHtmlClipData) obj).getHtml()) == 0;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    protected void readFromSource(Parcel parcel) {
        try {
            this.mHtml = parcel.readString();
            this.mPlainText = parcel.readString();
            this.mThumbnailImagePath = parcel.readString();
        } catch (Exception e) {
            Log.secI(TAG, "readFromSource~Exception :" + e.getMessage());
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public ParcelFileDescriptor getParcelFileDescriptor() {
        ParcelFileDescriptor parcelFileDescriptor = super.getParcelFileDescriptor();
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor;
        }
        if (TextUtils.isEmpty(this.mThumbnailImagePath)) {
            return null;
        }
        try {
            return ParcelFileDescriptor.open(new File(this.mThumbnailImagePath), 268435456);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Log.secI(TAG, "html write to parcel");
        parcel.writeInt(4);
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mHtml);
        parcel.writeString(this.mPlainText);
        parcel.writeString(this.mThumbnailImagePath);
    }

    public String toString() {
        return "SemHtmlClipData class.";
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toLoad() {
        String str = this.mThumbnailImagePath;
        if (str != null && str.contains(CompatabilityHelper.OLD_CLIPBOARD_ROOT_PATH)) {
            this.mThumbnailImagePath = CompatabilityHelper.replacePathForCompatability(this.mThumbnailImagePath);
            setClipData();
        }
        Log.secD(TAG, "htmlclipdata toLoad called");
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public String createThumbnailFromData(Context context) {
        return FileHelper.getInstance().createThumnailFromData(context, this);
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public String getThumbnailPath() {
        android.util.Log.d(TAG, "SemHtmlClipData - getThumbnailPath");
        return getThumbnailImagePath();
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean setThumbnailPath(String str) {
        return setThumbnailImagePath(str);
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void convertForRemote() {
        String str = this.mThumbnailImagePath;
        if (str == null || !str.contains(ClipboardConstants.CLIPBOARD_ROOT_PATH)) {
            return;
        }
        this.mThumbnailImagePath = "/data/semclipboard/remote/previewhtmlclipboarditem_thum.jpg";
        setClipData();
        Log.d(TAG, "success converting");
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void insertContentUri(Context context, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_data", str);
            Uri insert = context.getContentResolver().insert(SemImageClipDataProvider.CONTENT_URI, contentValues);
            setHtml(this.mHtml.replace(ClipboardConstants.CLIPBOARD_REMOTE_SEND_PATH + str.substring(str.lastIndexOf("/")), insert.toString()));
        } catch (Exception e) {
            Log.e(TAG, "Exception occurs because " + e.getMessage());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void deleteContentUri(Context context, String str) {
        File file = new File(str);
        if (file.exists()) {
            for (File file2 : file.listFiles()) {
                if (file2.getAbsolutePath().contains(ClipboardConstants.CLIPBOARD_REMOTE_FILE)) {
                    deleteContentUriInternal(context, file2.getAbsolutePath());
                }
            }
        }
    }
}
