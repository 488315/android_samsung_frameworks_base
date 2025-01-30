package com.samsung.android.media;

import android.content.res.AssetFileDescriptor;
import android.os.Parcel;
import java.io.FileDescriptor;
import java.util.ArrayList;

/* loaded from: classes5.dex */
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
    Parcel writeToParcel = super.writeToParcel(str);
    writeToParcel.writeInt(1);
    writeToParcel.writeInt(this.mBodyCycle);
    writeToParcel.writeInt(this.mLastIndex);
    writeToParcel.writeInt(this.mEndOutro ? 1 : 0);
    return writeToParcel;
  }

  public void setIntro(FileDescriptor fd, int startTime, int endTime) {
    if (this.mFBGMIntro == null) {
      this.mFBGMIntro = new SemBackgroundMusic.BGMInfo();
    }
    this.mFBGMIntro = super.addInfo(this.mFBGMIntro, fd, startTime, endTime);
  }

  public void setIntro(AssetFileDescriptor afd, int startTime, int endTime) {
    if (this.mFBGMIntro == null) {
      this.mFBGMIntro = new SemBackgroundMusic.BGMInfo();
    }
    this.mFBGMIntro = super.addInfo(this.mFBGMIntro, afd, startTime, endTime);
  }

  public int addBody(FileDescriptor fd, int startTime, int endTime) {
    SemBackgroundMusic.BGMInfo bgmInfo = new SemBackgroundMusic.BGMInfo();
    this.mFBGMBody.add(super.addInfo(bgmInfo, fd, startTime, endTime));
    int i = this.mBodyCount + 1;
    this.mBodyCount = i;
    return i;
  }

  public int addBody(AssetFileDescriptor afd, int startTime, int endTime) {
    SemBackgroundMusic.BGMInfo bgmInfo = new SemBackgroundMusic.BGMInfo();
    this.mFBGMBody.add(super.addInfo(bgmInfo, afd, startTime, endTime));
    int i = this.mBodyCount + 1;
    this.mBodyCount = i;
    return i;
  }

  public void setOutro(FileDescriptor fd, int startTime, int endTime) {
    if (this.mFBGMOutro == null) {
      this.mFBGMOutro = new SemBackgroundMusic.BGMInfo();
    }
    this.mFBGMOutro = super.addInfo(this.mFBGMOutro, fd, startTime, endTime);
  }

  public void setOutro(AssetFileDescriptor afd, int startTime, int endTime) {
    if (this.mFBGMOutro == null) {
      this.mFBGMOutro = new SemBackgroundMusic.BGMInfo();
    }
    this.mFBGMOutro = super.addInfo(this.mFBGMOutro, afd, startTime, endTime);
  }

  public void setPlaybackRule(int bodyRepeatCount, int bodyLastIndex, boolean useOutro)
      throws IllegalArgumentException {
    if (bodyLastIndex > this.mBodyCount) {
      String msg =
          "bodyLastIndex "
              + bodyLastIndex
              + "is invalid; larger than BGM_SECTION_TYPE_BODY count "
              + this.mBodyCount;
      throw new IllegalArgumentException(msg);
    }
    this.mBodyCycle = bodyRepeatCount;
    this.mLastIndex = bodyLastIndex;
    this.mEndOutro = useOutro;
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
