package android.media.midi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MidiUmpDeviceService extends Service {
    public static final String SERVICE_INTERFACE = "android.media.midi.MidiUmpDeviceService";
    private static final String TAG = "MidiUmpDeviceService";
    private final MidiDeviceServer.Callback mCallback =
            new MidiDeviceServer
                    .Callback() { // from class: android.media.midi.MidiUmpDeviceService.1
                @Override // android.media.midi.MidiDeviceServer.Callback
                public void onDeviceStatusChanged(
                        MidiDeviceServer server, MidiDeviceStatus status) {
                    MidiUmpDeviceService.this.onDeviceStatusChanged(status);
                }

                @Override // android.media.midi.MidiDeviceServer.Callback
                public void onClose() {
                    MidiUmpDeviceService.this.onClose();
                }
            };
    private MidiDeviceInfo mDeviceInfo;
    private IMidiManager mMidiManager;
    private MidiDeviceServer mServer;

    public abstract List<MidiReceiver> onGetInputPortReceivers();

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
            Log.e(TAG, "Could not find MidiDeviceInfo for MidiUmpDeviceService " + this);
            return;
        }
        this.mDeviceInfo = deviceInfo;
        List<MidiReceiver> inputPortReceivers = onGetInputPortReceivers();
        if (inputPortReceivers == null) {
            Log.e(TAG, "Could not get input port receivers for MidiUmpDeviceService " + this);
            return;
        }
        MidiReceiver[] inputPortReceiversArr = new MidiReceiver[inputPortReceivers.size()];
        inputPortReceivers.toArray(inputPortReceiversArr);
        server =
                new MidiDeviceServer(
                        this.mMidiManager, inputPortReceiversArr, deviceInfo, this.mCallback);
        this.mServer = server;
    }

    public final List<MidiReceiver> getOutputPortReceivers() {
        if (this.mServer == null) {
            return new ArrayList();
        }
        return Arrays.asList(this.mServer.getOutputPortReceivers());
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
