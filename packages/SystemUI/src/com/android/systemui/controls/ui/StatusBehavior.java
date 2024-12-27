package com.android.systemui.controls.ui;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.service.controls.Control;
import android.view.View;
import android.view.Window;
import com.android.systemui.R;
import com.android.systemui.popup.util.PopupUIUtil;

public final class StatusBehavior implements Behavior, SecBehavior {
    public ControlViewHolder cvh;

    @Override // com.android.systemui.controls.ui.Behavior
    public final void bind(final ControlWithState controlWithState, int i) {
        int i2;
        final Control control = controlWithState.control;
        if (control != null) {
            ControlViewHolder controlViewHolder = this.cvh;
            if (controlViewHolder == null) {
                controlViewHolder = null;
            }
            controlViewHolder.layout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.StatusBehavior$bind$1$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ControlViewHolder controlViewHolder2 = StatusBehavior.this.cvh;
                    if (controlViewHolder2 == null) {
                        controlViewHolder2 = null;
                    }
                    SecControlActionCoordinator secControlActionCoordinator = controlViewHolder2.getSecControlViewHolder().secControlActionCoordinator;
                    if (secControlActionCoordinator != null) {
                        ControlViewHolder controlViewHolder3 = StatusBehavior.this.cvh;
                        ((ControlActionCoordinatorImpl) secControlActionCoordinator).touchCard(controlViewHolder3 != null ? controlViewHolder3 : null, control.getControlTemplate().getTemplateId(), control);
                    }
                }
            });
        }
        Control control2 = controlWithState.control;
        int status = control2 != null ? control2.getStatus() : 0;
        if (status == 2) {
            ControlViewHolder controlViewHolder2 = this.cvh;
            if (controlViewHolder2 == null) {
                controlViewHolder2 = null;
            }
            controlViewHolder2.layout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.StatusBehavior$bind$msg$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    StatusBehavior statusBehavior = StatusBehavior.this;
                    final ControlViewHolder controlViewHolder3 = statusBehavior.cvh;
                    if (controlViewHolder3 == null) {
                        controlViewHolder3 = null;
                    }
                    final ControlWithState controlWithState2 = controlWithState;
                    statusBehavior.getClass();
                    PackageManager packageManager = controlViewHolder3.context.getPackageManager();
                    CharSequence applicationLabel = packageManager.getApplicationLabel(packageManager.getApplicationInfo(controlWithState2.componentName.getPackageName(), 128));
                    final AlertDialog.Builder builder = new AlertDialog.Builder(controlViewHolder3.context, 2132018525);
                    Resources resources = controlViewHolder3.context.getResources();
                    builder.setTitle(resources.getString(R.string.sec_controls_error_removed_title, controlViewHolder3.title.getText()));
                    builder.setMessage(resources.getString(R.string.sec_controls_error_removed_message, applicationLabel));
                    builder.setPositiveButton(R.string.sec_controls_open_app, new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.StatusBehavior$showSecNotFoundDialog$builder$1$1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i3) {
                            PendingIntent appIntent;
                            try {
                                Control control3 = ControlWithState.this.control;
                                if (control3 != null && (appIntent = control3.getAppIntent()) != null) {
                                    appIntent.send();
                                }
                                builder.getContext().sendBroadcast(new Intent(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS));
                            } catch (PendingIntent.CanceledException unused) {
                                controlViewHolder3.setErrorStatus();
                            }
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setNegativeButton(R.string.controls_dialog_cancel, new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.StatusBehavior$showSecNotFoundDialog$builder$1$2
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i3) {
                            dialogInterface.cancel();
                        }
                    });
                    final AlertDialog create = builder.create();
                    create.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.controls.ui.StatusBehavior$showSecNotFoundDialog$1$1
                        @Override // android.content.DialogInterface.OnShowListener
                        public final void onShow(DialogInterface dialogInterface) {
                            int color = create.getContext().getResources().getColor(R.color.basic_interaction_dialog_button, create.getContext().getTheme());
                            AlertDialog alertDialog = create;
                            alertDialog.getButton(-1).setTextColor(color);
                            alertDialog.getButton(-2).setTextColor(color);
                        }
                    });
                    Window window = create.getWindow();
                    if (window != null) {
                        window.setType(2020);
                        create.show();
                    }
                    controlViewHolder3.visibleDialog = create;
                }
            });
            i2 = R.string.sec_controls_error_removed;
        } else if (status == 3) {
            i2 = R.string.sec_controls_error_generic;
        } else if (status != 4) {
            ControlViewHolder controlViewHolder3 = this.cvh;
            if (controlViewHolder3 == null) {
                controlViewHolder3 = null;
            }
            controlViewHolder3.isLoading = true;
            i2 = R.string.controls_loading;
        } else {
            i2 = R.string.sec_controls_error_timeout;
        }
        ControlViewHolder controlViewHolder4 = this.cvh;
        ControlViewHolder controlViewHolder5 = controlViewHolder4 != null ? controlViewHolder4 : null;
        if (controlViewHolder4 == null) {
            controlViewHolder4 = null;
        }
        controlViewHolder5.setStatusText(controlViewHolder4.context.getString(i2), false);
        ControlViewHolder controlViewHolder6 = this.cvh;
        (controlViewHolder6 != null ? controlViewHolder6 : null).applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(i, false, true);
    }

    @Override // com.android.systemui.controls.ui.SecBehavior
    public final void dispose() {
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder == null) {
            controlViewHolder = null;
        }
        controlViewHolder.layout.setOnClickListener(null);
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public final void initialize(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
    }
}
