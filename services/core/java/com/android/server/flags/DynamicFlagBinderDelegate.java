package com.android.server.flags;

import android.os.Build;
import android.os.IBinder;
import android.provider.DeviceConfig;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DynamicFlagBinderDelegate {
    public static final DynamicFlagBinderDelegate$$ExternalSyntheticLambda0 NEW_CALLBACK_SET = new DynamicFlagBinderDelegate$$ExternalSyntheticLambda0();
    public final DynamicFlagBinderDelegate$$ExternalSyntheticLambda1 mFlagChangeCallback;
    public final FlagOverrideStore mFlagStore;
    public final FlagCache mDynamicFlags = new FlagCache();
    public final Map mCallbacks = new HashMap();
    public final AnonymousClass1 mDeviceConfigListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.flags.DynamicFlagBinderDelegate.1
        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            String namespace = properties.getNamespace();
            for (String str : properties.getKeyset()) {
                if (DynamicFlagBinderDelegate.this.mDynamicFlags.contains(namespace, str) && DynamicFlagBinderDelegate.this.mFlagStore.get(namespace, str) == null) {
                    DynamicFlagBinderDelegate.this.mFlagChangeCallback.onFlagChanged(namespace, str, properties.getString(str, (String) null));
                }
            }
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BinderGriever implements IBinder.DeathRecipient {
        public final int mPid;

        public BinderGriever(int i) {
            this.mPid = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (DynamicFlagBinderDelegate.this.mCallbacks) {
                ((HashMap) DynamicFlagBinderDelegate.this.mCallbacks).remove(Integer.valueOf(this.mPid));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DynamicFlagData {
        public String mDefaultValue;
        public final String mName;
        public final String mNamespace;
        public final Set mPids = new HashSet();
        public String mValue;

        public DynamicFlagData(String str, String str2) {
            this.mNamespace = str;
            this.mName = str2;
        }

        public final boolean equals(Object obj) {
            if (obj == null || !(obj instanceof DynamicFlagData)) {
                return false;
            }
            DynamicFlagData dynamicFlagData = (DynamicFlagData) obj;
            return this.mName.equals(dynamicFlagData.mName) && this.mNamespace.equals(dynamicFlagData.mNamespace) && this.mValue.equals(dynamicFlagData.mValue) && this.mDefaultValue.equals(dynamicFlagData.mDefaultValue);
        }

        public final int hashCode() {
            return this.mDefaultValue.hashCode() + this.mValue.hashCode() + this.mNamespace.hashCode() + this.mName.hashCode();
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.flags.DynamicFlagBinderDelegate$1] */
    public DynamicFlagBinderDelegate(FlagOverrideStore flagOverrideStore) {
        DynamicFlagBinderDelegate$$ExternalSyntheticLambda1 dynamicFlagBinderDelegate$$ExternalSyntheticLambda1 = new DynamicFlagBinderDelegate$$ExternalSyntheticLambda1(this);
        this.mFlagChangeCallback = dynamicFlagBinderDelegate$$ExternalSyntheticLambda1;
        this.mFlagStore = flagOverrideStore;
        flagOverrideStore.mCallback = dynamicFlagBinderDelegate$$ExternalSyntheticLambda1;
    }

    public final String getFlagValue(String str, String str2, String str3) {
        String str4;
        FlagCache flagCache = this.mDynamicFlags;
        DynamicFlagData dynamicFlagData = (DynamicFlagData) flagCache.getOrNull(str, str2);
        if (dynamicFlagData != null) {
            str4 = dynamicFlagData.mValue;
        } else {
            flagCache.setIfChanged(str, str2, new DynamicFlagData(str, str2));
            str4 = null;
        }
        if (!Build.IS_USER && str4 == null) {
            str4 = this.mFlagStore.get(str, str2);
        }
        return str4 == null ? DeviceConfig.getString(str, str2, str3) : str4;
    }
}
