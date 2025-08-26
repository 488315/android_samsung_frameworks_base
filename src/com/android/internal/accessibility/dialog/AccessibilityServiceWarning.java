package com.android.internal.accessibility.dialog;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.BidiFormatter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.android.internal.R;
import java.util.Locale;

/* loaded from: classes5.dex */
public class AccessibilityServiceWarning {
    public static AlertDialog createAccessibilityServiceWarningDialog(Context context, AccessibilityServiceInfo accessibilityServiceInfo, final View.OnClickListener onClickListener, final View.OnClickListener onClickListener2, final View.OnClickListener onClickListener3) {
        AlertDialog.Builder cancelable = new AlertDialog.Builder(context).setView(createAccessibilityServiceWarningDialogContentView(context, accessibilityServiceInfo, onClickListener, onClickListener2, onClickListener3)).setTitle(context.getString(R.string.accessibility_enable_service_title, getServiceName(context, accessibilityServiceInfo))).setPositiveButton(R.string.accessibility_dialog_button_allow_samsung, (DialogInterface.OnClickListener) null).setNegativeButton(R.string.accessibility_dialog_button_deny_samsung, (DialogInterface.OnClickListener) null).setCancelable(true);
        if (!accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.isSystemApp()) {
            cancelable.setNeutralButton(R.string.accessibility_dialog_button_uninstall_samsung, (DialogInterface.OnClickListener) null);
        }
        AlertDialog alertDialogCreate = cancelable.create();
        alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityServiceWarning.1
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialogInterface) {
                AlertDialog alertDialog = (AlertDialog) dialogInterface;
                Button button = alertDialog.getButton(-1);
                button.setOnClickListener(onClickListener);
                button.setOnTouchListener(AccessibilityServiceWarning.getTouchConsumingListener());
                alertDialog.getButton(-2).setOnClickListener(onClickListener2);
                alertDialog.getButton(-3).setOnClickListener(onClickListener3);
            }
        });
        Window window = alertDialogCreate.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.privateFlags |= 524288;
        window.setAttributes(attributes);
        return alertDialogCreate;
    }

    public static View createAccessibilityServiceWarningDialogContentView(Context context, AccessibilityServiceInfo accessibilityServiceInfo, View.OnClickListener onClickListener, View.OnClickListener onClickListener2, View.OnClickListener onClickListener3) {
        return ((LayoutInflater) context.getSystemService(LayoutInflater.class)).inflate(R.layout.accessibility_service_warning_samsung, (ViewGroup) null);
    }

    public static View.OnTouchListener getTouchConsumingListener() {
        return new View.OnTouchListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityServiceWarning$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AccessibilityServiceWarning.lambda$getTouchConsumingListener$0(view, motionEvent);
            }
        };
    }

    static /* synthetic */ boolean lambda$getTouchConsumingListener$0(View view, MotionEvent motionEvent) {
        if ((motionEvent.getFlags() & 1) == 0 && (motionEvent.getFlags() & 2) == 0) {
            return false;
        }
        if (motionEvent.getAction() == 1) {
            Toast.makeText(view.getContext(), R.string.accessibility_dialog_touch_filtered_warning, 0).show();
        }
        return true;
    }

    private static CharSequence getServiceName(Context context, AccessibilityServiceInfo accessibilityServiceInfo) {
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        return BidiFormatter.getInstance(locale).unicodeWrap(accessibilityServiceInfo.getResolveInfo().loadLabel(context.getPackageManager()));
    }
}
