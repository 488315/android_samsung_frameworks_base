package android.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.text.method.TranslationTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.translation.UiTranslationManager;
import android.view.translation.ViewTranslationCallback;
import java.lang.ref.WeakReference;

/* loaded from: classes5.dex */
public class TextViewTranslationCallback implements ViewTranslationCallback {
    private static final char COMPAT_PAD_CHARACTER = 8194;
    private static final boolean DEBUG = Log.isLoggable(UiTranslationManager.LOG_TAG, 3);
    private static final String TAG = "TextViewTranslationCb";
    private ValueAnimator mAnimator;
    private CharSequence mContentDescription;
    private CharSequence mPaddedText;
    private TranslationTransformationMethod mTranslationTransformation;
    private boolean mIsShowingTranslation = false;
    private boolean mAnimationRunning = false;
    private boolean mIsTextPaddingEnabled = false;
    private boolean mOriginalIsTextSelectable = false;
    private int mOriginalFocusable = 0;
    private boolean mOriginalFocusableInTouchMode = false;
    private boolean mOriginalClickable = false;
    private boolean mOriginalLongClickable = false;
    private int mAnimationDurationMillis = 250;
    private int mTranslatedTextLength = 0;

    private void clearTranslationTransformation() {
        if (DEBUG) {
            Log.v(TAG, "clearTranslationTransformation: " + this.mTranslationTransformation);
        }
        this.mTranslationTransformation = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005e  */
    @Override // android.view.translation.ViewTranslationCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onShowTranslation(android.view.View r6) {
        /*
            r5 = this;
            r0 = 0
            if (r6 == 0) goto L2c
            android.view.translation.ViewTranslationResponse r1 = r6.getViewTranslationResponse()
            if (r1 == 0) goto L2c
            android.view.translation.ViewTranslationResponse r1 = r6.getViewTranslationResponse()
            java.lang.String r2 = "android:text"
            android.view.translation.TranslationResponseValue r1 = r1.getValue(r2)
            if (r1 == 0) goto L2c
            android.view.translation.ViewTranslationResponse r1 = r6.getViewTranslationResponse()
            android.view.translation.TranslationResponseValue r1 = r1.getValue(r2)
            java.lang.CharSequence r2 = r1.getText()
            if (r2 == 0) goto L2c
            java.lang.CharSequence r1 = r1.getText()
            int r1 = r1.length()
            goto L2d
        L2c:
            r1 = r0
        L2d:
            boolean r2 = r5.mIsShowingTranslation
            java.lang.String r3 = "TextViewTranslationCb"
            if (r2 == 0) goto L50
            int r2 = r5.mTranslatedTextLength
            if (r2 != r1) goto L50
            boolean r5 = android.widget.TextViewTranslationCallback.DEBUG
            if (r5 == 0) goto L4f
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r6)
            java.lang.String r6 = " is already showing translated text."
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r3, r5)
        L4f:
            return r0
        L50:
            android.view.translation.ViewTranslationResponse r2 = r6.getViewTranslationResponse()
            r5.mTranslatedTextLength = r1
            if (r2 != 0) goto L5e
            java.lang.String r5 = "onShowTranslation() shouldn't be called before onViewTranslationResponse()."
            android.util.Log.e(r3, r5)
            return r0
        L5e:
            r0 = r6
            android.widget.TextView r0 = (android.widget.TextView) r0
            android.text.method.TranslationTransformationMethod r1 = r5.mTranslationTransformation
            if (r1 == 0) goto L6f
            android.view.translation.ViewTranslationResponse r1 = r1.getViewTranslationResponse()
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L7a
        L6f:
            android.text.method.TransformationMethod r1 = r0.getTransformationMethod()
            android.text.method.TranslationTransformationMethod r3 = new android.text.method.TranslationTransformationMethod
            r3.<init>(r2, r1)
            r5.mTranslationTransformation = r3
        L7a:
            android.text.method.TranslationTransformationMethod r1 = r5.mTranslationTransformation
            java.lang.ref.WeakReference r3 = new java.lang.ref.WeakReference
            r3.<init>(r0)
            android.widget.TextViewTranslationCallback$$ExternalSyntheticLambda1 r4 = new android.widget.TextViewTranslationCallback$$ExternalSyntheticLambda1
            r4.<init>()
            r5.runChangeTextWithAnimationIfNeeded(r0, r4)
            java.util.Set r0 = r2.getKeys()
            java.lang.String r1 = "android:content_description"
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto Lac
            android.view.translation.TranslationResponseValue r0 = r2.getValue(r1)
            java.lang.CharSequence r0 = r0.getText()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto Lac
            java.lang.CharSequence r1 = r6.getContentDescription()
            r5.mContentDescription = r1
            r6.setContentDescription(r0)
        Lac:
            r5 = 1
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TextViewTranslationCallback.onShowTranslation(android.view.View):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onShowTranslation$0(WeakReference weakReference, TransformationMethod transformationMethod) {
        this.mIsShowingTranslation = true;
        this.mAnimationRunning = false;
        TextView textView = (TextView) weakReference.get();
        if (textView == null) {
            return;
        }
        boolean isTextSelectable = textView.isTextSelectable();
        this.mOriginalIsTextSelectable = isTextSelectable;
        if (isTextSelectable) {
            this.mOriginalFocusableInTouchMode = textView.isFocusableInTouchMode();
            this.mOriginalFocusable = textView.getFocusable();
            this.mOriginalClickable = textView.isClickable();
            this.mOriginalLongClickable = textView.isLongClickable();
            textView.setTextIsSelectable(false);
        }
        textView.setTransformationMethod(transformationMethod);
    }

    @Override // android.view.translation.ViewTranslationCallback
    public boolean onHideTranslation(View view) {
        if (view.getViewTranslationResponse() == null) {
            Log.e(TAG, "onHideTranslation() shouldn't be called before onViewTranslationResponse().");
            return false;
        }
        TranslationTransformationMethod translationTransformationMethod = this.mTranslationTransformation;
        if (translationTransformationMethod != null) {
            final TransformationMethod originalTransformationMethod = translationTransformationMethod.getOriginalTransformationMethod();
            TextView textView = (TextView) view;
            final WeakReference weakReference = new WeakReference(textView);
            runChangeTextWithAnimationIfNeeded(textView, new Runnable() { // from class: android.widget.TextViewTranslationCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TextViewTranslationCallback.this.lambda$onHideTranslation$1(weakReference, originalTransformationMethod);
                }
            });
            if (TextUtils.isEmpty(this.mContentDescription)) {
                return true;
            }
            view.setContentDescription(this.mContentDescription);
            return true;
        }
        if (DEBUG) {
            Log.w(TAG, "onHideTranslation(): no translated text.");
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onHideTranslation$1(WeakReference weakReference, TransformationMethod transformationMethod) {
        this.mIsShowingTranslation = false;
        this.mAnimationRunning = false;
        this.mTranslatedTextLength = 0;
        TextView textView = (TextView) weakReference.get();
        if (textView == null) {
            return;
        }
        textView.setTransformationMethod(transformationMethod);
        if (!this.mOriginalIsTextSelectable || textView.isTextSelectable()) {
            return;
        }
        textView.setTextIsSelectable(true);
        textView.setFocusableInTouchMode(this.mOriginalFocusableInTouchMode);
        textView.setFocusable(this.mOriginalFocusable);
        textView.setClickable(this.mOriginalClickable);
        textView.setLongClickable(this.mOriginalLongClickable);
    }

    @Override // android.view.translation.ViewTranslationCallback
    public boolean onClearTranslation(View view) {
        if (this.mTranslationTransformation != null) {
            onHideTranslation(view);
            clearTranslationTransformation();
            this.mPaddedText = null;
            this.mContentDescription = null;
            this.mTranslatedTextLength = 0;
            return true;
        }
        if (DEBUG) {
            Log.w(TAG, "onClearTranslation(): no translated text.");
        }
        return false;
    }

    public boolean isShowingTranslation() {
        return this.mIsShowingTranslation;
    }

    public boolean isAnimationRunning() {
        return this.mAnimationRunning;
    }

    @Override // android.view.translation.ViewTranslationCallback
    public void enableContentPadding() {
        this.mIsTextPaddingEnabled = true;
    }

    boolean isTextPaddingEnabled() {
        return this.mIsTextPaddingEnabled;
    }

    CharSequence getPaddedText(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            return null;
        }
        if (this.mPaddedText == null) {
            this.mPaddedText = computePaddedText(charSequence, charSequence2);
        }
        return this.mPaddedText;
    }

    private CharSequence computePaddedText(CharSequence charSequence, CharSequence charSequence2) {
        int length;
        if (charSequence2 == null || (length = charSequence2.length()) <= charSequence.length()) {
            return charSequence;
        }
        StringBuilder sb = new StringBuilder(length);
        sb.append(charSequence);
        for (int length2 = charSequence.length(); length2 < length; length2++) {
            sb.append(COMPAT_PAD_CHARACTER);
        }
        return sb;
    }

    @Override // android.view.translation.ViewTranslationCallback
    public void setAnimationDurationMillis(int i) {
        this.mAnimationDurationMillis = i;
    }

    private void runChangeTextWithAnimationIfNeeded(final TextView textView, final Runnable runnable) {
        if (!ValueAnimator.areAnimatorsEnabled()) {
            runnable.run();
            return;
        }
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator != null) {
            valueAnimator.end();
        }
        this.mAnimationRunning = true;
        ValueAnimator ofArgb = ValueAnimator.ofArgb(textView.getCurrentTextColor(), colorWithAlpha(textView.getCurrentTextColor(), 0));
        this.mAnimator = ofArgb;
        ofArgb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.TextViewTranslationCallback$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                TextView.this.setTextColor(((Integer) valueAnimator2.getAnimatedValue()).intValue());
            }
        });
        this.mAnimator.setRepeatMode(2);
        this.mAnimator.setRepeatCount(1);
        this.mAnimator.setDuration(this.mAnimationDurationMillis);
        final ColorStateList textColors = textView.getTextColors();
        final WeakReference weakReference = new WeakReference(textView);
        this.mAnimator.addListener(new Animator.AnimatorListener() { // from class: android.widget.TextViewTranslationCallback.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                TextView textView2 = (TextView) weakReference.get();
                if (textView2 != null) {
                    textView2.setTextColor(textColors);
                }
                TextViewTranslationCallback.this.mAnimator = null;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
                runnable.run();
            }
        });
        this.mAnimator.start();
    }

    private static int colorWithAlpha(int i, int i2) {
        return Color.argb(i2, Color.red(i), Color.green(i), Color.blue(i));
    }
}
