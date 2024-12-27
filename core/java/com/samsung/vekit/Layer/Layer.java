package com.samsung.vekit.Layer;

import android.util.Log;

import com.samsung.vekit.Animation.Animation;
import com.samsung.vekit.Animation.TransitionAnimation;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.Type.LayerType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Interface.HierarchyInterface;
import com.samsung.vekit.Item.Item;
import com.samsung.vekit.Panel.Panel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class Layer extends Element implements HierarchyInterface<Item> {
    protected ArrayList<Animation<?>> animationList;
    protected ItemType[] availableTypes;
    protected boolean isVisible;
    protected ArrayList<Item> itemList;
    private final LayerType layerType;
    Panel panel;
    protected ArrayList<TransitionAnimation> transitionAnimationList;

    protected Layer(VEContext context, LayerType type, int id, String name) {
        super(context, ElementType.LAYER, id, name);
        this.itemList = new ArrayList<>();
        this.isVisible = true;
        this.layerType = type;
        this.TAG = getClass().getSimpleName();
        this.panel = new Panel();
        this.animationList = new ArrayList<>();
        this.transitionAnimationList = new ArrayList<>();
    }

    public LayerType getLayerType() {
        return this.layerType;
    }

    @Override // com.samsung.vekit.Common.Object.Element
    public Panel getPanel() {
        return this.panel;
    }

    public Layer setPanel(Panel panel) {
        this.panel = panel.m9389clone();
        return this;
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void attach(Item element) {
        try {
            checkValidItem(element);
            this.itemList.add(element);
            element.setParent(this);
            this.context.getNativeInterface().attach(this, element.getId());
        } catch (Exception e) {
            Log.e(this.TAG, "attach: ", e);
        }
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void attach(Item element, int index) {
        try {
            checkValidItem(element);
            this.itemList.add(index, element);
            element.setParent(this);
            this.context.getNativeInterface().attach(this, index, element.getId());
        } catch (Exception e) {
            Log.e(this.TAG, "attach: ", e);
        }
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void attach(ArrayList<Item> list) {
        try {
            Iterator<Item> it = list.iterator();
            while (it.hasNext()) {
                checkValidItem(it.next());
            }
            this.itemList.addAll(list);
            ArrayList<Integer> idList = new ArrayList<>();
            Iterator<Item> it2 = list.iterator();
            while (it2.hasNext()) {
                Item item = it2.next();
                item.setParent(this);
                idList.add(Integer.valueOf(item.getId()));
            }
            this.context.getNativeInterface().attach(this, idList);
        } catch (Exception e) {
            Log.e(this.TAG, "attach: ", e);
        }
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void detach(Item element) {
        try {
            checkValidItem(element);
            this.context.getNativeInterface().detach(this, element.getId());
            element.setParent(null);
            this.itemList.remove(element);
        } catch (Exception e) {
            Log.e(this.TAG, "detach: ", e);
        }
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void detach(int index) {
        Item item = this.itemList.get(index);
        detach(item);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void clear() {
        this.context.getNativeInterface().clear(this);
        Iterator<Item> it = this.itemList.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            item.setParent(null);
        }
        this.itemList.clear();
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public boolean isEmpty() {
        return this.itemList.isEmpty();
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public int getChildSize() {
        return this.itemList.size();
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public List<Item> getChildren() {
        return Collections.unmodifiableList(this.itemList);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public boolean contains(Item element) {
        return this.itemList.contains(element);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public int getIndex(Item element) {
        return this.itemList.indexOf(element);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public Item getChild(int index) {
        return this.itemList.get(index);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void swap(Item from, Item to) {
        int fromIndex = this.itemList.indexOf(from);
        int toIndex = this.itemList.indexOf(to);
        Collections.swap(this.itemList, fromIndex, toIndex);
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

    public void attachTransitionAnimation(TransitionAnimation animation) {
        this.transitionAnimationList.add(animation);
        this.context.getNativeInterface().attachAnimation(this, animation.getId());
    }

    public TransitionAnimation findTransitionAnimationByFirstTarget(Item firstTarget) {
        Iterator<TransitionAnimation> it = this.transitionAnimationList.iterator();
        while (it.hasNext()) {
            TransitionAnimation animation = it.next();
            if (animation.getFirstTarget() == firstTarget) {
                return animation;
            }
        }
        return null;
    }

    public TransitionAnimation findTransitionAnimationBySecondTarget(Item secondTarget) {
        Iterator<TransitionAnimation> it = this.transitionAnimationList.iterator();
        while (it.hasNext()) {
            TransitionAnimation animation = it.next();
            if (animation.getSecondTarget() == secondTarget) {
                return animation;
            }
        }
        return null;
    }

    public TransitionAnimation findTransitionAnimation(Item firstTarget, Item secondTarget) {
        Iterator<TransitionAnimation> it = this.transitionAnimationList.iterator();
        while (it.hasNext()) {
            TransitionAnimation animation = it.next();
            if (animation.getFirstTarget() == firstTarget
                    && animation.getSecondTarget() == secondTarget) {
                return animation;
            }
        }
        return null;
    }

    public void detachTransitionAnimation(TransitionAnimation animation) {
        this.context.getNativeInterface().detachAnimation(this, animation.getId());
        this.transitionAnimationList.remove(animation);
        animation.setTarget((Element) null);
    }

    public TransitionAnimation getTransitionAnimation(int index) {
        if (this.transitionAnimationList.size() <= index || index < 0) {
            Log.e(this.TAG, "failed to get transitionAnimation (invalid index)");
            return null;
        }
        return this.transitionAnimationList.get(index);
    }

    public int getTransitionAnimationIndex(Animation<?> animation) {
        if (animation == null) {
            Log.e(this.TAG, "failed to getTransitionAnimationIndex (animation is null)");
            return -1;
        }
        return this.transitionAnimationList.indexOf(animation);
    }

    public List<Animation<?>> getTransitionAnimationList() {
        return Collections.unmodifiableList(this.transitionAnimationList);
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    public void checkValidItem(final Item item) throws Exception {
        if (this.availableTypes == null) {
            return;
        }
        boolean valid =
                Arrays.stream(this.availableTypes)
                        .anyMatch(
                                new Predicate() { // from class:
                                                  // com.samsung.vekit.Layer.Layer$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return Layer.lambda$checkValidItem$0(
                                                Item.this, (ItemType) obj);
                                    }
                                });
        if (!valid) {
            throw new Exception("isInvalidElement : please attach correct Item.");
        }
    }

    static /* synthetic */ boolean lambda$checkValidItem$0(Item item, ItemType type) {
        return type == item.getItemType();
    }

    public void checkValidAnimation(Animation animation) throws Exception {
        boolean valid = animation.getAnimationType() != AnimationType.TRANSITION;
        if (!valid) {
            throw new Exception(
                    "isInvalidElement : please attach correct TransitionAnimation To Layer.");
        }
    }
}
