package android.hardware.scontext;

import android.os.Bundle;

@Deprecated
/* loaded from: classes2.dex */
public class SContextApproachAttribute extends SContextAttribute {
  private static final String TAG = "SContextApproachAttribute";
  private int mUserID;

  SContextApproachAttribute() {
    this.mUserID = -1;
    setAttribute();
  }

  public SContextApproachAttribute(int userID) {
    this.mUserID = -1;
    this.mUserID = userID;
    setAttribute();
  }

  @Override // android.hardware.scontext.SContextAttribute,
            // com.samsung.android.hardware.context.SemContextAttribute
  public boolean checkAttribute() {
    return true;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    attribute.putInt("UserID", this.mUserID);
    super.setAttribute(1, attribute);
  }
}
