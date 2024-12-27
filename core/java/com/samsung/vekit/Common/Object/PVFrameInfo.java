package com.samsung.vekit.Common.Object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class PVFrameInfo {
    int blurLevel;
    ArrayList<PVDetectionInfo> detectionInfoList;
    int deviceRoll;
    int focusX;
    int focusY;
    int mainObjectId;
    int objectCount;
    long timestamp;
    String version;

    public PVFrameInfo() {
        this.version = "";
        this.timestamp = 0L;
        this.deviceRoll = 0;
        this.focusX = 0;
        this.focusY = 0;
        this.objectCount = 0;
        this.mainObjectId = -1;
        this.blurLevel = 0;
        this.detectionInfoList = new ArrayList<>();
    }

    public PVFrameInfo(
            String version,
            long timestamp,
            int deviceRoll,
            int focusX,
            int focusY,
            int objectCount,
            int mainObjectId,
            int blurLevel,
            ArrayList<PVDetectionInfo> detectionInfoList) {
        this.version = version;
        this.timestamp = timestamp;
        this.deviceRoll = deviceRoll;
        this.focusX = focusX;
        this.focusY = focusY;
        this.objectCount = objectCount;
        this.mainObjectId = mainObjectId;
        this.blurLevel = blurLevel;
        this.detectionInfoList = detectionInfoList;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getDeviceRoll() {
        return this.deviceRoll;
    }

    public void setDeviceRoll(int deviceRoll) {
        this.deviceRoll = deviceRoll;
    }

    public int getFocusX() {
        return this.focusX;
    }

    public void setFocusX(int focusX) {
        this.focusX = focusX;
    }

    public int getFocusY() {
        return this.focusY;
    }

    public void setFocusY(int focusY) {
        this.focusY = focusY;
    }

    public int getObjectCount() {
        return this.objectCount;
    }

    public void setObjectCount(int objectCount) {
        this.objectCount = objectCount;
    }

    public int getMainObjectId() {
        return this.mainObjectId;
    }

    public void setMainObjectId(int mainObjectId) {
        this.mainObjectId = mainObjectId;
    }

    public int getBlurLevel() {
        return this.blurLevel;
    }

    public void setBlurLevel(int blurLevel) {
        this.blurLevel = blurLevel;
    }

    public List<PVDetectionInfo> getDetectionInfoList() {
        return Collections.unmodifiableList(this.detectionInfoList);
    }

    public void setDetectionInfoList(ArrayList<PVDetectionInfo> detectionInfoList) {
        this.detectionInfoList = detectionInfoList;
    }

    public PVDetectionInfo findDetectionInfo(int id) {
        Iterator<PVDetectionInfo> it = this.detectionInfoList.iterator();
        while (it.hasNext()) {
            PVDetectionInfo info = it.next();
            if (info.id == id) {
                return info;
            }
        }
        return null;
    }
}
