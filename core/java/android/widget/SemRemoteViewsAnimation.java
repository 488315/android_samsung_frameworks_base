package android.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public abstract class SemRemoteViewsAnimation implements Parcelable {
    protected final long MAX_DURATION = 4000;
    protected boolean mIsExpired;
    protected int mViewId;

    /* renamed from: endAnimation, reason: merged with bridge method [inline-methods] */
    public abstract void lambda$play$0(View view);

    protected abstract void startAnimation(View view);

    public SemRemoteViewsAnimation(int viewId) {
        this.mViewId = viewId;
    }

    protected SemRemoteViewsAnimation(Parcel parcel) {
        this.mViewId = parcel.readInt();
        this.mIsExpired = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mViewId);
        dest.writeBoolean(this.mIsExpired);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void play(final View root) {
        if (root == null) {
            return;
        }
        startAnimation(root);
        new Handler(Looper.myLooper())
                .postDelayed(
                        new Runnable() { // from class:
                                         // android.widget.SemRemoteViewsAnimation$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SemRemoteViewsAnimation.this.lambda$play$0(root);
                            }
                        },
                        4000L);
    }
}
