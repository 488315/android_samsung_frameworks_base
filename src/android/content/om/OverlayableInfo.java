package android.content.om;

import android.annotation.NonNull;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

/* loaded from: classes.dex */
public final class OverlayableInfo {
    public final String actor;
    public final String name;

    @Deprecated
    private void __metadata() {
    }

    public OverlayableInfo(String str, String str2) {
        this.name = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.actor = str2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            OverlayableInfo overlayableInfo = (OverlayableInfo) obj;
            if (Objects.equals(this.name, overlayableInfo.name) && Objects.equals(this.actor, overlayableInfo.actor)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((Objects.hashCode(this.name) + 31) * 31) + Objects.hashCode(this.actor);
    }
}
