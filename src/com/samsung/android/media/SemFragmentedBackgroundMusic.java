package com.samsung.android.media;

import android.content.res.AssetFileDescriptor;
import android.os.Parcel;
import com.samsung.android.media.SemBackgroundMusic;
import java.io.FileDescriptor;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class SemFragmentedBackgroundMusic extends SemBackgroundMusic {
    private static final int BGM_SECTION_TYPE_BODY = 1;
    private static final int BGM_SECTION_TYPE_INTRO = 0;
    private static final int BGM_SECTION_TYPE_OUTRO = 2;
    private SemBackgroundMusic.BGMInfo mFBGMIntro = null;
    private SemBackgroundMusic.BGMInfo mFBGMOutro = null;
    private ArrayList<SemBackgroundMusic.BGMInfo> mFBGMBody = new ArrayList<>();
    private int mBodyCount = 0;
    private int mBodyCycle = 0;
    private int mLastIndex = 0;
    private boolean mEndOutro = false;

    @Override // com.samsung.android.media.SemBackgroundMusic
    public void clear() {
        super.clear();
        this.mFBGMIntro = null;
        this.mFBGMBody.clear();
        this.mFBGMOutro = null;
        this.mBodyCount = 0;
        this.mBodyCycle = 0;
        this.mLastIndex = 0;
        this.mEndOutro = false;
    }

    @Override // com.samsung.android.media.SemBackgroundMusic
    public Parcel writeToParcel(String str) {
        addSections();
        Parcel parcelWriteToParcel = super.writeToParcel(str);
        parcelWriteToParcel.writeInt(1);
        parcelWriteToParcel.writeInt(this.mBodyCycle);
        parcelWriteToParcel.writeInt(this.mLastIndex);
        parcelWriteToParcel.writeInt(this.mEndOutro ? 1 : 0);
        return parcelWriteToParcel;
    }

    public void setIntro(FileDescriptor fileDescriptor, int i, int i2) {
        if (this.mFBGMIntro == null) {
            this.mFBGMIntro = new SemBackgroundMusic.BGMInfo();
        }
        this.mFBGMIntro = super.addInfo(this.mFBGMIntro, fileDescriptor, i, i2);
    }

    public void setIntro(AssetFileDescriptor assetFileDescriptor, int i, int i2) {
        if (this.mFBGMIntro == null) {
            this.mFBGMIntro = new SemBackgroundMusic.BGMInfo();
        }
        this.mFBGMIntro = super.addInfo(this.mFBGMIntro, assetFileDescriptor, i, i2);
    }

    public int addBody(FileDescriptor fileDescriptor, int i, int i2) {
        this.mFBGMBody.add(super.addInfo(new SemBackgroundMusic.BGMInfo(), fileDescriptor, i, i2));
        int i3 = this.mBodyCount + 1;
        this.mBodyCount = i3;
        return i3;
    }

    public int addBody(AssetFileDescriptor assetFileDescriptor, int i, int i2) {
        this.mFBGMBody.add(super.addInfo(new SemBackgroundMusic.BGMInfo(), assetFileDescriptor, i, i2));
        int i3 = this.mBodyCount + 1;
        this.mBodyCount = i3;
        return i3;
    }

    public void setOutro(FileDescriptor fileDescriptor, int i, int i2) {
        if (this.mFBGMOutro == null) {
            this.mFBGMOutro = new SemBackgroundMusic.BGMInfo();
        }
        this.mFBGMOutro = super.addInfo(this.mFBGMOutro, fileDescriptor, i, i2);
    }

    public void setOutro(AssetFileDescriptor assetFileDescriptor, int i, int i2) {
        if (this.mFBGMOutro == null) {
            this.mFBGMOutro = new SemBackgroundMusic.BGMInfo();
        }
        this.mFBGMOutro = super.addInfo(this.mFBGMOutro, assetFileDescriptor, i, i2);
    }

    public void setPlaybackRule(int i, int i2, boolean z) throws IllegalArgumentException {
        if (i2 > this.mBodyCount) {
            throw new IllegalArgumentException("bodyLastIndex " + i2 + "is invalid; larger than BGM_SECTION_TYPE_BODY count " + this.mBodyCount);
        }
        this.mBodyCycle = i;
        this.mLastIndex = i2;
        this.mEndOutro = z;
    }

    private void addSections() {
        if (this.mBGMInfos.size() > 0) {
            this.mBGMInfos.clear();
        }
        if (this.mFBGMIntro != null) {
            this.mBGMInfos.add(this.mFBGMIntro);
        }
        for (int i = 0; i < this.mFBGMBody.size(); i++) {
            this.mBGMInfos.add(this.mFBGMBody.get(i));
        }
        if (this.mFBGMOutro != null) {
            this.mBGMInfos.add(this.mFBGMOutro);
        }
    }
}
