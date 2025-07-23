package android.nfc;

/* loaded from: classes3.dex */
public interface FeatureFlags {
    boolean enableCardEmulationEuicc();

    boolean enableNfcCharging();

    boolean enableNfcMainline();

    boolean enableNfcReaderOption();

    boolean enableNfcSetDiscoveryTech();

    boolean enableNfcUserRestriction();

    boolean enableTagDetectionBroadcasts();

    boolean nfcActionManageServicesSettings();

    boolean nfcApduServiceInfoConstructor();

    boolean nfcAssociatedRoleServices();

    boolean nfcCheckTagIntentPreference();

    boolean nfcEventListener();

    boolean nfcObserveMode();

    boolean nfcObserveModeStShim();

    boolean nfcOemExtension();

    boolean nfcOverrideRecoverRoutingTable();

    boolean nfcPersistLog();

    boolean nfcReadPollingLoop();

    boolean nfcReadPollingLoopStShim();

    boolean nfcSetDefaultDiscTech();

    boolean nfcSetServiceEnabledForCategoryOther();

    boolean nfcStateChange();

    boolean nfcStateChangeSecurityLogEventEnabled();

    boolean nfcVendorCmd();

    boolean nfcWatchdog();
}
