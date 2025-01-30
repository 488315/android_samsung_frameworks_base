package com.android.systemui.accessibility;

import android.os.RemoteException;
import android.util.Log;
import android.view.accessibility.IWindowMagnificationConnectionCallback;
import com.android.systemui.accessibility.WindowMagnification;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class WindowMagnification$3$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WindowMagnification.C10003 f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ WindowMagnification$3$$ExternalSyntheticLambda0(WindowMagnification.C10003 c10003, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = c10003;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback;
        switch (this.$r8$classId) {
            case 0:
                WindowMagnification.C10003 c10003 = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                WindowMagnification windowMagnification = WindowMagnification.this;
                if (((WindowMagnificationController) windowMagnification.mMagnificationControllerSupplier.get(i)).isActivated() ^ (i2 == 2)) {
                    MagnificationSettingsController magnificationSettingsController = (MagnificationSettingsController) windowMagnification.mMagnificationSettingsSupplier.get(i);
                    if (magnificationSettingsController != null) {
                        magnificationSettingsController.mContext.unregisterComponentCallbacks(magnificationSettingsController);
                        magnificationSettingsController.mWindowMagnificationSettings.hideSettingPanel(true);
                    }
                    WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = windowMagnification.mWindowMagnificationConnectionImpl;
                    if (windowMagnificationConnectionImpl != null && (iWindowMagnificationConnectionCallback = windowMagnificationConnectionImpl.mConnectionCallback) != null) {
                        try {
                            iWindowMagnificationConnectionCallback.onChangeMagnificationMode(i, i2);
                            break;
                        } catch (RemoteException e) {
                            Log.e("WindowMagnificationConnectionImpl", "Failed to inform changing magnification mode", e);
                        }
                    }
                }
                break;
            default:
                WindowMagnification.C10003 c100032 = this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) WindowMagnification.this.mMagnificationControllerSupplier.get(i3);
                if (windowMagnificationController != null) {
                    windowMagnificationController.changeMagnificationSize(i4);
                    break;
                }
                break;
        }
    }
}
