package com.android.systemui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.plugins.DarkIconDispatcher;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DarkReceiverImpl extends View implements DarkIconDispatcher.DarkReceiver {
    public final DualToneHandler dualToneHandler;

    public DarkReceiverImpl(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        if (!DarkIconDispatcher.isInAreas(arrayList, this)) {
            f = 0.0f;
        }
        setBackgroundColor(this.dualToneHandler.getSingleColor(f));
    }

    public DarkReceiverImpl(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public DarkReceiverImpl(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ DarkReceiverImpl(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public DarkReceiverImpl(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.dualToneHandler = new DualToneHandler(context);
        onDarkChanged(new ArrayList(), 1.0f, -301989889);
    }
}
