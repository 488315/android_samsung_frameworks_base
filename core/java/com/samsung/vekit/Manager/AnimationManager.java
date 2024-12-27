package com.samsung.vekit.Manager;

import android.util.Log;
import com.samsung.vekit.Animation.AlphaAnimation;
import com.samsung.vekit.Animation.Animation;
import com.samsung.vekit.Animation.ClipAnimation;
import com.samsung.vekit.Animation.DissolveAnimation;
import com.samsung.vekit.Animation.FadeAnimation;
import com.samsung.vekit.Animation.FilterAnimation;
import com.samsung.vekit.Animation.RotateAnimation;
import com.samsung.vekit.Animation.ScaleAnimation;
import com.samsung.vekit.Animation.SlideAnimation;
import com.samsung.vekit.Animation.ToneAnimation;
import com.samsung.vekit.Animation.TransformAnimation;
import com.samsung.vekit.Animation.TransitionAnimation;
import com.samsung.vekit.Animation.TranslateAnimation;
import com.samsung.vekit.Animation.WaveAnimation;
import com.samsung.vekit.Animation.WipeAnimation;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.Type.ManagerType;
import com.samsung.vekit.Common.Type.TransitionType;
import com.samsung.vekit.Common.VEContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class AnimationManager extends Manager<Animation<?>> {
    ArrayList<Animation<?>> uiAnimationList;

    public AnimationManager(VEContext context) {
        super(context, ManagerType.ANIMATION);
        this.uiAnimationList = new ArrayList<>();
        this.TAG = getClass().getSimpleName();
    }

    public Animation<?> create(AnimationType type, String name) {
        return create(type, TransitionType.DEFAULT, name);
    }

    public Animation<?> create(AnimationType type, TransitionType transitionType, String name) {
        Animation<?> animation;
        try {
            int uniqueId = generateUniqueId();
            switch (type) {
                case TRANSFORM:
                    animation = new TransformAnimation(this.context, uniqueId, name);
                    break;
                case FILTER:
                    animation = new FilterAnimation(this.context, uniqueId, name);
                    break;
                case TONE:
                    animation = new ToneAnimation(this.context, uniqueId, name);
                    break;
                case TRANSITION:
                    Animation<?> animation2 = createTransitionAnimation(transitionType, uniqueId, name);
                    return animation2;
                case ALPHA:
                    animation = new AlphaAnimation(this.context, uniqueId, name);
                    break;
                case CLIP:
                    animation = new ClipAnimation(this.context, uniqueId, name);
                    break;
                case WAVE:
                    animation = new WaveAnimation(this.context, uniqueId, name);
                    break;
                case SCALE:
                    animation = new ScaleAnimation(this.context, uniqueId, name);
                    break;
                case ROTATE:
                    animation = new RotateAnimation(this.context, uniqueId, name);
                    break;
                case TRANSLATE:
                    animation = new TranslateAnimation(this.context, uniqueId, name);
                    break;
                default:
                    return null;
            }
            add(animation);
            return animation;
        } catch (Exception e) {
            Log.e(this.TAG, "create: ", e);
            return null;
        }
    }

    private TransitionAnimation createTransitionAnimation(TransitionType type, int uniqueId, String name) {
        TransitionAnimation animation;
        switch (type) {
            case FADE:
                animation = new FadeAnimation(this.context, uniqueId, name);
                break;
            case DISSOLVE:
                animation = new DissolveAnimation(this.context, uniqueId, name);
                break;
            case SLIDE:
                animation = new SlideAnimation(this.context, uniqueId, name);
                break;
            case WIPE:
                animation = new WipeAnimation(this.context, uniqueId, name);
                break;
            default:
                return null;
        }
        add(animation);
        return animation;
    }

    public void attachAnimation(Animation animation) {
        try {
            checkValidAnimation(animation);
            this.uiAnimationList.add(animation);
            this.context.getNativeInterface().attachAnimation(this, animation.getId());
        } catch (Exception e) {
            Log.e(this.TAG, "attachAnimation: ", e);
        }
    }

    public void detachAnimation(Animation animation) {
        try {
            checkValidAnimation(animation);
            this.uiAnimationList.remove(animation);
            this.context.getNativeInterface().detachAnimation(this, animation.getId());
        } catch (Exception e) {
            Log.e(this.TAG, "detachAnimation: ", e);
        }
    }

    public void clearAnimations() {
        this.uiAnimationList.clear();
        this.context.getNativeInterface().clearAnimations(this);
    }

    public void animate() {
        this.context.getNativeInterface().animate();
    }

    public void checkValidAnimation(Animation animation) throws Exception {
        boolean valid = animation.getAnimationType() != AnimationType.TRANSITION;
        if (!valid) {
            throw new Exception("isInvalidElement : please attach correct uiAnimation(not TransitionAnimation) to AnimationManager.");
        }
    }

    public List<Animation<?>> getUiAnimationList() {
        return Collections.unmodifiableList(this.uiAnimationList);
    }
}
