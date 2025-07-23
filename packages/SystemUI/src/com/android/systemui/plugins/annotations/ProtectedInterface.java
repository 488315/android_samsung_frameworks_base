package com.android.systemui.plugins.annotations;

import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes2.dex */
public @interface ProtectedInterface {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final ProtectedInterface Default;

        static {
            final String[] strArr = {"java.lang.Exception", "java.lang.LinkageError"};
            Default = new ProtectedInterface() { // from class: com.android.systemui.plugins.annotations.ProtectedInterface$Companion$annotationImpl$com_android_systemui_plugins_annotations_ProtectedInterface$0
                @Override // java.lang.annotation.Annotation
                public final /* synthetic */ Class annotationType() {
                    return ProtectedInterface.class;
                }

                @Override // java.lang.annotation.Annotation
                public final boolean equals(Object obj) {
                    return (obj instanceof ProtectedInterface) && Arrays.equals(exTypes(), ((ProtectedInterface) obj).exTypes());
                }

                @Override // com.android.systemui.plugins.annotations.ProtectedInterface
                public final /* synthetic */ String[] exTypes() {
                    return strArr;
                }

                @Override // java.lang.annotation.Annotation
                public final int hashCode() {
                    return Arrays.hashCode(strArr) ^ (-1992921222);
                }

                @Override // java.lang.annotation.Annotation
                public final String toString() {
                    return OpaqueKey$$ExternalSyntheticOutline0.m(new StringBuilder("@com.android.systemui.plugins.annotations.ProtectedInterface(exTypes="), Arrays.toString(strArr), ')');
                }
            };
        }

        private Companion() {
        }

        public final ProtectedInterface getDefault() {
            return Default;
        }
    }

    String[] exTypes();
}
