package android.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.View;

/* loaded from: classes5.dex */
public abstract class SemRemoteViewsAnimation implements Parcelable {
    private static final String TAG = "SemRemoteViewsAnimation";
    protected final long MAX_DURATION = 4000;
    protected boolean mIsExpired;
    protected int mViewId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: endAnimation, reason: merged with bridge method [inline-methods] */
    public abstract void lambda$play$0(View view);

    protected abstract void startAnimation(View view);

    public SemRemoteViewsAnimation(int i) {
        this.mViewId = i;
    }

    protected SemRemoteViewsAnimation(Parcel parcel) {
        this.mViewId = parcel.readInt();
        this.mIsExpired = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mViewId);
        parcel.writeBoolean(this.mIsExpired);
    }

    public void play(final View view) {
        if (view == null) {
            return;
        }
        try {
            startAnimation(view);
            if (Looper.myLooper() == null) {
                Log.w(TAG, "Looper is null. Stop the play endAnimation");
            } else {
                new Handler(Looper.myLooper()).postDelayed(new Runnable() { // from class: android.widget.SemRemoteViewsAnimation$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$play$0(view);
                    }
                }, 4000L);
            }
        } catch (AndroidRuntimeException e) {
            Log.w(TAG, "Not ready looper. Stop the play startAnimation " + e);
        }
    }
}
