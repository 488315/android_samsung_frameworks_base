package com.android.wm.shell.pip.phone;

import android.graphics.Region;
import android.os.RemoteException;
import android.view.MagnificationSpec;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC4050xdda2519d implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl f$0;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ IAccessibilityInteractionConnectionCallback f$5;

    public /* synthetic */ RunnableC4050xdda2519d(PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl, long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, int i5) {
        this.$r8$classId = i5;
        this.f$0 = pipAccessibilityInteractionConnectionImpl;
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
            default:
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
        }
    }
}
