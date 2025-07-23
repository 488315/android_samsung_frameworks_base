package android.app;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class AuthenticationRequiredException extends SecurityException implements Parcelable {
    public static final Parcelable.Creator<AuthenticationRequiredException> CREATOR = new Parcelable.Creator<AuthenticationRequiredException>() { // from class: android.app.AuthenticationRequiredException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationRequiredException createFromParcel(Parcel parcel) {
            return new AuthenticationRequiredException(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationRequiredException[] newArray(int i) {
            return new AuthenticationRequiredException[i];
        }
    };
    private static final String TAG = "AuthenticationRequiredException";
    private final PendingIntent mUserAction;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AuthenticationRequiredException(Parcel parcel) {
        this(new SecurityException(parcel.readString()), PendingIntent.CREATOR.createFromParcel(parcel));
    }

    public AuthenticationRequiredException(Throwable th, PendingIntent pendingIntent) {
        super(th.getMessage());
        this.mUserAction = (PendingIntent) Objects.requireNonNull(pendingIntent);
    }

    public PendingIntent getUserAction() {
        return this.mUserAction;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getMessage());
        this.mUserAction.writeToParcel(parcel, i);
    }
}
