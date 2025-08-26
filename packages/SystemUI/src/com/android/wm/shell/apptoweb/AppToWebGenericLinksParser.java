package com.android.wm.shell.apptoweb;

import android.content.Context;
import android.provider.DeviceConfig;
import android.webkit.URLUtil;
import com.android.systemui.R;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import com.android.wm.shell.shared.desktopmode.DesktopConfigImpl;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes3.dex */
public final class AppToWebGenericLinksParser {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final DesktopConfig desktopConfig;
    public final Map genericLinksMap = new LinkedHashMap();
    public final ShellExecutor mainExecutor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getFLAG_GENERIC_LINKS$annotations() {
        }
    }

    public final class DeviceConfigListener implements DeviceConfig.OnPropertiesChangedListener {
        public DeviceConfigListener() {
            DeviceConfig.addOnPropertiesChangedListener("app_compat_overrides", AppToWebGenericLinksParser.this.mainExecutor, this);
        }

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            if (properties.getKeyset().contains("generic_links_flag")) {
                AppToWebGenericLinksParser appToWebGenericLinksParser = AppToWebGenericLinksParser.this;
                int i = AppToWebGenericLinksParser.$r8$clinit;
                appToWebGenericLinksParser.updateGenericLinksMap();
            }
        }
    }

    static {
        new Companion(null);
    }

    public AppToWebGenericLinksParser(Context context, ShellExecutor shellExecutor, DesktopConfig desktopConfig) {
        this.context = context;
        this.mainExecutor = shellExecutor;
        this.desktopConfig = desktopConfig;
        if (!((DesktopConfigImpl) desktopConfig).useAppToWebBuildTimeGenericLinks) {
            new DeviceConfigListener();
        }
        updateGenericLinksMap();
    }

    public final void updateGenericLinksMap() {
        int i = 0;
        String string = ((DesktopConfigImpl) this.desktopConfig).useAppToWebBuildTimeGenericLinks ? this.context.getResources().getString(R.string.generic_links_list) : DeviceConfig.getString("app_compat_overrides", "generic_links_flag", "");
        if (string == null) {
            return;
        }
        List listSplit$default = StringsKt__StringsKt.split$default(string, new String[]{" "}, 0, 6);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listSplit$default) {
            if (StringsKt__StringsKt.contains$default((String) obj, ':')) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj2 = arrayList.get(i2);
            i2++;
            List listSplit$default2 = StringsKt__StringsKt.split$default((String) obj2, new char[]{':'}, 2);
            arrayList2.add(new Pair((String) listSplit$default2.get(0), (String) listSplit$default2.get(1)));
        }
        ArrayList arrayList3 = new ArrayList();
        int size2 = arrayList2.size();
        while (i < size2) {
            Object obj3 = arrayList2.get(i);
            i++;
            if (URLUtil.isNetworkUrl((String) ((Pair) obj3).getSecond())) {
                arrayList3.add(obj3);
            }
        }
        ((LinkedHashMap) this.genericLinksMap).clear();
        MapsKt__MapsKt.putAll(this.genericLinksMap, arrayList3);
    }
}
