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
import java.io.FileDescriptor;
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
    public void writeToParcel(Parcel parcel, int i) throws IOException {
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
            String string = parcel.readString();
            if (TextUtils.isEmpty(string)) {
                this.mContentUri = null;
            } else {
                this.mContentUri = Uri.parse(string);
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
        CharSequence charSequenceSubSequence = str;
        if (length > 20) {
            charSequenceSubSequence = str.subSequence(0, 20);
        }
        sb.append((Object) charSequenceSubSequence);
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

    /* JADX WARN: Removed duplicated region for block: B:40:0x004b A[Catch: Exception -> 0x0047, TRY_LEAVE, TryCatch #4 {Exception -> 0x0047, blocks: (B:36:0x0043, B:40:0x004b), top: B:48:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0043 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean compareFile(String str, FileDescriptor fileDescriptor) throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        FileInputStream fileInputStream3 = null;
        try {
            fileInputStream2 = new FileInputStream(str);
            try {
                fileInputStream = new FileInputStream(fileDescriptor);
            } catch (FileNotFoundException e) {
                e = e;
                fileInputStream = null;
            } catch (Throwable th) {
                th = th;
                fileInputStream = null;
            }
        } catch (FileNotFoundException e2) {
            e = e2;
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
        }
        try {
            boolean zCompareFile = compareFile(fileInputStream2, fileInputStream);
            try {
                fileInputStream2.close();
                fileInputStream.close();
                return zCompareFile;
            } catch (Exception e3) {
                e3.printStackTrace();
                return zCompareFile;
            }
        } catch (FileNotFoundException e4) {
            e = e4;
            fileInputStream3 = fileInputStream2;
            try {
                e.printStackTrace();
                if (fileInputStream3 != null) {
                    try {
                        fileInputStream3.close();
                    } catch (Exception e5) {
                        e5.printStackTrace();
                        return false;
                    }
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                return false;
            } catch (Throwable th3) {
                th = th3;
                if (fileInputStream3 != null) {
                    try {
                        fileInputStream3.close();
                    } catch (Exception e6) {
                        e6.printStackTrace();
                        throw th;
                    }
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            fileInputStream3 = fileInputStream2;
            if (fileInputStream3 != null) {
            }
            if (fileInputStream != null) {
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0049 A[Catch: Exception -> 0x0045, TRY_LEAVE, TryCatch #3 {Exception -> 0x0045, blocks: (B:32:0x0041, B:36:0x0049), top: B:41:0x0041 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0041 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v14, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v6, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean compareFile(String str, String str2) throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        FileInputStream fileInputStream3 = null;
        try {
            try {
                fileInputStream2 = new FileInputStream(str);
                try {
                    fileInputStream = new FileInputStream(str2);
                } catch (FileNotFoundException e) {
                    e = e;
                    fileInputStream = null;
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = null;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (FileNotFoundException e3) {
            e = e3;
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
        }
        try {
            this = compareFile(fileInputStream2, fileInputStream);
            fileInputStream2.close();
            fileInputStream.close();
        } catch (FileNotFoundException e4) {
            e = e4;
            fileInputStream3 = fileInputStream2;
            try {
                e.printStackTrace();
                this = str.equals(str2);
                if (fileInputStream3 != null) {
                    fileInputStream3.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                return this;
            } catch (Throwable th3) {
                th = th3;
                if (fileInputStream3 != null) {
                    try {
                        fileInputStream3.close();
                    } catch (Exception e5) {
                        e5.printStackTrace();
                        throw th;
                    }
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            fileInputStream3 = fileInputStream2;
            if (fileInputStream3 != null) {
            }
            if (fileInputStream != null) {
            }
            throw th;
        }
        return this;
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
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_data", str);
            setContentUri(context.getContentResolver().insert(SemImageClipDataProvider.CONTENT_URI, contentValues));
        } catch (Exception e) {
            Log.e(TAG, "Exception occurs in insertContentUri because " + e.getMessage());
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void deleteContentUri(Context context, String str) {
        if (new File(this.mImagePath).exists()) {
            deleteContentUriInternal(context, this.mImagePath);
        }
    }
}
