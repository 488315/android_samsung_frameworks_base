package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.KeyAttributes;
import androidx.constraintlayout.motion.widget.KeyPosition;
import androidx.constraintlayout.motion.widget.MotionController;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionPaths;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.motion.widget.ViewTransition;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.R$styleable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MotionEffect extends MotionHelper {
    public int fadeMove;
    public float motionEffectAlpha;
    public int motionEffectEnd;
    public int motionEffectStart;
    public boolean motionEffectStrictMove;
    public int motionEffectTranslationX;
    public int motionEffectTranslationY;
    public int viewTransitionId;

    public MotionEffect(Context context) {
        super(context);
        this.motionEffectAlpha = 0.1f;
        this.motionEffectStart = 49;
        this.motionEffectEnd = 50;
        this.motionEffectTranslationX = 0;
        this.motionEffectTranslationY = 0;
        this.motionEffectStrictMove = true;
        this.viewTransitionId = -1;
        this.fadeMove = -1;
    }

    public final void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MotionEffect);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 3) {
                    int i2 = obtainStyledAttributes.getInt(index, this.motionEffectStart);
                    this.motionEffectStart = i2;
                    this.motionEffectStart = Math.max(Math.min(i2, 99), 0);
                } else if (index == 1) {
                    int i3 = obtainStyledAttributes.getInt(index, this.motionEffectEnd);
                    this.motionEffectEnd = i3;
                    this.motionEffectEnd = Math.max(Math.min(i3, 99), 0);
                } else if (index == 5) {
                    this.motionEffectTranslationX = obtainStyledAttributes.getDimensionPixelOffset(index, this.motionEffectTranslationX);
                } else if (index == 6) {
                    this.motionEffectTranslationY = obtainStyledAttributes.getDimensionPixelOffset(index, this.motionEffectTranslationY);
                } else if (index == 0) {
                    this.motionEffectAlpha = obtainStyledAttributes.getFloat(index, this.motionEffectAlpha);
                } else if (index == 2) {
                    this.fadeMove = obtainStyledAttributes.getInt(index, this.fadeMove);
                } else if (index == 4) {
                    this.motionEffectStrictMove = obtainStyledAttributes.getBoolean(index, this.motionEffectStrictMove);
                } else if (index == 7) {
                    this.viewTransitionId = obtainStyledAttributes.getResourceId(index, this.viewTransitionId);
                }
            }
            int i4 = this.motionEffectStart;
            int i5 = this.motionEffectEnd;
            if (i4 == i5) {
                if (i4 > 0) {
                    this.motionEffectStart = i4 - 1;
                } else {
                    this.motionEffectEnd = i5 + 1;
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x0188, code lost:
    
        if (r14 == 0.0f) goto L67;
     */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01b9  */
    @Override // androidx.constraintlayout.motion.widget.MotionHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onPreSetup(MotionLayout motionLayout, HashMap hashMap) {
        KeyAttributes keyAttributes;
        KeyAttributes keyAttributes2;
        KeyAttributes keyAttributes3;
        KeyAttributes keyAttributes4;
        boolean z;
        KeyAttributes keyAttributes5;
        View[] viewArr;
        KeyAttributes keyAttributes6;
        int i;
        MotionEffect motionEffect = this;
        HashMap hashMap2 = hashMap;
        View[] views = motionEffect.getViews((ConstraintLayout) getParent());
        if (views == null) {
            Debug.getLoc();
            return;
        }
        KeyAttributes keyAttributes7 = new KeyAttributes();
        KeyAttributes keyAttributes8 = new KeyAttributes();
        keyAttributes7.setValue(Float.valueOf(motionEffect.motionEffectAlpha), "alpha");
        keyAttributes8.setValue(Float.valueOf(motionEffect.motionEffectAlpha), "alpha");
        keyAttributes7.mFramePosition = motionEffect.motionEffectStart;
        keyAttributes8.mFramePosition = motionEffect.motionEffectEnd;
        KeyPosition keyPosition = new KeyPosition();
        keyPosition.mFramePosition = motionEffect.motionEffectStart;
        int i2 = 0;
        keyPosition.mPositionType = 0;
        keyPosition.setValue(0, "percentX");
        keyPosition.setValue(0, "percentY");
        KeyPosition keyPosition2 = new KeyPosition();
        keyPosition2.mFramePosition = motionEffect.motionEffectEnd;
        keyPosition2.mPositionType = 0;
        keyPosition2.setValue(1, "percentX");
        keyPosition2.setValue(1, "percentY");
        KeyAttributes keyAttributes9 = null;
        if (motionEffect.motionEffectTranslationX > 0) {
            keyAttributes = new KeyAttributes();
            keyAttributes2 = new KeyAttributes();
            keyAttributes.setValue(Integer.valueOf(motionEffect.motionEffectTranslationX), "translationX");
            keyAttributes.mFramePosition = motionEffect.motionEffectEnd;
            keyAttributes2.setValue(0, "translationX");
            keyAttributes2.mFramePosition = motionEffect.motionEffectEnd - 1;
        } else {
            keyAttributes = null;
            keyAttributes2 = null;
        }
        if (motionEffect.motionEffectTranslationY > 0) {
            KeyAttributes keyAttributes10 = new KeyAttributes();
            KeyAttributes keyAttributes11 = new KeyAttributes();
            keyAttributes10.setValue(Integer.valueOf(motionEffect.motionEffectTranslationY), "translationY");
            keyAttributes10.mFramePosition = motionEffect.motionEffectEnd;
            keyAttributes11.setValue(0, "translationY");
            keyAttributes11.mFramePosition = motionEffect.motionEffectEnd - 1;
            keyAttributes3 = keyAttributes10;
            keyAttributes9 = keyAttributes11;
        } else {
            keyAttributes3 = null;
        }
        int i3 = motionEffect.fadeMove;
        if (i3 == -1) {
            int[] iArr = new int[4];
            int i4 = 0;
            while (i4 < views.length) {
                MotionController motionController = (MotionController) hashMap2.get(views[i4]);
                if (motionController == null) {
                    keyAttributes6 = keyAttributes9;
                } else {
                    MotionPaths motionPaths = motionController.mEndMotionPath;
                    float f = motionPaths.f27x;
                    MotionPaths motionPaths2 = motionController.mStartMotionPath;
                    keyAttributes6 = keyAttributes9;
                    float f2 = f - motionPaths2.f27x;
                    float f3 = motionPaths.f28y - motionPaths2.f28y;
                    if (f3 < 0.0f) {
                        i = 1;
                        iArr[1] = iArr[1] + 1;
                    } else {
                        i = 1;
                    }
                    if (f3 > 0.0f) {
                        iArr[0] = iArr[0] + i;
                    }
                    if (f2 > 0.0f) {
                        iArr[3] = iArr[3] + i;
                    }
                    if (f2 < 0.0f) {
                        iArr[2] = iArr[2] + i;
                    }
                }
                i4++;
                keyAttributes9 = keyAttributes6;
                i2 = 0;
            }
            keyAttributes4 = keyAttributes9;
            int i5 = i2;
            int i6 = iArr[i5];
            i3 = i5;
            for (int i7 = 1; i7 < 4; i7++) {
                int i8 = iArr[i7];
                if (i6 < i8) {
                    i6 = i8;
                    i3 = i7;
                }
            }
        } else {
            keyAttributes4 = keyAttributes9;
        }
        int i9 = 0;
        while (i9 < views.length) {
            MotionController motionController2 = (MotionController) hashMap2.get(views[i9]);
            if (motionController2 != null) {
                MotionPaths motionPaths3 = motionController2.mEndMotionPath;
                float f4 = motionPaths3.f27x;
                MotionPaths motionPaths4 = motionController2.mStartMotionPath;
                float f5 = f4 - motionPaths4.f27x;
                float f6 = motionPaths3.f28y - motionPaths4.f28y;
                if (i3 == 0) {
                    if (f6 <= 0.0f || (motionEffect.motionEffectStrictMove && f5 != 0.0f)) {
                        z = true;
                        if (z) {
                            int i10 = motionEffect.viewTransitionId;
                            if (i10 == -1) {
                                motionController2.addKey(keyAttributes7);
                                motionController2.addKey(keyAttributes8);
                                motionController2.addKey(keyPosition);
                                motionController2.addKey(keyPosition2);
                                if (motionEffect.motionEffectTranslationX > 0) {
                                    motionController2.addKey(keyAttributes);
                                    motionController2.addKey(keyAttributes2);
                                }
                                if (motionEffect.motionEffectTranslationY > 0) {
                                    motionController2.addKey(keyAttributes3);
                                    keyAttributes5 = keyAttributes4;
                                    motionController2.addKey(keyAttributes5);
                                } else {
                                    keyAttributes5 = keyAttributes4;
                                }
                            } else {
                                keyAttributes5 = keyAttributes4;
                                MotionScene motionScene = motionLayout.mScene;
                                if (motionScene != null) {
                                    Iterator it = motionScene.mViewTransitionController.viewTransitions.iterator();
                                    while (it.hasNext()) {
                                        ViewTransition viewTransition = (ViewTransition) it.next();
                                        viewArr = views;
                                        if (viewTransition.mId == i10) {
                                            ArrayList arrayList = (ArrayList) viewTransition.mKeyFrames.mFramesMap.get(-1);
                                            if (arrayList != null) {
                                                motionController2.mKeyList.addAll(arrayList);
                                            }
                                            i9++;
                                            motionEffect = this;
                                            hashMap2 = hashMap;
                                            keyAttributes4 = keyAttributes5;
                                            views = viewArr;
                                        } else {
                                            views = viewArr;
                                        }
                                    }
                                }
                            }
                            viewArr = views;
                            i9++;
                            motionEffect = this;
                            hashMap2 = hashMap;
                            keyAttributes4 = keyAttributes5;
                            views = viewArr;
                        }
                    } else {
                        z = false;
                        if (z) {
                        }
                    }
                } else if (i3 == 1) {
                    if (f6 < 0.0f) {
                        if (motionEffect.motionEffectStrictMove) {
                        }
                        z = false;
                        if (z) {
                        }
                    }
                    z = true;
                    if (z) {
                    }
                } else if (i3 != 2) {
                    if (i3 == 3 && f5 > 0.0f && (!motionEffect.motionEffectStrictMove || f6 == 0.0f)) {
                        z = false;
                        if (z) {
                        }
                    }
                    z = true;
                    if (z) {
                    }
                } else if (f5 >= 0.0f || (motionEffect.motionEffectStrictMove && f6 != 0.0f)) {
                    z = true;
                    if (z) {
                    }
                } else {
                    z = false;
                    if (z) {
                    }
                }
            }
            viewArr = views;
            keyAttributes5 = keyAttributes4;
            i9++;
            motionEffect = this;
            hashMap2 = hashMap;
            keyAttributes4 = keyAttributes5;
            views = viewArr;
        }
    }

    public MotionEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.motionEffectAlpha = 0.1f;
        this.motionEffectStart = 49;
        this.motionEffectEnd = 50;
        this.motionEffectTranslationX = 0;
        this.motionEffectTranslationY = 0;
        this.motionEffectStrictMove = true;
        this.viewTransitionId = -1;
        this.fadeMove = -1;
        init(context, attributeSet);
    }

    public MotionEffect(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.motionEffectAlpha = 0.1f;
        this.motionEffectStart = 49;
        this.motionEffectEnd = 50;
        this.motionEffectTranslationX = 0;
        this.motionEffectTranslationY = 0;
        this.motionEffectStrictMove = true;
        this.viewTransitionId = -1;
        this.fadeMove = -1;
        init(context, attributeSet);
    }
}
