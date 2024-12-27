package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;

import com.samsung.android.hardware.context.SemContextEventContext;

@Deprecated
/* loaded from: classes2.dex */
public class SContextEventContext extends SemContextEventContext {
    public SContextEventContext() {}

    public SContextEventContext(Parcel src) {}

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public int describeContents() {
        return super.describeContents();
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        super.setValues(context);
    }
}
