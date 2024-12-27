package com.android.systemui.controls.management;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.service.controls.Control;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.Favorites;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.panels.AuthorizedPanelsRepositoryImpl;
import com.android.systemui.controls.ui.RenderInfo;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public class ControlsRequestDialog extends ComponentActivity implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
    public Control control;
    public ComponentName controlComponent;
    public final ControlsController controller;
    public final ControlsListingController controlsListingController;
    public final ControlsUtil controlsUtil;
    public Dialog dialog;
    public final Executor mainExecutor;
    public final UserTracker userTracker;
    public final ControlsRequestDialog$callback$1 callback = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.management.ControlsRequestDialog$callback$1
        @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
        public final void onServicesUpdated(List list) {
        }
    };
    public final ControlsRequestDialog$userTrackerCallback$1 userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.controls.management.ControlsRequestDialog$userTrackerCallback$1
        public final int startingUser;

        {
            this.startingUser = ((ControlsControllerImpl) ControlsRequestDialog.this.controller).currentUser.getIdentifier();
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            if (i != this.startingUser) {
                ControlsRequestDialog controlsRequestDialog = ControlsRequestDialog.this;
                ((UserTrackerImpl) controlsRequestDialog.userTracker).removeCallback(this);
                controlsRequestDialog.finish();
            }
        }
    };

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.controls.management.ControlsRequestDialog$callback$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.controls.management.ControlsRequestDialog$userTrackerCallback$1] */
    public ControlsRequestDialog(Executor executor, ControlsController controlsController, UserTracker userTracker, ControlsListingController controlsListingController, ControlsUtil controlsUtil) {
        this.mainExecutor = executor;
        this.controller = controlsController;
        this.userTracker = userTracker;
        this.controlsListingController = controlsListingController;
        this.controlsUtil = controlsUtil;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            ControlsController controlsController = this.controller;
            final ComponentName componentName = this.controlComponent;
            if (componentName == null) {
                componentName = null;
            }
            Control control = this.control;
            if (control == null) {
                control = null;
            }
            final CharSequence structure = control.getStructure();
            if (structure == null) {
                structure = "";
            }
            Control control2 = this.control;
            if (control2 == null) {
                control2 = null;
            }
            String controlId = control2.getControlId();
            Control control3 = this.control;
            if (control3 == null) {
                control3 = null;
            }
            CharSequence title = control3.getTitle();
            Control control4 = this.control;
            if (control4 == null) {
                control4 = null;
            }
            CharSequence subtitle = control4.getSubtitle();
            Control control5 = this.control;
            final ControlInfo controlInfo = new ControlInfo(controlId, title, subtitle, (control5 != null ? control5 : null).getDeviceType(), 0, 16, null);
            final ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) controlsController;
            if (controlsControllerImpl.confirmAvailability()) {
                controlsControllerImpl.executor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$addFavorite$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Favorites favorites = Favorites.INSTANCE;
                        ComponentName componentName2 = componentName;
                        CharSequence charSequence = structure;
                        ControlInfo controlInfo2 = controlInfo;
                        favorites.getClass();
                        if (Favorites.addFavorite(componentName2, charSequence, controlInfo2)) {
                            ((AuthorizedPanelsRepositoryImpl) controlsControllerImpl.authorizedPanelsRepository).addAuthorizedPanels(Collections.singleton(componentName.getPackageName()));
                            controlsControllerImpl.persistenceWrapper.storeFavorites(Favorites.getAllStructures());
                        }
                    }
                });
            }
        }
        finish();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((UserTrackerImpl) this.userTracker).addCallback(this.userTrackerCallback, this.mainExecutor);
        ControlsListingController controlsListingController = this.controlsListingController;
        ControlsRequestDialog$callback$1 controlsRequestDialog$callback$1 = this.callback;
        ControlsListingControllerImpl controlsListingControllerImpl = (ControlsListingControllerImpl) controlsListingController;
        controlsListingControllerImpl.getClass();
        controlsListingControllerImpl.addCallback((ControlsListingController.ControlsListingCallback) controlsRequestDialog$callback$1);
        int intExtra = getIntent().getIntExtra("android.intent.extra.USER_ID", -10000);
        int identifier = ((ControlsControllerImpl) this.controller).currentUser.getIdentifier();
        if (intExtra != identifier) {
            Log.w("ControlsRequestDialog", HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(identifier, intExtra, "Current user (", ") different from request user (", ")"));
            finish();
        }
        ComponentName componentName = (ComponentName) getIntent().getParcelableExtra("android.intent.extra.COMPONENT_NAME");
        if (componentName == null) {
            Log.e("ControlsRequestDialog", "Request did not contain componentName");
            finish();
            return;
        }
        this.controlComponent = componentName;
        Control control = (Control) getIntent().getParcelableExtra("android.service.controls.extra.CONTROL");
        if (control != null) {
            this.control = control;
        } else {
            Log.e("ControlsRequestDialog", "Request did not contain control");
            finish();
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Dialog dialog = this.dialog;
        if (dialog != null) {
            dialog.dismiss();
        }
        ((UserTrackerImpl) this.userTracker).removeCallback(this.userTrackerCallback);
        ((ControlsListingControllerImpl) this.controlsListingController).removeCallback(this.callback);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        ControlsListingController controlsListingController = this.controlsListingController;
        ComponentName componentName = this.controlComponent;
        if (componentName == null) {
            componentName = null;
        }
        if (((ControlsListingControllerImpl) controlsListingController).getAppLabel(componentName) == null) {
            ComponentName componentName2 = this.controlComponent;
            Log.e("ControlsRequestDialog", "The component specified (" + (componentName2 != null ? componentName2 : null).flattenToString() + " is not a valid ControlsProviderService");
            finish();
            return;
        }
        ControlsController controlsController = this.controller;
        ComponentName componentName3 = this.controlComponent;
        if (componentName3 == null) {
            componentName3 = null;
        }
        ((ControlsControllerImpl) controlsController).getClass();
        Favorites.INSTANCE.getClass();
        List structuresForComponent = Favorites.getStructuresForComponent(componentName3);
        if (!(structuresForComponent instanceof Collection) || !structuresForComponent.isEmpty()) {
            Iterator it = structuresForComponent.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                List list = ((StructureInfo) it.next()).controls;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator it2 = list.iterator();
                    while (it2.hasNext()) {
                        String str = ((ControlInfo) it2.next()).controlId;
                        Control control = this.control;
                        if (control == null) {
                            control = null;
                        }
                        if (Intrinsics.areEqual(str, control.getControlId())) {
                            Control control2 = this.control;
                            if (control2 == null) {
                                control2 = null;
                            }
                            Log.w("ControlsRequestDialog", "The control " + ((Object) control2.getTitle()) + " is already a favorite");
                            finish();
                        }
                    }
                }
            }
        }
        RenderInfo.Companion companion = RenderInfo.Companion;
        ComponentName componentName4 = this.controlComponent;
        if (componentName4 == null) {
            componentName4 = null;
        }
        Control control3 = this.control;
        if (control3 == null) {
            control3 = null;
        }
        int deviceType = control3.getDeviceType();
        companion.getClass();
        RenderInfo lookup = RenderInfo.Companion.lookup(this, componentName4, deviceType, 0);
        View inflate = LayoutInflater.from(this).inflate(R.layout.controls_add_dialog, (ViewGroup) null);
        ImageView imageView = (ImageView) inflate.requireViewById(R.id.icon);
        imageView.setImageDrawable(lookup.icon);
        imageView.setImageTintList(imageView.getContext().getResources().getColorStateList(lookup.foreground, imageView.getContext().getTheme()));
        TextView textView = (TextView) inflate.requireViewById(R.id.title);
        Control control4 = this.control;
        if (control4 == null) {
            control4 = null;
        }
        textView.setText(control4.getTitle());
        TextView textView2 = (TextView) inflate.requireViewById(R.id.subtitle);
        Control control5 = this.control;
        textView2.setText((control5 != null ? control5 : null).getSubtitle());
        View requireViewById = inflate.requireViewById(R.id.control);
        requireViewById.setBackground(requireViewById.getResources().getDrawable(R.drawable.control_add_dialog_bg));
        ControlsUtil controlsUtil = this.controlsUtil;
        Context context = requireViewById.getContext();
        LayerDrawable layerDrawable = (LayerDrawable) requireViewById.getBackground();
        controlsUtil.getClass();
        layerDrawable.mutate();
        ((GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.background)).setColor(context.getResources().getColor(R.color.control_favorite_default_background, context.getTheme()));
        AlertDialog create = new AlertDialog.Builder(this, R.style.Theme_SystemUI_Dialog_Alert).setTitle(getString(R.string.controls_add_devices_panel_title)).setPositiveButton(R.string.controls_dialog_add, this).setNegativeButton(R.string.controls_dialog_cancel, this).setOnCancelListener(this).setView(inflate).create();
        SystemUIDialog.registerDismissListener(create);
        create.setCanceledOnTouchOutside(true);
        this.dialog = create;
        create.show();
    }
}
