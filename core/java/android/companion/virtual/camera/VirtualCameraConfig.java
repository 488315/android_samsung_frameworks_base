package android.companion.virtual.camera;

import android.annotation.SystemApi;
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
public final class VirtualCameraConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualCameraConfig> CREATOR =
            new Parcelable.Creator<VirtualCameraConfig>() { // from class:
                // android.companion.virtual.camera.VirtualCameraConfig.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualCameraConfig createFromParcel(Parcel in) {
                    return new VirtualCameraConfig(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualCameraConfig[] newArray(int size) {
                    return new VirtualCameraConfig[size];
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
    public @interface SensorOrientation {}

    private VirtualCameraConfig(
            String name,
            Set<VirtualCameraStreamConfig> streamConfigurations,
            Executor executor,
            VirtualCameraCallback callback,
            int sensorOrientation,
            int lensFacing) {
        this.mName = (String) Objects.requireNonNull(name, "Missing name");
        if (lensFacing == -1) {
            throw new IllegalArgumentException("Lens facing must be set");
        }
        this.mLensFacing = lensFacing;
        this.mStreamConfigurations =
                Set.copyOf(
                        (Collection)
                                Objects.requireNonNull(
                                        streamConfigurations, "Missing stream configurations"));
        if (this.mStreamConfigurations.isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one stream configuration is needed to create a virtual camera.");
        }
        this.mCallback =
                new VirtualCameraCallbackInternal(
                        (VirtualCameraCallback)
                                Objects.requireNonNull(callback, "Missing callback"),
                        (Executor) Objects.requireNonNull(executor, "Missing callback executor"));
        this.mSensorOrientation = sensorOrientation;
    }

    private VirtualCameraConfig(Parcel in) {
        this.mName = in.readString8();
        this.mCallback = IVirtualCameraCallback.Stub.asInterface(in.readStrongBinder());
        this.mStreamConfigurations =
                Set.of(
                        in.readParcelableArray(
                                VirtualCameraStreamConfig.class.getClassLoader(),
                                VirtualCameraStreamConfig.class));
        this.mSensorOrientation = in.readInt();
        this.mLensFacing = in.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString8(this.mName);
        dest.writeStrongInterface(this.mCallback);
        dest.writeParcelableArray(
                (VirtualCameraStreamConfig[])
                        this.mStreamConfigurations.toArray(new VirtualCameraStreamConfig[0]),
                flags);
        dest.writeInt(this.mSensorOrientation);
        dest.writeInt(this.mLensFacing);
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

        public Builder(String name) {
            this.mName = (String) Objects.requireNonNull(name, "Name cannot be null");
        }

        public Builder addStreamConfig(
                int width, int height, int format, int maximumFramesPerSecond) {
            if (width <= 0) {
                throw new IllegalArgumentException(
                        "Invalid width passed for stream config: "
                                + width
                                + ", must be greater than 0");
            }
            if (height <= 0) {
                throw new IllegalArgumentException(
                        "Invalid height passed for stream config: "
                                + height
                                + ", must be greater than 0");
            }
            if (!VirtualCameraConfig.isFormatSupported(format)) {
                throw new IllegalArgumentException(
                        "Invalid format passed for stream config: " + format);
            }
            if (maximumFramesPerSecond <= 0 || maximumFramesPerSecond > 60) {
                throw new IllegalArgumentException(
                        "Invalid maximumFramesPerSecond, must be greater than 0 and less than 60");
            }
            this.mStreamConfigurations.add(
                    new VirtualCameraStreamConfig(width, height, format, maximumFramesPerSecond));
            return this;
        }

        public Builder setSensorOrientation(int sensorOrientation) {
            if (sensorOrientation != 0
                    && sensorOrientation != 90
                    && sensorOrientation != 180
                    && sensorOrientation != 270) {
                throw new IllegalArgumentException(
                        "Invalid sensor orientation: " + sensorOrientation);
            }
            this.mSensorOrientation = sensorOrientation;
            return this;
        }

        public Builder setLensFacing(int lensFacing) {
            if (lensFacing != 1 && lensFacing != 0) {
                throw new IllegalArgumentException("Unsupported lens facing: " + lensFacing);
            }
            this.mLensFacing = lensFacing;
            return this;
        }

        public Builder setVirtualCameraCallback(Executor executor, VirtualCameraCallback callback) {
            this.mCallbackExecutor = (Executor) Objects.requireNonNull(executor);
            this.mCallback = (VirtualCameraCallback) Objects.requireNonNull(callback);
            return this;
        }

        public VirtualCameraConfig build() {
            return new VirtualCameraConfig(
                    this.mName,
                    this.mStreamConfigurations,
                    this.mCallbackExecutor,
                    this.mCallback,
                    this.mSensorOrientation,
                    this.mLensFacing);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class VirtualCameraCallbackInternal extends IVirtualCameraCallback.Stub {
        private final VirtualCameraCallback mCallback;
        private final Executor mExecutor;

        private VirtualCameraCallbackInternal(VirtualCameraCallback callback, Executor executor) {
            this.mCallback = callback;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStreamConfigured$0(
                int streamId, Surface surface, int width, int height, int format) {
            this.mCallback.onStreamConfigured(streamId, surface, width, height, format);
        }

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onStreamConfigured(
                final int streamId,
                final Surface surface,
                final int width,
                final int height,
                final int format) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.companion.virtual.camera.VirtualCameraConfig$VirtualCameraCallbackInternal$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            VirtualCameraConfig.VirtualCameraCallbackInternal.this
                                    .lambda$onStreamConfigured$0(
                                            streamId, surface, width, height, format);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProcessCaptureRequest$1(int streamId, long frameId) {
            this.mCallback.onProcessCaptureRequest(streamId, frameId);
        }

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onProcessCaptureRequest(final int streamId, final long frameId) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.companion.virtual.camera.VirtualCameraConfig$VirtualCameraCallbackInternal$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            VirtualCameraConfig.VirtualCameraCallbackInternal.this
                                    .lambda$onProcessCaptureRequest$1(streamId, frameId);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStreamClosed$2(int streamId) {
            this.mCallback.onStreamClosed(streamId);
        }

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onStreamClosed(final int streamId) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.companion.virtual.camera.VirtualCameraConfig$VirtualCameraCallbackInternal$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            VirtualCameraConfig.VirtualCameraCallbackInternal.this
                                    .lambda$onStreamClosed$2(streamId);
                        }
                    });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isFormatSupported(int format) {
        switch (format) {
            case 1:
            case 35:
                return true;
            default:
                return false;
        }
    }
}
