package com.android.systemui.volume.view.warnings;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.util.ContextUtils;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0;
import com.android.systemui.volume.view.warnings.VolumeWarningWalletMiniDialog;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

public final class VolumeWarningWalletMiniDialog extends Dialog implements VolumeObserver {
    public final WarningDialogType dialogType;
    public final StoreInteractor storeInteractor;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class WarningDialogType {
        public static final /* synthetic */ WarningDialogType[] $VALUES;
        public static final WarningDialogType DEFAULT_SAFETY_VOLUME_WARNING;
        public static final WarningDialogType MEDIA_VOLUME_LIMITER_WARNING;
        public static final WarningDialogType VOLUME_CSD_100_WARNING;

        static {
            WarningDialogType warningDialogType = new WarningDialogType("DEFAULT_SAFETY_VOLUME_WARNING", 0);
            DEFAULT_SAFETY_VOLUME_WARNING = warningDialogType;
            WarningDialogType warningDialogType2 = new WarningDialogType("MEDIA_VOLUME_LIMITER_WARNING", 1);
            MEDIA_VOLUME_LIMITER_WARNING = warningDialogType2;
            WarningDialogType warningDialogType3 = new WarningDialogType("VOLUME_CSD_100_WARNING", 2);
            VOLUME_CSD_100_WARNING = warningDialogType3;
            WarningDialogType[] warningDialogTypeArr = {warningDialogType, warningDialogType2, warningDialogType3};
            $VALUES = warningDialogTypeArr;
            EnumEntriesKt.enumEntries(warningDialogTypeArr);
        }

        private WarningDialogType(String str, int i) {
        }

        public static WarningDialogType valueOf(String str) {
            return (WarningDialogType) Enum.valueOf(WarningDialogType.class, str);
        }

        public static WarningDialogType[] values() {
            return (WarningDialogType[]) $VALUES.clone();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[WarningDialogType.values().length];
            try {
                iArr[WarningDialogType.DEFAULT_SAFETY_VOLUME_WARNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WarningDialogType.MEDIA_VOLUME_LIMITER_WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[WarningDialogType.VOLUME_CSD_100_WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[VolumePanelState.StateType.values().length];
            try {
                iArr2[VolumePanelState.StateType.STATE_DISMISS.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_COVER_STATE_CHANGED.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_VOLUME_SAFETY_WARNING_DIALOG_CANCEL_CLICKED.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_VOLUME_SAFETY_WARNING_DIALOG_FLAG_DISMISS.ordinal()] = 6;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_VOLUME_CSD_100_WARNING_DIALOG_FLAG_DISMISS.ordinal()] = 7;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED.ordinal()] = 8;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED.ordinal()] = 9;
            } catch (NoSuchFieldError unused12) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        new Companion(null);
    }

    public VolumeWarningWalletMiniDialog(Context context, WarningDialogType warningDialogType) {
        super(context, R.style.Theme_SystemUI_Dialog_VolumeWarningDialog);
        this.dialogType = warningDialogType;
        this.storeInteractor = new StoreInteractor(this, null);
        setContentView(R.layout.volume_warning_wallet_mini_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.layoutInDisplayCutoutMode = 1;
        attributes.semAddPrivateFlags(16);
        attributes.semSetScreenTimeout(6000L);
        attributes.semSetScreenDimDuration(0L);
        attributes.setTitle("VolumeWarningWalletMiniDialog");
        window.setAttributes(attributes);
        getWindow().addFlags(131072);
        getWindow().addFlags(1024);
        getWindow().addFlags(256);
        getWindow().addFlags(512);
        getWindow().setBackgroundDrawable(new ColorDrawable(-16777216));
        getWindow().setType(2099);
        getWindow().setLayout(-1, -1);
        ContextUtils contextUtils = ContextUtils.INSTANCE;
        Context context2 = getContext();
        contextUtils.getClass();
        float displayWidth = ((ContextUtils.getDisplayWidth(getContext()) / 360.0f) * 160) / context2.getResources().getConfiguration().densityDpi;
        ViewGroup viewGroup = (ViewGroup) requireViewById(R.id.volume_warning_wallet_mini_contents);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
        marginLayoutParams.topMargin = Math.round(getContext().getResources().getDimensionPixelSize(R.dimen.volume_warning_popup_top_padding_wallet_mini) * displayWidth);
        viewGroup.setLayoutParams(marginLayoutParams);
        scaleWidthAndHeight(displayWidth, viewGroup);
        scaleWidthAndHeight(displayWidth, requireViewById(R.id.volume_warning_wallet_mini_dialog_buttons));
        Button button = (Button) findViewById(R.id.negative_button);
        if (button != null) {
            button.setTextSize(0, button.getTextSize() * displayWidth);
        }
        Button button2 = (Button) findViewById(R.id.positive_button);
        if (button2 != null) {
            button2.setTextSize(0, button2.getTextSize() * displayWidth);
            if (warningDialogType == WarningDialogType.VOLUME_CSD_100_WARNING) {
                ViewVisibilityUtil.INSTANCE.getClass();
                button2.setVisibility(8);
            }
        }
        TextView textView = (TextView) findViewById(R.id.volume_warning_wallet_mini_dialog_text_view);
        if (textView != null) {
            textView.setTextSize(0, textView.getTextSize() * displayWidth);
            scaleWidthAndHeight(displayWidth, textView);
        }
        TextView textView2 = (TextView) findViewById(R.id.volume_warning_toast_text_view);
        if (textView2 != null) {
            textView2.setTextSize(0, textView2.getTextSize() * displayWidth);
            scaleWidthAndHeight(displayWidth, textView2);
        }
        TextView textView3 = (TextView) requireViewById(R.id.volume_warning_wallet_mini_dialog_text_view);
        TextView textView4 = (TextView) requireViewById(R.id.positive_button);
        int i = WhenMappings.$EnumSwitchMapping$0[warningDialogType.ordinal()];
        if (i == 1) {
            textView3.setText(getContext().getString(R.string.volume_safety_warning_text_for_cover));
            textView4.setText(getContext().getString(R.string.volume_safety_warning_increase_button));
        } else if (i == 2) {
            textView3.setText(getContext().getString(R.string.volume_limiter_warning_text_for_cover));
            textView4.setText(getContext().getString(R.string.volume_limiter_button_settings));
        } else if (i == 3) {
            textView3.setText(getContext().getString(R.string.tw_volume_csd_100_warning));
            textView4.setText(getContext().getString(android.R.string.yes));
        }
        ((Button) requireViewById(R.id.negative_button)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.warnings.VolumeWarningWalletMiniDialog$setClickListener$1

            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[VolumeWarningWalletMiniDialog.WarningDialogType.values().length];
                    try {
                        iArr[VolumeWarningWalletMiniDialog.WarningDialogType.DEFAULT_SAFETY_VOLUME_WARNING.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[VolumeWarningWalletMiniDialog.WarningDialogType.MEDIA_VOLUME_LIMITER_WARNING.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = WhenMappings.$EnumSwitchMapping$0[VolumeWarningWalletMiniDialog.this.dialogType.ordinal()];
                if (i2 == 1) {
                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_SAFETY_WARNING_DIALOG_CANCEL_CLICKED), true, VolumeWarningWalletMiniDialog.this.storeInteractor, false);
                } else {
                    if (i2 != 2) {
                        return;
                    }
                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED), true, VolumeWarningWalletMiniDialog.this.storeInteractor, false);
                }
            }
        });
        ((Button) requireViewById(R.id.positive_button)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.warnings.VolumeWarningWalletMiniDialog$setClickListener$2

            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[VolumeWarningWalletMiniDialog.WarningDialogType.values().length];
                    try {
                        iArr[VolumeWarningWalletMiniDialog.WarningDialogType.DEFAULT_SAFETY_VOLUME_WARNING.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[VolumeWarningWalletMiniDialog.WarningDialogType.MEDIA_VOLUME_LIMITER_WARNING.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[VolumeWarningWalletMiniDialog.WarningDialogType.VOLUME_CSD_100_WARNING.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = WhenMappings.$EnumSwitchMapping$0[VolumeWarningWalletMiniDialog.this.dialogType.ordinal()];
                if (i2 == 1) {
                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED), true, VolumeWarningWalletMiniDialog.this.storeInteractor, false);
                } else if (i2 == 2) {
                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED), true, VolumeWarningWalletMiniDialog.this.storeInteractor, false);
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED), true, VolumeWarningWalletMiniDialog.this.storeInteractor, false);
                }
            }
        });
    }

    public static void scaleWidthAndHeight(float f, View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            int i = layoutParams.width;
            if (i > 0) {
                layoutParams.width = MathKt__MathJVMKt.roundToInt(i * f);
            }
            int i2 = layoutParams.height;
            if (i2 > 0) {
                layoutParams.height = MathKt__MathJVMKt.roundToInt(i2 * f);
            }
            view.setLayoutParams(layoutParams);
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        super.dismiss();
        int i = WhenMappings.$EnumSwitchMapping$0[this.dialogType.ordinal()];
        if (i == 1) {
            this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_SAFETY_WARNING_DIALOG).build(), true);
        } else if (i == 2) {
            this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_LIMITER_DIALOG).build(), true);
        } else if (i == 3) {
            this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_CSD_100_WARNING_DIALOG).build(), true);
        }
        this.storeInteractor.dispose();
    }

    @Override // android.app.Dialog
    public final Window getWindow() {
        Window window = super.getWindow();
        Intrinsics.checkNotNull(window);
        return window;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(Object obj) {
        switch (WhenMappings.$EnumSwitchMapping$1[((VolumePanelState) obj).getStateType().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                dismiss();
                break;
            case 9:
                ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
                View requireViewById = requireViewById(R.id.volume_warning_wallet_mini_dialog_text_view);
                viewVisibilityUtil.getClass();
                ViewVisibilityUtil.setGone(requireViewById);
                ViewVisibilityUtil.setGone(requireViewById(R.id.volume_warning_wallet_mini_dialog_buttons));
                requireViewById(R.id.volume_warning_toast_text_view).setVisibility(0);
                getWindow().setBackgroundDrawable(new ColorDrawable(-872415232));
                break;
        }
    }
}
