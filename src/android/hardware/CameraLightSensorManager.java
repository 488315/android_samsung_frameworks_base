package android.hardware;

import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.gesture.SemMotionRecognitionManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class CameraLightSensorManager {
    private short listenerRegistered;
    private ServiceConnection mCameraLightSensorConnection;
    private Sensor mCameraSensor;
    private boolean mConnectionFailed;
    private final Context mContext;
    private boolean mEnabledService;
    private boolean mProcessBinded;
    private ResponseHandler mSystemHandler;
    private Messenger mSystemIPC;
    private Messenger mSystemMessenger;
    private final String TAG = "CameraLightSensor_Manager";
    private final boolean DEBUG = true;
    private final boolean DEBUG_HANDLER = false;
    private final String DBG_TAG = "Debug_Camera_Manager";
    private final String LISTENER_TAG = "Debug_Camera_Listener";
    private final Object mLockIPC = new Object();
    private final int IPC_SERVICE_START_CAMERA = 1;
    private final int IPC_MANAGER_UPDATE_LUX = 2;
    private final int IPC_SERVICE_UNREGISTER_CONNECTION = 3;
    private final int MSG_SERVICE_START_CAMERA = 4;
    private final int MSG_SERVICE_STOP_CAMERA = 5;
    private final int MSG_MANAGER_UPDATE_LUX = 6;
    private final int MSG_SERVICE_CAMERA_LOOP = 7;
    private final int MSG_MANAGER_LISTENER_LOOP = 8;
    private List<SensorEventListener> mCameraLightListener = Collections.synchronizedList(new ArrayList());
    private List<String> mAllowListListenerList = new ArrayList();
    private KeyguardManager mkeyguard = null;
    private SemMotionRecognitionManager mMotionManager = null;
    private final Boolean mSABCLite = Boolean.valueOf(SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SENSOR_SUPPORT_SABC_LITE"));

    public CameraLightSensorManager(Context context) {
        this.mContext = context;
        initAllowListListenerList();
        this.mConnectionFailed = false;
        this.mEnabledService = false;
        this.mProcessBinded = false;
    }

    private void initLocked(Looper looper) {
        if (this.mSystemHandler == null) {
            if (looper == null) {
                HandlerThread handlerThread = new HandlerThread("CameraManagerThread", 19);
                handlerThread.start();
                this.mSystemHandler = new ResponseHandler(handlerThread.getLooper());
                Log.d("CameraLightSensor_Manager", "Using CameraManagerThread looper " + handlerThread.getLooper());
            } else {
                Log.d("CameraLightSensor_Manager", "Using handler looper " + looper);
                this.mSystemHandler = new ResponseHandler(looper);
            }
            this.mSystemMessenger = new Messenger(this.mSystemHandler);
        }
        if (this.mCameraLightSensorConnection == null) {
            Log.d("CameraLightSensor_Manager", "Creating new connection for service.");
            this.mCameraLightSensorConnection = new ServiceConnection() { // from class: android.hardware.CameraLightSensorManager.1
                @Override // android.content.ServiceConnection
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    Log.d("CameraLightSensor_Manager", " Service is connected, cmp name = " + componentName + " service = " + iBinder);
                    synchronized (CameraLightSensorManager.this.mLockIPC) {
                        CameraLightSensorManager.this.mSystemIPC = new Messenger(iBinder);
                        if (CameraLightSensorManager.this.mSystemHandler != null && CameraLightSensorManager.this.mEnabledService && CameraLightSensorManager.this.mConnectionFailed) {
                            CameraLightSensorManager.this.mSystemHandler.removeMessages(1);
                            CameraLightSensorManager.this.mSystemHandler.sendEmptyMessage(1);
                            CameraLightSensorManager.this.mConnectionFailed = false;
                        }
                    }
                }

                @Override // android.content.ServiceConnection
                public void onServiceDisconnected(ComponentName componentName) {
                    Log.d("CameraLightSensor_Manager", " Service is disconnected, cmp name = " + componentName);
                    synchronized (CameraLightSensorManager.this.mLockIPC) {
                        CameraLightSensorManager.this.mProcessBinded = false;
                        CameraLightSensorManager.this.mSystemIPC = null;
                        if (CameraLightSensorManager.this.mCameraLightSensorConnection != null) {
                            Log.d("CameraLightSensor_Manager", "Unbinding and removing connection for service.");
                            CameraLightSensorManager.this.mContext.unbindService(CameraLightSensorManager.this.mCameraLightSensorConnection);
                            CameraLightSensorManager.this.mCameraLightSensorConnection = null;
                            CameraLightSensorManager.this.mSystemHandler = null;
                        }
                    }
                }
            };
        }
        if (this.mProcessBinded) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.samsung.adaptivebrightnessgo", "com.samsung.adaptivebrightnessgo.CameraLightSensorService"));
            this.mProcessBinded = this.mContext.bindServiceAsUser(intent, this.mCameraLightSensorConnection, 1, UserHandle.CURRENT_OR_SELF);
            Log.d("CameraLightSensor_Manager", "Binding Package status= " + this.mProcessBinded + " Component= " + intent.getComponent() + " UserHandle= " + UserHandle.CURRENT_OR_SELF);
            if (this.mMotionManager == null) {
                this.mMotionManager = (SemMotionRecognitionManager) this.mContext.getSystemService(Context.SEM_MOTION_RECOGNITION_SERVICE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("CameraLightSensor_Manager", "Exception while starting service.");
        }
    }

    protected boolean isAvailableEvLuxTable() {
        if (this.mMotionManager == null) {
            this.mMotionManager = (SemMotionRecognitionManager) this.mContext.getSystemService(Context.SEM_MOTION_RECOGNITION_SERVICE);
        }
        return this.mMotionManager.getEvToLux(new float[]{100.0f, 100.0f})[0] >= 0.0f;
    }

    public boolean registerCameraLightSensor(Looper looper, SensorEventListener sensorEventListener, Sensor sensor) {
        synchronized (this.mLockIPC) {
            if (this.mCameraLightListener != null) {
                if (isAllowListedListener(sensorEventListener.toString())) {
                    if (!isAvailableEvLuxTable()) {
                        Log.d("Debug_Camera_Manager", "There is no Ev-Lux table for this model ");
                        return false;
                    }
                    initLocked(looper);
                    this.mEnabledService = true;
                    Log.d("Debug_Camera_Manager", "Entering1 registerCameraLightSensor " + sensorEventListener);
                    synchronized (this.mCameraLightListener) {
                        if (this.mCameraLightListener.contains(sensorEventListener)) {
                            this.listenerRegistered = (short) 1;
                        } else {
                            this.mCameraLightListener.add(sensorEventListener);
                            this.listenerRegistered = (short) 0;
                        }
                    }
                    Log.d("Debug_Camera_Manager", "Exit1 registerCameraLightSensor" + sensorEventListener);
                    this.mCameraSensor = sensor;
                    if (sensorEventListener.toString().contains("com.android.server.display.AutomaticBrightnessController")) {
                        try {
                            if (this.mkeyguard == null) {
                                this.mkeyguard = (KeyguardManager) this.mContext.getSystemService(Context.KEYGUARD_SERVICE);
                            }
                            boolean z = new LockPatternUtils(this.mContext).getBiometricState(256, UserHandle.semGetMyUserId()) == 1;
                            if (this.mkeyguard != null) {
                                Log.d("Debug_Camera_Manager", "lock screen, mkeyguard= " + this.mkeyguard.isDeviceLocked() + " isFaceUnlockActive= " + z);
                            }
                            KeyguardManager keyguardManager = this.mkeyguard;
                            if (keyguardManager != null && keyguardManager.isDeviceLocked() && z) {
                                Log.d("CameraLightSensor_Manager", "On lock screen, FaceUnlock is true");
                                ResponseHandler responseHandler = this.mSystemHandler;
                                if (responseHandler != null) {
                                    responseHandler.sendEmptyMessageDelayed(1, 300L);
                                }
                            } else {
                                Log.d("CameraLightSensor_Manager", "On lock screen, FaceUnlock is false");
                                ResponseHandler responseHandler2 = this.mSystemHandler;
                                if (responseHandler2 != null) {
                                    responseHandler2.sendEmptyMessage(1);
                                }
                            }
                            Log.d("CameraLightSensor_Manager", sensorEventListener + " registered");
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("CameraLightSensor_Manager", "Error during FaceUnlock check.");
                        }
                    } else {
                        ResponseHandler responseHandler3 = this.mSystemHandler;
                        if (responseHandler3 != null) {
                            responseHandler3.sendEmptyMessage(1);
                        }
                        Log.d("CameraLightSensor_Manager", sensorEventListener + " registered");
                    }
                    return true;
                }
                Log.e("CameraLightSensor_Manager", sensorEventListener + " not in allowlisted packages of Camera Light Sensor.");
            }
            Log.e("CameraLightSensor_Manager", "mCameraLightListener is NULL");
            return false;
        }
    }

    public boolean unRegisterCameraLightSensor(SensorEventListener sensorEventListener) {
        Log.d("Debug_Camera_Manager", "Entering2 unRegisterCameraLightSensor" + sensorEventListener);
        synchronized (this.mLockIPC) {
            List<SensorEventListener> list = this.mCameraLightListener;
            if (list != null) {
                synchronized (list) {
                    if (!this.mCameraLightListener.contains(sensorEventListener)) {
                        Log.e("CameraLightSensor_Manager", "No camera light sensor listeners present");
                        return false;
                    }
                    this.mCameraLightListener.remove(sensorEventListener);
                    Log.d("Debug_Camera_Manager", "Exit2 unRegisterCameraLightSensor" + sensorEventListener);
                    this.mEnabledService = false;
                    ResponseHandler responseHandler = this.mSystemHandler;
                    if (responseHandler != null) {
                        responseHandler.removeMessages(1);
                        this.mSystemHandler.sendEmptyMessage(3);
                    }
                    return true;
                }
            }
            Log.e("CameraLightSensor_Manager", "mCameraLightListener List is not initialized");
            return false;
        }
    }

    private void initAllowListListenerList() {
        if (this.mSABCLite.booleanValue()) {
            this.mAllowListListenerList.add("com.android.server.display.AutomaticBrightnessController");
            this.mAllowListListenerList.add("com.samsung.adaptivebrightnessgo");
            this.mAllowListListenerList.add("com.sec.android.app.camera");
            return;
        }
        this.mAllowListListenerList.add("com.sec.android.app.camera");
        this.mAllowListListenerList.add("com.android.server.display.AutomaticBrightnessController");
        this.mAllowListListenerList.add("com.samsung.android.app.aodservice.ui.AODUiController");
        this.mAllowListListenerList.add("com.samsung.android.app.aodservice.ui.model");
        this.mAllowListListenerList.add("com.samsung.adaptivebrightnessgo");
        this.mAllowListListenerList.add("com.samsung.android.gesture.AdaptiveBrightnessController");
        this.mAllowListListenerList.add("com.samsung.android.gesture.PocketModeEvent");
        this.mAllowListListenerList.add("com.sec.android.sdhms.thermal.overheatcontrol.OverheatSensorChecker");
        this.mAllowListListenerList.add("com.sec.android.sdhms.thermal.siop.scenario.HighBrightness");
        this.mAllowListListenerList.add("com.salab.act");
    }

    public boolean isAllowListedListener(String str) {
        for (int i = 0; i < this.mAllowListListenerList.size(); i++) {
            if (str.contains(this.mAllowListListenerList.get(i))) {
                return (this.mContext.getApplicationInfo().flags & 129) != 0;
            }
        }
        return false;
    }

    class ResponseHandler extends Handler {
        public float[] camera_ev_bv;
        private float old_lux;
        private int retry;
        private int retyrBinding;

        public ResponseHandler(Looper looper) {
            super(looper);
            this.camera_ev_bv = new float[2];
            this.retyrBinding = 60;
            this.old_lux = 0.0f;
            this.retry = 5;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                synchronized (CameraLightSensorManager.this.mLockIPC) {
                    Message messageObtain = Message.obtain(null, 4, 0, (int) Math.random());
                    messageObtain.replyTo = CameraLightSensorManager.this.mSystemMessenger;
                    if (CameraLightSensorManager.this.mSystemIPC != null) {
                        try {
                            Log.d("CameraLightSensor_Manager", "IPC_SERVICE_START_CAMERA -> MSG_SERVICE_START_CAMERA");
                            Bundle bundle = new Bundle();
                            bundle.putShort("respAlreadyRegister", CameraLightSensorManager.this.listenerRegistered);
                            messageObtain.setData(bundle);
                            CameraLightSensorManager.this.mSystemIPC.send(messageObtain);
                            this.retry = 5;
                        } catch (Exception e) {
                            Log.d("CameraLightSensor_Manager", "IPC_SERVICE_START_CAMERA -> Dead Object");
                            e.printStackTrace();
                        }
                    } else if (!CameraLightSensorManager.this.mProcessBinded && CameraLightSensorManager.this.mSystemHandler != null && CameraLightSensorManager.this.mEnabledService && this.retyrBinding > 0) {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName("com.samsung.adaptivebrightnessgo", "com.samsung.adaptivebrightnessgo.CameraLightSensorService"));
                        CameraLightSensorManager cameraLightSensorManager = CameraLightSensorManager.this;
                        Context context = cameraLightSensorManager.mContext;
                        ServiceConnection serviceConnection = CameraLightSensorManager.this.mCameraLightSensorConnection;
                        Context unused = CameraLightSensorManager.this.mContext;
                        cameraLightSensorManager.mProcessBinded = context.bindServiceAsUser(intent, serviceConnection, 1, UserHandle.CURRENT_OR_SELF);
                        Log.d("CameraLightSensor_Manager", "Binding Package status= " + CameraLightSensorManager.this.mProcessBinded + " Component= " + intent.getComponent() + " UserHandle= " + UserHandle.CURRENT_OR_SELF);
                        StringBuilder sb = new StringBuilder("IPC_SERVICE_START_CAMERA Service not started yet,retry binding service = ");
                        sb.append(this.retyrBinding);
                        Log.d("CameraLightSensor_Manager", sb.toString());
                        if (CameraLightSensorManager.this.mProcessBinded) {
                            this.retry = 20;
                        }
                        this.retyrBinding--;
                        CameraLightSensorManager.this.mSystemHandler.sendEmptyMessageDelayed(1, 1000L);
                    } else if (CameraLightSensorManager.this.mSystemHandler != null && this.retry > 0) {
                        Log.d("CameraLightSensor_Manager", "IPC_SERVICE_START_CAMERA Service not started yet,retry= " + this.retry);
                        this.retry = this.retry - 1;
                        CameraLightSensorManager.this.mSystemHandler.sendEmptyMessageDelayed(1, 500L);
                    } else if (this.retry == 0) {
                        Log.d("CameraLightSensor_Manager", "IPC_SERVICE_START_CAMERA Service not started yet, will try it again after service connected");
                        CameraLightSensorManager.this.mConnectionFailed = true;
                    }
                }
                return;
            }
            if (i == 3) {
                synchronized (CameraLightSensorManager.this.mLockIPC) {
                    CameraLightSensorManager.this.mConnectionFailed = false;
                    Message messageObtain2 = Message.obtain(null, 5, 0, (int) Math.random());
                    messageObtain2.replyTo = CameraLightSensorManager.this.mSystemMessenger;
                    if (CameraLightSensorManager.this.mSystemIPC != null) {
                        try {
                            Log.d("CameraLightSensor_Manager", "IPC_SERVICE_UNREGISTER_CONNECTION -> MSG_SERVICE_STOP_CAMERA");
                            CameraLightSensorManager.this.mSystemIPC.send(messageObtain2);
                        } catch (Exception e2) {
                            Log.d("CameraLightSensor_Manager", "IPC_SERVICE_UNREGISTER_CONNECTION -> Dead Object");
                            e2.printStackTrace();
                        }
                    }
                }
                return;
            }
            if (i == 6) {
                synchronized (CameraLightSensorManager.this.mLockIPC) {
                    float[] floatArray = message.getData().getFloatArray("respData");
                    this.camera_ev_bv = floatArray;
                    if (floatArray == null) {
                        Log.d("CameraLightSensor_Manager", "received a null event from service");
                        return;
                    }
                    if (CameraLightSensorManager.this.mSystemHandler != null) {
                        CameraLightSensorManager.this.mSystemHandler.removeMessages(8);
                    }
                    if (CameraLightSensorManager.this.mCameraLightListener != null && CameraLightSensorManager.this.mCameraLightListener.size() > 0) {
                        if (CameraLightSensorManager.this.mSystemHandler != null) {
                            CameraLightSensorManager.this.mSystemHandler.sendEmptyMessage(8);
                        }
                        Log.d("CameraLightSensor_Manager", " Exposure= " + this.camera_ev_bv[0] + " Brightness= " + this.camera_ev_bv[1] + " Total listeners= " + CameraLightSensorManager.this.mCameraLightListener.size());
                    }
                    return;
                }
            }
            if (i == 8) {
                synchronized (CameraLightSensorManager.this.mLockIPC) {
                    if (CameraLightSensorManager.this.mCameraLightListener != null) {
                        synchronized (CameraLightSensorManager.this.mCameraLightListener) {
                            if (CameraLightSensorManager.this.mCameraLightListener.size() > 0) {
                                SensorEvent sensorEvent = new SensorEvent(3);
                                sensorEvent.sensor = CameraLightSensorManager.this.mCameraSensor;
                                sensorEvent.timestamp = SystemClock.elapsedRealtimeNanos();
                                try {
                                    for (SensorEventListener sensorEventListener : CameraLightSensorManager.this.mCameraLightListener) {
                                        if (sensorEventListener != null && this.camera_ev_bv != null) {
                                            float[] evToLux = CameraLightSensorManager.this.mMotionManager.getEvToLux(this.camera_ev_bv);
                                            if (evToLux[0] != this.old_lux) {
                                                Log.d("Debug_Camera_Listener", "" + sensorEventListener.toString() + " lux= " + evToLux[0] + " exposure= " + evToLux[1] + " bv= " + evToLux[2]);
                                            }
                                            this.old_lux = evToLux[0];
                                            sensorEvent.values[0] = evToLux[0];
                                            sensorEvent.values[1] = evToLux[1];
                                            sensorEvent.values[2] = evToLux[2];
                                            sensorEventListener.onSensorChanged(sensorEvent);
                                        }
                                    }
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                    Log.d("CameraLightSensor_Manager", "Exception while getting listener.");
                                }
                                if (CameraLightSensorManager.this.mSystemHandler != null) {
                                    CameraLightSensorManager.this.mSystemHandler.removeMessages(8);
                                    CameraLightSensorManager.this.mSystemHandler.sendEmptyMessageDelayed(8, 200L);
                                } else {
                                    Log.d("CameraLightSensor_Manager", "MSG_MANAGER_LISTENER_LOOP mSystemHandler is null");
                                }
                            } else {
                                Log.d("CameraLightSensor_Manager", "MSG_MANAGER_LISTENER_LOOP array is " + CameraLightSensorManager.this.mCameraLightListener.size());
                                if (CameraLightSensorManager.this.mSystemHandler != null) {
                                    CameraLightSensorManager.this.mSystemHandler.removeMessages(8);
                                }
                            }
                        }
                    }
                }
                return;
            }
            super.handleMessage(message);
        }
    }
}
