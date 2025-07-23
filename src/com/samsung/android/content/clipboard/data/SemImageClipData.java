package com.samsung.android.content.clipboard.data;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Binder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.sec.clipboard.data.ClipboardConstants;
import android.sec.clipboard.util.CompatabilityHelper;
import android.sec.clipboard.util.Log;
import android.text.TextUtils;
import com.samsung.android.content.clipboard.provider.SemImageClipDataProvider;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

/* loaded from: classes6.dex */
public class SemImageClipData extends SemClipData {
    private static final String TAG = "SemImageClipData";
    private static final long serialVersionUID = 1;
    private transient Uri mContentUri;
    private String mContentUriString;
    private String mExtraDataPath;
    private transient ParcelFileDescriptor mExtraParcelFd;
    private String mImagePath;
    private String mInitBaseValue;
    private boolean mInitBaseValueCheck;

    public SemImageClipData() {
        super(2);
        this.mImagePath = "";
        this.mContentUriString = "";
        this.mContentUri = null;
        this.mInitBaseValue = "";
        this.mInitBaseValueCheck = true;
        this.mExtraDataPath = "";
        this.mExtraParcelFd = null;
    }

    public SemImageClipData(Parcel parcel) {
        super(parcel);
        this.mImagePath = "";
        this.mContentUriString = "";
        this.mContentUri = null;
        this.mInitBaseValue = "";
        this.mInitBaseValueCheck = true;
        this.mExtraDataPath = "";
        this.mExtraParcelFd = null;
        readFromSource(parcel);
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean setAlternateClipData(int i, SemClipData semClipData) {
        if (!super.setAlternateClipData(i, semClipData) || this.mImagePath == null || i != 2) {
            return false;
        }
        SemImageClipData semImageClipData = (SemImageClipData) semClipData;
        semImageClipData.setExtraParcelFileDescriptor(this.mExtraParcelFd);
        return semImageClipData.setBitmapPath(getBitmapPath(), getExtraDataPath());
    }

    private void setClipData() {
        File file = new File(this.mImagePath);
        try {
            setClipData(new String[]{Files.probeContentType(file.toPath())}, new ClipData.Item(Uri.fromFile(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toSave() {
        Uri uri = this.mContentUri;
        if (uri != null) {
            this.mContentUriString = uri.toString();
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toLoad() {
        if (TextUtils.isEmpty(this.mContentUriString)) {
            this.mContentUri = null;
        } else {
            this.mContentUri = Uri.parse(this.mContentUriString);
        }
        String str = this.mImagePath;
        if (str != null && str.contains(CompatabilityHelper.OLD_CLIPBOARD_ROOT_PATH)) {
            setImagePath(CompatabilityHelper.replacePathForCompatability(this.mImagePath));
        }
        Log.secD(TAG, "imageclipdata toLoad called");
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

    public boolean setImagePath(String str) {
        if (str != null && str.length() >= 1) {
            if (this.mInitBaseValueCheck) {
                this.mInitBaseValue = str;
                this.mInitBaseValueCheck = false;
            }
            this.mImagePath = str;
            if (new File(str).isFile()) {
                return true;
            }
            Log.secE(TAG, "ClipboardDataBitmap : value is no file path ..check plz");
        }
        return false;
    }

    public boolean setExtraDataPath(String str) {
        if (str != null && str.length() >= 1) {
            this.mExtraDataPath = str;
            if (new File(str).isFile()) {
                return true;
            }
            Log.secE(TAG, "ClipboardDataBitmap : ExtraDataPath is no file path ..check plz");
        }
        return false;
    }

    public ParcelFileDescriptor getImageFileDescriptor() {
        return getParcelFileDescriptor();
    }

    public void setExtraParcelFileDescriptor(ParcelFileDescriptor parcelFileDescriptor) {
        this.mExtraParcelFd = parcelFileDescriptor;
    }

    public ParcelFileDescriptor getExtraParcelFileDescriptor() {
        ParcelFileDescriptor parcelFileDescriptor = this.mExtraParcelFd;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor;
        }
        if (TextUtils.isEmpty(this.mExtraDataPath)) {
            return null;
        }
        try {
            return ParcelFileDescriptor.open(new File(this.mExtraDataPath), 268435456);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getInitBasePath() {
        return this.mInitBaseValue;
    }

    public boolean hasExtraData() {
        String str = this.mExtraDataPath;
        return str != null && str.length() >= 1;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean equals(Object obj) {
        Log.secI(TAG, "bitmap equals");
        if (!super.equals(obj) || !(obj instanceof SemImageClipData)) {
            return false;
        }
        SemImageClipData semImageClipData = (SemImageClipData) obj;
        String bitmapPath = semImageClipData.getBitmapPath();
        String initBasePath = semImageClipData.getInitBasePath();
        if (initBasePath != null && initBasePath.compareTo(this.mInitBaseValue) == 0) {
            ParcelFileDescriptor parcelFileDescriptor = semImageClipData.getParcelFileDescriptor();
            if (parcelFileDescriptor != null) {
                if (compareFile(this.mImagePath, parcelFileDescriptor.getFileDescriptor())) {
                    Log.secE(TAG, "bitmap equals");
                    return true;
                }
            } else if (compareFile(this.mImagePath, bitmapPath)) {
                Log.secE(TAG, "bitmap equals");
                return true;
            }
        }
        return false;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Log.secI(TAG, "Bitmap write to parcel");
        parcel.writeInt(2);
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mImagePath);
        Uri uri = this.mContentUri;
        if (uri == null) {
            parcel.writeString("");
        } else {
            parcel.writeString(uri.toString());
        }
        parcel.writeString(this.mInitBaseValue);
        parcel.writeByte(this.mInitBaseValueCheck ? (byte) 1 : (byte) 0);
        parcel.writeString(this.mExtraDataPath);
        parcel.writeParcelable(this.mExtraParcelFd, i);
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    protected void readFromSource(Parcel parcel) {
        try {
            this.mImagePath = parcel.readString();
            String readString = parcel.readString();
            if (TextUtils.isEmpty(readString)) {
                this.mContentUri = null;
            } else {
                this.mContentUri = Uri.parse(readString);
            }
            this.mInitBaseValue = parcel.readString();
            this.mInitBaseValueCheck = parcel.readByte() != 0;
            this.mExtraDataPath = parcel.readString();
            this.mExtraParcelFd = (ParcelFileDescriptor) parcel.readParcelable(ParcelFileDescriptor.class.getClassLoader());
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
        if (TextUtils.isEmpty(this.mImagePath)) {
            return null;
        }
        try {
            return ParcelFileDescriptor.open(new File(this.mImagePath), 268435456);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SemImageClipData class. Value is ");
        int length = this.mImagePath.length();
        String str = this.mImagePath;
        CharSequence charSequence = str;
        if (length > 20) {
            charSequence = str.subSequence(0, 20);
        }
        sb.append((Object) charSequence);
        return sb.toString();
    }

    private boolean compareFile(FileInputStream fileInputStream, FileInputStream fileInputStream2) {
        try {
            try {
                int size = (int) fileInputStream.getChannel().size();
                int size2 = (int) fileInputStream2.getChannel().size();
                if (size == size2 && size >= 1 && size2 >= 1) {
                    int i = size <= 128 ? size : 128;
                    int i2 = size / i;
                    if (i2 >= 5) {
                        i2 = 5;
                    }
                    int i3 = (size - (i * i2)) / i2;
                    byte[] bArr = new byte[i];
                    byte[] bArr2 = new byte[i];
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                    BufferedInputStream bufferedInputStream2 = new BufferedInputStream(fileInputStream2);
                    int i4 = 0;
                    boolean z = false;
                    for (int i5 = 0; i5 < i2; i5++) {
                        bufferedInputStream.read(bArr, 0, i);
                        bufferedInputStream2.read(bArr2, 0, i);
                        i4 += i + i3;
                        long j = i4;
                        bufferedInputStream.skip(j);
                        bufferedInputStream2.skip(j);
                        for (int i6 = 0; i6 < i; i6++) {
                            z = bArr[i6] == bArr2[i6];
                        }
                    }
                    try {
                        fileInputStream.close();
                        fileInputStream2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return z;
                }
                try {
                    fileInputStream.close();
                    fileInputStream2.close();
                    return false;
                } catch (IOException e2) {
                    e2.printStackTrace();
                    return false;
                }
            } catch (IOException e3) {
                e3.printStackTrace();
                try {
                    fileInputStream.close();
                    fileInputStream2.close();
                    return false;
                } catch (IOException e4) {
                    e4.printStackTrace();
                    return false;
                }
            }
        } catch (Throwable th) {
            try {
                fileInputStream.close();
                fileInputStream2.close();
            } catch (IOException e5) {
                e5.printStackTrace();
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x004b A[Catch: Exception -> 0x0047, TRY_LEAVE, TryCatch #4 {Exception -> 0x0047, blocks: (B:42:0x0043, B:35:0x004b), top: B:41:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0043 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean compareFile(java.lang.String r3, java.io.FileDescriptor r4) {
        /*
            r2 = this;
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L27 java.io.FileNotFoundException -> L2a
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L27 java.io.FileNotFoundException -> L2a
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L1f java.io.FileNotFoundException -> L23
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L1f java.io.FileNotFoundException -> L23
            boolean r2 = r2.compareFile(r1, r3)     // Catch: java.lang.Throwable -> L1b java.io.FileNotFoundException -> L1d
            r1.close()     // Catch: java.lang.Exception -> L16
            r3.close()     // Catch: java.lang.Exception -> L16
            goto L3f
        L16:
            r3 = move-exception
            r3.printStackTrace()
            goto L3f
        L1b:
            r2 = move-exception
            goto L21
        L1d:
            r2 = move-exception
            goto L25
        L1f:
            r2 = move-exception
            r3 = r0
        L21:
            r0 = r1
            goto L41
        L23:
            r2 = move-exception
            r3 = r0
        L25:
            r0 = r1
            goto L2c
        L27:
            r2 = move-exception
            r3 = r0
            goto L41
        L2a:
            r2 = move-exception
            r3 = r0
        L2c:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L40
            if (r0 == 0) goto L34
            r0.close()     // Catch: java.lang.Exception -> L3a
        L34:
            if (r3 == 0) goto L3e
            r3.close()     // Catch: java.lang.Exception -> L3a
            goto L3e
        L3a:
            r2 = move-exception
            r2.printStackTrace()
        L3e:
            r2 = 0
        L3f:
            return r2
        L40:
            r2 = move-exception
        L41:
            if (r0 == 0) goto L49
            r0.close()     // Catch: java.lang.Exception -> L47
            goto L49
        L47:
            r3 = move-exception
            goto L4f
        L49:
            if (r3 == 0) goto L52
            r3.close()     // Catch: java.lang.Exception -> L47
            goto L52
        L4f:
            r3.printStackTrace()
        L52:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.content.clipboard.data.SemImageClipData.compareFile(java.lang.String, java.io.FileDescriptor):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0049 A[Catch: Exception -> 0x0045, TRY_LEAVE, TryCatch #3 {Exception -> 0x0045, blocks: (B:32:0x0041, B:25:0x0049), top: B:31:0x0041 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0041 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v14, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v6, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean compareFile(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L27 java.io.FileNotFoundException -> L2a
            r1.<init>(r4)     // Catch: java.lang.Throwable -> L27 java.io.FileNotFoundException -> L2a
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L1f java.io.FileNotFoundException -> L23
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L1f java.io.FileNotFoundException -> L23
            boolean r3 = r3.compareFile(r1, r2)     // Catch: java.lang.Throwable -> L1b java.io.FileNotFoundException -> L1d
            r1.close()     // Catch: java.lang.Exception -> L16
            r2.close()     // Catch: java.lang.Exception -> L16
            goto L3d
        L16:
            r4 = move-exception
            r4.printStackTrace()
            goto L3d
        L1b:
            r3 = move-exception
            goto L21
        L1d:
            r3 = move-exception
            goto L25
        L1f:
            r3 = move-exception
            r2 = r0
        L21:
            r0 = r1
            goto L3f
        L23:
            r3 = move-exception
            r2 = r0
        L25:
            r0 = r1
            goto L2c
        L27:
            r3 = move-exception
            r2 = r0
            goto L3f
        L2a:
            r3 = move-exception
            r2 = r0
        L2c:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L3e
            boolean r3 = r4.equals(r5)     // Catch: java.lang.Throwable -> L3e
            if (r0 == 0) goto L38
            r0.close()     // Catch: java.lang.Exception -> L16
        L38:
            if (r2 == 0) goto L3d
            r2.close()     // Catch: java.lang.Exception -> L16
        L3d:
            return r3
        L3e:
            r3 = move-exception
        L3f:
            if (r0 == 0) goto L47
            r0.close()     // Catch: java.lang.Exception -> L45
            goto L47
        L45:
            r4 = move-exception
            goto L4d
        L47:
            if (r2 == 0) goto L50
            r2.close()     // Catch: java.lang.Exception -> L45
            goto L50
        L4d:
            r4.printStackTrace()
        L50:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.content.clipboard.data.SemImageClipData.compareFile(java.lang.String, java.lang.String):boolean");
    }

    public String getBitmapPath() {
        return this.mImagePath;
    }

    public Uri getContentUri() {
        return this.mContentUri;
    }

    public String getExtraDataPath() {
        return this.mExtraDataPath;
    }

    public boolean setBitmapPath(String str, String str2) {
        Log.secI(TAG, "setBitmapPath");
        if (str != null && str.length() >= 1) {
            if (this.mInitBaseValueCheck) {
                this.mInitBaseValue = str;
                this.mInitBaseValueCheck = false;
            }
            this.mImagePath = str;
            if (str2 != null && str2.length() > 0) {
                Log.secI(TAG, "ExtraDataPath =" + str2);
                this.mExtraDataPath = str2;
            }
            ParcelFileDescriptor parcelFileDescriptor = super.getParcelFileDescriptor();
            if (parcelFileDescriptor != null && parcelFileDescriptor.getFileDescriptor().valid()) {
                ParcelFileDescriptor parcelFileDescriptor2 = this.mExtraParcelFd;
                if (parcelFileDescriptor2 != null && !parcelFileDescriptor2.getFileDescriptor().valid()) {
                    this.mExtraParcelFd = null;
                }
                return true;
            }
        }
        return false;
    }

    public void setContentUri(Uri uri) {
        this.mContentUri = uri;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void convertForRemote() {
        String str = this.mImagePath;
        if (setImagePath(ClipboardConstants.CLIPBOARD_REMOTE_PATH + str.substring(str.lastIndexOf("/")))) {
            Log.d(TAG, "success converting");
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void insertContentUri(Context context, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_data", str);
            setContentUri(context.getContentResolver().insert(SemImageClipDataProvider.CONTENT_URI, contentValues));
        } catch (Exception e) {
            Log.e(TAG, "Exception occurs in insertContentUri because " + e.getMessage());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void deleteContentUri(Context context, String str) {
        if (new File(this.mImagePath).exists()) {
            deleteContentUriInternal(context, this.mImagePath);
        }
    }
}
