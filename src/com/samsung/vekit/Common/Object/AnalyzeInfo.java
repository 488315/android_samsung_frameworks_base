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

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onAnalyzeCompleted() {
    }

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onAnalyzeFailed() {
    }

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onAnalyzePaused() {
    }

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onAnalyzeResumed() {
    }

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onAnalyzeStarted() {
    }

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onAnalyzeStopped() {
    }

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onSolutionError(AnalyzeType analyzeType, AnalyzeSolutionErrorType analyzeSolutionErrorType) {
    }

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onSolutionLoaded(AnalyzeType analyzeType) {
    }

    @Override // com.samsung.vekit.Listener.AnalyzeStatusListener
    public void onSolutionUnloaded(AnalyzeType analyzeType) {
    }

    public AnalyzeInfo(AnalyzeType analyzeType, Content content, String str, String str2) {
        this.analyzeType = analyzeType;
        this.target = content;
        this.analyzedDataPath = str;
        this.extension = str2;
    }

    public AnalyzeInfo(AnalyzeType analyzeType, Item item, String str, String str2) {
        this.analyzeType = analyzeType;
        this.target = item;
        this.analyzedDataPath = str;
        this.extension = str2;
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

    public void setTarget(Element element) {
        this.target = element;
    }

    public String getAnalyzedDataPath() {
        return this.analyzedDataPath;
    }

    public void setAnalyzedDataPath(String str) {
        this.analyzedDataPath = str;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String str) {
        this.extension = str;
    }
}
