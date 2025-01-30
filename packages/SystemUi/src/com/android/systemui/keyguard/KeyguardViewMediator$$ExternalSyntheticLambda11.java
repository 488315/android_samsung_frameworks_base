package com.android.systemui.keyguard;

import com.android.keyguard.KeyguardDisplayManager;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import java.util.function.BiConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda11 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediatorHelperImpl f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda11(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediatorHelperImpl;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.enableLooperLogController(((Integer) obj).intValue(), ((Long) obj2).longValue());
                break;
            default:
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.f$0;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                boolean booleanValue2 = ((Boolean) obj2).booleanValue();
                keyguardViewMediatorHelperImpl.getClass();
                if (!booleanValue && booleanValue2) {
                    KeyguardUnlockInfo.reset();
                }
                KeyguardDumpLog.state$default(KeyguardDumpLog.INSTANCE, 4, booleanValue, false, false, 0, 0, 60);
                ((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).userActivity();
                String str = LsRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
                if (booleanValue2) {
                    boolean z = LsRune.KEYGUARD_SUB_DISPLAY_COVER;
                    KeyguardDisplayManager keyguardDisplayManager = keyguardViewMediatorHelperImpl.keyguardDisplayManager;
                    if ((z && !keyguardViewMediatorHelperImpl.foldControllerImpl.isFoldOpened()) || !booleanValue) {
                        keyguardDisplayManager.show();
                        break;
                    } else {
                        keyguardDisplayManager.getClass();
                        KeyguardVisibilityMonitor keyguardVisibilityMonitor = (KeyguardVisibilityMonitor) Dependency.get(KeyguardVisibilityMonitor.class);
                        if (!(keyguardVisibilityMonitor.curVisibility == 0)) {
                            keyguardDisplayManager.hide();
                            break;
                        } else {
                            keyguardVisibilityMonitor.addVisibilityChangedListener(keyguardDisplayManager.mVisibilityListener);
                            break;
                        }
                    }
                }
                break;
        }
    }
}
