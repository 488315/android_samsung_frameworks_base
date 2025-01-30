package android.service.games;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.view.SurfaceControlViewHost;

/* loaded from: classes3.dex */
public final class CreateGameSessionResult implements Parcelable {
  public static final Parcelable.Creator<CreateGameSessionResult> CREATOR =
      new Parcelable.Creator<
          CreateGameSessionResult>() { // from class:
                                       // android.service.games.CreateGameSessionResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateGameSessionResult createFromParcel(Parcel source) {
          return new CreateGameSessionResult(
              IGameSession.Stub.asInterface(source.readStrongBinder()),
              (SurfaceControlViewHost.SurfacePackage)
                  source.readParcelable(
                      SurfaceControlViewHost.SurfacePackage.class.getClassLoader(),
                      SurfaceControlViewHost.SurfacePackage.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateGameSessionResult[] newArray(int size) {
          return new CreateGameSessionResult[0];
        }
      };
  private final IGameSession mGameSession;
  private final SurfaceControlViewHost.SurfacePackage mSurfacePackage;

  public CreateGameSessionResult(
      IGameSession gameSession, SurfaceControlViewHost.SurfacePackage surfacePackage) {
    this.mGameSession = gameSession;
    this.mSurfacePackage = surfacePackage;
  }

  public IGameSession getGameSession() {
    return this.mGameSession;
  }

  public SurfaceControlViewHost.SurfacePackage getSurfacePackage() {
    return this.mSurfacePackage;
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeStrongBinder(this.mGameSession.asBinder());
    dest.writeParcelable(this.mSurfacePackage, flags);
  }
}
