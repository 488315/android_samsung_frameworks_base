package android.window;

import android.graphics.Rect;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public final class TaskFragmentCreationParams implements Parcelable {
    public static final Parcelable.Creator<TaskFragmentCreationParams> CREATOR = new Parcelable.Creator<TaskFragmentCreationParams>() { // from class: android.window.TaskFragmentCreationParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentCreationParams createFromParcel(Parcel parcel) {
            return new TaskFragmentCreationParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentCreationParams[] newArray(int i) {
            return new TaskFragmentCreationParams[i];
        }
    };
    private final boolean mAllowTransitionWhenEmpty;
    private final int mConfigurationChangeMask;
    private final IBinder mFragmentToken;
    private final Rect mInitialRelativeBounds;
    private final TaskFragmentOrganizerToken mOrganizer;
    private final int mOverrideOrientation;
    private final IBinder mOwnerToken;
    private final IBinder mPairedActivityToken;
    private final IBinder mPairedPrimaryFragmentToken;
    private final int mWindowingMode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TaskFragmentCreationParams(TaskFragmentOrganizerToken taskFragmentOrganizerToken, IBinder iBinder, IBinder iBinder2, Rect rect, int i, IBinder iBinder3, IBinder iBinder4, boolean z, int i2, int i3) {
        Rect rect2 = new Rect();
        this.mInitialRelativeBounds = rect2;
        if (iBinder3 != null && iBinder4 != null) {
            throw new IllegalArgumentException("pairedPrimaryFragmentToken and pairedActivityToken should not be set at the same time.");
        }
        this.mOrganizer = taskFragmentOrganizerToken;
        this.mFragmentToken = iBinder;
        this.mOwnerToken = iBinder2;
        rect2.set(rect);
        this.mWindowingMode = i;
        this.mPairedPrimaryFragmentToken = iBinder3;
        this.mPairedActivityToken = iBinder4;
        this.mAllowTransitionWhenEmpty = z;
        this.mOverrideOrientation = i2;
        this.mConfigurationChangeMask = i3;
    }

    public TaskFragmentOrganizerToken getOrganizer() {
        return this.mOrganizer;
    }

    public IBinder getFragmentToken() {
        return this.mFragmentToken;
    }

    public IBinder getOwnerToken() {
        return this.mOwnerToken;
    }

    public Rect getInitialRelativeBounds() {
        return this.mInitialRelativeBounds;
    }

    public int getWindowingMode() {
        return this.mWindowingMode;
    }

    public IBinder getPairedPrimaryFragmentToken() {
        return this.mPairedPrimaryFragmentToken;
    }

    public IBinder getPairedActivityToken() {
        return this.mPairedActivityToken;
    }

    public boolean getAllowTransitionWhenEmpty() {
        return this.mAllowTransitionWhenEmpty;
    }

    public int getOverrideOrientation() {
        return this.mOverrideOrientation;
    }

    public int getConfigurationChangeMask() {
        return this.mConfigurationChangeMask;
    }

    private TaskFragmentCreationParams(Parcel parcel) {
        Rect rect = new Rect();
        this.mInitialRelativeBounds = rect;
        this.mOrganizer = TaskFragmentOrganizerToken.CREATOR.createFromParcel(parcel);
        this.mFragmentToken = parcel.readStrongBinder();
        this.mOwnerToken = parcel.readStrongBinder();
        rect.readFromParcel(parcel);
        this.mWindowingMode = parcel.readInt();
        this.mPairedPrimaryFragmentToken = parcel.readStrongBinder();
        this.mPairedActivityToken = parcel.readStrongBinder();
        this.mAllowTransitionWhenEmpty = parcel.readBoolean();
        this.mOverrideOrientation = parcel.readInt();
        this.mConfigurationChangeMask = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mOrganizer.writeToParcel(parcel, i);
        parcel.writeStrongBinder(this.mFragmentToken);
        parcel.writeStrongBinder(this.mOwnerToken);
        this.mInitialRelativeBounds.writeToParcel(parcel, i);
        parcel.writeInt(this.mWindowingMode);
        parcel.writeStrongBinder(this.mPairedPrimaryFragmentToken);
        parcel.writeStrongBinder(this.mPairedActivityToken);
        parcel.writeBoolean(this.mAllowTransitionWhenEmpty);
        parcel.writeInt(this.mOverrideOrientation);
        parcel.writeInt(this.mConfigurationChangeMask);
    }

    public String toString() {
        return "TaskFragmentCreationParams{ organizer=" + this.mOrganizer + " fragmentToken=" + this.mFragmentToken + " ownerToken=" + this.mOwnerToken + " initialRelativeBounds=" + this.mInitialRelativeBounds + " windowingMode=" + this.mWindowingMode + " pairedFragmentToken=" + this.mPairedPrimaryFragmentToken + " pairedActivityToken=" + this.mPairedActivityToken + " allowTransitionWhenEmpty=" + this.mAllowTransitionWhenEmpty + " overrideOrientation=" + this.mOverrideOrientation + " configurationChangeMask=" + this.mConfigurationChangeMask + "}";
    }

    public static final class Builder {
        private boolean mAllowTransitionWhenEmpty;
        private final IBinder mFragmentToken;
        private final TaskFragmentOrganizerToken mOrganizer;
        private final IBinder mOwnerToken;
        private IBinder mPairedActivityToken;
        private IBinder mPairedPrimaryFragmentToken;
        private final Rect mInitialRelativeBounds = new Rect();
        private int mWindowingMode = 0;
        private int mOverrideOrientation = -1;
        private int mConfigurationChangeMask = 0;

        public Builder(TaskFragmentOrganizerToken taskFragmentOrganizerToken, IBinder iBinder, IBinder iBinder2) {
            this.mOrganizer = taskFragmentOrganizerToken;
            this.mFragmentToken = iBinder;
            this.mOwnerToken = iBinder2;
        }

        public Builder setInitialRelativeBounds(Rect rect) {
            this.mInitialRelativeBounds.set(rect);
            return this;
        }

        public Builder setWindowingMode(int i) {
            this.mWindowingMode = i;
            return this;
        }

        public Builder setPairedPrimaryFragmentToken(IBinder iBinder) {
            this.mPairedPrimaryFragmentToken = iBinder;
            return this;
        }

        public Builder setPairedActivityToken(IBinder iBinder) {
            this.mPairedActivityToken = iBinder;
            return this;
        }

        public Builder setAllowTransitionWhenEmpty(boolean z) {
            this.mAllowTransitionWhenEmpty = z;
            return this;
        }

        public Builder setOverrideOrientation(int i) {
            this.mOverrideOrientation = i;
            return this;
        }

        public Builder setConfigurationChangeMask(int i) {
            this.mConfigurationChangeMask = i;
            return this;
        }

        public TaskFragmentCreationParams build() {
            return new TaskFragmentCreationParams(this.mOrganizer, this.mFragmentToken, this.mOwnerToken, this.mInitialRelativeBounds, this.mWindowingMode, this.mPairedPrimaryFragmentToken, this.mPairedActivityToken, this.mAllowTransitionWhenEmpty, this.mOverrideOrientation, this.mConfigurationChangeMask);
        }
    }
}
