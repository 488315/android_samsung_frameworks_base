package com.android.server.locksettings;

import android.util.SparseArray;

public abstract class SyntheticPasswordMdfpp {
    public static final SparseArray mSecureModeCache = new SparseArray();

    public class EmptySlotException extends SecurityException {
        private static final long serialVersionUID = 1;

        public EmptySlotException(String str) {
            super(str);
        }
    }

    static {
        new SparseArray();
    }

    public static int getSecureMode(int i) {
        int intValue;
        SparseArray sparseArray = mSecureModeCache;
        synchronized (sparseArray) {
            try {
                Integer num = (Integer) sparseArray.get(i);
                if (num == null) {
                    throw new EmptySlotException("Empty secure mode for user " + i);
                }
                intValue = num.intValue();
            } catch (Throwable th) {
                throw th;
            }
        }
        return intValue;
    }
}
