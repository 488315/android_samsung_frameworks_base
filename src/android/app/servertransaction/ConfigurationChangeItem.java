package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public class ConfigurationChangeItem extends ClientTransactionItem {
    public static final Parcelable.Creator<ConfigurationChangeItem> CREATOR = new Parcelable.Creator<ConfigurationChangeItem>() { // from class: android.app.servertransaction.ConfigurationChangeItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConfigurationChangeItem createFromParcel(Parcel parcel) {
            return new ConfigurationChangeItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConfigurationChangeItem[] newArray(int i) {
            return new ConfigurationChangeItem[i];
        }
    };
    private final Configuration mConfiguration;
    private final int mDeviceId;

    public ConfigurationChangeItem(Configuration configuration, int i) {
        this.mConfiguration = new Configuration(configuration);
        this.mDeviceId = i;
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(ClientTransactionHandler clientTransactionHandler) {
        CompatibilityInfo.applyOverrideIfNeeded(this.mConfiguration);
        clientTransactionHandler.updatePendingConfiguration(this.mConfiguration);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.handleConfigurationChanged(this.mConfiguration, this.mDeviceId);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mConfiguration, i);
        parcel.writeInt(this.mDeviceId);
    }

    private ConfigurationChangeItem(Parcel parcel) {
        this.mConfiguration = (Configuration) Objects.requireNonNull((Configuration) parcel.readTypedObject(Configuration.CREATOR));
        this.mDeviceId = parcel.readInt();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ConfigurationChangeItem configurationChangeItem = (ConfigurationChangeItem) obj;
            if (Objects.equals(this.mConfiguration, configurationChangeItem.mConfiguration) && this.mDeviceId == configurationChangeItem.mDeviceId) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((527 + this.mDeviceId) * 31) + this.mConfiguration.hashCode();
    }

    public String toString() {
        return "ConfigurationChangeItem{deviceId=" + this.mDeviceId + ", config" + this.mConfiguration + "}";
    }
}
