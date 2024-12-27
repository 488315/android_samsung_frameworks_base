package com.android.systemui.volume.view.warnings;

import android.app.Presentation;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0;
import com.android.systemui.volume.view.warnings.VolumeWarningCameraViewPresentation;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class VolumeWarningCameraViewPresentation extends Presentation implements VolumeObserver {
    public final WarningDialogType dialogType;
    public final StoreInteractor storeInteractor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public VolumeWarningCameraViewPresentation(Context context, Display display, WarningDialogType warningDialogType) {
        super(context, display, R.style.Theme_SystemUI_VolumePanelPresentation);
        this.dialogType = warningDialogType;
        this.storeInteractor = new StoreInteractor(this, null);
        setContentView(R.layout.volume_warning_camera_view_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.flags |= 2230016;
        attributes.type = 2037;
        attributes.semAddPrivateFlags(16);
        attributes.semSetScreenTimeout(6000L);
        attributes.semSetScreenDimDuration(0L);
        attributes.setTitle("VolumeWarningCameraViewPresentation");
        window.setAttributes(attributes);
        getWindow().setBackgroundDrawable(new ColorDrawable(-16777216));
        getWindow().setLayout(-1, -1);
        if (warningDialogType == WarningDialogType.VOLUME_CSD_100_WARNING) {
            Button button = (Button) requireViewById(R.id.negative_button);
            ViewVisibilityUtil.INSTANCE.getClass();
            ViewVisibilityUtil.setGone(button);
        }
        TextView textView = (TextView) requireViewById(R.id.volume_warning_camera_view_dialog_text_view);
        TextView textView2 = (TextView) requireViewById(R.id.positive_button);
        int i = WhenMappings.$EnumSwitchMapping$0[warningDialogType.ordinal()];
        if (i == 1) {
            textView.setText(getContext().getString(R.string.volume_safety_warning_text_for_cover));
            textView2.setText(getContext().getString(R.string.volume_safety_warning_increase_button));
        } else if (i == 2) {
            textView.setText(getContext().getString(R.string.volume_limiter_warning_text_for_cover));
            textView2.setText(getContext().getString(R.string.volume_limiter_button_settings));
        } else if (i == 3) {
            textView.setText(getContext().getString(R.string.tw_volume_csd_100_warning));
            textView2.setText(getContext().getString(android.R.string.yes));
        }
        ((Button) requireViewById(R.id.negative_button)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.warnings.VolumeWarningCameraViewPresentation$setClickListener$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[VolumeWarningCameraViewPresentation.WarningDialogType.values().length];
                    try {
                        iArr[VolumeWarningCameraViewPresentation.WarningDialogType.DEFAULT_SAFETY_VOLUME_WARNING.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[VolumeWarningCameraViewPresentation.WarningDialogType.MEDIA_VOLUME_LIMITER_WARNING.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = WhenMappings.$EnumSwitchMapping$0[VolumeWarningCameraViewPresentation.this.dialogType.ordinal()];
                if (i2 == 1) {
                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_SAFETY_WARNING_DIALOG_CANCEL_CLICKED), true, VolumeWarningCameraViewPresentation.this.storeInteractor, false);
                } else {
                    if (i2 != 2) {
                        return;
                    }
                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED), true, VolumeWarningCameraViewPresentation.this.storeInteractor, false);
                }
            }
        });
        ((Button) requireViewById(R.id.positive_button)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.warnings.VolumeWarningCameraViewPresentation$setClickListener$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[VolumeWarningCameraViewPresentation.WarningDialogType.values().length];
                    try {
                        iArr[VolumeWarningCameraViewPresentation.WarningDialogType.DEFAULT_SAFETY_VOLUME_WARNING.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[VolumeWarningCameraViewPresentation.WarningDialogType.MEDIA_VOLUME_LIMITER_WARNING.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[VolumeWarningCameraViewPresentation.WarningDialogType.VOLUME_CSD_100_WARNING.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = WhenMappings.$EnumSwitchMapping$0[VolumeWarningCameraViewPresentation.this.dialogType.ordinal()];
                if (i2 == 1) {
                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED), true, VolumeWarningCameraViewPresentation.this.storeInteractor, false);
                } else if (i2 == 2) {
                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED), true, VolumeWarningCameraViewPresentation.this.storeInteractor, false);
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED), true, VolumeWarningCameraViewPresentation.this.storeInteractor, false);
                }
            }
        });
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
                View requireViewById = requireViewById(R.id.volume_warning_camera_view_dialog_view_container);
                viewVisibilityUtil.getClass();
                ViewVisibilityUtil.setGone(requireViewById);
                requireViewById(R.id.volume_warning_camera_view_toast_text_view).setVisibility(0);
                getWindow().setBackgroundDrawable(new ColorDrawable(-16777216));
                break;
        }
    }
}
