package android.nfc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ENABLE_CARD_EMULATION_EUICC, Flags.FLAG_ENABLE_NFC_CHARGING, Flags.FLAG_ENABLE_NFC_MAINLINE, Flags.FLAG_ENABLE_NFC_READER_OPTION, Flags.FLAG_ENABLE_NFC_SET_DISCOVERY_TECH, Flags.FLAG_ENABLE_NFC_USER_RESTRICTION, Flags.FLAG_ENABLE_TAG_DETECTION_BROADCASTS, Flags.FLAG_NFC_ACTION_MANAGE_SERVICES_SETTINGS, Flags.FLAG_NFC_APDU_SERVICE_INFO_CONSTRUCTOR, Flags.FLAG_NFC_ASSOCIATED_ROLE_SERVICES, Flags.FLAG_NFC_CHECK_TAG_INTENT_PREFERENCE, Flags.FLAG_NFC_EVENT_LISTENER, Flags.FLAG_NFC_OBSERVE_MODE, Flags.FLAG_NFC_OBSERVE_MODE_ST_SHIM, Flags.FLAG_NFC_OEM_EXTENSION, Flags.FLAG_NFC_OVERRIDE_RECOVER_ROUTING_TABLE, Flags.FLAG_NFC_PERSIST_LOG, Flags.FLAG_NFC_READ_POLLING_LOOP, Flags.FLAG_NFC_READ_POLLING_LOOP_ST_SHIM, Flags.FLAG_NFC_SET_DEFAULT_DISC_TECH, Flags.FLAG_NFC_SET_SERVICE_ENABLED_FOR_CATEGORY_OTHER, Flags.FLAG_NFC_STATE_CHANGE, Flags.FLAG_NFC_STATE_CHANGE_SECURITY_LOG_EVENT_ENABLED, Flags.FLAG_NFC_VENDOR_CMD, Flags.FLAG_NFC_WATCHDOG, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableCardEmulationEuicc() {
        return getValue(Flags.FLAG_ENABLE_CARD_EMULATION_EUICC, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCardEmulationEuicc();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableNfcCharging() {
        return getValue(Flags.FLAG_ENABLE_NFC_CHARGING, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableNfcCharging();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableNfcMainline() {
        return getValue(Flags.FLAG_ENABLE_NFC_MAINLINE, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableNfcMainline();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableNfcReaderOption() {
        return getValue(Flags.FLAG_ENABLE_NFC_READER_OPTION, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableNfcReaderOption();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableNfcSetDiscoveryTech() {
        return getValue(Flags.FLAG_ENABLE_NFC_SET_DISCOVERY_TECH, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableNfcSetDiscoveryTech();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableNfcUserRestriction() {
        return getValue(Flags.FLAG_ENABLE_NFC_USER_RESTRICTION, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableNfcUserRestriction();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableTagDetectionBroadcasts() {
        return getValue(Flags.FLAG_ENABLE_TAG_DETECTION_BROADCASTS, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTagDetectionBroadcasts();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcActionManageServicesSettings() {
        return getValue(Flags.FLAG_NFC_ACTION_MANAGE_SERVICES_SETTINGS, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcActionManageServicesSettings();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcApduServiceInfoConstructor() {
        return getValue(Flags.FLAG_NFC_APDU_SERVICE_INFO_CONSTRUCTOR, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcApduServiceInfoConstructor();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcAssociatedRoleServices() {
        return getValue(Flags.FLAG_NFC_ASSOCIATED_ROLE_SERVICES, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcAssociatedRoleServices();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcCheckTagIntentPreference() {
        return getValue(Flags.FLAG_NFC_CHECK_TAG_INTENT_PREFERENCE, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcCheckTagIntentPreference();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcEventListener() {
        return getValue(Flags.FLAG_NFC_EVENT_LISTENER, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcEventListener();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcObserveMode() {
        return getValue(Flags.FLAG_NFC_OBSERVE_MODE, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcObserveMode();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcObserveModeStShim() {
        return getValue(Flags.FLAG_NFC_OBSERVE_MODE_ST_SHIM, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcObserveModeStShim();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcOemExtension() {
        return getValue(Flags.FLAG_NFC_OEM_EXTENSION, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcOemExtension();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcOverrideRecoverRoutingTable() {
        return getValue(Flags.FLAG_NFC_OVERRIDE_RECOVER_ROUTING_TABLE, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcOverrideRecoverRoutingTable();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcPersistLog() {
        return getValue(Flags.FLAG_NFC_PERSIST_LOG, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcPersistLog();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcReadPollingLoop() {
        return getValue(Flags.FLAG_NFC_READ_POLLING_LOOP, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcReadPollingLoop();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcReadPollingLoopStShim() {
        return getValue(Flags.FLAG_NFC_READ_POLLING_LOOP_ST_SHIM, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcReadPollingLoopStShim();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcSetDefaultDiscTech() {
        return getValue(Flags.FLAG_NFC_SET_DEFAULT_DISC_TECH, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcSetDefaultDiscTech();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcSetServiceEnabledForCategoryOther() {
        return getValue(Flags.FLAG_NFC_SET_SERVICE_ENABLED_FOR_CATEGORY_OTHER, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcSetServiceEnabledForCategoryOther();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcStateChange() {
        return getValue(Flags.FLAG_NFC_STATE_CHANGE, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcStateChange();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcStateChangeSecurityLogEventEnabled() {
        return getValue(Flags.FLAG_NFC_STATE_CHANGE_SECURITY_LOG_EVENT_ENABLED, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcStateChangeSecurityLogEventEnabled();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcVendorCmd() {
        return getValue(Flags.FLAG_NFC_VENDOR_CMD, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcVendorCmd();
            }
        });
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcWatchdog() {
        return getValue(Flags.FLAG_NFC_WATCHDOG, new Predicate() { // from class: android.nfc.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nfcWatchdog();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ENABLE_CARD_EMULATION_EUICC, Flags.FLAG_ENABLE_NFC_CHARGING, Flags.FLAG_ENABLE_NFC_MAINLINE, Flags.FLAG_ENABLE_NFC_READER_OPTION, Flags.FLAG_ENABLE_NFC_SET_DISCOVERY_TECH, Flags.FLAG_ENABLE_NFC_USER_RESTRICTION, Flags.FLAG_ENABLE_TAG_DETECTION_BROADCASTS, Flags.FLAG_NFC_ACTION_MANAGE_SERVICES_SETTINGS, Flags.FLAG_NFC_APDU_SERVICE_INFO_CONSTRUCTOR, Flags.FLAG_NFC_ASSOCIATED_ROLE_SERVICES, Flags.FLAG_NFC_CHECK_TAG_INTENT_PREFERENCE, Flags.FLAG_NFC_EVENT_LISTENER, Flags.FLAG_NFC_OBSERVE_MODE, Flags.FLAG_NFC_OBSERVE_MODE_ST_SHIM, Flags.FLAG_NFC_OEM_EXTENSION, Flags.FLAG_NFC_OVERRIDE_RECOVER_ROUTING_TABLE, Flags.FLAG_NFC_PERSIST_LOG, Flags.FLAG_NFC_READ_POLLING_LOOP, Flags.FLAG_NFC_READ_POLLING_LOOP_ST_SHIM, Flags.FLAG_NFC_SET_DEFAULT_DISC_TECH, Flags.FLAG_NFC_SET_SERVICE_ENABLED_FOR_CATEGORY_OTHER, Flags.FLAG_NFC_STATE_CHANGE, Flags.FLAG_NFC_STATE_CHANGE_SECURITY_LOG_EVENT_ENABLED, Flags.FLAG_NFC_VENDOR_CMD, Flags.FLAG_NFC_WATCHDOG);
    }
}
