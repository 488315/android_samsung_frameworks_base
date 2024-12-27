package com.android.server.input;

import android.hardware.input.TouchCalibration;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseIntArray;
import android.util.Xml;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PersistentDataStore {
    public boolean mDirty;
    public boolean mDirtyEtc;
    public boolean mDirtyGamePadProfiles;
    public final Injector mInjector;
    public boolean mLoaded;
    public boolean mLoadedEtc;
    public boolean mLoadedGamePadProfiles;
    public boolean mNumLockState;
    public final HashMap mInputDevices = new HashMap();
    public final Map mKeyRemapping = new HashMap();
    public final Map mGamePadProfiles = new HashMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GamePadProfile {
        public int mId;
        public String mName;
        public final Map mSimpeButtonMap = new ArrayMap();
        public final Map mSimpeStickMap = new ArrayMap();
        public boolean mUsed;
        public final /* synthetic */ PersistentDataStore this$0;

        public GamePadProfile(PersistentDataStore persistentDataStore, int i) {
            this.mId = i;
            this.mName = "Profile_" + this.mId;
            this.mUsed = false;
            if (this.mId == 0) {
                this.mUsed = true;
            }
        }

        public final void clearData() {
            ((ArrayMap) this.mSimpeButtonMap).clear();
            ((ArrayMap) this.mSimpeStickMap).clear();
            this.mName = "Profile_" + this.mId;
            this.mUsed = false;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GamePadStick {
        public final boolean mInverseH;
        public final boolean mInverseRot;
        public final boolean mInverseV;
        public final int mToStick;

        public GamePadStick(int i, boolean z, boolean z2, boolean z3) {
            this.mToStick = i;
            this.mInverseH = z;
            this.mInverseV = z2;
            this.mInverseRot = z3;
        }

        public final boolean isSame(GamePadStick gamePadStick) {
            return this.mToStick == gamePadStick.mToStick && this.mInverseH == gamePadStick.mInverseH && this.mInverseV == gamePadStick.mInverseV && this.mInverseRot == gamePadStick.mInverseRot;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
        public final AtomicFile mAtomicFile = new AtomicFile(new File("/data/system/input-manager-state.xml"), "input-state");
        public final AtomicFile mAtomicFileEtc = new AtomicFile(new File("/data/system/input-manager-state-etc.xml"), "input-state-etc");
        public final AtomicFile mAtomicFileGamePadProfiles = new AtomicFile(new File("/data/system/input-manager-state-gamepad-profiles.xml"), "input-state-gamepad-profiles");
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InputDeviceState {
        public static final String[] CALIBRATION_NAME = {"x_scale", "x_ymix", "x_offset", "y_xmix", "y_scale", "y_offset"};
        public Set mSelectedKeyboardLayouts;
        public final TouchCalibration[] mTouchCalibration = new TouchCalibration[4];
        public final SparseIntArray mKeyboardBacklightBrightnessMap = new SparseIntArray();
        public final Map mKeyboardLayoutMap = new ArrayMap();
    }

    public PersistentDataStore(Injector injector) {
        this.mInjector = injector;
    }

    public final void clearMappedKey(int i) {
        loadIfNeeded();
        if (((HashMap) this.mKeyRemapping).containsKey(Integer.valueOf(i))) {
            ((HashMap) this.mKeyRemapping).remove(Integer.valueOf(i));
            this.mDirty = true;
        }
    }

    public final void clearStateGamePadProfiles() {
        ((HashMap) this.mGamePadProfiles).clear();
        for (int i = 0; i < 5; i++) {
            ((HashMap) this.mGamePadProfiles).put(Integer.valueOf(i), new GamePadProfile(this, i));
        }
    }

    public final boolean getNumLockState() {
        FileInputStream openRead;
        if (!this.mLoadedEtc) {
            this.mNumLockState = false;
            try {
                try {
                    openRead = this.mInjector.mAtomicFileEtc.openRead();
                    try {
                        loadFromXmlEtc(Xml.resolvePullParser(openRead));
                    } catch (IOException e) {
                        Slog.w("InputManager", "Failed to load input manager persistent store data eTc.", e);
                        this.mNumLockState = false;
                    } catch (XmlPullParserException e2) {
                        Slog.w("InputManager", "Failed to load input manager persistent store data etC.", e2);
                        this.mNumLockState = false;
                    }
                } finally {
                    IoUtils.closeQuietly(openRead);
                }
            } catch (FileNotFoundException unused) {
            }
            this.mLoadedEtc = true;
        }
        return this.mNumLockState;
    }

    public final InputDeviceState getOrCreateInputDeviceState(String str) {
        loadIfNeeded();
        InputDeviceState inputDeviceState = (InputDeviceState) this.mInputDevices.get(str);
        if (inputDeviceState != null) {
            return inputDeviceState;
        }
        InputDeviceState inputDeviceState2 = new InputDeviceState();
        this.mInputDevices.put(str, inputDeviceState2);
        this.mDirty = true;
        return inputDeviceState2;
    }

    public final int getStickForGamePadProfiles(int i, int i2) {
        loadIfNeededGamePadProfiles();
        GamePadStick gamePadStick = (GamePadStick) ((GamePadProfile) ((HashMap) this.mGamePadProfiles).get(Integer.valueOf(i))).mSimpeStickMap.getOrDefault(Integer.valueOf(i2), new GamePadStick(i2, false, false, false));
        int i3 = gamePadStick.mToStick;
        int i4 = gamePadStick.mInverseH ? 32768 : 0;
        return i3 | i4 | (gamePadStick.mInverseV ? EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION : 0) | (gamePadStick.mInverseRot ? 4096 : 0);
    }

    public final void loadFromXml(TypedXmlPullParser typedXmlPullParser) {
        char c;
        XmlUtils.beginDocument(typedXmlPullParser, "input-manager-state");
        int depth = typedXmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            String str = null;
            if (typedXmlPullParser.getName().equals("key-remapping")) {
                int depth2 = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                    if (typedXmlPullParser.getName().equals("remap")) {
                        ((HashMap) this.mKeyRemapping).put(Integer.valueOf(typedXmlPullParser.getAttributeInt((String) null, "from-key")), Integer.valueOf(typedXmlPullParser.getAttributeInt((String) null, "to-key")));
                    }
                }
            } else if (typedXmlPullParser.getName().equals("input-devices")) {
                int depth3 = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth3)) {
                    if (typedXmlPullParser.getName().equals("input-device")) {
                        String attributeValue = typedXmlPullParser.getAttributeValue(str, "descriptor");
                        if (attributeValue == null) {
                            throw new XmlPullParserException("Missing descriptor attribute on input-device.");
                        }
                        if (this.mInputDevices.containsKey(attributeValue)) {
                            throw new XmlPullParserException("Found duplicate input device.");
                        }
                        InputDeviceState inputDeviceState = new InputDeviceState();
                        int depth4 = typedXmlPullParser.getDepth();
                        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth4)) {
                            if (typedXmlPullParser.getName().equals("keyed-keyboard-layout")) {
                                String attributeValue2 = typedXmlPullParser.getAttributeValue(str, "key");
                                if (attributeValue2 == null) {
                                    throw new XmlPullParserException("Missing key attribute on keyed-keyboard-layout.");
                                }
                                String attributeValue3 = typedXmlPullParser.getAttributeValue(str, "layout");
                                if (attributeValue3 == null) {
                                    throw new XmlPullParserException("Missing layout attribute on keyed-keyboard-layout.");
                                }
                                ((ArrayMap) inputDeviceState.mKeyboardLayoutMap).put(attributeValue2, attributeValue3);
                            } else if (typedXmlPullParser.getName().equals("selected-keyboard-layout")) {
                                String attributeValue4 = typedXmlPullParser.getAttributeValue(str, "layout");
                                if (attributeValue4 == null) {
                                    throw new XmlPullParserException("Missing layout attribute on selected-keyboard-layout.");
                                }
                                if (inputDeviceState.mSelectedKeyboardLayouts == null) {
                                    inputDeviceState.mSelectedKeyboardLayouts = new HashSet();
                                }
                                ((HashSet) inputDeviceState.mSelectedKeyboardLayouts).add(attributeValue4);
                            } else if (typedXmlPullParser.getName().equals("light-info")) {
                                inputDeviceState.mKeyboardBacklightBrightnessMap.put(typedXmlPullParser.getAttributeInt(str, "light-id"), typedXmlPullParser.getAttributeInt(str, "light-brightness"));
                            } else if (typedXmlPullParser.getName().equals("calibration")) {
                                String attributeValue5 = typedXmlPullParser.getAttributeValue(str, "format");
                                String attributeValue6 = typedXmlPullParser.getAttributeValue(str, "rotation");
                                if (attributeValue5 == null) {
                                    throw new XmlPullParserException("Missing format attribute on calibration.");
                                }
                                if (!attributeValue5.equals("affine")) {
                                    throw new XmlPullParserException("Unsupported format for calibration.");
                                }
                                if (attributeValue6 != null) {
                                    try {
                                        if ("0".equals(attributeValue6)) {
                                            c = 0;
                                        } else if ("90".equals(attributeValue6)) {
                                            c = 1;
                                        } else if ("180".equals(attributeValue6)) {
                                            c = 2;
                                        } else {
                                            if (!"270".equals(attributeValue6)) {
                                                throw new IllegalArgumentException(XmlUtils$$ExternalSyntheticOutline0.m("Unsupported surface rotation string '", attributeValue6, "'"));
                                            }
                                            c = 3;
                                        }
                                    } catch (IllegalArgumentException unused) {
                                        throw new XmlPullParserException("Unsupported rotation for calibration.");
                                    }
                                } else {
                                    c = 65535;
                                }
                                float[] affineTransform = TouchCalibration.IDENTITY.getAffineTransform();
                                int depth5 = typedXmlPullParser.getDepth();
                                while (true) {
                                    int i = 0;
                                    if (!XmlUtils.nextElementWithin(typedXmlPullParser, depth5)) {
                                        break;
                                    }
                                    String lowerCase = typedXmlPullParser.getName().toLowerCase();
                                    String nextText = typedXmlPullParser.nextText();
                                    while (true) {
                                        if (i < affineTransform.length && i < 6) {
                                            if (lowerCase.equals(InputDeviceState.CALIBRATION_NAME[i])) {
                                                affineTransform[i] = Float.parseFloat(nextText);
                                                break;
                                            }
                                            i++;
                                        }
                                    }
                                }
                                if (c == 65535) {
                                    int i2 = 0;
                                    while (true) {
                                        TouchCalibration[] touchCalibrationArr = inputDeviceState.mTouchCalibration;
                                        if (i2 >= touchCalibrationArr.length) {
                                            break;
                                        }
                                        touchCalibrationArr[i2] = new TouchCalibration(affineTransform[0], affineTransform[1], affineTransform[2], affineTransform[3], affineTransform[4], affineTransform[5]);
                                        i2++;
                                    }
                                } else {
                                    inputDeviceState.mTouchCalibration[c] = new TouchCalibration(affineTransform[0], affineTransform[1], affineTransform[2], affineTransform[3], affineTransform[4], affineTransform[5]);
                                }
                                str = null;
                            } else {
                                continue;
                            }
                        }
                        this.mInputDevices.put(attributeValue, inputDeviceState);
                        str = null;
                    }
                }
            } else {
                continue;
            }
        }
    }

    public final void loadFromXmlEtc(TypedXmlPullParser typedXmlPullParser) {
        XmlUtils.beginDocument(typedXmlPullParser, "input-manager-state-etc");
        int depth = typedXmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals("keyboard-meta-info")) {
                this.mNumLockState = typedXmlPullParser.getAttributeBoolean((String) null, "numLock", false);
            }
        }
    }

    public final void loadFromXmlGamePadProfiles(TypedXmlPullParser typedXmlPullParser) {
        XmlUtils.beginDocument(typedXmlPullParser, "input-manager-state-gamepad-profiles");
        int depth = typedXmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals("profiles")) {
                int depth2 = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                    if (typedXmlPullParser.getName().equals("profile")) {
                        String str = null;
                        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "id");
                        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
                        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((String) null, "used");
                        GamePadProfile gamePadProfile = (GamePadProfile) ((HashMap) this.mGamePadProfiles).get(Integer.valueOf(attributeInt));
                        if (gamePadProfile != null) {
                            int depth3 = typedXmlPullParser.getDepth();
                            gamePadProfile.clearData();
                            while (XmlUtils.nextElementWithin(typedXmlPullParser, depth3)) {
                                if (typedXmlPullParser.getName().equals("simpleButton")) {
                                    int attributeInt2 = typedXmlPullParser.getAttributeInt(str, "fromCode");
                                    int attributeInt3 = typedXmlPullParser.getAttributeInt(str, "toCode");
                                    ((ArrayMap) gamePadProfile.mSimpeButtonMap).put(Integer.valueOf(attributeInt2), Integer.valueOf(attributeInt3));
                                } else if (typedXmlPullParser.getName().equals("simpleStick")) {
                                    int attributeInt4 = typedXmlPullParser.getAttributeInt(str, "fromCode");
                                    int attributeInt5 = typedXmlPullParser.getAttributeInt(str, "toCode");
                                    boolean attributeBoolean2 = typedXmlPullParser.getAttributeBoolean(str, "inverseH", false);
                                    boolean attributeBoolean3 = typedXmlPullParser.getAttributeBoolean(str, "inverseV", false);
                                    boolean attributeBoolean4 = typedXmlPullParser.getAttributeBoolean(str, "inverseRot", false);
                                    ((ArrayMap) gamePadProfile.mSimpeStickMap).put(Integer.valueOf(attributeInt4), new GamePadStick(attributeInt5, attributeBoolean2, attributeBoolean3, attributeBoolean4));
                                    str = null;
                                }
                            }
                            gamePadProfile.mId = attributeInt;
                            gamePadProfile.mName = attributeValue;
                            gamePadProfile.mUsed = attributeBoolean;
                        } else {
                            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(attributeInt, "wrong profile id : ", "InputManager");
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.util.HashMap] */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.FileInputStream, java.io.InputStream, java.lang.AutoCloseable] */
    public final void loadIfNeeded() {
        if (this.mLoaded) {
            return;
        }
        ((HashMap) this.mKeyRemapping).clear();
        ?? r2 = this.mInputDevices;
        r2.clear();
        try {
            try {
                r2 = this.mInjector.mAtomicFile.openRead();
                try {
                    loadFromXml(Xml.resolvePullParser(r2));
                } catch (IOException e) {
                    Slog.w("InputManager", "Failed to load input manager persistent store data.", e);
                    ((HashMap) this.mKeyRemapping).clear();
                    this.mInputDevices.clear();
                } catch (XmlPullParserException e2) {
                    Slog.w("InputManager", "Failed to load input manager persistent store data.", e2);
                    ((HashMap) this.mKeyRemapping).clear();
                    this.mInputDevices.clear();
                }
            } finally {
                IoUtils.closeQuietly((AutoCloseable) r2);
            }
        } catch (FileNotFoundException unused) {
        }
        this.mLoaded = true;
    }

    public final void loadIfNeededGamePadProfiles() {
        FileInputStream openRead;
        if (this.mLoadedGamePadProfiles) {
            return;
        }
        clearStateGamePadProfiles();
        try {
            try {
                openRead = this.mInjector.mAtomicFileGamePadProfiles.openRead();
                try {
                    loadFromXmlGamePadProfiles(Xml.resolvePullParser(openRead));
                } catch (IOException e) {
                    Slog.w("InputManager", "Failed to load input manager persistent store data GamePadProfiles.", e);
                    clearStateGamePadProfiles();
                } catch (XmlPullParserException e2) {
                    Slog.w("InputManager", "Failed to load input manager persistent store data GamepadProfiles.", e2);
                    clearStateGamePadProfiles();
                }
            } finally {
                IoUtils.closeQuietly(openRead);
            }
        } catch (FileNotFoundException unused) {
        }
        this.mLoadedGamePadProfiles = true;
    }

    public final void removeUninstalledKeyboardLayouts(Set set) {
        boolean z = false;
        for (InputDeviceState inputDeviceState : this.mInputDevices.values()) {
            inputDeviceState.getClass();
            ArrayList arrayList = new ArrayList();
            for (String str : ((ArrayMap) inputDeviceState.mKeyboardLayoutMap).keySet()) {
                if (!((HashSet) set).contains(((ArrayMap) inputDeviceState.mKeyboardLayoutMap).get(str))) {
                    arrayList.add(str);
                }
            }
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((ArrayMap) inputDeviceState.mKeyboardLayoutMap).remove((String) it.next());
                }
                z = true;
            }
        }
        if (z) {
            this.mDirty = true;
        }
    }

    public final void saveIfNeeded() {
        if (this.mDirty) {
            Injector injector = this.mInjector;
            try {
                FileOutputStream startWrite = injector.mAtomicFile.startWrite();
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                    saveToXml(resolveSerializer);
                    resolveSerializer.flush();
                    injector.mAtomicFile.finishWrite(startWrite);
                } catch (Throwable th) {
                    injector.mAtomicFile.failWrite(startWrite);
                    throw th;
                }
            } catch (IOException e) {
                Slog.w("InputManager", "Failed to save input manager persistent store data.", e);
            }
            this.mDirty = false;
        }
    }

    public final void saveIfNeededEtc() {
        if (this.mDirtyEtc) {
            Injector injector = this.mInjector;
            try {
                FileOutputStream startWrite = injector.mAtomicFileEtc.startWrite();
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((String) null, Boolean.TRUE);
                    resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    resolveSerializer.startTag((String) null, "input-manager-state-etc");
                    resolveSerializer.startTag((String) null, "keyboard-meta-info");
                    resolveSerializer.attributeBoolean((String) null, "numLock", this.mNumLockState);
                    resolveSerializer.endTag((String) null, "keyboard-meta-info");
                    resolveSerializer.endTag((String) null, "input-manager-state-etc");
                    resolveSerializer.endDocument();
                    resolveSerializer.flush();
                    injector.mAtomicFileEtc.finishWrite(startWrite);
                } catch (Throwable th) {
                    injector.mAtomicFileEtc.failWrite(startWrite);
                    throw th;
                }
            } catch (IOException e) {
                Slog.w("InputManager", "Failed to save input manager persistent store data Etc.", e);
            }
            this.mDirtyEtc = false;
        }
    }

    public final void saveIfNeededGamePadProfiles() {
        if (this.mDirtyGamePadProfiles) {
            Injector injector = this.mInjector;
            try {
                FileOutputStream startWrite = injector.mAtomicFileGamePadProfiles.startWrite();
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                    saveToXmlGamePadProfiles(resolveSerializer);
                    resolveSerializer.flush();
                    injector.mAtomicFileGamePadProfiles.finishWrite(startWrite);
                } catch (Throwable th) {
                    injector.mAtomicFileGamePadProfiles.failWrite(startWrite);
                    throw th;
                }
            } catch (IOException e) {
                Slog.w("InputManager", "Failed to save input manager persistent store data GamePadprofiles.", e);
            }
            this.mDirtyGamePadProfiles = false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x0181, code lost:
    
        r14.endTag((java.lang.String) null, "input-device");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void saveToXml(com.android.modules.utils.TypedXmlSerializer r14) {
        /*
            Method dump skipped, instructions count: 400
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.input.PersistentDataStore.saveToXml(com.android.modules.utils.TypedXmlSerializer):void");
    }

    public final void saveToXmlGamePadProfiles(TypedXmlSerializer typedXmlSerializer) {
        typedXmlSerializer.startDocument((String) null, Boolean.TRUE);
        typedXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        typedXmlSerializer.startTag((String) null, "input-manager-state-gamepad-profiles");
        typedXmlSerializer.startTag((String) null, "profiles");
        typedXmlSerializer.attributeInt((String) null, "maxNum", 5);
        int i = 0;
        for (int i2 = 5; i < i2; i2 = 5) {
            GamePadProfile gamePadProfile = (GamePadProfile) ((HashMap) this.mGamePadProfiles).get(Integer.valueOf(i));
            gamePadProfile.getClass();
            typedXmlSerializer.startTag((String) null, "profile");
            typedXmlSerializer.attributeInt((String) null, "id", gamePadProfile.mId);
            typedXmlSerializer.attribute((String) null, "name", gamePadProfile.mName);
            typedXmlSerializer.attributeBoolean((String) null, "used", gamePadProfile.mUsed);
            if (!((ArrayMap) gamePadProfile.mSimpeButtonMap).isEmpty()) {
                for (Integer num : ((ArrayMap) gamePadProfile.mSimpeButtonMap).keySet()) {
                    int intValue = num.intValue();
                    typedXmlSerializer.startTag((String) null, "simpleButton");
                    typedXmlSerializer.attribute((String) null, "from", GamePadRemapper.getButtonString(intValue));
                    typedXmlSerializer.attributeInt((String) null, "fromCode", intValue);
                    int intValue2 = ((Integer) ((ArrayMap) gamePadProfile.mSimpeButtonMap).get(num)).intValue();
                    typedXmlSerializer.attribute((String) null, "to", GamePadRemapper.getButtonString(intValue2));
                    typedXmlSerializer.attributeInt((String) null, "toCode", intValue2);
                    typedXmlSerializer.endTag((String) null, "simpleButton");
                }
            }
            if (!((ArrayMap) gamePadProfile.mSimpeStickMap).isEmpty()) {
                for (Integer num2 : ((ArrayMap) gamePadProfile.mSimpeStickMap).keySet()) {
                    int intValue3 = num2.intValue();
                    typedXmlSerializer.startTag((String) null, "simpleStick");
                    typedXmlSerializer.attribute((String) null, "from", GamePadRemapper.getButtonString(intValue3));
                    typedXmlSerializer.attributeInt((String) null, "fromCode", intValue3);
                    GamePadStick gamePadStick = (GamePadStick) ((ArrayMap) gamePadProfile.mSimpeStickMap).get(num2);
                    typedXmlSerializer.attribute((String) null, "to", GamePadRemapper.getButtonString(gamePadStick.mToStick));
                    typedXmlSerializer.attributeInt((String) null, "toCode", gamePadStick.mToStick);
                    typedXmlSerializer.attributeBoolean((String) null, "inverseH", gamePadStick.mInverseH);
                    typedXmlSerializer.attributeBoolean((String) null, "inverseV", gamePadStick.mInverseV);
                    typedXmlSerializer.attributeBoolean((String) null, "inverseRot", gamePadStick.mInverseRot);
                    typedXmlSerializer.endTag((String) null, "simpleStick");
                }
            }
            typedXmlSerializer.endTag((String) null, "profile");
            i++;
        }
        typedXmlSerializer.endTag((String) null, "profiles");
        typedXmlSerializer.endTag((String) null, "input-manager-state-gamepad-profiles");
        typedXmlSerializer.endDocument();
    }

    public final void updateStickForGamePadProfiles(int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
        GamePadProfile gamePadProfile = (GamePadProfile) ((HashMap) this.mGamePadProfiles).get(Integer.valueOf(i));
        GamePadStick gamePadStick = new GamePadStick(i3, z, z2, z3);
        if (((GamePadStick) gamePadProfile.mSimpeStickMap.getOrDefault(Integer.valueOf(i2), new GamePadStick(i2, false, false, false))).isSame(gamePadStick)) {
            return;
        }
        ((ArrayMap) gamePadProfile.mSimpeStickMap).put(Integer.valueOf(i2), gamePadStick);
        gamePadProfile.mUsed = true;
        if (gamePadStick.isSame(gamePadStick)) {
            this.mDirtyGamePadProfiles = true;
        }
        saveIfNeededGamePadProfiles();
    }
}
