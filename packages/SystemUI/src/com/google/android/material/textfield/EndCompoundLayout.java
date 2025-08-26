package com.google.android.material.textfield;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.Editable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityManagerCompat$TouchExplorationStateChangeListener;
import androidx.core.view.accessibility.AccessibilityManagerCompat$TouchExplorationStateChangeListenerWrapper;
import androidx.fragment.app.FragmentManager$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.WeakHashMap;

/* loaded from: classes4.dex */
public class EndCompoundLayout extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager accessibilityManager;
    public EditText editText;
    public final AnonymousClass1 editTextWatcher;
    public final LinkedHashSet endIconChangedListeners;
    public final EndIconDelegates endIconDelegates;
    public final FrameLayout endIconFrame;
    public final int endIconMinSize;
    public int endIconMode;
    public final ColorStateList endIconTintList;
    public final PorterDuff.Mode endIconTintMode;
    public final CheckableImageButton endIconView;
    public final ColorStateList errorIconTintList;
    public final PorterDuff.Mode errorIconTintMode;
    public final CheckableImageButton errorIconView;
    public boolean hintExpanded;
    public final CharSequence suffixText;
    public final AppCompatTextView suffixTextView;
    public final TextInputLayout textInputLayout;
    public AccessibilityManagerCompat$TouchExplorationStateChangeListener touchExplorationStateChangeListener;

    /* renamed from: com.google.android.material.textfield.EndCompoundLayout$2, reason: invalid class name */
    public class AnonymousClass2 {
        public AnonymousClass2() {
        }

        public final void onEditTextAttached(TextInputLayout textInputLayout) {
            EndCompoundLayout endCompoundLayout = EndCompoundLayout.this;
            EditText editText = endCompoundLayout.editText;
            if (editText == textInputLayout.editText) {
                return;
            }
            if (editText != null) {
                editText.removeTextChangedListener(endCompoundLayout.editTextWatcher);
                if (endCompoundLayout.editText.getOnFocusChangeListener() == endCompoundLayout.getEndIconDelegate().getOnEditTextFocusChangeListener()) {
                    endCompoundLayout.editText.setOnFocusChangeListener(null);
                }
            }
            EditText editText2 = textInputLayout.editText;
            endCompoundLayout.editText = editText2;
            if (editText2 != null) {
                editText2.addTextChangedListener(endCompoundLayout.editTextWatcher);
            }
            endCompoundLayout.getEndIconDelegate().onEditTextAttached(endCompoundLayout.editText);
            endCompoundLayout.setOnFocusChangeListenersIfNeeded(endCompoundLayout.getEndIconDelegate());
        }
    }

    public class EndIconDelegates {
        public final int customEndIconDrawableId;
        public final SparseArray delegates = new SparseArray();
        public final EndCompoundLayout endLayout;
        public final int passwordIconDrawableId;

        public EndIconDelegates(EndCompoundLayout endCompoundLayout, TintTypedArray tintTypedArray) {
            this.endLayout = endCompoundLayout;
            this.customEndIconDrawableId = tintTypedArray.mWrapped.getResourceId(28, 0);
            this.passwordIconDrawableId = tintTypedArray.mWrapped.getResourceId(52, 0);
        }
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.google.android.material.textfield.EndCompoundLayout$1] */
    public EndCompoundLayout(TextInputLayout textInputLayout, TintTypedArray tintTypedArray) throws Resources.NotFoundException {
        CharSequence text;
        super(textInputLayout.getContext());
        this.endIconMode = 0;
        this.endIconChangedListeners = new LinkedHashSet();
        this.editTextWatcher = new TextWatcherAdapter() { // from class: com.google.android.material.textfield.EndCompoundLayout.1
            @Override // com.google.android.material.internal.TextWatcherAdapter, android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
                EndCompoundLayout.this.getEndIconDelegate().afterEditTextChanged();
            }

            @Override // com.google.android.material.internal.TextWatcherAdapter, android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                EndCompoundLayout.this.getEndIconDelegate().beforeEditTextChanged();
            }
        };
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        this.textInputLayout = textInputLayout;
        setVisibility(8);
        setOrientation(0);
        setLayoutParams(new FrameLayout.LayoutParams(-2, -1, 8388613));
        FrameLayout frameLayout = new FrameLayout(getContext());
        this.endIconFrame = frameLayout;
        frameLayout.setVisibility(8);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
        CheckableImageButton checkableImageButtonCreateIconView = createIconView(this, layoutInflaterFrom, R.id.text_input_error_icon);
        this.errorIconView = checkableImageButtonCreateIconView;
        CheckableImageButton checkableImageButtonCreateIconView2 = createIconView(frameLayout, layoutInflaterFrom, R.id.text_input_end_icon);
        this.endIconView = checkableImageButtonCreateIconView2;
        this.endIconDelegates = new EndIconDelegates(this, tintTypedArray);
        AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
        this.suffixTextView = appCompatTextView;
        if (tintTypedArray.mWrapped.hasValue(38)) {
            this.errorIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, 38);
        }
        if (tintTypedArray.mWrapped.hasValue(39)) {
            this.errorIconTintMode = ViewUtils.parseTintMode(tintTypedArray.mWrapped.getInt(39, -1), null);
        }
        if (tintTypedArray.mWrapped.hasValue(37)) {
            checkableImageButtonCreateIconView.setImageDrawable(tintTypedArray.getDrawable(37));
            updateErrorIconVisibility();
            IconHelper.applyIconTint(textInputLayout, checkableImageButtonCreateIconView, this.errorIconTintList, this.errorIconTintMode);
        }
        checkableImageButtonCreateIconView.setContentDescription(getResources().getText(R.string.error_icon_content_description));
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        checkableImageButtonCreateIconView.setImportantForAccessibility(2);
        checkableImageButtonCreateIconView.setClickable(false);
        checkableImageButtonCreateIconView.pressable = false;
        checkableImageButtonCreateIconView.setFocusable(false);
        if (!tintTypedArray.mWrapped.hasValue(53)) {
            if (tintTypedArray.mWrapped.hasValue(32)) {
                this.endIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, 32);
            }
            if (tintTypedArray.mWrapped.hasValue(33)) {
                this.endIconTintMode = ViewUtils.parseTintMode(tintTypedArray.mWrapped.getInt(33, -1), null);
            }
        }
        if (tintTypedArray.mWrapped.hasValue(30)) {
            setEndIconMode(tintTypedArray.mWrapped.getInt(30, 0));
            if (tintTypedArray.mWrapped.hasValue(27) && checkableImageButtonCreateIconView2.getContentDescription() != (text = tintTypedArray.mWrapped.getText(27))) {
                checkableImageButtonCreateIconView2.setContentDescription(text);
            }
            boolean z = tintTypedArray.mWrapped.getBoolean(26, true);
            if (checkableImageButtonCreateIconView2.checkable != z) {
                checkableImageButtonCreateIconView2.checkable = z;
                checkableImageButtonCreateIconView2.sendAccessibilityEvent(0);
            }
        } else if (tintTypedArray.mWrapped.hasValue(53)) {
            if (tintTypedArray.mWrapped.hasValue(54)) {
                this.endIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, 54);
            }
            if (tintTypedArray.mWrapped.hasValue(55)) {
                this.endIconTintMode = ViewUtils.parseTintMode(tintTypedArray.mWrapped.getInt(55, -1), null);
            }
            setEndIconMode(tintTypedArray.mWrapped.getBoolean(53, false) ? 1 : 0);
            CharSequence text2 = tintTypedArray.mWrapped.getText(51);
            if (checkableImageButtonCreateIconView2.getContentDescription() != text2) {
                checkableImageButtonCreateIconView2.setContentDescription(text2);
            }
        }
        int dimensionPixelSize = tintTypedArray.mWrapped.getDimensionPixelSize(29, getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size));
        if (dimensionPixelSize < 0) {
            throw new IllegalArgumentException("endIconSize cannot be less than 0");
        }
        if (dimensionPixelSize != this.endIconMinSize) {
            this.endIconMinSize = dimensionPixelSize;
            checkableImageButtonCreateIconView2.setMinimumWidth(dimensionPixelSize);
            checkableImageButtonCreateIconView2.setMinimumHeight(dimensionPixelSize);
            checkableImageButtonCreateIconView.setMinimumWidth(dimensionPixelSize);
            checkableImageButtonCreateIconView.setMinimumHeight(dimensionPixelSize);
        }
        if (tintTypedArray.mWrapped.hasValue(31)) {
            ImageView.ScaleType scaleTypeConvertScaleType = IconHelper.convertScaleType(tintTypedArray.mWrapped.getInt(31, -1));
            checkableImageButtonCreateIconView2.setScaleType(scaleTypeConvertScaleType);
            checkableImageButtonCreateIconView.setScaleType(scaleTypeConvertScaleType);
        }
        appCompatTextView.setVisibility(8);
        appCompatTextView.setId(R.id.textinput_suffix_text);
        appCompatTextView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2, 80.0f));
        appCompatTextView.setAccessibilityLiveRegion(1);
        appCompatTextView.setTextAppearance(tintTypedArray.mWrapped.getResourceId(72, 0));
        if (tintTypedArray.mWrapped.hasValue(73)) {
            appCompatTextView.setTextColor(tintTypedArray.getColorStateList(73));
        }
        CharSequence text3 = tintTypedArray.mWrapped.getText(71);
        this.suffixText = TextUtils.isEmpty(text3) ? null : text3;
        appCompatTextView.setText(text3);
        updateSuffixTextVisibility();
        frameLayout.addView(checkableImageButtonCreateIconView2);
        addView(appCompatTextView);
        addView(frameLayout);
        addView(checkableImageButtonCreateIconView);
        textInputLayout.editTextAttachedListeners.add(anonymousClass2);
        if (textInputLayout.editText != null) {
            anonymousClass2.onEditTextAttached(textInputLayout);
        }
        addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.google.android.material.textfield.EndCompoundLayout.3
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                EndCompoundLayout endCompoundLayout = EndCompoundLayout.this;
                int i = EndCompoundLayout.$r8$clinit;
                if (endCompoundLayout.touchExplorationStateChangeListener == null || endCompoundLayout.accessibilityManager == null) {
                    return;
                }
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                if (endCompoundLayout.isAttachedToWindow()) {
                    endCompoundLayout.accessibilityManager.addTouchExplorationStateChangeListener(new AccessibilityManagerCompat$TouchExplorationStateChangeListenerWrapper(endCompoundLayout.touchExplorationStateChangeListener));
                }
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                AccessibilityManager accessibilityManager;
                EndCompoundLayout endCompoundLayout = EndCompoundLayout.this;
                int i = EndCompoundLayout.$r8$clinit;
                AccessibilityManagerCompat$TouchExplorationStateChangeListener accessibilityManagerCompat$TouchExplorationStateChangeListener = endCompoundLayout.touchExplorationStateChangeListener;
                if (accessibilityManagerCompat$TouchExplorationStateChangeListener == null || (accessibilityManager = endCompoundLayout.accessibilityManager) == null) {
                    return;
                }
                accessibilityManager.removeTouchExplorationStateChangeListener(new AccessibilityManagerCompat$TouchExplorationStateChangeListenerWrapper(accessibilityManagerCompat$TouchExplorationStateChangeListener));
            }
        });
    }

    public final CheckableImageButton createIconView(ViewGroup viewGroup, LayoutInflater layoutInflater, int i) {
        CheckableImageButton checkableImageButton = (CheckableImageButton) layoutInflater.inflate(R.layout.design_text_input_end_icon, viewGroup, false);
        checkableImageButton.setId(i);
        if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
            ((ViewGroup.MarginLayoutParams) checkableImageButton.getLayoutParams()).setMarginStart(0);
        }
        return checkableImageButton;
    }

    public final EndIconDelegate getEndIconDelegate() {
        EndIconDelegates endIconDelegates = this.endIconDelegates;
        int i = this.endIconMode;
        EndIconDelegate customEndIconDelegate = (EndIconDelegate) endIconDelegates.delegates.get(i);
        if (customEndIconDelegate == null) {
            EndCompoundLayout endCompoundLayout = endIconDelegates.endLayout;
            if (i == -1) {
                customEndIconDelegate = new CustomEndIconDelegate(endCompoundLayout);
            } else if (i == 0) {
                customEndIconDelegate = new NoEndIconDelegate(endCompoundLayout);
            } else if (i == 1) {
                customEndIconDelegate = new PasswordToggleEndIconDelegate(endCompoundLayout, endIconDelegates.passwordIconDrawableId);
            } else if (i == 2) {
                customEndIconDelegate = new ClearTextEndIconDelegate(endCompoundLayout);
            } else {
                if (i != 3) {
                    throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid end icon mode: "));
                }
                customEndIconDelegate = new DropdownMenuEndIconDelegate(endCompoundLayout);
            }
            endIconDelegates.delegates.append(i, customEndIconDelegate);
        }
        return customEndIconDelegate;
    }

    public final int getSuffixTextEndOffset() {
        int measuredWidth = (isEndIconVisible() || isErrorIconVisible()) ? this.endIconView.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) this.endIconView.getLayoutParams()).getMarginStart() : 0;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        return this.suffixTextView.getPaddingEnd() + getPaddingEnd() + measuredWidth;
    }

    public final boolean isEndIconVisible() {
        return this.endIconFrame.getVisibility() == 0 && this.endIconView.getVisibility() == 0;
    }

    public final boolean isErrorIconVisible() {
        return this.errorIconView.getVisibility() == 0;
    }

    public final void refreshIconState(boolean z) {
        boolean z2;
        boolean zIsActivated;
        boolean z3;
        EndIconDelegate endIconDelegate = getEndIconDelegate();
        boolean z4 = true;
        if (!endIconDelegate.isIconCheckable() || (z3 = this.endIconView.checked) == endIconDelegate.isIconChecked()) {
            z2 = false;
        } else {
            this.endIconView.setChecked(!z3);
            z2 = true;
        }
        if (!(endIconDelegate instanceof DropdownMenuEndIconDelegate) || (zIsActivated = this.endIconView.isActivated()) == endIconDelegate.isIconActivated()) {
            z4 = z2;
        } else {
            this.endIconView.setActivated(!zIsActivated);
        }
        if (z || z4) {
            IconHelper.refreshIconDrawableState(this.textInputLayout, this.endIconView, this.endIconTintList);
        }
    }

    public final void setEndIconMode(int i) throws Resources.NotFoundException {
        AccessibilityManager accessibilityManager;
        if (this.endIconMode == i) {
            return;
        }
        EndIconDelegate endIconDelegate = getEndIconDelegate();
        AccessibilityManagerCompat$TouchExplorationStateChangeListener accessibilityManagerCompat$TouchExplorationStateChangeListener = this.touchExplorationStateChangeListener;
        if (accessibilityManagerCompat$TouchExplorationStateChangeListener != null && (accessibilityManager = this.accessibilityManager) != null) {
            accessibilityManager.removeTouchExplorationStateChangeListener(new AccessibilityManagerCompat$TouchExplorationStateChangeListenerWrapper(accessibilityManagerCompat$TouchExplorationStateChangeListener));
        }
        this.touchExplorationStateChangeListener = null;
        endIconDelegate.tearDown();
        this.endIconMode = i;
        Iterator it = this.endIconChangedListeners.iterator();
        if (it.hasNext()) {
            throw FragmentManager$$ExternalSyntheticOutline0.m(it);
        }
        setEndIconVisible(i != 0);
        EndIconDelegate endIconDelegate2 = getEndIconDelegate();
        int iconDrawableResId = this.endIconDelegates.customEndIconDrawableId;
        if (iconDrawableResId == 0) {
            iconDrawableResId = endIconDelegate2.getIconDrawableResId();
        }
        Drawable drawable = iconDrawableResId != 0 ? AppCompatResources.getDrawable(iconDrawableResId, getContext()) : null;
        this.endIconView.setImageDrawable(drawable);
        if (drawable != null) {
            IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, this.endIconTintMode);
            IconHelper.refreshIconDrawableState(this.textInputLayout, this.endIconView, this.endIconTintList);
        }
        int iconContentDescriptionResId = endIconDelegate2.getIconContentDescriptionResId();
        CharSequence text = iconContentDescriptionResId != 0 ? getResources().getText(iconContentDescriptionResId) : null;
        if (this.endIconView.getContentDescription() != text) {
            this.endIconView.setContentDescription(text);
        }
        boolean zIsIconCheckable = endIconDelegate2.isIconCheckable();
        CheckableImageButton checkableImageButton = this.endIconView;
        if (checkableImageButton.checkable != zIsIconCheckable) {
            checkableImageButton.checkable = zIsIconCheckable;
            checkableImageButton.sendAccessibilityEvent(0);
        }
        if (!endIconDelegate2.isBoxBackgroundModeSupported(this.textInputLayout.boxBackgroundMode)) {
            throw new IllegalStateException("The current box background mode " + this.textInputLayout.boxBackgroundMode + " is not supported by the end icon mode " + i);
        }
        endIconDelegate2.setUp();
        AccessibilityManagerCompat$TouchExplorationStateChangeListener touchExplorationStateChangeListener = endIconDelegate2.getTouchExplorationStateChangeListener();
        this.touchExplorationStateChangeListener = touchExplorationStateChangeListener;
        if (touchExplorationStateChangeListener != null && this.accessibilityManager != null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (isAttachedToWindow()) {
                this.accessibilityManager.addTouchExplorationStateChangeListener(new AccessibilityManagerCompat$TouchExplorationStateChangeListenerWrapper(this.touchExplorationStateChangeListener));
            }
        }
        View.OnClickListener onIconClickListener = endIconDelegate2.getOnIconClickListener();
        CheckableImageButton checkableImageButton2 = this.endIconView;
        checkableImageButton2.setOnClickListener(onIconClickListener);
        IconHelper.setIconClickable(checkableImageButton2);
        EditText editText = this.editText;
        if (editText != null) {
            endIconDelegate2.onEditTextAttached(editText);
            setOnFocusChangeListenersIfNeeded(endIconDelegate2);
        }
        IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, this.endIconTintMode);
        refreshIconState(true);
    }

    public final void setEndIconVisible(boolean z) throws Resources.NotFoundException {
        if (isEndIconVisible() != z) {
            this.endIconView.setVisibility(z ? 0 : 8);
            updateEndLayoutVisibility();
            updateSuffixTextViewPadding();
            this.textInputLayout.updateDummyDrawables();
        }
    }

    public final void setOnFocusChangeListenersIfNeeded(EndIconDelegate endIconDelegate) {
        if (this.editText == null) {
            return;
        }
        if (endIconDelegate.getOnEditTextFocusChangeListener() != null) {
            this.editText.setOnFocusChangeListener(endIconDelegate.getOnEditTextFocusChangeListener());
        }
        if (endIconDelegate.getOnIconViewFocusChangeListener() != null) {
            this.endIconView.setOnFocusChangeListener(endIconDelegate.getOnIconViewFocusChangeListener());
        }
    }

    public final void updateEndLayoutVisibility() {
        this.endIconFrame.setVisibility((this.endIconView.getVisibility() != 0 || isErrorIconVisible()) ? 8 : 0);
        setVisibility((isEndIconVisible() || isErrorIconVisible() || !((this.suffixText == null || this.hintExpanded) ? 8 : false)) ? 0 : 8);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0019  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateErrorIconVisibility() throws Resources.NotFoundException {
        boolean z;
        if (this.errorIconView.getDrawable() != null) {
            TextInputLayout textInputLayout = this.textInputLayout;
            z = textInputLayout.indicatorViewController.errorEnabled && textInputLayout.shouldShowError();
        }
        this.errorIconView.setVisibility(z ? 0 : 8);
        updateEndLayoutVisibility();
        updateSuffixTextViewPadding();
        if (this.endIconMode != 0) {
            return;
        }
        this.textInputLayout.updateDummyDrawables();
    }

    public final void updateSuffixTextViewPadding() throws Resources.NotFoundException {
        int paddingEnd;
        if (this.textInputLayout.editText == null) {
            return;
        }
        if (isEndIconVisible() || isErrorIconVisible()) {
            paddingEnd = 0;
        } else {
            EditText editText = this.textInputLayout.editText;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            paddingEnd = editText.getPaddingEnd();
        }
        AppCompatTextView appCompatTextView = this.suffixTextView;
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.material_input_text_to_prefix_suffix_padding);
        int paddingTop = this.textInputLayout.editText.getPaddingTop();
        int paddingBottom = this.textInputLayout.editText.getPaddingBottom();
        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
        appCompatTextView.setPaddingRelative(dimensionPixelSize, paddingTop, paddingEnd, paddingBottom);
    }

    public final void updateSuffixTextVisibility() {
        int visibility = this.suffixTextView.getVisibility();
        int i = (this.suffixText == null || this.hintExpanded) ? 8 : 0;
        if (visibility != i) {
            getEndIconDelegate().onSuffixVisibilityChanged(i == 0);
        }
        updateEndLayoutVisibility();
        this.suffixTextView.setVisibility(i);
        this.textInputLayout.updateDummyDrawables();
    }
}
