package com.android.server.apphibernation;

import java.text.SimpleDateFormat;

public final class UserLevelState {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public boolean hibernated;
    public long lastUnhibernatedMs;
    public String packageName;
    public long savedByte;

    public final String toString() {
        return "UserLevelState{packageName='"
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
