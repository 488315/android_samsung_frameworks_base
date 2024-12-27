package com.android.server.wm;

import android.os.Environment;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;
import android.view.DisplayAddress;
import android.view.DisplayInfo;

import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DisplayWindowSettingsProvider {
    public FileData mBaseSettings;
    public final WritableSettings mOverrideSettings;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AtomicFileStorage implements WritableSettingsStorage {
        public final AtomicFile mAtomicFile;

        public AtomicFileStorage(AtomicFile atomicFile) {
            this.mAtomicFile = atomicFile;
        }

        public final void finishWrite(OutputStream outputStream, boolean z) {
            if (!(outputStream instanceof FileOutputStream)) {
                throw new IllegalArgumentException(
                        "Unexpected OutputStream as argument: " + outputStream);
            }
            FileOutputStream fileOutputStream = (FileOutputStream) outputStream;
            if (z) {
                this.mAtomicFile.finishWrite(fileOutputStream);
            } else {
                this.mAtomicFile.failWrite(fileOutputStream);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class FileData {
        public int mIdentifierType;
        public final /* synthetic */ int $r8$classId = 0;
        public final Object mSettings = new ArrayMap();

        public FileData() {}

        /* JADX WARN: Code restructure failed: missing block: B:24:0x005a, code lost:

           if (r6 != 4) goto L80;
        */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x005d, code lost:

           r6 = r3.getName();
        */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0067, code lost:

           if (r6.equals("display") == false) goto L81;
        */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x0073, code lost:

           if (r6.equals("config") == false) goto L84;
        */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0079, code lost:

           android.util.Slog.w("WindowManager", "Unknown element under <display-settings>: " + r3.getName());
           com.android.internal.util.XmlUtils.skipCurrentTag(r3);
        */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x0075, code lost:

           com.android.server.wm.DisplayWindowSettingsProvider.readConfig(r3, r2);
        */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0069, code lost:

           com.android.server.wm.DisplayWindowSettingsProvider.readDisplay(r3, r2);
        */
        /* JADX WARN: Removed duplicated region for block: B:48:0x012e  */
        /* JADX WARN: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public FileData(
                com.android.server.wm.DisplayWindowSettingsProvider.ReadableSettingsStorage r10) {
            /*
                Method dump skipped, instructions count: 318
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.wm.DisplayWindowSettingsProvider.FileData.<init>(com.android.server.wm.DisplayWindowSettingsProvider$ReadableSettingsStorage):void");
        }

        public String getIdentifier(DisplayInfo displayInfo) {
            DisplayAddress displayAddress;
            if (this.mIdentifierType != 1
                    || (displayAddress = displayInfo.address) == null
                    || !(displayAddress instanceof DisplayAddress.Physical)) {
                return displayInfo.uniqueId;
            }
            return "port:" + displayInfo.address.getPort();
        }

        public String toString() {
            switch (this.$r8$classId) {
                case 0:
                    return "FileData{mIdentifierType="
                            + this.mIdentifierType
                            + ", mSettings="
                            + ((Map) this.mSettings)
                            + '}';
                default:
                    return super.toString();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ReadableSettingsStorage {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WritableSettings extends FileData {
        public final WritableSettingsStorage mSettingsStorage;
        public final ArraySet mVirtualDisplayIdentifiers;

        public WritableSettings(WritableSettingsStorage writableSettingsStorage) {
            super(writableSettingsStorage);
            this.mVirtualDisplayIdentifiers = new ArraySet();
            this.mSettingsStorage = writableSettingsStorage;
        }

        public final DisplayWindowSettings$SettingsProvider$SettingsEntry getOrCreateSettingsEntry(
                DisplayInfo displayInfo) {
            String identifier = getIdentifier(displayInfo);
            DisplayWindowSettings$SettingsProvider$SettingsEntry
                    displayWindowSettings$SettingsProvider$SettingsEntry =
                            (DisplayWindowSettings$SettingsProvider$SettingsEntry)
                                    ((ArrayMap) this.mSettings).get(identifier);
            if (displayWindowSettings$SettingsProvider$SettingsEntry != null) {
                return displayWindowSettings$SettingsProvider$SettingsEntry;
            }
            DisplayWindowSettings$SettingsProvider$SettingsEntry
                    displayWindowSettings$SettingsProvider$SettingsEntry2 =
                            (DisplayWindowSettings$SettingsProvider$SettingsEntry)
                                    ((ArrayMap) this.mSettings).get(displayInfo.name);
            if (displayWindowSettings$SettingsProvider$SettingsEntry2 != null) {
                ((ArrayMap) this.mSettings).remove(displayInfo.name);
                ((ArrayMap) this.mSettings)
                        .put(identifier, displayWindowSettings$SettingsProvider$SettingsEntry2);
                writeSettings();
                return displayWindowSettings$SettingsProvider$SettingsEntry2;
            }
            DisplayWindowSettings$SettingsProvider$SettingsEntry
                    displayWindowSettings$SettingsProvider$SettingsEntry3 =
                            new DisplayWindowSettings$SettingsProvider$SettingsEntry();
            ((ArrayMap) this.mSettings)
                    .put(identifier, displayWindowSettings$SettingsProvider$SettingsEntry3);
            if (displayInfo.type == 5) {
                this.mVirtualDisplayIdentifiers.add(identifier);
            }
            return displayWindowSettings$SettingsProvider$SettingsEntry3;
        }

        public final void writeSettings() {
            ArrayMap arrayMap = new ArrayMap();
            int i = this.mIdentifierType;
            int size = ((ArrayMap) this.mSettings).size();
            for (int i2 = 0; i2 < size; i2++) {
                String str = (String) ((ArrayMap) this.mSettings).keyAt(i2);
                if (!this.mVirtualDisplayIdentifiers.contains(str)) {
                    arrayMap.put(
                            str,
                            (DisplayWindowSettings$SettingsProvider$SettingsEntry)
                                    ((ArrayMap) this.mSettings).get(str));
                }
            }
            WritableSettingsStorage writableSettingsStorage = this.mSettingsStorage;
            try {
                FileOutputStream startWrite =
                        ((AtomicFileStorage) writableSettingsStorage).mAtomicFile.startWrite();
                try {
                    try {
                        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                        resolveSerializer.startDocument((String) null, Boolean.TRUE);
                        resolveSerializer.startTag((String) null, "display-settings");
                        resolveSerializer.startTag((String) null, "config");
                        resolveSerializer.attributeInt((String) null, "identifier", i);
                        resolveSerializer.endTag((String) null, "config");
                        for (Map.Entry entry : arrayMap.entrySet()) {
                            String str2 = (String) entry.getKey();
                            DisplayWindowSettings$SettingsProvider$SettingsEntry
                                    displayWindowSettings$SettingsProvider$SettingsEntry =
                                            (DisplayWindowSettings$SettingsProvider$SettingsEntry)
                                                    entry.getValue();
                            if (!displayWindowSettings$SettingsProvider$SettingsEntry.isEmpty()) {
                                resolveSerializer.startTag((String) null, "display");
                                resolveSerializer.attribute((String) null, "name", str2);
                                int i3 =
                                        displayWindowSettings$SettingsProvider$SettingsEntry
                                                .mWindowingMode;
                                if (i3 != 0) {
                                    resolveSerializer.attributeInt(
                                            (String) null, "windowingMode", i3);
                                }
                                Integer num =
                                        displayWindowSettings$SettingsProvider$SettingsEntry
                                                .mUserRotationMode;
                                if (num != null) {
                                    resolveSerializer.attributeInt(
                                            (String) null, "userRotationMode", num.intValue());
                                }
                                Integer num2 =
                                        displayWindowSettings$SettingsProvider$SettingsEntry
                                                .mUserRotation;
                                if (num2 != null) {
                                    resolveSerializer.attributeInt(
                                            (String) null, "userRotation", num2.intValue());
                                }
                                int i4 =
                                        displayWindowSettings$SettingsProvider$SettingsEntry
                                                .mForcedWidth;
                                if (i4 != 0
                                        && displayWindowSettings$SettingsProvider$SettingsEntry
                                                        .mForcedHeight
                                                != 0) {
                                    resolveSerializer.attributeInt(
                                            (String) null, "forcedWidth", i4);
                                    resolveSerializer.attributeInt(
                                            (String) null,
                                            "forcedHeight",
                                            displayWindowSettings$SettingsProvider$SettingsEntry
                                                    .mForcedHeight);
                                }
                                int i5 =
                                        displayWindowSettings$SettingsProvider$SettingsEntry
                                                .mForcedDensity;
                                if (i5 != 0) {
                                    resolveSerializer.attributeInt(
                                            (String) null, "forcedDensity", i5);
                                }
                                Integer num3 =
                                        displayWindowSettings$SettingsProvider$SettingsEntry
                                                .mForcedScalingMode;
                                if (num3 != null) {
                                    resolveSerializer.attributeInt(
                                            (String) null, "forcedScalingMode", num3.intValue());
                                }
                                int i6 =
                                        displayWindowSettings$SettingsProvider$SettingsEntry
                                                .mRemoveContentMode;
                                if (i6 != 0) {
                                    resolveSerializer.attributeInt(
                                            (String) null, "removeContentMode", i6);
                                }
                                Boolean bool =
                                        displayWindowSettings$SettingsProvider$SettingsEntry
                                                .mShouldShowWithInsecureKeyguard;
                                if (bool != null) {
                                    resolveSerializer.attributeBoolean(
                                            (String) null,
                                            "shouldShowWithInsecureKeyguard",
                                            bool.booleanValue());
                                }
                                Boolean bool2 =
                                        displayWindowSettings$SettingsProvider$SettingsEntry
                                                .mShouldShowSystemDecors;
                                if (bool2 != null) {
                                    resolveSerializer.attributeBoolean(
                                            (String) null,
                                            "shouldShowSystemDecors",
                                            bool2.booleanValue());
                                }
                                Integer num4 =
                                        displayWindowSettings$SettingsProvider$SettingsEntry
                                                .mImePolicy;
                                if (num4 != null) {
                                    resolveSerializer.attributeInt(
                                            (String) null, "imePolicy", num4.intValue());
                                }
                                Integer num5 =
                                        displayWindowSettings$SettingsProvider$SettingsEntry
                                                .mFixedToUserRotation;
                                if (num5 != null) {
                                    resolveSerializer.attributeInt(
                                            (String) null, "fixedToUserRotation", num5.intValue());
                                }
                                Boolean bool3 =
                                        displayWindowSettings$SettingsProvider$SettingsEntry
                                                .mIgnoreOrientationRequest;
                                if (bool3 != null) {
                                    resolveSerializer.attributeBoolean(
                                            (String) null,
                                            "ignoreOrientationRequest",
                                            bool3.booleanValue());
                                }
                                Boolean bool4 =
                                        displayWindowSettings$SettingsProvider$SettingsEntry
                                                .mIgnoreDisplayCutout;
                                if (bool4 != null) {
                                    resolveSerializer.attributeBoolean(
                                            (String) null,
                                            "ignoreDisplayCutout",
                                            bool4.booleanValue());
                                }
                                Boolean bool5 =
                                        displayWindowSettings$SettingsProvider$SettingsEntry
                                                .mDontMoveToTop;
                                if (bool5 != null) {
                                    resolveSerializer.attributeBoolean(
                                            (String) null, "dontMoveToTop", bool5.booleanValue());
                                }
                                resolveSerializer.endTag((String) null, "display");
                            }
                        }
                        resolveSerializer.endTag((String) null, "display-settings");
                        resolveSerializer.endDocument();
                        ((AtomicFileStorage) writableSettingsStorage).finishWrite(startWrite, true);
                    } catch (IOException e) {
                        Slog.w("WindowManager", "Failed to write display window settings.", e);
                        ((AtomicFileStorage) writableSettingsStorage)
                                .finishWrite(startWrite, false);
                    }
                } catch (Throwable th) {
                    ((AtomicFileStorage) writableSettingsStorage).finishWrite(startWrite, false);
                    throw th;
                }
            } catch (IOException e2) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        "Failed to write display settings: ", e2, "WindowManager");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface WritableSettingsStorage extends ReadableSettingsStorage {}

    public DisplayWindowSettingsProvider(
            ReadableSettingsStorage readableSettingsStorage,
            WritableSettingsStorage writableSettingsStorage) {
        this.mBaseSettings = new FileData(readableSettingsStorage);
        this.mOverrideSettings = new WritableSettings(writableSettingsStorage);
    }

    public static Boolean getBooleanAttribute(TypedXmlPullParser typedXmlPullParser, String str) {
        try {
            return Boolean.valueOf(typedXmlPullParser.getAttributeBoolean((String) null, str));
        } catch (Exception unused) {
            return null;
        }
    }

    public static Integer getIntegerAttribute(TypedXmlPullParser typedXmlPullParser, String str) {
        try {
            return Integer.valueOf(typedXmlPullParser.getAttributeInt((String) null, str));
        } catch (Exception unused) {
            return null;
        }
    }

    public static AtomicFile getVendorSettingsFile() {
        File file = new File(Environment.getProductDirectory(), "etc/display_settings.xml");
        if (!file.exists()) {
            file = new File(Environment.getVendorDirectory(), "etc/display_settings.xml");
        }
        return new AtomicFile(file, "wm-displays");
    }

    public static void readConfig(TypedXmlPullParser typedXmlPullParser, FileData fileData) {
        fileData.mIdentifierType =
                typedXmlPullParser.getAttributeInt((String) null, "identifier", 0);
        XmlUtils.skipCurrentTag(typedXmlPullParser);
    }

    public static void readDisplay(TypedXmlPullParser typedXmlPullParser, FileData fileData) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        if (attributeValue != null) {
            DisplayWindowSettings$SettingsProvider$SettingsEntry
                    displayWindowSettings$SettingsProvider$SettingsEntry =
                            new DisplayWindowSettings$SettingsProvider$SettingsEntry();
            displayWindowSettings$SettingsProvider$SettingsEntry.mWindowingMode =
                    typedXmlPullParser.getAttributeInt((String) null, "windowingMode", 0);
            displayWindowSettings$SettingsProvider$SettingsEntry.mUserRotationMode =
                    getIntegerAttribute(typedXmlPullParser, "userRotationMode");
            displayWindowSettings$SettingsProvider$SettingsEntry.mUserRotation =
                    getIntegerAttribute(typedXmlPullParser, "userRotation");
            displayWindowSettings$SettingsProvider$SettingsEntry.mForcedWidth =
                    typedXmlPullParser.getAttributeInt((String) null, "forcedWidth", 0);
            displayWindowSettings$SettingsProvider$SettingsEntry.mForcedHeight =
                    typedXmlPullParser.getAttributeInt((String) null, "forcedHeight", 0);
            displayWindowSettings$SettingsProvider$SettingsEntry.mForcedDensity =
                    typedXmlPullParser.getAttributeInt((String) null, "forcedDensity", 0);
            displayWindowSettings$SettingsProvider$SettingsEntry.mForcedScalingMode =
                    getIntegerAttribute(typedXmlPullParser, "forcedScalingMode");
            displayWindowSettings$SettingsProvider$SettingsEntry.mRemoveContentMode =
                    typedXmlPullParser.getAttributeInt((String) null, "removeContentMode", 0);
            displayWindowSettings$SettingsProvider$SettingsEntry.mShouldShowWithInsecureKeyguard =
                    getBooleanAttribute(typedXmlPullParser, "shouldShowWithInsecureKeyguard");
            displayWindowSettings$SettingsProvider$SettingsEntry.mShouldShowSystemDecors =
                    getBooleanAttribute(typedXmlPullParser, "shouldShowSystemDecors");
            Boolean booleanAttribute = getBooleanAttribute(typedXmlPullParser, "shouldShowIme");
            if (booleanAttribute != null) {
                displayWindowSettings$SettingsProvider$SettingsEntry.mImePolicy =
                        Integer.valueOf(!booleanAttribute.booleanValue() ? 1 : 0);
            } else {
                displayWindowSettings$SettingsProvider$SettingsEntry.mImePolicy =
                        getIntegerAttribute(typedXmlPullParser, "imePolicy");
            }
            displayWindowSettings$SettingsProvider$SettingsEntry.mFixedToUserRotation =
                    getIntegerAttribute(typedXmlPullParser, "fixedToUserRotation");
            displayWindowSettings$SettingsProvider$SettingsEntry.mIgnoreOrientationRequest =
                    getBooleanAttribute(typedXmlPullParser, "ignoreOrientationRequest");
            displayWindowSettings$SettingsProvider$SettingsEntry.mIgnoreDisplayCutout =
                    getBooleanAttribute(typedXmlPullParser, "ignoreDisplayCutout");
            displayWindowSettings$SettingsProvider$SettingsEntry.mDontMoveToTop =
                    getBooleanAttribute(typedXmlPullParser, "dontMoveToTop");
            ((ArrayMap) ((Map) fileData.mSettings))
                    .put(attributeValue, displayWindowSettings$SettingsProvider$SettingsEntry);
        }
        XmlUtils.skipCurrentTag(typedXmlPullParser);
    }

    public final DisplayWindowSettings$SettingsProvider$SettingsEntry getOverrideSettings(
            DisplayInfo displayInfo) {
        return new DisplayWindowSettings$SettingsProvider$SettingsEntry(
                this.mOverrideSettings.getOrCreateSettingsEntry(displayInfo));
    }

    public int getOverrideSettingsSize() {
        return ((ArrayMap) this.mOverrideSettings.mSettings).size();
    }

    public final DisplayWindowSettings$SettingsProvider$SettingsEntry getSettings(
            DisplayInfo displayInfo) {
        FileData fileData = this.mBaseSettings;
        String identifier = fileData.getIdentifier(displayInfo);
        DisplayWindowSettings$SettingsProvider$SettingsEntry
                displayWindowSettings$SettingsProvider$SettingsEntry =
                        (DisplayWindowSettings$SettingsProvider$SettingsEntry)
                                ((ArrayMap) fileData.mSettings).get(identifier);
        if (displayWindowSettings$SettingsProvider$SettingsEntry == null) {
            displayWindowSettings$SettingsProvider$SettingsEntry =
                    (DisplayWindowSettings$SettingsProvider$SettingsEntry)
                            ((ArrayMap) fileData.mSettings).get(displayInfo.name);
            if (displayWindowSettings$SettingsProvider$SettingsEntry != null) {
                ((ArrayMap) fileData.mSettings).remove(displayInfo.name);
                ((ArrayMap) fileData.mSettings)
                        .put(identifier, displayWindowSettings$SettingsProvider$SettingsEntry);
            } else {
                displayWindowSettings$SettingsProvider$SettingsEntry = null;
            }
        }
        DisplayWindowSettings$SettingsProvider$SettingsEntry orCreateSettingsEntry =
                this.mOverrideSettings.getOrCreateSettingsEntry(displayInfo);
        if (displayWindowSettings$SettingsProvider$SettingsEntry == null) {
            return new DisplayWindowSettings$SettingsProvider$SettingsEntry(orCreateSettingsEntry);
        }
        DisplayWindowSettings$SettingsProvider$SettingsEntry
                displayWindowSettings$SettingsProvider$SettingsEntry2 =
                        new DisplayWindowSettings$SettingsProvider$SettingsEntry(
                                displayWindowSettings$SettingsProvider$SettingsEntry);
        int i = orCreateSettingsEntry.mWindowingMode;
        if (i != 0 && i != displayWindowSettings$SettingsProvider$SettingsEntry2.mWindowingMode) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mWindowingMode = i;
        }
        Integer num = orCreateSettingsEntry.mUserRotationMode;
        if (num != null
                && !num.equals(
                        displayWindowSettings$SettingsProvider$SettingsEntry2.mUserRotationMode)) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mUserRotationMode =
                    orCreateSettingsEntry.mUserRotationMode;
        }
        Integer num2 = orCreateSettingsEntry.mUserRotation;
        if (num2 != null
                && !num2.equals(
                        displayWindowSettings$SettingsProvider$SettingsEntry2.mUserRotation)) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mUserRotation =
                    orCreateSettingsEntry.mUserRotation;
        }
        int i2 = orCreateSettingsEntry.mForcedWidth;
        if (i2 != 0 && i2 != displayWindowSettings$SettingsProvider$SettingsEntry2.mForcedWidth) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mForcedWidth = i2;
        }
        int i3 = orCreateSettingsEntry.mForcedHeight;
        if (i3 != 0 && i3 != displayWindowSettings$SettingsProvider$SettingsEntry2.mForcedHeight) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mForcedHeight = i3;
        }
        int i4 = orCreateSettingsEntry.mForcedDensity;
        if (i4 != 0 && i4 != displayWindowSettings$SettingsProvider$SettingsEntry2.mForcedDensity) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mForcedDensity = i4;
        }
        Integer num3 = orCreateSettingsEntry.mForcedScalingMode;
        if (num3 != null
                && !num3.equals(
                        displayWindowSettings$SettingsProvider$SettingsEntry2.mForcedScalingMode)) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mForcedScalingMode =
                    orCreateSettingsEntry.mForcedScalingMode;
        }
        int i5 = orCreateSettingsEntry.mRemoveContentMode;
        if (i5 != 0
                && i5 != displayWindowSettings$SettingsProvider$SettingsEntry2.mRemoveContentMode) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mRemoveContentMode = i5;
        }
        Boolean bool = orCreateSettingsEntry.mShouldShowWithInsecureKeyguard;
        if (bool != null
                && !bool.equals(
                        displayWindowSettings$SettingsProvider$SettingsEntry2
                                .mShouldShowWithInsecureKeyguard)) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mShouldShowWithInsecureKeyguard =
                    orCreateSettingsEntry.mShouldShowWithInsecureKeyguard;
        }
        Boolean bool2 = orCreateSettingsEntry.mShouldShowSystemDecors;
        if (bool2 != null
                && !bool2.equals(
                        displayWindowSettings$SettingsProvider$SettingsEntry2
                                .mShouldShowSystemDecors)) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mShouldShowSystemDecors =
                    orCreateSettingsEntry.mShouldShowSystemDecors;
        }
        Boolean bool3 = orCreateSettingsEntry.mIsHomeSupported;
        if (bool3 != null
                && !bool3.equals(
                        displayWindowSettings$SettingsProvider$SettingsEntry2.mIsHomeSupported)) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mIsHomeSupported =
                    orCreateSettingsEntry.mIsHomeSupported;
        }
        Integer num4 = orCreateSettingsEntry.mImePolicy;
        if (num4 != null
                && !num4.equals(displayWindowSettings$SettingsProvider$SettingsEntry2.mImePolicy)) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mImePolicy =
                    orCreateSettingsEntry.mImePolicy;
        }
        Integer num5 = orCreateSettingsEntry.mFixedToUserRotation;
        if (num5 != null
                && !num5.equals(
                        displayWindowSettings$SettingsProvider$SettingsEntry2
                                .mFixedToUserRotation)) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mFixedToUserRotation =
                    orCreateSettingsEntry.mFixedToUserRotation;
        }
        Boolean bool4 = orCreateSettingsEntry.mIgnoreOrientationRequest;
        if (bool4 != null
                && !bool4.equals(
                        displayWindowSettings$SettingsProvider$SettingsEntry2
                                .mIgnoreOrientationRequest)) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mIgnoreOrientationRequest =
                    orCreateSettingsEntry.mIgnoreOrientationRequest;
        }
        Boolean bool5 = orCreateSettingsEntry.mIgnoreDisplayCutout;
        if (bool5 != null
                && !bool5.equals(
                        displayWindowSettings$SettingsProvider$SettingsEntry2
                                .mIgnoreDisplayCutout)) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mIgnoreDisplayCutout =
                    orCreateSettingsEntry.mIgnoreDisplayCutout;
        }
        Boolean bool6 = orCreateSettingsEntry.mDontMoveToTop;
        if (bool6 != null
                && !bool6.equals(
                        displayWindowSettings$SettingsProvider$SettingsEntry2.mDontMoveToTop)) {
            displayWindowSettings$SettingsProvider$SettingsEntry2.mDontMoveToTop =
                    orCreateSettingsEntry.mDontMoveToTop;
        }
        return displayWindowSettings$SettingsProvider$SettingsEntry2;
    }

    public final void setBaseSettingsFilePath(String str) {
        AtomicFile vendorSettingsFile;
        File file = str != null ? new File(str) : null;
        if (file == null || !file.exists()) {
            Slog.w(
                    "WindowManager",
                    "display settings " + str + " does not exist, using vendor defaults");
            vendorSettingsFile = getVendorSettingsFile();
        } else {
            vendorSettingsFile = new AtomicFile(file, "wm-displays");
        }
        setBaseSettingsStorage(new AtomicFileStorage(vendorSettingsFile));
    }

    public void setBaseSettingsStorage(ReadableSettingsStorage readableSettingsStorage) {
        this.mBaseSettings = new FileData(readableSettingsStorage);
    }

    public final void updateOverrideSettings(
            DisplayInfo displayInfo,
            DisplayWindowSettings$SettingsProvider$SettingsEntry
                    displayWindowSettings$SettingsProvider$SettingsEntry) {
        WritableSettings writableSettings = this.mOverrideSettings;
        if (!writableSettings
                        .getOrCreateSettingsEntry(displayInfo)
                        .setTo(displayWindowSettings$SettingsProvider$SettingsEntry)
                || displayInfo.type == 5) {
            return;
        }
        writableSettings.writeSettings();
    }
}
