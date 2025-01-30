package com.samsung.android.content.clipboard.data;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.p009os.Binder;
import android.p009os.Parcel;
import android.p009os.ParcelFileDescriptor;
import android.sec.clipboard.data.ClipboardConstants;
import android.sec.clipboard.util.FileHelper;
import android.sec.clipboard.util.Log;
import android.text.TextUtils;
import com.samsung.android.content.clipboard.provider.SemImageClipDataProvider;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;

/* loaded from: classes5.dex */
public class SemUriClipData extends SemClipData {
  private static final String TAG = "SemUriClipData";
  private static final long serialVersionUID = 1;
  private String mThumbnailFilePath;
  private String mValue;

  public SemUriClipData() {
    super(16);
    this.mThumbnailFilePath = "";
    this.mValue = "";
  }

  public SemUriClipData(Parcel source) {
    super(source);
    this.mThumbnailFilePath = "";
    this.mValue = "";
    readFromSource(source);
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
  public boolean setAlternateClipData(int type, SemClipData altData) {
    if (!super.setAlternateClipData(type, altData) || this.mValue.length() < 1) {
      return false;
    }
    switch (type) {
      case 16:
        if (!(altData instanceof SemUriClipData)) {
          return false;
        }
        SemUriClipData data = (SemUriClipData) altData;
        boolean result = data.setUri(Uri.parse(this.mValue));
        if (this.mThumbnailFilePath.length() > 1) {
          return result & data.setThumbnailPath(this.mThumbnailFilePath);
        }
        return result;
      default:
        return false;
    }
  }

  private void setClipData() {
    String[] mimeType = {ClipDescription.MIMETYPE_TEXT_URILIST};
    ClipData.Item item = new ClipData.Item(Uri.parse(this.mValue));
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
    Log.secI(TAG, "uri equals");
    if (!super.equals(o) || !(o instanceof SemUriClipData)) {
      return false;
    }
    SemUriClipData trgData = (SemUriClipData) o;
    return this.mValue.compareTo(trgData.getUri().toString()) == 0;
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  public boolean setThumbnailPath(String filePath) {
    Log.secI(TAG, "setPreviewImgPath :" + filePath);
    if (TextUtils.isEmpty(filePath)) {
      return false;
    }
    File tempFile = new File(filePath);
    if (tempFile.isFile() && isImageFile(tempFile)) {
      this.mThumbnailFilePath = filePath;
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
      this.extensions = new String[] {"jpg", "png", "gif", "jpeg"};
    }

    @Override // java.io.FileFilter
    public boolean accept(File file) {
      if (file == null) {
        return false;
      }
      for (String extension : this.extensions) {
        if (file.getName().toLowerCase().endsWith(extension)) {
          return true;
        }
      }
      return false;
    }
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  protected void readFromSource(Parcel source) {
    this.mValue = source.readString();
    this.mThumbnailFilePath = source.readString();
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  public ParcelFileDescriptor getParcelFileDescriptor() {
    ParcelFileDescriptor fd = super.getParcelFileDescriptor();
    if (fd != null) {
      return fd;
    }
    if (TextUtils.isEmpty(this.mThumbnailFilePath)) {
      return null;
    }
    File file = new File(this.mThumbnailFilePath);
    try {
      return ParcelFileDescriptor.open(file, 268435456);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData, android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    Log.secI(TAG, "Uri write to parcel");
    dest.writeInt(16);
    super.writeToParcel(dest, flags);
    dest.writeString(this.mValue);
    dest.writeString(this.mThumbnailFilePath);
  }

  public String toString() {
    return "SemUriClipData class. Value is "
        + ((Object) (this.mValue.length() > 20 ? this.mValue.subSequence(0, 20) : this.mValue));
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  public void toSave() {}

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  public void toLoad() {}

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  public String createThumbnailFromData(Context context) {
    return FileHelper.getInstance().createThumnailFromUriData(context, this);
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  public void convertForRemote() {
    String str = this.mThumbnailFilePath;
    String imageName = str.substring(str.lastIndexOf("/"));
    if (setThumbnailPath(ClipboardConstants.CLIPBOARD_REMOTE_PATH + imageName)) {
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
        setUri(contentUri);
      } catch (Exception e) {
        Log.m60e(TAG, "Exception occurs because " + e.getMessage());
      }
    } finally {
      Binder.restoreCallingIdentity(identity);
    }
  }

  @Override // com.samsung.android.content.clipboard.data.SemClipData
  public void deleteContentUri(Context context, String path) {
    File remoteUriFile = new File(path, ClipboardConstants.CLIPBOARD_REMOTE_FILE);
    if (remoteUriFile.exists()) {
      deleteContentUriInternal(context, remoteUriFile.getAbsolutePath());
    }
  }
}
