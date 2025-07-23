package android.app;

import android.accounts.AccountManager;
import android.accounts.IAccountManager;
import android.adservices.AdServicesFrameworkInitializer;
import android.annotation.SystemApi;
import android.app.IAlarmManager;
import android.app.IGameManagerService;
import android.app.IGrammaticalInflectionManager;
import android.app.ILocaleManager;
import android.app.INotificationManager;
import android.app.IWallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.admin.IDevicePolicyManager;
import android.app.ambientcontext.AmbientContextManager;
import android.app.ambientcontext.IAmbientContextManager;
import android.app.appfunctions.AppFunctionManager;
import android.app.appfunctions.AppFunctionManagerConfiguration;
import android.app.appfunctions.IAppFunctionManager;
import android.app.appsearch.AppSearchManagerFrameworkInitializer;
import android.app.blob.BlobStoreManagerFrameworkInitializer;
import android.app.contentsuggestions.ContentSuggestionsManager;
import android.app.contentsuggestions.IContentSuggestionsManager;
import android.app.contextualsearch.ContextualSearchManager;
import android.app.ecm.EnhancedConfirmationFrameworkInitializer;
import android.app.job.JobSchedulerFrameworkInitializer;
import android.app.ondeviceintelligence.OnDeviceIntelligenceFrameworkInitializer;
import android.app.people.PeopleManager;
import android.app.prediction.AppPredictionManager;
import android.app.role.RoleFrameworkInitializer;
import android.app.sdksandbox.SdkSandboxManagerFrameworkInitializer;
import android.app.search.SearchUiManager;
import android.app.slice.SliceManager;
import android.app.smartspace.SmartspaceManager;
import android.app.supervision.ISupervisionManager;
import android.app.supervision.SupervisionManager;
import android.app.time.TimeManager;
import android.app.timedetector.TimeDetector;
import android.app.timedetector.TimeDetectorImpl;
import android.app.timezonedetector.TimeZoneDetector;
import android.app.timezonedetector.TimeZoneDetectorImpl;
import android.app.trust.TrustManager;
import android.app.usage.IStorageStatsManager;
import android.app.usage.IUsageStatsManager;
import android.app.usage.StorageStatsManager;
import android.app.usage.UsageStatsManager;
import android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager;
import android.app.wallpapereffectsgeneration.WallpaperEffectsGenerationManager;
import android.app.wearable.IWearableSensingManager;
import android.app.wearable.WearableSensingManager;
import android.apphibernation.AppHibernationManager;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothFrameworkInitializer;
import android.companion.CompanionDeviceManager;
import android.companion.ICompanionDeviceManager;
import android.companion.virtual.IVirtualDeviceManager;
import android.companion.virtual.VirtualDeviceManager;
import android.compat.Compatibility;
import android.content.ClipboardManager;
import android.content.ContentCaptureOptions;
import android.content.Context;
import android.content.IRestrictionsManager;
import android.content.RestrictionsManager;
import android.content.om.IOverlayManager;
import android.content.om.OverlayManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.CrossProfileApps;
import android.content.pm.DataLoaderManager;
import android.content.pm.ICrossProfileApps;
import android.content.pm.IDataLoaderManager;
import android.content.pm.IShortcutService;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutManager;
import android.content.pm.verify.domain.DomainVerificationManager;
import android.content.pm.verify.domain.IDomainVerificationManager;
import android.content.res.Resources;
import android.content.rollback.RollbackManagerFrameworkInitializer;
import android.credentials.CredentialManager;
import android.credentials.ICredentialManager;
import android.debug.AdbManager;
import android.debug.IAdbManager;
import android.devicelock.DeviceLockFrameworkInitializer;
import android.graphics.fonts.FontManager;
import android.hardware.ConsumerIrManager;
import android.hardware.ISensorPrivacyManager;
import android.hardware.ISerialManager;
import android.hardware.SensorManager;
import android.hardware.SensorPrivacyManager;
import android.hardware.SerialManager;
import android.hardware.SystemSensorManager;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.IAuthService;
import android.hardware.camera2.CameraManager;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.ExynosDisplaySolutionManager;
import android.hardware.display.IExynosDisplaySolutionManager;
import android.hardware.face.FaceManager;
import android.hardware.face.IFaceService;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.IFingerprintService;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.IHdmiControlService;
import android.hardware.input.InputManager;
import android.hardware.iris.IIrisService;
import android.hardware.iris.IrisManager;
import android.hardware.lights.LightsManager;
import android.hardware.lights.SystemLightsManager;
import android.hardware.location.ContextHubManager;
import android.hardware.location.IContextHubService;
import android.hardware.radio.RadioManager;
import android.hardware.scontext.SContextManager;
import android.hardware.usb.IUsbManager;
import android.hardware.usb.UsbManager;
import android.health.connect.HealthServicesInitializer;
import android.location.CountryDetector;
import android.location.ICountryDetector;
import android.location.ILocationManager;
import android.location.LocationManager;
import android.media.AudioDeviceVolumeManager;
import android.media.AudioManager;
import android.media.MediaFrameworkInitializer;
import android.media.MediaFrameworkPlatformInitializer;
import android.media.MediaRouter;
import android.media.metrics.IMediaMetricsManager;
import android.media.metrics.MediaMetricsManager;
import android.media.midi.IMidiManager;
import android.media.midi.MidiManager;
import android.media.musicrecognition.IMusicRecognitionManager;
import android.media.musicrecognition.MusicRecognitionManager;
import android.media.projection.MediaProjectionManager;
import android.media.quality.IMediaQualityManager;
import android.media.quality.MediaQualityManager;
import android.media.soundtrigger.SoundTriggerManager;
import android.media.tv.ITvInputManager;
import android.media.tv.TvInputManager;
import android.media.tv.ad.ITvAdManager;
import android.media.tv.ad.TvAdManager;
import android.media.tv.interactive.ITvInteractiveAppManager;
import android.media.tv.interactive.TvInteractiveAppManager;
import android.media.tv.tunerresourcemanager.ITunerResourceManager;
import android.media.tv.tunerresourcemanager.TunerResourceManager;
import android.nearby.NearbyFrameworkInitializer;
import android.net.ConnectivityFrameworkInitializer;
import android.net.ConnectivityFrameworkInitializerBaklava;
import android.net.ConnectivityFrameworkInitializerTiramisu;
import android.net.INetworkPolicyManager;
import android.net.IPacProxyManager;
import android.net.IVpnManager;
import android.net.NetworkPolicyManager;
import android.net.NetworkScoreManager;
import android.net.NetworkWatchlistManager;
import android.net.PacProxyManager;
import android.net.TetheringManager;
import android.net.VpnManager;
import android.net.wifi.WifiFrameworkInitializer;
import android.net.wifi.nl80211.WifiNl80211Manager;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
import android.nfc.NfcFrameworkInitializer;
import android.ondevicepersonalization.OnDevicePersonalizationFrameworkInitializer;
import android.os.BatteryManager;
import android.os.BatteryStatsManager;
import android.os.BugreportManager;
import android.os.CustomFrequencyManager;
import android.os.DropBoxManager;
import android.os.HardwarePropertiesManager;
import android.os.IBatteryPropertiesRegistrar;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.IDumpstate;
import android.os.IHardwarePropertiesManager;
import android.os.IHintManager;
import android.os.IPowerManager;
import android.os.IPowerStatsService;
import android.os.IRecoverySystem;
import android.os.ISecurityStateManager;
import android.os.ISemHcmManager;
import android.os.ISemHqmManager;
import android.os.ISystemUpdateManager;
import android.os.IThermalService;
import android.os.IUserManager;
import android.os.IncidentManager;
import android.os.PerformanceHintManager;
import android.os.PermissionEnforcer;
import android.os.PowerManager;
import android.os.Process;
import android.os.ProfilingFrameworkInitializer;
import android.os.RecoverySystem;
import android.os.SecurityStateManager;
import android.os.SemHcmManager;
import android.os.SemHqmManager;
import android.os.ServiceManager;
import android.os.StatsFrameworkInitializer;
import android.os.SystemConfigManager;
import android.os.SystemUpdateManager;
import android.os.SystemVibrator;
import android.os.SystemVibratorManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.os.flagging.ConfigInfrastructureFrameworkInitializer;
import android.os.health.SystemHealthManager;
import android.os.image.DynamicSystemManager;
import android.os.image.IDynamicSystemService;
import android.os.incremental.IIncrementalService;
import android.os.incremental.IncrementalManager;
import android.os.storage.StorageManager;
import android.permission.LegacyPermissionManager;
import android.permission.PermissionCheckerManager;
import android.permission.PermissionControllerManager;
import android.permission.PermissionManager;
import android.print.IPrintManager;
import android.print.PrintManager;
import android.provider.E2eeContactKeysManager;
import android.provider.ProviderFrameworkInitializer;
import android.ranging.RangingFrameworkInitializer;
import android.safetycenter.SafetyCenterFrameworkInitializer;
import android.scheduling.SchedulingFrameworkInitializer;
import android.security.FileIntegrityManager;
import android.security.IFileIntegrityService;
import android.security.advancedprotection.AdvancedProtectionManager;
import android.security.advancedprotection.IAdvancedProtectionService;
import android.security.attestationverification.AttestationVerificationManager;
import android.security.attestationverification.IAttestationVerificationManagerService;
import android.security.authenticationpolicy.AuthenticationPolicyManager;
import android.security.authenticationpolicy.IAuthenticationPolicyService;
import android.security.intrusiondetection.IIntrusionDetectionService;
import android.security.intrusiondetection.IntrusionDetectionManager;
import android.security.keystore.KeyStoreManager;
import android.service.oemlock.IOemLockService;
import android.service.oemlock.OemLockManager;
import android.service.persistentdata.IPersistentDataBlockService;
import android.service.persistentdata.PersistentDataBlockManager;
import android.service.vr.IVrManager;
import android.system.virtualmachine.VirtualizationFrameworkInitializer;
import android.telecom.TelecomManager;
import android.telephony.MmsManager;
import android.telephony.TelephonyFrameworkInitializer;
import android.telephony.TelephonyRegistryManager;
import android.transparency.BinaryTransparencyManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.uwb.UwbFrameworkInitializer;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.WindowManagerImpl;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.autofill.AutofillManager;
import android.view.autofill.IAutoFillManager;
import android.view.contentcapture.ContentCaptureManager;
import android.view.contentcapture.IContentCaptureManager;
import android.view.displayhash.DisplayHashManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textclassifier.TextClassificationManager;
import android.view.textservice.TextServicesManager;
import android.view.translation.ITranslationManager;
import android.view.translation.TranslationManager;
import android.view.translation.UiTranslationManager;
import android.webkit.WebViewBootstrapFrameworkInitializer;
import com.android.internal.R;
import com.android.internal.app.IAppOpsService;
import com.android.internal.app.IBatteryStats;
import com.android.internal.app.ISoundTriggerService;
import com.android.internal.appwidget.IAppWidgetService;
import com.android.internal.graphics.fonts.IFontManager;
import com.android.internal.net.INetworkWatchlistManager;
import com.android.internal.os.IBinaryTransparencyService;
import com.android.internal.os.IDropBoxManagerService;
import com.android.internal.policy.PhoneLayoutInflater;
import com.android.internal.util.Preconditions;
import com.samsung.android.camera.manager.CameraServiceWorkerManager;
import com.samsung.android.cocktailbar.CocktailBarManager;
import com.samsung.android.cocktailbar.ICocktailBarService;
import com.samsung.android.content.clipboard.SemClipboardManager;
import com.samsung.android.content.smartclip.SpenGestureManager;
import com.samsung.android.contextengine.ISemContextEngineManager;
import com.samsung.android.contextengine.SemContextEngineManager;
import com.samsung.android.continuity.ISemContinuityManager;
import com.samsung.android.continuity.SemContinuityManager;
import com.samsung.android.displayaiqe.DisplayAiqeManager;
import com.samsung.android.displayaiqe.IDisplayAiqeManager;
import com.samsung.android.displayquality.ISemDisplayQualityManager;
import com.samsung.android.displayquality.SemDisplayQualityFeature;
import com.samsung.android.displayquality.SemDisplayQualityManager;
import com.samsung.android.displaysolution.ISemDisplaySolutionManager;
import com.samsung.android.displaysolution.SemDisplaySolutionManager;
import com.samsung.android.edge.SemEdgeManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.gesture.SemMotionRecognitionManager;
import com.samsung.android.hardware.display.ISemMdnieManager;
import com.samsung.android.hardware.display.SemMdnieManager;
import com.samsung.android.hardware.secinputdev.ISemInputDeviceManager;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.hwrs.ISemHwrsManager;
import com.samsung.android.hwrs.SemHwrsManager;
import com.samsung.android.iccc.IIntegrityControlCheckCenter;
import com.samsung.android.iccc.IntegrityControlCheckCenter;
import com.samsung.android.isrb.IsrbManager;
import com.samsung.android.knox.ISemPersonaManager;
import com.samsung.android.knox.ISemRemoteContentManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.SemRemoteContentManager;
import com.samsung.android.knox.mtd.IMTDService;
import com.samsung.android.knox.mtd.KMTDManager;
import com.samsung.android.lifeguard.LifeGuardManagerFrameworkInitializer;
import com.samsung.android.location.ISLocationManager;
import com.samsung.android.location.SemLocationManager;
import com.samsung.android.media.codec.SemVideoTranscodingService;
import com.samsung.android.media.fmradio.SemFmPlayer;
import com.samsung.android.mocca.IMoccaService;
import com.samsung.android.mocca.SemMdContextManager;
import com.samsung.android.multicontrol.IMultiControlManager;
import com.samsung.android.multicontrol.SemMultiControlManager;
import com.samsung.android.net.ExtendedEthernetManager;
import com.samsung.android.net.IExtendedEthernetManager;
import com.samsung.android.powerSolution.IpowerSolution;
import com.samsung.android.powerSolution.powerSolutionManager;
import com.samsung.android.provider.DynamicFeatureManager;
import com.samsung.android.provider.IDynamicFeatureManager;
import com.samsung.android.provider.SemDynamicFeature;
import com.samsung.android.remoteappmode.IRemoteAppMode;
import com.samsung.android.remoteappmode.SemRemoteAppModeManager;
import com.samsung.android.sepunion.IUnionManager;
import com.samsung.android.sepunion.SemUnionManager;
import com.samsung.android.sepunion.UnionUtils;
import com.samsung.android.shell.ShellFrameworkInitializer;
import com.samsung.android.ssdid.ISemSsdidManagerService;
import com.samsung.android.ssdid.SemSsdidManager;
import com.samsung.android.telecom.SemTelecomManager;
import com.samsung.android.wifi.ISemWifiManager;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifi.aware.ISemWifiAwareManager;
import com.samsung.android.wifi.aware.SemWifiAwareManager;
import com.samsung.android.wifi.p2p.ISemWifiP2pManager;
import com.samsung.android.wifi.p2p.SemWifiP2pManager;
import com.samsung.android.wifi.stdp.IStandardPlusManager;
import com.samsung.android.wifi.stdp.StandardPlusManager;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import vendor.samsung.frameworks.codecsolution.SemCodecSolutionService;

