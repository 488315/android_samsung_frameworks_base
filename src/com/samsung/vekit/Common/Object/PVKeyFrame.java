package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.PVFocusType;

/* loaded from: classes6.dex */
public class PVKeyFrame {
    long duration;
    int endFrameNum;
    long endTime;
    int id;
    int mainObjectId;
    int startFrameNum;
    long startTime;
    PVFocusType type;

    public PVKeyFrame(long j, long j2, long j3, int i, PVFocusType pVFocusType, int i2, int i3, int i4) {
        this.startTime = j;
        this.endTime = j2;
        this.duration = j3;
        this.mainObjectId = i;
        this.type = pVFocusType;
        this.id = i2;
        this.startFrameNum = i3;
        this.endFrameNum = i4;
    }

    public PVKeyFrame() {
        this.startTime = 0L;
        this.endTime = 0L;
        this.duration = 0L;
        this.mainObjectId = -1;
        this.type = PVFocusType.NONE;
        this.id = 0;
        this.startFrameNum = 0;
        this.endFrameNum = 0;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long j) {
        this.startTime = j;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long j) {
        this.endTime = j;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long j) {
        this.duration = j;
    }

    public int getMainObjectId() {
        return this.mainObjectId;
    }

    public void setMainObjectId(int i) {
        this.mainObjectId = i;
    }

    public PVFocusType getType() {
        return this.type;
    }

    public void setType(PVFocusType pVFocusType) {
        this.type = pVFocusType;
    }

    public int getStartFrameNum() {
        return this.startFrameNum;
    }

    public void setStartFrameNum(int i) {
        this.startFrameNum = this.startFrameNum;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getEndFrameNum() {
        return this.endFrameNum;
    }

    public void setEndFrameNum(int i) {
        this.endFrameNum = this.endFrameNum;
    }
}
