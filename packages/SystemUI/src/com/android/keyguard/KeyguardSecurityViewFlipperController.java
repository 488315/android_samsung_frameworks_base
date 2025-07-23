package com.android.keyguard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardInputViewController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyguardSecurityViewFlipperController extends ViewController {
    public final AsyncLayoutInflater mAsyncLayoutInflater;
    public final List mChildren;
    public final FeatureFlags mFeatureFlags;
    public final KeyguardInputViewController.Factory mKeyguardSecurityViewControllerFactory;
    public final List mOnViewInflatedListeners;
    public final Set mSecurityModeInProgress;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnViewInflatedCallback {
        void onViewInflated(KeyguardInputViewController keyguardInputViewController);
    }

    public static /* synthetic */ void $r8$lambda$dwzoxrh2ePlPnwmsrWRxF3KbrBc(KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController, int i, KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback, View view) {
        ArrayList arrayList;
        ((KeyguardSecurityViewFlipper) keyguardSecurityViewFlipperController.mView).addView(view);
        Log.d("KeyguardSecurityView", "asynchronouslyInflateView layoutId = " + i + " securityMode = " + securityMode);
        ((HashSet) keyguardSecurityViewFlipperController.mSecurityModeInProgress).remove(securityMode);
        KeyguardInputViewController create = keyguardSecurityViewFlipperController.mKeyguardSecurityViewControllerFactory.create((KeyguardInputView) view, securityMode, keyguardSecurityCallback);
        create.init();
        ((ArrayList) keyguardSecurityViewFlipperController.mChildren).add(create);
        synchronized (keyguardSecurityViewFlipperController.mOnViewInflatedListeners) {
            arrayList = new ArrayList(keyguardSecurityViewFlipperController.mOnViewInflatedListeners);
            ((ArrayList) keyguardSecurityViewFlipperController.mOnViewInflatedListeners).clear();
        }
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((OnViewInflatedCallback) obj).onViewInflated(create);
        }
        FeatureFlags featureFlags = keyguardSecurityViewFlipperController.mFeatureFlags;
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
    }

    public KeyguardSecurityViewFlipperController(KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, LayoutInflater layoutInflater, AsyncLayoutInflater asyncLayoutInflater, KeyguardInputViewController.Factory factory, EmergencyButtonController.Factory factory2, FeatureFlags featureFlags) {
        super(keyguardSecurityViewFlipper);
        this.mChildren = new ArrayList();
        this.mOnViewInflatedListeners = new ArrayList();
        this.mSecurityModeInProgress = new HashSet();
        this.mKeyguardSecurityViewControllerFactory = factory;
        this.mAsyncLayoutInflater = asyncLayoutInflater;
        this.mFeatureFlags = featureFlags;
    }

    public final void clearViews() {
        ((KeyguardSecurityViewFlipper) this.mView).removeAllViews();
        ((ArrayList) this.mChildren).clear();
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0082, code lost:
    
        if (r12 != 4) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x008a, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x008c, code lost:
    
        r2 = com.android.systemui.R.layout.keyguard_sec_pin_view_tablet;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x008f, code lost:
    
        r2 = com.android.systemui.R.layout.keyguard_sec_pin_view;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0096, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0098, code lost:
    
        r2 = com.android.systemui.R.layout.keyguard_sec_password_view_tablet;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x009b, code lost:
    
        r2 = com.android.systemui.R.layout.keyguard_sec_password_view;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00a2, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00a4, code lost:
    
        r2 = com.android.systemui.R.layout.keyguard_sec_pattern_view_tablet;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00a7, code lost:
    
        r2 = com.android.systemui.R.layout.keyguard_sec_pattern_view;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0103, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x010a, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0112, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void getSecurityView(final com.android.keyguard.KeyguardSecurityModel.SecurityMode r10, final com.android.keyguard.KeyguardSecurityCallback r11, com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback r12) {
        /*
            Method dump skipped, instructions count: 340
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardSecurityViewFlipperController.getSecurityView(com.android.keyguard.KeyguardSecurityModel$SecurityMode, com.android.keyguard.KeyguardSecurityCallback, com.android.keyguard.KeyguardSecurityViewFlipperController$OnViewInflatedCallback):void");
    }

    public final void reset$1() {
        ArrayList arrayList = (ArrayList) this.mChildren;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            KeyguardInputViewController keyguardInputViewController = (KeyguardInputViewController) obj;
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
