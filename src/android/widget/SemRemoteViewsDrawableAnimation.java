package android.widget;

import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

/* loaded from: classes5.dex */
public class SemRemoteViewsDrawableAnimation extends SemRemoteViewsAnimation {
    public static final Parcelable.Creator<SemRemoteViewsDrawableAnimation> CREATOR = new Parcelable.Creator<SemRemoteViewsDrawableAnimation>() { // from class: android.widget.SemRemoteViewsDrawableAnimation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsDrawableAnimation createFromParcel(Parcel parcel) {
            return new SemRemoteViewsDrawableAnimation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsDrawableAnimation[] newArray(int i) {
            return new SemRemoteViewsDrawableAnimation[i];
        }
    };
    private static final String LOG_TAG = "SemRemoteViewsDrawableAnimation";
    private Animatable2 mAnimatableDrawable;
    private final boolean mNeedToStart;
    private final int mResId;
    private final Uri mUri;

    @Override // android.widget.SemRemoteViewsAnimation, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.SemRemoteViewsAnimation
    /* renamed from: endAnimation */
    public void lambda$play$0(View view) {
    }

    public SemRemoteViewsDrawableAnimation(int i, boolean z) {
        super(i);
        this.mNeedToStart = z;
        this.mResId = 0;
        this.mUri = null;
    }

    public SemRemoteViewsDrawableAnimation(int i, boolean z, int i2) {
        super(i);
        this.mNeedToStart = z;
        this.mResId = i2;
        this.mUri = null;
    }

    public SemRemoteViewsDrawableAnimation(int i, boolean z, Uri uri) {
        super(i);
        this.mNeedToStart = z;
        this.mResId = 0;
        this.mUri = uri;
    }

    protected SemRemoteViewsDrawableAnimation(Parcel parcel) {
        super(parcel);
        this.mNeedToStart = parcel.readBoolean();
        this.mResId = parcel.readInt();
        this.mUri = Uri.CREATOR.createFromParcel(parcel);
    }

    @Override // android.widget.SemRemoteViewsAnimation, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mNeedToStart);
        parcel.writeInt(this.mResId);
        Uri.writeToParcel(parcel, this.mUri);
    }

    public static void writeToParcel(SemRemoteViewsDrawableAnimation semRemoteViewsDrawableAnimation, Parcel parcel) {
        if (semRemoteViewsDrawableAnimation != null) {
            semRemoteViewsDrawableAnimation.writeToParcel(parcel, 0);
        } else {
            parcel.writeString(null);
        }
    }

    @Override // android.widget.SemRemoteViewsAnimation
    protected void startAnimation(View view) {
        if (this.mIsExpired) {
            return;
        }
        View findViewById = view.findViewById(this.mViewId);
        if (findViewById instanceof ImageView) {
            ImageView imageView = (ImageView) findViewById;
            int i = this.mResId;
            if (i > 0) {
                imageView.setImageResource(i);
            }
            Uri uri = this.mUri;
            if (uri != null) {
                imageView.setImageURI(uri);
            }
            Object drawable = imageView.getDrawable();
            if ((drawable instanceof AnimatedImageDrawable) || (drawable instanceof AnimatedVectorDrawable)) {
                Animatable2 animatable2 = (Animatable2) drawable;
                this.mAnimatableDrawable = animatable2;
                if (this.mNeedToStart) {
                    animatable2.start();
                    this.mIsExpired = true;
                    return;
                } else {
                    animatable2.stop();
                    return;
                }
            }
            Log.w(LOG_TAG, "ImageView drawable is not animatable");
        }
    }
}
