package com.samsung.android.sume.core.message;

import android.util.Pair;

import java.util.Map;

/* loaded from: classes6.dex */
public interface MessageProducer {
    Message newMessage(int i);

    Message newMessage(int i, Object obj);

    Message newMessage(int i, String str, Object obj);

    Message newMessage(int i, Map<String, Object> map);

    Message newMessage(int i, Pair<String, Object>... pairArr);
}
