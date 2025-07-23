package androidx.window.area.reflectionguard;

import android.app.Activity;
import android.util.DisplayMetrics;
import androidx.window.extensions.area.ExtensionWindowAreaPresentation;
import androidx.window.extensions.area.ExtensionWindowAreaStatus;
import androidx.window.extensions.core.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface WindowAreaComponentApi3Requirements {
    void addRearDisplayPresentationStatusListener(Consumer<ExtensionWindowAreaStatus> consumer);

    void addRearDisplayStatusListener(Consumer<Integer> consumer);

    void endRearDisplayPresentationSession();

    void endRearDisplaySession();

    DisplayMetrics getRearDisplayMetrics();

    ExtensionWindowAreaPresentation getRearDisplayPresentation();

    void removeRearDisplayPresentationStatusListener(Consumer<ExtensionWindowAreaStatus> consumer);

    void removeRearDisplayStatusListener(Consumer<Integer> consumer);

    void startRearDisplayPresentationSession(Activity activity, Consumer<Integer> consumer);

    void startRearDisplaySession(Activity activity, Consumer<Integer> consumer);
}
