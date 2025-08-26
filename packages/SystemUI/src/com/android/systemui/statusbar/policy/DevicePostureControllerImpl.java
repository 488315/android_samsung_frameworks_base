package com.android.systemui.statusbar.policy;

import android.R;
import android.content.Context;
import android.content.res.Resources;
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
import kotlin.jvm.internal.PropertyReference0Impl;

/* loaded from: classes3.dex */
public class DevicePostureControllerImpl implements DevicePostureController {
    public DeviceState mCurrentDeviceState;
    public final List mSupportedStates;
    public final List mListeners = new CopyOnWriteArrayList();
    public int mCurrentDevicePosture = 0;
    public final SparseIntArray mDeviceStateToPostureMap = new SparseIntArray();

    public DevicePostureControllerImpl(Context context, DeviceStateManager deviceStateManager, Executor executor) throws Resources.NotFoundException, NumberFormatException {
        for (String str : context.getResources().getStringArray(R.array.config_udfps_enroll_stage_thresholds)) {
            String[] strArrSplit = str.split(":");
            if (strArrSplit.length == 2) {
                try {
                    this.mDeviceStateToPostureMap.put(Integer.parseInt(strArrSplit[0]), Integer.parseInt(strArrSplit[1]));
                } catch (NumberFormatException unused) {
                }
            }
        }
        this.mSupportedStates = deviceStateManager.getSupportedDeviceStates();
        deviceStateManager.registerCallback(executor, new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.statusbar.policy.DevicePostureControllerImpl.1
            /* JADX WARN: Type inference failed for: r2v2, types: [com.android.app.tracing.ListenersTracing$forEachTraced$1$1$1] */
            public final void onDeviceStateChanged(DeviceState deviceState) {
                DevicePostureControllerImpl.this.mCurrentDeviceState = deviceState;
                Assert.isMainThread();
                int i = DevicePostureControllerImpl.this.mDeviceStateToPostureMap.get(deviceState.getIdentifier(), 0);
                DevicePostureControllerImpl devicePostureControllerImpl = DevicePostureControllerImpl.this;
                if (i != devicePostureControllerImpl.mCurrentDevicePosture || i == 1000) {
                    devicePostureControllerImpl.mCurrentDevicePosture = i;
                    ListenersTracing listenersTracing = ListenersTracing.INSTANCE;
                    List list = devicePostureControllerImpl.mListeners;
                    listenersTracing.getClass();
                    Iterator it = ((CopyOnWriteArrayList) list).iterator();
                    while (it.hasNext()) {
                        final Object next = it.next();
                        boolean zIsEnabled = Trace.isEnabled();
                        if (zIsEnabled) {
                            TraceUtilsKt.beginSlice("DevicePostureControllerImpl#".concat(((Class) new PropertyReference0Impl(next) { // from class: com.android.app.tracing.ListenersTracing$forEachTraced$1$1$1
                                @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                                public final Object get() {
                                    return this.receiver.getClass();
                                }
                            }.get()).getName()));
                        }
                        try {
                            ((DevicePostureController.Callback) next).onPostureChanged(DevicePostureControllerImpl.this.getDevicePosture());
                            Unit unit = Unit.INSTANCE;
                            Unit unit2 = Unit.INSTANCE;
                            if (zIsEnabled) {
                                TraceUtilsKt.endSlice();
                            }
                        } catch (Throwable th) {
                            if (zIsEnabled) {
                                TraceUtilsKt.endSlice();
                            }
                            throw th;
                        }
                    }
                }
            }
        });
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
