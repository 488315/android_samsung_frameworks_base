package com.android.server.om;

import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Xml;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class OverlayManagerSettings {
    public final Object mItemsLock = new Object();
    public final ArrayList mItems = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class BadKeyException extends Exception {
        public BadKeyException(OverlayIdentifier overlayIdentifier, int i) {
            super("Bad key '" + overlayIdentifier + "' for user " + i);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class Serializer {
        static final int CURRENT_VERSION = 4;

        public static void persist(ArrayList arrayList, OutputStream outputStream) {
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(outputStream);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            resolveSerializer.startTag((String) null, "overlays");
            resolveSerializer.attributeInt((String) null, "version", 4);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                SettingsItem settingsItem = (SettingsItem) arrayList.get(i);
                resolveSerializer.startTag((String) null, "item");
                XmlUtils.writeStringAttribute(resolveSerializer, "packageName", settingsItem.mOverlay.getPackageName());
                XmlUtils.writeStringAttribute(resolveSerializer, "overlayName", settingsItem.mOverlay.getOverlayName());
                resolveSerializer.attributeInt((String) null, "userId", settingsItem.mUserId);
                XmlUtils.writeStringAttribute(resolveSerializer, "targetPackageName", settingsItem.mTargetPackageName);
                XmlUtils.writeStringAttribute(resolveSerializer, "targetOverlayableName", settingsItem.mTargetOverlayableName);
                XmlUtils.writeStringAttribute(resolveSerializer, "baseCodePath", settingsItem.mBaseCodePath);
                resolveSerializer.attributeInt((String) null, LauncherConfigurationInternal.KEY_STATE_BOOLEAN, settingsItem.mState);
                XmlUtils.writeBooleanAttribute(resolveSerializer, "isEnabled", settingsItem.mIsEnabled);
                XmlUtils.writeBooleanAttribute(resolveSerializer, "isStatic", !settingsItem.mIsMutable);
                resolveSerializer.attributeInt((String) null, "priority", settingsItem.mPriority);
                XmlUtils.writeStringAttribute(resolveSerializer, "category", settingsItem.mCategory);
                XmlUtils.writeBooleanAttribute(resolveSerializer, "fabricated", settingsItem.mIsFabricated);
                resolveSerializer.endTag((String) null, "item");
            }
            resolveSerializer.endTag((String) null, "overlays");
            resolveSerializer.endDocument();
        }

        public static void restore(ArrayList arrayList, InputStream inputStream) {
            arrayList.clear();
            TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(inputStream);
            XmlUtils.beginDocument(resolvePullParser, "overlays");
            String str = null;
            int attributeInt = resolvePullParser.getAttributeInt((String) null, "version");
            if (attributeInt != 4) {
                if (attributeInt == 0 || attributeInt == 1 || attributeInt == 2) {
                    throw new XmlPullParserException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(attributeInt, "old version ", "; ignoring"));
                }
                if (attributeInt != 3) {
                    throw new XmlPullParserException(VibrationParam$1$$ExternalSyntheticOutline0.m(attributeInt, "unrecognized version "));
                }
            }
            int depth = resolvePullParser.getDepth();
            while (XmlUtils.nextElementWithin(resolvePullParser, depth)) {
                if ("item".equals(resolvePullParser.getName())) {
                    arrayList.add(new SettingsItem(new OverlayIdentifier(XmlUtils.readStringAttribute(resolvePullParser, "packageName"), XmlUtils.readStringAttribute(resolvePullParser, "overlayName")), resolvePullParser.getAttributeInt(str, "userId"), XmlUtils.readStringAttribute(resolvePullParser, "targetPackageName"), XmlUtils.readStringAttribute(resolvePullParser, "targetOverlayableName"), XmlUtils.readStringAttribute(resolvePullParser, "baseCodePath"), resolvePullParser.getAttributeInt(str, LauncherConfigurationInternal.KEY_STATE_BOOLEAN), resolvePullParser.getAttributeBoolean(str, "isEnabled", false), !resolvePullParser.getAttributeBoolean(str, "isStatic", false), resolvePullParser.getAttributeInt(str, "priority"), XmlUtils.readStringAttribute(resolvePullParser, "category"), resolvePullParser.getAttributeBoolean(str, "fabricated", false)));
                    str = null;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsItem {
        public String mBaseCodePath;
        public OverlayInfo mCache = null;
        public String mCategory;
        public boolean mIsEnabled;
        public final boolean mIsFabricated;
        public final boolean mIsMutable;
        public final OverlayIdentifier mOverlay;
        public int mPriority;
        public int mState;
        public final String mTargetOverlayableName;
        public final String mTargetPackageName;
        public final int mUserId;

        /* renamed from: -$$Nest$mgetOverlayInfo, reason: not valid java name */
        public static OverlayInfo m739$$Nest$mgetOverlayInfo(SettingsItem settingsItem) {
            if (settingsItem.mCache == null) {
                String packageName = settingsItem.mOverlay.getPackageName();
                String overlayName = settingsItem.mOverlay.getOverlayName();
                String str = settingsItem.mCategory;
                String str2 = settingsItem.mBaseCodePath;
                int i = settingsItem.mState;
                int i2 = settingsItem.mPriority;
                settingsItem.mCache = new OverlayInfo(packageName, overlayName, settingsItem.mTargetPackageName, settingsItem.mTargetOverlayableName, str, str2, i, settingsItem.mUserId, i2, settingsItem.mIsMutable, settingsItem.mIsFabricated);
            }
            return settingsItem.mCache;
        }

        public SettingsItem(OverlayIdentifier overlayIdentifier, int i, String str, String str2, String str3, int i2, boolean z, boolean z2, int i3, String str4, boolean z3) {
            this.mOverlay = overlayIdentifier;
            this.mUserId = i;
            this.mTargetPackageName = str;
            this.mTargetOverlayableName = str2;
            this.mBaseCodePath = str3;
            this.mState = i2;
            this.mIsEnabled = z;
            this.mCategory = str4;
            this.mIsMutable = z2;
            this.mPriority = i3;
            this.mIsFabricated = z3;
        }
    }

    public final Set getAllBaseCodePaths() {
        ArraySet arraySet;
        synchronized (this.mItemsLock) {
            arraySet = new ArraySet();
            this.mItems.forEach(new OverlayManagerSettings$$ExternalSyntheticLambda1(1, arraySet));
        }
        return arraySet;
    }

    public final Set getAllIdentifiersAndBaseCodePaths() {
        ArraySet arraySet = new ArraySet();
        this.mItems.forEach(new OverlayManagerSettings$$ExternalSyntheticLambda1(0, arraySet));
        return arraySet;
    }

    public final boolean getEnabled(OverlayIdentifier overlayIdentifier, int i) {
        boolean z;
        synchronized (this.mItemsLock) {
            try {
                int select = select(overlayIdentifier, i);
                if (select < 0) {
                    throw new BadKeyException(overlayIdentifier, i);
                }
                z = ((SettingsItem) this.mItems.get(select)).mIsEnabled;
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public final OverlayInfo getNullableOverlayInfo(OverlayIdentifier overlayIdentifier, int i) {
        synchronized (this.mItemsLock) {
            try {
                int select = select(overlayIdentifier, i);
                if (select < 0) {
                    return null;
                }
                return SettingsItem.m739$$Nest$mgetOverlayInfo((SettingsItem) this.mItems.get(select));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final OverlayInfo getOverlayInfo(OverlayIdentifier overlayIdentifier, int i) {
        OverlayInfo m739$$Nest$mgetOverlayInfo;
        synchronized (this.mItemsLock) {
            try {
                int select = select(overlayIdentifier, i);
                if (select < 0) {
                    throw new BadKeyException(overlayIdentifier, i);
                }
                m739$$Nest$mgetOverlayInfo = SettingsItem.m739$$Nest$mgetOverlayInfo((SettingsItem) this.mItems.get(select));
            } catch (Throwable th) {
                throw th;
            }
        }
        return m739$$Nest$mgetOverlayInfo;
    }

    public final List getOverlaysForTarget(int i, String str) {
        List selectWhereUser;
        synchronized (this.mItemsLock) {
            selectWhereUser = selectWhereUser(i);
            ((ArrayList) selectWhereUser).removeIf(new OverlayManagerSettings$$ExternalSyntheticLambda4(3, str));
        }
        return CollectionUtils.map(selectWhereUser, new OverlayManagerSettings$$ExternalSyntheticLambda9(0));
    }

    public final ArrayMap getOverlaysForUser(int i) {
        List selectWhereUser = selectWhereUser(i);
        ArrayMap arrayMap = new ArrayMap();
        ArrayList arrayList = (ArrayList) selectWhereUser;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            SettingsItem settingsItem = (SettingsItem) arrayList.get(i2);
            ((List) arrayMap.computeIfAbsent(settingsItem.mTargetPackageName, new OverlayManagerSettings$$ExternalSyntheticLambda9(1))).add(SettingsItem.m739$$Nest$mgetOverlayInfo(settingsItem));
        }
        return arrayMap;
    }

    public final int getState(OverlayIdentifier overlayIdentifier, int i) {
        int i2;
        synchronized (this.mItemsLock) {
            try {
                int select = select(overlayIdentifier, i);
                if (select < 0) {
                    throw new BadKeyException(overlayIdentifier, i);
                }
                i2 = ((SettingsItem) this.mItems.get(select)).mState;
            } catch (Throwable th) {
                throw th;
            }
        }
        return i2;
    }

    public final int[] getUsers() {
        int[] array;
        synchronized (this.mItemsLock) {
            array = this.mItems.stream().mapToInt(new OverlayManagerSettings$$ExternalSyntheticLambda2()).distinct().toArray();
        }
        return array;
    }

    public final void insert(SettingsItem settingsItem) {
        synchronized (this.mItemsLock) {
            try {
                int size = this.mItems.size() - 1;
                while (size >= 0 && ((SettingsItem) this.mItems.get(size)).mPriority > settingsItem.mPriority) {
                    size--;
                }
                this.mItems.add(size + 1, settingsItem);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean remove(OverlayIdentifier overlayIdentifier, int i) {
        synchronized (this.mItemsLock) {
            try {
                int select = select(overlayIdentifier, i);
                if (select < 0) {
                    return false;
                }
                this.mItems.remove(select);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List removeIf(Predicate predicate) {
        List emptyIfNull;
        synchronized (this.mItemsLock) {
            try {
                List list = null;
                for (int size = this.mItems.size() - 1; size >= 0; size--) {
                    OverlayInfo m739$$Nest$mgetOverlayInfo = SettingsItem.m739$$Nest$mgetOverlayInfo((SettingsItem) this.mItems.get(size));
                    if (predicate.test(m739$$Nest$mgetOverlayInfo)) {
                        this.mItems.remove(size);
                        list = CollectionUtils.add(list, m739$$Nest$mgetOverlayInfo);
                    }
                }
                emptyIfNull = CollectionUtils.emptyIfNull(list);
            } catch (Throwable th) {
                throw th;
            }
        }
        return emptyIfNull;
    }

    public final void removeUser(int i) {
        synchronized (this.mItemsLock) {
            this.mItems.removeIf(new OverlayManagerSettings$$ExternalSyntheticLambda0(i, 0));
        }
    }

    public final int select(OverlayIdentifier overlayIdentifier, int i) {
        synchronized (this.mItemsLock) {
            try {
                int size = this.mItems.size();
                for (int i2 = 0; i2 < size; i2++) {
                    SettingsItem settingsItem = (SettingsItem) this.mItems.get(i2);
                    if (settingsItem.mUserId == i && settingsItem.mOverlay.equals(overlayIdentifier)) {
                        return i2;
                    }
                }
                return -1;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List selectWhereUser(int i) {
        ArrayList arrayList;
        synchronized (this.mItemsLock) {
            arrayList = new ArrayList();
            CollectionUtils.addIf(this.mItems, arrayList, new OverlayManagerSettings$$ExternalSyntheticLambda0(i, 1));
        }
        return arrayList;
    }

    public final boolean setBaseCodePath(int i, OverlayIdentifier overlayIdentifier, String str) {
        boolean z;
        synchronized (this.mItemsLock) {
            try {
                int select = select(overlayIdentifier, i);
                if (select < 0) {
                    throw new BadKeyException(overlayIdentifier, i);
                }
                SettingsItem settingsItem = (SettingsItem) this.mItems.get(select);
                if (settingsItem.mBaseCodePath.equals(str)) {
                    z = false;
                } else {
                    settingsItem.mBaseCodePath = str;
                    settingsItem.mCache = null;
                    z = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public final boolean setEnabled(int i, OverlayIdentifier overlayIdentifier, boolean z) {
        boolean z2;
        synchronized (this.mItemsLock) {
            try {
                int select = select(overlayIdentifier, i);
                if (select < 0) {
                    throw new BadKeyException(overlayIdentifier, i);
                }
                SettingsItem settingsItem = (SettingsItem) this.mItems.get(select);
                z2 = false;
                if (settingsItem.mIsMutable && settingsItem.mIsEnabled != z) {
                    settingsItem.mIsEnabled = z;
                    settingsItem.mCache = null;
                    z2 = true;
                }
            } finally {
            }
        }
        return z2;
    }

    public final boolean setHighestPriority(OverlayIdentifier overlayIdentifier, int i) {
        synchronized (this.mItemsLock) {
            try {
                int select = select(overlayIdentifier, i);
                if (select >= 0 && select != this.mItems.size() - 1) {
                    SettingsItem settingsItem = (SettingsItem) this.mItems.get(select);
                    this.mItems.remove(select);
                    this.mItems.add(settingsItem);
                    return true;
                }
                return false;
            } finally {
            }
        }
    }

    public final void setPriority(OverlayIdentifier overlayIdentifier, int i, int i2) {
        synchronized (this.mItemsLock) {
            try {
                int select = select(overlayIdentifier, i);
                if (select < 0) {
                    throw new BadKeyException(overlayIdentifier, i);
                }
                SettingsItem settingsItem = (SettingsItem) this.mItems.get(select);
                this.mItems.remove(select);
                settingsItem.mPriority = i2;
                settingsItem.mCache = null;
                insert(settingsItem);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setPriority(OverlayIdentifier overlayIdentifier, OverlayIdentifier overlayIdentifier2, int i) {
        synchronized (this.mItemsLock) {
            try {
                if (overlayIdentifier.equals(overlayIdentifier2)) {
                    return false;
                }
                int select = select(overlayIdentifier, i);
                if (select < 0) {
                    return false;
                }
                int select2 = select(overlayIdentifier2, i);
                if (select2 < 0) {
                    return false;
                }
                SettingsItem settingsItem = (SettingsItem) this.mItems.get(select);
                if (!settingsItem.mTargetPackageName.equals(((SettingsItem) this.mItems.get(select2)).mTargetPackageName)) {
                    return false;
                }
                this.mItems.remove(select);
                int select3 = select(overlayIdentifier2, i) + 1;
                this.mItems.add(select3, settingsItem);
                return select != select3;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setState(OverlayIdentifier overlayIdentifier, int i, int i2) {
        boolean z;
        synchronized (this.mItemsLock) {
            try {
                int select = select(overlayIdentifier, i);
                if (select < 0) {
                    throw new BadKeyException(overlayIdentifier, i);
                }
                SettingsItem settingsItem = (SettingsItem) this.mItems.get(select);
                if (settingsItem.mState != i2) {
                    settingsItem.mState = i2;
                    settingsItem.mCache = null;
                    z = true;
                } else {
                    z = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }
}
