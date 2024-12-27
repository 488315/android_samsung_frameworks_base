package com.android.systemui.statusbar.policy;

import android.R;
import android.content.Context;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.devicestate.DeviceStateUtil;
import android.os.Trace;
import android.util.SparseIntArray;
import com.android.app.tracing.ListenersTracing;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.Assert;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.PropertyReference0Impl;

public final class DevicePostureControllerImpl implements DevicePostureController {
    public DeviceState mCurrentDeviceState;
    public final List mSupportedStates;
    public final List mListeners = new CopyOnWriteArrayList();
    public int mCurrentDevicePosture = 0;
    public final SparseIntArray mDeviceStateToPostureMap = new SparseIntArray();

    /* renamed from: com.android.systemui.statusbar.policy.DevicePostureControllerImpl$1, reason: invalid class name */
    public final class AnonymousClass1 implements DeviceStateManager.DeviceStateCallback {
        public AnonymousClass1() {
        }

        /* JADX WARN: Type inference failed for: r2v0, types: [com.android.app.tracing.ListenersTracing$forEachTraced$1$1$1] */
        public final void onDeviceStateChanged(DeviceState deviceState) {
            DevicePostureControllerImpl.this.mCurrentDeviceState = deviceState;
            Assert.isMainThread();
            int i = DevicePostureControllerImpl.this.mDeviceStateToPostureMap.get(deviceState.getIdentifier(), 0);
            DevicePostureControllerImpl devicePostureControllerImpl = DevicePostureControllerImpl.this;
            if (i != devicePostureControllerImpl.mCurrentDevicePosture || i == 1000) {
                devicePostureControllerImpl.mCurrentDevicePosture = i;
                ListenersTracing listenersTracing = ListenersTracing.INSTANCE;
                List list = devicePostureControllerImpl.mListeners;
                Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.policy.DevicePostureControllerImpl$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ((DevicePostureController.Callback) obj).onPostureChanged(DevicePostureControllerImpl.this.getDevicePosture());
                        return Unit.INSTANCE;
                    }
                };
                listenersTracing.getClass();
                Iterator it = ((CopyOnWriteArrayList) list).iterator();
                while (it.hasNext()) {
                    final Object next = it.next();
                    boolean isEnabled = Trace.isEnabled();
                    if (isEnabled) {
                        TraceUtilsKt.beginSlice("DevicePostureControllerImpl#".concat(((Class) new PropertyReference0Impl(next) { // from class: com.android.app.tracing.ListenersTracing$forEachTraced$1$1$1
                            @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                            public final Object get() {
                                return this.receiver.getClass();
                            }
                        }.get()).getName()));
                    }
                    try {
                        function1.invoke(next);
                        Unit unit = Unit.INSTANCE;
                        if (isEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                    } catch (Throwable th) {
                        if (isEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                        throw th;
                    }
                }
            }
        }
    }

    public DevicePostureControllerImpl(Context context, DeviceStateManager deviceStateManager, Executor executor) {
        for (String str : context.getResources().getStringArray(R.array.config_tether_bluetooth_regexs)) {
            String[] split = str.split(":");
            if (split.length == 2) {
                try {
                    this.mDeviceStateToPostureMap.put(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                } catch (NumberFormatException unused) {
                }
            }
        }
        this.mSupportedStates = deviceStateManager.getSupportedDeviceStates();
        deviceStateManager.registerCallback(executor, new AnonymousClass1());
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        Assert.isMainThread();
        ((CopyOnWriteArrayList) this.mListeners).add((DevicePostureController.Callback) obj);
    }

    public final int getDevicePosture() {
        int i = this.mCurrentDevicePosture;
        return i == 1000 ? DeviceStateUtil.calculateBaseStateIdentifier(this.mCurrentDeviceState, this.mSupportedStates) : i;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        Assert.isMainThread();
        ((CopyOnWriteArrayList) this.mListeners).remove((DevicePostureController.Callback) obj);
    }
}
