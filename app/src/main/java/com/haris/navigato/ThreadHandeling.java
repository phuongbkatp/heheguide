package com.haris.navigato;

/**
 * Created by hp on 5/20/2018.
 */

public class ThreadHandeling implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
    }
}
