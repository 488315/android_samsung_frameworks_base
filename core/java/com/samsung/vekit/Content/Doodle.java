package com.samsung.vekit.Content;

import android.util.Log;

import com.samsung.vekit.Common.Object.DoodleStroke;
import com.samsung.vekit.Common.Type.ContentColorType;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Panel.Panel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class Doodle extends Content {
    private Panel capturedImagePanel;
    private String capturedImagePath;
    private int capturedStrokeCount;
    private boolean hasCapturedMask;
    private ArrayList<DoodleStroke> strokeList;

    public Doodle(VEContext context, int id, String name) {
        super(context, ContentType.DOODLE, id, name);
        this.capturedImagePanel = new Panel();
        this.capturedStrokeCount = 0;
        this.hasCapturedMask = true;
        this.strokeList = new ArrayList<>();
    }

    public Doodle setStrokeList(ArrayList<DoodleStroke> strokeList) {
        this.strokeList.clear();
        this.strokeList.addAll(strokeList);
        Log.d(this.TAG, "setStrokeList size : " + strokeList.size());
        return this;
    }

    public void clearStrokeList() {
        this.strokeList.clear();
        Log.d(this.TAG, "clearStrokeList size : " + this.strokeList.size());
    }

    public Doodle addStroke(DoodleStroke stroke) {
        this.strokeList.add(stroke);
        Log.d(this.TAG, "addStroke size : " + this.strokeList.size());
        return this;
    }

    public List<DoodleStroke> getStrokeList() {
        return Collections.unmodifiableList(this.strokeList);
    }

    public Doodle addStrokeList(ArrayList<DoodleStroke> strokeList) {
        this.strokeList.addAll(strokeList);
        Log.d(this.TAG, "addStrokeList size : " + strokeList.size());
        return this;
    }

    public Doodle removeStroke(int index) {
        if (index < 0 || index >= this.strokeList.size()) {
            Log.e(this.TAG, "strokeIndex is invalid - index : " + index);
            return this;
        }
        this.strokeList.remove(index);
        Log.d(this.TAG, "removeStroke size : " + this.strokeList.size());
        return this;
    }

    public Doodle removeStroke(DoodleStroke stroke) {
        this.strokeList.remove(stroke);
        Log.d(this.TAG, "removeStroke size : " + this.strokeList.size());
        return this;
    }

    @Override // com.samsung.vekit.Content.Content
    public Doodle setWidth(int width) {
        return (Doodle) super.setWidth(width);
    }

    @Override // com.samsung.vekit.Content.Content
    public Doodle setHeight(int height) {
        return (Doodle) super.setHeight(height);
    }

    @Override // com.samsung.vekit.Content.Content
    public Doodle setDuration(long duration) {
        return (Doodle) super.setDuration(duration);
    }

    public void setCapturedImagePath(String capturedImagePath) {
        this.capturedImagePath = capturedImagePath;
    }

    public boolean isHasCapturedMask() {
        return this.hasCapturedMask;
    }

    public void setHasCapturedMask(boolean hasCapturedMask) {
        this.hasCapturedMask = hasCapturedMask;
    }

    public Doodle setCapturedStrokeCount(int count) {
        this.capturedStrokeCount = count;
        return this;
    }

    public int getCapturedStrokeCount() {
        return this.capturedStrokeCount;
    }

    public Panel getCapturedImagePanel() {
        return this.capturedImagePanel;
    }

    public Doodle setCapturedImagePanel(Panel panel) {
        this.capturedImagePanel = panel;
        return this;
    }

    public String getCapturedImagePath() {
        return this.capturedImagePath;
    }

    public Doodle setCapturedImageInfo(String path, int width, int height, int savedStrokeSize) {
        Log.d(this.TAG, "setCapturedImageInfo(Legacy)");
        this.capturedImagePath = path;
        this.width = width;
        this.height = height;
        this.capturedStrokeCount = savedStrokeSize;
        this.colorType = ContentColorType.SDR;
        this.hasCapturedMask = false;
        if (this.context.getLayerGroup() != null) {
            this.capturedImagePanel = this.context.getLayerGroup().getPanel();
        }
        return this;
    }

    public Doodle setCapturedImageInfo(
            String path,
            int width,
            int height,
            int savedStrokeSize,
            ContentColorType colorType,
            boolean hasCapturedMask) {
        Log.d(this.TAG, "setCapturedImageInfo");
        this.capturedImagePath = path;
        this.width = width;
        this.height = height;
        this.capturedStrokeCount = savedStrokeSize;
        this.colorType = colorType;
        this.hasCapturedMask = hasCapturedMask;
        if (this.context.getLayerGroup() != null) {
            this.capturedImagePanel = this.context.getLayerGroup().getPanel();
        }
        return this;
    }
}
