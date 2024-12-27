package com.android.server.inputmethod;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.android.internal.R;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.wm.WindowManagerInternal;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.SemEnterpriseDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.widget.SemHoverPopupWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class InputMethodMenuController {
    public AlertDialog.Builder mDialogBuilder;
    public InputMethodDialogWindowContext mDialogWindowContext;
    public InputMethodInfo[] mIms;
    public final SemInputMethodMenuControllerUtil mSemImcUtil;
    public final InputMethodManagerService mService;
    public boolean mShowImeWithHardKeyboard;
    public int[] mSubtypeIds;
    public View mSwitchInSelectDialogView;
    public AlertDialog mSwitchingDialog;

    public final class ImeSubtypeListAdapter extends ArrayAdapter {
        public int mCheckedItem;
        public final Context mContext;
        public final LayoutInflater mInflater;
        public final List mItemsList;
        public final SemInputMethodMenuControllerUtil mSemImcUtil;
        public final int mTextViewResourceId;
        public int mUserId;

        public ImeSubtypeListAdapter(int i, Context context, List list) {
            super(context, 17367483, list);
            this.mTextViewResourceId = 17367483;
            this.mItemsList = list;
            this.mCheckedItem = i;
            this.mInflater = LayoutInflater.from(context);
            this.mContext = context;
            this.mSemImcUtil = new SemInputMethodMenuControllerUtil();
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.view.View getView(
                int r22, android.view.View r23, android.view.ViewGroup r24) {
            /*
                Method dump skipped, instructions count: 578
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.inputmethod.InputMethodMenuController.ImeSubtypeListAdapter.getView(int,"
                        + " android.view.View, android.view.ViewGroup):android.view.View");
        }
    }

    public InputMethodMenuController(InputMethodManagerService inputMethodManagerService) {
        this.mService = inputMethodManagerService;
        WindowManagerInternal windowManagerInternal =
                (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        InputMethodMenuController$$ExternalSyntheticLambda0
                inputMethodMenuController$$ExternalSyntheticLambda0 =
                        new InputMethodMenuController$$ExternalSyntheticLambda0(this);
        SemInputMethodMenuControllerUtil semInputMethodMenuControllerUtil =
                new SemInputMethodMenuControllerUtil();
        semInputMethodMenuControllerUtil.mService = inputMethodManagerService;
        semInputMethodMenuControllerUtil.mWindowManagerInternal = windowManagerInternal;
        semInputMethodMenuControllerUtil.mSemInputMethodMenuListener =
                inputMethodMenuController$$ExternalSyntheticLambda0;
        this.mSemImcUtil = semInputMethodMenuControllerUtil;
    }

    public final void hideInputMethodMenuLocked() {
        AlertDialog alertDialog = this.mSwitchingDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mSwitchingDialog = null;
            InputMethodManagerService inputMethodManagerService = this.mService;
            inputMethodManagerService.updateSystemUiLocked$1();
            inputMethodManagerService.sendOnNavButtonFlagsChangedLocked();
            inputMethodManagerService.updateImeSwitchStatus("imeSwitcherHidden");
            this.mDialogBuilder = null;
            this.mIms = null;
        }
    }

    public final void showInputMethodMenuLocked(int i, String str, int i2, List list) {
        boolean isShowImeWithHardKeyboardEnabled;
        InputMethodSubtype currentInputMethodSubtypeLocked;
        InputMethodManagerService inputMethodManagerService = this.mService;
        inputMethodManagerService.getClass();
        hideInputMethodMenuLocked();
        int i3 = i2;
        if (i3 == -1
                && (currentInputMethodSubtypeLocked =
                                inputMethodManagerService.getCurrentInputMethodSubtypeLocked())
                        != null) {
            i3 =
                    SubtypeUtils.getSubtypeIdFromHashCode(
                            InputMethodSettingsRepository.get(
                                            inputMethodManagerService.mCurrentUserId)
                                    .mMethodMap
                                    .get(inputMethodManagerService.getSelectedMethodIdLocked()),
                            currentInputMethodSubtypeLocked.hashCode());
        }
        boolean isDesktopMode = SemDesktopModeManager.isDesktopMode();
        final SemInputMethodMenuControllerUtil semInputMethodMenuControllerUtil = this.mSemImcUtil;
        semInputMethodMenuControllerUtil.getClass();
        final int i4 = 0;
        InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem =
                (InputMethodSubtypeSwitchingController.ImeSubtypeListItem)
                        list.stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.server.inputmethod.SemInputMethodMenuControllerUtil$$ExternalSyntheticLambda4
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                InputMethodSubtypeSwitchingController
                                                                .ImeSubtypeListItem
                                                        imeSubtypeListItem2 =
                                                                (InputMethodSubtypeSwitchingController
                                                                                .ImeSubtypeListItem)
                                                                        obj;
                                                switch (i4) {
                                                    case 0:
                                                        return "com.samsung.android.honeyboard/.service.HoneyBoardService"
                                                                .equals(
                                                                        imeSubtypeListItem2.mImi
                                                                                .getId());
                                                    case 1:
                                                        String id =
                                                                imeSubtypeListItem2.mImi.getId();
                                                        return "com.samsung.android.honeyboard/.service.HoneyBoardService"
                                                                        .equals(id)
                                                                || "com.samsung.android.bixby.voiceinput/com.samsung.android.svoiceime.BixbyDictVoiceReco"
                                                                        .equals(id)
                                                                || "com.samsung.android.bixby.service/.dictation.DictationInputMethodService"
                                                                        .equals(id);
                                                    default:
                                                        String packageName =
                                                                imeSubtypeListItem2.mImi
                                                                        .getPackageName();
                                                        return (KnoxCustomManagerService
                                                                                .SAMSUNG_HONEYBOARD_PKG_NAME
                                                                                .equals(packageName)
                                                                        || "com.google.android.googlequicksearchbox"
                                                                                .equals(
                                                                                        packageName))
                                                                ? false
                                                                : true;
                                                }
                                            }
                                        })
                                .findFirst()
                                .orElse(null);
        final int i5 = 1;
        Predicate predicate =
                new Predicate() { // from class:
                                  // com.android.server.inputmethod.SemInputMethodMenuControllerUtil$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        InputMethodSubtypeSwitchingController.ImeSubtypeListItem
                                imeSubtypeListItem2 =
                                        (InputMethodSubtypeSwitchingController.ImeSubtypeListItem)
                                                obj;
                        switch (i5) {
                            case 0:
                                return "com.samsung.android.honeyboard/.service.HoneyBoardService"
                                        .equals(imeSubtypeListItem2.mImi.getId());
                            case 1:
                                String id = imeSubtypeListItem2.mImi.getId();
                                return "com.samsung.android.honeyboard/.service.HoneyBoardService"
                                                .equals(id)
                                        || "com.samsung.android.bixby.voiceinput/com.samsung.android.svoiceime.BixbyDictVoiceReco"
                                                .equals(id)
                                        || "com.samsung.android.bixby.service/.dictation.DictationInputMethodService"
                                                .equals(id);
                            default:
                                String packageName = imeSubtypeListItem2.mImi.getPackageName();
                                return (KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME.equals(
                                                        packageName)
                                                || "com.google.android.googlequicksearchbox"
                                                        .equals(packageName))
                                        ? false
                                        : true;
                        }
                    }
                };
        ArrayList arrayList = (ArrayList) list;
        arrayList.removeIf(predicate);
        arrayList.add(imeSubtypeListItem);
        if (isDesktopMode) {
            final int i6 = 2;
            arrayList.removeIf(
                    new Predicate() { // from class:
                                      // com.android.server.inputmethod.SemInputMethodMenuControllerUtil$$ExternalSyntheticLambda4
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            InputMethodSubtypeSwitchingController.ImeSubtypeListItem
                                    imeSubtypeListItem2 =
                                            (InputMethodSubtypeSwitchingController
                                                            .ImeSubtypeListItem)
                                                    obj;
                            switch (i6) {
                                case 0:
                                    return "com.samsung.android.honeyboard/.service.HoneyBoardService"
                                            .equals(imeSubtypeListItem2.mImi.getId());
                                case 1:
                                    String id = imeSubtypeListItem2.mImi.getId();
                                    return "com.samsung.android.honeyboard/.service.HoneyBoardService"
                                                    .equals(id)
                                            || "com.samsung.android.bixby.voiceinput/com.samsung.android.svoiceime.BixbyDictVoiceReco"
                                                    .equals(id)
                                            || "com.samsung.android.bixby.service/.dictation.DictationInputMethodService"
                                                    .equals(id);
                                default:
                                    String packageName = imeSubtypeListItem2.mImi.getPackageName();
                                    return (KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME
                                                            .equals(packageName)
                                                    || "com.google.android.googlequicksearchbox"
                                                            .equals(packageName))
                                            ? false
                                            : true;
                            }
                        }
                    });
        }
        int size = arrayList.size();
        this.mIms = new InputMethodInfo[size];
        this.mSubtypeIds = new int[size];
        int i7 = -1;
        for (int i8 = 0; i8 < size; i8++) {
            InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem2 =
                    (InputMethodSubtypeSwitchingController.ImeSubtypeListItem) arrayList.get(i8);
            if (imeSubtypeListItem2 == null) {
                Slog.i(
                        "InputMethodMenuController",
                        "showInputMethodMenu: index=" + i8 + ", imList=" + list);
            } else {
                InputMethodInfo[] inputMethodInfoArr = this.mIms;
                InputMethodInfo inputMethodInfo = imeSubtypeListItem2.mImi;
                inputMethodInfoArr[i8] = inputMethodInfo;
                this.mSubtypeIds[i8] = imeSubtypeListItem2.mSubtypeId;
                if (inputMethodInfo.getId().equals(str)) {
                    int i9 = this.mSubtypeIds[i8];
                    if (i9 == -1 || ((i3 == -1 && i9 == 0) || i9 == i3)) {
                        i7 = i8;
                    }
                    InputMethodInfo inputMethodInfo2 = this.mIms[i8];
                    if (inputMethodInfo2 == null
                            ? false
                            : "com.samsung.android.honeyboard/.service.HoneyBoardService"
                                    .equals(inputMethodInfo2.getId())) {
                        i7 = i8;
                    }
                }
            }
        }
        if (i7 == -1) {
            Slog.w(
                    "InputMethodMenuController",
                    "Switching menu shown with no item selected, IME id: "
                            + str
                            + ", subtype index: "
                            + i3);
        }
        if (this.mDialogWindowContext == null) {
            this.mDialogWindowContext = new InputMethodDialogWindowContext();
        }
        Context context = this.mDialogWindowContext.get(i);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        this.mDialogBuilder = builder;
        builder.setOnCancelListener(
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        InputMethodMenuController inputMethodMenuController =
                                InputMethodMenuController.this;
                        inputMethodMenuController.getClass();
                        synchronized (ImfLock.class) {
                            inputMethodMenuController.hideInputMethodMenuLocked();
                        }
                    }
                });
        Context context2 = this.mDialogBuilder.getContext();
        TypedArray obtainStyledAttributes =
                context2.obtainStyledAttributes(
                        null, R.styleable.DialogPreference, android.R.attr.alertDialogStyle, 0);
        Drawable drawable = obtainStyledAttributes.getDrawable(2);
        obtainStyledAttributes.recycle();
        this.mDialogBuilder.setIcon(drawable);
        LayoutInflater layoutInflater =
                (LayoutInflater) context2.getSystemService(LayoutInflater.class);
        this.mDialogBuilder.setCustomTitle(layoutInflater.inflate(17367481, (ViewGroup) null));
        View inflate = layoutInflater.inflate(17367480, (ViewGroup) null);
        this.mSwitchInSelectDialogView = inflate;
        AlertDialog.Builder builder2 = this.mDialogBuilder;
        View findViewById = inflate.findViewById(android.R.id.immersive_cling_back_bg);
        InputMethodManagerService inputMethodManagerService2 =
                semInputMethodMenuControllerUtil.mService;
        findViewById.setVisibility(inputMethodManagerService2.isAccessoryKeyboard() != 0 ? 0 : 8);
        final Switch r14 =
                (Switch) inflate.findViewById(android.R.id.immersive_cling_back_bg_light);
        final InputMethodSettings inputMethodSettings =
                InputMethodSettingsRepository.get(inputMethodManagerService2.mCurrentUserId);
        r14.setChecked(inputMethodSettings.isShowImeWithHardKeyboardEnabled());
        if (inputMethodManagerService2.isDexSetting()) {
            isShowImeWithHardKeyboardEnabled =
                    inputMethodManagerService2.getDexSettingsValue("keyboard_dex", "0");
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "getShowImeWithHardKeyboardValue for DEX: ",
                    "InputMethodManagerService",
                    isShowImeWithHardKeyboardEnabled);
        } else {
            isShowImeWithHardKeyboardEnabled =
                    InputMethodSettingsRepository.get(inputMethodManagerService2.mCurrentUserId)
                            .isShowImeWithHardKeyboardEnabled();
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "getShowImeWithHardKeyboardValue for Phone: ",
                    "InputMethodManagerService",
                    isShowImeWithHardKeyboardEnabled);
        }
        r14.setChecked(isShowImeWithHardKeyboardEnabled);
        r14.semSetSamsungBasicInteraction();
        r14.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() { // from class:
                    // com.android.server.inputmethod.SemInputMethodMenuControllerUtil$$ExternalSyntheticLambda0
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        SemInputMethodMenuControllerUtil semInputMethodMenuControllerUtil2 =
                                SemInputMethodMenuControllerUtil.this;
                        InputMethodManagerService inputMethodManagerService3 =
                                semInputMethodMenuControllerUtil2.mService;
                        InputMethodSettings inputMethodSettings2 =
                                InputMethodSettingsRepository.get(
                                        inputMethodManagerService3.mCurrentUserId);
                        String str2 = z ? "1" : "0";
                        if (inputMethodManagerService3.isDexSetting()) {
                            Slog.d(
                                    "InputMethodManagerService",
                                    "setShowImeWithHardKeyboardValue for DEX: "
                                            + inputMethodManagerService3.getDexSettingsValue(
                                                    "keyboard_dex", "0"));
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                InputMethodManagerService.setDexSettings(
                                        inputMethodManagerService3.mContext.getContentResolver(),
                                        str2);
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        } else {
                            Slog.d(
                                    "InputMethodManagerService",
                                    "setShowImeWithHardKeyboardValue for Phone: "
                                            + inputMethodSettings2
                                                    .isShowImeWithHardKeyboardEnabled());
                            inputMethodSettings2.setShowImeWithHardKeyboard(z);
                        }
                        InputMethodMenuController$$ExternalSyntheticLambda0
                                inputMethodMenuController$$ExternalSyntheticLambda0 =
                                        semInputMethodMenuControllerUtil2
                                                .mSemInputMethodMenuListener;
                        if (inputMethodMenuController$$ExternalSyntheticLambda0 != null) {
                            InputMethodMenuController inputMethodMenuController =
                                    inputMethodMenuController$$ExternalSyntheticLambda0.f$0;
                            inputMethodMenuController.getClass();
                            synchronized (ImfLock.class) {
                                inputMethodMenuController.hideInputMethodMenuLocked();
                            }
                        }
                        new Thread(
                                        new SemInputMethodMenuControllerUtil$$ExternalSyntheticLambda8(
                                                "com.samsung.android.intent.action.ONSCREENKEYBOARD_SWITCH",
                                                Boolean.valueOf(z),
                                                compoundButton.getContext()))
                                .start();
                    }
                });
        final int i10 = 0;
        findViewById.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.server.inputmethod.SemInputMethodMenuControllerUtil$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i11 = i10;
                        Switch r0 = r14;
                        switch (i11) {
                            case 0:
                                r0.toggle();
                                break;
                            default:
                                r0.toggle();
                                break;
                        }
                    }
                });
        View findViewById2 = inflate.findViewById(android.R.id.words);
        boolean isDeskTopMode = inputMethodManagerService2.isDeskTopMode();
        WindowManagerInternal windowManagerInternal =
                semInputMethodMenuControllerUtil.mWindowManagerInternal;
        boolean z =
                windowManagerInternal.isKeyguardLocked()
                        && windowManagerInternal.isKeyguardSecure(
                                inputMethodManagerService2.mCurrentUserId);
        Bundle bundle =
                SemEnterpriseDeviceManager.getInstance(context2)
                        .getApplicationRestrictions(KnoxCustomManagerService.SETTING_PKG_NAME)
                        .getBundle("key_show_keyboard_button");
        boolean z2 =
                (isDeskTopMode
                                || (bundle != null && bundle.getBoolean("hide"))
                                || SemEmergencyManager.isEmergencyMode(context2)
                                || z)
                        ? false
                        : true;
        findViewById2.setVisibility(z2 ? 0 : 8);
        TextView textView = (TextView) inflate.findViewById(android.R.id.loose);
        String format =
                String.format(
                        textView.getText().toString(), context2.getResources().getString(17043192));
        textView.setContentDescription(format);
        Resources resources = context2.getResources();
        String string = resources.getString(17043028);
        SpannableString spannableString = new SpannableString(string);
        int i11 = i7;
        int indexOf = string.indexOf("%s");
        int color = resources.getColor(17171596, null);
        Drawable drawable2 = resources.getDrawable(17304695, null);
        drawable2.setTint(color);
        drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
        spannableString.setSpan(new ImageSpan(drawable2), indexOf, indexOf + 2, 17);
        textView.setText(spannableString);
        textView.semSetHoverPopupType(1);
        SemHoverPopupWindow semGetHoverPopup = textView.semGetHoverPopup(true);
        if (semGetHoverPopup != null) {
            semGetHoverPopup.setGravity(12849);
            semGetHoverPopup.setContent(format);
        }
        final Switch r0 = (Switch) inflate.findViewById(android.R.id.work_widget_app_icon);
        r0.semSetSamsungBasicInteraction();
        Bundle bundle2 =
                SemEnterpriseDeviceManager.getInstance(context2)
                        .getApplicationRestrictions(KnoxCustomManagerService.SETTING_PKG_NAME)
                        .getBundle("key_show_keyboard_button");
        r0.setEnabled(!(z2 && bundle2 != null && bundle2.getBoolean("grayout")));
        r0.setChecked(z2 && inputMethodSettings.isShowKeyboardButton());
        r0.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() { // from class:
                    // com.android.server.inputmethod.SemInputMethodMenuControllerUtil$$ExternalSyntheticLambda2
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                        SemInputMethodMenuControllerUtil semInputMethodMenuControllerUtil2 =
                                SemInputMethodMenuControllerUtil.this;
                        InputMethodSettings inputMethodSettings2 = inputMethodSettings;
                        semInputMethodMenuControllerUtil2.getClass();
                        SecureSettingsWrapper.get(inputMethodSettings2.mUserId)
                                .putInt(z3 ? 1 : 0, "show_keyboard_button");
                        InputMethodMenuController$$ExternalSyntheticLambda0
                                inputMethodMenuController$$ExternalSyntheticLambda0 =
                                        semInputMethodMenuControllerUtil2
                                                .mSemInputMethodMenuListener;
                        if (inputMethodMenuController$$ExternalSyntheticLambda0 != null) {
                            InputMethodMenuController inputMethodMenuController =
                                    inputMethodMenuController$$ExternalSyntheticLambda0.f$0;
                            inputMethodMenuController.getClass();
                            synchronized (ImfLock.class) {
                                inputMethodMenuController.hideInputMethodMenuLocked();
                            }
                        }
                        new Thread(
                                        new SemInputMethodMenuControllerUtil$$ExternalSyntheticLambda8(
                                                "com.samsung.android.intent.action.KEYBOARDBUTTON_SWITCH",
                                                Boolean.valueOf(z3),
                                                compoundButton.getContext()))
                                .start();
                    }
                });
        final int i12 = 1;
        findViewById2.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.server.inputmethod.SemInputMethodMenuControllerUtil$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i112 = i12;
                        Switch r02 = r0;
                        switch (i112) {
                            case 0:
                                r02.toggle();
                                break;
                            default:
                                r02.toggle();
                                break;
                        }
                    }
                });
        int i13 = inputMethodManagerService2.mSamsungIMMSHWKeyboard.keyboardState;
        boolean z3 = (i13 & 15) != 0;
        boolean z4 = (i13 & 128) != 0;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "isConnectedDexOnPC ", "InputMethodManagerServicePhysicalKey", z4);
        if (z3 || z4 || inputMethodManagerService2.mRes.getConfiguration().keyboard == 2 || z2) {
            builder2.setView(inflate);
        }
        final ImeSubtypeListAdapter imeSubtypeListAdapter =
                new ImeSubtypeListAdapter(i11, context2, list);
        imeSubtypeListAdapter.mUserId = inputMethodManagerService.mCurrentUserId;
        this.mDialogBuilder.setSingleChoiceItems(
                imeSubtypeListAdapter,
                i11,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i14) {
                        int[] iArr;
                        InputMethodMenuController inputMethodMenuController =
                                InputMethodMenuController.this;
                        InputMethodMenuController.ImeSubtypeListAdapter imeSubtypeListAdapter2 =
                                imeSubtypeListAdapter;
                        inputMethodMenuController.getClass();
                        synchronized (ImfLock.class) {
                            try {
                                InputMethodInfo[] inputMethodInfoArr2 =
                                        inputMethodMenuController.mIms;
                                if (inputMethodInfoArr2 != null
                                        && inputMethodInfoArr2.length > i14
                                        && (iArr = inputMethodMenuController.mSubtypeIds) != null
                                        && iArr.length > i14) {
                                    InputMethodInfo inputMethodInfo3 = inputMethodInfoArr2[i14];
                                    int i15 = iArr[i14];
                                    imeSubtypeListAdapter2.mCheckedItem = i14;
                                    imeSubtypeListAdapter2.notifyDataSetChanged();
                                    if (inputMethodInfo3 != null) {
                                        if (i15 >= 0) {
                                            if (i15 >= inputMethodInfo3.getSubtypeCount()) {}
                                            inputMethodMenuController.mService.setInputMethodLocked(
                                                    i15, 0, inputMethodInfo3.getId());
                                        }
                                        i15 = -1;
                                        inputMethodMenuController.mService.setInputMethodLocked(
                                                i15, 0, inputMethodInfo3.getId());
                                    }
                                    inputMethodMenuController.hideInputMethodMenuLocked();
                                }
                            } finally {
                            }
                        }
                    }
                });
        AlertDialog create = this.mDialogBuilder.create();
        this.mSwitchingDialog = create;
        create.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda3
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        InputMethodMenuController inputMethodMenuController =
                                InputMethodMenuController.this;
                        AlertDialog alertDialog = inputMethodMenuController.mSwitchingDialog;
                        if (alertDialog != null) {
                            Button button = alertDialog.getButton(-1);
                            InputMethodManagerService inputMethodManagerService3 =
                                    inputMethodMenuController.mService;
                            float f = inputMethodManagerService3.mRes.getConfiguration().fontScale;
                            float textSize =
                                    button.getTextSize()
                                            / inputMethodManagerService3.mRes.getDisplayMetrics()
                                                    .scaledDensity;
                            if (f > 1.2f) {
                                f = 1.2f;
                            }
                            button.setTextSize(1, textSize * f);
                        }
                    }
                });
        if (inputMethodManagerService.isDEXStandAloneMode()) {
            this.mSwitchingDialog.getListView().setEnabled(false);
        }
        this.mSwitchingDialog.setCanceledOnTouchOutside(true);
        Window window = this.mSwitchingDialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        window.setType(2012);
        window.setHideOverlayWindows(true);
        attributes.token = context.getWindowContextToken();
        attributes.privateFlags |= 16;
        attributes.setTitle("Select input method");
        window.setAttributes(attributes);
        inputMethodManagerService.updateSystemUiLocked$1();
        inputMethodManagerService.sendOnNavButtonFlagsChangedLocked();
        this.mSwitchingDialog.show();
        inputMethodManagerService.updateImeSwitchStatus("imeSwitcherShown");
    }

    public final void updateKeyboardFromSettingsLocked() {
        this.mShowImeWithHardKeyboard =
                InputMethodSettingsRepository.get(this.mService.mCurrentUserId)
                        .isShowImeWithHardKeyboardEnabled();
        AlertDialog alertDialog = this.mSwitchingDialog;
        if (alertDialog == null
                || this.mSwitchInSelectDialogView == null
                || !alertDialog.isShowing()) {
            return;
        }
        ((Switch)
                        this.mSwitchInSelectDialogView.findViewById(
                                android.R.id.immersive_cling_back_bg_light))
                .setChecked(this.mShowImeWithHardKeyboard);
    }
}
