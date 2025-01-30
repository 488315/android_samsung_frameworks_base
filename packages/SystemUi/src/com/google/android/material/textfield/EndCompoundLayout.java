package com.google.android.material.textfield;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.Editable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityManagerCompat$TouchExplorationStateChangeListener;
import androidx.core.view.accessibility.AccessibilityManagerTouchExplorationStateChangeListenerC0161xc359c985;
import com.android.systemui.R;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class EndCompoundLayout extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager accessibilityManager;
    public EditText editText;
    public final C43851 editTextWatcher;
    public final LinkedHashSet endIconChangedListeners;
    public final EndIconDelegates endIconDelegates;
    public final FrameLayout endIconFrame;
    public int endIconMode;
    public View.OnLongClickListener endIconOnLongClickListener;
    public ColorStateList endIconTintList;
    public PorterDuff.Mode endIconTintMode;
    public final CheckableImageButton endIconView;
    public ColorStateList errorIconTintList;
    public PorterDuff.Mode errorIconTintMode;
    public final CheckableImageButton errorIconView;
    public boolean hintExpanded;
    public CharSequence suffixText;
    public final AppCompatTextView suffixTextView;
    public final TextInputLayout textInputLayout;
    public AccessibilityManagerCompat$TouchExplorationStateChangeListener touchExplorationStateChangeListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.android.material.textfield.EndCompoundLayout$2 */
    public final class C43862 {
        public C43862() {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EndIconDelegates {
        public final int customEndIconDrawableId;
        public final SparseArray delegates = new SparseArray();
        public final EndCompoundLayout endLayout;
        public final int passwordIconDrawableId;

        public EndIconDelegates(EndCompoundLayout endCompoundLayout, TintTypedArray tintTypedArray) {
            this.endLayout = endCompoundLayout;
            this.customEndIconDrawableId = tintTypedArray.getResourceId(26, 0);
            this.passwordIconDrawableId = tintTypedArray.getResourceId(47, 0);
        }
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.google.android.material.textfield.EndCompoundLayout$1] */
    public EndCompoundLayout(TextInputLayout textInputLayout, TintTypedArray tintTypedArray) {
        super(textInputLayout.getContext());
        CharSequence text;
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
        C43862 c43862 = new C43862();
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        this.textInputLayout = textInputLayout;
        setVisibility(8);
        setOrientation(0);
        setLayoutParams(new FrameLayout.LayoutParams(-2, -1, 8388613));
        FrameLayout frameLayout = new FrameLayout(getContext());
        this.endIconFrame = frameLayout;
        frameLayout.setVisibility(8);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
        LayoutInflater from = LayoutInflater.from(getContext());
        CheckableImageButton createIconView = createIconView(this, from, R.id.text_input_error_icon);
        this.errorIconView = createIconView;
        CheckableImageButton createIconView2 = createIconView(frameLayout, from, R.id.text_input_end_icon);
        this.endIconView = createIconView2;
        this.endIconDelegates = new EndIconDelegates(this, tintTypedArray);
        AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
        this.suffixTextView = appCompatTextView;
        if (tintTypedArray.hasValue(33)) {
            this.errorIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, 33);
        }
        if (tintTypedArray.hasValue(34)) {
            this.errorIconTintMode = ViewUtils.parseTintMode(tintTypedArray.getInt(34, -1), null);
        }
        if (tintTypedArray.hasValue(32)) {
            createIconView.setImageDrawable(tintTypedArray.getDrawable(32));
            updateErrorIconVisibility();
            IconHelper.applyIconTint(textInputLayout, createIconView, this.errorIconTintList, this.errorIconTintMode);
        }
        createIconView.setContentDescription(getResources().getText(R.string.error_icon_content_description));
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setImportantForAccessibility(createIconView, 2);
        createIconView.setClickable(false);
        createIconView.pressable = false;
        createIconView.setFocusable(false);
        if (!tintTypedArray.hasValue(48)) {
            if (tintTypedArray.hasValue(28)) {
                this.endIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, 28);
            }
            if (tintTypedArray.hasValue(29)) {
                this.endIconTintMode = ViewUtils.parseTintMode(tintTypedArray.getInt(29, -1), null);
            }
        }
        if (tintTypedArray.hasValue(27)) {
            setEndIconMode(tintTypedArray.getInt(27, 0));
            if (tintTypedArray.hasValue(25) && createIconView2.getContentDescription() != (text = tintTypedArray.getText(25))) {
                createIconView2.setContentDescription(text);
            }
            boolean z = tintTypedArray.getBoolean(24, true);
            if (createIconView2.checkable != z) {
                createIconView2.checkable = z;
                createIconView2.sendAccessibilityEvent(0);
            }
        } else if (tintTypedArray.hasValue(48)) {
            if (tintTypedArray.hasValue(49)) {
                this.endIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, 49);
            }
            if (tintTypedArray.hasValue(50)) {
                this.endIconTintMode = ViewUtils.parseTintMode(tintTypedArray.getInt(50, -1), null);
            }
            setEndIconMode(tintTypedArray.getBoolean(48, false) ? 1 : 0);
            CharSequence text2 = tintTypedArray.getText(46);
            if (createIconView2.getContentDescription() != text2) {
                createIconView2.setContentDescription(text2);
            }
        }
        appCompatTextView.setVisibility(8);
        appCompatTextView.setId(R.id.textinput_suffix_text);
        appCompatTextView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2, 80.0f));
        ViewCompat.Api19Impl.setAccessibilityLiveRegion(appCompatTextView, 1);
        appCompatTextView.setTextAppearance(tintTypedArray.getResourceId(65, 0));
        if (tintTypedArray.hasValue(66)) {
            appCompatTextView.setTextColor(tintTypedArray.getColorStateList(66));
        }
        CharSequence text3 = tintTypedArray.getText(64);
        this.suffixText = TextUtils.isEmpty(text3) ? null : text3;
        appCompatTextView.setText(text3);
        updateSuffixTextVisibility();
        frameLayout.addView(createIconView2);
        addView(appCompatTextView);
        addView(frameLayout);
        addView(createIconView);
        textInputLayout.editTextAttachedListeners.add(c43862);
        if (textInputLayout.editText != null) {
            c43862.onEditTextAttached(textInputLayout);
        }
        addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.google.android.material.textfield.EndCompoundLayout.3
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                EndCompoundLayout endCompoundLayout = EndCompoundLayout.this;
                int i = EndCompoundLayout.$r8$clinit;
                endCompoundLayout.addTouchExplorationStateChangeListenerIfNeeded();
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
                accessibilityManager.removeTouchExplorationStateChangeListener(new AccessibilityManagerTouchExplorationStateChangeListenerC0161xc359c985(accessibilityManagerCompat$TouchExplorationStateChangeListener));
            }
        });
    }

    public final void addTouchExplorationStateChangeListenerIfNeeded() {
        if (this.touchExplorationStateChangeListener == null || this.accessibilityManager == null) {
            return;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api19Impl.isAttachedToWindow(this)) {
            this.accessibilityManager.addTouchExplorationStateChangeListener(new AccessibilityManagerTouchExplorationStateChangeListenerC0161xc359c985(this.touchExplorationStateChangeListener));
        }
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
        EndIconDelegate customEndIconDelegate;
        EndIconDelegates endIconDelegates = this.endIconDelegates;
        int i = this.endIconMode;
        SparseArray sparseArray = endIconDelegates.delegates;
        EndIconDelegate endIconDelegate = (EndIconDelegate) sparseArray.get(i);
        if (endIconDelegate == null) {
            EndCompoundLayout endCompoundLayout = endIconDelegates.endLayout;
            if (i == -1) {
                customEndIconDelegate = new CustomEndIconDelegate(endCompoundLayout);
            } else if (i == 0) {
                customEndIconDelegate = new NoEndIconDelegate(endCompoundLayout);
            } else if (i == 1) {
                endIconDelegate = new PasswordToggleEndIconDelegate(endCompoundLayout, endIconDelegates.passwordIconDrawableId);
                sparseArray.append(i, endIconDelegate);
            } else if (i == 2) {
                customEndIconDelegate = new ClearTextEndIconDelegate(endCompoundLayout);
            } else {
                if (i != 3) {
                    throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Invalid end icon mode: ", i));
                }
                customEndIconDelegate = new DropdownMenuEndIconDelegate(endCompoundLayout);
            }
            endIconDelegate = customEndIconDelegate;
            sparseArray.append(i, endIconDelegate);
        }
        return endIconDelegate;
    }

    public final boolean isEndIconVisible() {
        return this.endIconFrame.getVisibility() == 0 && this.endIconView.getVisibility() == 0;
    }

    public final boolean isErrorIconVisible() {
        return this.errorIconView.getVisibility() == 0;
    }

    public final void refreshIconState(boolean z) {
        boolean z2;
        boolean isActivated;
        boolean isChecked;
        EndIconDelegate endIconDelegate = getEndIconDelegate();
        boolean z3 = true;
        if (!endIconDelegate.isIconCheckable() || (isChecked = this.endIconView.isChecked()) == endIconDelegate.isIconChecked()) {
            z2 = false;
        } else {
            this.endIconView.setChecked(!isChecked);
            z2 = true;
        }
        if (!(endIconDelegate instanceof DropdownMenuEndIconDelegate) || (isActivated = this.endIconView.isActivated()) == endIconDelegate.isIconActivated()) {
            z3 = z2;
        } else {
            this.endIconView.setActivated(!isActivated);
        }
        if (z || z3) {
            IconHelper.refreshIconDrawableState(this.textInputLayout, this.endIconView, this.endIconTintList);
        }
    }

    public final void setEndIconMode(int i) {
        AccessibilityManager accessibilityManager;
        if (this.endIconMode == i) {
            return;
        }
        EndIconDelegate endIconDelegate = getEndIconDelegate();
        AccessibilityManagerCompat$TouchExplorationStateChangeListener accessibilityManagerCompat$TouchExplorationStateChangeListener = this.touchExplorationStateChangeListener;
        if (accessibilityManagerCompat$TouchExplorationStateChangeListener != null && (accessibilityManager = this.accessibilityManager) != null) {
            accessibilityManager.removeTouchExplorationStateChangeListener(new AccessibilityManagerTouchExplorationStateChangeListenerC0161xc359c985(accessibilityManagerCompat$TouchExplorationStateChangeListener));
        }
        this.touchExplorationStateChangeListener = null;
        endIconDelegate.tearDown();
        this.endIconMode = i;
        Iterator it = this.endIconChangedListeners.iterator();
        if (it.hasNext()) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(it.next());
            throw null;
        }
        setEndIconVisible(i != 0);
        EndIconDelegate endIconDelegate2 = getEndIconDelegate();
        int i2 = this.endIconDelegates.customEndIconDrawableId;
        if (i2 == 0) {
            i2 = endIconDelegate2.getIconDrawableResId();
        }
        Drawable drawable = i2 != 0 ? AppCompatResources.getDrawable(i2, getContext()) : null;
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
        boolean isIconCheckable = endIconDelegate2.isIconCheckable();
        CheckableImageButton checkableImageButton = this.endIconView;
        if (checkableImageButton.checkable != isIconCheckable) {
            checkableImageButton.checkable = isIconCheckable;
            checkableImageButton.sendAccessibilityEvent(0);
        }
        if (!endIconDelegate2.isBoxBackgroundModeSupported(this.textInputLayout.boxBackgroundMode)) {
            throw new IllegalStateException("The current box background mode " + this.textInputLayout.boxBackgroundMode + " is not supported by the end icon mode " + i);
        }
        endIconDelegate2.setUp();
        this.touchExplorationStateChangeListener = endIconDelegate2.getTouchExplorationStateChangeListener();
        addTouchExplorationStateChangeListenerIfNeeded();
        View.OnClickListener onIconClickListener = endIconDelegate2.getOnIconClickListener();
        CheckableImageButton checkableImageButton2 = this.endIconView;
        View.OnLongClickListener onLongClickListener = this.endIconOnLongClickListener;
        checkableImageButton2.setOnClickListener(onIconClickListener);
        IconHelper.setIconClickable(checkableImageButton2, onLongClickListener);
        EditText editText = this.editText;
        if (editText != null) {
            endIconDelegate2.onEditTextAttached(editText);
            setOnFocusChangeListenersIfNeeded(endIconDelegate2);
        }
        IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, this.endIconTintMode);
        refreshIconState(true);
    }

    public final void setEndIconVisible(boolean z) {
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
        setVisibility(isEndIconVisible() || isErrorIconVisible() || !((this.suffixText == null || this.hintExpanded) ? 8 : false) ? 0 : 8);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001f  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateErrorIconVisibility() {
        boolean z;
        if (this.errorIconView.getDrawable() != null) {
            TextInputLayout textInputLayout = this.textInputLayout;
            if (textInputLayout.indicatorViewController.errorEnabled && textInputLayout.shouldShowError()) {
                z = true;
                this.errorIconView.setVisibility(!z ? 0 : 8);
                updateEndLayoutVisibility();
                updateSuffixTextViewPadding();
                if (this.endIconMode != 0) {
                    this.textInputLayout.updateDummyDrawables();
                    return;
                }
                return;
            }
        }
        z = false;
        this.errorIconView.setVisibility(!z ? 0 : 8);
        updateEndLayoutVisibility();
        updateSuffixTextViewPadding();
        if (this.endIconMode != 0) {
        }
    }

    public final void updateSuffixTextViewPadding() {
        int i;
        if (this.textInputLayout.editText == null) {
            return;
        }
        if (isEndIconVisible() || isErrorIconVisible()) {
            i = 0;
        } else {
            EditText editText = this.textInputLayout.editText;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            i = ViewCompat.Api17Impl.getPaddingEnd(editText);
        }
        AppCompatTextView appCompatTextView = this.suffixTextView;
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.material_input_text_to_prefix_suffix_padding);
        int paddingTop = this.textInputLayout.editText.getPaddingTop();
        int paddingBottom = this.textInputLayout.editText.getPaddingBottom();
        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api17Impl.setPaddingRelative(appCompatTextView, dimensionPixelSize, paddingTop, i, paddingBottom);
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
