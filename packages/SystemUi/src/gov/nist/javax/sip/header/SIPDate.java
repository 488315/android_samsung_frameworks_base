package gov.nist.javax.sip.header;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SIPDate implements Cloneable, Serializable {
    private static final long serialVersionUID = 8544101899928346909L;
    protected int day;
    protected int hour;
    private Calendar javaCal;
    protected int minute;
    protected int month;
    protected int second;
    protected String sipMonth;
    protected String sipWkDay;
    protected int wkday;
    protected int year;

    public SIPDate() {
        this.wkday = -1;
        this.day = -1;
        this.month = -1;
        this.year = -1;
        this.hour = -1;
        this.minute = -1;
        this.second = -1;
        this.javaCal = null;
    }

    public final Object clone() {
        try {
            SIPDate sIPDate = (SIPDate) super.clone();
            Calendar calendar = this.javaCal;
            if (calendar != null) {
                sIPDate.javaCal = (Calendar) calendar.clone();
            }
            return sIPDate;
        } catch (CloneNotSupportedException unused) {
            throw new RuntimeException("Internal error");
        }
    }

    public final boolean equals(Object obj) {
        if (obj.getClass() != getClass()) {
            return false;
        }
        SIPDate sIPDate = (SIPDate) obj;
        return this.wkday == sIPDate.wkday && this.day == sIPDate.day && this.month == sIPDate.month && this.year == sIPDate.year && this.hour == sIPDate.hour && this.minute == sIPDate.minute && this.second == sIPDate.second;
    }

    public SIPDate(long j) {
        this.javaCal = new GregorianCalendar(TimeZone.getTimeZone("GMT:0"), Locale.getDefault());
        this.javaCal.setTime(new Date(j));
        int i = this.javaCal.get(7);
        this.wkday = i;
        switch (i) {
            case 1:
                this.sipWkDay = "Sun";
                break;
            case 2:
                this.sipWkDay = "Mon";
                break;
            case 3:
                this.sipWkDay = "Tue";
                break;
            case 4:
                this.sipWkDay = "Wed";
                break;
            case 5:
                this.sipWkDay = "Thu";
                break;
            case 6:
                this.sipWkDay = "Fri";
                break;
            case 7:
                this.sipWkDay = "Sat";
                break;
            default:
                String str = "No date map for wkday " + this.wkday;
                new Exception().printStackTrace();
                System.err.println("Unexepcted INTERNAL ERROR FIXME!!");
                System.err.println(str);
                throw new RuntimeException(str);
        }
        this.day = this.javaCal.get(5);
        int i2 = this.javaCal.get(2);
        this.month = i2;
        switch (i2) {
            case 0:
                this.sipMonth = "Jan";
                break;
            case 1:
                this.sipMonth = "Feb";
                break;
            case 2:
                this.sipMonth = "Mar";
                break;
            case 3:
                this.sipMonth = "Apr";
                break;
            case 4:
                this.sipMonth = "May";
                break;
            case 5:
                this.sipMonth = "Jun";
                break;
            case 6:
                this.sipMonth = "Jul";
                break;
            case 7:
                this.sipMonth = "Aug";
                break;
            case 8:
                this.sipMonth = "Sep";
                break;
            case 9:
                this.sipMonth = "Oct";
                break;
            case 10:
                this.sipMonth = "Nov";
                break;
            case 11:
                this.sipMonth = "Dec";
                break;
            default:
                String str2 = "No date map for month " + this.month;
                new Exception().printStackTrace();
                System.err.println("Unexepcted INTERNAL ERROR FIXME!!");
                System.err.println(str2);
                throw new RuntimeException(str2);
        }
        this.year = this.javaCal.get(1);
        this.hour = this.javaCal.get(11);
        this.minute = this.javaCal.get(12);
        this.second = this.javaCal.get(13);
    }
}
