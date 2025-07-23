package android.net.wifi.sharedconnectivity.app;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class SharedConnectivitySettingsState implements Parcelable {
    public static final Parcelable.Creator<SharedConnectivitySettingsState> CREATOR = new Parcelable.Creator<SharedConnectivitySettingsState>() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SharedConnectivitySettingsState createFromParcel(Parcel parcel) {
            return SharedConnectivitySettingsState.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SharedConnectivitySettingsState[] newArray(int i) {
            return new SharedConnectivitySettingsState[i];
        }
    };
    private final Bundle mExtras;
    private final boolean mInstantTetherEnabled;
    private final PendingIntent mInstantTetherSettingsPendingIntent;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private Bundle mExtras = Bundle.EMPTY;
        private boolean mInstantTetherEnabled;
        private PendingIntent mInstantTetherSettingsPendingIntent;

        public Builder setInstantTetherEnabled(boolean z) {
            this.mInstantTetherEnabled = z;
            return this;
        }

        public Builder setInstantTetherSettingsPendingIntent(PendingIntent pendingIntent) {
            this.mInstantTetherSettingsPendingIntent = pendingIntent;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public SharedConnectivitySettingsState build() {
            return new SharedConnectivitySettingsState(this.mInstantTetherEnabled, this.mInstantTetherSettingsPendingIntent, this.mExtras);
        }
    }

    private static void validate(PendingIntent pendingIntent) {
        if (pendingIntent != null && !pendingIntent.isImmutable()) {
            throw new IllegalArgumentException("Pending intent must be immutable");
        }
    }

    private SharedConnectivitySettingsState(boolean z, PendingIntent pendingIntent, Bundle bundle) {
        validate(pendingIntent);
        this.mInstantTetherEnabled = z;
        this.mInstantTetherSettingsPendingIntent = pendingIntent;
        this.mExtras = bundle;
    }

    public boolean isInstantTetherEnabled() {
        return this.mInstantTetherEnabled;
    }

    public PendingIntent getInstantTetherSettingsPendingIntent() {
        return this.mInstantTetherSettingsPendingIntent;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SharedConnectivitySettingsState)) {
            return false;
        }
        SharedConnectivitySettingsState sharedConnectivitySettingsState = (SharedConnectivitySettingsState) obj;
        return this.mInstantTetherEnabled == sharedConnectivitySettingsState.isInstantTetherEnabled() && Objects.equals(this.mInstantTetherSettingsPendingIntent, sharedConnectivitySettingsState.getInstantTetherSettingsPendingIntent());
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mInstantTetherEnabled), this.mInstantTetherSettingsPendingIntent);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        PendingIntent.writePendingIntentOrNullToParcel(this.mInstantTetherSettingsPendingIntent, parcel);
        parcel.writeBoolean(this.mInstantTetherEnabled);
        parcel.writeBundle(this.mExtras);
    }

    public static SharedConnectivitySettingsState readFromParcel(Parcel parcel) {
        return new SharedConnectivitySettingsState(parcel.readBoolean(), PendingIntent.readPendingIntentOrNullFromParcel(parcel), parcel.readBundle());
    }

    public String toString() {
        return "SharedConnectivitySettingsState[instantTetherEnabled=" + this.mInstantTetherEnabled + "PendingIntent=" + this.mInstantTetherSettingsPendingIntent + "extras=" + this.mExtras.toString() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
