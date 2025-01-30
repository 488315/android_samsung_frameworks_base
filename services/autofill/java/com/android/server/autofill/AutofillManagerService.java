package com.android.server.autofill;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.ActivityThread;
import android.content.AutofillOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.service.autofill.FillEventHistory;
import android.service.autofill.UserData;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.LocalLog;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.TimeUtils;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillManagerInternal;
import android.view.autofill.AutofillValue;
import android.view.autofill.IAutoFillManager;
import android.view.autofill.IAutoFillManagerClient;
import com.android.internal.infra.GlobalWhitelistState;
import com.android.internal.infra.WhitelistHelper;
import com.android.internal.os.IResultReceiver;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.SyncResultReceiver;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.autofill.p008ui.AutoFillUI;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;
import com.android.server.infra.SecureSettingsServiceNameResolver;
import com.android.server.infra.ServiceNameResolver;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class AutofillManagerService extends AbstractMasterSystemService {
  public static final Object sLock = AutofillManagerService.class;
  public static int sPartitionMaxCount = 10;
  public static int sVisibleDatasetsMaxCount;
  public final ActivityManagerInternal mAm;
  public final FrameworkResourcesServiceNameResolver mAugmentedAutofillResolver;
  public final AugmentedAutofillState mAugmentedAutofillState;
  public int mAugmentedServiceIdleUnbindTimeoutMs;
  public int mAugmentedServiceRequestTimeoutMs;
  public final AutofillCompatState mAutofillCompatState;
  public final BroadcastReceiver mBroadcastReceiver;
  public final DisabledInfoCache mDisabledInfoCache;
  public final FrameworkResourcesServiceNameResolver mFieldClassificationResolver;
  public final Object mFlagLock;
  public final LocalService mLocalService;
  public boolean mPccClassificationEnabled;
  public boolean mPccPreferProviderOverPcc;
  public String mPccProviderHints;
  public boolean mPccUseFallbackDetection;
  public final LocalLog mRequestsHistory;
  public int mSupportedSmartSuggestionModes;
  public final AutoFillUI mUi;
  public final LocalLog mUiLatencyHistory;
  public final LocalLog mWtfHistory;

  @Override // com.android.server.infra.AbstractMasterSystemService
  public String getServiceSettingsProperty() {
    return "autofill_service";
  }

  /* renamed from: com.android.server.autofill.AutofillManagerService$1 */
  public class C07661 extends BroadcastReceiver {
    public C07661() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
        if (Helper.sDebug) {
          Slog.d("AutofillManagerService", "Close system dialogs");
        }
        synchronized (AutofillManagerService.this.mLock) {
          AutofillManagerService.this.visitServicesLocked(
              new AbstractMasterSystemService.Visitor() { // from class:
                // com.android.server.autofill.AutofillManagerService$1$$ExternalSyntheticLambda0
                @Override // com.android.server.infra.AbstractMasterSystemService.Visitor
                public final void visit(Object obj) {
                  ((AutofillManagerServiceImpl) obj).forceRemoveFinishedSessionsLocked();
                }
              });
        }
        AutofillManagerService.this.mUi.hideAll(null);
      }
    }
  }

  public AutofillManagerService(Context context) {
    super(
        context,
        new SecureSettingsServiceNameResolver(context, "autofill_service"),
        "no_autofill",
        4);
    this.mRequestsHistory = new LocalLog(20);
    this.mUiLatencyHistory = new LocalLog(20);
    this.mWtfHistory = new LocalLog(50);
    this.mAutofillCompatState = new AutofillCompatState();
    this.mDisabledInfoCache = new DisabledInfoCache();
    this.mLocalService = new LocalService();
    C07661 c07661 = new C07661();
    this.mBroadcastReceiver = c07661;
    this.mAugmentedAutofillState = new AugmentedAutofillState();
    this.mFlagLock = new Object();
    this.mUi = new AutoFillUI(ActivityThread.currentActivityThread().getSystemUiContext());
    this.mAm = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
    DeviceConfig.addOnPropertiesChangedListener(
        "autofill",
        ActivityThread.currentApplication().getMainExecutor(),
        new DeviceConfig.OnPropertiesChangedListener() { // from class:
          // com.android.server.autofill.AutofillManagerService$$ExternalSyntheticLambda0
          public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            AutofillManagerService.this.lambda$new$0(properties);
          }
        });
    setLogLevelFromSettings();
    setMaxPartitionsFromSettings();
    setMaxVisibleDatasetsFromSettings();
    setDeviceConfigProperties();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
    context.registerReceiver(c07661, intentFilter, null, FgThread.getHandler(), 2);
    FrameworkResourcesServiceNameResolver frameworkResourcesServiceNameResolver =
        new FrameworkResourcesServiceNameResolver(
            getContext(), R.string.data_usage_mobile_limit_title);
    this.mAugmentedAutofillResolver = frameworkResourcesServiceNameResolver;
    frameworkResourcesServiceNameResolver.setOnTemporaryServiceNameChangedCallback(
        new ServiceNameResolver.NameResolverListener() { // from class:
          // com.android.server.autofill.AutofillManagerService$$ExternalSyntheticLambda1
          @Override // com.android.server.infra.ServiceNameResolver.NameResolverListener
          public final void onNameResolved(int i, String str, boolean z) {
            AutofillManagerService.this.lambda$new$1(i, str, z);
          }
        });
    FrameworkResourcesServiceNameResolver frameworkResourcesServiceNameResolver2 =
        new FrameworkResourcesServiceNameResolver(
            getContext(), R.string.date_picker_decrement_day_button);
    this.mFieldClassificationResolver = frameworkResourcesServiceNameResolver2;
    if (Helper.sVerbose) {
      Slog.v(
          "AutofillManagerService",
          "Resolving FieldClassificationService to serviceName: "
              + frameworkResourcesServiceNameResolver2.readServiceName(0));
    }
    frameworkResourcesServiceNameResolver2.setOnTemporaryServiceNameChangedCallback(
        new ServiceNameResolver.NameResolverListener() { // from class:
          // com.android.server.autofill.AutofillManagerService$$ExternalSyntheticLambda2
          @Override // com.android.server.infra.ServiceNameResolver.NameResolverListener
          public final void onNameResolved(int i, String str, boolean z) {
            AutofillManagerService.this.lambda$new$2(i, str, z);
          }
        });
    if (this.mSupportedSmartSuggestionModes != 0) {
      List supportedUsers = getSupportedUsers();
      for (int i = 0; i < supportedUsers.size(); i++) {
        int i2 = ((UserInfo) supportedUsers.get(i)).id;
        getServiceForUserLocked(i2);
        this.mAugmentedAutofillState.setServiceInfo(
            i2,
            this.mAugmentedAutofillResolver.getServiceName(i2),
            this.mAugmentedAutofillResolver.isTemporary(i2));
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0(DeviceConfig.Properties properties) {
    onDeviceConfigChange(properties.getKeyset());
  }

  @Override // com.android.server.infra.AbstractMasterSystemService
  public void registerForExtraSettingsChanges(
      ContentResolver contentResolver, ContentObserver contentObserver) {
    contentResolver.registerContentObserver(
        Settings.Global.getUriFor("autofill_logging_level"), false, contentObserver, -1);
    contentResolver.registerContentObserver(
        Settings.Global.getUriFor("autofill_max_partitions_size"), false, contentObserver, -1);
    contentResolver.registerContentObserver(
        Settings.Global.getUriFor("autofill_max_visible_datasets"), false, contentObserver, -1);
    contentResolver.registerContentObserver(
        Settings.Secure.getUriFor("selected_input_method_subtype"), false, contentObserver, -1);
  }

  @Override // com.android.server.infra.AbstractMasterSystemService
  public void onSettingsChanged(int i, String str) {
    str.hashCode();
    switch (str) {
      case "autofill_max_visible_datasets":
        setMaxVisibleDatasetsFromSettings();
        return;
      case "autofill_logging_level":
        setLogLevelFromSettings();
        return;
      case "autofill_max_partitions_size":
        setMaxPartitionsFromSettings();
        return;
      case "selected_input_method_subtype":
        handleInputMethodSwitch(i);
        return;
      default:
        Slog.w(
            "AutofillManagerService", "Unexpected property (" + str + "); updating cache instead");
        synchronized (this.mLock) {
          updateCachedServiceLocked(i);
        }
        return;
    }
  }

  public final void handleInputMethodSwitch(int i) {
    synchronized (this.mLock) {
      AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
          peekServiceForUserWithLocalBinderIdentityLocked(i);
      if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
        peekServiceForUserWithLocalBinderIdentityLocked.onSwitchInputMethod();
      }
    }
  }

  public final void onDeviceConfigChange(Set set) {
    String str;
    Iterator it = set.iterator();
    while (it.hasNext()) {
      str = (String) it.next();
      str.hashCode();
      switch (str) {
        case "pcc_classification_enabled":
        case "prefer_provider_over_pcc":
        case "augmented_service_idle_unbind_timeout":
        case "augmented_service_request_timeout":
        case "pcc_classification_hints":
        case "pcc_use_fallback":
        case "smart_suggestion_supported_modes":
          setDeviceConfigProperties();
          break;
        case "compat_mode_allowed_packages":
          updateCachedServices();
          break;
        default:
          Slog.i(this.mTag, "Ignoring change on " + str);
          break;
      }
    }
  }

  /* renamed from: onAugmentedServiceNameChanged, reason: merged with bridge method [inline-methods] */
  public final void lambda$new$1(int i, String str, boolean z) {
    this.mAugmentedAutofillState.setServiceInfo(i, str, z);
    synchronized (this.mLock) {
      AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
          peekServiceForUserWithLocalBinderIdentityLocked(i);
      if (peekServiceForUserWithLocalBinderIdentityLocked == null) {
        getServiceForUserWithLocalBinderIdentityLocked(i);
      } else {
        peekServiceForUserWithLocalBinderIdentityLocked.updateRemoteAugmentedAutofillService();
      }
    }
  }

  /* renamed from: onFieldClassificationServiceNameChanged, reason: merged with bridge method [inline-methods] */
  public final void lambda$new$2(int i, String str, boolean z) {
    synchronized (this.mLock) {
      AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
          peekServiceForUserWithLocalBinderIdentityLocked(i);
      if (peekServiceForUserWithLocalBinderIdentityLocked == null) {
        getServiceForUserWithLocalBinderIdentityLocked(i);
      } else {
        peekServiceForUserWithLocalBinderIdentityLocked.updateRemoteFieldClassificationService();
      }
    }
  }

  public final AutofillManagerServiceImpl getServiceForUserWithLocalBinderIdentityLocked(int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return (AutofillManagerServiceImpl) getServiceForUserLocked(i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked(int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return (AutofillManagerServiceImpl) peekServiceForUserLocked(i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  @Override // com.android.server.infra.AbstractMasterSystemService
  public AutofillManagerServiceImpl newServiceLocked(int i, boolean z) {
    return new AutofillManagerServiceImpl(
        this,
        this.mLock,
        this.mUiLatencyHistory,
        this.mWtfHistory,
        i,
        this.mUi,
        this.mAutofillCompatState,
        z,
        this.mDisabledInfoCache);
  }

  @Override // com.android.server.infra.AbstractMasterSystemService
  public void onServiceRemoved(AutofillManagerServiceImpl autofillManagerServiceImpl, int i) {
    autofillManagerServiceImpl.destroyLocked();
    this.mDisabledInfoCache.remove(i);
    this.mAutofillCompatState.removeCompatibilityModeRequests(i);
  }

  @Override // com.android.server.infra.AbstractMasterSystemService
  public void onServiceEnabledLocked(AutofillManagerServiceImpl autofillManagerServiceImpl, int i) {
    addCompatibilityModeRequestsLocked(autofillManagerServiceImpl, i);
  }

  @Override // com.android.server.infra.AbstractMasterSystemService
  public void enforceCallingPermissionForManagement() {
    getContext()
        .enforceCallingPermission("android.permission.MANAGE_AUTO_FILL", "AutofillManagerService");
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    publishBinderService("autofill", new AutoFillManagerServiceStub());
    publishLocalService(AutofillManagerInternal.class, this.mLocalService);
  }

  @Override // com.android.server.SystemService
  public boolean isUserSupported(SystemService.TargetUser targetUser) {
    return targetUser.isFull() || targetUser.isProfile();
  }

  @Override // com.android.server.SystemService
  public void onUserSwitching(
      SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
    if (Helper.sDebug) {
      Slog.d("AutofillManagerService", "Hiding UI when user switched");
    }
    this.mUi.hideAll(null);
  }

  public int getSupportedSmartSuggestionModesLocked() {
    return this.mSupportedSmartSuggestionModes;
  }

  public void logRequestLocked(String str) {
    this.mRequestsHistory.log(str);
  }

  public boolean isInstantServiceAllowed() {
    return this.mAllowInstantService;
  }

  public void removeAllSessions(int i, IResultReceiver iResultReceiver) {
    Slog.i("AutofillManagerService", "removeAllSessions() for userId " + i);
    enforceCallingPermissionForManagement();
    synchronized (this.mLock) {
      if (i != -1) {
        AutofillManagerServiceImpl autofillManagerServiceImpl =
            (AutofillManagerServiceImpl) peekServiceForUserLocked(i);
        if (autofillManagerServiceImpl != null) {
          autofillManagerServiceImpl.forceRemoveAllSessionsLocked();
        }
      } else {
        visitServicesLocked(
            new AbstractMasterSystemService.Visitor() { // from class:
              // com.android.server.autofill.AutofillManagerService$$ExternalSyntheticLambda4
              @Override // com.android.server.infra.AbstractMasterSystemService.Visitor
              public final void visit(Object obj) {
                ((AutofillManagerServiceImpl) obj).forceRemoveAllSessionsLocked();
              }
            });
      }
    }
    try {
      iResultReceiver.send(0, new Bundle());
    } catch (RemoteException unused) {
    }
  }

  public void listSessions(int i, IResultReceiver iResultReceiver) {
    Slog.i("AutofillManagerService", "listSessions() for userId " + i);
    enforceCallingPermissionForManagement();
    Bundle bundle = new Bundle();
    final ArrayList<String> arrayList = new ArrayList<>();
    synchronized (this.mLock) {
      if (i != -1) {
        AutofillManagerServiceImpl autofillManagerServiceImpl =
            (AutofillManagerServiceImpl) peekServiceForUserLocked(i);
        if (autofillManagerServiceImpl != null) {
          autofillManagerServiceImpl.listSessionsLocked(arrayList);
        }
      } else {
        visitServicesLocked(
            new AbstractMasterSystemService.Visitor() { // from class:
              // com.android.server.autofill.AutofillManagerService$$ExternalSyntheticLambda5
              @Override // com.android.server.infra.AbstractMasterSystemService.Visitor
              public final void visit(Object obj) {
                ((AutofillManagerServiceImpl) obj).listSessionsLocked(arrayList);
              }
            });
      }
    }
    bundle.putStringArrayList("sessions", arrayList);
    try {
      iResultReceiver.send(0, bundle);
    } catch (RemoteException unused) {
    }
  }

  public void reset() {
    Slog.i("AutofillManagerService", "reset()");
    enforceCallingPermissionForManagement();
    synchronized (this.mLock) {
      visitServicesLocked(
          new AbstractMasterSystemService.Visitor() { // from class:
            // com.android.server.autofill.AutofillManagerService$$ExternalSyntheticLambda3
            @Override // com.android.server.infra.AbstractMasterSystemService.Visitor
            public final void visit(Object obj) {
              ((AutofillManagerServiceImpl) obj).destroyLocked();
            }
          });
      clearCacheLocked();
    }
  }

  public void setLogLevel(int i) {
    Slog.i("AutofillManagerService", "setLogLevel(): " + i);
    enforceCallingPermissionForManagement();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      Settings.Global.putInt(getContext().getContentResolver(), "autofill_logging_level", i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:11:0x0068 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void setLogLevelFromSettings() {
    boolean z;
    int i =
        Settings.Global.getInt(
            getContext().getContentResolver(),
            "autofill_logging_level",
            AutofillManager.DEFAULT_LOGGING_LEVEL);
    boolean z2 = false;
    if (i != 0) {
      z = true;
      if (i == 4) {
        z2 = true;
      } else if (i == 2) {
        z = false;
        z2 = true;
      } else {
        Slog.w("AutofillManagerService", "setLogLevelFromSettings(): invalid level: " + i);
      }
      if (!z2 || Helper.sDebug) {
        Slog.d(
            "AutofillManagerService",
            "setLogLevelFromSettings(): level=" + i + ", debug=" + z2 + ", verbose=" + z);
      }
      synchronized (this.mLock) {
        setLoggingLevelsLocked(z2, z);
      }
      return;
    }
    z = false;
    if (!z2) {}
    Slog.d(
        "AutofillManagerService",
        "setLogLevelFromSettings(): level=" + i + ", debug=" + z2 + ", verbose=" + z);
    synchronized (this.mLock) {
    }
  }

  public int getLogLevel() {
    enforceCallingPermissionForManagement();
    synchronized (this.mLock) {
      if (Helper.sVerbose) {
        return 4;
      }
      return Helper.sDebug ? 2 : 0;
    }
  }

  public int getMaxPartitions() {
    int i;
    enforceCallingPermissionForManagement();
    synchronized (this.mLock) {
      i = sPartitionMaxCount;
    }
    return i;
  }

  public void setMaxPartitions(int i) {
    Slog.i("AutofillManagerService", "setMaxPartitions(): " + i);
    enforceCallingPermissionForManagement();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      Settings.Global.putInt(getContext().getContentResolver(), "autofill_max_partitions_size", i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void setMaxPartitionsFromSettings() {
    int i =
        Settings.Global.getInt(
            getContext().getContentResolver(), "autofill_max_partitions_size", 10);
    if (Helper.sDebug) {
      Slog.d("AutofillManagerService", "setMaxPartitionsFromSettings(): " + i);
    }
    synchronized (sLock) {
      sPartitionMaxCount = i;
    }
  }

  public int getMaxVisibleDatasets() {
    int i;
    enforceCallingPermissionForManagement();
    synchronized (sLock) {
      i = sVisibleDatasetsMaxCount;
    }
    return i;
  }

  public void setMaxVisibleDatasets(int i) {
    Slog.i("AutofillManagerService", "setMaxVisibleDatasets(): " + i);
    enforceCallingPermissionForManagement();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      Settings.Global.putInt(getContext().getContentResolver(), "autofill_max_visible_datasets", i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void setMaxVisibleDatasetsFromSettings() {
    int i =
        Settings.Global.getInt(
            getContext().getContentResolver(), "autofill_max_visible_datasets", 0);
    if (Helper.sDebug) {
      Slog.d("AutofillManagerService", "setMaxVisibleDatasetsFromSettings(): " + i);
    }
    synchronized (sLock) {
      sVisibleDatasetsMaxCount = i;
    }
  }

  public final void setDeviceConfigProperties() {
    synchronized (this.mLock) {
      this.mAugmentedServiceIdleUnbindTimeoutMs =
          DeviceConfig.getInt("autofill", "augmented_service_idle_unbind_timeout", 0);
      this.mAugmentedServiceRequestTimeoutMs =
          DeviceConfig.getInt("autofill", "augmented_service_request_timeout", 5000);
      this.mSupportedSmartSuggestionModes =
          DeviceConfig.getInt("autofill", "smart_suggestion_supported_modes", 1);
      this.mAugmentedServiceIdleUnbindTimeoutMs = 60000;
      if (this.verbose) {
        Slog.v(
            this.mTag,
            "setDeviceConfigProperties() for AugmentedAutofill: augmentedIdleTimeout="
                + this.mAugmentedServiceIdleUnbindTimeoutMs
                + ", augmentedRequestTimeout="
                + this.mAugmentedServiceRequestTimeoutMs
                + ", smartSuggestionMode="
                + AutofillManager.getSmartSuggestionModeToString(
                    this.mSupportedSmartSuggestionModes));
      }
    }
    synchronized (this.mFlagLock) {
      this.mPccClassificationEnabled =
          DeviceConfig.getBoolean("autofill", "pcc_classification_enabled", false);
      this.mPccPreferProviderOverPcc =
          DeviceConfig.getBoolean("autofill", "prefer_provider_over_pcc", true);
      this.mPccUseFallbackDetection = DeviceConfig.getBoolean("autofill", "pcc_use_fallback", true);
      this.mPccProviderHints = DeviceConfig.getString("autofill", "pcc_classification_hints", "");
      if (this.verbose) {
        Slog.v(
            this.mTag,
            "setDeviceConfigProperties() for PCC: mPccClassificationEnabled="
                + this.mPccClassificationEnabled
                + ", mPccPreferProviderOverPcc="
                + this.mPccPreferProviderOverPcc
                + ", mPccUseFallbackDetection="
                + this.mPccUseFallbackDetection
                + ", mPccProviderHints="
                + this.mPccProviderHints);
      }
    }
  }

  public final void updateCachedServices() {
    for (UserInfo userInfo : getSupportedUsers()) {
      synchronized (this.mLock) {
        updateCachedServiceLocked(userInfo.id);
      }
    }
  }

  public void calculateScore(String str, String str2, String str3, RemoteCallback remoteCallback) {
    enforceCallingPermissionForManagement();
    new FieldClassificationStrategy(getContext(), -2)
        .calculateScores(
            remoteCallback,
            Arrays.asList(AutofillValue.forText(str2)),
            new String[] {str3},
            new String[] {null},
            str,
            null,
            null,
            null);
  }

  public Boolean getFullScreenMode() {
    enforceCallingPermissionForManagement();
    return Helper.sFullScreenMode;
  }

  public void setFullScreenMode(Boolean bool) {
    enforceCallingPermissionForManagement();
    Helper.sFullScreenMode = bool;
  }

  public void setTemporaryAugmentedAutofillService(int i, String str, int i2) {
    Slog.i(
        this.mTag,
        "setTemporaryAugmentedAutofillService(" + i + ") to " + str + " for " + i2 + "ms");
    enforceCallingPermissionForManagement();
    Objects.requireNonNull(str);
    if (i2 > 120000) {
      throw new IllegalArgumentException("Max duration is 120000 (called with " + i2 + ")");
    }
    this.mAugmentedAutofillResolver.setTemporaryService(i, str, i2);
  }

  public void resetTemporaryAugmentedAutofillService(int i) {
    enforceCallingPermissionForManagement();
    this.mAugmentedAutofillResolver.resetTemporaryService(i);
  }

  public boolean isDefaultAugmentedServiceEnabled(int i) {
    enforceCallingPermissionForManagement();
    return this.mAugmentedAutofillResolver.isDefaultServiceEnabled(i);
  }

  public boolean setDefaultAugmentedServiceEnabled(int i, boolean z) {
    Slog.i(this.mTag, "setDefaultAugmentedServiceEnabled() for userId " + i + ": " + z);
    enforceCallingPermissionForManagement();
    synchronized (this.mLock) {
      AutofillManagerServiceImpl autofillManagerServiceImpl =
          (AutofillManagerServiceImpl) getServiceForUserLocked(i);
      if (autofillManagerServiceImpl != null) {
        if (this.mAugmentedAutofillResolver.setDefaultServiceEnabled(i, z)) {
          autofillManagerServiceImpl.updateRemoteAugmentedAutofillService();
          return true;
        }
        if (this.debug) {
          Slog.d("AutofillManagerService", "setDefaultAugmentedServiceEnabled(): already " + z);
        }
      }
      return false;
    }
  }

  public boolean isFieldDetectionServiceEnabledForUser(int i) {
    enforceCallingPermissionForManagement();
    synchronized (this.mLock) {
      AutofillManagerServiceImpl autofillManagerServiceImpl =
          (AutofillManagerServiceImpl) getServiceForUserLocked(i);
      if (autofillManagerServiceImpl == null) {
        return false;
      }
      return autofillManagerServiceImpl.isPccClassificationEnabled();
    }
  }

  public boolean setTemporaryDetectionService(int i, String str, int i2) {
    Slog.i(this.mTag, "setTemporaryDetectionService(" + i + ") to " + str + " for " + i2 + "ms");
    enforceCallingPermissionForManagement();
    Objects.requireNonNull(str);
    this.mFieldClassificationResolver.setTemporaryService(i, str, i2);
    return false;
  }

  public void resetTemporaryDetectionService(int i) {
    enforceCallingPermissionForManagement();
    this.mFieldClassificationResolver.resetTemporaryService(i);
  }

  public boolean requestSavedPasswordCount(int i, IResultReceiver iResultReceiver) {
    enforceCallingPermissionForManagement();
    synchronized (this.mLock) {
      AutofillManagerServiceImpl autofillManagerServiceImpl =
          (AutofillManagerServiceImpl) peekServiceForUserLocked(i);
      if (autofillManagerServiceImpl != null) {
        autofillManagerServiceImpl.requestSavedPasswordCount(iResultReceiver);
        return true;
      }
      if (Helper.sVerbose) {
        Slog.v("AutofillManagerService", "requestSavedPasswordCount(): no service for " + i);
      }
      return false;
    }
  }

  public final void setLoggingLevelsLocked(boolean z, boolean z2) {
    Helper.sDebug = z;
    android.view.autofill.Helper.sDebug = z;
    this.debug = z;
    Helper.sVerbose = z2;
    android.view.autofill.Helper.sVerbose = z2;
    this.verbose = z2;
  }

  public final void addCompatibilityModeRequestsLocked(
      AutofillManagerServiceImpl autofillManagerServiceImpl, int i) {
    this.mAutofillCompatState.reset(i);
    ArrayMap compatibilityPackagesLocked =
        autofillManagerServiceImpl.getCompatibilityPackagesLocked();
    if (compatibilityPackagesLocked == null || compatibilityPackagesLocked.isEmpty()) {
      return;
    }
    Map allowedCompatModePackages = getAllowedCompatModePackages();
    int size = compatibilityPackagesLocked.size();
    for (int i2 = 0; i2 < size; i2++) {
      String str = (String) compatibilityPackagesLocked.keyAt(i2);
      if (allowedCompatModePackages == null || !allowedCompatModePackages.containsKey(str)) {
        Slog.w("AutofillManagerService", "Ignoring not allowed compat package " + str);
      } else {
        Long l = (Long) compatibilityPackagesLocked.valueAt(i2);
        if (l != null) {
          this.mAutofillCompatState.addCompatibilityModeRequest(
              str, l.longValue(), (String[]) allowedCompatModePackages.get(str), i);
        }
      }
    }
  }

  public final String getAllowedCompatModePackagesFromDeviceConfig() {
    String string =
        DeviceConfig.getString("autofill", "compat_mode_allowed_packages", (String) null);
    return !TextUtils.isEmpty(string) ? string : getAllowedCompatModePackagesFromSettings();
  }

  public final String getAllowedCompatModePackagesFromSettings() {
    return Settings.Global.getString(
        getContext().getContentResolver(), "autofill_compat_mode_allowed_packages");
  }

  public final Map getAllowedCompatModePackages() {
    return getAllowedCompatModePackages(getAllowedCompatModePackagesFromDeviceConfig());
  }

  public final void send(IResultReceiver iResultReceiver, int i) {
    try {
      iResultReceiver.send(i, (Bundle) null);
    } catch (RemoteException e) {
      Slog.w("AutofillManagerService", "Error async reporting result to client: " + e);
    }
  }

  public final void send(IResultReceiver iResultReceiver, Bundle bundle) {
    try {
      iResultReceiver.send(0, bundle);
    } catch (RemoteException e) {
      Slog.w("AutofillManagerService", "Error async reporting result to client: " + e);
    }
  }

  public final void send(IResultReceiver iResultReceiver, String str) {
    send(iResultReceiver, SyncResultReceiver.bundleFor(str));
  }

  public final void send(IResultReceiver iResultReceiver, String[] strArr) {
    send(iResultReceiver, SyncResultReceiver.bundleFor(strArr));
  }

  public final void send(IResultReceiver iResultReceiver, Parcelable parcelable) {
    send(iResultReceiver, SyncResultReceiver.bundleFor(parcelable));
  }

  public final void send(IResultReceiver iResultReceiver, boolean z) {
    send(iResultReceiver, z ? 1 : 0);
  }

  public final void send(IResultReceiver iResultReceiver, int i, int i2) {
    try {
      iResultReceiver.send(i, SyncResultReceiver.bundleFor(i2));
    } catch (RemoteException e) {
      Slog.w("AutofillManagerService", "Error async reporting result to client: " + e);
    }
  }

  public boolean isPccClassificationFlagEnabled() {
    boolean z;
    synchronized (this.mFlagLock) {
      z = this.mPccClassificationEnabled;
    }
    return z;
  }

  public boolean preferProviderOverPcc() {
    boolean z;
    synchronized (this.mFlagLock) {
      z = this.mPccPreferProviderOverPcc;
    }
    return z;
  }

  public boolean shouldUsePccFallback() {
    boolean z;
    synchronized (this.mFlagLock) {
      z = this.mPccUseFallbackDetection;
    }
    return z;
  }

  public String getPccProviderHints() {
    String str;
    synchronized (this.mFlagLock) {
      str = this.mPccProviderHints;
    }
    return str;
  }

  public static Map getAllowedCompatModePackages(String str) {
    ArrayList arrayList;
    if (TextUtils.isEmpty(str)) {
      return null;
    }
    ArrayMap arrayMap = new ArrayMap();
    TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(':');
    simpleStringSplitter.setString(str);
    while (simpleStringSplitter.hasNext()) {
      String next = simpleStringSplitter.next();
      int indexOf = next.indexOf(91);
      if (indexOf == -1) {
        arrayList = null;
      } else if (next.charAt(next.length() - 1) != ']') {
        Slog.w(
            "AutofillManagerService",
            "Ignoring entry '" + next + "' on '" + str + "'because it does not end on ']'");
      } else {
        String substring = next.substring(0, indexOf);
        arrayList = new ArrayList();
        String substring2 = next.substring(indexOf + 1, next.length() - 1);
        if (Helper.sVerbose) {
          Slog.v(
              "AutofillManagerService",
              "pkg:"
                  + substring
                  + ": block:"
                  + next
                  + ": urls:"
                  + arrayList
                  + ": block:"
                  + substring2
                  + XmlUtils.STRING_ARRAY_SEPARATOR);
        }
        TextUtils.SimpleStringSplitter simpleStringSplitter2 =
            new TextUtils.SimpleStringSplitter(',');
        simpleStringSplitter2.setString(substring2);
        while (simpleStringSplitter2.hasNext()) {
          arrayList.add(simpleStringSplitter2.next());
        }
        next = substring;
      }
      if (arrayList == null) {
        arrayMap.put(next, null);
      } else {
        String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        arrayMap.put(next, strArr);
      }
    }
    return arrayMap;
  }

  public static int getPartitionMaxCount() {
    int i;
    synchronized (sLock) {
      i = sPartitionMaxCount;
    }
    return i;
  }

  public static int getVisibleDatasetsMaxCount() {
    int i;
    synchronized (sLock) {
      i = sVisibleDatasetsMaxCount;
    }
    return i;
  }

  public final class LocalService extends AutofillManagerInternal {
    public LocalService() {}

    public void onBackKeyPressed() {
      if (Helper.sDebug) {
        Slog.d("AutofillManagerService", "onBackKeyPressed()");
      }
      AutofillManagerService.this.mUi.hideAll(null);
      synchronized (AutofillManagerService.this.mLock) {
        AutofillManagerService.this
            .getServiceForUserWithLocalBinderIdentityLocked(UserHandle.getCallingUserId())
            .onBackKeyPressed();
      }
    }

    public AutofillOptions getAutofillOptions(String str, long j, int i) {
      int i2;
      AutofillManagerService autofillManagerService = AutofillManagerService.this;
      if (autofillManagerService.verbose) {
        i2 = 6;
      } else {
        i2 = autofillManagerService.debug ? 2 : 0;
      }
      AutofillOptions autofillOptions =
          new AutofillOptions(
              i2,
              autofillManagerService.mAutofillCompatState.isCompatibilityModeRequested(str, j, i));
      AutofillManagerService.this.mAugmentedAutofillState.injectAugmentedAutofillInfo(
          autofillOptions, i, str);
      injectDisableAppInfo(autofillOptions, i, str);
      return autofillOptions;
    }

    public boolean isAugmentedAutofillServiceForUser(int i, int i2) {
      synchronized (AutofillManagerService.this.mLock) {
        AutofillManagerServiceImpl autofillManagerServiceImpl =
            (AutofillManagerServiceImpl) AutofillManagerService.this.peekServiceForUserLocked(i2);
        if (autofillManagerServiceImpl == null) {
          return false;
        }
        return autofillManagerServiceImpl.isAugmentedAutofillServiceForUserLocked(i);
      }
    }

    public final void injectDisableAppInfo(AutofillOptions autofillOptions, int i, String str) {
      autofillOptions.appDisabledExpiration =
          AutofillManagerService.this.mDisabledInfoCache.getAppDisabledExpiration(i, str);
      autofillOptions.disabledActivities =
          AutofillManagerService.this.mDisabledInfoCache.getAppDisabledActivities(i, str);
    }
  }

  public final class PackageCompatState {
    public final long maxVersionCode;
    public final String[] urlBarResourceIds;

    public PackageCompatState(long j, String[] strArr) {
      this.maxVersionCode = j;
      this.urlBarResourceIds = strArr;
    }

    public String toString() {
      return "maxVersionCode="
          + this.maxVersionCode
          + ", urlBarResourceIds="
          + Arrays.toString(this.urlBarResourceIds);
    }
  }

  public final class DisabledInfoCache {
    public final Object mLock = new Object();
    public final SparseArray mCache = new SparseArray();

    public void remove(int i) {
      synchronized (this.mLock) {
        this.mCache.remove(i);
      }
    }

    public void addDisabledAppLocked(int i, String str, long j) {
      Objects.requireNonNull(str);
      synchronized (this.mLock) {
        getOrCreateAutofillDisabledInfoByUserIdLocked(i).putDisableAppsLocked(str, j);
      }
    }

    public void addDisabledActivityLocked(int i, ComponentName componentName, long j) {
      Objects.requireNonNull(componentName);
      synchronized (this.mLock) {
        getOrCreateAutofillDisabledInfoByUserIdLocked(i).putDisableActivityLocked(componentName, j);
      }
    }

    public boolean isAutofillDisabledLocked(int i, ComponentName componentName) {
      boolean isAutofillDisabledLocked;
      Objects.requireNonNull(componentName);
      synchronized (this.mLock) {
        AutofillDisabledInfo autofillDisabledInfo = (AutofillDisabledInfo) this.mCache.get(i);
        isAutofillDisabledLocked =
            autofillDisabledInfo != null
                ? autofillDisabledInfo.isAutofillDisabledLocked(componentName)
                : false;
      }
      return isAutofillDisabledLocked;
    }

    public long getAppDisabledExpiration(int i, String str) {
      Long valueOf;
      Objects.requireNonNull(str);
      synchronized (this.mLock) {
        AutofillDisabledInfo autofillDisabledInfo = (AutofillDisabledInfo) this.mCache.get(i);
        valueOf =
            Long.valueOf(
                autofillDisabledInfo != null
                    ? autofillDisabledInfo.getAppDisabledExpirationLocked(str)
                    : 0L);
      }
      return valueOf.longValue();
    }

    public ArrayMap getAppDisabledActivities(int i, String str) {
      ArrayMap appDisabledActivitiesLocked;
      Objects.requireNonNull(str);
      synchronized (this.mLock) {
        AutofillDisabledInfo autofillDisabledInfo = (AutofillDisabledInfo) this.mCache.get(i);
        appDisabledActivitiesLocked =
            autofillDisabledInfo != null
                ? autofillDisabledInfo.getAppDisabledActivitiesLocked(str)
                : null;
      }
      return appDisabledActivitiesLocked;
    }

    public void dump(int i, String str, PrintWriter printWriter) {
      synchronized (this.mLock) {
        AutofillDisabledInfo autofillDisabledInfo = (AutofillDisabledInfo) this.mCache.get(i);
        if (autofillDisabledInfo != null) {
          autofillDisabledInfo.dumpLocked(str, printWriter);
        }
      }
    }

    public final AutofillDisabledInfo getOrCreateAutofillDisabledInfoByUserIdLocked(int i) {
      AutofillDisabledInfo autofillDisabledInfo = (AutofillDisabledInfo) this.mCache.get(i);
      if (autofillDisabledInfo != null) {
        return autofillDisabledInfo;
      }
      AutofillDisabledInfo autofillDisabledInfo2 = new AutofillDisabledInfo();
      this.mCache.put(i, autofillDisabledInfo2);
      return autofillDisabledInfo2;
    }
  }

  public final class AutofillDisabledInfo {
    public ArrayMap mDisabledActivities;
    public ArrayMap mDisabledApps;

    public AutofillDisabledInfo() {}

    public void putDisableAppsLocked(String str, long j) {
      if (this.mDisabledApps == null) {
        this.mDisabledApps = new ArrayMap(1);
      }
      this.mDisabledApps.put(str, Long.valueOf(j));
    }

    public void putDisableActivityLocked(ComponentName componentName, long j) {
      if (this.mDisabledActivities == null) {
        this.mDisabledActivities = new ArrayMap(1);
      }
      this.mDisabledActivities.put(componentName, Long.valueOf(j));
    }

    public long getAppDisabledExpirationLocked(String str) {
      Long l;
      ArrayMap arrayMap = this.mDisabledApps;
      if (arrayMap == null || (l = (Long) arrayMap.get(str)) == null) {
        return 0L;
      }
      return l.longValue();
    }

    public ArrayMap getAppDisabledActivitiesLocked(String str) {
      ArrayMap arrayMap = this.mDisabledActivities;
      ArrayMap arrayMap2 = null;
      if (arrayMap != null) {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
          ComponentName componentName = (ComponentName) this.mDisabledActivities.keyAt(i);
          if (str.equals(componentName.getPackageName())) {
            if (arrayMap2 == null) {
              arrayMap2 = new ArrayMap();
            }
            arrayMap2.put(
                componentName.flattenToShortString(),
                Long.valueOf(((Long) this.mDisabledActivities.valueAt(i)).longValue()));
          }
        }
      }
      return arrayMap2;
    }

    public boolean isAutofillDisabledLocked(ComponentName componentName) {
      long j;
      Long l;
      if (this.mDisabledActivities != null) {
        j = SystemClock.elapsedRealtime();
        Long l2 = (Long) this.mDisabledActivities.get(componentName);
        if (l2 != null) {
          if (l2.longValue() >= j) {
            return true;
          }
          if (Helper.sVerbose) {
            Slog.v(
                "AutofillManagerService",
                "Removing " + componentName.toShortString() + " from disabled list");
          }
          this.mDisabledActivities.remove(componentName);
        }
      } else {
        j = 0;
      }
      String packageName = componentName.getPackageName();
      ArrayMap arrayMap = this.mDisabledApps;
      if (arrayMap == null || (l = (Long) arrayMap.get(packageName)) == null) {
        return false;
      }
      if (j == 0) {
        j = SystemClock.elapsedRealtime();
      }
      if (l.longValue() >= j) {
        return true;
      }
      if (Helper.sVerbose) {
        Slog.v("AutofillManagerService", "Removing " + packageName + " from disabled list");
      }
      this.mDisabledApps.remove(packageName);
      return false;
    }

    public void dumpLocked(String str, PrintWriter printWriter) {
      printWriter.print(str);
      printWriter.print("Disabled apps: ");
      ArrayMap arrayMap = this.mDisabledApps;
      if (arrayMap == null) {
        printWriter.println("N/A");
      } else {
        int size = arrayMap.size();
        printWriter.println(size);
        StringBuilder sb = new StringBuilder();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        for (int i = 0; i < size; i++) {
          String str2 = (String) this.mDisabledApps.keyAt(i);
          long longValue = ((Long) this.mDisabledApps.valueAt(i)).longValue();
          sb.append(str);
          sb.append(str);
          sb.append(i);
          sb.append(". ");
          sb.append(str2);
          sb.append(": ");
          TimeUtils.formatDuration(longValue - elapsedRealtime, sb);
          sb.append('\n');
        }
        printWriter.println(sb);
      }
      printWriter.print(str);
      printWriter.print("Disabled activities: ");
      ArrayMap arrayMap2 = this.mDisabledActivities;
      if (arrayMap2 == null) {
        printWriter.println("N/A");
        return;
      }
      int size2 = arrayMap2.size();
      printWriter.println(size2);
      StringBuilder sb2 = new StringBuilder();
      long elapsedRealtime2 = SystemClock.elapsedRealtime();
      for (int i2 = 0; i2 < size2; i2++) {
        ComponentName componentName = (ComponentName) this.mDisabledActivities.keyAt(i2);
        long longValue2 = ((Long) this.mDisabledActivities.valueAt(i2)).longValue();
        sb2.append(str);
        sb2.append(str);
        sb2.append(i2);
        sb2.append(". ");
        sb2.append(componentName);
        sb2.append(": ");
        TimeUtils.formatDuration(longValue2 - elapsedRealtime2, sb2);
        sb2.append('\n');
      }
      printWriter.println(sb2);
    }
  }

  public final class AutofillCompatState {
    public final Object mLock = new Object();
    public SparseArray mUserSpecs;

    public boolean isCompatibilityModeRequested(String str, long j, int i) {
      synchronized (this.mLock) {
        SparseArray sparseArray = this.mUserSpecs;
        if (sparseArray == null) {
          return false;
        }
        ArrayMap arrayMap = (ArrayMap) sparseArray.get(i);
        if (arrayMap == null) {
          return false;
        }
        PackageCompatState packageCompatState = (PackageCompatState) arrayMap.get(str);
        if (packageCompatState == null) {
          return false;
        }
        return j <= packageCompatState.maxVersionCode;
      }
    }

    public String[] getUrlBarResourceIds(String str, int i) {
      synchronized (this.mLock) {
        SparseArray sparseArray = this.mUserSpecs;
        if (sparseArray == null) {
          return null;
        }
        ArrayMap arrayMap = (ArrayMap) sparseArray.get(i);
        if (arrayMap == null) {
          return null;
        }
        PackageCompatState packageCompatState = (PackageCompatState) arrayMap.get(str);
        if (packageCompatState == null) {
          return null;
        }
        return packageCompatState.urlBarResourceIds;
      }
    }

    public void addCompatibilityModeRequest(String str, long j, String[] strArr, int i) {
      synchronized (this.mLock) {
        if (this.mUserSpecs == null) {
          this.mUserSpecs = new SparseArray();
        }
        ArrayMap arrayMap = (ArrayMap) this.mUserSpecs.get(i);
        if (arrayMap == null) {
          arrayMap = new ArrayMap();
          this.mUserSpecs.put(i, arrayMap);
        }
        arrayMap.put(str, new PackageCompatState(j, strArr));
      }
    }

    public void removeCompatibilityModeRequests(int i) {
      synchronized (this.mLock) {
        SparseArray sparseArray = this.mUserSpecs;
        if (sparseArray != null) {
          sparseArray.remove(i);
          if (this.mUserSpecs.size() <= 0) {
            this.mUserSpecs = null;
          }
        }
      }
    }

    public void reset(int i) {
      synchronized (this.mLock) {
        SparseArray sparseArray = this.mUserSpecs;
        if (sparseArray != null) {
          sparseArray.delete(i);
          int size = this.mUserSpecs.size();
          if (size == 0) {
            if (Helper.sVerbose) {
              Slog.v("AutofillManagerService", "reseting mUserSpecs");
            }
            this.mUserSpecs = null;
          } else if (Helper.sVerbose) {
            Slog.v("AutofillManagerService", "mUserSpecs down to " + size);
          }
        }
      }
    }

    public final void dump(String str, PrintWriter printWriter) {
      synchronized (this.mLock) {
        if (this.mUserSpecs == null) {
          printWriter.println("N/A");
          return;
        }
        printWriter.println();
        String str2 = str + "  ";
        for (int i = 0; i < this.mUserSpecs.size(); i++) {
          int keyAt = this.mUserSpecs.keyAt(i);
          printWriter.print(str);
          printWriter.print("User: ");
          printWriter.println(keyAt);
          ArrayMap arrayMap = (ArrayMap) this.mUserSpecs.valueAt(i);
          for (int i2 = 0; i2 < arrayMap.size(); i2++) {
            String str3 = (String) arrayMap.keyAt(i2);
            PackageCompatState packageCompatState = (PackageCompatState) arrayMap.valueAt(i2);
            printWriter.print(str2);
            printWriter.print(str3);
            printWriter.print(": ");
            printWriter.println(packageCompatState);
          }
        }
      }
    }
  }

  public final class AugmentedAutofillState extends GlobalWhitelistState {
    public final SparseArray mServicePackages = new SparseArray();
    public final SparseBooleanArray mTemporaryServices = new SparseBooleanArray();

    public final void setServiceInfo(int i, String str, boolean z) {
      synchronized (((GlobalWhitelistState) this).mGlobalWhitelistStateLock) {
        if (z) {
          this.mTemporaryServices.put(i, true);
        } else {
          this.mTemporaryServices.delete(i);
        }
        if (str != null) {
          ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
          if (unflattenFromString == null) {
            Slog.w("AutofillManagerService", "setServiceInfo(): invalid name: " + str);
            this.mServicePackages.remove(i);
          } else {
            this.mServicePackages.put(i, unflattenFromString.getPackageName());
          }
        } else {
          this.mServicePackages.remove(i);
        }
      }
    }

    public void injectAugmentedAutofillInfo(AutofillOptions autofillOptions, int i, String str) {
      synchronized (((GlobalWhitelistState) this).mGlobalWhitelistStateLock) {
        SparseArray sparseArray = ((GlobalWhitelistState) this).mWhitelisterHelpers;
        if (sparseArray == null) {
          return;
        }
        WhitelistHelper whitelistHelper = (WhitelistHelper) sparseArray.get(i);
        if (whitelistHelper != null) {
          if (isContainsService("com.samsung.android.smartsuggestions") && i == 0) {
            autofillOptions.augmentedAutofillEnabled = true;
            autofillOptions.whitelistedActivitiesForAugmentedAutofill = null;
          } else {
            autofillOptions.augmentedAutofillEnabled = whitelistHelper.isWhitelisted(str);
            autofillOptions.whitelistedActivitiesForAugmentedAutofill =
                whitelistHelper.getWhitelistedComponents(str);
          }
        }
      }
    }

    public boolean isWhitelisted(int i, ComponentName componentName) {
      synchronized (((GlobalWhitelistState) this).mGlobalWhitelistStateLock) {
        if (isContainsService("com.samsung.android.smartsuggestions") && i == 0) {
          return true;
        }
        if (!super.isWhitelisted(i, componentName)) {
          return false;
        }
        if (Build.IS_USER && this.mTemporaryServices.get(i)) {
          String packageName = componentName.getPackageName();
          if (!packageName.equals(this.mServicePackages.get(i))) {
            Slog.w(
                "AutofillManagerService",
                "Ignoring package "
                    + packageName
                    + " for augmented autofill while using temporary service "
                    + ((String) this.mServicePackages.get(i)));
            return false;
          }
        }
        return true;
      }
    }

    public void dump(String str, PrintWriter printWriter) {
      super.dump(str, printWriter);
      synchronized (((GlobalWhitelistState) this).mGlobalWhitelistStateLock) {
        if (this.mServicePackages.size() > 0) {
          printWriter.print(str);
          printWriter.print("Service packages: ");
          printWriter.println(this.mServicePackages);
        }
        if (this.mTemporaryServices.size() > 0) {
          printWriter.print(str);
          printWriter.print("Temp services: ");
          printWriter.println(this.mTemporaryServices);
        }
      }
    }

    public final boolean isContainsService(String str) {
      String str2 = (String) this.mServicePackages.get(UserHandle.getCallingUserId());
      return str2 != null && str2.contains(str);
    }
  }

  public final class AutoFillManagerServiceStub extends IAutoFillManager.Stub {
    public AutoFillManagerServiceStub() {}

    public void addClient(
        IAutoFillManagerClient iAutoFillManagerClient,
        ComponentName componentName,
        int i,
        IResultReceiver iResultReceiver) {
      try {
        try {
          synchronized (AutofillManagerService.this.mLock) {
            int addClientLocked =
                AutofillManagerService.this
                    .getServiceForUserWithLocalBinderIdentityLocked(i)
                    .addClientLocked(iAutoFillManagerClient, componentName);
            r0 = addClientLocked != 0 ? 0 | addClientLocked : 0;
            if (Helper.sDebug) {
              r0 |= 2;
            }
            if (Helper.sVerbose) {
              r0 |= 4;
            }
          }
        } catch (Exception e) {
          Log.wtf("AutofillManagerService", "addClient(): failed " + e.toString());
        }
      } finally {
        AutofillManagerService.this.send(iResultReceiver, r0);
      }
    }

    public void removeClient(IAutoFillManagerClient iAutoFillManagerClient, int i) {
      synchronized (AutofillManagerService.this.mLock) {
        AutofillManagerServiceImpl autofillManagerServiceImpl =
            (AutofillManagerServiceImpl) AutofillManagerService.this.peekServiceForUserLocked(i);
        if (autofillManagerServiceImpl != null) {
          autofillManagerServiceImpl.removeClientLocked(iAutoFillManagerClient);
        } else if (Helper.sVerbose) {
          Slog.v("AutofillManagerService", "removeClient(): no service for " + i);
        }
      }
    }

    public void setAuthenticationResult(Bundle bundle, int i, int i2, int i3) {
      synchronized (AutofillManagerService.this.mLock) {
        AutofillManagerService.this
            .getServiceForUserWithLocalBinderIdentityLocked(i3)
            .setAuthenticationResultLocked(bundle, i, i2, IAutoFillManager.Stub.getCallingUid());
      }
    }

    public void setHasCallback(int i, int i2, boolean z) {
      synchronized (AutofillManagerService.this.mLock) {
        AutofillManagerService.this
            .getServiceForUserWithLocalBinderIdentityLocked(i2)
            .setHasCallback(i, IAutoFillManager.Stub.getCallingUid(), z);
      }
    }

    public void startSession(
        IBinder iBinder,
        IBinder iBinder2,
        AutofillId autofillId,
        Rect rect,
        AutofillValue autofillValue,
        int i,
        boolean z,
        int i2,
        ComponentName componentName,
        boolean z2,
        IResultReceiver iResultReceiver) {
      long startSessionLocked;
      Objects.requireNonNull(iBinder, "activityToken");
      Objects.requireNonNull(iBinder2, "clientCallback");
      Objects.requireNonNull(autofillId, "autofillId");
      Objects.requireNonNull(componentName, "clientActivity");
      String packageName = componentName.getPackageName();
      Objects.requireNonNull(packageName);
      Preconditions.checkArgument(
          i == UserHandle.getUserId(IAutoFillManager.Stub.getCallingUid()), "userId");
      try {
        AutofillManagerService.this
            .getContext()
            .getPackageManager()
            .getPackageInfoAsUser(packageName, 0, i);
        int taskIdForActivity =
            AutofillManagerService.this.mAm.getTaskIdForActivity(iBinder, false);
        synchronized (AutofillManagerService.this.mLock) {
          startSessionLocked =
              AutofillManagerService.this
                  .getServiceForUserWithLocalBinderIdentityLocked(i)
                  .startSessionLocked(
                      iBinder,
                      taskIdForActivity,
                      IAutoFillManager.Stub.getCallingUid(),
                      iBinder2,
                      autofillId,
                      rect,
                      autofillValue,
                      z,
                      componentName,
                      z2,
                      AutofillManagerService.this.mAllowInstantService,
                      i2);
        }
        int i3 = (int) startSessionLocked;
        int i4 = (int) (startSessionLocked >> 32);
        if (i4 != 0) {
          AutofillManagerService.this.send(iResultReceiver, i3, i4);
        } else {
          AutofillManagerService.this.send(iResultReceiver, i3);
        }
      } catch (PackageManager.NameNotFoundException e) {
        throw new IllegalArgumentException(packageName + " is not a valid package", e);
      }
    }

    public void getFillEventHistory(IResultReceiver iResultReceiver) {
      int callingUserId = UserHandle.getCallingUserId();
      FillEventHistory fillEventHistory = null;
      try {
        try {
          synchronized (AutofillManagerService.this.mLock) {
            AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
                AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(
                    callingUserId);
            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
              fillEventHistory =
                  peekServiceForUserWithLocalBinderIdentityLocked.getFillEventHistory(
                      IAutoFillManager.Stub.getCallingUid());
            } else if (Helper.sVerbose) {
              Slog.v(
                  "AutofillManagerService",
                  "getFillEventHistory(): no service for " + callingUserId);
            }
          }
        } catch (Exception e) {
          Log.wtf("AutofillManagerService", "getFillEventHistory(): failed " + e.toString());
        }
      } finally {
        AutofillManagerService.this.send(iResultReceiver, fillEventHistory);
      }
    }

    public void getUserData(IResultReceiver iResultReceiver) {
      int callingUserId = UserHandle.getCallingUserId();
      UserData userData = null;
      try {
        try {
          synchronized (AutofillManagerService.this.mLock) {
            AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
                AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(
                    callingUserId);
            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
              userData =
                  peekServiceForUserWithLocalBinderIdentityLocked.getUserData(
                      IAutoFillManager.Stub.getCallingUid());
            } else if (Helper.sVerbose) {
              Slog.v("AutofillManagerService", "getUserData(): no service for " + callingUserId);
            }
          }
        } catch (Exception e) {
          Log.wtf("AutofillManagerService", "getUserData(): failed " + e.toString());
        }
      } finally {
        AutofillManagerService.this.send(iResultReceiver, userData);
      }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void getUserDataId(IResultReceiver iResultReceiver) {
      UserData userData;
      UserData userData2;
      int callingUserId = UserHandle.getCallingUserId();
      try {
      } catch (Exception e) {
        e = e;
        userData = null;
      } catch (Throwable th) {
        th = th;
        userData = null;
        AutofillManagerService.this.send(
            iResultReceiver, userData != null ? userData.getId() : null);
        throw th;
      }
      synchronized (AutofillManagerService.this.mLock) {
        try {
          AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
              AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(
                  callingUserId);
          if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
            userData2 =
                peekServiceForUserWithLocalBinderIdentityLocked.getUserData(
                    IAutoFillManager.Stub.getCallingUid());
          } else {
            if (Helper.sVerbose) {
              Slog.v("AutofillManagerService", "getUserDataId(): no service for " + callingUserId);
            }
            userData2 = null;
          }
          try {
            if (userData2 != null) {
              r1 = userData2.getId();
            }
            AutofillManagerService.this.send(iResultReceiver, r1);
          } catch (Throwable th2) {
            userData = userData2;
            th = th2;
            while (true) {
              try {
                try {
                  try {
                    throw th;
                  } catch (Exception e2) {
                    e = e2;
                    Log.wtf("AutofillManagerService", "getUserDataId(): failed " + e.toString());
                    if (userData != null) {
                      r1 = userData.getId();
                    }
                    AutofillManagerService.this.send(iResultReceiver, r1);
                  }
                } catch (Throwable th3) {
                  th = th3;
                  AutofillManagerService.this.send(
                      iResultReceiver, userData != null ? userData.getId() : null);
                  throw th;
                }
              } catch (Throwable th4) {
                th = th4;
              }
            }
          }
        } catch (Throwable th5) {
          th = th5;
          userData = null;
        }
      }
    }

    public void setUserData(UserData userData) {
      int callingUserId = UserHandle.getCallingUserId();
      synchronized (AutofillManagerService.this.mLock) {
        AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
            AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(
                callingUserId);
        if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
          peekServiceForUserWithLocalBinderIdentityLocked.setUserData(
              IAutoFillManager.Stub.getCallingUid(), userData);
        } else if (Helper.sVerbose) {
          Slog.v("AutofillManagerService", "setUserData(): no service for " + callingUserId);
        }
      }
    }

    public void isFieldClassificationEnabled(IResultReceiver iResultReceiver) {
      int callingUserId = UserHandle.getCallingUserId();
      boolean z = false;
      try {
        try {
          synchronized (AutofillManagerService.this.mLock) {
            AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
                AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(
                    callingUserId);
            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
              z =
                  peekServiceForUserWithLocalBinderIdentityLocked.isFieldClassificationEnabled(
                      IAutoFillManager.Stub.getCallingUid());
            } else if (Helper.sVerbose) {
              Slog.v(
                  "AutofillManagerService",
                  "isFieldClassificationEnabled(): no service for " + callingUserId);
            }
          }
        } catch (Exception e) {
          Log.wtf(
              "AutofillManagerService", "isFieldClassificationEnabled(): failed " + e.toString());
        }
      } finally {
        AutofillManagerService.this.send(iResultReceiver, z);
      }
    }

    public void getDefaultFieldClassificationAlgorithm(IResultReceiver iResultReceiver) {
      int callingUserId = UserHandle.getCallingUserId();
      String str = null;
      try {
        try {
          synchronized (AutofillManagerService.this.mLock) {
            AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
                AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(
                    callingUserId);
            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
              str =
                  peekServiceForUserWithLocalBinderIdentityLocked
                      .getDefaultFieldClassificationAlgorithm(
                          IAutoFillManager.Stub.getCallingUid());
            } else if (Helper.sVerbose) {
              Slog.v(
                  "AutofillManagerService",
                  "getDefaultFcAlgorithm(): no service for " + callingUserId);
            }
          }
        } catch (Exception e) {
          Log.wtf(
              "AutofillManagerService",
              "getDefaultFieldClassificationAlgorithm(): failed " + e.toString());
        }
      } finally {
        AutofillManagerService.this.send(iResultReceiver, str);
      }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x003d, code lost:

       if (r7 != false) goto L15;
    */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x003f, code lost:

       r1 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0040, code lost:

       r6.send(r9, r1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0076, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0073, code lost:

       if (r8 == false) goto L16;
    */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setAugmentedAutofillWhitelist(
        List list, List list2, IResultReceiver iResultReceiver) {
      boolean z;
      AutofillManagerService autofillManagerService;
      boolean z2;
      int callingUserId = UserHandle.getCallingUserId();
      try {
        synchronized (AutofillManagerService.this.mLock) {
          try {
            AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
                AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(
                    callingUserId);
            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
              z2 =
                  peekServiceForUserWithLocalBinderIdentityLocked
                      .setAugmentedAutofillWhitelistLocked(
                          list, list2, IAutoFillManager.Stub.getCallingUid());
            } else {
              if (Helper.sVerbose) {
                Slog.v(
                    "AutofillManagerService",
                    "setAugmentedAutofillWhitelist(): no service for " + callingUserId);
              }
              z2 = false;
            }
            try {
              autofillManagerService = AutofillManagerService.this;
            } catch (Throwable th) {
              z = z2;
              th = th;
              while (true) {
                try {
                  try {
                    try {
                      throw th;
                    } catch (Exception e) {
                      e = e;
                      Log.wtf(
                          "AutofillManagerService",
                          "setAugmentedAutofillWhitelist(): failed " + e.toString());
                      autofillManagerService = AutofillManagerService.this;
                    }
                  } catch (Throwable th2) {
                    th = th2;
                    AutofillManagerService.this.send(iResultReceiver, z ? 0 : -1);
                    throw th;
                  }
                } catch (Throwable th3) {
                  th = th3;
                }
              }
            }
          } catch (Throwable th4) {
            th = th4;
            z = false;
          }
        }
      } catch (Exception e2) {
        e = e2;
        z = false;
      } catch (Throwable th5) {
        th = th5;
        z = false;
        AutofillManagerService.this.send(iResultReceiver, z ? 0 : -1);
        throw th;
      }
    }

    public void getAvailableFieldClassificationAlgorithms(IResultReceiver iResultReceiver) {
      int callingUserId = UserHandle.getCallingUserId();
      String[] strArr = null;
      try {
        synchronized (AutofillManagerService.this.mLock) {
          AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
              AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(
                  callingUserId);
          if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
            strArr =
                peekServiceForUserWithLocalBinderIdentityLocked
                    .getAvailableFieldClassificationAlgorithms(
                        IAutoFillManager.Stub.getCallingUid());
          } else if (Helper.sVerbose) {
            Slog.v(
                "AutofillManagerService",
                "getAvailableFcAlgorithms(): no service for " + callingUserId);
          }
        }
      } catch (Exception e) {
        Log.wtf(
            "AutofillManagerService",
            "getAvailableFieldClassificationAlgorithms(): failed " + e.toString());
      } finally {
        AutofillManagerService.this.send(iResultReceiver, strArr);
      }
    }

    public void getAutofillServiceComponentName(IResultReceiver iResultReceiver) {
      int callingUserId = UserHandle.getCallingUserId();
      ComponentName componentName = null;
      try {
        try {
          synchronized (AutofillManagerService.this.mLock) {
            AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
                AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(
                    callingUserId);
            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
              componentName =
                  peekServiceForUserWithLocalBinderIdentityLocked.getServiceComponentName();
            } else if (Helper.sVerbose) {
              Slog.v(
                  "AutofillManagerService",
                  "getAutofillServiceComponentName(): no service for " + callingUserId);
            }
          }
        } catch (Exception e) {
          Log.wtf(
              "AutofillManagerService",
              "getAutofillServiceComponentName(): failed " + e.toString());
        }
      } finally {
        AutofillManagerService.this.send(iResultReceiver, componentName);
      }
    }

    public void restoreSession(
        int i, IBinder iBinder, IBinder iBinder2, IResultReceiver iResultReceiver) {
      int callingUserId = UserHandle.getCallingUserId();
      boolean z = false;
      try {
        try {
          Objects.requireNonNull(iBinder, "activityToken");
          Objects.requireNonNull(iBinder2, "appCallback");
          synchronized (AutofillManagerService.this.mLock) {
            AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
                AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(
                    callingUserId);
            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
              z =
                  peekServiceForUserWithLocalBinderIdentityLocked.restoreSession(
                      i, IAutoFillManager.Stub.getCallingUid(), iBinder, iBinder2);
            } else if (Helper.sVerbose) {
              Slog.v("AutofillManagerService", "restoreSession(): no service for " + callingUserId);
            }
          }
        } catch (Exception e) {
          Log.wtf("AutofillManagerService", "restoreSession(): failed " + e.toString());
        }
      } finally {
        AutofillManagerService.this.send(iResultReceiver, z);
      }
    }

    public void updateSession(
        int i,
        AutofillId autofillId,
        Rect rect,
        AutofillValue autofillValue,
        int i2,
        int i3,
        int i4) {
      synchronized (AutofillManagerService.this.mLock) {
        AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
            AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i4);
        if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
          peekServiceForUserWithLocalBinderIdentityLocked.updateSessionLocked(
              i, IAutoFillManager.Stub.getCallingUid(), autofillId, rect, autofillValue, i2, i3);
        } else if (Helper.sVerbose) {
          Slog.v("AutofillManagerService", "updateSession(): no service for " + i4);
        }
      }
    }

    public void setAutofillFailure(int i, List list, int i2) {
      synchronized (AutofillManagerService.this.mLock) {
        AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
            AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i2);
        if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
          peekServiceForUserWithLocalBinderIdentityLocked.setAutofillFailureLocked(
              i, IAutoFillManager.Stub.getCallingUid(), list);
        } else if (Helper.sVerbose) {
          Slog.v("AutofillManagerService", "setAutofillFailure(): no service for " + i2);
        }
      }
    }

    public void finishSession(int i, int i2, int i3) {
      synchronized (AutofillManagerService.this.mLock) {
        AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
            AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i2);
        if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
          peekServiceForUserWithLocalBinderIdentityLocked.finishSessionLocked(
              i, IAutoFillManager.Stub.getCallingUid(), i3);
        } else if (Helper.sVerbose) {
          Slog.v("AutofillManagerService", "finishSession(): no service for " + i2);
        }
      }
    }

    public void cancelSession(int i, int i2) {
      synchronized (AutofillManagerService.this.mLock) {
        AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
            AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i2);
        if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
          peekServiceForUserWithLocalBinderIdentityLocked.cancelSessionLocked(
              i, IAutoFillManager.Stub.getCallingUid());
        } else if (Helper.sVerbose) {
          Slog.v("AutofillManagerService", "cancelSession(): no service for " + i2);
        }
      }
    }

    public void disableOwnedAutofillServices(int i) {
      synchronized (AutofillManagerService.this.mLock) {
        AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
            AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i);
        if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
          peekServiceForUserWithLocalBinderIdentityLocked.disableOwnedAutofillServicesLocked(
              Binder.getCallingUid());
        } else if (Helper.sVerbose) {
          Slog.v("AutofillManagerService", "cancelSession(): no service for " + i);
        }
      }
    }

    public void isServiceSupported(int i, IResultReceiver iResultReceiver) {
      try {
        try {
          synchronized (AutofillManagerService.this.mLock) {
            r0 = AutofillManagerService.this.isDisabledLocked(i) ? false : true;
          }
        } catch (Exception e) {
          Log.wtf("AutofillManagerService", "isServiceSupported(): failed " + e.toString());
        }
      } finally {
        AutofillManagerService.this.send(iResultReceiver, r0);
      }
    }

    public void isServiceEnabled(int i, String str, IResultReceiver iResultReceiver) {
      boolean z = false;
      try {
        try {
          synchronized (AutofillManagerService.this.mLock) {
            z =
                Objects.equals(
                    str,
                    AutofillManagerService.this
                        .peekServiceForUserWithLocalBinderIdentityLocked(i)
                        .getServicePackageName());
          }
        } catch (Exception e) {
          Log.wtf("AutofillManagerService", "isServiceEnabled(): failed " + e.toString());
        }
      } finally {
        AutofillManagerService.this.send(iResultReceiver, z);
      }
    }

    public void onPendingSaveUi(int i, IBinder iBinder) {
      Objects.requireNonNull(iBinder, KnoxCustomManagerService.SPCM_KEY_TOKEN);
      boolean z = true;
      if (i != 1 && i != 2) {
        z = false;
      }
      Preconditions.checkArgument(z, "invalid operation: %d", new Object[] {Integer.valueOf(i)});
      synchronized (AutofillManagerService.this.mLock) {
        AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked =
            AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(
                UserHandle.getCallingUserId());
        if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
          peekServiceForUserWithLocalBinderIdentityLocked.onPendingSaveUi(i, iBinder);
        }
      }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
      boolean z;
      if (DumpUtils.checkDumpPermission(
          AutofillManagerService.this.getContext(), "AutofillManagerService", printWriter)) {
        boolean z2 = false;
        if (strArr != null) {
          boolean z3 = false;
          z = true;
          for (String str : strArr) {
            str.hashCode();
            switch (str) {
              case "--ui-only":
                z3 = true;
                break;
              case "--no-history":
                z = false;
                break;
              case "--help":
                printWriter.println("Usage: dumpsys autofill [--ui-only|--no-history]");
                return;
              default:
                Slog.w("AutofillManagerService", "Ignoring invalid dump arg: " + str);
                break;
            }
          }
          z2 = z3;
        } else {
          z = true;
        }
        if (z2) {
          AutofillManagerService.this.mUi.dump(printWriter);
          return;
        }
        boolean z4 = Helper.sDebug;
        boolean z5 = Helper.sVerbose;
        try {
          Helper.sVerbose = true;
          Helper.sDebug = true;
          synchronized (AutofillManagerService.this.mLock) {
            printWriter.print("sDebug: ");
            printWriter.print(z4);
            printWriter.print(" sVerbose: ");
            printWriter.println(z5);
            printWriter.print("Flags: ");
            synchronized (AutofillManagerService.this.mFlagLock) {
              printWriter.print("mPccClassificationEnabled=");
              printWriter.print(AutofillManagerService.this.mPccClassificationEnabled);
              printWriter.print(KnoxVpnFirewallHelper.DELIMITER);
              printWriter.print("mPccPreferProviderOverPcc=");
              printWriter.print(AutofillManagerService.this.mPccPreferProviderOverPcc);
              printWriter.print(KnoxVpnFirewallHelper.DELIMITER);
              printWriter.print("mPccUseFallbackDetection=");
              printWriter.print(AutofillManagerService.this.mPccUseFallbackDetection);
              printWriter.print(KnoxVpnFirewallHelper.DELIMITER);
              printWriter.print("mPccProviderHints=");
              printWriter.println(AutofillManagerService.this.mPccProviderHints);
            }
            AutofillManagerService.this.dumpLocked("", printWriter);
            AutofillManagerService.this.mAugmentedAutofillResolver.dumpShort(printWriter);
            printWriter.println();
            printWriter.print("Max partitions per session: ");
            printWriter.println(AutofillManagerService.sPartitionMaxCount);
            printWriter.print("Max visible datasets: ");
            printWriter.println(AutofillManagerService.sVisibleDatasetsMaxCount);
            if (Helper.sFullScreenMode != null) {
              printWriter.print("Overridden full-screen mode: ");
              printWriter.println(Helper.sFullScreenMode);
            }
            printWriter.println("User data constraints: ");
            UserData.dumpConstraints("  ", printWriter);
            AutofillManagerService.this.mUi.dump(printWriter);
            printWriter.print("Autofill Compat State: ");
            AutofillManagerService.this.mAutofillCompatState.dump("  ", printWriter);
            printWriter.print("from device config: ");
            printWriter.println(
                AutofillManagerService.this.getAllowedCompatModePackagesFromDeviceConfig());
            if (AutofillManagerService.this.mSupportedSmartSuggestionModes != 0) {
              printWriter.print("Smart Suggestion modes: ");
              printWriter.println(
                  AutofillManager.getSmartSuggestionModeToString(
                      AutofillManagerService.this.mSupportedSmartSuggestionModes));
            }
            printWriter.print("Augmented Service Idle Unbind Timeout: ");
            printWriter.println(AutofillManagerService.this.mAugmentedServiceIdleUnbindTimeoutMs);
            printWriter.print("Augmented Service Request Timeout: ");
            printWriter.println(AutofillManagerService.this.mAugmentedServiceRequestTimeoutMs);
            if (z) {
              printWriter.println();
              printWriter.println("Requests history:");
              printWriter.println();
              AutofillManagerService.this.mRequestsHistory.reverseDump(
                  fileDescriptor, printWriter, strArr);
              printWriter.println();
              printWriter.println("UI latency history:");
              printWriter.println();
              AutofillManagerService.this.mUiLatencyHistory.reverseDump(
                  fileDescriptor, printWriter, strArr);
              printWriter.println();
              printWriter.println("WTF history:");
              printWriter.println();
              AutofillManagerService.this.mWtfHistory.reverseDump(
                  fileDescriptor, printWriter, strArr);
            }
            printWriter.println("Augmented Autofill State: ");
            AutofillManagerService.this.mAugmentedAutofillState.dump("  ", printWriter);
          }
        } finally {
          Helper.sDebug = z4;
          Helper.sVerbose = z5;
        }
      }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(
        FileDescriptor fileDescriptor,
        FileDescriptor fileDescriptor2,
        FileDescriptor fileDescriptor3,
        String[] strArr,
        ShellCallback shellCallback,
        ResultReceiver resultReceiver) {
      new AutofillManagerServiceShellCommand(AutofillManagerService.this)
          .exec(
              this,
              fileDescriptor,
              fileDescriptor2,
              fileDescriptor3,
              strArr,
              shellCallback,
              resultReceiver);
    }
  }
}
