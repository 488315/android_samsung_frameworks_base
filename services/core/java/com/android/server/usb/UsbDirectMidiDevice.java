package com.android.server.usb;

import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.media.midi.MidiDeviceServer;
import android.media.midi.MidiManager;
import android.media.midi.MidiReceiver;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Bundle;
import android.util.Log;
import com.android.internal.midi.MidiEventMultiScheduler;
import com.android.internal.midi.MidiEventScheduler;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.usb.UsbMidiPacketConverter;
import com.android.server.usb.descriptors.ByteStream;
import com.android.server.usb.descriptors.UsbACMidi10Endpoint;
import com.android.server.usb.descriptors.UsbDescriptor;
import com.android.server.usb.descriptors.UsbDescriptorParser;
import com.android.server.usb.descriptors.UsbEndpointDescriptor;
import com.android.server.usb.descriptors.UsbInterfaceDescriptor;
import com.android.server.usb.descriptors.UsbMidiBlockParser;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import libcore.io.IoUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbDirectMidiDevice implements Closeable {
    public final AnonymousClass1 mCallback;
    public Context mContext;
    public int mDefaultMidiProtocol;
    public ArrayList mInputUsbEndpointCableCounts;
    public ArrayList mInputUsbEndpoints;
    public boolean mIsOpen;
    public final boolean mIsUniversalMidiDevice;
    public final Object mLock;
    public final UsbMidiBlockParser mMidiBlockParser;
    public ArrayList mMidiEventMultiSchedulers;
    public final InputReceiverProxy[] mMidiInputPortReceivers;
    public String mName;
    public final int mNumInputs;
    public final int mNumOutputs;
    public ArrayList mOutputUsbEndpointCableCounts;
    public ArrayList mOutputUsbEndpoints;
    public final UsbDescriptorParser mParser;
    public final PowerBoostSetter mPowerBoostSetter;
    public MidiDeviceServer mServer;
    public boolean mServerAvailable;
    public final boolean mShouldCallSetInterface;
    public ArrayList mThreads;
    public final String mUniqueUsbDeviceIdentifier;
    public final UsbDevice mUsbDevice;
    public ArrayList mUsbDeviceConnections;
    public final ArrayList mUsbInterfaces;

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

    /* renamed from: -$$Nest$mopenLocked, reason: not valid java name */
    public static void m1016$$Nest$mopenLocked(UsbDirectMidiDevice usbDirectMidiDevice) {
        usbDirectMidiDevice.getClass();
        Log.d("UsbDirectMidiDevice", "openLocked()");
        UsbManager usbManager = (UsbManager) usbDirectMidiDevice.mContext.getSystemService(UsbManager.class);
        usbDirectMidiDevice.mUsbDeviceConnections = new ArrayList();
        usbDirectMidiDevice.mInputUsbEndpoints = new ArrayList();
        usbDirectMidiDevice.mOutputUsbEndpoints = new ArrayList();
        usbDirectMidiDevice.mInputUsbEndpointCableCounts = new ArrayList();
        usbDirectMidiDevice.mOutputUsbEndpointCableCounts = new ArrayList();
        usbDirectMidiDevice.mMidiEventMultiSchedulers = new ArrayList();
        usbDirectMidiDevice.mThreads = new ArrayList();
        for (int i = 0; i < usbDirectMidiDevice.mUsbInterfaces.size(); i++) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            ArrayList arrayList5 = new ArrayList();
            UsbInterfaceDescriptor usbInterfaceDescriptor = (UsbInterfaceDescriptor) usbDirectMidiDevice.mUsbInterfaces.get(i);
            for (int i2 = 0; i2 < usbInterfaceDescriptor.mNumEndpoints; i2++) {
                UsbEndpointDescriptor endpointDescriptor = usbInterfaceDescriptor.getEndpointDescriptor(i2);
                if (endpointDescriptor != null) {
                    if (endpointDescriptor.getDirection() == 0) {
                        arrayList2.add(new UsbEndpoint(endpointDescriptor.mEndpointAddress, endpointDescriptor.mAttributes, endpointDescriptor.mPacketSize, endpointDescriptor.mInterval));
                        arrayList4.add(Integer.valueOf(getNumJacks(endpointDescriptor)));
                        arrayList5.add(new MidiEventMultiScheduler(getNumJacks(endpointDescriptor)));
                    } else {
                        arrayList.add(new UsbEndpoint(endpointDescriptor.mEndpointAddress, endpointDescriptor.mAttributes, endpointDescriptor.mPacketSize, endpointDescriptor.mInterval));
                        arrayList3.add(Integer.valueOf(getNumJacks(endpointDescriptor)));
                    }
                }
            }
            if (!arrayList2.isEmpty() || !arrayList.isEmpty()) {
                UsbDeviceConnection openDevice = usbManager.openDevice(usbDirectMidiDevice.mUsbDevice);
                if (usbDirectMidiDevice.updateUsbInterface(usbInterfaceDescriptor.toAndroid(usbDirectMidiDevice.mParser), openDevice)) {
                    usbDirectMidiDevice.mUsbDeviceConnections.add(openDevice);
                    usbDirectMidiDevice.mInputUsbEndpoints.add(arrayList);
                    usbDirectMidiDevice.mOutputUsbEndpoints.add(arrayList2);
                    usbDirectMidiDevice.mInputUsbEndpointCableCounts.add(arrayList3);
                    usbDirectMidiDevice.mOutputUsbEndpointCableCounts.add(arrayList4);
                    usbDirectMidiDevice.mMidiEventMultiSchedulers.add(arrayList5);
                }
            }
        }
        int i3 = 0;
        for (int i4 = 0; i4 < usbDirectMidiDevice.mMidiEventMultiSchedulers.size(); i4++) {
            for (int i5 = 0; i5 < ((ArrayList) usbDirectMidiDevice.mMidiEventMultiSchedulers.get(i4)).size(); i5++) {
                int intValue = ((Integer) ((ArrayList) usbDirectMidiDevice.mOutputUsbEndpointCableCounts.get(i4)).get(i5)).intValue();
                MidiEventMultiScheduler midiEventMultiScheduler = (MidiEventMultiScheduler) ((ArrayList) usbDirectMidiDevice.mMidiEventMultiSchedulers.get(i4)).get(i5);
                for (int i6 = 0; i6 < intValue; i6++) {
                    MidiEventScheduler eventScheduler = midiEventMultiScheduler.getEventScheduler(i6);
                    usbDirectMidiDevice.mMidiInputPortReceivers[i3].mReceiver = eventScheduler.getReceiver();
                    i3++;
                }
            }
        }
        final MidiReceiver[] outputPortReceivers = usbDirectMidiDevice.mServer.getOutputPortReceivers();
        int i7 = 0;
        int i8 = 0;
        while (i8 < usbDirectMidiDevice.mInputUsbEndpoints.size()) {
            int i9 = i7;
            for (int i10 = 0; i10 < ((ArrayList) usbDirectMidiDevice.mInputUsbEndpoints.get(i8)).size(); i10++) {
                final UsbDeviceConnection usbDeviceConnection = (UsbDeviceConnection) usbDirectMidiDevice.mUsbDeviceConnections.get(i8);
                final UsbEndpoint usbEndpoint = (UsbEndpoint) ((ArrayList) usbDirectMidiDevice.mInputUsbEndpoints.get(i8)).get(i10);
                final int intValue2 = ((Integer) ((ArrayList) usbDirectMidiDevice.mInputUsbEndpointCableCounts.get(i8)).get(i10)).intValue();
                final int i11 = i9;
                Thread thread = new Thread(VibrationParam$1$$ExternalSyntheticOutline0.m(i9, "UsbDirectMidiDevice input thread ")) { // from class: com.android.server.usb.UsbDirectMidiDevice.2
                    @Override // java.lang.Thread, java.lang.Runnable
                    public final void run() {
                        int i12;
                        MidiReceiver midiReceiver;
                        int i13;
                        byte b;
                        UsbRequest usbRequest = new UsbRequest();
                        UsbMidiPacketConverter usbMidiPacketConverter = new UsbMidiPacketConverter();
                        int i14 = intValue2;
                        UsbMidiPacketConverter.UsbMidiDecoder usbMidiDecoder = new UsbMidiPacketConverter.UsbMidiDecoder();
                        usbMidiDecoder.mNumJacks = i14;
                        usbMidiDecoder.mDecodedByteArrays = new ByteArrayOutputStream[i14];
                        for (int i15 = 0; i15 < i14; i15++) {
                            usbMidiDecoder.mDecodedByteArrays[i15] = new ByteArrayOutputStream();
                        }
                        usbMidiPacketConverter.mUsbMidiDecoder = usbMidiDecoder;
                        try {
                            try {
                                usbRequest.initialize(usbDeviceConnection, usbEndpoint);
                                byte[] bArr = new byte[usbEndpoint.getMaxPacketSize()];
                                boolean z = true;
                                while (true) {
                                    if (!z) {
                                        break;
                                    }
                                    Thread.currentThread();
                                    if (Thread.interrupted()) {
                                        Log.w("UsbDirectMidiDevice", "input thread interrupted");
                                        break;
                                    }
                                    ByteBuffer wrap = ByteBuffer.wrap(bArr);
                                    if (!usbRequest.queue(wrap)) {
                                        Log.w("UsbDirectMidiDevice", "Cannot queue request");
                                        break;
                                    }
                                    UsbRequest requestWait = usbDeviceConnection.requestWait();
                                    if (requestWait == null) {
                                        Log.w("UsbDirectMidiDevice", "Response is null");
                                        break;
                                    }
                                    if (usbRequest != requestWait) {
                                        Log.w("UsbDirectMidiDevice", "Skipping response");
                                    } else {
                                        long nanoTime = System.nanoTime();
                                        int position = wrap.position();
                                        if (position > 0) {
                                            UsbDirectMidiDevice.m1018$$Nest$smlogByteArray(position, "Input before conversion ", bArr);
                                            if (!UsbDirectMidiDevice.this.mIsUniversalMidiDevice) {
                                                usbMidiPacketConverter.decodeMidiPackets(position, bArr);
                                            }
                                            while (i12 < intValue2) {
                                                UsbDirectMidiDevice usbDirectMidiDevice2 = UsbDirectMidiDevice.this;
                                                byte[] m1017$$Nest$mswapEndiannessPerWord = usbDirectMidiDevice2.mIsUniversalMidiDevice ? UsbDirectMidiDevice.m1017$$Nest$mswapEndiannessPerWord(usbDirectMidiDevice2, bArr, position) : usbMidiPacketConverter.pullDecodedMidiPackets(i12);
                                                UsbDirectMidiDevice.m1018$$Nest$smlogByteArray(m1017$$Nest$mswapEndiannessPerWord.length, "Input " + i12 + " after conversion ", m1017$$Nest$mswapEndiannessPerWord);
                                                if (m1017$$Nest$mswapEndiannessPerWord.length == 0) {
                                                    i13 = i12;
                                                } else {
                                                    MidiReceiver[] midiReceiverArr = outputPortReceivers;
                                                    if (midiReceiverArr == null || (midiReceiver = midiReceiverArr[i11 + i12]) == null) {
                                                        Log.w("UsbDirectMidiDevice", "outputReceivers is null");
                                                        z = false;
                                                        break;
                                                    }
                                                    i13 = i12;
                                                    midiReceiver.send(m1017$$Nest$mswapEndiannessPerWord, 0, m1017$$Nest$mswapEndiannessPerWord.length, nanoTime);
                                                    UsbDirectMidiDevice usbDirectMidiDevice3 = UsbDirectMidiDevice.this;
                                                    PowerBoostSetter powerBoostSetter = usbDirectMidiDevice3.mPowerBoostSetter;
                                                    if (powerBoostSetter != null && m1017$$Nest$mswapEndiannessPerWord.length > 1) {
                                                        i12 = (!usbDirectMidiDevice3.mIsUniversalMidiDevice || (b = (byte) ((m1017$$Nest$mswapEndiannessPerWord[0] >> 4) & 15)) == 2 || b == 4) ? 0 : i13 + 1;
                                                        powerBoostSetter.boostPower();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (IOException unused) {
                                Log.d("UsbDirectMidiDevice", "reader thread exiting");
                            } catch (NullPointerException e) {
                                Log.e("UsbDirectMidiDevice", "input thread: ", e);
                            }
                            usbRequest.close();
                            Log.d("UsbDirectMidiDevice", "input thread exit");
                        } catch (Throwable th) {
                            usbRequest.close();
                            throw th;
                        }
                    }
                };
                thread.start();
                usbDirectMidiDevice.mThreads.add(thread);
                i9 += intValue2;
            }
            i8++;
            i7 = i9;
        }
        int i12 = 0;
        int i13 = 0;
        while (i13 < usbDirectMidiDevice.mOutputUsbEndpoints.size()) {
            int i14 = i12;
            for (int i15 = 0; i15 < ((ArrayList) usbDirectMidiDevice.mOutputUsbEndpoints.get(i13)).size(); i15++) {
                final UsbDeviceConnection usbDeviceConnection2 = (UsbDeviceConnection) usbDirectMidiDevice.mUsbDeviceConnections.get(i13);
                final UsbEndpoint usbEndpoint2 = (UsbEndpoint) ((ArrayList) usbDirectMidiDevice.mOutputUsbEndpoints.get(i13)).get(i15);
                final int intValue3 = ((Integer) ((ArrayList) usbDirectMidiDevice.mOutputUsbEndpointCableCounts.get(i13)).get(i15)).intValue();
                final MidiEventMultiScheduler midiEventMultiScheduler2 = (MidiEventMultiScheduler) ((ArrayList) usbDirectMidiDevice.mMidiEventMultiSchedulers.get(i13)).get(i15);
                Thread thread2 = new Thread(VibrationParam$1$$ExternalSyntheticOutline0.m(i14, "UsbDirectMidiDevice output write thread ")) { // from class: com.android.server.usb.UsbDirectMidiDevice.3
                    @Override // java.lang.Thread, java.lang.Runnable
                    public final void run() {
                        byte[] byteArray;
                        try {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            UsbMidiPacketConverter usbMidiPacketConverter = new UsbMidiPacketConverter();
                            usbMidiPacketConverter.createEncoders(intValue3);
                            int i16 = 0;
                            boolean z = false;
                            while (true) {
                                if (z) {
                                    break;
                                }
                                if (!midiEventMultiScheduler2.waitNextEvent()) {
                                    Log.d("UsbDirectMidiDevice", "output thread closed");
                                    break;
                                }
                                long nanoTime = System.nanoTime();
                                for (int i17 = i16; i17 < intValue3; i17++) {
                                    MidiEventScheduler eventScheduler2 = midiEventMultiScheduler2.getEventScheduler(i17);
                                    for (MidiEventScheduler.MidiEvent nextEvent = eventScheduler2.getNextEvent(nanoTime); nextEvent != null; nextEvent = (MidiEventScheduler.MidiEvent) eventScheduler2.getNextEvent(nanoTime)) {
                                        UsbDirectMidiDevice.m1018$$Nest$smlogByteArray(nextEvent.count, "Output before conversion ", nextEvent.data);
                                        UsbDirectMidiDevice usbDirectMidiDevice2 = UsbDirectMidiDevice.this;
                                        if (usbDirectMidiDevice2.mIsUniversalMidiDevice) {
                                            byte[] m1017$$Nest$mswapEndiannessPerWord = UsbDirectMidiDevice.m1017$$Nest$mswapEndiannessPerWord(usbDirectMidiDevice2, nextEvent.data, nextEvent.count);
                                            byteArrayOutputStream.write(m1017$$Nest$mswapEndiannessPerWord, i16, m1017$$Nest$mswapEndiannessPerWord.length);
                                        } else {
                                            usbMidiPacketConverter.encodeMidiPackets(nextEvent.count, i17, nextEvent.data);
                                        }
                                        eventScheduler2.addEventToPool(nextEvent);
                                    }
                                }
                                Thread.currentThread();
                                if (Thread.interrupted()) {
                                    Log.d("UsbDirectMidiDevice", "output thread interrupted");
                                    break;
                                }
                                if (UsbDirectMidiDevice.this.mIsUniversalMidiDevice) {
                                    byteArray = byteArrayOutputStream.toByteArray();
                                    byteArrayOutputStream.reset();
                                } else {
                                    byteArray = usbMidiPacketConverter.mEncoderOutputStream.toByteArray();
                                    usbMidiPacketConverter.mEncoderOutputStream.reset();
                                }
                                UsbDirectMidiDevice.m1018$$Nest$smlogByteArray(byteArray.length, "Output after conversion ", byteArray);
                                int i18 = i16;
                                while (i18 < byteArray.length && !z) {
                                    int min = Math.min(usbEndpoint2.getMaxPacketSize(), byteArray.length - i18);
                                    int i19 = -1;
                                    int i20 = i16;
                                    while (true) {
                                        if (i19 >= 0 || i20 > 20) {
                                            break;
                                        }
                                        i19 = usbDeviceConnection2.bulkTransfer(usbEndpoint2, byteArray, i18, min, 50);
                                        i20++;
                                        Thread.currentThread();
                                        if (Thread.interrupted()) {
                                            Log.w("UsbDirectMidiDevice", "output thread interrupted after send");
                                            z = true;
                                            break;
                                        } else if (i19 < 0) {
                                            Log.d("UsbDirectMidiDevice", "retrying packet. retryCount = " + i20 + " result = " + i19);
                                            if (i20 > 20) {
                                                Log.w("UsbDirectMidiDevice", "Skipping packet because timeout");
                                            }
                                        }
                                    }
                                    i18 += usbEndpoint2.getMaxPacketSize();
                                    i16 = 0;
                                }
                                i16 = 0;
                            }
                        } catch (InterruptedException e) {
                            Log.w("UsbDirectMidiDevice", "output thread: ", e);
                        } catch (NullPointerException e2) {
                            Log.e("UsbDirectMidiDevice", "output thread: ", e2);
                        }
                        Log.d("UsbDirectMidiDevice", "output thread exit");
                    }
                };
                thread2.start();
                usbDirectMidiDevice.mThreads.add(thread2);
                i14 += intValue3;
            }
            i13++;
            i12 = i14;
        }
        usbDirectMidiDevice.mIsOpen = true;
    }

    /* renamed from: -$$Nest$mswapEndiannessPerWord, reason: not valid java name */
    public static byte[] m1017$$Nest$mswapEndiannessPerWord(UsbDirectMidiDevice usbDirectMidiDevice, byte[] bArr, int i) {
        usbDirectMidiDevice.getClass();
        int i2 = i & 3;
        if (i2 != 0) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "size not multiple of 4: ", "UsbDirectMidiDevice");
        }
        byte[] bArr2 = new byte[i - i2];
        int i3 = 0;
        while (true) {
            int i4 = i3 + 3;
            if (i4 >= i) {
                return bArr2;
            }
            bArr2[i3] = bArr[i4];
            int i5 = i3 + 1;
            int i6 = i3 + 2;
            bArr2[i5] = bArr[i6];
            bArr2[i6] = bArr[i5];
            bArr2[i4] = bArr[i3];
            i3 += 4;
        }
    }

    /* renamed from: -$$Nest$smlogByteArray, reason: not valid java name */
    public static void m1018$$Nest$smlogByteArray(int i, String str, byte[] bArr) {
        StringBuilder sb = new StringBuilder(str);
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(String.format("0x%02X", Byte.valueOf(bArr[i2])));
            if (i2 != bArr.length - 1) {
                sb.append(", ");
            }
        }
        Log.d("UsbDirectMidiDevice", sb.toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x012c  */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.server.usb.UsbDirectMidiDevice$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public UsbDirectMidiDevice(android.hardware.usb.UsbDevice r17, com.android.server.usb.descriptors.UsbDescriptorParser r18, boolean r19, java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 497
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.usb.UsbDirectMidiDevice.<init>(android.hardware.usb.UsbDevice, com.android.server.usb.descriptors.UsbDescriptorParser, boolean, java.lang.String):void");
    }

    public static UsbDirectMidiDevice create(Context context, UsbDevice usbDevice, UsbDescriptorParser usbDescriptorParser, boolean z, String str) {
        int i;
        int controlTransfer;
        UsbDirectMidiDevice usbDirectMidiDevice = new UsbDirectMidiDevice(usbDevice, usbDescriptorParser, z, str);
        usbDirectMidiDevice.mContext = context;
        MidiManager midiManager = (MidiManager) context.getSystemService(MidiManager.class);
        if (midiManager == null) {
            Log.e("UsbDirectMidiDevice", "No MidiManager in UsbDirectMidiDevice.register()");
        } else {
            if (usbDirectMidiDevice.mIsUniversalMidiDevice) {
                UsbManager usbManager = (UsbManager) usbDirectMidiDevice.mContext.getSystemService(UsbManager.class);
                int i2 = 0;
                while (true) {
                    if (i2 >= usbDirectMidiDevice.mUsbInterfaces.size()) {
                        Log.w("UsbDirectMidiDevice", "Cannot find interface with both input and output endpoints");
                        i = 1;
                        break;
                    }
                    UsbInterfaceDescriptor usbInterfaceDescriptor = (UsbInterfaceDescriptor) usbDirectMidiDevice.mUsbInterfaces.get(i2);
                    boolean z2 = false;
                    boolean z3 = false;
                    for (int i3 = 0; i3 < usbInterfaceDescriptor.mNumEndpoints && (!z2 || !z3); i3++) {
                        UsbEndpointDescriptor endpointDescriptor = usbInterfaceDescriptor.getEndpointDescriptor(i3);
                        if (endpointDescriptor != null) {
                            if (endpointDescriptor.getDirection() == 0) {
                                z3 = true;
                            } else {
                                z2 = true;
                            }
                        }
                    }
                    if (z2 && z3) {
                        UsbDeviceConnection openDevice = usbManager.openDevice(usbDirectMidiDevice.mUsbDevice);
                        if (usbDirectMidiDevice.updateUsbInterface(usbInterfaceDescriptor.toAndroid(usbDirectMidiDevice.mParser), openDevice)) {
                            UsbMidiBlockParser usbMidiBlockParser = usbDirectMidiDevice.mMidiBlockParser;
                            int i4 = usbInterfaceDescriptor.mInterfaceNumber;
                            byte b = usbInterfaceDescriptor.mAlternateSetting;
                            usbMidiBlockParser.getClass();
                            byte[] bArr = new byte[5];
                            int i5 = b + 9728;
                            try {
                                controlTransfer = openDevice.controlTransfer(129, 6, i5, i4, bArr, 5, 2000);
                            } catch (Exception e) {
                                Log.e("UsbMidiBlockParser", "Can not communicate with USB device", e);
                            }
                            if (controlTransfer <= 0) {
                                Log.e("UsbMidiBlockParser", "first transfer failed: " + controlTransfer);
                            } else if (bArr[1] != 38) {
                                Log.e("UsbMidiBlockParser", "Incorrect descriptor type: " + ((int) bArr[1]));
                            } else if (bArr[2] != 1) {
                                Log.e("UsbMidiBlockParser", "Incorrect descriptor subtype: " + ((int) bArr[2]));
                            } else {
                                int i6 = ((bArr[4] & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT) << 8) + (bArr[3] & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                                if (i6 <= 0) {
                                    Log.e("UsbMidiBlockParser", "Parsed a non-positive block terminal size: " + i6);
                                } else {
                                    byte[] bArr2 = new byte[i6];
                                    int controlTransfer2 = openDevice.controlTransfer(129, 6, i5, i4, bArr2, i6, 2000);
                                    if (controlTransfer2 > 0) {
                                        usbMidiBlockParser.parseRawDescriptors(new ByteStream(bArr2));
                                        if (usbMidiBlockParser.mGroupTerminalBlocks.isEmpty()) {
                                            Log.e("UsbMidiBlockParser", "Group Terminal Blocks failed parsing: 1");
                                        } else {
                                            Log.d("UsbMidiBlockParser", "MIDI protocol: " + ((UsbMidiBlockParser.GroupTerminalBlock) usbMidiBlockParser.mGroupTerminalBlocks.get(0)).mMidiProtocol);
                                            i = ((UsbMidiBlockParser.GroupTerminalBlock) usbMidiBlockParser.mGroupTerminalBlocks.get(0)).mMidiProtocol;
                                            openDevice.close();
                                        }
                                    } else {
                                        Log.e("UsbMidiBlockParser", "second transfer failed: " + controlTransfer2);
                                    }
                                }
                            }
                            i = 1;
                            openDevice.close();
                        }
                    }
                    i2++;
                }
                usbDirectMidiDevice.mDefaultMidiProtocol = i;
            } else {
                usbDirectMidiDevice.mDefaultMidiProtocol = -1;
            }
            Bundle bundle = new Bundle();
            String manufacturerName = usbDirectMidiDevice.mUsbDevice.getManufacturerName();
            String productName = usbDirectMidiDevice.mUsbDevice.getProductName();
            String version = usbDirectMidiDevice.mUsbDevice.getVersion();
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m((manufacturerName == null || manufacturerName.isEmpty()) ? productName : (productName == null || productName.isEmpty()) ? manufacturerName : AnyMotionDetector$$ExternalSyntheticOutline0.m(manufacturerName, " ", productName), "#");
            m.append(usbDirectMidiDevice.mUniqueUsbDeviceIdentifier);
            String sb = m.toString();
            String m$1 = usbDirectMidiDevice.mIsUniversalMidiDevice ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(sb, " MIDI 2.0") : ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(sb, " MIDI 1.0");
            usbDirectMidiDevice.mName = m$1;
            bundle.putString("name", m$1);
            bundle.putString("manufacturer", manufacturerName);
            bundle.putString("product", productName);
            bundle.putString("version", version);
            bundle.putString("serial_number", usbDirectMidiDevice.mUsbDevice.getSerialNumber());
            bundle.putParcelable("usb_device", usbDirectMidiDevice.mUsbDevice);
            usbDirectMidiDevice.mServerAvailable = true;
            MidiDeviceServer createDeviceServer = midiManager.createDeviceServer(usbDirectMidiDevice.mMidiInputPortReceivers, usbDirectMidiDevice.mNumInputs, null, null, bundle, 1, usbDirectMidiDevice.mDefaultMidiProtocol, usbDirectMidiDevice.mCallback);
            usbDirectMidiDevice.mServer = createDeviceServer;
            if (createDeviceServer != null) {
                return usbDirectMidiDevice;
            }
        }
        IoUtils.closeQuietly(usbDirectMidiDevice);
        Log.e("UsbDirectMidiDevice", "createDeviceServer failed");
        return null;
    }

    public static int getNumJacks(UsbEndpointDescriptor usbEndpointDescriptor) {
        UsbDescriptor usbDescriptor = usbEndpointDescriptor.mClassSpecificEndpointDescriptor;
        if (usbDescriptor == null || !(usbDescriptor instanceof UsbACMidi10Endpoint)) {
            return 1;
        }
        return ((UsbACMidi10Endpoint) usbDescriptor).mNumJacks;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
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
        Log.d("UsbDirectMidiDevice", "closeLocked()");
        Iterator it = this.mThreads.iterator();
        while (it.hasNext()) {
            Thread thread = (Thread) it.next();
            if (thread != null) {
                thread.interrupt();
            }
        }
        Iterator it2 = this.mThreads.iterator();
        while (it2.hasNext()) {
            Thread thread2 = (Thread) it2.next();
            if (thread2 != null) {
                try {
                    thread2.join(200L);
                } catch (InterruptedException unused) {
                    Log.w("UsbDirectMidiDevice", "thread join interrupted");
                }
            }
        }
        this.mThreads = null;
        int i = 0;
        while (true) {
            InputReceiverProxy[] inputReceiverProxyArr = this.mMidiInputPortReceivers;
            if (i >= inputReceiverProxyArr.length) {
                break;
            }
            inputReceiverProxyArr[i].mReceiver = null;
            i++;
        }
        for (int i2 = 0; i2 < this.mMidiEventMultiSchedulers.size(); i2++) {
            for (int i3 = 0; i3 < ((ArrayList) this.mMidiEventMultiSchedulers.get(i2)).size(); i3++) {
                ((MidiEventMultiScheduler) ((ArrayList) this.mMidiEventMultiSchedulers.get(i2)).get(i3)).close();
            }
        }
        this.mMidiEventMultiSchedulers = null;
        Iterator it3 = this.mUsbDeviceConnections.iterator();
        while (it3.hasNext()) {
            ((UsbDeviceConnection) it3.next()).close();
        }
        this.mUsbDeviceConnections = null;
        this.mInputUsbEndpoints = null;
        this.mOutputUsbEndpoints = null;
        this.mInputUsbEndpointCableCounts = null;
        this.mOutputUsbEndpointCableCounts = null;
        this.mIsOpen = false;
    }

    public final void dump(DualDumpOutputStream dualDumpOutputStream) {
        long j = 2246267895813L;
        long start = dualDumpOutputStream.start("midi_devices", 2246267895813L);
        dualDumpOutputStream.write("num_inputs", 1120986464257L, this.mNumInputs);
        dualDumpOutputStream.write("num_outputs", 1120986464258L, this.mNumOutputs);
        dualDumpOutputStream.write("is_universal", 1133871366147L, this.mIsUniversalMidiDevice);
        dualDumpOutputStream.write("name", 1138166333444L, this.mName);
        if (this.mIsUniversalMidiDevice) {
            UsbMidiBlockParser usbMidiBlockParser = this.mMidiBlockParser;
            usbMidiBlockParser.getClass();
            long start2 = dualDumpOutputStream.start("block_parser", 1146756268037L);
            dualDumpOutputStream.write("length", 1120986464257L, usbMidiBlockParser.mHeaderLength);
            dualDumpOutputStream.write("descriptor_type", 1120986464258L, usbMidiBlockParser.mHeaderDescriptorType);
            dualDumpOutputStream.write("descriptor_subtype", 1120986464259L, usbMidiBlockParser.mHeaderDescriptorSubtype);
            dualDumpOutputStream.write("total_length", 1120986464260L, usbMidiBlockParser.mTotalLength);
            Iterator it = usbMidiBlockParser.mGroupTerminalBlocks.iterator();
            while (it.hasNext()) {
                UsbMidiBlockParser.GroupTerminalBlock groupTerminalBlock = (UsbMidiBlockParser.GroupTerminalBlock) it.next();
                groupTerminalBlock.getClass();
                long start3 = dualDumpOutputStream.start("block", j);
                dualDumpOutputStream.write("length", 1120986464257L, groupTerminalBlock.mLength);
                dualDumpOutputStream.write("descriptor_type", 1120986464258L, groupTerminalBlock.mDescriptorType);
                dualDumpOutputStream.write("descriptor_subtype", 1120986464259L, groupTerminalBlock.mDescriptorSubtype);
                dualDumpOutputStream.write("group_block_id", 1120986464260L, groupTerminalBlock.mGroupBlockId);
                dualDumpOutputStream.write("group_terminal_block_type", 1120986464261L, groupTerminalBlock.mGroupTerminalBlockType);
                dualDumpOutputStream.write("group_terminal", 1120986464262L, groupTerminalBlock.mGroupTerminal);
                dualDumpOutputStream.write("num_group_terminals", 1120986464263L, groupTerminalBlock.mNumGroupTerminals);
                dualDumpOutputStream.write("block_item", 1120986464264L, groupTerminalBlock.mBlockItem);
                dualDumpOutputStream.write("midi_protocol", 1120986464265L, groupTerminalBlock.mMidiProtocol);
                dualDumpOutputStream.write("max_input_bandwidth", 1120986464266L, groupTerminalBlock.mMaxInputBandwidth);
                dualDumpOutputStream.write("max_output_bandwidth", 1120986464267L, groupTerminalBlock.mMaxOutputBandwidth);
                dualDumpOutputStream.end(start3);
                j = 2246267895813L;
            }
            dualDumpOutputStream.end(start2);
        }
        dualDumpOutputStream.end(start);
    }

    public final boolean updateUsbInterface(UsbInterface usbInterface, UsbDeviceConnection usbDeviceConnection) {
        if (usbDeviceConnection == null) {
            Log.e("UsbDirectMidiDevice", "UsbDeviceConnection is null");
            return false;
        }
        if (!usbDeviceConnection.claimInterface(usbInterface, true)) {
            Log.e("UsbDirectMidiDevice", "Can't claim interface");
            return false;
        }
        if (!this.mShouldCallSetInterface) {
            Log.w("UsbDirectMidiDevice", "no alternate interface");
        } else if (!usbDeviceConnection.setInterface(usbInterface)) {
            Log.w("UsbDirectMidiDevice", "Can't set interface");
        }
        return true;
    }
}
