package android.window;

import android.os.Parcel;
import android.os.Parcelable;
import android.window.IBackAnimationRunner;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class BackAnimationAdapter implements Parcelable {
    public static final Parcelable.Creator<BackAnimationAdapter> CREATOR = new Parcelable.Creator<BackAnimationAdapter>() { // from class: android.window.BackAnimationAdapter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackAnimationAdapter createFromParcel(Parcel parcel) {
            return new BackAnimationAdapter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackAnimationAdapter[] newArray(int i) {
            return new BackAnimationAdapter[i];
        }
    };
    public int mOriginDisplayId;
    private final IBackAnimationRunner mRunner;
    private int[] mSupportedAnimators;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BackAnimationAdapter(IBackAnimationRunner iBackAnimationRunner) {
        this.mOriginDisplayId = -1;
        this.mRunner = iBackAnimationRunner;
    }

    public BackAnimationAdapter(Parcel parcel) {
        this.mOriginDisplayId = -1;
        this.mRunner = IBackAnimationRunner.Stub.asInterface(parcel.readStrongBinder());
        int[] iArr = new int[parcel.readInt()];
        this.mSupportedAnimators = iArr;
        parcel.readIntArray(iArr);
        this.mOriginDisplayId = parcel.readInt();
    }

    public IBackAnimationRunner getRunner() {
        return this.mRunner;
    }

    public void updateSupportedAnimators(ArrayList<Integer> arrayList) {
        int size = arrayList.size();
        this.mSupportedAnimators = new int[size];
        for (int i = size - 1; i >= 0; i--) {
            this.mSupportedAnimators[i] = arrayList.get(i).intValue();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongInterface(this.mRunner);
        parcel.writeInt(this.mSupportedAnimators.length);
        parcel.writeIntArray(this.mSupportedAnimators);
        parcel.writeInt(this.mOriginDisplayId);
    }

    public boolean isAnimatable(int i) {
        int[] iArr = this.mSupportedAnimators;
        if (iArr == null) {
            return false;
        }
        for (int length = iArr.length - 1; length >= 0; length--) {
            if (i == this.mSupportedAnimators[length]) {
                return true;
            }
        }
        return false;
    }
}
