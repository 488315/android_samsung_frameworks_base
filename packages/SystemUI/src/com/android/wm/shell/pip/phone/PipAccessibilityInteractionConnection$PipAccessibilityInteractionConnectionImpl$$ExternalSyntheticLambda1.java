package com.android.wm.shell.pip.phone;

import android.graphics.Region;
import android.os.RemoteException;
import android.view.MagnificationSpec;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl f$0;
    public final /* synthetic */ Region f$3;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ IAccessibilityInteractionConnectionCallback f$5;

    public /* synthetic */ PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl$$ExternalSyntheticLambda1(PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl, long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, int i5) {
        this.$r8$classId = i5;
        this.f$0 = pipAccessibilityInteractionConnectionImpl;
        this.f$3 = region;
        this.f$4 = i2;
        this.f$5 = iAccessibilityInteractionConnectionCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl = this.f$0;
                int i = this.f$4;
                IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = this.f$5;
                pipAccessibilityInteractionConnectionImpl.this$0.getClass();
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfoResult((AccessibilityNodeInfo) null, i);
                    break;
                } catch (RemoteException unused) {
                    return;
                }
            case 1:
                PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl2 = this.f$0;
                int i2 = this.f$4;
                IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback2 = this.f$5;
                pipAccessibilityInteractionConnectionImpl2.this$0.getClass();
                try {
                    iAccessibilityInteractionConnectionCallback2.setFindAccessibilityNodeInfoResult((AccessibilityNodeInfo) null, i2);
                    break;
                } catch (RemoteException unused2) {
                    return;
                }
            case 2:
                PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl3 = this.f$0;
                int i3 = this.f$4;
                IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback3 = this.f$5;
                pipAccessibilityInteractionConnectionImpl3.this$0.getClass();
                try {
                    iAccessibilityInteractionConnectionCallback3.setFindAccessibilityNodeInfoResult((AccessibilityNodeInfo) null, i3);
                    break;
                } catch (RemoteException unused3) {
                    return;
                }
            default:
                PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl4 = this.f$0;
                int i4 = this.f$4;
                IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback4 = this.f$5;
                pipAccessibilityInteractionConnectionImpl4.this$0.getClass();
                try {
                    iAccessibilityInteractionConnectionCallback4.setFindAccessibilityNodeInfoResult((AccessibilityNodeInfo) null, i4);
                    break;
                } catch (RemoteException unused4) {
                    return;
                }
        }
    }

    public /* synthetic */ PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl$$ExternalSyntheticLambda1(PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl, long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, int i4) {
        this.$r8$classId = i4;
        this.f$0 = pipAccessibilityInteractionConnectionImpl;
        this.f$3 = region;
        this.f$4 = i;
        this.f$5 = iAccessibilityInteractionConnectionCallback;
    }
}
