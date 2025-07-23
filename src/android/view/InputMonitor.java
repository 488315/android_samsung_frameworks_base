package android.view;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.IInputMonitorHost;
import com.android.internal.util.AnnotationValidations;

@Deprecated
/* loaded from: classes4.dex */
public final class InputMonitor implements Parcelable {
    public static final Parcelable.Creator<InputMonitor> CREATOR = new Parcelable.Creator<InputMonitor>() { // from class: android.view.InputMonitor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputMonitor[] newArray(int i) {
            return new InputMonitor[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputMonitor createFromParcel(Parcel parcel) {
            return new InputMonitor(parcel);
        }
    };
    private static final boolean DEBUG = false;
    private static final String TAG = "InputMonitor";
    private final IInputMonitorHost mHost;
    private final InputChannel mInputChannel;
    private final SurfaceControl mSurface;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public void pilferPointers() {
        try {
            this.mHost.pilferPointers();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void dispose() {
        this.mInputChannel.dispose();
        this.mSurface.release();
        try {
            this.mHost.dispose();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public InputMonitor(InputChannel inputChannel, IInputMonitorHost iInputMonitorHost, SurfaceControl surfaceControl) {
        this.mInputChannel = inputChannel;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) inputChannel);
        this.mHost = iInputMonitorHost;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) iInputMonitorHost);
        this.mSurface = surfaceControl;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) surfaceControl);
    }

    public InputChannel getInputChannel() {
        return this.mInputChannel;
    }

    public IInputMonitorHost getHost() {
        return this.mHost;
    }

    public SurfaceControl getSurface() {
        return this.mSurface;
    }

    public String toString() {
        return "InputMonitor { inputChannel = " + this.mInputChannel + ", host = " + this.mHost + ", surface = " + this.mSurface + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mInputChannel, i);
        parcel.writeStrongInterface(this.mHost);
        parcel.writeTypedObject(this.mSurface, i);
    }

    InputMonitor(Parcel parcel) {
        InputChannel inputChannel = (InputChannel) parcel.readTypedObject(InputChannel.CREATOR);
        IInputMonitorHost asInterface = IInputMonitorHost.Stub.asInterface(parcel.readStrongBinder());
        SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
        this.mInputChannel = inputChannel;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) inputChannel);
        this.mHost = asInterface;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) asInterface);
        this.mSurface = surfaceControl;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) surfaceControl);
    }
}
