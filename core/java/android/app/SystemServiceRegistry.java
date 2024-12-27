package android.app;

import android.accounts.AccountManager;
import android.accounts.IAccountManager;
import android.adservices.AdServicesFrameworkInitializer;
import android.annotation.SystemApi;
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
import android.app.ondeviceintelligence.IOnDeviceIntelligenceManager;
import android.app.ondeviceintelligence.OnDeviceIntelligenceManager;
import android.app.people.PeopleManager;
import android.app.prediction.AppPredictionManager;
import android.app.role.RoleFrameworkInitializer;
import android.app.sdksandbox.SdkSandboxManagerFrameworkInitializer;
import android.app.search.SearchUiManager;
import android.app.slice.SliceManager;
import android.app.smartspace.SmartspaceManager;
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
import android.content.integrity.AppIntegrityManager;
import android.content.integrity.IAppIntegrityManager;
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
import android.net.vcn.IVcnManagementService;
import android.net.vcn.VcnManager;
import android.net.wifi.WifiFrameworkInitializer;
import android.net.wifi.nl80211.WifiNl80211Manager;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
import android.nfc.NfcFrameworkInitializer;
import android.ondevicepersonalization.OnDevicePersonalizationFrameworkInitializer;
import android.os.BatteryManager;
import android.os.BatteryStatsManager;
import android.os.BugreportManager;
import android.os.Build;
import android.os.CustomFrequencyManager;
import android.os.DropBoxManager;
import android.os.HardwarePropertiesManager;
import android.os.IBatteryPropertiesRegistrar;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.IDumpstate;
import android.os.IHardwarePropertiesManager;
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
import android.os.SystemProperties;
import android.os.SystemUpdateManager;
import android.os.SystemVibrator;
import android.os.SystemVibratorManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.VibratorManager;
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
import android.safetycenter.SafetyCenterFrameworkInitializer;
import android.scheduling.SchedulingFrameworkInitializer;
import android.security.FileIntegrityManager;
import android.security.IFileIntegrityService;
import android.security.attestationverification.AttestationVerificationManager;
import android.security.attestationverification.IAttestationVerificationManagerService;
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
import com.samsung.android.desktopmode.IDesktopMode;
import com.samsung.android.desktopmode.SemDesktopModeManager;
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
import com.samsung.android.media.AudioTag;
import com.samsung.android.media.codec.SemVideoTranscodingService;
import com.samsung.android.media.fmradio.SemFmPlayer;
import com.samsung.android.mocca.IMoccaService;
import com.samsung.android.mocca.SemMdContextManager;
import com.samsung.android.multicontrol.IMultiControlManager;
import com.samsung.android.multicontrol.SemMultiControlManager;
import com.samsung.android.net.ExtendedEthernetManager;
import com.samsung.android.net.IExtendedEthernetManager;
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

import vendor.samsung.frameworks.codecsolution.SemCodecSolutionService;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

@SystemApi
/* loaded from: classes.dex */
public final class SystemServiceRegistry {
    static final long ENABLE_CHECKING_TELEPHONY_FEATURES_FOR_VCN = 330902016;
    private static final String TAG = "SystemServiceRegistry";
    private static final int VENDOR_API_FOR_ANDROID_V = 202404;
    private static volatile boolean sInitializing;
    private static int sServiceCacheSize;
    public static boolean sEnableServiceNotFoundWtf = false;
    private static final Map<Class<?>, String> SYSTEM_SERVICE_NAMES = new ArrayMap();
    private static final Map<String, ServiceFetcher<?>> SYSTEM_SERVICE_FETCHERS = new ArrayMap();
    private static final Map<String, String> SYSTEM_SERVICE_CLASS_NAMES = new ArrayMap();

    @SystemApi
    public interface ContextAwareServiceProducerWithBinder<TServiceClass> {
        TServiceClass createService(Context context, IBinder iBinder);
    }

