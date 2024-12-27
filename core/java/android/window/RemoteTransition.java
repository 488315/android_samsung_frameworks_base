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

/* loaded from: classes4.dex */
public final class RemoteTransition implements Parcelable {
    public static final Parcelable.Creator<RemoteTransition> CREATOR = new Parcelable.Creator<RemoteTransition>() { // from class: android.window.RemoteTransition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteTransition[] newArray(int size) {
            return new RemoteTransition[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteTransition createFromParcel(Parcel in) {
            return new RemoteTransition(in);
        }
    };
    public static final int FLAG_CAN_BE_FORCE_MERGED_TO_REMOTE_TRANSIT = 1;
    private IApplicationThread mAppThread;
    private String mDebugName;
    private int mFlags;
    private IRemoteTransition mRemoteTransition;

    public RemoteTransition(IRemoteTransition remoteTransition) {
        this(remoteTransition, null, null);
    }

    public RemoteTransition(IRemoteTransition remoteTransition, String debugName) {
        this(remoteTransition, null, debugName);
    }

    public IBinder asBinder() {
        return this.mRemoteTransition.asBinder();
    }

    public RemoteTransition(IRemoteTransition remoteTransition, IApplicationThread appThread, String debugName) {
        this.mRemoteTransition = remoteTransition;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mRemoteTransition);
        this.mAppThread = appThread;
        this.mDebugName = debugName;
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

    public RemoteTransition setRemoteTransition(IRemoteTransition value) {
        this.mRemoteTransition = value;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mRemoteTransition);
        return this;
    }

    public RemoteTransition setAppThread(IApplicationThread value) {
        this.mAppThread = value;
        return this;
    }

    public RemoteTransition setDebugName(String value) {
        this.mDebugName = value;
        return this;
    }

    public RemoteTransition setFlags(int flags) {
        this.mFlags = flags;
        return this;
    }

    public static String flagsToString(int flags) {
        if (flags == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        StringBuilder sb = new StringBuilder();
        if ((flags & 1) != 0) {
            sb.append("CAN_BE_FORCE_MERGED_TO_REMOTE_TRANSIT");
        }
        return sb.toString();
    }

    public String toString() {
        return "RemoteTransition { remoteTransition = " + this.mRemoteTransition + ", appThread = " + this.mAppThread + ", debugName = " + this.mDebugName + (CoreRune.FW_SHELL_TRANSITION_MERGE ? "flags =" + flagsToString(this.mFlags) : null) + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mAppThread != null ? (byte) (0 | 2) : (byte) 0;
        if (this.mDebugName != null) {
            flg = (byte) (flg | 4);
        }
        dest.writeByte(flg);
        dest.writeStrongInterface(this.mRemoteTransition);
        if (this.mAppThread != null) {
            dest.writeStrongInterface(this.mAppThread);
        }
        if (this.mDebugName != null) {
            dest.writeString(this.mDebugName);
        }
        if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
            dest.writeInt(this.mFlags);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected RemoteTransition(Parcel in) {
        byte flg = in.readByte();
        IRemoteTransition remoteTransition = IRemoteTransition.Stub.asInterface(in.readStrongBinder());
        IApplicationThread appThread = (flg & 2) == 0 ? null : IApplicationThread.Stub.asInterface(in.readStrongBinder());
        String debugName = (flg & 4) == 0 ? null : in.readString();
        this.mRemoteTransition = remoteTransition;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mRemoteTransition);
        this.mAppThread = appThread;
        this.mDebugName = debugName;
        if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
            this.mFlags = in.readInt();
        }
    }
}
