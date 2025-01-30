package com.android.systemui.edgelighting.manager;

import android.content.ContentResolver;
import android.provider.Settings;
import android.util.Slog;
import com.android.systemui.R;
import com.android.systemui.edgelighting.Feature;
import com.android.systemui.edgelighting.data.style.EdgeLightingStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EdgeLightingStyleManager {
    public static EdgeLightingStyleManager mInstance;
    public LinkedHashMap mStyleHashMap;

    private EdgeLightingStyleManager() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.mStyleHashMap = linkedHashMap;
        linkedHashMap.clear();
        this.mStyleHashMap.put("preload/noframe", new EdgeLightingStyle("preload/noframe", true, false, false, R.string.edge_lighting_style_noframe, R.string.edge_lighting_style_noframe_effect, R.drawable.noframe_effect_thumbnail, true));
        LinkedHashMap linkedHashMap2 = this.mStyleHashMap;
        boolean z = Feature.FEATURE_SUPPORT_BASIC_LIGHTING;
        linkedHashMap2.put("preload/basic", new EdgeLightingStyle("preload/basic", true, z, R.string.edge_lighting_style_basic, R.string.edge_lighting_style_basic_effect, R.drawable.basic_effect_thumbnail));
        LinkedHashMap linkedHashMap3 = this.mStyleHashMap;
        boolean z2 = Feature.FEATURE_SUPPORT_COCKTAIL_COLOR_PHONE_COLOR;
        linkedHashMap3.put("preload/wave", new EdgeLightingStyle("preload/wave", true, z, R.string.edge_lighting_style_wave, R.string.edge_lighting_style_wave_effect, R.drawable.wave_effect_thumbnail, z2));
        if (z) {
            this.mStyleHashMap.put("preload/bubble", new EdgeLightingStyle("preload/bubble", true, z, R.string.edge_lighting_style_bubble, R.string.edge_lighting_style_bubble_effect, R.drawable.bubble_effect_thumbnail, z2));
        } else {
            this.mStyleHashMap.put("preload/bubble", new EdgeLightingStyle("preload/bubble", true, z, R.string.edge_lighting_style_bubble, R.string.edge_lighting_style_bubble_effect, R.drawable.bubble_effect_thumbnail_without_line, z2));
        }
        this.mStyleHashMap.put("preload/gradation", new EdgeLightingStyle("preload/gradation", true, z, R.string.edge_lighting_style_multicolor, R.string.edge_lighting_style_multicolor_effect, R.drawable.gradation_effect_thumbnail));
        this.mStyleHashMap.put("preload/glow", new EdgeLightingStyle("preload/glow", true, z, R.string.edge_lighting_style_glow, R.string.edge_lighting_style_glow_effect, R.drawable.glow_effect_thumbnail));
        this.mStyleHashMap.put("preload/reflection", new EdgeLightingStyle("preload/reflection", true, z, R.string.edge_lighting_style_glitter, R.string.edge_lighting_style_glitter_effect, R.drawable.glitter_effect_thumbnail));
        this.mStyleHashMap.put("preload/heart", new EdgeLightingStyle("preload/heart", true, z, R.string.edge_lighting_style_heart, R.string.edge_lighting_style_heart_effect, z ? R.drawable.heart_effect_thumbnail : R.drawable.heart_effect_thumbnail_without_line, z2));
        this.mStyleHashMap.put("preload/fireworks", new EdgeLightingStyle("preload/fireworks", true, z, R.string.edge_lighting_style_fireworks, R.string.edge_lighting_style_fireworks_effect, z ? R.drawable.fireworks_effect_thumbnail : R.drawable.fireworks_effect_thumbnail_without_line, z2));
        String str = "preload/reflection";
        String str2 = "preload/gradation";
        String str3 = "preload/wave";
        this.mStyleHashMap.put("preload/eclipse", new EdgeLightingStyle("preload/eclipse", true, false, R.string.edge_lighting_style_eclipse, R.string.edge_lighting_style_eclipse_effect, R.drawable.eclipse_effect_thumbnail, z2));
        this.mStyleHashMap.put("preload/echo", new EdgeLightingStyle("preload/echo", true, false, R.string.edge_lighting_style_echo, R.string.edge_lighting_style_echo_effect, R.drawable.echo_effect_thumbnail, z2));
        this.mStyleHashMap.put("preload/spotlight", new EdgeLightingStyle("preload/spotlight", true, false, R.string.edge_lighting_style_spotlight, R.string.edge_lighting_style_spotlight_effect, R.drawable.spotlight_effect_thumbnail, z2));
        if (z) {
            return;
        }
        LinkedHashMap linkedHashMap4 = new LinkedHashMap();
        for (EdgeLightingStyle edgeLightingStyle : this.mStyleHashMap.values()) {
            String str4 = edgeLightingStyle.mKey;
            if (!"preload/basic".equals(str4)) {
                String str5 = str3;
                if (str5.equals(str4)) {
                    str3 = str5;
                } else {
                    String str6 = str2;
                    if (str6.equals(str4) || "preload/glow".equals(str4)) {
                        str3 = str5;
                        str2 = str6;
                    } else {
                        String str7 = str;
                        if (!str7.equals(str4)) {
                            linkedHashMap4.put(str4, edgeLightingStyle);
                        }
                        str3 = str5;
                        str2 = str6;
                        str = str7;
                    }
                }
            }
        }
        this.mStyleHashMap = linkedHashMap4;
    }

    public static EdgeLightingStyleManager getInstance() {
        if (mInstance == null) {
            mInstance = new EdgeLightingStyleManager();
        }
        return mInstance;
    }

    public static boolean isSupportEffectForRoutine(String str) {
        return ("preload/bubble".equals(str) || "preload/wave".equals(str) || "preload/heart".equals(str) || "preload/fireworks".equals(str) || "preload/eclipse".equals(str)) ? false : true;
    }

    public final EdgeLightingStyle getDefalutStyle() {
        return (EdgeLightingStyle) new ArrayList(this.mStyleHashMap.values()).get(0);
    }

    public final String getEdgeLightingStyleType(ContentResolver contentResolver) {
        int intForUser = Settings.System.getIntForUser(contentResolver, "edge_lighting_style_type", -1, -2);
        Slog.i("EdgeLightingStyleManager", "getEdgeLightingStyleType : " + intForUser);
        if (intForUser >= 0) {
            ArrayList arrayList = new ArrayList(this.mStyleHashMap.values());
            Settings.System.putIntForUser(contentResolver, "edge_lighting_style_type", -1, -2);
            if (intForUser != 0) {
                if (intForUser != 1) {
                    if (intForUser != 2) {
                        if (intForUser == 3) {
                            if (this.mStyleHashMap.containsKey("preload/reflection")) {
                                Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", "preload/reflection", -2);
                            } else if (arrayList.size() > 3) {
                                Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", ((EdgeLightingStyle) arrayList.get(3)).mKey, -2);
                            } else {
                                Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", ((EdgeLightingStyle) arrayList.get(0)).mKey, -2);
                            }
                        }
                    } else if (this.mStyleHashMap.containsKey("preload/glow")) {
                        Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", "preload/glow", -2);
                    } else if (arrayList.size() > 2) {
                        Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", ((EdgeLightingStyle) arrayList.get(2)).mKey, -2);
                    } else {
                        Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", ((EdgeLightingStyle) arrayList.get(0)).mKey, -2);
                    }
                } else if (this.mStyleHashMap.containsKey("preload/gradation")) {
                    Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", "preload/gradation", -2);
                } else if (arrayList.size() > 1) {
                    Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", ((EdgeLightingStyle) arrayList.get(1)).mKey, -2);
                } else {
                    Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", ((EdgeLightingStyle) arrayList.get(0)).mKey, -2);
                }
            } else if (this.mStyleHashMap.containsKey("preload/noframe")) {
                Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", "preload/noframe", -2);
            } else {
                Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", ((EdgeLightingStyle) arrayList.get(0)).mKey, -2);
            }
        }
        String stringForUser = Settings.System.getStringForUser(contentResolver, "edge_lighting_style_type_str", -2);
        if (stringForUser == null) {
            return this.mStyleHashMap.containsKey("preload/noframe") ? "preload/noframe" : ((EdgeLightingStyle) new ArrayList(this.mStyleHashMap.values()).get(0)).mKey;
        }
        if (stringForUser.split("/").length <= 2 && this.mStyleHashMap.containsKey(stringForUser)) {
            return stringForUser;
        }
        getInstance().getClass();
        Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", "preload/noframe", -2);
        return "preload/noframe";
    }

    public final int getPreloadIndex(String str) {
        if (!this.mStyleHashMap.containsKey(str)) {
            return 100;
        }
        if ("preload/noframe".equals(str)) {
            return 0;
        }
        if ("preload/basic".equals(str)) {
            return 1;
        }
        if ("preload/gradation".equals(str)) {
            return 2;
        }
        if ("preload/glow".equals(str)) {
            return 3;
        }
        if ("preload/reflection".equals(str)) {
            return 4;
        }
        if ("preload/wave".equals(str)) {
            return 5;
        }
        if ("preload/bubble".equals(str)) {
            return 6;
        }
        if ("preload/heart".equals(str)) {
            return 7;
        }
        if ("preload/fireworks".equals(str)) {
            return 8;
        }
        if ("preload/eclipse".equals(str)) {
            return 9;
        }
        if ("preload/echo".equals(str)) {
            return 10;
        }
        if ("preload/spotlight".equals(str)) {
            return 11;
        }
        return getPreloadIndex(((EdgeLightingStyle) new ArrayList(this.mStyleHashMap.values()).get(0)).mKey);
    }
}
