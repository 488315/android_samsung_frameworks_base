package gov.nist.javax.sip.header;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import java.util.Calendar;
import javax.sip.header.Header;

/* loaded from: classes4.dex */
public class SIPDateHeader extends SIPHeader implements Header {
    private static final long serialVersionUID = 1734186339037274664L;
    protected SIPDate date;

    public SIPDateHeader() {
        super("Date");
    }

    @Override // gov.nist.core.GenericObject
    public final Object clone() {
        SIPDateHeader sIPDateHeader = (SIPDateHeader) super.clone();
        SIPDate sIPDate = this.date;
        if (sIPDate != null) {
            sIPDateHeader.date = (SIPDate) sIPDate.clone();
        }
        return sIPDateHeader;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        String str;
        String str2;
        String str3;
        String str4;
        SIPDate sIPDate = this.date;
        if (sIPDate.day < 10) {
            str = "0" + sIPDate.day;
        } else {
            str = "" + sIPDate.day;
        }
        if (sIPDate.hour < 10) {
            str2 = "0" + sIPDate.hour;
        } else {
            str2 = "" + sIPDate.hour;
        }
        if (sIPDate.minute < 10) {
            str3 = "0" + sIPDate.minute;
        } else {
            str3 = "" + sIPDate.minute;
        }
        if (sIPDate.second < 10) {
            str4 = "0" + sIPDate.second;
        } else {
            str4 = "" + sIPDate.second;
        }
        String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sIPDate.sipWkDay != null ? TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(""), sIPDate.sipWkDay, ", ") : "", str, " ");
        if (sIPDate.sipMonth != null) {
            strM = TransitionKt$$ExternalSyntheticOutline0.m(PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM), sIPDate.sipMonth, " ");
        }
        StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM);
        sbM.append(sIPDate.year);
        sbM.append(" ");
        sbM.append(str2);
        sbM.append(":");
        return NotificationController$$ExternalSyntheticOutline0.m(sbM, str3, ":", str4, " GMT");
    }

    public final void setDate(Calendar calendar) {
        this.date = new SIPDate(calendar.getTime().getTime());
    }
}
