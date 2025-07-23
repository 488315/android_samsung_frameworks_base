package com.samsung.android.content.clipboard.data;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Binder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.sec.clipboard.data.ClipboardConstants;
import android.sec.clipboard.data.ClipboardDataFactory;
import android.sec.clipboard.util.Log;
import android.text.TextUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;

/* loaded from: classes6.dex */
public abstract class SemClipData implements Parcelable, Serializable {
    public static final Parcelable.Creator<SemClipData> CREATOR = new Parcelable.Creator<SemClipData>() { // from class: com.samsung.android.content.clipboard.data.SemClipData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemClipData createFromParcel(Parcel parcel) {
            return ClipboardDataFactory.createClipBoardData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemClipData[] newArray(int i) {
            return new SemClipData[i];
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

    public String createThumbnailFromData(Context context) {
        return null;
    }

    public abstract void deleteContentUri(Context context, String str);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected abstract ClipData getClipDataInternal();

    public String getThumbnailPath() {
        return null;
    }

    public abstract void insertContentUri(Context context, String str);

    protected abstract void readFromSource(Parcel parcel);

    public boolean setThumbnailPath(String str) {
        return false;
    }

    public abstract void toLoad();

    public abstract void toSave();

    public SemClipData(int i) {
        this.mTimestamp = 0L;
        this.mIsProtected = false;
        this.mClipData = null;
        this.mLabel = "";
        this.mBundle = null;
        this.mIsPCClip = false;
        this.mIsRemoteClip = false;
        this.mRemoteState = 0;
        this.activePermissionOwners = new HashSet<>();
        this.mType = i;
        this.mCallerUid = Binder.getCallingUid();
        this.mTimestamp = System.currentTimeMillis();
        this.mParcelFd = null;
        this.mClipId = createUniqueId();
    }

    public SemClipData(Parcel parcel) {
        this.mTimestamp = 0L;
        this.mIsProtected = false;
        this.mClipData = null;
        this.mLabel = "";
        this.mBundle = null;
        this.mIsPCClip = false;
        this.mIsRemoteClip = false;
        this.mRemoteState = 0;
        this.activePermissionOwners = new HashSet<>();
        this.mType = parcel.readInt();
        this.mTimestamp = parcel.readLong();
        this.mIsProtected = ((Boolean) parcel.readValue(Boolean.class.getClassLoader())).booleanValue();
        this.mCallerUid = parcel.readLong();
        this.mClipData = (ClipData) parcel.readParcelable(ClipData.class.getClassLoader());
        this.mParcelFd = (ParcelFileDescriptor) parcel.readParcelable(ParcelFileDescriptor.class.getClassLoader());
        this.mClipId = parcel.readString();
        this.mMimeTypes = parcel.createStringArrayList();
        this.mLabel = parcel.readCharSequence();
        this.mKeyList = parcel.readArrayList(Object.class.getClassLoader());
        this.mObjList = parcel.readArrayList(Object.class.getClassLoader());
        this.mBundle = parcel.readPersistableBundle();
        this.mIsPCClip = ((Boolean) parcel.readValue(Boolean.class.getClassLoader())).booleanValue();
        this.mIsRemoteClip = ((Boolean) parcel.readValue(Boolean.class.getClassLoader())).booleanValue();
        this.mRemoteClipId = parcel.readString();
        this.mRemoteState = parcel.readInt();
    }

    private String createUniqueId() {
        int hashCode = hashCode();
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        stringBuffer.append(hashCode);
        stringBuffer.append(calendar.get(12));
        stringBuffer.append(calendar.get(13));
        stringBuffer.append(calendar.get(14));
        stringBuffer.append(random.nextInt(calendar.get(14) + 1));
        return stringBuffer.toString();
    }

    public void checkClipId() {
        if (this.mClipId == null) {
            this.mClipId = createUniqueId();
        }
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public void setTimestamp(long j) {
        this.mTimestamp = j;
    }

    public ParcelFileDescriptor getParcelFileDescriptor() {
        return this.mParcelFd;
    }

    public void setParcelFileDescriptor(ParcelFileDescriptor parcelFileDescriptor) {
        this.mParcelFd = parcelFileDescriptor;
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

    public void setCallerUid(long j) {
        this.mCallerUid = j;
    }

    public void setClipId(String str) {
        this.mClipId = str;
    }

    public String getClipId() {
        return this.mClipId;
    }

    public void setProtected(boolean z) {
        this.mIsProtected = z;
        if (this.mIsPCClip && z) {
            setPCClip(false);
            setPCClipExtra(false);
        }
        if (this.mIsRemoteClip && z) {
            setRemoteClip(false);
        }
    }

    @Deprecated
    public void setProtectState(boolean z) {
        this.mIsProtected = z;
    }

    public boolean isProtected() {
        return this.mIsProtected;
    }

    public ClipData getClipData() {
        return getClipDataInternal();
    }

    public void setClipData(ClipData clipData) {
        this.mClipData = clipData;
    }

    public void setClipData(String[] strArr, ClipData.Item item) {
        ClipData clipData;
        CharSequence label = getLabel();
        if (!TextUtils.isEmpty(label)) {
            if (this.mMimeTypes == null) {
                clipData = new ClipData(label, strArr, item);
            } else {
                ArrayList<String> arrayList = this.mMimeTypes;
                clipData = new ClipData(label, (String[]) arrayList.toArray(new String[arrayList.size()]), item);
            }
        } else {
            clipData = new ClipData(ClipboardConstants.CLIPBOARD_DRAGNDROP, strArr, item);
        }
        PersistableBundle persistableBundle = getPersistableBundle();
        if (persistableBundle != null) {
            clipData.getDescription().setExtras(persistableBundle);
        }
        this.mClipData = clipData;
    }

    public boolean canAlternateClipData(int i) {
        if (i == -1 || this.mType == i) {
            return true;
        }
        return setAlternateClipData(i, ClipboardDataFactory.createClipBoardData(i));
    }

    public SemClipData getAlternateClipData(int i) {
        SemClipData createClipBoardData = ClipboardDataFactory.createClipBoardData(i);
        if (createClipBoardData != null) {
            createClipBoardData.setProtected(isProtected());
            createClipBoardData.setPCClip(isPCClip());
            if (setAlternateClipData(i, createClipBoardData)) {
                return createClipBoardData;
            }
            return null;
        }
        Log.secI(TAG, "ClipBoardDataFactory.createClipBoardData() is null : " + i);
        return createClipBoardData;
    }

    public boolean setAlternateClipData(int i, SemClipData semClipData) {
        if (semClipData == null) {
            return false;
        }
        semClipData.setParcelFileDescriptor(this.mParcelFd);
        semClipData.setTimestamp(this.mTimestamp);
        semClipData.setCallerUid(this.mCallerUid);
        semClipData.setClipData(this.mClipData);
        semClipData.setClipId(this.mClipId);
        semClipData.setMimeTypes(this.mMimeTypes);
        semClipData.setLabel(this.mLabel);
        semClipData.setKeyList(this.mKeyList);
        semClipData.setObjList(this.mObjList);
        semClipData.setPersistableBundle(getPersistableBundle());
        return true;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof SemClipData) {
            return ((SemClipData) obj).getClipType() == getClipType();
        }
        return super.equals(obj);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeLong(this.mTimestamp);
        parcel.writeValue(Boolean.valueOf(this.mIsProtected));
        parcel.writeLong(this.mCallerUid);
        parcel.writeParcelable(this.mClipData, i);
        parcel.writeParcelable(this.mParcelFd, i);
        parcel.writeString(this.mClipId);
        parcel.writeStringList(this.mMimeTypes);
        parcel.writeCharSequence(this.mLabel);
        parcel.writeList(this.mKeyList);
        parcel.writeList(this.mObjList);
        parcel.writePersistableBundle(getPersistableBundle());
        parcel.writeValue(Boolean.valueOf(this.mIsPCClip));
        parcel.writeValue(Boolean.valueOf(this.mIsRemoteClip));
        parcel.writeString(this.mRemoteClipId);
        parcel.writeInt(this.mRemoteState);
    }

    public void setMimeType(String str) {
        if (this.mMimeTypes == null) {
            this.mMimeTypes = new ArrayList<>();
        }
        this.mMimeTypes.add(str);
    }

    void setMimeTypes(ArrayList<String> arrayList) {
        this.mMimeTypes = arrayList;
    }

    public void setLabelAndMimeType(ClipData clipData) {
        ClipDescription description = clipData.getDescription();
        if (description == null) {
            return;
        }
        if (!TextUtils.isEmpty(description.getLabel())) {
            setLabel(description.getLabel().toString());
        }
        int mimeTypeCount = description.getMimeTypeCount();
        while (true) {
            int i = mimeTypeCount - 1;
            if (mimeTypeCount <= 0) {
                return;
            }
            setMimeType(description.getMimeType(i));
            mimeTypeCount = i;
        }
    }

    public boolean setLabel(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        if (charSequence.length() > 131072) {
            charSequence = charSequence.subSequence(0, 131072);
        }
        this.mLabel = charSequence;
        return true;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public void setPersistableBundle(PersistableBundle persistableBundle) {
        if (persistableBundle == null) {
            return;
        }
        this.mBundle = persistableBundle;
        if (this.mKeyList == null) {
            this.mKeyList = new ArrayList<>();
        }
        if (this.mObjList == null) {
            this.mObjList = new ArrayList<>();
        }
        this.mKeyList.clear();
        this.mObjList.clear();
        for (String str : persistableBundle.keySet()) {
            this.mKeyList.add(str);
            this.mObjList.add(persistableBundle.get(str));
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
            int size = arrayList.size();
            this.mBundle = new PersistableBundle(size);
            while (true) {
                size--;
                if (size < 0) {
                    break;
                }
                putIntoBundle((String) this.mKeyList.get(size), this.mObjList.get(size));
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

    private void putIntoBundle(String str, Object obj) {
        if (obj instanceof Integer) {
            this.mBundle.putInt(str, ((Integer) obj).intValue());
            return;
        }
        if (obj instanceof int[]) {
            this.mBundle.putIntArray(str, (int[]) obj);
            return;
        }
        if (obj instanceof Long) {
            this.mBundle.putLong(str, ((Long) obj).longValue());
            return;
        }
        if (obj instanceof long[]) {
            this.mBundle.putLongArray(str, (long[]) obj);
            return;
        }
        if (obj instanceof Double) {
            this.mBundle.putDouble(str, ((Double) obj).doubleValue());
            return;
        }
        if (obj instanceof double[]) {
            this.mBundle.putDoubleArray(str, (double[]) obj);
            return;
        }
        if (obj instanceof String) {
            this.mBundle.putString(str, (String) obj);
            return;
        }
        if (obj instanceof String[]) {
            this.mBundle.putStringArray(str, (String[]) obj);
            return;
        }
        if (obj instanceof Boolean) {
            this.mBundle.putBoolean(str, ((Boolean) obj).booleanValue());
            return;
        }
        if (obj instanceof boolean[]) {
            this.mBundle.putBooleanArray(str, (boolean[]) obj);
        } else {
            if (obj instanceof PersistableBundle) {
                this.mBundle.putAll((PersistableBundle) obj);
                return;
            }
            Log.secE(TAG, "putIntoBundle fails. value is " + obj.getClass());
        }
    }

    public void setKeyList(ArrayList<Object> arrayList) {
        this.mKeyList = arrayList;
    }

    public ArrayList<Object> getKeyList() {
        return this.mKeyList;
    }

    public void setObjList(ArrayList<Object> arrayList) {
        this.mObjList = arrayList;
    }

    public ArrayList<Object> getObjList() {
        return this.mObjList;
    }

    public boolean isPCClip() {
        return this.mIsPCClip;
    }

    public void setPCClip(boolean z) {
        this.mIsPCClip = z;
    }

    public void setPCClipExtra(boolean z) {
        ArrayList<Object> arrayList;
        if (this.mBundle == null || (arrayList = this.mKeyList) == null || this.mObjList == null) {
            return;
        }
        this.mObjList.set(arrayList.indexOf(ClipboardConstants.PC_CLIP_EXTRA_VALUE), Boolean.valueOf(z));
        this.mBundle.putBoolean(ClipboardConstants.PC_CLIP_EXTRA_VALUE, z);
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

    public void setRemoteClip(boolean z) {
        this.mIsRemoteClip = z;
    }

    public String getRemoteClipId() {
        return this.mRemoteClipId;
    }

    public void setRemoteClipId(String str) {
        this.mRemoteClipId = str;
    }

    public int getRemoteState() {
        return this.mRemoteState;
    }

    public void setRemoteState(int i) {
        this.mRemoteState = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x009a A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void deleteContentUriInternal(android.content.Context r12, java.lang.String r13) {
        /*
            r11 = this;
            java.lang.String r11 = "id"
            java.lang.String r1 = "SemClipData"
            java.lang.String r0 = ""
            java.lang.String r2 = "Exception occurs in deleteContentUri because "
            java.lang.String r3 = "SQLiteException occurs in deleteContentUri because "
            r4 = 0
            android.content.ContentResolver r5 = r12.getContentResolver()     // Catch: java.lang.Throwable -> L4d android.database.sqlite.SQLiteException -> L50
            android.net.Uri r6 = com.samsung.android.content.clipboard.provider.SemImageClipDataProvider.CONTENT_URI     // Catch: java.lang.Throwable -> L4d android.database.sqlite.SQLiteException -> L50
            java.lang.String[] r7 = new java.lang.String[]{r11}     // Catch: java.lang.Throwable -> L4d android.database.sqlite.SQLiteException -> L50
            java.lang.String r8 = "_data=? "
            java.lang.String[] r9 = new java.lang.String[]{r13}     // Catch: java.lang.Throwable -> L4d android.database.sqlite.SQLiteException -> L50
            r10 = 0
            android.database.Cursor r13 = r5.query(r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L4d android.database.sqlite.SQLiteException -> L50
            if (r13 == 0) goto L46
            boolean r5 = r13.moveToFirst()     // Catch: android.database.sqlite.SQLiteException -> L43 java.lang.Throwable -> L9b
            if (r5 == 0) goto L46
            int r11 = r13.getColumnIndex(r11)     // Catch: android.database.sqlite.SQLiteException -> L43 java.lang.Throwable -> L9b
            int r11 = r13.getInt(r11)     // Catch: android.database.sqlite.SQLiteException -> L43 java.lang.Throwable -> L9b
            android.net.Uri r5 = com.samsung.android.content.clipboard.provider.SemImageClipDataProvider.CONTENT_URI     // Catch: android.database.sqlite.SQLiteException -> L43 java.lang.Throwable -> L9b
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: android.database.sqlite.SQLiteException -> L43 java.lang.Throwable -> L9b
            r6.<init>(r0)     // Catch: android.database.sqlite.SQLiteException -> L43 java.lang.Throwable -> L9b
            r6.append(r11)     // Catch: android.database.sqlite.SQLiteException -> L43 java.lang.Throwable -> L9b
            java.lang.String r11 = r6.toString()     // Catch: android.database.sqlite.SQLiteException -> L43 java.lang.Throwable -> L9b
            android.net.Uri r11 = android.net.Uri.withAppendedPath(r5, r11)     // Catch: android.database.sqlite.SQLiteException -> L43 java.lang.Throwable -> L9b
            goto L47
        L43:
            r0 = move-exception
            r11 = r0
            goto L53
        L46:
            r11 = r4
        L47:
            if (r13 == 0) goto L6c
            r13.close()
            goto L6c
        L4d:
            r0 = move-exception
            r11 = r0
            goto L9e
        L50:
            r0 = move-exception
            r11 = r0
            r13 = r4
        L53:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L9b
            r0.<init>(r3)     // Catch: java.lang.Throwable -> L9b
            java.lang.String r11 = r11.getMessage()     // Catch: java.lang.Throwable -> L9b
            r0.append(r11)     // Catch: java.lang.Throwable -> L9b
            java.lang.String r11 = r0.toString()     // Catch: java.lang.Throwable -> L9b
            android.sec.clipboard.util.Log.e(r1, r11)     // Catch: java.lang.Throwable -> L9b
            if (r13 == 0) goto L6b
            r13.close()
        L6b:
            r11 = r4
        L6c:
            if (r11 == 0) goto L9a
            long r5 = android.os.Binder.clearCallingIdentity()
            android.content.ContentResolver r12 = r12.getContentResolver()     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L80
            r12.delete(r11, r4, r4)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L80
        L79:
            android.os.Binder.restoreCallingIdentity(r5)
            goto L9a
        L7d:
            r0 = move-exception
            r11 = r0
            goto L96
        L80:
            r0 = move-exception
            r11 = r0
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7d
            r12.<init>(r2)     // Catch: java.lang.Throwable -> L7d
            java.lang.String r11 = r11.getMessage()     // Catch: java.lang.Throwable -> L7d
            r12.append(r11)     // Catch: java.lang.Throwable -> L7d
            java.lang.String r11 = r12.toString()     // Catch: java.lang.Throwable -> L7d
            android.sec.clipboard.util.Log.e(r1, r11)     // Catch: java.lang.Throwable -> L7d
            goto L79
        L96:
            android.os.Binder.restoreCallingIdentity(r5)
            throw r11
        L9a:
            return
        L9b:
            r0 = move-exception
            r11 = r0
            r4 = r13
        L9e:
            if (r4 == 0) goto La3
            r4.close()
        La3:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.content.clipboard.data.SemClipData.deleteContentUriInternal(android.content.Context, java.lang.String):void");
    }
}
