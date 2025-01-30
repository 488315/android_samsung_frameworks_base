package gov.nist.javax.sip.header;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.sec.ims.configuration.DATA;
import java.util.Calendar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SIPDateHeader extends SIPHeader {
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
            str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN + sIPDate.day;
        } else {
            str = "" + sIPDate.day;
        }
        if (sIPDate.hour < 10) {
            str2 = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN + sIPDate.hour;
        } else {
            str2 = "" + sIPDate.hour;
        }
        if (sIPDate.minute < 10) {
            str3 = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN + sIPDate.minute;
        } else {
            str3 = "" + sIPDate.minute;
        }
        if (sIPDate.second < 10) {
            str4 = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN + sIPDate.second;
        } else {
            str4 = "" + sIPDate.second;
        }
        String m15m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(sIPDate.sipWkDay != null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(""), sIPDate.sipWkDay, ", ") : "", str, " ");
        if (sIPDate.sipMonth != null) {
            m15m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(m15m), sIPDate.sipMonth, " ");
        }
        StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(m15m);
        m18m.append(sIPDate.year);
        m18m.append(" ");
        m18m.append(str2);
        m18m.append(":");
        m18m.append(str3);
        m18m.append(":");
        m18m.append(str4);
        m18m.append(" GMT");
        return m18m.toString();
    }

    public final void setDate(Calendar calendar) {
        this.date = new SIPDate(calendar.getTime().getTime());
    }
}
