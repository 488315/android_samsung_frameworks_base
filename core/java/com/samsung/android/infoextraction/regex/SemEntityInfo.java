package com.samsung.android.infoextraction.regex;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class SemEntityInfo {
    private ArrayList<String> dateInfo = new ArrayList<>();
    private ArrayList<String> timeInfo = new ArrayList<>();
    private ArrayList<String> phoneNumInfo = new ArrayList<>();
    private ArrayList<String> emailAddressInfo = new ArrayList<>();
    private ArrayList<String> urlInfo = new ArrayList<>();
    private ArrayList<String> dateMillisInfo = new ArrayList<>();
    private ArrayList<String> timeMillisInfo = new ArrayList<>();

    public class Type {

        @Deprecated(forRemoval = true, since = "15.0")
        public static final int DATE = 1;

        @Deprecated(forRemoval = true, since = "15.0")
        public static final int DATE_MILLISECOND = 2;

        @Deprecated(forRemoval = true, since = "15.0")
        public static final int EMAIL_ADDRESS = 6;

        @Deprecated(forRemoval = true, since = "15.0")
        public static final int PHONE_NUMBER = 5;

        @Deprecated(forRemoval = true, since = "15.0")
        public static final int TIME = 3;

        @Deprecated(forRemoval = true, since = "15.0")
        public static final int TIME_MILLISECOND = 4;

        @Deprecated(forRemoval = true, since = "15.0")
        public static final int URL = 7;

        private Type() {}
    }

    @Deprecated(forRemoval = true, since = "15.0")
    public List<String> getInfoList(int type) {
        switch (type) {
            case 1:
                return this.dateInfo;
            case 2:
                return this.dateMillisInfo;
            case 3:
                return this.timeInfo;
            case 4:
                return this.timeMillisInfo;
            case 5:
                return this.phoneNumInfo;
            case 6:
                return this.emailAddressInfo;
            case 7:
                return this.urlInfo;
            default:
                return new ArrayList();
        }
    }

    public void setInfo(String data, int type) {
        switch (type) {
            case 1:
                this.dateInfo.add(data);
                break;
            case 2:
                this.dateMillisInfo.add(data);
                break;
            case 3:
                this.timeInfo.add(data);
                break;
            case 4:
                this.timeMillisInfo.add(data);
                break;
            case 5:
                this.phoneNumInfo.add(data);
                break;
            case 6:
                this.emailAddressInfo.add(data);
                break;
            case 7:
                this.urlInfo.add(data);
                break;
        }
    }

    public int getCount(int type) {
        switch (type) {
            case 1:
                return this.dateInfo.size();
            case 2:
                return this.dateMillisInfo.size();
            case 3:
                return this.timeInfo.size();
            case 4:
                return this.timeMillisInfo.size();
            case 5:
                return this.phoneNumInfo.size();
            case 6:
                return this.emailAddressInfo.size();
            case 7:
                return this.urlInfo.size();
            default:
                return 0;
        }
    }

    public boolean deleteInfo(int index, int type) {
        switch (type) {
            case 1:
                if (index >= this.dateInfo.size()) {
                    return false;
                }
                this.dateInfo.remove(index);
                return true;
            case 2:
                if (index >= this.dateMillisInfo.size()) {
                    return false;
                }
                this.dateMillisInfo.remove(index);
                return true;
            case 3:
                if (index >= this.timeInfo.size()) {
                    return false;
                }
                this.timeInfo.remove(index);
                return true;
            case 4:
                if (index >= this.timeMillisInfo.size()) {
                    return false;
                }
                this.timeMillisInfo.remove(index);
                return true;
            case 5:
                if (index >= this.phoneNumInfo.size()) {
                    return false;
                }
                this.phoneNumInfo.remove(index);
                return true;
            case 6:
                if (index >= this.emailAddressInfo.size()) {
                    return false;
                }
                this.emailAddressInfo.remove(index);
                return true;
            case 7:
                if (index >= this.urlInfo.size()) {
                    return false;
                }
                this.urlInfo.remove(index);
                return true;
            default:
                return false;
        }
    }

    public void clear() {
        this.dateInfo.clear();
        this.dateMillisInfo.clear();
        this.timeInfo.clear();
        this.timeMillisInfo.clear();
        this.phoneNumInfo.clear();
        this.emailAddressInfo.clear();
        this.urlInfo.clear();
    }
}
