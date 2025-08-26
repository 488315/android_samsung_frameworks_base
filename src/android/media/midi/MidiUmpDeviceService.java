package android.media.midi;

import android.app.Service;
import android.content.Intent;
import android.media.midi.IMidiManager;
import android.media.midi.MidiDeviceServer;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class MidiUmpDeviceService extends Service {
    public static final String SERVICE_INTERFACE = "android.media.midi.MidiUmpDeviceService";
    private static final String TAG = "MidiUmpDeviceService";
    private final MidiDeviceServer.Callback mCallback = new MidiDeviceServer.Callback() { // from class: android.media.midi.MidiUmpDeviceService.1
        @Override // android.media.midi.MidiDeviceServer.Callback
        public void onDeviceStatusChanged(MidiDeviceServer midiDeviceServer, MidiDeviceStatus midiDeviceStatus) {
            MidiUmpDeviceService.this.onDeviceStatusChanged(midiDeviceStatus);
        }

        @Override // android.media.midi.MidiDeviceServer.Callback
        public void onClose() {
            MidiUmpDeviceService.this.onClose();
        }
    };
    private MidiDeviceInfo mDeviceInfo;
    private IMidiManager mMidiManager;
    private MidiDeviceServer mServer;

    public void onClose() {
    }

    public void onDeviceStatusChanged(MidiDeviceStatus midiDeviceStatus) {
    }

    public abstract List<MidiReceiver> onGetInputPortReceivers();

    @Override // android.app.Service
    public void onCreate() {
        MidiDeviceServer midiDeviceServer;
        MidiDeviceInfo serviceDeviceInfo;
        IMidiManager iMidiManagerAsInterface = IMidiManager.Stub.asInterface(ServiceManager.getService("midi"));
        this.mMidiManager = iMidiManagerAsInterface;
        try {
            serviceDeviceInfo = iMidiManagerAsInterface.getServiceDeviceInfo(getPackageName(), getClass().getName());
        } catch (RemoteException unused) {
            Log.e(TAG, "RemoteException in IMidiManager.getServiceDeviceInfo");
            midiDeviceServer = null;
        }
        if (serviceDeviceInfo == null) {
            Log.e(TAG, "Could not find MidiDeviceInfo for MidiUmpDeviceService " + this);
            return;
        }
        this.mDeviceInfo = serviceDeviceInfo;
        List<MidiReceiver> listOnGetInputPortReceivers = onGetInputPortReceivers();
        if (listOnGetInputPortReceivers == null) {
            Log.e(TAG, "Could not get input port receivers for MidiUmpDeviceService " + this);
        } else {
            MidiReceiver[] midiReceiverArr = new MidiReceiver[listOnGetInputPortReceivers.size()];
            listOnGetInputPortReceivers.toArray(midiReceiverArr);
            midiDeviceServer = new MidiDeviceServer(this.mMidiManager, midiReceiverArr, serviceDeviceInfo, this.mCallback);
            this.mServer = midiDeviceServer;
        }
    }

    public final List<MidiReceiver> getOutputPortReceivers() {
        MidiDeviceServer midiDeviceServer = this.mServer;
        if (midiDeviceServer == null) {
            return new ArrayList();
        }
        return Arrays.asList(midiDeviceServer.getOutputPortReceivers());
    }

    public final MidiDeviceInfo getDeviceInfo() {
        return this.mDeviceInfo;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        MidiDeviceServer midiDeviceServer;
        if (!SERVICE_INTERFACE.equals(intent.getAction()) || (midiDeviceServer = this.mServer) == null) {
            return null;
        }
        return midiDeviceServer.getBinderInterface().asBinder();
    }
}
