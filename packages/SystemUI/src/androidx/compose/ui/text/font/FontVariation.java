package androidx.compose.ui.text.font;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FontVariation {
    public static final FontVariation INSTANCE = new FontVariation();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Setting {
        String getAxisName();

        float toVariationValue();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class SettingFloat implements Setting {
        public final String axisName;
        public final float value;

        public SettingFloat(String str, float f) {
            this.axisName = str;
            this.value = f;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SettingFloat)) {
                return false;
            }
            SettingFloat settingFloat = (SettingFloat) obj;
            return Intrinsics.areEqual(this.axisName, settingFloat.axisName) && this.value == settingFloat.value;
        }

        @Override // androidx.compose.ui.text.font.FontVariation.Setting
        public final String getAxisName() {
            return this.axisName;
        }

        public final int hashCode() {
            return Float.hashCode(this.value) + (this.axisName.hashCode() * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("FontVariation.Setting(axisName='");
            sb.append(this.axisName);
            sb.append("', value=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.value, ')');
        }

        @Override // androidx.compose.ui.text.font.FontVariation.Setting
        public final float toVariationValue() {
            return this.value;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class SettingInt implements Setting {
        public final String axisName;
        public final int value;

        public SettingInt(String str, int i) {
            this.axisName = str;
            this.value = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SettingInt)) {
                return false;
            }
            SettingInt settingInt = (SettingInt) obj;
            return Intrinsics.areEqual(this.axisName, settingInt.axisName) && this.value == settingInt.value;
        }

        @Override // androidx.compose.ui.text.font.FontVariation.Setting
        public final String getAxisName() {
            return this.axisName;
        }

        public final int hashCode() {
            return (this.axisName.hashCode() * 31) + this.value;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("FontVariation.Setting(axisName='");
            sb.append(this.axisName);
            sb.append("', value=");
            return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.value, ')');
        }

        @Override // androidx.compose.ui.text.font.FontVariation.Setting
        public final float toVariationValue() {
            return this.value;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Settings {
        public final List settings;

        public Settings(Setting... settingArr) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Setting setting : settingArr) {
                String axisName = setting.getAxisName();
                Object obj = linkedHashMap.get(axisName);
                if (obj == null) {
                    obj = new ArrayList();
                    linkedHashMap.put(axisName, obj);
                }
                ((List) obj).add(setting);
            }
            ArrayList arrayList = new ArrayList();
            for (Map.Entry entry : linkedHashMap.entrySet()) {
                String str = (String) entry.getKey();
                List list = (List) entry.getValue();
                if (list.size() != 1) {
                    throw new IllegalArgumentException(OpaqueKey$$ExternalSyntheticOutline0.m(ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("'", str, "' must be unique. Actual [ ["), CollectionsKt___CollectionsKt.joinToString$default(list, null, null, null, null, 63), ']').toString());
                }
                CollectionsKt__MutableCollectionsKt.addAll(list, arrayList);
            }
            ArrayList arrayList2 = new ArrayList(arrayList);
            this.settings = arrayList2;
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                ((Setting) arrayList2.get(i)).getClass();
            }
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Settings) && Intrinsics.areEqual(this.settings, ((Settings) obj).settings);
        }

        public final int hashCode() {
            return this.settings.hashCode();
        }
    }

    private FontVariation() {
    }
}