@SystemApi
/* loaded from: classes.dex */
public final class SystemServiceRegistry {
    static final long NULL_GAME_MANAGER_IN_WEAR = 340929737;
    private static final Map<String, String> SYSTEM_SERVICE_CLASS_NAMES;
    private static final Map<String, ServiceFetcher<?>> SYSTEM_SERVICE_FETCHERS;
    private static final Map<Class<?>, String> SYSTEM_SERVICE_NAMES;
    private static final String TAG = "SystemServiceRegistry";
    public static boolean sEnableServiceNotFoundWtf = false;
    private static volatile boolean sInitializing;
    private static int sServiceCacheSize;

    @SystemApi
    public interface ContextAwareServiceProducerWithBinder<TServiceClass> {
        TServiceClass createService(Context context, IBinder iBinder);
    }

    @SystemApi
    public interface ContextAwareServiceProducerWithoutBinder<TServiceClass> {
        TServiceClass createService(Context context);
    }

    interface ServiceFetcher<T> {
        T getService(ContextImpl contextImpl);

        default boolean supportsFetchWithoutContext() {
            return false;
        }
    }

    @SystemApi
    public interface StaticServiceProducerWithBinder<TServiceClass> {
        TServiceClass createService(IBinder iBinder);
    }

    @SystemApi
    public interface StaticServiceProducerWithoutBinder<TServiceClass> {
        TServiceClass createService();
    }

