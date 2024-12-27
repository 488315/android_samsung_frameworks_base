package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.PenType;
import com.samsung.vekit.Common.Type.StrokeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DoodleStroke {
    private boolean isAuto;
    private int mosaicStrength;
    private String patternURI;
    private PenType penType;
    private final ArrayList<DoodlePoint> pointList;
    private int strokeColor;
    private int strokeSize;
    private StrokeType strokeType;

    public DoodleStroke(int strokeColor, int strokeSize, PenType penType, boolean isAuto) {
        this.strokeType = StrokeType.NORMAL;
        this.strokeColor = strokeColor;
        this.strokeSize = strokeSize;
        this.penType = penType;
        this.mosaicStrength = 0;
        this.pointList = new ArrayList<>();
        this.isAuto = isAuto;
    }

    public DoodleStroke(DoodleStroke doodleStroke) {
        this.strokeType = StrokeType.NORMAL;
        this.strokeColor = doodleStroke.strokeColor;
        this.strokeSize = doodleStroke.strokeSize;
        this.strokeType = doodleStroke.strokeType;
        this.penType = doodleStroke.penType;
        this.mosaicStrength = doodleStroke.mosaicStrength;
        this.patternURI = doodleStroke.patternURI;
        this.isAuto = doodleStroke.isAuto;
        this.pointList = new ArrayList<>();
        if (doodleStroke.pointList != null) {
            this.pointList.addAll(doodleStroke.pointList);
        }
    }

    public void clear() {
        this.pointList.clear();
    }

    public PenType getPenType() {
        return this.penType;
    }

    public boolean isAuto() {
        return this.isAuto;
    }

    public DoodleStroke setPenType(PenType penType) {
        this.penType = penType;
        return this;
    }

    public StrokeType getStrokeType() {
        return this.strokeType;
    }

    public DoodleStroke setStrokeType(StrokeType strokeType) {
        this.strokeType = strokeType;
        return this;
    }

    public int getStrokeColor() {
        return this.strokeColor;
    }

    public DoodleStroke setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        return this;
    }

    public String getPatternURI() {
        return this.patternURI;
    }

    public DoodleStroke setPatternURI(String patternURI) {
        this.patternURI = patternURI;
        return this;
    }

    public int getMosaicStrength() {
        return this.mosaicStrength;
    }

    public DoodleStroke setMosaicStrength(int mosaicStrength) {
        this.mosaicStrength = mosaicStrength;
        return this;
    }

    public int getStrokeSize() {
        return this.strokeSize;
    }

    public DoodleStroke setStrokeSize(int strokeSize) {
        this.strokeSize = strokeSize;
        return this;
    }

    public void addDoodlePoint(DoodlePoint doodlePoint) {
        this.pointList.add(doodlePoint);
    }

    public void addDoodlePointList(ArrayList<DoodlePoint> doodlePointList) {
        this.pointList.addAll(doodlePointList);
    }

    public List<DoodlePoint> getDoodlePointList() {
        return Collections.unmodifiableList(this.pointList);
    }
}
