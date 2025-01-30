package com.android.systemui.controls.management;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Pair;
import com.android.systemui.R;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.ui.ControlsActivity;
import com.android.systemui.controls.ui.SelectedItem;
import com.android.systemui.controls.panels.AuthorizedPanelsRepositoryImpl;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.controls.panels.SelectedComponentRepositoryImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.Collections;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
final /* synthetic */ class ControlsProviderSelectorActivity$onStart$1 extends FunctionReferenceImpl implements Function1 {
    public ControlsProviderSelectorActivity$onStart$1(Object obj) {
        super(1, obj, ControlsProviderSelectorActivity.class, "onAppSelected", "onAppSelected(Lcom/android/systemui/controls/ControlsServiceInfo;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        final ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj;
        final ControlsProviderSelectorActivity controlsProviderSelectorActivity = (ControlsProviderSelectorActivity) this.receiver;
        Dialog dialog = controlsProviderSelectorActivity.dialog;
        if (dialog != null) {
            dialog.cancel();
        }
        if (controlsServiceInfo.panelActivity == null) {
            final ComponentName componentName = controlsServiceInfo.componentName;
            controlsProviderSelectorActivity.executor.execute(new Runnable() { // from class: com.android.systemui.controls.management.ControlsProviderSelectorActivity$launchFavoritingActivity$1
                @Override // java.lang.Runnable
                public final void run() {
                    ComponentName componentName2 = componentName;
                    if (componentName2 != null) {
                        ControlsProviderSelectorActivity controlsProviderSelectorActivity2 = controlsProviderSelectorActivity;
                        Intent intent = new Intent(controlsProviderSelectorActivity2.getApplicationContext(), (Class<?>) ControlsFavoritingActivity.class);
                        intent.putExtra("extra_app_label", ((ControlsListingControllerImpl) controlsProviderSelectorActivity2.listingController).getAppLabel(componentName2));
                        intent.putExtra("android.intent.extra.COMPONENT_NAME", componentName2);
                        intent.putExtra("extra_source", (byte) 1);
                        controlsProviderSelectorActivity2.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(controlsProviderSelectorActivity2, new Pair[0]).toBundle());
                    }
                }
            });
        } else {
            final CharSequence loadLabel = controlsServiceInfo.loadLabel();
            final Consumer consumer = new Consumer() { // from class: com.android.systemui.controls.management.ControlsProviderSelectorActivity$onAppSelected$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj2) {
                    if (((Boolean) obj2).booleanValue()) {
                        ((AuthorizedPanelsRepositoryImpl) ControlsProviderSelectorActivity.this.authorizedPanelsRepository).addAuthorizedPanels(Collections.singleton(controlsServiceInfo.componentName.getPackageName()));
                        SelectedItem.PanelItem panelItem = new SelectedItem.PanelItem(loadLabel, controlsServiceInfo.componentName);
                        ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) ControlsProviderSelectorActivity.this.controlsController;
                        controlsControllerImpl.getClass();
                        ((SelectedComponentRepositoryImpl) controlsControllerImpl.selectedComponentRepository).setSelectedComponent(new SelectedComponentRepository.SelectedComponent(panelItem));
                        ControlsProviderSelectorActivity.this.m118x8067cc31();
                        ControlsProviderSelectorActivity controlsProviderSelectorActivity2 = ControlsProviderSelectorActivity.this;
                        controlsProviderSelectorActivity2.getClass();
                        controlsProviderSelectorActivity2.startActivity(new Intent(controlsProviderSelectorActivity2.getApplicationContext(), (Class<?>) ControlsActivity.class), ActivityOptions.makeSceneTransitionAnimation(controlsProviderSelectorActivity2, new Pair[0]).toBundle());
                    }
                    ControlsProviderSelectorActivity.this.dialog = null;
                }
            };
            PanelConfirmationDialogFactory panelConfirmationDialogFactory = controlsProviderSelectorActivity.panelConfirmationDialogFactory;
            panelConfirmationDialogFactory.getClass();
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.management.PanelConfirmationDialogFactory$createConfirmationDialog$listener$1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    consumer.accept(Boolean.valueOf(i == -1));
                }
            };
            Object invoke = panelConfirmationDialogFactory.internalDialogFactory.invoke(controlsProviderSelectorActivity);
            SystemUIDialog systemUIDialog = (SystemUIDialog) invoke;
            systemUIDialog.setTitle(systemUIDialog.getContext().getString(R.string.controls_panel_authorization_title, loadLabel));
            systemUIDialog.setMessage(systemUIDialog.getContext().getString(R.string.controls_panel_authorization, loadLabel));
            systemUIDialog.setCanceledOnTouchOutside(true);
            systemUIDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.controls.management.PanelConfirmationDialogFactory$createConfirmationDialog$1$1
                @Override // android.content.DialogInterface.OnCancelListener
                public final void onCancel(DialogInterface dialogInterface) {
                    consumer.accept(Boolean.FALSE);
                }
            });
            systemUIDialog.setPositiveButton(R.string.controls_dialog_ok, onClickListener);
            systemUIDialog.setNeutralButton(R.string.cancel, onClickListener);
            Dialog dialog2 = (Dialog) invoke;
            dialog2.show();
            controlsProviderSelectorActivity.dialog = dialog2;
        }
        return Unit.INSTANCE;
    }
}
