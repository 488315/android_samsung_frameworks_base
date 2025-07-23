package com.android.systemui.keyboard.shortcut.data.repository;

import android.hardware.input.InputManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import kotlin.collections.EmptyList;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CustomInputGesturesRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _customInputGesture;
    public final CoroutineContext bgCoroutineContext;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 customInputGestures;
    public final UserTracker userTracker;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public CustomInputGesturesRepository(UserTracker userTracker, CoroutineContext coroutineContext) {
        this.userTracker = userTracker;
        this.bgCoroutineContext = coroutineContext;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._customInputGesture = MutableStateFlow;
        this.customInputGestures = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CustomInputGesturesRepository$customInputGestures$1(this, null), MutableStateFlow);
    }

    public final InputManager getInputManager() {
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) this.userTracker;
        return (InputManager) userTrackerImpl.createCurrentUserContext(userTrackerImpl.getUserContext()).getSystemService("input");
    }
}
