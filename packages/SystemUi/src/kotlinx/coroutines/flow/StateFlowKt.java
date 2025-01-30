package kotlinx.coroutines.flow;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class StateFlowKt {
    public static final Symbol NONE = new Symbol(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE);
    public static final Symbol PENDING = new Symbol("PENDING");

    public static final StateFlowImpl MutableStateFlow(Object obj) {
        if (obj == null) {
            obj = NullSurrogateKt.NULL;
        }
        return new StateFlowImpl(obj);
    }
}
