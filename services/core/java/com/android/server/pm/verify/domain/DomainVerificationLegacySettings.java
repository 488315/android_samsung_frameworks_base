package com.android.server.pm.verify.domain;

import android.content.pm.IntentFilterVerificationInfo;
import android.util.ArrayMap;
import android.util.SparseIntArray;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.pm.SettingsXml;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;

/* loaded from: classes3.dex */
public class DomainVerificationLegacySettings {
    public final Object mLock = new Object();
    public final ArrayMap mStates = new ArrayMap();

    public void add(String str, IntentFilterVerificationInfo intentFilterVerificationInfo) {
        synchronized (this.mLock) {
            getOrCreateStateLocked(str).setInfo(intentFilterVerificationInfo);
        }
    }

    public void add(String str, int i, int i2) {
        synchronized (this.mLock) {
            getOrCreateStateLocked(str).addUserState(i, i2);
        }
    }

    public int getUserState(String str, int i) {
        synchronized (this.mLock) {
            LegacyState legacyState = (LegacyState) this.mStates.get(str);
            if (legacyState == null) {
                return 0;
            }
            return legacyState.getUserState(i);
        }
    }

    public SparseIntArray getUserStates(String str) {
        synchronized (this.mLock) {
            LegacyState legacyState = (LegacyState) this.mStates.get(str);
            if (legacyState == null) {
                return null;
            }
            return legacyState.getUserStates();
        }
    }

    public IntentFilterVerificationInfo remove(String str) {
        synchronized (this.mLock) {
            LegacyState legacyState = (LegacyState) this.mStates.get(str);
            if (legacyState == null || legacyState.isAttached()) {
                return null;
            }
            legacyState.markAttached();
            return legacyState.getInfo();
        }
    }

    public final LegacyState getOrCreateStateLocked(String str) {
        LegacyState legacyState = (LegacyState) this.mStates.get(str);
        if (legacyState != null) {
            return legacyState;
        }
        LegacyState legacyState2 = new LegacyState();
        this.mStates.put(str, legacyState2);
        return legacyState2;
    }

    public void writeSettings(TypedXmlSerializer typedXmlSerializer) {
        SettingsXml.Serializer serializer = SettingsXml.serializer(typedXmlSerializer);
        try {
            SettingsXml.WriteSection startSection = serializer.startSection("domain-verifications-legacy");
            try {
                synchronized (this.mLock) {
                    int size = this.mStates.size();
                    for (int i = 0; i < size; i++) {
                        SparseIntArray userStates = ((LegacyState) this.mStates.valueAt(i)).getUserStates();
                        if (userStates != null) {
                            SettingsXml.WriteSection attribute = serializer.startSection("user-states").attribute("packageName", (String) this.mStates.keyAt(i));
                            try {
                                int size2 = userStates.size();
                                for (int i2 = 0; i2 < size2; i2++) {
                                    attribute.startSection("user-state").attribute("userId", userStates.keyAt(i2)).attribute(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, userStates.valueAt(i2)).finish();
                                }
                                if (attribute != null) {
                                    attribute.close();
                                }
                            } finally {
                            }
                        }
                    }
                }
                if (startSection != null) {
                    startSection.close();
                }
                serializer.close();
            } finally {
            }
        } catch (Throwable th) {
            if (serializer != null) {
                try {
                    serializer.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void readSettings(TypedXmlPullParser typedXmlPullParser) {
        SettingsXml.ChildSection children = SettingsXml.parser(typedXmlPullParser).children();
        while (children.moveToNext()) {
            if ("user-states".equals(children.getName())) {
                readUserStates(children);
            }
        }
    }

    public final void readUserStates(SettingsXml.ReadSection readSection) {
        String string = readSection.getString("packageName");
        synchronized (this.mLock) {
            LegacyState orCreateStateLocked = getOrCreateStateLocked(string);
            SettingsXml.ChildSection children = readSection.children();
            while (children.moveToNext()) {
                if ("user-state".equals(children.getName())) {
                    readUserState(children, orCreateStateLocked);
                }
            }
        }
    }

    public final void readUserState(SettingsXml.ReadSection readSection, LegacyState legacyState) {
        legacyState.addUserState(readSection.getInt("userId"), readSection.getInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN));
    }

    public class LegacyState {
        public boolean attached;
        public IntentFilterVerificationInfo mInfo;
        public SparseIntArray mUserStates;

        public IntentFilterVerificationInfo getInfo() {
            return this.mInfo;
        }

        public int getUserState(int i) {
            return this.mUserStates.get(i, 0);
        }

        public SparseIntArray getUserStates() {
            return this.mUserStates;
        }

        public void setInfo(IntentFilterVerificationInfo intentFilterVerificationInfo) {
            this.mInfo = intentFilterVerificationInfo;
        }

        public void addUserState(int i, int i2) {
            if (this.mUserStates == null) {
                this.mUserStates = new SparseIntArray(1);
            }
            this.mUserStates.put(i, i2);
        }

        public boolean isAttached() {
            return this.attached;
        }

        public void markAttached() {
            this.attached = true;
        }
    }
}
