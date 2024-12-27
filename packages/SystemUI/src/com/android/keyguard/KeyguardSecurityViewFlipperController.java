package com.android.keyguard;

import android.view.LayoutInflater;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardInputViewController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardSecurityViewFlipperController extends ViewController {
    public final List mChildren;
    public final KeyguardInputViewController.Factory mKeyguardSecurityViewControllerFactory;
    public final LayoutInflater mLayoutInflater;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.KeyguardSecurityViewFlipperController$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SimPin.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SimPuk.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SimPerso.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Permanent.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Swipe.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.AdminLock.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.FMM.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.KNOXGUARD.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SKTCarrierLock.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SKTCarrierPassword.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SmartcardPIN.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.ForgotPassword.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface OnViewInflatedCallback {
        void onViewInflated(KeyguardInputViewController keyguardInputViewController);
    }

    public KeyguardSecurityViewFlipperController(KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, LayoutInflater layoutInflater, AsyncLayoutInflater asyncLayoutInflater, KeyguardInputViewController.Factory factory, EmergencyButtonController.Factory factory2, FeatureFlags featureFlags) {
        super(keyguardSecurityViewFlipper);
        this.mChildren = new ArrayList();
        this.mKeyguardSecurityViewControllerFactory = factory;
        this.mLayoutInflater = layoutInflater;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x004c, code lost:
    
        if (r0 != 4) goto L4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0053, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0055, code lost:
    
        r6 = com.android.systemui.R.layout.keyguard_sec_pin_view_tablet;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x005c, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x005e, code lost:
    
        r4 = com.android.systemui.R.layout.keyguard_sec_password_view_tablet;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x005f, code lost:
    
        r6 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0066, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0068, code lost:
    
        r2 = com.android.systemui.R.layout.keyguard_sec_pattern_view_tablet;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0069, code lost:
    
        r6 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00c6, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00cd, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00d4, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void asynchronouslyInflateView(com.android.keyguard.KeyguardSecurityModel.SecurityMode r10, com.android.keyguard.KeyguardSecurityCallback r11, com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback r12) {
        /*
            Method dump skipped, instructions count: 292
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardSecurityViewFlipperController.asynchronouslyInflateView(com.android.keyguard.KeyguardSecurityModel$SecurityMode, com.android.keyguard.KeyguardSecurityCallback, com.android.keyguard.KeyguardSecurityViewFlipperController$OnViewInflatedCallback):void");
    }

    public final void clearViews() {
        ((KeyguardSecurityViewFlipper) this.mView).removeAllViews();
        ((ArrayList) this.mChildren).clear();
    }

    public void getSecurityView(KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback, OnViewInflatedCallback onViewInflatedCallback) {
        Iterator it = ((ArrayList) this.mChildren).iterator();
        while (it.hasNext()) {
            KeyguardInputViewController keyguardInputViewController = (KeyguardInputViewController) it.next();
            if (keyguardInputViewController.mSecurityMode == securityMode) {
                onViewInflatedCallback.onViewInflated(keyguardInputViewController);
                return;
            }
        }
        asynchronouslyInflateView(securityMode, keyguardSecurityCallback, onViewInflatedCallback);
    }

    public final void reset$1() {
        Iterator it = ((ArrayList) this.mChildren).iterator();
        while (it.hasNext()) {
            KeyguardInputViewController keyguardInputViewController = (KeyguardInputViewController) it.next();
            if (keyguardInputViewController.getIndexIn((KeyguardSecurityViewFlipper) this.mView) == ((KeyguardSecurityViewFlipper) this.mView).getDisplayedChild()) {
                keyguardInputViewController.reset$1();
            }
        }
    }

    public final void show(KeyguardInputViewController keyguardInputViewController) {
        int indexIn = keyguardInputViewController.getIndexIn((KeyguardSecurityViewFlipper) this.mView);
        if (indexIn != -1) {
            ((KeyguardSecurityViewFlipper) this.mView).setDisplayedChild(indexIn);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
    }
}
