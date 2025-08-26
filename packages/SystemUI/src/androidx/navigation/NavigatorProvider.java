package androidx.navigation;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.navigation.Navigator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public class NavigatorProvider {
    public static final Companion Companion = new Companion(null);
    public static final Map annotationNames = new LinkedHashMap();
    public final Map _navigators = new LinkedHashMap();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static String getNameForNavigator$navigation_common_release(Class cls) {
            Map map = NavigatorProvider.annotationNames;
            String strValue = (String) ((LinkedHashMap) map).get(cls);
            if (strValue == null) {
                Navigator.Name name = (Navigator.Name) cls.getAnnotation(Navigator.Name.class);
                strValue = name != null ? name.value() : null;
                if (strValue == null || strValue.length() <= 0) {
                    throw new IllegalArgumentException("No @Navigator.Name annotation found for ".concat(cls.getSimpleName()).toString());
                }
                map.put(cls, strValue);
            }
            strValue.getClass();
            return strValue;
        }

        private Companion() {
        }
    }

    public final void addNavigator(Navigator navigator) {
        Class<?> cls = navigator.getClass();
        Companion.getClass();
        String nameForNavigator$navigation_common_release = Companion.getNameForNavigator$navigation_common_release(cls);
        if (nameForNavigator$navigation_common_release.length() <= 0) {
            throw new IllegalArgumentException("navigator name cannot be an empty string");
        }
        Navigator navigator2 = (Navigator) ((LinkedHashMap) this._navigators).get(nameForNavigator$navigation_common_release);
        if (Intrinsics.areEqual(navigator2, navigator)) {
            return;
        }
        boolean z = false;
        if (navigator2 != null && navigator2.isAttached) {
            z = true;
        }
        if (z) {
            throw new IllegalStateException(("Navigator " + navigator + " is replacing an already attached " + navigator2).toString());
        }
        if (!navigator.isAttached) {
            return;
        }
        throw new IllegalStateException(("Navigator " + navigator + " is already attached to another NavController").toString());
    }

    public final Navigator getNavigator(Class cls) {
        Companion.getClass();
        return getNavigator(Companion.getNameForNavigator$navigation_common_release(cls));
    }

    public final Navigator getNavigator(String str) {
        Companion.getClass();
        if (str != null && str.length() > 0) {
            Navigator navigator = (Navigator) ((LinkedHashMap) this._navigators).get(str);
            if (navigator != null) {
                return navigator;
            }
            throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Could not find Navigator with name \"", str, "\". You must call NavController.addNavigator() for each navigation type."));
        }
        throw new IllegalArgumentException("navigator name cannot be an empty string");
    }
}
