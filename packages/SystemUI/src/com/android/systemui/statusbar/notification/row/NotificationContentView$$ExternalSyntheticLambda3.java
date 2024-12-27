package com.android.systemui.statusbar.notification.row;

import android.util.IndentingPrintWriter;
import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationContentView$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ IndentingPrintWriter f$1;

    public /* synthetic */ NotificationContentView$$ExternalSyntheticLambda3(IndentingPrintWriter indentingPrintWriter, View view) {
        this.f$1 = indentingPrintWriter;
        this.f$0 = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationContentView notificationContentView = (NotificationContentView) this.f$0;
                IndentingPrintWriter indentingPrintWriter = this.f$1;
                View view = notificationContentView.mContractedChild;
                if (view != null) {
                    NotificationContentView.dumpChildViewDimensions(indentingPrintWriter, view, "Contracted Child:");
                    indentingPrintWriter.println();
                }
                View view2 = notificationContentView.mExpandedChild;
                if (view2 != null) {
                    NotificationContentView.dumpChildViewDimensions(indentingPrintWriter, view2, "Expanded Child:");
                    indentingPrintWriter.println();
                }
                View view3 = notificationContentView.mHeadsUpChild;
                if (view3 != null) {
                    NotificationContentView.dumpChildViewDimensions(indentingPrintWriter, view3, "HeadsUp Child:");
                    indentingPrintWriter.println();
                }
                HybridNotificationView hybridNotificationView = notificationContentView.mSingleLineView;
                if (hybridNotificationView != null) {
                    NotificationContentView.dumpChildViewDimensions(indentingPrintWriter, hybridNotificationView, "Single Line  View:");
                    indentingPrintWriter.println();
                    break;
                }
                break;
            default:
                IndentingPrintWriter indentingPrintWriter2 = this.f$1;
                View view4 = (View) this.f$0;
                int i = NotificationContentView.$r8$clinit;
                indentingPrintWriter2.print("width", Integer.valueOf(view4.getWidth()));
                indentingPrintWriter2.print("height", Integer.valueOf(view4.getHeight()));
                indentingPrintWriter2.print("measuredWidth", Integer.valueOf(view4.getMeasuredWidth()));
                indentingPrintWriter2.print("measuredHeight", Integer.valueOf(view4.getMeasuredHeight()));
                break;
        }
    }

    public /* synthetic */ NotificationContentView$$ExternalSyntheticLambda3(NotificationContentView notificationContentView, IndentingPrintWriter indentingPrintWriter) {
        this.f$0 = notificationContentView;
        this.f$1 = indentingPrintWriter;
    }
}
