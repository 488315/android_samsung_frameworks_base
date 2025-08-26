package com.android.systemui.flags;

import android.content.res.Resources;
import android.os.SystemProperties;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public class FeatureFlagsClassicRelease implements FeatureFlagsClassic {
    public final Resources mResources;
    public final Restarter mRestarter;
    public final ServerFlagReader mServerFlagReader;
    public final SystemPropertiesHelper mSystemProperties;
    public final Map mBooleanCache = new HashMap();
    public final Map mStringCache = new HashMap();
    public final Map mIntCache = new HashMap();

    /* renamed from: com.android.systemui.flags.FeatureFlagsClassicRelease$1, reason: invalid class name */
    public class AnonymousClass1 {
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

    /* JADX WARN: Removed duplicated region for block: B:21:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00f7  */
    @Override // android.util.Dumpable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dump(PrintWriter printWriter, String[] strArr) throws Resources.NotFoundException {
        boolean z;
        String string;
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
                if (((HashMap) this.mBooleanCache).containsKey(flag.getName())) {
                    z4 = false;
                    printWriter.println("  " + flag.getName() + ": " + ((HashMap) this.mBooleanCache).getOrDefault(flag.getName(), Boolean.valueOf(z4)));
                } else {
                    if (z3) {
                        SysPropBooleanFlag sysPropBooleanFlag = (SysPropBooleanFlag) flag;
                        SystemPropertiesHelper systemPropertiesHelper = this.mSystemProperties;
                        String str = sysPropBooleanFlag.name;
                        systemPropertiesHelper.getClass();
                        z4 = SystemProperties.getBoolean(str, sysPropBooleanFlag.f42default);
                    } else if (z2) {
                        z4 = this.mResources.getBoolean(((ResourceBooleanFlag) flag).resourceId);
                    } else if (flag instanceof BooleanFlag) {
                        z4 = ((BooleanFlag) flag).f40default;
                    }
                    printWriter.println("  " + flag.getName() + ": " + ((HashMap) this.mBooleanCache).getOrDefault(flag.getName(), Boolean.valueOf(z4)));
                }
            }
        }
        printWriter.println("Strings: ");
        Iterator it2 = linkedHashMap.entrySet().iterator();
        while (it2.hasNext()) {
            Flag flag2 = (Flag) ((Map.Entry) it2.next()).getValue();
            if ((flag2 instanceof StringFlag) && ((z = flag2 instanceof ResourceStringFlag))) {
                StringFlag stringFlag = (StringFlag) flag2;
                if (((HashMap) this.mBooleanCache).containsKey(stringFlag.name)) {
                    string = "";
                    String str2 = (String) ((HashMap) this.mStringCache).getOrDefault(stringFlag.name, string);
                    printWriter.println("  " + stringFlag.name + ": [length=" + str2.length() + "] \"" + str2 + "\"");
                } else {
                    if (z) {
                        string = this.mResources.getString(((ResourceStringFlag) flag2).resourceId);
                    } else if (flag2 instanceof StringFlag) {
                        string = ((StringFlag) flag2).f41default;
                    }
                    String str22 = (String) ((HashMap) this.mStringCache).getOrDefault(stringFlag.name, string);
                    printWriter.println("  " + stringFlag.name + ": [length=" + str22.length() + "] \"" + str22 + "\"");
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isEnabled(ReleasedFlag releasedFlag) {
        boolean z;
        String str = releasedFlag.name;
        ServerFlagReader serverFlagReader = this.mServerFlagReader;
        String str2 = releasedFlag.namespace;
        ServerFlagReaderImpl serverFlagReaderImpl = (ServerFlagReaderImpl) serverFlagReader;
        serverFlagReaderImpl.getClass();
        if (StringsKt__StringsKt.isBlank(str2) || StringsKt__StringsKt.isBlank(str)) {
            z = false;
        } else {
            z = true;
            if (!serverFlagReaderImpl.deviceConfig.getBoolean(str2, str, true)) {
            }
        }
        return isEnabledInternal(str, z);
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
