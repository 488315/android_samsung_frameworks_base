package android.app.servertransaction;

import android.os.Parcelable;

/* loaded from: classes.dex */
public abstract class ClientTransactionItem implements BaseClientRequest, Parcelable {
  public int getPostExecutionState() {
    return -1;
  }

  boolean shouldHaveDefinedPreExecutionState() {
    return true;
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }
}
