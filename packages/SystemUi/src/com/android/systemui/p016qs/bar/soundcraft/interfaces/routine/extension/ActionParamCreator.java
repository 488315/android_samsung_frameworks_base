package com.android.systemui.p016qs.bar.soundcraft.interfaces.routine.extension;

import com.android.systemui.p016qs.bar.soundcraft.interfaces.wearable.BudsPluginInfo;
import com.samsung.android.sdk.routines.automationservice.data.MetaInfo;
import com.samsung.android.sdk.routines.automationservice.data.ParameterValues;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ActionParamCreator {
    public static final ActionParamCreator INSTANCE = new ActionParamCreator();

    private ActionParamCreator() {
    }

    public static void putActionValue(HashMap hashMap, ActionType actionType, String str) {
        BudsPluginInfo[] values = BudsPluginInfo.values();
        ArrayList arrayList = new ArrayList();
        for (BudsPluginInfo budsPluginInfo : values) {
            if (budsPluginInfo.isSupport()) {
                arrayList.add(budsPluginInfo);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            BudsPluginInfo budsPluginInfo2 = (BudsPluginInfo) it.next();
            MetaInfo.Companion companion = MetaInfo.Companion;
            String packageName = budsPluginInfo2.getPackageName();
            String tag = actionType.getTag(budsPluginInfo2.getProjectName());
            companion.getClass();
            MetaInfo metaInfo = new MetaInfo(packageName, tag, null);
            ParameterValues.Companion.getClass();
            ParameterValues parameterValues = new ParameterValues();
            parameterValues.put("v2IntentParam", str);
            Unit unit = Unit.INSTANCE;
            hashMap.put(metaInfo, parameterValues);
        }
    }
}
