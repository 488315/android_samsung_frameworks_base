package com.android.p038wm.shell.controlpanel.action;

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
import com.android.p038wm.shell.controlpanel.GridUIManager;
import com.android.p038wm.shell.controlpanel.utils.ControlPanelUtils;
import com.android.systemui.R;
import com.samsung.android.content.clipboard.SemClipboardEventListener;
import com.samsung.android.content.clipboard.SemClipboardManager;
import com.samsung.android.content.clipboard.data.SemClipData;
import com.samsung.android.emergencymode.SemEmergencyManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenCaptureAction extends MenuActionType {
    public final Context context;
    public final HandlerC39091 mHandler = new Handler() { // from class: com.android.wm.shell.controlpanel.action.ScreenCaptureAction.1
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
    public final C39102 mClipboardEventListener = new SemClipboardEventListener() { // from class: com.android.wm.shell.controlpanel.action.ScreenCaptureAction.2
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

    public static ScreenCaptureAction createAction(Context context) {
        return new ScreenCaptureAction(context);
    }

    @Override // com.android.p038wm.shell.controlpanel.action.MenuActionType
    public final void doControlAction(String str, GridUIManager gridUIManager) {
        DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
        Context context = this.context;
        boolean z = false;
        if (!devicePolicyCache.isScreenCaptureAllowed(ControlPanelUtils.getTopTaskUserId(context))) {
            Log.d("ScreenCaptureAction", "ScreenCapure is bloked by knox mode");
            Toast.makeText(context, R.string.assistant_menu_knox_message, 0).show();
        } else if (SemEmergencyManager.isEmergencyMode(context)) {
            Log.i("ScreenCaptureAction", "screen capture is blocked by emergency mode");
            gridUIManager.getClass();
            Toast.makeText(context, context.getResources().getString(R.string.toast_can_not_use_while_emergency_mode, context.getResources().getString(R.string.flex_panel_screen_capture)), 1).show();
        } else {
            z = true;
        }
        if (z) {
            gridUIManager.getClass();
            new Handler().postDelayed(new Runnable() { // from class: com.android.wm.shell.controlpanel.action.ScreenCaptureAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenCaptureAction screenCaptureAction = ScreenCaptureAction.this;
                    screenCaptureAction.getClass();
                    Point point = new Point();
                    Context context2 = screenCaptureAction.context;
                    ((WindowManager) context2.getSystemService("window")).getDefaultDisplay().getRealSize(point);
                    Rect rect = new Rect();
                    rect.set(0, 0, point.x, point.y / 2);
                    Intent intent = new Intent("com.samsung.android.capture.ScreenshotExecutor");
                    intent.putExtra("capturedOrigin", 6);
                    intent.putExtra("rect", rect);
                    intent.setPackage("android");
                    context2.sendBroadcastAsUser(intent, UserHandle.SEM_CURRENT);
                    ((SemClipboardManager) context2.getSystemService("semclipboard")).registerClipboardEventListener(screenCaptureAction.mClipboardEventListener);
                    screenCaptureAction.mHandler.sendEmptyMessageDelayed(9, 5000L);
                }
            }, 50L);
        }
    }
}
