package com.android.systemui.statusbar.notification;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SubscreenNotificationSmartReplyDisclaimerDialog {
    public AlertDialog alertDialog;
    public final Context mContext;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7, types: [boolean, int] */
    public SubscreenNotificationSmartReplyDisclaimerDialog(final Context context, final DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2, DialogInterface.OnDismissListener onDismissListener) {
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.subscreen_notification_smart_reply_main_disclaimer_dialog, (ViewGroup) null);
        View findViewById = inflate.findViewById(R.id.subscreen_noti_smart_reply_main_dialog_terms_and_condition);
        Intrinsics.checkNotNull(findViewById);
        TextView textView = (TextView) findViewById;
        String string = context.getString(R.string.subscreen_notification_smart_reply_disclaimer_terms_and_condition);
        String substringBefore$default = StringsKt__StringsKt.substringBefore$default(StringsKt__StringsKt.substringAfter$default(string, "%1$s"), "%2$s");
        String substringBefore$default2 = StringsKt__StringsKt.substringBefore$default(StringsKt__StringsKt.substringAfter$default(string, "%3$s"), "%4$s");
        ArrayList arrayList = new ArrayList();
        arrayList.add(substringBefore$default);
        arrayList.add(substringBefore$default2);
        ClickableSpan clickableSpan = new ClickableSpan() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationSmartReplyDisclaimerDialog$setClickableSpanText$1$clickableSpanForPrivacyNotice$1
            @Override // android.text.style.ClickableSpan
            public final void onClick(View view) {
                SubscreenNotificationSmartReplyDisclaimerDialog.access$handleTextLinkClick(SubscreenNotificationSmartReplyDisclaimerDialog.this, context, "PN");
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationSmartReplyDisclaimerDialog$setClickableSpanText$1$clickableSpanForTermsAndConditions$1
            @Override // android.text.style.ClickableSpan
            public final void onClick(View view) {
                SubscreenNotificationSmartReplyDisclaimerDialog.access$handleTextLinkClick(SubscreenNotificationSmartReplyDisclaimerDialog.this, context, "TC");
            }
        };
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(clickableSpan);
        arrayList2.add(clickableSpan2);
        int i = StringCompanionObject.$r8$clinit;
        String format = String.format(string, Arrays.copyOf(new Object[]{"", "", "", ""}, 4));
        ?? r7 = 0;
        int style = Typeface.create(null, VolteConstants.ErrorCode.BUSY_EVERYWHERE, false).getStyle();
        SpannableString spannableString = new SpannableString(format);
        Iterator it = arrayList.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            Object next = it.next();
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            String str = (String) next;
            int indexOf$default = StringsKt__StringsKt.indexOf$default(spannableString, str, (int) r7, (boolean) r7, 6);
            int length = str.length() + indexOf$default;
            if (indexOf$default < 0 || length > spannableString.length()) {
                textView.setText(format);
            } else {
                spannableString.setSpan(arrayList2.get(i2), indexOf$default, length, 33);
                spannableString.setSpan(new StyleSpan(style), indexOf$default, length, 33);
                spannableString.setSpan(new ForegroundColorSpan(context.getColor(R.color.subscreen_noti_smart_reply_main_disclaimer_spannable_link_text_color)), indexOf$default, length, 33);
            }
            i2 = i3;
            r7 = 0;
        }
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        TextView textView2 = (TextView) inflate.findViewById(R.id.subscreen_noti_smart_reply_main_dialog_description);
        if (textView2 != null) {
            textView2.setText(context.getString(R.string.subscreen_notification_smart_reply_disclaimer_introduce) + "\n" + context.getString(R.string.subscreen_notification_smart_reply_disclaimer_policy));
        }
        final ScrollView scrollView = (ScrollView) inflate.findViewById(R.id.smart_reply_main_disclaimer_scroll_container);
        final LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.smart_reply_main_disclaimer_container);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, R.style.Theme_SystemUI_Dialog_Alert);
        builder.setPositiveButton(R.string.subscreen_notification_dialog_ok, (DialogInterface.OnClickListener) null);
        builder.setNegativeButton(R.string.subscreen_notification_dialog_cancel, onClickListener2);
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        this.alertDialog = create;
        if (create != null) {
            create.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationSmartReplyDisclaimerDialog$1$1
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    ScrollView scrollView2 = scrollView;
                    Integer valueOf = scrollView2 != null ? Integer.valueOf(scrollView2.getHeight()) : null;
                    LinearLayout linearLayout2 = linearLayout;
                    Integer valueOf2 = linearLayout2 != null ? Integer.valueOf(linearLayout2.getHeight()) : null;
                    if (valueOf != null) {
                        int intValue = valueOf.intValue();
                        Intrinsics.checkNotNull(valueOf2);
                        if (intValue < valueOf2.intValue()) {
                            create.getButton(-1).setText(context.getString(R.string.subscreen_notification_dialog_more));
                            create.getButton(-1).setTag("MORE");
                            final ScrollView scrollView3 = scrollView;
                            if (scrollView3 != null) {
                                final LinearLayout linearLayout3 = linearLayout;
                                final AlertDialog alertDialog = create;
                                final Context context2 = context;
                                scrollView3.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationSmartReplyDisclaimerDialog$1$1.1
                                    @Override // android.view.View.OnScrollChangeListener
                                    public final void onScrollChange(View view, int i4, int i5, int i6, int i7) {
                                        ScrollView scrollView4 = scrollView3;
                                        Integer valueOf3 = scrollView4 != null ? Integer.valueOf(scrollView4.getHeight() + i5) : null;
                                        Intrinsics.checkNotNull(valueOf3);
                                        int intValue2 = valueOf3.intValue();
                                        LinearLayout linearLayout4 = linearLayout3;
                                        Integer valueOf4 = linearLayout4 != null ? Integer.valueOf(linearLayout4.getHeight()) : null;
                                        Intrinsics.checkNotNull(valueOf4);
                                        if (intValue2 >= valueOf4.intValue()) {
                                            alertDialog.getButton(-1).setText(context2.getString(R.string.subscreen_notification_dialog_ok));
                                            alertDialog.getButton(-1).setTag(null);
                                            ScrollView scrollView5 = scrollView3;
                                            if (scrollView5 != null) {
                                                scrollView5.setOnScrollChangeListener(null);
                                            }
                                        }
                                    }
                                });
                            }
                        } else {
                            create.getButton(-1).setText(context.getString(R.string.subscreen_notification_dialog_ok));
                            create.getButton(-1).setTag(null);
                        }
                    }
                    Button button = create.getButton(-1);
                    final ScrollView scrollView4 = scrollView;
                    final DialogInterface.OnClickListener onClickListener3 = onClickListener;
                    final SubscreenNotificationSmartReplyDisclaimerDialog subscreenNotificationSmartReplyDisclaimerDialog = this;
                    button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationSmartReplyDisclaimerDialog$1$1.2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            if ("MORE".equals(view.getTag())) {
                                ScrollView scrollView5 = scrollView4;
                                if (scrollView5 != null) {
                                    scrollView5.fullScroll(130);
                                    return;
                                }
                                return;
                            }
                            DialogInterface.OnClickListener onClickListener4 = onClickListener3;
                            if (onClickListener4 != null) {
                                onClickListener4.onClick(subscreenNotificationSmartReplyDisclaimerDialog.alertDialog, -1);
                            }
                        }
                    });
                }
            });
            create.setOnDismissListener(onDismissListener);
        }
    }

    public static final void access$handleTextLinkClick(SubscreenNotificationSmartReplyDisclaimerDialog subscreenNotificationSmartReplyDisclaimerDialog, Context context, String str) {
        subscreenNotificationSmartReplyDisclaimerDialog.getClass();
        if (context != null) {
            try {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(MotionLayout$$ExternalSyntheticOutline0.m("https://policies.account.samsung.com/terms?appKey=j5p7ll8g33", "&applicationRegion=".concat(subscreenNotificationSmartReplyDisclaimerDialog.getIsoCountryCode()), AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("&language=", Locale.getDefault().getLanguage()), "&region=".concat(subscreenNotificationSmartReplyDisclaimerDialog.getIsoCountryCode()), "&type=".concat(str))));
                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.e("SubscreenNotificationSmartReplyDisclaimerDialog", "ActivityNotFoundException : " + e.getMessage());
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0033 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getIsoCountryCode() {
        /*
            r2 = this;
            android.content.Context r2 = r2.mContext     // Catch: java.lang.Exception -> L19
            if (r2 == 0) goto L3b
            java.lang.String r0 = "phone"
            java.lang.Object r2 = r2.getSystemService(r0)     // Catch: java.lang.Exception -> L19
            android.telephony.TelephonyManager r2 = (android.telephony.TelephonyManager) r2     // Catch: java.lang.Exception -> L19
            java.lang.String r0 = r2.getSimCountryIso()     // Catch: java.lang.Exception -> L19
            if (r0 == 0) goto L1b
            int r1 = r0.length()     // Catch: java.lang.Exception -> L19
            if (r1 != 0) goto L1f
            goto L1b
        L19:
            r2 = move-exception
            goto L34
        L1b:
            java.lang.String r0 = r2.getNetworkCountryIso()     // Catch: java.lang.Exception -> L19
        L1f:
            java.util.Locale r2 = new java.util.Locale     // Catch: java.lang.Exception -> L19
            java.lang.String r1 = ""
            r2.<init>(r1, r0)     // Catch: java.lang.Exception -> L19
            java.lang.String r2 = r2.getISO3Country()     // Catch: java.lang.Exception -> L19
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)     // Catch: java.lang.Exception -> L19
            int r0 = r2.length()     // Catch: java.lang.Exception -> L19
            if (r0 <= 0) goto L3b
            return r2
        L34:
            java.lang.String r0 = "getIsoCountryCode: "
            java.lang.String r1 = "SubscreenNotificationSmartReplyDisclaimerDialog"
            com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0.m(r0, r2, r1)
        L3b:
            java.util.Locale r2 = java.util.Locale.getDefault()
            java.lang.String r2 = r2.getISO3Country()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenNotificationSmartReplyDisclaimerDialog.getIsoCountryCode():java.lang.String");
    }
}
