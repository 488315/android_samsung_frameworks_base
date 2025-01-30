package android.service.autofill;

import android.p009os.Parcelable;

/* loaded from: classes3.dex */
public abstract class InternalValidator implements Validator, Parcelable {
    public abstract boolean isValid(ValueFinder valueFinder);
}
