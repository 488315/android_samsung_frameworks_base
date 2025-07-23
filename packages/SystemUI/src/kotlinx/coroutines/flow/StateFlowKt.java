package kotlinx.coroutines.flow;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
