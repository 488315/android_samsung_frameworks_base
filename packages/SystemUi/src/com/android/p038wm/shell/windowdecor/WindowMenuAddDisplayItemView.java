package com.android.p038wm.shell.windowdecor;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.widget.LinearLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class WindowMenuAddDisplayItemView extends LinearLayout {
    public WindowMenuAddDisplayItemView(Context context, AttributeSet attributeSet) {
        super(new ContextThemeWrapper(context, R.style.Theme.DeviceDefault.DayNight), attributeSet);
        setTooltipNull(true);
        setClickable(true);
    }
}
