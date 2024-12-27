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

/* loaded from: classes5.dex */
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

    public SemImageClipData(Parcel source) {
        super(source);
        this.mImagePath = "";
        this.mContentUriString = "";
        this.mContentUri = null;
        this.mInitBaseValue = "";
        this.mInitBaseValueCheck = true;
        this.mExtraDataPath = "";
        this.mExtraParcelFd = null;
        readFromSource(source);
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean setAlternateClipData(int type, SemClipData altData) {
        boolean result = super.setAlternateClipData(type, altData);
        if (!result || this.mImagePath == null) {
            return false;
        }
        switch (type) {
            case 2:
                ((SemImageClipData) altData).setExtraParcelFileDescriptor(this.mExtraParcelFd);
                boolean result2 =
                        ((SemImageClipData) altData)
                                .setBitmapPath(getBitmapPath(), getExtraDataPath());
                return result2;
            default:
                return false;
        }
    }

    private void setClipData() {
        File file = new File(this.mImagePath);
        ClipData.Item item = new ClipData.Item(Uri.fromFile(file));
        try {
            String mimeType = Files.probeContentType(file.toPath());
            String[] mimeTypes = {mimeType};
            setClipData(mimeTypes, item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toSave() {
        if (this.mContentUri != null) {
            this.mContentUriString = this.mContentUri.toString();
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toLoad() {
        if (TextUtils.isEmpty(this.mContentUriString)) {
            this.mContentUri = null;
        } else {
            this.mContentUri = Uri.parse(this.mContentUriString);
        }
        if (this.mImagePath != null
                && this.mImagePath.contains(CompatabilityHelper.OLD_CLIPBOARD_ROOT_PATH)) {
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

    public boolean setImagePath(String FilePath) {
        if (FilePath == null || FilePath.length() < 1) {
            return false;
        }
        if (this.mInitBaseValueCheck) {
            this.mInitBaseValue = FilePath;
            this.mInitBaseValueCheck = false;
        }
        this.mImagePath = FilePath;
        File tempFile = new File(FilePath);
        if (tempFile.isFile()) {
            return true;
        }
        Log.secE(TAG, "ClipboardDataBitmap : value is no file path ..check plz");
        return false;
    }

    public boolean setExtraDataPath(String FilePath) {
        if (FilePath == null || FilePath.length() < 1) {
            return false;
        }
        this.mExtraDataPath = FilePath;
        File tempFile = new File(FilePath);
        if (tempFile.isFile()) {
            return true;
        }
        Log.secE(TAG, "ClipboardDataBitmap : ExtraDataPath is no file path ..check plz");
        return false;
    }

    public ParcelFileDescriptor getImageFileDescriptor() {
        return getParcelFileDescriptor();
    }

    public void setExtraParcelFileDescriptor(ParcelFileDescriptor pfd) {
        this.mExtraParcelFd = pfd;
    }

    public ParcelFileDescriptor getExtraParcelFileDescriptor() {
        if (this.mExtraParcelFd != null) {
            return this.mExtraParcelFd;
        }
        if (TextUtils.isEmpty(this.mExtraDataPath)) {
            return null;
        }
        File file = new File(this.mExtraDataPath);
        try {
            return ParcelFileDescriptor.open(file, 268435456);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getInitBasePath() {
        return this.mInitBaseValue;
    }

    public boolean hasExtraData() {
        return this.mExtraDataPath != null && this.mExtraDataPath.length() >= 1;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean equals(Object o) {
        Log.secI(TAG, "bitmap equals");
        if (!super.equals(o) || !(o instanceof SemImageClipData)) {
            return false;
        }
        SemImageClipData trgData = (SemImageClipData) o;
        String trgBmp = trgData.getBitmapPath();
        String trgInitBasePath = trgData.getInitBasePath();
        if (trgInitBasePath == null || trgInitBasePath.compareTo(this.mInitBaseValue) != 0) {
            return false;
        }
        ParcelFileDescriptor pfd = trgData.getParcelFileDescriptor();
        if (pfd != null) {
            if (!compareFile(this.mImagePath, pfd.getFileDescriptor())) {
                return false;
            }
            Log.secE(TAG, "bitmap equals");
            return true;
        }
        if (!compareFile(this.mImagePath, trgBmp)) {
            return false;
        }
        Log.secE(TAG, "bitmap equals");
        return true;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Log.secI(TAG, "Bitmap write to parcel");
        parcel.writeInt(2);
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mImagePath);
        if (this.mContentUri == null) {
            parcel.writeString("");
        } else {
            parcel.writeString(this.mContentUri.toString());
        }
        parcel.writeString(this.mInitBaseValue);
        parcel.writeByte(this.mInitBaseValueCheck ? (byte) 1 : (byte) 0);
        parcel.writeString(this.mExtraDataPath);
        parcel.writeParcelable(this.mExtraParcelFd, i);
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    protected void readFromSource(Parcel source) {
        try {
            this.mImagePath = source.readString();
            String contentUri = source.readString();
            if (TextUtils.isEmpty(contentUri)) {
                this.mContentUri = null;
            } else {
                this.mContentUri = Uri.parse(contentUri);
            }
            this.mInitBaseValue = source.readString();
            this.mInitBaseValueCheck = source.readByte() != 0;
            this.mExtraDataPath = source.readString();
            this.mExtraParcelFd =
                    (ParcelFileDescriptor)
                            source.readParcelable(ParcelFileDescriptor.class.getClassLoader());
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
        if (TextUtils.isEmpty(this.mImagePath)) {
            return null;
        }
        File file = new File(this.mImagePath);
        try {
            return ParcelFileDescriptor.open(file, 268435456);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        return "SemImageClipData class. Value is "
                + ((Object)
                        (this.mImagePath.length() > 20
                                ? this.mImagePath.subSequence(0, 20)
                                : this.mImagePath));
    }

    private boolean compareFile(FileInputStream src, FileInputStream dest) {
        Throwable th;
        boolean result;
        int compareSize = 128;
        boolean result2 = false;
        try {
            int srcSize = (int) src.getChannel().size();
            int destSize = (int) dest.getChannel().size();
            if (srcSize == destSize && srcSize >= 1) {
                if (destSize >= 1) {
                    int buffSize = srcSize <= 128 ? srcSize : 128;
                    int tmp = srcSize / buffSize;
                    int iCnt = 5;
                    if (tmp < 5) {
                        iCnt = tmp;
                    }
                    int offset = (srcSize - (buffSize * iCnt)) / iCnt;
                    int position = 0;
                    byte[] readSrcData = new byte[buffSize];
                    byte[] readDestData = new byte[buffSize];
                    try {
                        BufferedInputStream bisSrc = new BufferedInputStream(src);
                        try {
                            BufferedInputStream bisDest = new BufferedInputStream(dest);
                            int compareCount = 0;
                            while (compareCount < iCnt) {
                                int compareSize2 = compareSize;
                                try {
                                    bisSrc.read(readSrcData, 0, buffSize);
                                    bisDest.read(readDestData, 0, buffSize);
                                    position += buffSize + offset;
                                    result = result2;
                                } catch (IOException e) {
                                    e = e;
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                                try {
                                    bisSrc.skip(position);
                                    bisDest.skip(position);
                                    int i2 = 0;
                                    result2 = result;
                                    while (i2 < buffSize) {
                                        BufferedInputStream bisDest2 = bisDest;
                                        result2 = readSrcData[i2] == readDestData[i2];
                                        i2++;
                                        bisDest = bisDest2;
                                    }
                                    compareCount++;
                                    compareSize = compareSize2;
                                } catch (IOException e2) {
                                    e = e2;
                                    try {
                                        e.printStackTrace();
                                        try {
                                            try {
                                                src.close();
                                                dest.close();
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }
                                            return false;
                                        } finally {
                                        }
                                    } catch (Throwable th3) {
                                        th = th3;
                                        try {
                                            try {
                                                src.close();
                                                dest.close();
                                            } catch (IOException e12) {
                                                e12.printStackTrace();
                                                throw th;
                                            }
                                            throw th;
                                        } finally {
                                        }
                                    }
                                } catch (Throwable th4) {
                                    th = th4;
                                    src.close();
                                    dest.close();
                                    throw th;
                                }
                            }
                            boolean result3 = result2;
                            try {
                                try {
                                    src.close();
                                    dest.close();
                                } catch (IOException e13) {
                                    e13.printStackTrace();
                                }
                                return result3;
                            } finally {
                            }
                        } catch (IOException e3) {
                            e = e3;
                            e.printStackTrace();
                            src.close();
                            dest.close();
                            return false;
                        } catch (Throwable th5) {
                            th = th5;
                            th = th;
                            src.close();
                            dest.close();
                            throw th;
                        }
                    } catch (IOException e4) {
                        e = e4;
                        e.printStackTrace();
                        src.close();
                        dest.close();
                        return false;
                    } catch (Throwable th6) {
                        th = th6;
                        th = th;
                        src.close();
                        dest.close();
                        throw th;
                    }
                }
            }
            try {
                try {
                    src.close();
                    dest.close();
                } catch (IOException e14) {
                    e14.printStackTrace();
                }
                return false;
            } finally {
            }
        } catch (IOException e5) {
            e = e5;
        } catch (Throwable th7) {
            th = th7;
        }
    }

    private boolean compareFile(String src, FileDescriptor fd) {
        FileInputStream fisSrc = null;
        FileInputStream fisDest = null;
        boolean isSameFile = false;
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            try {
                fisSrc = new FileInputStream(src);
                fisDest = new FileInputStream(fd);
                isSameFile = compareFile(fisSrc, fisDest);
                fisSrc.close();
                fisDest.close();
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
                if (fisSrc != null) {
                    fisSrc.close();
                }
                if (fisDest != null) {
                    fisDest.close();
                }
            }
            return isSameFile;
        } catch (Throwable th) {
            if (fisSrc != null) {
                try {
                    fisSrc.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                    throw th;
                }
            }
            if (fisDest != null) {
                fisDest.close();
            }
            throw th;
        }
    }

    private boolean compareFile(String src, String dest) {
        boolean isSameFile;
        FileInputStream fisSrc = null;
        FileInputStream fisDest = null;
        try {
            try {
                fisSrc = new FileInputStream(src);
                fisDest = new FileInputStream(dest);
                isSameFile = compareFile(fisSrc, fisDest);
                try {
                    fisSrc.close();
                    fisDest.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
                boolean isSameFile2 = src.equals(dest);
                if (fisSrc != null) {
                    try {
                        fisSrc.close();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        isSameFile = isSameFile2;
                        return isSameFile;
                    }
                }
                if (fisDest != null) {
                    fisDest.close();
                }
                isSameFile = isSameFile2;
            }
            return isSameFile;
        } catch (Throwable th) {
            if (fisSrc != null) {
                try {
                    fisSrc.close();
                } catch (Exception e4) {
                    e4.printStackTrace();
                    throw th;
                }
            }
            if (fisDest != null) {
                fisDest.close();
            }
            throw th;
        }
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

    public boolean setBitmapPath(String FilePath, String ExtraDataPath) {
        Log.secI(TAG, "setBitmapPath");
        boolean result = false;
        if (FilePath == null || FilePath.length() < 1) {
            return false;
        }
        if (this.mInitBaseValueCheck) {
            this.mInitBaseValue = FilePath;
            this.mInitBaseValueCheck = false;
        }
        this.mImagePath = FilePath;
        if (ExtraDataPath != null && ExtraDataPath.length() > 0) {
            Log.secI(TAG, "ExtraDataPath =" + ExtraDataPath);
            this.mExtraDataPath = ExtraDataPath;
        }
        ParcelFileDescriptor fd = super.getParcelFileDescriptor();
        if (fd != null && fd.getFileDescriptor().valid()) {
            result = true;
            if (this.mExtraParcelFd != null && !this.mExtraParcelFd.getFileDescriptor().valid()) {
                this.mExtraParcelFd = null;
            }
        }
        return result;
    }

    public void setContentUri(Uri contentUri) {
        this.mContentUri = contentUri;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void convertForRemote() {
        String imageName = this.mImagePath.substring(this.mImagePath.lastIndexOf("/"));
        if (setImagePath(ClipboardConstants.CLIPBOARD_REMOTE_PATH + imageName)) {
            Log.d(TAG, "success converting");
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
                        context.getContentResolver()
                                .insert(SemImageClipDataProvider.CONTENT_URI, values);
                setContentUri(contentUri);
            } catch (Exception e) {
                Log.e(TAG, "Exception occurs in insertContentUri because " + e.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void deleteContentUri(Context context, String path) {
        File imageFile = new File(this.mImagePath);
        if (imageFile.exists()) {
            deleteContentUriInternal(context, this.mImagePath);
        }
    }
}
