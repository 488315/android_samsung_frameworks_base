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

/* loaded from: classes6.dex */
public class Layer extends Element implements HierarchyInterface<Item> {
    protected ArrayList<Animation<?>> animationList;
    protected ItemType[] availableTypes;
    protected boolean isVisible;
    protected ArrayList<Item> itemList;
    private final LayerType layerType;
    Panel panel;
    protected ArrayList<TransitionAnimation> transitionAnimationList;

    protected Layer(VEContext vEContext, LayerType layerType, int i, String str) {
        super(vEContext, ElementType.LAYER, i, str);
        this.itemList = new ArrayList<>();
        this.isVisible = true;
        this.layerType = layerType;
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
        this.panel = panel.m9809clone();
        return this;
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void attach(Item item) {
        try {
            checkValidItem(item);
            this.itemList.add(item);
            item.setParent(this);
            this.context.getNativeInterface().attach(this, item.getId());
        } catch (Exception e) {
            Log.e(this.TAG, "attach: ", e);
        }
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void attach(Item item, int i) {
        try {
            checkValidItem(item);
            this.itemList.add(i, item);
            item.setParent(this);
            this.context.getNativeInterface().attach(this, i, item.getId());
        } catch (Exception e) {
            Log.e(this.TAG, "attach: ", e);
        }
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void attach(ArrayList<Item> arrayList) {
        try {
            Iterator<Item> it = arrayList.iterator();
            while (it.hasNext()) {
                checkValidItem(it.next());
            }
            this.itemList.addAll(arrayList);
            ArrayList<Integer> arrayList2 = new ArrayList<>();
            Iterator<Item> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Item next = it2.next();
                next.setParent(this);
                arrayList2.add(Integer.valueOf(next.getId()));
            }
            this.context.getNativeInterface().attach(this, arrayList2);
        } catch (Exception e) {
            Log.e(this.TAG, "attach: ", e);
        }
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void detach(Item item) {
        try {
            checkValidItem(item);
            this.context.getNativeInterface().detach(this, item.getId());
            item.setParent(null);
            this.itemList.remove(item);
        } catch (Exception e) {
            Log.e(this.TAG, "detach: ", e);
        }
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void detach(int i) {
        detach(this.itemList.get(i));
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void clear() {
        this.context.getNativeInterface().clear(this);
        Iterator<Item> it = this.itemList.iterator();
        while (it.hasNext()) {
            it.next().setParent(null);
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
    public boolean contains(Item item) {
        return this.itemList.contains(item);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public int getIndex(Item item) {
        return this.itemList.indexOf(item);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public Item getChild(int i) {
        return this.itemList.get(i);
    }

    @Override // com.samsung.vekit.Interface.HierarchyInterface
    public void swap(Item item, Item item2) {
        int indexOf = this.itemList.indexOf(item);
        int indexOf2 = this.itemList.indexOf(item2);
        Collections.swap(this.itemList, indexOf, indexOf2);
        this.context.getNativeInterface().swap(this, indexOf, indexOf2);
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

    public void attachTransitionAnimation(TransitionAnimation transitionAnimation) {
        this.transitionAnimationList.add(transitionAnimation);
        this.context.getNativeInterface().attachAnimation(this, transitionAnimation.getId());
    }

    public TransitionAnimation findTransitionAnimationByFirstTarget(Item item) {
        Iterator<TransitionAnimation> it = this.transitionAnimationList.iterator();
        while (it.hasNext()) {
            TransitionAnimation next = it.next();
            if (next.getFirstTarget() == item) {
                return next;
            }
        }
        return null;
    }

    public TransitionAnimation findTransitionAnimationBySecondTarget(Item item) {
        Iterator<TransitionAnimation> it = this.transitionAnimationList.iterator();
        while (it.hasNext()) {
            TransitionAnimation next = it.next();
            if (next.getSecondTarget() == item) {
                return next;
            }
        }
        return null;
    }

    public TransitionAnimation findTransitionAnimation(Item item, Item item2) {
        Iterator<TransitionAnimation> it = this.transitionAnimationList.iterator();
        while (it.hasNext()) {
            TransitionAnimation next = it.next();
            if (next.getFirstTarget() == item && next.getSecondTarget() == item2) {
                return next;
            }
        }
        return null;
    }

    public void detachTransitionAnimation(TransitionAnimation transitionAnimation) {
        this.context.getNativeInterface().detachAnimation(this, transitionAnimation.getId());
        this.transitionAnimationList.remove(transitionAnimation);
        transitionAnimation.setTarget((Element) null);
    }

    public TransitionAnimation getTransitionAnimation(int i) {
        if (this.transitionAnimationList.size() <= i || i < 0) {
            Log.e(this.TAG, "failed to get transitionAnimation (invalid index)");
            return null;
        }
        return this.transitionAnimationList.get(i);
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

    public void setVisible(boolean z) {
        this.isVisible = z;
    }

    public void checkValidItem(final Item item) throws Exception {
        ItemType[] itemTypeArr = this.availableTypes;
        if (itemTypeArr != null && !Arrays.stream(itemTypeArr).anyMatch(new Predicate() { // from class: com.samsung.vekit.Layer.Layer$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Layer.lambda$checkValidItem$0(Item.this, (ItemType) obj);
            }
        })) {
            throw new Exception("isInvalidElement : please attach correct Item.");
        }
    }

    static /* synthetic */ boolean lambda$checkValidItem$0(Item item, ItemType itemType) {
        return itemType == item.getItemType();
    }

    public void checkValidAnimation(Animation animation) throws Exception {
        if (animation.getAnimationType() == AnimationType.TRANSITION) {
            throw new Exception("isInvalidElement : please attach correct TransitionAnimation To Layer.");
        }
    }
}
