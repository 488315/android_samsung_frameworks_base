package com.samsung.vekit.Animation;

import android.util.Log;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.Type.LayerType;
import com.samsung.vekit.Common.Type.TransitionType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.Item;
import java.util.Arrays;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public class TransitionAnimation extends Animation<Float> {
    protected Item secondTarget;
    protected TransitionType transitionType;

    @Override // com.samsung.vekit.Animation.Animation
    @Deprecated
    public TransitionAnimation setDuration(long j) {
        return this;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.vekit.Animation.Animation
    @Deprecated
    public TransitionAnimation setFrom(Float f) {
        return this;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.vekit.Animation.Animation
    @Deprecated
    public TransitionAnimation setTo(Float f) {
        return this;
    }

    public TransitionAnimation(VEContext vEContext, int i, String str, TransitionType transitionType) {
        super(vEContext, AnimationType.TRANSITION, i, str);
        this.transitionType = transitionType;
    }

    public TransitionType getTransitionType() {
        return this.transitionType;
    }

    public TransitionAnimation setTargets(Item item, Item item2) {
        try {
            checkValidItem(item);
            checkValidItem(item2);
            this.firstTarget = item;
            this.secondTarget = item2;
            return this;
        } catch (Exception e) {
            Log.e(this.TAG, "setTargets: ", e);
            return this;
        }
    }

    public TransitionAnimation setFirstTarget(Item item) {
        try {
            checkValidItem(item);
            this.firstTarget = item;
            return this;
        } catch (Exception e) {
            Log.e(this.TAG, "setFirstTarget: ", e);
            return this;
        }
    }

    public TransitionAnimation setSecondTarget(Item item) {
        try {
            checkValidItem(item);
            this.secondTarget = item;
            return this;
        } catch (Exception e) {
            Log.e(this.TAG, "setSecondTarget: ", e);
            return this;
        }
    }

    public Element getFirstTarget() {
        return this.firstTarget;
    }

    public Item getSecondTarget() {
        return this.secondTarget;
    }

    @Override // com.samsung.vekit.Animation.Animation
    public TransitionAnimation setTarget(Element element) {
        return (TransitionAnimation) super.setTarget(element);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public TransitionAnimation setBezierControlPoint(float f, float f2, float f3, float f4) {
        return (TransitionAnimation) super.setBezierControlPoint(f, f2, f3, f4);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public TransitionAnimation setStartTime(long j) {
        return (TransitionAnimation) super.setStartTime(j);
    }

    @Override // com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationStarted(Object obj) {
        super.onAnimationStarted(obj);
    }

    @Override // com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationUpdated(Object obj) {
        super.onAnimationUpdated(obj);
    }

    @Override // com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationFinished(Object obj) {
        super.onAnimationFinished(obj);
    }

    @Override // com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationCanceled(Object obj) {
        super.onAnimationCanceled(obj);
    }

    public void checkValidItem(final Item item) throws Exception {
        boolean anyMatch = Arrays.stream(new ItemType[]{ItemType.IMAGE, ItemType.VIDEO, ItemType.COLOR, ItemType.PORTRAIT_VIDEO}).anyMatch(new Predicate() { // from class: com.samsung.vekit.Animation.TransitionAnimation$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return TransitionAnimation.lambda$checkValidItem$0(Item.this, (ItemType) obj);
            }
        });
        boolean z = item.getParent().getLayerType() == LayerType.MEDIA;
        if (!anyMatch || !z) {
            throw new Exception("isInvalidElement : please set correct Items to TransitionAnimation.");
        }
    }

    static /* synthetic */ boolean lambda$checkValidItem$0(Item item, ItemType itemType) {
        return itemType == item.getItemType();
    }
}
