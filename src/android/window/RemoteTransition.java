package android.window;

import android.annotation.NonNull;
import android.app.IApplicationThread;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import android.window.IRemoteTransition;
import com.android.internal.util.AnnotationValidations;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes5.dex */
public final class RemoteTransition implements Parcelable {
    public static final Parcelable.Creator<RemoteTransition> CREATOR = new Parcelable.Creator<RemoteTransition>() { // from class: android.window.RemoteTransition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteTransition[] newArray(int i) {
            return new RemoteTransition[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteTransition createFromParcel(Parcel parcel) {
            return new RemoteTransition(parcel);
        }
    };
    public static final int FLAG_CAN_BE_FORCE_MERGED_TO_REMOTE_TRANSIT = 1;
    private IApplicationThread mAppThread;
    private String mDebugName;
    private int mFlags;
    private IRemoteTransition mRemoteTransition;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RemoteTransition(IRemoteTransition iRemoteTransition) {
        this(iRemoteTransition, null, null);
    }

    public RemoteTransition(IRemoteTransition iRemoteTransition, String str) {
        this(iRemoteTransition, null, str);
    }

    public IBinder asBinder() {
        return this.mRemoteTransition.asBinder();
    }

    public RemoteTransition(IRemoteTransition iRemoteTransition, IApplicationThread iApplicationThread, String str) {
        this.mRemoteTransition = iRemoteTransition;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) iRemoteTransition);
        this.mAppThread = iApplicationThread;
        this.mDebugName = str;
    }

    public IRemoteTransition getRemoteTransition() {
        return this.mRemoteTransition;
    }

    public IApplicationThread getAppThread() {
        return this.mAppThread;
    }

    public String getDebugName() {
        return this.mDebugName;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public RemoteTransition setRemoteTransition(IRemoteTransition iRemoteTransition) {
        this.mRemoteTransition = iRemoteTransition;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) iRemoteTransition);
        return this;
    }

    public RemoteTransition setAppThread(IApplicationThread iApplicationThread) {
        this.mAppThread = iApplicationThread;
        return this;
    }

    public RemoteTransition setDebugName(String str) {
        this.mDebugName = str;
        return this;
    }

    public RemoteTransition setFlags(int i) {
        this.mFlags = i;
        return this;
    }

    public static String flagsToString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        StringBuilder sb = new StringBuilder();
        if ((i & 1) != 0) {
            sb.append("CAN_BE_FORCE_MERGED_TO_REMOTE_TRANSIT");
        }
        return sb.toString();
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder("RemoteTransition { remoteTransition = ");
        sb.append(this.mRemoteTransition);
        sb.append(", appThread = ");
        sb.append(this.mAppThread);
        sb.append(", debugName = ");
        sb.append(this.mDebugName);
        if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
            str = "flags =" + flagsToString(this.mFlags);
        } else {
            str = null;
        }
        sb.append(str);
        sb.append(" }");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.mAppThread != null ? (byte) 2 : (byte) 0;
        if (this.mDebugName != null) {
            b = (byte) (b | 4);
        }
        parcel.writeByte(b);
        parcel.writeStrongInterface(this.mRemoteTransition);
        IApplicationThread iApplicationThread = this.mAppThread;
        if (iApplicationThread != null) {
            parcel.writeStrongInterface(iApplicationThread);
        }
        String str = this.mDebugName;
        if (str != null) {
            parcel.writeString(str);
        }
        if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
            parcel.writeInt(this.mFlags);
        }
    }

    protected RemoteTransition(Parcel parcel) {
        byte b = parcel.readByte();
        IRemoteTransition iRemoteTransitionAsInterface = IRemoteTransition.Stub.asInterface(parcel.readStrongBinder());
        IApplicationThread iApplicationThreadAsInterface = (b & 2) == 0 ? null : IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
        String string = (b & 4) == 0 ? null : parcel.readString();
        this.mRemoteTransition = iRemoteTransitionAsInterface;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) iRemoteTransitionAsInterface);
        this.mAppThread = iApplicationThreadAsInterface;
        this.mDebugName = string;
        if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
            this.mFlags = parcel.readInt();
        }
    }
}
