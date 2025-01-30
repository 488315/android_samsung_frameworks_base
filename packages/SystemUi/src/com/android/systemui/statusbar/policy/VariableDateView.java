package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.R$styleable;
import java.util.Date;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VariableDateView extends TextView {
    public final String longerPattern;
    public VariableDateViewController$onMeasureListener$1 onMeasureListener;
    public final String shorterPattern;

    public VariableDateView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.VariableDateView, 0, 0);
        String string = obtainStyledAttributes.getString(0);
        this.longerPattern = string == null ? context.getString(R.string.system_ui_date_pattern) : string;
        String string2 = obtainStyledAttributes.getString(1);
        this.shorterPattern = string2 == null ? context.getString(R.string.abbrev_month_day_no_year) : string2;
        obtainStyledAttributes.recycle();
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        VariableDateViewController$onMeasureListener$1 variableDateViewController$onMeasureListener$1;
        int size = (View.MeasureSpec.getSize(i) - getPaddingStart()) - getPaddingEnd();
        if (View.MeasureSpec.getMode(i) != 0 && (variableDateViewController$onMeasureListener$1 = this.onMeasureListener) != null) {
            VariableDateViewController variableDateViewController = variableDateViewController$onMeasureListener$1.this$0;
            if (size != variableDateViewController.lastWidth) {
                ((VariableDateView) variableDateViewController.mView).getClass();
                if ((size <= variableDateViewController.lastWidth || !Intrinsics.areEqual(variableDateViewController.datePattern, ((VariableDateView) variableDateViewController.mView).longerPattern)) && (size >= variableDateViewController.lastWidth || !Intrinsics.areEqual(variableDateViewController.datePattern, ""))) {
                    Date date = variableDateViewController.currentTime;
                    float f = size;
                    if (StaticLayout.getDesiredWidth(VariableDateViewControllerKt.getTextForFormat(date, VariableDateViewControllerKt.getFormatFromPattern(((VariableDateView) variableDateViewController.mView).longerPattern)), ((VariableDateView) variableDateViewController.mView).getPaint()) <= f) {
                        variableDateViewController.changePattern(((VariableDateView) variableDateViewController.mView).longerPattern);
                    } else if (StaticLayout.getDesiredWidth(VariableDateViewControllerKt.getTextForFormat(date, VariableDateViewControllerKt.getFormatFromPattern(((VariableDateView) variableDateViewController.mView).shorterPattern)), ((VariableDateView) variableDateViewController.mView).getPaint()) <= f) {
                        variableDateViewController.changePattern(((VariableDateView) variableDateViewController.mView).shorterPattern);
                    } else {
                        variableDateViewController.changePattern("");
                    }
                }
                variableDateViewController.lastWidth = size;
            }
        }
        super.onMeasure(i, i2);
    }
}
