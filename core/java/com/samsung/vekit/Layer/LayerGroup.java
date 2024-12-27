package com.samsung.vekit.Layer;

import android.util.Log;

import com.samsung.vekit.Animation.Animation;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.FrameworkType;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.Type.LayerType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Interface.HierarchyInterface;
import com.samsung.vekit.Item.FragmentAudioItem;
import com.samsung.vekit.Item.Item;
import com.samsung.vekit.Panel.Panel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LayerGroup extends Element implements HierarchyInterface<Layer> {
    protected ArrayList<Animation<?>> animationList;
    protected boolean isVisible;
    ArrayList<Layer> layerList;
    Panel panel;
    long totalDuration;

    public LayerGroup(VEContext context) {
        super(context, ElementType.LAYERGROUP, 0, "LayerGroup");
        this.layerList = new ArrayList<>();
        this.isVisible = true;
        this.totalDuration = 0L;
        this.TAG = getClass().getSimpleName();
        this.panel = new Panel();
        this.animationList = new ArrayList<>();
    }

    public long getTotalDuration() {
        return this.totalDuration;
    }

    public long calculateTotalDuration() {
        this.totalDuration = 0L;
        Iterator<Layer> it = this.layerList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Layer layer = it.next();
            if (this.context.getFrameworkType() == FrameworkType.SINGLE) {
                if (layer.getLayerType() == LayerType.MEDIA) {
                    for (Item item : layer.getChildren()) {
                        this.totalDuration += item.getDuration() + item.getPadding();
                    }
                }
            } else {
                long layerDuration = 0;
                for (Item item2 : layer.getChildren()) {
                    if (!item2.getItemType().equals(ItemType.FRAGMENT_AUDIO)
                            || !((FragmentAudioItem) item2).isEnableAutoDuration()) {
                        layerDuration += item2.getDuration() + item2.getPadding();
                    }
                }
                Log.i(
                        this.TAG,
                        "total duration : "
                                + this.totalDuration
                                + "layerDuration : "
                                + layerDuration);
                this.totalDuration = Math.max(this.totalDuration, layerDuration);
            }
        }
        Log.i(
                this.TAG,
                "Final total duration : "
                        + this.totalDuration
                        + ", FrameworkMode : "
                        + this.context.getFrameworkType().toString());
        calculateAutoDurationItems();
        return this.totalDuration;
    }

    public LayerGroup setTotalDuration(long totalDuration) {
        this.totalDuration = totalDuration;
        return this;
    }

    private void calculateAutoDurationItems() {
        Iterator<Layer> it = this.layerList.iterator();
        while (it.hasNext()) {
            Layer layer = it.next();
            if (layer.getLayerType().equals(LayerType.AUDIO)) {
                for (Item item : layer.getChildren()) {
                    if (item.getItemType().equals(ItemType.FRAGMENT_AUDIO)
                            && ((FragmentAudioItem) item).isEnableAutoDuration()) {
                        ((FragmentAudioItem) item).setDuration((int) this.totalDuration).update();
                    }
                }
            }
        }
    }

    @Override // com.samsung.vekit.Common.Object.Element
    public Panel getPanel() {
        return this.panel;
    }

    public LayerGroup setPanel(Panel panel) {
        this.panel = panel.m9389clone();
        return this;
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void attach(Layer element) {
        this.layerList.add(element);
        this.context.getNativeInterface().attach(this, element.getId());
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void attach(Layer element, int index) {
        this.layerList.add(index, element);
        this.context.getNativeInterface().attach(this, index, element.getId());
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void attach(ArrayList<Layer> list) {
        this.layerList.addAll(list);
        ArrayList<Integer> idList = new ArrayList<>();
        Iterator<Layer> it = list.iterator();
        while (it.hasNext()) {
            Layer layer = it.next();
            idList.add(Integer.valueOf(layer.getId()));
        }
        this.context.getNativeInterface().attach(this, idList);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void detach(Layer element) {
        this.context.getNativeInterface().detach(this, element.getId());
        this.layerList.remove(element);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void detach(int index) {
        Layer layer = this.layerList.get(index);
        detach(layer);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void clear() {
        this.context.getNativeInterface().clear(this);
        this.layerList.clear();
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public boolean isEmpty() {
        return this.layerList.isEmpty();
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public int getChildSize() {
        return this.layerList.size();
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public List<Layer> getChildren() {
        return Collections.unmodifiableList(this.layerList);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public boolean contains(Layer element) {
        return this.layerList.contains(element);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public int getIndex(Layer element) {
        return this.layerList.indexOf(element);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public Layer getChild(int index) {
        return this.layerList.get(index);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void swap(Layer from, Layer to) {
        int fromIndex = this.layerList.indexOf(from);
        int toIndex = this.layerList.indexOf(to);
        Collections.swap(this.layerList, fromIndex, toIndex);
        this.context.getNativeInterface().swap(this, fromIndex, toIndex);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
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

    @Override // com.samsung.vekit.Interface.HierarchyInterface
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

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public Animation<?> getAnimation(int index) {
        if (this.animationList.size() <= index || index < 0) {
            Log.e(this.TAG, "failed to get animation (invalid index)");
            return null;
        }
        return this.animationList.get(index);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
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

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public int getAnimationIndex(Animation<?> animation) {
        if (animation == null) {
            Log.e(this.TAG, "failed to getAnimationIndex (animation is null)");
            return -1;
        }
        return this.animationList.indexOf(animation);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public List<Animation<?>> getAnimationList() {
        return Collections.unmodifiableList(this.animationList);
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    public void checkValidAnimation(Animation animation) throws Exception {
        boolean valid = animation.getAnimationType() != AnimationType.TRANSITION;
        if (!valid) {
            throw new Exception(
                    "isInvalidElement : please attach correct uiAnimation(not TransitionAnimation)"
                        + " to LayerGroup.");
        }
    }
}
