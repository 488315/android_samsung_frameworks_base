package com.android.systemui.pluginlock;

import com.android.systemui.LsRune;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.google.gson.annotations.SerializedName;
import com.sec.ims.scab.CABContract;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PluginLockInstanceData {
    private static final int MAIN_HOME = 4;
    private static final int MAIN_LOCK = 1;
    private static final int SUB_HOME = 8;
    private static final int SUB_LOCK = 2;
    public static final int VERSION = 3;

    @SerializedName("data")
    private ArrayList<Data> mData;

    @SerializedName("version")
    private Integer mVersion = 3;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class Data {

        @SerializedName(CABContract.CABBusinessContactPhone.NUMBER)
        private Integer mNumber;

        @SerializedName("package_name")
        private String mPackageName;

        @SerializedName("recover_data")
        private RecoverData mRecoverData;

        @SerializedName("time_stamp_list")
        private final List<Long> mTimeStampList;

        @SerializedName("time_stamp")
        private Long mTimeStamp = 0L;

        @SerializedName("which")
        private Integer mWhich = 0;

        public Data() {
            ArrayList arrayList = new ArrayList();
            this.mTimeStampList = arrayList;
            arrayList.add(0, 0L);
            arrayList.add(1, 0L);
        }

        public boolean equals(Object obj) {
            if (obj instanceof Data) {
                return this.mPackageName.equals(((Data) obj).getPackageName());
            }
            return false;
        }

        public Integer getNumber() {
            return this.mNumber;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public RecoverData getRecoverData() {
            if (this.mRecoverData == null) {
                this.mRecoverData = new RecoverData();
            }
            return this.mRecoverData;
        }

        public Long getTimeStamp() {
            return this.mTimeStamp;
        }

        public Long getTimeStamps(int i) {
            List<Long> list = this.mTimeStampList;
            if (list != null) {
                return list.get(i);
            }
            return 0L;
        }

        public int getWhich() {
            return this.mWhich.intValue();
        }

        public boolean isEnabled(int i) {
            return LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION ? i == 0 ? (this.mWhich.intValue() & 1) != 0 : i == 1 && (this.mWhich.intValue() & 2) != 0 : this.mTimeStamp.longValue() > 0;
        }

        public void setNumber(Integer num) {
            this.mNumber = num;
        }

        public void setPackageName(String str) {
            this.mPackageName = str;
        }

        public void setRecoverData(RecoverData recoverData) {
            this.mRecoverData = recoverData;
        }

        public void setScreen(int i, boolean z) {
            if (i == 0) {
                if (z) {
                    this.mWhich = Integer.valueOf(this.mWhich.intValue() | 1);
                    return;
                } else {
                    this.mWhich = Integer.valueOf(this.mWhich.intValue() & (-2));
                    return;
                }
            }
            if (i == 1) {
                if (z) {
                    this.mWhich = Integer.valueOf(this.mWhich.intValue() | 2);
                } else {
                    this.mWhich = Integer.valueOf(this.mWhich.intValue() & (-3));
                }
            }
        }

        public void setTimeStamp(Long l) {
            this.mTimeStamp = l;
        }

        public void setTimeStampList(List<Long> list) {
            List<Long> list2 = list;
            if (list == null) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(0, 0L);
                arrayList.add(1, 0L);
                list2 = arrayList;
            }
            Collections.copy(this.mTimeStampList, list2);
        }

        public void setWhich(int i) {
            this.mWhich = Integer.valueOf(i);
        }

        public String toString() {
            String l;
            if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
                l = this.mTimeStampList.get(0) + ", " + this.mTimeStampList.get(1);
            } else {
                l = this.mTimeStamp.toString();
            }
            return "[" + this.mPackageName + "," + this.mNumber + "," + l + "," + this.mWhich + ", " + this.mRecoverData + "]";
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public class RecoverData {

            @SerializedName("clock_state")
            private Integer mClockState = -1;

            @SerializedName(SubRoom.EXTRA_VALUE_CLOCK)
            private Integer mClock = -1;

            @SerializedName(SubRoom.EXTRA_VALUE_NOTIFICATION)
            private Integer mNotificationState = -1;

            @SerializedName("notification_origin")
            private Integer mNotificationBackupType = -1;

            @SerializedName("notification_visibility")
            private Integer mNotificationBackup = -1;

            @SerializedName("wallpaper_dynamic")
            private Integer mWallpaperDynamic = -2;

            @SerializedName("wallpaper_type")
            private Integer mWallpaperType = -1;

            @SerializedName("wallpaper_source")
            private Integer mWallpaperSource = -1;

            @SerializedName("wallpaper_dynamic_sub")
            private Integer mWallpaperDynamicSub = -2;

            @SerializedName("wallpaper_type_sub")
            private Integer mWallpaperTypeSub = -1;

            @SerializedName("wallpaper_source_sub")
            private Integer mWallpaperSourceSub = -1;

            @SerializedName("shortcut")
            private Integer mShortcut = -1;

            @SerializedName("shortcut_state")
            private Integer mShortcutState = -1;

            public Integer getClock() {
                Integer num = this.mClock;
                if (num == null) {
                    return -1;
                }
                return num;
            }

            public Integer getClockState() {
                Integer num = this.mClockState;
                if (num == null) {
                    return -1;
                }
                return num;
            }

            public Integer getNotificationBackupType() {
                Integer num = this.mNotificationBackupType;
                if (num == null) {
                    return -1;
                }
                return num;
            }

            public Integer getNotificationBackupVisibility() {
                Integer num = this.mNotificationBackup;
                if (num == null) {
                    return -1;
                }
                return num;
            }

            public Integer getNotificationState() {
                Integer num = this.mNotificationState;
                if (num == null) {
                    return -1;
                }
                return num;
            }

            public Integer getShortcutBackupValue() {
                Integer num = this.mShortcut;
                if (num == null) {
                    return -1;
                }
                return num;
            }

            public Integer getShortcutState() {
                Integer num = this.mShortcutState;
                if (num == null) {
                    return -1;
                }
                return num;
            }

            public Integer getWallpaperDynamic(int i) {
                if (i == 0) {
                    Integer num = this.mWallpaperDynamic;
                    if (num == null) {
                        return -2;
                    }
                    return num;
                }
                Integer num2 = this.mWallpaperDynamicSub;
                if (num2 == null) {
                    return -2;
                }
                return num2;
            }

            public Integer getWallpaperSource(int i) {
                if (i == 0) {
                    Integer num = this.mWallpaperSource;
                    if (num == null) {
                        return -1;
                    }
                    return num;
                }
                Integer num2 = this.mWallpaperSourceSub;
                if (num2 == null) {
                    return -1;
                }
                return num2;
            }

            public Integer getWallpaperType(int i) {
                if (i == 0) {
                    Integer num = this.mWallpaperType;
                    if (num == null) {
                        return -1;
                    }
                    return num;
                }
                Integer num2 = this.mWallpaperTypeSub;
                if (num2 == null) {
                    return -1;
                }
                return num2;
            }

            public void setClock(int i) {
                this.mClock = Integer.valueOf(i);
            }

            public void setClockState(int i) {
                this.mClockState = Integer.valueOf(i);
            }

            public void setNotificationBackupType(Integer num) {
                this.mNotificationBackupType = num;
            }

            public void setNotificationBackupVisibility(Integer num) {
                this.mNotificationBackup = num;
            }

            public void setNotificationState(int i) {
                this.mNotificationState = Integer.valueOf(i);
            }

            public void setShortcutBackup(int i) {
                this.mShortcut = Integer.valueOf(i);
            }

            public void setShortcutState(int i) {
                this.mShortcutState = Integer.valueOf(i);
            }

            public void setWallpaperDynamic(int i) {
                this.mWallpaperDynamic = Integer.valueOf(i);
                this.mWallpaperDynamicSub = Integer.valueOf(i);
            }

            public void setWallpaperSource(int i) {
                this.mWallpaperSource = Integer.valueOf(i);
                this.mWallpaperSourceSub = Integer.valueOf(i);
            }

            public void setWallpaperType(int i) {
                this.mWallpaperType = Integer.valueOf(i);
                this.mWallpaperTypeSub = Integer.valueOf(i);
            }

            public String toString() {
                return "[" + this.mClock + "," + this.mNotificationState + "," + this.mNotificationBackupType + "," + this.mNotificationBackup + "," + this.mWallpaperDynamic + "," + this.mWallpaperType + "," + this.mWallpaperSource + "," + this.mWallpaperDynamicSub + "," + this.mWallpaperTypeSub + "," + this.mWallpaperSourceSub + "," + this.mShortcut + "," + this.mShortcutState + "]";
            }

            public void setWallpaperDynamic(int i, int i2) {
                if (i == 0) {
                    this.mWallpaperDynamic = Integer.valueOf(i2);
                } else {
                    this.mWallpaperDynamicSub = Integer.valueOf(i2);
                }
            }

            public void setWallpaperSource(int i, int i2) {
                if (i == 0) {
                    this.mWallpaperSource = Integer.valueOf(i2);
                } else {
                    this.mWallpaperSourceSub = Integer.valueOf(i2);
                }
            }

            public void setWallpaperType(int i, int i2) {
                if (i == 0) {
                    this.mWallpaperType = Integer.valueOf(i2);
                } else {
                    this.mWallpaperTypeSub = Integer.valueOf(i2);
                }
            }
        }

        public void setTimeStamp(int i, Long l) {
            this.mTimeStampList.set(i, l);
        }

        public List<Long> getTimeStamps() {
            return this.mTimeStampList;
        }
    }

    public void addData(Data data) {
        if (this.mData == null) {
            this.mData = new ArrayList<>();
        }
        this.mData.add(data);
    }

    public boolean contain(String str) {
        Iterator<Data> it = this.mData.iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public Data getData(String str) {
        Iterator<Data> it = this.mData.iterator();
        while (it.hasNext()) {
            Data next = it.next();
            if (str.equals(next.getPackageName())) {
                return next;
            }
        }
        return null;
    }

    public ArrayList<Data> getDataList() {
        if (this.mData == null) {
            this.mData = new ArrayList<>();
        }
        return this.mData;
    }

    public Integer getVersion() {
        return this.mVersion;
    }

    public void setData(ArrayList<Data> arrayList) {
        this.mData = arrayList;
    }

    public void setVersion(int i) {
        this.mVersion = Integer.valueOf(i);
    }

    public String toString() {
        if (this.mData == null) {
            return "no data";
        }
        StringBuilder sb = new StringBuilder("\n");
        Iterator<Data> it = this.mData.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
