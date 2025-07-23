package android.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioAttributes;
import android.os.IBinder;
import android.os.IExternalVibrationController;
import android.os.Parcelable;
import android.os.VibrationAttributes;
import android.util.Slog;
import com.android.internal.util.Preconditions;
import java.util.HashSet;
import java.util.NoSuchElementException;

/* loaded from: classes3.dex */
public class ExternalVibration implements Parcelable {
    public static final Parcelable.Creator<ExternalVibration> CREATOR = new Parcelable.Creator<ExternalVibration>() { // from class: android.os.ExternalVibration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExternalVibration createFromParcel(Parcel parcel) {
            return new ExternalVibration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExternalVibration[] newArray(int i) {
            return new ExternalVibration[i];
        }
    };
    private static final String TAG = "ExternalVibration";
    private AudioAttributes mAttrs;
    private IExternalVibrationController mController;
    private String mPkg;
    private IBinder mToken;
    private int mUid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ExternalVibration(int i, String str, AudioAttributes audioAttributes, IExternalVibrationController iExternalVibrationController) {
        this(i, str, audioAttributes, iExternalVibrationController, new Binder());
    }

    public ExternalVibration(int i, String str, AudioAttributes audioAttributes, IExternalVibrationController iExternalVibrationController, IBinder iBinder) {
        this.mUid = i;
        this.mPkg = (String) Preconditions.checkNotNull(str);
        this.mAttrs = (AudioAttributes) Preconditions.checkNotNull(audioAttributes);
        this.mController = (IExternalVibrationController) Preconditions.checkNotNull(iExternalVibrationController);
        this.mToken = (IBinder) Preconditions.checkNotNull(iBinder);
        Binder.allowBlocking(this.mController.asBinder());
    }

    private ExternalVibration(Parcel parcel) {
        this(parcel.readInt(), parcel.readString(), readAudioAttributes(parcel), IExternalVibrationController.Stub.asInterface(parcel.readStrongBinder()), parcel.readStrongBinder());
    }

    private static AudioAttributes readAudioAttributes(Parcel parcel) {
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        int readInt4 = parcel.readInt();
        HashSet<String> hashSet = new HashSet<>();
        for (String str : parcel.readString().split(NavigationBarInflaterView.GRAVITY_SEPARATOR)) {
            hashSet.add(str);
        }
        AudioAttributes.Builder builder = new AudioAttributes.Builder();
        if (AudioAttributes.isSystemUsage(readInt)) {
            builder.setSystemUsage(readInt);
        } else {
            builder.setUsage(readInt);
        }
        return builder.setContentType(readInt2).setCapturePreset(readInt3).setFlags(readInt4).addTags(hashSet).build();
    }

    public int getUid() {
        return this.mUid;
    }

    public String getPackage() {
        return this.mPkg;
    }

    public AudioAttributes getAudioAttributes() {
        return this.mAttrs;
    }

    public IBinder getToken() {
        return this.mToken;
    }

    public VibrationAttributes getVibrationAttributes() {
        return new VibrationAttributes.Builder(this.mAttrs).build();
    }

    public boolean mute() {
        try {
            this.mController.mute();
            return true;
        } catch (RemoteException e) {
            Slog.wtf(TAG, "Failed to mute vibration stream: " + this, e);
            return false;
        }
    }

    public boolean unmute() {
        try {
            this.mController.unmute();
            return true;
        } catch (RemoteException e) {
            Slog.wtf(TAG, "Failed to unmute vibration stream: " + this, e);
            return false;
        }
    }

    public void linkToDeath(IBinder.DeathRecipient deathRecipient) {
        try {
            this.mToken.linkToDeath(deathRecipient, 0);
        } catch (RemoteException e) {
            Slog.wtf(TAG, "Failed to link to token death: " + this, e);
        }
    }

    public void unlinkToDeath(IBinder.DeathRecipient deathRecipient) {
        try {
            this.mToken.unlinkToDeath(deathRecipient, 0);
        } catch (NoSuchElementException e) {
            Slog.wtf(TAG, "Failed to unlink to token death", e);
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ExternalVibration)) {
            return false;
        }
        return this.mToken.equals(((ExternalVibration) obj).mToken);
    }

    public String toString() {
        return "ExternalVibration{uid=" + this.mUid + ", pkg=" + this.mPkg + ", attrs=" + this.mAttrs + ", controller=" + this.mController + "token=" + this.mToken + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mUid);
        parcel.writeString(this.mPkg);
        writeAudioAttributes(this.mAttrs, parcel);
        parcel.writeStrongBinder(this.mController.asBinder());
        parcel.writeStrongBinder(this.mToken);
    }

    private static void writeAudioAttributes(AudioAttributes audioAttributes, Parcel parcel) {
        parcel.writeInt(audioAttributes.getSystemUsage());
        parcel.writeInt(audioAttributes.getContentType());
        parcel.writeInt(audioAttributes.getCapturePreset());
        parcel.writeInt(audioAttributes.getAllFlags());
        parcel.writeString("dummy");
    }

    public boolean isRepeating() {
        return getVibrationAttributes().getUsage() == 33;
    }

    public VibrationAttributes getVibrationAttributesWithTags() {
        return new VibrationAttributes.Builder(this.mAttrs).build();
    }
}
