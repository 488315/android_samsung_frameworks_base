package com.samsung.android.ims.ft;

public interface SemFtIntent {
    public static final String CATEGORY_ACTION =
            "com.samsung.rcs.framework.filetransfer.category.ACTION";
    public static final String CATEGORY_NOTIFICATION =
            "com.samsung.rcs.framework.filetransfer.category.NOTIFICATION";

    public interface Extras {
        public static final String EXTRA_FILE_PATH = "filePath";
    }
}
