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

    public Doodle(VEContext vEContext, int i, String str) {
        super(vEContext, ContentType.DOODLE, i, str);
        this.capturedImagePanel = new Panel();
        this.capturedStrokeCount = 0;
        this.hasCapturedMask = true;
        this.strokeList = new ArrayList<>();
    }

    public Doodle setStrokeList(ArrayList<DoodleStroke> arrayList) {
        this.strokeList.clear();
        this.strokeList.addAll(arrayList);
        Log.d(this.TAG, "setStrokeList size : " + arrayList.size());
        return this;
    }

    public void clearStrokeList() {
        this.strokeList.clear();
        Log.d(this.TAG, "clearStrokeList size : " + this.strokeList.size());
    }

    public Doodle addStroke(DoodleStroke doodleStroke) {
        this.strokeList.add(doodleStroke);
        Log.d(this.TAG, "addStroke size : " + this.strokeList.size());
        return this;
    }

    public List<DoodleStroke> getStrokeList() {
        return Collections.unmodifiableList(this.strokeList);
    }

    public Doodle addStrokeList(ArrayList<DoodleStroke> arrayList) {
        this.strokeList.addAll(arrayList);
        Log.d(this.TAG, "addStrokeList size : " + arrayList.size());
        return this;
    }

    public Doodle removeStroke(int i) {
        if (i < 0 || i >= this.strokeList.size()) {
            Log.e(this.TAG, "strokeIndex is invalid - index : " + i);
            return this;
        }
        this.strokeList.remove(i);
        Log.d(this.TAG, "removeStroke size : " + this.strokeList.size());
        return this;
    }

    public Doodle removeStroke(DoodleStroke doodleStroke) {
        this.strokeList.remove(doodleStroke);
        Log.d(this.TAG, "removeStroke size : " + this.strokeList.size());
        return this;
    }

    @Override // com.samsung.vekit.Content.Content
    public Doodle setWidth(int i) {
        return (Doodle) super.setWidth(i);
    }

    @Override // com.samsung.vekit.Content.Content
    public Doodle setHeight(int i) {
        return (Doodle) super.setHeight(i);
    }

    @Override // com.samsung.vekit.Content.Content
    public Doodle setDuration(long j) {
        return (Doodle) super.setDuration(j);
    }

    public void setCapturedImagePath(String str) {
        this.capturedImagePath = str;
    }

    public boolean isHasCapturedMask() {
        return this.hasCapturedMask;
    }

    public void setHasCapturedMask(boolean z) {
        this.hasCapturedMask = z;
    }

    public Doodle setCapturedStrokeCount(int i) {
        this.capturedStrokeCount = i;
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

    public Doodle setCapturedImageInfo(String str, int i, int i2, int i3) {
        Log.d(this.TAG, "setCapturedImageInfo(Legacy)");
        this.capturedImagePath = str;
        this.width = i;
        this.height = i2;
        this.capturedStrokeCount = i3;
        this.colorType = ContentColorType.SDR;
        this.hasCapturedMask = false;
        if (this.context.getLayerGroup() != null) {
            this.capturedImagePanel = this.context.getLayerGroup().getPanel();
        }
        return this;
    }

    public Doodle setCapturedImageInfo(String str, int i, int i2, int i3, ContentColorType contentColorType, boolean z) {
        Log.d(this.TAG, "setCapturedImageInfo");
        this.capturedImagePath = str;
        this.width = i;
        this.height = i2;
        this.capturedStrokeCount = i3;
        this.colorType = contentColorType;
        this.hasCapturedMask = z;
        if (this.context.getLayerGroup() != null) {
            this.capturedImagePanel = this.context.getLayerGroup().getPanel();
        }
        return this;
    }
}
