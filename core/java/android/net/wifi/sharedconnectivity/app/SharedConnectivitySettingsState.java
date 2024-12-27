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
        public SharedConnectivitySettingsState createFromParcel(Parcel in) {
            return SharedConnectivitySettingsState.readFromParcel(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SharedConnectivitySettingsState[] newArray(int size) {
            return new SharedConnectivitySettingsState[size];
        }
    };
    private final Bundle mExtras;
    private final boolean mInstantTetherEnabled;
    private final PendingIntent mInstantTetherSettingsPendingIntent;

    public static final class Builder {
        private Bundle mExtras = Bundle.EMPTY;
        private boolean mInstantTetherEnabled;
        private PendingIntent mInstantTetherSettingsPendingIntent;

        public Builder setInstantTetherEnabled(boolean instantTetherEnabled) {
            this.mInstantTetherEnabled = instantTetherEnabled;
            return this;
        }

        public Builder setInstantTetherSettingsPendingIntent(PendingIntent pendingIntent) {
            this.mInstantTetherSettingsPendingIntent = pendingIntent;
            return this;
        }

        public Builder setExtras(Bundle extras) {
            this.mExtras = extras;
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

    private SharedConnectivitySettingsState(boolean instantTetherEnabled, PendingIntent pendingIntent, Bundle extras) {
        validate(pendingIntent);
        this.mInstantTetherEnabled = instantTetherEnabled;
        this.mInstantTetherSettingsPendingIntent = pendingIntent;
        this.mExtras = extras;
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
        SharedConnectivitySettingsState other = (SharedConnectivitySettingsState) obj;
        return this.mInstantTetherEnabled == other.isInstantTetherEnabled() && Objects.equals(this.mInstantTetherSettingsPendingIntent, other.getInstantTetherSettingsPendingIntent());
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mInstantTetherEnabled), this.mInstantTetherSettingsPendingIntent);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        PendingIntent.writePendingIntentOrNullToParcel(this.mInstantTetherSettingsPendingIntent, dest);
        dest.writeBoolean(this.mInstantTetherEnabled);
        dest.writeBundle(this.mExtras);
    }

    public static SharedConnectivitySettingsState readFromParcel(Parcel in) {
        PendingIntent pendingIntent = PendingIntent.readPendingIntentOrNullFromParcel(in);
        boolean instantTetherEnabled = in.readBoolean();
        Bundle extras = in.readBundle();
        return new SharedConnectivitySettingsState(instantTetherEnabled, pendingIntent, extras);
    }

    public String toString() {
        return "SharedConnectivitySettingsState[instantTetherEnabled=" + this.mInstantTetherEnabled + "PendingIntent=" + this.mInstantTetherSettingsPendingIntent + "extras=" + this.mExtras.toString() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
