package com.samsung.android.media;

import android.content.res.AssetFileDescriptor;
import com.samsung.android.media.SemBackgroundMusic;
import java.io.FileDescriptor;

/* loaded from: classes6.dex */
public class SemSingleBackgroundMusic extends SemBackgroundMusic {
    public void set(FileDescriptor fileDescriptor, int i, int i2) {
        this.mBGMInfos.add(super.addInfo(new SemBackgroundMusic.BGMInfo(), fileDescriptor, i, i2));
    }

    public void set(AssetFileDescriptor assetFileDescriptor, int i, int i2) {
        this.mBGMInfos.add(super.addInfo(new SemBackgroundMusic.BGMInfo(), assetFileDescriptor, i, i2));
    }
}
