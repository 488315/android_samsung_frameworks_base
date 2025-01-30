package androidx.appcompat.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslMisc;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SeslSwitchBar extends LinearLayout implements CompoundButton.OnCheckedChangeListener {
    public final LinearLayout mBackground;
    public final int mBackgroundActivatedColor;
    public final int mBackgroundColor;
    public final SwitchBarDelegate mDelegate;
    public String mLabel;
    public final int mOffTextColor;
    public int mOffTextId;
    public final int mOnTextColor;
    public int mOnTextId;
    public final SeslToggleSwitch mSwitch;
    public final List mSwitchChangeListeners;
    public final TextView mTextView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.appcompat.widget.SeslSwitchBar$2 */
    public final class C00952 {
        public C00952() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: androidx.appcompat.widget.SeslSwitchBar.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean checked;
        public boolean visible;

        public final String toString() {
            StringBuilder sb = new StringBuilder("SeslSwitchBar.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" checked=");
            sb.append(this.checked);
            sb.append(" visible=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.visible, "}");
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Boolean.valueOf(this.checked));
            parcel.writeValue(Boolean.valueOf(this.visible));
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.checked = ((Boolean) parcel.readValue(null)).booleanValue();
            this.visible = ((Boolean) parcel.readValue(null)).booleanValue();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SwitchBarDelegate extends AccessibilityDelegateCompat {
        public String mSessionName = "";
        public final SeslToggleSwitch mSwitch;
        public final TextView mText;

        public SwitchBarDelegate(View view) {
            this.mText = (TextView) view.findViewById(R.id.sesl_switchbar_text);
            this.mSwitch = (SeslToggleSwitch) view.findViewById(R.id.sesl_switchbar_switch);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
            this.mSwitch.setContentDescription(this.mText.getText());
            if (TextUtils.isEmpty(this.mSessionName)) {
                return;
            }
            accessibilityNodeInfoCompat.setText(this.mSessionName);
        }
    }

    public SeslSwitchBar(Context context) {
        this(context, null);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return SeslSwitchBar.class.getName();
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        int size = ((ArrayList) this.mSwitchChangeListeners).size();
        for (int i = 0; i < size; i++) {
            SeslSwitchBar.this.setTextViewLabelAndBackground(z);
        }
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSwitch.setCheckedInternal(savedState.checked);
        setTextViewLabelAndBackground(savedState.checked);
        setVisibility(savedState.visible ? 0 : 8);
        this.mSwitch.setOnCheckedChangeListener(savedState.visible ? this : null);
        requestLayout();
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checked = this.mSwitch.isChecked();
        savedState.visible = getVisibility() == 0;
        return savedState;
    }

    @Override // android.view.View
    public final boolean performClick() {
        return this.mSwitch.performClick();
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mTextView.setEnabled(z);
        this.mSwitch.setEnabled(z);
        this.mBackground.setEnabled(z);
        setTextViewLabelAndBackground(this.mSwitch.isChecked());
    }

    public final void setTextViewLabelAndBackground(boolean z) {
        this.mLabel = getResources().getString(z ? this.mOnTextId : this.mOffTextId);
        this.mBackground.getBackground().mutate().setTintList(ColorStateList.valueOf(z ? this.mBackgroundActivatedColor : this.mBackgroundColor));
        this.mTextView.setTextColor(z ? this.mOnTextColor : this.mOffTextColor);
        if (isEnabled()) {
            this.mTextView.setAlpha(1.0f);
        } else if (SeslMisc.isLightTheme(getContext()) && z) {
            this.mTextView.setAlpha(0.55f);
        } else {
            this.mTextView.setAlpha(0.4f);
        }
        String str = this.mLabel;
        if (str == null || !str.contentEquals(this.mTextView.getText())) {
            this.mTextView.setText(this.mLabel);
        }
    }

    public SeslSwitchBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.seslSwitchBarStyle);
    }

    public SeslSwitchBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslSwitchBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        String str;
        ArrayList arrayList = new ArrayList();
        this.mSwitchChangeListeners = arrayList;
        LayoutInflater.from(context).inflate(R.layout.sesl_switchbar, this);
        Resources resources = getResources();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SeslSwitchBar, i, i2);
        this.mBackgroundColor = obtainStyledAttributes.getColor(1, resources.getColor(R.color.sesl_switchbar_off_background_color_light));
        this.mBackgroundActivatedColor = obtainStyledAttributes.getColor(0, resources.getColor(R.color.sesl_switchbar_on_background_color_light));
        this.mOnTextColor = obtainStyledAttributes.getColor(2, resources.getColor(R.color.sesl_switchbar_on_text_color_light));
        this.mOffTextColor = obtainStyledAttributes.getColor(3, resources.getColor(R.color.sesl_switchbar_on_text_color_light));
        obtainStyledAttributes.recycle();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.sesl_switchbar_container);
        this.mBackground = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: androidx.appcompat.widget.SeslSwitchBar.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SeslToggleSwitch seslToggleSwitch = SeslSwitchBar.this.mSwitch;
                if (seslToggleSwitch == null || !seslToggleSwitch.isEnabled()) {
                    return;
                }
                SeslSwitchBar.this.mSwitch.setChecked(!r0.isChecked());
            }
        });
        this.mOnTextId = R.string.sesl_switchbar_on_text;
        this.mOffTextId = R.string.sesl_switchbar_off_text;
        TextView textView = (TextView) findViewById(R.id.sesl_switchbar_text);
        this.mTextView = textView;
        ((ViewGroup.MarginLayoutParams) textView.getLayoutParams()).setMarginStart((int) resources.getDimension(R.dimen.sesl_switchbar_margin_start));
        SeslToggleSwitch seslToggleSwitch = (SeslToggleSwitch) findViewById(R.id.sesl_switchbar_switch);
        this.mSwitch = seslToggleSwitch;
        seslToggleSwitch.setSaveEnabled(false);
        seslToggleSwitch.setFocusable(false);
        seslToggleSwitch.setClickable(false);
        seslToggleSwitch.setOnCheckedChangeListener(this);
        int i3 = this.mOnTextId;
        int i4 = this.mOffTextId;
        this.mOnTextId = i3;
        this.mOffTextId = i4;
        setTextViewLabelAndBackground(seslToggleSwitch.isChecked());
        C00952 c00952 = new C00952();
        if (!arrayList.contains(c00952)) {
            arrayList.add(c00952);
            ((ViewGroup.MarginLayoutParams) seslToggleSwitch.getLayoutParams()).setMarginEnd((int) resources.getDimension(R.dimen.sesl_switchbar_margin_end));
            SwitchBarDelegate switchBarDelegate = new SwitchBarDelegate(this);
            this.mDelegate = switchBarDelegate;
            ViewCompat.setAccessibilityDelegate(linearLayout, switchBarDelegate);
            Context context2 = getContext();
            while (true) {
                if (!(context2 instanceof ContextWrapper)) {
                    break;
                }
                if (context2 instanceof Activity) {
                    CharSequence title = ((Activity) context2).getTitle();
                    if (title != null) {
                        str = title.toString();
                    }
                } else {
                    context2 = ((ContextWrapper) context2).getBaseContext();
                }
            }
            str = "";
            this.mDelegate.mSessionName = str;
            return;
        }
        throw new IllegalStateException("Cannot add twice the same OnSwitchChangeListener");
    }
}
