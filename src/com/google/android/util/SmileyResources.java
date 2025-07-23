package com.google.android.util;

import com.google.android.util.AbstractMessageParser;
import java.util.HashMap;
import java.util.Set;

/* loaded from: classes6.dex */
public class SmileyResources implements AbstractMessageParser.Resources {
    private HashMap<String, Integer> mSmileyToRes = new HashMap<>();
    private final AbstractMessageParser.TrieNode smileys = new AbstractMessageParser.TrieNode();

    @Override // com.google.android.util.AbstractMessageParser.Resources
    public AbstractMessageParser.TrieNode getAcronyms() {
        return null;
    }

    @Override // com.google.android.util.AbstractMessageParser.Resources
    public AbstractMessageParser.TrieNode getDomainSuffixes() {
        return null;
    }

    @Override // com.google.android.util.AbstractMessageParser.Resources
    public Set<String> getSchemes() {
        return null;
    }

    public SmileyResources(String[] strArr, int[] iArr) {
        for (int i = 0; i < strArr.length; i++) {
            AbstractMessageParser.TrieNode.addToTrie(this.smileys, strArr[i], "");
            this.mSmileyToRes.put(strArr[i], Integer.valueOf(iArr[i]));
        }
    }

    public int getSmileyRes(String str) {
        Integer num = this.mSmileyToRes.get(str);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    @Override // com.google.android.util.AbstractMessageParser.Resources
    public AbstractMessageParser.TrieNode getSmileys() {
        return this.smileys;
    }
}
