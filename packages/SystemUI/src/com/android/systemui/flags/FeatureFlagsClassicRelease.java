package com.android.systemui.flags;

import android.content.res.Resources;
import android.os.SystemProperties;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FeatureFlagsClassicRelease implements FeatureFlagsClassic {
    public final Resources mResources;
    public final Restarter mRestarter;
    public final ServerFlagReader mServerFlagReader;
    public final SystemPropertiesHelper mSystemProperties;
    public final Map mBooleanCache = new HashMap();
    public final Map mStringCache = new HashMap();
    public final Map mIntCache = new HashMap();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.flags.FeatureFlagsClassicRelease$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    public FeatureFlagsClassicRelease(Resources resources, SystemPropertiesHelper systemPropertiesHelper, ServerFlagReader serverFlagReader, Map<String, Flag> map, Restarter restarter) {
        new AnonymousClass1();
        this.mResources = resources;
        this.mSystemProperties = systemPropertiesHelper;
        this.mServerFlagReader = serverFlagReader;
        this.mRestarter = restarter;
    }

    @Override // android.util.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        boolean z;
        String str;
        boolean z2;
        boolean z3;
        boolean z4;
        printWriter.println("can override: false");
        FlagsFactory.INSTANCE.getClass();
        Map map = FlagsFactory.flagMap;
        map.containsKey(Flags.NULL_FLAG.name);
        printWriter.println("Booleans: ");
        LinkedHashMap linkedHashMap = (LinkedHashMap) map;
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Flag flag = (Flag) ((Map.Entry) it.next()).getValue();
            if ((flag instanceof BooleanFlag) && ((z2 = flag instanceof ResourceBooleanFlag)) && ((z3 = flag instanceof SysPropBooleanFlag))) {
                if (!((HashMap) this.mBooleanCache).containsKey(flag.getName())) {
                    if (z3) {
                        SysPropBooleanFlag sysPropBooleanFlag = (SysPropBooleanFlag) flag;
                        SystemPropertiesHelper systemPropertiesHelper = this.mSystemProperties;
                        String str2 = sysPropBooleanFlag.name;
                        systemPropertiesHelper.getClass();
                        z4 = SystemProperties.getBoolean(str2, sysPropBooleanFlag.f38default);
                    } else if (z2) {
                        z4 = this.mResources.getBoolean(((ResourceBooleanFlag) flag).resourceId);
                    } else if (flag instanceof BooleanFlag) {
                        z4 = ((BooleanFlag) flag).f36default;
                    }
                    printWriter.println("  " + flag.getName() + ": " + ((HashMap) this.mBooleanCache).getOrDefault(flag.getName(), Boolean.valueOf(z4)));
                }
                z4 = false;
                printWriter.println("  " + flag.getName() + ": " + ((HashMap) this.mBooleanCache).getOrDefault(flag.getName(), Boolean.valueOf(z4)));
            }
        }
        printWriter.println("Strings: ");
        Iterator it2 = linkedHashMap.entrySet().iterator();
        while (it2.hasNext()) {
            Flag flag2 = (Flag) ((Map.Entry) it2.next()).getValue();
            if ((flag2 instanceof StringFlag) && ((z = flag2 instanceof ResourceStringFlag))) {
                StringFlag stringFlag = (StringFlag) flag2;
                if (!((HashMap) this.mBooleanCache).containsKey(stringFlag.name)) {
                    if (z) {
                        str = this.mResources.getString(((ResourceStringFlag) flag2).resourceId);
                    } else if (flag2 instanceof StringFlag) {
                        str = ((StringFlag) flag2).f37default;
                    }
                    String str3 = (String) ((HashMap) this.mStringCache).getOrDefault(stringFlag.name, str);
                    printWriter.println("  " + stringFlag.name + ": [length=" + str3.length() + "] \"" + str3 + "\"");
                }
                str = "";
                String str32 = (String) ((HashMap) this.mStringCache).getOrDefault(stringFlag.name, str);
                printWriter.println("  " + stringFlag.name + ": [length=" + str32.length() + "] \"" + str32 + "\"");
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x001e, code lost:
    
        if (r1.deviceConfig.getBoolean(r4, r0, true) != false) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isEnabled(com.android.systemui.flags.ReleasedFlag r4) {
        /*
            r3 = this;
            java.lang.String r0 = r4.name
            com.android.systemui.flags.ServerFlagReader r1 = r3.mServerFlagReader
            java.lang.String r4 = r4.namespace
            com.android.systemui.flags.ServerFlagReaderImpl r1 = (com.android.systemui.flags.ServerFlagReaderImpl) r1
            r1.getClass()
            boolean r2 = kotlin.text.StringsKt__StringsJVMKt.isBlank(r4)
            if (r2 != 0) goto L21
            boolean r2 = kotlin.text.StringsKt__StringsJVMKt.isBlank(r0)
            if (r2 != 0) goto L21
            com.android.systemui.util.DeviceConfigProxy r1 = r1.deviceConfig
            r2 = 1
            boolean r4 = r1.getBoolean(r4, r0, r2)
            if (r4 == 0) goto L21
            goto L22
        L21:
            r2 = 0
        L22:
            boolean r3 = r3.isEnabledInternal(r0, r2)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.flags.FeatureFlagsClassicRelease.isEnabled(com.android.systemui.flags.ReleasedFlag):boolean");
    }

    public final boolean isEnabledInternal(String str, boolean z) {
        if (!((HashMap) this.mBooleanCache).containsKey(str)) {
            ((HashMap) this.mBooleanCache).put(str, Boolean.valueOf(z));
        }
        return ((Boolean) ((HashMap) this.mBooleanCache).get(str)).booleanValue();
    }

    public final boolean isEnabled(ResourceBooleanFlag resourceBooleanFlag) {
        return isEnabledInternal(resourceBooleanFlag.name, this.mResources.getBoolean(resourceBooleanFlag.resourceId));
    }
}
