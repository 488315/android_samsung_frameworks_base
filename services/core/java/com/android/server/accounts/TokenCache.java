package com.android.server.accounts;

import android.accounts.Account;
import android.util.LruCache;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class TokenCache {
    public TokenLruCache mCachedTokens;

    public final class Key {
        public final Account account;
        public final String packageName;
        public final byte[] sigDigest;
        public final String tokenType;

        public Key(Account account, String str, String str2, byte[] bArr) {
            this.account = account;
            this.tokenType = str;
            this.packageName = str2;
            this.sigDigest = bArr;
        }

        public final boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Key)) {
                return false;
            }
            Key key = (Key) obj;
            return Objects.equals(this.account, key.account)
                    && Objects.equals(this.packageName, key.packageName)
                    && Objects.equals(this.tokenType, key.tokenType)
                    && Arrays.equals(this.sigDigest, key.sigDigest);
        }

        public final int hashCode() {
            return Arrays.hashCode(this.sigDigest)
                    ^ ((this.account.hashCode() ^ this.packageName.hashCode())
                            ^ this.tokenType.hashCode());
        }
    }

    public final class TokenLruCache extends LruCache {
        public HashMap mAccountEvictors;
        public HashMap mTokenEvictors;

        public final class Evictor {
            public final List mKeys = new ArrayList();

            public Evictor() {}

            public final void evict() {
                Iterator it = ((ArrayList) this.mKeys).iterator();
                while (it.hasNext()) {
                    TokenLruCache.this.remove((Key) it.next());
                }
            }
        }

        @Override // android.util.LruCache
        public final void entryRemoved(boolean z, Object obj, Object obj2, Object obj3) {
            Evictor evictor;
            Key key = (Key) obj;
            Value value = (Value) obj2;
            Value value2 = (Value) obj3;
            if (value == null
                    || value2 != null
                    || (evictor =
                                    (Evictor)
                                            this.mTokenEvictors.remove(
                                                    new Pair(key.account.type, value.token)))
                            == null) {
                return;
            }
            evictor.evict();
        }

        @Override // android.util.LruCache
        public final int sizeOf(Object obj, Object obj2) {
            return ((Value) obj2).token.length();
        }
    }

    public final class Value {
        public final long expiryEpochMillis;
        public final String token;

        public Value(long j, String str) {
            this.token = str;
            this.expiryEpochMillis = j;
        }
    }

    public final void put(
            Account account, String str, String str2, String str3, byte[] bArr, long j) {
        Objects.requireNonNull(account);
        if (System.currentTimeMillis() > j) {
            return;
        }
        Key key = new Key(account, str2, str3, bArr);
        Value value = new Value(j, str);
        TokenLruCache tokenLruCache = this.mCachedTokens;
        tokenLruCache.getClass();
        Pair pair = new Pair(account.type, str);
        TokenLruCache.Evictor evictor =
                (TokenLruCache.Evictor) tokenLruCache.mTokenEvictors.get(pair);
        if (evictor == null) {
            evictor = tokenLruCache.new Evictor();
        }
        ((ArrayList) evictor.mKeys).add(key);
        tokenLruCache.mTokenEvictors.put(pair, evictor);
        TokenLruCache.Evictor evictor2 =
                (TokenLruCache.Evictor) tokenLruCache.mAccountEvictors.get(account);
        if (evictor2 == null) {
            evictor2 = tokenLruCache.new Evictor();
        }
        ((ArrayList) evictor2.mKeys).add(key);
        tokenLruCache.mAccountEvictors.put(account, evictor2);
        tokenLruCache.put(key, value);
    }
}
