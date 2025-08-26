package com.android.keyguard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardInputViewController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class KeyguardSecurityViewFlipperController extends ViewController {
    public final AsyncLayoutInflater mAsyncLayoutInflater;
    public final List mChildren;
    public final FeatureFlags mFeatureFlags;
    public final KeyguardInputViewController.Factory mKeyguardSecurityViewControllerFactory;
    public final HashMap mOnViewInflatedListenerMap;
    public final Set mSecurityModeInProgress;

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

    public interface OnViewInflatedCallback {
        void onViewInflated(KeyguardInputViewController keyguardInputViewController);
    }

    public static /* synthetic */ void $r8$lambda$dwzoxrh2ePlPnwmsrWRxF3KbrBc(KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController, int i, KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback, View view) {
        ArrayList arrayList;
        ((KeyguardSecurityViewFlipper) keyguardSecurityViewFlipperController.mView).addView(view);
        Log.d("KeyguardSecurityView", "asynchronouslyInflateView layoutId = " + i + " securityMode = " + securityMode);
        ((HashSet) keyguardSecurityViewFlipperController.mSecurityModeInProgress).remove(securityMode);
        KeyguardInputViewController keyguardInputViewControllerCreate = keyguardSecurityViewFlipperController.mKeyguardSecurityViewControllerFactory.create((KeyguardInputView) view, securityMode, keyguardSecurityCallback);
        keyguardInputViewControllerCreate.init();
        ((ArrayList) keyguardSecurityViewFlipperController.mChildren).add(keyguardInputViewControllerCreate);
        synchronized (keyguardSecurityViewFlipperController.mOnViewInflatedListenerMap) {
            try {
                List list = (List) keyguardSecurityViewFlipperController.mOnViewInflatedListenerMap.get(securityMode);
                arrayList = list != null ? new ArrayList(list) : new ArrayList();
                keyguardSecurityViewFlipperController.mOnViewInflatedListenerMap.remove(securityMode);
            } catch (Throwable th) {
                throw th;
            }
        }
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((OnViewInflatedCallback) obj).onViewInflated(keyguardInputViewControllerCreate);
        }
        FeatureFlags featureFlags = keyguardSecurityViewFlipperController.mFeatureFlags;
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
    }

    public KeyguardSecurityViewFlipperController(KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, LayoutInflater layoutInflater, AsyncLayoutInflater asyncLayoutInflater, KeyguardInputViewController.Factory factory, EmergencyButtonController.Factory factory2, FeatureFlags featureFlags) {
        super(keyguardSecurityViewFlipper);
        this.mChildren = new ArrayList();
        new ArrayList();
        this.mOnViewInflatedListenerMap = new HashMap();
        this.mSecurityModeInProgress = new HashSet();
        this.mKeyguardSecurityViewControllerFactory = factory;
        this.mAsyncLayoutInflater = asyncLayoutInflater;
        this.mFeatureFlags = featureFlags;
    }

    public final void clearViews() {
        ((KeyguardSecurityViewFlipper) this.mView).removeAllViews();
        ((ArrayList) this.mChildren).clear();
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0096, code lost:
    
        if (r11 != 4) goto L75;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00bb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void getSecurityView(final KeyguardSecurityModel.SecurityMode securityMode, final KeyguardSecurityCallback keyguardSecurityCallback, OnViewInflatedCallback onViewInflatedCallback) throws InterruptedException {
        ArrayList arrayList = (ArrayList) this.mChildren;
        int size = arrayList.size();
        final int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            KeyguardInputViewController keyguardInputViewController = (KeyguardInputViewController) obj;
            if (keyguardInputViewController.mSecurityMode == securityMode) {
                onViewInflatedCallback.onViewInflated(keyguardInputViewController);
                return;
            }
        }
        synchronized (this.mOnViewInflatedListenerMap) {
            try {
                this.mOnViewInflatedListenerMap.putIfAbsent(securityMode, new ArrayList());
                List list = (List) this.mOnViewInflatedListenerMap.get(securityMode);
                if (list != null) {
                    list.add(onViewInflatedCallback);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (((HashSet) this.mSecurityModeInProgress).contains(securityMode)) {
            return;
        }
        ((HashSet) this.mSecurityModeInProgress).add(securityMode);
        Log.d("KeyguardSecurityView", "getLayoutIdFor securityMode = " + securityMode);
        switch (AnonymousClass1.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[securityMode.ordinal()]) {
            case 1:
                if (!DeviceType.isTablet()) {
                    i = R.layout.keyguard_sec_pattern_view;
                    break;
                } else {
                    i = R.layout.keyguard_sec_pattern_view_tablet;
                    break;
                }
            case 2:
                if (!DeviceType.isTablet()) {
                    i = R.layout.keyguard_sec_pin_view;
                    break;
                } else {
                    i = R.layout.keyguard_sec_pin_view_tablet;
                    break;
                }
            case 3:
                if (!DeviceType.isTablet()) {
                    i = R.layout.keyguard_sec_password_view;
                    break;
                } else {
                    i = R.layout.keyguard_sec_password_view_tablet;
                    break;
                }
            case 4:
                if (!DeviceType.isTablet()) {
                    i = R.layout.keyguard_sec_sim_pin_view;
                    break;
                } else {
                    i = R.layout.keyguard_sec_sim_pin_view_tablet;
                    break;
                }
            case 5:
                if (!DeviceType.isTablet()) {
                    i = R.layout.keyguard_sec_sim_puk_view;
                    break;
                } else {
                    i = R.layout.keyguard_sec_sim_puk_view_tablet;
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
                i = R.layout.keyguard_knox_guard_view;
                break;
            case 12:
                i = R.layout.keyguard_carrier_view;
                break;
            case 13:
                i = R.layout.keyguard_carrier_password_view;
                break;
            case 14:
                i = R.layout.keyguard_ucm_view;
                break;
            case 15:
                int prevCredentialType = ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).getPrevCredentialType();
                if (prevCredentialType != 1) {
                    if (prevCredentialType != 2) {
                        if (prevCredentialType != 3) {
                            break;
                        } else if (DeviceType.isTablet()) {
                        }
                    }
                    if (DeviceType.isTablet()) {
                    }
                } else if (DeviceType.isTablet()) {
                }
                break;
        }
        if (i != 0) {
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "inflating on bg thread id = ", " .", "KeyguardSecurityView");
            AsyncLayoutInflater asyncLayoutInflater = this.mAsyncLayoutInflater;
            asyncLayoutInflater.inflateInternal(i, (ViewGroup) this.mView, new AsyncLayoutInflater.OnInflateFinishedListener() { // from class: com.android.keyguard.KeyguardSecurityViewFlipperController$$ExternalSyntheticLambda0
                @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
                public final void onInflateFinished(int i3, View view, ViewGroup viewGroup) {
                    KeyguardSecurityViewFlipperController.$r8$lambda$dwzoxrh2ePlPnwmsrWRxF3KbrBc(this.f$0, i, securityMode, keyguardSecurityCallback, view);
                }
            }, asyncLayoutInflater.mInflater);
        } else {
            synchronized (this.mOnViewInflatedListenerMap) {
                this.mOnViewInflatedListenerMap.remove(securityMode);
            }
        }
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
