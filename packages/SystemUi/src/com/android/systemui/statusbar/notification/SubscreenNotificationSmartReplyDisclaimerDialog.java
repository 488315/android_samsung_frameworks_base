package com.android.systemui.statusbar.notification;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.telephony.TelephonyManager;
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
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscreenNotificationSmartReplyDisclaimerDialog {
    public AlertDialog alertDialog;
    public final Context mContext;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        TextView textView = (TextView) inflate.findViewById(R.id.subscreen_noti_smart_reply_main_dialog_terms_and_condition);
        String string = context.getString(R.string.f802x82c49343);
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
            int indexOf$default = StringsKt__StringsKt.indexOf$default(spannableString, str, r7, r7, 6);
            int length = str.length() + indexOf$default;
            if (indexOf$default < 0 || length > spannableString.length()) {
                textView.setText(format);
            } else {
                spannableString.setSpan(arrayList2.get(i2), indexOf$default, length, 33);
                spannableString.setSpan(new StyleSpan(style), indexOf$default, length, 33);
                spannableString.setSpan(new ForegroundColorSpan(context.getColor(R.color.f694xebac9036)), indexOf$default, length, 33);
            }
            i2 = i3;
            r7 = 0;
        }
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) inflate.findViewById(R.id.subscreen_noti_smart_reply_main_dialog_description)).setText(context.getString(R.string.subscreen_notification_smart_reply_disclaimer_introduce) + "\n" + context.getString(R.string.subscreen_notification_smart_reply_disclaimer_policy));
        final ScrollView scrollView = (ScrollView) inflate.findViewById(R.id.smart_reply_main_disclaimer_scroll_container);
        final LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.smart_reply_main_disclaimer_container);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, 2132018528);
        builder.setPositiveButton(R.string.subscreen_notification_dialog_ok, (DialogInterface.OnClickListener) null);
        builder.setNegativeButton(R.string.subscreen_notification_dialog_cancel, onClickListener2);
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        this.alertDialog = create;
        if (create != null) {
            create.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationSmartReplyDisclaimerDialog$1$1
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    if (scrollView.getHeight() < linearLayout.getHeight()) {
                        create.getButton(-1).setText(context.getString(R.string.subscreen_notification_dialog_more));
                        create.getButton(-1).setTag("MORE");
                        final ScrollView scrollView2 = scrollView;
                        final LinearLayout linearLayout2 = linearLayout;
                        final AlertDialog alertDialog = create;
                        final Context context2 = context;
                        scrollView2.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationSmartReplyDisclaimerDialog$1$1.1
                            @Override // android.view.View.OnScrollChangeListener
                            public final void onScrollChange(View view, int i4, int i5, int i6, int i7) {
                                if (scrollView2.getHeight() + i5 >= linearLayout2.getHeight()) {
                                    alertDialog.getButton(-1).setText(context2.getString(R.string.subscreen_notification_dialog_ok));
                                    alertDialog.getButton(-1).setTag(null);
                                    scrollView2.setOnScrollChangeListener(null);
                                }
                            }
                        });
                    } else {
                        create.getButton(-1).setText(context.getString(R.string.subscreen_notification_dialog_ok));
                        create.getButton(-1).setTag(null);
                    }
                    Button button = create.getButton(-1);
                    final ScrollView scrollView3 = scrollView;
                    final DialogInterface.OnClickListener onClickListener3 = onClickListener;
                    final SubscreenNotificationSmartReplyDisclaimerDialog subscreenNotificationSmartReplyDisclaimerDialog = this;
                    button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationSmartReplyDisclaimerDialog$1$1.2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            if (Intrinsics.areEqual("MORE", view.getTag())) {
                                scrollView3.fullScroll(130);
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
                intent.setData(Uri.parse(subscreenNotificationSmartReplyDisclaimerDialog.getConsentUri(str)));
                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.e("SubscreenNotificationSmartReplyDisclaimerDialog", "ActivityNotFoundException : " + e.getMessage());
            }
        }
    }

    public final String getConsentUri(String str) {
        return MotionLayout$$ExternalSyntheticOutline0.m22m("https://policies.account.samsung.com/terms?appKey=j5p7ll8g33", "&applicationRegion=".concat(getIsoCountryCode()), KeyAttributes$$ExternalSyntheticOutline0.m21m("&language=", Locale.getDefault().getLanguage()), "&region=".concat(getIsoCountryCode()), "&type=".concat(str));
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0021 A[Catch: Exception -> 0x003a, TryCatch #0 {Exception -> 0x003a, blocks: (B:2:0x0000, B:4:0x0004, B:6:0x0015, B:11:0x0021, B:12:0x0025), top: B:1:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0039 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getIsoCountryCode() {
        boolean z;
        String iSO3Country;
        try {
            Context context = this.mContext;
            if (context != null) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                String simCountryIso = telephonyManager.getSimCountryIso();
                if (simCountryIso != null && simCountryIso.length() != 0) {
                    z = false;
                    if (z) {
                        simCountryIso = telephonyManager.getNetworkCountryIso();
                    }
                    iSO3Country = new Locale("", simCountryIso).getISO3Country();
                    if (iSO3Country.length() > 0) {
                        return iSO3Country;
                    }
                }
                z = true;
                if (z) {
                }
                iSO3Country = new Locale("", simCountryIso).getISO3Country();
                if (iSO3Country.length() > 0) {
                }
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m58m("getIsoCountryCode: ", e, "SubscreenNotificationSmartReplyDisclaimerDialog");
        }
        return Locale.getDefault().getISO3Country();
    }
}
