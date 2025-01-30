package android.credentials.ui;

import android.annotation.NonNull;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes.dex */
public final class CancelUiRequest implements Parcelable {
  public static final Parcelable.Creator<CancelUiRequest> CREATOR =
      new Parcelable.Creator<
          CancelUiRequest>() { // from class: android.credentials.ui.CancelUiRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CancelUiRequest createFromParcel(Parcel in) {
          return new CancelUiRequest(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CancelUiRequest[] newArray(int size) {
          return new CancelUiRequest[size];
        }
      };
  public static final String EXTRA_CANCEL_UI_REQUEST =
      "android.credentials.ui.extra.EXTRA_CANCEL_UI_REQUEST";
  private final String mAppPackageName;
  private final boolean mShouldShowCancellationUi;
  private final IBinder mToken;

  public IBinder getToken() {
    return this.mToken;
  }

  public String getAppPackageName() {
    return this.mAppPackageName;
  }

  public boolean shouldShowCancellationUi() {
    return this.mShouldShowCancellationUi;
  }

  public CancelUiRequest(IBinder token, boolean shouldShowCancellationUi, String appPackageName) {
    this.mToken = token;
    this.mShouldShowCancellationUi = shouldShowCancellationUi;
    this.mAppPackageName = appPackageName;
  }

  private CancelUiRequest(Parcel in) {
    IBinder readStrongBinder = in.readStrongBinder();
    this.mToken = readStrongBinder;
    AnnotationValidations.validate(
        (Class<NonNull>) NonNull.class, (NonNull) null, (Object) readStrongBinder);
    this.mShouldShowCancellationUi = in.readBoolean();
    String readString8 = in.readString8();
    this.mAppPackageName = readString8;
    AnnotationValidations.validate(
        (Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString8);
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeStrongBinder(this.mToken);
    dest.writeBoolean(this.mShouldShowCancellationUi);
    dest.writeString8(this.mAppPackageName);
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }
}
