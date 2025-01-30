package com.android.p038wm.shell.pip.phone;

import android.graphics.Region;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.MagnificationSpec;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import com.android.p038wm.shell.pip.phone.PipAccessibilityInteractionConnection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl$$ExternalSyntheticLambda2 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC4052xdda2519f implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ IAccessibilityInteractionConnectionCallback f$5;

    public /* synthetic */ RunnableC4052xdda2519f(PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl, long j, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, Bundle bundle) {
        this.$r8$classId = 2;
        this.f$0 = pipAccessibilityInteractionConnectionImpl;
        this.f$1 = j;
        this.f$4 = i;
        this.f$5 = iAccessibilityInteractionConnectionCallback;
        this.f$2 = bundle;
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
            default:
                PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl3 = this.f$0;
                long j = this.f$1;
                int i3 = this.f$4;
                IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback3 = this.f$5;
                PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection = pipAccessibilityInteractionConnectionImpl3.this$0;
                pipAccessibilityInteractionConnection.getClass();
                try {
                    iAccessibilityInteractionConnectionCallback3.setFindAccessibilityNodeInfosResult(j == AccessibilityNodeInfo.ROOT_NODE_ID ? pipAccessibilityInteractionConnection.getNodeList() : null, i3);
                    break;
                } catch (RemoteException unused3) {
                    return;
                }
        }
    }

    public /* synthetic */ RunnableC4052xdda2519f(PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl, long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, int i4) {
        this.$r8$classId = i4;
        this.f$0 = pipAccessibilityInteractionConnectionImpl;
        this.f$1 = j;
        this.f$2 = str;
        this.f$4 = i;
        this.f$5 = iAccessibilityInteractionConnectionCallback;
    }
}
