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
    public static AlertDialog createAccessibilityServiceWarningDialog(
            Context context,
            AccessibilityServiceInfo info,
            final View.OnClickListener allowListener,
            final View.OnClickListener denyListener,
            final View.OnClickListener uninstallListener) {
        AlertDialog.Builder adBuilder =
                new AlertDialog.Builder(context)
                        .setView(
                                createAccessibilityServiceWarningDialogContentView(
                                        context,
                                        info,
                                        allowListener,
                                        denyListener,
                                        uninstallListener))
                        .setTitle(
                                context.getString(
                                        R.string.accessibility_enable_service_title,
                                        getServiceName(context, info)))
                        .setPositiveButton(
                                R.string.accessibility_dialog_button_allow_samsung,
                                (DialogInterface.OnClickListener) null)
                        .setNegativeButton(
                                R.string.accessibility_dialog_button_deny_samsung,
                                (DialogInterface.OnClickListener) null)
                        .setCancelable(true);
        if (!info.getResolveInfo().serviceInfo.applicationInfo.isSystemApp()) {
            adBuilder.setNeutralButton(
                    R.string.accessibility_dialog_button_uninstall_samsung,
                    (DialogInterface.OnClickListener) null);
        }
        AlertDialog ad = adBuilder.create();
        ad.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.android.internal.accessibility.dialog.AccessibilityServiceWarning.1
                    @Override // android.content.DialogInterface.OnShowListener
                    public void onShow(DialogInterface dialog) {
                        AlertDialog alertDialog = (AlertDialog) dialog;
                        Button allowButton = alertDialog.getButton(-1);
                        allowButton.setOnClickListener(View.OnClickListener.this);
                        allowButton.setOnTouchListener(
                                AccessibilityServiceWarning.getTouchConsumingListener());
                        alertDialog.getButton(-2).setOnClickListener(denyListener);
                        alertDialog.getButton(-3).setOnClickListener(uninstallListener);
                    }
                });
        Window window = ad.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.privateFlags |= 524288;
        params.type = 2008;
        window.setAttributes(params);
        return ad;
    }

    public static View createAccessibilityServiceWarningDialogContentView(
            Context context,
            AccessibilityServiceInfo info,
            View.OnClickListener allowListener,
            View.OnClickListener denyListener,
            View.OnClickListener uninstallListener) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LayoutInflater.class);
        View content =
                inflater.inflate(R.layout.accessibility_service_warning_samsung, (ViewGroup) null);
        return content;
    }

    public static View.OnTouchListener getTouchConsumingListener() {
        return new View
                .OnTouchListener() { // from class:
                                     // com.android.internal.accessibility.dialog.AccessibilityServiceWarning$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AccessibilityServiceWarning.lambda$getTouchConsumingListener$0(
                        view, motionEvent);
            }
        };
    }

    static /* synthetic */ boolean lambda$getTouchConsumingListener$0(
            View view, MotionEvent event) {
        if ((event.getFlags() & 1) == 0 && (event.getFlags() & 2) == 0) {
            return false;
        }
        if (event.getAction() == 1) {
            Toast.makeText(
                            view.getContext(),
                            R.string.accessibility_dialog_touch_filtered_warning,
                            0)
                    .show();
        }
        return true;
    }

    private static CharSequence getServiceName(Context context, AccessibilityServiceInfo info) {
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        CharSequence label = info.getResolveInfo().loadLabel(context.getPackageManager());
        return BidiFormatter.getInstance(locale).unicodeWrap(label);
    }
}
