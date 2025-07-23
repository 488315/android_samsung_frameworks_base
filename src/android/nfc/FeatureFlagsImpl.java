package android.nfc;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.nfc.FeatureFlags
    public boolean enableCardEmulationEuicc() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableNfcCharging() {
        return false;
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableNfcMainline() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableNfcReaderOption() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableNfcSetDiscoveryTech() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableNfcUserRestriction() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableTagDetectionBroadcasts() {
        return false;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcActionManageServicesSettings() {
        return false;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcApduServiceInfoConstructor() {
        return false;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcAssociatedRoleServices() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcCheckTagIntentPreference() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcEventListener() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcObserveMode() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcObserveModeStShim() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcOemExtension() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcOverrideRecoverRoutingTable() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcPersistLog() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcReadPollingLoop() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcReadPollingLoopStShim() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcSetDefaultDiscTech() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcSetServiceEnabledForCategoryOther() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcStateChange() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcStateChangeSecurityLogEventEnabled() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcVendorCmd() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcWatchdog() {
        return true;
    }
}
