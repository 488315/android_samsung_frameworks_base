package com.samsung.vekit.Item;

import android.util.Log;

import com.samsung.vekit.Animation.Animation;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.Filter;
import com.samsung.vekit.Common.Object.Region;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.ItemErrorType;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.Type.MeshType;
import com.samsung.vekit.Common.Type.ToneType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Layer.Layer;
import com.samsung.vekit.Listener.ItemStatusListener;
import com.samsung.vekit.Listener.PcmInfoListener;
import com.samsung.vekit.Panel.Panel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Item extends Element implements ItemStatusListener {
    protected ArrayList<Animation<?>> animationList;
    protected Content content;
    protected long duration;
    protected boolean isVisible;
    protected ItemType itemType;
    ItemStatusListener listener;
    protected MeshType meshType;
    protected long padding;
    protected Panel panel;
    protected Layer parent;
    protected ArrayList<Region> regionList;
    protected float speed;

    protected Item(VEContext context, ItemType type, int id, String name) {
        super(context, ElementType.ITEM, id, name);
        this.speed = 1.0f;
        this.meshType = MeshType.PLANE;
        this.isVisible = true;
        this.itemType = type;
        this.TAG = getClass().getSimpleName();
        this.panel = new Panel();
        this.animationList = new ArrayList<>();
        this.regionList = new ArrayList<>();
    }

    public void checkValidContent(Content content) throws Exception {}

    public Layer getParent() {
        return this.parent;
    }

    public Item setParent(Layer parent) {
        this.parent = parent;
        return this;
    }

    @Override // com.samsung.vekit.Common.Object.Element
    public Panel getPanel() {
        return this.panel;
    }

    public Item setPanel(Panel panel) {
        this.panel = panel.m9389clone();
        return this;
    }

    public Content getContent() {
        return this.content;
    }

    public Item setContent(Content content) {
        this.content = content;
        return this;
    }

    public long getPadding() {
        return this.padding;
    }

    public long getItemStartTime() {
        return this.padding;
    }

    public long getItemEndTime() {
        return this.padding + this.duration;
    }

    public Item setPadding(long padding) {
        this.padding = padding;
        return this;
    }

    public long getDuration() {
        return this.duration;
    }

    public Item setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public float getSpeed() {
        return this.speed;
    }

    public Item setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public Item addRegion(Region region) {
        this.regionList.add(region);
        return this;
    }

    public Item removeRegion(Region region) {
        this.regionList.remove(region);
        return this;
    }

    public Item clearRegions() {
        this.regionList.clear();
        return this;
    }

    private int getRegionIndex(Region region) {
        if (region == null) {
            Log.e(this.TAG, "failed to get Region Index");
            return -1;
        }
        return this.regionList.indexOf(region);
    }

    public List<Region> getRegionList() {
        return Collections.unmodifiableList(this.regionList);
    }

    public Region getRegion(int index) {
        if (this.regionList.size() <= index || index < 0) {
            Log.e(this.TAG, "failed to get region (invalid index) : " + index);
            return null;
        }
        return this.regionList.get(index);
    }

    public void attachAnimation(Animation<?> animation) {
        try {
            checkValidAnimation(animation);
            this.animationList.add(animation);
            animation.setTarget(this);
            this.context.getNativeInterface().attachAnimation(this, animation.getId());
        } catch (Exception e) {
            Log.e(this.TAG, "attachAnimation: ", e);
        }
    }

    public void detachAnimation(Animation<?> animation) {
        try {
            checkValidAnimation(animation);
            this.context.getNativeInterface().detachAnimation(this, animation.getId());
            this.animationList.remove(animation);
            animation.rollback();
            animation.setTarget(null);
        } catch (Exception e) {
            Log.e(this.TAG, "detachAnimation: ", e);
        }
    }

    public Animation<?> getAnimation(int index) {
        if (this.animationList.size() <= index || index < 0) {
            Log.e(this.TAG, "failed to get animation (invalid index)");
            return null;
        }
        return this.animationList.get(index);
    }

    public void clearAnimations() {
        Iterator<Animation<?>> it = this.animationList.iterator();
        while (it.hasNext()) {
            Animation animation = it.next();
            animation.rollback();
            animation.setTarget(null);
        }
        this.animationList.clear();
        this.context.getNativeInterface().clearAnimations(this);
    }

    private int getAnimationIndex(Animation<?> animation) {
        if (animation == null) {
            Log.e(this.TAG, "failed to getAnimationIndex (animation is null)");
            return -1;
        }
        return this.animationList.indexOf(animation);
    }

    public List<Animation<?>> getAnimationList() {
        return Collections.unmodifiableList(this.animationList);
    }

    public ItemType getItemType() {
        return this.itemType;
    }

    public long getAbsoluteStartTime() {
        Item item;
        if (this.parent == null) {
            return this.padding;
        }
        long startTime = this.padding;
        List<Item> itemList = this.parent.getChildren();
        Iterator<Item> it = itemList.iterator();
        while (it.hasNext() && (item = it.next()) != this) {
            startTime += item.getItemEndTime();
        }
        return startTime;
    }

    public long getAbsoluteEndTime() {
        return getAbsoluteStartTime() + this.duration;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public Item setVisible(boolean visible) {
        this.isVisible = visible;
        return this;
    }

    public Item setFilter(Filter filter) {
        return this;
    }

    public Item setFilterIntensity(float intensity) {
        return this;
    }

    public Filter getFilter() {
        return null;
    }

    public float getFilterIntensity() {
        return 0.0f;
    }

    public float getOpacity() {
        return 0.0f;
    }

    @Override // com.samsung.vekit.Common.Object.Element
    public Item setOpacity(float opacity) {
        return this;
    }

    public Item setToneIntensity(ToneType type, int intensity) {
        return this;
    }

    public int getToneIntensity(ToneType type) {
        return 0;
    }

    public void checkValidAnimation(Animation animation) throws Exception {
        Boolean valid = Boolean.valueOf(animation.getAnimationType() != AnimationType.TRANSITION);
        if (!valid.booleanValue()) {
            throw new Exception(
                    "isInvalidElement : please attach correct Animation(not TransitionAnimation) to"
                        + " Item.");
        }
    }

    @Override // com.samsung.vekit.Listener.ItemStatusListener
    public void onError(ItemErrorType errorType) {
        if (this.listener != null) {
            this.listener.onError(errorType);
        }
    }

    public void setStatusListener(ItemStatusListener listener) {
        this.listener = listener;
    }

    public PcmInfoListener getPcmInfoListener() {
        return null;
    }
}
