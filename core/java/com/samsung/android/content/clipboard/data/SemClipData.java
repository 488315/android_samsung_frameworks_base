package com.samsung.android.content.clipboard.data;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Binder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.sec.clipboard.data.ClipboardConstants;
import android.sec.clipboard.data.ClipboardDataFactory;
import android.sec.clipboard.util.Log;
import android.text.TextUtils;
import com.samsung.android.content.clipboard.provider.SemImageClipDataProvider;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;

/* loaded from: classes5.dex */
public abstract class SemClipData implements Parcelable, Serializable {
  public static final Parcelable.Creator<SemClipData> CREATOR =
      new Parcelable.Creator<
          SemClipData>() { // from class: com.samsung.android.content.clipboard.data.SemClipData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemClipData createFromParcel(Parcel source) {
          SemClipData concreteData = ClipboardDataFactory.createClipBoardData(source);
          return concreteData;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemClipData[] newArray(int size) {
          return new SemClipData[size];
        }
      };
  private static final String TAG = "SemClipData";
  private static final long serialVersionUID = 1;
  private transient HashSet<String> activePermissionOwners;
  private transient PersistableBundle mBundle;
  private long mCallerUid;
  protected transient ClipData mClipData;
  private String mClipId;
  private boolean mIsPCClip;
  private boolean mIsProtected;
  private boolean mIsRemoteClip;
  private ArrayList<Object> mKeyList;
  private CharSequence mLabel;
  private ArrayList<String> mMimeTypes;
  private ArrayList<Object> mObjList;
  private transient ParcelFileDescriptor mParcelFd;
  private String mRemoteClipId;
  private int mRemoteState;
  private long mTimestamp;
  private int mType;

  public abstract void convertForRemote();

  public abstract void deleteContentUri(Context context, String str);

  protected abstract ClipData getClipDataInternal();

  public abstract void insertContentUri(Context context, String str);

  protected abstract void readFromSource(Parcel parcel);

  public abstract void toLoad();

  public abstract void toSave();

  public SemClipData(int type) {
    this.mTimestamp = 0L;
    this.mIsProtected = false;
    this.mClipData = null;
    this.mLabel = "";
    this.mBundle = null;
    this.mIsPCClip = false;
    this.mIsRemoteClip = false;
    this.mRemoteState = 0;
    this.activePermissionOwners = new HashSet<>();
    this.mType = type;
    this.mCallerUid = Binder.getCallingUid();
    this.mTimestamp = System.currentTimeMillis();
    this.mParcelFd = null;
    this.mClipId = createUniqueId();
  }

  public SemClipData(Parcel source) {
    this.mTimestamp = 0L;
    this.mIsProtected = false;
    this.mClipData = null;
    this.mLabel = "";
    this.mBundle = null;
    this.mIsPCClip = false;
    this.mIsRemoteClip = false;
    this.mRemoteState = 0;
    this.activePermissionOwners = new HashSet<>();
    this.mType = source.readInt();
    this.mTimestamp = source.readLong();
    this.mIsProtected = ((Boolean) source.readValue(Boolean.class.getClassLoader())).booleanValue();
    this.mCallerUid = source.readLong();
    this.mClipData = (ClipData) source.readParcelable(ClipData.class.getClassLoader());
    this.mParcelFd =
        (ParcelFileDescriptor) source.readParcelable(ParcelFileDescriptor.class.getClassLoader());
    this.mClipId = source.readString();
    this.mMimeTypes = source.createStringArrayList();
    this.mLabel = source.readCharSequence();
    this.mKeyList = source.readArrayList(Object.class.getClassLoader());
    this.mObjList = source.readArrayList(Object.class.getClassLoader());
    this.mBundle = source.readPersistableBundle();
    this.mIsPCClip = ((Boolean) source.readValue(Boolean.class.getClassLoader())).booleanValue();
    this.mIsRemoteClip =
        ((Boolean) source.readValue(Boolean.class.getClassLoader())).booleanValue();
    this.mRemoteClipId = source.readString();
    this.mRemoteState = source.readInt();
  }

  private String createUniqueId() {
    int id = hashCode();
    StringBuffer sb = new StringBuffer();
    Random rand = new Random();
    Calendar oCalendar = Calendar.getInstance();
    sb.append(id);
    sb.append(oCalendar.get(12));
    sb.append(oCalendar.get(13));
    sb.append(oCalendar.get(14));
    sb.append(rand.nextInt(oCalendar.get(14) + 1));
    return sb.toString();
  }

  public void checkClipId() {
    if (this.mClipId == null) {
      this.mClipId = createUniqueId();
    }
  }

  public long getTimestamp() {
    return this.mTimestamp;
  }

