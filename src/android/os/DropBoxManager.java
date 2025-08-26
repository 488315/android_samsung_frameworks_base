package android.os;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Log;
import com.android.internal.os.IDropBoxManagerService;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

/* loaded from: classes3.dex */
public class DropBoxManager {
    public static final String ACTION_DROPBOX_ENTRY_ADDED = "android.intent.action.DROPBOX_ENTRY_ADDED";
    public static final String EXTRA_DROPPED_COUNT = "android.os.extra.DROPPED_COUNT";
    public static final String EXTRA_TAG = "tag";
    public static final String EXTRA_TIME = "time";
    private static final int HAS_BYTE_ARRAY = 8;
    public static final int IS_EMPTY = 1;
    public static final int IS_GZIPPED = 4;
    public static final int IS_TEXT = 2;
    private static final String TAG = "DropBoxManager";
    private final Context mContext;
    private final IDropBoxManagerService mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public static class Entry implements Parcelable, Closeable {
        public static final Parcelable.Creator<Entry> CREATOR = new Parcelable.Creator() { // from class: android.os.DropBoxManager.Entry.1
            @Override // android.os.Parcelable.Creator
            public Entry[] newArray(int i) {
                return new Entry[i];
            }

            @Override // android.os.Parcelable.Creator
            public Entry createFromParcel(Parcel parcel) {
                String string = parcel.readString();
                long j = parcel.readLong();
                int i = parcel.readInt();
                if ((i & 8) != 0) {
                    return new Entry(string, j, parcel.createByteArray(), i & (-9));
                }
                return new Entry(string, j, ParcelFileDescriptor.CREATOR.createFromParcel(parcel), i);
            }
        };
        private final byte[] mData;
        private final ParcelFileDescriptor mFileDescriptor;
        private final int mFlags;
        private final String mTag;
        private final long mTimeMillis;

        public Entry(String str, long j) {
            if (str == null) {
                throw new NullPointerException("tag == null");
            }
            this.mTag = str;
            this.mTimeMillis = j;
            this.mData = null;
            this.mFileDescriptor = null;
            this.mFlags = 1;
        }

        public Entry(String str, long j, String str2) {
            if (str == null) {
                throw new NullPointerException("tag == null");
            }
            if (str2 == null) {
                throw new NullPointerException("text == null");
            }
            this.mTag = str;
            this.mTimeMillis = j;
            this.mData = str2.getBytes(StandardCharsets.UTF_8);
            this.mFileDescriptor = null;
            this.mFlags = 2;
        }

        public Entry(String str, long j, byte[] bArr, int i) {
            if (str == null) {
                throw new NullPointerException("tag == null");
            }
            if (((i & 1) != 0) != (bArr == null)) {
                throw new IllegalArgumentException("Bad flags: " + i);
            }
            this.mTag = str;
            this.mTimeMillis = j;
            this.mData = bArr;
            this.mFileDescriptor = null;
            this.mFlags = i;
        }

        public Entry(String str, long j, ParcelFileDescriptor parcelFileDescriptor, int i) {
            if (str == null) {
                throw new NullPointerException("tag == null");
            }
            if (((i & 1) != 0) != (parcelFileDescriptor == null)) {
                throw new IllegalArgumentException("Bad flags: " + i);
            }
            this.mTag = str;
            this.mTimeMillis = j;
            this.mData = null;
            this.mFileDescriptor = parcelFileDescriptor;
            this.mFlags = i;
        }

        public Entry(String str, long j, File file, int i) throws IOException {
            if (str == null) {
                throw new NullPointerException("tag == null");
            }
            if ((i & 1) != 0) {
                throw new IllegalArgumentException("Bad flags: " + i);
            }
            this.mTag = str;
            this.mTimeMillis = j;
            this.mData = null;
            this.mFileDescriptor = ParcelFileDescriptor.open(file, 268435456);
            this.mFlags = i;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            try {
                ParcelFileDescriptor parcelFileDescriptor = this.mFileDescriptor;
                if (parcelFileDescriptor != null) {
                    parcelFileDescriptor.close();
                }
            } catch (IOException unused) {
            }
        }

        public String getTag() {
            return this.mTag;
        }

        public long getTimeMillis() {
            return this.mTimeMillis;
        }

        public int getFlags() {
            return this.mFlags & (-5);
        }

        public String getText(int i) throws Throwable {
            InputStream inputStream;
            InputStream inputStream2 = null;
            if ((this.mFlags & 2) == 0) {
                return null;
            }
            if (this.mData != null) {
                byte[] bArr = this.mData;
                return new String(bArr, 0, Math.min(i, bArr.length));
            }
            try {
                inputStream = getInputStream();
                if (inputStream == null) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused) {
                        }
                    }
                    return null;
                }
                try {
                    byte[] bArr2 = new byte[i];
                    int i2 = 0;
                    int i3 = 0;
                    while (i2 >= 0) {
                        i3 += i2;
                        if (i3 >= i) {
                            break;
                        }
                        i2 = inputStream.read(bArr2, i3, i - i3);
                    }
                    String str = new String(bArr2, 0, i3);
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    return str;
                } catch (IOException unused3) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused4) {
                        }
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    inputStream2 = inputStream;
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException unused5) {
                        }
                    }
                    throw th;
                }
            } catch (IOException unused6) {
                inputStream = null;
            } catch (Throwable th2) {
                th = th2;
            }
        }

        public InputStream getInputStream() throws IOException {
            InputStream autoCloseInputStream;
            if (this.mData != null) {
                autoCloseInputStream = new ByteArrayInputStream(this.mData);
            } else {
                if (this.mFileDescriptor == null) {
                    return null;
                }
                autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(this.mFileDescriptor);
            }
            return (this.mFlags & 4) != 0 ? new GZIPInputStream(new BufferedInputStream(autoCloseInputStream)) : autoCloseInputStream;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return this.mFileDescriptor != null ? 1 : 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mTag);
            parcel.writeLong(this.mTimeMillis);
            if (this.mFileDescriptor != null) {
                parcel.writeInt(this.mFlags & (-9));
                this.mFileDescriptor.writeToParcel(parcel, i);
            } else {
                parcel.writeInt(this.mFlags | 8);
                parcel.writeByteArray(this.mData);
            }
        }
    }

    public DropBoxManager(Context context, IDropBoxManagerService iDropBoxManagerService) {
        this.mContext = context;
        this.mService = iDropBoxManagerService;
    }

    protected DropBoxManager() {
        this.mContext = null;
        this.mService = null;
    }

    public void addText(String str, String str2) {
        addData(str, str2.getBytes(StandardCharsets.UTF_8), 2);
    }

    public void addData(String str, byte[] bArr, int i) {
        if (bArr == null) {
            throw new NullPointerException("data == null");
        }
        try {
            this.mService.addData(str, bArr, i);
        } catch (RemoteException e) {
            if ((e instanceof TransactionTooLargeException) && this.mContext.getApplicationInfo().targetSdkVersion < 24) {
                Log.e(TAG, "App sent too much data, so it was ignored", e);
                return;
            }
            throw e.rethrowFromSystemServer();
        }
    }

    public void addFile(String str, File file, int i) throws IOException {
        if (file == null) {
            throw new NullPointerException("file == null");
        }
        try {
            ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(file, 268435456);
            try {
                this.mService.addFile(str, parcelFileDescriptorOpen, i);
                if (parcelFileDescriptorOpen != null) {
                    parcelFileDescriptorOpen.close();
                }
            } finally {
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isTagEnabled(String str) {
        try {
            return this.mService.isTagEnabled(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Entry getNextEntry(String str, long j) {
        try {
            return this.mService.getNextEntryWithAttribution(str, j, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SecurityException e2) {
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 28) {
                throw e2;
            }
            Log.w(TAG, e2.getMessage());
            return null;
        }
    }
}