    static {
        boolean z;
        ArrayMap arrayMap = new ArrayMap();
        SYSTEM_SERVICE_NAMES = arrayMap;
        SYSTEM_SERVICE_FETCHERS = new ArrayMap();
        SYSTEM_SERVICE_CLASS_NAMES = new ArrayMap();
        registerService(Context.ACCESSIBILITY_SERVICE, AccessibilityManager.class, new CachedServiceFetcher<AccessibilityManager>() { // from class: android.app.SystemServiceRegistry.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public AccessibilityManager createService(ContextImpl contextImpl) {
                return AccessibilityManager.getInstance(contextImpl);
            }
        });
        registerService(Context.CAPTIONING_SERVICE, CaptioningManager.class, new CachedServiceFetcher<CaptioningManager>() { // from class: android.app.SystemServiceRegistry.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public CaptioningManager createService(ContextImpl contextImpl) {
                return new CaptioningManager(contextImpl);
            }
        });
        registerService("account", AccountManager.class, new CachedServiceFetcher<AccountManager>() { // from class: android.app.SystemServiceRegistry.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public AccountManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new AccountManager(contextImpl, IAccountManager.Stub.asInterface(ServiceManager.getServiceOrThrow("account")));
            }
        });
        registerService("activity", ActivityManager.class, new CachedServiceFetcher<ActivityManager>() { // from class: android.app.SystemServiceRegistry.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public ActivityManager createService(ContextImpl contextImpl) {
                return new ActivityManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        registerService(Context.ACTIVITY_TASK_SERVICE, ActivityTaskManager.class, new CachedServiceFetcher<ActivityTaskManager>() { // from class: android.app.SystemServiceRegistry.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public ActivityTaskManager createService(ContextImpl contextImpl) {
                return ActivityTaskManager.getInstance();
            }
        });
        registerService(Context.URI_GRANTS_SERVICE, UriGrantsManager.class, new CachedServiceFetcher<UriGrantsManager>() { // from class: android.app.SystemServiceRegistry.6
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public UriGrantsManager createService(ContextImpl contextImpl) {
                return new UriGrantsManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        registerService("alarm", AlarmManager.class, new CachedServiceFetcher<AlarmManager>() { // from class: android.app.SystemServiceRegistry.7
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public AlarmManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new AlarmManager(IAlarmManager.Stub.asInterface(ServiceManager.getServiceOrThrow("alarm")), contextImpl);
            }
        });
        registerService("audio", AudioManager.class, new CachedServiceFetcher<AudioManager>() { // from class: android.app.SystemServiceRegistry.8
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public AudioManager createService(ContextImpl contextImpl) {
                return new AudioManager(contextImpl);
            }
        });
        registerService(Context.AUDIO_DEVICE_VOLUME_SERVICE, AudioDeviceVolumeManager.class, new CachedServiceFetcher<AudioDeviceVolumeManager>() { // from class: android.app.SystemServiceRegistry.9
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public AudioDeviceVolumeManager createService(ContextImpl contextImpl) {
                return new AudioDeviceVolumeManager(contextImpl);
            }
        });
        registerService(Context.MEDIA_ROUTER_SERVICE, MediaRouter.class, new CachedServiceFetcher<MediaRouter>() { // from class: android.app.SystemServiceRegistry.10
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public MediaRouter createService(ContextImpl contextImpl) {
                return new MediaRouter(contextImpl);
            }
        });
        registerService(Context.CFMS_SERVICE, CustomFrequencyManager.class, new CachedServiceFetcher<CustomFrequencyManager>() { // from class: android.app.SystemServiceRegistry.11
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public CustomFrequencyManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                ICustomFrequencyManager asInterface = ICustomFrequencyManager.Stub.asInterface(ServiceManager.getService(Context.CFMS_SERVICE));
                if (asInterface == null) {
                    Log.wtf(SystemServiceRegistry.TAG, "Failed to get custom frequency manager service.");
                }
                return new CustomFrequencyManager(asInterface, contextImpl.mMainThread.getHandler());
            }
        });
        registerService(Context.ICCC_SERVICE, IntegrityControlCheckCenter.class, new CachedServiceFetcher<IntegrityControlCheckCenter>() { // from class: android.app.SystemServiceRegistry.12
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public IntegrityControlCheckCenter createService(ContextImpl contextImpl) {
                return new IntegrityControlCheckCenter(IIntegrityControlCheckCenter.Stub.asInterface(ServiceManager.getService(Context.ICCC_SERVICE)));
            }
        });
        registerService(Context.DISPLAY_AIQE_SERVICE, DisplayAiqeManager.class, new CachedServiceFetcher<DisplayAiqeManager>() { // from class: android.app.SystemServiceRegistry.14
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public DisplayAiqeManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new DisplayAiqeManager(contextImpl, IDisplayAiqeManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.DISPLAY_AIQE_SERVICE)));
            }
        });
        registerService(Context.HDMI_CONTROL_SERVICE, HdmiControlManager.class, new StaticServiceFetcher<HdmiControlManager>() { // from class: android.app.SystemServiceRegistry.15
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public HdmiControlManager createService() throws ServiceManager.ServiceNotFoundException {
                return new HdmiControlManager(IHdmiControlService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.HDMI_CONTROL_SERVICE)));
            }
        });
        registerService(Context.TEXT_CLASSIFICATION_SERVICE, TextClassificationManager.class, new CachedServiceFetcher<TextClassificationManager>() { // from class: android.app.SystemServiceRegistry.16
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TextClassificationManager createService(ContextImpl contextImpl) {
                return new TextClassificationManager(contextImpl);
            }
        });
        registerService(Context.FONT_SERVICE, FontManager.class, new CachedServiceFetcher<FontManager>() { // from class: android.app.SystemServiceRegistry.17
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public FontManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return FontManager.create(IFontManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.FONT_SERVICE)));
            }
        });
        registerService("clipboard", ClipboardManager.class, new CachedServiceFetcher<ClipboardManager>() { // from class: android.app.SystemServiceRegistry.18
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public ClipboardManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new ClipboardManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        arrayMap.put(android.text.ClipboardManager.class, "clipboard");
        registerService(Context.SEM_CLIPBOARD_SERVICE, SemClipboardManager.class, new CachedServiceFetcher<SemClipboardManager>() { // from class: android.app.SystemServiceRegistry.19
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemClipboardManager createService(ContextImpl contextImpl) {
                return new SemClipboardManager(contextImpl.getOuterContext());
            }
        });
        registerService(Context.PAC_PROXY_SERVICE, PacProxyManager.class, new CachedServiceFetcher<PacProxyManager>() { // from class: android.app.SystemServiceRegistry.20
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public PacProxyManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new PacProxyManager(contextImpl.getOuterContext(), IPacProxyManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.PAC_PROXY_SERVICE)));
            }
        });
        registerService(Context.NETD_SERVICE, IBinder.class, new StaticServiceFetcher<IBinder>() { // from class: android.app.SystemServiceRegistry.21
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public IBinder createService() throws ServiceManager.ServiceNotFoundException {
                return ServiceManager.getServiceOrThrow(Context.NETD_SERVICE);
            }
        });
        registerService(Context.TETHERING_SERVICE, TetheringManager.class, new AnonymousClass22());
        registerService(Context.VPN_MANAGEMENT_SERVICE, VpnManager.class, new CachedServiceFetcher<VpnManager>() { // from class: android.app.SystemServiceRegistry.23
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public VpnManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IVpnManager asInterface = IVpnManager.Stub.asInterface(ServiceManager.getService(Context.VPN_MANAGEMENT_SERVICE));
                if (asInterface == null && contextImpl.getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH) && android.server.Flags.allowRemovingVpnService()) {
                    return null;
                }
                return new VpnManager(contextImpl, asInterface);
            }
        });
        registerService(Context.COUNTRY_DETECTOR, CountryDetector.class, new StaticServiceFetcher<CountryDetector>() { // from class: android.app.SystemServiceRegistry.24
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public CountryDetector createService() throws ServiceManager.ServiceNotFoundException {
                return new CountryDetector(ICountryDetector.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.COUNTRY_DETECTOR)));
            }
        });
        registerService(Context.DEVICE_POLICY_SERVICE, DevicePolicyManager.class, new CachedServiceFetcher<DevicePolicyManager>() { // from class: android.app.SystemServiceRegistry.25
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public DevicePolicyManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new DevicePolicyManager(contextImpl, IDevicePolicyManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.DEVICE_POLICY_SERVICE)));
            }
        });
        registerService(Context.DOWNLOAD_SERVICE, DownloadManager.class, new CachedServiceFetcher<DownloadManager>() { // from class: android.app.SystemServiceRegistry.26
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public DownloadManager createService(ContextImpl contextImpl) {
                return new DownloadManager(contextImpl);
            }
        });
        registerService(Context.HQM_SERVICE, SemHqmManager.class, new CachedServiceFetcher<SemHqmManager>() { // from class: android.app.SystemServiceRegistry.27
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemHqmManager createService(ContextImpl contextImpl) {
                ISemHqmManager asInterface = ISemHqmManager.Stub.asInterface(ServiceManager.getService(Context.HQM_SERVICE));
                if (asInterface == null) {
                    Log.e(SystemServiceRegistry.TAG, "Failed to get Hqm manager service.");
                    return null;
                }
                return new SemHqmManager(asInterface, contextImpl.mMainThread.getHandler());
            }
        });
        if (!"0".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEM_CONFIG_HCM_AI_POWER_SAVING_LEVEL"))) {
            registerService(Context.HCM_SERVICE, SemHcmManager.class, new CachedServiceFetcher<SemHcmManager>() { // from class: android.app.SystemServiceRegistry.28
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                public SemHcmManager createService(ContextImpl contextImpl) {
                    ISemHcmManager asInterface = ISemHcmManager.Stub.asInterface(ServiceManager.getService(Context.HCM_SERVICE));
                    if (asInterface == null) {
                        Log.e(SystemServiceRegistry.TAG, "Failed to get Hcm manager service.");
                        return null;
                    }
                    return new SemHcmManager(asInterface, contextImpl.mMainThread.getHandler());
                }
            });
        }
        registerService(Context.BATTERY_SERVICE, BatteryManager.class, new CachedServiceFetcher<BatteryManager>() { // from class: android.app.SystemServiceRegistry.29
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public BatteryManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new BatteryManager(contextImpl, IBatteryStats.Stub.asInterface(ServiceManager.getServiceOrThrow("batterystats")), IBatteryPropertiesRegistrar.Stub.asInterface(ServiceManager.getServiceOrThrow("batteryproperties")));
            }
        });
        registerService(Context.DROPBOX_SERVICE, DropBoxManager.class, new CachedServiceFetcher<DropBoxManager>() { // from class: android.app.SystemServiceRegistry.30
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public DropBoxManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new DropBoxManager(contextImpl, IDropBoxManagerService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.DROPBOX_SERVICE)));
            }
        });
        registerService("transparency", BinaryTransparencyManager.class, new CachedServiceFetcher<BinaryTransparencyManager>() { // from class: android.app.SystemServiceRegistry.31
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public BinaryTransparencyManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new BinaryTransparencyManager(contextImpl, IBinaryTransparencyService.Stub.asInterface(ServiceManager.getServiceOrThrow("transparency")));
            }
        });
        registerService("input", InputManager.class, new CachedServiceFetcher<InputManager>() { // from class: android.app.SystemServiceRegistry.32
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public InputManager createService(ContextImpl contextImpl) {
                return new InputManager(contextImpl.getOuterContext());
            }
        });
        registerService(Context.DISPLAY_SERVICE, DisplayManager.class, new CachedServiceFetcher<DisplayManager>() { // from class: android.app.SystemServiceRegistry.33
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public DisplayManager createService(ContextImpl contextImpl) {
                return new DisplayManager(contextImpl.getOuterContext());
            }
        });
        registerService(Context.COLOR_DISPLAY_SERVICE, ColorDisplayManager.class, new CachedServiceFetcher<ColorDisplayManager>() { // from class: android.app.SystemServiceRegistry.34
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public ColorDisplayManager createService(ContextImpl contextImpl) {
                return new ColorDisplayManager();
            }
        });
        registerService(Context.INPUT_METHOD_SERVICE, InputMethodManager.class, new ServiceFetcher<InputMethodManager>() { // from class: android.app.SystemServiceRegistry.35
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.ServiceFetcher
            public InputMethodManager getService(ContextImpl contextImpl) {
                return InputMethodManager.forContext(contextImpl.getOuterContext());
            }
        });
        registerService(Context.TEXT_SERVICES_MANAGER_SERVICE, TextServicesManager.class, new CachedServiceFetcher<TextServicesManager>() { // from class: android.app.SystemServiceRegistry.36
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TextServicesManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                if (contextImpl.getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH) && ServiceManager.getService(Context.TEXT_SERVICES_MANAGER_SERVICE) == null && android.server.Flags.removeTextService()) {
                    return null;
                }
                return TextServicesManager.createInstance(contextImpl);
            }
        });
        registerService(Context.KEYGUARD_SERVICE, KeyguardManager.class, new CachedServiceFetcher<KeyguardManager>() { // from class: android.app.SystemServiceRegistry.37
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public KeyguardManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new KeyguardManager(contextImpl);
            }
        });
        registerService(Context.LAYOUT_INFLATER_SERVICE, LayoutInflater.class, new CachedServiceFetcher<LayoutInflater>() { // from class: android.app.SystemServiceRegistry.38
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public LayoutInflater createService(ContextImpl contextImpl) {
                return new PhoneLayoutInflater(contextImpl.getOuterContext());
            }
        });
        registerService("location", LocationManager.class, new CachedServiceFetcher<LocationManager>() { // from class: android.app.SystemServiceRegistry.39
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public LocationManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new LocationManager(contextImpl, ILocationManager.Stub.asInterface(ServiceManager.getServiceOrThrow("location")));
            }
        });
        registerService(Context.SEM_LOCATION_SERVICE, SemLocationManager.class, new CachedServiceFetcher<SemLocationManager>() { // from class: android.app.SystemServiceRegistry.40
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemLocationManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                Log.e(SystemServiceRegistry.TAG, "create SemLocationManager service");
                return new SemLocationManager(contextImpl, ISLocationManager.Stub.asInterface(ServiceManager.getService(Context.SEM_LOCATION_SERVICE)));
            }
        });
        registerService(Context.NETWORK_POLICY_SERVICE, NetworkPolicyManager.class, new CachedServiceFetcher<NetworkPolicyManager>() { // from class: android.app.SystemServiceRegistry.41
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public NetworkPolicyManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new NetworkPolicyManager(contextImpl, INetworkPolicyManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.NETWORK_POLICY_SERVICE)));
            }
        });
        registerService("notification", NotificationManager.class, new CachedServiceFetcher<NotificationManager>() { // from class: android.app.SystemServiceRegistry.42
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public NotificationManager createService(ContextImpl contextImpl) {
                Context outerContext = contextImpl.getOuterContext();
                return new NotificationManager(new ContextThemeWrapper(outerContext, Resources.selectSystemTheme(0, outerContext.getApplicationInfo().targetSdkVersion, 16973835, 16973935, 16974126, 16974130)));
            }
        });
        registerService(Context.PEOPLE_SERVICE, PeopleManager.class, new CachedServiceFetcher<PeopleManager>() { // from class: android.app.SystemServiceRegistry.43
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public PeopleManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new PeopleManager(contextImpl);
            }
        });
        registerService("power", PowerManager.class, new CachedServiceFetcher<PowerManager>() { // from class: android.app.SystemServiceRegistry.44
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public PowerManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new PowerManager(contextImpl.getOuterContext(), IPowerManager.Stub.asInterface(ServiceManager.getServiceOrThrow("power")), IThermalService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.THERMAL_SERVICE)), contextImpl.mMainThread.getHandler());
            }
        });
        registerService(Context.PERFORMANCE_HINT_SERVICE, PerformanceHintManager.class, new CachedServiceFetcher<PerformanceHintManager>() { // from class: android.app.SystemServiceRegistry.46
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public PerformanceHintManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return PerformanceHintManager.create();
            }
        });
        registerService("recovery", RecoverySystem.class, new CachedServiceFetcher<RecoverySystem>() { // from class: android.app.SystemServiceRegistry.47
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public RecoverySystem createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new RecoverySystem(IRecoverySystem.Stub.asInterface(ServiceManager.getServiceOrThrow("recovery")));
            }
        });
        registerService("search", SearchManager.class, new CachedServiceFetcher<SearchManager>() { // from class: android.app.SystemServiceRegistry.48
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SearchManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SearchManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        registerService(Context.SECURITY_STATE_SERVICE, SecurityStateManager.class, new CachedServiceFetcher<SecurityStateManager>() { // from class: android.app.SystemServiceRegistry.49
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SecurityStateManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SecurityStateManager(ISecurityStateManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.SECURITY_STATE_SERVICE)));
            }
        });
        registerService(Context.SENSOR_SERVICE, SensorManager.class, new CachedServiceFetcher<SensorManager>() { // from class: android.app.SystemServiceRegistry.50
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SensorManager createService(ContextImpl contextImpl) {
                return new SystemSensorManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler().getLooper());
            }
        });
        registerService(Context.SENSOR_PRIVACY_SERVICE, SensorPrivacyManager.class, new CachedServiceFetcher<SensorPrivacyManager>() { // from class: android.app.SystemServiceRegistry.51
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SensorPrivacyManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return SensorPrivacyManager.getInstance(contextImpl, ISensorPrivacyManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.SENSOR_PRIVACY_SERVICE)));
            }
        });
        registerService(Context.STATUS_BAR_SERVICE, StatusBarManager.class, new CachedServiceFetcher<StatusBarManager>() { // from class: android.app.SystemServiceRegistry.52
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public StatusBarManager createService(ContextImpl contextImpl) {
                return new StatusBarManager(contextImpl.getOuterContext());
            }
        });
        registerService(Context.SEM_STATUS_BAR_SERVICE, SemStatusBarManager.class, new CachedServiceFetcher<SemStatusBarManager>() { // from class: android.app.SystemServiceRegistry.53
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemStatusBarManager createService(ContextImpl contextImpl) {
                return new SemStatusBarManager(contextImpl.getOuterContext());
            }
        });
        registerService(Context.SEM_EDGE_SERVICE, SemEdgeManager.class, new CachedServiceFetcher<SemEdgeManager>() { // from class: android.app.SystemServiceRegistry.54
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemEdgeManager createService(ContextImpl contextImpl) {
                return new SemEdgeManager(contextImpl, INotificationManager.Stub.asInterface(ServiceManager.getService("notification")));
            }
        });
        registerService(Context.STORAGE_SERVICE, StorageManager.class, new CachedServiceFetcher<StorageManager>() { // from class: android.app.SystemServiceRegistry.55
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public StorageManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new StorageManager(contextImpl, contextImpl.mMainThread.getHandler().getLooper());
            }
        });
        registerService(Context.STORAGE_STATS_SERVICE, StorageStatsManager.class, new CachedServiceFetcher<StorageStatsManager>() { // from class: android.app.SystemServiceRegistry.56
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public StorageStatsManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new StorageStatsManager(contextImpl, IStorageStatsManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.STORAGE_STATS_SERVICE)));
            }
        });
        registerService(Context.SYSTEM_UPDATE_SERVICE, SystemUpdateManager.class, new CachedServiceFetcher<SystemUpdateManager>() { // from class: android.app.SystemServiceRegistry.57
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SystemUpdateManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SystemUpdateManager(ISystemUpdateManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.SYSTEM_UPDATE_SERVICE)));
            }
        });
        registerService(Context.SYSTEM_CONFIG_SERVICE, SystemConfigManager.class, new CachedServiceFetcher<SystemConfigManager>() { // from class: android.app.SystemServiceRegistry.58
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SystemConfigManager createService(ContextImpl contextImpl) {
                return new SystemConfigManager();
            }
        });
        registerService(Context.TELEPHONY_REGISTRY_SERVICE, TelephonyRegistryManager.class, new CachedServiceFetcher<TelephonyRegistryManager>() { // from class: android.app.SystemServiceRegistry.59
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TelephonyRegistryManager createService(ContextImpl contextImpl) {
                return new TelephonyRegistryManager(contextImpl);
            }
        });
        registerService(Context.TELECOM_SERVICE, TelecomManager.class, new CachedServiceFetcher<TelecomManager>() { // from class: android.app.SystemServiceRegistry.60
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TelecomManager createService(ContextImpl contextImpl) {
                return new TelecomManager(contextImpl.getOuterContext());
            }
        });
        if (SemTelecomManager.hasSamsungTelecomSystemFeature()) {
            registerService(Context.SEM_TELECOM_SERVICE, SemTelecomManager.class, new CachedServiceFetcher<SemTelecomManager>() { // from class: android.app.SystemServiceRegistry.61
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                public SemTelecomManager createService(ContextImpl contextImpl) {
                    return new SemTelecomManager(contextImpl.getOuterContext());
                }
            });
        }
        registerService("mms", MmsManager.class, new CachedServiceFetcher<MmsManager>() { // from class: android.app.SystemServiceRegistry.62
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public MmsManager createService(ContextImpl contextImpl) {
                return new MmsManager(contextImpl.getOuterContext());
            }
        });
        registerService(Context.UI_MODE_SERVICE, UiModeManager.class, new CachedServiceFetcher<UiModeManager>() { // from class: android.app.SystemServiceRegistry.63
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public UiModeManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new UiModeManager(contextImpl.getOuterContext());
            }
        });
        registerService("usb", UsbManager.class, new CachedServiceFetcher<UsbManager>() { // from class: android.app.SystemServiceRegistry.64
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public UsbManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new UsbManager(contextImpl, IUsbManager.Stub.asInterface(ServiceManager.getServiceOrThrow("usb")));
            }
        });
        registerService("adb", AdbManager.class, new CachedServiceFetcher<AdbManager>() { // from class: android.app.SystemServiceRegistry.65
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public AdbManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new AdbManager(contextImpl, IAdbManager.Stub.asInterface(ServiceManager.getServiceOrThrow("adb")));
            }
        });
        registerService(Context.SERIAL_SERVICE, SerialManager.class, new CachedServiceFetcher<SerialManager>() { // from class: android.app.SystemServiceRegistry.66
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SerialManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SerialManager(contextImpl, ISerialManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.SERIAL_SERVICE)));
            }
        });
        registerService(Context.VIBRATOR_MANAGER_SERVICE, VibratorManager.class, new CachedServiceFetcher<VibratorManager>() { // from class: android.app.SystemServiceRegistry.67
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public VibratorManager createService(ContextImpl contextImpl) {
                return new SystemVibratorManager(contextImpl);
            }
        });
        registerService(Context.VIBRATOR_SERVICE, Vibrator.class, new CachedServiceFetcher<Vibrator>() { // from class: android.app.SystemServiceRegistry.68
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public Vibrator createService(ContextImpl contextImpl) {
                return new SystemVibrator(contextImpl);
            }
        });
        registerService("wallpaper", WallpaperManager.class, new CachedServiceFetcher<WallpaperManager>() { // from class: android.app.SystemServiceRegistry.69
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public WallpaperManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IBinder service = ServiceManager.getService("wallpaper");
                if (service == null) {
                    ApplicationInfo applicationInfo = contextImpl.getApplicationInfo();
                    if (applicationInfo.targetSdkVersion >= 28 && applicationInfo.isInstantApp()) {
                        throw new ServiceManager.ServiceNotFoundException("wallpaper");
                    }
                    if (!Resources.getSystem().getBoolean(R.bool.config_enableWallpaperService)) {
                        return DisabledWallpaperManager.getInstance();
                    }
                    Log.e(SystemServiceRegistry.TAG, "No wallpaper service");
                }
                return new WallpaperManager(IWallpaperManager.Stub.asInterface(service), contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        registerService(Context.WIFI_NL80211_SERVICE, WifiNl80211Manager.class, new CachedServiceFetcher<WifiNl80211Manager>() { // from class: android.app.SystemServiceRegistry.70
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public WifiNl80211Manager createService(ContextImpl contextImpl) {
                return new WifiNl80211Manager(contextImpl.getOuterContext());
            }
        });
        registerService(Context.Power_Solution_FrameWork_Service, powerSolutionManager.class, new CachedServiceFetcher<powerSolutionManager>() { // from class: android.app.SystemServiceRegistry.71
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public powerSolutionManager createService(ContextImpl contextImpl) {
                return new powerSolutionManager(IpowerSolution.Stub.asInterface(ServiceManager.getService(Context.Power_Solution_FrameWork_Service)));
            }
        });
        registerService(Context.CameraServiceWorker_manager, CameraServiceWorkerManager.class, new CachedServiceFetcher<CameraServiceWorkerManager>() { // from class: android.app.SystemServiceRegistry.72
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public CameraServiceWorkerManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new CameraServiceWorkerManager(ServiceManager.getService("media.camera.worker"));
            }
        });
        registerService(Context.WINDOW_SERVICE, WindowManager.class, new CachedServiceFetcher<WindowManager>() { // from class: android.app.SystemServiceRegistry.73
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public WindowManager createService(ContextImpl contextImpl) {
                return new WindowManagerImpl(contextImpl);
            }
        });
        registerService("user", UserManager.class, new CachedServiceFetcher<UserManager>() { // from class: android.app.SystemServiceRegistry.74
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public UserManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new UserManager(contextImpl, IUserManager.Stub.asInterface(ServiceManager.getServiceOrThrow("user")));
            }
        });
        registerService(Context.APP_OPS_SERVICE, AppOpsManager.class, new CachedServiceFetcher<AppOpsManager>() { // from class: android.app.SystemServiceRegistry.75
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public AppOpsManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new AppOpsManager(contextImpl, IAppOpsService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.APP_OPS_SERVICE)));
            }
        });
        registerService(Context.CAMERA_SERVICE, CameraManager.class, new CachedServiceFetcher<CameraManager>() { // from class: android.app.SystemServiceRegistry.76
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public CameraManager createService(ContextImpl contextImpl) {
                return new CameraManager(contextImpl);
            }
        });
        registerService(Context.LAUNCHER_APPS_SERVICE, LauncherApps.class, new CachedServiceFetcher<LauncherApps>() { // from class: android.app.SystemServiceRegistry.77
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public LauncherApps createService(ContextImpl contextImpl) {
                return new LauncherApps(contextImpl);
            }
        });
        registerService(Context.RESTRICTIONS_SERVICE, RestrictionsManager.class, new CachedServiceFetcher<RestrictionsManager>() { // from class: android.app.SystemServiceRegistry.78
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public RestrictionsManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new RestrictionsManager(contextImpl, IRestrictionsManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.RESTRICTIONS_SERVICE)));
            }
        });
        registerService(Context.PRINT_SERVICE, PrintManager.class, new CachedServiceFetcher<PrintManager>() { // from class: android.app.SystemServiceRegistry.79
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public PrintManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new PrintManager(contextImpl.getOuterContext(), contextImpl.getPackageManager().hasSystemFeature(PackageManager.FEATURE_PRINTING) ? IPrintManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.PRINT_SERVICE)) : null, contextImpl.getUserId(), UserHandle.getAppId(contextImpl.getApplicationInfo().uid));
            }
        });
        registerService(Context.COMPANION_DEVICE_SERVICE, CompanionDeviceManager.class, new CachedServiceFetcher<CompanionDeviceManager>() { // from class: android.app.SystemServiceRegistry.80
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public CompanionDeviceManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new CompanionDeviceManager(contextImpl.getPackageManager().hasSystemFeature(PackageManager.FEATURE_COMPANION_DEVICE_SETUP) ? ICompanionDeviceManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.COMPANION_DEVICE_SERVICE)) : null, contextImpl.getOuterContext());
            }
        });
        if (com.android.internal.hidden_from_bootclasspath.android.app.appfunctions.flags.Flags.enableAppFunctionManager()) {
            registerService(Context.APP_FUNCTION_SERVICE, AppFunctionManager.class, new CachedServiceFetcher<AppFunctionManager>() { // from class: android.app.SystemServiceRegistry.81
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                public AppFunctionManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                    if (AppFunctionManagerConfiguration.isSupported(contextImpl)) {
                        return new AppFunctionManager(IAppFunctionManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.APP_FUNCTION_SERVICE)), contextImpl.getOuterContext());
                    }
                    return null;
                }
            });
        }
        registerService(Context.VIRTUAL_DEVICE_SERVICE, VirtualDeviceManager.class, new CachedServiceFetcher<VirtualDeviceManager>() { // from class: android.app.SystemServiceRegistry.82
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public VirtualDeviceManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                if (contextImpl.getResources().getBoolean(R.bool.config_enableVirtualDeviceManager)) {
                    return new VirtualDeviceManager(IVirtualDeviceManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.VIRTUAL_DEVICE_SERVICE)), contextImpl.getOuterContext());
                }
                return null;
            }
        });
        registerService(Context.CONSUMER_IR_SERVICE, ConsumerIrManager.class, new CachedServiceFetcher<ConsumerIrManager>() { // from class: android.app.SystemServiceRegistry.83
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public ConsumerIrManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new ConsumerIrManager(contextImpl);
            }
        });
        registerService(Context.TRUST_SERVICE, TrustManager.class, new StaticServiceFetcher<TrustManager>() { // from class: android.app.SystemServiceRegistry.84
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public TrustManager createService() throws ServiceManager.ServiceNotFoundException {
                return new TrustManager(ServiceManager.getServiceOrThrow(Context.TRUST_SERVICE));
            }
        });
        registerService(Context.FINGERPRINT_SERVICE, FingerprintManager.class, new CachedServiceFetcher<FingerprintManager>() { // from class: android.app.SystemServiceRegistry.85
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public FingerprintManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IBinder service;
                if (contextImpl.getApplicationInfo().targetSdkVersion >= 26) {
                    service = ServiceManager.getServiceOrThrow(Context.FINGERPRINT_SERVICE);
                } else {
                    service = ServiceManager.getService(Context.FINGERPRINT_SERVICE);
                }
                return new FingerprintManager(contextImpl.getOuterContext(), IFingerprintService.Stub.asInterface(service));
            }
        });
        registerService(Context.FACE_SERVICE, FaceManager.class, new CachedServiceFetcher<FaceManager>() { // from class: android.app.SystemServiceRegistry.86
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public FaceManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IBinder service;
                if (contextImpl.getApplicationInfo().targetSdkVersion >= 26) {
                    service = ServiceManager.getServiceOrThrow(Context.FACE_SERVICE);
                } else {
                    service = ServiceManager.getService(Context.FACE_SERVICE);
                }
                return new FaceManager(contextImpl.getOuterContext(), IFaceService.Stub.asInterface(service));
            }
        });
        registerService(Context.IRIS_SERVICE, IrisManager.class, new CachedServiceFetcher<IrisManager>() { // from class: android.app.SystemServiceRegistry.87
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public IrisManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new IrisManager(contextImpl.getOuterContext(), IIrisService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.IRIS_SERVICE)));
            }
        });
        registerService(Context.BIOMETRIC_SERVICE, BiometricManager.class, new CachedServiceFetcher<BiometricManager>() { // from class: android.app.SystemServiceRegistry.88
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public BiometricManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new BiometricManager(contextImpl.getOuterContext(), IAuthService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.AUTH_SERVICE)));
            }
        });
        registerService(Context.AUTHENTICATION_POLICY_SERVICE, AuthenticationPolicyManager.class, new CachedServiceFetcher<AuthenticationPolicyManager>() { // from class: android.app.SystemServiceRegistry.89
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public AuthenticationPolicyManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                if (!com.android.internal.hidden_from_bootclasspath.android.security.Flags.secureLockdown()) {
                    throw new ServiceManager.ServiceNotFoundException(Context.AUTHENTICATION_POLICY_SERVICE);
                }
                return new AuthenticationPolicyManager(contextImpl.getOuterContext(), IAuthenticationPolicyService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.AUTHENTICATION_POLICY_SERVICE)));
            }
        });
        registerService(Context.TV_INTERACTIVE_APP_SERVICE, TvInteractiveAppManager.class, new CachedServiceFetcher<TvInteractiveAppManager>() { // from class: android.app.SystemServiceRegistry.90
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TvInteractiveAppManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new TvInteractiveAppManager(ITvInteractiveAppManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.TV_INTERACTIVE_APP_SERVICE)), contextImpl.getUserId());
            }
        });
        registerService(Context.TV_AD_SERVICE, TvAdManager.class, new CachedServiceFetcher<TvAdManager>() { // from class: android.app.SystemServiceRegistry.91
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TvAdManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new TvAdManager(ITvAdManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.TV_AD_SERVICE)), contextImpl.getUserId());
            }
        });
        registerService(Context.TV_INPUT_SERVICE, TvInputManager.class, new CachedServiceFetcher<TvInputManager>() { // from class: android.app.SystemServiceRegistry.92
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TvInputManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new TvInputManager(ITvInputManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.TV_INPUT_SERVICE)), contextImpl.getUserId());
            }
        });
        registerService(Context.TV_TUNER_RESOURCE_MGR_SERVICE, TunerResourceManager.class, new CachedServiceFetcher<TunerResourceManager>() { // from class: android.app.SystemServiceRegistry.93
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TunerResourceManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new TunerResourceManager(ITunerResourceManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.TV_TUNER_RESOURCE_MGR_SERVICE)), contextImpl.getUserId());
            }
        });
        registerService(Context.NETWORK_SCORE_SERVICE, NetworkScoreManager.class, new CachedServiceFetcher<NetworkScoreManager>() { // from class: android.app.SystemServiceRegistry.94
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public NetworkScoreManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new NetworkScoreManager(contextImpl);
            }
        });
        registerService(Context.USAGE_STATS_SERVICE, UsageStatsManager.class, new CachedServiceFetcher<UsageStatsManager>() { // from class: android.app.SystemServiceRegistry.95
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public UsageStatsManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new UsageStatsManager(contextImpl.getOuterContext(), IUsageStatsManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.USAGE_STATS_SERVICE)));
            }
        });
        registerService(Context.PERSISTENT_DATA_BLOCK_SERVICE, PersistentDataBlockManager.class, new StaticServiceFetcher<PersistentDataBlockManager>() { // from class: android.app.SystemServiceRegistry.96
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public PersistentDataBlockManager createService() throws ServiceManager.ServiceNotFoundException {
                IPersistentDataBlockService asInterface = IPersistentDataBlockService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.PERSISTENT_DATA_BLOCK_SERVICE));
                if (asInterface != null) {
                    return new PersistentDataBlockManager(asInterface);
                }
                return null;
            }
        });
        registerService(Context.OEM_LOCK_SERVICE, OemLockManager.class, new StaticServiceFetcher<OemLockManager>() { // from class: android.app.SystemServiceRegistry.97
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public OemLockManager createService() throws ServiceManager.ServiceNotFoundException {
                IOemLockService asInterface = IOemLockService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.OEM_LOCK_SERVICE));
                if (asInterface != null) {
                    return new OemLockManager(asInterface);
                }
                return null;
            }
        });
        registerService(Context.MEDIA_PROJECTION_SERVICE, MediaProjectionManager.class, new CachedServiceFetcher<MediaProjectionManager>() { // from class: android.app.SystemServiceRegistry.98
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public MediaProjectionManager createService(ContextImpl contextImpl) {
                return new MediaProjectionManager(contextImpl);
            }
        });
        registerService(Context.APPWIDGET_SERVICE, AppWidgetManager.class, new CachedServiceFetcher<AppWidgetManager>() { // from class: android.app.SystemServiceRegistry.99
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public AppWidgetManager createService(ContextImpl contextImpl) {
                IBinder service = ServiceManager.getService(Context.APPWIDGET_SERVICE);
                if (service == null) {
                    return null;
                }
                return new AppWidgetManager(contextImpl, IAppWidgetService.Stub.asInterface(service));
            }
        });
        registerService("midi", MidiManager.class, new CachedServiceFetcher<MidiManager>() { // from class: android.app.SystemServiceRegistry.100
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public MidiManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new MidiManager(IMidiManager.Stub.asInterface(ServiceManager.getServiceOrThrow("midi")));
            }
        });
        registerService(Context.RADIO_SERVICE, RadioManager.class, new CachedServiceFetcher<RadioManager>() { // from class: android.app.SystemServiceRegistry.101
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public RadioManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new RadioManager(contextImpl);
            }
        });
        registerService(Context.HARDWARE_PROPERTIES_SERVICE, HardwarePropertiesManager.class, new CachedServiceFetcher<HardwarePropertiesManager>() { // from class: android.app.SystemServiceRegistry.102
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public HardwarePropertiesManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new HardwarePropertiesManager(contextImpl, IHardwarePropertiesManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.HARDWARE_PROPERTIES_SERVICE)));
            }
        });
        registerService(Context.SOUND_TRIGGER_SERVICE, SoundTriggerManager.class, new CachedServiceFetcher<SoundTriggerManager>() { // from class: android.app.SystemServiceRegistry.103
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SoundTriggerManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SoundTriggerManager(contextImpl, ISoundTriggerService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.SOUND_TRIGGER_SERVICE)));
            }
        });
        registerService("shortcut", ShortcutManager.class, new CachedServiceFetcher<ShortcutManager>() { // from class: android.app.SystemServiceRegistry.104
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public ShortcutManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new ShortcutManager(contextImpl, IShortcutService.Stub.asInterface(ServiceManager.getServiceOrThrow("shortcut")));
            }
        });
        registerService("overlay", OverlayManager.class, new CachedServiceFetcher<OverlayManager>() { // from class: android.app.SystemServiceRegistry.105
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public OverlayManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IBinder serviceOrThrow;
                if (Compatibility.isChangeEnabled(OverlayManager.SELF_TARGETING_OVERLAY)) {
                    serviceOrThrow = ServiceManager.getService("overlay");
                } else {
                    serviceOrThrow = ServiceManager.getServiceOrThrow("overlay");
                }
                return new OverlayManager(contextImpl, IOverlayManager.Stub.asInterface(serviceOrThrow));
            }
        });
        registerService(Context.NETWORK_WATCHLIST_SERVICE, NetworkWatchlistManager.class, new CachedServiceFetcher<NetworkWatchlistManager>() { // from class: android.app.SystemServiceRegistry.106
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public NetworkWatchlistManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new NetworkWatchlistManager(contextImpl, INetworkWatchlistManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.NETWORK_WATCHLIST_SERVICE)));
            }
        });
        registerService(Context.SYSTEM_HEALTH_SERVICE, SystemHealthManager.class, new CachedServiceFetcher<SystemHealthManager>() { // from class: android.app.SystemServiceRegistry.107
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SystemHealthManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SystemHealthManager(IBatteryStats.Stub.asInterface(ServiceManager.getServiceOrThrow("batterystats")), IPowerStatsService.Stub.asInterface(ServiceManager.getService(Context.POWER_STATS_SERVICE)), IHintManager.Stub.asInterface(ServiceManager.getService(Context.PERFORMANCE_HINT_SERVICE)));
            }
        });
        registerService(Context.CONTEXTHUB_SERVICE, ContextHubManager.class, new CachedServiceFetcher<ContextHubManager>() { // from class: android.app.SystemServiceRegistry.108
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public ContextHubManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IBinder service = ServiceManager.getService(Context.CONTEXTHUB_SERVICE);
                if (service == null) {
                    return null;
                }
                return new ContextHubManager(IContextHubService.Stub.asInterface(service), contextImpl.mMainThread.getHandler().getLooper());
            }
        });
        registerService(Context.INCIDENT_SERVICE, IncidentManager.class, new CachedServiceFetcher<IncidentManager>() { // from class: android.app.SystemServiceRegistry.109
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public IncidentManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new IncidentManager(contextImpl);
            }
        });
        registerService(Context.BUGREPORT_SERVICE, BugreportManager.class, new CachedServiceFetcher<BugreportManager>() { // from class: android.app.SystemServiceRegistry.110
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public BugreportManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new BugreportManager(contextImpl.getOuterContext(), IDumpstate.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.BUGREPORT_SERVICE)));
            }
        });
        registerService(Context.AUTOFILL_MANAGER_SERVICE, AutofillManager.class, new CachedServiceFetcher<AutofillManager>() { // from class: android.app.SystemServiceRegistry.111
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public AutofillManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new AutofillManager(contextImpl.getOuterContext(), IAutoFillManager.Stub.asInterface(ServiceManager.getService(Context.AUTOFILL_MANAGER_SERVICE)));
            }
        });
        registerService("credential", CredentialManager.class, new CachedServiceFetcher<CredentialManager>() { // from class: android.app.SystemServiceRegistry.112
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public CredentialManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                ICredentialManager asInterface = ICredentialManager.Stub.asInterface(ServiceManager.getService("credential"));
                if (asInterface != null) {
                    return new CredentialManager(contextImpl.getOuterContext(), asInterface);
                }
                return null;
            }
        });
        registerService(Context.MUSIC_RECOGNITION_SERVICE, MusicRecognitionManager.class, new CachedServiceFetcher<MusicRecognitionManager>() { // from class: android.app.SystemServiceRegistry.113
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public MusicRecognitionManager createService(ContextImpl contextImpl) {
                return new MusicRecognitionManager(IMusicRecognitionManager.Stub.asInterface(ServiceManager.getService(Context.MUSIC_RECOGNITION_SERVICE)));
            }
        });
        registerService(Context.CONTENT_CAPTURE_MANAGER_SERVICE, ContentCaptureManager.class, new CachedServiceFetcher<ContentCaptureManager>() { // from class: android.app.SystemServiceRegistry.114
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public ContentCaptureManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IContentCaptureManager asInterface;
                Context outerContext = contextImpl.getOuterContext();
                ContentCaptureOptions contentCaptureOptions = outerContext.getContentCaptureOptions();
                if (contentCaptureOptions == null) {
                    return null;
                }
                if ((contentCaptureOptions.lite || contentCaptureOptions.isWhitelisted(outerContext)) && (asInterface = IContentCaptureManager.Stub.asInterface(ServiceManager.getService(Context.CONTENT_CAPTURE_MANAGER_SERVICE))) != null) {
                    return new ContentCaptureManager(outerContext, asInterface, contentCaptureOptions);
                }
                return null;
            }
        });
        registerService(Context.TRANSLATION_MANAGER_SERVICE, TranslationManager.class, new CachedServiceFetcher<TranslationManager>() { // from class: android.app.SystemServiceRegistry.115
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TranslationManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                ITranslationManager asInterface = ITranslationManager.Stub.asInterface(ServiceManager.getService(Context.TRANSLATION_MANAGER_SERVICE));
                if (asInterface != null) {
                    return new TranslationManager(contextImpl.getOuterContext(), asInterface);
                }
                return null;
            }
        });
        registerService(Context.UI_TRANSLATION_SERVICE, UiTranslationManager.class, new CachedServiceFetcher<UiTranslationManager>() { // from class: android.app.SystemServiceRegistry.116
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public UiTranslationManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                ITranslationManager asInterface = ITranslationManager.Stub.asInterface(ServiceManager.getService(Context.TRANSLATION_MANAGER_SERVICE));
                if (asInterface != null) {
                    return new UiTranslationManager(contextImpl.getOuterContext(), asInterface);
                }
                return null;
            }
        });
        registerService(Context.SEARCH_UI_SERVICE, SearchUiManager.class, new CachedServiceFetcher<SearchUiManager>() { // from class: android.app.SystemServiceRegistry.117
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SearchUiManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                if (ServiceManager.getService(Context.SEARCH_UI_SERVICE) == null) {
                    return null;
                }
                return new SearchUiManager(contextImpl);
            }
        });
        registerService(Context.SMARTSPACE_SERVICE, SmartspaceManager.class, new CachedServiceFetcher<SmartspaceManager>() { // from class: android.app.SystemServiceRegistry.118
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SmartspaceManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                if (ServiceManager.getService(Context.SMARTSPACE_SERVICE) == null) {
                    return null;
                }
                return new SmartspaceManager(contextImpl);
            }
        });
        registerService(Context.CONTEXTUAL_SEARCH_SERVICE, ContextualSearchManager.class, new CachedServiceFetcher<ContextualSearchManager>() { // from class: android.app.SystemServiceRegistry.119
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public ContextualSearchManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                if (ServiceManager.getService(Context.CONTEXTUAL_SEARCH_SERVICE) == null) {
                    return null;
                }
                return new ContextualSearchManager();
            }
        });
        registerService(Context.APP_PREDICTION_SERVICE, AppPredictionManager.class, new CachedServiceFetcher<AppPredictionManager>() { // from class: android.app.SystemServiceRegistry.120
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public AppPredictionManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                if (ServiceManager.getService(Context.APP_PREDICTION_SERVICE) == null) {
                    return null;
                }
                return new AppPredictionManager(contextImpl);
            }
        });
        registerService(Context.CONTENT_SUGGESTIONS_SERVICE, ContentSuggestionsManager.class, new CachedServiceFetcher<ContentSuggestionsManager>() { // from class: android.app.SystemServiceRegistry.121
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public ContentSuggestionsManager createService(ContextImpl contextImpl) {
                return new ContentSuggestionsManager(contextImpl.getUserId(), IContentSuggestionsManager.Stub.asInterface(ServiceManager.getService(Context.CONTENT_SUGGESTIONS_SERVICE)));
            }
        });
        registerService(Context.WALLPAPER_EFFECTS_GENERATION_SERVICE, WallpaperEffectsGenerationManager.class, new CachedServiceFetcher<WallpaperEffectsGenerationManager>() { // from class: android.app.SystemServiceRegistry.122
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public WallpaperEffectsGenerationManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IBinder service = ServiceManager.getService(Context.WALLPAPER_EFFECTS_GENERATION_SERVICE);
                if (service == null) {
                    return null;
                }
                return new WallpaperEffectsGenerationManager(IWallpaperEffectsGenerationManager.Stub.asInterface(service));
            }
        });
        registerService(Context.VR_SERVICE, VrManager.class, new CachedServiceFetcher<VrManager>() { // from class: android.app.SystemServiceRegistry.123
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public VrManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new VrManager(IVrManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.VR_SERVICE)));
            }
        });
        registerService(Context.CROSS_PROFILE_APPS_SERVICE, CrossProfileApps.class, new CachedServiceFetcher<CrossProfileApps>() { // from class: android.app.SystemServiceRegistry.124
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public CrossProfileApps createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new CrossProfileApps(contextImpl.getOuterContext(), ICrossProfileApps.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.CROSS_PROFILE_APPS_SERVICE)));
            }
        });
        registerService("slice", SliceManager.class, new CachedServiceFetcher<SliceManager>() { // from class: android.app.SystemServiceRegistry.125
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SliceManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SliceManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_MCF_SUPPORT_CONTINUITY") > 0) {
            registerService(Context.SEM_CONTINUITY_SERVICE, SemContinuityManager.class, new CachedServiceFetcher<SemContinuityManager>() { // from class: android.app.SystemServiceRegistry.126
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                public SemContinuityManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                    return new SemContinuityManager(contextImpl.getOuterContext(), ISemContinuityManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.SEM_CONTINUITY_SERVICE)), contextImpl.getUserId());
                }
            });
        }
        registerService(Context.SEM_CONTEXT_ENGINE_SERVICE, SemContextEngineManager.class, new CachedServiceFetcher<SemContextEngineManager>() { // from class: android.app.SystemServiceRegistry.127
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemContextEngineManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SemContextEngineManager(contextImpl.getOuterContext(), ISemContextEngineManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.SEM_CONTEXT_ENGINE_SERVICE)), contextImpl.getUserId());
            }
        });
        try {
            registerService(Context.SEM_HWRS_SERVICE, SemHwrsManager.class, new CachedServiceFetcher<SemHwrsManager>() { // from class: android.app.SystemServiceRegistry.128
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                public SemHwrsManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                    return new SemHwrsManager(contextImpl.getOuterContext(), ISemHwrsManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.SEM_HWRS_SERVICE)), contextImpl.getUserId());
                }
            });
        } catch (Exception e) {
            Slog.e(TAG, "SemHwrsManager registerService failed", e);
        }
        registerService(Context.SEM_MOTION_RECOGNITION_SERVICE, SemMotionRecognitionManager.class, new CachedServiceFetcher<SemMotionRecognitionManager>() { // from class: android.app.SystemServiceRegistry.129
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemMotionRecognitionManager createService(ContextImpl contextImpl) {
                return new SemMotionRecognitionManager(contextImpl.mMainThread.getHandler().getLooper());
            }
        });
        registerService("scontext", SContextManager.class, new CachedServiceFetcher<SContextManager>() { // from class: android.app.SystemServiceRegistry.130
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SContextManager createService(ContextImpl contextImpl) {
                return new SContextManager(contextImpl, contextImpl.mMainThread.getHandler().getLooper());
            }
        });
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_CHIP_VENDOR");
        if (string.length() > 0 && Integer.parseInt(string) > 0) {
            registerService(Context.SEM_FM_RADIO_SERVICE, SemFmPlayer.class, new CachedServiceFetcher<SemFmPlayer>() { // from class: android.app.SystemServiceRegistry.131
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                public SemFmPlayer createService(ContextImpl contextImpl) {
                    return new SemFmPlayer(contextImpl);
                }
            });
        }
        registerService(Context.SEM_MULTI_CONTROL_SERVICE, SemMultiControlManager.class, new CachedServiceFetcher<SemMultiControlManager>() { // from class: android.app.SystemServiceRegistry.132
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemMultiControlManager createService(ContextImpl contextImpl) {
                IMultiControlManager asInterface = IMultiControlManager.Stub.asInterface(ServiceManager.getService(Context.SEM_MULTI_CONTROL_SERVICE));
                if (asInterface == null) {
                    Log.e(SystemServiceRegistry.TAG, "SemMultiControlManager is not supported");
                    return null;
                }
                return new SemMultiControlManager(contextImpl.getOuterContext(), asInterface);
            }
        });
        registerService(Context.SEM_REMOTE_APP_MODE_SERVICE, SemRemoteAppModeManager.class, new CachedServiceFetcher<SemRemoteAppModeManager>() { // from class: android.app.SystemServiceRegistry.133
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemRemoteAppModeManager createService(ContextImpl contextImpl) {
                IRemoteAppMode asInterface = IRemoteAppMode.Stub.asInterface(ServiceManager.getService(Context.SEM_REMOTE_APP_MODE_SERVICE));
                if (asInterface == null) {
                    Log.e(SystemServiceRegistry.TAG, "SemRemoteAppModeManager is not supported");
                    return null;
                }
                return new SemRemoteAppModeManager(contextImpl.getOuterContext(), asInterface);
            }
        });
        if (SemDynamicFeature.isSuitable()) {
            registerService(SemDynamicFeature.SERVICE_NAME, DynamicFeatureManager.class, new CachedServiceFetcher<DynamicFeatureManager>() { // from class: android.app.SystemServiceRegistry.134
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                public DynamicFeatureManager createService(ContextImpl contextImpl) {
                    IDynamicFeatureManager asInterface = IDynamicFeatureManager.Stub.asInterface(ServiceManager.getService(SemDynamicFeature.SERVICE_NAME));
                    if (asInterface == null) {
                        Log.e(SystemServiceRegistry.TAG, "IDynamicFeatureManager is not supported");
                        return null;
                    }
                    return new DynamicFeatureManager(asInterface);
                }
            });
        }
        registerService("time_detector", TimeDetector.class, new CachedServiceFetcher<TimeDetector>() { // from class: android.app.SystemServiceRegistry.135
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TimeDetector createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new TimeDetectorImpl();
            }
        });
        registerService("time_zone_detector", TimeZoneDetector.class, new CachedServiceFetcher<TimeZoneDetector>() { // from class: android.app.SystemServiceRegistry.136
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TimeZoneDetector createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new TimeZoneDetectorImpl();
            }
        });
        registerService(Context.TIME_MANAGER_SERVICE, TimeManager.class, new CachedServiceFetcher<TimeManager>() { // from class: android.app.SystemServiceRegistry.137
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TimeManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new TimeManager();
            }
        });
        registerService("permission", PermissionManager.class, new CachedServiceFetcher<PermissionManager>() { // from class: android.app.SystemServiceRegistry.138
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public PermissionManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new PermissionManager(contextImpl.getOuterContext());
            }
        });
        registerService(Context.LEGACY_PERMISSION_SERVICE, LegacyPermissionManager.class, new CachedServiceFetcher<LegacyPermissionManager>() { // from class: android.app.SystemServiceRegistry.139
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public LegacyPermissionManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new LegacyPermissionManager();
            }
        });
        registerService(Context.PERMISSION_CONTROLLER_SERVICE, PermissionControllerManager.class, new CachedServiceFetcher<PermissionControllerManager>() { // from class: android.app.SystemServiceRegistry.140
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public PermissionControllerManager createService(ContextImpl contextImpl) {
                return new PermissionControllerManager(contextImpl.getOuterContext(), contextImpl.getMainThreadHandler());
            }
        });
        registerService(Context.PERMISSION_CHECKER_SERVICE, PermissionCheckerManager.class, new CachedServiceFetcher<PermissionCheckerManager>() { // from class: android.app.SystemServiceRegistry.141
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public PermissionCheckerManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new PermissionCheckerManager(contextImpl.getOuterContext());
            }
        });
        registerService(Context.PERMISSION_ENFORCER_SERVICE, PermissionEnforcer.class, new CachedServiceFetcher<PermissionEnforcer>() { // from class: android.app.SystemServiceRegistry.142
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public PermissionEnforcer createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new PermissionEnforcer(contextImpl.getOuterContext());
            }
        });
        registerService(Context.DYNAMIC_SYSTEM_SERVICE, DynamicSystemManager.class, new CachedServiceFetcher<DynamicSystemManager>() { // from class: android.app.SystemServiceRegistry.143
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public DynamicSystemManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new DynamicSystemManager(IDynamicSystemService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.DYNAMIC_SYSTEM_SERVICE)));
            }
        });
        registerService("batterystats", BatteryStatsManager.class, new CachedServiceFetcher<BatteryStatsManager>() { // from class: android.app.SystemServiceRegistry.144
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public BatteryStatsManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new BatteryStatsManager(IBatteryStats.Stub.asInterface(ServiceManager.getServiceOrThrow("batterystats")));
            }
        });
        registerService(Context.DATA_LOADER_MANAGER_SERVICE, DataLoaderManager.class, new CachedServiceFetcher<DataLoaderManager>() { // from class: android.app.SystemServiceRegistry.145
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public DataLoaderManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new DataLoaderManager(IDataLoaderManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.DATA_LOADER_MANAGER_SERVICE)));
            }
        });
        registerService(Context.LIGHTS_SERVICE, LightsManager.class, new CachedServiceFetcher<LightsManager>() { // from class: android.app.SystemServiceRegistry.146
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public LightsManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SystemLightsManager(contextImpl);
            }
        });
        registerService("locale", LocaleManager.class, new CachedServiceFetcher<LocaleManager>() { // from class: android.app.SystemServiceRegistry.147
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public LocaleManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new LocaleManager(contextImpl, ILocaleManager.Stub.asInterface(ServiceManager.getServiceOrThrow("locale")));
            }
        });
        registerService(Context.INCREMENTAL_SERVICE, IncrementalManager.class, new CachedServiceFetcher<IncrementalManager>() { // from class: android.app.SystemServiceRegistry.148
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public IncrementalManager createService(ContextImpl contextImpl) {
                IBinder service = ServiceManager.getService(Context.INCREMENTAL_SERVICE);
                if (service == null) {
                    return null;
                }
                return new IncrementalManager(IIncrementalService.Stub.asInterface(service));
            }
        });
        registerService(Context.FILE_INTEGRITY_SERVICE, FileIntegrityManager.class, new CachedServiceFetcher<FileIntegrityManager>() { // from class: android.app.SystemServiceRegistry.149
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public FileIntegrityManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new FileIntegrityManager(contextImpl.getOuterContext(), IFileIntegrityService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.FILE_INTEGRITY_SERVICE)));
            }
        });
        registerService(Context.ATTESTATION_VERIFICATION_SERVICE, AttestationVerificationManager.class, new CachedServiceFetcher<AttestationVerificationManager>() { // from class: android.app.SystemServiceRegistry.150
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public AttestationVerificationManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new AttestationVerificationManager(contextImpl.getOuterContext(), IAttestationVerificationManagerService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.ATTESTATION_VERIFICATION_SERVICE)));
            }
        });
        registerService(Context.APP_HIBERNATION_SERVICE, AppHibernationManager.class, new CachedServiceFetcher<AppHibernationManager>() { // from class: android.app.SystemServiceRegistry.151
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public AppHibernationManager createService(ContextImpl contextImpl) {
                if (ServiceManager.getService(Context.APP_HIBERNATION_SERVICE) == null) {
                    return null;
                }
                return new AppHibernationManager(contextImpl);
            }
        });
        registerService("dream", DreamManager.class, new CachedServiceFetcher<DreamManager>() { // from class: android.app.SystemServiceRegistry.152
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public DreamManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new DreamManager(contextImpl);
            }
        });
        registerService(Context.DEVICE_STATE_SERVICE, DeviceStateManager.class, new CachedServiceFetcher<DeviceStateManager>() { // from class: android.app.SystemServiceRegistry.153
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public DeviceStateManager createService(ContextImpl contextImpl) {
                return new DeviceStateManager();
            }
        });
        registerService(Context.COCKTAIL_BAR_SERVICE, CocktailBarManager.class, new CachedServiceFetcher<CocktailBarManager>() { // from class: android.app.SystemServiceRegistry.154
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public CocktailBarManager createService(ContextImpl contextImpl) {
                return new CocktailBarManager(contextImpl, ICocktailBarService.Stub.asInterface(ServiceManager.getService(Context.COCKTAIL_BAR_SERVICE)));
            }
        });
        registerService(Context.MEDIA_METRICS_SERVICE, MediaMetricsManager.class, new CachedServiceFetcher<MediaMetricsManager>() { // from class: android.app.SystemServiceRegistry.155
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public MediaMetricsManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new MediaMetricsManager(IMediaMetricsManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.MEDIA_METRICS_SERVICE)), contextImpl.getUserId());
            }
        });
        registerService("game", GameManager.class, new CachedServiceFetcher<GameManager>() { // from class: android.app.SystemServiceRegistry.156
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public GameManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IBinder serviceOrThrow;
                boolean hasSystemFeature = contextImpl.getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH);
                if (android.server.Flags.removeGameManagerServiceFromWear() && hasSystemFeature) {
                    serviceOrThrow = ServiceManager.getService("game");
                } else {
                    serviceOrThrow = ServiceManager.getServiceOrThrow("game");
                }
                if (serviceOrThrow == null && Compatibility.isChangeEnabled(SystemServiceRegistry.NULL_GAME_MANAGER_IN_WEAR)) {
                    return null;
                }
                return new GameManager(contextImpl.getOuterContext(), IGameManagerService.Stub.asInterface(serviceOrThrow));
            }
        });
        registerService(Context.DOMAIN_VERIFICATION_SERVICE, DomainVerificationManager.class, new CachedServiceFetcher<DomainVerificationManager>() { // from class: android.app.SystemServiceRegistry.157
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public DomainVerificationManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new DomainVerificationManager(contextImpl, IDomainVerificationManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.DOMAIN_VERIFICATION_SERVICE)));
            }
        });
        registerService(Context.DISPLAY_HASH_SERVICE, DisplayHashManager.class, new CachedServiceFetcher<DisplayHashManager>() { // from class: android.app.SystemServiceRegistry.158
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public DisplayHashManager createService(ContextImpl contextImpl) {
                return new DisplayHashManager();
            }
        });
        try {
            z = Resources.getSystem().getBoolean(R.bool.config_enableExynosDisplaySolutionService);
        } catch (Exception e2) {
            Slog.e(TAG, "Not starting ExynosDisplaySolutionService", e2);
            z = false;
        }
        if (z) {
            registerService(Context.EXYNOS_DISPLAY_SOLUTION_SERVICE, ExynosDisplaySolutionManager.class, new CachedServiceFetcher<ExynosDisplaySolutionManager>() { // from class: android.app.SystemServiceRegistry.159
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                public ExynosDisplaySolutionManager createService(ContextImpl contextImpl) {
                    IExynosDisplaySolutionManager asInterface = IExynosDisplaySolutionManager.Stub.asInterface(ServiceManager.getService(Context.EXYNOS_DISPLAY_SOLUTION_SERVICE));
                    if (asInterface == null) {
                        Log.e(SystemServiceRegistry.TAG, "Failed to get ExynosDisplaySolution Manager Service.");
                        return null;
                    }
                    return new ExynosDisplaySolutionManager(asInterface);
                }
            });
        }
        registerService(Context.AMBIENT_CONTEXT_SERVICE, AmbientContextManager.class, new CachedServiceFetcher<AmbientContextManager>() { // from class: android.app.SystemServiceRegistry.160
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public AmbientContextManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new AmbientContextManager(contextImpl.getOuterContext(), IAmbientContextManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.AMBIENT_CONTEXT_SERVICE)));
            }
        });
        registerService(Context.WEARABLE_SENSING_SERVICE, WearableSensingManager.class, new CachedServiceFetcher<WearableSensingManager>() { // from class: android.app.SystemServiceRegistry.161
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public WearableSensingManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IBinder service = ServiceManager.getService(Context.WEARABLE_SENSING_SERVICE);
                if (service != null) {
                    return new WearableSensingManager(contextImpl.getOuterContext(), IWearableSensingManager.Stub.asInterface(service));
                }
                if (contextImpl.getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH) && android.server.Flags.removeWearableSensingServiceFromWear()) {
                    return null;
                }
                throw new ServiceManager.ServiceNotFoundException(Context.WEARABLE_SENSING_SERVICE);
            }
        });
        registerService(Context.GRAMMATICAL_INFLECTION_SERVICE, GrammaticalInflectionManager.class, new CachedServiceFetcher<GrammaticalInflectionManager>() { // from class: android.app.SystemServiceRegistry.162
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public GrammaticalInflectionManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new GrammaticalInflectionManager(contextImpl, IGrammaticalInflectionManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.GRAMMATICAL_INFLECTION_SERVICE)));
            }
        });
        registerService(Context.SEM_MDNIE_SERVICE, SemMdnieManager.class, new CachedServiceFetcher<SemMdnieManager>() { // from class: android.app.SystemServiceRegistry.163
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemMdnieManager createService(ContextImpl contextImpl) {
                return new SemMdnieManager(ISemMdnieManager.Stub.asInterface(ServiceManager.getService(Context.SEM_MDNIE_SERVICE)));
            }
        });
        registerService(Context.SEM_DISPLAY_SOLUTION_SERVICE, SemDisplaySolutionManager.class, new CachedServiceFetcher<SemDisplaySolutionManager>() { // from class: android.app.SystemServiceRegistry.164
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemDisplaySolutionManager createService(ContextImpl contextImpl) {
                return new SemDisplaySolutionManager(ISemDisplaySolutionManager.Stub.asInterface(ServiceManager.getService(Context.SEM_DISPLAY_SOLUTION_SERVICE)));
            }
        });
        registerService("persona", SemPersonaManager.class, new CachedServiceFetcher<SemPersonaManager>() { // from class: android.app.SystemServiceRegistry.165
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemPersonaManager createService(ContextImpl contextImpl) {
                return new SemPersonaManager(contextImpl, ISemPersonaManager.Stub.asInterface(ServiceManager.getService("persona")));
            }
        });
        registerService("rcp", SemRemoteContentManager.class, new CachedServiceFetcher<SemRemoteContentManager>() { // from class: android.app.SystemServiceRegistry.166
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemRemoteContentManager createService(ContextImpl contextImpl) {
                return new SemRemoteContentManager(ISemRemoteContentManager.Stub.asInterface(ServiceManager.getService("rcp")));
            }
        });
        registerService(Context.ISRB_MANAGER_SERVICE, IsrbManager.class, new CachedServiceFetcher<IsrbManager>() { // from class: android.app.SystemServiceRegistry.167
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public IsrbManager createService(ContextImpl contextImpl) {
                return new IsrbManager(contextImpl);
            }
        });
        registerService(Context.SHARED_CONNECTIVITY_SERVICE, SharedConnectivityManager.class, new CachedServiceFetcher<SharedConnectivityManager>() { // from class: android.app.SystemServiceRegistry.168
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SharedConnectivityManager createService(ContextImpl contextImpl) {
                return SharedConnectivityManager.create(contextImpl);
            }
        });
        registerService(KMTDManager.SERVICE_LABEL, KMTDManager.class, new CachedServiceFetcher<KMTDManager>() { // from class: android.app.SystemServiceRegistry.169
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public KMTDManager createService(ContextImpl contextImpl) {
                IMTDService asInterface = IMTDService.Stub.asInterface(ServiceManager.getService(KMTDManager.SERVICE_LABEL));
                if (asInterface == null) {
                    Log.e(SystemServiceRegistry.TAG, "Failed to get MTDService");
                    return null;
                }
                return new KMTDManager(asInterface, contextImpl.getOuterContext());
            }
        });
        registerService(Context.KEYSTORE_SERVICE, KeyStoreManager.class, new StaticServiceFetcher<KeyStoreManager>() { // from class: android.app.SystemServiceRegistry.170
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public KeyStoreManager createService() throws ServiceManager.ServiceNotFoundException {
                if (!com.android.internal.hidden_from_bootclasspath.android.security.Flags.keystoreGrantApi()) {
                    throw new ServiceManager.ServiceNotFoundException("KeyStoreManager is not supported");
                }
                return KeyStoreManager.getInstance();
            }
        });
        registerService(Context.CONTACT_KEYS_SERVICE, E2eeContactKeysManager.class, new CachedServiceFetcher<E2eeContactKeysManager>() { // from class: android.app.SystemServiceRegistry.171
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public E2eeContactKeysManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                if (!com.android.internal.hidden_from_bootclasspath.android.provider.Flags.userKeys()) {
                    throw new ServiceManager.ServiceNotFoundException("ContactKeysManager is not supported");
                }
                return new E2eeContactKeysManager(contextImpl);
            }
        });
        registerService(Context.SUPERVISION_SERVICE, SupervisionManager.class, new CachedServiceFetcher<SupervisionManager>() { // from class: android.app.SystemServiceRegistry.172
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SupervisionManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                if (!com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.Flags.supervisionApi()) {
                    throw new ServiceManager.ServiceNotFoundException("SupervisionManager is not supported");
                }
                return new SupervisionManager(contextImpl, ISupervisionManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.SUPERVISION_SERVICE)));
            }
        });
        if (com.android.internal.hidden_from_bootclasspath.android.security.Flags.aapmApi()) {
            registerService(Context.ADVANCED_PROTECTION_SERVICE, AdvancedProtectionManager.class, new CachedServiceFetcher<AdvancedProtectionManager>() { // from class: android.app.SystemServiceRegistry.173
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                public AdvancedProtectionManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                    IAdvancedProtectionService asInterface = IAdvancedProtectionService.Stub.asInterface(ServiceManager.getService(Context.ADVANCED_PROTECTION_SERVICE));
                    if (asInterface == null) {
                        return null;
                    }
                    return new AdvancedProtectionManager(asInterface);
                }
            });
        }
        if (Flags.bicClient()) {
            registerService(Context.BACKGROUND_INSTALL_CONTROL_SERVICE, BackgroundInstallControlManager.class, new CachedServiceFetcher<BackgroundInstallControlManager>() { // from class: android.app.SystemServiceRegistry.174
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                public BackgroundInstallControlManager createService(ContextImpl contextImpl) {
                    return new BackgroundInstallControlManager(contextImpl);
                }
            });
        }
        registerService(Context.MEDIA_QUALITY_SERVICE, MediaQualityManager.class, new CachedServiceFetcher<MediaQualityManager>() { // from class: android.app.SystemServiceRegistry.175
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public MediaQualityManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new MediaQualityManager(contextImpl, IMediaQualityManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.MEDIA_QUALITY_SERVICE)));
            }
        });
        registerService(Context.INTRUSION_DETECTION_SERVICE, IntrusionDetectionManager.class, new CachedServiceFetcher<IntrusionDetectionManager>() { // from class: android.app.SystemServiceRegistry.176
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public IntrusionDetectionManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                if (!com.android.internal.hidden_from_bootclasspath.android.security.Flags.aflApi()) {
                    throw new ServiceManager.ServiceNotFoundException("Intrusion Detection is not supported");
                }
                return new IntrusionDetectionManager(IIntrusionDetectionService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.INTRUSION_DETECTION_SERVICE)));
            }
        });
        registerService(Context.SEM_MDCONTEXT_SERVICE, SemMdContextManager.class, new CachedServiceFetcher<SemMdContextManager>() { // from class: android.app.SystemServiceRegistry.177
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemMdContextManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                Log.i(SystemServiceRegistry.TAG, "create SemMdContextManager service");
                IBinder service = ServiceManager.getService(Context.SEM_MDCONTEXT_SERVICE);
                if (service == null) {
                    Log.e(SystemServiceRegistry.TAG, "MOCCA is not supported on this device");
                    return null;
                }
                return new SemMdContextManager(IMoccaService.Stub.asInterface(service));
            }
        });
        registerService(Context.SEM_INPUT_DEVICE_SERVICE, SemInputDeviceManager.class, new CachedServiceFetcher<SemInputDeviceManager>() { // from class: android.app.SystemServiceRegistry.178
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemInputDeviceManager createService(ContextImpl contextImpl) {
                return new SemInputDeviceManager(ISemInputDeviceManager.Stub.asInterface(ServiceManager.getService(Context.SEM_INPUT_DEVICE_SERVICE)));
            }
        });
        registerService(Context.SEM_WIFI_SERVICE, SemWifiManager.class, new CachedServiceFetcher<SemWifiManager>() { // from class: android.app.SystemServiceRegistry.179
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemWifiManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SemWifiManager(contextImpl.getOuterContext(), ISemWifiManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.SEM_WIFI_SERVICE)), contextImpl.mMainThread.getHandler().getLooper());
            }
        });
        registerService(Context.SEM_WIFI_P2P_SERVICE, SemWifiP2pManager.class, new CachedServiceFetcher<SemWifiP2pManager>() { // from class: android.app.SystemServiceRegistry.180
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemWifiP2pManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SemWifiP2pManager(contextImpl.getOuterContext(), ISemWifiP2pManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.SEM_WIFI_P2P_SERVICE)));
            }
        });
        registerService(Context.SEM_WIFI_AWARE_SERVICE, SemWifiAwareManager.class, new CachedServiceFetcher<SemWifiAwareManager>() { // from class: android.app.SystemServiceRegistry.181
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemWifiAwareManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SemWifiAwareManager(contextImpl.getOuterContext(), ISemWifiAwareManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.SEM_WIFI_AWARE_SERVICE)));
            }
        });
        registerService(Context.STANDARD_PLUS_SERVICE, StandardPlusManager.class, new CachedServiceFetcher<StandardPlusManager>() { // from class: android.app.SystemServiceRegistry.182
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public StandardPlusManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new StandardPlusManager(contextImpl.getOuterContext(), IStandardPlusManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.STANDARD_PLUS_SERVICE)));
            }
        });
        Log.e(TAG, "SemDisplayQualityFeature.ENABLED:" + SemDisplayQualityFeature.ENABLED + ",PLATFORM:" + SemDisplayQualityFeature.PLATFORM);
        if (SemDisplayQualityFeature.ENABLED) {
            registerService(Context.SEM_DISPLAY_QUALITY_SERVICE, SemDisplayQualityManager.class, new CachedServiceFetcher<SemDisplayQualityManager>() { // from class: android.app.SystemServiceRegistry.183
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                public SemDisplayQualityManager createService(ContextImpl contextImpl) {
                    return new SemDisplayQualityManager(ISemDisplayQualityManager.Stub.asInterface(ServiceManager.getService(Context.SEM_DISPLAY_QUALITY_SERVICE)));
                }
            });
        }
        registerService(Context.SEM_VIDEO_TRANSCODING_SERVICE, SemVideoTranscodingService.class, new CachedServiceFetcher<SemVideoTranscodingService>() { // from class: android.app.SystemServiceRegistry.184
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemVideoTranscodingService createService(ContextImpl contextImpl) {
                return new SemVideoTranscodingService();
            }
        });
        if (UnionUtils.FEATURE_ENABLED) {
            registerService(Context.SEP_UNION_SERVICE, SemUnionManager.class, new CachedServiceFetcher<SemUnionManager>() { // from class: android.app.SystemServiceRegistry.186
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                public SemUnionManager createService(ContextImpl contextImpl) {
                    return new SemUnionManager(contextImpl, IUnionManager.Stub.asInterface(ServiceManager.getService(Context.SEP_UNION_SERVICE)));
                }
            });
        }
        registerService(Context.CODEC_SOLUTION_SERVICE, SemCodecSolutionService.class, new CachedServiceFetcher<SemCodecSolutionService>() { // from class: android.app.SystemServiceRegistry.187
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemCodecSolutionService createService(ContextImpl contextImpl) {
                return new SemCodecSolutionService();
            }
        });
        registerService(Context.EXTENDED_ETHERNET_SERVICE, ExtendedEthernetManager.class, new CachedServiceFetcher<ExtendedEthernetManager>() { // from class: android.app.SystemServiceRegistry.188
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public ExtendedEthernetManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new ExtendedEthernetManager(IExtendedEthernetManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.EXTENDED_ETHERNET_SERVICE)));
            }
        });
        registerService(Context.SEM_SPEN_GESTURE_SERVICE, SpenGestureManager.class, new CachedServiceFetcher<SpenGestureManager>() { // from class: android.app.SystemServiceRegistry.189
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SpenGestureManager createService(ContextImpl contextImpl) {
                return new SpenGestureManager(contextImpl);
            }
        });
        registerService(Context.SEM_SSDID_SERVICE, SemSsdidManager.class, new CachedServiceFetcher<SemSsdidManager>() { // from class: android.app.SystemServiceRegistry.190
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public SemSsdidManager createService(ContextImpl contextImpl) {
                return new SemSsdidManager(contextImpl, ISemSsdidManagerService.Stub.asInterface(ServiceManager.getService(Context.SEM_SSDID_SERVICE)));
            }
        });
        sInitializing = true;
        try {
            ConnectivityFrameworkInitializer.registerServiceWrappers();
            JobSchedulerFrameworkInitializer.registerServiceWrappers();
            BlobStoreManagerFrameworkInitializer.initialize();
            BluetoothFrameworkInitializer.registerServiceWrappers();
            NfcFrameworkInitializer.registerServiceWrappers();
            TelephonyFrameworkInitializer.registerServiceWrappers();
            AppSearchManagerFrameworkInitializer.initialize();
            HealthServicesInitializer.registerServiceWrappers();
            WifiFrameworkInitializer.registerServiceWrappers();
            StatsFrameworkInitializer.registerServiceWrappers();
            RollbackManagerFrameworkInitializer.initialize();
            MediaFrameworkPlatformInitializer.registerServiceWrappers();
            MediaFrameworkInitializer.registerServiceWrappers();
            RoleFrameworkInitializer.registerServiceWrappers();
            SchedulingFrameworkInitializer.registerServiceWrappers();
            SdkSandboxManagerFrameworkInitializer.registerServiceWrappers();
            AdServicesFrameworkInitializer.registerServiceWrappers();
            UwbFrameworkInitializer.registerServiceWrappers();
            SafetyCenterFrameworkInitializer.registerServiceWrappers();
            ConnectivityFrameworkInitializerTiramisu.registerServiceWrappers();
            NearbyFrameworkInitializer.registerServiceWrappers();
            OnDevicePersonalizationFrameworkInitializer.registerServiceWrappers();
            OnDeviceIntelligenceFrameworkInitializer.registerServiceWrappers();
            DeviceLockFrameworkInitializer.registerServiceWrappers();
            VirtualizationFrameworkInitializer.registerServiceWrappers();
            ConnectivityFrameworkInitializerBaklava.registerServiceWrappers();
            if (com.android.internal.hidden_from_bootclasspath.android.provider.flags.Flags.newStoragePublicApi()) {
                ConfigInfrastructureFrameworkInitializer.registerServiceWrappers();
            }
            if (com.android.server.telecom.flags.Flags.telecomMainlineBlockedNumbersManager()) {
                ProviderFrameworkInitializer.registerServiceWrappers();
            }
            if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.enhancedConfirmationModeApisEnabled()) {
                EnhancedConfirmationFrameworkInitializer.registerServiceWrappers();
            }
            if (android.server.Flags.telemetryApisService()) {
                ProfilingFrameworkInitializer.registerServiceWrappers();
            }
            if (android.webkit.Flags.updateServiceIpcWrapper()) {
                WebViewBootstrapFrameworkInitializer.registerServiceWrappers();
            }
            LifeGuardManagerFrameworkInitializer.initialize();
            ShellFrameworkInitializer.registerServiceWrappers();
            RangingFrameworkInitializer.registerServiceWrappers();
        } finally {
            sInitializing = false;
        }
    }

    private SystemServiceRegistry() {
    }

    /* renamed from: android.app.SystemServiceRegistry$22, reason: invalid class name */
    class AnonymousClass22 extends CachedServiceFetcher<TetheringManager> {
        AnonymousClass22() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
        public TetheringManager createService(ContextImpl contextImpl) {
            return new TetheringManager(contextImpl, new Supplier() { // from class: android.app.SystemServiceRegistry$22$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    IBinder service;
                    service = ServiceManager.getService(Context.TETHERING_SERVICE);
                    return service;
                }
            });
        }
    }

    private static void ensureInitializing(String str) {
        Preconditions.checkState(sInitializing, "Internal error: %s can only be called during class initialization.", str);
    }

    public static Object[] createServiceCache() {
        return new Object[sServiceCacheSize];
    }

    private static ServiceFetcher<?> getSystemServiceFetcher(String str) {
        if (str == null) {
            return null;
        }
        ServiceFetcher<?> serviceFetcher = SYSTEM_SERVICE_FETCHERS.get(str);
        if (serviceFetcher != null) {
            return serviceFetcher;
        }
        if (sEnableServiceNotFoundWtf) {
            Slog.wtf(TAG, "Unknown manager requested: " + str);
        }
        return null;
    }

    private static boolean hasSystemFeatureOpportunistic(ContextImpl contextImpl, String str) {
        PackageManager packageManager = contextImpl.getPackageManager();
        if (packageManager == null) {
            return true;
        }
        return packageManager.hasSystemFeature(str);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00c5, code lost:
    
        if (hasSystemFeatureOpportunistic(r3, android.content.pm.PackageManager.FEATURE_WATCH) != false) goto L68;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object getSystemService(android.app.ContextImpl r3, java.lang.String r4) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.SystemServiceRegistry.getSystemService(android.app.ContextImpl, java.lang.String):java.lang.Object");
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static Object getSystemServiceWithNoContext(String str) {
        ServiceFetcher<?> systemServiceFetcher = getSystemServiceFetcher(str);
        if (systemServiceFetcher == null) {
            return null;
        }
        if (!systemServiceFetcher.supportsFetchWithoutContext()) {
            throw new IllegalArgumentException("Manager cannot be fetched without a context: " + str);
        }
        return systemServiceFetcher.getService(null);
    }

    public static String getSystemServiceName(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        String str = SYSTEM_SERVICE_NAMES.get(cls);
        if (sEnableServiceNotFoundWtf && str == null) {
            Slog.wtf(TAG, "Unknown manager requested: " + cls.getCanonicalName());
        }
        return str;
    }

    private static <T> void registerService(String str, Class<T> cls, ServiceFetcher<T> serviceFetcher) {
        SYSTEM_SERVICE_NAMES.put(cls, str);
        SYSTEM_SERVICE_FETCHERS.put(str, serviceFetcher);
        SYSTEM_SERVICE_CLASS_NAMES.put(str, cls.getSimpleName());
    }

    public static String getSystemServiceClassName(String str) {
        return SYSTEM_SERVICE_CLASS_NAMES.get(str);
    }

    @SystemApi
    public static <TServiceClass> void registerStaticService(final String str, Class<TServiceClass> cls, final StaticServiceProducerWithBinder<TServiceClass> staticServiceProducerWithBinder) {
        ensureInitializing("registerStaticService");
        Preconditions.checkStringNotEmpty(str);
        Objects.requireNonNull(cls);
        Objects.requireNonNull(staticServiceProducerWithBinder);
        registerService(str, cls, new StaticServiceFetcher<TServiceClass>() { // from class: android.app.SystemServiceRegistry.191
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public TServiceClass createService() throws ServiceManager.ServiceNotFoundException {
                return (TServiceClass) StaticServiceProducerWithBinder.this.createService(ServiceManager.getServiceOrThrow(str));
            }
        });
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static <TServiceClass> void registerForeverStaticService(final String str, Class<TServiceClass> cls, final StaticServiceProducerWithBinder<TServiceClass> staticServiceProducerWithBinder) {
        ensureInitializing("registerStaticService");
        Preconditions.checkStringNotEmpty(str);
        Objects.requireNonNull(cls);
        Objects.requireNonNull(staticServiceProducerWithBinder);
        registerService(str, cls, new StaticServiceFetcher<TServiceClass>() { // from class: android.app.SystemServiceRegistry.192
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher, android.app.SystemServiceRegistry.ServiceFetcher
            public boolean supportsFetchWithoutContext() {
                return true;
            }

            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public TServiceClass createService() throws ServiceManager.ServiceNotFoundException {
                return (TServiceClass) StaticServiceProducerWithBinder.this.createService(ServiceManager.getServiceOrThrow(str));
            }
        });
    }

    @SystemApi
    public static <TServiceClass> void registerStaticService(String str, Class<TServiceClass> cls, final StaticServiceProducerWithoutBinder<TServiceClass> staticServiceProducerWithoutBinder) {
        ensureInitializing("registerStaticService");
        Preconditions.checkStringNotEmpty(str);
        Objects.requireNonNull(cls);
        Objects.requireNonNull(staticServiceProducerWithoutBinder);
        registerService(str, cls, new StaticServiceFetcher<TServiceClass>() { // from class: android.app.SystemServiceRegistry.193
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public TServiceClass createService() {
                return (TServiceClass) StaticServiceProducerWithoutBinder.this.createService();
            }
        });
    }

    @SystemApi
    public static <TServiceClass> void registerContextAwareService(final String str, Class<TServiceClass> cls, final ContextAwareServiceProducerWithBinder<TServiceClass> contextAwareServiceProducerWithBinder) {
        ensureInitializing("registerContextAwareService");
        Preconditions.checkStringNotEmpty(str);
        Objects.requireNonNull(cls);
        Objects.requireNonNull(contextAwareServiceProducerWithBinder);
        registerService(str, cls, new CachedServiceFetcher<TServiceClass>() { // from class: android.app.SystemServiceRegistry.194
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TServiceClass createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return (TServiceClass) ContextAwareServiceProducerWithBinder.this.createService(contextImpl.getOuterContext(), ServiceManager.getServiceOrThrow(str));
            }
        });
    }

    @SystemApi
    public static <TServiceClass> void registerContextAwareService(String str, Class<TServiceClass> cls, final ContextAwareServiceProducerWithoutBinder<TServiceClass> contextAwareServiceProducerWithoutBinder) {
        ensureInitializing("registerContextAwareService");
        Preconditions.checkStringNotEmpty(str);
        Objects.requireNonNull(cls);
        Objects.requireNonNull(contextAwareServiceProducerWithoutBinder);
        registerService(str, cls, new CachedServiceFetcher<TServiceClass>() { // from class: android.app.SystemServiceRegistry.195
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TServiceClass createService(ContextImpl contextImpl) {
                return (TServiceClass) ContextAwareServiceProducerWithoutBinder.this.createService(contextImpl.getOuterContext());
            }
        });
    }

    static abstract class CachedServiceFetcher<T> implements ServiceFetcher<T> {
        private final int mCacheIndex;

        public abstract T createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException;

        @Override // android.app.SystemServiceRegistry.ServiceFetcher
        public final boolean supportsFetchWithoutContext() {
            return false;
        }

        CachedServiceFetcher() {
            int i = SystemServiceRegistry.sServiceCacheSize;
            SystemServiceRegistry.sServiceCacheSize = i + 1;
            this.mCacheIndex = i;
        }

        @Override // android.app.SystemServiceRegistry.ServiceFetcher
        public final T getService(ContextImpl contextImpl) {
            T t;
            boolean z;
            Object[] objArr = contextImpl.mServiceCache;
            int[] iArr = contextImpl.mServiceInitializationStateArray;
            boolean z2 = false;
            while (true) {
                synchronized (objArr) {
                    int i = this.mCacheIndex;
                    t = (T) objArr[i];
                    if (t != null) {
                        break;
                    }
                    int i2 = iArr[i];
                    if (i2 == 2 || i2 == 3) {
                        iArr[i] = 0;
                    }
                    if (iArr[i] == 0) {
                        iArr[i] = 1;
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        try {
                            try {
                                T createService = createService(contextImpl);
                                synchronized (objArr) {
                                    int i3 = this.mCacheIndex;
                                    objArr[i3] = createService;
                                    iArr[i3] = 2;
                                    objArr.notifyAll();
                                }
                                t = createService;
                                break;
                            } catch (Throwable th) {
                                synchronized (objArr) {
                                    int i4 = this.mCacheIndex;
                                    objArr[i4] = null;
                                    iArr[i4] = 3;
                                    objArr.notifyAll();
                                    throw th;
                                }
                            }
                        } catch (ServiceManager.ServiceNotFoundException e) {
                            SystemServiceRegistry.onServiceNotFound(e);
                            synchronized (objArr) {
                                objArr[this.mCacheIndex] = null;
                                iArr[this.mCacheIndex] = 3;
                                objArr.notifyAll();
                                t = null;
                            }
                        }
                    } else {
                        synchronized (objArr) {
                            while (iArr[this.mCacheIndex] < 2) {
                                try {
                                    z2 |= Thread.interrupted();
                                    objArr.wait();
                                } catch (InterruptedException unused) {
                                    Slog.w(SystemServiceRegistry.TAG, "getService() interrupted");
                                    z2 = true;
                                }
                            }
                        }
                    }
                }
            }
            if (z2) {
                Thread.currentThread().interrupt();
            }
            return t;
        }
    }

    static abstract class StaticServiceFetcher<T> implements ServiceFetcher<T> {
        private T mCachedInstance;

        public abstract T createService() throws ServiceManager.ServiceNotFoundException;

        @Override // android.app.SystemServiceRegistry.ServiceFetcher
        public boolean supportsFetchWithoutContext() {
            return false;
        }

        StaticServiceFetcher() {
        }

        @Override // android.app.SystemServiceRegistry.ServiceFetcher
        public final T getService(ContextImpl contextImpl) {
            T t;
            synchronized (this) {
                if (this.mCachedInstance == null) {
                    try {
                        this.mCachedInstance = createService();
                    } catch (ServiceManager.ServiceNotFoundException e) {
                        SystemServiceRegistry.onServiceNotFound(e);
                    }
                }
                t = this.mCachedInstance;
            }
            return t;
        }
    }

    public static void onServiceNotFound(ServiceManager.ServiceNotFoundException serviceNotFoundException) {
        if (Process.myUid() < 10000) {
            Log.wtf(TAG, serviceNotFoundException.getMessage(), serviceNotFoundException);
        } else {
            Log.w(TAG, serviceNotFoundException.getMessage());
        }
    }
}
