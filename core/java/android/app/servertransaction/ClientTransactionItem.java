package android.app.servertransaction;

import android.p009os.Parcelable;

/* loaded from: classes.dex */
public abstract class ClientTransactionItem implements BaseClientRequest, Parcelable {
  public int getPostExecutionState() {
    return -1;
  }

  boolean shouldHaveDefinedPreExecutionState() {
    return true;
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }
}
