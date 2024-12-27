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

public final class VisibilitySetterAction extends InternalOnClickAction
        implements OnClickAction, Parcelable {
    public static final Parcelable.Creator<VisibilitySetterAction> CREATOR =
            new Parcelable.Creator<
                    VisibilitySetterAction>() { // from class:
                                                // android.service.autofill.VisibilitySetterAction.1
                @Override // android.os.Parcelable.Creator
                public VisibilitySetterAction createFromParcel(Parcel parcel) {
                    SparseIntArray visibilities = parcel.readSparseIntArray();
                    Builder builder = null;
                    for (int i = 0; i < visibilities.size(); i++) {
                        int id = visibilities.keyAt(i);
                        int visibility = visibilities.valueAt(i);
                        if (builder == null) {
                            builder = new Builder(id, visibility);
                        } else {
                            builder.setVisibility(id, visibility);
                        }
                    }
                    if (builder == null) {
                        return null;
                    }
                    return builder.build();
                }

                @Override // android.os.Parcelable.Creator
                public VisibilitySetterAction[] newArray(int size) {
                    return new VisibilitySetterAction[size];
                }
            };
    private static final String TAG = "VisibilitySetterAction";
    private final SparseIntArray mVisibilities;

    private VisibilitySetterAction(Builder builder) {
        this.mVisibilities = builder.mVisibilities;
    }

    @Override // android.service.autofill.InternalOnClickAction
    public void onClick(ViewGroup rootView) {
        for (int i = 0; i < this.mVisibilities.size(); i++) {
            int id = this.mVisibilities.keyAt(i);
            View child = rootView.findViewById(id);
            if (child == null) {
                Slog.w(TAG, "Skipping view id " + id + " because it's not found on " + rootView);
            } else {
                int visibility = this.mVisibilities.valueAt(i);
                if (Helper.sVerbose) {
                    Slog.v(
                            TAG,
                            "Changing visibility of view "
                                    + child
                                    + " from "
                                    + child.getVisibility()
                                    + " to  "
                                    + visibility);
                }
                child.setVisibility(visibility);
            }
        }
    }

    public static final class Builder {
        private boolean mDestroyed;
        private final SparseIntArray mVisibilities = new SparseIntArray();

        public Builder(int id, int visibility) {
            setVisibility(id, visibility);
        }

        public Builder setVisibility(int id, int visibility) {
            throwIfDestroyed();
            switch (visibility) {
                case 0:
                case 4:
                case 8:
                    this.mVisibilities.put(id, visibility);
                    return this;
                default:
                    throw new IllegalArgumentException("Invalid visibility: " + visibility);
            }
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
        return !Helper.sDebug
                ? super.toString()
                : "VisibilitySetterAction: ["
                        + this.mVisibilities
                        + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeSparseIntArray(this.mVisibilities);
    }
}
