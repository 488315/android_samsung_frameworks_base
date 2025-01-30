package com.android.server.autofill.p008ui;

import android.R;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.IBinder;
import android.os.UserHandle;
import android.p005os.IInstalld;
import android.provider.Settings;
import android.service.autofill.BatchUpdates;
import android.service.autofill.CustomDescription;
import android.service.autofill.InternalOnClickAction;
import android.service.autofill.InternalTransformation;
import android.service.autofill.InternalValidator;
import android.service.autofill.SaveInfo;
import android.service.autofill.ValueFinder;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.ArrayUtils;
import com.android.server.UiThread;
import com.android.server.autofill.Helper;
import com.android.server.autofill.p008ui.SaveUi;
import com.android.server.utils.Slogf;
import com.samsung.android.feature.SemCscFeature;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final class SaveUi {
    public final boolean mCompatMode;
    public final ComponentName mComponentName;
    public boolean mDestroyed;
    public final Dialog mDialog;
    public final OneActionThenDestroyListener mListener;
    public final OverlayControl mOverlayControl;
    public final PendingUi mPendingUi;
    public final String mServicePackageName;
    public final CharSequence mSubTitle;
    public final int mThemeId;
    public final CharSequence mTitle;
    public final int mType;
    public final Handler mHandler = UiThread.getHandler();
    public final MetricsLogger mMetricsLogger = new MetricsLogger();

    public interface OnSaveListener {
        void onCancel(IntentSender intentSender);

        void onDestroy();

        void onSave();

        void startIntentSender(IntentSender intentSender, Intent intent);
    }

    public class OneActionThenDestroyListener implements OnSaveListener {
        public boolean mDone;
        public final OnSaveListener mRealListener;

        public OneActionThenDestroyListener(OnSaveListener onSaveListener) {
            this.mRealListener = onSaveListener;
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public void onSave() {
            if (Helper.sDebug) {
                Slog.d("SaveUi", "OneTimeListener.onSave(): " + this.mDone);
            }
            if (this.mDone) {
                return;
            }
            this.mRealListener.onSave();
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public void onCancel(IntentSender intentSender) {
            if (Helper.sDebug) {
                Slog.d("SaveUi", "OneTimeListener.onCancel(): " + this.mDone);
            }
            if (this.mDone) {
                return;
            }
            this.mRealListener.onCancel(intentSender);
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public void onDestroy() {
            if (Helper.sDebug) {
                Slog.d("SaveUi", "OneTimeListener.onDestroy(): " + this.mDone);
            }
            if (this.mDone) {
                return;
            }
            this.mDone = true;
            this.mRealListener.onDestroy();
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public void startIntentSender(IntentSender intentSender, Intent intent) {
            if (Helper.sDebug) {
                Slog.d("SaveUi", "OneTimeListener.startIntentSender(): " + this.mDone);
            }
            if (this.mDone) {
                return;
            }
            this.mRealListener.startIntentSender(intentSender, intent);
        }
    }

    public SaveUi(Context context, PendingUi pendingUi, CharSequence charSequence, Drawable drawable, String str, ComponentName componentName, final SaveInfo saveInfo, ValueFinder valueFinder, OverlayControl overlayControl, OnSaveListener onSaveListener, boolean z, boolean z2, boolean z3, boolean z4) {
        View inflate;
        CharSequence charSequence2;
        if (Helper.sVerbose) {
            Slogf.m100v("SaveUi", "nightMode: %b displayId: %d", Boolean.valueOf(z), Integer.valueOf(context.getDisplayId()));
        }
        int i = z ? R.style.Theme.DeviceDefault.System.Dialog : R.style.Theme.GlobalSearchBar;
        this.mThemeId = i;
        this.mPendingUi = pendingUi;
        this.mListener = new OneActionThenDestroyListener(onSaveListener);
        this.mOverlayControl = overlayControl;
        this.mServicePackageName = str;
        this.mComponentName = componentName;
        this.mCompatMode = z3;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, i) { // from class: com.android.server.autofill.ui.SaveUi.1
            @Override // android.content.ContextWrapper, android.content.Context
            public void startActivity(Intent intent) {
                if (resolveActivity(intent) == null) {
                    if (Helper.sDebug) {
                        Slog.d("SaveUi", "Can not startActivity for save UI with intent=" + intent);
                        return;
                    }
                    return;
                }
                intent.putExtra("android.view.autofill.extra.RESTORE_CROSS_ACTIVITY", true);
                PendingIntent activityAsUser = PendingIntent.getActivityAsUser(this, 0, intent, 50331648, null, UserHandle.CURRENT);
                if (Helper.sDebug) {
                    Slog.d("SaveUi", "startActivity add save UI restored with intent=" + intent);
                }
                SaveUi.this.startIntentSenderWithRestore(activityAsUser, intent);
            }

            public final ComponentName resolveActivity(Intent intent) {
                PackageManager packageManager = getPackageManager();
                ComponentName resolveActivity = intent.resolveActivity(packageManager);
                if (resolveActivity != null) {
                    return resolveActivity;
                }
                intent.addFlags(IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
                ActivityInfo resolveActivityInfo = intent.resolveActivityInfo(packageManager, 8388608);
                if (resolveActivityInfo != null) {
                    return new ComponentName(resolveActivityInfo.applicationInfo.packageName, resolveActivityInfo.name);
                }
                return null;
            }
        };
        LayoutInflater from = LayoutInflater.from(contextThemeWrapper);
        if (saveInfo.getNegativeActionStyle() == 999) {
            inflate = from.inflate(R.layout.text_edit_suggestion_item_material, (ViewGroup) null);
            inflate.findViewById(R.id.textAutoComplete).setAllowStacking(false);
            charSequence2 = contextThemeWrapper.getString(R.string.config_defaultAmbientContextConsentComponent);
            charSequence2 = "ja".equals(Locale.getDefault().getLanguage()) ? String.format(contextThemeWrapper.getString(R.string.config_defaultAccessibilityService), charSequence2) : charSequence2;
            if (SemCscFeature.getInstance().getBoolean("CscFeature_Common_ReplaceSecBrandAsGalaxy", false)) {
                Log.d("SaveUi", "SemCscFeature");
                View findViewById = inflate.findViewById(R.id.balanced);
                if (findViewById != null) {
                    findViewById.setVisibility(8);
                }
                View findViewById2 = inflate.findViewById(R.id.textCapCharacters);
                if (findViewById2 != null) {
                    findViewById2.setVisibility(0);
                }
            }
        } else {
            inflate = from.inflate(R.layout.car_preference, (ViewGroup) null);
            charSequence2 = charSequence;
        }
        TextView textView = (TextView) inflate.findViewById(R.id.beginning);
        ArraySet arraySet = new ArraySet(3);
        int type = saveInfo.getType();
        this.mType = type;
        if ((type & 1) != 0) {
            arraySet.add(contextThemeWrapper.getString(R.string.config_customVpnConfirmDialogComponent));
        }
        if ((type & 2) != 0) {
            arraySet.add(contextThemeWrapper.getString(R.string.config_customAdbWifiNetworkConfirmationComponent));
        }
        if (Integer.bitCount(type & 100) > 1 || (type & 128) != 0) {
            arraySet.add(contextThemeWrapper.getString(R.string.config_customVpnAlwaysOnDisconnectedDialogComponent));
        } else if ((type & 64) != 0) {
            arraySet.add(contextThemeWrapper.getString(R.string.config_dataUsageSummaryComponent));
        } else if ((type & 4) != 0) {
            arraySet.add(contextThemeWrapper.getString(R.string.config_customAdbWifiNetworkConfirmationSecondaryUserComponent));
        } else if ((type & 32) != 0) {
            arraySet.add(contextThemeWrapper.getString(R.string.config_customCountryDetector));
        }
        if ((type & 8) != 0) {
            arraySet.add(contextThemeWrapper.getString(R.string.config_datause_iface));
        }
        if ((type & 16) != 0) {
            arraySet.add(contextThemeWrapper.getString(R.string.config_customResolverActivity));
        }
        int size = arraySet.size();
        if (size == 1) {
            this.mTitle = Html.fromHtml(contextThemeWrapper.getString(z2 ? R.string.config_defaultAttentionService : R.string.config_customAdbPublicKeyConfirmationSecondaryUserComponent, arraySet.valueAt(0), charSequence2), 0);
        } else if (size == 2) {
            this.mTitle = Html.fromHtml(contextThemeWrapper.getString(z2 ? R.string.config_defaultAppPredictionService : R.string.config_credentialManagerReceiverComponent, arraySet.valueAt(0), arraySet.valueAt(1), charSequence2), 0);
        } else if (size == 3) {
            this.mTitle = Html.fromHtml(contextThemeWrapper.getString(z2 ? R.string.config_defaultAssistantAccessComponent : R.string.config_customAdbPublicKeyConfirmationComponent, arraySet.valueAt(0), arraySet.valueAt(1), arraySet.valueAt(2), charSequence2), 0);
        } else {
            this.mTitle = Html.fromHtml(contextThemeWrapper.getString(z2 ? R.string.config_defaultAmbientContextDetectionService : R.string.config_controlsPackage, charSequence2), 0);
        }
        textView.setText(this.mTitle);
        if (saveInfo.getNegativeActionStyle() != 999) {
            setServiceIcon(contextThemeWrapper, inflate, drawable);
        }
        if (applyCustomDescription(contextThemeWrapper, inflate, valueFinder, saveInfo)) {
            this.mSubTitle = null;
            if (Helper.sDebug) {
                Slog.d("SaveUi", "on constructor: applied custom description");
            }
        } else {
            CharSequence description = saveInfo.getDescription();
            this.mSubTitle = description;
            if (description != null) {
                writeLog(1131);
                ViewGroup viewGroup = (ViewGroup) inflate.findViewById(R.id.back_button);
                TextView textView2 = new TextView(contextThemeWrapper);
                textView2.setText(description);
                applyMovementMethodIfNeed(textView2);
                viewGroup.addView(textView2, new ViewGroup.LayoutParams(-1, -2));
                viewGroup.setVisibility(0);
                viewGroup.setScrollBarDefaultDelayBeforeFade(500);
            }
            if (Helper.sDebug) {
                Slog.d("SaveUi", "on constructor: title=" + ((Object) this.mTitle) + ", subTitle=" + ((Object) description));
            }
        }
        TextView textView3 = (TextView) inflate.findViewById(R.id.beforeDescendants);
        int negativeActionStyle = saveInfo.getNegativeActionStyle();
        TextView textView4 = (TextView) inflate.findViewById(R.id.behind);
        if (saveInfo.getPositiveActionStyle() == 1) {
            textView4.setText(R.string.config_biometric_prompt_ui_package);
        } else if (z2) {
            textView4.setText(R.string.config_defaultAugmentedAutofillService);
        }
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.android.server.autofill.ui.SaveUi$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SaveUi.this.lambda$new$0(view);
            }
        });
        if (saveInfo.getNegativeActionStyle() == 999) {
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.android.server.autofill.ui.SaveUi$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SaveUi.this.lambda$new$1(saveInfo, view);
                }
            });
            CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.textAutoCorrect);
            checkBox.setText(String.format(checkBox.getContext().getString(R.string.config_bodyFontFamilyMedium), (String) charSequence2));
            checkBox.setOnCheckedChangeListener(new C07942(textView4, saveInfo, z2));
        } else {
            if (negativeActionStyle == 1) {
                textView3.setText(R.string.config_companionDeviceManagerPackage);
            } else if (negativeActionStyle == 2) {
                textView3.setText(R.string.config_chooseTypeAndAccountActivity);
            } else {
                textView3.setText(R.string.config_clockFontFamily);
            }
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.android.server.autofill.ui.SaveUi$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SaveUi.this.lambda$new$2(saveInfo, view);
                }
            });
        }
        TypedValue typedValue = new TypedValue();
        contextThemeWrapper.getTheme().resolveAttribute(R.^attr-private.minorWeightMax, typedValue, true);
        if (typedValue.data != 0) {
            boolean z5 = Settings.System.getInt(contextThemeWrapper.getContentResolver(), "show_button_background", 0) == 1;
            TypedValue typedValue2 = new TypedValue();
            contextThemeWrapper.getTheme().resolveAttribute(R.attr.colorBackground, typedValue2, true);
            if (typedValue.resourceId > 0) {
                int color = contextThemeWrapper.getResources().getColor(typedValue2.resourceId);
                textView3.semSetButtonShapeEnabled(z5, color);
                textView4.semSetButtonShapeEnabled(z5, color);
            } else {
                textView3.semSetButtonShapeEnabled(z5);
                textView4.semSetButtonShapeEnabled(z5);
            }
        }
        Dialog dialog = new Dialog(contextThemeWrapper, i);
        this.mDialog = dialog;
        dialog.setContentView(inflate);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.server.autofill.ui.SaveUi$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                SaveUi.this.lambda$new$3(dialogInterface);
            }
        });
        Window window = dialog.getWindow();
        window.setType(2038);
        window.addFlags(131074);
        window.setDimAmount(0.6f);
        window.addPrivateFlags(16);
        window.setSoftInputMode(32);
        window.setGravity(81);
        window.setCloseOnTouchOutside(true);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.accessibilityTitle = contextThemeWrapper.getString(R.string.config_carrierAppInstallDialogComponent);
        attributes.windowAnimations = R.style.AutofillDatasetPicker;
        attributes.setTrustedOverlay();
        show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        this.mListener.onSave();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(SaveInfo saveInfo, View view) {
        this.mListener.onCancel(saveInfo.getNegativeActionListener());
    }

    /* renamed from: com.android.server.autofill.ui.SaveUi$2 */
    public class C07942 implements CompoundButton.OnCheckedChangeListener {
        public final /* synthetic */ SaveInfo val$info;
        public final /* synthetic */ boolean val$isUpdate;
        public final /* synthetic */ TextView val$yesButton;

        public C07942(TextView textView, SaveInfo saveInfo, boolean z) {
            this.val$yesButton = textView;
            this.val$info = saveInfo;
            this.val$isUpdate = z;
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (compoundButton.isChecked()) {
                this.val$yesButton.setText(R.string.config_chooserActivity);
                TextView textView = this.val$yesButton;
                final SaveInfo saveInfo = this.val$info;
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.android.server.autofill.ui.SaveUi$2$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SaveUi.C07942.this.lambda$onCheckedChanged$0(saveInfo, view);
                    }
                });
                return;
            }
            if (this.val$isUpdate) {
                this.val$yesButton.setText(R.string.config_defaultAugmentedAutofillService);
            } else {
                this.val$yesButton.setText(R.string.config_defaultAccessibilityNotificationSound);
            }
            this.val$yesButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.server.autofill.ui.SaveUi$2$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SaveUi.C07942.this.lambda$onCheckedChanged$1(view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCheckedChanged$0(SaveInfo saveInfo, View view) {
            SaveUi.this.mListener.onCancel(saveInfo.semGetNegativeSecondActionListener());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCheckedChanged$1(View view) {
            SaveUi.this.mListener.onSave();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(SaveInfo saveInfo, View view) {
        this.mListener.onCancel(saveInfo.getNegativeActionListener());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(DialogInterface dialogInterface) {
        this.mListener.onCancel(null);
    }

    public final boolean applyCustomDescription(Context context, View view, ValueFinder valueFinder, SaveInfo saveInfo) {
        CustomDescription customDescription = saveInfo.getCustomDescription();
        if (customDescription == null) {
            return false;
        }
        writeLog(1129);
        RemoteViews sanitizeRemoteView = Helper.sanitizeRemoteView(customDescription.getPresentation());
        if (sanitizeRemoteView == null) {
            Slog.w("SaveUi", "No remote view on custom description");
            return false;
        }
        ArrayList transformations = customDescription.getTransformations();
        if (Helper.sVerbose) {
            Slog.v("SaveUi", "applyCustomDescription(): transformations = " + transformations);
        }
        if (transformations != null && !InternalTransformation.batchApply(valueFinder, sanitizeRemoteView, transformations)) {
            Slog.w("SaveUi", "could not apply main transformations on custom description");
            return false;
        }
        try {
            View applyWithTheme = sanitizeRemoteView.applyWithTheme(context, null, new RemoteViews.InteractionHandler() { // from class: com.android.server.autofill.ui.SaveUi$$ExternalSyntheticLambda4
                public final boolean onInteraction(View view2, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
                    boolean lambda$applyCustomDescription$4;
                    lambda$applyCustomDescription$4 = SaveUi.this.lambda$applyCustomDescription$4(view2, pendingIntent, remoteResponse);
                    return lambda$applyCustomDescription$4;
                }
            }, this.mThemeId);
            ArrayList updates = customDescription.getUpdates();
            if (Helper.sVerbose) {
                Slog.v("SaveUi", "applyCustomDescription(): view = " + applyWithTheme + " updates=" + updates);
            }
            if (updates != null) {
                int size = updates.size();
                if (Helper.sDebug) {
                    Slog.d("SaveUi", "custom description has " + size + " batch updates");
                }
                for (int i = 0; i < size; i++) {
                    Pair pair = (Pair) updates.get(i);
                    InternalValidator internalValidator = (InternalValidator) pair.first;
                    if (internalValidator != null && internalValidator.isValid(valueFinder)) {
                        BatchUpdates batchUpdates = (BatchUpdates) pair.second;
                        RemoteViews sanitizeRemoteView2 = Helper.sanitizeRemoteView(batchUpdates.getUpdates());
                        if (sanitizeRemoteView2 != null) {
                            if (Helper.sDebug) {
                                Slog.d("SaveUi", "Applying template updates for batch update #" + i);
                            }
                            sanitizeRemoteView2.reapply(context, applyWithTheme);
                        }
                        ArrayList transformations2 = batchUpdates.getTransformations();
                        if (transformations2 == null) {
                            continue;
                        } else {
                            if (Helper.sDebug) {
                                Slog.d("SaveUi", "Applying child transformation for batch update #" + i + ": " + transformations2);
                            }
                            if (!InternalTransformation.batchApply(valueFinder, sanitizeRemoteView, transformations2)) {
                                Slog.w("SaveUi", "Could not apply child transformation for batch update #" + i + ": " + transformations2);
                                return false;
                            }
                            sanitizeRemoteView.reapply(context, applyWithTheme);
                        }
                    }
                    if (Helper.sDebug) {
                        Slog.d("SaveUi", "Skipping batch update #" + i);
                    }
                }
            }
            SparseArray actions = customDescription.getActions();
            if (actions != null) {
                int size2 = actions.size();
                if (Helper.sDebug) {
                    Slog.d("SaveUi", "custom description has " + size2 + " actions");
                }
                if (applyWithTheme instanceof ViewGroup) {
                    final ViewGroup viewGroup = (ViewGroup) applyWithTheme;
                    for (int i2 = 0; i2 < size2; i2++) {
                        int keyAt = actions.keyAt(i2);
                        final InternalOnClickAction internalOnClickAction = (InternalOnClickAction) actions.valueAt(i2);
                        View findViewById = viewGroup.findViewById(keyAt);
                        if (findViewById == null) {
                            Slog.w("SaveUi", "Ignoring action " + internalOnClickAction + " for view " + keyAt + " because it's not on " + viewGroup);
                        } else {
                            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.server.autofill.ui.SaveUi$$ExternalSyntheticLambda5
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    SaveUi.lambda$applyCustomDescription$5(internalOnClickAction, viewGroup, view2);
                                }
                            });
                        }
                    }
                } else {
                    Slog.w("SaveUi", "cannot apply actions because custom description root is not a ViewGroup: " + applyWithTheme);
                }
            }
            applyTextViewStyle(applyWithTheme);
            ViewGroup viewGroup2 = (ViewGroup) view.findViewById(R.id.back_button);
            viewGroup2.addView(applyWithTheme);
            viewGroup2.setVisibility(0);
            viewGroup2.setScrollBarDefaultDelayBeforeFade(500);
            return true;
        } catch (Exception e) {
            Slog.e("SaveUi", "Error applying custom description. ", e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$applyCustomDescription$4(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
        Intent intent = (Intent) remoteResponse.getLaunchOptions(view).first;
        if (!isValidLink(pendingIntent, intent)) {
            LogMaker newLogMaker = newLogMaker(1132, this.mType);
            newLogMaker.setType(0);
            this.mMetricsLogger.write(newLogMaker);
            return false;
        }
        startIntentSenderWithRestore(pendingIntent, intent);
        return true;
    }

    public static /* synthetic */ void lambda$applyCustomDescription$5(InternalOnClickAction internalOnClickAction, ViewGroup viewGroup, View view) {
        if (Helper.sVerbose) {
            Slog.v("SaveUi", "Applying " + internalOnClickAction + " after " + view + " was clicked");
        }
        internalOnClickAction.onClick(viewGroup);
    }

    public final void startIntentSenderWithRestore(PendingIntent pendingIntent, Intent intent) {
        if (Helper.sVerbose) {
            Slog.v("SaveUi", "Intercepting custom description intent");
        }
        IBinder token = this.mPendingUi.getToken();
        intent.putExtra("android.view.autofill.extra.RESTORE_SESSION_TOKEN", token);
        this.mListener.startIntentSender(pendingIntent.getIntentSender(), intent);
        this.mPendingUi.setState(2);
        if (Helper.sDebug) {
            Slog.d("SaveUi", "hiding UI until restored with token " + token);
        }
        hide();
        LogMaker newLogMaker = newLogMaker(1132, this.mType);
        newLogMaker.setType(1);
        this.mMetricsLogger.write(newLogMaker);
    }

    public final void applyTextViewStyle(View view) {
        final ArrayList arrayList = new ArrayList();
        view.findViewByPredicate(new Predicate() { // from class: com.android.server.autofill.ui.SaveUi$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$applyTextViewStyle$6;
                lambda$applyTextViewStyle$6 = SaveUi.lambda$applyTextViewStyle$6(arrayList, (View) obj);
                return lambda$applyTextViewStyle$6;
            }
        });
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            applyMovementMethodIfNeed((TextView) arrayList.get(i));
        }
    }

    public static /* synthetic */ boolean lambda$applyTextViewStyle$6(List list, View view) {
        if (!(view instanceof TextView)) {
            return false;
        }
        list.add((TextView) view);
        return false;
    }

    public final void applyMovementMethodIfNeed(TextView textView) {
        CharSequence text = textView.getText();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        if (ArrayUtils.isEmpty((ClickableSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ClickableSpan.class))) {
            return;
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public final void setServiceIcon(Context context, View view, Drawable drawable) {
        ImageView imageView = (ImageView) view.findViewById(R.id.balanced);
        context.getResources();
        imageView.setImageDrawable(drawable);
    }

    public static boolean isValidLink(PendingIntent pendingIntent, Intent intent) {
        if (pendingIntent == null) {
            Slog.w("SaveUi", "isValidLink(): custom description without pending intent");
            return false;
        }
        if (!pendingIntent.isActivity()) {
            Slog.w("SaveUi", "isValidLink(): pending intent not for activity");
            return false;
        }
        if (intent != null) {
            return true;
        }
        Slog.w("SaveUi", "isValidLink(): no intent");
        return false;
    }

    public final LogMaker newLogMaker(int i, int i2) {
        return newLogMaker(i).addTaggedData(1130, Integer.valueOf(i2));
    }

    public final LogMaker newLogMaker(int i) {
        return Helper.newLogMaker(i, this.mComponentName, this.mServicePackageName, this.mPendingUi.sessionId, this.mCompatMode);
    }

    public final void writeLog(int i) {
        this.mMetricsLogger.write(newLogMaker(i, this.mType));
    }

    public void onPendingUi(int i, IBinder iBinder) {
        if (!this.mPendingUi.matches(iBinder)) {
            Slog.w("SaveUi", "restore(" + i + "): got token " + iBinder + " instead of " + this.mPendingUi.getToken());
            return;
        }
        LogMaker newLogMaker = newLogMaker(1134);
        try {
            if (i == 1) {
                newLogMaker.setType(5);
                if (Helper.sDebug) {
                    Slog.d("SaveUi", "Cancelling pending save dialog for " + iBinder);
                }
                hide();
            } else if (i == 2) {
                if (Helper.sDebug) {
                    Slog.d("SaveUi", "Restoring save dialog for " + iBinder);
                }
                newLogMaker.setType(1);
                show();
            } else {
                newLogMaker.setType(11);
                Slog.w("SaveUi", "restore(): invalid operation " + i);
            }
            this.mMetricsLogger.write(newLogMaker);
            this.mPendingUi.setState(4);
        } catch (Throwable th) {
            this.mMetricsLogger.write(newLogMaker);
            throw th;
        }
    }

    public final void show() {
        Slog.i("SaveUi", "Showing save dialog: " + ((Object) this.mTitle));
        this.mDialog.show();
        this.mOverlayControl.hideOverlays();
    }

    public PendingUi hide() {
        if (Helper.sVerbose) {
            Slog.v("SaveUi", "Hiding save dialog.");
        }
        try {
            this.mDialog.hide();
            this.mOverlayControl.showOverlays();
            return this.mPendingUi;
        } catch (Throwable th) {
            this.mOverlayControl.showOverlays();
            throw th;
        }
    }

    public boolean isShowing() {
        return this.mDialog.isShowing();
    }

    public void destroy() {
        try {
            if (Helper.sDebug) {
                Slog.d("SaveUi", "destroy()");
            }
            throwIfDestroyed();
            this.mListener.onDestroy();
            this.mHandler.removeCallbacksAndMessages(this.mListener);
            this.mDialog.dismiss();
            this.mDestroyed = true;
        } finally {
            this.mOverlayControl.showOverlays();
        }
    }

    public final void throwIfDestroyed() {
        if (this.mDestroyed) {
            throw new IllegalStateException("cannot interact with a destroyed instance");
        }
    }

    public String toString() {
        CharSequence charSequence = this.mTitle;
        return charSequence == null ? "NO TITLE" : charSequence.toString();
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.print("title: ");
        printWriter.println(this.mTitle);
        printWriter.print(str);
        printWriter.print("subtitle: ");
        printWriter.println(this.mSubTitle);
        printWriter.print(str);
        printWriter.print("pendingUi: ");
        printWriter.println(this.mPendingUi);
        printWriter.print(str);
        printWriter.print("service: ");
        printWriter.println(this.mServicePackageName);
        printWriter.print(str);
        printWriter.print("app: ");
        printWriter.println(this.mComponentName.toShortString());
        printWriter.print(str);
        printWriter.print("compat mode: ");
        printWriter.println(this.mCompatMode);
        printWriter.print(str);
        printWriter.print("theme id: ");
        printWriter.print(this.mThemeId);
        int i = this.mThemeId;
        if (i == 16974865) {
            printWriter.println(" (dark)");
        } else if (i == 16974878) {
            printWriter.println(" (light)");
        } else {
            printWriter.println("(UNKNOWN_MODE)");
        }
        View decorView = this.mDialog.getWindow().getDecorView();
        int[] locationOnScreen = decorView.getLocationOnScreen();
        printWriter.print(str);
        printWriter.print("coordinates: ");
        printWriter.print('(');
        printWriter.print(locationOnScreen[0]);
        printWriter.print(',');
        printWriter.print(locationOnScreen[1]);
        printWriter.print(')');
        printWriter.print('(');
        printWriter.print(locationOnScreen[0] + decorView.getWidth());
        printWriter.print(',');
        printWriter.print(locationOnScreen[1] + decorView.getHeight());
        printWriter.println(')');
        printWriter.print(str);
        printWriter.print("destroyed: ");
        printWriter.println(this.mDestroyed);
    }
}
