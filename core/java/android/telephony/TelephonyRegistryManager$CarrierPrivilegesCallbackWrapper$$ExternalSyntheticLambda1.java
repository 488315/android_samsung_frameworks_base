package android.telephony;

import android.telephony.TelephonyManager;
import java.lang.ref.WeakReference;
import java.util.function.Supplier;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes4.dex */
public final /* synthetic */ class TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ WeakReference f$0;

    public /* synthetic */ TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda1(WeakReference weakReference) {
        this.f$0 = weakReference;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return (TelephonyManager.CarrierPrivilegesCallback) this.f$0.get();
    }
}
