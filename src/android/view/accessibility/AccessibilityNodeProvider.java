package android.view.accessibility;

import android.os.Bundle;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class AccessibilityNodeProvider {
    public static final int HOST_VIEW_ID = -1;

    public void addExtraDataToAccessibilityNodeInfo(int i, AccessibilityNodeInfo accessibilityNodeInfo, String str, Bundle bundle) {
    }

    public AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
        return null;
    }

    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String str, int i) {
        return null;
    }

    public AccessibilityNodeInfo findFocus(int i) {
        return null;
    }

    public boolean performAction(int i, int i2, Bundle bundle) {
        return false;
    }
}
