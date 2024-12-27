package com.android.server.usb;

import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.media.midi.MidiDeviceInfo;
import android.media.midi.MidiDeviceServer;
import android.media.midi.MidiDeviceStatus;
import android.media.midi.MidiManager;
import android.media.midi.MidiReceiver;
import android.os.Bundle;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructPollfd;
import android.util.Log;
import com.android.internal.midi.MidiEventScheduler;
import com.android.internal.util.dump.DualDumpOutputStream;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import libcore.io.IoUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbAlsaMidiDevice implements Closeable {
    public static final int BUFFER_SIZE = 512;
    public static final String TAG = "UsbAlsaMidiDevice";
    public final int mAlsaCard;
    public final int mAlsaDevice;
    public MidiEventScheduler[] mEventSchedulers;
    public FileDescriptor[] mFileDescriptors;
    public FileInputStream[] mInputStreams;
    public boolean mIsOpen;
    public final InputReceiverProxy[] mMidiInputPortReceivers;
    public final int mNumInputs;
    public final int mNumOutputs;
    public FileOutputStream[] mOutputStreams;
    public StructPollfd[] mPollFDs;
    public PowerBoostSetter mPowerBoostSetter;
    public MidiDeviceServer mServer;
    public boolean mServerAvailable;
    public final Object mLock = new Object();
    public int mPipeFD = -1;
    public final MidiDeviceServer.Callback mCallback = new MidiDeviceServer.Callback() { // from class: com.android.server.usb.UsbAlsaMidiDevice.1
        public final void onClose() {
        }

        public final void onDeviceStatusChanged(MidiDeviceServer midiDeviceServer, MidiDeviceStatus midiDeviceStatus) {
            MidiDeviceInfo deviceInfo = midiDeviceStatus.getDeviceInfo();
            int inputPortCount = deviceInfo.getInputPortCount();
            int outputPortCount = deviceInfo.getOutputPortCount();
            int i = 0;
            for (int i2 = 0; i2 < inputPortCount; i2++) {
                if (midiDeviceStatus.isInputPortOpen(i2)) {
                    i++;
                }
            }
            for (int i3 = 0; i3 < outputPortCount; i3++) {
                if (midiDeviceStatus.getOutputPortOpenCount(i3) > 0) {
                    i = midiDeviceStatus.getOutputPortOpenCount(i3) + i;
                }
            }
            synchronized (UsbAlsaMidiDevice.this.mLock) {
                try {
                    Log.d(UsbAlsaMidiDevice.TAG, "numOpenPorts: " + i + " isOpen: " + UsbAlsaMidiDevice.this.mIsOpen + " mServerAvailable: " + UsbAlsaMidiDevice.this.mServerAvailable);
                    if (i > 0) {
                        UsbAlsaMidiDevice usbAlsaMidiDevice = UsbAlsaMidiDevice.this;
                        if (!usbAlsaMidiDevice.mIsOpen && usbAlsaMidiDevice.mServerAvailable) {
                            usbAlsaMidiDevice.openLocked();
                        }
                    }
                    if (i == 0) {
                        UsbAlsaMidiDevice usbAlsaMidiDevice2 = UsbAlsaMidiDevice.this;
                        if (usbAlsaMidiDevice2.mIsOpen) {
                            usbAlsaMidiDevice2.closeLocked();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InputReceiverProxy extends MidiReceiver {
        public MidiReceiver mReceiver;

        @Override // android.media.midi.MidiReceiver
        public final void onFlush() {
            MidiReceiver midiReceiver = this.mReceiver;
            if (midiReceiver != null) {
                midiReceiver.flush();
            }
        }

        @Override // android.media.midi.MidiReceiver
        public final void onSend(byte[] bArr, int i, int i2, long j) {
            MidiReceiver midiReceiver = this.mReceiver;
            if (midiReceiver != null) {
                midiReceiver.send(bArr, i, i2, j);
            }
        }
    }

    public UsbAlsaMidiDevice(int i, int i2, int i3, int i4) {
        this.mPowerBoostSetter = null;
        this.mAlsaCard = i;
        this.mAlsaDevice = i2;
        this.mNumInputs = i3;
        this.mNumOutputs = i4;
        this.mMidiInputPortReceivers = new InputReceiverProxy[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            this.mMidiInputPortReceivers[i5] = new InputReceiverProxy();
        }
        this.mPowerBoostSetter = new PowerBoostSetter();
    }

    public static UsbAlsaMidiDevice create(Context context, Bundle bundle, int i, int i2, int i3, int i4) {
        UsbAlsaMidiDevice usbAlsaMidiDevice = new UsbAlsaMidiDevice(i, i2, i3, i4);
        if (usbAlsaMidiDevice.register(context, bundle)) {
            return usbAlsaMidiDevice;
        }
        IoUtils.closeQuietly(usbAlsaMidiDevice);
        Log.e(TAG, "createDeviceServer failed");
        return null;
    }

    private native void nativeClose(FileDescriptor[] fileDescriptorArr);

    private native FileDescriptor[] nativeOpen(int i, int i2, int i3, int i4);

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        synchronized (this.mLock) {
            try {
                if (this.mIsOpen) {
                    closeLocked();
                }
                this.mServerAvailable = false;
            } catch (Throwable th) {
                throw th;
            }
        }
        MidiDeviceServer midiDeviceServer = this.mServer;
        if (midiDeviceServer != null) {
            IoUtils.closeQuietly(midiDeviceServer);
        }
    }

    public final void closeLocked() {
        int i = 0;
        while (true) {
            MidiEventScheduler[] midiEventSchedulerArr = this.mEventSchedulers;
            if (i >= midiEventSchedulerArr.length) {
                break;
            }
            this.mMidiInputPortReceivers[i].mReceiver = null;
            midiEventSchedulerArr[i].close();
            i++;
        }
        this.mEventSchedulers = null;
        int i2 = 0;
        while (true) {
            FileInputStream[] fileInputStreamArr = this.mInputStreams;
            if (i2 >= fileInputStreamArr.length) {
                break;
            }
            IoUtils.closeQuietly(fileInputStreamArr[i2]);
            i2++;
        }
        this.mInputStreams = null;
        int i3 = 0;
        while (true) {
            FileOutputStream[] fileOutputStreamArr = this.mOutputStreams;
            if (i3 >= fileOutputStreamArr.length) {
                this.mOutputStreams = null;
                nativeClose(this.mFileDescriptors);
                this.mFileDescriptors = null;
                this.mIsOpen = false;
                return;
            }
            IoUtils.closeQuietly(fileOutputStreamArr[i3]);
            i3++;
        }
    }

    public final void dump(String str, DualDumpOutputStream dualDumpOutputStream, String str2, long j) {
        long start = dualDumpOutputStream.start(str2, j);
        dualDumpOutputStream.write("device_address", 1138166333443L, str);
        dualDumpOutputStream.write("card", 1120986464257L, this.mAlsaCard);
        dualDumpOutputStream.write("device", 1120986464258L, this.mAlsaDevice);
        dualDumpOutputStream.end(start);
    }

    public final boolean openLocked() {
        int i = this.mNumInputs;
        if (i > 0) {
            i++;
        }
        int i2 = this.mNumOutputs;
        FileDescriptor[] nativeOpen = nativeOpen(this.mAlsaCard, this.mAlsaDevice, i, i2);
        if (nativeOpen == null) {
            Log.e(TAG, "nativeOpen failed");
            return false;
        }
        this.mFileDescriptors = nativeOpen;
        this.mPollFDs = new StructPollfd[i];
        this.mInputStreams = new FileInputStream[i];
        for (int i3 = 0; i3 < i; i3++) {
            FileDescriptor fileDescriptor = nativeOpen[i3];
            StructPollfd structPollfd = new StructPollfd();
            structPollfd.fd = fileDescriptor;
            structPollfd.events = (short) OsConstants.POLLIN;
            this.mPollFDs[i3] = structPollfd;
            this.mInputStreams[i3] = new FileInputStream(fileDescriptor);
        }
        this.mOutputStreams = new FileOutputStream[i2];
        this.mEventSchedulers = new MidiEventScheduler[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            this.mOutputStreams[i4] = new FileOutputStream(nativeOpen[i + i4]);
            MidiEventScheduler midiEventScheduler = new MidiEventScheduler();
            this.mEventSchedulers[i4] = midiEventScheduler;
            this.mMidiInputPortReceivers[i4].mReceiver = midiEventScheduler.getReceiver();
        }
        final MidiReceiver[] outputPortReceivers = this.mServer.getOutputPortReceivers();
        if (i > 0) {
            new Thread() { // from class: com.android.server.usb.UsbAlsaMidiDevice.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super("UsbAlsaMidiDevice input thread");
                }

                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    StructPollfd[] structPollfdArr;
                    byte[] bArr = new byte[512];
                    while (true) {
                        try {
                            long nanoTime = System.nanoTime();
                            synchronized (UsbAlsaMidiDevice.this.mLock) {
                                try {
                                    if (!UsbAlsaMidiDevice.this.mIsOpen) {
                                        break;
                                    }
                                    int i5 = 0;
                                    while (true) {
                                        UsbAlsaMidiDevice usbAlsaMidiDevice = UsbAlsaMidiDevice.this;
                                        structPollfdArr = usbAlsaMidiDevice.mPollFDs;
                                        if (i5 >= structPollfdArr.length) {
                                            break;
                                        }
                                        StructPollfd structPollfd2 = structPollfdArr[i5];
                                        short s = structPollfd2.revents;
                                        if (((OsConstants.POLLERR | OsConstants.POLLHUP) & s) != 0) {
                                            break;
                                        }
                                        if ((s & OsConstants.POLLIN) != 0) {
                                            structPollfd2.revents = (short) 0;
                                            FileInputStream[] fileInputStreamArr = usbAlsaMidiDevice.mInputStreams;
                                            if (i5 == fileInputStreamArr.length - 1) {
                                                break;
                                            }
                                            int read = fileInputStreamArr[i5].read(bArr);
                                            outputPortReceivers[i5].send(bArr, 0, read, nanoTime);
                                            PowerBoostSetter powerBoostSetter = UsbAlsaMidiDevice.this.mPowerBoostSetter;
                                            if (powerBoostSetter != null && read > 1) {
                                                powerBoostSetter.boostPower();
                                            }
                                        }
                                        i5++;
                                    }
                                } finally {
                                }
                            }
                            Os.poll(structPollfdArr, -1);
                        } catch (ErrnoException unused) {
                            Log.d(UsbAlsaMidiDevice.TAG, "reader thread exiting");
                        } catch (IOException unused2) {
                            Log.d(UsbAlsaMidiDevice.TAG, "reader thread exiting");
                        }
                    }
                    Log.d(UsbAlsaMidiDevice.TAG, "input thread exit");
                }
            }.start();
        }
        for (final int i5 = 0; i5 < i2; i5++) {
            final MidiEventScheduler midiEventScheduler2 = this.mEventSchedulers[i5];
            final FileOutputStream fileOutputStream = this.mOutputStreams[i5];
            new Thread(VibrationParam$1$$ExternalSyntheticOutline0.m(i5, "UsbAlsaMidiDevice output thread ")) { // from class: com.android.server.usb.UsbAlsaMidiDevice.3
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    MidiEventScheduler.MidiEvent waitNextEvent;
                    while (true) {
                        try {
                            waitNextEvent = midiEventScheduler2.waitNextEvent();
                        } catch (InterruptedException unused) {
                        }
                        if (waitNextEvent == null) {
                            Log.d(UsbAlsaMidiDevice.TAG, "output thread exit");
                            return;
                        }
                        try {
                            fileOutputStream.write(waitNextEvent.data, 0, waitNextEvent.count);
                        } catch (IOException unused2) {
                            Log.e(UsbAlsaMidiDevice.TAG, "write failed for port " + i5);
                        }
                        midiEventScheduler2.addEventToPool(waitNextEvent);
                    }
                }
            }.start();
        }
        this.mIsOpen = true;
        return true;
    }

    public final boolean register(Context context, Bundle bundle) {
        MidiManager midiManager = (MidiManager) context.getSystemService(MidiManager.class);
        if (midiManager == null) {
            Log.e(TAG, "No MidiManager in UsbAlsaMidiDevice.register()");
            return false;
        }
        this.mServerAvailable = true;
        MidiDeviceServer createDeviceServer = midiManager.createDeviceServer(this.mMidiInputPortReceivers, this.mNumInputs, null, null, bundle, 1, -1, this.mCallback);
        this.mServer = createDeviceServer;
        return createDeviceServer != null;
    }
}
