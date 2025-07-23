package com.android.wm.shell.controlpanel.action;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyCache;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import com.android.systemui.R;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import com.samsung.android.content.clipboard.SemClipboardEventListener;
import com.samsung.android.content.clipboard.SemClipboardManager;
import com.samsung.android.content.clipboard.data.SemClipData;
import com.samsung.android.emergencymode.SemEmergencyManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ScreenCaptureAction extends MenuActionType {
    public final Context context;
    public final AnonymousClass1 mHandler = new Handler() { // from class: com.android.wm.shell.controlpanel.action.ScreenCaptureAction.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 9) {
                return;
            }
            ScreenCaptureAction screenCaptureAction = ScreenCaptureAction.this;
            screenCaptureAction.mHandler.removeMessages(9);
            ((SemClipboardManager) screenCaptureAction.context.getSystemService("semclipboard")).unregisterClipboardEventListener(screenCaptureAction.mClipboardEventListener);
        }
    };
    public final AnonymousClass2 mClipboardEventListener = new SemClipboardEventListener() { // from class: com.android.wm.shell.controlpanel.action.ScreenCaptureAction.2
        public final void onClipboardUpdated(int i, SemClipData semClipData) {
            if (i == 1) {
                Log.i("ScreenCaptureAction", "clip added. doScreenCaptureDone");
                ScreenCaptureAction screenCaptureAction = ScreenCaptureAction.this;
                screenCaptureAction.mHandler.removeMessages(9);
                ((SemClipboardManager) screenCaptureAction.context.getSystemService("semclipboard")).unregisterClipboardEventListener(screenCaptureAction.mClipboardEventListener);
            }
        }

        public final void onFilterUpdated(int i) {
        }
    };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.controlpanel.action.ScreenCaptureAction$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.wm.shell.controlpanel.action.ScreenCaptureAction$2] */
    private ScreenCaptureAction(Context context) {
        this.context = null;
        this.context = context;
    }

    public static ScreenCaptureAction createAction(FlexPanelActivity flexPanelActivity) {
        return new ScreenCaptureAction(flexPanelActivity);
    }

    @Override // com.android.wm.shell.controlpanel.action.MenuActionType
    public final void doControlAction(String str, FlexPanelActivity flexPanelActivity) {
        Context context = this.context;
        DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
        ActivityManager.RunningTaskInfo runningTaskExcept = ControlPanelUtils.getRunningTaskExcept(context);
        if (!devicePolicyCache.isScreenCaptureAllowed(runningTaskExcept != null ? runningTaskExcept.userId : 0)) {
            Log.d("ScreenCaptureAction", "ScreenCapure is bloked by knox mode");
            Toast.makeText(context, R.string.assistant_menu_knox_message, 0).show();
        } else if (!SemEmergencyManager.isEmergencyMode(context)) {
            new Handler().postDelayed(new Runnable() { // from class: com.android.wm.shell.controlpanel.action.ScreenCaptureAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenCaptureAction screenCaptureAction = ScreenCaptureAction.this;
                    screenCaptureAction.getClass();
                    Point point = new Point();
                    ((WindowManager) screenCaptureAction.context.getSystemService("window")).getDefaultDisplay().getRealSize(point);
                    Rect rect = new Rect();
                    rect.set(0, 0, point.x, point.y / 2);
                    Intent intent = new Intent("com.samsung.android.capture.ScreenshotExecutor");
                    intent.putExtra("capturedOrigin", 6);
                    intent.putExtra("rect", rect);
                    intent.setPackage("android");
                    screenCaptureAction.context.sendBroadcastAsUser(intent, UserHandle.SEM_CURRENT);
                    ((SemClipboardManager) screenCaptureAction.context.getSystemService("semclipboard")).registerClipboardEventListener(screenCaptureAction.mClipboardEventListener);
                    screenCaptureAction.mHandler.sendEmptyMessageDelayed(9, 5000L);
                }
            }, 50L);
        } else {
            Log.i("ScreenCaptureAction", "screen capture is blocked by emergency mode");
            Toast.makeText(context, context.getResources().getString(R.string.toast_can_not_use_while_emergency_mode, context.getResources().getString(R.string.flex_panel_screen_capture)), 1).show();
        }
    }
}
