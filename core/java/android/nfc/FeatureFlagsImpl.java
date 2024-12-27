package android.nfc;

public final class FeatureFlagsImpl implements FeatureFlags {
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
    public boolean nfcObserveMode() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcObserveModeStShim() {
        return true;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcOemExtension() {
        return false;
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
        return false;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcStateChange() {
        return false;
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcVendorCmd() {
        return true;
    }
}
