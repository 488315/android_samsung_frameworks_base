package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.AnalyzeSolutionErrorType;
import com.samsung.vekit.Common.Type.AnalyzeType;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Item.Item;
import com.samsung.vekit.Listener.AnalyzeStatusListener;

/* loaded from: classes6.dex */
public class AnalyzeInfo implements AnalyzeStatusListener {
    private AnalyzeType analyzeType;
    private String analyzedDataPath;
    private String extension;
    private Element target;

    public AnalyzeInfo(
            AnalyzeType analyzeType,
            Content targetContent,
            String analyzedDataPath,
            String extension) {
        this.analyzeType = analyzeType;
        this.target = targetContent;
        this.analyzedDataPath = analyzedDataPath;
        this.extension = extension;
    }

    public AnalyzeInfo(
            AnalyzeType analyzeType, Item targetItem, String analyzedDataPath, String extension) {
        this.analyzeType = analyzeType;
        this.target = targetItem;
        this.analyzedDataPath = analyzedDataPath;
        this.extension = extension;
    }

    public AnalyzeType getAnalyzeType() {
        return this.analyzeType;
    }

    public void setAnalyzeType(AnalyzeType analyzeType) {
        this.analyzeType = analyzeType;
    }

    public Element getTarget() {
        return this.target;
    }

    public void setTarget(Element target) {
        this.target = target;
    }

    public String getAnalyzedDataPath() {
        return this.analyzedDataPath;
    }

    public void setAnalyzedDataPath(String analyzedDataPath) {
        this.analyzedDataPath = analyzedDataPath;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onAnalyzeStarted() {}

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onAnalyzeResumed() {}

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onAnalyzePaused() {}

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onAnalyzeCompleted() {}

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onAnalyzeStopped() {}

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onAnalyzeFailed() {}

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onSolutionLoaded(AnalyzeType type) {}

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onSolutionUnloaded(AnalyzeType type) {}

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onSolutionError(AnalyzeType type, AnalyzeSolutionErrorType errorType) {}
}
