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
import java.util.concurrent.Executor;

public final class RearDisplayDialogController implements CoreStartable, ConfigurationController.ConfigurationListener, CommandQueue.Callbacks {
    public final CommandQueue mCommandQueue;
    public DeviceStateManagerGlobal mDeviceStateManagerGlobal;
    public LinearLayout mDialogViewContainer;
    public final Executor mExecutor;
    public int[] mFoldedStates;
    public final LayoutInflater mLayoutInflater;
    public SystemUIDialog mRearDisplayEducationDialog;
    public final Resources mResources;
    public boolean mStartedFolded;
    public final SystemUIDialog.Factory mSystemUIDialogFactory;
    public boolean mServiceNotified = false;
    public final int mAnimationRepeatCount = -1;
    public final DeviceStateManagerCallback mDeviceStateManagerCallback = new DeviceStateManagerCallback(this, 0);

    public final class DeviceStateManagerCallback implements DeviceStateManager.DeviceStateCallback {
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

    public RearDisplayDialogController(CommandQueue commandQueue, Executor executor, Resources resources, LayoutInflater layoutInflater, SystemUIDialog.Factory factory) {
        this.mCommandQueue = commandQueue;
        this.mExecutor = executor;
        this.mResources = resources;
        this.mLayoutInflater = layoutInflater;
        this.mSystemUIDialogFactory = factory;
    }

    public final void closeOverlayAndNotifyService(boolean z) {
        this.mServiceNotified = true;
        this.mDeviceStateManagerGlobal.unregisterDeviceStateCallback(this.mDeviceStateManagerCallback);
        this.mDeviceStateManagerGlobal.onStateRequestOverlayDismissed(z);
        this.mDialogViewContainer = null;
    }

    public final View createDialogView(Context context) {
        LayoutInflater cloneInContext = this.mLayoutInflater.cloneInContext(context);
        View inflate = this.mStartedFolded ? cloneInContext.inflate(R.layout.activity_rear_display_education, (ViewGroup) null) : cloneInContext.inflate(R.layout.activity_rear_display_education_opened, (ViewGroup) null);
        ((LottieAnimationView) inflate.findViewById(R.id.rear_display_folded_animation)).setRepeatCount(this.mAnimationRepeatCount);
        return inflate;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        SystemUIDialog systemUIDialog = this.mRearDisplayEducationDialog;
        if (systemUIDialog == null || !systemUIDialog.isShowing() || this.mDialogViewContainer == null) {
            return;
        }
        View createDialogView = createDialogView(this.mRearDisplayEducationDialog.getContext());
        this.mDialogViewContainer.removeAllViews();
        this.mDialogViewContainer.addView(createDialogView);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showRearDisplayDialog(int i) {
        boolean z;
        this.mRearDisplayEducationDialog = this.mSystemUIDialogFactory.create();
        if (this.mFoldedStates == null) {
            this.mFoldedStates = this.mResources.getIntArray(android.R.array.preloaded_freeform_multi_window_drawables);
        }
        int i2 = 0;
        while (true) {
            int[] iArr = this.mFoldedStates;
            if (i2 >= iArr.length) {
                z = false;
                break;
            } else {
                if (iArr[i2] == i) {
                    z = true;
                    break;
                }
                i2++;
            }
        }
        this.mStartedFolded = z;
        DeviceStateManagerGlobal deviceStateManagerGlobal = DeviceStateManagerGlobal.getInstance();
        this.mDeviceStateManagerGlobal = deviceStateManagerGlobal;
        deviceStateManagerGlobal.registerDeviceStateCallback(this.mDeviceStateManagerCallback, this.mExecutor);
        this.mServiceNotified = false;
        Context context = this.mRearDisplayEducationDialog.getContext();
        View createDialogView = createDialogView(context);
        LinearLayout linearLayout = new LinearLayout(context);
        this.mDialogViewContainer = linearLayout;
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.mDialogViewContainer.setOrientation(1);
        this.mDialogViewContainer.addView(createDialogView);
        this.mRearDisplayEducationDialog.setView(this.mDialogViewContainer);
        if (!this.mStartedFolded) {
            final int i3 = 0;
            this.mRearDisplayEducationDialog.setButton(-1, R.string.rear_display_bottom_sheet_confirm, new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.reardisplay.RearDisplayDialogController$$ExternalSyntheticLambda0
                public final /* synthetic */ RearDisplayDialogController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    int i5 = i3;
                    RearDisplayDialogController rearDisplayDialogController = this.f$0;
                    switch (i5) {
                        case 0:
                            rearDisplayDialogController.closeOverlayAndNotifyService(false);
                            break;
                        default:
                            rearDisplayDialogController.closeOverlayAndNotifyService(true);
                            break;
                    }
                }
            }, true);
        }
        final int i4 = 1;
        this.mRearDisplayEducationDialog.setNegativeButton(R.string.rear_display_bottom_sheet_cancel, new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.reardisplay.RearDisplayDialogController$$ExternalSyntheticLambda0
            public final /* synthetic */ RearDisplayDialogController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i42) {
                int i5 = i4;
                RearDisplayDialogController rearDisplayDialogController = this.f$0;
                switch (i5) {
                    case 0:
                        rearDisplayDialogController.closeOverlayAndNotifyService(false);
                        break;
                    default:
                        rearDisplayDialogController.closeOverlayAndNotifyService(true);
                        break;
                }
            }
        });
        this.mRearDisplayEducationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.reardisplay.RearDisplayDialogController$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                RearDisplayDialogController rearDisplayDialogController = RearDisplayDialogController.this;
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
