package android.sec.enterprise.content;

import java.util.Arrays;

/* loaded from: classes3.dex */
public class SecContentProviderURI {
  public static final String ACTION_GEARPOLICY_ENABLED_INTERNAL =
      "com.samsung.android.knox.intent.action.GEARPOLICY_ENABLE_INTERNAL";
  public static final String ADVANCEDRESTRICTIONPOLICY = "AdvancedRestrictionPolicy";
  public static final String ADVANCEDRESTRICTIONPOLICY_FIRMWAREAUTOUPDATEALLOWED =
      "isFirmwareAutoUpdateAllowed";
  public static final String ADVANCEDRESTRICTION_URI =
      "content://com.sec.knox.provider2/AdvancedRestrictionPolicy";
  public static final String APPLICATIONPERMISSIONCONTROLPOLICY =
      "ApplicationPermissionControlPolicy";

  /* renamed from: APPLICATIONPERMISSIONCONTROLPOLICY_GETPERMISSIONBLOCKEDLIST_METHOD */
  public static final String f466x5ed1ac6c = "getPermissionBlockedList";
  public static final String APPLICATIONPERMISSIONCONTROL_URI =
      "content://com.sec.knox.provider/ApplicationPermissionControlPolicy";
  public static final String APPLICATIONPOLICY = "ApplicationPolicy";
  public static final String APPLICATIONPOLICY_APPINSTALLTOSDCARD_METHOD = "getAppInstallToSdCard";
  public static final String APPLICATIONPOLICY_APPLICATIONCLEARCACHE_METHOD =
      "isApplicationClearCacheDisabled";
  public static final String APPLICATIONPOLICY_APPLICATIONCLEARDATA_METHOD =
      "isApplicationClearDataDisabled";
  public static final String APPLICATIONPOLICY_APPLICATIONCOMPONENTSTATE_METHOD =
      "getApplicationComponentState";
  public static final String APPLICATIONPOLICY_APPLICATIONFORCESTOP_METHOD =
      "isApplicationForceStopDisabled";
  public static final String APPLICATIONPOLICY_APPLICATIONICONFROMDB_METHOD =
      "getApplicationIconFromDb";
  public static final String APPLICATIONPOLICY_APPLICATIONINSTALLATIONMODE_METHOD =
      "getAppInstallationMode";
  public static final String APPLICATIONPOLICY_APPLICATIONINSTALLED_METHOD =
      "isApplicationInstalled";
  public static final String APPLICATIONPOLICY_APPLICATIONNAMEFROMDB_METHOD =
      "getApplicationNameFromDb";
  public static final String APPLICATIONPOLICY_APPLICATIONNOTIFICATIONMODE_METHOD =
      "getApplicationNotificationMode";
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
  public static final String APPLICATIONPOLICY_INTENTDISABLED_METHOD = "isIntentDisabled";
  public static final String APPLICATIONPOLICY_ISAPPLICATIONSETTODEFAULT_METHOD =
      "isApplicationSetToDefault";
  public static final String APPLICATIONPOLICY_PACKAGEUPDATEALLOWED_METHOD =
      "isPackageUpdateAllowed";
  public static final String APPLICATIONPOLICY_USBPERMITTEDFORPACKAGE_METHOD =
      "isUsbDevicePermittedForPackage";
  public static final String APPLICATION_URI = "content://com.sec.knox.provider2/ApplicationPolicy";
  public static final String AUDITLOGPOLICY_AUDITLOGASUSER_METHOD = "AuditLoggerAsUser";
  public static final String AUDITLOGPOLICY_AUDITLOGENABLED_METHOD = "isAuditLogEnabled";
  public static final String AUDITLOGPOLICY_AUTOCOMPLETE_EVENT = "AUTO_COMPLETING_DATA";
  public static final String AUDITLOGPOLICY_OPENPOPUP_EVENT = "OPEN_POPUP_URL";
  public static final String AUDITLOGPOLICY_OPENURL_EVENT = "OPEN_URL";
  public static final String AUDITPOLICY = "AuditLog";
  public static final String AUDIT_URI = "content://com.sec.knox.provider/AuditLog";
  public static final String AUTHORITY = "com.sec.knox.provider";
  public static final String AUTHORITY2 = "com.sec.knox.provider2";
  public static final String BLUETOOTHPOLICY = "BluetoothPolicy";
  public static final String BLUETOOTHPOLICY_BLESCANNINGALLOWED_METHOD = "isBLEAllowed";
  public static final String BLUETOOTHPOLICY_BLUETOOTHENABLEDWITHMSG_METHOD =
      "isBluetoothEnabledWithMsg";
  public static final String BLUETOOTHPOLICY_BLUETOOTHENABLED_METHOD = "isBluetoothEnabled";
  public static final String BLUETOOTHPOLICY_BLUETOOTHLOGENABLED_METHOD = "isBluetoothLogEnabled";
  public static final String BLUETOOTHPOLICY_BLUETOOTHLOG_METHOD = "bluetoothLog";
  public static final String BLUETOOTHPOLICY_DESKTOPCONNECTIVITY_METHOD =
      "isDesktopConnectivityEnabled";
  public static final String BLUETOOTHPOLICY_DISCOVERABLE_METHOD = "isDiscoverableEnabled";
  public static final String BLUETOOTHPOLICY_LIMITEDDISCOVERABLED_METHOD =
      "isLimitedDiscoverableEnabled";
  public static final String BLUETOOTHPOLICY_OUTGOINGCALLSALLOWED_METHOD = "isOutgoingCallsAllowed";
  public static final String BLUETOOTHUTILSPOLICY = "BluetoothUtils";
  public static final String BLUETOOTHUTILSPOLICY_URI =
      "content://com.sec.knox.provider/BluetoothUtils";
  public static final String BLUETOOTHUTILS_BLUETOOTHLOGENABLED_METHOD = "isBluetoothLogEnabled";
  public static final String BLUETOOTHUTILS_BLUETOOTHLOGFORDEVICE_METHOD = "bluetoothLogForDevice";
  public static final String BLUETOOTHUTILS_BLUETOOTHLOGFORREMOTE_METHOD = "bluetoothLogForRemote";
  public static final String BLUETOOTHUTILS_BLUETOOTHLOG_METHOD = "bluetoothLog";
  public static final String BLUETOOTHUTILS_BLUETOOTHSOCKETLOG_METHOD = "bluetoothSocketLog";
  public static final String BLUETOOTHUTILS_HEADSETALLOWEDBYSECURITY_METHOD =
      "isHeadsetAllowedBySecurityPolicy";
  public static final String BLUETOOTHUTILS_PAIRINGALLOWEDBYSECURITY_METHOD =
      "isPairingAllowedbySecurityPolicy";
  public static final String BLUETOOTHUTILS_PROFILEAUTHORIZEDBYSECURITY_METHOD =
      "isProfileAuthorizedBySecurityPolicy";
  public static final String BLUETOOTHUTILS_SOCKETALLOWEDBYSECURITY_METHOD =
      "isSocketAllowedBySecurityPolicy";
  public static final String BLUETOOTHUTILS_SVCRFCOMPORTNUMBERBLOCKEDBYSECURITY_METHOD =
      "isSvcRfComPortNumberBlockedBySecurityPolicy";
  public static final String BLUETOOTH_URI = "content://com.sec.knox.provider/BluetoothPolicy";
  public static final String BROWSERPOLICY = "BrowserPolicy";
  public static final String BROWSERPOLICY_AUTOFILL_METHOD = "getAutoFillSetting";
  public static final String BROWSERPOLICY_COOKIES_METHOD = "getCookiesSetting";
  public static final String BROWSERPOLICY_FORCEFRAUDWARNING_METHOD = "getForceFraudWarningSetting";
  public static final String BROWSERPOLICY_HTTPPROXY_METHOD = "getHttpProxy";
  public static final String BROWSERPOLICY_JAVASCRIPT_METHOD = "getJavaScriptSetting";
  public static final String BROWSERPOLICY_POPUPS_METHOD = "getPopupsSetting";
  public static final String BROWSERPOLICY_SETTINGSTATUS_METHOD = "getBrowserSettingStatus";
  public static final String BROWSER_URI = "content://com.sec.knox.provider/BrowserPolicy";
  public static final String CERTIFICATEPOLICY = "CertificatePolicy";
  public static final String CERTIFICATEPOLICY_CACERTIFICATEDISABLEDASUSER_METHOD =
      "isCaCertificateDisabledAsUser";
  public static final String CERTIFICATEPOLICY_CACERTIFICATEDISABLED_METHOD =
      "isCaCertificateDisabled";
  public static final String CERTIFICATEPOLICY_CACERTIFICATETRUSTEDASUSER_METHOD =
      "isCaCertificateTrustedAsUser";
  public static final String CERTIFICATEPOLICY_CACERTIFICATETRUSTED_METHOD =
      "isCaCertificateTrusted";
  public static final String CERTIFICATEPOLICY_CERTIFICATEVALIDATION_METHOD =
      "isCertificateValidationAtInstallEnabled";
  public static final String CERTIFICATEPOLICY_CERTIFICATE_REMOVED_METHOD = "certificateRemoved";
  public static final String CERTIFICATEPOLICY_IDENTITIEDFROMSIGNATURE_METHOD =
      "getIdentitiesFromSignatures";
  public static final String CERTIFICATEPOLICY_NOTIFY_METHOD = "notifyCertificateFailure";
  public static final String CERTIFICATEPOLICY_OCSPCHECK_METHOD = "isOcspCheckEnabled";
  public static final String CERTIFICATEPOLICY_PRIVATEKEYAPPLICATIONPERMITTED_METHOD =
      "isPrivateKeyApplicationPermitted";
  public static final String CERTIFICATEPOLICY_REVOCATIONCHECK_METHOD = "isRevocationCheckEnabled";
  public static final String CERTIFICATEPOLICY_SIGNATUREIDENTITYINFORMATION_METHOD =
      "isSignatureIdentityInformationEnabled";
  public static final String CERTIFICATEPOLICY_USERREMOVECERTIFICATES_METHOD =
      "isUserRemoveCertificatesAllowed";
  public static final String CERTIFICATEPOLICY_VALIDATECERTIFICATEATINSTALL_METHOD =
      "validateCertificateAtInstall";
  public static final String CERTIFICATEPOLICY_VALIDATECHAINATINSTALL_METHOD =
      "validateChainAtInstall";
  public static final String CERTIFICATE_URI = "content://com.sec.knox.provider/CertificatePolicy";
  public static final String CLIENTCERTIFICATEMANAGERPOLICY = "ClientCertificateManager";
  public static final String CLIENTCERTIFICATEMANAGER_CCMPOLICYENALBLEDFORPACKAGE_METHOD =
      "isCCMPolicyEnabledForPackage";
  public static final String CLIENTCERTIFICATEMANAGER_DELETECERTIFICATE_METHOD =
      "deleteCertificate";
  public static final String CLIENTCERTIFICATEMANAGER_GETCCMVERSION_METHOD = "getCCMVersion";
  public static final String CLIENTCERTIFICATEMANAGER_INSTALLCERTIFICATE_METHOD =
      "installCertificate";
  public static final String CLIENTCERTIFICATEMANAGER_URI =
      "content://com.sec.knox.provider2/ClientCertificateManager";
  public static final String CONTAINERAPPLICATIONPOLICY = "ContainerApplicationPolicy";
  public static final String CONTAINERAPPLICATIONPOLICY_GETPACKAGES_METHOD = "getPackages";
  public static final String CONTAINERAPPLICATION_URI =
      "content://com.sec.knox.provider/ContainerApplicationPolicy";
  public static final String CONTENT = "content://";
  public static final String DATETIMEPOLICY = "DateTimePolicy";
  public static final String DATETIMEPOLICY_DATETIMECHANGE_METHOD = "isDateTimeChangeEnalbed";
  public static final String DATETIMEPOLICY_GETAUTOMATIONTIME_METHOD = "getAutomaticTime";
  public static final String DATETIME_URI = "content://com.sec.knox.provider/DateTimePolicy";
  public static final String DEVICEACCOUNTPOLICY = "DeviceAccountPolicy";
  public static final String DEVICEACCOUNTPOLICY_ACCOUNTADDITION_METHOD =
      "isAccountAdditionAllowed";
  public static final String DEVICEACCOUNTPOLICY_ACCOUNTREMOVAL_METHOD = "isAccountRemovalAllowed";
  public static final String DEVICEACCOUNT_URI =
      "content://com.sec.knox.provider2/DeviceAccountPolicy";
  public static final String DEVICESETTIGNSPOLICY = "DeviceSettingsPolicy";
  public static final String DEVICESETTIGNS_URI =
      "content://com.sec.knox.provider/DeviceSettingsPolicy";
  public static final String DEVICESETTINGSPOLICY_NFCSTATECHANGE_METHOD = "isNFCStateChangeAllowed";
  public static final String DEXPOLICY = "DexPolicy";
  public static final String DEXPOLICY_DEX_CHECK_USE_DEX_MAC_ADDRESS_METHOD =
      "isVirtualMacAddressEnforced";
  public static final String DEXPOLICY_DEX_DISABLED_METHOD = "isDexDisabled";
  public static final String DEXPOLICY_DEX_ETHERNETONLY_MODE_METHOD = "isEthernetOnlyEnforced";
  public static final String DEXPOLICY_DEX_GET_VIRTUAL_MAC_ADDRESS_METHOD = "getVirtualMacAddress";
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
  public static final String DUALSIMPOLICY = "DualSimPolicy";
  public static final String DUALSIMSPOLICY_CHECKPRIVILEGEDNUMBER_METHOD = "checkPrivilegedNumber";
  public static final String DUALSIMSPOLICY_GETPREFERREDSIMSLOT_METHOD = "getpreferredsimslot";
  public static final String DUALSIM_URI = "content://com.sec.knox.provider2/DualSimPolicy";
  public static final String EMAILACCOUNTPOLICY = "EmailAccountPolicy";
  public static final String EMAILACCOUNTPOLICY_SECURITYINCOMING_METHOD =
      "getSecurityIncomingServerPassword";
  public static final String EMAILACCOUNTPOLICY_SECURITYOUTGOING_METHOD =
      "getSecurityOutgoingServerPassword";
  public static final String EMAILACCOUNTPOLICY_SETSECURITYINCOMING_METHOD =
      "setSecurityInComingServerPassword";
  public static final String EMAILACCOUNTPOLICY_SETSECURITYOUTGOING_METHOD =
      "setSecurityOutGoingServerPassword";
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
  public static final String ENTERPRISECERTENROLLPOLICY = "EnterpriseKnoxManagerPolicy";
  public static final String ENTERPRISECERTENROLL_NOTIFYUSERKEYUNLOCKED_METHOD =
      "notifyUserKeyUnlocked";
  public static final String ENTERPRISECERTENROLL_URI =
      "content://com.sec.knox.provider2/EnterpriseKnoxManagerPolicy";
  public static final String ENTERPRISECONTAINERPOLICY = "EnterpriseContainerPolicy";

