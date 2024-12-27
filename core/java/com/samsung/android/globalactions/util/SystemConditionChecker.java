package com.samsung.android.globalactions.util;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.samsung.android.globalactions.presentation.features.Features;

/* loaded from: classes6.dex */
public class SystemConditionChecker implements ConditionChecker {
    private static final String TAG = "SystemConditionChecker";
    private final Features mFeatures;
    private final LogWrapper mLogWrapper;
    private final UtilFactory mUtilFactory;

    public SystemConditionChecker(UtilFactory utilFactory, Features features, LogWrapper logWrapper) {
        this.mUtilFactory = utilFactory;
        this.mFeatures = features;
        this.mLogWrapper = logWrapper;
    }

    private boolean isEnabled(SystemConditions facadeEnum) {
        switch (facadeEnum) {
        }
        return false;
    }

    @Override // com.samsung.android.globalactions.util.ConditionChecker
    public boolean isEnabled(Object name) {
        try {
            Long time = Long.valueOf(System.currentTimeMillis());
            SystemConditions condition = SystemConditions.valueOf(name.toString());
            boolean ret = isEnabled(condition);
            Long current = Long.valueOf(System.currentTimeMillis());
            this.mLogWrapper.i(TAG, NavigationBarInflaterView.SIZE_MOD_START + condition.name().toLowerCase() + "] " + ret + " (" + (current.longValue() - time.longValue()) + NavigationBarInflaterView.KEY_CODE_END);
            return ret;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
