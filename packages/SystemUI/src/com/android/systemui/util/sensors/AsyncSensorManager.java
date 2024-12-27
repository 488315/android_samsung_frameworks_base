package com.android.systemui.util.sensors;

import android.content.Context;
import android.hardware.HardwareBuffer;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorDirectChannel;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEventListener;
import android.os.Handler;
import android.os.MemoryFile;
import android.util.Log;
import com.android.internal.util.Preconditions;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.SensorManagerPlugin;
import com.android.systemui.util.concurrency.ThreadFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class AsyncSensorManager extends SensorManager implements PluginListener<SensorManagerPlugin> {
    private static final String TAG = "AsyncSensorManager";
    private final Executor mExecutor;
    private final SensorManager mInner;
    private final List<SensorManagerPlugin> mPlugins = new ArrayList();
    private final List<Sensor> mSensorCache;

    public AsyncSensorManager(SensorManager sensorManager, ThreadFactory threadFactory, PluginManager pluginManager) {
        this.mInner = sensorManager;
        this.mExecutor = threadFactory.buildExecutorOnNewThread("async_sensor");
        this.mSensorCache = sensorManager.getSensorList(-1);
        if (pluginManager != null) {
            pluginManager.addPluginListener((PluginListener) this, SensorManagerPlugin.class, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelTriggerSensorImpl$4(TriggerEventListener triggerEventListener, Sensor sensor) {
        if (this.mInner.cancelTriggerSensor(triggerEventListener, sensor)) {
            return;
        }
        Log.e(TAG, "Canceling " + triggerEventListener + " for " + sensor + " failed.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerDynamicSensorCallbackImpl$1(SensorManager.DynamicSensorCallback dynamicSensorCallback, Handler handler) {
        this.mInner.registerDynamicSensorCallback(dynamicSensorCallback, handler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerListenerImpl$0(SensorEventListener sensorEventListener, Sensor sensor, int i, int i2, Handler handler) {
        if (this.mInner.registerListener(sensorEventListener, sensor, i, i2, handler)) {
            return;
        }
        Log.e(TAG, "Registering " + sensorEventListener + " for " + sensor + " failed.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerPluginListener$5(SensorManagerPlugin.Sensor sensor, SensorManagerPlugin.SensorEventListener sensorEventListener) {
        for (int i = 0; i < this.mPlugins.size(); i++) {
            this.mPlugins.get(i).registerListener(sensor, sensorEventListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestTriggerSensorImpl$3(TriggerEventListener triggerEventListener, Sensor sensor) {
        if (this.mInner.requestTriggerSensor(triggerEventListener, sensor)) {
            return;
        }
        Log.e(TAG, "Requesting " + triggerEventListener + " for " + sensor + " failed.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setOperationParameterImpl$7(SensorAdditionalInfo sensorAdditionalInfo) {
        this.mInner.setOperationParameter(sensorAdditionalInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unregisterDynamicSensorCallbackImpl$2(SensorManager.DynamicSensorCallback dynamicSensorCallback) {
        this.mInner.unregisterDynamicSensorCallback(dynamicSensorCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unregisterListenerImpl$8(Sensor sensor, SensorEventListener sensorEventListener) {
        if (sensor == null) {
            this.mInner.unregisterListener(sensorEventListener);
        } else {
            this.mInner.unregisterListener(sensorEventListener, sensor);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unregisterPluginListener$6(SensorManagerPlugin.Sensor sensor, SensorManagerPlugin.SensorEventListener sensorEventListener) {
        for (int i = 0; i < this.mPlugins.size(); i++) {
            this.mPlugins.get(i).unregisterListener(sensor, sensorEventListener);
        }
    }

    public boolean cancelTriggerSensorImpl(TriggerEventListener triggerEventListener, Sensor sensor, boolean z) {
        Preconditions.checkArgument(z);
        this.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda6(this, triggerEventListener, sensor, 0));
        return true;
    }

    public int configureDirectChannelImpl(SensorDirectChannel sensorDirectChannel, Sensor sensor, int i) {
        throw new UnsupportedOperationException("not implemented");
    }

    public SensorDirectChannel createDirectChannelImpl(MemoryFile memoryFile, HardwareBuffer hardwareBuffer) {
        throw new UnsupportedOperationException("not implemented");
    }

    public void destroyDirectChannelImpl(SensorDirectChannel sensorDirectChannel) {
        throw new UnsupportedOperationException("not implemented");
    }

    public boolean flushImpl(SensorEventListener sensorEventListener) {
        return this.mInner.flush(sensorEventListener);
    }

    public List<Sensor> getFullDynamicSensorList() {
        return this.mInner.getSensorList(-1);
    }

    public List<Sensor> getFullSensorList() {
        return this.mSensorCache;
    }

    public boolean initDataInjectionImpl(boolean z, int i) {
        throw new UnsupportedOperationException("not implemented");
    }

    public boolean injectSensorDataImpl(Sensor sensor, float[] fArr, int i, long j) {
        throw new UnsupportedOperationException("not implemented");
    }

    public void registerDynamicSensorCallbackImpl(SensorManager.DynamicSensorCallback dynamicSensorCallback, Handler handler) {
        this.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda4(this, dynamicSensorCallback, handler, 1));
    }

    public boolean registerListenerImpl(final SensorEventListener sensorEventListener, final Sensor sensor, final int i, final Handler handler, final int i2, int i3) {
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.util.sensors.AsyncSensorManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AsyncSensorManager.this.lambda$registerListenerImpl$0(sensorEventListener, sensor, i, i2, handler);
            }
        });
        return true;
    }

    public boolean registerPluginListener(SensorManagerPlugin.Sensor sensor, SensorManagerPlugin.SensorEventListener sensorEventListener) {
        if (this.mPlugins.isEmpty()) {
            Log.w(TAG, "No plugins registered");
            return false;
        }
        this.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda0(this, sensor, sensorEventListener, 1));
        return true;
    }

    public boolean requestTriggerSensorImpl(TriggerEventListener triggerEventListener, Sensor sensor) {
        if (triggerEventListener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }
        if (sensor == null) {
            throw new IllegalArgumentException("sensor cannot be null");
        }
        this.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda6(this, triggerEventListener, sensor, 1));
        return true;
    }

    public boolean setOperationParameterImpl(SensorAdditionalInfo sensorAdditionalInfo) {
        this.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda1(this, sensorAdditionalInfo, 0));
        return true;
    }

    public void unregisterDynamicSensorCallbackImpl(SensorManager.DynamicSensorCallback dynamicSensorCallback) {
        this.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda1(this, dynamicSensorCallback, 1));
    }

    public void unregisterListenerImpl(SensorEventListener sensorEventListener, Sensor sensor) {
        this.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda4(this, sensor, sensorEventListener, 0));
    }

    public void unregisterPluginListener(SensorManagerPlugin.Sensor sensor, SensorManagerPlugin.SensorEventListener sensorEventListener) {
        this.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda0(this, sensor, sensorEventListener, 0));
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginConnected(SensorManagerPlugin sensorManagerPlugin, Context context) {
        this.mPlugins.add(sensorManagerPlugin);
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginDisconnected(SensorManagerPlugin sensorManagerPlugin) {
        this.mPlugins.remove(sensorManagerPlugin);
    }
}
