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

/* loaded from: classes.dex */
public class TokenCache {
    public TokenLruCache mCachedTokens = new TokenLruCache();

    public class Value {
        public final long expiryEpochMillis;
        public final String token;

        public Value(String str, long j) {
            this.token = str;
            this.expiryEpochMillis = j;
        }
    }

    public class Key {
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

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Key)) {
                return false;
            }
            Key key = (Key) obj;
            return Objects.equals(this.account, key.account) && Objects.equals(this.packageName, key.packageName) && Objects.equals(this.tokenType, key.tokenType) && Arrays.equals(this.sigDigest, key.sigDigest);
        }

        public int hashCode() {
            return Arrays.hashCode(this.sigDigest) ^ ((this.account.hashCode() ^ this.packageName.hashCode()) ^ this.tokenType.hashCode());
        }
    }

    public class TokenLruCache extends LruCache {
        public HashMap mAccountEvictors;
        public HashMap mTokenEvictors;

        public class Evictor {
            public final List mKeys = new ArrayList();

            public Evictor() {
            }

            public void add(Key key) {
                this.mKeys.add(key);
            }

            public void evict() {
                Iterator it = this.mKeys.iterator();
                while (it.hasNext()) {
                    TokenLruCache.this.remove((Key) it.next());
                }
            }
        }

        public TokenLruCache() {
            super(64000);
            this.mTokenEvictors = new HashMap();
            this.mAccountEvictors = new HashMap();
        }

        @Override // android.util.LruCache
        public int sizeOf(Key key, Value value) {
            return value.token.length();
        }

        @Override // android.util.LruCache
        public void entryRemoved(boolean z, Key key, Value value, Value value2) {
            Evictor evictor;
            if (value == null || value2 != null || (evictor = (Evictor) this.mTokenEvictors.remove(new Pair(key.account.type, value.token))) == null) {
                return;
            }
            evictor.evict();
        }

        public void putToken(Key key, Value value) {
            Pair pair = new Pair(key.account.type, value.token);
            Evictor evictor = (Evictor) this.mTokenEvictors.get(pair);
            if (evictor == null) {
                evictor = new Evictor();
            }
            evictor.add(key);
            this.mTokenEvictors.put(pair, evictor);
            Evictor evictor2 = (Evictor) this.mAccountEvictors.get(key.account);
            if (evictor2 == null) {
                evictor2 = new Evictor();
            }
            evictor2.add(key);
            this.mAccountEvictors.put(key.account, evictor2);
            put(key, value);
        }

        public void evict(String str, String str2) {
            Evictor evictor = (Evictor) this.mTokenEvictors.get(new Pair(str, str2));
            if (evictor != null) {
                evictor.evict();
            }
        }

        public void evict(Account account) {
            Evictor evictor = (Evictor) this.mAccountEvictors.get(account);
            if (evictor != null) {
                evictor.evict();
            }
        }
    }

    public void put(Account account, String str, String str2, String str3, byte[] bArr, long j) {
        Objects.requireNonNull(account);
        if (str == null || System.currentTimeMillis() > j) {
            return;
        }
        this.mCachedTokens.putToken(new Key(account, str2, str3, bArr), new Value(str, j));
    }

    public void remove(String str, String str2) {
        this.mCachedTokens.evict(str, str2);
    }

    public void remove(Account account) {
        this.mCachedTokens.evict(account);
    }

    public Value get(Account account, String str, String str2, byte[] bArr) {
        Value value = (Value) this.mCachedTokens.get(new Key(account, str, str2, bArr));
        long currentTimeMillis = System.currentTimeMillis();
        if (value != null && currentTimeMillis < value.expiryEpochMillis) {
            return value;
        }
        if (value == null) {
            return null;
        }
        remove(account.type, value.token);
        return null;
    }
}
