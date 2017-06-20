package com.zionstudio.youzhu.test;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;

/**
 * Created by hasee on 3/16/2017.
 */

public abstract class MySimpleAsyncTask<Result> {
    /**
     * HandlerThread内部封装了自己的Handler和Thread，有单独的Looper和线程队列
     */
    private static final HandlerThread mHandlerThread = new HandlerThread("mysimpleasynctask" ,
            Process.THREAD_PRIORITY_BACKGROUND);
    //在静态代码块中启动该线程
    static {
        mHandlerThread.start();
    }
    /**
     *     创建两个Handler，分别关联UI线程消息队列和关联异步线程队列HandlerThread子线程
     */
    final Handler mUIHandler = new Handler(Looper.getMainLooper());
    final Handler mAsyncHandler = new Handler(mHandlerThread.getLooper());

    /**
     * onPreExecute()任务执行前的初始化操作等
     */
    protected void onPreExecute(){}

    /**
     * doInBackground()后台执行任务
     * @return 返回执行结果
     */
    protected abstract Result doInBackground();

    /**
     * onPostExecute()  接收doInBackground()的执行结果，更新UI
     * @param result 执行结果
     */
    protected void onPostExecute(Result result){}

    /**
     * execute()执行任务，调用doInBackground，并且将结果传递给UI线程，使用户可以在onPostExecute处理结果
     * @return
     */
    public final MySimpleAsyncTask<Result> execute(){

        onPreExecute();
        //将任务传递到HandlerThread线程中执行
        mAsyncHandler.post(new Runnable() {
            @Override
            public void run() {
                //后台执行任务，完成之后向UI线程POST数据，用以更新UI等操作
                postExecute(doInBackground());
            }
        });
        return this;
    }

    protected void postExecute(final Result result){
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                onPostExecute(result);
            }
        });
    }
}
