package com.android.systemui.reardisplay;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.devicestate.DeviceStateManagerGlobal;
import android.view.View;
import android.widget.LinearLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RearDisplayDialogController implements CoreStartable, CommandQueue.Callbacks {
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public DeviceStateManagerGlobal mDeviceStateManagerGlobal;
    public LinearLayout mDialogViewContainer;
    public final Executor mExecutor;
    public int[] mFoldedStates;
    SystemUIDialog mRearDisplayEducationDialog;
    public boolean mStartedFolded;
    public boolean mServiceNotified = false;
    public final int mAnimationRepeatCount = -1;
    public final DeviceStateManagerCallback mDeviceStateManagerCallback = new DeviceStateManagerCallback(this, 0);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DeviceStateManagerCallback implements DeviceStateManager.DeviceStateCallback {
        public /* synthetic */ DeviceStateManagerCallback(RearDisplayDialogController rearDisplayDialogController, int i) {
            this();
        }

        public final void onBaseStateChanged(int i) {
            RearDisplayDialogController rearDisplayDialogController = RearDisplayDialogController.this;
            if (rearDisplayDialogController.mStartedFolded && !rearDisplayDialogController.isFoldedState(i)) {
                RearDisplayDialogController.this.mRearDisplayEducationDialog.dismiss();
                RearDisplayDialogController.this.closeOverlayAndNotifyService(false);
                return;
            }
            RearDisplayDialogController rearDisplayDialogController2 = RearDisplayDialogController.this;
            if (rearDisplayDialogController2.mStartedFolded || !rearDisplayDialogController2.isFoldedState(i)) {
                return;
            }
            RearDisplayDialogController.this.mRearDisplayEducationDialog.dismiss();
            RearDisplayDialogController.this.closeOverlayAndNotifyService(true);
        }

        private DeviceStateManagerCallback() {
        }

        public final void onStateChanged(int i) {
        }
    }

    public RearDisplayDialogController(Context context, CommandQueue commandQueue, Executor executor) {
        this.mContext = context;
        this.mCommandQueue = commandQueue;
        this.mExecutor = executor;
    }

    public final void closeOverlayAndNotifyService(boolean z) {
        this.mServiceNotified = true;
        this.mDeviceStateManagerGlobal.unregisterDeviceStateCallback(this.mDeviceStateManagerCallback);
        this.mDeviceStateManagerGlobal.onStateRequestOverlayDismissed(z);
        this.mDialogViewContainer = null;
    }

    public final View createDialogView(Context context) {
        View inflate = this.mStartedFolded ? View.inflate(context, R.layout.activity_rear_display_education, null) : View.inflate(context, R.layout.activity_rear_display_education_opened, null);
        ((LottieAnimationView) inflate.findViewById(R.id.rear_display_folded_animation)).setRepeatCount(this.mAnimationRepeatCount);
        return inflate;
    }

    public final boolean isFoldedState(int i) {
        int i2 = 0;
        while (true) {
            int[] iArr = this.mFoldedStates;
            if (i2 >= iArr.length) {
                return false;
            }
            if (iArr[i2] == i) {
                return true;
            }
            i2++;
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void onConfigurationChanged(Configuration configuration) {
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
        Context context = this.mContext;
        this.mRearDisplayEducationDialog = new SystemUIDialog(context);
        if (this.mFoldedStates == null) {
            this.mFoldedStates = context.getResources().getIntArray(android.R.array.networks_not_clear_data);
        }
        this.mStartedFolded = isFoldedState(i);
        DeviceStateManagerGlobal deviceStateManagerGlobal = DeviceStateManagerGlobal.getInstance();
        this.mDeviceStateManagerGlobal = deviceStateManagerGlobal;
        deviceStateManagerGlobal.registerDeviceStateCallback(this.mDeviceStateManagerCallback, this.mExecutor);
        final int i2 = 0;
        this.mServiceNotified = false;
        Context context2 = this.mRearDisplayEducationDialog.getContext();
        View createDialogView = createDialogView(context2);
        LinearLayout linearLayout = new LinearLayout(context2);
        this.mDialogViewContainer = linearLayout;
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        final int i3 = 1;
        this.mDialogViewContainer.setOrientation(1);
        this.mDialogViewContainer.addView(createDialogView);
        this.mRearDisplayEducationDialog.setView(this.mDialogViewContainer);
        if (!this.mStartedFolded) {
            this.mRearDisplayEducationDialog.setButton(-1, R.string.rear_display_bottom_sheet_confirm, new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.reardisplay.RearDisplayDialogController$$ExternalSyntheticLambda0
                public final /* synthetic */ RearDisplayDialogController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    switch (i2) {
                        case 0:
                            this.f$0.closeOverlayAndNotifyService(false);
                            break;
                        default:
                            this.f$0.closeOverlayAndNotifyService(true);
                            break;
                    }
                }
            }, true);
        }
        this.mRearDisplayEducationDialog.setButton(-2, R.string.rear_display_bottom_sheet_cancel, new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.reardisplay.RearDisplayDialogController$$ExternalSyntheticLambda0
            public final /* synthetic */ RearDisplayDialogController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i4) {
                switch (i3) {
                    case 0:
                        this.f$0.closeOverlayAndNotifyService(false);
                        break;
                    default:
                        this.f$0.closeOverlayAndNotifyService(true);
                        break;
                }
            }
        }, true);
        this.mRearDisplayEducationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.reardisplay.RearDisplayDialogController$$ExternalSyntheticLambda1
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
