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

/* loaded from: classes6.dex */
public class LayerGroup extends Element implements HierarchyInterface<Layer> {
    protected ArrayList<Animation<?>> animationList;
    protected boolean isVisible;
    ArrayList<Layer> layerList;
    Panel panel;
    long totalDuration;

    public LayerGroup(VEContext vEContext) {
        super(vEContext, ElementType.LAYERGROUP, 0, "LayerGroup");
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
            Layer next = it.next();
            if (this.context.getFrameworkType() == FrameworkType.SINGLE) {
                if (next.getLayerType() == LayerType.MEDIA) {
                    for (Item item : next.getChildren()) {
                        this.totalDuration += item.getDuration() + item.getPadding();
                    }
                }
            } else {
                long duration = 0;
                for (Item item2 : next.getChildren()) {
                    if (!item2.getItemType().equals(ItemType.FRAGMENT_AUDIO) || !((FragmentAudioItem) item2).isEnableAutoDuration()) {
                        duration += item2.getDuration() + item2.getPadding();
                    }
                }
                Log.i(this.TAG, "total duration : " + this.totalDuration + "layerDuration : " + duration);
                this.totalDuration = Math.max(this.totalDuration, duration);
            }
        }
        Log.i(this.TAG, "Final total duration : " + this.totalDuration + ", FrameworkMode : " + this.context.getFrameworkType().toString());
        calculateAutoDurationItems();
        return this.totalDuration;
    }

    public LayerGroup setTotalDuration(long j) {
        this.totalDuration = j;
        return this;
    }

    private void calculateAutoDurationItems() {
        Iterator<Layer> it = this.layerList.iterator();
        while (it.hasNext()) {
            Layer next = it.next();
            if (next.getLayerType().equals(LayerType.AUDIO)) {
                for (Item item : next.getChildren()) {
                    if (item.getItemType().equals(ItemType.FRAGMENT_AUDIO)) {
                        FragmentAudioItem fragmentAudioItem = (FragmentAudioItem) item;
                        if (fragmentAudioItem.isEnableAutoDuration()) {
                            fragmentAudioItem.setDuration((int) this.totalDuration).update();
                        }
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
        this.panel = panel.m9822clone();
        return this;
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void attach(Layer layer) {
        this.layerList.add(layer);
        this.context.getNativeInterface().attach(this, layer.getId());
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void attach(Layer layer, int i) {
        this.layerList.add(i, layer);
        this.context.getNativeInterface().attach(this, i, layer.getId());
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void attach(ArrayList<Layer> arrayList) {
        this.layerList.addAll(arrayList);
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        Iterator<Layer> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(Integer.valueOf(it.next().getId()));
        }
        this.context.getNativeInterface().attach(this, arrayList2);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void detach(Layer layer) {
        this.context.getNativeInterface().detach(this, layer.getId());
        this.layerList.remove(layer);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void detach(int i) {
        detach(this.layerList.get(i));
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
    public boolean contains(Layer layer) {
        return this.layerList.contains(layer);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public int getIndex(Layer layer) {
        return this.layerList.indexOf(layer);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public Layer getChild(int i) {
        return this.layerList.get(i);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void swap(Layer layer, Layer layer2) {
        int iIndexOf = this.layerList.indexOf(layer);
        int iIndexOf2 = this.layerList.indexOf(layer2);
        Collections.swap(this.layerList, iIndexOf, iIndexOf2);
        this.context.getNativeInterface().swap(this, iIndexOf, iIndexOf2);
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
    public Animation<?> getAnimation(int i) {
        if (this.animationList.size() <= i || i < 0) {
            Log.e(this.TAG, "failed to get animation (invalid index)");
            return null;
        }
        return this.animationList.get(i);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void clearAnimations() {
        Iterator<Animation<?>> it = this.animationList.iterator();
        while (it.hasNext()) {
            Animation<?> next = it.next();
            next.rollback();
            next.setTarget(null);
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

    public void setVisible(boolean z) {
        this.isVisible = z;
    }

    public void checkValidAnimation(Animation animation) throws Exception {
        if (animation.getAnimationType() == AnimationType.TRANSITION) {
            throw new Exception("isInvalidElement : please attach correct uiAnimation(not TransitionAnimation) to LayerGroup.");
        }
    }
}
