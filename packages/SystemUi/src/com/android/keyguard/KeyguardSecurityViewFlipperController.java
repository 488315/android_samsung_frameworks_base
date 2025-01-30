package com.android.keyguard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardInputViewController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardSecurityViewFlipperController extends ViewController {
    public final List mChildren;
    public final KeyguardInputViewController.Factory mKeyguardSecurityViewControllerFactory;
    public final LayoutInflater mLayoutInflater;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardSecurityViewFlipperController$1 */
    public abstract /* synthetic */ class AbstractC07681 {

        /* renamed from: $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode */
        public static final /* synthetic */ int[] f210xdc0e830a;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            f210xdc0e830a = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f210xdc0e830a[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f210xdc0e830a[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f210xdc0e830a[KeyguardSecurityModel.SecurityMode.SimPin.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f210xdc0e830a[KeyguardSecurityModel.SecurityMode.SimPuk.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f210xdc0e830a[KeyguardSecurityModel.SecurityMode.SimPerso.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f210xdc0e830a[KeyguardSecurityModel.SecurityMode.Permanent.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f210xdc0e830a[KeyguardSecurityModel.SecurityMode.Swipe.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f210xdc0e830a[KeyguardSecurityModel.SecurityMode.AdminLock.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f210xdc0e830a[KeyguardSecurityModel.SecurityMode.FMM.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f210xdc0e830a[KeyguardSecurityModel.SecurityMode.RMM.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f210xdc0e830a[KeyguardSecurityModel.SecurityMode.KNOXGUARD.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f210xdc0e830a[KeyguardSecurityModel.SecurityMode.SKTCarrierLock.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f210xdc0e830a[KeyguardSecurityModel.SecurityMode.SKTCarrierPassword.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f210xdc0e830a[KeyguardSecurityModel.SecurityMode.SmartcardPIN.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f210xdc0e830a[KeyguardSecurityModel.SecurityMode.ForgotPassword.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnViewInflatedCallback {
        void onViewInflated(KeyguardInputViewController keyguardInputViewController);
    }

    public KeyguardSecurityViewFlipperController(KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, LayoutInflater layoutInflater, AsyncLayoutInflater asyncLayoutInflater, KeyguardInputViewController.Factory factory, EmergencyButtonController.Factory factory2, FeatureFlags featureFlags) {
        super(keyguardSecurityViewFlipper);
        this.mChildren = new ArrayList();
        this.mKeyguardSecurityViewControllerFactory = factory;
        this.mLayoutInflater = layoutInflater;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0037, code lost:
    
        if (r0 != 4) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x003f, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x00dd, code lost:
    
        r0 = com.android.systemui.R.layout.keyguard_sec_pin_view;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00d9, code lost:
    
        r0 = com.android.systemui.R.layout.keyguard_sec_pin_view_tablet;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0047, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00cf, code lost:
    
        r0 = com.android.systemui.R.layout.keyguard_sec_password_view;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00cb, code lost:
    
        r0 = com.android.systemui.R.layout.keyguard_sec_password_view_tablet;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x004f, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00eb, code lost:
    
        r0 = com.android.systemui.R.layout.keyguard_sec_pattern_view;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00e7, code lost:
    
        r0 = com.android.systemui.R.layout.keyguard_sec_pattern_view_tablet;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00c9, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00d7, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00e5, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L64;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void asynchronouslyInflateView(KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback, OnViewInflatedCallback onViewInflatedCallback) {
        int i;
        Log.d("KeyguardSecurityView", "getLayoutIdFor securityMode = " + securityMode);
        switch (AbstractC07681.f210xdc0e830a[securityMode.ordinal()]) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                if (!DeviceType.isTablet()) {
                    i = R.layout.keyguard_sec_sim_pin_view;
                    break;
                } else {
                    i = R.layout.keyguard_sec_sim_pin_view_tablet;
                    break;
                }
            case 5:
                if (!LsRune.SECURITY_NOT_REQUIRE_SIMPUK_CODE) {
                    if (!DeviceType.isTablet()) {
                        i = R.layout.keyguard_sec_sim_puk_view;
                        break;
                    } else {
                        i = R.layout.keyguard_sec_sim_puk_view_tablet;
                        break;
                    }
                } else {
                    i = R.layout.keyguard_sec_sim_puk_view_tmo;
                    break;
                }
            case 6:
                if (LsRune.SECURITY_SIM_PERSO_LOCK) {
                    i = R.layout.keyguard_sec_sim_perso_view;
                    break;
                }
            case 7:
                i = R.layout.keyguard_permanent_view;
                break;
            case 8:
                if (LsRune.SECURITY_SWIPE_BOUNCER) {
                    i = R.layout.keyguard_swipe_view;
                    break;
                }
            case 9:
                i = R.layout.keyguard_admin_view;
                break;
            case 10:
                if (!DeviceType.isTablet()) {
                    i = R.layout.keyguard_fmm_view;
                    break;
                } else {
                    i = R.layout.keyguard_fmm_view_tablet;
                    break;
                }
            case 11:
                if (!DeviceType.isTablet()) {
                    i = R.layout.keyguard_rmm_view;
                    break;
                } else {
                    i = R.layout.keyguard_rmm_view_tablet;
                    break;
                }
            case 12:
                i = R.layout.keyguard_knox_guard_view;
                break;
            case 13:
                i = R.layout.keyguard_carrier_view;
                break;
            case 14:
                i = R.layout.keyguard_carrier_password_view;
                break;
            case 15:
                i = R.layout.keyguard_ucm_view;
                break;
            case 16:
                int prevCredentialType = ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).getPrevCredentialType();
                if (prevCredentialType == 1) {
                    break;
                } else {
                    if (prevCredentialType != 2) {
                        if (prevCredentialType == 3) {
                            break;
                        } else {
                            break;
                        }
                    }
                    break;
                }
            default:
                i = 0;
                break;
        }
        if (i != 0) {
            KeyguardInputView keyguardInputView = (KeyguardInputView) this.mLayoutInflater.inflate(i, (ViewGroup) this.mView, false);
            ((KeyguardSecurityViewFlipper) this.mView).addView(keyguardInputView);
            KeyguardInputViewController create = this.mKeyguardSecurityViewControllerFactory.create(keyguardInputView, securityMode, keyguardSecurityCallback);
            create.init();
            ((ArrayList) this.mChildren).add(create);
            if (onViewInflatedCallback != null) {
                onViewInflatedCallback.onViewInflated(create);
            }
        }
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

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
    }
}
