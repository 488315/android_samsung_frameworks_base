package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.PVObjectType;

public class PVDetectionInfo {
    int angles;
    int bottom;
    int id;
    int left;
    PVObjectType objectType;
    int right;
    int top;

    public PVDetectionInfo(
            int left, int top, int right, int bottom, int angles, int id, PVObjectType objectType) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.angles = angles;
        this.id = id;
        this.objectType = objectType;
    }

    public PVDetectionInfo() {
        this.left = 0;
        this.top = 0;
        this.right = 0;
        this.bottom = 0;
        this.angles = 0;
        this.id = -1;
        this.objectType = PVObjectType.FACE;
    }

    public int getAngles() {
        return this.angles;
    }

    public void setAngles(int angles) {
        this.angles = angles;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PVObjectType getObjectType() {
        return this.objectType;
    }

    public void setObjectType(PVObjectType objectType) {
        this.objectType = objectType;
    }

    public int getLeft() {
        return this.left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return this.top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return this.right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return this.bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }
}
