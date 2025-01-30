package android.p009os;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public final class NewUserResponse {
  private final int mOperationResult;
  private final UserHandle mUser;

  public NewUserResponse(UserHandle user, int operationResult) {
    this.mUser = user;
    this.mOperationResult = operationResult;
  }

  public boolean isSuccessful() {
    return this.mUser != null;
  }

  public UserHandle getUser() {
    return this.mUser;
  }

  public int getOperationResult() {
    return this.mOperationResult;
  }

  public String toString() {
    return "NewUserResponse{mUser="
        + this.mUser
        + ", mOperationResult="
        + this.mOperationResult
        + '}';
  }
}
