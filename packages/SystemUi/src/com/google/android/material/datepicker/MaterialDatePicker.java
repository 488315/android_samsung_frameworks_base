package com.google.android.material.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.DialogFragment;
import com.android.systemui.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.EdgeToEdgeUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MaterialDatePicker<S> extends DialogFragment {
    public MaterialShapeDrawable background;
    public MaterialCalendar calendar;
    public CalendarConstraints calendarConstraints;
    public Button confirmButton;
    public boolean edgeToEdgeEnabled;
    public boolean fullscreen;
    public CheckableImageButton headerToggleButton;
    public int inputMode;
    public CharSequence negativeButtonText;
    public int negativeButtonTextResId;
    public int overrideThemeResId;
    public PickerFragment pickerFragment;
    public CharSequence positiveButtonText;
    public int positiveButtonTextResId;
    public CharSequence titleText;
    public int titleTextResId;
    public final LinkedHashSet onPositiveButtonClickListeners = new LinkedHashSet();
    public final LinkedHashSet onNegativeButtonClickListeners = new LinkedHashSet();
    public final LinkedHashSet onCancelListeners = new LinkedHashSet();
    public final LinkedHashSet onDismissListeners = new LinkedHashSet();

    public static int getPaddedPickerWidth(Context context) {
        Resources resources = context.getResources();
        int dimensionPixelOffset = resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_content_padding);
        int i = Month.current().daysInWeek;
        return ((i - 1) * resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_month_horizontal_padding)) + (resources.getDimensionPixelSize(R.dimen.mtrl_calendar_day_width) * i) + (dimensionPixelOffset * 2);
    }

    public static boolean isFullscreen(Context context) {
        return readMaterialCalendarStyleBoolean(android.R.attr.windowFullscreen, context);
    }

    public static boolean readMaterialCalendarStyleBoolean(int i, Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(MaterialAttributes.resolveTypedValueOrThrow(context, MaterialCalendar.class.getCanonicalName(), R.attr.materialCalendarStyle).data, new int[]{i});
        boolean z = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        return z;
    }

    public final void getDateSelector() {
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(this.mArguments.getParcelable("DATE_SELECTOR_KEY"));
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        Iterator it = this.onCancelListeners.iterator();
        while (it.hasNext()) {
            ((DialogInterface.OnCancelListener) it.next()).onCancel(dialogInterface);
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            bundle = this.mArguments;
        }
        this.overrideThemeResId = bundle.getInt("OVERRIDE_THEME_RES_ID");
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(bundle.getParcelable("DATE_SELECTOR_KEY"));
        this.calendarConstraints = (CalendarConstraints) bundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
        this.titleTextResId = bundle.getInt("TITLE_TEXT_RES_ID_KEY");
        this.titleText = bundle.getCharSequence("TITLE_TEXT_KEY");
        this.inputMode = bundle.getInt("INPUT_MODE_KEY");
        this.positiveButtonTextResId = bundle.getInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY");
        this.positiveButtonText = bundle.getCharSequence("POSITIVE_BUTTON_TEXT_KEY");
        this.negativeButtonTextResId = bundle.getInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY");
        this.negativeButtonText = bundle.getCharSequence("NEGATIVE_BUTTON_TEXT_KEY");
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog() {
        Context requireContext = requireContext();
        requireContext();
        int i = this.overrideThemeResId;
        if (i == 0) {
            getDateSelector();
            throw null;
        }
        Dialog dialog = new Dialog(requireContext, i);
        Context context = dialog.getContext();
        this.fullscreen = isFullscreen(context);
        int i2 = MaterialAttributes.resolveTypedValueOrThrow(context, MaterialDatePicker.class.getCanonicalName(), R.attr.colorSurface).data;
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(context, null, R.attr.materialCalendarStyle, 2132019117);
        this.background = materialShapeDrawable;
        materialShapeDrawable.initializeElevationOverlay(context);
        this.background.setFillColor(ColorStateList.valueOf(i2));
        MaterialShapeDrawable materialShapeDrawable2 = this.background;
        View decorView = dialog.getWindow().getDecorView();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        materialShapeDrawable2.setElevation(ViewCompat.Api21Impl.getElevation(decorView));
        return dialog;
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(this.fullscreen ? R.layout.mtrl_picker_fullscreen : R.layout.mtrl_picker_dialog, viewGroup);
        Context context = inflate.getContext();
        if (this.fullscreen) {
            inflate.findViewById(R.id.mtrl_calendar_frame).setLayoutParams(new LinearLayout.LayoutParams(getPaddedPickerWidth(context), -2));
        } else {
            inflate.findViewById(R.id.mtrl_calendar_main_pane).setLayoutParams(new LinearLayout.LayoutParams(getPaddedPickerWidth(context), -1));
        }
        TextView textView = (TextView) inflate.findViewById(R.id.mtrl_picker_header_selection_text);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api19Impl.setAccessibilityLiveRegion(textView, 1);
        this.headerToggleButton = (CheckableImageButton) inflate.findViewById(R.id.mtrl_picker_header_toggle);
        TextView textView2 = (TextView) inflate.findViewById(R.id.mtrl_picker_title_text);
        CharSequence charSequence = this.titleText;
        if (charSequence != null) {
            textView2.setText(charSequence);
        } else {
            textView2.setText(this.titleTextResId);
        }
        this.headerToggleButton.setTag("TOGGLE_BUTTON_TAG");
        CheckableImageButton checkableImageButton = this.headerToggleButton;
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, AppCompatResources.getDrawable(R.drawable.material_ic_calendar_black_24dp, context));
        stateListDrawable.addState(new int[0], AppCompatResources.getDrawable(R.drawable.material_ic_edit_black_24dp, context));
        checkableImageButton.setImageDrawable(stateListDrawable);
        this.headerToggleButton.setChecked(this.inputMode != 0);
        ViewCompat.setAccessibilityDelegate(this.headerToggleButton, null);
        CheckableImageButton checkableImageButton2 = this.headerToggleButton;
        this.headerToggleButton.setContentDescription(checkableImageButton2.isChecked() ? checkableImageButton2.getContext().getString(R.string.mtrl_picker_toggle_to_calendar_input_mode) : checkableImageButton2.getContext().getString(R.string.mtrl_picker_toggle_to_text_input_mode));
        this.headerToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialDatePicker.5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MaterialDatePicker materialDatePicker = MaterialDatePicker.this;
                Button button = materialDatePicker.confirmButton;
                materialDatePicker.getDateSelector();
                throw null;
            }
        });
        this.confirmButton = (Button) inflate.findViewById(R.id.confirm_button);
        getDateSelector();
        throw null;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        Iterator it = this.onDismissListeners.iterator();
        while (it.hasNext()) {
            ((DialogInterface.OnDismissListener) it.next()).onDismiss(dialogInterface);
        }
        ViewGroup viewGroup = (ViewGroup) this.mView;
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        super.onDismiss(dialogInterface);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("OVERRIDE_THEME_RES_ID", this.overrideThemeResId);
        bundle.putParcelable("DATE_SELECTOR_KEY", null);
        CalendarConstraints.Builder builder = new CalendarConstraints.Builder(this.calendarConstraints);
        Month month = this.calendar.current;
        if (month != null) {
            builder.openAt = Long.valueOf(month.timeInMillis);
        }
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("DEEP_COPY_VALIDATOR_KEY", builder.validator);
        Month create = Month.create(builder.start);
        Month create2 = Month.create(builder.end);
        CalendarConstraints.DateValidator dateValidator = (CalendarConstraints.DateValidator) bundle2.getParcelable("DEEP_COPY_VALIDATOR_KEY");
        Long l = builder.openAt;
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", new CalendarConstraints(create, create2, dateValidator, l != null ? Month.create(l.longValue()) : null, builder.firstDayOfWeek));
        bundle.putInt("TITLE_TEXT_RES_ID_KEY", this.titleTextResId);
        bundle.putCharSequence("TITLE_TEXT_KEY", this.titleText);
        bundle.putInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY", this.positiveButtonTextResId);
        bundle.putCharSequence("POSITIVE_BUTTON_TEXT_KEY", this.positiveButtonText);
        bundle.putInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY", this.negativeButtonTextResId);
        bundle.putCharSequence("NEGATIVE_BUTTON_TEXT_KEY", this.negativeButtonText);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStart() {
        PickerFragment pickerFragment;
        super.onStart();
        Window window = requireDialog().getWindow();
        if (this.fullscreen) {
            window.setLayout(-1, -1);
            window.setBackgroundDrawable(this.background);
            if (!this.edgeToEdgeEnabled) {
                final View findViewById = requireView().findViewById(R.id.fullscreen_header);
                Integer valueOf = findViewById.getBackground() instanceof ColorDrawable ? Integer.valueOf(((ColorDrawable) findViewById.getBackground()).getColor()) : null;
                boolean z = valueOf == null || valueOf.intValue() == 0;
                int color = MaterialColors.getColor(android.R.attr.colorBackground, window.getContext(), EmergencyPhoneWidget.BG_COLOR);
                if (z) {
                    valueOf = Integer.valueOf(color);
                }
                Integer valueOf2 = Integer.valueOf(color);
                window.setDecorFitsSystemWindows(false);
                window.getContext();
                window.getContext();
                window.setStatusBarColor(0);
                window.setNavigationBarColor(0);
                EdgeToEdgeUtils.setLightStatusBar(window, MaterialColors.isColorLight(0) || MaterialColors.isColorLight(valueOf.intValue()));
                boolean z2 = MaterialColors.isColorLight(0) || MaterialColors.isColorLight(valueOf2.intValue());
                WindowInsetsControllerCompat.Impl30 impl30 = new WindowInsetsControllerCompat(window, window.getDecorView()).mImpl;
                WindowInsetsController windowInsetsController = impl30.mInsetsController;
                Window window2 = impl30.mWindow;
                if (z2) {
                    if (window2 != null) {
                        View decorView = window2.getDecorView();
                        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 16);
                    }
                    windowInsetsController.setSystemBarsAppearance(16, 16);
                } else {
                    if (window2 != null) {
                        View decorView2 = window2.getDecorView();
                        decorView2.setSystemUiVisibility(decorView2.getSystemUiVisibility() & (-17));
                    }
                    windowInsetsController.setSystemBarsAppearance(0, 16);
                }
                final int paddingTop = findViewById.getPaddingTop();
                final int i = findViewById.getLayoutParams().height;
                OnApplyWindowInsetsListener onApplyWindowInsetsListener = new OnApplyWindowInsetsListener(this) { // from class: com.google.android.material.datepicker.MaterialDatePicker.3
                    @Override // androidx.core.view.OnApplyWindowInsetsListener
                    public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                        int i2 = windowInsetsCompat.getInsets(7).top;
                        View view2 = findViewById;
                        int i3 = i;
                        if (i3 >= 0) {
                            view2.getLayoutParams().height = i3 + i2;
                            view2.setLayoutParams(view2.getLayoutParams());
                        }
                        view2.setPadding(view2.getPaddingLeft(), paddingTop + i2, view2.getPaddingRight(), view2.getPaddingBottom());
                        return windowInsetsCompat;
                    }
                };
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(findViewById, onApplyWindowInsetsListener);
                this.edgeToEdgeEnabled = true;
            }
        } else {
            window.setLayout(-2, -2);
            int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.mtrl_calendar_dialog_background_inset);
            Rect rect = new Rect(dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset);
            window.setBackgroundDrawable(new InsetDrawable((Drawable) this.background, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset));
            window.getDecorView().setOnTouchListener(new InsetDialogOnTouchListener(requireDialog(), rect));
        }
        requireContext();
        int i2 = this.overrideThemeResId;
        if (i2 == 0) {
            getDateSelector();
            throw null;
        }
        getDateSelector();
        CalendarConstraints calendarConstraints = this.calendarConstraints;
        MaterialCalendar materialCalendar = new MaterialCalendar();
        Bundle bundle = new Bundle();
        bundle.putInt("THEME_RES_ID_KEY", i2);
        bundle.putParcelable("GRID_SELECTOR_KEY", null);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", calendarConstraints);
        bundle.putParcelable("CURRENT_MONTH_KEY", calendarConstraints.openAt);
        materialCalendar.setArguments(bundle);
        this.calendar = materialCalendar;
        if (this.headerToggleButton.isChecked()) {
            getDateSelector();
            CalendarConstraints calendarConstraints2 = this.calendarConstraints;
            pickerFragment = new MaterialTextInputPicker();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("THEME_RES_ID_KEY", i2);
            bundle2.putParcelable("DATE_SELECTOR_KEY", null);
            bundle2.putParcelable("CALENDAR_CONSTRAINTS_KEY", calendarConstraints2);
            pickerFragment.setArguments(bundle2);
        } else {
            pickerFragment = this.calendar;
        }
        this.pickerFragment = pickerFragment;
        getDateSelector();
        getContext();
        throw null;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStop() {
        this.pickerFragment.onSelectionChangedListeners.clear();
        super.onStop();
    }
}
