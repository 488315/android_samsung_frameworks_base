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
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SwitchPreference extends TwoStatePreference {
    public final DummyClickListener mClickListener;
    public int mIsLargeLayout;
    public final Listener mListener;
    public CharSequence mSwitchOff;
    public CharSequence mSwitchOn;
    public int mWidth;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DummyClickListener implements View.OnClickListener {
        private DummyClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            SwitchPreference.this.callClickListener();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Listener implements CompoundButton.OnCheckedChangeListener {
        public Listener() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (SwitchPreference.this.callChangeListener(Boolean.valueOf(z))) {
                SwitchPreference.this.setChecked(z);
            } else {
                compoundButton.setChecked(!z);
            }
        }
    }

    public SwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mListener = new Listener();
        this.mWidth = 0;
        this.mClickListener = new DummyClickListener();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SwitchPreference, i, i2);
        this.mIsLargeLayout = 0;
        this.mSummaryOn = TypedArrayUtils.getString(obtainStyledAttributes, 7, 0);
        if (this.mChecked) {
            notifyChanged();
        }
        this.mSummaryOff = TypedArrayUtils.getString(obtainStyledAttributes, 6, 1);
        if (!this.mChecked) {
            notifyChanged();
        }
        this.mSwitchOn = TypedArrayUtils.getString(obtainStyledAttributes, 9, 3);
        notifyChanged();
        this.mSwitchOff = TypedArrayUtils.getString(obtainStyledAttributes, 8, 4);
        notifyChanged();
        this.mDisableDependentsState = obtainStyledAttributes.getBoolean(5, obtainStyledAttributes.getBoolean(2, false));
        obtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mIsLargeLayout != 1) {
            syncSwitchView(preferenceViewHolder.findViewById(R.id.switch_widget));
        }
        syncSummaryView(preferenceViewHolder.findViewById(R.id.summary));
    }

    @Override // androidx.preference.Preference
    public final void performClick(View view) {
        performClick();
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
        if (accessibilityManager == null || accessibilityManager.isEnabled()) {
            if (this.mIsLargeLayout != 1) {
                syncSwitchView(view.findViewById(R.id.switch_widget));
            }
            if (isTalkBackIsRunning()) {
                return;
            }
            syncSummaryView(view.findViewById(R.id.summary));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void syncSwitchView(View view) {
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
            if (isTalkBackIsRunning()) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.setBackground(switchCompat, null);
                switchCompat.setClickable(false);
            }
        }
    }

    public SwitchPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SwitchPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(com.android.systemui.R.attr.switchPreferenceStyle, context, R.attr.switchPreferenceStyle));
    }

    public SwitchPreference(Context context) {
        this(context, null);
    }
}
