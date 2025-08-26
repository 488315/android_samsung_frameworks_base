package androidx.compose.ui.input.pointer;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class PointerInteropFilter implements PointerInputModifier {
    public boolean disallowIntercept;
    public Function1 onTouchEvent;
    public final PointerInteropFilter$pointerInputFilter$1 pointerInputFilter = new PointerInteropFilter$pointerInputFilter$1(this);
    public RequestDisallowInterceptTouchEvent requestDisallowInterceptTouchEvent;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class DispatchToViewState {
        public static final /* synthetic */ DispatchToViewState[] $VALUES;
        public static final DispatchToViewState Dispatching;
        public static final DispatchToViewState NotDispatching;
        public static final DispatchToViewState Unknown;

        static {
            DispatchToViewState dispatchToViewState = new DispatchToViewState(C2paManifestList.UNKNOWN_VALUE, 0);
            Unknown = dispatchToViewState;
            DispatchToViewState dispatchToViewState2 = new DispatchToViewState("Dispatching", 1);
            Dispatching = dispatchToViewState2;
            DispatchToViewState dispatchToViewState3 = new DispatchToViewState("NotDispatching", 2);
            NotDispatching = dispatchToViewState3;
            DispatchToViewState[] dispatchToViewStateArr = {dispatchToViewState, dispatchToViewState2, dispatchToViewState3};
            $VALUES = dispatchToViewStateArr;
            EnumEntriesKt.enumEntries(dispatchToViewStateArr);
        }

        private DispatchToViewState(String str, int i) {
        }

        public static DispatchToViewState valueOf(String str) {
            return (DispatchToViewState) Enum.valueOf(DispatchToViewState.class, str);
        }

        public static DispatchToViewState[] values() {
            return (DispatchToViewState[]) $VALUES.clone();
        }
    }
}
