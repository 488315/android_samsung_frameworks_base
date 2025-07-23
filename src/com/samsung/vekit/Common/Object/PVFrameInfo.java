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

    public PVFrameInfo(String str, long j, int i, int i2, int i3, int i4, int i5, int i6, ArrayList<PVDetectionInfo> arrayList) {
        this.version = str;
        this.timestamp = j;
        this.deviceRoll = i;
        this.focusX = i2;
        this.focusY = i3;
        this.objectCount = i4;
        this.mainObjectId = i5;
        this.blurLevel = i6;
        this.detectionInfoList = arrayList;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }

    public int getDeviceRoll() {
        return this.deviceRoll;
    }

    public void setDeviceRoll(int i) {
        this.deviceRoll = i;
    }

    public int getFocusX() {
        return this.focusX;
    }

    public void setFocusX(int i) {
        this.focusX = i;
    }

    public int getFocusY() {
        return this.focusY;
    }

    public void setFocusY(int i) {
        this.focusY = i;
    }

    public int getObjectCount() {
        return this.objectCount;
    }

    public void setObjectCount(int i) {
        this.objectCount = i;
    }

    public int getMainObjectId() {
        return this.mainObjectId;
    }

    public void setMainObjectId(int i) {
        this.mainObjectId = i;
    }

    public int getBlurLevel() {
        return this.blurLevel;
    }

    public void setBlurLevel(int i) {
        this.blurLevel = i;
    }

    public List<PVDetectionInfo> getDetectionInfoList() {
        return Collections.unmodifiableList(this.detectionInfoList);
    }

    public void setDetectionInfoList(ArrayList<PVDetectionInfo> arrayList) {
        this.detectionInfoList = arrayList;
    }

    public PVDetectionInfo findDetectionInfo(int i) {
        Iterator<PVDetectionInfo> it = this.detectionInfoList.iterator();
        while (it.hasNext()) {
            PVDetectionInfo next = it.next();
            if (next.id == i) {
                return next;
            }
        }
        return null;
    }
}
