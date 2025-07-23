package androidx.navigation;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.navigation.Navigator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class NavigatorProvider {
    public static final Companion Companion = new Companion(null);
    public static final Map annotationNames = new LinkedHashMap();
    public final Map _navigators = new LinkedHashMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static String getNameForNavigator$navigation_common_release(Class cls) {
            Map map = NavigatorProvider.annotationNames;
            String str = (String) ((LinkedHashMap) map).get(cls);
            if (str == null) {
                Navigator.Name name = (Navigator.Name) cls.getAnnotation(Navigator.Name.class);
                str = name != null ? name.value() : null;
                if (str == null || str.length() <= 0) {
                    throw new IllegalArgumentException("No @Navigator.Name annotation found for ".concat(cls.getSimpleName()).toString());
                }
                map.put(cls, str);
            }
            str.getClass();
            return str;
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
