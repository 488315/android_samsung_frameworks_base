package com.samsung.android.content.clipboard.data;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.p009os.Binder;
import android.p009os.Parcel;
import android.p009os.ParcelFileDescriptor;
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

/* loaded from: classes5.dex */
public class SemHtmlClipData extends SemClipData {
  private static final String REGEX =
      "(?i)<[^/bpd][^>]*>|<p[a-z][^>]*>|<br[a-z][^>]*>|<d[^i][^v][^>]*>|<div[a-z][^>]*>|</[^bpd]+?>|</p[a-z]+>|</br[a-z]+>|</d[^i][^v]+>|</div[a-z]+>";
  private static final String TAG = "SemHtmlClipData";
  private static final long serialVersionUID = 1;
  private String mHtml;
  private String mPlainText;
  private String mThumbnailImagePath;

  public SemHtmlClipData() {
    super(4);
    this.mHtml = "";
    this.mPlainText = "";
    this.mThumbnailImagePath = "";
  }

  public SemHtmlClipData(Parcel source) {
    super(source);
    this.mHtml = "";
    this.mPlainText = "";
    this.mThumbnailImagePath = "";
    readFromSource(source);
  }

  public boolean setHtml(CharSequence text) {
    return setHtmlInternal(this.mPlainText, text);
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
  public boolean setAlternateClipData(int type, SemClipData altData) {
    boolean result = super.setAlternateClipData(type, altData);
    if (!result || this.mHtml.length() < 1) {
      return false;
    }
    switch (type) {
      case 1:
        boolean result2 = ((SemTextClipData) altData).setText(this.mPlainText);
        return result2;
      case 4:
        ((SemHtmlClipData) altData)
            .setHtmlWithImagePathInternal(this.mPlainText, this.mHtml, this.mThumbnailImagePath);
        boolean result3 = this.mHtml.length() > 0;
        return result3;
      default:
        return false;
    }
  }

  private void setClipData() {
    String[] mimeType = {"text/html"};
    ClipData.Item item =
        new ClipData.Item(
            this.mPlainText, this.mHtml, null, Uri.fromFile(new File(this.mThumbnailImagePath)));
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

  public boolean setHtmlInternal(CharSequence text, CharSequence html) {
    if (TextUtils.isEmpty(html)) {
      return false;
    }
    if (html.length() > 131072) {
      html = html.subSequence(0, 131072);
    }
    this.mHtml = html.toString();
    if (TextUtils.isEmpty(text)) {
      String replaceAll = this.mHtml.replaceAll(REGEX, "");
      this.mPlainText = replaceAll;
      this.mPlainText = Html.fromHtml(replaceAll).toString();
    } else {
      this.mPlainText = text.toString();
    }
    Log.secD(TAG, "htmlclipdata setHtmlInternal called");
    return true;
  }

  public boolean setHtmlWithImagePathInternal(
      CharSequence text, CharSequence html, CharSequence filePath) {
    if (text != null && text.toString().length() > 0) {
      this.mPlainText = text.toString();
    }
    return setHtmlWithImagePath(html, filePath);
  }

  public boolean setHtmlWithImagePath(CharSequence text, CharSequence filePath) {
    if (!setHtmlInternal(this.mPlainText, text)) {
      return false;
    }
    if (filePath == null || filePath.length() < 1) {
      Log.secI(TAG, "filePath is null");
      return false;
    }
    this.mThumbnailImagePath = filePath.toString();
    new File(filePath.toString());
    ParcelFileDescriptor fd = super.getParcelFileDescriptor();
    if (fd != null && fd.getFileDescriptor().valid()) {
      Log.secI(TAG, "setHtmlWithImagePath : value is GOOD file path.");
      return true;
    }
    if (ClipboardConstants.DEBUG) {
      Log.m60e(TAG, "setHtmlWithImagePath : value is no file descriptor ..check plz");
    }
    return false;
  }

  public Bitmap getThumbnailBitmap(int reqWidth, int reqHeight) {
    if (this.mHtml.length() < 1) {
      Log.secW(TAG, "getThumbnailBitmap : Data is empty.");
      return null;
    }
    String sFileName = "";
    try {
      String sFileName2 = ClipboardProcText.getImgFileNameFromHtml(this.mHtml);
      sFileName = Uri.decode(sFileName2);
      sFileName = Html.fromHtml(sFileName).toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (sFileName != null && sFileName.length() < 1) {
      Log.secW(TAG, "getThumbnailBitmap : FileName is empty.");
      return null;
    }
    if (sFileName != null
        && sFileName.length() > 7
        && sFileName.substring(0, 7).compareTo("http://") == 0) {
      return null;
    }
    if (sFileName != null
        && sFileName.length() > 7
        && sFileName.substring(0, 7).compareTo("file://") == 0) {
      String substring = sFileName.substring(7, sFileName.length());
      Bitmap result = ClipboardDataBitmapUtil.getFilePathBitmap(substring, reqWidth, reqHeight);
      return result;
    }
    Bitmap result2 = ClipboardDataBitmapUtil.getFilePathBitmap(sFileName, reqWidth, reqHeight);
    return result2;
  }

  public boolean setThumbnailImagePath(String filePath) {
    if (TextUtils.isEmpty(filePath)) {
      return false;
    }
    this.mThumbnailImagePath = filePath;
    ParcelFileDescriptor fd = getParcelFileDescriptor();
    if (fd != null && fd.getFileDescriptor().valid()) {
      return true;
    }
    if (ClipboardConstants.DEBUG) {
      Log.m60e(TAG, "ClipboardDataHtml : value is no file descriptor ..check plz");
    }
    return false;
  }

  public String getThumbnailImagePath() {
    return this.mThumbnailImagePath;
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  public boolean equals(Object o) {
    Log.secI(TAG, "html equals");
    if (!super.equals(o) || !(o instanceof SemHtmlClipData)) {
      return false;
    }
    SemHtmlClipData trgData = (SemHtmlClipData) o;
    return this.mHtml.compareTo(trgData.getHtml()) == 0;
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  protected void readFromSource(Parcel source) {
    try {
      this.mHtml = source.readString();
      this.mPlainText = source.readString();
      this.mThumbnailImagePath = source.readString();
    } catch (Exception e) {
      Log.secI(TAG, "readFromSource~Exception :" + e.getMessage());
    }
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  public ParcelFileDescriptor getParcelFileDescriptor() {
    ParcelFileDescriptor fd = super.getParcelFileDescriptor();
    if (fd != null) {
      return fd;
    }
    if (TextUtils.isEmpty(this.mThumbnailImagePath)) {
      return null;
    }
    File file = new File(this.mThumbnailImagePath);
    try {
      return ParcelFileDescriptor.open(file, 268435456);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData, android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    Log.secI(TAG, "html write to parcel");
    dest.writeInt(4);
    super.writeToParcel(dest, flags);
    dest.writeString(this.mHtml);
    dest.writeString(this.mPlainText);
    dest.writeString(this.mThumbnailImagePath);
  }

  public String toString() {
    return "SemHtmlClipData class.";
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  public void toSave() {}

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  public void toLoad() {
    String str = this.mThumbnailImagePath;
    if (str != null && str.contains(CompatabilityHelper.OLD_CLIPBOARD_ROOT_PATH)) {
      this.mThumbnailImagePath =
          CompatabilityHelper.replacePathForCompatability(this.mThumbnailImagePath);
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
    android.util.Log.m94d(TAG, "SemHtmlClipData - getThumbnailPath");
    return getThumbnailImagePath();
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  public boolean setThumbnailPath(String filePath) {
    return setThumbnailImagePath(filePath);
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  public void convertForRemote() {
    String str = this.mThumbnailImagePath;
    if (str != null && str.contains(ClipboardConstants.CLIPBOARD_ROOT_PATH)) {
      this.mThumbnailImagePath = "/data/semclipboard/remote/previewhtmlclipboarditem_thum.jpg";
      setClipData();
      Log.m59d(TAG, "success converting");
    }
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  public void insertContentUri(Context context, String path) {
    long identity = Binder.clearCallingIdentity();
    try {
      try {
        ContentValues values = new ContentValues();
        values.put("_data", path);
        Uri contentUri =
            context.getContentResolver().insert(SemImageClipDataProvider.CONTENT_URI, values);
        int pos = path.lastIndexOf("/");
        String before = ClipboardConstants.CLIPBOARD_REMOTE_SEND_PATH + path.substring(pos);
        setHtml(this.mHtml.replace(before, contentUri.toString()));
      } catch (Exception e) {
        Log.m60e(TAG, "Exception occurs because " + e.getMessage());
      }
    } finally {
      Binder.restoreCallingIdentity(identity);
    }
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  public void deleteContentUri(Context context, String path) {
    File remoteUriFiles = new File(path);
    if (remoteUriFiles.exists()) {
      for (File remoteUriFile : remoteUriFiles.listFiles()) {
        if (remoteUriFile.getAbsolutePath().contains(ClipboardConstants.CLIPBOARD_REMOTE_FILE)) {
          deleteContentUriInternal(context, remoteUriFile.getAbsolutePath());
        }
      }
    }
  }
}
