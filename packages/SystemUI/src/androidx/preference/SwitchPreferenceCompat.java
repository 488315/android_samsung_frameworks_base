package androidx.preference;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Checkable;
import android.widget.CompoundButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SwitchPreferenceCompat extends TwoStatePreference {
    public final DummyClickListener mClickListener;
    public int mIsLargeLayout;
    public final Listener mListener;
    public final CharSequence mSwitchOff;
    public final CharSequence mSwitchOn;
    public int mWidth;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DummyClickListener implements View.OnClickListener {
        private DummyClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            SwitchPreferenceCompat.this.callClickListener();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Listener implements CompoundButton.OnCheckedChangeListener {
        public Listener() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (SwitchPreferenceCompat.this.callChangeListener(Boolean.valueOf(z))) {
                SwitchPreferenceCompat.this.setChecked(z);
            } else {
                compoundButton.setChecked(!z);
            }
        }
    }

    public SwitchPreferenceCompat(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mListener = new Listener();
        this.mWidth = 0;
        this.mClickListener = new DummyClickListener();
        this.mIsLargeLayout = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SwitchPreferenceCompat, i, i2);
        String string = obtainStyledAttributes.getString(7);
        this.mSummaryOn = string == null ? obtainStyledAttributes.getString(0) : string;
        if (this.mChecked) {
            notifyChanged();
        }
        String string2 = obtainStyledAttributes.getString(6);
        this.mSummaryOff = string2 == null ? obtainStyledAttributes.getString(1) : string2;
        if (!this.mChecked) {
            notifyChanged();
        }
        String string3 = obtainStyledAttributes.getString(9);
        this.mSwitchOn = string3 == null ? obtainStyledAttributes.getString(3) : string3;
        notifyChanged();
        String string4 = obtainStyledAttributes.getString(8);
        this.mSwitchOff = string4 == null ? obtainStyledAttributes.getString(4) : string4;
        notifyChanged();
        this.mDisableDependentsState = obtainStyledAttributes.getBoolean(5, obtainStyledAttributes.getBoolean(2, false));
        obtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mIsLargeLayout != 1) {
            syncSwitchView$1(preferenceViewHolder.findViewById(R.id.switch_widget));
        }
        syncSummaryView(preferenceViewHolder.findViewById(R.id.summary));
    }

    @Override // androidx.preference.Preference
    public final void performClick(View view) {
        performClick();
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
        if (accessibilityManager == null || accessibilityManager.isEnabled()) {
            if (this.mIsLargeLayout != 1) {
                syncSwitchView$1(view.findViewById(R.id.switch_widget));
            }
            if (isTalkBackIsRunning$1()) {
                return;
            }
            syncSummaryView(view.findViewById(R.id.summary));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void syncSwitchView$1(View view) {
        boolean z = view instanceof SwitchCompat;
        if (z) {
            ((SwitchCompat) view).setOnCheckedChangeListener(null);
        }
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(this.mChecked);
        }
        if (z) {
            SwitchCompat switchCompat = (SwitchCompat) view;
            switchCompat.setTextOnInternal(this.mSwitchOn);
            switchCompat.requestLayout();
            if (switchCompat.isChecked()) {
                switchCompat.setOnStateDescriptionOnRAndAbove();
            }
            switchCompat.setTextOffInternal(this.mSwitchOff);
            switchCompat.requestLayout();
            if (!switchCompat.isChecked()) {
                switchCompat.setOffStateDescriptionOnRAndAbove();
            }
            switchCompat.setOnCheckedChangeListener(this.mListener);
            if (switchCompat.isClickable()) {
                switchCompat.setOnClickListener(this.mClickListener);
            }
            if (!isTalkBackIsRunning$1() || (this instanceof SeslSwitchPreferenceScreen)) {
                return;
            }
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            switchCompat.setBackground(null);
            switchCompat.setClickable(false);
        }
    }

    public SwitchPreferenceCompat(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SwitchPreferenceCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.switchPreferenceCompatStyle);
    }

    public SwitchPreferenceCompat(Context context) {
        this(context, null);
    }
}
