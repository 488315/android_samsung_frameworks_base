package com.android.systemui.controls.util;

import android.content.Context;
import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.RangeTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.service.controls.templates.ThumbnailTemplate;
import android.service.controls.templates.ToggleRangeTemplate;
import android.service.controls.templates.ToggleTemplate;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ControlsUtil {
    public static final Companion Companion = new Companion(null);
    public final KeyguardStateController keyguardStateController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static StringBuilder dump(Control control) {
            StringBuilder sb = new StringBuilder();
            ControlsUtil.Companion.getClass();
            sb.append("id=" + control.getControlId() + "|");
            sb.append("deviceType=" + control.getDeviceType() + "|");
            sb.append("appIntent=" + control.getAppIntent().getIntent() + "|");
            CharSequence title = control.getTitle();
            Intrinsics.checkNotNull(title);
            if (title.length() == 0) {
                title = null;
            }
            if (title != null) {
                sb.append("title=" + ((Object) title) + "|");
            }
            CharSequence subtitle = control.getSubtitle();
            Intrinsics.checkNotNull(subtitle);
            if (subtitle.length() == 0) {
                subtitle = null;
            }
            if (subtitle != null) {
                sb.append("subtitle=" + ((Object) subtitle) + "|");
            }
            CharSequence structure = control.getStructure();
            if (structure != null) {
                sb.append("structure=" + ((Object) structure) + "|");
            }
            CharSequence zone = control.getZone();
            if (zone != null) {
                sb.append("zone=" + ((Object) zone) + "|");
            }
            int status = control.getStatus();
            Integer valueOf = Integer.valueOf(status);
            if (status == 1) {
                valueOf = null;
            }
            if (valueOf != null) {
                sb.append("status=" + valueOf.intValue() + "|");
            }
            CharSequence statusText = control.getStatusText();
            Intrinsics.checkNotNull(statusText);
            if (statusText.length() == 0) {
                statusText = null;
            }
            if (statusText != null) {
                sb.append("statusText=" + ((Object) statusText) + "|");
            }
            if (control.getCustomColor() != null) {
                sb.append("CustomColor|");
            }
            if (control.getCustomIcon() != null) {
                sb.append("CustomIcon|");
            }
            boolean isAuthRequired = control.isAuthRequired();
            Boolean valueOf2 = Boolean.valueOf(isAuthRequired);
            if (isAuthRequired) {
                valueOf2 = null;
            }
            if (valueOf2 != null) {
                ControlsUtil$Companion$$ExternalSyntheticOutline0.m("isAuthRequired=", valueOf2.booleanValue(), "|", sb);
            }
            getTemplateInformation(control.getControlTemplate(), sb);
            if (control.getCustomControl().getActionIcon() != null) {
                sb.append("Action|");
            }
            boolean allowBasicActionWhenLocked = control.getCustomControl().getAllowBasicActionWhenLocked();
            Boolean valueOf3 = Boolean.valueOf(allowBasicActionWhenLocked);
            if (!allowBasicActionWhenLocked) {
                valueOf3 = null;
            }
            if (valueOf3 != null) {
                sb.append("AllowLocked|");
            }
            String customIconAnimationJson = control.getCustomControl().getCustomIconAnimationJson();
            Intrinsics.checkNotNull(customIconAnimationJson);
            if (customIconAnimationJson.length() == 0) {
                customIconAnimationJson = null;
            }
            if (customIconAnimationJson != null) {
                sb.append("JSON");
                int customIconAnimationStartFrame = control.getCustomControl().getCustomIconAnimationStartFrame();
                Integer valueOf4 = Integer.valueOf(customIconAnimationStartFrame);
                if (customIconAnimationStartFrame <= -1) {
                    valueOf4 = null;
                }
                if (valueOf4 != null) {
                    sb.append(":SF[" + valueOf4.intValue() + "]");
                }
                int customIconAnimationEndFrame = control.getCustomControl().getCustomIconAnimationEndFrame();
                Integer valueOf5 = Integer.valueOf(customIconAnimationEndFrame);
                if (customIconAnimationEndFrame <= -1) {
                    valueOf5 = null;
                }
                if (valueOf5 != null) {
                    sb.append(":EF[" + valueOf5.intValue() + "]");
                }
                int customIconAnimationRepeatCount = control.getCustomControl().getCustomIconAnimationRepeatCount();
                Integer valueOf6 = Integer.valueOf(customIconAnimationRepeatCount);
                if (customIconAnimationRepeatCount <= -1) {
                    valueOf6 = null;
                }
                if (valueOf6 != null) {
                    sb.append(":RC[" + valueOf6.intValue() + "]");
                }
                sb.append("|");
            }
            if (control.getCustomControl().getCustomStatusIcon() != null) {
                sb.append("CustomStatusIcon|");
            }
            int layoutType = control.getCustomControl().getLayoutType();
            Integer valueOf7 = Integer.valueOf(layoutType);
            if (layoutType != 1) {
                valueOf7 = null;
            }
            if (valueOf7 != null) {
                sb.append("LayoutType:" + valueOf7.intValue() + "|");
            }
            int order = control.getCustomControl().getOrder();
            Integer valueOf8 = Integer.valueOf(order);
            if (order <= 0) {
                valueOf8 = null;
            }
            if (valueOf8 != null) {
                sb.append("Order:" + valueOf8.intValue() + "|");
            }
            boolean useCustomIconWithoutPadding = control.getCustomControl().getUseCustomIconWithoutPadding();
            Boolean valueOf9 = Boolean.valueOf(useCustomIconWithoutPadding);
            if (!useCustomIconWithoutPadding) {
                valueOf9 = null;
            }
            if (valueOf9 != null) {
                sb.append("NoPadding|");
            }
            boolean useCustomIconWithoutShadowBg = control.getCustomControl().getUseCustomIconWithoutShadowBg();
            Boolean valueOf10 = Boolean.valueOf(useCustomIconWithoutShadowBg);
            if (!useCustomIconWithoutShadowBg) {
                valueOf10 = null;
            }
            if (valueOf10 != null) {
                sb.append("NoShadowBg|");
            }
            boolean useFullScreenDetailDialog = control.getCustomControl().getUseFullScreenDetailDialog();
            Boolean valueOf11 = Boolean.valueOf(useFullScreenDetailDialog);
            if (!useFullScreenDetailDialog) {
                valueOf11 = null;
            }
            if (valueOf11 != null) {
                sb.append("FullScreen|");
            }
            int statusIconType = control.getCustomControl().getStatusIconType();
            Integer valueOf12 = statusIconType != 0 ? Integer.valueOf(statusIconType) : null;
            if (valueOf12 != null) {
                sb.append("StatusIconType:" + valueOf12.intValue() + "|");
            }
            if (control.getCustomControl().getStatusTextColor() != null) {
                sb.append("StatusTextColor|");
            }
            if (control.getCustomControl().getOverlayCustomIcon() != null) {
                sb.append("overlayCustomIcon|");
            }
            return sb;
        }

        public static void getTemplateInformation(ControlTemplate controlTemplate, StringBuilder sb) {
            sb.append("id=" + controlTemplate.getTemplateId());
            int templateType = controlTemplate.getTemplateType();
            if (templateType == -1) {
                sb.append("[ERROR]|");
                return;
            }
            if (templateType == 0) {
                sb.append("[NO_TEMPLATE]|");
                return;
            }
            if (templateType == 1) {
                ControlsUtil$Companion$$ExternalSyntheticOutline0.m("[TOGGLE],checked=", ((ToggleTemplate) controlTemplate).isChecked(), "|", sb);
                return;
            }
            if (templateType == 2) {
                sb.append("[RANGE],value=" + ((RangeTemplate) controlTemplate).getCurrentValue() + "|");
                return;
            }
            if (templateType == 3) {
                ControlsUtil$Companion$$ExternalSyntheticOutline0.m("[THUMBNAIL],active=", ((ThumbnailTemplate) controlTemplate).isActive(), "|", sb);
                return;
            }
            if (templateType == 6) {
                ToggleRangeTemplate toggleRangeTemplate = (ToggleRangeTemplate) controlTemplate;
                ControlsUtil$Companion$$ExternalSyntheticOutline0.m("[TOGGLE_RANGE],checked=", toggleRangeTemplate.isChecked(), "|", sb);
                Companion companion = ControlsUtil.Companion;
                RangeTemplate range = toggleRangeTemplate.getRange();
                companion.getClass();
                getTemplateInformation(range, sb);
                return;
            }
            if (templateType != 7) {
                if (templateType == 8) {
                    sb.append("[STATELESS]|");
                    return;
                }
                sb.append("[UNKNOWN],type=" + controlTemplate.getTemplateType() + "|");
                return;
            }
            TemperatureControlTemplate temperatureControlTemplate = (TemperatureControlTemplate) controlTemplate;
            int currentMode = temperatureControlTemplate.getCurrentMode();
            int currentActiveMode = temperatureControlTemplate.getCurrentActiveMode();
            int modes = temperatureControlTemplate.getModes();
            StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(currentMode, currentActiveMode, "[TEMPERATURE],mode=", ",activeMode=", ",modes=");
            m.append(modes);
            m.append("|");
            sb.append(m.toString());
            Companion companion2 = ControlsUtil.Companion;
            ControlTemplate template = temperatureControlTemplate.getTemplate();
            companion2.getClass();
            getTemplateInformation(template, sb);
        }

        public static void setSize(View view, int i, int i2) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = i;
            layoutParams.height = i2;
            view.setLayoutParams(layoutParams);
        }

        public static void updateFontSize(TextView textView, int i, float f) {
            float f2 = textView.getResources().getConfiguration().fontScale;
            Float valueOf = Float.valueOf(f2);
            if (f2 > f) {
                valueOf = null;
            }
            if (valueOf != null) {
                f = valueOf.floatValue();
            }
            textView.setTextSize(1, (textView.getResources().getDimensionPixelSize(i) / textView.getResources().getDisplayMetrics().scaledDensity) * f);
        }

        public static /* synthetic */ void updateFontSize$default(Companion companion, TextView textView, int i) {
            companion.getClass();
            updateFontSize(textView, i, 1.3f);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ControlsUtil(KeyguardStateController keyguardStateController) {
        this.keyguardStateController = keyguardStateController;
    }

    public static List getListOfServices(Context context, List list) {
        Object obj;
        final Collator collator = Collator.getInstance(context.getResources().getConfiguration().getLocales().get(0));
        Intrinsics.checkNotNull(collator);
        List sortedWith = CollectionsKt___CollectionsKt.sortedWith(list, new Comparator() { // from class: com.android.systemui.controls.util.ControlsUtil$getListOfServices$getLocaleComparator$$inlined$compareBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj2, Object obj3) {
                return collator.compare(((ControlsServiceInfo) obj2).loadLabel(), ((ControlsServiceInfo) obj3).loadLabel());
            }
        });
        Iterator it = sortedWith.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if ("com.samsung.android.oneconnect".equals(((ControlsServiceInfo) obj).componentName.getPackageName())) {
                break;
            }
        }
        ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj;
        if (controlsServiceInfo == null) {
            return sortedWith;
        }
        ArrayList arrayList = new ArrayList(sortedWith);
        arrayList.remove(controlsServiceInfo);
        arrayList.add(0, controlsServiceInfo);
        return arrayList;
    }

    public static boolean isFoldDelta(Context context) {
        return context.getResources().getConfiguration().screenWidthDp == 320;
    }

    public static LottieAnimationView updateLottieIcon(Context context, ImageView imageView, View view, LottieAnimationView lottieAnimationView, String str, String str2, int i, int i2, int i3) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            if (lottieAnimationView == null) {
                lottieAnimationView = (LottieAnimationView) ((ViewStub) view.requireViewById(R.id.icon_custom_animation)).inflate();
                if (BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD && isFoldDelta(context)) {
                    int dimensionPixelSize = lottieAnimationView.getContext().getResources().getDimensionPixelSize(R.dimen.control_icon_size_fold);
                    Companion.getClass();
                    Companion.setSize(lottieAnimationView, dimensionPixelSize, dimensionPixelSize);
                }
            }
            imageView.setVisibility(8);
            lottieAnimationView.setAnimationFromJson(str, str2);
            lottieAnimationView.setVisibility(0);
            if (i3 > -1) {
                lottieAnimationView.setRepeatCount(i3);
            }
            if (i > -1 && i2 > -1) {
                lottieAnimationView.setMinAndMaxFrame(i, i2);
            }
            lottieAnimationView.playAnimation();
        }
        return lottieAnimationView;
    }

    public final boolean isSecureLocked() {
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        return ((KeyguardStateControllerImpl) keyguardStateController).mShowing && ((KeyguardStateControllerImpl) keyguardStateController).mSecure;
    }
}
