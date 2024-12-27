package com.android.server.apphibernation;

import java.text.SimpleDateFormat;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GlobalLevelState {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public boolean hibernated;
    public long lastUnhibernatedMs;
    public String packageName;
    public long savedByte;

    public final String toString() {
        return "GlobalLevelState{packageName='"
                + this.packageName
                + "', hibernated="
                + this.hibernated
                + "', savedByte="
                + this.savedByte
                + "', lastUnhibernated="
                + DATE_FORMAT.format(Long.valueOf(this.lastUnhibernatedMs))
                + '}';
    }
}
