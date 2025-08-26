package android.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/* loaded from: classes5.dex */
public class SemRemoteViewsViewAnimation extends SemRemoteViewsAnimation {
    public static final Parcelable.Creator<SemRemoteViewsViewAnimation> CREATOR = new Parcelable.Creator<SemRemoteViewsViewAnimation>() { // from class: android.widget.SemRemoteViewsViewAnimation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsViewAnimation createFromParcel(Parcel parcel) {
            return new SemRemoteViewsViewAnimation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsViewAnimation[] newArray(int i) {
            return new SemRemoteViewsViewAnimation[i];
        }
    };
    private static final String LOG_TAG = "SemRemoteViewsViewAnimation";
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

    public SemRemoteViewsViewAnimation(int i, int i2) {
        super(i);
        this.mAnimResId = i2;
    }

    protected SemRemoteViewsViewAnimation(Parcel parcel) {
        super(parcel);
        this.mAnimResId = parcel.readInt();
    }

    @Override // android.widget.SemRemoteViewsAnimation, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mAnimResId);
    }

    public static void writeToParcel(SemRemoteViewsViewAnimation semRemoteViewsViewAnimation, Parcel parcel) {
        if (semRemoteViewsViewAnimation != null) {
            semRemoteViewsViewAnimation.writeToParcel(parcel, 0);
        } else {
            parcel.writeString(null);
        }
    }

    @Override // android.widget.SemRemoteViewsAnimation
    protected void startAnimation(View view) {
        View viewFindViewById;
        Animation animationLoadAnimation;
        if (view == null || this.mAnimResId <= 0 || this.mIsExpired || (viewFindViewById = view.findViewById(this.mViewId)) == null || (animationLoadAnimation = AnimationUtils.loadAnimation(view.getContext(), this.mAnimResId)) == null) {
            return;
        }
        animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: android.widget.SemRemoteViewsViewAnimation.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                SemRemoteViewsViewAnimation.this.mIsExpired = true;
            }
        });
        if (this.mIsExpired) {
            animationLoadAnimation.setDuration(0L);
        }
        viewFindViewById.startAnimation(animationLoadAnimation);
    }
}
