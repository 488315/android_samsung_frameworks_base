package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.KeyAttributes;
import androidx.constraintlayout.motion.widget.KeyPosition;
import androidx.constraintlayout.motion.widget.MotionController;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.R$styleable;
import java.util.HashMap;

/* loaded from: classes.dex */
public class MotionEffect extends MotionHelper {
    private int mFadeMove;
    private float mMotionEffectAlpha;
    private int mMotionEffectEnd;
    private int mMotionEffectStart;
    private boolean mMotionEffectStrictMove;
    private int mMotionEffectTranslationX;
    private int mMotionEffectTranslationY;
    private int mViewTransitionId;

    public MotionEffect(Context context) {
        super(context);
        this.mMotionEffectAlpha = 0.1f;
        this.mMotionEffectStart = 49;
        this.mMotionEffectEnd = 50;
        this.mMotionEffectTranslationX = 0;
        this.mMotionEffectTranslationY = 0;
        this.mMotionEffectStrictMove = true;
        this.mViewTransitionId = -1;
        this.mFadeMove = -1;
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MotionEffect);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 3) {
                    int i2 = obtainStyledAttributes.getInt(index, this.mMotionEffectStart);
                    this.mMotionEffectStart = i2;
                    this.mMotionEffectStart = Math.max(Math.min(i2, 99), 0);
                } else if (index == 1) {
                    int i3 = obtainStyledAttributes.getInt(index, this.mMotionEffectEnd);
                    this.mMotionEffectEnd = i3;
                    this.mMotionEffectEnd = Math.max(Math.min(i3, 99), 0);
                } else if (index == 5) {
                    this.mMotionEffectTranslationX = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMotionEffectTranslationX);
                } else if (index == 6) {
                    this.mMotionEffectTranslationY = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMotionEffectTranslationY);
                } else if (index == 0) {
                    this.mMotionEffectAlpha = obtainStyledAttributes.getFloat(index, this.mMotionEffectAlpha);
                } else if (index == 2) {
                    this.mFadeMove = obtainStyledAttributes.getInt(index, this.mFadeMove);
                } else if (index == 4) {
                    this.mMotionEffectStrictMove = obtainStyledAttributes.getBoolean(index, this.mMotionEffectStrictMove);
                } else if (index == 7) {
                    this.mViewTransitionId = obtainStyledAttributes.getResourceId(index, this.mViewTransitionId);
                }
            }
            int i4 = this.mMotionEffectStart;
            int i5 = this.mMotionEffectEnd;
            if (i4 == i5) {
                if (i4 > 0) {
                    this.mMotionEffectStart = i4 - 1;
                } else {
                    this.mMotionEffectEnd = i5 + 1;
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x01bc, code lost:
    
        if (r15 == 0.0f) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0186, code lost:
    
        if (r14 == 0.0f) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0199, code lost:
    
        if (r14 == 0.0f) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x01a9, code lost:
    
        if (r15 == 0.0f) goto L75;
     */
    @Override // androidx.constraintlayout.motion.widget.MotionHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onPreSetup(MotionLayout motionLayout, HashMap<View, MotionController> hashMap) {
        KeyAttributes keyAttributes;
        KeyAttributes keyAttributes2;
        KeyAttributes keyAttributes3;
        boolean z;
        HashMap<View, MotionController> hashMap2 = hashMap;
        View[] views = getViews((ConstraintLayout) getParent());
        if (views == null) {
            Log.v("FadeMove", Debug.getLoc() + " views = null");
            return;
        }
        KeyAttributes keyAttributes4 = new KeyAttributes();
        KeyAttributes keyAttributes5 = new KeyAttributes();
        keyAttributes4.setValue(Float.valueOf(this.mMotionEffectAlpha), "alpha");
        keyAttributes5.setValue(Float.valueOf(this.mMotionEffectAlpha), "alpha");
        keyAttributes4.setFramePosition(this.mMotionEffectStart);
        keyAttributes5.setFramePosition(this.mMotionEffectEnd);
        KeyPosition keyPosition = new KeyPosition();
        keyPosition.setFramePosition(this.mMotionEffectStart);
        keyPosition.setType();
        keyPosition.setValue(0, "percentX");
        keyPosition.setValue(0, "percentY");
        KeyPosition keyPosition2 = new KeyPosition();
        keyPosition2.setFramePosition(this.mMotionEffectEnd);
        keyPosition2.setType();
        keyPosition2.setValue(1, "percentX");
        keyPosition2.setValue(1, "percentY");
        KeyAttributes keyAttributes6 = null;
        if (this.mMotionEffectTranslationX > 0) {
            keyAttributes = new KeyAttributes();
            keyAttributes2 = new KeyAttributes();
            keyAttributes.setValue(Integer.valueOf(this.mMotionEffectTranslationX), "translationX");
            keyAttributes.setFramePosition(this.mMotionEffectEnd);
            keyAttributes2.setValue(0, "translationX");
            keyAttributes2.setFramePosition(this.mMotionEffectEnd - 1);
        } else {
            keyAttributes = null;
            keyAttributes2 = null;
        }
        if (this.mMotionEffectTranslationY > 0) {
            keyAttributes6 = new KeyAttributes();
            keyAttributes3 = new KeyAttributes();
            keyAttributes6.setValue(Integer.valueOf(this.mMotionEffectTranslationY), "translationY");
            keyAttributes6.setFramePosition(this.mMotionEffectEnd);
            keyAttributes3.setValue(0, "translationY");
            keyAttributes3.setFramePosition(this.mMotionEffectEnd - 1);
        } else {
            keyAttributes3 = null;
        }
        int i = this.mFadeMove;
        if (i == -1) {
            int[] iArr = new int[4];
            for (View view : views) {
                MotionController motionController = hashMap2.get(view);
                if (motionController != null) {
                    float finalX = motionController.getFinalX() - motionController.getStartX();
                    float finalY = motionController.getFinalY() - motionController.getStartY();
                    if (finalY < 0.0f) {
                        iArr[1] = iArr[1] + 1;
                    }
                    if (finalY > 0.0f) {
                        iArr[0] = iArr[0] + 1;
                    }
                    if (finalX > 0.0f) {
                        iArr[3] = iArr[3] + 1;
                    }
                    if (finalX < 0.0f) {
                        iArr[2] = iArr[2] + 1;
                    }
                }
            }
            int i2 = iArr[0];
            i = 0;
            for (int i3 = 1; i3 < 4; i3++) {
                int i4 = iArr[i3];
                if (i2 < i4) {
                    i = i3;
                    i2 = i4;
                }
            }
        }
        int i5 = 0;
        while (i5 < views.length) {
            MotionController motionController2 = hashMap2.get(views[i5]);
            if (motionController2 != null) {
                float finalX2 = motionController2.getFinalX() - motionController2.getStartX();
                float finalY2 = motionController2.getFinalY() - motionController2.getStartY();
                if (i == 0) {
                    if (finalY2 > 0.0f) {
                        if (this.mMotionEffectStrictMove) {
                        }
                        z = false;
                    }
                    z = true;
                } else if (i == 1) {
                    if (finalY2 < 0.0f) {
                        if (this.mMotionEffectStrictMove) {
                        }
                        z = false;
                    }
                    z = true;
                } else if (i == 2) {
                    if (finalX2 < 0.0f) {
                        if (this.mMotionEffectStrictMove) {
                        }
                        z = false;
                    }
                    z = true;
                } else {
                    if (i == 3) {
                        if (finalX2 > 0.0f) {
                            if (this.mMotionEffectStrictMove) {
                            }
                            z = false;
                        }
                    }
                    z = true;
                }
                if (z) {
                    int i6 = this.mViewTransitionId;
                    if (i6 == -1) {
                        motionController2.addKey(keyAttributes4);
                        motionController2.addKey(keyAttributes5);
                        motionController2.addKey(keyPosition);
                        motionController2.addKey(keyPosition2);
                        if (this.mMotionEffectTranslationX > 0) {
                            motionController2.addKey(keyAttributes);
                            motionController2.addKey(keyAttributes2);
                        }
                        if (this.mMotionEffectTranslationY > 0) {
                            motionController2.addKey(keyAttributes6);
                            motionController2.addKey(keyAttributes3);
                        }
                    } else {
                        motionLayout.applyViewTransition(i6, motionController2);
                    }
                    i5++;
                    hashMap2 = hashMap;
                }
            }
            i5++;
            hashMap2 = hashMap;
        }
    }

    public MotionEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMotionEffectAlpha = 0.1f;
        this.mMotionEffectStart = 49;
        this.mMotionEffectEnd = 50;
        this.mMotionEffectTranslationX = 0;
        this.mMotionEffectTranslationY = 0;
        this.mMotionEffectStrictMove = true;
        this.mViewTransitionId = -1;
        this.mFadeMove = -1;
        init(context, attributeSet);
    }

    public MotionEffect(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMotionEffectAlpha = 0.1f;
        this.mMotionEffectStart = 49;
        this.mMotionEffectEnd = 50;
        this.mMotionEffectTranslationX = 0;
        this.mMotionEffectTranslationY = 0;
        this.mMotionEffectStrictMove = true;
        this.mViewTransitionId = -1;
        this.mFadeMove = -1;
        init(context, attributeSet);
    }
}