  /* renamed from: ENTERPRISECONTAINERPOLICY_PASSWORDENABLEDCONTAINERLOCKTIMEOUT_METHOD */
  public static final String f467x69cea8d2 = "getPasswordEnabledContainerLockTimeout";
  public static final String ENTERPRISECONTAINERPOLICY_PASSWORDQUALITY_METHOD =
      "getPasswordQuality";
  public static final String ENTERPRISECONTAINERPOLICY_PASSWORDVISIBILITY_METHOD =
      "isPasswordVisibilityEnabled";
  public static final String ENTERPRISECONTAINERSERVICEPOLICY = "EnterpriseContainerService";
  public static final String ENTERPRISECONTAINERSERVICE_GETCONTAINERPACKAGES_METHOD =
      "getContainerPackages";
  public static final String ENTERPRISECONTAINERSERVICE_URI =
      "content://com.sec.knox.provider2/EnterpriseContainerService";
  public static final String ENTERPRISECONTAINER_URI =
      "content://com.sec.knox.provider2/EnterpriseContainerPolicy";
  public static final String ENTERPRISEDEVICEMANAGERPOLICY = "EnterpriseDeviceManager";
  public static final String ENTERPRISEDEVICEMANAGERPOLICY_ACTIVEADMINS_METHOD = "getActiveAdmins";
  public static final String ENTERPRISEDEVICEMANAGERPOLICY_ACTIVEADMIN_METHOD = "isActiveAdmin";
  public static final String ENTERPRISEDEVICEMANAGERPOLICY_ADMINREMOVABLE_METHOD =
      "getAdminRemovable";
  public static final String ENTERPRISEDEVICEMANAGERPOLICY_ISADMINREMOVABLE_METHOD =
      "isAdminRemovable";
  public static final String ENTERPRISEDEVICEMANAGERPOLICY_ISMDMADMINPRESENT_METHOD =
      "isMdmAdminPresent";
  public static final String ENTERPRISEDEVICEMANAGERPOLICY_MDMVERSIONCHECK_METHOD =
      "getEnterpriseSdkVer";
  public static final String ENTERPRISEDEVICEMANAGERPOLICY_MYKNOXADMIN_METHOD = "getMyKnoxAdmin";
  public static final String ENTERPRISEDEVICEMANAGERPOLICY_REMOVEACTIVEADMIN_METHOD =
      "removeActiveAdmin";
  public static final String ENTERPRISEDEVICEMANAGERPOLICY_REMOVEWARNINGS_METHOD =
      "getRemoveWarning";
  public static final String ENTERPRISEDEVICEMANAGERPOLICY_SETACTIVEADMIN_METHOD = "setActiveAdmin";
  public static final String ENTERPRISEDEVICEMANAGERPOLICY_SETADMINREMOVABLE_METHOD =
      "setAdminRemovable";
  public static final String ENTERPRISEDEVICEMANAGERPOLICY_UPDATEADMINPERMISSIONS_METHOD =
      "updateAdminPermissions";
  public static final String ENTERPRISEDEVICEMANAGER_URI =
      "content://com.sec.knox.provider2/EnterpriseDeviceManager";
  public static final String ENTERPRISEKNOXMANAGERPOLICY = "EnterpriseKnoxManagerPolicy";
  public static final String ENTERPRISEKNOXMANAGER_ADVANCEDRESTRICTION_METHOD =
      "getAdvancedRestrictionPolicy";
  public static final String ENTERPRISEKNOXMANAGER_URI =
      "content://com.sec.knox.provider/EnterpriseKnoxManagerPolicy";
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
  public static final String FIREWALLPOLICY_SAVEURLBLOCKEDREPORT_METHOD = "saveURLBlockedReport";
  public static final String FIREWALLPOLICY_URLBLOCKED_METHOD = "isUrlBlocked";
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
  public static final String KIOSKMODE_BLOCKEDHWKEYCACHE_METHOD = "getBlockedHwKeysCache";
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
  public static final String KIOSKMODE_TASKMANAGERALLOWED_METHOD = "isTaskManagerAllowed";
  public static final String KIOSKMODE_URI = "content://com.sec.knox.provider2/KioskMode";
  public static final String KNOXCONFIGURATIONTYPEPOLICY = "KnoxConfigurationType";
  public static final String KNOXCONFIGURATIONTYPE_REQUIREDPWDPATTERNRESTRICTIONS_METHOD =
      "getRequiredPwdPatternRestrictions";
  public static final String KNOXCONFIGURATIONTYPE_URI =
      "content://com.sec.knox.provider/KnoxConfigurationType";
  public static final String KNOXCUSTOMMANAGERSERVICE1_URI =
      "content://com.sec.knox.provider2/KnoxCustomManagerService1";
  public static final String KNOXCUSTOMMANAGERSERVICE2_URI =
      "content://com.sec.knox.provider2/KnoxCustomManagerService2";
  public static final String KNOXCUSTOMMANAGERSERVICEPOLICY1 = "KnoxCustomManagerService1";
  public static final String KNOXCUSTOMMANAGERSERVICEPOLICY2 = "KnoxCustomManagerService2";
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
  public static final String KNOXCUSTOMMANAGERSERVICE_CHECKCOVERPOPUPSTATE_METHOD =
      "getCheckCoverPopupState";
  public static final String KNOXCUSTOMMANAGERSERVICE_DEXAUTOOPENLASTAPP_METHOD =
      "isDexAutoOpenLastApp";
  public static final String KNOXCUSTOMMANAGERSERVICE_DEXHDMIAUTOENTER_METHOD =
      "getDexHDMIAutoEnter";
  public static final String KNOXCUSTOMMANAGERSERVICE_ETHERNETCONFIGURATIONTYPE_METHOD =
      "getEthernetConfigurationType";
  public static final String KNOXCUSTOMMANAGERSERVICE_ETHERNETSTATE_METHOD = "getEthernetState";
  public static final String KNOXCUSTOMMANAGERSERVICE_EXITUI_METHOD = "getSealedExitUI";
  public static final String KNOXCUSTOMMANAGERSERVICE_EXTENTEDCALLINFOSTATE_METHOD =
      "getExtendedCallInfoState";
  public static final String KNOXCUSTOMMANAGERSERVICE_GEARNOTIFICATIONSTATE_METHOD =
      "getGearNotificationState";
  public static final String KNOXCUSTOMMANAGERSERVICE_HARDKEYINTENTSTATE_METHOD =
      "getSealedHardKeyIntentState";
  public static final String KNOXCUSTOMMANAGERSERVICE_HIDENOTIFICATIONMESSAGES_METHOD =
      "getSealedHideNotificationMessages";
  public static final String KNOXCUSTOMMANAGERSERVICE_HOMEACTIVITY_METHOD = "getSealedHomeActivity";
  public static final String KNOXCUSTOMMANAGERSERVICE_INFRAREDSTATE_METHOD = "getInfraredState";
  public static final String KNOXCUSTOMMANAGERSERVICE_LOADINGLOGOPATH_METHOD = "getLoadingLogoPath";
  public static final String KNOXCUSTOMMANAGERSERVICE_LOCKSCREENHIDDENITEMS_METHOD =
      "getLockScreenHiddenItems";
  public static final String KNOXCUSTOMMANAGERSERVICE_LTESETTINGSTATE_METHOD = "getLTESettingState";
  public static final String KNOXCUSTOMMANAGERSERVICE_MULTIWINDOWFIXEDSTATE_METHOD =
      "getSealedMultiWindowFixedState";
  public static final String KNOXCUSTOMMANAGERSERVICE_NOTIFICATIONMESSAGESTATE_METHOD =
      "getSealedNotificationMessagesState";
  public static final String KNOXCUSTOMMANAGERSERVICE_POWERDIALOGCUSTOMITEMSSTATE_METHOD =
      "getSealedPowerDialogCustomItemsState";
  public static final String KNOXCUSTOMMANAGERSERVICE_POWERDIALOGCUSTOMITEMS_METHOD =
      "getSealedPowerDialogCustomItems";
  public static final String KNOXCUSTOMMANAGERSERVICE_POWERDIALOGITEMS_METHOD =
      "getSealedPowerDialogItems";
  public static final String KNOXCUSTOMMANAGERSERVICE_POWERDIALOGOPTIONMODE_METHOD =
      "getSealedPowerDialogOptionMode";
  public static final String KNOXCUSTOMMANAGERSERVICE_POWERMENULOCKEDSTATE_METHOD =
      "getPowerMenuLockedState";
  public static final String KNOXCUSTOMMANAGERSERVICE_POWERSAVINGMODE_METHOD = "getPowerSavingMode";
  public static final String KNOXCUSTOMMANAGERSERVICE_RECENTLONGPRESSACTIVITY_METHOD =
      "getRecentLongPressActivity";
  public static final String KNOXCUSTOMMANAGERSERVICE_RECENTLONGPRESSMODE_METHOD =
      "getRecentLongPressMode";
  public static final String KNOXCUSTOMMANAGERSERVICE_SCREENOFFONHOMELONGPRESSSTATE_METHOD =
      "getScreenOffOnHomeLongPressState";

