package com.samsung.android.camera.scpm.list;

import com.samsung.android.camera.scpm.ScpmList;

public final class HiddenIdPermittedList extends ScpmList {
    public static final HiddenIdPermittedList INSTANCE;

    static {
        HiddenIdPermittedList hiddenIdPermittedList = new HiddenIdPermittedList();
        hiddenIdPermittedList.mType = ScpmList.PolicyType.HIDDEN_ID_PERMITTED;
        hiddenIdPermittedList.mVersion = "2024062500";
        hiddenIdPermittedList.mConfigurationName = "hiddenidpermittedlist-3a1e";
        hiddenIdPermittedList.mItemNames = new String[] {"packageName", "value", "extra"};
        hiddenIdPermittedList.setPackageList(
                new String[][] {
                    new String[] {"Y29tLmZhY3RvcnkubW1pZ3JvdXA=", "1", "0"},
                    new String[] {"Y29tLnZhbC5oYXJkd2FyZQ==", "1", "0"},
                    new String[] {"Y29tLmxvbmdjaGVlci5taWR0ZXN0", "1", "0"},
                    new String[] {"Y29tLnNhbXN1bmc=", "2", "0"},
                    new String[] {"Y29tLnNlYw==", "2", "0"},
                    new String[] {"Y2xpZW50LnBpZA==", "2", "0"},
                    new String[] {"Y29tLmRpYW1vbmQuYXBw", "2", "0"},
                    new String[] {"Y29tLndhbG1hcnQuZGlhbW9uZC5hcHA=", "2", "0"},
                    new String[] {"Y29tLnNuYXBjaGF0LmFuZHJvaWQ=", "2", "0"},
                    new String[] {"aWUuZ2FyZGEuYW5kcm9pZC5hbnBy", "2", "0"},
                    new String[] {"Y29tLnF1cmFtc29mdC50YWN0aWNzY2FtZXJh", "2", "0"},
                    new String[] {"Y29tLnNyYnIuYXBwLnNlbnNvcnByb3h5", "2", "0"},
                    new String[] {"Y29tLnByaXNtLmxpdmU=", "2", "0"},
                    new String[] {"Y29tLnNzLmFuZHJvaWQudWdjLmF3ZW1l", "2", "0"},
                    new String[] {"Y29tLnNzLmFuZHJvaWQudWdjLnRyaWxs", "2", "0"},
                    new String[] {"Y29tLnpoaWxpYW9hcHAubXVzaWNhbGx5", "2", "0"},
                    new String[] {"Y29tLmNhbXBtb2JpbGUuc25vdw==", "2", "0"},
                    new String[] {"Y29tLmxpbmVjb3JwLmI2MTIuYW5kcm9pZA==", "2", "0"},
                    new String[] {"Y29tLmxpbmVjb3JwLmZvb2RjYW0uYW5kcm9pZA==", "2", "0"},
                    new String[] {"Y29tLmxpbmVjb3JwLmZvb2RjYW1jbi5hbmRyb2lk", "2", "0"},
                    new String[] {"Y29tLnNub3djb3JwLnNvZGEuYW5kcm9pZA==", "2", "0"},
                    new String[] {"Y29tLnlpcnVpa2Uuc29kYWNuLmFuZHJvaWQ=", "2", "0"}
                });
        INSTANCE = hiddenIdPermittedList;
    }
}
