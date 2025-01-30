package com.samsung.android.globalactions.util;

import android.content.Context;
import android.util.ArrayMap;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsManager;

/* loaded from: classes5.dex */
public class DefaultUtilFactory implements UtilFactory {
  Context mContext;
  private ArrayMap<Object, Object> mProvider = new ArrayMap<>();

  public DefaultUtilFactory(Context context, SamsungGlobalActionsManager windowManagerFuncs) {
    this.mContext = context;
    LogWrapper logWrapper = new LogWrapper();
    this.mProvider.put(LogWrapper.class, logWrapper);
    this.mProvider.put(SamsungGlobalActionsManager.class, windowManagerFuncs);
    this.mProvider.put(AlertDialogFactory.class, new AlertDialogFactory(this.mContext));
    this.mProvider.put(
        BroadcastManager.class, new BroadcastManager(this.mContext, logWrapper, new HandlerUtil()));
    this.mProvider.put(Context.class, context);
    this.mProvider.put(
        DevicePolicyManagerWrapper.class, new DevicePolicyManagerWrapper(this.mContext));
    this.mProvider.put(HandlerUtil.class, new HandlerUtil());
    this.mProvider.put(
        InputMethodManagerWrapper.class, new InputMethodManagerWrapper(this.mContext));
    this.mProvider.put(
        KeyGuardManagerWrapper.class, new KeyGuardManagerWrapper(this.mContext, logWrapper));
    this.mProvider.put(
        LockPatternUtilsWrapper.class,
        new LockPatternUtilsWrapper(this.mContext, logWrapper, new HandlerUtil()));
    this.mProvider.put(PackageManagerWrapper.class, new PackageManagerWrapper(this.mContext));
    this.mProvider.put(ResourcesWrapper.class, new ResourcesWrapper(this.mContext.getResources()));
    this.mProvider.put(
        SemEmergencyManagerWrapper.class, new SemEmergencyManagerWrapper(this.mContext));
    this.mProvider.put(SemTipPopupWrapper.class, new SemTipPopupWrapper(this.mContext));
    this.mProvider.put(SettingsWrapper.class, new SettingsWrapper(this.mContext, logWrapper));
    this.mProvider.put(
        SystemController.class, new SystemController(this.mContext, new HandlerUtil()));
    this.mProvider.put(
        SystemPropertiesWrapper.class, new SystemPropertiesWrapper(this.mContext, logWrapper));
    this.mProvider.put(TelephonyManagerWrapper.class, new TelephonyManagerWrapper(this.mContext));
    ScreenCaptureUtil screenCaptureUtil = new ScreenCaptureUtil(this.mContext, logWrapper);
    this.mProvider.put(ScreenCaptureUtil.class, screenCaptureUtil);
    this.mProvider.put(WindowManagerUtils.class, new WindowManagerUtils(this.mContext, logWrapper));
    this.mProvider.put(
        ThemeChecker.class, new ThemeChecker(this.mContext, screenCaptureUtil, logWrapper));
    this.mProvider.put(ToastController.class, new ToastController(this.mContext));
    this.mProvider.put(TSafeLockUtil.class, new TSafeLockUtil(this.mContext));
    this.mProvider.put(UserManagerWrapper.class, new UserManagerWrapper(this.mContext));
    this.mProvider.put(
        AccessibilityManagerWrapper.class, new AccessibilityManagerWrapper(this.mContext));
    this.mProvider.put(ContentObserverWrapper.class, new ContentObserverWrapper(this.mContext));
    this.mProvider.put(SemReserveBatteryWrapper.class, new SemReserveBatteryWrapper(this.mContext));
    this.mProvider.put(
        BiometricPromptWrapper.class, new BiometricPromptWrapper(this.mContext, logWrapper));
    this.mProvider.put(UsageStatsWrapper.class, new UsageStatsWrapper(this.mContext, logWrapper));
    this.mProvider.put(
        DesktopModeManagerWrapper.class, new DesktopModeManagerWrapper(this.mContext, logWrapper));
    this.mProvider.put(SemPersonaWrapper.class, new SemPersonaWrapper(this.mContext));
    this.mProvider.put(ActivityManagerWrapper.class, new ActivityManagerWrapper(this.mContext));
  }

  @Override // com.samsung.android.globalactions.util.UtilFactory
  public <T> T get(Class<T> cls) {
    return (T) this.mProvider.get(cls);
  }
}
