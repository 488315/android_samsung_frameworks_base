package com.android.systemui.inputdevice.data.repository;

import android.hardware.input.InputManager;
import android.os.Handler;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InputDeviceRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Handler backgroundHandler;
    public final ReadonlySharedFlow deviceChange;
    public final InputManager inputManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DeviceAdded implements DeviceChange {
        public final int deviceId;

        public DeviceAdded(int i) {
            this.deviceId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DeviceAdded) && this.deviceId == ((DeviceAdded) obj).deviceId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.deviceId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.deviceId, ")", new StringBuilder("DeviceAdded(deviceId="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface DeviceChange {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DeviceRemoved implements DeviceChange {
        public final int deviceId;

        public DeviceRemoved(int i) {
            this.deviceId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DeviceRemoved) && this.deviceId == ((DeviceRemoved) obj).deviceId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.deviceId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.deviceId, ")", new StringBuilder("DeviceRemoved(deviceId="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FreshStart implements DeviceChange {
        public static final FreshStart INSTANCE = new FreshStart();

        private FreshStart() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof FreshStart);
        }

        public final int hashCode() {
            return -375358235;
        }

        public final String toString() {
            return "FreshStart";
        }
    }

    static {
        new Companion(null);
    }

    public InputDeviceRepository(Handler handler, CoroutineScope coroutineScope, InputManager inputManager) {
        this.backgroundHandler = handler;
        this.inputManager = inputManager;
        Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new InputDeviceRepository$deviceChange$1(this, null));
        SharingStarted.Companion.getClass();
        this.deviceChange = FlowKt.shareIn(conflatedCallbackFlow, coroutineScope, SharingStarted.Companion.Lazily, 1);
    }
}
