package android.widget;

import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

/* loaded from: classes4.dex */
public class SemRemoteViewsDrawableAnimation extends SemRemoteViewsAnimation {
    public static final Parcelable.Creator<SemRemoteViewsDrawableAnimation> CREATOR =
            new Parcelable.Creator<
                    SemRemoteViewsDrawableAnimation>() { // from class:
                                                         // android.widget.SemRemoteViewsDrawableAnimation.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemRemoteViewsDrawableAnimation createFromParcel(Parcel in) {
                    return new SemRemoteViewsDrawableAnimation(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemRemoteViewsDrawableAnimation[] newArray(int size) {
                    return new SemRemoteViewsDrawableAnimation[size];
                }
            };
    private static final String LOG_TAG = "SemRemoteViewsDrawableAnimation";
    private Animatable2 mAnimatableDrawable;
    private final boolean mNeedToStart;
    private final int mResId;
    private final Uri mUri;

    public SemRemoteViewsDrawableAnimation(int viewId, boolean needToStart) {
        super(viewId);
        this.mNeedToStart = needToStart;
        this.mResId = 0;
        this.mUri = null;
    }

    public SemRemoteViewsDrawableAnimation(int viewId, boolean needToStart, int resId) {
        super(viewId);
        this.mNeedToStart = needToStart;
        this.mResId = resId;
        this.mUri = null;
    }

    public SemRemoteViewsDrawableAnimation(int viewId, boolean needToStart, Uri uri) {
        super(viewId);
        this.mNeedToStart = needToStart;
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
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeBoolean(this.mNeedToStart);
        dest.writeInt(this.mResId);
        Uri.writeToParcel(dest, this.mUri);
    }

    public static void writeToParcel(SemRemoteViewsDrawableAnimation c, Parcel out) {
        if (c != null) {
            c.writeToParcel(out, 0);
        } else {
            out.writeString(null);
        }
    }

    @Override // android.widget.SemRemoteViewsAnimation, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.widget.SemRemoteViewsAnimation
    protected void startAnimation(View root) {
        if (this.mIsExpired) {
            return;
        }
        View target = root.findViewById(this.mViewId);
        if (target instanceof ImageView) {
            ImageView imageView = (ImageView) target;
            if (this.mResId > 0) {
                imageView.setImageResource(this.mResId);
            }
            if (this.mUri != null) {
                imageView.setImageURI(this.mUri);
            }
            Object drawable = imageView.getDrawable();
            if ((drawable instanceof AnimatedImageDrawable)
                    || (drawable instanceof AnimatedVectorDrawable)) {
                this.mAnimatableDrawable = (Animatable2) drawable;
                if (this.mNeedToStart) {
                    this.mAnimatableDrawable.start();
                    this.mIsExpired = true;
                    return;
                } else {
                    this.mAnimatableDrawable.stop();
                    return;
                }
            }
            Log.w(LOG_TAG, "ImageView drawable is not animatable");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.SemRemoteViewsAnimation
    /* renamed from: endAnimation */
    public void lambda$play$0(View root) {}
}
