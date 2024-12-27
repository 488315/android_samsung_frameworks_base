package android.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.MergedConfiguration;
import android.window.ActivityWindowInfo;
import android.window.ClientWindowFrames;

import java.util.Objects;

public final class WindowRelayoutResult implements Parcelable {
    public static final Parcelable.Creator<WindowRelayoutResult> CREATOR =
            new Parcelable.Creator<
                    WindowRelayoutResult>() { // from class: android.view.WindowRelayoutResult.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public WindowRelayoutResult createFromParcel(Parcel in) {
                    return new WindowRelayoutResult(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public WindowRelayoutResult[] newArray(int size) {
                    return new WindowRelayoutResult[size];
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

    public WindowRelayoutResult() {
        this(
                new ClientWindowFrames(),
                new MergedConfiguration(),
                new SurfaceControl(),
                new InsetsState(),
                new InsetsSourceControl.Array());
    }

    public WindowRelayoutResult(
            ClientWindowFrames frames,
            MergedConfiguration mergedConfiguration,
            SurfaceControl surfaceControl,
            InsetsState insetsState,
            InsetsSourceControl.Array activeControls) {
        this.frames = (ClientWindowFrames) Objects.requireNonNull(frames);
        this.mergedConfiguration =
                (MergedConfiguration) Objects.requireNonNull(mergedConfiguration);
        this.surfaceControl = (SurfaceControl) Objects.requireNonNull(surfaceControl);
        this.insetsState = (InsetsState) Objects.requireNonNull(insetsState);
        this.activeControls = (InsetsSourceControl.Array) Objects.requireNonNull(activeControls);
    }

    private WindowRelayoutResult(Parcel in) {
        this();
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        this.frames.readFromParcel(in);
        this.mergedConfiguration.readFromParcel(in);
        this.surfaceControl.readFromParcel(in);
        this.insetsState.readFromParcel(in);
        this.activeControls.readFromParcel(in);
        this.syncSeqId = in.readInt();
        this.activityWindowInfo =
                (ActivityWindowInfo) in.readTypedObject(ActivityWindowInfo.CREATOR);
        this.cutoutPolicy = in.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.frames.writeToParcel(dest, flags);
        this.mergedConfiguration.writeToParcel(dest, flags);
        this.surfaceControl.writeToParcel(dest, flags);
        this.insetsState.writeToParcel(dest, flags);
        this.activeControls.writeToParcel(dest, flags);
        dest.writeInt(this.syncSeqId);
        dest.writeTypedObject(this.activityWindowInfo, flags);
        dest.writeInt(this.cutoutPolicy);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
