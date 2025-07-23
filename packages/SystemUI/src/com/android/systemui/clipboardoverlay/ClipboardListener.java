package com.android.systemui.clipboardoverlay;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.clipboardoverlay.ClipboardListener;
import com.android.systemui.clipboardoverlay.SemRemoteServiceStateManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.user.utils.UserScopedService;
import com.android.systemui.user.utils.UserScopedServiceImpl;
import com.android.systemui.util.SettingsHelper;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.inject.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ClipboardListener implements CoreStartable, ClipboardManager.OnPrimaryClipChangedListener {
    static final String EXTRA_SUPPRESS_OVERLAY = "com.android.systemui.SUPPRESS_CLIPBOARD_OVERLAY";
    static final String SHELL_PACKAGE = "com.android.shell";
    public final UserTracker.Callback mCallback = new UserTracker.Callback() { // from class: com.android.systemui.clipboardoverlay.ClipboardListener.1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            ClipboardListener clipboardListener = ClipboardListener.this;
            clipboardListener.mClipboardManagerForUser.removePrimaryClipChangedListener(clipboardListener);
            UserHandle userHandle = ((UserTrackerImpl) clipboardListener.mUserTracker).getUserHandle();
            clipboardListener.mClipboardManagerForUser = (ClipboardManager) ((UserScopedServiceImpl) clipboardListener.mClipboardManagerProvider).forUser(userHandle);
            clipboardListener.mKeyguardManagerForUser = (KeyguardManager) ((UserScopedServiceImpl) clipboardListener.mKeyguardManagerProvider).forUser(userHandle);
            clipboardListener.mClipboardManagerForUser.addPrimaryClipChangedListener(clipboardListener);
        }
    };
    public ClipboardManager mClipboardManagerForUser;
    public final UserScopedService mClipboardManagerProvider;
    public final ClipboardToast mClipboardToast;
    public final Context mContext;
    public KeyguardManager mKeyguardManagerForUser;
    public final UserScopedService mKeyguardManagerProvider;
    public final Executor mMainExecutor;
    public SemClipboardToastController mSemClipboardToast;
    public final Provider mSemClipboardToastProvider;
    public final UiEventLogger mUiEventLogger;
    public final UserTracker mUserTracker;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.clipboardoverlay.ClipboardListener$2, reason: invalid class name */
    public class AnonymousClass2 extends Thread {
        public static final /* synthetic */ int $r8$clinit = 0;

        public AnonymousClass2() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                if (ClipboardListener.this.mClipboardManagerForUser.hasPrimaryClip()) {
                    final String primaryClipSource = ClipboardListener.this.mClipboardManagerForUser.getPrimaryClipSource();
                    final ClipDescription primaryClipDescription = ClipboardListener.this.mClipboardManagerForUser.getPrimaryClipDescription();
                    if (ClipboardListener.shouldSuppressOverlay(ClipboardListener.this.mClipboardManagerForUser.getPrimaryClip(), primaryClipSource, Build.IS_EMULATOR)) {
                        Log.i("ClipboardListener", "Clipboard overlay suppressed.");
                        return;
                    }
                    boolean z = true;
                    if (!ClipboardListener.this.mKeyguardManagerForUser.isDeviceLocked() && Settings.Secure.getInt(ClipboardListener.this.mContext.getContentResolver(), SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0) == 1 && primaryClipDescription != null) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardListener$2$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ClipboardListener.AnonymousClass2 anonymousClass2 = ClipboardListener.AnonymousClass2.this;
                                ClipDescription clipDescription = primaryClipDescription;
                                String str = primaryClipSource;
                                int i = ClipboardListener.AnonymousClass2.$r8$clinit;
                                try {
                                    ClipboardListener.this.showCopyToast(clipDescription, str);
                                } catch (RuntimeException e) {
                                    Log.e("ClipboardListener", "showCopyToast exception", e);
                                }
                            }
                        });
                        return;
                    }
                    ClipboardListener clipboardListener = ClipboardListener.this;
                    clipboardListener.getClass();
                    if (primaryClipDescription == null) {
                        z = false;
                    } else if (primaryClipDescription.getClassificationStatus() == 3) {
                        z = true ^ (clipboardListener.mClipboardToast.mCopiedToast != null);
                    }
                    if (z) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardListener$2$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ClipboardListener.AnonymousClass2 anonymousClass2 = ClipboardListener.AnonymousClass2.this;
                                String str = primaryClipSource;
                                int i = ClipboardListener.AnonymousClass2.$r8$clinit;
                                anonymousClass2.getClass();
                                try {
                                    ClipboardListener.this.mUiEventLogger.log(ClipboardOverlayEvent.CLIPBOARD_TOAST_SHOWN, 0, str);
                                    ClipboardToast clipboardToast = ClipboardListener.this.mClipboardToast;
                                    Toast toast = clipboardToast.mCopiedToast;
                                    if (toast != null) {
                                        toast.cancel();
                                    }
                                    Toast makeText = Toast.makeText(clipboardToast.mContext, R.string.clipboard_overlay_text_copied, 0);
                                    clipboardToast.mCopiedToast = makeText;
                                    makeText.addCallback(clipboardToast);
                                    clipboardToast.mCopiedToast.show();
                                } catch (RuntimeException e) {
                                    Log.e("ClipboardListener", "showCopiedToast exception", e);
                                }
                            }
                        });
                    }
                }
            } catch (Exception e) {
                Log.e("ClipboardListener", "Failed to show copy toast", e);
            }
        }
    }

    public ClipboardListener(Context context, Provider provider, Provider provider2, ClipboardToast clipboardToast, UserTracker userTracker, UserScopedService userScopedService, UserScopedService userScopedService2, UiEventLogger uiEventLogger, Executor executor, ClipboardOverlaySuppressionController clipboardOverlaySuppressionController) {
        this.mContext = context;
        this.mSemClipboardToastProvider = provider2;
        this.mClipboardToast = clipboardToast;
        this.mClipboardManagerProvider = userScopedService;
        this.mKeyguardManagerProvider = userScopedService2;
        this.mUiEventLogger = uiEventLogger;
        this.mMainExecutor = executor;
        this.mUserTracker = userTracker;
        UserHandle userHandle = ((UserTrackerImpl) userTracker).getUserHandle();
        this.mClipboardManagerForUser = (ClipboardManager) ((UserScopedServiceImpl) userScopedService).forUser(userHandle);
        this.mKeyguardManagerForUser = (KeyguardManager) ((UserScopedServiceImpl) userScopedService2).forUser(userHandle);
    }

    public static boolean shouldSuppressOverlay(ClipData clipData, String str, boolean z) {
        if ((!z && !SHELL_PACKAGE.equals(str)) || clipData == null || clipData.getDescription().getExtras() == null) {
            return false;
        }
        return clipData.getDescription().getExtras().getBoolean(EXTRA_SUPPRESS_OVERLAY, false);
    }

    @Override // android.content.ClipboardManager.OnPrimaryClipChangedListener
    public final void onPrimaryClipChanged() {
        new AnonymousClass2().start();
    }

    public final void showCopyToast(ClipDescription clipDescription, String str) {
        String string;
        int i;
        Display display;
        if (this.mSemClipboardToast == null) {
            this.mSemClipboardToast = (SemClipboardToastController) this.mSemClipboardToastProvider.get();
        }
        SemClipboardToastController semClipboardToastController = this.mSemClipboardToast;
        semClipboardToastController.getClass();
        if (System.currentTimeMillis() - semClipboardToastController.lastCopiedTime > TimeUnit.SECONDS.toMillis(1L)) {
            SemRemoteServiceStateManager semRemoteServiceStateManager = semClipboardToastController.mRemoteServiceStateManager;
            CharSequence label = clipDescription.getLabel();
            if (label == null || label.isEmpty() || "file_copy".contentEquals(label) || "file_move".contentEquals(label)) {
                if ((((Integer) semRemoteServiceStateManager.mRemoteServiceStateMap.get("dexonpc_connection_state")).intValue() == 3 ? 1 : 0) + (((Integer) semRemoteServiceStateManager.mRemoteServiceStateMap.get("mcf_continuity_nearby_device_state")).intValue() != 0 ? 1 : 0) + (((Integer) semRemoteServiceStateManager.mRemoteServiceStateMap.get("samsungflow_clipboard_sync_state")).intValue() != 0 ? 1 : 0) + (((Integer) semRemoteServiceStateManager.mRemoteServiceStateMap.get("ltw_clipboard_sync_state")).intValue() != 0 ? 1 : 0) + (((Integer) semRemoteServiceStateManager.mRemoteServiceStateMap.get("multi_control_connection_state")).intValue() == 0 ? 0 : 1) > 0) {
                    String str2 = SystemProperties.get("ro.build.characteristics");
                    string = (str2 == null || !str2.contains("tablet")) ? semClipboardToastController.mContext.getResources().getString(R.string.clipboard_mcf_copied_toast_on_this_phone_or_connected_devices) : semClipboardToastController.mContext.getResources().getString(R.string.clipboard_mcf_copied_toast_on_this_tablet_or_connected_devices);
                } else {
                    string = semClipboardToastController.mContext.getResources().getString(R.string.clipboard_copied_toast);
                }
            } else {
                String[] split = label.toString().split(";");
                String str3 = split[0];
                String replaceFirst = split.length > 1 ? split[1].replaceFirst("device_name=", "") : "";
                semRemoteServiceStateManager.getClass();
                if (!(("com.samsung.android.honeyboard".equals(str) && "mcf_continuity".equals(str3)) || (("com.sec.android.app.dexonpc".equals(str) && ("startDoPCopy".equals(str3) || "startDoPDrag".equals(str3))) || (("com.samsung.android.mdx".equals(str) && "com.samsung.android.mdx".equals(str3)) || (("com.samsung.android.galaxycontinuity".equals(str) && "com.samsung.android.galaxycontinuity".equals(str3)) || ("com.samsung.android.inputshare".equals(str) && "com.samsung.android.inputshare".equals(str3)))))) || replaceFirst.isBlank()) {
                    KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("ClipLabel is not empty but not for remote service. ", str, "SemClipboardToastController");
                    string = semClipboardToastController.mContext.getResources().getString(R.string.clipboard_copied_toast);
                } else {
                    string = semClipboardToastController.mContext.getResources().getString(R.string.clipboard_copied_from_remote_device, replaceFirst);
                }
            }
            Display[] displays = semClipboardToastController.mDisplayManager.getDisplays();
            int length = displays.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    Display[] displays2 = semClipboardToastController.mDisplayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
                    int length2 = displays2.length;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length2) {
                            i = 0;
                            display = semClipboardToastController.mDisplayManager.getDisplay(0);
                            break;
                        } else {
                            display = displays2[i3];
                            if (display.getState() == 2) {
                                i = 0;
                                break;
                            }
                            i3++;
                        }
                    }
                } else {
                    display = displays[i2];
                    if ((display.getFlags() & 131072) != 0) {
                        i = 0;
                        break;
                    }
                    i2++;
                }
            }
            Toast.makeText(semClipboardToastController.mContext.createDisplayContext(display), string, i).show();
            try {
                Log.i("SemClipboardToastController", "Copy toast is shown by " + semClipboardToastController.mContext.getPackageManager().getPackageUid(str, PackageManager.PackageInfoFlags.of(0L)));
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("SemClipboardToastController", "Unknown package is access to show toast : " + str);
            }
            Log.i("SemRemoteServiceStateManager", "remote service connection state. dop(" + semRemoteServiceStateManager.mRemoteServiceStateMap.get("dexonpc_connection_state") + "), mcf(" + semRemoteServiceStateManager.mRemoteServiceStateMap.get("mcf_continuity_nearby_device_state") + "), sf(" + semRemoteServiceStateManager.mRemoteServiceStateMap.get("samsungflow_clipboard_sync_state") + "), ltw(" + semRemoteServiceStateManager.mRemoteServiceStateMap.get("ltw_clipboard_sync_state") + "), mc(" + semRemoteServiceStateManager.mRemoteServiceStateMap.get("multi_control_connection_state") + ")");
            SemRemoteServiceStateManager.ConnectionStateClearHandler connectionStateClearHandler = semRemoteServiceStateManager.mClipboardClearHandler;
            connectionStateClearHandler.removeEqualMessages(101, Integer.valueOf(ActivityManager.getCurrentUser()));
            connectionStateClearHandler.sendMessageDelayed(Message.obtain(connectionStateClearHandler, 101), 10000L);
        }
        semClipboardToastController.lastCopiedTime = System.currentTimeMillis();
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mClipboardManagerForUser.addPrimaryClipChangedListener(this);
    }
}
