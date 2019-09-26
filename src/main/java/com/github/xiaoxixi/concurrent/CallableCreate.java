package com.github.xiaoxixi.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableCreate implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("Callable implements");
        return "call able result";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new CallableCreate());
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("result from call able:" + futureTask.get());
    }
}
