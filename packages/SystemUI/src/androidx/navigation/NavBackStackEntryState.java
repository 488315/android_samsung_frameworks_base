package androidx.navigation;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NavBackStackEntryState implements Parcelable {
    public static final Parcelable.Creator<NavBackStackEntryState> CREATOR;
    public final Bundle args;
    public final int destinationId;
    public final String id;
    public final Bundle savedState;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        CREATOR = new Parcelable.Creator() { // from class: androidx.navigation.NavBackStackEntryState$Companion$CREATOR$1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new NavBackStackEntryState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new NavBackStackEntryState[i];
            }
        };
    }

    public NavBackStackEntryState(NavBackStackEntry navBackStackEntry) {
        this.id = navBackStackEntry.id;
        this.destinationId = navBackStackEntry.destination.id;
        this.args = navBackStackEntry.getArguments();
        Bundle bundle = new Bundle();
        this.savedState = bundle;
        navBackStackEntry.savedStateRegistryController.performSave(bundle);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeInt(this.destinationId);
        parcel.writeBundle(this.args);
        parcel.writeBundle(this.savedState);
    }

    public NavBackStackEntryState(Parcel parcel) {
        String readString = parcel.readString();
        readString.getClass();
        this.id = readString;
        this.destinationId = parcel.readInt();
        this.args = parcel.readBundle(NavBackStackEntryState.class.getClassLoader());
        Bundle readBundle = parcel.readBundle(NavBackStackEntryState.class.getClassLoader());
        readBundle.getClass();
        this.savedState = readBundle;
    }
}
