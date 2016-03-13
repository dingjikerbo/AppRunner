package com.dingjikerbo.library;

/**
 * Created by dingjikerbo on 16/3/13.
 */
public abstract class Singleton<T> {
    private T sInstance;

    protected abstract T create();

    public final T getInstance() {
        if (sInstance == null) {
            synchronized (this) {
                if (sInstance == null) {
                    sInstance = create();
                }
                return sInstance;
            }
        }
        return sInstance;
    }
}
