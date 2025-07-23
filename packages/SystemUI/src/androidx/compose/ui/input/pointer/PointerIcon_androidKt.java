package androidx.compose.ui.input.pointer;

import com.samsung.android.knox.EnterpriseContainerCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PointerIcon_androidKt {
    public static final AndroidPointerIconType pointerIconCrosshair = null;
    public static final AndroidPointerIconType pointerIconDefault = new AndroidPointerIconType(1000);
    public static final AndroidPointerIconType pointerIconHand;
    public static final AndroidPointerIconType pointerIconText;

    static {
        new AndroidPointerIconType(1007);
        pointerIconText = new AndroidPointerIconType(EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS);
        pointerIconHand = new AndroidPointerIconType(1002);
    }
}
