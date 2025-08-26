package android.service.autofill;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Slog;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
public final class VisibilitySetterAction extends InternalOnClickAction implements OnClickAction, Parcelable {
    public static final Parcelable.Creator<VisibilitySetterAction> CREATOR = new Parcelable.Creator<VisibilitySetterAction>() { // from class: android.service.autofill.VisibilitySetterAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisibilitySetterAction createFromParcel(Parcel parcel) {
            SparseIntArray sparseIntArray = parcel.readSparseIntArray();
            Builder builder = null;
            for (int i = 0; i < sparseIntArray.size(); i++) {
                int iKeyAt = sparseIntArray.keyAt(i);
                int iValueAt = sparseIntArray.valueAt(i);
                if (builder == null) {
                    builder = new Builder(iKeyAt, iValueAt);
                } else {
                    builder.setVisibility(iKeyAt, iValueAt);
                }
            }
            if (builder == null) {
                return null;
            }
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisibilitySetterAction[] newArray(int i) {
            return new VisibilitySetterAction[i];
        }
    };
    private static final String TAG = "VisibilitySetterAction";
    private final SparseIntArray mVisibilities;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VisibilitySetterAction(Builder builder) {
        this.mVisibilities = builder.mVisibilities;
    }

    @Override // android.service.autofill.InternalOnClickAction
    public void onClick(ViewGroup viewGroup) {
        for (int i = 0; i < this.mVisibilities.size(); i++) {
            int iKeyAt = this.mVisibilities.keyAt(i);
            View viewFindViewById = viewGroup.findViewById(iKeyAt);
            if (viewFindViewById == null) {
                Slog.w(TAG, "Skipping view id " + iKeyAt + " because it's not found on " + viewGroup);
            } else {
                int iValueAt = this.mVisibilities.valueAt(i);
                if (Helper.sVerbose) {
                    Slog.v(TAG, "Changing visibility of view " + viewFindViewById + " from " + viewFindViewById.getVisibility() + " to  " + iValueAt);
                }
                viewFindViewById.setVisibility(iValueAt);
            }
        }
    }

    public static final class Builder {
        private boolean mDestroyed;
        private final SparseIntArray mVisibilities = new SparseIntArray();

        public Builder(int i, int i2) {
            setVisibility(i, i2);
        }

        public Builder setVisibility(int i, int i2) {
            throwIfDestroyed();
            if (i2 == 0 || i2 == 4 || i2 == 8) {
                this.mVisibilities.put(i, i2);
                return this;
            }
            throw new IllegalArgumentException("Invalid visibility: " + i2);
        }

        public VisibilitySetterAction build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new VisibilitySetterAction(this);
        }

        private void throwIfDestroyed() {
            Preconditions.checkState(!this.mDestroyed, "Already called build()");
        }
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        return "VisibilitySetterAction: [" + this.mVisibilities + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSparseIntArray(this.mVisibilities);
    }
}
