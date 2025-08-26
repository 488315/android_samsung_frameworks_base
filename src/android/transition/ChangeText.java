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
        int iIntValue;
        int iIntValue2;
        final int i2;
        final int i3;
        ValueAnimator valueAnimator;
        final int i4;
        final CharSequence charSequence;
        Animator animatorOfFloat;
        ValueAnimator valueAnimatorOfInt;
        Animator animator;
        Animator animator2;
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
            iIntValue = map.get(PROPNAME_TEXT_SELECTION_START) != null ? ((Integer) map.get(PROPNAME_TEXT_SELECTION_START)).intValue() : -1;
            int iIntValue3 = map.get(PROPNAME_TEXT_SELECTION_END) != null ? ((Integer) map.get(PROPNAME_TEXT_SELECTION_END)).intValue() : iIntValue;
            iIntValue = map2.get(PROPNAME_TEXT_SELECTION_START) != null ? ((Integer) map2.get(PROPNAME_TEXT_SELECTION_START)).intValue() : -1;
            iIntValue2 = map2.get(PROPNAME_TEXT_SELECTION_END) != null ? ((Integer) map2.get(PROPNAME_TEXT_SELECTION_END)).intValue() : iIntValue;
            i = iIntValue3;
        } else {
            i = -1;
            iIntValue = -1;
            iIntValue2 = -1;
        }
        if (str.equals(charSequence2)) {
            return null;
        }
        if (this.mChangeBehavior != 2) {
            textView.lambda$setTextAsync$0(str);
            if (z) {
                setSelection((EditText) textView, iIntValue, i);
            }
        }
        int i5 = 0;
        if (this.mChangeBehavior == 0) {
            animatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            i2 = iIntValue;
            charSequence = str;
            i3 = iIntValue2;
            animatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: android.transition.ChangeText.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator3) {
                    if (charSequence.equals(textView.getText())) {
                        textView.lambda$setTextAsync$0(charSequence2);
                        TextView textView2 = textView;
                        if (textView2 instanceof EditText) {
                            ChangeText.this.setSelection((EditText) textView2, i2, i3);
                        }
                    }
                }
            });
        } else {
            i2 = iIntValue;
            i3 = iIntValue2;
            final int iIntValue4 = ((Integer) map.get(PROPNAME_TEXT_COLOR)).intValue();
            int iIntValue5 = ((Integer) map2.get(PROPNAME_TEXT_COLOR)).intValue();
            int i6 = this.mChangeBehavior;
            if (i6 == 3 || i6 == 1) {
                ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(Color.alpha(iIntValue4), 0);
                valueAnimator = null;
                valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: android.transition.ChangeText.2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        int iIntValue6 = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                        textView.setTextColor((iIntValue4 & 16777215) | (iIntValue6 << 24));
                    }
                });
                CharSequence charSequence3 = str;
                i4 = iIntValue5;
                charSequence = charSequence3;
                valueAnimatorOfInt2.addListener(new AnimatorListenerAdapter() { // from class: android.transition.ChangeText.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator3) {
                        if (charSequence.equals(textView.getText())) {
                            textView.lambda$setTextAsync$0(charSequence2);
                            TextView textView2 = textView;
                            if (textView2 instanceof EditText) {
                                ChangeText.this.setSelection((EditText) textView2, i2, i3);
                            }
                        }
                        textView.setTextColor(i4);
                    }
                });
                animatorOfFloat = valueAnimatorOfInt2;
            } else {
                CharSequence charSequence4 = str;
                i4 = iIntValue5;
                charSequence = charSequence4;
                valueAnimator = null;
                animatorOfFloat = null;
            }
            int i7 = this.mChangeBehavior;
            if (i7 == 3 || i7 == 2) {
                valueAnimatorOfInt = ValueAnimator.ofInt(0, Color.alpha(i4));
                valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: android.transition.ChangeText.4
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        int iIntValue6 = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                        textView.setTextColor((i4 & 16777215) | (iIntValue6 << 24));
                    }
                });
                valueAnimatorOfInt.addListener(new AnimatorListenerAdapter(this) { // from class: android.transition.ChangeText.5
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator3) {
                        textView.setTextColor(i4);
                    }
                });
            } else {
                valueAnimatorOfInt = valueAnimator;
            }
            if (animatorOfFloat != null && valueAnimatorOfInt != null) {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playSequentially(animatorOfFloat, valueAnimatorOfInt);
                animator = animatorSet;
            } else if (animatorOfFloat != null) {
                i5 = i4;
            } else {
                animator = valueAnimatorOfInt;
            }
            i5 = i4;
            animator2 = animator;
            final CharSequence charSequence5 = charSequence;
            final int i8 = i2;
            final int i9 = i3;
            final int i10 = iIntValue;
            final int i11 = i5;
            addListener(new TransitionListenerAdapter() { // from class: android.transition.ChangeText.6
                int mPausedColor = 0;

                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionPause(Transition transition) {
                    if (ChangeText.this.mChangeBehavior != 2) {
                        textView.lambda$setTextAsync$0(charSequence2);
                        TextView textView2 = textView;
                        if (textView2 instanceof EditText) {
                            ChangeText.this.setSelection((EditText) textView2, i8, i9);
                        }
                    }
                    if (ChangeText.this.mChangeBehavior > 0) {
                        this.mPausedColor = textView.getCurrentTextColor();
                        textView.setTextColor(i11);
                    }
                }

                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionResume(Transition transition) {
                    if (ChangeText.this.mChangeBehavior != 2) {
                        textView.lambda$setTextAsync$0(charSequence5);
                        TextView textView2 = textView;
                        if (textView2 instanceof EditText) {
                            ChangeText.this.setSelection((EditText) textView2, i10, i);
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
            return animator2;
        }
        animator2 = animatorOfFloat;
        final CharSequence charSequence52 = charSequence;
        final int i82 = i2;
        final int i92 = i3;
        final int i102 = iIntValue;
        final int i112 = i5;
        addListener(new TransitionListenerAdapter() { // from class: android.transition.ChangeText.6
            int mPausedColor = 0;

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionPause(Transition transition) {
                if (ChangeText.this.mChangeBehavior != 2) {
                    textView.lambda$setTextAsync$0(charSequence2);
                    TextView textView2 = textView;
                    if (textView2 instanceof EditText) {
                        ChangeText.this.setSelection((EditText) textView2, i82, i92);
                    }
                }
                if (ChangeText.this.mChangeBehavior > 0) {
                    this.mPausedColor = textView.getCurrentTextColor();
                    textView.setTextColor(i112);
                }
            }

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionResume(Transition transition) {
                if (ChangeText.this.mChangeBehavior != 2) {
                    textView.lambda$setTextAsync$0(charSequence52);
                    TextView textView2 = textView;
                    if (textView2 instanceof EditText) {
                        ChangeText.this.setSelection((EditText) textView2, i102, i);
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
        return animator2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSelection(EditText editText, int i, int i2) {
        if (i < 0 || i2 < 0) {
            return;
        }
        editText.setSelection(i, i2);
    }
}
