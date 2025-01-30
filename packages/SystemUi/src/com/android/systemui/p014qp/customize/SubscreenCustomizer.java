package com.android.systemui.p014qp.customize;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.p014qp.SubscreenPagedTileLayout;
import com.android.systemui.p016qs.QSPanel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SubscreenCustomizer extends QSPanel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final HandlerC20131 mHandler;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MessageObjectAnim {
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qp.customize.SubscreenCustomizer$1] */
    public SubscreenCustomizer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHandler = new Handler(Looper.myLooper()) { // from class: com.android.systemui.qp.customize.SubscreenCustomizer.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("handleMessage() msg.what="), message.what, "SubscreenCustomizer");
                SubscreenCustomizer subscreenCustomizer = SubscreenCustomizer.this;
                int i = SubscreenCustomizer.$r8$clinit;
                SubscreenPagedTileLayout subscreenPagedTileLayout = (SubscreenPagedTileLayout) subscreenCustomizer.mTileLayout;
                switch (message.what) {
                    case 100:
                    case 101:
                    case 102:
                        MessageObjectAnim messageObjectAnim = (MessageObjectAnim) message.obj;
                        subscreenPagedTileLayout.getCurrentItem();
                        messageObjectAnim.getClass();
                        throw null;
                    default:
                        return;
                }
            }
        };
        new View.OnDragListener() { // from class: com.android.systemui.qp.customize.SubscreenCustomizer.2
            @Override // android.view.View.OnDragListener
            public final boolean onDrag(View view, DragEvent dragEvent) {
                SubscreenCustomizer subscreenCustomizer = SubscreenCustomizer.this;
                int i = SubscreenCustomizer.$r8$clinit;
                subscreenCustomizer.getClass();
                throw null;
            }
        };
        Log.d("SubscreenCustomizer", "SubscreenCustomizer");
        boolean z = QpRune.QUICK_PANEL_BLUR_DEFAULT;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // com.android.systemui.p016qs.QSPanel, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        Log.d("SubscreenCustomizer", "onFinishInflate");
    }
}
