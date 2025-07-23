package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.PVObjectType;

/* loaded from: classes6.dex */
public class PVDetectionInfo {
    int angles;
    int bottom;
    int id;
    int left;
    PVObjectType objectType;
    int right;
    int top;

    public PVDetectionInfo(int i, int i2, int i3, int i4, int i5, int i6, PVObjectType pVObjectType) {
        this.left = i;
        this.top = i2;
        this.right = i3;
        this.bottom = i4;
        this.angles = i5;
        this.id = i6;
        this.objectType = pVObjectType;
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

    public void setAngles(int i) {
        this.angles = i;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public PVObjectType getObjectType() {
        return this.objectType;
    }

    public void setObjectType(PVObjectType pVObjectType) {
        this.objectType = pVObjectType;
    }

    public int getLeft() {
        return this.left;
    }

    public void setLeft(int i) {
        this.left = i;
    }

    public int getTop() {
        return this.top;
    }

    public void setTop(int i) {
        this.top = i;
    }

    public int getRight() {
        return this.right;
    }

    public void setRight(int i) {
        this.right = i;
    }

    public int getBottom() {
        return this.bottom;
    }

    public void setBottom(int i) {
        this.bottom = i;
    }
}
