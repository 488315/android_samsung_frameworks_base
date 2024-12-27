package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.PVFocusType;

public class PVKeyFrame {
    long duration;
    int endFrameNum;
    long endTime;
    int id;
    int mainObjectId;
    int startFrameNum;
    long startTime;
    PVFocusType type;

    public PVKeyFrame(
            long startTime,
            long endTime,
            long duration,
            int mainObjectId,
            PVFocusType type,
            int id,
            int startFrameNum,
            int endFrameNum) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.mainObjectId = mainObjectId;
        this.type = type;
        this.id = id;
        this.startFrameNum = startFrameNum;
        this.endFrameNum = endFrameNum;
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

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getMainObjectId() {
        return this.mainObjectId;
    }

    public void setMainObjectId(int mainObjectId) {
        this.mainObjectId = mainObjectId;
    }

    public PVFocusType getType() {
        return this.type;
    }

    public void setType(PVFocusType type) {
        this.type = type;
    }

    public int getStartFrameNum() {
        return this.startFrameNum;
    }

    public void setStartFrameNum(int startFrameId) {
        this.startFrameNum = this.startFrameNum;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEndFrameNum() {
        return this.endFrameNum;
    }

    public void setEndFrameNum(int endFrameId) {
        this.endFrameNum = this.endFrameNum;
    }
}
