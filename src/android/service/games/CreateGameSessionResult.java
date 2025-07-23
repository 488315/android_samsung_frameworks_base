package android.service.games;

import android.os.Parcel;
import android.os.Parcelable;
import android.service.games.IGameSession;
import android.view.SurfaceControlViewHost;

/* loaded from: classes3.dex */
public final class CreateGameSessionResult implements Parcelable {
    public static final Parcelable.Creator<CreateGameSessionResult> CREATOR = new Parcelable.Creator<CreateGameSessionResult>() { // from class: android.service.games.CreateGameSessionResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateGameSessionResult createFromParcel(Parcel parcel) {
            return new CreateGameSessionResult(IGameSession.Stub.asInterface(parcel.readStrongBinder()), (SurfaceControlViewHost.SurfacePackage) parcel.readParcelable(SurfaceControlViewHost.SurfacePackage.class.getClassLoader(), SurfaceControlViewHost.SurfacePackage.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateGameSessionResult[] newArray(int i) {
            return new CreateGameSessionResult[0];
        }
    };
    private final IGameSession mGameSession;
    private final SurfaceControlViewHost.SurfacePackage mSurfacePackage;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CreateGameSessionResult(IGameSession iGameSession, SurfaceControlViewHost.SurfacePackage surfacePackage) {
        this.mGameSession = iGameSession;
        this.mSurfacePackage = surfacePackage;
    }

    public IGameSession getGameSession() {
        return this.mGameSession;
    }

    public SurfaceControlViewHost.SurfacePackage getSurfacePackage() {
        return this.mSurfacePackage;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mGameSession.asBinder());
        parcel.writeParcelable(this.mSurfacePackage, i);
    }
}
