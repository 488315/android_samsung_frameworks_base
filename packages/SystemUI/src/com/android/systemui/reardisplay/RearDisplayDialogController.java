package com.android.systemui.reardisplay;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.devicestate.DeviceStateManagerGlobal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class RearDisplayDialogController implements CoreStartable, ConfigurationController.ConfigurationListener, CommandQueue.Callbacks {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CommandQueue mCommandQueue;
    public final DeviceStateManager mDeviceStateManager;
    public DeviceStateManagerGlobal mDeviceStateManagerGlobal;
    public LinearLayout mDialogViewContainer;
    public final Executor mExecutor;
    public List mFoldedStates;
    public final LayoutInflater mLayoutInflater;
    public SystemUIDialog mRearDisplayEducationDialog;
    public boolean mStartedFolded;
    public final SystemUIDialog.Factory mSystemUIDialogFactory;
    public boolean mServiceNotified = false;
    public final DeviceStateManagerCallback mDeviceStateManagerCallback = new DeviceStateManagerCallback(this, 0);

    public class DeviceStateManagerCallback implements DeviceStateManager.DeviceStateCallback {
        public /* synthetic */ DeviceStateManagerCallback(RearDisplayDialogController rearDisplayDialogController, int i) {
            this();
        }

        public final void onDeviceStateChanged(DeviceState deviceState) {
            if (RearDisplayDialogController.this.mStartedFolded && !deviceState.hasProperty(1)) {
                RearDisplayDialogController.this.mRearDisplayEducationDialog.dismiss();
                RearDisplayDialogController.this.closeOverlayAndNotifyService(false);
            } else {
                if (RearDisplayDialogController.this.mStartedFolded || !deviceState.hasProperty(1)) {
                    return;
                }
                RearDisplayDialogController.this.mRearDisplayEducationDialog.dismiss();
                RearDisplayDialogController.this.closeOverlayAndNotifyService(true);
            }
        }

        private DeviceStateManagerCallback() {
        }
    }

    public RearDisplayDialogController(CommandQueue commandQueue, Executor executor, Resources resources, LayoutInflater layoutInflater, SystemUIDialog.Factory factory, DeviceStateManager deviceStateManager) {
        this.mCommandQueue = commandQueue;
        this.mExecutor = executor;
        this.mLayoutInflater = layoutInflater;
        this.mSystemUIDialogFactory = factory;
        this.mDeviceStateManager = deviceStateManager;
    }

    public final void closeOverlayAndNotifyService(boolean z) {
        this.mServiceNotified = true;
        this.mDeviceStateManagerGlobal.unregisterDeviceStateCallback(this.mDeviceStateManagerCallback);
        this.mDeviceStateManagerGlobal.onStateRequestOverlayDismissed(z);
        this.mDialogViewContainer = null;
    }

    public final View createDialogView(Context context) {
        LayoutInflater layoutInflaterCloneInContext = this.mLayoutInflater.cloneInContext(context);
        View viewInflate = this.mStartedFolded ? layoutInflaterCloneInContext.inflate(R.layout.activity_rear_display_education, (ViewGroup) null) : layoutInflaterCloneInContext.inflate(R.layout.activity_rear_display_education_opened, (ViewGroup) null);
        ((LottieAnimationView) viewInflate.findViewById(R.id.rear_display_folded_animation)).setRepeatCount(-1);
        return viewInflate;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        SystemUIDialog systemUIDialog = this.mRearDisplayEducationDialog;
        if (systemUIDialog == null || !systemUIDialog.isShowing() || this.mDialogViewContainer == null) {
            return;
        }
        View viewCreateDialogView = createDialogView(this.mRearDisplayEducationDialog.getContext());
        this.mDialogViewContainer.removeAllViews();
        this.mDialogViewContainer.addView(viewCreateDialogView);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showRearDisplayDialog(int i) {
        boolean z;
        this.mRearDisplayEducationDialog = this.mSystemUIDialogFactory.create();
        if (this.mFoldedStates == null) {
            this.mFoldedStates = new ArrayList();
            List supportedDeviceStates = this.mDeviceStateManager.getSupportedDeviceStates();
            for (int i2 = 0; i2 < supportedDeviceStates.size(); i2++) {
                DeviceState deviceState = (DeviceState) supportedDeviceStates.get(i2);
                if (deviceState.hasProperty(11)) {
                    ((ArrayList) this.mFoldedStates).add(Integer.valueOf(deviceState.getIdentifier()));
                }
            }
        }
        int i3 = 0;
        while (true) {
            if (i3 >= ((ArrayList) this.mFoldedStates).size()) {
                z = false;
                break;
            } else {
                if (((Integer) ((ArrayList) this.mFoldedStates).get(i3)).intValue() == i) {
                    z = true;
                    break;
                }
                i3++;
            }
        }
        this.mStartedFolded = z;
        DeviceStateManagerGlobal deviceStateManagerGlobal = DeviceStateManagerGlobal.getInstance();
        this.mDeviceStateManagerGlobal = deviceStateManagerGlobal;
        deviceStateManagerGlobal.registerDeviceStateCallback(this.mDeviceStateManagerCallback, this.mExecutor);
        this.mServiceNotified = false;
        Context context = this.mRearDisplayEducationDialog.getContext();
        View viewCreateDialogView = createDialogView(context);
        LinearLayout linearLayout = new LinearLayout(context);
        this.mDialogViewContainer = linearLayout;
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.mDialogViewContainer.setOrientation(1);
        this.mDialogViewContainer.addView(viewCreateDialogView);
        this.mRearDisplayEducationDialog.setView(this.mDialogViewContainer);
        if (!this.mStartedFolded) {
            final int i4 = 0;
            this.mRearDisplayEducationDialog.setButton(-1, R.string.rear_display_bottom_sheet_confirm, new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.reardisplay.RearDisplayDialogController$$ExternalSyntheticLambda0
                public final /* synthetic */ RearDisplayDialogController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i5) {
                    int i6 = i4;
                    RearDisplayDialogController rearDisplayDialogController = this.f$0;
                    switch (i6) {
                        case 0:
                            int i7 = RearDisplayDialogController.$r8$clinit;
                            rearDisplayDialogController.closeOverlayAndNotifyService(false);
                            break;
                        default:
                            int i8 = RearDisplayDialogController.$r8$clinit;
                            rearDisplayDialogController.closeOverlayAndNotifyService(true);
                            break;
                    }
                }
            }, true);
        }
        final int i5 = 1;
        this.mRearDisplayEducationDialog.setNegativeButton(R.string.rear_display_bottom_sheet_cancel, new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.reardisplay.RearDisplayDialogController$$ExternalSyntheticLambda0
            public final /* synthetic */ RearDisplayDialogController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i52) {
                int i6 = i5;
                RearDisplayDialogController rearDisplayDialogController = this.f$0;
                switch (i6) {
                    case 0:
                        int i7 = RearDisplayDialogController.$r8$clinit;
                        rearDisplayDialogController.closeOverlayAndNotifyService(false);
                        break;
                    default:
                        int i8 = RearDisplayDialogController.$r8$clinit;
                        rearDisplayDialogController.closeOverlayAndNotifyService(true);
                        break;
                }
            }
        });
        this.mRearDisplayEducationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.reardisplay.RearDisplayDialogController$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                RearDisplayDialogController rearDisplayDialogController = this.f$0;
                if (rearDisplayDialogController.mServiceNotified) {
                    return;
                }
                rearDisplayDialogController.closeOverlayAndNotifyService(true);
            }
        });
        this.mRearDisplayEducationDialog.show();
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
    }
}
