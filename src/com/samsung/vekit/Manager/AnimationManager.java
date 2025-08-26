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

    public AnimationManager(VEContext vEContext) {
        super(vEContext, ManagerType.ANIMATION);
        this.uiAnimationList = new ArrayList<>();
        this.TAG = getClass().getSimpleName();
    }

    public Animation<?> create(AnimationType animationType, String str) {
        return create(animationType, TransitionType.DEFAULT, str);
    }

    public Animation<?> create(AnimationType animationType, TransitionType transitionType, String str) {
        Animation<?> transformAnimation;
        try {
            int iGenerateUniqueId = generateUniqueId();
            switch (AnonymousClass1.$SwitchMap$com$samsung$vekit$Common$Type$AnimationType[animationType.ordinal()]) {
                case 1:
                    transformAnimation = new TransformAnimation(this.context, iGenerateUniqueId, str);
                    break;
                case 2:
                    transformAnimation = new FilterAnimation(this.context, iGenerateUniqueId, str);
                    break;
                case 3:
                    transformAnimation = new ToneAnimation(this.context, iGenerateUniqueId, str);
                    break;
                case 4:
                    return createTransitionAnimation(transitionType, iGenerateUniqueId, str);
                case 5:
                    transformAnimation = new AlphaAnimation(this.context, iGenerateUniqueId, str);
                    break;
                case 6:
                    transformAnimation = new ClipAnimation(this.context, iGenerateUniqueId, str);
                    break;
                case 7:
                    transformAnimation = new WaveAnimation(this.context, iGenerateUniqueId, str);
                    break;
                case 8:
                    transformAnimation = new ScaleAnimation(this.context, iGenerateUniqueId, str);
                    break;
                case 9:
                    transformAnimation = new RotateAnimation(this.context, iGenerateUniqueId, str);
                    break;
                case 10:
                    transformAnimation = new TranslateAnimation(this.context, iGenerateUniqueId, str);
                    break;
                default:
                    return null;
            }
            add(transformAnimation);
            return transformAnimation;
        } catch (Exception e) {
            Log.e(this.TAG, "create: ", e);
            return null;
        }
    }

    /* renamed from: com.samsung.vekit.Manager.AnimationManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$AnimationType;
        static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$TransitionType;

        static {
            int[] iArr = new int[TransitionType.values().length];
            $SwitchMap$com$samsung$vekit$Common$Type$TransitionType = iArr;
            try {
                iArr[TransitionType.FADE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$TransitionType[TransitionType.DISSOLVE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$TransitionType[TransitionType.SLIDE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$TransitionType[TransitionType.WIPE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$TransitionType[TransitionType.DEFAULT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr2 = new int[AnimationType.values().length];
            $SwitchMap$com$samsung$vekit$Common$Type$AnimationType = iArr2;
            try {
                iArr2[AnimationType.TRANSFORM.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AnimationType[AnimationType.FILTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AnimationType[AnimationType.TONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AnimationType[AnimationType.TRANSITION.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AnimationType[AnimationType.ALPHA.ordinal()] = 5;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AnimationType[AnimationType.CLIP.ordinal()] = 6;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AnimationType[AnimationType.WAVE.ordinal()] = 7;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AnimationType[AnimationType.SCALE.ordinal()] = 8;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AnimationType[AnimationType.ROTATE.ordinal()] = 9;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AnimationType[AnimationType.TRANSLATE.ordinal()] = 10;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AnimationType[AnimationType.DEFAULT.ordinal()] = 11;
            } catch (NoSuchFieldError unused16) {
            }
        }
    }

    private TransitionAnimation createTransitionAnimation(TransitionType transitionType, int i, String str) {
        TransitionAnimation fadeAnimation;
        int i2 = AnonymousClass1.$SwitchMap$com$samsung$vekit$Common$Type$TransitionType[transitionType.ordinal()];
        if (i2 == 1) {
            fadeAnimation = new FadeAnimation(this.context, i, str);
        } else if (i2 == 2) {
            fadeAnimation = new DissolveAnimation(this.context, i, str);
        } else if (i2 == 3) {
            fadeAnimation = new SlideAnimation(this.context, i, str);
        } else {
            if (i2 != 4) {
                return null;
            }
            fadeAnimation = new WipeAnimation(this.context, i, str);
        }
        add(fadeAnimation);
        return fadeAnimation;
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
        if (animation.getAnimationType() == AnimationType.TRANSITION) {
            throw new Exception("isInvalidElement : please attach correct uiAnimation(not TransitionAnimation) to AnimationManager.");
        }
    }

    public List<Animation<?>> getUiAnimationList() {
        return Collections.unmodifiableList(this.uiAnimationList);
    }
}
