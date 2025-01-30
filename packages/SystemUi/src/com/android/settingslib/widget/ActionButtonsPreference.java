package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ActionButtonsPreference extends Preference {
    public final List mBtnBackgroundStyle1;
    public final List mBtnBackgroundStyle2;
    public final List mBtnBackgroundStyle3;
    public final List mBtnBackgroundStyle4;
    public final ButtonInfo mButton1Info;
    public final ButtonInfo mButton2Info;
    public final ButtonInfo mButton3Info;
    public final ButtonInfo mButton4Info;
    public View mDivider1;
    public View mDivider2;
    public View mDivider3;
    public final List mVisibleButtonInfos;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ButtonInfo {
        public Button mButton;
        public final boolean mIsEnabled = true;
        public final boolean mIsVisible = true;

        public final boolean isVisible() {
            return this.mButton.getVisibility() == 0;
        }

        public final void setUpButton() {
            this.mButton.setText((CharSequence) null);
            this.mButton.setOnClickListener(null);
            this.mButton.setEnabled(this.mIsEnabled);
            this.mButton.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            if (this.mIsVisible && !TextUtils.isEmpty(null)) {
                this.mButton.setVisibility(0);
            } else {
                this.mButton.setVisibility(8);
            }
        }
    }

    public ActionButtonsPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mButton1Info = new ButtonInfo();
        this.mButton2Info = new ButtonInfo();
        this.mButton3Info = new ButtonInfo();
        this.mButton4Info = new ButtonInfo();
        this.mVisibleButtonInfos = new ArrayList(4);
        this.mBtnBackgroundStyle1 = new ArrayList(1);
        this.mBtnBackgroundStyle2 = new ArrayList(2);
        this.mBtnBackgroundStyle3 = new ArrayList(3);
        this.mBtnBackgroundStyle4 = new ArrayList(4);
        init();
    }

    public static void setupBackgrounds(List list, List list2) {
        for (int i = 0; i < list2.size(); i++) {
            ((ButtonInfo) list.get(i)).mButton.setBackground((Drawable) list2.get(i));
        }
    }

    public static void setupRtlBackgrounds(List list, List list2) {
        int size = list2.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            } else {
                ((ButtonInfo) list.get((list2.size() - 1) - size)).mButton.setBackground((Drawable) list2.get(size));
            }
        }
    }

    public final void fetchDrawableArray(List list, TypedArray typedArray) {
        for (int i = 0; i < typedArray.length(); i++) {
            list.add(this.mContext.getDrawable(typedArray.getResourceId(i, 0)));
        }
    }

    public final void init() {
        this.mLayoutResId = R.layout.settingslib_action_buttons;
        setSelectable(false);
        Resources resources = this.mContext.getResources();
        fetchDrawableArray(this.mBtnBackgroundStyle1, resources.obtainTypedArray(R.array.background_style1));
        fetchDrawableArray(this.mBtnBackgroundStyle2, resources.obtainTypedArray(R.array.background_style2));
        fetchDrawableArray(this.mBtnBackgroundStyle3, resources.obtainTypedArray(R.array.background_style3));
        fetchDrawableArray(this.mBtnBackgroundStyle4, resources.obtainTypedArray(R.array.background_style4));
    }

    @Override // androidx.preference.Preference
    public final void notifyChanged() {
        super.notifyChanged();
        if (this.mVisibleButtonInfos.isEmpty()) {
            return;
        }
        this.mVisibleButtonInfos.clear();
        updateLayout();
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        this.mButton1Info.mButton = (Button) preferenceViewHolder.findViewById(R.id.button1);
        this.mButton2Info.mButton = (Button) preferenceViewHolder.findViewById(R.id.button2);
        this.mButton3Info.mButton = (Button) preferenceViewHolder.findViewById(R.id.button3);
        this.mButton4Info.mButton = (Button) preferenceViewHolder.findViewById(R.id.button4);
        this.mDivider1 = preferenceViewHolder.findViewById(R.id.divider1);
        this.mDivider2 = preferenceViewHolder.findViewById(R.id.divider2);
        this.mDivider3 = preferenceViewHolder.findViewById(R.id.divider3);
        this.mButton1Info.setUpButton();
        this.mButton2Info.setUpButton();
        this.mButton3Info.setUpButton();
        this.mButton4Info.setUpButton();
        if (!((ArrayList) this.mVisibleButtonInfos).isEmpty()) {
            ((ArrayList) this.mVisibleButtonInfos).clear();
        }
        updateLayout();
    }

    public final void updateLayout() {
        if (this.mButton1Info.isVisible()) {
            ((ArrayList) this.mVisibleButtonInfos).add(this.mButton1Info);
        }
        if (this.mButton2Info.isVisible()) {
            ((ArrayList) this.mVisibleButtonInfos).add(this.mButton2Info);
        }
        if (this.mButton3Info.isVisible()) {
            ((ArrayList) this.mVisibleButtonInfos).add(this.mButton3Info);
        }
        if (this.mButton4Info.isVisible()) {
            ((ArrayList) this.mVisibleButtonInfos).add(this.mButton4Info);
        }
        boolean z = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(this.mContext) == 1;
        int size = ((ArrayList) this.mVisibleButtonInfos).size();
        if (size != 1) {
            if (size != 2) {
                if (size != 3) {
                    if (size != 4) {
                        Log.e("ActionButtonPreference", "No visible buttons info, skip background settings.");
                    } else if (z) {
                        setupRtlBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle4);
                    } else {
                        setupBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle4);
                    }
                } else if (z) {
                    setupRtlBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle3);
                } else {
                    setupBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle3);
                }
            } else if (z) {
                setupRtlBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle2);
            } else {
                setupBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle2);
            }
        } else if (z) {
            setupRtlBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle1);
        } else {
            setupBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle1);
        }
        if (this.mDivider1 != null && this.mButton1Info.isVisible() && this.mButton2Info.isVisible()) {
            this.mDivider1.setVisibility(0);
        }
        if (this.mDivider2 != null && this.mButton3Info.isVisible() && (this.mButton1Info.isVisible() || this.mButton2Info.isVisible())) {
            this.mDivider2.setVisibility(0);
        }
        if (this.mDivider3 == null || ((ArrayList) this.mVisibleButtonInfos).size() <= 1 || !this.mButton4Info.isVisible()) {
            return;
        }
        this.mDivider3.setVisibility(0);
    }

    public ActionButtonsPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mButton1Info = new ButtonInfo();
        this.mButton2Info = new ButtonInfo();
        this.mButton3Info = new ButtonInfo();
        this.mButton4Info = new ButtonInfo();
        this.mVisibleButtonInfos = new ArrayList(4);
        this.mBtnBackgroundStyle1 = new ArrayList(1);
        this.mBtnBackgroundStyle2 = new ArrayList(2);
        this.mBtnBackgroundStyle3 = new ArrayList(3);
        this.mBtnBackgroundStyle4 = new ArrayList(4);
        init();
    }

    public ActionButtonsPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mButton1Info = new ButtonInfo();
        this.mButton2Info = new ButtonInfo();
        this.mButton3Info = new ButtonInfo();
        this.mButton4Info = new ButtonInfo();
        this.mVisibleButtonInfos = new ArrayList(4);
        this.mBtnBackgroundStyle1 = new ArrayList(1);
        this.mBtnBackgroundStyle2 = new ArrayList(2);
        this.mBtnBackgroundStyle3 = new ArrayList(3);
        this.mBtnBackgroundStyle4 = new ArrayList(4);
        init();
    }

    public ActionButtonsPreference(Context context) {
        super(context);
        this.mButton1Info = new ButtonInfo();
        this.mButton2Info = new ButtonInfo();
        this.mButton3Info = new ButtonInfo();
        this.mButton4Info = new ButtonInfo();
        this.mVisibleButtonInfos = new ArrayList(4);
        this.mBtnBackgroundStyle1 = new ArrayList(1);
        this.mBtnBackgroundStyle2 = new ArrayList(2);
        this.mBtnBackgroundStyle3 = new ArrayList(3);
        this.mBtnBackgroundStyle4 = new ArrayList(4);
        init();
    }
}
