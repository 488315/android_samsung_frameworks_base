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

    public Parcel writeToParcel(String interfaceName) {
        Parcel p = Parcel.obtain();
        if (interfaceName != null) {
            p.writeInterfaceToken(interfaceName);
        }
        p.writeInt(this.mBGMInfos.size());
        Log.i(TAG, "BackgroundMusic size : " + this.mBGMInfos.size());
        for (int i = 0; i < this.mBGMInfos.size(); i++) {
            if (interfaceName != null) {
                try {
                    p.writeFileDescriptor(this.mBGMInfos.get(i).fd);
                } catch (IOException e) {
                    Log.i(TAG, "setBackgroundMusic ParcelFileDescriptor.dup failed");
                }
            } else {
                ParcelFileDescriptor pfd = ParcelFileDescriptor.dup(this.mBGMInfos.get(i).fd);
                p.writeInt(pfd.detachFd());
            }
            p.writeLong(this.mBGMInfos.get(i).offset);
            p.writeLong(this.mBGMInfos.get(i).length);
            p.writeInt(this.mBGMInfos.get(i).startTimeMs);
            p.writeInt(this.mBGMInfos.get(i).endTimeMs);
            p.writeInt(this.mBGMInfos.get(i).durationMs);
        }
        return p;
    }

    protected BGMInfo addInfo(BGMInfo bgmInfo, FileDescriptor fd, int startTime, int endTime) {
        bgmInfo.fd = fd;
        bgmInfo.offset = 0L;
        bgmInfo.length = 576460752303423487L;
        bgmInfo.startTimeMs = startTime;
        bgmInfo.endTimeMs = endTime;
        bgmInfo.durationMs = endTime - startTime;
        return bgmInfo;
    }

    protected BGMInfo addInfo(BGMInfo bgmInfo, AssetFileDescriptor afd, int startTime, int endTime) {
        bgmInfo.fd = afd.getFileDescriptor();
        bgmInfo.offset = afd.getStartOffset();
        bgmInfo.length = afd.getLength();
        bgmInfo.startTimeMs = startTime;
        bgmInfo.endTimeMs = endTime;
        bgmInfo.durationMs = endTime - startTime;
        return bgmInfo;
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
