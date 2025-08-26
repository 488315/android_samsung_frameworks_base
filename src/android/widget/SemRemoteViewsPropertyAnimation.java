package android.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/* loaded from: classes5.dex */
public class SemRemoteViewsPropertyAnimation extends SemRemoteViewsAnimation {
    public static final Parcelable.Creator<SemRemoteViewsPropertyAnimation> CREATOR = new Parcelable.Creator<SemRemoteViewsPropertyAnimation>() { // from class: android.widget.SemRemoteViewsPropertyAnimation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsPropertyAnimation createFromParcel(Parcel parcel) {
            return new SemRemoteViewsPropertyAnimation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsPropertyAnimation[] newArray(int i) {
            return new SemRemoteViewsPropertyAnimation[i];
        }
    };
    private static final String LOG_TAG = "SemRemoteViewsPropertyAnimation";
    private final int mAnimResId;

    @Override // android.widget.SemRemoteViewsAnimation, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.SemRemoteViewsAnimation
    /* renamed from: endAnimation */
    public void lambda$play$0(View view) {
    }

    public SemRemoteViewsPropertyAnimation(int i, int i2) {
        super(i);
        this.mAnimResId = i2;
    }

    protected SemRemoteViewsPropertyAnimation(Parcel parcel) {
        super(parcel);
        this.mAnimResId = parcel.readInt();
    }

    @Override // android.widget.SemRemoteViewsAnimation, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mAnimResId);
    }

    public static void writeToParcel(SemRemoteViewsPropertyAnimation semRemoteViewsPropertyAnimation, Parcel parcel) {
        if (semRemoteViewsPropertyAnimation != null) {
            semRemoteViewsPropertyAnimation.writeToParcel(parcel, 0);
        } else {
            parcel.writeString(null);
        }
    }

    @Override // android.widget.SemRemoteViewsAnimation
    protected void startAnimation(View view) {
        View viewFindViewById;
        AnimatorSet animatorSet;
        if (view == null || this.mAnimResId <= 0 || (viewFindViewById = view.findViewById(this.mViewId)) == null || (animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), this.mAnimResId)) == null) {
            return;
        }
        animatorSet.setTarget(viewFindViewById);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: android.widget.SemRemoteViewsPropertyAnimation.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SemRemoteViewsPropertyAnimation.this.mIsExpired = true;
            }
        });
        if (this.mIsExpired) {
            animatorSet.setDuration(0L);
        }
        animatorSet.start();
    }
}
