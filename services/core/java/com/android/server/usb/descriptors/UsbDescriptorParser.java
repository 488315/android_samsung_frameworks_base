package com.android.server.usb.descriptors;

import android.hardware.usb.UsbConfiguration;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.util.Log;

import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.usb.UsbAlsaManager$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Iterator;

public final class UsbDescriptorParser {
    public UsbConfigDescriptor mCurConfigDescriptor;
    public UsbEndpointDescriptor mCurEndpointDescriptor;
    public UsbInterfaceDescriptor mCurInterfaceDescriptor;
    public final String mDeviceAddr;
    public UsbDeviceDescriptor mDeviceDescriptor;
    public int mACInterfacesSpec = 256;
    public final ArrayList mDescriptors = new ArrayList(128);

    class UsbDescriptorsStreamFormatException extends Exception {
        String mMessage;

        public UsbDescriptorsStreamFormatException(String str) {
            this.mMessage = str;
        }

        @Override // java.lang.Throwable
        public final String toString() {
            return "Descriptor Stream Format Exception: " + this.mMessage;
        }
    }

    public UsbDescriptorParser(String str, byte[] bArr) {
        UsbDescriptor usbDescriptor;
        this.mDeviceAddr = str;
        ByteStream byteStream = new ByteStream(bArr);
        while (byteStream.available() > 0) {
            try {
                usbDescriptor = allocDescriptor(byteStream);
            } catch (Exception e) {
                Log.e("UsbDescriptorParser", "Exception allocating USB descriptor.", e);
                usbDescriptor = null;
            }
            if (usbDescriptor != null) {
                try {
                    try {
                        usbDescriptor.parseRawDescriptors(byteStream);
                        usbDescriptor.postParse(byteStream);
                    } catch (Exception e2) {
                        usbDescriptor.postParse(byteStream);
                        Log.w(
                                "UsbDescriptorParser",
                                "Exception parsing USB descriptors. type:0x"
                                        + ((int) usbDescriptor.mType)
                                        + " status:"
                                        + usbDescriptor.mStatus);
                        StackTraceElement[] stackTrace = e2.getStackTrace();
                        if (stackTrace.length > 0) {
                            Log.i(
                                    "UsbDescriptorParser",
                                    "  class:"
                                            + stackTrace[0].getClassName()
                                            + " @ "
                                            + stackTrace[0].getLineNumber());
                        }
                        if (stackTrace.length > 1) {
                            Log.i(
                                    "UsbDescriptorParser",
                                    "  class:"
                                            + stackTrace[1].getClassName()
                                            + " @ "
                                            + stackTrace[1].getLineNumber());
                        }
                        usbDescriptor.mStatus = 4;
                    }
                } finally {
                    this.mDescriptors.add(usbDescriptor);
                }
            }
        }
    }

