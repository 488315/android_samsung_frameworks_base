package com.android.internal.app;

import android.content.pm.ResolveInfo;

import com.android.internal.app.chooser.TargetInfo;

import java.util.Comparator;

interface ResolverComparatorModel {
    Comparator<ResolveInfo> getComparator();

    float getScore(TargetInfo targetInfo);

    void notifyOnTargetSelected(TargetInfo targetInfo);
}
