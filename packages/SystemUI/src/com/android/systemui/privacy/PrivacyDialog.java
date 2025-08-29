package com.android.systemui.privacy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.ScRune;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.privacy.PrivacyDialog;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.privacy.logging.PrivacyLogger$$ExternalSyntheticLambda0;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.view.SemWindowManager;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class PrivacyDialog extends SystemUIDialog {
    public final Function4 activityStarter;
    public final PrivacyDialog$clickListener$1 clickListener;
    public final List dismissListeners;
    public final AtomicBoolean dismissed;
    public final int iconColorSolid;
    public final List list;
    public ImageView mBlurView;
    public int mDialogTopMargin;
    public SemPersonaManager mPersonaManager;
    public final String phonecall;
    public boolean qsExpanded;
    public ViewGroup rootView;

    public final class PrivacyElement {
        public final boolean active;
        public final CharSequence applicationName;
        public final CharSequence attributionLabel;
        public final CharSequence attributionTag;
        public final StringBuilder builder;
        public final boolean enterprise;
        public final boolean isSystem;
        public final long lastActiveTimestamp;
        public final Intent navigationIntent;
        public final String packageName;
        public final CharSequence permGroupName;
        public final boolean phoneCall;
        public final CharSequence proxyLabel;
        public final PrivacyType type;
        public final int userId;

        public PrivacyElement(PrivacyType privacyType, String str, int i, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, long j, boolean z, boolean z2, boolean z3, CharSequence charSequence5, Intent intent, boolean z4) {
            this.type = privacyType;
            this.packageName = str;
            this.userId = i;
            this.applicationName = charSequence;
            this.attributionTag = charSequence2;
            this.attributionLabel = charSequence3;
            this.proxyLabel = charSequence4;
            this.lastActiveTimestamp = j;
            this.active = z;
            this.enterprise = z2;
            this.phoneCall = z3;
            this.permGroupName = charSequence5;
            this.navigationIntent = intent;
            this.isSystem = z4;
            StringBuilder sb = new StringBuilder("PrivacyElement(");
            this.builder = sb;
            sb.append("type=" + privacyType.getLogName());
            sb.append(", packageName=" + str);
            sb.append(", userId=" + i);
            sb.append(", appName=" + ((Object) charSequence));
            if (charSequence2 != null) {
                sb.append(", attributionTag=" + ((Object) charSequence2));
            }
            if (charSequence3 != null) {
                sb.append(", attributionLabel=" + ((Object) charSequence3));
            }
            if (charSequence4 != null) {
                sb.append(", proxyLabel=" + ((Object) charSequence4));
            }
            sb.append(", lastActive=" + j);
            if (z) {
                sb.append(", active");
            }
            if (z2) {
                sb.append(", enterprise");
            }
            if (z3) {
                sb.append(", phoneCall");
            }
            sb.append(", permGroupName=" + ((Object) charSequence5) + ")");
            if (intent != null) {
                sb.append(", navigationIntent=" + intent);
            }
            sb.append(", isSystem=" + z4);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PrivacyElement)) {
                return false;
            }
            PrivacyElement privacyElement = (PrivacyElement) obj;
            return this.type == privacyElement.type && Intrinsics.areEqual(this.packageName, privacyElement.packageName) && this.userId == privacyElement.userId && Intrinsics.areEqual(this.applicationName, privacyElement.applicationName) && Intrinsics.areEqual(this.attributionTag, privacyElement.attributionTag) && Intrinsics.areEqual(this.attributionLabel, privacyElement.attributionLabel) && Intrinsics.areEqual(this.proxyLabel, privacyElement.proxyLabel) && this.lastActiveTimestamp == privacyElement.lastActiveTimestamp && this.active == privacyElement.active && this.enterprise == privacyElement.enterprise && this.phoneCall == privacyElement.phoneCall && Intrinsics.areEqual(this.permGroupName, privacyElement.permGroupName) && Intrinsics.areEqual(this.navigationIntent, privacyElement.navigationIntent) && this.isSystem == privacyElement.isSystem;
        }

        public final int hashCode() {
            int iM = ControlInfo$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.userId, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.type.hashCode() * 31, 31, this.packageName), 31), 31, this.applicationName);
            CharSequence charSequence = this.attributionTag;
            int iHashCode = (iM + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
            CharSequence charSequence2 = this.attributionLabel;
            int iHashCode2 = (iHashCode + (charSequence2 == null ? 0 : charSequence2.hashCode())) * 31;
            CharSequence charSequence3 = this.proxyLabel;
            int iM2 = ControlInfo$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m((iHashCode2 + (charSequence3 == null ? 0 : charSequence3.hashCode())) * 31, 31, this.lastActiveTimestamp), 31, this.active), 31, this.enterprise), 31, this.phoneCall), 31, this.permGroupName);
            Intent intent = this.navigationIntent;
            return Boolean.hashCode(this.isSystem) + ((iM2 + (intent != null ? intent.hashCode() : 0)) * 31);
        }

        public final String toString() {
            return this.builder.toString();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PrivacyType.values().length];
            try {
                iArr[PrivacyType.TYPE_LOCATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PrivacyType.TYPE_CAMERA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[PrivacyType.TYPE_MICROPHONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[PrivacyType.TYPE_MEDIA_PROJECTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.privacy.PrivacyDialog$clickListener$1] */
    public PrivacyDialog(Context context, List<PrivacyElement> list, Function4 function4) {
        super(context, R.style.SecPrivacyDialog);
        this.list = list;
        this.activityStarter = function4;
        this.dismissListeners = new ArrayList();
        this.dismissed = new AtomicBoolean(false);
        this.iconColorSolid = context.getColor(R.color.privacy_chip_icon_color);
        context.getString(R.string.ongoing_privacy_dialog_enterprise);
        String string = context.getString(R.string.sec_ongoing_privacy_dialog_phonecall);
        string.getClass();
        this.phonecall = string;
        this.clickListener = new View.OnClickListener() { // from class: com.android.systemui.privacy.PrivacyDialog$clickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Object tag = view.getTag();
                if (tag != null) {
                    PrivacyDialog.PrivacyElement privacyElement = (PrivacyDialog.PrivacyElement) tag;
                    this.this$0.activityStarter.invoke(privacyElement.packageName, Integer.valueOf(privacyElement.userId), privacyElement.attributionTag, privacyElement.navigationIntent);
                }
            }
        };
    }

    public final View createView(final PrivacyElement privacyElement) {
        int i;
        View viewRequireViewById;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
        ViewGroup viewGroup = this.rootView;
        String string = null;
        if (viewGroup == null) {
            viewGroup = null;
        }
        ViewGroup viewGroup2 = (ViewGroup) layoutInflaterFrom.inflate(R.layout.sec_privacy_dialog_item, viewGroup, false);
        PrivacyType privacyType = privacyElement.type;
        Context context = getContext();
        int i2 = WhenMappings.$EnumSwitchMapping$0[privacyType.ordinal()];
        if (i2 == 1) {
            i = R.drawable.sec_privacy_item_circle_location;
        } else if (i2 == 2) {
            i = R.drawable.sec_privacy_item_circle_camera;
        } else if (i2 == 3) {
            i = R.drawable.sec_privacy_item_circle_microphone;
        } else {
            if (i2 != 4) {
                throw new NoWhenBranchMatchedException();
            }
            i = R.drawable.privacy_item_circle_media_projection;
        }
        LayerDrawable layerDrawable = (LayerDrawable) context.getDrawable(i);
        layerDrawable.findDrawableByLayerId(R.id.icon).setTint(this.iconColorSolid);
        ImageView imageView = (ImageView) viewGroup2.requireViewById(R.id.icon);
        imageView.setImageDrawable(layerDrawable);
        imageView.setContentDescription(privacyElement.type.getName(imageView.getContext()));
        int i3 = privacyElement.active ? R.string.sec_ongoing_privacy_dialog_using_op : R.string.sec_ongoing_privacy_dialog_recent_op;
        boolean z = privacyElement.phoneCall;
        CharSequence charSequenceConcat = z ? this.phonecall : privacyElement.applicationName;
        int i4 = privacyElement.userId;
        boolean zIsDualAppId = SemDualAppManager.isDualAppId(i4);
        boolean z2 = ScRune.QUICK_SUPPORT_LOCATION_PRIVACY_CHIP;
        boolean z3 = privacyElement.isSystem;
        if (z2 && z3) {
            charSequenceConcat = getContext().getString(R.string.sec_ongoing_privacy_dialog_system_app);
        } else if (privacyElement.enterprise || zIsDualAppId) {
            SemPersonaManager semPersonaManager = this.mPersonaManager;
            if (semPersonaManager == null) {
                semPersonaManager = null;
            }
            String containerName = semPersonaManager.getContainerName(i4, getContext());
            if (containerName != null) {
                charSequenceConcat = TextUtils.concat(charSequenceConcat, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("(", containerName, ")"));
            }
        }
        CharSequence string2 = getContext().getString(i3, charSequenceConcat);
        CharSequence charSequence = privacyElement.attributionLabel;
        CharSequence charSequence2 = privacyElement.proxyLabel;
        if (!z2 || !z3) {
            if (charSequence != null && charSequence2 != null) {
                string = getContext().getString(R.string.ongoing_privacy_dialog_attribution_proxy_label, charSequence, charSequence2);
            } else if (charSequence != null) {
                string = getContext().getString(R.string.ongoing_privacy_dialog_attribution_label, charSequence);
            } else if (charSequence2 != null) {
                string = getContext().getString(R.string.ongoing_privacy_dialog_attribution_text, charSequence2);
            }
        }
        if (string != null) {
            string2 = TextUtils.concat(string2, " ", string);
        }
        ((TextView) viewGroup2.requireViewById(R.id.text)).setText(string2);
        viewGroup2.setTag(privacyElement);
        if (!z) {
            viewGroup2.setOnClickListener(this.clickListener);
        }
        if (z2 && z3 && (viewRequireViewById = viewGroup2.requireViewById(R.id.system_setting)) != null) {
            viewRequireViewById.setVisibility(0);
            viewRequireViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.privacy.PrivacyDialog$createView$2$1$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Intent intent = new Intent("android.settings.LOCATION_SCANNING_SETTINGS");
                    Bundle bundle = new Bundle();
                    bundle.putString(":settings:fragment_args_key", "key_show_status_bar_location_icon");
                    intent.putExtra(":settings:show_fragment_args", bundle);
                    Function4 function4 = this.this$0.activityStarter;
                    PrivacyDialog.PrivacyElement privacyElement2 = privacyElement;
                    function4.invoke(privacyElement2.packageName, Integer.valueOf(privacyElement2.userId), privacyElement.attributionTag, intent);
                }
            });
        }
        return viewGroup2;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0090  */
    @Override // com.android.systemui.statusbar.phone.SystemUIDialog, android.app.AlertDialog, android.app.Dialog
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onCreate(Bundle bundle) throws Resources.NotFoundException {
        int dimensionPixelSize;
        WindowManager windowManager;
        WindowMetrics currentWindowMetrics;
        WindowInsets windowInsets;
        super.onCreate(bundle);
        int dimensionPixelSize2 = getContext().getResources().getDimensionPixelSize(R.dimen.sec_privacy_dialog_top_margin);
        this.mDialogTopMargin = dimensionPixelSize2;
        if (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
            SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
            Context context = getContext();
            float availableDisplayHeight = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getAvailableDisplayHeight(context);
            SecQSPanelResourceCommon.Companion.getClass();
            dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.status_bar_height) + ((int) (context.getResources().getFloat(R.dimen.qs_header_top_margin_ratio) * availableDisplayHeight));
        } else if (getContext().getResources().getConfiguration().orientation == 1) {
            Window window = getWindow();
            dimensionPixelSize = (window == null || (windowManager = window.getWindowManager()) == null || (currentWindowMetrics = windowManager.getCurrentWindowMetrics()) == null || (windowInsets = currentWindowMetrics.getWindowInsets()) == null) ? 0 : windowInsets.getStableInsetTop();
        } else if (getContext().getResources().getConfiguration().orientation == 2) {
            dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.shade_header_no_cutout_top_margin);
        }
        this.mDialogTopMargin = dimensionPixelSize2 + dimensionPixelSize;
        Window window2 = getWindow();
        if (window2 != null) {
            window2.getAttributes().setFitInsetsTypes(window2.getAttributes().getFitInsetsTypes());
            window2.getAttributes().receiveInsetsIgnoringZOrder = true;
            window2.getAttributes().layoutInDisplayCutoutMode = 1;
            window2.getAttributes().y = this.mDialogTopMargin;
            window2.getAttributes().x = 0;
            window2.setDecorFitsSystemWindows(false);
            window2.setNavigationBarColor(window2.getContext().getColor(R.color.transparent));
            window2.setStatusBarColor(window2.getContext().getColor(R.color.transparent));
            window2.setNavigationBarContrastEnforced(false);
            window2.setLayout(((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getPanelWidth(window2.getContext()) - ((window2.getContext().getResources().getConfiguration().orientation == 1 ? window2.getContext().getResources().getDimensionPixelSize(R.dimen.sec_ongoing_appops_dialog_side_margins) : this.qsExpanded ? 0 : ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getQQSPanelSidePadding(window2.getContext())) * 2), -2);
            window2.setWindowAnimations(R.style.Animation_SecPrivacyDialog);
            window2.setGravity(49);
        }
        setContentView(R.layout.sec_privacy_dialog);
        this.mBlurView = (ImageView) requireViewById(R.id.background_blur);
        this.mPersonaManager = (SemPersonaManager) getContext().getSystemService("persona");
        this.rootView = (ViewGroup) requireViewById(R.id.root);
        if (ScRune.QUICK_SUPPORT_LOCATION_PRIVACY_CHIP) {
            List list = this.list;
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (!((PrivacyElement) obj).isSystem) {
                    arrayList.add(obj);
                }
            }
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                PrivacyElement privacyElement = (PrivacyElement) obj2;
                ViewGroup viewGroup = this.rootView;
                if (viewGroup == null) {
                    viewGroup = null;
                }
                viewGroup.addView(createView(privacyElement));
            }
            List list2 = this.list;
            ArrayList arrayList2 = new ArrayList();
            for (Object obj3 : list2) {
                if (((PrivacyElement) obj3).isSystem) {
                    arrayList2.add(obj3);
                }
            }
            if (arrayList2.size() > 0) {
                ViewGroup viewGroup2 = this.rootView;
                if (viewGroup2 == null) {
                    viewGroup2 = null;
                }
                viewGroup2.addView(createView((PrivacyElement) arrayList2.get(0)));
            }
        } else {
            for (PrivacyElement privacyElement2 : this.list) {
                ViewGroup viewGroup3 = this.rootView;
                if (viewGroup3 == null) {
                    viewGroup3 = null;
                }
                viewGroup3.addView(createView(privacyElement2));
            }
        }
        ViewGroup viewGroup4 = this.rootView;
        (viewGroup4 != null ? viewGroup4 : null).setClipToOutline(true);
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.privacy.PrivacyDialog.onCreate.8
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) throws Resources.NotFoundException {
                Bitmap bitmapScreenshot;
                PrivacyDialog privacyDialog = PrivacyDialog.this;
                ImageView imageView = privacyDialog.mBlurView;
                SemBlurInfo.Builder bitmap = null;
                if (imageView == null) {
                    imageView = null;
                }
                float dimensionPixelSize3 = privacyDialog.getContext().getResources().getDimensionPixelSize(R.dimen.sec_privacy_dialog_corner_radius);
                int color = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled() ? privacyDialog.getContext().getResources().getColor(R.color.sec_privacy_dialog_bg_reduce_transparency) : privacyDialog.getContext().getResources().getColor(R.color.sec_privacy_dialog_bg);
                int qQSPanelSidePadding = 0;
                if (QpRune.QUICK_PANEL_BLUR_DEFAULT) {
                    bitmap = new SemBlurInfo.Builder(0).setRadius(120).setBackgroundColor(color).setBackgroundCornerRadius(dimensionPixelSize3);
                } else if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
                    if (privacyDialog.getContext().getResources().getConfiguration().orientation == 1) {
                        qQSPanelSidePadding = privacyDialog.getContext().getResources().getDimensionPixelSize(R.dimen.sec_ongoing_appops_dialog_side_margins);
                    } else if (!privacyDialog.qsExpanded) {
                        qQSPanelSidePadding = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getQQSPanelSidePadding(privacyDialog.getContext());
                    }
                    int panelWidth = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getPanelWidth(privacyDialog.getContext()) - (qQSPanelSidePadding * 2);
                    int dimensionPixelSize4 = privacyDialog.getContext().getResources().getDimensionPixelSize(R.dimen.sec_privacy_dialog_item_height) * privacyDialog.list.size();
                    Point point = new Point();
                    Display display = privacyDialog.getContext().getDisplay();
                    display.getClass();
                    display.getRealSize(point);
                    int i2 = (point.x - panelWidth) / 2;
                    int i3 = privacyDialog.mDialogTopMargin;
                    try {
                        bitmapScreenshot = SemWindowManager.getInstance().screenshot(((WindowManager) privacyDialog.getContext().getSystemService("window")).getDefaultDisplay().getDisplayId(), 2036, true, new Rect(i2, i3, i2 + panelWidth, i3 + dimensionPixelSize4), panelWidth, dimensionPixelSize4, false, 0, true);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                        bitmapScreenshot = null;
                    }
                    if (bitmapScreenshot != null) {
                        bitmap = new SemBlurInfo.Builder(1).setRadius(120).setBitmap(bitmapScreenshot);
                    }
                }
                if (bitmap != null) {
                    imageView.semSetBlurInfo(bitmap.build());
                }
                imageView.setClipToOutline(true);
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void stop() {
        this.dismissed.set(true);
        Iterator it = ((ArrayList) this.dismissListeners).iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            it.remove();
            PrivacyDialogController$onDialogDismissed$1 privacyDialogController$onDialogDismissed$1 = (PrivacyDialogController$onDialogDismissed$1) weakReference.get();
            if (privacyDialogController$onDialogDismissed$1 != null) {
                PrivacyDialogController privacyDialogController = privacyDialogController$onDialogDismissed$1.this$0;
                PrivacyLogger privacyLogger = privacyDialogController.privacyLogger;
                privacyLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda0 = new PrivacyLogger$$ExternalSyntheticLambda0(14);
                LogBuffer logBuffer = privacyLogger.buffer;
                logBuffer.commit(logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null));
                privacyDialogController.uiEventLogger.log(PrivacyDialogEvent.PRIVACY_DIALOG_DISMISSED);
                privacyDialogController.dialog = null;
            }
        }
    }
}
