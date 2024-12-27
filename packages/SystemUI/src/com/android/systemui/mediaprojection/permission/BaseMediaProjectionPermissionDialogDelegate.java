package com.android.systemui.mediaprojection.permission;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.statusbar.phone.DialogDelegate;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public abstract class BaseMediaProjectionPermissionDialogDelegate implements DialogDelegate, AdapterView.OnItemSelectedListener {
    public final String appName;
    public TextView cancelButton;
    public final int defaultSelectedMode;
    public AlertDialog dialog;
    public final Integer dialogIconDrawable;
    public final Integer dialogIconTint;
    public TextView dialogTitle;
    public final int hostUid;
    public final MediaProjectionMetricsLogger mediaProjectionMetricsLogger;
    public Spinner screenShareModeSpinner;
    public final List screenShareOptions;
    public ScreenShareOption selectedScreenShareOption;
    public boolean shouldLogCancel;
    public TextView startButton;
    public TextView warning;

    public BaseMediaProjectionPermissionDialogDelegate(List<ScreenShareOption> list, String str, int i, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, Integer num, Integer num2, int i2) {
        this.screenShareOptions = list;
        this.appName = str;
        this.hostUid = i;
        this.mediaProjectionMetricsLogger = mediaProjectionMetricsLogger;
        this.dialogIconDrawable = num;
        this.dialogIconTint = num2;
        this.defaultSelectedMode = i2;
        this.shouldLogCancel = true;
        for (ScreenShareOption screenShareOption : list) {
            if (screenShareOption.mode == this.defaultSelectedMode) {
                this.selectedScreenShareOption = screenShareOption;
                return;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public Integer getOptionsViewLayoutId() {
        return null;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView adapterView, View view, int i, long j) {
        this.selectedScreenShareOption = (ScreenShareOption) this.screenShareOptions.get(i);
        TextView textView = this.warning;
        if (textView == null) {
            textView = null;
        }
        AlertDialog alertDialog = this.dialog;
        textView.setText((alertDialog != null ? alertDialog : null).getContext().getString(this.selectedScreenShareOption.warningText, this.appName));
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onStop(Dialog dialog) {
        if (this.shouldLogCancel) {
            MediaProjectionMetricsLogger mediaProjectionMetricsLogger = this.mediaProjectionMetricsLogger;
            int i = this.hostUid;
            mediaProjectionMetricsLogger.getClass();
            try {
                mediaProjectionMetricsLogger.service.notifyPermissionRequestCancelled(i);
            } catch (RemoteException e) {
                Log.e("MediaProjectionMetricsLogger", "Error notifying server of projection cancelled", e);
            }
            this.shouldLogCancel = false;
        }
    }

    public final void setDialogTitle(int i) {
        AlertDialog alertDialog = this.dialog;
        if (alertDialog == null) {
            alertDialog = null;
        }
        String string = alertDialog.getContext().getString(i, this.appName);
        TextView textView = this.dialogTitle;
        (textView != null ? textView : null).setText(string);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public void onCreate(AlertDialog alertDialog, Bundle bundle) {
        Context applicationContext;
        this.dialog = alertDialog;
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.addPrivateFlags(16);
        }
        Window window2 = alertDialog.getWindow();
        if (window2 != null) {
            window2.setGravity(17);
        }
        alertDialog.setContentView(R.layout.screen_share_dialog);
        this.dialogTitle = (TextView) alertDialog.requireViewById(R.id.screen_share_dialog_title);
        this.warning = (TextView) alertDialog.requireViewById(R.id.text_warning);
        this.startButton = (TextView) alertDialog.requireViewById(android.R.id.button1);
        this.cancelButton = (TextView) alertDialog.requireViewById(android.R.id.button2);
        AlertDialog alertDialog2 = this.dialog;
        if (alertDialog2 == null) {
            alertDialog2 = null;
        }
        ImageView imageView = (ImageView) alertDialog2.requireViewById(R.id.screen_share_dialog_icon);
        if (this.dialogIconTint != null) {
            AlertDialog alertDialog3 = this.dialog;
            if (alertDialog3 == null) {
                alertDialog3 = null;
            }
            imageView.setColorFilter(alertDialog3.getContext().getColor(this.dialogIconTint.intValue()));
        }
        if (this.dialogIconDrawable != null) {
            AlertDialog alertDialog4 = this.dialog;
            if (alertDialog4 == null) {
                alertDialog4 = null;
            }
            imageView.setImageDrawable(alertDialog4.getContext().getDrawable(this.dialogIconDrawable.intValue()));
        }
        for (ScreenShareOption screenShareOption : this.screenShareOptions) {
            if (screenShareOption.mode == this.defaultSelectedMode) {
                this.selectedScreenShareOption = screenShareOption;
                TextView textView = this.warning;
                if (textView == null) {
                    textView = null;
                }
                AlertDialog alertDialog5 = this.dialog;
                if (alertDialog5 == null) {
                    alertDialog5 = null;
                }
                textView.setText(alertDialog5.getContext().getString(this.selectedScreenShareOption.warningText, this.appName));
                AlertDialog alertDialog6 = this.dialog;
                if (alertDialog6 == null) {
                    alertDialog6 = null;
                }
                Display display = alertDialog6.getContext().getDisplay();
                if (display == null || display.getDisplayId() != 2) {
                    AlertDialog alertDialog7 = this.dialog;
                    if (alertDialog7 == null) {
                        alertDialog7 = null;
                    }
                    applicationContext = alertDialog7.getContext().getApplicationContext();
                } else {
                    AlertDialog alertDialog8 = this.dialog;
                    if (alertDialog8 == null) {
                        alertDialog8 = null;
                    }
                    applicationContext = alertDialog8.getContext();
                }
                Intrinsics.checkNotNull(applicationContext);
                OptionsAdapter optionsAdapter = new OptionsAdapter(applicationContext, this.screenShareOptions);
                AlertDialog alertDialog9 = this.dialog;
                if (alertDialog9 == null) {
                    alertDialog9 = null;
                }
                Spinner spinner = (Spinner) alertDialog9.requireViewById(R.id.screen_share_mode_spinner);
                this.screenShareModeSpinner = spinner;
                spinner.setAdapter((SpinnerAdapter) optionsAdapter);
                Spinner spinner2 = this.screenShareModeSpinner;
                if (spinner2 == null) {
                    spinner2 = null;
                }
                spinner2.setOnItemSelectedListener(this);
                Spinner spinner3 = this.screenShareModeSpinner;
                if (spinner3 == null) {
                    spinner3 = null;
                }
                spinner3.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionDialogDelegate$initScreenShareSpinner$1
                    @Override // android.view.View.AccessibilityDelegate
                    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                        accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    }
                });
                Spinner spinner4 = this.screenShareModeSpinner;
                if (spinner4 == null) {
                    spinner4 = null;
                }
                spinner4.setLongClickable(false);
                Iterator it = this.screenShareOptions.iterator();
                int i = 0;
                while (true) {
                    if (!it.hasNext()) {
                        i = -1;
                        break;
                    } else if (((ScreenShareOption) it.next()).mode == this.defaultSelectedMode) {
                        break;
                    } else {
                        i++;
                    }
                }
                Spinner spinner5 = this.screenShareModeSpinner;
                if (spinner5 == null) {
                    spinner5 = null;
                }
                spinner5.setSelection(i, false);
                Integer optionsViewLayoutId = getOptionsViewLayoutId();
                if (optionsViewLayoutId == null) {
                    return;
                }
                AlertDialog alertDialog10 = this.dialog;
                ViewStub viewStub = (ViewStub) (alertDialog10 != null ? alertDialog10 : null).requireViewById(R.id.options_stub);
                viewStub.setLayoutResource(optionsViewLayoutId.intValue());
                viewStub.inflate();
                return;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public BaseMediaProjectionPermissionDialogDelegate(List list, String str, int i, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, Integer num, Integer num2, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, str, i, mediaProjectionMetricsLogger, (i3 & 16) != 0 ? null : num, (i3 & 32) != 0 ? null : num2, (i3 & 64) != 0 ? ((ScreenShareOption) CollectionsKt___CollectionsKt.first(list)).mode : i2);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView adapterView) {
    }
}
