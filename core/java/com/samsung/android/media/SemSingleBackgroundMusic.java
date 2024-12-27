package com.samsung.android.media;

import android.content.res.AssetFileDescriptor;

import java.io.FileDescriptor;

public class SemSingleBackgroundMusic extends SemBackgroundMusic {
    public void set(FileDescriptor fd, int startTime, int endTime) {
        SemBackgroundMusic.BGMInfo bgmSingle = new SemBackgroundMusic.BGMInfo();
        this.mBGMInfos.add(super.addInfo(bgmSingle, fd, startTime, endTime));
    }

    public void set(AssetFileDescriptor afd, int startTime, int endTime) {
        SemBackgroundMusic.BGMInfo bgmSingle = new SemBackgroundMusic.BGMInfo();
        this.mBGMInfos.add(super.addInfo(bgmSingle, afd, startTime, endTime));
    }
}
