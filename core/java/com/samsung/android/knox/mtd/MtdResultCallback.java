package com.samsung.android.knox.mtd;

import java.util.List;

public abstract class MtdResultCallback {
    public abstract void onFinished(List<AnalysisResult> list);
}
