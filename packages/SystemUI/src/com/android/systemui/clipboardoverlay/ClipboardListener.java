package com.android.systemui.clipboardoverlay;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.clipboardoverlay.ClipboardListener;
import com.android.systemui.clipboardoverlay.SemRemoteServiceStateManager;
import java.util.concurrent.TimeUnit;
import javax.inject.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ClipboardListener implements CoreStartable, ClipboardManager.OnPrimaryClipChangedListener {
    static final String EXTRA_SUPPRESS_OVERLAY = "com.android.systemui.SUPPRESS_CLIPBOARD_OVERLAY";
    static final String SHELL_PACKAGE = "com.android.shell";
    public final ClipboardManager mClipboardManager;
    public final ClipboardToast mClipboardToast;
    public final Context mContext;
    public SemClipboardToastController mSemClipboardToast;
    public final Provider mSemClipboardToastProvider;
    public final UiEventLogger mUiEventLogger;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.clipboardoverlay.ClipboardListener$1, reason: invalid class name */
    public final class AnonymousClass1 extends Thread {
        public AnonymousClass1() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            if (ClipboardListener.this.mClipboardManager.hasPrimaryClip()) {
                final String primaryClipSource = ClipboardListener.this.mClipboardManager.getPrimaryClipSource();
                final ClipDescription primaryClipDescription = ClipboardListener.this.mClipboardManager.getPrimaryClipDescription();
                ClipboardListener clipboardListener = ClipboardListener.this;
                boolean z = SystemProperties.getBoolean("ro.boot.qemu", false);
                clipboardListener.getClass();
                if (((!z && !ClipboardListener.SHELL_PACKAGE.equals(primaryClipSource)) || primaryClipDescription == null || primaryClipDescription.getExtras() == null) ? false : primaryClipDescription.getExtras().getBoolean(ClipboardListener.EXTRA_SUPPRESS_OVERLAY, false)) {
                    Log.i("ClipboardListener", "Clipboard overlay suppressed.");
                    return;
                }
                if (Settings.Secure.getInt(ClipboardListener.this.mContext.getContentResolver(), "user_setup_complete", 0) == 1) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardListener$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ClipboardListener.AnonymousClass1 anonymousClass1 = ClipboardListener.AnonymousClass1.this;
                            ClipDescription clipDescription = primaryClipDescription;
                            String str = primaryClipSource;
                            anonymousClass1.getClass();
                            try {
                                ClipboardListener.this.showCopyToast(clipDescription, str);
                            } catch (RuntimeException e) {
                                Log.e("ClipboardListener", "showCopyToast exception", e);
                            }
                        }
                    });
                    return;
                }
                ClipboardListener clipboardListener2 = ClipboardListener.this;
                clipboardListener2.getClass();
                if (primaryClipDescription != null) {
                    if (primaryClipDescription.getClassificationStatus() == 3) {
                        r4 = !(clipboardListener2.mClipboardToast.mCopiedToast != null);
                    } else {
                        r4 = true;
                    }
                }
                if (r4) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardListener$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ClipboardListener.AnonymousClass1 anonymousClass1 = ClipboardListener.AnonymousClass1.this;
                            String str = primaryClipSource;
                            anonymousClass1.getClass();
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
        }
    }

    public ClipboardListener(Context context, Provider provider, Provider provider2, ClipboardToast clipboardToast, ClipboardManager clipboardManager, KeyguardManager keyguardManager, UiEventLogger uiEventLogger) {
        this.mContext = context;
        this.mSemClipboardToastProvider = provider2;
        this.mClipboardToast = clipboardToast;
        this.mClipboardManager = clipboardManager;
        this.mUiEventLogger = uiEventLogger;
    }

    public static boolean shouldSuppressOverlay(ClipData clipData, String str, boolean z) {
        if ((!z && !SHELL_PACKAGE.equals(str)) || clipData == null || clipData.getDescription().getExtras() == null) {
            return false;
        }
        return clipData.getDescription().getExtras().getBoolean(EXTRA_SUPPRESS_OVERLAY, false);
    }

    @Override // android.content.ClipboardManager.OnPrimaryClipChangedListener
    public final void onPrimaryClipChanged() {
        new AnonymousClass1().start();
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
            if ((((Integer) semRemoteServiceStateManager.mRemoteServiceStateMap.get("dexonpc_connection_state")).intValue() == 3 ? 1 : 0) + (((Integer) semRemoteServiceStateManager.mRemoteServiceStateMap.get("mcf_continuity_nearby_device_state")).intValue() != 0 ? 1 : 0) + (((Integer) semRemoteServiceStateManager.mRemoteServiceStateMap.get("samsungflow_clipboard_sync_state")).intValue() != 0 ? 1 : 0) + (((Integer) semRemoteServiceStateManager.mRemoteServiceStateMap.get("ltw_clipboard_sync_state")).intValue() != 0 ? 1 : 0) + (((Integer) semRemoteServiceStateManager.mRemoteServiceStateMap.get("multi_control_connection_state")).intValue() != 0 ? 1 : 0) <= 0 || clipDescription == null) {
                string = semClipboardToastController.mContext.getResources().getString(R.string.clipboard_copied_toast);
            } else {
                CharSequence label = clipDescription.getLabel();
                if (label == null || label.isEmpty()) {
                    string = semClipboardToastController.getRemoteSenderToastString();
                } else {
                    String[] split = label.toString().split(";");
                    String str2 = split[0];
                    String replaceFirst = split.length > 1 ? split[1].replaceFirst("device_name=", "") : "";
                    if (!(("com.samsung.android.honeyboard".equals(str) && "mcf_continuity".equals(str2)) || (("com.sec.android.app.dexonpc".equals(str) && ("startDoPCopy".equals(str2) || "startDoPDrag".equals(str2))) || (("com.samsung.android.mdx".equals(str) && "com.samsung.android.mdx".equals(str2)) || (("com.samsung.android.galaxycontinuity".equals(str) && "com.samsung.android.galaxycontinuity".equals(str2)) || ("com.samsung.android.inputshare".equals(str) && "com.samsung.android.inputshare".equals(str2)))))) || replaceFirst.isBlank()) {
                        Log.i("SemClipboardToastController", "Remote service is connected but label is not empty and not for remote service. " + str);
                        string = semClipboardToastController.getRemoteSenderToastString();
                    } else {
                        string = semClipboardToastController.mContext.getResources().getString(R.string.clipboard_copied_from_remote_device, replaceFirst);
                    }
                }
            }
            Display[] displays = semClipboardToastController.mDisplayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
            int length = displays.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    i = 0;
                    display = semClipboardToastController.mDisplayManager.getDisplay(0);
                    break;
                } else {
                    display = displays[i2];
                    if (display.getState() == 2) {
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
            Integer valueOf = Integer.valueOf(ActivityManager.getCurrentUser());
            SemRemoteServiceStateManager.ConnectionStateClearHandler connectionStateClearHandler = semRemoteServiceStateManager.mClipboardClearHandler;
            connectionStateClearHandler.removeEqualMessages(101, valueOf);
            connectionStateClearHandler.sendMessageDelayed(Message.obtain(connectionStateClearHandler, 101), 10000L);
        }
        semClipboardToastController.lastCopiedTime = System.currentTimeMillis();
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mClipboardManager.addPrimaryClipChangedListener(this);
    }
}
