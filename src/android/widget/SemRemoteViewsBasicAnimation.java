package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/* loaded from: classes5.dex */
public class SemRemoteViewsBasicAnimation extends SemRemoteViewsAnimation {
    public static final Parcelable.Creator<SemRemoteViewsBasicAnimation> CREATOR = new Parcelable.Creator<SemRemoteViewsBasicAnimation>() { // from class: android.widget.SemRemoteViewsBasicAnimation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsBasicAnimation createFromParcel(Parcel parcel) {
            return new SemRemoteViewsBasicAnimation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsBasicAnimation[] newArray(int i) {
            return new SemRemoteViewsBasicAnimation[i];
        }
    };
    private static final String LOG_TAG = "SemRemoteViewsBasicAnimation";
    public static final String TYPE_TEXT_SWITCHER = "text_switcher";
    private static final String TYPE_UNKNOWN = "unknown";
    private final String mAnimationType;
    private final Bundle mExtras;

    @Override // android.widget.SemRemoteViewsAnimation, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.SemRemoteViewsAnimation
    /* renamed from: endAnimation */
    public void lambda$play$0(View view) {
    }

    public SemRemoteViewsBasicAnimation(int i, String str, Bundle bundle) {
        super(i);
        this.mAnimationType = str;
        this.mExtras = bundle;
    }

    protected SemRemoteViewsBasicAnimation(Parcel parcel) {
        super(parcel);
        this.mAnimationType = parcel.readString();
        this.mExtras = parcel.readBundle(getClass().getClassLoader());
    }

    @Override // android.widget.SemRemoteViewsAnimation, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mAnimationType);
        parcel.writeBundle(this.mExtras);
    }

    public static void writeToParcel(SemRemoteViewsBasicAnimation semRemoteViewsBasicAnimation, Parcel parcel) {
        if (semRemoteViewsBasicAnimation != null) {
            semRemoteViewsBasicAnimation.writeToParcel(parcel, 0);
        } else {
            parcel.writeString(null);
        }
    }

    @Override // android.widget.SemRemoteViewsAnimation
    protected void startAnimation(View view) {
        View viewFindViewById;
        String string;
        if (this.mIsExpired || view == null || (viewFindViewById = view.findViewById(this.mViewId)) == null) {
            return;
        }
        String str = this.mAnimationType;
        str.hashCode();
        if (str.equals(TYPE_TEXT_SWITCHER) && (viewFindViewById instanceof TextView) && (string = this.mExtras.getString("new_text")) != null) {
            animateTextSwitcher((TextView) viewFindViewById, string);
        }
    }

    private void animateTextSwitcher(final TextView textView, final CharSequence charSequence) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(textView, "alpha", 1.0f, 0.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(textView, "translationY", 0.0f, -50.0f);
        if (objectAnimatorOfFloat == null || objectAnimatorOfFloat2 == null) {
            return;
        }
        objectAnimatorOfFloat.setDuration(300L);
        objectAnimatorOfFloat2.setDuration(300L);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.addListener(new AnimatorListenerAdapter(this) { // from class: android.widget.SemRemoteViewsBasicAnimation.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                textView.lambda$setTextAsync$0(charSequence);
            }
        });
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(textView, "alpha", 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(textView, "translationY", 50.0f, 0.0f);
        if (objectAnimatorOfFloat3 == null || objectAnimatorOfFloat4 == null) {
            return;
        }
        objectAnimatorOfFloat3.setDuration(300L);
        objectAnimatorOfFloat4.setDuration(300L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(objectAnimatorOfFloat3, objectAnimatorOfFloat4);
        animatorSet2.addListener(new AnimatorListenerAdapter() { // from class: android.widget.SemRemoteViewsBasicAnimation.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                textView.setTranslationY(50.0f);
                textView.setAlpha(0.0f);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                textView.setTranslationY(0.0f);
                SemRemoteViewsBasicAnimation.this.mIsExpired = true;
            }
        });
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playSequentially(animatorSet, animatorSet2);
        animatorSet3.start();
    }
}
