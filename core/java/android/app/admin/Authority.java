package android.app.admin;

import android.annotation.SystemApi;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public abstract class Authority implements Parcelable {
    protected Authority() {
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o != null && getClass() == o.getClass();
    }

    public int hashCode() {
        return 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
