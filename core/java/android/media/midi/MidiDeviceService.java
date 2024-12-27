package android.media.midi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

public abstract class MidiDeviceService extends Service {
    public static final String SERVICE_INTERFACE = "android.media.midi.MidiDeviceService";
    private static final String TAG = "MidiDeviceService";
    private final MidiDeviceServer.Callback mCallback =
            new MidiDeviceServer.Callback() { // from class: android.media.midi.MidiDeviceService.1
                @Override // android.media.midi.MidiDeviceServer.Callback
                public void onDeviceStatusChanged(
                        MidiDeviceServer server, MidiDeviceStatus status) {
                    MidiDeviceService.this.onDeviceStatusChanged(status);
                }

                @Override // android.media.midi.MidiDeviceServer.Callback
                public void onClose() {
                    MidiDeviceService.this.onClose();
                }
            };
    private MidiDeviceInfo mDeviceInfo;
    private IMidiManager mMidiManager;
    private MidiDeviceServer mServer;

    public abstract MidiReceiver[] onGetInputPortReceivers();

    @Override // android.app.Service
    public void onCreate() {
        MidiDeviceServer server;
        MidiDeviceInfo deviceInfo;
        this.mMidiManager = IMidiManager.Stub.asInterface(ServiceManager.getService("midi"));
        try {
            deviceInfo =
                    this.mMidiManager.getServiceDeviceInfo(getPackageName(), getClass().getName());
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in IMidiManager.getServiceDeviceInfo");
            server = null;
        }
        if (deviceInfo == null) {
            Log.e(TAG, "Could not find MidiDeviceInfo for MidiDeviceService " + this);
            return;
        }
        this.mDeviceInfo = deviceInfo;
        MidiReceiver[] inputPortReceivers = onGetInputPortReceivers();
        if (inputPortReceivers == null) {
            inputPortReceivers = new MidiReceiver[0];
        }
        server =
                new MidiDeviceServer(
                        this.mMidiManager, inputPortReceivers, deviceInfo, this.mCallback);
        this.mServer = server;
    }

    public final MidiReceiver[] getOutputPortReceivers() {
        if (this.mServer == null) {
            return null;
        }
        return this.mServer.getOutputPortReceivers();
    }

    public final MidiDeviceInfo getDeviceInfo() {
        return this.mDeviceInfo;
    }

    public void onDeviceStatusChanged(MidiDeviceStatus status) {}

    public void onClose() {}

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction()) && this.mServer != null) {
            return this.mServer.getBinderInterface().asBinder();
        }
        return null;
    }
}
