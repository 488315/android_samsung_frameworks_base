package com.android.server.usb;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.media.AudioManager;
import android.media.IAudioService;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.sysfwutil.Slog;

import com.android.internal.alsa.AlsaCardsParser;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.usb.descriptors.UsbACTerminal;
import com.android.server.usb.descriptors.UsbDescriptor;
import com.android.server.usb.descriptors.UsbDescriptorParser;

import libcore.io.IoUtils;

import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public final class UsbAlsaManager {
    public static final boolean IS_MULTI_MODE =
            SystemProperties.getBoolean("ro.audio.multi_usb_mode", false);
    public static final List sDeviceDenylist =
            Arrays.asList(
                    new DenyListEntry(1476), new DenyListEntry(2508), new DenyListEntry(3302));
    public IAudioService mAudioService;
    public final Context mContext;
    public final boolean mHasMidiFeature;
    public final AlsaCardsParser mCardsParser = new AlsaCardsParser();
    public final ArrayList mAlsaDevices = new ArrayList();
    public final HashMap mAttachedDevices = new HashMap();
    public UsbAlsaDevice mSelectedBundleDevice = null;
    public boolean isBundleRemovedDone = true;
    public final HashMap mMidiDevices = new HashMap();
    public UsbAlsaMidiDevice mPeripheralMidiDevice = null;
    public final HashSet mAlsaCards = new HashSet();
    public final AnonymousClass1 mAlsaObserver =
            new FileObserver(
                    new File("/dev/snd/")) { // from class: com.android.server.usb.UsbAlsaManager.1
                @Override // android.os.FileObserver
                public final void onEvent(int i, String str) {
                    if (i != 256) {
                        if (i != 512) {
                            return;
                        }
                        UsbAlsaManager usbAlsaManager = UsbAlsaManager.this;
                        usbAlsaManager.getClass();
                        int cardNumberFromAlsaFilePath =
                                UsbAlsaManager.getCardNumberFromAlsaFilePath(str);
                        if (cardNumberFromAlsaFilePath == -1) {
                            return;
                        }
                        synchronized (usbAlsaManager.mAlsaCards) {
                            usbAlsaManager.mAlsaCards.remove(
                                    Integer.valueOf(cardNumberFromAlsaFilePath));
                        }
                        return;
                    }
                    UsbAlsaManager usbAlsaManager2 = UsbAlsaManager.this;
                    usbAlsaManager2.getClass();
                    Slog.i("UsbAlsaManager", "alsaFileAdded(" + str + ")");
                    int cardNumberFromAlsaFilePath2 =
                            UsbAlsaManager.getCardNumberFromAlsaFilePath(str);
                    if (cardNumberFromAlsaFilePath2 == -1) {
                        return;
                    }
                    synchronized (usbAlsaManager2.mAlsaCards) {
                        try {
                            if (!usbAlsaManager2.mAlsaCards.contains(
                                    Integer.valueOf(cardNumberFromAlsaFilePath2))) {
                                Slog.d(
                                        "UsbAlsaManager",
                                        "Adding ALSA device card=" + cardNumberFromAlsaFilePath2);
                                usbAlsaManager2.mAlsaCards.add(
                                        Integer.valueOf(cardNumberFromAlsaFilePath2));
                                usbAlsaManager2.mAlsaCards.notifyAll();
                            }
                        } finally {
                        }
                    }
                }
            };

    public final class DenyListEntry {
        public final int mProductId;

        public DenyListEntry(int i) {
            this.mProductId = i;
        }
    }

    public UsbAlsaManager(Context context) {
        this.mContext = context;
        this.mHasMidiFeature =
                context.getPackageManager().hasSystemFeature("android.software.midi");
    }

    public static int getCardNumberFromAlsaFilePath(String str) {
        if (!str.startsWith("pcmC")
                ? str.startsWith("midiC")
                : str.endsWith(KnoxAnalyticsDataConverter.PAYLOAD) || str.endsWith("c")) {
            Slog.i("UsbAlsaManager", "Unknown type file(" + str + ") added.");
            return -1;
        }
        try {
            return Integer.parseInt(str.substring(str.indexOf(67) + 1, str.indexOf(68)));
        } catch (Exception e) {
            Slog.e("UsbAlsaManager", "Could not parse ALSA file name ".concat(str), e);
            return -1;
        }
    }

    public static boolean isDeviceDenylisted(int i, int i2, int i3) {
        for (DenyListEntry denyListEntry : sDeviceDenylist) {
            denyListEntry.getClass();
            if (1356 == i && denyListEntry.mProductId == i2) {
                return (1 & i3) != 0;
            }
        }
        return false;
    }

    public final void addDeviceToAttachedDevicesMap(int i, UsbAlsaDevice usbAlsaDevice) {
        if (i == 0) {
            Slog.i(
                    "UsbAlsaManager",
                    "Ignore caching device as the type is NONE, device=" + usbAlsaDevice);
        } else {
            Stack stack = (Stack) this.mAttachedDevices.get(Integer.valueOf(i));
            if (stack == null) {
                this.mAttachedDevices.put(Integer.valueOf(i), new Stack());
                stack = (Stack) this.mAttachedDevices.get(Integer.valueOf(i));
            }
            stack.push(usbAlsaDevice);
        }
    }

    public final synchronized void deselectAlsaDevice(UsbAlsaDevice usbAlsaDevice) {
        Slog.d("UsbAlsaManager", "deselectAlsaDevice() selectedDevice " + usbAlsaDevice);
        synchronized (usbAlsaDevice) {
            usbAlsaDevice.stopOutput();
            usbAlsaDevice.stopInput();
        }
    }

    public final void deselectCurrentDevice(int i) {
        Stack stack;
        Slog.d("UsbAlsaManager", "deselectCurrentDevice():" + i);
        if (i == 0
                || (stack = (Stack) this.mAttachedDevices.get(Integer.valueOf(i))) == null
                || stack.isEmpty()) {
            return;
        }
        UsbAlsaDevice usbAlsaDevice = (UsbAlsaDevice) stack.peek();
        Slog.d("UsbAlsaManager", "deselect current device:" + usbAlsaDevice);
        if (AudioManager.isInputDevice(i)) {
            usbAlsaDevice.stopInput();
        } else {
            usbAlsaDevice.stopOutput();
        }
    }

    public final void dump(DualDumpOutputStream dualDumpOutputStream) {
        long start = dualDumpOutputStream.start("alsa_manager", 1146756268036L);
        dualDumpOutputStream.write(
                "cards_parser", 1120986464257L, this.mCardsParser.getScanStatus());
        Iterator it = this.mAlsaDevices.iterator();
        while (it.hasNext()) {
            UsbAlsaDevice usbAlsaDevice = (UsbAlsaDevice) it.next();
            synchronized (usbAlsaDevice) {
                long start2 = dualDumpOutputStream.start("alsa_devices", 2246267895810L);
                dualDumpOutputStream.write("card", 1120986464257L, usbAlsaDevice.mCardNum);
                dualDumpOutputStream.write("device", 1120986464258L, 0);
                dualDumpOutputStream.write("name", 1138166333443L, usbAlsaDevice.mDeviceName);
                dualDumpOutputStream.write(
                        "has_output", 1133871366148L, usbAlsaDevice.mHasDevice[1]);
                dualDumpOutputStream.write(
                        "has_input", 1133871366149L, usbAlsaDevice.mHasDevice[0]);
                dualDumpOutputStream.write("address", 1138166333446L, usbAlsaDevice.mDeviceAddress);
                dualDumpOutputStream.end(start2);
            }
        }
        for (String str : this.mMidiDevices.keySet()) {
            ((UsbAlsaMidiDevice) this.mMidiDevices.get(str))
                    .dump(str, dualDumpOutputStream, "alsa_midi_devices", 2246267895812L);
        }
        dualDumpOutputStream.end(start);
    }

    public final void logDevices(String str) {
        String str2;
        Slog.i("UsbAlsaManager", str.concat("----------------"));
        Iterator it = this.mAlsaDevices.iterator();
        while (it.hasNext()) {
            UsbAlsaDevice usbAlsaDevice = (UsbAlsaDevice) it.next();
            synchronized (usbAlsaDevice) {
                str2 =
                        "[card:"
                                + usbAlsaDevice.mCardNum
                                + " device:0 "
                                + usbAlsaDevice.mDeviceName
                                + "]";
            }
            Slog.i("UsbAlsaManager", str2);
        }
        Slog.i("UsbAlsaManager", "----------------");
    }

    public final UsbAlsaDevice removeAlsaDevice(String str) {
        int i = 0;
        while (true) {
            if (i >= this.mAlsaDevices.size()) {
                i = -1;
                break;
            }
            if (((UsbAlsaDevice) this.mAlsaDevices.get(i)).mDeviceAddress.equals(str)) {
                break;
            }
            i++;
        }
        if (i <= -1) {
            return null;
        }
        UsbAlsaDevice usbAlsaDevice = (UsbAlsaDevice) this.mAlsaDevices.remove(i);
        removeDeviceFromAttachedDevicesMap(usbAlsaDevice.mDeviceType[1], usbAlsaDevice);
        removeDeviceFromAttachedDevicesMap(usbAlsaDevice.mDeviceType[0], usbAlsaDevice);
        return usbAlsaDevice;
    }

    public final void removeDeviceFromAttachedDevicesMap(int i, UsbAlsaDevice usbAlsaDevice) {
        Stack stack = (Stack) this.mAttachedDevices.get(Integer.valueOf(i));
        if (stack == null) {
            return;
        }
        stack.remove(usbAlsaDevice);
        if (stack.isEmpty()) {
            this.mAttachedDevices.remove(Integer.valueOf(i));
        }
    }

    public final synchronized void selectAlsaDevice(UsbAlsaDevice usbAlsaDevice) {
        Slog.d("UsbAlsaManager", "selectAlsaDevice() " + usbAlsaDevice);
        if (Settings.Secure.getInt(
                        this.mContext.getContentResolver(),
                        "usb_audio_automatic_routing_disabled",
                        0)
                != 0) {
            Slog.d("UsbAlsaManager", "USB_AUDIO_AUTOMATIC_ROUTING_DISABLED");
            return;
        }
        synchronized (usbAlsaDevice) {
            try {
                synchronized (usbAlsaDevice) {
                    usbAlsaDevice.startDevice(1);
                }
                Slog.d("UsbAlsaManager", "selectAlsaDevice() - done.");
            } catch (Throwable th) {
                throw th;
            }
        }
        synchronized (usbAlsaDevice) {
            usbAlsaDevice.startDevice(0);
        }
        Slog.d("UsbAlsaManager", "selectAlsaDevice() - done.");
    }

    public final void selectDefaultDevice(int i) {
        Slog.d("UsbAlsaManager", "selectDefaultDevice():" + i);
        Stack stack = (Stack) this.mAttachedDevices.get(Integer.valueOf(i));
        if (stack == null || stack.isEmpty()) {
            return;
        }
        UsbAlsaDevice usbAlsaDevice = (UsbAlsaDevice) stack.peek();
        Slog.d("UsbAlsaManager", "select default device:" + usbAlsaDevice);
        if (AudioManager.isInputDevice(i)) {
            synchronized (usbAlsaDevice) {
                usbAlsaDevice.startDevice(0);
            }
        } else {
            synchronized (usbAlsaDevice) {
                usbAlsaDevice.startDevice(1);
            }
        }
    }

    public final void usbDeviceAdded(
            String str, UsbDevice usbDevice, UsbDescriptorParser usbDescriptorParser) {
        boolean z;
        boolean z2;
        Slog.d(
                "UsbAlsaManager",
                "usbDeviceAdded(): "
                        + usbDevice.getManufacturerName()
                        + " nm:"
                        + usbDevice.getProductName());
        this.mCardsParser.scan();
        AlsaCardsParser.AlsaCardRecord findCardNumFor = this.mCardsParser.findCardNumFor(str);
        if (findCardNumFor == null) {
            Slog.d("UsbAlsaManager", "no AlsaCardRecord");
            if (!usbDescriptorParser.getInterfaceDescriptorsForClass(1).isEmpty()) {
                Slog.e("UsbAlsaManager", "usbDeviceAdded(): cannot find sound card for " + str);
                return;
            }
            return;
        }
        waitForAlsaDevice(findCardNumFor.getCardNum(), true);
        Iterator it = usbDescriptorParser.getACInterfaceDescriptors((byte) 2).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            UsbDescriptor usbDescriptor = (UsbDescriptor) it.next();
            if (usbDescriptor instanceof UsbACTerminal) {
                int i = ((UsbACTerminal) usbDescriptor).mTerminalType & (-256);
                if (i != 256 && i != 768) {
                    if (!isDeviceDenylisted(usbDevice.getVendorId(), usbDevice.getProductId(), 2)) {
                        z = true;
                    }
                }
            } else {
                StringBuilder sb = new StringBuilder("Undefined Audio Input terminal l: ");
                sb.append(usbDescriptor.mLength);
                sb.append(" t:0x");
                UsbAlsaManager$$ExternalSyntheticOutline0.m(
                        usbDescriptor.mType, sb, "UsbDescriptorParser");
            }
        }
        z = false;
        Iterator it2 = usbDescriptorParser.getACInterfaceDescriptors((byte) 3).iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            UsbDescriptor usbDescriptor2 = (UsbDescriptor) it2.next();
            if (usbDescriptor2 instanceof UsbACTerminal) {
                int i2 = ((UsbACTerminal) usbDescriptor2).mTerminalType & (-256);
                if (i2 != 256 && i2 != 512) {
                    if (!isDeviceDenylisted(usbDevice.getVendorId(), usbDevice.getProductId(), 1)) {
                        z2 = true;
                    }
                }
            } else {
                StringBuilder sb2 = new StringBuilder("Undefined Audio Input terminal l: ");
                sb2.append(usbDescriptor2.mLength);
                sb2.append(" t:0x");
                UsbAlsaManager$$ExternalSyntheticOutline0.m(
                        usbDescriptor2.mType, sb2, "UsbDescriptorParser");
            }
        }
        z2 = false;
        Slog.d("UsbAlsaManager", "hasInput: " + z + " hasOutput:" + z2);
        if (z || z2) {
            boolean isInputHeadset = usbDescriptorParser.isInputHeadset();
            boolean isOutputHeadset = usbDescriptorParser.isOutputHeadset();
            boolean isDock = usbDescriptorParser.isDock();
            IAudioService iAudioService = this.mAudioService;
            if (iAudioService == null) {
                Slog.e("UsbAlsaManager", "no AudioService");
                return;
            }
            UsbAlsaDevice usbAlsaDevice =
                    new UsbAlsaDevice(
                            iAudioService,
                            findCardNumFor.getCardNum(),
                            str,
                            z2,
                            z,
                            isInputHeadset,
                            isOutputHeadset,
                            isDock);
            String cardName = findCardNumFor.getCardName();
            findCardNumFor.getCardDescription();
            synchronized (usbAlsaDevice) {
                usbAlsaDevice.mDeviceName = cardName;
            }
            boolean z3 = IS_MULTI_MODE;
            int[] iArr = usbAlsaDevice.mDeviceType;
            if (z3) {
                deselectCurrentDevice(iArr[0]);
                deselectCurrentDevice(iArr[1]);
            } else if (!this.mAlsaDevices.isEmpty()) {
                deselectAlsaDevice((UsbAlsaDevice) this.mAlsaDevices.get(0));
            }
            this.mAlsaDevices.add(0, usbAlsaDevice);
            addDeviceToAttachedDevicesMap(iArr[0], usbAlsaDevice);
            addDeviceToAttachedDevicesMap(iArr[1], usbAlsaDevice);
            selectAlsaDevice(usbAlsaDevice);
        }
        boolean hasMIDIInterface = usbDescriptorParser.hasMIDIInterface();
        boolean doesInterfaceContainEndpoint =
                UsbDescriptorParser.doesInterfaceContainEndpoint(
                        usbDescriptorParser.findMidiInterfaceDescriptors(512));
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        "hasMidi: ", " mHasMidiFeature:", hasMIDIInterface);
        boolean z4 = this.mHasMidiFeature;
        m.append(z4);
        Slog.d("UsbAlsaManager", m.toString());
        Slog.d("UsbAlsaManager", "hasMidi2: " + doesInterfaceContainEndpoint);
        if (z4 && hasMIDIInterface && !doesInterfaceContainEndpoint) {
            Bundle bundle = new Bundle();
            String manufacturerName = usbDevice.getManufacturerName();
            String productName = usbDevice.getProductName();
            String version = usbDevice.getVersion();
            bundle.putString(
                    "name",
                    (manufacturerName == null || manufacturerName.isEmpty())
                            ? productName
                            : (productName == null || productName.isEmpty())
                                    ? manufacturerName
                                    : AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                            manufacturerName, " ", productName));
            bundle.putString("manufacturer", manufacturerName);
            bundle.putString("product", productName);
            bundle.putString("version", version);
            bundle.putString("serial_number", usbDevice.getSerialNumber());
            bundle.putInt("alsa_card", findCardNumFor.getCardNum());
            bundle.putInt("alsa_device", 0);
            bundle.putParcelable("usb_device", usbDevice);
            int calculateNumLegacyMidiPorts =
                    usbDescriptorParser.calculateNumLegacyMidiPorts(false);
            int calculateNumLegacyMidiPorts2 =
                    usbDescriptorParser.calculateNumLegacyMidiPorts(true);
            Slog.d("UsbAlsaManager", "numLegacyMidiInputs: " + calculateNumLegacyMidiPorts);
            Slog.d("UsbAlsaManager", "numLegacyMidiOutputs:" + calculateNumLegacyMidiPorts2);
            UsbAlsaMidiDevice create =
                    UsbAlsaMidiDevice.create(
                            this.mContext,
                            bundle,
                            findCardNumFor.getCardNum(),
                            0,
                            calculateNumLegacyMidiPorts,
                            calculateNumLegacyMidiPorts2);
            if (create != null) {
                this.mMidiDevices.put(str, create);
            }
        }
        logDevices("deviceAdded()");
        Slog.d("UsbAlsaManager", "deviceAdded() - done");
    }

    public final synchronized void usbDeviceRemoved(String str) {
        try {
            Slog.d("UsbAlsaManager", "deviceRemoved(" + str + ")");
            UsbAlsaDevice removeAlsaDevice = removeAlsaDevice(str);
            Slog.i("UsbAlsaManager", "USB Audio Device Removed: " + removeAlsaDevice);
            if (this.mSelectedBundleDevice != null && this.isBundleRemovedDone) {
                Slog.d(
                        "UsbAlsaManager",
                        "usbDeviceRemoved set mSelectedBundleDevice to null, as it's not set even"
                            + " Bundle Remove was done");
                this.mSelectedBundleDevice = null;
                this.isBundleRemovedDone = false;
            }
            if (removeAlsaDevice != null) {
                waitForAlsaDevice(removeAlsaDevice.mCardNum, false);
                deselectAlsaDevice(removeAlsaDevice);
                if (IS_MULTI_MODE) {
                    selectDefaultDevice(removeAlsaDevice.mDeviceType[1]);
                    selectDefaultDevice(removeAlsaDevice.mDeviceType[0]);
                } else if (!this.mAlsaDevices.isEmpty() && this.mAlsaDevices.get(0) != null) {
                    selectAlsaDevice((UsbAlsaDevice) this.mAlsaDevices.get(0));
                }
            }
            UsbAlsaMidiDevice usbAlsaMidiDevice = (UsbAlsaMidiDevice) this.mMidiDevices.remove(str);
            if (usbAlsaMidiDevice != null) {
                Slog.i("UsbAlsaManager", "USB MIDI Device Removed: " + str);
                IoUtils.closeQuietly(usbAlsaMidiDevice);
            }
            logDevices("usbDeviceRemoved()");
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void waitForAlsaDevice(int i, boolean z) {
        Slog.e("UsbAlsaManager", "waitForAlsaDevice(c:" + i + ")");
        synchronized (this.mAlsaCards) {
            long elapsedRealtime = SystemClock.elapsedRealtime() + 2500;
            while ((this.mAlsaCards.contains(Integer.valueOf(i)) ^ z)
                    && elapsedRealtime > SystemClock.elapsedRealtime()) {
                long elapsedRealtime2 = elapsedRealtime - SystemClock.elapsedRealtime();
                if (elapsedRealtime2 > 0) {
                    try {
                        this.mAlsaCards.wait(elapsedRealtime2);
                    } catch (InterruptedException unused) {
                        Slog.d(
                                "UsbAlsaManager",
                                "usb: InterruptedException while waiting for ALSA file.");
                    }
                }
            }
            boolean contains = this.mAlsaCards.contains(Integer.valueOf(i));
            if (!(z ^ contains) || elapsedRealtime <= SystemClock.elapsedRealtime()) {
                Slog.i(
                        "UsbAlsaManager",
                        "waitForAlsaDevice for device card="
                                + i
                                + ", isAdded="
                                + z
                                + ", found="
                                + contains);
            } else {
                Slog.e("UsbAlsaManager", "waitForAlsaDevice(" + i + ") timeout");
            }
        }
    }
}
