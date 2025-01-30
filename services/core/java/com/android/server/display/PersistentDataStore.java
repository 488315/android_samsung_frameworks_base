package com.android.server.display;

import android.bluetooth.BluetoothAdapter;
import android.graphics.Point;
import android.hardware.display.BrightnessConfiguration;
import android.hardware.display.WifiDisplay;
import android.os.Debug;
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
import com.android.server.clipboard.ClipboardService;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TimeZone;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public final class PersistentDataStore {
    public float mBrightnessNitsForDefaultDisplay;
    public boolean mDirty;
    public final HashMap mDisplayStates;
    public final Object mFileAccessLock;
    public BrightnessConfigurations mGlobalBrightnessConfigurations;
    public final Handler mHandler;
    public String mInitiatedMirroringUuid;
    public Injector mInjector;
    public boolean mIsFitToActiveDisplay;
    public boolean mLoaded;
    public ArrayList mRememberedInitiatedDevices;
    public ArrayList mRememberedWifiDisplays;
    public final StableDeviceValues mStableDeviceValues;

    public PersistentDataStore() {
        this(new Injector());
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

    public void saveIfNeeded() {
        if (this.mDirty) {
            save();
            this.mDirty = false;
        }
    }

    public WifiDisplay getRememberedWifiDisplay(String str) {
        loadIfNeeded();
        int findRememberedWifiDisplay = findRememberedWifiDisplay(str);
        if (findRememberedWifiDisplay >= 0) {
            return (WifiDisplay) this.mRememberedWifiDisplays.get(findRememberedWifiDisplay);
        }
        return null;
    }

    public WifiDisplay[] getRememberedWifiDisplays() {
        loadIfNeeded();
        ArrayList arrayList = this.mRememberedWifiDisplays;
        return (WifiDisplay[]) arrayList.toArray(new WifiDisplay[arrayList.size()]);
    }

    public WifiDisplay applyWifiDisplayAlias(WifiDisplay wifiDisplay) {
        if (wifiDisplay != null) {
            loadIfNeeded();
            int findRememberedWifiDisplay = findRememberedWifiDisplay(wifiDisplay.getDeviceAddress());
            String deviceAlias = findRememberedWifiDisplay >= 0 ? ((WifiDisplay) this.mRememberedWifiDisplays.get(findRememberedWifiDisplay)).getDeviceAlias() : null;
            if (!Objects.equals(wifiDisplay.getDeviceAlias(), deviceAlias)) {
                return new WifiDisplay(wifiDisplay.getDeviceAddress(), wifiDisplay.getDeviceName(), deviceAlias, wifiDisplay.isAvailable(), wifiDisplay.canConnect(), wifiDisplay.isRemembered(), wifiDisplay.getPrimaryDeviceType());
            }
        }
        return wifiDisplay;
    }

    public WifiDisplay[] applyWifiDisplayAliases(WifiDisplay[] wifiDisplayArr) {
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

    public boolean rememberWifiDisplay(WifiDisplay wifiDisplay) {
        loadIfNeeded();
        int findRememberedWifiDisplay = findRememberedWifiDisplay(wifiDisplay.getDeviceAddress());
        if (findRememberedWifiDisplay >= 0) {
            if (((WifiDisplay) this.mRememberedWifiDisplays.get(findRememberedWifiDisplay)).equals(wifiDisplay)) {
                return false;
            }
            this.mRememberedWifiDisplays.set(findRememberedWifiDisplay, wifiDisplay);
        } else {
            this.mRememberedWifiDisplays.add(wifiDisplay);
        }
        setDirty();
        return true;
    }

    public boolean forgetWifiDisplay(String str) {
        loadIfNeeded();
        int findRememberedWifiDisplay = findRememberedWifiDisplay(str);
        if (findRememberedWifiDisplay < 0) {
            return false;
        }
        this.mRememberedWifiDisplays.remove(findRememberedWifiDisplay);
        setDirty();
        return true;
    }

    public final int findRememberedWifiDisplay(String str) {
        int size = this.mRememberedWifiDisplays.size();
        for (int i = 0; i < size; i++) {
            if (((WifiDisplay) this.mRememberedWifiDisplays.get(i)).getDeviceAddress().equals(str)) {
                return i;
            }
        }
        return -1;
    }

    public int getColorMode(DisplayDevice displayDevice) {
        DisplayState displayState;
        if (displayDevice.hasStableUniqueId() && (displayState = getDisplayState(displayDevice.getUniqueId(), false)) != null) {
            return displayState.getColorMode();
        }
        return -1;
    }

    public boolean setColorMode(DisplayDevice displayDevice, int i) {
        if (!displayDevice.hasStableUniqueId() || !getDisplayState(displayDevice.getUniqueId(), true).setColorMode(i)) {
            return false;
        }
        setDirty();
        return true;
    }

    public float getBrightness(DisplayDevice displayDevice) {
        DisplayState displayState;
        if (displayDevice == null || !displayDevice.hasStableUniqueId() || (displayState = getDisplayState(displayDevice.getUniqueId(), false)) == null) {
            return Float.NaN;
        }
        return displayState.getBrightness();
    }

    public boolean setBrightness(DisplayDevice displayDevice, float f) {
        String uniqueId;
        if (displayDevice == null || !displayDevice.hasStableUniqueId() || (uniqueId = displayDevice.getUniqueId()) == null || !getDisplayState(uniqueId, true).setBrightness(f)) {
            return false;
        }
        setDirty();
        return true;
    }

    public float getBrightnessNitsForDefaultDisplay() {
        return this.mBrightnessNitsForDefaultDisplay;
    }

    public boolean setBrightnessNitsForDefaultDisplay(float f) {
        if (f == this.mBrightnessNitsForDefaultDisplay) {
            return false;
        }
        this.mBrightnessNitsForDefaultDisplay = f;
        setDirty();
        return true;
    }

    public boolean setUserPreferredRefreshRate(DisplayDevice displayDevice, float f) {
        String uniqueId = displayDevice.getUniqueId();
        if (!displayDevice.hasStableUniqueId() || uniqueId == null || !getDisplayState(displayDevice.getUniqueId(), true).setRefreshRate(f)) {
            return false;
        }
        setDirty();
        return true;
    }

    public float getUserPreferredRefreshRate(DisplayDevice displayDevice) {
        DisplayState displayState;
        if (displayDevice == null || !displayDevice.hasStableUniqueId() || (displayState = getDisplayState(displayDevice.getUniqueId(), false)) == null) {
            return Float.NaN;
        }
        return displayState.getRefreshRate();
    }

    public boolean setUserPreferredResolution(DisplayDevice displayDevice, int i, int i2) {
        String uniqueId = displayDevice.getUniqueId();
        if (!displayDevice.hasStableUniqueId() || uniqueId == null || !getDisplayState(displayDevice.getUniqueId(), true).setResolution(i, i2)) {
            return false;
        }
        setDirty();
        return true;
    }

    public Point getUserPreferredResolution(DisplayDevice displayDevice) {
        DisplayState displayState;
        if (displayDevice == null || !displayDevice.hasStableUniqueId() || (displayState = getDisplayState(displayDevice.getUniqueId(), false)) == null) {
            return null;
        }
        return displayState.getResolution();
    }

    public Point getStableDisplaySize() {
        loadIfNeeded();
        return this.mStableDeviceValues.getDisplaySize();
    }

    public void setStableDisplaySize(Point point) {
        loadIfNeeded();
        if (this.mStableDeviceValues.setDisplaySize(point)) {
            setDirty();
        }
    }

    public void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int i, String str) {
        loadIfNeeded();
        if (this.mGlobalBrightnessConfigurations.setBrightnessConfigurationForUser(brightnessConfiguration, i, str)) {
            setDirty();
        }
        this.mGlobalBrightnessConfigurations.saveHistory(null, null, null);
    }

    public void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int i, String str, List list, List list2, List list3) {
        loadIfNeeded();
        if (this.mGlobalBrightnessConfigurations.setBrightnessConfigurationForUser(brightnessConfiguration, i, str)) {
            setDirty();
        }
        this.mGlobalBrightnessConfigurations.saveHistory(list, list2, list3);
    }

    public boolean setBrightnessConfigurationForDisplayLocked(BrightnessConfiguration brightnessConfiguration, DisplayDevice displayDevice, int i, String str) {
        if (displayDevice == null || !displayDevice.hasStableUniqueId() || !getDisplayState(displayDevice.getUniqueId(), true).setBrightnessConfiguration(brightnessConfiguration, i, str)) {
            return false;
        }
        setDirty();
        return true;
    }

    public BrightnessConfiguration getBrightnessConfigurationForDisplayLocked(String str, int i) {
        loadIfNeeded();
        DisplayState displayState = (DisplayState) this.mDisplayStates.get(str);
        if (displayState != null) {
            return displayState.getBrightnessConfiguration(i);
        }
        return null;
    }

    public BrightnessConfiguration getBrightnessConfiguration(int i) {
        loadIfNeeded();
        return this.mGlobalBrightnessConfigurations.getBrightnessConfiguration(i);
    }

    public final DisplayState getDisplayState(String str, boolean z) {
        loadIfNeeded();
        DisplayState displayState = (DisplayState) this.mDisplayStates.get(str);
        if (displayState != null || !z) {
            return displayState;
        }
        DisplayState displayState2 = new DisplayState();
        this.mDisplayStates.put(str, displayState2);
        setDirty();
        return displayState2;
    }

    public void loadIfNeeded() {
        if (this.mLoaded) {
            return;
        }
        load();
        this.mLoaded = true;
    }

    public final void setDirty() {
        this.mDirty = true;
    }

    public boolean getRememberedActiveDisplayFitStatus() {
        loadIfNeeded();
        return this.mIsFitToActiveDisplay;
    }

    public ArrayList getRememberedInitiatedDevices() {
        loadIfNeeded();
        return this.mRememberedInitiatedDevices;
    }

    public boolean rememberActiveDisplayFitStatus(boolean z) {
        loadIfNeeded();
        if (z == this.mIsFitToActiveDisplay) {
            return false;
        }
        this.mIsFitToActiveDisplay = z;
        setDirty();
        return true;
    }

    public boolean rememberInitiatedDevice(String str) {
        boolean z;
        loadIfNeeded();
        if (this.mRememberedInitiatedDevices.contains(str)) {
            this.mRememberedInitiatedDevices.remove(str);
            z = false;
        } else {
            z = true;
        }
        this.mRememberedInitiatedDevices.add(str);
        if (this.mRememberedInitiatedDevices.size() > 3) {
            this.mRememberedInitiatedDevices.remove(0);
        }
        setDirty();
        return z;
    }

    public void setInitiatedMirroringUuid(String str) {
        loadIfNeeded();
        this.mInitiatedMirroringUuid = str;
        setDirty();
    }

    public String getInitiatedMirroringUuid() {
        return this.mInitiatedMirroringUuid;
    }

    public final void clearState() {
        this.mRememberedWifiDisplays.clear();
    }

    public final void load() {
        synchronized (this.mFileAccessLock) {
            clearState();
            try {
                InputStream openRead = this.mInjector.openRead();
                try {
                    try {
                        try {
                            loadFromXml(Xml.resolvePullParser(openRead));
                        } finally {
                            IoUtils.closeQuietly(openRead);
                        }
                    } catch (XmlPullParserException e) {
                        Slog.w("DisplayManager.PersistentDataStore", "Failed to load display manager persistent store data.", e);
                        clearState();
                    }
                } catch (IOException e2) {
                    Slog.w("DisplayManager.PersistentDataStore", "Failed to load display manager persistent store data.", e2);
                    clearState();
                } catch (Exception e3) {
                    Slog.w("DisplayManager.PersistentDataStore", "Failed to load display manager persistent store data!", e3);
                    clearState();
                }
            } catch (FileNotFoundException unused) {
            }
        }
    }

    public final void save() {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(byteArrayOutputStream);
            saveToXml(resolveSerializer);
            resolveSerializer.flush();
            this.mHandler.removeCallbacksAndMessages(null);
            this.mHandler.post(new Runnable() { // from class: com.android.server.display.PersistentDataStore$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PersistentDataStore.this.lambda$save$0(byteArrayOutputStream);
                }
            });
        } catch (IOException e) {
            Slog.w("DisplayManager.PersistentDataStore", "Failed to process the XML serializer.", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$0(ByteArrayOutputStream byteArrayOutputStream) {
        Injector injector;
        synchronized (this.mFileAccessLock) {
            OutputStream outputStream = null;
            try {
                try {
                    outputStream = this.mInjector.startWrite();
                    byteArrayOutputStream.writeTo(outputStream);
                    outputStream.flush();
                    injector = this.mInjector;
                } catch (IOException e) {
                    Slog.w("DisplayManager.PersistentDataStore", "Failed to save display manager persistent store data.", e);
                    if (outputStream != null) {
                        injector = this.mInjector;
                    }
                }
                injector.finishWrite(outputStream, true);
            } catch (Throwable th) {
                if (outputStream != null) {
                    this.mInjector.finishWrite(outputStream, true);
                }
                throw th;
            }
        }
    }

    public final void loadFromXml(TypedXmlPullParser typedXmlPullParser) {
        XmlUtils.beginDocument(typedXmlPullParser, "display-manager-state");
        int depth = typedXmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals("remembered-wifi-displays")) {
                loadRememberedWifiDisplaysFromXml(typedXmlPullParser);
            }
            if (typedXmlPullParser.getName().equals("display-states")) {
                loadDisplaysFromXml(typedXmlPullParser);
            }
            if (typedXmlPullParser.getName().equals("stable-device-values")) {
                this.mStableDeviceValues.loadFromXml(typedXmlPullParser);
            }
            if (typedXmlPullParser.getName().equals("brightness-configurations")) {
                this.mGlobalBrightnessConfigurations.loadFromXml(typedXmlPullParser);
            }
            if (typedXmlPullParser.getName().equals("brightness-nits-for-default-display")) {
                this.mBrightnessNitsForDefaultDisplay = Float.parseFloat(typedXmlPullParser.nextText());
            }
            if (typedXmlPullParser.getName().equals("remembered-active-display-fit-status")) {
                loadRememberedActiveDisplayFitStatusFromXml(typedXmlPullParser);
            }
            if (typedXmlPullParser.getName().equals("remembered-initiated-device")) {
                loadRememberedInitiatedDevicesFromXml(typedXmlPullParser);
            }
            if (typedXmlPullParser.getName().equals("initiated-mirroring-uuid")) {
                loadRememberedInitiatedMirroringUuidFromXml(typedXmlPullParser);
            }
        }
    }

    public final void loadRememberedWifiDisplaysFromXml(TypedXmlPullParser typedXmlPullParser) {
        int depth = typedXmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals("wifi-display")) {
                String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "deviceAddress");
                String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "deviceName");
                String attributeValue3 = typedXmlPullParser.getAttributeValue((String) null, "deviceAlias");
                String attributeValue4 = typedXmlPullParser.getAttributeValue((String) null, "deviceType");
                if (attributeValue == null || attributeValue2 == null) {
                    throw new XmlPullParserException("Missing deviceAddress or deviceName attribute on wifi-display.");
                }
                if (findRememberedWifiDisplay(attributeValue) >= 0) {
                    throw new XmlPullParserException("Found duplicate wifi display device address.");
                }
                this.mRememberedWifiDisplays.add(new WifiDisplay(attributeValue, attributeValue2, attributeValue3, false, false, false, attributeValue4));
            }
        }
    }

    public final void loadDisplaysFromXml(TypedXmlPullParser typedXmlPullParser) {
        int depth = typedXmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals("display")) {
                String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "unique-id");
                if (attributeValue == null) {
                    throw new XmlPullParserException("Missing unique-id attribute on display.");
                }
                if (this.mDisplayStates.containsKey(attributeValue)) {
                    throw new XmlPullParserException("Found duplicate display.");
                }
                DisplayState displayState = new DisplayState();
                displayState.loadFromXml(typedXmlPullParser);
                this.mDisplayStates.put(attributeValue, displayState);
            }
        }
    }

    public final void loadRememberedActiveDisplayFitStatusFromXml(TypedXmlPullParser typedXmlPullParser) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "activeDisplayFitStatus");
        Slog.d("DisplayManager.PersistentDataStore", "loadRememberedActiveDisplayFitStatusFromXml activeDisplayFitStatus : " + attributeValue);
        this.mIsFitToActiveDisplay = attributeValue.equals("true");
    }

    public final void loadRememberedInitiatedDevicesFromXml(TypedXmlPullParser typedXmlPullParser) {
        int depth = typedXmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals("initiated-device")) {
                String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "macAddress");
                if (attributeValue == null) {
                    throw new XmlPullParserException("Missing macAddress attribute on initiated-device.");
                }
                this.mRememberedInitiatedDevices.remove(attributeValue);
                if (!BluetoothAdapter.checkBluetoothAddress(attributeValue)) {
                    Slog.w("DisplayManager.PersistentDataStore", "remove invalid device  = " + attributeValue);
                } else {
                    this.mRememberedInitiatedDevices.add(attributeValue);
                    if (this.mRememberedInitiatedDevices.size() > 3) {
                        this.mRememberedInitiatedDevices.remove(0);
                    }
                }
            }
        }
    }

    public final void loadRememberedInitiatedMirroringUuidFromXml(TypedXmlPullParser typedXmlPullParser) {
        this.mInitiatedMirroringUuid = typedXmlPullParser.getAttributeValue((String) null, "uuid");
    }

    public final void saveToXml(TypedXmlSerializer typedXmlSerializer) {
        typedXmlSerializer.startDocument((String) null, Boolean.TRUE);
        typedXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        typedXmlSerializer.startTag((String) null, "display-manager-state");
        typedXmlSerializer.startTag((String) null, "remembered-wifi-displays");
        Iterator it = this.mRememberedWifiDisplays.iterator();
        while (it.hasNext()) {
            WifiDisplay wifiDisplay = (WifiDisplay) it.next();
            typedXmlSerializer.startTag((String) null, "wifi-display");
            typedXmlSerializer.attribute((String) null, "deviceAddress", wifiDisplay.getDeviceAddress());
            typedXmlSerializer.attribute((String) null, "deviceName", wifiDisplay.getDeviceName());
            if (wifiDisplay.getDeviceAlias() != null) {
                typedXmlSerializer.attribute((String) null, "deviceAlias", wifiDisplay.getDeviceAlias());
            }
            if (wifiDisplay.getPrimaryDeviceType() != null) {
                typedXmlSerializer.attribute((String) null, "deviceType", wifiDisplay.getPrimaryDeviceType());
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
            displayState.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((String) null, "display");
        }
        typedXmlSerializer.endTag((String) null, "display-states");
        typedXmlSerializer.startTag((String) null, "stable-device-values");
        this.mStableDeviceValues.saveToXml(typedXmlSerializer);
        typedXmlSerializer.endTag((String) null, "stable-device-values");
        typedXmlSerializer.startTag((String) null, "brightness-configurations");
        this.mGlobalBrightnessConfigurations.saveToXml(typedXmlSerializer);
        typedXmlSerializer.endTag((String) null, "brightness-configurations");
        typedXmlSerializer.startTag((String) null, "brightness-nits-for-default-display");
        typedXmlSerializer.text(Float.toString(this.mBrightnessNitsForDefaultDisplay));
        typedXmlSerializer.endTag((String) null, "brightness-nits-for-default-display");
        typedXmlSerializer.startTag((String) null, "remembered-active-display-fit-status");
        typedXmlSerializer.attribute((String) null, "activeDisplayFitStatus", String.valueOf(this.mIsFitToActiveDisplay));
        typedXmlSerializer.endTag((String) null, "remembered-active-display-fit-status");
        Slog.d("DisplayManager.PersistentDataStore", "saveToXml remembered active display fit status value:" + String.valueOf(this.mIsFitToActiveDisplay));
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

    public void dump(PrintWriter printWriter) {
        printWriter.println("PersistentDataStore");
        printWriter.println("  mLoaded=" + this.mLoaded);
        printWriter.println("  mDirty=" + this.mDirty);
        printWriter.println("  RememberedWifiDisplays:");
        Iterator it = this.mRememberedWifiDisplays.iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            printWriter.println("    " + i2 + ": " + ((WifiDisplay) it.next()));
            i2++;
        }
        printWriter.println("  DisplayStates:");
        for (Map.Entry entry : this.mDisplayStates.entrySet()) {
            printWriter.println("    " + i + ": " + ((String) entry.getKey()));
            ((DisplayState) entry.getValue()).dump(printWriter, "      ");
            i++;
        }
        printWriter.println("  StableDeviceValues:");
        this.mStableDeviceValues.dump(printWriter, "      ");
        printWriter.println("  GlobalBrightnessConfigurations:");
        this.mGlobalBrightnessConfigurations.dump(printWriter, "      ");
        printWriter.println("  mBrightnessNitsForDefaultDisplay=" + this.mBrightnessNitsForDefaultDisplay);
    }

    public final class DisplayState {
        public float mBrightness;
        public int mColorMode;
        public BrightnessConfigurations mDisplayBrightnessConfigurations;
        public int mHeight;
        public float mRefreshRate;
        public int mWidth;

        public DisplayState() {
            this.mBrightness = Float.NaN;
            this.mDisplayBrightnessConfigurations = new BrightnessConfigurations();
        }

        public boolean setColorMode(int i) {
            if (i == this.mColorMode) {
                return false;
            }
            this.mColorMode = i;
            return true;
        }

        public int getColorMode() {
            return this.mColorMode;
        }

        public boolean setBrightness(float f) {
            if (f == this.mBrightness) {
                return false;
            }
            this.mBrightness = f;
            return true;
        }

        public float getBrightness() {
            return this.mBrightness;
        }

        public boolean setBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration, int i, String str) {
            this.mDisplayBrightnessConfigurations.setBrightnessConfigurationForUser(brightnessConfiguration, i, str);
            return true;
        }

        public BrightnessConfiguration getBrightnessConfiguration(int i) {
            return (BrightnessConfiguration) this.mDisplayBrightnessConfigurations.mConfigurations.get(i);
        }

        public boolean setResolution(int i, int i2) {
            if (i == this.mWidth && i2 == this.mHeight) {
                return false;
            }
            this.mWidth = i;
            this.mHeight = i2;
            return true;
        }

        public Point getResolution() {
            return new Point(this.mWidth, this.mHeight);
        }

        public boolean setRefreshRate(float f) {
            if (f == this.mRefreshRate) {
                return false;
            }
            this.mRefreshRate = f;
            return true;
        }

        public float getRefreshRate() {
            return this.mRefreshRate;
        }

        public void loadFromXml(TypedXmlPullParser typedXmlPullParser) {
            int depth = typedXmlPullParser.getDepth();
            while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                String name = typedXmlPullParser.getName();
                name.hashCode();
                switch (name) {
                    case "resolution-width":
                        this.mWidth = Integer.parseInt(typedXmlPullParser.nextText());
                        break;
                    case "brightness-configurations":
                        this.mDisplayBrightnessConfigurations.loadFromXml(typedXmlPullParser);
                        break;
                    case "brightness-value":
                        try {
                            this.mBrightness = Float.parseFloat(typedXmlPullParser.nextText());
                            break;
                        } catch (NumberFormatException unused) {
                            this.mBrightness = Float.NaN;
                            break;
                        }
                    case "resolution-height":
                        this.mHeight = Integer.parseInt(typedXmlPullParser.nextText());
                        break;
                    case "refresh-rate":
                        this.mRefreshRate = Float.parseFloat(typedXmlPullParser.nextText());
                        break;
                    case "color-mode":
                        this.mColorMode = Integer.parseInt(typedXmlPullParser.nextText());
                        break;
                }
            }
        }

        public void saveToXml(TypedXmlSerializer typedXmlSerializer) {
            typedXmlSerializer.startTag((String) null, "color-mode");
            typedXmlSerializer.text(Integer.toString(this.mColorMode));
            typedXmlSerializer.endTag((String) null, "color-mode");
            typedXmlSerializer.startTag((String) null, "brightness-value");
            if (!Float.isNaN(this.mBrightness)) {
                typedXmlSerializer.text(Float.toString(this.mBrightness));
            }
            typedXmlSerializer.endTag((String) null, "brightness-value");
            typedXmlSerializer.startTag((String) null, "brightness-configurations");
            this.mDisplayBrightnessConfigurations.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((String) null, "brightness-configurations");
            typedXmlSerializer.startTag((String) null, "resolution-width");
            typedXmlSerializer.text(Integer.toString(this.mWidth));
            typedXmlSerializer.endTag((String) null, "resolution-width");
            typedXmlSerializer.startTag((String) null, "resolution-height");
            typedXmlSerializer.text(Integer.toString(this.mHeight));
            typedXmlSerializer.endTag((String) null, "resolution-height");
            typedXmlSerializer.startTag((String) null, "refresh-rate");
            typedXmlSerializer.text(Float.toString(this.mRefreshRate));
            typedXmlSerializer.endTag((String) null, "refresh-rate");
        }

        public void dump(PrintWriter printWriter, String str) {
            printWriter.println(str + "ColorMode=" + this.mColorMode);
            printWriter.println(str + "BrightnessValue=" + this.mBrightness);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("DisplayBrightnessConfigurations: ");
            printWriter.println(sb.toString());
            this.mDisplayBrightnessConfigurations.dump(printWriter, str);
            printWriter.println(str + "Resolution=" + this.mWidth + " " + this.mHeight);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("RefreshRate=");
            sb2.append(this.mRefreshRate);
            printWriter.println(sb2.toString());
        }
    }

    public final class StableDeviceValues {
        public int mHeight;
        public int mWidth;

        public StableDeviceValues() {
        }

        public final Point getDisplaySize() {
            return new Point(this.mWidth, this.mHeight);
        }

        public boolean setDisplaySize(Point point) {
            int i = this.mWidth;
            int i2 = point.x;
            if (i == i2 && this.mHeight == point.y) {
                return false;
            }
            this.mWidth = i2;
            this.mHeight = point.y;
            return true;
        }

        public void loadFromXml(TypedXmlPullParser typedXmlPullParser) {
            int depth = typedXmlPullParser.getDepth();
            while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                String name = typedXmlPullParser.getName();
                name.hashCode();
                if (name.equals("stable-display-height")) {
                    this.mHeight = loadIntValue(typedXmlPullParser);
                } else if (name.equals("stable-display-width")) {
                    this.mWidth = loadIntValue(typedXmlPullParser);
                }
            }
        }

        public static int loadIntValue(TypedXmlPullParser typedXmlPullParser) {
            try {
                return Integer.parseInt(typedXmlPullParser.nextText());
            } catch (NumberFormatException unused) {
                return 0;
            }
        }

        public void saveToXml(TypedXmlSerializer typedXmlSerializer) {
            if (this.mWidth <= 0 || this.mHeight <= 0) {
                return;
            }
            typedXmlSerializer.startTag((String) null, "stable-display-width");
            typedXmlSerializer.text(Integer.toString(this.mWidth));
            typedXmlSerializer.endTag((String) null, "stable-display-width");
            typedXmlSerializer.startTag((String) null, "stable-display-height");
            typedXmlSerializer.text(Integer.toString(this.mHeight));
            typedXmlSerializer.endTag((String) null, "stable-display-height");
        }

        public void dump(PrintWriter printWriter, String str) {
            printWriter.println(str + "StableDisplayWidth=" + this.mWidth);
            printWriter.println(str + "StableDisplayHeight=" + this.mHeight);
        }
    }

    public final class BrightnessConfigurations {
        public final SparseArray mConfigurations = new SparseArray();
        public final SparseLongArray mTimeStamps = new SparseLongArray();
        public final SparseArray mPackageNames = new SparseArray();
        public SparseArray mSetConfigurations = new SparseArray();
        public SparseLongArray mSetTimeStamps = new SparseLongArray();
        public SparseArray mSetPackageNames = new SparseArray();

        public final boolean setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int i, String str) {
            BrightnessConfiguration brightnessConfiguration2 = (BrightnessConfiguration) this.mConfigurations.get(i);
            Slog.d("DisplayManager.PersistentDataStore", "setBrightnessConfigurationForUser: " + brightnessConfiguration + " userSerial: " + i + " packageName: " + str + " (" + Debug.getCallers(5) + ")");
            if (brightnessConfiguration != null) {
                if (str == null) {
                    this.mSetPackageNames.remove(i);
                } else {
                    this.mSetPackageNames.put(i, str);
                }
                this.mSetTimeStamps.put(i, System.currentTimeMillis());
                if (brightnessConfiguration2 != null && brightnessConfiguration2.equals(brightnessConfiguration)) {
                    this.mSetConfigurations.put(i, null);
                } else {
                    this.mSetConfigurations.put(i, brightnessConfiguration);
                }
            }
            if (brightnessConfiguration2 == brightnessConfiguration) {
                return false;
            }
            if (brightnessConfiguration2 != null && brightnessConfiguration2.equals(brightnessConfiguration)) {
                return false;
            }
            if (brightnessConfiguration != null) {
                if (str == null) {
                    this.mPackageNames.remove(i);
                } else {
                    this.mPackageNames.put(i, str);
                }
                this.mTimeStamps.put(i, System.currentTimeMillis());
                this.mConfigurations.put(i, brightnessConfiguration);
                return true;
            }
            this.mPackageNames.remove(i);
            this.mTimeStamps.delete(i);
            this.mConfigurations.remove(i);
            return true;
        }

        public BrightnessConfiguration getBrightnessConfiguration(int i) {
            return (BrightnessConfiguration) this.mConfigurations.get(i);
        }

        public void loadFromXml(TypedXmlPullParser typedXmlPullParser) {
            int i;
            int depth = typedXmlPullParser.getDepth();
            while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                if ("brightness-configuration".equals(typedXmlPullParser.getName())) {
                    try {
                        i = typedXmlPullParser.getAttributeInt((String) null, "user-serial");
                    } catch (NumberFormatException e) {
                        Slog.e("DisplayManager.PersistentDataStore", "Failed to read in brightness configuration", e);
                        i = -1;
                    }
                    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "package-name");
                    long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "timestamp", -1L);
                    try {
                        BrightnessConfiguration loadFromXml = BrightnessConfiguration.loadFromXml(typedXmlPullParser);
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
                        Slog.e("DisplayManager.PersistentDataStore", "Failed to load brightness configuration!", e2);
                    }
                }
            }
        }

        public void saveToXml(TypedXmlSerializer typedXmlSerializer) {
            for (int i = 0; i < this.mConfigurations.size(); i++) {
                int keyAt = this.mConfigurations.keyAt(i);
                BrightnessConfiguration brightnessConfiguration = (BrightnessConfiguration) this.mConfigurations.valueAt(i);
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

        public void dump(PrintWriter printWriter, String str) {
            for (int i = 0; i < this.mConfigurations.size(); i++) {
                int keyAt = this.mConfigurations.keyAt(i);
                long j = this.mTimeStamps.get(keyAt, -1L);
                String str2 = (String) this.mPackageNames.get(keyAt);
                printWriter.println(str + "User " + keyAt + com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
                if (j != -1) {
                    printWriter.println(str + "  set at: " + TimeUtils.formatForLogging(j));
                }
                if (str2 != null) {
                    printWriter.println(str + "  set by: " + str2);
                }
                printWriter.println(str + "  " + this.mConfigurations.valueAt(i));
            }
            dumpSetBrightnessConfiguration(printWriter, "  ");
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:17:0x008e  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x0249 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:71:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:72:0x023e A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r3v1 */
        /* JADX WARN: Type inference failed for: r3v10 */
        /* JADX WARN: Type inference failed for: r3v11 */
        /* JADX WARN: Type inference failed for: r3v12, types: [java.io.FileOutputStream] */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v15 */
        /* JADX WARN: Type inference failed for: r3v16 */
        /* JADX WARN: Type inference failed for: r3v2 */
        /* JADX WARN: Type inference failed for: r3v3 */
        /* JADX WARN: Type inference failed for: r3v4, types: [java.io.FileOutputStream] */
        /* JADX WARN: Type inference failed for: r3v5, types: [java.io.FileOutputStream] */
        /* JADX WARN: Type inference failed for: r3v6, types: [java.io.FileInputStream] */
        /* JADX WARN: Type inference failed for: r3v7 */
        /* JADX WARN: Type inference failed for: r3v8 */
        /* JADX WARN: Type inference failed for: r3v9 */
        /* JADX WARN: Type inference failed for: r6v17, types: [java.io.FileOutputStream] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void saveHistory(List list, List list2, List list3) {
            ?? r3;
            String str;
            int readFilePosition = readFilePosition();
            FileInputStream fileInputStream = null;
            try {
                if (readFilePosition == 0) {
                    r3 = new FileOutputStream(new File("/data/log/Brightness_Configurations_1"));
                } else if (readFilePosition == 1) {
                    File file = new File("/data/log/Brightness_Configurations_1");
                    r3 = new FileInputStream(file);
                    try {
                        if (r3.available() <= 100000) {
                            fileInputStream = r3;
                            r3 = new FileOutputStream(file, true);
                        } else {
                            ?? fileOutputStream = new FileOutputStream(new File("/data/log/Brightness_Configurations_2"));
                            try {
                                fileWriteInt("/data/log/BC_Position", 2);
                                fileInputStream = r3;
                                r3 = fileOutputStream;
                            } catch (IOException e) {
                                e = e;
                                fileInputStream = fileOutputStream;
                                e.printStackTrace();
                                FileInputStream fileInputStream2 = fileInputStream;
                                fileInputStream = r3;
                                r3 = fileInputStream2;
                                if (r3 != 0) {
                                }
                                if (r3 != 0) {
                                }
                                if (fileInputStream != null) {
                                }
                            }
                        }
                    } catch (IOException e2) {
                        e = e2;
                    }
                } else if (readFilePosition == 2) {
                    File file2 = new File("/data/log/Brightness_Configurations_2");
                    FileInputStream fileInputStream3 = new FileInputStream(file2);
                    try {
                        if (fileInputStream3.available() <= 100000) {
                            r3 = new FileOutputStream(file2, true);
                        } else {
                            ?? fileOutputStream2 = new FileOutputStream(new File("/data/log/Brightness_Configurations_1"));
                            try {
                                fileWriteInt("/data/log/BC_Position", 1);
                                r3 = fileOutputStream2;
                            } catch (IOException e3) {
                                e = e3;
                                fileInputStream = fileOutputStream2;
                                r3 = fileInputStream3;
                                e.printStackTrace();
                                FileInputStream fileInputStream22 = fileInputStream;
                                fileInputStream = r3;
                                r3 = fileInputStream22;
                                if (r3 != 0) {
                                }
                                if (r3 != 0) {
                                }
                                if (fileInputStream != null) {
                                }
                            }
                        }
                        fileInputStream = fileInputStream3;
                    } catch (IOException e4) {
                        e = e4;
                    }
                } else {
                    Slog.d("DisplayManager.PersistentDataStore", "Error case");
                    return;
                }
            } catch (IOException e5) {
                e = e5;
                r3 = 0;
            }
            if (r3 != 0) {
                for (int i = 0; i < this.mSetTimeStamps.size(); i++) {
                    int keyAt = this.mSetConfigurations.keyAt(i);
                    long j = this.mSetTimeStamps.get(keyAt, -1L);
                    String str2 = (String) this.mSetPackageNames.get(keyAt);
                    try {
                        r3.write(("\n\n User " + keyAt + com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR).getBytes(StandardCharsets.UTF_8));
                        StringBuilder sb = new StringBuilder();
                        sb.append("\n  set at: ");
                        sb.append(TimeUtils.formatForLogging(j));
                        r3.write(sb.toString().getBytes(StandardCharsets.UTF_8));
                        long offset = TimeZone.getDefault().getOffset(j);
                        r3.write(("\n  set at: " + TimeUtils.formatForLogging(j + offset) + " GMT : " + Long.toString(offset / ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS)).getBytes(StandardCharsets.UTF_8));
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("\n  set by: ");
                        sb2.append(str2);
                        r3.write(sb2.toString().getBytes(StandardCharsets.UTF_8));
                        if (this.mSetConfigurations.valueAt(i) == null) {
                            str = "\n   Same Configuration";
                        } else {
                            str = "\n   " + this.mSetConfigurations.valueAt(i);
                            r3.write(str.getBytes(StandardCharsets.UTF_8));
                            if (list != null && list.size() != 0) {
                                Iterator it = list.iterator();
                                String str3 = "\n   weight: ";
                                r3.write("\n   weight: ".getBytes(StandardCharsets.UTF_8));
                                while (it.hasNext()) {
                                    str3 = " (" + ((String) it.next()) + "),";
                                    r3.write(str3.getBytes(StandardCharsets.UTF_8));
                                }
                                str = str3;
                            }
                            if (list2 != null && list2.size() != 0) {
                                Iterator it2 = list2.iterator();
                                String str4 = "\n   timeWeights: ";
                                r3.write("\n   timeWeights: ".getBytes(StandardCharsets.UTF_8));
                                while (it2.hasNext()) {
                                    str4 = " (" + ((String) it2.next()) + "),";
                                    r3.write(str4.getBytes(StandardCharsets.UTF_8));
                                }
                                str = str4;
                            }
                            if (list3 != null && list3.size() != 0) {
                                Iterator it3 = list3.iterator();
                                String str5 = "\n   continuityWeights: ";
                                r3.write("\n   continuityWeights: ".getBytes(StandardCharsets.UTF_8));
                                while (it3.hasNext()) {
                                    str5 = " (" + ((String) it3.next()) + "),";
                                    r3.write(str5.getBytes(StandardCharsets.UTF_8));
                                }
                                str = str5;
                            }
                        }
                        r3.write(str.getBytes(StandardCharsets.UTF_8));
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
            }
            if (r3 != 0) {
                try {
                    r3.close();
                } catch (Exception e7) {
                    e7.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e8) {
                    e8.printStackTrace();
                }
            }
        }

        public void dumpSetBrightnessConfiguration(PrintWriter printWriter, String str) {
            try {
                printWriter.println(str + "\n\n dumpSetBrightnessConfiguration_1");
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
                printWriter.println(str + "\n\n dumpSetBrightnessConfiguration_2");
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

        public final int readFilePosition() {
            int parseInt;
            if (new File("/data/log/BC_Position").exists()) {
                String readFromFile = readFromFile("/data/log/BC_Position");
                if (readFromFile == null) {
                    Slog.d("DisplayManager.PersistentDataStore", "/data/log/BC_Position : data is null.");
                    parseInt = -1;
                } else {
                    try {
                        parseInt = Integer.parseInt(readFromFile);
                    } catch (NumberFormatException unused) {
                        Slog.e("DisplayManager.PersistentDataStore", "/data/log/BC_Position : data is \"" + readFromFile + "\"");
                        return 0;
                    }
                }
                return parseInt;
            }
            Slog.d("DisplayManager.PersistentDataStore", "make new position file");
            fileWriteInt("/data/log/BC_Position", 1);
            return 0;
        }

        public final String readFromFile(String str) {
            String str2;
            RandomAccessFile randomAccessFile;
            RandomAccessFile randomAccessFile2 = null;
            String str3 = null;
            try {
                randomAccessFile = new RandomAccessFile(new File(str), "r");
            } catch (IOException unused) {
                str2 = null;
            }
            try {
                str3 = randomAccessFile.readLine();
                randomAccessFile.close();
                Slog.d("DisplayManager.PersistentDataStore", "readFromFile " + str + ": " + str3);
                return str3;
            } catch (IOException unused2) {
                str2 = str3;
                randomAccessFile2 = randomAccessFile;
                Slog.e("DisplayManager.PersistentDataStore", "IOException in readFromFile");
                if (randomAccessFile2 != null) {
                    try {
                        randomAccessFile2.close();
                    } catch (Exception unused3) {
                        Slog.e("DisplayManager.PersistentDataStore", "Exception in readFromFile");
                    }
                }
                return str2;
            }
        }

        public static void fileWriteInt(String str, int i) {
            Slog.i("DisplayManager.PersistentDataStore", "fileWriteInt : " + str + "  value : " + i);
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(new File(str));
                try {
                    fileOutputStream2.write(Integer.toString(i).getBytes(StandardCharsets.UTF_8));
                    fileOutputStream2.close();
                } catch (IOException e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    try {
                        fileOutputStream.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (FileNotFoundException unused) {
            } catch (IOException e3) {
                e = e3;
            }
        }
    }

    class Injector {
        public final AtomicFile mAtomicFile = new AtomicFile(new File("/data/system/display-manager-state.xml"), "display-state");

        public InputStream openRead() {
            return this.mAtomicFile.openRead();
        }

        public OutputStream startWrite() {
            return this.mAtomicFile.startWrite();
        }

        public void finishWrite(OutputStream outputStream, boolean z) {
            if (!(outputStream instanceof FileOutputStream)) {
                throw new IllegalArgumentException("Unexpected OutputStream as argument: " + outputStream);
            }
            FileOutputStream fileOutputStream = (FileOutputStream) outputStream;
            if (z) {
                this.mAtomicFile.finishWrite(fileOutputStream);
            } else {
                this.mAtomicFile.failWrite(fileOutputStream);
            }
        }
    }
}
