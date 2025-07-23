package com.android.internal.widget.remotecompose.core.documentation;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public class OperationField {
    final String mDescription;
    final String mName;
    ArrayList<StringPair> mPossibleValues;
    final int mType;
    String mVarSize;

    public OperationField(int i, String str, String str2) {
        this.mVarSize = null;
        this.mPossibleValues = new ArrayList<>();
        this.mType = i;
        this.mName = str;
        this.mDescription = str2;
    }

    public OperationField(int i, String str, String str2, String str3) {
        this.mVarSize = null;
        this.mPossibleValues = new ArrayList<>();
        this.mType = i;
        this.mName = str;
        this.mDescription = str3;
        this.mVarSize = str2;
    }

    public int getType() {
        return this.mType;
    }

    public String getName() {
        return this.mName;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public ArrayList<StringPair> getPossibleValues() {
        return this.mPossibleValues;
    }

    public void possibleValue(String str, String str2) {
        this.mPossibleValues.add(new StringPair(str, str2));
    }

    public boolean hasEnumeratedValues() {
        return !this.mPossibleValues.isEmpty();
    }

    public String getVarSize() {
        return this.mVarSize;
    }

    public int getSize() {
        int i = this.mType;
        if (i == 0 || i == 1) {
            return 4;
        }
        if (i == 6) {
            return 1;
        }
        switch (i) {
            case 8:
                return 8;
            case 9:
                return 2;
            case 10:
            case 11:
                return -1;
            default:
                return 0;
        }
    }
}
