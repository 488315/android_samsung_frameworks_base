package android.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.text.method.TranslationTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.translation.TranslationResponseValue;
import android.view.translation.UiTranslationManager;
import android.view.translation.ViewTranslationCallback;
import android.view.translation.ViewTranslationRequest;
import android.view.translation.ViewTranslationResponse;
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x002c  */
    @Override // android.view.translation.ViewTranslationCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onShowTranslation(View view) throws Resources.NotFoundException {
        int length;
        if (view == null || view.getViewTranslationResponse() == null || view.getViewTranslationResponse().getValue(ViewTranslationRequest.ID_TEXT) == null) {
            length = 0;
        } else {
            TranslationResponseValue value = view.getViewTranslationResponse().getValue(ViewTranslationRequest.ID_TEXT);
            if (value.getText() != null) {
                length = value.getText().length();
            }
        }
        if (this.mIsShowingTranslation && this.mTranslatedTextLength == length) {
            if (DEBUG) {
                Log.d(TAG, view + " is already showing translated text.");
            }
            return false;
        }
        ViewTranslationResponse viewTranslationResponse = view.getViewTranslationResponse();
        this.mTranslatedTextLength = length;
        if (viewTranslationResponse == null) {
            Log.e(TAG, "onShowTranslation() shouldn't be called before onViewTranslationResponse().");
            return false;
        }
        TextView textView = (TextView) view;
        TranslationTransformationMethod translationTransformationMethod = this.mTranslationTransformation;
        if (translationTransformationMethod == null || !viewTranslationResponse.equals(translationTransformationMethod.getViewTranslationResponse())) {
            this.mTranslationTransformation = new TranslationTransformationMethod(viewTranslationResponse, textView.getTransformationMethod());
        }
        final TranslationTransformationMethod translationTransformationMethod2 = this.mTranslationTransformation;
        final WeakReference weakReference = new WeakReference(textView);
        runChangeTextWithAnimationIfNeeded(textView, new Runnable() { // from class: android.widget.TextViewTranslationCallback$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onShowTranslation$0(weakReference, translationTransformationMethod2);
            }
        });
        if (!viewTranslationResponse.getKeys().contains(ViewTranslationRequest.ID_CONTENT_DESCRIPTION)) {
            return true;
        }
        CharSequence text = viewTranslationResponse.getValue(ViewTranslationRequest.ID_CONTENT_DESCRIPTION).getText();
        if (TextUtils.isEmpty(text)) {
            return true;
        }
        this.mContentDescription = view.getContentDescription();
        view.setContentDescription(text);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onShowTranslation$0(WeakReference weakReference, TransformationMethod transformationMethod) {
        this.mIsShowingTranslation = true;
        this.mAnimationRunning = false;
        TextView textView = (TextView) weakReference.get();
        if (textView == null) {
            return;
        }
        boolean zIsTextSelectable = textView.isTextSelectable();
        this.mOriginalIsTextSelectable = zIsTextSelectable;
        if (zIsTextSelectable) {
            this.mOriginalFocusableInTouchMode = textView.isFocusableInTouchMode();
            this.mOriginalFocusable = textView.getFocusable();
            this.mOriginalClickable = textView.isClickable();
            this.mOriginalLongClickable = textView.isLongClickable();
            textView.setTextIsSelectable(false);
        }
        textView.setTransformationMethod(transformationMethod);
    }

    @Override // android.view.translation.ViewTranslationCallback
    public boolean onHideTranslation(View view) throws Resources.NotFoundException {
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
                    this.f$0.lambda$onHideTranslation$1(weakReference, originalTransformationMethod);
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
    public boolean onClearTranslation(View view) throws Resources.NotFoundException {
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
        ValueAnimator valueAnimatorOfArgb = ValueAnimator.ofArgb(textView.getCurrentTextColor(), colorWithAlpha(textView.getCurrentTextColor(), 0));
        this.mAnimator = valueAnimatorOfArgb;
        valueAnimatorOfArgb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.TextViewTranslationCallback$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                textView.setTextColor(((Integer) valueAnimator2.getAnimatedValue()).intValue());
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
