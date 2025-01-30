package com.android.systemui.clipboardoverlay;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
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
import com.samsung.android.util.SemViewUtils;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ClipboardListener implements CoreStartable, ClipboardManager.OnPrimaryClipChangedListener {
    static final String EXTRA_SUPPRESS_OVERLAY = "com.android.systemui.SUPPRESS_CLIPBOARD_OVERLAY";
    static final String SHELL_PACKAGE = "com.android.shell";
    public final ClipboardManager mClipboardManager;
    public final ClipboardToast mClipboardToast;
    public final Context mContext;
    public final KeyguardManager mKeyguardManager;
    public SemClipboardToastController mSemClipboardToast;
    public final Provider mSemClipboardToastProvider;
    public final UiEventLogger mUiEventLogger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.clipboardoverlay.ClipboardListener$1 */
    public final class C11461 extends Thread {
        public C11461() {
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
                if (!ClipboardListener.this.mKeyguardManager.isDeviceLocked()) {
                    if ((Settings.Secure.getInt(ClipboardListener.this.mContext.getContentResolver(), "user_setup_complete", 0) == 1) && primaryClipDescription != null) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardListener$1$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ClipboardListener.C11461 c11461 = ClipboardListener.C11461.this;
                                ClipDescription clipDescription = primaryClipDescription;
                                String str = primaryClipSource;
                                c11461.getClass();
                                try {
                                    ClipboardListener.this.showCopyToast(clipDescription, str);
                                } catch (RuntimeException e) {
                                    Log.e("ClipboardListener", "showCopyToast exception", e);
                                }
                            }
                        });
                        return;
                    }
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
                            ClipboardListener.C11461 c11461 = ClipboardListener.C11461.this;
                            String str = primaryClipSource;
                            c11461.getClass();
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
        this.mKeyguardManager = keyguardManager;
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
        new C11461().start();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(19:6|(1:8)(1:88)|9|(1:11)(1:87)|12|(1:14)(1:86)|15|(1:17)(1:85)|18|(1:20)(1:84)|21|(3:(4:24|(1:81)(1:28)|(3:30|(1:60)(1:36)|(3:38|(1:59)(1:43)|(3:45|(1:58)(1:49)|(2:51|(1:57)(1:55)))))|(8:62|(1:64)(1:80)|65|(1:(2:67|(2:70|71)(1:69))(2:78|79))|72|73|74|75))|82|(0))|83|65|(2:(0)(0)|69)|72|73|74|75) */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x010e, code lost:
    
        if (("com.samsung.android.inputshare".equals(r19) && "com.samsung.android.inputshare".equals(r6)) != false) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0193, code lost:
    
        android.util.Log.e("SemClipboardToastController", "Unknown package is access to show toast : " + r19);
     */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x015e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void showCopyToast(ClipDescription clipDescription, String str) {
        String string;
        Display[] displays;
        int length;
        int i;
        int i2;
        Display display;
        Display[] displayArr;
        if (this.mSemClipboardToast == null) {
            this.mSemClipboardToast = (SemClipboardToastController) this.mSemClipboardToastProvider.get();
        }
        SemClipboardToastController semClipboardToastController = this.mSemClipboardToast;
        semClipboardToastController.getClass();
        if (System.currentTimeMillis() - semClipboardToastController.lastCopiedTime > TimeUnit.SECONDS.toMillis(1L)) {
            SemRemoteServiceStateManager semRemoteServiceStateManager = semClipboardToastController.mRemoteServiceStateManager;
            HashMap hashMap = semRemoteServiceStateManager.mRemoteServiceStateMap;
            boolean z = true;
            int i3 = (((Integer) hashMap.get("dexonpc_connection_state")).intValue() == 3 ? 1 : 0) + (((Integer) hashMap.get("mcf_continuity_nearby_device_state")).intValue() != 0 ? 1 : 0) + (((Integer) hashMap.get("samsungflow_clipboard_sync_state")).intValue() != 0 ? 1 : 0) + (((Integer) hashMap.get("ltw_clipboard_sync_state")).intValue() != 0 ? 1 : 0) + (((Integer) hashMap.get("multi_control_connection_state")).intValue() != 0 ? 1 : 0);
            Context context = semClipboardToastController.mContext;
            if (i3 > 0) {
                if (clipDescription != null) {
                    CharSequence label = clipDescription.getLabel();
                    PersistableBundle extras = clipDescription.getExtras();
                    if (!("com.samsung.android.honeyboard".equals(str) && "mcf_continuity".equals(label))) {
                        if (!("com.sec.android.app.dexonpc".equals(str) && ("startDoPCopy".equals(label) || "startDoPDrag".equals(label)))) {
                            if (!("com.samsung.android.mdx".equals(str) && extras != null && extras.containsKey("com.microsoft.appmanager"))) {
                                if (!("com.samsung.android.galaxycontinuity".equals(str) && "com.samsung.android.galaxycontinuity".equals(label))) {
                                }
                            }
                        }
                    }
                    if (!z) {
                        string = SemViewUtils.isTablet() ? context.getResources().getString(R.string.clipboard_mcf_copied_toast_on_this_tablet_or_connected_devices) : context.getResources().getString(R.string.clipboard_mcf_copied_toast_on_this_phone_or_connected_devices);
                        DisplayManager displayManager = semClipboardToastController.mDisplayManager;
                        displays = displayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
                        length = displays.length;
                        i = 0;
                        while (true) {
                            if (i < length) {
                                i2 = 0;
                                display = displayManager.getDisplay(0);
                                break;
                            }
                            Display display2 = displays[i];
                            displayArr = displays;
                            if (display2.getState() == 2) {
                                display = display2;
                                i2 = 0;
                                break;
                            } else {
                                i++;
                                displays = displayArr;
                            }
                        }
                        Toast.makeText(context.createDisplayContext(display), string, i2).show();
                        Log.i("SemClipboardToastController", "Copy toast is shown by " + context.getPackageManager().getPackageUid(str, PackageManager.PackageInfoFlags.of(0L)));
                        StringBuilder sb = new StringBuilder("remote service connection state. dop(");
                        HashMap hashMap2 = semRemoteServiceStateManager.mRemoteServiceStateMap;
                        sb.append(hashMap2.get("dexonpc_connection_state"));
                        sb.append("), mcf(");
                        sb.append(hashMap2.get("mcf_continuity_nearby_device_state"));
                        sb.append("), sf(");
                        sb.append(hashMap2.get("samsungflow_clipboard_sync_state"));
                        sb.append("), ltw(");
                        sb.append(hashMap2.get("ltw_clipboard_sync_state"));
                        sb.append("), mc(");
                        sb.append(hashMap2.get("multi_control_connection_state"));
                        sb.append(")");
                        Log.i("SemRemoteServiceStateManager", sb.toString());
                        Integer valueOf = Integer.valueOf(ActivityManager.getCurrentUser());
                        SemRemoteServiceStateManager.ConnectionStateClearHandler connectionStateClearHandler = semRemoteServiceStateManager.mClipboardClearHandler;
                        connectionStateClearHandler.removeEqualMessages(101, valueOf);
                        connectionStateClearHandler.sendMessageDelayed(Message.obtain(connectionStateClearHandler, 101), 10000L);
                    }
                }
                z = false;
                if (!z) {
                }
            }
            string = context.getResources().getString(R.string.clipboard_copied_toast);
            DisplayManager displayManager2 = semClipboardToastController.mDisplayManager;
            displays = displayManager2.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
            length = displays.length;
            i = 0;
            while (true) {
                if (i < length) {
                }
                i++;
                displays = displayArr;
            }
            Toast.makeText(context.createDisplayContext(display), string, i2).show();
            Log.i("SemClipboardToastController", "Copy toast is shown by " + context.getPackageManager().getPackageUid(str, PackageManager.PackageInfoFlags.of(0L)));
            StringBuilder sb2 = new StringBuilder("remote service connection state. dop(");
            HashMap hashMap22 = semRemoteServiceStateManager.mRemoteServiceStateMap;
            sb2.append(hashMap22.get("dexonpc_connection_state"));
            sb2.append("), mcf(");
            sb2.append(hashMap22.get("mcf_continuity_nearby_device_state"));
            sb2.append("), sf(");
            sb2.append(hashMap22.get("samsungflow_clipboard_sync_state"));
            sb2.append("), ltw(");
            sb2.append(hashMap22.get("ltw_clipboard_sync_state"));
            sb2.append("), mc(");
            sb2.append(hashMap22.get("multi_control_connection_state"));
            sb2.append(")");
            Log.i("SemRemoteServiceStateManager", sb2.toString());
            Integer valueOf2 = Integer.valueOf(ActivityManager.getCurrentUser());
            SemRemoteServiceStateManager.ConnectionStateClearHandler connectionStateClearHandler2 = semRemoteServiceStateManager.mClipboardClearHandler;
            connectionStateClearHandler2.removeEqualMessages(101, valueOf2);
            connectionStateClearHandler2.sendMessageDelayed(Message.obtain(connectionStateClearHandler2, 101), 10000L);
        }
        semClipboardToastController.lastCopiedTime = System.currentTimeMillis();
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mClipboardManager.addPrimaryClipChangedListener(this);
    }
}