    @SystemApi
    public interface ContextAwareServiceProducerWithoutBinder<TServiceClass> {
        TServiceClass createService(Context context);
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
        registerService(
                Context.ACCESSIBILITY_SERVICE,
                AccessibilityManager.class,
                new CachedServiceFetcher<
                        AccessibilityManager>() { // from class: android.app.SystemServiceRegistry.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public AccessibilityManager createService(ContextImpl ctx) {
                        return AccessibilityManager.getInstance(ctx);
                    }
                });
        registerService(
                Context.CAPTIONING_SERVICE,
                CaptioningManager.class,
                new CachedServiceFetcher<
                        CaptioningManager>() { // from class: android.app.SystemServiceRegistry.2
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public CaptioningManager createService(ContextImpl ctx) {
                        return new CaptioningManager(ctx);
                    }
                });
        registerService(
                "account",
                AccountManager.class,
                new CachedServiceFetcher<
                        AccountManager>() { // from class: android.app.SystemServiceRegistry.3
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public AccountManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow("account");
                        IAccountManager service = IAccountManager.Stub.asInterface(b);
                        return new AccountManager(ctx, service);
                    }
                });
        registerService(
                "activity",
                ActivityManager.class,
                new CachedServiceFetcher<
                        ActivityManager>() { // from class: android.app.SystemServiceRegistry.4
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public ActivityManager createService(ContextImpl ctx) {
                        return new ActivityManager(
                                ctx.getOuterContext(), ctx.mMainThread.getHandler());
                    }
                });
        registerService(
                Context.ACTIVITY_TASK_SERVICE,
                ActivityTaskManager.class,
                new CachedServiceFetcher<
                        ActivityTaskManager>() { // from class: android.app.SystemServiceRegistry.5
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public ActivityTaskManager createService(ContextImpl ctx) {
                        return ActivityTaskManager.getInstance();
                    }
                });
        registerService(
                Context.URI_GRANTS_SERVICE,
                UriGrantsManager.class,
                new CachedServiceFetcher<
                        UriGrantsManager>() { // from class: android.app.SystemServiceRegistry.6
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public UriGrantsManager createService(ContextImpl ctx) {
                        return new UriGrantsManager(
                                ctx.getOuterContext(), ctx.mMainThread.getHandler());
                    }
                });
        registerService(
                "alarm",
                AlarmManager.class,
                new CachedServiceFetcher<
                        AlarmManager>() { // from class: android.app.SystemServiceRegistry.7
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public AlarmManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow("alarm");
                        IAlarmManager service = IAlarmManager.Stub.asInterface(b);
                        return new AlarmManager(service, ctx);
                    }
                });
        registerService(
                "audio",
                AudioManager.class,
                new CachedServiceFetcher<
                        AudioManager>() { // from class: android.app.SystemServiceRegistry.8
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public AudioManager createService(ContextImpl ctx) {
                        return new AudioManager(ctx);
                    }
                });
        registerService(
                Context.AUDIO_DEVICE_VOLUME_SERVICE,
                AudioDeviceVolumeManager.class,
                new CachedServiceFetcher<AudioDeviceVolumeManager>() { // from class:
                    // android.app.SystemServiceRegistry.9
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public AudioDeviceVolumeManager createService(ContextImpl ctx) {
                        return new AudioDeviceVolumeManager(ctx);
                    }
                });
        registerService(
                Context.MEDIA_ROUTER_SERVICE,
                MediaRouter.class,
                new CachedServiceFetcher<
                        MediaRouter>() { // from class: android.app.SystemServiceRegistry.10
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public MediaRouter createService(ContextImpl ctx) {
                        return new MediaRouter(ctx);
                    }
                });
        registerService(
                Context.CFMS_SERVICE,
                CustomFrequencyManager.class,
                new CachedServiceFetcher<CustomFrequencyManager>() { // from class:
                    // android.app.SystemServiceRegistry.11
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public CustomFrequencyManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getService(Context.CFMS_SERVICE);
                        ICustomFrequencyManager service =
                                ICustomFrequencyManager.Stub.asInterface(b);
                        if (service == null) {
                            Log.wtf(
                                    SystemServiceRegistry.TAG,
                                    "Failed to get custom frequency manager service.");
                        }
                        return new CustomFrequencyManager(service, ctx.mMainThread.getHandler());
                    }
                });
        registerService(
                Context.ICCC_SERVICE,
                IntegrityControlCheckCenter.class,
                new CachedServiceFetcher<IntegrityControlCheckCenter>() { // from class:
                    // android.app.SystemServiceRegistry.12
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public IntegrityControlCheckCenter createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(Context.ICCC_SERVICE);
                        return new IntegrityControlCheckCenter(
                                IIntegrityControlCheckCenter.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.DISPLAY_AIQE_SERVICE,
                DisplayAiqeManager.class,
                new CachedServiceFetcher<
                        DisplayAiqeManager>() { // from class: android.app.SystemServiceRegistry.14
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public DisplayAiqeManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.DISPLAY_AIQE_SERVICE);
                        return new DisplayAiqeManager(ctx, IDisplayAiqeManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.HDMI_CONTROL_SERVICE,
                HdmiControlManager.class,
                new StaticServiceFetcher<
                        HdmiControlManager>() { // from class: android.app.SystemServiceRegistry.15
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
                    public HdmiControlManager createService()
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.HDMI_CONTROL_SERVICE);
                        return new HdmiControlManager(IHdmiControlService.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.TEXT_CLASSIFICATION_SERVICE,
                TextClassificationManager.class,
                new CachedServiceFetcher<TextClassificationManager>() { // from class:
                    // android.app.SystemServiceRegistry.16
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public TextClassificationManager createService(ContextImpl ctx) {
                        return new TextClassificationManager(ctx);
                    }
                });
        registerService(
                Context.FONT_SERVICE,
                FontManager.class,
                new CachedServiceFetcher<
                        FontManager>() { // from class: android.app.SystemServiceRegistry.17
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public FontManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.FONT_SERVICE);
                        return FontManager.create(IFontManager.Stub.asInterface(b));
                    }
                });
        registerService(
                "clipboard",
                ClipboardManager.class,
                new CachedServiceFetcher<
                        ClipboardManager>() { // from class: android.app.SystemServiceRegistry.18
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public ClipboardManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new ClipboardManager(
                                ctx.getOuterContext(), ctx.mMainThread.getHandler());
                    }
                });
        SYSTEM_SERVICE_NAMES.put(android.text.ClipboardManager.class, "clipboard");
        registerService(
                Context.SEM_CLIPBOARD_SERVICE,
                SemClipboardManager.class,
                new CachedServiceFetcher<
                        SemClipboardManager>() { // from class: android.app.SystemServiceRegistry.19
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemClipboardManager createService(ContextImpl ctx) {
                        return new SemClipboardManager(ctx.getOuterContext());
                    }
                });
        registerService(
                Context.PAC_PROXY_SERVICE,
                PacProxyManager.class,
                new CachedServiceFetcher<
                        PacProxyManager>() { // from class: android.app.SystemServiceRegistry.20
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public PacProxyManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.PAC_PROXY_SERVICE);
                        IPacProxyManager service = IPacProxyManager.Stub.asInterface(b);
                        return new PacProxyManager(ctx.getOuterContext(), service);
                    }
                });
        registerService(
                Context.NETD_SERVICE,
                IBinder.class,
                new StaticServiceFetcher<
                        IBinder>() { // from class: android.app.SystemServiceRegistry.21
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
                    public IBinder createService() throws ServiceManager.ServiceNotFoundException {
                        return ServiceManager.getServiceOrThrow(Context.NETD_SERVICE);
                    }
                });
        registerService(Context.TETHERING_SERVICE, TetheringManager.class, new AnonymousClass22());
        registerService(
                Context.VPN_MANAGEMENT_SERVICE,
                VpnManager.class,
                new CachedServiceFetcher<
                        VpnManager>() { // from class: android.app.SystemServiceRegistry.23
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public VpnManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getService(Context.VPN_MANAGEMENT_SERVICE);
                        IVpnManager service = IVpnManager.Stub.asInterface(b);
                        return new VpnManager(ctx, service);
                    }
                });
        registerService(
                Context.VCN_MANAGEMENT_SERVICE,
                VcnManager.class,
                new CachedServiceFetcher<
                        VcnManager>() { // from class: android.app.SystemServiceRegistry.24
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public VcnManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        String telephonyFeatureToCheck =
                                SystemServiceRegistry.getVcnFeatureDependency();
                        if (telephonyFeatureToCheck != null
                                && !ctx.getPackageManager()
                                        .hasSystemFeature(telephonyFeatureToCheck)) {
                            return null;
                        }
                        IBinder b = ServiceManager.getService(Context.VCN_MANAGEMENT_SERVICE);
                        IVcnManagementService service = IVcnManagementService.Stub.asInterface(b);
                        return new VcnManager(ctx, service);
                    }
                });
        registerService(
                Context.COUNTRY_DETECTOR,
                CountryDetector.class,
                new StaticServiceFetcher<
                        CountryDetector>() { // from class: android.app.SystemServiceRegistry.25
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
                    public CountryDetector createService()
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.COUNTRY_DETECTOR);
                        return new CountryDetector(ICountryDetector.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.DEVICE_POLICY_SERVICE,
                DevicePolicyManager.class,
                new CachedServiceFetcher<
                        DevicePolicyManager>() { // from class: android.app.SystemServiceRegistry.26
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public DevicePolicyManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.DEVICE_POLICY_SERVICE);
                        return new DevicePolicyManager(
                                ctx, IDevicePolicyManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.DOWNLOAD_SERVICE,
                DownloadManager.class,
                new CachedServiceFetcher<
                        DownloadManager>() { // from class: android.app.SystemServiceRegistry.27
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public DownloadManager createService(ContextImpl ctx) {
                        return new DownloadManager(ctx);
                    }
                });
        registerService(
                Context.HQM_SERVICE,
                SemHqmManager.class,
                new CachedServiceFetcher<
                        SemHqmManager>() { // from class: android.app.SystemServiceRegistry.28
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemHqmManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(Context.HQM_SERVICE);
                        ISemHqmManager service = ISemHqmManager.Stub.asInterface(b);
                        if (service == null) {
                            Log.e(SystemServiceRegistry.TAG, "Failed to get Hqm manager service.");
                            return null;
                        }
                        return new SemHqmManager(service, ctx.mMainThread.getHandler());
                    }
                });
        if (!"0"
                .equals(
                        SemFloatingFeature.getInstance()
                                .getString(
                                        "SEC_FLOATING_FEATURE_SYSTEM_CONFIG_HCM_AI_POWER_SAVING_LEVEL"))) {
            registerService(
                    Context.HCM_SERVICE,
                    SemHcmManager.class,
                    new CachedServiceFetcher<
                            SemHcmManager>() { // from class: android.app.SystemServiceRegistry.29
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                        public SemHcmManager createService(ContextImpl ctx) {
                            IBinder b = ServiceManager.getService(Context.HCM_SERVICE);
                            ISemHcmManager service = ISemHcmManager.Stub.asInterface(b);
                            if (service == null) {
                                Log.e(
                                        SystemServiceRegistry.TAG,
                                        "Failed to get Hcm manager service.");
                                return null;
                            }
                            return new SemHcmManager(service, ctx.mMainThread.getHandler());
                        }
                    });
        }
        registerService(
                Context.BATTERY_SERVICE,
                BatteryManager.class,
                new CachedServiceFetcher<
                        BatteryManager>() { // from class: android.app.SystemServiceRegistry.30
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public BatteryManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBatteryStats stats =
                                IBatteryStats.Stub.asInterface(
                                        ServiceManager.getServiceOrThrow("batterystats"));
                        IBatteryPropertiesRegistrar registrar =
                                IBatteryPropertiesRegistrar.Stub.asInterface(
                                        ServiceManager.getServiceOrThrow("batteryproperties"));
                        return new BatteryManager(ctx, stats, registrar);
                    }
                });
        registerService(
                Context.DROPBOX_SERVICE,
                DropBoxManager.class,
                new CachedServiceFetcher<
                        DropBoxManager>() { // from class: android.app.SystemServiceRegistry.31
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public DropBoxManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.DROPBOX_SERVICE);
                        IDropBoxManagerService service = IDropBoxManagerService.Stub.asInterface(b);
                        return new DropBoxManager(ctx, service);
                    }
                });
        registerService(
                "transparency",
                BinaryTransparencyManager.class,
                new CachedServiceFetcher<BinaryTransparencyManager>() { // from class:
                    // android.app.SystemServiceRegistry.32
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public BinaryTransparencyManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow("transparency");
                        IBinaryTransparencyService service =
                                IBinaryTransparencyService.Stub.asInterface(b);
                        return new BinaryTransparencyManager(ctx, service);
                    }
                });
        registerService(
                "input",
                InputManager.class,
                new CachedServiceFetcher<
                        InputManager>() { // from class: android.app.SystemServiceRegistry.33
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public InputManager createService(ContextImpl ctx) {
                        return new InputManager(ctx.getOuterContext());
                    }
                });
        registerService(
                Context.DISPLAY_SERVICE,
                DisplayManager.class,
                new CachedServiceFetcher<
                        DisplayManager>() { // from class: android.app.SystemServiceRegistry.34
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public DisplayManager createService(ContextImpl ctx) {
                        return new DisplayManager(ctx.getOuterContext());
                    }
                });
        registerService(
                Context.COLOR_DISPLAY_SERVICE,
                ColorDisplayManager.class,
                new CachedServiceFetcher<
                        ColorDisplayManager>() { // from class: android.app.SystemServiceRegistry.35
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public ColorDisplayManager createService(ContextImpl ctx) {
                        return new ColorDisplayManager();
                    }
                });
        registerService(
                Context.INPUT_METHOD_SERVICE,
                InputMethodManager.class,
                new ServiceFetcher<
                        InputMethodManager>() { // from class: android.app.SystemServiceRegistry.36
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.ServiceFetcher
                    public InputMethodManager getService(ContextImpl ctx) {
                        return InputMethodManager.forContext(ctx.getOuterContext());
                    }
                });
        registerService(
                Context.TEXT_SERVICES_MANAGER_SERVICE,
                TextServicesManager.class,
                new CachedServiceFetcher<
                        TextServicesManager>() { // from class: android.app.SystemServiceRegistry.37
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public TextServicesManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return TextServicesManager.createInstance(ctx);
                    }
                });
        registerService(
                Context.KEYGUARD_SERVICE,
                KeyguardManager.class,
                new CachedServiceFetcher<
                        KeyguardManager>() { // from class: android.app.SystemServiceRegistry.38
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public KeyguardManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new KeyguardManager(ctx);
                    }
                });
        registerService(
                Context.LAYOUT_INFLATER_SERVICE,
                LayoutInflater.class,
                new CachedServiceFetcher<
                        LayoutInflater>() { // from class: android.app.SystemServiceRegistry.39
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public LayoutInflater createService(ContextImpl ctx) {
                        return new PhoneLayoutInflater(ctx.getOuterContext());
                    }
                });
        registerService(
                "location",
                LocationManager.class,
                new CachedServiceFetcher<
                        LocationManager>() { // from class: android.app.SystemServiceRegistry.40
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public LocationManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow("location");
                        return new LocationManager(ctx, ILocationManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.SEM_LOCATION_SERVICE,
                SemLocationManager.class,
                new CachedServiceFetcher<
                        SemLocationManager>() { // from class: android.app.SystemServiceRegistry.41
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemLocationManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        Log.e(SystemServiceRegistry.TAG, "create SemLocationManager service");
                        IBinder b = ServiceManager.getService(Context.SEM_LOCATION_SERVICE);
                        return new SemLocationManager(ctx, ISLocationManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.NETWORK_POLICY_SERVICE,
                NetworkPolicyManager.class,
                new CachedServiceFetcher<NetworkPolicyManager>() { // from class:
                    // android.app.SystemServiceRegistry.42
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public NetworkPolicyManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new NetworkPolicyManager(
                                ctx,
                                INetworkPolicyManager.Stub.asInterface(
                                        ServiceManager.getServiceOrThrow(
                                                Context.NETWORK_POLICY_SERVICE)));
                    }
                });
        registerService(
                "notification",
                NotificationManager.class,
                new CachedServiceFetcher<
                        NotificationManager>() { // from class: android.app.SystemServiceRegistry.43
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public NotificationManager createService(ContextImpl ctx) {
                        Context outerContext = ctx.getOuterContext();
                        return new NotificationManager(
                                new ContextThemeWrapper(
                                        outerContext,
                                        Resources.selectSystemTheme(
                                                0,
                                                outerContext.getApplicationInfo().targetSdkVersion,
                                                16973835,
                                                16973935,
                                                16974126,
                                                16974130)),
                                ctx.mMainThread.getHandler());
                    }
                });
        registerService(
                Context.PEOPLE_SERVICE,
                PeopleManager.class,
                new CachedServiceFetcher<
                        PeopleManager>() { // from class: android.app.SystemServiceRegistry.44
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public PeopleManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new PeopleManager(ctx);
                    }
                });
        registerService(
                "power",
                PowerManager.class,
                new CachedServiceFetcher<
                        PowerManager>() { // from class: android.app.SystemServiceRegistry.45
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public PowerManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder powerBinder = ServiceManager.getServiceOrThrow("power");
                        IPowerManager powerService = IPowerManager.Stub.asInterface(powerBinder);
                        IBinder thermalBinder =
                                ServiceManager.getServiceOrThrow(Context.THERMAL_SERVICE);
                        IThermalService thermalService =
                                IThermalService.Stub.asInterface(thermalBinder);
                        return new PowerManager(
                                ctx.getOuterContext(),
                                powerService,
                                thermalService,
                                ctx.mMainThread.getHandler());
                    }
                });
        registerService(
                Context.PERFORMANCE_HINT_SERVICE,
                PerformanceHintManager.class,
                new CachedServiceFetcher<PerformanceHintManager>() { // from class:
                    // android.app.SystemServiceRegistry.47
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public PerformanceHintManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return PerformanceHintManager.create();
                    }
                });
        registerService(
                "recovery",
                RecoverySystem.class,
                new CachedServiceFetcher<
                        RecoverySystem>() { // from class: android.app.SystemServiceRegistry.48
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public RecoverySystem createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow("recovery");
                        IRecoverySystem service = IRecoverySystem.Stub.asInterface(b);
                        return new RecoverySystem(service);
                    }
                });
        registerService(
                "search",
                SearchManager.class,
                new CachedServiceFetcher<
                        SearchManager>() { // from class: android.app.SystemServiceRegistry.49
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SearchManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new SearchManager(
                                ctx.getOuterContext(), ctx.mMainThread.getHandler());
                    }
                });
        registerService(
                Context.SECURITY_STATE_SERVICE,
                SecurityStateManager.class,
                new CachedServiceFetcher<SecurityStateManager>() { // from class:
                    // android.app.SystemServiceRegistry.50
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SecurityStateManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b =
                                ServiceManager.getServiceOrThrow(Context.SECURITY_STATE_SERVICE);
                        ISecurityStateManager service = ISecurityStateManager.Stub.asInterface(b);
                        return new SecurityStateManager(service);
                    }
                });
        registerService(
                Context.SENSOR_SERVICE,
                SensorManager.class,
                new CachedServiceFetcher<
                        SensorManager>() { // from class: android.app.SystemServiceRegistry.51
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SensorManager createService(ContextImpl ctx) {
                        return new SystemSensorManager(
                                ctx.getOuterContext(), ctx.mMainThread.getHandler().getLooper());
                    }
                });
        registerService(
                Context.SENSOR_PRIVACY_SERVICE,
                SensorPrivacyManager.class,
                new CachedServiceFetcher<SensorPrivacyManager>() { // from class:
                    // android.app.SystemServiceRegistry.52
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SensorPrivacyManager createService(ContextImpl ctx) {
                        return SensorPrivacyManager.getInstance(ctx);
                    }
                });
        registerService(
                Context.STATUS_BAR_SERVICE,
                StatusBarManager.class,
                new CachedServiceFetcher<
                        StatusBarManager>() { // from class: android.app.SystemServiceRegistry.53
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public StatusBarManager createService(ContextImpl ctx) {
                        return new StatusBarManager(ctx.getOuterContext());
                    }
                });
        registerService(
                Context.SEM_STATUS_BAR_SERVICE,
                SemStatusBarManager.class,
                new CachedServiceFetcher<
                        SemStatusBarManager>() { // from class: android.app.SystemServiceRegistry.54
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemStatusBarManager createService(ContextImpl ctx) {
                        return new SemStatusBarManager(ctx.getOuterContext());
                    }
                });
        registerService(
                Context.SEM_EDGE_SERVICE,
                SemEdgeManager.class,
                new CachedServiceFetcher<
                        SemEdgeManager>() { // from class: android.app.SystemServiceRegistry.55
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemEdgeManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService("notification");
                        return new SemEdgeManager(ctx, INotificationManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.STORAGE_SERVICE,
                StorageManager.class,
                new CachedServiceFetcher<
                        StorageManager>() { // from class: android.app.SystemServiceRegistry.56
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public StorageManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new StorageManager(ctx, ctx.mMainThread.getHandler().getLooper());
                    }
                });
        registerService(
                Context.STORAGE_STATS_SERVICE,
                StorageStatsManager.class,
                new CachedServiceFetcher<
                        StorageStatsManager>() { // from class: android.app.SystemServiceRegistry.57
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public StorageStatsManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IStorageStatsManager service =
                                IStorageStatsManager.Stub.asInterface(
                                        ServiceManager.getServiceOrThrow(
                                                Context.STORAGE_STATS_SERVICE));
                        return new StorageStatsManager(ctx, service);
                    }
                });
        registerService(
                Context.SYSTEM_UPDATE_SERVICE,
                SystemUpdateManager.class,
                new CachedServiceFetcher<
                        SystemUpdateManager>() { // from class: android.app.SystemServiceRegistry.58
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SystemUpdateManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.SYSTEM_UPDATE_SERVICE);
                        ISystemUpdateManager service = ISystemUpdateManager.Stub.asInterface(b);
                        return new SystemUpdateManager(service);
                    }
                });
        registerService(
                Context.SYSTEM_CONFIG_SERVICE,
                SystemConfigManager.class,
                new CachedServiceFetcher<
                        SystemConfigManager>() { // from class: android.app.SystemServiceRegistry.59
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SystemConfigManager createService(ContextImpl ctx) {
                        return new SystemConfigManager();
                    }
                });
        registerService(
                Context.TELEPHONY_REGISTRY_SERVICE,
                TelephonyRegistryManager.class,
                new CachedServiceFetcher<TelephonyRegistryManager>() { // from class:
                    // android.app.SystemServiceRegistry.60
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public TelephonyRegistryManager createService(ContextImpl ctx) {
                        return new TelephonyRegistryManager(ctx);
                    }
                });
        registerService(
                Context.TELECOM_SERVICE,
                TelecomManager.class,
                new CachedServiceFetcher<
                        TelecomManager>() { // from class: android.app.SystemServiceRegistry.61
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public TelecomManager createService(ContextImpl ctx) {
                        return new TelecomManager(ctx.getOuterContext());
                    }
                });
        if (SemTelecomManager.hasSamsungTelecomSystemFeature()) {
            registerService(
                    Context.SEM_TELECOM_SERVICE,
                    SemTelecomManager.class,
                    new CachedServiceFetcher<SemTelecomManager>() { // from class:
                        // android.app.SystemServiceRegistry.62
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                        public SemTelecomManager createService(ContextImpl ctx) {
                            return new SemTelecomManager(ctx.getOuterContext());
                        }
                    });
        }
        registerService(
                "mms",
                MmsManager.class,
                new CachedServiceFetcher<
                        MmsManager>() { // from class: android.app.SystemServiceRegistry.63
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public MmsManager createService(ContextImpl ctx) {
                        return new MmsManager(ctx.getOuterContext());
                    }
                });
        registerService(
                Context.UI_MODE_SERVICE,
                UiModeManager.class,
                new CachedServiceFetcher<
                        UiModeManager>() { // from class: android.app.SystemServiceRegistry.64
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public UiModeManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new UiModeManager(ctx.getOuterContext());
                    }
                });
        registerService(
                "usb",
                UsbManager.class,
                new CachedServiceFetcher<
                        UsbManager>() { // from class: android.app.SystemServiceRegistry.65
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public UsbManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow("usb");
                        return new UsbManager(ctx, IUsbManager.Stub.asInterface(b));
                    }
                });
        registerService(
                "adb",
                AdbManager.class,
                new CachedServiceFetcher<
                        AdbManager>() { // from class: android.app.SystemServiceRegistry.66
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public AdbManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow("adb");
                        return new AdbManager(ctx, IAdbManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.SERIAL_SERVICE,
                SerialManager.class,
                new CachedServiceFetcher<
                        SerialManager>() { // from class: android.app.SystemServiceRegistry.67
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SerialManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.SERIAL_SERVICE);
                        return new SerialManager(ctx, ISerialManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.VIBRATOR_MANAGER_SERVICE,
                VibratorManager.class,
                new CachedServiceFetcher<
                        VibratorManager>() { // from class: android.app.SystemServiceRegistry.68
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public VibratorManager createService(ContextImpl ctx) {
                        return new SystemVibratorManager(ctx);
                    }
                });
        registerService(
                Context.VIBRATOR_SERVICE,
                Vibrator.class,
                new CachedServiceFetcher<
                        Vibrator>() { // from class: android.app.SystemServiceRegistry.69
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public Vibrator createService(ContextImpl ctx) {
                        return new SystemVibrator(ctx);
                    }
                });
        registerService(
                "wallpaper",
                WallpaperManager.class,
                new CachedServiceFetcher<
                        WallpaperManager>() { // from class: android.app.SystemServiceRegistry.70
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public WallpaperManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getService("wallpaper");
                        if (b == null) {
                            ApplicationInfo appInfo = ctx.getApplicationInfo();
                            if (appInfo.targetSdkVersion >= 28 && appInfo.isInstantApp()) {
                                throw new ServiceManager.ServiceNotFoundException("wallpaper");
                            }
                            boolean enabled =
                                    Resources.getSystem()
                                            .getBoolean(R.bool.config_enableWallpaperService);
                            if (!enabled) {
                                return DisabledWallpaperManager.getInstance();
                            }
                            Log.e(SystemServiceRegistry.TAG, "No wallpaper service");
                        }
                        IWallpaperManager service = IWallpaperManager.Stub.asInterface(b);
                        return new WallpaperManager(
                                service, ctx.getOuterContext(), ctx.mMainThread.getHandler());
                    }
                });
        registerService(
                Context.WIFI_NL80211_SERVICE,
                WifiNl80211Manager.class,
                new CachedServiceFetcher<
                        WifiNl80211Manager>() { // from class: android.app.SystemServiceRegistry.71
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public WifiNl80211Manager createService(ContextImpl ctx) {
                        return new WifiNl80211Manager(ctx.getOuterContext());
                    }
                });
        registerService(
                Context.CameraServiceWorker_manager,
                CameraServiceWorkerManager.class,
                new CachedServiceFetcher<CameraServiceWorkerManager>() { // from class:
                    // android.app.SystemServiceRegistry.72
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public CameraServiceWorkerManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new CameraServiceWorkerManager(
                                ServiceManager.getService("media.camera.worker"));
                    }
                });
        registerService(
                Context.WINDOW_SERVICE,
                WindowManager.class,
                new CachedServiceFetcher<
                        WindowManager>() { // from class: android.app.SystemServiceRegistry.73
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public WindowManager createService(ContextImpl ctx) {
                        return new WindowManagerImpl(ctx);
                    }
                });
        registerService(
                "user",
                UserManager.class,
                new CachedServiceFetcher<
                        UserManager>() { // from class: android.app.SystemServiceRegistry.74
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public UserManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow("user");
                        IUserManager service = IUserManager.Stub.asInterface(b);
                        return new UserManager(ctx, service);
                    }
                });
        registerService(
                Context.APP_OPS_SERVICE,
                AppOpsManager.class,
                new CachedServiceFetcher<
                        AppOpsManager>() { // from class: android.app.SystemServiceRegistry.75
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public AppOpsManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.APP_OPS_SERVICE);
                        IAppOpsService service = IAppOpsService.Stub.asInterface(b);
                        return new AppOpsManager(ctx, service);
                    }
                });
        registerService(
                Context.CAMERA_SERVICE,
                CameraManager.class,
                new CachedServiceFetcher<
                        CameraManager>() { // from class: android.app.SystemServiceRegistry.76
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public CameraManager createService(ContextImpl ctx) {
                        return new CameraManager(ctx);
                    }
                });
        registerService(
                Context.LAUNCHER_APPS_SERVICE,
                LauncherApps.class,
                new CachedServiceFetcher<
                        LauncherApps>() { // from class: android.app.SystemServiceRegistry.77
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public LauncherApps createService(ContextImpl ctx) {
                        return new LauncherApps(ctx);
                    }
                });
        registerService(
                Context.RESTRICTIONS_SERVICE,
                RestrictionsManager.class,
                new CachedServiceFetcher<
                        RestrictionsManager>() { // from class: android.app.SystemServiceRegistry.78
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public RestrictionsManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.RESTRICTIONS_SERVICE);
                        IRestrictionsManager service = IRestrictionsManager.Stub.asInterface(b);
                        return new RestrictionsManager(ctx, service);
                    }
                });
        registerService(
                Context.PRINT_SERVICE,
                PrintManager.class,
                new CachedServiceFetcher<
                        PrintManager>() { // from class: android.app.SystemServiceRegistry.79
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public PrintManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IPrintManager service = null;
                        if (ctx.getPackageManager()
                                .hasSystemFeature(PackageManager.FEATURE_PRINTING)) {
                            service =
                                    IPrintManager.Stub.asInterface(
                                            ServiceManager.getServiceOrThrow(
                                                    Context.PRINT_SERVICE));
                        }
                        int userId = ctx.getUserId();
                        int appId = UserHandle.getAppId(ctx.getApplicationInfo().uid);
                        return new PrintManager(ctx.getOuterContext(), service, userId, appId);
                    }
                });
        registerService(
                Context.COMPANION_DEVICE_SERVICE,
                CompanionDeviceManager.class,
                new CachedServiceFetcher<CompanionDeviceManager>() { // from class:
                    // android.app.SystemServiceRegistry.80
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public CompanionDeviceManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        ICompanionDeviceManager service = null;
                        if (ctx.getPackageManager()
                                .hasSystemFeature(PackageManager.FEATURE_COMPANION_DEVICE_SETUP)) {
                            service =
                                    ICompanionDeviceManager.Stub.asInterface(
                                            ServiceManager.getServiceOrThrow(
                                                    Context.COMPANION_DEVICE_SERVICE));
                        }
                        return new CompanionDeviceManager(service, ctx.getOuterContext());
                    }
                });
        if (SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_COMMON_CONFIG_AI_VERSION", -1)
                        >= 20251
                && com.android.internal.hidden_from_bootclasspath.android.app.appfunctions.flags
                        .Flags.enableAppFunctionManager()) {
            registerService(
                    Context.APP_FUNCTION_SERVICE,
                    AppFunctionManager.class,
                    new CachedServiceFetcher<AppFunctionManager>() { // from class:
                        // android.app.SystemServiceRegistry.81
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                        public AppFunctionManager createService(ContextImpl ctx)
                                throws ServiceManager.ServiceNotFoundException {
                            if (!AppFunctionManagerConfiguration.isSupported(ctx)) {
                                return null;
                            }
                            IAppFunctionManager service =
                                    IAppFunctionManager.Stub.asInterface(
                                            ServiceManager.getServiceOrThrow(
                                                    Context.APP_FUNCTION_SERVICE));
                            return new AppFunctionManager(service, ctx.getOuterContext());
                        }
                    });
        }
        registerService(
                Context.VIRTUAL_DEVICE_SERVICE,
                VirtualDeviceManager.class,
                new CachedServiceFetcher<VirtualDeviceManager>() { // from class:
                    // android.app.SystemServiceRegistry.82
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public VirtualDeviceManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        if (!ctx.getResources()
                                .getBoolean(R.bool.config_enableVirtualDeviceManager)) {
                            return null;
                        }
                        IVirtualDeviceManager service =
                                IVirtualDeviceManager.Stub.asInterface(
                                        ServiceManager.getServiceOrThrow(
                                                Context.VIRTUAL_DEVICE_SERVICE));
                        return new VirtualDeviceManager(service, ctx.getOuterContext());
                    }
                });
        registerService(
                Context.CONSUMER_IR_SERVICE,
                ConsumerIrManager.class,
                new CachedServiceFetcher<
                        ConsumerIrManager>() { // from class: android.app.SystemServiceRegistry.83
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public ConsumerIrManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new ConsumerIrManager(ctx);
                    }
                });
        registerService(
                Context.TRUST_SERVICE,
                TrustManager.class,
                new StaticServiceFetcher<
                        TrustManager>() { // from class: android.app.SystemServiceRegistry.84
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
                    public TrustManager createService()
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.TRUST_SERVICE);
                        return new TrustManager(b);
                    }
                });
        registerService(
                Context.FINGERPRINT_SERVICE,
                FingerprintManager.class,
                new CachedServiceFetcher<
                        FingerprintManager>() { // from class: android.app.SystemServiceRegistry.85
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public FingerprintManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder binder;
                        if (ctx.getApplicationInfo().targetSdkVersion >= 26) {
                            binder = ServiceManager.getServiceOrThrow(Context.FINGERPRINT_SERVICE);
                        } else {
                            binder = ServiceManager.getService(Context.FINGERPRINT_SERVICE);
                        }
                        IFingerprintService service = IFingerprintService.Stub.asInterface(binder);
                        return new FingerprintManager(ctx.getOuterContext(), service);
                    }
                });
        registerService(
                Context.FACE_SERVICE,
                FaceManager.class,
                new CachedServiceFetcher<
                        FaceManager>() { // from class: android.app.SystemServiceRegistry.86
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public FaceManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder binder;
                        if (ctx.getApplicationInfo().targetSdkVersion >= 26) {
                            binder = ServiceManager.getServiceOrThrow(Context.FACE_SERVICE);
                        } else {
                            binder = ServiceManager.getService(Context.FACE_SERVICE);
                        }
                        IFaceService service = IFaceService.Stub.asInterface(binder);
                        return new FaceManager(ctx.getOuterContext(), service);
                    }
                });
        registerService(
                Context.IRIS_SERVICE,
                IrisManager.class,
                new CachedServiceFetcher<
                        IrisManager>() { // from class: android.app.SystemServiceRegistry.87
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public IrisManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder binder = ServiceManager.getServiceOrThrow(Context.IRIS_SERVICE);
                        IIrisService service = IIrisService.Stub.asInterface(binder);
                        return new IrisManager(ctx.getOuterContext(), service);
                    }
                });
        registerService(
                Context.BIOMETRIC_SERVICE,
                BiometricManager.class,
                new CachedServiceFetcher<
                        BiometricManager>() { // from class: android.app.SystemServiceRegistry.88
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public BiometricManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder binder = ServiceManager.getServiceOrThrow(Context.AUTH_SERVICE);
                        IAuthService service = IAuthService.Stub.asInterface(binder);
                        return new BiometricManager(ctx.getOuterContext(), service);
                    }
                });
        registerService(
                Context.TV_INTERACTIVE_APP_SERVICE,
                TvInteractiveAppManager.class,
                new CachedServiceFetcher<TvInteractiveAppManager>() { // from class:
                    // android.app.SystemServiceRegistry.89
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public TvInteractiveAppManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder iBinder =
                                ServiceManager.getServiceOrThrow(
                                        Context.TV_INTERACTIVE_APP_SERVICE);
                        ITvInteractiveAppManager service =
                                ITvInteractiveAppManager.Stub.asInterface(iBinder);
                        return new TvInteractiveAppManager(service, ctx.getUserId());
                    }
                });
        registerService(
                Context.TV_AD_SERVICE,
                TvAdManager.class,
                new CachedServiceFetcher<
                        TvAdManager>() { // from class: android.app.SystemServiceRegistry.90
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public TvAdManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder iBinder = ServiceManager.getServiceOrThrow(Context.TV_AD_SERVICE);
                        ITvAdManager service = ITvAdManager.Stub.asInterface(iBinder);
                        return new TvAdManager(service, ctx.getUserId());
                    }
                });
        registerService(
                Context.TV_INPUT_SERVICE,
                TvInputManager.class,
                new CachedServiceFetcher<
                        TvInputManager>() { // from class: android.app.SystemServiceRegistry.91
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public TvInputManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder iBinder =
                                ServiceManager.getServiceOrThrow(Context.TV_INPUT_SERVICE);
                        ITvInputManager service = ITvInputManager.Stub.asInterface(iBinder);
                        return new TvInputManager(service, ctx.getUserId());
                    }
                });
        registerService(
                Context.TV_TUNER_RESOURCE_MGR_SERVICE,
                TunerResourceManager.class,
                new CachedServiceFetcher<TunerResourceManager>() { // from class:
                    // android.app.SystemServiceRegistry.92
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public TunerResourceManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder iBinder =
                                ServiceManager.getServiceOrThrow(
                                        Context.TV_TUNER_RESOURCE_MGR_SERVICE);
                        ITunerResourceManager service =
                                ITunerResourceManager.Stub.asInterface(iBinder);
                        return new TunerResourceManager(service, ctx.getUserId());
                    }
                });
        registerService(
                Context.NETWORK_SCORE_SERVICE,
                NetworkScoreManager.class,
                new CachedServiceFetcher<
                        NetworkScoreManager>() { // from class: android.app.SystemServiceRegistry.93
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public NetworkScoreManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new NetworkScoreManager(ctx);
                    }
                });
        registerService(
                Context.USAGE_STATS_SERVICE,
                UsageStatsManager.class,
                new CachedServiceFetcher<
                        UsageStatsManager>() { // from class: android.app.SystemServiceRegistry.94
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public UsageStatsManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder iBinder =
                                ServiceManager.getServiceOrThrow(Context.USAGE_STATS_SERVICE);
                        IUsageStatsManager service = IUsageStatsManager.Stub.asInterface(iBinder);
                        return new UsageStatsManager(ctx.getOuterContext(), service);
                    }
                });
        registerService(
                Context.PERSISTENT_DATA_BLOCK_SERVICE,
                PersistentDataBlockManager.class,
                new StaticServiceFetcher<PersistentDataBlockManager>() { // from class:
                    // android.app.SystemServiceRegistry.95
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
                    public PersistentDataBlockManager createService()
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b =
                                ServiceManager.getServiceOrThrow(
                                        Context.PERSISTENT_DATA_BLOCK_SERVICE);
                        IPersistentDataBlockService persistentDataBlockService =
                                IPersistentDataBlockService.Stub.asInterface(b);
                        if (persistentDataBlockService != null) {
                            return new PersistentDataBlockManager(persistentDataBlockService);
                        }
                        return null;
                    }
                });
        registerService(
                Context.OEM_LOCK_SERVICE,
                OemLockManager.class,
                new StaticServiceFetcher<
                        OemLockManager>() { // from class: android.app.SystemServiceRegistry.96
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
                    public OemLockManager createService()
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.OEM_LOCK_SERVICE);
                        IOemLockService oemLockService = IOemLockService.Stub.asInterface(b);
                        if (oemLockService != null) {
                            return new OemLockManager(oemLockService);
                        }
                        return null;
                    }
                });
        registerService(
                Context.MEDIA_PROJECTION_SERVICE,
                MediaProjectionManager.class,
                new CachedServiceFetcher<MediaProjectionManager>() { // from class:
                    // android.app.SystemServiceRegistry.97
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public MediaProjectionManager createService(ContextImpl ctx) {
                        return new MediaProjectionManager(ctx);
                    }
                });
        registerService(
                Context.APPWIDGET_SERVICE,
                AppWidgetManager.class,
                new CachedServiceFetcher<
                        AppWidgetManager>() { // from class: android.app.SystemServiceRegistry.98
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public AppWidgetManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(Context.APPWIDGET_SERVICE);
                        if (b == null) {
                            return null;
                        }
                        return new AppWidgetManager(ctx, IAppWidgetService.Stub.asInterface(b));
                    }
                });
        registerService(
                "midi",
                MidiManager.class,
                new CachedServiceFetcher<
                        MidiManager>() { // from class: android.app.SystemServiceRegistry.99
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public MidiManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow("midi");
                        return new MidiManager(IMidiManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.RADIO_SERVICE,
                RadioManager.class,
                new CachedServiceFetcher<
                        RadioManager>() { // from class: android.app.SystemServiceRegistry.100
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public RadioManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new RadioManager(ctx);
                    }
                });
        registerService(
                Context.HARDWARE_PROPERTIES_SERVICE,
                HardwarePropertiesManager.class,
                new CachedServiceFetcher<HardwarePropertiesManager>() { // from class:
                    // android.app.SystemServiceRegistry.101
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public HardwarePropertiesManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b =
                                ServiceManager.getServiceOrThrow(
                                        Context.HARDWARE_PROPERTIES_SERVICE);
                        IHardwarePropertiesManager service =
                                IHardwarePropertiesManager.Stub.asInterface(b);
                        return new HardwarePropertiesManager(ctx, service);
                    }
                });
        registerService(
                Context.SOUND_TRIGGER_SERVICE,
                SoundTriggerManager.class,
                new CachedServiceFetcher<SoundTriggerManager>() { // from class:
                    // android.app.SystemServiceRegistry.102
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SoundTriggerManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.SOUND_TRIGGER_SERVICE);
                        return new SoundTriggerManager(
                                ctx, ISoundTriggerService.Stub.asInterface(b));
                    }
                });
        registerService(
                "shortcut",
                ShortcutManager.class,
                new CachedServiceFetcher<
                        ShortcutManager>() { // from class: android.app.SystemServiceRegistry.103
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public ShortcutManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow("shortcut");
                        return new ShortcutManager(ctx, IShortcutService.Stub.asInterface(b));
                    }
                });
        registerService(
                "overlay",
                OverlayManager.class,
                new CachedServiceFetcher<
                        OverlayManager>() { // from class: android.app.SystemServiceRegistry.104
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public OverlayManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b;
                        if (Compatibility.isChangeEnabled(OverlayManager.SELF_TARGETING_OVERLAY)) {
                            b = ServiceManager.getService("overlay");
                        } else {
                            b = ServiceManager.getServiceOrThrow("overlay");
                        }
                        return new OverlayManager(ctx, IOverlayManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.NETWORK_WATCHLIST_SERVICE,
                NetworkWatchlistManager.class,
                new CachedServiceFetcher<NetworkWatchlistManager>() { // from class:
                    // android.app.SystemServiceRegistry.105
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public NetworkWatchlistManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b =
                                ServiceManager.getServiceOrThrow(Context.NETWORK_WATCHLIST_SERVICE);
                        return new NetworkWatchlistManager(
                                ctx, INetworkWatchlistManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.SYSTEM_HEALTH_SERVICE,
                SystemHealthManager.class,
                new CachedServiceFetcher<SystemHealthManager>() { // from class:
                    // android.app.SystemServiceRegistry.106
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SystemHealthManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder batteryStats = ServiceManager.getServiceOrThrow("batterystats");
                        IBinder powerStats = ServiceManager.getService(Context.POWER_STATS_SERVICE);
                        return new SystemHealthManager(
                                IBatteryStats.Stub.asInterface(batteryStats),
                                IPowerStatsService.Stub.asInterface(powerStats));
                    }
                });
        registerService(
                Context.CONTEXTHUB_SERVICE,
                ContextHubManager.class,
                new CachedServiceFetcher<
                        ContextHubManager>() { // from class: android.app.SystemServiceRegistry.107
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public ContextHubManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getService(Context.CONTEXTHUB_SERVICE);
                        if (b == null) {
                            return null;
                        }
                        return new ContextHubManager(
                                IContextHubService.Stub.asInterface(b),
                                ctx.mMainThread.getHandler().getLooper());
                    }
                });
        registerService(
                Context.INCIDENT_SERVICE,
                IncidentManager.class,
                new CachedServiceFetcher<
                        IncidentManager>() { // from class: android.app.SystemServiceRegistry.108
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public IncidentManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new IncidentManager(ctx);
                    }
                });
        registerService(
                Context.BUGREPORT_SERVICE,
                BugreportManager.class,
                new CachedServiceFetcher<
                        BugreportManager>() { // from class: android.app.SystemServiceRegistry.109
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public BugreportManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.BUGREPORT_SERVICE);
                        return new BugreportManager(
                                ctx.getOuterContext(), IDumpstate.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.AUTOFILL_MANAGER_SERVICE,
                AutofillManager.class,
                new CachedServiceFetcher<
                        AutofillManager>() { // from class: android.app.SystemServiceRegistry.110
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public AutofillManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getService(Context.AUTOFILL_MANAGER_SERVICE);
                        IAutoFillManager service = IAutoFillManager.Stub.asInterface(b);
                        return new AutofillManager(ctx.getOuterContext(), service);
                    }
                });
        registerService(
                "credential",
                CredentialManager.class,
                new CachedServiceFetcher<
                        CredentialManager>() { // from class: android.app.SystemServiceRegistry.111
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public CredentialManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getService("credential");
                        ICredentialManager service = ICredentialManager.Stub.asInterface(b);
                        if (service != null) {
                            return new CredentialManager(ctx.getOuterContext(), service);
                        }
                        return null;
                    }
                });
        registerService(
                Context.MUSIC_RECOGNITION_SERVICE,
                MusicRecognitionManager.class,
                new CachedServiceFetcher<MusicRecognitionManager>() { // from class:
                    // android.app.SystemServiceRegistry.112
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public MusicRecognitionManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(Context.MUSIC_RECOGNITION_SERVICE);
                        return new MusicRecognitionManager(
                                IMusicRecognitionManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.CONTENT_CAPTURE_MANAGER_SERVICE,
                ContentCaptureManager.class,
                new CachedServiceFetcher<ContentCaptureManager>() { // from class:
                    // android.app.SystemServiceRegistry.113
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public ContentCaptureManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        Context outerContext = ctx.getOuterContext();
                        ContentCaptureOptions options = outerContext.getContentCaptureOptions();
                        if (options == null) {
                            return null;
                        }
                        if (options.lite || options.isWhitelisted(outerContext)) {
                            IBinder b =
                                    ServiceManager.getService(
                                            Context.CONTENT_CAPTURE_MANAGER_SERVICE);
                            IContentCaptureManager service =
                                    IContentCaptureManager.Stub.asInterface(b);
                            if (service != null) {
                                return new ContentCaptureManager(outerContext, service, options);
                            }
                            return null;
                        }
                        return null;
                    }
                });
        registerService(
                Context.TRANSLATION_MANAGER_SERVICE,
                TranslationManager.class,
                new CachedServiceFetcher<
                        TranslationManager>() { // from class: android.app.SystemServiceRegistry.114
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public TranslationManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getService(Context.TRANSLATION_MANAGER_SERVICE);
                        ITranslationManager service = ITranslationManager.Stub.asInterface(b);
                        if (service != null) {
                            return new TranslationManager(ctx.getOuterContext(), service);
                        }
                        return null;
                    }
                });
        registerService(
                Context.UI_TRANSLATION_SERVICE,
                UiTranslationManager.class,
                new CachedServiceFetcher<UiTranslationManager>() { // from class:
                    // android.app.SystemServiceRegistry.115
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public UiTranslationManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getService(Context.TRANSLATION_MANAGER_SERVICE);
                        ITranslationManager service = ITranslationManager.Stub.asInterface(b);
                        if (service != null) {
                            return new UiTranslationManager(ctx.getOuterContext(), service);
                        }
                        return null;
                    }
                });
        registerService(
                Context.SEARCH_UI_SERVICE,
                SearchUiManager.class,
                new CachedServiceFetcher<
                        SearchUiManager>() { // from class: android.app.SystemServiceRegistry.116
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SearchUiManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getService(Context.SEARCH_UI_SERVICE);
                        if (b == null) {
                            return null;
                        }
                        return new SearchUiManager(ctx);
                    }
                });
        registerService(
                Context.SMARTSPACE_SERVICE,
                SmartspaceManager.class,
                new CachedServiceFetcher<
                        SmartspaceManager>() { // from class: android.app.SystemServiceRegistry.117
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SmartspaceManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getService(Context.SMARTSPACE_SERVICE);
                        if (b == null) {
                            return null;
                        }
                        return new SmartspaceManager(ctx);
                    }
                });
        registerService(
                Context.CONTEXTUAL_SEARCH_SERVICE,
                ContextualSearchManager.class,
                new CachedServiceFetcher<ContextualSearchManager>() { // from class:
                    // android.app.SystemServiceRegistry.118
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public ContextualSearchManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getService(Context.CONTEXTUAL_SEARCH_SERVICE);
                        if (b == null) {
                            return null;
                        }
                        return new ContextualSearchManager();
                    }
                });
        registerService(
                Context.APP_PREDICTION_SERVICE,
                AppPredictionManager.class,
                new CachedServiceFetcher<AppPredictionManager>() { // from class:
                    // android.app.SystemServiceRegistry.119
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public AppPredictionManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getService(Context.APP_PREDICTION_SERVICE);
                        if (b == null) {
                            return null;
                        }
                        return new AppPredictionManager(ctx);
                    }
                });
        registerService(
                Context.CONTENT_SUGGESTIONS_SERVICE,
                ContentSuggestionsManager.class,
                new CachedServiceFetcher<ContentSuggestionsManager>() { // from class:
                    // android.app.SystemServiceRegistry.120
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public ContentSuggestionsManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(Context.CONTENT_SUGGESTIONS_SERVICE);
                        IContentSuggestionsManager service =
                                IContentSuggestionsManager.Stub.asInterface(b);
                        return new ContentSuggestionsManager(ctx.getUserId(), service);
                    }
                });
        registerService(
                Context.WALLPAPER_EFFECTS_GENERATION_SERVICE,
                WallpaperEffectsGenerationManager.class,
                new CachedServiceFetcher<WallpaperEffectsGenerationManager>() { // from class:
                    // android.app.SystemServiceRegistry.121
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public WallpaperEffectsGenerationManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b =
                                ServiceManager.getService(
                                        Context.WALLPAPER_EFFECTS_GENERATION_SERVICE);
                        if (b == null) {
                            return null;
                        }
                        return new WallpaperEffectsGenerationManager(
                                IWallpaperEffectsGenerationManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.VR_SERVICE,
                VrManager.class,
                new CachedServiceFetcher<
                        VrManager>() { // from class: android.app.SystemServiceRegistry.122
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public VrManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.VR_SERVICE);
                        return new VrManager(IVrManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.CROSS_PROFILE_APPS_SERVICE,
                CrossProfileApps.class,
                new CachedServiceFetcher<
                        CrossProfileApps>() { // from class: android.app.SystemServiceRegistry.123
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public CrossProfileApps createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b =
                                ServiceManager.getServiceOrThrow(
                                        Context.CROSS_PROFILE_APPS_SERVICE);
                        return new CrossProfileApps(
                                ctx.getOuterContext(), ICrossProfileApps.Stub.asInterface(b));
                    }
                });
        registerService(
                "slice",
                SliceManager.class,
                new CachedServiceFetcher<
                        SliceManager>() { // from class: android.app.SystemServiceRegistry.124
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SliceManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new SliceManager(
                                ctx.getOuterContext(), ctx.mMainThread.getHandler());
                    }
                });
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_MCF_SUPPORT_CONTINUITY")
                > 0) {
            registerService(
                    Context.SEM_CONTINUITY_SERVICE,
                    SemContinuityManager.class,
                    new CachedServiceFetcher<SemContinuityManager>() { // from class:
                        // android.app.SystemServiceRegistry.125
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                        public SemContinuityManager createService(ContextImpl ctx)
                                throws ServiceManager.ServiceNotFoundException {
                            IBinder binder =
                                    ServiceManager.getServiceOrThrow(
                                            Context.SEM_CONTINUITY_SERVICE);
                            ISemContinuityManager service =
                                    ISemContinuityManager.Stub.asInterface(binder);
                            return new SemContinuityManager(
                                    ctx.getOuterContext(), service, ctx.getUserId());
                        }
                    });
        }
        if (!SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI", false)
                && SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_COMMON_CONFIG_AI_VERSION", -1)
                        >= 20251) {
            registerService(
                    Context.SEM_CONTEXT_ENGINE_SERVICE,
                    SemContextEngineManager.class,
                    new CachedServiceFetcher<SemContextEngineManager>() { // from class:
                        // android.app.SystemServiceRegistry.126
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                        public SemContextEngineManager createService(ContextImpl ctx)
                                throws ServiceManager.ServiceNotFoundException {
                            IBinder binder =
                                    ServiceManager.getServiceOrThrow(
                                            Context.SEM_CONTEXT_ENGINE_SERVICE);
                            ISemContextEngineManager service =
                                    ISemContextEngineManager.Stub.asInterface(binder);
                            return new SemContextEngineManager(
                                    ctx.getOuterContext(), service, ctx.getUserId());
                        }
                    });
        }
        try {
            if (AudioTag.TAG_CAMERA.contains(AudioTag.TAG_CAMERA)
                    || AudioTag.TAG_CAMERA.contains("STORAGE")) {
                registerService(
                        Context.SEM_HWRS_SERVICE,
                        SemHwrsManager.class,
                        new CachedServiceFetcher<SemHwrsManager>() { // from class:
                            // android.app.SystemServiceRegistry.127
                            /* JADX WARN: Can't rename method to resolve collision */
                            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                            public SemHwrsManager createService(ContextImpl ctx)
                                    throws ServiceManager.ServiceNotFoundException {
                                IBinder binder =
                                        ServiceManager.getServiceOrThrow(Context.SEM_HWRS_SERVICE);
                                ISemHwrsManager service = ISemHwrsManager.Stub.asInterface(binder);
                                return new SemHwrsManager(
                                        ctx.getOuterContext(), service, ctx.getUserId());
                            }
                        });
            }
        } catch (Exception e) {
            Slog.e(TAG, "SemHwrsManager registerService failed", e);
        }
        registerService(
                Context.SEM_MOTION_RECOGNITION_SERVICE,
                SemMotionRecognitionManager.class,
                new CachedServiceFetcher<SemMotionRecognitionManager>() { // from class:
                    // android.app.SystemServiceRegistry.128
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemMotionRecognitionManager createService(ContextImpl ctx) {
                        return new SemMotionRecognitionManager(
                                ctx.mMainThread.getHandler().getLooper());
                    }
                });
        registerService(
                "scontext",
                SContextManager.class,
                new CachedServiceFetcher<
                        SContextManager>() { // from class: android.app.SystemServiceRegistry.129
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SContextManager createService(ContextImpl ctx) {
                        return new SContextManager(ctx, ctx.mMainThread.getHandler().getLooper());
                    }
                });
        String fmradioChipVendor =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_CHIP_VENDOR");
        if (fmradioChipVendor.length() > 0 && Integer.parseInt(fmradioChipVendor) > 0) {
            registerService(
                    Context.SEM_FM_RADIO_SERVICE,
                    SemFmPlayer.class,
                    new CachedServiceFetcher<
                            SemFmPlayer>() { // from class: android.app.SystemServiceRegistry.130
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                        public SemFmPlayer createService(ContextImpl ctx) {
                            return new SemFmPlayer(ctx);
                        }
                    });
        }
        registerService(
                Context.SEM_MULTI_CONTROL_SERVICE,
                SemMultiControlManager.class,
                new CachedServiceFetcher<SemMultiControlManager>() { // from class:
                    // android.app.SystemServiceRegistry.131
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemMultiControlManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(Context.SEM_MULTI_CONTROL_SERVICE);
                        IMultiControlManager service = IMultiControlManager.Stub.asInterface(b);
                        if (service == null) {
                            Log.e(
                                    SystemServiceRegistry.TAG,
                                    "SemMultiControlManager is not supported");
                            return null;
                        }
                        return new SemMultiControlManager(ctx.getOuterContext(), service);
                    }
                });
        registerService(
                Context.SEM_REMOTE_APP_MODE_SERVICE,
                SemRemoteAppModeManager.class,
                new CachedServiceFetcher<SemRemoteAppModeManager>() { // from class:
                    // android.app.SystemServiceRegistry.132
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemRemoteAppModeManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(Context.SEM_REMOTE_APP_MODE_SERVICE);
                        IRemoteAppMode service = IRemoteAppMode.Stub.asInterface(b);
                        if (service == null) {
                            Log.e(
                                    SystemServiceRegistry.TAG,
                                    "SemRemoteAppModeManager is not supported");
                            return null;
                        }
                        return new SemRemoteAppModeManager(ctx.getOuterContext(), service);
                    }
                });
        if (SemDynamicFeature.isSuitable()) {
            registerService(
                    SemDynamicFeature.SERVICE_NAME,
                    DynamicFeatureManager.class,
                    new CachedServiceFetcher<DynamicFeatureManager>() { // from class:
                        // android.app.SystemServiceRegistry.133
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                        public DynamicFeatureManager createService(ContextImpl ctx) {
                            IBinder b = ServiceManager.getService(SemDynamicFeature.SERVICE_NAME);
                            IDynamicFeatureManager service =
                                    IDynamicFeatureManager.Stub.asInterface(b);
                            if (service == null) {
                                Log.e(
                                        SystemServiceRegistry.TAG,
                                        "IDynamicFeatureManager is not supported");
                                return null;
                            }
                            return new DynamicFeatureManager(service);
                        }
                    });
        }
        registerService(
                "time_detector",
                TimeDetector.class,
                new CachedServiceFetcher<
                        TimeDetector>() { // from class: android.app.SystemServiceRegistry.134
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public TimeDetector createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new TimeDetectorImpl();
                    }
                });
        registerService(
                "time_zone_detector",
                TimeZoneDetector.class,
                new CachedServiceFetcher<
                        TimeZoneDetector>() { // from class: android.app.SystemServiceRegistry.135
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public TimeZoneDetector createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new TimeZoneDetectorImpl();
                    }
                });
        registerService(
                Context.TIME_MANAGER_SERVICE,
                TimeManager.class,
                new CachedServiceFetcher<
                        TimeManager>() { // from class: android.app.SystemServiceRegistry.136
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public TimeManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new TimeManager();
                    }
                });
        registerService(
                "permission",
                PermissionManager.class,
                new CachedServiceFetcher<
                        PermissionManager>() { // from class: android.app.SystemServiceRegistry.137
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public PermissionManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new PermissionManager(ctx.getOuterContext());
                    }
                });
        registerService(
                Context.LEGACY_PERMISSION_SERVICE,
                LegacyPermissionManager.class,
                new CachedServiceFetcher<LegacyPermissionManager>() { // from class:
                    // android.app.SystemServiceRegistry.138
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public LegacyPermissionManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new LegacyPermissionManager();
                    }
                });
        registerService(
                Context.PERMISSION_CONTROLLER_SERVICE,
                PermissionControllerManager.class,
                new CachedServiceFetcher<PermissionControllerManager>() { // from class:
                    // android.app.SystemServiceRegistry.139
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public PermissionControllerManager createService(ContextImpl ctx) {
                        return new PermissionControllerManager(
                                ctx.getOuterContext(), ctx.getMainThreadHandler());
                    }
                });
        registerService(
                Context.PERMISSION_CHECKER_SERVICE,
                PermissionCheckerManager.class,
                new CachedServiceFetcher<PermissionCheckerManager>() { // from class:
                    // android.app.SystemServiceRegistry.140
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public PermissionCheckerManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new PermissionCheckerManager(ctx.getOuterContext());
                    }
                });
        registerService(
                Context.PERMISSION_ENFORCER_SERVICE,
                PermissionEnforcer.class,
                new CachedServiceFetcher<
                        PermissionEnforcer>() { // from class: android.app.SystemServiceRegistry.141
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public PermissionEnforcer createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new PermissionEnforcer(ctx.getOuterContext());
                    }
                });
        registerService(
                Context.DYNAMIC_SYSTEM_SERVICE,
                DynamicSystemManager.class,
                new CachedServiceFetcher<DynamicSystemManager>() { // from class:
                    // android.app.SystemServiceRegistry.142
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public DynamicSystemManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b =
                                ServiceManager.getServiceOrThrow(Context.DYNAMIC_SYSTEM_SERVICE);
                        return new DynamicSystemManager(IDynamicSystemService.Stub.asInterface(b));
                    }
                });
        registerService(
                "batterystats",
                BatteryStatsManager.class,
                new CachedServiceFetcher<BatteryStatsManager>() { // from class:
                    // android.app.SystemServiceRegistry.143
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public BatteryStatsManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow("batterystats");
                        return new BatteryStatsManager(IBatteryStats.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.DATA_LOADER_MANAGER_SERVICE,
                DataLoaderManager.class,
                new CachedServiceFetcher<
                        DataLoaderManager>() { // from class: android.app.SystemServiceRegistry.144
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public DataLoaderManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b =
                                ServiceManager.getServiceOrThrow(
                                        Context.DATA_LOADER_MANAGER_SERVICE);
                        return new DataLoaderManager(IDataLoaderManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.LIGHTS_SERVICE,
                LightsManager.class,
                new CachedServiceFetcher<
                        LightsManager>() { // from class: android.app.SystemServiceRegistry.145
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public LightsManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new SystemLightsManager(ctx);
                    }
                });
        registerService(
                "locale",
                LocaleManager.class,
                new CachedServiceFetcher<
                        LocaleManager>() { // from class: android.app.SystemServiceRegistry.146
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public LocaleManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new LocaleManager(
                                ctx,
                                ILocaleManager.Stub.asInterface(
                                        ServiceManager.getServiceOrThrow("locale")));
                    }
                });
        registerService(
                Context.INCREMENTAL_SERVICE,
                IncrementalManager.class,
                new CachedServiceFetcher<
                        IncrementalManager>() { // from class: android.app.SystemServiceRegistry.147
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public IncrementalManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(Context.INCREMENTAL_SERVICE);
                        if (b == null) {
                            return null;
                        }
                        return new IncrementalManager(IIncrementalService.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.FILE_INTEGRITY_SERVICE,
                FileIntegrityManager.class,
                new CachedServiceFetcher<FileIntegrityManager>() { // from class:
                    // android.app.SystemServiceRegistry.148
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public FileIntegrityManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b =
                                ServiceManager.getServiceOrThrow(Context.FILE_INTEGRITY_SERVICE);
                        return new FileIntegrityManager(
                                ctx.getOuterContext(), IFileIntegrityService.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.ATTESTATION_VERIFICATION_SERVICE,
                AttestationVerificationManager.class,
                new CachedServiceFetcher<AttestationVerificationManager>() { // from class:
                    // android.app.SystemServiceRegistry.149
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public AttestationVerificationManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b =
                                ServiceManager.getServiceOrThrow(
                                        Context.ATTESTATION_VERIFICATION_SERVICE);
                        return new AttestationVerificationManager(
                                ctx.getOuterContext(),
                                IAttestationVerificationManagerService.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.APP_INTEGRITY_SERVICE,
                AppIntegrityManager.class,
                new CachedServiceFetcher<AppIntegrityManager>() { // from class:
                    // android.app.SystemServiceRegistry.150
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public AppIntegrityManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder b = ServiceManager.getServiceOrThrow(Context.APP_INTEGRITY_SERVICE);
                        return new AppIntegrityManager(IAppIntegrityManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.APP_HIBERNATION_SERVICE,
                AppHibernationManager.class,
                new CachedServiceFetcher<AppHibernationManager>() { // from class:
                    // android.app.SystemServiceRegistry.151
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public AppHibernationManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(Context.APP_HIBERNATION_SERVICE);
                        if (b == null) {
                            return null;
                        }
                        return new AppHibernationManager(ctx);
                    }
                });
        registerService(
                "dream",
                DreamManager.class,
                new CachedServiceFetcher<
                        DreamManager>() { // from class: android.app.SystemServiceRegistry.152
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public DreamManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new DreamManager(ctx);
                    }
                });
        registerService(
                Context.DEVICE_STATE_SERVICE,
                DeviceStateManager.class,
                new CachedServiceFetcher<
                        DeviceStateManager>() { // from class: android.app.SystemServiceRegistry.153
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public DeviceStateManager createService(ContextImpl ctx) {
                        return new DeviceStateManager();
                    }
                });
        registerService(
                Context.COCKTAIL_BAR_SERVICE,
                CocktailBarManager.class,
                new CachedServiceFetcher<
                        CocktailBarManager>() { // from class: android.app.SystemServiceRegistry.154
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public CocktailBarManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(Context.COCKTAIL_BAR_SERVICE);
                        return new CocktailBarManager(ctx, ICocktailBarService.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.MEDIA_METRICS_SERVICE,
                MediaMetricsManager.class,
                new CachedServiceFetcher<MediaMetricsManager>() { // from class:
                    // android.app.SystemServiceRegistry.155
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public MediaMetricsManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder iBinder =
                                ServiceManager.getServiceOrThrow(Context.MEDIA_METRICS_SERVICE);
                        IMediaMetricsManager service =
                                IMediaMetricsManager.Stub.asInterface(iBinder);
                        return new MediaMetricsManager(service, ctx.getUserId());
                    }
                });
        registerService(
                Context.GAME_SERVICE,
                GameManager.class,
                new CachedServiceFetcher<
                        GameManager>() { // from class: android.app.SystemServiceRegistry.156
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public GameManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new GameManager(ctx.getOuterContext(), ctx.mMainThread.getHandler());
                    }
                });
        registerService(
                Context.DOMAIN_VERIFICATION_SERVICE,
                DomainVerificationManager.class,
                new CachedServiceFetcher<DomainVerificationManager>() { // from class:
                    // android.app.SystemServiceRegistry.157
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public DomainVerificationManager createService(ContextImpl context)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder binder =
                                ServiceManager.getServiceOrThrow(
                                        Context.DOMAIN_VERIFICATION_SERVICE);
                        IDomainVerificationManager service =
                                IDomainVerificationManager.Stub.asInterface(binder);
                        return new DomainVerificationManager(context, service);
                    }
                });
        registerService(
                Context.DISPLAY_HASH_SERVICE,
                DisplayHashManager.class,
                new CachedServiceFetcher<
                        DisplayHashManager>() { // from class: android.app.SystemServiceRegistry.158
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public DisplayHashManager createService(ContextImpl ctx) {
                        return new DisplayHashManager();
                    }
                });
        boolean isExynosDisplaySolutionService = false;
        try {
            isExynosDisplaySolutionService =
                    Resources.getSystem()
                            .getBoolean(R.bool.config_enableExynosDisplaySolutionService);
        } catch (Exception e2) {
            Slog.e(TAG, "Not starting ExynosDisplaySolutionService", e2);
        }
        if (isExynosDisplaySolutionService) {
            registerService(
                    Context.EXYNOS_DISPLAY_SOLUTION_SERVICE,
                    ExynosDisplaySolutionManager.class,
                    new CachedServiceFetcher<ExynosDisplaySolutionManager>() { // from class:
                        // android.app.SystemServiceRegistry.159
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                        public ExynosDisplaySolutionManager createService(ContextImpl ctx) {
                            IBinder b =
                                    ServiceManager.getService(
                                            Context.EXYNOS_DISPLAY_SOLUTION_SERVICE);
                            IExynosDisplaySolutionManager service =
                                    IExynosDisplaySolutionManager.Stub.asInterface(b);
                            if (service == null) {
                                Log.e(
                                        SystemServiceRegistry.TAG,
                                        "Failed to get ExynosDisplaySolution Manager Service.");
                                return null;
                            }
                            return new ExynosDisplaySolutionManager(service);
                        }
                    });
        }
        registerService(
                Context.AMBIENT_CONTEXT_SERVICE,
                AmbientContextManager.class,
                new CachedServiceFetcher<AmbientContextManager>() { // from class:
                    // android.app.SystemServiceRegistry.160
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public AmbientContextManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder iBinder =
                                ServiceManager.getServiceOrThrow(Context.AMBIENT_CONTEXT_SERVICE);
                        IAmbientContextManager manager =
                                IAmbientContextManager.Stub.asInterface(iBinder);
                        return new AmbientContextManager(ctx.getOuterContext(), manager);
                    }
                });
        registerService(
                Context.WEARABLE_SENSING_SERVICE,
                WearableSensingManager.class,
                new CachedServiceFetcher<WearableSensingManager>() { // from class:
                    // android.app.SystemServiceRegistry.161
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public WearableSensingManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder iBinder =
                                ServiceManager.getServiceOrThrow(Context.WEARABLE_SENSING_SERVICE);
                        IWearableSensingManager manager =
                                IWearableSensingManager.Stub.asInterface(iBinder);
                        return new WearableSensingManager(ctx.getOuterContext(), manager);
                    }
                });
        registerService(
                Context.ON_DEVICE_INTELLIGENCE_SERVICE,
                OnDeviceIntelligenceManager.class,
                new CachedServiceFetcher<OnDeviceIntelligenceManager>() { // from class:
                    // android.app.SystemServiceRegistry.162
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public OnDeviceIntelligenceManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IBinder iBinder =
                                ServiceManager.getServiceOrThrow(
                                        Context.ON_DEVICE_INTELLIGENCE_SERVICE);
                        IOnDeviceIntelligenceManager manager =
                                IOnDeviceIntelligenceManager.Stub.asInterface(iBinder);
                        return new OnDeviceIntelligenceManager(ctx.getOuterContext(), manager);
                    }
                });
        registerService(
                Context.GRAMMATICAL_INFLECTION_SERVICE,
                GrammaticalInflectionManager.class,
                new CachedServiceFetcher<GrammaticalInflectionManager>() { // from class:
                    // android.app.SystemServiceRegistry.163
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public GrammaticalInflectionManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        return new GrammaticalInflectionManager(
                                ctx,
                                IGrammaticalInflectionManager.Stub.asInterface(
                                        ServiceManager.getServiceOrThrow(
                                                Context.GRAMMATICAL_INFLECTION_SERVICE)));
                    }
                });
        registerService(
                Context.SEM_MDNIE_SERVICE,
                SemMdnieManager.class,
                new CachedServiceFetcher<
                        SemMdnieManager>() { // from class: android.app.SystemServiceRegistry.164
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemMdnieManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(Context.SEM_MDNIE_SERVICE);
                        ISemMdnieManager service = ISemMdnieManager.Stub.asInterface(b);
                        return new SemMdnieManager(service);
                    }
                });
        registerService(
                Context.SEM_DISPLAY_SOLUTION_SERVICE,
                SemDisplaySolutionManager.class,
                new CachedServiceFetcher<SemDisplaySolutionManager>() { // from class:
                    // android.app.SystemServiceRegistry.165
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemDisplaySolutionManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(Context.SEM_DISPLAY_SOLUTION_SERVICE);
                        ISemDisplaySolutionManager service =
                                ISemDisplaySolutionManager.Stub.asInterface(b);
                        return new SemDisplaySolutionManager(service);
                    }
                });
        registerService(
                "persona",
                SemPersonaManager.class,
                new CachedServiceFetcher<
                        SemPersonaManager>() { // from class: android.app.SystemServiceRegistry.166
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemPersonaManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService("persona");
                        ISemPersonaManager service = ISemPersonaManager.Stub.asInterface(b);
                        return new SemPersonaManager(ctx, service);
                    }
                });
        registerService(
                "rcp",
                SemRemoteContentManager.class,
                new CachedServiceFetcher<SemRemoteContentManager>() { // from class:
                    // android.app.SystemServiceRegistry.167
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemRemoteContentManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService("rcp");
                        return new SemRemoteContentManager(
                                ISemRemoteContentManager.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.ISRB_MANAGER_SERVICE,
                IsrbManager.class,
                new CachedServiceFetcher<
                        IsrbManager>() { // from class: android.app.SystemServiceRegistry.168
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public IsrbManager createService(ContextImpl ctx) {
                        return new IsrbManager(ctx);
                    }
                });
        registerService(
                Context.SHARED_CONNECTIVITY_SERVICE,
                SharedConnectivityManager.class,
                new CachedServiceFetcher<SharedConnectivityManager>() { // from class:
                    // android.app.SystemServiceRegistry.169
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SharedConnectivityManager createService(ContextImpl ctx) {
                        return SharedConnectivityManager.create(ctx);
                    }
                });
        registerService(
                KMTDManager.SERVICE_LABEL,
                KMTDManager.class,
                new CachedServiceFetcher<
                        KMTDManager>() { // from class: android.app.SystemServiceRegistry.170
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public KMTDManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(KMTDManager.SERVICE_LABEL);
                        IMTDService service = IMTDService.Stub.asInterface(b);
                        if (service == null) {
                            Log.e(SystemServiceRegistry.TAG, "Failed to get MTDService");
                            return null;
                        }
                        return new KMTDManager(service, ctx.getOuterContext());
                    }
                });
        registerService(
                Context.CONTACT_KEYS_SERVICE,
                E2eeContactKeysManager.class,
                new CachedServiceFetcher<E2eeContactKeysManager>() { // from class:
                    // android.app.SystemServiceRegistry.171
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public E2eeContactKeysManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        if (!com.android.internal.hidden_from_bootclasspath.android.provider.Flags
                                .userKeys()) {
                            throw new ServiceManager.ServiceNotFoundException(
                                    "ContactKeysManager is not supported");
                        }
                        return new E2eeContactKeysManager(ctx);
                    }
                });
        if (Flags.bicClient()) {
            registerService(
                    Context.BACKGROUND_INSTALL_CONTROL_SERVICE,
                    BackgroundInstallControlManager.class,
                    new CachedServiceFetcher<BackgroundInstallControlManager>() { // from class:
                        // android.app.SystemServiceRegistry.172
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                        public BackgroundInstallControlManager createService(ContextImpl ctx) {
                            return new BackgroundInstallControlManager(ctx);
                        }
                    });
        }
        registerService(
                Context.SEM_MDCONTEXT_SERVICE,
                SemMdContextManager.class,
                new CachedServiceFetcher<SemMdContextManager>() { // from class:
                    // android.app.SystemServiceRegistry.173
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemMdContextManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        Log.i(SystemServiceRegistry.TAG, "create SemMdContextManager service");
                        IBinder b = ServiceManager.getService(Context.SEM_MDCONTEXT_SERVICE);
                        if (b == null) {
                            Log.e(
                                    SystemServiceRegistry.TAG,
                                    "MOCCA is not supported on this device");
                            return null;
                        }
                        return new SemMdContextManager(IMoccaService.Stub.asInterface(b));
                    }
                });
        registerService(
                Context.SEM_INPUT_DEVICE_SERVICE,
                SemInputDeviceManager.class,
                new CachedServiceFetcher<SemInputDeviceManager>() { // from class:
                    // android.app.SystemServiceRegistry.174
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemInputDeviceManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(Context.SEM_INPUT_DEVICE_SERVICE);
                        ISemInputDeviceManager service = ISemInputDeviceManager.Stub.asInterface(b);
                        return new SemInputDeviceManager(service);
                    }
                });
        registerService(
                Context.SEM_WIFI_SERVICE,
                SemWifiManager.class,
                new CachedServiceFetcher<
                        SemWifiManager>() { // from class: android.app.SystemServiceRegistry.175
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemWifiManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        ISemWifiManager service =
                                ISemWifiManager.Stub.asInterface(
                                        ServiceManager.getServiceOrThrow(Context.SEM_WIFI_SERVICE));
                        return new SemWifiManager(
                                ctx.getOuterContext(),
                                service,
                                ctx.mMainThread.getHandler().getLooper());
                    }
                });
        registerService(
                Context.SEM_WIFI_P2P_SERVICE,
                SemWifiP2pManager.class,
                new CachedServiceFetcher<
                        SemWifiP2pManager>() { // from class: android.app.SystemServiceRegistry.176
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemWifiP2pManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        ISemWifiP2pManager service =
                                ISemWifiP2pManager.Stub.asInterface(
                                        ServiceManager.getServiceOrThrow(
                                                Context.SEM_WIFI_P2P_SERVICE));
                        return new SemWifiP2pManager(ctx.getOuterContext(), service);
                    }
                });
        registerService(
                Context.SEM_WIFI_AWARE_SERVICE,
                SemWifiAwareManager.class,
                new CachedServiceFetcher<SemWifiAwareManager>() { // from class:
                    // android.app.SystemServiceRegistry.177
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemWifiAwareManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        ISemWifiAwareManager service =
                                ISemWifiAwareManager.Stub.asInterface(
                                        ServiceManager.getServiceOrThrow(
                                                Context.SEM_WIFI_AWARE_SERVICE));
                        return new SemWifiAwareManager(ctx.getOuterContext(), service);
                    }
                });
        registerService(
                Context.STANDARD_PLUS_SERVICE,
                StandardPlusManager.class,
                new CachedServiceFetcher<StandardPlusManager>() { // from class:
                    // android.app.SystemServiceRegistry.178
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public StandardPlusManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IStandardPlusManager service =
                                IStandardPlusManager.Stub.asInterface(
                                        ServiceManager.getServiceOrThrow(
                                                Context.STANDARD_PLUS_SERVICE));
                        return new StandardPlusManager(ctx.getOuterContext(), service);
                    }
                });
        Log.e(
                TAG,
                "SemDisplayQualityFeature.ENABLED:"
                        + SemDisplayQualityFeature.ENABLED
                        + ",PLATFORM:"
                        + SemDisplayQualityFeature.PLATFORM);
        if (SemDisplayQualityFeature.ENABLED) {
            registerService(
                    Context.SEM_DISPLAY_QUALITY_SERVICE,
                    SemDisplayQualityManager.class,
                    new CachedServiceFetcher<SemDisplayQualityManager>() { // from class:
                        // android.app.SystemServiceRegistry.179
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                        public SemDisplayQualityManager createService(ContextImpl ctx) {
                            IBinder b =
                                    ServiceManager.getService(Context.SEM_DISPLAY_QUALITY_SERVICE);
                            ISemDisplayQualityManager service =
                                    ISemDisplayQualityManager.Stub.asInterface(b);
                            return new SemDisplayQualityManager(service);
                        }
                    });
        }
        registerService(
                Context.SEM_VIDEO_TRANSCODING_SERVICE,
                SemVideoTranscodingService.class,
                new CachedServiceFetcher<SemVideoTranscodingService>() { // from class:
                    // android.app.SystemServiceRegistry.180
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemVideoTranscodingService createService(ContextImpl ctx) {
                        return new SemVideoTranscodingService();
                    }
                });
        registerService(
                Context.SEM_DESKTOP_MODE_SERVICE,
                SemDesktopModeManager.class,
                new CachedServiceFetcher<SemDesktopModeManager>() { // from class:
                    // android.app.SystemServiceRegistry.181
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemDesktopModeManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(Context.SEM_DESKTOP_MODE_SERVICE);
                        IDesktopMode service = IDesktopMode.Stub.asInterface(b);
                        if (service == null) {
                            Log.e(
                                    SystemServiceRegistry.TAG,
                                    "SemDesktopModeManager is not supported");
                            return null;
                        }
                        return new SemDesktopModeManager(ctx.getOuterContext(), service);
                    }
                });
        if (UnionUtils.FEATURE_ENABLED) {
            registerService(
                    Context.SEP_UNION_SERVICE,
                    SemUnionManager.class,
                    new CachedServiceFetcher<SemUnionManager>() { // from class:
                        // android.app.SystemServiceRegistry.182
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                        public SemUnionManager createService(ContextImpl ctx) {
                            IBinder b = ServiceManager.getService(Context.SEP_UNION_SERVICE);
                            return new SemUnionManager(ctx, IUnionManager.Stub.asInterface(b));
                        }
                    });
        }
        registerService(
                Context.CODEC_SOLUTION_SERVICE,
                SemCodecSolutionService.class,
                new CachedServiceFetcher<SemCodecSolutionService>() { // from class:
                    // android.app.SystemServiceRegistry.183
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemCodecSolutionService createService(ContextImpl ctx) {
                        return new SemCodecSolutionService();
                    }
                });
        registerService(
                Context.EXTENDED_ETHERNET_SERVICE,
                ExtendedEthernetManager.class,
                new CachedServiceFetcher<ExtendedEthernetManager>() { // from class:
                    // android.app.SystemServiceRegistry.184
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public ExtendedEthernetManager createService(ContextImpl ctx)
                            throws ServiceManager.ServiceNotFoundException {
                        IExtendedEthernetManager service =
                                IExtendedEthernetManager.Stub.asInterface(
                                        ServiceManager.getServiceOrThrow(
                                                Context.EXTENDED_ETHERNET_SERVICE));
                        return new ExtendedEthernetManager(service);
                    }
                });
        registerService(
                Context.SEM_SPEN_GESTURE_SERVICE,
                SpenGestureManager.class,
                new CachedServiceFetcher<
                        SpenGestureManager>() { // from class: android.app.SystemServiceRegistry.185
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SpenGestureManager createService(ContextImpl ctx) {
                        return new SpenGestureManager(ctx);
                    }
                });
        registerService(
                Context.SEM_SSDID_SERVICE,
                SemSsdidManager.class,
                new CachedServiceFetcher<
                        SemSsdidManager>() { // from class: android.app.SystemServiceRegistry.186
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public SemSsdidManager createService(ContextImpl ctx) {
                        IBinder b = ServiceManager.getService(Context.SEM_SSDID_SERVICE);
                        ISemSsdidManagerService service =
                                ISemSsdidManagerService.Stub.asInterface(b);
                        return new SemSsdidManager(ctx, service);
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
            DeviceLockFrameworkInitializer.registerServiceWrappers();
            VirtualizationFrameworkInitializer.registerServiceWrappers();
            if (com.android.server.telecom.flags.Flags.telecomMainlineBlockedNumbersManager()) {
                ProviderFrameworkInitializer.registerServiceWrappers();
            }
            if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags
                    .enhancedConfirmationModeApisEnabled()) {
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
        } finally {
            sInitializing = false;
        }
    }

    private SystemServiceRegistry() {}

    /* renamed from: android.app.SystemServiceRegistry$22, reason: invalid class name */
    class AnonymousClass22 extends CachedServiceFetcher<TetheringManager> {
        AnonymousClass22() {}

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
        public TetheringManager createService(ContextImpl ctx) {
            return new TetheringManager(
                    ctx,
                    new Supplier() { // from class:
                        // android.app.SystemServiceRegistry$22$$ExternalSyntheticLambda0
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            IBinder service;
                            service = ServiceManager.getService(Context.TETHERING_SERVICE);
                            return service;
                        }
                    });
        }
    }

    private static void ensureInitializing(String methodName) {
        Preconditions.checkState(
                sInitializing,
                "Internal error: %s can only be called during class initialization.",
                methodName);
    }

    public static Object[] createServiceCache() {
        return new Object[sServiceCacheSize];
    }

    private static ServiceFetcher<?> getSystemServiceFetcher(String name) {
        if (name == null) {
            return null;
        }
        ServiceFetcher<?> fetcher = SYSTEM_SERVICE_FETCHERS.get(name);
        if (fetcher == null) {
            if (sEnableServiceNotFoundWtf) {
                Slog.wtf(TAG, "Unknown manager requested: " + name);
            }
            return null;
        }
        return fetcher;
    }

    private static boolean hasSystemFeatureOpportunistic(ContextImpl ctx, String featureName) {
        PackageManager manager = ctx.getPackageManager();
        if (manager == null) {
            return true;
        }
        return manager.hasSystemFeature(featureName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getVcnFeatureDependency() {
        if (!Compatibility.isChangeEnabled(ENABLE_CHECKING_TELEPHONY_FEATURES_FOR_VCN)) {
            return null;
        }
        int vendorApiLevel =
                SystemProperties.getInt(
                        "ro.vendor.api_level", Build.VERSION.DEVICE_INITIAL_SDK_INT);
        if (vendorApiLevel < 202404) {
            return PackageManager.FEATURE_TELEPHONY;
        }
        return PackageManager.FEATURE_TELEPHONY_SUBSCRIPTION;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static Object getSystemService(ContextImpl ctx, String name) {
        char c;
        ServiceFetcher<?> fetcher = getSystemServiceFetcher(name);
        if (fetcher == null) {
            return null;
        }
        Object ret = fetcher.getService(ctx);
        if (sEnableServiceNotFoundWtf && ret == null) {
            switch (name.hashCode()) {
                case -1775044699:
                    if (name.equals(Context.APPWIDGET_SERVICE)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -1419358249:
                    if (name.equals(Context.ETHERNET_SERVICE)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -906336856:
                    if (name.equals("search")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -769002131:
                    if (name.equals(Context.APP_PREDICTION_SERVICE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 56472313:
                    if (name.equals(Context.VIRTUALIZATION_SERVICE)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 273796326:
                    if (name.equals(Context.CONTEXTHUB_SERVICE)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 948275489:
                    if (name.equals(Context.VIRTUAL_DEVICE_SERVICE)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 974854528:
                    if (name.equals(Context.CONTENT_CAPTURE_MANAGER_SERVICE)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1085372378:
                    if (name.equals(Context.INCREMENTAL_SERVICE)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2094026721:
                    if (name.equals(Context.VCN_MANAGEMENT_SERVICE)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    return null;
                case 7:
                    if (!hasSystemFeatureOpportunistic(
                            ctx, PackageManager.FEATURE_TELEPHONY_SUBSCRIPTION)) {
                        return null;
                    }
                    break;
                case '\b':
                    if (hasSystemFeatureOpportunistic(ctx, PackageManager.FEATURE_WATCH)) {
                        return null;
                    }
                    break;
                case '\t':
                    if (!hasSystemFeatureOpportunistic(ctx, PackageManager.FEATURE_APP_WIDGETS)) {
                        return null;
                    }
                    break;
            }
            Slog.wtf(TAG, "Manager wrapper not available: " + name);
            return null;
        }
        return ret;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static Object getSystemServiceWithNoContext(String name) {
        ServiceFetcher<?> fetcher = getSystemServiceFetcher(name);
        if (fetcher == null) {
            return null;
        }
        if (!fetcher.supportsFetchWithoutContext()) {
            throw new IllegalArgumentException(
                    "Manager cannot be fetched without a context: " + name);
        }
        return fetcher.getService(null);
    }

    public static String getSystemServiceName(Class<?> serviceClass) {
        if (serviceClass == null) {
            return null;
        }
        String serviceName = SYSTEM_SERVICE_NAMES.get(serviceClass);
        if (sEnableServiceNotFoundWtf && serviceName == null) {
            Slog.wtf(TAG, "Unknown manager requested: " + serviceClass.getCanonicalName());
        }
        return serviceName;
    }

    private static <T> void registerService(
            String serviceName, Class<T> serviceClass, ServiceFetcher<T> serviceFetcher) {
        SYSTEM_SERVICE_NAMES.put(serviceClass, serviceName);
        SYSTEM_SERVICE_FETCHERS.put(serviceName, serviceFetcher);
        SYSTEM_SERVICE_CLASS_NAMES.put(serviceName, serviceClass.getSimpleName());
    }

    public static String getSystemServiceClassName(String name) {
        return SYSTEM_SERVICE_CLASS_NAMES.get(name);
    }

    @SystemApi
    public static <TServiceClass> void registerStaticService(
            final String serviceName,
            Class<TServiceClass> serviceWrapperClass,
            final StaticServiceProducerWithBinder<TServiceClass> serviceProducer) {
        ensureInitializing("registerStaticService");
        Preconditions.checkStringNotEmpty(serviceName);
        Objects.requireNonNull(serviceWrapperClass);
        Objects.requireNonNull(serviceProducer);
        registerService(
                serviceName,
                serviceWrapperClass,
                new StaticServiceFetcher<
                        TServiceClass>() { // from class: android.app.SystemServiceRegistry.187
                    @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
                    public TServiceClass createService()
                            throws ServiceManager.ServiceNotFoundException {
                        return (TServiceClass)
                                StaticServiceProducerWithBinder.this.createService(
                                        ServiceManager.getServiceOrThrow(serviceName));
                    }
                });
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static <TServiceClass> void registerForeverStaticService(
            final String serviceName,
            Class<TServiceClass> serviceWrapperClass,
            final StaticServiceProducerWithBinder<TServiceClass> serviceProducer) {
        ensureInitializing("registerStaticService");
        Preconditions.checkStringNotEmpty(serviceName);
        Objects.requireNonNull(serviceWrapperClass);
        Objects.requireNonNull(serviceProducer);
        registerService(
                serviceName,
                serviceWrapperClass,
                new StaticServiceFetcher<
                        TServiceClass>() { // from class: android.app.SystemServiceRegistry.188
                    @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
                    public TServiceClass createService()
                            throws ServiceManager.ServiceNotFoundException {
                        return (TServiceClass)
                                StaticServiceProducerWithBinder.this.createService(
                                        ServiceManager.getServiceOrThrow(serviceName));
                    }

                    @Override // android.app.SystemServiceRegistry.StaticServiceFetcher,
                    // android.app.SystemServiceRegistry.ServiceFetcher
                    public boolean supportsFetchWithoutContext() {
                        return true;
                    }
                });
    }

    @SystemApi
    public static <TServiceClass> void registerStaticService(
            String serviceName,
            Class<TServiceClass> serviceWrapperClass,
            final StaticServiceProducerWithoutBinder<TServiceClass> serviceProducer) {
        ensureInitializing("registerStaticService");
        Preconditions.checkStringNotEmpty(serviceName);
        Objects.requireNonNull(serviceWrapperClass);
        Objects.requireNonNull(serviceProducer);
        registerService(
                serviceName,
                serviceWrapperClass,
                new StaticServiceFetcher<
                        TServiceClass>() { // from class: android.app.SystemServiceRegistry.189
                    @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
                    public TServiceClass createService() {
                        return (TServiceClass)
                                StaticServiceProducerWithoutBinder.this.createService();
                    }
                });
    }

    @SystemApi
    public static <TServiceClass> void registerContextAwareService(
            final String serviceName,
            Class<TServiceClass> serviceWrapperClass,
            final ContextAwareServiceProducerWithBinder<TServiceClass> serviceProducer) {
        ensureInitializing("registerContextAwareService");
        Preconditions.checkStringNotEmpty(serviceName);
        Objects.requireNonNull(serviceWrapperClass);
        Objects.requireNonNull(serviceProducer);
        registerService(
                serviceName,
                serviceWrapperClass,
                new CachedServiceFetcher<
                        TServiceClass>() { // from class: android.app.SystemServiceRegistry.190
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public TServiceClass createService(ContextImpl contextImpl)
                            throws ServiceManager.ServiceNotFoundException {
                        return (TServiceClass)
                                ContextAwareServiceProducerWithBinder.this.createService(
                                        contextImpl.getOuterContext(),
                                        ServiceManager.getServiceOrThrow(serviceName));
                    }
                });
    }

    @SystemApi
    public static <TServiceClass> void registerContextAwareService(
            String serviceName,
            Class<TServiceClass> serviceWrapperClass,
            final ContextAwareServiceProducerWithoutBinder<TServiceClass> serviceProducer) {
        ensureInitializing("registerContextAwareService");
        Preconditions.checkStringNotEmpty(serviceName);
        Objects.requireNonNull(serviceWrapperClass);
        Objects.requireNonNull(serviceProducer);
        registerService(
                serviceName,
                serviceWrapperClass,
                new CachedServiceFetcher<
                        TServiceClass>() { // from class: android.app.SystemServiceRegistry.191
                    @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                    public TServiceClass createService(ContextImpl contextImpl) {
                        return (TServiceClass)
                                ContextAwareServiceProducerWithoutBinder.this.createService(
                                        contextImpl.getOuterContext());
                    }
                });
    }

    interface ServiceFetcher<T> {
        T getService(ContextImpl contextImpl);

        default boolean supportsFetchWithoutContext() {
            return false;
        }
    }

    abstract static class CachedServiceFetcher<T> implements ServiceFetcher<T> {
        private final int mCacheIndex;

        public abstract T createService(ContextImpl contextImpl)
                throws ServiceManager.ServiceNotFoundException;

        CachedServiceFetcher() {
            int i = SystemServiceRegistry.sServiceCacheSize;
            SystemServiceRegistry.sServiceCacheSize = i + 1;
            this.mCacheIndex = i;
        }

        /* JADX WARN: Code restructure failed: missing block: B:78:0x000e, code lost:

           r3 = r5;
        */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Object, java.lang.Object[]] */
        /* JADX WARN: Type inference failed for: r5v2 */
        @Override // android.app.SystemServiceRegistry.ServiceFetcher
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final T getService(android.app.ContextImpl r10) {
            /*
                Method dump skipped, instructions count: 164
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.app.SystemServiceRegistry.CachedServiceFetcher.getService(android.app.ContextImpl):java.lang.Object");
        }

        @Override // android.app.SystemServiceRegistry.ServiceFetcher
        public final boolean supportsFetchWithoutContext() {
            return false;
        }
    }

    abstract static class StaticServiceFetcher<T> implements ServiceFetcher<T> {
        private T mCachedInstance;

        public abstract T createService() throws ServiceManager.ServiceNotFoundException;

        StaticServiceFetcher() {}

        @Override // android.app.SystemServiceRegistry.ServiceFetcher
        public final T getService(ContextImpl ctx) {
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

        @Override // android.app.SystemServiceRegistry.ServiceFetcher
        public boolean supportsFetchWithoutContext() {
            return false;
        }
    }

    public static void onServiceNotFound(ServiceManager.ServiceNotFoundException e) {
        if (Process.myUid() < 10000) {
            Log.wtf(TAG, e.getMessage(), e);
        } else {
            Log.w(TAG, e.getMessage());
        }
    }
}
