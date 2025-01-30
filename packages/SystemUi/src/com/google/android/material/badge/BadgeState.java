package com.google.android.material.badge;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Xml;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import java.io.IOException;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BadgeState {
    public final float badgeRadius;
    public final float badgeWidePadding;
    public final float badgeWithTextRadius;
    public final State currentState = new State();
    public final State overridingState;

    public BadgeState(Context context, int i, int i2, int i3, State state) {
        AttributeSet attributeSet;
        int i4;
        int next;
        state = state == null ? new State() : state;
        if (i != 0) {
            state.badgeResId = i;
        }
        int i5 = state.badgeResId;
        if (i5 != 0) {
            try {
                XmlResourceParser xml = context.getResources().getXml(i5);
                do {
                    next = xml.next();
                    if (next == 2) {
                        break;
                    }
                } while (next != 1);
                if (next != 2) {
                    throw new XmlPullParserException("No start tag found");
                }
                if (!TextUtils.equals(xml.getName(), "badge")) {
                    throw new XmlPullParserException("Must have a <" + ((Object) "badge") + "> start tag");
                }
                AttributeSet asAttributeSet = Xml.asAttributeSet(xml);
                i4 = asAttributeSet.getStyleAttribute();
                attributeSet = asAttributeSet;
            } catch (IOException | XmlPullParserException e) {
                Resources.NotFoundException notFoundException = new Resources.NotFoundException("Can't load badge resource ID #0x" + Integer.toHexString(i5));
                notFoundException.initCause(e);
                throw notFoundException;
            }
        } else {
            attributeSet = null;
            i4 = 0;
        }
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R$styleable.Badge, i2, i4 == 0 ? i3 : i4, new int[0]);
        Resources resources = context.getResources();
        this.badgeRadius = obtainStyledAttributes.getDimensionPixelSize(2, resources.getDimensionPixelSize(R.dimen.mtrl_badge_radius));
        this.badgeWidePadding = obtainStyledAttributes.getDimensionPixelSize(4, resources.getDimensionPixelSize(R.dimen.mtrl_badge_long_text_horizontal_padding));
        this.badgeWithTextRadius = obtainStyledAttributes.getDimensionPixelSize(5, resources.getDimensionPixelSize(R.dimen.mtrl_badge_with_text_radius));
        State state2 = this.currentState;
        int i6 = state.alpha;
        state2.alpha = i6 == -2 ? 255 : i6;
        CharSequence charSequence = state.contentDescriptionNumberless;
        state2.contentDescriptionNumberless = charSequence == null ? context.getString(R.string.mtrl_badge_numberless_content_description) : charSequence;
        State state3 = this.currentState;
        int i7 = state.contentDescriptionQuantityStrings;
        state3.contentDescriptionQuantityStrings = i7 == 0 ? R.plurals.mtrl_badge_content_description : i7;
        int i8 = state.contentDescriptionExceedsMaxBadgeNumberRes;
        state3.contentDescriptionExceedsMaxBadgeNumberRes = i8 == 0 ? R.string.mtrl_exceed_max_badge_number_content_description : i8;
        Boolean bool = state.isVisible;
        state3.isVisible = Boolean.valueOf(bool == null || bool.booleanValue());
        State state4 = this.currentState;
        int i9 = state.maxCharacterCount;
        state4.maxCharacterCount = i9 == -2 ? obtainStyledAttributes.getInt(8, 4) : i9;
        int i10 = state.number;
        if (i10 != -2) {
            this.currentState.number = i10;
        } else if (obtainStyledAttributes.hasValue(9)) {
            this.currentState.number = obtainStyledAttributes.getInt(9, 0);
        } else {
            this.currentState.number = -1;
        }
        State state5 = this.currentState;
        Integer num = state.backgroundColor;
        state5.backgroundColor = Integer.valueOf(num == null ? MaterialResources.getColorStateList(context, obtainStyledAttributes, 0).getDefaultColor() : num.intValue());
        Integer num2 = state.badgeTextColor;
        if (num2 != null) {
            this.currentState.badgeTextColor = num2;
        } else if (obtainStyledAttributes.hasValue(3)) {
            this.currentState.badgeTextColor = Integer.valueOf(MaterialResources.getColorStateList(context, obtainStyledAttributes, 3).getDefaultColor());
        } else {
            this.currentState.badgeTextColor = Integer.valueOf(new TextAppearance(context, 2132018234).textColor.getDefaultColor());
        }
        State state6 = this.currentState;
        Integer num3 = state.badgeGravity;
        state6.badgeGravity = Integer.valueOf(num3 == null ? obtainStyledAttributes.getInt(1, 8388661) : num3.intValue());
        State state7 = this.currentState;
        Integer num4 = state.horizontalOffsetWithoutText;
        state7.horizontalOffsetWithoutText = Integer.valueOf(num4 == null ? obtainStyledAttributes.getDimensionPixelOffset(6, 0) : num4.intValue());
        State state8 = this.currentState;
        Integer num5 = state.verticalOffsetWithoutText;
        state8.verticalOffsetWithoutText = Integer.valueOf(num5 == null ? obtainStyledAttributes.getDimensionPixelOffset(10, 0) : num5.intValue());
        State state9 = this.currentState;
        Integer num6 = state.horizontalOffsetWithText;
        state9.horizontalOffsetWithText = Integer.valueOf(num6 == null ? obtainStyledAttributes.getDimensionPixelOffset(7, state9.horizontalOffsetWithoutText.intValue()) : num6.intValue());
        State state10 = this.currentState;
        Integer num7 = state.verticalOffsetWithText;
        state10.verticalOffsetWithText = Integer.valueOf(num7 == null ? obtainStyledAttributes.getDimensionPixelOffset(11, state10.verticalOffsetWithoutText.intValue()) : num7.intValue());
        State state11 = this.currentState;
        Integer num8 = state.additionalHorizontalOffset;
        state11.additionalHorizontalOffset = Integer.valueOf(num8 == null ? 0 : num8.intValue());
        State state12 = this.currentState;
        Integer num9 = state.additionalVerticalOffset;
        state12.additionalVerticalOffset = Integer.valueOf(num9 != null ? num9.intValue() : 0);
        obtainStyledAttributes.recycle();
        Locale locale = state.numberLocale;
        if (locale == null) {
            this.currentState.numberLocale = Locale.getDefault(Locale.Category.FORMAT);
        } else {
            this.currentState.numberLocale = locale;
        }
        this.overridingState = state;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class State implements Parcelable {
        public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator() { // from class: com.google.android.material.badge.BadgeState.State.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new State(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new State[i];
            }
        };
        public Integer additionalHorizontalOffset;
        public Integer additionalVerticalOffset;
        public int alpha;
        public Integer backgroundColor;
        public Integer badgeGravity;
        public int badgeResId;
        public Integer badgeTextColor;
        public int contentDescriptionExceedsMaxBadgeNumberRes;
        public CharSequence contentDescriptionNumberless;
        public int contentDescriptionQuantityStrings;
        public Integer horizontalOffsetWithText;
        public Integer horizontalOffsetWithoutText;
        public Boolean isVisible;
        public int maxCharacterCount;
        public int number;
        public Locale numberLocale;
        public Integer verticalOffsetWithText;
        public Integer verticalOffsetWithoutText;

        public State() {
            this.alpha = 255;
            this.number = -2;
            this.maxCharacterCount = -2;
            this.isVisible = Boolean.TRUE;
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.badgeResId);
            parcel.writeSerializable(this.backgroundColor);
            parcel.writeSerializable(this.badgeTextColor);
            parcel.writeInt(this.alpha);
            parcel.writeInt(this.number);
            parcel.writeInt(this.maxCharacterCount);
            CharSequence charSequence = this.contentDescriptionNumberless;
            parcel.writeString(charSequence == null ? null : charSequence.toString());
            parcel.writeInt(this.contentDescriptionQuantityStrings);
            parcel.writeSerializable(this.badgeGravity);
            parcel.writeSerializable(this.horizontalOffsetWithoutText);
            parcel.writeSerializable(this.verticalOffsetWithoutText);
            parcel.writeSerializable(this.horizontalOffsetWithText);
            parcel.writeSerializable(this.verticalOffsetWithText);
            parcel.writeSerializable(this.additionalHorizontalOffset);
            parcel.writeSerializable(this.additionalVerticalOffset);
            parcel.writeSerializable(this.isVisible);
            parcel.writeSerializable(this.numberLocale);
        }

        public State(Parcel parcel) {
            this.alpha = 255;
            this.number = -2;
            this.maxCharacterCount = -2;
            this.isVisible = Boolean.TRUE;
            this.badgeResId = parcel.readInt();
            this.backgroundColor = (Integer) parcel.readSerializable();
            this.badgeTextColor = (Integer) parcel.readSerializable();
            this.alpha = parcel.readInt();
            this.number = parcel.readInt();
            this.maxCharacterCount = parcel.readInt();
            this.contentDescriptionNumberless = parcel.readString();
            this.contentDescriptionQuantityStrings = parcel.readInt();
            this.badgeGravity = (Integer) parcel.readSerializable();
            this.horizontalOffsetWithoutText = (Integer) parcel.readSerializable();
            this.verticalOffsetWithoutText = (Integer) parcel.readSerializable();
            this.horizontalOffsetWithText = (Integer) parcel.readSerializable();
            this.verticalOffsetWithText = (Integer) parcel.readSerializable();
            this.additionalHorizontalOffset = (Integer) parcel.readSerializable();
            this.additionalVerticalOffset = (Integer) parcel.readSerializable();
            this.isVisible = (Boolean) parcel.readSerializable();
            this.numberLocale = (Locale) parcel.readSerializable();
        }
    }
}
