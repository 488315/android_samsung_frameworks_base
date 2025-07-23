package android.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.MergedConfiguration;
import android.view.InsetsSourceControl;
import android.window.ActivityWindowInfo;
import android.window.ClientWindowFrames;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class WindowRelayoutResult implements Parcelable {
    public static final Parcelable.Creator<WindowRelayoutResult> CREATOR = new Parcelable.Creator<WindowRelayoutResult>() { // from class: android.view.WindowRelayoutResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowRelayoutResult createFromParcel(Parcel parcel) {
            return new WindowRelayoutResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowRelayoutResult[] newArray(int i) {
            return new WindowRelayoutResult[i];
        }
    };
    public final InsetsSourceControl.Array activeControls;
    public ActivityWindowInfo activityWindowInfo;
    public int cutoutPolicy;
    public final ClientWindowFrames frames;
    public final InsetsState insetsState;
    public final MergedConfiguration mergedConfiguration;
    public final SurfaceControl surfaceControl;
    public int syncSeqId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WindowRelayoutResult() {
        this(new ClientWindowFrames(), new MergedConfiguration(), new SurfaceControl(), new InsetsState(), new InsetsSourceControl.Array());
    }

    public WindowRelayoutResult(ClientWindowFrames clientWindowFrames, MergedConfiguration mergedConfiguration, SurfaceControl surfaceControl, InsetsState insetsState, InsetsSourceControl.Array array) {
        this.frames = (ClientWindowFrames) Objects.requireNonNull(clientWindowFrames);
        this.mergedConfiguration = (MergedConfiguration) Objects.requireNonNull(mergedConfiguration);
        this.surfaceControl = (SurfaceControl) Objects.requireNonNull(surfaceControl);
        this.insetsState = (InsetsState) Objects.requireNonNull(insetsState);
        this.activeControls = (InsetsSourceControl.Array) Objects.requireNonNull(array);
    }

    private WindowRelayoutResult(Parcel parcel) {
        this();
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.frames.readFromParcel(parcel);
        this.mergedConfiguration.readFromParcel(parcel);
        this.surfaceControl.readFromParcel(parcel);
        this.insetsState.readFromParcel(parcel);
        this.activeControls.readFromParcel(parcel);
        this.syncSeqId = parcel.readInt();
        this.activityWindowInfo = (ActivityWindowInfo) parcel.readTypedObject(ActivityWindowInfo.CREATOR);
        this.cutoutPolicy = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.frames.writeToParcel(parcel, i);
        this.mergedConfiguration.writeToParcel(parcel, i);
        this.surfaceControl.writeToParcel(parcel, i);
        this.insetsState.writeToParcel(parcel, i);
        this.activeControls.writeToParcel(parcel, i);
        parcel.writeInt(this.syncSeqId);
        parcel.writeTypedObject(this.activityWindowInfo, i);
        parcel.writeInt(this.cutoutPolicy);
    }
}
