package com.samsung.vekit.Item;

import android.graphics.Bitmap;
import android.util.Log;
import com.samsung.vekit.Common.Object.DoodlePoint;
import com.samsung.vekit.Common.Object.DoodleStroke;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.DoodleType;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Content.Doodle;
import com.samsung.vekit.Layer.Layer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class DoodleItem extends Item {
    private int currentStrokeIndex;
    private DoodleType doodleType;
    private boolean isDrawing;
    private ArrayList<DoodleStroke> strokeList;

    @Override // com.samsung.vekit.Item.Item
    public void checkValidContent(Content content) throws Exception {
        if (content.getContentType() != ContentType.DOODLE) {
            throw new Exception("isInvalidElement : please set doodle(content).");
        }
    }

    public DoodleItem(VEContext context, int id, String name) {
        super(context, ItemType.DOODLE, id, name);
        this.isDrawing = false;
        this.strokeList = new ArrayList<>();
        this.currentStrokeIndex = -1;
        this.doodleType = DoodleType.EDIT;
    }

    public DoodleType getDoodleType() {
        return this.doodleType;
    }

    public DoodleItem setDoodleType(DoodleType doodleType) {
        this.doodleType = doodleType;
        return this;
    }

    public boolean isDrawing() {
        return this.isDrawing;
    }

    public DoodleStroke getCurrentDoodleStroke() {
        int i = this.currentStrokeIndex;
        if (i < 0 || i >= this.strokeList.size()) {
            Log.m96e(this.TAG, "strokeIndex is invalid - index : " + this.currentStrokeIndex);
            return null;
        }
        return this.strokeList.get(this.currentStrokeIndex);
    }

    public int getCurrentStrokeIndex() {
        return this.currentStrokeIndex;
    }

    public List<DoodleStroke> getStrokeList() {
        return Collections.unmodifiableList(this.strokeList);
    }

    public int getStrokeListSize() {
        return Collections.unmodifiableList(this.strokeList).size();
    }

    public DoodleItem clearStrokeList() {
        this.strokeList.clear();
        this.currentStrokeIndex = -1;
        Log.m94d(this.TAG, "clearStrokeList currentStrokeIndex : " + this.currentStrokeIndex);
        return this;
    }

    public void attachStroke(DoodleStroke stroke) {
        this.strokeList.add(stroke);
        this.currentStrokeIndex++;
        Log.m94d(this.TAG, "attachStroke currentStrokeIndex : " + this.currentStrokeIndex);
        this.context.getNativeInterface().attachStroke(this, stroke);
    }

    public void detachStroke() {
        if (this.strokeList.isEmpty()) {
            Log.m96e(this.TAG, "strokeList is empty");
            return;
        }
        this.strokeList.remove(r0.size() - 1);
        this.currentStrokeIndex--;
        Log.m94d(this.TAG, "detachStroke stroke size : " + this.strokeList.size());
        this.context.getNativeInterface().detachStroke(this);
    }

    public void startDoodle(DoodleStroke doodleStroke) {
        this.strokeList.add(doodleStroke);
        this.currentStrokeIndex++;
        Log.m94d(this.TAG, "startDoodle currentStrokeIndex : " + this.currentStrokeIndex);
        this.isDrawing = true;
        this.context.getNativeInterface().startDoodle(this, doodleStroke);
    }

    public void drawDoodle(ArrayList<DoodlePoint> pointList) {
        if (!this.isDrawing) {
            Log.m96e(this.TAG, "isDrawing is false");
        }
        int i = this.currentStrokeIndex;
        if (i < 0 || i >= this.strokeList.size()) {
            Log.m96e(this.TAG, "currentStrokeIndex is invalid : " + this.currentStrokeIndex + " Size : " + this.strokeList.size());
            return;
        }
        Log.m94d(this.TAG, "currentStrokeIndex : " + this.currentStrokeIndex);
        this.strokeList.get(this.currentStrokeIndex).addDoodlePointList(pointList);
        this.context.getNativeInterface().drawDoodle(this, pointList);
    }

    public void finishDoodle() {
        this.isDrawing = false;
        this.context.getNativeInterface().finishDoodle(this);
    }

    public void saveDoodle() {
        if (this.content == null) {
            Log.m96e(this.TAG, "doodle content is null");
        } else {
            if (this.strokeList.isEmpty()) {
                return;
            }
            ((Doodle) this.content).clearStrokeList();
            ((Doodle) this.content).addStrokeList(this.strokeList);
            Log.m94d(this.TAG, "saveDoodle size : " + this.strokeList.size());
            this.context.getNativeInterface().saveDoodle(this);
        }
    }

    public void loadDoodle() {
        if (this.content == null) {
            Log.m96e(this.TAG, "doodle content is null");
            return;
        }
        this.strokeList.clear();
        this.strokeList.addAll(((Doodle) this.content).getStrokeList());
        this.currentStrokeIndex = this.strokeList.size() - 1;
        Log.m94d(this.TAG, "loadDoodle size : " + this.currentStrokeIndex);
        this.context.getNativeInterface().loadDoodle(this);
    }

    public Bitmap captureStaticDoodle(int width, int height) {
        return this.context.getNativeInterface().captureStaticDoodle(this, width, height);
    }

    @Override // com.samsung.vekit.Item.Item
    public DoodleItem setParent(Layer parent) {
        return (DoodleItem) super.setParent(parent);
    }

    @Override // com.samsung.vekit.Item.Item
    public DoodleItem setContent(Content content) {
        try {
            checkValidContent(content);
            return (DoodleItem) super.setContent(content);
        } catch (Exception e) {
            Log.m97e(this.TAG, "setContent: ", e);
            return this;
        }
    }

    @Override // com.samsung.vekit.Item.Item
    public DoodleItem setPadding(long padding) {
        return (DoodleItem) super.setPadding(padding);
    }

    @Override // com.samsung.vekit.Item.Item
    public DoodleItem setDuration(long duration) {
        return (DoodleItem) super.setDuration(duration);
    }
}
