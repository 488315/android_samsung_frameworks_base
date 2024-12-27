package com.android.server.display;

import android.bluetooth.BluetoothAdapter;
import android.hardware.display.BrightnessConfiguration;
import android.hardware.display.WifiDisplay;
import android.os.Handler;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseLongArray;
import android.util.TimeUtils;
import android.util.Xml;

import com.android.internal.os.BackgroundThread;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;
import com.android.server.chimera.AggressivePolicyHandler$$ExternalSyntheticOutline0;

import libcore.io.IoUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public final class PersistentDataStore {
    public float mBrightnessNitsForDefaultDisplay;
    public boolean mDirty;
    public final HashMap mDisplayStates;
    public final Object mFileAccessLock;
    public final BrightnessConfigurations mGlobalBrightnessConfigurations;
    public final Handler mHandler;
    public String mInitiatedMirroringUuid;
    public final Injector mInjector;
    public boolean mIsFitToActiveDisplay;
    public boolean mLoaded;
    public final ArrayList mRememberedInitiatedDevices;
    public final ArrayList mRememberedWifiDisplays;
    public final StableDeviceValues mStableDeviceValues;

    public final class BrightnessConfigurations {
        public final SparseArray mConfigurations = new SparseArray();
        public final SparseLongArray mTimeStamps = new SparseLongArray();
        public final SparseArray mPackageNames = new SparseArray();
        public final SparseArray mSetConfigurations = new SparseArray();
        public final SparseLongArray mSetTimeStamps = new SparseLongArray();
        public final SparseArray mSetPackageNames = new SparseArray();

        /* renamed from: -$$Nest$msetBrightnessConfigurationForUser, reason: not valid java name */
        public static boolean m463$$Nest$msetBrightnessConfigurationForUser(
                BrightnessConfigurations brightnessConfigurations,
                BrightnessConfiguration brightnessConfiguration,
                int i,
                String str) {
            BrightnessConfiguration brightnessConfiguration2 =
                    (BrightnessConfiguration) brightnessConfigurations.mConfigurations.get(i);
            if (brightnessConfiguration != null) {
                if (str == null) {
                    brightnessConfigurations.mSetPackageNames.remove(i);
                } else {
                    brightnessConfigurations.mSetPackageNames.put(i, str);
                }
                brightnessConfigurations.mSetTimeStamps.put(i, System.currentTimeMillis());
                if (brightnessConfiguration2 == null
                        || !brightnessConfiguration2.equals(brightnessConfiguration)) {
                    brightnessConfigurations.mSetConfigurations.put(i, brightnessConfiguration);
                } else {
                    brightnessConfigurations.mSetConfigurations.put(i, null);
                }
            }
            if (brightnessConfiguration2 == brightnessConfiguration
                    || (brightnessConfiguration2 != null
                            && brightnessConfiguration2.equals(brightnessConfiguration))) {
                return false;
            }
            if (brightnessConfiguration != null) {
                if (str == null) {
                    brightnessConfigurations.mPackageNames.remove(i);
                } else {
                    brightnessConfigurations.mPackageNames.put(i, str);
                }
                brightnessConfigurations.mTimeStamps.put(i, System.currentTimeMillis());
                brightnessConfigurations.mConfigurations.put(i, brightnessConfiguration);
            } else {
                brightnessConfigurations.mPackageNames.remove(i);
                brightnessConfigurations.mTimeStamps.delete(i);
                brightnessConfigurations.mConfigurations.remove(i);
            }
            return true;
        }

        public static void fileWriteInt(int i) {
            FileOutputStream fileOutputStream;
            HermesService$3$$ExternalSyntheticOutline0.m(
                    i,
                    "fileWriteInt : /data/log/BC_Position  value : ",
                    "DisplayManager.PersistentDataStore");
            FileOutputStream fileOutputStream2 = null;
            try {
                fileOutputStream = new FileOutputStream(new File("/data/log/BC_Position"));
            } catch (FileNotFoundException unused) {
                return;
            } catch (IOException e) {
                e = e;
            }
            try {
                fileOutputStream.write(Integer.toString(i).getBytes(StandardCharsets.UTF_8));
                fileOutputStream.close();
            } catch (IOException e2) {
                e = e2;
                fileOutputStream2 = fileOutputStream;
                e.printStackTrace();
                try {
                    fileOutputStream2.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }

        public final void dump(PrintWriter printWriter) {
            for (int i = 0; i < this.mConfigurations.size(); i++) {
                int keyAt = this.mConfigurations.keyAt(i);
                long j = this.mTimeStamps.get(keyAt, -1L);
                String str = (String) this.mPackageNames.get(keyAt);
                ActiveServices$$ExternalSyntheticOutline0.m(keyAt, printWriter, "      User ", ":");
                if (j != -1) {
                    printWriter.println("        set at: " + TimeUtils.formatForLogging(j));
                }
                if (str != null) {
                    printWriter.println("        set by: ".concat(str));
                }
                printWriter.println("        " + this.mConfigurations.valueAt(i));
            }
            try {
                printWriter.println("  \n\n dumpSetBrightnessConfiguration_1");
                File file = new File("/data/log/Brightness_Configurations_1");
                if (file.exists()) {
                    Scanner scanner = new Scanner(file, "UTF-8");
                    while (scanner.hasNextLine()) {
                        printWriter.println(scanner.nextLine());
                    }
                    scanner.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                printWriter.println("  \n\n dumpSetBrightnessConfiguration_2");
                File file2 = new File("/data/log/Brightness_Configurations_2");
                if (file2.exists()) {
                    Scanner scanner2 = new Scanner(file2, "UTF-8");
                    while (scanner2.hasNextLine()) {
                        printWriter.println(scanner2.nextLine());
                    }
                    scanner2.close();
                }
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
        }

        public final void loadFromXml(TypedXmlPullParser typedXmlPullParser) {
            int i;
            int depth = typedXmlPullParser.getDepth();
            while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                if ("brightness-configuration".equals(typedXmlPullParser.getName())) {
                    try {
                        i = typedXmlPullParser.getAttributeInt((String) null, "user-serial");
                    } catch (NumberFormatException e) {
                        Slog.e(
                                "DisplayManager.PersistentDataStore",
                                "Failed to read in brightness configuration",
                                e);
                        i = -1;
                    }
                    String attributeValue =
                            typedXmlPullParser.getAttributeValue((String) null, "package-name");
                    long attributeLong =
                            typedXmlPullParser.getAttributeLong((String) null, "timestamp", -1L);
                    try {
                        BrightnessConfiguration loadFromXml =
                                BrightnessConfiguration.loadFromXml(typedXmlPullParser);
                        if (i >= 0 && loadFromXml != null) {
                            this.mConfigurations.put(i, loadFromXml);
                            if (attributeLong != -1) {
                                this.mTimeStamps.put(i, attributeLong);
                            }
                            if (attributeValue != null) {
                                this.mPackageNames.put(i, attributeValue);
                            }
                        }
                    } catch (IllegalArgumentException e2) {
                        Slog.e(
                                "DisplayManager.PersistentDataStore",
                                "Failed to load brightness configuration!",
                                e2);
                    }
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:105:0x008e, code lost:

           r0 = e;
        */
        /* JADX WARN: Code restructure failed: missing block: B:106:0x008f, code lost:

           r3 = null;
        */
        /* JADX WARN: Removed duplicated region for block: B:21:0x00fb A[LOOP:0: B:21:0x00fb->B:30:0x029b, LOOP_START, PHI: r6
        0x00fb: PHI (r6v1 int) = (r6v0 int), (r6v2 int) binds: [B:20:0x00f9, B:30:0x029b] A[DONT_GENERATE, DONT_INLINE]] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void saveHistory(java.util.List r16, java.util.List r17, java.util.List r18) {
            /*
                Method dump skipped, instructions count: 694
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.display.PersistentDataStore.BrightnessConfigurations.saveHistory(java.util.List,"
                        + " java.util.List, java.util.List):void");
        }

        public final void saveToXml(TypedXmlSerializer typedXmlSerializer) {
            for (int i = 0; i < this.mConfigurations.size(); i++) {
                int keyAt = this.mConfigurations.keyAt(i);
                BrightnessConfiguration brightnessConfiguration =
                        (BrightnessConfiguration) this.mConfigurations.valueAt(i);
                typedXmlSerializer.startTag((String) null, "brightness-configuration");
                typedXmlSerializer.attributeInt((String) null, "user-serial", keyAt);
                String str = (String) this.mPackageNames.get(keyAt);
                if (str != null) {
                    typedXmlSerializer.attribute((String) null, "package-name", str);
                }
                long j = this.mTimeStamps.get(keyAt, -1L);
                if (j != -1) {
                    typedXmlSerializer.attributeLong((String) null, "timestamp", j);
                }
                brightnessConfiguration.saveToXml(typedXmlSerializer);
                typedXmlSerializer.endTag((String) null, "brightness-configuration");
            }
        }
    }

    public final class DisplayState {
        public int mColorMode;
        public int mHeight;
        public float mRefreshRate;
        public int mWidth;
        public final SparseArray mPerUserBrightness = new SparseArray();
        public final BrightnessConfigurations mDisplayBrightnessConfigurations =
                new BrightnessConfigurations();

        public final float getBrightness(int i) {
            SparseArray sparseArray = this.mPerUserBrightness;
            Float valueOf = Float.valueOf(Float.NaN);
            float floatValue = ((Float) sparseArray.get(i, valueOf)).floatValue();
            return Float.isNaN(floatValue)
                    ? ((Float) this.mPerUserBrightness.get(-1, valueOf)).floatValue()
                    : floatValue;
        }
    }

    class Injector {
        public final AtomicFile mAtomicFile =
                new AtomicFile(new File("/data/system/display-manager-state.xml"), "display-state");

        public final void finishWrite(OutputStream outputStream) {
            this.mAtomicFile.finishWrite((FileOutputStream) outputStream);
        }
    }

    public final class StableDeviceValues {
        public int mHeight;
        public int mWidth;
    }

    public PersistentDataStore(Injector injector) {
        this(injector, new Handler(BackgroundThread.getHandler().getLooper()));
    }

    public PersistentDataStore(Injector injector, Handler handler) {
        this.mIsFitToActiveDisplay = false;
        this.mRememberedInitiatedDevices = new ArrayList();
        this.mRememberedWifiDisplays = new ArrayList();
        this.mDisplayStates = new HashMap();
        this.mBrightnessNitsForDefaultDisplay = -1.0f;
        this.mStableDeviceValues = new StableDeviceValues();
        this.mGlobalBrightnessConfigurations = new BrightnessConfigurations();
        this.mFileAccessLock = new Object();
        this.mInjector = injector;
        this.mHandler = handler;
    }

    public final WifiDisplay applyWifiDisplayAlias(WifiDisplay wifiDisplay) {
        if (wifiDisplay != null) {
            loadIfNeeded();
            int findRememberedWifiDisplay =
                    findRememberedWifiDisplay(wifiDisplay.getDeviceAddress());
            String deviceAlias =
                    findRememberedWifiDisplay >= 0
                            ? ((WifiDisplay)
                                            this.mRememberedWifiDisplays.get(
                                                    findRememberedWifiDisplay))
                                    .getDeviceAlias()
                            : null;
            if (!Objects.equals(wifiDisplay.getDeviceAlias(), deviceAlias)) {
                return new WifiDisplay(
                        wifiDisplay.getDeviceAddress(),
                        wifiDisplay.getDeviceName(),
                        deviceAlias,
                        wifiDisplay.isAvailable(),
                        wifiDisplay.canConnect(),
                        wifiDisplay.isRemembered(),
                        wifiDisplay.getPrimaryDeviceType());
            }
        }
        return wifiDisplay;
    }

    public final WifiDisplay[] applyWifiDisplayAliases(WifiDisplay[] wifiDisplayArr) {
        if (wifiDisplayArr == null) {
            return wifiDisplayArr;
        }
        int length = wifiDisplayArr.length;
        WifiDisplay[] wifiDisplayArr2 = wifiDisplayArr;
        for (int i = 0; i < length; i++) {
            WifiDisplay applyWifiDisplayAlias = applyWifiDisplayAlias(wifiDisplayArr[i]);
            if (applyWifiDisplayAlias != wifiDisplayArr[i]) {
                if (wifiDisplayArr2 == wifiDisplayArr) {
                    wifiDisplayArr2 = new WifiDisplay[length];
                    System.arraycopy(wifiDisplayArr, 0, wifiDisplayArr2, 0, length);
                }
                wifiDisplayArr2[i] = applyWifiDisplayAlias;
            }
        }
        return wifiDisplayArr2;
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                printWriter, "PersistentDataStore", "  mLoaded="),
                        this.mLoaded,
                        printWriter,
                        "  mDirty=");
        m.append(this.mDirty);
        printWriter.println(m.toString());
        printWriter.println("  RememberedWifiDisplays:");
        Iterator it = this.mRememberedWifiDisplays.iterator();
        int i = 0;
        while (it.hasNext()) {
            printWriter.println("    " + i + ": " + ((WifiDisplay) it.next()));
            i++;
        }
        printWriter.println("  DisplayStates:");
        int i2 = 0;
        for (Map.Entry entry : this.mDisplayStates.entrySet()) {
            StringBuilder sb = new StringBuilder("    ");
            int i3 = i2 + 1;
            sb.append(i2);
            sb.append(": ");
            sb.append((String) entry.getKey());
            printWriter.println(sb.toString());
            DisplayState displayState = (DisplayState) entry.getValue();
            displayState.getClass();
            printWriter.println("      ColorMode=" + displayState.mColorMode);
            printWriter.println("      BrightnessValues: ");
            for (int i4 = 0; i4 < displayState.mPerUserBrightness.size(); i4++) {
                printWriter.println(
                        "User: "
                                + displayState.mPerUserBrightness.keyAt(i4)
                                + " Value: "
                                + displayState.mPerUserBrightness.valueAt(i4));
            }
            printWriter.println("      DisplayBrightnessConfigurations: ");
            displayState.mDisplayBrightnessConfigurations.dump(printWriter);
            StringBuilder sb2 = new StringBuilder("      Resolution=");
            sb2.append(displayState.mWidth);
            sb2.append(" ");
            AggressivePolicyHandler$$ExternalSyntheticOutline0.m(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            sb2, displayState.mHeight, printWriter, "      RefreshRate="),
                    displayState.mRefreshRate,
                    printWriter);
            i2 = i3;
        }
        printWriter.println("  StableDeviceValues:");
        StableDeviceValues stableDeviceValues = this.mStableDeviceValues;
        stableDeviceValues.getClass();
        StringBuilder m2 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("      StableDisplayWidth="),
                        stableDeviceValues.mWidth,
                        printWriter,
                        "      StableDisplayHeight=");
        m2.append(stableDeviceValues.mHeight);
        printWriter.println(m2.toString());
        printWriter.println("  GlobalBrightnessConfigurations:");
        this.mGlobalBrightnessConfigurations.dump(printWriter);
        AggressivePolicyHandler$$ExternalSyntheticOutline0.m(
                new StringBuilder("  mBrightnessNitsForDefaultDisplay="),
                this.mBrightnessNitsForDefaultDisplay,
                printWriter);
    }

    public final int findRememberedWifiDisplay(String str) {
        int size = this.mRememberedWifiDisplays.size();
        for (int i = 0; i < size; i++) {
            if (((WifiDisplay) this.mRememberedWifiDisplays.get(i))
                    .getDeviceAddress()
                    .equals(str)) {
                return i;
            }
        }
        return -1;
    }

    public final DisplayState getDisplayState(String str, boolean z) {
        loadIfNeeded();
        DisplayState displayState = (DisplayState) this.mDisplayStates.get(str);
        if (displayState != null || !z) {
            return displayState;
        }
        DisplayState displayState2 = new DisplayState();
        this.mDisplayStates.put(str, displayState2);
        this.mDirty = true;
        return displayState2;
    }

    public final void loadFromXml(TypedXmlPullParser typedXmlPullParser) {
        int i;
        int i2;
        char c;
        int i3;
        XmlUtils.beginDocument(typedXmlPullParser, "display-manager-state");
        int depth = typedXmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals("remembered-wifi-displays")) {
                int depth2 = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                    if (typedXmlPullParser.getName().equals("wifi-display")) {
                        String attributeValue =
                                typedXmlPullParser.getAttributeValue(
                                        (String) null, "deviceAddress");
                        String attributeValue2 =
                                typedXmlPullParser.getAttributeValue((String) null, "deviceName");
                        String attributeValue3 =
                                typedXmlPullParser.getAttributeValue((String) null, "deviceAlias");
                        String attributeValue4 =
                                typedXmlPullParser.getAttributeValue((String) null, "deviceType");
                        if (attributeValue == null || attributeValue2 == null) {
                            throw new XmlPullParserException(
                                    "Missing deviceAddress or deviceName attribute on"
                                        + " wifi-display.");
                        }
                        if (findRememberedWifiDisplay(attributeValue) >= 0) {
                            throw new XmlPullParserException(
                                    "Found duplicate wifi display device address.");
                        }
                        this.mRememberedWifiDisplays.add(
                                new WifiDisplay(
                                        attributeValue,
                                        attributeValue2,
                                        attributeValue3,
                                        false,
                                        false,
                                        false,
                                        attributeValue4));
                    }
                }
            }
            if (typedXmlPullParser.getName().equals("display-states")) {
                int depth3 = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth3)) {
                    if (typedXmlPullParser.getName().equals("display")) {
                        String attributeValue5 =
                                typedXmlPullParser.getAttributeValue((String) null, "unique-id");
                        if (attributeValue5 == null) {
                            throw new XmlPullParserException(
                                    "Missing unique-id attribute on display.");
                        }
                        if (this.mDisplayStates.containsKey(attributeValue5)) {
                            throw new XmlPullParserException("Found duplicate display.");
                        }
                        DisplayState displayState = new DisplayState();
                        int depth4 = typedXmlPullParser.getDepth();
                        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth4)) {
                            String name = typedXmlPullParser.getName();
                            name.getClass();
                            switch (name.hashCode()) {
                                case -1377859227:
                                    if (name.equals("resolution-width")) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1321967815:
                                    if (name.equals("brightness-configurations")) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -945778443:
                                    if (name.equals("brightness-value")) {
                                        c = 2;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -196957848:
                                    if (name.equals("resolution-height")) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -92443502:
                                    if (name.equals("refresh-rate")) {
                                        c = 4;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1243304397:
                                    if (name.equals("color-mode")) {
                                        c = 5;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                default:
                                    c = 65535;
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    displayState.mWidth =
                                            Integer.parseInt(typedXmlPullParser.nextText());
                                    break;
                                case 1:
                                    displayState.mDisplayBrightnessConfigurations.loadFromXml(
                                            typedXmlPullParser);
                                    break;
                                case 2:
                                    try {
                                        i3 =
                                                typedXmlPullParser.getAttributeInt(
                                                        (String) null, "user-serial");
                                    } catch (NumberFormatException | XmlPullParserException e) {
                                        Slog.e(
                                                "DisplayManager.PersistentDataStore",
                                                "Failed to read user serial",
                                                e);
                                        i3 = -1;
                                    }
                                    try {
                                        displayState.mPerUserBrightness.set(
                                                i3,
                                                Float.valueOf(
                                                        Float.parseFloat(
                                                                typedXmlPullParser.nextText())));
                                        break;
                                    } catch (NumberFormatException e2) {
                                        Slog.e(
                                                "DisplayManager.PersistentDataStore",
                                                "Failed to read brightness",
                                                e2);
                                        break;
                                    }
                                case 3:
                                    displayState.mHeight =
                                            Integer.parseInt(typedXmlPullParser.nextText());
                                    break;
                                case 4:
                                    displayState.mRefreshRate =
                                            Float.parseFloat(typedXmlPullParser.nextText());
                                    break;
                                case 5:
                                    displayState.mColorMode =
                                            Integer.parseInt(typedXmlPullParser.nextText());
                                    break;
                            }
                        }
                        this.mDisplayStates.put(attributeValue5, displayState);
                    }
                }
            }
            if (typedXmlPullParser.getName().equals("stable-device-values")) {
                StableDeviceValues stableDeviceValues = this.mStableDeviceValues;
                stableDeviceValues.getClass();
                int depth5 = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth5)) {
                    String name2 = typedXmlPullParser.getName();
                    name2.getClass();
                    if (name2.equals("stable-display-height")) {
                        try {
                            i2 = Integer.parseInt(typedXmlPullParser.nextText());
                        } catch (NumberFormatException unused) {
                            i2 = 0;
                        }
                        stableDeviceValues.mHeight = i2;
                    } else if (name2.equals("stable-display-width")) {
                        try {
                            i = Integer.parseInt(typedXmlPullParser.nextText());
                        } catch (NumberFormatException unused2) {
                            i = 0;
                        }
                        stableDeviceValues.mWidth = i;
                    }
                }
            }
            if (typedXmlPullParser.getName().equals("brightness-configurations")) {
                this.mGlobalBrightnessConfigurations.loadFromXml(typedXmlPullParser);
            }
            if (typedXmlPullParser.getName().equals("brightness-nits-for-default-display")) {
                this.mBrightnessNitsForDefaultDisplay =
                        Float.parseFloat(typedXmlPullParser.nextText());
            }
            if (typedXmlPullParser.getName().equals("remembered-active-display-fit-status")) {
                String attributeValue6 =
                        typedXmlPullParser.getAttributeValue(
                                (String) null, "activeDisplayFitStatus");
                Slog.d(
                        "DisplayManager.PersistentDataStore",
                        "loadRememberedActiveDisplayFitStatusFromXml activeDisplayFitStatus : "
                                + attributeValue6);
                this.mIsFitToActiveDisplay = attributeValue6.equals("true");
            }
            if (typedXmlPullParser.getName().equals("remembered-initiated-device")) {
                int depth6 = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth6)) {
                    if (typedXmlPullParser.getName().equals("initiated-device")) {
                        String attributeValue7 =
                                typedXmlPullParser.getAttributeValue((String) null, "macAddress");
                        if (attributeValue7 == null) {
                            throw new XmlPullParserException(
                                    "Missing macAddress attribute on initiated-device.");
                        }
                        this.mRememberedInitiatedDevices.remove(attributeValue7);
                        if (BluetoothAdapter.checkBluetoothAddress(attributeValue7)) {
                            this.mRememberedInitiatedDevices.add(attributeValue7);
                            if (this.mRememberedInitiatedDevices.size() > 3) {
                                this.mRememberedInitiatedDevices.remove(0);
                            }
                        } else {
                            Slog.w(
                                    "DisplayManager.PersistentDataStore",
                                    "remove invalid device  = ".concat(attributeValue7));
                        }
                    }
                }
            }
            if (typedXmlPullParser.getName().equals("initiated-mirroring-uuid")) {
                this.mInitiatedMirroringUuid =
                        typedXmlPullParser.getAttributeValue((String) null, "uuid");
            }
        }
    }

    public final void loadIfNeeded() {
        if (this.mLoaded) {
            return;
        }
        synchronized (this.mFileAccessLock) {
            this.mRememberedWifiDisplays.clear();
            try {
                FileInputStream openRead = this.mInjector.mAtomicFile.openRead();
                try {
                    try {
                        try {
                            try {
                                loadFromXml(Xml.resolvePullParser(openRead));
                            } catch (Exception e) {
                                Slog.w(
                                        "DisplayManager.PersistentDataStore",
                                        "Failed to load display manager persistent store data!",
                                        e);
                                this.mRememberedWifiDisplays.clear();
                            }
                        } catch (XmlPullParserException e2) {
                            Slog.w(
                                    "DisplayManager.PersistentDataStore",
                                    "Failed to load display manager persistent store data.",
                                    e2);
                            this.mRememberedWifiDisplays.clear();
                        }
                    } catch (IOException e3) {
                        Slog.w(
                                "DisplayManager.PersistentDataStore",
                                "Failed to load display manager persistent store data.",
                                e3);
                        this.mRememberedWifiDisplays.clear();
                    }
                } finally {
                    IoUtils.closeQuietly(openRead);
                }
            } catch (FileNotFoundException unused) {
            }
        }
        this.mLoaded = true;
    }

    public final boolean rememberWifiDisplay(WifiDisplay wifiDisplay) {
        loadIfNeeded();
        int findRememberedWifiDisplay = findRememberedWifiDisplay(wifiDisplay.getDeviceAddress());
        if (findRememberedWifiDisplay < 0) {
            this.mRememberedWifiDisplays.add(wifiDisplay);
        } else {
            if (((WifiDisplay) this.mRememberedWifiDisplays.get(findRememberedWifiDisplay))
                    .equals(wifiDisplay)) {
                return false;
            }
            this.mRememberedWifiDisplays.set(findRememberedWifiDisplay, wifiDisplay);
        }
        this.mDirty = true;
        return true;
    }

    public final void saveIfNeeded() {
        if (this.mDirty) {
            Handler handler = this.mHandler;
            try {
                final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(byteArrayOutputStream);
                saveToXml(resolveSerializer);
                resolveSerializer.flush();
                handler.removeCallbacksAndMessages(null);
                handler.post(
                        new Runnable() { // from class:
                            // com.android.server.display.PersistentDataStore$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                PersistentDataStore.Injector injector;
                                PersistentDataStore persistentDataStore = PersistentDataStore.this;
                                ByteArrayOutputStream byteArrayOutputStream2 =
                                        byteArrayOutputStream;
                                synchronized (persistentDataStore.mFileAccessLock) {
                                    FileOutputStream fileOutputStream = null;
                                    try {
                                        try {
                                            fileOutputStream =
                                                    persistentDataStore.mInjector.mAtomicFile
                                                            .startWrite();
                                            byteArrayOutputStream2.writeTo(fileOutputStream);
                                            fileOutputStream.flush();
                                            injector = persistentDataStore.mInjector;
                                        } catch (Throwable th) {
                                            if (fileOutputStream != null) {
                                                persistentDataStore.mInjector.finishWrite(
                                                        fileOutputStream);
                                            }
                                            throw th;
                                        }
                                    } catch (IOException e) {
                                        Slog.w(
                                                "DisplayManager.PersistentDataStore",
                                                "Failed to save display manager persistent store"
                                                    + " data.",
                                                e);
                                        if (fileOutputStream != null) {
                                            injector = persistentDataStore.mInjector;
                                        }
                                    }
                                    injector.finishWrite(fileOutputStream);
                                }
                            }
                        });
            } catch (IOException e) {
                Slog.w(
                        "DisplayManager.PersistentDataStore",
                        "Failed to process the XML serializer.",
                        e);
            }
            this.mDirty = false;
        }
    }

    public final void saveToXml(TypedXmlSerializer typedXmlSerializer) {
        typedXmlSerializer.startDocument((String) null, Boolean.TRUE);
        typedXmlSerializer.setFeature(
                "http://xmlpull.org/v1/doc/features.html#indent-output", true);
        typedXmlSerializer.startTag((String) null, "display-manager-state");
        typedXmlSerializer.startTag((String) null, "remembered-wifi-displays");
        Iterator it = this.mRememberedWifiDisplays.iterator();
        while (it.hasNext()) {
            WifiDisplay wifiDisplay = (WifiDisplay) it.next();
            typedXmlSerializer.startTag((String) null, "wifi-display");
            typedXmlSerializer.attribute(
                    (String) null, "deviceAddress", wifiDisplay.getDeviceAddress());
            typedXmlSerializer.attribute((String) null, "deviceName", wifiDisplay.getDeviceName());
            if (wifiDisplay.getDeviceAlias() != null) {
                typedXmlSerializer.attribute(
                        (String) null, "deviceAlias", wifiDisplay.getDeviceAlias());
            }
            if (wifiDisplay.getPrimaryDeviceType() != null) {
                typedXmlSerializer.attribute(
                        (String) null, "deviceType", wifiDisplay.getPrimaryDeviceType());
            }
            typedXmlSerializer.endTag((String) null, "wifi-display");
        }
        typedXmlSerializer.endTag((String) null, "remembered-wifi-displays");
        typedXmlSerializer.startTag((String) null, "display-states");
        for (Map.Entry entry : this.mDisplayStates.entrySet()) {
            String str = (String) entry.getKey();
            DisplayState displayState = (DisplayState) entry.getValue();
            typedXmlSerializer.startTag((String) null, "display");
            typedXmlSerializer.attribute((String) null, "unique-id", str);
            displayState.getClass();
            typedXmlSerializer.startTag((String) null, "color-mode");
            typedXmlSerializer.text(Integer.toString(displayState.mColorMode));
            typedXmlSerializer.endTag((String) null, "color-mode");
            for (int i = 0; i < displayState.mPerUserBrightness.size(); i++) {
                typedXmlSerializer.startTag((String) null, "brightness-value");
                typedXmlSerializer.attributeInt(
                        (String) null, "user-serial", displayState.mPerUserBrightness.keyAt(i));
                typedXmlSerializer.text(
                        Float.toString(
                                ((Float) displayState.mPerUserBrightness.valueAt(i)).floatValue()));
                typedXmlSerializer.endTag((String) null, "brightness-value");
            }
            typedXmlSerializer.startTag((String) null, "brightness-configurations");
            displayState.mDisplayBrightnessConfigurations.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((String) null, "brightness-configurations");
            typedXmlSerializer.startTag((String) null, "resolution-width");
            typedXmlSerializer.text(Integer.toString(displayState.mWidth));
            typedXmlSerializer.endTag((String) null, "resolution-width");
            typedXmlSerializer.startTag((String) null, "resolution-height");
            typedXmlSerializer.text(Integer.toString(displayState.mHeight));
            typedXmlSerializer.endTag((String) null, "resolution-height");
            typedXmlSerializer.startTag((String) null, "refresh-rate");
            typedXmlSerializer.text(Float.toString(displayState.mRefreshRate));
            typedXmlSerializer.endTag((String) null, "refresh-rate");
            typedXmlSerializer.endTag((String) null, "display");
        }
        typedXmlSerializer.endTag((String) null, "display-states");
        typedXmlSerializer.startTag((String) null, "stable-device-values");
        StableDeviceValues stableDeviceValues = this.mStableDeviceValues;
        if (stableDeviceValues.mWidth > 0 && stableDeviceValues.mHeight > 0) {
            typedXmlSerializer.startTag((String) null, "stable-display-width");
            typedXmlSerializer.text(Integer.toString(stableDeviceValues.mWidth));
            typedXmlSerializer.endTag((String) null, "stable-display-width");
            typedXmlSerializer.startTag((String) null, "stable-display-height");
            typedXmlSerializer.text(Integer.toString(stableDeviceValues.mHeight));
            typedXmlSerializer.endTag((String) null, "stable-display-height");
        }
        typedXmlSerializer.endTag((String) null, "stable-device-values");
        typedXmlSerializer.startTag((String) null, "brightness-configurations");
        this.mGlobalBrightnessConfigurations.saveToXml(typedXmlSerializer);
        typedXmlSerializer.endTag((String) null, "brightness-configurations");
        typedXmlSerializer.startTag((String) null, "brightness-nits-for-default-display");
        typedXmlSerializer.text(Float.toString(this.mBrightnessNitsForDefaultDisplay));
        typedXmlSerializer.endTag((String) null, "brightness-nits-for-default-display");
        typedXmlSerializer.startTag((String) null, "remembered-active-display-fit-status");
        typedXmlSerializer.attribute(
                (String) null,
                "activeDisplayFitStatus",
                String.valueOf(this.mIsFitToActiveDisplay));
        typedXmlSerializer.endTag((String) null, "remembered-active-display-fit-status");
        Slog.d(
                "DisplayManager.PersistentDataStore",
                "saveToXml remembered active display fit status value:"
                        + String.valueOf(this.mIsFitToActiveDisplay));
        typedXmlSerializer.startTag((String) null, "remembered-changed-view-mode");
        typedXmlSerializer.endTag((String) null, "remembered-changed-view-mode");
        typedXmlSerializer.startTag((String) null, "remembered-initiated-device");
        Iterator it2 = this.mRememberedInitiatedDevices.iterator();
        while (it2.hasNext()) {
            String str2 = (String) it2.next();
            typedXmlSerializer.startTag((String) null, "initiated-device");
            typedXmlSerializer.attribute((String) null, "macAddress", str2);
            typedXmlSerializer.endTag((String) null, "initiated-device");
        }
        typedXmlSerializer.endTag((String) null, "remembered-initiated-device");
        typedXmlSerializer.startTag((String) null, "initiated-mirroring-uuid");
        String str3 = this.mInitiatedMirroringUuid;
        if (str3 != null) {
            typedXmlSerializer.attribute((String) null, "uuid", str3);
        }
        typedXmlSerializer.endTag((String) null, "initiated-mirroring-uuid");
        typedXmlSerializer.endTag((String) null, "display-manager-state");
        typedXmlSerializer.endDocument();
    }

    public final void setBrightness(DisplayDevice displayDevice, float f, int i) {
        String str;
        if (displayDevice == null
                || !displayDevice.hasStableUniqueId()
                || (str = displayDevice.mUniqueId) == null) {
            return;
        }
        DisplayState displayState = getDisplayState(str, true);
        displayState.mPerUserBrightness.remove(-1);
        if (displayState.getBrightness(i) == f) {
            return;
        }
        displayState.mPerUserBrightness.set(i, Float.valueOf(f));
        this.mDirty = true;
    }
}
