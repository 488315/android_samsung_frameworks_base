package com.android.server;

public interface RichwaveTuningParameteres extends CommonTuningParamters {
    int getSeekDC();

    int getSeekQA();

    boolean setSeekDC(int i);

    boolean setSeekQA(int i);
}
