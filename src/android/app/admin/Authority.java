package android.app.admin;

import android.annotation.SystemApi;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public abstract class Authority implements Parcelable {
    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return 0;
    }

    protected Authority() {
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass();
    }
}
