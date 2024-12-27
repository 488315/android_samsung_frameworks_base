package android.sec.enterprise.content;

import com.samsung.android.provider.SemKnoxPolicyContract;

import java.util.Arrays;

/* loaded from: classes3.dex */
public class SecContentProviderURI {
    public static final String ACTION_GEARPOLICY_ENABLED_INTERNAL =
            "com.samsung.android.knox.intent.action.GEARPOLICY_ENABLE_INTERNAL";
    public static final String APPLICATIONPOLICY = "ApplicationPolicy";
    public static final String APPLICATIONPOLICY_APPLICATIONCLEARCACHE_METHOD =
            "isApplicationClearCacheDisabled";
    public static final String APPLICATIONPOLICY_APPLICATIONINSTALLATIONMODE_METHOD =
            "getAppInstallationMode";
    public static final String APPLICATIONPOLICY_APPLICATIONNAMEFROMDB_METHOD =
            "getApplicationNameFromDb";
    public static final String APPLICATIONPOLICY_APPLICATIONSTATEDISABLEDLIST_METHOD =
            "getApplicationStateDisabledList";
    public static final String APPLICATIONPOLICY_APPLICATIONSTATE_METHOD =
            "getApplicationStateEnabled";
    public static final String APPLICATIONPOLICY_APPLICATIONUNINSTALLATIONMODE_METHOD =
            "getApplicationUninstallationMode";
    public static final String APPLICATIONPOLICY_APPLICATIONUNINSTALLATION_METHOD =
            "getApplicationUninstallationEnabled";
    public static final String APPLICATIONPOLICY_APPLICATION_INSTALL_UNINSTALL_LIST_METHOD =
            "getApplicationInstallUninstallList";
    public static final String APPLICATIONPOLICY_DEFAULTASSISTAPP_METHOD =
            "isChangeAssistDefaultAppAllowed";
    public static final String APPLICATIONPOLICY_DEFAULTSMSAPP_METHOD =
            "isChangeSmsDefaultAppAllowed";
    public static final String APPLICATIONPOLICY_DISABLED_CLIPBOARD_BLACKLIST_METHOD =
            "getPackagesFromDisableClipboardBlackList";
    public static final String APPLICATIONPOLICY_DISABLED_CLIPBOARD_BLACKLIST_PERUID_METHOD =
            "getPackagesFromDisableClipboardBlackListPerUidInternal";
    public static final String APPLICATIONPOLICY_DISABLED_CLIPBOARD_WHITELIST_METHOD =
            "getPackagesFromDisableClipboardWhiteList";
    public static final String APPLICATIONPOLICY_DISABLED_CLIPBOARD_WHITELIST_PERUID_METHOD =
            "getPackagesFromDisableClipboardWhiteListPerUidInternal";
    public static final String APPLICATIONPOLICY_GETBATTERYOPTIMIZATIONWHITELIST_METHOD =
            "getAllPackagesFromBatteryOptimizationWhiteList";
    public static final String APPLICATIONPOLICY_GETDEFAULTAPPLICATION_METHOD =
            "getDefaultApplicationInternal";
    public static final String APPLICATIONPOLICY_ISAPPLICATIONSETTODEFAULT_METHOD =
            "isApplicationSetToDefault";
    public static final String APPLICATIONPOLICY_PACKAGEUPDATEALLOWED_METHOD =
            "isPackageUpdateAllowed";
    public static final String APPLICATION_URI =
            "content://com.sec.knox.provider2/ApplicationPolicy";
    public static final String AUDITLOGPOLICY_AUDITLOGENABLED_METHOD = "isAuditLogEnabled";
    public static final String AUDITPOLICY = "AuditLog";
    public static final String AUDIT_URI = "content://com.sec.knox.provider/AuditLog";
    public static final String AUTHORITY = "com.sec.knox.provider";
    public static final String AUTHORITY2 = "com.sec.knox.provider2";
    public static final String BLUETOOTHPOLICY = "BluetoothPolicy";
    public static final String BLUETOOTHPOLICY_BLESCANNINGALLOWED_METHOD = "isBLEAllowed";
    public static final String BLUETOOTHPOLICY_BLUETOOTHENABLED_METHOD = "isBluetoothEnabled";
    public static final String BLUETOOTHPOLICY_DESKTOPCONNECTIVITY_METHOD =
            "isDesktopConnectivityEnabled";
    public static final String BLUETOOTHPOLICY_DISCOVERABLE_METHOD = "isDiscoverableEnabled";
    public static final String BLUETOOTHPOLICY_OUTGOINGCALLSALLOWED_METHOD =
            "isOutgoingCallsAllowed";
    public static final String BLUETOOTHUTILSPOLICY = "BluetoothUtils";
    public static final String BLUETOOTHUTILSPOLICY_URI =
            "content://com.sec.knox.provider/BluetoothUtils";
    public static final String BLUETOOTHUTILS_BLUETOOTHLOGENABLED_METHOD = "isBluetoothLogEnabled";
    public static final String BLUETOOTHUTILS_BLUETOOTHLOGFORDEVICE_METHOD =
            "bluetoothLogForDevice";
    public static final String BLUETOOTHUTILS_BLUETOOTHLOGFORREMOTE_METHOD =
            "bluetoothLogForRemote";
    public static final String BLUETOOTHUTILS_BLUETOOTHLOG_METHOD = "bluetoothLog";
    public static final String BLUETOOTHUTILS_HEADSETALLOWEDBYSECURITY_METHOD =
            "isHeadsetAllowedBySecurityPolicy";
    public static final String BLUETOOTHUTILS_PAIRINGALLOWEDBYSECURITY_METHOD =
            "isPairingAllowedbySecurityPolicy";
    public static final String BLUETOOTHUTILS_PROFILEAUTHORIZEDBYSECURITY_METHOD =
            "isProfileAuthorizedBySecurityPolicy";
    public static final String BLUETOOTH_URI = "content://com.sec.knox.provider/BluetoothPolicy";
    public static final String BROWSERPOLICY = "BrowserPolicy";
    public static final String BROWSERPOLICY_AUTOFILL_METHOD = "getAutoFillSetting";
    public static final String BROWSERPOLICY_COOKIES_METHOD = "getCookiesSetting";
    public static final String BROWSERPOLICY_HTTPPROXY_METHOD = "getHttpProxy";
    public static final String BROWSERPOLICY_JAVASCRIPT_METHOD = "getJavaScriptSetting";
    public static final String BROWSERPOLICY_POPUPS_METHOD = "getPopupsSetting";
    public static final String BROWSER_URI = "content://com.sec.knox.provider/BrowserPolicy";
    public static final String CERTIFICATEPOLICY = "CertificatePolicy";
    public static final String CERTIFICATEPOLICY_CACERTIFICATEDISABLEDASUSER_METHOD =
            "isCaCertificateDisabledAsUser";
    public static final String CERTIFICATEPOLICY_CERTIFICATE_REMOVED_METHOD = "certificateRemoved";
    public static final String CERTIFICATEPOLICY_NOTIFY_METHOD = "notifyCertificateFailure";
    public static final String CERTIFICATEPOLICY_OCSPCHECK_METHOD = "isOcspCheckEnabled";
    public static final String CERTIFICATEPOLICY_REVOCATIONCHECK_METHOD =
            "isRevocationCheckEnabled";
    public static final String CERTIFICATEPOLICY_USERREMOVECERTIFICATES_METHOD =
            "isUserRemoveCertificatesAllowed";
    public static final String CERTIFICATE_URI =
            "content://com.sec.knox.provider/CertificatePolicy";
    public static final String CONTENT = "content://";
    public static final String DATETIMEPOLICY = "DateTimePolicy";
    public static final String DATETIMEPOLICY_DATETIMECHANGE_METHOD = "isDateTimeChangeEnalbed";
    public static final String DATETIMEPOLICY_GETAUTOMATIONTIME_METHOD = "getAutomaticTime";
    public static final String DATETIME_URI = "content://com.sec.knox.provider/DateTimePolicy";
    public static final String DEVICEACCOUNTPOLICY = "DeviceAccountPolicy";
    public static final String DEVICEACCOUNTPOLICY_ACCOUNTADDITION_METHOD =
            "isAccountAdditionAllowed";
    public static final String DEVICEACCOUNTPOLICY_ACCOUNTREMOVAL_METHOD =
            "isAccountRemovalAllowed";
    public static final String DEVICEACCOUNT_URI =
            "content://com.sec.knox.provider2/DeviceAccountPolicy";
    public static final String DEXPOLICY = "DexPolicy";
    public static final String DEXPOLICY_DEX_DISABLED_METHOD = "isDexDisabled";
    public static final String DEXPOLICY_SCREENTIMEOUT_CHANGE_ALLOWED_METHOD =
            "isScreenTimeoutChangeAllowed";
    public static final String DEX_URI = "content://com.sec.knox.provider/DexPolicy";
    public static final String DLPPOLICY = "DlpPolicy";
    public static final String DLPPOLICY_IS_ALLOWEDTO_SHARE = "isAllowedToShare";
    public static final String DLPPOLICY_IS_DLP_ACTIVATED = "isDLPActivated";
    public static final String DLP_URI = "content://com.sec.knox.provider/DlpPolicy";
    public static final String DOMAINFILTERPOLICY = "DomainFilterPolicy";
    public static final String DOMAINFILTER_GETDEFAULTCAPTIVEPORTALURL_METHOD =
            "getDefaultCaptivePortalUrl";
    public static final String DOMAINFILTER_URI =
            "content://com.sec.knox.provider/DomainFilterPolicy";
    public static final String DUALSIMSPOLICY_GETPREFERREDSIMSLOT_METHOD = "getpreferredsimslot";
    public static final String EMAILACCOUNTPOLICY = "EmailAccountPolicy";
    public static final String EMAILACCOUNTPOLICY_SECURITYINCOMING_METHOD =
            "getSecurityIncomingServerPassword";
    public static final String EMAILACCOUNTPOLICY_SECURITYOUTGOING_METHOD =
            "getSecurityOutgoingServerPassword";
    public static final String EMAILACCOUNT_URI =
            "content://com.sec.knox.provider2/EmailAccountPolicy";
    public static final String EMAILPOLICY = "EmailPolicy";
    public static final String EMAILPOLICY_ACCOUNTADDITION_METHOD = "isAccountAdditionAllowed";
    public static final String EMAILPOLICY_ALLOWEMAILFORWARDING_METHOD = "getAllowEmailForwarding";
    public static final String EMAILPOLICY_ALLOWHTMLEMAIL_METHOD = "getAllowHtmlEmail";
    public static final String EMAILPOLICY_EMAILSETTINGSCHANGE_METHOD =
            "isEmailSettingsChangesAllowed";
    public static final String EMAILPOLICY_GET_EMAIL_ACCOUNT_OBJECT_METHOD =
            "getEnterpriseEmailAccountObject";
    public static final String EMAILPOLICY_GET_EXCHANGE_ACCOUNT_OBJECT_METHOD =
            "getEnterpriseExchangeAccountObject";
    public static final String EMAIL_URI = "content://com.sec.knox.provider2/EmailPolicy";
    public static final String ENTERPRISEDEVICEMANAGERPOLICY = "EnterpriseDeviceManager";
    public static final String ENTERPRISEDEVICEMANAGERPOLICY_ACTIVEADMINS_METHOD =
            "getActiveAdmins";
    public static final String ENTERPRISEDEVICEMANAGERPOLICY_ISMDMADMINPRESENT_METHOD =
            "isMdmAdminPresent";
    public static final String ENTERPRISEDEVICEMANAGERPOLICY_MDMVERSIONCHECK_METHOD =
            "getEnterpriseSdkVer";
    public static final String ENTERPRISEDEVICEMANAGER_URI =
            "content://com.sec.knox.provider2/EnterpriseDeviceManager";
    public static final String ENTERPRISELICENSEPOLICY_ISSERVICEAVAILABLE_METHOD =
            "isServiceAvailable";
    public static final String ENTERPRISELICENSESERVICEPOLICY = "EnterpriseLicenseService";
    public static final String ENTERPRISELICENSESERVICE_URI =
            "content://com.sec.knox.provider2/EnterpriseLicenseService";
    public static final String EXCHANGEACCOUNTPOLICY = "ExchangeAccountPolicy";
    public static final String EXCHANGEACCOUNTPOLICY_ACCOUNTCERTIFICATEPASSWORD_METHOD =
            "getAccountCertificatePassword";
    public static final String EXCHANGEACCOUNTPOLICY_ACCOUNTEMAILPASSWORD_METHOD =
            "getAccountEmailPassword";
    public static final String EXCHANGEACCOUNTPOLICY_FORCESMIMECERTIFICATEFORENCRYPTION_METHOD =
            "getForceSMIMECertificateForEncryption";
    public static final String EXCHANGEACCOUNTPOLICY_FORCESMIMECERTIFICATEFORSIGNING_METHOD =
            "getForceSMIMECertificateForSigning";
    public static final String EXCHANGEACCOUNTPOLICY_FORCESMIMECERTIFICATE_METHOD =
            "getForceSMIMECertificate";
    public static final String EXCHANGEACCOUNTPOLICY_INCOMINGATTACHMENTALLOWED_METHOD =
            "isIncomingAttachmentsAllowed";
    public static final String EXCHANGEACCOUNTPOLICY_INCOMINGATTACHMENTSIZE_METHOD =
            "getIncomingAttachmentSize";
    public static final String EXCHANGEACCOUNTPOLICY_MAXCALENDARAGEFILTER_METHOD =
            "getMaxCalendarAgeFilter";
    public static final String EXCHANGEACCOUNTPOLICY_MAXEMAILAGEFILTER_METHOD =
            "getMaxEmailAgeFilter";
    public static final String EXCHANGEACCOUNTPOLICY_MAXEMAILBODYTRUNCATIONSIZE_METHOD =
            "getMaxEmailBodyTruncationSize";
    public static final String EXCHANGEACCOUNTPOLICY_MAXEMAILHTMLBODYTRUNCATIONSIZE_METHOD =
            "getMaxEmailHTMLBodyTruncationSize";
    public static final String EXCHANGEACCOUNTPOLICY_REQUIREDENCRYPTED_METHOD =
            "getRequiredEncryptedMIMEMessages";
    public static final String EXCHANGEACCOUNTPOLICY_REQUIREDSIGNED_METHOD =
            "getRequiredSignedMIMEMessages";
    public static final String EXCHANGEACCOUNTPOLICY_SETACCOUNTEMAILPASSWORD_METHOD =
            "setAccountEmailPassword";
    public static final String EXCHANGEACCOUNT_URI =
            "content://com.sec.knox.provider2/ExchangeAccountPolicy";
    public static final String FIREWALLPOLICY = "FirewallPolicy";
    public static final String FIREWALLPOLICY_GLOBALPROXYALLOWED_METHOD = "isGlobalProxyAllowed";
    public static final String FIREWALLPOLICY_URLFILTERENABLED_METHOD = "getURLFilterEnabled";
    public static final String FIREWALLPOLICY_URLFILTERLIST_METHOD = "getURLFilterList";
    public static final String FIREWALLPOLICY_URLFILTERREPORTENABLED_METHOD =
            "getURLFilterReportEnabled";
    public static final String FIREWALL_URI = "content://com.sec.knox.provider/FirewallPolicy";
    public static final String KEY_GET_CLIPBOARD_BLACKLIST_PERUID = "clipboard_blacklist_perUid";
    public static final String KEY_GET_CLIPBOARD_WHITELIST_PERUID = "clipboard_whitelist_perUid";
    public static final String KIOSKMODEPOLICY = "KioskMode";
    public static final String KIOSKMODE_AIRCOMMANDMODE_METHOD = "isAirCommandModeAllowed";
    public static final String KIOSKMODE_AIRVIEWMODE_METHOD = "isAirViewModeAllowed";
    public static final String KIOSKMODE_APPSEDGEALLOWED_METHOD = "isAppsEdgeAllowed";
    public static final String KIOSKMODE_EDGEALLOWED_METHOD = "isEdgeAllowed";
    public static final String KIOSKMODE_EDGELIGHTINGALLOWED_METHOD = "isEdgeLightingAllowed";
    public static final String KIOSKMODE_INFORMATIONSTREAMALLOWED_METHOD =
            "isInformationStreamAllowed";
    public static final String KIOSKMODE_KIOSKHOMEPACKAGE_METHOD = "getKioskHomePackage";
    public static final String KIOSKMODE_KIOSKMODEENABLED_METHOD = "isKioskModeEnabled";
    public static final String KIOSKMODE_MULTIWINDOWMODEASUSER_METHOD =
            "isMultiWindowModeAllowedAsUser";
    public static final String KIOSKMODE_MULTIWINDOWMODE_METHOD = "isMultiWindowModeAllowed";
    public static final String KIOSKMODE_NAVIGATIONBARHIDDEN_METHOD = "isNavigationBarHidden";
    public static final String KIOSKMODE_NIGHTCLOCKALLOWED_METHOD = "isNightClockAllowed";
    public static final String KIOSKMODE_PEOPLEEDGEALLOWED_METHOD = "isPeopleEdgeAllowed";
    public static final String KIOSKMODE_URI = "content://com.sec.knox.provider2/KioskMode";
    public static final String KNOXCUSTOMMANAGERSERVICE1_URI =
            "content://com.sec.knox.provider2/KnoxCustomManagerService1";
    public static final String KNOXCUSTOMMANAGERSERVICE2_URI =
            "content://com.sec.knox.provider2/KnoxCustomManagerService2";
    public static final String KNOXCUSTOMMANAGERSERVICE_AIRGESTUREOPTIONSTATE_METHOD =
            "getAirGestureOptionState";
    public static final String KNOXCUSTOMMANAGERSERVICE_APPBLOCKDOWNLOADNAMESPACES_METHOD =
            "getAppBlockDownloadNamespaces";
    public static final String KNOXCUSTOMMANAGERSERVICE_APPBLOCKDOWNLOADSTATE_METHOD =
            "getAppBlockDownloadState";
    public static final String KNOXCUSTOMMANAGERSERVICE_AUTOCALLNUMBERANSWERMODE_METHOD =
            "getAutoCallNumberAnswerMode";
    public static final String KNOXCUSTOMMANAGERSERVICE_AUTOCALLNUMBERDELAY_METHOD =
            "getAutoCallNumberDelay";
    public static final String KNOXCUSTOMMANAGERSERVICE_AUTOCALLNUMBERLIST_METHOD =
            "getAutoCallNumberList";
    public static final String KNOXCUSTOMMANAGERSERVICE_AUTOCALLPICKUPSTATE_METHOD =
            "getAutoCallPickupState";
    public static final String KNOXCUSTOMMANAGERSERVICE_CALLSCREENDISABLEDITEMS_METHOD =
            "getCallScreenDisabledItems";
    public static final String KNOXCUSTOMMANAGERSERVICE_CHARGINGLEDSTATE_METHOD =
            "getChargingLEDState";
    public static final String KNOXCUSTOMMANAGERSERVICE_DEXAUTOOPENLASTAPP_METHOD =
            "isDexAutoOpenLastApp";
    public static final String KNOXCUSTOMMANAGERSERVICE_DEXHDMIAUTOENTER_METHOD =
            "getDexHDMIAutoEnter";
    public static final String KNOXCUSTOMMANAGERSERVICE_ETHERNETCONFIGURATIONTYPE_METHOD =
            "getEthernetConfigurationType";
    public static final String KNOXCUSTOMMANAGERSERVICE_ETHERNETSTATE_METHOD = "getEthernetState";
    public static final String KNOXCUSTOMMANAGERSERVICE_GEARNOTIFICATIONSTATE_METHOD =
            "getGearNotificationState";
    public static final String KNOXCUSTOMMANAGERSERVICE_HARDKEYINTENTSTATE_METHOD =
            "getSealedHardKeyIntentState";
    public static final String KNOXCUSTOMMANAGERSERVICE_INFRAREDSTATE_METHOD = "getInfraredState";
    public static final String KNOXCUSTOMMANAGERSERVICE_LOADINGLOGOPATH_METHOD =
            "getLoadingLogoPath";
    public static final String KNOXCUSTOMMANAGERSERVICE_LOCKSCREENHIDDENITEMS_METHOD =
            "getLockScreenHiddenItems";
    public static final String KNOXCUSTOMMANAGERSERVICE_LTESETTINGSTATE_METHOD =
            "getLTESettingState";
    public static final String KNOXCUSTOMMANAGERSERVICE_POWERMENULOCKEDSTATE_METHOD =
            "getPowerMenuLockedState";
    public static final String KNOXCUSTOMMANAGERSERVICE_POWERSAVINGMODE_METHOD =
            "getPowerSavingMode";
    public static final String KNOXCUSTOMMANAGERSERVICE_SCREENOFFONHOMELONGPRESSSTATE_METHOD =
            "getScreenOffOnHomeLongPressState";
    public static final String KNOXCUSTOMMANAGERSERVICE_SCREENOFFONSTATUSBARDOUBLETAPSTATE_METHOD =
            "getScreenOffOnStatusBarDoubleTapState";
    public static final String KNOXCUSTOMMANAGERSERVICE_SCREENWAKEUPONPOWERSTATE_METHOD =
            "getScreenWakeupOnPowerState";
    public static final String KNOXCUSTOMMANAGERSERVICE_SENSORDISABLED_METHOD = "getSensorDisabled";
    public static final String KNOXCUSTOMMANAGERSERVICE_SETTINGENABLEDITEM_METHOD =
            "getSettingsEnabledItems";
    public static final String KNOXCUSTOMMANAGERSERVICE_STATE_METHOD = "getSealedState";
    public static final String KNOXCUSTOMMANAGERSERVICE_STATUSBARTEXTSIZE_METHOD =
            "getStatusBarTextSize";
    public static final String KNOXCUSTOMMANAGERSERVICE_STATUSBARTEXTSTYLE_METHOD =
            "getStatusBarTextStyle";
    public static final String KNOXCUSTOMMANAGERSERVICE_STATUSBARTEXT_METHOD = "getStatusBarText";
    public static final String KNOXCUSTOMMANAGERSERVICE_TOASTENABLEDSTATE_METHOD =
            "getToastEnabledState";
    public static final String KNOXCUSTOMMANAGERSERVICE_TOASTGRAVITYENABLEDSTATE_METHOD =
            "getToastGravityEnabledState";
    public static final String KNOXCUSTOMMANAGERSERVICE_TOASTGRAVITYXOFFSET_METHOD =
            "getToastGravityXOffset";
    public static final String KNOXCUSTOMMANAGERSERVICE_TOASTGRAVITYYOFFSET_METHOD =
            "getToastGravityYOffset";
    public static final String KNOXCUSTOMMANAGERSERVICE_TOASTGRAVITY_METHOD = "getToastGravity";
    public static final String KNOXCUSTOMMANAGERSERVICE_TOASTSHOWPACKAGENAMESTATE_METHOD =
            "getToastShowPackageNameState";
    public static final String KNOXCUSTOMMANAGERSERVICE_TORCHONVOLUMEBUTTONSSTATE_METHOD =
            "getTorchOnVolumeButtonsState";
    public static final String KNOXCUSTOMMANAGERSERVICE_ULTRAPOWERSAVINGMODE_METHOD =
            "getUltraPowerSavingPackages";
    public static final String KNOXCUSTOMMANAGERSERVICE_USBCONNECTIONTYPEINTERNAL_METHOD =
            "getUsbConnectionTypeInternal";
    public static final String KNOXCUSTOMMANAGERSERVICE_USBCONNECTIONTYPE_METHOD =
            "getUsbConnectionType";
    public static final String KNOXCUSTOMMANAGERSERVICE_VOLUMEBUTTONROTATIONSTATE_METHOD =
            "getVolumeButtonRotationState";
    public static final String KNOXCUSTOMMANAGERSERVICE_VOLUMECONTROLSTREAM_METHOD =
            "getVolumeControlStream";
    public static final String KNOXCUSTOMMANAGERSERVICE_VOLUMEKEYAPPSLIST_METHOD =
            "getSealedVolumeKeyAppsList";
    public static final String KNOXCUSTOMMANAGERSERVICE_VOLUMEKEYAPPSTATE_METHOD =
            "getSealedVolumeKeyAppState";
    public static final String KNOXCUSTOMMANAGERSERVICE_VOLUMEPANELENABLEDSTATE_METHOD =
            "getVolumePanelEnabledState";
    public static final String KNOXCUSTOMMANAGERSERVICE_WIFICONNECTEDMESSAGESTATE_METHOD =
            "getWifiConnectedMessageState";
    public static final String LOCATIONPOLICY = "LocationPolicy";
    public static final String LOCATIONPOLICY_GPSSTATECHANGE_METHOD = "isGPSStateChangeAllowed";
    public static final String LOCATIONPOLICY_LOCATIONPROVIDERBLOCKEDASUSER_METHOD =
            "isLocationProviderBlockedAsUser";
    public static final String LOCATION_URI = "content://com.sec.knox.provider/LocationPolicy";
    public static final String MISCPOLICY = "MiscPolicy";
    public static final String MISCPOLICY_CURRENTLOCKSCREENSTRING_METHOD =
            "getCurrentLockScreenString";
    public static final String MISCPOLICY_NFCSTATECHANGE_METHOD = "isNFCStateChangeAllowed";
    public static final String MISC_URI = "content://com.sec.knox.provider2/MiscPolicy";
    public static final String MULTIUSERMANAGERPOLICY = "MultiUserManager";
    public static final String MULTIUSERMANAGERPOLICY_MULTIPLEUSERSUPPORTED_METHOD =
            "multipleUsersSupported";
    public static final String MUMCONTAINERPOLICY = "KnoxMUMContainerPolicy";
    public static final String MUMCONTAINERPOLICY_NFCENABLED_METHOD = "isNFCEnabled";
    public static final String MUMCONTAINER_URI =
            "content://com.sec.knox.provider2/KnoxMUMContainerPolicy";
    public static final String PASSWORD2_URI = "content://com.sec.knox.provider/PasswordPolicy2";
    public static final String PASSWORDPOLICY2 = "PasswordPolicy2";
    public static final String PASSWORDPOLICY_BIOMETRICAUTHENTICATIONASUSER_METHOD =
            "isBiometricAuthenticationEnabledAsUser";
    public static final String PASSWORDPOLICY_CHANGEREQUESTED_METHOD = "isChangeRequested";
    public static final String PASSWORDPOLICY_GETCURRENTFAILEDPASSWORDATEEMPTS_METHOD =
            "getCurrentFailedPasswordAttempts";
    public static final String PASSWORDPOLICY_MAXIMUMFAILEDPASSWORDSFORDISABLE_METHOD =
            "getMaximumFailedPasswordsForDisable";
    public static final String PASSWORDPOLICY_PASSWORDLOCKDELAY_METHOD = "getPasswordLockDelay";
    public static final String PASSWORDPOLICY_PASSWORDVISIBILITYEDASUSER_METHOD =
            "isPasswordVisibilityEnabledAsUser";
    public static final String PASSWORDPOLICY_PASSWORDVISIBILITYED_METHOD =
            "isPasswordVisibilityEnabled";
    public static final String PASSWORDPOLICY_SETPWDCHANGEREQUESTED_METHOD =
            "setPwdChangeRequested";
    public static final String PHONERESTRICTIONPOLICY = "PhoneRestrictionPolicy";
    public static final String PHONERESTRICTIONPOLICY_CHECKENABLEUSEOFPACKETDATA_METHOD =
            "checkEnableUseOfPacketData";
    public static final String PHONERESTRICTIONPOLICY_COPYCONTACTTOSIM_METHOD =
            "isCopyContactToSimAllowed";
    public static final String PHONERESTRICTIONPOLICY_EMERGENCYCALLONLY_METHOD =
            "getEmergencyCallOnly";
    public static final String PHONERESTRICTIONPOLICY_GETDISCLAIMERTEXT_METHOD =
            "getDisclaimerText";
    public static final String PHONERESTRICTIONPOLICY_INCOMINGMMS_METHOD = "isIncomingMmsAllowed";
    public static final String PHONERESTRICTIONPOLICY_ISDATAALLOWEDFROMSIMSLOT1_METHOD =
            "isDataAllowedFromSimSlot1";
    public static final String PHONERESTRICTIONPOLICY_ISDATAALLOWEDFROMSIMSLOT2_METHOD =
            "isDataAllowedFromSimSlot2";
    public static final String PHONERESTRICTIONPOLICY_ISMMSALLOWEDFROMSIMSLOT1_METHOD =
            "isMmsAllowedFromSimSlot1";
    public static final String PHONERESTRICTIONPOLICY_ISMMSALLOWEDFROMSIMSLOT2_METHOD =
            "isMmsAllowedFromSimSlot2";
    public static final String PHONERESTRICTIONPOLICY_OUTGOINGMMS_METHOD = "isOutgoingMmsAllowed";
    public static final String PHONERESTRICTIONPOLICY_RCS_ENABLED_METHOD = "isRCSEnabled";
    public static final String PHONERESTRICTIONPOLICY_SIMLOCKEDBYADMIN_METHOD =
            "isSimLockedByAdmin";
    public static final String PHONERESTRICTION_URI =
            "content://com.sec.knox.provider2/PhoneRestrictionPolicy";
    public static final String RESTRICTION1_URI =
            "content://com.sec.knox.provider/RestrictionPolicy1";
    public static final String RESTRICTION2_URI =
            "content://com.sec.knox.provider/RestrictionPolicy2";
    public static final String RESTRICTION3_URI =
            "content://com.sec.knox.provider/RestrictionPolicy3";
    public static final String RESTRICTION4_URI =
            "content://com.sec.knox.provider/RestrictionPolicy4";
    public static final String RESTRICTIONPOLICY1 = "RestrictionPolicy1";
    public static final String RESTRICTIONPOLICY2 = "RestrictionPolicy2";
    public static final String RESTRICTIONPOLICY3 = "RestrictionPolicy3";
    public static final String RESTRICTIONPOLICY4 = "RestrictionPolicy4";
    public static final String RESTRICTIONPOLICY_AIRPLANEMODEALLOWED_METHOD =
            "isAirplaneModeAllowed";
    public static final String RESTRICTIONPOLICY_ANDROIDBEAMALLOWED_METHOD = "isAndroidBeamAllowed";
    public static final String RESTRICTIONPOLICY_AUDIORECORDALLOWED_METHOD = "isAudioRecordAllowed";
    public static final String RESTRICTIONPOLICY_BACKGROUNDPROCESSLIMIT_METHOD =
            "isBackgroundProcessLimitEnabled";
    public static final String RESTRICTIONPOLICY_BACKUPALLOWED_METHOD = "isBackupAllowed";
    public static final String RESTRICTIONPOLICY_BLUETOOTHTETHERINGENABLED_METHOD =
            "isBluetoothTetheringEnabled";
    public static final String RESTRICTIONPOLICY_CAMERAENABLED_METHOD = "isCameraEnabled";
    public static final String RESTRICTIONPOLICY_CELLULARDATAALLOWED_METHOD =
            "isCellularDataAllowed";
    public static final String RESTRICTIONPOLICY_CLIPBOARDALLOWED_METHOD = "isClipboardAllowed";
    public static final String RESTRICTIONPOLICY_DATASAVINGALLOWED_METHOD = "isDataSavingAllowed";
    public static final String RESTRICTIONPOLICY_DEVELOPERMODEALLOWED_METHOD =
            "isDeveloperModeAllowed";
    public static final String RESTRICTIONPOLICY_FACTORYRESETALLOWED_METHOD =
            "isFactoryResetAllowed";
    public static final String RESTRICTIONPOLICY_FIRMWAREAUTOUPDATEALLOWED_METHOD =
            "isFirmwareAutoUpdateAllowed";
    public static final String RESTRICTIONPOLICY_FIRMWARERECOVERYALLOWED_METHOD =
            "isFirmwareRecoveryAllowed";
    public static final String RESTRICTIONPOLICY_GET_SELECTIVE_FOTA_METHOD = "getAllowedFOTAInfo";
    public static final String RESTRICTIONPOLICY_GOOGLECRASHREPORTED_METHOD =
            "isGoogleCrashReportedAllowed";
    public static final String RESTRICTIONPOLICY_IRISCAMERAENABLEDASUSER_METHOD =
            "isIrisCameraEnabledAsUser";
    public static final String RESTRICTIONPOLICY_KILLINGACTIVITIESONLEAVE_METHOD =
            "isKillingActivitiesOnLeaveAllowed";
    public static final String RESTRICTIONPOLICY_LOCKSCREENENABLED_METHOD = "isLockScreenEnabled";
    public static final String RESTRICTIONPOLICY_LOCKSCREENVIEW_METHOD = "isLockScreenViewAllowed";
    public static final String RESTRICTIONPOLICY_MICROPHONEASUSER_METHOD =
            "isMicrophoneEnabledAsUser";
    public static final String RESTRICTIONPOLICY_MICROPHONE_METHOD = "isMicrophoneEnabled";
    public static final String RESTRICTIONPOLICY_MOCKLOCATION_METHOD = "isMockLocationEnabled";
    public static final String RESTRICTIONPOLICY_NFCENABLEDWITHMSG_METHOD = "isNFCEnabledWithMsg";
    public static final String RESTRICTIONPOLICY_NFCENABLED_METHOD = "isNFCEnabled";
    public static final String RESTRICTIONPOLICY_NONMARKETAPP_METHOD = "isNonMarketAppAllowed";
    public static final String RESTRICTIONPOLICY_OTAUPGRADE_METHOD = "isOTAUpgradeAllowed";
    public static final String RESTRICTIONPOLICY_POWERSAVINGMODE_ALLOWED_METHOD =
            "isPowerSavingModeAllowed";
    public static final String RESTRICTIONPOLICY_SAFEMODE_METHOD = "isSafeModeAllowed";
    public static final String RESTRICTIONPOLICY_SCREENCAPTUREENABLED_METHOD =
            "isScreenCaptureEnabledInternal";
    public static final String RESTRICTIONPOLICY_SCREENPINNINGALLOWEDASUSER_METHOD =
            "isScreenPinningAllowedAsUser";
    public static final String RESTRICTIONPOLICY_SDCARDENABLED_METHOD = "isSdCardEnabled";
    public static final String RESTRICTIONPOLICY_SDCARDMOVEALLOWED_METHOD = "isSDCardMoveAllowed";
    public static final String RESTRICTIONPOLICY_SDCARDWRITEALLOW_METHOD = "isSDCardWriteAllowed";
    public static final String RESTRICTIONPOLICY_SETTINGSCHANGESASUSER_METHOD =
            "isSettingsChangesAllowedAsUser";
    public static final String RESTRICTIONPOLICY_SETTINGSCHANGES_METHOD =
            "isSettingsChangesAllowed";
    public static final String RESTRICTIONPOLICY_SHARELIST_METHOD = "isShareListAllowed";
    public static final String RESTRICTIONPOLICY_SMARTCLIPMODE_METHOD = "isSmartClipModeAllowed";
    public static final String RESTRICTIONPOLICY_STOPSYSTEMAPP_METHOD = "isStopSystemAppAllowed";
    public static final String RESTRICTIONPOLICY_SVOICEALLOWED_METHOD = "isSVoiceAllowed";
    public static final String RESTRICTIONPOLICY_USBDEBUGGING_METHOD = "isUsbDebuggingEnabled";
    public static final String RESTRICTIONPOLICY_USBTETHERING_METHOD = "isUsbTetheringEnabled";
    public static final String RESTRICTIONPOLICY_VIDEORECORD_METHOD = "isVideoRecordAllowed";
    public static final String RESTRICTIONPOLICY_VPNALLOWED_METHOD = "isVpnAllowed";
    public static final String RESTRICTIONPOLICY_WALLPAPERCHANGE_METHOD =
            "isWallpaperChangeAllowed";
    public static final String RESTRICTIONPOLICY_WIFIDIRECT_METHOD = "isWifiDirectAllowed";
    public static final String RESTRICTIONPOLICY_WIFIENABLED_METHOD = "isWifiEnabled";
    public static final String RESTRICTIONPOLICY_WIFITETHERING_METHOD = "isWifiTetheringEnabled";
    public static final String ROAMINGPOLICY = "RoamingPolicy";
    public static final String ROAMINGPOLICY_DATA_METHOD = "isRoamingDataEnabled";
    public static final String ROAMINGPOLICY_SYNC_METHOD = "isRoamingSyncEnabled";
    public static final String ROAMINGPOLICY_VOICECALL_METHOD = "isRoamingVoiceCallsEnabled";
    public static final String ROAMING_URI = "content://com.sec.knox.provider/RoamingPolicy";
    public static final String SEAMSPOLICY = "SeamsPolicy";
    public static final String SEAMS_URI = "content://com.sec.knox.provider/SeamsPolicy";
    public static final String SECURITYPOLICY = "SecurityPolicy";
    public static final String SECURITYPOLICY_DODBANNERVISIBLE_METHOD = "isDodBannerVisible";
    public static final String SECURITYPOLICY_GETCREDENTIALSTORAGESTATUS =
            "getCredentialStorageStatus";
    public static final String SECURITYPOLICY_SETDODBANNERVISIBLESTATUS_METHOD =
            "setDodBannerVisibleStatus";
    public static final String SECURITY_URI = "content://com.sec.knox.provider/SecurityPolicy";
    public static final String VPNPOLICY = "vpnPolicy";
    public static final String VPNPOLICY_USERADDPROFILESALLOWED_METHOD = "isUserAddProfilesAllowed";
    public static final String VPNPOLICY_USERCHANGEPROFILESALLOWED_METHOD =
            "isUserChangeProfilesAllowed";
    public static final String VPNPOLICY_USERSETALWAYSONALLOWED_METHOD = "isUserSetAlwaysOnAllowed";
    public static final String VPN_URI = "content://com.sec.knox.provider2/vpnPolicy";
    public static final String WIFIPOLICY = "WifiPolicy";
    public static final String WIFIPOLICY_ALLOWUSERPOLICYCHANGES_METHOD =
            "getAllowUserPolicyChanges";
    public static final String WIFIPOLICY_ENTERPRISENETWORK_METHOD = "isEnterpriseNetwork";
    public static final String WIFIPOLICY_PASSWORDHIDDEN_METHOD = "getPasswordHidden";
    public static final String WIFIPOLICY_PROMPTCREDENTIAL_METHOD = "getPromptCredentialsEnabled";
    public static final int WIFIPOLICY_SHOWTOASTFROMWIFIMODULE_WIFIBLOCKEDNETWORK = 0;
    public static final int WIFIPOLICY_SHOWTOASTFROMWIFIMODULE_WIFINETWORKINSECURE = 1;
    public static final String WIFIPOLICY_WIFISCANNINGALLOWED_METHOD = "isWifiScanningAllowed";
    public static final String WIFIPOLICY_WIFISTATECHANGEALLOWED_METHOD =
            "isWifiStateChangeAllowed";
    public static final String WIFI_URI = "content://com.sec.knox.provider2/WifiPolicy";
    public static final String EMAILPOLICY_EMAILNOTIFICATIONS_METHOD =
            "isEmailNotificationsEnabled";
    public static final String RESTRICTIONPOLICY_SCREENCAPTURE_METHOD = "isScreenCaptureEnabled";
    private static String[] list_supportContentObserver = {
        "isRCSEnabled",
        "isSettingsChangesAllowed",
        EMAILPOLICY_EMAILNOTIFICATIONS_METHOD,
        "isClipboardAllowed",
        SemKnoxPolicyContract.RestrictionPolicy.CLIPBOARD_SHARE_ALLOWED,
        RESTRICTIONPOLICY_SCREENCAPTURE_METHOD,
        "isCameraEnabled",
        SemKnoxPolicyContract.RestrictionPolicy.MIC_ENABLED,
        "getPackagesFromDisableClipboardBlackList",
        "getPackagesFromDisableClipboardWhiteList"
    };

    private SecContentProviderURI() {}

    public static boolean isContentObserverSupported(String selection) {
        if (selection != null && Arrays.asList(list_supportContentObserver).contains(selection)) {
            return true;
        }
        return false;
    }
}