  public void setTimestamp(long timestamp) {
    this.mTimestamp = timestamp;
  }

  public ParcelFileDescriptor getParcelFileDescriptor() {
    return this.mParcelFd;
  }

  public void setParcelFileDescriptor(ParcelFileDescriptor fd) {
    this.mParcelFd = fd;
  }

  public void closeParcelFileDescriptor() {
    ParcelFileDescriptor parcelFileDescriptor = this.mParcelFd;
    if (parcelFileDescriptor != null) {
      try {
        parcelFileDescriptor.close();
      } catch (IOException e) {
        if (ClipboardConstants.DEBUG) {
          e.printStackTrace();
        } else {
          Log.secD(TAG, "IOException!");
        }
      }
      this.mParcelFd = null;
    }
  }

  public int getClipType() {
    return this.mType;
  }

  public long getCallerUid() {
    return this.mCallerUid;
  }

  public void setCallerUid(long callerUid) {
    this.mCallerUid = callerUid;
  }

  public void setClipId(String id) {
    this.mClipId = id;
  }

  public String getClipId() {
    return this.mClipId;
  }

  public void setProtected(boolean isProtected) {
    this.mIsProtected = isProtected;
    if (this.mIsPCClip && isProtected) {
      setPCClip(false);
      setPCClipExtra(false);
    }
    if (this.mIsRemoteClip && isProtected) {
      setRemoteClip(false);
    }
  }

  @Deprecated
  public void setProtectState(boolean isProtected) {
    this.mIsProtected = isProtected;
  }

  public boolean isProtected() {
    return this.mIsProtected;
  }

  public ClipData getClipData() {
    return getClipDataInternal();
  }

  public void setClipData(ClipData data) {
    this.mClipData = data;
  }

  public void setClipData(String[] mimeType, ClipData.Item item) {
    ClipData data;
    CharSequence label = getLabel();
    if (!TextUtils.isEmpty(label)) {
      if (this.mMimeTypes == null) {
        data = new ClipData(label, mimeType, item);
      } else {
        ArrayList<String> arrayList = this.mMimeTypes;
        data =
            new ClipData(label, (String[]) arrayList.toArray(new String[arrayList.size()]), item);
      }
    } else {
      data = new ClipData(ClipboardConstants.CLIPBOARD_DRAGNDROP, mimeType, item);
    }
    PersistableBundle bundle = getPersistableBundle();
    if (bundle != null) {
      data.getDescription().setExtras(bundle);
    }
    this.mClipData = data;
  }

  public boolean canAlternateClipData(int type) {
    if (type == -1 || this.mType == type) {
      return true;
    }
    SemClipData altData = ClipboardDataFactory.createClipBoardData(type);
    return setAlternateClipData(type, altData);
  }

  public SemClipData getAlternateClipData(int type) {
    SemClipData clipData = ClipboardDataFactory.createClipBoardData(type);
    if (clipData != null) {
      clipData.setProtected(isProtected());
      clipData.setPCClip(isPCClip());
      if (!setAlternateClipData(type, clipData)) {
        return null;
      }
      return clipData;
    }
    Log.secI(TAG, "ClipBoardDataFactory.createClipBoardData() is null : " + type);
    return clipData;
  }

