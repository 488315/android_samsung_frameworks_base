package com.android.internal.org.bouncycastle.asn1.x509;

import android.hardware.gnss.GnssSignalType;
import com.android.internal.org.bouncycastle.asn1.ASN1Choice;
import com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.ASN1UTCTime;
import com.android.internal.org.bouncycastle.asn1.DERGeneralizedTime;
import com.android.internal.org.bouncycastle.asn1.DERUTCTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

/* loaded from: classes5.dex */
public class Time extends ASN1Object implements ASN1Choice {
    ASN1Primitive time;

    public static Time getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (!z) {
            throw new IllegalArgumentException("choice item must be explicitly tagged");
        }
        return getInstance(aSN1TaggedObject.getExplicitBaseObject());
    }

    public Time(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1UTCTime) && !(aSN1Primitive instanceof ASN1GeneralizedTime)) {
            throw new IllegalArgumentException("unknown object passed to Time");
        }
        this.time = aSN1Primitive;
    }

    public Time(Date date) {
        SimpleTimeZone simpleTimeZone = new SimpleTimeZone(0, GnssSignalType.CODE_TYPE_Z);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        simpleDateFormat.setTimeZone(simpleTimeZone);
        String str = simpleDateFormat.format(date) + GnssSignalType.CODE_TYPE_Z;
        int parseInt = Integer.parseInt(str.substring(0, 4));
        if (parseInt < 1950 || parseInt > 2049) {
            this.time = new DERGeneralizedTime(str);
        } else {
            this.time = new DERUTCTime(str.substring(2));
        }
    }

    public Time(Date date, Locale locale) {
        SimpleTimeZone simpleTimeZone = new SimpleTimeZone(0, GnssSignalType.CODE_TYPE_Z);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        simpleDateFormat.setCalendar(Calendar.getInstance(locale));
        simpleDateFormat.setTimeZone(simpleTimeZone);
        String str = simpleDateFormat.format(date) + GnssSignalType.CODE_TYPE_Z;
        int parseInt = Integer.parseInt(str.substring(0, 4));
        if (parseInt < 1950 || parseInt > 2049) {
            this.time = new DERGeneralizedTime(str);
        } else {
            this.time = new DERUTCTime(str.substring(2));
        }
    }

    public static Time getInstance(Object obj) {
        if (obj == null || (obj instanceof Time)) {
            return (Time) obj;
        }
        if (obj instanceof ASN1UTCTime) {
            return new Time((ASN1UTCTime) obj);
        }
        if (obj instanceof ASN1GeneralizedTime) {
            return new Time((ASN1GeneralizedTime) obj);
        }
        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public String getTime() {
        ASN1Primitive aSN1Primitive = this.time;
        if (aSN1Primitive instanceof ASN1UTCTime) {
            return ((ASN1UTCTime) aSN1Primitive).getAdjustedTime();
        }
        return ((ASN1GeneralizedTime) aSN1Primitive).getTime();
    }

    public Date getDate() {
        try {
            ASN1Primitive aSN1Primitive = this.time;
            if (aSN1Primitive instanceof ASN1UTCTime) {
                return ((ASN1UTCTime) aSN1Primitive).getAdjustedDate();
            }
            return ((ASN1GeneralizedTime) aSN1Primitive).getDate();
        } catch (ParseException e) {
            throw new IllegalStateException("invalid date string: " + e.getMessage());
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return this.time;
    }

    public String toString() {
        return getTime();
    }
}
