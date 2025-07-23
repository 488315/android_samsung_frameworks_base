package android.view.accessibility;

import android.accessibilityservice.IAccessibilityServiceConnection;
import android.graphics.Region;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.view.accessibility.IAccessibilityManager;

/* loaded from: classes4.dex */
class DirectAccessibilityConnection extends IAccessibilityServiceConnection.Default {
    private static final int FETCH_FLAGS = 384;
    private static final Region INTERACTIVE_REGION = null;
    private final IAccessibilityInteractionConnection mAccessibilityInteractionConnection;
    private final AccessibilityManager mAccessibilityManager;
    private final int mMyProcessId = Process.myPid();

    DirectAccessibilityConnection(IAccessibilityInteractionConnection iAccessibilityInteractionConnection, AccessibilityManager accessibilityManager) {
        this.mAccessibilityInteractionConnection = iAccessibilityInteractionConnection;
        this.mAccessibilityManager = accessibilityManager;
    }

    @Override // android.accessibilityservice.IAccessibilityServiceConnection.Default, android.accessibilityservice.IAccessibilityServiceConnection
    public String[] findAccessibilityNodeInfoByAccessibilityId(int i, long j, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, long j2, Bundle bundle) throws RemoteException {
        IAccessibilityManager.WindowTransformationSpec windowTransformationSpec = this.mAccessibilityManager.getWindowTransformationSpec(i);
        this.mAccessibilityInteractionConnection.findAccessibilityNodeInfoByAccessibilityId(j, INTERACTIVE_REGION, i2, iAccessibilityInteractionConnectionCallback, 384, this.mMyProcessId, j2, windowTransformationSpec.magnificationSpec, windowTransformationSpec.transformationMatrix, bundle);
        return new String[0];
    }

    @Override // android.accessibilityservice.IAccessibilityServiceConnection.Default, android.accessibilityservice.IAccessibilityServiceConnection
    public String[] findAccessibilityNodeInfosByText(int i, long j, String str, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
        IAccessibilityManager.WindowTransformationSpec windowTransformationSpec = this.mAccessibilityManager.getWindowTransformationSpec(i);
        this.mAccessibilityInteractionConnection.findAccessibilityNodeInfosByText(j, str, INTERACTIVE_REGION, i2, iAccessibilityInteractionConnectionCallback, 384, this.mMyProcessId, j2, windowTransformationSpec.magnificationSpec, windowTransformationSpec.transformationMatrix);
        return new String[0];
    }

    @Override // android.accessibilityservice.IAccessibilityServiceConnection.Default, android.accessibilityservice.IAccessibilityServiceConnection
    public String[] findAccessibilityNodeInfosByViewId(int i, long j, String str, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
        IAccessibilityManager.WindowTransformationSpec windowTransformationSpec = this.mAccessibilityManager.getWindowTransformationSpec(i);
        this.mAccessibilityInteractionConnection.findAccessibilityNodeInfosByViewId(j, str, INTERACTIVE_REGION, i2, iAccessibilityInteractionConnectionCallback, 384, this.mMyProcessId, j2, windowTransformationSpec.magnificationSpec, windowTransformationSpec.transformationMatrix);
        return new String[0];
    }

    @Override // android.accessibilityservice.IAccessibilityServiceConnection.Default, android.accessibilityservice.IAccessibilityServiceConnection
    public String[] findFocus(int i, long j, int i2, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
        IAccessibilityManager.WindowTransformationSpec windowTransformationSpec = this.mAccessibilityManager.getWindowTransformationSpec(i);
        this.mAccessibilityInteractionConnection.findFocus(j, i2, INTERACTIVE_REGION, i3, iAccessibilityInteractionConnectionCallback, 384, this.mMyProcessId, j2, windowTransformationSpec.magnificationSpec, windowTransformationSpec.transformationMatrix);
        return new String[0];
    }

    @Override // android.accessibilityservice.IAccessibilityServiceConnection.Default, android.accessibilityservice.IAccessibilityServiceConnection
    public String[] focusSearch(int i, long j, int i2, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
        IAccessibilityManager.WindowTransformationSpec windowTransformationSpec = this.mAccessibilityManager.getWindowTransformationSpec(i);
        this.mAccessibilityInteractionConnection.focusSearch(j, i2, INTERACTIVE_REGION, i3, iAccessibilityInteractionConnectionCallback, 384, this.mMyProcessId, j2, windowTransformationSpec.magnificationSpec, windowTransformationSpec.transformationMatrix);
        return new String[0];
    }

    @Override // android.accessibilityservice.IAccessibilityServiceConnection.Default, android.accessibilityservice.IAccessibilityServiceConnection
    public boolean performAccessibilityAction(int i, long j, int i2, Bundle bundle, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
        this.mAccessibilityInteractionConnection.performAccessibilityAction(j, i2, bundle, i3, iAccessibilityInteractionConnectionCallback, 384, this.mMyProcessId, j2);
        return true;
    }
}
