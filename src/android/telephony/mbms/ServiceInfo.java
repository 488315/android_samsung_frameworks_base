package android.telephony.mbms;

import android.os.Parcel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes4.dex */
public class ServiceInfo {
    static final int MAP_LIMIT = 1000;
    private final String className;
    private final List<Locale> locales;
    private final Map<Locale, String> names;
    private final String serviceId;
    private final Date sessionEndTime;
    private final Date sessionStartTime;

    public ServiceInfo(Map<Locale, String> map, String str, List<Locale> list, String str2, Date date, Date date2) {
        if (map == null || str == null || list == null || str2 == null || date == null || date2 == null) {
            throw new IllegalArgumentException("Bad ServiceInfo construction");
        }
        if (map.size() > 1000) {
            throw new RuntimeException("bad map length " + map.size());
        }
        if (list.size() > 1000) {
            throw new RuntimeException("bad locales length " + list.size());
        }
        HashMap map2 = new HashMap(map.size());
        this.names = map2;
        map2.putAll(map);
        this.className = str;
        this.locales = new ArrayList(list);
        this.serviceId = str2;
        this.sessionStartTime = (Date) date.clone();
        this.sessionEndTime = (Date) date2.clone();
    }

    protected ServiceInfo(Parcel parcel) {
        int i = parcel.readInt();
        if (i > 1000 || i < 0) {
            throw new RuntimeException("bad map length" + i);
        }
        this.names = new HashMap(i);
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            this.names.put((Locale) parcel.readSerializable(Locale.class.getClassLoader(), Locale.class), parcel.readString());
            i = i2;
        }
        this.className = parcel.readString();
        int i3 = parcel.readInt();
        if (i3 > 1000 || i3 < 0) {
            throw new RuntimeException("bad locale length " + i3);
        }
        this.locales = new ArrayList(i3);
        while (true) {
            int i4 = i3 - 1;
            if (i3 > 0) {
                this.locales.add((Locale) parcel.readSerializable(Locale.class.getClassLoader(), Locale.class));
                i3 = i4;
            } else {
                this.serviceId = parcel.readString();
                this.sessionStartTime = (Date) parcel.readSerializable(Date.class.getClassLoader(), Date.class);
                this.sessionEndTime = (Date) parcel.readSerializable(Date.class.getClassLoader(), Date.class);
                return;
            }
        }
    }

    public void writeToParcel(Parcel parcel, int i) throws IOException {
        Set<Locale> setKeySet = this.names.keySet();
        parcel.writeInt(setKeySet.size());
        for (Locale locale : setKeySet) {
            parcel.writeSerializable(locale);
            parcel.writeString(this.names.get(locale));
        }
        parcel.writeString(this.className);
        parcel.writeInt(this.locales.size());
        Iterator<Locale> it = this.locales.iterator();
        while (it.hasNext()) {
            parcel.writeSerializable(it.next());
        }
        parcel.writeString(this.serviceId);
        parcel.writeSerializable(this.sessionStartTime);
        parcel.writeSerializable(this.sessionEndTime);
    }

    public CharSequence getNameForLocale(Locale locale) {
        if (!this.names.containsKey(locale)) {
            throw new NoSuchElementException("Locale not supported");
        }
        return this.names.get(locale);
    }

    public Set<Locale> getNamedContentLocales() {
        return Collections.unmodifiableSet(this.names.keySet());
    }

    public String getServiceClassName() {
        return this.className;
    }

    public List<Locale> getLocales() {
        return this.locales;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public Date getSessionStartTime() {
        return this.sessionStartTime;
    }

    public Date getSessionEndTime() {
        return this.sessionEndTime;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ServiceInfo)) {
            return false;
        }
        ServiceInfo serviceInfo = (ServiceInfo) obj;
        return Objects.equals(this.names, serviceInfo.names) && Objects.equals(this.className, serviceInfo.className) && Objects.equals(this.locales, serviceInfo.locales) && Objects.equals(this.serviceId, serviceInfo.serviceId) && Objects.equals(this.sessionStartTime, serviceInfo.sessionStartTime) && Objects.equals(this.sessionEndTime, serviceInfo.sessionEndTime);
    }

    public int hashCode() {
        return Objects.hash(this.names, this.className, this.locales, this.serviceId, this.sessionStartTime, this.sessionEndTime);
    }
}