  /* renamed from: KNOXCUSTOMMANAGERSERVICE_SCREENOFFONSTATUSBARDOUBLETAPSTATE_METHOD */
  public static final String f468xa1949b48 = "getScreenOffOnStatusBarDoubleTapState";
  public static final String KNOXCUSTOMMANAGERSERVICE_SCREENWAKEUPONPOWERSTATE_METHOD =
      "getScreenWakeupOnPowerState";
  public static final String KNOXCUSTOMMANAGERSERVICE_SEALEDMODESTRING_METHOD =
      "getSealedModeString";
  public static final String KNOXCUSTOMMANAGERSERVICE_SENSORDISABLED_METHOD = "getSensorDisabled";
  public static final String KNOXCUSTOMMANAGERSERVICE_SETSEALEDSTATE_METHOD = "setSealedState";
  public static final String KNOXCUSTOMMANAGERSERVICE_SETTINGENABLEDITEM_METHOD =
      "getSettingsEnabledItems";
  public static final String KNOXCUSTOMMANAGERSERVICE_SETTINGSHIDDENSTATE_METHOD =
      "getSettingsHiddenState";
  public static final String KNOXCUSTOMMANAGERSERVICE_STATE_METHOD = "getSealedState";
  public static final String KNOXCUSTOMMANAGERSERVICE_STATUSBARCLOCKSTATE_METHOD =
      "getSealedStatusBarClockState";
  public static final String KNOXCUSTOMMANAGERSERVICE_STATUSBARICONSTATE_METHOD =
      "getSealedStatusBarIconsState";
  public static final String KNOXCUSTOMMANAGERSERVICE_STATUSBARMODE_METHOD =
      "getSealedStatusBarMode";
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
  public static final String KNOXCUSTOMMANAGERSERVICE_USBMASSSTORAGESTATE_METHOD =
      "getSealedUsbMassStorageState";
  public static final String KNOXCUSTOMMANAGERSERVICE_USBNETADDRESS_METHOD =
      "getSealedUsbNetAddress";
  public static final String KNOXCUSTOMMANAGERSERVICE_USBNETSTATE_METHOD = "getSealedUsbNetState";
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
  public static final String LOCATIONPOLICY_LOCATIONPROVIDERBLOCKED_METHOD =
      "isLocationProviderBlocked";
  public static final String LOCATION_URI = "content://com.sec.knox.provider/LocationPolicy";
  public static final String MISCPOLICY = "MiscPolicy";
  public static final String MISCPOLICY_CURRENTLOCKSCREENSTRING_METHOD =
      "getCurrentLockScreenString";
  public static final String MISCPOLICY_NFCSTATECHANGE_METHOD = "isNFCStateChangeAllowed";
  public static final String MISC_URI = "content://com.sec.knox.provider2/MiscPolicy";
  public static final String MULTIUSERMANAGERPOLICY = "MultiUserManager";
  public static final String MULTIUSERMANAGERPOLICY_MULTIPLEUSERSUPPORTED_METHOD =
      "multipleUsersSupported";
  public static final String MULTIUSERMANAGERPOLICY_MULTIPLEUSER_METHOD = "multipleUsersAllowed";
  public static final String MULTIUSERMANAGERPOLICY_USERCREATION_METHOD = "isUserCreationAllowed";
  public static final String MULTIUSERMANAGERPOLICY_USERREMOVAL_METHOD = "isUserRemovalAllowed";
  public static final String MULTIUSERMANAGER_URI =
      "content://com.sec.knox.provider2/MultiUserManager";
  public static final String PASSWORD1_URI = "content://com.sec.knox.provider/PasswordPolicy1";
  public static final String PASSWORD2_URI = "content://com.sec.knox.provider/PasswordPolicy2";
  public static final String PASSWORDPOLICY1 = "PasswordPolicy1";
  public static final String PASSWORDPOLICY2 = "PasswordPolicy2";
  public static final String PASSWORDPOLICY_BIOMETRICAUTHENTICATIONASUSER_METHOD =
      "isBiometricAuthenticationEnabledAsUser";
  public static final String PASSWORDPOLICY_BIOMETRICAUTHENTICATION_METHOD =
      "isBiometricAuthenticationEnabled";
  public static final String PASSWORDPOLICY_CHANGEREQUESTED_METHOD = "isChangeRequested";
  public static final String PASSWORDPOLICY_EXTERNALSTORAGEFORFAILEDPASSWORDSWIPE_METHOD =
      "isExternalStorageForFailedPasswordsWipeExcluded";
  public static final String PASSWORDPOLICY_FORBIDDENCHARACTERSEQUENCE_METHOD =
      "hasForbiddenCharacterSequence";
  public static final String PASSWORDPOLICY_FORBIDDENDATA_METHOD = "hasForbiddenData";
  public static final String PASSWORDPOLICY_FORBIDDENNUMERICSEQUENCE_METHOD =
      "hasForbiddenNumericSequence";
  public static final String PASSWORDPOLICY_FORBIDDENSTRINGDISTANCE_METHOD =
      "hasForbiddenStringDistance";
  public static final String PASSWORDPOLICY_FORBIDDENSTRINGS_METHOD = "getForbiddenStrings";
  public static final String PASSWORDPOLICY_GETCURRENTFAILEDPASSWORDATEEMPTS_METHOD =
      "getCurrentFailedPasswordAttempts";
  public static final String PASSWORDPOLICY_MAXIMUMCHARACTERCHANGELENGTH_METHOD =
      "getMinimumCharacterChangeLength";
  public static final String PASSWORDPOLICY_MAXIMUMCHARACTEROCCURENCES_METHOD =
      "getMaximumCharacterOccurences";
  public static final String PASSWORDPOLICY_MAXIMUMCHARACTERSEQUENCELENGTH_METHOD =
      "getMaximumCharacterSequenceLength";
  public static final String PASSWORDPOLICY_MAXIMUMFAILEDPASSWORDSFORDISABLE_METHOD =
      "getMaximumFailedPasswordsForDisable";
  public static final String PASSWORDPOLICY_MAXIMUMFAILEDPASSWORDSFORWIPE_METHOD =
      "getMaximumFailedPasswordsForWipe";
  public static final String PASSWORDPOLICY_MAXIMUMNUMBERICSEQUENCELENGTH_METHOD =
      "getMaximumNumericSequenceLength";
  public static final String PASSWORDPOLICY_MAXREPEATEDCHARACTERS_METHOD =
      "hasMaxRepeatedCharacters";
  public static final String PASSWORDPOLICY_PASSWORDCHANGETIMEOUT_METHOD =
      "getPasswordChangeTimeout";
  public static final String PASSWORDPOLICY_PASSWORDLOCKDELAY_METHOD = "getPasswordLockDelay";
  public static final String PASSWORDPOLICY_PASSWORDPATTERNMATCHED_METHOD =
      "isPasswordPatternMatched";
  public static final String PASSWORDPOLICY_PASSWORDVISIBILITYEDASUSER_METHOD =
      "isPasswordVisibilityEnabledAsUser";
  public static final String PASSWORDPOLICY_PASSWORDVISIBILITYED_METHOD =
      "isPasswordVisibilityEnabled";
  public static final String PASSWORDPOLICY_REQUIREDPWDPATTERNRESTRICTIONS_METHOD =
      "getRequiredPwdPatternRestrictions";
  public static final String PASSWORDPOLICY_SCREENLOCKPATTERNVISIBILITY_METHOD =
      "isScreenLockPatternVisibilityEnabled";
  public static final String PASSWORDPOLICY_SERVICERUNNING_METHOD = "isServiceRunning";
  public static final String PASSWORDPOLICY_SETPWDCHANGEREQUESTED_METHOD = "setPwdChangeRequested";
  public static final String PHONERESTRICTIONPOLICY = "PhoneRestrictionPolicy";
  public static final String PHONERESTRICTIONPOLICY_BLOCKMMSWITHSTORAGE_METHOD =
      "isBlockMmsWithStorageEnabled";
  public static final String PHONERESTRICTIONPOLICY_BLOCKSMSWITHSTORAGE_METHOD =
      "isBlockSmsWithStorageEnabled";
  public static final String PHONERESTRICTIONPOLICY_CALLERIDDISPLAY_METHOD =
      "isCallerIDDisplayAllowed";
  public static final String PHONERESTRICTIONPOLICY_CANINCOMINGCALL_METHOD = "canIncomingCall";
  public static final String PHONERESTRICTIONPOLICY_CANINCOMINGSMS_METHOD = "canIncomingSms";
  public static final String PHONERESTRICTIONPOLICY_CANOUTGINGSMS_METHOD = "canOutgoingSms";
  public static final String PHONERESTRICTIONPOLICY_CANOUTGOINGCALL_METHOD = "canOutgoingCall";
  public static final String PHONERESTRICTIONPOLICY_CHECKENABLEUSEOFPACKETDATA_METHOD =
      "checkEnableUseOfPacketData";
  public static final String PHONERESTRICTIONPOLICY_COPYCONTACTTOSIM_METHOD =
      "isCopyContactToSimAllowed";
  public static final String PHONERESTRICTIONPOLICY_EMERGENCYCALLONLY_METHOD =
      "getEmergencyCallOnly";
  public static final String PHONERESTRICTIONPOLICY_GETDISCLAIMERTEXT_METHOD = "getDisclaimerText";
  public static final String PHONERESTRICTIONPOLICY_INCOMINGMMS_METHOD = "isIncomingMmsAllowed";
  public static final String PHONERESTRICTIONPOLICY_INCOMINGSMS_METHOD = "isIncomingSmsAllowed";
  public static final String PHONERESTRICTIONPOLICY_ISDATAALLOWEDFROMSIMSLOT1_METHOD =
      "isDataAllowedFromSimSlot1";
  public static final String PHONERESTRICTIONPOLICY_ISDATAALLOWEDFROMSIMSLOT2_METHOD =
      "isDataAllowedFromSimSlot2";
  public static final String PHONERESTRICTIONPOLICY_ISMMSALLOWEDFROMSIMSLOT1_METHOD =
      "isMmsAllowedFromSimSlot1";
  public static final String PHONERESTRICTIONPOLICY_ISMMSALLOWEDFROMSIMSLOT2_METHOD =
      "isMmsAllowedFromSimSlot2";
  public static final String PHONERESTRICTIONPOLICY_LIMITNUMBEROFSMS_METHOD =
      "isLimitNumberOfSmsEnabled";
  public static final String PHONERESTRICTIONPOLICY_OUTGOINGMMS_METHOD = "isOutgoingMmsAllowed";
  public static final String PHONERESTRICTIONPOLICY_OUTGOINGSMS_METHOD = "isOutgoingSmsAllowed";
  public static final String PHONERESTRICTIONPOLICY_RCS_ENABLED_METHOD = "isRCSEnabled";
  public static final String PHONERESTRICTIONPOLICY_SIMLOCKEDBYADMIN_METHOD = "isSimLockedByAdmin";
  public static final String PHONERESTRICTIONPOLICY_WAPPUSHALLOWED_METHOD = "isWapPushAllowed";
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
  public static final String RESTRICTIONPOLICY_ACTIVATIONLOCKALLOWED_METHOD =
      "isActivationLockAllowed";
  public static final String RESTRICTIONPOLICY_AIRPLANEMODEALLOWED_METHOD = "isAirplaneModeAllowed";
  public static final String RESTRICTIONPOLICY_ANDROIDBEAMALLOWED_METHOD = "isAndroidBeamAllowed";
  public static final String RESTRICTIONPOLICY_AUDIORECORDALLOWED_METHOD = "isAudioRecordAllowed";
  public static final String RESTRICTIONPOLICY_BACKGROUNDDATAENABLED_METHOD =
      "isBackgroundDataEnabled";
  public static final String RESTRICTIONPOLICY_BACKGROUNDPROCESSLIMIT_METHOD =
      "isBackgroundProcessLimitEnabled";
  public static final String RESTRICTIONPOLICY_BACKUPALLOWED_METHOD = "isBackupAllowed";
  public static final String RESTRICTIONPOLICY_BLUETOOTHTETHERINGENABLED_METHOD =
      "isBluetoothTetheringEnabled";
  public static final String RESTRICTIONPOLICY_CAMERAENABLED_METHOD = "isCameraEnabled";
  public static final String RESTRICTIONPOLICY_CELLULARDATAALLOWED_METHOD = "isCellularDataAllowed";
  public static final String RESTRICTIONPOLICY_CHECKPACKAGESOURCE_METHOD = "checkPackageSource";
  public static final String RESTRICTIONPOLICY_CLIPBOARDALLOWEDASUSER_METHOD =
      "isClipboardAllowedAsUser";
  public static final String RESTRICTIONPOLICY_CLIPBOARDALLOWED_METHOD = "isClipboardAllowed";
  public static final String RESTRICTIONPOLICY_CLIPBOARDSHAREALLOWEDASUSER_METHOD =
      "isClipboardShareAllowedAsUser";
  public static final String RESTRICTIONPOLICY_CLIPBOARDSHAREALLOWED_METHOD =
      "isClipboardShareAllowed";
  public static final String RESTRICTIONPOLICY_DATASAVINGALLOWED_METHOD = "isDataSavingAllowed";
  public static final String RESTRICTIONPOLICY_DEVELOPERMODEALLOWED_METHOD =
      "isDeveloperModeAllowed";
  public static final String RESTRICTIONPOLICY_FACTORYRESETALLOWED_METHOD = "isFactoryResetAllowed";
  public static final String RESTRICTIONPOLICY_FASTENCRYPTIONALLOWED_METHOD =
      "isFastEncryptionAllowed";
  public static final String RESTRICTIONPOLICY_FIRMWAREAUTOUPDATEALLOWED_METHOD =
      "isFirmwareAutoUpdateAllowed";
  public static final String RESTRICTIONPOLICY_FIRMWARERECOVERYALLOWED_METHOD =
      "isFirmwareRecoveryAllowed";
  public static final String RESTRICTIONPOLICY_FOTAUPDATEALLOWED_METHOD = "isFotaVersionAllowed";
  public static final String RESTRICTIONPOLICY_GEARPOLICYENABLED_INTENT =
      "com.samsung.edm.intent.action.GEARPOLICY_ENABLE_INTERNAL";
  public static final String RESTRICTIONPOLICY_GEARPOLICYENABLED_METHOD = "isGearPolicyEnabled";
  public static final String RESTRICTIONPOLICY_GET_SELECTIVE_FOTA_METHOD = "getAllowedFOTAInfo";
  public static final String RESTRICTIONPOLICY_GOOGLEACCOUNTSAUTOSYNCALLOWED_METHOD =
      "isGoogleAccountsAutoSyncAllowed";
  public static final String RESTRICTIONPOLICY_GOOGLECRASHREPORTED_METHOD =
      "isGoogleCrashReportedAllowed";
  public static final String RESTRICTIONPOLICY_HEADPHONE_METHOD = "isHeadPhoneEnabled";
  public static final String RESTRICTIONPOLICY_HOMEKEY_METHOD = "isHomeKeyEnabled";
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
  public static final String RESTRICTIONPOLICY_NEWADMININSTALLATION_METHOD =
      "isNewAdminInstallationEnabled";
  public static final String RESTRICTIONPOLICY_NFCENABLEDWITHMSG_METHOD = "isNFCEnabledWithMsg";
  public static final String RESTRICTIONPOLICY_NFCENABLED_METHOD = "isNFCEnabled";
  public static final String RESTRICTIONPOLICY_NONMARKETAPP_METHOD = "isNonMarketAppAllowed";
  public static final String RESTRICTIONPOLICY_NONTRUSTEDAPPINSTALLBLOCKED_METHOD =
      "isNonTrustedAppInstallBlocked";
  public static final String RESTRICTIONPOLICY_ODETRUSTEDBOOTVERIFICATION_METHOD =
      "isOdeTrustedBootVerificationEnabled";
  public static final String RESTRICTIONPOLICY_OTAUPGRADE_METHOD = "isOTAUpgradeAllowed";
  public static final String RESTRICTIONPOLICY_POWEROFF_METHOD = "isPowerOffAllowed";
  public static final String RESTRICTIONPOLICY_POWERSAVINGMODE_ALLOWED_METHOD =
      "isPowerSavingModeAllowed";
  public static final String RESTRICTIONPOLICY_SAFEMODE_METHOD = "isSafeModeAllowed";
  public static final String RESTRICTIONPOLICY_SBEAM_METHOD = "isSBeamAllowed";
  public static final String RESTRICTIONPOLICY_SCREENCAPTUREENABLED_METHOD =
      "isScreenCaptureEnabledInternal";
  public static final String RESTRICTIONPOLICY_SCREENPINNINGALLOWEDASUSER_METHOD =
      "isScreenPinningAllowedAsUser";
  public static final String RESTRICTIONPOLICY_SDCARDENABLED_METHOD = "isSdCardEnabled";
  public static final String RESTRICTIONPOLICY_SDCARDMOVEALLOWED_METHOD = "isSDCardMoveAllowed";
  public static final String RESTRICTIONPOLICY_SDCARDWRITEALLOW_METHOD = "isSDCardWriteAllowed";
  public static final String RESTRICTIONPOLICY_SETTINGSCHANGESASUSER_METHOD =
      "isSettingsChangesAllowedAsUser";
  public static final String RESTRICTIONPOLICY_SETTINGSCHANGES_METHOD = "isSettingsChangesAllowed";
  public static final String RESTRICTIONPOLICY_SHARELIST_METHOD = "isShareListAllowed";
  public static final String RESTRICTIONPOLICY_SMARTCLIPMODE_METHOD = "isSmartClipModeAllowed";
  public static final String RESTRICTIONPOLICY_STATUSBAREXPANSIONALLOWEDASUSER_METHOD =
      "isStatusBarExpansionallowedAsUser";
  public static final String RESTRICTIONPOLICY_STOPSYSTEMAPP_METHOD = "isStopSystemAppAllowed";
  public static final String RESTRICTIONPOLICY_SVOICEALLOWED_METHOD = "isSVoiceAllowed";
  public static final String RESTRICTIONPOLICY_USBDEBUGGING_METHOD = "isUsbDebuggingEnabled";
  public static final String RESTRICTIONPOLICY_USBHOSTSTORAGE_METHOD = "isUsbHostStorageAllowed";
  public static final String RESTRICTIONPOLICY_USBMASSSTORAGE_METHOD = "isUsbMassStorageEnabled";
  public static final String RESTRICTIONPOLICY_USBMEDIAPLAYERAVAILABLE_METHOD =
      "isUsbMediaPlayerAvailable";
  public static final String RESTRICTIONPOLICY_USBTETHERING_METHOD = "isUsbTetheringEnabled";
  public static final String RESTRICTIONPOLICY_USERMOBILEDATALIMIT_METHOD =
      "isUserMobileDataLimitAllowed";
  public static final String RESTRICTIONPOLICY_USESECUREKEYPAD_METHOD = "isUseSecureKeypadEnabled";
  public static final String RESTRICTIONPOLICY_VIDEORECORD_METHOD = "isVideoRecordAllowed";
  public static final String RESTRICTIONPOLICY_VPNALLOWED_METHOD = "isVpnAllowed";
  public static final String RESTRICTIONPOLICY_WALLPAPERCHANGE_METHOD = "isWallpaperChangeAllowed";
  public static final String RESTRICTIONPOLICY_WIFIDIRECT_METHOD = "isWifiDirectAllowed";
  public static final String RESTRICTIONPOLICY_WIFIENABLED_METHOD = "isWifiEnabled";
  public static final String RESTRICTIONPOLICY_WIFITETHERING_METHOD = "isWifiTetheringEnabled";
  public static final String ROAMINGPOLICY = "RoamingPolicy";
  public static final String ROAMINGPOLICY_DATA_METHOD = "isRoamingDataEnabled";
  public static final String ROAMINGPOLICY_PUSH_METHOD = "isRoamingPushEnabled";
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
  public static final String SMARTCARDBROWSERPOLICY = "SmartCardBrowserPolicy";
  public static final String SMARTCARDBROWSERPOLICY_AUTHENTICATION_METHOD =
      "isAuthenticationEnabled";
  public static final String SMARTCARDBROWSERPOLICY_GETCLIENTCERTIFICATE_METHOD =
      "getClientCertificateAlias";
  public static final String SMARTCARDBROWSER_URI =
      "content://com.sec.knox.provider/SmartCardBrowserPolicy";
  public static final String SMARTCARDEMAILPOLICY = "SmartCardEmailPolicy";
  public static final String SMARTCARDEMAILPOLICY_CREDENTIALREQUIRED_METHOD =
      "isCredentialRequired";
  public static final String SMARTCARDEMAILPOLICY_SMIMEENCRYPTIONCERTIFICATE_METHOD =
      "getSMIMEEncryptionCertificate";
  public static final String SMARTCARDEMAILPOLICY_SMIMESIGNATURECERTIFICATE_METHOD =
      "getSMIMESignatureCertificate";
  public static final String SMARTCARDEMAIL_URI =
      "content://com.sec.knox.provider/SmartCardEmailPolicy";
  public static final String SMARTCARD_URI =
      "content://com.sec.knox.provider/SmartCardBrowserPolicy";
  public static final String VPNPOLICY = "vpnPolicy";
  public static final String VPNPOLICY_CHECKRACOONSECURITY_METHOD = "checkRacoonSecurity";
  public static final String VPNPOLICY_USERADDPROFILESALLOWED_METHOD = "isUserAddProfilesAllowed";
  public static final String VPNPOLICY_USERCHANGEPROFILESALLOWED_METHOD =
      "isUserChangeProfilesAllowed";
  public static final String VPNPOLICY_USERSETALWAYSONALLOWED_METHOD = "isUserSetAlwaysOnAllowed";
  public static final String VPNPOLICY_VPNALLOWED_METHOD = "isVpnAllowed";
  public static final String VPN_URI = "content://com.sec.knox.provider2/vpnPolicy";
  public static final String WIFIPOLICY = "WifiPolicy";
  public static final String WIFIPOLICY_ALLOWUSERPOLICYCHANGES_METHOD = "getAllowUserPolicyChanges";
  public static final String WIFIPOLICY_ALLOWUSERPROFILES_METHOD = "getAllowUserProfiles";
  public static final String WIFIPOLICY_AUTOMATICCONNECTION_METHOD = "getAutomaticConnectionToWifi";
  public static final String WIFIPOLICY_EDMADDORUPDATE_METHOD = "edmAddOrUpdate";
  public static final String WIFIPOLICY_ENTERPRISENETWORK_METHOD = "isEnterpriseNetwork";
  public static final String WIFIPOLICY_OPENWIFIAPALLOWED_METHOD = "isOpenWifiApAllowed";
  public static final String WIFIPOLICY_PASSWORDHIDDEN_METHOD = "getPasswordHidden";
  public static final String WIFIPOLICY_PROMPTCREDENTIAL_METHOD = "getPromptCredentialsEnabled";
  public static final String WIFIPOLICY_REMOVENETWORKCONFIGURATION_METHOD =
      "removeNetworkConfiguration";
  public static final int WIFIPOLICY_SHOWTOASTFROMWIFIMODULE_WIFIBLOCKEDNETWORK = 0;
  public static final int WIFIPOLICY_SHOWTOASTFROMWIFIMODULE_WIFINETWORKINSECURE = 1;
  public static final String WIFIPOLICY_WIFIAPSETTINGUSERMODIFICATION_METHOD =
      "isWifiApSettingUserModificationAllowed";
  public static final String WIFIPOLICY_WIFISCANNINGALLOWED_METHOD = "isWifiScanningAllowed";
  public static final String WIFIPOLICY_WIFISTATECHANGEALLOWED_METHOD = "isWifiStateChangeAllowed";
  public static final String WIFI_URI = "content://com.sec.knox.provider2/WifiPolicy";
  public static final String EMAILPOLICY_EMAILNOTIFICATIONS_METHOD = "isEmailNotificationsEnabled";
  public static final String RESTRICTIONPOLICY_SCREENCAPTURE_METHOD = "isScreenCaptureEnabled";
  private static String[] list_supportContentObserver = {
    "isRCSEnabled",
    "isSettingsChangesAllowed",
    EMAILPOLICY_EMAILNOTIFICATIONS_METHOD,
    "isClipboardAllowed",
    "isClipboardShareAllowed",
    RESTRICTIONPOLICY_SCREENCAPTURE_METHOD,
    "isCameraEnabled",
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
