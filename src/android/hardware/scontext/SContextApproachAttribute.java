package android.hardware.scontext;

import android.os.Bundle;

@Deprecated
/* loaded from: classes2.dex */
public class SContextApproachAttribute extends SContextAttribute {
    private static final String TAG = "SContextApproachAttribute";
    private int mUserID;

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        return true;
    }

    SContextApproachAttribute() {
        this.mUserID = -1;
        setAttribute();
    }

    public SContextApproachAttribute(int i) {
        this.mUserID = i;
        setAttribute();
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("UserID", this.mUserID);
        super.setAttribute(1, bundle);
    }
}
