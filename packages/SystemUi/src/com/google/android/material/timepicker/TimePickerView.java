package com.google.android.material.timepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.HashMap;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
class TimePickerView extends ConstraintLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ViewOnClickListenerC44001 selectionListener;
    public final MaterialButtonToggleGroup toggle;

    public TimePickerView(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateToggleConstraints();
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (view == this && i == 0) {
            updateToggleConstraints();
        }
    }

    public final void updateToggleConstraints() {
        ConstraintSet.Constraint constraint;
        if (this.toggle.getVisibility() == 0) {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this);
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            char c = ViewCompat.Api17Impl.getLayoutDirection(this) == 0 ? (char) 2 : (char) 1;
            HashMap hashMap = constraintSet.mConstraints;
            if (hashMap.containsKey(Integer.valueOf(R.id.material_clock_display)) && (constraint = (ConstraintSet.Constraint) hashMap.get(Integer.valueOf(R.id.material_clock_display))) != null) {
                ConstraintSet.Layout layout = constraint.layout;
                switch (c) {
                    case 1:
                        layout.leftToRight = -1;
                        layout.leftToLeft = -1;
                        layout.leftMargin = -1;
                        layout.goneLeftMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
                        break;
                    case 2:
                        layout.rightToRight = -1;
                        layout.rightToLeft = -1;
                        layout.rightMargin = -1;
                        layout.goneRightMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
                        break;
                    case 3:
                        layout.topToBottom = -1;
                        layout.topToTop = -1;
                        layout.topMargin = 0;
                        layout.goneTopMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
                        break;
                    case 4:
                        layout.bottomToTop = -1;
                        layout.bottomToBottom = -1;
                        layout.bottomMargin = 0;
                        layout.goneBottomMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
                        break;
                    case 5:
                        layout.baselineToBaseline = -1;
                        layout.baselineToTop = -1;
                        layout.baselineToBottom = -1;
                        layout.baselineMargin = 0;
                        layout.goneBaselineMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
                        break;
                    case 6:
                        layout.startToEnd = -1;
                        layout.startToStart = -1;
                        layout.startMargin = 0;
                        layout.goneStartMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
                        break;
                    case 7:
                        layout.endToStart = -1;
                        layout.endToEnd = -1;
                        layout.endMargin = 0;
                        layout.goneEndMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
                        break;
                    case '\b':
                        layout.circleAngle = -1.0f;
                        layout.circleRadius = -1;
                        layout.circleConstraint = -1;
                        break;
                    default:
                        throw new IllegalArgumentException("unknown constraint");
                }
            }
            constraintSet.applyTo(this);
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
        this.toggle = materialButtonToggleGroup;
        materialButtonToggleGroup.onButtonCheckedListeners.add(new TimePickerView$$ExternalSyntheticLambda0(this));
        Chip chip = (Chip) findViewById(R.id.material_minute_tv);
        Chip chip2 = (Chip) findViewById(R.id.material_hour_tv);
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
