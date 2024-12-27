package android.view.autofill;

import android.content.Context;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.ArraySet;

import com.android.internal.util.ArrayUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public class AutofillFeatureFlags {
    private static final String DEFAULT_AFAA_ALLOWLIST = "";
    private static final String DEFAULT_AFAA_DENYLIST = "";
    private static final String DEFAULT_AFAA_NON_AUTOFILLABLE_IME_ACTIONS = "3,4";
    private static final boolean DEFAULT_AFAA_ON_IMPORTANT_VIEW_ENABLED = true;
    private static final boolean DEFAULT_AFAA_ON_UNIMPORTANT_VIEW_ENABLED = true;
    private static final boolean DEFAULT_AFAA_SHOULD_ENABLE_AUTOFILL_ON_ALL_VIEW_TYPES = true;
    private static final boolean DEFAULT_AFAA_SHOULD_ENABLE_MULTILINE_FILTER = true;
    private static final boolean
            DEFAULT_AFAA_SHOULD_INCLUDE_ALL_AUTOFILL_TYPE_NOT_NONE_VIEWS_IN_ASSIST_STRUCTURE = true;
    public static final boolean DEFAULT_AUTOFILL_PCC_CLASSIFICATION_ENABLED = false;
    private static final boolean DEFAULT_CREDENTIAL_MANAGER_ENABLED = true;
    private static final boolean DEFAULT_CREDENTIAL_MANAGER_SUPPRESS_FILL_AND_SAVE_DIALOG = true;
    private static final String DEFAULT_FILL_DIALOG_ENABLED_HINTS = "";
    private static final boolean DEFAULT_HAS_FILL_DIALOG_UI_FEATURE = false;
    public static final int DEFAULT_MAX_INPUT_LENGTH_FOR_AUTOFILL = 3;
    public static final String DEVICE_CONFIG_ALWAYS_INCLUDE_WEBVIEW_IN_ASSIST_STRUCTURE =
            "always_include_webview_in_assist_structure";
    public static final String DEVICE_CONFIG_AUGMENTED_SERVICE_IDLE_UNBIND_TIMEOUT =
            "augmented_service_idle_unbind_timeout";
    public static final String DEVICE_CONFIG_AUGMENTED_SERVICE_REQUEST_TIMEOUT =
            "augmented_service_request_timeout";
    public static final String DEVICE_CONFIG_AUTOFILL_COMPAT_MODE_ALLOWED_PACKAGES =
            "compat_mode_allowed_packages";
    public static final String DEVICE_CONFIG_AUTOFILL_CREDENTIAL_MANAGER_ENABLED =
            "autofill_credential_manager_enabled";
    public static final String DEVICE_CONFIG_AUTOFILL_CREDENTIAL_MANAGER_IGNORE_VIEWS =
            "autofill_credential_manager_ignore_views";
    public static final String
            DEVICE_CONFIG_AUTOFILL_CREDENTIAL_MANAGER_SUPPRESS_FILL_AND_SAVE_DIALOG =
                    "autofill_credential_manager_suppress_fill_and_save_dialog";
    public static final String DEVICE_CONFIG_AUTOFILL_DIALOG_ENABLED = "autofill_dialog_enabled";
    public static final String DEVICE_CONFIG_AUTOFILL_DIALOG_HINTS = "autofill_dialog_hints";
    public static final String DEVICE_CONFIG_AUTOFILL_PCC_CLASSIFICATION_ENABLED =
            "pcc_classification_enabled";
    public static final String DEVICE_CONFIG_AUTOFILL_PCC_FEATURE_PROVIDER_HINTS =
            "pcc_classification_hints";
    public static final String DEVICE_CONFIG_AUTOFILL_SMART_SUGGESTION_SUPPORTED_MODES =
            "smart_suggestion_supported_modes";
    public static final String DEVICE_CONFIG_AUTOFILL_TOOLTIP_SHOW_UP_DELAY =
            "autofill_inline_tooltip_first_show_delay";
    public static final String DEVICE_CONFIG_FILL_FIELDS_FROM_CURRENT_SESSION_ONLY =
            "fill_fields_from_current_session_only";
    public static final String DEVICE_CONFIG_IGNORE_RELAYOUT_WHEN_AUTH_PENDING =
            "ignore_relayout_auth_pending";
    public static final String DEVICE_CONFIG_IGNORE_VIEW_STATE_RESET_TO_EMPTY =
            "ignore_view_state_reset_to_empty";
    public static final String
            DEVICE_CONFIG_INCLUDE_ALL_AUTOFILL_TYPE_NOT_NONE_VIEWS_IN_ASSIST_STRUCTURE =
                    "include_all_autofill_type_not_none_views_in_assist_structure";
    public static final String DEVICE_CONFIG_INCLUDE_ALL_VIEWS_IN_ASSIST_STRUCTURE =
            "include_all_views_in_assist_structure";
    public static final String DEVICE_CONFIG_INCLUDE_INVISIBLE_VIEW_GROUP_IN_ASSIST_STRUCTURE =
            "include_invisible_view_group_in_assist_structure";
    public static final String DEVICE_CONFIG_MAX_INPUT_LENGTH_FOR_AUTOFILL =
            "max_input_length_for_autofill";
    public static final String DEVICE_CONFIG_MULTILINE_FILTER_ENABLED = "multiline_filter_enabled";
    public static final String DEVICE_CONFIG_NON_AUTOFILLABLE_IME_ACTION_IDS =
            "non_autofillable_ime_action_ids";
    public static final String
            DEVICE_CONFIG_PACKAGE_AND_ACTIVITY_ALLOWLIST_FOR_TRIGGERING_FILL_REQUEST =
                    "package_and_activity_allowlist_for_triggering_fill_request";
    public static final String DEVICE_CONFIG_PACKAGE_DENYLIST_FOR_UNIMPORTANT_VIEW =
            "package_deny_list_for_unimportant_view";
    public static final String DEVICE_CONFIG_PCC_USE_FALLBACK = "pcc_use_fallback";
    public static final String DEVICE_CONFIG_PREFER_PROVIDER_OVER_PCC = "prefer_provider_over_pcc";
    public static final String DEVICE_CONFIG_SHOULD_ENABLE_AUTOFILL_ON_ALL_VIEW_TYPES =
            "should_enable_autofill_on_all_view_types";
    public static final String DEVICE_CONFIG_TRIGGER_FILL_REQUEST_ON_FILTERED_IMPORTANT_VIEWS =
            "trigger_fill_request_on_filtered_important_views";
    public static final String DEVICE_CONFIG_TRIGGER_FILL_REQUEST_ON_UNIMPORTANT_VIEW =
            "trigger_fill_request_on_unimportant_view";
    private static final String DIALOG_HINTS_DELIMITER = ":";

    private AutofillFeatureFlags() {}

    public static boolean isFillDialogEnabled() {
        return DeviceConfig.getBoolean(
                Context.AUTOFILL_MANAGER_SERVICE, DEVICE_CONFIG_AUTOFILL_DIALOG_ENABLED, false);
    }

    public static String[] getFillDialogEnabledHints() {
        String dialogHints =
                DeviceConfig.getString(
                        Context.AUTOFILL_MANAGER_SERVICE, DEVICE_CONFIG_AUTOFILL_DIALOG_HINTS, "");
        if (TextUtils.isEmpty(dialogHints)) {
            return new String[0];
        }
        return (String[])
                ArrayUtils.filter(
                        dialogHints.split(":"),
                        new IntFunction() { // from class:
                                            // android.view.autofill.AutofillFeatureFlags$$ExternalSyntheticLambda0
                            @Override // java.util.function.IntFunction
                            public final Object apply(int i) {
                                return AutofillFeatureFlags.lambda$getFillDialogEnabledHints$0(i);
                            }
                        },
                        new Predicate() { // from class:
                                          // android.view.autofill.AutofillFeatureFlags$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return AutofillFeatureFlags.lambda$getFillDialogEnabledHints$1(
                                        (String) obj);
                            }
                        });
    }

    static /* synthetic */ String[] lambda$getFillDialogEnabledHints$0(int x$0) {
        return new String[x$0];
    }

    static /* synthetic */ boolean lambda$getFillDialogEnabledHints$1(String str) {
        return !TextUtils.isEmpty(str);
    }

    public static boolean isCredentialManagerEnabled() {
        return DeviceConfig.getBoolean(
                Context.AUTOFILL_MANAGER_SERVICE,
                DEVICE_CONFIG_AUTOFILL_CREDENTIAL_MANAGER_ENABLED,
                true);
    }

    public static boolean isFillAndSaveDialogDisabledForCredentialManager() {
        return isCredentialManagerEnabled()
                && DeviceConfig.getBoolean(
                        Context.AUTOFILL_MANAGER_SERVICE,
                        DEVICE_CONFIG_AUTOFILL_CREDENTIAL_MANAGER_SUPPRESS_FILL_AND_SAVE_DIALOG,
                        true);
    }

    public static boolean isTriggerFillRequestOnUnimportantViewEnabled() {
        return DeviceConfig.getBoolean(
                Context.AUTOFILL_MANAGER_SERVICE,
                DEVICE_CONFIG_TRIGGER_FILL_REQUEST_ON_UNIMPORTANT_VIEW,
                true);
    }

    public static boolean isTriggerFillRequestOnFilteredImportantViewsEnabled() {
        return DeviceConfig.getBoolean(
                Context.AUTOFILL_MANAGER_SERVICE,
                DEVICE_CONFIG_TRIGGER_FILL_REQUEST_ON_FILTERED_IMPORTANT_VIEWS,
                true);
    }

    public static boolean shouldEnableAutofillOnAllViewTypes() {
        return DeviceConfig.getBoolean(
                Context.AUTOFILL_MANAGER_SERVICE,
                DEVICE_CONFIG_SHOULD_ENABLE_AUTOFILL_ON_ALL_VIEW_TYPES,
                true);
    }

    public static Set<String> getNonAutofillableImeActionIdSetFromFlag() {
        String mNonAutofillableImeActions =
                DeviceConfig.getString(
                        Context.AUTOFILL_MANAGER_SERVICE,
                        DEVICE_CONFIG_NON_AUTOFILLABLE_IME_ACTION_IDS,
                        DEFAULT_AFAA_NON_AUTOFILLABLE_IME_ACTIONS);
        return new ArraySet(Arrays.asList(mNonAutofillableImeActions.split(",")));
    }

    public static String getDenylistStringFromFlag() {
        return DeviceConfig.getString(
                Context.AUTOFILL_MANAGER_SERVICE,
                DEVICE_CONFIG_PACKAGE_DENYLIST_FOR_UNIMPORTANT_VIEW,
                "");
    }

    public static String getAllowlistStringFromFlag() {
        return DeviceConfig.getString(
                Context.AUTOFILL_MANAGER_SERVICE,
                DEVICE_CONFIG_PACKAGE_AND_ACTIVITY_ALLOWLIST_FOR_TRIGGERING_FILL_REQUEST,
                "");
    }

    public static boolean shouldIncludeAllViewsAutofillTypeNotNoneInAssistStructrue() {
        return DeviceConfig.getBoolean(
                Context.AUTOFILL_MANAGER_SERVICE,
                DEVICE_CONFIG_INCLUDE_ALL_AUTOFILL_TYPE_NOT_NONE_VIEWS_IN_ASSIST_STRUCTURE,
                true);
    }

    public static boolean shouldIncludeAllChildrenViewInAssistStructure() {
        return DeviceConfig.getBoolean(
                Context.AUTOFILL_MANAGER_SERVICE,
                DEVICE_CONFIG_INCLUDE_ALL_VIEWS_IN_ASSIST_STRUCTURE,
                false);
    }

    public static boolean shouldAlwaysIncludeWebviewInAssistStructure() {
        return DeviceConfig.getBoolean(
                Context.AUTOFILL_MANAGER_SERVICE,
                DEVICE_CONFIG_ALWAYS_INCLUDE_WEBVIEW_IN_ASSIST_STRUCTURE,
                true);
    }

    public static boolean shouldIncludeInvisibleViewInAssistStructure() {
        return DeviceConfig.getBoolean(
                Context.AUTOFILL_MANAGER_SERVICE,
                DEVICE_CONFIG_INCLUDE_INVISIBLE_VIEW_GROUP_IN_ASSIST_STRUCTURE,
                true);
    }

    public static boolean shouldIgnoreViewStateResetToEmpty() {
        return DeviceConfig.getBoolean(
                Context.AUTOFILL_MANAGER_SERVICE,
                DEVICE_CONFIG_IGNORE_VIEW_STATE_RESET_TO_EMPTY,
                true);
    }

    public static boolean shouldIgnoreRelayoutWhenAuthPending() {
        return DeviceConfig.getBoolean(
                Context.AUTOFILL_MANAGER_SERVICE,
                DEVICE_CONFIG_IGNORE_RELAYOUT_WHEN_AUTH_PENDING,
                false);
    }

    public static boolean shouldFillFieldsFromCurrentSessionOnly() {
        return DeviceConfig.getBoolean(
                Context.AUTOFILL_MANAGER_SERVICE,
                DEVICE_CONFIG_FILL_FIELDS_FROM_CURRENT_SESSION_ONLY,
                true);
    }

    public static boolean shouldEnableMultilineFilter() {
        return DeviceConfig.getBoolean(
                Context.AUTOFILL_MANAGER_SERVICE, DEVICE_CONFIG_MULTILINE_FILTER_ENABLED, true);
    }

    public static boolean isAutofillPccClassificationEnabled() {
        return DeviceConfig.getBoolean(
                Context.AUTOFILL_MANAGER_SERVICE,
                DEVICE_CONFIG_AUTOFILL_PCC_CLASSIFICATION_ENABLED,
                false);
    }
}