    public static boolean doesInterfaceContainEndpoint(ArrayList arrayList) {
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            UsbInterfaceDescriptor usbInterfaceDescriptor =
                    (UsbInterfaceDescriptor) arrayList.get(i3);
            for (int i4 = 0; i4 < usbInterfaceDescriptor.mNumEndpoints; i4++) {
                if (usbInterfaceDescriptor.getEndpointDescriptor(i4).getDirection() == 0) {
                    i++;
                } else {
                    i2++;
                }
            }
        }
        return i > 0 || i2 > 0;
    }

    private native String getDescriptorString_native(String str, int i);

    private native byte[] getRawDescriptors_native(String str);

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.usb.descriptors.UsbDescriptor allocDescriptor(
            com.android.server.usb.descriptors.ByteStream r12) {
        /*
            Method dump skipped, instructions count: 758
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.usb.descriptors.UsbDescriptorParser.allocDescriptor(com.android.server.usb.descriptors.ByteStream):com.android.server.usb.descriptors.UsbDescriptor");
    }

    public final int calculateNumLegacyMidiPorts(boolean z) {
        UsbConfigDescriptor usbConfigDescriptor;
        UsbDescriptor usbDescriptor;
        UsbDescriptor usbDescriptor2;
        Iterator it = this.mDescriptors.iterator();
        while (true) {
            if (!it.hasNext()) {
                usbConfigDescriptor = null;
                break;
            }
            UsbDescriptor usbDescriptor3 = (UsbDescriptor) it.next();
            if (usbDescriptor3.mType == 2) {
                if (usbDescriptor3 instanceof UsbConfigDescriptor) {
                    usbConfigDescriptor = (UsbConfigDescriptor) usbDescriptor3;
                    break;
                }
                StringBuilder sb = new StringBuilder("Unrecognized Config l: ");
                sb.append(usbDescriptor3.mLength);
                sb.append(" t:0x");
                UsbAlsaManager$$ExternalSyntheticOutline0.m(
                        usbDescriptor3.mType, sb, "UsbDescriptorParser");
            }
        }
        if (usbConfigDescriptor == null) {
            Log.w("UsbDescriptorParser", "Config not found");
            return 0;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it2 = usbConfigDescriptor.mInterfaceDescriptors.iterator();
        while (it2.hasNext()) {
            UsbInterfaceDescriptor usbInterfaceDescriptor = (UsbInterfaceDescriptor) it2.next();
            if (usbInterfaceDescriptor.mUsbClass == 1
                    && usbInterfaceDescriptor.mUsbSubclass == 3
                    && (usbDescriptor2 = usbInterfaceDescriptor.mMidiHeaderInterfaceDescriptor)
                            != null
                    && (usbDescriptor2 instanceof UsbMSMidiHeader)
                    && ((UsbMSMidiHeader) usbDescriptor2).mMidiStreamingClass == 256) {
                arrayList.add(usbInterfaceDescriptor);
            }
        }
        Iterator it3 = arrayList.iterator();
        int i = 0;
        while (it3.hasNext()) {
            UsbInterfaceDescriptor usbInterfaceDescriptor2 = (UsbInterfaceDescriptor) it3.next();
            for (int i2 = 0; i2 < usbInterfaceDescriptor2.mNumEndpoints; i2++) {
                UsbEndpointDescriptor endpointDescriptor =
                        usbInterfaceDescriptor2.getEndpointDescriptor(i2);
                if ((endpointDescriptor.getDirection() == 0) == z
                        && (usbDescriptor = endpointDescriptor.mClassSpecificEndpointDescriptor)
                                != null
                        && (usbDescriptor instanceof UsbACMidi10Endpoint)) {
                    i += ((UsbACMidi10Endpoint) usbDescriptor).mNumJacks;
                }
            }
        }
        return i;
    }

    public final ArrayList findMidiInterfaceDescriptors(int i) {
        UsbDescriptor usbDescriptor;
        ArrayList interfaceDescriptorsForClass = getInterfaceDescriptorsForClass(1);
        ArrayList arrayList = new ArrayList();
        Iterator it = interfaceDescriptorsForClass.iterator();
        while (it.hasNext()) {
            UsbDescriptor usbDescriptor2 = (UsbDescriptor) it.next();
            if (usbDescriptor2 instanceof UsbInterfaceDescriptor) {
                UsbInterfaceDescriptor usbInterfaceDescriptor =
                        (UsbInterfaceDescriptor) usbDescriptor2;
                if (usbInterfaceDescriptor.mUsbSubclass == 3
                        && (usbDescriptor = usbInterfaceDescriptor.mMidiHeaderInterfaceDescriptor)
                                != null
                        && (usbDescriptor instanceof UsbMSMidiHeader)
                        && ((UsbMSMidiHeader) usbDescriptor).mMidiStreamingClass == i) {
                    arrayList.add(usbInterfaceDescriptor);
                }
            } else {
                StringBuilder sb = new StringBuilder("Undefined Audio Class Interface l: ");
                sb.append(usbDescriptor2.mLength);
                sb.append(" t:0x");
                UsbAlsaManager$$ExternalSyntheticOutline0.m(
                        usbDescriptor2.mType, sb, "UsbDescriptorParser");
            }
        }
        return arrayList;
    }

    public final ArrayList getACInterfaceDescriptors(byte b) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mDescriptors.iterator();
        while (it.hasNext()) {
            UsbDescriptor usbDescriptor = (UsbDescriptor) it.next();
            if (usbDescriptor.mType == 36) {
                if (usbDescriptor instanceof UsbACInterface) {
                    UsbACInterface usbACInterface = (UsbACInterface) usbDescriptor;
                    if (usbACInterface.mSubtype == b && usbACInterface.mSubclass == 1) {
                        arrayList.add(usbDescriptor);
                    }
                } else {
                    StringBuilder sb = new StringBuilder("Unrecognized Audio Interface len: ");
                    sb.append(usbDescriptor.mLength);
                    sb.append(" type:0x");
                    UsbAlsaManager$$ExternalSyntheticOutline0.m(
                            usbDescriptor.mType, sb, "UsbDescriptorParser");
                }
            }
        }
        return arrayList;
    }

    public final String getDescriptorString(int i) {
        return getDescriptorString_native(this.mDeviceAddr, i);
    }

    public final ArrayList getInterfaceDescriptorsForClass(int i) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mDescriptors.iterator();
        while (it.hasNext()) {
            UsbDescriptor usbDescriptor = (UsbDescriptor) it.next();
            if (usbDescriptor.mType == 4) {
                if (!(usbDescriptor instanceof UsbInterfaceDescriptor)) {
                    StringBuilder sb = new StringBuilder("Unrecognized Interface l: ");
                    sb.append(usbDescriptor.mLength);
                    sb.append(" t:0x");
                    UsbAlsaManager$$ExternalSyntheticOutline0.m(
                            usbDescriptor.mType, sb, "UsbDescriptorParser");
                } else if (((UsbInterfaceDescriptor) usbDescriptor).mUsbClass == i) {
                    arrayList.add(usbDescriptor);
                }
            }
        }
        return arrayList;
    }

    public final byte[] getRawDescriptors() {
        return getRawDescriptors_native(this.mDeviceAddr);
    }

    public final boolean hasAudioTerminal(int i) {
        Iterator it = this.mDescriptors.iterator();
        while (it.hasNext()) {
            UsbDescriptor usbDescriptor = (UsbDescriptor) it.next();
            if (usbDescriptor instanceof UsbACTerminal) {
                UsbACTerminal usbACTerminal = (UsbACTerminal) usbDescriptor;
                if (usbACTerminal.mSubclass == 1
                        && usbACTerminal.mSubtype == i
                        && usbACTerminal.mTerminalType == 257) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean hasAudioTerminalExcludeType(int i) {
        Iterator it = this.mDescriptors.iterator();
        while (it.hasNext()) {
            UsbDescriptor usbDescriptor = (UsbDescriptor) it.next();
            if (usbDescriptor instanceof UsbACTerminal) {
                UsbACTerminal usbACTerminal = (UsbACTerminal) usbDescriptor;
                if (usbACTerminal.mSubclass == 1
                        && usbACTerminal.mSubtype == i
                        && usbACTerminal.mTerminalType != 257) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean hasHIDInterface() {
        return !getInterfaceDescriptorsForClass(3).isEmpty();
    }

    public final boolean hasMIDIInterface() {
        Iterator it = getInterfaceDescriptorsForClass(1).iterator();
        while (it.hasNext()) {
            UsbDescriptor usbDescriptor = (UsbDescriptor) it.next();
            if (!(usbDescriptor instanceof UsbInterfaceDescriptor)) {
                StringBuilder sb = new StringBuilder("Undefined Audio Class Interface l: ");
                sb.append(usbDescriptor.mLength);
                sb.append(" t:0x");
                UsbAlsaManager$$ExternalSyntheticOutline0.m(
                        usbDescriptor.mType, sb, "UsbDescriptorParser");
            } else if (((UsbInterfaceDescriptor) usbDescriptor).mUsbSubclass == 3) {
                return true;
            }
        }
        return false;
    }

    public final boolean isDock() {
        if (!hasMIDIInterface() && !hasHIDInterface()) {
            ArrayList aCInterfaceDescriptors = getACInterfaceDescriptors((byte) 3);
            if (aCInterfaceDescriptors.size() != 1) {
                return false;
            }
            if (!(aCInterfaceDescriptors.get(0) instanceof UsbACTerminal)) {
                StringBuilder sb = new StringBuilder("Undefined Audio Output terminal l: ");
                sb.append(((UsbDescriptor) aCInterfaceDescriptors.get(0)).mLength);
                sb.append(" t:0x");
                UsbAlsaManager$$ExternalSyntheticOutline0.m(
                        ((UsbDescriptor) aCInterfaceDescriptors.get(0)).mType,
                        sb,
                        "UsbDescriptorParser");
            } else if (((UsbACTerminal) aCInterfaceDescriptors.get(0)).mTerminalType == 1538) {
                return true;
            }
        }
        return false;
    }

    public final boolean isInputHeadset() {
        boolean z;
        boolean z2;
        boolean hasMIDIInterface = hasMIDIInterface();
        float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
        if (!hasMIDIInterface) {
            Iterator it = getACInterfaceDescriptors((byte) 2).iterator();
            while (it.hasNext()) {
                UsbDescriptor usbDescriptor = (UsbDescriptor) it.next();
                if (usbDescriptor instanceof UsbACTerminal) {
                    int i = ((UsbACTerminal) usbDescriptor).mTerminalType;
                    if (i == 513 || i == 1026 || i == 1024 || i == 1539) {
                        z = true;
                        break;
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
            Iterator it2 = getACInterfaceDescriptors((byte) 3).iterator();
            while (it2.hasNext()) {
                UsbDescriptor usbDescriptor2 = (UsbDescriptor) it2.next();
                if (usbDescriptor2 instanceof UsbACTerminal) {
                    int i2 = ((UsbACTerminal) usbDescriptor2).mTerminalType;
                    if (i2 == 769 || i2 == 770 || i2 == 1026) {
                        z2 = true;
                        break;
                    }
                } else {
                    StringBuilder sb2 = new StringBuilder("Undefined Audio Output terminal l: ");
                    sb2.append(usbDescriptor2.mLength);
                    sb2.append(" t:0x");
                    UsbAlsaManager$$ExternalSyntheticOutline0.m(
                            usbDescriptor2.mType, sb2, "UsbDescriptorParser");
                }
            }
            z2 = false;
            if (z && z2) {
                f = 0.75f;
            }
            if (z && hasHIDInterface()) {
                f += 0.25f;
            }
        }
        return f >= 0.75f;
    }

    public final boolean isOutputHeadset() {
        boolean z;
        boolean hasMIDIInterface = hasMIDIInterface();
        float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
        if (!hasMIDIInterface) {
            Iterator it = getACInterfaceDescriptors((byte) 3).iterator();
            boolean z2 = false;
            boolean z3 = false;
            loop0:
            while (true) {
                z = z3;
                while (it.hasNext()) {
                    UsbDescriptor usbDescriptor = (UsbDescriptor) it.next();
                    if (usbDescriptor instanceof UsbACTerminal) {
                        UsbACTerminal usbACTerminal = (UsbACTerminal) usbDescriptor;
                        int i = usbACTerminal.mTerminalType;
                        if (i == 769) {
                            if (usbACTerminal.mAssocTerminal != 0) {
                                break;
                            }
                            z3 = true;
                        } else if (i == 770 || i == 1026) {
                            z2 = true;
                        }
                    } else {
                        StringBuilder sb = new StringBuilder("Undefined Audio Output terminal l: ");
                        sb.append(usbDescriptor.mLength);
                        sb.append(" t:0x");
                        UsbAlsaManager$$ExternalSyntheticOutline0.m(
                                usbDescriptor.mType, sb, "UsbDescriptorParser");
                    }
                }
                z3 = true;
            }
            if (z2) {
                f = 0.75f;
            } else if (z3) {
                f = z ? 0.75f : 0.5f;
                Iterator it2 = this.mDescriptors.iterator();
                int i2 = 0;
                while (it2.hasNext()) {
                    Object obj = (UsbDescriptor) it2.next();
                    if (obj instanceof UsbAudioChannelCluster) {
                        i2 = Math.max(i2, (int) ((UsbAudioChannelCluster) obj).getChannelCount());
                    }
                }
                if (i2 > 2) {
                    f -= 0.25f;
                }
            }
            if ((z2 || z3) && hasHIDInterface()) {
                f += 0.25f;
            }
        }
        return f >= 0.75f;
    }

    public final UsbDevice.Builder toAndroidUsbDeviceBuilder() {
        boolean z;
        boolean z2;
        UsbDeviceDescriptor usbDeviceDescriptor = this.mDeviceDescriptor;
        if (usbDeviceDescriptor == null) {
            Log.e("UsbDescriptorParser", "toAndroidUsbDevice() ERROR - No Device Descriptor");
            return null;
        }
        byte b = usbDeviceDescriptor.mMfgIndex;
        String str = this.mDeviceAddr;
        String descriptorString_native = getDescriptorString_native(str, b);
        String descriptorString_native2 =
                getDescriptorString_native(str, usbDeviceDescriptor.mProductIndex);
        String deviceReleaseString = usbDeviceDescriptor.getDeviceReleaseString();
        String descriptorString_native3 =
                getDescriptorString_native(str, usbDeviceDescriptor.mSerialIndex);
        int size = usbDeviceDescriptor.mConfigDescriptors.size();
        UsbConfiguration[] usbConfigurationArr = new UsbConfiguration[size];
        NetworkScoreService$$ExternalSyntheticOutline0.m(
                size, "  ", " configs", "UsbDeviceDescriptor");
        for (int i = 0; i < usbDeviceDescriptor.mConfigDescriptors.size(); i++) {
            UsbConfigDescriptor usbConfigDescriptor =
                    (UsbConfigDescriptor) usbDeviceDescriptor.mConfigDescriptors.get(i);
            UsbConfiguration usbConfiguration =
                    new UsbConfiguration(
                            usbConfigDescriptor.mConfigValue,
                            getDescriptorString_native(str, usbConfigDescriptor.mConfigIndex),
                            usbConfigDescriptor.mAttribs,
                            usbConfigDescriptor.mMaxPower);
            ArrayList arrayList = new ArrayList();
            Iterator it = usbConfigDescriptor.mInterfaceDescriptors.iterator();
            while (it.hasNext()) {
                arrayList.add(((UsbInterfaceDescriptor) it.next()).toAndroid(this));
            }
            usbConfiguration.setInterfaces((UsbInterface[]) arrayList.toArray(new UsbInterface[0]));
            usbConfigurationArr[i] = usbConfiguration;
        }
        int i2 = usbDeviceDescriptor.mVendorID;
        int i3 = usbDeviceDescriptor.mProductID;
        int i4 = usbDeviceDescriptor.mDevClass;
        int i5 = usbDeviceDescriptor.mDevSubClass;
        int i6 = usbDeviceDescriptor.mProtocol;
        boolean z3 = hasAudioTerminalExcludeType(3) && hasAudioTerminal(2);
        boolean z4 = hasAudioTerminalExcludeType(2) && hasAudioTerminal(3);
        boolean hasMIDIInterface = hasMIDIInterface();
        Iterator it2 = this.mDescriptors.iterator();
        while (true) {
            if (!it2.hasNext()) {
                z = false;
                break;
            }
            if (((UsbDescriptor) it2.next()) instanceof UsbVCOutputTerminal) {
                z = true;
                break;
            }
        }
        Iterator it3 = this.mDescriptors.iterator();
        while (true) {
            if (!it3.hasNext()) {
                z2 = false;
                break;
            }
            if (((UsbDescriptor) it3.next()) instanceof UsbVCInputTerminal) {
                z2 = true;
                break;
            }
        }
        return new UsbDevice.Builder(
                this.mDeviceAddr,
                i2,
                i3,
                i4,
                i5,
                i6,
                descriptorString_native,
                descriptorString_native2,
                deviceReleaseString,
                usbConfigurationArr,
                descriptorString_native3,
                z3,
                z4,
                hasMIDIInterface,
                z,
                z2);
    }
}