  public boolean setAlternateClipData(int type, SemClipData altData) {
    if (altData != null) {
      altData.setParcelFileDescriptor(this.mParcelFd);
      altData.setTimestamp(this.mTimestamp);
      altData.setCallerUid(this.mCallerUid);
      altData.setClipData(this.mClipData);
      altData.setClipId(this.mClipId);
      altData.setMimeTypes(this.mMimeTypes);
      altData.setLabel(this.mLabel);
      altData.setKeyList(this.mKeyList);
      altData.setObjList(this.mObjList);
      altData.setPersistableBundle(getPersistableBundle());
      return true;
    }
    return false;
  }

  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o instanceof SemClipData) {
      SemClipData trgData = (SemClipData) o;
      boolean result = trgData.getClipType() == getClipType();
      return result;
    }
    boolean result2 = super.equals(o);
    return result2;
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mType);
    dest.writeLong(this.mTimestamp);
    dest.writeValue(Boolean.valueOf(this.mIsProtected));
    dest.writeLong(this.mCallerUid);
    dest.writeParcelable(this.mClipData, flags);
    dest.writeParcelable(this.mParcelFd, flags);
    dest.writeString(this.mClipId);
    dest.writeStringList(this.mMimeTypes);
    dest.writeCharSequence(this.mLabel);
    dest.writeList(this.mKeyList);
    dest.writeList(this.mObjList);
    dest.writePersistableBundle(getPersistableBundle());
    dest.writeValue(Boolean.valueOf(this.mIsPCClip));
    dest.writeValue(Boolean.valueOf(this.mIsRemoteClip));
    dest.writeString(this.mRemoteClipId);
    dest.writeInt(this.mRemoteState);
  }

  public void setMimeType(String mimeType) {
    if (this.mMimeTypes == null) {
      this.mMimeTypes = new ArrayList<>();
    }
    this.mMimeTypes.add(mimeType);
  }

  void setMimeTypes(ArrayList<String> mimeTypes) {
    this.mMimeTypes = mimeTypes;
  }

  public void setLabelAndMimeType(ClipData clipData) {
    ClipDescription description = clipData.getDescription();
    if (description != null) {
      if (!TextUtils.isEmpty(description.getLabel())) {
        setLabel(description.getLabel().toString());
      }
      int mimeCount = description.getMimeTypeCount();
      while (true) {
        int mimeCount2 = mimeCount - 1;
        if (mimeCount > 0) {
          setMimeType(description.getMimeType(mimeCount2));
          mimeCount = mimeCount2;
        } else {
          return;
        }
      }
    }
  }

  public boolean setLabel(CharSequence label) {
    if (TextUtils.isEmpty(label)) {
      return false;
    }
    if (label.length() > 131072) {
      label = label.subSequence(0, 131072);
    }
    this.mLabel = label;
    return true;
  }

  public CharSequence getLabel() {
    return this.mLabel;
  }

  public void setPersistableBundle(PersistableBundle bundle) {
    if (bundle == null) {
      return;
    }
    this.mBundle = bundle;
    if (this.mKeyList == null) {
      this.mKeyList = new ArrayList<>();
    }
    if (this.mObjList == null) {
      this.mObjList = new ArrayList<>();
    }
    this.mKeyList.clear();
    this.mObjList.clear();
    for (String key : bundle.keySet()) {
      this.mKeyList.add(key);
      this.mObjList.add(bundle.get(key));
    }
    this.mKeyList.add(ClipboardConstants.PC_CLIP_EXTRA_VALUE);
    this.mObjList.add(Boolean.valueOf(this.mIsPCClip));
    this.mBundle.putBoolean(ClipboardConstants.PC_CLIP_EXTRA_VALUE, this.mIsPCClip);
  }

  public PersistableBundle getPersistableBundle() {
    PersistableBundle persistableBundle = this.mBundle;
    if (persistableBundle != null) {
      return persistableBundle;
    }
    ArrayList<Object> arrayList = this.mKeyList;
    if ((arrayList == null || this.mObjList == null) && !this.mIsPCClip) {
      return null;
    }
    if (arrayList != null && this.mObjList != null) {
      int itemCount = arrayList.size();
      this.mBundle = new PersistableBundle(itemCount);
      while (true) {
        itemCount--;
        if (itemCount < 0) {
          break;
        }
        putIntoBundle((String) this.mKeyList.get(itemCount), this.mObjList.get(itemCount));
      }
    }
    if (this.mIsPCClip) {
      if (this.mKeyList == null) {
        ArrayList<Object> arrayList2 = new ArrayList<>();
        this.mKeyList = arrayList2;
        arrayList2.clear();
      }
      if (this.mObjList == null) {
        ArrayList<Object> arrayList3 = new ArrayList<>();
        this.mObjList = arrayList3;
        arrayList3.clear();
      }
      if (this.mBundle == null) {
        this.mBundle = new PersistableBundle();
      }
      this.mKeyList.add(ClipboardConstants.PC_CLIP_EXTRA_VALUE);
      this.mObjList.add(Boolean.valueOf(this.mIsPCClip));
      this.mBundle.putBoolean(ClipboardConstants.PC_CLIP_EXTRA_VALUE, this.mIsPCClip);
    }
    return this.mBundle;
  }

  private void putIntoBundle(String key, Object value) {
    if (value instanceof Integer) {
      this.mBundle.putInt(key, ((Integer) value).intValue());
      return;
    }
    if (value instanceof int[]) {
      this.mBundle.putIntArray(key, (int[]) value);
      return;
    }
    if (value instanceof Long) {
      this.mBundle.putLong(key, ((Long) value).longValue());
      return;
    }
    if (value instanceof long[]) {
      this.mBundle.putLongArray(key, (long[]) value);
      return;
    }
    if (value instanceof Double) {
      this.mBundle.putDouble(key, ((Double) value).doubleValue());
      return;
    }
    if (value instanceof double[]) {
      this.mBundle.putDoubleArray(key, (double[]) value);
      return;
    }
    if (value instanceof String) {
      this.mBundle.putString(key, (String) value);
      return;
    }
    if (value instanceof String[]) {
      this.mBundle.putStringArray(key, (String[]) value);
      return;
    }
    if (value instanceof Boolean) {
      this.mBundle.putBoolean(key, ((Boolean) value).booleanValue());
      return;
    }
    if (value instanceof boolean[]) {
      this.mBundle.putBooleanArray(key, (boolean[]) value);
    } else if (value instanceof PersistableBundle) {
      this.mBundle.putAll((PersistableBundle) value);
    } else {
      Log.secE(TAG, "putIntoBundle fails. value is " + value.getClass());
    }
  }

  public void setKeyList(ArrayList<Object> keyList) {
    this.mKeyList = keyList;
  }

  public ArrayList<Object> getKeyList() {
    return this.mKeyList;
  }

  public void setObjList(ArrayList<Object> objList) {
    this.mObjList = objList;
  }

  public ArrayList<Object> getObjList() {
    return this.mObjList;
  }

  public String createThumbnailFromData(Context context) {
    return null;
  }

  public String getThumbnailPath() {
    return null;
  }

  public boolean setThumbnailPath(String filePath) {
    return false;
  }

  public boolean isPCClip() {
    return this.mIsPCClip;
  }

  public void setPCClip(boolean value) {
    this.mIsPCClip = value;
  }

  public void setPCClipExtra(boolean value) {
    ArrayList<Object> arrayList;
    if (this.mBundle != null && (arrayList = this.mKeyList) != null && this.mObjList != null) {
      int index = arrayList.indexOf(ClipboardConstants.PC_CLIP_EXTRA_VALUE);
      this.mObjList.set(index, Boolean.valueOf(value));
      this.mBundle.putBoolean(ClipboardConstants.PC_CLIP_EXTRA_VALUE, value);
    }
  }

  public HashSet<String> getActivePermissionOwners() {
    if (this.activePermissionOwners == null) {
      this.activePermissionOwners = new HashSet<>();
    }
    return this.activePermissionOwners;
  }

  public boolean isRemoteClip() {
    return this.mIsRemoteClip;
  }

  public void setRemoteClip(boolean value) {
    this.mIsRemoteClip = value;
  }

  public String getRemoteClipId() {
    return this.mRemoteClipId;
  }

  public void setRemoteClipId(String id) {
    this.mRemoteClipId = id;
  }

  public int getRemoteState() {
    return this.mRemoteState;
  }

  public void setRemoteState(int state) {
    this.mRemoteState = state;
  }

  /* JADX WARN: Code restructure failed: missing block: B:12:0x0072, code lost:

     if (r2 == null) goto L28;
  */
  /* JADX WARN: Code restructure failed: missing block: B:13:0x0074, code lost:

     r4 = android.os.Binder.clearCallingIdentity();
  */
  /* JADX WARN: Code restructure failed: missing block: B:15:0x0078, code lost:

     r11.getContentResolver().delete(r2, null, null);
  */
  /* JADX WARN: Code restructure failed: missing block: B:21:0x0085, code lost:

     r0 = move-exception;
  */
  /* JADX WARN: Code restructure failed: missing block: B:22:0x00a4, code lost:

     android.os.Binder.restoreCallingIdentity(r4);
  */
  /* JADX WARN: Code restructure failed: missing block: B:23:0x00a7, code lost:

     throw r0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:24:0x0087, code lost:

     r0 = move-exception;
  */
  /* JADX WARN: Code restructure failed: missing block: B:25:0x0088, code lost:

     android.sec.clipboard.util.Log.m60e(com.samsung.android.content.clipboard.data.SemClipData.TAG, "Exception occurs in deleteContentUri because " + r0.getMessage());
  */
  /* JADX WARN: Code restructure failed: missing block: B:26:0x00a8, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:33:0x006f, code lost:

     if (r3 == null) goto L17;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  protected void deleteContentUriInternal(Context context, String path) {
    Uri contentUri = null;
    Cursor cursor = null;
    try {
      try {
        cursor =
            context
                .getContentResolver()
                .query(
                    SemImageClipDataProvider.CONTENT_URI,
                    new String[] {"id"},
                    "_data=? ",
                    new String[] {path},
                    null);
        if (cursor != null && cursor.moveToFirst()) {
          int id = cursor.getInt(cursor.getColumnIndex("id"));
          contentUri = Uri.withAppendedPath(SemImageClipDataProvider.CONTENT_URI, "" + id);
        }
      } finally {
        if (cursor != null) {
          cursor.close();
        }
      }
    } catch (SQLiteException e) {
      Log.m60e(TAG, "SQLiteException occurs in deleteContentUri because " + e.getMessage());
    }
  }
}
