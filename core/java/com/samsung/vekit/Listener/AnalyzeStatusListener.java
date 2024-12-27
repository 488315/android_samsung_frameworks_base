package com.samsung.vekit.Listener;

import com.samsung.vekit.Common.Type.AnalyzeSolutionErrorType;
import com.samsung.vekit.Common.Type.AnalyzeType;

public interface AnalyzeStatusListener extends NativeInterfaceListener {
    void onAnalyzeCompleted();

    void onAnalyzeFailed();

    void onAnalyzePaused();

    void onAnalyzeResumed();

    void onAnalyzeStarted();

    void onAnalyzeStopped();

    void onSolutionError(
            AnalyzeType analyzeType, AnalyzeSolutionErrorType analyzeSolutionErrorType);

    void onSolutionLoaded(AnalyzeType analyzeType);

    void onSolutionUnloaded(AnalyzeType analyzeType);
}
