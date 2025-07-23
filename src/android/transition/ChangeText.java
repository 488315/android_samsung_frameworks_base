package android.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Map;

/* loaded from: classes4.dex */
public class ChangeText extends Transition {
    public static final int CHANGE_BEHAVIOR_IN = 2;
    public static final int CHANGE_BEHAVIOR_KEEP = 0;
    public static final int CHANGE_BEHAVIOR_OUT = 1;
    public static final int CHANGE_BEHAVIOR_OUT_IN = 3;
    private static final String LOG_TAG = "TextChange";
    private static final String PROPNAME_TEXT_COLOR = "android:textchange:textColor";
    private int mChangeBehavior = 0;
    private static final String PROPNAME_TEXT = "android:textchange:text";
    private static final String PROPNAME_TEXT_SELECTION_START = "android:textchange:textSelectionStart";
    private static final String PROPNAME_TEXT_SELECTION_END = "android:textchange:textSelectionEnd";
    private static final String[] sTransitionProperties = {PROPNAME_TEXT, PROPNAME_TEXT_SELECTION_START, PROPNAME_TEXT_SELECTION_END};

    public ChangeText setChangeBehavior(int i) {
        if (i >= 0 && i <= 3) {
            this.mChangeBehavior = i;
        }
        return this;
    }

