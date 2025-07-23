package com.google.android.material.timepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
class TimePickerView extends ConstraintLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Chip hourView;
    public final AnonymousClass1 selectionListener;

    public TimePickerView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (view == this && i == 0) {
            this.hourView.sendAccessibilityEvent(8);
        }
    }

    public TimePickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1, types: [android.view.View$OnClickListener, com.google.android.material.timepicker.TimePickerView$1] */
    public TimePickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ?? r5 = new View.OnClickListener() { // from class: com.google.android.material.timepicker.TimePickerView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (view != null) {
                    TimePickerView timePickerView = TimePickerView.this;
                    int i2 = TimePickerView.$r8$clinit;
                    timePickerView.getClass();
                }
            }
        };
        this.selectionListener = r5;
        LayoutInflater.from(context).inflate(R.layout.material_timepicker, this);
        MaterialButtonToggleGroup materialButtonToggleGroup = (MaterialButtonToggleGroup) findViewById(R.id.material_clock_period_toggle);
        materialButtonToggleGroup.onButtonCheckedListeners.add(new TimePickerView$$ExternalSyntheticLambda0(this));
        Chip chip = (Chip) findViewById(R.id.material_minute_tv);
        Chip chip2 = (Chip) findViewById(R.id.material_hour_tv);
        this.hourView = chip2;
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.google.android.material.timepicker.TimePickerView.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public final boolean onDoubleTap(MotionEvent motionEvent) {
                TimePickerView timePickerView = TimePickerView.this;
                int i2 = TimePickerView.$r8$clinit;
                timePickerView.getClass();
                return false;
            }
        });
        View.OnTouchListener onTouchListener = new View.OnTouchListener(this) { // from class: com.google.android.material.timepicker.TimePickerView.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (((Checkable) view).isChecked()) {
                    return gestureDetector.onTouchEvent(motionEvent);
                }
                return false;
            }
        };
        chip.setOnTouchListener(onTouchListener);
        chip2.setOnTouchListener(onTouchListener);
        chip.setTag(R.id.selection_type, 12);
        chip2.setTag(R.id.selection_type, 10);
        chip.setOnClickListener(r5);
        chip2.setOnClickListener(r5);
        chip.accessibilityClassName = "android.view.View";
        chip2.accessibilityClassName = "android.view.View";
    }
}
