package android.companion.virtual.camera;

import android.annotation.SystemApi;
import android.companion.virtual.camera.IVirtualCameraCallback;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.view.Surface;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes.dex */
public final class VirtualCameraConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualCameraConfig> CREATOR = new Parcelable.Creator<VirtualCameraConfig>() { // from class: android.companion.virtual.camera.VirtualCameraConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualCameraConfig createFromParcel(Parcel parcel) {
            return new VirtualCameraConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualCameraConfig[] newArray(int i) {
            return new VirtualCameraConfig[i];
        }
    };
    private static final int LENS_FACING_UNKNOWN = -1;
    public static final int SENSOR_ORIENTATION_0 = 0;
    public static final int SENSOR_ORIENTATION_180 = 180;
    public static final int SENSOR_ORIENTATION_270 = 270;
    public static final int SENSOR_ORIENTATION_90 = 90;
    private final IVirtualCameraCallback mCallback;
    private final int mLensFacing;
    private final String mName;
    private final int mSensorOrientation;
    private final Set<VirtualCameraStreamConfig> mStreamConfigurations;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SensorOrientation {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isFormatSupported(int i) {
        return i == 1 || i == 35;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualCameraConfig(String str, Set<VirtualCameraStreamConfig> set, Executor executor, VirtualCameraCallback virtualCameraCallback, int i, int i2) {
        this.mName = (String) Objects.requireNonNull(str, "Missing name");
        if (i2 == -1) {
            throw new IllegalArgumentException("Lens facing must be set");
        }
        this.mLensFacing = i2;
        Set<VirtualCameraStreamConfig> setCopyOf = Set.copyOf((Collection) Objects.requireNonNull(set, "Missing stream configurations"));
        this.mStreamConfigurations = setCopyOf;
        if (setCopyOf.isEmpty()) {
            throw new IllegalArgumentException("At least one stream configuration is needed to create a virtual camera.");
        }
        this.mCallback = new VirtualCameraCallbackInternal((VirtualCameraCallback) Objects.requireNonNull(virtualCameraCallback, "Missing callback"), (Executor) Objects.requireNonNull(executor, "Missing callback executor"));
        this.mSensorOrientation = i;
    }

    private VirtualCameraConfig(Parcel parcel) {
        this.mName = parcel.readString8();
        this.mCallback = IVirtualCameraCallback.Stub.asInterface(parcel.readStrongBinder());
        this.mStreamConfigurations = Set.of(parcel.readParcelableArray(VirtualCameraStreamConfig.class.getClassLoader(), VirtualCameraStreamConfig.class));
        this.mSensorOrientation = parcel.readInt();
        this.mLensFacing = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mName);
        parcel.writeStrongInterface(this.mCallback);
        parcel.writeParcelableArray((VirtualCameraStreamConfig[]) this.mStreamConfigurations.toArray(new VirtualCameraStreamConfig[0]), i);
        parcel.writeInt(this.mSensorOrientation);
        parcel.writeInt(this.mLensFacing);
    }

    public String getName() {
        return this.mName;
    }

    public Set<VirtualCameraStreamConfig> getStreamConfigs() {
        return this.mStreamConfigurations;
    }

    public IVirtualCameraCallback getCallback() {
        return this.mCallback;
    }

    public int getSensorOrientation() {
        return this.mSensorOrientation;
    }

    public int getLensFacing() {
        return this.mLensFacing;
    }

    public static final class Builder {
        private VirtualCameraCallback mCallback;
        private Executor mCallbackExecutor;
        private final String mName;
        private final ArraySet<VirtualCameraStreamConfig> mStreamConfigurations = new ArraySet<>();
        private int mSensorOrientation = 0;
        private int mLensFacing = -1;

        public Builder(String str) {
            this.mName = (String) Objects.requireNonNull(str, "Name cannot be null");
        }

        public Builder addStreamConfig(int i, int i2, int i3, int i4) {
            if (i <= 0) {
                throw new IllegalArgumentException("Invalid width passed for stream config: " + i + ", must be greater than 0");
            }
            if (i2 <= 0) {
                throw new IllegalArgumentException("Invalid height passed for stream config: " + i2 + ", must be greater than 0");
            }
            if (!VirtualCameraConfig.isFormatSupported(i3)) {
                throw new IllegalArgumentException("Invalid format passed for stream config: " + i3);
            }
            if (i4 <= 0 || i4 > 60) {
                throw new IllegalArgumentException("Invalid maximumFramesPerSecond, must be greater than 0 and less than 60");
            }
            this.mStreamConfigurations.add(new VirtualCameraStreamConfig(i, i2, i3, i4));
            return this;
        }

        public Builder setSensorOrientation(int i) {
            if (i != 0 && i != 90 && i != 180 && i != 270) {
                throw new IllegalArgumentException("Invalid sensor orientation: " + i);
            }
            this.mSensorOrientation = i;
            return this;
        }

        public Builder setLensFacing(int i) {
            if (i != 1 && i != 0) {
                throw new IllegalArgumentException("Unsupported lens facing: " + i);
            }
            this.mLensFacing = i;
            return this;
        }

        public Builder setVirtualCameraCallback(Executor executor, VirtualCameraCallback virtualCameraCallback) {
            this.mCallbackExecutor = (Executor) Objects.requireNonNull(executor);
            this.mCallback = (VirtualCameraCallback) Objects.requireNonNull(virtualCameraCallback);
            return this;
        }

        public VirtualCameraConfig build() {
            return new VirtualCameraConfig(this.mName, this.mStreamConfigurations, this.mCallbackExecutor, this.mCallback, this.mSensorOrientation, this.mLensFacing);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class VirtualCameraCallbackInternal extends IVirtualCameraCallback.Stub {
        private final VirtualCameraCallback mCallback;
        private final Executor mExecutor;

        private VirtualCameraCallbackInternal(VirtualCameraCallback virtualCameraCallback, Executor executor) {
            this.mCallback = virtualCameraCallback;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStreamConfigured$0(int i, Surface surface, int i2, int i3, int i4) {
            this.mCallback.onStreamConfigured(i, surface, i2, i3, i4);
        }

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onStreamConfigured(final int i, final Surface surface, final int i2, final int i3, final int i4) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.camera.VirtualCameraConfig$VirtualCameraCallbackInternal$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onStreamConfigured$0(i, surface, i2, i3, i4);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProcessCaptureRequest$1(int i, long j) {
            this.mCallback.onProcessCaptureRequest(i, j);
        }

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onProcessCaptureRequest(final int i, final long j) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.camera.VirtualCameraConfig$VirtualCameraCallbackInternal$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onProcessCaptureRequest$1(i, j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStreamClosed$2(int i) {
            this.mCallback.onStreamClosed(i);
        }

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onStreamClosed(final int i) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.camera.VirtualCameraConfig$VirtualCameraCallbackInternal$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onStreamClosed$2(i);
                }
            });
        }
    }
}