    @Override // android.transition.Transition
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public int getChangeBehavior() {
        return this.mChangeBehavior;
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof TextView) {
            TextView textView = (TextView) transitionValues.view;
            transitionValues.values.put(PROPNAME_TEXT, textView.getText());
            if (textView instanceof EditText) {
                transitionValues.values.put(PROPNAME_TEXT_SELECTION_START, Integer.valueOf(textView.getSelectionStart()));
                transitionValues.values.put(PROPNAME_TEXT_SELECTION_END, Integer.valueOf(textView.getSelectionEnd()));
            }
            if (this.mChangeBehavior > 0) {
                transitionValues.values.put(PROPNAME_TEXT_COLOR, Integer.valueOf(textView.getCurrentTextColor()));
            }
        }
    }

    @Override // android.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.transition.Transition
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        final int i;
        int i2;
        int i3;
        final int i4;
        final int i5;
        ValueAnimator valueAnimator;
        final int i6;
        final CharSequence charSequence;
        Animator animator;
        ValueAnimator ofInt;
        Animator animator2;
        Animator animator3;
        if (transitionValues == null || transitionValues2 == null || !(transitionValues.view instanceof TextView) || !(transitionValues2.view instanceof TextView)) {
            return null;
        }
        final TextView textView = (TextView) transitionValues2.view;
        Map<String, Object> map = transitionValues.values;
        Map<String, Object> map2 = transitionValues2.values;
        String str = map.get(PROPNAME_TEXT) != null ? (CharSequence) map.get(PROPNAME_TEXT) : "";
        final CharSequence charSequence2 = map2.get(PROPNAME_TEXT) != null ? (CharSequence) map2.get(PROPNAME_TEXT) : "";
        boolean z = textView instanceof EditText;
        if (z) {
            i2 = map.get(PROPNAME_TEXT_SELECTION_START) != null ? ((Integer) map.get(PROPNAME_TEXT_SELECTION_START)).intValue() : -1;
            int intValue = map.get(PROPNAME_TEXT_SELECTION_END) != null ? ((Integer) map.get(PROPNAME_TEXT_SELECTION_END)).intValue() : i2;
            r6 = map2.get(PROPNAME_TEXT_SELECTION_START) != null ? ((Integer) map2.get(PROPNAME_TEXT_SELECTION_START)).intValue() : -1;
            i3 = map2.get(PROPNAME_TEXT_SELECTION_END) != null ? ((Integer) map2.get(PROPNAME_TEXT_SELECTION_END)).intValue() : r6;
            i = intValue;
        } else {
            i = -1;
            i2 = -1;
            i3 = -1;
        }
        if (str.equals(charSequence2)) {
            return null;
        }
        if (this.mChangeBehavior != 2) {
            textView.lambda$setTextAsync$0(str);
            if (z) {
                setSelection((EditText) textView, i2, i);
            }
        }
        int i7 = 0;
        if (this.mChangeBehavior == 0) {
            animator = ValueAnimator.ofFloat(0.0f, 1.0f);
            i4 = r6;
            charSequence = str;
            i5 = i3;
            animator.addListener(new AnimatorListenerAdapter() { // from class: android.transition.ChangeText.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator4) {
                    if (charSequence.equals(textView.getText())) {
                        textView.lambda$setTextAsync$0(charSequence2);
                        TextView textView2 = textView;
                        if (textView2 instanceof EditText) {
                            ChangeText.this.setSelection((EditText) textView2, i4, i5);
                        }
                    }
                }
            });
        } else {
            i4 = r6;
            i5 = i3;
            final int intValue2 = ((Integer) map.get(PROPNAME_TEXT_COLOR)).intValue();
            int intValue3 = ((Integer) map2.get(PROPNAME_TEXT_COLOR)).intValue();
            int i8 = this.mChangeBehavior;
            if (i8 == 3 || i8 == 1) {
                ValueAnimator ofInt2 = ValueAnimator.ofInt(Color.alpha(intValue2), 0);
                valueAnimator = null;
                ofInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: android.transition.ChangeText.2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        int intValue4 = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                        textView.setTextColor((intValue2 & 16777215) | (intValue4 << 24));
                    }
                });
                CharSequence charSequence3 = str;
                i6 = intValue3;
                charSequence = charSequence3;
                ofInt2.addListener(new AnimatorListenerAdapter() { // from class: android.transition.ChangeText.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator4) {
                        if (charSequence.equals(textView.getText())) {
                            textView.lambda$setTextAsync$0(charSequence2);
                            TextView textView2 = textView;
                            if (textView2 instanceof EditText) {
                                ChangeText.this.setSelection((EditText) textView2, i4, i5);
                            }
                        }
                        textView.setTextColor(i6);
                    }
                });
                animator = ofInt2;
            } else {
                CharSequence charSequence4 = str;
                i6 = intValue3;
                charSequence = charSequence4;
                valueAnimator = null;
                animator = null;
            }
            int i9 = this.mChangeBehavior;
            if (i9 == 3 || i9 == 2) {
                ofInt = ValueAnimator.ofInt(0, Color.alpha(i6));
                ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: android.transition.ChangeText.4
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        int intValue4 = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                        textView.setTextColor((i6 & 16777215) | (intValue4 << 24));
                    }
                });
                ofInt.addListener(new AnimatorListenerAdapter(this) { // from class: android.transition.ChangeText.5
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator4) {
                        textView.setTextColor(i6);
                    }
                });
            } else {
                ofInt = valueAnimator;
            }
            if (animator != null && ofInt != null) {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playSequentially(animator, ofInt);
                animator2 = animatorSet;
            } else if (animator != null) {
                i7 = i6;
            } else {
                animator2 = ofInt;
            }
            i7 = i6;
            animator3 = animator2;
            final CharSequence charSequence5 = charSequence;
            final int i10 = i4;
            final int i11 = i5;
            final int i12 = i2;
            final int i13 = i7;
            addListener(new TransitionListenerAdapter() { // from class: android.transition.ChangeText.6
                int mPausedColor = 0;

                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionPause(Transition transition) {
                    if (ChangeText.this.mChangeBehavior != 2) {
                        textView.lambda$setTextAsync$0(charSequence2);
                        TextView textView2 = textView;
                        if (textView2 instanceof EditText) {
                            ChangeText.this.setSelection((EditText) textView2, i10, i11);
                        }
                    }
                    if (ChangeText.this.mChangeBehavior > 0) {
                        this.mPausedColor = textView.getCurrentTextColor();
                        textView.setTextColor(i13);
                    }
                }

                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionResume(Transition transition) {
                    if (ChangeText.this.mChangeBehavior != 2) {
                        textView.lambda$setTextAsync$0(charSequence5);
                        TextView textView2 = textView;
                        if (textView2 instanceof EditText) {
                            ChangeText.this.setSelection((EditText) textView2, i12, i);
                        }
                    }
                    if (ChangeText.this.mChangeBehavior > 0) {
                        textView.setTextColor(this.mPausedColor);
                    }
                }

                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionEnd(Transition transition) {
                    transition.removeListener(this);
                }
            });
            return animator3;
        }
        animator3 = animator;
        final CharSequence charSequence52 = charSequence;
        final int i102 = i4;
        final int i112 = i5;
        final int i122 = i2;
        final int i132 = i7;
        addListener(new TransitionListenerAdapter() { // from class: android.transition.ChangeText.6
            int mPausedColor = 0;

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionPause(Transition transition) {
                if (ChangeText.this.mChangeBehavior != 2) {
                    textView.lambda$setTextAsync$0(charSequence2);
                    TextView textView2 = textView;
                    if (textView2 instanceof EditText) {
                        ChangeText.this.setSelection((EditText) textView2, i102, i112);
                    }
                }
                if (ChangeText.this.mChangeBehavior > 0) {
                    this.mPausedColor = textView.getCurrentTextColor();
                    textView.setTextColor(i132);
                }
            }

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionResume(Transition transition) {
                if (ChangeText.this.mChangeBehavior != 2) {
                    textView.lambda$setTextAsync$0(charSequence52);
                    TextView textView2 = textView;
                    if (textView2 instanceof EditText) {
                        ChangeText.this.setSelection((EditText) textView2, i122, i);
                    }
                }
                if (ChangeText.this.mChangeBehavior > 0) {
                    textView.setTextColor(this.mPausedColor);
                }
            }

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
            }
        });
        return animator3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSelection(EditText editText, int i, int i2) {
        if (i < 0 || i2 < 0) {
            return;
        }
        editText.setSelection(i, i2);
    }
}
