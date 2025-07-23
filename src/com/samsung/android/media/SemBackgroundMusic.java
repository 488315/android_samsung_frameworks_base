package com.samsung.android.media;

import android.content.res.AssetFileDescriptor;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public abstract class SemBackgroundMusic {
    private static final String TAG = "SemBackgroundMusic";
    protected ArrayList<BGMInfo> mBGMInfos = new ArrayList<>();

    public void clear() {
        this.mBGMInfos.clear();
    }

    public Parcel writeToParcel(String str) {
        Parcel obtain = Parcel.obtain();
        if (str != null) {
            obtain.writeInterfaceToken(str);
        }
        obtain.writeInt(this.mBGMInfos.size());
        Log.i(TAG, "BackgroundMusic size : " + this.mBGMInfos.size());
        for (int i = 0; i < this.mBGMInfos.size(); i++) {
            if (str != null) {
                try {
                    obtain.writeFileDescriptor(this.mBGMInfos.get(i).fd);
                } catch (IOException unused) {
                    Log.i(TAG, "setBackgroundMusic ParcelFileDescriptor.dup failed");
                }
            } else {
                obtain.writeInt(ParcelFileDescriptor.dup(this.mBGMInfos.get(i).fd).detachFd());
            }
            obtain.writeLong(this.mBGMInfos.get(i).offset);
            obtain.writeLong(this.mBGMInfos.get(i).length);
            obtain.writeInt(this.mBGMInfos.get(i).startTimeMs);
            obtain.writeInt(this.mBGMInfos.get(i).endTimeMs);
            obtain.writeInt(this.mBGMInfos.get(i).durationMs);
        }
        return obtain;
    }

    protected BGMInfo addInfo(BGMInfo bGMInfo, FileDescriptor fileDescriptor, int i, int i2) {
        bGMInfo.fd = fileDescriptor;
        bGMInfo.offset = 0L;
        bGMInfo.length = 576460752303423487L;
        bGMInfo.startTimeMs = i;
        bGMInfo.endTimeMs = i2;
        bGMInfo.durationMs = i2 - i;
        return bGMInfo;
    }

    protected BGMInfo addInfo(BGMInfo bGMInfo, AssetFileDescriptor assetFileDescriptor, int i, int i2) {
        bGMInfo.fd = assetFileDescriptor.getFileDescriptor();
        bGMInfo.offset = assetFileDescriptor.getStartOffset();
        bGMInfo.length = assetFileDescriptor.getLength();
        bGMInfo.startTimeMs = i;
        bGMInfo.endTimeMs = i2;
        bGMInfo.durationMs = i2 - i;
        return bGMInfo;
    }

    protected static class BGMInfo {
        int durationMs;
        int endTimeMs;
        FileDescriptor fd;
        long length;
        long offset;
        int startTimeMs;

        protected BGMInfo() {
        }
    }
}
