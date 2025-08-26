package android.hardware.camera2.params;

import android.annotation.SystemApi;
import android.graphics.ColorSpace;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.utils.HashCodeHelpers;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class SessionConfiguration implements Parcelable {
    public static final Parcelable.Creator<SessionConfiguration> CREATOR = new Parcelable.Creator<SessionConfiguration>() { // from class: android.hardware.camera2.params.SessionConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SessionConfiguration createFromParcel(Parcel parcel) {
            return new SessionConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SessionConfiguration[] newArray(int i) {
            return new SessionConfiguration[i];
        }
    };
    public static final int SESSION_HIGH_SPEED = 1;
    public static final int SESSION_REGULAR = 0;

    @SystemApi
    public static final int SESSION_SHARED = 2;
    public static final int SESSION_VENDOR_START = 32768;
    private static final String TAG = "SessionConfiguration";
    private int mColorSpace;
    private Executor mExecutor;
    private InputConfiguration mInputConfig;
    private final List<OutputConfiguration> mOutputConfigurations;
    private CaptureRequest mSessionParameters;
    private int mSessionType;
    private CameraCaptureSession.StateCallback mStateCallback;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionMode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SessionConfiguration(int i, List<OutputConfiguration> list, Executor executor, CameraCaptureSession.StateCallback stateCallback) {
        this.mStateCallback = null;
        this.mExecutor = null;
        this.mInputConfig = null;
        this.mSessionParameters = null;
        this.mSessionType = i;
        this.mOutputConfigurations = Collections.unmodifiableList(new ArrayList(list));
        this.mStateCallback = stateCallback;
        this.mExecutor = executor;
    }

    public SessionConfiguration(int i, List<OutputConfiguration> list) {
        this.mStateCallback = null;
        this.mExecutor = null;
        this.mInputConfig = null;
        this.mSessionParameters = null;
        this.mSessionType = i;
        this.mOutputConfigurations = Collections.unmodifiableList(new ArrayList(list));
    }

    private SessionConfiguration(Parcel parcel) {
        this.mStateCallback = null;
        this.mExecutor = null;
        this.mInputConfig = null;
        this.mSessionParameters = null;
        int i = parcel.readInt();
        int i2 = parcel.readInt();
        int i3 = parcel.readInt();
        int i4 = parcel.readInt();
        boolean z = parcel.readBoolean();
        ArrayList arrayList = new ArrayList();
        parcel.readTypedList(arrayList, OutputConfiguration.CREATOR);
        if (parcel.readBoolean()) {
            new CameraMetadataNative().readFromParcel(parcel);
        }
        if (i2 > 0 && i3 > 0 && i4 != -1) {
            this.mInputConfig = new InputConfiguration(i2, i3, i4, z);
        }
        this.mSessionType = i;
        this.mOutputConfigurations = arrayList;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            throw new IllegalArgumentException("dest must not be null");
        }
        parcel.writeInt(this.mSessionType);
        InputConfiguration inputConfiguration = this.mInputConfig;
        if (inputConfiguration != null) {
            parcel.writeInt(inputConfiguration.getWidth());
            parcel.writeInt(this.mInputConfig.getHeight());
            parcel.writeInt(this.mInputConfig.getFormat());
            parcel.writeBoolean(this.mInputConfig.isMultiResolution());
        } else {
            parcel.writeInt(0);
            parcel.writeInt(0);
            parcel.writeInt(-1);
            parcel.writeBoolean(false);
        }
        parcel.writeTypedList(this.mOutputConfigurations);
        if (this.mSessionParameters != null) {
            parcel.writeBoolean(true);
            this.mSessionParameters.getNativeCopy().writeToParcel(parcel, 0);
        } else {
            parcel.writeBoolean(false);
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof SessionConfiguration) {
            SessionConfiguration sessionConfiguration = (SessionConfiguration) obj;
            if (this.mInputConfig == sessionConfiguration.mInputConfig && this.mSessionType == sessionConfiguration.mSessionType && this.mOutputConfigurations.size() == sessionConfiguration.mOutputConfigurations.size()) {
                for (int i = 0; i < this.mOutputConfigurations.size(); i++) {
                    if (!this.mOutputConfigurations.get(i).equals(sessionConfiguration.mOutputConfigurations.get(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(this.mOutputConfigurations.hashCode(), Objects.hashCode(this.mInputConfig), this.mSessionType);
    }

    public int getSessionType() {
        return this.mSessionType;
    }

    public List<OutputConfiguration> getOutputConfigurations() {
        return this.mOutputConfigurations;
    }

    public CameraCaptureSession.StateCallback getStateCallback() {
        return this.mStateCallback;
    }

    public Executor getExecutor() {
        return this.mExecutor;
    }

    public void setInputConfiguration(InputConfiguration inputConfiguration) {
        if (this.mSessionType != 1) {
            this.mInputConfig = inputConfiguration;
            return;
        }
        throw new UnsupportedOperationException("Method not supported for high speed session types");
    }

    public InputConfiguration getInputConfiguration() {
        return this.mInputConfig;
    }

    public void setSessionParameters(CaptureRequest captureRequest) {
        this.mSessionParameters = captureRequest;
    }

    public CaptureRequest getSessionParameters() {
        return this.mSessionParameters;
    }

    public void setColorSpace(ColorSpace.Named named) {
        this.mColorSpace = named.ordinal();
        Iterator<OutputConfiguration> it = this.mOutputConfigurations.iterator();
        while (it.hasNext()) {
            it.next().setColorSpace(named);
        }
    }

    public void clearColorSpace() {
        this.mColorSpace = -1;
        Iterator<OutputConfiguration> it = this.mOutputConfigurations.iterator();
        while (it.hasNext()) {
            it.next().clearColorSpace();
        }
    }

    public ColorSpace getColorSpace() {
        if (this.mColorSpace != -1) {
            return ColorSpace.get(ColorSpace.Named.values()[this.mColorSpace]);
        }
        return null;
    }

    public void setStateCallback(Executor executor, CameraCaptureSession.StateCallback stateCallback) {
        this.mStateCallback = stateCallback;
        this.mExecutor = executor;
    }
}
